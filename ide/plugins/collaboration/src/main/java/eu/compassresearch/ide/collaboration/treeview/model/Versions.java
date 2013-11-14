package eu.compassresearch.ide.collaboration.treeview.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Versions extends Model {
	protected List versions;

	public Versions() {
		super("Versions");
		versions = new ArrayList();
	}
	
	public void addVersion(Version version) {
		versions.add(0,version);
		version.setParent(this); 
		fireAdd(version);
	}
	
	protected void removeVersion(Version version) {
		versions.remove(version);
		version.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(version);
	}
	
	public List getVersions() {
		return versions;
	}
	
	public int size() {
		return versions.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		
		for (Iterator iterator = versions.iterator(); iterator.hasNext();) {
			Version version = (Version) iterator.next();
			version.addListener(listener);
		}
		
		super.addListener(listener);
	}

}
