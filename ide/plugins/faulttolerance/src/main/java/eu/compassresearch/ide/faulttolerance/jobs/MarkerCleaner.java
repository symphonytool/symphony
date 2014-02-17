/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class MarkerCleaner"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class MarkerCleaner implements IMarkerModifier {

	public static void clearMarkers(IResource resource, String systemName)
			throws CoreException {
		IMarker[] markers = resource.findMarkers(MARKERS_ID, true,
				IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			if (systemName.equals(marker.getAttribute(ATTRIBUTE_SYSTEM_NAME))) {
				marker.delete();
			}
		}
	}
}
