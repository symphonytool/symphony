package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class CollaborationDataModelRoot extends Model {

	private static final long serialVersionUID = -1055683893766465746L;

	HashMap<String,CollaborationProject> collaborationProjects;	
	
	public CollaborationDataModelRoot() {
		super("Collaboration Data Model Root");
		
		collaborationProjects = new HashMap<String, CollaborationProject>();
	}
	
	public CollaborationDataModelRoot(String name) {
		this();
		this.name = name;
	}
	
	public boolean addCollaborationProject(CollaborationProject project) {
		
		 String id = project.getUniqueID();
		
		if(collaborationProjects.containsKey(id)){
			return false;
		} else {
			collaborationProjects.put(id, project);
			project.addListener(listener);
			fireObjectAddedEvent(project);
			
			return true;
		}
	}
	
	@Override
	public void removeListener(IDeltaListener listener)
	{
		for (CollaborationProject p : collaborationProjects.values())
		{
			p.removeListener(listener);
		}
		super.removeListener(listener);
	}
	
	public ArrayList<CollaborationProject> getCollaborationProjects() {
		return new ArrayList<>(collaborationProjects.values());
	}
	
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}
}
