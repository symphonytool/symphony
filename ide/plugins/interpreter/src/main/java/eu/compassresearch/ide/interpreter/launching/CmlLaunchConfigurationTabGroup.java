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

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode)
	{

		List<ILaunchConfigurationTab> tabs = new Vector<ILaunchConfigurationTab>();
		tabs.add(new CmlMainLaunchConfigurationTab());
		tabs.add(new CmlAdvancedLaunchConfigurationTab());
		tabs.add(new CmlDevelopLaunchConfigurationTab());
		tabs.add(new CommonTab());
		setTabs(tabs.toArray(new ILaunchConfigurationTab[tabs.size()]));

	}


}
