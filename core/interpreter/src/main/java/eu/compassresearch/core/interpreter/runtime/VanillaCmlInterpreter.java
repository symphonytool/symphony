package eu.compassresearch.core.interpreter.runtime;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;
import org.overture.typechecker.Environment;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.api.NoProcessFoundException;
import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.DefaultSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.InstantiatedProcess;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.scheduler.CmlScheduler;
import eu.compassresearch.core.interpreter.values.ProcessValueOld;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class VanillaCmlInterpreter extends AbstractCmlInterpreter
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
    private CmlScheduler               cmlScheduler     = new CmlScheduler();
    
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

    protected void initialize() throws NoProcessFoundException
    {
    	EnvironmentBuilder envBuilder = new EnvironmentBuilder(sourceForest);

    	env = envBuilder.getGlobalEnvironment();
    	globalContext = envBuilder.getGlobalContext();
    	if(defaultName != null)
    	{
    		LexNameToken name = new LexNameToken("Default",getDefaultName(),null);
            AProcessDefinition processDef = (AProcessDefinition)env.findName(name, NameScope.GLOBAL);
            
            if (processDef == null)
              throw new NoProcessFoundException("No process identified by '"
                  + getDefaultName() + "' exists");
    		
            topProcess = processDef;
    	}
    	else
    		topProcess = envBuilder.getLastDefinedProcess();
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
    public void setDefaultName(String name) throws Exception
    {

    	defaultName = name;
    }

    @Override
    public Value execute() throws AnalysisException
      {
        
        Environment env = getGlobalEnvironment();
        
        // if(getDefaultName() == null)
        // Find the default process
        //AProcessDefinition processDef = null;
                
        CmlRuntime.setGlobalEnvironment(env);
        // This constructs the runtime process structure from the AST
        //ProcessValueOld pv = (ProcessValueOld) topProcess.getProcess().apply(
        //    this.evalutor, getInitialContext(topProcess.getLocation()));
        // Wrap the top process in an InstantiatedProcess
        //InstantiatedProcess instantProcess = new InstantiatedProcess(
        //		topProcess, pv.getProcess());
        
        // Add the top process to the scheduler and start it
        //cmlScheduler.addProcess(instantProcess);
        //cmlScheduler.start();
        
        CMLSupervisorEnvironment sve = CmlRuntime.getSupervisorEnvironment();
        CMLProcessInstance pi = new CMLProcessInstance(topProcess, null,getInitialContext(null));
        
        pi.start(sve);
        sve.start();
        
        return null;
      }
    
    public String getAnalysisName()
      {
        return "The CML Interpreter";
      }
    
    // ---------------------------------------
    // Static stuff for running the TypeChecker from Eclipse
    // ---------------------------------------
    
    // setting the file on AFileSource allows the CmlParser factory method
    // to create both parser and lexer.
    private static PSource prepareSource(File f)
      {
        if (f == null)
          {
            AInputStreamSource iss = new AInputStreamSource();
            iss.setStream(System.in);
            iss.setOrigin("stdin");
            return iss;
          } else
          {
            AFileSource fs = new AFileSource();
            fs.setName(f.getName());
            fs.setFile(f);
            return fs;
          }
      }
    
    private static void runOnFile(File f) throws IOException, InterpreterException
      {
        // set file name
        PSource source = prepareSource(f);
        
        // Call factory method to build parser and lexer
        CmlParser parser = CmlParser.newParserFromSource(source);
        
        // Run the parser and lexer and report errors if any
        if (!parser.parse())
          {
            System.out.println("Failed to parse: " + source.toString());
            return;
          }
        
        // Type check
        CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
            Arrays.asList(new PSource[] { source }), null);
        
        TypeIssueHandler issueHandler = (TypeIssueHandler)cmlTC;
        
        // Print result and report errors if any
        if (!cmlTC.typeCheck())
          {
            System.out.println("Failed to type check " + source.toString());
            System.out.println(issueHandler.getTypeErrors());
            return;
          }
        
        // interpret
        VanillaCmlInterpreter cmlInterp = new VanillaCmlInterpreter(source);
        try
          {
            // cmlInterp.setDefaultName("A");
            cmlInterp.execute();
          } catch (Exception ex)
          {
            System.out.println("Failed to interpret" + source.toString());
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
            "src/test/resources/action/action-prefix.cml");
        // "src/test/resources/process/GeneralisedParallelismAction.cml");
        runOnFile(cml_example);
        
      }
    
    public InterpreterStatus getStatus()
      {
        return new InterpreterStatus(cmlScheduler.getTrace());
      }

	@Override
	public Context getInitialContext(LexLocation location) {
		return globalContext;
	}
  }
