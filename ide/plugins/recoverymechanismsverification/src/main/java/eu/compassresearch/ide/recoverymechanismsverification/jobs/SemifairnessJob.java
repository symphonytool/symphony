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
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.jobs, class SemifarinessJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class SemifairnessJob extends Job {
	public SemifairnessJob() {
		super(Messages.SEMIFAIRNESS_JOB.getName());
	}

	@Override
	protected IStatus run(IProgressMonitor progressMonitor) {
		try {
			Thread.sleep(2000);
			return Status.OK_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
	}

}
