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
			result = //this.leftBinding.equals(other.getLeftBinding())
					//&& this.rightBinding.equals(other.getRightBinding())
					//&& this.syncSet.equals(other.getSyncSet())
					this.left.equals(other.getLeft())
					&& this.right.equals(other.getRight());
					
		} //else if (obj instanceof GenPar) {
		//	GenPar other = (GenPar) obj;
		//	result = //this.left.equals(other.getLeft())
					//&& this.right.equals(other.getRight());
		//			this.left.equals(other.getLeft())
		//			&& this.right.equals(other.getRight());
					
		//}

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
