package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.SingleBind;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class MCDeclarationAndDefinitionVisitor extends
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {

	final private QuestionAnswerAdaptor<CMLModelcheckerContext, StringBuilder> rootVisitor;
	
	public MCDeclarationAndDefinitionVisitor(QuestionAnswerAdaptor<CMLModelcheckerContext, StringBuilder> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	//FOR THE CASE OF DEFINITIONS AND DECLARATIONS WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public StringBuilder defaultPCMLDefinition(PCMLDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
		
	@Override
	public StringBuilder defaultPSingleDeclaration(PSingleDeclaration node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	//// DEFINITIONS
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
