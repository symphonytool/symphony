package eu.compassresearch.core.lexer;

class LexicographicalRuntimeException extends RuntimeException
{
  private int pos,line;
  private  String value;
  public LexicographicalRuntimeException(String chars)
    {
      this.pos = CMLToken.curPos;
      this.line = CMLToken.curLine;
      this.value = chars;
    }
  
  @Override
    public String toString()
    {
      return "Offending syntax "+(value != null ? "starting with \""+value+"\" ":"")+"found at line "+(line+1)+" position "+(pos+1);
    }
}

