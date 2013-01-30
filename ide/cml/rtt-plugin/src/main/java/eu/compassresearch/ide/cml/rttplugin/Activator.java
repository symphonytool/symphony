package eu.compassresearch.ide.cml.rttplugin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import eu.compassResearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;
import eu.compassResearch.rttMbtTmsClientApi.IRttMbtLoggingFacility;

public class Activator implements BundleActivator
  {

	// RTT-MBT client attribute
	static RttMbtClient client;

	static public RttMbtClient getClient() {
		return client;
	}

	// progress bars
	static RttMbtProgressBar progressBars;
	
	static public void setConsole(org.eclipse.swt.widgets.Text t) {
		if (client != null) {
			RttMbtConsoleLogger consoleLogger = new RttMbtConsoleLogger();
	    	consoleLogger.setConsole(t);
	    	client.setLoggingFacility(client.getProjectName(), consoleLogger);
		}
	}
	
	static public void addProgressBar(IRttMbtProgressBar.Tasks task, org.eclipse.swt.widgets.ProgressBar bar) {
		if (client != null) {
			if (progressBars == null) {
				progressBars = new RttMbtProgressBar();
			}
			progressBars.addProgressBar(task, bar);
	    	client.setProgressBar(progressBars);
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
