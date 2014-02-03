package eu.compassresearch.ide.collaboration.ui.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.IFlushable;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.sync.SerializationException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.ConfigurationComparison;
import eu.compassresearch.ide.collaboration.datamodel.File;
import eu.compassresearch.ide.collaboration.datamodel.Model;
import eu.compassresearch.ide.collaboration.files.FileComparison;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.AddCollaboratorRosterMenuContributionItem;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

/**
 * @see ViewPart
 */
public class CollaborationView extends ViewPart
{
	protected TreeViewer treeViewer;
	protected Text text;
	protected CollaborationLabelProvider labelProvider;

	protected IAction diffWithPrevConfigurationAction;
	protected IAction activateConfigurationAction;
	protected Action signAndShareAction;
	protected Action diffWithPrevFileAction;
	protected Action approveContractAction;
	protected Action rejectContractAction;
	protected Action negotiateContractAction;
	protected Action addToCollaborationGroup;

	public void createPartControl(Composite parent)
	{
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);

		text = new Text(parent, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		text.setLayoutData(layoutData);

		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(new CollaborationContentProvider());
		labelProvider = new CollaborationLabelProvider();
		treeViewer.setLabelProvider(labelProvider);

		treeViewer.setUseHashlookup(true);

		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		treeViewer.getControl().setLayoutData(layoutData);

		createActions();
		hookListeners();
		addContextMenu();

		treeViewer.setInput(getInitalInput());
		treeViewer.expandAll();
	}

	protected void createActions()
	{
		diffWithPrevConfigurationAction = new Action("Diff with previous configuration")
		{
			public void run()
			{
				diffWithPrev();
			}
		};
		diffWithPrevConfigurationAction.setToolTipText("Diff selected configuration with the previous configuration");

		activateConfigurationAction = new Action("Activate configuration (introduce files into workspace)")
		{
			public void run()
			{
				activateConfiguration();
			}
		};
		activateConfigurationAction.setToolTipText("Activates this configuration by moving the files into the workspace project");

		diffWithPrevFileAction = new Action("Diff with previous file")
		{
			public void run()
			{
				diffWithPrev();
			}
		};
		diffWithPrevFileAction.setToolTipText("Diff selected file version with the version in the previous configuration");

		signAndShareAction = new Action("Sign and Share Configuration")
		{
			public void run()
			{
				signAndShare();
			}
		};
		signAndShareAction.setToolTipText("Sign configuration and share with collaboration group");

		approveContractAction = new Action("Approve")
		{
			public void run()
			{
				try
				{
					approveSelected();
				} catch (CoreException e)
				{
					ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
					e.printStackTrace();
				}
			}
		};
		approveContractAction.setToolTipText("Approve this file");

		rejectContractAction = new Action("Reject")
		{
			public void run()
			{
				try
				{
					rejectSelected();
				} catch (ECFException e)
				{
					ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
					e.printStackTrace();
				}
			}
		};
		rejectContractAction.setToolTipText("Reject this file");

		negotiateContractAction = new Action("Negotiate")
		{
			public void run()
			{
				negotiateSelected();
			}
		};
		negotiateContractAction.setToolTipText("Renegotiate this file");
	}

	private void addContextMenu()
	{
		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(treeViewer.getControl());

		menuMgr.addMenuListener(new IMenuListener()
		{
			@Override
			public void menuAboutToShow(IMenuManager manager)
			{
				if (treeViewer.getSelection().isEmpty())
				{
					return;
				}

				if (treeViewer.getSelection() instanceof IStructuredSelection)
				{
					IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
					Model selectedDomainObject = (Model) selection.getFirstElement();

					if ((selectedDomainObject instanceof Configuration))
					{
						Configuration config = (Configuration) selectedDomainObject;

						if (config.isShared() || config.isReceived())
						{
							manager.add(activateConfigurationAction);
						}

						if (Activator.getDefault().getConnectionManager().isConnectionInitialized())
						{

							if (config.isLocal())
							{
								manager.add(signAndShareAction);
							}

							if (config.isReceived())
							{
								manager.add(approveContractAction);
								manager.add(rejectContractAction);
							}
						}

						manager.add(diffWithPrevConfigurationAction);

					} else if ((selectedDomainObject instanceof File))
					{

						File file = (File) selectedDomainObject;

						if (!file.isNewFile())
						{
							manager.add(diffWithPrevFileAction);
						}
					} else if (selectedDomainObject instanceof CollaborationGroup)
					{
						manager.add(new AddCollaboratorRosterMenuContributionItem());
					}
				}
			}
		});

		menuMgr.setRemoveAllWhenShown(true);
		treeViewer.getControl().setMenu(menu);
	}

	protected void hookListeners()
	{
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				// if the selection is empty clear the label
				if (event.getSelection().isEmpty())
				{
					text.setText("");
					return;
				}
				if (event.getSelection() instanceof IStructuredSelection)
				{
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					StringBuffer toShow = new StringBuffer();
					for (Iterator iterator = selection.iterator(); iterator.hasNext();)
					{
						Object domain = (Model) iterator.next();
						String value = labelProvider.getText(domain);
						toShow.append(value);
						toShow.append(" , ");
					}
					// remove the trailing comma space pair
					if (toShow.length() > 0)
					{
						toShow.setLength(toShow.length() - 2);
					}
					text.setText(toShow.toString());
				}
			}
		});

		treeViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			@Override
			public void doubleClick(DoubleClickEvent event)
			{
				ISelection selection = event.getSelection();

				if (selection.isEmpty())
				{
					return;
				} else if (selection instanceof TreeSelection)
				{

					TreeSelection ts = (TreeSelection) selection;
					Object clickedElement = ts.getFirstElement();

					// open file version in editor on double click
					if (clickedElement instanceof File)
					{

						File file = (File) clickedElement;
						try
						{
							CollaborationProject collabProj = file.getCollaborationProject();
							openFileInEditor(file, collabProj);
						} catch (CoreException | IOException e)
						{
							e.printStackTrace();
							Notification.logError(Notification.Collab_Dialog_ERROR_COULD_NOT_OPEN_EDITOR, e);
						}
					}
				}
			}
		});
	}

	protected void activateConfiguration()
	{
		if (treeViewer.getSelection().isEmpty())
		{
			return;
		} else
		{
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();

			if (selectedDomainObject instanceof Configuration)
			{

				Configuration selectedConfig = (Configuration) selectedDomainObject;

				CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();

				try
				{
					dataModelManager.activateConfiguration(selectedConfig);
				} catch (CoreException e)
				{
					ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
					e.printStackTrace();
				}

			}
		}
	}

	private void openFileInEditor(File file,
			CollaborationProject collaborationProject) throws CoreException,
			IOException
	{
		CollaborationDataModelManager collabMgM = Activator.getDefault().getDataModelManager();

		IFile iFile = collabMgM.getFile(file, collaborationProject);

		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart openEditor;
		if (file.isStored())
		{
			// file is loaded from the protected collaboration dir,
			// so open in a temp file to avoid modifications or work around of read-only
			IFileStore tempFileStore = getTempFileStore(file, iFile);
			openEditor = IDE.openEditorOnFileStore(page, tempFileStore);
		} else
		{
			// file is not stored in the hidden collaboration dir and can be opened directly from the workspace
			openEditor = IDE.openEditor(page, iFile, true);
		}
		openEditor.getEditorInput();
	}

	IFileStore getTempFileStore(File file, IFile storedFile)
			throws IOException, CoreException
	{

		java.io.File tempFile = java.io.File.createTempFile(file.getName()
				+ "-" + file.getHashFileName(), null);
		tempFile.deleteOnExit();

		final IFileStore storedFileStore = EFS.getStore(storedFile.getLocationURI());
		final IFileStore tempFileStore = EFS.getLocalFileSystem().fromLocalFile(tempFile);

		storedFileStore.copy(tempFileStore, EFS.OVERWRITE, null);
		tempFile = tempFileStore.toLocalFile(EFS.CACHE, null);

		return tempFileStore;
	}

	protected void diffWithPrev()
	{

		if (treeViewer.getSelection().isEmpty())
		{
			return;
		}

		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();

		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		Model selectedDomainObject = (Model) selection.getFirstElement();

		if ((selectedDomainObject instanceof Configuration))
		{
			Configuration configuration = (Configuration) selectedDomainObject;

			ConfigurationComparison configurationCompare = dataModelManager.compareConfigurationWithPrev(configuration);

			if (configurationCompare == null)
			{
				CollaborationDialogs.getInstance().displayNotificationPopup(configuration.getName(), Notification.Collab_Dialog_ERROR_COMPARE_NO_PREVIOUS);
				return;
			}

			configurationCompare.getCompareResult();

		} else if ((selectedDomainObject instanceof File))
		{
			File selectedFile = (File) selectedDomainObject;

			try
			{
				FileComparison prevAndSelectedCompare = dataModelManager.compareFileWithPrev(selectedFile);
				// is file defined previously
				if (prevAndSelectedCompare == null)
				{
					CollaborationDialogs.getInstance().displayNotificationPopup(selectedFile.getName(), Notification.Collab_Dialog_ERROR_COMPARE_NO_PREVIOUS);
					return;
				}

				// are they identical?
				if (prevAndSelectedCompare.isIdentical())
				{
					CollaborationDialogs.getInstance().displayNotificationPopup(selectedFile.getName(), Notification.Collab_Dialog_COMPARE_ARE_IDENTICAL);
					return;
				}

				showCompareDialog(prevAndSelectedCompare);

			} catch (CoreException | IOException e)
			{
				e.printStackTrace();
				Notification.logError(Notification.Collab_Dialog_ERROR_COMPARE_FAILED, e);
			}
		}
	}

	private void showCompareDialog(final FileComparison fileCompare)
	{
		final CompareConfiguration compareConfiguration = new CompareConfiguration();
		compareConfiguration.setLeftLabel(fileCompare.getPreviousFileName());
		compareConfiguration.setRightLabel(fileCompare.getTargetFileName());
		CompareUI.openCompareDialog(new CompareEditorInput(compareConfiguration)
		{
			@Override
			protected Object prepareInput(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException
			{

				CompareItem left = new CompareItem(fileCompare.getTargetFileName(), fileCompare.getTargetFileContent());
				CompareItem right = new CompareItem(fileCompare.getPreviousFileName(), fileCompare.getPreviousFileContent());
				DiffNode diffNode = new DiffNode(left, right);

				return diffNode;
			}

			@Override
			public String getCancelButtonLabel()
			{
				return Notification.Collab_Dialog_BTN_OK;
			}
		});
	}

	protected void signAndShare()
	{
		if (treeViewer.getSelection().isEmpty())
		{
			return;
		} else
		{
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();

			if (selectedDomainObject instanceof Configuration)
			{
				Configuration config = (Configuration) selectedDomainObject;
				CollaborationDataModelManager collabMgM = Activator.getDefault().getDataModelManager();

				try
				{
					collabMgM.signAndShareConfiguration(config);
				} catch (CoreException e)
				{
					e.printStackTrace();
					Notification.logError(e.toString(), e);
				}
			}
		}
	}

	public class CompareItem implements IFlushable, IStreamContentAccessor,
			ITypedElement, IModificationDate, IEditableContent
	{
		private String name;
		private long time;
		private byte[] contents = null;
		private boolean editable = true;
		private IFile toSave;

		CompareItem(IFile file, String name, String contents, long time)
		{
			this.name = name;
			this.contents = contents == null ? null : contents.getBytes();
			this.time = time;
			this.toSave = file;
		}

		CompareItem(String name, String contents, long time)
		{
			this(null, name, contents, time);
		}

		public CompareItem(String name, String contents)
		{
			this(name, contents, 0);
		}

		public InputStream getContents() throws CoreException
		{
			return contents == null ? null : new ByteArrayInputStream(contents);
		}

		public Image getImage()
		{
			return null;
		}

		public long getModificationDate()
		{
			return time;
		}

		public String getName()
		{
			return name;
		}

		public String getType()
		{
			return ITypedElement.TEXT_TYPE;
		}

		@Override
		public boolean isEditable()
		{
			return editable;
		}

		@Override
		public ITypedElement replace(ITypedElement dest, ITypedElement src)
		{
			return dest;
		}

		@Override
		public void setContent(byte[] newContent)
		{
			contents = newContent;
		}

		@Override
		public void flush(IProgressMonitor monitor)
		{
			try
			{
				toSave.setContents(getContents(), IFile.KEEP_HISTORY
						| IFile.FORCE, monitor);
			} catch (CoreException e)
			{
				ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
				e.printStackTrace();
			}
		}
	}

	protected void approveSelected() throws CoreException
	{
		if (treeViewer.getSelection().isEmpty())
		{
			return;
		} else
		{
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();

			if (selectedDomainObject instanceof Configuration)
			{
				Configuration configToApprove = (Configuration) selectedDomainObject;

				CollaborationDataModelManager collabMgM = Activator.getDefault().getDataModelManager();

				collabMgM.approveConfiguration(configToApprove);
			}
		}
	}

	protected void rejectSelected() throws SerializationException
	{
		if (treeViewer.getSelection().isEmpty())
		{
			return;
		} else
		{
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();

			if (selectedDomainObject instanceof Configuration)
			{
				Configuration configurationToReject = (Configuration) selectedDomainObject;

				CollaborationDataModelManager collabMgM = Activator.getDefault().getDataModelManager();

				collabMgM.rejectConfiguration(configurationToReject);
			}
		}
	}

	protected void negotiateSelected()
	{
		if (treeViewer.getSelection().isEmpty())
		{
			return;
		} else
		{
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();

			if (selectedDomainObject instanceof Configuration)
			{
				Configuration contract = (Configuration) selectedDomainObject;
				MessageProcessor messageProcessor = Activator.getDefault().getConnectionManager().getMessageProcessor();

				// IFile oldFile = collabMgM.getProjectFolder().getFile(contract.getFilename());
				// final IFile file = collabMgM.nextFilename(contract.getFilename());
				//
				// try
				// {
				// file.create(oldFile.getContents(), IResource.NONE, null);
				// } catch (CoreException e1)
				// {
				// ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
				// e.getMessage(), e));
				// e.printStackTrace();
				// }

				// Display.getDefault().asyncExec(new Runnable()
				// {
				// public void run()
				// {
				// IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				// try
				// {
				// IEditorPart openEditor = IDE.openEditor(page, file, true);
				// } catch (PartInitException e)
				// {
				// ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
				// e.getMessage(), e));
				// e.printStackTrace();
				// }
				// }
				// });
			}
		}
	}

	public CollaborationDataModelRoot getInitalInput()
	{

		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();

		return dataModelManager.getDataModel();
	}

	public Model getSelectedEntry()
	{
		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		Model selectedDomainObject = (Model) selection.getFirstElement();
		return selectedDomainObject;
	}

	public void setFocus()
	{
		treeViewer.expandAll();
	}

}
