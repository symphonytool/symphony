/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import eu.compassresearch.ide.modelchecker.Activator;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification, class Activator"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class RMVActivator extends AbstractUIPlugin {
	public static final String ID = "eu.compassresearch.ide.recoverymechanismsverification";
	private static RMVActivator plugin;

	public RMVActivator() {

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

	public static RMVActivator getDefault() {
		return plugin;
	}

	public boolean isModelCheckerOk() {
		return Activator.FORMULA_OK;
	}

}
