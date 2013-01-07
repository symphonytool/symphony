package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.CPUValue;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.eval.AlphabetInspector;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessTraceObserver;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.runtime.ProcessContext;
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

	private AProcessDefinition processDef;
	private CmlAction mainBehaviour = null;
	private Context globalContext;
	
	public CmlProcess(AProcessDefinition processDef, CmlProcess parent, Context globalContext)
	{
		super(parent);
		this.globalContext = globalContext; 
		ProcessContext context = new ProcessContext(processDef.getLocation(), "", globalContext, null);
		this.processDef = processDef;
		//this.prcEval = new ProcessEvaluatorNew(processDef.getProcess(),context,this);
		pushNext(processDef.getProcess(), context);
	}
	
	public CmlProcess(PProcess startProcess, CmlProcess parent, Context globalContext)
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
					alpha = nextState().first.apply(alphabetInspectionVisitor,nextState().second);
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
	public LexNameToken name() {
		return processDef.getName();
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
			notifyOnStateChange(new CmlProcessStateEvent(this, stateEvent.getFrom(), stateEvent.getTo()));
		}
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
			
			//Create the context for this process and hand it over to the process behavior 
			Context newContext = new Context(node.getLocation(), "Process Context :" + name(), question);
			//this needs to be set when we evaluates the expressions
			newContext.setThreadState(null, CPUValue.vCPU);
			
			for (PDefinition def : node.getDefinitionParagraphs())
			{
				def.apply(cmlEvaluator, newContext);
				// question.put(def.getName(), def.getType().g);
			}

			//Create the name for the action behavior and start it 
			LexNameToken mainActionName = new LexNameToken(name().getModule(),
					name().getName() + "@",
					node.getAction().getLocation());

			mainBehaviour = new CmlAction(node.getAction(),newContext,mainActionName);
			mainBehaviour.onStateChanged().registerObserver(this);
			mainBehaviour.onTraceChanged().registerObserver(this);
			mainBehaviour.start(supervisor());
			//push this node onto the execution stack again since this should execute
			//the action behavoir until it terminates
			pushNext(node, newContext);
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

		CmlProcess childProcess = new CmlProcess(processDef, this, newContext);

		this.children.add(childProcess);


		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
}
