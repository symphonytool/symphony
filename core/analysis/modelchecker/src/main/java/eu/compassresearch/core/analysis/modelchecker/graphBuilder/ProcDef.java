package eu.compassresearch.core.analysis.modelchecker.graphBuilder;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Process;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class ProcDef {
	private String name;
	private Type parametro;
	private Process process;

	public ProcDef(String name, Type parametro, Process process) {
		super();
		this.setName(name);
		this.setParametro(parametro);
		this.setProcess(process);
	}


	public Type getParametro() {
		return parametro;
	}


	public void setParametro(Type parametro) {
		this.parametro = parametro;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof ProcDef){
			ProcDef other = (ProcDef) obj;
			result = this.getName().equals(other.getName()) && this.getParametro().equals(other.getParametro()) && this.getProcess().equals(other.getProcess());
		}
		return result;
	}

}
