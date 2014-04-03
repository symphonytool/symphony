/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class FaultToleranceType"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public enum FaultToleranceType {
	DivergenceFreedom(Message.DIVERGENCE_FREEDOM_TYPE_NAME), Semifairness(
			Message.SEMIFAIRNESS_TYPE_NAME), FullFaultTolerance(
			Message.FULL_FAULT_TOLERANCE_TYPE_NAME), LimitedFaultTolerance(
			Message.LIMITED_FAULT_TOLERANCE_TYPE_NAME), DeadlockFreedom(
			Message.DEADLOCK_FREEDOM_TYPE_NAME);

	private Message message;

	private FaultToleranceType(Message message) {
		this.message = message;
	}
	
	public String formattedName() {
		return message.format();
	}

	public FaultToleranceProperty newProperty() {
		return new FaultToleranceProperty(this);
	}
}
