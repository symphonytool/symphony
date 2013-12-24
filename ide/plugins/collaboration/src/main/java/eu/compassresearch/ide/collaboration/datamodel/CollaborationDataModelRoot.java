package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class CollaborationDataModelRoot extends Model {

	private static final long serialVersionUID = -1055683893766465746L;

	HashMap<String,CollaborationProject> collaborationProjects;	
	
	public CollaborationDataModelRoot() {
		super();

		collaborationProjects = new HashMap<String, CollaborationProject>();
	}
	
	public CollaborationDataModelRoot(String name ) {
		this();
		this.name = name;
	}
	
	public boolean addCollaborationProject(CollaborationProject project) {
		
		 String title = project.getTitle();
		
		if(collaborationProjects.containsKey(title)){
			return false;
		} else {
			collaborationProjects.put(project.getTitle(), project);
			fireObjectAddedEvent(project);
			
			return true;
		}
	}
	
	public ArrayList<CollaborationProject> getCollaborationProjects() {
		return new ArrayList<>(collaborationProjects.values());
	}
	
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}
}
