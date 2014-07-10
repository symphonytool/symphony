package eu.compassresearch.core.s2c;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.compassresearch.core.s2c.dom.ClassDefinition;
import eu.compassresearch.core.s2c.dom.DataType;
import eu.compassresearch.core.s2c.dom.EnumType;
import eu.compassresearch.core.s2c.dom.Event;
import eu.compassresearch.core.s2c.dom.Factory;
import eu.compassresearch.core.s2c.dom.Operation;
import eu.compassresearch.core.s2c.dom.Property;
import eu.compassresearch.core.s2c.dom.Signal;
import eu.compassresearch.core.s2c.dom.State;
import eu.compassresearch.core.s2c.dom.StateMachine;
import eu.compassresearch.core.s2c.dom.Trigger;
import eu.compassresearch.core.s2c.util.InvalidNameSpaceFix;
import eu.compassresearch.core.s2c.util.NamedNodeMapIterator;
import eu.compassresearch.core.s2c.util.NodeIterator;
import eu.compassresearch.core.s2c.util.UniversalNamespaceResolver;

public class S2cTranslator
{
	private static final Boolean DEBUG = false;
	private static final String ANY_STATE_MACHINE_IN_ANY_PACKAGES_CLASS = "//packagedElement[@xmi:type='uml:Class']/ownedBehavior[@xmi:type='uml:StateMachine']";
	private static final String ANY_DATATYPE = "//packagedElement[@xmi:type='uml:DataType']";
	private static final String ANY_STATE_MACHINE_IN_ANY_NESTED_CLASSIFIER = "//nestedClassifier[@xmi:type='uml:Class']/ownedBehavior[@xmi:type='uml:StateMachine']";
	private static final String ANY_STATE_MACHINE = "//packagedElement[@xmi:type='uml:StateMachine']";

	private Map<String, String> types = new HashMap<String, String>();

	private Set<String> usedCustomTypes = new HashSet<String>();

	private List<Signal> newsignals = new LinkedList<Signal>();
	
	private Set<String> blockNames = new HashSet<String>();
	
	
	
	public static void main(String[] args) throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException
	{
		// String path = "src/test/resources/s2c-lite-initial-model2.xmi";
		String path = "src/test/resources/Insiel/insiel.xmi";

		if (args.length > 0)
		{
			path = args[0];
		}

		new S2cTranslator().translate(new File(path.replace('/', File.separatorChar)), new File("target".replace('/', File.separatorChar)),true);
	}

	public State buildCompositeState(State state, Node node, Document doc,
			StateMachine sm, XPath xpath) throws XPathExpressionException
	{
		Node region = lookup(node, xpath, "region[@xmi:type='uml:Region']").item(0);
		if (region != null)
		{
			System.out.println("State " + state.name + " is composite");
			System.out.println("The final state");
			Node finalState = lookup(region, xpath, "subvertex[@xmi:type='uml:FinalState']").item(0);

			if (finalState != null)
			{
				state.substates.add(Factory.buildState(finalState, lookupSingle(finalState, xpath, "entry"), lookupSingle(finalState, xpath, "exit")));
			}

			System.out.println("Any State");
			NodeList anyStates = lookup(region, xpath, "subvertex[@xmi:type='uml:State']");
			for (Node n : new NodeIterator(anyStates))
			{
				State s = Factory.buildState(n, lookupSingle(n, xpath, "entry"), lookupSingle(n, xpath, "exit"));
//				if (s.name.startsWith("Initial")) {
//					state.initial = s;
//				} else {
					buildCompositeState(s, n, doc, sm, xpath);
					state.substates.add(s);
//				}
			}

			System.out.println("Any Pseudostates");
			NodeList anyPseudoStates = lookup(region, xpath, "subvertex[@xmi:type='uml:Pseudostate']");
			for (Node n : new NodeIterator(anyPseudoStates))
			{
				state.substates.add(Factory.buildState(n, lookupSingle(n, xpath, "entry"), lookupSingle(n, xpath, "exit")));
			}
			return state;
		} else
		{
			System.out.println("State " + state.name + " is simple");
			return state;
		}
	}

	public State buildCompositeStateTransitions(State state, Node node,
			Document doc, StateMachine sm, XPath xpath)
			throws XPathExpressionException
	{
		Node region = lookup(node, xpath, "region[@xmi:type='uml:Region']").item(0);
		if (region != null)
		{
			
			NodeList transactions = lookup(region, xpath, "transition[@xmi:type='uml:Transition']");
			
			System.out.println("--- The transitions within state "+ state.name +" --- ("+ transactions.getLength() +")");
			
			for (Node t : new NodeIterator(transactions))
			{
				NamedNodeMap atts = t.getAttributes();

				String sourceId = atts.getNamedItem("source").getNodeValue();
				String targetId = atts.getNamedItem("target").getNodeValue();

				System.out.println("transition from" + sm.lookupState(sourceId).name + " to " + sm.lookupState(targetId).name);
				
				System.out.println("Looking up translation details for: "
						+ t.getAttributes().getNamedItem("xmi:id"));
				// Node source = lookupId(doc, xpath, sourceId);
				// Node target = lookupId(doc, xpath, targetId);
				Node guard = null;
				if (atts.getNamedItem("guard") != null)
				{
					String guardId = atts.getNamedItem("guard").getNodeValue();
					guard = lookupId(doc, xpath, guardId);
				}

				Node effect = lookupSingle(t, xpath, "effect[@xmi:type='uml:OpaqueBehavior']");
				// Node trigger = lookupSingle(t, xpath, "trigger[@xmi:type='uml:Trigger']");
				//
				// state.transitions.add(Factory.buidlTransition(sm, t, effect, sourceId, targetId, guard));
				Node triggerNode = lookupSingle(t, xpath, "trigger[@xmi:type='uml:Trigger']");
				Trigger trigger = extractTrigger(doc, xpath, triggerNode);

				sm.transitions.add(Factory.buidlTransition(sm, t, effect, sourceId, targetId, guard, trigger));

				System.out.println("\n");
			}
			
			NodeList states = lookup(region,xpath,"subvertex[@xmi:type='uml:State']");
			System.out.print("Substates of "+state.name+": ");
			for (Node n: new NodeIterator(states)) {
				System.out.print(n.getAttributes().getNamedItem("name").getTextContent() + " ");
			}
			
			for (Node n : new NodeIterator(states))
			{
				State s = sm.lookupState(n.getAttributes().getNamedItem("xmi:id").getTextContent());
				System.out.println("Treating state: "+s.name);
				buildCompositeStateTransitions(s, n, doc, sm, xpath);
			}
			
			return state;
		} else
		{
			return state;
		}
	}

	/**
	 * See this for xpath {@link "http://www.w3schools.com/XPath/xpath_syntax.asp"}
	 * 
	 * @param input
	 * @param output
	 * @param overwrite 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public File translate(File input, File output, boolean overwrite)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(InvalidNameSpaceFix.createFixedFile(input));
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();

		xpath.setNamespaceContext(new UniversalNamespaceResolver(doc));

		System.out.println("Find the state machine");
		NodeList stateMachines = lookup(doc, xpath, ANY_STATE_MACHINE_IN_ANY_PACKAGES_CLASS);

		if (stateMachines.getLength() == 0)
		{
			stateMachines = lookup(doc, xpath, ANY_STATE_MACHINE_IN_ANY_NESTED_CLASSIFIER);
		}
		if (stateMachines.getLength() == 0)
		{
			stateMachines = lookup(doc,xpath,ANY_STATE_MACHINE);
		}

		System.out.println("Find the class thats that parent of the state machine");
		System.out.println(stateMachines.getLength());
		NodeList theClass = lookup(stateMachines.item(0), xpath, "..");
		ClassDefinition theClassDef = null;
		if (theClass.getLength() == 0) {
			theClassDef = new ClassDefinition();
			theClassDef.name = stateMachines.item(0).getAttributes().getNamedItem("name").getTextContent();
		} else {
			theClassDef = Factory.buildClass(theClass.item(0));
			System.out.println("Class properties");
			theClassDef.properties.addAll(buildProperties(doc, xpath, theClass.item(0)));

			System.out.println("Class operations");
			theClassDef.operations.addAll(buildOperations(doc, xpath, theClass.item(0)));
		}
		
		System.out.println("Find all other classes and ignore any state machines");
		//NodeList allClassNodes = lookup(doc,xpath,"//packagedElement[@xmi:type='uml:Class']");
		NodeList allBlocks = lookup(doc,xpath,"//sysml:Block");
		for (Node n: new NodeIterator(allBlocks)) {
			Node base_class = lookupId(doc, xpath, n.getAttributes().getNamedItem("base_Class").getTextContent());
			blockNames.add(base_class.getAttributes().getNamedItem("name").getTextContent());
		}
		
		List<ClassDefinition> allClasses = new LinkedList<ClassDefinition>();
		for (Node n: new NodeIterator(allBlocks)) {
			Node base_class = lookupId(doc, xpath, n.getAttributes().getNamedItem("base_Class").getTextContent());
			ClassDefinition aux = Factory.buildClass(base_class);
			aux.properties.addAll(buildProperties(doc,xpath,base_class));
			aux.operations.addAll(buildOperations(doc,xpath,base_class));
			allClasses.add(aux);
		}
		
		System.out.println("Find all data and value types");
		NodeList allValueTypes = lookup(doc,xpath,"//packagedElement[@xmi:type='uml:ValueType']");
		NodeList allDataTypes = lookup(doc,xpath,"//packagedElement[@xmi:type='uml:DataType']");
		List<DataType> alldatatypes = new Vector<DataType>();
		for (Node n: new NodeIterator(allValueTypes)) {
			DataType dt = Factory.buildDataType(n);
			dt.properties.addAll(buildProperties(doc,xpath,n));
			alldatatypes.add(dt);
		}
		for (Node n: new NodeIterator(allDataTypes)) {
			DataType dt = Factory.buildDataType(n);
			dt.properties.addAll(buildProperties(doc,xpath,n));
			alldatatypes.add(dt);
		}
		
		
		System.out.println("##\nState machine\n##");
		final Node stateMachine = stateMachines.item(0);
		String statemachineName = stateMachine.getAttributes().getNamedItem("name").getNodeValue();
		System.out.println("State machine name = " + statemachineName);
		StateMachine sm = Factory.buildStateMachine(stateMachine);

		System.out.println("The main region");
		Node region = lookup(stateMachine, xpath, "region[@xmi:type='uml:Region']").item(0);

		System.out.println("The final state");
		Node finalState = lookup(region, xpath, "subvertex[@xmi:type='uml:FinalState']").item(0);

		if (finalState != null)
		{
			sm.states.add(Factory.buildState(finalState, lookupSingle(finalState, xpath, "entry"), lookupSingle(finalState, xpath, "exit")));
		}

		System.out.println("Any State");
		NodeList anyStates = lookup(region, xpath, "subvertex[@xmi:type='uml:State']");
		for (Node n : new NodeIterator(anyStates))
		{
			State s = Factory.buildState(n, lookupSingle(n, xpath, "entry"), lookupSingle(n, xpath, "exit"));
			buildCompositeState(s, n, doc, sm, xpath);
			sm.states.add(s);
		}

		System.out.println("Any Pseudostates");
		NodeList anyPseudoStates = lookup(region, xpath, "subvertex[@xmi:type='uml:Pseudostate']");
		for (Node n : new NodeIterator(anyPseudoStates))
		{
			State s = Factory.buildState(n, lookupSingle(n, xpath, "entry"), lookupSingle(n, xpath, "exit"));
			sm.states.add(s);
		}

		System.out.println("--- The transitions ---");
		NodeList transactions = lookup(region, xpath, "transition[@xmi:type='uml:Transition']");

		System.out.println("Number of states: "+sm.allStates().size());
		
		for (Node t : new NodeIterator(transactions))
		{
			NamedNodeMap atts = t.getAttributes();

			String sourceId = atts.getNamedItem("source").getNodeValue();
			String targetId = atts.getNamedItem("target").getNodeValue();
			
			System.out.println("transition from" + sm.lookupState(sourceId).name + " to " + sm.lookupState(targetId).name);

			if (DEBUG)
			{
				System.out.println("Looking up translation details for: "
						+ t.getAttributes().getNamedItem("xmi:id"));
			}
			// Node source = lookupId(doc, xpath, sourceId);
			// Node target = lookupId(doc, xpath, targetId);
			Node guard = null;
			if (atts.getNamedItem("guard") != null)
			{
				String guardId = atts.getNamedItem("guard").getNodeValue();
				guard = lookupId(doc, xpath, guardId);
			}

			Node effect = lookupSingle(t, xpath, "effect[@xmi:type='uml:OpaqueBehavior']");
			Node triggerNode = lookupSingle(t, xpath, "trigger[@xmi:type='uml:Trigger']");
			Trigger trigger = extractTrigger(doc, xpath, triggerNode);

			sm.transitions.add(Factory.buidlTransition(sm, t, effect, sourceId, targetId, guard, trigger));

			if (DEBUG)
			{
				System.out.println("\n");
			}
		}

		for (Node n : new NodeIterator(anyStates))
		{
			State s = sm.lookupState(n.getAttributes().getNamedItem("xmi:id").getTextContent());
			buildCompositeStateTransitions(s, n, doc, sm, xpath);
		}

		// collect all signals to be used as channels

		NodeList allSignals = lookup(doc, xpath, "//*[@xmi:type='uml:Signal']");

		List<Signal> signals = new Vector<Signal>();
		for (Node signalNode : new NodeIterator(allSignals))
		{
			//String name = signalNode.getAttributes().getNamedItem("name").getTextContent();
			//System.out.println("Looking at signal "+name);
			List<Property> properties = buildProperties(doc, xpath, signalNode);
			signals.add(Factory.buildSignal(signalNode, properties));
		}
		signals.addAll(newsignals);

		// add types to the class
		for (String typeId : this.usedCustomTypes)
		{
			Node type = lookupSingle(doc, xpath, "//*[@xmi:id='" + typeId
					+ "' and @xmi:type='uml:Enumeration']");
			if (type != null)
			{
				EnumType etype = Factory.buildEnumeration(type, lookup(type, xpath, "ownedLiteral[@xmi:type='uml:EnumerationLiteral']"));
				theClassDef.types.add(etype);
			}
		}

		System.out.println("----------------------------------------------------------------------------");
		System.out.println(sm);

		return new SysMlToCmlTranslator(signals, theClassDef, sm, allClasses,alldatatypes).translate(output,overwrite);
	}

	protected List<Operation> buildOperations(Document doc, XPath xpath,
			Node theClass) throws XPathExpressionException
	{
		List<Operation> ops = new Vector<Operation>();
		NodeList operations = lookup(theClass, xpath, "ownedOperation[@xmi:type='uml:Operation']");

		for (Node op : new NodeIterator(operations))
		{
			final Node methodIdNode = lookupSingle(op, xpath, "@method");
			Node method = null;
			if (methodIdNode != null)
			{
				method = lookupId(doc, xpath, methodIdNode.getNodeValue());
			}
			
			
			Node isStaticNode = op.getAttributes().getNamedItem("isStatic");
			boolean isStatic = isStaticNode!=null && isStaticNode.getNodeValue().equals("true");
				
			
			Operation operation = Factory.buildOperation(op, method,isStatic);
			NodeList params = lookup(op, xpath, "ownedParameter[@xmi:type='uml:Parameter']");

			for (Node parm : new NodeIterator(params))
			{
				Node typeNode = extractTypeNode(doc, xpath, parm);
				if (typeNode != null)
					operation.parameters.add(Factory.buildParameter(parm, typeNode));
			}

			ops.add(operation);
		}
		return ops;
	}

	protected Trigger extractTrigger(Document doc, XPath xpath, Node triggerNode)
			throws XPathExpressionException
	{
		Trigger trigger = null;
		Event event = null;

		if (triggerNode != null)
		{
			Node eventNode = triggerNode.getAttributes().getNamedItem("event");
			if (eventNode != null)
			{
				String eventId = eventNode.getNodeValue();
				Node signalEvent = lookupId(doc, xpath, eventId);

				final Node signalIdNode = signalEvent.getAttributes().getNamedItem("signal");
				Signal signal = null;

				if (signalIdNode != null)
				{
					Node signalNode = lookupId(doc, xpath, signalIdNode.getNodeValue());

					List<Property> properties = buildProperties(doc, xpath, signalNode);

					signal = Factory.buildSignal(signalNode, properties);
				} else
				{
					// opaque event
					signal = new Signal();
					signal.name = signalEvent.getAttributes().getNamedItem("name").getNodeValue();
					newsignals.add(signal);
				}

				event = Factory.buildEvent(signalEvent, signal);
			}
			trigger = Factory.buildTrigger(triggerNode, event);
		}
		return trigger;
	}

	protected List<Property> buildProperties(Document doc, XPath xpath,
			Node theClass) throws XPathExpressionException
	{
		List<Property> props = new Vector<Property>();

		NodeList properties = lookup(theClass, xpath, "ownedAttribute[@xmi:type='uml:Property']");

		for (Node prop : new NodeIterator(properties))
		{
			Node name = prop.getAttributes().getNamedItem("name");
			Node upper = lookupSingle(prop, xpath, "upperValue");
			Node lower = lookupSingle(prop, xpath, "lowerValue");
			Node typeNode = extractTypeNode(doc, xpath, prop);
			if (typeNode != null && name != null) {
				String type = typeNode.getAttributes().getNamedItem("xmi:type").getTextContent();
				//System.out.println("looking at property "+name.getTextContent()+" with type "+type);
				//String name = typeNode.getAttributes().getNamedItem("name").getTextContent();
				if (type.equals("uml:DataType") || 
					type.equals("uml:ValueType") || 
					(type.equals("uml:Class") && typeIsBlock(typeNode)) || 
					type.equals("uml:PrimitiveType") ||
					type.equals("uml:Enumeration")) {
					props.add(Factory.buildProperty(prop, typeNode, lower, upper));
				}
			}
		}
		return props;
	}
	
	private boolean typeIsBlock(Node type) {
		Node nname = type.getAttributes().getNamedItem("name");
		if (nname == null) return false;
		String name = nname.getTextContent();
		if (blockNames.contains(name))
			return true;
		else return false;
	}

	/**
	 * extracts the type node from a parameter or attribute. using the type attribute or child type element. The method
	 * collects the ids of the custom types used
	 * 
	 * @param doc
	 * @param xpath
	 * @param prop
	 * @return
	 * @throws XPathExpressionException
	 * @throws DOMException
	 */
	private Node extractTypeNode(Document doc, XPath xpath, Node prop)
			throws XPathExpressionException, DOMException
	{
		final Node typeAtt = prop.getAttributes().getNamedItem("type");
		Node typeNode = null;
		if (typeAtt != null)
		{
			final String typeId = typeAtt.getNodeValue();
			this.usedCustomTypes.add(typeId);
			typeNode = lookupId(doc, xpath, typeId);
		} else
		{
			typeNode = lookupSingle(prop, xpath, "type");
		}
		return typeNode;
	}

	private Node lookupId(Document doc, XPath xpath, String sourceId)
			throws XPathExpressionException
	{

		final NodeList list = lookup(doc, xpath, "//*[@xmi:id='" + sourceId
				+ "']");
		if (list.getLength() != 0)
		{
			return list.item(0);
		}
		return null;
	}

	public Node lookupSingle(Object doc, XPath xpath, String expression)
			throws XPathExpressionException
	{
		NodeList list = lookup(doc, xpath, expression);
		if (list != null)
		{
			return list.item(0);
		}
		return null;
	}

	public NodeList lookup(Object doc, XPath xpath, String expression)
			throws XPathExpressionException
	{
		XPathExpression expr = xpath.compile(expression);

		if (DEBUG)
		{
			System.out.println("Starting from: " + formateNodeWithAtt(doc));
		}
		final NodeList list = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		if (DEBUG)
		{
			System.out.print("\tFound: ");
		}
		boolean first = true;
		for (Node n : new NodeIterator(list))
		{
			if (DEBUG)
			{
				System.out.println((!first ? "\t       " : "")
						+ formateNodeWithAtt(n));
			}
			first = false;
		}
		if (first)
		{
			if (DEBUG)
			{
				System.out.println("none");
			}
		}
		return list;

	}

	public static String formateNodeWithAtt(Object o)
	{
		if (o instanceof Document)
		{
			return "Root document";
		} else if (o instanceof Node)
		{
			Node node = (Node) o;

			String tmp = "";
			tmp = node.getLocalName();
			if (node.hasAttributes())
			{
				for (Node att : new NamedNodeMapIterator(node.getAttributes()))
				{
					tmp += " " + att + ", ";
				}
			}
			return tmp;
		}
		return o.toString();
	}
}
