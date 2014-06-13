package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;
import org.overture.pog.obligation.TypeCompatibility;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IPogAssistantFactory;

public class CmlSubTypeObligation extends TypeCompatibility
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * CMl Version of SubtypeObligation for Operations. Used because we need to manipulate the different operation node.
	 * Factory Method since we need to return null STOs (which should be discarded).
	 * 
	 * @param exp
	 *            The expression to be checked
	 * @param etype
	 *            The expected type
	 * @param atype
	 *            The actual type
	 * @param ctxt
	 *            Context Information
	 * @param af 
	 * @return
	 */
	public static CmlSubTypeObligation newInstance(
			AExplicitOperationDefinition def, PType actualResult,
			IPOContextStack ctxt, IPogAssistantFactory af)
	{
		AVariableExp result = AstFactory.newAVariableExp(new LexNameToken(def.getName().getModule(), "RESULT", def.getLocation()));

		CmlSubTypeObligation sto = new CmlSubTypeObligation(def, result, actualResult, ctxt,af);
		if (sto.getValueTree() != null)
		{
			return sto;
		}

		return null;
	}


	private CmlSubTypeObligation(AExplicitOperationDefinition def,
			PExp resultexp, PType actualResult, IPOContextStack ctxt, IPogAssistantFactory af)
	{
		super(def, def.getLocation(), resultexp, ((AOperationType) def.getType()).getResult().clone(), actualResult.clone(), ctxt,af);
	}

}
