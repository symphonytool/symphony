package eu.compassresearch.ide.cml.pogplugin.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class PogCommandStateProvider extends AbstractSourceProvider
{

	public final static String TP_STATE = "eu.compassresearch.ide.pogplugin.tpstate";
	public final static String ENABLED = "ENABLED";
	public final static String DISABLED = "DISABLED";
	private boolean enabled = false;

	public PogCommandStateProvider()
	{
		// TODO Auto-generated constructor stub
	}

	
	public void setVarState(boolean state){
		this.enabled = state;
		String value = enabled ? ENABLED : DISABLED;
	    fireSourceChanged(ISources.WORKBENCH, TP_STATE, value);		
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
	    map.put(TP_STATE, value);
	    return map;
	}

	@Override
	public String[] getProvidedSourceNames()
	{
		return new String[] { TP_STATE };
	}

}
