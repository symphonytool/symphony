/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationResults;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.markers, class MarkerManager"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class MarkerManager {

	public final static String ATTRIBUTE_PROCESS_NAME = "processName";
	public final static String MARKERS_ID = "eu.compassresearch.ide.faulttolerance.verification.problem";

	public void faultToleranceVerificationsFinished(
			FaultToleranceVerificationResults r) {
		if (r.getDefinitionsMessage() != null) {
			markerDefinitions(r);
			return;
		}
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

	private void markerDefinitions(FaultToleranceVerificationResults r) {
		renewMarker(
				IMarker.SEVERITY_ERROR,
				Message.MISSING_DEFINITIONS.format(r.getProcessName(),
						r.getDefinitionsMessage()), r.getProcessName(), null,
				r.getResource(), r.getLocation());
	}

	private String getLimitExpression(FaultToleranceVerificationResults r) {
		/*
		 * final int maxLen = Message.LIMIT_EXPRESSION.format().length(); String
		 * le = r.getLimitExpression(); if (le.length() > maxLen) { le =
		 * le.substring(0, maxLen - 3) + "..."; } return le;
		 */
		return Message.LIMIT_PROCESS_NAME.format(r.getProcessName());
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
		renewMarker(IMarker.SEVERITY_ERROR,
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

				IMarker marker = selectedResource.createMarker(MARKERS_ID);
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
			IMarker[] markers = selectedResource.findMarkers(MARKERS_ID, true,
					IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				if (processName.equals(marker
						.getAttribute(ATTRIBUTE_PROCESS_NAME))) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
			//
		}
	}

}
