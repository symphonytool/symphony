package eu.compassresearch.ide.cml.rttplugin;

import eu.compassResearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtProgressBar implements IRttMbtProgressBar {
	private int percent = -1;

	org.eclipse.swt.widgets.ProgressBar progressBar;

	public void setProgress(int p) {
		percent = p;
		progressBar.setSelection(percent);
	}

	public int getProgress() {
		return percent;
	}

	public void setProgressBar(org.eclipse.swt.widgets.ProgressBar p) {
		progressBar = p;
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		percent = 0;
		progressBar.setSelection(percent);
		System.out.println("console assigned to RttMbtConsoleLogger");
	}
	
	public org.eclipse.swt.widgets.ProgressBar getProgressBar() {
		return progressBar;
	}
}
