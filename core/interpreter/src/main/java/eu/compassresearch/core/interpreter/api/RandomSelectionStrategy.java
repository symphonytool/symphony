package eu.compassresearch.core.interpreter.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

/**
 * This class implements a random selection CMLCommunicaiton of the alphabet
 * 
 * @author akm
 */
public class RandomSelectionStrategy implements SelectionStrategy
{

	private static final long randomSeed = 675674345;
	private final Random rndChoice = new Random(randomSeed);
	CmlTransitionSet availableChannelEvents;


	private Value getRandomValueFromType(PType type)
	{
		try
		{
			return type.apply(new RandomVDMValueGenerator(randomSeed));
		} catch (AnalysisException e)
		{
			e.printStackTrace();
		}

		return new UndefinedValue();
	}

	@Override
	public void choices(CmlTransitionSet availableTransitions)
	{
		this.availableChannelEvents = availableTransitions;
	}

	@Override
	public CmlTransition resolveChoice()
	{
		CmlTransition selectedComm = null;

		// If nothing is available then we do nothing
		// TODO This is probably not the best thing to do
		if (!availableChannelEvents.isEmpty())
		{
			int nElems = availableChannelEvents.getAllEvents().size();

			// pick a random but deterministic choice
			List<CmlTransition> transitions = new ArrayList<CmlTransition>(availableChannelEvents.getAllEvents());
			selectedComm = transitions.get(rndChoice.nextInt(nElems));

			// If the selected transition contains a channelname that are not precise then we
			// need to resolve this and let the environment take a random choice
			if (selectedComm instanceof LabelledTransition
					&& !((LabelledTransition) selectedComm).getChannelName().isPrecise())
			{
				LabelledTransition chosenChannelEvent = (LabelledTransition) selectedComm;
				ChannelNameValue channnelName = chosenChannelEvent.getChannelName();

				for (int i = 0; i < channnelName.getValues().size(); i++)
				{
					Value currentValue = channnelName.getValues().get(i);

					if (!AbstractValueInterpreter.isValueMostPrecise(currentValue))
					{
						channnelName.updateValue(i, getRandomValueFromType(channnelName.getChannel().getValueTypes().get(i)));
					}
				}
			}
		}

		return selectedComm;
	}

}
