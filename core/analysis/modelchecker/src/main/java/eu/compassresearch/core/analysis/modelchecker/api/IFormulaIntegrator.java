package eu.compassresearch.core.analysis.modelchecker.api;


public interface IFormulaIntegrator {
	public FormulaResult analyse(String specificationContent) throws FormulaIntegrationException; 
	public FormulaResult analyseFile(String specificationPath) throws FormulaIntegrationException;
	public void finalize() throws Throwable;
}
