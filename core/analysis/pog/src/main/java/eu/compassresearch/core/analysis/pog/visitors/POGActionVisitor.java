package eu.compassresearch.core.analysis.pog.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AExternalClause;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.utility.POException;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AParametrisedAction;
import eu.compassresearch.ast.actions.AParametrisedInstantiatedAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AResParametrisation;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.analysis.pog.obligations.CmlNonZeroTimeObligation;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;

@SuppressWarnings("serial")
public class POGActionVisitor extends
		QuestionAnswerCMLAdaptor<IPOContextStack, CmlProofObligationList>
{

	private ProofObligationGenerator parentPOG;

	// FIXME dispatch chan and varsets to the apropriate visitors

	/**
	 * Constructor - simply initialise parent POG
	 * 
	 * @param parent
	 */
	public POGActionVisitor(ProofObligationGenerator parent)
	{
		this.parentPOG = parent;
	}

	// Default action
	@Override
	public CmlProofObligationList defaultPAction(PAction node,
			IPOContextStack question) throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();

		return pol;
	}

	// Call the main pog when it's not a statement
	@Override
	public CmlProofObligationList defaultINode(INode node,
			IPOContextStack question) throws AnalysisException
	{

		CmlProofObligationList pol = new CmlProofObligationList();
		pol.addAll(node.apply(parentPOG, question));
		return pol;
	}

	
	
	
	@Override
	public CmlProofObligationList caseAStmAction(AStmAction node,
			IPOContextStack question) throws AnalysisException
	{
		//FIXME What do we do with statements in actions?
	//	return node.getStatement().apply(parentPOG,question);
		// TODO Auto-generated method stub
		return super.caseAStmAction(node, question);
	}



	/**
	 * Composition action. Process left part, then right.
	 */
	@Override
	public CmlProofObligationList caseASequentialCompositionAction(
			ASequentialCompositionAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction left = node.getLeft();
			PAction right = node.getRight();

			pol.addAll(left.apply(parentPOG, question));
			pol.addAll(right.apply(parentPOG, question));

			// TODO: Any ASequentialCompositionAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}




	@Override
	public CmlProofObligationList caseATimedInterruptAction(
			ATimedInterruptAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction left = node.getLeft();
			PAction right = node.getRight();
			PExp timeExp = node.getTimeExpression();

			// Send left-hand side
			pol.addAll(left.apply(this, question));
			// check for Non-Zero time obligation and dispatch exp for POG checking
			// Changing format of CML POs... pol.add(new
			// CMLNonZeroTimeObligation(timeExp, question));
			pol.add(new CmlNonZeroTimeObligation(timeExp, question));
			pol.addAll(timeExp.apply(this, question));
			// Send right-hand side
			pol.addAll(right.apply(this, question));

			// TODO: Any ATimedInterruptAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAValParametrisation(
			AValParametrisation node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			ALocalDefinition decl = node.getDeclaration();

			// TODO: Any AValParametrisation POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAResParametrisation(
			AResParametrisation node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			ALocalDefinition decl = node.getDeclaration();

			// TODO: Any AResParametrisation POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAUntimedTimeoutAction(
			AUntimedTimeoutAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction left = node.getLeft();
			PAction right = node.getRight();

			pol.addAll(left.apply(parentPOG, question));
			pol.addAll(right.apply(parentPOG, question));

			// TODO: Any AUntimedTimeoutAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseATimeoutAction(ATimeoutAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();
			
			// Get subparts
			PAction left = node.getLeft();
			PAction right = node.getRight();
			PExp timedExp = node.getTimeoutExpression();

			pol.addAll(left.apply(parentPOG, question));
			pol.addAll(timedExp.apply(parentPOG, question));
			// check for Non-Zero time obligation and dispatch exp for POG checking
			pol.add(new CmlNonZeroTimeObligation(timedExp, question));
			pol.addAll(right.apply(parentPOG, question));

			// TODO: Any ATimeoutAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAExternalClause(AExternalClause node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			LinkedList<ILexNameToken> ids = node.getIdentifiers();
			for (ILexNameToken id : ids)
			{
				// TODO anything to do w/ ids?
			}
			// TODO: Any AExternalClause POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}


	@Override
	public CmlProofObligationList caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction repAction = node.getReplicatedAction();
			LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();
			for (PSingleDeclaration d : repDecl)
			{
				// TODO anything to do w/ decls?
			}
			// TODO: Any AInternalChoiceReplicatedAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PVarsetExpression csexp = node.getChansetExpression();
			PVarsetExpression sexp = node.getNamesetExpression();
			PAction repAction = node.getReplicatedAction();
			LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();

			// TODO: Any AGeneralisedParallelismReplicatedAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction action = node.getReplicatedAction();
			LinkedList<PSingleDeclaration> decl = node.getReplicationDeclaration();
			for (PSingleDeclaration d : decl)
			{
			}

			pol.addAll(action.apply(parentPOG, question));

			// TODO: Any AExternalChoiceReplicatedAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}




	
	
	@Override
	public CmlProofObligationList caseAInterruptAction(AInterruptAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction left = node.getLeft();
			PAction right = node.getRight();

			pol.addAll(left.apply(parentPOG, question));
			pol.addAll(right.apply(parentPOG, question));

			// TODO: Any AInterruptAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAInterleavingParallelAction(
			AInterleavingParallelAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction leftAction = node.getLeftAction();
			PAction rightAction = node.getRightAction();
			PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
			PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

			pol.addAll(leftAction.apply(parentPOG, question));
			pol.addAll(rightAction.apply(parentPOG, question));

			// GUard against empty nameset expressions
			if (leftNamesetExp != null)
			{
				pol.addAll(leftNamesetExp.apply(parentPOG, question));
			}
			if (leftNamesetExp != null)
			{
				pol.addAll(rightnamesetExp.apply(parentPOG, question));
			}
			// TODO: Consider AInterleavingParallelAction POs

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}


	@Override
	public CmlProofObligationList caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction leftAction = node.getLeftAction();
			PAction rightAction = node.getRightAction();
			PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
			PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

			pol.addAll(leftAction.apply(parentPOG, question));
			pol.addAll(rightAction.apply(parentPOG, question));
			if (leftNamesetExp != null)
				pol.addAll(leftNamesetExp.apply(parentPOG, question));
			if (rightnamesetExp != null)
				pol.addAll(rightnamesetExp.apply(parentPOG, question));

			// TODO: Consider AGeneralisedParallelismParallelAction POs

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	


	@Override
	public CmlProofObligationList caseAChannelRenamingAction(
			AChannelRenamingAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction act = node.getAction();
			PExp exp = node.getRenameExpression();

			// TODO: Any AChannelRenamingAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAWaitAction(AWaitAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PExp timedExp = node.getExpression();

			pol.addAll(timedExp.apply(parentPOG, question));

			// TODO: Any AWaitAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	
	
	

	
	@Override
	public CmlProofObligationList caseAMuAction(AMuAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			LinkedList<ILexIdentifierToken> ids = node.getIdentifiers();
			LinkedList<PAction> acts = node.getActions();

			// TODO: Any AMuAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmlProofObligationList caseAChaosAction(AChaosAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// TODO: Any AChaosAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmlProofObligationList caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction replicatedAction = node.getReplicatedAction();
			LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

			// TODO: Any ASequentialCompositionReplicatedAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction leftAction = node.getLeftAction();
			PVarsetExpression leftChanSet = node.getLeftChansetExpression();
			PVarsetExpression leftNameSet = node.getLeftNamesetExpression();
			PAction rightAction = node.getRightAction();
			PVarsetExpression rightChanSet = node.getRightChansetExpression();
			PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

			pol.addAll(leftAction.apply(parentPOG, question));
			pol.addAll(leftChanSet.apply(parentPOG, question));
			pol.addAll(leftNameSet.apply(parentPOG, question));
			pol.addAll(rightAction.apply(parentPOG, question));
			pol.addAll(rightChanSet.apply(parentPOG, question));
			pol.addAll(rightNameSet.apply(parentPOG, question));

			// TODO: Any AAlphabetisedParallelismParallelAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}



	@Override
	public CmlProofObligationList caseAGuardedAction(AGuardedAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			// Get subparts
			PExp exp = node.getExpression();
			PAction action = node.getAction();

			CmlProofObligationList pol = new CmlProofObligationList();

			pol.addAll(exp.apply(parentPOG, question));
			pol.addAll(action.apply(parentPOG, question));

			// TODO: Any AGuardedAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseADivAction(ADivAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// TODO: Any ADivAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}


	@Override
	public CmlProofObligationList caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction acts = node.getReplicatedAction();
			LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();
			PVarsetExpression namesetExp = node.getNamesetExpression();

			// TODO: Any ACommonInterleavingReplicatedAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PVarsetExpression namesetExp = node.getNamesetExpression();
			PAction repAction = node.getReplicatedAction();
			LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

			// TODO: Any AInterleavingReplicatedAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseASynchronousParallelismReplicatedAction(
			ASynchronousParallelismReplicatedAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PVarsetExpression namesetExp = node.getNamesetExpression();
			PAction repAction = node.getReplicatedAction();
			LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

			// TODO Any ASynchronousParallelismReplicatedAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	

	@Override
	public CmlProofObligationList caseAInternalChoiceAction(
			AInternalChoiceAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction left = node.getLeft();
			PAction right = node.getRight();

			pol.addAll(left.apply(parentPOG, question));
			pol.addAll(right.apply(parentPOG, question));

			// TODO Any AInternalChoiceAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAReferenceAction(AReferenceAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			LinkedList<PExp> args = node.getArgs();
			ILexNameToken name = node.getName();

			// TODO Any AReferenceAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseACommunicationAction(
			ACommunicationAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction act = node.getAction();
			LinkedList<PCommunicationParameter> commparam = node.getCommunicationParameters();
			ILexIdentifierToken ident = node.getIdentifier();

			// TODO Any ACommunicationAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmlProofObligationList caseASkipAction(ASkipAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// TODO Any ASkipAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmlProofObligationList caseAExternalChoiceAction(
			AExternalChoiceAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction left = node.getLeft();
			PAction right = node.getRight();

			pol.addAll(left.apply(parentPOG, question));
			pol.addAll(right.apply(parentPOG, question));

			// TODO Any AInternalChoiceAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAHidingAction(AHidingAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction action = node.getLeft();
			PVarsetExpression chanSet = node.getChansetExpression();

			pol.addAll(action.apply(parentPOG, question));
			pol.addAll(chanSet.apply(parentPOG, question));

			// TODO Any AHidingAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAVresParametrisation(
			AVresParametrisation node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			ALocalDefinition decl = node.getDeclaration();
			pol.addAll(decl.apply(parentPOG, question));

			// TODO Any AVresParametrisation POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			AParametrisedAction action = node.getAction();
			LinkedList<PExp> args = node.getArgs();
			LinkedList<PParametrisation> parameterNames = node.getAction().getParametrisations();

			pol.addAll(action.apply(parentPOG, question));

			// TODO Any AParametrisedInstantiatedAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmlProofObligationList caseAStartDeadlineAction(
			AStartDeadlineAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction event = node.getLeft();
			PExp timeExp = node.getExpression();

			pol.addAll(event.apply(parentPOG, question));
			pol.addAll(timeExp.apply(parentPOG, question));

			// TODO Any AStartDeadlineAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmlProofObligationList caseAEndDeadlineAction(
			AEndDeadlineAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction event = node.getLeft();
			PExp timeExp = node.getExpression();

			pol.addAll(event.apply(parentPOG, question));
			pol.addAll(timeExp.apply(parentPOG, question));

			// TODO: Any AEndDeadlineAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList caseAStopAction(AStopAction node,
			IPOContextStack question) throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// TODO: any AStopAction POs?
			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}



	@Override
	public CmlProofObligationList caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction leftAction = node.getLeftAction();
			PVarsetExpression leftNameSet = node.getLeftNamesetExpression();
			PAction rightAction = node.getRightAction();
			PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

			pol.addAll(leftAction.apply(parentPOG, question));
			pol.addAll(leftNameSet.apply(parentPOG, question));
			pol.addAll(rightAction.apply(parentPOG, question));
			pol.addAll(rightNameSet.apply(parentPOG, question));

			// TODO: Any ASynchronousParallelismParallelAction POs?

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}



	@Override
	public CmlProofObligationList caseAParametrisedAction(
			AParametrisedAction node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList pol = new CmlProofObligationList();

			// Get subparts
			PAction action = node.getAction();
			LinkedList<PParametrisation> params = node.getParametrisations();

			// TODO: Consider any AParametrisedAction POs
			for (PParametrisation p : params)
			{
			}

			pol.addAll(action.apply(parentPOG, question));

			return pol;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public CmlProofObligationList createNewReturnValue(INode node,
			IPOContextStack question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmlProofObligationList createNewReturnValue(Object node,
			IPOContextStack question)
	{
		// TODO Auto-generated method stub
		return null;
	}

}