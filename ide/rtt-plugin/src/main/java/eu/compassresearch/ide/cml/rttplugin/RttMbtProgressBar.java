package eu.compassresearch.ide.cml.rttplugin;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.widgets.ProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtProgressBar implements IRttMbtProgressBar {
	Map<IRttMbtProgressBar.Tasks, ProgressBar> progressBars;
	
	public RttMbtProgressBar() {
		progressBars = new HashMap<IRttMbtProgressBar.Tasks, ProgressBar>();
	}

	public void setProgress(IRttMbtProgressBar.Tasks task, int percent) {
		if (task == IRttMbtProgressBar.Tasks.ALL) {
			resetAllProgressBars();
			return;
		}
		ProgressBar bar = getProgressBar(task);
		if (bar != null) {
			bar.setSelection(percent);
		} else {
			System.out.println("[" + task.toString() + "]:" + percent + "%");
		}
	}

	public int getProgress(IRttMbtProgressBar.Tasks task) {
		ProgressBar bar = getProgressBar(task);
		if (bar != null) {
			return bar.getSelection();
		}
		return -1;
	}

	public void addProgressBar(IRttMbtProgressBar.Tasks task, ProgressBar bar) {
		progressBars.put(task, bar);
		bar.setMaximum(100);
		bar.setMinimum(0);
		bar.setSelection(0);
		System.out.println("progress bar '" + task + "' assigned to RttMbtProgressBar");
	}

	public ProgressBar getProgressBar(IRttMbtProgressBar.Tasks task) {
		return progressBars.get(task);
	}
	
	public void resetAllProgressBars() {
		ProgressBar bar = getProgressBar(IRttMbtProgressBar.Tasks.Global);
		if (bar != null) {
			bar.setSelection(0);
		}		
		bar = getProgressBar(IRttMbtProgressBar.Tasks.Goal);
		if (bar != null) {
			bar.setSelection(0);
		}		
		bar = getProgressBar(IRttMbtProgressBar.Tasks.TC_COV);
		if (bar != null) {
			bar.setSelection(0);
		}		
		bar = getProgressBar(IRttMbtProgressBar.Tasks.TR_COV);
		if (bar != null) {
			bar.setSelection(0);
		}		
		bar = getProgressBar(IRttMbtProgressBar.Tasks.BCS_COV);
		if (bar != null) {
			bar.setSelection(0);
		}		
		bar = getProgressBar(IRttMbtProgressBar.Tasks.MCDC_COV);
		if (bar != null) {
			bar.setSelection(0);
		}		
	}
}
