package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtAdvConfParser;

public class RttMbtGenerateTest extends RttMbtAbstractTestProcedureAction {

	@Override
	public String getTaskName() {
		return "Generate Test";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {
		IStatus success;

		// check for iterative test generation flag
		int IterTPGen = 0;
		RttMbtAdvConfParser advConf = new RttMbtAdvConfParser();
		if (advConf.readAdvancedConfig(selectedObjectFilesystemPath +
				                       File.separator + "conf" +
				                       File.separator + "advanced.conf")) {
			IterTPGen = advConf.getIterTPGen();
		}
		if (IterTPGen == 1) {
			int idx = 1;
			// if advConf contains IterTPGen = 1:
			// use prepareNextTestProcedureGeneration to check generated
			// goals and to prepare next generation context if necessary.
			String testProcBasename = selectedObjectName;
			String oldPostfix = "";
			String newPostfix = "_" + idx;
			// perform initial generation
			success = performSingleGeneration(monitor,testProcBasename + oldPostfix);
			while ((success == Status.OK_STATUS) && (prepareNextTestGeneration(monitor, testProcBasename, oldPostfix, newPostfix))) {
				oldPostfix = newPostfix;
				idx++;
				newPostfix = "_" + idx;
				success = performSingleGeneration(monitor,testProcBasename + oldPostfix);
			}
		} else {
			success = performSingleGeneration(monitor,selectedObjectName);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return success;
	}

	private IStatus performSingleGeneration(IProgressMonitor monitor,
			                                String testProcTGenCtx) {

		// if a test procedure is selected, switch to test procedure generation context
		if ((!isTProcGenCtxSelected()) && (isRttTestProcSelected())) {
			getTProcGenCtxPathFromRttTestProcPath();
		}

		// check that test procedure generation context is selected
		if (!isTProcGenCtxSelected()) {
			client.addErrorMessage("Please select a valid test procedure generation context!");
			return Status.CANCEL_STATUS;
		}

		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("generate test procedure " + testProcTGenCtx, 5);
		client.addLogMessage("generating test procedure " + testProcTGenCtx + "... please wait for the task to be finished.");

		// generate concrete test procedure
		if (client.generateTestProcedure(testProcTGenCtx)) {
			client.addLogMessage("[PASS]: generate test procedure " + testProcTGenCtx);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: generate test procedure " + testProcTGenCtx);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			status = Status.CANCEL_STATUS;
		}

		return status;
	}

	private Boolean prepareNextTestGeneration(IProgressMonitor monitor,
                                              String testProcBasename,
                                              String oldPostfix,
                                              String newPostfix) {

		// check if the current test procedure generation context exists
		File tgenCtx = new File(selectedObjectFilesystemPath + oldPostfix);
		if (!tgenCtx.isDirectory()) {
			client.addErrorMessage("test procedure generation context " + testProcBasename + oldPostfix + " does not exist - stopping generation");
			return false;
		}

		// start task
		client.beginTask("checking reached goals in test procedure generation context " + testProcBasename + oldPostfix, 5);
		client.addLogMessage("checking reached goals in test procedure generation context  " + testProcBasename + oldPostfix + "... please wait for the task to be finished.");

		//check reached goals
		if (client.prepareNextTestProcedureGeneration(testProcBasename, oldPostfix, newPostfix)) {
			client.addLogMessage("[PASS]: checking reached goals in test procedure generation context " + testProcBasename + oldPostfix);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: checking reached goals in test procedure generation context " + testProcBasename + oldPostfix);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		}

		// check if the new/next test procedure generation context exists
		tgenCtx = new File(selectedObjectFilesystemPath + newPostfix);
		if (!tgenCtx.isDirectory()) {
			client.addLogMessage("no remaining goals found or remaining goals cannot be covered - stopping generation.");
			return false;
		} else {
			return true;
		}
	}
}
