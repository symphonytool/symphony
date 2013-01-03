package eu.compassresearch.ide.cml.interpreter_plugin.launch;

public enum CmlLaunchConfigurationConstants {
	
	LAUNCH_CONFIG_NAME("CML Launch Configuration"),
	ATTR_PROJECT_NAME("eu.compassresearch.ide.cml.interpreter_plugin.project.name"),
	ATTR_PROJECT_PATH("eu.compassresearch.ide.cml.interpreter_plugin.project.path");
	
	private final String str;      

    private CmlLaunchConfigurationConstants(String s) {
    	str = s;
    }
    
    public String toString(){
       return str;
    }
}
