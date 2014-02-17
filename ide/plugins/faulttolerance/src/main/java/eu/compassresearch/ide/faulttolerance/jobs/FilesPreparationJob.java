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
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
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
import eu.compassresearch.ide.faulttolerance.FaultToleranceProperty;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FilesPreparationJob"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FilesPreparationJob extends FaultToleranceVerificationJobBase {

	private IFile baseCmlFile;
	private IFile faultToleranceCmlFile;

	public FilesPreparationJob(IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(Message.FILE_PREPARATION_JOB_NAME, request, response);
		initFiles(request, response);
	}

	private void initFiles(IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		baseCmlFile = response.getFolder().getFile(
				formatSystemName(Message.BASE_CML_FILE_NAME));
		faultToleranceCmlFile = response.getFolder().getFile(
				formatSystemName(Message.CML_PROCESSES_FILE_NAME));
	}

	@Override
	protected ISchedulingRule updateSchedulingRules(ISchedulingRule baseRule,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		initFiles(request, response);
		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace()
				.getRuleFactory();
		ISchedulingRule combinedRule = baseRule;
		for (FaultToleranceProperty property : response.properties()) {
			ISchedulingRule rule = ruleFactory.createRule(property
					.getFormulaScriptFile());
			combinedRule = MultiRule.combine(rule, combinedRule);
		}
		combinedRule = MultiRule.combine(ruleFactory.createRule(baseCmlFile),
				combinedRule);
		combinedRule = MultiRule.combine(
				ruleFactory.createRule(faultToleranceCmlFile), combinedRule);
		return combinedRule;
	}

	@Override
	protected IStatus runInWorkspace(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(
					formatSystemName(Message.STARTING_FAULT_TOLERANCE_FILES_MANAGEMENT),
					4);
			createFolder(request, response, new SubProgressMonitor(monitor, 1));
			List<PDefinition> definitions = new LinkedList<>();
			prepareDefinitions(definitions, request, response,
					new SubProgressMonitor(monitor, 1));
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			if (response.getDefinitionsMessage() == null) {
				return createFormulaFiles(definitions, request, response,
						new SubProgressMonitor(monitor, 1));
			} else {
				return Status.OK_STATUS;
			}
		} catch (IOException | AnalysisException | RuntimeException e) {
			response.setException(e);
			return Status.OK_STATUS;
		} finally {
			monitor.done();
		}

	}

	private void createFolder(IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			String folderName = formatSystemName(Message.FOLDER_NAME);
			monitor.beginTask(Message.CREATE_FOLDER.format(
					request.getSystemName(), folderName), 5);
			IContainer container = request.getSourceUnit().getProject()
					.getModelBuildPath().getOutput();
			IFolder folder = container.getFolder(new Path(folderName));
			if (!folder.getParent().exists()) {
				((IFolder) folder.getParent()).create(false, false,
						new SubProgressMonitor(monitor, 2));
			} else {
				monitor.worked(2);
			}
			if (!folder.exists()) {
				folder.getParent().refreshLocal(IResource.DEPTH_ONE,
						new SubProgressMonitor(monitor, 1));
				folder.create(false, false, new SubProgressMonitor(monitor, 1));
				folder.getParent().refreshLocal(IResource.DEPTH_ONE,
						new SubProgressMonitor(monitor, 1));
			} else {
				monitor.worked(3);
			}
			response.setFolder(folder);
		} finally {
			monitor.done();
		}
	}

	private void prepareDefinitions(List<PDefinition> definitions,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(formatSystemName(Message.PREPARE_DEFINITIONS), 3);
			List<String> channelsNotFound = new LinkedList<>();
			List<String> chansetsNotFound = new LinkedList<>();
			List<String> processesNotFound = new LinkedList<>();
			List<String> valuesNotFound = new LinkedList<>();
			List<String> namesetsNotFound = new LinkedList<>();

			checkDefinitions(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound,
					request, new SubProgressMonitor(monitor, 1));

			createCmlFiles(namesetsNotFound, valuesNotFound, channelsNotFound,
					chansetsNotFound, processesNotFound, request, response,
					new SubProgressMonitor(monitor, 1));

			createMissingDefinitionsMessage(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound,
					request, response);

			findDefinitions(definitions, request, response,
					new SubProgressMonitor(monitor, 1));

		} finally {
			monitor.done();
		}

	}

	private void findDefinitions(List<PDefinition> definitions,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		ICmlProject cmlProject = request.getSourceUnit().getProject();
		try {
			monitor.beginTask(
					formatSystemName(Message.FIND_PARSE_LIST_DEFINITIONS_TASK_NAME),
					cmlProject.getSourceUnits().size() + 2);
			response.getFolder().refreshLocal(IResource.DEPTH_ONE,
					new SubProgressMonitor(monitor, 1));
			request.getSourceUnit().getProject().getModel()
					.refresh(false, new SubProgressMonitor(monitor, 1));
			for (ICmlSourceUnit su : cmlProject.getSourceUnits()) {
				definitions.addAll(su.getParseListDefinitions());
				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}
	}

	private boolean createMissingDefinitionsMessage(
			List<String> namesetsNotFound, List<String> valuesNotFound,
			List<String> channelsNotFound, List<String> chansetsNotFound,
			List<String> processesNotFound,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {

		String message;
		if (channelsNotFound.isEmpty() && chansetsNotFound.isEmpty()
				&& processesNotFound.isEmpty() && valuesNotFound.isEmpty()) {
			message = null;
			return false;
		} else {
			String systemName = request.getSystemName();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);

			if (!namesetsNotFound.isEmpty()) {
				ps.println(Message.NAMESETS_NOT_FOUND.format(systemName,
						namesetsNotFound));
			}

			if (!valuesNotFound.isEmpty()) {
				ps.println(Message.VALUES_NOT_FOUND.format(systemName,
						valuesNotFound));
			}

			if (!channelsNotFound.isEmpty()) {
				ps.println(Message.CHANNELS_NOT_FOUND.format(systemName,
						channelsNotFound));
			}

			if (!chansetsNotFound.isEmpty()) {
				ps.println(Message.CHANSETS_NOT_FOUND.format(systemName,
						chansetsNotFound));
			}

			if (!processesNotFound.isEmpty()) {
				ps.println(Message.PROCESSES_NOT_FOUND.format(systemName,
						processesNotFound));
			}
			message = new String(baos.toByteArray());
			response.setDefinitionsMessage(message);
			return true;
		}
	}

	private void createCmlFiles(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(
					formatSystemName(Message.CREATE_CML_FILES_TASK_NAME), 2);
			createFaultToleranceBaseFile(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound,
					request, response, new SubProgressMonitor(monitor, 1));
			createFaultToleranceProcessesFile(namesetsNotFound, valuesNotFound,
					channelsNotFound, chansetsNotFound, processesNotFound,
					request, response, new SubProgressMonitor(monitor, 1));
		} finally {
			monitor.done();
		}
	}

	private void createFaultToleranceProcessesFile(
			List<String> namesetsNotFound, List<String> valuesNotFound,
			List<String> channelsNotFound, List<String> chansetsNotFound,
			List<String> processesNotFound,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(
					formatSystemName(Message.CREATE_CML_FILES_TASK_NAME), 1);
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
				writeFile(formatSystemName(Message.CML_PROCESSES_FILE_NAME),
						baos.toByteArray(), request, response,
						new SubProgressMonitor(monitor, 1));
			} else {
				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}
	}

	private void createFaultToleranceBaseFile(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(
					formatSystemName(Message.CREATE_CML_FILES_TASK_NAME), 2);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);

			boolean requires;
			requires = createBaseFileChansets(chansetsNotFound, ps);
			requires = createBaseFileProcesses(processesNotFound, ps)
					|| requires;

			if (requires) {
				writeFile(formatSystemName(Message.BASE_CML_FILE_NAME),
						baos.toByteArray(), request, response,
						new SubProgressMonitor(monitor, 1));

			} else {
				monitor.worked(2);
			}
		} finally {
			monitor.done();
		}
	}

	private boolean createBaseFileProcesses(List<String> processesNotFound,
			PrintStream ps) {
		List<String> processesToAdd = new LinkedList<>();
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

		if (!chansetsNotFound
				.contains(formatSystemName(Message.CHANSET_H_NAME))) {
			StringTokenizer relatedToH = new StringTokenizer(
					formatSystemName(Message.CHANSET_H_RELATED), ",");
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
		String name = formatSystemName(nameMessage);
		if (namesNotFound.contains(name)) {
			templateList.add(formatSystemName(template));
		}
		namesNotFound.remove(name);
	}

	private void checkDefinitions(List<String> namesetsNotFound,
			List<String> valuesNotFound, List<String> channelsNotFound,
			List<String> chansetsNotFound, List<String> processesNotFound,
			IFaultToleranceVerificationRequest request, IProgressMonitor monitor)
			throws CoreException {
		try {
			monitor.beginTask(
					formatSystemName(Message.DEFINITIONS_VERIFICATION_TASK_NAME),
					10);

			Set<String> channelNames = new TreeSet<>();
			Set<String> chansetNames = new TreeSet<>();
			Set<String> processNames = new TreeSet<>();
			Set<String> valueNames = new TreeSet<>();
			Set<String> namesetNames = new TreeSet<>();
			findDefinitions(AChannelDefinition.class, channelNames, request,
					new SubProgressMonitor(monitor, 1));
			findDefinitions(AChansetDefinition.class, chansetNames, request,
					new SubProgressMonitor(monitor, 1));
			findDefinitions(AProcessDefinition.class, processNames, request,
					new SubProgressMonitor(monitor, 1));
			findDefinitions(AValueDefinition.class, valueNames, request,
					new SubProgressMonitor(monitor, 1));
			findDefinitions(ANamesetDefinition.class, valueNames, request,
					new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_NAMESETS, namesetsNotFound,
					namesetNames, request, new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_VALUES, valuesNotFound,
					valueNames, request, new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_CHANNELS, channelsNotFound,
					channelNames, request, new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_CHANSETS, chansetsNotFound,
					chansetNames, request, new SubProgressMonitor(monitor, 1));
			checkNames(Message.EXISTING_NEEDED_PROCESSES, processesNotFound,
					processNames, request, new SubProgressMonitor(monitor, 1));
		} finally {
			monitor.done();
		}

	}

	private <T extends PDefinition> void findDefinitions(Class<T> cl,
			Set<String> names, IFaultToleranceVerificationRequest request,
			IProgressMonitor monitor) throws CoreException {
		ICmlProject cmlProject = request.getSourceUnit().getProject();
		try {
			monitor.beginTask(formatSystemName(Message.CHECK_NAMES_TASK),
					cmlProject.getSourceUnits().size());
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
			Set<String> existingNames,
			IFaultToleranceVerificationRequest request, IProgressMonitor monitor) {
		try {
			StringTokenizer namesTokenizer = new StringTokenizer(
					formatSystemName(namesMessage), ",");
			monitor.beginTask(formatSystemName(Message.CHECK_NAMES_TASK),
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

	private IStatus createFormulaFiles(List<PDefinition> definitions,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException, IOException,
			AnalysisException {
		try {
			monitor.beginTask(
					formatSystemName(Message.CREATE_FORMULA_FILES_TASK_NAME), 6);

			createFormulaFile(definitions, response.getDeadlockFreedom(),
					request, response, new SubProgressMonitor(monitor, 1));
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			createFormulaFile(definitions, response.getDivergenceFreedom(),
					request, response, new SubProgressMonitor(monitor, 1));
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			createFormulaFile(definitions, response.getSemifairness(), request,
					response, new SubProgressMonitor(monitor, 1));
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			createFormulaFile(definitions, response.getLimitedFaultTolerance(),
					request, response, new SubProgressMonitor(monitor, 1));
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			createFormulaFile(definitions, response.getFullFaultTolerance(),
					request, response, new SubProgressMonitor(monitor, 1));

			response.getFolder()
					.getParent()
					.refreshLocal(IResource.DEPTH_ONE,
							new SubProgressMonitor(monitor, 1));
			return Status.OK_STATUS;
		} finally {
			monitor.done();
		}
	}

	private void createFormulaFile(List<PDefinition> definitions,
			FaultToleranceProperty property,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws IOException, AnalysisException,
			CoreException {
		try {
			monitor.beginTask(
					Message.CREATE_FORMULA_FILE.format(request.getSystemName(),
							property.getFormulaScriptFile().getName()), 1);
			NewMCVisitor adaptor = new NewMCVisitor();
			String formulaScriptContent = adaptor.generateFormulaScript(
					definitions, property.getModelCheckerProperty(),
					property.getImplementationExpression());
			writeFile(property.getFormulaScriptFile().getName(),
					formulaScriptContent, request, response,
					new SubProgressMonitor(monitor, 1));
		} finally {
			monitor.done();
		}
	}

	private void writeFile(String fileName, String contents,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {
		writeFile(fileName, contents.getBytes(), request, response, monitor);
	}

	private void writeFile(String fileName, byte[] contents,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException {

		try {
			monitor.beginTask(Message.WRITE_FILE.format(
					request.getSystemName(), fileName), 3);

			IFile outputFile = response.getFolder().getFile(fileName);

			InputStream in = new ByteArrayInputStream(contents);
			if (outputFile.exists()) {
				outputFile.setContents(in, false, false,
						new SubProgressMonitor(monitor, 2));
			} else {
				outputFile
						.create(in, false, new SubProgressMonitor(monitor, 1));
			}
		} finally {
			monitor.done();
		}
	}

}
