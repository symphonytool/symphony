package eu.compassresearch.core.analysis.modelchecker.visitors;

import eu.compassresearch.ast.definitions.AChannelNameDefinition;

public class ChannelTypeDefinition {
	protected AChannelNameDefinition chanDef;
	public String createdChanType = ""; 
	
	public ChannelTypeDefinition(AChannelNameDefinition chanDef) {
		super();
		this.chanDef = chanDef;
	}

	public AChannelNameDefinition getChanDef() {
		return chanDef;
	}

	public void setChanDef(AChannelNameDefinition chanDef) {
		this.chanDef = chanDef;
	}

	
}
