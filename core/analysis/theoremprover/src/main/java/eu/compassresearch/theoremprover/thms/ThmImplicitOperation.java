package eu.compassresearch.theoremprover.thms;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.theoremprover.utils.ThmProcessUtil;

public class ThmImplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	private String params;

	public ThmImplicitOperation(String name, LinkedList<APatternListTypePair> params, String pre, String post) {
		this.name = name;
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

	private String createPrePostFunc(String name, String exp, String prepost, LinkedList<APatternListTypePair> params)
	{
		LinkedList<List<PPattern>> pats = new LinkedList();
		for(APatternListTypePair p : params)
		{
			pats.add(p.getPatterns());
		}
		
		ThmExpFunc preFunc = new ThmExpFunc((prepost + "_" + name), exp, pats);
		return preFunc.getRefFunction();
	}
	
	private String getParams(LinkedList<APatternListTypePair> parPair) {
		StringBuilder sb = new StringBuilder();
		for(APatternListTypePair p : parPair)
		{
			for(PPattern pat: p.getPatterns())
			{
				for (PDefinition def : pat.getDefinitions())
				{
					sb.append(def.getName().toString() + " ");
				}
			}
		}
		return sb.toString();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + ThmProcessUtil.opExpLeft + "pre_"+ name + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + "post_" + name + ThmProcessUtil.opExpRight + "`\"\n" + tacHook(name));
	
		return res.toString();
	}
}
