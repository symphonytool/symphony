package eu.compassresearch.ide.cml.rttplugin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
//import org.eclipse.jface.preference.PreferenceDialog;
//import org.eclipse.ui.preferences.*;//;
import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

public class Activator implements BundleActivator
  {

	// RTT-MBT client attribute
	static RttMbtClient client;

	static public RttMbtClient getClient() {
		return client;
	}
	
    @Override
    public void start(BundleContext arg0) throws Exception
      {
    	// load preferences
    	//PreferenceDialog prefs = new PreferenceDialog(null, null);
    	//prefs.open();
    	
    	// load setting
    	String server = "172.16.17.128";
    	Integer port = 9116;
    	String user = "uwe";
    	String id = "uschulze@informatik.uni-bremen.de";

    	// create client
    	client = new RttMbtClient(server, port, user, id);

      }
    
    @Override
    public void stop(BundleContext arg0) throws Exception
      {
        // TODO Auto-generated method stub
        /*
         * 
         * Do what needs to be done for tearing down this plug-in
         */

    	// store settings
    	
      }
    
  }
