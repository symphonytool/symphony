package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmStringVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmVarsContext;

public class ThmImplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	// private String body;
	private LinkedList<APatternListTypePair> params;
	private List<String> frame;
	private APatternTypePair result;
	private String resType;


	
	public ThmImplicitOperation(String name, LinkedList<APatternListTypePair> params, List<String> frame, String pre, String post, APatternTypePair res, String resType) {
		this.name = name;
		this.params = params;
		this.result = res;
		//generate function for precondition
		this.frame = frame;
		this.pre = pre;
		this.post = post;
		this.resType = resType;
	}
	
	
	@Override
	public String toString() {
		
		StringBuilder res = new StringBuilder();
		
		res.append("cmliop " + name + "\n");
		
		if (params.size() > 0) {
			
			res.append("  inp ");
		
			for (Iterator<APatternListTypePair> pitr = params.iterator(); pitr.hasNext(); ) {
			
				APatternListTypePair pats = pitr.next();
				
				for (Iterator<PPattern> itr = pats.getPatterns().iterator(); itr.hasNext(); ) {
				
					PPattern pat = itr.next();
					String ty = "";
					try {
						ty = pats.getType().apply(new ThmStringVisitor(), new ThmVarsContext());
					} catch (AnalysisException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//ThmTypeUtil.getIsabelleType(field.getType());

					res.append(((AIdentifierPattern) pat).getName().toString());
					res.append(" :: \"" + ty + "\"");
					if (itr.hasNext()) res.append (" and ");
				}
				if (pitr.hasNext()) res.append (" and ");
			}
			res.append("\n");
		}
		
		if (resType != null) {
			// FIXME: This might be a pattern on not a variable...
			res.append("  out " + result.getPattern().toString() + " :: \"" + resType + "\"\n");
		}
		if (!frame.isEmpty()) {
			res.append("  frame ");
			for (String x : frame) res.append(x + " ");
			res.append("\n");
		}
		if (pre != null) res.append("  pre \"" + pre + "\"\n");
		if (post != null) res.append("  post \"" + post + "\"\n");
		
		
		return res.toString();
	}
}