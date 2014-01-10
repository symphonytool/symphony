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

import eu.compassresearch.ide.faulttolerance.Message;

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
	private final ListenerFirer allFinishedFirer;

	protected abstract class ListenerFirer {
		protected abstract void doFire(IFaultToleranceVerificationListener l,
				FaultToleranceVerificationEvent event);

		public final void fire(FaultToleranceVerificationEvent event) {
			for (IFaultToleranceVerificationListener l : FaultToleranceVerificationJob.this.listeners) {
				doFire(l, event);
			}
			if (event.getResults().isAllVerificationsChecked(4)) {
				FaultToleranceVerificationEvent allFiredEvent = new FaultToleranceVerificationEvent(
						event.getResults(), true);
				allFinishedFirer.fire(allFiredEvent);
			}
		}
	}

	public FaultToleranceVerificationJob(
			FaultToleranceVerificationResults results, Message jobNameMessage,
			int totalUnitsOfWork) {
		super(jobNameMessage, results.getProcessName(), totalUnitsOfWork + 1);
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
		this.allFinishedFirer = new ListenerFirer() {
			@Override
			protected void doFire(IFaultToleranceVerificationListener l,
					FaultToleranceVerificationEvent event) {
				l.faultToleranceVerificationsFinished(event);
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
		monitor.subTask(Message.CHECKING_PREREQUISITES.format());
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
		group.beginTask(Message.CHECKING_PREREQUISITES.format(), 2);

		DivergenceFreeVerificationJob dfj = new DivergenceFreeVerificationJob(
				faultToleranceResults.getProcessName());
		SemifairnessVerificationJob sj = new SemifairnessVerificationJob(
				faultToleranceResults.getProcessName());

		dfj.setProgressGroup(group, dfj.getTotalUnitsOfWork());
		sj.setProgressGroup(group, sj.getTotalUnitsOfWork());

		dfj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingResult mcr = ((ModelCheckingJob) event.getJob())
						.getModelCheckingResult();
				FaultToleranceVerificationEvent ftEvent = new FaultToleranceVerificationEvent(
						faultToleranceResults, mcr.isSuccess());
				faultToleranceResults.setDivergenceFree(mcr.isSuccess());
				faultToleranceResults.incrementVerification();
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
				faultToleranceResults.incrementVerification();
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
