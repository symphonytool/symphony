package eu.compassresearch.ide.interpreter.launching;

import java.util.List;
import java.util.Vector;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class CmlLaunchConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup
{

	// TODO: This is not done at all
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode)
	{

		List<ILaunchConfigurationTab> tabs = new Vector<ILaunchConfigurationTab>();
		tabs.add(new CmlMainLaunchConfigurationTab());
		// tabs.add(getRuntimeTab());
		// tabs.add(new VmArgumentsLaunchConfigurationTab());
		tabs.add(new CmlDevelopLaunchConfigurationTab());
		// tabs.addAll(getAdditionalTabs());
		// tabs.add(new EnvironmentTab());
		// tabs.add(new SourceLookupTab());
		tabs.add(new CommonTab());
		setTabs(tabs.toArray(new ILaunchConfigurationTab[tabs.size()]));

	}

	// @Override
	// public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
	// {
	//
	// // FIXME: This method can only determine the project if its selected in the project explorer
	// // Therefore this should not be set like this, but should be put in the launchconfiggui
	// IProject project = CmlUtil.getCurrentSelectedProject();
	// if (project != null)
	// {
	// // Set the project name
	// configuration.setAttribute(ICmlLaunchConfigurationConstants.ATTR_PROJECT_NAME, project.getName());
	// // Set the project path
	// configuration.setAttribute(ICmlLaunchConfigurationConstants.ATTR_PROJECT_PATH, CmlUtil.getProjectPath(project));
	//
	// // Find the path for all the sources of the project
	// configuration.setAttribute(CmlInterpreterLaunchConfigurationConstants.CML_SOURCES_PATH.toString(),
	// CmlUtil.getCmlSourcesPathsFromProject(project));
	// }
	// super.setDefaults(configuration);
	// }

}
