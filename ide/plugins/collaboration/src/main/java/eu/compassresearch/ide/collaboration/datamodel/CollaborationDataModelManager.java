package eu.compassresearch.ide.collaboration.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.user.IUser;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.CollaborationPluginUtils;
import eu.compassresearch.ide.collaboration.communication.ConnectionManager;
import eu.compassresearch.ide.collaboration.communication.messages.ConfigurationStatusMessage;
import eu.compassresearch.ide.collaboration.communication.messages.ConfigurationStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.communication.messages.NewConfigurationMessage;
import eu.compassresearch.ide.collaboration.files.FileComparison;
import eu.compassresearch.ide.collaboration.files.FileHandler;
import eu.compassresearch.ide.collaboration.files.FileSet;
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

	public FileStatus handleFile(IFile file)
	{
		FileStatus fileStatus = createFileStatus(file);

		String projectName = file.getProject().getName();
		CollaborationProject collaborationProject = getCollaborationProject(projectName);

		if (collaborationProject == null)
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, Notification.Collab_File_ERROR_NO_SUCH_PROJECT
					+ projectName, null));
			fileStatus.setStatus(FileState.ERROR);
			return fileStatus;
		}

		fileStatus = collaborationProject.getFileStatus(fileStatus);

		if (fileStatus.getStatus() != FileState.UNCHANGED)
		{
			Configurations configurations = collaborationProject.getConfigurations();
			Configuration newestConfiguration = configurations.getNewestConfiguration();

			if (newestConfiguration == null
					|| newestConfiguration.isShared() || newestConfiguration.isReceived())
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
				if (fileStatus.getStatus() == FileState.NEWFILE)
				{
					collaborationProject.addNewFile(file);

				} else if (fileStatus.getStatus() == FileState.CHANGED)
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
		FileStatus fileStatus = new FileStatus(file.getName(), hash);
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
			return fileStatus.getStatus() != FileState.NEWFILE;
		} else
		{
			return false;
		}
	}

	public void signAndShareConfiguration(Configuration config)
			throws CoreException
	{
		CollaborationProject collaborationProject = config.getCollaborationProject();

		List<File> files = config.getFiles().getFilesList();
		List<FileSet> fileSets = new ArrayList<FileSet>();
		IFile iFile;
		String content;
		try
		{
			for (File file : files)
			{
				if (!file.isStored())
				{
					iFile = FileHandler.copyFileToCollaborationDir(file, collaborationProject);
					file.setAsStored();
				} else
				{
					iFile = FileHandler.loadFileFromCollaborationDir(file, collaborationProject);
				}

				content = CollaborationPluginUtils.convertStreamToString(iFile.getContents());
				fileSets.add(new FileSet(file, content));
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			Notification.logError(e.toString(), e);
		}

		ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
		
		//TODO rework
		config.setSignedBy(connectionManager.getConnectedUser().getName());
		
		NewConfigurationMessage newConfigurationMessage = new NewConfigurationMessage(connectionManager.getConnectedUser(), collaborationProject.getUniqueID(), config, fileSets);
		connectionManager.send(newConfigurationMessage, collaborationProject);

		config.setConfigurationShared();
	}

	public FileComparison getComparisonWithPrev(File targetFile)
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

	public void activateConfiguration(Configuration configToActivate) throws CoreException
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

	public void approveConfiguration(Configuration configToApprove) throws CoreException
	{
		ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
		CollaborationProject collaborationProject = configToApprove.getCollaborationProject();
		
		ConfigurationStatusMessage statMsg = new ConfigurationStatusMessage(connectionManager.getConnectedUser(), collaborationProject.getUniqueID(), configToApprove.getUniqueID(), NegotiationStatus.ACCEPT);
		connectionManager.send(statMsg, collaborationProject);
		
		configToApprove.setStatus(NegotiationStatus.ACCEPT);
	}

	public void updateConfigurationStatus(String configurationId, NegotiationStatus negotiationStatus, String projectId)
	{
		CollaborationProject collaborationProject = getCollaborationProjectFromID(projectId);
		Configuration configuration = collaborationProject.getConfiguration(configurationId);
		configuration.setStatus(negotiationStatus);
	}
}