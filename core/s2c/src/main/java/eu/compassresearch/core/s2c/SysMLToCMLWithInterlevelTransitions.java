package eu.compassresearch.core.s2c;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
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
	}

	public Collection<File> translate(File output, boolean overwrite)
			throws FileNotFoundException {

		Collection<File> files = new Vector<File>();

		File specFolder = new File(output, sm.name);
		if (!specFolder.exists()) {
			specFolder.mkdirs();
		}

		StringBuilder sb = new StringBuilder();

		printChannels(sb);

		files.add(writeTypes(specFolder, overwrite));

		files.addAll(writeClasses(specFolder, overwrite));

		sb.append("\n\nprocess " + makeNameCMLCompatible(sm.name)
				+ " = begin\n");

		printState(sb);

		printOperations(sb);

		sb.append("actions\n");

		for (State state : sm.states) {
			sb.append(translate(state, null));
			/*
			 * List<Transition> transitions = getTransitions(state);
			 * 
			 * for (Iterator<Transition> iterator = transitions.iterator();
			 * iterator.hasNext();) { Transition t = iterator.next();
			 * 
			 * sb.append("\n("); if (t.constraint != null) { sb.append("[ " +
			 * fixSyntaxErrors(t.constraint.expression + "") + " ] & "); }
			 * 
			 * if (t.effect != null) { sb.append(fixSyntaxErrors(t.effect.body +
			 * " ; ")); }
			 * 
			 * sb.append(getCmlName(t.target.name)); sb.append(")"); if
			 * (iterator.hasNext()) { sb.append("\n[]"); } }
			 */

			sb.append("\n\n");
		}

		sb.append("\n\n@ " + getCmlName(sm.getInitial().name) + "\n\nend");

		System.out.println(sb.toString());

		files.add(writeSpecFile(new File(specFolder, "StateMachine_" + sm.name
				+ ".cml"), sb.toString(), overwrite));
		return files;
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
		sb.append(getCmlName("exit_" + t.source.name));
		for (State x : states) {
			if (x.exit != null)
				sb.append(fixSyntaxErrors(";" + x.exit));
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
			if (s.entry != null) {
				states.add(s);
			}
			s = s.parent;
		}
		StringBuilder sb = new StringBuilder();

		for (int i = states.size() - 1; i >= 0; i--) {
			State x = states.get(i);
			sb.append(fixSyntaxErrors(x.entry.toString()));
			if (i > 0)
				sb.append("; ");
		}
		// sb.append(getCmlName(t.target.name));

		return sb.toString();
	}

	@Override
	protected void transitionAction(Transition t, StringBuilder sb) {
		String aux = generateExitActions(t);
		if (aux.length() > 0) {
			sb.append(aux + "; ");
		}
		if (t.effect != null) {
			sb.append(fixSyntaxErrors(t.effect.body + " ; "));
		}
		aux = generateEntryActions(t);
		if (aux.length() > 0) {
			sb.append(aux + "; ");
		}
		sb.append(getCmlName(t.target.name));
	}

	public String generateExitActions(State s) {
		StringBuilder sb = new StringBuilder();
		if (s.substates.isEmpty()) {
			sb.append(getCmlName("exit_" + s.name) + " = ");
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
				sb.append("\t<" + getCmlName(aux.name) + "> -> "
						+ getCmlName("exit_" + aux.name) + ",\n");
			}
			sb.append("\tothers -> Skip\n");
			sb.append("\tend\n");
			sb.append(")");
			if (s.exit != null) {
				sb.append(fixSyntaxErrors("; " + s.exit));
			}
			sb.append("\n\n");
		}
		return sb.toString();
	}

	public State lca(Transition t) {
		State source = t.source;
		State target = t.target;
		return lca(source, target);
	}

	public State lca(State source, State target) {
		if (source == target)
			return source;
		if (source == null || target == null)
			return null;
		if (source.parent == target)
			return source.parent;
		if (target.parent == source)
			return target.parent;
		return lca(source.parent, target.parent);
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

		sb.append(generateExitActions(s) + "\n\n");

		if (s.substates.isEmpty()) {
			sb.append(getCmlName(s.name) + " = " + stateAssignment(s, p));
			if (s.name.startsWith("Final")) {
				/*
				 * The completion transitions of the composite state are put in
				 * external choice with the final state because they are only
				 * executed if that state is reached. Stop is a unity of
				 * external choice, therefore the completion transitions can
				 * happen. Furthermore, they can be interrupted by the
				 * noncompletion transitions
				 */
				sb.append("Stop\n\n");
				if (s.parent != null) {
					List<Transition> transitions = getTransitions(s.parent);
					List<Transition> completion = new Vector<Transition>();

					for (Transition t : transitions) {
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

			for (Transition t : transitions) {
				if (t.trigger == null || t.trigger.event == null) {
					completion.add(t);
				} else {
					noncompletion.add(t);
				}
			}

			// if do activities are to be treated, add them here being
			// interrupted by the noncompletion transitions.

			translateTransitions(sb, completion, noncompletion);

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
				if (s.substates.size() == 0) {
					sb.append("Stop");
				} else {
					System.out
							.println("The state "
									+ s.name
									+ " should have an initial state. I'll offer the internal choice of all the substates for robustness.");
					sb.append("(");
					for (Iterator<State> it = s.substates.iterator(); it
							.hasNext();) {
						State ss = it.next();
						sb.append(getCmlName(ss.name));
						if (it.hasNext()) {
							sb.append(" |~| ");
						}
					}
					sb.append(")");
				}
			}
			sb.append(")");

			List<Transition> transitions = getTransitions(s);

			if (transitions.size() > 0) {
				sb.append("/_\\(");

				for (Iterator<Transition> iterator = transitions.iterator(); iterator
						.hasNext();) {
					Transition t = iterator.next();
					sb.append(translate(t));

					if (iterator.hasNext()) {
						sb.append("\n\t[]");
					}
				}
				sb.append("\n)");
			}
			sb.append("\n\n");

			for (State ss : s.substates) {
				sb.append(translate(ss, s));
			}
		}
		return sb.toString();
	}

}
