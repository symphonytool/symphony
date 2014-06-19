package eu.compassresearch.ide.theoremprover;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class IsabelleOn extends AbstractSourceProvider {

	public final static String MY_STATE = "eu.compassresearch.ide.theoremprover.tpon";
	public final static String ON = "ON";
	public final static String OFF = "OFF";
	private boolean on = true;


	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { MY_STATE };
	}

	// You cannot return NULL
	@SuppressWarnings("unchecked")
	@Override
	public Map getCurrentState() {
		Map map = new HashMap(1);
		String value = on ? ON : OFF;
		map.put(MY_STATE, value);
		return map;
	}

	public void turnOn() {
		String value = ON;
		on=true;
		fireSourceChanged(ISources.WORKBENCH, MY_STATE, value);
	}

	public void turnOff() {
		String value = OFF;
		on=false;
		fireSourceChanged(ISources.WORKBENCH, MY_STATE, value);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
