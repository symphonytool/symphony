package eu.compassresearch.core.interpreter.debug;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.interpreter.runtime.Context;

public class DebugContext
{
	public final ILexLocation location;
	public final Context ctxt;

	public DebugContext(ILexLocation location, Context ctxt)
	{
		this.location = location;
		this.ctxt = ctxt;
	}
}
