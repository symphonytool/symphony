package eu.compassresearch.core.analysis.modelchecker.api;

import java.io.IOException;

public class FormulaIntegrator implements IFormulaIntegrator {

	private static FormulaIntegrator instance;
	private IFORMULAInvoker executor; 
	private JavaFormulaLogger logger;

	public static synchronized FormulaIntegrator getInstance() throws FormulaIntegrationException{
		if(instance == null){
			instance = new FormulaIntegrator();
		}
		return instance;
	}  
	
	public static synchronized boolean hasInstance() {
		return instance != null;
	}
	
	private FormulaIntegrator() throws FormulaIntegrationException{
		try {
			logger = JavaFormulaLogger.obterInstancia();
			//the real executor must be instantiated
			executor = Formula.getInstance();
			
		} catch (IOException e) {
			logger.log(e.getMessage());
			throw new FormulaIntegrationException(e.getMessage());
		}
	}
	
	public static boolean checkFormulaInstallation(){
		boolean result = true;
		try {
			FormulaIntegrator.getInstance();
		} catch (FormulaIntegrationException e) {
			result = false;
		}
		return result;
	}
	@Override
	public synchronized FormulaResult analyse(String specificationContent)
			throws FormulaIntegrationException {
		
		FormulaResult result = new FormulaResult();
		try {
			result = executor.runFormula(specificationContent);
		} catch (IOException e) {
			logger.log(e.getMessage());
			throw new FormulaIntegrationException(e.getMessage());
		}  
		
		return result;
	}
	
	@Override
	public FormulaResult analyseFile(String specificationPath) throws FormulaIntegrationException{
		FormulaResult result = new FormulaResult();

		try {
			result = executor.runFormulaUsingFile(specificationPath);
		} catch (IOException e) {
			logger.log(e.getMessage());
			throw new FormulaIntegrationException(e.getMessage());
		}  
		
		return result;
	}
	
	@Override
	public void finalize() throws Throwable {
		try {
			if(executor!=null){
				executor.finalizeProcess();
			}
		} catch (Exception e) {
			logger.log(e.getMessage());
		}
		super.finalize();
	}
	
	@Override
	public void resetInstance() throws Throwable {
		try {
			executor.resetInstance();
		} catch (Exception e) {
			logger.log(e.getMessage());
		}
	}
}
