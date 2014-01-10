/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ide.faulttolerance.Activator;
import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationEvent;
import eu.compassresearch.ide.faulttolerance.jobs.IFaultToleranceVerificationListener;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.marker, class MarkerManager"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class MarkerManager implements IFaultToleranceVerificationListener {

	@Override
	public void divergenceFreeVerificationStarted() {
	}

	@Override
	public void divergenceFreeVerificationFinished(
			FaultToleranceVerificationEvent event) {
		int severity;
		String message;

		if (event.getResults().isDivergenceFree()) {
			severity = IMarker.SEVERITY_INFO;
			message = Message.DIVERGENCE_FREE_SUCCESS.format(event.getResults()
					.getProcessName());
		} else {
			severity = IMarker.SEVERITY_ERROR;
			message = Message.DIVERGENCE_FREE_ERROR.format(event.getResults()
					.getProcessName());
		}
		renewMarker(severity, message, event.getResults().getProcessName(),
				null, event.getResults().getResource(), event.getResults()
						.getLocation());
	}

	@Override
	public void semifairnessVerificationStarted() {
	}

	@Override
	public void semifairnessVerificationFinished(
			FaultToleranceVerificationEvent event) {
		int severity;
		String message;

		if (event.getResults().isSemifair()) {
			severity = IMarker.SEVERITY_INFO;
			message = Message.SEMIFAIR_SUCCESS.format(event.getResults()
					.getProcessName());
		} else {
			severity = IMarker.SEVERITY_ERROR;
			message = Message.SEMIFAIR_ERROR.format(event.getResults()
					.getProcessName());
		}
		renewMarker(severity, message, event.getResults().getProcessName(),
				null, event.getResults().getResource(), event.getResults()
						.getLocation());
	}

	@Override
	public void fullFaultToleranceVerificationStarted() {
	}

	private void renewMarker(int severity, String message, String processName,
			String limitProcess, IResource selectedResource,
			ILexLocation location) {
		try {
			if (selectedResource != null) {

				IMarker marker = selectedResource
						.createMarker(Activator.MARKERS_ID);
				marker.setAttribute(IMarker.LOCATION,
						Message.MARKER_LOCATION.format(location.getStartLine(),
								location.getStartPos()));
				marker.setAttribute(IMarker.SEVERITY, severity);
				marker.setAttribute(IMarker.MESSAGE, message);
				marker.setAttribute(IMarker.LINE_NUMBER,
						location.getStartLine());
				marker.setAttribute(IMarker.CHAR_START,
						location.getStartOffset());
				marker.setAttribute(IMarker.CHAR_END,
						location.getEndOffset() + 1);
				if (processName != null) {
					marker.setAttribute("processName", processName);
				}
				if (limitProcess != null) {
					marker.setAttribute("limitProcess", limitProcess);
				}
			}
		} catch (CoreException e) {
			//
		}
	}

	public void clearMarkers(String processName, IResource selectedResource) {
		try {
			IMarker[] markers = selectedResource.findMarkers(
					Activator.MARKERS_ID, true, IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				if (processName.equals(marker.getAttribute("processName"))) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
			//
		}
	}

	@Override
	public void fullFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event) {
		if (!event.getResults().isPreRequisitesOk()) {
			return;
		}
		int severity;
		String message;

		if (event.getResults().isFullFaultTolerant()) {
			severity = IMarker.SEVERITY_WARNING;
			message = Message.FULL_FAULT_TOLERANCE_SUCCESS.format(event
					.getResults().getProcessName());
		} else {
			severity = IMarker.SEVERITY_INFO;
			message = Message.FULL_FAULT_TOLERANCE_ERROR.format(event
					.getResults().getProcessName());
		}
		renewMarker(severity, message, event.getResults().getProcessName(),
				null, event.getResults().getResource(), event.getResults()
						.getLocation());
	}

	@Override
	public void limitedFaultToleranceVerificationStarted() {
	}

	@Override
	public void limitedFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event) {

		if (!event.getResults().isPreRequisitesOk()) {
			return;
		}

		int severity;
		String message;

		if (event.getResults().isLimitedFaultTolerant()) {
			severity = IMarker.SEVERITY_INFO;
			message = Message.LIMITED_FAULT_TOLERANCE_SUCCESS.format(event
					.getResults().getProcessName(), event.getResults()
					.getLimitExpression());
		} else {
			severity = IMarker.SEVERITY_ERROR;
			message = Message.LIMITED_FAULT_TOLERANCE_ERROR.format(event
					.getResults().getProcessName(), event.getResults()
					.getLimitExpression());
		}

		renewMarker(severity, message, event.getResults().getProcessName(),
				event.getResults().getLimitExpression(), event.getResults()
						.getResource(), event.getResults().getLocation());

	}

	@Override
	public void faultToleranceVerificationsFinished(
			FaultToleranceVerificationEvent event) {
		// TODO Auto-generated method stub

	}
}
