/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;


/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceVerificationEvent"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationEvent {
	private final FaultToleranceVerificationResults results;
	private final boolean success;

	public FaultToleranceVerificationEvent(
			FaultToleranceVerificationResults results, boolean success) {
		this.results = results;
		this.success = success;
	}

	public FaultToleranceVerificationResults getResults() {
		return results;
	}

	public boolean isSuccess() {
		return success;
	}

}
