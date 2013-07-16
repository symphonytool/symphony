package eu.compassresearch.core.analysis.pog.utility;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.pog.utility.GetLocationHelper;

public class CmlLocationHelper extends GetLocationHelper {

	public static ILexLocation getLocation(INode node) {
		ILexLocation loc;
		try {
			loc = node.apply(new CmlGetLocationVisitor());
		} catch (AnalysisException e) {
			loc = null;
		}

		return loc;

	}

}
