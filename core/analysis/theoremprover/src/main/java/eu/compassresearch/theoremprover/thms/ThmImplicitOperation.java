package eu.compassresearch.theoremprover.thms;

import java.util.Iterator;
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
	private String prePostParamList;

	public ThmImplicitOperation(String name, LinkedList<APatternListTypePair> params, String pre, String post) {
		this.name = name;
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
			// generate function for postcondition
			this.post = createPrePostFunc(name, post, "post", params);
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

	public String getPrePostParamList(LinkedList<APatternListTypePair> parPair){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(APatternListTypePair p : parPair)
		{
			for(PPattern pat: p.getPatterns())
			{
				for (Iterator<PDefinition> itr = pat.getDefinitions().listIterator(); itr.hasNext(); ) {
					PDefinition def = itr.next();
					sb.append("^");
					sb.append(def.getName().toString());
					sb.append("^");
					//If there are remaining parameters, add a ","
					if(itr.hasNext()){	
						sb.append(", ");
					}
				}
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(pre + "\n\n");

		res.append(post + "\n\n");
		
		res.append(ThmProcessUtil.isaOp + " \"" + name + " " + params + " = `" + ThmProcessUtil.opExpLeft + "pre_"+ name + prePostParamList + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + "post_" + name + prePostParamList + ThmProcessUtil.opExpRight + "`\"\n" + tacHook(name));
	
		return res.toString();
	}
	
}
