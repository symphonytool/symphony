package eu.compassresearch.theoremprover.thms;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.theoremprover.utils.ThmProcessUtil;

public class ThmExplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	private String body;
	private String params;

	public ThmExplicitOperation(String name, LinkedList<PPattern> params, String pre, String post, String initExprs) {
		this.name = name;
		this.body = initExprs;
		this.params = getParams(params);
		if (pre == null)
		{
			this.pre = createPrePostFunc(name, "true", "pre", params);
		}
		else
		{
			//TODO: CHECK THIS WORKS!!
			//generate function for precondition
			this.pre = createPrePostFunc(name, pre, "pre", params);
		}
		
		if (post == null)
		{
			this.post = createPrePostFunc(name, "true", "post", params);
		}
		else
		{
			//TODO: generate function for postcondition
			this.post = createPrePostFunc(name, post, "post", params);
		}
	}
	
	private String getParams(LinkedList<PPattern> param) {
		StringBuilder sb = new StringBuilder();
		for(PPattern pat: param)		
		{
			for (PDefinition def : pat.getDefinitions())
			{
				sb.append(def.getName().toString() + " ");
			}
		}
		return sb.toString();
	}
	

	private String createPrePostFunc(String name, String exp, String prepost, LinkedList<PPattern> params)
	{
		LinkedList<List<PPattern>> pats = new LinkedList();
		pats.add(params);
		
		ThmExpFunc preFunc = new ThmExpFunc((prepost + "_" + name), exp, pats);
		return preFunc.getRefFunction();
	}
	
	
	
	//definition "Init = `true ⊢ (dw := mk_DwarfType(&stop, {}, {}, &stop, &stop, &stop))`"
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + ThmProcessUtil.opExpLeft + "pre_"+ name + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + "post_" + name + ThmProcessUtil.opExpRight + " \\<and> (" +
				body + ")`\"\n" + tacHook(name));
		
		return res.toString();
	}

	
	
	
}
