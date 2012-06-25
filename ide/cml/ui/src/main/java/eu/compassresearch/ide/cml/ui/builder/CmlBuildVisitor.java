package eu.compassresearch.ide.cml.ui.builder;

import java.io.FileReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.overture.ast.program.ASourcefileSourcefile;
import org.overture.ast.util.ClonableFile;

import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlBuildVisitor implements IResourceVisitor {

	
	
	
	@Override
	public boolean visit(IResource resource) throws CoreException {

		// We want to resource to be a file with the cml extension 
		if (resource instanceof IFile)
		{
			IFile file = (IFile)resource;
			if ("cml".equalsIgnoreCase(file.getFileExtension()))
			{
				System.out.println("Building CML Source-file: "+file.getName());
				try {
					// Look up DOM object
					CmlSourceUnit dom = CmlSourceUnit.getFromFileResource(file);
					
					// Create parser and lexer to handle the given cml source
					String localPathToFile = file.getLocation().toString();
					ASourcefileSourcefile source = new ASourcefileSourcefile();

					CmlLexer lexer = new CmlLexer(new FileReader(localPathToFile));
					CmlParser parser = new CmlParser(lexer);
					parser.setDocument(source);
					
					// Parse the document
					if (!parser.parse())
						System.out.println("Parsing file: "+file.getName()+ " failed.");
				
					
					// Set the build source on the dom object
					ClonableFile f = new ClonableFile(localPathToFile);
					source.setFile(f);
					dom.setSourceAst(source);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
				System.out.println("Extension: "+file.getFileExtension()+" not cml file but other resource, skipped by build.");
			// A source file is the bottom
			return false;

		}
		// Okay it was not a cml fil
		return true;
	}


}
