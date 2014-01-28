package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.ConfigurationStatusMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;

public class ConfigurationStatusMessageHandler extends
		BaseMessageHandler<ConfigurationStatusMessage>
{
	public ConfigurationStatusMessageHandler(MessageProcessor processor)
	{
		super(ConfigurationStatusMessage.class, processor);
	}

	@Override
	public void process(ConfigurationStatusMessage msg)
	{
		final ConfigurationStatusMessage statusMsg = (ConfigurationStatusMessage) msg;
		
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
				modelMgm.updateConfigurationStatus(statusMsg.getConfigurationId(), statusMsg.getStatus(), statusMsg.getProjectID());
			}
		});
	}
}
