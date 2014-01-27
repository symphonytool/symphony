/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.modelchecker.ModelCheckerCaller;
import eu.compassresearch.ide.faulttolerance.modelchecker.ModelCheckingResult;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class SemifarinessJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class SemifairnessVerificationJob extends ModelCheckingJob {
	public SemifairnessVerificationJob(String processName,
			ICmlSourceUnit cmlSourceUnit) {
		super(Message.SEMIFAIRNESS_JOB, processName, 1);
		getModelCheckingResult().setCmlSourceUnit(cmlSourceUnit);
	}

	@Override
	protected void performModelCheckingCall(ModelCheckingResult result,
			ModelCheckerCaller caller, IProgressMonitor monitor)
			throws InterruptedException {
		try {
			String processName = Message.SEMIFAIRNESS_PROCESS_NAME
					.format(result.getProcessName());
			caller.verifyDivergenceFreedom(processName, result);
		} finally {
			monitor.worked(1);
		}
	}

}
