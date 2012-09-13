package eu.compassresearch.core.typechecker;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.types.AStatementType;
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
        
        node.setType(new AStatementType());
        return node.getType();
      }
    
    @Override
    public PType caseABlockStatementAction(ABlockStatementAction node,
        TypeCheckInfo question) throws AnalysisException
      {
        // extend the environment
        
        //
        PAction action = node.getAction();
        PType actionType = action.apply(parentChecker, question);
        if (actionType == null)
          throw new AnalysisException(
              "Unable to type check enclosed action in block statement action.");
        
        node.setType(new AStatementType());
        
        return node.getType();
      }
    
    @Override
    public PType caseASingleGeneralAssignmentStatementAction(
    		ASingleGeneralAssignmentStatementAction node, TypeCheckInfo question)
    		throws AnalysisException {

    	//FIXME Some scope stuff is not correct when typechecking let exp
//    	PType expType = node.getExpression().apply(parentChecker, question);
//    	if (expType == null)
//            throw new AnalysisException(
//                "Unable to type check expression in assignment action.");
    	
    	PType stateDesignatorType = node.getStateDesignator().apply(parentChecker, question);
//  TODO This is not implemented yet
//    	if (stateDesignatorType == null)
//            throw new AnalysisException(
//                "Unable to type check state designator in assignment action.");
    	
    	node.setType(new AStatementType());
    	
    	return node.getType();
    }
    
    @Override
    public PType caseACommunicationAction(ACommunicationAction node,
    		TypeCheckInfo question) throws AnalysisException {
    	
    	//There should be a channel defined with this name
    	if(!question.env.getGlobalChannelNames().contains(node.getIdentifier().getName()))
    		throw new AnalysisException(
                    "No channel with name '" + node.getIdentifier() + "' is defined");
    	
    	node.getAction().apply(this,question);
    	
    	//TODO there is no type field on a general action
    	//node.setType
    	
    	return new AStatementType();
    }
    
  }
