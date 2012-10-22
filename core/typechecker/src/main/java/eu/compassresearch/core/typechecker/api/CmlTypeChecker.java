package eu.compassresearch.core.typechecker.api;

import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.types.PType;

/**
 * 
 * @author rwl
 * 
 *         A CML Type checker has the characteristics of this interface.
 * 
 */
public interface CmlTypeChecker extends
    IQuestionAnswer<TypeCheckQuestion, PType>
  {
    /**
     * Run the type checker. This will update the source(s) this type checker
     * instance was constructed with.
     * 
     * @return - Returns true if the entire tree could be type checked without
     *         errors. It returns false otherwise and courses to failing the
     *         type check can be inspected though getErrors.
     */
    public boolean typeCheck();
    
    /**
     * Return a pretty name for this type checker user-interactive tools can use
     * for printing.
     * 
     * @return - analysis pretty name.
     */
    public String getAnalysisName();
  }
