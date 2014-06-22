package eu.compassresearch.ide.pog.view;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.plugins.poviewer.view.PoTableView;
import org.overture.pog.pub.IProofObligation;

import eu.compassresearch.ide.pog.Activator;
import eu.compassresearch.ide.pog.PogPluginPrefConstants;

public class PoDetailView extends PoTableView
{

	StyledText styledTest;

	public void clearPoView()
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				styledTest.setText("");
			}
		});

	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent)
	{
		// viewer = new Text(parent, SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
		// viewer.setFont(font);
		// this.setLayout(new FillLayout());
		Color highlight = new Color(display, 127, 0, 85);
		Color comment = new Color(display, 63, 127, 95);
		styledTest = new StyledText(parent, SWT.WRAP | SWT.READ_ONLY);
		styledTest.addLineStyleListener(new PogLineSyleListener(highlight,comment));
		styledTest.setFont(font);
	}

	@Override
	public void setFocus()
	{
		styledTest.setFocus();
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

				String source = (data.getName().equals("") ? "GLOBAL"
						: data.getName());
				
				StringBuilder sb = new StringBuilder();
				sb.append("-- ");
				sb.append(data.getKindString());
				sb.append(" obligation - ");
				sb.append(source);
				sb.append("\n");
			
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
				// cheat on separators for now;
				styledTest.setText(sb.toString().replaceAll("&", "@"));

				return;
			}

		});
	}

}
