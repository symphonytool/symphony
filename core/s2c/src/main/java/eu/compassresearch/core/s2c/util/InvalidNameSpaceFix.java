package eu.compassresearch.core.s2c.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class InvalidNameSpaceFix
{
	public static File createFixedFile(File file) throws IOException
	{
		FileReader fr = null;
		FileWriter fw = null;
		final File output = new File(file.getParentFile(), file.getName()
				+ ".tmp");
		try
		{
			fr = new FileReader(file);
			fw = new FileWriter(output);
			int c =-1;
			boolean inElemName = false;
			boolean seenColon = false;
			String read = "";
			Set<String> customNameSpaces = new HashSet<String>();
			StringBuffer buffer = new StringBuffer();
			
			while ((c= fr.read()) != -1)
			{
				if(c=='<')
				{
					inElemName = true;
					seenColon = false;
					read="";
				}
				if(c==' ' || c=='/')
				{
					inElemName = false;
				}
				
				if(c==':' && inElemName)
				{
					if(seenColon)
					{
						customNameSpaces.add(read.split(":")[0].substring(1));
						c='-';
					}
					seenColon = true;
				}
				buffer.append((char)c);
				read+=(char)c;
			}
			
			final String XMI = "<xmi:XMI ";
			final String NS_DEFAULT="xmlns:%s = \"http://www.omg.org/spec/UML/20090901/StandardProfileL2\" ";
			int index = buffer.indexOf(XMI);
			for (String ns : customNameSpaces)
			{
				buffer.insert(index+XMI.length(),String.format(NS_DEFAULT,  ns))	;
			}
			fw.write(buffer.toString());
			
		} catch (IOException e)
		{
			System.out.println(e);
		} finally
		{
			fr.close();
			fw.close();
		}
		return output;
	}
}
