package eu.compassresearch.ide.collaboration.ui.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.sync.SerializationException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.Contract;
import eu.compassresearch.ide.collaboration.datamodel.Contracts;
import eu.compassresearch.ide.collaboration.datamodel.Model;
import eu.compassresearch.ide.collaboration.datamodel.Version;
import eu.compassresearch.ide.collaboration.ui.menu.AddCollaboratorRosterMenuContributionItem;

/**
 * Insert the type's description here.
 * @see ViewPart
 */
public class CollaborationView extends ViewPart {
	protected TreeViewer treeViewer;
	protected Text text;
	protected CollaborationLabelProvider labelProvider;
	
	protected Action diffWithPrevAction; 
	protected Action approveContractAction; 
	protected Action rejectContractAction;
	protected Action negotiateContractAction;
	protected Action addToCollaborationGroup;
	protected Action connectAction;
	
	private CollaborationDataModelRoot root;
	
	/**
	 * The constructor.
	 */
	public CollaborationView() {
	}

	public void createPartControl(Composite parent) {
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
	
protected void createActions() {
		
		diffWithPrevAction = new Action("Diff With Previous") {
			public void run() {
				diffWithPrev();
			}			
		};
		diffWithPrevAction.setToolTipText("Diff selected version with previous version");

		approveContractAction = new Action("Approve") {
			public void run() {
				try
				{
					approveSelected();
				} catch (SerializationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		};
		approveContractAction.setToolTipText("Approve this file");
		
		rejectContractAction = new Action("Reject") {
			public void run() {
				try
				{
					rejectSelected();
				} catch (ECFException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		};
		rejectContractAction.setToolTipText("Reject this file");
		
		negotiateContractAction = new Action("Negotiate") {
			public void run() {
				negotiateSelected();
			}			
		};
		negotiateContractAction.setToolTipText("Renegotiate this file");
	}
	
	protected void diffWithPrev() {

		if (treeViewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();
			
			if ((selectedDomainObject instanceof Contracts)) {
				Contracts contracts = (Contracts) selectedDomainObject;
						
			} else if ((selectedDomainObject instanceof Version)) {
				Version version = (Version) selectedDomainObject;
				Contract contract = (Contract) version.getParent();
				
				contract.getFilename();
				MessageProcessor collabMgM = Activator.getDefault().getMessageProcessor();
				
				// CompareUI.openCompareEditor(new CompareInput());
				
				CompareConfiguration configuration = new CompareConfiguration();
				CompareUI.openCompareDialog(new CompareEditorInput(configuration) {
				    @Override
				    protected Object prepareInput(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				        String text1 = "channels\na : nat * nat\nb : int * nat * int\n";
				        String text2 = "channels\na : nat * int\nb : int * nat * int\n";

				        CompareItem left = new CompareItem("1", text1);
				        CompareItem right = new CompareItem("2", text2);
				        DiffNode diffNode = new DiffNode(left, right);
				        return diffNode;
				    }
				});
				
				
			} else {
			
				return;
			}
		}
	}
	
	private void addContextMenu() {
		  MenuManager menuMgr = new MenuManager();
		  Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		  
		  menuMgr.addMenuListener(new IMenuListener() {
		    @Override
		    public void menuAboutToShow(IMenuManager manager) {
		      if(treeViewer.getSelection().isEmpty()) {
		        return;
		      }
		      
		      if(treeViewer.getSelection() instanceof IStructuredSelection) {
	    		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				Model selectedDomainObject = (Model) selection.getFirstElement();
		        
				if ((selectedDomainObject instanceof Contract)) {
					manager.add(approveContractAction);
					manager.add(rejectContractAction);
					manager.add(negotiateContractAction);
		        } else if ((selectedDomainObject instanceof Version)) {
		        	manager.add(diffWithPrevAction);
		        }	
		        else if(selectedDomainObject instanceof CollaborationGroup) {
		        	manager.add(new AddCollaboratorRosterMenuContributionItem());
		        }
		      }
		    }
		  });	
		  
		  menuMgr.setRemoveAllWhenShown(true);
		  treeViewer.getControl().setMenu(menu);
	}

	protected void hookListeners() {
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				// if the selection is empty clear the label
				if(event.getSelection().isEmpty()) {
					text.setText("");
					return;
				}
				if(event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					StringBuffer toShow = new StringBuffer();
					for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
						Object domain = (Model) iterator.next();
						String value = labelProvider.getText(domain);
						toShow.append(value);
						toShow.append(" , ");
					}
					// remove the trailing comma space pair
					if(toShow.length() > 0) {
						toShow.setLength(toShow.length() - 2);
					}
					text.setText(toShow.toString());
				}
			}
		});
	}
	
	public class CompareItem implements IFlushable, IStreamContentAccessor, ITypedElement, IModificationDate, IEditableContent {
	    private String name;
	    private long time;
	    private byte[] contents = null;
	    private boolean editable = true;
	    private IFile toSave;
	   
	    CompareItem(IFile file, String name, String contents, long time) {
	       this.name = name;
	       this.contents = contents == null? null: contents.getBytes();
	       this.time = time;
	       this.toSave = file;
	    }
	    CompareItem(String name, String contents, long time) {
	        this(null, name, contents, time);
	    }
	    public CompareItem(String name, String contents) {
	                this(name, contents, 0);
	        }
	        public InputStream getContents() throws CoreException {
	       return contents == null? null: new ByteArrayInputStream(contents);
	    }
	    public Image getImage() {return null;}
	    public long getModificationDate() {return time;}
	    public String getName() {return name;}
	    public String getType() {
	        // return something else..
	        return ITypedElement.TEXT_TYPE;
	    }
	
	    /*@Override
	    public Object[] getChildren() {
	        // TODO Auto-generated method stub
	        return null;
	    }
	    @Override
	    public Object getAdapter(Class adapter) {
	        // TODO Auto-generated method stub
	        return null;
	    }*/
	    
	    @Override
	    public boolean isEditable() {
	        return editable;
	    }
	    @Override
	    public ITypedElement replace(ITypedElement dest, ITypedElement src) {
	        return dest;
	    }
	    @Override
	    public void setContent(byte[] newContent) {
	        contents = newContent;
	    }
	    @Override
	    public void flush(IProgressMonitor monitor) {
	        try {
	            toSave.setContents(getContents(), IFile.KEEP_HISTORY | IFile.FORCE ,monitor);
	        } catch (CoreException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	}
	
	protected void approveSelected() throws SerializationException {
		if (treeViewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();
		
			if(selectedDomainObject instanceof Contract)
			{
				Contract contract = (Contract) selectedDomainObject;
				MessageProcessor collabMgM = Activator.getDefault().getMessageProcessor();
				FileStatusMessage statMsg = new FileStatusMessage(contract.getReceiver(), contract.getSender(), contract.getFilename(), NegotiationStatus.ACCEPT, new Date());
				collabMgM.sendMessage(contract.getSender(), statMsg.serialize());
			}
		}
	}
	
	protected void rejectSelected() throws SerializationException {
		if (treeViewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();
		
			if(selectedDomainObject instanceof Contract)
			{
				Contract contract = (Contract) selectedDomainObject;
				MessageProcessor collabMgM = Activator.getDefault().getMessageProcessor();
				FileStatusMessage statMsg = new FileStatusMessage(contract.getReceiver(), contract.getSender(), contract.getFilename(), NegotiationStatus.REJECT, new Date());
				collabMgM.sendMessage(contract.getSender(), statMsg.serialize());
			}
		}
	}
	
	protected void negotiateSelected() {
		if (treeViewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();
		
			if(selectedDomainObject instanceof Contract)
			{
				Contract contract = (Contract) selectedDomainObject;
				MessageProcessor collabMgM = Activator.getDefault().getMessageProcessor();
				
			
				IFile oldFile = collabMgM.getProjectFolder().getFile(contract.getFilename());
				final IFile file = collabMgM.nextFilename(contract.getFilename());
			
				try
				{
					file.create(oldFile.getContents(), IResource.NONE, null);
				} catch (CoreException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Display.getDefault().asyncExec(new Runnable()
				{
					public void run()
					{
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						try
						{
							IEditorPart openEditor = IDE.openEditor(page, file, true);
						} catch (PartInitException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}
	}
		
	public CollaborationDataModelRoot getInitalInput() {

		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();
		
		return dataModelManager.getDatamodel(); 
		
//		root = new CollaborationDataModelRoot();
//		
//		CollaborationProject clPr = new CollaborationProject("test");
//		root.addCollaborationProject(clPr);
////		
////		CollaborationProject clPr2 = new CollaborationProject("test 2");
////		root.addCollaborationProject(clPr2);
////
//		User u1 = new User("(You)");
//		
//		Contract contract = new Contract();
////		
////		clPr.addContract(contract);
//		clPr.addCollaborator(u1);
		
//		return root;
	}
	
	public CollaborationDataModelRoot getDataModel()
	{
		return root;
	}
	
	public void setFocus() {}

}
