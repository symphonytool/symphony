package eu.compassresearch.ide.collaboration.ui.menu;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.presence.ui.MultiRosterView;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.ui.view.CollaborationView;

public class CollaborationDialogs
{
	protected static CollaborationDialogs soleInstance = new CollaborationDialogs();
	public static CollaborationDialogs getInstance() {
		return soleInstance;
	}
	
	public static void displayErrorDialog(final String errorMessage, final String reason) {
		
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
					ErrorDialog.openError(null, "Message", errorMessage, new Status(IStatus.ERROR, Activator.PLUGIN_ID, reason, null));
			}
		});
	}
	
	public void displayNotificationPopup(String username, String message) {
		
		CollaborationView collaborationView = getCollaborationView();
		
		if(collaborationView == null) return;
		
		final CollaborationNotificationPopUp popup = new CollaborationNotificationPopUp(getWorkbench(), collaborationView);
	
		popup.setContent(username, message);
		popup.open();
		
		new UIJob("Close Popup Job") { 
			public IStatus runInUIThread(IProgressMonitor monitor) {
				Shell shell = popup.getShell();
				if (shell != null && !shell.isDisposed()) {
					popup.close();
				}
				return Status.OK_STATUS;
			}
		}.schedule(8000);
	}
	
	public NewCollaborationProjectDialog getCollaborationProjectDialog(List<CollaborationProject> collabProjects)
	{
		NewCollaborationProjectDialog collabReqDia = new NewCollaborationProjectDialog(collabProjects, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		collabReqDia.create();
		
		return collabReqDia;
	}
	
	public CollaborationRequestDialog getCollaborationRequestDialog(String projectTitle, String receiverName){
		return new CollaborationRequestDialog(projectTitle, receiverName, null);
	}
	

	public AddFileLimitedVisibilityDialog getAddFileLimitedVisibilityDialog(String fileName,  CollaborationProject collaborationProject)
	{
		return new AddFileLimitedVisibilityDialog(fileName, collaborationProject, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
	} 
	
	public static IWorkbenchWindow getWorkbench()
	{
		final IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		return workbenchWindow;
	}
	
	public static CollaborationView getCollaborationView(){
		
		IWorkbenchWindow workbench = getWorkbench();
		if(workbench == null) return null;
		
		IWorkbenchPage page = getWorkbench().getActivePage();
		if(page == null) return null;
		
		IViewPart view = page.findView("eu.compassresearch.ide.collaboration.ui.view.CollaborationView");
		CollaborationView collabview = (CollaborationView) view;
		
		return collabview;
	} 
	
	public static void showCollaborationView(){
		IWorkbenchPage page = getWorkbench().getActivePage();
		try
		{
			page.showView("eu.compassresearch.ide.collaboration.ui.view.CollaborationView");
		} catch (PartInitException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
