package eu.compassresearch.ide.cml.interpreter_plugin.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;

import eu.compassresearch.ast.program.PSource;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IResource getLaunchableResource(ISelection selection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		// TODO Auto-generated method stub
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
			launch(ast,mode);
		}
		else
		//If no ast is attached then there are either parser or type errors
		{
			MessageDialog.openError(null, "Launch failure", "The Cml model contains error and therefore cannot be launched");
		}
	
	}

    protected void launch(PSource sourceUnit, String mode) {
        try {
            ILaunchConfiguration config = findLaunchConfiguration(sourceUnit, mode);
            
            if (config != null) {
             config.launch(mode, null);
            }
        } catch (CoreException e) {
            /* Handle exceptions*/
        	e.printStackTrace();
        }
    }
    
    protected ILaunchConfiguration findLaunchConfiguration(PSource sourceUnit, String mode) throws CoreException
    {
    	ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
    	ILaunchConfigurationType ctype = 
    			launchManager.getLaunchConfigurationType(CmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE.toString());
    	
    	ILaunchConfiguration result = null;
    	
    	IProject project = CmlUtil.getCurrentSelectedProject();
    	
    	for(ILaunchConfiguration lc : launchManager.getLaunchConfigurations(ctype))
    	{
    		String projectName = lc.getAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(), "");
    		if(project.getName().equals(projectName))
    			result = lc;
    		
    	}
    	
    	return result;
    }
}
