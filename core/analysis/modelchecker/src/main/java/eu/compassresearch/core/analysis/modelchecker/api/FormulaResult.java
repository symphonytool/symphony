/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.compassresearch.core.analysis.modelchecker.api;

/**
 *
 * @author Gleydson
 */
public class FormulaResult {
    
	private String formulaCmdMessage;
	private String loadCmdMessage;
	private String solveCmdMessage;
	private String saveCmdMessage;
	private boolean satisfiable = true;
	private String facts;
	private double elapsedTimeSolve;
	private double elapsedTimeLoad;
	private double elapsedTimeKnows;
    private String filePath;
    private String fileContent;
	
    public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public String getFacts() {
		return facts;
	}
	public void setFacts(String facts) {
		this.facts = facts;
	}
	public boolean isSatisfiable() {
		return satisfiable;
	}
	public void setSatisfiable(boolean satisfiable) {
		this.satisfiable = satisfiable;
	}
	
    
	public String getFormulaCmdMessage() {
		return formulaCmdMessage;
	}
	public void setFormulaCmdMessage(String formulaCmdMessage) {
		this.formulaCmdMessage = formulaCmdMessage;
	}
	public String getLoadCmdMessage() {
		return loadCmdMessage;
	}
	public void setLoadCmdMessage(String loadCmdMessage) {
		this.loadCmdMessage = loadCmdMessage;
	}
	public String getSolveCmdMessage() {
		return solveCmdMessage;
	}
	public void setSolveCmdMessage(String solveCmdMessage) {
		this.solveCmdMessage = solveCmdMessage;
	}
	public String getSaveCmdMessage() {
		return saveCmdMessage;
	}
	public void setSaveCmdMessage(String saveCmdMessage) {
		this.saveCmdMessage = saveCmdMessage;
	}
	public double getElapsedTimeSolve() {
		return elapsedTimeSolve;
	}
	public void setElapsedTimeSolve(double elapsedTimeSolve) {
		this.elapsedTimeSolve = elapsedTimeSolve;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public double getElapsedTimeLoad() {
		return elapsedTimeLoad;
	}
	public void setElapsedTimeLoad(double elapsedTimeLoad) {
		this.elapsedTimeLoad = elapsedTimeLoad;
	}
	public double getElapsedTimeKnows() {
		return elapsedTimeKnows;
	}
	public void setElapsedTimeKnows(double elapsedTimeKnows) {
		this.elapsedTimeKnows = elapsedTimeKnows;
	}

    
}
