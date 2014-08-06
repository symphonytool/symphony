package eu.compassresearch.ide.interpreter;

import org.eclipse.swt.graphics.RGB;

public interface ICmlDebugConstants
{

	public final String ATTR_LAUNCH_CONFIGURATION_TYPE = "eu.compassresearch.ide.interpreter.launchConfigurationType";
	public final String ATTR_DEBUGGER_CONFIG_PARAMS = "eu.compassresearch.ide.interpreter.debugger_config_params";
	public final String ID_CML_DEBUG_MODEL = "eu.compassresearch.ide.interpreter.modelId";
	public final String ID_CML_OPTION_VIEW = "eu.compassresearch.ide.interpreter.OptionsView";
	public final String ID_CML_HISTORY_VIEW = "eu.compassresearch.ide.interpreter.historyView";
	public final String ID_CML_PLUGIN_NAME = "eu.compassresearch.ide.interpreter";

	public final String CML_LAUNCH_CONFIG_PROJECT = "CML_LAUNCH_CONFIG_PROJECT".toLowerCase();
	public final String CML_LAUNCH_CONFIG_PROCESS_NAME = "CML_LAUNCH_CONFIG_PROCESS_NAME".toLowerCase();
	public final String CML_LAUNCH_CONFIG_IS_ANIMATION = "eu.compassresearch.ide.interpreter.is_animation";
	public final String CML_LAUNCH_CONFIG_USE_REMOTE_CONTROL = "CML_LAUNCH_CONFIG_USE_REMOTE_CONTROL".toLowerCase();
	// public final String CML_LAUNCH_CONFIG_PROCESS_FILE_PATH = "CML_LAUNCH_CONFIG_PROCESS_FILE_PATH".toLowerCase();
	public final String PLUGIN_ID = "eu.compassresearch.ide.interpreter";
	// public final String PREFERENCES_REMOTE_DEBUG = "PREFERENCES_REMOTE_DEBUG".toLowerCase();
	public final String CML_LAUNCH_CONFIG_REMOTE_DEBUG = "CML_LAUNCH_CONFIG_REMOTE_DEBUG".toLowerCase();
	public final String CML_LAUNCH_CONFIG_ENABLE_LOGGING = "CML_LAUNCH_CONFIG_ENABLE_LOGGING".toLowerCase();
	public final String CML_LAUNCH_CONFIG_REMOTE_INTERPRETER_CLASS = "CML_LAUNCH_CONFIG_REMOTE_INTERPRETER_CLASS".toLowerCase();
	public final String PREFERENCES_DEBUG_HIGHLIGHT_COLOR = "PREFERENCES_DEBUG_HIGHLIGHT_COLOR".toLowerCase();
	
	public static final String LINE_BREAKPOINT_MARKER_ID = "cml.lineBreakpoint.marker"; //$NON-NLS-1$
	
	public static final String DEBUG_ENGINE_CLASS = "eu.compassresearch.core.interpreter.debug.DebugMain";
	public final String PREFERENCES_AUTO_FILTER_TOCK_EVENTS = "PREFERENCES_AUTO_FILTER_TOCK_EVENTS".toLowerCase();
	
	public final RGB DEFAULT_HIGHLIGHT_COLOR = new RGB(245, 251, 145);
	public final boolean DEFAULT_AUTO_FILTER_TOCK_EVENTS=true;
	public final String CML_LAUNCH_CONFIG_COSIM_HOST = "CML_LAUNCH_CONFIG_COSIM_HOST".toLowerCase();
	public final String CML_LAUNCH_CONFIG_COSIM_IS_COORDINATOR = "CML_LAUNCH_CONFIG_COSIM_IS_COORDINATOR".toLowerCase();
	public final String CML_LAUNCH_CONFIG_COSIM_EXTERNAL_PROCESSES = "CML_LAUNCH_CONFIG_COSIM_EXTERNAL_PROCESSES".toLowerCase();
	public final String CML_LAUNCH_CONFIG_MININT = "CML_LAUNCH_CONFIG_COSIM_MININT".toLowerCase();
	public final String CML_LAUNCH_CONFIG_MAXINT = "CML_LAUNCH_CONFIG_COSIM_MAXINT".toLowerCase();
	public final String CML_LAUNCH_CONFIG_RANDOM_SEED = "CML_LAUNCH_CONFIG_RANDOM_SEED".toLowerCase();
	
}
