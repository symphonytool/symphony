package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

class VanillaCmlInterpreter extends AbstractCmlInterpreter
{
	static
	{
	    BasicSchedulableThread.setInitialThread(new InitThread(Thread.currentThread()));
	}

	/**
	 * 
	 */
	private static final long          serialVersionUID = 6664128061930795395L;
	protected List<PSource>            sourceForest;
	protected Context                  globalContext;
	protected String 				   defaultName      = null;	
	protected AProcessDefinition       topProcess;
	protected CmlBehaviour	   		   runningTopProcess = null;	
	
	/**
	 * Construct a CmlInterpreter with a list of PSources. These source may
	 * refer to each other.
	 * 
	 * 
	 * @param cmlSources
	 *          - Source containing CML Paragraphs for type checking.
	 */
	public VanillaCmlInterpreter(List<PSource> cmlSources) throws InterpreterException
	{
		this.sourceForest = cmlSources;
		initialize();
	}

	/**
	 * Construct a CmlTypeInterpreter with the intension of checking a single
	 * source.
	 * 
	 * @param singleSource
	 */
	public VanillaCmlInterpreter(PSource singleSource) throws InterpreterException
	{
		this.sourceForest = new LinkedList<PSource>();
		this.sourceForest.add(singleSource);
		initialize();
	}

	/**
	 * Initializes the interpreter by making a global context and setting the 
	 * last defined process as the top process
	 * @throws InterpreterException
	 */
	protected void initialize() throws InterpreterException
	{
		GlobalEnvironmentBuilder envBuilder = new GlobalEnvironmentBuilder(sourceForest);

		//Build the global context
		globalContext = envBuilder.getGlobalContext();
		//set the last defined process as the top process
		//FIXME When there are multiple files there are no way to determine which one it will be!
		topProcess = envBuilder.getLastDefinedProcess();
	}

	@Override
	public String getDefaultName()
	{
		return defaultName;
	}

	@Override
	public void setDefaultName(String name) 
	{
		defaultName = name;
	}

	@Override
	public Value execute(CmlSupervisorEnvironment sve) throws AnalysisException
	{
		if(null == sve)
			throw new NullPointerException("The supervisor must not be set to null in the cml scheduler");
		
		currentSupervisor = sve; 

		//Find and initialize the top process value
		ProcessObjectValue pov = InitializeTopProcess();
		//Create the initial context with the global definitions
		Context topContext = getInitialContext(null);
		//Create a CmlBehaviour for the top process
		runningTopProcess = new ConcreteCmlBehaviour(topProcess.getProcess(), topContext, topProcess.getName());
		currentSupervisor.addPupil(runningTopProcess);
		
		//Fire the interpreter running event before we start
		setNewState(CmlInterpreterState.RUNNING);
		//start the execution of the top process
		try{
			executeTopProcess(runningTopProcess);
		}
		catch(Exception ex)
		{
			setNewState(CmlInterpreterState.FAILED);
			throw ex;
		}
		
		
		//Finally we return the top process value
		return pov;
	}
	
	/**
	 * Finds and initializes the top process
	 * @return 
	 * @throws AnalysisException
	 */
	private ProcessObjectValue InitializeTopProcess() throws AnalysisException
	{
		if(defaultName != null && !defaultName.equals(""))
		{
			LexNameToken name = new LexNameToken("",getDefaultName(),null);
			ProcessObjectValue pov = (ProcessObjectValue)globalContext.check(name);
			
			if (pov == null)
				throw new AnalysisException("No process identified by '"
						+ getDefaultName() + "' exists");

			topProcess = pov.getProcessDefinition();
			
			return pov;
		}
		
		return null;
	}
	
	/**
	 * Main loop for executing the top process
	 * @param topProcess
	 * @throws AnalysisException
	 */
	private void executeTopProcess(CmlBehaviour topProcess) throws AnalysisException
	{
		//continue until the top process is not finished and not deadlocked
		while(!topProcess.finished() && !topProcess.deadlocked())
		{
			//inspect the top process to get the next possible trace element
			CmlAlphabet topAlphabet = topProcess.inspect();
			//expand what's possible in the alphabet
			CmlAlphabet availableEvents = topAlphabet.expandAlphabet();
			
			CmlRuntime.logger().fine("Waiting for environment on : " + availableEvents.getAllEvents());
			
			for(CmlTransition event : availableEvents.getAllEvents())
			{
				//TODO this should be handled differently
				Context context = event.getEventSources().iterator().next().getNextState().second;

				String state;

				if(context.getSelf() != null)
					state = context.getSelf().toString();
				else if (context.outer != null)
					state = context.getRoot().toString();
				else
					state = context.toString();

				CmlRuntime.logger().fine("State for "+event+" : " +  state);
			}

			//Let the given decision function select one of the observable events 
			CmlTransition selectedEvent = null;
			try {
				selectedEvent = currentSupervisor.decisionFunction().select(availableEvents);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			//Set the selected event on the supervisor
			currentSupervisor.setSelectedTransition(selectedEvent);
			
			topProcess.execute(currentSupervisor);
			
			CmlTrace trace = topProcess.getTraceModel();
			
			if(trace.getLastEvent() instanceof ObservableEvent)
			{
				CmlRuntime.logger().fine("----------------observable step by '"+ topProcess +"'----------------");
				CmlRuntime.logger().fine("Observable trace of '"+topProcess+"': " + trace.getObservableTrace());
				
			}
			else 
			{
				CmlRuntime.logger().fine("----------------Silent step by '"+ topProcess +"'----------------");
				CmlRuntime.logger().fine("Trace of '"+topProcess+"': " + trace);
			}
			CmlRuntime.logger().fine("Eval. Status={ " + topProcess.nextStepToString() + " }");
		}
		
		if(topProcess.deadlocked())
			setNewState(CmlInterpreterState.DEADLOCKED);
		else
			setNewState(CmlInterpreterState.TERMINATED);
	}

	@Override
	public InterpreterStatus getStatus()
	{
		return new InterpreterStatus(runningTopProcess,getCurrentState());
	}

	@Override
	public Context getInitialContext(LexLocation location) {
		return globalContext;
	}

	// ---------------------------------------
	// Static stuff for running the Interpreter from Eclipse
	// ---------------------------------------

	private static void runOnFile(File f) throws IOException, InterpreterException
	{
		AFileSource source = new AFileSource();
		source.setName(f.getName());
		source.setFile(f);
		
		// Run the parser and lexer and report errors if any
		if (!CmlParserUtil.parseSource(source))
		{
			System.out.println("Failed to parse: " + source.toString());
			return;
		}

		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		
		// Type check
		CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
				Arrays.asList(new PSource[] { source }),issueHandler);

		
		// Print result and report errors if any
		if (!cmlTC.typeCheck())
		{
			System.out.println("Failed to type check: " + source.toString());
			System.out.println(issueHandler.getTypeErrors());
			return;
		}

		// interpret
		VanillaCmlInterpreter cmlInterp = new VanillaCmlInterpreter(source);
		try
		{
			CmlSupervisorEnvironment sve = 
					VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new ConsoleSelectionStrategy());
			//CmlSupervisorEnvironment sve = 
			//				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new RandomSelectionStrategy());

			CmlRuntime.logger().setLevel(Level.FINEST);
			cmlInterp.onStatusChanged().registerObserver(new CmlInterpreterStatusObserver() {
				
				@Override
				public void onStatusChanged(Object source, InterpreterStatusEvent event) {
					System.out.println("Simulator status event : " + event.getStatus());
					
				}
			});
			
			cmlInterp.execute(sve);
		} catch (Exception ex)
		{
			System.out.println("Failed to interpret: " + source.toString());
			System.out.println("With Error : ");
			ex.printStackTrace();
			return;

		}

		// Report success
		System.out.println("The given CML Program is done simulating.");
	}

	public static void main(String[] args) throws IOException, InterpreterException
	{
		File cml_example = new File(
				"src/test/resources/action/replicated/replicated-interleaving.cml");
		runOnFile(cml_example);

	}

	@Override
	public CmlBehaviour getTopLevelCmlBehaviour() {
		
		return runningTopProcess;
	}
}
