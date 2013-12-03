package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class ProdType implements Type {

	private Type first;
	private Type second;
	
	
	
	public ProdType(Type first, Type second) {
		super();
		this.first = first;
		this.second = second;
	}

	
	public Type getFirst() {
		return first;
	}


	public void setFirst(Type first) {
		this.first = first;
	}


	public Type getSecond() {
		return second;
	}


	public void setSecond(Type second) {
		this.second = second;
	}


	@Override
	public String toString() {
		return first.toString() + "." + second.toString();
	}

	@Override
	public String toFormula() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toFormulaWithState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toFormulaWithUnderscore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toFormulaGeneric() {
		// TODO Auto-generated method stub
		return null;
	}

}
