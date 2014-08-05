package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.ConfigurationStatusMessage;
import eu.compassresearch.ide.collaboration.communication.messages.ConfigurationStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.ConfigurationStatus.ConfigurationNegotiationStatus;

public class ConfigurationStatusMessageHandler extends
		BaseMessageHandler<ConfigurationStatusMessage>
{
	public ConfigurationStatusMessageHandler()
	{
		super(ConfigurationStatusMessage.class);
	}

	@Override
	public void process(ConfigurationStatusMessage msg)
	{
		final ConfigurationStatusMessage statusMsg = msg;

		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();

				ConfigurationNegotiationStatus status = convertMessageStatus(statusMsg.getStatus());

				modelMgm.updateConfigurationStatus(statusMsg.getSenderID(), statusMsg.getConfigurationId(), status, statusMsg.getProjectID());
			}
		});
	}
	
	private ConfigurationNegotiationStatus convertMessageStatus(
			NegotiationStatus status)
	{
		switch (status)
		{
			case ACCEPT:
				return ConfigurationNegotiationStatus.ACCEPT;
			case REJECT:
				return ConfigurationNegotiationStatus.REJECT;
			case RENEGOTIATED:
				return ConfigurationNegotiationStatus.RENEGOTIATED;
			default:
				return ConfigurationNegotiationStatus.NOTSET;
		}
	}
}
