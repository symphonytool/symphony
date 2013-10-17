package eu.compassresearch.core.typechecker.api;

import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.intf.ICMLQuestionAnswer;

/**
 * A CmlRootVisitor is used to override functionality in the CML Type Checker. Each visitor in will invoke the root
 * visitor for all recursive descents and thus the root visitor can take control any time during type checking. A
 * developer for an extension to CML can implement this interface to override any wanted behaviour.
 * 
 * @author rwl
 */
public interface ICmlRootVisitor extends
// CML cases
		ICMLQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType>,
		// Overture cases
		IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType>
{
}
