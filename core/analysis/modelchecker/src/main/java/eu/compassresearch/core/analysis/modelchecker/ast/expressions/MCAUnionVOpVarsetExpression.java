package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MCAUnionVOpVarsetExpression implements MCPVarsetExpression {

	protected MCPVarsetExpression left;
	protected MCPVarsetExpression right;

	public MCAUnionVOpVarsetExpression(MCPVarsetExpression l,
			MCPVarsetExpression r) {
		this.left = l;
		this.right = r;
	}

	public LinkedList<MCANameChannelExp> getChannelNames() {
		LinkedList<MCANameChannelExp> result = new LinkedList<MCANameChannelExp>();

		result.addAll(left.getChannelNames());
		result.addAll(right.getChannelNames());

		return result;
	}

	@Override
	public String toFormula(String option) {
		LinkedList<MCANameChannelExp> channelNames = new LinkedList<MCANameChannelExp>();
		MCAFatEnumVarsetExpression syncSet = new MCAFatEnumVarsetExpression(
				channelNames);

		syncSet.getChannelNames().addAll(getChannelNames(left));
		syncSet.getChannelNames().addAll(getChannelNames(right));

		return syncSet.toFormula(option);
	}

	private Set<MCANameChannelExp> getChannelNames(MCPVarsetExpression exp) {
		Set<MCANameChannelExp> channelNames = new HashSet<>();
		if (exp instanceof MCAFatEnumVarsetExpression) {
			channelNames.addAll(((MCAFatEnumVarsetExpression) exp)
					.getChannelNames());
		} else if (exp instanceof MCAIdentifierVarsetExpression) {
			channelNames.addAll(((MCAIdentifierVarsetExpression) exp)
					.getChannelNames());
		}
		return channelNames;
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
