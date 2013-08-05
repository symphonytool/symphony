package eu.compassresearch.ide.rttmbt;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtLoggingFacility;

public class RttMbtConsoleLogger implements IRttMbtLoggingFacility {

	org.eclipse.swt.widgets.Text console;

	// Get UISynchronize injected as field

	@Override
	public void addLogMessage(String consoleName, String msg) {
		final String line = "[" + consoleName + "]: " + msg;
		Display.getDefault().syncExec(new Runnable() {
		      @Override
		      public void run() {
		  		if (console != null) {
					console.append(line);
				} else {
					System.out.println(line);
				}
				console.redraw();
				console.update();
		      }
	    });
	}

	public void addErrorMessage(String consoleName, String msg) {
		final String line = "[" + consoleName + "]: " + "*** error: " + msg;
		Display.getDefault().syncExec(new Runnable() {
		      @Override
		      public void run() {
		  		if (console != null) {
					console.insert(line);
				} else {
					System.err.println(line);
				}
				console.redraw();
				console.update();
		      }
		});
	}

	public void setConsole(org.eclipse.swt.widgets.Text c) {
		console = c;
		System.out.println("console assigned to RttMbtConsoleLogger");
	}
	
	public org.eclipse.swt.widgets.Text getConsole() {
		return console;
	}
}
