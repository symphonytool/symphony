package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.expressions.ASetRangeSetExp;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;

public class MCASetRangeSetExp implements MCPCMLExp {

	private MCPCMLExp first;
	private MCPCMLExp last;
	
	
	public MCASetRangeSetExp(MCPCMLExp first, MCPCMLExp last) {
		super();
		this.first = first;
		this.last = last;
	}


	@Override
	public String toFormula(String option) {
		ExpressionEvaluator expEvaluator = ExpressionEvaluator.getInstance();
		StringBuilder result = new StringBuilder();
		//it gets the array of values defined by this set range 
		LinkedList<String> values = expEvaluator.getValueSet(this);
		
		result.append("{");
		Iterator<String> it = values.iterator(); 
		while (it.hasNext()) {
			String item = (String) it.next();
			result.append(item);
			if(it.hasNext()){
				result.append(",");
			}
		}
		result.append("}");

		return result.toString();
	}


	public MCPCMLExp getFirst() {
		return first;
	}


	public void setFirst(MCPCMLExp first) {
		this.first = first;
	}


	public MCPCMLExp getLast() {
		return last;
	}


	public void setLast(MCPCMLExp last) {
		this.last = last;
	}

	
}
