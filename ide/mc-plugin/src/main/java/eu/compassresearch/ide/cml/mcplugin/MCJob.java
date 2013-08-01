package eu.compassresearch.ide.cml.mcplugin;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerVisitor;

public class MCJob extends Job{

	private FormulaResult result;
	private PSource source;
	private String propertyToCheck;
	private CMLModelcheckerVisitor adaptor;
	private IFormulaIntegrator mc;
	
	public MCJob(String name) {
		super(name);
	}
	
	public MCJob(String name, PSource s, String p){
		super(name);
		this.propertyToCheck = p;
		this.source = s;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("Analysing...", 100);
		try {
			this.result = getMCOutputFromSource(source, propertyToCheck);
		} catch (Exception e) {
			e.printStackTrace();
		}
		monitor.done();
		return Status.OK_STATUS;
	}
	
	private FormulaResult getMCOutputFromSource(PSource source, String propertyToCheck) throws Exception {

		FormulaResult mcResult;

		this.adaptor = new CMLModelcheckerVisitor(source);
		this.adaptor.setPropertyToCheck(propertyToCheck);

		this.mc = FormulaIntegrator.getInstance();
		String[] codes = adaptor.generateFormulaCodeForAll();
				
		mcResult = mc.analyse(codes[0]);
		return mcResult;
	}
	
	public FormulaResult getFormulaResult(){
		IProgressMonitor i = new MCMonitor();
		run(i);
		return this.result;
	}

}
