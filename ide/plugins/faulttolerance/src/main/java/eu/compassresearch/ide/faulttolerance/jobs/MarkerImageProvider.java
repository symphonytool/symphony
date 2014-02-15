/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.internal.ide.IMarkerImageProvider;

import eu.compassresearch.ide.faulttolerance.Image;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class MarkerImageProvider"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class MarkerImageProvider implements IMarkerImageProvider,
		IMarkerModifier {

	@Override
	public String getImagePath(IMarker marker) {
		int severity = marker.getAttribute(IMarker.SEVERITY, 0);
		boolean success = marker.getAttribute(ATTRIBUTE_SUCCESS, false);
		Image img = null;
		switch (severity) {
		case IMarker.SEVERITY_ERROR:
			img = Image.FT_RED;
			break;
		case IMarker.SEVERITY_WARNING:
			img = Image.FT_ORANGE;
			break;
		case IMarker.SEVERITY_INFO:
			if (success) {
				img = Image.FT_GREEN;
			} else {
				img = Image.FT_BLUE;
			}
			break;
		}

		return img == null ? null : img.path();
	}

}
