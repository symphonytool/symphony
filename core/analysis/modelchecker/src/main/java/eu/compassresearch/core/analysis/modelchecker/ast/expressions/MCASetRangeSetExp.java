package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

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
		//it gets the first and the last (real) values
		String firstValue = expEvaluator.obtainValue(this.getFirst());
		String lastValue = expEvaluator.obtainValue(this.getLast());
		if(firstValue != null && lastValue != null){
			Integer firstValueInt = Integer.valueOf(firstValue);
			Integer lastValueInt = Integer.valueOf(lastValue);
			result.append("{");
			int currValue = firstValueInt;
			while(currValue <= lastValueInt){
				result.append(currValue);
				if(currValue < lastValueInt){
					result.append(",");
				}
				currValue++;
			}
			result.append("}");
		}else{
			result.append("ASetRangeSetExp");
		}
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
