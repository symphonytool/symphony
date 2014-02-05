package eu.compassresearch.ide.collaboration.distributedsimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.jface.window.Window;

import eu.compassresearch.ide.collaboration.communication.ConnectionManager;
import eu.compassresearch.ide.collaboration.communication.messages.SimulationReplyMessage;
import eu.compassresearch.ide.collaboration.communication.messages.SimulationRequestMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.User;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;
import eu.compassresearch.ide.collaboration.ui.menu.DistributedSimulationInitialisationDialog;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;

public class DistributedSimulationManager 	
{
	private List<DistributedSimulationConfiguration> configurations;
	private Map<String, List<ISimulationListener>> listeners;
	private CollaborationDataModelManager dataModelManager;
	private ConnectionManager connectionManager;
	private CollaborationProject projectForcurrentSession;
	private DistributedSimulationInitialisationDialog distSimDlg;
	
	public DistributedSimulationManager(CollaborationDataModelManager dataModelManager, ConnectionManager connectionManager)
	{
		this.dataModelManager = dataModelManager;
		this.connectionManager = connectionManager;
		configurations = new ArrayList<>();
		listeners = new HashMap<String, List<ISimulationListener>>();
	}
	
	public void addConfiguration(DistributedSimulationConfiguration distributedConfiguration, ISimulationListener notify)
	{
		configurations.add(distributedConfiguration);
		
		String collaborator = distributedConfiguration.getCollaborator();
		addListener(collaborator, notify);
	}
	
	public void addListener(String collaborator, ISimulationListener notify){
		if(!listeners.containsKey(collaborator)){
			
			listeners.put(collaborator, new ArrayList<ISimulationListener>());
		}
		listeners.get(collaborator).add(notify);
	}
	
	public void removeListener(ISimulationListener notify){
		
		//TODO remove on collaborator name
		
	}
	
	public void requestSimulationFromCollaborators(){
		
		CollaborationGroup collaboratorGroup = projectForcurrentSession.getCollaboratorGroup();
		String uniqueID = projectForcurrentSession.getUniqueID();
		final ID sender = connectionManager.getConnectedUser();
		
		for (DistributedSimulationConfiguration config : configurations)
		{
			User user = collaboratorGroup.getUser(config.getCollaborator());
			SimulationRequestMessage simRequest = new SimulationRequestMessage(sender, uniqueID, config.getProcess());
			
			if(user != null){
				connectionManager.sendTo(user, simRequest);
				
				//TODO
				//attach listener this to manager
			}
		}
	}
	
	public void initiateCollaborationOnProject(CollaborationProject project) {
		
		projectForcurrentSession = project;
		String projectWorkspaceName = project.getProjectWorkspaceName();
		List<User> joinedCollaborators = project.getCollaboratorGroup().getJoinedCollaborators();
		
		//load collaborators
		ArrayList<String> collaborators = new ArrayList<>();
		for (User user : joinedCollaborators)
		{
			collaborators.add(user.getName());
		}
		
		//load processes
		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectWorkspaceName);
		if(iProject == null) {
			return ;
		}
		
		//TODO load processes
		ICmlProject cmlProj = (ICmlProject) iProject.getAdapter(ICmlProject.class);
		ICmlModel model = cmlProj.getModel();
		//CmlUtil.getGlobalProcessesFromSource(definitions)
		
		//TODO get processes from project
		ArrayList<String> processes = new ArrayList<>();
		processes.add("A");
		processes.add("B");

		distSimDlg = CollaborationDialogs.getInstance().getDistributedSimulationInitialisationDialog(processes, collaborators, this);

		if(distSimDlg.open() == Window.OK){
			System.out.println("a");
		}
		
		//TODO unsubscribe
		
		//if accept
		//distSimDlg.getConfigurations();
		
		startDistributedSimulation(project);
	}
	
	public void startDistributedSimulation(CollaborationProject project){
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType ctype = launchManager.getLaunchConfigurationType(ICmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE);

//		try
//		{
//			ILaunchConfigurationWorkingCopy lcwc = ctype.newInstance(null, launchManager.generateLaunchConfigurationName(project.getName()));
//			
//			//lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, processName)
//			
//			//project
//			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, project.getName());
//			
//			//launch mode
//			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, true);
//			//is coordinator 
//			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_IS_COORDINATOR, true);
//			//lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_EXTERNAL_PROCESSES, encodeArrayAsCoommaSeperatedString(null /* list */));
//			
//			//lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_HOST, hostText.getText());
//			
//			
//			DebugUITools.launch(lcwc, "debug");
//			
//		} catch (CoreException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		// lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, processName);
		
//		<?xml version="1.0" encoding="UTF-8" standalone="no"?>
//		<launchConfiguration type="eu.compassresearch.ide.interpreter.launchConfigurationTypeCoSim">
//		<stringAttribute key="cml_launch_config_cosim_external_processes" value=""/>
//		<stringAttribute key="cml_launch_config_cosim_host" value="localhost:8882"/>
//		<booleanAttribute key="cml_launch_config_cosim_is_coordinator" value="true"/>
//		<booleanAttribute key="cml_launch_config_enable_logging" value="false"/>
//		<stringAttribute key="cml_launch_config_process_name" value="P"/>
//		<stringAttribute key="cml_launch_config_project" value="EmergencyMgm"/>
//		<booleanAttribute key="cml_launch_config_remote_debug" value="false"/>
//		<booleanAttribute key="eu.compassresearch.ide.interpreter.is_animation" value="true"/>
//		</launchConfiguration>
	}

	public void approveSimulation(String user, CollaborationProject collaborationProject,
			String process)
	{
		User userObj = collaborationProject.getCollaboratorGroup().getUser(user);
		final ID sender = connectionManager.getConnectedUser();
		
		SimulationReplyMessage replyMsg = new SimulationReplyMessage(sender, collaborationProject.getUniqueID(), process, true);
		
		connectionManager.sendTo(userObj, replyMsg);
	}

	public void receivedReply(String user, String projectID, String process,
			boolean accepted)
	{
		if(listeners.containsKey(user)){
			List<ISimulationListener> notifyList = listeners.get(user);
			
			for (ISimulationListener notify : notifyList)
			{
				notify.onMsgUpdated(process, accepted);
			}
		}
	}
}
