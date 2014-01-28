/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

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
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.core.analysis.modelchecker.visitors.NewMCVisitor;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.UnableToRunFaultToleranceVerificationException;
import eu.compassresearch.ide.modelchecker.MCConstants;

/**
 * @author Andr&eacute; Didier &lt;<a
 *         href="mailto:andredidier@gmail.com">andredidier@gmail.com</a>&gt;
 * 
 */
public class FilesManagementJob extends Job {

	private final String processName;
	private UnableToRunFaultToleranceVerificationException exception;
	private final IContainer container;
	private final ICmlSourceUnit cmlSourceUnit;
	private IFolder folder;

	/**
	 * 
	 * @param processName
	 * @param container
	 *            Container for generated files.
	 */
	public FilesManagementJob(String processName, ICmlSourceUnit cmlSourceUnit,
			IContainer container) {
		super(Message.FILES_MANAGEMENT_JOB.format(processName));
		this.processName = processName;
		this.container = container;
		this.cmlSourceUnit = cmlSourceUnit;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {

			monitor.beginTask(Message.STARTING_FAULT_TOLERANCE_FILES_MANAGEMENT
					.format(processName), 7);
			checkMonitor(monitor);
			createFolder();
			monitor.worked(1);
			checkMonitor(monitor);
			createFaultToleranceBaseFile(folder);
			monitor.worked(1);
			checkMonitor(monitor);
			createFaultToleranceProcessesFile(folder);
			monitor.worked(1);
			checkMonitor(monitor);
			createDivergenceFreedomFormulaScript(folder);
			monitor.worked(1);
			checkMonitor(monitor);
			createSemifarinessFormulaScript(folder);
			monitor.worked(1);
			checkMonitor(monitor);
			createFullFaultToleranceFormulaScript(folder);
			monitor.worked(1);
			checkMonitor(monitor);
			createLimitedFaultToleranceFormulaScript(folder);
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

	private void createLimitedFaultToleranceFormulaScript(IFolder folder) {
		// TODO Auto-generated method stub

	}

	private void createFullFaultToleranceFormulaScript(IFolder folder) {
		// TODO Auto-generated method stub

	}

	private void createSemifarinessFormulaScript(IFolder folder)
			throws UnableToRunFaultToleranceVerificationException {

		try {
			NewMCVisitor adaptor = new NewMCVisitor();
			List<PDefinition> definitions = cmlSourceUnit
					.getParseListDefinitions();
			String formulaScriptContent = adaptor.generateFormulaScript(
					definitions, MCConstants.LIVELOCK_PROPERTY,
					Message.SEMIFAIRNESS_PROCESS_NAME.format(processName));
			writeFile(folder, Message.SEMIFAIRNESS_FORMULA_SCRIPT_FILE_NAME,
					formulaScriptContent,
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT);
		} catch (IOException | AnalysisException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT, e, processName);
		}

	}

	private void createDivergenceFreedomFormulaScript(IFolder folder)
			throws UnableToRunFaultToleranceVerificationException {
		try {
			NewMCVisitor adaptor = new NewMCVisitor();
			List<PDefinition> definitions = cmlSourceUnit
					.getParseListDefinitions();
			String formulaScriptContent = adaptor
					.generateFormulaScript(definitions,
							MCConstants.LIVELOCK_PROPERTY,
							Message.DIVERGENCE_FREEDOM_PROCESS_NAME
									.format(processName));
			writeFile(folder,
					Message.DIVERGENCE_FREEDOM_FORMULA_SCRIPT_FILE_NAME,
					formulaScriptContent,
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT);
		} catch (IOException | AnalysisException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT, e, processName);
		}
	}

	private void createFaultToleranceProcessesFile(IFolder folder)
			throws UnableToRunFaultToleranceVerificationException {
		writeFile(folder, Message.CML_PROCESSES_FILE_NAME,
				Message.CML_PROCESSES_TEMPLATE,
				Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_PROCESSES_FILE);
	}

	private void createFaultToleranceBaseFile(IFolder folder)
			throws UnableToRunFaultToleranceVerificationException {
		writeFile(folder, Message.BASE_CML_FILE_NAME,
				Message.BASE_CML_TEMPLATE,
				Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_PROCESSES_FILE);
	}

	private void writeFile(IFolder folder, Message fileName,
			Message fileContents, Message errorMessage)
			throws UnableToRunFaultToleranceVerificationException {
		writeFile(folder, fileName, fileContents.format(processName),
				errorMessage);
	}

	private void writeFile(IFolder folder, Message fileName, String contents,
			Message errorMessage)
			throws UnableToRunFaultToleranceVerificationException {
		IFile outputFile = folder.getFile(fileName.format(processName));

		try {
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

	private void createFolder()
			throws UnableToRunFaultToleranceVerificationException {
		try {
			if (container == null) {
				throw new UnableToRunFaultToleranceVerificationException(
						Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_FOLDER,
						processName);
			}
			folder = container.getFolder(new Path(Message.FOLDER_NAME
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

	public IFolder getFolder() {
		return folder;
	}

}
