package eu.compassresearch.core.analysis.modelchecker.visitors;

import org.overture.ast.expressions.PExp;

public class Condition {
	protected PExp expression;
	protected int counter;
	
	public Condition(PExp expression, int counter) {
		super();
		this.expression = expression;
		this.counter = counter;
	}
	
}
