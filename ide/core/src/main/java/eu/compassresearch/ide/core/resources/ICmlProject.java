package eu.compassresearch.ide.core.resources;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.overture.ide.core.resources.ModelBuildPath;

public interface ICmlProject extends IAdaptable
{
	public List<IContentType> getContentTypeIds();

	public boolean typeCheck(IProgressMonitor monitor)
			throws CoreException;

	public void typeCheck(boolean clean, IProgressMonitor monitor)
			throws CoreException;

	public String getName();

	public String getNature();

	public List<ICmlSourceUnit> getSourceUnits() throws CoreException;

	public File getFile(IFile file);
	
	boolean isModelFile(IFile file) throws CoreException;

	public IFile findIFile(File file);

	public File getSystemFile(IPath path);

	public File getFile(IWorkspaceRoot wroot, IPath path);

	public ICmlSourceUnit findSourceUnit(IFile file)
			throws CoreException;

	public ICmlModel getModel();

	public ModelBuildPath getModelBuildPath();
	
}
