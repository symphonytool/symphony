package eu.compassresearch.ide.collaboration.datamodel;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;

public class CollaborationDataModelRoot extends Model
{

	private static final long serialVersionUID = -1055683893766465746L;

	HashMap<String, CollaborationProject> collaborationProjects;

	public CollaborationDataModelRoot()
	{
		super("Collaboration Data Model Root", null);

		collaborationProjects = new HashMap<String, CollaborationProject>();
	}

	public CollaborationDataModelRoot(String name)
	{
		this();
		this.name = name;
	}

	@Override
	public void addListener(IModelEventListener listener)
	{
		for (CollaborationProject collab_project : collaborationProjects.values())
		{
			collab_project.addListener(listener);
		}

		super.addListener(listener);
	}

	@Override
	public void removeListener(IModelEventListener listener)
	{
		for (CollaborationProject p : collaborationProjects.values())
		{
			p.removeListener(listener);
		}
		super.removeListener(listener);
	}

	public ArrayList<CollaborationProject> getCollaborationProjects()
	{
		return new ArrayList<>(collaborationProjects.values());
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument)
	{
		// visitor.visitContracts(this, passAlongArgument);
	}

	@Override
	public CollaborationProject getCollaborationProject()
	{
		return null;
	}

	public CollaborationProject getCollaborationProjectFromID(String id)
	{
		return collaborationProjects.get(id);
	}

	public boolean addCollaborationProject(CollaborationProject collabProject)
	{
		String id = collabProject.getUniqueID();

		if (collaborationProjects.containsKey(id))
		{
			return false;
		} else
		{
			collaborationProjects.put(id, collabProject);
			collabProject.addListener(listener);
			fireObjectUpdatedEvent(collabProject);
			return true;
		}
	}

	public boolean addCollaborationProject(String project, String title,
			String description)
	{
		CollaborationProject collabProject = new CollaborationProject(project, title, description, this);
		return addCollaborationProject(collabProject);
	}

	public boolean addCollaborationProject(String project, String title,
			String description, String collabProjectId)
	{
		CollaborationProject collabProject = new CollaborationProject(project, title, description, collabProjectId, this);
		return addCollaborationProject(collabProject);
	}

	public boolean removeCollaborationProject(CollaborationProject project)
	{
		String uniqueID = project.getUniqueID();
		if (collaborationProjects.containsKey(uniqueID))
		{
			collaborationProjects.remove(uniqueID);
			fireObjectUpdatedEvent(this);
			return true;
		} else
		{
			return false;
		}
	}
}
