/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.core.resources.IFolder;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class IFaultToleranceVerificationResponse"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public interface IFaultToleranceVerificationResponse {
	FaultToleranceProperty getDivergenceFreedom();

	FaultToleranceProperty getSemifairness();

	FaultToleranceProperty getLimitedFaultTolerance();

	FaultToleranceProperty getFullFaultTolerance();

	FaultToleranceProperty getDeadlockFreedom();

	Iterable<FaultToleranceProperty> properties();

	IFolder getFolder();

	void setFolder(IFolder folder);

	Exception getException();

	void setException(Exception e);

	void setDefinitionsMessage(String message);

	String getDefinitionsMessage();
}
