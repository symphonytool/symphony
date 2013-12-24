package eu.compassresearch.ide.collaboration.datamodel;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ide.collaboration.CollaborationPluginUtils;
import eu.compassresearch.ide.collaboration.files.FileHandler;

public class CollaborationDataModelManager
{
	private CollaborationDataModelRoot datamodel;

	public CollaborationDataModelManager()
	{
		datamodel = new CollaborationDataModelRoot();
	}

	public CollaborationDataModelRoot getDatamodel()
	{
		return datamodel;
	}

	public void addCollaborationProject(String project, String title,
			String description)
	{
		datamodel.addCollaborationProject(new CollaborationProject(project, title, description));
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
}