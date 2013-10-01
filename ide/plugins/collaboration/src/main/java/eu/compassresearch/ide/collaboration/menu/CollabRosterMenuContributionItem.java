package eu.compassresearch.ide.collaboration.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ecf.presence.IPresenceContainerAdapter;
import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.ui.menu.AbstractRosterMenuContributionItem;
import org.eclipse.ecf.presence.ui.menu.AbstractRosterMenuHandler;
import org.eclipse.jface.action.*;

import eu.compassresearch.ide.collaboration.notifications.Notification;



public class CollabRosterMenuContributionItem extends
		AbstractRosterMenuContributionItem
{

	public CollabRosterMenuContributionItem()
	{
		super();
		setTopMenuName(Notification.CollabRosterMenuContributionItem_MAIN_MENU);
	}
	
	public CollabRosterMenuContributionItem(String id)
	{
		super(id);
		setTopMenuName(Notification.CollabRosterMenuContributionItem_MAIN_MENU);
	}
	
	@Override
	protected AbstractRosterMenuHandler createRosterEntryHandler(
			IRosterEntry rosterEntry)
	{
		return new CollabMenuRosterMenuHandler(rosterEntry);
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
			for (int i = 0; i < contributions.length; i++)
			{
				menuManager.add(contributions[i]);
			}
		}
		
		return new IContributionItem[] {new Separator(), menuManager};
	}	
}