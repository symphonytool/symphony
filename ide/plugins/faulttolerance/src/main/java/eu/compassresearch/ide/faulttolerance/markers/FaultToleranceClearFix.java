/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IMarkerResolution;

import eu.compassresearch.ide.faulttolerance.jobs.MarkerCleanerJob;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.markers, class FaultToleranceClearFix"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceClearFix implements IMarkerResolution {

	private final String systemName;

	public FaultToleranceClearFix(String systemName) {
		this.systemName = systemName;
	}

	@Override
	public String getLabel() {
		// TODO colocar em Message.java
		return "Clear " + systemName + " fault tolerance verification.";
	}

	@Override
	public void run(IMarker marker) {
		Job job = new MarkerCleanerJob(systemName, marker.getResource());
		job.schedule();
	}

}
