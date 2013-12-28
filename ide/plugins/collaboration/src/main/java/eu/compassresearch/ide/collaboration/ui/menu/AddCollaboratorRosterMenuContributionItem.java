package eu.compassresearch.ide.collaboration.ui.menu;

import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.ui.menu.AbstractRosterMenuContributionItem;
import org.eclipse.ecf.presence.ui.menu.AbstractRosterMenuHandler;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;

import eu.compassresearch.ide.collaboration.notifications.Notification;


public class AddCollaboratorRosterMenuContributionItem extends
		AbstractRosterMenuContributionItem
{

	public AddCollaboratorRosterMenuContributionItem()
	{
		super();
		setTopMenuName(Notification.CollabRosterMenuContributionItem_ADD_COLLABORATOR_MENU);
	}
	
	public AddCollaboratorRosterMenuContributionItem(String id)
	{
		super(id);
		setTopMenuName(Notification.CollabRosterMenuContributionItem_ADD_COLLABORATOR_MENU);
	}
	
	@Override
	protected AbstractRosterMenuHandler createRosterEntryHandler(
			IRosterEntry rosterEntry)
	{
		return new AddCollaboratorRosterMenuHandler(rosterEntry);
	}
	
	protected IContributionItem[] getContributionItems() {

		MenuManager menuManager = createMenuManagerForTop();
		
		IContributionItem[] contributions = super.getContributionItems();

		//No collaborators connected 
		if(contributions == NO_CONTRIBUTIONS){
			final IAction notConnected = new Action() {
				public void run() {
				}
			};
			notConnected.setText(Notification.CollabRosterMenuContributionItem_NO_OTHER_COLLABORATORS);
			notConnected.setEnabled(false);
			notConnected.setImageDescriptor(getTopMenuImageDescriptor());
			menuManager.add(new ActionContributionItem(notConnected));
			
		} else {
			
			//if only one connected account, create simpler menu
			for (IContributionItem item : contributions)
			{
				//loose seperators
				if(item instanceof MenuManager)
				{
					MenuManager menuConnectedAccounts = (MenuManager) item;
					
					if(menuConnectedAccounts.getSize() == 1) {
						IContributionItem connectAccountItem = menuConnectedAccounts.getItems()[0];
						MenuManager menuConnectAccount = (MenuManager) connectAccountItem;
						
						for (IContributionItem menuItem : menuConnectAccount.getItems())
						{
							menuManager.add(menuItem);
						}
						
					} else {
						menuManager.add(menuConnectedAccounts);
					}
				} 
			}
		}
		
		return new IContributionItem[] {menuManager};
	}	
}