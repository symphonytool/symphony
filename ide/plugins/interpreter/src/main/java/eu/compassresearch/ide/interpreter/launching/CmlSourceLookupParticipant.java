package eu.compassresearch.ide.interpreter.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;

import eu.compassresearch.ide.interpreter.model.CmlStackFrame;

public class CmlSourceLookupParticipant extends AbstractSourceLookupParticipant
{

	@Override
	public String getSourceName(Object object) throws CoreException
	{
		if (object instanceof CmlStackFrame)
		{
			return ((CmlStackFrame) object).getOnlyFileName();
		}
		return null;
	}

}
