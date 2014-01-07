/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification.jobs;

import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.jobs, class VerificationsManager"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class VerificationsManager {
	private final static VerificationsManager def;
	private final ILock divergenceLock;
	private final ILock semifairnessLock;


	public VerificationsManager() {
		divergenceLock = Job.getJobManager().newLock();
		semifairnessLock = Job.getJobManager().newLock();
	}

	static {
		def = new VerificationsManager();
	}

	public static VerificationsManager getDefault() {
		return def;
	}

	public ILock getDivergenceLock() {
		return divergenceLock;
	}

	public ILock getSemifairnessLock() {
		return semifairnessLock;
	}
}
