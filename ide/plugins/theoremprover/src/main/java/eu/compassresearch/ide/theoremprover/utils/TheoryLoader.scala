package eu.compassresearch.ide.theoremprover.utils

import org.eclipse.core.filesystem.EFS
import isabelle.Document
import org.eclipse.jface.text.IDocument
import isabelle.Session
import isabelle.eclipse.core.resource.URIThyLoad
import isabelle.eclipse.ui.editors.EditorUtil
import isabelle.eclipse.core.text.{ DocumentModel, EditDocumentModel, IsabelleDocument, ReadOnlyDocumentModel }
import org.eclipse.core.runtime.CoreException
import org.eclipse.ui.texteditor.IDocumentProvider
import org.eclipse.ui.texteditor.AbstractTextEditor
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.editors.text.TextFileDocumentProvider
import org.eclipse.core.resources.IFile
import org.eclipse.core.runtime.NullProgressMonitor

object TheoryLoader {

  // Create an EditDocumentModel from a given thy file. Doesn't resolve dependencies.
  def addTheory(node: String, dir: String, theory: String
                , session: Session, file: IFile
                , thyProvider : IDocumentProvider) : EditDocumentModel = {

    val thyNode = Document.Node.Name(node, dir, theory)
    
    thyProvider.connect(file); // file is of IFile
    val document = thyProvider.getDocument(file);
   
    val model = new EditDocumentModel(session, document, thyNode)
    
    model.init()
    model.submitFullPerspective(new NullProgressMonitor());
    
   // provider.disconnect(file)
    
    model

  }
  

  def addReadOnlyTheory(){}
  
  
  
  
  

}