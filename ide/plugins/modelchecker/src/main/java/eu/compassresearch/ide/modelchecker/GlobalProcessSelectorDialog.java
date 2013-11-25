package eu.compassresearch.ide.modelchecker;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.overture.ast.definitions.PDefinition;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.internal.viewsupport.DecorationgVdmLabelProvider;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.ui.navigator.CmlUiLabelProvider;

public class GlobalProcessSelectorDialog{
	private DecorationgVdmLabelProvider labelProvider = new DecorationgVdmLabelProvider(new CmlUiLabelProvider());
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
	private ICmlSourceUnit selectedCmlUnit;

	public GlobalProcessSelectorDialog(Shell shell, IVdmProject project, ICmlSourceUnit selectedCmlUnit)
	{
		this.project = project;
		this.shell = shell;
		this.selectedCmlUnit = selectedCmlUnit;
	}

	public AProcessDefinition showDialog(){
		AProcessDefinition result = null;
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, labelProvider, contentProvider);
		dialog.setTitle("Process Selection");
		dialog.setMessage("Select a process:");
		dialog.setComparator(new ViewerComparator());

		if (VdmTypeCheckerUi.typeCheck(shell, project)){
			//ICmlProject cp = (ICmlProject) project.getAdapter(ICmlProject.class);
			//dialog.setInput(getGlobalProcessesFromSource(cp.getModel().getDefinitions()));
			dialog.setInput(getGlobalProcessesFromSource(selectedCmlUnit.getParseListDefinitions()));

			if (dialog.open() == Window.OK){
				if (dialog.getFirstResult() != null
						&& dialog.getFirstResult() instanceof AProcessDefinition){
					result = (AProcessDefinition) dialog.getFirstResult();
				}

			}
		}
		return result;
	}
	
	private List<AProcessDefinition> getGlobalProcessesFromSource(
			List<PDefinition> projectSources){
		
		List<AProcessDefinition> result = new LinkedList<AProcessDefinition>(); 
		
		if (this.hasMultipleProcessDefinitions(projectSources)) {
			for (PDefinition pDefinition : projectSources) {
				if(pDefinition instanceof AProcessDefinition){
					result.add((AProcessDefinition) pDefinition);
				}
			}
		} 
		return result;

	}
	private boolean hasMultipleProcessDefinitions(List<PDefinition> projectSources){
		boolean result = false;
		int i = 0;
		for (PDefinition pDefinition : projectSources) {
			if(pDefinition instanceof AProcessDefinition){
				i++;
				if(i > 1){
					result = true;
					break;
				}
			}
		}
		return result;
	}

}
