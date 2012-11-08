package eu.compassresearch.ide.cml.ui.editor.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Point;

import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlContentAssistProcessor implements IContentAssistProcessor {

    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
	    int documentOffset) {
	CmlDocument doc = (CmlDocument) viewer.getDocument();
	CmlSourceUnit csu = doc.getCmlSourceUnit();
	Point selectedRange = viewer.getSelectedRange();

	// Making sure of whats been asked
	System.out.println("Requested code completion in" + csu.toString()
		+ " at " + selectedRange.toString());

	List<ICompletionProposal> proposalList = new ArrayList<ICompletionProposal>();

	// if text was selected...
	if (selectedRange.y > 0) {
	    try {
		String selectedText = doc.get(selectedRange.x, selectedRange.y);

		computeSelectedTextProposals(selectedText, selectedRange,
			proposalList);

	    } catch (BadLocationException e) {
		// TODO add pretty error string
		e.printStackTrace();
	    }
	} else {
	    // find qualifier
	    String qualifier = getQualifier(doc, documentOffset);
	    computeQualifiedProposals(qualifier, documentOffset, csu,
		    proposalList);
	}

	ICompletionProposal[] r = new ICompletionProposal[proposalList.size()];
	proposalList.toArray(r);
	return r;
    }

    private void computeSelectedTextProposals(String selectedText,
	    Point selectedRange, List<ICompletionProposal> proposalList) {
	// TODO Auto-generated method stub

    }

    private void computeQualifiedProposals(String qualifier,
	    int documentOffset, CmlSourceUnit csu,
	    List<ICompletionProposal> proposalList) {
	
	int qlen = qualifier.length();
	
	for (SParagraphDefinition spd : csu.getSourceAst().getParagraphs()) {
	    if (spd.getName() != null)
	    if ((spd.getName().name).toLowerCase().startsWith(qualifier.toLowerCase())) {
		int cursor = spd.getName().name.length();
		CompletionProposal proposal = new CompletionProposal(
			spd.getName().name, documentOffset-qlen,qlen,cursor);
		proposalList.add(proposal);
	    }
	}
	System.out.println("Qualifier is " + qualifier);

    }

    
    
    // TODO refactor this method. It's a mess
    private String getQualifier(CmlDocument doc, int documentOffset) {
	StringBuffer buff = new StringBuffer();
	try {
	    if (Character.isWhitespace(doc.getChar(documentOffset))
		    && Character.isWhitespace(doc.getChar(documentOffset - 1)))
		return "";
	    while (true) {
		// Read backwards
		char c = doc.getChar(--documentOffset);
		if (Character.isWhitespace(c))
		    return buff.reverse().toString();
		buff.append(c);
	    }

	} catch (BadLocationException e) {
	    // at start, found nothing. quit
	    return "";
	}
    }

    @Override
    public IContextInformation[] computeContextInformation(ITextViewer viewer,
	    int documentOffset) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {
	return new char[] { '.' };
    }

    @Override
    public char[] getContextInformationAutoActivationCharacters() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public IContextInformationValidator getContextInformationValidator() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getErrorMessage() {
	// TODO Auto-generated method stub
	return null;
    }

}
