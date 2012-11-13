package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.cml.ChannelObserver;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlChannelEvent;
import eu.compassresearch.core.interpreter.cml.CmlCommunication;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlProcessObserver;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.CmlTauEvent;
import eu.compassresearch.core.interpreter.cml.CmlTrace;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluator;

public abstract class AbstractInstance<T extends INode> extends AbstractEvaluator<T>
		implements CmlProcess , ChannelObserver {
	
	protected CmlProcessState 			state;
	protected List<CmlProcess> 			children = new LinkedList<CmlProcess>();
	protected CmlProcess 				parent;
	protected CmlSupervisorEnvironment 	env;
	protected CmlTrace 					trace = new CmlTrace();
	protected List<CmlProcessObserver>  observers = new LinkedList<CmlProcessObserver>();

	public AbstractInstance(CmlProcess parent)
	{
		state = CmlProcessState.INITIALIZED;
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
			setState(CmlProcessState.RUNNING);
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
				setState(CmlProcessState.WAIT_EVENT);
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
		return getState() == CmlProcessState.RUNNABLE || 
				running() ||
				waiting();
	}

	@Override public boolean waiting() {
		return getState() == CmlProcessState.WAIT_EVENT ||
				getState() == CmlProcessState.WAIT_CHILD;
	}
	
	@Override
	public boolean running() {
		return getState() == CmlProcessState.RUNNING;
	}

	@Override
	public boolean finished() {
		return getState() == CmlProcessState.FINISHED;
	}

	@Override
	public boolean deadlocked() {
		return false;
	}
	
	protected void notifyOnStateChange(CmlProcessStateEvent event)
	{
		for(CmlProcessObserver o : observers)
			o.onStateChange(event);
	}
	
	@Override
	public void registerOnStateChanged(CmlProcessObserver observer) {
		observers.add(observer);
	}
	
	@Override
	public void unregisterOnStateChanged(CmlProcessObserver observer) {
		observers.remove(observer);
	}
	
	protected abstract void setState(CmlProcessState state);
	
	/**
	 * ChannelListener interface method.
	 * Here the process is notified when a registered channel is signalled by the supervisor
	 */

	@Override
	public void onChannelEvent(CmlChannelEvent event) {
		//enable the processthread to run again and unregister from the channel
		setState(CmlProcessState.RUNNABLE);
		
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
