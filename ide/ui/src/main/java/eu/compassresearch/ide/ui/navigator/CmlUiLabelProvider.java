package eu.compassresearch.ide.ui.navigator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.overture.ide.ui.internal.viewsupport.VdmElementLabels;
import org.overture.ide.ui.internal.viewsupport.VdmUILabelProvider;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.ui.editor.syntax.CmlElementImageProvider;
import eu.compassresearch.ide.ui.editor.syntax.OutlineEntryType;

public class CmlUiLabelProvider extends VdmUILabelProvider
{

	// TODO Initialize map w/ correct number of images
	private static Map<String, Image> imageCache = new HashMap<String, Image>();

	// DecorationgVdmLabelProvider vdmlabelProvider = new DecorationgVdmLabelProvider(new VdmUILabelProvider());
	public CmlUiLabelProvider()
	{
		fImageLabelProvider = new CmlElementImageProvider();
	}

	/*
	 * @see ILabelProvider#getImage(Object)
	 */
	public Image getImage(Object element)
	{
		Image img = null;
		img = super.getImage(element);

		if (img == null)
		{
			return OutlineEntryType.getImageForElement(element);

		}
		return img;
	}

	public StyledString getStyledText(Object element)
	{
		StyledString string = CmlElementLabels.getStyledTextLabel(element, (evaluateTextFlags(element) | VdmElementLabels.COLORIZE));

		String decorated = decorateText(string.getString(), element);
		if (decorated != null)
		{
			return StyledCellLabelProvider.styleDecoratedString(decorated, StyledString.DECORATIONS_STYLER, string);
		}
		return string;
	}

	/*
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element)
	{
		String text = super.getText(element);

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
