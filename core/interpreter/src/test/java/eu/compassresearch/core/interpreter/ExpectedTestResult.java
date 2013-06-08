package eu.compassresearch.core.interpreter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;

public class ExpectedTestResult {

	public static ExpectedTestResult parseTestResultFile(String filePath) throws IOException 
	{
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		ExpectedTestResult testResult = null;
		
		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse(filePath);
			Element docEle = dom.getDocumentElement();
			
			//parse the type of the result
			//TODO this should pass on the expected exception string
			String exceptionName = null;
			NodeList exceptionNl = docEle.getElementsByTagName("exception");
			if(exceptionNl != null && exceptionNl.getLength() > 0)
			{
				Node firstChildNode = exceptionNl.item(0).getFirstChild();
				if(firstChildNode != null && firstChildNode.getNodeValue() != null)
					exceptionName = firstChildNode.getNodeValue();
			}
			
			//Parse the expected events
			//get a nodelist of elements
			NodeList nl = docEle.getElementsByTagName("events");
			List<List<String>> traces = new LinkedList<List<String>>();
						
			for(int i = 0; i < nl.getLength();i++)
			{
				Node n = nl.item(i);
				LinkedList<String> trace = new LinkedList<String>();
				
				if(n.hasChildNodes())
				{
					String value = n.getFirstChild().getNodeValue();

					for(String s : value.split(",(?! )"))
					{
						trace.add(s);
					}
				}
				traces.add(trace);
			}
			
			//Parse the expected timed trace 
			nl = docEle.getElementsByTagName("timedTrace");
			List<List<String>> timedTraces = new LinkedList<List<String>>();
						
			for(int i = 0; i < nl.getLength();i++)
			{
				Node n = nl.item(i);
				LinkedList<String> timedTrace = new LinkedList<String>();
				
				if(n.hasChildNodes())
				{
					String value = n.getFirstChild().getNodeValue();

					for(String s : value.split(",(?! )"))
					{
						timedTrace.add(s);
					}
				}
				timedTraces.add(timedTrace);
			}
			
			testResult = new ExpectedTestResult(traces,timedTraces,exceptionName,parseInterpreterState(docEle));

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}
		
		return testResult;
	}
	
	private static CmlInterpreterState parseInterpreterState(Element docEle)
	{
		Node stateNode = docEle.getElementsByTagName("interpreterState").item(0).getFirstChild();
		
		if(stateNode != null)
		{
			return CmlInterpreterState.valueOf(stateNode.getNodeValue());
		}
		else
			return null;
		
	}
	
	//The visible traces that the model should produce
	private final List<List<String>> eventTraces;
	//The timed traces that the model should produce including the tock events
	private final List<List<String>> timedTraces;
	/**
	 * The name of the exception that should be thrown
	 */
	private final String exceptionName;
	/**
	 * The state of the interpreter when the test are executed
	 */
	private final CmlInterpreterState state;
	
	public ExpectedTestResult(List<List<String>> eventTraces,List<List<String>> timedTraces, String exceptionName,CmlInterpreterState state)
	{
		this.eventTraces = eventTraces;
		this.timedTraces = timedTraces;
		this.exceptionName = exceptionName;
		this.state = state;
	}
	
	public CmlInterpreterState getInterpreterState() {
		return state;
	}
	
	public boolean isInterleaved()
	{
		return this.eventTraces.size() > 1;
	}
	
	public List<String> getFirstEventTrace()
	{
		return this.eventTraces.get(0);
	}
		
	public List<List<String>> getEventTraces()
	{
		return this.eventTraces;
	}
	
	public List<String> getFirstTimedTrace()
	{
		return this.timedTraces.get(0);
	}
	
	public boolean hasTimedTrace()
	{
		return timedTraces.size() > 0;
	}
	
	public List<List<String>> getTimedTraces()
	{
		return this.timedTraces;
	}
	
	public boolean throwsException()
	{
		return exceptionName != null;
	}
	
}
