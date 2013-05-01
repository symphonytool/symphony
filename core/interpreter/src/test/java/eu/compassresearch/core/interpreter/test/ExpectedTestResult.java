package eu.compassresearch.core.interpreter.test;

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
			boolean throwsException = false;
			NodeList exceptionNl = docEle.getElementsByTagName("exception");

			if(exceptionNl != null && exceptionNl.getLength() > 0)
			{
				Node firstChildNode = exceptionNl.item(0).getFirstChild();
				if(firstChildNode != null && firstChildNode.getNodeValue() != null)
					throwsException = true;
			}
			
			//get a nodelist of elements
			NodeList nl = docEle.getElementsByTagName("events");
			
			List<List<String>> traces = new LinkedList<List<String>>();
						
			//Node n = nl.item(0);
			
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
			
			//get a nodelist of elements
			nl = docEle.getElementsByTagName("timedTrace");
			
			List<List<String>> timedTraces = new LinkedList<List<String>>();
						
			//Node n = nl.item(0);
			
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
			
			testResult = new ExpectedTestResult(traces,timedTraces,throwsException);

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}
		
		return testResult;
	}
	
	//The visible traces that the model should produce
	private final List<List<String>> eventTraces;
	//The timed traces that the model should produce including the tock events
	private final List<List<String>> timedTraces;
	//This indicates whether the model should fail with an exception or not
	private final boolean shouldFail;
	
	//private String exceptionName;
	
	public ExpectedTestResult(List<List<String>> eventTraces,List<List<String>> timedTraces, boolean shouldFail)
	{
		this.eventTraces = eventTraces;
		this.shouldFail = shouldFail;
		this.timedTraces = timedTraces;
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
		return shouldFail;
	}
	
}
