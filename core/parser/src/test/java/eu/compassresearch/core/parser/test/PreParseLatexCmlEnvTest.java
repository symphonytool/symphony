package eu.compassresearch.core.parser.test;

import static eu.compassresearch.core.parser.LatexCmlEnvInputStream.BEGIN_CML;
import static eu.compassresearch.core.parser.LatexCmlEnvInputStream.END_CML;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

import eu.compassresearch.core.parser.LatexCmlEnvInputStream;
import eu.compassresearch.core.parser.ParserUtil;

public class PreParseLatexCmlEnvTest
{
	@Test
	public void checkEmpty() throws UnsupportedEncodingException, IOException
	{
		Assert.assertEquals("", parse("nothing").trim());
	}

	@Test
	public void checkBeginOnly() throws UnsupportedEncodingException,
			IOException
	{
		Assert.assertEquals("process A end", parse("something" + BEGIN_CML
				+ "process A end").trim());
	}

	@Test
	public void checkBeginAndEnd() throws UnsupportedEncodingException,
			IOException
	{
		Assert.assertEquals("process A end", parse("something" + BEGIN_CML
				+ "process A end" + END_CML + "nothing").trim());
	}

	@Test
	public void checkEnd() throws UnsupportedEncodingException, IOException
	{
		Assert.assertEquals("", parse("something process A end" + END_CML + "nothing").trim());
	}

	@Test
	public void checkEndBegin() throws UnsupportedEncodingException,
			IOException
	{
		Assert.assertEquals("this", parse("something" + END_CML + "process A end"
				+ BEGIN_CML + "this").trim());
	}

	@Test
	public void checkBeginAndEndEnd() throws UnsupportedEncodingException,
			IOException
	{
		Assert.assertEquals("process A end", parse("something" + BEGIN_CML
				+ "process A end" + END_CML + "nothing" + END_CML + "nothing").trim());
	}

	@Test
	public void checkBeginBeginAndEnd() throws UnsupportedEncodingException,
			IOException
	{
		Assert.assertEquals("after1" + BEGIN_CML + "process A end", parse("before"
				+ BEGIN_CML
				+ "after1"
				+ BEGIN_CML
				+ "process A end"
				+ END_CML
				+ "after").trim());
	}

	@Test
	public void checkBeginBeginAndEndEnd() throws UnsupportedEncodingException,
			IOException
	{
		Assert.assertEquals("after1" + BEGIN_CML + "process A end", parse("before"
				+ BEGIN_CML
				+ "after1"
				+ BEGIN_CML
				+ "process A end"
				+ END_CML
				+ "after1" + END_CML + "after2").trim());
	}

	private static String parse(String test)
			throws UnsupportedEncodingException, IOException
	{
		final String act = readInputStreamAsString(new LatexCmlEnvInputStream(ParserUtil.getCharStream(test, "utf-8")));
		return act;
	}

	static String readInputStreamAsString(InputStream in)
			throws IOException
	{

		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1)
		{
			byte b = (byte) result;
			buf.write(b);
			result = bis.read();
		}
		return buf.toString();
	}

	static String convertStreamToString(java.io.InputStream is)
	{

		String res;
		java.util.Scanner s = new java.util.Scanner(is);
		s.useDelimiter("\\A");
		try
		{
			res = s.hasNext() ? s.next() : "";
		} finally
		{
			s.close();
		}
		return res;
	}
}
