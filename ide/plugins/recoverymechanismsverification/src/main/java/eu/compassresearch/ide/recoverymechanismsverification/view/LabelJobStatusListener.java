/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification.view;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.view, class LabelJobStatusListener"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class LabelJobStatusListener extends JobChangeAdapter {
	private final Label label;

	public LabelJobStatusListener(Label label) {
		this.label = label;
	}

	@Override
	public void done(IJobChangeEvent event) {
		// if (event.getResult().equals(Status.OK_STATUS)) {
		label.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		// }
	}

	@Override
	public void aboutToRun(IJobChangeEvent event) {
		label.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
	}
}
