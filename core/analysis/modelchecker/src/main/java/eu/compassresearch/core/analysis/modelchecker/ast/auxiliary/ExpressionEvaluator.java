package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import org.overture.ast.patterns.AIdentifierPattern;

import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASignalCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAWriteCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAApplyExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCABooleanConstExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAInSetBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotUnaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAQuoteLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetRangeSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAUnaryMinusUnaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCAIdentifierPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCABooleanBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANatNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAQuoteType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCVoidType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class ExpressionEvaluator implements IExpressionEvaluator {

	private static ExpressionEvaluator instance;
	
	public static ExpressionEvaluator getInstance(){
		if(instance == null){
			instance = new ExpressionEvaluator();
		}
		return instance;
	}
	
	
	private MCPCMLType getTypeFor(MCPCMLExp exp){
		MCPCMLType result = null;
		if(exp instanceof MCAIntLiteralExp){
			result = this.getTypeFor((MCAIntLiteralExp)exp);
		} else if(exp instanceof MCAVariableExp){
			result = this.getTypeFor((MCAVariableExp)exp);
		} else if(exp instanceof MCAQuoteLiteralExp){
			result = this.getTypeFor((MCAQuoteLiteralExp)exp);
		}
		
		return result;
	}
	
	private MCPCMLType getTypeForIOComm(MCPCMLExp exp){
		MCPCMLType result = null;
		if(exp instanceof MCAIntLiteralExp){
			result = this.getTypeFor((MCAIntLiteralExp)exp);
		} else if(exp instanceof MCAVariableExp){
			result = this.getTypeForIOComm((MCAVariableExp)exp);
		} else if(exp instanceof MCAQuoteLiteralExp){
			result = this.getTypeForIOComm((MCAQuoteLiteralExp)exp);
		}
		
		return result;
	}
	
	private MCPCMLType getTypeForIOComm(MCAVariableExp exp){
		MCPCMLType result = null;
		result = new MCANamedInvariantType(exp.getName());
		return result;
	}
	
	private MCPCMLType getTypeForIOComm(MCAQuoteLiteralExp exp){
		MCPCMLType result = null;
		
		result = new MCAQuoteType(exp.getValue());
		
		return result;
	}
	private MCPCMLType getTypeFor(MCPCMLPattern pattern){
		MCPCMLType result = null;
		if(pattern instanceof MCAIdentifierPattern){
			result = this.getTypeFor((MCAIdentifierPattern)pattern);
		} 
		
		return result;
	}
	
	public MCPCMLType getTypeFor(MCPCommunicationParameter param){
		MCPCMLType result = null;
		if(param instanceof MCASignalCommunicationParameter){
			result = this.getTypeFor((MCASignalCommunicationParameter)param);
		} else if(param instanceof MCAReadCommunicationParameter){
			result = this.getTypeFor((MCAReadCommunicationParameter)param);
		} else if(param instanceof MCAWriteCommunicationParameter){
			result = this.getTypeFor((MCAWriteCommunicationParameter)param);
		}
		
		return result;
	}
	
	public MCPCMLType getTypeForIOComm(MCPCommunicationParameter param){
		MCPCMLType result = null;
		if(param instanceof MCASignalCommunicationParameter){
			result = this.getTypeForIOComm((MCASignalCommunicationParameter)param);
		} else if(param instanceof MCAReadCommunicationParameter){
			result = this.getTypeForIOComm((MCAReadCommunicationParameter)param);
		} else if(param instanceof MCAWriteCommunicationParameter){
			result = this.getTypeForIOComm((MCAWriteCommunicationParameter)param);
		}
		
		return result;
	}
	/*
	private MCPCMLType getTypeFor(MCATypeSingleDeclaration decl){
		MCPCMLType result = null;
		
		result = new MCANamedInvariantType(decl.getIdentifier());
		
		return result;
	}
	*/
	private MCPCMLType getTypeFor(MCALocalDefinition def){
		MCPCMLType result = null;
		
		result = new MCANamedInvariantType(def.getName());
		
		return result;
	}
	private MCPCMLType getTypeFor(MCPParametrisation param){
		MCPCMLType result = null;
		
		if(param instanceof MCAValParametrisation){
			result = this.getTypeFor((MCAValParametrisation)param);
		}
		
		return result;
	}
	
	public MCPCMLType instantiateMCType(LinkedList<MCPCMLExp> exps){
		MCPCMLType result = null;
		if(exps == null){
			result = new MCVoidType();
		}else {
			if(exps.size() == 0){
				result = new MCVoidType();
			} else if (exps.size() == 1){
				result = this.getTypeFor(exps.getFirst());
			} else if (exps.size() > 1){
				LinkedList<MCPCMLType> types = new LinkedList<MCPCMLType>();
				for (MCPCMLExp exp : exps) {
					types.add(instantiateMCType(exp));
				}
				result = new MCAProductType(types);
			}
		}
		
		return result;
	}
	
	
	
	public MCPCMLType instantiateMCTypeFromPatterns(LinkedList<MCPCMLPattern> patterns){
		MCPCMLType result = null;
		
		if(patterns.size() == 0){
			result = new MCVoidType();
		} else if (patterns.size() == 1){
			result = this.getTypeFor(patterns.getFirst());
		} else if (patterns.size() > 1){
			LinkedList<MCPCMLType> types = new LinkedList<MCPCMLType>();
			for (MCPCMLPattern pattern : patterns) {
				types.add(instantiateMCType(pattern));
			}
			result = new MCAProductType(types);
		}
		
		return result;
	}
	
	public MCPCMLType instantiateMCTypeFromTypes(LinkedList<MCPCMLType> types){
		MCPCMLType result = null;
		
		if(types.size() == 0){
			result = new MCVoidType();
		} else if (types.size() == 1){
			result = types.getFirst();
		} else if (types.size() > 1){
			result = new MCAProductType(types);
		}
		
		return result;
	}
	public MCPCMLType instantiateMCTypeFromParams(LinkedList<MCPParametrisation> params){
		MCPCMLType result = null;
		
		if(params.size() == 0){
			result = new MCVoidType();
		} else if (params.size() == 1){
			result = this.getTypeFor(params.getFirst());
		} else if (params.size() > 1){
			LinkedList<MCPCMLType> types = new LinkedList<MCPCMLType>();
			for (MCPParametrisation param : params) {
				types.add(instantiateMCType(param));
			}
			result = new MCAProductType(types);
		}
		
		return result;
	}
	
	public MCPCMLType instantiateMCTypeFromCommParams(LinkedList<MCPCommunicationParameter> params){
		MCPCMLType result = null;
		
		if(params.size() == 0){
			result = new MCVoidType();
		} else if (params.size() == 1){
			result = this.getTypeFor(params.getFirst());
		} else if (params.size() > 1){
			LinkedList<MCPCMLType> types = new LinkedList<MCPCMLType>();
			for (MCPCommunicationParameter param : params) {
				types.add(instantiateMCType(param));
			}
			result = new MCAProductType(types);
		}
		
		return result;
	}
	
	public MCPCMLType instantiateMCTypeFromCommParamsForIOCommDef(LinkedList<MCPCommunicationParameter> params, String suffix){
		MCPCMLType result = null;
		
		if(params.size() == 0){
			result = new MCVoidType();
		} else if (params.size() == 1){
			result = this.getTypeForIOComm(params.getFirst());
			addSuffixToVarName(result,suffix);
		} else if (params.size() > 1){
			LinkedList<MCPCMLType> types = new LinkedList<MCPCMLType>();
			for (MCPCommunicationParameter param : params) {
				MCPCMLType type = instantiateMCTypeForIOCommDef(param, suffix);
				//addSuffixToVarName(type,suffix);
				types.add(type);
			}
			result = new MCAProductType(types);
		}
		
		return result;
	}
	
	//it adds a suffix to a name type 
	private void addSuffixToVarName(MCPCMLType type,String suffix){
		if(type instanceof MCANamedInvariantType){
			((MCANamedInvariantType) type).setName(((MCANamedInvariantType) type).getName() + suffix);
		} 
	}
	
	public MCPCMLType instantiateMCTypeFromDefs(LinkedList<MCALocalDefinition> defs){
		MCPCMLType result = null;
		
		if(defs.size() == 0){
			result = new MCVoidType();
		} else if (defs.size() == 1){
			result = this.getTypeFor(defs.getFirst());
		} else if (defs.size() > 1){
			LinkedList<MCPCMLType> types = new LinkedList<MCPCMLType>();
			for (MCALocalDefinition decl : defs) {
				types.add(instantiateMCType((MCALocalDefinition)decl));
			}
			result = new MCAProductType(types);
		}
		
		return result;
	}
	
	public MCPCMLType instantiateMCType(MCPCMLExp exp){
		
		LinkedList<MCPCMLExp> exps = new LinkedList<MCPCMLExp>();
		exps.add(exp);
		
		return this.instantiateMCType(exps);
	}
	public MCPCMLType instantiateMCType(MCALocalDefinition def){
		
		LinkedList<MCALocalDefinition> defs = new LinkedList<MCALocalDefinition>();
		defs.add(def);
		
		return this.instantiateMCTypeFromDefs(defs);
	}
	public MCPCMLType instantiateMCType(MCPCommunicationParameter param){
		
		LinkedList<MCPCommunicationParameter> params = new LinkedList<MCPCommunicationParameter>();
		params.add(param);
		
		return this.instantiateMCTypeFromCommParams(params);
	}
	public MCPCMLType instantiateMCTypeForIOCommDef(MCPCommunicationParameter param, String suffix){
		
		LinkedList<MCPCommunicationParameter> params = new LinkedList<MCPCommunicationParameter>();
		params.add(param);
		
		return this.instantiateMCTypeFromCommParamsForIOCommDef(params, suffix);
	}
	/*
	public MCPCMLType instantiateMCType(MCATypeSingleDeclaration decl){
		
		LinkedList<MCATypeSingleDeclaration> decls = new LinkedList<MCATypeSingleDeclaration>();
		decls.add(decl);
		
		return this.instantiateMCTypeFromDecls(decls);
	}
	*/
	public MCPCMLType instantiateMCType(MCPCMLPattern pattern){
		
		LinkedList<MCPCMLPattern> patterns = new LinkedList<MCPCMLPattern>();
		patterns.add(pattern);
		
		return this.instantiateMCTypeFromPatterns(patterns);
	}
	public MCPCMLType instantiateMCType(MCPParametrisation params){
		
		LinkedList<MCPParametrisation> patterns = new LinkedList<MCPParametrisation>();
		patterns.add(params);
		
		return this.instantiateMCTypeFromParams(patterns);
	}
	
	private MCPCMLType getTypeFor(MCAIntLiteralExp exp){
		MCPCMLType result = null;
		
		result = new MCAIntNumericBasicType(exp.getValue());
		
		return result;
	}
	private MCPCMLType getTypeFor(MCAVariableExp exp){
		MCPCMLType result = null;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		NameValue mapping = context.getNameValue(exp.getName());
		//if there is a local variable with the same name assigned with a value, then use such a value
		if(mapping != null){
			result = new MCANamedInvariantType(mapping.getVariableValue());
		} else{
			result = new MCANamedInvariantType(exp.getName());
		}
		
		return result;
	}
	
	private MCPCMLType getTypeFor(MCAQuoteLiteralExp exp){
		MCPCMLType result = null;
		
		result = new MCANamedInvariantType(exp.getValue());
		
		return result;
	}
	
	private MCPCMLType getTypeFor(MCAIdentifierPattern exp){
		MCPCMLType result = new MCANamedInvariantType(exp.getName());;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		NameValue mapping = context.getNameValue(exp.getName());
		//if there is a local variable with the same name assigned with a value, then use such a value
		if(mapping != null){
			result = new MCANamedInvariantType(mapping.getVariableValue());
		} else{
			result = new MCANamedInvariantType(exp.getName());
		}
		
		return result;
	}
	private MCPCMLType getTypeForIOComm(MCAIdentifierPattern exp){
		MCPCMLType result = new MCANamedInvariantType(exp.getName());;
		
		return result;
	}
	private MCPCMLType getTypeFor(MCASignalCommunicationParameter param){
		MCPCMLType result = null;
		
		result = getTypeFor(param.getExpression());
		
		return result;
	}
	private MCPCMLType getTypeForIOComm(MCASignalCommunicationParameter param){
		MCPCMLType result = null;
		
		result = getTypeForIOComm(param.getExpression());
		
		return result;
	}
	private MCPCMLType getTypeFor(MCAValParametrisation param){
		MCPCMLType result = null;
		
		result = getTypeFor(param.getDeclaration());
		
		return result;
	}
	private MCPCMLType getTypeFor(MCAReadCommunicationParameter param){
		MCPCMLType result = null;
		
		result = getTypeFor((MCAIdentifierPattern)param.getPattern());
		
		return result;
	}
	private MCPCMLType getTypeForIOComm(MCAReadCommunicationParameter param){
		MCPCMLType result = null;
		
		result = getTypeForIOComm((MCAIdentifierPattern)param.getPattern());
		
		return result;
	}
	private MCPCMLType getTypeForIOComm(MCAWriteCommunicationParameter param){
		MCPCMLType result = null;
		
		result = getTypeForIOComm(param.getExpression());
		
		return result;
	}

	private MCPCMLType getTypeFor(MCAWriteCommunicationParameter param){
		MCPCMLType result = null;
		
		result = getTypeFor(param.getExpression());
		
		return result;
	}
	public String obtainValue(MCPCMLExp expression){
		String result = null;
		
		if(expression instanceof MCAIntLiteralExp){
			result = this.obtainValue((MCAIntLiteralExp)expression);
		} else if(expression instanceof MCAVariableExp){
			result = this.obtainValue((MCAVariableExp)expression);
		} else if(expression instanceof MCAUnaryMinusUnaryExp){
			result = this.obtainValue((MCAUnaryMinusUnaryExp)expression);
		}
		
		return result;
	}
	
	public MCPCMLExp getDefaultValue(MCPCMLType type){
		MCPCMLExp result = null;
		
		if(type instanceof MCAIntNumericBasicType){
			result = new MCAIntLiteralExp("0");
		} else if(type instanceof MCANatNumericBasicType){
			result = new MCAIntLiteralExp("0");
		} else if(type instanceof MCANamedInvariantType){
			NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
			MCATypeDefinition typeDef = context.getTypeDefinition(((MCANamedInvariantType) type).getName());
			LinkedList<MCPCMLExp> values = new LinkedList<MCPCMLExp>();
			if(typeDef.getInvExpression() != null){
				values = this.getValues(typeDef.getInvExpression());
			} else {
				TypeManipulator typeHandler = TypeManipulator.getInstance();
				LinkedList<TypeValue> typeValues = typeHandler.getValues(typeDef.getType());
				for (TypeValue typeValue : typeValues) {
					MCPCMLExp newExp = new MCAVariableExp(typeValue.toFormula(MCNode.DEFAULT));
					values.add(newExp);
				}
			}
			if(values.size() > 0){
				result = values.getFirst();
			}
		} else if(type instanceof MCABooleanBasicType){
			result = new MCABooleanConstExp(false);
		}
		
		return result;
	}
	
	private MCAIntLiteralExp getDefaultValue(MCANatNumericBasicType type){
		MCAIntLiteralExp result = null;
		
		result = new MCAIntLiteralExp("0");
		
		return result;
	}
	
	private MCAIntLiteralExp getDefaultValue(MCAIntNumericBasicType type){
		MCAIntLiteralExp result = null;
		
		result = new MCAIntLiteralExp("0");
		
		return result;
	}
	
	
	private String obtainValue(MCAIntLiteralExp expression){
		return expression.getValue();
	}
	
	private String obtainValue(MCAUnaryMinusUnaryExp expression){
		return "-" + obtainValue(expression.getExp());
	}

	private String obtainValue(MCAVariableExp expression){
		String result = null;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCAValueDefinition valueDef = context.getValueDefinition(((MCAVariableExp) expression).getName());
		if(valueDef != null){
			result = valueDef.getExpression().toFormula(MCNode.GENERIC);
		}

		return result;
	} 
	
	public boolean canEvaluate(MCAEqualsBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCANotEqualsBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCAGreaterEqualNumericBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCALessNumericBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCAGreaterNumericBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCALessEqualNumericBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}

	public boolean evaluate(MCAEqualsBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			String leftValue = ((MCAIntLiteralExp) left).getValue().toString();
			String rightValue = ((MCAIntLiteralExp) right).getValue().toString();
			resp = leftValue.equals(rightValue);
		} 
		return resp;
	}
	
	public boolean evaluate(MCANotEqualsBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			String leftValue = ((MCAIntLiteralExp) left).getValue().toString();
			String rightValue = ((MCAIntLiteralExp) right).getValue().toString();
			resp = !(leftValue.equals(rightValue));
		} 
		return resp;
	}
	
	public boolean evaluate(MCAGreaterEqualNumericBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
			Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
			resp = leftValue >= rightValue;
		} 
		return resp;
	}
	
	public boolean evaluate(MCALessNumericBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
			Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
			resp = leftValue < rightValue;
		} 
		return resp;
	}
	
	public boolean evaluate(MCAGreaterNumericBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
			Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
			resp = leftValue > rightValue;
		} 
		return resp;
	}
	
	public boolean evaluate(MCALessEqualNumericBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
			Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
			resp = leftValue <= rightValue;
		} 
		return resp;
	}
	
	@Override
	public boolean canEvaluate(MCPCMLExp expression) {
		boolean resp = false;
		if(expression instanceof MCAEqualsBinaryExp){
			resp = this.canEvaluate((MCAEqualsBinaryExp)expression);
		} else if(expression instanceof MCAGreaterEqualNumericBinaryExp){
			resp = this.canEvaluate((MCAGreaterEqualNumericBinaryExp)expression);
		} else if(expression instanceof MCANotEqualsBinaryExp){
			resp = this.canEvaluate((MCANotEqualsBinaryExp)expression);
		} else if(expression instanceof MCALessNumericBinaryExp){
			resp = this.canEvaluate((MCALessNumericBinaryExp)expression);
		} else if(expression instanceof MCALessEqualNumericBinaryExp){
			resp = this.canEvaluate((MCALessEqualNumericBinaryExp)expression);
		} else if(expression instanceof MCAGreaterNumericBinaryExp){
			resp = this.canEvaluate((MCAGreaterNumericBinaryExp)expression);
		} else if(expression instanceof MCAIntLiteralExp){
			resp = this.canEvaluate((MCAIntLiteralExp)expression);
		} else if(expression instanceof MCAVariableExp){
			resp = this.canEvaluate((MCAVariableExp)expression);
		} else if(expression instanceof MCANotUnaryExp){
			resp = this.canEvaluate((MCANotUnaryExp)expression);
		} else if(expression instanceof MCAApplyExp){
			resp = this.canEvaluate((MCAApplyExp)expression);
		}
		return resp;
	}

	public boolean canEvaluate(MCAIntLiteralExp expression){
		return true;
	}

	public boolean canEvaluate(MCAVariableExp expression){
		boolean result = false;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCAValueDefinition valueDef = context.getValueDefinition(((MCAVariableExp) expression).getName());
		if(valueDef != null){
			result = true;
		}
		return result;
	}
	
	public boolean canEvaluate(MCANotUnaryExp expression){
		boolean result = false;
		result = canEvaluate(expression.getExp());
		return result;
	}
	
	public boolean canEvaluate(MCAApplyExp expression){
		boolean result = true;
		for (MCPCMLExp exp : expression.getArgs()) {
			if(!canEvaluate(exp)){
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	@Override
	public boolean evaluate(MCPCMLExp expression) {
		boolean resp = false;
		if(expression instanceof MCAEqualsBinaryExp){
			resp = this.evaluate((MCAEqualsBinaryExp)expression);
		} else if(expression instanceof MCAGreaterEqualNumericBinaryExp){
			resp = this.evaluate((MCAGreaterEqualNumericBinaryExp)expression);
		} else if(expression instanceof MCANotEqualsBinaryExp){
			resp = this.evaluate((MCANotEqualsBinaryExp)expression);
		} else if(expression instanceof MCALessNumericBinaryExp){
			resp = this.evaluate((MCALessNumericBinaryExp)expression);
		} else if(expression instanceof MCALessEqualNumericBinaryExp){
			resp = this.evaluate((MCALessEqualNumericBinaryExp)expression);
		} else if(expression instanceof MCAGreaterNumericBinaryExp){
			resp = this.evaluate((MCAGreaterNumericBinaryExp)expression);
		} 
		return resp;
	}

	public LinkedList<MCPCMLExp> getValues(MCPCMLExp expression){
		LinkedList<MCPCMLExp> result = new LinkedList<MCPCMLExp>();
		
		if(expression instanceof MCASetRangeSetExp){
			result = this.getValues((MCASetRangeSetExp)expression);
		} else if(expression instanceof MCAInSetBinaryExp){
			result = this.getValues((MCAInSetBinaryExp)expression);
		}
			
		return result;
	}
	
	private LinkedList<MCPCMLExp> getValues(MCASetRangeSetExp expression){
		LinkedList<MCPCMLExp> result = new LinkedList<MCPCMLExp>();
		
		//it gets the first and the last (real) values
		String firstValue = this.obtainValue(expression.getFirst());
		String lastValue = this.obtainValue(expression.getLast());
		if(firstValue != null && lastValue != null){
			Integer firstValueInt = Integer.valueOf(firstValue);
			Integer lastValueInt = Integer.valueOf(lastValue);
			
			int currValue = firstValueInt;
			while(currValue <= lastValueInt){
				result.add(new MCAIntLiteralExp(String.valueOf(currValue)));
				currValue++;
			}
		}
			
		return result;
	}
	
	private LinkedList<MCPCMLExp> getValues(MCAInSetBinaryExp expression){
		LinkedList<MCPCMLExp> result = new LinkedList<MCPCMLExp>();
		
		if(expression.getRight() instanceof MCASetRangeSetExp){
			result = this.getValues((MCASetRangeSetExp)expression.getRight()); 
		}
		
		return result; 
	}
	//some methods for evaluating expressions and obtaining a set of all possible values
	//this is useful to find out the explicit values of types defined by some conditions
	public LinkedList<String> getValueSet(MCPCMLExp expression){
		LinkedList<String> result = new LinkedList<String>();
		
		if(expression instanceof MCASetRangeSetExp){
			result = this.getValueSet((MCASetRangeSetExp)expression);
		} else if(expression instanceof MCAInSetBinaryExp){
			result = this.getValueSet((MCAInSetBinaryExp)expression);
		}
			
		return result;
	}
	
	public LinkedList<String> getValueSet(MCASetRangeSetExp expression){
		LinkedList<String> result = new LinkedList<String>();
		//it gets the first and the last (real) values
		String firstValue = this.obtainValue(expression.getFirst());
		String lastValue = this.obtainValue(expression.getLast());
		if(firstValue != null && lastValue != null){
			Integer firstValueInt = Integer.valueOf(firstValue);
			Integer lastValueInt = Integer.valueOf(lastValue);
			
			int currValue = firstValueInt;
			while(currValue <= lastValueInt){
				result.add(String.valueOf(currValue));
				currValue++;
			}
		}
		return result;
	}
	
	public LinkedList<String> getValueSet(MCAInSetBinaryExp expression){
		LinkedList<String> result = new LinkedList<String>();
		
		if(expression.getRight() instanceof MCASetRangeSetExp){
			result = this.getValueSet((MCASetRangeSetExp)expression.getRight()); 
		}
		
		return result; 
	}
	
	public LinkedList<PatternValue> buildPatternValueList(LinkedList<MCPCMLPattern> patterns, LinkedList<MCPCMLExp> exprs){
		LinkedList<PatternValue> result = new LinkedList<PatternValue>();
		int i = 0;
		for (MCPCMLPattern pat : patterns) {
			if(pat instanceof MCAIdentifierPattern){
				MCPCMLExp value = exprs.get(i);
				result.add(new PatternValue(pat,value));
				i++;
			}
		}
		
		return result;
	}
}
