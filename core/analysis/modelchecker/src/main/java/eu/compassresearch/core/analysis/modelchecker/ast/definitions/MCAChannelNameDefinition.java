package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCChannel;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAChannelNameDefinition implements MCPCMLDefinition {

	private MCATypeSingleDeclaration typeSingle;
	private LinkedList<MCChannel> channelDefs;

	public MCAChannelNameDefinition(MCATypeSingleDeclaration typeSingle) {
		this.typeSingle = typeSingle;
		channelDefs = new LinkedList<MCChannel>();
		
		MCPCMLType type = typeSingle.getType(); 
		
		for (String name : typeSingle.getIdentifiers()) {
			MCChannel channel = new MCChannel(name,type);
			channelDefs.add(channel);
		}
	}

	@Override
	public String toFormula(String option) {
		//TODO Stub generated method
		return null;

	}

	public MCATypeSingleDeclaration getSingleType() {
		return typeSingle;
	}

	public void setSingleType(MCATypeSingleDeclaration singleType) {
		this.typeSingle = singleType;
	}

	public MCATypeSingleDeclaration getTypeSingle() {
		return typeSingle;
	}

	public void setTypeSingle(MCATypeSingleDeclaration typeSingle) {
		this.typeSingle = typeSingle;
	}

	public LinkedList<MCChannel> getChannelDefs() {
		return channelDefs;
	}

	public void setChannelDefs(LinkedList<MCChannel> channelDefs) {
		this.channelDefs = channelDefs;
	}

	
}
