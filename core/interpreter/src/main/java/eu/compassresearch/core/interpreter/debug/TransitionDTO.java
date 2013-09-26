package eu.compassresearch.core.interpreter.debug;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexLocation;

public class TransitionDTO {

	private int transitionObjectId;
	private String name;
	private List<ILexLocation> locations;
	
	private TransitionDTO()
	{
		locations = new LinkedList<ILexLocation>();
	}
	
	public TransitionDTO(int transitionObjectId, String name, List<ILexLocation> locations)
	{
		this.transitionObjectId = transitionObjectId;
		this.name = name;
		this.locations = locations;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTransitionObjectId() {
		return transitionObjectId;
	}
	
	public List<ILexLocation> getLocations() {
		return locations;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
