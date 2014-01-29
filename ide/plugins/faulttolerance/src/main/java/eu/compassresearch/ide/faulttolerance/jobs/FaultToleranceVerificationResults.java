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
import eu.compassresearch.ide.faulttolerance.UnableToRunFaultToleranceVerificationException;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceResults"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationResults {
	private boolean divergenceFree;
	private boolean semifair;
	private boolean fullFaultTolerant;
	private boolean limitedFaultTolerant;
	private boolean prerequisitesChecked;
	private String definitionsMessage;
	private String processName;
	private IResource resource;
	private ILexLocation location;
	private IContainer outputContainer;
	private ICmlProject cmlProject;
	private IFolder folder;

	private UnableToRunFaultToleranceVerificationException exception;

	private final List<Exception> otherExceptions;

	private final ILock prerequisitesLock;

	public FaultToleranceVerificationResults() {
		prerequisitesLock = Job.getJobManager().newLock();
		otherExceptions = new LinkedList<>();
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
		return divergenceFree;
	}

	public boolean isSemifair() {
		return semifair;
	}

	public boolean isFullFaultTolerant() {
		return fullFaultTolerant;
	}

	public boolean isLimitedFaultTolerant() {
		return limitedFaultTolerant;
	}

	public String getProcessName() {
		return processName;
	}

	public ILock getPrerequisitesLock() {
		return prerequisitesLock;
	}

	public void setDivergenceFree(boolean divergenceFree) {
		this.divergenceFree = divergenceFree;
	}

	public void setSemifair(boolean semifair) {
		this.semifair = semifair;
	}

	public void setFullFaultTolerant(boolean fullFaultTolerant) {
		this.fullFaultTolerant = fullFaultTolerant;
	}

	public void setLimitedFaultTolerant(boolean limitedFaultTolerant) {
		this.limitedFaultTolerant = limitedFaultTolerant;
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
		return divergenceFree && semifair && definitionsMessage == null;
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
			ps.print(i);
			ps.print(". ");
			ps.println(exception.getLocalizedMessage());
		}

		for (Exception e : otherExceptions) {
			ps.print(++i);
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

}
