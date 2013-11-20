package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAGeneralisedParallelismParallelAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionClassDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAActionProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAGeneralisedParallelismProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAHidingProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAReferenceProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;

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
		MCAActionClassDefinition classDefinition = (MCAActionClassDefinition) node.getActionDefinition().apply(rootVisitor, question); 
		MCPAction mcAction = (MCPAction) node.getAction().apply(rootVisitor, question);
		MCAActionProcess result = new MCAActionProcess(classDefinition,mcAction);
		
		return result;
		
		
	}
	private PAction findRealAction(AActionProcess node,AReferenceAction action){
		PAction result = null;
		boolean found = false; 
		for (PDefinition definition : node.getActionDefinition().getDefinitions()) {
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
	public MCNode caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPVarsetExpression chanSetExpression = (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		MCAGeneralisedParallelismProcess result = new MCAGeneralisedParallelismProcess(left, chanSetExpression, right);
		
		question.globalChanSets.add(chanSetExpression);
		
		return result;
		
	}


	

	@Override
	public MCNode caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		LinkedList<PSingleDeclaration> replicationDecls = node.getReplicationDeclaration();
		LinkedList<MCPSingleDeclaration> declarations = new LinkedList<MCPSingleDeclaration>();
		for (PSingleDeclaration s : replicationDecls) {
			declarations.add((MCPSingleDeclaration)s.apply(rootVisitor, question));
		}
		MCPProcess replicatedProc = (MCPProcess) node.getReplicatedProcess().apply(rootVisitor, question);
		MCPVarsetExpression chansetExpression =  (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		
		question.globalChanSets.add(chansetExpression);
		
		MCAAlphabetisedParallelismReplicatedProcess result = new MCAAlphabetisedParallelismReplicatedProcess(declarations, replicatedProc, chansetExpression);
		
		return result;
	}

	@Override
	public MCNode caseAReferenceProcess(AReferenceProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = node.getProcessName().getName();
		LinkedList<MCPCMLExp> args = new LinkedList<MCPCMLExp>();
		LinkedList<PExp> arguments = node.getArgs();
		for (PExp pExp : arguments) {
			args.add((MCPCMLExp) pExp.apply(rootVisitor, question));
		}
		MCAProcessDefinition procDef = null;
		if (node.getProcessDefinition() != null) {
			procDef = (MCAProcessDefinition) node.getProcessDefinition().apply(rootVisitor, question);
		} 
		
		MCAReferenceProcess result = new MCAReferenceProcess(name, args, procDef);
		
		return result;
	}



	@Override
	public MCNode caseAHidingProcess(AHidingProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		MCPProcess proc = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPVarsetExpression chansetExp = (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		return new MCAHidingProcess(chansetExp, proc);
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
