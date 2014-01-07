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
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.jobs, class DivergenceFreeJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class DivergenceFreeJob extends Job {

	public DivergenceFreeJob() {
		super(Messages.DIVERGENCE_FREE_JOB.getName());
	}

	@Override
	protected IStatus run(IProgressMonitor progressMonitor) {
		try {
			VerificationsManager.getDefault().getDivergenceLock().acquire();
			Thread.sleep(2000);
			return Status.OK_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		} finally {
			VerificationsManager.getDefault().getDivergenceLock().release();
		}
	}
}
