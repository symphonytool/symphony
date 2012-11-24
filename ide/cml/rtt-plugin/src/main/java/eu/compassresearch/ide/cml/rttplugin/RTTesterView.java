package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class RTTesterView extends ViewPart implements IViewPart
  {
    
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
        // TODO Auto-generated method stub
        return "RTT-MBT";
      }
    
    @Override
    public org.eclipse.swt.graphics.Image getTitleImage()
      {
        // TODO Auto-generated method stub
        return super.getTitleImage();
      }
    
    @Override
    public String getTitleToolTip()
      {
        // TODO Auto-generated method stub
        return "Nothing";
      }
    
    @Override
    public void removePropertyListener(IPropertyListener listener)
      {
        // TODO Auto-generated method stub
        
      }
    
    @Override
    public void setFocus()
      {
        // TODO Auto-generated method stub
        
      }
    
    @SuppressWarnings("rawtypes")
	@Override
    public Object getAdapter(Class adapter)
      {
        // TODO Auto-generated method stub
        return null;
      }
    
    @Override
    public IViewSite getViewSite()
      {
        // TODO Auto-generated method stub
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
    	org.eclipse.swt.widgets.Text console = new Text(parent, SWT.V_SCROLL);

        // make console view accessable
        Activator.setConsole(console);

        // paint listener definition
        console.addPaintListener(new PaintListener()
          {
            @Override
            public void paintControl(PaintEvent e)
              {
              }
          });
      }
  }
