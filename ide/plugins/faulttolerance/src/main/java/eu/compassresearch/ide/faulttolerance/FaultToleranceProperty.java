/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import java.io.Serializable;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.ide.faulttolerance.jobs.ModelCheckingResult;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class FaultToleranceProperty"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceProperty implements Serializable {
	private static final long serialVersionUID = -4233877393072129545L;

	private final FaultToleranceType type;
	private boolean satisfied;
	private FormulaResult formulaResult;
	private String formulaScriptAbsolutePathName;
	private String modelCheckerProperty;
	private String processName;

	public FaultToleranceProperty(FaultToleranceType type) {
		this.type = type;
	}

	public FaultToleranceType getType() {
		return type;
	}

	public boolean isSatisfied() {
		return satisfied;
	}

	public FormulaResult getFormulaResult() {
		return formulaResult;
	}

	public String getFormulaScriptAbsolutePathName() {
		return formulaScriptAbsolutePathName;
	}

	public void setSatisfied(boolean satisfied) {
		this.satisfied = satisfied;
	}

	public void setFormulaResult(FormulaResult formulaResult) {
		this.formulaResult = formulaResult;
	}

	public void setFormulaScriptAbsolutePathName(
			String formulaScriptAbsolutePathName) {
		this.formulaScriptAbsolutePathName = formulaScriptAbsolutePathName;
	}

	public String getModelCheckerProperty() {
		return modelCheckerProperty;
	}

	public void setModelCheckerProperty(String modelCheckerProperty) {
		this.modelCheckerProperty = modelCheckerProperty;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void update(ModelCheckingResult results) {
		setFormulaResult(results.getFormulaResult());
		setFormulaScriptAbsolutePathName(results.getFormulaScriptAbsolutePath());
		setProcessName(results.getProcessName());
		setSatisfied(results.isSuccess());
	}
}
