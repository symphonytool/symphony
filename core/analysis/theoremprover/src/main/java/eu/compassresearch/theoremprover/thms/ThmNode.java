package eu.compassresearch.theoremprover.thms;

import java.util.LinkedList;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;

public class ThmNode {

	public ILexNameToken id;
	public LinkedList <ILexNameToken> depIds;
	public ThmArtifact art;
	

    public ThmNode() {
    	depIds = new LinkedList();
    }
	
	
    public ThmNode(ILexNameToken iLexNameToken) {
    	this.id = iLexNameToken;
    	depIds = new LinkedList();
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
	
}
