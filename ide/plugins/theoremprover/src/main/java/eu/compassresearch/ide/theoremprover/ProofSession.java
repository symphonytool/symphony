package eu.compassresearch.ide.theoremprover;

import isabelle.Session;
import isabelle.eclipse.core.text.EditDocumentModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligation;

import eu.compassresearch.core.analysis.theoremprover.visitors.TPVisitor;

public class ProofSession {
	
	
	public ProofSession(EditDocumentModel poEDM, List<INode> ast,
			IDocumentProvider thyProvider, Session sess) {
		super();
		this.poEDM = poEDM;
		this.ast = ast;
		this.thyProvider = thyProvider;
		this.mapping = new HashMap<>();
	    this.tpListener = new TPListener(sess, null); // FIXME: What is IpoStatusChanged?
	}

	EditDocumentModel poEDM;
	List<INode> ast;
	IDocumentProvider thyProvider;
	TPListener tpListener;
	Map<Integer, IProofObligation> mapping;
	
	public IDocumentProvider getThyProvider() {
		return thyProvider;
	}
	public void setThyProvider(TextFileDocumentProvider thyProvider) {
		this.thyProvider = thyProvider;
	}
	public EditDocumentModel getPoEDM() {
		return poEDM;
	}
	public void setPoEDM(EditDocumentModel poEDM) {
		this.poEDM = poEDM;
	}
	public List<INode> getAst() {
		return ast;
	}
	public void setAst(List<INode> ast) {
		this.ast = ast;
	}
	
	
	
	public void enqueuePO(IProofObligation ipo) throws BadLocationException, CoreException{
		String isaPO = TPVisitor.generatePoStr(ast, ipo);
		
		IDocument doc = poEDM.document();
		int offset = doc.getLineOffset(doc.getNumberOfLines()-1);
		doc.replace(offset, 0, isaPO + "\n");
		
		
		// tpListener.cmdAt(, offset)
		int byPos = doc.getLineOffset(doc.getNumberOfLines() - TPConstants.BY_CML_AUTO_TAC_OFFSET);
		
		mapping.put(byPos, ipo);

	}
	
	
	

}
