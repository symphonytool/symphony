package eu.compassresearch.ide.cml.mcplugin;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.common.AnalysisArtifact;

public class FormulaResultWrapper implements AnalysisArtifact {
	private FormulaResult result;

	public FormulaResultWrapper(FormulaResult result) {
		super();
		this.result = result;
	}

	public FormulaResult getResult() {
		return result;
	}

	public void setResult(FormulaResult result) {
		this.result = result;
	}
}
