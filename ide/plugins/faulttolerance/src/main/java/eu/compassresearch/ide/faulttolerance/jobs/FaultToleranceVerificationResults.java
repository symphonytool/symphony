/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
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
	private int verifications;
	@Deprecated
	private String limitExpression;
	private String processName;
	private IResource resource;
	private ILexLocation location;
	private IContainer outputContainer;
	private ICmlSourceUnit cmlSourceUnit;
	private ICmlProject cmlProject;

	private UnableToRunFaultToleranceVerificationException exception;

	private final ILock prerequisitesLock;
	private final ILock verificationsLock;

	public FaultToleranceVerificationResults() {
		prerequisitesLock = Job.getJobManager().newLock();
		verificationsLock = Job.getJobManager().newLock();
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

	@Deprecated
	public String getLimitExpression() {
		return limitExpression;
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

	@Deprecated
	public void setLimitExpression(String limitExpression) {
		this.limitExpression = limitExpression;
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
		return divergenceFree && semifair;
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

	public void incrementVerification() {
		try {
			verificationsLock.acquire();
			verifications++;
		} finally {
			verificationsLock.release();
		}
	}

	public boolean isAllVerificationsChecked(int maxVerifications) {
		return verifications == maxVerifications;
	}

	public UnableToRunFaultToleranceVerificationException getException() {
		return exception;
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

	public ICmlSourceUnit getCmlSourceUnit() {
		return cmlSourceUnit;
	}

	public void setCmlSourceUnit(ICmlSourceUnit cmlSourceUnit) {
		this.cmlSourceUnit = cmlSourceUnit;
	}

	public ICmlProject getCmlProject() {
		return cmlProject;
	}

	public void setCmlProject(ICmlProject cmlProject) {
		this.cmlProject = cmlProject;
	}

}
