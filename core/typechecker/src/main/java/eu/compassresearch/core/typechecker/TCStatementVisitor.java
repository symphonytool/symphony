package eu.compassresearch.core.typechecker;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.ast.types.PType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings("serial")
class TCStatementVisitor extends
    QuestionAnswerAdaptor<TypeCheckQuestion, PType>
  {
    
    private final CmlTypeChecker   parentChecker;
    private final TypeIssueHandler issueHandler;
    
    public TCStatementVisitor(CmlTypeChecker parentChecker,
        TypeIssueHandler issueHandler)
      {
        this.parentChecker = parentChecker;
        this.issueHandler = issueHandler;
      }
    
    @Override
    public PType caseAReturnStatementAction(AReturnStatementAction node,
        TypeCheckQuestion question) throws AnalysisException
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
    
    @Override
    public PType caseABlockStatementAction(ABlockStatementAction node,
        TypeCheckQuestion question) throws AnalysisException
      {
        // extend the environment
        
        //
        PAction action = node.getAction();
        PType actionType = action.apply(parentChecker, question);
        if (actionType == null)
          issueHandler.addTypeError(action,
              TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
                  .customizeMessage(action.toString()));
        node.setType(new AStatementType());
        
        return node.getType();
      }
    
  }
