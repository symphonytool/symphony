package eu.compassresearch.core.typechecker;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.PExp;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

/**
 * @author rwl
 * 
 */
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
        
        node.setType(new AStatementType());
        return node.getType();
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
    
    @Override
    public PType caseASingleGeneralAssignmentStatementAction(
        ASingleGeneralAssignmentStatementAction node, TypeCheckQuestion question)
        throws AnalysisException
      {
        
        // FIXME Some scope stuff is not correct when typechecking let exp
        // PType expType = node.getExpression().apply(parentChecker, question);
        // if (expType == null)
        // throw new AnalysisException(
        // "Unable to type check expression in assignment action.");
        
        PType stateDesignatorType = node.getStateDesignator().apply(
            parentChecker, question);
        // TODO This is not implemented yet
        // if (stateDesignatorType == null)
        // throw new AnalysisException(
        // "Unable to type check state designator in assignment action.");
        
        node.setType(new AStatementType());
        
        return node.getType();
      }
    
    @Override
    public PType caseAInternalChoiceAction(AInternalChoiceAction node,
        TypeCheckQuestion question) throws AnalysisException
      {
        
        // TODO there is no type marker on a general action
        // node.setType
        
        // node.setType(new AStatementType());
        
        return new AStatementType();
      }
    
    @Override
    public PType caseACommunicationAction(ACommunicationAction node,
        TypeCheckQuestion question) throws AnalysisException
      {
        
        // There should be a channel defined with this name
        if (null == question.lookupChannel(node.getIdentifier()))
          {
            issueHandler.addTypeError(node,
                TypeErrorMessages.NAMED_TYPE_UNDEFINED.customizeMessage(node
                    .getIdentifier().name));
            return new AErrorType(node.getLocation(), true);
          }
        node.getAction().apply(this, question);
        
        // TODO there is no type marker on a general action
        // node.setType
        
        return new AStatementType();
      }
  }
