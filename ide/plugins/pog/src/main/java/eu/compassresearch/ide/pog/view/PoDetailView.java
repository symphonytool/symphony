package eu.compassresearch.ide.pog.view;

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

}
