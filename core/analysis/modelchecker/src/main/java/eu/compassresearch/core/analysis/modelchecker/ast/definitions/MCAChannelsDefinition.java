package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

public class MCAChannelsDefinition implements MCPCMLDefinition {

	private LinkedList<MCAChannelNameDefinition> channelNameDeclarations = new LinkedList<MCAChannelNameDefinition>();

	public MCAChannelsDefinition(
			LinkedList<MCAChannelNameDefinition> channelNameDeclarations) {
		super();
		this.channelNameDeclarations = channelNameDeclarations;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<MCAChannelNameDefinition> getChannelNameDeclarations() {
		return channelNameDeclarations;
	}

	public void setChannelNameDeclarations(
			LinkedList<MCAChannelNameDefinition> channelNameDeclarations) {
		this.channelNameDeclarations = channelNameDeclarations;
	}
	

}
