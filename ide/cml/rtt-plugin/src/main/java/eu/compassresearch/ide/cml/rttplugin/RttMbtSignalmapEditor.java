package eu.compassresearch.ide.cml.rttplugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class RttMbtSignalmapEditor extends EditorPart {

	// table to display the data
	private Table tableView;
	private int rows, columns;
	private TableEditor[][] editorArray;
	
	// file scanner initialised with input file
	private Scanner fileScanner;
	
	// signalmap.csv file with path
	private File output;
	
	private void notifyChanged() {
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}
	
	@Override
	// generate table and fill it with the content from the csv file
	public void createPartControl(Composite parent) {
		// create table
		tableView = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
	    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    tableView.setLinesVisible(true);
	    tableView.setHeaderVisible(true);
	    tableView.setLayoutData(data);
	    
		// header:
		Scanner line;
		line = new Scanner(fileScanner.nextLine());
		line.useDelimiter(";");
		columns = 0;
		while (line.hasNext()) {
			TableColumn column = new TableColumn(tableView, SWT.NONE);
			column.setText(line.next());
			tableView.getColumn(columns).pack();
			columns++;
		}
		line.close();

		// initialise content. 
		rows = 0;
		while (fileScanner.hasNextLine()) {
			line = new Scanner(fileScanner.nextLine());
			line.useDelimiter(";");
			TableItem item = new TableItem(tableView, SWT.NONE);
			int idx = 0;
			while (line.hasNext() && idx < columns) {
				String content = line.next();
				item.setText(idx, content);
				idx++;
			}
			rows++;
			line.close();
		}
		
		// add editors for table cells
		editorArray = new TableEditor[rows][columns];
		int row, column;
		for (row = 0; row < rows; row++) {
			TableItem item = tableView.getItem(row);			
			for (column = 0; column < columns; column++) {
				TableEditor editor = new TableEditor(tableView);
				editor.horizontalAlignment = SWT.LEFT;
				editor.grabHorizontal = true;
				editor.minimumWidth = 50;
			    Text cellEditor = new Text(tableView, SWT.NONE);
			    cellEditor.setText(item.getText(column));
			    cellEditor.addModifyListener(new ModifyListener() {
			        public void modifyText(ModifyEvent event) {
			        	Text source = (Text) event.getSource();
			            System.out.println("content changed to '" + source.getText() + "'");
			        	notifyChanged();
			        }});
			    editor.setEditor(cellEditor, item, column);
				editorArray[row][column] = editor;
			}
		}
	}

	@Override
	public void dispose() {
		// @todo: cleanup
	}

	@Override
	public String getTitle() {
		return "Test Procedure Signal Ranges";
	}

	@Override
	public String getTitleToolTip() {
		return "Defines ranges for signal values in this test procedure generation context";
	}

	@Override
	public void setFocus() {
		tableView.setFocus();
	}

	@Override
	public void doSave(IProgressMonitor arg0) {

		// if nothing is to be saved, exit.
		if (!isDirty()) return;
		
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

			// index variables
			int row, column;

			// write column headers
			for (column = 0; column < columns; column++) {
				TableColumn col = tableView.getColumn(column);
				bWriter.write(col.getText() + ";");
				System.out.print(col.getText() + ";");
			}
			bWriter.write("\n");
			System.out.print("\n");
			// got through all rows
			String tData, eData;
			for (row = 0; row < rows; row++) {
				TableItem item = tableView.getItem(row);
				for (column = 0; column < columns; column++) {

					// get data from table item
					tData = item.getText(column);

					// get Data from editor
					eData = tData;
					TableEditor editor = editorArray[row][column];
					if (editor != null) {
						Text cellEditor = (Text) editor.getEditor();
						if (cellEditor != null) {
							eData = cellEditor.getText();
						}
					}

					// copy data from editor to table item
					if (tData.compareTo(eData) != 0) {
						item.setText(column, eData);
						tData = eData;
					}
				
					// write data to file
					bWriter.write(tData + ";");
					System.out.print(tData + ";");
				}
				bWriter.write("\n");
				System.out.print("\n");
			}
			bWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// notify that content has changed (saved)
    	notifyChanged();
	}

	@Override
	public boolean isSaveAsAllowed() {
		// no save as is allowed for signalmap.csv
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
			IFile ifile = iFileInput.getFile();
			InputStream istream;
			try {
				istream = ifile.getContents();
				fileScanner = new Scanner(istream);
				// create output file for saving
		    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
				File workspaceDirectory = workspace.getRoot().getLocation().toFile();
				output = new File(workspaceDirectory.getAbsolutePath() + ifile.getFullPath().toString());
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
		
		// got through all rows
		int row, column;
		String tData, eData;
		for (row = 0; row < rows; row++) {
			TableItem item = tableView.getItem(row);
			for (column = 0; column < columns; column++) {

				// get data from table item
				tData = item.getText(column);

				// get Data from editor
				eData = tData;
				TableEditor editor = editorArray[row][column];
				if (editor != null) {
					Text cellEditor = (Text) editor.getEditor();
					if (cellEditor != null) {
						eData = cellEditor.getText();
					}
				}

				// compare values from table and editor
				if (tData.compareTo(eData) != 0) {
					System.out.println("item:   " + tData);
					System.out.println("editor: " + eData);
					return true;
				}
			}
		}
		return false;
	}

}
