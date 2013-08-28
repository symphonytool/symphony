package eu.compassresearch.theoremprover.thms;

import java.util.LinkedList;

import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

public class ThmImpFunc extends ThmDecl {

	public String name;
	public String expr;
	public String post;
	public String pre;
	private String resType;
	
	public ThmImpFunc(String name, String post, String pre, LinkedList<APatternListTypePair> params, APatternTypePair res , String resType)
	{
		this.name = name;
		if(post == null)
			this.post = "true";
		else 
			this.post = fixFuncExpr(post,params);
		if(pre == null)
			this.pre = "true";
		else 
			this.pre = fixFuncExpr(pre,params);
		if(res != null)
		{
			this.post = fixFuncPostExpr(this.post, res);
		}
		this.resType = resType;
	}

	private String fixFuncExpr(String ex, LinkedList<APatternListTypePair> pattern){
		int count = 1;
		for(APatternListTypePair p : pattern )
		{
			LinkedList<PPattern> pats = p.getPatterns();
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
		    createFuncExp() + ThmTypeUtil.isaFuncBar + "\"\n" + tacHook(name));
	}

	private String createFuncExp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("if (" + pre + ")\n");
		sb.append("then (" + ThmTypeUtil.isaFuncLambdaPost + " " + ThmTypeUtil.isaFuncLambdaPostVal+ " : " + resType + " @ (" + post +"))\n");
		sb.append("else undefined");
		
		return sb.toString();
	}
}
