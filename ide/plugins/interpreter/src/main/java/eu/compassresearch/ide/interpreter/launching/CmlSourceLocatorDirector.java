package eu.compassresearch.ide.interpreter.launching;

import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

public class CmlSourceLocatorDirector extends AbstractSourceLookupDirector
{

	public void initializeParticipants()
	{
		addParticipants(new ISourceLookupParticipant[] { new CmlSourceLookupParticipant() });

	}
}
