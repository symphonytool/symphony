package eu.compassresearch.ide.collaboration.files;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;

public class FileChangeManager implements IResourceChangeListener
{
	private CollaborationDataModelManager modelMgm;

	public FileChangeManager()
	{
		modelMgm = Activator.getDefault().getDataModelManager();
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event)
	{
		if(!modelMgm.hasActiveCollaborationProjects())
			return;
		
		if (event.getType() != IResourceChangeEvent.POST_CHANGE)
			return;

		IResourceDelta rootDelta = event.getDelta();

		final ArrayList<IResource> changed = new ArrayList<IResource>();
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor()
		{
			public boolean visit(IResourceDelta delta)
			{

				if (delta.getKind() != IResourceDelta.CHANGED)
					return true;

				if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
					return true;

				IResource resource = delta.getResource();
				if (resource.getType() == IResource.FILE
						&& "cml".equalsIgnoreCase(resource.getFileExtension()))
				{
					changed.add(resource);
				}

				return true;
			}
		};

		try
		{
			rootDelta.accept(visitor);
		} catch (CoreException e)
		{
			e.printStackTrace();
		}

		for (IResource changedFile : changed)
		{	
			IFile file = (IFile) changedFile;
			
			if(modelMgm.isKnownFile(file)){
				modelMgm.handleFile((IFile)changedFile);
			}
		}
	}
}
