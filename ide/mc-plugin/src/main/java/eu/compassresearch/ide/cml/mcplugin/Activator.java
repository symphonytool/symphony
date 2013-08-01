package eu.compassresearch.ide.cml.mcplugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.eclipse.ui.IStartup;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.StandAloneFormulaIntegrationFactory;

public class Activator extends AbstractUIPlugin  {
	
	public static final String PLUGIN_ID = "mc-plugin";
    
	// The shared instance
	private static Activator plugin;
		
    /*
	@Override
	public void earlyStartup() {
    	
    	try {
			StandAloneFormulaIntegrationFactory.getInstance().createFormulaIntegrator();
		} catch (FormulaIntegrationException e) {
			throw new RuntimeException(e);
		}
	}
	*/

	@Override
	public void shutdown() throws CoreException {
		
	}

	@Override
	public void startup() throws CoreException {
		//TODO
	}

	
    public static Activator getDefault() {
		return plugin;
	}
    public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
    
  }
