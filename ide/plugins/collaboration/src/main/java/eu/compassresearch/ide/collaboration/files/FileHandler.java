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
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.CollaborationPluginUtils;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.File;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class FileHandler
{
	private static final String defaultFileName = "collaboration-configuration-";
	private static final String defaultFileNameExtension = ".dat";
	private static final String defaultCollaborationFolderName = ".Collaboration";

	
	public static IFile loadFileFromCollaborationDir(File file,
			CollaborationProject collaborationProject) throws CoreException,
			IOException
	{
		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		IFolder collabProjectFolder = getCollaborationDirectory(workspaceProjectName);

		if (collabProjectFolder != null)
		{
			IFile loadedFile = collabProjectFolder.getFile(file.getHashFileName());

			return loadedFile;
		}

		return null;
	}

	public static IFile loadFileFromProject(File file,
			CollaborationProject collaborationProject) throws CoreException
	{
		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		IProject project = getIProjectFromWorkspaceProjectName(workspaceProjectName);

		if (project != null)
		{
			IFile loadedFile = project.getFile(file.getFilePath());

			return loadedFile;
		}

		return null;
	}

	public static IFile copyFileToCollaborationDir(File file,
			CollaborationProject collaborationProject) throws CoreException
	{
		// find project
		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		IProject project = getIProjectFromWorkspaceProjectName(workspaceProjectName);
		if (project == null)
			return null;

		// copy file
		IFolder collabProjectFolder = getCollaborationDirectory(project);
		IFile newFile = collabProjectFolder.getFile(file.getHashFileName());
		if (!newFile.exists())
		{
			// find file to copy to collaboration dir
			IFile fileToCopy = project.getFile(file.getFilePath());

			if (fileToCopy.exists())
			{
				newFile.create(fileToCopy.getContents(), IResource.FORCE, null);
				ResourceAttributes resourceAttributes = new ResourceAttributes();
				resourceAttributes.setReadOnly(true);
				resourceAttributes.setHidden(true);
				newFile.setResourceAttributes(resourceAttributes);
			} else
			{
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Notification.Collab_file_ERROR_FILE_DOES_NOT_EXIST
						+ " " + file.getFilePath()));
			}
		}

		return newFile;
	}

	public static boolean saveFilesToCollaborationDir(List<FileDTO> list,
			CollaborationProject collaborationProject) throws CoreException
	{
		// find project
		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		IProject project = getIProjectFromWorkspaceProjectName(workspaceProjectName);
		if (project == null)
			return false;

		// save files
		IFolder collabProjectFolder = getCollaborationDirectory(project);
		for (FileDTO fs : list)
		{
			IFile newFile = collabProjectFolder.getFile(fs.getFileHashName());

			if (!newFile.exists())
			{
				byte[] bytes = fs.getFileContent().getBytes();
				InputStream source = new ByteArrayInputStream(bytes);
				newFile.create(source, IResource.FORCE, null);
				ResourceAttributes resourceAttributes = new ResourceAttributes();
				resourceAttributes.setReadOnly(true);
				resourceAttributes.setHidden(true);
				newFile.setResourceAttributes(resourceAttributes);
			}
		}

		return true;
	}
	
	public static void copyFilesToProjectWorkspace(List<File> filesList,
			CollaborationProject collaborationProject) throws CoreException
	{
		String workspaceProjectName = collaborationProject.getProjectWorkspaceName();
		IProject project = getIProjectFromWorkspaceProjectName(workspaceProjectName);
		
		IFolder collabProjectFolder = getCollaborationDirectory(workspaceProjectName);

		if (collabProjectFolder == null)
		{
			return;
		}
		
		for (File file : filesList)
		{
		  IFile projectFile = project.getFile(file.getFilePath());
		  
		  if(!projectFile.exists()){
			  
			  IFile loadedFile = collabProjectFolder.getFile(file.getHashFileName());
			  projectFile.create(loadedFile.getContents(), true, null);
			  
		  } else {
			  //TODO report error, file list should be handled at the GUI level
		  }
		}
		
	}

	private static IFolder getCollaborationDirectory(String workspaceProjectName)
			throws CoreException
	{
		IProject project = getIProjectFromWorkspaceProjectName(workspaceProjectName);
		return getCollaborationDirectory(project);
	}

	private static IFolder getCollaborationDirectory(IProject project)
			throws CoreException
	{
		IFolder projectFolder = project.getFolder(defaultCollaborationFolderName);

		if (!projectFolder.exists())
		{
			projectFolder.create(IResource.NONE, true, null);
			projectFolder.setTeamPrivateMember(true);
		}

		return projectFolder;
	}

	private static IProject getIProjectFromWorkspaceProjectName(
			String workspaceProjectName) throws CoreException
	{
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(workspaceProjectName);

		if (project == null || !project.exists())
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Notification.Collab_File_ERROR_NO_SUCH_PROJECT
					+ " " + workspaceProjectName, null));
			return null;
		}

		if (!project.isOpen())
			project.open(null);

		return project;
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

	public static String calculateSha(IFile file)
	{
		String sha = null;
		try
		{
			MessageDigest md;
			md = MessageDigest.getInstance("SHA1");

			InputStream contents = file.getContents();

			byte[] dataBytes = new byte[1024];

			int read = 0;

			while ((read = contents.read(dataBytes)) != -1)
			{
				md.update(dataBytes, 0, read);
			}

			sha = byteArray2Hex(md.digest());

		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			Notification.logError(Notification.Collab_File_ERROR_HASH_CALCULATION
					+ " SHA Hash failed for file: " + file, e);
		} catch (CoreException | IOException e)
		{
			e.printStackTrace();
			Notification.logError(Notification.Collab_File_ERROR_HASH_CALCULATION
					+ " Error could not find file : " + file, e);
		}

		return sha;
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
	
	//Will retrieve a file from the collaboration dir. If file is not already stored, it will 
	//copy it from the workspace to the collaboration dir before returning it. 
	public static String persistFileAndRetrieveContents(File file) throws CoreException, IOException
	{
		CollaborationProject collaborationProject = file.getCollaborationProject();
		
		IFile iFile;
		//if file is not already stored, copy it from the workspace to the collaboration dir, before retrieving it. 
		if (!file.isStored())
		{
			iFile = FileHandler.copyFileToCollaborationDir(file, collaborationProject);
			file.setAsStored();
		} else
		{
			iFile = FileHandler.loadFileFromCollaborationDir(file, collaborationProject);
		}

		//return file contents
		return CollaborationPluginUtils.convertStreamToString(iFile.getContents());
	}
}
