package eu.compassresearch.core.s2c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import eu.compassresearch.core.s2c.dom.ClassDefinition;
import eu.compassresearch.core.s2c.dom.DataType;
import eu.compassresearch.core.s2c.dom.EnumType;
import eu.compassresearch.core.s2c.dom.Operation;
import eu.compassresearch.core.s2c.dom.Parameter;
import eu.compassresearch.core.s2c.dom.Property;
import eu.compassresearch.core.s2c.dom.Signal;
import eu.compassresearch.core.s2c.dom.State;
import eu.compassresearch.core.s2c.dom.StateMachine;
import eu.compassresearch.core.s2c.dom.Transition;
import eu.compassresearch.core.s2c.dom.Type;

public class SysMlToCmlTranslator {
	protected StateMachine sm;
	protected ClassDefinition cdef;
	protected List<Signal> signals;
	protected List<ClassDefinition> classes;
	protected List<DataType> datatypes;

	public SysMlToCmlTranslator(List<Signal> signals, ClassDefinition cDef,
			StateMachine sm, List<ClassDefinition> classes,
			List<DataType> datatypes) {
		this.cdef = cDef;
		this.sm = sm;
		this.signals = signals;
		this.classes = classes;
		this.datatypes = datatypes;
	}

	/**
	 * a naive translation from the uml dom
	 * @param t 
	 * 
	 * @param output
	 * @throws FileNotFoundException
	 */
	public String translate(Transition t) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\t(");

		if (t.trigger != null && t.trigger.event != null) {
			Signal s = t.trigger.event.signal;
			if (s.property.size() > 0) {
				sb.append(t.trigger.event.signal.name);

				if (s.property.size() > 1) {
					sb.append("?mk_(");
					for (Iterator<Property> i = s.property.iterator(); i
							.hasNext();) {
						Property p = i.next();
						sb.append(p.name);
						if (i.hasNext())
							sb.append(", ");
					}
					sb.append(")");
				} else {
					sb.append("?" + s.property.get(0).name);
				}
				if (t.constraint != null) {
					sb.append(":("
							+ fixSyntaxErrors(t.constraint.expression + "")
							+ ")");
				}
				sb.append(" -> ");
				transitionAction(t, sb);
			} else {
				if (t.constraint != null) {
					sb.append("["
							+ fixSyntaxErrors(t.constraint.expression + "")
							+ "]&");

					sb.append("(");
					sb.append(t.trigger.event.signal.name);
					sb.append(" -> ");

					transitionAction(t, sb);

					sb.append(")");
				} else {
					sb.append(t.trigger.event.signal.name);
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

	protected void transitionAction(Transition t, StringBuilder sb) {
		if (t.effect != null) {
			sb.append(fixSyntaxErrors(t.effect.body + " ; "));
		}
		if (t.source.exit != null) {
			sb.append(getCmlName("exit_" + t.source.name) + ";");
		}
		sb.append(getCmlName(t.target.name));
	}

	public String stateAssignment(State s, State p) {
		if (p != null) {
			return "active_" + getCmlName(p.name) + " := <"
					+ getCmlName(s.name) + ">;";
		} else
			return "";
	}

	public String translate(State s, State p) {
		StringBuilder sb = new StringBuilder();

		if (s.substates.isEmpty()) {
			sb.append(getCmlName("exit_" + s.name) + " = ");
			if (s.exit != null) {
				sb.append(fixSyntaxErrors(s.exit + "\n\n"));
			} else {
				sb.append("Skip\n\n");
			}

			sb.append(getCmlName(s.name) + " = " + stateAssignment(s, p));
			if (s.name.equals("Final")) {
				sb.append("Stop\n\n");
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
			sb.append(getCmlName("exit_" + s.name) + " = ");
			if (s.exit != null) {
				sb.append(fixSyntaxErrors(s.exit + ";"));
			}
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

	protected void translateNonCompletionTransitions(StringBuilder sb,
			List<Transition> noncompletion) {
		if (noncompletion.size() > 0) {
			sb.append("(");
			for (Iterator<Transition> iterator = noncompletion.iterator(); iterator
					.hasNext();) {
				Transition t = iterator.next();
				sb.append(translate(t));

				if (iterator.hasNext()) {
					sb.append("\n\t[]");
				}
			}
			sb.append("\n)");
		} else {
			sb.append("Skip\n");
		}

		
	}

	protected void translateTransitions(StringBuilder sb,
			List<Transition> completion, List<Transition> noncompletion) {
		if (completion.size() > 0) {
			sb.append("(");

			StringBuilder elseguard = new StringBuilder();
			List<String> guards = new Vector<String>();
			for (Transition t: completion) {
				if (t.constraint != null && t.constraint.expression != null) {
					guards.add(fixSyntaxErrors(t.constraint.expression.toString()));
				}
			}
			for (Iterator<String> it = guards.iterator(); it.hasNext();) {
				elseguard.append("not("+it.next()+")");
				if (it.hasNext()) {
					elseguard.append(" and ");
				}
			}
			
			for (Iterator<Transition> iterator = completion.iterator(); iterator
					.hasNext();) {
				Transition t = iterator.next();
				sb.append(translate(t));
				
				if (iterator.hasNext()) {
					sb.append("\n\t[]");
				}
			}
			
			if (guards.size() > 0) {
				sb.append("\n\t[]");
				sb.append("\n\t[" + elseguard.toString() + "] & ");
				translateNonCompletionTransitions(sb, noncompletion);
				sb.append(")");
			} else {
				sb.append(") [] ");
				translateNonCompletionTransitions(sb, noncompletion);
			}
		} else {
			translateNonCompletionTransitions(sb, noncompletion);
		}
	}

	public Collection<File> translate(File output, boolean overwrite) throws FileNotFoundException {
		
		Collection<File> files = new Vector<File>();
		
		File specFolder = new File(output,sm.name);
		if(!specFolder.exists())
		{
			specFolder.mkdirs();
		}
		
		StringBuilder sb = new StringBuilder();
		
		printChannels(sb);
		
		files.add(writeTypes(specFolder,overwrite));

		files.addAll(writeClasses(specFolder,overwrite));

		sb.append("\n\nprocess " + makeNameCMLCompatible(sm.name) + " = begin\n");

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

		sb.append("\n\n@ " + getCmlName("Initial") + "\n\nend");

		System.out.println(sb.toString());
		
		files.add(writeSpecFile(new File(specFolder,"StateMachine_"+ sm.name + ".cml"), sb.toString(), overwrite));
		return files;
	}

	protected void printChannels(StringBuilder sb) {
		Set<String> channels = new HashSet<String>();
		if (signals.size() > 0) {
			sb.append("channels\n");
			for (Signal s : signals) {
				StringBuilder channel = new StringBuilder();
				channel.append("\t"+makeNameCMLCompatible(s.name));
				if (!s.property.isEmpty()) {
					channel.append(" : ");
					for (Iterator<Property> itr = s.property.iterator(); itr
							.hasNext();) {
						Property p = itr.next();
						channel.append(convertType(p.type));
						if (itr.hasNext()) {
							sb.append(" * ");
						}
					}
				}
				channel.append("\n");
				channels.add(channel.toString());
			}
			for (String s: channels) {
				sb.append(s);
			}
			sb.append("\n");
		}
	}
	

	protected Collection<File> writeClasses(File output, boolean overwrite) throws FileNotFoundException {
		
		Collection<File> files = new Vector<File>();
		
		for (ClassDefinition c : classes) {
			StringBuffer sb = new StringBuffer();
			sb.append("class ");
			sb.append(makeNameCMLCompatible(c.name) + " = begin\n");
			if (c.properties.size() > 0) {
				sb.append("state\n");
				for (Property p : c.properties) {
					sb.append("\t "+p.getVisibility() +" "+ makeNameCMLCompatible(p.name) + " : " + convertType(p.type)
							+ "\n");
				}
			}
//			if (c.operations.size() > 0) {
//				printOperations(sb, c.operations);
//			}
			sb.append("end\n\n");
			System.out.println(sb);
		files.add(	writeSpecFile(new File(output,c.name+".cml"),sb.toString(),overwrite));
		}
		return files;
	}

	protected static File writeSpecFile(File file, String content, boolean overwrite) throws FileNotFoundException
	{
		PrintWriter out = null;

		try {
			if(file.exists())
			{
				if(overwrite)
				{
				file.delete();
				}
			}
			
			if (!file.exists()) {
				out = new PrintWriter(file);
				out.print(content);
			}
			return file;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	protected File writeTypes(File output,  boolean overwrite) throws FileNotFoundException {
		StringBuffer sb = new StringBuffer();
		
		if (cdef.types.size() > 0) {
			StringBuffer values = new StringBuffer();
			values.append("\nvalues\n");
			sb.append("types\n");
			for (Type t : cdef.types) {
				if (t instanceof EnumType) {
					EnumType et = (EnumType) t;
					sb.append(et.name + " = ");
					for (Iterator<String> iterator = et.literals.iterator(); iterator
							.hasNext();) {
						String lit = iterator.next();
						final String litQuote = String.format("<%s>", lit);
						sb.append(litQuote);
						values.append(lit + " = " + litQuote + "\n");
						if (iterator.hasNext()) {
							sb.append(" | ");
						}
					}
					sb.append("\n");
				}
			}
			sb.append(values + "\n\n");
		}

		List<DataType> ok_dt = new Vector<DataType>();
		for (DataType d: datatypes) {
			if (!d.name.equals("bool") &&
				!d.name.equals("int") &&
				!d.name.equals("real") &&
				!d.name.equals("double") &&
				!d.name.equals("char") &&
				!d.name.equals("nat") &&
				!d.name.equals("token") &&
				!d.name.startsWith("set of") &&
				!d.name.startsWith("seq of")
			) {
				ok_dt.add(d);
			}
		}
		
		if (ok_dt.size() > 0) {
			sb.append("types\n");
			for (DataType t : ok_dt) {
				sb.append("\t" + makeNameCMLCompatible(t.name) + " :: \n");
				for (Property p : t.properties) {
					sb.append("\t\t" + makeNameCMLCompatible(p.name) + ": "
							+ convertType(p.type) + "\n");
				}
				sb.append("\n");
			}
		}
		
		return writeSpecFile(new File(output,"global-types.cml"), sb.toString(), overwrite);
	}

	protected void printOperations(StringBuilder sbClass) {
		if (cdef.operations.size() == 0)
			return;
		StringBuffer sbOps = new StringBuffer();
		StringBuffer sbFuns = new StringBuffer();
		
		for (Operation op : cdef.operations) {
			StringBuffer sb = new StringBuffer();
			
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
			if (op.getParameters().size() == 0)
				sb.append("()");
			
			if(op.isStatic)
			{
				sb.append(" -> ");
			}else
			{
				sb.append(" ==> ");
			}
			
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
				sb.append((!op.isStatic?"return ":"") + op.body.body);
			}
			sb.append("\n");
			
			if(op.isStatic)
			{
				sbFuns.append(sb);
			}else
			{
				sbOps.append(sb);
			}
		}

		if (sbOps.length() > 0)
		{
			sbOps.insert(0, "operations\n");
			sbClass.append(sbOps);
		}

		if (sbFuns.length() > 0)
		{
			sbFuns.insert(0, "functions\n");
			sbClass.append(sbFuns);
		}

		sbClass.append("\n");
	}

//	protected void printOperations(StringBuilder sb) {
//		if (cdef.operations.size() == 0)
//			return;
//		sb.append("operations\n");
//		for (Operation op : cdef.operations) {
//			StringBuilder patterns = new StringBuilder();
//			sb.append("\t" + op.name + " : ");
//			patterns.append("\t" + op.name + "(");
//
//			for (Iterator<Parameter> iterator = op.getParameters().iterator(); iterator
//					.hasNext();) {
//				Parameter p = iterator.next();
//
//				sb.append(convertType(p.type));
//				patterns.append(p.name);
//				if (iterator.hasNext()) {
//					sb.append(" * ");
//					patterns.append(", ");
//				}
//			}
//
//			sb.append(" ==> ");
//			if (op.getReturn() == null) {
//				sb.append("()");
//			} else {
//				sb.append(convertType(op.getReturn().type));
//			}
//
//			sb.append("\n");
//			sb.append(patterns);
//			sb.append(") == ");
//			if (op.body == null) {
//				sb.append("is not yet specified");
//			} else {
//				sb.append("return " + op.body.body);
//			}
//			sb.append("\n");
//		}
//
//		sb.append("\n");
//	}

	protected void printState(StringBuilder sb) {
		int i = 0;
		for (State s : sm.allStates()) {
			if (s.substates.size() > 0)
				i++;
		}
		if (cdef.properties.size() + i == 0)
			return;

		sb.append("state\n");
		for (Property p : cdef.properties) {
			sb.append("\t" + p.name + " : " + convertType(p.type) + "\n");
		}
		for (State s : sm.allStates()) {
			if (!s.substates.isEmpty()) {
				sb.append("\tactive_" + getCmlName(s.name) + ": ");
				for (Iterator<State> it = s.substates.iterator(); it.hasNext();) {
					State aux = it.next();
					sb.append("<" + getCmlName(aux.name) + ">");
					if (it.hasNext())
						sb.append(" | ");
				}
				sb.append(" | <NO_STATE>");
				if (s.getInitial() != null) {
					sb.append(" := <" + getCmlName(s.getInitial().name) + ">");
				} else {
					sb.append(" := <NO_STATE>");
				}
				sb.append("\n");
			}
		}
		sb.append("\n\n");
	}

	protected String convertType(String type) {
		if (type.equals("Boolean")) {
			return "bool";
		} else if (type.equals("Integer")) {
			return "int";
		} else if (type.equals("String")) {
			return "seq of char";
		}
		return makeNameCMLCompatible(type);
	}

	String getCmlName(String name) {
		return "act_" + makeNameCMLCompatible(name);
	}

	String makeNameCMLCompatible(String name) {
		//return name.replace(' ', '_').replace('/', '_').replace('-', '_');
		String aux = name.replaceAll("[^a-zA-Z0-9_]", "_");
		if (aux.startsWith("_")) {
			aux = "$"+aux.substring(1);
		}
		return aux;
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
