package eu.compassresearch.ide.internal.core.resources;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.overture.ide.builders.vdmj.IBuilderVdmjConstants;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.ide.core.resources.ModelBuildPath;
import org.overture.ide.core.utility.FileUtility;

import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.core.unsupported.UnsupportedElementInfo;

 class CmlProject implements ICmlProject
{
	 final IVdmProject project;

	public CmlProject(IVdmProject project)
	{
		this.project = project;
	}

	@Override
	public List<IContentType> getContentTypeIds()
	{
		return project.getContentTypeIds();
	}

	@Override
	public boolean typeCheck(IProgressMonitor monitor) throws CoreException
	{
		return project.typeCheck(monitor);
	}

	@Override
	public void typeCheck(boolean clean, IProgressMonitor monitor)
			throws CoreException
	{
		project.typeCheck(clean, monitor);
	}

	@Override
	public String getName()
	{
		return project.getName();
	}

	@Override
	public String getNature()
	{
		return project.getVdmNature();
	}

	@Override
	public List<ICmlSourceUnit> getSourceUnits() throws CoreException
	{
		List<IVdmSourceUnit> vdmsources = project.getSpecFiles();
		List<ICmlSourceUnit> sources = new Vector<ICmlSourceUnit>();

		for (IVdmSourceUnit s : vdmsources)
		{
			sources.add((ICmlSourceUnit) s.getAdapter(ICmlSourceUnit.class));
		}
		return sources;
	}

	@Override
	public File getFile(IFile file)
	{
		return project.getFile(file);
	}

	@Override
	public boolean isModelFile(IFile file) throws CoreException
	{
		return project.isModelFile(file);
	}

	@Override
	public IFile findIFile(File file)
	{
		return project.findIFile(file);
	}

	@Override
	public File getSystemFile(IPath path)
	{
		return project.getSystemFile(path);
	}

	@Override
	public File getFile(IWorkspaceRoot wroot, IPath path)
	{
		return project.getFile(wroot, path);
	}

	@Override
	public ICmlSourceUnit findSourceUnit(IFile file) throws CoreException
	{
		IVdmSourceUnit source = project.findSourceUnit(file);
		if (source != null)
		{
			return (ICmlSourceUnit) source.getAdapter(ICmlSourceUnit.class);
		}
		return null;
	}

	@Override
	public ICmlModel getModel()
	{
		return (ICmlModel) project.getModel().getAdapter(ICmlModel.class);
	}

	@Override
	public ModelBuildPath getModelBuildPath()
	{
		return project.getModelBuildPath();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof CmlProject)
		{
			return project.equals(((CmlProject) obj).project);
		}
		return super.equals(obj);
	}
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter)
	{
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
	
	public void addUnsupportedMarkers(List<UnsupportedElementInfo> ueis)
	{
		for (UnsupportedElementInfo uei : ueis)
		{

			FileUtility.addMarker(this.findIFile(uei.getLocation().getFile()), uei.getMessage(), uei.getLocation(), IMarker.SEVERITY_WARNING, IBuilderVdmjConstants.PLUGIN_ID);
		}
	}

}
