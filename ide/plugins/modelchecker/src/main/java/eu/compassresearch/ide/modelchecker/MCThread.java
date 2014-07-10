package eu.compassresearch.ide.modelchecker;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IWorkbenchWindow;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;

enum MCStatus { CREATED, RUNNING, ERROR, FINISHED}

public class MCThread extends Thread{
	
	private MCStatus status;
	private IFile file;
	private FormulaResult result;
	private FormulaIntegrator mc;
	private Throwable exception;
	private FormulaResultWrapper formulaResultWrapper;
	private String analysedProcess;
	private String propertyToCheck;
	private IFolder mcFolder;
	//private ICmlSourceUnit selectedUnit;
	//private IResource cmlFile;
	private IWorkbenchWindow window;
	private Registry registry;
	
	public MCThread(IFile f, String property, IFolder mcFolder, IWorkbenchWindow win, String analysedProcess) {
		this.status = MCStatus.CREATED;
		this.file = f;
		this.propertyToCheck = property;
		this.mcFolder = mcFolder;
		this.window = win;
		this.analysedProcess = analysedProcess;
		RegistryFactory factory = eu.compassresearch.core.common.RegistryFactory.getInstance(MCConstants.MC_REGISTRY_ID);
		this.registry = factory.getRegistry();
	}
	
	
	@Override
	public void run(){
		this.status = MCStatus.RUNNING;
		try {
			mc = FormulaIntegrator.getInstance();
			String absolutePath = file.getLocation().toPortableString();
			this.result = mc.analyseFile(absolutePath);
			IFile factsFile = this.writeFormulaOutputTofile(file, mcFolder, result);
			this.formulaResultWrapper = new FormulaResultWrapper(result, null, propertyToCheck, mcFolder, analysedProcess, factsFile);
			MCPluginUtility.refreshMCListView(formulaResultWrapper, factsFile, this.window);
			//registry.store(selectedUnit.getParseNode(), formulaResultWrapper);
		} catch (FormulaIntegrationException e) {
			exception = e;
			this.status = MCStatus.ERROR;
			if(!e.getMessage().contains("pipe")){
				MCPluginUtility.popErrorMessage(e);
				this.cancelExecution();
				//throw e;
			}
		} catch (Throwable e) {
			//excep = e;
			exception = e;
			this.status = MCStatus.ERROR;
			throw e;
		}
		this.status = MCStatus.FINISHED;
	}

	public void cancelExecution(){
		mc = FormulaIntegrator.getInstance();
		try {
			mc.resetInstance();
		} catch (Throwable e1) {
			// formula instance is finished 
		}
		this.status = MCStatus.FINISHED;
	}
	
	private IFile writeFormulaOutputTofile(IFile selectedCmlSourceUnit, IFolder mcFolder, FormulaResult result){
		String name = selectedCmlSourceUnit.getName();
		String formulaFileName = name.substring(0,name.length()-selectedCmlSourceUnit.getFileExtension().length())+"facts";
		IFile outputFile = mcFolder.getFile(formulaFileName);
		
		try{
			if(!outputFile.exists()){
				outputFile.create(new ByteArrayInputStream(result.getFacts().getBytes()), true, new NullProgressMonitor());
			}else{
				outputFile.setContents(new ByteArrayInputStream(result.getFacts().getBytes()), true, true, new NullProgressMonitor());
			}
		}catch(CoreException e){
			CmlMCPlugin.log(e);
		}
		
		return outputFile;
	} 
	
	public synchronized MCStatus getStatus(){
		return this.status;
	}
	
	public Throwable getException(){
		return this.exception;
	}

	

}
