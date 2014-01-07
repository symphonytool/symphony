package eu.compassresearch.ide.collaboration.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ide.collaboration.files.FileHandler;

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
		files.put(file.getHash(),file);
		fireObjectAddedEvent(file);
	}
	
	protected void removeFile(File file) {
		files.remove(file);
		file.removeListener(listener);
		fireObjectRemovedEvent(file);
	}
	
	public List<File> getFiles() {
		return new ArrayList<File>(files.values());
	}
	
	public int size() {
		return files.size();
	}
	
	public Files clone() {
		
		Map<String, File> clone = new HashMap<String,File>();
		
		File f;
		for (File file : files.values())
		{
			f = file.clone();
			clone.put(f.getName(), f);
		}
		
		return new Files(clone, getParent());
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "(" + files.size() + ")";
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		
		for (File f : files.values())
		{
			f.addListener(listener);
		}
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IDeltaListener listener)
	{
		for (File f : files.values())
		{
			f.removeListener(listener);
		}
		
		super.removeListener(listener);
	}

	public boolean isFileKnown(IFile file) throws CoreException, IOException
	{
		String calculatedSha = FileHandler.calculateSha(file);
		
		if(files.containsKey(calculatedSha)){
			
			File foundFile = files.get(calculatedSha);
			if(foundFile.getName().equals(file.getName())){
				return true;
			}  
		}
		
		return false;
	}
	
	public List<String> getFileNames(){
		
		List<String> fileList = new ArrayList<String>();
		for (File f : files.values())
		{
			fileList.add(f.getName());
		}
		
		return fileList;
	}
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitFiles(this, passAlongArgument);
	}

	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}
}
