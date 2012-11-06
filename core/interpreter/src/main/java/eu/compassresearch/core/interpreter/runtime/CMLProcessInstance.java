package eu.compassresearch.core.interpreter.runtime;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.ObjectValue;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CMLAlphabet;
import eu.compassresearch.core.interpreter.cml.CMLBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CMLProcess;
import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ProcessState;
import eu.compassresearch.core.interpreter.eval.AlphabetInspectionVisitor;
import eu.compassresearch.core.interpreter.util.Pair;

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
		executionStack.push(new Pair<PProcess, Context>(processDef.getProcess(), context));
	}
	
	@Override
	public void start(CMLSupervisorEnvironment env) {
		this.env = env;
		state = ProcessState.RUNNABLE;
		env.addPupil(this);
	}

//	@Override
//	public CMLBehaviourSignal execute(CMLSupervisorEnvironment env) throws AnalysisException {
//		this.env = env;
//		
//		//inspect
//		CMLAlphabet alpha = inspect();
//		CMLBehaviourSignal ret = null;
//		
//		//execute if the next is an invisible action
//		if(alpha.containsTau()){
//			state = ProcessState.RUNNING;
//			ret = executeNext();
//			//state = ProcessState.WAIT;
//		}
//		else 
//		{	
//			//if no com is selected yet we set go to wait state
//			if(env.communicationSelected() && alpha.containsCommunication(env.selectedCommunication()))
//			{
//				ret = executeNext();
//			}
//			else 
//			{
//				state = ProcessState.WAIT;
//				ret = CMLBehaviourSignal.EXEC_SUCCESS;
//			}
//		}
//		
//		return ret;
//	}

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
	public CMLBehaviourSignal caseAStateProcess(AStateProcess node, Context question) throws AnalysisException
	{
		
		CMLBehaviourSignal ret = null;
		
		if(mainBehaviour == null)
	    //The main action has not been started yet
		{
			
			// TODO Add state, value, etc to the corresponding processValue and
//			for (PDefinition def : node.getDefinitionParagraphs())
//			{
//				def.apply(this.parentInterpreter, question);
//				// question.put(def.getName(), def.getType().g);
//			}
			//ProcessThread pt = new ProcessThread(question, node);

			
			Context newContext = new Context(node.getLocation(), "caseAStateProcess", question);

			
			LexNameToken mainActionName = new LexNameToken(name().getModule(),
					name().getName() + "@",
					node.getAction().getLocation());

			
			mainBehaviour = new CMLActionInstance(node.getAction(),newContext,mainActionName);
					
			mainBehaviour.start(supervisor());
		}
		
		
		if(!mainBehaviour.finished())
		{
			ret = mainBehaviour.execute(supervisor());
			executionStack.push(new Pair<PProcess, Context>(node, question)); 
		}
		else
		{
			ret = CMLBehaviourSignal.EXEC_SUCCESS; 
		}
		
		return ret;
	}

	@Override
	public void setState(ProcessState state) {
		if(null == mainBehaviour)
			this.state = state;
		else
			mainBehaviour.setState(state);
		
	}

}
