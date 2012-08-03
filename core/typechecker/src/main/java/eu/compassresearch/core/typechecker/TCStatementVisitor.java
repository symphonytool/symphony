package eu.compassresearch.core.typechecker;

import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.types.PType;

@SuppressWarnings("serial")
public class TCStatementVisitor extends
    QuestionAnswerAdaptor<TypeCheckInfo, PType>
  {
    
    private VanillaCmlTypeChecker parentChecker;
    
    public TCStatementVisitor(VanillaCmlTypeChecker parentChecker)
      {
        this.parentChecker = parentChecker;
      }
    
    @Override
    public PType caseAReturnStatementAction(AReturnStatementAction node,
        TypeCheckInfo question) throws AnalysisException
      {
        AExplicitOperationDefinition operation = node
            .getAncestor(AExplicitOperationDefinition.class);
        if (operation == null)
          throw new AnalysisException(
              "Return Statement Action does not have explicit operation as parent.");
        
        PExp exp = node.getExp();
        PType type = exp.apply(parentChecker, question);
        if (type == null)
          throw new AnalysisException("Unable to type check expression \""
              + exp + "\" in return statement action of " + operation.getName());
        
        return super.caseAReturnStatementAction(node, question);
      }
  }
