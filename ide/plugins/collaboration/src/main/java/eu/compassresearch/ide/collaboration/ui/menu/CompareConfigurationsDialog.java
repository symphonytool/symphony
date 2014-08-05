package eu.compassresearch.ide.collaboration.ui.menu;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.File;
import eu.compassresearch.ide.collaboration.files.FileStatus;

public class CompareConfigurationsDialog extends Dialog
{
	private final List<FileStatus> compareResult;
	private final Configuration configuration;
	private Table t;

	public CompareConfigurationsDialog(Configuration configuration, List<FileStatus> compareResult,
			Shell shell)
	{
		super(shell);
		this.configuration = configuration;
		this.compareResult = compareResult;
	}
	
	@Override
	public void create()
	{
		super.create();
		
	}
	
	@Override
	protected void configureShell(Shell shell) {
	      super.configureShell(shell);
	      shell.setText("Configuration Comparison");
	   }
	
	@Override
	protected void createButtonsForButtonBar (Composite parent)
	{
	createButton (parent, IDialogConstants.OK_ID, "Ok", true) ;
	}

	
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite area = (Composite) super.createDialogArea(parent);
		area.setLayout(new GridLayout(1, false));
		
		t = new Table(area, SWT.BORDER);
		t.setHeaderVisible(true);

		TableColumn tcFile = new TableColumn(t, SWT.CENTER);
		tcFile.setText("File");
		tcFile.setWidth(200);
		TableColumn tcStatus = new TableColumn(t, SWT.CENTER);
		tcStatus.setText("Status");
		tcStatus.setWidth(200);
		
		t.addMouseListener(new MouseListener()
		{
			public void mouseDoubleClick(MouseEvent e)
			{
				int index = t.getSelectionIndex();
		           if (index == -1) 
		             return; //no row selected

		           TableItem item = t.getItem(index);  
		           item.getData();

		           File file = configuration.getFile(item.getText());
		           
		           CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();
		           CollaborationDialogs.getCollaborationView().compareFileWithPrev(dataModelManager, file);
			}
			
			public void mouseDown(MouseEvent e)
			{
			}
			public void mouseUp(MouseEvent e)
			{
			}
	 
		});
		
		TableItem ti;
		for (FileStatus status : compareResult)
		{
			ti = new TableItem(t, SWT.NONE);
			ti.setText(new String[]{status.getFileName(),status.getState().toString()});
		}
		
		
		
		return area;
	}
}
