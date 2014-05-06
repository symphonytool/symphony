package eu.compassresearch.core.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Stream to skip all except the text between {@code \\begin\ cml\} \\end\{cml\}}
 * 
 * @author kel
 */
public class LatexCmlEnvInputStream extends InputStream
{

	public static final String END_CML = "\\end{cml}";
	public static final String BEGIN_CML = "\\begin{cml}";

	final Queue<Character> q = new ArrayDeque<Character>();
	final Queue<Integer> lookahead = new ArrayDeque<Integer>();
	final InputStream input;
	boolean skipping = true;

	public LatexCmlEnvInputStream(InputStream input)
	{
		this.input = input;
	}

	private void add(int e)
	{
		if (q.size() > 10)
		{
			q.poll();
		}
		q.add((char) e);
	}

	/**
	 * reads a char from the stream or ' ' is in skipping mode. The mode is changed to read if the
	 * {@link LatexCmlEnvInputStream#BEGIN_CML} tag is read and changed to skipping if {@link LatexCmlEnvInputStream#END_CML} tag is
	 * read
	 */
	@Override
	public int read() throws IOException
	{
		if(lookahead.isEmpty())
		{
			for (int i = 0; i < END_CML.length(); i++)
			{
				lookahead.add(input.read());
			}
		}
		int value = lookahead.poll();
		lookahead.add(input.read());
		add(value);
		
		if(value !=-1 && isSkipping())
		{
			value = ' ';
		}
		return value;
	}

	/**
	 * Checks if the reader is in skipping mode. The method changed the internal next skipping mode based on the current
	 * read value and returns the current skipping mode
	 * 
	 * @return
	 */
	private boolean isSkipping()
	{
		boolean ret = skipping;
		if (skipping && contains(q, BEGIN_CML))
		{
			skipping = false;
		} else if (!skipping && contains(lookahead, END_CML))
		{
			skipping = true;
		}

		return ret;
	}

	/**
	 * checks if the buffer ends with the string given
	 * 
	 * @param buf
	 * @param string
	 * @return
	 */
	boolean contains(@SuppressWarnings("rawtypes") Queue buf, String string)
	{
		Object[] arr = buf.toArray();
		if (arr.length >= string.length())
		{
			for (int i = arr.length - 1, j = string.length() - 1; i >= 0; i--, j--)
			{
				final Object elem =  arr[i];
				char c;
				
				if(elem instanceof Character)
				{
					c = (char) arr[i];
					
				}else if(elem instanceof Integer)
				{
					c = (char)((int) arr[i]);
				}else
				{
					return false;//not supported
				}
				
				
				if (j >= 0 && c != string.charAt(j))
				{
					return false;
				}
				
				
			}
			return true;
		}
		return false;
	}
	
	

	@Override
	public int available() throws IOException
	{
		return input.available();
	}

	@Override
	public void close() throws IOException
	{
		input.close();
	}

	@Override
	public synchronized void mark(int readlimit)
	{
		input.mark(readlimit);
	}

	@Override
	public boolean markSupported()
	{
		return input.markSupported();
	}

}
