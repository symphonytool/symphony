package eu.compassresearch.ide.cml.rttplugin;

import eu.compassResearch.rttMbtTmsClientApi.IRttMbtLoggingFacility;

public class RttMbtConsoleLogger implements IRttMbtLoggingFacility {

	org.eclipse.swt.widgets.Text console;
	
	@Override
	public void addLogMessage(String consoleName, String msg) {
		if (console != null) {
			console.append("[" + consoleName + "]: " + msg);
		} else {
			System.out.println("[" + consoleName + "]: " + msg);
		}
		console.redraw();
		console.update();
	}

	public void addErrorMessage(String consoleName, String msg) {
		if (console != null) {
			console.insert("[" + consoleName + "]: " + "*** error: " + msg);
		} else {
			System.err.println("[" + consoleName + "]: " + msg);
		}
		console.redraw();
		console.update();
	}

	public void setConsole(org.eclipse.swt.widgets.Text c) {
		console = c;
		System.out.println("console assigned to RttMbtConsoleLogger");
	}
	
	public org.eclipse.swt.widgets.Text getConsole() {
		return console;
	}
}
