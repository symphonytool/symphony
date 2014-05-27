/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.compassresearch.core.analysis.modelchecker.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import javax.swing.filechooser.FileSystemView;

import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;

public class Formula implements IFORMULAInvoker {

	public static final String DEFAULT_MODEL_NAME = "StartProcModel";
	public static final String MAIN_QUERY_CLAUSE = "conforms";
	public static final String FORMULA_CMD = "formula";
	public static final String FORMULA_LOAD_CMD = "load";
	public static final String FORMULA_SOLVE_CMD = "solve";
	public static final String FORMULA_KNOWS_CMD = "knows";
	public static final String FORMULA_EXIT_CMD = "exit";
	private String basicFormulaCmdMsg;

	enum FormulaStatus {NOT_INSTALLED,INSTALLED, FORMULA_ERROR, LOAD_ERROR,SOLVE_ERROR,SAVE_ERROR};
	enum ProcessStatus {NOT_STARTED,STARTED,TERMINATED};
	
    private FormulaStatus formulaStatus;
    private ProcessStatus processStatus;
    private Process process;
    private static Formula instance;
    private JavaFormulaLogger defaultLogger;

    public synchronized static Formula getInstance() throws IOException, FormulaIntegrationException{
    	if (instance == null){
    		instance = new Formula();
    	} else if (instance.processStatus == ProcessStatus.TERMINATED){
    		instance = new Formula();
    	}
    	return instance;
    }
    private Formula() throws IOException, FormulaIntegrationException {
    	defaultLogger = JavaFormulaLogger.obterInstancia();
    	this.launchFormula();
    }
	

    private void load(File specFile, FormulaResult result) throws IOException, FormulaIntegrationException{
    	String loadCommand = 	FORMULA_LOAD_CMD + " " + specFile.getAbsolutePath() + "\n";
    	
    	OutputStream stdin = process.getOutputStream();
	    InputStream stdout = process.getInputStream();
	    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
	    
    	writer.write(loadCommand);
		writer.flush();
		String line = "";
		StringBuilder msg = new StringBuilder();
		double elapsedTimeLoad = 0.0;
		while ((line = reader.readLine()) != null ) {
			msg.append(line + "\n");
    		if (line.contains("error")) {
    			defaultLogger.log(line);
    			formulaStatus = FormulaStatus.FORMULA_ERROR;
    			//throw new FormulaIntegrationException(line);
    		} 
    		if (line.contains("Elapsed")) {
    			StringTokenizer tokens = new StringTokenizer(line);
				while (tokens.hasMoreTokens()) {
					if (tokens.nextToken().equals("Elapsed:")) {
						String time = tokens.nextToken().trim();
						elapsedTimeLoad = Double.valueOf(time.replace(",", "."));
						line = reader.readLine();
						break;
					}
				}
				break;
			}
		}
		result.setElapsedTimeLoad(elapsedTimeLoad);
		result.setLoadCmdMessage(msg.toString());
		if(result.getFormulaCmdMessage() == null){
			result.setFormulaCmdMessage(basicFormulaCmdMsg);
		}
    }

	private void solve(FormulaResult result) throws IOException, FormulaIntegrationException {
		String solveCommand = 	FORMULA_SOLVE_CMD + " " + 
								DEFAULT_MODEL_NAME + " " +
								MAIN_QUERY_CLAUSE + "\n";  
		
		OutputStream stdin = process.getOutputStream();
	    InputStream stdout = process.getInputStream();
	    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
		writer.write(solveCommand);
		writer.flush();
		String line = "";
		StringBuilder msg = new StringBuilder();
		double elapsedTimeSolve = 0.0;
		while ((line = reader.readLine()) != null) {
			msg.append(line + "\n");
			if(line.contains("No more solutions")){
				result.setSatisfiable(false);
			}
			if (line.contains("Elapsed")) {
				StringTokenizer tokens = new StringTokenizer(line);
				while (tokens.hasMoreTokens()) {
					if (tokens.nextToken().equals("Elapsed:")) {
						String time = tokens.nextToken().trim();
						elapsedTimeSolve = Double.valueOf(time.replace(",", "."));
						line = reader.readLine();
						break;
					}
				}
				break;
			}
		}
		result.setElapsedTimeSolve(elapsedTimeSolve);
		result.setSolveCmdMessage(msg.toString());
	}

	private void knows(FormulaResult result) throws IOException, FormulaIntegrationException {
		String knowsCommand = 	FORMULA_KNOWS_CMD + " " +
				DEFAULT_MODEL_NAME + "\n";

		OutputStream stdin = process.getOutputStream();
	    InputStream stdout = process.getInputStream();
	    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
		writer.write(knowsCommand);
		writer.flush();
		String line = "";
		StringBuilder facts = new StringBuilder();
		StringBuilder msg = new StringBuilder();
		double elapsedTimeKnows = 0.0;
		while ((line = reader.readLine()) != null) {
			msg.append(line + "\n");
			if (line.contains("List of terms")) {
				line = reader.readLine();
				msg.append(line + "\n");
				while ((line = reader.readLine()) != null) {
					if (line.contains("Query evaluation results")) {
						break;
					}
					facts.append(line.trim() + "\n");
					msg.append(line + "\n");
				}
			}
			
			if (line.contains("Elapsed")) {
				StringTokenizer tokens = new StringTokenizer(line);
				while (tokens.hasMoreTokens()) {
					if (tokens.nextToken().equals("Elapsed:")) {
						String time = tokens.nextToken().trim();
						elapsedTimeKnows = Double.valueOf(time.replace(",", "."));
						line = reader.readLine();
						break;
					}
				}
				break;
			}

		}
		result.setFacts(facts.toString());
		result.setElapsedTimeKnows(elapsedTimeKnows);
		result.setSaveCmdMessage(msg.toString());
		
	}
	private void exit() throws IOException{
		String exitCommand = 	FORMULA_EXIT_CMD + "\n";
		
		OutputStream stdin = process.getOutputStream();
	    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
		writer.write(exitCommand);
		writer.flush();
	}

	@Override
	public FormulaResult runFormulaUsingFile(String specificationPath) throws FormulaIntegrationException,
		IOException{
		
		File file = new File(specificationPath);
		StringBuilder specificationContent = Utilities.readScriptFromFile(specificationPath, true);
		FormulaResult result = this.launchFormula();

		if(this.formulaStatus == FormulaStatus.NOT_INSTALLED){
	    	FormulaIntegrationException ex = new FormulaIntegrationException(result.getFormulaCmdMessage());
	    	ex.printStackTrace();
	    	throw ex;
	    }
		result.setFileContent(specificationContent.toString());
		this.processRemainingCommands(file,result);

    	return result;
		
		
	}
	
	@Override
	public FormulaResult runFormula(String specification) throws FormulaIntegrationException, IOException {
		File file = createTmpFile(specification);
		
		FormulaResult result = this.launchFormula();
		
		if(this.formulaStatus == FormulaStatus.NOT_INSTALLED){
	    	FormulaIntegrationException ex = new FormulaIntegrationException(result.getFormulaCmdMessage());
	    	//ex.printStackTrace();
	    	throw ex;
	    }
		result.setFileContent(specification);
		this.processRemainingCommands(file,result);

    	return result;
    }
	private File createTmpFile(String content) throws IOException{
		//File temp = new File(FormulaIntegrationUtilities.WORKING_DIRECTORY, "formulaTmpFile.4ml");
		File temp = new File(FileSystemView.getFileSystemView().getHomeDirectory(), "formulaTmpFile.4ml");
	//	String basicContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.BASIC_FORMULA_SCRIPT).toString();
		temp.deleteOnExit();
		
		FileWriter fw;
		fw = new FileWriter(temp);
	//	fw.write(basicContent);
	//	fw.write("\n\n");
		fw.write(content);
		fw.flush();
		fw.close();
		
		return temp;
	}
	private FormulaResult launchFormula() throws IOException{
		FormulaResult result = new FormulaResult();
		if(this.processStatus != ProcessStatus.STARTED){
			process = Runtime.getRuntime().exec(FORMULA_CMD);
			
			InputStream stdout = process.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

		    String line = reader.readLine();
		    StringBuilder msg = new StringBuilder();
	    	if (!line.contains("Formula Version")) {
				formulaStatus = FormulaStatus.NOT_INSTALLED;
			}else{
				formulaStatus = FormulaStatus.INSTALLED;
				processStatus = ProcessStatus.STARTED;
				while ((line = reader.readLine()) != null) {
					msg.append(line + "\n");
			    	if (line.contains("Started Formula Interpreter")) {
		    			line = reader.readLine();
		    			msg.append(line + "\n");
		    			break;
		    		}
		    	}
				result.setFormulaCmdMessage(msg.toString());
				this.basicFormulaCmdMsg = msg.toString();
			}
		}else{
			result.setFormulaCmdMessage(this.basicFormulaCmdMsg);
		}
		
    	return result;
	}
	private void processRemainingCommands(File specFile, FormulaResult result) throws FormulaIntegrationException, IOException{

		if(this.processStatus == ProcessStatus.STARTED){
			load(specFile,result);
		}else{
			result = this.launchFormula();
			load(specFile, result);
		}
        if(this.formulaStatus == FormulaStatus.FORMULA_ERROR){
        	FormulaIntegrationException ex = new FormulaIntegrationException(result.getLoadCmdMessage());
        	ex.printStackTrace();
        	throw ex;
        }
        solve(result);
        knows(result);
	}

    public void finalizeProcess() throws IOException{
        if (process != null) {
        	try {
				this.exit();
			} catch (IOException e) {
				defaultLogger.log(e.getMessage());
			}finally{
				process.destroy();
				this.processStatus = ProcessStatus.TERMINATED;
			}
        }
    }
    
    @Override
	public void resetInstance() throws IOException{
		finalizeProcess();
	}
	 public static String generateFactsFileName(String formulaFileName){
		 String result = new String(formulaFileName);
		 int indexOfDot = result.lastIndexOf('.');
		 result = result.substring(0, indexOfDot);
		 result = result + ".txt";
		 return result;
	 }
}
