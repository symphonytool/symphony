package eu.compassresearch.ide.cml.ui.editor.core;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class CmlAssistanceProcessor implements IContentAssistProcessor
  {
    
    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer arg0,
        int arg1)
      {
        // TODO Auto-generated method stub
        return null;
      }
    
    @Override
    public IContextInformation[] computeContextInformation(ITextViewer arg0,
        int arg1)
      {
        // TODO Auto-generated method stub
        return null;
      }
    
    @Override
    public char[] getCompletionProposalAutoActivationCharacters()
      {
        // TODO Auto-generated method stub
        return null;
      }
    
    @Override
    public char[] getContextInformationAutoActivationCharacters()
      {
        // TODO Auto-generated method stub
        return null;
      }
    
    @Override
    public IContextInformationValidator getContextInformationValidator()
      {
        // TODO Auto-generated method stub
        return null;
      }
    
    @Override
    public String getErrorMessage()
      {
        // TODO Auto-generated method stub
        return null;
      }
    
  }
