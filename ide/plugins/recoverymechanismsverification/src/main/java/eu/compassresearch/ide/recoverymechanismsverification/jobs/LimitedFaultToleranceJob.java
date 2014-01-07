/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import eu.compassresearch.ide.recoverymechanismsverification.Messages;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.jobs, class LimitedFaultTolerance"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class LimitedFaultToleranceJob extends Job {
	private final Shell shell;

	public LimitedFaultToleranceJob(Composite parent) {
		super(Messages.LIMITED_FAULT_TOLERANCE_JOB.getName());
		this.shell = parent.getShell();
	}

	@Override
	protected IStatus run(IProgressMonitor progressMonitor) {
		try {
			Thread.sleep(4000);
			/*
			 * if (((Boolean)
			 * getProperty(IProgressConstants.PROPERTY_IN_DIALOG))
			 * .booleanValue()) { setProperty(IProgressConstants.KEEP_PROPERTY,
			 * Boolean.TRUE); setProperty(IProgressConstants.ACTION_PROPERTY,
			 * getLimitedFaultToleranceCompleteAction()); }
			 */
			return Status.OK_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
	}

	private Action getLimitedFaultToleranceCompleteAction() {
		return new Action(
				Messages.LIMITED_FAULT_TOLERANCE_VERIFICATION_COMPLETED
						.getName()) {
			@Override
			public void run() {
				MessageDialog.openInformation(shell,
						Messages.LIMITED_FAULT_TOLERANCE_VERIFICATION_COMPLETED
								.getTitle(),
						Messages.LIMITED_FAULT_TOLERANCE_VERIFICATION_COMPLETED
								.getText());
			}
		};
	}
}
