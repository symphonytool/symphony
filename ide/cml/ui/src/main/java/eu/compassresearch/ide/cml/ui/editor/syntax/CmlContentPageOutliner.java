package eu.compassresearch.ide.cml.ui.editor.syntax;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlContentPageOutliner extends ContentOutlinePage implements
    IContentOutlinePage
  {
    
    private CmlSourceUnit          input;
    private CmlTreeContentProvider provider;
    
    public void refresh()
      {
        final Display curDisp = Display.getDefault();
        if (curDisp != null)
          curDisp.syncExec(new Runnable()
            {
              public void run()
                {
                  getTreeViewer().refresh();
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
        // viewer.setLabelProvider(new CmlLabelProvider());
        viewer.addSelectionChangedListener(this);
        viewer.setInput(input);
      }
    
    public void setInput(CmlSourceUnit input)
      {
        this.input = input;
      }
    
  }
