package eu.compassresearch.core.interpreter;

import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;

class NamespaceUtility
{
	private static ILexNameToken namesetName = new CmlLexNameToken("|NAMESET|", "writable_names", new LexLocation());

	public static ILexNameToken createSimpleName(ILexIdentifierToken id)
	{
		return new CmlLexNameToken("", id.getName(), id.getLocation(), false, false);
	}
	
	public static ILexNameToken getRenamingValueName()
	{
		return new CmlLexNameToken("|RENAMING|", "values", new LexLocation());
	}

	public static ILexNameToken createChannelName(ILexIdentifierToken id)
	{
		return new CmlLexNameToken("|CHANNELS|", id.getName(), id.getLocation(), false, false);
	}

	public static ILexNameToken createChansetName(ILexIdentifierToken id)
	{
		return new CmlLexNameToken("|CHANSET|", id.getName(), id.getLocation(), false, false);
	}

	public static ILexNameToken getStartTimeName()
	{
		return new CmlLexNameToken("|TIMEOUT|", "STARTTIME", new LexLocation());
	}
	
	public static ILexNameToken getStartsByTimeName()
	{
		return new CmlLexNameToken("|STARTSBY|", "STARTTIME", new LexLocation());
	}
	
	public static ILexNameToken getEndsByTimeName()
	{
		return new CmlLexNameToken("|ENDSBY|", "STARTTIME", new LexLocation());
	}

	public static ILexNameToken getSeqForName()
	{
		return new CmlLexNameToken("|FOR_SEQ|", "v", new LexLocation());
	}
	
	public static ILexNameToken getForAllName()
	{
		return new CmlLexNameToken("|FOR_ALL|", "v", new LexLocation());
	}
	
	public static ILexNameToken getForIndexByName()
	{
		return new CmlLexNameToken("|FOR_INDEX|", "by", new LexLocation());
	}
	
	public static ILexNameToken getForIndexToName()
	{
		return new CmlLexNameToken("|FOR_INDEX|", "to", new LexLocation());
	}

	public static ILexNameToken getNamesetName()
	{
		return namesetName;
	}

	public static ILexNameToken getLeftPrecalculatedChannetSet()
	{
		return new CmlLexNameToken("|ALPHABETISED_PARALLELISM|", "leftChannelset", new LexLocation());
	}

	public static ILexNameToken getRightPrecalculatedChannetSet()
	{
		return new CmlLexNameToken("|ALPHABETISED_PARALLELISM|", "rightChannelset", new LexLocation());
	}

	public static ILexNameToken getReplicationNodeName(INode node)
	{
		// The name of the value holding the state of the remaining values of the replication
		ILexLocation loc = LocationExtractor.extractLocation(node);
		return new CmlLexNameToken("|REPLICATION|", loc.toShortString(), loc);
	}
}
