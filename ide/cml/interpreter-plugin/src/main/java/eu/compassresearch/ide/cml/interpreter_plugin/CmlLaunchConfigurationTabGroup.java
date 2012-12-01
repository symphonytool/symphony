package eu.compassresearch.ide.cml.interpreter_plugin;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

public class CmlLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup  {

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
		
		IProject project = CmlUtil.getCurrentSelectedProject();
		//Set the project name
		configuration.setAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(),project.getName());
		//Set the project path
		configuration.setAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_PATH.toString(),getProjectPath(project));
		
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
