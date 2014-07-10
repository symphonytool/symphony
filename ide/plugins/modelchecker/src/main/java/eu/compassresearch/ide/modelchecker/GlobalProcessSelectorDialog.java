package eu.compassresearch.ide.modelchecker;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.internal.navigator.NavigatorDecoratingLabelProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.resources.ICmlProject;

public class GlobalProcessSelectorDialog{
	
	@SuppressWarnings("restriction")
	public static AProcessDefinition chooseProcess(ICmlProject cmlProject,Shell shell){
		
		ILabelProvider labelProvider = new NavigatorDecoratingLabelProvider(new WorkbenchLabelProvider());
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, labelProvider);
		dialog.setElements(MCPluginUtility.getGlobalProcessesFromSource(cmlProject.getModel().getDefinitions()).toArray());
		dialog.setTitle("Process Selection");
		dialog.setMessage("Select a process:");
		dialog.setMultipleSelection(false);

		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK){
			if (dialog.getFirstResult() != null
					&& dialog.getFirstResult() instanceof AProcessDefinition){
				return (AProcessDefinition) dialog.getFirstResult();
			}
		}
		return null;
	}
}
