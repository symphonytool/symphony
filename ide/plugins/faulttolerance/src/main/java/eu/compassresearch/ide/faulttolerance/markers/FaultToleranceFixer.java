/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

import eu.compassresearch.ide.faulttolerance.jobs.IMarkerModifier;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.markers, class FaultToleranceFixer"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceFixer implements IMarkerResolutionGenerator2,
		IMarkerModifier {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		String systemName = marker.getAttribute(ATTRIBUTE_SYSTEM_NAME, "");
		/*
		 * TODO add graph fix FaultToleranceProperty property =
		 * (FaultToleranceProperty) marker
		 * .getAttribute("faultToleranceProperty");
		 */
		List<IMarkerResolution> resolutions = new LinkedList<>();
		resolutions.add(new FaultToleranceClearFix(systemName));
		/*
		 * TODO add graph fix if (property != null) { resolutions.add(new
		 * ViewLtsGraphFix(property)); }
		 */
		return resolutions.toArray(new IMarkerResolution[] {});
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		try {
			return marker.getType().equals(MARKERS_ID);
		} catch (CoreException e) {
			return false;
		}
	}

}
