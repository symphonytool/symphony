/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.faulttolerance.FaultToleranceProperty;
import eu.compassresearch.ide.faulttolerance.FaultToleranceType;
import eu.compassresearch.ide.faulttolerance.UnableToRunFaultToleranceVerificationException;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceResults"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationResults {
	private final FaultToleranceProperty divergenceFreedom;
	private final FaultToleranceProperty semifairness;
	private final FaultToleranceProperty fullFaultTolerance;
	private final FaultToleranceProperty limitedFaultTolerance;
	private boolean prerequisitesChecked;
	private String definitionsMessage;
	private String processName;
	private IResource resource;
	private ILexLocation location;
	private IContainer outputContainer;
	private ICmlProject cmlProject;
	private IFolder folder;
	private boolean cancelledByUser;

	private UnableToRunFaultToleranceVerificationException exception;

	private final List<Exception> otherExceptions;

	private final ILock prerequisitesLock;

	public FaultToleranceVerificationResults() {
		this.prerequisitesLock = Job.getJobManager().newLock();
		this.otherExceptions = new LinkedList<>();
		this.divergenceFreedom = FaultToleranceType.DivergenceFreedom
				.newProperty();
		this.semifairness = FaultToleranceType.Semifairness.newProperty();
		this.fullFaultTolerance = FaultToleranceType.FullFaultTolerance
				.newProperty();
		this.limitedFaultTolerance = FaultToleranceType.LimitedFaultTolerance
				.newProperty();
	}

	public void add(Exception e) {
		if (e != null) {
			synchronized (otherExceptions) {
				otherExceptions.add(e);
			}
		}
	}

	public void acquire() {
		prerequisitesLock.acquire();
	}

	public void release() {
		prerequisitesLock.release();
	}

	public boolean isDivergenceFree() {
		return divergenceFreedom.isSatisfied();
	}

	public boolean isSemifair() {
		return semifairness.isSatisfied();
	}

	public boolean isFullFaultTolerant() {
		return fullFaultTolerance.isSatisfied();
	}

	public boolean isLimitedFaultTolerant() {
		return limitedFaultTolerance.isSatisfied();
	}

	public String getProcessName() {
		return processName;
	}

	public ILock getPrerequisitesLock() {
		return prerequisitesLock;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public boolean isPrerequisitesChecked() {
		return prerequisitesChecked;
	}

	public void setPrerequisitesChecked(boolean prerequisitesChecked) {
		this.prerequisitesChecked = prerequisitesChecked;
	}

	public boolean isPreRequisitesOk() {
		return divergenceFreedom.isSatisfied() && semifairness.isSatisfied()
				&& definitionsMessage == null;
	}

	public IResource getResource() {
		return resource;
	}

	public ILexLocation getLocation() {
		return location;
	}

	public void setResource(IResource resource) {
		this.resource = resource;
	}

	public void setLocation(ILexLocation location) {
		this.location = location;
	}

	public void setException(
			UnableToRunFaultToleranceVerificationException exception) {
		this.exception = exception;
	}

	public IContainer getOutputContainer() {
		return outputContainer;
	}

	public void setOutputContainer(IContainer outputContainer) {
		this.outputContainer = outputContainer;
	}

	public ICmlProject getCmlProject() {
		return cmlProject;
	}

	public void setCmlProject(ICmlProject cmlProject) {
		this.cmlProject = cmlProject;
	}

	public IFolder getFolder() {
		return folder;
	}

	public void setFolder(IFolder folder) {
		this.folder = folder;
	}

	public boolean hasException() {
		return exception != null || !otherExceptions.isEmpty();
	}

	public String getExceptionsLocalizedMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		int i = 1;

		if (exception != null) {
			ps.print(i++);
			ps.print(". ");
			ps.println(exception.getLocalizedMessage());
		}

		for (Exception e : otherExceptions) {
			ps.print(i++);
			ps.print(". ");
			ps.println(e.getLocalizedMessage());
		}

		return new String(baos.toByteArray());
	}

	public void showExceptions(PrintStream ps) {
		if (exception != null) {
			exception.printStackTrace(ps);
		}
		for (Exception e : otherExceptions) {
			e.printStackTrace(ps);
		}
	}

	public Exception getFirstException() {
		if (exception != null) {
			return exception;
		}
		if (!otherExceptions.isEmpty()) {
			return otherExceptions.get(0);
		}
		return null;
	}

	public String getDefinitionsMessage() {
		return definitionsMessage;
	}

	public void setDefinitionsMessage(String definitionsMessage) {
		this.definitionsMessage = definitionsMessage;
	}

	public boolean isCancelledByUser() {
		return cancelledByUser;
	}

	public void setCancelledByUser(boolean cancelledByUser) {
		this.cancelledByUser = cancelledByUser;
	}

	public FaultToleranceProperty getDivergenceFreedom() {
		return divergenceFreedom;
	}

	public FaultToleranceProperty getSemifairness() {
		return semifairness;
	}

	public FaultToleranceProperty getFullFaultTolerance() {
		return fullFaultTolerance;
	}

	public FaultToleranceProperty getLimitedFaultTolerance() {
		return limitedFaultTolerance;
	}

}
