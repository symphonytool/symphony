package eu.compassresearch.ide.collaboration.files;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class FileHandler
{
	private static final String defaultFileName = "collaboration-configuration-";
	private static final String defaultFileNameExtension = ".dat";
	private static final String defaultCollaborationFolderName = "Collaboration";

	public void saveCollaborationProject(
			CollaborationProject collaborationProject) throws CoreException,
			IOException
	{

		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		String filename = defaultFileName + workspaceProjectName + defaultFileNameExtension;

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(workspaceProjectName);

		if (!project.exists())
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(Status.ERROR, workspaceProjectName, 0, Notification.Collab_File_ERROR_NO_SUCH_PROJECT, null));
			return;
		}

		if (!project.isOpen())
			project.open(null);

		IFolder projectFolder = project.getFolder(defaultCollaborationFolderName);

		if (!projectFolder.exists())
			projectFolder.create(IResource.NONE, true, null);

		IFile file = projectFolder.getFile(filename);

		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutput oos = new ObjectOutputStream(baos);
		try
		{
			oos.writeObject(collaborationProject);
			bais = new ByteArrayInputStream(baos.toByteArray());
		} finally
		{
			oos.close();
			baos.close();
		}

		if (!file.exists())
		{
			file.create(bais, IResource.NONE, null);

		} else
		{
			file.setContents(bais, true, false, null);
		}
	}

	public CollaborationProject loadCollaborationProject(IProject project)
	{
		String filename = defaultFileName + project.getName() + defaultFileNameExtension;
		IFolder projectFolder = project.getFolder(defaultCollaborationFolderName);

		if (projectFolder.exists())
		{
			IFile file = projectFolder.getFile(filename);

			if(file.exists())
			{
				ObjectInput ois = null;
				try
				{
					ois = new ObjectInputStream(file.getContents());
					CollaborationProject collabProject = (CollaborationProject) ois.readObject();
					ois.close();
					return collabProject;
				} catch (ClassNotFoundException | IOException | CoreException e)
				{
					CollaborationDialogs.displayErrorDialog("Error loading stored collaboration project: "
							+ filename);
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
