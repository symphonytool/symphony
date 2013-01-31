package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.interpreter.values.Value;
import org.overture.typechecker.Environment;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.CmlInterpreterStatus;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.cml.ConcreteBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ConsoleSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.runtime.ProcessContext;
import eu.compassresearch.core.interpreter.scheduler.FCFSPolicy;
import eu.compassresearch.core.interpreter.scheduler.Scheduler;
import eu.compassresearch.core.interpreter.util.CmlUtil;
import eu.compassresearch.core.interpreter.util.GlobalEnvironmentBuilder;
import eu.compassresearch.core.interpreter.values.ProcessObjectValue;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

class VanillaCmlInterpreter extends AbstractCmlInterpreter
{

	/**
	 * 
	 */
	private static final long          serialVersionUID = 6664128061930795395L;
	protected List<PSource>            sourceForest;
	protected CmlContext               globalContext;
	protected String 				   defaultName      = null;	
	protected AProcessDefinition       topProcess;
	protected Scheduler                cmlScheduler     = null;

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
	
	private void InitializeTopProcess() throws InterpreterException 
	{
		if(defaultName != null && !defaultName.equals(""))
		{
			LexNameToken name = new LexNameToken("",getDefaultName(),null);
			ProcessObjectValue pov = (ProcessObjectValue)globalContext.check(name);
			
			if (pov == null)
				throw new InterpreterException("No process identified by '"
						+ getDefaultName() + "' exists");

			topProcess = pov.getProcessDefinition();
		}
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
	public Value execute(CmlSupervisorEnvironment sve, Scheduler scheduler) throws InterpreterException
	{
		InitializeTopProcess();

		cmlScheduler = scheduler;
		
		currentSupervisor = sve; 
		cmlScheduler.setCmlSupervisorEnvironment(currentSupervisor);
		
		CmlContext topContext = getInitialContext(null);
		
		ProcessObjectValue self = topContext.lookup(topProcess.getName()); 
		
		ProcessContext processContext = new ProcessContext(topProcess.getLocation(), "Top Process context", topContext, self);
		
		ConcreteBehaviourThread pi = new ConcreteBehaviourThread(topProcess.getProcess(), processContext, topProcess.getName());
		pi.start(currentSupervisor);
		statusEventHandler.fireEvent(new InterpreterStatusEvent(this, CmlInterpreterStatus.RUNNING));
		cmlScheduler.start();

		return null;
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
		if (!CmlUtil.parseSource(source))
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
			Scheduler scheduler = VanillaInterpreterFactory.newScheduler(new FCFSPolicy());
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
				"src/test/resources/examples/simpler-register.cml");
		//"/home/akm/runtime-COMPASS_configuration/test/test.cml");
		runOnFile(cml_example);

	}

	public InterpreterStatus getStatus()
	{
		//CmlBehaviourThread topCmlProcessInstance = currentSupervisor.findNamedProcess(topProcess.getName().toString());
		
		//Collect the processInfos
		
		return new InterpreterStatus(cmlScheduler.getAllProcesses());
	}

	@Override
	public CmlContext getInitialContext(LexLocation location) {
		return globalContext;
	}
}
