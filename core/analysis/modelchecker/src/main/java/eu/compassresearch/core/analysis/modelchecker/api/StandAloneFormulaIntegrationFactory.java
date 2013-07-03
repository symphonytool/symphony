package eu.compassresearch.core.analysis.modelchecker.api;

public class StandAloneFormulaIntegrationFactory implements
		FormulaIntegrationFactory {

	private static StandAloneFormulaIntegrationFactory instance;
	
	private StandAloneFormulaIntegrationFactory(){
		
	}
	public synchronized static StandAloneFormulaIntegrationFactory getInstance(){
		if(instance == null){
			instance = new StandAloneFormulaIntegrationFactory();
		}
		return instance;
	} 
	@Override
	public IFormulaIntegrator createFormulaIntegrator()
			throws FormulaIntegrationException {
		
		return FormulaIntegrator.getInstance();
	}

}
