package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.core.interpreter.CmlExpressionVisitor;

public class ExpressionConstraint implements ValueConstraint
{

	private PExp constraintExpression;
	private Context evaluationContext;
	private ILexNameToken name;
	private CmlExpressionVisitor expVisitor = new CmlExpressionVisitor();

	// public ExpressionConstraint(PExp constraintExpression, Context evaluationContext, ILexNameToken name)
	// {
	// this.constraintExpression = constraintExpression;
	// this.evaluationContext = evaluationContext;
	// this.name = name;
	// }

	public ExpressionConstraint(AReadCommunicationParameter node,
			Context evaluationContext)
	{
		this.constraintExpression = node.getExpression();
		this.evaluationContext = evaluationContext;

		// add the pattern name to the constraint expression context
		if (node.getPattern() instanceof AIdentifierPattern)
		{
			AIdentifierPattern idPattern = (AIdentifierPattern) node.getPattern();
			name = idPattern.getName().clone();
			this.evaluationContext.putNew(new NameValuePair(name, new UndefinedValue()));
		}
	}

	@Override
	public boolean isValid(Value val) throws ValueException, AnalysisException
	{

		evaluationContext.put(name, val);
		return constraintExpression.apply(expVisitor, evaluationContext).boolValue(evaluationContext);
	}

}
