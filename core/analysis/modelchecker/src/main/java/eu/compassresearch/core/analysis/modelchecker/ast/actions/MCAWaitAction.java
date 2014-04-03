package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAWaitAction extends MCATimeoutAction {

	public MCAWaitAction(MCPCMLExp timeoutExpression) {
		super(new MCAStopAction(), new MCASkipAction(), timeoutExpression);
	}

}
