package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ide.collaboration.files.FileChangeManager.FileStatus;
import eu.compassresearch.ide.collaboration.files.FileHandler;

public class Configurations extends Model
{

	private static final long serialVersionUID = -7710832265326698319L;

	protected LinkedList<Configuration> configurations;

	public Configurations(Model parent)
	{
		super("Configurations", parent);
		configurations = new LinkedList<Configuration>();
	}

	private void addConfiguration(Configuration configuration)
	{

		assert (!configurations.contains(configuration));

		configurations.add(0, configuration);
		configuration.addListener(listener);
		fireObjectUpdatedEvent(this);
	}

	protected void removeConfiguration(Configuration configuration)
	{
		configurations.remove(configuration);
		configuration.removeListener(listener);
		fireObjectRemovedEvent(configuration);
	}

	public List<Configuration> getConfigurations()
	{
		return new ArrayList<Configuration>(configurations);
	}

	public int size()
	{
		return configurations.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument)
	{
		// visitor.visitContracts(this, passAlongArgument);
	}

	@Override
	public String toString()
	{
		int size = configurations.size();
		
		return super.toString() + (size > 0 ? " (" + configurations.size() + ")" : "");
	}

	@Override
	public void addListener(IDeltaListener listener)
	{

		for (Configuration c : configurations)
		{
			c.addListener(listener);
		}

		super.addListener(listener);
	}

	@Override
	public void removeListener(IDeltaListener listener)
	{
		for (Configuration c : configurations)
		{
			c.removeListener(listener);
		}

		super.removeListener(listener);
	}

	public Configuration getConfiguration(String name)
	{
		return null; // configurations.get(name);
	}

	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}

	public void addNewConfiguration()
	{
		if (configurations.isEmpty())
		{
			Configuration configuration = new Configuration(this);
			addConfiguration(configuration);
		} else
		{
			Configuration newConfig = new Configuration(getNewestConfiguration(), this);
			addConfiguration(newConfig);
		}
	}

	public void addFile(IFile file) throws CoreException
	{
		if (configurations.isEmpty())
		{
			addNewConfiguration();
		}
				
		Configuration newestConfiguration = getNewestConfiguration();
		
		String calculatedSha = FileHandler.calculateSha(file);
		File newFile = new File(file.getName(), calculatedSha, file.getProjectRelativePath().toString(), newestConfiguration.getFiles());
		newFile.setAsNewFile();
		
		newestConfiguration.addFile(newFile);
	}

	public boolean updateFile(IFile file)
	{
		Configuration newestConfiguration = getNewestConfiguration();
		
		File fileToUpdate = newestConfiguration.getFile(file.getName());
		
		//if new file, don't set as updated
		if(!fileToUpdate.isNewFile()){
			fileToUpdate.setAsUpdated();
		}

		return false;
	}

	public Configuration getConfiguration(int index)
	{
		if (!configurations.isEmpty()
				&& (index >= 0 || index < configurations.size()))
		{
			return configurations.get(index);
		} else
		{
			return null;
		}
	}

	public Configuration getNewestConfiguration()
	{
		if (!configurations.isEmpty())
		{
			return configurations.getFirst();
		}

		return null;
	}

	public FileStatus getFileStatus()
	{

		// if(configurations.isEmpty()) return false;

		// Configuration newestConfig = configurations.getFirst();

		// is filename known.

		// if not ->
		// return FileStatus.NEWFILE;

		// is it the same file

		// if not sha match, is known file, but changed
		//
		// return FileStatus.CHANGED

		// unchanged
		return FileStatus.UNCHANGED;
	}

}
