package eu.compassresearch.core.typechecker.weeding;

import java.util.Collection;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.PSource;

/**
 * 
 * 
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class Weeding1 extends DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		Weeding1 lv = new Weeding1();
		for (PSource s : lp)
		{
			if (s != null)
				try
				{
					s.apply(lv);
				} catch (AnalysisException e)
				{
				}
		}
	}

	private Weeding1()
	{
	}

	@Override
	public void caseAImplicitFunctionDefinition(AImplicitFunctionDefinition node)
			throws AnalysisException
	{
		flattenProductParameterType(node.getType());
	}

	@Override
	public void caseAExplicitFunctionDefinition(AExplicitFunctionDefinition node)
			throws AnalysisException
	{
		flattenProductParameterType(node.getType());
	}

	private void flattenProductParameterType(PType type)
	{
		if (type instanceof AFunctionType)
		{
			flattenProductParameterType((AFunctionType) type);
		}
	}

	/*
	 * VDMPP parser adds types one by one for the outer product type. E.g. f: int * int -> int has arg-types [int,int]
	 * rather than (int * int).
	 * @param pdef
	 */
	private void flattenProductParameterType(AFunctionType fnType)
	{
		if (fnType.getParameters().size() == 1)
		{
			PType parameters = fnType.getParameters().get(0);
			LinkedList<PType> types = new LinkedList<PType>();

			if (parameters instanceof AProductType)
			{
				// Expand unbracketed product types
				AProductType pt = (AProductType) parameters;
				types.addAll(pt.getTypes());
			} else if (parameters instanceof AVoidType)
			{
				// No type
			} else
			{
				// One parameter, including bracketed product types
				types.add(parameters);
			}

			fnType.setParameters(types);
		}

	}
}
