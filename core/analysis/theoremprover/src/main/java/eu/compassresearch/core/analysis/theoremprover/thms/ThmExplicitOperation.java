package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;

public class ThmExplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	private String body;
	private String params;
	private String preParamList;
	private String postParamList;
	private String resType;

	public ThmExplicitOperation(String name, LinkedList<PPattern> params, String pre, String post, String initExprs, String resType) {
		this.name = name;
		this.body = initExprs;
		this.params = getParams(params);
		this.resType = resType;
		this.preParamList = getPrePostParamList(params, "pre");
		this.postParamList = getPrePostParamList(params, "post");
		this.pre = createPrePostFunc(name, pre, "pre", params);
		this.post = createPrePostFunc(name, post, "post", params);
	}
	
	private String getParams(LinkedList<PPattern> param) {
		StringBuilder sb = new StringBuilder();
		for(PPattern pat: param)		
		{
			sb.append(((AIdentifierPattern) pat).getName().toString() + " ");
		}
		return sb.toString();
	}
	

	private String createPrePostFunc(String name, String exp, String prepost, LinkedList<PPattern> params)
	{	
		if(exp == null)
		{
			exp = "true";
		}
		if (prepost.equals("post"))
		{
			exp = fixPostFuncExpr(exp, params);
		}	
		
		LinkedList<List<PPattern>> pats = new LinkedList<List<PPattern>>();
		pats.add(params);
		
		ThmExpFunc preFunc = new ThmExpFunc((prepost + "_" + name), exp, pats);
		return preFunc.getRefFunction();
	}
	
	/**
	 * Method to change the value names in an expression when they are parameter names
	 * This is so that the lambda expression of a function operates as expected. 
	 * Parameters are determined by numeric order.
	 * @param ex - expression to fix
	 * @param pattern - the parameters
	 * @return the new, fixed string
	 */
	private String fixPostFuncExpr(String ex, LinkedList<PPattern> pattern){
		if (resType != null)
		{
			int count = 0;

			//for each parameter, add 1 to the parameter count
			for(PPattern p : pattern)
			{
				count++;
			}
			
			//the result is therefore the next parameter
			int resCount = count+1;
			
			//replace the keyword RESULT with the result parameter
			String lambdaName = "^" +ThmTypeUtil.isaFuncLambaVal+"^.#" + resCount;
			ex = ex.replace("^RESULT^", lambdaName);
		}
		return ex;
	}

	private String getPrePostParamList(LinkedList<PPattern> paras, String prepost){
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (Iterator<PPattern> itr = paras.listIterator(); itr.hasNext(); ) {
			
			PPattern pat = itr.next();
			sb.append("^");
			sb.append(((AIdentifierPattern) pat).getName().toString());
			sb.append("^");
			//If there are remaining parameters, add a ","
			if(itr.hasNext() || (prepost.equals("post") && resType != null)){	
				sb.append(", ");
			}
		}
		//if there is a result value
		if (prepost.equals("post") && resType != null)
		{
			sb.append("^" + ThmTypeUtil.isaFuncLambdaPostVal + "^");
		}
		sb.append(")");
		
		return fixParamRefs(sb.toString(), paras);
	}
	
	/**
	 * Method to change the value names in an expression when they are parameter names
	 * This is so that the lambda expression of a function operates as expected. 
	 * Parameters are determined by numeric order.
	 * @param ex - expression to fix
	 * @param pattern - the parameters
	 * @return the new, fixed string
	 */
	private String fixParamRefs(String ex, LinkedList<PPattern> pattern){
		int count = 1;
		
		for(PPattern p : pattern )
		{
			String pName = "^" + ((AIdentifierPattern) p).getName().toString() + "^";
			String lambdaName = "^" +ThmTypeUtil.isaFuncLambaVal+"^.#" + count;
		
			ex = ex.replace(pName, lambdaName);
			count++;
		}
		
		//Replace the keyword "RESULT" with the Lambda post value
		ex = ex.replace("^RESULT^", "^" + ThmTypeUtil.isaFuncLambdaPostVal + "^");
		
		return ex;
	}
	
		
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + 
				ThmProcessUtil.opExpLeft+  "pre_"+ name + preParamList + ThmProcessUtil.opExpRight +" " +  
				ThmProcessUtil.opTurn + " ");
		if (resType	!= null)
		{
			res.append(ThmProcessUtil.opExpLeft + "(" + ThmTypeUtil.isaFuncLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : " + resType + " @ (post_" + name + postParamList +"))");
		}
		else
		{
			res.append("post_" + name + postParamList);
		}
				
		res.append(" \\<and> (" + body + ")`\"\n" + tacHook(name));
		
		return res.toString();
	}
}
