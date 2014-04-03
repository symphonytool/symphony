package eu.compassresearch.ide.interpreter.launch;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

import eu.compassresearch.core.interpreter.debug.CmlInterpreterArguments;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;

public class CmlCoSimLaunchConfigurationDelegate extends
		CmlLaunchConfigurationDelegate
{
	@Override
	protected Map<String, Object> createDebuggerArgumentMap(
			ILaunchConfiguration configuration, int port) throws CoreException,
			IOException
	{
		Map<String, Object> map = super.createDebuggerArgumentMap(configuration, port);

		map.put(CmlInterpreterArguments.COSIM_MODE.key, configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_IS_COORDINATOR, true) ? "server"
				: "client");
		map.put(CmlInterpreterArguments.COSIM_EXTERNAL_PROCESSES.key, configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_EXTERNAL_PROCESSES, ""));
		map.put(CmlInterpreterArguments.COSIM_HOST.key, configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_HOST, ""));

		return map;
	}

	@Override
	protected boolean shouldAutoTerminate()
	{
		return false;
	}
}
