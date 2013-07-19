package eu.compassresearch.ide.internal.core.resources;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

public class CmlResourceAdapter implements IAdapterFactory
{

	@Override
	public Object getAdapter(Object adaptableObject,
			@SuppressWarnings("rawtypes") Class adapterType)
	{
		// project
		if (adapterType == ICmlProject.class)
		{
			if (adaptableObject instanceof IProject)
			{
				IVdmProject p = (IVdmProject) ((IProject) adaptableObject).getAdapter(IVdmProject.class);
				if (p != null)
				{
					return new CmlProject(p);
				}
			} else if (adaptableObject instanceof IVdmProject)
			{
				return new CmlProject((IVdmProject) adaptableObject);
			}
		} else if (adapterType == IVdmProject.class)
		{
			if (adaptableObject instanceof CmlProject)
			{
				return ((CmlProject) adaptableObject).project;
			}
		}else if (adapterType == IProject.class)
		{
			if (adaptableObject instanceof CmlProject)
			{
				return ((CmlProject) adaptableObject).project.getAdapter(IProject.class);
			}
		}

		// source unit
		else if (adapterType == IVdmSourceUnit.class)
		{
			if (adaptableObject instanceof CmlSourceUnit)
			{
				return ((CmlSourceUnit) adaptableObject).source;
			}
		} else if (adapterType == ICmlSourceUnit.class)
		{
			if (adaptableObject instanceof IVdmSourceUnit)
			{
				return new CmlSourceUnit((IVdmSourceUnit) adaptableObject);
			} else if (adaptableObject instanceof IFile)
			{
				IFile file = (IFile) adaptableObject;
				IVdmSourceUnit source = (IVdmSourceUnit) file.getAdapter(IVdmSourceUnit.class);

				if (source != null)
				{
					ICmlSourceUnit cmlSource = (ICmlSourceUnit) source.getAdapter(ICmlSourceUnit.class);
					return cmlSource;
				}

			}

		}

		// model
		else if (adapterType == IVdmModel.class)
		{
			if (adaptableObject instanceof ICmlModel)
			{
				return ((CmlModel) adaptableObject).model;
			}
		} else if (adapterType == ICmlModel.class)
		{
			if (adaptableObject instanceof IVdmModel)
			{
				return new CmlModel((IVdmModel) adaptableObject);
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList()
	{
		return new Class[] { ICmlProject.class, ICmlSourceUnit.class,
				ICmlModel.class, IVdmProject.class, IVdmModel.class,IProject.class };
	}

	// test case
	/*
	 * ICmlProject cp = (ICmlProject) project.getAdapter(ICmlProject.class); List<ICmlSourceUnit> ss =
	 * cp.getSourceUnits(); IVdmProject vp = (IVdmProject) cp.getAdapter(IVdmProject.class); IVdmSourceUnit vsu
	 * =(IVdmSourceUnit) ss.get(0).getAdapter(IVdmSourceUnit.class); ICmlModel m =(ICmlModel)
	 * vp.getModel().getAdapter(ICmlModel.class); IVdmModel vm = (IVdmModel) m.getAdapter(IVdmModel.class);
	 * ICmlSourceUnit su = (ICmlSourceUnit) ss.get(0).getFile().getAdapter(ICmlSourceUnit.class);
	 */
}
