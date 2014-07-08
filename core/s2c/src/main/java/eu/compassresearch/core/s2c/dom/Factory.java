package eu.compassresearch.core.s2c.dom;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.compassresearch.core.s2c.S2cTranslator;
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
		if (n.getAttributes().getNamedItem("name") != null)
		{
			sm.name = n.getAttributes().getNamedItem("name").getNodeValue();
		}
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
			Node effect, String sourceId, String targetId, Node guard,
			Trigger trigger)
	{
		Transition transition = new Transition();

		setIdAndName(n, transition);

		transition.source = sm.lookupState(sourceId);
		transition.target = sm.lookupState(targetId);

		transition.constraint = guard != null ? buildConstraint(guard) : null;
		transition.effect = effect != null ? buildOpaqueBehaviour(effect)
				: null;
		transition.trigger = trigger;

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

	public static ClassDefinition buildClass(Node theClass)
	{
		ClassDefinition cDef = new ClassDefinition();
		setIdAndName(theClass, cDef);

		return cDef;
	}

	private static Integer buildUpperLower(Node child)
	{
		String type = child.getAttributes().getNamedItem("xmi:type").getNodeValue();

		if (type.equals("uml:LiteralUnlimitedNatural"))
		{
			return null;
		} else if (type.equals("uml:LiteralInteger"))
		{
			if (child.getAttributes().getNamedItem("value") != null)
			{
				return Integer.parseInt(child.getAttributes().getNamedItem("value").getNodeValue());
			}
			return 0;
		}
		return null;
	}

	private static String buildType(Node child)
	{
		// todo
		if (child.getAttributes().getNamedItem("xmi:type").getTextContent().equals("uml:DataType"))
		{
			return child.getAttributes().getNamedItem("name").getTextContent();
		}
		if (child.getAttributes().getNamedItem("xmi:type").getTextContent().equals("uml:Class"))
		{
			return child.getAttributes().getNamedItem("name").getTextContent();
		}
		if (child.getAttributes().getNamedItem("xmi:type").getTextContent().equals("uml:Enumeration"))
		{
			return child.getAttributes().getNamedItem("name").getTextContent();
		}
System.out.println(S2cTranslator.formateNodeWithAtt(child));
		String tmp = child.getAttributes().getNamedItem("href").getNodeValue();
		if (tmp.contains("#"))
		{
			return tmp.substring(tmp.indexOf("#") + 1);
		}
		return tmp;
	}

	public static Property buildProperty(Node n, Node type, Node lower,
			Node upper)
	{
		Property prop = new Property();
		setIdAndName(n, prop);
		prop.visibility = n.getAttributes().getNamedItem("visibility").getNodeValue();

		if (upper != null)
		{
			prop.upper = buildUpperLower(upper);
		}
		if (lower != null)
		{
			prop.lower = buildUpperLower(lower);
		}
		if (type != null)
		{
			prop.type = buildType(type);
		}

		return prop;
	}

	public static Operation buildOperation(Node n, Node method)
	{
		Operation op = new Operation();
		setIdAndName(n, op);
		op.visibility = n.getAttributes().getNamedItem("visibility").getNodeValue();

		if (method != null)
		{
			op.body = buildOpaqueBehaviour(method);
		}
		return op;
	}

	public static Parameter buildParameter(Node n, Node typeNode)
	{
		Parameter param = new Parameter();
		setIdAndName(n, param);

		Node directionNode;
		if ((directionNode = n.getAttributes().getNamedItem("direction")) != null)
		{
			param.isReturn = directionNode.getNodeValue().equals("return");
		}

		param.type = buildType(typeNode);
		return param;
	}

	public static Trigger buildTrigger(Node triggerNode, Event event)
	{
		Trigger trigger = new Trigger();
		setIdAndName(triggerNode, trigger);
		trigger.event = event;
		return trigger;
	}

	public static Event buildEvent(Node eventNode, Signal signal)
	{
		Event event = new Event();
		setIdAndName(eventNode, event);
			event.signal = signal;;
		return event;
	}

	public static Signal buildSignal(Node signal,List<Property> properties)
	{
		Signal s = new Signal();
		setIdAndName(signal, s);
		
		s.property.addAll(properties);

		return s;
	}

}
