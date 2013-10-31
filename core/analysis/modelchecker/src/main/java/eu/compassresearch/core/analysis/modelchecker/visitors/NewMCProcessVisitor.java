package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAActionProcess;

public class NewMCProcessVisitor extends
		QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;
	
	public NewMCProcessVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	
	
	@Override
	public MCNode caseAActionProcess(AActionProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		/*PAction mainAction = node.getAction();
		if(mainAction instanceof AReferenceAction){
			mainAction = findRealAction(node,(AReferenceAction) mainAction);
		}
		if(mainAction instanceof AHidingAction){
			PVarsetExpression chanSet = ((AHidingAction) mainAction).getChansetExpression();
			question.setStack.add((MCPVarsetExpression) chanSet.apply(rootVisitor, question));
		}
		*/
		// it applies to each definition of this action process
		LinkedList<MCPCMLDefinition> mcDefinitionParagraphs = new LinkedList<MCPCMLDefinition>(); 
		for (PDefinition definition : node.getDefinitionParagraphs()) {
			mcDefinitionParagraphs.add((MCPCMLDefinition) definition.apply(rootVisitor, question));
		}
			
		MCPAction mcAction = (MCPAction) node.getAction().apply(rootVisitor, question);
		MCAActionProcess result = new MCAActionProcess(mcDefinitionParagraphs,mcAction);
		
		return result;
		
		
	}
	private PAction findRealAction(AActionProcess node,AReferenceAction action){
		PAction result = null;
		boolean found = false; 
		for (PDefinition definition : node.getDefinitionParagraphs()) {
			if(definition instanceof AActionsDefinition){
				for (AActionDefinition actionDef : ((AActionsDefinition) definition).getActions()) {
					if(actionDef.getName().toString().equals(action.getName().toString())){
						result = actionDef.getAction();
						found = true;
						break;
					}
				}
				if(found){
					break;
				}
				
			}
		}
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
