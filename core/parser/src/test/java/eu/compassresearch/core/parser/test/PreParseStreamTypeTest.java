package eu.compassresearch.core.parser.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.PreParser;

public class PreParseStreamTypeTest
{
	@Test
	public void checkPlain() throws UnsupportedEncodingException, IOException
	{
		Assert.assertEquals(PreParser.StreamType.Plain, PreParser.detectStreamType(ParserUtil.getCharStream("kjhjkhkj", "utf-8")));
	}
	
	@Test
	public void checkLatex() throws UnsupportedEncodingException, IOException
	{
		Assert.assertEquals(PreParser.StreamType.Latex, PreParser.detectStreamType(ParserUtil.getCharStream("kjhjk \\begin{cml} hkj", "utf-8")));
	}
}
