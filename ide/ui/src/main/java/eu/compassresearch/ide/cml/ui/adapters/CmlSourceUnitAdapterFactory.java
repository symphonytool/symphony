package eu.compassresearch.ide.cml.ui.adapters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.ide.cml.ui.editor.core.dom.ICmlSourceUnit;

/**
 * This class provides adapters that will adapt objects of  various types  to objects of type
 * {@link CmlSourceUnitAdapterFactory}.
 * 
 * <br><br>
 * The adapter types supported by this factory are:  {@link IVDMSourceUnit} and {@link IFile}.
 * 
 * @author ldc
 */

public class CmlSourceUnitAdapterFactory implements IAdapterFactory
{

	@Override
	public Object getAdapter(Object adaptableObject,
			@SuppressWarnings("rawtypes") Class adapterType)
	{
		if (adapterType == ICmlSourceUnit.class)
		{
			if (adaptableObject instanceof IVdmSourceUnit)
			{
				IVdmSourceUnit ivdmSource = (IVdmSourceUnit) adaptableObject;

				try
				{

					CmlSourceUnit r = CmlSourceUnit.getFromFileResource(ivdmSource.getFile());
					PSource ast = (PSource) ivdmSource.getParseList().get(0);
					r.setSourceAst(ast, !ivdmSource.hasParseErrors());
					r.setUpSourceUnit(ivdmSource);
					return r;
				}

				catch (NullPointerException e)
				{
					System.err.println("Failed to adapt IVdmSourceUnit to ICmlSourceUnit");
					e.printStackTrace();
				}
			}

			if (adaptableObject instanceof IFile)
			{
				IFile file = (IFile) adaptableObject;
				IVdmSourceUnit source = (IVdmSourceUnit) file.getAdapter(IVdmSourceUnit.class);
				
				if (source != null){
					ICmlSourceUnit cmlSource = (ICmlSourceUnit) source.getAdapter(ICmlSourceUnit.class);
					return cmlSource;
				}
					

			}
		}

		return null;
	}

	@Override
	public @SuppressWarnings("rawtypes")
	Class[] getAdapterList()
	{

		return new Class[] { ICmlSourceUnit.class };

	}

}
