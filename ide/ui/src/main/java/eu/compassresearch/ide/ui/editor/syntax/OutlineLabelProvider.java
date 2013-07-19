package eu.compassresearch.ide.ui.editor.syntax;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.overture.ide.ui.internal.viewsupport.DecorationgVdmLabelProvider;
import org.overture.ide.ui.internal.viewsupport.VdmUILabelProvider;

import eu.compassresearch.ast.definitions.AProcessDefinition;

public class OutlineLabelProvider extends LabelProvider
{

	// TODO Initialize map w/ correct number of images
	private static Map<String, Image> imageCache = new HashMap<String, Image>();

	DecorationgVdmLabelProvider vdmlabelProvider = new DecorationgVdmLabelProvider(new VdmUILabelProvider());

	// static {imageCache.put(key, value)
	//
	// }
	//
	/*
	 * @see ILabelProvider#getImage(Object)
	 */
	public Image getImage(Object element)
	{
		Image img = null;
		if (element instanceof Wrapper)
		{
			Wrapper<?> w = (Wrapper<?>) element;
			img = vdmlabelProvider.getImage(w.value);
		}

		if (img == null)
		{
			return OutlineEntryType.getImageForElement(element);
		}
		return img;
	}

	/*
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element)
	{

		String text = null;
		if (element instanceof Wrapper)
		{
			Wrapper<?> w = (Wrapper<?>) element;
			text = vdmlabelProvider.getText(w.value);
		}
		if (text == null || text.startsWith("Unsupported"))
		{

			if (element instanceof AProcessDefinition)
			{
				text = ((AProcessDefinition) element).getName().getName();
			}
			text = element.toString();
		}

		if (text == null)
		{
			text = element.toString();
		}
		// TODO extract proper label texts
		return text;// element.toString();
	}

	public void dispose()
	{
		for (Iterator<Image> i = imageCache.values().iterator(); i.hasNext();)
		{
			((Image) i.next()).dispose();
		}
		imageCache.clear();
	}

	protected RuntimeException unknownElement(Object element)
	{
		return new RuntimeException("Unknown type of element in outline of type "
				+ element.getClass().getName());
	}

}
