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
import eu.compassresearch.core.s2c.dom.State;
import eu.compassresearch.core.s2c.dom.StateMachine;
import eu.compassresearch.core.s2c.dom.Transition;

public class SysMlToCmlTranslator
{
	private StateMachine sm;
	private ClassDefinition cdef;

	public SysMlToCmlTranslator(ClassDefinition cDef, StateMachine sm)
	{
		this.cdef = cDef;
		this.sm = sm;
	}

	/**
	 * a naive translation from the uml dom
	 * 
	 * @param output
	 * @return 
	 * @throws FileNotFoundException
	 */
	public File translate(File output) throws FileNotFoundException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("process " + sm.name + " = begin\n");

		printState(sb);

		printOperations(sb);

		sb.append("actions\n");

		for (State state : sm.states)
		{
			sb.append(getCmlName(state.name) + " = ");

			if (state.name.equals("Final"))
			{
				sb.append("Stop\n\n");
				continue;
			}

			if (state.entry != null)
			{
				sb.append(fixSyntaxErrors(state.entry.name + " ; "));
			}

			List<Transition> transitions = getTransitions(state);

			for (Iterator<Transition> iterator = transitions.iterator(); iterator.hasNext();)
			{
				Transition t = iterator.next();

				sb.append("\n(");
				if (t.constraint != null)
				{
					sb.append("[ "
							+ fixSyntaxErrors(t.constraint.expression + "")
							+ " ] & ");
				}

				if (t.effect != null)
				{
					sb.append(fixSyntaxErrors(t.effect.body + " ; "));
				}

				sb.append(getCmlName(t.target.name));
				sb.append(")");
				if (iterator.hasNext())
				{
					sb.append("\n[]");
				}
			}

			sb.append("\n\n");
		}

		sb.append("\n\n@ " + getCmlName("Initial") + "\n\nend");

		System.out.println(sb.toString());

		PrintWriter out = null;

		try
		{
			final File file = new File(output, sm.name + ".cml");
			if (!file.exists())
			{
				out = new PrintWriter(file);
				out.print(sb.toString());
			}
			return file;
		} finally
		{
			if (out != null)
			{
				out.close();
			}
		}
	}

	protected void printOperations(StringBuilder sb)
	{
		sb.append("operations\n");
		for (Operation op : cdef.operations)
		{
			StringBuilder patterns = new StringBuilder();
			sb.append("\t" + op.name + " : ");
			patterns.append("\t" + op.name + "(");

			for (Iterator<Parameter> iterator = op.getParameters().iterator(); iterator.hasNext();)
			{
				Parameter p = iterator.next();

				sb.append(convertType(p.type));
				patterns.append(p.name);
				if (iterator.hasNext())
				{
					sb.append(" * ");
					patterns.append(", ");
				}
			}

			sb.append(" ==> ");
			if (op.getReturn() == null)
			{
				sb.append("()");
			} else
			{
				sb.append(convertType(op.getReturn().type));
			}

			sb.append("\n");
			sb.append(patterns);
			sb.append(") == ");
			if (op.body == null)
			{
				sb.append("is not yet specified");
			} else
			{
				sb.append("return " + op.body.body);
			}
			sb.append("\n");
		}

		sb.append("\n");
	}

	protected void printState(StringBuilder sb)
	{
		sb.append("state\n");
		for (Property p : cdef.properties)
		{
			sb.append("\t" + p.name + " : " + convertType(p.type) + "\n");
		}
	}

	private String convertType(String type)
	{
		if (type.equals("Boolean"))
		{
			return "bool";
		} else if (type.equals("Integer"))
		{
			return "int";
		} else if (type.equals("String"))
		{
			return "seq of char";
		}
		return null;
	}

	String getCmlName(String name)
	{
		return "act_" + name.replace(' ', '_');
	}

	/**
	 * Hacked string patching for the streaming model, should be either removed or made decent
	 * @param spec
	 * @return
	 */
	String fixSyntaxErrors(String spec)
	{
		//FIXME delete or reimplement this
		return spec.replace("; ;", "; ").replace("!=", "<>").replace("==", "########").replace(" =", ":=").replace("########", "=").replace("&&", "and").replace("||", "or");
	}

	public List<Transition> getTransitions(State state)
	{
		List<Transition> transitions = new Vector<Transition>();
		for (Transition t : sm.transitions)
		{
			if (t.source.id == state.id)
			{
				transitions.add(t);
			}
		}
		return transitions;
	}

}
