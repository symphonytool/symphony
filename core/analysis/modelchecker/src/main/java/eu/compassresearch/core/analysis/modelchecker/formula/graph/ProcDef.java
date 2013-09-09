package eu.compassresearch.core.analysis.modelchecker.formula.graph;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.param.Param;
import eu.compassresearch.core.analysis.modelchecker.formula.graph.process.Process;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parametro == null) ? 0 : parametro.hashCode());
		result = prime * result + ((process == null) ? 0 : process.hashCode());
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
		ProcDef other = (ProcDef) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parametro == null) {
			if (other.parametro != null)
				return false;
		} else if (!parametro.equals(other.parametro))
			return false;
		if (process == null) {
			if (other.process != null)
				return false;
		} else if (!process.equals(other.process))
			return false;
		return true;
	}

}
