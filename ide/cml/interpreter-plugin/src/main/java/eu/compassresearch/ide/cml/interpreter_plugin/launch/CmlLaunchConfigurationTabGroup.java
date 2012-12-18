package eu.compassresearch.ide.cml.interpreter_plugin.launch;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.ide.cml.interpreter_plugin.CmlUtil;

public class CmlLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup  {

	//TODO: This is not done at all
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		
		List<ILaunchConfigurationTab> tabs = new Vector<ILaunchConfigurationTab>();
//		tabs.add(getMainTab());
//		tabs.add(getRuntimeTab());		
//		tabs.add(new VmArgumentsLaunchConfigurationTab());
//		tabs.add(new VdmDevelopLaunchConfigurationTab());
//		tabs.addAll(getAdditionalTabs());
		tabs.add(new EnvironmentTab());
		tabs.add(new SourceLookupTab());
		tabs.add(new CommonTab());
		setTabs(tabs.toArray(new ILaunchConfigurationTab[tabs.size()]));

		
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
						
		//FIXME: This method can only determine the project if its selected in the project explorer
		//		 Therefore this should not be set like this, but should be put in the launchconfiggui
		IProject project = CmlUtil.getCurrentSelectedProject();
		//Set the project name
		configuration.setAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(),project.getName());
		//Set the project path
		configuration.setAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_PATH.toString(),getProjectPath(project));
		//Set the project src path
		configuration.setAttribute(CmlLaunchConfigurationConstants.ATTR_CML_SOURCES_PATH.toString(),getProjectPath(project));
		
		super.setDefaults(configuration);
	}

	
	private String getProjectPath(IProject project)
	{
		String projectPath = project.getFullPath().toOSString();
		//String pat = System.getProperty("user.dir");
		
		String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toOSString();
		
		return workspacePath + projectPath;
	}
		
}
