package eu.compassresearch.theoremprover.thms;

import java.util.LinkedList;

import org.overture.ast.definitions.PDefinition;
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
			this.pre = "true";
		}
		else
		{
			this.pre = pre;
		}
		
		if (post == null)
		{
			this.post = "true";
		}
		else
		{
			this.post = post;
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
	//definition "Init = `true ⊢ (dw := mk_DwarfType(&stop, {}, {}, &stop, &stop, &stop))`"
	@Override
	public String toString() {
		return (ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + ThmProcessUtil.opExpLeft + pre + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + post + ThmProcessUtil.opExpRight + " \\<and> (" +
				body + ")`\"\n" + tacHook(name));
	}

	
	
	
}
