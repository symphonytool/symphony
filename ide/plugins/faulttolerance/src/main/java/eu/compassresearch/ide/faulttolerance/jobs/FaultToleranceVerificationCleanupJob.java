/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceVerificationCleanupJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationCleanupJob extends
		FaultToleranceVerificationJobBase {

	private final IFolder folder;

	public FaultToleranceVerificationCleanupJob(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(Message.FILES_CLEANUP_JOB_NAME, request, response);
		this.folder = response.getFolder();
		add(new IFaultToleranceVerificationPreRequisite() {

			@Override
			public boolean checkPreRequisite() {
				return FaultToleranceVerificationCleanupJob.this.folder
						.exists();
			}
		});
	}

	@Override
	protected IStatus runInWorkspace(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(
					formatSystemName(Message.FILES_CLEANUP_TASK_NAME), 2);
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			folder.delete(true, new SubProgressMonitor(monitor, 1));
			finishFormula();
			return Status.OK_STATUS;
		} finally {
			monitor.done();
		}
	}

	/**
	 * 
	 */
	private void finishFormula() {
		try {
			FormulaIntegrator.getInstance().finalize();
		} catch (Throwable e) {
			// TODO log exception
		}
	}

}
