package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.binding.Binding;

public class ExtraChoice extends Choice {
	
	private Binding bindingLeft;
	private Binding bindingRight;
	
	
	public ExtraChoice(Binding bindingLeft, Process left,Binding bindingRight, Process right) {
		super(left, right);
		this.bindingLeft = bindingLeft;
		this.bindingRight = bindingRight;
	}
	
	@Override
	public String toString() {
		//it needs to include bindings
		return firstProcess.toString() + "[+]" + secondProcess.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bindingLeft == null) ? 0 : bindingLeft.hashCode());
		result = prime * result
				+ ((bindingRight == null) ? 0 : bindingRight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtraChoice other = (ExtraChoice) obj;
		if (bindingLeft == null) {
			if (other.bindingLeft != null)
				return false;
		} else if (!bindingLeft.equals(other.bindingLeft))
			return false;
		if (bindingRight == null) {
			if (other.bindingRight != null)
				return false;
		} else if (!bindingRight.equals(other.bindingRight))
			return false;
		return true;
	}

}
