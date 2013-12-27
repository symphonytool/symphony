package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;

public class ExpectedTestResult
{

	public static ExpectedTestResult parseTestResultFile(String filePath)
			throws IOException
	{
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		ExpectedTestResult testResult = null;

		try
		{

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			Document dom = db.parse(filePath);
			Element docEle = dom.getDocumentElement();

			// validate the xml
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("src/test/resources/testSchema.xsd"));
			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(dom));

			// parse exception element
			String exceptionName = null;
			NodeList exceptionNl = docEle.getElementsByTagName("exception");
			if (exceptionNl != null && exceptionNl.getLength() > 0)
			{
				Node firstChildNode = exceptionNl.item(0).getFirstChild();
				if (firstChildNode != null
						&& firstChildNode.getNodeValue() != null)
				{
					exceptionName = firstChildNode.getNodeValue();
				}
			}

			// Parse the expected events
			Pattern traces = extractPatternFromNodeList(docEle.getElementsByTagName("events"));

			// Parse the expected timed trace
			Pattern timedTraces = extractPatternFromNodeList(docEle.getElementsByTagName("timedTrace"));

			// Parse the interpreter state
			CmlInterpreterState interpreterState = parseInterpreterState(docEle);

			testResult = new ExpectedTestResult(traces, timedTraces, exceptionName, interpreterState);

		} catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		} catch (SAXException se)
		{
			se.printStackTrace();
		}

		return testResult;
	}

	private static CmlInterpreterState parseInterpreterState(Element docEle)
	{
		Node stateNode = docEle.getElementsByTagName("interpreterState").item(0).getFirstChild();

		if (stateNode != null)
		{
			return CmlInterpreterState.valueOf(stateNode.getNodeValue());
		} else
		{
			return null;
		}
	}

	private static Pattern extractPatternFromNodeList(NodeList nl)
	{
		// Pattern pattern = defaultPattern;
		Pattern pattern = null;

		// only move on if the element is not null or the length of the list is more tha zero
		if (nl.getLength() > 0)
		{
			Node n = nl.item(0);
			if (n.hasChildNodes())
			{
				String value = n.getFirstChild().getNodeValue();
				pattern = Pattern.compile(value);
			} else
			{
				pattern = Pattern.compile("");
			}
		}

		return pattern;
	}

	// The visible traces that the model should produce
	private final Pattern eventTraces;
	// The timed traces that the model should produce including the tock events
	private final Pattern timedTraces;
	/**
	 * The name of the exception that should be thrown
	 */
	private final String exceptionName;
	/**
	 * The state of the interpreter when the test are executed
	 */
	private final CmlInterpreterState state;

	public ExpectedTestResult(Pattern eventTraces, Pattern timedTraces,
			String exceptionName, CmlInterpreterState state)
	{
		this.eventTraces = eventTraces;
		this.timedTraces = timedTraces;
		this.exceptionName = exceptionName;
		this.state = state;
	}

	public CmlInterpreterState getInterpreterState()
	{
		return state;
	}

	public Pattern getExpectedEventTracePattern()
	{
		return this.eventTraces;
	}

	public boolean hasTimedTrace()
	{
		return timedTraces != null;
	}

	public Pattern getExpectedTimedTracePattern()
	{
		return this.timedTraces;
	}

	public boolean throwsException()
	{
		return exceptionName != null;
	}

}
