package eu.compassresearch.cml.compiler;
import java.util.LinkedList;
import java.util.List;
import eu.compassresearch.cml.compiler.CmlParser.Lexer;
import eu.compassresearch.cml.compiler.CmlParser.Location;

class CmlContext {
  
}

class ParserInterface implements Lexer {
    /**
     * Method to retrieve the beginning position of the last scanned token.
     * @return the position at which the last scanned token starts.  */
  public Position getStartPos () { return null; }

    /**
     * Method to retrieve the ending position of the last scanned token.
     * @return the first position beyond the last scanned token.  */
  public Position getEndPos () { return null; }

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
  public Object getLVal () { return null; }

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * and beginning/ending positions of the token. 
     * @return the token identifier corresponding to the next token. */
  public int yylex () throws java.io.IOException { return 0; }

    /**
     * Entry point for error reporting.  Emits an error
     * referring to the given location in a user-defined way.
     *
     * @param loc The location of the element to which the
     *                error message is related
     * @param s The string for the error message.  */
  public void yyerror (Location loc, String s) { return ; }

}

class LexicographicalRuntimeException extends RuntimeException
{
  private int pos,line;
  private  String value;
  public LexicographicalRuntimeException(String chars)
    {
      this.pos = Yytoken.curPos;
      this.line = Yytoken.curLine;
      this.value = chars;
    }
  
  @Override
    public String toString()
    {
      return "Offending syntax "+(value != null ? "starting with \""+value+"\" ":"")+"found at line "+(line+1)+" position "+(pos+1);
    }
}

class Yytoken {
  public static int curLine;
  public static int curPos; 

  private int line;
  private int pos;
  protected  String value;
  public Yytoken(String value)
  {
    this.value = value;
    this.line = line;
    this.pos = pos;
  }

  public String getValue()
  {
    return this.value;
  }

  public static void main(String[] args) throws Exception
  {
    try{
    new CmlLexer(System.in).yylex();
    } catch (LexicographicalRuntimeException e)
	{
	  System.out.println(e);
	}
  }
}

class StringToken extends Yytoken {
  static StringToken currentString;
  private StringBuilder currentLine;
  private List<String> lines = new LinkedList<String>();

  private int endLine;
  private int endPos;

  public StringToken(int startLine, int startPos)
  {
    super("\"");
    currentLine = new StringBuilder();
  }

  public void append(String chars)
  {
    currentLine.append(chars);
  }

  public void newLine()
  {
    lines.add(currentLine.toString());
    currentLine = new StringBuilder();
  }

  public void endString(int line, int pos)
  {
    lines.add(currentLine.toString());
    this.endLine = line;
    this.endPos = pos;
    StringBuilder sb = new StringBuilder();
    for(String s : lines)
      sb.append(s);
    super.value = sb.toString();
    lines=null;
  }
}

class CommentBlock extends Yytoken {
  static CommentBlock current;
  private int level;
  StringBuilder content;

  public CommentBlock()
  {
    super("--");
    current=this;
    content=new StringBuilder();
    level = 1;
  }

  public void appendLine(String line)
  {
    this.content.append(line);
  }

  public void increaseLevel()
  {
    level ++;
  }

  public boolean decreaseLevel()
  {
    level--;
    return level == 0;
  }
}


%%
%class CmlLexer
%unicode
%line
%char

newline=\n
ws=[\t ]
digit=[0-9]
digits={digit}*
decdigits=[1-9][0-9]*
octdigits=0[1-7][0-7]*
identifier=[a-zA-Z$_][0-9a-zA-Z$_]*
equalsign==
minus=-
plus=\+
times=\*
divide=\/
backslash=\\
lbrace=\{
rbrace=\}
lparen=\(
rparen=\)
lbrack=\[
rbrack=\]
quote=\"
colon=:
semicolon=;
string=[^\"]*
gt=>
lt=<
at=@
comma=,
KW_Class=class
KW_for=for
KW_if=if
KW_then=then
KW_else=else
KW_define=define
KW_begin=begin
KW_end=end
KW_externalChoice=\[\]
KW_assign=:=
%state STRING, STRINGESCAPE, COMMENT
%%
<YYINITIAL>--               { yybegin(COMMENT);  }
<COMMENT>--                 { /* Todo pick up the comment */  }
<COMMENT>\/\*               { yybegin(COMMENT); }
<COMMENT>.*                 { /* We have read one line of comment add it */ }
<COMMENT>{newline}          { /* Handle newlines in block if it is a '--' block the return the token */  }

<YYINITIAL>{quote}         { yybegin(STRING);}
<STRING>{newline}          { /*Handle new line in a string */}
<STRING>{backslash}        { yybegin(STRINGESCAPE);}
<STRING>{string}           { /*Handle every character not being a double quote or newline */}
<STRINGESCAPE>{quote}      { yybegin(STRING); /*Only quote needs to be escaped everything else 
						can be in a string so far */ }
<STRING>{quote}            { yybegin(YYINITIAL); /* Return string token */ }

<YYINITIAL>{newline}       { /* Nothing to do really */ }
<YYINITIAL>{ws}            { /* Eat white space */ }
<YYINITIAL>{decdigits}     { /* TODO */ }
<YYINITIAL>{octdigits}     { /* TODO */ }
<YYINITIAL>{identifier}    { /* TODO */ }
<YYINITIAL>{equalsign}     { /* TODO */ }
<YYINITIAL>{minus}         { /* TODO */ }
<YYINITIAL>{plus}          { /* TODO */ }
<YYINITIAL>{times}         { /* TODO */ }
<YYINITIAL>{divide}        { /* TODO */ }
<YYINITIAL>{backslash}     { /* TODO */ }
<YYINITIAL>{lbrace}        { /* TODO */ }
<YYINITIAL>{rbrace}        { /* TODO */ }
<YYINITIAL>{lparen}        { /* TODO */ }
<YYINITIAL>{rparen}        { /* TODO */ }
<YYINITIAL>{lbrack}        { /* TODO */ }
<YYINITIAL>{rbrack}        { /* TODO */ }
<YYINITIAL>{colon}         { /* TODO */ }
<YYINITIAL>{semicolon}     { /* TODO */ }
<YYINITIAL>{at}            { /* TODO */ }
<YYINITIAL,STRING>.        { throw new LexicographicalRuntimeException(yytext());}
