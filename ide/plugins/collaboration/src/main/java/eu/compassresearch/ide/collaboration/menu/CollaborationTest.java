package eu.compassresearch.ide.collaboration.menu;

import java.util.Date;

import org.eclipse.jface.action.ContributionItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class CollaborationTest extends ContributionItem
{

	public CollaborationTest() {
		super();
	}
 
	public CollaborationTest(String id) {
		super(id);
	}
 
	public void fill(Menu menu, int index) {
		//Here you could get selection and decide what to do
		//You can also simply return if you do not want to show a menu
 
		//create the menu item
		MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
		menuItem.setText("Test menu item (" + new Date() + ")");
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//what to do when menu is subsequently selected.
				System.err.println("Dynamic menu selected");
			}
		});
	}
	
	@Override
	public boolean isDynamic() {
		return true;
	}
	
}
