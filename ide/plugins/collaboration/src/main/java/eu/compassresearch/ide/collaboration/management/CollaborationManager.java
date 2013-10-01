package eu.compassresearch.ide.collaboration.management;

import java.io.InvalidObjectException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.datashare.AbstractShare;
import org.eclipse.ecf.datashare.IChannelContainerAdapter;
import org.eclipse.ecf.sync.IModelChangeMessage;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.messages.NewFileMessage;
import eu.compassresearch.ide.collaboration.messages.TestMessage;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class CollaborationManager extends AbstractShare
{

	private ID ownID;
	
	public CollaborationManager(IChannelContainerAdapter adapter)
			throws ECFException
	{
		super(adapter);
	}


	@Override
	protected void handleMessage(ID fromContainerID, byte[] data)
	{
		try {
			final IModelChangeMessage msg = TestMessage.deserialize(data);
			Assert.isNotNull(msg);
			
			if(msg instanceof TestMessage){
				TestMessage recvMsg = (TestMessage)msg;
				final String recvString = "Received message: " + recvMsg.getData() + " from " + recvMsg.getFromUsername() + "(" + recvMsg.getSenderID() +")" ;
				
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						try {
				
							ErrorDialog.openError(null, "Message", recvString, new Status(IStatus.ERROR, Activator.PLUGIN_ID, recvString, null));
							
						} finally {}
					}
				});
					
			} else if(msg instanceof NewFileMessage){
				
				NewFileMessage fileMsg = (NewFileMessage)msg;
				final String filename = fileMsg.getFilename();
				
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						try {
								 MessageDialog.openQuestion(null, "Receiving new file" + filename, "Do Or not");
							
						} finally {}
					}
				});
				
				
			} else {
				throw new InvalidObjectException(Notification.Collaboration_ERROR_UNKNOWN_MESSAGE_TYPE + msg.getClass().getName());
			}
			
		} catch (final Exception e) {
			Notification.logError(Notification.Collaboration_ERROR_HANDLE_MSG_EXCEPTION_CAUGHT,e);
		}
	}
	
	public synchronized void dispose() {
		super.dispose();
	}

}
