package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.pog.obligation.NonZeroObligation;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IPogAssistantFactory;

public class CmlNonZeroTimeObligation extends NonZeroObligation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CmlNonZeroTimeObligation(PExp exp,
			IPOContextStack ctxt, IPogAssistantFactory af) throws AnalysisException {
		super(exp.getLocation(), exp, ctxt, af);

	}


}
