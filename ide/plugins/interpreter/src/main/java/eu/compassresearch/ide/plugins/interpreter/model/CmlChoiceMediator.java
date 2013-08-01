package eu.compassresearch.ide.plugins.interpreter.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.core.interpreter.debug.Choice;
import eu.compassresearch.core.interpreter.utility.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.utility.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.utility.messaging.ResponseMessage;
import eu.compassresearch.ide.plugins.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.plugins.interpreter.views.CmlEventOptionView;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;

public class CmlChoiceMediator implements IDoubleClickListener,
		ISelectionChangedListener
{

	// SynchronousQueue<String> selectSync = new SynchronousQueue<String>();
	final CmlDebugTarget cmlDebugTarget;
	RequestMessage requestMessage = null;
	private List<StyleRange> lastSelectedRanges = new LinkedList<StyleRange>();

	public CmlChoiceMediator(CmlDebugTarget cmlDebugTarget)
	{
		this.cmlDebugTarget = cmlDebugTarget;

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
					CmlChoiceMediator.this.cmlDebugTarget.resume();

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
					CmlEditor cmlEditor = (CmlEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					if (cmlEditor != null)
					{
						StyledText styledText = (StyledText) cmlEditor.getAdapter(Control.class);
						clearSelections(styledText);
					}
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
		this.cmlDebugTarget.sendMessage(new ResponseMessage(requestMessage.getRequestId(), CmlRequest.CHOICE, event));
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

	private void clearSelections(StyledText styledText)
	{
		for (StyleRange sr : lastSelectedRanges)
		{
			sr.background = null;
			styledText.setStyleRange(sr);
		}
		lastSelectedRanges.clear();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event)
	{

		if (event.getSelection() instanceof IStructuredSelection)
		{
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();

			Choice choice = (Choice) selection.getFirstElement();

			// Path path = new Path(choice.getLocations().get(0).getFile().getAbsolutePath());
			// IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath location = Path.fromOSString(choice.getLocations().get(0).getFile().getAbsolutePath());
			IFile file = workspace.getRoot().getFileForLocation(location);
			// It may be a linked resource
			if (file == null
					&& workspace.getRoot().findFilesForLocation(location).length > 0)
				file = workspace.getRoot().findFilesForLocation(location)[0];
			IEditorPart editor = null;
			try
			{
				editor = IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), file, true);
			} catch (PartInitException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// CmlEditor cmlEditor = (CmlEditor)
			// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editor != null)
			{
				StyledText styledText = (StyledText) ((CmlEditor) editor).getAdapter(Control.class);
				// clear the last selection
				clearSelections(styledText);

				for (ILexLocation loc : choice.getLocations())
				{
					int length = loc.getEndOffset() - loc.getStartOffset() + 1;
					StyleRange sr = styledText.getStyleRangeAtOffset(loc.getStartOffset());

					// if nothing is found we try to look nearby
					if (sr == null)
						for (int i = loc.getStartOffset() - 50; i < loc.getStartOffset() + 50; i++)
						{
							sr = styledText.getStyleRangeAtOffset(i);
							if (sr != null)
								break;
						}

					if (sr != null)
					{
						sr.length = length;
						sr.background = new Color(null, new RGB(java.awt.Color.GRAY.getRed(), java.awt.Color.GRAY.getGreen(), java.awt.Color.GRAY.getBlue()));
						styledText.setStyleRange(sr);
						lastSelectedRanges.add(sr);
					}
				}
				styledText.setCaretOffset(choice.getLocations().get(0).getStartOffset());
				styledText.showSelection();
			}
		}
	}
}
