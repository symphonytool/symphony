package eu.compassresearch.ide.plugins.interpreter.launch;

import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.plugins.interpreter.CmlUtil;
import eu.compassresearch.ide.ui.editor.syntax.OutlineLabelProvider;

public class GlobalProcessSelectorDialog
{
	private LabelProvider labelProvider = new OutlineLabelProvider();
	// new LabelProvider()
	// {
	// @Override
	// public String getText(Object element)
	// {
	//
	// if (element instanceof AProcessDefinition)
	// return ((AProcessDefinition) element).getName().getName();
	// else
	// return null;
	// }
	//
	// };
	private BaseWorkbenchContentProvider contentProvider = new BaseWorkbenchContentProvider()
	{
		@Override
		public boolean hasChildren(Object element)
		{
			if (element instanceof AProcessDefinition)
			{
				return false;
			} else
			{
				return super.hasChildren(element);
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Object[] getElements(Object element)
		{
			List pdefs = (List) element;
			return pdefs.toArray();

		}

	};
	private IVdmProject project;
	private Shell shell;

	public GlobalProcessSelectorDialog(Shell shell, IVdmProject project)
	{
		this.project = project;
		this.shell = shell;
	}

	public AProcessDefinition showDialog()
	{
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, labelProvider, contentProvider);
		dialog.setTitle("Process Selection");
		dialog.setMessage("Select a process:");
		dialog.setComparator(new ViewerComparator());

		if (VdmTypeCheckerUi.typeCheck(shell, project))
		{

			dialog.setInput(CmlUtil.GetGlobalProcessesFromSource(CmlUtil.getPSources(project.getModel())));

			if (dialog.open() == Window.OK)
			{
				if (dialog.getFirstResult() != null
						&& dialog.getFirstResult() instanceof AProcessDefinition)
				{
					return (AProcessDefinition) dialog.getFirstResult();
				}

			}
		}
		return null;
	}

}
