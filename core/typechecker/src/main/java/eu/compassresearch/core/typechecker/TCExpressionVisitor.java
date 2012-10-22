package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.visitor.TypeCheckVisitor;
import org.overture.typechecker.visitor.TypeCheckerExpVisitor;

import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.transformation.CmlAstToOvertureAst;
import eu.compassresearch.transformation.CopyTypesFromOvtToCmlAst;

class TCExpressionVisitor extends
    QuestionAnswerAdaptor<TypeCheckQuestion, PType>
  {
    
    /**
	 * 
	 */
    private static final long      serialVersionUID = -6509187123701383525L;
    
    // A parent checker may actually not be necessary on this
    @SuppressWarnings("unused")
    final private CmlTypeChecker   parent;
    private final TypeIssueHandler issueHandler;
    
    public TCExpressionVisitor(CmlTypeChecker parentChecker,
        TypeIssueHandler issueHandler)
      {
        parent = parentChecker;
        this.issueHandler = issueHandler;
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
    public PType defaultPExp(PExp node, TypeCheckQuestion question)
        throws AnalysisException
      {
        org.overture.typechecker.TypeChecker.clearErrors();
        
        question.updateContextNameToCurrentScope(node);
        
        CmlAstToOvertureAst transform = new CmlAstToOvertureAst(parent,
            issueHandler);
        INode ovtNode = node.apply(transform);
        
        TypeCheckerExpVisitor ovtExpVist = new TypeCheckerExpVisitor(
            new TypeCheckVisitor());
        
        org.overture.typechecker.TypeCheckInfo quest = new org.overture.typechecker.TypeCheckInfo(
            question.getOvertureEnvironment());
        
        quest.scope = NameScope.NAMES;
        
        try
          {
            ovtNode.apply(ovtExpVist, quest);
          } catch (org.overture.ast.analysis.AnalysisException e1)
          {
            e1.printStackTrace();
          }
        
        if (org.overture.typechecker.TypeChecker.getErrorCount() > 0)
          {
            List<VDMError> errorList = TypeChecker.getErrors();
            for (VDMError err : errorList)
              {
                issueHandler.addTypeError(node, err.toProblemString());
              }
            return new AErrorType(node.getLocation(), true);
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
