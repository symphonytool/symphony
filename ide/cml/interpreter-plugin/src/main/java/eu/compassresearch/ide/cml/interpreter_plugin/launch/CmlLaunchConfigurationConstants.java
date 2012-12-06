package eu.compassresearch.ide.cml.interpreter_plugin.launch;

public enum CmlLaunchConfigurationConstants {
	
	LAUNCH_CONFIG_NAME("CML Launch Configuration"),
	ATTR_PROJECT_NAME("eu.compassresearch.ide.cml.interpreter_plugin.project.name"),
	ATTR_PROJECT_PATH("eu.compassresearch.ide.cml.interpreter_plugin.project.path"),
	ATTR_CML_SOURCES_PATH("eu.compassresearch.ide.cml.interpreter_plugin.cml_sources_path"),
	CML_EXEC_MODE("eu.compassresearch.ide.cml.interpreter_plugin.exec_mode"),
	ATTR_PROCESS_NAME("eu.compassresearch.ide.cml.interpreter_plugin.process.name");
	
	private final String str;      

    private CmlLaunchConfigurationConstants(String s) {
    	str = s;
    }
    
    public String toString(){
       return str;
    }
}
