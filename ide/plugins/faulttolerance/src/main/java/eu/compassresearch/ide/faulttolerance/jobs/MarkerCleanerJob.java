/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class MarkerCleanerJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class MarkerCleanerJob extends WorkspaceJob implements IMarkerModifier {

	private final String systemName;
	private final IResource resource;

	public MarkerCleanerJob(String systemName, IResource resource) {
		super(Message.CLEAR_MARKERS_JOB_NAME.format(systemName));
		this.systemName = systemName;
		this.resource = resource;
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		clearMarkers();
		return Status.OK_STATUS;
	}

	private void clearMarkers() {
		try {
			IMarker[] markers = resource.findMarkers(MARKERS_ID, true,
					IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				if (systemName.equals(marker
						.getAttribute(ATTRIBUTE_SYSTEM_NAME))) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
			//
		}
	}

}
