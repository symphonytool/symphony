package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.AIfStm;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.ast.statements.AUnresolvedStateDesignator;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.GuardDefGenerator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCAssignDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NewMCGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAAssignmentDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAActionStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAAssignmentStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCABlockSimpleBlockStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCACallStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAIfStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAUnresolvedStateDesignator;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;

public class NewMCStmVisitor extends
		QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;
	
	public NewMCStmVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	//FOR THE CASE OF TYPES WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public MCNode defaultPStm(PStm node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	

	@Override
	public MCNode caseAActionStm(AActionStm node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction action = (MCPAction) node.getAction().apply(rootVisitor, question);
		MCAActionStm result = new MCAActionStm(action);
		
		return result;
	}

	
	@Override
	public MCNode caseAAssignmentStm(AAssignmentStm node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLExp expression = null;
		if(node.getExp() != null) {
			expression = (MCPCMLExp) node.getExp().apply(rootVisitor, question);
		}
		MCAUnresolvedStateDesignator target = (MCAUnresolvedStateDesignator) node.getTarget().apply(rootVisitor, question);
		MCAAssignmentStm result = new MCAAssignmentStm(expression,target);
		INode ancestor = node.parent();
		if(ancestor instanceof AStmAction){
			ancestor = ancestor.parent();
			if(ancestor instanceof AActionStm){
				ancestor = ancestor.parent();
				if(!(ancestor instanceof AExplicitOperationDefinition)){
					//assignments inside operation definitions should not originate assign defs.
					MCAssignDef assignDef = new MCAssignDef(result.getCounterId(), expression,target.getPath(), result);
					question.assignDefs.add(assignDef);
				}
			}
		}
		
		
		return result; 
	}

	
	@Override
	public MCNode caseAUnresolvedStateDesignator(
			AUnresolvedStateDesignator node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLExp path = (MCPCMLExp) node.getPath().apply(rootVisitor, question);
		MCAUnresolvedStateDesignator result = new MCAUnresolvedStateDesignator(path);
		
		return result;
	}

	
	@Override
	public MCNode caseABlockSimpleBlockStm(ABlockSimpleBlockStm node,
			NewCMLModelcheckerContext question) throws AnalysisException {
	
		LinkedList<MCAAssignmentDefinition> assignDefs= new LinkedList<MCAAssignmentDefinition>();
		
		for (AAssignmentDefinition aAssignmentDefinition : node.getAssignmentDefs()) {
			assignDefs.add((MCAAssignmentDefinition) aAssignmentDefinition.apply(rootVisitor, question));
		}
		
		LinkedList<MCPCMLStm> statements = new LinkedList<MCPCMLStm>();
		for (PStm pStm : node.getStatements()) {
			statements.add((MCPCMLStm) pStm.apply(this, question));
		}
		
		MCABlockSimpleBlockStm result = new MCABlockSimpleBlockStm(assignDefs, statements);
		
		return result;
	}

	
	@Override
	public MCNode caseACallStm(ACallStm node, NewCMLModelcheckerContext question)
			throws AnalysisException {

		String name = node.getName().toString();
		LinkedList<MCPCMLExp> args = new LinkedList<MCPCMLExp>();
		for (PExp pExp : node.getArgs()) {
			args.add((MCPCMLExp) pExp.apply(rootVisitor, question));
		}
		MCACallStm result = new MCACallStm(name, args);
		
		return result;
	}

	
	@Override
	public MCNode caseAIfStm(AIfStm node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLExp ifExp = (MCPCMLExp) node.getIfExp().apply(rootVisitor, question);
		MCPCMLStm thenStm = (MCPCMLStm) node.getThenStm().apply(this, question);
		MCPCMLStm elseStm = (MCPCMLStm) node.getElseStm().apply(this, question);
		
		MCAIfStm result = new MCAIfStm(ifExp, thenStm, elseStm);
		
		LinkedList<NewMCGuardDef> guarDefs = GuardDefGenerator.generateGuardDefs(ifExp, result.getCounterId(), result);
		question.stmGuardDefs.put(ifExp, guarDefs);
		
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
	/////  VALUES

}
