package eu.compassresearch.core.s2c;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.compassresearch.core.s2c.dom.Factory;
import eu.compassresearch.core.s2c.dom.StateMachine;
import eu.compassresearch.core.s2c.util.NamedNodeMapIterator;
import eu.compassresearch.core.s2c.util.NodeIterator;
import eu.compassresearch.core.s2c.util.UniversalNamespaceResolver;

public class S2cTranslator
{
	private static final String ANY_STATE_MACHINE_IN_ANY_PACKAGES_CLASS = "//packagedElement[@xmi:type='uml:Class']/ownedBehavior[@xmi:type='uml:StateMachine']";

	public static void main(String[] args) throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException
	{
		new S2cTranslator().translate(new File("src/test/resources/s2c-lite-initial-model.xmi".replace('/', File.separatorChar)), null);
	}

	/**
	 * See this for xpath {@link "http://www.w3schools.com/XPath/xpath_syntax.asp"}
	 * 
	 * @param input
	 * @param output
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public void translate(File input, File output)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(input);
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();

		xpath.setNamespaceContext(new UniversalNamespaceResolver(doc));

		System.out.println("Find the state machine");
		NodeList stateMachines = lookup(doc, xpath, ANY_STATE_MACHINE_IN_ANY_PACKAGES_CLASS);

		System.out.println("Find the class thats that parent of the state machine");
		Node theClass = lookup(stateMachines.item(0), xpath, "..").item(0);

		System.out.println("Class properties");
		NodeList properties = lookup(theClass, xpath, "ownedAttribute[@xmi:type='uml:Property']");

		for (Node prop : new NodeIterator(properties))
		{
			lookup(prop, xpath, "upperValue");
			lookup(prop, xpath, "loweerValue");
		}

		System.out.println("Class operations");
		NodeList operations = lookup(theClass, xpath, "ownedOperation[@xmi:type='uml:Operation']");

		System.out.println("##\nState machine\n##");
		final Node stateMachine = stateMachines.item(0);
		String statemachineName = stateMachine.getAttributes().getNamedItem("name").getNodeValue();
		System.out.println("State machine name = " + statemachineName);
		StateMachine sm = Factory.buildStateMachine(stateMachine);

		System.out.println("The final state");
		Node finalState = lookup(stateMachine, xpath, "//subvertex[@xmi:type='uml:FinalState']").item(0);

		sm.states.add(Factory.buildState(finalState, lookupSingle(finalState, xpath, "entry"), lookupSingle(finalState, xpath, "exit")));

		System.out.println("Any State");
		NodeList anyStates = lookup(stateMachine, xpath, "//subvertex[@xmi:type='uml:State']");
		for (Node n : new NodeIterator(anyStates))
		{
			sm.states.add(Factory.buildState(n, lookupSingle(n, xpath, "entry"), lookupSingle(n, xpath, "exit")));
		}

		System.out.println("Any Pseudostates");
		NodeList anyPseudoStates = lookup(stateMachine, xpath, "//subvertex[@xmi:type='uml:Pseudostate']");
		for (Node n : new NodeIterator(anyPseudoStates))
		{
			sm.states.add(Factory.buildState(n, lookupSingle(n, xpath, "entry"), lookupSingle(n, xpath, "exit")));
		}

		System.out.println("--- The transactions ---");
		NodeList transactions = lookup(stateMachine, xpath, "//transition[@xmi:type='uml:Transition']");

		for (Node t : new NodeIterator(transactions))
		{
			NamedNodeMap atts = t.getAttributes();

			String sourceId = atts.getNamedItem("source").getNodeValue();
			String targetId = atts.getNamedItem("target").getNodeValue();

			System.out.println("Looking up translation details for: "
					+ t.getAttributes().getNamedItem("xmi:id"));
			Node source = lookupId(doc, xpath, sourceId);
			Node target = lookupId(doc, xpath, targetId);
			Node guard = null;
			if (atts.getNamedItem("guard") != null)
			{
				String guardId = atts.getNamedItem("guard").getNodeValue();
				guard = lookupId(doc, xpath, guardId);
			}

			Node effect = lookupSingle(t, xpath, "effect[@xmi:type='uml:OpaqueBehavior']");
			Node trigger = lookupSingle(t, xpath, "trigger[@xmi:type='uml:Trigger']");

			sm.transitions.add(Factory.buidlTransition(sm, t, effect, sourceId, targetId, guard));

			System.out.println("\n");
		}

		System.out.println("----------------------------------------------------------------------------");
		System.out.println(sm);

		new SysMlToCmlTranslator(sm).translate(output);
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

		System.out.println("Starting from: " + formateNodeWithAtt(doc));
		final NodeList list = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		System.out.print("\tFound: ");
		boolean first = true;
		for (Node n : new NodeIterator(list))
		{
			System.out.println((!first ? "\t       " : "")
					+ formateNodeWithAtt(n));
			first = false;
		}
		if (first)
		{
			System.out.println("none");
		}
		return list;

	}

	public String formateNodeWithAtt(Object o)
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
