package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;

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

	/*
	@Override
	public MCNode caseAActionsDefinition(AActionsDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		// it applies to each internal definition
		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition currentActionDefinition : actions) {
			question.localActions.add(currentActionDefinition);
			currentActionDefinition.apply(this, question);
		}
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAActionDefinition(AActionDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.getScriptContent().append("  ProcDef(");
		question.getScriptContent().append("\"" + node.getName() + "\",");
		// parameters
		
		LinkedList<PParametrisation> parameters = node.getDeclarations();
		
		if(parameters.size()==0){
			question.getScriptContent().append("nopar");
			question.getScriptContent().append(",");
			
			
			// it converts the internal action (body)
			//node.getAction().apply(this, question);
			
			node.getAction().apply(rootVisitor,question);
			question.getScriptContent().append(")");
			//if(!question.info.containsKey(Utilities.STATES_KEY)){
			//	question.getScriptContent().append(").\n");
			//}
		} else if(parameters.size()==1){
			question.getScriptContent().append("SPar(");
			node.getDeclarations().getFirst().apply(rootVisitor, question);
			question.getScriptContent().append("),");
			//node.getAction().apply(this, question);
			node.getAction().apply(rootVisitor, question);
			question.getScriptContent().append(")");
		}
		
		//if the action has dependencies we get them from the context
		if(question.channelDependencies.size() > 0){
			question.getScriptContent().append(" :- ");
			Iterator<String> it = question.channelDependencies.iterator(); 
			do{
				String channelDep = it.next();
				question.getScriptContent().append(channelDep);
				if(it.hasNext()){
					question.getScriptContent().append(", ");
				}
			}while(it.hasNext());
			
		}
		
		question.getScriptContent().append(".\n");

		return question.getScriptContent();
	}
	

	@Override
	public StringBuilder caseAChannelsDefinition(AChannelsDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		//question.getScriptContent().append("Channel(0,\"");
		//node.getChannelNameDeclarations().getFirst().apply(this, question);
		//question.getScriptContent().append(")");
		for (AChannelNameDefinition chanDef : node.getChannelNameDeclarations()) {
			ChannelTypeDefinition newChanTypeDef = new ChannelTypeDefinition(chanDef);
			question.channelDefinitions.add(newChanTypeDef);
		}
		
		//question.info.put(Utilities.CHANNEL_DEFINITIONS_KEY, node.getChannelNameDeclarations());		
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAChannelNameDefinition(
			AChannelNameDefinition node, CMLModelcheckerContext question)
			throws AnalysisException {
		
		if( ((AChannelType)node.getSingleType().getType()).getType() != null){
			question.getScriptContent().append("Channel(0,\"");
			String type = node.getSingleType().getType().toString();
			type = type.substring(0,1).toUpperCase().concat(type.substring(1));
			question.getScriptContent().append(node.getSingleType().getIdentifiers().getFirst().getName()+"\",");
			question.getScriptContent().append(type);
			//if there is a fixed initial value it has to be used here
			question.getScriptContent().append("(_))");
		}
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAValuesDefinition(AValuesDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		//question.info.put(Utilities.VALUE_DEFINITIONS_KEY, node.getValueDefinitions());
		
		
		for (PDefinition valueDef : node.getValueDefinitions()) {
			//valueDef.apply(this, question);
			valueDef.apply(this, question);
		}
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAValueDefinition(AValueDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.valueDefinitions.add(new UserDefinedValue(node));
		//lets put the information in the context to be processed as bellow in the partial 
		//model construction.
		 
		//if(node.getExpression() instanceof AIntLiteralExp){
		 
		//	question.getScriptContent().append("  primitive ");
		//} else{
		//	question.getScriptContent().append("  ");
		//}
		//question.getScriptContent().append(node.getPattern().toString()+" ::= (");		
		//node.getType().apply(this, question);
		//question.getScriptContent().append(").\n");
		//if(node.getExpression() instanceof ATimesNumericBinaryExp){
		//	question.getScriptContent().append("  "+node.getPattern().toString()+"("+node.getPattern().toString().toLowerCase()+") :- #, "+node.getPattern().toString().toLowerCase()+" = @");
		//	node.getExpression().apply(this, question);
		//	question.getScriptContent().append(".\n");
		//}
		
		return question.getScriptContent();
	}
	
	
	@Override
	public StringBuilder caseATypesDefinition(ATypesDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		for (ATypeDefinition typeDef : node.getTypes()) {
			//typeDef.apply(this, question);
			typeDef.apply(this, question);
		}
		//question.info.put(Utilities.TYPE_USER_DEFINITION, node.getTypes());
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseATypeDefinition(ATypeDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.typeDefinitions.add(new UserTypeDefinition(node));
		//node.getInvType().apply(this, question);
		//question.getScriptContent().append(".\n");
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAOperationsDefinition(AOperationsDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		LinkedList<SCmlOperationDefinition> operations = node.getOperations();
		for (SCmlOperationDefinition currentOperationDefinition : operations) {
			currentOperationDefinition.apply(this, question);
		}
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		//it builds the operation definition in formula and put it into the context
		question.operations.add(node); 
		
		return question.getScriptContent();
	}
	@Override
	public StringBuilder caseAAssignmentDefinition(AAssignmentDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.getScriptContent().append("var(\""+node.getName().toString()+"\",\"");
		
		//node.getType().apply(this, question);
		node.getType().apply(rootVisitor, question);
		question.getScriptContent().append("\",");
		//question.updateVariables(node.getName().toString());
		//question.info.put(Utilities.VAR_DECLARATIONS_KEY, "add");
		//puts the local variable in the bindings
		String varName = node.getName().toString();
		//Type varValue = new UndefinedValue();
		Int varValue = new Int(0);
		varValue.setS(varName);
		SingleBind newBind = new SingleBind(varName,varValue);
		question.stateVariables.add(newBind);

		//ppppp
		question.info.put(Utilities.DEL_BBINDING, "del");
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAStateDefinition(AStateDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		CMLModelcheckerContext aux = new CMLModelcheckerContext();
		
		while(!node.getStateDefs().isEmpty()){
			PDefinition currDef = node.getStateDefs().pollFirst();
			if (currDef instanceof AAssignmentDefinition){
				String varName = currDef.getName().toString();
				Type value = Utilities.createValue(((AAssignmentDefinition) currDef).getExpression());
				SingleBind newBind = new SingleBind(varName,value);
				question.stateVariables.add(newBind);
			}
			//SingleBind newBind = new SingleBind(node);
		}
		
		//a.getExpression().apply(this, aux);
		//aux.getScriptContent().replace(aux.getScriptContent().indexOf("(")+1, aux.getScriptContent().indexOf(")"), node.getStateDefs().getFirst().getName().toString());
		
		//StringBuilder s = new StringBuilder(" fetch(\"");
		//s.append(node.getStateDefs().getFirst().getName().toString());
		//s.append("\",_,");
		//s.append(aux.getScriptContent().toString());
		//s.append(")");
		
		//question.putStates(Utilities.STATES_KEY, s);
		
		//question.updateStates(node.getStateDefs().getFirst().getName().toString());
		//pppp
		question.info.put(Utilities.DEL_BBINDING, "del");
		
		return question.getScriptContent();
	}
	
	
	//// DECLARATIONS
	@Override
	public StringBuilder caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, CMLModelcheckerContext question)
			throws AnalysisException {
		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getIdentifiers().getFirst().getName());
		question.getScriptContent().append(")");
		return question.getScriptContent();
	}
	
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
