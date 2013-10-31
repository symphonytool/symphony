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
	
	
// <<<<<<< HEAD
// =======
// 			int aux2 = context.basicContent.indexOf(s2);
// 			context.basicContent.replace(aux2, aux2+s2.length(), "");
// 			context.basicContent.replace(aux2, aux2 + 1, "+ ChannTypes.");
// 		} 
// 	}
	
// 	private void generateOperationsDefinitions(CMLModelcheckerContext context) throws AnalysisException{
// 		Binding maximalBinding = context.getMaxBindingWithStates();
// 		for (SCmlOperationDefinition operation : context.operations) {
// 			AExplicitCmlOperationDefinition op = (AExplicitCmlOperationDefinition) operation;
// 			StringBuilder opStr = new StringBuilder();
// 			opStr.append("operationDef(");
// 			opStr.append("\"" + op.getName().toString() + "\"");
// 			opStr.append(",");
// 			if(op.getParamDefinitions().size()==0){
// 				opStr.append("nopar");
// 			}else if(op.getParamDefinitions().size()==1){
// 				PDefinition pDef = op.getParamDefinitions().getFirst();
// 				CMLModelcheckerContext newCtxt = new CMLModelcheckerContext();
// 				StringBuilder result = pDef.apply(this,newCtxt);
// 				opStr.append(result.toString());
// 			}
// 			opStr.append(",");
// 			//write the original maximal bind
// 			//opStr.append(maximalBinding.toFormula());
// 			opStr.append("st");
// 			opStr.append(",");
// 			//writes the maximal bind modified. it is good to make a copy and modify it
// 			Binding maximalCopy = maximalBinding.copy();
// 			//modificar o value da variavel 
// 			PAction body = op.getBody();
// 			ASingleGeneralAssignmentStatementAction actionBody = null;
// 			if(body instanceof ASingleGeneralAssignmentStatementAction){
// 				actionBody = (ASingleGeneralAssignmentStatementAction) body;
// 			}

// 			if(actionBody != null){
// 				PExp stateDesignator = actionBody.getStateDesignator();
// 				if(stateDesignator instanceof AVariableExp){
// 					AVariableExp nextStateDesignator = (AVariableExp) stateDesignator.clone();
// 					String module = ((AVariableExp) stateDesignator).getName().getModule();
// 					String nextName = ((AVariableExp) stateDesignator).getName().getName() + "_";
// 					ILexLocation location = stateDesignator.getLocation();
// 					CmlLexNameToken nextNameToken = new CmlLexNameToken(module,nextName,location);
// 					CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
// 					StringBuilder nextValue = stateDesignator.apply(this,localCtxt);
// 					nextValue.replace(nextValue.indexOf("(")+1, nextValue.indexOf(")"), nextName);
// 					//we assume that only Int() are used here
// 					Int newValue = new Int(0);
// 					newValue.setS(nextName);
// 					maximalCopy.updateBinding(((AVariableExp) stateDesignator).getName().toString(), newValue);
// 				}
// 			}
// 			//depois verificar se rpecisa gerar o complemento RHS para essa operacao.
// 			//opStr.append(maximalCopy.toFormulaWithState());
// 			opStr.append("st_");
// 			opStr.append(")");

// 			opStr.append(" :- ");

// 			//put the state information
// 			StringBuilder stateFact = new StringBuilder();
// 			stateFact.append("State(l,st,_,operation(\"" + op.getName().toString() + "\",");
// 			if(op.getParamDefinitions().size()==0){
// 				stateFact.append("nopar");
// 			}else if(op.getParamDefinitions().size()==1){
// 				PDefinition pDef = op.getParamDefinitions().getFirst();
// 				CMLModelcheckerContext newCtxt = new CMLModelcheckerContext();
// 				StringBuilder result = pDef.apply(this,newCtxt);
// 				stateFact.append(result.toString());
// 			}
// 			stateFact.append("))");
// 			opStr.append(stateFact);
// 			opStr.append(",");
// 			//put the st = maximal bind information
// 			opStr.append("st = " + maximalBinding.toFormulaWithState());
// 			opStr.append(",");
// 			//put the next bind information
// 			opStr.append("st_ = " + maximalCopy.toFormulaWithState());
// 			opStr.append(",");

// 			//put the expression
// 			CMLModelcheckerContext newCtxt = new CMLModelcheckerContext();

// 			if(actionBody != null){
// 				newCtxt.getScriptContent().append(((AVariableExp) actionBody.getStateDesignator()).getName().getName() + "_" + " = ");
// 				String expression = actionBody.getExpression().apply(this, newCtxt).toString();
// 				opStr.append(expression);

// 			}
// 			opStr.append(".\n");

// 			//generate the preconditionOK e preconditioNOk
// 			//"preOpOk("Init", nopar, BBinding(SingleBind("a",Int(a)),nBind)) :- State(BBinding(SingleBind("a",Int(a)),nBind),_)."
// 			//PExp exp = actionBody.getExpression();
// 			if(op.getPrecondition() != null){
// 				//convert precondition
// 			}else{
// 				//we assume that all preconditions are true for the moment
// 				StringBuilder preOpOkFact = new StringBuilder();
// 				preOpOkFact.append("preOpOk(\"" + op.getName().toString() + "\",");
// 				if(op.getParamDefinitions().size()==0){
// 					preOpOkFact.append("nopar");
// 				}else if(op.getParamDefinitions().size()==1){
// 					PDefinition pDef = op.getParamDefinitions().getFirst();
// 					CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
// 					StringBuilder result = pDef.apply(this,localCtxt);
// 					preOpOkFact.append(result.toString());
// 				}
// 				preOpOkFact.append(",");
// 				preOpOkFact.append(maximalBinding.toFormulaWithState());
// 				preOpOkFact.append(")");
// 				preOpOkFact.append(" :- ");
// 				preOpOkFact.append("State(l," + maximalBinding.toFormulaWithState() + ",np,_).");
// 				preOpOkFact.append("\n");
// 				opStr.append(preOpOkFact.toString());
// 			}
// 			//we assume that post conditions are not present

// 			//puts the oepration definition and the precondition
// 			context.getScriptContent().append(opStr);
// 		}
// 	} 
	
// 	private void generateIOCommDefs(CMLModelcheckerContext context){
// 		//if(question.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
// 		if(context.ioCommDefs.size() > 0){
// 			Binding maximalBinding = context.getMaxBinding();
// 			//if(context.info.containsKey(Utilities.STATES_KEY)){
// 			//if(context.stateVariables.size() > 0){
// 			//	StringBuilder s = new StringBuilder(context.getScriptContent().toString());
// 			//	s.replace(0, s.indexOf("nopar,"), "");
// 			//	String ss = s.substring("nopar,".length(), s.indexOf(" :"));

// 			//	StringBuilder io = new StringBuilder(context.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
// 			//	io.append(ss+".\n");
// 			//context.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), io.toString());
// 			//context.getScriptContent().append(io.toString());
// 			//} else{
// 			StringBuilder ioCommDefs = new StringBuilder();
// 			for (String ioCommDef : context.ioCommDefs) {
// 				ioCommDef = ioCommDef.replaceAll(Utilities.MAX_BIND, maximalBinding.toFormula());
// 				ioCommDefs.append(ioCommDef + "\n");
// 			}
// 			//question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
// 			//context.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), ioCommDefs.toString());
// 			context.getScriptContent().append(ioCommDefs.toString());
// 			//}

// 		}

// 	}
	
// 	private void generateGuardDefinitions(CMLModelcheckerContext context) throws AnalysisException{
// 		//for all guard conditions found in the specification
// 		for (Condition condition : context.guards) {
// 			CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
// 			condition.expression.apply(this,context);

// 			Binding maxBinding = context.getMaxBinding();

// 			//generating positive guards
// 			String positiveGuardExp = context.positiveGuardExps.get(condition.expression);
// 			StringBuilder positiveGuardBuilder = new StringBuilder();
// 			//context.getScriptContent().append(condResult.toString());
// 			if(positiveGuardExp != null){
// 				positiveGuardBuilder.append("  guardDef(l," + condition.counter + "," + maxBinding.toFormulaWithState() + ")");
// 				positiveGuardBuilder.append(" :- State(l," + maxBinding.toFormulaWithState() + "," +maxBinding.getProcName()+ ",condChoice(" + condition.counter + ",_,_))");				positiveGuardBuilder.append(",");
// 				positiveGuardBuilder.append(positiveGuardExp);
// 				positiveGuardBuilder.append(".\n");
// 			}


// 			//generating negative guards
// 			String negativeGuardExp = context.negativeGuardExps.get(condition.expression);
// 			StringBuilder negativeGuardBuilder = new StringBuilder();
// 			//context.getScriptContent().append(condResult.toString());
// 			if(negativeGuardExp != null){
// 				negativeGuardBuilder.append("  guardNDef(l," + condition.counter + "," + maxBinding.toFormulaWithState() + ")");
// 				negativeGuardBuilder.append(" :- State(l," + maxBinding.toFormulaWithState() + "," +maxBinding.getProcName()+ ",condChoice(" + condition.counter + ",_,_))");
// 				negativeGuardBuilder.append(",");
// 				negativeGuardBuilder.append(negativeGuardExp);
// 				negativeGuardBuilder.append(".\n");
// 			}

// 			context.getScriptContent().append(positiveGuardBuilder.toString());
// 			context.getScriptContent().append(negativeGuardBuilder.toString());
// 			/*
// 			StringBuilder condResult = auxCtxt.getScriptContent();
// 			int indexGuardNDefHash = condResult.indexOf("#");
// 			if(indexGuardNDefHash != -1){
// 				context.getScriptContent().append("  guardNDef(l,occ,b) :- State(l,b,_,condChoice(occ,_,_))");
// 				condResult = condResult.replace(0,indexGuardNDefHash+1,"");
// 				context.getScriptContent().append(condResult.toString());
// 				context.getScriptContent().append(".");
// 			}else{
// 				context.getScriptContent().append("  guardDef(l,occ,b) :- State(l,b,_,condChoice(occ,_,_))");
// 				context.getScriptContent().append(condResult.toString());
// 				context.getScriptContent().append(".");
// 			}
// 			 */
// 			//context.getScriptContent().append("\n");

// 		}
// 	}

// 	private void generateValueDefinitions(CMLModelcheckerContext context) throws AnalysisException{
// 		LinkedList<UserDefinedValue> userValues = context.valueDefinitions;
// 		for (UserDefinedValue userDefinedValue : userValues) {
// 			AValueDefinition def = userDefinedValue.getValueDef();
// 			if(def.getExpression() instanceof AIntLiteralExp){
// 				context.getScriptContent().append("primitive ");
// 			} 
// 			context.getScriptContent().append(def.getPattern().toString()+" ::= (");		
// 			def.getType().apply(this, context);
// 			context.getScriptContent().append(").\n");
// 			if(def.getExpression() instanceof ATimesNumericBinaryExp){
// 				context.getScriptContent().append(def.getPattern().toString());
// 				//context.getScriptContent().append("(" + def.getPattern().toString().toLowerCase() + ") :- #, " + def.getPattern().toString().toLowerCase()+" = @");
// 				context.getScriptContent().append("(" + def.getPattern().toString().toLowerCase() + ") :- #, " + def.getPattern().toString().toLowerCase()+" = @");
// 				def.getExpression().apply(this, context);
// 				context.getScriptContent().append(".\n");
// 			}
// 		}
		
// 	}
	
// 	private void generateLieInFacts(CMLModelcheckerContext context){
// 		if(context.lieIn.size() != 0){
// 			context.getScriptContent().append("\n");
// 			for (String lieIn : context.lieIn) {
// 				context.getScriptContent().append(lieIn + "\n");
// 			}
// 		}
// 	}
	
// 	private void generateBindingFacts(CMLModelcheckerContext context){
// 		//generate fetch facts
// 		Binding maxBinding = context.getMaxBinding();
// 		context.getScriptContent().append(maxBinding.generateAllFetchFacts(context.numberOfFetchFacts));
		
// 		//generate upd facts
		
		
// 		//generate del facts
// 	}
	
// 	private void generateChannelDefinitions(CMLModelcheckerContext context) throws AnalysisException{
// 		for (ChannelTypeDefinition channelDef : context.channelDefinitions) {
// 			channelDef.chanDef.apply(this, context);
// 			context.getScriptContent().append("\n");
// 		}
// 	}
// >>>>>>> 7f88b966a54ed9b708713f91d9c804b84b1d8a75
	
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
			 definition.apply(rootVisitor, question);
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
		node.getAction().apply(rootVisitor, question);
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
			CMLModelcheckerContext question) throws AnalysisException
	{
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
