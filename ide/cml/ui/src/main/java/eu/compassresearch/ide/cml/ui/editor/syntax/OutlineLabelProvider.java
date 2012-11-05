package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ide.cml.ui.editor.syntax.CmlTreeContentProvider.Wrapper;

public class OutlineLabelProvider extends LabelProvider {

	// TODO Initialize map w/ correct number of images
	private static Map imageCache = new HashMap();

	// static {imageCache.put(key, value)
	//
	// }
	//
	/*
	 * @see ILabelProvider#getImage(Object)
	 */
	public Image getImage(Object element) {
		return OutlineEntryType.getImageForElement(element);
	}

	/*
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element) {
		// TODO extract proper label texts
		return element.toString();
	}

	public void dispose() {
		for (Iterator i = imageCache.values().iterator(); i.hasNext();) {
			((Image) i.next()).dispose();
		}
		imageCache.clear();
	}

	protected RuntimeException unknownElement(Object element) {
		return new RuntimeException(
				"Unknown type of element in outline of type "
						+ element.getClass().getName());
	}

}
