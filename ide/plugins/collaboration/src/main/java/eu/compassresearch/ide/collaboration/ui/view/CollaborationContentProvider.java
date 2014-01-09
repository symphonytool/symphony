package eu.compassresearch.ide.collaboration.ui.view;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.Configurations;
import eu.compassresearch.ide.collaboration.datamodel.DeltaEvent;
import eu.compassresearch.ide.collaboration.datamodel.IDeltaListener;
import eu.compassresearch.ide.collaboration.datamodel.Model;
import eu.compassresearch.ide.collaboration.datamodel.Shares;
import eu.compassresearch.ide.collaboration.datamodel.File;
import eu.compassresearch.ide.collaboration.datamodel.Files;


public class CollaborationContentProvider implements ITreeContentProvider, IDeltaListener {
	private static Object[] EMPTY_ARRAY = new Object[0];
	protected TreeViewer viewer;
	
	public void dispose() {}

	/**
	* Notifies this content provider that the given viewer's input
	* has been switched to a different element.
	* <p>
	* A typical use for this method is registering the content provider as a listener
	* to changes on the new input (using model-specific means), and deregistering the viewer 
	* from the old input. In response to these change notifications, the content provider
	* propagates the changes to the viewer.
	* </p>
	*
	* @param viewer the viewer
	* @param oldInput the old input element, or <code>null</code> if the viewer
	*   did not previously have an input
	* @param newInput the new input element, or <code>null</code> if the viewer
	*   does not have an input
	*/
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer)viewer;
		if(oldInput != null) {
			removeListenerFrom((CollaborationDataModelRoot)oldInput);
		}
		if(newInput != null) {
			addListenerTo((CollaborationDataModelRoot)newInput);
		}
	}
	
	protected void removeListenerFrom(CollaborationDataModelRoot root) {
		root.removeListener(this);
	}
	
	protected void addListenerTo(Model root) {
		root.addListener(this);
	}

	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof CollaborationDataModelRoot) {
			CollaborationDataModelRoot root = (CollaborationDataModelRoot)parentElement;
				return root.getCollaborationProjects().toArray();
		} else if(parentElement instanceof CollaborationProject){
			CollaborationProject collabPrj = (CollaborationProject)parentElement;
			Object[] both = new Object[]{collabPrj.getConfigurations(),collabPrj.getCollaboratorGroup()};
			return both;
		} else if(parentElement instanceof Configurations) {
			Configurations contracts = (Configurations)parentElement;
			return contracts.getConfigurations().toArray();
		} else if(parentElement instanceof Configuration) {
			Configuration contract = (Configuration)parentElement;
			return new Object[]{contract.getFiles()};
		} else if(parentElement instanceof Shares) {
			Shares shares = (Shares)parentElement;
			return shares.getShares().toArray();
		} else if(parentElement instanceof Files) {
			Files versions = (Files)parentElement;
			return versions.getFilesList().toArray();
		} else if(parentElement instanceof File) {
			File version = (File)parentElement;
			return version.getShares().getShares().toArray();
		} 
		else if(parentElement instanceof CollaborationGroup) {
			CollaborationGroup collabGroup = (CollaborationGroup)parentElement;
			return collabGroup.getCollaborators().toArray();
		}
		return EMPTY_ARRAY;
	}
	
	protected Object[] concat(Object[] objects, Object[] objects2) {
		Object[] both = new Object[objects.length + objects2.length];
		System.arraycopy(objects, 0, both, 0, objects.length);
		System.arraycopy(objects2, 0, both, objects.length, objects2.length);		
		return both;
	}

	public Object getParent(Object element) {
		if(element instanceof Model) {
			return ((Model)element).getParent();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void onObjectUpdated(DeltaEvent event) {
		Object root = ((Model)event.receiver()).getParent();
		viewer.refresh(root, true);
	}

	public void onObjectRemove(DeltaEvent event) {
		onObjectRemove(event);
	}
}
