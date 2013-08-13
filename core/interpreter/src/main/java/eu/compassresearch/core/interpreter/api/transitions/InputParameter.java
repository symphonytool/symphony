package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.core.interpreter.CmlExpressionVisitor;
import eu.compassresearch.core.interpreter.api.values.AnyValue;

public class InputParameter extends CommunicationParameter {

	private AReadCommunicationParameter node;
	private Context 					evalContext;
	private ILexNameToken 				name;
	private CmlExpressionVisitor 		expVisitor = new CmlExpressionVisitor();
	
	public InputParameter(AReadCommunicationParameter node, AnyValue value, Context evalContext) {
		super(value,node);
		this.node = node;
		this.evalContext = evalContext;

		//add the pattern name to the constraint expression context
		if(node.getPattern() instanceof AIdentifierPattern)
		{
			AIdentifierPattern idPattern = (AIdentifierPattern)node.getPattern();
			name = idPattern.getName().clone();
			this.evalContext.putNew(new NameValuePair(name, value));
		}
	}
	
	public PPattern getPattern() {
		return node.getPattern();
	}
	
	public PExp getContraintExpression() {
		return node.getExpression();
	}
	
	public Context getContraintContext(Value val) {
		
		evalContext.put(name, val);
		return evalContext;
	}
	
	public boolean evaluateContraint(Value val) throws ValueException, AnalysisException
	{
		Context context = getContraintContext(val);
		return node.getExpression() == null || node.getExpression().apply(expVisitor,context).boolValue(context);
	}

	@Override
	public String toString() {
		return "." + getValue();
	}
}
