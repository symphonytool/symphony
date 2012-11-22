/**
 * Proof Obligation Generator Analysis
 *
 * Description: 
 * 
 * This analysis extends the QuestionAnswerAdaptor to generate
 * POs from the AST generated by the CML parser
 *
 */

package eu.compassresearch.core.analysis.pog.visitors;
 
// Java libraries 
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
//import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.pog.visitor.PogExpVisitor;
import org.overture.pog.visitor.PogVisitor;


import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.POContextStack;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligationList;

//From Overture POG
//import eu.compassresearch.ast.expressions.PStm;

//From TC - not needed ?
//import eu.compassresearch.ast.analysis.intf.IQuestionAnswer;
//import eu.compassresearch.ast.node.INode;
//import eu.compassresearch.core.parser.CmlParser; CAN'T FIND PARSER PACKAGE    



public class ProofObligationGenerator extends 
				QuestionAnswerAdaptor<POContextStack, ProofObligationList>
{
    private final static String ANALYSIS_NAME = "Proof Obligation Generator";
     
    private List<PSource> sourceForest;
    
    // ---------------------------------------------
    // -- Proof Obligation Generator State
    // ---------------------------------------------
    
    // subcheckers
    private POGExpressionVisitor exp;
    private PogExpVisitor exp2;
    private POGStatementVisitor stm;
    private POGProcessVisitor prc;
    private POGDeclAndDefVisitor dad;
    
    private void initialize()
    {
        exp = new POGExpressionVisitor(this);
        exp2 = new PogExpVisitor(new PogVisitor());
        stm = new POGStatementVisitor(this);
        prc = new POGProcessVisitor(this);
        dad = new POGDeclAndDefVisitor(this);
    }
    
    // ---------------------------------------------
    // -- Dispatch to sub-checkers
    // ---------------------------------------------    

// SUBCHECKER FROM TYPECHECKER
//     @Override
//     public ProofObligationList defaultPType(PType node, TypeCheckQuestion question)
//         throws AnalysisException
//       {
//         return node.apply(typ, question);
//       }
//   
// SUBCHECKER FROM OVERTURE POG
//     @Override
// 	public ProofObligationList defaultPStm(PStm node, POContextStack question)
//         throws AnalysisException
// 	{
// 		return node.apply(stm, question);
// 	}  

//    @Override
//    public ProofObligationList defaultPProcess(PProcess node, POContextStack question)
//         throws AnalysisException
//    {
//         return node.apply(this.prc, question);
//    }
//      
//    @Override
//    public ProofObligationList defaultPAction(PAction node, POContextStack question)
//      throws AnalysisException
//    {
//      return node.apply(this.stm, question);
//    }
//   
//    @Override
//    public ProofObligationList defaultPDeclaration(PDeclaration node,
//       POContextStack question) throws AnalysisException
//    {
//       return node.apply(this.dad, question);
//    }
    
    @Override
    public ProofObligationList defaultPDefinition(PDefinition node, POContextStack question)
        throws AnalysisException
      {
        return node.apply(this.dad, question);
      }
    
    @Override
    public ProofObligationList defaultPExp(PExp node, POContextStack question)
        throws AnalysisException
      {
        return node.apply(this.exp, question);
      }
      

      
	// ---------------------------------------------
    // -- Public API to CML POG
    // ---------------------------------------------
    // Taken from Type Checker code
    // ---------------------------------------------
    /**
     * This method is invoked by the command line tool when pretty printing the
     * analysis name.
     * 
     * @return Pretty short name for this analysis.
     */
    public String getAnalysisName()
    {
        return ANALYSIS_NAME;
    }
    
    /**
     * Construct a ProofObligationGenerator with the intension of checking a list of
     * PSources. These source may refer to each other.
     * 
     * @param cmlSources - Sources containing CML Paragraphs for PO gen.
     */
    public ProofObligationGenerator(List<PSource> cmlSources)
    {
        initialize();
        this.sourceForest = cmlSources; 
    }
    
    /**
     * Construct a ProofObligationGenerator with the intension of checking a single
     * source.
     * 
     * @param singleSource - Source containing CML Paragraphs for PO gen.
     */
    public ProofObligationGenerator(PSource singleSource)
    {
        initialize();
        this.sourceForest = new LinkedList<PSource>();
        this.sourceForest.add(singleSource);
    }

	/**
     * Run the proof obligation generator. This will update the source(s) this POG
     * instance was constructed with.
     * 
     * @return - Returns ProofObligation list. This may need to change. 
     */
    public ProofObligationList generatePOs()
    {
        ProofObligationList obligations = new ProofObligationList();
		POContextStack ctxt = new POContextStack();
		
		// for each source
        for (PSource s : sourceForest)
        {	
        	// for each CML paragraph
            for (SParagraphDefinition paragraph : s.getParagraphs())
            {
                try
                {	
                    System.out.println("--------------------------------PROCESSING--------------------------------");
                	System.out.println(paragraph.toString());
                	System.out.println("----------------------------------RESULT----------------------------------");

                	// process paragraph:
                    obligations = paragraph.apply(this, ctxt);

                    System.out.println();
					System.out.println();
                } 
                catch (AnalysisException ae)
                {
                  	// This means we have a bug in the pog
                  	//ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //ae.printStackTrace(new PrintStream(baos));
                    //errors.add(new CMLTypeError(s,
                       System.out.println( "The COMPASS Proof Obligation Generator failed on this cml-source. Please submit it for investigation to richard.payne@ncl.ac.uk.\n");
                    //          + new String(baos.toByteArray())));
                    return null;
                  }
              }
          }
		       
		System.out.println(obligations.size() + " Proof Obligations generated");
		return obligations;
    }


    // ---------------------------------------------
    // Static stuff for running the TypeChecker from Eclipse
    // ---------------------------------------------
    // Taken from Type Checker code
    // ---------------------------------------------
    
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
        } 
        else
        {
            AFileSource fs = new AFileSource();
            fs.setName(f.getName());
            fs.setFile(f);
            return fs;
        }
    }
    
    /**
     * This method runs the PO generator on a given file. The method invokes methods to generate POs.
     * 
     * @param f - The file to generate POs
     */
    private static void runOnFile(File f) throws IOException
    {
        // set file name
        PSource source = prepareSource(f);
               
        // generate POs
        ProofObligationGenerator cmlPOG = new ProofObligationGenerator(source);
        cmlPOG.generatePOs();
        
        // Report success
        System.out.println("Proof Obligation Generation is complete for the given CML Program");
    }
   
     /**
     * Main method for class. Current test class takes a set of cml examples and 
     * generates POs for each
     */
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
                System.out.print("Generating Proof Obligations for example: " + example.getName()
                    + " \t\t...: ");
                System.out.flush();
                try
                {
                    runOnFile(example);
                    System.out.println("done");
                    successes++;
                } 
                catch (Exception e)
                {
                    System.out.println("exception");
                    failures++;
                }
            }
        }
        
        System.out.println(successes + " was successful, " + failures + " was failures.");      
	}
}
