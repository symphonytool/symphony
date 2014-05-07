package eu.compassresearch.core.interpreter.api;

import java.util.Random;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.BooleanValue;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NaturalValue;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;

class RandomVDMValueGenerator extends AnswerCMLAdaptor<Value>
{
	private final Random rndValue;

	public RandomVDMValueGenerator(long seed)
	{
		rndValue = new Random(seed);
	}

	@Override
	public Value caseAIntNumericBasicType(AIntNumericBasicType node)
			throws AnalysisException
	{

		return new IntegerValue(rndValue.nextInt());
	}

	@Override
	public Value caseANatNumericBasicType(ANatNumericBasicType node)
			throws AnalysisException
	{

		try
		{

			return new NaturalValue(Math.abs(rndValue.nextLong()));
		} catch (Exception e)
		{
			throw new AnalysisException(e.getMessage(), e);
		}
	}

	@Override
	public Value caseANamedInvariantType(ANamedInvariantType node)
			throws AnalysisException
	{
		return node.getType().apply(this);
	}

	@Override
	public Value caseAUnionType(AUnionType node) throws AnalysisException
	{

		PType type = node.getTypes().get(rndValue.nextInt(node.getTypes().size()));

		return type.apply(this);
	}

	@Override
	public Value caseAQuoteType(AQuoteType node) throws AnalysisException
	{

		return new QuoteValue(node.getValue().getValue());
	}
	
	@Override
	public Value caseABooleanBasicType(ABooleanBasicType node)
			throws AnalysisException
	{
		return new BooleanValue(rndValue.nextBoolean());
	}

	@Override
	public Value caseAProductType(AProductType node) throws AnalysisException
	{

		ValueList argvals = new ValueList();

		for (int i = 0; i < node.getTypes().size(); i++)
		{
			argvals.add(node.getTypes().get(i).apply(this));
		}
		return new TupleValue(argvals);
	}

	@Override
	public Value createNewReturnValue(INode node)
	{
		throw new InterpreterRuntimeException("Missing implementation in random CML value generator for: "
				+ node.getClass().getName());
	}

	@Override
	public Value createNewReturnValue(Object node)
	{
		throw new InterpreterRuntimeException("Missing implementation in random CML value generator for: "
				+ node.getClass().getName());
	}
}