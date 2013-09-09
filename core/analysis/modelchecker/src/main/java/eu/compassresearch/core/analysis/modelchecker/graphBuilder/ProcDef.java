package eu.compassresearch.core.analysis.modelchecker.graphBuilder;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.Param;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Process;

public class ProcDef {
	private String name;
	private Param parametro;
	private Process process;

	public ProcDef(String name, Param parametro, Process process) {
		super();
		this.setName(name);
		this.setParametro(parametro);
		this.setProcess(process);
	}

	public Param getParametro() {
		return parametro;
	}

	public void setParametro(Param parametro) {
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
