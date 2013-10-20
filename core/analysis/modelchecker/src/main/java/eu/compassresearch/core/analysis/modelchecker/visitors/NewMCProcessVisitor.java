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
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAActionProcess;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;

public class NewMCProcessVisitor extends
		QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;
	
	public NewMCProcessVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	
	
	@Override
	public MCNode caseAActionProcess(AActionProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		
		// it applies to each definition of this action process
		LinkedList<MCPCMLDefinition> mcDefinitionParagraphs = new LinkedList<MCPCMLDefinition>(); 
		for (PDefinition definition : node.getDefinitionParagraphs()) {
			mcDefinitionParagraphs.add((MCPCMLDefinition) definition.apply(rootVisitor, question));
		}
			
		MCPAction mcAction = (MCPAction) node.getAction().apply(rootVisitor, question);
		MCAActionProcess result = new MCAActionProcess(mcDefinitionParagraphs,mcAction);
		
		return result;
		
		
	}
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
