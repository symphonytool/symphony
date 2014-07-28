package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.PObjectDesignator;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.statements.PStm;
import org.overture.interpreter.eval.DelegateExpressionEvaluator;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.runtime.VdmRuntimeError;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.Quantifier;
import org.overture.interpreter.values.QuantifierList;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.ValueSet;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AEnumerationRenameChannelExp;
import eu.compassresearch.ast.expressions.AFatCompVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.AInterVOpVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.ASubVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnionVOpVarsetExpression;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.patterns.ARenamePair;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;
import eu.compassresearch.core.interpreter.api.values.CmlChannel;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.api.values.NameValue;
import eu.compassresearch.core.interpreter.api.values.RenamingValue;

public class CmlExpressionVisitor extends
		QuestionAnswerCMLAdaptor<Context, Value>
{
	static
	{
		VdmRuntime.initialize(new CmlExpressionVisitor());
	}

	class VdmExpressionEvaluator extends DelegateExpressionEvaluator
	{

		@Override
		public Value defaultPExp(PExp node, Context question)
				throws AnalysisException
		{

			if (node instanceof PCMLExp)
			{
				return defaultPCMLExp((PCMLExp) node, question);
			} else
			{
				return node.apply(this, question);
			}
		}
	}

	private VdmExpressionEvaluator vdmExpEvaluator = new VdmExpressionEvaluator();

	private CmlStatementEvaluator stmEvaluator = new CmlStatementEvaluator();

	public CmlExpressionVisitor()
	{
	}

	@Override
	public Value defaultPStm(PStm node, Context question)
			throws AnalysisException
	{
		return node.apply(stmEvaluator, question);
	}

	@Override
	public Value defaultPExp(PExp node, Context question)
			throws AnalysisException
	{
		return vdmExpEvaluator.defaultPExp(node, question);
	}

	@Override
	public Value defaultPCMLExp(PCMLExp node, Context question)
			throws AnalysisException
	{
		throw new CmlInterpreterException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	@Override
	public Value defaultPVarsetExpression(PVarsetExpression node,
			Context question) throws AnalysisException
	{
		throw new CmlInterpreterException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	@Override
	public Value caseAApplyExp(AApplyExp node, Context ctxt)
			throws AnalysisException
	{
		try
		{
			Value object = node.getRoot().apply(VdmRuntime.getExpressionEvaluator(), ctxt).deref();
			if (object instanceof OperationValue)
			{
				return StatementInspectionVisitor.invokeOperation(node.getLocation(),node, node.getArgs(), ctxt, (OperationValue)object, this);
			} else
			{
				return super.caseAApplyExp(node, ctxt);
			}
		} catch (ValueException e)
		{
			return VdmRuntimeError.abort(node.getLocation(), e);
		}

	}

	protected ChannelValue createChannelNameValue(ILexIdentifierToken id,
			Context question) throws AnalysisException
	{
		// find the channel value
		// TODO this might change if channel renaming does not
		// require the renamed channel name to be defined
		ILexNameToken channelName = NamespaceUtility.createChannelName(id);
		CmlChannel chanValue = (CmlChannel) question.lookup(channelName);

		return new ChannelValue(chanValue);
	}

	protected ChannelValue createChannelNameValue(ANameChannelExp chanNameExp,
			Context question) throws AnalysisException
	{
		// find the channel value
		ILexNameToken channelName = NamespaceUtility.createChannelName(chanNameExp.getIdentifier());
		CmlChannel chanValue = (CmlChannel) question.lookup(channelName);

		// extract the values
		List<Value> values = new LinkedList<Value>();
		for (int i = 0; i < chanValue.getValueTypes().size(); i++)
		{
			// if the index is less than the number of expressions its defined
			// else we put an anyValue
			if (i < chanNameExp.getExpressions().size())
			{
				values.add(chanNameExp.getExpressions().get(i).apply(this, question));
			} else
			{
				values.add(new LatticeTopValue(chanValue.getValueTypes().get(i)));
			}
		}

		return new ChannelValue(chanValue, values);
	}

	private boolean isChannelSetExp(ANameChannelExp varexp, Context question)
	{
		// find the channel value
		ILexNameToken channelName = NamespaceUtility.createChannelName(varexp.getIdentifier());
		return question.check(channelName) instanceof CmlChannel;
	}

	@Override
	public Value caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			Context question) throws AnalysisException
	{
		/*
		 * Before we do anything we need to find out if this is a channelexp or nameexp. In most cases it does not make
		 * sense to make an empty channelset, so if its empty we assume that this is a nameset
		 */
		ValueSet coms = new ValueSet();
		if (node.getChannelNames().size() > 0
				&& isChannelSetExp(node.getChannelNames().get(0), question))
		{
			for (ANameChannelExp chanNameExp : node.getChannelNames())
			{
				ChannelValue channelName = createChannelNameValue(chanNameExp, question);
				coms.add(channelName);
			}
		}
		// then it must be a nameset expression
		else
		{
			for (ANameChannelExp chanNameExp : node.getChannelNames())
			{
				// FIXME At the moment we only support simple names without any expressions after it!
				ILexNameToken name = NamespaceUtility.createSimpleName(chanNameExp.getIdentifier());
				coms.add(new NameValue(name));
			}
		}
		return new SetValue(coms);
	}

	@Override
	public Value defaultPStateDesignator(PStateDesignator node, Context question)
			throws AnalysisException
	{
		return node.apply(stmEvaluator, question);
	}

	@Override
	public Value defaultPObjectDesignator(PObjectDesignator node,
			Context question) throws AnalysisException
	{
		return node.apply(stmEvaluator, question);
	}

	@Override
	public Value caseAEnumVarsetExpression(AEnumVarsetExpression node,
			Context question) throws AnalysisException
	{
		/*
		 * Before we do anything we need to find out if this is a channelexp or nameexp. In most cases it does not make
		 * sense to make an empty channelset, so if its empty we assume that this is a nameset
		 */
		ValueSet coms = new ValueSet();
		if (node.getChannelNames().size() > 0
				&& isChannelSetExp(node.getChannelNames().get(0), question))
		{
			for (ANameChannelExp chanNameExp : node.getChannelNames())
			{
				ChannelValue channelName = createChannelNameValue(chanNameExp, question);
				coms.add(channelName);
			}

		} else
		{
			// FIXME At the moment we only support simple names without any expressions after it!
			for (ANameChannelExp chanNameExp : node.getChannelNames())
			{
				coms.add(new NameValue(NamespaceUtility.createSimpleName(chanNameExp.getIdentifier())));
			}

		}
		return new SetValue(coms);
	}

	@Override
	public Value caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression node,
			Context ctxt) throws AnalysisException
	{
		try
		{
			ValueSet result = new ValueSet();
			result.addAll(node.getLeft().apply(VdmRuntime.getExpressionEvaluator(), ctxt).setValue(ctxt));
			result.addAll(node.getRight().apply(VdmRuntime.getExpressionEvaluator(), ctxt).setValue(ctxt));
			return new SetValue(result);
		} catch (ValueException e)
		{
			return VdmRuntimeError.abort(node.getLocation(), e);
		}
	}

	@Override
	public Value caseAInterVOpVarsetExpression(AInterVOpVarsetExpression node,
			Context ctxt) throws AnalysisException
	{
		try
		{
			ValueSet result = new ValueSet();
			result.addAll(node.getLeft().apply(VdmRuntime.getExpressionEvaluator(), ctxt).setValue(ctxt));
			result.retainAll(node.getRight().apply(VdmRuntime.getExpressionEvaluator(), ctxt).setValue(ctxt));

			return new SetValue(result);
		} catch (ValueException e)
		{
			e.printStackTrace();
			return VdmRuntimeError.abort(node.getLocation(), e);
		}
	}

	@Override
	public Value caseASubVOpVarsetExpression(ASubVOpVarsetExpression node,
			Context ctxt) throws AnalysisException
	{
		ValueSet result = new ValueSet();
		ValueSet togo = null;

		try
		{
			togo = node.getRight().apply(VdmRuntime.getExpressionEvaluator(), ctxt).setValue(ctxt);
			result.addAll(node.getLeft().apply(VdmRuntime.getExpressionEvaluator(), ctxt).setValue(ctxt));
		} catch (ValueException e)
		{
			return VdmRuntimeError.abort(node.getLocation(), e);
		}

		for (Value r : togo)
		{
			result.remove(r);
		}

		return new SetValue(result);
	}

	@Override
	public Value caseAFatCompVarsetExpression(AFatCompVarsetExpression node,
			Context ctxt) throws AnalysisException
	{

		SetValue set = new SetValue();

		boolean isChannel = isChannelSetExp(node.getChannelNameExp(), ctxt);

		try
		{
			QuantifierList quantifiers = new QuantifierList();

			for (PMultipleBind mb : node.getBindings())
			{
				ValueList bvals = ctxt.assistantFactory.createPMultipleBindAssistant().getBindValues(mb, ctxt);

				for (PPattern p : mb.getPlist())
				{
					Quantifier q = new Quantifier(p, bvals);
					quantifiers.add(q);
				}
			}

			quantifiers.init(ctxt, false);

			while (quantifiers.hasNext())
			{
				Context evalContext = new Context(ctxt.assistantFactory, node.getLocation(), "set comprehension", ctxt);
				NameValuePairList nvpl = quantifiers.next();
				boolean matches = true;

				for (NameValuePair nvp : nvpl)
				{
					Value v = evalContext.get(nvp.name);

					if (v == null)
					{
						evalContext.put(nvp.name, nvp.value);
					} else
					{
						if (!v.equals(nvp.value))
						{
							matches = false;
							break; // This quantifier set does not match
						}
					}
				}

				if (matches
						&& (node.getPredicate() == null || node.getPredicate().apply(VdmRuntime.getExpressionEvaluator(), evalContext).boolValue(ctxt)))
				{
					if (isChannel)
					{
						set.values.add(createChannelNameValue(node.getChannelNameExp(), evalContext));
					} else
					{
						set.values.add(new NameValue(NamespaceUtility.createSimpleName(node.getChannelNameExp().getIdentifier())));
					}
				}
			}
		} catch (ValueException e)
		{
			return VdmRuntimeError.abort(node.getLocation(), e);
		}

		return set;
	}

	@Override
	public Value caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, Context question)
			throws AnalysisException
	{

		ILexNameToken name = NamespaceUtility.createChansetName(node.getIdentifier());
		return question.lookup(name);
	}

	@Override
	public Value caseABracketedExp(ABracketedExp node, Context question)
			throws AnalysisException
	{

		return node.getExpression().apply(this, question);
	}

	@Override
	public Value caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node, Context question)
			throws AnalysisException
	{
		RenamingValue rnv = new RenamingValue();

		for (ARenamePair pair : node.getRenamePairs())
		{
			ChannelValue src = createChannelNameValue(pair.getFrom(), question);
			ChannelValue dst = createChannelNameValue(pair.getTo(), question);
			rnv.renamingMap().put(src, dst);
		}

		return rnv;
	}

	@Override
	public Value createNewReturnValue(INode node, Context question)
	{
		return null;
	}

	@Override
	public Value createNewReturnValue(Object node, Context question)
	{
		return null;
	}

}
