package eu.compassresearch.ide.modelchecker;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

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
    
    public static void log(Exception exception){
		getDefault().getLog().log(new Status(IStatus.ERROR, MCConstants.PLUGIN_ID, Activator.class.getSimpleName(), exception));
	}

	public static void log(String message, Exception exception){
		getDefault().getLog().log(new Status(IStatus.ERROR, MCConstants.PLUGIN_ID, message, exception));
	}

	public static void logErrorMessage(String message){
		getDefault().getLog().log(new Status(IStatus.ERROR, MCConstants.PLUGIN_ID, message));

	}
    
  }
