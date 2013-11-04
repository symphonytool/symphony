package eu.compassresearch.core.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.definitions.PDefinition;

public class ParserUtil
{
	public static class ParserResult
	{
		public final List<CmlParserError> errors;
		public final List<PDefinition> definitions;

		public ParserResult(List<CmlParserError> errors,
				List<PDefinition> definitions)
		{
			this.errors = errors;
			this.definitions = definitions;
		}
	}

	public static ParserResult parse(File file, CharStream input)
	{
		// boolean ok = true;
		List<CmlParserError> errors = new Vector<CmlParserError>();
		List<PDefinition> definitions = new Vector<PDefinition>();

		CmlLexer lexer = null;
		CmlParser parser = null;
		try
		{
			lexer = new CmlLexer(input);
			lexer.sourceFileName = file.getName();
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			parser = new CmlParser(tokens);
			parser.sourceFileName = lexer.sourceFileName;
			definitions.addAll(parser.source());

			// ok &= true;
		} catch (Exception e)
		{
			if (!(e instanceof RecognitionException))
			{
				String msg = "";
				if (e instanceof RuntimeException)
				{
					msg = getErrorMessages((RuntimeException) e);
				} else
				{
					msg = e.getMessage();
				}
				errors.add(new CmlParserError(msg, null, file.getName(), lexer.getLine(), 0, 0, 0));
			}
		}

		errors.addAll(lexer.getErrors());
		errors.addAll(parser.getErrors());

		return new ParserResult(errors, definitions);

	}

	public static CharStream getCharStream(String content, String charset)
			throws UnsupportedEncodingException, IOException
	{
		return new ANTLRInputStream(new ByteArrayInputStream(content.getBytes(charset)));
	}

	public static CharStream getCharStream(File file) throws FileNotFoundException, IOException 
	{
		return new ANTLRInputStream(new FileInputStream(file));
	}
	
	public static ParserResult parse(List<File> files) throws FileNotFoundException, IOException
	{
		return parse(files.toArray(new File[]{}));
	}

	public static ParserResult parse(File... files) throws FileNotFoundException, IOException
	{
		List<CmlParserError> errors = new Vector<CmlParserError>();
		List<PDefinition> definitions = new Vector<PDefinition>();
		for (File file : files)
		{
			ParserResult res = parse(file, getCharStream(file));

			errors.addAll(res.errors);
			definitions.addAll(res.definitions);
		}

		return new ParserResult(errors, definitions);

	}

	public static String getErrorMessages(Exception e)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(b);
		e.printStackTrace(pw);
		pw.flush();
		try
		{
			b.flush();
		} catch (IOException e1)
		{
		}
		return new String(b.toByteArray());
	}
}
