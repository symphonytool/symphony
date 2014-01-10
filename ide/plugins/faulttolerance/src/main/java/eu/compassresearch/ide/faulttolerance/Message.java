/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import java.util.ResourceBundle;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class Message"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public enum Message {
	DIVERGENCE_FREE_JOB, SEMIFAIRNESS_JOB, FULL_FAULT_TOLERANCE_JOB,
	LIMITED_FAULT_TOLERANCE_JOB, STARTING_MODEL_CHECKING,
	CHECKING_PREREQUISITES, LIMIT_EXPRESSION, FULL_FAULT_TOLERANCE_SUCCESS,
	FULL_FAULT_TOLERANCE_ERROR, LIMITED_FAULT_TOLERANCE_SUCCESS,
	LIMITED_FAULT_TOLERANCE_ERROR, NO_PROJECT_SELECTED, MARKER_LOCATION,
	DIVERGENCE_FREE_SUCCESS, DIVERGENCE_FREE_ERROR, SEMIFAIR_SUCCESS,
	SEMIFAIR_ERROR;

	public String format(Object... params) {
		return String.format(
				ResourceBundle.getBundle("Message").getString(this.name()),
				params);
	}
}
