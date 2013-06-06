package eu.compassresearch.ide.cml.interpreter.launch;

import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

public class CmlSourceLookupDirector extends AbstractSourceLookupDirector {

	@Override
	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[]{new CmlSourceLookupParticipant()});
	}

}
