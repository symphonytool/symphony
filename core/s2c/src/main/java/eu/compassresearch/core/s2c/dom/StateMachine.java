package eu.compassresearch.core.s2c.dom;

import java.util.List;
import java.util.Vector;

public class StateMachine extends NamedUmlNode
{
	public List<State> states = new Vector<State>();

	public List<Transition> transitions = new Vector<Transition>();

	public State lookupState(String id)
	{	
		for (State s : states)
		{
			State aux = s.lookupState(id);
			if (aux != null) return aux;
		}
		return null;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("States:\n");
		for (State s : states)
		{
			sb.append(s + "\n");
		}

		sb.append("\n\n");

		sb.append("Transitions:\n");
		for (Transition s : transitions)
		{
			sb.append(s + "\n");
		}
		return sb.toString();
	}
	
	public List<Transition> allTransitions() {
		List<Transition> alltransitions = new Vector<Transition>();
		alltransitions.addAll(this.transitions);
		for (State ss: states) {
			alltransitions.addAll(ss.allTransitions());
		}
		return alltransitions;
	}

}
