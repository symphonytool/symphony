package eu.compassresearch.core.interpreter.testing.remote;

import java.util.Set;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.cosim.communication.Utils;
import eu.compassresearch.core.interpreter.remote.IRemoteControl;
import eu.compassresearch.core.interpreter.remote.IRemoteInterpreter;

/**
 * Internal test implementation of the @ IRemoteControl} interface. The implementation selects the first event and
 * continues while the interpreter state is not {@link CmlInterpreterState#DEADLOCKED} or
 * {@link CmlInterpreterState#FAILED} or {@link CmlInterpreterState#FINISHED}or
 * {@link CmlInterpreterState#TERMINATED_BY_USER}. If the events takes an argument then it selects it with {@code 4}.
 * 
 * @author kel
 */
class InternalRemoteTester implements IRemoteControl
{
	@Override
	public void run(final IRemoteInterpreter interpreter) throws Exception
	{
		final Thread thisThread = Thread.currentThread();
		Thread t = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					if (interpreter.getState() == CmlInterpreterState.DEADLOCKED
							|| interpreter.getState() == CmlInterpreterState.FAILED
							|| interpreter.getState() == CmlInterpreterState.FINISHED
							|| interpreter.getState() == CmlInterpreterState.TERMINATED_BY_USER)
					{
						thisThread.interrupt();
						return;
					}
					Utils.milliPause(500);
				}
			}
		});
		t.setDaemon(true);
		t.start();

		try
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

					if (interpreter.requireArguments(event))
					{
						interpreter.select(event, "4");
					} else
					{
						interpreter.select(event);
					}
				}
			}
		} catch (InterpreterRuntimeException e)
		{
			if (e.getCause() instanceof InterruptedException
					&& interpreter.getState() == CmlInterpreterState.FINISHED)
			{
				// ignore
			} else
			{
				throw e;
			}
		}
	}
}
