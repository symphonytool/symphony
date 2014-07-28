/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a
 *         href="mailto:alrd@cin.ufpe.br">alrd@cin.ufpe.br</a>)
 *
 */
public class UpdateUnsupportedElementsJob extends
		FaultToleranceVerificationJobBase {

	/**
	 * @param request
	 * @param response
	 */
	public UpdateUnsupportedElementsJob(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(Message.UPDATE_UNSUPPORTED_ELEMENTS_JOB_NAME, request, response);
	}

	/**
	 * @see eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationJobBase#runInWorkspace(eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest,
	 *      eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus runInWorkspace(
			final IFaultToleranceVerificationRequest request,
			final IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		String taskName = Message.UPDATE_UNSUPPORTED_ELEMENTS_TASK_NAME
				.format(request.getSystemName());
		try {
			monitor.beginTask(taskName, 1);
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					request.getSourceUnit()
							.getProject()
							.addUnsupportedMarkers(
									response.getUnsupportedElementsInfo());
				}
			});

			monitor.worked(1);
			return Status.OK_STATUS;
		} finally {
			monitor.done();
		}
	}

}
