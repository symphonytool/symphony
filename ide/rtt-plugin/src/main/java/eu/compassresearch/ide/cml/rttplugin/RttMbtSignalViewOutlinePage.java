package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class RttMbtSignalViewOutlinePage implements IContentOutlinePage {

	private Tree treeView;
	private String[] signalNames;

	public RttMbtSignalViewOutlinePage(String[] signals) {
		signalNames = signals;		
	}
	
	@Override
	public void createControl(Composite parent) {
		System.out.println("RttMbtSignalViewOutlinePage::createControl");
		treeView = new Tree(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		TreeItem rootItem = new TreeItem(treeView, SWT.CHECK);
		rootItem.setText("Signals");
		if (signalNames == null) {
			System.out.println("no signal names available!");
			return;
		}
		for (int idx = 0; idx < signalNames.length; idx++) {
			if (signalNames[idx] != null) {
				TreeItem item = new TreeItem(treeView, SWT.CHECK);
				item.setText(signalNames[idx]);
				System.out.println("added TreeItem for signal " + item.getText());
			}
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public Control getControl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionBars(IActionBars actionBars) {
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ISelection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
	}

	@Override
	public void setSelection(ISelection selection) {
	}

}
