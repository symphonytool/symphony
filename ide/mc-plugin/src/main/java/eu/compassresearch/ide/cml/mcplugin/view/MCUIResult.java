package eu.compassresearch.ide.cml.mcplugin.view;

import org.eclipse.core.resources.IResource;
import eu.compassresearch.ide.cml.mcplugin.FormulaResultWrapper;

public class MCUIResult {
	protected IResource file;
	protected FormulaResultWrapper formulaResult;
	
	public MCUIResult(IResource file, FormulaResultWrapper formulaResult) {
		super();
		this.file = file;
		this.formulaResult = formulaResult;
	}

	public IResource getFile() {
		return file;
	}

	public void setFile(IResource file) {
		this.file = file;
	}

	public FormulaResultWrapper getFormulaResult() {
		return formulaResult;
	}

	public void setFormulaResult(FormulaResultWrapper formulaResult) {
		this.formulaResult = formulaResult;
	}
	
	public String getProperty(){
		return formulaResult.getProperty();
	}
	
}
