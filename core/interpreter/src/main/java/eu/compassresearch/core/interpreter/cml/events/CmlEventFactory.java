package eu.compassresearch.core.interpreter.cml.events;

import java.util.List;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.CmlChannel;

public class CmlEventFactory {

	protected static CmlTau instance = null;
	
	/*
	 * Tau event factory methods
	 */
	
	public static CmlTau referenceTauEvent()
	{
		if(instance == null)
			instance = new CmlTau(null,null,null,"referenceTau");
		
		return instance;
	}
	
	public static CmlTau newTauEvent(CmlBehaviour source, INode transitionSrcNode, INode transitionDstNode, String transitionMessage)
	{
		return new CmlTau(source, transitionSrcNode,transitionDstNode,transitionMessage);
	}
	
	
	/*
	 * prefix event factory methods 
	 */
	
	public static AbstractChannelEvent newPrefixEvent(CmlBehaviour eventSource, CmlChannel channel) {
		return new PrefixEvent(eventSource,channel);
	}
	
	public static AbstractChannelEvent newPrefixEvent(Set<CmlBehaviour> eventSources, CmlChannel channel) {
		return new PrefixEvent(eventSources,channel);
	}
	
	public static AbstractChannelEvent newPrefixEvent(CmlChannel channel) {
		return new PrefixEvent(channel);
	}
	
	/*
	 * communication event factory methods 
	 */
	
	public static AbstractChannelEvent newCmlCommunicationEvent(CmlBehaviour source, CmlChannel channel, List<CommunicationParameter> params)
	{
		return new CmlCommunicationEvent(source, channel, params);
	}
	
	public static AbstractChannelEvent newCmlCommunicationEvent(CmlChannel channel, List<CommunicationParameter> params)
	{
		return new CmlCommunicationEvent(channel, params);
	}
	
}
