package eu.compassresearch.producer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CompassActivator implements BundleActivator {
    
    public static CompassActivator instance;
    public static CompassActivator getInstance()
    {
	return instance;
    }

    private Thread t;
    private volatile Boolean run = new Boolean(true);
    public String takeMe;
    public Object takeMeLock = new Object();
    
    

    private class DoStuff implements Runnable
    {
	private int c;
	public void run() 
	{
	    boolean cont = run;
	    try {
		while(cont)
		    {
			synchronized(takeMeLock)
			    {
				if (takeMe != null) takeMeLock.wait();
				takeMe = "The Producer says hey for the !" + (c++) +"th time" ;
				takeMeLock.notifyAll();
			    }
			synchronized (run) { cont = run; }
			Thread.sleep(1000);
		    }
	    } catch (InterruptedException ie)
		{
		    // Thread.currentThread().interrupt(); No we are
		    // at top level in this thread no reason to set
		    // interrupt flag, appropriate action is taken: 
		    return;
		}
	}
    }

    @Override
	public void start(BundleContext bundle)  { 
	    synchronized(this.getClass()) { instance = this; }
	    synchronized(run) { run = true; }
	    t = new Thread(new DoStuff());
	    t.start(); 
	    System.out.println("Producer Online");
	};
    
    @Override
	public void stop(BundleContext bundle) { 
	    synchronized(this.getClass()) {instance = null;};
	    synchronized(run) { run = false; }
	    try { if (t != null) { t.interrupt(); t.join();} } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
	    System.out.println("Producer Off-line");
	};

}