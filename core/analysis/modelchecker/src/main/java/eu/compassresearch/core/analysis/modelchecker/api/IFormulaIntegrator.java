package eu.compassresearch.core.analysis.modelchecker.api;


public interface IFormulaIntegrator {
	public FormulaResult analyse(String specificationContent) throws FormulaIntegrationException; 
	public void finalize() throws Throwable;
}
