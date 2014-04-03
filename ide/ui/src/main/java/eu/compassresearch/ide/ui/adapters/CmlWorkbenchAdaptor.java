package eu.compassresearch.ide.ui.adapters;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.overture.ide.ui.adapters.IOvertureWorkbenchAdapter;
import org.overture.ide.ui.internal.viewsupport.DecorationgVdmLabelProvider;
import org.overture.ide.ui.internal.viewsupport.VdmColoringLabelProvider;

import eu.compassresearch.ide.ui.navigator.CmlTreeContentProvider;
import eu.compassresearch.ide.ui.navigator.CmlUiLabelProvider;

public class CmlWorkbenchAdaptor implements IOvertureWorkbenchAdapter
{

	VdmColoringLabelProvider labelProvider;
	ITreeContentProvider contentProvider;

	public CmlWorkbenchAdaptor()
	{
		this.contentProvider = new CmlTreeContentProvider();
		this.labelProvider = new DecorationgVdmLabelProvider(new CmlUiLabelProvider());
	}

	@Override
	public StyledString getStyledText(Object element)
	{
		return labelProvider.getStyledStringProvider().getStyledText(element);
	}

	@Override
	public Object[] getChildren(Object o)
	{
		return this.contentProvider.getChildren(o);
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object)
	{
		Image img = labelProvider.getImage(object);
		if (img != null)
		{
			return ImageDescriptor.createFromImage(img);
		}
		return null;
	}

	@Override
	public String getLabel(Object o)
	{
		return labelProvider.getText(o);
	}

	@Override
	public Object getParent(Object o)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
