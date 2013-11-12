package eu.compassresearch.core.typechecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.AFieldExp;
import org.overture.ast.expressions.ANewExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.AFieldObjectDesignator;
import org.overture.ast.statements.AFieldStateDesignator;
import org.overture.ast.statements.AIdentifierObjectDesignator;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.ast.statements.AMapSeqStateDesignator;
import org.overture.ast.statements.PObjectDesignator;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.statements.PStm;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.PType;
import org.overture.ast.util.PTypeSet;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeCheckerErrors;
import org.overture.typechecker.TypeComparator;
import org.overture.typechecker.assistant.type.PTypeAssistantTC;
import org.overture.typechecker.visitor.TypeCheckVisitor;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.ast.statements.AAltNonDeterministicStm;
import eu.compassresearch.ast.statements.ADoNonDeterministicStm;
import eu.compassresearch.ast.statements.AIfNonDeterministicStm;
import eu.compassresearch.ast.statements.ANewStm;
import eu.compassresearch.ast.statements.AUnresolvedObjectDesignator;
import eu.compassresearch.ast.statements.AUnresolvedStateDesignator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.environment.VdmRestrictedEnvironment;

public class CmlVdmTypeCheckVisitor extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{
	/**
	 * Inner class representing the VDM type checker.
	 * <p>
	 * If any VDM behavior has to be overridden then this has to happen here. Unless the changed behavior only is called
	 * from a non VDM construct.
	 * </p>
	 */
	TypeCheckVisitor vdmVisitor = new TypeCheckVisitor()
	{

		public PType defaultINode(INode node, TypeCheckInfo question)
				throws AnalysisException
		{
			return node.apply(CmlVdmTypeCheckVisitor.this, question);
		};

		public PType defaultPType(PType node, TypeCheckInfo question)
				throws AnalysisException
		{
			return null;
		};

		/**
		 * The AApplyExp is used to hold operation calls which is ok when used in an assignment or return statement and
		 * assignment/value-definition.
		 */
		public PType caseAApplyExp(AApplyExp node, TypeCheckInfo question)
				throws AnalysisException
		{
			PType type = super.caseAApplyExp(node, question);

			if (PTypeAssistantTC.isOperation(node.getRoot().getType()))
			{
				// FIXME is the correct? not sure if value def should be excluded hereS
				if (!(node.parent() instanceof PStm
						|| node.parent() instanceof AAssignmentDefinition || node.parent() instanceof AValueDefinition))
				{

					// if(!(node.parent() instanceof PExp))
					// we also report an error when it is called from a AWriteCommunicationParameter is that intentional
					// System.out.println(node.parent().getClass().getSimpleName());
					TypeCheckerErrors.report(3425, "Operation '"
							+ node.getRoot()
							+ "' cannot be called in an expression", node.getLocation(), node);
				}
			}

			return type;
		};

		/**
		 * This method create a new env that hides all CSP/CML stuff and then calls the super implementation
		 */
		public PType defaultPExp(PExp node, TypeCheckInfo question)
				throws AnalysisException
		{
			TypeCheckInfo info = question.newInfo(new VdmRestrictedEnvironment(question.assistantFactory, question.env));
			return super.defaultPExp(node, info);
		};

	};

	@Override
	public PType createNewReturnValue(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
			throws AnalysisException
	{
		return null;
	}

	@Override
	public PType defaultINode(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(vdmVisitor, question);
	}

	@Override
	public PType caseAActionStm(AActionStm node, TypeCheckInfo question)
			throws AnalysisException
	{
		PType type = node.getAction().apply(this, question);
		node.setType(type);
		return type;
	}

	@Override
	public PType caseASkipAction(ASkipAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return super.caseASkipAction(node, question);
	}

	@Override
	public PType caseAUnresolvedObjectDesignator(
			AUnresolvedObjectDesignator node, TypeCheckInfo question)
			throws AnalysisException
	{
		PExp exp = node.getPath();

		PObjectDesignator designator = createObjectDesignator(exp);

		node.parent().replaceChild(node, designator);
		return designator.apply(this, question);
	}

	@Override
	public PType caseAUnresolvedStateDesignator(
			AUnresolvedStateDesignator node, TypeCheckInfo question)
			throws AnalysisException
	{
		PExp exp = node.getPath();

		PStateDesignator designator = createStateDesignator(exp);

		node.parent().replaceChild(node, designator);
		return designator.apply(this, question);

	}

	private PObjectDesignator createObjectDesignator(PExp exp)
	{
		if (exp instanceof AVariableExp)
		{
			AVariableExp varExp = (AVariableExp) exp;
			AIdentifierObjectDesignator designator = AstFactory.newAIdentifierObjectDesignator(varExp.getName());
			return designator;
		} else if (exp instanceof AFieldExp)
		{
			AFieldExp field = (AFieldExp) exp;

			PObjectDesignator object = createObjectDesignator(field.getObject());
			AFieldObjectDesignator designator = AstFactory.newAFieldObjectDesignator(object, field.getField());
			return designator;
		}
		// else if(exp instanceof AUnresolvedPathExp &&((AUnresolvedPathExp)exp).getIdentifiers().size()==1)
		// {
		// ILexIdentifierToken id = ((AUnresolvedPathExp)exp).getIdentifiers().iterator().next();
		// ILexNameToken name = new CmlLexNameToken("", id.getName(), exp.getLocation(), false, false);
		// AIdentifierObjectDesignator designator = AstFactory.newAIdentifierObjectDesignator(name);
		// return designator;
		//
		// }

		throw new RuntimeException("hit case for unresolved object designator: "
				+ exp);
	}

	private PStateDesignator createStateDesignator(PExp exp)
	{
		if (exp instanceof AVariableExp)
		{
			AVariableExp varExp = (AVariableExp) exp;
			AIdentifierStateDesignator designator = AstFactory.newAIdentifierStateDesignator(varExp.getName());
			return designator;
		} else if (exp instanceof AFieldExp)
		{
			AFieldExp field = (AFieldExp) exp;

			PStateDesignator object = createStateDesignator(field.getObject());
			AFieldStateDesignator designator = AstFactory.newAFieldStateDesignator(object, field.getField());
			return designator;
		} else if (exp instanceof AApplyExp)
		{
			AApplyExp apply = (AApplyExp) exp;

			if (apply.getArgs().size() != 1)
			{
				throw new RuntimeException("Parse error at: "
						+ exp.getLocation() + " expected 1 argument but found "
						+ apply.getArgs().size());
			}

			PStateDesignator mapseq = createStateDesignator(apply.getRoot());

			AMapSeqStateDesignator designator = AstFactory.newAMapSeqStateDesignator(mapseq, apply.getArgs().get(0));
			return designator;

		}

		throw new RuntimeException("hit case for unresolved state designator: "
				+ exp);
	}

	@Override
	public PType caseAUnresolvedPathExp(AUnresolvedPathExp node,
			TypeCheckInfo question) throws AnalysisException
	{

		throw new RuntimeException("Unresolved path not resolved before TC");
	}

	/**
	 * Re-writes a ANewStm into an assignment that assigns a new expression</br> Not sure if this is ok? If not the
	 * assign node should not replace the other but be a clone and copy back the type.
	 */
	@Override
	public PType caseANewStm(ANewStm node, TypeCheckInfo question)
			throws AnalysisException
	{
		ANewExp newExp = AstFactory.newANewExp(node.getLocation(), node.getClassName(), node.getArgs());

		AAssignmentStm assign = AstFactory.newAAssignmentStm(node.getLocation(), node.getDestination(), newExp);
		node.parent().replaceChild(node, assign);
		return assign.apply(this, question);
	}

	@Override
	public PType caseABracketedExp(ABracketedExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		PType type = node.getExpression().apply(this, question);
		node.setType(type);
		return type;
	}

	@Override
	public PType caseAIfNonDeterministicStm(AIfNonDeterministicStm node,
			TypeCheckInfo question) throws AnalysisException
	{
		PType type = typeCheckAlternatives(node.getAlternatives(), question, node.getLocation());

		node.setType(type);
		return node.getType();
	}

	private PType typeCheckAlternatives(
			LinkedList<AAltNonDeterministicStm> alternatives,
			TypeCheckInfo question, ILexLocation location)
			throws AnalysisException
	{
		PTypeSet rtypes = new PTypeSet();
		for (AAltNonDeterministicStm alt : alternatives)
		{
			rtypes.add(alt.apply(THIS, question));
		}

		return rtypes.getType(location);
	}

	public void typeCheckAlternatives(AIfNonDeterministicStm node,
			TypeCheckInfo question, PTypeSet rtypes) throws AnalysisException
	{

	}

	@Override
	public PType caseAAltNonDeterministicStm(AAltNonDeterministicStm node,
			TypeCheckInfo question) throws AnalysisException
	{

		PExp guard = node.getGuard();
		PType guardType = guard.apply(THIS, question);

		if (!(TypeComparator.isSubType(guardType, new ABooleanBasicType())))
		{
			TypeCheckerErrors.report(0, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage("Boolean", "a guard of type "
					+ guardType), guard.getLocation(), guard);
			return node.getType();
		}

		PStm action = node.getAction();
		PType actionType = action.apply(THIS, question);

		node.setType(actionType);
		return node.getType();
	}

	@Override
	public PType caseADoNonDeterministicStm(ADoNonDeterministicStm node,
			TypeCheckInfo question) throws AnalysisException
	{
		PType type = typeCheckAlternatives(node.getAlternatives(), question, node.getLocation());

		node.setType(type);
		return node.getType();
	}
}
