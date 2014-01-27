/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.modelchecker;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class ModelCheckingResults"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class ModelCheckingResult {
	private boolean success;
	private ICmlSourceUnit cmlSourceUnit;
	private String processName;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ICmlSourceUnit getCmlSourceUnit() {
		return cmlSourceUnit;
	}

	public void setCmlSourceUnit(ICmlSourceUnit cmlSourceUnit) {
		this.cmlSourceUnit = cmlSourceUnit;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
