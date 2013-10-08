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
	private String expr;
	private String post;
	private String pre;
	private String resType;
	
	public ThmImpFunc(String name, String post, String pre, LinkedList<APatternListTypePair> params, APatternTypePair res , String resType)
	{
		this.name = name;
		if(post == null)
			this.post = "true";
		else 

			//generate function for precondition
			this.post = fixFuncExpr(post,params);
		if(pre == null)
			this.pre = "true";
		else 
			this.pre = fixFuncExpr(pre,params);
		if(res != null)
		{

			//generate function for postcondition
			this.post = fixFuncPostExpr(this.post, res);
		}
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
	private String fixFuncExpr(String ex, LinkedList<APatternListTypePair> pattern){
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

	/***
	 * Method to swap return value name in a postcondition for a Isabelle lambda result name
	 * @param ex - postcondition expression
	 * @param res - result of the function
	 * @return fixed string
	 */
	private String fixFuncPostExpr(String ex, APatternTypePair res){
		PPattern p = res.getPattern();
		
		String pName = "^" + ((AIdentifierPattern) p).getName().toString() + "^";
		String lambdaName = "^" +ThmTypeUtil.isaFuncLambdaPostVal+"^";
			
		ex = ex.replace(pName, lambdaName);

		//Replace the keyword "RESULT" with the Lambda post value - PROBABLY JUST FOR EXP FUNCS...
		ex = ex.replace("^RESULT^", "^" + ThmTypeUtil.isaFuncLambdaPostVal + "^");
		
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
	private String createPrePostFunc(String name, String exp, String prepost, LinkedList<List<PPattern>> params)
	{
		//Create a simple function for the precondition
		ThmExpFunc prePostFunc = new ThmExpFunc((prepost + "_" + name), exp, params);
		return prePostFunc.getRefFunction();
	}
	
	/*****
	 * Method to create the parameter list used in the explicit function - used when
	 * calling the pre/post functions
	 * @param paras
	 * @return
	 */
	public String getPrePostParamList(LinkedList<List<PPattern>> paras){
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (List<PPattern> para : paras)
		{
			for (Iterator<PPattern> itr = para.listIterator(); itr.hasNext(); ) {
				
				PPattern pat = itr.next();
				sb.append("^");
				sb.append(((AIdentifierPattern) pat).getName().toString());
				sb.append("^");
				//If there are remaining parameters, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	
	
	/****
	 * To string method returns the function definition 
	 */
	public String toString(){
		return (ThmTypeUtil.isaFunc + " \"" + name + " = " + 
			ThmTypeUtil.isaFuncBar + ThmTypeUtil.isaFuncLambda + " " +ThmTypeUtil.isaFuncLambaVal+" @ " +
		    createFuncExp() + ThmTypeUtil.isaFuncBar + "\"\n" + tacHook(name));
	}

	/****
	 * Method to provide a string containing the precondition, function body and postcondition
	 * @return Function expression string
	 */
	private String createFuncExp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("if (" + pre + ")\n");
		sb.append("then (" + ThmTypeUtil.isaFuncLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : " + resType + " @ (" + post +"))\n");
		sb.append("else undefined");
		
		return sb.toString();
	}
}
