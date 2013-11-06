package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Vector;

import org.overture.ast.intf.lex.ILexNameToken;


/*****************
 * Stores collection of Theory objects
 *****************/
@SuppressWarnings("serial")
public class ThmNodeList extends Vector<ThmNode>{


	@Override
    // Outputs each member of the list
    public String toString() 
    {
    	StringBuilder sb = new StringBuilder();

    	for (ThmNode tp : this) {
    		sb.append(tp.toString());
    		sb.append("\n\n");
    	}

		return sb.toString();
    }
    
    
    /******
     * Checks if all dependencies have been fulfilled in the ordered list
     ******/
    public boolean allDepsFulfilled(NodeNameList depIds){
    	boolean flag = false;
    	
    	//for each dependency supplied in the parameter list
    	for (ILexNameToken d : depIds){
    		flag = false;

			//remove 'pre_' on depandancies - preconditions are automatically generated, 
			//as part of the function/operation, so must depend on the function/operation itself.
    		String tempdepId = d.getName();
    		if(tempdepId.startsWith("pre_"))
    		{
    			tempdepId = tempdepId.replace("pre_", "");
    		}
    		
    		//for each of the nodes already in this list
    		for (ThmNode tp : this){
        		String temptpId = tp.getId().getName(); //CHANGED DUE TO NEW LEXNAME STUFF
        		
        		//if (the dependency tempdepId is the same as the node name temptpId
    			if (tempdepId.equals(temptpId)){
    				//set the flag to true and break from the loop and return true 
    				flag = true;
    				break;
    			}
    		}
    		//if the dependency d is not in this list, return false.
    		if (!flag)
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    //Get all the node dependencies from the nodes in this list
    public NodeNameList getAllNodeDeps() 
	{
    	NodeNameList nodeDeps = new NodeNameList();
		
		for(ThmNode n : this)
			nodeDeps.addAll(n.getDepIds());
		return nodeDeps;
	}	
    
    //for each node in the list, restrict the dependencies
	public ThmNodeList restrictExtOperationsDeps(NodeNameList procNodeNames) {

		for(ThmNode n: this)
		{
			n.restrictDeps(procNodeNames);
		}		
		return this;
	}
	
	//BASIC ERROR DETECTION - JUST GETS ALL ThmNode IDs with errors.
	public NodeNameList getErrorNodes(){

    	NodeNameList errNodes = new NodeNameList();
    	
		for(ThmNode n: this)
		{
			if(n.isError())
			{
				errNodes.add(n.getId());
			}
		}
		return errNodes;
	}


	/****
	 * Method to duplicate contents of this list into a new object
	 * @return Duplicated node list
	 */
	public ThmNodeList duplicate() {
		ThmNodeList newList = new ThmNodeList();
		newList.addAll(this);
		return newList;
	}
}
