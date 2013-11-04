package eu.compassresearch.core.typechecker.weeding;

import java.util.Collection;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.ABracketType;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.PSource;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class Weeding2 extends DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		Weeding2 lv = new Weeding2();
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

	private Weeding2()
	{
	}

	@Override
	public void caseABracketType(ABracketType node) throws AnalysisException
	{
		node.parent().replaceChild(node, node.getType());
		node.getType().apply(this);
	}

	// @Override
	// public void caseAUnresolvedPathExp(AUnresolvedPathExp node
	// ) throws AnalysisException
	// {
	//
	// if(node.getIdentifiers().isEmpty())
	// {
	// assert false: "Oops missing identifiers";
	//
	// }
	//
	// if(node.parent() instanceof PStm || node.parent() instanceof PAction)
	// {
	// //state designatores
	// return;
	// }else
	// {
	// PExp exp =null;
	// Iterator<ILexIdentifierToken> itr = node.getIdentifiers().iterator();
	//
	// ILexIdentifierToken id = itr.next();
	//
	// String module = "";
	// if(isClassName(id))
	// {
	// module = id.getName();
	// if(itr.hasNext())
	// {
	// itr.next();
	// }else
	// {
	// assert false: "Missing identifier for static reference";
	// return;
	// }
	// }
	//
	//
	// ILexLocation location = id.getLocation();
	// LexLocation loc = new LexLocation(location .getFile(), id.getName(),
	// location.getStartLine(), location.getStartPos(), location.getEndLine(),
	// location.getEndPos(), location.getStartOffset(), location.getEndOffset());
	//
	// ILexNameToken name = new LexNameToken(module, id.getName(), loc);
	//
	// exp = AstFactory.newAVariableExp(name);
	//
	// while(itr.hasNext())
	// {
	// exp = AstFactory.newAFieldExp(exp, itr.next());
	// }
	// node.parent().replaceChild(node, exp);
	// exp.parent(node.parent());
	// exp.apply(this);
	// }
	// PExp obj = AstFactory.newAVariableExp(node.getIdentifiers().get(0).getClassName());
	// PExp fieldExp = AstFactory.newAFieldExp(obj, node.getIdentifiers().get(1));
	//
	// node.parent().replaceChild(node, fieldExp);
	// return fieldExp.apply(this, question);
	// }
}
