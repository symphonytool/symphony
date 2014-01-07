package eu.compassresearch.ide.collaboration.files;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.File;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class FileHandler
{
	private static final String defaultFileName = "collaboration-configuration-";
	private static final String defaultFileNameExtension = ".dat";
	private static final String defaultCollaborationFolderName = ".Collaboration";

	public void loadFile(String hash, CollaborationProject collaborationProject)
	{

	}
	
	public static IFile loadFile(File file, CollaborationProject collaborationProject) throws CoreException
	{
		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		IFolder collabProjectFolder = getCollaborationDirectory(workspaceProjectName);
		
		if(collabProjectFolder != null) {
			IFile loadedFile = collabProjectFolder.getFile(file.getName());
			
			return loadedFile;
		}
		
		return null;
	}

	public static IFile saveFile(IFile file,
			CollaborationProject collaborationProject) throws CoreException
	{
		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		IFolder collabProjectFolder = getCollaborationDirectory(workspaceProjectName);

		IFile newFile = collabProjectFolder.getFile(file.getName());
		if (!newFile.exists())
		{
			InputStream source = file.getContents();
			newFile.create(source, IResource.FORCE, null);
			ResourceAttributes resourceAttributes = new ResourceAttributes();
			resourceAttributes.setReadOnly(true);
			resourceAttributes.setHidden(true);
			newFile.setResourceAttributes(resourceAttributes);
		}

		return newFile;
	}

	public static String calculateSha(IFile file) throws CoreException,
			IOException
	{
		MessageDigest md;
		try
		{
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			throw new CoreException(new Status(Status.ERROR, "", "SHA Hash failed for file: "
					+ file));
		}
		InputStream contents = file.getContents();

		byte[] dataBytes = new byte[1024];

		int read = 0;

		while ((read = contents.read(dataBytes)) != -1)
		{
			md.update(dataBytes, 0, read);
		}

		return byteArray2Hex(md.digest());
	}

	private static String byteArray2Hex(final byte[] hash)
	{

		Formatter formatter = new Formatter();
		try
		{
			for (byte b : hash)
			{
				formatter.format("%02x", b);
			}

			return formatter.toString();
		} finally
		{
			formatter.close();
		}
	}

	public void saveCollaborationProject(
			CollaborationProject collaborationProject) throws CoreException,
			IOException
	{
		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		IFolder projectFolder = getCollaborationDirectory(workspaceProjectName);
		String filename = defaultFileName + workspaceProjectName
				+ defaultFileNameExtension;
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

	private static IFolder getCollaborationDirectory(
			String workspaceProjectName) throws CoreException
	{
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(workspaceProjectName);

		if (!project.exists())
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(Status.ERROR, workspaceProjectName, 0, Notification.Collab_File_ERROR_NO_SUCH_PROJECT, null));
			return null;
		}

		if (!project.isOpen())
			project.open(null);

		IFolder projectFolder = project.getFolder(defaultCollaborationFolderName);

		if (!projectFolder.exists())
		{
			projectFolder.create(IResource.NONE, true, null);
			projectFolder.setTeamPrivateMember(true);
		}

		return projectFolder;

	}

	public CollaborationProject loadCollaborationProject(IProject project)
	{
		String filename = defaultFileName + project.getName()
				+ defaultFileNameExtension;
		IFolder projectFolder = project.getFolder(defaultCollaborationFolderName);
		try
		{
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
			if (projectFolder.exists())
			{
				IFile file = projectFolder.getFile(filename);

				if (file.exists())
				{
					ObjectInput ois = null;
					InputStream contents = file.getContents();
					ois = new ObjectInputStream(contents);
					CollaborationProject collabProject = (CollaborationProject) ois.readObject();
					ois.close();
					return collabProject;

				}
			}

		} catch (ClassNotFoundException | IOException | CoreException e)
		{
			CollaborationDialogs.displayErrorDialog("Error loading stored collaboration project: "
					+ filename, "Exception thrown. Stack trace printed");
			e.printStackTrace();
		}

		return null;
	}
}
