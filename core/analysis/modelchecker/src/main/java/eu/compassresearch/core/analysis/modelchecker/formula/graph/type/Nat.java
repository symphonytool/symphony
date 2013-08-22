package eu.compassresearch.core.analysis.modelchecker.formula.graph.type;

public class Nat implements Type {
	
	private Object nat;
	
	
	public Nat(Object nat) {
		this.nat = nat;
	}

	@Override
	public String toString() {
		return nat.toString();
	}

}
