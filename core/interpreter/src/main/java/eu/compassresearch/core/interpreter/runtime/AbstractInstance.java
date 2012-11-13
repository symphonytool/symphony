package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlChannelEvent;
import eu.compassresearch.core.interpreter.cml.CmlCommunication;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.CmlTauEvent;
import eu.compassresearch.core.interpreter.cml.CmlTrace;
import eu.compassresearch.core.interpreter.cml.ChannelObserver;
import eu.compassresearch.core.interpreter.cml.ProcessState;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluator;

public abstract class AbstractInstance<T extends INode> extends AbstractEvaluator<T>
		implements CmlProcess , ChannelObserver {
	
	protected ProcessState state;
	protected List<CmlProcess> children = new LinkedList<CmlProcess>();
	protected CmlProcess parent;
	protected CmlSupervisorEnvironment env;
	protected CmlTrace trace = new CmlTrace();

	public AbstractInstance(CmlProcess parent)
	{
		state = ProcessState.INITIALIZED;
		this.parent = parent;
	}
	
	@Override
	public CmlTrace getTraceModel() {
		return trace;
	}
		
	@Override
	public CmlBehaviourSignal execute(CmlSupervisorEnvironment env) throws AnalysisException {
		this.env= env;

		//inspect if there are any immediate events
		CmlAlphabet alpha = inspect();
		CmlBehaviourSignal ret = null;

		//execute silently if the next is an invisible action
		if(alpha.containsSpecialEvent(CmlTauEvent.referenceTauEvent())){
			setState(ProcessState.RUNNING);
			//FIXME: this might not be the best idea
			trace.addEvent(alpha.getSpecialEvents().iterator().next());
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
				setState(ProcessState.WAIT_EVENT);
				registerChannelsInAlpha(alpha);
				ret = CmlBehaviourSignal.EXEC_SUCCESS;
			}
		}

		return ret;
	}
		
	private void registerChannelsInAlpha(CmlAlphabet alpha)
	{
		for(CmlCommunication com : alpha.getCommunicationEvents())
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
	public CmlSupervisorEnvironment supervisor() {
		return env;
	}
	
	/**
	 * Process graph methods
	 */
	
	@Override
	public long level() {

		if(parent() == null)
			return 0;
		else 
			return parent().level() + 1;
	}

	@Override
	public CmlProcess parent() {
		return parent;
	}

	@Override
	public List<CmlProcess> children() {
		return children;
	}

	@Override
	public boolean hasChildren() {
		return children.size() > 0;
	}

	/**
	 * State methods
	 */
	@Override
	public boolean started() {
		return getState() == ProcessState.RUNNING || 
				getState() == ProcessState.RUNNABLE ||
				getState() == ProcessState.WAIT_EVENT;
	}

	@Override public boolean waiting() {
		return getState() == ProcessState.WAIT_EVENT;
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
	
	/**
	 * ChannelListener interface method.
	 * Here the process is notified when a registered channel is signalled by the supervisor
	 */

	@Override
	public void onChannelEvent(CmlChannelEvent event) {
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
