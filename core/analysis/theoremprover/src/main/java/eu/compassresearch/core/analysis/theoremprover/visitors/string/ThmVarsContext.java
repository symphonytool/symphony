package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import org.overture.ast.intf.lex.ILexNameToken;

import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

public class ThmVarsContext {

	
	private NodeNameList svars;
	private NodeNameList bvars;
	
	public ThmVarsContext()
	{
		svars = new NodeNameList();
		bvars = new NodeNameList();
	}
	
	public ThmVarsContext(NodeNameList svars, NodeNameList bvars)
	{
		this.svars = svars;
		this.bvars = bvars;
	}
	
	public void addBVars(NodeNameList newBvars){
		bvars.addAll(newBvars);
	}
	
	public void addSVars(NodeNameList newSvars){
		svars.addAll(newSvars);
	}
	
	public NodeNameList getBVars(){
		return bvars;
	}

	public NodeNameList getSVars(){
		return svars;
	}

	public void addBVar(ILexNameToken name) {
		bvars.add(name);		
	}

	public void addSVar(ILexNameToken name) {
		svars.add(name);		
	}
}
