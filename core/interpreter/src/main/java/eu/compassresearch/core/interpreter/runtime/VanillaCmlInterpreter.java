package eu.compassresearch.core.interpreter.runtime;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.scheduler.InstantiatedProcess;
import eu.compassresearch.core.interpreter.values.ProcessValue;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.Environment;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;

public class VanillaCmlInterpreter extends AbstractCmlInterpreter
  {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 6664128061930795395L;
    private CmlEvaluator      evalutor         = new CmlEvaluator();
    
    /**
     * Construct a CmlInterpreter with a list of PSources. These source may
     * refer to each other.
     * 
     * 
     * @param cmlSources
     *          - Source containing CML Paragraphs for type checking.
     */
    public VanillaCmlInterpreter(List<PSource> cmlSources)
      {
        // initialize();
        this.sourceForest = cmlSources;
      }
    
    @Override
    public Value execute() throws AnalysisException
      {
        
        Environment env = getGlobalEnvironment();
        // Find the default process
        AProcessDefinition processDef = (AProcessDefinition) env
            .lookupName(new LexIdentifierToken(getDefaultName(), false, null));
        
        if (processDef == null)
          throw new AnalysisException("No process identified by '"
              + getDefaultName() + "' exists");
        
        CmlRuntime.setGlobalEnvironment(env);
        // This constructs the runtime process structure from the AST
        ProcessValue pv = (ProcessValue) processDef.getProcess().apply(
            this.evalutor, getInitialContext(processDef.getLocation()));
        // Wrap the top process in an InstantiatedProcess
        InstantiatedProcess instantProcess = new InstantiatedProcess(
            processDef, pv.getProcess());
        
        // Add the top process to the scheduler and start it
        CmlRuntime.getCmlScheduler().addProcess(instantProcess);
        CmlRuntime.getCmlScheduler().start();
        
        return null;
      }
    
    /**
     * Construct a CmlTypeChecker with the intension of checking a single
     * source.
     * 
     * @param singleSource
     */
    public VanillaCmlInterpreter(PSource singleSource)
      {
        // initialize();
        this.sourceForest = new LinkedList<PSource>();
        this.sourceForest.add(singleSource);
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
    
    private static void runOnFile(File f) throws IOException
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
        
        // Print result and report errors if any
        if (!cmlTC.typeCheck())
          {
            System.out.println("Failed to type check" + source.toString());
            return;
          }
        
        // interpret
        VanillaCmlInterpreter cmlInterp = new VanillaCmlInterpreter(source);
        try
          {
            cmlInterp.setDefaultName("C");
            cmlInterp.execute();
          } catch (Exception ex)
          {
            System.out.println("Failed to interpret" + source.toString());
            System.out.println("With Error : ");
            ex.printStackTrace();
            return;
            
          }
        
        // Report success
        System.out.println("The given CML Program is type checked.");
      }
    
    public static void main(String[] args) throws IOException
      {
        
        File cml_example = new File(
            "src/test/resources/firstInterpreterTest.cml");
        runOnFile(cml_example);
        // File cml_examples = new File("../../docs/cml-examples");
        // int failures = 0;
        // int successes = 0;
        // runOnFile(null);
        
        // if (cml_examples.isDirectory())
        // {
        // for (File example : cml_examples.listFiles())
        // {
        // System.out.print("Typechecking example: " + example.getName()
        // + " \t\t...: ");
        // System.out.flush();
        // try
        // {
        // runOnFile(example);
        // System.out.println("done");
        // successes++;
        // } catch (Exception e)
        // {
        // System.out.println("exception");
        // failures++;
        // }
        // }
        // }
        //
        // System.out.println(successes + " was successful, " + failures
        // + " was failures.");
        
      }
  }
