package eu.compassresearch.ide.collaboration;

import java.util.Hashtable;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ecf.core.IContainerManager;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.user.IUser;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.datashare.IChannelContainerAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationRequestHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationStatusMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.FileStatusMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.NewConfigurationMessageHandler;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.files.FileChangeManager;

public class Activator extends AbstractUIPlugin
{
	public static final String PLUGIN_ID = "eu.compassresearch.ide.collaboration"; 
	
	private BundleContext context;
	private static Activator plugin;
	
	@SuppressWarnings("rawtypes")
	private ServiceTracker containerManagerTracker;
	
	private CollaborationDataModelManager dataModelManager;
	private FileChangeManager fileChangeManager;
	
	private static final Hashtable<ID, MessageProcessor> collaborationProcessors = new Hashtable<ID, MessageProcessor>();
	
	private MessageProcessor messageProcessor;

	//TODO remove when presence container is functional 
	private IUser self;
	private IUser receiver;
	
	public Activator() {
		// nothing to do
	}
	
	public void start(BundleContext ctxt) throws Exception {
		super.start(ctxt);
		plugin = this;
		context = ctxt;
		dataModelManager = new CollaborationDataModelManager();
		
		//Persist data model
		dataModelManager.loadModel();
		
		fileChangeManager = new FileChangeManager();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(fileChangeManager);
	}
	
	@Override
	public void stop(BundleContext context) throws Exception
	{
		if (containerManagerTracker != null) {
			containerManagerTracker.close();
			containerManagerTracker = null;
		}
		
		dataModelManager.saveModel();
	
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.removeResourceChangeListener(fileChangeManager);
		
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
			 loadEcfPresenceActivator();
			return messageProcessor;
		}
			
		return messageProcessor;
	}
	
	private void addMessageHandlers()
	{
		messageProcessor.addMessageHandler(new CollaborationRequestHandler(messageProcessor));	
		messageProcessor.addMessageHandler(new NewConfigurationMessageHandler(messageProcessor));
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	public boolean isConnectionInitialized(){
		return messageProcessor != null;
	}

	public CollaborationDataModelManager getDataModelManager()
	{
		return dataModelManager;
	}
	
	public void loadEcfPresenceActivator(){
		
//		Bundle bundle = Platform.getBundle("org.eclipse.ecf.presence.ui");
//		String activator = (String)bundle.getHeaders().get(Constants.BUNDLE_ACTIVATOR);
//		try
//		{
			//Class activatorClass = bundle.loadClass(activator);
			//org.eclipse.ecf.internal.presence.ui.Activator activator2  = ( org.eclipse.ecf.internal.presence.ui.Activator) activatorClass.getMethod("getDefault").invoke(null);

//		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 		
	}

	//TODO remove when presence container is functional 
	public void setConnections(IUser self, IUser receiver)
	{
		this.self = self;
		this.receiver = receiver;
	}

	public IUser getSelf()
	{
		return self;
	}

	public IUser getReceiver()
	{
		return receiver;
	}
}
