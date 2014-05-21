package eu.compassresearch.core.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.core.parser.PreParser.StreamType;

/**
 * Parser utility class for parsing CML files. All use of the CML parser should happen through this class
 * @author kel
 *
 */
public class ParserUtil
{
	public static class ParserResult
	{
		public final List<CmlParserError> errors;
		public final List<PDefinition> definitions;
		public final PExp exp;

		public ParserResult(List<CmlParserError> errors,
				List<PDefinition> definitions, PExp exp)
		{
			this.errors = errors;
			this.definitions = definitions;
			this.exp = exp;
		}

		public ParserResult(List<CmlParserError> errors,
				List<PDefinition> definitions)
		{
			this(errors, definitions, null);
		}

		public ParserResult(List<CmlParserError> errors, PExp exp)
		{
			this(errors, null, exp);
		}

		public void printErrors(PrintStream printer)
		{
			for (CmlParserError err : errors)
			{
				printer.println(err.toString());
			}

		}
	}

	private enum Type
	{
		Definition, Expression, Statement
	};

	public static ParserResult parse(File file, InputStream input,
			StreamType streamType)
	{
		return parse(file, input, Type.Definition, streamType);
	}
	
	
	public static ParserResult parse(File file, String source, String charset) throws UnsupportedEncodingException, IOException
	{
		StreamType streamType = PreParser.detectStreamType(getCharStream(source, charset));
		return parse(file, getCharStream(source, charset), Type.Definition, streamType);
	}

	public static ParserResult parseExpression(File file, InputStream input,
			StreamType streamType)
	{
		return parse(file, input, Type.Expression, streamType);
	}

	private static ParserResult parse(File file, InputStream input, Type type,
			StreamType streamType)
	{
		// boolean ok = true;
		List<CmlParserError> errors = new Vector<CmlParserError>();
		List<PDefinition> definitions = new Vector<PDefinition>();
		PExp exp = null;

		CmlLexer lexer = null;
		CmlParser parser = null;
		try
		{

			lexer = new CmlLexer(new ANTLRInputStream(PreParser.getInputStream(input, streamType)));
			lexer.sourceFileName = file.getPath();
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			parser = new CmlParser(tokens);
			parser.sourceFileName = lexer.sourceFileName;

			switch (type)
			{

				case Expression:
					exp = parser.expression().exp;
					break;
				case Statement:
					break;
				case Definition:
				default:
					definitions.addAll(parser.source());
					break;

			}

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
		} finally
		{
			input = null;
		}

		errors.addAll(lexer.getErrors());
		errors.addAll(parser.getErrors());

		return new ParserResult(errors, definitions, exp);

	}
	
	public static String getLanguageVersion()
	{
		return CmlParser.CML_LANG_VERSION;
	}

	 public static InputStream getCharStream(String content, String charset)
	 throws UnsupportedEncodingException, IOException
	 {
	 return new ByteArrayInputStream(content.getBytes(charset));
	 }

	private static InputStream getCharStream(File file)
			throws FileNotFoundException, IOException
	{
		return new FileInputStream(file);
	}

	public static ParserResult parse(List<File> files)
			throws FileNotFoundException, IOException
	{
		return parse(files.toArray(new File[] {}));
	}

	public static ParserResult parse(File... files)
			throws FileNotFoundException, IOException
	{
		List<CmlParserError> errors = new Vector<CmlParserError>();
		List<PDefinition> definitions = new Vector<PDefinition>();
		for (File file : files)
		{
			ParserResult res = parse(file, getCharStream(file), PreParser.detectStreamType(getCharStream(file)));

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
