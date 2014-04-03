package eu.compassresearch.ide.rttmbt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtLoggingFacility;

public class RttMbtConsoleLogger implements IRttMbtLoggingFacility {

	org.eclipse.swt.widgets.Text console;

	Font font = new Font(Display.getDefault(), "Courier", 10, SWT.BOLD);
	
	@Override
	public void addLogMessage(String consoleName, String msg) {
		final String line = "[" + consoleName + "]: " + msg;
		Display.getDefault().syncExec(new Runnable() {
		      @Override
		      public void run() {
		  		if (console != null) {
		  			console.setFont(font);
					console.append(line);
					console.redraw();
					console.update();
				} else {
					System.out.println(line);
				}
		      }
	    });
	}

	public void addErrorMessage(String consoleName, String msg) {
		final String line = "[" + consoleName + "]: " + "*** error: " + msg;
		Display.getDefault().syncExec(new Runnable() {
		      @Override
		      public void run() {
		  		if (console != null) {
		  			console.setFont(font);
					console.insert(line);
					console.redraw();
					console.update();
				} else {
					System.err.println(line);
				}
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
