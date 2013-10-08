package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
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
	private String prePostParamList;
	
	public ThmExpFunc(String name, String expr, String post, String pre, LinkedList<List<PPattern>> pattern, String resType)
	{
		this.name = name;
		this.pattern = pattern;
		this.expr = fixFuncExpr(expr,pattern);
		this.prePostParamList = getPrePostParamList(pattern);
		if(post == null)
			this.post = createPrePostFunc(name, "true","post", pattern);
		else 
			//generate function for postcondition
			this.post = createPrePostFunc(name, post,"post", pattern);
		if(pre == null)
			this.pre = createPrePostFunc(name, "true","pre", pattern);
		else 
			//generate function for precondition
			this.pre = createPrePostFunc(name, pre,"pre", pattern);
		this.resType = resType;
	}
	
	/****
	 * Constructor for functions with no pre/post conditions, typically for
	 * pre/post functions
	 * @param name
	 * @param expr
	 * @param pattern
	 */
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
		//Replace the keyword "RESULT" with the Lambda post value
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
		
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmTypeUtil.isaFunc + " \"" + name + " = " + 
			ThmTypeUtil.isaFuncBar + ThmTypeUtil.isaFuncLambda + " " +ThmTypeUtil.isaFuncLambaVal+" @ " +
		    createFuncExp() + ThmTypeUtil.isaFuncBar + "\"\n" + tacHook(name));

		return res.toString();
	}

	/****
	 * Method to provide a string containing the precondition, function body and postcondition
	 * @return Function expression string
	 */
	private String createFuncExp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("if (pre_"+ name + prePostParamList + ")\n");
		sb.append("then (" + ThmTypeUtil.isaFuncLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : " + resType + " @ (post_" + name + prePostParamList + " and ^" + ThmTypeUtil.isaFuncLambdaPostVal +  "^ = " + expr +"))\n");
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
