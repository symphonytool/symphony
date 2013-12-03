package eu.compassresearch.ide.collaboration;

import java.util.Hashtable;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ecf.core.IContainerManager;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.datashare.IChannelContainerAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import eu.compassresearch.ide.collaboration.communication.dispatch.MessageProcessor;

public class Activator extends AbstractUIPlugin
{

	public static final String PLUGIN_ID = "eu.compassresearch.ide.collaboration"; 
	
	private BundleContext context;
	private static Activator plugin;
	
	private ServiceTracker containerManagerTracker;
	
	private static final Hashtable<ID, MessageProcessor> collaborationChannels = new Hashtable<ID, MessageProcessor>();
	
	private MessageProcessor collabMgm;
	
	public Activator() {
		// nothing to do
	}
	
	public void start(BundleContext ctxt) throws Exception {
		super.start(ctxt);
		plugin = this;
		context = ctxt;
	}
	
	@Override
	public void stop(BundleContext context) throws Exception
	{
		if (containerManagerTracker != null) {
			containerManagerTracker.close();
			containerManagerTracker = null;
		}
		
		plugin = null;
		this.context = null;
		
		super.stop(context);
	}
	
	public MessageProcessor addCollaborationManager(ID containerID, IChannelContainerAdapter channelAdapter) throws ECFException {

		collabMgm = collaborationChannels.get(containerID);
		if (collabMgm == null){
			collabMgm = new MessageProcessor(channelAdapter);
			collaborationChannels.put(containerID, collabMgm);
			return collabMgm;
		}
			
		return collabMgm;
	}
	
	public void removeCollaborationManager(ID containerID){
		MessageProcessor collabMgm = collaborationChannels.remove(containerID);
		if(collabMgm != null ){
			collabMgm.dispose();
		}
	}
	
	public MessageProcessor getCollaborationManager(ID containerID) {
		return collaborationChannels.get(containerID);
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

	public MessageProcessor getCollaborationManager()
	{
		return collabMgm;
	}
	
}
