package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.CmlExpressionVisitor;

public class UnresolvedExpressionValue extends Value implements ImpreciseValue
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8816224487604268890L;
	private static CmlExpressionVisitor expVisitor = new CmlExpressionVisitor();
	
	private PExp unresolvedExpression;
	private Context evalContext;
		
	public UnresolvedExpressionValue(PExp unresolvedExpression, Context evalContext)
	{
		this.unresolvedExpression = unresolvedExpression;
		this.evalContext = evalContext;
	}
	
	@Override
	public boolean isResolvable(Value val)
	{
		try
		{
			unresolvedExpression.apply(expVisitor,evalContext);
		} catch (AnalysisException e)
		{
			return false;
		}
		
		return true;
	}
	
//	@Override
//	public Value resolve() throws AnalysisException
//	{
//		return unresolvedExpression.apply(expVisitor,evalContext);
//	}
	
	@Override
	public String toString()
	{
		return this.unresolvedExpression.toString();
	}

	@Override
	public boolean equals(Object other)
	{
		if(other instanceof UnresolvedExpressionValue)
		{
			return unresolvedExpression.equals(((UnresolvedExpressionValue) other).unresolvedExpression) && 
					evalContext.equals(((UnresolvedExpressionValue) other).evalContext);
		}
		else
			return false;
	}

	@Override
	public int hashCode()
	{
		return unresolvedExpression.hashCode() + evalContext.hashCode();
	}

	@Override
	public String kind()
	{
		return UnresolvedExpressionValue.class.getSimpleName();
	}

	@Override
	public Object clone()
	{
		return new UnresolvedExpressionValue(unresolvedExpression, evalContext);
	}

}
