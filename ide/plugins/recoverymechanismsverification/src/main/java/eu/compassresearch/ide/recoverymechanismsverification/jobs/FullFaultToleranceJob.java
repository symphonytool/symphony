/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.ide.recoverymechanismsverification.Messages;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.jobs, class FullFaultToleranceJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FullFaultToleranceJob extends Job {

	public FullFaultToleranceJob() {
		super(Messages.FULL_FAULT_TOLERANCE_JOB.getName());
	}

	@Override
	protected IStatus run(IProgressMonitor progressMonitor) {
		try {
			Thread.sleep(5000);
			return Status.OK_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
	}

}
