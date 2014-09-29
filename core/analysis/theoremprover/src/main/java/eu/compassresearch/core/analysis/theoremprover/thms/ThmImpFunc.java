package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;

public class ThmImpFunc extends ThmDecl {

	private String name;
	private String post;
	private String pre;
	LinkedList<APatternListTypePair> pattern;
	private List<String> paramTypes;
	private String resType;
	private String preParamList;
	private APatternTypePair result;
	private String postParamList;
	
	public ThmImpFunc(String name, String post, String pre, LinkedList<APatternListTypePair> params, APatternTypePair res , List<String> paramTypes, String resType)
	{
		this.name = name;
		this.preParamList = getPrePostParamList(params, null, "pre");
		this.postParamList = getPrePostParamList(params, res, "post");
		this.pattern = params;
		this.result = res;
		// this.post = createPrePostFunc(name, post, params, "post"); //fixFuncExpr(post,params);
		// this.pre = createPrePostFunc(name, pre, params, "pre"); //fixFuncExpr(pre,params);
		this.post = post;
		this.pre = pre;
		this.paramTypes = paramTypes;
		this.resType = resType;
	}

	/**
	 * Method to change the value names in an expression when they are parameter names
	 * This is so that the lambda expression of a function operates as expected. 
	 * Parameters are determined by numeric order.
	 * @param ex - expression to fix
	 * @param pattern - the parameters
	 * @return the new, fixed string
	 */
	private String fixParamRefs(String ex, LinkedList<APatternListTypePair> pattern){
		int count = 1;
		for(APatternListTypePair p : pattern )
		{
			LinkedList<PPattern> pats = p.getPatterns();
			//for each parameter, find it in the expression and replace with the lambda value
			for(PPattern param : pats )
			{
				String pName = "^" + ((AIdentifierPattern) param).getName().toString() + "^";
				String lambdaName = "^" +ThmTypeUtil.isaFuncLambaVal+"^.#" + count;
			
				ex = ex.replace(pName, lambdaName);
				count++;
			}
		}
	
		return ex;
	}

	
	/*****
	 * Method to create a pre/post function for the Explicitly defined function 
	 * @param name
	 * @param exp
	 * @param prepost
	 * @param params
	 * @return
	 */
	private String createPrePostFunc(String name, String exp, LinkedList<APatternListTypePair> params, String prepost)
	{		
		if (exp == null)
		{
			exp = "true";
		}
		LinkedList<List<PPattern>> fixedparams = new LinkedList<List<PPattern>>();
		
		for(APatternListTypePair p : params)
		{
			fixedparams.add(p.getPatterns());
		}

		if (prepost.equals("post"))
		{
			exp = fixPostFuncExpr(exp, params);
		}
		
		//Create a simple function for the precondition
		ThmExpFunc prePostFunc = new ThmExpFunc((prepost+ "_" + name), exp, fixedparams);
		return prePostFunc.getRefFunction();
	}
	
//	/*****
//	 * Method to create a pre/post function for the Explicitly defined function 
//	 * @param name
//	 * @param exp
//	 * @param prepost
//	 * @param params
//	 * @return
//	 */
//	private String createPostFunc(String name, String exp, LinkedList<APatternListTypePair> params)
//	{		
//		LinkedList<List<PPattern>> fixedparams = new LinkedList();
//		
//		for(APatternListTypePair p : params)
//		{
//			fixedparams.add(p.getPatterns());
//		}
//				
//		String postExpr = fixPostFuncExpr(exp, params);
//		
//		//Create a simple function for the precondition
//		ThmExpFunc prePostFunc = new ThmExpFunc(("post_" + name), postExpr, fixedparams);
//		return prePostFunc.getRefFunction();
//	}
	
	
	/**
	 * Method to change the value names in an expression when they are parameter names
	 * This is so that the lambda expression of a function operates as expected. 
	 * Parameters are determined by numeric order.
	 * @param ex - expression to fix
	 * @param pattern - the parameters
	 * @return the new, fixed string
	 */
	private String fixPostFuncExpr(String ex, LinkedList<APatternListTypePair> pattern){
		int count = 0;
		//first, need to determine how many parameters are supplied in the function.
		for(APatternListTypePair p : pattern )
		{
			LinkedList<PPattern> pats = p.getPatterns();
			//for each parameter, add 1 to the parameter count
			for(PPattern param : pats )
			{
				count++;
			}
		}
		PPattern p = result.getPattern();
		
		//the result is therefore the next parameter
		int resCount = count+1;
		
		String pName = "^" + ((AIdentifierPattern) p).getName().toString() + "^";
		String lambdaName = "^" +ThmTypeUtil.isaFuncLambaVal+"^.#" + resCount;
			
		ex = ex.replace(pName, lambdaName);
	
		return ex;
	}
	
	
	/*****
	 * Method to create the parameter list used in the explicit function - used when
	 * calling the pre/post functions
	 * @param paras
	 * @return
	 */
	private String getPrePostParamList(LinkedList<APatternListTypePair> params, APatternTypePair res, String prepost){
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		
		for (Iterator<APatternListTypePair> itr1 = params.listIterator(); itr1.hasNext(); ) {
			APatternListTypePair p = itr1.next();
		
			LinkedList<PPattern> pats = p.getPatterns();
			for (Iterator<PPattern> itr2 = pats.listIterator(); itr2.hasNext(); ) {
				
				PPattern pat = itr2.next();
				sb.append("^");
				sb.append(((AIdentifierPattern) pat).getName().toString());
				sb.append("^");
				//If there are remaining parameters, add a ","
				if(itr2.hasNext()){	
					sb.append(", ");
				}
			}
			//If there are remaining parameters, add a ","
			if(itr1.hasNext() || ((prepost.equals("post") && res != null))){	
				sb.append(", ");
			}
			
		}
		//if there is a result value
		if (prepost.equals("post") && res != null)
		{
			sb.append("^" + ThmTypeUtil.isaFuncLambdaPostVal + "^");
		}
		sb.append(")");
		
		return fixParamRefs(sb.toString(), params);
	}
//	/*****
//	 * Method to create the parameter list used in the explicit function - used when
//	 * calling the post functions
//	 * @param paras
//	 * @return
//	 */
//	public String getPostParamList(LinkedList<APatternListTypePair> params, APatternTypePair res){
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append("(");
//		
//		for (Iterator<APatternListTypePair> itr1 = params.listIterator(); itr1.hasNext(); ) {
//			APatternListTypePair p = itr1.next();
//		
//			LinkedList<PPattern> pats = p.getPatterns();
//			for (Iterator<PPattern> itr2 = pats.listIterator(); itr2.hasNext(); ) {
//				
//				PPattern pat = itr2.next();
//				sb.append("^");
//				sb.append(((AIdentifierPattern) pat).getName().toString());
//				sb.append("^");
//				//If there are remaining parameters, add a ","
//				if(itr2.hasNext()){	
//					sb.append(", ");
//				}
//			}
//			//add a "," either more params to come, or for the result
//			sb.append(", ");
//			
//		}
//		//if there is a result value
//		if (res != null)
//		{
//			//Add the result too
//			PPattern p = res.getPattern();
//		
//			sb.append("^" + ((AIdentifierPattern) p).getName().toString() + "^");
//		}
//		sb.append(")");
//		
//		return fixParamRefs(sb.toString(), params);
//	}
	
	
	
	/****
	 * To string method returns the function definition 
	 */
	public String toString(){
		StringBuilder res = new StringBuilder();

		res.append("cmlifun " + name + "\n");
		res.append("  inp ");

		//Require a slight hack as theorem does not deal with implicit functions with no
		//parameters correctly. If there are no parameters, then add a 'blank' parameter
		if (paramTypes.isEmpty())
		{
			res.append("emptyParam :: \"@bool\"");
		}
		else
		{	
			Iterator<String> titr = paramTypes.iterator();
			
			
			for (Iterator<APatternListTypePair> itr1 = pattern.listIterator(); itr1.hasNext(); ) {
				APatternListTypePair p = itr1.next();
				LinkedList<PPattern> pats = p.getPatterns();
				for (Iterator<PPattern> itr2 = pats.listIterator(); itr2.hasNext(); ) {				
					PPattern pat = itr2.next();
					String ty = titr.next();
					res.append(((AIdentifierPattern) pat).getName().toString());
					res.append(" :: \"" + ty + "\"");
					if (itr2.hasNext()) res.append (" and ");
				}
				if (itr1.hasNext()) res.append (" and ");
			}
		}
		res.append("\n");
		
		String outVar = ((AIdentifierPattern) result.getPattern()).toString();
		
		res.append("  out " + outVar + " :: \"" + resType + "\"\n");
		if (pre != null) res.append("  pre \"" + pre + "\"\n");
		if (post != null) res.append("  post \"" + post + "\"\n");
		
/*
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmTypeUtil.isaFunc + " \"" + name + " = " + 
			ThmTypeUtil.isaFuncBar + ThmTypeUtil.isaFuncLambda + " " +ThmTypeUtil.isaFuncLambaVal+" @ " +
		    createFuncExp() + ThmTypeUtil.isaFuncBar + "\"\n" + tacHook(name));
*/

		return res.toString();
	}

	/****
	 * Method to provide a string containing the precondition, function body and postcondition
	 * @return Function expression string
	 */
	private String createFuncExp() {
		StringBuilder sb = new StringBuilder();

		sb.append("if (pre_"+ name + preParamList + ")\n");
		sb.append("then (" + ThmTypeUtil.isaFuncLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : " + resType + " @ (post_" + name + postParamList +"))\n");
		sb.append("else undefined");
		
		return sb.toString();
	}
}
