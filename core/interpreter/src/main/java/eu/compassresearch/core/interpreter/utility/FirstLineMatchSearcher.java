package eu.compassresearch.core.interpreter.utility;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;

public class FirstLineMatchSearcher extends DepthFirstAnalysisCMLAdaptor
{
	public static INode search(List<PDefinition> sourceForest, URI file,
			int line)
	{
		FirstLineMatchSearcher searcher = new FirstLineMatchSearcher(new File(file), line);
		try
		{
			for (PDefinition def : sourceForest)
			{
				def.apply(searcher);
			}
		} catch (AnalysisException e)
		{

		}
		return searcher.matched;
	}

	private INode matched;
	private File file;
	private int line;

	public FirstLineMatchSearcher(File file, int line)
	{
		this.file = file;
		this.line = line;
	}
	
	@Override
	public void caseAActionClassDefinition(AActionClassDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		super.caseAActionClassDefinition(node);
	}

	@Override
	public void defaultInPExp(PExp node) throws AnalysisException
	{
		check(node, node.getLocation());
	}

	@Override
	public void defaultInPStm(PStm node) throws AnalysisException
	{
		check(node, node.getLocation());
	}

	private void check(INode node, ILexLocation loc) throws AnalysisException
	{
		if (loc.getFile().equals(file) && loc.getStartLine() == line)
		{
			this.matched = node;
			throw new AnalysisException("Match found");
		}
	}
}
