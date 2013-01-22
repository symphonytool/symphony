package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ASkipProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessTraceObserver;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.runtime.ProcessContext;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.values.ProcessObjectValue;
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
public class CmlProcess extends AbstractBehaviourThread<PProcess>  implements CmlProcessStateObserver, CmlProcessTraceObserver
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4707044690749902194L;
	private AProcessDefinition processDef;
	private CmlAction mainBehaviour = null;
	
	public CmlProcess(AProcessDefinition processDef, CmlProcess parent, CmlContext outer)
	{
		super(parent,processDef.getName());
		this.processDef = processDef;
		//find the self object, this should be no problem since the 
		//typechecker should make sure this is a process.
		ProcessObjectValue self = outer.<ProcessObjectValue>lookup(processDef.getName());
		ProcessContext context = new ProcessContext(processDef.getLocation(), "Process "+ processDef.getName() +" top context", outer, self);
	
		//push the initial execution state on the stack
		pushNext(processDef.getProcess(), context);
	}
	
	public CmlProcess(PProcess startProcess,LexNameToken name, CmlProcess parent, CmlContext outer)
	{
		super(parent,name);
		ProcessContext context = new ProcessContext(startProcess.getLocation(), "", outer, null);
		this.processDef = null;
		//this.prcEval = new ProcessEvaluatorNew(processDef.getProcess(),context,this);
		pushNext(startProcess, context);
	}
	
	@Override
	public void start(CmlSupervisorEnvironment env) {
		this.env = env;
		state = CmlProcessState.RUNNABLE;
		env.addPupil(this);
	}

	@Override
	public CmlAlphabet inspect() 
	{
		try{

			CmlAlphabet alpha = null;

			//If this process is a state process it has its behaviour defined in the mainBehaviour action part
			//Therefore when this process is inspected this is forwarded to the mainBehaviour
			if(null != mainBehaviour)
				alpha = mainBehaviour.inspect();
			else
			{
				if(hasNext())
				{
					alpha = nextState().first.apply(alphabetInspectionVisitor,nextState().second);
				}
				else
					alpha = new CmlAlphabet();
			}

			return alpha;
			
		}catch(AnalysisException ex)
		{
			CmlRuntime.logger().throwing(this.toString(),"inspect()", ex);
			throw new InterpreterRuntimeException(InterpretationErrorMessages.FATAL_ERROR.customizeMessage(),ex);
		}
	}
	
	@Override
	public CmlProcessState getState() {
		//If the main behaviour is null then this process is either not started or it is a composition process without
		//behavior defined through other defined processes
		if(null == mainBehaviour)
			return this.state;
		else
			return mainBehaviour.getState();
	}

	@Override
	protected void setState(CmlProcessState state) {
		
		if(getState() != state)
		{

			if(null == mainBehaviour)
			{
				CmlProcessStateEvent ev = new CmlProcessStateEvent(this, this.state, state);
				this.state = state;
				notifyOnStateChange(ev);
			}
			else
				mainBehaviour.setState(state);
		}
	}
	
	@Override
	public String nextStepToString() {
		
		String value = null;
		
		if(hasNext())
		{
			if(mainBehaviour == null)
				value = nextState().first.toString();
			else
				value = nextState().first.toString() + mainBehaviour.nextStepToString();
		}
		else
			value = "Finished";


		return value;
	}
	
	@Override
	public String toString() {

		return name().toString();
	}

	/**
	 * CmlProcessObserver interface methods
	 */
	
	@Override
	public void onStateChange(CmlProcessStateEvent stateEvent) {
		
		//special case for the action behaviour of a process
		if(stateEvent.getSource() == this.mainBehaviour)
		{
			//this can occur when the process is evaluating a Sequential composition process
			if(hasNext() && this.mainBehaviour.finished())
			{
				//if we are in a sequential composition and the main behaviour is finished
				//then it must be set to nothing since this is only set when it an ActionProcess
				this.mainBehaviour = null;
				setState(CmlProcessState.RUNNABLE);
			}
			else	
				//this means the main behaviour has changed state, this must be redirected as the process state
				notifyOnStateChange(new CmlProcessStateEvent(this, stateEvent.getFrom(), stateEvent.getTo()));
		}
		else
			super.onStateChange(stateEvent);
	}
	
	/**
	 * This will provide the traces from all the child actions
	 */
	@Override
	public void onTraceChange(TraceEvent traceEvent) {

		//To prevent the trace to get updated twice from the mainThread, we need to check that
		//the event did not originate from the mainThread since this would already be registered
		if(traceEvent.getSource() == this.mainBehaviour  && traceEvent.isRedirectedEvent())
		{
			this.trace.addEvent(traceEvent.getEvent());
			notifyOnTraceChange(TraceEvent.createRedirectedEvent(this, traceEvent));
		}
	}
	
	/**
	 * Create the main behaviour from a given AActionProcess node and context
	 * @param node
	 * @param question
	 */
	private void createMainBehaviour(AActionProcess node, CmlContext question)
	{
		LexNameToken mainActionName = new LexNameToken(name().getModule(),
				name().getName() + "@",
				node.getAction().getLocation());

		mainBehaviour = new CmlAction(node.getAction(),question,mainActionName);
		//register for state and trace changes. This enables the process to reflect it 
		//as its own behaviour.
		mainBehaviour.onStateChanged().registerObserver(this);
		mainBehaviour.onTraceChanged().registerObserver(this);
		mainBehaviour.start(supervisor());
	}
	
	/**
	 * Transition functions
	 */
		
	@Override
	public CmlBehaviourSignal defaultPProcess(PProcess node, CmlContext question)
			throws AnalysisException {
		
		throw new InterpreterRuntimeException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
		
	}
	
	@Override
	public CmlBehaviourSignal caseASkipProcess(ASkipProcess node, CmlContext question)
			throws AnalysisException {

		//if hasNext() is true then Skip is in sequential composition with next
		if(!hasNext())
			setState(CmlProcessState.FINISHED);
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CmlBehaviourSignal caseAActionProcess(AActionProcess node, CmlContext question) throws AnalysisException
	{
		CmlBehaviourSignal ret = null;
		//If mainBehaviour is null then the behaviour of this process has not been started yet, 
		//so start it and execute the main behaviour in the next execution step.
		if(mainBehaviour == null)
		{
			//Add and evaluate the definitions to the current process context
			for (PDefinition def : node.getDefinitionParagraphs())
			{
				def.apply(cmlEvaluator, question);
			}

			//Create the name for the action behaviour and start it 
			createMainBehaviour(node,question);
			
			//push this node onto the execution stack again since this should execute
			//the action behaviour until it terminates
			pushNext(node, question);
			ret = CmlBehaviourSignal.EXEC_SUCCESS; 
		}
		else
		{
			if(!mainBehaviour.finished())
			{
				ret = mainBehaviour.execute(supervisor());
				if(mainBehaviour != null)
					pushNext(node, question);
			}
			else
			{
				ret = CmlBehaviourSignal.EXEC_SUCCESS; 
			}
		}
				
		return ret;
	}
	
	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2. 
	 * (Even though this is a process I assume something similar will happen)
	 */
	@Override
	public CmlBehaviourSignal caseAReferenceProcess(AReferenceProcess node,
			CmlContext question) throws AnalysisException {

		//initials this process with the global context since this should see any of creators members
//		CmlProcess childProcess = new CmlProcess(node.getProcessDefinition(), this, question.getGlobal());
//		this.children.add(childProcess);
//		return CmlBehaviourSignal.EXEC_SUCCESS;
		
		ProcessObjectValue processValue = (ProcessObjectValue)question.lookup(node.getProcessName());
		name = new LexNameToken(name.module,name.getIdentifier().getName() + " = " + processValue.getProcessDefinition().getName().getSimpleName(),name.location);
		pushNext( processValue.getProcessDefinition().getProcess(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
		
	}
	
	
	@Override
	public CmlBehaviourSignal caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, CmlContext question)
			throws AnalysisException {
		
		//First push right and then left, so that left get executed first
		pushNext(node.getRight(), question);
		pushNext(node.getLeft(), question);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * There are no actual transition rule for this. The rule for interleaving action is that they evolve 
	 * into Skip. However, this will just terminate successfully when all its children terminates successfully.
	 */
	@Override
	public CmlBehaviourSignal caseAInterleavingProcess(
			AInterleavingProcess node, CmlContext question)
			throws AnalysisException {
		
		//TODO: This only implements the "A ||| B (no state)" and not "A [|| ns1 | ns2 ||] B"
		CmlBehaviourSignal result = null;

		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!hasChildren()){
			result = caseParallelBegin(node,question);
			//We push the current state, since this process will control the child processes created by it
			pushNext(node, question);

		}
		//At least one child is not finished and waiting for event, this will invoke the Parallel Non-sync 
		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(this))
		{
			result = caseParallelSync();

			//We push the current state, 
			pushNext(node, question);

		}
		//the process has children and must now handle either termination or event sync
		else if (CmlBehaviourThreadUtility.isAllChildrenFinished(this))
		{
			removeTheChildren();
			
			pushNext(new ASkipProcess(), question);
			
			result = CmlBehaviourSignal.EXEC_SUCCESS;
		}
		//else if ()

		return result;
	}
	
	private CmlBehaviourSignal caseParallelBegin(PProcess node, CmlContext question)
	{
		PProcess left = null;
		PProcess right = null;
		
		if(node instanceof AInterleavingProcess)
		{
			left = ((AInterleavingProcess) node).getLeft();
			right = ((AInterleavingProcess) node).getRight();
		}
		
		if(left == null || right == null)
			throw new InterpreterRuntimeException(
					InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
		
		//TODO: create a local copy of the question state for each of the actions
		CmlProcess leftInstance = 
				new CmlProcess(left,new LexNameToken(name.module,name.getIdentifier().getName() + "|||" ,left.getLocation()),this, question);
		
		CmlProcess rightInstance = 
				new CmlProcess(right,new LexNameToken(name.module,name.getIdentifier().getName() + "|||" ,right.getLocation()),this, question);
		
		return caseParallelBeginGeneral(leftInstance,rightInstance,question);
	}
	
	@Override
	public CmlBehaviourSignal caseAInternalChoiceProcess(
			AInternalChoiceProcess node, CmlContext question)
			throws AnalysisException {
		
		//For now we always pick the left action
		pushNext(node.getLeft(), question);
				
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
}
