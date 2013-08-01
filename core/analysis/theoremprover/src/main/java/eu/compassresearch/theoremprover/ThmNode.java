package eu.compassresearch.theoremprover;

import java.util.LinkedList;

import org.overture.ast.lex.LexNameToken;

public class ThmNode {

	public LexNameToken id;
	public LinkedList <LexNameToken> depIds;
	public String str;
	
    public ThmNode(LexNameToken id) {
    	this.id = id;
    }
    

	public LexNameToken getId(){
		return id;
	}
	
	public LinkedList <LexNameToken> getDepIds(){
		return depIds;
	}
	
	public int getnumDep(){
		return depIds.size();
	}
	
	public String getString(){
		return str;
	}
	

	public void addDependant(LexNameToken id) {
	  depIds.add(id);
	}

	public void addDependants(LinkedList <LexNameToken> ids) {
	  depIds.addAll(ids);
	}
	
	public void setString(String str) {
	  this.str = str;
	}	
	
	
//		public void addNode(ThmNode tp){
//			this.depIds.allAll(tp.getDepIds();
//			this.dcl.append(tp.getDecl());		
//		}
	
	
		
	@Override
	public String toString(){
		return str;
	}
	
}
