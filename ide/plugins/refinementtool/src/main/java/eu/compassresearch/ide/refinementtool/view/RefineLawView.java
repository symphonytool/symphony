package eu.compassresearch.ide.refinementtool.view;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.refinementtool.IRefineLaw;

public class RefineLawView extends ViewPart implements ISelectionListener
{

	List<IRefineLaw> laws = null;
	
	@Override
	public void selectionChanged(IWorkbenchPart arg0, ISelection arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPartControl(Composite arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
