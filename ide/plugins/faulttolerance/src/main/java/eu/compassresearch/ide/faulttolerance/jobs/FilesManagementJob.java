/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.UnableToRunFaultToleranceVerificationException;

/**
 * @author Andr&eacute; Didier &lt;<a
 *         href="mailto:andredidier@gmail.com">andredidier@gmail.com</a>&gt;
 * 
 */
public class FilesManagementJob extends Job {

	private final String processName;
	private UnableToRunFaultToleranceVerificationException exception;
	private final IContainer container;

	/**
	 * 
	 * @param processName
	 * @param container
	 *            Container for generated files.
	 */
	public FilesManagementJob(String processName, IContainer container) {
		super(Message.FILES_MANAGEMENT_JOB.format(processName));
		this.processName = processName;
		this.container = container;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {

			monitor.beginTask(Message.STARTING_FAULT_TOLERANCE_FILES_MANAGEMENT
					.format(processName), 3);
			checkMonitor(monitor);
			IFolder folder = createFolder();
			monitor.worked(1);
			createFaultToleranceBaseFile(folder);
			monitor.worked(1);
			createFaultToleranceProcessesFile(folder);
			monitor.worked(1);

			return Status.OK_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		} catch (UnableToRunFaultToleranceVerificationException e) {
			this.exception = e;
			return Status.CANCEL_STATUS;
		} finally {
			monitor.done();
		}
	}

	private void createFaultToleranceProcessesFile(IFolder folder)
			throws UnableToRunFaultToleranceVerificationException {
		writeFile(folder, Message.CML_PROCESSES_FILE_NAME,
				Message.CML_PROCESSES_TEMPLATE,
				Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_PROCESSES_FILE);
	}

	private void writeFile(IFolder folder, Message fileName,
			Message fileContents, Message errorMessage)
			throws UnableToRunFaultToleranceVerificationException {
		IFile outputFile = folder.getFile(fileName.format(processName));

		try {
			String contents = fileContents.format(processName);
			if (!outputFile.exists()) {
				outputFile.create(
						new ByteArrayInputStream(contents.getBytes()), true,
						new NullProgressMonitor());
			}
		} catch (CoreException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					errorMessage, e, processName);
		}
	}

	private void createFaultToleranceBaseFile(IFolder folder)
			throws UnableToRunFaultToleranceVerificationException {
		writeFile(folder, Message.BASE_CML_FILE_NAME,
				Message.BASE_CML_TEMPLATE,
				Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_PROCESSES_FILE);
	}

	private IFolder createFolder()
			throws UnableToRunFaultToleranceVerificationException {
		try {
			IFolder folder = container.getFolder(new Path(Message.FOLDER_NAME
					.format(processName)));
			if (!folder.getParent().exists()) {
				((IFolder) folder.getParent()).create(true, true,
						new NullProgressMonitor());
			}
			if (!folder.exists()) {
				folder.create(true, true, new NullProgressMonitor());
				folder.refreshLocal(IResource.DEPTH_ZERO,
						new NullProgressMonitor());
			}
			return folder;
		} catch (CoreException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_FOLDER, e,
					processName);
		}
	}

	private void checkMonitor(IProgressMonitor monitor)
			throws InterruptedException {
		if (monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}

	public UnableToRunFaultToleranceVerificationException getException() {
		return exception;
	}

}
