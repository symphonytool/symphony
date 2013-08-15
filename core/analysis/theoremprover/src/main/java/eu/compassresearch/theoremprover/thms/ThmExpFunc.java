package eu.compassresearch.theoremprover.thms;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

public class ThmExpFunc extends ThmDecl {

	public String name;
	public String expr;
	public String post;
	public String pre;
	public LinkedList<List<PPattern>> pattern;
	private APatternTypePair res;
	private String resType;
	

	public ThmExpFunc(String name, String expr, String post, String pre, LinkedList<List<PPattern>> pattern, String resType)
	{
		this.name = name;
		this.pattern = pattern;
		this.expr = fixFuncExpr(expr,pattern);
		if(post == null)
			this.post = "true";
		else 
			this.post = fixFuncExpr(post,pattern);
		if(pre == null)
			this.pre = "true";
		else 
			this.pre = fixFuncExpr(pre,pattern);
//		if(res != null)
//		{
//			this.res = res;
//			this.post = fixFuncPostExpr(post, res);
//		}
		this.resType = resType;
	}
	
//	public ThmExpFunc(String name, String expr, LinkedList<List<PPattern>> pattern)
//	{
//		this.name = name;
//		this.expr = fixFuncExpr(expr,pattern);
//		this.pattern = pattern;
//	}
	
	private String fixFuncExpr(String ex, LinkedList<List<PPattern>> pattern){
		int count = 1;
		List<PPattern> pat = pattern.getFirst();
		for(PPattern p : pat )
		{
			String pName = "^" + ((AIdentifierPattern) p).getName().toString() + "^";
			String lambdaName = "^" +ThmTypeUtil.isaFuncLambaVal+"^.#" + count;
			
			ex = ex.replace(pName, lambdaName);
			count++;
		}
	
		return ex;
	}

	//MORE FORE IMPLICIT, I THINK
	private String fixFuncPostExpr(String ex, APatternTypePair res){
		PPattern p = res.getPattern();
		
		String pName = "^" + ((AIdentifierPattern) p).getName().toString() + "^";
		String lambdaName = "^" +ThmTypeUtil.isaFuncLambdaPostVal+"^";
			
		ex = ex.replace(pName, lambdaName);
	
		return ex;
	}
	
	//definition 
	//  "f = |lambda d @ ^d^.#1 <> ^d^.#2|"
	public String toString(){
		return (ThmTypeUtil.isaFunc + " \"" + name + " = " + 
			ThmTypeUtil.isaFuncBar + ThmTypeUtil.isaFuncLambda + " " +ThmTypeUtil.isaFuncLambaVal+" @ " +
		    createFuncExp() + ThmTypeUtil.isaFuncBar + "\"");
		
	}

	private String createFuncExp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("if (" + pre + ")\n");
		sb.append("then (" + ThmTypeUtil.isaFuncLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : " + resType + " @ (" + post + " and ^" + ThmTypeUtil.isaFuncLambdaPostVal +  "^ = " + expr +"))\n");
		sb.append("else undefined");
		
		
		return sb.toString();
	}
	
}
