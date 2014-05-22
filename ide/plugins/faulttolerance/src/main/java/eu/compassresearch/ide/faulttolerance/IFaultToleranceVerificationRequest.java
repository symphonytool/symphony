/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;


/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class IFaultToleranceVerificationRequest"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public interface IFaultToleranceVerificationRequest {
	String getRequestId();
	
	String getSystemName();

	int getLineNumber();

	int getCharStart();

	int getCharEnd();

	ICmlSourceUnit getSourceUnit();

}
