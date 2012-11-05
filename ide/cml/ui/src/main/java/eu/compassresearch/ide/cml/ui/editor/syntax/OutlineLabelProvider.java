package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

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

		ImageDescriptor descriptor = null;
		//TODO add code for multiple images
		String img = "/icons/cview16/cml_file_tsk.png";
		URL url = this.getClass().getResource(img);
		descriptor= ImageDescriptor.createFromURL(url);
		Image image = (Image)imageCache.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageCache.put(descriptor, image);
		}
		return image;
	}



	/*
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element) {
		// TODO extract proper label texts
		return element.toString();
		//
		// Object o = new Object();
		// for (Method m : element.getClass().getMethods())
		// {
		// if ("getLocation".equals(m.getName()))
		// {
		// try {
		//
		// LexLocation loc = (LexLocation)m.invoke(o, new
		// Object[0]);
		//
		// } catch (Exception e)
		// {
		//
		// }
		// }
		// }

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
