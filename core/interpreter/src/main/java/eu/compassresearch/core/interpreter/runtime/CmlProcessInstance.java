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
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ProcessState;
import eu.compassresearch.core.interpreter.eval.AlphabetInspectionVisitor;
import eu.compassresearch.core.interpreter.util.Pair;
import eu.compassresearch.core.interpreter.values.ProcessValue;
/**
 *  This class represents a running CML Process. It represents a specific node as specified in D23.2 section 7.4.2,
 *  where a node is specified as a tuple (w,s,a) where w is the set of variables, s is the state values and a is the 
 *  current action.
 *  w and s are stored in the current Context object and a is represented by storing the next action AST node to be executed.
 * 
 * 	The possible transitions are handled in the visitor case methods.
 * 
 *  Therefore this Class should fully consistent with the operational semantics described in D23.2 chapter 7.
 * 
 * 
 * @author akm
 *
 */
public class CmlProcessInstance extends AbstractInstance<PProcess>  {

	private AProcessDefinition processDef;
	private CmlActionInstance mainBehaviour = null;
	private AlphabetInspectionVisitor alphabetInspectionVisitor = new AlphabetInspectionVisitor();
	private Context globalContext;
	
	public CmlProcessInstance(AProcessDefinition processDef, CmlProcess parent, Context globalContext)
	{
		super(parent);
		this.globalContext = globalContext; 
		ProcessContext context = new ProcessContext(processDef.getLocation(), "", globalContext, null);
		this.processDef = processDef;
		//this.prcEval = new ProcessEvaluatorNew(processDef.getProcess(),context,this);
		pushNext(processDef.getProcess(), context);
	}
	
	public CmlProcessInstance(PProcess startProcess, CmlProcess parent, Context globalContext)
	{
		super(parent);
		this.globalContext = globalContext; 
		ProcessContext context = new ProcessContext(processDef.getLocation(), "", globalContext, null);
		this.processDef = null;
		//this.prcEval = new ProcessEvaluatorNew(processDef.getProcess(),context,this);
		pushNext(startProcess, context);
	}
	
	@Override
	public void start(CmlSupervisorEnvironment env) {
		this.env = env;
		state = ProcessState.RUNNABLE;
		env.addPupil(this);
	}

	@Override
	public CmlAlphabet inspect() throws AnalysisException{
		
		CmlAlphabet alpha = null;
		
		if(null != mainBehaviour)
			alpha = mainBehaviour.inspect();
		else
		{
			alpha = nextState().first.apply(alphabetInspectionVisitor,nextState().second);
		}
		
		return alpha;
	}
	
	@Override
	public LexNameToken name() {
		return processDef.getName();
	}

	@Override
	public ProcessState getState() {
		//If the main behaviour is null then this process is either not started or it is a composition process without
		//behavior defined through other defined processes
		if(null == mainBehaviour)
			return this.state;
		else
			return mainBehaviour.getState();
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
	public CmlBehaviourSignal caseAStateProcess(AStateProcess node, Context question) throws AnalysisException
	{
		
		CmlBehaviourSignal ret = null;
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

			mainBehaviour = new CmlActionInstance(node.getAction(),newContext,mainActionName);
			mainBehaviour.start(supervisor());
			pushNext(node, question);
			ret = CmlBehaviourSignal.EXEC_SUCCESS; 
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
				ret = CmlBehaviourSignal.EXEC_SUCCESS; 
			}
		}
				
		return ret;
	}
	
	@Override
	public CmlBehaviourSignal caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException {

		//first push the right process
		//pushNext(node.getRight(), question);
		//then push the left process so it will execute first
		//pushNext(node.getLeft(), question);
		
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2. 
	 * (Even though this is a process I assume something similar will happen)
	 */
	@Override
	public CmlBehaviourSignal caseAReferenceProcess(AReferenceProcess node,
			Context question) throws AnalysisException {

		//TODO add decls to the context
		//ProcessValue value = new ProcessValue();
		//ProcessContext processContext = new ProcessContext(node.getLocation(), "", 
		//		question.getGlobal(), value);
		Context newContext = new Context(node.getLocation(), "Child Process Context", question.getGlobal());

		AProcessDefinition processDef = node.getProcessDefinition();

		CmlProcessInstance childProcess = new CmlProcessInstance(processDef, this, newContext);

		this.children.add(childProcess);


		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public String toString() {
		
		String value = null;
		
		if(hasNext())
		{
			if(mainBehaviour == null)
				value = nextState().first.toString();
			else
				value = nextState().first.toString() + mainBehaviour.toString();
		}
		else
			value = "Finished";


		return value;
	}
}
