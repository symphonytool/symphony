package eu.compassresearch.ide.interpreter.launching;

import java.util.List;
import java.util.Vector;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class CmlLaunchConfigurationTabGroupCoSim extends
		AbstractLaunchConfigurationTabGroup
{

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode)
	{

		List<ILaunchConfigurationTab> tabs = new Vector<ILaunchConfigurationTab>();
		tabs.add(new CmlCoSimMainLaunchConfigurationTab());
		// tabs.add(getRuntimeTab());
		// tabs.add(new VmArgumentsLaunchConfigurationTab());
		tabs.add(new CmlDevelopLaunchConfigurationTab());
		// tabs.addAll(getAdditionalTabs());
		// tabs.add(new EnvironmentTab());
		// tabs.add(new SourceLookupTab());
		tabs.add(new CommonTab());
		setTabs(tabs.toArray(new ILaunchConfigurationTab[tabs.size()]));

	}


}
