/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import eu.compassresearch.ide.faulttolerance.Messages;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public abstract class FaultToleranceVerificationJob extends ModelCheckingJob {

	private final FaultToleranceVerificationResults faultToleranceResults;

	private final List<IFaultToleranceVerificationListener> listeners;

	private final ListenerFirer divergenceFreeStartFirer;
	private final ListenerFirer divergenceFreeFinishFirer;
	private final ListenerFirer semifairnessStartFirer;
	private final ListenerFirer semifairnessFinishFirer;

	protected abstract class ListenerFirer {
		protected abstract void doFire(IFaultToleranceVerificationListener l,
				FaultToleranceVerificationEvent event);

		public final void fire(FaultToleranceVerificationEvent event) {
			for (IFaultToleranceVerificationListener l : FaultToleranceVerificationJob.this.listeners) {
				doFire(l, event);
			}
		}
	}

	public FaultToleranceVerificationJob(
			FaultToleranceVerificationResults results, Messages jobNameMessage,
			int totalUnitsOfWork) {
		super(jobNameMessage, totalUnitsOfWork);
		this.faultToleranceResults = results;
		this.listeners = new LinkedList<>();
		this.divergenceFreeStartFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.divergenceFreeVerificationStarted();
			}
		};
		this.divergenceFreeFinishFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.divergenceFreeVerificationFinished(event);
			}
		};
		this.semifairnessStartFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.semifairnessVerificationStarted();
			}
		};
		this.semifairnessFinishFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.semifairnessVerificationFinished(event);
			}
		};
	}

	public final void add(IFaultToleranceVerificationListener listener) {
		listeners.add(listener);
	}

	private void checkMonitor(IProgressMonitor monitor)
			throws InterruptedException {
		if (monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}

	@Override
	protected final void performModelCheckingCall(ModelCheckingResult results,
			IProgressMonitor monitor) throws InterruptedException {
		checkPreRequisites(monitor);

		if (faultToleranceResults.isPreRequisitesOk()) {
			performFaultToleranceCall(results, faultToleranceResults, monitor);
		} else {
			performPrerequisitesNotMet(faultToleranceResults, monitor);
		}
	}

	protected abstract void performPrerequisitesNotMet(
			FaultToleranceVerificationResults faultToleranceResults,
			IProgressMonitor monitor);

	protected abstract void performFaultToleranceCall(
			ModelCheckingResult mcResults,
			FaultToleranceVerificationResults ftResults,
			IProgressMonitor monitor) throws InterruptedException;

	private void checkPreRequisites(IProgressMonitor monitor)
			throws InterruptedException {
		faultToleranceResults.acquire();
		checkMonitor(monitor);
		if (!faultToleranceResults.isPrerequisitesChecked()) {
			IProgressMonitor group = Job.getJobManager().createProgressGroup();
			runPreRequisitesVerification(group);
			faultToleranceResults.setPrerequisitesChecked(true);
		}
		faultToleranceResults.release();
		monitor.worked(1);
	}

	private void runPreRequisitesVerification(IProgressMonitor group)
			throws InterruptedException {
		group.beginTask(Messages.CHECKING_PREREQUISITES.getText(), 2);

		DivergenceFreeVerificationJob dfj = new DivergenceFreeVerificationJob();
		SemifairnessVerificationJob sj = new SemifairnessVerificationJob();

		dfj.setProgressGroup(group, 1);
		sj.setProgressGroup(group, 1);

		dfj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingResult mcr = ((ModelCheckingJob) event.getJob())
						.getModelCheckingResult();
				FaultToleranceVerificationEvent ftEvent = new FaultToleranceVerificationEvent(
						faultToleranceResults, mcr.isSuccess());
				faultToleranceResults.setDivergenceFree(mcr.isSuccess());
				divergenceFreeFinishFirer.fire(ftEvent);
			}

			@Override
			public void aboutToRun(IJobChangeEvent event) {
				divergenceFreeStartFirer.fire(null);
			}
		});
		sj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingResult mcr = ((ModelCheckingJob) event.getJob())
						.getModelCheckingResult();
				FaultToleranceVerificationEvent ftEvent = new FaultToleranceVerificationEvent(
						faultToleranceResults, mcr.isSuccess());
				faultToleranceResults.setSemifair(mcr.isSuccess());
				semifairnessFinishFirer.fire(ftEvent);
			}

			@Override
			public void aboutToRun(IJobChangeEvent event) {
				semifairnessStartFirer.fire(null);
			}
		});

		dfj.schedule();
		sj.schedule();

		dfj.join();
		sj.join();

	}

}
