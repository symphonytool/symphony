package eu.compassresearch.ide.cml.ui.builder;


import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.overture.ast.definitions.PDefinition;

import org.antlr.runtime.MismatchedTokenException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.lexer.ParserError;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
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
		boolean parseResult = parse(file, source);


		// Set the AST on the source unit
		CmlSourceUnit dom = CmlSourceUnit.getFromFileResource(file);
		if (parseResult)
			dom.setSourceAst(source, new LinkedList<ParserError>(), parseResult);
		else
			dom.setSourceAst(emptySource(source.getName()+""), new LinkedList<ParserError>(), parseResult);
		return false;
	}


	private PSource emptySource(String name) {
		AFileSource s = new AFileSource(new LinkedList<PDefinition>(), name);
		return s;
	}


	/**
	 * Set the error start, end takes 
	 * 
	 * 
	 * @param marker
	 * @param text
	 * @param line
	 * @param start
	 * @param end
	 * @throws CoreException
	 */
	private static void setProblem(IMarker marker, String text, int line,  int... more)
			throws CoreException {
		marker.setAttribute(IMarker.MESSAGE, text);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		if (more.length < 2)
		{
			marker.setAttribute(IMarker.LINE_NUMBER, line);
		}
		else
		{
			
			marker.setAttribute(IMarker.CHAR_START, more[0]);
			marker.setAttribute(IMarker.CHAR_END, more[1]);

		}
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
				List<PDefinition> paragraphs = parser.source();
				List<PDefinition> notNullParagraphs = new LinkedList<PDefinition>();
				for( PDefinition par : paragraphs)
					if (par != null)
						notNullParagraphs.add(par);
					else
						setProblem(file.createMarker(IMarker.PROBLEM), "Parser gave back a null paragraph.", 1,1);
				
				source.setParagraphs(notNullParagraphs);
				return true;
			} 
			catch(RecognitionException e)
			{
				String expectedToken = "";
				CommonToken ct = null; 
				if (e instanceof MismatchedTokenException)
				{
					ct = (CommonToken)e.token;
					MismatchedTokenException ee = (MismatchedTokenException)e;
					expectedToken= CmlParser.tokenNames[ee.expecting];
					setProblem(file.createMarker(IMarker.PROBLEM), "Syntax error, expecting '"+expectedToken+"' near '"+ct.getText()+"'.",ct.getStartIndex(), ct.getStopIndex());
				}
				
				if (e.token != null)
				{
					ct = (CommonToken)e.token;
					setProblem(file.createMarker(IMarker.PROBLEM), "Syntax error near '"+ct.getText()+"'.",ct.getStartIndex(), ct.getStopIndex());	
				}
				else
					setProblem(file.createMarker(IMarker.PROBLEM), "Syntax error, expecting at line"+e.line+".", e.line);
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
