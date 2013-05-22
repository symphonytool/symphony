package eu.compassresearch.ide.cml.ui.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;


/**
 * This class provides adapters that will adapt objects of type {@link IVDMSourceUnit} to objects of 
 * type {@link CmlSourceUnitAdapterFactory}.
 * 
 * @author ldc
 *
 */





public class CmlSourceUnitAdapterFactory implements IAdapterFactory
{

	@Override
	public Object getAdapter(Object adaptableObject,  @SuppressWarnings("rawtypes") Class adapterType)
	{
		if(adapterType == CmlSourceUnit.class)
		{
			if(adaptableObject instanceof IVdmSourceUnit){
				IVdmSourceUnit ivdmSource = (IVdmSourceUnit) adaptableObject;
			
				
					CmlSourceUnit r = CmlSourceUnit.getFromFileResource(ivdmSource.getFile());
					r.setUpSourceUnit(ivdmSource);
					return r;
					
				
			}
		}
	
		
		
		return null;
	}

	@Override
	public  @SuppressWarnings("rawtypes") Class[] getAdapterList()
	{
		
		return new Class[]{CmlSourceUnit.class};

	}

}
