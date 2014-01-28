/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.UnableToRunFaultToleranceVerificationException;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationJob extends Job {

	private final FaultToleranceVerificationResults faultToleranceResults;

	public FaultToleranceVerificationJob(
			FaultToleranceVerificationResults results) {
		super(Message.FAULT_TOLERANCE_JOB_NAME.format(results.getProcessName()));
		this.faultToleranceResults = results;
	}

	private void checkMonitor(IProgressMonitor monitor)
			throws InterruptedException {
		if (monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}

	private void verifyPreRequisites(final IProgressMonitor monitor)
			throws InterruptedException {

		try {
			// group.beginTask(Message.CHECKING_PREREQUISITES.format(), 2);

			ModelCheckingJob dfj = new ModelCheckingJob(
					Message.DIVERGENCE_FREE_JOB,
					faultToleranceResults.getProcessName());

			ModelCheckingJob sj = new ModelCheckingJob(
					Message.SEMIFAIRNESS_JOB,
					faultToleranceResults.getProcessName());

			dfj.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					ModelCheckingStatus status = ((ModelCheckingStatus) event
							.getResult());
					faultToleranceResults.setDivergenceFree(status.getResults()
							.isSuccess());
					monitor.worked(1);
				}
			});
			sj.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					ModelCheckingStatus status = ((ModelCheckingStatus) event
							.getResult());
					faultToleranceResults.setSemifair(status.getResults()
							.isSuccess());
					monitor.worked(1);
				}
			});

			dfj.schedule();
			sj.schedule();

			dfj.join();
			sj.join();
		} finally {
			faultToleranceResults.setPrerequisitesChecked(true);
		}
	}

	private boolean manageFiles(IProgressMonitor monitor)
			throws InterruptedException {

		FilesManagementJob fmj = new FilesManagementJob(
				faultToleranceResults.getProcessName(),
				faultToleranceResults.getCmlSourceUnit(),
				faultToleranceResults.getOutputContainer());

		fmj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				FilesManagementJob job = (FilesManagementJob) event.getJob();
				UnableToRunFaultToleranceVerificationException e = job
						.getException();
				faultToleranceResults.setException(e);
				faultToleranceResults.setFolder(job.getFolder());
			}
		});

		fmj.schedule();
		fmj.join();
		monitor.worked(1);
		return faultToleranceResults.getException() == null;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			monitor.beginTask(Message.FAULT_TOLERANCE_VERIFICATION_TASK_MESSAGE
					.format(faultToleranceResults.getProcessName()), 5);

			manageFiles(monitor);
			checkMonitor(monitor);
			verifyPreRequisites(monitor);
			checkMonitor(monitor);

			if (faultToleranceResults.isPreRequisitesOk()) {
				runFaultToleranceVerification(monitor);
			}
			return new FaultToleranceVerificationStatus(faultToleranceResults);
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		} finally {
			monitor.done();
		}
	}

	private void runFaultToleranceVerification(final IProgressMonitor monitor) {
		final ModelCheckingJob fftj = new ModelCheckingJob(
				Message.FULL_FAULT_TOLERANCE_JOB,
				faultToleranceResults.getProcessName());
		final ModelCheckingJob lftj = new ModelCheckingJob(
				Message.LIMITED_FAULT_TOLERANCE_JOB,
				faultToleranceResults.getProcessName());

		fftj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingStatus status = ((ModelCheckingStatus) event
						.getResult());
				faultToleranceResults.setFullFaultTolerant(status.getResults()
						.isSuccess());
				monitor.worked(1);
			}
		});

		lftj.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingStatus status = ((ModelCheckingStatus) event
						.getResult());
				faultToleranceResults.setLimitedFaultTolerant(status
						.getResults().isSuccess());
				monitor.worked(1);
			}

		});

		fftj.schedule();
		lftj.schedule();

		try {
			lftj.join();
			fftj.join();
		} catch (InterruptedException e) {
			// Interrupted...
		}

	}
}
