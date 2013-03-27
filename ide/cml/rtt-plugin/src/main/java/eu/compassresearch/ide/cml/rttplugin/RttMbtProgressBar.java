package eu.compassresearch.ide.cml.rttplugin;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import eu.compassResearch.rttMbtTmsClientApi.IRttMbtProgressBar;

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
		final ProgressBar bar = getProgressBar(task);
		final int perc = percent;
		final String line = "[" + task.toString() + "]:" + percent + "%";
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (bar != null) {
					bar.setSelection(perc);
				} else {
					System.out.println(line);
				}
			}
		});
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
		final ProgressBar global = getProgressBar(IRttMbtProgressBar.Tasks.Global);
		final ProgressBar goal = getProgressBar(IRttMbtProgressBar.Tasks.TC_COV);
		final ProgressBar tc = getProgressBar(IRttMbtProgressBar.Tasks.TC_COV);
		final ProgressBar tr = getProgressBar(IRttMbtProgressBar.Tasks.TR_COV);
		final ProgressBar bcs = getProgressBar(IRttMbtProgressBar.Tasks.BCS_COV);
		final ProgressBar mcdc = getProgressBar(IRttMbtProgressBar.Tasks.MCDC_COV);

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (global != null) {
					global.setSelection(0);
				}		
				if (goal != null) {
					goal.setSelection(0);
				}		
				if (tc != null) {
					tc.setSelection(0);
				}		
				if (tr != null) {
					tr.setSelection(0);
				}		
				if (bcs != null) {
					bcs.setSelection(0);
				}		
				if (mcdc != null) {
					mcdc.setSelection(0);
				}						
			}
		});
	}
}
