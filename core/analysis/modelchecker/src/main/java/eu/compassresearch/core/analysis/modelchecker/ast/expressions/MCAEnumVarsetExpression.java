package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

public class MCAEnumVarsetExpression implements MCPVarsetExpression {

	private LinkedList<MCANameChannelExp> channelNames = new LinkedList<MCANameChannelExp>();
	
	
	public MCAEnumVarsetExpression(LinkedList<MCANameChannelExp> channelNames) {
		super();
		this.channelNames = channelNames;
	}

	@Override
	public String toFormula(String option) {
		return "\""+channelNames.getFirst().toFormula(option)+"\"";
	}

	public LinkedList<MCANameChannelExp> getChannelNames() {
		return channelNames;
	}

	public void setChannelNames(LinkedList<MCANameChannelExp> channelNames) {
		this.channelNames = channelNames;
	}
	
	

}
