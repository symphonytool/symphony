/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.ui.progress.IProgressConstants;

import eu.compassresearch.ide.faulttolerance.Activator;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Image;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a
 *         href="mailto:alrd@cin.ufpe.br">alrd@cin.ufpe.br</a>)
 *
 */
public abstract class FaultToleranceVerificationJobBase extends WorkspaceJob {
	private final IFaultToleranceVerificationRequest request;

	/**
	 * @return the request
	 */
	public IFaultToleranceVerificationRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	public IFaultToleranceVerificationResponse getResponse() {
		return response;
	}

	private final IFaultToleranceVerificationResponse response;

	public FaultToleranceVerificationJobBase(Message jobNameMessage,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(jobNameMessage.format(request.getSystemName()));
		this.request = request;
		this.response = response;
		init();
	}

	public FaultToleranceVerificationJobBase(String name,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(name);
		this.request = request;
		this.response = response;
		init();
	}

	private void init() {
		addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				setProperty(IProgressConstants.ICON_PROPERTY,
						Image.FAULT_TOLERANCE_JOB_ICON.getImageDescriptor());
			}

			@Override
			public void scheduled(IJobChangeEvent event) {
				setProperty(IProgressConstants.ICON_PROPERTY,
						Image.FAULT_TOLERANCE_CLOCK_ICON.getImageDescriptor());
			}

			@Override
			public void done(IJobChangeEvent event) {
				setProperty(IProgressConstants.ICON_PROPERTY,
						Image.FAULT_TOLERANCE_TICK_ICON.getImageDescriptor());
			}
		});
	}

	@Override
	public final IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		return runInWorkspace(getRequest(), getResponse(), monitor);
	}

	protected abstract IStatus runInWorkspace(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException;

	protected final String formatSystemName(Message message) {
		return message.format(request.getSystemName());
	}

	protected final String formatSystemName(CodeTemplate template) {
		return template.format(request.getSystemName());
	}

	@Override
	public boolean belongsTo(Object family) {
		if (family instanceof String) {
			return Activator.FAULT_TOLERANCE_JOB_FAMILY.equals(family);
		}
		return super.belongsTo(family);
	}
}
