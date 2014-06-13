package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.files.FileStatus.FileState;

public class Files extends Model {

	private static final long serialVersionUID = -2544433612278015772L;
	
	protected Map<String, File> files;

	public Files(Model parent) {
		super("Files", parent);
		files = new HashMap<String,File>();
	}
	
	private Files(Map<String, File> files, Model parent) {
		super("Files", parent);
		this.files = files;
	}
	
	public void addFile(File file) {
		files.put(file.getName(),file);
		file.addListener(listener);
		fireObjectUpdatedEvent(file);
	}
	
	protected void removeFile(File file) {
		files.remove(file);
		file.removeListener(listener);
		fireObjectRemovedEvent(file);
	}
	
	public List<File> getFilesList() {
		return new ArrayList<File>(files.values());
	}
	
	public int size() {
		return files.size();
	}
	
	public Files copy(Model newParent) {
		
		Map<String, File> clone = new HashMap<String,File>();
		
		File f;
		for (File file : files.values())
		{
			f = file.copy(newParent);
			clone.put(f.getName(), f);
		}
		
		return new Files(clone, newParent);
	}
	
	public FileStatus retrieveFileStatus(FileStatus forFile) 
	{
		String fileName = forFile.getFileName();
		if(files.containsKey(fileName)){
			File foundFile = files.get(fileName);

			if(foundFile.getHash().equals(forFile.getHash())){
				 forFile.setState(FileState.UNCHANGED);
			}  else {
				forFile.setState(FileState.CHANGED);
			}
		} else {
			forFile.setState(FileState.ADDED);
		}
		
		return forFile;
	}
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitFiles(this, passAlongArgument);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "(" + files.size() + ")";
	}
	
	@Override
	public void addListener(IModelEventListener listener) {
		
		for (File f : files.values())
		{
			f.addListener(listener);
		}
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IModelEventListener listener)
	{
		for (File f : files.values())
		{
			f.removeListener(listener);
		}
		
		super.removeListener(listener);
	}
	
	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}

	public File getFile(String filename)
	{
		return files.get(filename);
	}
}
