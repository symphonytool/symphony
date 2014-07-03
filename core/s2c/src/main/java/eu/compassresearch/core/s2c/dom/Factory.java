package eu.compassresearch.core.s2c.dom;

import org.w3c.dom.Node;

import eu.compassresearch.core.s2c.util.NodeIterator;

public class Factory
{
	public static StateMachine buildStateMachine(Node n)
	{
		StateMachine sm = new StateMachine();
		setIdAndName(n, sm);

		return sm;
	}

	protected static void setIdAndName(Node n, NamedUmlNode sm)
	{
		sm.id = n.getAttributes().getNamedItem("xmi:id").getNodeValue();
		sm.name = n.getAttributes().getNamedItem("name").getNodeValue();
		sm.type = n.getAttributes().getNamedItem("xmi:type").getNodeValue();
	}

	protected static void setIdAndName(Node n, UmlNode sm)
	{
		sm.id = n.getAttributes().getNamedItem("xmi:id").getNodeValue();
		// sm.name = n.getAttributes().getNamedItem("name").getNodeValue();
		sm.type = n.getAttributes().getNamedItem("xmi:type").getNodeValue();
	}

	public static State buildState(Node n, Node entry, Node exit)
	{
		State state = new State();
		setIdAndName(n, state);

		if (n.getAttributes().getNamedItem("kind") != null)
		{
			state.kind = n.getAttributes().getNamedItem("kind").getNodeValue();
		}

		if (entry != null)
		{
			NamedUmlNode entryNode = new NamedUmlNode();
			setIdAndName(entry, entryNode);
			state.entry = entryNode;
		}
		if (exit != null)
		{
			NamedUmlNode exitNode = new NamedUmlNode();
			setIdAndName(exit, exitNode);
			state.exit = exitNode;
		}

		return state;
	}

	public static Transition buidlTransition(StateMachine sm, Node n,
			Node effect, String sourceId, String targetId, Node guard)
	{
		Transition transition = new Transition();

		setIdAndName(n, transition);

		transition.source = sm.lookupState(sourceId);
		transition.target = sm.lookupState(targetId);

		transition.constraint = guard != null ? buildConstraint(guard) : null;
		transition.effect = effect != null ? buildOpaqueBehaviour(effect)
				: null;

		return transition;
	}

	private static OpaqueBehavior buildOpaqueBehaviour(Node effect)
	{
		OpaqueBehavior behaviour = new OpaqueBehavior();
		setIdAndName(effect, behaviour);
		behaviour.body = effect.getAttributes().getNamedItem("body").getNodeValue();
		return behaviour;
	}

	private static Constraint buildConstraint(Node guard)
	{
		Constraint constraint = new Constraint();
		setIdAndName(guard, constraint);

		for (Node cn : new NodeIterator(guard.getChildNodes()))
		{
			if (cn.getNodeType() == Node.ELEMENT_NODE
					&& cn.getNodeName().equals("specification"))
			{
				constraint.expression = buidlOpaqueExpression(cn);
				break;
			}
		}

		return constraint;
	}

	private static OpaqueExpression buidlOpaqueExpression(Node n)
	{
		OpaqueExpression exp = new OpaqueExpression();
		setIdAndName(n, exp);
		exp.body = n.getAttributes().getNamedItem("body").getNodeValue();
		return exp;

	}

}
