package eu.compassresearch.ide.modelchecker;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.common.AnalysisArtifact;

public class FormulaResultWrapper implements AnalysisArtifact {
	private FormulaResult result;
	private String property;

	public FormulaResultWrapper(FormulaResult result, String prop) {
		super();
		this.result = result;
		this.property = prop;
	}

	public FormulaResult getResult() {
		return result;
	}

	public void setResult(FormulaResult result) {
		this.result = result;
	}
	
	public void setProperty(String prop){
		this.property = prop;
	}
	
	public String getProperty(){
		return property;
	}
}
