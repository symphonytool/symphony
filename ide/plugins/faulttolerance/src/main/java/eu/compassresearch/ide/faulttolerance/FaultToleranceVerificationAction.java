/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.jface.action.Action;

import eu.compassresearch.ast.definitions.AProcessDefinition;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class FaultToleranceVerificationAction"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationAction extends Action {

	private final AProcessDefinition processDefinition;

	public FaultToleranceVerificationAction(AProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	@Override
	public void run() {
	}

}
