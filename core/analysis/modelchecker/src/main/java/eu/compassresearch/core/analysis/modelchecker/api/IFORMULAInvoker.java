package eu.compassresearch.core.analysis.modelchecker.api;

import java.io.IOException;

public interface IFORMULAInvoker {

	public FormulaResult runFormula(String specification) throws FormulaIntegrationException,
			IOException;
	public FormulaResult runFormulaUsingFile(String specificationPath) throws FormulaIntegrationException,
			IOException;
	
	public void finalizeProcess() throws IOException;
	
}