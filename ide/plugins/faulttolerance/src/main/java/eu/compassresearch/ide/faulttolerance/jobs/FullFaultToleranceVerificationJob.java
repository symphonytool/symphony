/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.util.Random;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FullFaultToleranceJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FullFaultToleranceVerificationJob extends
		FaultToleranceVerificationJob {
	private final ListenerFirer fullFaultToleranceStartFirer;
	private final ListenerFirer fullFaultToleranceFinishFirer;

	public FullFaultToleranceVerificationJob(
			final FaultToleranceVerificationResults results) {
		super(results, Message.FULL_FAULT_TOLERANCE_JOB, 1);
		this.fullFaultToleranceStartFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.fullFaultToleranceVerificationStarted();
			}
		};
		this.fullFaultToleranceFinishFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.fullFaultToleranceVerificationFinished(event);
			}
		};
		addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				fullFaultToleranceStartFirer.fire(null);
			}

			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingResult mcr = ((ModelCheckingJob) event.getJob())
						.getModelCheckingResult();
				FaultToleranceVerificationEvent ftEvent = new FaultToleranceVerificationEvent(
						results, mcr.isSuccess());
				results.setFullFaultTolerant(mcr.isSuccess());
				fullFaultToleranceFinishFirer.fire(ftEvent);
			}
		});
	}

	@Override
	protected void performFaultToleranceCall(ModelCheckingResult mcResults,
			FaultToleranceVerificationResults ftResults,
			IProgressMonitor monitor) throws InterruptedException {
		try {
			Thread.sleep(10000);
			Random r = new Random(System.currentTimeMillis());
			mcResults.setSuccess(r.nextInt(100) > 85);
		} finally {
			monitor.worked(1);
			ftResults.incrementVerification();
		}
	}

	@Override
	protected void performPrerequisitesNotMet(
			FaultToleranceVerificationResults faultToleranceResults,
			IProgressMonitor monitor) {
		faultToleranceResults.setFullFaultTolerant(false);
		monitor.worked(1);
	}

}
