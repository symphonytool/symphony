package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;

public class CollaborationDataModelRoot extends Model {

	ArrayList<CollaborationProject> collabPrj;	
	
	public CollaborationDataModelRoot() {
		super();

		collabPrj = new ArrayList<CollaborationProject>();
	}
	
	public CollaborationDataModelRoot(String name ) {
		this();
		this.name = name;
	}
	
	public void addCollaborationProject(CollaborationProject project) {
		collabPrj.add(project);
	}
	
	public ArrayList<CollaborationProject> getCollaborationProjects() {
		return collabPrj;	
	}
	
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}
}
