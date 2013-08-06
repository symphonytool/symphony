package eu.compassresearch.ide.interpreter.debug.ui.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.texteditor.ITextEditor;

import eu.compassresearch.ide.core.resources.ICmlProject;

public class CmlBreakpointAdapterFactory implements IAdapterFactory
{

	@Override
	public Object getAdapter(Object adaptableObject,
			@SuppressWarnings("rawtypes") Class adapterType)
	{
		if (adaptableObject instanceof ITextEditor)
		{
			ITextEditor editorPart = (ITextEditor) adaptableObject;
			IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource != null && resource instanceof IFile)
			{
				IFile file = (IFile) resource;
				try
				{
					if (file == null || !file.exists()
							|| !file.isSynchronized(IResource.DEPTH_ZERO))
					{
						return null;
					}
					ICmlProject project = (ICmlProject) file.getProject().getAdapter(ICmlProject.class);
					if (project != null && project.isModelFile(file))
					{
						return new CmlLineBreakpointAdapter();
					}
				} catch (CoreException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
