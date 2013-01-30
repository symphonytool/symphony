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

public class TestResult {

	public static TestResult parseTestResultFile(String filePath) throws IOException 
	{
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		TestResult testResult = null;
		
		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse(filePath);

			Element docEle = dom.getDocumentElement();

			//get a nodelist of elements
			NodeList nl = docEle.getElementsByTagName("visibleTrace");
			
			List<List<String>> traces = new LinkedList<List<String>>();
						
			//Node n = nl.item(0);
			
			for(int i = 0; i < nl.getLength();i++)
			{
				Node n = nl.item(i);
				LinkedList<String> trace = new LinkedList<String>();
				
				if(n.hasChildNodes())
				{
					String value = n.getFirstChild().getNodeValue();

					for(String s : value.split(";"))
					{
						trace.add(s);
					}
				}
				traces.add(trace);
			}
			
			testResult = new TestResult(traces);

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}
		
		return testResult;
	}
	
	private List<List<String>> visibleTraces;
	
	public TestResult(List<List<String>> visibleTraces)
	{
		this.visibleTraces = visibleTraces;
	}
	
	public boolean isInterleaved()
	{
		return this.visibleTraces.size() > 1;
	}
	
	public List<String> getFirstVisibleTrace()
	{
		return this.visibleTraces.get(0);
	}
		
	public List<List<String>> getVisibleTraces()
	{
		return this.visibleTraces;
	}
	
}
