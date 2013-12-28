package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.Contract;
import eu.compassresearch.ide.collaboration.datamodel.Contracts;
import eu.compassresearch.ide.collaboration.datamodel.Version;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;
import eu.compassresearch.ide.collaboration.ui.view.CollaborationView;

public class FileStatusMessageHandler extends BaseMessageHandler<FileStatusMessage>
{

	public FileStatusMessageHandler(MessageProcessor processor)
	{
		super(FileStatusMessage.class, processor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(FileStatusMessage msg)
	{
		final FileStatusMessage statusMsg = (FileStatusMessage) msg;
		final String filename = statusMsg.getFilename();
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
					NegotiationStatus status = statusMsg.getStatus();
					if(status == NegotiationStatus.RECEIVED) {
					//MessageDialog.openInformation(null, "File received", statusMsg.getSenderID().getName() + " received " + statusMsg.getFilename());
					
					CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
					CollaborationDataModelRoot root = modelMgm.getDataModel();

					String senderName = statusMsg.getSenderID().getName();
					Contracts contracts = root.getCollaborationProjects().get(0).getContracts();
					
					String cleanFilename = filename.substring(0, filename.indexOf(".")); 
					Contract contract = contracts.getContract(cleanFilename);
					if (contract == null)
					{
						contract = new Contract(statusMsg.getFilename(), statusMsg.getSenderID(), statusMsg.getReceiverID());
						//contract.addShare(new Share(senderName));
						//contract.addShare(new Share("You"));
						contracts.addContract(contract);
					}
					
					contract.addVersion(new Version(contract.getFilename()
							+ " Received by " + senderName + " on "
							+ statusMsg.getTimestamp()));

					
					CollaborationDialogs.showCollaborationView();
					
					} else if (status == NegotiationStatus.ACCEPT || status == NegotiationStatus.REJECT) {
						
						CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
						CollaborationDataModelRoot root = modelMgm.getDataModel();
						
						Contracts cs = (Contracts) root.getCollaborationProjects().get(0).getContracts();
						Contract c = (Contract) cs.getContracts().get(0);
						c.setStatus(status);
					}

			}
		});
	}
}
