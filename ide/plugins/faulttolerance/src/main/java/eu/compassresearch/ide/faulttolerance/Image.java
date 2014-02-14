/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.Bundle;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class Image"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public enum Image {
	FAULT_TOLERANCE_JOB_ICON("ft-graphic.png"), FT_RED("ft-red.png"), FT_BLUE(
			"ft-blue.png"), FT_GREEN("ft-green.png"), FT_ORANGE("ft-orange.png"), FAULT_TOLERANCE_CLOCK_ICON(
			"ft-graphic-clock.png"), FAULT_TOLERANCE_TICK_ICON(
			"ft-graphic-tick.png");

	private String iconFileName;

	private Image(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.createFromImage(Activator.getDefault()
				.getImageRegistry().get(name()));
	}
	
	public void updateImageDescriptor(ImageRegistry reg) {
		Bundle bundle = Activator.getDefault().getBundle();
		IPath path = new Path(path());
		URL url = FileLocator.find(bundle, path, null);
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		reg.put(name(), desc);
	}

	public String path() {
		return "icons/" + iconFileName;
	}
}
