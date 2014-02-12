/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class Activator"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class Activator extends AbstractUIPlugin implements IStartup {
	public static final String ID = "eu.compassresearch.ide.faulttolerance";
	private static Activator plugin;

	public Activator() {

	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public boolean isModelCheckerOk() {
		return eu.compassresearch.ide.modelchecker.Activator.FORMULA_OK;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		Image.RELOAD.updateImageDescriptor(reg, "reload.png");
	}

	@Override
	public void earlyStartup() {
	}

	public void log(Exception exception) {
		getLog().log(
				new Status(IStatus.ERROR, ID, Activator.class.getSimpleName(),
						exception));
	}
}
