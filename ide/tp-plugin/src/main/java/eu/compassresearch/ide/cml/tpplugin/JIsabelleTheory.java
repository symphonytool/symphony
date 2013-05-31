package eu.compassresearch.ide.cml.tpplugin;

import eu.compassresearch.core.common.AnalysisArtifact;

public class JIsabelleTheory implements AnalysisArtifact {
	private IsabelleTheory ithy;
	
	public JIsabelleTheory(IsabelleTheory ithy) {
		this.ithy = ithy;
	}
	
	public IsabelleTheory getIsabelleTheory() {
		return ithy;
	}
	
}
