package eu.compassresearch.core.analysis.pog.utility;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;

public class CmlLocationHelper {

	public static ILexLocation findLocation(INode node) {
		ILexLocation loc;
		try {
			loc = node.apply(new CmlGetLocationVisitor());
		} catch (AnalysisException e) {
			loc = null;
		}

		return loc;

	}

}
