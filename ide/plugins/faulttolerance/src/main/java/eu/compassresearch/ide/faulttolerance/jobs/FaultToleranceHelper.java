/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceHelper"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceHelper {
	public static void schedule(FaultToleranceVerificationResults results,
			IFaultToleranceVerificationListener listener) {
		final FullFaultToleranceVerificationJob fftj = new FullFaultToleranceVerificationJob(
				results);
		final LimitedFaultToleranceVerificationJob lftj = new LimitedFaultToleranceVerificationJob(
				results);

		if (listener != null) {
			fftj.add(listener);
			lftj.add(listener);
		}

		fftj.schedule();
		lftj.schedule();
	}
}
