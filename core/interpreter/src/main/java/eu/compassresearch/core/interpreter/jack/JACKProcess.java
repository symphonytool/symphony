package eu.compassresearch.core.interpreter.jack;

import java.util.Iterator;

import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;

public interface JACKProcess {
	// Process Graph/Representation related methods
//	public long level();
	public JACKProcess parent();
//	public CMLProcessBehaviour behaviour();
	//public Iterator children(boolean recursive);
//	public CMLAlphabet childInspectedAlphabet(CMLProcessNew child);
//	public void setChildInspectedAlphabet(CMLProcessNew child, CMLAlphabet alpha);
//	public boolean hasChild(CMLProcessNew child, boolean recursive);
//	public boolean hasChildren();
	
	// Process state methods 
//	public boolean inKernelMode();
	public boolean started();
	public boolean running();
	public boolean finished();
	public boolean deadlocked();
//	public boolean isAnActiveObject();
	public boolean aborted();
//	public boolean backtracking();
	
	// Process Supervision related methods
//	public CMLProcessNetwork network();
	public CMLSupervisorEnvironment supervisor();
	//public Reason abortReason();
	//public void setAbort(Reason reason);
//	public void uncaughtException(Thread t, Throwable e);
	// Methods that the user ought to call
	public void start(CMLSupervisorEnvironment sve); //throws IllegalProcessStateException;
//	public void join();
	// Process Network graph searching facilities
//	public CMLProcessNew findInnerMostBacktrackable();
//	public CMLProcessNew findInnerMostRecursiveDefinition();
//	public CMLProcessNew findInnerMostHideable(CMLCommunication forComm);
//	public CMLProcessNew findInnerMostSequence(CMLCommunication seqOn);
//	public CMLProcessNew findInnerMostSynchronizable(CMLCommunication forComm);
	// Denotational Semantics Information that might be calculated
	//public List after(CSPCommunication comm);
	//public CSPTraces tracesModel();
	//public CSPFailures failuresModel();
	//public CSPDivergencies divergenciesModel();
	//public CSPFailuresDivergencies failuresDivergenciesModel();
}
