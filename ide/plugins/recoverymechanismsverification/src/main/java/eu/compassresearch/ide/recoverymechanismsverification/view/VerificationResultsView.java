/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification.view;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.recoverymechanismsverification.Messages;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.view, class VerificationResultsView"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class VerificationResultsView extends ViewPart {
	private static final String ID = "eu.compassresearch.ide.recoverymechanismsverification.view.VerificationResultsView";

	private Label processName;
	private Label divergenceFreeLabel;
	private Label semifairnessLabel;
	private Label fullFaultToleranceLabel;
	private Label limitedFaultToleranceLabel;
	private Button verifyButton;

	@Override
	public void createPartControl(Composite parent) {
		processName = new Label(parent, 0);
		divergenceFreeLabel = new Label(parent, 0);
		semifairnessLabel = new Label(parent, 0);
		fullFaultToleranceLabel = new Label(parent, 0);
		limitedFaultToleranceLabel = new Label(parent, 0);
		verifyButton = new Button(parent, 0);

		processName.setText("System");
		divergenceFreeLabel.setText(Messages.DIVERGENCE_FREE_VERIFICATION
				.getName());
		semifairnessLabel.setText(Messages.SEMIFAIRNESS_VERIFICATION.getName());
		fullFaultToleranceLabel
				.setText(Messages.FULL_FAULT_TOLERANCE_VERIFICATION.getName());
		limitedFaultToleranceLabel
				.setText(Messages.LIMITED_FAULT_TOLERANCE_VERIFICATION
						.getName());
		verifyButton.setText(Messages.VERIFY_FAULT_TOLERANCE.getName());
	}

	@Override
	public void setFocus() {
		verifyButton.setFocus();
	}

}
