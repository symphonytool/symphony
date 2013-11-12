package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

public class GenPar implements ParallelProcess {
	
	private Process left;
	private String syncSet;
	private Process right;
	
	
	public GenPar(Process left, String set, Process right) {

		this.left = left;
		this.syncSet = set;
		this.right = right;
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
	public Process getRight() {
		return right;
	}
	public void setRight(Process right) {
		this.right = right;
	}




	@Override
	public String toString() {
		return this.getLeft().toString() + " " + this.syncSet + " " + this.getRight().toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof GenPar) {
			GenPar other = (GenPar) obj;
			result = this.getLeft().equals(other.getLeft())
					&& this.getRight().equals(other.getRight());
		} 
		return result;
	}
	
	@Override
	public boolean isDeadlock(){
		return false;
	}


}
