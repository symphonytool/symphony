package eu.compassresearch.ide.cml.rttplugin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

public class Activator implements BundleActivator
  {

	// RTT-MBT client attribute
	static RttMbtClient client;

	static public RttMbtClient getClient() {
		return client;
	}

	static public void setConsole(org.eclipse.swt.widgets.Text t) {
		if (client != null) {
			RttMbtConsoleLogger consoleLogger = new RttMbtConsoleLogger();
	    	consoleLogger.setConsole(t);
	    	client.setLoggingFacility(consoleLogger);
		}
	}
	
	static public void setProgressBar(org.eclipse.swt.widgets.ProgressBar p) {
		if (client != null) {
			RttMbtProgressBar progressBar = new RttMbtProgressBar();
			progressBar.setProgressBar(p);
	    	client.setProgressBar(progressBar);
		}
	}

    @Override
    public void start(BundleContext arg0) throws Exception
      {
    	// load setting
    	String server = "localhost";
    	Integer port = 9116;
    	String user = "uwe";
    	String id = "uschulze@informatik.uni-bremen.de";

    	// create client
    	client = new RttMbtClient(server, port, user, id);
      }
    
    @Override
    public void stop(BundleContext arg0) throws Exception
      {
        /*
         * 
         * Do what needs to be done for tearing down this plug-in
         */

    	// store settings
    	
      }
    
  }
