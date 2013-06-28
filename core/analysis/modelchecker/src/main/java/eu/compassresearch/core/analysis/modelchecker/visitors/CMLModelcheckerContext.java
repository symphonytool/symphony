package eu.compassresearch.core.analysis.modelchecker.visitors;

public class CMLModelcheckerContext {
	
	private StringBuilder scriptContent;

	public CMLModelcheckerContext() {
		scriptContent = new StringBuilder();
	}

	public StringBuilder getScriptContent() {
		return scriptContent;
	}

	public void setScriptContent(StringBuilder scriptContent) {
		this.scriptContent = scriptContent;
	}
	
	
	
}
