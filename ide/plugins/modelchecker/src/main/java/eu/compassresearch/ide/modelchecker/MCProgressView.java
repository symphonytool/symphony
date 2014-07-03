package eu.compassresearch.ide.modelchecker;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.ExtensionFactory;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

public class MCProgressView extends ExtensionFactory {

	private MCThread thread;
	private IWorkbenchWindow window;
	private Throwable exception;
	
	public MCProgressView() {
		super();
	}

	public MCProgressView(IFile out, String property, IFolder mcFolder,
			ExecutionEvent event, String analysedProcess, MCHandler uiManager) {
		try {
			window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MCPluginUtility.showModelcheckerPerspective(window);
			this.thread = new MCThread(out, property, mcFolder, window, analysedProcess);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public synchronized void execute() throws ExecutionException {
		Job b = new Job("Model Checker progress") {

			@Override
			protected synchronized IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Analysing in FORMULA", 1000);
				thread.start();
				int i = 20;
				monitor.worked(i);
				while (thread.getStatus() != MCStatus.FINISHED) { 
					if (monitor.isCanceled()) {
						thread.cancelExecution();
						return Status.CANCEL_STATUS;
					}
					try {
						//the status of the job has to be checked continuously to re-start the thread
						Thread.sleep(400);
						i = i + 10;
						if(i < 800 && thread.getStatus() != MCStatus.FINISHED){
							monitor.worked(i);
						}
						
					} catch (InterruptedException e) {
						
					} // ignore
				}
				
				monitor.done();
								
				return Status.OK_STATUS;
			}
		};
		
		b.schedule();
		
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	
}
