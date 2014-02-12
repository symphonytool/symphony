/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.util.Set;
import java.util.TreeSet;

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

	class MarkerData implements Comparable<MarkerData> {
		private final String message;
		private final int severity;
		private final boolean success;

		public MarkerData(boolean success, int severity, String message) {
			this.success = success;
			this.severity = severity;
			this.message = message;
		}

		@Override
		public int compareTo(MarkerData that) {
			int r = ((Integer) that.severity).compareTo(this.severity);
			if (r != 0) {
				return r;
			}
			r = this.success == that.success ? 0 : (this.success ? 1 : -1);
			if (r != 0) {
				return r;
			}
			return this.message.compareTo(that.message);
		}

		@Override
		public String toString() {
			return "MarkerData [success=" + success + ", severity=" + severity
					+ ", message=" + message + "]";
		}
	}

	@Override
	protected IStatus runInWorkspace(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(
					formatSystemName(Message.MARKER_UPDATER_TASK_NAME), 4);
			MarkerCleaner.clearMarkers(request.getSourceUnit().getFile(),
					request.getSystemName());
			Set<MarkerData> markerDataSet = new TreeSet<>();
			handleExceptionAndUserCancelation(markerDataSet, request, response);
			handleCases(markerDataSet, request, response);

			for (MarkerData markerData : markerDataSet) {
				renewMarker(markerData, request, response);
			}
			return Status.OK_STATUS;
		} finally {
			monitor.done();
		}
	}

	private void handleExceptionAndUserCancelation(
			Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {

		if (response.getException() != null) {
			markerDataSet
					.add(new MarkerData(false, IMarker.SEVERITY_ERROR,
							Message.EXCEPTION_OCCURRED.format(request
									.getSystemName(), "-", response
									.getException().getLocalizedMessage())));
		} else {
			for (FaultToleranceProperty property : response.properties()) {
				handleExceptionAndUserCancelation(property, markerDataSet,
						request, response);
			}
		}
	}

	private void handleExceptionAndUserCancelation(
			FaultToleranceProperty property, Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		if (property.getException() != null) {
			markerDataSet.add(new MarkerData(false, IMarker.SEVERITY_ERROR,
					Message.EXCEPTION_OCCURRED.format(request.getSystemName(),
							property.getType().formattedName(), property
									.getException().getLocalizedMessage())));
		} else if (!property.isChecked()) {
			markerDataSet.add(new MarkerData(false, IMarker.SEVERITY_INFO,
					Message.CANCELED_BY_USER.format(request.getSystemName(),
							property.getType().formattedName())));

		}

	}

	private void handleCases(Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {

		if (response.getException() != null) {
			return;
		}

		for (FaultToleranceProperty property : response.properties()) {
			if (!property.isChecked() || property.getException() != null) {
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
			markerSemifair(markerDataSet, request, response);
			break;
		case 2:
		case 6:
		case 10:
		case 14:
			markerDivergenceFree(markerDataSet, request, response);
			break;
		case 3:
		case 7:
			markerLimitedFaultTolerant(markerDataSet, request, response);
			break;
		case 0:
		case 4:
		case 8:
		case 12:
			markerDivergenceFreeSemifair(markerDataSet, request, response);
			break;
		case 11:
			markerAllGood(markerDataSet, request, response);
			break;
		case 15:
			markerFullFaultTolerant(markerDataSet, request, response);
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

	private void markerFullFaultTolerant(Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		markerDataSet.add(new MarkerData(false, IMarker.SEVERITY_WARNING,
				Message.FULL_FAULT_TOLERANCE_SUCCESS.format(
						request.getSystemName(),
						getTotalElapsedTimeFormatted(response))));
	}

	private void markerAllGood(Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		markerDataSet.add(new MarkerData(true, IMarker.SEVERITY_INFO,
				Message.LIMITED_FAULT_TOLERANCE_SUCCESS.format(
						request.getSystemName(),
						getLimitExpression(request, response),
						getTotalElapsedTimeFormatted(response))));
	}

	private void markerDivergenceFreeSemifair(Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		markerDataSet.add(new MarkerData(false, IMarker.SEVERITY_ERROR,
				Message.DIVERGENCE_FREE_SEMIFAIR_ERROR.format(
						request.getSystemName(),
						getTotalElapsedTimeFormatted(response))));
	}

	private void markerLimitedFaultTolerant(Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		markerDataSet.add(new MarkerData(false, IMarker.SEVERITY_ERROR,
				Message.LIMITED_FAULT_TOLERANCE_ERROR.format(
						request.getSystemName(),
						getLimitExpression(request, response),
						getTotalElapsedTimeFormatted(response))));

	}

	private void markerDivergenceFree(Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		markerDataSet.add(new MarkerData(false, IMarker.SEVERITY_ERROR,
				Message.DIVERGENCE_FREE_ERROR.format(request.getSystemName(),
						getTotalElapsedTimeFormatted(response))));
	}

	private void markerSemifair(Set<MarkerData> markerDataSet,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		markerDataSet.add(new MarkerData(false, IMarker.SEVERITY_ERROR,
				Message.SEMIFAIR_ERROR.format(request.getSystemName(),
						getTotalElapsedTimeFormatted(response))));

	}

	private void renewMarker(MarkerData data,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		try {
			IMarker marker = request.getSourceUnit().getFile()
					.createMarker(MARKERS_ID);
			marker.setAttribute(IMarker.LOCATION, Message.MARKER_LOCATION
					.format(request.getLineNumber(), request.getCharStart()));
			marker.setAttribute(IMarker.SEVERITY, data.severity);
			marker.setAttribute(IMarker.MESSAGE, data.message);
			marker.setAttribute(IMarker.LINE_NUMBER, request.getLineNumber());
			marker.setAttribute(IMarker.CHAR_START, request.getCharStart());
			marker.setAttribute(IMarker.CHAR_END, request.getCharEnd());
			marker.setAttribute(ATTRIBUTE_SYSTEM_NAME, request.getSystemName());
			marker.setAttribute(ATTRIBUTE_SUCCESS, data.success);

		} catch (CoreException e) {
			//
		}
	}
}
