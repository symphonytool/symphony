package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.UndefinedValue;

public class CMLModelcheckerContext {
	
	protected HashMap<Object, Object> info; 
	
	private Set<String> variables;
	
	public static int ASSIGN_COUNTER;
	
	private StringBuilder scriptContent;

	public CMLModelcheckerContext() {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		variables = new LinkedHashSet<String>();
		ASSIGN_COUNTER = 0;
	}
	
	public CMLModelcheckerContext(int i) {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		variables = new LinkedHashSet<String>();
		ASSIGN_COUNTER = i;
	}
	
	public Binding getMaxBinding(){
		Binding maximalBinding = new NullBinding();
		for (String currVarname : variables) {
			maximalBinding = maximalBinding.addBinding("np", currVarname, new UndefinedValue());
		}
		return maximalBinding;
	}
	
	public void updateVariables(String s){
		variables.add(s);
	}
	
	public Object putVarInBinding(Object key, Object value){
		StringBuilder s =  (StringBuilder) info.get(key);
		s.append(value);
		return info.put(key, s);
	}
	
	public Object putVarAttInBinding(Object key, Object key2, Object value){
		Map<String, ArrayList<String>> s;
		if(info.get(key) != null){
			s = (HashMap<String, ArrayList<String>>) info.get(key);
			if(s.get(key2) != null){
				s.get(key2).add((String) value);
			} else{
				ArrayList<String> aux = new ArrayList<String>();
				aux.add((String) value);
				s.put((String) key2, aux);
			}
			
		} else{
			s = new HashMap<String, ArrayList<String>>();
			ArrayList<String> aux = new ArrayList<String>();
			aux.add((String) value);
			s.put((String) key2, aux);
		}
		return info.put(key, s);
	}
	
	public Set<String> getVariables(){
		return variables;
	}
	
	public StringBuilder getScriptContent() {
		return scriptContent;
	}

	public void setScriptContent(StringBuilder scriptContent) {
		this.scriptContent = scriptContent;
	}
	
	public void copyChannelInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
			this.info.put(Utilities.CHANNEL_DEFINITIONS_KEY, otherContext.info.get(Utilities.CHANNEL_DEFINITIONS_KEY));
		}
	}
	public void copyIOCommDefInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
			this.info.put(Utilities.IOCOMM_DEFINITIONS_KEY, otherContext.info.get(Utilities.IOCOMM_DEFINITIONS_KEY));
		}
	}
	
}
