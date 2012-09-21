package eu.compassresearch.core.typechecker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.intf.IQuestionAnswer;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.node.INode;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.PType;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeComparator;

@SuppressWarnings("serial")
class VanillaCmlTypeChecker extends AbstractTypeChecker
  {
    
    // ---------------------------------------------
    // -- Type Checker State
    // ---------------------------------------------
    // subcheckers
    private IQuestionAnswer<TypeCheckQuestion, PType> exp;
    private IQuestionAnswer<TypeCheckQuestion, PType> stm;
    private IQuestionAnswer<TypeCheckQuestion, PType> dad;
    private IQuestionAnswer<TypeCheckQuestion, PType> typ;           // basic
    private IQuestionAnswer<TypeCheckQuestion, PType> prc;
    // type
    // checker
    private boolean                                   lastResult;
    private final TypeComparator                      typeComparator;
    
    private void initialize()
      {
        exp = new TCExpressionVisitor(this, this);
        stm = new TCStatementVisitor(this, this);
        dad = new TCDeclAndDefVisitor(this, typeComparator, this);
        typ = new TCTypeVisitor(this, this);
      }
    
    // ---------------------------------------------
    // -- Dispatch to sub-checkers
    // ---------------------------------------------
    
    @Override
    public PType defaultPType(PType node, TypeCheckQuestion question)
        throws AnalysisException
      {
        question.updateContextNameToCurrentScope(node);
        return node.apply(typ, question);
      }
    
    @Override
    public PType defaultINode(INode node, TypeCheckQuestion question)
        throws AnalysisException
      {
        question.updateContextNameToCurrentScope(node);
        return super.defaultINode(node, question);
      }
    
    @Override
    public PType defaultPDeclaration(PDeclaration node,
        TypeCheckQuestion question) throws AnalysisException
      {
        question.updateContextNameToCurrentScope(node);
        return node.apply(this.dad, question);
      }
    
    @Override
    public PType defaultPDefinition(PDefinition node, TypeCheckQuestion question)
        throws AnalysisException
      {
        question.updateContextNameToCurrentScope(node);
        return node.apply(this.dad, question);
      }
    
    @Override
    public PType defaultPExp(PExp node, TypeCheckQuestion question)
        throws AnalysisException
      {
        question.updateContextNameToCurrentScope(node);
        return node.apply(exp, question);
      }
    
    @Override
    public PType defaultPProcess(PProcess node, TypeCheckQuestion question)
        throws AnalysisException
      {
        return node.apply(prc, question);
      }
    
    @Override
    public PType defaultPAction(PAction node, TypeCheckQuestion question)
        throws AnalysisException
      {
        question.updateContextNameToCurrentScope(node);
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
        
        this.sourceForest = cmlSources;
        typeComparator = SimpleTypeComparator.newInstance();
        initialize();
      }
    
    void clear()
      {
        cleared = true;
        sourceForest = null;
        this.issueHandler = new CollectingIssueHandler();
      }
    
    public VanillaCmlTypeChecker(List<PSource> cmlSource,
        TypeComparator typeComparator)
      {
        this.sourceForest = new LinkedList<PSource>();
        sourceForest.addAll(cmlSource);
        this.typeComparator = typeComparator;
        initialize();
      }
    
    /**
     * Construct a CmlTypeChecker with the intension of checking a single
     * source.
     * 
     * @param singleSource
     */
    public VanillaCmlTypeChecker(PSource singleSource)
      {
        
        this.sourceForest = new LinkedList<PSource>();
        this.sourceForest.add(singleSource);
        typeComparator = SimpleTypeComparator.newInstance();
        initialize();
        
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
        TypeCheckInfo info = TypeCheckInfo.getNewTopLevelInstance(this);
        
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
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ae.printStackTrace(new PrintStream(baos));
                    super
                        .addTypeError(
                            s,
                            "The COMPASS Type checker failed on this cml-source. Please submit it for investigation to rala@iha.dk.\n"
                                + new String(baos.toByteArray()));
                    // This means we have a bug in the type checker
                    return false;
                  }
              }
          }
        super.cleared = false;
        
        return !super.issueHandler.hasErrors();
      }
    
    /**
     * Get errors that occurred while type checking.
     * 
     * @return list of CMLTypeErrors
     */
    public List<CMLTypeError> getTypeErrors()
      {
        return issueHandler.getTypeErrors();
      }
    
    /**
     * Get warnings that occurred while type checking. The type check method
     * will return true even though this returns an non-empty list.
     * 
     * @return list of CMLTypeWarnings
     */
    public List<CMLTypeWarning> getTypeWarnings()
      {
        return issueHandler.getTypeWarnings();
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
    
    @Override
    public boolean hasErrors()
      {
        return issueHandler.hasErrors();
      }
    
    @Override
    public boolean hasWarnings()
      {
        return issueHandler.hasWarnings();
      }
    
    @Override
    public boolean hasIssues()
      {
        return issueHandler.hasIssues();
      }
    
  }