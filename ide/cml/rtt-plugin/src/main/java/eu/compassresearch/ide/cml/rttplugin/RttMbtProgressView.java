package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
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
    	GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        parent.setLayout(layout);

        //RowLayout layout = new RowLayout(SWT.VERTICAL);
    	//parent.setLayout(layout);

        // create grid data
    	GridData gridData = new GridData();
    	gridData.horizontalAlignment = GridData.FILL;
    	gridData.grabExcessHorizontalSpace = true;
        
    	// create progress bars
    	Label captionGlobal = new Label(parent, SWT.NONE);
    	captionGlobal.setText("Total: ");
    	ProgressBar progBar = new ProgressBar(parent, SWT.NONE);
    	progBar.setLayoutData(gridData);

    	Label captionGoal = new Label(parent, SWT.NONE);
    	captionGoal.setText("Goals: ");
    	ProgressBar goals = new ProgressBar(parent, SWT.NONE);
    	goals.setLayoutData(gridData);

    	Label captionTC = new Label(parent, SWT.NONE);
    	captionTC.setText("TC Coverage: ");
    	ProgressBar tc_cov = new ProgressBar(parent, SWT.NONE);
    	tc_cov.setLayoutData(gridData);

    	Label captionBCS = new Label(parent, SWT.NONE);
    	captionBCS.setText("BCS Coverage: ");
    	ProgressBar bcs_cov = new ProgressBar(parent, SWT.NONE);
    	bcs_cov.setLayoutData(gridData);

    	Label captionTR = new Label(parent, SWT.NONE);
    	captionTR.setText("TR Coverage: ");
    	ProgressBar tr_cov = new ProgressBar(parent, SWT.NONE);
    	tr_cov.setLayoutData(gridData);

    	Label captionMCDC = new Label(parent, SWT.NONE);
    	captionMCDC.setText("MCDC coverage: ");
    	ProgressBar mcdc_cov = new ProgressBar(parent, SWT.NONE);
    	mcdc_cov.setLayoutData(gridData);

    	progBar.setToolTipText("Overall Progress");
    	tc_cov.setToolTipText("Test Case Coverage");
    	tr_cov.setToolTipText("Transition Coverage");
    	bcs_cov.setToolTipText("BCS Coverage");
    	mcdc_cov.setToolTipText("MCDC Coverage");
    	
        // make console view accessable
        Activator.addProgressBar(IRttMbtProgressBar.Tasks.Global, progBar);
        Activator.addProgressBar(IRttMbtProgressBar.Tasks.Goal, goals);
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
