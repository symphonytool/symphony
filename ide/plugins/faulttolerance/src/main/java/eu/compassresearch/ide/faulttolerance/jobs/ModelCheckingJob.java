/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.util.Random;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class ModelCheckingJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public final class ModelCheckingJob extends Job {

	private final String processName;

	public ModelCheckingJob(Message jobNameMessage, String processName) {
		super(jobNameMessage.format(processName));
		this.processName = processName;
	}

	@Override
	protected final IStatus run(IProgressMonitor monitor) {
		try {
			ModelCheckingResult modelCheckingResult = new ModelCheckingResult();
			modelCheckingResult.setProcessName(processName);
			monitor.beginTask(
					Message.STARTING_MODEL_CHECKING.format(getName()), 100);
			performModelCheckingCall(modelCheckingResult, monitor);
			return new ModelCheckingStatus(modelCheckingResult);
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		} finally {
			monitor.done();
		}
	}

	private void performModelCheckingCall(ModelCheckingResult result,
			IProgressMonitor monitor) throws InterruptedException {
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			Thread.sleep(r.nextInt(100) * 10);
			monitor.worked(10);
		}
		result.setSuccess(r.nextInt(10) > 2);
		// TODO call formula with absoluteFilePath
	}

}
