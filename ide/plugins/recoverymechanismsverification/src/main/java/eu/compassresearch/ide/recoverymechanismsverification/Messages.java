/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification;

import java.util.ResourceBundle;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification, class Messages"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public enum Messages {
	DIVERGENCE_FREE_JOB, SEMIFAIRNESS_JOB, FULL_FAULT_TOLERANCE_JOB, LIMITED_FAULT_TOLERANCE_JOB, DIVERGENCE_FREE_OK, LIMITED_FAULT_TOLERANCE_VERIFICATION_COMPLETED, DIVERGENCE_FREE_VERIFICATION, SEMIFAIRNESS_VERIFICATION, FULL_FAULT_TOLERANCE_VERIFICATION, LIMITED_FAULT_TOLERANCE_VERIFICATION, VERIFY_FAULT_TOLERANCE, RUN_RMV, STARTING_MODEL_CHECKING, CHECKING_PREREQUISITES, LIMIT_EXPRESSION;

	public String getName() {
		return ResourceBundle.getBundle("Messages").getString(
				this.name() + ".name");
	}

	public String getTitle() {
		return ResourceBundle.getBundle("Messages").getString(
				this.name() + ".title");
	}

	public String getText() {
		return ResourceBundle.getBundle("Messages").getString(
				this.name() + ".text");
	}
}
