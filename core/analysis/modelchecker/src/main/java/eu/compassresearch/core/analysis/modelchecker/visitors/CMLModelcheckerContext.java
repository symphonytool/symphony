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
	
	
	
}
