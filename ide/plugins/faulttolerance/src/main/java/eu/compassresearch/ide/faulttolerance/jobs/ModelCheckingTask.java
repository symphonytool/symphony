/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;

/**
 * @author Andr&eacute; Didier &lt;<a
 *         href="mailto:andredidier@gmail.com">andredidier@gmail.com</a>&gt;
 * 
 */
public class ModelCheckingTask implements Runnable {
	private final String processName;
	private final String absolutePath;
	private final IModelCheckingTaskListener listener;

	public ModelCheckingTask(String processName, String absolutePath,
			IModelCheckingTaskListener listener) {
		this.processName = processName;
		this.absolutePath = absolutePath;
		this.listener = listener;
	}

	@Override
	public void run() {
		ModelCheckingResult results = new ModelCheckingResult();
		try {
			results.setProcessName(processName);
			results.setFormulaScriptAbsolutePath(absolutePath);
			performModelCheckingCall(results);
		} finally {
			listener.done(results);
		}
	}

	private void performModelCheckingCall(ModelCheckingResult results) {
		try {
			FormulaResult formulaResult = FormulaIntegrator.getInstance()
					.analyseFile(absolutePath);
			results.setSuccess(!formulaResult.isSatisfiable());
			results.setFormulaResult(formulaResult);
		} catch (Exception e) {
			results.setException(e);
			results.setSuccess(false);
		}
	}
}
