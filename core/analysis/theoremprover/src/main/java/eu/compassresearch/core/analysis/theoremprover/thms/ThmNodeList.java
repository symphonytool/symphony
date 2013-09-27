package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Vector;

import org.overture.ast.intf.lex.ILexNameToken;

import eu.compassresearch.core.common.AnalysisArtifact;


/*****************
 * Stores collection of Theory objects
 *****************/
@SuppressWarnings("serial")
public class ThmNodeList extends Vector<ThmNode> implements AnalysisArtifact {


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
    		
    		ILexNameToken tempdepId = d;
    		//for each of the nodes already in this list
    		for (ThmNode tp : this){

        		ILexNameToken temptpId = tp.getId();

    			//if (the dependency d is the same as the node name temptp
    			if (tempdepId.getName().equals(temptpId.getName())){
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

		for(ThmNode o: this)
		{
			o.restrictDeps(procNodeNames);
		}		
		return this;
	}
}
