package eu.compassresearch.ide.modelchecker;

import org.eclipse.core.resources.IFile;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.common.AnalysisArtifact;

public class FormulaResultWrapper implements AnalysisArtifact {
	private FormulaResult result;
	private IFile svgFile;
	private String property;

	public FormulaResultWrapper(FormulaResult result, IFile svgFile, String property) {
		super();
		this.result = result;
		this.svgFile = svgFile;
		this.property = property;
	}

	public IFile getSvgFile() {
		return svgFile;
	}

	public void setSvgFile(IFile svgFile) {
		this.svgFile = svgFile;
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
