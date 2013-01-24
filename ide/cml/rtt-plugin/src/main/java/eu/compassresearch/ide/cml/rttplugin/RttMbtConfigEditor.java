package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class RttMbtConfigEditor extends EditorPart {

	private Tree treeView;
	
	@Override
	public void addPropertyListener(IPropertyListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPartControl(Composite parent) {
		// @todo: generate treeview here and fill it with the content from the csv file
		treeView = new Tree(parent, SWT.NONE);
	}

	@Override
	public void dispose() {
		// @todo: cleanup
		
	}

	@Override
	public String getTitle() {
		return "Test Procedure Advanced Configuration";
	}

	@Override
	public String getTitleToolTip() {
		return "Defines the advanced configuration of test procedure generation context";
	}

	@Override
	public void setFocus() {
		treeView.setFocus();
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
		// @todo: save the content to configuration.csv
		
	}

	@Override
	public boolean isSaveAsAllowed() {
		// no save as is allowed for configuration.csv
		return false;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// @todo: initialise content. 
		// - is called shortly after editor construction
		// - This marks the start of the editor lifecycle
		if (! (input instanceof IFileEditorInput) ) {
			throw new PartInitException("Invalid input! Expected file input");
		}
		setSite(site);
		setInput(input);
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

}
