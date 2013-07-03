package eu.compassresearch.ide.cml.ui.editor.core;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.MismatchedTokenException;
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

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.CmlTCUtil;

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
		AFileSource sourceFile = new AFileSource(null,null,file.getFile().getName(),file.getSystemFile());

		CmlLexer lexer = null;

		List<VDMError> errors = new Vector<VDMError>();
		List<VDMWarning> warnings = new Vector<VDMWarning>();
		try
		{

			ByteArrayInputStream is = new ByteArrayInputStream(source.getBytes(charset));
			ANTLRInputStream in = null;
			in = new ANTLRInputStream(is);
			lexer = new CmlLexer(in);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			CmlParser parser = new CmlParser(tokens);
			//FIXME Figure out the correct path for sourceFileNAME!
	//		file.getFile().getProjectRelativePath()
			parser.sourceFileName=file.getFile().getLocation().toOSString();

			try
			{
				List<PDefinition> paragraphs = parser.source();
				List<PDefinition> notNullParagraphs = new LinkedList<PDefinition>();
				for (PDefinition par : paragraphs)
				{
					if (par != null)
					{
						notNullParagraphs.add(par);
					} else
					{
						// setProblem(file.createMarker(IMarker.PROBLEM), "Parser gave back a null paragraph.", 1, 1);
						errors.add(getErrorMessage(file, "Parser gave back a null paragraph.", 1));
					}
				}
				sourceFile.setParagraphs(notNullParagraphs);
				// return result; //Don't return here
			} catch (RecognitionException e)
			{
				String expectedToken = "";
				CommonToken ct = null;
				if (e instanceof MismatchedTokenException)
				{
					ct = (CommonToken) e.token;
					MismatchedTokenException ee = (MismatchedTokenException) e;
					if (ee.expecting < CmlParser.tokenNames.length
							&& ee.expecting >= 0)
					{
						expectedToken = CmlParser.tokenNames[ee.expecting];
					} else
					{
						expectedToken = "out of range";
					}
					LexLocation location = new LexLocation(file.getSystemFile(), "", ct.getLine(), ct.getCharPositionInLine(), ct.getLine(), ct.getCharPositionInLine(), ct.getStartIndex(), ct.getStopIndex());
					VDMError error = new VDMError(0, "Syntax error, expecting '"
							+ expectedToken + "' near '" + ct.getText() + "'.", location);
					errors.add(error);
				} else
				{

					if (e.token != null)
					{
						ct = (CommonToken) e.token;
						errors.add(getErrorMessage(file, "Syntax error near '"
								+ ct.getText() + "'.", ct));
					} else
					{
						errors.add(getErrorMessage(file, "Syntax error, expecting at line"
								+ e.line + ".", ct));
					}
				}
			}

		} catch (Exception e1)
		{

			String msg = CmlTCUtil.getErrorMessages((RuntimeException) e1);
			// setProblem(file.createMarker(IMarker.PROBLEM), msg, Math.max(lexer.getLine(), 1));
			errors.add(getErrorMessage(file, msg, lexer.getLine()));
			// return false;
		}
		List<INode> nodes = new Vector<INode>();
		nodes.add(sourceFile);
		result.setAst(nodes);
		result.setErrors(errors);
		result.setWarnings(warnings);
		//result.setAllLocation(new Vector<LexLocation>());
		//result.setLocationToAstNodeMap(new HashMap<LexLocation, INode>());

		return result;
	}

	private VDMError getErrorMessage(IVdmSourceUnit unit, String msg, int line)
	{
		LexLocation location = new LexLocation(unit.getSystemFile(), "", line, 0, line, 0, 0, 0);
		VDMError error = new VDMError(0, msg, location);
		return error;
	}

	private static VDMError getErrorMessage(IVdmSourceUnit unit, String msg,
			CommonToken ct)
	{
		LexLocation location = new LexLocation(unit.getSystemFile(), "", ct.getLine(), ct.getCharPositionInLine(), ct.getLine(), ct.getCharPositionInLine(), ct.getStartIndex(), ct.getStopIndex());
		VDMError error = new VDMError(0, msg, location);
		return error;
	}

}
