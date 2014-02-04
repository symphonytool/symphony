/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;

import eu.compassresearch.ide.faulttolerance.FaultToleranceProperty;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.markers, class ViewLtsGraphFix"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class ViewLtsGraphFix implements IMarkerResolution {

	private final FaultToleranceProperty property;

	public ViewLtsGraphFix(FaultToleranceProperty property) {
		this.property = property;
	}

	@Override
	public String getLabel() {
		return Message.VIEW_LTS_LABEL.format(property.getProcessName(),
				property.getType().formattedName());
	}

	@Override
	public void run(IMarker marker) {
		// TODO falta implementar
	}

}
