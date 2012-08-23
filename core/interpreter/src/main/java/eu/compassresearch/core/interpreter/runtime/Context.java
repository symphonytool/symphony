package eu.compassresearch.core.interpreter.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.scheduler.ProcessThread;

public class Context {
	

    /**
     * The enclosing Context to search if a symbol is not found in this
     */
	private Context outer;
	private ProcessThread processThread = null;
	private Set<LexIdentifierToken> synchronizationChannels;
	private Map<LexNameToken,Value> map;
	
	public Context()
	{
		this.outer = null;
		map = new HashMap<LexNameToken,Value>();
	}
	
	public Context(Context outer)
	{
		this.outer = outer;
		map = new HashMap<LexNameToken,Value>();
	}
	
	public void setProcessThread(ProcessThread processThread)
	{
		this.processThread = processThread;
	}
	
	public void setSynchronizationChannels(Set<LexIdentifierToken> channelNames)
	{
		synchronizationChannels = channelNames;
	}
	
	public void put(LexNameToken name, Value value)
	{
		map.put(name, value);
	}
}
