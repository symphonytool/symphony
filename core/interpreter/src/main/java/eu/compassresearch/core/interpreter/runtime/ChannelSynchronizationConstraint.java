package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.process.PProcess;

public class ChannelSynchronizationConstraint {

	private Set<LexIdentifierToken> synchronizationChannels;
	private List<PProcess> processes = new LinkedList<PProcess>();
	
	
	public ChannelSynchronizationConstraint(Set<LexIdentifierToken> synchronizationChannels, PProcess p)
	{
		this.synchronizationChannels = synchronizationChannels; 
		processes.add(p);
	}
	
	public Set<LexIdentifierToken> getSynchronizationChannels() {
		return synchronizationChannels;
	}

	private List<PProcess> getProcesses() {
		return processes;
	}
	
}
