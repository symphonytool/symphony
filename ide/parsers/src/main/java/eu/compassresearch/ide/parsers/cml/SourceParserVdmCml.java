/*******************************************************************************
 * Copyright (c) 2009, 2011 Overture Team and others.
 *
 * Overture is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Overture is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Overture.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The Overture Tool web-site: http://overturetool.org/
 *******************************************************************************/
package eu.compassresearch.ide.parsers.cml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.messages.InternalException;
import org.overture.ast.node.INode;
import org.overture.ast.program.ASourcefileSourcefile;
import org.overture.ide.core.parser.AbstractParserParticipant;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.parser.messages.VDMError;

//import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.lexer.ParserError;
import eu.compassresearch.core.parser.CmlParser;

public class SourceParserVdmCml extends AbstractParserParticipant {

	@Override
	protected ParseResult startParse(IVdmSourceUnit file, String source,
			String charset) {
		return startParseFile(file, source, charset);
	}

	protected ParseResult startParseFile(IVdmSourceUnit file, String source,
			String charset) {
		/*
		file.setType(IVdmSourceUnit.VDM_CLASS_SPEC);

		ParseResult result = new ParseResult();
		List<PDeclaration> declarations = new LinkedList<PDeclaration>();
	//	CmlLexer scanner = null;
		CmlParser cmlParser = null;
		try {
			
			
			InputStream is = new ByteArrayInputStream( source.getBytes(  ) ); 
			
		//	scanner = new CmlLexer(is);
			// cmlParser = new CmlParser(scanner);
			// cmlParser.setDebugLevel(1);

			// do {
			// System.out.println(scanner.yylex());
			if (cmlParser.parse()) {
				System.out.println("parsed!");
				ASourcefileSourcefile document = cmlParser.getDocument();
				
				
				for (PDeclaration decl : document.getDecls()) {
					declarations.add(decl);
				}
				
				List<INode> nodes = new Vector<INode>();
				for(PDeclaration d : declarations)
					if (d instanceof INode)
						nodes.add((INode)d);

//				if (nodes.size() > 0) {
//					result.setAst(nodes);
//				} else {
//					result.setFatalError(new Exception("No CML source in file"));
//				}
				
				
			} else {
				System.out.println("Not parsed!");

				// } while (!scanner.zzAtEOF);

			}

			// ReaderType streamReaderType =
			// findStreamReaderType(file.getFile());

			// LexTokenReader ltr = new LexTokenReader(source, Settings.dialect,
			// file.getSystemFile(), charset, streamReaderType);

			// classes.addAll(reader.readClasses());
			

		} catch (InternalException e) {

			result.setFatalError(e);
		} catch (Throwable e) {
			e.printStackTrace();
			result.setFatalError(e);
		}
		/*
		if(scanner != null && scanner.errors>0)
		{
			List<VDMError> errors = new Vector<VDMError>();
			for (ParserError error : scanner.parseErrors) {
				errors.add(new VDMError(error.line, error.message, new LexLocation(error.file, "", error.line+1, error.col, error.line, error.col,0,0)));
			}
//			result.setErrors(errors);
		}
*/
		// if (reader != null && reader.getErrorCount() > 0) {
		//
		// result.setErrors(reader.getErrors());
		// }
		//
		// if (reader != null && reader.getWarningCount() > 0) {
		// result.setWarnings(reader.getWarnings());
		// }
		//

//		result.setAllLocation(new Vector<LexLocation>());
//		 result.setLocationToAstNodeMap(new HashMap<LexLocation, INode>());

		return new ParseResult();
	}
}
