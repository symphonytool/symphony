/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.ide.faulttolerance.FaultToleranceProperty;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class MarkerUpdaterJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class MarkerUpdaterJob extends FaultToleranceVerificationJobBase
		implements IMarkerModifier {

	public MarkerUpdaterJob(IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(Message.MARKER_UPDATER_JOB_NAME, request, response);
	}

	@Override
	protected IStatus runInWorkspace(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(
					formatSystemName(Message.MARKER_UPDATER_TASK_NAME), 4);
			handleExceptionAndUserCancelation(request, response);
			handleCases(request, response);
			return Status.OK_STATUS;
		} finally {
			monitor.done();
		}
	}

	private void handleExceptionAndUserCancelation(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {

		if (response.getException() != null) {
			renewMarker(
					IMarker.SEVERITY_ERROR,
					Message.EXCEPTION_OCCURRED.format(request.getSystemName(),
							"-", response.getException().getLocalizedMessage()),
					request, response);
		} else {
			for (FaultToleranceProperty property : response.properties()) {
				handleExceptionAndUserCancelation(property, request, response);
			}
		}
	}

	private void handleExceptionAndUserCancelation(
			FaultToleranceProperty property,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		if (property.isCanceledByUser()) {
			renewMarker(IMarker.SEVERITY_INFO, Message.CANCELED_BY_USER.format(
					request.getSystemName(), property.format()), request,
					response);
		} else if (property.getException() != null) {
			renewMarker(IMarker.SEVERITY_ERROR,
					Message.EXCEPTION_OCCURRED.format(request.getSystemName(),
							property.format(), property.getException()
									.getLocalizedMessage()), request, response);
		}

	}

	private void handleCases(IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {

		if (response.getException() != null) {
			return;
		}

		for (FaultToleranceProperty property : response.properties()) {
			if (property.isCanceledByUser() || property.getException() != null) {
				return;
			}
		}

		int cases = (response.getDivergenceFreedom().isSatisfied() ? 1 : 0)
				| (response.getSemifairness().isSatisfied() ? 2 : 0)
				| (response.getFullFaultTolerance().isSatisfied() ? 4 : 0)
				| (response.getLimitedFaultTolerance().isSatisfied() ? 8 : 0);
		System.out.println("Fault tolerance result: " + cases);
		switch (cases) {
		case 1:
		case 5:
		case 9:
		case 13:
			markerSemifair(request, response);
			break;
		case 2:
		case 6:
		case 10:
		case 14:
			markerDivergenceFree(request, response);
			break;
		case 3:
		case 7:
			markerLimitedFaultTolerant(request, response);
			break;
		case 0:
		case 4:
		case 8:
		case 12:
			markerDivergenceFreeSemifair(request, response);
			break;
		case 11:
			markerAllGood(request, response);
			break;
		case 15:
			markerFullFaultTolerant(request, response);
			break;
		}
	}

	private String getLimitExpression(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		/*
		 * final int maxLen = Message.LIMIT_EXPRESSION.format().length(); String
		 * le = r.getLimitExpression(); if (le.length() > maxLen) { le =
		 * le.substring(0, maxLen - 3) + "..."; } return le;
		 */
		return Message.LIMIT_PROCESS_NAME.format(request.getSystemName());
	}

	private String getTotalElapsedTimeFormatted(
			IFaultToleranceVerificationResponse response) {
		long total = 0;
		for (FaultToleranceProperty property : response.properties()) {
			total += property.getElapsedTime();
		}
		return String.format("%d s", total / 1000);
	}

	private void markerFullFaultTolerant(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		renewMarker(IMarker.SEVERITY_WARNING,
				Message.FULL_FAULT_TOLERANCE_SUCCESS.format(
						request.getSystemName(),
						getTotalElapsedTimeFormatted(response)), request,
				response);

	}

	private void markerAllGood(IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		renewMarker(IMarker.SEVERITY_INFO,
				Message.LIMITED_FAULT_TOLERANCE_SUCCESS.format(
						request.getSystemName(),
						getLimitExpression(request, response),
						getTotalElapsedTimeFormatted(response)), request,
				response);

	}

	private void markerDivergenceFreeSemifair(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		renewMarker(IMarker.SEVERITY_ERROR,
				Message.DIVERGENCE_FREE_SEMIFAIR_ERROR.format(
						request.getSystemName(),
						getTotalElapsedTimeFormatted(response)), request,
				response);
	}

	private void markerLimitedFaultTolerant(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		renewMarker(IMarker.SEVERITY_ERROR,
				Message.LIMITED_FAULT_TOLERANCE_ERROR.format(
						request.getSystemName(),
						getLimitExpression(request, response),
						getTotalElapsedTimeFormatted(response)), request,
				response);

	}

	private void markerDivergenceFree(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		renewMarker(IMarker.SEVERITY_ERROR,
				Message.DIVERGENCE_FREE_ERROR.format(request.getSystemName(),
						getTotalElapsedTimeFormatted(response)), request,
				response);
	}

	private void markerSemifair(IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		renewMarker(IMarker.SEVERITY_ERROR,
				Message.SEMIFAIR_ERROR.format(request.getSystemName(),
						getTotalElapsedTimeFormatted(response)), request,
				response);

	}

	private void renewMarker(int severity, String message,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		try {
			IMarker marker = request.getSourceUnit().getFile()
					.createMarker(MARKERS_ID);
			marker.setAttribute(IMarker.LOCATION, Message.MARKER_LOCATION
					.format(request.getLineNumber(), request.getCharStart()));
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.LINE_NUMBER, request.getLineNumber());
			marker.setAttribute(IMarker.CHAR_START, request.getCharStart());
			marker.setAttribute(IMarker.CHAR_END, request.getCharEnd());
			marker.setAttribute(ATTRIBUTE_SYSTEM_NAME, request.getSystemName());

		} catch (CoreException e) {
			//
		}
	}
}
