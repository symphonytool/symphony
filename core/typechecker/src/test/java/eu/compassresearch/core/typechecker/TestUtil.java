package eu.compassresearch.core.typechecker;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.accessibility.AccessibleStreamable;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;


import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;

public class TestUtil
{

	public static class TypeCheckerResult
	{
		TypeIssueHandler issueHandler;
		boolean parsedOk;
		boolean tcOk;
		PSource source;
		List<String> parseErrors;
	}


	public static TypeCheckerResult parse(PSource s) throws FileNotFoundException, IOException
	{
		
		TypeCheckerResult result = new TypeCheckerResult();
		
		ANTLRInputStream in = null;
		if (s instanceof AFileSource)
			in = new ANTLRInputStream(new FileInputStream(((AFileSource)s).getFile()));

		if (s instanceof AInputStreamSource)
			in = new ANTLRInputStream(((AInputStreamSource)s).getStream());

		if (in == null)
			return result;

		CmlLexer lexer = new CmlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);

		try {
			s.setParagraphs(new LinkedList<PDefinition>());
			for(PDefinition d : parser.source())
			{
				if (d != null)
					s.getParagraphs().add(d);
				else	
				{
				}
			}
			result.parsedOk = true;
			return result;
		} catch (RecognitionException e) {
			String expectedToken = "";
			CommonToken ct = null;
			List<String> parseErrors = new LinkedList<String>();
			result.parseErrors = parseErrors;
			if (e instanceof MismatchedTokenException)
			{
				ct = (CommonToken)e.token;
				MismatchedTokenException ee = (MismatchedTokenException)e;
				expectedToken= CmlParser.tokenNames[ee.expecting];
				parseErrors.add("Syntax error in "+s+" expecting '"+expectedToken+"' near '"+ct.getText()+"' at line "+e.line+" - "+ct.getStartIndex()+":"+ ct.getStopIndex());
				return result;
			}
			
			if (e.token != null)
			{
				ct = (org.antlr.runtime.CommonToken)e.token;
				parseErrors.add("Syntax error in "+s+" snear '"+ct.getText()+"'. Error at line "+e.line + " - "+ct.getStartIndex()+":"+ ct.getStopIndex());	
			}
			else
				parseErrors.add("Syntax error, expecting at line at line "+e.line+".");
			return result;
		}
	}

	public static TypeCheckerResult runTypeChecker(PSource s) throws FileNotFoundException, IOException
	{
		TypeCheckerResult res = new TypeCheckerResult();

		ANTLRInputStream in = null;
		if (s instanceof AFileSource)
			in = new ANTLRInputStream(new FileInputStream(((AFileSource)s).getFile()));

		if (s instanceof AInputStreamSource)
			in = new ANTLRInputStream(((AInputStreamSource)s).getStream());

		CmlLexer lexer = new CmlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);


		try {
			List<PDefinition> forest = parser.source();
			s.setParagraphs(new LinkedList<PDefinition>());
			if (forest != null)
			{
				for (PDefinition def : forest)
					if (def != null)
						s.getParagraphs().add(def);
				res.parsedOk = true;
			}
			else
				res.parsedOk = false;
		} catch (RecognitionException e) {
			e.printStackTrace();
			res.parsedOk=false;
		}

		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		res.issueHandler=issueHandler;
		List<PSource> cmlSources = new LinkedList<PSource>();
		cmlSources.add(s);
		CmlTypeChecker checker = VanillaFactory.newTypeChecker(cmlSources , issueHandler);

		res.tcOk = checker.typeCheck();
		res.source = s;

		return res;
	}


	public static TypeCheckerResult runTypeChecker(String file) throws IOException
	{
		TypeCheckerResult res = new TypeCheckerResult();

		AFileSource fileSource = new AFileSource();

		fileSource.setFile(new File(file));

		List<PSource> cmlSources = Arrays.asList(new PSource[] { fileSource });

		ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(fileSource.getFile()));
		CmlLexer lexer = new CmlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);


		try {
			fileSource.setParagraphs(parser.source());
			res.parsedOk = true;
		} catch (RecognitionException e) {
			e.printStackTrace();
			res.parsedOk=false;
		}

		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		res.issueHandler=issueHandler;
		CmlTypeChecker checker = VanillaFactory.newTypeChecker(cmlSources, issueHandler);

		res.tcOk = checker.typeCheck();
		res.source = fileSource;

		return res;
	}

	public static String buildErrorMessage(TypeIssueHandler tc, boolean expectedTypesOk) {
		StringBuilder sb = new StringBuilder();
		if (expectedTypesOk) {
			sb.append("Expected type checking to be successful, the following errors were unexpected:\n");
			for (CMLTypeError error : tc.getTypeErrors())
				sb.append(error.toString() + "\n------\n");
			if (tc.getTypeErrors().size() > 0)
				System.out.println(tc.getTypeErrors().get(0).getStackTrace());
		} else {
			sb.append("Expected type checking to fail but it was successful.");
		}
		return sb.toString();
	}


	public static <T> void addTestProgram(List<Object[]> col, String src,
			Object... objs)
	{
		Object[] a = new Object[objs.length + 1];
		a[0] = src;
		System.arraycopy(objs, 0, a, 1, objs.length);
		col.add(a);
	}

	@SuppressWarnings("serial")
	public static Object findFirst(final Class<?> c, PSource s)
			throws AnalysisException
			{
		class Holder
		{
			Object pointer;
		}
		final Holder h = new Holder();

		DepthFirstAnalysisCMLAdaptor d = new DepthFirstAnalysisCMLAdaptor()
		{

			@Override
			public void defaultInINode(org.overture.ast.node.INode node)
					throws AnalysisException
					{
				if (node.getClass().equals(c))
				{
					h.pointer = node;
					return;
				}
				super.defaultInINode(node);
					}

		};
		s.apply(d);
		return h.pointer;
			}

	public static <T> void addFileProgram(List<Object[]> col, String filename,
			Object... objs) throws IOException
			{
		String progDir = "../../docs/cml-examples/";
		File fin = new File(progDir + filename);
		InputStream is = new FileInputStream(fin);
		byte[] buffer = new byte[is.available()];
		is.read(buffer);
		is.close();
		Object[] args = new Object[objs.length + 1];
		args[0] = new String(buffer);
		System.arraycopy(objs, 0, args, 1, objs.length);
		col.add(args);
			}

	public static PSource makeSource(String cmlSource, String... names)
	{
		InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
		AInputStreamSource source = new AInputStreamSource();
		source.setOrigin(names != null && names.length > 0 ? names[0] : "Test Parameter");
		source.setStream(cmlSourceIn);
		return source;
	}

}
