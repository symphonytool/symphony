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
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceModelCheckingJob;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationCleanupJob;
import eu.compassresearch.ide.faulttolerance.jobs.FilesPreparationJob;
import eu.compassresearch.ide.faulttolerance.jobs.IFaultToleranceVerificationPreRequisite;
import eu.compassresearch.ide.faulttolerance.jobs.MarkerUpdaterJob;
import eu.compassresearch.ide.modelchecker.MCConstants;

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

		prepareFolder(request, response);

		prepareDivergenceFreedom(request, response);
		prepareSemifairness(request, response);
		prepareLimitedFaultTolerance(request, response);
		prepareFullFaultTolerance(request, response);

		IProgressService progressService = Activator.getDefault()
				.getWorkbench().getProgressService();

		startJobs(createJobs(request, response), progressService, shell);
	}

	private void prepareFolder(Request request, Response response) {
		String folderName = Message.FOLDER_NAME.format(request.getSystemName());
		IContainer container = request.getSourceUnit().getProject()
				.getModelBuildPath().getOutput();
		IFolder folder = container.getFolder(new Path(folderName));
		response.setFolder(folder);
	}

	private void prepareFullFaultTolerance(Request request, Response response) {
		FaultToleranceProperty property = response.getFullFaultTolerance();
		property.setModelCheckerProperty(MCConstants.DEADLOCK_PROPERTY);
		setImplementationExpression(property,
				Message.LAZY_DEADLOCK_CHECK_PROCESS_NAME, request);
		setFile(property,
				Message.FULL_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME, request,
				response);
	}

	private void prepareLimitedFaultTolerance(Request request, Response response) {
		FaultToleranceProperty property = response.getLimitedFaultTolerance();
		property.setModelCheckerProperty(MCConstants.DEADLOCK_PROPERTY);
		setImplementationExpression(property,
				Message.LAZY_LIMIT_DEADLOCK_CHECK_PROCESS_NAME, request);
		setFile(property,
				Message.LIMITED_FAULT_TOLERANCE_FORMULA_SCRIPT_FILE_NAME,
				request, response);
	}

	private void prepareSemifairness(Request request, Response response) {
		FaultToleranceProperty property = response.getSemifairness();
		property.setModelCheckerProperty(MCConstants.LIVELOCK_PROPERTY);
		setImplementationExpression(property,
				Message.SEMIFAIRNESS_PROCESS_NAME, request);
		setFile(property, Message.SEMIFAIRNESS_FORMULA_SCRIPT_FILE_NAME,
				request, response);
	}

	private void prepareDivergenceFreedom(Request request, Response response) {
		FaultToleranceProperty property = response.getDivergenceFreedom();
		property.setModelCheckerProperty(MCConstants.LIVELOCK_PROPERTY);
		setImplementationExpression(property,
				Message.DIVERGENCE_FREEDOM_PROCESS_NAME, request);
		setFile(property, Message.DIVERGENCE_FREEDOM_FORMULA_SCRIPT_FILE_NAME,
				request, response);
	}

	private void setImplementationExpression(FaultToleranceProperty property,
			Message implementationNameMessage, Request request) {
		property.setImplementationExpression(implementationNameMessage
				.format(request.getSystemName()));
	}

	private void setFile(FaultToleranceProperty property,
			Message formulaScriptFileName, Request request, Response response) {
		IFile file = response.getFolder().getFile(
				formulaScriptFileName.format(request.getSystemName()));
		property.setFormulaScriptFile(file);
	}

	private void startJobs(List<Set<Job>> jobsSets,
			IProgressService progressService, Shell shell) {
		int group = 1;
		for (Set<Job> jobs : jobsSets) {
			int index = 1;
			for (Job job : jobs) {
				// progressService.showInDialog(shell, job);
				OrderSchedulingRule orderRule = new OrderSchedulingRule(group,
						index);
				job.setRule(MultiRule.combine(orderRule, job.getRule()));
				job.schedule();
				index++;
			}
			group++;
		}
	}

	private List<Set<Job>> createJobs(
			IFaultToleranceVerificationRequest request,
			final IFaultToleranceVerificationResponse response) {
		List<Set<Job>> jobsSets = new LinkedList<>();
		Set<Job> jobs1 = new HashSet<>();
		jobsSets.add(jobs1);
		jobs1.add(new FilesPreparationJob(request, response));

		Set<Job> jobs2 = new HashSet<>();
		jobsSets.add(jobs2);
		jobs2.add(new FaultToleranceModelCheckingJob(response
				.getDivergenceFreedom(), request, response));
		jobs2.add(new FaultToleranceModelCheckingJob(
				response.getSemifairness(), request, response));

		IFaultToleranceVerificationPreRequisite divergenceFreeAndSemifair = new IFaultToleranceVerificationPreRequisite() {
			@Override
			public boolean checkPreRequisite() {
				return response.getDivergenceFreedom().isSatisfied()
						&& response.getSemifairness().isSatisfied();
			}
		};

		Set<Job> jobs3 = new HashSet<>();
		jobsSets.add(jobs3);
		FaultToleranceModelCheckingJob limitedJob = new FaultToleranceModelCheckingJob(
				response.getLimitedFaultTolerance(), request, response);
		FaultToleranceModelCheckingJob fullJob = new FaultToleranceModelCheckingJob(
				response.getFullFaultTolerance(), request, response);
		limitedJob.add(divergenceFreeAndSemifair);
		fullJob.add(divergenceFreeAndSemifair);
		limitedJob.setUser(true);
		jobs3.add(limitedJob);
		jobs3.add(fullJob);

		Set<Job> jobs4 = new HashSet<>();
		jobsSets.add(jobs4);
		Job cleanupJob = new FaultToleranceVerificationCleanupJob(request,
				response);
		jobs4.add(cleanupJob);

		Set<Job> jobs5 = new HashSet<>();
		jobsSets.add(jobs5);
		Job markerJob = new MarkerUpdaterJob(request, response);
		jobs5.add(markerJob);

		return jobsSets;

	}

}
