package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;

import org.overture.ast.intf.lex.ILexNameToken;

public class ThmNode {

	private ILexNameToken id;
	private NodeNameList depIds;
	private ThmArtifact art;
	
//
//    public ThmNode() {
//    	depIds = new NodeNameList();
//    }
	
	
//    public ThmNode(ILexNameToken iLexNameToken) {
//    	this.id = iLexNameToken;
//    	depIds = new NodeNameList();
//    }
    
//    public ThmNode(ILexNameToken iLexNameToken, NodeNameList ids) {
//    	this.id = iLexNameToken;
//    	this.depIds = ids;
//    }
    
    public ThmNode(ILexNameToken iLexNameToken, NodeNameList ids, ThmArtifact art) {
    	this.id = iLexNameToken;
    	this.depIds = ids;
    	this.art = art;
    }
    
	public ILexNameToken getId(){
		return id;
	}
	
	public NodeNameList getDepIds(){
		return depIds;
	}
	
	public int getnumDep(){
		return depIds.size();
	}
	
	public ThmArtifact getArtifact(){
		return art;
	}
	public void setId(ILexNameToken id) {
    	this.id = id;
	}	
	
	public void addDependant(ILexNameToken id) {
	  depIds.add(id);
	}

	public void addDependants(NodeNameList ids) {
	  depIds.addAll(ids);
	}
	
	public void setArtifact(ThmArtifact art) {
	  this.art = art;
	}	
	
		
	@Override
	public String toString(){
		return art.toString();
	}


	/**
	 * Remove the dependencies from the node's dependency list
	 * @param remNodeNames - node names to remove
	 */
	public void removeDeps(NodeNameList remNodeNames) {
		//Need to add remove all supplied dependencies 
		//For each name to be removed	
		for(ILexNameToken pn :remNodeNames)
		{
			//for each dependency 
			for(Iterator<ILexNameToken> itr = depIds.listIterator(); itr.hasNext(); )
			{
				ILexNameToken nd = itr.next();
				//if the dependency is a name to be removed, remove it.
				if(pn.toString().equals(nd.toString()))
				{
					itr.remove();
				}
			}
		}
	}

	/**
	 * Restrict dependencies to only those supplied 
	 * @param restNodeNames - the node names to restrict
	 */
	public void restrictDeps(NodeNameList restNodeNames) {
		//Temp list of dependencies to keep
		NodeNameList nodeNames = new NodeNameList();
		
		//Need to add keep only those dependencies supplied
		//For each node dependency
		for(ILexNameToken nd : this.depIds)
		{
			//For each name in the acceptable list to keep		
			for(ILexNameToken pn :restNodeNames)
			{
				//if the depandency is an accepted name, then add to the new list
				if(pn.toString().equals(nd.toString()))
				{
					nodeNames.add(nd);
				}
			}
		}
		
		//assign the new set of dependencies to keep
		this.depIds = nodeNames;
		
	}

	/***
	 * Checks to see if a node depends upon itself (for example in a recursive action)
	 * @return
	 */
	public boolean selfDep() {
		boolean selfDep = false;
		
		//for each dependency
		for(ILexNameToken dep : depIds)
		{
			//if the dependency is this node's name, break and set flag to true
			if (id.getName().equals(dep.getName())){
				selfDep = true;
				break;
			}
		}
		return selfDep;
	}
}
