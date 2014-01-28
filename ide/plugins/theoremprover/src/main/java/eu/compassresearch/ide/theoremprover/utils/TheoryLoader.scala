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

object TheoryLoader {

  // Add a new theory and link it an appropriate IDocument
  def addTheory(name: String, dir: String, session: Session, file: IFile) {

    val node = Document.Node.Name(name, dir, name)
    
    val provider = new TextFileDocumentProvider();
    provider.connect(file); // file is of IFile
    val document = provider.getDocument(file);
   
    val model = new EditDocumentModel(session, document, node)
    
     model.init()
     // dispose immediately after initialisation
     model.dispose()
    
     provider.disconnect(file);

  }
  
  

}