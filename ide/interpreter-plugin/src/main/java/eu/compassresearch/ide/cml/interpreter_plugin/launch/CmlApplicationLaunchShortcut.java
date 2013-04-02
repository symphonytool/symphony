package eu.compassresearch.ide.cml.interpreter_plugin.launch;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.debug.CmlInterpreterLaunchConfigurationConstants;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlDebugConstants;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlUtil;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlApplicationLaunchShortcut implements ILaunchShortcut2
{

	@Override
	public void launch(ISelection selection, String mode) {
		
		if(selection instanceof TreeSelection)
		{
			TreeSelection treeSelection = (TreeSelection)selection;
			//find the associated CmlSourceUnit for this selected file. 
			searchAndLaunch(treeSelection.getFirstElement(),mode);
		}
		
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		System.out.println(editor.toString());		
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		
		if(selection instanceof TreeSelection)
		{
			TreeSelection treeSelection = (TreeSelection)selection;
			//find the associated CmlSourceUnit for this selected file. 
			IFile file = (IFile)treeSelection.getFirstElement();
			
			List<ILaunchConfiguration> foundConfs = findLaunchConfigurationsByFile(file);
			
			return foundConfs.toArray(new ILaunchConfiguration[foundConfs.size()]);
		}
		else 
			return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		return null;
	}
	
	@Override
	public IResource getLaunchableResource(ISelection selection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		return null;
	}

	/**
	 * Protected Methods
	 */
	protected void searchAndLaunch(Object file, String mode) {
		
		CmlSourceUnit cmlSourceUnit = CmlSourceUnit.getFromFileResource((IFile)file);
		PSource ast = cmlSourceUnit.getSourceAst();

		if(ast != null)
		{
			List<PSource> sourceList = new LinkedList<PSource>();
			sourceList.add(ast);
			List<AProcessDefinition> defsInFile = CmlUtil.GetGlobalProcessesFromSource(sourceList); 

			if(defsInFile.size() == 1)
			{
				String processName = defsInFile.get(0).getName().getName();
				launch((IFile)file,processName,mode);
			}
			else if(defsInFile.size() > 1)
			{

				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(null, new LabelProvider()
				{
					@Override
					public String getText(Object element) {

						if(element instanceof AProcessDefinition)
							return ((AProcessDefinition)element).getName().getName();
						else			
							return null;
					}

				}, new BaseWorkbenchContentProvider()
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

					@Override
					public Object[] getElements(Object element)
					{
						List<AProcessDefinition> pdefs = (List<AProcessDefinition>)element;
						return pdefs.toArray();


					}

				});
				dialog.setTitle("Process Selection");
				dialog.setMessage("Select a process:");
				dialog.setComparator(new ViewerComparator());

				dialog.setInput( defsInFile);

				if (dialog.open() == Window.OK)
				{
					if (dialog.getFirstResult() != null
							&& dialog.getFirstResult() instanceof AProcessDefinition)
						//&& ((IProject) dialog.getFirstResult()).getAdapter(IVdmProject.class) != null)
					{
						String processName = ((AProcessDefinition) dialog.getFirstResult()).getName().getName();
						launch((IFile)file,processName,mode);
					}
				}
			}
		}
		else
		//If no ast is attached then there are either parser or type errors
		{
			MessageDialog.openError(null, "Launch failure", "The Cml model is not loaded or contains error and therefore cannot be launched");
		}
	
	}

    protected void launch(IFile sourceUnit,String processName, String mode) {
        try {
            ILaunchConfiguration config = findLaunchConfiguration(sourceUnit,processName, mode);
            
            if (config != null) {
             config.launch(mode, null);
            }
        } catch (CoreException e) {
            /* Handle exceptions*/
        	e.printStackTrace();
        }
    }
    
    protected List<ILaunchConfiguration> findLaunchConfigurationsByFile(IFile file) 

    {
    	ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
    	ILaunchConfigurationType ctype = 
    			launchManager.getLaunchConfigurationType(CmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE.toString());
    	
    	
    	//Get the current project which this file lives in
    	
    	List<ILaunchConfiguration> result = new LinkedList<ILaunchConfiguration>();
    	
    	try {
			for(ILaunchConfiguration lc : launchManager.getLaunchConfigurations(ctype))
			{
				String projectName = lc.getAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(), "");
				if(file.getProject().getName().equals(projectName))
					result.add(lc);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    protected ILaunchConfiguration findLaunchConfiguration(IFile sourceUnit,String processName, String mode) throws CoreException
    {
    	List<ILaunchConfiguration> confs = findLaunchConfigurationsByFile(sourceUnit); 
    	
    	ILaunchConfiguration result = null;
    	
    	for(ILaunchConfiguration lc : confs)
    	{
    		String foundProcessName = lc.getAttribute(CmlInterpreterLaunchConfigurationConstants.PROCESS_NAME.toString(), "");
    		if(foundProcessName.equals(processName))
    			result = lc;
    	}
    	
    	//create a new one
    	if(result == null)
    	{
    		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
        	ILaunchConfigurationType ctype = 
        			launchManager.getLaunchConfigurationType(CmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE.toString());
        	
        	
        	ILaunchConfigurationWorkingCopy lcwc = ctype.newInstance(null, launchManager.generateLaunchConfigurationName("Quick Launch"));
        	
        	lcwc.setAttribute(CmlInterpreterLaunchConfigurationConstants.PROCESS_NAME.toString(), processName);
        	lcwc.setAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(), 
        			sourceUnit.getProject().getName());
        	lcwc.setAttribute(CmlInterpreterLaunchConfigurationConstants.CML_SOURCES_PATH.toString(),
        			CmlUtil.getCmlSourcesPathsFromProject(sourceUnit.getProject()));
        	lcwc.doSave();		
        	result = lcwc;
    	}
    	
    	return result;
    }
}
