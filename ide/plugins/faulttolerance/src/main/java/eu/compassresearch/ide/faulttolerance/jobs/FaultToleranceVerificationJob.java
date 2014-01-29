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

	private String getAbsolutePath(Message fileNameMessage) {
		return String.format("%s/%s", faultToleranceResults.getFolder()
				.getLocationURI(), fileNameMessage.format(faultToleranceResults
				.getProcessName()));
	}

	private void verifyPreRequisites(final IProgressMonitor monitor)
			throws InterruptedException {

		try {
			// group.beginTask(Message.CHECKING_PREREQUISITES.format(), 2);

			ModelCheckingJob dfj = new ModelCheckingJob(
					Message.DIVERGENCE_FREE_JOB,
					faultToleranceResults.getProcessName(),
					getAbsolutePath(Message.DIVERGENCE_FREEDOM_FORMULA_SCRIPT_FILE_NAME));

			ModelCheckingJob sj = new ModelCheckingJob(
					Message.SEMIFAIRNESS_JOB,
					faultToleranceResults.getProcessName(),
					getAbsolutePath(Message.SEMIFAIRNESS_FORMULA_SCRIPT_FILE_NAME));

			dfj.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					if (event.getResult() instanceof ModelCheckingStatus) {
						ModelCheckingStatus status = ((ModelCheckingStatus) event
								.getResult());
						faultToleranceResults.setDivergenceFree(status
								.getResults().isSuccess());
						faultToleranceResults.add(status.getResults()
								.getException());
					}
					monitor.worked(1);
					if (!event.getJob().getResult().isOK()) {
						monitor.setCanceled(true);
					}
				}
			});
			sj.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					ModelCheckingStatus status = ((ModelCheckingStatus) event
							.getResult());
					faultToleranceResults.setSemifair(status.getResults()
							.isSuccess());
					faultToleranceResults.add(status.getResults()
							.getException());
					monitor.worked(1);
					if (!event.getJob().getResult().isOK()) {
						monitor.setCanceled(true);
					}
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

	private void manageFiles(final IProgressMonitor monitor)
			throws InterruptedException {

		FilesManagementJob fmj = new FilesManagementJob(
				faultToleranceResults.getProcessName(),
				faultToleranceResults.getCmlSourceUnit(),
				faultToleranceResults.getOutputContainer());

		fmj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getJob() instanceof FilesManagementJob) {
					FilesManagementJob job = (FilesManagementJob) event
							.getJob();
					UnableToRunFaultToleranceVerificationException e = job
							.getException();
					faultToleranceResults.setException(e);
					faultToleranceResults.setFolder(job.getFolder());
				}
				monitor.worked(1);
				if (!event.getJob().getResult().isOK()) {
					monitor.setCanceled(true);
				}
			}
		});

		fmj.schedule();
		fmj.join();
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
				faultToleranceResults.getProcessName(),
				getAbsolutePath(Message.FULL_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME));
		final ModelCheckingJob lftj = new ModelCheckingJob(
				Message.LIMITED_FAULT_TOLERANCE_JOB,
				faultToleranceResults.getProcessName(),
				getAbsolutePath(Message.LIMITED_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME));

		fftj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingStatus status = ((ModelCheckingStatus) event
						.getResult());
				faultToleranceResults.setFullFaultTolerant(status.getResults()
						.isSuccess());
				faultToleranceResults.add(status.getResults().getException());
				monitor.worked(1);
				if (!event.getJob().getResult().isOK()) {
					monitor.setCanceled(true);
				}
			}
		});

		lftj.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingStatus status = ((ModelCheckingStatus) event
						.getResult());
				faultToleranceResults.setLimitedFaultTolerant(status
						.getResults().isSuccess());
				faultToleranceResults.add(status.getResults().getException());
				monitor.worked(1);
				if (!event.getJob().getResult().isOK()) {
					monitor.setCanceled(true);
				}
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
