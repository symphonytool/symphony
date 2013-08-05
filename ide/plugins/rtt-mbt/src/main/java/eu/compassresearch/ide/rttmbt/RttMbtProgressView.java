package eu.compassresearch.ide.rttmbt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtProgressView extends ViewPart {

	public RttMbtProgressView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
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

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
