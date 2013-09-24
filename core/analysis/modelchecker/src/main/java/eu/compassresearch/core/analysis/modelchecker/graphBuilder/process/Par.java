package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;

public class Par implements ParallelProcess {
	
	private Binding leftBinding;
	private Process left;
	private String syncSet;
	private Binding rightBinding;
	private Process right;
	

	public Par(Binding leftBinding, Process left, String syncSet, Binding rightBinding, Process right) {
		this.leftBinding = leftBinding;
		this.left = left;
		this.syncSet = syncSet;
		this.rightBinding = rightBinding;
		this.right = right;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Par) {
			Par other = (Par) obj;
			result = //this.getBinding1().equals(other.getBinding1())
					//&& this.getBinding2().equals(other.getBinding2())
					this.getLeft().equals(other.getLeft())
					&& this.getRight().equals(other.getRight())
					&& this.getSyncSet().equals(other.getSyncSet());
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "(" +this.leftBinding.toString() + "," + this.getLeft().toString() + ") " + this.syncSet + " ("+ this.rightBinding.toString() + "," + this.getRight().toString() + ")";
	}
	
	@Override
	public boolean isDeadlock(){
		return false;
	}

	public Binding getLeftBinding() {
		return leftBinding;
	}

	public void setLeftBinding(Binding leftBinding) {
		this.leftBinding = leftBinding;
	}

	public Process getLeft() {
		return left;
	}

	public void setLeft(Process left) {
		this.left = left;
	}

	public String getSyncSet() {
		return syncSet;
	}

	public void setSyncSet(String syncSet) {
		this.syncSet = syncSet;
	}

	public Binding getRightBinding() {
		return rightBinding;
	}

	public void setRightBinding(Binding rightBinding) {
		this.rightBinding = rightBinding;
	}

	public Process getRight() {
		return right;
	}

	public void setRight(Process right) {
		this.right = right;
	}
	
	
	
}
