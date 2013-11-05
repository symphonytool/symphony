package eu.compassresearch.ide.core.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.Dialect;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.config.Settings;
import org.overture.ide.core.parser.AbstractParserParticipant;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.parser.messages.VDMError;
import org.overture.parser.messages.VDMWarning;

import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.parser.CmlParserError;

public class SourceParserCml extends AbstractParserParticipant
{

	@Override
	protected ParseResult startParse(IVdmSourceUnit file, String source,
			String charset)
	{
		Settings.dialect = Dialect.CML;
		return startParseFile(file, source, charset);
	}

	protected ParseResult startParseFile(IVdmSourceUnit file, String source,
			String charset)
	{
		ParseResult result = new ParseResult();
		List<INode> parselist = new Vector<INode>();

		CmlLexer lexer = null;
		CmlParser parser = null;

		List<VDMError> errors = new Vector<VDMError>();
		List<VDMWarning> warnings = new Vector<VDMWarning>();
		try
		{

			ByteArrayInputStream is = new ByteArrayInputStream(source.getBytes(charset));
			ANTLRInputStream in = null;
			in = new ANTLRInputStream(is);
			lexer = new CmlLexer(in);
			lexer.sourceFileName = file.getFile().getLocation().toOSString();
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			parser = new CmlParser(tokens);
			parser.sourceFileName = lexer.sourceFileName;

			List<PDefinition> paragraphs = parser.source();
			List<PDefinition> notNullParagraphs = new LinkedList<PDefinition>();
			for (PDefinition par : paragraphs)
			{
				if (par != null)
				{
					notNullParagraphs.add(par);
				} else
				{
					errors.add(getErrorMessage(file, "Parser gave back a null paragraph.", 1));
				}
			}
			parselist.addAll(notNullParagraphs);

		} catch (Exception e1)
		{
			if (!(e1 instanceof RecognitionException))
			{
				String msg = "";
				if (e1 instanceof RuntimeException)
				{
					msg = getErrorMessages((RuntimeException) e1);
				} else
				{
					msg = e1.getMessage();
				}
				errors.add(getErrorMessage(file, msg, lexer.getLine()));
			}
		}

		if (lexer != null)
		{
			for (CmlParserError error : lexer.getErrors())
			{
				errors.add(new VDMError(0, error.message, error.getLocation(file.getSystemFile())));

			}
		}
		if (parser != null)
		{
			for (CmlParserError error : parser.getErrors())
			{
				errors.add(new VDMError(0, error.message, error.getLocation(file.getSystemFile())));

			}
		}

		result.setAst(parselist);
		result.setErrors(errors);
		result.setWarnings(warnings);

		return result;
	}
	
	public static String getErrorMessages(Exception e) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(b);
		e.printStackTrace(pw);
		pw.flush();
		try {
			b.flush();
		} catch (IOException e1) {
		}
		return new String(b.toByteArray());
	}

	private VDMError getErrorMessage(IVdmSourceUnit unit, String msg, int line)
	{
		LexLocation location = new LexLocation(unit.getSystemFile(), "", line, 0, line, 0, 0, 0);
		VDMError error = new VDMError(0, msg, location);
		return error;
	}

}
