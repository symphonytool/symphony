package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;


public class MCAUnionVOpVarsetExpression implements MCPVarsetExpression{

	protected MCPVarsetExpression left;
	protected MCPVarsetExpression right;
	
	public MCAUnionVOpVarsetExpression(MCPVarsetExpression l, MCPVarsetExpression r) {
		super();
		this.left = l;
		this.right = r;
	}
	
	@Override
	public String toFormula(String option) {
		LinkedList<MCANameChannelExp> channelNames = new LinkedList<MCANameChannelExp>(); 
		MCAFatEnumVarsetExpression syncSet = new MCAFatEnumVarsetExpression(channelNames);
		
		if(left instanceof MCAFatEnumVarsetExpression){
			syncSet.getChannelNames().addAll(((MCAFatEnumVarsetExpression) left).getChannelNames());
		}
		if(right instanceof MCAFatEnumVarsetExpression){
			syncSet.getChannelNames().addAll(((MCAFatEnumVarsetExpression) right).getChannelNames());
		}
		return syncSet.toFormula(option);
	}

	public MCPVarsetExpression getLeft() {
		return left;
	}

	public void setLeft(MCPVarsetExpression left) {
		this.left = left;
	}

	public MCPVarsetExpression getRight() {
		return right;
	}

	public void setRight(MCPVarsetExpression right) {
		this.right = right;
	}

}
