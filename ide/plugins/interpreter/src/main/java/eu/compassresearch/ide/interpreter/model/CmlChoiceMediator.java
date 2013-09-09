package eu.compassresearch.ide.interpreter.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.core.interpreter.debug.Choice;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.debug.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.debug.messaging.ResponseMessage;
import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.interpreter.protocol.CmlCommunicationManager;
import eu.compassresearch.ide.interpreter.views.CmlEventOptionView;

public class CmlChoiceMediator implements IDoubleClickListener, ISelectionChangedListener {

	// SynchronousQueue<String> selectSync = new SynchronousQueue<String>();
	final CmlDebugTarget cmlDebugTarget;
	RequestMessage requestMessage = null;
	private Map<StyledText,List<StyleRange>> lastSelectedRanges = new HashMap<StyledText,List<StyleRange>>();
	CmlCommunicationManager communication;

	public CmlChoiceMediator(CmlDebugTarget cmlDebugTarget,
			CmlCommunicationManager cmlCommunicationManager)
	{
		this.cmlDebugTarget = cmlDebugTarget;
		this.communication = cmlCommunicationManager;

		DebugPlugin.getDefault().addDebugEventListener(new IDebugEventSetListener()
		{

			@Override
			public void handleDebugEvents(DebugEvent[] events)
			{
				for (DebugEvent dbgEvent : events)
					if (dbgEvent.getKind() == DebugEvent.TERMINATE)
					{
						finish();
						DebugPlugin.getDefault().removeDebugEventListener(this);
					}

			}
		});

		Display.getDefault().syncExec(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					CmlEventOptionView view = (CmlEventOptionView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_OPTION_VIEW.toString());
					view.getListViewer().addDoubleClickListener(CmlChoiceMediator.this);
					view.getListViewer().addSelectionChangedListener(CmlChoiceMediator.this);
					// CmlChoiceMediator.this.cmlDebugTarget.resume();

				} catch (PartInitException e)
				{
					e.printStackTrace();
				} catch (CoreException e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void finish()
	{
		Display.getDefault().syncExec(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					CmlEventOptionView view = (CmlEventOptionView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_OPTION_VIEW.toString());
					view.getListViewer().removeDoubleClickListener(CmlChoiceMediator.this);
					view.getListViewer().removeSelectionChangedListener(CmlChoiceMediator.this);
					CmlUtil.clearSelections(lastSelectedRanges);
					view.getListViewer().setInput(null);
					view.getListViewer().refresh();

				} catch (PartInitException e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public void setChoiceOptions(final List<Choice> events,
			RequestMessage requestMessage)
	{
		this.requestMessage = requestMessage;
		Display.getDefault().asyncExec(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					CmlEventOptionView view = (CmlEventOptionView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_OPTION_VIEW.toString());
					Collections.sort(events, new Comparator<Choice>()
							{

						@Override
						public int compare(Choice o1, Choice o2)
						{
							if (o1.getName().equals("tock"))
								return -1;
							else if (o2.getName().equals("tock"))
								return 1;
							else
								return o1.getName().compareToIgnoreCase(o2.getName());
						}

							});
					view.getListViewer().setInput(events);
					view.getListViewer().setSelection(new StructuredSelection(events.get(0)));
				} catch (PartInitException e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public void selectChoice(Choice event)
	{
		communication.sendMessage(new ResponseMessage(requestMessage.getRequestId(), CmlRequest.CHOICE, event));
		// setChoiceOptions(null,null);
		finish();
	}

	@Override
	public void doubleClick(DoubleClickEvent event)
	{

		IStructuredSelection selection = null;
		Choice choice = null;

		// CmlEditor cmlEditor =
		// (CmlEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		// StyledText styledText = (StyledText)cmlEditor.getAdapter(Control.class);
		// clearSelections(styledText);

		if (event.getSelection() instanceof IStructuredSelection)
		{
			selection = (IStructuredSelection) event.getSelection();
			choice = (Choice) selection.getFirstElement();
		}

		selectChoice(choice);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event)
	{
		if (event.getSelection() instanceof IStructuredSelection)
		{
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();

			Choice choice = (Choice) selection.getFirstElement();
			CmlUtil.clearSelections(lastSelectedRanges);
			CmlUtil.setSelectionFromLocations(choice.getLocations(), lastSelectedRanges);
			//get a random one and set the 
			StyledText st = lastSelectedRanges.keySet().iterator().next();
			CmlUtil.showLocation(st,choice.getLocations().get(0));
		}
	}
}
