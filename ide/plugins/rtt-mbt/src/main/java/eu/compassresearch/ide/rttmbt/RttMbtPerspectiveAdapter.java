package eu.compassresearch.ide.rttmbt;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.ISourceProviderService;

public class RttMbtPerspectiveAdapter extends PerspectiveAdapter {

	@Override
	public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {

		super.perspectiveActivated(page, perspective);

        boolean rttMbtPerspective = false;
        if (perspective.getId().equals("eu.compassresearch.ide.rttmbt.rtt-perspective")) {
        	rttMbtPerspective = true;
        }
 
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
        ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class);
        if (service instanceof RttMbtCommandState) {
            RttMbtCommandState sourceProvider = (RttMbtCommandState) (service.getSourceProvider(RttMbtCommandState.keyIsRttPerspectiveActive));
            if (rttMbtPerspective) {
                sourceProvider.setValue(RttMbtCommandState.keyIsRttPerspectiveActive, "truumpel");
            } else {
                sourceProvider.setValue(RttMbtCommandState.keyIsRttPerspectiveActive, "falsumpel");
            }
        }
	}

	@Override
	public void perspectiveDeactivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		super.perspectiveDeactivated(page, perspective);
	}
}
