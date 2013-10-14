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
import eu.compassresearch.ast.lex.LexNameToken;
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
			CMLModelcheckerContext question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}


}
