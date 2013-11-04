package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
import java.util.Vector;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;

@SuppressWarnings("serial")
public class NodeNameList extends Vector<ILexNameToken>{
	
	/***
	 * Method to remove a list of dependencies from a list of dependencies
	 * @param nodeDeps
	 * @param procNames
	 * @return
	 */
	public NodeNameList removeDeps(
			NodeNameList procNames) {
		//TODO: poss use removeAll();
		
		//Need to add remove all inter-process dependencies 
		//For each name used within a process		
		for(ILexNameToken pn :procNames)
		{
			//for each dependency 
			for(Iterator<ILexNameToken> itr = this.listIterator(); itr.hasNext(); )
			{
				ILexNameToken nd = itr.next();
				//if the dependency is a process name, remove it.
				if(pn.toString().equals(nd.toString()))
				{
					itr.remove();
				}
			}
		}
		return this;
	}
	
	
	/**
	 * Method to restrict node dependency list to only those in a second set 
	 * @param nodeDeps
	 * @param procNames
	 * @return nodeNameList
	 */
	public NodeNameList restrictDeps(
			NodeNameList restr) {
		//TODO: poss use retainAll();
		
		boolean match = false;
		
		//for each node name in this collection 
		for(Iterator<ILexNameToken> itr = this.listIterator(); itr.hasNext(); )
		{
			ILexNameToken n = itr.next();
			match = false;
			
			//For each name to restrict		
			for(ILexNameToken r :restr)
			{
				//if the dependency is a process name, then add to the new list
				if(r.toString().equals(n.toString()))
				{
					match = true;
					break;
				}
			}
			if (!match)
			{
				itr.remove();
			}
		}
		return this;
	}
	
	/****
	 * Method to return the NodeNameList as a comma-separated string of the list contents and their line number
	 */
	public String nameAndLocs() {
		StringBuilder sb = new StringBuilder();
		//for each dependency 
		for(Iterator<ILexNameToken> itr = this.listIterator(); itr.hasNext(); )
		{
			ILexNameToken nd = itr.next();
			
			sb.append(nd.toString());
			sb.append(" (line number: " + nd.getLocation().getStartLine() +")");
			if(itr.hasNext()){
				sb.append(",\n");
			}
		}
		return sb.toString();
	}
}
