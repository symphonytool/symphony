package eu.compassresearch.ide.cml.ui.builder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ast.lex.LexLocation;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.lexer.ParserError;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.CmlTypeChecker;
import eu.compassresearch.core.typechecker.CmlTypeChecker.CMLTypeError;
import eu.compassresearch.core.typechecker.VanillaCmlTypeChecker;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlBuildVisitor implements IResourceVisitor
  {
    
    @Override
    public boolean visit(IResource resource) throws CoreException
      {
        
        // We want the resource to be a file with the cml extension
        if (resource instanceof IFile)
          {
            IFile file = (IFile) resource;
            
            if ("cml".equalsIgnoreCase(file.getFileExtension()))
              {
                System.out.println("Building CML Source-file: "
                    + file.getName());
                try
                  {
                    // Look up DOM object
                    CmlSourceUnit dom = CmlSourceUnit.getFromFileResource(file);
                    
                    // Create parser and lexer to handle the given cml source
                    String localPathToFile = file.getLocation().toString();
                    AFileSource source = new AFileSource();
                    source.setFile(new File(localPathToFile));
                    
                    CmlLexer lexer = new CmlLexer(new FileReader(
                        localPathToFile));
                    CmlParser parser = new CmlParser(lexer);
                    parser.setDocument(source);
                    
                    try
                      {
                        file.deleteMarkers(IMarker.PROBLEM, true,
                            IResource.DEPTH_INFINITE);
                        
                        // Parse the document
                        if (!parser.parse())
                          {
                            System.out.println(lexer.parseErrors.size()
                                + " errors occurred.");
                            
                            for (ParserError e : lexer.parseErrors)
                              {
                                IMarker marker = file
                                    .createMarker(IMarker.PROBLEM);
                                marker.setAttribute(IMarker.MESSAGE, e.message);
                                marker.setAttribute(IMarker.SEVERITY,
                                    IMarker.SEVERITY_ERROR);
                                marker
                                    .setAttribute(IMarker.LINE_NUMBER, e.line);
                              }
                            
                          } else
                          {
                            try
                              {
                                CmlTypeChecker cmlTC = new VanillaCmlTypeChecker(
                                    source);
                                boolean tcSuccess = cmlTC.typeCheck();
                                if (!tcSuccess)
                                  {
                                    
                                    List<CMLTypeError> tcerrors = cmlTC
                                        .getTypeErrors();
                                    for (CMLTypeError tcError : tcerrors)
                                      {
                                        LexLocation loc = tcError.getLocation();
                                        IMarker m = file
                                            .createMarker(IMarker.PROBLEM);
                                        m.setAttribute(IMarker.MESSAGE,
                                            tcError.getDescription());
                                        m.setAttribute(IMarker.SEVERITY,
                                            IMarker.SEVERITY_ERROR);
                                        m.setAttribute(IMarker.LINE_NUMBER,
                                            loc.startLine);
                                        
                                      }
                                  }
                              } catch (Exception tcException)
                              {
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                tcException.printStackTrace(new PrintStream(
                                    baos));
                                IMarker tcMarker = file
                                    .createMarker(IMarker.PROBLEM);
                                
                                tcMarker
                                    .setAttribute(
                                        IMarker.MESSAGE,
                                        "A "
                                            + tcException.getClass()
                                                .getCanonicalName()
                                            + " was thrown from the type checker.\nThe message is: "
                                            + tcException.getMessage());
                                tcMarker.setAttribute(IMarker.LINE_NUMBER, 0);
                                tcMarker.setAttribute(IMarker.TEXT, new String(
                                    baos.toByteArray()));
                              }
                          }
                      } catch (NoSuchMethodError e)
                      {
                        System.out
                            .println("Sorry, the parser gave up with a hard error.");
                        e.printStackTrace();
                      }
                    
                    // Set the build source on the dom object
                    File f = new File(localPathToFile);
                    source.setFile(f);
                    dom.setSourceAst(source, lexer.parseErrors);
                    
                  } catch (Exception e)
                  {
                    IMarker internalErrorMarker = file
                        .createMarker(IMarker.PROBLEM);
                    internalErrorMarker.setAttribute(IMarker.MESSAGE,
                        "Exception: " + e.getMessage());
                    e.printStackTrace();
                  }
              } else
              System.out.println("Extension: " + file.getFileExtension()
                  + " not cml file but other resource, skipped by build.");
            // A source file is the bottom
            return false;
            
          }
        // Okay it was not a cml fil
        return true;
      }
  }
