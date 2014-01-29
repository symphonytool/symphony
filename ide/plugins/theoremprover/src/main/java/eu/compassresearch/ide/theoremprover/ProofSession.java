package eu.compassresearch.ide.theoremprover;

import isabelle.eclipse.core.text.EditDocumentModel;

import java.util.List;

import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.overture.ast.node.INode;

public class ProofSession {
	
	
	
	
	public ProofSession(EditDocumentModel poEDM, List<INode> ast,
			TextFileDocumentProvider thyProvider) {
		super();
		this.poEDM = poEDM;
		this.ast = ast;
		this.thyProvider = thyProvider;
	}

	EditDocumentModel poEDM;
	List<INode> ast;
	TextFileDocumentProvider thyProvider;
	
	
	
	public TextFileDocumentProvider getThyProvider() {
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
	
	

}
