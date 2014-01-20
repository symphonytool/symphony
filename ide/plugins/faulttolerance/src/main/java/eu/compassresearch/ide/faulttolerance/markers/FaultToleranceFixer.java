/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.markers, class FaultToleranceFixer"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceFixer implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			String processName = (String) marker.getAttribute("processName");
			return new IMarkerResolution[] { new FaultToleranceClearFix(
					processName) };
		} catch (CoreException e) {
			return new IMarkerResolution[0];
		}
	}

}
