package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

public class MCAChannelsDefinition implements MCPCMLDefinition {

	private LinkedList<MCAChannelDefinition> channelNameDeclarations = new LinkedList<MCAChannelDefinition>();

	public MCAChannelsDefinition(
			LinkedList<MCAChannelDefinition> channelNameDeclarations) {
		super();
		this.channelNameDeclarations = channelNameDeclarations;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<MCAChannelDefinition> getChannelNameDeclarations() {
		return channelNameDeclarations;
	}

	public void setChannelNameDeclarations(
			LinkedList<MCAChannelDefinition> channelNameDeclarations) {
		this.channelNameDeclarations = channelNameDeclarations;
	}
	

}
