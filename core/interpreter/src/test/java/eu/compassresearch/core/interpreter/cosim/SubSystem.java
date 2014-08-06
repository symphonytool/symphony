package eu.compassresearch.core.interpreter.cosim;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.NamespaceUtility;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableLabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;
import eu.compassresearch.core.interpreter.api.values.CmlChannel;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.cosim.external.ExternalCoSimulationClient;

public class SubSystem implements IProcessDelegate
{
	public static class AbortException extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int error;

		public AbortException(int error, String message)
		{
			super(message);
			this.error = error;
		}
	}

	@Override
	public CmlTransitionSet inspect() throws Exception
	{
		SortedSet<CmlTransition> events = new TreeSet<CmlTransition>();
		events.add(untypedChannel("c"));
		events.add(sendChannel("a", 1));
		events.add(readChannel("b", new AIntNumericBasicType()));

		CmlTransitionSet transitions = new CmlTransitionSet(events);

		return transitions;
	}

	private ObservableTransition untypedChannel(String name)
	{
		CmlChannel channel = createChannelValue(name);
		ChannelValue channelName = new ChannelValue(channel, new LinkedList<Value>(), null);
		ObservableTransition transition = new ObservableLabelledTransition((CmlBehaviour)null, channelName);
		return transition;
	}

	private ObservableTransition sendChannel(String name, int... value)
	{
		CmlChannel channel = createChannelValue(name, new AIntNumericBasicType());
		List<Value> values = new Vector<Value>();
		for (int v : value)
		{
			values.add(new IntegerValue(v));
		}

		ChannelValue channelName = new ChannelValue(channel, values, null);
		ObservableTransition transition = new ObservableLabelledTransition((CmlBehaviour)null, channelName);
		return transition;
	}

	private ObservableTransition readChannel(String name, PType... value)
	{
		CmlChannel channel = createChannelValue(name, value);
		List<Value> values = new Vector<Value>();
		for (PType v : value)
		{
			values.add(new LatticeTopValue(v));
		}

		ChannelValue channelName = new ChannelValue(channel, values, null);
		ObservableTransition transition = new ObservableLabelledTransition((CmlBehaviour)null, channelName);
		return transition;
	}

	private static CmlChannel createChannelValue(String name)
	{
		return createChannelValue(name, (PType[]) null);
	}

	private static CmlChannel createChannelValue(String name, PType... type)
	{
		return new CmlChannel(null, NamespaceUtility.createChannelName(new LexIdentifierToken(name, false, new LexLocation())));
	}

	@Override
	public void execute(CmlTransition transition) throws Exception
	{
		System.out.println("Executing: " + transition);
		if (transition instanceof LabelledTransition
				&& ((LabelledTransition) transition).getChannelName().getChannel().getName().equals("b"))
		{
			throw new AbortException(999, "execution error");
		}
		// Utils.milliPause(1000);

	}

	@Override
	public String getProcessName()
	{
		return "A";
	}

	@Override
	public boolean isFinished() throws Exception
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disconnect() throws Exception
	{
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) throws InterruptedException,
			IOException
	{
		final SubSystem sys = new SubSystem();
		Integer post = 8088;
		if (args.length > 0)
		{
			post = Integer.valueOf(args[0]);
		}
		final ExternalCoSimulationClient client = new ExternalCoSimulationClient("localhost", post);
		client.setSubSystem(sys);

		client.connect();
		client.start();
		client.registerImplementation(sys.getProcessName());

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						sys.execute(client.getExecutableTransition());
						client.executionCompleted();
					} catch (AbortException e)
					{
						try
						{
							client.abort(e.error, e.getMessage());
							Thread.sleep(1000);
							System.exit(0);
						} catch (InterruptedException e1)
						{
							e1.printStackTrace();
						} catch (Exception e1)
						{
							e1.printStackTrace();
						}
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}).start();

		client.join();
	}

}
