package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;

public class ThmExpFunc extends ThmDecl {

	private String name;
	private String expr;
	private String post;
	private String pre;
	private LinkedList<List<PPattern>> pattern;
	private String resType;
	
	public ThmExpFunc(String name, String expr, String post, String pre, LinkedList<List<PPattern>> pattern, String resType)
	{
		this.name = name;
		this.pattern = pattern;
		this.expr = fixFuncExpr(expr,pattern);
		if(post == null)
			this.post = "true";
		else 
			//generate function for postcondition
			this.post = fixFuncExpr(post,pattern);
		if(pre == null)
			this.pre = "true";
		else 
			//generate function for precondition
			this.pre = fixFuncExpr(pre,pattern);
		this.resType = resType;
	}
	
	public ThmExpFunc(String name, String expr, LinkedList<List<PPattern>> pattern)
	{
		this.name = name;
		this.pattern = pattern;
		this.expr = fixFuncExpr(expr,pattern);
	}
	

	/**
	 * Method to change the value names in an expression when they are parameter names
	 * This is so that the lambda expression of a function operates as expected. 
	 * Parameters are determined by numeric order.
	 * @param ex - expression to fix
	 * @param pattern - the parameters
	 * @return the new, fixed string
	 */
	private String fixFuncExpr(String ex, LinkedList<List<PPattern>> pattern){
		int count = 1;
		
		for (List<PPattern> pat : pattern)
		{
			for(PPattern p : pat )
			{
				String pName = "^" + ((AIdentifierPattern) p).getName().toString() + "^";
				String lambdaName = "^" +ThmTypeUtil.isaFuncLambaVal+"^.#" + count;
			
				ex = ex.replace(pName, lambdaName);
				count++;
			}
		}
	
		return ex;
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
		sb.append("then (" + ThmTypeUtil.isaFuncLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : " + resType + " @ (" + post + " and ^" + ThmTypeUtil.isaFuncLambdaPostVal +  "^ = " + expr +"))\n");
		sb.append("else undefined");
		
		return sb.toString();
	}
	
	/***
	 * A simpler version of the toString - for a function (mainly pre/post functions) which
	 * Don't have pre or postconditions
	 * @return String for function definition
	 */
	public String getRefFunction()
	{
		return (ThmTypeUtil.isaFunc + " \"" + name + " = " + 
				ThmTypeUtil.isaFuncBar + ThmTypeUtil.isaFuncLambda + " " +ThmTypeUtil.isaFuncLambaVal+" @ " +
				createSimpFuncExp() + ThmTypeUtil.isaFuncBar + "\"\n" + tacHook(name));
	}

	/****
	 * Method to provide a string containing the function body only.
	 * @return Function expression string
	 */
	private String createSimpFuncExp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(" + ThmTypeUtil.isaFuncLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : @bool @ (^" + ThmTypeUtil.isaFuncLambdaPostVal +  "^ = " + expr +"))");		
		return sb.toString();
	}
	
}
