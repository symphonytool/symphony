package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.PDefinition;
import org.overture.typechecker.TypeCheckException;

import eu.compassresearch.core.parser.CmlParserError;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

public class TestUtil
{
	public static class TypeCheckerResult
	{
		public ITypeIssueHandler issueHandler;
		public boolean parsedOk;
		public boolean tcOk;
		public List<String> parseErrors = new Vector<String>();
		public List<PDefinition> sources;
	}

	public static TypeCheckerResult runTypeChecker(String... fileNames)
			throws IOException
	{
		TypeCheckerResult res = new TypeCheckerResult();

		List<File> files = new Vector<File>();
		for (String f : fileNames)
		{
			files.add(new File(f));
		}

		ParserResult parserRes = ParserUtil.parse(files);

		res.parsedOk = parserRes.errors.isEmpty();
		for (CmlParserError err : parserRes.errors)
		{
			res.parseErrors.add(err.toString());
		}

		if (res.parsedOk)
		{
			ITypeIssueHandler issueHandler = new CollectingIssueHandler();
			res.issueHandler = issueHandler;
			ICmlTypeChecker checker = new VanillaCmlTypeChecker(parserRes.definitions, issueHandler);

			try
			{
				res.tcOk = checker.typeCheck();
			} catch (TypeCheckException e)
			{

			}catch(org.overture.ast.messages.InternalException e)
			{
				//do not record too many errors
			}catch(eu.compassresearch.ast.messages.InternalException e)
			{
				//do not record too many errors
			}
		}
		res.sources = parserRes.definitions;

		return res;
	}

}
