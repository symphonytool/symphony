package eu.compassresearch.core.interpreter.debug;

public enum CmlInterpreterLaunchConfiguration {

	CML_SOURCES_PATH("eu.compassresearch.ide.cml.interpreter.cml_sources_path"),
	CML_EXEC_MODE("eu.compassresearch.ide.cml.interpreter.exec_mode"),
	//PROCESS_FILE_PATH("eu.compassresearch.ide.cml.interpreter.process_file_path"),
	PROCESS_NAME("eu.compassresearch.ide.cml.interpreter.process_name");
	
	private final String str;      

    private CmlInterpreterLaunchConfiguration(String s) {
    	str = s;
    }
    
    public String toString(){
       return str;
    }
	
}
