package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public class SilentEvent extends AbstractCmlEvent implements CmlTau {

	protected static SilentEvent instance = null;
	public final static String tauString = "\u03C4".toLowerCase();
	private final INode transitionSrcNode;
	private final INode transitionDstNode;
	private String transitionText = null;
						
	public SilentEvent(CmlBehaviour source, INode transitionSrcNode, INode transitionDstNode, String transitionText)
	{
		super(source);
		this.transitionSrcNode = transitionSrcNode;
		this.transitionDstNode = transitionDstNode;
		this.transitionText = transitionText;
	}
	
	public SilentEvent(CmlBehaviour source, String transitionText)
	{
		super(source);
		this.transitionSrcNode = null;
		this.transitionDstNode = null;
		this.transitionText = transitionText;
	}
	
	
	@Override
	public int hashCode() {
		return tauString.hashCode();
	}

	@Override
	public String toString() {
		if(CmlRuntime.isShowHiddenEvents())
			return tauString + "(" + ( transitionSrcNode != null ? transitionSrcNode.getClass().getSimpleName(): "initial" ) 
				
					+ ( transitionText != null ? "--" + transitionText + "-->"  : "->" )
			
					+ ( transitionDstNode != null ? transitionDstNode.getClass().getSimpleName(): "" ) + ") : " + getEventSources();
		else
			return tauString;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof CmlTau))
			return false;
		else
			return super.equals(obj);
	}

	@Override
	public CmlAlphabet getAsAlphabet() {
		
		Set<CmlEvent> events = new HashSet<CmlEvent>();
		events.add(this);
		return new CmlAlphabet(events);
	}

}
