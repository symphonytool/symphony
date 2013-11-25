package eu.compassresearch.ide.modelchecker;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.ExtensionFactory;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

public class MCProgressView extends ExtensionFactory{

	private MCProgressMonitorHandler pm;
	private IFile out;
	private String property;
	private IFolder mcFolder;
	private ICmlSourceUnit selectedUnit;
	private IResource cmlFile;
	private ExecutionEvent event;
	private String analysedProcess;
	
	public MCProgressView() {
		super();
	}
	
	public MCProgressView(IFile out, String property, IFolder mcFolder, ICmlSourceUnit selectedUnit, IResource cmlFile, ExecutionEvent event,String analysedProcess){
		this.out = out;
		this.property = property;
		this.mcFolder = mcFolder;
		this.selectedUnit = selectedUnit;
		this.cmlFile = cmlFile;
		this.event = event;
		this.analysedProcess = analysedProcess;
		this.pm = new MCProgressMonitorHandler(out, property, mcFolder, selectedUnit, cmlFile, event,analysedProcess);
	}
	
	public synchronized void execute() throws ExecutionException{
		this.pm.execute(event);
	}
	
	public synchronized FormulaResult getFormulaResult() throws InterruptedException{
		//pm.join();
		return pm.getFormulaResult();
	}
}
