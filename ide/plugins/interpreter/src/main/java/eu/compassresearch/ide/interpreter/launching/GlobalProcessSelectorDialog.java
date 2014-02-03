package eu.compassresearch.ide.interpreter.launching;

import java.util.List;
import java.util.Vector;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.internal.navigator.NavigatorDecoratingLabelProvider;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.CmlUtil;

@SuppressWarnings("restriction")
public class GlobalProcessSelectorDialog
{
	public static AProcessDefinition chooseProcess(ICmlProject cmlProject,
			Shell shell)
	{
		ILabelProvider labelProvider = new NavigatorDecoratingLabelProvider(new WorkbenchLabelProvider());
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, labelProvider);
		dialog.setElements(CmlUtil.getGlobalProcessesFromSource(cmlProject.getModel().getDefinitions()).toArray());
		dialog.setTitle("Process Selection");
		dialog.setMessage("Select a process:");
		dialog.setMultipleSelection(false);

		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK)
		{
			if (dialog.getFirstResult() != null
					&& dialog.getFirstResult() instanceof AProcessDefinition)
			{
				return (AProcessDefinition) dialog.getFirstResult();
			}
		}
		return null;
	}

	public static List<AProcessDefinition> chooseProcesses(
			ICmlProject cmlProject, Shell shell,List<AProcessDefinition> checked)
	{
		ILabelProvider labelProvider = new NavigatorDecoratingLabelProvider(new WorkbenchLabelProvider());
		CheckedTreeSelectionDialog dialog = new CheckedTreeSelectionDialog(shell, labelProvider, new BaseWorkbenchContentProvider()
		{
			@Override
			public Object[] getElements(Object element)
			{
				if (element instanceof Object[])
				{
					return (Object[]) element;
				}
				return super.getElements(element);
			}

			@Override
			public boolean hasChildren(Object element)
			{
				return false;
			}

		});
		dialog.setInput(CmlUtil.getGlobalProcessesFromSource(cmlProject.getModel().getDefinitions()).toArray());
		dialog.setInitialElementSelections(checked);
		dialog.setTitle("Process Selection");
		dialog.setMessage("Select processes:");
		// dialog.setMultipleSelection(false);

		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK)
		{
			if (dialog.getResult() != null
					&& dialog.getResult() instanceof Object[])
			{
				List<AProcessDefinition> res = new Vector<AProcessDefinition>();
				for (Object object : dialog.getResult())
				{
					if(object instanceof AProcessDefinition)
					{
						res.add((AProcessDefinition) object);
					}
				}
				return res;
			}
		}
		return null;
	}
}
