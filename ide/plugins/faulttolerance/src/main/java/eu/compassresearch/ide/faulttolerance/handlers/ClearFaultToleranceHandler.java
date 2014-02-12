/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.handlers;

import org.eclipse.swt.widgets.Shell;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.markers.MarkerManager;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class ClearFaultToleranceHandler"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class ClearFaultToleranceHandler extends SelectProcessHandler {

	@Override
	protected void doOnSelectedProcess(ICmlSourceUnit su,
			PDefinition processDefinition, Shell shell) {
		MarkerManager.clearMarkers(processDefinition.getName().getFullName(),
				su.getFile());
	}

}
