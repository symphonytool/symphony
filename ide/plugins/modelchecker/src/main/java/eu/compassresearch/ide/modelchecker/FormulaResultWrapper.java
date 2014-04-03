package eu.compassresearch.ide.modelchecker;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.common.AnalysisArtifact;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

public class FormulaResultWrapper implements AnalysisArtifact {
	private FormulaResult result;
	private IFile svgFile;
	private String property;
	private IContainer mcFolder;
	private ICmlSourceUnit selectedUnit;
	private String analysedProcess;
	private IFile factsFile;

	public FormulaResultWrapper(FormulaResult result, IFile svgFile, String property,
			IContainer mcFolder, ICmlSourceUnit selectedUnit, String analysedProcess, IFile facts) {
		super();
		this.result = result;
		this.svgFile = svgFile;
		this.property = property;
		this.mcFolder = mcFolder;
		this.selectedUnit = selectedUnit;
		this.analysedProcess = analysedProcess;
		this.factsFile = facts;
	}

	
	public IContainer getMcFolder() {
		return mcFolder;
	}


	public void setMcFolder(IContainer mcFolder) {
		this.mcFolder = mcFolder;
	}


	public ICmlSourceUnit getSelectedUnit() {
		return selectedUnit;
	}


	public void setSelectedUnit(ICmlSourceUnit selectedUnit) {
		this.selectedUnit = selectedUnit;
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


	public String getAnalysedProcess() {
		return analysedProcess;
	}


	public void setAnalysedProcess(String analysedProcess) {
		this.analysedProcess = analysedProcess;
	}


	public IFile getFactsFile() {
		return factsFile;
	}


	public void setFactsFile(IFile factsFile) {
		this.factsFile = factsFile;
	}
	
}
