package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;

import eu.compassresearch.ast.CmlAstFactory;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.utility.Pair;

public class CmlInspectionVisitor extends AbstractInspectionVisitor
{

	private QuestionAnswerCMLAdaptor<Context, Inspection> processVisitor;
	private QuestionAnswerCMLAdaptor<Context, Inspection> actionVisitor;

	public CmlInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess)
	{

		super(ownerProcess, visitorAccess, null);
		this.actionVisitor = new ActionInspectionVisitor(ownerProcess, visitorAccess, this);
		this.processVisitor = new ProcessInspectionVisitor(ownerProcess, visitorAccess, this);
	}

	@Override
	public Inspection defaultPProcess(PProcess node, Context question)
			throws AnalysisException
	{
		return node.apply(this.processVisitor, question);
	}

	@Override
	public Inspection defaultPAction(PAction node, Context question)
			throws AnalysisException
	{
		return node.apply(this.actionVisitor, question);
	}

	@Override
	public Inspection defaultPStm(PStm node, Context question)
			throws AnalysisException
	{
		return node.apply(this.actionVisitor, question);
	}

	@Override
	public Inspection defaultPExp(final PExp node, final Context question)
			throws AnalysisException
	{
		// TODO should this really evolve into skip?
		final ASkipAction skipAction = CmlAstFactory.newASkipAction(node.getLocation());
		return newInspection(createTauTransitionWithoutTime(skipAction, "inv/Pre/Post expression"), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{

				if (!node.apply(cmlExpressionVisitor, question).boolValue(question))
				{
					// FIXME whats with this number shit
					throw new ValueException(4061, question.prepostMsg, question);
				}
				return new Pair<INode, Context>(skipAction, question);
			}
		});
	}

}
