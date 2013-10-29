package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ATimesNumericBinaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;

public class MCProcessVisitor extends
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {

	final private QuestionAnswerAdaptor<CMLModelcheckerContext, StringBuilder> rootVisitor;
	
	public MCProcessVisitor(QuestionAnswerAdaptor<CMLModelcheckerContext, StringBuilder> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	@Override
	public StringBuilder caseAProcessDefinition(AProcessDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {

		generateUserTypeDefinitions(question);

		generateChannelTypes(question);

		question.getScriptContent().append(
				"domain StartProcDomain includes CML_PropertiesSpec {\n");
		// .append("domain StartProcDomain includes CSP_Properties at \"./basic_formula_script.fml\" {\n");
		// .append("domain StartProcDomain includes CSP_Properties at \"../resources/basic_formula_script.fml\" {\n");

		// it converts all structures defined in the process body (auxiliary
		// actions, etc).

		//if(question.info.containsKey(Utilities.VALUE_DEFINITIONS_KEY)){
		//	LinkedList<AValueDefinition> values = (LinkedList<AValueDefinition>) question.info.get(Utilities.VALUE_DEFINITIONS_KEY);
		//	for(int i = 0; i < values.size(); i++){
		//		values.get(i).apply(this, question);
		//	}
		//}

		node.getProcess().apply(this, question);

		int startIndex = question.getScriptContent().indexOf("#");
		int endIndex = question.getScriptContent().lastIndexOf("#");
		question.getScriptContent().replace(startIndex, endIndex + 1,
				"\"" + node.getName().toString() + "\"");


		if(question.info.containsKey(Utilities.ASSIGNMENT_DEFINITION_KEY)){
			Map<String, Map<String, String>> assigns = (Map<String, Map<String, String>>) question.info.get(Utilities.ASSIGNMENT_DEFINITION_KEY);

			for(Iterator<String> it = assigns.keySet().iterator(); it.hasNext();){
				String key = it.next();
				Map<String, String> aux = assigns.get(key);
				for(Iterator<String> iterator = aux.keySet().iterator(); iterator.hasNext();){
					String key2 = iterator.next();
					String values = aux.get(key2);
					Integer val = new Integer(values);
					Binding max;
					question.getScriptContent().append("  assignDef(0, "+ key +", st, st_)  :- State(0,st,name,assign("+key+")), st = ");
					//if(question.info.containsKey(Utilities.STATES_KEY)){
					if(question.stateVariables.size() > 0){
						//max = question.getMaxBindingWithStates();
						max = question.getMaxBinding();
						max.setProcName("name");
						//question.getScriptContent().append(max.toFormulaWithState());
						question.getScriptContent().append(max.toFormulaWithState());
						question.getScriptContent().append(", st_ = ");
						max.updateBinding(key2, new Int(key2+"_"));
						question.getScriptContent().append(max.toFormulaWithState());
						question.getScriptContent().append(", "+key2+"_ = "+ val.toString());
					} else{
						max = question.getMaxBinding();
						max.setProcName("name");
						//question.getScriptContent().append(max.toFormula());
						question.getScriptContent().append(max.toFormulaWithUnderscore());
						question.getScriptContent().append(", st_ = ");
						max.updateBinding(key2, new Int(val.intValue()));
						//question.getScriptContent().append(max.toFormula());
						question.getScriptContent().append(max.toFormula());
					}
					question.getScriptContent().append(".\n");
				}
			}
		}

		generateOperationsDefinitions(question);

		generateIOCommDefs(question);

		generateGuardDefinitions(question);

		question.getScriptContent().append(
				"  conforms := CML_PropertiesSpec." + question.propertyToCheck + ".\n");
		question.getScriptContent().append("}\n\n");
		question.getScriptContent().append(
				"partial model StartProcModel of StartProcDomain{\n");

		generateValueDefinitions(question);



		//if(question.info.containsKey(Utilities.STATES_KEY)){
		/*
		if(question.stateVariables.size() > 0){
			//pppppppp
			ArrayList<StringBuilder> states = (ArrayList<StringBuilder>) question.info.get(Utilities.STATES_KEY);
			StringBuilder s;
			for (int i = 0; i < states.size(); i++) {
				s = states.get(i);
				String currentState = question.getStates().get(i);
				StringBuilder aux = new StringBuilder(question.getMaxBindingWithStates().toFormulaWithState());
				aux.replace(aux.indexOf("Int("), aux.indexOf(")")+1, currentState);
				s.replace(s.indexOf("_"), s.indexOf("_")+1, aux.toString());
				s.replace(s.indexOf("Int("), s.length(), currentState+")");
				s.append("\n");
				question.getScriptContent().append(" "+s);
			}
		}
		 */
		/* generate del biind facts
		if(question.info.containsKey(Utilities.DEL_BBINDING)){
			if(question.getVariables().size() != 0){
				Set<SingleBind> varToDel = question.getVariables();
				for(Iterator<SingleBind> var = varToDel.iterator(); var.hasNext();){
					String s = var.next().getVariableName();
					Binding b = question.getMaxBinding();
					StringBuilder del = new StringBuilder("  del(");
					del.append(question.getMaxBinding().toFormulaWithUnderscore());
					del.append(",\""+s+"\",");
					b = b.deleteBinding(s);
					del.append(b.toFormulaWithUnderscore()+")\n");
					question.getScriptContent().append(del);
				}
			} else{
				ArrayList<String> varToDel = question.getStates();
				for(String s: varToDel){
					Binding b = question.getMaxBinding();
					StringBuilder del = new StringBuilder("  del(");
					del.append(question.getMaxBindingWithStates().toFormulaWithUnderscore());
					del.append(",\""+s+"\",");
					b = b.deleteBinding(s);
					del.append(b.toFormulaWithUnderscore()+")\n");
					question.getScriptContent().append(del);
				}
			}
		}
		 */

		generateLieInFacts(question);

		//pppp
		//generate fetch facts
		//generate del facts
		//generate upd facts (if necessary)

		generateBindingFacts(question);

		generateChannelDefinitions(question);

		question.getScriptContent().append(
				"  GivenProc(\"" + node.getName() + "\")\n");
		question.getScriptContent().append("}");


		String content = question.getScriptContent().toString();
		Binding maxBinding = question.getMaxBinding();
		content = content.replaceAll(Utilities.MAX_BIND, maxBinding.toFormula());
		StringBuilder newContent = new StringBuilder();
		newContent.append(content);
		question.setScriptContent(newContent);

		return question.getScriptContent();
	}

	private void generateUserTypeDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		if(context.typeDefinitions.size() > 0){
			//LinkedList<ATypeDefinition> t = (LinkedList<ATypeDefinition>) question.info.get(Utilities.TYPE_USER_DEFINITION);
			LinkedList<UserTypeDefinition> types = context.typeDefinitions;
			String s = "//USER_DEF_TYPES";
			String s2 = "/*INCLUDE USER_DEF_TYPES*/";
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
					realtype.apply(this, auxctxt);
					auxctxt.getScriptContent().append(").\n");
				}
				//if it is ANamedInvariantType
				if (realtype instanceof ANamedInvariantType){
					auxctxt.getScriptContent().append("primitive ");
					auxctxt.getScriptContent().append("ChanType" + auxctxt.CHANTYPE_COUNTER);
					createdChanTypes.add("ChanType" + auxctxt.CHANTYPE_COUNTER);
					auxctxt.getScriptContent().append(" ::= (");
					realtype.apply(this, auxctxt);
					auxctxt.getScriptContent().append(").\n");
				}
				//if it is AIntNumericBasicType
				if (realtype instanceof AIntNumericBasicType){
					//channel of int type are already manipulated 
					/*
					auxctxt.getScriptContent().append("primitive ");
					auxctxt.getScriptContent().append("ChanType" + auxctxt.CHANTYPE_COUNTER);
					createdChanTypes.add("ChanType" + auxctxt.CHANTYPE_COUNTER);
					auxctxt.getScriptContent().append(" ::= (");
					realtype.apply(this, auxctxt);
					auxctxt.getScriptContent().append(").\n");
					*/
				}
				
				context.CHANTYPE_COUNTER++;
			}
		}
		
		if(createdChanTypes.size() == 0){
			//nothing to do
		}else if (createdChanTypes.size() == 1){ //do not use type union
			//chan
			String s = "//CHAN_TYPES";
			String s2 = "/*UNION_CHAN_TYPES*/";
			int aux = context.basicContent.indexOf(s);
			context.basicContent.replace(aux, aux+s.length(), "");
			context.basicContent.replace(aux, aux+1, auxctxt.getScriptContent().toString());
			
			int aux2 = context.basicContent.indexOf(s2);
			context.basicContent.replace(aux2, aux2+s2.length(), "");
			context.basicContent.replace(aux2, aux2+1, " + " + createdChanTypes.getFirst());
		}
		if(createdChanTypes.size() > 1){ //we have to use type union 
				
			String s = "//CHAN_TYPES";
			String s2 = "/*UNION_CHAN_TYPES*/";
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
				StringBuilder result = pDef.apply(this,newCtxt);
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
					CmlLexNameToken nextNameToken = new CmlLexNameToken(module,nextName,location);
					CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
					StringBuilder nextValue = stateDesignator.apply(this,localCtxt);
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
				StringBuilder result = pDef.apply(this,newCtxt);
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
				String expression = actionBody.getExpression().apply(this, newCtxt).toString();
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
					StringBuilder result = pDef.apply(this,localCtxt);
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
			condition.expression.apply(this,context);

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
			/*
			StringBuilder condResult = auxCtxt.getScriptContent();
			int indexGuardNDefHash = condResult.indexOf("#");
			if(indexGuardNDefHash != -1){
				context.getScriptContent().append("  guardNDef(l,occ,b) :- State(l,b,_,condChoice(occ,_,_))");
				condResult = condResult.replace(0,indexGuardNDefHash+1,"");
				context.getScriptContent().append(condResult.toString());
				context.getScriptContent().append(".");
			}else{
				context.getScriptContent().append("  guardDef(l,occ,b) :- State(l,b,_,condChoice(occ,_,_))");
				context.getScriptContent().append(condResult.toString());
				context.getScriptContent().append(".");
			}
			 */
			//context.getScriptContent().append("\n");

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
			def.getType().apply(this, context);
			context.getScriptContent().append(").\n");
			if(def.getExpression() instanceof ATimesNumericBinaryExp){
				context.getScriptContent().append(def.getPattern().toString());
				//context.getScriptContent().append("(" + def.getPattern().toString().toLowerCase() + ") :- #, " + def.getPattern().toString().toLowerCase()+" = @");
				context.getScriptContent().append("(" + def.getPattern().toString().toLowerCase() + ") :- #, " + def.getPattern().toString().toLowerCase()+" = @");
				def.getExpression().apply(this, context);
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
			channelDef.chanDef.apply(this, context);
			context.getScriptContent().append("\n");
		}
	}
	
	@Override
	public StringBuilder caseAActionProcess(AActionProcess node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PAction mainAction = node.getAction();
		
		if(mainAction instanceof AGeneralisedParallelismReplicatedAction){
			PVarsetExpression chanSet = ((AGeneralisedParallelismReplicatedAction) mainAction).getChansetExpression();
			question.setStack.add(chanSet);
		}
		
		if(mainAction instanceof AGeneralisedParallelismParallelAction){
			PVarsetExpression chanSet = ((AGeneralisedParallelismParallelAction) mainAction).getChansetExpression();
			question.setStack.add(chanSet);
		}
		// it applies to each definition of this action process
		for (PDefinition definition : node.getDefinitionParagraphs()) {
			 definition.apply(this, question);
		}
		//if(!question.setStack.isEmpty()){
		//	question.setStack.pop();
		//}
		
		// question.info.put(node.getAction(), node.getDefinitionParagraphs());
		//question.info.put(Utilities.LOCAL_DEFINITIONS_KEY,
		//		node.getDefinitionParagraphs());
		
		//node.getDefinitionParagraphs().getFirst().apply(this, question);

		// Auxiliary processes will be written here
		//question.getScriptContent().append("#AUXILIARY_PROCESSES#\n");

		// Iocommdefs will be written here
		//question.getScriptContent().append("#IOCOMM_DEFS#\n");
		
		// it converts the top level process
		question.getScriptContent().append("  ProcDef(");
		question.getScriptContent().append("#PROCNAME#");
		question.getScriptContent().append(",");
		// parameters
		question.getScriptContent().append("nopar");
		question.getScriptContent().append(",");
		// the body
		node.getAction().apply(this, question);
		question.getScriptContent().append(").\n");

		if(!question.setStack.isEmpty()){
			question.setStack.pop();
		}
		
		// if there is no auxiliary definitions, then remove the TAG
		//int index = question.getScriptContent()
		//		.indexOf("#AUXILIARY_PROCESSES#");
		//if (index != -1) {
		//	question.getScriptContent().replace(index,
		//			index + "#AUXILIARY_PROCESSES#".length(), "");
		//}
		
		//if(question.info.containsKey(Utilities.VAR_DECLARATIONS_KEY)){
		//	question.getScriptContent().append("  State(0,");
		//	question.getScriptContent().append(question.getMaxBinding().toFormula());
		//	question.getScriptContent().append(",np,pBody)  :- GivenProc(np), ProcDef(np,nopar,pBody).\n");
		//}
		//if(question.info.containsKey(Utilities.STATES_KEY)){ //this should change to look at the variable states in the context
		if(question.stateVariables.size() > 0){
			question.getScriptContent().append("  State(0,");
			question.getScriptContent().append(question.getMaxBinding().toFormula());
			question.getScriptContent().append(",np,pBody)  :- GivenProc(np), ProcDef(np,nopar,pBody).\n");
		} else {
			//putting the initial state to be generated
			question.getScriptContent().append("  State(0,nBind,np,pBody)  :- GivenProc(np), ProcDef(np,nopar,pBody).\n");
		}
		
		/*
		int indexIoCommDef = question.getScriptContent().indexOf("#IOCOMM_DEFS#");
		if(indexIoCommDef != -1){
			//if(question.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
			if(question.ioCommDefs.size() > 0){
				Binding maximalBinding = question.getMaxBinding();
				if(question.info.containsKey(Utilities.STATES_KEY)){
					StringBuilder s = new StringBuilder(question.getScriptContent().toString());
					s.replace(0, s.indexOf("nopar,"), "");
					String ss = s.substring("nopar,".length(), s.indexOf(" :"));
					StringBuilder io = new StringBuilder(question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
					io.append(ss+".\n");
					question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), io.toString());
				} else{
					StringBuilder ioCommDefs = new StringBuilder();
					for (String ioCommDef : question.ioCommDefs) {
						ioCommDef = ioCommDef.replaceAll(Utilities.MAX_BIND, maximalBinding.toFormula());
						ioCommDefs.append(ioCommDef + "\n");
					}
					//question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
					question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), ioCommDefs.toString());
				}
				
			}else{
				question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), "");
			}
		}
		*/	

		//if(question.info.containsKey(Utilities.CONDITION_KEY)){
		/*
		for (PExp condition : question.guards) {
		
			//PExp condition = (PExp)question.info.get(Utilities.CONDITION_KEY);
			CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
			condition.apply(this,auxCtxt);
			StringBuilder condResult = auxCtxt.getScriptContent();
			int indexGuardNDefHash = condResult.indexOf("#");
			if(indexGuardNDefHash != -1){
				question.getScriptContent().append("  guardNDef(l,occ,b) :- State(l,b,_,condChoice(occ,_,_))");
				condResult = condResult.replace(0,indexGuardNDefHash+1,"");
				question.getScriptContent().append(condResult.toString());
				question.getScriptContent().append(".");
			}else{
				question.getScriptContent().append("  guardDef(l,occ,b) :- State(l,b,_,condChoice(occ,_,_))");
				question.getScriptContent().append(condResult.toString());
				question.getScriptContent().append(".");
			}
			question.getScriptContent().append("\n");
			
		}
		*/
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder createNewReturnValue(INode node,
			CMLModelcheckerContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder createNewReturnValue(Object node,
			CMLModelcheckerContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}


}
