package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;


public class VarDeclaration implements Process {
	
	private String name;
	private String type;
	private Process process;
	
	public VarDeclaration(String name, String type, Process process) {
		super();
		this.name = name;
		this.type = type;
		this.process = process;
	}

	@Override
	public String toString() {
		return "var " + name + " @ " + process.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof VarDeclaration){
			VarDeclaration other = (VarDeclaration) obj;
			result = name.equals(other.name);
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
	@Override
	public boolean isDeadlock(){
		return false;
	}

}
