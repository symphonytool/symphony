package eu.compassresearch.theoremprover.thms;

import java.util.LinkedList;

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
		return (ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + ThmProcessUtil.opExpLeft + pre + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + post + ThmProcessUtil.opExpRight + "`\"\n" + tacHook(name));
	}
}
