/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class ModelCheckingJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public final class ModelCheckingJob extends Job {

	private final String processName;
	private final String absolutePath;

	public ModelCheckingJob(Message jobNameMessage, String processName,
			String absolutePath) {
		super(jobNameMessage.format(processName));
		this.processName = processName;
		this.absolutePath = absolutePath;
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
		try {
			FormulaResult formulaResult = FormulaIntegrator.getInstance()
					.analyse(absolutePath);
			// TODO process formulaResult
			monitor.worked(100);
		} catch (FormulaIntegrationException e) {
			result.setException(e);
			result.setSuccess(false);
		}
	}

}
