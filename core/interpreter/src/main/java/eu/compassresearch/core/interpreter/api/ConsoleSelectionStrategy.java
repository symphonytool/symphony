package eu.compassresearch.core.interpreter.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.Dialect;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.Interpreter;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.values.Value;
import org.overture.parser.lex.LexTokenReader;
import org.overture.parser.syntax.ExpressionReader;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.core.interpreter.api.transitions.AbstractSilentTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public class ConsoleSelectionStrategy implements SelectionStrategy
{

	Scanner scanIn = new Scanner(System.in);
	private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();
	private boolean hideSilentTransitions;
	CmlTransitionSet availableChannelEvents;

	public ConsoleSelectionStrategy()
	{
		setHideSilentTransitions(true);
	}

	public ConsoleSelectionStrategy(boolean hideSilentTransitions)
	{
		this.setHideSilentTransitions(hideSilentTransitions);
	}

	private boolean isSystemSelect(CmlTransitionSet availableChannelEvents)
	{

		Set<AbstractSilentTransition> silentTransitions = availableChannelEvents.getSilentTransitionsAsSet();
		// don't let the system run the divergent processes since it will never stop
		int count = 0;

		for (AbstractSilentTransition st : silentTransitions)
			count = st.getEventSources().iterator().next().isDivergent() ? count + 1
					: count;

		return silentTransitions.size() > count;
	}

	private CmlTransition systemSelect(CmlTransitionSet availableChannelEvents)
	{
		rndSelect.choices(new CmlTransitionSet((Set) availableChannelEvents.getSilentTransitionsAsSet()));
		return rndSelect.resolveChoice();
	}

	private void printTransitions(List<CmlTransition> events)
	{
		for (int i = 0; i < events.size(); i++)
		{
			CmlTransition obsEvent = events.get(i);
			System.out.println("[" + i + "]" + obsEvent.toString());
		}
	}

	private CmlTransition userSelect(CmlTransitionSet availableChannelEvents)
	{
		List<CmlTransition> events = new ArrayList<CmlTransition>(availableChannelEvents.getAllEvents());

		int choiceNumber = -1;
		while (choiceNumber < 0 || choiceNumber >= events.size())
		{
			printTransitions(events);
			choiceNumber = scanIn.nextInt();
			if (choiceNumber < 0 || choiceNumber >= events.size())
				System.out.println("Please try again!");
		}

		CmlTransition chosenEvent = events.get(choiceNumber);

		if (chosenEvent instanceof LabelledTransition
				&& !((LabelledTransition) chosenEvent).getChannelName().isPrecise())
		{
			readChannelNameValues((LabelledTransition) chosenEvent);
		}

		return chosenEvent;
	}

	// FIXME this should be moved to the console class not the selection class. This is two different things
	public static void readChannelNameValues(LabelledTransition chosenEvent)
	{
		LabelledTransition chosenChannelEvent = (LabelledTransition) chosenEvent;
		ChannelNameValue channnelName = chosenChannelEvent.getChannelName();

		for (int i = 0; i < channnelName.getValues().size(); i++)
		{
			Value currentValue = channnelName.getValues().get(i);

			if (!AbstractValueInterpreter.isValueMostPrecise(currentValue))
			{
				System.out.println("Enter value : ");
				Value val;
				try
				{
					//FIXME this should be changed to the be handles by the COMPASS parser since the syntax i changed
					//in some of the syntax
					LexTokenReader ltr = new LexTokenReader(new BufferedReader(new InputStreamReader(System.in)).readLine(), Dialect.VDM_PP);
					ExpressionReader reader = new ExpressionReader(ltr);
					reader.setCurrentModule("IO");
					PExp exp = reader.readExpression();

					Interpreter ip = Interpreter.getInstance();
					PType expectedType = channnelName.getChannel().getValueTypes().get(i);
					PType expType = ip.typeCheck(exp, ip.getGlobalEnvironment());
					if (!TypeComparator.compatible(expectedType, expType))
					{
						throw new InterpreterRuntimeException("Wrong expression type read from console. Expected: "
								+ expectedType + " Actual: " + expType);
					}

					Context ctxt = chosenEvent.getEventSources().iterator().next().getNextState().second;
					val = exp.apply(VdmRuntime.getExpressionEvaluator(), ctxt);
					// type compare
					channnelName.updateValue(i, val);
					return;
				} catch (AnalysisException e)
				{
					throw new InterpreterRuntimeException("Analysis error in read user value", e);
				} catch (IOException e)
				{
					throw new InterpreterRuntimeException("IO error in read user value", e);
				} catch (Exception e)
				{
					if (e instanceof InterpreterRuntimeException)
					{
						throw (InterpreterRuntimeException) e;
					}
					throw new InterpreterRuntimeException("Unknown internal error in read user value", e);
				}
			}
		}
	}

	public boolean isHideSilentTransitions()
	{
		return hideSilentTransitions;
	}

	public void setHideSilentTransitions(boolean hideSilentTransitions)
	{
		this.hideSilentTransitions = hideSilentTransitions;
	}

	@Override
	public void choices(CmlTransitionSet availableTransitions)
	{
		this.availableChannelEvents = availableTransitions;
	}

	@Override
	public CmlTransition resolveChoice()
	{
		// At this point we don't want the internal transition to propagate
		// to the user, so we randomly choose all the possible internal transitions
		// before we let anything through to the user
		if (isSystemSelect(availableChannelEvents) && isHideSilentTransitions())
		{
			CmlTransition t = systemSelect(availableChannelEvents);
			System.out.println("The system picked: " + t);
			return t;
		} else
		{
			CmlTransition t = userSelect(availableChannelEvents);
			System.out.println("The environment picked: " + t);
			return t;
		}
	}

}
