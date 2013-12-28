package eu.compassresearch.ide.collaboration.communication.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.communication.messages.NewFileMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.Contract;
import eu.compassresearch.ide.collaboration.datamodel.Contracts;
import eu.compassresearch.ide.collaboration.datamodel.Version;
import eu.compassresearch.ide.collaboration.ui.view.CollaborationView;

public class NewFileHandler extends BaseMessageHandler<NewFileMessage>
{
	public NewFileHandler(
			MessageProcessor processor)
	{
		super(NewFileMessage.class, processor);
	}

	@Override
	public void process(NewFileMessage msg)
	{
		final NewFileMessage fileMsg = (NewFileMessage) msg;
		final String filename = fileMsg.getFilename();
		final String fileContents = fileMsg.getContents();
		final String senderName = fileMsg.getSenderID().getName();
		final Date time = new Date();
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				try
				{
					boolean recv = MessageDialog.openQuestion(null, "Receiving new file", "Receiving new file: "
							+ filename
							+ " from: "
							+ senderName
							+ "\n Do you want to receive this file?");

					if (recv)
					{
						final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						final IViewPart view = page.findView("eu.compassresearch.ide.collaboration.treeview.ui.CollaborationView");

//						IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getProject());
//						projectFolder = project.getFolder("Collaboration");
//						IFile file = projectFolder.getFile(filename);
//						// at this point, no resources have been created
//						if (!project.exists())
//							project.create(null);
//						if (!project.isOpen())
//							project.open(null);
//						if (!projectFolder.exists())
//							projectFolder.create(IResource.NONE, true, null);
						
						CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
						CollaborationDataModelRoot root = modelMgm.getDataModel();
						
						Contracts contracts = (Contracts) root.getCollaborationProjects().get(0).getContracts().getContracts();
						
						byte[] bytes = fileContents.getBytes();
						InputStream source = new ByteArrayInputStream(bytes);
						
						String cleanFilename = filename.substring(0, filename.indexOf(".")); 
						Contract contract = contracts.getContract(cleanFilename);
						if (contract == null)
						{
							contract = new Contract(filename, fileMsg.getSenderID(), fileMsg.getReceiverID());
							//contract.addShare(new Share(senderName));
							//contract.addShare(new Share("You"));
							contracts.addContract(contract);

						} else {
							//file = nextFilename(filename);
						}
						
						//file.create(source, IResource.NONE, null);

						//FileStatusMessage statusMsg = new FileStatusMessage(fileMsg.getReceiverID(), fileMsg.getSenderID(), filename, NegotiationStatus.RECEIVED, time);
						//messageProcessor.sendMessage(statusMsg.getReceiverID(), statusMsg.serialize());

						contract.addVersion(new Version(contract.getFilename() + " Version: " + 1
								+ " Received: " + new Date()));
						
						//IEditorPart openEditor = IDE.openEditor(page, file, true);
						//IEditorInput editorInput = openEditor.getEditorInput();

						page.showView("eu.compassresearch.ide.collaboration.treeview.ui.CollaborationView");
					}

				} catch (CoreException e)
				{
					MessageDialog.openError(null, "Error", e.toString());
					e.printStackTrace();
				} finally
				{
				}
			}
		});
	}
}
