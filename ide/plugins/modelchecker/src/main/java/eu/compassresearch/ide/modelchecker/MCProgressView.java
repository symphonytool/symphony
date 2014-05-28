package eu.compassresearch.ide.modelchecker;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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
			ICmlSourceUnit selectedUnit, IResource cmlFile,
			ExecutionEvent event, String analysedProcess, MCHandler uiManager) {
		try {
			window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MCPluginUtility.showModelcheckerPerspective(window);
			this.thread = new MCThread(out, property, mcFolder, selectedUnit,
					cmlFile, window, analysedProcess);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public synchronized void execute() throws ExecutionException {
		Job b = new Job("Model Checker progress") {

			@Override
			protected synchronized IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Analysing in FORMULA", 1000);
				monitor.setCanceled(true);
				thread.start();
				int i = 20;
				monitor.worked(i);
				while (thread.getStatus() != MCStatus.FINISHED && thread.getStatus() != MCStatus.ERROR) { // && i <= 80
					try {
						Thread.sleep(500);
						i += 10;
						monitor.worked(i);
						if(thread.getStatus() == MCStatus.ERROR){
							String msg = "";
							if(thread.getException() != null){
								msg = thread.getException().getMessage();
							} 
						}
					} catch (InterruptedException e) {
					} // ignore
				}
				if (thread.getStatus() == MCStatus.FINISHED && i < 100) {
					for (int j = i; j < 100; j++) {
						try {
							Thread.sleep(10);
							monitor.worked(j);
						} catch (InterruptedException e) {
						} // ignore
					}
				}
				monitor.done();
				if(thread.getStatus() == MCStatus.ERROR){
					exception = thread.getException();
					MCPluginUtility.popErrorMessage(exception);
				}
				
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
