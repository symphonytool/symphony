package eu.compassresearch.ide.pog.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.overture.ide.plugins.poviewer.view.PoTableView;

public class PoDetailView extends PoTableView
{

	public void clearPoView()
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				viewer.setText("");
			}
		});

	}
	
	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent)
	{
		viewer = new Text(parent, SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setFont(font);
	}

}
