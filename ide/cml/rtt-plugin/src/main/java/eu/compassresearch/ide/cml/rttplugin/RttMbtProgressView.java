package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class RttMbtProgressView extends ViewPart implements IViewPart {
    
    @Override
    public void addPropertyListener(IPropertyListener listener)
      {
        
      }
    
    @Override
    public void dispose()
      {
        
      }
    
    @Override
    public IWorkbenchPartSite getSite()
      {
        return super.getSite();
      }
    
    @Override
    public String getTitle()
      {
        return "Progress";
      }
    
    @Override
    public org.eclipse.swt.graphics.Image getTitleImage()
      {
        return super.getTitleImage();
      }
    
    @Override
    public String getTitleToolTip()
      {
        return "Progress information for RTT-MBT commands";
      }
    
    @Override
    public void removePropertyListener(IPropertyListener listener)
      {
      }
    
    @Override
    public void setFocus()
      {
      }
    
    @SuppressWarnings("rawtypes")
	@Override
    public Object getAdapter(Class adapter)
      {
        return null;
      }
    
    @Override
    public IViewSite getViewSite()
      {
        return super.getViewSite();
      }
    
    @Override
    public void init(IViewSite site) throws PartInitException
      {
        super.init(site);
      }
    
    @Override
    public void init(IViewSite site, IMemento memento) throws PartInitException
      {
        super.init(site, memento);
      }
    
    @Override
    public void saveState(IMemento memento)
      {
        super.saveState(memento);
        
      }
    
    @Override
    public void createPartControl(final org.eclipse.swt.widgets.Composite parent)
      {
    	// create text field used for console output
    	org.eclipse.swt.widgets.ProgressBar progBar = new ProgressBar(parent, SWT.NONE);

        // make console view accessable
        Activator.setProgressBar(progBar);

        // paint listener definition
        progBar.addPaintListener(new PaintListener()
          {
            @Override
            public void paintControl(PaintEvent e)
              {
              }
          });
      }

}
