/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.IProgressService;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.Activator;
import eu.compassresearch.ide.faulttolerance.FaultToleranceProperty;
import eu.compassresearch.ide.faulttolerance.FaultToleranceType;
import eu.compassresearch.ide.faulttolerance.FaultToleranceVerificationResponseStatus;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.jobs.FilesPreparationJob;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class FaultToleranceVerificationHandler"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationHandler extends SelectProcessHandler {

	@Override
	public boolean isEnabled() {
		return Activator.getDefault().isModelCheckerOk();
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	private class Request implements IFaultToleranceVerificationRequest {
		private String requestId;
		private String systemName;
		private int lineNumber;
		private int charStart;
		private int charEnd;
		private ICmlSourceUnit sourceUnit;

		@Override
		public String getRequestId() {
			return requestId;
		}

		@Override
		public String getSystemName() {
			return systemName;
		}

		@Override
		public int getLineNumber() {
			return lineNumber;
		}

		@Override
		public int getCharStart() {
			return charStart;
		}

		@Override
		public int getCharEnd() {
			return charEnd;
		}

		@Override
		public ICmlSourceUnit getSourceUnit() {
			return sourceUnit;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}

		public void setSystemName(String systemName) {
			this.systemName = systemName;
		}

		public void setLineNumber(int lineNumber) {
			this.lineNumber = lineNumber;
		}

		public void setCharStart(int charStart) {
			this.charStart = charStart;
		}

		public void setCharEnd(int charEnd) {
			this.charEnd = charEnd;
		}

		public void setSourceUnit(ICmlSourceUnit sourceUnit) {
			this.sourceUnit = sourceUnit;
		}

	}

	private class Response implements IFaultToleranceVerificationResponse {
		private final FaultToleranceProperty divergenceFreedom;
		private final FaultToleranceProperty semifairness;
		private final FaultToleranceProperty limitedFaultTolerance;
		private final FaultToleranceProperty fullFaultTolerance;
		private final List<FaultToleranceProperty> properties;
		private IFolder folder;
		private FaultToleranceVerificationResponseStatus status;
		private Exception exception;
		private String definitionsMessage;

		public Response() {
			divergenceFreedom = new FaultToleranceProperty(
					FaultToleranceType.DivergenceFreedom);
			semifairness = new FaultToleranceProperty(
					FaultToleranceType.Semifairness);
			limitedFaultTolerance = new FaultToleranceProperty(
					FaultToleranceType.LimitedFaultTolerance);
			fullFaultTolerance = new FaultToleranceProperty(
					FaultToleranceType.FullFaultTolerance);
			this.properties = new ArrayList<>(4);
			this.properties.add(divergenceFreedom);
			this.properties.add(semifairness);
			this.properties.add(limitedFaultTolerance);
			this.properties.add(fullFaultTolerance);
		}

		@Override
		public Iterable<FaultToleranceProperty> properties() {
			return properties;
		}

		@Override
		public FaultToleranceProperty getDivergenceFreedom() {
			return divergenceFreedom;
		}

		@Override
		public FaultToleranceProperty getSemifairness() {
			return semifairness;
		}

		@Override
		public FaultToleranceProperty getLimitedFaultTolerance() {
			return limitedFaultTolerance;
		}

		@Override
		public FaultToleranceProperty getFullFaultTolerance() {
			return fullFaultTolerance;
		}

		@Override
		public IFolder getFolder() {
			return folder;
		}

		@Override
		public FaultToleranceVerificationResponseStatus getStatus() {
			return status;
		}

		@Override
		public Exception getException() {
			return exception;
		}

		@Override
		public String getDefinitionsMessage() {
			return definitionsMessage;
		}

		@Override
		public void setFolder(IFolder folder) {
			this.folder = folder;
		}

		@Override
		public void setStatus(FaultToleranceVerificationResponseStatus status) {
			this.status = status;
		}

		@Override
		public void setException(Exception exception) {
			this.exception = exception;
		}

		@Override
		public void setDefinitionsMessage(String definitionsMessage) {
			this.definitionsMessage = definitionsMessage;
		}

	}

	@Override
	protected void doOnSelectedProcess(ICmlSourceUnit su,
			PDefinition processDefinition, Shell shell)
			throws ExecutionException {
		Request request = new Request();
		// TODO set request id
		request.setRequestId(null);
		request.setSystemName(processDefinition.getName().getFullName());
		request.setSourceUnit(su);

		ILexLocation location = processDefinition.getLocation();
		request.setLineNumber(location.getStartLine());
		request.setCharStart(location.getStartOffset());
		request.setCharEnd(location.getEndOffset() + 1);

		Response response = new Response();

		updateFilesAndFolders(request, response);

		IProgressService progressService = Activator.getDefault()
				.getWorkbench().getProgressService();

		startJobs(createJobs(request, response), progressService, shell);
	}

	private void updateFilesAndFolders(Request request, Response response) {
		String folderName = Message.FOLDER_NAME.format(request.getSystemName());
		IContainer container = request.getSourceUnit().getProject()
				.getModelBuildPath().getOutput();
		IFolder folder = container.getFolder(new Path(folderName));
		response.setFolder(folder);

		// TODO put CML files as well?

		setFile(response.getDivergenceFreedom(),
				Message.DIVERGENCE_FREEDOM_FORMULA_SCRIPT_FILE_NAME, request,
				response);

		setFile(response.getSemifairness(),
				Message.SEMIFAIRNESS_FORMULA_SCRIPT_FILE_NAME, request,
				response);

		setFile(response.getLimitedFaultTolerance(),
				Message.LIMITED_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME,
				request, response);

		setFile(response.getFullFaultTolerance(),
				Message.FULL_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME, request,
				response);
	}

	private void setFile(FaultToleranceProperty property,
			Message formulaScriptFileName, Request request, Response response) {
		IFile file = response.getFolder().getFile(
				formulaScriptFileName.format(request.getSystemName()));
		property.setFormulaScriptFile(file);
	}

	private void startJobs(List<Set<Job>> jobsSets,
			IProgressService progressService, Shell shell) {
		int order = 1;
		for (Set<Job> jobs : jobsSets) {
			for (Job job : jobs) {
				progressService.showInDialog(shell, job);
				OrderSchedulingRule orderRule = new OrderSchedulingRule(order);
				job.setRule(MultiRule.combine(orderRule, job.getRule()));
				job.schedule();
			}
			order++;
		}
	}

	private List<Set<Job>> createJobs(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		List<Set<Job>> jobsSets = new LinkedList<>();
		Set<Job> jobs1 = new HashSet<>();
		jobsSets.add(jobs1);
		jobs1.add(new FilesPreparationJob(request, response));

		// FIXME add model-checking jobs

		return jobsSets;

	}

}
