package eu.compassresearch.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.intf.lex.ILexNameToken;

public class ThmNode {

	public ILexNameToken id;
	public LinkedList <ILexNameToken> depIds;
	public ThmArtifact art;
	

    public ThmNode() {
    	depIds = new LinkedList<ILexNameToken>();
    }
	
	
    public ThmNode(ILexNameToken iLexNameToken) {
    	this.id = iLexNameToken;
    	depIds = new LinkedList<ILexNameToken>();
    }
    
    public ThmNode(ILexNameToken iLexNameToken, LinkedList <ILexNameToken> ids) {
    	this.id = iLexNameToken;
    	this.depIds = ids;
    }
    
    public ThmNode(ILexNameToken iLexNameToken, LinkedList <ILexNameToken> ids, ThmArtifact art) {
    	this.id = iLexNameToken;
    	this.depIds = ids;
    	this.art = art;
    }
    
	public ILexNameToken getId(){
		return id;
	}
	
	public LinkedList <ILexNameToken> getDepIds(){
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

	public void addDependants(LinkedList <ILexNameToken> ids) {
	  depIds.addAll(ids);
	}
	
	public void setArtifact(ThmArtifact art) {
	  this.art = art;
	}	
	
	
//		public void addNode(ThmNode tp){
//			this.depIds.allAll(tp.getDepIds();
//			this.dcl.append(tp.getDecl());		
//		}
	
	
		
	@Override
	public String toString(){
		return art.toString();
	}


	public void removeDeps(LinkedList<ILexNameToken> remNodeNames) {
		//Need to add remove all suplpied dependancies 
		//For each name to be removed	
		for(ILexNameToken pn :remNodeNames)
		{
			//for each dependancy 
			for(Iterator<ILexNameToken> itr = depIds.listIterator(); itr.hasNext(); )
			{
				ILexNameToken nd = itr.next();
				//if the dependancy is a name to be removed, remove it.
				if(pn.toString().equals(nd.toString()))
				{
					itr.remove();
				}
			}
		}
	}

	//Restrict dependancies to only those supplied 
	public void restrictDeps(LinkedList<ILexNameToken> restNodeNames) {
		//Temp list of dependancies to keep
		LinkedList<ILexNameToken> nodeNames = new LinkedList<ILexNameToken>();
		
		//Need to add keep only those dependancies supplied
		//For each node dependancy
		for(ILexNameToken nd : this.depIds)
		{
			//For each name in the acceptable list to keep		
			for(ILexNameToken pn :restNodeNames)
			{
				//if the depandancy is an accepted name, then add to the new list
				if(pn.toString().equals(nd.toString()))
				{
					nodeNames.add(nd);
				}
			}
		}
		
		//assign the new set of dependancies to keep
		this.depIds = nodeNames;
		
	}
}
