package eu.compassresearch.core.s2c;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import eu.compassresearch.core.s2c.dom.ClassDefinition;
import eu.compassresearch.core.s2c.dom.DataType;
import eu.compassresearch.core.s2c.dom.Signal;
import eu.compassresearch.core.s2c.dom.State;
import eu.compassresearch.core.s2c.dom.StateMachine;
import eu.compassresearch.core.s2c.dom.Transition;

public class SysMLToCMLWithInterlevelTransitions extends SysMlToCmlTranslator {

	public SysMLToCMLWithInterlevelTransitions(List<Signal> signals,
			ClassDefinition cDef, StateMachine sm,
			List<ClassDefinition> classes, List<DataType> datatypes) {
		super(signals, cDef, sm, classes, datatypes);
		// TODO Auto-generated constructor stub
	}
	
	public String generateExitActions(Transition t) {
		List<State> states = new Vector<State>();
		State lca = lca(t);
		if (t.source == lca) {
			return "";
		}
		State s = t.source.parent;
		while (s != lca) {
			states.add(s);
			s = s.parent;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(getCmlName("exit_"+t.source.name));
		for (State x: states) {
			if (x.exit != null)
				sb.append(fixSyntaxErrors(";"+ x.exit));
		}
		return sb.toString();
	}
	
	public String generateEntryActions(Transition t) {
		List<State> states = new Vector<State>();
		State lca = lca(t);
		if (t.target == lca) {
			return "";
		}
		State s = t.target.parent;
		while (s != lca) {
			states.add(s);
			s = s.parent;
		}
		StringBuilder sb = new StringBuilder();
		
		for (int i = states.size()-1; i >= 0; i--) {
			State x = states.get(i);
			if (x.entry != null)
				sb.append(fixSyntaxErrors(x.entry+";"));
		}
		sb.append(getCmlName(t.source.name));
		
		return sb.toString();
	}
	
	public String generateExitActions(State s) {
		StringBuilder sb = new StringBuilder();
		if (s.substates.isEmpty()) {
			sb.append(getCmlName("exit_"+s.name)+" = ");
			if (s.exit != null) {
				sb.append(fixSyntaxErrors(s.exit.toString()));
			} else {
				sb.append("Skip");
			}
		} else {
			sb.append(getCmlName("exit_" + s.name) + " = ");

			sb.append("(\n");
			sb.append("\tcases active_" + getCmlName(s.name) + ":\n");
			for (Iterator<State> it = s.substates.iterator(); it.hasNext();) {
				State aux = it.next();
				sb.append("\t<" + getCmlName(aux.name) + "> -> " + getCmlName("exit_"
						+aux.name) + ",\n");
			}
			sb.append("\tothers -> Skip\n");
			sb.append("end\n");
			sb.append(")\n\n");
			if (s.exit != null) {
				sb.append(fixSyntaxErrors(";\n "+s.exit));
			}
		}
		return sb.toString();
	}
	
	public State lca(Transition t) {
		State source = t.source;
		State target = t.target;
		return lca(source,target);
	}
	
	public State lca(State source, State target) {
		if (source == target) return source;
		if (source.parent == target) return source.parent;
		if (target.parent == source) return target.parent;
		return lca(source.parent,target.parent);
	}
	
	protected void translateCompletionTransitions(StringBuilder sb,
			List<Transition> completion) {
		if (completion.size() > 0) {
			for (Iterator<Transition> iterator = completion.iterator(); iterator
					.hasNext();) {
				Transition t = iterator.next();
				sb.append(translate(t));
				
				if (iterator.hasNext()) {
					sb.append("\n\t[]");
				}
			}
		}
	}

	public String translate(State s, State p) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(generateExitActions(s)+"\n\n");
		
		if (s.substates.isEmpty()) {
			sb.append(getCmlName(s.name) + " = " + stateAssignment(s, p));
			if (s.name.startsWith("Final")) {
				/* The completion transitions of the composite state are put in external choice with the final state
				 * because they are only executed if that state is reached. Stop is a unity of external choice, therefore
				 * the completion transitions can happen. Furthermore, they can be interrupted by the noncompletion transitions */
				sb.append("Stop\n\n");
				if (s.parent != null) {
					List<Transition> transitions = getTransitions(s.parent);
					List<Transition> completion = new Vector<Transition>();

					for (Transition t: transitions) {
						if (t.trigger == null || t.trigger.event == null) {
							completion.add(t);
						}
					}
					if (completion.size() > 0) {
						sb.append("[]\n");
						translateCompletionTransitions(sb, completion);
					}
				}
				return sb.toString();
			}
			if (s.entry != null) {
				sb.append(fixSyntaxErrors(s.entry.name + " ; "));
			}

			List<Transition> transitions = getTransitions(s);
			
			List<Transition> completion = new Vector<Transition>();
			List<Transition> noncompletion = new Vector<Transition>();

			for (Transition t: transitions) {
				if (t.trigger == null || t.trigger.event == null) {
					completion.add(t);
				} else {
					noncompletion.add(t);
				}
			}
			
			// if do activities are to be treated, add them here being interrupted by the noncompletion transitions.
			
			translateTransitions(sb, completion,noncompletion);
			
			sb.append("\n");

			for (State ss : s.substates) {
				sb.append(translate(ss, s));
			}

		} else {

			sb.append(getCmlName(s.name) + " = (" + stateAssignment(s, p));
			if (s.entry != null) {
				sb.append(fixSyntaxErrors(s.entry.name + " ; "));
			}
			
			if (s.getInitial() != null) {
				sb.append(getCmlName(s.getInitial().name));
			} else {
				System.out.println("The state "+s.name+" should have an initial state");
			}
			sb.append(")");

			List<Transition> transitions = getTransitions(s);
			
			if (transitions.size() > 0) {
				sb.append("/_\\(");
			}

			for (Iterator<Transition> iterator = transitions.iterator(); iterator
					.hasNext();) {
				Transition t = iterator.next();
				sb.append(translate(t));

				if (iterator.hasNext()) {
					sb.append("\n\t[]");
				}
			}
			sb.append("\n)");
			sb.append("\n\n");

			for (State ss : s.substates) {
				sb.append(translate(ss, s));
			}
		}
		return sb.toString();
	}
	

}
