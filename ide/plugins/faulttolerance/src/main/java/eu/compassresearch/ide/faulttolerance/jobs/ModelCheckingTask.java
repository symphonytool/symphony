/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.io.IOException;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.GraphBuilder;

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
			generateOutputFiles(formulaResult);
		} catch (Exception e) {
			results.setException(e);
			results.setSuccess(false);
		}
	}

	private void generateOutputFiles(FormulaResult formulaResult) {
		try {
			generateReachability(formulaResult);
			// generateDot(formulaResult);
		} catch (IOException e) {
			// TODO throw exception or define warning message.
			// couldn't generate reachability graph.
		}
	}

	private void generateReachability(FormulaResult formulaResult)
			throws IOException {
		if (formulaResult.isSatisfiable()) {
			GraphBuilder gb = new GraphBuilder();

			String rg = gb.generateDotRachabilityGraph(new StringBuilder(
					formulaResult.getFileContent()));
			System.out.println(rg);
		}
	}
}
