package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Versions extends Model {

	private static final long serialVersionUID = -2544433612278015772L;
	
	protected List<Version> versions;

	public Versions() {
		super("Versions");
		versions = new ArrayList<Version>();
	}
	
	public void addVersion(Version version) {
		versions.add(0,version);
		version.setParent(this); 
		fireObjectAddedEvent(version);
	}
	
	protected void removeVersion(Version version) {
		versions.remove(version);
		version.removeListener(listener);
		fireObjectRemovedEvent(version);
	}
	
	public List<Version> getVersions() {
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
		
		for (Iterator<Version> iterator = versions.iterator(); iterator.hasNext();) {
			Version version = iterator.next();
			version.addListener(listener);
		}
		
		super.addListener(listener);
	}

}
