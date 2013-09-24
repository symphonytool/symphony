package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.common.AnalysisArtifact;

import java.util.Vector;

public class ThmTheoremList extends Vector<ThmTheorem> implements AnalysisArtifact {
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
