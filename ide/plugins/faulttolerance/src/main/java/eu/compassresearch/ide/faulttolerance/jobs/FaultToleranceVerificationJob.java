/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

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
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewMCVisitor;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.UnableToRunFaultToleranceVerificationException;
import eu.compassresearch.ide.modelchecker.MCConstants;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationJob extends Job {

	private final FaultToleranceVerificationResults faultToleranceResults;

	public FaultToleranceVerificationJob(
			FaultToleranceVerificationResults results) {
		super(Message.FAULT_TOLERANCE_JOB_NAME.format(results.getProcessName()));
		this.faultToleranceResults = results;
	}

	private void checkMonitor(IProgressMonitor monitor)
			throws InterruptedException {
		if (monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}

	private String getAbsolutePath(Message fileNameMessage) {
		return String.format("%s/%s", faultToleranceResults.getFolder()
				.getLocationURI(), fileNameMessage.format(faultToleranceResults
				.getProcessName()));
	}

	private void verifyPreRequisites(final IProgressMonitor monitor)
			throws InterruptedException {

		try {
			// group.beginTask(Message.CHECKING_PREREQUISITES.format(), 2);

			ModelCheckingJob dfj = new ModelCheckingJob(
					Message.DIVERGENCE_FREE_JOB,
					faultToleranceResults.getProcessName(),
					getAbsolutePath(Message.DIVERGENCE_FREEDOM_FORMULA_SCRIPT_FILE_NAME));

			ModelCheckingJob sj = new ModelCheckingJob(
					Message.SEMIFAIRNESS_JOB,
					faultToleranceResults.getProcessName(),
					getAbsolutePath(Message.SEMIFAIRNESS_FORMULA_SCRIPT_FILE_NAME));

			dfj.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					if (event.getResult() instanceof ModelCheckingStatus) {
						ModelCheckingStatus status = ((ModelCheckingStatus) event
								.getResult());
						faultToleranceResults.setDivergenceFree(status
								.getResults().isSuccess());
						faultToleranceResults.add(status.getResults()
								.getException());
					}
					monitor.worked(1);
					if (!event.getJob().getResult().isOK()) {
						monitor.setCanceled(true);
					}
				}
			});
			sj.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					ModelCheckingStatus status = ((ModelCheckingStatus) event
							.getResult());
					faultToleranceResults.setSemifair(status.getResults()
							.isSuccess());
					faultToleranceResults.add(status.getResults()
							.getException());
					monitor.worked(1);
					if (!event.getJob().getResult().isOK()) {
						monitor.setCanceled(true);
					}
				}
			});

			dfj.schedule();
			sj.schedule();

			dfj.join();
			sj.join();
		} finally {
			faultToleranceResults.setPrerequisitesChecked(true);
		}
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			monitor.beginTask(Message.FAULT_TOLERANCE_VERIFICATION_TASK_MESSAGE
					.format(faultToleranceResults.getProcessName()), 6);

			createFilesAndCheckDefinitions(new SubProgressMonitor(monitor, 4));
			checkMonitor(monitor);

			if (faultToleranceResults.getDefinitionsMessage() == null) {
				verifyPreRequisites(monitor);
				checkMonitor(monitor);
			}
			monitor.worked(1);

			if (faultToleranceResults.isPreRequisitesOk()) {
				runFaultToleranceVerification(monitor);
			}
			monitor.worked(1);
			return new FaultToleranceVerificationStatus(faultToleranceResults);
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		} finally {
			monitor.done();
		}
	}

	private void createFilesAndCheckDefinitions(IProgressMonitor monitor) {
		try {
			monitor.beginTask(Message.STARTING_FAULT_TOLERANCE_FILES_MANAGEMENT
					.format(faultToleranceResults.getProcessName()), 4);
			checkMonitor(monitor);

			createFolder();
			monitor.worked(1);
			checkMonitor(monitor);

			List<String> channelsNotFound = new LinkedList<>();
			List<String> chansetsNotFound = new LinkedList<>();
			List<String> processesNotFound = new LinkedList<>();
			List<String> valuesNotFound = new LinkedList<>();
			List<String> namesetsNotFound = new LinkedList<>();

			clearGeneratedCmlFiles(monitor);

			checkDefinitions(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound,
					monitor);
			monitor.worked(1);
			checkMonitor(monitor);

			createCmlFiles(namesetsNotFound, valuesNotFound, channelsNotFound,
					chansetsNotFound, processesNotFound, monitor);
			monitor.worked(1);
			checkMonitor(monitor);

			createMissingDefinitionsMessage(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound);

			if (faultToleranceResults.getDefinitionsMessage() == null) {
				createFormulaFiles(monitor);
				monitor.worked(1);
			}
		} catch (InterruptedException e) {
			faultToleranceResults.add(e);
			monitor.setCanceled(true);
		} catch (UnableToRunFaultToleranceVerificationException e) {
			faultToleranceResults.setException(e);
			monitor.setCanceled(true);
		} finally {
			monitor.done();
		}
	}

	private void clearGeneratedCmlFiles(IProgressMonitor monitor) {
		List<Message> fileNames = new LinkedList<>();
		IFolder folder = faultToleranceResults.getFolder();
		String processName = faultToleranceResults.getProcessName();

		fileNames.add(Message.BASE_CML_FILE_NAME);
		fileNames.add(Message.CML_PROCESSES_FILE_NAME);

		for (Message fileName : fileNames) {
			IFile outputFile = folder.getFile(fileName.format(processName));
			if (outputFile.exists()) {
				try {
					outputFile.delete(true, new NullProgressMonitor());
				} catch (CoreException e) {
					// move to next, don't care.
				}
			}
		}
	}

	private void checkDefinitions(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound,
			IProgressMonitor monitor) {
		String processName = faultToleranceResults.getProcessName();
		ICmlProject cmlProject = faultToleranceResults.getCmlProject();
		try {
			monitor.beginTask(Message.DEFINITIONS_VERIFICATION_TASK_NAME
					.format(processName), 11);
			cmlProject.getModel().refresh(false,
					new SubProgressMonitor(monitor, 1));
			Set<String> channelNames = new TreeSet<>();
			Set<String> chansetNames = new TreeSet<>();
			Set<String> processNames = new TreeSet<>();
			Set<String> valueNames = new TreeSet<>();
			Set<String> namesetNames = new TreeSet<>();
			findDefinitions(AChannelDefinition.class, channelNames,
					new SubProgressMonitor(monitor, 1));
			findDefinitions(AChansetDefinition.class, chansetNames,
					new SubProgressMonitor(monitor, 1));
			findDefinitions(AProcessDefinition.class, processNames,
					new SubProgressMonitor(monitor, 1));
			findDefinitions(AValueDefinition.class, valueNames,
					new SubProgressMonitor(monitor, 1));
			findDefinitions(ANamesetDefinition.class, valueNames,
					new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_NAMESETS, namesetsNotFound,
					namesetNames, new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_VALUES, valuesNotFound,
					valueNames, new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_CHANNELS, channelsNotFound,
					channelNames, new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_CHANSETS, chansetsNotFound,
					chansetNames, new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_PROCESSES, processesNotFound,
					processNames, new SubProgressMonitor(monitor, 1));
		} catch (CoreException e) {
			faultToleranceResults.add(e);
		} finally {
			monitor.done();
		}

	}

	private void createMissingDefinitionsMessage(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound) {

		String message;
		if (channelsNotFound.isEmpty() && chansetsNotFound.isEmpty()
				&& processesNotFound.isEmpty() && valuesNotFound.isEmpty()) {
			message = null;
		} else {
			String processName = faultToleranceResults.getProcessName();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);

			if (!namesetsNotFound.isEmpty()) {
				ps.println(Message.NAMESETS_NOT_FOUND.format(processName,
						namesetsNotFound));
			}

			if (!valuesNotFound.isEmpty()) {
				ps.println(Message.VALUES_NOT_FOUND.format(processName,
						valuesNotFound));
			}

			if (!channelsNotFound.isEmpty()) {
				ps.println(Message.CHANNELS_NOT_FOUND.format(processName,
						channelsNotFound));
			}

			if (!chansetsNotFound.isEmpty()) {
				ps.println(Message.CHANSETS_NOT_FOUND.format(processName,
						chansetsNotFound));
			}

			if (!processesNotFound.isEmpty()) {
				ps.println(Message.PROCESSES_NOT_FOUND.format(processName,
						processesNotFound));
			}
			message = new String(baos.toByteArray());
		}
		faultToleranceResults.setDefinitionsMessage(message);

	}

	private <T extends PDefinition> void findDefinitions(Class<T> cl,
			Set<String> names, IProgressMonitor monitor) throws CoreException {
		String processName = faultToleranceResults.getProcessName();
		ICmlProject cmlProject = faultToleranceResults.getCmlProject();
		try {
			monitor.beginTask(Message.CHECK_NAMES_TASK.format(processName), 1);
			for (ICmlSourceUnit su : cmlProject.getSourceUnits()) {
				for (PDefinition def : su.getParseListDefinitions()) {
					if (cl.isAssignableFrom(def.getClass())) {
						if (def.getName() != null) {
							names.add(def.getName().getFullName());
						} else {
							System.out.println(def.getName());
						}
					}
				}
			}
			monitor.worked(1);
		} finally {
			monitor.done();
		}

	}

	private void checkNames(Message namesMessage, List<String> namesNotFound,
			Set<String> existingNames, IProgressMonitor monitor) {
		String processName = faultToleranceResults.getProcessName();
		try {
			StringTokenizer namesTokenizer = new StringTokenizer(
					namesMessage.format(processName), ",");
			monitor.beginTask(Message.CHECK_NAMES_TASK.format(processName),
					namesTokenizer.countTokens());
			while (namesTokenizer.hasMoreTokens()) {
				String name = namesTokenizer.nextToken();
				if (!existingNames.contains(name)) {
					namesNotFound.add(name);
				}
				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}
	}

	private void createFormulaFiles(IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		List<PDefinition> definitions = new LinkedList<>();
		findDefinitions(definitions, new SubProgressMonitor(monitor, 2));
		createDivergenceFreedomFormulaScript(definitions);
		createSemifarinessFormulaScript(definitions);
		createFullFaultToleranceFormulaScript(definitions);
		createLimitedFaultToleranceFormulaScript(definitions);
	}

	private void createCmlFiles(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound,
			IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		createFaultToleranceBaseFile(namesetsNotFound, valuesNotFound,
				channelsNotFound, chansetsNotFound, processesNotFound);
		createFaultToleranceProcessesFile(namesetsNotFound, valuesNotFound,
				channelsNotFound, chansetsNotFound, processesNotFound);
	}

	private void findDefinitions(List<PDefinition> definitions,
			SubProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		ICmlProject cmlProject = faultToleranceResults.getCmlProject();
		String processName = faultToleranceResults.getProcessName();
		try {
			for (ICmlSourceUnit su : cmlProject.getSourceUnits()) {
				definitions.addAll(su.getParseListDefinitions());
			}
			monitor.worked(1);
		} catch (CoreException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_FIND_PROJECT_DEFINITIONS, e, processName);
		} finally {
			monitor.done();
		}
	}

	private void createLimitedFaultToleranceFormulaScript(
			List<PDefinition> definitions)
			throws UnableToRunFaultToleranceVerificationException {
		IFolder folder = faultToleranceResults.getFolder();
		String processName = faultToleranceResults.getProcessName();
		try {
			NewMCVisitor adaptor = new NewMCVisitor();
			String formulaScriptContent = adaptor.generateFormulaScript(
					definitions, MCConstants.DEADLOCK_PROPERTY,
					Message.LAZY_LIMIT_DEADLOCK_CHECK_PROCESS_NAME
							.format(processName));
			writeFile(folder,
					Message.LIMITED_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME,
					formulaScriptContent,
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT);
		} catch (IOException | AnalysisException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT, e, processName);
		}
	}

	private void createFullFaultToleranceFormulaScript(
			List<PDefinition> definitions)
			throws UnableToRunFaultToleranceVerificationException {
		IFolder folder = faultToleranceResults.getFolder();
		String processName = faultToleranceResults.getProcessName();
		try {
			NewMCVisitor adaptor = new NewMCVisitor();
			String formulaScriptContent = adaptor.generateFormulaScript(
					definitions, MCConstants.DEADLOCK_PROPERTY,
					Message.LAZY_DEADLOCK_CHECK_PROCESS_NAME
							.format(processName));
			writeFile(folder,
					Message.FULL_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME,
					formulaScriptContent,
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT);
		} catch (IOException | AnalysisException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT, e, processName);
		}
	}

	private void createSemifarinessFormulaScript(List<PDefinition> definitions)
			throws UnableToRunFaultToleranceVerificationException {
		IFolder folder = faultToleranceResults.getFolder();
		String processName = faultToleranceResults.getProcessName();

		try {
			NewMCVisitor adaptor = new NewMCVisitor();
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

	private void createDivergenceFreedomFormulaScript(
			List<PDefinition> definitions)
			throws UnableToRunFaultToleranceVerificationException {
		IFolder folder = faultToleranceResults.getFolder();
		String processName = faultToleranceResults.getProcessName();
		try {
			NewMCVisitor adaptor = new NewMCVisitor();
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

	private void createFaultToleranceProcessesFile(
			List<String> namesetsNotFound, List<String> valuesNotFound,
			List<String> channelsNotFound, List<String> chansetsNotFound,
			List<String> processesNotFound)
			throws UnableToRunFaultToleranceVerificationException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		List<String> processesToAdd = new LinkedList<>();

		updateElementsToAdd(Message.DIVERGENCE_FREEDOM_PROCESS_NAME,
				Message.DIVERGENCE_FREEDOM_PROCESS_TEMPLATE, processesNotFound,
				processesToAdd);
		updateElementsToAdd(Message.SEMIFAIRNESS_PROCESS_NAME,
				Message.SEMIFAIRNESS_PROCESS_TEMPLATE, processesNotFound,
				processesToAdd);
		updateElementsToAdd(Message.LAZY_DEADLOCK_CHECK_PROCESS_NAME,
				Message.LAZY_DEADLOCK_CHECK_PROCESS_TEMPLATE,
				processesNotFound, processesToAdd);
		updateElementsToAdd(Message.LAZY_LIMIT_DEADLOCK_CHECK_PROCESS_NAME,
				Message.LAZY_LIMIT_DEADLOCK_CHECK_PROCESS_TEMPLATE,
				processesNotFound, processesToAdd);
		updateElementsToAdd(Message.NO_FAULTS_PROCESS_NAME,
				Message.NO_FAULTS_PROCESS_TEMPLATE, processesNotFound,
				processesToAdd);
		updateElementsToAdd(Message.LAZY_PROCESS_NAME,
				Message.LAZY_PROCESS_TEMPLATE, processesNotFound,
				processesToAdd);
		updateElementsToAdd(Message.LIMIT_PROCESS_NAME,
				Message.LIMIT_PROCESS_TEMPLATE, processesNotFound,
				processesToAdd);
		updateElementsToAdd(Message.LAZY_LIMIT_PROCESS_NAME,
				Message.LAZY_LIMIT_PROCESS_TEMPLATE, processesNotFound,
				processesToAdd);

		for (String template : processesToAdd) {
			ps.println(template);
			ps.println();
		}

		if (!processesToAdd.isEmpty()) {
			IFolder folder = faultToleranceResults.getFolder();
			writeFile(folder, Message.CML_PROCESSES_FILE_NAME,
					baos.toByteArray(),
					Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_PROCESSES_FILE);
		}
	}

	private void createFaultToleranceBaseFile(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound)
			throws UnableToRunFaultToleranceVerificationException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		boolean requires;
		requires = createBaseFileChansets(chansetsNotFound, ps);
		requires = createBaseFileProcesses(processesNotFound, ps) || requires;

		if (requires) {
			IFolder folder = faultToleranceResults.getFolder();
			writeFile(folder, Message.BASE_CML_FILE_NAME, baos.toByteArray(),
					Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_PROCESSES_FILE);
		}
	}

	private boolean createBaseFileProcesses(List<String> processesNotFound,
			PrintStream ps) {
		List<String> processesToAdd = new LinkedList<>();
		updateElementsToAdd(Message.PROCESS_RUN_E_NAME,
				Message.PROCESS_RUN_E_TEMPLATE, processesNotFound,
				processesToAdd);
		updateElementsToAdd(Message.PROCESS_CHAOS_E_NAME,
				Message.PROCESS_CHAOS_E_TEMPLATE, processesNotFound,
				processesToAdd);

		for (String processTemplate : processesToAdd) {
			ps.println(processTemplate);
			ps.println();
		}
		return !processesToAdd.isEmpty();
	}

	private boolean createBaseFileChansets(List<String> chansetsNotFound,
			PrintStream ps) {
		List<String> chansetsToAdd = new LinkedList<>();

		String pn = faultToleranceResults.getProcessName();
		if (!chansetsNotFound.contains(Message.CHANSET_H_NAME.format(pn))) {
			StringTokenizer relatedToH = new StringTokenizer(
					Message.CHANSET_H_RELATED.format(pn), ",");
			while (relatedToH.hasMoreTokens()) {
				chansetsNotFound.remove(relatedToH.nextToken());
			}
		}

		updateElementsToAdd(Message.CHANSET_E_NAME, Message.CHANSET_E_TEMPLATE,
				chansetsNotFound, chansetsToAdd);
		updateElementsToAdd(Message.CHANSET_F_NAME, Message.CHANSET_F_TEMPLATE,
				chansetsNotFound, chansetsToAdd);
		updateElementsToAdd(Message.CHANSET_H_NAME, Message.CHANSET_H_TEMPLATE,
				chansetsNotFound, chansetsToAdd);

		if (!chansetsToAdd.isEmpty()) {
			ps.println("chansets");
			for (String chansetTemplate : chansetsToAdd) {
				ps.printf("\t%s", chansetTemplate);
				ps.println();
			}
		}
		return !chansetsToAdd.isEmpty();
	}

	private void updateElementsToAdd(Message nameMessage, Message template,
			Collection<String> namesNotFound, List<String> templateList) {
		String pn = faultToleranceResults.getProcessName();
		String name = nameMessage.format(pn);
		if (namesNotFound.contains(name)) {
			templateList.add(template.format(pn));
		}
		namesNotFound.remove(name);
	}

	private void writeFile(IFolder folder, Message fileName, String contents,
			Message errorMessage)
			throws UnableToRunFaultToleranceVerificationException {
		writeFile(folder, fileName, contents.getBytes(), errorMessage);
	}

	private void writeFile(IFolder folder, Message fileName, byte[] contents,
			Message errorMessage)
			throws UnableToRunFaultToleranceVerificationException {
		String processName = faultToleranceResults.getProcessName();
		IFile outputFile = folder.getFile(fileName.format(processName));

		try {
			InputStream in = new ByteArrayInputStream(contents);
			if (outputFile.exists()) {
				outputFile.setContents(in, true, true,
						new NullProgressMonitor());
			} else {
				outputFile.create(in, true, new NullProgressMonitor());
				faultToleranceResults.getCmlProject().getModel()
						.refresh(true, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					errorMessage, e, processName);
		}
	}

	private void createFolder()
			throws UnableToRunFaultToleranceVerificationException {
		IContainer container = faultToleranceResults.getOutputContainer();
		String processName = faultToleranceResults.getProcessName();
		try {
			IFolder folder;
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
			faultToleranceResults.setFolder(folder);
		} catch (CoreException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_FOLDER, e,
					processName);
		}
	}

	private void runFaultToleranceVerification(final IProgressMonitor monitor) {
		final ModelCheckingJob fftj = new ModelCheckingJob(
				Message.FULL_FAULT_TOLERANCE_JOB,
				faultToleranceResults.getProcessName(),
				getAbsolutePath(Message.FULL_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME));
		final ModelCheckingJob lftj = new ModelCheckingJob(
				Message.LIMITED_FAULT_TOLERANCE_JOB,
				faultToleranceResults.getProcessName(),
				getAbsolutePath(Message.LIMITED_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME));

		fftj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingStatus status = ((ModelCheckingStatus) event
						.getResult());
				faultToleranceResults.setFullFaultTolerant(status.getResults()
						.isSuccess());
				faultToleranceResults.add(status.getResults().getException());
				monitor.worked(1);
				if (!event.getJob().getResult().isOK()) {
					monitor.setCanceled(true);
				}
			}
		});

		lftj.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				ModelCheckingStatus status = ((ModelCheckingStatus) event
						.getResult());
				faultToleranceResults.setLimitedFaultTolerant(status
						.getResults().isSuccess());
				faultToleranceResults.add(status.getResults().getException());
				monitor.worked(1);
				if (!event.getJob().getResult().isOK()) {
					monitor.setCanceled(true);
				}
			}

		});

		fftj.schedule();
		lftj.schedule();

		try {
			lftj.join();
			fftj.join();
		} catch (InterruptedException e) {
			// Interrupted...
		}

	}
}
