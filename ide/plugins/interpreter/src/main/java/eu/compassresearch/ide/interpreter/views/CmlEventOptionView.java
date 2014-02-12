package eu.compassresearch.ide.interpreter.views;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import eu.compassresearch.core.interpreter.debug.CmlInterpreterStateDTO;
import eu.compassresearch.core.interpreter.debug.TransitionDTO;
import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;

public class CmlEventOptionView extends AbstractCmlDebugView implements
		IDebugEventSetListener, IDoubleClickListener, ISelectionChangedListener
{
	List<String> options = new LinkedList<String>();
	private Map<StyledText, List<StyleRange>> lastSelectedRanges = new HashMap<StyledText, List<StyleRange>>();

	Group consoleGroup = null;

	@Override
	public void handleDebugEvents(final DebugEvent[] events)
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			@Override
			public void run()
			{
				for (DebugEvent e : events)
				{
					if (e.getKind() == DebugEvent.SUSPEND
							&& e.getSource() == target)
					{
						if (e.getSource() != null
								&& e.getSource() instanceof CmlDebugTarget)
						{
							CmlDebugTarget t = (CmlDebugTarget) e.getSource();
							if (isAvailable())
							{
								Display display = viewer.getControl().getDisplay();
								if (t.isSuspendedForSelection())
								{
									filltransitionList();
									viewer.getControl().setBackground(display.getSystemColor(SWT.COLOR_WHITE));
								} else
								{
									viewer.getControl().setBackground(new Color(display, 240, 240, 240));
								}
								viewer.getControl().setEnabled(t.isSuspendedForSelection());

							}
						}
					} else if (e.getKind() == DebugEvent.TERMINATE
							&& e.getSource() == target)
					{
						if (isAvailable())
						{
							viewer.setInput(null);
						}
					}
				}
			}
		});
	}

	@Override
	public String getTitle()
	{
		return "Event Options";
	}

	@Override
	public void setFocus()
	{
	}

	@Override
	public void createPartControl(final org.eclipse.swt.widgets.Composite parent)
	{

		FormLayout layout = new FormLayout();
		parent.setLayout(layout);

		createConsoleInputGroup(parent);
		createListViewer(parent);

		DebugPlugin.getDefault().addDebugEventListener(this);
		super.createPartControl(parent);
	}

	private void createConsoleInputGroup(Composite parent)
	{
		consoleGroup = new Group(parent, parent.getStyle());
		consoleGroup.setText("Console");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		// group.setLayoutData(gd);

		FormData formData = new FormData();
		// formData.bottom = new FormAttachment(70,10);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment(85);
		formData.left = new FormAttachment(0);
		formData.bottom = new FormAttachment(100);
		consoleGroup.setLayoutData(formData);

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 3;
		layout.horizontalSpacing = SWT.FILL;
		consoleGroup.setLayout(layout);

		// editParent = group;

		Label label = new Label(consoleGroup, SWT.MIN);
		label.setText("Input:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);

		final Text consoleText = new Text(consoleGroup, SWT.SINGLE | SWT.BORDER
				| SWT.FILL);
		gd = new GridData(GridData.BEGINNING | GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		gd.horizontalAlignment = SWT.FILL;
		consoleText.setLayoutData(gd);

		consoleText.addKeyListener(new KeyListener()
		{

			@Override
			public void keyReleased(KeyEvent event)
			{
				if (event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR)
				{
					sendConsoleInput(consoleText);
				}
			}

			@Override
			public void keyPressed(KeyEvent e)
			{

			}
		});

		Button selectProjectButton = createPushButton(consoleGroup, "Enter", null);

		selectProjectButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				sendConsoleInput(consoleText);
			}
		});

		recursiveSetEnabled(consoleGroup, false);
	}

	/**
	 * Creates and returns a new push button with the given label and/or image.
	 * 
	 * @param parent
	 *            parent control
	 * @param label
	 *            button label or <code>null</code>
	 * @param image
	 *            image of <code>null</code>
	 * @return a new push button
	 */
	protected Button createPushButton(Composite parent, String label,
			Image image)
	{
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (image != null)
		{
			button.setImage(image);
		}
		if (label != null)
		{
			button.setText(label);
		}
		GridData gd = new GridData();
		button.setLayoutData(gd);
		// setButtonDimensionHint(button);
		return button;
	}

	private void createListViewer(final org.eclipse.swt.widgets.Composite parent)
	{
		Group eventSelectionGroup = new Group(parent, parent.getStyle());
		eventSelectionGroup.setText("Event Selection");
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 1;
		layout.horizontalSpacing = SWT.FILL;
		eventSelectionGroup.setLayout(layout);

		FormData formData = new FormData();
		formData.bottom = new FormAttachment(consoleGroup);
		formData.right = new FormAttachment(100);
		formData.left = new FormAttachment(0);
		formData.top = new FormAttachment(0);
		eventSelectionGroup.setLayoutData(formData);

		viewer = new ListViewer(eventSelectionGroup, SWT.FILL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		viewer.getControl().setLayoutData(gd);

		viewer.addDoubleClickListener(this);
		viewer.addSelectionChangedListener(this);
		viewer.setContentProvider(new IStructuredContentProvider()
		{

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput)
			{
			}

			@Override
			public void dispose()
			{

			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object[] getElements(Object inputElement)
			{
				return ((List) inputElement).toArray();
			}
		});
	}

	private void selectChoice(TransitionDTO event)
	{
		boolean expectConsoleRead = event.getName().contains("?");
		target.select(event, expectConsoleRead);
		finish();
		recursiveSetEnabled(consoleGroup, expectConsoleRead);
	}

	private void finish()
	{
		CmlUtil.clearSelections(lastSelectedRanges);
		viewer.setInput(null);
		viewer.refresh();
	}

	@Override
	public void doubleClick(DoubleClickEvent event)
	{
		IStructuredSelection selection = null;
		TransitionDTO choice = null;

		if (event.getSelection() instanceof IStructuredSelection)
		{
			selection = (IStructuredSelection) event.getSelection();
			choice = (TransitionDTO) selection.getFirstElement();
		}

		selectChoice(choice);

	}

	@Override
	public void selectionChanged(SelectionChangedEvent event)
	{
		if (event.getSelection() instanceof IStructuredSelection)
		{
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();

			TransitionDTO choice = (TransitionDTO) selection.getFirstElement();
			CmlUtil.clearSelections(lastSelectedRanges);
			CmlUtil.setSelectionFromLocations(choice.getLocations(), lastSelectedRanges);
			// get a random one and set the
			StyledText st = lastSelectedRanges.keySet().iterator().next();
			CmlUtil.showLocation(st, choice.getLocations().get(0));
		}
	}

	public void recursiveSetEnabled(Control ctrl, boolean enabled)
	{
		if (ctrl instanceof Composite)
		{
			Composite comp = (Composite) ctrl;
			for (Control c : comp.getChildren())
			{
				recursiveSetEnabled(c, enabled);
			}
		} else
		{
			ctrl.setEnabled(enabled);
		}
	}

	private void filltransitionList()
	{
		recursiveSetEnabled(consoleGroup, false);
		CmlInterpreterStateDTO lastState = target.getLastState();
		if (lastState != null)
		{
			List<TransitionDTO> transitions = lastState.getTransitions();
			if (transitions != null && !transitions.isEmpty())
			{
				Collections.sort(transitions, new Comparator<TransitionDTO>()
				{

					@Override
					public int compare(TransitionDTO o1, TransitionDTO o2)
					{
						if (o1.getName().equals("tock"))
						{
							return -1;
						} else if (o2.getName().equals("tock"))
						{
							return 1;
						} else
						{
							return o1.getName().compareToIgnoreCase(o2.getName());
						}
					}

				});

				viewer.setInput(transitions);
				if (!transitions.isEmpty())
				{
					viewer.setSelection(new StructuredSelection(transitions.get(0)));
				}
				viewer.refresh();
			}else
			{
				viewer.setInput(null);
				viewer.refresh();
			}
		}
	}

	void internalViewerUpdate()
	{
		if (target != null)
		{
			filltransitionList();
			recursiveSetEnabled(consoleGroup, target.isSuspendedForConsoleRead());
		}
	}

	private void sendConsoleInput(final Text consoleText)
	{
		if (!consoleText.getText().isEmpty())
		{
			try
			{
				target.getProcess().getStreamsProxy().write(consoleText.getText()
						+ "\n");
				consoleText.setText("");
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
