package eu.compassresearch.core.interpreter;

import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;

import eu.compassresearch.ast.lex.LexNameToken;

class NamespaceUtility {

	public static ILexNameToken createChannelName(ILexIdentifierToken id)
	{
		return new LexNameToken("|CHANNELS|",id);
	}
}