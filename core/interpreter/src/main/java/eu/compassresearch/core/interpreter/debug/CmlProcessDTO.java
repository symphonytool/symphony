package eu.compassresearch.core.interpreter.debug;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorState;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;

public class CmlProcessDTO
{

	private final String name;
	private final int id;
	private final List<String> trace;
	private final CmlBehaviorState state;
	private final ILexLocation location;
	private final CmlProcessDTO leftChild;
	private final CmlProcessDTO rightChild;
	private final CmlProcessDTO parent;

	/**
	 * Dummy constructor for serialization
	 */
	protected CmlProcessDTO()
	{
		name = null;
		id = -1;
		trace = new LinkedList<String>();
		state = null;
		location = null;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
	}

	public CmlProcessDTO(CmlBehaviour process, CmlProcessDTO parent)
	{
		this.name = process.getName().toString();
		this.id = process.getId();
		if (process.getTraceModel() != null)
		{
			this.trace = convertCmlEventsToStringList(process.getTraceModel().getObservableTrace());
		} else
		{
			this.trace = new Vector<String>();
		}
		// this.isProcess = process.getNextState().first instanceof PProcess;
		this.state = process.getState();
		this.location = LocationExtractor.extractLocation(process.getNextState().first);
		this.parent = parent;

		if (process.getLeftChild() != null)
		{
			this.leftChild = new CmlProcessDTO(process.getLeftChild(), this);
		} else
		{
			this.leftChild = null;
		}

		if (process.getRightChild() != null)
		{
			this.rightChild = new CmlProcessDTO(process.getRightChild(), this);
		} else
		{
			this.rightChild = null;
		}
	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return this.id;
	}

	public long level()
	{
		if (this.parent == null)
		{
			return 0;
		} else
		{
			return 1 + parent.level();
		}
	}

	public CmlBehaviorState getState()
	{
		return state;
	}

	public List<String> getTrace()
	{
		return this.trace;
	}

	private List<String> convertCmlEventsToStringList(List<CmlTransition> events)
	{
		List<String> result = new LinkedList<String>();

		for (CmlTransition e : events)
		{
			result.add(e.toString());
		}

		return result;
	}

	public ILexLocation getLocation()
	{
		return this.location;
	}

	public CmlProcessDTO getParent()
	{
		return parent;
	}

	public CmlProcessDTO getLeftChild()
	{
		return leftChild;
	}

	public CmlProcessDTO getRightChild()
	{
		return rightChild;
	}

	@Override
	public String toString()
	{
		return "Name: " + name + System.lineSeparator() + "trace :" + trace;
	}
}
