package eu.compassresearch.ide.modelchecker;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.internal.navigator.NavigatorDecoratingLabelProvider;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.overture.ast.definitions.PDefinition;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.internal.viewsupport.DecorationgVdmLabelProvider;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.ui.navigator.CmlUiLabelProvider;

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
