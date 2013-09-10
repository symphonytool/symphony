package eu.compassresearch.ide.rttmbt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class RttMbtConsoleView extends ViewPart {

	public RttMbtConsoleView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// create text field used for console output
		org.eclipse.swt.widgets.Text console = new Text(parent, SWT.V_SCROLL);

        // make console view accessable
        Activator.setConsole(console);

        // paint listener definition
        console.addPaintListener(new PaintListener()
          {
            @Override
            public void paintControl(PaintEvent e)
              {
              }
          });
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
