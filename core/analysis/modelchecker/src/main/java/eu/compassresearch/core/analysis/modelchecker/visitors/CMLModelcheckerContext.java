package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.UndefinedValue;

public class CMLModelcheckerContext {
	
	protected HashMap<Object, Object> info; 
	
	private Set<String> variables;
	
	private ArrayList<String> states;
	
	public static int ASSIGN_COUNTER;
	
	private StringBuilder scriptContent;

	public CMLModelcheckerContext() {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		variables = new LinkedHashSet<String>();
		states = new ArrayList<String>();
		ASSIGN_COUNTER = 0;
	}
	
	public CMLModelcheckerContext(int i) {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		variables = new LinkedHashSet<String>();
		states = new ArrayList<String>();
		ASSIGN_COUNTER = i;
	}
	
	public Binding getMaxBinding(){
		Binding maximalBinding = new NullBinding();
		for (String currVarname : variables) {
			maximalBinding = maximalBinding.addBinding("np", currVarname, new UndefinedValue());
		}
		return maximalBinding;
	}
	
	public Binding getMaxBindingWithStates(){
		Binding maximalBinding = new NullBinding();
		for (String currVarname : states) {
			maximalBinding = maximalBinding.addBinding("np", currVarname, new Int(currVarname));
		}
		return maximalBinding;
	}
	
	public void updateVariables(String s){
		variables.add(s);
	}
	
	public void updateStates(String s){
		states.add(s);
	}
	
	public Object putVarInBinding(Object key, Object value){
		StringBuilder s =  (StringBuilder) info.get(key);
		s.append(value);
		return info.put(key, s);
	}
	
	public Object putVarAttInBinding(Object key, Object key1, Object key2, Object value){
		Map<String,  Map <String, String>> s;
		Map<String, String> assign;
		if(info.get(key) != null){
			s = (HashMap<String, Map<String, String>>) info.get(key);
			if(s.get(key1) != null){
				assign = s.get(key1);
				assign.put((String) key2, (String) value);
				s.put((String) key1, assign);
			} else{
				assign = new HashMap<String, String>();
				assign.put((String) key2, (String) value);
				s.put((String) key1, assign);
			}
		} else{
			s = new HashMap<String,  Map <String, String>>();
			assign = new HashMap<String, String>();
			assign.put((String) key2, (String) value);
			s.put((String) key1, assign);
		}
		return info.put(key, s);
	}
	
	public Object putStates(String key, StringBuilder s){
		ArrayList<StringBuilder> state;
		if(info.get(key) != null){
			state = (ArrayList<StringBuilder>) info.get(key);
		} else{
			state = new ArrayList<StringBuilder>();
		}
		state.add(s);
		return info.put(key, state);
	}
	
	public Set<String> getVariables(){
		return variables;
	}
	
	public ArrayList<String> getStates(){
		return states;
	}
	
	public StringBuilder getScriptContent() {
		return scriptContent;
	}
	
	public void setVariables(Set<String> vars){
		this.variables = vars;
	}

	public void setScriptContent(StringBuilder scriptContent) {
		this.scriptContent = scriptContent;
	}
	
	public void copyVarDeclarationInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.VAR_DECLARATIONS_KEY)){
			this.info.put(Utilities.VAR_DECLARATIONS_KEY, otherContext.info.get(Utilities.VAR_DECLARATIONS_KEY));
		}
	}
	
	public void copyVarDelInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.DEL_BBINDING)){
			this.info.put(Utilities.DEL_BBINDING, otherContext.info.get(Utilities.DEL_BBINDING));
		}
	}
	
	public void copyAssignmentDefInfo(CMLModelcheckerContext otherContext){
		if (otherContext.info.containsKey(Utilities.ASSIGNMENT_DEFINITION_KEY)) {
			if(!this.info.containsKey(Utilities.ASSIGNMENT_DEFINITION_KEY)){
				this.info.put(Utilities.ASSIGNMENT_DEFINITION_KEY, otherContext.info.get(Utilities.ASSIGNMENT_DEFINITION_KEY));
			} else {
				Map<String, Map<String, String>> aux = (Map<String, Map<String, String>>) this.info.get(Utilities.ASSIGNMENT_DEFINITION_KEY);
				Map<String, Map<String, String>> other = (Map<String, Map<String, String>>) otherContext.info.get(Utilities.ASSIGNMENT_DEFINITION_KEY);
				
				for(Iterator<String> key = other.keySet().iterator(); key.hasNext();){
					aux.put(key.next(), other.get(key));
				}
				this.info.put(Utilities.ASSIGNMENT_DEFINITION_KEY,aux);
			}
		}
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
