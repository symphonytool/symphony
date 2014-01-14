/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;


/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class IFaultToleranceVerificationListener"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public interface IFaultToleranceVerificationListener {
	void divergenceFreeVerificationStarted();

	void divergenceFreeVerificationFinished(
			FaultToleranceVerificationEvent event);

	void semifairnessVerificationStarted();

	void semifairnessVerificationFinished(FaultToleranceVerificationEvent event);

	void fullFaultToleranceVerificationStarted();

	void fullFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event);

	void limitedFaultToleranceVerificationStarted();

	void limitedFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event);

	void faultToleranceVerificationsFinished(
			FaultToleranceVerificationEvent event);
}
