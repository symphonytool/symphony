package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;

public class RttMbtTreeNodeLabelProvider implements ILabelProvider {


	@Override
	public String getText(Object element) {
		if (element instanceof TreeNode) {
            return ((TreeNode) element).getValue().toString();
		} else {
		    return "Invalid element class!";
		}
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}
}
