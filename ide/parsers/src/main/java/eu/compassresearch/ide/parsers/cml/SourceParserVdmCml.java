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
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.node.INode;
import org.overture.ide.core.parser.AbstractParserParticipant;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overturetool.util.definitions.ClassList;
import org.overturetool.vdmj.lex.LexLocation;
import org.overturetool.vdmj.messages.InternalException;
import org.overturetool.vdmj.messages.VDMError;

import eu.compassresearch.core.lexer.CmlLexer;
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
		file.setType(IVdmSourceUnit.VDM_CLASS_SPEC);

		ClassList classes = new ClassList();
		classes.clear();

		ParseResult result = new ParseResult();
		CmlLexer scanner = null;
		CmlParser cmlParser = null;
		try {
			
			
			InputStream is = new ByteArrayInputStream( source.getBytes(  ) ); 
			
			scanner = new CmlLexer(is);
			 cmlParser = new CmlParser(scanner);
			// cmlParser.setDebugLevel(1);

			// do {
			// System.out.println(scanner.yylex());
			if (cmlParser.parse()) {
				System.out.println("parsed!");
				System.out.println(cmlParser.getDocument());
				
				for (PDefinition def : cmlParser.getDocument()) {
					if(def instanceof SClassDefinition)
					{
						classes.add((SClassDefinition) def);
					}
				}
				
				List<INode> nodes = new Vector<INode>();
				for (SClassDefinition classDefinition : classes) {
					nodes.add(classDefinition);
				}
				if (nodes.size() > 0) {
					result.setAst(nodes);
				} else {
					result.setFatalError(new Exception("No VDM source in file"));
				}
				
				
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
		
		if(scanner != null && scanner.errors>0)
		{
			List<VDMError> errors = new Vector<VDMError>();
			for (ParserError error : scanner.parseErrors) {
				errors.add(new VDMError(error.line, error.message, new LexLocation(error.file, "", error.line+1, error.col, error.line, error.col, 1, 1)));
			}
			result.setErrors(errors);
		}

		// if (reader != null && reader.getErrorCount() > 0) {
		//
		// result.setErrors(reader.getErrors());
		// }
		//
		// if (reader != null && reader.getWarningCount() > 0) {
		// result.setWarnings(reader.getWarnings());
		// }
		//
		for (SClassDefinition classDefinition : classes) {
			classDefinition.getDefinitions();
		}

		result.setAllLocation(new Vector<LexLocation>());
		 result.setLocationToAstNodeMap(new Hashtable<LexLocation, INode>());

		return result;
	}
}
