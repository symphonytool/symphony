/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.modelchecker.ModelCheckerCaller;
import eu.compassresearch.ide.faulttolerance.modelchecker.ModelCheckingResult;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class DivergenceFreeJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class DivergenceFreeVerificationJob extends ModelCheckingJob {

	public DivergenceFreeVerificationJob(String processName,
			ICmlSourceUnit cmlSourceUnit, IFolder folder) {
		super(Message.DIVERGENCE_FREE_JOB, processName, 1);
		getModelCheckingResult().setCmlSourceUnit(cmlSourceUnit);
		getModelCheckingResult().setFormulaScriptAbsolutePath(
				String.format("%s/%s", folder.getFullPath(),
						Message.DIVERGENCE_FREEDOM_FORMULA_SCRIPT_FILE_NAME
								.format(processName)));
	}

	@Override
	protected void performModelCheckingCall(ModelCheckingResult result,
			ModelCheckerCaller caller, IProgressMonitor monitor)
			throws InterruptedException {
		try {
			caller.runFormula(result);
		} finally {
			monitor.worked(1);
		}
	}
}
