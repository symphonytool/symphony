/**
 * NoDivAnalysis Example.
 *
 * Description: 
 * 
 * This analysis extends the DepthFirstAnalysisAdaptor to run through
 * the AST only checking div-nodes. If a div node is encountered a
 * warning is produced.
 *
 */

package eu.compassresearch.examples;

/**
 * Core stuff needed in this simple analysis.
 *
 */
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.expressions.ADivideNumericBinaryExp;
import org.overturetool.vdmj.lex.LexLocation;

/**
 * A bit of java 
 */
import java.util.LinkedList;
import java.util.List;

public class DivWarnAnalysis extends DepthFirstAnalysisAdaptor {

    // Constants
    private final static String ANALYSIS_STRING = "DIV ANALYSIS WARNING: ";
    private final static String ANALYSIS_NAME = "Div warn analysis";
 
    // Analysis Result
    private List<String> warnings;

    // Constructor setting warnings up
    public DivWarnAnalysis()
    {
	warnings = new LinkedList<String>();
    }

    // Pretty warning for the result
    private static String prettyPrintLocation(LexLocation loc)
    {
	StringBuilder sb = new StringBuilder();
	sb.append(ANALYSIS_STRING);
	sb.append(" Div expression found starting at : ");
	sb.append(loc.startLine +":"+loc.startPos);
	sb.append(" to " + loc.endLine + ":"+loc.endPos);
	return sb.toString();
    }


    /**
     * When the DepthFirstAnalysisAdaptor reaches a Divide binary
     * expression this method is invoke. Here this analysis wants to
     * create a warning and add it to its output.
     */
    @Override
    public void caseADivideNumericBinaryExp(ADivideNumericBinaryExp node) {
	super.caseADivideNumericBinaryExp(node);
	warnings.add(prettyPrintLocation(node.getLocation()));
    }

    /**
     * The ide/cmdline tool will pick this method up and use it for
     * pretty printing the analysis name. If this method is missing
     * the cmdline tool will use the class name.
     *
     * @return usefriendly name for this analysis.
     */
    public String getAnalysisName() 
    {
	return ANALYSIS_NAME;
    }

    /**
     * Method to acquire the result produced by this analysis.
     *
     */
    public List<String> getWarnings()
    {
	return warnings;
    }
}