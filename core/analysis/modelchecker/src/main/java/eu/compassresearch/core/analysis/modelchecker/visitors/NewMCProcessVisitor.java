package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AExternalChoiceReplicatedProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionClassDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAActionProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAExternalChoiceProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAExternalChoiceReplicatedProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAGeneralisedParallelismProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAHidingProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAInterleavingProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAInternalChoiceProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAInterruptProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAReferenceProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCASequentialCompositionProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCATimedInterruptProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCATimeoutProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAUntimedTimeoutProcess;
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
		
		// it applies to each definition of this action process
		MCAActionClassDefinition classDefinition = (MCAActionClassDefinition) node.getActionDefinition().apply(rootVisitor, question); 
		MCPAction mcAction = (MCPAction) node.getAction().apply(rootVisitor, question);
		MCAActionProcess result = new MCAActionProcess(classDefinition,mcAction);
		
		return result;
		
		
	}

	@Override
	public MCNode caseAInterruptProcess(AInterruptProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		
		MCAInterruptProcess result = new MCAInterruptProcess(left,right); 

		return result;
	}



	@Override
	public MCNode caseAInterleavingProcess(AInterleavingProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		MCAInterleavingProcess result = new MCAInterleavingProcess(left, right);
		
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
	public MCNode caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<PSingleDeclaration> replicationDecls = node.getReplicationDeclaration();
		LinkedList<MCPSingleDeclaration> replicationDeclarations = new LinkedList<MCPSingleDeclaration>();
		for (PSingleDeclaration pSingleDecl : replicationDecls) {
			replicationDeclarations.add((MCPSingleDeclaration) pSingleDecl.apply(rootVisitor, question));
		}
		MCPProcess replicatedProc = (MCPProcess) node.getReplicatedProcess().apply(rootVisitor, question);
		MCAExternalChoiceReplicatedProcess result = 
				new MCAExternalChoiceReplicatedProcess(replicationDeclarations, replicatedProc);
		
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
		
		question.setStack.add(chansetExp);
		
		return new MCAHidingProcess(chansetExp, proc);
	}

	

	@Override
	public MCNode caseASequentialCompositionProcess(
			ASequentialCompositionProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		MCASequentialCompositionProcess result = new MCASequentialCompositionProcess(left, right);
		
		return result;
	}


	

	@Override
	public MCNode caseAExternalChoiceProcess(AExternalChoiceProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		MCAExternalChoiceProcess result = new MCAExternalChoiceProcess(left, right);
		
		return result;
	}


	

	@Override
	public MCNode caseAInternalChoiceProcess(AInternalChoiceProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		MCAInternalChoiceProcess result = new MCAInternalChoiceProcess(left, right);
		
		return result;
	}


	

	@Override
	public MCNode caseATimedInterruptProcess(ATimedInterruptProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		MCPCMLExp timeExpression = (MCPCMLExp) node.getTimeExpression().apply(rootVisitor, question);
		MCATimedInterruptProcess result = new MCATimedInterruptProcess(left, right, timeExpression);
		
		//we need to update the maximum value for clock in the context 
		question.updateMaxClock(timeExpression);
		
		return result;
	}


	

	@Override
	public MCNode caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		MCAUntimedTimeoutProcess result = new MCAUntimedTimeoutProcess(left,right);

		return result;
	}



	@Override
	public MCNode caseATimeoutProcess(ATimeoutProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		MCPProcess left = (MCPProcess) node.getLeft().apply(rootVisitor, question);
		MCPProcess right = (MCPProcess) node.getRight().apply(rootVisitor, question);
		MCPCMLExp timeoutExpression = (MCPCMLExp) node.getTimeoutExpression().apply(rootVisitor, question);
		MCATimeoutProcess result = new MCATimeoutProcess(left, right, timeoutExpression);
		
		//we need to update the maximum value for clock in the context 
		question.updateMaxClock(timeoutExpression);
		
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
