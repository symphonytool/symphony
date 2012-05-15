public class Main {

    static class InternalLexerMockup implements YYParser.Lexer {
	// public static final int EOF = 0;
	// public static final int INTEGER_LITERAL = 258;
	// public static final int PLUS = 259;
	// public static final int MULT = 260;

	public int[] seq = { YYParser.INTEGER_LITERAL, YYParser.PLUS, YYParser.INTEGER_LITERAL };
	public int pos = -1;

	/**
	 * Method to retrieve the semantic value of the last scanned token.
	 * @return the semantic value of the last scanned token.  */
	public Object getLVal () {
	    return new Integer(23);
	}

	/**
	 * Entry point for the scanner.  Returns the token identifier corresponding
	 * to the next token and prepares to return the semantic value
	 * of the token.
	 * @return the token identifier corresponding to the next token. */
	public int yylex () { //throws java.io.IOException {
	    System.out.println("yylex: " + pos);
	    pos++;
	    if (pos < seq.length ) {
		return seq[pos];
	    } else {
		return YYParser.EOF;
	    }
	}

	/**
	 * Entry point for error reporting.  Emits an error
	 * in a user-defined way.
	 *
	 * 
	 * @param s The string for the error message.  */
	public void yyerror (String s) {
	    System.err.println("yyerror: " + s);
	}
    }


    public static void main(String[] args) throws java.io.IOException {
	System.out.println("starting.");
	YYParser calc = new YYParser(new InternalLexerMockup());
	boolean result = calc.parse();
	System.out.println("done.");
    }
}