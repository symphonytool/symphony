package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.Iterator;
import java.util.LinkedList;

public class MCAEnumVarsetExpression implements MCPVarsetExpression {

	private LinkedList<MCANameChannelExp> channelNames = new LinkedList<MCANameChannelExp>();
	
	
	public MCAEnumVarsetExpression(LinkedList<MCANameChannelExp> channelNames) {
		super();
		this.channelNames = channelNames;
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
		result.append("{");
		Iterator<MCANameChannelExp> it = this.channelNames.iterator();
		while (it.hasNext()) {
			MCANameChannelExp item = (MCANameChannelExp) it.next();
			result.append(item.toFormula(option));
			if(it.hasNext()){
				result.append(",");
			}
		}
		result.append("}");
		
		return result.toString();
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAEnumVarsetExpression){
			result = this.channelNames.equals(((MCAEnumVarsetExpression) obj).getChannelNames());
		}
		return result;
	}

	public LinkedList<MCANameChannelExp> getChannelNames() {
		return channelNames;
	}

	public void setChannelNames(LinkedList<MCANameChannelExp> channelNames) {
		this.channelNames = channelNames;
	}
	
	

}
