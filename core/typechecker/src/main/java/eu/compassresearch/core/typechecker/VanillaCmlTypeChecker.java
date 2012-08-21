package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.intf.IQuestionAnswer;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.PType;
import eu.compassresearch.core.parser.CmlParser;

@SuppressWarnings("serial")
public class VanillaCmlTypeChecker extends AbstractTypeChecker
  {
    
    // ---------------------------------------------
    // -- Type Checker State
    // ---------------------------------------------
    // subcheckers
    private IQuestionAnswer<TypeCheckInfo, PType> exp;
    private IQuestionAnswer<TypeCheckInfo, PType> stm;
    private IQuestionAnswer<TypeCheckInfo, PType> prc;
    private IQuestionAnswer<TypeCheckInfo, PType> dad;
    private boolean                               lastResult;
    
    private void initialize()
      {
        exp = new TCExpressionVisitor(this);
        stm = new TCStatementVisitor(this);
        prc = new TCProcessVisitor(this);
        dad = new TCDeclAndDefVisitor(this);
      }
    
    // ---------------------------------------------
    // -- Dispatch to sub-checkers
    // ---------------------------------------------
    @Override
    public PType defaultPDeclaration(PDeclaration node, TypeCheckInfo question)
        throws AnalysisException
      {
        return node.apply(this.dad, question);
      }
    
    @Override
    public PType defaultPDefinition(PDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        return node.apply(this.dad, question);
      }
    
    @Override
    public PType defaultPExp(PExp node, TypeCheckInfo question)
        throws AnalysisException
      {
        return node.apply(exp, question);
      }
    
    @Override
    public PType defaultPProcess(PProcess node, TypeCheckInfo question)
    		throws AnalysisException
    {
    	return node.apply(prc, question);
    }
    
    @Override
    public PType defaultPAction(PAction node, TypeCheckInfo question)
        throws AnalysisException
      {
        return node.apply(stm, question);
      }
    
    // ---------------------------------------------
    // -- Public API to CML Type Checker
    // ---------------------------------------------
    /**
     * This method is invoked by the command line tool when pretty printing the
     * analysis name.
     * 
     * @return Pretty short name for this analysis.
     */
    public String getAnalysisName()
      {
        return "The CML Type Checker";
      }
    
    /**
     * Construct a CmlTypeChecker with the intension of checking a list of
     * PSources. These source may refer to each other.
     * 
     * 
     * @param cmlSources
     *          - Source containing CML Paragraphs for type checking.
     */
    public VanillaCmlTypeChecker(List<PSource> cmlSources)
      {
        initialize();
        this.sourceForest = cmlSources;
      }
    
    /**
     * Construct a CmlTypeChecker with the intension of checking a single
     * source.
     * 
     * @param singleSource
     */
    public VanillaCmlTypeChecker(PSource singleSource)
      {
        initialize();
        this.sourceForest = new LinkedList<PSource>();
        this.sourceForest.add(singleSource);
      }
    
    /**
     * Run the type checker. This will update the source(s) this type checker
     * instance was constructed with.
     * 
     * @return - Returns true if the entire tree could be type checked without
     *         errors.
     */
    public boolean typeCheck()
      {
        TypeCheckInfo info = new TypeCheckInfo();
        
        if (!cleared)
          return lastResult;
        
        // for each source
        for (PSource s : sourceForest)
          {
            for (SParagraphDefinition paragraph : s.getParagraphs())
              {
                try
                  {
                    paragraph.apply(this, info);
                  } catch (AnalysisException ae)
                  {
                    // This means we have a bug in the type checker
                    ae.printStackTrace();
                    return false;
                  }
              }
          }
        super.cleared = false;
        
        return (lastResult = (errors.size() == 0));
      }
    
    /**
     * Get errors that occurred while type checking.
     * 
     * @return list of CMLTypeErrors
     */
    public List<CMLTypeError> getTypeErrors()
      {
        if (cleared)
          throw new IllegalStateException(
              "The type checker has not run yet, invoke typeCheck before getting the errors.");
        return errors;
      }
    
    /**
     * Get warnings that occurred while type checking. The type check method
     * will return true even though this returns an non-empty list.
     * 
     * @return list of CMLTypeWarnings
     */
    public List<CMLTypeWarning> getTypeWarnings()
      {
        if (cleared)
          throw new IllegalStateException(
              "The type checker has not run yet, please invoke typeCheck method before getting the warnings.");
        return warnings;
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
        File cml_examples = new File("../../docs/cml-examples");
        int failures = 0;
        int successes = 0;
        // runOnFile(null);
        
        if (cml_examples.isDirectory())
          {
            for (File example : cml_examples.listFiles())
              {
                System.out.print("Typechecking example: " + example.getName()
                    + " \t\t...: ");
                System.out.flush();
                try
                  {
                    runOnFile(example);
                    System.out.println("done");
                    successes++;
                  } catch (Exception e)
                  {
                    System.out.println("exception");
                    failures++;
                  }
              }
          }
        
        System.out.println(successes + " was successful, " + failures
            + " was failures.");
        
      }
    
  }