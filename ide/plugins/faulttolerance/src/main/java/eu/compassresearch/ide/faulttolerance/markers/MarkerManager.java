/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ide.faulttolerance.Activator;
import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationEvent;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationResults;
import eu.compassresearch.ide.faulttolerance.jobs.IFaultToleranceVerificationListener;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.markers, class MarkerManager"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class MarkerManager implements IFaultToleranceVerificationListener {

	public final static String ATTRIBUTE_PROCESS_NAME = "processName";

	@Override
	public void divergenceFreeVerificationStarted() {
	}

	@Override
	public void divergenceFreeVerificationFinished(
			FaultToleranceVerificationEvent event) {
		/*
		 * int severity; String message;
		 * 
		 * if (event.getResults(). isDivergenceFree()) { severity =
		 * IMarker.SEVERITY_INFO; message = Message. DIVERGENCE_FREE_SUCCESS
		 * .format (event.getResults() .getProcessName()); } else { severity =
		 * IMarker.SEVERITY_ERROR; message = Message.DIVERGENCE_FREE_ERROR .
		 * format(event.getResults() .getProcessName()); } renewMarker(severity,
		 * message, event.getResults( ).getProcessName(), null, event
		 * .getResults().getResource (), event.getResults() .getLocation());
		 */
	}

	@Override
	public void semifairnessVerificationStarted() {
	}

	@Override
	public void semifairnessVerificationFinished(
			FaultToleranceVerificationEvent event) {
		/*
		 * int severity; String message;
		 * 
		 * if (event.getResults().isSemifair()) { severity =
		 * IMarker.SEVERITY_INFO; message =
		 * Message.SEMIFAIR_SUCCESS.format(event.getResults()
		 * .getProcessName()); } else { severity = IMarker.SEVERITY_ERROR;
		 * message = Message.SEMIFAIR_ERROR.format(event.getResults()
		 * .getProcessName()); } renewMarker(severity, message,
		 * event.getResults().getProcessName(), null,
		 * event.getResults().getResource(), event.getResults() .getLocation());
		 */
	}

	@Override
	public void fullFaultToleranceVerificationStarted() {
	}

	@Override
	public void fullFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event) {
		/*
		 * if (!event.getResults().isPreRequisitesOk()) { return; } int
		 * severity; String message;
		 * 
		 * if (event.getResults().isFullFaultTolerant()) { severity =
		 * IMarker.SEVERITY_WARNING; message =
		 * Message.FULL_FAULT_TOLERANCE_SUCCESS.format(event
		 * .getResults().getProcessName()); } else { severity =
		 * IMarker.SEVERITY_INFO; message =
		 * Message.FULL_FAULT_TOLERANCE_ERROR.format(event
		 * .getResults().getProcessName()); } renewMarker(severity, message,
		 * event.getResults().getProcessName(), null,
		 * event.getResults().getResource(), event.getResults() .getLocation());
		 */
	}

	@Override
	public void limitedFaultToleranceVerificationStarted() {
	}

	@Override
	public void limitedFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event) {
		/*
		 * 
		 * if (!event.getResults().isPreRequisitesOk()) { return; }
		 * 
		 * int severity; String message;
		 * 
		 * if (event.getResults().isLimitedFaultTolerant()) { severity =
		 * IMarker.SEVERITY_INFO; message =
		 * Message.LIMITED_FAULT_TOLERANCE_SUCCESS.format(event
		 * .getResults().getProcessName(), event.getResults()
		 * .getLimitExpression()); } else { severity = IMarker.SEVERITY_ERROR;
		 * message = Message.LIMITED_FAULT_TOLERANCE_ERROR.format(event
		 * .getResults().getProcessName(), event.getResults()
		 * .getLimitExpression()); }
		 * 
		 * renewMarker(severity, message, event.getResults().getProcessName(),
		 * event.getResults().getLimitExpression(), event.getResults()
		 * .getResource(), event.getResults().getLocation());
		 */

	}

	@Override
	public void faultToleranceVerificationsFinished(
			FaultToleranceVerificationEvent event) {
		FaultToleranceVerificationResults r = event.getResults();
		int cases = (r.isDivergenceFree() ? 1 : 0) | (r.isSemifair() ? 2 : 0)
				| (r.isFullFaultTolerant() ? 4 : 0)
				| (r.isLimitedFaultTolerant() ? 8 : 0);
		System.out.println("Fault tolerance result: " + cases);
		switch (cases) {
		case 1:
		case 5:
		case 9:
		case 13:
			markerSemifair(r);
			break;
		case 2:
		case 6:
		case 10:
		case 14:
			markerDivergenceFree(r);
			break;
		case 3:
		case 7:
			markerLimitedFaultTolerant(r);
			break;
		case 0:
		case 4:
		case 8:
		case 12:
			markerDivergenceFreeSemifair(r);
			break;
		case 11:
			markerAllGood(r);
			break;
		case 15:
			markerFullFaultTolerant(r);
			break;
		}
	}

	private String getLimitExpression(FaultToleranceVerificationResults r) {
		final int maxLen = Message.LIMIT_EXPRESSION.format().length();
		String le = r.getLimitExpression();
		if (le.length() > maxLen) {
			le = le.substring(0, maxLen - 3) + "...";
		}
		return le;
	}

	private void markerFullFaultTolerant(FaultToleranceVerificationResults r) {
		renewMarker(
				IMarker.SEVERITY_WARNING,
				Message.FULL_FAULT_TOLERANCE_SUCCESS.format(r.getProcessName()),
				r.getProcessName(), null, r.getResource(), r.getLocation());

	}

	private void markerAllGood(FaultToleranceVerificationResults r) {
		renewMarker(
				IMarker.SEVERITY_INFO,
				Message.LIMITED_FAULT_TOLERANCE_SUCCESS.format(
						r.getProcessName(), getLimitExpression(r)),
				r.getProcessName(), null, r.getResource(), r.getLocation());

	}

	private void markerDivergenceFreeSemifair(
			FaultToleranceVerificationResults r) {
		renewMarker(IMarker.SEVERITY_ERROR,
				Message.DIVERGENCE_FREE_SEMIFAIR_ERROR.format(r
						.getProcessName()), r.getProcessName(), null,
				r.getResource(), r.getLocation());
	}

	private void markerLimitedFaultTolerant(FaultToleranceVerificationResults r) {
		renewMarker(
				IMarker.SEVERITY_ERROR,
				Message.LIMITED_FAULT_TOLERANCE_ERROR.format(
						r.getProcessName(), getLimitExpression(r)),
				r.getProcessName(), null, r.getResource(), r.getLocation());

	}

	private void markerDivergenceFree(FaultToleranceVerificationResults r) {
		renewMarker(IMarker.SEVERITY_ERROR,
				Message.DIVERGENCE_FREE_ERROR.format(r.getProcessName()),
				r.getProcessName(), null, r.getResource(), r.getLocation());
	}

	private void markerSemifair(FaultToleranceVerificationResults r) {
		renewMarker(IMarker.SEVERITY_ERROR,
				Message.SEMIFAIR_ERROR.format(r.getProcessName()),
				r.getProcessName(), null, r.getResource(), r.getLocation());

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
					marker.setAttribute(ATTRIBUTE_PROCESS_NAME, processName);
				}
				/*
				 * TODO: verify if needed. if (limitProcess != null) {
				 * marker.setAttribute("limitProcess", limitProcess); }
				 */
			}
		} catch (CoreException e) {
			//
		}
	}

	public static void clearMarkers(String processName,
			IResource selectedResource) {
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

}
