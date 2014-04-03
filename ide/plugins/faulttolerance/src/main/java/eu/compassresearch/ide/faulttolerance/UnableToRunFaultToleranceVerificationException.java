/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class UnableToRunFaultToleranceVerificationException"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class UnableToRunFaultToleranceVerificationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6791707308196632425L;

	public UnableToRunFaultToleranceVerificationException(Message message,
			Throwable cause, Object... args) {
		super(message.format(args), cause);
	}

	public UnableToRunFaultToleranceVerificationException(Message message,
			Object... args) {
		super(message.format(args));
	}

}
