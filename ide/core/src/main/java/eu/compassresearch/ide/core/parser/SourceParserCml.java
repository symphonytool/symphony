package eu.compassresearch.ide.core.parser;

import java.util.List;
import java.util.Vector;

import org.overture.ast.lex.Dialect;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.config.Settings;
import org.overture.ide.core.parser.AbstractParserParticipant;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.parser.messages.VDMError;
import org.overture.parser.messages.VDMWarning;

import eu.compassresearch.core.parser.CmlParserError;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;

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

		List<VDMError> errors = new Vector<VDMError>();
		List<VDMWarning> warnings = new Vector<VDMWarning>();

		ParserResult parserResult;
		try
		{
			parserResult = ParserUtil.parse(file.getSystemFile(), source, charset);

			if (parserResult.errors.isEmpty())
			{
				parselist.addAll(parserResult.definitions);
			}

			for (CmlParserError error : parserResult.errors)
			{
				errors.add(new VDMError(0, error.message, error.getLocation(file.getSystemFile())));

			}

		} catch (Exception e)
		{
			errors.add(new VDMError(0, "internal parser error", new LexLocation(file.getSystemFile(), "", 0, 0, 0, 0, 0, 0)));
		}

		result.setAst(parselist);
		result.setErrors(errors);
		result.setWarnings(warnings);

		return result;
	}

}
