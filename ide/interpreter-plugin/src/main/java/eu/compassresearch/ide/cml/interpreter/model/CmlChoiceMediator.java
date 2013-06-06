package eu.compassresearch.ide.cml.interpreter.model;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.core.interpreter.debug.Choice;
import eu.compassresearch.core.interpreter.utility.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.utility.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.utility.messaging.ResponseMessage;
import eu.compassresearch.ide.cml.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.cml.interpreter.views.CmlEventOptionView;

public class CmlChoiceMediator implements IDoubleClickListener, ISelectionChangedListener{

//	SynchronousQueue<String> selectSync = new SynchronousQueue<String>();
	final CmlDebugTarget cmlDebugTarget;
	RequestMessage requestMessage = null;
	
	public CmlChoiceMediator(CmlDebugTarget cmlDebugTarget)
	{
		this.cmlDebugTarget = cmlDebugTarget;
		
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					CmlEventOptionView view = (CmlEventOptionView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_OPTION_VIEW.toString());
					view.getListViewer().addDoubleClickListener(CmlChoiceMediator.this);
					view.getListViewer().addSelectionChangedListener(CmlChoiceMediator.this);
					CmlChoiceMediator.this.cmlDebugTarget.resume();
					
				} catch (PartInitException e) {
					e.printStackTrace();
				}
				catch (CoreException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void finish()
	{
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					CmlEventOptionView view = (CmlEventOptionView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_OPTION_VIEW.toString());
					view.getListViewer().removeDoubleClickListener(CmlChoiceMediator.this);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	public void setChoiceOptions(final List<Choice> events, RequestMessage requestMessage)
	{
		this.requestMessage = requestMessage; 
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					CmlEventOptionView view = (CmlEventOptionView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_OPTION_VIEW.toString());
					view.getListViewer().setInput(events);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	public void selectChoice(Choice event)
	{
		this.cmlDebugTarget.sendMessage(new ResponseMessage( requestMessage.getRequestId(),CmlRequest.CHOICE,event));
		setChoiceOptions(null,null);
		finish();
	}
		
	@Override
	public void doubleClick(DoubleClickEvent event) {
		
		IStructuredSelection selection = null;
		Choice choice = null;
		
		if(event.getSelection() instanceof IStructuredSelection)
		{
			selection = (IStructuredSelection)event.getSelection();
			choice = (Choice)selection.getFirstElement();
		}
		
		selectChoice(choice);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		
		if(event.getSelection() instanceof IStructuredSelection)
		{
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			
//			CmlEditor cmlEditor = (CmlEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//			//cmlEditor.resetHighlightRange();
//			//cmlEditor.setHighlightRange(10, 4, true);
//			
//			int line = 0;
//			int lenght = 0;
//			try {
//				CmlStackFrame stackFrame = (CmlStackFrame)cmlDebugTarget.getThreads()[0].getTopStackFrame();
//				ILexLocation location = stackFrame.getLocation();
//				line = location.getStartOffset();
//				lenght = location.getEndOffset() - location.getStartOffset() + 1;
//			} catch (DebugException e) {
//				e.printStackTrace();
//			}
//			
//			//cmlEditor.selectAndReveal(line, lenght);
//			cmlEditor.setHighlightRange(line, lenght, true);
		}
		
		
		//cmlEditor.selectAndReveal(0, 50);
	}
}
