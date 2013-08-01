package eu.compassresearch.theoremprover;

import java.util.LinkedList;
import java.util.Vector;

import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.core.common.AnalysisArtifact;


/******************
 *
 * Stores collection of Theory objects
 *
 *
 *******************/
public class ThmNodeList extends Vector<ThmNode> implements AnalysisArtifact {


    @Override
    // override superclass method
    public String toString() 
    {
    	StringBuilder sb = new StringBuilder();

    	for (ThmNode tp : this) {
    		sb.append(tp.toString());
    		//Add line break?
    	}

		return sb.toString();
    }
    
    
    /******
     * Checks if all dependancies have been fulfilled in the ordered list
     ******/
    public boolean allDepsFulfilled(LinkedList<LexNameToken> depIds){
    	boolean flag = false;
    	
    	for (LexNameToken d : depIds){
    		flag = false;
    		
    		for (ThmNode tp : this){
    			if (d == tp.getId()){
    				flag = true;
    				break;
    			}
    		}
    			
    		if (!flag)
    		{
    			return false;
    		}
    	}
    	
    	return true;
    }
	
}
