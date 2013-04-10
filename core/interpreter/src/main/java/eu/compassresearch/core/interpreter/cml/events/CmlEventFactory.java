package eu.compassresearch.core.interpreter.cml.events;

import java.util.List;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlChannel;

public class CmlEventFactory {

	protected static CmlTauEvent instance = null;
	
	/*
	 * Tau event factory methods
	 */
	
	public static CmlSpecialEvent referenceTauEvent()
	{
		if(instance == null)
			instance = new CmlTauEvent(null,null,null,"referenceTau");
		
		return instance;
	}
	
	public static CmlSpecialEvent newTauEvent(CmlBehaviourThread source, INode transitionSrcNode, INode transitionDstNode, String transitionMessage)
	{
		return new CmlTauEvent(source, transitionSrcNode,transitionDstNode,transitionMessage);
	}
	
	
	/*
	 * prefix event factory methods 
	 */
	
	public static ObservableEvent newPrefixEvent(CmlBehaviourThread eventSource, CmlChannel channel) {
		return new PrefixEvent(eventSource,channel);
	}
	
	public static ObservableEvent newPrefixEvent(Set<CmlBehaviourThread> eventSources, CmlChannel channel) {
		return new PrefixEvent(eventSources,channel);
	}
	
	public static ObservableEvent newPrefixEvent(CmlChannel channel) {
		return new PrefixEvent(channel);
	}
	
	/*
	 * communication event factory methods 
	 */
	
	public static ObservableEvent newCmlCommunicationEvent(CmlBehaviourThread source, CmlChannel channel, List<CommunicationParameter> params)
	{
		return new CmlCommunicationEvent(source, channel, params);
	}
	
	public static ObservableEvent newCmlCommunicationEvent(CmlChannel channel, List<CommunicationParameter> params)
	{
		return new CmlCommunicationEvent(channel, params);
	}
	
}
