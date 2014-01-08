/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification.jobs;

import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.jobs, class FaultToleranceResults"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationResults {
	private boolean divergenceFree;
	private boolean semifair;
	private boolean fullFaultTolerant;
	private boolean limitedFaultTolerant;
	private boolean prerequisitesChecked;
	private String limitExpression;
	private String processName;

	private final ILock prerequisitesLock;

	public FaultToleranceVerificationResults() {
		prerequisitesLock = Job.getJobManager().newLock();
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

}
