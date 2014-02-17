/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.core.resources.IFile;

import eu.compassresearch.ide.modelchecker.MCConstants;

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
	private boolean canceledByUser;
	private boolean canceledByPreRequisite;
	private IFile formulaScriptFile;
	private String modelCheckerProperty;
	private String implementationExpression;
	private String specificationExpression;

	private double elapsedTime;

	private Exception exception;

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

	@Override
	public String toString() {
		return format();
	}

	public String format() {
		String formatted;
		if (modelCheckerProperty.equals(MCConstants.DEADLOCK_PROPERTY)) {
			formatted = Message.DEADLOCK_CHECK.format(implementationExpression);
		} else if (modelCheckerProperty.equals(MCConstants.LIVELOCK_PROPERTY)) {
			formatted = Message.LIVELOCK_CHECK.format(implementationExpression);
		} else {
			formatted = "?";
		}
		return formatted;
	}

	public double getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public boolean isCanceledByUser() {
		return canceledByUser;
	}

	public void setCanceledByUser(boolean canceledByUser) {
		this.canceledByUser = canceledByUser;
	}

	public boolean isCanceledByPreRequisite() {
		return canceledByPreRequisite;
	}

	public void setCanceledByPreRequisite(boolean canceledByPreRequisite) {
		this.canceledByPreRequisite = canceledByPreRequisite;
	}

}
