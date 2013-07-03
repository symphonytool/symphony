package eu.compassresearch.core.analysis.modelchecker.api;

public interface FormulaIntegrationFactory {
	public IFormulaIntegrator createFormulaIntegrator() throws FormulaIntegrationException;
}
