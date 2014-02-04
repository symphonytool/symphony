/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

import eu.compassresearch.ide.faulttolerance.FaultToleranceProperty;

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
			String processName = marker.getAttribute("processName", "");
			FaultToleranceProperty property = (FaultToleranceProperty) marker
					.getAttribute("faultToleranceProperty");
			List<IMarkerResolution> resolutions = new LinkedList<>();
			resolutions.add(new FaultToleranceClearFix(processName));
			if (property != null) {
				resolutions.add(new ViewLtsGraphFix(property));
			}
			return resolutions.toArray(new IMarkerResolution[] {});
		} catch (CoreException e) {
			return new IMarkerResolution[0];
		}
	}

}
