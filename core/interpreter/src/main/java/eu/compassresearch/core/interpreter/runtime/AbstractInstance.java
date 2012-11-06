package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.cml.CMLAlphabet;
import eu.compassresearch.core.interpreter.cml.CMLBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CMLChannelEvent;
import eu.compassresearch.core.interpreter.cml.CMLCommunication;
import eu.compassresearch.core.interpreter.cml.CMLProcess;
import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.CMLTauEvent;
import eu.compassresearch.core.interpreter.cml.CMLTrace;
import eu.compassresearch.core.interpreter.cml.ChannelObserver;
import eu.compassresearch.core.interpreter.cml.ProcessState;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluator;

public abstract class AbstractInstance<T extends INode> extends AbstractEvaluator<T>
		implements CMLProcess , ChannelObserver {
	
	protected ProcessState state;
	protected List<CMLProcess> children = new LinkedList<CMLProcess>();
	protected CMLProcess parent;
	protected CMLSupervisorEnvironment env;
	protected CMLTrace trace = new CMLTrace();

	public AbstractInstance(CMLProcess parent)
	{
		state = ProcessState.INITIALIZED;
		this.parent = parent;
	}
	
	@Override
	public CMLTrace getTraceModel() {
		return trace;
	}
		
	@Override
	public CMLBehaviourSignal execute(CMLSupervisorEnvironment env) throws AnalysisException {
		this.env= env;

		//inspect if there are any immediate events
		CMLAlphabet alpha = inspect();
		CMLBehaviourSignal ret = null;

		//execute silently if the next is an invisible action
		if(alpha.containsSpecialEvent(CMLTauEvent.instance())){
			setState(ProcessState.RUNNING);
			trace.addEvent(CMLTauEvent.instance());
			ret = executeNext();
			//state = ProcessState.WAIT;
		}
		else 
		{	
			//If the selected event is in the immediate alphabet then we can continue
			if(env.communicationSelected() && alpha.containsCommunication(env.selectedCommunication()))
			{
				ret = executeNext();
				trace.addEvent(env.selectedCommunication());
			}
			//if no communication is selected by the supervisor or we cannot sync the selected events
			//then we go to wait state and wait for channelEvent
			else 
			{
				setState(ProcessState.WAIT);
				registerChannelsInAlpha(alpha);
				ret = CMLBehaviourSignal.EXEC_SUCCESS;
			}
		}

		return ret;
	}
		
	private void registerChannelsInAlpha(CMLAlphabet alpha)
	{
		for(CMLCommunication com : alpha.getCommunicationEvents())
		{
			switch(com.getCommunicationType())
			{
			case SIGNAL:
				com.getChannel().registerOnChannelSignal(this);
				break;
			case WRITE:
				com.getChannel().registerOnChannelWrite(this);
				break;
			case READ:
				com.getChannel().registerOnChannelRead(this);
				break;
			}
		}
	}
	
	public abstract void setState(ProcessState state);
	
	@Override
	public CMLSupervisorEnvironment supervisor() {
		return env;
	}

	@Override
	public CMLProcess parent() {
		return parent;
	}

	@Override
	public List<CMLProcess> children() {
		return children;
	}

	@Override
	public boolean hasChildren() {
		return children.size() > 0;
	}

	@Override
	public boolean started() {
		return getState() == ProcessState.RUNNING || 
				getState() == ProcessState.RUNNABLE ||
				getState() == ProcessState.WAIT;
	}

	@Override public boolean waiting() {
		return getState() == ProcessState.WAIT;
	}
	
	
	@Override
	public boolean running() {
		return getState() == ProcessState.RUNNING;
	}

	@Override
	public boolean finished() {
		return getState() == ProcessState.FINISHED;
	}

	@Override
	public boolean deadlocked() {
		return false;
	}

	@Override
	public void onChannelEvent(CMLChannelEvent event) {
		//enable the processthread to run again and unregister from the channel
		setState(ProcessState.RUNNABLE);
		
		switch(event.getEventType())
		{
		case READ:
			event.getChannel().unregisterOnChannelRead(this);
			break;
		case WRITE:
			event.getChannel().unregisterOnChannelWrite(this);
			break;
		case SIGNAL:
			event.getChannel().unregisterOnChannelSignal(this);
			break;
		
		}
	}
}
