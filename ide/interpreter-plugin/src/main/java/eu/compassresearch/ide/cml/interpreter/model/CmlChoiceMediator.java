package eu.compassresearch.ide.cml.interpreter.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.core.interpreter.debug.Choice;
import eu.compassresearch.core.interpreter.utility.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.utility.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.utility.messaging.ResponseMessage;
import eu.compassresearch.ide.cml.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.cml.interpreter.views.CmlEventOptionView;
import eu.compassresearch.ide.cml.ui.editor.core.CmlEditor;

public class CmlChoiceMediator implements IDoubleClickListener, ISelectionChangedListener{

//	SynchronousQueue<String> selectSync = new SynchronousQueue<String>();
	final CmlDebugTarget cmlDebugTarget;
	RequestMessage requestMessage = null;
	private List<StyleRange> lastSelectedRanges = new LinkedList<StyleRange>();
	
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
					view.getListViewer().removeSelectionChangedListener(CmlChoiceMediator.this);
					CmlEditor cmlEditor = (CmlEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					StyledText styledText = (StyledText)cmlEditor.getAdapter(Control.class);
					clearSelections(styledText);
					view.getListViewer().setInput(null);
					view.getListViewer().setSelection(null);
					
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
					Collections.sort(events, new Comparator<Choice>()
							{

								@Override
								public int compare(Choice o1, Choice o2) {
									if(o1.getName().equals("tock"))
										return -1;
									else if(o2.getName().equals("tock"))
										return 1;
									else
										return o1.getName().compareToIgnoreCase(o2.getName());
								}
						
						
							});
					view.getListViewer().setInput(events);
					view.getListViewer().setSelection(new StructuredSelection(events.get(0)));
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	public void selectChoice(Choice event)
	{
		this.cmlDebugTarget.sendMessage(new ResponseMessage( requestMessage.getRequestId(),CmlRequest.CHOICE,event));
		//setChoiceOptions(null,null);
		finish();
	}
		
	@Override
	public void doubleClick(DoubleClickEvent event) {
		
		IStructuredSelection selection = null;
		Choice choice = null;
		
//		CmlEditor cmlEditor = (CmlEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		StyledText styledText = (StyledText)cmlEditor.getAdapter(Control.class);
//		clearSelections(styledText);
		
		if(event.getSelection() instanceof IStructuredSelection)
		{
			selection = (IStructuredSelection)event.getSelection();
			choice = (Choice)selection.getFirstElement();
		}
		
		selectChoice(choice);
	}
	
	private void clearSelections(StyledText styledText)
	{
		for(StyleRange sr : lastSelectedRanges)
		{
			sr.background = null;
			styledText.setStyleRange(sr);
		}
		lastSelectedRanges.clear();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		
		if(event.getSelection() instanceof IStructuredSelection)
		{
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			
			Choice choice = (Choice)selection.getFirstElement();
			
			CmlEditor cmlEditor = (CmlEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			StyledText styledText = (StyledText)cmlEditor.getAdapter(Control.class);
			
			//cmlEditor.resetHighlightRange();
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
			clearSelections(styledText);
			
			for(ILexLocation loc : choice.getLocations())
			{
				int length = loc.getEndOffset() - loc.getStartOffset() + 1;
				StyleRange sr = styledText.getStyleRangeAtOffset(loc.getStartOffset());
				sr.length = length;
				sr.background = new Color(null, new RGB(java.awt.Color.GRAY.getRed(), java.awt.Color.GRAY.getGreen(), java.awt.Color.GRAY.getBlue()));
				styledText.setStyleRange(sr);
				lastSelectedRanges.add(sr);
			}
			styledText.setCaretOffset(choice.getLocations().get(0).getStartOffset());
			styledText.showSelection();
			//cmlEditor.selectAndReveal(firstLoc.getStartOffset(), length);
			
			//styledText.setSelection(firstLoc.getStartOffset());
			//styledText.setSelection(firstLoc.getStartOffset(), firstLoc.getEndOffset()+1);
			//styledText.setSelectionBackground(new Color(null, new RGB(java.awt.Color.BLUE.getRed(), java.awt.Color.BLUE.getGreen(), java.awt.Color.BLUE.getBlue())));
			//styledText.setBackground(new Color(null, new RGB(java.awt.Color.BLUE.getRed(), java.awt.Color.BLUE.getGreen(), java.awt.Color.BLUE.getBlue())));
			//cmlEditor.setHighlightRange(line, lenght, true);
		}
	}
}
