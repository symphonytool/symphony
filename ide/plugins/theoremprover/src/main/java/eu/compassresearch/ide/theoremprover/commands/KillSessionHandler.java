package eu.compassresearch.ide.theoremprover.commands;

import isabelle.Platform;
import isabelle.Session;
import isabelle.eclipse.core.IsabelleCore;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import eu.compassresearch.ide.pog.PogPluginUtils;
import eu.compassresearch.ide.theoremprover.TPConstants;
import eu.compassresearch.ide.theoremprover.TPPluginDoStuff;
import eu.compassresearch.ide.theoremprover.TPPluginUtils;

public class KillSessionHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// we know we have a session because we checked for it outside
	//	Session sess = IsabelleCore.isabelle().session().get();	
		Bundle b = org.eclipse.core.runtime.Platform.getBundle(TPConstants.ISABELLE_ECLIPSE_CORE_PLUGIN_ID);
		try
		{
			b.stop();
			TPPluginUtils.disableKillIsabelle(event);
			PogPluginUtils.disableAllPosIcon(event);
		} catch (BundleException e)
		{
	//		MessageDialog.openError(parent, title, message);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		sess.stop();
		
		return null;
	}

}
