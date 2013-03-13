package eu.compassresearch.ide.cml.interpreter_plugin.launch;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.debug.CmlInterpreterLaunchConfigurationConstants;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlUtil;

public class CmlLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup  {

	//TODO: This is not done at all
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		
		List<ILaunchConfigurationTab> tabs = new Vector<ILaunchConfigurationTab>();
		tabs.add(new CmlMainLaunchConfigurationTab());
//		tabs.add(getRuntimeTab());		
//		tabs.add(new VmArgumentsLaunchConfigurationTab());
//		tabs.add(new VdmDevelopLaunchConfigurationTab());
//		tabs.addAll(getAdditionalTabs());
		//tabs.add(new EnvironmentTab());
		//tabs.add(new SourceLookupTab());
		tabs.add(new CommonTab());
		setTabs(tabs.toArray(new ILaunchConfigurationTab[tabs.size()]));

		
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
						
		//FIXME: This method can only determine the project if its selected in the project explorer
		//		 Therefore this should not be set like this, but should be put in the launchconfiggui
		IProject project = CmlUtil.getCurrentSelectedProject();
		if(project != null)
		{
			//Set the project name
			configuration.setAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(),project.getName());
			//Set the project path
			configuration.setAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_PATH.toString(),CmlUtil.getProjectPath(project));
			
			//Find the path for all the sources of the project
			configuration.setAttribute(CmlInterpreterLaunchConfigurationConstants.CML_SOURCES_PATH.toString(),
					CmlUtil.getCmlSourcesPathsFromProject(project));
		}
		super.setDefaults(configuration);
	}
		
}
