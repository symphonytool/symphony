/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.core.resources.IFile;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class FaultToleranceProperty"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceProperty {

	private final FaultToleranceType type;
	private boolean satisfied;
	private boolean checked;
	private IFile formulaScriptFile;
	private String modelCheckerProperty;
	private String implementationExpression;
	private String specificationExpression;

	private Exception exception;
	private boolean canceledByUser;

	public FaultToleranceProperty(FaultToleranceType type) {
		this.type = type;
	}

	public FaultToleranceType getType() {
		return type;
	}

	public boolean isSatisfied() {
		return satisfied;
	}

	public void setSatisfied(boolean satisfied) {
		this.satisfied = satisfied;
	}

	public String getModelCheckerProperty() {
		return modelCheckerProperty;
	}

	public void setModelCheckerProperty(String modelCheckerProperty) {
		this.modelCheckerProperty = modelCheckerProperty;
	}

	public boolean isChecked() {
		return checked;
	}

	public Exception getException() {
		return exception;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public boolean isCanceledByUser() {
		return canceledByUser;
	}

	public void setCanceledByUser(boolean canceledByUser) {
		this.canceledByUser = canceledByUser;
	}

	public String getImplementationExpression() {
		return implementationExpression;
	}

	public String getSpecificationExpression() {
		return specificationExpression;
	}

	public void setImplementationExpression(String implementationExpression) {
		this.implementationExpression = implementationExpression;
	}

	public void setSpecificationExpression(String specificationExpression) {
		this.specificationExpression = specificationExpression;
	}

	public IFile getFormulaScriptFile() {
		return formulaScriptFile;
	}

	public void setFormulaScriptFile(IFile formulaScriptFile) {
		this.formulaScriptFile = formulaScriptFile;
	}
}
