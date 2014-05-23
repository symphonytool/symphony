package eu.compassresearch.ide.collaboration.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.user.IUser;
import org.eclipse.ecf.sync.SerializationException;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.CollaborationPluginUtils;
import eu.compassresearch.ide.collaboration.communication.ConnectionManager;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationGroupUpdateMessage;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationRequest;
import eu.compassresearch.ide.collaboration.communication.messages.ConfigurationStatusMessage;
import eu.compassresearch.ide.collaboration.communication.messages.ConfigurationStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.communication.messages.NewConfigurationMessage;
import eu.compassresearch.ide.collaboration.datamodel.ConfigurationStatus.ConfigurationNegotiationStatus;
import eu.compassresearch.ide.collaboration.files.FileComparison;
import eu.compassresearch.ide.collaboration.files.FileDTO;
import eu.compassresearch.ide.collaboration.files.FileHandler;
import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.files.FileStatus.FileState;
import eu.compassresearch.ide.collaboration.files.FileUpdate;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class CollaborationDataModelManager
{
	private CollaborationDataModelRoot datamodel;

	public CollaborationDataModelManager()
	{
		datamodel = new CollaborationDataModelRoot();
	}

	public CollaborationDataModelRoot getDataModel()
	{
		return datamodel;
	}

	public void addCollaborationProject(String project, String title,
			String description)
	{
		datamodel.addCollaborationProject(project, title, description);
	}
	
	public void addReceivedCollaborationProject(String project, String title,
			String description, String collabProjectId)
	{
		datamodel.addCollaborationProject(project, title, description, collabProjectId);
	}

	public List<CollaborationProject> getExistingProjects()
	{
		return datamodel.getCollaborationProjects();
	}

	public CollaborationProject getCollaborationProject(
			String workspaceProjectName)
	{
		ArrayList<CollaborationProject> collaborationProjects = datamodel.getCollaborationProjects();

		for (CollaborationProject collaborationProject : collaborationProjects)
		{
			if (collaborationProject.getProjectWorkspaceName().equals(workspaceProjectName))
				return collaborationProject;
		}

		return null;
	}

	public CollaborationProject getCollaborationProjectFromID(String id)
	{
		return datamodel.getCollaborationProjectFromID(id);
	}

	public FileStatus addFileWithLimitedVisibility(IFile file,
			List<String> collaboratorNames)
	{
		// handle file normally
		FileStatus fileStatus = handleFile(file);

		// now limit scope of file
		if (fileStatus.getState() == FileState.ADDED)
		{
			String projectName = file.getProject().getName();
			CollaborationProject collaborationProject = getCollaborationProject(projectName);

			Configuration newestConfiguration = collaborationProject.getNewestConfiguration();
			File addedFile = newestConfiguration.getFile(file.getName());

			for (String name : collaboratorNames)
			{
				addedFile.addVisibility(name);
			}
		}

		return fileStatus;
	}

	public FileStatus handleFile(IFile file)
	{
		if(file == null) return null;
		
		// create initial filestatus with file hash
		FileStatus fileStatus = createFileStatus(file);

		// find project
		String projectName = file.getProject().getName();
		CollaborationProject collaborationProject = getCollaborationProject(projectName);
		if (collaborationProject == null)
		{
			fileStatus.setState(FileState.ERROR);
			return fileStatus;
		}

		fileStatus = collaborationProject.getFileStatus(fileStatus);

		// new or changed file
		if (fileStatus.getState() != FileState.UNCHANGED)
		{
			Configurations configurations = collaborationProject.getConfigurations();
			Configuration newestConfiguration = configurations.getNewestConfiguration();

			// new configuration if none exists or if it is not open
			if (newestConfiguration == null || newestConfiguration.isShared()
					|| newestConfiguration.isReceived())
			{
				collaborationProject.addNewConfiguration();
			}

			// TODO
			// if received but unapproved configuration
			// ?? how does getFileStatus behave ??
			// ?? add temporary configuration on top of unapproved configuration ??
			// ?? How to do merge ??
			// ?? or prompt user with what to do, to approved the previous ??

			try
			{
				if (fileStatus.getState() == FileState.ADDED) // if the file is new, add it to the tree
				{
					collaborationProject.addNewFile(fileStatus);
				} else if (fileStatus.getState() == FileState.CHANGED) // else update its state and hash
				{
					FileUpdate fileUdate = new FileUpdate(fileStatus.getFileName(), fileStatus.getHash());
					collaborationProject.updateFile(fileUdate);
				}

			} catch (CoreException e)
			{
				e.printStackTrace();
				ResourcesPlugin.getPlugin().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, e.toString(), null));
			}
		}

		return fileStatus;
	}

	private FileStatus createFileStatus(IFile file)
	{
		String hash = FileHandler.calculateSha(file);
		FileStatus fileStatus = new FileStatus(file.getName(), hash, file.getProjectRelativePath().toString());
		return fileStatus;
	}

	public IFile getFile(File file, CollaborationProject collaborationProject)
			throws CoreException, IOException
	{
		IFile iFile = null;

		// has file already been stored previously, get it from the collaboration dir
		if (file.isStored())
		{
			iFile = FileHandler.loadFileFromCollaborationDir(file, collaborationProject);
		} else
		{
			// otherwise try to get it directly from the project
			iFile = FileHandler.loadFileFromProject(file, collaborationProject);
		}

		return iFile;
	}

	public boolean hasCollaborationProjects()
	{
		return !datamodel.getCollaborationProjects().isEmpty();
	}

	public boolean isKnownFile(IFile file)
	{
		FileStatus fileStatus = createFileStatus(file);

		String projectName = file.getProject().getName();
		CollaborationProject collaborationProject = getCollaborationProject(projectName);

		if (collaborationProject != null)
		{
			fileStatus = collaborationProject.getFileStatus(fileStatus);
			return fileStatus.getState() != FileState.ADDED;
		} else
		{
			return false;
		}
	}

	public void signAndShareConfiguration(Configuration config)
			throws CoreException
	{
		List<User> sendTo;
		if (config.hasLimitedVisibility())
		{
			sendTo = shareWithLimitedVisibility(config);
		} else
		{
			sendTo = shareWithAll(config);
		}
		
		ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
		ID connectedUser = connectionManager.getConnectedUser();
		
		config.addSentTo(sendTo);
		
		config.setSignedBy(connectionManager.getConnectedUser().getName());
		config.setConfigurationShared();
	}

	private List<User> shareWithAll(Configuration config)
			throws SerializationException
	{
		CollaborationProject collaborationProject = config.getCollaborationProject();
		ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();

		List<FileDTO> filesToSend = new ArrayList<FileDTO>();

		List<File> filesInConfiguration = config.getFiles().getFilesList();
		// run over files in configuration to create the DTOs needed for sending
		for (File file : filesInConfiguration)
		{
			FileDTO fileDTO = createFileDTO(file);
			filesToSend.add(fileDTO);
		}

		// there is no limitation on visibility so send to all
		CollaborationGroup collaboratorGroup = collaborationProject.getCollaboratorGroup();
		List<User> sendTo = collaboratorGroup.getJoinedCollaborators();
		NewConfigurationMessage newConfigurationMessage = new NewConfigurationMessage(connectionManager.getConnectedUser(), collaborationProject.getUniqueID(), config, filesToSend, sendTo);
		connectionManager.sendToAll(newConfigurationMessage, collaborationProject);
		
		return new ArrayList<User>(sendTo);
	}

	private List<User> shareWithLimitedVisibility(Configuration config)
			throws SerializationException
	{
		CollaborationProject collaborationProject = config.getCollaborationProject();
		ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();

		List<FileDTO> filesToAll = new ArrayList<FileDTO>();
		Map<String, List<FileDTO>> filesToSpecificCollaborators = new HashMap<String, List<FileDTO>>();
		List<FileDTO> changedFiles = new ArrayList<FileDTO>();
		Map<User, NewConfigurationMessage> configurationsToSend = new HashMap<User, NewConfigurationMessage>();

		boolean sendToAll = false; // has a file changed that needs to be send to all?
		List<File> filesInConfiguration = config.getFiles().getFilesList();
		// run over files in configuration to figure out what has changed, and what has a limited visibility
		for (File file : filesInConfiguration)
		{
			FileDTO fileDTO = createFileDTO(file);

			// find the files that has changed
			if (file.isNewFile() || file.isUpdatedFile())
			{
				changedFiles.add(fileDTO);
			}

			// some files are only to be send to certain collaborators, so divide
			if (file.isVisibilityLimited())
			{
				for (String collaborator : file.getVisibilityList())
				{
					if (!filesToSpecificCollaborators.containsKey(collaborator))
					{
						filesToSpecificCollaborators.put(collaborator, new ArrayList<FileDTO>());
					}
					// for each collaborator add the DTO
					filesToSpecificCollaborators.get(collaborator).add(fileDTO);
				}
			} else
			{
				// if files with no limitation on visibility has changed, we need to send these files to all.
				if (file.isNewFile() || file.isUpdatedFile())
				{
					sendToAll = true;
				}
				filesToAll.add(fileDTO);
			}
		}

		List<User> collaborators = collaborationProject.getCollaboratorGroup().getJoinedCollaborators();
		// figure out what to send to who
		for (User user : collaborators)
		{
			NewConfigurationMessage newConfigurationMessage = new NewConfigurationMessage(connectionManager.getConnectedUser(), collaborationProject.getUniqueID(), config);
			List<FileDTO> filesToCollaborator = filesToSpecificCollaborators.get(user.getName());

			if (sendToAll)
			{
				// a file with no limitation on visibility has also changed, so we need to send to all
				newConfigurationMessage.addFiles(filesToAll);
				newConfigurationMessage.addFiles(filesToCollaborator);
			} else if (!filesToCollaborator.isEmpty())
			{
				// has any files specific for this collaborator been changed
				List<FileDTO> intersect = new ArrayList<FileDTO>(changedFiles);
				intersect.retainAll(filesToCollaborator);

				// only send if there are any changes
				if (!intersect.isEmpty())
				{
					newConfigurationMessage.addFiles(filesToAll);
					newConfigurationMessage.addFiles(filesToCollaborator);
				}
			}

			//only add if there are changes 
			if(!newConfigurationMessage.getFileDTOs().isEmpty()){
				// store our configuration. 
				configurationsToSend.put(user, newConfigurationMessage);
			}
		}
		
		//send 
		Set<Entry<User, NewConfigurationMessage>> entrySet = configurationsToSend.entrySet();
		for (Entry<User, NewConfigurationMessage> entry : entrySet)
		{
			connectionManager.sendTo(entry.getKey(), entry.getValue());
		}
		
		 return new ArrayList<User>(configurationsToSend.keySet());
	}

	private FileDTO createFileDTO(File file)
	{
		// create DTO for sending the file info and content
		String content = "";
		try
		{
			content = FileHandler.persistFileAndRetrieveContents(file);
		} catch (IOException | CoreException e)
		{
			e.printStackTrace();
			Notification.logError(e.toString(), e);
		}
		FileDTO fileDTO = new FileDTO(file, content);

		return fileDTO;
	}

	public ConfigurationComparison compareConfigurationWithPrev(
			Configuration targetConfiguration)
	{
		ConfigurationComparison compare = compareConfigurations(targetConfiguration, targetConfiguration.getParentConfiguration());

		return compare;
	}

	private ConfigurationComparison compareConfigurations(Configuration target,
			Configuration compareTo)
	{
		if (target == null || compareTo == null)
		{
			return null;
		}

		ConfigurationComparison configComparison = new ConfigurationComparison();

		Files targetFiles = target.getFiles();
		List<File> targetFilesList = targetFiles.getFilesList();

		Files compareTofiles = compareTo.getFiles();
		List<File> compareTofilesList = compareTofiles.getFilesList();

		// find removed files
		ArrayList<File> removedFiles = new ArrayList<File>(compareTofilesList);
		removedFiles.removeAll(targetFilesList);
		configComparison.addRemovedFiles(removedFiles);

		for (File file : targetFilesList)
		{
			FileStatus fileStatus = new FileStatus(file.getName(), file.getHash(), file.getFilePath());
			fileStatus = compareTofiles.retrieveFileStatus(fileStatus);
			configComparison.addFileStatus(fileStatus);
		}

		return configComparison;
	}

	public FileComparison compareFileWithPrev(File targetFile)
			throws CoreException, IOException
	{
		CollaborationProject collaborationProject = targetFile.getCollaborationProject();

		Configuration thisConfiguration = (Configuration) targetFile.getParent();
		Configuration previousConfiguration = thisConfiguration.getParentConfiguration();

		if (previousConfiguration == null)
		{
			return null;
		}

		File previousFile = previousConfiguration.getFile(targetFile.getName());

		if (previousFile == null)
		{
			return null;
		}

		IFile iTargetFile = getFile(targetFile, collaborationProject);
		String targetContent = CollaborationPluginUtils.convertStreamToString(iTargetFile.getContents());

		IFile iPrevFile = getFile(previousFile, collaborationProject);
		String prevContent = CollaborationPluginUtils.convertStreamToString(iPrevFile.getContents());

		return new FileComparison(targetFile, targetContent, previousFile, prevContent);
	}

	public void activateConfiguration(Configuration configToActivate)
			throws CoreException
	{
		Files files = configToActivate.getFiles();
		FileHandler.copyFilesToProjectWorkspace(files.getFilesList(), configToActivate.getCollaborationProject());
	}

	public void saveModel() throws CoreException, IOException
	{
		FileHandler filehandler = new FileHandler();
		for (CollaborationProject collabProject : datamodel.getCollaborationProjects())
		{
			filehandler.saveCollaborationProject(collabProject);
		}
	}

	public void loadModel()
	{
		IProject[] projectsInWorkbench = CollaborationPluginUtils.getProjectsInWorkbench();
		FileHandler filehandler = new FileHandler();

		for (IProject iProject : projectsInWorkbench)
		{
			CollaborationProject project = filehandler.loadCollaborationProject(iProject);
			if (project != null)
			{
				datamodel.addCollaborationProject(project);
			}
		}
	}

	public void approveConfiguration(Configuration configToApprove)
			throws CoreException
	{
		ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
		ID connectedUser = connectionManager.getConnectedUser();
		CollaborationProject collaborationProject = configToApprove.getCollaborationProject();
		
		ConfigurationStatusMessage statMsg = new ConfigurationStatusMessage(connectionManager.getConnectedUser(), collaborationProject.getUniqueID(), configToApprove.getUniqueID(), NegotiationStatus.ACCEPT);
		connectionManager.sendToAll(statMsg, collaborationProject);
		
		configToApprove.setStatus(connectedUser, ConfigurationNegotiationStatus.ACCEPT);
	}
	
	public void rejectConfiguration(Configuration configurationToReject)
	{
		ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
		ID connectedUser = connectionManager.getConnectedUser();
		CollaborationProject collaborationProject = configurationToReject.getCollaborationProject();
		
		ConfigurationStatusMessage statMsg = new ConfigurationStatusMessage(connectionManager.getConnectedUser(), collaborationProject.getUniqueID(), configurationToReject.getUniqueID(), NegotiationStatus.REJECT);
		connectionManager.sendToAll(statMsg, collaborationProject);
		
		configurationToReject.setStatus(connectedUser, ConfigurationNegotiationStatus.REJECT);
	}

	public void updateConfigurationStatus(ID id, String configurationId,
			ConfigurationNegotiationStatus negotiationStatus, String projectId)
	{
		CollaborationProject collaborationProject = getCollaborationProjectFromID(projectId);
		Configuration configuration = collaborationProject.getConfiguration(configurationId);
		configuration.setStatus(id, negotiationStatus);
	}

	public void collaboratorJoining(ID senderID, boolean joining, String projectID, boolean notifyCollaborators) throws SerializationException
	{
		CollaborationProject collaborationProject = getCollaborationProjectFromID(projectID);
		CollaborationGroup collaboratorGroup = collaborationProject.getCollaboratorGroup();
		
		String userName = senderID.getName();	
		User usr = collaboratorGroup.getUser(userName);
		
		if(joining){
			usr.acceptedToJoinGroup(true);
		} else{
			usr.acceptedToJoinGroup(false);
		}
		
		if(notifyCollaborators){
			ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
			
			//send updated collaborator list to everyone
			List<User> collaborators = collaborationProject.getCollaboratorGroup().getCollaborators();
			
			List<ID> collaboratorIds = new ArrayList<>();
			for (User user : collaborators)
			{
				//collaborators are only shared if they have agreed to join the group
				if(user.hasJoinedGroup()) {
					collaboratorIds.add(user.getUserID());
				}
			}
			
			CollaborationGroupUpdateMessage collabGroupUpdateMsg = new CollaborationGroupUpdateMessage(connectionManager.getConnectedUser(), collaborationProject.getUniqueID(), collaboratorIds);
			connectionManager.sendToAll(collabGroupUpdateMsg, collaborationProject);
		}
	}

	public void updateCollaborationGroup(List<ID> collaborationGroupMembers,
			String projectID)
	{
		CollaborationProject collaborationProject = getCollaborationProjectFromID(projectID);
		if(collaborationProject == null){
			ResourcesPlugin.getPlugin().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, Notification.Collab_File_ERROR_NO_SUCH_PROJECT
					+ projectID, null));
			return;
		}
		
		CollaborationGroup collaboratorGroup = collaborationProject.getCollaboratorGroup();
		
		for (ID id : collaborationGroupMembers)
		{
			if(!collaboratorGroup.hasCollaborator(id.getName())){
				collaboratorGroup.addCollaborator(id, true);
			}
		}
	}

	public void addNewCollaborator(IUser receiver, CollaborationGroup group,
			CollaborationProject project, String description) throws SerializationException
	{
			ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
			ID connectedUser = connectionManager.getConnectedUser();
			
			//build reply
			CollaborationRequest msg = new CollaborationRequest(connectionManager.getConnectedUser(), project.getUniqueID(), project.getTitle(), description);
			connectionManager.sendTo(receiver.getID(), msg);
			
			//add collaborator locally, currently as not joined. 
			group.addCollaborator(receiver.getID(), false);	
			
			//we are inviting others, so we should be part of the collaboration ourself.  
			if(!group.hasCollaborator(connectedUser.getName())){
				group.addCollaborator(connectedUser, true);
			}
	}
}