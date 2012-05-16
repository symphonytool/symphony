package eu.compassresearch.core.lexer;

public class CMLToken {
  public static int curLine;
  public static int curPos; 

  private int line;
  private int pos;
  protected  String value;
  public CMLToken(String value)
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

