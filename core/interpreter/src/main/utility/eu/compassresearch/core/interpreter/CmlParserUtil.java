package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

public class CmlParserUtil
{

	public static boolean parseSource(PSource source) throws IOException
	{
		boolean parseOK = false;

		ANTLRInputStream in = null;
		File file = null;
		if (source instanceof AFileSource)
		{
			in = new ANTLRInputStream(new FileInputStream(((AFileSource) source).getFile()));
			file = ((AFileSource) source).getFile();
		}

		if (source instanceof AInputStreamSource)
			in = new ANTLRInputStream(((AInputStreamSource) source).getStream());

		if (in == null)
			return false;

		CmlLexer lexer = new CmlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);
		parser.sourceFileName = file.getAbsolutePath();

		try
		{
			List<PDefinition> sourceDefs = parser.source();
			source.setParagraphs(new LinkedList<PDefinition>());
			if (sourceDefs != null)
			{
				for (PDefinition def : sourceDefs)
					if (def != null)
						source.getParagraphs().add(def);
				parseOK = true;
			} else
				parseOK = false;
		} catch (RecognitionException e)
		{
			e.printStackTrace();
			parseOK = false;
		}

		return parseOK;
	}
}
