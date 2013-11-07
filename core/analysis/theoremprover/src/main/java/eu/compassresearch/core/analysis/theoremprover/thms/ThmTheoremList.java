package eu.compassresearch.core.analysis.theoremprover.thms;


import java.util.Vector;

@SuppressWarnings("serial")
public class ThmTheoremList extends Vector<ThmTheorem> {
	@Override
    // Outputs each member of the list
    public String toString() 
    {
    	StringBuilder sb = new StringBuilder();

    	for (ThmTheorem tp : this) {
    		sb.append(tp.toString());
    		sb.append("\n\n");
    	}

		return sb.toString();
    }
}
