package eu.compassresearch.core.typechecker.weeding;

import java.util.Collection;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexIdentifierToken;

import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.program.PSource;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class Weeding3UnfoldSingleDeclIdentifiers extends
		DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		Weeding3UnfoldSingleDeclIdentifiers lv = new Weeding3UnfoldSingleDeclIdentifiers();
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

	private Weeding3UnfoldSingleDeclIdentifiers()
	{
	}

	@Override
	public void caseAActionDefinition(AActionDefinition node)
			throws AnalysisException
	{
		LinkedList<PParametrisation> decelrations = new LinkedList<PParametrisation>();

//		for (PParametrisation decl : node.getDeclarations())
//		{
//
//			ATypeSingleDeclaration tDecl = decl.getDeclaration();
//
//			if (tDecl.getIdentifiers().size() > 1)
//			{
//				// unfold
//				@SuppressWarnings("unchecked")
//				LinkedList<ILexIdentifierToken> identifiers = (LinkedList<ILexIdentifierToken>) tDecl.getIdentifiers().clone();
//				for (ILexIdentifierToken id : identifiers)
//				{
//					PParametrisation idDecl = decl.clone();
//					LinkedList<ILexIdentifierToken> tmp = new LinkedList<ILexIdentifierToken>();
//					tmp.add(id);
//					idDecl.getDeclaration().setIdentifiers(tmp);
//					decelrations.add(idDecl);
//
//				}
//				continue;
//			}
//
//			decelrations.add(decl);
//
//		}
		node.setDeclarations(decelrations);
	}

}
