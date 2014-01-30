package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.LinkedList;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

public class ThmExplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	private String body;
	private LinkedList<PPattern> params;
	private String resType;
	private String paramTypes;
	
	private static String inputName = "inp";
	private static String outputName = "outp";


	public ThmExplicitOperation(String name, LinkedList<PPattern> params, String pre, String post, String body, LinkedList<String> paramT, String resType) {
		this.name = name;
		this.params = params;
		this.paramTypes = genParamTypeList(paramT);
		if(resType == null)
		{
			this.resType = "()";
		}
		else 
		{
			this.resType = resType;
		}
		this.pre = genPre(pre);
		this.post = genPost(post);
		this.body = genBody(body);
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
				"|" + fixParamRefs(exp) + "|" + "\"\n" + tactic("pre_"+ name, operation);
		
		return preOutput;	
	}
	
	private String genPost(String exp)
	{
		if(exp == null)
		{
			exp = "true";
		}
		
		String postOutput = ThmProcessUtil.isaOp + " \"post_" + name + " (" + inputName +") (" + outputName +")" + " = " + 
				"|" + fixParamRefs(exp) + "|"  + "\"\n" + tactic("post_"+ name, operation);
		
		return postOutput;	
	}
	

	
	private String genBody(String exp)
	{
		if(exp == null)
		{
			exp = "true";
		}
		
		String bodyOutput = ThmProcessUtil.isaOp + " \"body_" + name + " (" + inputName +")" + " = " + 
				ThmProcessUtil.opBodyLeft + fixParamRefs(exp) +
				ThmProcessUtil.opBodyRight + "\"\n" + tactic("body_"+ name, operation);
		
		return bodyOutput;	
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
		
		for(PPattern p : params)
		{
			String pName = "^" + ((AIdentifierPattern) p).getName().toString() + "^";
			String lambdaName = "@" + inputName +".#" + count;
		
			ex = ex.replace(pName, lambdaName);
			count++;
		}
		
		//Replace the keyword "RESULT" with the Lambda post value
		ex = ex.replace("^RESULT^", "@" + outputName);
		
		return ex;
	}
	
		
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(body + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " = CMLOpO " + 
				ThmProcessUtil.opParamLeft + paramTypes + ThmProcessUtil.opParamRight + " " + 
				ThmProcessUtil.opParamLeft + resType + ThmProcessUtil.opParamRight + " " + 
				"pre_" + name + " " + 
				"post_" + name + " " + 
				"body_" + name + "\"\n" + 
				tactic(name, operation));
		
		return res.toString();
	}
}
