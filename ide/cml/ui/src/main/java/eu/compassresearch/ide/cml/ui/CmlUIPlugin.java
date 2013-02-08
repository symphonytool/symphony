/*******************************************************************************
 * Copyright (c) 2009, 2011 Overture Team and others.
 *
 * Overture is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Overture is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Overture.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The Overture Tool web-site: http://overturetool.org/
 *******************************************************************************/
package eu.compassresearch.ide.cml.ui;

import org.eclipse.core.internal.runtime.Log;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class CmlUIPlugin extends AbstractUIPlugin {

	private static final boolean DEBUG = true;
	private static CmlUIPlugin plugin;
	private static ILog log;

	private static synchronized void setLogger(ILog iLog) { CmlUIPlugin.log = iLog; }
	public static synchronized ILog getLogger() { return CmlUIPlugin.log; }
	
	public BundleContext currentContext;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setLogger(this.getLog());
		plugin = this;
		this.currentContext = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static CmlUIPlugin getDefault() {
		return plugin;
	}

	public static void println(String s) {
		if (DEBUG)
			System.out.println(s);
	}

	public static void log(Exception exception) {
		getDefault().getLog().log(
				new Status(IStatus.ERROR, ICmlUiConstants.PLUGIN_ID,
						CmlUIPlugin.class.getSimpleName(), exception));
	}

	public static void log(String message, Exception exception) {
		getDefault().getLog().log(
				new Status(IStatus.ERROR, ICmlUiConstants.PLUGIN_ID, message,
						exception));
	}

	public static void logErrorMessage(String message) {
		getDefault().getLog().log(
				new Status(IStatus.ERROR, ICmlUiConstants.PLUGIN_ID, message));

	}

	public static void test() {
	}
}
