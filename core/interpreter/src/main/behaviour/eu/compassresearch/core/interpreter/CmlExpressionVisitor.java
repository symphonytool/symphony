package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.ast.util.definitions.ClassList;
import org.overture.interpreter.assistant.pattern.PMultipleBindAssistantInterpreter;
import org.overture.interpreter.eval.DelegateExpressionEvaluator;
import org.overture.interpreter.runtime.ClassInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.runtime.VdmRuntimeError;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.Quantifier;
import org.overture.interpreter.values.QuantifierList;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatCompVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.AUnionVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.api.values.NamesetValue;

@SuppressWarnings("serial")
public class CmlExpressionVisitor extends
		QuestionAnswerCMLAdaptor<Context, Value>
{
	static
	{
		VdmRuntime.initialize(new CmlExpressionVisitor());
		try
		{
			new ClassInterpreter(new ClassList());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class VdmExpressionEvaluator extends DelegateExpressionEvaluator
	{

		@Override
		public Value defaultPExp(PExp node, Context question)
				throws AnalysisException
		{

			if (node instanceof PCMLExp)
				return defaultPCMLExp((PCMLExp) node, question);
			else
				return node.apply(this, question);
		}
	}

	private VdmExpressionEvaluator vdmExpEvaluator = new VdmExpressionEvaluator();

	public CmlExpressionVisitor()
	{
		// To be able to work together with the VDM interpreter we need to set this
		// to the current thread.
		InitThread initThread = new InitThread(Thread.currentThread());
		BasicSchedulableThread.setInitialThread(initThread);
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

	protected ChannelNameValue createChannelNameValue(ILexIdentifierToken id,
			Context question) throws AnalysisException
	{
		// find the channel value
		// TODO this might change if channel renaming does not
		// require the renamed channel name to be defined
		ILexNameToken channelName = NamespaceUtility.createChannelName(id);
		CMLChannelValue chanValue = (CMLChannelValue) question.lookup(channelName);

		return new ChannelNameValue(chanValue);
	}

	protected ChannelNameValue createChannelNameValue(
			ANameChannelExp chanNameExp, Context question)
			throws AnalysisException
	{
		// find the channel value
		ILexNameToken channelName = NamespaceUtility.createChannelName(chanNameExp.getIdentifier());
		CMLChannelValue chanValue = (CMLChannelValue) question.lookup(channelName);

		// extract the values
		List<Value> values = new LinkedList<Value>();
		for (int i = 0; i < chanValue.getValueTypes().size(); i++)
		{
			// if the index is less than the number of expressions its defined
			// else we put an anyValue
			if (i < chanNameExp.getExpressions().size())
				values.add(chanNameExp.getExpressions().get(i).apply(this, question));
			else
				values.add(new LatticeTopValue(chanValue.getValueTypes().get(i)));
		}

		return new ChannelNameValue(chanValue, values);
	}

	private boolean isChannelSetExp(ANameChannelExp varexp, Context question)
	{
		// find the channel value
		ILexNameToken channelName = NamespaceUtility.createChannelName(varexp.getIdentifier());
		return (CMLChannelValue) question.lookup(channelName) != null;
	}

	@Override
	public Value caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			Context question) throws AnalysisException
	{
		/*
		 * Before we do anything we need to find out if this is a channelexp or nameexp. In most cases it does not make
		 * sense to make an empty channelset, so if its empty we assume that this is a nameset
		 */
		if (node.getChannelNames().size() > 0
				&& isChannelSetExp(node.getChannelNames().get(0), question))
		{

			Set<ChannelNameValue> coms = new HashSet<ChannelNameValue>();

			for (ANameChannelExp chanNameExp : node.getChannelNames())
			{
				ChannelNameValue channelName = createChannelNameValue(chanNameExp, question);
				coms.add(channelName);
			}

			return new ChannelNameSetValue(coms);
		}
		// then it must be a nameset expression
		else
		{
			Set<ILexNameToken> coms = new HashSet<ILexNameToken>();

			for (ANameChannelExp chanNameExp : node.getChannelNames())
			{
				// FIXME At the moment we only support simple names without any expressions after it!
				ILexNameToken name = NamespaceUtility.createSimpleName(chanNameExp.getIdentifier());
				coms.add(name);
			}

			return new NamesetValue(coms);

		}
	}

	@Override
	public Value caseAIdentifierStateDesignator(
			AIdentifierStateDesignator node, Context question)
			throws AnalysisException
	{
		// We lookup the name in a context comprising only state...
		// return ctxt.getUpdateable().lookup(name.getExplicit(true));
		return question.lookup(node.getName());
	}

	@Override
	public Value caseAEnumVarsetExpression(AEnumVarsetExpression node,
			Context question) throws AnalysisException
	{
		/*
		 * Before we do anything we need to find out if this is a channelexp or nameexp. In most cases it does not make
		 * sense to make an empty channelset, so if its empty we assume that this is a nameset
		 */
		if (node.getChannelNames().size() > 0
				&& isChannelSetExp(node.getChannelNames().get(0), question))
		{
			Set<ChannelNameValue> coms = new HashSet<ChannelNameValue>();
			for (ANameChannelExp chanNameExp : node.getChannelNames())
			{
				ChannelNameValue channelName = createChannelNameValue(chanNameExp, question);
				coms.add(channelName);
			}

			return new ChannelNameSetValue(coms);

		} else
		{
			// FIXME At the moment we only support simple names without any expressions after it!
			Set<ILexNameToken> names = new HashSet<ILexNameToken>();
			for (ANameChannelExp chanNameExp : node.getChannelNames())
			{
				names.add(NamespaceUtility.createSimpleName(chanNameExp.getIdentifier()));
			}

			return new NamesetValue(names);
		}
	}

	@Override
	public Value caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression node,
			Context question) throws AnalysisException
	{
		Value leftValue = node.getLeft().apply(this, question);
		Value rightValue = node.getRight().apply(this, question);

		if (leftValue instanceof ChannelNameSetValue
				&& rightValue instanceof ChannelNameSetValue)
		{
			ChannelNameSetValue leftCNV = (ChannelNameSetValue) leftValue;
			leftCNV.addAll((ChannelNameSetValue) rightValue);
			return leftCNV;
		} else if (leftValue instanceof NamesetValue
				&& rightValue instanceof NamesetValue)
		{
			NamesetValue leftNameset = (NamesetValue) leftValue;
			leftNameset.addAll((NamesetValue) rightValue);
			return leftNameset;
		} else
			throw new CmlInterpreterException(node, InterpretationErrorMessages.FATAL_ERROR.customizeMessage(""));

	}

	@Override
	public Value caseAFatCompVarsetExpression(AFatCompVarsetExpression node,
			Context ctxt) throws AnalysisException
	{

		Value set = null;

		if (isChannelSetExp(node.getChannelNameExp(), ctxt))
		{
			set = new ChannelNameSetValue(new HashSet<ChannelNameValue>());
		} else
		{
			set = new NamesetValue(new HashSet<ILexNameToken>());
		}

		try
		{
			QuantifierList quantifiers = new QuantifierList();

			for (PMultipleBind mb : node.getBindings())
			{
				ValueList bvals = PMultipleBindAssistantInterpreter.getBindValues(mb, ctxt);

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
					if (set instanceof ChannelNameSetValue)
					{
						((ChannelNameSetValue) set).add(createChannelNameValue(node.getChannelNameExp(), evalContext));
					} else if (set instanceof NamesetValue)
					{
						((NamesetValue) set).add(NamespaceUtility.createSimpleName(node.getChannelNameExp().getIdentifier()));
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
	public Value caseAUnresolvedPathExp(AUnresolvedPathExp node,
			Context question) throws AnalysisException
	{

		// FIXME This is just for testing, this should be done in a more generic way.

		Iterator<ILexIdentifierToken> iter = node.getIdentifiers().iterator();

		Value val = question.check(new CmlLexNameToken("", iter.next()));

		if (val.deref() instanceof RecordValue)
		{
			RecordValue recordVal = val.recordValue(question);
			Value fieldValue = recordVal.fieldmap.get(iter.next().getName());

			return fieldValue;
		} else if (val.deref() instanceof ObjectValue)
		{
			ObjectValue objectVal = val.objectValue(question);
			return objectVal.get(new CmlLexNameToken("", (ILexIdentifierToken) iter.next().clone()), false);
		}

		if (val.isUndefined())
			throw new CmlInterpreterException(node, InterpretationErrorMessages.EVAL_OF_UNDEFINED_VALUE.customizeMessage(node.toString(), node.getLocation().toString()));

		return val;
	}

	@Override
	public Value createNewReturnValue(INode node, Context question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value createNewReturnValue(Object node, Context question)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
