package eu.compassresearch.core.typechecker;

import org.overture.ast.node.INode;
import org.overture.typechecker.visitor.TypeCheckVisitor;
import org.overture.typechecker.visitor.TypeCheckerExpVisitor;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.types.PType;
import eu.compassresearch.transformation.CmlAstToOvertureAst;
import eu.compassresearch.transformation.CopyTypesFromOvtToCmlAst;

public class TCExpressionVisitor extends
    QuestionAnswerAdaptor<TypeCheckInfo, PType>
  {
    
    /**
	 * 
	 */
    private static final long                                 serialVersionUID = -6509187123701383525L;
    
    // A parent checker may actually not be necessary on this
    @SuppressWarnings("unused")
    final private QuestionAnswerAdaptor<TypeCheckInfo, PType> parent;
    
    public TCExpressionVisitor(VanillaCmlTypeChecker parentChecker)
      {
        parent = parentChecker;
      }
    
    /**
     * Translate a CML expression into an equivalent Overture VDM expression and
     * type check that. Afterwards use the CopyTypesFromOvtToCmlAst to copy over
     * the Overture VDM types.
     * 
     * 
     * @param node
     *          - the expression to type check
     * @param question
     *          - environmental stuff, green trees whatever
     * @return A type checked cml expression
     * @throws AnalysisException
     *           - if anythings goes wrong that is not just a type error.
     */
    @Override
    public PType defaultPExp(PExp node, TypeCheckInfo question)
        throws AnalysisException
      {
        CmlAstToOvertureAst transform = new CmlAstToOvertureAst();
        INode ovtNode = transform.defaultINode(node);
        
        TypeCheckerExpVisitor ovtExpVist = new TypeCheckerExpVisitor(
            new TypeCheckVisitor());
        
        org.overture.typechecker.TypeCheckInfo quest = new org.overture.typechecker.TypeCheckInfo(
            question.env.getOvertureEnv());
        
        try
          {
            ovtExpVist.defaultPExp((org.overture.ast.expressions.PExp) ovtNode,
                quest);
          } catch (org.overture.ast.analysis.AnalysisException e1)
          {
            e1.printStackTrace();
          }
        
        CopyTypesFromOvtToCmlAst copier = new CopyTypesFromOvtToCmlAst(
            transform.getNodeMap());
        
        try
          {
            node = (PExp) copier.defaultINode(ovtNode);
          } catch (org.overture.ast.analysis.AnalysisException e)
          {
            throw new AnalysisException(e.getMessage());
          }
        
        return node.getType();
      }
  }
