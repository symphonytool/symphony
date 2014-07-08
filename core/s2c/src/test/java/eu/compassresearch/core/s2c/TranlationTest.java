package eu.compassresearch.core.s2c;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class TranlationTest
{
	@Test
	public void TranslateDwarfSimple() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		translate(new File("src/test/resources/Dwarf/DwarfSimple.xmi".replace('/', File.separatorChar)));
	}
	
	@Test
	public void TranslateS2CLight() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		translate(new File("src/test/resources/s2c-lite-initial-model2.xmi".replace('/', File.separatorChar)));
	}
	
	@Test
	public void TranslateHSUV() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		translate(new File("src/test/resources/HSUV/HSUV.xmi".replace('/', File.separatorChar)));
	}

	public File translate(File input) throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException
	{
		final File output = new File("target".replace('/', File.separatorChar));
		new S2cTranslator().translate(input, output);
		return output;
	}
}
