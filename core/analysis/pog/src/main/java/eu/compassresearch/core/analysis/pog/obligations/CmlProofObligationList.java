/******************
 *
 * Inspired by Proof Obligation List from Overture
 * Class as complex list Datatype, stores collection 
 * of Proof Obligation objects
 *
 *******************/

package eu.compassresearch.core.analysis.pog.obligations;

import java.util.Vector;

import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.common.AnalysisArtifact;

@SuppressWarnings("serial")
public class CmlProofObligationList extends Vector<IProofObligation> implements AnalysisArtifact, IProofObligationList {

	@Override
	public void trivialCheck()
	{
		// only used in overture 
		
	}

	@Override
	public void renumber()
	{
		// only used in overture 
		
	}

	@Override
	public void renumber(int from)
	{
		// only used in overture 
		
	}


}