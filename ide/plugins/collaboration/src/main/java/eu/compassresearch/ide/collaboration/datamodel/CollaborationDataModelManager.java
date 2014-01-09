package eu.compassresearch.ide.collaboration.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.CollaborationPluginUtils;
import eu.compassresearch.ide.collaboration.files.FileChangeManager.FileStatus;
import eu.compassresearch.ide.collaboration.files.FileHandler;
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

	public FileStatus handleFile(IFile file)
	{
		String projectName = file.getProject().getName();
		CollaborationProject collaborationProject = getCollaborationProject(projectName);

		if (collaborationProject == null)
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, Notification.Collab_File_ERROR_NO_SUCH_PROJECT
					+ projectName, null));
			return FileStatus.ERROR;
		}

		FileStatus fileStatus = collaborationProject.getFileStatus(file);

		if (fileStatus != FileStatus.UNCHANGED)
		{
			Configurations configurations = collaborationProject.getConfigurations();
			Configuration newestConfiguration = configurations.getNewestConfiguration();

			if (newestConfiguration == null
					|| newestConfiguration.isSharedConfiguration())
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
				if (fileStatus == FileStatus.NEWFILE)
				{
					collaborationProject.addNewFile(file);

				} else if (fileStatus == FileStatus.CHANGED)
				{
					collaborationProject.updateFile(file);
				}

			} catch (CoreException e)
			{
				e.printStackTrace();
				ResourcesPlugin.getPlugin().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, e.toString(), null));
			}
		}

		return fileStatus;
	}

	public IFile getFile(File file, CollaborationProject collaborationProject) throws CoreException, IOException{
		
		IFile iFile = null;
		
		//has file already been stored previously, get it from the collaboration dir
		if(file.isStored()){
			iFile = FileHandler.loadFileFromCollaborationDir(file, collaborationProject);
		} else {
			//otherwise try to get it directly from the project
			iFile = FileHandler.loadFileFromProject(file, collaborationProject);
		}
		
		return iFile;
	}
	
	public boolean hasActiveCollaborationProjects()
	{
		return !datamodel.getCollaborationProjects().isEmpty();
	}

	public boolean isKnownFile(IFile file)
	{
		String projectName = file.getProject().getName();
		CollaborationProject collaborationProject = getCollaborationProject(projectName);

		if (collaborationProject != null)
		{
			return collaborationProject.getFileStatus(file) != FileStatus.NEWFILE;
		} else
		{
			return false;
		}
	}

	public void signAndShareConfiguration(Configuration config)
			throws CoreException
	{
		// TODO add user
		// set as shared
		
		List<File> files = config.getFiles().getFilesList();
		for (File file : files)
		{
			FileHandler.copyFileToCollaborationDir(file, config.getCollaborationProject());
			file.setAsStored();
		}
		config.setSignedBy("Not logged in");

		// TODO send over the line
		config.setConfigurationShared();
	}
}