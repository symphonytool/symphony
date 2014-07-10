package eu.compassresearch.core.s2c.dom;

import java.util.List;
import java.util.Vector;

public class State extends NamedUmlNode
{
	public NamedUmlNode entry;
	public NamedUmlNode exit;
	public String kind;
	public List<State> substates = new Vector<State>();
	public List<Transition> transitions = new Vector<Transition>();
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name + (kind != null ? " kind=" + kind : "")+"{\n");
		sb.append(entry != null ? " Entry=(" + entry+")" : "");sb.append("\n");
		sb.append(exit != null ? " Exit=(" + exit+")" : "");sb.append("\n");
		for (State s: substates) {
			sb.append(s.toString()+"\n");
		}
		for (Transition t: transitions) {
			sb.append(t.toString()+"\n");
		}
		sb.append("}\n");
		
		return sb.toString();
	}
	
	public State getInitial() {
		for (State s: substates) {
			if (s.name.startsWith("Initial")) {
				return s;
			}
		}
		return null;
	}
	
	public State lookupState(String id)
	{
		if (this.id.equals(id)) {
			return this;
		} else {
			for (State s : substates)
			{
				State aux = s.lookupState(id);
				if (aux != null) return aux;
			}
			return null;
		}
	}
	
	public List<Transition> allTransitions() {
		List<Transition> alltransitions = new Vector<Transition>();
		alltransitions.addAll(transitions);
		for (State s: substates) {
			alltransitions.addAll(s.allTransitions());
		}
		return alltransitions;
	}
	
	public List<State> allStates() {
		List<State> allstates= new Vector<State>();
		allstates.addAll(substates);
		for (State s: substates) {
			allstates.addAll(s.allStates());
		}
		return allstates;
	}
}
