package eu.compassresearch.core.s2c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import eu.compassresearch.core.s2c.dom.State;
import eu.compassresearch.core.s2c.dom.StateMachine;
import eu.compassresearch.core.s2c.dom.Transition;

public class SysMlToCmlTranslator
{
	private StateMachine sm;

	public SysMlToCmlTranslator(StateMachine sm)
	{
		this.sm = sm;
	}

	/**
	 * a naive translation from the uml dom
	 * 
	 * @param output
	 * @throws FileNotFoundException
	 */
	public void translate(File output) throws FileNotFoundException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("process " + sm.name + "= begin\n");

		// hardcoded section which should come from the class
		sb.append("state\n");
		sb.append("\n");
		sb.append("b: bool\n");
		sb.append("x: int\n");
		sb.append("\n");
		sb.append("operations\n");
		sb.append("Operation1 : int*int==>bool\n");
		sb.append("Operation1(x,e)==return x>e\n");
		sb.append("\n");

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
				sb.append(state.entry.name + " ; ");
			}

			List<Transition> transitions = getTransitions(state);

			for (Iterator<Transition> iterator = transitions.iterator(); iterator.hasNext();)
			{
				Transition t = iterator.next();

				sb.append("\n(");
				if (t.constraint != null)
				{
					sb.append("[ " + t.constraint.expression + " ] & ");
				}

				if (t.effect != null)
				{
					sb.append(t.effect.body + " ; ");
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

		sb.append("\n\n@ Initial\n\nend");

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
		} finally
		{
			out.close();
		}
	}

	String getCmlName(String name)
	{
		return name.replace(' ', '_');
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
