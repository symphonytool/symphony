package eu.compassresearch.consumer;

import org.osgi.framework.*;
//import eu.compassresearch.producer.CompassActivator ;

public class CompassActivator implements BundleActivator {

    private Boolean run = new Boolean(true);
    private Object runLock = new Object();
    private Thread t;

    private class UseDoneStuff implements Runnable {

	
	public void run()
	{
	    boolean cont = true;
	    synchronized (runLock) { cont = run ; }
	    try {
		while (cont)
		    {
			eu.compassresearch.producer.CompassActivator prod= null;
			synchronized(eu.compassresearch.producer.CompassActivator.class) {
			 prod = eu.compassresearch.producer.CompassActivator.getInstance();
			}
			if (prod != null)
			    {
			    synchronized(prod.takeMeLock)
				{
				    if (prod.takeMe == null) prod.takeMeLock.wait();
				    System.out.println("Consumer: "+prod.takeMe);
				    prod.takeMe = null;
				    prod.takeMeLock.notifyAll();
				}
			    }
			else 
			    {
				System.out.println("Consumer: No producer");
				Thread.sleep(1000);
			    }
			synchronized (runLock) { cont = run ; }
		    }
	    } catch (InterruptedException ie)
		{
		    return;
		}
	    
	}
    }
    
    public void start(BundleContext bundle) {
	System.out.println("Consumer Online");
	synchronized(runLock) { this.run = true; }
	t = new Thread(new UseDoneStuff());
	t.start();
    }
    public void stop (BundleContext bundle) {
	synchronized(runLock) { this.run = false; }
	try { if (t != null) { t.interrupt(); t.join();} } catch (InterruptedException ie) { Thread.currentThread().interrupt();}
	System.out.println("Consumer Down");
    }
}