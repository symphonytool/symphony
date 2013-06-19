package eu.compassresearch.ide.cml.rttplugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class RttMbtConfigEditor extends EditorPart {

	private enum state_t {
		Checked,
		Grayed,
	    Unchecked,
	    Undefined
	}

	// widget to display the data
	private Tree treeView;
	
	// table to display the data
	private int columns;
	private List<List<TreeEditor>> editors;
	private Map<TreeItem,List<TreeEditor>> items2editors;
	private List<String[]> fileContent;
	private List<String> headerLine;
	
	// file scanner initialised with input file
	private Scanner fileScanner;
	
	// signalmap.csv file with path
	private File output;
	private IFile iFile;
	
	private void notifyChanged(SelectionEvent e, TreeItem item, int column) {
		// propagate item state to parents and children
		propagateSelectionChange(item, column);
		// fire property dirty event
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}

	// get the state of the item
	private state_t getItemState(TreeItem item, int column) {
		state_t state = state_t.Undefined;
		// get editor for this item
		List<TreeEditor> lineEditors = items2editors.get(item);
		TreeEditor edit = lineEditors.get(column);
		Button checkBox = (Button) edit.getEditor();
		boolean grayed = checkBox.getGrayed();
		boolean checked = checkBox.getSelection();
		
		// debug output
		System.out.print(item.getText() + ", column #" + column + ", ");
		if (checked && !grayed) {
			System.out.print( "checked");
			state = state_t.Checked;
		}
		if (grayed) {
			System.out.print("greyed");
			state = state_t.Grayed;
		}
		if (!checked && !grayed) {
			System.out.print("unchecked");
			state = state_t.Unchecked;
		}
		System.out.print("\n");
		return state;
	}

	private void setItemState(TreeItem item, int column, state_t state) {
		// get editor for this item
		List<TreeEditor> lineEditors = items2editors.get(item);
		TreeEditor edit = lineEditors.get(column);
		Button checkBox = (Button) edit.getEditor();
		switch(state) {
		case Checked:
			System.out.println("set item " + item.getText() + " checked");
			checkBox.setGrayed(false);
			checkBox.setSelection(true);
			break;
		case Unchecked:
			System.out.println("set item " + item.getText() + " unchecked");
			checkBox.setGrayed(false);
			checkBox.setSelection(false);
			break;
		case Grayed:
			System.out.println("set item " + item.getText() + " grayed");
			checkBox.setSelection(true);
			checkBox.setGrayed(true);
			break;
		case Undefined:
			break;
		}
	}

	private void updateSomeChildItemsChecked(TreeItem item, int column) {
		if (item == null) return;
		TreeItem[] children = item.getItems();
		if (children.length == 0) return;
		Boolean allChecked = true;
		Boolean noneChecked = true;
		for (int cidx = 0; cidx < children.length; cidx++) {
			// recursively update state for child item
			updateSomeChildItemsChecked(children[cidx], column);
			state_t state = getItemState(children[cidx], column);
			if (state == state_t.Checked) {
				noneChecked = false;
			} else if (state == state_t.Grayed) {
				noneChecked = false;
				allChecked = false;
			} else {
				allChecked = false;
			}
		}
		if (allChecked) {
			System.out.println("all siblings checked");
			setItemState(item, column, state_t.Checked);
		} else if (noneChecked) {
			System.out.println("all siblings unchecked");
			setItemState(item, column, state_t.Unchecked);
		} else {
			System.out.println("some siblings checked");
			setItemState(item, column, state_t.Grayed);
		}
	}
	
	// propagate to parents
	private void propagateSelectionChangeToParents(TreeItem item, int column) {
		if (item == null) {
			return;
		}
		// propagate to parent
		TreeItem parent = item.getParentItem();
		if (parent == null) {
			return;
		}

		TreeItem[] children = null;
		children = parent.getItems();
		System.out.println("parent " + parent.getText() + " has " + children.length + " child items");
		// check if all children are checked -> parent = checked
		// check if some children are checked -> parent = grayed
		// parent = unchecked, otherwise
		Boolean allChecked = true;
		Boolean noneChecked = true;
		for (int cidx = 0;
				cidx < children.length && (allChecked || noneChecked);
				cidx++) {
			state_t state = getItemState(children[cidx], column);
			if (state == state_t.Checked) {
				noneChecked = false;
			} else if (state == state_t.Grayed) {
				noneChecked = false;
				allChecked = false;
			} else {
				allChecked = false;
			}
		}
		if (allChecked) {
			System.out.println("all siblings checked");
			setItemState(parent, column, state_t.Checked);
		} else if (noneChecked) {
			System.out.println("all siblings unchecked");
			setItemState(parent, column, state_t.Unchecked);
		} else {
			System.out.println("some siblings checked");
			setItemState(parent, column, state_t.Grayed);
		}
		// recursively propagate to parent of this parent item
		propagateSelectionChangeToParents(parent, column);
	}

	private void propagateSelectionChangeToChildren(TreeItem item, int column) {
		// propagate to children
		state_t itemState = getItemState(item, column);
		TreeItem[] children = item.getItems();
		// if checked -> set all children checked
		// if not checked -> set all children unchecked
		// if grayed -> do nothing
		if (itemState == state_t.Checked || itemState == state_t.Unchecked) {
			for (int cidx = 0; cidx < children.length; cidx++) {
				setItemState(children[cidx], column, itemState);
				propagateSelectionChangeToChildren(children[cidx], column);
			}
		}
	}
	// propagate the new state of a tree item to its parents or children
	private void propagateSelectionChange(TreeItem item, int column) {
		if (item != null) {

			// if a state changes from unchecked to grayed, it should change to selected
			state_t itemState = getItemState(item, column);
			if (itemState == state_t.Grayed) itemState = state_t.Checked;
			setItemState(item, column, itemState);

			// propagate to parent
			propagateSelectionChangeToParents(item, column);
			
			// propagate to children
			propagateSelectionChangeToChildren(item, column);

		}
	}
	
	// generate tree view and fill it with the content from the csv file
	@Override
	public void createPartControl(Composite parent) {
		treeView = new Tree(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		treeView.setHeaderVisible(true);
		
		// header:
		String[] headers = {"Component", "CT", "Allocation", "TC", "SC", "SIM", "DEACT", "ROB"};
		Scanner line;
		for (int idx = 0; idx < headers.length; idx++) {
			TreeColumn column = new TreeColumn(treeView, SWT.LEFT);
			column.setText(headers[idx]);
			column.setResizable(idx == 0);
			treeView.getColumn(columns).pack();
		}
		
		// read first line (for saving later)
		if (fileScanner == null) {
			System.err.println("*** error: opening file failed!");
			return;
		}
		headerLine = new ArrayList<String>();
		line = new Scanner(fileScanner.nextLine());
		line.useDelimiter(";");
		columns = 0;
		while (line.hasNext()) {
			headerLine.add(line.next());
			columns++;
		}
		line.close();

		// initialise content.
		List<TreeItem> parents = new ArrayList<TreeItem>();
		editors = new ArrayList<List<TreeEditor>>();
		items2editors = new HashMap<TreeItem,List<TreeEditor>>();
		fileContent = new ArrayList<String[]>();
		TreeItem lastParent = null;
		TreeItem rootItem = null;
		while (fileScanner.hasNextLine()) {
			line = new Scanner(fileScanner.nextLine());
			line.useDelimiter(";");

			// scan line
			int idx = 0;
			String[] cells = new String[columns];
			while (line.hasNext() && idx < columns) {
				cells[idx] = line.next();
				idx++;
			}
			if (idx < columns) continue;

			// store line for saving later
			fileContent.add(cells);

			// create tree item
			TreeItem item = null;
			if (lastParent == null) {
				item = new TreeItem(treeView, SWT.NONE);
				parents.add(item);
				lastParent = item;
				rootItem = item;
			} else {
				String condAction = cells[6];
				if (condAction.compareTo("-") != 0) {
					item = new TreeItem(lastParent, SWT.NONE);					
				} else {
					String name = cells[3];
					int lastIdx = name.lastIndexOf('.');
					if (lastIdx == -1) {
						item = new TreeItem(treeView, SWT.NONE);
						parents.add(item);
						lastParent = item;
					} else {
						name = name.substring(0, lastIdx);
						for (int hidx = 0; hidx < parents.size() && item == null; hidx++) {
							String pname = parents.get(hidx).getText(0);
							if (pname.compareTo(name) == 0) {
								item = new TreeItem(parents.get(hidx), SWT.NONE);
								parents.add(item);
								lastParent = item;
							}
						}
						if (item == null) {
							System.err.println("*** error: unable to find parent item for component '" + cells[3] + "'");
							item = new TreeItem(treeView, SWT.NONE);
						}
					}
				}
			}

			// set background to green if covered
			if (cells[0].compareTo("X") == 0) {
				item.setBackground(new Color(Display.getDefault(), new RGB(0,255,0)));
			}

			// fill content of item
			List<TreeEditor> itemEditors = new ArrayList<TreeEditor>();
			for (int column = 0; column < headers.length; column++) {
				int index = treeColumn2CsvIdx(column);
				String text = cells[index];
				if ((column == 0) && (text.compareTo("-") == 0)) {
					text = cells[3];
				}
				if (column < 3) {
					item.setText(column, text);					
				} else {
					final TreeItem checkItem = item;
					final int checkColumn = column - 3;
					TreeEditor editor = new TreeEditor(treeView);
					editor.horizontalAlignment = SWT.LEFT;
					editor.minimumWidth = 15;
				    Button cellEditor = new Button(treeView, SWT.CHECK);
				    cellEditor.setSelection(text.compareTo("X") == 0);
				    cellEditor.setBackground(item.getBackground());
				    cellEditor.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) { notifyChanged(e, checkItem, checkColumn); }

						@Override
						public void widgetDefaultSelected(SelectionEvent e) { notifyChanged(e, checkItem, checkColumn); }
						});
				    editor.setEditor(cellEditor, item, column);
				    itemEditors.add(editor);
				}
				treeView.getColumn(column).pack();
			}
			editors.add(itemEditors);
			items2editors.put(item, itemEditors);
			line.close();
		}
		for (int colIndex = 0; colIndex < (headers.length - 3); colIndex++) {
			updateSomeChildItemsChecked(rootItem, colIndex);			
		}

		// expand all items
		for (int idx = 0; idx < parents.size(); idx++) {
			parents.get(idx).setExpanded(true);
		}
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
	// save the content to configuration.csv
	public void doSave(IProgressMonitor arg0) {
		// if nothing is to be saved, exit.
		if (!isDirty()) return;
		
		// if data is inconsitent: do not save anything
		if (editors.size() < fileContent.size()) {
			System.err.println("*** error: number of editor rows (" + editors.size() +
							   ") does not match number of csv file rows (" + fileContent.size() + ")");
			return;
		}

		// create output writer
		if (output == null) {
			System.err.println("*** error: unable to create Writer for output file null");
			return;
		}
		Writer writer = null;
		try {
			// create output stream writer
			writer = new FileWriter(output);
			BufferedWriter bWriter = new BufferedWriter(writer);

			// write header to file
			for (int idx = 0; idx < headerLine.size(); idx++) {
				bWriter.write(headerLine.get(idx));
				System.out.print(headerLine.get(idx));
				if (idx < headerLine.size() - 1) {
					bWriter.write(";");
					System.out.print(";");
				}
			}
			bWriter.write("\n");
			System.out.print("\n");

			for (int row = 0; row < fileContent.size(); row++) {
				String[] line = fileContent.get(row);
				List<TreeEditor> lineEditors = editors.get(row);
				for (int idx = 0; idx < line.length; idx++) {
					String content = line[idx];
					if (csvIdx2TreeColumn(idx) >= 3) {
						// compare content
						boolean wasChecked = content.compareTo("X") == 0;
						int eIdx = csvIdx2TreeColumn(idx) - 3;
						TreeEditor edit = lineEditors.get(eIdx);
						Button checkBox = (Button) edit.getEditor();
						boolean isChecked = (checkBox.getSelection() && (!checkBox.getGrayed()));
						if (isChecked != wasChecked) {
							// set file content according to check box
							if (isChecked) {
								line[idx] = "X";
							} else {
								line[idx] = "-";							
							}
							fileContent.remove(row);
							fileContent.add(row, line);
						}
					}
					// write data to file
					bWriter.write(line[idx] + ";");
					System.out.print(line[idx] + ";");
				}
				bWriter.write("UNCHANGED\n");
				System.out.print("UNCHANGED\n");
			}
			bWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// notify that content has changed (saved)
    	notifyChanged(null, null, -1);
    	try {
			iFile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		// no save as is allowed for configuration.csv
		return false;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// - is called shortly after editor construction
		// - This marks the start of the editor lifecycle
		if (! (input instanceof IFileEditorInput) ) {
			throw new PartInitException("Invalid input! Expected file input");
		} else {
			IFileEditorInput iFileInput = (IFileEditorInput) input;
			iFile = iFileInput.getFile();
			InputStream istream;
			try {
				istream = iFile.getContents();
				fileScanner = new Scanner(istream);
				// create output file for saving
		    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
				File workspaceDirectory = workspace.getRoot().getLocation().toFile();
				String filename = workspaceDirectory.getAbsolutePath() + iFile.getFullPath().toString();
				output = new File(filename);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		setSite(site);
		setInput(input);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isDirty() {
		if (editors == null) {
			return false;
		}
		if (editors.size() < fileContent.size()) {
			System.err.println("*** error: number of editor rows (" + editors.size() +
							   ") does not match number of csv file rows (" + fileContent.size() + ")");
			return false;
		}
		for (int row = 0; row < fileContent.size(); row++) {
			String[] line = fileContent.get(row);
			List<TreeEditor> lineEditors = editors.get(row);
			for (int idx = 0; idx < line.length; idx++) {
				String content = line[idx];
				if (csvIdx2TreeColumn(idx) >= 3) {
					// compare content
					boolean wasChecked = content.compareTo("X") == 0;
					int eIdx = csvIdx2TreeColumn(idx) - 3;
					TreeEditor edit = lineEditors.get(eIdx);
					Button checkBox = (Button) edit.getEditor();
					boolean isChecked = (checkBox.getSelection() && (!checkBox.getGrayed()));
					if (isChecked != wasChecked) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private int csvIdx2TreeColumn(int idx) {
		switch(idx) {
		case 4:
			return 1;
		case 5:
			return 2;
		case 6:
			return 0;
		case 9:
			return 3;
		case 10:
			return 4;
		case 11:
			return 7;
		case 13:
			return 5;
		case 14:
			return 6;
		}
		
		return -1;
	}
	
	private int treeColumn2CsvIdx(int column) {
		switch(column) {
		case 0:
			return 6;
		case 1:
			return 4;
		case 2:
			return 5;
		case 3:
			return 9;
		case 4:
			return 10;
		case 5:
			return 13;
		case 6:
			return 14;
		case 7:
			return 11;
		}
		
		return -1;
	}

}
