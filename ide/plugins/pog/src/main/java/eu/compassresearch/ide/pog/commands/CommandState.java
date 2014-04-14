package eu.compassresearch.ide.pog.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class CommandState extends AbstractSourceProvider {

	public final static String MY_STATE = "eu.compassresearch.ide.pog.suballicon";
	public final static String ENABLED = "ENABLED";
	public final static String DISABLED = "DISABLED";
	private boolean enabled = false;

	@Override
	public void dispose() {
	}

	// We could return several values but for this example one value is
	// sufficient
	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { MY_STATE };
	}

	// You cannot return NULL
	@SuppressWarnings("unchecked")
	@Override
	public Map getCurrentState() {
		Map map = new HashMap(1);
		String value = enabled ? ENABLED : DISABLED;
		map.put(MY_STATE, value);
		return map;
	}

	public void enabled() {
		String value = ENABLED;
		enabled=true;
		fireSourceChanged(ISources.WORKBENCH, MY_STATE, value);
	}

	public void disable() {
		String value = DISABLED;
		enabled=false;
		fireSourceChanged(ISources.WORKBENCH, MY_STATE, value);

	}

}
