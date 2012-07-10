package eu.compassresearch.equinox.example.helloworld;

import org.osgi.framework.*;

public class HelloWorld implements BundleActivator {

    public void start(BundleContext ctxt) {
	System.out.println("COMPASS Says hello");
    }
    public void stop (BundleContext ctxt) {
	System.out.println("COMPASS Says goodbye");
    }
}