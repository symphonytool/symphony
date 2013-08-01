package eu.compassresearch.ide.cml.mcplugin;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import eu.compassresearch.ide.ui.CmlUIPlugin;

public class MCPerspectiveFactory implements IPerspectiveFactory {
	private IPageLayout layout;
	
	@Override
	public void createInitialLayout(IPageLayout layout) {
		this.layout = layout;
		addViews();
	}
	private void addViews(){
		
		String editorArea = layout.getEditorArea();

		IFolderLayout leftFolder= layout.createFolder("left", IPageLayout.LEFT, (float)0.2, editorArea); //$NON-NLS-1$
		leftFolder.addView("eu.compassresearch.ide.ui.CmlNavigator");
		
		IFolderLayout bottomFolder = layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.75, editorArea); //$NON-NLS-1$
		if (CmlUIPlugin.DEBUG)
			bottomFolder.addView("org.eclipse.pde.runtime.LogView");

		bottomFolder.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottomFolder.addView(IPageLayout.ID_TASK_LIST);
		bottomFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);

		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, (float) 0.75, editorArea);		
	}

	
	public void defineActions(IPageLayout layout) {
		// Add "new wizards".
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");
		layout.addNewWizardShortcut("eu.compassresearch.ide.ui.newcmlfilewizard");
		layout.addNewWizardShortcut("eu.compassresearch.ide.ui.newcmlclasswizard");
		layout.addNewWizardShortcut("eu.compassresearch.ide.ui.newcmlprocesswizard");

		// Add "show views".
		//layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
	}

	public void defineLayout(IPageLayout layout) {
		// Editors are placed for free.
		String editorArea = layout.getEditorArea();

		// Place navigator and outline to left of
		// editor area.
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT,
				(float) 0.25, editorArea);
		left.addView("eu.compassresearch.ide.ui.CmlNavigator");
		//IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, (float) 0.60, editorArea);
		//right.addView("eu.compassresearch.ide.mcplugin.GraphListView");
		//IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.50, editorArea);
		//bottom.addView("eu.compassresearch.ide.mcplugin.GraphListView");
	}
}