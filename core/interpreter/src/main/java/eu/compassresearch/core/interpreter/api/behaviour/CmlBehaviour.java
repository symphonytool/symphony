package eu.compassresearch.core.interpreter.api.behaviour;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.events.CmlBehaviorStateObserver;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.api.events.TraceObserver;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.utility.Pair;

/**
 * This interfaces specifies a specific process behavior. E.g: prefix : a -> P CmlBehaviour.inspect() = {a}
 * CmlBehaviour.execute() : trace: a CmlBehaviour.inspect() = alpha(P)
 * 
 * @author akm
 */
public interface CmlBehaviour extends Serializable , Comparable<CmlBehaviour>
{
	public static class BehaviourName
	{
		public static final String PROCESS_SEPERATOR = " = ";
		public static final String ACTION_SEPERATOR = " -> ";
		BehaviourName owner = null;
		List<String> processes = new Vector<String>();
		List<String> actions = new Vector<String>();
		private boolean unNamed = false;

		public BehaviourName(String process)
		{
			this.processes.add(process);
		}

		public BehaviourName(PProcess process, BehaviourName owner,
				String prefix, String postfix)
		{
			this(extractName(process), owner, prefix, postfix);
		}

		public static String extractName(INode node)
		{
			if (node instanceof AReferenceProcess)
			{
				return ((AReferenceProcess) node).getProcessName().getName();
			} else if (node instanceof AReferenceAction)
			{
				return ((AReferenceAction) node).getName().getName();
			} else if (node instanceof AExternalChoiceProcess
					|| node instanceof AExternalChoiceAction)
			{
				return "[]";
			} else if (node instanceof AGeneralisedParallelismParallelAction)
			{
				return "[||]";
			} else if (node instanceof ACommunicationAction)
			{
				return ((ACommunicationAction) node).getIdentifier().getName();
			} else if (node instanceof ASequentialCompositionAction
					|| node instanceof ASequentialCompositionProcess)
			{
				return ";";
			}
			return "???";
		}

		public BehaviourName(String process, BehaviourName owner,
				String prefix, String postfix)
		{
			this.owner = owner;
			if (CmlRuntime.consoleMode)
			{
				this.processes.add(prefix + process + postfix);
			} else
			{
				this.processes.add(process);
			}
		}

		public BehaviourName(BehaviourName owner, PAction action,
				String prefix, String postfix)
		{
			this(owner, extractName(action), prefix, postfix);
		}

		public BehaviourName(BehaviourName owner, String action, String prefix,
				String postfix)
		{
			this.owner = owner;
			if (CmlRuntime.consoleMode)
			{
				this.actions.add(prefix + action + postfix);
			} else
			{
				this.actions.add(action);
			}
		}

		public BehaviourName(BehaviourName name)
		{
			this.owner = name;
		}

		private BehaviourName()
		{
			unNamed = true;
		}

		public void addProcess(String name)
		{
			processes.add(name);
			this.unNamed = false;
		}

		public void addAction(String name)
		{
			actions.add(name);
			this.unNamed = false;
		}

		public void addAction(PAction action)
		{
			actions.add(extractName(action));
		}

		public String getLastProcess()
		{
			if (processes.isEmpty())
			{
				return "";
			}
			return processes.get(processes.size() - 1);
		}

		public String getLastAction()
		{
			if (getAllActions().isEmpty())
			{
				return "";
			}
			return getAllActions().get(getAllActions().size() - 1);
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			List<String> processes = new Vector<String>(getAllProcesses());
			List<String> actions = new Vector<String>(getAllActions());

			for (Iterator<String> itr = processes.iterator(); itr.hasNext();)
			{
				sb.append(itr.next());
				if (itr.hasNext())
				{
					sb.append(PROCESS_SEPERATOR);
				}
			}

			if (unNamed && this.processes.isEmpty())
			{
				sb.append(PROCESS_SEPERATOR);
				sb.append("???");
			}

			if (!actions.isEmpty())
			{
				sb.append(ACTION_SEPERATOR);
			}

			for (Iterator<String> itr = actions.iterator(); itr.hasNext();)
			{
				sb.append(itr.next());
				if (itr.hasNext())
				{
					sb.append(ACTION_SEPERATOR);
				}
			}

			if (unNamed && !this.processes.isEmpty())
			{
				sb.append(ACTION_SEPERATOR);
				sb.append("???");
			}

			return sb.toString();
		}

		private List<? extends String> getAllActions()
		{
			List<String> names = new Vector<String>();
			if (owner != null)
			{
				names.addAll(owner.getAllActions());
			}
			names.addAll(this.actions);

			return names;
		}

		private Collection<? extends String> getAllProcesses()
		{
			List<String> names = new Vector<String>();
			if (owner != null)
			{
				names.addAll(owner.getAllProcesses());
			}
			names.addAll(this.processes);

			return names;
		}

		public BehaviourName clone()
		{
			BehaviourName nb = new BehaviourName();
			nb.owner = this.owner;
			nb.processes.addAll(this.processes);
			nb.actions.addAll(this.actions);
			return nb;
		}

		public BehaviourName clone(boolean unnamed)
		{
			BehaviourName n = clone();
			n.unNamed = unnamed;
			return n;

		}

	}

	/**
	 * Executes the behaviour of this process
	 * 
	 * @return
	 */
	public void execute(CmlTransition selectedTransition)
			throws AnalysisException;

	/**
	 * Returns the immediate alphabet of the process, meaning the next possible cml event including tau
	 * 
	 * @return The immediate alphabet of the process
	 */
	public CmlTransitionSet inspect() throws AnalysisException;

	// public Reason abortReason();
	// public void setAbort(Reason reason);

	/**
	 * Returns the current execution state of the process
	 * 
	 * @return The current context
	 */
	public Pair<INode, Context> getNextState();

	public void replaceState(Context context) throws ValueException;

	// public Pair<INode,Context> getPostCondition();
	// public setPostCondition(Pair<INode,Context> postcondition);

	/**
	 * Name of the process
	 * 
	 * @return The name of the process
	 */
	public BehaviourName getName();

	public int getId();

	/**
	 * This constructs a string representing the next execution step of this process
	 * 
	 * @return
	 */
	public String nextStepToString();

	// Process Graph/Representation related methods
	/**
	 * The level of this object in the process network.
	 * 
	 * @return return 0 if this is the root, 1 if this is a child of the root etc.
	 */
	public long level();

	public CmlBehaviour parent();

	public List<CmlBehaviour> children();

	public CmlBehaviour getLeftChild();

	public CmlBehaviour getRightChild();

	// public CMLAlphabet childInspectedAlphabet(CMLProcessNew child);
	// public void setChildInspectedAlphabet(CMLProcessNew child, CMLAlphabet alpha);
	// public boolean hasChild(CMLProcess child, boolean recursive);
	public boolean hasChildren();

	/**
	 * Process state methods
	 */

	/**
	 * Determines whether the process is started
	 * 
	 * @return true if the process has been started, meaning the start method has been invoked else false
	 */
	public boolean started();

	public boolean finished();

	public boolean isDivergent();

	/**
	 * Determines whether the process is in a waiting state.
	 * 
	 * @return true if the process is either waiting for a child or an event to occur else false
	 */
	public boolean waiting();

	/**
	 * Determines whether this process is deadlocked
	 * 
	 * @return true if the process is deadlocked else false
	 */
	public boolean deadlocked() throws AnalysisException;

	/**
	 * @return The current state of the process
	 */
	public CmlBehaviorState getState();

	/**
	 * Register or unregister for the State Changed event
	 * 
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<CmlBehaviorStateObserver> onStateChanged();

	/**
	 * Denotational Semantics Information
	 */
	public CmlTrace getTraceModel();

	// public CSPFailures failuresModel();
	// public CSPDivergencies divergenciesModel();
	// public CSPFailuresDivergencies failuresDivergenciesModel();
	public long getCurrentTime();

	/**
	 * Register or unregister for the State Changed event
	 * 
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<TraceObserver> onTraceChanged();


}
