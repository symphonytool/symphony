package eu.compassresearch.ide.cml.rttplugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RttMbtSignalViewOutlinePage extends ContentOutlinePage implements IContentOutlinePage {

	private JSONArray jsonSignals;
	private RttMbtTreeNodeLabelProvider labelProvider;
	private TreeNodeContentProvider contentProvider;
	private CheckboxTreeViewer treeView;
	private TreeNode[] components;
	private ISelectionChangedListener treeSelectionListener = null;
	
	public RttMbtSignalViewOutlinePage(Composite parent,
                                       JSONArray signals,
                                       RttMbtTreeNodeLabelProvider labels,
                                       TreeNodeContentProvider content) {
		jsonSignals = signals;
		labelProvider = labels;
		contentProvider = content;
	}
	
	@Override
	public void createControl(Composite parent) {
		System.out.println("RttMbtSignalViewOutlinePage::createControl");
		
		// create tree view content
		TreeNode[] signalNodes = createTreeNodes();

		// create tree view
		treeView = new CheckboxTreeViewer(parent);
		treeView.setContentProvider(contentProvider);
		treeView.setLabelProvider(labelProvider);
		treeView.setInput(signalNodes);
		treeView.setExpandedElements(signalNodes);
		treeView.setGrayedElements(components);
		if (treeSelectionListener != null) {
			treeView.addSelectionChangedListener(treeSelectionListener);
			treeSelectionListener = null;
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public Control getControl() {
		return treeView.getControl();
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
		if (treeView != null) {
			treeView.addSelectionChangedListener(listener);
		} else {
			treeSelectionListener = listener;
		}
	}

	@Override
	public ISelection getSelection() {
		return treeView.getSelection();
	}

	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		treeView.removeSelectionChangedListener(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		treeView.setSelection(selection);
	}

	public TreeNode[] createTreeNodes() {
		TreeNode rootNode = new TreeNode("Model Signals");
		// create list of all signals and tree nodes
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		List<TreeNode> parents = new ArrayList<TreeNode>();
		List<String> names = new ArrayList<String>();
		parents.add(rootNode);
		// select all signals
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonSignals.iterator();
		while (iterator.hasNext()) {
			// get JSONArray entry for the current signal
			JSONObject entry = iterator.next();
			String signalName = (String) entry.get("name");

			// check if signal is <signalName>_exe signal
			if ((signalName.lastIndexOf("_exe") == signalName.length() - 4) &&
				(hasSignalEntry(signalName.substring(0, signalName.length() - 4)))) {
				continue;
			} else {
				// add signal to signal selection dialog
				int pos = signalName.lastIndexOf('.') + 1;
				TreeNode signalNode = new TreeNode(signalName.substring(pos));
				signalNode.setParent(getParent(parents, signalName));
				nodes.add(signalNode);
				names.add(signalName);
			}
		}
		// prepare signal selection dialog content
		updateParentNodeChilds(parents, nodes);
        String[] signalNames = new String[names.size()];
        for (int i = 0; i < names.size(); i++) { signalNames[i] = names.get(i); }
        TreeNode[] childNodes = new TreeNode[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) { childNodes[i] = nodes.get(i); }
		//rootNode.setChildren(childNodes);

		// return the signals
        TreeNode[] signalNodes = new TreeNode[1];
        signalNodes[0] = rootNode;
		return signalNodes;
	}
	
	public Boolean hasSignalEntry(String signalName) {
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonSignals.iterator();
		while (iterator.hasNext()) {
			JSONObject entry = iterator.next();
			String name = (String) entry.get("name");
			if (signalName.compareTo(name) == 0) {
				return true;
			}
		}
		return false;
	}

	TreeNode getParent(List<TreeNode> parents, String signalName) {
		TreeNode lastParent = null;

		// check arguments
		if ((parents == null) || (signalName == null)) {
			return null;
		}

		// retrieve root node
		lastParent = parents.get(0);

		// check for '.' in signal name
		int start = 0;
		int pos = signalName.indexOf('.');
		while (pos > 0) {
			// extract parent name
			String parentName = signalName.substring(start, pos);
			TreeNode parentNode = null;
			// search for parent in list of parents
			for (int idx = 0; idx < parents.size(); idx++) {
				if (((String) parents.get(idx).getValue()).compareTo(parentName) == 0) {
					parentNode = parents.get(idx);
				}
			}
			if (parentNode == null) {
				parentNode = new TreeNode(parentName);
				parentNode.setParent(lastParent);
				parents.add(parentNode);
			}
			// prepare next loop
			lastParent = parentNode;
			start = pos + 1;
			pos = signalName.indexOf('.', start);
		}
		return lastParent;
	}
	
	void updateParentNodeChilds(List<TreeNode> parents, List<TreeNode> children) {
		// check arguments
		if (parents == null) {
			return;
		}
		// create list of components (will be grayed out in the tree view
		components = new TreeNode[parents.size()];
		// for each parent node, create a list of all child nodes of this parent
		for (int idx = 0; idx < parents.size(); idx++) {
			TreeNode parentNode = parents.get(idx);
			List<TreeNode> currentChildren = new ArrayList<TreeNode>();
			// go through all parent nodes and collect all nodes that are child nodes of this parent
			for (int childIdx = 0; childIdx < parents.size(); childIdx++) {
				if (parents.get(childIdx).getParent() == parentNode) {
					currentChildren.add(parents.get(childIdx));
				}
			}
			// go through all signal nodes and collect all nodes that are child nodes of this parent
			for (int childIdx = 0; childIdx < children.size(); childIdx++) {
				if (children.get(childIdx).getParent() == parentNode) {
					currentChildren.add(children.get(childIdx));
				}
			}
			if (currentChildren.size() > 0) {
				TreeNode[] childNodes = new TreeNode[currentChildren.size()];
				for (int index = 0; index < currentChildren.size(); index++) {
					childNodes[index] = currentChildren.get(index);
				}
				parentNode.setChildren(childNodes);
			}
			// add to components
			components[idx] = parentNode;
		}
	}
	
	public void setInput(JSONArray signals) {
		jsonSignals = signals;
		treeView.setInput(createTreeNodes());
	}

	private String getFullName(TreeNode node) {
		String name = (String) node.getValue();
		TreeNode current = node.getParent();
		while (current != null) {
			if (current.getParent() != null) {
				name = ((String) current.getValue()) + "." + name;
			}
			current = current.getParent();
		}
		return name;
	}
	
	public String[] getSelectedSignals() {
		Object[] checkedObjects = treeView.getCheckedElements();
		String[] selectedSignals = new String[checkedObjects.length];
		for (int idx = 0; idx < checkedObjects.length; idx++) {
			TreeNode node = (TreeNode)checkedObjects[idx];
			selectedSignals[idx] = getFullName(node);
		}
		return selectedSignals;
	}
}
