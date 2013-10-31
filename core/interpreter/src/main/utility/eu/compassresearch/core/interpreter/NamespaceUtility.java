package eu.compassresearch.core.interpreter;

import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;

import eu.compassresearch.ast.lex.CmlLexNameToken;

class NamespaceUtility
{

	public static ILexNameToken createChannelName(ILexIdentifierToken id)
	{
		return new CmlLexNameToken("|CHANNELS|", id.getName(), id.getLocation(), false, true);
	}

	public static ILexNameToken createChansetName(ILexIdentifierToken id)
	{
		return new CmlLexNameToken("|CHANSET|", id.getName(), id.getLocation(), false, true);
	}

	public static ILexNameToken getStartTimeName()
	{
		return new CmlLexNameToken("|TIMEOUT|", "STARTTIME", new LexLocation());
	}

	public static ILexNameToken getSeqForName()
	{
		return new CmlLexNameToken("|FORSEQ|", "v", new LexLocation());
	}

	public static ILexNameToken getVarExpContextName()
	{
		return new CmlLexNameToken("|VARSET|", "namesetExpression", new LexLocation());
	}
}
