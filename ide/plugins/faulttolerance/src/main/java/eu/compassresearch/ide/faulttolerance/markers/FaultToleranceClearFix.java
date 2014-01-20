/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.markers, class FaultToleranceClearFix"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceClearFix implements IMarkerResolution {

	private final String processName;

	public FaultToleranceClearFix(String processName) {
		this.processName = processName;
	}

	@Override
	public String getLabel() {
		return "Clear " + processName + " fault tolerance verification.";
	}

	@Override
	public void run(IMarker marker) {
		MarkerManager.clearMarkers(processName, marker.getResource());
	}

}
