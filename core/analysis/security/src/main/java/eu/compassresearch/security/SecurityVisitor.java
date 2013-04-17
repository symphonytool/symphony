package eu.compassresearch.security;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.ALenUnaryExp;
import org.overture.ast.expressions.APlusNumericBinaryExp;
import org.overture.ast.expressions.ASubtractNumericBinaryExp;
import org.overture.ast.expressions.ATailUnaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.node.NodeList;
import org.overture.ast.node.NodeListList;
import org.overture.ast.statements.ACasesStm;
import org.overture.ast.types.AFieldField;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ACaseAlternativeAction;
import eu.compassresearch.ast.actions.ACasesStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.security.SecurityEnvironment.Judgement;
import eu.compassresearch.security.SecurityEnvironment.SecurityJudgement;
import eu.compassresearch.security.SecurityEnvironment.SubtreeJudgement;



@SuppressWarnings("serial")
class SecurityVisitor extends QuestionAnswerCMLAdaptor<SecurityEnvironment, SubtreeJudgement>
{

	@Override
	public SubtreeJudgement caseAAssignmentCallStatementAction(
			AAssignmentCallStatementAction node, SecurityEnvironment question)
					throws AnalysisException {
		return super.caseAAssignmentCallStatementAction(node, question);
	}

	@Override
	public SubtreeJudgement caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node,
			SecurityEnvironment question) throws AnalysisException {
		return super.caseASingleGeneralAssignmentStatementAction(node, question);
	}

	@Override
	public SubtreeJudgement caseAAssignmentDefinition(
			AAssignmentDefinition node, SecurityEnvironment question)
					throws AnalysisException {
		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp exp = node.getExpression();

		if (exp != null) {
			SubtreeJudgement expJudgement =  exp.apply(parent,question);
			if (expJudgement != null) {
				result.setJudgement(expJudgement.getJudgement());
				result.getInvolvedJudgements().addAll(expJudgement.getInvolvedJudgements());
			}
		}

		return result;
	}

	private  QuestionAnswerCMLAdaptor<SecurityEnvironment, SubtreeJudgement> parent;

	public SecurityVisitor() {
		this.parent = this;
	}

	public SecurityVisitor( QuestionAnswerCMLAdaptor<SecurityEnvironment, SubtreeJudgement> parent) {
		this.parent = parent;
	}

	@Override
	public SubtreeJudgement caseAValueDefinition(AValueDefinition node,
			SecurityEnvironment question) throws AnalysisException{

		question.addJudgement(node.getName(), SecurityJudgement.Public);
		return super.caseAValueDefinition(node, question);
	}

	@Override 
	public SubtreeJudgement caseAValParametrisation(AValParametrisation node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node); 

		LinkedList<ILexIdentifierToken> ids = node.getDeclaration().getIdentifiers();
		for(ILexIdentifierToken id : ids) {
			question.addJudgement(id, SecurityJudgement.fromId(id));
		}

		return result;
	}





	@Override
	public SubtreeJudgement caseAReadCommunicationParameter(
			AReadCommunicationParameter node, SecurityEnvironment question)
					throws AnalysisException {
		return super.caseAReadCommunicationParameter(node, question);
	}



	@Override
	public SubtreeJudgement caseAApplyExp(AApplyExp node,
			SecurityEnvironment question) throws AnalysisException {
		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp target = node.getRoot();
		SubtreeJudgement targetJudgement = target.apply(parent,question);
		if (targetJudgement != null) {
			result.getInvolvedJudgements().addAll(targetJudgement.getInvolvedJudgements());
			result.setJudgement(targetJudgement.getJudgement());
		} 

		return result;
	}

	@Override
	public SubtreeJudgement caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		SubtreeJudgement actionJudgement = node.getReplicatedAction().apply(parent,question);
		if (actionJudgement != null) {
			result.getInvolvedJudgements().addAll(actionJudgement.getInvolvedJudgements());
			result.setJudgement(actionJudgement.getJudgement());
		}

		return result;
	}



	@Override
	public SubtreeJudgement caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PAction repAction = node.getReplicatedAction();
		SubtreeJudgement repActionJudgement = repAction.apply(parent,question);

		result.setJudgement(repActionJudgement.getJudgement());
		result.getInvolvedJudgements().addAll(repActionJudgement.getInvolvedJudgements());

		return result;
	}



	@Override
	public SubtreeJudgement caseABracketedExp(ABracketedExp node,
			SecurityEnvironment question) throws AnalysisException {
		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp exp = node.getExpression();
		SubtreeJudgement expJudgement = exp.apply(parent,question);
		if (expJudgement != null) {
			result.getInvolvedJudgements().addAll(expJudgement.getInvolvedJudgements());
			result.setJudgement(expJudgement.getJudgement());
		}

		return result;
	}



	@Override
	public SubtreeJudgement caseAEqualsBinaryExp(AEqualsBinaryExp node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp left = node.getLeft();
		PExp right = node.getRight();

		SubtreeJudgement leftJ = left.apply(parent,question);
		if (leftJ != null) {
			result.getInvolvedJudgements().addAll(leftJ.getInvolvedJudgements());
		}

		SubtreeJudgement rightJ = right.apply(parent,question);
		if (rightJ != null) {
			result.getInvolvedJudgements().addAll(rightJ.getInvolvedJudgements());
		}

		return result;
	}

	@Override
	public SubtreeJudgement caseALenUnaryExp(ALenUnaryExp node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp exp = node.getExp();

		SubtreeJudgement expJudgement = exp.apply(parent,question);
		if (expJudgement != null) {
			result.getInvolvedJudgements().addAll(expJudgement.getInvolvedJudgements());
			result.setJudgement(expJudgement.getJudgement());
		}

		return result;
	}



	@Override
	public SubtreeJudgement defaultPAction(PAction node,
			SecurityEnvironment question) throws AnalysisException {

		return handleDefault(node, question);
	}

	@Override
	public SubtreeJudgement caseAIfStatementAction(AIfStatementAction node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp test = node.getIfExp();
		SubtreeJudgement testJudgement = test.apply(parent,question);
		if (testJudgement != null) {
			result.getInvolvedJudgements().addAll(testJudgement.getInvolvedJudgements());
		}

		PAction thenAction = node.getThenStm();
		SubtreeJudgement thenActionJudgement = thenAction.apply(parent,question);
		if (thenActionJudgement !=null) {
			result.getInvolvedJudgements().addAll(thenActionJudgement.getInvolvedJudgements());
		}

		PAction elseAction = node.getElseStm();
		if (elseAction != null) {
			SubtreeJudgement elseActionJudgement = elseAction.apply(parent,question);
			if (elseActionJudgement != null) {
				result.getInvolvedJudgements().addAll(elseActionJudgement.getInvolvedJudgements());
			}
		}
		return result;
	}



	@Override
	public SubtreeJudgement caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node, SecurityEnvironment question)
					throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp exp = node.getExpression();
		SubtreeJudgement expJudgement = exp.apply(parent,question);

		if (expJudgement != null) {
			if (expJudgement.getJudgement() == SecurityJudgement.Random)
			{
				boolean hasSecret = false;
				int numRandom = 0;
				for(Judgement j : expJudgement.getInvolvedJudgements()) {
					if (j.getJudgement() == SecurityJudgement.Secret) hasSecret = true;
					if (j.getJudgement() == SecurityJudgement.Random) numRandom++;
				}
				if (hasSecret)
					for(Judgement j : expJudgement.getInvolvedJudgements()) {
						j.updateJudgement(SecurityJudgement.Secret);
					}
				else
					if (numRandom == 1) {
						for(Judgement j : expJudgement.getInvolvedJudgements()) {
							if (j.getJudgement() == SecurityJudgement.Random)
								j.updateJudgement(SecurityJudgement.Public);
						}						
					}
			}
			else
			{
				for(Judgement j : expJudgement.getInvolvedJudgements()) {
					j.updateJudgement(SecurityJudgement.Public);
				}

			}
		}
		return result;
	}



	@Override
	public SubtreeJudgement caseAUnresolvedPathExp(AUnresolvedPathExp node,
			SecurityEnvironment question) throws AnalysisException {
		SubtreeJudgement result = new SubtreeJudgement(node);


		PDefinition root = node.getType().getDefinitions().get(0);
		SubtreeJudgement rootJudgement = root.apply(parent,question);
		if (rootJudgement != null) {
			result.setJudgement(rootJudgement.getJudgement());
			result.getInvolvedJudgements().addAll(rootJudgement.getInvolvedJudgements());
		}

		return result;
	}

	@Override
	public SubtreeJudgement caseAFieldField(AFieldField node,
			SecurityEnvironment question) throws AnalysisException {


		return super.caseAFieldField(node, question);
	}

	@Override
	public SubtreeJudgement caseAVariableExp(AVariableExp node,
			SecurityEnvironment question) throws AnalysisException {

		Judgement j = question.getJudgement(node.getName());
		if (j == null) return new SubtreeJudgement(node);
		SubtreeJudgement result = new SubtreeJudgement(node,j.getJudgement(),new JudgementList(j));

		return result;
	}

	@Override
	public SubtreeJudgement caseAProcessDefinition(AProcessDefinition node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);


		LinkedList<PSingleDeclaration> localS = node.getLocalState();
		for(PSingleDeclaration singleDecl : localS) {
			SubtreeJudgement singleDeclJudgement = singleDecl.apply(this,question);
			if (singleDeclJudgement != null) result.getInvolvedJudgements().addAll(singleDeclJudgement.getInvolvedJudgements());
		}

		PProcess process = node.getProcess();
		SubtreeJudgement processJudgement = process.apply(parent,question);
		if (processJudgement != null) result.getInvolvedJudgements().addAll(processJudgement.getInvolvedJudgements());


		return result;
	}



	@Override
	public SubtreeJudgement caseACommunicationAction(ACommunicationAction node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node); 

		LinkedList<PCommunicationParameter> params = node.getCommunicationParameters();
		for(PCommunicationParameter param : params) {
			SubtreeJudgement list = param.apply(parent,question);
			if (list != null) result.getInvolvedJudgements().addAll(list.getInvolvedJudgements());
		}

		PAction action = node.getAction();
		SubtreeJudgement actionJ = action.apply(parent,question);
		if (actionJ != null) {
			result.getInvolvedJudgements().addAll(actionJ.getInvolvedJudgements());
		}

		return result;
	}



	@Override
	public SubtreeJudgement caseASignalCommunicationParameter(
			ASignalCommunicationParameter node, SecurityEnvironment question)
					throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp exp = node.getExpression();
		SubtreeJudgement expJudgement = exp.apply(parent,question);

		if (expJudgement != null) {
			if (expJudgement.getJudgement() == SecurityJudgement.Random)
			{
				boolean hasSecret = false;
				int numRandom = 0;
				for(Judgement j : expJudgement.getInvolvedJudgements()) {
					if (j.getJudgement() == SecurityJudgement.Secret) hasSecret = true;
					if (j.getJudgement() == SecurityJudgement.Random) numRandom++;
				}
				if (hasSecret)
					for(Judgement j : expJudgement.getInvolvedJudgements()) {
						j.updateJudgement(SecurityJudgement.Secret);
					}
				else
					if (numRandom == 1) {
						for(Judgement j : expJudgement.getInvolvedJudgements()) {
							if (j.getJudgement() == SecurityJudgement.Random)
								j.updateJudgement(SecurityJudgement.Public);
						}						
					}
			}
			else
			{
				for(Judgement j : expJudgement.getInvolvedJudgements()) {
					j.updateJudgement(SecurityJudgement.Public);
				}

			}
		}
		return result;
	}

	@Override
	public SubtreeJudgement caseAPlusNumericBinaryExp(APlusNumericBinaryExp node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp left = node.getLeft();
		PExp right = node.getRight();

		SubtreeJudgement leftJ = left.apply(parent,question);
		if (leftJ != null) {
			result.getInvolvedJudgements().addAll(leftJ.getInvolvedJudgements());
		}

		SubtreeJudgement rightJ= right.apply(parent,question);
		if (rightJ != null) {
			result.getInvolvedJudgements().addAll(rightJ.getInvolvedJudgements());
		}

		if (leftJ.getJudgement() == SecurityJudgement.Secret && rightJ.getJudgement() != SecurityJudgement.Random) 
			result.setJudgement(SecurityJudgement.Secret);

		if (rightJ.getJudgement() == SecurityJudgement.Secret && leftJ.getJudgement() != SecurityJudgement.Random) 
			result.setJudgement(SecurityJudgement.Secret);


		if (leftJ.getJudgement() == SecurityJudgement.Random) {
			result.setJudgement(SecurityJudgement.Random);
			if (rightJ.getJudgement() == SecurityJudgement.Secret) {
				for(Judgement j : rightJ.getInvolvedJudgements()) {
					if (j.getJudgement() == SecurityJudgement.Secret) {
						for(Judgement jj : leftJ.getInvolvedJudgements()) {
							if (jj.getJudgement() == SecurityJudgement.Random) {
								jj.getHides().add(j);
							}
						}
					}
				}
			}
		}

		if (rightJ.getJudgement() == SecurityJudgement.Random){
			result.setJudgement(SecurityJudgement.Random);
			if (leftJ.getJudgement() == SecurityJudgement.Secret) {
				for(Judgement j : leftJ.getInvolvedJudgements()) {
					if (j.getJudgement() == SecurityJudgement.Secret) {
						for(Judgement jj : rightJ.getInvolvedJudgements()) {
							if (jj.getJudgement() == SecurityJudgement.Random) {
								jj.getHides().add(j);
							}
						}
					}
				}
			}
		}

		return result;

	}

	@Override
	public SubtreeJudgement caseASubtractNumericBinaryExp(
			ASubtractNumericBinaryExp node, SecurityEnvironment question)
					throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp left = node.getLeft();
		PExp right = node.getRight();

		SubtreeJudgement leftJ = left.apply(parent,question);
		if (leftJ != null) {
			result.getInvolvedJudgements().addAll(leftJ.getInvolvedJudgements());
		}

		SubtreeJudgement rightJ= right.apply(parent,question);
		if (rightJ != null) {
			result.getInvolvedJudgements().addAll(rightJ.getInvolvedJudgements());
		}

		if (leftJ.getJudgement() == SecurityJudgement.Secret && rightJ.getJudgement() != SecurityJudgement.Random) 
			result.setJudgement(SecurityJudgement.Secret);

		if (rightJ.getJudgement() == SecurityJudgement.Secret && leftJ.getJudgement() != SecurityJudgement.Random) 
			result.setJudgement(SecurityJudgement.Secret);


		if (leftJ.getJudgement() == SecurityJudgement.Random)
			result.setJudgement(SecurityJudgement.Random);

		if (rightJ.getJudgement() == SecurityJudgement.Random)
			result.setJudgement(SecurityJudgement.Random);

		return result;
	}

	@Override
	public SubtreeJudgement caseAActionsDefinition(AActionsDefinition node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		for(PDefinition action : node.getActions()) {
			SubtreeJudgement list = action.apply(parent,question);
			if (list!=null) result.getInvolvedJudgements().addAll(list.getInvolvedJudgements());
		}

		return result;
	}

	@Override
	public SubtreeJudgement caseAActionProcess(AActionProcess node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		for(PDefinition d : node.getDefinitionParagraphs()) {
			SubtreeJudgement dJudgements = d.apply(parent,question);
			if (dJudgements != null) result.getInvolvedJudgements().addAll(dJudgements.getInvolvedJudgements());
		}

		return result;
	}

	@Override
	public SubtreeJudgement caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, SecurityEnvironment question)
					throws AnalysisException {

		SubtreeJudgement res = new SubtreeJudgement(node);

		LinkedList<ILexIdentifierToken> ids = node.getIdentifiers();
		for(ILexIdentifierToken id : ids) {
			res.getInvolvedJudgements().add(question.addJudgement(id, SecurityJudgement.fromId(id)));
		}

		return res;
	}



	@Override
	public SubtreeJudgement caseAStateDefinition(AStateDefinition node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		for(PDefinition def : node.getStateDefs()) {
			SubtreeJudgement defJ = def.apply(parent,question);
			if (defJ != null) {
				result.getInvolvedJudgements().addAll(defJ.getInvolvedJudgements());
			}
			if (def instanceof AAssignmentDefinition) {
				AAssignmentDefinition adef = (AAssignmentDefinition)def;
				question.addJudgement(adef.getName(), defJ.getJudgement());
			}
		}

		return result;
	}



	@Override
	public SubtreeJudgement caseAReferenceAction(AReferenceAction node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);


		return result;
	}



	@Override
	public SubtreeJudgement caseATailUnaryExp(ATailUnaryExp node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PExp exp = node.getExp();
		SubtreeJudgement expJ = exp.apply(parent,question);
		if (expJ != null) {
			result.setJudgement(expJ.getJudgement());
			result.getInvolvedJudgements().addAll(expJ.getInvolvedJudgements());
		}

		return result;
	}

	@Override
	public SubtreeJudgement caseASequentialCompositionAction(
			ASequentialCompositionAction node, SecurityEnvironment question)
					throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PAction left = node.getLeft();
		PAction right = node.getRight();

		SubtreeJudgement leftJudgement = left.apply(parent,question);
		SubtreeJudgement rightJudgement = right.apply(parent,question);

		if (leftJudgement != null) result.getInvolvedJudgements().addAll(leftJudgement.getInvolvedJudgements());
		if (rightJudgement != null) result.getInvolvedJudgements().addAll(rightJudgement.getInvolvedJudgements());


		return result;
	}

	private SubtreeJudgement handleDefault(INode node, SecurityEnvironment question) throws AnalysisException {
		SubtreeJudgement res = new SubtreeJudgement(node);

		for(Object d : node.getChildren(false).values()) {
			SubtreeJudgement tmp = null;

			// is it a node list
			if (d instanceof NodeList) {

				NodeList<INode> nodes = (NodeList<INode>)d;
				for(INode n : nodes) { 
					tmp = n.apply(parent,question);
					if (tmp != null) res.getInvolvedJudgements().addAll(tmp.getInvolvedJudgements());
				}
			}

			// is it a node list list
			if (d instanceof NodeListList) {
				NodeListList<INode> nodeLists = (NodeListList<INode>)d;
				for(List<INode> list : nodeLists) {
					for(INode n : list) {
						tmp = n.apply(parent,question);
						if (tmp != null) res.getInvolvedJudgements().addAll(tmp.getInvolvedJudgements());
					}
				}
			}

			// or an inode
			if (d instanceof INode) {
				tmp = ((INode) d).apply(parent, question);
				if (tmp != null) res.getInvolvedJudgements().addAll(tmp.getInvolvedJudgements());
			}
		}
		return res;

	}



	@Override
	public SubtreeJudgement caseACasesStatementAction(
			ACasesStatementAction node, SecurityEnvironment question)
					throws AnalysisException {

		SubtreeJudgement result  = new SubtreeJudgement(node);

		LinkedList<ACaseAlternativeAction> cases = node.getCases();
		for(ACaseAlternativeAction alt : cases) {
			SubtreeJudgement altJ = alt.apply(parent,question);
			if (altJ != null) {
				result.getInvolvedJudgements().addAll(result.getInvolvedJudgements());
			}
		}

		return super.caseACasesStatementAction(node, question);
	}


	@Override
	public SubtreeJudgement caseACaseAlternativeAction(
			ACaseAlternativeAction node, SecurityEnvironment question)
					throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		PAction resultAction = node.getResult();
		SubtreeJudgement resultActionJ = resultAction.apply(parent,question);
		if (resultActionJ != null) {
			result.getInvolvedJudgements().addAll(resultActionJ.getInvolvedJudgements());
		}

		return result;
	}

	@Override
	public SubtreeJudgement caseACasesStm(ACasesStm node,
			SecurityEnvironment question) throws AnalysisException {
		return super.caseACasesStm(node, question);
	}

	@Override
	public SubtreeJudgement defaultPDefinition(PDefinition node,
			SecurityEnvironment question) throws AnalysisException {
		return handleDefault(node, question);
	}

	@Override
	public SubtreeJudgement caseALocalDefinition(ALocalDefinition node,
			SecurityEnvironment question) throws AnalysisException {

		SubtreeJudgement result = new SubtreeJudgement(node);

		Judgement localDef = question.getJudgement(node.getName());
		if (localDef != null) {
			result.setJudgement(localDef.getJudgement());
			result.getInvolvedJudgements().add(localDef);
		}

		return result;
	}
}