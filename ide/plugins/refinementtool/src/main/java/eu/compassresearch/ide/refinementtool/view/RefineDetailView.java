package eu.compassresearch.ide.refinementtool.view;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class RefineDetailView extends ViewPart {

	private Label refLawLabel;
	
	public RefineDetailView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {
		refLawLabel = new Label(parent, 0);
		Display display = Display.getCurrent();
		refLawLabel.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		Font terminalFont = JFaceResources.getFont(JFaceResources.TEXT_FONT);
		refLawLabel.setFont(terminalFont);
		refLawLabel.setText("<Refinement Laws>");
	}

	@Override
	public void setFocus() {
		refLawLabel.setFocus();
	}

	public void setRefLaw(String law) {
		refLawLabel.setText(law);
	}
	
}
