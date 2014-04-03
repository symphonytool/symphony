package eu.compassresearch.ide.internal.core.resources;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.core.ICmlCoreConstants;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

class CmlModel implements ICmlModel
{
	final IVdmModel model;

	public CmlModel(IVdmModel model)
	{
		this.model = model;
	}

	@Override
	public List<INode> getAst()
	{
		return model.getRootElementList();
	}

	@Override
	public Date getCheckedTime()
	{
		return model.getCheckedTime();
	}

	@Override
	public boolean isTypeCorrect()
	{
		return model.isTypeCorrect();
	}

	@Override
	public boolean isTypeChecked()
	{
		return model.isTypeChecked();
	}

	@Override
	public boolean hasFile(File file)
	{
		return model.hasFile(file);
	}

	@Override
	public boolean hasFile(IVdmSourceUnit file)
	{
		return model.hasFile(file);
	}

	@Override
	public boolean hasFile(ICmlSourceUnit file)
	{
		return model.hasFile(((IVdmSourceUnit) file.getAdapter(IVdmSourceUnit.class)));
	}

	@Override
	public boolean isParseCorrect()
	{
		return model.isParseCorrect();
	}

	@Override
	public boolean exists()
	{
		return model.exists();
	}

	@Override
	public ICmlSourceUnit getSourceUnit(IFile file)
	{
		IVdmSourceUnit source = model.getVdmSourceUnit(file);
		if (source != null)
		{
			return (ICmlSourceUnit) source.getAdapter(ICmlSourceUnit.class);
		}
		return null;
	}

	@Override
	public void clean()
	{
		model.clean();
	}

	@Override
	public List<ICmlSourceUnit> getSourceUnits()
	{
		List<IVdmSourceUnit> vdmsources = model.getSourceUnits();
		List<ICmlSourceUnit> sources = new Vector<ICmlSourceUnit>();

		for (IVdmSourceUnit s : vdmsources)
		{
			sources.add((ICmlSourceUnit) s.getAdapter(ICmlSourceUnit.class));
		}
		return sources;
	}

	@Override
	public void refresh(boolean completeRefresh, IProgressMonitor monitor)
	{
		model.refresh(completeRefresh, monitor);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof CmlModel)
		{
			return model.equals(((CmlModel) obj).model);
		}
		return super.equals(obj);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter)
	{
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	@Override
	@Deprecated
	public List<PSource> getAstSource()
	{
		List<PSource> list = new Vector<PSource>();
		for (ICmlSourceUnit unit : getSourceUnits())
		{
			list.add(unit.getParseNode());
		}
		return list;
	}

	@Override
	public boolean hasAttribute(String attributeName)
	{
		return model.hasAttribute(attributeName);
	}

	@Override
	public <K> K getAttribute(String attributeName, Class<K> defaultValue)
	{
		return model.getAttribute(attributeName, defaultValue);
	}

	@Override
	public <K> void setAttribute(String attributeName, K value)
	{
		model.setAttribute(attributeName, value);
	}

	@Override
	public void backup(final IFolder location)
	{
		final ICmlModel model = this;
		WorkspaceJob job = new WorkspaceJob("Backing up model to: " + location)
		{

			private void prepare(IFolder folder) throws CoreException
			{
				if (!folder.exists())
				{
					if (folder.getParent() instanceof IFolder)
					{
						prepare((IFolder) folder.getParent());
					}
					folder.create(true, false, null);
				}
			}

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException
			{
				if (!location.exists())
				{
					try
					{
						prepare(location);
					} catch (CoreException e)
					{
						return new Status(IStatus.ERROR, ICmlCoreConstants.PLUGIN_ID, "failed at creating output location for back of model", e);
					}
				}
				for (ICmlSourceUnit su : model.getSourceUnits())
				{
					IFile f = location.getFile(new Path(su.getFile().getName()
							+ ".bak"));
					if (f.exists())
					{
						try
						{
							f.delete(true, null);
						} catch (CoreException e)
						{
							return new Status(IStatus.ERROR, ICmlCoreConstants.PLUGIN_ID, "failed to delete existing file", e);
						}
					}
					try
					{
						f.create(su.getFile().getContents(), true, null);
						ResourceAttributes attributes = new ResourceAttributes();
						attributes.setReadOnly(true);
						f.setResourceAttributes(attributes);
					} catch (CoreException e)
					{
						return new Status(IStatus.ERROR, ICmlCoreConstants.PLUGIN_ID, "failed at creating backup for location: "
								+ location, e);
					}
				}

				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	@Override
	public List<PDefinition> getDefinitions()
	{
		List<PDefinition> defs = new Vector<PDefinition>();
		for (INode n : getAst())
		{
			if(n instanceof PDefinition)
			{
				defs.add((PDefinition) n);
			}
		}
		return defs;
	}
}
