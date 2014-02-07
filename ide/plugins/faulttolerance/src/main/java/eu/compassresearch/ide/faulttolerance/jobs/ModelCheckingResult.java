/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class ModelCheckingResults"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class ModelCheckingResult {
	private boolean success;
	private String processName;

	private String formulaScriptAbsolutePath;

	private FormulaResult formulaResult;

	private Exception exception;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getFormulaScriptAbsolutePath() {
		return formulaScriptAbsolutePath;
	}

	public void setFormulaScriptAbsolutePath(String formulaScriptAbsolutePath) {
		this.formulaScriptAbsolutePath = formulaScriptAbsolutePath;
	}

	public FormulaResult getFormulaResult() {
		return formulaResult;
	}

	public void setFormulaResult(FormulaResult formulaResult) {
		this.formulaResult = formulaResult;
	}

}
