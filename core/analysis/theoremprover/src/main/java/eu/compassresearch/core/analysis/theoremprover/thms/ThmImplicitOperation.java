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
	private String params;
	private String preParamList;
	private String postParamList;
	private APatternTypePair result;
	private String resType;

	
	public ThmImplicitOperation(String name, LinkedList<APatternListTypePair> params, String pre, String post, APatternTypePair res, String resType) {
		this.name = name;
		this.params = getParams(params);
		this.result = res;
		this.preParamList = getPrePostParamList(params, null); //supply null as precond shouldn't use result
		this.postParamList = getPrePostParamList(params, res);
		//generate function for precondition
		this.pre = createPrePostFunc(pre, "pre", params);
		this.post = createPrePostFunc(post, "post", params);
		this.resType = resType;
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
	 * Create a string containing the definition for a pre/postcondition expression
	 * @param exp - the pre/post expression
	 * @param prepost - string to say either a precondition or postcondition.
	 * @param params - the parameters of the operation
	 * @return string containing function definition
	 */
	private String createPrePostFunc(String exp, String prepost, LinkedList<APatternListTypePair> params)
	{
		if(exp == null)
		{
			exp = "true";
		}
		
		LinkedList<List<PPattern>> pats = new LinkedList<List<PPattern>>();
		for(APatternListTypePair p : params)
		{
			pats.add(p.getPatterns());
		}
		
		if (prepost.equals("post"))
		{
			exp = fixPostFuncExpr(exp, params);
		}
		
		ThmExpFunc preFunc = new ThmExpFunc((prepost + "_" + this.name), exp, pats);
		//calls the 'simple' function method
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
	private String fixPostFuncExpr(String ex, LinkedList<APatternListTypePair> pattern){

		//if there is a result value (it is a postcondition)
		if  (result != null )
		{
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
			
			//Add the result too
			PPattern p = result.getPattern();
			//the result is therefore the next parameter
			int resCount = count+1;
			
			String pName = "^" + ((AIdentifierPattern) p).getName().toString() + "^";
			String lambdaName = "^" +ThmTypeUtil.isaFuncLambaVal+"^.#" + resCount;
			
			ex = ex.replace(pName, lambdaName);
		}
		
			
	
		return ex;
	}
	
	/***
	 * Method to get parameter list for sending to the pre/postcondition functions
	 * @param parPair - the operation parameters
	 * @return a Sting with the parameter list
	 */
	public String getPrePostParamList(LinkedList<APatternListTypePair> parPair, APatternTypePair res){
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
			if(itr.hasNext() || res != null){	
				sb.append(", ");
			}
		}
		//if there is a result value (it is a postcondition)
		if  (res != null )
		{
			//Add the result too
			PPattern p = res.getPattern();
	
			sb.append("^" + ((AIdentifierPattern) p).getName().toString() + "^");
		}
		
		sb.append(")");
		return sb.toString(); //NOT SURE IF NEED TO DO THIS =-fixParamRefs(sb.toString(), parPair);
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
	/**
	 * To string method, first returns pre and post conditions, and then the
	 * operation definition. 
	 */
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + 
				ThmProcessUtil.opExpLeft + "pre_"+ name + preParamList + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " "); 
				
		if (resType	!= null)
		{
			res.append(ThmProcessUtil.opExpLeft + "(" + ThmTypeUtil.isaOpLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : " + resType + " @ (post_" + name + postParamList +"))"+ ThmProcessUtil.opExpRight);
		}
		else
		{
			res.append(ThmProcessUtil.opExpLeft +"post_" + name + postParamList + ThmProcessUtil.opExpRight +" ");
		}
		
		res.append("`\"\n" + tacHook(name));
		
		return res.toString();
	}
	
}
