/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.modelchecker.ModelCheckerCaller;
import eu.compassresearch.ide.faulttolerance.modelchecker.ModelCheckingResult;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class LimitedFaultTolerance"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class LimitedFaultToleranceVerificationJob extends FaultToleranceVerificationJob {

	private final ListenerFirer limitedFaultToleranceStartFirer;
	private final ListenerFirer limitedFaultToleranceFinishFirer;

	public LimitedFaultToleranceVerificationJob(
			final FaultToleranceVerificationResults results) {
		super(results, Message.LIMITED_FAULT_TOLERANCE_JOB, 1);
		this.limitedFaultToleranceStartFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.limitedFaultToleranceVerificationStarted();
			}
		};
		this.limitedFaultToleranceFinishFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.limitedFaultToleranceVerificationFinished(event);
			}
		};
		addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				limitedFaultToleranceStartFirer.fire(null);
			}

			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingResult mcr = ((ModelCheckingJob) event.getJob())
						.getModelCheckingResult();
				FaultToleranceVerificationEvent ftEvent = new FaultToleranceVerificationEvent(
						results, mcr.isSuccess());
				results.setLimitedFaultTolerant(mcr.isSuccess());
				limitedFaultToleranceFinishFirer.fire(ftEvent);
			}
		});
	}

	@Override
	protected void performFaultToleranceCall(ModelCheckingResult mcResults,
			ModelCheckerCaller caller,
			FaultToleranceVerificationResults ftResults, IProgressMonitor monitor)
			throws InterruptedException {
		try {
			String specName = Message.NO_FAULTS_PROCESS_NAME.format(ftResults
					.getProcessName());
			String implName = Message.LAZY_LIMIT_PROCESS_NAME.format(ftResults
					.getProcessName());
			mcResults.setCmlSourceUnit(ftResults.getCmlSourceUnit());
			// caller.runFormula();
		} finally {
			monitor.worked(1);
		}
	}

	@Override
	protected void performPrerequisitesNotMet(
			FaultToleranceVerificationResults faultToleranceResults,
			IProgressMonitor monitor) {
		faultToleranceResults.setLimitedFaultTolerant(false);
		monitor.worked(1);
	}
}
