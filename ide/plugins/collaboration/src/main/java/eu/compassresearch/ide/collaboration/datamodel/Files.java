package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Files extends Model {

	private static final long serialVersionUID = -2544433612278015772L;
	
	protected Map<String, File> files;

	public Files() {
		super("Files");
		files = new HashMap<String,File>();
	}
	
	private Files(Map<String, File> files) {
		super("Files");
		
		this.files = files;
	}
	
	public void addFile(File file) {
		files.put(file.getHash(),file);
		file.setParent(this); 
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
		
		return new Files(clone);
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

	public boolean isFileKnown(String hash)
	{
		return files.containsKey(hash);
	}
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitFiles(this, passAlongArgument);
	}
}
