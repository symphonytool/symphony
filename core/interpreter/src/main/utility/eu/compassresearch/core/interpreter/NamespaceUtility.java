package eu.compassresearch.core.interpreter;

import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;

import eu.compassresearch.ast.lex.LexNameToken;

class NamespaceUtility
{

	public static ILexNameToken createChannelName(ILexIdentifierToken id)
	{
		return new LexNameToken("|CHANNELS|", id.getName(), id.getLocation(), false, true);
	}

	public static ILexNameToken createChansetName(ILexIdentifierToken id)
	{
		return new LexNameToken("|CHANSET|", id.getName(), id.getLocation(), false, true);
	}

	public static ILexNameToken getStartTimeName()
	{
		return new LexNameToken("|TIMEOUT|", "STARTTIME", new LexLocation());
	}

	public static ILexNameToken getSeqForName()
	{
		return new LexNameToken("|FORSEQ|", "v", new LexLocation());
	}

	public static ILexNameToken getVarExpContextName()
	{
		return new LexNameToken("|VARSET|", "namesetExpression", new LexLocation());
	}
}
