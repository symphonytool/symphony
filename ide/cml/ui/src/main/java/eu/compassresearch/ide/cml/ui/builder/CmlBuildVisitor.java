package eu.compassresearch.ide.cml.ui.builder;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.overture.ast.lex.LexLocation;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.lexer.ParserError;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlBuildVisitor implements IResourceVisitor {

	public boolean visit(IResource resource) throws CoreException {

		// Resource for this build
		if (!shouldBuild(resource))
			return true;

		// This visitor only builds files.
		IFile file = (IFile) resource;

		// Parse the source
		AFileSource source = new AFileSource();
		if (!parse(file, source))
			return false;

		// Lets run the type checker
		if (!typeCheck(file, source))
			return false;

		// Set the AST on the source unit
		CmlSourceUnit dom = CmlSourceUnit.getFromFileResource(file);
		dom.setSourceAst(source, new LinkedList<ParserError>());
		return false;
	}

	/*
	 * Run the type checker.
	 */
	private synchronized static boolean typeCheck(IFile file, AFileSource source)
			throws CoreException {
		try {

			List<PSource> cmlSources = new LinkedList<PSource>();
			cmlSources.add(source);
			TypeIssueHandler issueHandler = VanillaFactory
					.newCollectingIssueHandle();
			CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(cmlSources,
					issueHandler);
			boolean tcSuccess = cmlTC.typeCheck();
			if (!tcSuccess) {

				List<CMLTypeError> tcerrors = issueHandler.getTypeErrors();
				for (CMLTypeError tcError : tcerrors) {
					LexLocation loc = tcError.getLocation();
					IMarker marker = file.createMarker(IMarker.PROBLEM);
					setProblem(marker, tcError.getDescription(), loc.startLine);
				}
			}
			return true;
		} catch (Exception tcException) {
			tcException.printStackTrace();
			IMarker tcMarker = file.createMarker(IMarker.PROBLEM);
			setExceptionInfo(
					tcMarker,
					tcException,
					"An exception occurred while type checking file \""
							+ file.getName() + "\".");

		}
		return true;
	}

	private static void setProblem(IMarker marker, String text, int line)
			throws CoreException {
		marker.setAttribute(IMarker.MESSAGE, text);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		marker.setAttribute(IMarker.LINE_NUMBER, line);
	}

	private static void setInfo(IMarker marker, String shortText, String text)
			throws CoreException {
		marker.setAttribute(IMarker.MESSAGE, shortText + "\n" + text);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
		marker.setAttribute(IMarker.TEXT, text);
	}

	private static void setExceptionInfo(IMarker marker, Exception e,
			String shortText) throws CoreException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(baos);
		e.printStackTrace(pw);
		pw.flush();
		pw.close();
		setInfo(marker, shortText, new String(baos.toByteArray()));
	}

	/*
	 * Run the parser and lexer on the file-resource
	 */
	private static boolean parse(IFile file, AFileSource source)
			throws CoreException {
		// Create parser and lexer to handle the given cml source
		String localPathToFile = file.getLocation().toString();
		source.setFile(new File(localPathToFile));

		// Clear markers for this file
		file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		CmlLexer lexer = null;
		try {
			ANTLRInputStream in = null;
			in = new ANTLRInputStream(new FileInputStream(source.getFile()));
			lexer = new CmlLexer(in);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			CmlParser parser = new CmlParser(tokens);

			try {
				source.setParagraphs(parser.source());
				return true;
			} catch (RecognitionException e) {
				String errorHeader = parser.getErrorHeader(e);
				if (errorHeader == null) errorHeader = e.getMessage();
				setProblem(file.createMarker(IMarker.PROBLEM), errorHeader, lexer.getLine());
				return false;
			}

		} catch (Exception e1) {
			setProblem(file.createMarker(IMarker.PROBLEM),e1.getMessage(),lexer.getLine());
			return false;
		}
	}

	/*
	 * Return true of this build visitor should continue to build the given
	 * resource.
	 */
	private static boolean shouldBuild(IResource resource) {
		return resource instanceof IFile
				&& "cml".equalsIgnoreCase(((IFile) resource).getFileExtension());
	}
}
