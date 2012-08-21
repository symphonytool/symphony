package eu.compassresearch.core.interpreter.runtime;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.PType;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.TypeCheckInfo;
import eu.compassresearch.core.typechecker.VanillaCmlTypeChecker;

public class VanillaCmlInterpreter extends AbstractCmlInterpreter {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6664128061930795395L;

		
	
	/**
     * Construct a CmlInterpreter with a list of
     * PSources. These source may refer to each other.
     * 
     * 
     * @param cmlSources
     *          - Source containing CML Paragraphs for type checking.
     */
    public VanillaCmlInterpreter(List<PSource> cmlSources)
      {
//    	initialize();
        this.sourceForest = cmlSources;
      }
    
    /**
     * Construct a CmlTypeChecker with the intension of checking a single
     * source.
     * 
     * @param singleSource
     */
    public VanillaCmlInterpreter(PSource singleSource)
      {
        //initialize();
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
      VanillaCmlTypeChecker cmlTC = new VanillaCmlTypeChecker(source);
      
      // Print result and report errors if any
      if (!cmlTC.typeCheck())
        {
          System.out.println("Failed to type check" + source.toString());
        }
      ;
      
      // Report success
      System.out.println("The given CML Program is type checked.");
    }
  
  public static void main(String[] args) throws IOException
    {
	  
	  File cml_example = new File("src/test/resources/firstInterpreterTest.cml");
	  runOnFile(cml_example);
//      File cml_examples = new File("../../docs/cml-examples");
//      int failures = 0;
//      int successes = 0;
      // runOnFile(null);
      
//      if (cml_examples.isDirectory())
//        {
//          for (File example : cml_examples.listFiles())
//            {
//              System.out.print("Typechecking example: " + example.getName()
//                  + " \t\t...: ");
//              System.out.flush();
//              try
//                {
//                  runOnFile(example);
//                  System.out.println("done");
//                  successes++;
//                } catch (Exception e)
//                {
//                  System.out.println("exception");
//                  failures++;
//                }
//            }
//        }
//      
//      System.out.println(successes + " was successful, " + failures
//          + " was failures.");
      
    }
}
