package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

public class ThmImplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	private String params;
	private String prePostParamList;

	
	public ThmImplicitOperation(String name, LinkedList<APatternListTypePair> params, String pre, String post) {
		this.name = name;
		this.params = getParams(params);
		this.prePostParamList = getPrePostParamList(params);
		if (pre == null)
		{
			this.pre = createPrePostFunc("true", "pre", params);
		}
		else
		{
			//generate function for precondition
			this.pre = createPrePostFunc(pre, "pre", params);
		}
		
		if (post == null)
		{
			this.post = createPrePostFunc("true", "post", params);
		}
		else
		{
			// generate function for postcondition
			this.post = createPrePostFunc(post, "post", params);
		}
	}
	
	/***
	 * Method to return a string with all paramaters seperated by a space (" ")
	 * @param parPair - the operation parameters
	 * @return
	 */
	private String getParams(LinkedList<APatternListTypePair> parPair) {
		StringBuilder sb = new StringBuilder();
		for(APatternListTypePair p : parPair)
		{
			for(PPattern pat: p.getPatterns())
			{
				sb.append(((AIdentifierPattern) pat).getName().toString() + " ");
			}
		}
		return sb.toString();
	}

	/***
	 * Create a string containing the defintion for a pre/postcondition expression
	 * @param exp - the pre/post expression
	 * @param prepost - string to say either a precondition or postcondition.
	 * @param params - the parameters of the operation
	 * @return string containing function definition
	 */
	private String createPrePostFunc(String exp, String prepost, LinkedList<APatternListTypePair> params)
	{
		LinkedList<List<PPattern>> pats = new LinkedList<List<PPattern>>();
		for(APatternListTypePair p : params)
		{
			pats.add(p.getPatterns());
		}
		
		ThmExpFunc preFunc = new ThmExpFunc((prepost + "_" + this.name), exp, pats);
		//calls the 'simple' function method
		return preFunc.getRefFunction();
	}

	/***
	 * Method to get parameter list for sending to the pre/postcondition functions
	 * @param parPair - the operation parameters
	 * @return a Sting with the parameter list
	 */
	public String getPrePostParamList(LinkedList<APatternListTypePair> parPair){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		
		for (Iterator<APatternListTypePair> itr = parPair.listIterator(); itr.hasNext(); )
		{
			APatternListTypePair pat = itr.next();
			for (Iterator<PPattern> itr2 = pat.getPatterns().listIterator(); itr2.hasNext(); ) 
			{
				PPattern p = itr2.next();
				sb.append("^");
				sb.append(((AIdentifierPattern) p).getName().toString());
				sb.append("^");
				//If there are remaining parameters, add a ","
				if(itr2.hasNext()){	
					sb.append(", ");
				}
			}

			//If there are remaining parameters, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * To string method, first returns pre and post conditions, and then the
	 * operation definition. 
	 */
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + ThmProcessUtil.opExpLeft + "pre_"+ name + prePostParamList + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + "post_" + name + prePostParamList + ThmProcessUtil.opExpRight + "`\"\n" + tacHook(name));
	
		return res.toString();
	}
	
}
