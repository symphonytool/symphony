package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.Iterator;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;


import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlContentPageOutliner extends ContentOutlinePage implements
    IContentOutlinePage
  {
    
    private CmlSourceUnit          input;
    private CmlTreeContentProvider provider;
    private OutlineLabelProvider labelprovider;
	//public static final int ALL_LEVELS = -1;
    
    public void refresh()
      {
        final Display curDisp = Display.getDefault();
        if (curDisp != null)
          curDisp.syncExec(new Runnable()
            {
              public void run()
                {
                  getTreeViewer().refresh();
                  getTreeViewer().expandAll();
                }
            });
        
      }
    
    public CmlContentPageOutliner()
      {
        provider = new CmlTreeContentProvider(this.getControl());
        
      }
    
    @Override
    public void createControl(Composite parent)
      {
        super.createControl(parent);
        TreeViewer viewer = getTreeViewer();
        viewer.setContentProvider(provider);
        viewer.setUseHashlookup(true);
        //viewer.addSelectionChangedListener(listener)
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				// if the selection is empty clear the label
				if(event.getSelection().isEmpty()) {
					System.out.println("Empty Selection");
					return;
				}
				if(event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					StringBuffer toShow = new StringBuffer();
					for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
						System.out.println("Selected " + iterator.next().toString());
						
//						Object domain = (Model) iterator.next();
	//					String value = labelProvider.getText(domain);
		//				toShow.append(value);
			//			toShow.append(", ");
					}
					// remove the trailing comma space pair
					if(toShow.length() > 0) {
						toShow.setLength(toShow.length() - 2);
					}
				//	text.setText(toShow.toString());
				}
			}
		});
        
        //FIXME ldc -Need to add Labels
		labelprovider = new OutlineLabelProvider();
        viewer.setLabelProvider(labelprovider);
        viewer.addSelectionChangedListener(this);
        viewer.setInput(input);
        viewer.expandAll();
      }
    
    public void setInput(CmlSourceUnit input)
      {
        this.input = input;
      }
    
  }
