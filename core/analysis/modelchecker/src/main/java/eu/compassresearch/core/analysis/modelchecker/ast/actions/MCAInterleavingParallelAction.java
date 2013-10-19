package eu.compassresearch.core.analysis.modelchecker.ast.actions;

public class MCAInterleavingParallelAction extends MCSParallelActionBase {

	public MCAInterleavingParallelAction(MCPAction leftAction,MCPAction rightAction) {
		super(leftAction, null, null, rightAction);
	}

	@Override
	public String toFormula(String option) {
		StringBuffer result = new StringBuffer();
		
		// it writes the interleave as a generalised parallelism with am empty synchronisation set
		result.append("genPar(");
		result.append(this.leftAction.toFormula(option));
		result.append(",");
		result.append("\"{}\"");
		result.append(",");
		// it writes the right process into the buffer
		result.append(this.rightAction.toFormula(option));
		result.append(")");

		return result.toString();

	}
	
}
