package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;

public class ThmImplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	private LinkedList<APatternListTypePair> params;
	private String preParamList;
	private String postParamList;
	private APatternTypePair result;
	private String resType;
	private Object paramTypes;
	
	private static String inputName = "inp";
	private static String outputName = "outp";

	
	public ThmImplicitOperation(String name, LinkedList<APatternListTypePair> params, String pre, String post, APatternTypePair res, LinkedList<String> paramT,String resType) {
		this.name = name;
		this.params = params;//getParams(params);
		this.paramTypes = genParamTypeList(paramT);
		this.result = res;
		//generate function for precondition
		

		this.pre = genPre(pre);
		this.post = genPost(post);
		

//		this.preParamList = getPrePostParamList(params, null); //supply null as precond shouldn't use result
//		this.postParamList = getPrePostParamList(params, res);
//		this.pre = createPrePostFunc(pre, "pre", params);
//		this.post = createPrePostFunc(post, "post", params);
		this.resType = resType;
	}
	

	private String genParamTypeList(LinkedList<String> paramT) {

		StringBuilder sb = new StringBuilder();
		if(paramT == null)
		{
			sb.append("()");
		}
		else 
		{
			for(String p: paramT)		
			{
				sb.append("(" + p + ")*");
			}
			sb.append("()");
		}
		return sb.toString();
	}
	
	
	private String genPre(String exp)
	{
		if(exp == null)
		{
			exp = "true";
		}
		
		String preOutput = ThmProcessUtil.isaOp + " \"pre_" + name + " (" +inputName +") " + " = " + 
				"|" + fixParamRefs(exp) + "|\"\n" + tactic("pre_"+ name, operation);
		
		return preOutput;	
	}
	
	private String genPost(String exp)
	{
		if(exp == null)
		{
			exp = "true";
		}
		
		String postOutput = ThmProcessUtil.isaOp + " \"post_" + name + " (" + inputName +") (" + outputName +")" + " = " + 
				"|" + fixParamRefs(exp) + "|\"\n" + tactic("post_"+ name, operation);
		
		return postOutput;	
	}
	
	
	
	/**
	 * Method to change the value names in an expression when they are parameter names
	 * This is so that the lambda expression of a function operates as expected. 
	 * Parameters are determined by numeric order.
	 * @param ex - expression to fix
	 * @param pattern - the parameters
	 * @return the new, fixed string
	 */
	private String fixParamRefs(String ex){
		int count = 1;
		for(APatternListTypePair p : params)
		{
			LinkedList<PPattern> pats = p.getPatterns();
			//for each parameter, find it in the expression and replace with the lambda value
			for(PPattern param : pats )
			{
				String pName = "^" + ((AIdentifierPattern) param).getName().toString() + "^";
				String lambdaName = "@" + inputName + ".#" + count;
			
				ex = ex.replace(pName, lambdaName);
				count++;
			}
		}
		
		//And the result too
		String resName = "^" + ((AIdentifierPattern) result.getPattern()).getName().toString() + "^";	
		//Replace the keyword "RESULT" with the post name
		ex = ex.replace(resName, "@" + outputName);
		
		return ex;
	}

	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");
	
		res.append(post + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " = CMPOpO " + 
				ThmProcessUtil.opParamLeft + paramTypes + ThmProcessUtil.opParamRight + " " + 
				ThmProcessUtil.opParamLeft + resType + ThmProcessUtil.opParamRight + " " + 
				"pre_" + name + " " + 
				"post_" + name + "\n" + 
				tactic(name, operation));
		
		return res.toString();
	}
	
	
}
