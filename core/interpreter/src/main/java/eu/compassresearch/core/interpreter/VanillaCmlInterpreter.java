package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;
import org.overture.typechecker.Environment;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.CmlInterpreterStatus;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.scheduler.CmlScheduler;
import eu.compassresearch.core.interpreter.scheduler.FCFSPolicy;
import eu.compassresearch.core.interpreter.scheduler.Scheduler;
import eu.compassresearch.core.interpreter.util.CmlUtil;
import eu.compassresearch.core.interpreter.util.EnvironmentBuilder;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

class VanillaCmlInterpreter extends AbstractCmlInterpreter
{

	/**
	 * 
	 */
	private static final long          serialVersionUID = 6664128061930795395L;
	private CmlEvaluator               evalutor         = new CmlEvaluator();
	protected List<PSource>            sourceForest;
	protected Environment 			   env;
	protected Context                  globalContext;
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
		EnvironmentBuilder envBuilder = new EnvironmentBuilder(sourceForest);

		env = envBuilder.getGlobalEnvironment();
		globalContext = envBuilder.getGlobalContext();
		topProcess = envBuilder.getLastDefinedProcess();
	}
	
	private void InitializeTopProcess() throws InterpreterException 
	{
		if(defaultName != null && !defaultName.equals(""))
		{
			LexNameToken name = new LexNameToken("",getDefaultName(),null);
			AProcessDefinition processDef = (AProcessDefinition)env.findName(name, NameScope.GLOBAL);

			if (processDef == null)
				throw new InterpreterException("No process identified by '"
						+ getDefaultName() + "' exists");

			topProcess = processDef;
		}
	}
	

	@Override
	public Environment getGlobalEnvironment()
	{
		return env;
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

//	@Override
//	public Value execute() throws InterpreterException
//	{
//		return execute(new RandomSelectionStrategy());
//	}

	@Override
	public Value execute(CmlSupervisorEnvironment sve, Scheduler scheduler) throws InterpreterException
	{
		InitializeTopProcess();
		Environment env = getGlobalEnvironment();
		CmlRuntime.setGlobalEnvironment(env);

		cmlScheduler = scheduler;//VanillaInterpreterFactory.newScheduler(new FCFSPolicy());
		
		currentSupervisor = sve; //VanillaInterpreterFactory.newCmlSupervisorEnvironment(selectionStrategy,cmlScheduler);
		cmlScheduler.setCmlSupervisorEnvironment(currentSupervisor);
		
		CmlProcess pi = new CmlProcess(topProcess, null,getInitialContext(null));

		pi.start(currentSupervisor);
		try {
			statusEventHandler.fireEvent(new InterpreterStatusEvent(this, CmlInterpreterStatus.RUNNING));
			cmlScheduler.start();
		} catch (AnalysisException e) {
			throw new InterpreterException("Yes YES",e);
		}

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
			CmlSupervisorEnvironment sve = 
					VanillaInterpreterFactory.newCmlSupervisorEnvironment(new RandomSelectionStrategy(), scheduler);
			
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
				"src/test/resources/action/action-generalised-parallelism-no-state-simple.cml");
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
	public Context getInitialContext(LexLocation location) {
		return globalContext;
	}
}
