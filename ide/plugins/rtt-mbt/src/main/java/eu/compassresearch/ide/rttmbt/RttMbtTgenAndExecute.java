package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtTgenAndExecute extends RttMbtAbstractTestProcedureAction {

	@Override
	public String getTaskName() {
		return "Generate and Execute Test";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

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
		client.beginTask("generate and execute test procedure " + selectedObjectName, 34);


		/**
		 * clean test procedure generation context
		 */
		client.addLogMessage("cleaning up test procedure generation context " + selectedObjectName + "... please wait for the task to be finished.");
		// cleanup concrete test procedure
		if (client.cleanTestProcedureGenerationContext(selectedObjectName)) {
			client.addLogMessage("[PASS]: cleanup test procedure generation context " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: cleanup test procedure generation context " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.addCompletedTaskItems(31);
			return Status.CANCEL_STATUS;
		}
		client.addCompletedTaskItems(1);


		/**
		 * generate test procedure
		 */
		client.addLogMessage("generating test procedure " + selectedObjectName + "... please wait for the task to be finished.");
		// generate concrete test procedure
		if (client.generateTestProcedure(selectedObjectName)) {
			client.addLogMessage("[PASS]: generate test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: generate test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.addCompletedTaskItems(26);
			return Status.CANCEL_STATUS;
		}
		client.addCompletedTaskItems(1);


		/**
		 * switch from generation context to execution context
		 */
		// if a test procedure generation context is selected, switch to test procedure
		if ((!isRttTestProcSelected()) && (isTProcGenCtxSelected())) {
			getRttTestProcPathFromTProcGenCtxPath();
		}
		// check that a test procedure is selected
		if ((!isRttTestProcSelected()) && (!RttMbtClient.isRtt6TestProcedure(selectedObjectFilesystemPath))) {
			client.addErrorMessage("Please select a valid test procedure!");
			client.addCompletedTaskItems(20);
			return Status.CANCEL_STATUS;
		}


		/**
		 * compile test procedure
		 */

		String rttTestprocPath = client.getPathInsideRttTestcontext(selectedObjectFilesystemPath);
		client.addLogMessage("cleanup test procedure " + rttTestprocPath + "... please wait for the task to be finished.");
		if (client.cleanTestProcedure(rttTestprocPath)) {
			client.addLogMessage("[PASS]: clean up test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: clean up test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.addCompletedTaskItems(21);
			return Status.CANCEL_STATUS;
		}
		client.addCompletedTaskItems(1);


		/**
		 * compile test procedure
		 */
		// compile test procedure
		client.addLogMessage("compiling test procedure " + rttTestprocPath + "... please wait for the task to be finished.");
		if (client.compileTestProcedure(rttTestprocPath)) {
			client.addLogMessage("[PASS]: compile test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: compile test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.addCompletedTaskItems(16);
			return Status.CANCEL_STATUS;
		}
		client.addCompletedTaskItems(1);


		/**
		 * run test procedure
		 */
		// run test procedure
		client.addLogMessage("executing test procedure " + rttTestprocPath + "... please wait for the task to be finished.");
		if (client.runTestProcedure(rttTestprocPath)) {
			client.addLogMessage("[PASS]: execute test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: execute test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.addCompletedTaskItems(11);
			return Status.CANCEL_STATUS;
		}
		client.addCompletedTaskItems(1);

		
		/**
		 * doc test procedure
		 */
		// generate test procedure documentation
		client.addLogMessage("generating documentation for " + rttTestprocPath + "... please wait for the task to be finished.");
		if (client.docTestProcedure(rttTestprocPath)) {
			client.addLogMessage("[PASS]: generate test procedure documentation for " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: generate test procedure documentation for " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.addCompletedTaskItems(6);
			return Status.CANCEL_STATUS;
		}
		client.addCompletedTaskItems(1);


		/**
		 * switch from execution context to generation context
		 */
		// if a test procedure is selected, switch to test procedure generation context
		if ((!isTProcGenCtxSelected()) && (isRttTestProcSelected())) {
			getTProcGenCtxPathFromRttTestProcPath();
		}
		// check that test procedure generation context is selected
		if (!isTProcGenCtxSelected()) {
			client.addErrorMessage("Please select a valid test procedure generation context!");
			client.addCompletedTaskItems(5);
			return Status.CANCEL_STATUS;
		}


		/**
		 * replay test procedure
		 */
		client.addLogMessage("replaying test results from test procedure " + selectedObjectName + "... please wait for the task to be finished.");
		// replay test procedure
		if (client.replayTestProcedure(selectedObjectName)) {
			client.addLogMessage("[PASS]: replay test procedure " + selectedObjectName);
		} else {
			client.addErrorMessage("[FAIL]: replay test procedure " + selectedObjectName);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
