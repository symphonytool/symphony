package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.types.PType;

public class PTypeAsssistance
  {
    
    public boolean equals(PType one, PType other)
      {
        switch (one.kindPType())
          {
//            case ACTIONPARAGRAPH:
//              break;
            case BASIC:
              return one.getClass().equals(other.getClass());
            case BRACKET:
              break;
//            case CHANNEL:
//              break;
//            case CHANSETPARAGRAPH:
//              break;
            case CLASS:
              break;
//            case ERROR:
//              break;
            case FUNCTION:
              break;
//            case FUNCTIONPARAGRAPH:
//              break;
            case INVARIANT:
              break;
            case MAP:
              break;
            case OPERATION:
              break;
            case OPTIONAL:
              break;
            case PARAMETER:
              break;
//            case PROCESSPARAGRAPH:
//              break;
            case PRODUCT:
              break;
            case QUOTE:
              break;
            case SEQ:
              break;
            case SET:
              break;
//            case SOURCE:
//              break;
//            case STATEMENT:
//              break;
//            case TYPEPARAGRAPH:
//              break;
            case UNDEFINED:
              break;
            case UNION:
              break;
            case UNKNOWN:
              break;
            case UNRESOLVED:
              break;
//            case VALUEPARAGRAPH:
//              break;
            case VOID:
              break;
            case VOIDRETURN:
              break;
          
          }
        return false;
      }
    
  }
