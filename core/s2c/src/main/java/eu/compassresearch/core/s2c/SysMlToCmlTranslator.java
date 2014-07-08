package eu.compassresearch.core.s2c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import eu.compassresearch.core.s2c.dom.ClassDefinition;
import eu.compassresearch.core.s2c.dom.Operation;
import eu.compassresearch.core.s2c.dom.Parameter;
import eu.compassresearch.core.s2c.dom.Property;
import eu.compassresearch.core.s2c.dom.Signal;
import eu.compassresearch.core.s2c.dom.State;
import eu.compassresearch.core.s2c.dom.StateMachine;
import eu.compassresearch.core.s2c.dom.Transition;

public class SysMlToCmlTranslator {
	private StateMachine sm;
	private ClassDefinition cdef;

	public SysMlToCmlTranslator(ClassDefinition cDef, StateMachine sm) {
		this.cdef = cDef;
		this.sm = sm;
	}

	/**
	 * a naive translation from the uml dom
	 * 
	 * @param output
	 * @throws FileNotFoundException
	 */
	public String translate(Transition t) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\t(");

		if (t.trigger != null) {
			Signal s = t.trigger.event.signal;
			if (s.property.size() > 0) {
				sb.append(t.trigger.event.name);
				
				
				if (s.property.size() > 1) {
					sb.append("?mk_(");
					for (Iterator<Property> i = s.property.iterator(); i.hasNext();) {
						Property p = i.next();
						sb.append(p.name);
						if (i.hasNext())
							sb.append(", ");
					}
					sb.append(")");
				} else {
					sb.append("?"+s.property.get(0).name);
				}
				if (t.constraint != null) {
					sb.append(":("
							+ fixSyntaxErrors(t.constraint.expression + "")
							+ ")");
					sb.append(" -> ");

					transitionAction(t, sb);
				} else {
					sb.append(t.trigger.event.name);
					sb.append(" -> ");

					transitionAction(t, sb);
				}
			} else {
				if (t.constraint != null) {
					sb.append("["
							+ fixSyntaxErrors(t.constraint.expression + "")
							+ "]&");

					sb.append("(");
					sb.append(t.trigger.event.name);
					sb.append(" -> ");

					transitionAction(t, sb);

					sb.append(")");
				} else {
					sb.append(t.trigger.event.name);
					sb.append(" -> ");

					transitionAction(t, sb);
				}
			}

		} else {
			if (t.constraint != null) {
				sb.append("[" + fixSyntaxErrors(t.constraint.expression + "")
						+ "]&");

				sb.append("(");

				transitionAction(t, sb);

				sb.append(")");
			} else {

				transitionAction(t, sb);
			}

		}
		sb.append(")");
		return sb.toString();
	}

	private void transitionAction(Transition t, StringBuilder sb) {
		if (t.effect != null) {
			sb.append(fixSyntaxErrors(t.effect.body + " ; "));
		}
		if (t.source.exit != null) {
			sb.append("exit_"+getCmlName(t.source.name) + ";");
		}
		sb.append(getCmlName(t.target.name));
	}

	public String stateAssignment(State s, State p) {
		if (p != null) {
			return "active_"+getCmlName(p.name)+" := <"+getCmlName(s.name)+">;";
		} else
			return "";
	}
	
	public String translate(State s, State p) {
		StringBuilder sb = new StringBuilder();

		if (s.substates.isEmpty()) {
			sb.append("exit_"+getCmlName(s.name)+" = ");
			if (s.exit != null) {
				sb.append(fixSyntaxErrors(s.exit + "\n\n"));
			} else {
				sb.append("Skip\n\n");
			}
			
			
			sb.append(getCmlName(s.name) + " = "+stateAssignment(s,p));
			if (s.name.equals("Final")) {
				sb.append("Stop\n\n");
				return sb.toString();
			}
			if (s.entry != null) {
				sb.append(fixSyntaxErrors(s.entry.name + " ; "));
			}

			List<Transition> transitions = getTransitions(s);

			if (transitions.size() > 0) {
				sb.append("(");
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
			sb.append("\n");

			for (State ss : s.substates) {
				sb.append(translate(ss,s));
			}

		} else {
			sb.append("exit_"+getCmlName(s.name)+" = ");
			if (s.exit != null) {
				sb.append(fixSyntaxErrors(s.exit + ";"));
			}
			sb.append("(\n");
			sb.append("\tcases active_"+getCmlName(s.name)+":\n");
			for (Iterator<State> it = s.substates.iterator(); it.hasNext();) {
				State aux = it.next();
				sb.append("\t<"+getCmlName(aux.name)+"> -> exit_"+getCmlName(aux.name)+",\n");
			}
			sb.append("\tothers -> Skip\n");
			sb.append("end\n");
			sb.append(")\n\n");
			
			sb.append(getCmlName(s.name) + " = (" +stateAssignment(s,p));
			if (s.entry != null) {
				sb.append(fixSyntaxErrors(s.entry.name + " ; "));
			}
			
			for (State ss: s.substates) {
				if (ss.name.startsWith("Initial")) {
					sb.append(getCmlName(ss.name));
				}
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
				sb.append(translate(ss,s));
			}
		}
		return sb.toString();
	}

	public File translate(File output) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		sb.append("process " + sm.name + " = begin\n");

		printState(sb);

		printOperations(sb);

		sb.append("actions\n");

		for (State state : sm.states) {
			sb.append(translate(state,null));
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

		sb.append("\n\n@ " + getCmlName("Initial") + "\n\nend");

		System.out.println(sb.toString());

		PrintWriter out = null;

		try {
			final File file = new File(output, sm.name + ".cml");
			if (!file.exists()) {
				out = new PrintWriter(file);
				out.print(sb.toString());
			}
			return file;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	protected void printOperations(StringBuilder sb) {
		sb.append("operations\n");
		for (Operation op : cdef.operations) {
			StringBuilder patterns = new StringBuilder();
			sb.append("\t" + op.name + " : ");
			patterns.append("\t" + op.name + "(");

			for (Iterator<Parameter> iterator = op.getParameters().iterator(); iterator
					.hasNext();) {
				Parameter p = iterator.next();

				sb.append(convertType(p.type));
				patterns.append(p.name);
				if (iterator.hasNext()) {
					sb.append(" * ");
					patterns.append(", ");
				}
			}

			sb.append(" ==> ");
			if (op.getReturn() == null) {
				sb.append("()");
			} else {
				sb.append(convertType(op.getReturn().type));
			}

			sb.append("\n");
			sb.append(patterns);
			sb.append(") == ");
			if (op.body == null) {
				sb.append("is not yet specified");
			} else {
				sb.append("return " + op.body.body);
			}
			sb.append("\n");
		}

		sb.append("\n");
	}

	protected void printState(StringBuilder sb) {
		sb.append("state\n");
		for (Property p : cdef.properties) {
			sb.append("\t" + p.name + " : " + convertType(p.type) + "\n");
		}
		for (State s: sm.allStates()) {
			if (!s.substates.isEmpty()) {
				sb.append("\tactive_" + getCmlName(s.name) + ": ");
				for (Iterator<State> it = s.substates.iterator(); it.hasNext();) {
					State aux = it.next();
					sb.append("<"+getCmlName(aux.name)+">");
					if (it.hasNext()) sb.append(" | ");
				}
				for (State aux: s.substates) {
					if (aux.name.startsWith("Initial")) {
						sb.append(" := <"+getCmlName(aux.name)+">");
						break;
					}
				}
				sb.append("\n");
			}
		}
	}

	private String convertType(String type) {
		if (type.equals("Boolean")) {
			return "bool";
		} else if (type.equals("Integer")) {
			return "int";
		} else if (type.equals("String")) {
			return "seq of char";
		}
		return null;
	}

	String getCmlName(String name) {
		return "act_" + name.replace(' ', '_').replace('/', '_');
	}

	/**
	 * Hacked string patching for the streaming model, should be either removed
	 * or made decent
	 * 
	 * @param spec
	 * @return
	 */
	String fixSyntaxErrors(String spec) {
		// FIXME delete or reimplement this
		return spec.replace("; ;", "; ").replace("!=", "<>")
				.replace("==", "########").replace(" =", ":=")
				.replace("########", "=").replace("&&", "and")
				.replace("||", "or");
	}

	public List<Transition> getTransitions(State state) {
		List<Transition> transitions = new Vector<Transition>();
		List<Transition> alltransitions = sm.allTransitions();

		for (Transition t : alltransitions) {
			if (t.source.id == state.id) {
				transitions.add(t);
			}
		}
		return transitions;
	}

}
