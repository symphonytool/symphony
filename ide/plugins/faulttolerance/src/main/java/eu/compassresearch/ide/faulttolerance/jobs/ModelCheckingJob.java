/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.ide.faulttolerance.Messages;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class ModelCheckingJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public abstract class ModelCheckingJob extends Job {

	private final int totalUnitsOfWork;
	private final ModelCheckingResult modelCheckingResult;

	public ModelCheckingJob(Messages jobNameMessage, int totalUnitsOfWork) {
		super(jobNameMessage.getName());
		this.totalUnitsOfWork = totalUnitsOfWork;
		this.modelCheckingResult = new ModelCheckingResult();
	}

	@Override
	protected final IStatus run(IProgressMonitor monitor) {
		try {
			monitor.beginTask(Messages.STARTING_MODEL_CHECKING.getText(),
					totalUnitsOfWork);
			performModelCheckingCall(modelCheckingResult, monitor);
			return Status.OK_STATUS;

		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
	}

	protected abstract void performModelCheckingCall(
			ModelCheckingResult results, IProgressMonitor monitor)
			throws InterruptedException;

	public ModelCheckingResult getModelCheckingResult() {
		return modelCheckingResult;
	}

}
