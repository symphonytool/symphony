package eu.compassresearch.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.theoremprover.utils.ThmProcessUtil;

public class ThmExplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	private String body;
	private String params;
	private String prePostParamList;


	public ThmExplicitOperation(String name, LinkedList<PPattern> params, String pre, String post, String initExprs) {
		this.name = name;
		this.body = initExprs;
		this.params = getParams(params);
		this.prePostParamList = getPrePostParamList(params);
		if (pre == null)
		{
			this.pre = createPrePostFunc(name, "true", "pre", params);
		}
		else
		{
			//generate function for precondition
			this.pre = createPrePostFunc(name, pre, "pre", params);
		}
		
		if (post == null)
		{
			this.post = createPrePostFunc(name, "true", "post", params);
		}
		else
		{
			//generate function for postcondition
			this.post = createPrePostFunc(name, post, "post", params);
		}
	}
	
	private String getParams(LinkedList<PPattern> param) {
		StringBuilder sb = new StringBuilder();
		for(PPattern pat: param)		
		{
			sb.append(((AIdentifierPattern) pat).getName().toString() + " ");
			
//			for (PDefinition def : pat.getDefinitions())
//			{
//				sb.append(def.getName().toString() + " ");
//			}
		}
		return sb.toString();
	}
	

	private String createPrePostFunc(String name, String exp, String prepost, LinkedList<PPattern> params)
	{
		LinkedList<List<PPattern>> pats = new LinkedList<List<PPattern>>();
		pats.add(params);
		
		ThmExpFunc preFunc = new ThmExpFunc((prepost + "_" + name), exp, pats);
		return preFunc.getRefFunction();
	}
	


	public String getPrePostParamList(LinkedList<PPattern> paras){
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (Iterator<PPattern> itr = paras.listIterator(); itr.hasNext(); ) {
			
			PPattern pat = itr.next();
			sb.append("^");
			sb.append(((AIdentifierPattern) pat).getName().toString());
			sb.append("^");
			//If there are remaining parameters, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		sb.append(")");
		
		return sb.toString();
	}
	
		
	
	
	//definition "Init = `true ⊢ (dw := mk_DwarfType(&stop, {}, {}, &stop, &stop, &stop))`"
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + ThmProcessUtil.opExpLeft+  "pre_"+ name + prePostParamList + ThmProcessUtil.opExpRight +" " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + "post_" + name + prePostParamList + ThmProcessUtil.opExpRight + " \\<and> (" +
				body + ")`\"\n" + tacHook(name));
		
		return res.toString();
	}

	
	
	
}
