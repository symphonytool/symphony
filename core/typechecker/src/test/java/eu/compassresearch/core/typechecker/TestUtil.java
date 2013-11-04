package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.parser.CmlParserError;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.CollectingIssueHandler;
import eu.compassresearch.core.typechecker.VanillaCmlTypeChecker;
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
		public List<PSource> sources;
	}

	public static TypeCheckerResult runTypeChecker(String... files)
			throws IOException
	{
		TypeCheckerResult res = new TypeCheckerResult();

		List<PSource> sources = new Vector<PSource>();

		for (String file : files)
		{
			AFileSource fileSource = new AFileSource();

			fileSource.setFile(new File(file));
			fileSource.setName(file);

			ParserResult parserResult = ParserUtil.parse(new File(file));
			
			for (CmlParserError err : parserResult.errors)
			{
				res.parseErrors.add(err.toString());
			}
			fileSource.setParagraphs(parserResult.definitions);
			
			res.parsedOk = res.parseErrors.isEmpty();

			sources.add(fileSource);
		}

		if (res.parsedOk)
		{
			ITypeIssueHandler issueHandler = new CollectingIssueHandler();
			res.issueHandler = issueHandler;
			ICmlTypeChecker checker = new VanillaCmlTypeChecker(sources, issueHandler);

			res.tcOk = checker.typeCheck();
		}
		res.sources = sources;

		return res;
	}

//	static List<String> parse(AFileSource... files)
//	{
//		// boolean ok = true;
//		List<String> errors = new Vector<String>();
//		for (AFileSource fileSource : files)
//		{
//			CmlLexer lexer = null;
//			CmlParser parser = null;
//			try
//			{
//				ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(fileSource.getFile()));
//				lexer = new CmlLexer(in);
//				lexer.sourceFileName = fileSource.getFile().getName();
//				CommonTokenStream tokens = new CommonTokenStream(lexer);
//				parser = new CmlParser(tokens);
//				parser.sourceFileName = lexer.sourceFileName;
//				fileSource.setParagraphs(parser.source());
//
//				// ok &= true;
//			} catch (Exception e)
//			{
//				// e.printStackTrace();
//				// ok &= false;
//			}
//
//			for (CmlParserError string : lexer.getErrors())
//			{
//				errors.add(string.toString());
//			}
//			for (CmlParserError string : parser.getErrors())
//			{
//				errors.add(string.toString());
//			}
//		}
//
//		return errors;
//
//	}
}
