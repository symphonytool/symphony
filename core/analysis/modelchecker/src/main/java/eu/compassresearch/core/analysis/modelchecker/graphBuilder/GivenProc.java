package eu.compassresearch.core.analysis.modelchecker.graphBuilder;


public class GivenProc {
	
	private String name;
	
	public GivenProc(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof GivenProc){
			GivenProc other = (GivenProc) obj;
			result = this.getName().equals(other.getName());
		}
		return result;
	}


}
