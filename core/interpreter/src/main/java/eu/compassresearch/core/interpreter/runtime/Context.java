package eu.compassresearch.core.interpreter.runtime;

import java.util.HashMap;
import java.util.Map;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.scheduler.ProcessThread;

public class Context {
	
    /**
     * The enclosing Context to search if a symbol is not found in this
     */
	private Context outer = null;
	private ProcessThread processThread = null;
	private String continueOnEvent = null;
	private ChannelSynchronizationConstraint channelConstraint = null;
	private Map<LexNameToken,Value> map;
	
	public Context()
	{
		map = new HashMap<LexNameToken,Value>();
	}
	
	public Context(Context outer)
	{
		this.outer = outer;
		map = new HashMap<LexNameToken,Value>();
	}
	
	public Context(Context outer, ChannelSynchronizationConstraint channelConstraint)
	{
		this.outer = outer;
		map = new HashMap<LexNameToken,Value>();
		this.channelConstraint = channelConstraint;
	}
	
	public void setProcessThread(ProcessThread processThread)
	{
		this.processThread = processThread;
	}
	
	public ProcessThread getProcessThread()
	{
		if (this.processThread == null)
			return outer.getProcessThread();
		else
			return this.processThread;
	}
	
	public void setChannelConstraint(ChannelSynchronizationConstraint channelConstraint)
	{
		this.channelConstraint = channelConstraint;
	}
	
	public ChannelSynchronizationConstraint  getChannelConstraint()
	{
		return this.channelConstraint;
	}
	
	public void put(LexNameToken name, Value value)
	{
		map.put(name, value);
	}
	
	public Value lookupName(LexNameToken name)
	{
		Value v = map.get(name);
        if (v == null)
          if (outer != null)
            v = outer.lookupName(name);
        return v;
	}

}
