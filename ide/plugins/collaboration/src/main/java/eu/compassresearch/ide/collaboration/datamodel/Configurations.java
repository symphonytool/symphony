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

	public Configurations() {
		configurations = new LinkedList<Configuration>();
		this.name = "Configurations";
	}

	protected void addConfiguration(Configuration configuration) {
		
		assert(!configurations.contains(configuration));
		
		configurations.add(configuration);
		configuration.addListener(listener);
		configuration.setParent(this);
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

	private CollaborationProject getCollaborationProject(){
		return (CollaborationProject) this.getParent();
	}
	
	private boolean isKnownFile(String hash)
	{
		if(configurations.isEmpty()) return false;
		
		Configuration newestConfig = configurations.getLast();

		return newestConfig.isKnownFile(hash);
	}

	public boolean addFile(IFile file) throws CoreException, IOException
	{
		String calculatedSha = FileHandler.calculateSha(file);
		
		if(isKnownFile(calculatedSha)){
			return false;
		} 
		
		IFile filePath = FileHandler.saveFile(file, getCollaborationProject());
		File newFile = new File(file.getName(), calculatedSha, filePath.getFullPath().toString());
		
		Configuration configuration;
		if(configurations.isEmpty()){
			 configuration = new Configuration(newFile);

		} else {
			Configuration oldConfig = configurations.getLast();
			configuration = new Configuration(oldConfig, newFile);
		}
		
		addConfiguration(configuration);
			
		return true;
	}
}
