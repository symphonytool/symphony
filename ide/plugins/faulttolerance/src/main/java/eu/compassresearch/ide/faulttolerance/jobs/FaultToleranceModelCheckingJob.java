/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.IProgressConstants;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.ide.faulttolerance.FaultToleranceProperty;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceModelCheckingJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceModelCheckingJob extends
		FaultToleranceVerificationJobBase {

	private final FaultToleranceProperty property;

	public FaultToleranceModelCheckingJob(FaultToleranceProperty property,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(Message.MODEL_CHECKING_JOB_NAME.format(request.getSystemName(),
				property.getType().formattedName()), request, response);
		this.property = property;
		add(new IFaultToleranceVerificationPreRequisite() {
			@Override
			public boolean checkPreRequisite() {
				return FaultToleranceModelCheckingJob.this.property
						.getFormulaScriptFile().exists();
			}
		});
		addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().getSeverity() == IStatus.CANCEL) {
					if (!FaultToleranceModelCheckingJob.this.property
							.isCanceledByPreRequisite()) {
						FaultToleranceModelCheckingJob.this.property
								.setCanceledByUser(true);
					}
				}
			}
		});

		setProperty(IProgressConstants.ACTION_PROPERTY,
				createAction(property, request, response));
		setProperty(IProgressConstants.KEEP_PROPERTY, true);
	}

	private Action createAction(final FaultToleranceProperty property,
			final IFaultToleranceVerificationRequest request,
			final IFaultToleranceVerificationResponse response) {
		return new Action() {
			@Override
			public void run() {
				if (property.getException() != null) {
					MessageDialog.openError(new Shell(), getName(),
							Message.EXCEPTION_OCCURRED.format(request
									.getSystemName(), property.getType()
									.formattedName(), property.getException()
									.getLocalizedMessage()));
				} else if (property.isChecked()) {
					MessageDialog.openInformation(new Shell(), getName(),
							Message.PROPERTY_CHECKED.format(request
									.getSystemName(), property.getType()
									.formattedName(), property.isSatisfied(),
									property.getElapsedTime()));
				} else if (getState() == Job.RUNNING
						|| getState() == Job.WAITING) {
					MessageDialog.openInformation(new Shell(), getName(),
							Message.PROPERTY_NOT_YET_CHECKED.format(request
									.getSystemName(), property.getType()
									.formattedName()));
				}
			}
		};
	}

	@Override
	protected IStatus runInWorkspace(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			final ILock formulaLock = getJobManager().newLock();
			String taskName = Message.MODEL_CHECKING_PROPERTY_TASK_NAME.format(
					request.getSystemName(), property.format());
			monitor.beginTask(taskName, 1);
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						formulaLock.acquire();
						String fullPath = property.getFormulaScriptFile()
								.getRawLocation().toOSString();
						FormulaResult formulaResult = FormulaIntegrator
								.getInstance().analyseFile(fullPath);
						synchronized (property) {
							property.setChecked(true);
							property.setSatisfied(!formulaResult
									.isSatisfiable());
							property.setElapsedTime(formulaResult
									.getElapsedTimeKnows()
									+ formulaResult.getElapsedTimeLoad()
									+ formulaResult.getElapsedTimeSolve());
						}
					} catch (RuntimeException e) {
						property.setException(e);
					} finally {
						formulaLock.release();
					}
				}
			};

			Thread t = new Thread(r, taskName);
			t.start();

			boolean done = false;
			while (!done) {
				try {
					formulaLock.acquire(4000);
					synchronized (property) {
						if (monitor.isCanceled()) {
							done = true;
							t.interrupt();
							FormulaIntegrator.getInstance().finalize();
						} else if (property.isChecked()) {
							done = true;
						}
					}
				} catch (InterruptedException e) {
					// TODO log exception
					done = true;
				} catch (Throwable e) {
					// TODO log exception from FormulaIntegrator
				} finally {
					formulaLock.release();
				}
			}

			monitor.worked(1);
			IStatus status = Status.OK_STATUS;
			if (monitor.isCanceled()) {
				property.setCanceledByUser(true);
				status = Status.CANCEL_STATUS;
			}
			return status;
		} catch (RuntimeException e) {
			property.setException(e);
			return Status.OK_STATUS;
		} finally {
			monitor.done();
		}
	}

	@Override
	protected ISchedulingRule updateSchedulingRules(ISchedulingRule baseRule,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		ISchedulingRule combinedRule = baseRule;

		combinedRule = MultiRule.combine(property.getFormulaScriptFile(),
				combinedRule);
		combinedRule = MultiRule.combine(new ModelCheckerSchedulingRule(),
				combinedRule);

		return combinedRule;
	}

	@Override
	protected void canceling() {
		property.setCanceledByUser(true);
	}

	@Override
	protected void handleShouldRun(boolean should) {
		property.setCanceledByPreRequisite(!should);
	}

}
