package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.ast.expressions.AFatCompVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPMultipleBind;

public class MCAFatCompVarsetExpression implements MCPVarsetExpression {

	private LinkedList<MCPMultipleBind> bindings;
	private MCANameChannelExp channelNameExp;
	
	

	public MCAFatCompVarsetExpression(LinkedList<MCPMultipleBind> bindings,
			MCANameChannelExp channelNameExp) {
		super();
		this.bindings = bindings;
		this.channelNameExp = channelNameExp;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append("\"");
		result.append(channsetToString(option));
		result.append("\"");
		
		return result.toString();
	}

	private String channsetToString(String option){
		StringBuilder result = new StringBuilder();
		result.append("{|");
		result.append(this.channelNameExp.toFormula(option));
		result.append("|}");
		
		return result.toString();
	}
	@Override
	public LinkedList<MCANameChannelExp> getChannelNames() {
		LinkedList<MCANameChannelExp> result = new LinkedList<MCANameChannelExp>();
		result.add(this.channelNameExp);
		
		return result;
	}

	public LinkedList<MCPMultipleBind> getBindings() {
		return bindings;
	}

	public void setBindings(LinkedList<MCPMultipleBind> bindings) {
		this.bindings = bindings;
	}

	public MCANameChannelExp getChannelNameExp() {
		return channelNameExp;
	}

	public void setChannelNameExp(MCANameChannelExp channelNameExp) {
		this.channelNameExp = channelNameExp;
	}
	
	

}
