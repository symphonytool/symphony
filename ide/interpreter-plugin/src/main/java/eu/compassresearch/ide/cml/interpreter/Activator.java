package eu.compassresearch.ide.cml.interpreter;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin
  {
    
    @Override
    public void start(BundleContext context) throws Exception
      {
        /*
         * 
         * Do what needs to be done for initializing this plug-in
         */
    		super.start(context);
      }
    
    @Override
    public void stop(BundleContext context) throws Exception
      {
    	super.stop(context);
        /*
         * 
         * Do what needs to be done for tearing down this plug-in
         */
      }
    
  }
