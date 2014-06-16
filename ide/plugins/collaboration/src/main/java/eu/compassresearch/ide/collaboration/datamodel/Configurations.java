package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.files.FileUpdate;

public class Configurations extends Model
{
	private static final long serialVersionUID = -7710832265326698319L;

	protected LinkedList<Configuration> configurations;

	public Configurations(Model parent)
	{
		super("Configurations", parent);
		configurations = new LinkedList<Configuration>();
	}

	public void addConfiguration(Configuration configuration)
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
	public void addListener(IModelEventListener listener)
	{

		for (Configuration c : configurations)
		{
			c.addListener(listener);
		}

		super.addListener(listener);
	}

	@Override
	public void removeListener(IModelEventListener listener)
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

	public void addFile(FileStatus fileStatus) throws CoreException
	{
		if (configurations.isEmpty())
		{
			addNewConfiguration();
		}
				
		Configuration newestConfiguration = getNewestConfiguration();
		
		File newFile = new File(fileStatus, newestConfiguration);
		newFile.setAsNewFile();
		
		newestConfiguration.addFile(newFile);
	}

	public void updateFile(FileUpdate fileUpdate)
	{
		Configuration newestConfiguration = getNewestConfiguration();
		File fileToUpdate = newestConfiguration.getFile(fileUpdate.getFileName());
		
		//if new file, don't set as updated //TODO if is received
		if(!fileToUpdate.isNewFile()){
			fileToUpdate.setUpdate(fileUpdate);
		}
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

	public Configuration getConfigurationFromId(String configurationUniqueID)
	{
		for (Configuration config : configurations)
		{
			if(config.getUniqueID().equals(configurationUniqueID))
				return config;
		}
		
		return null;
	}

}
