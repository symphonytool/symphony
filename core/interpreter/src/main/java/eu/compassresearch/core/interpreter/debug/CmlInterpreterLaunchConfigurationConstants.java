package eu.compassresearch.core.interpreter.debug;

public enum CmlInterpreterLaunchConfigurationConstants {

	CML_SOURCES_PATH("eu.compassresearch.ide.interpreter.cml_sources_path"),
	CML_EXEC_MODE("eu.compassresearch.ide.interpreter.exec_mode"),
	PROCESS_FILE_PATH("eu.compassresearch.ide.interpreter.process_file_path"),
	PROCESS_NAME("eu.compassresearch.ide.interpreter.process_name");
	
	private final String str;      

    private CmlInterpreterLaunchConfigurationConstants(String s) {
    	str = s;
    }
    
    public String toString(){
       return str;
    }
	
}
