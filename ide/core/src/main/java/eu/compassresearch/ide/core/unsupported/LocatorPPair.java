package eu.compassresearch.ide.core.unsupported;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPair;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.patterns.ARenamePair;

/**
 * Visitor to extract the {@link ILexLocation} from {@link PPair} nodes.
 * @author ldc
 *
 */
public class LocatorPPair extends AnswerCMLAdaptor<ILexLocation> {


	@Override
	public ILexLocation createNewReturnValue(INode node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILexLocation createNewReturnValue(Object node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILexLocation caseARenamePair(ARenamePair node)
			throws AnalysisException {
		return node.getFrom().getLocation();

	}

	@Override
	public ILexLocation caseAPatternTypePair(APatternTypePair node)
			throws AnalysisException {
		return node.getPattern().getLocation();
	}

	@Override
	public ILexLocation caseAPatternListTypePair(APatternListTypePair node)
			throws AnalysisException {
		if (!node.getPatterns().isEmpty()){
			return node.getPatterns().get(0).getLocation();
		}
		throw new AnalysisException("Could not extract pattern for PatternListTypePair");
	}


	
	
	
	
	

	
	
}
