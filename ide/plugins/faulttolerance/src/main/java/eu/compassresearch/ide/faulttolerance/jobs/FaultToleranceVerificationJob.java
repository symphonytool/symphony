/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
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
public class FaultToleranceVerificationJob extends WorkspaceJob {

	private final FaultToleranceVerificationResults faultToleranceResults;
	private final ThreadGroup threads;

	public FaultToleranceVerificationJob(
			FaultToleranceVerificationResults results) {
		super(Message.FAULT_TOLERANCE_JOB_NAME.format(results.getProcessName()));
		this.faultToleranceResults = results;
		this.threads = new ThreadGroup(
				Message.FAULT_TOLERANCE_JOB_NAME.format(results
						.getProcessName()));
	}

	private String getAbsolutePath(Message fileNameMessage) {
		return String.format("%s/%s", faultToleranceResults.getFolder()
				.getLocation().toPortableString(),
				fileNameMessage.format(faultToleranceResults.getProcessName()));
	}

	private void verifyPreRequisites(final IProgressMonitor monitor)
			throws InterruptedException {

		try {
			beginSubTask(Message.CHECKING_PREREQUISITES, 2, monitor);

			Thread dft = new Thread(
					threads,
					new ModelCheckingTask(
							faultToleranceResults.getProcessName(),
							getAbsolutePath(Message.DIVERGENCE_FREEDOM_FORMULA_SCRIPT_FILE_NAME),
							new IModelCheckingTaskListener() {
								@Override
								public void done(ModelCheckingResult results) {
									faultToleranceResults
											.getDivergenceFreedom().update(
													results);
									faultToleranceResults.add(results
											.getException());
									monitor.worked(1);
								}
							}),
					Message.DIVERGENCE_FREE_JOB.format(faultToleranceResults
							.getProcessName()));

			Thread st = new Thread(
					threads,
					new ModelCheckingTask(
							faultToleranceResults.getProcessName(),
							getAbsolutePath(Message.SEMIFAIRNESS_FORMULA_SCRIPT_FILE_NAME),
							new IModelCheckingTaskListener() {
								@Override
								public void done(ModelCheckingResult results) {
									faultToleranceResults.getSemifairness()
											.update(results);
									faultToleranceResults.add(results
											.getException());
									monitor.worked(1);
								}
							}),
					Message.SEMIFAIRNESS_JOB.format(faultToleranceResults
							.getProcessName()));

			dft.setDaemon(true);
			st.setDaemon(true);

			dft.start();
			dft.join();

			st.start();
			st.join();

		} finally {
			faultToleranceResults.setPrerequisitesChecked(true);
		}
	}

	private IProgressMonitor createSubProgressMonitor(IProgressMonitor parent,
			int ticks) {
		return new SubProgressMonitor(parent, 1);
	}

	private void beginSubTask(Message message, int ticks,
			IProgressMonitor monitor, Object... additionalParameters) {
		List<Object> params = new ArrayList<>(
				Arrays.asList(additionalParameters));
		params.add(0, faultToleranceResults.getProcessName());
		String formattedMessage = message.format(params.toArray());
		monitor.beginTask(formattedMessage, ticks);
		monitor.subTask(formattedMessage);
	}

	@Override
	public IStatus runInWorkspace(final IProgressMonitor monitor) {
		try {
			monitor.beginTask(Message.FAULT_TOLERANCE_VERIFICATION_TASK_MESSAGE
					.format(faultToleranceResults.getProcessName()), 3);
			Runnable mainRun = new Runnable() {
				@Override
				public void run() {
					try {
						createFilesAndCheckDefinitions(createSubProgressMonitor(
								monitor, 1));

						if (faultToleranceResults.getDefinitionsMessage() == null
								&& !faultToleranceResults.hasException()) {
							verifyPreRequisites(createSubProgressMonitor(
									monitor, 1));
						}

						if (faultToleranceResults.isPreRequisitesOk()
								&& !faultToleranceResults.hasException()) {
							runFaultToleranceVerification(createSubProgressMonitor(
									monitor, 1));
						}
					} catch (InterruptedException e) {
						faultToleranceResults.setCancelledByUser(true);
					}
				}
			};

			Thread t = new Thread(threads, mainRun,
					Message.FAULT_TOLERANCE_VERIFICATION_THREAD_NAME
							.format(faultToleranceResults.getProcessName()));
			t.start();
			t.join();

			return new FaultToleranceVerificationStatus(faultToleranceResults);
		} catch (InterruptedException e) {
			return new FaultToleranceVerificationStatus(faultToleranceResults);
		} finally {
			monitor.done();
		}
	}

	private void createFilesAndCheckDefinitions(IProgressMonitor monitor) {
		try {
			beginSubTask(Message.STARTING_FAULT_TOLERANCE_FILES_MANAGEMENT, 4,
					monitor);

			List<String> channelsNotFound = new LinkedList<>();
			List<String> chansetsNotFound = new LinkedList<>();
			List<String> processesNotFound = new LinkedList<>();
			List<String> valuesNotFound = new LinkedList<>();
			List<String> namesetsNotFound = new LinkedList<>();

			clearGeneratedCmlFiles(createSubProgressMonitor(monitor, 1));

			checkDefinitions(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound,
					createSubProgressMonitor(monitor, 1));

			createCmlFiles(namesetsNotFound, valuesNotFound, channelsNotFound,
					chansetsNotFound, processesNotFound,
					createSubProgressMonitor(monitor, 1));

			createMissingDefinitionsMessage(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound);

			if (faultToleranceResults.getDefinitionsMessage() == null) {
				createFormulaFiles(createSubProgressMonitor(monitor, 1));
			}
		} catch (CoreException e) {
			faultToleranceResults.add(e);
		} catch (UnableToRunFaultToleranceVerificationException e) {
			faultToleranceResults.setException(e);
		} finally {
			monitor.done();
		}
	}

	private void clearGeneratedCmlFiles(IProgressMonitor monitor)
			throws CoreException,
			UnableToRunFaultToleranceVerificationException {
		List<Message> fileNames = new LinkedList<>();
		String processName = faultToleranceResults.getProcessName();

		fileNames.add(Message.BASE_CML_FILE_NAME);
		fileNames.add(Message.CML_PROCESSES_FILE_NAME);

		try {
			beginSubTask(Message.CLEAR_GENERATED_CML_FILES_TASK_NAME,
					(fileNames.size() + 2), monitor);
			prepareFolder(createSubProgressMonitor(monitor, 1));
			for (Message fileName : fileNames) {
				IFile outputFile = faultToleranceResults.getFolder().getFile(
						fileName.format(processName));
				if (outputFile.exists()) {
					try {
						outputFile.delete(true,
								createSubProgressMonitor(monitor, 1));
					} catch (CoreException e) {
						// move to next, don't care.
					}
				}
			}
			refreshModel(createSubProgressMonitor(monitor, 1));
		} finally {
			monitor.done();
		}
	}

	private void refreshModel(IProgressMonitor monitor) {
		faultToleranceResults.getCmlProject().getModel()
				.refresh(false, monitor);
	}

	private void checkDefinitions(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound,
			IProgressMonitor monitor) throws CoreException {
		try {
			beginSubTask(Message.DEFINITIONS_VERIFICATION_TASK_NAME, 11,
					monitor);
			refreshModel(createSubProgressMonitor(monitor, 1));
			Set<String> channelNames = new TreeSet<>();
			Set<String> chansetNames = new TreeSet<>();
			Set<String> processNames = new TreeSet<>();
			Set<String> valueNames = new TreeSet<>();
			Set<String> namesetNames = new TreeSet<>();
			findDefinitions(AChannelDefinition.class, channelNames,
					createSubProgressMonitor(monitor, 1));
			findDefinitions(AChansetDefinition.class, chansetNames,
					createSubProgressMonitor(monitor, 1));
			findDefinitions(AProcessDefinition.class, processNames,
					createSubProgressMonitor(monitor, 1));
			findDefinitions(AValueDefinition.class, valueNames,
					createSubProgressMonitor(monitor, 1));
			findDefinitions(ANamesetDefinition.class, valueNames,
					createSubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_NAMESETS, namesetsNotFound,
					namesetNames, createSubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_VALUES, valuesNotFound,
					valueNames, createSubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_CHANNELS, channelsNotFound,
					channelNames, createSubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_CHANSETS, chansetsNotFound,
					chansetNames, createSubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_PROCESSES, processesNotFound,
					processNames, createSubProgressMonitor(monitor, 1));
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
		ICmlProject cmlProject = faultToleranceResults.getCmlProject();
		try {
			beginSubTask(Message.CHECK_NAMES_TASK, cmlProject.getSourceUnits()
					.size(), monitor);
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
				monitor.worked(1);
			}
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
			beginSubTask(Message.CHECK_NAMES_TASK,
					namesTokenizer.countTokens(), monitor);
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
		try {
			beginSubTask(Message.CREATE_FORMULA_FILES_TASK_NAME, 5, monitor);
			List<PDefinition> definitions = new LinkedList<>();
			findDefinitions(definitions, createSubProgressMonitor(monitor, 1));
			createDivergenceFreedomFormulaScript(definitions,
					createSubProgressMonitor(monitor, 1));
			createSemifarinessFormulaScript(definitions,
					createSubProgressMonitor(monitor, 1));
			createFullFaultToleranceFormulaScript(definitions,
					createSubProgressMonitor(monitor, 1));
			createLimitedFaultToleranceFormulaScript(definitions,
					createSubProgressMonitor(monitor, 1));
		} finally {
			monitor.done();
		}
	}

	private void createCmlFiles(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound,
			IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		try {
			beginSubTask(Message.CREATE_CML_FILES_TASK_NAME, 2, monitor);
			createFaultToleranceBaseFile(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound,
					createSubProgressMonitor(monitor, 1));
			createFaultToleranceProcessesFile(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound,
					createSubProgressMonitor(monitor, 1));
		} finally {
			monitor.done();
		}
	}

	private void findDefinitions(List<PDefinition> definitions,
			IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		ICmlProject cmlProject = faultToleranceResults.getCmlProject();
		String processName = faultToleranceResults.getProcessName();
		try {
			beginSubTask(Message.FIND_PARSE_LIST_DEFINITIONS_TASK_NAME,
					cmlProject.getSourceUnits().size(), monitor);
			for (ICmlSourceUnit su : cmlProject.getSourceUnits()) {
				definitions.addAll(su.getParseListDefinitions());
				monitor.worked(1);
			}
		} catch (CoreException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_FIND_PROJECT_DEFINITIONS, e, processName);
		} finally {
			monitor.done();
		}
	}

	private void createLimitedFaultToleranceFormulaScript(
			List<PDefinition> definitions, IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		String processName = faultToleranceResults.getProcessName();
		try {
			beginSubTask(Message.CREATE_FORMULA_FILES_TASK_NAME, 1, monitor);
			NewMCVisitor adaptor = new NewMCVisitor();
			final String modelCheckerProperty = MCConstants.DEADLOCK_PROPERTY;
			faultToleranceResults.getLimitedFaultTolerance()
					.setModelCheckerProperty(modelCheckerProperty);
			String formulaScriptContent = adaptor.generateFormulaScript(
					definitions, modelCheckerProperty,
					Message.LAZY_LIMIT_DEADLOCK_CHECK_PROCESS_NAME
							.format(processName));
			writeFile(Message.LIMITED_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME,
					formulaScriptContent,
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT,
					createSubProgressMonitor(monitor, 1));
		} catch (IOException | AnalysisException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT, e, processName);
		} finally {
			monitor.done();
		}
	}

	private void createFullFaultToleranceFormulaScript(
			List<PDefinition> definitions, IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		String processName = faultToleranceResults.getProcessName();
		try {
			beginSubTask(Message.CREATE_FORMULA_FILES_TASK_NAME, 1, monitor);
			NewMCVisitor adaptor = new NewMCVisitor();
			final String modelCheckerProperty = MCConstants.DEADLOCK_PROPERTY;
			faultToleranceResults.getFullFaultTolerance()
					.setModelCheckerProperty(modelCheckerProperty);
			String formulaScriptContent = adaptor.generateFormulaScript(
					definitions, modelCheckerProperty,
					Message.LAZY_DEADLOCK_CHECK_PROCESS_NAME
							.format(processName));
			writeFile(Message.FULL_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME,
					formulaScriptContent,
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT,
					createSubProgressMonitor(monitor, 1));
		} catch (IOException | AnalysisException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT, e, processName);
		} finally {
			monitor.done();
		}
	}

	private void createSemifarinessFormulaScript(List<PDefinition> definitions,
			IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		String processName = faultToleranceResults.getProcessName();

		try {
			beginSubTask(Message.CREATE_FORMULA_FILES_TASK_NAME, 1, monitor);
			NewMCVisitor adaptor = new NewMCVisitor();
			final String modelCheckerProperty = MCConstants.LIVELOCK_PROPERTY;
			faultToleranceResults.getDivergenceFreedom()
					.setModelCheckerProperty(modelCheckerProperty);
			String formulaScriptContent = adaptor.generateFormulaScript(
					definitions, modelCheckerProperty,
					Message.SEMIFAIRNESS_PROCESS_NAME.format(processName));
			writeFile(Message.SEMIFAIRNESS_FORMULA_SCRIPT_FILE_NAME,
					formulaScriptContent,
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT,
					createSubProgressMonitor(monitor, 1));
		} catch (IOException | AnalysisException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT, e, processName);
		} finally {
			monitor.done();
		}

	}

	private void createDivergenceFreedomFormulaScript(
			List<PDefinition> definitions, IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		String processName = faultToleranceResults.getProcessName();
		try {
			beginSubTask(Message.CREATE_FORMULA_FILES_TASK_NAME, 1, monitor);
			NewMCVisitor adaptor = new NewMCVisitor();
			final String modelCheckerProperty = MCConstants.LIVELOCK_PROPERTY;
			faultToleranceResults.getDivergenceFreedom()
					.setModelCheckerProperty(modelCheckerProperty);
			String formulaScriptContent = adaptor
					.generateFormulaScript(definitions, modelCheckerProperty,
							Message.DIVERGENCE_FREEDOM_PROCESS_NAME
									.format(processName));
			writeFile(Message.DIVERGENCE_FREEDOM_FORMULA_SCRIPT_FILE_NAME,
					formulaScriptContent,
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT,
					createSubProgressMonitor(monitor, 1));
		} catch (Exception e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FORMULA_SCRIPT, e, processName);
		} finally {
			monitor.done();
		}
	}

	private void createFaultToleranceProcessesFile(
			List<String> namesetsNotFound, List<String> valuesNotFound,
			List<String> channelsNotFound, List<String> chansetsNotFound,
			List<String> processesNotFound, IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {

		try {
			beginSubTask(Message.CREATE_CML_FILES_TASK_NAME, 1, monitor);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);

			List<String> processesToAdd = new LinkedList<>();

			updateElementsToAdd(Message.DIVERGENCE_FREEDOM_PROCESS_NAME,
					Message.DIVERGENCE_FREEDOM_PROCESS_TEMPLATE,
					processesNotFound, processesToAdd);
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
				writeFile(
						Message.CML_PROCESSES_FILE_NAME,
						baos.toByteArray(),
						Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_PROCESSES_FILE,
						monitor);
			}
		} finally {
			monitor.done();
		}
	}

	private void createFaultToleranceBaseFile(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound,
			IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		try {
			beginSubTask(Message.CREATE_CML_FILES_TASK_NAME, 1, monitor);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);

			boolean requires;
			requires = createBaseFileChansets(chansetsNotFound, ps);
			requires = createBaseFileProcesses(processesNotFound, ps)
					|| requires;

			if (requires) {
				writeFile(Message.BASE_CML_FILE_NAME, baos.toByteArray(),
						Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_BASE_FILE,
						createSubProgressMonitor(monitor, 1));
			}
		} finally {
			monitor.done();
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

	private void writeFile(Message fileName, String contents,
			Message errorMessage, IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		writeFile(fileName, contents.getBytes(), errorMessage, monitor);
	}

	private void writeFile(Message fileName, byte[] contents,
			Message errorMessage, IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		String processName = faultToleranceResults.getProcessName();
		String fileNameFormatted = fileName.format(processName);

		try {
			beginSubTask(Message.WRITE_FILE, 3, monitor, fileNameFormatted);
			prepareFolder(createSubProgressMonitor(monitor, 1));

			IFile outputFile = faultToleranceResults.getFolder().getFile(
					fileNameFormatted);

			InputStream in = new ByteArrayInputStream(contents);
			if (outputFile.exists()) {
				outputFile.setContents(in, false, false,
						createSubProgressMonitor(monitor, 2));
			} else {
				outputFile.create(in, false,
						createSubProgressMonitor(monitor, 1));
				refreshModel(createSubProgressMonitor(monitor, 1));
			}
		} catch (CoreException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					errorMessage, e, processName);
		} finally {
			monitor.done();
		}
	}

	private void prepareFolder(IProgressMonitor monitor)
			throws UnableToRunFaultToleranceVerificationException {
		IContainer container = faultToleranceResults.getOutputContainer();
		String processName = faultToleranceResults.getProcessName();
		try {
			String folderName = Message.FOLDER_NAME.format(processName);
			beginSubTask(Message.CREATE_FOLDER, 2, monitor, folderName);
			IFolder folder;
			if (container == null) {
				throw new UnableToRunFaultToleranceVerificationException(
						Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_FOLDER,
						processName);
			}
			folder = container.getFolder(new Path(folderName));
			if (!folder.getParent().exists()) {
				((IFolder) folder.getParent()).create(false, false,
						createSubProgressMonitor(monitor, 2));
			}
			if (!folder.exists()) {
				folder.getParent().refreshLocal(IResource.DEPTH_ONE,
						createSubProgressMonitor(monitor, 1));
				folder.create(false, false,
						createSubProgressMonitor(monitor, 1));
			}
			faultToleranceResults.setFolder(folder);
		} catch (CoreException e) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.UNABLE_TO_CREATE_FAULT_TOLERANCE_FOLDER, e,
					processName);
		} finally {
			monitor.done();
		}
	}

	private void runFaultToleranceVerification(final IProgressMonitor monitor)
			throws InterruptedException {

		beginSubTask(Message.FAULT_TOLERANCE_VERIFICATION_TASK_MESSAGE, 2,
				monitor);

		Thread fftt = new Thread(
				threads,
				new ModelCheckingTask(
						faultToleranceResults.getProcessName(),
						getAbsolutePath(Message.FULL_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME),
						new IModelCheckingTaskListener() {
							@Override
							public void done(ModelCheckingResult results) {
								faultToleranceResults.getFullFaultTolerance()
										.update(results);
								faultToleranceResults.add(results
										.getException());
								monitor.worked(1);
							}
						}),
				Message.FULL_FAULT_TOLERANCE_JOB.format(faultToleranceResults
						.getProcessName()));

		Thread lftt = new Thread(
				threads,
				new ModelCheckingTask(
						faultToleranceResults.getProcessName(),
						getAbsolutePath(Message.LIMITED_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME),
						new IModelCheckingTaskListener() {
							@Override
							public void done(ModelCheckingResult results) {
								faultToleranceResults
										.getLimitedFaultTolerance().update(
												results);
								faultToleranceResults.add(results
										.getException());
								monitor.worked(1);
							}
						}),
				Message.LIMITED_FAULT_TOLERANCE_JOB
						.format(faultToleranceResults.getProcessName()));

		fftt.setDaemon(true);
		lftt.setDaemon(true);

		// TODO change the order when the ModelChecker supports multithreading.
		fftt.start();
		fftt.join();

		lftt.start();
		lftt.join();
	}

	@Override
	protected void canceling() {
		super.canceling();
		threads.interrupt();
		try {
			FormulaIntegrator.getInstance().finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
		}
	}
}
