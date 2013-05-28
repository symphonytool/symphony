package eu.compassresearch.ide.cml.interpreter.launch;

public enum CmlLaunchConfigurationConstants {
	
	//The name of the Launch Configuration
	LAUNCH_CONFIG_NAME("CML Launch Configuration"),
	//The name of the attribute that contains the name of the project in launch configuration.
	ATTR_PROJECT_NAME("eu.compassresearch.ide.cml.interpreter.project.name"),
	//The name of the attribute that contains the project path of the project in the launch configuration.
	ATTR_PROJECT_PATH("eu.compassresearch.ide.cml.interpreter.project.path");
	
	private final String str;      

    private CmlLaunchConfigurationConstants(String s) {
    	str = s;
    }
    
    public String toString(){
       return str;
    }
}
