package eu.compassresearch.ide.cml.interpreter;

public enum CmlDebugConstants {
	
	ATTR_LAUNCH_CONFIGURATION_TYPE("eu.compassresearch.ide.cml.interpreter.launchConfigurationType"),
	ATTR_DEBUGGER_CONFIG_PARAMS("eu.compassresearch.ide.cml.interpreter.debugger_config_params"),
	ID_CML_DEBUG_MODEL("eu.compassresearch.ide.cml.interpreter.modelId"),
	ID_CML_OPTION_VIEW("eu.compassresearch.ide.cml.interpreter.OptionsView"),
	ID_CML_HISTORY_VIEW("eu.compassresearch.ide.cml.interpreter.historyView"),
	ID_CML_PLUGIN_NAME("eu.compassresearch.ide.cml.interpreter");
	
	private final String str;      

    private CmlDebugConstants(String s) {
    	str = s;
    }
    
    public String toString(){
       return str;
    }
}
