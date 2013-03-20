package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.CmlInterpreterStatus;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ConsoleSelectionStrategy;
import eu.compassresearch.core.interpreter.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.scheduler.CmlScheduler;
import eu.compassresearch.core.interpreter.scheduler.FCFSPolicy;
import eu.compassresearch.core.interpreter.util.CmlParserUtil;
import eu.compassresearch.core.interpreter.util.GlobalEnvironmentBuilder;
import eu.compassresearch.core.interpreter.values.ProcessObjectValue;
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
	protected CmlScheduler                cmlScheduler     = null;

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

	protected void initialize() throws InterpreterException
	{
		GlobalEnvironmentBuilder envBuilder = new GlobalEnvironmentBuilder(sourceForest);

		globalContext = envBuilder.getGlobalContext();
		topProcess = envBuilder.getLastDefinedProcess();
	}
	
	private ProcessObjectValue InitializeTopProcess() throws InterpreterException 
	{
		if(defaultName != null && !defaultName.equals(""))
		{
			LexNameToken name = new LexNameToken("",getDefaultName(),null);
			ProcessObjectValue pov = (ProcessObjectValue)globalContext.check(name);
			
			if (pov == null)
				throw new InterpreterException("No process identified by '"
						+ getDefaultName() + "' exists");

			topProcess = pov.getProcessDefinition();
			
			return pov;
		}
		
		return null;
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
	public Value execute(CmlSupervisorEnvironment sve, CmlScheduler scheduler) throws InterpreterException
	{
		cmlScheduler = scheduler;
		currentSupervisor = sve; 

		//Find and initialize the top process value
		ProcessObjectValue pov = InitializeTopProcess();
		//Create the initial context with the global definitions
		Context topContext = getInitialContext(null);
		//Create a CmlBehaviourThread for the top process
		CmlBehaviourThread pi = VanillaInterpreterFactory.newCmlBehaviourThread(topProcess.getProcess(), topContext, topProcess.getName());
		pi.start(currentSupervisor);
		//Fire the interpreter running event before we start
		statusEventHandler.fireEvent(new InterpreterStatusEvent(this, CmlInterpreterStatus.RUNNING));
		cmlScheduler.start(currentSupervisor);
		
		if(cmlScheduler.isDeadlocked())
			statusEventHandler.fireEvent(new InterpreterStatusEvent(this, CmlInterpreterStatus.DEADLOCKED));
		else
			statusEventHandler.fireEvent(new InterpreterStatusEvent(this, CmlInterpreterStatus.TERMINATED));
		
		//Finally we return the top process value
		return pov;
	}

	public String getAnalysisName()
	{
		return "The CML Interpreter";
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
			CmlScheduler scheduler = VanillaInterpreterFactory.newScheduler(new FCFSPolicy());
			//CmlSupervisorEnvironment sve = 
			//		VanillaInterpreterFactory.newCmlSupervisorEnvironment(new RandomSelectionStrategy(), scheduler);
			CmlSupervisorEnvironment sve = 
							VanillaInterpreterFactory.newCmlSupervisorEnvironment(new ConsoleSelectionStrategy(), scheduler);

			CmlRuntime.logger().setLevel(Level.FINEST);
			cmlInterp.execute(sve,scheduler);
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
				"src/test/resources/process/process-interleaving-state.cml");
		//"/home/akm/runtime-COMPASS_configuration/test/test.cml");
		runOnFile(cml_example);

	}

	public InterpreterStatus getStatus()
	{
		return new InterpreterStatus(cmlScheduler.getAllProcesses());
	}

	@Override
	public Context getInitialContext(LexLocation location) {
		return globalContext;
	}
}
