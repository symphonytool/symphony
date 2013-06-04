package eu.compassresearch.ide.cml.rttplugin;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class RttMbtCommandState extends AbstractSourceProvider {

	// keys that can be used with this source provider
	public static String keyIsGenerationContextTP = "eu.compassresearch.ide.cml.rtt-plugin.IsGenerationContextTP";
	public static String keyIsExecutionContextTP = "eu.compassresearch.ide.cml.rtt-plugin.IsExecutionContextTP";

	// values that can be used for the keys
	public static String TRUE = "truumpel";
	public static String FALSE = "falsumpel";

	// storage for keys and values
	private String[] keys = {
			keyIsGenerationContextTP,
			keyIsExecutionContextTP};
	private String[] values = {
			FALSE,
			FALSE};

	// set a key to a specified value (key and value must be supported)
	public void setValue(String key, String value) {
		if ((key == null) || (value == null)) {
			return;
		}
		for (int idx = 0; idx < keys.length; idx++) {
			if ((key.compareTo(keys[idx]) == 0) &&
				((value.compareTo(TRUE) == 0) || (value.compareTo(FALSE) == 0))) {
				values[idx] = value;
			    fireSourceChanged(ISources.WORKBENCH, key, value);
			}
		}
	}
	
	// set a key to a specified value (key and value must be supported)
	public String getValue(String key) {
		if (key == null) {
			return null;
		}
		for (int idx = 0; idx < keys.length; idx++) {
			if (key.compareTo(keys[idx]) == 0) {
				return values[idx];
			}
		}
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getCurrentState() {
	    Map<String, String> state = new HashMap<String, String>(1);
		for (int idx = 0; idx < keys.length; idx++) {
			state.put(keys[idx], values[idx]);
		}
		return state;
	}

	@Override
	public String[] getProvidedSourceNames() {
		return keys;
	}
}
