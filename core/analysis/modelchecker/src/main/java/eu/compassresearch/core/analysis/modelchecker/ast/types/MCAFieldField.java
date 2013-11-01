package eu.compassresearch.core.analysis.modelchecker.ast.types;

public class MCAFieldField implements MCPCMLType {

	private String tagName;
	
	
	public MCAFieldField(String tagName) {
		super();
		this.tagName = tagName;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTagName() {
		return tagName;
	}


	public void setTagName(String tagName) {
		this.tagName = tagName;
	}


	@Override
	public MCPCMLType copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
