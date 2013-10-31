package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCChannel;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionsDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAAssignmentDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelNameDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelsDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAOperationsDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAStateDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypesDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAUntypedDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValuesDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAFieldField;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.SingleBind;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Str;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.UndefinedValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;

public class NewMCDeclarationAndDefinitionVisitor extends
		QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;
	
	public NewMCDeclarationAndDefinitionVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	//FOR THE CASE OF DEFINITIONS AND DECLARATIONS WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public MCNode defaultPCMLDefinition(PCMLDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
		
	@Override
	public MCNode defaultPSingleDeclaration(PSingleDeclaration node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	//// DEFINITIONS
	@Override
	public MCNode caseAProcessDefinition(AProcessDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCATypeSingleDeclaration> localState = new LinkedList<MCATypeSingleDeclaration>();
		for (ATypeSingleDeclaration aTypeSingleDeclaration : node.getLocalState()) {
			localState.add((MCATypeSingleDeclaration) aTypeSingleDeclaration.apply(this, question));
		}
		MCPProcess process = (MCPProcess) node.getProcess().apply(rootVisitor, question); 
		String name = node.getName().toString();
		MCAProcessDefinition result = new MCAProcessDefinition(name,localState, process);
		
		return result;
		
	}

	
	@Override
	public MCNode caseAActionsDefinition(AActionsDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		// it applies to each internal definition
		LinkedList<AActionDefinition> actions = node.getActions();
		LinkedList<MCAActionDefinition> mcActions = new LinkedList<MCAActionDefinition>();
		for (AActionDefinition currentActionDefinition : actions) {
			MCAActionDefinition mcActionDef = (MCAActionDefinition) currentActionDefinition.apply(this, question);
			question.localActions.add(mcActionDef);
			mcActions.add(mcActionDef);
		}
		
		MCAActionsDefinition result = new MCAActionsDefinition(mcActions);
		return result;
	}
	
	
	@Override
	public MCNode caseAActionDefinition(AActionDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		LinkedList<PParametrisation> parameters = node.getDeclarations();
		LinkedList<MCPParametrisation> mcParameters = new LinkedList<MCPParametrisation>();
		for (PParametrisation pParametrisation : parameters) {
			mcParameters.add((MCPParametrisation) pParametrisation.apply(rootVisitor, question));
		}
		MCPAction action = (MCPAction) node.getAction().apply(rootVisitor, question);
		String name = node.getName().toString();
		MCAActionDefinition result = new MCAActionDefinition(name, mcParameters, action);
		
		return result;
	}
	
	@Override
	public MCNode caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		LinkedList<String> identifiers = new LinkedList<String>();
		LinkedList<ILexIdentifierToken> ids = node.getIdentifiers();
		PExp expr = node.getExpression();
		MCPCMLExp expression;
		for(ILexIdentifierToken i : ids){
			identifiers.add(i.toString());
		}
		expression = (MCPCMLExp) expr.apply(rootVisitor, question);
		return new MCAExpressionSingleDeclaration(identifiers, expression);
	}

	
	@Override
	public MCNode caseAAssignmentDefinition(AAssignmentDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLType type = (MCPCMLType) node.getType().apply(rootVisitor, question);;
		MCPCMLExp expr = null;
		if(node.getExpression() != null) {
			expr = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		}
		MCPCMLType expType = null;
		if(type instanceof MCAIntNumericBasicType){
			expType = new MCAIntNumericBasicType();
		}		
		
		String varName = node.getName().toString();
		Type varValue = new UndefinedValue();
		if(expr != null){
			//this has to be improved to instantiate values of the suitable type of the expression.
			ExpressionEvaluator evaluator = new ExpressionEvaluator();
			String varValueString = evaluator.obtainValue(expr);
			varValue = new Str(varValueString);
		}
		question.maximalBinding = question.maximalBinding.addBinding("nP", varName, varValue);
		
		return new MCAAssignmentDefinition(varName, expr, expType);
	}

	@Override
	public MCNode caseAStateDefinition(AStateDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCPCMLDefinition> stateDefs = new LinkedList<MCPCMLDefinition>(); 
		
		for (PDefinition pDefinition : node.getStateDefs()) {
			stateDefs.add((MCPCMLDefinition) pDefinition.apply(this, question));
		}
		MCAStateDefinition result = new MCAStateDefinition(stateDefs, null, null, null, null, null);
		
		return result;
	}

	@Override
	public MCNode caseAChannelsDefinition(AChannelsDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCAChannelNameDefinition> chanNameDecls = new LinkedList<MCAChannelNameDefinition>(); 
		for (AChannelNameDefinition chanDef : node.getChannelNameDeclarations()) {
			chanNameDecls.add((MCAChannelNameDefinition) chanDef.apply(this, question));
		}
		MCAChannelsDefinition result = new MCAChannelsDefinition(chanNameDecls);
		
		return result;
	}
	@Override
	public MCNode caseAChannelNameDefinition(
			AChannelNameDefinition node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCATypeSingleDeclaration singleType = (MCATypeSingleDeclaration) node.getSingleType().apply(rootVisitor, question);
		MCAChannelNameDefinition result = new MCAChannelNameDefinition(singleType);
		
		for (MCChannel channDef : result.getChannelDefs()) {
			question.channelDefs.add(channDef);
		}

		return result;
		
	}

	@Override
	public MCNode caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLType type = null;
		LinkedList<String> identifiers = new LinkedList<String>();
		for (ILexIdentifierToken identifier : node.getIdentifiers()) {
			identifiers.add(identifier.toString());
		}
		if(node.getType() != null){
			type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		}
		MCATypeSingleDeclaration result = new MCATypeSingleDeclaration(identifiers, type);

		return result;
	}

	@Override
	public MCNode caseAOperationsDefinition(AOperationsDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		LinkedList<SCmlOperationDefinition> operations = node.getOperations();
		LinkedList<MCSCmlOperationDefinition> mcOperations = new LinkedList<MCSCmlOperationDefinition>();
		for (SCmlOperationDefinition currentOperationDefinition : operations) {
			mcOperations.add((MCAExplicitCmlOperationDefinition) currentOperationDefinition.apply(this, question));
		}
		MCAOperationsDefinition result = new MCAOperationsDefinition(mcOperations);
		
		return result;
	}

	@Override
	public MCNode caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = node.getName().toString();
		MCPAction body = (MCPAction) node.getBody().apply(rootVisitor, question);
		LinkedList<MCPCMLDefinition> mcParamDefinitions = new LinkedList<MCPCMLDefinition>();
		for (PDefinition pDef : node.getParamDefinitions()) {
			mcParamDefinitions.add((MCPCMLDefinition) pDef.apply(this, question));
		}
		
		MCAExplicitCmlOperationDefinition result = 
				new MCAExplicitCmlOperationDefinition(name,body,null,null,null,null,mcParamDefinitions,null,null,null);
		
		question.operations.add(result);
		
		return result;
		
	}
	
	@Override
	public MCNode caseAValuesDefinition(AValuesDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCPCMLDefinition> mcValueDefs = new LinkedList<MCPCMLDefinition>();
		for (PDefinition pDef : node.getValueDefinitions()) {
			mcValueDefs.add((MCPCMLDefinition) pDef.apply(this, question));
		}
		  
		MCAValuesDefinition result = new MCAValuesDefinition(mcValueDefs);
		
		return result;
	}
	
	@Override
	public MCNode caseAValueDefinition(AValueDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = node.getPattern().toString();
		LinkedList<MCPCMLDefinition> definitions = new LinkedList<MCPCMLDefinition>();
		for (PDefinition pDef : node.getDefs()) {
			definitions.add((MCPCMLDefinition) pDef.apply(this, question));
		}
		MCPCMLType type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		MCPCMLExp expression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		MCAValueDefinition result = new MCAValueDefinition(name, definitions, expression,type);

		question.valueDefinitions.add(result);
		
		return result;
	}
	
	
	
	@Override
	public MCNode caseAUntypedDefinition(AUntypedDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCAUntypedDefinition result = new MCAUntypedDefinition(node.getName().toString());
		
		return result;
	}
	
	@Override
	public MCNode caseATypesDefinition(ATypesDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCATypeDefinition> mcTypes = new LinkedList<MCATypeDefinition>();
		for (ATypeDefinition aTypeDef : node.getTypes()) {
			mcTypes.add((MCATypeDefinition) aTypeDef.apply(this, question));
		}
		MCATypesDefinition result = new MCATypesDefinition(mcTypes);
		
		return result;
	}

	@Override
	public MCNode caseATypeDefinition(ATypeDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		
		String name = node.getName().toString();
		MCPCMLExp invExpression = (MCPCMLExp) node.getInvExpression().apply(rootVisitor, question);
		MCATypeDefinition result = new MCATypeDefinition(name, invExpression);
		
		question.typeDefinitions.add(result);
		
	
		return result;
	}
	
	/*
	
	//// DECLARATIONS
	
	//AUXILIARY METHODS
	private void generateUserTypeDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		if(context.typeDefinitions.size() > 0){
			//LinkedList<ATypeDefinition> t = (LinkedList<ATypeDefinition>) question.info.get(Utilities.TYPE_USER_DEFINITION);
			LinkedList<UserTypeDefinition> types = context.typeDefinitions;
			String s = "//USER_DEF_TYPES";
			String s2 = "//INCLUDE USER_DEF_TYPES ";
			int aux = context.basicContent.indexOf(s);
			CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
			StringBuilder str2 = new StringBuilder();
			while(!types.isEmpty()){
				UserTypeDefinition type = types.pollFirst();
				ATypeDefinition typeDef = type.typeDefinition;
				//typeDef.apply(this, auxCtxt);
				auxCtxt.getScriptContent().append("primitive ");
				auxCtxt.getScriptContent().append(typeDef.getName().toString());
				auxCtxt.getScriptContent().append(" ::= (");
				//auxCtxt.getScriptContent().append(typeDef.getType().toString());
				//ppppppppppppp
				auxCtxt.getScriptContent().append("Natural");
				auxCtxt.getScriptContent().append(").\n");
				
				str2.append(" + " + typeDef.getInvType().toString());
			}
			str2.append(" ");
			//str2.append(".\n");
			context.basicContent.replace(aux, aux+s.length(), "");
			context.basicContent.replace(aux, aux+1, auxCtxt.getScriptContent().toString());
			int aux2 = context.basicContent.indexOf(s2);
			context.basicContent.replace(aux2, aux2+s2.length(), "");
			context.basicContent.replace(aux2, aux2+1, str2.toString());
		}
	}

	private void generateChannelTypes(CMLModelcheckerContext context) throws AnalysisException{
		LinkedList<ChannelTypeDefinition> channels = context.channelDefinitions;
		LinkedList<String> createdChanTypes = new LinkedList<String>();
		CMLModelcheckerContext auxctxt = new CMLModelcheckerContext();
		for (ChannelTypeDefinition aChanTypeDef : channels) {
			ATypeSingleDeclaration typeSingleDecl = aChanTypeDef.chanDef.getSingleType();
			PType type = typeSingleDecl.getType();
			
			if(type instanceof AChannelType){
				StringBuilder chanTypeBuilder = new StringBuilder();
				PType realtype = ((AChannelType) type).getType();
				
				//if it is AChannelType (with internal type null) = non typed channel
				if (realtype instanceof AChannelType){
					if(((AChannelType) realtype).getType() == null){
						//no channel type to create
					}
				}
				//if it is AProductType
				if(realtype instanceof AProductType){
					auxctxt.getScriptContent().append("primitive ");
					auxctxt.getScriptContent().append("ChanType" + auxctxt.CHANTYPE_COUNTER);
					createdChanTypes.add("ChanType" + auxctxt.CHANTYPE_COUNTER);
					auxctxt.getScriptContent().append(" ::= (");
					realtype.apply(rootVisitor, auxctxt);
					auxctxt.getScriptContent().append(").\n");
				}
				//if it is ANamedInvariantType
				if (realtype instanceof ANamedInvariantType){
					auxctxt.getScriptContent().append("primitive ");
					auxctxt.getScriptContent().append("ChanType" + auxctxt.CHANTYPE_COUNTER);
					createdChanTypes.add("ChanType" + auxctxt.CHANTYPE_COUNTER);
					auxctxt.getScriptContent().append(" ::= (");
					realtype.apply(rootVisitor, auxctxt);
					auxctxt.getScriptContent().append(").\n");
				}
				//if it is AIntNumericBasicType
				if (realtype instanceof AIntNumericBasicType){
					//channel of int type are already manipulated 
					
					//auxctxt.getScriptContent().append("primitive ");
					//auxctxt.getScriptContent().append("ChanType" + auxctxt.CHANTYPE_COUNTER);
					//createdChanTypes.add("ChanType" + auxctxt.CHANTYPE_COUNTER);
					//auxctxt.getScriptContent().append(" ::= (");
					//realtype.apply(this, auxctxt);
					//auxctxt.getScriptContent().append(").\n");
					
				}
				
				context.CHANTYPE_COUNTER++;
			}
		}
		
		if(createdChanTypes.size() == 0){
			//nothing to do
		}else if (createdChanTypes.size() == 1){ //do not use type union
			//chan
			String s = "//CHAN_TYPES";
			String s2 = "//UNION_CHAN_TYPES";
			int aux = context.basicContent.indexOf(s);
			context.basicContent.replace(aux, aux+s.length(), "");
			context.basicContent.replace(aux, aux+1, auxctxt.getScriptContent().toString());
			
			int aux2 = context.basicContent.indexOf(s2);
			context.basicContent.replace(aux2, aux2+s2.length(), "");
			context.basicContent.replace(aux2, aux2+1, " + " + createdChanTypes.getFirst());
		}
		if(createdChanTypes.size() > 1){ //we have to use type union 
				
			String s = "//CHAN_TYPES";
			String s2 = "UNION_CHAN_TYPES";
			int aux = context.basicContent.indexOf(s);
			StringBuilder str2 = new StringBuilder();
			str2.append("ChannTypes ::= ");
			while(!createdChanTypes.isEmpty()){
				String currChanType = createdChanTypes.pollFirst();
				str2.append(currChanType);
				if(!createdChanTypes.isEmpty()){
					str2.append(" + ");
				}
			}
			str2.append(".\n");
			auxctxt.getScriptContent().append(str2);
			context.basicContent.replace(aux, aux+s.length(), "");
			context.basicContent.replace(aux, aux+1, auxctxt.getScriptContent().toString() + "\n");
	
			int aux2 = context.basicContent.indexOf(s2);
			context.basicContent.replace(aux2, aux2+s2.length(), "");
			context.basicContent.replace(aux2, aux2 + 1, "+ ChannTypes.");
		} 
	}
	/*
	private void generateOperationsDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		Binding maximalBinding = context.getMaxBindingWithStates();
		for (SCmlOperationDefinition operation : context.operations) {
			AExplicitCmlOperationDefinition op = (AExplicitCmlOperationDefinition) operation;
			StringBuilder opStr = new StringBuilder();
			opStr.append("operationDef(");
			opStr.append("\"" + op.getName().toString() + "\"");
			opStr.append(",");
			if(op.getParamDefinitions().size()==0){
				opStr.append("nopar");
			}else if(op.getParamDefinitions().size()==1){
				PDefinition pDef = op.getParamDefinitions().getFirst();
				CMLModelcheckerContext newCtxt = new CMLModelcheckerContext();
				StringBuilder result = pDef.apply(rootVisitor,newCtxt);
				opStr.append(result.toString());
			}
			opStr.append(",");
			//write the original maximal bind
			//opStr.append(maximalBinding.toFormula());
			opStr.append("st");
			opStr.append(",");
			//writes the maximal bind modified. it is good to make a copy and modify it
			Binding maximalCopy = maximalBinding.copy();
			//modificar o value da variavel 
			PAction body = op.getBody();
			ASingleGeneralAssignmentStatementAction actionBody = null;
			if(body instanceof ASingleGeneralAssignmentStatementAction){
				actionBody = (ASingleGeneralAssignmentStatementAction) body;
			}

			if(actionBody != null){
				PExp stateDesignator = actionBody.getStateDesignator();
				if(stateDesignator instanceof AVariableExp){
					AVariableExp nextStateDesignator = (AVariableExp) stateDesignator.clone();
					String module = ((AVariableExp) stateDesignator).getName().getModule();
					String nextName = ((AVariableExp) stateDesignator).getName().getName() + "_";
					ILexLocation location = stateDesignator.getLocation();
					LexNameToken nextNameToken = new LexNameToken(module,nextName,location);
					CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
					StringBuilder nextValue = stateDesignator.apply(rootVisitor,localCtxt);
					nextValue.replace(nextValue.indexOf("(")+1, nextValue.indexOf(")"), nextName);
					//we assume that only Int() are used here
					Int newValue = new Int(0);
					newValue.setS(nextName);
					maximalCopy.updateBinding(((AVariableExp) stateDesignator).getName().toString(), newValue);
				}
			}
			//depois verificar se rpecisa gerar o complemento RHS para essa operacao.
			//opStr.append(maximalCopy.toFormulaWithState());
			opStr.append("st_");
			opStr.append(")");

			opStr.append(" :- ");

			//put the state information
			StringBuilder stateFact = new StringBuilder();
			stateFact.append("State(l,st,_,operation(\"" + op.getName().toString() + "\",");
			if(op.getParamDefinitions().size()==0){
				stateFact.append("nopar");
			}else if(op.getParamDefinitions().size()==1){
				PDefinition pDef = op.getParamDefinitions().getFirst();
				CMLModelcheckerContext newCtxt = new CMLModelcheckerContext();
				StringBuilder result = pDef.apply(rootVisitor,newCtxt);
				stateFact.append(result.toString());
			}
			stateFact.append("))");
			opStr.append(stateFact);
			opStr.append(",");
			//put the st = maximal bind information
			opStr.append("st = " + maximalBinding.toFormulaWithState());
			opStr.append(",");
			//put the next bind information
			opStr.append("st_ = " + maximalCopy.toFormulaWithState());
			opStr.append(",");

			//put the expression
			CMLModelcheckerContext newCtxt = new CMLModelcheckerContext();

			if(actionBody != null){
				newCtxt.getScriptContent().append(((AVariableExp) actionBody.getStateDesignator()).getName().getName() + "_" + " = ");
				String expression = actionBody.getExpression().apply(rootVisitor, newCtxt).toString();
				opStr.append(expression);

			}
			opStr.append(".\n");

			//generate the preconditionOK e preconditioNOk
			//"preOpOk("Init", nopar, BBinding(SingleBind("a",Int(a)),nBind)) :- State(BBinding(SingleBind("a",Int(a)),nBind),_)."
			//PExp exp = actionBody.getExpression();
			if(op.getPrecondition() != null){
				//convert precondition
			}else{
				//we assume that all preconditions are true for the moment
				StringBuilder preOpOkFact = new StringBuilder();
				preOpOkFact.append("preOpOk(\"" + op.getName().toString() + "\",");
				if(op.getParamDefinitions().size()==0){
					preOpOkFact.append("nopar");
				}else if(op.getParamDefinitions().size()==1){
					PDefinition pDef = op.getParamDefinitions().getFirst();
					CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
					StringBuilder result = pDef.apply(rootVisitor,localCtxt);
					preOpOkFact.append(result.toString());
				}
				preOpOkFact.append(",");
				preOpOkFact.append(maximalBinding.toFormulaWithState());
				preOpOkFact.append(")");
				preOpOkFact.append(" :- ");
				preOpOkFact.append("State(l," + maximalBinding.toFormulaWithState() + ",np,_).");
				preOpOkFact.append("\n");
				opStr.append(preOpOkFact.toString());
			}
			//we assume that post conditions are not present

			//puts the oepration definition and the precondition
			context.getScriptContent().append(opStr);
		}
	} 
	
	private void generateIOCommDefs(CMLModelcheckerContext context){
		//if(question.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
		if(context.ioCommDefs.size() > 0){
			Binding maximalBinding = context.getMaxBinding();
			//if(context.info.containsKey(Utilities.STATES_KEY)){
			//if(context.stateVariables.size() > 0){
			//	StringBuilder s = new StringBuilder(context.getScriptContent().toString());
			//	s.replace(0, s.indexOf("nopar,"), "");
			//	String ss = s.substring("nopar,".length(), s.indexOf(" :"));

			//	StringBuilder io = new StringBuilder(context.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
			//	io.append(ss+".\n");
			//context.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), io.toString());
			//context.getScriptContent().append(io.toString());
			//} else{
			StringBuilder ioCommDefs = new StringBuilder();
			for (String ioCommDef : context.ioCommDefs) {
				ioCommDef = ioCommDef.replaceAll(Utilities.MAX_BIND, maximalBinding.toFormula());
				ioCommDefs.append(ioCommDef + "\n");
			}
			//question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
			//context.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), ioCommDefs.toString());
			context.getScriptContent().append(ioCommDefs.toString());
			//}

		}

	}
	
	private void generateGuardDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		//for all guard conditions found in the specification
		for (Condition condition : context.guards) {
			CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
			condition.expression.apply(rootVisitor,context);

			Binding maxBinding = context.getMaxBinding();

			//generating positive guards
			String positiveGuardExp = context.positiveGuardExps.get(condition.expression);
			StringBuilder positiveGuardBuilder = new StringBuilder();
			//context.getScriptContent().append(condResult.toString());
			if(positiveGuardExp != null){
				positiveGuardBuilder.append("  guardDef(l," + condition.counter + "," + maxBinding.toFormulaWithState() + ")");
				positiveGuardBuilder.append(" :- State(l," + maxBinding.toFormulaWithState() + "," +maxBinding.getProcName()+ ",condChoice(" + condition.counter + ",_,_))");				positiveGuardBuilder.append(",");
				positiveGuardBuilder.append(positiveGuardExp);
				positiveGuardBuilder.append(".\n");
			}


			//generating negative guards
			String negativeGuardExp = context.negativeGuardExps.get(condition.expression);
			StringBuilder negativeGuardBuilder = new StringBuilder();
			//context.getScriptContent().append(condResult.toString());
			if(negativeGuardExp != null){
				negativeGuardBuilder.append("  guardNDef(l," + condition.counter + "," + maxBinding.toFormulaWithState() + ")");
				negativeGuardBuilder.append(" :- State(l," + maxBinding.toFormulaWithState() + "," +maxBinding.getProcName()+ ",condChoice(" + condition.counter + ",_,_))");
				negativeGuardBuilder.append(",");
				negativeGuardBuilder.append(negativeGuardExp);
				negativeGuardBuilder.append(".\n");
			}

			context.getScriptContent().append(positiveGuardBuilder.toString());
			context.getScriptContent().append(negativeGuardBuilder.toString());
			

		}
	}

	
	private void generateValueDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		LinkedList<UserDefinedValue> userValues = context.valueDefinitions;
		for (UserDefinedValue userDefinedValue : userValues) {
			AValueDefinition def = userDefinedValue.getValueDef();
			if(def.getExpression() instanceof AIntLiteralExp){
				context.getScriptContent().append("primitive ");
			} 
			context.getScriptContent().append(def.getPattern().toString()+" ::= (");		
			def.getType().apply(rootVisitor, context);
			context.getScriptContent().append(").\n");
			if(def.getExpression() instanceof ATimesNumericBinaryExp){
				context.getScriptContent().append(def.getPattern().toString());
				//context.getScriptContent().append("(" + def.getPattern().toString().toLowerCase() + ") :- #, " + def.getPattern().toString().toLowerCase()+" = @");
				context.getScriptContent().append("(" + def.getPattern().toString().toLowerCase() + ") :- #, " + def.getPattern().toString().toLowerCase()+" = @");
				def.getExpression().apply(rootVisitor, context);
				context.getScriptContent().append(".\n");
			}
		}
		
	}
	
	private void generateLieInFacts(CMLModelcheckerContext context){
		if(context.lieIn.size() != 0){
			context.getScriptContent().append("\n");
			for (String lieIn : context.lieIn) {
				context.getScriptContent().append(lieIn + "\n");
			}
		}
	}
	
	private void generateBindingFacts(CMLModelcheckerContext context){
		//generate fetch facts
		Binding maxBinding = context.getMaxBinding();
		context.getScriptContent().append(maxBinding.generateAllFetchFacts(context.numberOfFetchFacts));
		
		//generate upd facts
		
		
		//generate del facts
	}
	
	private void generateChannelDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		for (ChannelTypeDefinition channelDef : context.channelDefinitions) {
			channelDef.chanDef.apply(rootVisitor, context);
			context.getScriptContent().append("\n");
		}
	}
	*/
	@Override
	public MCNode createNewReturnValue(INode node,
			NewCMLModelcheckerContext question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public MCNode createNewReturnValue(Object node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
}
