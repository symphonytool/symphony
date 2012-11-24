package eu.compassresearch.ide.cml.rttplugin;

import eu.compassResearch.rttMbtTmsClientApi.IRttMbtLoggingFacility;

public class RttMbtConsoleLogger implements IRttMbtLoggingFacility {

	org.eclipse.swt.widgets.Text console;
	
	@Override
	public void addLogMessage(String msg) {
		if (console != null) {
			console.insert(msg);
		} else {
			System.out.println(msg);
		}
	}

	public void addErrorMessage(String msg) {
		if (console != null) {
			console.insert(msg);
		} else {
			System.err.println(msg);
		}
	}

	public void setConsole(org.eclipse.swt.widgets.Text c) {
		console = c;
		System.out.println("console assigned to RttMbtConsoleLogger");
	}
	
	public org.eclipse.swt.widgets.Text getConsole() {
		return console;
	}
}
