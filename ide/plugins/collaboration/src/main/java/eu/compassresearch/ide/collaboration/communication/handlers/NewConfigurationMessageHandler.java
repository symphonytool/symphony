package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.NewConfigurationMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.Configurations;
import eu.compassresearch.ide.collaboration.files.FileHandler;
import eu.compassresearch.ide.collaboration.files.FileSet;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class NewConfigurationMessageHandler extends
		BaseMessageHandler<NewConfigurationMessage>
{
	public NewConfigurationMessageHandler(MessageProcessor processor)
	{
		super(NewConfigurationMessage.class, processor);
	}

	@Override
	public void process(final NewConfigurationMessage msg)
	{
		final String senderName = msg.getSenderID().getName();

		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();

		// TODO move to collaboration manager
		final CollaborationProject collaborationProject = dataModelManager.getCollaborationProjectFromID(msg.getProjectID());
		if (collaborationProject == null)
		{
			Notification.logError(Notification.Collab_ERROR_NO_SUCH_COLLAB__PROJ_ID
					+ " " + senderName, null);
			// TODO reply with error msg of no such collaboration project id
		}

		// TODO check that parent config is the same as msg.getParentConfigurationUniqueID()
		try
		{
			FileHandler.saveFilesToCollaborationDir(msg.getFileSets(), collaborationProject);

		} catch (CoreException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				Configurations configurations = collaborationProject.getConfigurations();
			
				Configuration newestConfiguration = configurations.getNewestConfiguration();
				Configuration receivedConfiguration = new Configuration(msg.getConfigurationUniqueID(), msg.getTimeStamp(), msg.getSignedBy(), newestConfiguration, configurations);
				configurations.addConfiguration(receivedConfiguration);
				for (FileSet fs : msg.getFileSets())
				{
					receivedConfiguration.addNewFile(fs.getFileName(), fs.getFileHash(), fs.getTimestamp(), fs.getFilePath());
				}
				
				CollaborationDialogs.getInstance().displayNotificationPopup(senderName, Notification.Collab_Dialog_SEND_NEW_CONFIG);
			}
		});

		// TODO send received response
		// FileStatusMessage statusMsg = new FileStatusMessage(fileMsg.getReceiverID(), fileMsg.getSenderID(), filename,
		// NegotiationStatus.RECEIVED, time);

	}
}

// Display.getDefault().asyncExec(new Runnable()
// {
// public void run()
// {
// try
// {
// // boolean recv = MessageDialog.openQuestion(null, "Receiving new file", "Receiving new file: "
// // + filename
// // + " from: "
// // + senderName
// // + "\n Do you want to receive this file?");
// //
// // if (recv)
// // {
// // final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
// // final IViewPart view = page.findView("eu.compassresearch.ide.collaboration.treeview.ui.CollaborationView");
//
// // IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getProject());
// // projectFolder = project.getFolder("Collaboration");
// // IFile file = projectFolder.getFile(filename);
// // // at this point, no resources have been created
// // if (!project.exists())
// // project.create(null);
// // if (!project.isOpen())
// // project.open(null);
// // if (!projectFolder.exists())
// // projectFolder.create(IResource.NONE, true, null);
//
// CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
// CollaborationDataModelRoot root = modelMgm.getDataModel();
//
// Configurations contracts = (Configurations)
// root.getCollaborationProjects().get(0).getConfigurations().getConfigurations();
//
// byte[] bytes = fileContents.getBytes();
// InputStream source = new ByteArrayInputStream(bytes);
//
// String cleanFilename = filename.substring(0, filename.indexOf("."));
// Configuration contract = contracts.getConfiguration(cleanFilename);
// if (contract == null)
// {
// // contract = new Configuration(filename, fileMsg.getSenderID(), fileMsg.getReceiverID());
// //contract.addShare(new Share(senderName));
// //contract.addShare(new Share("You"));
// // contracts.addContract(contract);
//
// } else {
// //file = nextFilename(filename);
// }
//
// //file.create(source, IResource.NONE, null);
//
// //messageProcessor.sendMessage(statusMsg.getReceiverID(), statusMsg.serialize());
//
// // contract.addFile(new File(contract.getFilename() + " Version: " + 1
// // + " Received: " + new Date()));
//
// //IEditorPart openEditor = IDE.openEditor(page, file, true);
// //IEditorInput editorInput = openEditor.getEditorInput();
//
// page.showView("eu.compassresearch.ide.collaboration.treeview.ui.CollaborationView");
// }
//
// } catch (CoreException e)
// {
// MessageDialog.openError(null, "Error", e.toString());
// e.printStackTrace();
// } finally
// {
// }
// }
// });