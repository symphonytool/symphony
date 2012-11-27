package eu.compassresearch.ide.cml.interpreter_plugin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class CmlInterpreterView extends ViewPart implements IViewPart
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
        return "Cml Interpreter";
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
        org.eclipse.swt.widgets.Canvas canvas = new Canvas(parent, SWT.NONE);
        canvas.addPaintListener(new PaintListener()
          {
            @Override
            public void paintControl(PaintEvent e)
              {
                Canvas canvas = ((Canvas) e.widget);
                Rectangle rect = canvas.getBounds();
                e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
                e.gc.drawFocus(5, 5, rect.width - 10, rect.height - 10);
                e.gc.drawText("You can draw text directly on a canvas", 60, 60);
                e.gc.drawText("Visit: http://www.compassresearch.eu/", 60, 90);
                canvas.setBackground(new Color(parent.getDisplay(), 0, 213, 220));
              }
          });
      }
  }
