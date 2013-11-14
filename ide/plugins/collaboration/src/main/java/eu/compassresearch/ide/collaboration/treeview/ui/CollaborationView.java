package eu.compassresearch.ide.collaboration.treeview.ui;

import java.util.Date;
import java.util.Iterator;

import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.sync.SerializationException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.management.CollaborationManager;
import eu.compassresearch.ide.collaboration.menu.AddCollaboratorRosterMenuContributionItem;
import eu.compassresearch.ide.collaboration.messages.FileStatusMessage;
import eu.compassresearch.ide.collaboration.messages.FileStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.treeview.model.CollaborationGroup;
import eu.compassresearch.ide.collaboration.treeview.model.Contract;
import eu.compassresearch.ide.collaboration.treeview.model.Contracts;
import eu.compassresearch.ide.collaboration.treeview.model.Model;
import eu.compassresearch.ide.collaboration.treeview.model.TreeRoot;
import eu.compassresearch.ide.collaboration.treeview.model.User;
import eu.compassresearch.ide.collaboration.treeview.model.Version;

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
	
	private TreeRoot root;
	
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
		createToolbar();
		hookListeners();
		addContextMenu();
		
		treeViewer.setInput(getInitalInput());
		treeViewer.expandAll();
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
				} catch (ECFException e)
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
		negotiateContractAction.setEnabled(false);
		
		addToCollaborationGroup = new Action("Add Collaborator") {
			public void run() {
				negotiateSelected();
			}			
		};
		addToCollaborationGroup.setToolTipText("Add Collaborator to collaboration group");
		addToCollaborationGroup.setEnabled(false);
		//addToCollaborationGroup.
	}
	
	protected void diffWithPrev() {

		if (treeViewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();
			
			if ((selectedDomainObject instanceof Contracts)) {
				Contracts contracts = (Contracts) selectedDomainObject;
						
			} else if ((selectedDomainObject instanceof Contract)) {
				Contract contract = (Contract) selectedDomainObject;
			} else {
			
				return;
			}
		}
	}
	
	protected void approveSelected() throws SerializationException, ECFException {
		if (treeViewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();
		
			if(selectedDomainObject instanceof Contract)
			{
				Contract contract = (Contract) selectedDomainObject;
				CollaborationManager collabMgM = Activator.getDefault().getCollaborationManager();
				FileStatusMessage statMsg = new FileStatusMessage(contract.getReceiver(), contract.getSender(), contract.getFilename(), NegotiationStatus.ACCEPT, new Date());
				collabMgM.sendMessage(contract.getSender(), statMsg.serialize());
			}
		}
	}
	
	protected void rejectSelected() throws SerializationException, ECFException {
		if (treeViewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Model selectedDomainObject = (Model) selection.getFirstElement();
		
			if(selectedDomainObject instanceof Contract)
			{
				Contract contract = (Contract) selectedDomainObject;
				CollaborationManager collabMgM = Activator.getDefault().getCollaborationManager();
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
		
			if(selectedDomainObject instanceof CollaborationGroup)
			{
				CollaborationGroup group = (CollaborationGroup) selectedDomainObject;
				group.addCollaborator(new User("Bla"));
			}
		
		}
	}
	
	protected void createToolbar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	//toolbarManager.add(arg0);
		//toolbarManager.add(approveContractAction);
	}
	
	
	public TreeRoot getInitalInput() {
		root = new TreeRoot();
		
		Contracts contracts = new Contracts();
//		Contract c1 = new Contract("Contract 1");
//		Version v1 = new Version("version 1");
//		c1.addVersion(v1);
//		Version v2 = new Version("version 2");
//		c1.addVersion(v2);
//		Share s1 = new Share("share 1");
//		c1.addShare(s1);
		//contracts.addContract(c1);
	
		root.addContracts(contracts);
		
		CollaborationGroup collabGrp = new CollaborationGroup();
		User u1 = new User("(You)");
		collabGrp.addCollaborator(u1);
//		User u2 = new User("User 2");
//		collabGrp.addCollaborator(u2);
		
		root.addCollaboratorGroup(collabGrp);
		
		return root;
	}
	
	public TreeRoot getRoot()
	{
		return root;
	}
	
	public void setFocus() {}

}
