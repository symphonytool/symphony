package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;

public class ValueLatticeElement extends Value implements LatticeElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3172714347731268950L;
	private final Value value;
	private final PType type;

	public ValueLatticeElement(PType type, Value value)
	{
		this.type = type;
		this.value = value;
	}

	@Override
	public LatticeElement meet(LatticeElement other)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public Value meet(Value other)
	// {
	// return other;
	// }

	@Override
	public PType getType()
	{
		return type;
	}

	@Override
	public boolean isComparable(LatticeElement other)
	{
		if (this.type.equals(other.getType()))
		{
			if (other.isTopElement())
				return true;
			else if (isMostPrecise() && other.isMostPrecise())
				return this.equals(other);
			else
				return false;
		} else
			return false;
	}

	// @Override
	// public boolean isComparable(Value other)
	// {
	// return this.value.equals(other);
	// }

	@Override
	public String toString()
	{
		return value.toString();
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof LatticeElement)
		{
			return this.type.equals(((LatticeElement) other).getType())
					&& this.value.equals(((LatticeElement) other).getValue());
		} else
			return false;
	}

	@Override
	public int hashCode()
	{
		return type.hashCode() + value.hashCode();
	}

	@Override
	public String kind()
	{
		return ValueLatticeElement.class.getSimpleName() + ":" + type;
	}

	@Override
	public Object clone()
	{
		return new ValueLatticeElement(type, value);
	}

	@Override
	public boolean isTopElement()
	{
		return false;
	}

	@Override
	public Value getValue()
	{
		return value;
	}

	@Override
	public boolean isMostPrecise()
	{
		return false;
	}

	private class IsComparableVisitor extends
			QuestionAnswerCMLAdaptor<LatticeElement, Boolean>
	{
		@Override
		public Boolean defaultPType(PType node, LatticeElement question)
				throws AnalysisException
		{
			return super.defaultPType(node, question);
		}

		@Override
		public Boolean caseAProductType(AProductType node,
				LatticeElement question) throws AnalysisException
		{
			return null;
		}
	}
}
