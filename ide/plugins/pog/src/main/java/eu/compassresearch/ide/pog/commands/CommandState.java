package eu.compassresearch.ide.pog.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;

public class CommandState extends AbstractSourceProvider
{

	public final static String STATE_VAR = "eu.compassresearch.ide.pog.active";
	public final static String ENABLED = "ENABLED";
	public final static String DISABLED = "DISABLED";
	private boolean enabled = false;

	public CommandState()
	{
		// TODO Auto-generated constructor stub
	}

	
	public void setVarState(boolean state){
		this.enabled = state;
	}
	
	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getCurrentState()
	{
	    HashMap<String, String> map = new HashMap<String, String>(1);
	    String value = enabled ? ENABLED : DISABLED;
	    map.put(STATE_VAR, value);
	    return map;
	}

	@Override
	public String[] getProvidedSourceNames()
	{
		return new String[] { STATE_VAR };
	}

}
