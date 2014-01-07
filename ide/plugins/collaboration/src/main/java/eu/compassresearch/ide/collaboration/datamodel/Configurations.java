package eu.compassresearch.ide.collaboration.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ide.collaboration.files.FileHandler;

public class Configurations extends Model {

	private static final long serialVersionUID = -7710832265326698319L;
	
	protected LinkedList<Configuration> configurations;

	public Configurations(Model parent) {
		super("Configurations", parent);
		configurations = new LinkedList<Configuration>();
	}

	protected void addConfiguration(Configuration configuration) {
		
		assert(!configurations.contains(configuration));
		
		configurations.add(0,configuration);
		configuration.addListener(listener);
		fireObjectAddedEvent(configuration);
	}

	protected void removeConfiguration(Configuration configuration) {
		configurations.remove(configuration);
		configuration.removeListener(listener);
		fireObjectRemovedEvent(configuration);
	}

	public List<Configuration> getConfigurations() {
		return new ArrayList<Configuration>(configurations);
	}

	public int size() {
		return configurations.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		// visitor.visitContracts(this, passAlongArgument);
	}

	@Override
	public String toString()
	{
		return super.toString() + "(" + configurations.size() + ")";
	}
	
	@Override
	public void addListener(IDeltaListener listener) {

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
		return null; //configurations.get(name);
	}

	public CollaborationProject getCollaborationProject(){
		return getParent().getCollaborationProject();
	}
	
	private boolean isKnownFile(IFile file) throws CoreException, IOException
	{
		if(configurations.isEmpty()) return false;
		
		Configuration newestConfig = configurations.getFirst();

		return newestConfig.isKnownFile(file);
	}

	public boolean addFile(IFile file) throws CoreException, IOException
	{
		String calculatedSha = FileHandler.calculateSha(file);
		
		if(isKnownFile(file)){
			return false;
		} 
		
		IFile filePath = FileHandler.saveFile(file, getCollaborationProject());
		File newFile = new File(file.getName(), calculatedSha, filePath.getFullPath().toString(), this);
		newFile.setNewFile(true);
		
		Configuration configuration;
		if(configurations.isEmpty()){
			 configuration = new Configuration(newFile, this);

		} else {
			Configuration oldConfig = configurations.getFirst();
			configuration = new Configuration(oldConfig, newFile, this);
		}
		
		addConfiguration(configuration);
			
		return true;
	}

	public boolean updateFile(IFile file)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
