package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.SingleTypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAQuoteLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASeqEnumSeqExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetDifferenceBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAExternalChoiceReplicatedAction extends MCSReplicatedActionBase {

	public MCAExternalChoiceReplicatedAction(
			LinkedList<MCPSingleDeclaration> replicationDeclaration,
			MCPAction replicatedAction) {
		super(replicationDeclaration, replicatedAction);
	}

	@Override
	public String toFormula(String option) {
		 
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCPSingleDeclaration sDecl = this.getReplicationDeclaration().getFirst();
		LinkedList<MCPCMLExp> indexes = new LinkedList<MCPCMLExp>();
		if (sDecl instanceof MCAExpressionSingleDeclaration) {
			MCPCMLExp pExp = ((MCAExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof MCASetEnumSetExp) {
				indexes.addAll(((MCASetEnumSetExp) pExp).getMembers());
			} else if(pExp instanceof MCASeqEnumSeqExp){
				indexes.addAll(((MCASeqEnumSeqExp) pExp).getMembers());
			} else if(pExp instanceof MCAVariableExp){
				//values must be picked from a mapping in the context
				NameValue nameValue = context.getNameValue(((MCAVariableExp) pExp).getName());
				TypeManipulator typeHandler = TypeManipulator.getInstance();
				LinkedList<TypeValue> values = typeHandler.getValues(nameValue.getType());
				for (TypeValue typeValue : values) {
					if(typeValue instanceof SingleTypeValue){
						indexes.add(new MCAVariableExp(((SingleTypeValue) typeValue).getValue()));
					}
					//if it is a product type?
				}
			}else if(pExp instanceof MCASetDifferenceBinaryExp){
				//indexes = ((MCASetDifferenceBinaryExp) pExp).getMembers();
			}
		} else if (sDecl instanceof MCATypeSingleDeclaration){
			MCPCMLType type = ((MCATypeSingleDeclaration) sDecl).getType();
			//the values of this type can be caught from type definitions or from chanset definitions
			TypeManipulator typeHandler = TypeManipulator.getInstance();
			
			LinkedList<TypeValue> values = typeHandler.getValues(type);
			for (TypeValue typeValue : values) {
				indexes.add(new MCAQuoteLiteralExp(((SingleTypeValue) typeValue).getValue()));
			}
		} 
		
		
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(context,indexes, option);

		return replicatedAction.toString();
	}
	
	private StringBuilder buildReplicatedAction(NewCMLModelcheckerContext context,
			LinkedList<MCPCMLExp> indexes,String option) {

		MCPSingleDeclaration sDecl = this.getReplicationDeclaration().getFirst();
		String identifier = null;
		MCPCMLType identifierType = null;
		ExpressionEvaluator expressionHandler = ExpressionEvaluator.getInstance();
		
		if (sDecl instanceof MCAExpressionSingleDeclaration) {
			identifier = ((MCAExpressionSingleDeclaration)sDecl).getIdentifier();
			identifierType = expressionHandler.getTypeFor(((MCAExpressionSingleDeclaration)sDecl).getExpression());
		} else if (sDecl instanceof MCATypeSingleDeclaration){
			identifier = ((MCATypeSingleDeclaration)sDecl).getIdentifier();
			identifierType = ((MCATypeSingleDeclaration)sDecl).getType();
		}
	
		StringBuilder result = new StringBuilder();
		MCPAction replicatedAction = this.getReplicatedAction();
		NameValue mapping = new NameValue(identifier,null,identifierType);
		
		if(indexes.size() == 1){
			
			
			LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
			MCPCMLExp firstArg = indexes.removeFirst();
			realArgs.add(firstArg);
			mapping.setVariableValue(firstArg.toFormula(option));
			context.localIndexedVariablesMapping.push(mapping);
			
			if(replicatedAction instanceof MCAReferenceAction){
				((MCAReferenceAction) replicatedAction).setArgs(realArgs);
			} else if (replicatedAction instanceof MCACommunicationAction){
				((MCACommunicationAction) replicatedAction).setIdentifier(firstArg.toFormula(option));
				
			}
			result.append(this.getReplicatedAction().toFormula(option));
			context.localIndexedVariablesMapping.pop();
		}else if (indexes.size() > 1) {
			MCPCMLExp firstArg = indexes.removeFirst();
			mapping.setVariableValue(firstArg.toFormula(option));
			context.localIndexedVariablesMapping.push(mapping);
			result.append("eChoice(");
			
			
			if(replicatedAction instanceof MCAReferenceAction){
				LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
				realArgs.add(firstArg);
				((MCAReferenceAction) replicatedAction).setArgs(realArgs);
			} else if(replicatedAction instanceof MCACommunicationAction){
				replicatedAction = ((MCACommunicationAction) replicatedAction).copy();
				if (this.replicationDeclaration.getFirst() instanceof MCAExpressionSingleDeclaration){
					MCAExpressionSingleDeclaration singleDecl = (MCAExpressionSingleDeclaration) this.replicationDeclaration.getFirst();
					String varName = singleDecl.getIdentifier();
					((MCACommunicationAction) replicatedAction).setParameter(varName, firstArg);
				}
			}
			result.append(replicatedAction.toFormula(option));
			result.append(",");
			context.localIndexedVariablesMapping.pop();
			
			StringBuilder rest = buildReplicatedAction(context,indexes,option);
			result.append(rest.toString());
			result.append(")");
		}
		return result;
	}
	
}
