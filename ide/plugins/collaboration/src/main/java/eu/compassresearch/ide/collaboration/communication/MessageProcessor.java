package eu.compassresearch.ide.collaboration.communication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.user.IUser;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.datashare.AbstractShare;
import org.eclipse.ecf.datashare.IChannelContainerAdapter;
import org.eclipse.ecf.sync.SerializationException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.progress.UIJob;

import eu.compassresearch.ide.collaboration.communication.messages.BaseMessage;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationRequest;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationStatusMessage;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.communication.messages.NewFileMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.Contract;
import eu.compassresearch.ide.collaboration.datamodel.Contracts;
import eu.compassresearch.ide.collaboration.datamodel.User;
import eu.compassresearch.ide.collaboration.datamodel.Version;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationNotificationPopUp;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationRequestedDialog;
import eu.compassresearch.ide.collaboration.ui.view.CollaborationView;


public class MessageProcessor extends AbstractShare
{
	private String project;
	private int versionNrDummy = 1;
	private IFolder projectFolder;

	List<IMessageHandler> msgHandlers;
	
	public MessageProcessor(IChannelContainerAdapter adapter)
			throws ECFException
	{
		super(adapter);
		
		msgHandlers = new ArrayList<IMessageHandler>();
	}

	public void addMessageHandler(IMessageHandler messageHandler)
	{
		msgHandlers.add(messageHandler);
	}
	
	@Override
	protected void handleMessage(ID fromContainerID, byte[] data)
	{
		try
		{
			final BaseMessage msg = BaseMessage.deserialize(data);
			Assert.isNotNull(msg);
			
			for (IMessageHandler msgHndlr : msgHandlers)
			{
				if(msgHndlr.canHandleMessage(msg)){
					msgHndlr.handleMessage(msg);
				}
			}

		} catch (final SerializationException | InvalidObjectException e)
		{
			Notification.logError(Notification.Collaboration_ERROR_HANDLE_MSG_EXCEPTION_CAUGHT, e);
		}
	}

	public synchronized void dispose()
	{
		super.dispose();
	}
	
	public synchronized void sendMessage(IUser toUser, byte[] data)
	{
		try
		{
			this.sendMessage(toUser.getID(), data);
		} catch (ECFException e)
		{
			CollaborationDialogs.displayErrorDialog(Notification.ECF_ERROR_COULD_NOT_SEND);
			e.printStackTrace();
		}
	}

	public String getProject()
	{
		return project;
	}

	public void setProject(String project)
	{
		this.project = project;
	}
	
	public IFolder getProjectFolder()
	{
		return projectFolder;
	}

	public IFile nextFilename(String filename)
	{
		return projectFolder.getFile(filename.substring(0, filename.indexOf('.')) + "." + ++versionNrDummy + ".cml");
	}
}
