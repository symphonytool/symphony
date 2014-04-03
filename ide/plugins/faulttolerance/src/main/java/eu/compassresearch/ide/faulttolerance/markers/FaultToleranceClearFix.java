/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.jobs.IMarkerModifier;
import eu.compassresearch.ide.faulttolerance.jobs.MarkerCleanerJob;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.markers, class FaultToleranceClearFix"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceClearFix extends WorkbenchMarkerResolution implements IMarkerResolution2, IMarkerModifier {

	private final String systemName;

	public FaultToleranceClearFix(String systemName) {
		this.systemName = systemName;
	}

	@Override
	public String getLabel() {
		return Message.FAULT_TOLERANCE_CLEAR_FIX_LABEL.format(systemName);
	}

	@Override
	public void run(IMarker marker) {
		Job job = new MarkerCleanerJob(systemName, marker.getResource());
		job.schedule();
	}

	@Override
	public String getDescription() {
		return Message.FAULT_TOLERANCE_CLEAR_FIX_DESCRIPTION.format(systemName);
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public IMarker[] findOtherMarkers(IMarker[] markers) {
		List<IMarker> matches= new ArrayList<>();
		for (IMarker m: markers) {
			try {
				if (m.getType().equals(MARKERS_ID)) {
					matches.add(m);
				}
			} catch (CoreException e) {
				// ignore
			}
		}
		return matches.toArray(new IMarker[]{});
	}

}
