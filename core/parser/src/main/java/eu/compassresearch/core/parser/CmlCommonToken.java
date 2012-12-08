package eu.compassresearch.core.parser;

import org.antlr.runtime.Token;
import org.antlr.runtime.CommonToken;

import org.overture.ast.lex.LexToken;

public class CmlCommonToken extends CommonToken {
    protected LexToken cmlToken;

    public CmlCommonToken(Token t, LexToken cmlt) {
	super(t);
	cmlToken = cmlt;
    }
}
