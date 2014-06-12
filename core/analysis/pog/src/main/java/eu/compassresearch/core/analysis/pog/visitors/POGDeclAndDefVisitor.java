package eu.compassresearch.core.analysis.pog.visitors;

//POG-related imports
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.pog.contexts.POImpliesContext;
import org.overture.pog.contexts.PONameContext;
import org.overture.pog.obligation.TypeCompatibility;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.visitors.PogParamDefinitionVisitor;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.pog.obligations.CmlOperationDefinitionContext;
import eu.compassresearch.core.analysis.pog.obligations.CmlParameterPatternObligation;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CmlSatisfiabilityObligation;
import eu.compassresearch.core.analysis.pog.utility.ClonerProcessState;
import eu.compassresearch.core.analysis.pog.utility.MakerNameContexts;

public class POGDeclAndDefVisitor extends
		QuestionAnswerCMLAdaptor<IPOContextStack, CmlProofObligationList>
{

	// Errors and other things are recorded on this guy
	final private QuestionAnswerAdaptor<IPOContextStack, CmlProofObligationList> parentPOG;
	final private PogParamDefinitionVisitor<IPOContextStack, CmlProofObligationList> overtureVisitor;
	final CmlPogAssistantFactory assistantFactory;

	public POGDeclAndDefVisitor(
			QuestionAnswerAdaptor<IPOContextStack, CmlProofObligationList> parent,
			CmlPogAssistantFactory assistantFactory)
	{
		this.parentPOG = parent;
		this.overtureVisitor = new PogParamDefinitionVisitor<IPOContextStack, CmlProofObligationList>(this, this, assistantFactory);
		this.assistantFactory = assistantFactory;

	}

	// caseAins

	/**
	 * CML channel definition CURRENTLY JUST PRINT TO SCREEN
	 */
	@Override
	public CmlProofObligationList caseAChannelDefinition(
			AChannelDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList pol = new CmlProofObligationList();

		// NO POs here yet.

		return pol;
	}

	/**
	 * CML chanset definition CURRENTLY JUST PRINT TO SCREEN
	 */
	@Override
	public CmlProofObligationList caseAChansetDefinition(
			AChansetDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList pol = new CmlProofObligationList();

		// NO POs here yet

		return pol;
	}

	/**
	 * CML ELEMENT - Classes
	 */
	@Override
	public CmlProofObligationList caseAClassClassDefinition(
			AClassClassDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList pol = new CmlProofObligationList();

		for (PDefinition def : node.getDefinitions())
		{
			PONameContext name = def.apply(new MakerNameContexts());
			if (name != null)
			{
				question.push(def.apply(new MakerNameContexts()));
				pol.addAll(def.apply(parentPOG, question));
				question.pop();
			} else
			{
				pol.addAll(def.apply(parentPOG, question));
			}

		}

		return pol;
	}

	@Override
	public CmlProofObligationList caseAProcessDefinition(
			AProcessDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();

		PProcess process = node.getProcess();
		PONameContext name = process.apply(new MakerNameContexts());

		if (name != null)
		{
			question.push(process.apply(new MakerNameContexts()));
			pol.addAll(process.apply(parentPOG, question));
			question.pop();
		} else
		{
			pol.addAll(process.apply(parentPOG, question));
		}

		return pol;
	}

	@Override
	public CmlProofObligationList caseAStateDefinition(AStateDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();

		for (PDefinition def : node.getStateDefs())
		{
			pol.addAll(def.apply(parentPOG, question));
		}

		return pol;
	}

	@Override
	public CmlProofObligationList caseAActionDefinition(AActionDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();

		// TODO re-enable pog action visits. for now, not doing it.

		// PAction action = node.getAction();
		// pol.addAll(action.apply(parentPOG, question));

		return pol;
	}

	// These will involve structural changes to the Overture AST in the
	// future but for now we hack past it.
	@Override
	public CmlProofObligationList caseAUnresolvedPathExp(
			AUnresolvedPathExp node, IPOContextStack question)
			throws AnalysisException
	{
		return new CmlProofObligationList();
	}

	// default case. stuff with no pos
	@Override
	public CmlProofObligationList defaultPSingleDeclaration(
			PSingleDeclaration node, IPOContextStack question)
			throws AnalysisException
	{
		return new CmlProofObligationList();
	}

	// Call Overture for the other defs
	@Override
	public CmlProofObligationList defaultPDefinition(PDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();
		pol.addAll(node.apply(overtureVisitor, question));
		return pol;
	}

	// Call the main pog when it's not a defintion/declaration
	@Override
	public CmlProofObligationList defaultINode(INode node,
			IPOContextStack question) throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();
		pol.addAll(node.apply(parentPOG, question));
		return pol;
	}

	/**
	 * Implicit operations - CML does not reuse Overture operations
	 */
	@Override
	public CmlProofObligationList caseAImplicitOperationDefinition(
			AImplicitOperationDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();

		// Taken from Overture - Needed?
		LexNameList pids = new LexNameList();

		for (APatternListTypePair tp : node.getParameterPatterns())
			for (PPattern p : tp.getPatterns())
				for (PDefinition def : p.getDefinitions())
					pids.add(def.getName());

		// Taken from Overture - Needed?
		if (pids.hasDuplicates())
		{
			pol.add(new CmlParameterPatternObligation(assistantFactory, node, question));
		}

		// if implicit operation has a precondition, dispatch for PO checking
		if (node.getPrecondition() != null)
		{
			pol.addAll(node.getPrecondition().apply(parentPOG, question));
		}

		if (node.getPostcondition() != null)
		{
			if (node.getPrecondition() != null)
			{
				question.push(new POImpliesContext(node.getPrecondition()));

				pol.addAll(node.getPostcondition().apply(parentPOG, question));
				question.pop();
			} else
			{
				pol.addAll(node.getPostcondition().apply(parentPOG, question));
			}
			PDefinition stateDef;

			AActionProcess stater = node.getAncestor(AActionProcess.class);
			if (stater != null)
			{
				List<AInstanceVariableDefinition> stateDefs = stater.apply(new ClonerProcessState());
				question.push(new CmlOperationDefinitionContext(node, false, stateDefs));
				pol.add(new CmlSatisfiabilityObligation(node, stateDefs, question, assistantFactory));
				question.pop();
			} else
			{
				if (node.getClassDefinition() != null)
				{
					stateDef = node.getClassDefinition().clone();
				} else
				{
					stateDef = node.getStateDefinition();
				}
				question.push(new CmlOperationDefinitionContext(node, false, stateDef));
				pol.add(new CmlSatisfiabilityObligation(node, stateDef, question, assistantFactory));
				question.pop();
			}
		}

		return pol;
	}

	// >----------------------------------------------------------
	// >------------OVERTURE Overrides --------------------------
	// >--------------------------------------------------------

	@Override
	public CmlProofObligationList caseAAssignmentDefinition(
			AAssignmentDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		CmlProofObligationList obligations = new CmlProofObligationList();

		if (node.getExpression() == null)
			return obligations;
		PExp expression = node.getExpression();
		PType type = node.getType();
		PType expType = node.getExpType();

		obligations.addAll(expression.apply(parentPOG, question));

		if (!TypeComparator.isSubType(question.checkType(expression, expType), type, assistantFactory))
		{
			TypeCompatibility sto = TypeCompatibility.newInstance(expression, type, expType, question, assistantFactory);
			if (sto != null)
			{
				obligations.add(sto);
			}
		}

		return obligations;
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
