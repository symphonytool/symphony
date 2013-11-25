package eu.compassresearch.ide.modelchecker;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

public class MCProgressMonitorHandler extends AbstractHandler{

	//private IFile output;
	private MCThread thread;
	private IResource cmlFile;
	private String analysedProcess;
	
	public MCProgressMonitorHandler(IFile out, String property, IFolder mcFolder, ICmlSourceUnit selectedUnit, IResource cmlFile, ExecutionEvent event, String analysedProcess) {
		IWorkbenchWindow window;
		try {
			window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			this.thread = new MCThread(out, property, mcFolder, selectedUnit, cmlFile, window, analysedProcess);
			this.cmlFile = cmlFile;
			this.analysedProcess = analysedProcess;
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public synchronized Object execute(ExecutionEvent event) throws ExecutionException {
		//final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Job b = new Job("Model Checker progress") {
			
			@Override
			protected synchronized IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Analysing in FORMULA", 1000);
		         thread.start();
		         int i = 0;
		         while (thread.getStatus() != MCStatus.FINISHED) { // && i <= 80
		        	 try {
		                 Thread.sleep(1000);
		                 i++;
			        	 monitor.worked(i);
		             } catch (InterruptedException e) {} // ignore
		         }
		        if(thread.getStatus() == MCStatus.FINISHED && i < 100){
		        	for(int j = i; j < 100; j++){
		        		try {
			                 Thread.sleep(10);
			                 monitor.worked(j);
			             } catch (InterruptedException e) {} // ignore
		        		
		        	}
		        }
		        monitor.done();
				return Status.OK_STATUS;
			}
		};
		
		b.schedule();
		
		return null;
	}

	public FormulaResult getFormulaResult() throws InterruptedException{
		return thread.getFormulaResult();
	}

}
