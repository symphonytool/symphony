package eu.compassresearch.core.interpreter.runtime;

import eu.compassresearch.core.interpreter.cml.CMLDomain;
import eu.compassresearch.core.interpreter.cml.CMLProcessNew;
import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ProcessState;

public abstract class AbstractCMLInstance implements CMLProcessNew {

	private CMLProcessNew parentProcess;
	private CMLSupervisorEnvironment sve;
	private ProcessState state;
	
	public AbstractCMLInstance(CMLProcessNew parent)
	{
		this.parentProcess = parent;
		state = ProcessState.INITIALIZED;
	}
	
	@Override
	public CMLProcessNew parent() {
		return this.parentProcess;
	}
	
//	@Override
//	public CMLProcessBehaviour behaviour() {
//		return this;
//	}

	@Override
	public CMLSupervisorEnvironment supervisor() {

		return sve;
	}

	@Override
	public void start(CMLSupervisorEnvironment sve) {
		this.sve = sve;
		state = ProcessState.RUNNABLE;
	}

	@Override
	public boolean started() {
		return state == ProcessState.RUNNABLE ||
				state == ProcessState.RUNNING||
				state == ProcessState.WAIT;
	}

	@Override
	public boolean running() {
		return false;
	}

	@Override
	public boolean finished() {
		return state == ProcessState.FINISHED;
	}

	@Override
	public boolean deadlocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean aborted() {
		// TODO Auto-generated method stub
		return false;
	}

}
