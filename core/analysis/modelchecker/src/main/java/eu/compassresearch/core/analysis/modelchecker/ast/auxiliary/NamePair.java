package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

public class NamePair {

	private String originalNameString;
	private String newName;
	
	public NamePair(String originalNameString, String newName) {
		super();
		this.originalNameString = originalNameString;
		this.newName = newName;
	}
	public String getOriginalNameString() {
		return originalNameString;
	}
	public void setOriginalNameString(String originalNameString) {
		this.originalNameString = originalNameString;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}

}
