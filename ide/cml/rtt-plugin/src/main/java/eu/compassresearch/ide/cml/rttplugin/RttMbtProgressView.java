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

import eu.compassResearch.rttMbtTmsClientApi.IRttMbtProgressBar;

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
    	// create progress bars
    	ProgressBar progBar = new ProgressBar(parent, SWT.NONE);
    	ProgressBar tc_cov = new ProgressBar(parent, SWT.NONE);
    	ProgressBar bcs_cov = new ProgressBar(parent, SWT.NONE);
    	ProgressBar tr_cov = new ProgressBar(parent, SWT.NONE);
    	ProgressBar mcdc_cov = new ProgressBar(parent, SWT.NONE);

    	progBar.setToolTipText("Overall Progress");
    	tc_cov.setToolTipText("Test Case Coverage");
    	tr_cov.setToolTipText("Transition Coverage");
    	bcs_cov.setToolTipText("BCS Coverage");
    	mcdc_cov.setToolTipText("MCDC Coverage");
    	
        // make console view accessable
        Activator.addProgressBar(IRttMbtProgressBar.Tasks.Global, progBar);
        Activator.addProgressBar(IRttMbtProgressBar.Tasks.TC_COV, tc_cov);
        Activator.addProgressBar(IRttMbtProgressBar.Tasks.BCS_COV, bcs_cov);
        Activator.addProgressBar(IRttMbtProgressBar.Tasks.TR_COV, tr_cov);
        Activator.addProgressBar(IRttMbtProgressBar.Tasks.MCDC_COV, mcdc_cov);

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
