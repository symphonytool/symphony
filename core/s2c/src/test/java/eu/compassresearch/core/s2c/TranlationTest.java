package eu.compassresearch.core.s2c;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import eu.compassresearch.core.typechecker.TestUtil;

public class TranlationTest
{
	@Test
	public void TranslateDwarfSimple() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		check(translate(new File("src/test/resources/Dwarf/DwarfSimple.xmi".replace('/', File.separatorChar))));
	}
	
	@Test
	public void TranslateS2CLight() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		check(translate(new File("src/test/resources/s2c-lite-initial-model2.xmi".replace('/', File.separatorChar))));
	}
	
	@Test
	public void TranslateS2CLightStatic() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		check(translate(new File("src/test/resources/s2c-lite-initial-model2-static.xmi".replace('/', File.separatorChar))));
	}
	
	@Test
	public void TranslateHSUV() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		check(translate(new File("src/test/resources/HSUV/HSUV.xmi".replace('/', File.separatorChar))));
	}
	
//	@Test
//	public void TranslateBeoControlStreaming() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
//	{
//		check(translate(new File("src/test/resources/Streaming/BeoControlStreamingSosDesignModel.xmi".replace('/', File.separatorChar))));
//	}
//	
//	@Test
//	public void TranslateBeoControlStreamingUpdated() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
//	{
//		check(translate(new File("src/test/resources/Streaming/BeoControlStreamingSosDesignModel-updated.xmi".replace('/', File.separatorChar))));
//	}

	@Test
	public void TranslateInsiel() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		check(translate(new File("src/test/resources/Insiel/insiel.xmi".replace('/', File.separatorChar))));
	}
	
	public File translate(File input) throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException
	{
		final File output = new File("target".replace('/', File.separatorChar));
		return new S2cTranslator().translate(input, output,true);
	}
	
	public void check(File file) throws IOException
	{
		TestUtil.TypeCheckerResult res =  TestUtil.runTypeChecker(file.getAbsolutePath());
		
		if ( !res.parseErrors.isEmpty())
		{
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			PrintWriter out = new PrintWriter(outStream);


			for (String s : res.parseErrors)
			{
				out.println(s);
			}
			
			out.flush();
			Assert.assertTrue("Unexpected parse errors in model.\n\n"
					+ outStream.toString(), !res.parseErrors.isEmpty());

		} else
		if ( res.issueHandler.hasErrors())
		{
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			PrintWriter out = new PrintWriter(outStream);
			res.issueHandler.printErrors(out);
			out.flush();
			Assert.assertTrue("Unexpected type errors in model.\n\n"
					+ outStream.toString(), !res.issueHandler.hasErrors());

		} 
	}
}
