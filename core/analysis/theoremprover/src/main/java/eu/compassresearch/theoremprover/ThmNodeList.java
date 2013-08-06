package eu.compassresearch.theoremprover;

import java.util.LinkedList;
import java.util.Vector;

import org.overture.ast.intf.lex.ILexNameToken;
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
    		sb.append("\n\n");
    	}

		return sb.toString();
    }
    
    
    /******
     * Checks if all dependencies have been fulfilled in the ordered list
     ******/
    public boolean allDepsFulfilled(LinkedList<ILexNameToken> depIds){
    	boolean flag = false;
    	
    	for (ILexNameToken d : depIds){
    		flag = false;
    		
    		ILexNameToken tempdepId = d;
    		for (ThmNode tp : this){

        		ILexNameToken temptpId = tp.getId();
        		boolean idNameMatch = tempdepId.getName().equals(temptpId.getName());

    			//if (d == tp.getId()){
    			if (tempdepId.getName().equals(temptpId.getName())){
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
