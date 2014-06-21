package eu.compassresearch.ide.pog.view;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.plugins.poviewer.view.PoTableView;
import org.overture.pog.pub.IProofObligation;

import eu.compassresearch.ide.pog.Activator;
import eu.compassresearch.ide.pog.PogPluginPrefConstants;

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
	
	@Override
	public void setDataList(final IVdmProject project,
			final IProofObligation data)
	{
		display.asyncExec(new Runnable()
		{

			public void run()
			{
				String choice = Activator.getDefault().getPreferenceStore().getString(PogPluginPrefConstants.POG_VIEW_CHOICE);
				
				StringBuilder sb = new StringBuilder();
				sb.append("-- ");
				sb.append(data.getName());
				sb.append("\n");
				viewer.setText(data.getFullPredString());
				
				if (choice == null)
				{
					MessageDialog.openError(display.getActiveShell(), "Error", "Error with PO detail level. Check preferences.");
				}
				if (choice.equals(PogPluginPrefConstants.CTXT_ETC))
				{
					sb.append((data.getFullPredString()));
				}
				if (choice.equals(PogPluginPrefConstants.PRED_ONLY))
				{
					sb.append(data.getDefPredString());
				}
				MessageDialog.openError(display.getActiveShell(), "Error", "Error with PO detail level. Check preferences.");
				viewer.setText(sb.toString());
				return;
			}

		});
	}

}
