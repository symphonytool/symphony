package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReferenceAction;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.SingleTypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAQuoteLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASeqEnumSeqExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetDifferenceBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCASequentialCompositionReplicatedProcess extends MCSReplicatedProcessBase {

	public MCASequentialCompositionReplicatedProcess(
			LinkedList<MCPSingleDeclaration> declarations,
			MCPProcess replicatedProc) {
		super(declarations, replicatedProc);
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
				//values must be got from defined values 
				MCAValueDefinition valueDef = context.getValueDefinition(((MCAVariableExp) pExp).getName());
				NameValue nameValue = null;
				LinkedList<TypeValue> values = new LinkedList<TypeValue>();  
				TypeManipulator typeHandler = TypeManipulator.getInstance();
				ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
				if(valueDef != null){
					MCANamedInvariantType typeName = new MCANamedInvariantType(valueDef.getName(),valueDef.getName());
					
					values = typeHandler.getValues(typeName);
					
				}else{
					//values must be picked from a mapping in the context
					nameValue = context.getNameValue(((MCAVariableExp) pExp).getName());
					values = typeHandler.getValues(nameValue.getType());
				}
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
		StringBuilder replicatedAction = buildReplicatedProcess(context,indexes, option);

		return replicatedAction.toString();
	}
	
	private StringBuilder buildReplicatedProcess(NewCMLModelcheckerContext context,
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
		MCPProcess replicatedProcess = this.getReplicatedProcess();
		NameValue mapping = new NameValue(identifier,null,identifierType);
		
		if(indexes.size() == 1){
			
			
			LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
			MCPCMLExp firstArg = indexes.removeFirst();
			realArgs.add(firstArg);
			mapping.setVariableValue(firstArg.toFormula(option));
			context.localIndexedVariablesMapping.push(mapping);
			
			if(replicatedProcess instanceof MCAReferenceProcess){
				((MCAReferenceProcess) replicatedProcess).setArgs(realArgs);
			} 
			result.append(this.getReplicatedProcess().toFormula(option));
			context.localIndexedVariablesMapping.pop();
			context.localIndexedVariablesDiscarded.add(identifier);
		}else if (indexes.size() > 1) {
			MCPCMLExp firstArg = indexes.removeFirst();
			mapping.setVariableValue(firstArg.toFormula(option));
			context.localIndexedVariablesMapping.push(mapping);
			result.append("seqC(");
			
			
			if(replicatedProcess instanceof MCAReferenceProcess){
				LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
				realArgs.add(firstArg);
				((MCAReferenceAction) replicatedProcess).setArgs(realArgs);
			} 
			result.append(replicatedProcess.toFormula(option));
			result.append(",");
			context.localIndexedVariablesMapping.pop();
			
			StringBuilder rest = buildReplicatedProcess(context,indexes,option);
			result.append(rest.toString());
			result.append(")");
		}
		return result;
	}
}
