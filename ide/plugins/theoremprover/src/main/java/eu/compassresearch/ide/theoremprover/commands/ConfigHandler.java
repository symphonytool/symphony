package eu.compassresearch.ide.theoremprover.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;

import eu.compassresearch.ide.theoremprover.isabellelaunch.IsabelleSetupPreferencePage;

public class ConfigHandler extends AbstractHandler
{

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IPreferencePage page = new IsabelleSetupPreferencePage();
		PreferenceManager mgr = new PreferenceManager();
		IPreferenceNode node = new PreferenceNode("1", page);
		mgr.addToRoot(node);
		PreferenceDialog dialog = new PreferenceDialog(null, mgr);
		dialog.create();
		dialog.setMessage(page.getTitle());
		dialog.open();
		return null;
	}
}

