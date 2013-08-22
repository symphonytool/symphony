package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.HashMap;

public class CMLModelcheckerContext {
	
	protected HashMap<Object, Object> info; 
	
	private StringBuilder scriptContent;

	public CMLModelcheckerContext() {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
	}

	public StringBuilder getScriptContent() {
		return scriptContent;
	}

	public void setScriptContent(StringBuilder scriptContent) {
		this.scriptContent = scriptContent;
	}
	
	public void copyChannelInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
			this.info.put(Utilities.CHANNEL_DEFINITIONS_KEY, otherContext.info.get(Utilities.CHANNEL_DEFINITIONS_KEY));
		}
	}
	public void copyIOCommDefInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
			this.info.put(Utilities.IOCOMM_DEFINITIONS_KEY, otherContext.info.get(Utilities.IOCOMM_DEFINITIONS_KEY));
		}
	}
	
}
