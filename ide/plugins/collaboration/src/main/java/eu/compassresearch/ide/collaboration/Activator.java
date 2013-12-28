package eu.compassresearch.ide.collaboration;

import java.util.Hashtable;

import org.eclipse.ecf.core.IContainerManager;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.datashare.IChannelContainerAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationRequestHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationStatusMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.FileStatusMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.NewFileHandler;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;

public class Activator extends AbstractUIPlugin
{
	public static final String PLUGIN_ID = "eu.compassresearch.ide.collaboration"; 
	
	private BundleContext context;
	private static Activator plugin;
	
	private ServiceTracker containerManagerTracker;
	
	private CollaborationDataModelManager dataModelManager;
	
	private static final Hashtable<ID, MessageProcessor> collaborationProcessors = new Hashtable<ID, MessageProcessor>();
	
	private MessageProcessor messageProcessor;
	
	public Activator() {
		// nothing to do
	}
	
	public void start(BundleContext ctxt) throws Exception {
		super.start(ctxt);
		plugin = this;
		context = ctxt;
		dataModelManager = new CollaborationDataModelManager();
		
		//Persist data model
		//dataModelManager.loadModel();
		
	}
	
	@Override
	public void stop(BundleContext context) throws Exception
	{
		if (containerManagerTracker != null) {
			containerManagerTracker.close();
			containerManagerTracker = null;
		}
		
		//dataModelManager.saveModel();
		
		plugin = null;
		this.context = null;
		dataModelManager = null;
		
		super.stop(context);
	}
	
	public MessageProcessor addMessageProcessor(ID containerID, IChannelContainerAdapter channelAdapter) throws ECFException {

		messageProcessor = collaborationProcessors.get(containerID);
		if (messageProcessor == null){
			messageProcessor = new MessageProcessor(channelAdapter);
			collaborationProcessors.put(containerID, messageProcessor);
			
			addMessageHandlers();
			
			return messageProcessor;
		}
			
		return messageProcessor;
	}
	
	private void addMessageHandlers()
	{
		messageProcessor.addMessageHandler(new CollaborationRequestHandler(messageProcessor));	
		messageProcessor.addMessageHandler(new NewFileHandler(messageProcessor));
		messageProcessor.addMessageHandler(new FileStatusMessageHandler(messageProcessor));
		messageProcessor.addMessageHandler(new CollaborationStatusMessageHandler(messageProcessor));
	}

	public void removeCollaborationManager(ID containerID){
		MessageProcessor collabMgm = collaborationProcessors.remove(containerID);
		if(collabMgm != null ){
			collabMgm.dispose();
		}
	}
	
	public MessageProcessor getMessageProcessor(ID containerID) {
		return collaborationProcessors.get(containerID);
	}
	
	public IContainerManager getContainerManager() {
		if (containerManagerTracker == null) {
			containerManagerTracker = new ServiceTracker(context, IContainerManager.class.getName(), null);
			containerManagerTracker.open();
		}
		return (IContainerManager) containerManagerTracker.getService();
	}
	
	public static Activator getDefault() {
		return plugin;
	}

	public MessageProcessor getMessageProcessor()
	{
		return messageProcessor;
	}

	public CollaborationDataModelManager getDataModelManager()
	{
		return dataModelManager;
	}
}
