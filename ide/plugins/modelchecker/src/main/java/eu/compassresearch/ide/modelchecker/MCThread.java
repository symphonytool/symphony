package eu.compassresearch.ide.modelchecker;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.progress.FinishedJobs;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewMCVisitor;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

enum MCStatus { CREATED, RUNNING, ERROR, FINISHED}

public class MCThread extends Thread{
	
	private MCStatus status;
	private IFile file;
	private FormulaResult result;
	private FormulaIntegrator mc;
	private FormulaIntegrationException exception;
	private FormulaResultWrapper fmw;
	private String analysedProcess;
	private String propertyToCheck;
	private IFolder mcFolder;
	private ICmlSourceUnit selectedUnit;
	private IResource cmlFile;
	private IWorkbenchWindow window;
	private Registry registry;
	
	public MCThread(IFile f, String property, IFolder mcFolder, ICmlSourceUnit selectedUnit, IResource cmlFile, IWorkbenchWindow win, String analysedProcess) {
		this.status = MCStatus.CREATED;
		this.file = f;
		this.propertyToCheck = property;
		this.mcFolder = mcFolder;
		this.selectedUnit = selectedUnit;
		this.cmlFile = cmlFile;
		this.window = win;
		this.analysedProcess = analysedProcess;
		RegistryFactory factory = eu.compassresearch.core.common.RegistryFactory.getInstance(MCConstants.MC_REGISTRY_ID);
		this.registry = factory.getRegistry();
	}
	
	
	@Override
	public synchronized void run(){
		this.status = MCStatus.RUNNING;
		try {
			mc = FormulaIntegrator.getInstance();
			String absolutePath = file.getLocation().toPortableString();
			this.result = mc.analyseFile(absolutePath);
			this.writeFormulaOutputTofile(selectedUnit, mcFolder, result);
			this.fmw = new FormulaResultWrapper(result, null, propertyToCheck, mcFolder, selectedUnit, analysedProcess);
			MCPluginDoStuff mcp = new MCPluginDoStuff(getWindow().getActivePage().getActivePart().getSite(), cmlFile, this.fmw);
			mcp.run();
			registry.store(selectedUnit.getParseNode(), fmw);
		} catch (FormulaIntegrationException e) {
			exception = e;
			this.status = MCStatus.ERROR;
			throw e;
		}
		this.status = MCStatus.FINISHED;
	}

	private void writeFormulaOutputTofile(ICmlSourceUnit selectedCmlSourceUnit, IFolder mcFolder, FormulaResult result){
		String name = selectedCmlSourceUnit.getFile().getName();
		String formulaFileName = name.substring(0,name.length()-selectedCmlSourceUnit.getFile().getFileExtension().length())+"facts";
		IFile outputFile = mcFolder.getFile(formulaFileName);
		
		try{
			if(!outputFile.exists()){
				outputFile.create(new ByteArrayInputStream(result.getFacts().getBytes()), true, new NullProgressMonitor());
			}else{
				outputFile.setContents(new ByteArrayInputStream(result.getFacts().getBytes()), true, true, new NullProgressMonitor());
			}
		}catch(CoreException e){
			Activator.log(e);
		}
	} 
	
	public synchronized MCStatus getStatus(){
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
