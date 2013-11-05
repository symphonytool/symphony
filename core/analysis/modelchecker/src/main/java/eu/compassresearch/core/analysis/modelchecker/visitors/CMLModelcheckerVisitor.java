package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AGreaterEqualNumericBinaryExp;
import org.overture.ast.expressions.AGreaterNumericBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ALessEqualNumericBinaryExp;
import org.overture.ast.expressions.ALessNumericBinaryExp;
import org.overture.ast.expressions.ANotEqualBinaryExp;
import org.overture.ast.expressions.ANotUnaryExp;
import org.overture.ast.expressions.AOrBooleanBinaryExp;
import org.overture.ast.expressions.APlusNumericBinaryExp;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.ASubtractNumericBinaryExp;
import org.overture.ast.expressions.ATimesNumericBinaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADeclareStatementAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.actions.SReplicatedActionBase;
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
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationUtilities;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.SingleBind;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;


@SuppressWarnings("serial")
public class CMLModelcheckerVisitor extends
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {

	private List<PSource> sources;
	private final static String ANALYSIS_NAME = "Model Checker Visitor";
	private String propertyToCheck = Utilities.DEADLOCK_PROPERTY;
	private StringBuilder basicContent;

	public CMLModelcheckerVisitor(List<PSource> sources) {
		this.sources = sources;
	}
	public CMLModelcheckerVisitor() {
		this.sources = new LinkedList<PSource>();
		basicContent = new StringBuilder();
	}
	public CMLModelcheckerVisitor(PSource singleSource) {
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
	}

	public String getPropertyToCheck() {
		return propertyToCheck;
	}

	public void setPropertyToCheck(String propertyToCheck) {
		this.propertyToCheck = propertyToCheck;
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
				"  conforms := CML_PropertiesSpec." + this.propertyToCheck + ".\n");
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
	
	private void generateChannelDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		for (ChannelTypeDefinition channelDef : context.channelDefinitions) {
			channelDef.chanDef.apply(this, context);
			context.getScriptContent().append("\n");
		}
	}
	
	private void generateBindingFacts(CMLModelcheckerContext context){
		//generate fetch facts
		Binding maxBinding = context.getMaxBinding();
		context.getScriptContent().append(maxBinding.generateAllFetchFacts(context.numberOfFetchFacts));
		
		//generate upd facts
		
		
		//generate del facts
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
			int aux = this.basicContent.indexOf(s);
			this.basicContent.replace(aux, aux+s.length(), "");
			this.basicContent.replace(aux, aux+1, auxctxt.getScriptContent().toString());
			
			int aux2 = this.basicContent.indexOf(s2);
			this.basicContent.replace(aux2, aux2+s2.length(), "");
			this.basicContent.replace(aux2, aux2+1, " + " + createdChanTypes.getFirst());
		}
		if(createdChanTypes.size() > 1){ //we have to use type union 
				
			String s = "//CHAN_TYPES";
			String s2 = "/*UNION_CHAN_TYPES*/";
			int aux = this.basicContent.indexOf(s);
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
			this.basicContent.replace(aux, aux+s.length(), "");
			this.basicContent.replace(aux, aux+1, auxctxt.getScriptContent().toString() + "\n");
	
			int aux2 = this.basicContent.indexOf(s2);
			this.basicContent.replace(aux2, aux2+s2.length(), "");
			this.basicContent.replace(aux2, aux2 + 1, "+ ChannTypes.");

		} 
	}
	
	private void generateUserTypeDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		if(context.typeDefinitions.size() > 0){
			//LinkedList<ATypeDefinition> t = (LinkedList<ATypeDefinition>) question.info.get(Utilities.TYPE_USER_DEFINITION);
			LinkedList<UserTypeDefinition> types = context.typeDefinitions;
			String s = "//USER_DEF_TYPES";
			String s2 = "/*INCLUDE USER_DEF_TYPES*/";
			int aux = this.basicContent.indexOf(s);
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
			this.basicContent.replace(aux, aux+s.length(), "");
			this.basicContent.replace(aux, aux+1, auxCtxt.getScriptContent().toString());
			int aux2 = this.basicContent.indexOf(s2);
			this.basicContent.replace(aux2, aux2+s2.length(), "");
			this.basicContent.replace(aux2, aux2+1, str2.toString());
		}
	}
	private void generateValueDefinitions(CMLModelcheckerContext context) throws AnalysisException{
		LinkedList<UserDefinedValue> userValues = context.valueDefinitions;
		for (UserDefinedValue userDefinedValue : userValues) {
			AValueDefinition def = userDefinedValue.getValueDef();
			StringBuilder userValueDefBuilder = new StringBuilder(); 
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
	private void generateLieInFacts(CMLModelcheckerContext context){
		if(context.lieIn.size() != 0){
			context.getScriptContent().append("\n");
			for (String lieIn : context.lieIn) {
				context.getScriptContent().append(lieIn + "\n");
			}
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

	//visitor methods for operations
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
	// Visitor methods for actions
	@Override
	public StringBuilder caseAActionsDefinition(AActionsDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it applies to each internal definition
		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition currentActionDefinition : actions) {
			question.localActions.add(currentActionDefinition);
			currentActionDefinition.apply(this, question);
		}
		return question.getScriptContent();
	}

	
	
	@Override
	public StringBuilder caseAInterleavingParallelAction(
			AInterleavingParallelAction node, CMLModelcheckerContext question)
			throws AnalysisException {
		
		// it writes the interleave as a generalised parallelism with am empty synchronisation set
		// it writes the left process into the buffer
		question.getScriptContent().append("genPar(");
		node.getLeftAction().apply(this, question);
		question.getScriptContent().append(",");
		question.getScriptContent().append("\"{}\"");
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRightAction().apply(this, question);
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}
	
	
	
	@Override
	public StringBuilder caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if (sDecl instanceof AExpressionSingleDeclaration) {
			PExp pExp = ((AExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof ASetEnumSetExp) {
				indexes = ((ASetEnumSetExp) pExp).getMembers();
			}
		}
		// building combination of processses based on simple constructs
		StringBuilder replicatedActionBuilder = buildReplicatedAction(node, question,
				node.getReplicatedAction(), Utilities.GEN_PARALLELISM,
				indexes.size());
		String replicatedActionString = replicatedActionBuilder.toString();
		for (PExp pExp : indexes) {
			CMLModelcheckerContext argCtxt = new CMLModelcheckerContext();
			StringBuilder argValue = pExp.apply(this, argCtxt);
			PAction replicatedAction = node.getReplicatedAction();
			if(replicatedAction instanceof ACallStatementAction){
				PExp arg0 = ((ACallStatementAction) replicatedAction).getArgs().getFirst();
				if(arg0 instanceof AVariableExp){
					replicatedActionString = replicatedActionString.replaceFirst("(" +  ((AVariableExp) arg0).getName().toString() + ")", argValue.toString());
				}
			} 
		}
		question.getScriptContent().append(replicatedActionString);
		
		//it must put the sinchronisation set in the context before visiting the definitions
		//to generate lieIn
		//PVarsetExpression chanSet = node.getChansetExpression();
		//question.setStack.add(chanSet);
		
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
		//		.get(Utilities.LOCAL_DEFINITIONS_KEY);
		//CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		//if(localDefinitions != null){
		//	for (PDefinition pDefinition : localDefinitions) {
		//		pDefinition.apply(this, auxCtxt);
		//	}
		//}
		//question.setStack.pop();
		
		//int auxIndex = question.getScriptContent().indexOf(
		//		"#AUXILIARY_PROCESSES#");
		//if (auxIndex != -1) {
		//	question.getScriptContent().replace(auxIndex,
		//			auxIndex + "#AUXILIARY_PROCESSES#".length(),
		//			auxCtxt.getScriptContent().toString());
		//}

		return question.getScriptContent();
	}
	@Override
	public StringBuilder caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if (sDecl instanceof AExpressionSingleDeclaration) {
			PExp pExp = ((AExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof ASetEnumSetExp) {
				indexes = ((ASetEnumSetExp) pExp).getMembers();
			}
		}
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction( node, question,
				node.getReplicatedAction(), Utilities.EXTERNAL_CHOICE,
				indexes.size());
		question.getScriptContent().append(replicatedAction.toString());

		
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
		//		.get(Utilities.LOCAL_DEFINITIONS_KEY);
		//CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		//if(localDefinitions != null){
		//	for (PDefinition pDefinition : localDefinitions) {
		//		pDefinition.apply(this, auxCtxt);
		//	}
		//}
		//int auxIndex = question.getScriptContent().indexOf(
		//		"#AUXILIARY_PROCESSES#");
		//if (auxIndex != -1) {
		//	question.getScriptContent().replace(auxIndex,
		//			auxIndex + "#AUXILIARY_PROCESSES#".length(),
		//			auxCtxt.getScriptContent().toString());
		//}

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if (sDecl instanceof AExpressionSingleDeclaration) {
			PExp pExp = ((AExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof ASetEnumSetExp) {
				indexes = ((ASetEnumSetExp) pExp).getMembers();
			}
		}
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(node, question, 
				node.getReplicatedAction(), Utilities.SEQUENTIAL_COMPOSITION,
				indexes.size());
		question.getScriptContent().append(replicatedAction.toString());

		
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
				.get(Utilities.LOCAL_DEFINITIONS_KEY);
		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		for (PDefinition pDefinition : localDefinitions) {
			pDefinition.apply(this, auxCtxt);
		}
		int auxIndex = question.getScriptContent().indexOf(
				"#AUXILIARY_PROCESSES#");
		if (auxIndex != -1) {
			question.getScriptContent().replace(auxIndex,
					auxIndex + "#AUXILIARY_PROCESSES#".length(),
					auxCtxt.getScriptContent().toString());
		}

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.getScriptContent().append("assign("+CMLModelcheckerContext.ASSIGN_COUNTER +")");
		//StringBuilder str = new StringBuilder();
		//this line is generated for this assignment. Each assignment must have a line like this
		//probably the maximal binding is not ready to be put here and you need to put the assignment information into the context 
		//str.append("  assignDef(0, "+CMLModelcheckerContext.ASSIGN_COUNTER +", st, st_)  :- State(0,st,name,assign("+CMLModelcheckerContext.ASSIGN_COUNTER+")), st = OLD_BINDING, st_ = NEW_BINDING");
		//str.append(".\n");
		
		question.putVarAttInBinding(Utilities.ASSIGNMENT_DEFINITION_KEY, ""+CMLModelcheckerContext.ASSIGN_COUNTER, node.getStateDesignator().toString(), node.getExpression().toString());
		
		CMLModelcheckerContext.ASSIGN_COUNTER++;
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if (sDecl instanceof AExpressionSingleDeclaration) {
			PExp pExp = ((AExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof ASetEnumSetExp) {
				indexes = ((ASetEnumSetExp) pExp).getMembers();
			}
		}
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(node, question, 
				node.getReplicatedAction(), Utilities.INTERNAL_CHOICE,
				indexes.size());
		//PPPPP
		question.getScriptContent().append(replicatedAction.toString());

		
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
		//		.get(Utilities.LOCAL_DEFINITIONS_KEY);
		//CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		//for (PDefinition pDefinition : localDefinitions) {
		//	pDefinition.apply(this, auxCtxt);
		//}
		//int auxIndex = question.getScriptContent().indexOf(
		//		"#AUXILIARY_PROCESSES#");
		//if (auxIndex != -1) {
		//	question.getScriptContent().replace(auxIndex,
		//			auxIndex + "#AUXILIARY_PROCESSES#".length(),
		//			auxCtxt.getScriptContent().toString());
		//}

		return question.getScriptContent();
	}

	private StringBuilder buildReplicatedAction(SReplicatedActionBase replicatedAction, 
			CMLModelcheckerContext context, PAction action,
			int kindOfAction, int times) throws AnalysisException {

		CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
		localCtxt.localActions = context.localActions;
		if (times == 1) {
			action.apply(this, localCtxt);
		} else if (times > 1) {
			if (kindOfAction == Utilities.EXTERNAL_CHOICE) {
				localCtxt.getScriptContent().append("eChoice(");
			} else if (kindOfAction == Utilities.INTERNAL_CHOICE) {
				localCtxt.getScriptContent().append("iChoice(");
			} else if (kindOfAction == Utilities.SEQUENTIAL_COMPOSITION) {
				localCtxt.getScriptContent().append("seqC(");
			} else if (kindOfAction == Utilities.GEN_PARALLELISM) {
				localCtxt.getScriptContent().append("genPar(");
			}
			action.apply(this, localCtxt);
			localCtxt.getScriptContent().append(",");
			if(replicatedAction instanceof AGeneralisedParallelismReplicatedAction){
				((AGeneralisedParallelismReplicatedAction) replicatedAction).getChansetExpression().apply(this, localCtxt);
				localCtxt.getScriptContent().append(",");
			}
			StringBuilder rest = buildReplicatedAction(replicatedAction, context,action, kindOfAction,
					times - 1);
			localCtxt.getScriptContent().append(rest.toString());
			localCtxt.getScriptContent().append(")");
		}
		return localCtxt.getScriptContent();
	}

	@Override
	public StringBuilder caseAHidingAction(AHidingAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		//it puts the event set in the context so the internal process can access and generate lieIn
		question.setStack.add(node.getChansetExpression());
		
		// "actions\ {ev}" hide(actions,"{ev}")
		question.getScriptContent().append("hide(");
		
		//the internal action mut check if there is some event set to generate lieInEvents
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		node.getChansetExpression().apply(this, question);
		question.getScriptContent().append(")");
		
		//it remover the event set from the context at the end
		question.setStack.pop();
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAPlusNumericBinaryExp(APlusNumericBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		CMLModelcheckerContext localCtxt = new CMLModelcheckerContext(); 
		String result = node.getLeft().apply(this, localCtxt).toString();
		result = eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.extractConstructor(result);
		question.getScriptContent().append(result);
		question.getScriptContent().append(" + ");
		localCtxt.reset();
		result = node.getRight().apply(this, localCtxt).toString();
		result = eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.extractConstructor(result);
		question.getScriptContent().append(result);
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseASubtractNumericBinaryExp(
			ASubtractNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {
		CMLModelcheckerContext localCtxt = new CMLModelcheckerContext(); 
		String result = node.getLeft().apply(this, localCtxt).toString();
		result = eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.extractConstructor(result);
		question.getScriptContent().append(result);
		question.getScriptContent().append(" - ");
		localCtxt.reset();
		result = node.getRight().apply(this, localCtxt).toString();
		result = eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.extractConstructor(result);
		question.getScriptContent().append(result);
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAEnumVarsetExpression(AEnumVarsetExpression node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("\"" + node.toString() + "\"");

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAFatEnumVarsetExpression(
			AFatEnumVarsetExpression node, CMLModelcheckerContext question)
			throws AnalysisException {

		question.getScriptContent().append("\"" + node.toString() + "\"");
		
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
			node.getAction().apply(this, question);
			question.getScriptContent().append(")");
			//if(!question.info.containsKey(Utilities.STATES_KEY)){
			//	question.getScriptContent().append(").\n");
			//}
		} else if(parameters.size()==1){
			question.getScriptContent().append("SPar(");
			node.getDeclarations().getFirst().apply(this, question);
			question.getScriptContent().append("),");
			node.getAction().apply(this, question);
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
	public StringBuilder caseAValParametrisation(AValParametrisation node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getDeclaration().getIdentifiers().getFirst().toString());
		question.getScriptContent().append(")");
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, CMLModelcheckerContext question)
			throws AnalysisException {
		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getIdentifiers().getFirst().getName());
		question.getScriptContent().append(")");
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAStopAction(AStopAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append(node.toString());

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAChaosAction(AChaosAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append(node.toString());

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseADivAction(ADivAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append("Div");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseACommunicationAction(ACommunicationAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		//if the communication action does not involves values
		LinkedList<PCommunicationParameter> parameters = node.getCommunicationParameters();
		if(parameters.size() == 0){
			question.getScriptContent().append(
				"Prefix(BasicEv(\"" + node.getIdentifier() + "\"), ");

			//it applies recursivelly in the internal structure
			node.getAction().apply(this, question);

			question.getScriptContent().append(")");
			
			//if there is some set of event in the context we must generate lieIn events.
			SetStack chanSetStack = question.setStack.copy();
			while(!chanSetStack.isEmpty()){
				
				PVarsetExpression setExp = (PVarsetExpression)chanSetStack.pop();
				LinkedList<ANameChannelExp> chanNames = null;
				if(setExp instanceof AEnumVarsetExpression){
					chanNames = ((AEnumVarsetExpression) setExp).getChannelNames();
				}
				if(setExp instanceof AFatEnumVarsetExpression){
					chanNames = ((AFatEnumVarsetExpression) setExp).getChannelNames();
				}
				if(chanNames != null){
					boolean generateLieIn = false;
					for (ANameChannelExp aNameChannelExp : chanNames) {
						if(aNameChannelExp.getIdentifier().toString().equals(node.getIdentifier().toString())){
							generateLieIn = true;
							break;
						}
					}
					if(!generateLieIn && chanSetStack.size()==0){
						break;
					}else{
						StringBuilder lieIn = new StringBuilder();
						lieIn.append("lieIn(");
						lieIn.append("BasicEv(\"" + node.getIdentifier().toString() + "\")");
						lieIn.append(",");
						lieIn.append("\"");
						lieIn.append(setExp.toString());
						lieIn.append("\"");
						lieIn.append(")");
						
						if(!question.lieIn.contains(lieIn.toString())){
							question.lieIn.add(lieIn.toString());
						}
					}
				}
				
			}
			
		}//else if(parameters.size() == 1){
		else { //there are parameters
			
			question.getScriptContent().append(
					//"Prefix(IOComm(" + question.IOCOMM_COUNTER + ",\"" + node.getIdentifier()+"."+parameters.getFirst().toString() + "\",");
					"Prefix(IOComm(" + question.IOCOMM_COUNTER + ",\"" + node.getIdentifier()+"."+ parameters.toString() + "\",");
			String varName = parameters.getFirst().toString();
			if(parameters.size() == 1){
				//we assume that only integers are communicated on channels
				question.getScriptContent().append("Int(" + varName + ")");
			}
			question.getScriptContent().append("),");
			
				
				//it applies recursivelly in the internal structure
				node.getAction().apply(this, question);

				//question.getScriptContent().append("))");
				question.getScriptContent().append(")");
			
			//if this communication depends on a state variable. then we must add a code for fetching it in the bindings
			SingleBind bind = question.getBindByVariable(varName);
			if(bind != null){
				question.getScriptContent().append(" :- ");
				question.getScriptContent().append("fetch(" + "\"" + varName + "\"," + Utilities.MAX_BIND + "," + "Int(" + varName + ")");
			}else{
				//if this communication depends on a communicated variable (channel)
				PCommunicationParameter param = parameters.getFirst();
				if(param instanceof ASignalCommunicationParameter){
					PExp exp = param.getExpression();
					if(exp instanceof AVariableExp){
						CMLModelcheckerContext copyCtxt = question.copy();
						copyCtxt.scriptContent = new StringBuilder();
						
						//LinkedList<ChannelTypeDefinition> channelDefs = question.channelDefinitions;
						ChannelTypeDefinition channDef = question.getChannelDefinition(node.getIdentifier().toString());
						//question.getScriptContent().append(" :- ");
						//copyCtxt.scriptContent.append(" :- ");
						//aux.getFirst().apply(this, question);
						if(channDef != null){
							channDef.getChanDef().apply(this, copyCtxt);
							//int i = question.getScriptContent().lastIndexOf("_");
							int i = copyCtxt.getScriptContent().lastIndexOf("_");
							//question.getScriptContent().replace(i, i+1, parameters.getFirst().toString());
							copyCtxt.getScriptContent().replace(i, i+1, param.toString());
							//question.getScriptContent().append(".\n");
						
							//puts the information in the main context to be recovered at the end
							question.channelDependencies.add(copyCtxt.getScriptContent().toString());
						}
					}
					
				}
			}
				//if(question.info.containsKey(Utilities.STATES_KEY)){
				//if(question.stateVariables.size() > 0){
					//StringBuilder channelsStr = new StringBuilder(); 
					//ppppppppp
				//	Set stateVariables = question.stateVariables;
					//question.getScriptContent().append(")");
					//ArrayList<StringBuilder> states = (ArrayList<StringBuilder>) question.info.get(Utilities.STATES_KEY);
					//question.getScriptContent().append(") :- " + states.get(0));
					//channelsStr.append(") :- "+states.get(0));
					/*
					for(int i = 1; i < states.size(); i++){
						question.getScriptContent().append(",");
						//channelsStr.append(",");
						question.getScriptContent().append(states.get(i));
						//channelsStr.append(states.get(i));
					}
					question.getScriptContent().append(".\n");
					*/
				//} //else if (question.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
				/*
				 else if (question.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
				 
					PCommunicationParameter param = parameters.getFirst();
					if(param instanceof ASignalCommunicationParameter){
						PExp exp = param.getExpression();
						if(exp instanceof AVariableExp){
							CMLModelcheckerContext copyCtxt = question.copy();
							copyCtxt.scriptContent = new StringBuilder();
							LinkedList<AChannelNameDefinition> aux = (LinkedList<AChannelNameDefinition>) question.info.get(Utilities.CHANNEL_DEFINITIONS_KEY);
							//question.getScriptContent().append(" :- ");
							//copyCtxt.scriptContent.append(" :- ");
							//aux.getFirst().apply(this, question);
							aux.getFirst().apply(this, copyCtxt);
							//int i = question.getScriptContent().lastIndexOf("_");
							int i = copyCtxt.getScriptContent().lastIndexOf("_");
							//question.getScriptContent().replace(i, i+1, parameters.getFirst().toString());
							copyCtxt.getScriptContent().replace(i, i+1, param.toString());
							//question.getScriptContent().append(".\n");
							
							//puts the information in the main context to be recovered at the end
							question.channelDependencies.add(copyCtxt.getScriptContent().toString());
						}
						
					}
				}
				*/
				
				CMLModelcheckerContext aux = new CMLModelcheckerContext();
				aux.getScriptContent().append("  IOCommDef(0," + question.IOCOMM_COUNTER + ",");
				aux.getScriptContent().append("Int(" + parameters.getFirst().toString() + "),");
				//if(question.info.containsKey(Utilities.STATES_KEY)){
				if(question.stateVariables.size() > 0){
					aux.getScriptContent().append(question.getMaxBindingWithStates().toFormulaWithState()+","+question.getMaxBindingWithStates().toFormulaWithState());
					aux.getScriptContent().append(") :- State(0,_,np,");					
					
				} else {
					aux.getScriptContent().append(Utilities.MAX_BIND + "," + Utilities.MAX_BIND + ") :- State(0,_,_,");
					
					//aux.getScriptContent().append("Prefix(IOComm(0,\"" + node.getIdentifier()+"."+parameters.getFirst().toString() + "\",");
					aux.getScriptContent().append("Prefix(IOComm("+ question.IOCOMM_COUNTER +",\"" + node.getIdentifier()+"."+parameters.toString() + "\",");
					aux.getScriptContent().append("Int(" + parameters.getFirst().toString() + ")");
					aux.getScriptContent().append("),");
					node.getAction().apply(this, aux);
					aux.getScriptContent().append(")).\n");
				}
				question.IOCOMM_COUNTER++;
				//aux.getScriptContent().append("  State(0,nBind,np,pBody)  :- GivenProc(np), ProcDef(np,_,pBody).\n");
				
				//question.info.put(Utilities.IOCOMM_DEFINITIONS_KEY, aux.getScriptContent().toString());
				question.ioCommDefs.add(aux.getScriptContent().toString());
		}

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAVariableExp(AVariableExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("Int("+node.getName()+")");
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseACallStatementAction(ACallStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		LinkedList<PExp> args = node.getArgs();
		
		StringBuilder callStr = new StringBuilder();
		ArrayList<AActionDefinition> localActions = question.localActions;
		boolean callResolved = false;
		if(localActions != null){ //if there are auxiliary actions
			for (AActionDefinition localAction : localActions) {
				if(localAction.getName().toString().equals(node.getName().toString())){
					callResolved = true;
					callStr.append("proc(\"" + localAction.getName().toString() + "\",");
					if(args.size()==0){
						callStr.append("nopar");
					}
					if(args.size()==1){
						CMLModelcheckerContext newCtxt = new CMLModelcheckerContext();
						newCtxt.getScriptContent().append("SPar(");
						args.getFirst().apply(this, newCtxt);
						newCtxt.getScriptContent().append(")");
						callStr.append(newCtxt.getScriptContent().toString());
					}
					callStr.append(")");
				}
			}
		}
		if (!callResolved) {
			for (SCmlOperationDefinition pDefinition : question.operations) {
				if(pDefinition.getName().toString().equals(node.getName().toString())){
					callStr.append("operation(\"" + pDefinition.getName().toString() + "\",");
					if(args.size()==0){
						callStr.append("nopar");
					}
					if(args.size()==1){
						CMLModelcheckerContext newCtxt = new CMLModelcheckerContext(); 
						String argStr = args.getFirst().apply(this, newCtxt).toString();
						callStr.append(argStr);
					}
					callStr.append(")");
				}
			}
		}
		
		question.getScriptContent().append(callStr);
		
		// Adding auxiliary definitions
				// LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>)
				// question.info.get(node);
				//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
				//		.get(Utilities.LOCAL_DEFINITIONS_KEY);

				//CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
				//auxCtxt.copyChannelInfo(question);
				//if (localActions != null) {
				//	for (PDefinition pDefinition : localActions) {
				//		pDefinition.apply(this, auxCtxt);
				//	}
				//}
				//question.copyIOCommDefInfo(auxCtxt);
				//int auxIndex = question.getScriptContent().indexOf(
				//		"#AUXILIARY_PROCESSES#");
				//if (auxIndex != -1) {
				//	question.getScriptContent().replace(auxIndex,
				//			auxIndex + "#AUXILIARY_PROCESSES#".length(),
				//			auxCtxt.getScriptContent().toString());
				//}
				//if(question.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
				//	question.getScriptContent().append(question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY));
				//}
				
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseASkipAction(ASkipAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append(node.toString());

		return question.getScriptContent();

	}

	@Override
	public StringBuilder caseAExternalChoiceAction(AExternalChoiceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the left process into the buffer
		question.getScriptContent().append("eChoice(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRight().apply(this, question);
		question.getScriptContent().append(")");

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
	public StringBuilder caseAInternalChoiceAction(AInternalChoiceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the left process into the buffer
		question.getScriptContent().append("iChoice(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRight().apply(this, question);
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		//it puts the event set in the context so the internal process can access and generate lieIn
		question.setStack.add(node.getChansetExpression());
	
		// it writes the left process into the buffer
		question.getScriptContent().append("genPar(");
		node.getLeftAction().apply(this, question);
		question.getScriptContent().append(",");
		node.getChansetExpression().apply(this, question);
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRightAction().apply(this, question);
		question.getScriptContent().append(")");

		question.setStack.pop();
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseABlockStatementAction(ABlockStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		if(node.getDeclareStatement() != null){
			node.getDeclareStatement().apply(this, question);
		}
		node.getAction().apply(this, question);
		if(node.getDeclareStatement() != null){
			question.getScriptContent().append(")");
		}
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAGuardedAction(AGuardedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the conditional choice constructor
		question.getScriptContent().append("condChoice(");
		// it writes the condition as an integer and puts the expression
		//to be evaluated in the context
		question.getScriptContent().append(CMLModelcheckerContext.GUARD_COUNTER + ",");
		//question.info.put(Utilities.CONDITION_KEY, node.getExpression());
		Condition newCondition = new Condition(node.getExpression(),CMLModelcheckerContext.GUARD_COUNTER++);
		question.guards.add(newCondition);
		//node.getExpression().apply(this, question);
		//question.getScriptContent().append(Utilities.OCCUR_COUNT++ + ",");
		//question.getScriptContent().append(CMLModelcheckerContext.GUARD_COUNTER++ + ",");
		// it writes the behaviour in the if-true branch
		node.getAction().apply(this, question);
		question.getScriptContent().append(",Stop)"); // the else branch of a
														// guarded action is
														// stop

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseASequentialCompositionAction(
			ASequentialCompositionAction node, CMLModelcheckerContext question)
			throws AnalysisException {
		// it writes the sequential composition constructor
		question.getScriptContent().append("seqC(");

		// it writes the first action
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");

		// it writes the second action
		node.getRight().apply(this, question);
		//if the action does not depend on the state.
		question.getScriptContent().append(")");
		//if(!question.info.containsKey(Utilities.STATES_KEY)){
		//	question.getScriptContent().append(")");
		//}

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
	public StringBuilder caseAReferenceAction(AReferenceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// the parameters also need to be written
		question.getScriptContent().append(
				"proc(\"" + node.getName() + "\", nopar)");

		// Adding auxiliary definitions
		// LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>)
		// question.info.get(node); ppppppppppppppp mudar para pegar de localActions
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
				.get(Utilities.LOCAL_DEFINITIONS_KEY);

		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		if (localDefinitions != null) {
			for (PDefinition pDefinition : localDefinitions) {
				pDefinition.apply(this, auxCtxt);
			}
		}
		int auxIndex = question.getScriptContent().indexOf(
				"#AUXILIARY_PROCESSES#");
		if (auxIndex != -1) {
			question.getScriptContent().replace(auxIndex,
					auxIndex + "#AUXILIARY_PROCESSES#".length(),
					auxCtxt.getScriptContent().toString());
			if(auxCtxt.getVariables().size() != 0){
				question.setVariables(auxCtxt.getVariables());
				question.copyVarDeclarationInfo(auxCtxt);
				question.copyVarDelInfo(auxCtxt);
				question.copyAssignmentDefInfo(auxCtxt);
			}
		}
		return question.getScriptContent();
	}

	// Visitor methods for expressions
	@Override
	public StringBuilder caseAEqualsBinaryExp(AEqualsBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					//question.getScriptContent().append(leftValue + " = " + rightValue);
				   question.positiveGuardExps.put(node, leftValue + " = " + rightValue);
				}else{
					//question.getScriptContent().append("GUARDNDEF#" + leftValue + " != " + rightValue);
					question.negativeGuardExps.put(node, leftValue + " != " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " = " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " != " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " = " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " != " + rightValue);
		}
		
		
		return question.getScriptContent();
	}

	
	
	@Override
	public StringBuilder caseANotEqualBinaryExp(ANotEqualBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {

		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					//question.getScriptContent().append(leftValue + " = " + rightValue);
				   question.positiveGuardExps.put(node, leftValue + " != " + rightValue);
				}else{
					//question.getScriptContent().append("GUARDNDEF#" + leftValue + " != " + rightValue);
					question.negativeGuardExps.put(node, leftValue + " = " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " != " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " = " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " != " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " = " + rightValue);
		}

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAGreaterNumericBinaryExp(
			AGreaterNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {

		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					//question.getScriptContent().append(leftValue + " = " + rightValue);
				   question.positiveGuardExps.put(node, leftValue + " > " + rightValue);
				}else{
					//question.getScriptContent().append("GUARDNDEF#" + leftValue + " != " + rightValue);
					question.negativeGuardExps.put(node, leftValue + " <= " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " > " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " <= " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " > " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " <= " + rightValue);
		}
		
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAGreaterEqualNumericBinaryExp(
			AGreaterEqualNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {
		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					//question.getScriptContent().append(leftValue + " = " + rightValue);
				   question.positiveGuardExps.put(node, leftValue + " >= " + rightValue);
				}else{
					//question.getScriptContent().append("GUARDNDEF#" + leftValue + " != " + rightValue);
					question.negativeGuardExps.put(node, leftValue + " < " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " >= " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " < " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " >= " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " < " + rightValue);
		}

		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseALessEqualNumericBinaryExp(
			ALessEqualNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					
				   question.positiveGuardExps.put(node, leftValue + " <= " + rightValue);
				}else{
					
					question.negativeGuardExps.put(node, leftValue + " > " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " <= " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " > " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " <= " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " > " + rightValue);
		}

		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseALessNumericBinaryExp(ALessNumericBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					//question.getScriptContent().append(leftValue + " = " + rightValue);
				   question.positiveGuardExps.put(node, leftValue + " < " + rightValue);
				}else{
					//question.getScriptContent().append("GUARDNDEF#" + leftValue + " != " + rightValue);
					question.negativeGuardExps.put(node, leftValue + " >= " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " < " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " >= " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " < " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " >= " + rightValue);
		}

		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseABooleanConstExp(ABooleanConstExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		if(ExpressionEvaluator.evaluate(node)){
			//question.getScriptContent().append("true");
			//nothing todo
		}else{
			question.getScriptContent().append("GUARDNDEF#");
		}
		return super.caseABooleanConstExp(node, question);
	}
	@Override
	public StringBuilder caseAAndBooleanBinaryExp(AAndBooleanBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(ExpressionEvaluator.evaluate(node)){
			question.getScriptContent().append("," + left.toString() + " , "+ right.toString()+" = true");
		}else{
			question.getScriptContent().append("GUARDNDEF#,"+left.toString()+" , "+right.toString()+" = false");
		}
		/*
		if(left instanceof ABooleanConstExp){
			if(right instanceof ABooleanConstExp){
				Boolean leftBoolean = new Boolean(left.toString());
				Boolean rightBoolean = new Boolean(right.toString());
				if(!(leftBoolean && rightBoolean)){
					question.getScriptContent().append("GUARDNDEF#"+left.toString()+" and "+right.toString()+" = false");
				} else{
					question.getScriptContent().append(left.toString() + " and "+ right.toString()+" = true");
				}
			}
		}
		*/
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseANotUnaryExp(ANotUnaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
	
		if(ExpressionEvaluator.evaluate(node)){
			//question.getScriptContent().append("not "+node.getExp().toString()+" = false");
			//nothing todo
		}else{
			//question.getScriptContent().append("GUARDNDEF#"+"not "+node.getExp().toString()+" = true");
			question.getScriptContent().append("GUARDNDEF#");
		}
		/*
		if(node.getExp() instanceof ABooleanConstExp){
			Boolean b = new Boolean(node.getExp().toString());
			if(!b){
				question.getScriptContent().append("GUARDNDEF#"+"not "+node.getExp().toString()+" = true");
			} else{
				question.getScriptContent().append("not "+node.getExp().toString()+" = false");
			}
		}
		*/
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAOrBooleanBinaryExp(AOrBooleanBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(ExpressionEvaluator.evaluate(node)){
			question.getScriptContent().append(left.toString() + " or "+ right.toString()+" = true");
		}else{
			question.getScriptContent().append("GUARDNDEF#"+left.toString()+" or "+right.toString()+" = false");
		}
		/*
		if(left instanceof ABooleanConstExp){
			if(right instanceof ABooleanConstExp){
				Boolean leftBoolean = new Boolean(left.toString());
				Boolean rightBoolean = new Boolean(right.toString());
				if(!(leftBoolean || rightBoolean)){
					question.getScriptContent().append("GUARDNDEF#"+left.toString()+" or "+right.toString()+" = false");
				} else{
					question.getScriptContent().append(left.toString() + " or "+ right.toString()+" = true");
				}
			}
		}
		*/
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAIntLiteralExp(AIntLiteralExp node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getValue());
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	// to be used by the command line tool
	public String getAnalysisName() {
		return ANALYSIS_NAME;
	}

	@Override
	public StringBuilder caseAValuesDefinition(AValuesDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		//question.info.put(Utilities.VALUE_DEFINITIONS_KEY, node.getValueDefinitions());
		for (PDefinition valueDef : node.getValueDefinitions()) {
			valueDef.apply(this, question);
		}
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAValueDefinition(AValueDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.valueDefinitions.add(new UserDefinedValue(node));
		/* lets put the information in the context to be processed as bellow in the partial 
		 * model construction.
		 
		if(node.getExpression() instanceof AIntLiteralExp){
		 
			question.getScriptContent().append("  primitive ");
		} else{
			question.getScriptContent().append("  ");
		}
		question.getScriptContent().append(node.getPattern().toString()+" ::= (");		
		node.getType().apply(this, question);
		question.getScriptContent().append(").\n");
		if(node.getExpression() instanceof ATimesNumericBinaryExp){
			question.getScriptContent().append("  "+node.getPattern().toString()+"("+node.getPattern().toString().toLowerCase()+") :- #, "+node.getPattern().toString().toLowerCase()+" = @");
			node.getExpression().apply(this, question);
			question.getScriptContent().append(".\n");
		}
		*/
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseANatNumericBasicType(ANatNumericBasicType node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("Natural");
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseATimesNumericBinaryExp(
			ATimesNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {
		int first = question.getScriptContent().indexOf("#");
		String aux = node.getLeft().toString()+"("+node.getLeft().toString().toLowerCase()+"), "+node.getRight().toString()+"("+node.getRight().toString().toLowerCase()+")";
		String aux2 = node.getLeft().toString().toLowerCase()+" "+node.getOp().toString()+" "+node.getRight().toString().toLowerCase();
		question.getScriptContent().replace(first, first+1, aux);
		int second = question.getScriptContent().indexOf("@");
		question.getScriptContent().replace(second, second+1, aux2);
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseATypesDefinition(ATypesDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		for (ATypeDefinition typeDef : node.getTypes()) {
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
	public StringBuilder caseADeclareStatementAction(
			ADeclareStatementAction node, CMLModelcheckerContext question)
			throws AnalysisException {
		node.getAssignmentDefs().getFirst().apply(this,question);
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAAssignmentDefinition(AAssignmentDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("var(\""+node.getName().toString()+"\",\"");
		node.getType().apply(this, question);
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
	public StringBuilder caseAIntNumericBasicType(AIntNumericBasicType node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		//question.getScriptContent().append("int");
		question.getScriptContent().append("Natural");
		
		return question.getScriptContent();
	}
	
	
	@Override
	public StringBuilder caseANamedInvariantType(ANamedInvariantType node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		//question.getScriptContent().append("primitive ");
		question.getScriptContent().append(node.getName());
		//question.getScriptContent().append("  ::= (");
		//node.getType().apply(this, question);
		//question.getScriptContent().append(")");
		return question.getScriptContent();
	}

	
	
	@Override
	public StringBuilder caseAChannelType(AChannelType node,
			CMLModelcheckerContext question) throws AnalysisException {
		if(node.getType() != null){
			node.getType().apply(this, question);
		}
			
		return question.getScriptContent();
	}
	
	
	
	@Override
	public StringBuilder caseAProductType(AProductType node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		LinkedList<PType> types = new LinkedList<PType>(node.getTypes());
		do{
			PType auxType = types.pollFirst();
			question.getScriptContent().append(auxType.toString());
			if(!types.isEmpty()){
				question.getScriptContent().append(",");
			}
		}while(!types.isEmpty());
		
		
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
		/*
		a.getExpression().apply(this, aux);
		aux.getScriptContent().replace(aux.getScriptContent().indexOf("(")+1, aux.getScriptContent().indexOf(")"), node.getStateDefs().getFirst().getName().toString());
		
		StringBuilder s = new StringBuilder(" fetch(\"");
		s.append(node.getStateDefs().getFirst().getName().toString());
		s.append("\",_,");
		s.append(aux.getScriptContent().toString());
		s.append(")");
		
		question.putStates(Utilities.STATES_KEY, s);
		*/
		//question.updateStates(node.getStateDefs().getFirst().getName().toString());
		//pppp
		question.info.put(Utilities.DEL_BBINDING, "del");
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder defaultPAction(PAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	public String[] generateFormulaCodeForAll() throws IOException,
			AnalysisException {
		// StringBuilder basicContent = new StringBuilder();
		String[] codes = new String[0];
		if (sources.size() > 0) {
			codes = new String[sources.size()];
			// basicContent =
			// Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT);
		}

		for (PSource source : sources) {
			CMLModelcheckerContext context = new CMLModelcheckerContext();
			//PPPPPPP
			this.basicContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.BASIC_FORMULA_SCRIPT);
			
			// content.getScriptContent().append(basicContent.toString());
			for (PDefinition paragraph : source.getParagraphs()) {
				try {
					paragraph.apply(this, context);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
			codes[sources.indexOf(source)] = basicContent + "\n" + context.getScriptContent()
					.toString();
			// it saves the current generated formula file
			// String formulaFileName =
			// Utilities.generateFormulaFileName(((AFileSource)source).getFile().getPath());
			// System.out.println(formulaFileName);
			// Utilities.writeScriptToFile(formulaFileName,content);

		}
		return codes;
	}
	
	public static String generateFormulaScript(String basicContent, List<PDefinition> definitions, String propertyToCheck) throws IOException, AnalysisException{
				
		CMLModelcheckerContext content = new CMLModelcheckerContext();
		//adds the basic content (semantics embedding) to the generated script
		
		//content.getScriptContent().append(FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.BASIC_FORMULA_SCRIPT));
		//content.getScriptContent().append(basicContent); ppppppppppppppp
		CMLModelcheckerVisitor mcVisitor = new CMLModelcheckerVisitor();
		mcVisitor.setPropertyToCheck(propertyToCheck);
		mcVisitor.basicContent.append(basicContent);
		content.getScriptContent().append(mcVisitor.basicContent);
		for (PDefinition paragraph : definitions) {
			//try {
				paragraph.apply(mcVisitor, content);
			//} catch (Exception e) {
			//	System.out.println("Error: " + e.getMessage());
			//}
		}
		return content.getScriptContent().toString();
	}
	
	

	
	
	
	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		//String cml_example = "src/test/resources/action-operationcall-noargs.cml";
		//String cml_example = "src/test/resources/replicated-externalchoice.cml";
		//String cml_example = "src/test/resources/replicated-generalised-parallelism.cml";
		//String cml_example = "src/test/resources/action-seq-comp2.cml";
		//String cml_example = "src/test/resources/action-vardecl.cml";
		//String cml_example = "src/test/resources/action-vardecl2.cml";
		//String cml_example = "src/test/resources/action-reference-parametrised.cml";
		//String cml_example = "src/test/resources/action-guard-stateVar.cml";
		//String cml_example = "src/test/resources/action-guard.cml";
		//String cml_example = "src/test/resources/minimondex.cml";
		//String cml_example = "src/test/resources/replicated-seqcomp.cml";
		//String cml_example = "src/test/resources/Dphils.cml";
		String cml_example = "src/test/resources/action-guard-stateVar.cml";
		System.out.println("Testing on " + cml_example);
		// List<PSource> sources = new LinkedList<PSource>();
		PSource source = Utilities.makeSourceFromFile(cml_example);
		// sources.add(source);
		//String basic = Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT).toString();
		//String code = CMLModelcheckerVisitor.generateFormulaScript(basic, source.getParagraphs(), Utilities.DEADLOCK_PROPERTY);
		//System.out.println(code);
		CMLModelcheckerVisitor visitor = new CMLModelcheckerVisitor(source);
		visitor.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
		String[] codes = visitor.generateFormulaCodeForAll();
		for (int i = 0; i < codes.length; i++) {
			System.out.println(codes[i]);
		}
		System.out.println("Analysing generated FORMULA specification");
		IFormulaIntegrator mc = FormulaIntegrator.getInstance();
		FormulaResult mcResult = mc.analyse(codes[0]);

		System.out.println("File " + cml_example + " is "
				+ (mcResult.isSatisfiable() ? "SAT" : "UNSAT") + "\n");
		double loadTime = mcResult.getElapsedTimeLoad();
		double solveTime = mcResult.getElapsedTimeSolve();
		System.out.println("Analysis time (load + solve) = " + "(" + loadTime
				+ " + " + solveTime + ") = " + (loadTime + solveTime)
				+ " seconds\n");
		System.out.println("Base of Facts: \n");
		System.out.println(mcResult.getFacts());
		mc.finalize();
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
