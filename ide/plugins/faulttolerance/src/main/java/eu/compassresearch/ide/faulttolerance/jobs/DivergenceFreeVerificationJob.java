/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.util.Random;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.compassresearch.ide.faulttolerance.Messages;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class DivergenceFreeJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class DivergenceFreeVerificationJob extends ModelCheckingJob {

	public DivergenceFreeVerificationJob() {
		super(Messages.DIVERGENCE_FREE_JOB, 1);
	}

	@Override
	protected void performModelCheckingCall(ModelCheckingResult result,
			IProgressMonitor monitor) throws InterruptedException {
		try {
			Thread.sleep(5000);
			Random r = new Random(System.currentTimeMillis());
			result.setSuccess(r.nextInt(100) > 50);
		} finally {
			monitor.worked(1);
		}
	}
}
