/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.modelchecker.ModelCheckerCaller;
import eu.compassresearch.ide.faulttolerance.modelchecker.ModelCheckingResult;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class ModelCheckingJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public abstract class ModelCheckingJob extends Job {

	private final int totalUnitsOfWork;
	private final ModelCheckingResult modelCheckingResult;
	private final ModelCheckerCaller caller;

	public ModelCheckingJob(Message jobNameMessage, String processName,
			int totalUnitsOfWork) {
		super(jobNameMessage.format(processName));
		this.totalUnitsOfWork = totalUnitsOfWork;
		this.modelCheckingResult = new ModelCheckingResult();
		this.modelCheckingResult.setProcessName(processName);
		this.caller = new ModelCheckerCaller();
	}

	@Override
	protected final IStatus run(IProgressMonitor monitor) {
		try {
			monitor.beginTask(
					Message.STARTING_MODEL_CHECKING.format(getName()),
					totalUnitsOfWork);
			performModelCheckingCall(modelCheckingResult, caller, monitor);
			return Status.OK_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		} finally {
			monitor.done();
		}
	}

	protected abstract void performModelCheckingCall(
			ModelCheckingResult results, ModelCheckerCaller caller,
			IProgressMonitor monitor)
			throws InterruptedException;

	public ModelCheckingResult getModelCheckingResult() {
		return modelCheckingResult;
	}

	public int getTotalUnitsOfWork() {
		return totalUnitsOfWork;
	}

}
