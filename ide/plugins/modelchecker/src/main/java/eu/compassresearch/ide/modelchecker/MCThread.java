package eu.compassresearch.ide.modelchecker;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IWorkbenchWindow;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

enum MCStatus { CREATED, RUNNING, ERROR, FINISHED}

public class MCThread extends Thread{
	
	private MCStatus status;
	private IFile file;
	private FormulaResult result;
	private FormulaIntegrator mc;
	private FormulaIntegrationException exception;
	private FormulaResultWrapper fmw;
	private String propertyToCheck;
	private IContainer mcFolder;
	private ICmlSourceUnit selectedUnit;
	private IResource cmlFile;
	private IWorkbenchWindow window;
	
	public MCThread(IFile f, String property, IContainer mcFolder, ICmlSourceUnit selectedUnit, IResource cmlFile, IWorkbenchWindow win) {
		this.status = MCStatus.CREATED;
		this.file = f;
		this.propertyToCheck = property;
		this.mcFolder = mcFolder;
		this.selectedUnit = selectedUnit;
		this.cmlFile = cmlFile;
		this.window = win;
	}
	
	
	@Override
	public synchronized void run(){
		this.status = MCStatus.RUNNING;
		try {
			mc = FormulaIntegrator.getInstance();
			String absolutePath = file.getLocation().toPortableString();
			this.result = mc.analyseFile(absolutePath);
			this.fmw = new FormulaResultWrapper(result, null, propertyToCheck, mcFolder, selectedUnit);
			MCPluginDoStuff mcp = new MCPluginDoStuff(getWindow().getActivePage().getActivePart().getSite(), cmlFile, this.fmw);
			mcp.run();
		} catch (FormulaIntegrationException e) {
			exception = e;
			this.status = MCStatus.ERROR;
		}
		this.status = MCStatus.FINISHED;
	}

	public MCStatus getStatus(){
		return this.status;
	}
	
	public FormulaIntegrationException getException(){
		return this.exception;
	}


	public synchronized FormulaResult getFormulaResult() {
		return this.result;
	}


	public void setWindow(IWorkbenchWindow window2) {
		this.window = window2;
	}
	
	public IWorkbenchWindow getWindow(){
		return window;
	}

}
