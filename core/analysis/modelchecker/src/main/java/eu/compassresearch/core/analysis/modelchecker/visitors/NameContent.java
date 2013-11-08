package eu.compassresearch.core.analysis.modelchecker.visitors;

public class NameContent {
	private String fileName;
	private String content;
	public NameContent(String fileName, String content) {
		super();
		this.fileName = fileName;
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
