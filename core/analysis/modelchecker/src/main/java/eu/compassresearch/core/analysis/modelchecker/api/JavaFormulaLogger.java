package eu.compassresearch.core.analysis.modelchecker.api;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaFormulaLogger{
	private static Logger logger;
	private static FileHandler fh;
	private final static String LOGGER_NAME = "LogBanco"; 
	private final static int MAX_BYTES = 5000;
	private final static int MAX_FILES = 1;
	
	private static JavaFormulaLogger instancia;
	
	static{
		try {
			fh = new FileHandler("LOG.txt", MAX_BYTES,MAX_FILES,true);
			logger = Logger.getLogger(LOGGER_NAME);
			logger.setLevel(Level.FINE);
			logger.addHandler(fh);  
			instancia = new JavaFormulaLogger();
			
		} catch (SecurityException e) {
			System.out.println("Secutiry exception Logger was not created");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO exception Logger was not created");
			e.printStackTrace();
		}
	}
	
	private JavaFormulaLogger(){
		
	}
	public static JavaFormulaLogger obterInstancia(){
		return instancia;
	}  
	
	public void log(String content){
		//logger.info(content);
		logger.fine(content);
	}
	
	public void close(){
		fh.close();
	}
	
}
