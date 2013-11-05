package eu.compassresearch.core.interpreter.remote;

import java.util.Set;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;

public class RemoteTester implements IRemoteControl
{

	@Override
	public void run(IRemoteInterpreter interpreter) throws Exception
	{
		Set<? extends CmlTransition> options;
		while (!interpreter.isFinished())
		{
			if (!(options = interpreter.getEvents()).isEmpty())
			{
				CmlTransition event = null;
				for (CmlTransition ev : options)
				{
					if (ev instanceof TimedTransition)
					{
						continue;
					}
					event = ev;
					break;
				}

				System.out.println("Selected: " + event);

				if (event instanceof LabelledTransition
						&& !((LabelledTransition) event).getChannelName().getValues().isEmpty())
				{
					interpreter.select((LabelledTransition) event, "4");
				} else
				{
					interpreter.select(event);
				}
			}
		}

	}

}
