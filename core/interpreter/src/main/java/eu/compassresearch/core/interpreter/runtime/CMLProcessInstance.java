package eu.compassresearch.core.interpreter.runtime;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.ObjectValue;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CMLAlphabet;
import eu.compassresearch.core.interpreter.cml.CMLBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CMLProcess;
import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ProcessState;
import eu.compassresearch.core.interpreter.eval.AlphabetInspectionVisitor;
import eu.compassresearch.core.interpreter.util.Pair;
import eu.compassresearch.core.interpreter.values.ProcessValue;

public class CMLProcessInstance extends AbstractInstance<PProcess>  {

	private AProcessDefinition processDef;
	private CMLActionInstance mainBehaviour = null;
	private AlphabetInspectionVisitor alphabetInspectionVisitor = new AlphabetInspectionVisitor();
	private Context globalContext;
	
	public CMLProcessInstance(AProcessDefinition processDef, CMLProcess parent, Context globalContext)
	{
		super(parent);
		this.globalContext = globalContext; 
		ProcessContext context = new ProcessContext(processDef.getLocation(), "", globalContext, null);
		this.processDef = processDef;
		//this.prcEval = new ProcessEvaluatorNew(processDef.getProcess(),context,this);
		pushNext(processDef.getProcess(), context);
	}
	
	@Override
	public void start(CMLSupervisorEnvironment env) {
		this.env = env;
		state = ProcessState.RUNNABLE;
		env.addPupil(this);
	}

	@Override
	public CMLAlphabet inspect() throws AnalysisException{
		
		CMLAlphabet alpha = null;
		
		if(null != mainBehaviour)
			alpha = mainBehaviour.inspect();
		else
		{
			alpha = nextState().first.apply(alphabetInspectionVisitor,nextState().second);
		}
		
		return alpha;
	}

	@Override
	public ProcessState getState() {
		//If the mainNehaviour is null then this process is either not started or it is a composition process without
		//behavior defined through other defined processes
		if(null == mainBehaviour)
			return this.state;
		else
			return mainBehaviour.getState();
	}

	@Override
	public LexNameToken name() {
		// TODO Auto-generated method stub
		return processDef.getName();
	}
		
	@Override
	public void setState(ProcessState state) {
		if(null == mainBehaviour)
			this.state = state;
		else
			mainBehaviour.setState(state);
		
	}
	
	/**
	 * Transition functions
	 */
		
	@Override
	public CMLBehaviourSignal caseAStateProcess(AStateProcess node, Context question) throws AnalysisException
	{
		
		CMLBehaviourSignal ret = null;
		//The behavior of this process has not been started yet, so start it and execute the main
		//Behavior in the next execution step
		if(mainBehaviour == null)
		{
			
			// TODO Add state, value, etc to the corresponding processValue and
//			for (PDefinition def : node.getDefinitionParagraphs())
//			{
//				def.apply(this.parentInterpreter, question);
//				// question.put(def.getName(), def.getType().g);
//			}
			//ProcessThread pt = new ProcessThread(question, node);

			//Create the context for this process and hand it over to the process behavior 
			Context newContext = new Context(node.getLocation(), "Process Context :" + name(), question);

			//Create the name for the behavior and start it 
			LexNameToken mainActionName = new LexNameToken(name().getModule(),
					name().getName() + "@",
					node.getAction().getLocation());

			mainBehaviour = new CMLActionInstance(node.getAction(),newContext,mainActionName);
			mainBehaviour.start(supervisor());
			pushNext(node, question);
			ret = CMLBehaviourSignal.EXEC_SUCCESS; 
		}
		else
		{
			if(!mainBehaviour.finished())
			{
				ret = mainBehaviour.execute(supervisor());
				pushNext(node, question);
			}
			else
			{
				ret = CMLBehaviourSignal.EXEC_SUCCESS; 
			}
		}
				
		return ret;
	}
	
	@Override
	public CMLBehaviourSignal caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException {

		//first push the right process
		//pushNext(node.getRight(), question);
		//then push the left process so it will execute first
		//pushNext(node.getLeft(), question);
		
		
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CMLBehaviourSignal caseAReferenceProcess(AReferenceProcess node,
			Context question) throws AnalysisException {

		//TODO add decls to the context
		//ProcessValue value = new ProcessValue();
		//ProcessContext processContext = new ProcessContext(node.getLocation(), "", 
		//		question.getGlobal(), value);
		Context newContext = new Context(node.getLocation(), "Child Process Context", question.getGlobal());

		AProcessDefinition processDef = node.getProcessDefinition();

		CMLProcessInstance childProcess = new CMLProcessInstance(processDef, this, newContext);

		this.children.add(childProcess);


		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
}
