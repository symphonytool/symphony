package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.pog.pub.IPogAssistantFactory;
import org.overture.pog.utility.PogAssistantFactory;
import org.overture.pog.visitors.IVariableSubVisitor;

/**
 * An extension of the assistant factory for the CML POG.
 * @author ldc
 *
 */
public class CmlPogAssistantFactory extends PogAssistantFactory implements
		IPogAssistantFactory
{

	@Override
	public IVariableSubVisitor getVarSubVisitor()
	{
		return new CmlVarSubVisitor();
	}

}
