
/* A Bison parser, made by GNU Bison 2.5.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java
   
      Copyright (C) 2007, 2008 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */
package eu.compassresearch.core.parser;
/* First part of user declarations.  */

import static eu.compassresearch.core.parser.CmlParser.YyGotoClass.yypgoto_;
import static eu.compassresearch.core.parser.CmlParser.YyDefGotoClass.yydefgoto_;
import static eu.compassresearch.core.parser.CmlParser.YyTableClass.yytable_;
import static eu.compassresearch.core.parser.CmlParser.YyCheckClass.yycheck_;
import static eu.compassresearch.core.parser.CmlParser.YyTableClass.yytable_ninf_;
import static eu.compassresearch.core.parser.CmlParser.YyTokenClass.yystos_;
import static eu.compassresearch.core.parser.CmlParser.YyPactClass.yypact_ninf_;
import static eu.compassresearch.core.parser.CmlParser.YyPactClass.yypact_;
/* "%code imports" blocks.  */

/* Line 40 of cmlskeleton.java.m4  */
/* Line 7 of "src/main/bison/cml.y"  */


// ******************************
// *** required local imports ***
// ******************************

// required standard Java definitions
    import java.util.*;
    import java.io.File;
    import java.lang.reflect.*;
    import eu.compassresearch.ast.definitions.*;
    import eu.compassresearch.ast.declarations.*;
    import eu.compassresearch.ast.expressions.*;
    import eu.compassresearch.ast.statements.*;
    import eu.compassresearch.ast.actions.*;
    import eu.compassresearch.ast.process.*;
    import eu.compassresearch.ast.patterns.*;
    import eu.compassresearch.ast.program.*;
    import eu.compassresearch.ast.types.*;
    import eu.compassresearch.ast.lex.*;
    import eu.compassresearch.ast.typechecker.NameScope;
    import eu.compassresearch.ast.node.*;
    //    import org.overture.transforms.*;
    import eu.compassresearch.ast.util.*;
    import eu.compassresearch.core.lexer.CmlLexeme;
    import eu.compassresearch.core.lexer.CmlLexer;
    import eu.compassresearch.core.lexer.Position;
    public



/* Line 40 of cmlskeleton.java.m4  */
/* Line 81 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

/**
 * A Bison parser, automatically generated from <tt>src/main/bison/cml.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
class CmlParser
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.5";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "src/main/resources/cmlskeleton.java.m4";


  /** True if verbose error messages are enabled.  */
  public boolean errorVerbose = false;


  /**
   * A class defining a pair of positions.  Positions, defined by the
   * <code>Position</code> class, denote a point in the input.
   * Locations represent a part of the input through the beginning
   * and ending positions.  */
  public class Location {
    /** The first, inclusive, position in the range.  */
    public Position begin;

    /** The first position beyond the range.  */
    public Position end;

    /**
     * Create a <code>Location</code> denoting an empty range located at
     * a given point.
     * @param loc The position at which the range is anchored.  */
    public Location (Position loc) {
      this.begin = this.end = loc;
    }

    /**
     * Create a <code>Location</code> from the endpoints of the range.
     * @param begin The first position included in the range.
     * @param end   The first position beyond the range.  */
    public Location (Position begin, Position end) {
      this.begin = begin;
      this.end = end;
    }

    /**
     * Print a representation of the location.  For this to be correct,
     * <code>Position</code> should override the <code>equals</code>
     * method.  */
    public String toString () {
      if (begin.equals (end))
        return begin.toString ();
      else
        return begin.toString () + "-" + end.toString ();
    }
  }



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int CLASS = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int END = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int PROCESS = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int INITIAL = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int EQUALS = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int AT = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int BEGIN = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int ACTIONS = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int BARTILDEBAR = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int LRSQUARE = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int TBAR = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int LPAREN = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int RPAREN = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUARE = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int RSQUARE = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSKIP = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSTOP = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPCHAOS = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPDIV = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPWAIT = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int RARROW = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int LARROW = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLY = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int RCURLY = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int BAR = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int DBAR = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANNELS = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANSETS = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int TYPES = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int SEMI = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int DCOLON = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int COMPOSE = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int OF = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int STAR = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int TO = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int INMAPOF = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int MAPOF = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int SEQOF = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int SEQ1OF = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int SETOF = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUSGT = 298;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONDASH = 299;
  /** Token number, to be returned by the scanner.  */
  public static final int DEQUALS = 300;
  /** Token number, to be returned by the scanner.  */
  public static final int INV = 301;
  /** Token number, to be returned by the scanner.  */
  public static final int VALUES = 302;
  /** Token number, to be returned by the scanner.  */
  public static final int FUNCTIONS = 303;
  /** Token number, to be returned by the scanner.  */
  public static final int PRE = 304;
  /** Token number, to be returned by the scanner.  */
  public static final int POST = 305;
  /** Token number, to be returned by the scanner.  */
  public static final int MEASURE = 306;
  /** Token number, to be returned by the scanner.  */
  public static final int SUBCLASSRESP = 307;
  /** Token number, to be returned by the scanner.  */
  public static final int NOTYETSPEC = 308;
  /** Token number, to be returned by the scanner.  */
  public static final int OPERATIONS = 309;
  /** Token number, to be returned by the scanner.  */
  public static final int FRAME = 310;
  /** Token number, to be returned by the scanner.  */
  public static final int RD = 311;
  /** Token number, to be returned by the scanner.  */
  public static final int WR = 312;
  /** Token number, to be returned by the scanner.  */
  public static final int STATE = 313;
  /** Token number, to be returned by the scanner.  */
  public static final int LET = 314;
  /** Token number, to be returned by the scanner.  */
  public static final int IN = 315;
  /** Token number, to be returned by the scanner.  */
  public static final int IF = 316;
  /** Token number, to be returned by the scanner.  */
  public static final int THEN = 317;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSEIF = 318;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSE = 319;
  /** Token number, to be returned by the scanner.  */
  public static final int CASES = 320;
  /** Token number, to be returned by the scanner.  */
  public static final int OTHERS = 321;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUS = 322;
  /** Token number, to be returned by the scanner.  */
  public static final int MINUS = 323;
  /** Token number, to be returned by the scanner.  */
  public static final int ABS = 324;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOOR = 325;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT = 326;
  /** Token number, to be returned by the scanner.  */
  public static final int CARD = 327;
  /** Token number, to be returned by the scanner.  */
  public static final int POWER = 328;
  /** Token number, to be returned by the scanner.  */
  public static final int DUNION = 329;
  /** Token number, to be returned by the scanner.  */
  public static final int DINTER = 330;
  /** Token number, to be returned by the scanner.  */
  public static final int HD = 331;
  /** Token number, to be returned by the scanner.  */
  public static final int TL = 332;
  /** Token number, to be returned by the scanner.  */
  public static final int LEN = 333;
  /** Token number, to be returned by the scanner.  */
  public static final int ELEMS = 334;
  /** Token number, to be returned by the scanner.  */
  public static final int INDS = 335;
  /** Token number, to be returned by the scanner.  */
  public static final int REVERSE = 336;
  /** Token number, to be returned by the scanner.  */
  public static final int CONC = 337;
  /** Token number, to be returned by the scanner.  */
  public static final int DOM = 338;
  /** Token number, to be returned by the scanner.  */
  public static final int RNG = 339;
  /** Token number, to be returned by the scanner.  */
  public static final int MERGE = 340;
  /** Token number, to be returned by the scanner.  */
  public static final int INVERSE = 341;
  /** Token number, to be returned by the scanner.  */
  public static final int ELLIPSIS = 342;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRARROW = 343;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDER = 344;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDERNAME = 345;
  /** Token number, to be returned by the scanner.  */
  public static final int DOT = 346;
  /** Token number, to be returned by the scanner.  */
  public static final int DOTHASH = 347;
  /** Token number, to be returned by the scanner.  */
  public static final int NUMERAL = 348;
  /** Token number, to be returned by the scanner.  */
  public static final int LAMBDA = 349;
  /** Token number, to be returned by the scanner.  */
  public static final int NEW = 350;
  /** Token number, to be returned by the scanner.  */
  public static final int SELF = 351;
  /** Token number, to be returned by the scanner.  */
  public static final int ISUNDER = 352;
  /** Token number, to be returned by the scanner.  */
  public static final int PREUNDER = 353;
  /** Token number, to be returned by the scanner.  */
  public static final int ISOFCLASS = 354;
  /** Token number, to be returned by the scanner.  */
  public static final int TILDE = 355;
  /** Token number, to be returned by the scanner.  */
  public static final int DCL = 356;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONEQUALS = 357;
  /** Token number, to be returned by the scanner.  */
  public static final int ATOMIC = 358;
  /** Token number, to be returned by the scanner.  */
  public static final int DEQRARROW = 359;
  /** Token number, to be returned by the scanner.  */
  public static final int RETURN = 360;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER = 361;
  /** Token number, to be returned by the scanner.  */
  public static final int BACKTICK = 362;
  /** Token number, to be returned by the scanner.  */
  public static final int DIVIDE = 363;
  /** Token number, to be returned by the scanner.  */
  public static final int REM = 364;
  /** Token number, to be returned by the scanner.  */
  public static final int MOD = 365;
  /** Token number, to be returned by the scanner.  */
  public static final int LT = 366;
  /** Token number, to be returned by the scanner.  */
  public static final int LTE = 367;
  /** Token number, to be returned by the scanner.  */
  public static final int GT = 368;
  /** Token number, to be returned by the scanner.  */
  public static final int GTE = 369;
  /** Token number, to be returned by the scanner.  */
  public static final int NEQ = 370;
  /** Token number, to be returned by the scanner.  */
  public static final int OR = 371;
  /** Token number, to be returned by the scanner.  */
  public static final int AND = 372;
  /** Token number, to be returned by the scanner.  */
  public static final int EQRARROW = 373;
  /** Token number, to be returned by the scanner.  */
  public static final int LTEQUALSGT = 374;
  /** Token number, to be returned by the scanner.  */
  public static final int INSET = 375;
  /** Token number, to be returned by the scanner.  */
  public static final int NOTINSET = 376;
  /** Token number, to be returned by the scanner.  */
  public static final int SUBSET = 377;
  /** Token number, to be returned by the scanner.  */
  public static final int PROPER_SUBSET = 378;
  /** Token number, to be returned by the scanner.  */
  public static final int UNION = 379;
  /** Token number, to be returned by the scanner.  */
  public static final int BACKSLASH = 380;
  /** Token number, to be returned by the scanner.  */
  public static final int INTER = 381;
  /** Token number, to be returned by the scanner.  */
  public static final int CARET = 382;
  /** Token number, to be returned by the scanner.  */
  public static final int DPLUS = 383;
  /** Token number, to be returned by the scanner.  */
  public static final int MAPMERGE = 384;
  /** Token number, to be returned by the scanner.  */
  public static final int LTCOLON = 385;
  /** Token number, to be returned by the scanner.  */
  public static final int LTDASHCOLON = 386;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONGT = 387;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONDASHGT = 388;
  /** Token number, to be returned by the scanner.  */
  public static final int COMP = 389;
  /** Token number, to be returned by the scanner.  */
  public static final int DSTAR = 390;
  /** Token number, to be returned by the scanner.  */
  public static final int FORALL = 391;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS = 392;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS1 = 393;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING = 394;
  /** Token number, to be returned by the scanner.  */
  public static final int VRES = 395;
  /** Token number, to be returned by the scanner.  */
  public static final int RES = 396;
  /** Token number, to be returned by the scanner.  */
  public static final int VAL = 397;
  /** Token number, to be returned by the scanner.  */
  public static final int HEX_LITERAL = 398;
  /** Token number, to be returned by the scanner.  */
  public static final int QUOTE_LITERAL = 399;
  /** Token number, to be returned by the scanner.  */
  public static final int AMP = 400;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREBAR = 401;
  /** Token number, to be returned by the scanner.  */
  public static final int DLSQUARE = 402;
  /** Token number, to be returned by the scanner.  */
  public static final int DRSQUARE = 403;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRSQUARE = 404;
  /** Token number, to be returned by the scanner.  */
  public static final int COMMA = 405;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREDBAR = 406;
  /** Token number, to be returned by the scanner.  */
  public static final int DBARRSQUARE = 407;
  /** Token number, to be returned by the scanner.  */
  public static final int COLON = 408;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLYBAR = 409;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRCURLY = 410;
  /** Token number, to be returned by the scanner.  */
  public static final int QUESTION = 411;
  /** Token number, to be returned by the scanner.  */
  public static final int BANG = 412;
  /** Token number, to be returned by the scanner.  */
  public static final int SLASH = 413;
  /** Token number, to be returned by the scanner.  */
  public static final int SLASHBACKSLASH = 414;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREGT = 415;
  /** Token number, to be returned by the scanner.  */
  public static final int ENDBY = 416;
  /** Token number, to be returned by the scanner.  */
  public static final int STARTBY = 417;
  /** Token number, to be returned by the scanner.  */
  public static final int TBOOL = 418;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT = 419;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT1 = 420;
  /** Token number, to be returned by the scanner.  */
  public static final int TINT = 421;
  /** Token number, to be returned by the scanner.  */
  public static final int TRAT = 422;
  /** Token number, to be returned by the scanner.  */
  public static final int TREAL = 423;
  /** Token number, to be returned by the scanner.  */
  public static final int TCHAR = 424;
  /** Token number, to be returned by the scanner.  */
  public static final int TTOKEN = 425;
  /** Token number, to be returned by the scanner.  */
  public static final int PRIVATE = 426;
  /** Token number, to be returned by the scanner.  */
  public static final int PROTECTED = 427;
  /** Token number, to be returned by the scanner.  */
  public static final int PUBLIC = 428;
  /** Token number, to be returned by the scanner.  */
  public static final int LOGICAL = 429;
  /** Token number, to be returned by the scanner.  */
  public static final int nameset = 430;
  /** Token number, to be returned by the scanner.  */
  public static final int namesetExpr = 431;
  /** Token number, to be returned by the scanner.  */
  public static final int typeVarIdentifier = 432;
  /** Token number, to be returned by the scanner.  */
  public static final int UMINUS = 433;
  /** Token number, to be returned by the scanner.  */
  public static final int UPLUS = 434;



  
  private Location yylloc (YYStack rhs, int n)
  {
    if (n > 0)
      return new Location (rhs.locationAt (1).begin, rhs.locationAt (n).end);
    else
      return new Location (rhs.locationAt (0).end);
  }

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>CmlParser</tt>.
   */
  public interface Lexer {
    /**
     * Method to retrieve the beginning position of the last scanned token.
     * @return the position at which the last scanned token starts.  */
    Position getStartPos ();

    /**
     * Method to retrieve the ending position of the last scanned token.
     * @return the first position beyond the last scanned token.  */
    Position getEndPos ();

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * and beginning/ending positions of the token. 
     * @return the token identifier corresponding to the next token. */
    int yylex () throws java.io.IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * referring to the given location in a user-defined way.
     *
     * @param loc The location of the element to which the
     *                error message is related
     * @param s The string for the error message.  */
     void yyerror (Location loc, String s);
  }

  /** The object doing lexical analysis for us.  */
  private Lexer yylexer;
  
  



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public CmlParser (Lexer yylexer) {
    this.yylexer = yylexer;
    
  }

  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final java.io.PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  private final int yylex () throws java.io.IOException {
    return yylexer.yylex ();
  }
  protected final void yyerror (Location loc, String s) {
    yylexer.yyerror (loc, s);
  }

  
  protected final void yyerror (String s) {
    yylexer.yyerror ((Location)null, s);
  }
  protected final void yyerror (Position loc, String s) {
    yylexer.yyerror (new Location (loc), s);
  }

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    private Location[] locStack = new Location[16];
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;
    
    public final void push (int state, Object value    	   	      	    , Location loc) {
      height++;
      if (size == height) 
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  
	  Location[] newLocStack = new Location[size * 2];
	  System.arraycopy (locStack, 0, newLocStack, 0, height);
	  locStack = newLocStack;
	  
	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      locStack[height] = loc;
      valueStack[height] = value;
    }

    public final void pop () {
      height--;
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height, null);
        java.util.Arrays.fill (locStack, height - num + 1, height, null);
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Location locationAt (int i) {
      return locStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (java.io.PrintStream out)
    {
      out.print ("Stack now");
      
      for (int i = 0; i < height; i++)
        {
	  out.print (' ');
	  out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).  */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).  */
  public static final int YYABORT = 1;

  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.  */
  public static final int YYERROR = 2;

  /**
   * Returned by a Bison action in order to print an error message and start
   * error recovery.  */
  public static final int YYFAIL = 3;

  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;

  private int yyerrstatus_ = 0;

  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.  */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) 
  {
    Object yyval;
    Location yyloc = yylloc (yystack, yylen);

    /* If YYLEN is nonzero, implement the default value of the action:
       `$$ = $1'.  Otherwise, use the top of the stack.
    
       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);
    
    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
	  case 2:
  if (yyn == 2)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 362 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> decls = (List<PDeclaration>) ((yystack.valueAt (1-(1))));  
    currentSourceFile.setDecls(decls);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 368 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> globalDecls = (List<PDeclaration>)((yystack.valueAt (2-(1))));
    List<PDeclaration> decls = (List<PDeclaration>) ((yystack.valueAt (2-(2))));  
    decls.addAll(globalDecls);
    currentSourceFile.setDecls(decls);
};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 375 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> globalDecls = (List<PDeclaration>)((yystack.valueAt (1-(1))));
    currentSourceFile.setDecls(globalDecls);
};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 383 of "src/main/bison/cml.y"  */
    {  
      List<PDeclaration> programParagraphList = 
	  new Vector<PDeclaration>();
      programParagraphList.add((PDeclaration)((yystack.valueAt (1-(1)))));
      yyval = programParagraphList;   
  };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 391 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> programParagraphList = (List<PDeclaration>)((yystack.valueAt (2-(1))));

    if (programParagraphList == null) 
	programParagraphList = new Vector<PDeclaration>();
	    
    programParagraphList.add((PDeclaration)((yystack.valueAt (2-(2)))));
    yyval = programParagraphList;
 };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 403 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 404 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 405 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 406 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 11:
  if (yyn == 11)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 412 of "src/main/bison/cml.y"  */
    { 
  AClassbodyDefinition c = new AClassbodyDefinition();
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (4-(2))));
  Position startPos =  ((CmlLexeme)((yystack.valueAt (4-(1))))).getStartPos();
  Position endPos = ((CmlLexeme)((yystack.valueAt (4-(3))))).getEndPos(); // TODO Fix me, the ending position is the 
  LexNameToken lexName = extractLexNameToken( id ); 
  LexIdentifierToken classIdent = extractLexIdentifierToken( id );
  LexLocation loc = new LexLocation(currentSourceFile.getFile(),
				    id.getValue(),
				    startPos.line, 
				    startPos.column, 
				    endPos.line, 
				    endPos.column, 
				    startPos.offset, endPos.offset);
  
  c.setLocation(loc); 
  c.setName(lexName);
  c.setDeclarations( (List<PDeclaration>)((yystack.valueAt (4-(4)))) );
  //  c.setDefinitions((List)$4);
  AClassDeclaration res = new AClassDeclaration();
  res.setClassBody( c );
  res.setLocation ( loc );
  res.setIdentifier( classIdent );
  res.setNameScope( NameScope.CLASSNAME );
  yyval = res;
};
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 444 of "src/main/bison/cml.y"  */
    {
      LexLocation processLoc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))));
      AProcessDefinition processDef = (AProcessDefinition)((yystack.valueAt (4-(4))));
      LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (4-(2)))));
      LexLocation location = combineLexLocation(processLoc,processDef.getLocation());
      yyval = new AProcessDeclaration(location, NameScope.GLOBAL, id, processDef);
  };
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 455 of "src/main/bison/cml.y"  */
    { 
    PProcess process = (PProcess)((yystack.valueAt (3-(3))));
    List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (3-(1))));
    LexLocation loc = combineLexLocation(extractFirstLexLocation(decls),
					 process.getLocation());
    yyval = new AProcessDefinition(loc, 
				NameScope.GLOBAL, 
				false, 
				null, 
				decls,
				process); 
};
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 468 of "src/main/bison/cml.y"  */
    {
    PProcess process = (PProcess)((yystack.valueAt (1-(1))));
    yyval = new AProcessDefinition(process.getLocation(), 
				NameScope.GLOBAL, 
				false, 
				null, 
				null,
				process);
};
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 481 of "src/main/bison/cml.y"  */
    {
      LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5)))));
      List<PDeclaration> processDeclarations = (List<PDeclaration>)((yystack.valueAt (5-(2))));
      PAction action = (PAction)((yystack.valueAt (5-(4))));
      yyval = new AStateProcess(location,processDeclarations,action);
  };
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 488 of "src/main/bison/cml.y"  */
    {
      LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
      List<PDeclaration> processDeclarations = null;
      PAction action = (PAction)((yystack.valueAt (4-(3))));
      yyval = new AStateProcess(location,processDeclarations,action);
  };
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 495 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new ASequentialCompositionProcess(combineLexLocation(left.getLocation(),
							      right.getLocation()), 
					   left, 
					   right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 504 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new AExternalChoiceProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 513 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new AInternalChoiceProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 522 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (5-(1))));
    PProcess right = (PProcess)((yystack.valueAt (5-(5))));
    yyval = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left,
					    (SChansetSetExp)((yystack.valueAt (5-(3)))),
					    right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 532 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (7-(1))));
    PProcess right = (PProcess)((yystack.valueAt (7-(7))));
    yyval = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left,
					    (SChansetSetExp)((yystack.valueAt (7-(3)))),
					    (SChansetSetExp)((yystack.valueAt (7-(5)))),
					    right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 543 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new ASynchronousParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left, 
					    right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 552 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new AInterleavingProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 561 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    yyval = new AInterruptProcess(location, 
			      left, 
			      right);
};
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 583 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    yyval = new AUntimedTimeoutProcess(location, 
				   left, 
				   right);
};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 593 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (5-(1))));
    PProcess right = (PProcess)((yystack.valueAt (5-(5))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    yyval = new ATimeoutProcess(location,
			     left,
			     (PExp)((yystack.valueAt (5-(3)))),
			     right);
};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 615 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      exp.getLocation());
    yyval = new AStartDeadlineProcess(location, 
			     left, 
			     exp);
};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 625 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      exp.getLocation());
    yyval = new AEndDeadlineProcess(location, 
			   left, 
			   exp);
};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 639 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),(CmlLexeme)((yystack.valueAt (8-(8))))); 
    List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (8-(2))));
    LexNameToken identifier = extractLexNameToken((CmlLexeme)((yystack.valueAt (8-(4)))));
    yyval = new AInstantiationProcess(location, 
				   decls,
				   identifier,
				   (PExp)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 667 of "src/main/bison/cml.y"  */
    {
    SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
    PProcess process = (PProcess)((yystack.valueAt (2-(1))));
    
    yyval = new AChannelRenamingProcess(combineLexLocation(process.getLocation(),
						       renameExpression.getLocation()), 
				    process, 
				    renameExpression);
};
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 720 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> processParagraphList = 
	  new Vector<PDeclaration>();
      processParagraphList.add((PDeclaration)((yystack.valueAt (1-(1)))));
      yyval = processParagraphList;
};
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 727 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> processParagraphList = (List<PDeclaration>)((yystack.valueAt (2-(1))));

    if (processParagraphList == null) 
	processParagraphList = new Vector<PDeclaration>();
	    
    processParagraphList.add((PDeclaration)((yystack.valueAt (2-(2)))));
    yyval = processParagraphList;
};
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 740 of "src/main/bison/cml.y"  */
    {
     yyval = ((yystack.valueAt (1-(1))));
 };
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 744 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 751 of "src/main/bison/cml.y"  */
    {
    List<AActionDefinition> actionDefinitions = 
	(List<AActionDefinition>)((yystack.valueAt (2-(2))));
    LexLocation declLoc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
					     extractLastLexLocation(actionDefinitions));
    yyval = new AActionDeclaration(declLoc, 
				NameScope.GLOBAL, 
				actionDefinitions);
  };
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 765 of "src/main/bison/cml.y"  */
    {
    List<AActionDefinition> actionDefs = 
	new Vector<AActionDefinition>();
    actionDefs.add((AActionDefinition)((yystack.valueAt (1-(1)))));
    yyval = actionDefs;
};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 772 of "src/main/bison/cml.y"  */
    {
    List<AActionDefinition> actionDefs = 
	(List<AActionDefinition>)((yystack.valueAt (2-(1))));
    actionDefs.add((AActionDefinition)((yystack.valueAt (2-(2)))));
    yyval = actionDefs;
};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 782 of "src/main/bison/cml.y"  */
    {
    Object[] pa = (Object[])((yystack.valueAt (3-(3))));
    List<ASingleTypeDeclaration> declarations = 
	(List<ASingleTypeDeclaration>)pa[0];
    PAction action = (PAction)pa[1];
    LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))),
						 action.getLocation());
    AActionDefinition actionDefinition = new AActionDefinition(defLocation, 
							       NameScope.GLOBAL, 
							       false, 
							       null, 
							       declarations, 
							       action);
    yyval = actionDefinition;
};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 801 of "src/main/bison/cml.y"  */
    {
    yyval = new Object[]{new Vector<ASingleTypeDeclaration>(),((yystack.valueAt (1-(1))))};
};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 805 of "src/main/bison/cml.y"  */
    {
    yyval = new Object[]{((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3))))};
};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 812 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new ASkipAction(location);
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 817 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AStopAction(location);
};
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 822 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AChaosAction(location);
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 827 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new ADivAction(location);
};
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 832 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (4-(2))));
    
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
					      exp.getLocation());
    yyval = new AWaitAction(location,exp);
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 841 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    PAction action = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(id.getLocation(),action.getLocation());
    yyval = new ACommunicationAction(location, id, null,action);
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 848 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (4-(1)))));
    PAction action = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = combineLexLocation(id.getLocation(),action.getLocation());
    List<PCommunicationParameter> communicationParamters = (List<PCommunicationParameter>)((yystack.valueAt (4-(2))));
    yyval = new ACommunicationAction(location, id,
				  communicationParamters,
				  action);
};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 859 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
    yyval = new AGuardedAction(location, (PExp)((yystack.valueAt (4-(2)))), action);
};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 865 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new ASequentialCompositionAction(location, left, right);
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 872 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AExternalChoiceAction(location, left, right);
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 879 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AInternalChoiceAction(location, left, right);
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 886 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AInterruptAction(location, left, right);
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 901 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AUntimedTimeoutAction(location, left, right);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 909 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (6-(1))));
    PAction right = (PAction)((yystack.valueAt (6-(6))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new ATimeoutAction(location, left, right, (PExp)((yystack.valueAt (6-(3)))));
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 924 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),exp.getLocation());
    yyval = new AStartDeadlineAction(location, left, exp);
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 931 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),exp.getLocation());
    yyval = new AEndDeadlineAction(location, left, exp);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 938 of "src/main/bison/cml.y"  */
    {
    SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
    PAction action = (PAction)((yystack.valueAt (2-(1))));
    
    yyval = new AChannelRenamingAction(combineLexLocation(action.getLocation(),
						       renameExpression.getLocation()), 
				    action, 
				    renameExpression);
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 949 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 954 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 973 of "src/main/bison/cml.y"  */
    {
      List<PCommunicationParameter> comParamList = 
	  new Vector<PCommunicationParameter>();
      comParamList.add((PCommunicationParameter)((yystack.valueAt (1-(1)))));
      yyval = comParamList;
  };
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 980 of "src/main/bison/cml.y"  */
    {
    List<PCommunicationParameter> comParamList = 
	(List<PCommunicationParameter>)((yystack.valueAt (2-(1))));

    if (comParamList == null) 
	comParamList = new Vector<PCommunicationParameter>();
    
    comParamList.add((PCommunicationParameter)((yystack.valueAt (2-(2)))));
    yyval = comParamList;
};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 994 of "src/main/bison/cml.y"  */
    {
      PParameter parameter = (PParameter)((yystack.valueAt (2-(2))));
      LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
								   parameter.getLocation());
      yyval = new AReadCommunicationParameter(location, parameter, null);
  };
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1001 of "src/main/bison/cml.y"  */
    {
    PParameter parameter = (PParameter)((yystack.valueAt (4-(2))));
    PExp exp = (PExp)((yystack.valueAt (4-(4))));
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))),
								 exp.getLocation());
    yyval = new AReadCommunicationParameter(location, parameter, exp);
};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1009 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
								 exp.getLocation());
    yyval = new AWriteCommunicationParameter(location, exp);
};
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1027 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AIdentifierParameter(id.getLocation(),id);
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1032 of "src/main/bison/cml.y"  */
    {
    yyval = new ATupleParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
						(CmlLexeme)((yystack.valueAt (4-(4))))), 
			     (List<? extends PParameter>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1038 of "src/main/bison/cml.y"  */
    {
    yyval = new ATupleParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
						(CmlLexeme)((yystack.valueAt (3-(3))))), 
			     null);
};
  break;
    

  case 81:
  if (yyn == 81)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1044 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (4-(1)))));
    
    yyval = new ARecordParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
    						 (CmlLexeme)((yystack.valueAt (4-(4))))), 
    			      name, 
    			      (List<? extends PParameter>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1053 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (3-(1)))));
    
    yyval = new ARecordParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
    						 (CmlLexeme)((yystack.valueAt (3-(3))))), 
    			      name, 
    			      null);
};
  break;
    

  case 83:
  if (yyn == 83)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1065 of "src/main/bison/cml.y"  */
    {
    List<PParameter> parameters = new Vector<PParameter>();
    parameters.add((PParameter)((yystack.valueAt (1-(1)))));
    yyval = parameters;
};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1071 of "src/main/bison/cml.y"  */
    {
    List<PParameter> parameters = (List<PParameter>)((yystack.valueAt (3-(1))));
    parameters.add((PParameter)((yystack.valueAt (3-(3)))));
    yyval = parameters;
};
  break;
    

  case 98:
  if (yyn == 98)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1106 of "src/main/bison/cml.y"  */
    {
    yyval = new ADeclarationInstantiatedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8))))), 
					    (List<? extends ASingleTypeDeclaration>)((yystack.valueAt (8-(2)))), 
					    (PAction)((yystack.valueAt (8-(4)))), 
					    (List<PExp>)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1128 of "src/main/bison/cml.y"  */
    {
     yyval = ((yystack.valueAt (1-(1))));
 };
  break;
    

  case 109:
  if (yyn == 109)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1136 of "src/main/bison/cml.y"  */
    {
    yyval = new AEnumerationRenameChannelExp(null, 
					  extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3))))), 
					  (List<? extends ARenamePair>)((yystack.valueAt (3-(2))))); 
};
  break;
    

  case 110:
  if (yyn == 110)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1145 of "src/main/bison/cml.y"  */
    {
    yyval = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5))))), 
					    (List<? extends ARenamePair>)((yystack.valueAt (5-(2)))), 
					    (List<? extends PMultipleBind>)((yystack.valueAt (5-(4)))), 
					    null);
};
  break;
    

  case 111:
  if (yyn == 111)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1152 of "src/main/bison/cml.y"  */
    {
    yyval = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),(CmlLexeme)((yystack.valueAt (7-(7))))), 
					    (List<? extends ARenamePair>)((yystack.valueAt (7-(2)))), 
					    (List<? extends PMultipleBind>)((yystack.valueAt (7-(4)))), 
					    (PExp)((yystack.valueAt (7-(6)))));
};
  break;
    

  case 112:
  if (yyn == 112)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1162 of "src/main/bison/cml.y"  */
    {
    List<ARenamePair> renamePairs = 
	new Vector<ARenamePair>();
    renamePairs.add((ARenamePair)((yystack.valueAt (1-(1)))));
    yyval = renamePairs;
};
  break;
    

  case 113:
  if (yyn == 113)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1169 of "src/main/bison/cml.y"  */
    {
    List<ARenamePair> renamePairs = (List<ARenamePair>)((yystack.valueAt (3-(1))));
    renamePairs.add((ARenamePair)((yystack.valueAt (3-(3)))));
    yyval = renamePairs;
};
  break;
    

  case 114:
  if (yyn == 114)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1178 of "src/main/bison/cml.y"  */
    {
    yyval = new ARenamePair(false, 
			 (AEventChannelExp)((yystack.valueAt (3-(1)))), 
			 (AEventChannelExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 115:
  if (yyn == 115)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1187 of "src/main/bison/cml.y"  */
    {
    LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    List<? extends PExp> dotExpression = null;
    yyval = new AEventChannelExp(id.getLocation(), 
			      id, 
			      dotExpression);
};
  break;
    

  case 116:
  if (yyn == 116)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1195 of "src/main/bison/cml.y"  */
    {
    LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (2-(1)))));
    List<? extends PExp> dotExpression = (List<? extends PExp>)((yystack.valueAt (2-(2)))) ;
    yyval = new AEventChannelExp(id.getLocation(), 
			      id, 
			      dotExpression);
};
  break;
    

  case 117:
  if (yyn == 117)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1206 of "src/main/bison/cml.y"  */
    {
    List<PExp> expTokens = new Vector<PExp>();
    expTokens.add((PExp)((yystack.valueAt (2-(2)))));
    yyval = expTokens;
};
  break;
    

  case 118:
  if (yyn == 118)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1212 of "src/main/bison/cml.y"  */
    {
    List<PExp> expTokens = (List<PExp>)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    expTokens.add(exp);
    yyval = expTokens;
};
  break;
    

  case 119:
  if (yyn == 119)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1224 of "src/main/bison/cml.y"  */
    {
     List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (2-(2))));
     LexLocation start = decls.get(0).getLocation();
     LexLocation end = decls.get(decls.size()-1).getLocation();
     LexLocation location = combineLexLocation(start,end);

     AChannelDefinition channelDefinition = 
	 new AChannelDefinition(location,null,null,null,decls);
     AChannelDeclaration channelDecl = new AChannelDeclaration(location,
							       NameScope.GLOBAL,
							       channelDefinition);
     yyval = channelDecl;
 };
  break;
    

  case 120:
  if (yyn == 120)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1241 of "src/main/bison/cml.y"  */
    {
      List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
      decls.add((AChannelNameDeclaration)((yystack.valueAt (1-(1)))));
      AChannelDefinition channelDefinition = new AChannelDefinition();
      yyval = decls;
  };
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1248 of "src/main/bison/cml.y"  */
    {
     List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (3-(3))));
     decls.add((AChannelNameDeclaration)((yystack.valueAt (3-(1)))));
     yyval = decls;
 };
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1257 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (1-(1))));

  LexLocation start = ids.get(0).getLocation();
  LexLocation end = ids.get(ids.size()-1).getLocation();
  LexLocation location = combineLexLocation(start,end);

  ASingleTypeDeclaration singleTypeDeclaration = new ASingleTypeDeclaration(location,NameScope.GLOBAL,ids,null);
            
  AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(location,NameScope.GLOBAL,singleTypeDeclaration);
      
  yyval = channelNameDecl;
};
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1271 of "src/main/bison/cml.y"  */
    {
  ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)((yystack.valueAt (1-(1))));

  AChannelNameDeclaration channelNameDecl = 
    new AChannelNameDeclaration(singleTypeDeclaration.getLocation(),NameScope.GLOBAL,singleTypeDeclaration);
      
  yyval = channelNameDecl; 
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1283 of "src/main/bison/cml.y"  */
    {
  List<ASingleTypeDeclaration> decls = new Vector<ASingleTypeDeclaration>();
  decls.add((ASingleTypeDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1289 of "src/main/bison/cml.y"  */
    {
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (3-(3))));
  decls.add((ASingleTypeDeclaration)((yystack.valueAt (3-(1)))));
  yyval = decls;
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1298 of "src/main/bison/cml.y"  */
    {
    List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
    ASingleTypeDeclaration singleTypeDeclaration =
      new ASingleTypeDeclaration(ids.get(0).getLocation(),NameScope.GLOBAL,ids,(PType)((yystack.valueAt (3-(3)))));
    yyval = singleTypeDeclaration;
  };
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1310 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AChansetDeclaration(id.getLocation(), NameScope.GLOBAL, null);
};
  break;
    

  case 128:
  if (yyn == 128)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1315 of "src/main/bison/cml.y"  */
    {
    List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(2))));
    
    yyval = new AChansetDeclaration(combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
						    extractLastLexLocation(defs)), 
				 NameScope.GLOBAL, 
				 defs);
};
  break;
    

  case 129:
  if (yyn == 129)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1327 of "src/main/bison/cml.y"  */
    {
    List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
    defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
    yyval = defs;
};
  break;
    

  case 130:
  if (yyn == 130)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1333 of "src/main/bison/cml.y"  */
    {
    List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(1))));
    defs.add((AChansetDefinition)((yystack.valueAt (2-(2)))));
    yyval = defs;
};
  break;
    

  case 131:
  if (yyn == 131)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1343 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    SChansetSetBase chansetExp = (SChansetSetBase)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(idToken.getLocation(),
					      chansetExp.getLocation());
    yyval = new AChansetDefinition(location, 
				NameScope.GLOBAL, 
				false/*used_*/, 
				null,/*AAccessSpecifierAccessSpecifier access_*/
				idToken, 
				chansetExp);
};
  break;
    

  case 132:
  if (yyn == 132)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1360 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AIdentifierChansetSetExp(idToken.getLocation(),idToken);
};
  break;
    

  case 133:
  if (yyn == 133)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1372 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)((yystack.valueAt (3-(2))));
  yyval = new AEnumChansetSetExp(location,identifiers);
};
  break;
    

  case 134:
  if (yyn == 134)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1402 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5)))));
  LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (5-(2)))));
  List<PExp> dotted_expression = new Vector<PExp>();
  List<PMultipleBind> bindings = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  yyval = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,null);
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1410 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),(CmlLexeme)((yystack.valueAt (6-(6)))));
  LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (6-(2)))));
  List<PExp> dotted_expression = (List<PExp>)((yystack.valueAt (6-(3))));
  List<PMultipleBind> bindings = (List<PMultipleBind>)((yystack.valueAt (6-(5))));
  yyval = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,null);
};
  break;
    

  case 136:
  if (yyn == 136)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1418 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),(CmlLexeme)((yystack.valueAt (7-(7)))));
  LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (7-(2)))));
  List<PExp> dotted_expression = new Vector<PExp>();
  List<PMultipleBind> bindings = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  yyval = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,pred);
};
  break;
    

  case 137:
  if (yyn == 137)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1427 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),(CmlLexeme)((yystack.valueAt (8-(8)))));
  LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (8-(2)))));
  List<PExp> dotted_expression = (List<PExp>)((yystack.valueAt (8-(3))));
  List<PMultipleBind> bindings = (List<PMultipleBind>)((yystack.valueAt (8-(5))));
  PExp pred = (PExp)((yystack.valueAt (8-(7))));
  yyval = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,pred);
};
  break;
    

  case 138:
  if (yyn == 138)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1441 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1448 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> declBlockList = new Vector<PDeclaration>();
    PDeclaration globalDecl = (PDeclaration)((yystack.valueAt (1-(1))));
    if (globalDecl != null) declBlockList.add(globalDecl);
    yyval = declBlockList;
};
  break;
    

  case 140:
  if (yyn == 140)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1456 of "src/main/bison/cml.y"  */
    { 
    List<PDeclaration> declBlockList = (List<PDeclaration>)((yystack.valueAt (2-(1))));
    PDeclaration globalDecl = (PDeclaration)((yystack.valueAt (2-(2))));
    if (declBlockList != null) if (globalDecl != null) declBlockList.add(globalDecl);
    yyval = declBlockList;
};
  break;
    

  case 141:
  if (yyn == 141)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1466 of "src/main/bison/cml.y"  */
    {
  ATypeDeclaration typeDeclaration = (ATypeDeclaration)((yystack.valueAt (1-(1))));
  typeDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = typeDeclaration;
};
  break;
    

  case 142:
  if (yyn == 142)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1472 of "src/main/bison/cml.y"  */
    {
    AValueDeclaration valueGlobalDeclaration = new AValueDeclaration();
    yyval = valueGlobalDeclaration;
};
  break;
    

  case 143:
  if (yyn == 143)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1477 of "src/main/bison/cml.y"  */
    {
    AFunctionDeclaration functionGlobalDeclaration = (AFunctionDeclaration)((yystack.valueAt (1-(1))));
    functionGlobalDeclaration.setNameScope(NameScope.GLOBAL);
    yyval = functionGlobalDeclaration;
};
  break;
    

  case 144:
  if (yyn == 144)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1488 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1495 of "src/main/bison/cml.y"  */
    {
  
  List<PDeclaration> decls = new LinkedList<PDeclaration>();
  PDeclaration decl = (PDeclaration)((yystack.valueAt (1-(1))));
  decls.add(decl);
  yyval = decls;
};
  break;
    

  case 146:
  if (yyn == 146)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1504 of "src/main/bison/cml.y"  */
    { 
  List<PDeclaration> decls = (List<PDeclaration>)((yystack.valueAt (2-(2))));
  PDeclaration decl = (PDeclaration)((yystack.valueAt (2-(1))));
  decls.add(decl);
  yyval = decls;
};
  break;
    

  case 147:
  if (yyn == 147)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1514 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1518 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1522 of "src/main/bison/cml.y"  */
    {
  AFunctionDeclaration functionDeclaration = (AFunctionDeclaration)((yystack.valueAt (1-(1))));
  functionDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = functionDeclaration;
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1528 of "src/main/bison/cml.y"  */
    {
  AOperationDeclaration operationDeclaration = (AOperationDeclaration)((yystack.valueAt (1-(1))));
  operationDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = operationDeclaration;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1534 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 153:
  if (yyn == 153)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1544 of "src/main/bison/cml.y"  */
    { 
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(typesLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(new Vector<ATypeDefinition>());
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1553 of "src/main/bison/cml.y"  */
    {
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (3-(1))));
  CmlLexeme semiLexeme = (CmlLexeme)((yystack.valueAt (3-(3))));
  LexLocation loc = extractLexLocation(typesLexeme,semiLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions((List<ATypeDefinition>)((yystack.valueAt (3-(2)))));
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1563 of "src/main/bison/cml.y"  */
    {
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (2-(1))));
  List<ATypeDefinition> tdefs = (List<ATypeDefinition>)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation(typesLexeme,tdefs.get(tdefs.size()-1).getLocation());
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(tdefs);
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1576 of "src/main/bison/cml.y"  */
    {
    List<ATypeDefinition> list = new Vector<ATypeDefinition>(); 
    list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
    yyval = list;
};
  break;
    

  case 157:
  if (yyn == 157)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1582 of "src/main/bison/cml.y"  */
    {
    List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
    list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
    yyval = list;
};
  break;
    

  case 158:
  if (yyn == 158)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1591 of "src/main/bison/cml.y"  */
    {
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2)))));
    AInvariantInvariant inv = (AInvariantInvariant)((yystack.valueAt (5-(5))));
    LexLocation location = null;
    if (access.getLocation() != null)
	location = combineLexLocation(access.getLocation(),inv.getLocation());
    else
    {
	location = combineLexLocation(name.getLocation(),inv.getLocation());
    }
    
    

    yyval = new ATypeDefinition(location,null /*NameScope nameScope_*/, false, 
			     null/*SClassDefinition classDefinition_*/,access, 
			     (PType)((yystack.valueAt (5-(4)))),null,inv.getInvPattern(),inv.getInvExpression(), 
			     null, true, name); 
    
};
  break;
    

  case 159:
  if (yyn == 159)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1612 of "src/main/bison/cml.y"  */
    { 
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
    LexLocation location = null;
    if (access.getLocation() != null)
	location = combineLexLocation(access.getLocation(),((PType)((yystack.valueAt (4-(4))))).getLocation());
    else
    {
	location = combineLexLocation(name.getLocation(),((PType)((yystack.valueAt (4-(4))))).getLocation());
    }
        
    yyval = new ATypeDefinition(location,null /*NameScope nameScope_*/, false, 
			     null/*SClassDefinition classDefinition_*/,access, 
			     (PType)((yystack.valueAt (4-(4)))), null, null, null, 
			     null, true, name); 
};
  break;
    

  case 160:
  if (yyn == 160)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1629 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
  LexNameToken name = extractLexNameToken( (CmlLexeme)((yystack.valueAt (4-(2)))) );
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (4-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (4-(4))));

  LexLocation loc = combineLexLocation( access.getLocation(),
					extractLexLocation( vdmrec ) );
					

  //
  ARecordInvariantType recType = new ARecordInvariantType( loc, false, null, false, null, name, fields, true );
							   

  ATypeDefinition res = new ATypeDefinition( loc, NameScope.GLOBAL, 
					     false, null, access,
					     recType, null, null, null,
					     null, true, name );

  yyval = res;
};
  break;
    

  case 161:
  if (yyn == 161)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1651 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
  LexNameToken name = extractLexNameToken( (CmlLexeme)((yystack.valueAt (5-(2)))) );
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (5-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (5-(4))));
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.


  LexLocation loc = combineLexLocation( access.getLocation(),
					extractLexLocation( vdmrec ));
					

  //
  ARecordInvariantType recType = new ARecordInvariantType( loc, false, null, false, null, name, fields, true );
							   

  ATypeDefinition res = new ATypeDefinition( loc, NameScope.GLOBAL, 
					     false, null, access,
					     recType, null, null, null,
					     null, true, name );

  yyval = res;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1678 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
    res.setAccess(new APrivateAccess());
    res.setLocation(location);
    yyval = res;
};
  break;
    

  case 163:
  if (yyn == 163)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1686 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
    res.setLocation(location);
    res.setAccess(new AProtectedAccess());
    yyval = res;
};
  break;
    

  case 164:
  if (yyn == 164)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1694 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
    res.setLocation(location);
    res.setAccess(new APublicAccess());
    yyval = res; 
};
  break;
    

  case 165:
  if (yyn == 165)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1702 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(),null,null,location);
};
  break;
    

  case 166:
  if (yyn == 166)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1707 of "src/main/bison/cml.y"  */
    {
    /*Default private*/
  AAccessSpecifierAccessSpecifier a = new AAccessSpecifierAccessSpecifier();
  a.setAccess(new APrivateAccess());
  yyval = a;
};
  break;
    

  case 167:
  if (yyn == 167)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1717 of "src/main/bison/cml.y"  */
    { 
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 168:
  if (yyn == 168)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1721 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 169:
  if (yyn == 169)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1725 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 171:
  if (yyn == 171)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1730 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 172:
  if (yyn == 172)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1734 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 173:
  if (yyn == 173)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1738 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 174:
  if (yyn == 174)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1742 of "src/main/bison/cml.y"  */
    {
  // Get Constituents
  CmlLexeme setof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));

  LexLocation loc = combineLexLocation( extractLexLocation ( setof ),
					type.getLocation() );

  // Build ASetType
  ASetType res = new ASetType( loc, false, null, type, false, false );
  yyval = res;
};
  break;
    

  case 175:
  if (yyn == 175)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1755 of "src/main/bison/cml.y"  */
    {
  CmlLexeme seqof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation( extractLexLocation ( seqof ),
					type.getLocation() );

  // Build ASetType
  ASeqSeqType res = new ASeqSeqType( loc, false, null, type, false );
  yyval = res;
};
  break;
    

  case 176:
  if (yyn == 176)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1766 of "src/main/bison/cml.y"  */
    {
  CmlLexeme seqof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation( extractLexLocation ( seqof ),
					type.getLocation() );

  // Build ASetType
  ASeq1SeqType res = new ASeq1SeqType( loc, false, null, type, false );
  yyval = res;
};
  break;
    

  case 177:
  if (yyn == 177)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1777 of "src/main/bison/cml.y"  */
    {
  CmlLexeme mapof = (CmlLexeme)((yystack.valueAt (4-(1))));
  PType from = (PType)((yystack.valueAt (4-(2))));
  // $3 TO
  PType to   = (PType)((yystack.valueAt (4-(4))));
  
  LexLocation loc = combineLexLocation ( extractLexLocation ( mapof ),
					 to.getLocation() );

  // Build res
  AMapMapType res = new AMapMapType( loc, false, null, from, to, false );
  yyval = res;
};
  break;
    

  case 178:
  if (yyn == 178)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1791 of "src/main/bison/cml.y"  */
    {
  CmlLexeme mapof = (CmlLexeme)((yystack.valueAt (4-(1))));
  PType from = (PType)((yystack.valueAt (4-(2))));
  // $3 TO
  PType to   = (PType)((yystack.valueAt (4-(4))));
  
  LexLocation loc = combineLexLocation ( extractLexLocation ( mapof ),
					 to.getLocation() );


  // Build res
  AMapMapType res = new AMapMapType( loc, false, null, from, to, false );
  yyval = res;
};
  break;
    

  case 179:
  if (yyn == 179)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1806 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 180:
  if (yyn == 180)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1810 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = extractLexNameToken((ASimpleName)((yystack.valueAt (1-(1)))));
    yyval = new AUnresolvedType(lnt.location,false /*resolved*/, null/*defs*/,lnt);
};
  break;
    

  case 181:
  if (yyn == 181)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1815 of "src/main/bison/cml.y"  */
    {
  
};
  break;
    

  case 182:
  if (yyn == 182)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1822 of "src/main/bison/cml.y"  */
    { 
    yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 183:
  if (yyn == 183)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1829 of "src/main/bison/cml.y"  */
    { 
    yyval = new ABooleanBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 184:
  if (yyn == 184)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1833 of "src/main/bison/cml.y"  */
    { 
    yyval = new ANatNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 185:
  if (yyn == 185)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1837 of "src/main/bison/cml.y"  */
    { 
    yyval = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 186:
  if (yyn == 186)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1841 of "src/main/bison/cml.y"  */
    { 
    yyval = new AIntNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 187:
  if (yyn == 187)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1845 of "src/main/bison/cml.y"  */
    { 
    yyval = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 188:
  if (yyn == 188)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1849 of "src/main/bison/cml.y"  */
    { 
     yyval = new ARealNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 189:
  if (yyn == 189)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1853 of "src/main/bison/cml.y"  */
    { 
    yyval = new ACharBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 190:
  if (yyn == 190)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1857 of "src/main/bison/cml.y"  */
    { 
    yyval = new ATokenBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 191:
  if (yyn == 191)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1864 of "src/main/bison/cml.y"  */
    {
    LexQuoteToken value = (LexQuoteToken)((yystack.valueAt (1-(1))));
    yyval = new AQuoteType( value.location, false, null, value );
};
  break;
    

  case 192:
  if (yyn == 192)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1872 of "src/main/bison/cml.y"  */
    {
  yyval = new  AOptionalType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
					     (CmlLexeme)((yystack.valueAt (3-(3))))), 
			  false,/* resolved_*/ 
			  null,/* definitions_*/ 
			  (PType)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 193:
  if (yyn == 193)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1883 of "src/main/bison/cml.y"  */
    {
  // CmlLexeme lp = (CmlLexeme)$1;
  // CmlLexeme rp = (CmlLexeme)$5;
  PType fst = (PType)((yystack.valueAt (3-(1))));
  PType snd = (PType)((yystack.valueAt (3-(3))));

  LexLocation loc = combineLexLocation ( fst.getLocation(),
  					 snd.getLocation() );

  AUnionType utype = new AUnionType(loc, false, false, false );
  List<PType> types = new Vector<PType>();
  types.add(fst);
  types.add(snd);
  utype.setTypes(types);
  yyval = utype;
};
  break;
    

  case 194:
  if (yyn == 194)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1900 of "src/main/bison/cml.y"  */
    {
    AUnionType utype = (AUnionType)((yystack.valueAt (3-(1))));
    utype.getTypes().add((PType)((yystack.valueAt (3-(3)))));
    yyval = utype;
};
  break;
    

  case 195:
  if (yyn == 195)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1909 of "src/main/bison/cml.y"  */
    {
    List<PType> types = new Vector<PType>();
    PType left = (PType)((yystack.valueAt (3-(1))));
    PType right = (PType)((yystack.valueAt (3-(3))));
    types.add(left);
    types.add(right);
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AProductType(location, 
			  false /*resolved_*/, 
			  null/*List<? extends PDefinition> definitions_*/, 
			  types);
};
  break;
    

  case 196:
  if (yyn == 196)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1931 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 197:
  if (yyn == 197)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1935 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1)))); 
};
  break;
    

  case 198:
  if (yyn == 198)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1942 of "src/main/bison/cml.y"  */
    {
    PType domType = (PType)((yystack.valueAt (3-(1))));
    PType rngType = (PType)((yystack.valueAt (3-(3))));
    
    LexLocation loc = combineLexLocation ( domType.getLocation(),
					   rngType.getLocation() ) ;
    
    // [CONSIDER,RWL] The domain type of a function is not a list, 
    // I think the AST is wrong taking a list of types for params
    // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
    List<PType> params = new LinkedList<PType>();
    params.add(domType);
    yyval = new AFunctionType(loc, false, null, true, params, rngType );
};
  break;
    

  case 199:
  if (yyn == 199)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1957 of "src/main/bison/cml.y"  */
    {
    PType domType = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))), 
				  true);
    PType rngType = (PType)((yystack.valueAt (4-(3))));
    
    LexLocation loc = combineLexLocation ( domType.getLocation(),
					   rngType.getLocation() ) ;
    
    // [CONSIDER,RWL] The domain type of a function is not a list, 
    // I think the AST is wrong taking a list of types for params
    // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
    List<PType> params = new LinkedList<PType>();
    params.add(domType);
    yyval = new AFunctionType(loc, false, null, true, params, rngType );
};
  break;
    

  case 200:
  if (yyn == 200)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1976 of "src/main/bison/cml.y"  */
    {
    PType domType = (PType)((yystack.valueAt (3-(1))));
    PType rngType = (PType)((yystack.valueAt (3-(3))));
    
    LexLocation loc = combineLexLocation ( domType.getLocation(),
					   rngType.getLocation() ) ;
    
    // [CONSIDER,RWL] The domain type of a function is not a list, 
    // I think the AST is wrong taking a list of types for params
    // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
    List<PType> params = new LinkedList<PType>();
    params.add(domType);
    yyval = new AFunctionType(loc, false, null, false, params, rngType );
};
  break;
    

  case 201:
  if (yyn == 201)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1991 of "src/main/bison/cml.y"  */
    {
    PType domType = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))), 
				  true);
    PType rngType = (PType)((yystack.valueAt (4-(3))));
    
    LexLocation loc = combineLexLocation ( domType.getLocation(),
					   rngType.getLocation() ) ;
    
    // [CONSIDER,RWL] The domain type of a function is not a list, 
    // I think the AST is wrong taking a list of types for params
    // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
    List<PType> params = new LinkedList<PType>();
    params.add(domType);
    yyval = new AFunctionType(loc, false, null, false, params, rngType );
};
  break;
    

  case 202:
  if (yyn == 202)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2030 of "src/main/bison/cml.y"  */
    {
    CmlLexeme id = (CmlLexeme)((yystack.valueAt (1-(1))));
    LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    String value = id.getValue();
    yyval = new LexQuoteToken(value.substring(1,value.length()-2),loc);
};
  break;
    

  case 203:
  if (yyn == 203)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2040 of "src/main/bison/cml.y"  */
    {
    List<AFieldField> res = new LinkedList<AFieldField>();
    res.add ( (AFieldField) ((yystack.valueAt (1-(1)))) );
    yyval = res;
  };
  break;
    

  case 204:
  if (yyn == 204)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2046 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> tail = (List<AFieldField>)((yystack.valueAt (2-(1))));
  tail.add( (AFieldField) ((yystack.valueAt (2-(2)))) );
  yyval = tail;
};
  break;
    

  case 205:
  if (yyn == 205)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2055 of "src/main/bison/cml.y"  */
    {
    yyval = new AFieldField( null, null, null, (PType) ((yystack.valueAt (1-(1)))), null );
  };
  break;
    

  case 206:
  if (yyn == 206)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2059 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken( (CmlLexeme) ((yystack.valueAt (3-(1)))) );
  PType type = (PType) ((yystack.valueAt (3-(3))));

  yyval = new AFieldField( null, name, null, type, null );
};
  break;
    

  case 207:
  if (yyn == 207)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2066 of "src/main/bison/cml.y"  */
    {
  throw new RuntimeException("No way");
};
  break;
    

  case 208:
  if (yyn == 208)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2073 of "src/main/bison/cml.y"  */
    {
   CmlLexeme vdmInvLexeme = (CmlLexeme)((yystack.valueAt (4-(1))));
   PExp exp = (PExp)((yystack.valueAt (4-(4))));
   LexLocation loc = extractLexLocation(vdmInvLexeme,exp.getLocation());
   yyval = new AInvariantInvariant(loc,(PPattern)((yystack.valueAt (4-(2)))),exp);
 };
  break;
    

  case 209:
  if (yyn == 209)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2085 of "src/main/bison/cml.y"  */
    {
    List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
    AValueDeclaration valueDecl = new AValueDeclaration();
    valueDecl.setDefinitions( defs );
    yyval = valueDecl;
  };
  break;
    

  case 210:
  if (yyn == 210)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2128 of "src/main/bison/cml.y"  */
    {
   // Build resulting list 
   List<PDefinition> defs = new LinkedList<PDefinition>();
   defs.add((PDefinition)((yystack.valueAt (1-(1)))));
   yyval = defs;
};
  break;
    

  case 211:
  if (yyn == 211)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2135 of "src/main/bison/cml.y"  */
    {
   // This case allows tailing SEMI in value def. list, comment out to
   // enforce no tailing SEMI.

   // Build resulting list 
   List<PDefinition> defs = new LinkedList<PDefinition>();
   defs.add((PDefinition)((yystack.valueAt (2-(1)))));
   yyval = defs;
 };
  break;
    

  case 212:
  if (yyn == 212)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2145 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  PDefinition def = (PDefinition)((yystack.valueAt (3-(1))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(3))));
  
  // add hd to tl
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 213:
  if (yyn == 213)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2157 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
  PDefinition def = (PDefinition)((yystack.valueAt (2-(2))));
  
  // set qualifier
  def.setAccess(access);
  yyval = def;
};
  break;
    

  case 214:
  if (yyn == 214)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2170 of "src/main/bison/cml.y"  */
    {
   // Get constituent elements
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (5-(1))));
  // $2 COLON
  PType type = (PType)((yystack.valueAt (5-(3))));
  // $4 EQUALS
  PExp expression = (PExp)((yystack.valueAt (5-(5))));
  
  // Make pattern
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (5-(1))));
  LexNameToken lnt = extractLexNameToken(lexeme);
  //   AIdentifierPattern idp = new AIdentifierPattern(lnt.location,null,false,lnt);
  AIdentifierPattern idp = new AIdentifierPattern();
  idp.setLocation(lnt.location);
  // Build the resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( idp.getLocation(), 
				       expression.getLocation() ) );
  yyval = vdef;
};
  break;
    

  case 215:
  if (yyn == 215)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2196 of "src/main/bison/cml.y"  */
    {
  // Get constituent elements
  PPattern pattern = (PPattern)((yystack.valueAt (5-(1))));
  // $2 COLON
  PType type = (PType)((yystack.valueAt (5-(3))));
  // $4 EQUALS
  PExp expression = (PExp)((yystack.valueAt (5-(5))));

  // Build resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( pattern.getLocation(), 
				       expression.getLocation() ) );
  yyval = vdef;
};
  break;
    

  case 216:
  if (yyn == 216)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2216 of "src/main/bison/cml.y"  */
    {
   // Get constituent elements
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (3-(1))));
  // $4 EQUALS
  PExp expression = (PExp)((yystack.valueAt (3-(3))));
  
  // Make pattern
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (3-(1))));
  LexNameToken lnt = extractLexNameToken(lexeme);
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,null,false,lnt);
  
  // Build the resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( idp.getLocation(), 
				       expression.getLocation() ) );
  yyval = vdef;
};
  break;
    

  case 217:
  if (yyn == 217)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2239 of "src/main/bison/cml.y"  */
    {
  // Get constituent elements
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  // $2 EQUALS
  PExp expression = (PExp)((yystack.valueAt (3-(3))));

  // Build resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( pattern.getLocation(), 
				       expression.getLocation() ) );
  yyval = vdef;
};
  break;
    

  case 218:
  if (yyn == 218)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2263 of "src/main/bison/cml.y"  */
    {
  CmlLexeme functionsLexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  AFunctionDeclaration fdecl = new AFunctionDeclaration();
  fdecl.setLocation(extractLexLocation(functionsLexeme));
  yyval = fdecl;
};
  break;
    

  case 219:
  if (yyn == 219)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2270 of "src/main/bison/cml.y"  */
    {
  CmlLexeme functionsLexeme = (CmlLexeme)((yystack.valueAt (2-(1))));
  List<SFunctionDefinition> functionDefs = (List<SFunctionDefinition>) ((yystack.valueAt (2-(2))));
  AFunctionDeclaration fdecl = new AFunctionDeclaration();
  fdecl.setLocation(extractLexLocation(functionsLexeme,
				       functionDefs.get(functionDefs.size()-1).getLocation()));
  fdecl.setFunctionDefinitions(functionDefs);
  yyval = fdecl;
};
  break;
    

  case 220:
  if (yyn == 220)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2283 of "src/main/bison/cml.y"  */
    {
    List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
    functionList.add((SFunctionDefinition)((yystack.valueAt (1-(1)))));
    yyval = functionList;
};
  break;
    

  case 221:
  if (yyn == 221)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2289 of "src/main/bison/cml.y"  */
    {
    List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
    functionList.add((SFunctionDefinition)((yystack.valueAt (2-(1)))));
    yyval = functionList;
};
  break;
    

  case 222:
  if (yyn == 222)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2295 of "src/main/bison/cml.y"  */
    {
    List<SFunctionDefinition> functionList = (List<SFunctionDefinition>)((yystack.valueAt (3-(3))));
    functionList.add((SFunctionDefinition)((yystack.valueAt (3-(1)))));
    yyval = functionList;
};
  break;
    

  case 223:
  if (yyn == 223)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2304 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 224:
  if (yyn == 224)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2308 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 225:
  if (yyn == 225)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2315 of "src/main/bison/cml.y"  */
    {
  
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (6-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));

  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)((yystack.valueAt (6-(3))));
  List<APatternTypePair> result = (List<APatternTypePair>)((yystack.valueAt (6-(4))));  
  PExp preExp = (PExp)((yystack.valueAt (6-(5))));
  PExp postExp = (PExp)((yystack.valueAt (6-(6))));

  LexLocation location = null;
  if (access.getLocation() != null){
      location = combineLexLocation(access.getLocation(),postExp.getLocation());
  }
  else{
      location = combineLexLocation(name.getLocation(),postExp.getLocation());
  }
  AImplicitFunctionFunctionDefinition impFunc = 
      new AImplicitFunctionFunctionDefinition(location, null/*nameScope */, false/*used_*/, 
					      access, null /* typeParams*/, 
					      paramPatterns, result, preExp, postExp);
  impFunc.setName(name);
  yyval = impFunc;
};
  break;
    

  case 226:
  if (yyn == 226)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2343 of "src/main/bison/cml.y"  */
    {
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
    AExplicitFunctionFunctionDefinition f = (AExplicitFunctionFunctionDefinition)((yystack.valueAt (2-(2))));
    f.setAccess(access);
    yyval = f;
  };
  break;
    

  case 227:
  if (yyn == 227)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2353 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = extractLexNameToken( (CmlLexeme) ((yystack.valueAt (10-(1)))) );
    LexLocation loc = extractLexLocation ( (CmlLexeme) ((yystack.valueAt (10-(1)))) );
    AFunctionType ftype = (AFunctionType)((yystack.valueAt (10-(3))));
    AExplicitFunctionFunctionDefinition res = new AExplicitFunctionFunctionDefinition();
    res.setName(name);
    res.setLocation(loc);
    res.setType(ftype);

/* loc,  */
    						 /* name,  */
    						 /* NameScope.GLOBAL,  */
    						 /* false /\* used_*\/,  */
    						 /* null /\*declaration_*\/,  */
    						 /* null/\* access_*\/,  */
    						 /* null/\*List<? extends LexNameToken> typeParams_*\/,  */
    						 /* (Collection<? extends List<PPattern>>)$5,  */
    						 /* ftype,  */
    						 /* (PExp)$7 /\*body_*\/,  */
    						 /* (PExp)$8 /\*precondition_*\/,  */
    						 /* (PExp)$9 /\*postcondition_*\/,  */
    						 /* (LexNameToken)$10 /\*measure_*\/,  */
    						 /* null/\*AExplicitFunctionFunctionDefinition predef_*\/,  */
    						 /* null/\*AExplicitFunctionFunctionDefinition postdef_*\/,  */
    						 /* null/\*PDefinition measureDef_*\/,  */
    						 /* null/\*List<? extends PDefinition> paramDefinitionList_*\/,  */
    						 /* false /\*Boolean recursive_*\/,  */
    						 /* false /\*isUndefined_*\/,  */
    						 /* null/\*measureLexical_*\/,  */
    						 /* ftype.getResult(),  */
    						 /* null /\* actualResult_*\/,  */
    						 /* false /\*isTypeInvariant_*\/,  */
    						 /* false /\*isCurried_*\/,  */
    						 /* false /\*typeInvariant_*\/); */
    yyval = res;
  };
  break;
    

  case 228:
  if (yyn == 228)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2395 of "src/main/bison/cml.y"  */
    {
    List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
    yyval = patternListList;
};
  break;
    

  case 229:
  if (yyn == 229)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2400 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(2))));
    List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
    patternListList.add(patternList);
    yyval = patternListList;
};
  break;
    

  case 230:
  if (yyn == 230)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2407 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (4-(3))));
    List<List<PPattern>> patternListList = (List<List<PPattern>>)((yystack.valueAt (4-(1))));
    patternListList.add(patternList);
    yyval = patternListList;
};
  break;
    

  case 231:
  if (yyn == 231)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2417 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 232:
  if (yyn == 232)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2421 of "src/main/bison/cml.y"  */
    {
    yyval = new ASubclassResponsibilityExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 233:
  if (yyn == 233)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2425 of "src/main/bison/cml.y"  */
    {
    yyval = new ANotYetSpecifiedExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 234:
  if (yyn == 234)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2432 of "src/main/bison/cml.y"  */
    {
    yyval = new Vector<APatternListTypePair>();
};
  break;
    

  case 235:
  if (yyn == 235)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2436 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 236:
  if (yyn == 236)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2443 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(1))));
    List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
    pltpl.add(new APatternListTypePair(false /*resolved*/, 
				       patternList, 
				       (PType)((yystack.valueAt (3-(3))))));
    yyval = pltpl;
};
  break;
    

  case 237:
  if (yyn == 237)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2452 of "src/main/bison/cml.y"  */
    {
    List<APatternListTypePair> pltpl = (List<APatternListTypePair>)((yystack.valueAt (5-(1))));
    List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (5-(3))));
    pltpl.add(new APatternListTypePair(false /*resolved*/, 
				       patternList, 
				       (PType)((yystack.valueAt (5-(5))))));
    yyval = pltpl;
};
  break;
    

  case 238:
  if (yyn == 238)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2464 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 239:
  if (yyn == 239)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2468 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 240:
  if (yyn == 240)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2475 of "src/main/bison/cml.y"  */
    {
    AIdentifierTypePair typePair = 
	new AIdentifierTypePair(null /*resolved*/, 
				extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1))))), 
				(PType)((yystack.valueAt (3-(3))))
				);
    List<AIdentifierTypePair> typePairs = new Vector<AIdentifierTypePair>();
    typePairs.add(typePair);
    yyval = typePairs;
};
  break;
    

  case 241:
  if (yyn == 241)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2486 of "src/main/bison/cml.y"  */
    {
    AIdentifierTypePair typePair = 
	new AIdentifierTypePair(null /*resolved*/, 
				extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (5-(3))))), 
				(PType)((yystack.valueAt (5-(5))))
				);
    List<AIdentifierTypePair> typePairs = (List<AIdentifierTypePair>)((yystack.valueAt (5-(1))));
    typePairs.add(typePair);
    yyval = typePairs;
};
  break;
    

  case 242:
  if (yyn == 242)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2500 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 243:
  if (yyn == 243)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2504 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 244:
  if (yyn == 244)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2511 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 245:
  if (yyn == 245)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2518 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 246:
  if (yyn == 246)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2522 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 247:
  if (yyn == 247)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2529 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 248:
  if (yyn == 248)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2536 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 250:
  if (yyn == 250)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2546 of "src/main/bison/cml.y"  */
    {
      List<? extends SOperationDefinition> opDefinitions = 
	  (List<? extends SOperationDefinition>)((yystack.valueAt (2-(2))));
      LexLocation lastInListLoc = 
	  opDefinitions.get(opDefinitions.size()-1).getLocation();
      LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						lastInListLoc);
      yyval = new AOperationDeclaration(location, 
				     NameScope.GLOBAL,
				     opDefinitions);
  };
  break;
    

  case 251:
  if (yyn == 251)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2558 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AOperationDeclaration(location, 
				   NameScope.GLOBAL,
				   null);
};
  break;
    

  case 252:
  if (yyn == 252)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2568 of "src/main/bison/cml.y"  */
    {
    List<SOperationDefinition> opDefinitions = 
	new Vector<SOperationDefinition>();
    opDefinitions.add((SOperationDefinition)((yystack.valueAt (1-(1)))));
    yyval = opDefinitions;
};
  break;
    

  case 253:
  if (yyn == 253)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2575 of "src/main/bison/cml.y"  */
    {
    List<SOperationDefinition> opDefinitions = 
	  (List<SOperationDefinition>)((yystack.valueAt (3-(1))));
    opDefinitions.add((SOperationDefinition)((yystack.valueAt (3-(3)))));
    yyval = opDefinitions;
};
  break;
    

  case 254:
  if (yyn == 254)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2587 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 255:
  if (yyn == 255)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2591 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 256:
  if (yyn == 256)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2598 of "src/main/bison/cml.y"  */
    {
   LexLocation loc = extractLexLocation ( (CmlLexeme)((yystack.valueAt (10-(2)))) );
   AExplicitOperationOperationDefinition res = new AExplicitOperationOperationDefinition();
   res.setLocation( loc );
   yyval = res;
 };
  break;
    

  case 257:
  if (yyn == 257)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2608 of "src/main/bison/cml.y"  */
    {
    AAccessSpecifierAccessSpecifier access = 
	(AAccessSpecifierAccessSpecifier)((yystack.valueAt (7-(1))));
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (7-(2)))));
    List<? extends APatternListTypePair> parameterPatterns = 
	(List<? extends APatternListTypePair>)((yystack.valueAt (7-(3)))); 
    List<? extends AIdentifierTypePair> result = 
	(List<? extends AIdentifierTypePair>)((yystack.valueAt (7-(4))));
    List<? extends AExternalClause> externals = 
	(List<? extends AExternalClause>)((yystack.valueAt (7-(5))));
    PExp precondition = (PExp)((yystack.valueAt (7-(6))));
    PExp postcondition = (PExp)((yystack.valueAt (7-(7))));
    LexLocation location = null;
    if (access != null)
	location = combineLexLocation(name.location,
				      postcondition.getLocation());
    else
	location = combineLexLocation(access.getLocation(),
				      postcondition.getLocation());
					      
    AImplicitOperationOperationDefinition ifunc = 
	new AImplicitOperationOperationDefinition(location, 
						  NameScope.GLOBAL, 
						  null /*used*/, 
						  access, 
						  parameterPatterns, 
						  result, 
						  externals, 
						  precondition, 
						  postcondition, 
						  null/*errors*/, 
						  null/*isConstructor_*/);
    yyval = ifunc;
};
  break;
    

  case 265:
  if (yyn == 265)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2656 of "src/main/bison/cml.y"  */
    {
    yyval = new ASubclassResponsibilityAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 266:
  if (yyn == 266)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2660 of "src/main/bison/cml.y"  */
    {
    yyval = new ANotYetSpecifiedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), 
				    null, 
				    null);
};
  break;
    

  case 267:
  if (yyn == 267)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2669 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 268:
  if (yyn == 268)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2673 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 269:
  if (yyn == 269)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2680 of "src/main/bison/cml.y"  */
    {
      yyval = ((yystack.valueAt (2-(2))));
  };
  break;
    

  case 270:
  if (yyn == 270)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2687 of "src/main/bison/cml.y"  */
    {
      List<AExternalClause> infoList = 
	  new Vector<AExternalClause>();
      infoList.add((AExternalClause)((yystack.valueAt (1-(1)))));
      yyval = infoList;
  };
  break;
    

  case 271:
  if (yyn == 271)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2694 of "src/main/bison/cml.y"  */
    {
    List<AExternalClause> infoList = (List<AExternalClause>)((yystack.valueAt (2-(1))));
    infoList.add((AExternalClause)((yystack.valueAt (2-(2)))));
    yyval = infoList;
};
  break;
    

  case 272:
  if (yyn == 272)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2703 of "src/main/bison/cml.y"  */
    {
  yyval = new AExternalClause((LexToken)((yystack.valueAt (2-(1)))), (List<? extends LexNameToken>)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 273:
  if (yyn == 273)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2707 of "src/main/bison/cml.y"  */
    {
  yyval = new AExternalClause((LexToken)((yystack.valueAt (4-(1)))), (List<? extends LexNameToken>)((yystack.valueAt (4-(2)))), (PType)((yystack.valueAt (4-(4)))));
};
  break;
    

  case 274:
  if (yyn == 274)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2714 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.READ); // TODO why are we using VDMToken?
};
  break;
    

  case 275:
  if (yyn == 275)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2718 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.WRITE); // TODO why are we using VDMToken?
};
  break;
    

  case 276:
  if (yyn == 276)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2746 of "src/main/bison/cml.y"  */
    {
      
      // LexLocation lastInListLoc = 
      AStateDefinition stateDef = (AStateDefinition)((yystack.valueAt (2-(2))));
      // LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$1),
      // 					   stateDef.getLocation());
      yyval  = new AStateDeclaration(null,
				  NameScope.GLOBAL,
				  stateDef);
  };
  break;
    

  case 277:
  if (yyn == 277)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2757 of "src/main/bison/cml.y"  */
    {
      yyval  = new AStateDeclaration(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),NameScope.GLOBAL,null);
  };
  break;
    

  case 278:
  if (yyn == 278)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2764 of "src/main/bison/cml.y"  */
    {
     AStateDefinition stateDef = new AStateDefinition();
     List<PDefinition> defs = new Vector<PDefinition>();
     defs.add((PDefinition)((yystack.valueAt (1-(1)))));
     stateDef.setStateDefs(defs);
     yyval = stateDef;
 };
  break;
    

  case 279:
  if (yyn == 279)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2772 of "src/main/bison/cml.y"  */
    {
     AStateDefinition stateDef = new AStateDefinition();
     List<PDefinition> defs = new Vector<PDefinition>();
     defs.add((PDefinition)((yystack.valueAt (2-(1)))));
     stateDef.setStateDefs(defs);
     yyval = stateDef;
 };
  break;
    

  case 280:
  if (yyn == 280)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2780 of "src/main/bison/cml.y"  */
    {
    AStateDefinition stateDef = (AStateDefinition)((yystack.valueAt (3-(3))));
    stateDef.getStateDefs().add((PDefinition)((yystack.valueAt (3-(1)))));
    yyval = stateDef;
};
  break;
    

  case 281:
  if (yyn == 281)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2789 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 282:
  if (yyn == 282)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2793 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 283:
  if (yyn == 283)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2800 of "src/main/bison/cml.y"  */
    {
     //  if (42 > 2) throw new RuntimeException("In expression");
     PExp exp = (PExp) ((yystack.valueAt (2-(2))));
     LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),exp.getLocation());
     yyval = new AClassInvariantDefinition(location, 
					NameScope.GLOBAL, 
					true, 
					null/*AAccessSpecifierAccessSpecifier access_*/,
					exp);
 };
  break;
    

  case 284:
  if (yyn == 284)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2816 of "src/main/bison/cml.y"  */
    {
    List<PExp> exps = new Vector<PExp>();
    exps.add((PExp)((yystack.valueAt (1-(1)))));
    yyval = exps;    
};
  break;
    

  case 285:
  if (yyn == 285)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2822 of "src/main/bison/cml.y"  */
    {
    List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(1))));
    exps.add((PExp)((yystack.valueAt (3-(3)))));
    yyval = exps;    
};
  break;
    

  case 286:
  if (yyn == 286)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2842 of "src/main/bison/cml.y"  */
    {
  // Get a whole STRING from the lexer  
  CmlLexeme s = (CmlLexeme)((yystack.valueAt (1-(1))));

  LexLocation sl = extractLexLocation ( s );
  
  // extract the string and convert it to a char array
  String str = s.getValue();
  char[] chrs = str.toCharArray();

  // build a list of ACharLiteralSymbolicLiteralExp from the lexer String
  List<PExp> members = new LinkedList<PExp>();
  for(int i = 0; i < chrs.length;i++)
    {
      LexLocation cl = new LexLocation(currentSourceFile.getFile(), "Default",
				       sl.startLine, sl.startPos + i,
				       sl.startLine, sl.startPos + (i + 1),0,0);
      members.add(new ACharLiteralSymbolicLiteralExp(cl, new LexCharacterToken( chrs[i], cl )) ); 
    }

  // Build the ASeqEnumSeqExp as usual
  ASeqEnumSeqExp res = new ASeqEnumSeqExp( sl, members );
  yyval = res;
};
  break;
    

  case 287:
  if (yyn == 287)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2867 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
      yyval = new ABracketedExp(loc,(PExp)((yystack.valueAt (3-(2)))));
  };
  break;
    

  case 288:
  if (yyn == 288)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2872 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> l = (List<PDefinition>)((yystack.valueAt (4-(2))));
  PExp e = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = extractLexLocation( (CmlLexeme) ((yystack.valueAt (4-(1)))), e.getLocation());
  yyval = new ALetDefExp( loc, l, e );
};
  break;
    

  case 289:
  if (yyn == 289)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2879 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 290:
  if (yyn == 290)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2883 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 291:
  if (yyn == 291)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2887 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 292:
  if (yyn == 292)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2891 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 293:
  if (yyn == 293)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2896 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  CmlLexeme forall = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  CmlLexeme amp = ( CmlLexeme)((yystack.valueAt (4-(3))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(forall), exp.getLocation());
  // Build forall expression
  AForAllExp forallexp = new AForAllExp(loc, binds, exp);
  yyval = forallexp;    
};
  break;
    

  case 294:
  if (yyn == 294)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2908 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  // CmlLexeme amp = (CmlLexeme)$3; AMP $3
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  // Build exists expression
  AExistsExp existsExp = new AExistsExp( loc, binds, exp );
  yyval = existsExp;
};
  break;
    

  case 295:
  if (yyn == 295)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2920 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  // CmlLexeme amp = (CmlLexeme)$3; AMP $3
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  // Build exists expression
  AExists1Exp existsExp = new AExists1Exp( loc, bind, exp, null );
  yyval = existsExp;
};
  break;
    

  case 296:
  if (yyn == 296)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2933 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lc = (CmlLexeme)((yystack.valueAt (2-(1))));
  CmlLexeme rc = (CmlLexeme)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation ( extractLexLocation ( lc ),
					 extractLexLocation ( rc ) );
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation( loc );
  yyval = res;
};
  break;
    

  case 297:
  if (yyn == 297)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2943 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
    List<PExp> members = (List<PExp>)((yystack.valueAt (3-(2))));
    yyval = new ASetEnumSetExp(location,members);
};
  break;
    

  case 298:
  if (yyn == 298)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2950 of "src/main/bison/cml.y"  */
    {
  // Get Constituents
  CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (5-(1))));
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  // $3 BAR
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (5-(5))));
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly) );
  // Build result
  ASetCompSetExp setComp = new ASetCompSetExp( loc, exp, binds, null );
  yyval = setComp;
};
  break;
    

  case 299:
  if (yyn == 299)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2963 of "src/main/bison/cml.y"  */
    {
    // Get Constituents
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (7-(1))));
    PExp exp = (PExp)((yystack.valueAt (7-(2))));
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
    // $5 AMP
    PExp pred = (PExp)((yystack.valueAt (7-(6))));
    CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (7-(7))));
    LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
    // Build result
    ASetCompSetExp setComp = new ASetCompSetExp( loc, exp, binds, pred );
    yyval = setComp;
};
  break;
    

  case 300:
  if (yyn == 300)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2979 of "src/main/bison/cml.y"  */
    {
    // Get constituents
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (5-(1))));
    PExp start = (PExp)((yystack.valueAt (5-(2))));
    PExp end = (PExp)((yystack.valueAt (5-(4))));
    CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (5-(5))));
    LexLocation loc = combineLexLocation(extractLexLocation( lcurly ), extractLexLocation( rcurly ));
    // Build ASetRangeSetExpr
    ASetRangeSetExp res = new ASetRangeSetExp( loc, start, end );
    yyval = res;
  };
  break;
    

  case 301:
  if (yyn == 301)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2992 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (2-(1))));
    CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (2-(2))));
    List<PExp> exps = new LinkedList<PExp>();
    LexLocation loc = combineLexLocation( extractLexLocation( lsqr ),
					  extractLexLocation( rsqr ) );
    
    ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
    yyval = exp;
  };
  break;
    

  case 302:
  if (yyn == 302)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3003 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (3-(1))));
    List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(2))));
    CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (3-(3))));
    
    LexLocation loc = combineLexLocation( extractLexLocation( lsqr ),
					  extractLexLocation( rsqr ) );
    
    ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
    yyval = exp;
};
  break;
    

  case 303:
  if (yyn == 303)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3016 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (5-(1))));
    PExp exp = (PExp)((yystack.valueAt (5-(2))));
    // BAR $3
    ASetBind binds = (ASetBind)((yystack.valueAt (5-(4))));
    CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (5-(5))));
    LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
    // Build response
    ASeqCompSeqExp res = new ASeqCompSeqExp( loc, exp, binds, null );
    yyval = res;
  };
  break;
    

  case 304:
  if (yyn == 304)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3028 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (7-(1))));
    PExp exp = (PExp)((yystack.valueAt (7-(2))));
    // BAR $3
    ASetBind binds = (ASetBind)((yystack.valueAt (7-(4))));
    // AMP $5
    PExp pred = (PExp)((yystack.valueAt (7-(6))));
    CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (7-(7))));
    LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
    // Build response
    ASeqCompSeqExp res = new ASeqCompSeqExp( loc, exp, binds, pred );
    yyval = res;
};
  break;
    

  case 305:
  if (yyn == 305)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3043 of "src/main/bison/cml.y"  */
    {
    PExp seq = (PExp)((yystack.valueAt (6-(1))));
    PExp from = (PExp)((yystack.valueAt (6-(3))));
    PExp to   = (PExp)((yystack.valueAt (6-(5))));
    CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (6-(6))));
    LexLocation loc = combineLexLocation(seq.getLocation(), extractLexLocation(rparen));
    // Build result
    ASubseqExp res = new ASubseqExp( loc, seq, from , to );
    yyval = res;
  };
  break;
    

  case 306:
  if (yyn == 306)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3055 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (3-(1))));
    CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (3-(2))));
    LexLocation loc = combineLexLocation( extractLexLocation ( lcurly ),
					  extractLexLocation ( rcurly ) );
    AMapEnumMapExp res = new AMapEnumMapExp( loc, new LinkedList<AMapletExp>() );
    yyval = res;
  };
  break;
    

  case 307:
  if (yyn == 307)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3064 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (3-(1))));
    List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(2))));
    CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (3-(3))));
    LexLocation loc = combineLexLocation( extractLexLocation ( lcurly ),
					  extractLexLocation ( rcurly ) );
    AMapEnumMapExp res = new AMapEnumMapExp( loc, maplets );
    yyval = res;
};
  break;
    

  case 308:
  if (yyn == 308)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3075 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lcurl = (CmlLexeme)((yystack.valueAt (5-(1))));
    AMapletExp maplet = (AMapletExp)((yystack.valueAt (5-(2))));
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
    CmlLexeme rcurl = (CmlLexeme)((yystack.valueAt (5-(5))));
    LexLocation loc = combineLexLocation( extractLexLocation ( lcurl ),
					  extractLexLocation ( rcurl ) );
    AMapCompMapExp res = new AMapCompMapExp( loc, maplet, binds, null);
    yyval = res;
  };
  break;
    

  case 309:
  if (yyn == 309)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3087 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lcurl = (CmlLexeme)((yystack.valueAt (7-(1))));
    AMapletExp maplet = (AMapletExp)((yystack.valueAt (7-(2))));
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
    // $5 AMP
    PExp pred = (PExp)((yystack.valueAt (7-(6))));
    CmlLexeme rcurl = (CmlLexeme)((yystack.valueAt (7-(7))));
    LexLocation loc = combineLexLocation( extractLexLocation ( lcurl ),
					  extractLexLocation ( rcurl ) );
    AMapCompMapExp res = new AMapCompMapExp( loc, maplet, binds, pred);
    yyval = res;
};
  break;
    

  case 310:
  if (yyn == 310)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3101 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 311:
  if (yyn == 311)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3105 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 312:
  if (yyn == 312)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3110 of "src/main/bison/cml.y"  */
    {
  PExp root = (PExp)((yystack.valueAt (3-(1))));
  List<? extends PExp> args = null;      
  LexLocation location = combineLexLocation(root.getLocation(), extractLexLocation((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = new AApplyExp(location, root, args);
};
  break;
    

  case 313:
  if (yyn == 313)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3117 of "src/main/bison/cml.y"  */
    {
  PExp root = (PExp)((yystack.valueAt (4-(1))));
  List<? extends PExp> args = (List<? extends PExp>)((yystack.valueAt (4-(3))));
  LexLocation location = combineLexLocation(root.getLocation(), extractLexLocation((CmlLexeme)((yystack.valueAt (4-(4))))));
  yyval = new AApplyExp(location, root, args);
};
  break;
    

  case 314:
  if (yyn == 314)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3127 of "src/main/bison/cml.y"  */
    {
  PExp tup = (PExp)((yystack.valueAt (3-(1))));
  CmlLexeme lexNum = (CmlLexeme)((yystack.valueAt (3-(3))));
  int num = Integer.parseInt(lexNum.getValue());
  LexLocation loc = combineLexLocation(tup.getLocation(), extractLexLocation(lexNum));
  ATupleSelectExp res = new ATupleSelectExp(loc, tup, num);
  yyval = res;
};
  break;
    

  case 315:
  if (yyn == 315)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3136 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 316:
  if (yyn == 316)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3140 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 317:
  if (yyn == 317)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3144 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 318:
  if (yyn == 318)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3148 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 319:
  if (yyn == 319)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3152 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 320:
  if (yyn == 320)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3156 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (6-(1))));
};
  break;
    

  case 321:
  if (yyn == 321)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3160 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
  /* was IDENTIFIER */
  /* LexNameToken lnt = extractLexNameToken((CmlLexeme)$1); */
  /* $$ = new ANameExp(lnt.location,lnt); */
};
  break;
    

  case 322:
  if (yyn == 322)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3167 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = extractLexNameToken((CmlLexeme)((yystack.valueAt (2-(1))))).getOldName();
    yyval = new ANameExp(lnt.location,lnt);
};
  break;
    

  case 324:
  if (yyn == 324)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3176 of "src/main/bison/cml.y"  */
    {
    LexIntegerToken lit = (LexIntegerToken)((yystack.valueAt (1-(1))));
    yyval = new AIntLiteralSymbolicLiteralExp(lit.location,lit);
};
  break;
    

  case 325:
  if (yyn == 325)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3187 of "src/main/bison/cml.y"  */
    {
    LexQuoteToken value = (LexQuoteToken)((yystack.valueAt (1-(1))));
    yyval = new AQuoteLiteralSymbolicLiteralExp(value.location, value);
};
  break;
    

  case 326:
  if (yyn == 326)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3195 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
    LexLocation loc = extractLexLocation(lexeme);
    yyval = new LexIntegerToken(Long.decode(lexeme.getValue()),loc);   
};
  break;
    

  case 327:
  if (yyn == 327)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3201 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
    LexLocation loc = extractLexLocation(lexeme);
    yyval = new LexIntegerToken(Long.decode(lexeme.getValue()),loc);   
};
  break;
    

  case 328:
  if (yyn == 328)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3210 of "src/main/bison/cml.y"  */
    {
    List<PDefinition> res = new LinkedList<PDefinition>();
    res.add((PDefinition)((yystack.valueAt (1-(1)))));
    yyval = res;
  };
  break;
    

  case 329:
  if (yyn == 329)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3216 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (3-(3))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 330:
  if (yyn == 330)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3226 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 331:
  if (yyn == 331)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3231 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 332:
  if (yyn == 332)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3240 of "src/main/bison/cml.y"  */
    {
    // Get constituents 
    // $1 IF
    CmlLexeme _if = (CmlLexeme)((yystack.valueAt (5-(1))));

    Position sif = _if.getStartPos();
    Position eif = _if.getEndPos();

    PExp test = (PExp)((yystack.valueAt (5-(2))));
    // $3 THEN
    PExp then = (PExp)((yystack.valueAt (5-(4))));
    List<AElseIfExp> elses = (List<AElseIfExp>)((yystack.valueAt (5-(5))));
    
    // Build an AIfExp ast node
    AIfExp ifexp = new AIfExp();
    ifexp.setTest(test);
    ifexp.setThen(then);
    ifexp.setElseList(elses);
    ifexp.setLocation( 
		      combineLexLocation( new LexLocation(null, 
							  "DEFAULT", 
							  sif.line, 
							  sif.column, 
							  sif.line, 
							  eif.column, 
							  sif.offset, 
							  eif.offset ),  
					  extractLastLexLocation( elses ) ));
    yyval = ifexp;
    
  };
  break;
    

  case 333:
  if (yyn == 333)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3275 of "src/main/bison/cml.y"  */
    {

    CmlLexeme elsetok = (CmlLexeme)((yystack.valueAt (2-(1))));
    PExp exp = (PExp)((yystack.valueAt (2-(2))));

    AElseIfExp eie = new AElseIfExp();
    eie.setLocation( 
		    combineLexLocation(  extractLexLocation( elsetok ), 
					 exp.getLocation() )
		     );
    List<AElseIfExp> res = new LinkedList<AElseIfExp>();
    res.add(eie);
    yyval =res;
  };
  break;
    

  case 334:
  if (yyn == 334)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3290 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  CmlLexeme elseif = (CmlLexeme)((yystack.valueAt (5-(1))));
  PExp elseIf = (PExp)((yystack.valueAt (5-(2))));
  // $3 THEN
  PExp then = (PExp)((yystack.valueAt (5-(4))));
  List<AElseIfExp> tail = (List<AElseIfExp>)((yystack.valueAt (5-(5))));
  
  LexLocation loc = combineLexLocation( extractLexLocation( elseif ),
					then.getLocation() );

  // Build result
  AElseIfExp eie = new AElseIfExp();
  eie.setElseIf(elseIf);
  eie.setThen(then);
  eie.setLocation( loc );
  tail.add(eie);
  yyval = tail;
};
  break;
    

  case 335:
  if (yyn == 335)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3313 of "src/main/bison/cml.y"  */
    {
    // Get Constituents
    CmlLexeme cases = (CmlLexeme)((yystack.valueAt (5-(1))));
    PExp exp = (PExp)((yystack.valueAt (5-(2))));
    // $3 COLON
    ACasesExp bubbleUp = (ACasesExp)((yystack.valueAt (5-(4)))); // Others and Cases are taken care of
    CmlLexeme end = (CmlLexeme)((yystack.valueAt (5-(5))));
    LexLocation lexLoc = combineLexLocation( extractLexLocation( cases ),
					     extractLexLocation( end ) );
					     
    // Set expression and location
    bubbleUp.setExpression(exp);
    bubbleUp.setLocation(lexLoc);
   
    yyval = bubbleUp;
  };
  break;
    

  case 336:
  if (yyn == 336)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3333 of "src/main/bison/cml.y"  */
    {
    // Get Constituent
    ACasesExp casesExp = new ACasesExp();

    // Set up a CasesExp and add this alternative to its list
    ACaseAlternative caseAlt = (ACaseAlternative)((yystack.valueAt (1-(1))));
    casesExp.getCases().add(caseAlt);
    yyval = casesExp;
  };
  break;
    

  case 337:
  if (yyn == 337)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3343 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  ACaseAlternative altExp = (ACaseAlternative)((yystack.valueAt (4-(1))));
  // $2 OTHERS
  // $3 RARROW
  PExp othExp = (PExp)((yystack.valueAt (4-(4))));

  // Build ACasesExp
  List<ACaseAlternative> altList = new LinkedList<ACaseAlternative>();
  altList.add(altExp);
  ACasesExp casesExp = new ACasesExp();
  casesExp.setCases(altList);
  casesExp.setOthers(othExp);

  yyval = casesExp;
};
  break;
    

  case 338:
  if (yyn == 338)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3360 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  ACasesExp casesExp = (ACasesExp)((yystack.valueAt (2-(1))));
  ACaseAlternative altExp = (ACaseAlternative)((yystack.valueAt (2-(2))));

  // Add altExp to tail
  casesExp.getCases().add(altExp);
  yyval = casesExp;
};
  break;
    

  case 339:
  if (yyn == 339)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3373 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patList = (List<PPattern>)((yystack.valueAt (4-(1))));
    // $2 RARROW
    PExp exp = (PExp)((yystack.valueAt (4-(3))));
    CmlLexeme semi = (CmlLexeme)((yystack.valueAt (4-(4))));

    LexLocation leftMost = extractLexLeftMostFromPatterns( patList );
    LexLocation loc = combineLexLocation ( leftMost, 
					   extractLexLocation( semi )
					   );
    
    // Build res
    ACaseAlternative res = new ACaseAlternative();
    res.setPattern(patList);
    res.setLocation(loc);
    res.setCexp( exp );

    yyval = res;
  };
  break;
    

  case 340:
  if (yyn == 340)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3406 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    yyval = new AUnaryPlusUnaryExp(location,exp);
};
  break;
    

  case 341:
  if (yyn == 341)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3414 of "src/main/bison/cml.y"  */
    {
      PExp exp = (PExp)((yystack.valueAt (2-(2))));
      LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
      LexLocation location = 
	  combineLexLocation(opLocation,exp.getLocation());
      yyval = new AUnaryMinusUnaryExp(location,exp);
  };
  break;
    

  case 342:
  if (yyn == 342)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3422 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AAbsoluteUnaryExp(location,exp);
  };
  break;
    

  case 343:
  if (yyn == 343)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3430 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AFloorUnaryExp(location,exp);
  };
  break;
    

  case 344:
  if (yyn == 344)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3438 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ANotUnaryExp(location,exp);
  };
  break;
    

  case 345:
  if (yyn == 345)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3446 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ACardinalityUnaryExp(location,exp);
  };
  break;
    

  case 346:
  if (yyn == 346)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3454 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new APowerSetUnaryExp(location,exp);
  };
  break;
    

  case 347:
  if (yyn == 347)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3462 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ADistUnionUnaryExp(location,exp);
  };
  break;
    

  case 348:
  if (yyn == 348)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3470 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ADistIntersectUnaryExp(location,exp);
  };
  break;
    

  case 349:
  if (yyn == 349)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3478 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AHeadUnaryExp(location,exp);
  };
  break;
    

  case 350:
  if (yyn == 350)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3486 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ATailUnaryExp(location,exp);
  };
  break;
    

  case 351:
  if (yyn == 351)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3494 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ALenUnaryExp(location,exp);
  };
  break;
    

  case 352:
  if (yyn == 352)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3502 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AElementsUnaryExp(location,exp);
  };
  break;
    

  case 353:
  if (yyn == 353)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3510 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AIndicesUnaryExp(location,exp);
  };
  break;
    

  case 354:
  if (yyn == 354)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3518 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AReverseUnaryExp(location,exp);
  };
  break;
    

  case 355:
  if (yyn == 355)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3526 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    yyval = new ADistConcatUnaryExp(location,exp);
};
  break;
    

  case 356:
  if (yyn == 356)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3534 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    yyval = new AMapDomainUnaryExp(location,exp);
};
  break;
    

  case 357:
  if (yyn == 357)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3542 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AMapRangeUnaryExp(location,exp);
  };
  break;
    

  case 358:
  if (yyn == 358)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3550 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ADistMergeUnaryExp(location,exp);
  };
  break;
    

  case 359:
  if (yyn == 359)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3558 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AMapInverseUnaryExp(location,exp);
  };
  break;
    

  case 360:
  if (yyn == 360)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3575 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new APlusNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 361:
  if (yyn == 361)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3580 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ATimesNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));  
  };
  break;
    

  case 362:
  if (yyn == 362)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3585 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ASubstractNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 363:
  if (yyn == 363)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3590 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ADivideNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 364:
  if (yyn == 364)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3595 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ADivNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 365:
  if (yyn == 365)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3600 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ARemNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 366:
  if (yyn == 366)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3605 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new AModNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 367:
  if (yyn == 367)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3610 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ALessNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 368:
  if (yyn == 368)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3615 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ALessEqualNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 369:
  if (yyn == 369)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3620 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AGreaterNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 370:
  if (yyn == 370)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3625 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AGreaterEqualNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 371:
  if (yyn == 371)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3630 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AEqualsBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 372:
  if (yyn == 372)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3635 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ANotEqualBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 373:
  if (yyn == 373)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3640 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AOrBooleanBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 374:
  if (yyn == 374)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3645 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AAndBooleanBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 375:
  if (yyn == 375)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3650 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AImpliesBooleanBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 376:
  if (yyn == 376)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3655 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AEquivalentBooleanBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 377:
  if (yyn == 377)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3660 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AInSetBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 378:
  if (yyn == 378)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3665 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ANotInSetBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 379:
  if (yyn == 379)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3670 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASubsetBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 380:
  if (yyn == 380)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3675 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AProperSubsetBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 381:
  if (yyn == 381)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3680 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetUnionBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 382:
  if (yyn == 382)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3685 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetDifferenceBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 383:
  if (yyn == 383)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3690 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetIntersectBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 384:
  if (yyn == 384)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3695 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASeqConcatBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 385:
  if (yyn == 385)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3700 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 386:
  if (yyn == 386)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3705 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AMapUnionBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 387:
  if (yyn == 387)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3711 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ADomainResToBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 388:
  if (yyn == 388)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3717 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 389:
  if (yyn == 389)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3723 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 390:
  if (yyn == 390)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3729 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 391:
  if (yyn == 391)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3735 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 392:
  if (yyn == 392)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3741 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 393:
  if (yyn == 393)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3965 of "src/main/bison/cml.y"  */
    {
    List<AMapletExp> res = new LinkedList<AMapletExp>();
    res.add( (AMapletExp) ((yystack.valueAt (1-(1)))) );
    yyval = res;
  };
  break;
    

  case 394:
  if (yyn == 394)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3971 of "src/main/bison/cml.y"  */
    {
    List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(1))));
    AMapletExp hd = (AMapletExp)((yystack.valueAt (3-(3))));
    maplets.add(hd);
    yyval = maplets;
  };
  break;
    

  case 395:
  if (yyn == 395)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3981 of "src/main/bison/cml.y"  */
    {
    PExp domValue = (PExp)((yystack.valueAt (3-(1))));
    // BARRARROW
    PExp rngValue = (PExp)((yystack.valueAt (3-(3))));
    LexLocation loc = combineLexLocation(domValue.getLocation(), rngValue.getLocation());
    AMapletExp res = new AMapletExp(loc, domValue, rngValue);
    yyval = res;
  };
  break;
    

  case 396:
  if (yyn == 396)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4025 of "src/main/bison/cml.y"  */
    {
    CmlLexeme mku = (CmlLexeme)((yystack.valueAt (4-(1))));
    // $2 LPAREN
    List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
    CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (4-(4))));

    LexLocation loc = combineLexLocation( extractLexLocation ( mku ),
					  extractLexLocation ( rparen ) );

    ATupleExp res = new ATupleExp( loc, exprs );
    yyval = res;
  };
  break;
    

  case 397:
  if (yyn == 397)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4043 of "src/main/bison/cml.y"  */
    {
      
    CmlLexeme mku = (CmlLexeme)((yystack.valueAt (4-(1))));
    LexNameToken name = getNameTokenFromMKUNDERNAME(mku);
    // // $3 LPAREN
    List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));

    LexLocation loc = combineLexLocation( extractLexLocation ( mku ) ,
     					  extractLexLocation ( (CmlLexeme)((yystack.valueAt (4-(4)))) ) );

    ARecordExp res = new ARecordExp(loc, name, exprs );
    yyval = res;
  };
  break;
    

  case 398:
  if (yyn == 398)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4119 of "src/main/bison/cml.y"  */
    {
    CmlLexeme l = (CmlLexeme)((yystack.valueAt (4-(1))));
    List<ATypeBind> binds = (List<ATypeBind>)((yystack.valueAt (4-(2))));
    // AMP $3
    PExp body = (PExp)((yystack.valueAt (4-(4))));

    LexLocation loc = combineLexLocation ( extractLexLocation ( l ),
					   body.getLocation() );

    ALambdaExp res = new ALambdaExp( loc, binds, body, null, null );
    yyval = res;
  };
  break;
    

  case 399:
  if (yyn == 399)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4141 of "src/main/bison/cml.y"  */
    {
  CmlLexeme wen = (CmlLexeme)((yystack.valueAt (5-(1))));
  CmlLexeme lexId  = (CmlLexeme)((yystack.valueAt (5-(2))));
  // $3 LPAREN
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (5-(4))));
  CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (5-(5))));

  LexLocation loc = combineLexLocation( extractLexLocation ( wen ),
					extractLexLocation ( rparen ) );

  LexIdentifierToken id = extractLexIdentifierToken ( lexId );

  ANewExp res = new ANewExp( loc, id, exprs );
};
  break;
    

  case 400:
  if (yyn == 400)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4161 of "src/main/bison/cml.y"  */
    {
    CmlLexeme isUnder = (CmlLexeme)((yystack.valueAt (5-(1))));
    LexNameToken typeName = (LexNameToken)((yystack.valueAt (5-(2))));
    // // $3 LPAREN
    PExp test = null;//(PExp)$4;
    // CmlLexeme rparen = (CmlLexeme)$5;
    
    LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					   typeName.getLocation() );
    
    AIsExp res = new AIsExp( loc,typeName, test, null );
    yyval = res;
    
};
  break;
    

  case 401:
  if (yyn == 401)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4176 of "src/main/bison/cml.y"  */
    {
   CmlLexeme isUnder = (CmlLexeme)((yystack.valueAt (5-(1))));
   PType type = (PType)((yystack.valueAt (5-(2))));
   // LPAREN $3
   PExp test = (PExp)((yystack.valueAt (5-(4))));
   CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (5-(5))));

   LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					  extractLexLocation ( rparen ) );

   AIsExp res = new AIsExp( loc, null, test, null );
   res.setBasicType( type );
   yyval = res;

};
  break;
    

  case 402:
  if (yyn == 402)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4192 of "src/main/bison/cml.y"  */
    {
   CmlLexeme isUnder = (CmlLexeme)((yystack.valueAt (6-(1))));
   // LPAREN $2
   PExp test = (PExp)((yystack.valueAt (6-(3))));
   // COMMA $4
   PType type = (PType)((yystack.valueAt (6-(5))));
   CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (6-(6))));

   LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					  extractLexLocation ( rparen ) );


   AIsExp res = new AIsExp( loc, null, test, null);
   res.setBasicType( type );
   yyval = res;
};
  break;
    

  case 403:
  if (yyn == 403)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4213 of "src/main/bison/cml.y"  */
    {
    CmlLexeme preu = (CmlLexeme)((yystack.valueAt (4-(1))));
    // LPAREN $2
    List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
    CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (4-(4))));

    // RWL FIXME: Either this is right because we dedeuce the
    // function in a later phase where we know more or
    // the production above should be PREUNDER exp LPAREN expList RPAREN
    // however that introduces 36 reduce/reduce conflicts at this time.

    PExp function = null;
    LexLocation loc = combineLexLocation( extractLexLocation ( preu ),
					 extractLexLocation ( rparen ) );

    APreExp res = new APreExp( loc, function, exprs );
    yyval = res;
  };
  break;
    

  case 404:
  if (yyn == 404)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4237 of "src/main/bison/cml.y"  */
    {
    List<LexIdentifierToken> ids = 
	new Vector<LexIdentifierToken>();
    
    ids.add(extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1))))));
    yyval = new ASimpleName(ids);
};
  break;
    

  case 405:
  if (yyn == 405)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4245 of "src/main/bison/cml.y"  */
    {
    ASimpleName sname = (ASimpleName)((yystack.valueAt (3-(1))));
    
    sname.getIdentifiers().add(extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
    yyval = sname;
};
  break;
    

  case 406:
  if (yyn == 406)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4255 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = extractLexNameToken((ASimpleName)((yystack.valueAt (1-(1)))));
    List<LexNameToken> identifiers = new Vector<LexNameToken>();
    identifiers.add(lnt);
    yyval = identifiers;
};
  break;
    

  case 407:
  if (yyn == 407)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4262 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = extractLexNameToken((ASimpleName)((yystack.valueAt (3-(3)))));
    List<LexNameToken> identifiers = (List<LexNameToken>)((yystack.valueAt (3-(1))));
    identifiers.add(lnt);
    yyval = identifiers;
};
  break;
    

  case 408:
  if (yyn == 408)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4275 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 409:
  if (yyn == 409)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4279 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 411:
  if (yyn == 411)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4285 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 413:
  if (yyn == 413)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4295 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 414:
  if (yyn == 414)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4308 of "src/main/bison/cml.y"  */
    {
     yyval = new ANonDeterministicIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),
									   (CmlLexeme)((yystack.valueAt (5-(5))))), 
							(PExp)((yystack.valueAt (5-(2)))), 
							(PAction)((yystack.valueAt (5-(4)))), 
							null);
 };
  break;
    

  case 415:
  if (yyn == 415)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4316 of "src/main/bison/cml.y"  */
    {
     yyval = new ANonDeterministicIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
									   (CmlLexeme)((yystack.valueAt (6-(5))))), 
							(PExp)((yystack.valueAt (6-(2)))), 
							(PAction)((yystack.valueAt (6-(4)))), 
							(List<ANonDeterministicElseIfControlStatementAction>)((yystack.valueAt (6-(5)))));
 };
  break;
    

  case 416:
  if (yyn == 416)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4327 of "src/main/bison/cml.y"  */
    {
    PAction thenStm = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
					      thenStm.getLocation());
    yyval = new ANonDeterministicElseIfControlStatementAction(location, 
							   (PExp)((yystack.valueAt (4-(2)))), 
							   thenStm);
};
  break;
    

  case 417:
  if (yyn == 417)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4339 of "src/main/bison/cml.y"  */
    {
    List<ANonDeterministicElseIfControlStatementAction> alts =
	new Vector<ANonDeterministicElseIfControlStatementAction>();
    alts.add((ANonDeterministicElseIfControlStatementAction)((yystack.valueAt (1-(1)))));
    yyval = alts;
};
  break;
    

  case 418:
  if (yyn == 418)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4346 of "src/main/bison/cml.y"  */
    {
    List<ANonDeterministicElseIfControlStatementAction> alts =
	(List<ANonDeterministicElseIfControlStatementAction>)((yystack.valueAt (2-(1))));
    alts.add((ANonDeterministicElseIfControlStatementAction)((yystack.valueAt (2-(2)))));
    yyval = alts;
};
  break;
    

  case 420:
  if (yyn == 420)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4367 of "src/main/bison/cml.y"  */
    {
      LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
      PAction action = (PAction)((yystack.valueAt (3-(2))));
      yyval = new ABlockAction(location, 
			    null, 
			    action);
  };
  break;
    

  case 421:
  if (yyn == 421)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4375 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
    ADeclareStatementDeclareStatement dclStm = (ADeclareStatementDeclareStatement)((yystack.valueAt (4-(2))));
    PAction action = (PAction)((yystack.valueAt (4-(3))));
    yyval = new ABlockAction(location, 
			  dclStm, 
			  action);
};
  break;
    

  case 422:
  if (yyn == 422)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4387 of "src/main/bison/cml.y"  */
    {
    yyval = new ADeclareStatementDeclareStatement(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3))))), 
					       (List<? extends PDefinition>) ((yystack.valueAt (3-(2)))));
};
  break;
    

  case 423:
  if (yyn == 423)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4395 of "src/main/bison/cml.y"  */
    {
    List<AAssignmentDefinition> assignmentDefs = 
	new Vector<AAssignmentDefinition>();
    assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (1-(1)))));
    yyval = assignmentDefs; 
};
  break;
    

  case 424:
  if (yyn == 424)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4402 of "src/main/bison/cml.y"  */
    {
    List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)((yystack.valueAt (3-(1))));
    
    if (assignmentDefs == null) 
	assignmentDefs = new Vector<AAssignmentDefinition>();
    
    assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (3-(3)))));
    yyval = assignmentDefs;
};
  break;
    

  case 425:
  if (yyn == 425)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4415 of "src/main/bison/cml.y"  */
    {
      LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
      PType type = (PType)((yystack.valueAt (3-(3))));
      LexLocation location = combineLexLocation(name.location,type.getLocation());
      AAccessSpecifierAccessSpecifier access = null;
      yyval = new AAssignmentDefinition(location, 
				     name, 
				     NameScope.LOCAL, 
				     false /*Boolean used_*/, 
				     null /*PDeclaration declaration_*/, 
				     access, 
				     type, 
				     null /*PExp expression_*/, 
				     null /*PType expType_*/);
  };
  break;
    

  case 428:
  if (yyn == 428)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4467 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (7-(7))));
    yyval = new AIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
							  action.getLocation()),
				       (PExp)((yystack.valueAt (7-(2)))), 
				       (PAction)((yystack.valueAt (7-(4)))), 
				       (List<? extends AElseIfControlStatementAction>)((yystack.valueAt (7-(5)))), 
				       action);
};
  break;
    

  case 429:
  if (yyn == 429)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4477 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (6-(6))));
    yyval = new AIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
							  action.getLocation()),
				       (PExp)((yystack.valueAt (6-(2)))), 
				       (PAction)((yystack.valueAt (6-(4)))), 
				       null,
				       action);
};
  break;
    

  case 430:
  if (yyn == 430)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4490 of "src/main/bison/cml.y"  */
    {
    List<AElseIfControlStatementAction> elseStms = 
	new Vector<AElseIfControlStatementAction>();

    PAction thenStm = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),thenStm.getLocation());
    elseStms.add(new AElseIfControlStatementAction(location, 
						   (PExp)((yystack.valueAt (4-(2)))), 
						   thenStm));
    yyval = elseStms;
};
  break;
    

  case 431:
  if (yyn == 431)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4502 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (5-(5))));
    List<AElseIfControlStatementAction> elseStms = (List<AElseIfControlStatementAction>)((yystack.valueAt (5-(1))));
    LexLocation location = combineLexLocation(extractLastLexLocation(elseStms), extractLexLocation((CmlLexeme)((yystack.valueAt (5-(4))))));
    elseStms.add(0, new AElseIfControlStatementAction(location, (PExp)((yystack.valueAt (5-(3)))), action));
    yyval = elseStms;
};
  break;
    

  case 432:
  if (yyn == 432)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4522 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5)))));
    ACasesControlStatementAction cases = (ACasesControlStatementAction)((yystack.valueAt (5-(4))));
    cases.setLocation(location);
    cases.setExp((PExp)((yystack.valueAt (5-(2)))));
    yyval = cases;
};
  break;
    

  case 433:
  if (yyn == 433)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4533 of "src/main/bison/cml.y"  */
    {
    List<ACaseAlternativeAction> casesList = new Vector<ACaseAlternativeAction>();
    casesList.add((ACaseAlternativeAction)((yystack.valueAt (1-(1)))));
    
    ACasesControlStatementAction cases = 
	new ACasesControlStatementAction(null, 
					 null, 
					 casesList, 
					 null);
    yyval = cases;
    
};
  break;
    

  case 434:
  if (yyn == 434)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4546 of "src/main/bison/cml.y"  */
    {
    List<ACaseAlternativeAction> casesList = new Vector<ACaseAlternativeAction>();
    casesList.add((ACaseAlternativeAction)((yystack.valueAt (5-(1)))));
    
    ACasesControlStatementAction cases = 
	new ACasesControlStatementAction(null, 
					 null, 
					 casesList, 
					 (PAction)((yystack.valueAt (5-(5)))));
    yyval = cases;
};
  break;
    

  case 435:
  if (yyn == 435)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4558 of "src/main/bison/cml.y"  */
    {
    ACasesControlStatementAction cases = (ACasesControlStatementAction)((yystack.valueAt (3-(3))));
    cases.getCases().add((ACaseAlternativeAction)((yystack.valueAt (3-(1)))));
    yyval = cases;
};
  break;
    

  case 436:
  if (yyn == 436)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4567 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (3-(3))));
    List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
    
    yyval = new ACaseAlternativeAction(combineLexLocation(extractFirstLexLocation(patterns),
						       action.getLocation()), 
				    patterns, 
				    (PAction)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 437:
  if (yyn == 437)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4584 of "src/main/bison/cml.y"  */
    {
     yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 438:
  if (yyn == 438)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4588 of "src/main/bison/cml.y"  */
    {
    ACallCallStatementControlStatementAction call = 
	(ACallCallStatementControlStatementAction)((yystack.valueAt (3-(3))));
    PStateDesignator designator = convertToStateDesignator((PDesignator)((yystack.valueAt (3-(1)))));
    LexLocation location = combineLexLocation(designator.getLocation(),call.getLocation());
    yyval = new AAssignmentCallCallStatementControlStatementAction(location,
								designator, 
								call);
};
  break;
    

  case 441:
  if (yyn == 441)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4652 of "src/main/bison/cml.y"  */
    {
    LexNameToken self = extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new ASelfObjectDesignator(self.location, self);
};
  break;
    

  case 442:
  if (yyn == 442)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4657 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = (LexNameToken)((yystack.valueAt (1-(1))));
    yyval = new ANameObjectDesignator(name.location, name, null);
};
  break;
    

  case 447:
  if (yyn == 447)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4677 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (4-(2))));
  yyval = new AReturnControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), exp.getLocation()), exp);
};
  break;
    

  case 455:
  if (yyn == 455)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4713 of "src/main/bison/cml.y"  */
    {
      List<PPattern> patterns = new Vector<PPattern>();
      patterns.add((PPattern)((yystack.valueAt (1-(1)))));
      yyval = patterns;
  };
  break;
    

  case 456:
  if (yyn == 456)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4719 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patterns = (Vector<PPattern>)((yystack.valueAt (3-(1))));
    patterns.add((PPattern)((yystack.valueAt (3-(3)))));
    yyval = patterns;
};
  break;
    

  case 457:
  if (yyn == 457)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4728 of "src/main/bison/cml.y"  */
    {
      CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
      LexNameToken lnt = extractLexNameToken(lexeme);
      AIdentifierPattern res = new AIdentifierPattern();
      res.setName(lnt);
      res.setLocation(lnt.getLocation());
      yyval = res;
  };
  break;
    

  case 459:
  if (yyn == 459)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4742 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (1-(1))));
    if (exp instanceof AIntLiteralSymbolicLiteralExp)
      {
	AIntLiteralSymbolicLiteralExp intExp = (AIntLiteralSymbolicLiteralExp)exp;
	AIntegerPattern res = new AIntegerPattern();
	res.setLocation(intExp.getLocation());
	res.setValue(intExp.getValue());
	yyval = res;
      }
    else
      throw new RuntimeException("Unhandled expression type in pattern. ("+exp.getClass()+")"); // TODO RWL
  };
  break;
    

  case 460:
  if (yyn == 460)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4756 of "src/main/bison/cml.y"  */
    {
    
};
  break;
    

  case 462:
  if (yyn == 462)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4769 of "src/main/bison/cml.y"  */
    {
      List<? extends PPattern> plist = null;
      LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (3-(1)))));
      yyval = new ARecordPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
						 (CmlLexeme)((yystack.valueAt (3-(3))))), 
			      null, 
			      false, 
			      name, 
			      plist);
  };
  break;
    

  case 463:
  if (yyn == 463)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4780 of "src/main/bison/cml.y"  */
    {
    List<? extends PPattern> plist = (List<? extends PPattern>)((yystack.valueAt (4-(3))));
    LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (4-(1)))));
    yyval = new ARecordPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4))))), 
			    null, 
			    false, 
			    name, 
			    plist);

};
  break;
    

  case 464:
  if (yyn == 464)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4803 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
  };
  break;
    

  case 465:
  if (yyn == 465)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4807 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
  };
  break;
    

  case 466:
  if (yyn == 466)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4814 of "src/main/bison/cml.y"  */
    {
    PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));
    yyval = new ASetBind(location, pattern, exp);
};
  break;
    

  case 467:
  if (yyn == 467)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4824 of "src/main/bison/cml.y"  */
    {
    PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
    PType type = (PType)((yystack.valueAt (3-(3))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));
    yyval = new ATypeBind(location, pattern, type);
};
  break;
    

  case 468:
  if (yyn == 468)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4834 of "src/main/bison/cml.y"  */
    {
    List<PMultipleBind> binds = new Vector<PMultipleBind>();
    binds.add((PMultipleBind)((yystack.valueAt (1-(1)))));
    yyval = binds;
};
  break;
    

  case 469:
  if (yyn == 469)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4840 of "src/main/bison/cml.y"  */
    {
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (3-(1))));
    binds.add((PMultipleBind)((yystack.valueAt (3-(3)))));
    yyval = binds;
};
  break;
    

  case 470:
  if (yyn == 470)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4849 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
  };
  break;
    

  case 472:
  if (yyn == 472)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4857 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));;
    yyval = new ASetMultipleBind(location, patterns, exp);
};
  break;
    

  case 473:
  if (yyn == 473)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4867 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
    PType type = (PType)((yystack.valueAt (3-(3))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));;
    yyval = new ATypeMultipleBind(location, patterns, type);
};
  break;
    

  case 474:
  if (yyn == 474)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4877 of "src/main/bison/cml.y"  */
    {
    ATypeBind tb = (ATypeBind)((yystack.valueAt (1-(1))));
    List<ATypeBind> res = new LinkedList<ATypeBind>();
    res.add(tb);
    yyval = res;
  };
  break;
    

  case 475:
  if (yyn == 475)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4884 of "src/main/bison/cml.y"  */
    {
      ATypeBind hd = (ATypeBind)((yystack.valueAt (3-(3))));
      List<ATypeBind> tbl = (List<ATypeBind>)((yystack.valueAt (3-(1))));
      tbl.add(hd);
      yyval = tbl;
    };
  break;
    



/* Line 360 of cmlskeleton.java.m4  */
/* Line 6811 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval, yyloc);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    yyn = yyr1_[yyn];
    int yystate = yypgoto_[yyn - yyntokens_] + yystack.stateAt (0);
    if (0 <= yystate && yystate <= yylast_
	&& yycheck_[yystate] == yystack.stateAt (0))
      yystate = yytable_[yystate];
    else
      yystate = yydefgoto_[yyn - yyntokens_];

    yystack.push (yystate, yyval, yyloc);
    return YYNEWSTATE;
  }

  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
	      if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }

  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
			         Object yyvaluep				 , Object yylocationp)
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
	      + yylocationp + ": "
	      + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }

  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse () throws java.io.IOException
  {
    /// Lookahead and lookahead in internal form.
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;

    YYStack yystack = new YYStack ();

    /* Error handling.  */
    int yynerrs_ = 0;
    /// The location where the error started.
    Location yyerrloc = null;

    /// Location of the lookahead.
    Location yylloc = new Location (null, null);

    /// @$.
    Location yyloc;

    /// Semantic value of the lookahead.
    Object yylval = null;

    int yyresult;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval, yylloc);

    int label = YYNEWSTATE;
    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
	   pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);
    
        /* Accept?  */
        if (yystate == yyfinal_)
          return true;
    
        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyn == yypact_ninf_)
          {
            label = YYDEFAULT;
	    break;
          }
    
        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {
	    yycdebug ("Reading a token: ");
	    yychar = yylex ();
            
	    yylloc = new Location(yylexer.getStartPos (),
	    		   	            yylexer.getEndPos ());
            yylval = yylexer.getLVal ();
          }
    
        /* Convert token to internal form.  */
        if (yychar <= EOF)
          {
	    yychar = yytoken = EOF;
	    yycdebug ("Now at end of input.\n");
          }
        else
          {
	    yytoken = yytranslate_ (yychar);
	    yy_symbol_print ("Next token is", yytoken,
	    		     yylval, yylloc);
          }
    
        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;
    
        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
	    if (yyn == 0 || yyn == yytable_ninf_)
	      label = YYFAIL;
	    else
	      {
	        yyn = -yyn;
	        label = YYREDUCE;
	      }
          }
    
        else
          {
            /* Shift the lookahead token.  */
	    yy_symbol_print ("Shifting", yytoken,
	    		     yylval, yylloc);
    
            /* Discard the token being shifted.  */
            yychar = yyempty_;
    
            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;
    
            yystate = yyn;
            yystack.push (yystate, yylval, yylloc);
            label = YYNEWSTATE;
          }
        break;
    
      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYFAIL;
        else
          label = YYREDUCE;
        break;
    
      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
	yystate = yystack.stateAt (0);
        break;
    
      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYFAIL:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
	    ++yynerrs_;
	    yyerror (yylloc, yysyntax_error (yystate, yytoken));
          }
    
        yyerrloc = yylloc;
        if (yyerrstatus_ == 3)
          {
	    /* If just tried and failed to reuse lookahead token after an
	     error, discard it.  */
    
	    if (yychar <= EOF)
	      {
	      /* Return failure if at end of input.  */
	      if (yychar == EOF)
	        return false;
	      }
	    else
	      yychar = yyempty_;
          }
    
        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;
    
      /*---------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `---------------------------------------------------*/
      case YYERROR:
    
        yyerrloc = yystack.locationAt (yylen - 1);
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;
    
      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;	/* Each real token shifted decrements this.  */
    
        for (;;)
          {
	    yyn = yypact_[yystate];
	    if (yyn != yypact_ninf_)
	      {
	        yyn += yyterror_;
	        if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
	          {
	            yyn = yytable_[yyn];
	            if (0 < yyn)
		      break;
	          }
	      }
    
	    /* Pop the current state because it cannot handle the error token.  */
	    if (yystack.height == 1)
	      return false;
    
	    yyerrloc = yystack.locationAt (0);
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }
    
	
	/* Muck with the stack to setup for yylloc.  */
	yystack.push (0, null, yylloc);
	yystack.push (0, null, yyerrloc);
        yyloc = yylloc (yystack, 2);
	yystack.pop (2);

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval, yyloc);
    
        yystate = yyn;
	yystack.push (yyn, yylval, yyloc);
        label = YYNEWSTATE;
        break;
    
        /* Accept.  */
      case YYACCEPT:
        return true;
    
        /* Abort.  */
      case YYABORT:
        return false;
      }
  }

  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (errorVerbose)
      {
        int yyn = yypact_[yystate];
        if (yypact_ninf_ < yyn && yyn <= yylast_)
          {
	    StringBuffer res;

	    /* Start YYX at -YYN if negative to avoid negative indexes in
	       YYCHECK.  */
	    int yyxbegin = yyn < 0 ? -yyn : 0;

	    /* Stay within bounds of both yycheck and yytname.  */
	    int yychecklim = yylast_ - yyn + 1;
	    int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
	    int count = 0;
	    for (int x = yyxbegin; x < yyxend; ++x)
	      if (yycheck_[x + yyn] == x && x != yyterror_)
	        ++count;

	    // FIXME: This method of building the message is not compatible
	    // with internationalization.
	    res = new StringBuffer ("syntax error, unexpected ");
	    res.append (yytnamerr_ (yytname_[tok]));
	    if (count < 5)
	      {
	        count = 0;
	        for (int x = yyxbegin; x < yyxend; ++x)
	          if (yycheck_[x + yyn] == x && x != yyterror_)
		    {
		      res.append (count++ == 0 ? ", expecting " : " or ");
		      res.append (yytnamerr_ (yytname_[x]));
		    }
	      }
	    return res.toString ();
          }
      }

    return "syntax error";
  }


  static class YyPactClass {
  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
  public static final short yypact_ninf_ = -584;
  public static final short yypact_[] =
  {
       978,   -55,   -44,    78,   177,   222,   453,   222,   134,   549,
    -584,  -584,  -584,  -584,  -584,   549,   470,  -584,  -584,  -584,
    -584,   309,   326,  -584,  -584,   262,  -584,   243,   356,   337,
     177,  -584,  -584,  -584,  -584,  -584,   325,  -584,   279,   273,
    -584,   359,   291,  -584,   376,  -584,  -584,  -584,  -584,   549,
    -584,   410,   427,    78,   374,    78,  1675,   -63,  -584,   222,
     110,  5733,   421,   481,  -584,    21,  -584,  -584,  -584,  -584,
    -584,  -584,    34,  -584,  -584,  -584,    52,    28,  -584,   222,
     656,  -584,   945,   459,   502,   504,   427,   531,   545,  -584,
     144,   534,   550,   356,  -584,  -584,   243,   237,  1675,   480,
    1675,  1675,  1675,  1675,  1675,  -584,  -584,  -584,  -584,  -584,
    -584,  -584,  -584,  -584,   629,  -584,  -584,  -584,  -584,  -584,
    -584,  -584,  -584,  -584,   243,  -584,   482,  -584,  -584,  1675,
    1723,  5733,  5389,  5215,   447,  5733,  5733,  5733,  5733,  5733,
    5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,
    5733,  5733,  5733,  5733,  5733,  5733,  5733,   594,   633,   842,
     542,  -584,    94,   639,   641,   557,   842,   842,   842,  -584,
    2398,  -584,  -584,  -584,  -584,  -584,  -584,  -584,  -584,  -584,
    -584,  -584,   243,   842,   517,  5733,  1675,  5733,  1675,  -584,
     731,  1675,   553,  -584,   453,   222,   166,   659,   656,  -584,
    -584,  -584,  -584,  -584,  4905,   148,  1064,  -584,  -584,  -584,
      78,    78,    78,    56,   660,   -63,    78,  1066,  1066,  1066,
    5038,  1066,  1066,   -63,   558,  1066,  1066,  5733,  5733,  -584,
    -584,  -584,  1066,    78,   227,   696,   271,   468,   634,   386,
     566,   629,   637,   637,  1675,  1675,  1675,    13,   317,   653,
     -13,   629,  1342,  -584,  2467,  -584,    29,  2536,  -584,   651,
      61,  2071,   124,   654,    37,  -584,  -584,   -26,  -584,  2665,
    2734,  5826,  5826,  5826,  5826,  5826,  5826,  5826,  5826,  5826,
    5826,  5826,  5826,  5826,  5826,  5826,  5826,  5826,  5826,  5826,
    5826,  5733,  5733,  -584,  -584,   526,  -584,  -584,  -584,   347,
     671,  5733,   679,    15,  5733,    78,  -584,  -584,   373,   349,
    -584,  -584,  -584,   357,   -28,   555,  -584,  -584,  5733,  5475,
    -584,  5733,  5733,  5733,   605,  5733,  5733,  5733,  5733,  5733,
    5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,
    5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,  5733,
    5733,  5733,  5733,  5733,     3,  -584,     4,  4711,   329,  4711,
     498,  -584,    30,   394,   629,   595,   556,    -1,   600,  -584,
    -584,  -584,   680,  -584,  5733,   612,  -584,   692,  -584,  -584,
    -584,   700,   701,  1261,     8,  -584,  -584,  -584,  -584,   714,
      78,   704,   447,  5733,  5733,  -584,    33,   112,   559,   561,
    5733,   618,  -584,  -584,  -584,   243,  -584,  -584,  -584,  -584,
    -584,  -584,  -584,  -584,    45,  -584,  -584,   723,   627,   632,
    -584,  4905,  -584,   732,   734,   736,   733,   642,   730,   743,
    -584,  -584,  -584,   283,   729,  4780,  -584,  -584,   609,   668,
     205,  -584,   737,  -584,  -584,  4711,  4711,   144,  -584,  1675,
    1675,  -584,  1675,  -584,  1675,  -584,  1723,  1675,  1675,   637,
    -584,   637,   842,  5733,   206,  -584,   842,  -584,  1675,  1675,
    -584,  -584,  -584,  -584,  5733,   842,  -584,  -584,   842,  5733,
    5733,  -584,  5733,   842,  1675,  5733,   447,  5733,   842,    55,
    4711,    60,  1675,  5733,   842,  5733,  2803,  5733,  5733,    87,
     -22,  5733,   842,  1675,  5733,   842,  5733,  5733,  5733,   746,
    -584,    88,  2872,   746,   746,   746,  -584,   746,   746,   746,
     746,   746,   746,   746,   746,   746,   746,   746,   746,   746,
     746,   746,   746,   746,  5826,   746,   746,   746,   746,   746,
     746,   746,   746,   746,   746,  5826,  -584,  -584,  5733,  5733,
    -584,   842,  1675,   748,  1675,  5733,   657,   716,  -584,    41,
     453,  4711,   614,  -584,   166,    78,    78,   612,    78,    78,
      78,  1411,    22,  -584,   760,   369,  4905,   537,    64,   721,
    -584,   755,  5733,   765,    78,   -21,  2200,  2941,  5733,  -584,
    4905,   449,  5733,   168,  -584,   747,   623,  3010,  -584,  4905,
    4905,  4905,  1978,  4905,  4905,   344,   611,  4905,  4905,  5733,
    5733,  -584,  5561,   670,   346,  4905,   777,  -584,  1323,  1066,
    1066,  1066,    78,   776,    78,  1066,   -63,  5129,  1066,   705,
     842,  -584,   558,   558,   637,   637,   629,   629,   474,   637,
     637,    18,  4711,   842,  5733,   750,   629,   629,  4711,   677,
      16,    -6,  3079,  4711,  3150,  -584,     1,   746,  -584,  2329,
     331,   735,    -2,  -584,  -584,   629,  5826,  -584,    96,  1675,
    3219,  3288,  -584,  5733,  4711,  -584,   629,  5826,  -584,  5826,
    4711,  5826,  -584,  5733,  4711,  4711,   419,   629,   771,    44,
     629,  4711,   647,  5733,  -584,  1754,   553,  -584,  1675,  -584,
     797,   800,    48,  -584,  -584,  -584,  -584,  -584,  4905,   575,
    4905,  1786,  -584,  -584,   537,  -584,    78,   793,   716,  -584,
    3357,   794,   806,  4905,  4905,  4905,   842,  3426,  -584,   809,
     811,  -584,   673,  4711,  4905,  -584,   -63,   814,  4905,  -584,
    -584,  -584,   802,   808,  3495,   211,     2,  -584,  -584,   803,
     685,   815,  -584,  -584,  4711,  4711,   230,    97,  -584,  -584,
      47,  -584,   399,   833,   669,  -584,  1291,  2144,  3015,    86,
     832,   839,  3069,   836,  5129,  5647,  -584,  -584,    49,  -584,
    -584,  -584,  5733,  -584,    27,  4711,  5733,  -584,  5733,  -584,
    5733,  -584,  -584,  5733,  5733,  5733,  -584,  -584,  -584,   835,
    5733,  -584,   613,  -584,  -584,  3564,  3633,  1675,  -584,    98,
     842,  5303,  1675,  4711,   982,   460,   749,   807,   707,   311,
    4905,  4905,  -584,   612,  1819,  -584,  1940,  -584,  -584,   467,
      78,  -584,  -584,   691,  4905,  -584,  1149,  1665,   864,   720,
      95,  -584,   225,   278,  5733,  -584,   722,    78,  -584,   -63,
     -63,   759,    78,   381,  4905,   702,   287,  4905,  -584,  -584,
    -584,  -584,    78,  5733,  1066,  -584,  1066,  4947,  5733,  -584,
    3702,  5733,  -584,  4711,  3771,  3840,  3909,  4038,  5826,  5733,
    4107,  -584,  -584,  -584,   629,  -584,   105,  -584,  -584,   721,
    4711,   629,   284,  1832,   748,   721,  5733,  5733,  2092,  2114,
    -584,   862,   867,  1675,   874,   856,  2136,  -584,  5733,  -584,
     295,  5733,  4905,   552,  -584,   799,  4905,  -584,  -584,   106,
    -584,   115,  4711,   870,   877,   863,   884,  4905,    69,   745,
     875,  -584,   751,   399,    79,  4176,  3833,  -584,  4245,  -584,
    4314,  -584,  -584,  -584,  5733,  4711,  -584,  -584,   716,  1863,
    1502,   629,   190,   716,  4711,  4711,  -584,  -584,  5733,  5733,
     629,  4905,   -63,  -584,  4384,  -584,  -584,  4513,  -584,  5733,
    4905,   883,  -584,   399,  -584,   449,  -584,    78,  4905,   -63,
    4905,  -584,  -584,  4905,   740,  4905,  -584,  -584,  -584,  -584,
    -584,  2329,   857,  -584,  1582,   629,   227,   834,  -584,   116,
     117,  2209,   890,  4905,  4905,  4642,  -584,  4905,  -584,   903,
    2460,   885,  -584,  -584,   768,  -584,  -584,    78,  -584,   227,
    4512,   807,  -584,  -584,   721,  -584,  -584,  -584,  -584,  -584,
    -584,  4905,   399,   399,  4905,   399,  4905,  -584,   744,  4905,
     243,   716,  -584,   399,  2529,   902,  -584,  -584,  -584,  4905,
    -584
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,     0,     0,     0,   127,   153,   166,   218,     0,     2,
       5,     7,     8,     9,    10,     4,   138,   139,   141,   142,
     143,     0,     0,   404,   119,   120,   123,   406,   122,     0,
     128,   129,   162,   163,   164,   165,   155,   156,     0,     0,
     209,   210,     0,   219,   220,   223,   224,     1,     6,     3,
     140,     0,     0,     0,     0,     0,     0,     0,   130,   154,
       0,     0,     0,     0,   326,     0,   327,   202,   325,   213,
     459,   324,     0,   452,   453,   454,   211,     0,   226,   221,
       0,    11,     0,     0,     0,     0,     0,     0,     0,    12,
      14,     0,   124,     0,   121,   405,   407,     0,     0,     0,
       0,     0,     0,     0,     0,   183,   184,   185,   186,   187,
     188,   189,   190,   181,   126,   167,   168,   169,   173,   172,
     179,   196,   197,   191,   180,   132,     0,   131,   157,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   317,     0,     0,     0,   404,     0,     0,     0,   286,
       0,   323,   289,   290,   291,   292,   310,   311,   315,   316,
     318,   319,   321,     0,     0,     0,     0,     0,     0,   212,
       0,     0,     0,   222,   166,   251,   277,     0,   145,   147,
     148,   149,   150,   151,     0,     0,     0,    37,    40,    39,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    30,
     107,   108,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   175,   176,   174,     0,     0,     0,   404,     0,   159,
     404,   205,   160,   203,     0,   301,     0,   284,   296,     0,
       0,   284,     0,   393,     0,   330,   331,     0,   328,     0,
       0,   340,   341,   342,   343,   344,   345,   346,   347,   348,
     349,   350,   351,   352,   353,   354,   355,   356,   357,   358,
     359,     0,     0,   458,   457,     0,   451,   450,   474,     0,
       0,     0,     0,     0,     0,     0,   322,   455,     0,     0,
     468,   470,   471,     0,     0,     0,   464,   465,     0,     0,
     460,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   462,     0,   216,     0,   217,
       0,   234,     0,     0,     0,   179,     0,   243,     0,   152,
     255,   254,   250,   252,     0,     0,   276,   278,   282,   144,
     146,     0,     0,     0,   268,    48,    49,    50,    51,     0,
       0,     0,     0,     0,     0,   441,     0,   404,     0,     0,
       0,     0,    65,    67,    68,   442,    72,   408,    70,    71,
     409,   410,   411,   437,     0,   413,   412,     0,     0,    41,
      43,     0,    38,     0,     0,     0,     0,     0,     0,     0,
      19,    18,    23,   404,     0,     0,    22,    17,     0,   115,
       0,   112,     0,    24,    25,    28,    27,    13,   125,     0,
       0,   182,     0,   171,     0,   192,     0,     0,     0,   200,
     195,   198,     0,     0,     0,   133,     0,   158,     0,     0,
     204,   161,   287,   302,     0,     0,   306,   297,     0,     0,
       0,   307,     0,     0,     0,     0,     0,     0,     0,     0,
     284,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   371,
     312,     0,   284,   361,   360,   362,   314,   363,   365,   366,
     367,   368,   369,   370,   372,   373,   374,   375,   376,   377,
     378,   379,   380,   381,   382,   383,   384,   385,   386,   387,
     388,   389,   390,   391,   392,   364,   461,   463,     0,     0,
     235,     0,     0,     0,     0,     0,     0,     0,   242,     0,
     166,   283,     0,   281,   279,     0,     0,     0,     0,     0,
       0,     0,     0,    93,     0,   442,     0,     0,     0,   243,
     267,     0,     0,     0,     0,     0,     0,     0,     0,   446,
       0,     0,     0,     0,    73,     0,     0,     0,    16,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    64,     0,     0,     0,     0,     0,    44,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   116,
       0,   109,     0,     0,   201,   199,   193,   194,     0,   178,
     177,     0,   117,     0,     0,     0,   207,   206,   285,     0,
       0,     0,     0,   395,     0,   394,     0,   288,   329,     0,
       0,   336,     0,   396,   397,   467,   398,   475,     0,     0,
       0,     0,   403,     0,   472,   456,   473,   293,   469,   294,
     466,   295,   313,     0,   214,   215,     0,   236,     0,     0,
     240,   244,     0,     0,   225,     0,   238,   253,     0,   280,
       0,     0,     0,   423,    97,    96,    95,   420,     0,     0,
       0,     0,   274,   275,   269,   270,     0,     0,     0,   448,
       0,     0,     0,     0,     0,     0,     0,     0,    53,     0,
       0,    78,    75,    77,     0,    74,     0,     0,     0,    58,
      57,    86,     0,     0,     0,   321,     0,    88,    56,     0,
       0,     0,    59,    60,    63,    62,   439,     0,   443,   438,
       0,    45,    46,     0,     0,    15,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    26,    20,     0,   113,
     114,   170,     0,   134,     0,   118,     0,   303,     0,   298,
       0,   300,   308,     0,     0,     0,   332,   335,   338,     0,
       0,   399,     0,   401,   400,     0,     0,     0,   228,     0,
       0,     0,     0,   247,     0,     0,     0,   268,   239,   425,
       0,     0,   422,     0,     0,    94,     0,   421,   271,   272,
       0,   449,    52,     0,     0,   419,     0,     0,     0,   433,
       0,   447,     0,     0,     0,    54,     0,     0,    55,     0,
       0,     0,     0,     0,     0,     0,   440,     0,    42,    33,
      34,    36,     0,     0,     0,    32,     0,     0,     0,   110,
       0,     0,   135,   208,     0,     0,     0,     0,   333,     0,
       0,   402,   320,   305,   237,   229,     0,   232,   233,   243,
     231,   241,     0,     0,     0,   243,     0,     0,     0,     0,
     424,    66,     0,     0,     0,     0,     0,   414,     0,   417,
       0,     0,     0,     0,   432,     0,     0,    80,    83,     0,
      82,     0,    76,     0,     0,     0,     0,     0,     0,     0,
       0,    92,     0,    47,     0,     0,     0,    21,     0,   136,
       0,   304,   299,   309,     0,   337,   339,   230,   246,     0,
       0,   258,     0,     0,   427,   426,   101,   102,     0,     0,
     273,     0,     0,   100,     0,   415,   418,     0,   429,     0,
       0,     0,   435,   436,    79,     0,    81,     0,     0,     0,
       0,    61,    69,     0,     0,     0,    31,    29,    35,   111,
     137,     0,   249,   245,     0,   259,   260,     0,   257,     0,
       0,     0,     0,     0,     0,     0,   428,     0,    84,     0,
       0,     0,    90,    87,     0,    85,   334,     0,   227,   261,
       0,   268,   265,   266,   243,   264,   262,   263,    99,    98,
     106,     0,   416,   430,     0,   434,     0,   103,     0,     0,
     248,   246,   105,   431,     0,     0,    91,   256,   104,     0,
      89
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -584,  -584,   906,    84,  -584,  -584,  -584,    63,  -584,   717,
    -584,  -584,   503,  -584,   608,  -584,   340,  -583,    91,  -584,
    -584,   228,  -584,  -584,    26,  -584,  -584,   554,   312,   308,
     510,  -584,   897,  -584,   -41,     7,  -584,  -584,   922,   -34,
    -584,  -584,   938,  -584,   758,     0,   292,  -584,   898,    31,
     652,  -584,   798,  -584,  -584,  -584,  -584,  -175,  -584,  -584,
     -24,   505,  -248,   712,   330,   889,  -584,   920,   370,   887,
    -584,  -584,  -584,   926,    75,  -584,   412,  -584,  -584,   276,
    -576,  -584,   -62,  -555,  -584,  -584,  -584,  -186,  -584,  -584,
    -584,  -584,   161,  -584,  -584,   270,  -584,  -584,   423,  -584,
    -584,  -126,   912,   192,  -584,   596,   509,  -584,    14,  -584,
    -584,   350,  -584,  -584,  -584,   507,  -584,  -584,  -584,  -584,
    -584,  -584,    -3,    11,    -7,  -584,   101,  -584,     9,    35,
    -584,  -584,  -550,  -584,  -584,  -584,   100,  -584,  -584,   398,
     404,  -584,  -584,  -584,  -146,   -15,  -178,  -584,  -584,  -584,
    -584,  -584,   538,  -153,  -166,   514,  -584,  -584,  -584
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,    89,   213,   206,   207,
     208,   419,   420,   761,   571,   593,   594,   918,   919,   402,
     572,   573,   403,   404,   611,   230,   231,   440,   441,   442,
     464,    13,    24,    25,   214,    92,    14,    30,    31,   428,
      15,    16,    17,    81,   197,   198,   199,    36,    37,   368,
     235,   115,   116,   117,   118,   236,   119,   120,   121,   122,
      68,   252,   253,   467,   200,    40,    41,   265,   201,    43,
      44,    45,    46,   266,   689,   889,   192,   362,   817,   367,
     557,   558,   992,   993,  1018,   202,   372,   369,   370,   371,
     816,  1024,   579,   580,   714,   715,   716,   203,   376,   377,
     378,   256,   490,   171,    71,   267,   268,   172,   796,   173,
     660,   661,   174,   175,   262,   263,   176,   177,   178,   179,
     180,   181,   182,    93,   406,   407,   909,   910,   408,   409,
     576,   702,   563,   410,   913,   411,   838,   839,   412,   413,
     414,   415,   416,   581,   307,   296,   308,   297,    73,    74,
      75,   315,   316,   298,   309,   310,   311,   312,   299
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -446;
  public static final short
  yytable_[] =
  {
        27,   313,   694,   718,   470,   354,   356,   260,   732,   373,
      26,    91,   363,   295,    28,   317,   365,   703,   546,   547,
     789,   800,   314,   127,    72,   852,   782,   792,   185,   498,
     708,   468,   123,   787,   485,   871,    38,    39,    42,   723,
     462,   187,   190,   125,   185,   550,   473,   588,   555,    27,
      27,    21,    96,   124,   709,   190,   822,   868,   810,   612,
      26,   612,    22,   577,    28,   589,  -166,   217,   218,   219,
     663,   426,   220,   123,   123,   664,   123,   123,   123,   123,
     123,   717,   209,    27,   221,    54,   982,   477,   222,   811,
      38,   126,   507,    48,   124,   124,   986,   124,   124,   124,
     124,   124,   672,   682,   463,   123,   123,    39,   301,   862,
      42,   801,   856,   885,   439,    90,   229,   129,   916,    72,
     947,   974,    54,    27,   486,   492,   124,   124,   673,   486,
     976,  1028,  1029,    48,    47,   590,   613,   248,   613,   790,
     469,  -166,  -166,   130,   505,  -166,   793,   614,   502,   556,
     481,   505,    55,   502,   502,   217,   218,   219,  -166,   303,
     220,   788,   123,   831,   123,   489,   491,   123,   505,   423,
     424,   425,   221,   783,   186,   429,   222,   505,   499,   474,
     551,   191,   872,   124,    23,   124,   434,   188,   124,   438,
     484,   734,   448,   511,   695,  -166,  -166,   869,   823,   505,
      23,   405,   223,   224,   810,   474,   209,    27,    27,    27,
     474,   474,   374,    27,   632,   225,   226,   227,   228,    55,
     123,   123,   123,    32,    33,    34,    35,   375,   123,    55,
      27,    70,   630,   643,  -406,   997,    55,   474,   474,   229,
     917,   124,   124,   124,  -444,   502,   474,   474,   502,   124,
     449,    97,   234,    98,   417,   502,   975,   105,   106,   107,
     108,   109,   110,   111,   112,   975,   474,   474,   591,   592,
     450,    99,  -166,   900,   482,   100,   101,   102,   103,   104,
     430,   431,   432,    29,   436,   437,   453,    61,   443,   444,
     223,   224,    18,   920,    53,   447,   641,   644,   454,   965,
    -132,  -445,   500,   225,   226,   227,   228,   449,    18,   365,
     662,  -132,   651,   948,   729,   730,    51,   656,    54,   953,
     645,  -444,   908,   418,   591,   592,    70,   450,  -166,   649,
      19,   731,  -444,    52,   244,   797,   548,    32,    33,    34,
      35,   667,   574,    23,    57,    61,    19,   245,   295,   583,
      54,    70,   244,   631,   246,   632,   675,    59,    70,    70,
      70,  -406,    62,    63,  -406,   245,    64,   729,   730,   668,
      20,   896,   246,   686,   697,    70,    70,    72,  -445,    65,
     575,    67,    70,   306,   731,    60,    20,    27,   949,  -445,
     470,    76,  1008,    32,    33,    34,    35,    77,   998,   293,
     105,   106,   107,   108,   109,   110,   111,   112,    79,   244,
     599,   600,   601,   897,   113,   602,    66,    67,   405,    80,
      62,    63,   245,   457,    64,   123,   123,   603,   123,   246,
     123,   604,   123,   123,   123,   183,    82,   294,    83,    84,
      85,    86,   395,    87,   123,   123,   124,   124,  1041,   124,
     125,   124,    23,   124,   124,   124,   229,   229,   229,    88,
     123,    61,   229,   229,   778,   124,   124,    55,   123,   229,
     229,    72,   465,   229,    66,    67,    54,   784,   781,   123,
      95,   124,   662,   244,   210,   455,   757,   125,    97,   124,
      98,   244,   493,   501,   504,   184,   245,   494,   126,   505,
     124,     5,   506,   246,   245,   549,    55,   505,    99,    56,
     809,   246,   100,   101,   102,   103,   104,     6,     7,  -406,
     749,   244,  -406,   502,   700,   701,   503,   211,   123,   212,
     123,    61,   355,    23,   245,   126,    62,    63,   729,   730,
      64,   246,   232,   722,   502,   605,   224,   552,   840,   124,
     606,   124,     1,   264,     2,   731,   215,   929,   607,   608,
     609,   610,    27,    27,   893,    27,    27,    27,   743,   502,
     216,   750,   807,   405,   763,   704,   705,   706,     3,     4,
     250,    27,   233,   771,    70,   293,   238,   405,   247,   244,
      66,    67,   773,   712,   713,   375,   405,   405,   405,   745,
     405,   405,   245,   458,   405,   405,    62,    63,   291,   246,
      64,   405,   575,   746,   123,   969,   970,    55,    67,    27,
     903,    27,   598,   294,    32,    33,    34,    35,   881,   599,
     600,   601,   886,   769,   602,   124,   244,   105,   106,   107,
     108,   109,   110,   111,   112,   123,   603,   292,   300,   245,
     604,   113,   244,   304,    70,   305,   246,   306,    70,   366,
      66,    67,   194,   379,   439,   245,   124,    70,   427,   456,
      70,   123,   246,   245,   123,    70,   244,   476,    70,   492,
      70,   483,   766,   767,   768,   495,    70,     5,   772,   245,
     776,   777,   124,   497,    70,   124,   246,    70,   516,   466,
     508,   553,   846,     6,     7,   405,   559,   405,   114,   554,
     195,   451,   560,    27,   196,   568,   569,   570,   562,   244,
     405,   405,   405,   452,   564,   565,   566,   829,   582,   584,
     615,   405,   245,   616,   595,   405,   596,   840,   417,   246,
     619,   260,   620,    70,   621,    61,   361,   624,   623,   622,
     237,   625,   239,   240,   241,   242,   243,   626,   628,   463,
     319,   633,   688,   692,   605,   224,   693,   698,   710,   606,
     555,   745,   719,   721,   736,   737,   758,   607,   608,   609,
     610,   249,   251,   123,   764,    61,   808,   751,   123,   904,
     123,   770,   229,   229,   229,   786,   644,   507,   229,   293,
     812,   799,   229,   229,   124,   820,   924,   830,   821,   124,
     833,   124,   401,    61,   834,   925,   926,   405,   405,   930,
      62,    63,    70,   842,    64,   843,   844,    27,   847,   849,
     853,   405,   999,  1000,   854,    70,   850,   294,   358,   293,
     360,   857,   855,   364,    27,   858,   863,   864,  1020,    27,
    1021,   405,    70,   866,   405,   894,    61,   556,   879,    27,
      62,    63,   577,   928,    64,   971,   905,   293,   914,   123,
     915,   923,   927,   934,    66,    67,   958,   294,   932,   123,
      70,   959,   961,   962,   977,   978,  1022,  1023,    62,    63,
     124,   979,    64,   392,   983,   393,   459,   460,   461,   394,
     124,   980,   984,   985,   251,   294,  1007,  1031,  1017,   405,
     293,  1036,  1038,   405,    66,    67,  1014,  1039,    70,  1049,
    1045,    49,   617,   422,   405,   123,   123,   936,  1002,   937,
     395,    62,    63,   735,   921,    64,  1009,   825,   578,   396,
      23,   780,    66,    67,   779,  1011,   124,   124,   294,   629,
      94,   194,    58,   204,    50,   205,   380,   128,   405,    69,
     302,   638,   229,   229,   471,   189,   193,   405,    78,   952,
     123,   696,   818,   170,    27,   405,     5,   405,   895,  1047,
     405,     1,   405,     2,   828,    66,    67,   699,   585,   655,
    1025,   124,     6,     7,   405,   658,    97,   892,    98,   195,
     405,   405,    70,   196,   405,  1016,  1026,     3,     4,     5,
     798,   966,   759,   650,  1040,   972,    99,   405,   760,   678,
     100,   101,   102,   103,   104,     6,     7,     0,   405,   618,
       0,   405,  1027,   405,     0,     0,   405,     0,     0,     0,
       0,     0,     0,   254,   257,   261,   405,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,     0,
     194,     0,   421,     0,   205,    82,     0,    83,    84,    85,
      86,     0,    87,     0,     0,     0,     0,     0,    23,     0,
       0,     0,     0,     0,     0,     5,     0,   357,    88,   359,
       0,   634,   635,     0,   636,     0,   637,    70,   251,   639,
     640,     6,     7,     0,     0,     0,     0,     0,   195,     0,
     646,   647,   196,     0,     0,     0,    67,     0,     0,     0,
       0,     0,   435,     0,     0,     0,   358,     0,     0,   445,
     446,     0,     0,     0,   665,   105,   106,   107,   108,   109,
     110,   111,   112,   907,     0,   676,     0,     0,     0,   113,
     599,   600,   601,     0,     0,   602,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   908,   603,     0,     0,
       0,   604,     0,     0,   711,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   728,     0,
       0,     0,     0,     0,   687,     0,   690,   739,   740,   741,
       0,   747,   748,   496,     0,   752,   753,     0,     0,     0,
       0,     0,     0,   762,     0,     0,     0,     0,     0,     0,
     509,   512,     0,   513,   514,   515,     0,   517,   518,   519,
     520,   521,   522,   523,   524,   525,   526,   527,   528,   529,
     530,   531,   532,   533,   534,   535,   536,   537,   538,   539,
     540,   541,   542,   543,   544,   545,     0,     0,     0,     0,
       0,     0,   381,   382,     0,   383,     0,   384,     0,   385,
     386,   387,   388,   389,     0,     0,   561,     0,     0,   390,
     251,     0,     0,   391,     0,   605,   224,     0,     0,     0,
     606,     0,   217,   218,   219,   586,   587,   220,   607,   608,
     609,   610,   597,     0,     0,     0,   824,   859,   826,   221,
     392,   802,   393,   222,     0,     0,   394,   765,     0,     0,
       0,   835,   836,   837,   599,   600,   601,     0,     0,   602,
       0,     0,   845,     0,     0,     0,   848,   815,     0,     0,
     819,   603,     0,     0,     0,   604,    97,   395,    98,     0,
       0,     0,   567,     0,     0,     0,   396,   397,     0,     0,
       0,     0,     0,     0,     0,   642,    99,     0,     0,     0,
     100,   101,   102,   103,   104,     0,   648,     0,   466,     0,
       0,   652,   653,     0,   654,     0,     0,   657,     0,   659,
       0,   568,   569,   570,     0,   666,     0,   398,     0,   670,
     671,     0,   399,   674,   400,     0,   677,     0,   679,   680,
     681,     0,   599,   600,   601,     0,   707,   602,   898,   899,
       0,     0,     0,     0,     0,     0,     0,   223,   224,   603,
       0,     0,   906,   604,     0,     0,     0,     0,   250,     0,
     225,   226,   227,   228,     0,     0,     0,     0,     0,   884,
     684,   685,   931,     0,   891,   933,     0,   691,     0,   605,
     224,     0,     0,     0,   606,     0,     0,     0,     0,     0,
       0,     0,   607,   608,   609,   610,    67,     0,     0,     0,
       0,     0,     0,     0,   720,     0,     0,     0,     0,     0,
     727,     0,     0,     0,   733,   105,   106,   107,   108,   109,
     110,   111,   112,     0,   744,     0,    97,   996,    98,   113,
     968,   754,   755,     0,   973,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   981,    99,     0,     0,   522,
     100,   101,   102,   103,   104,   951,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   960,   785,   605,   224,     0,
       0,     0,   606,     0,     0,     0,     0,     0,     0,  1001,
     607,   608,   609,   610,     0,     0,     0,     0,  1006,     0,
       0,     0,     0,     0,     0,   805,  1010,     0,  1012,     0,
       0,  1013,     0,  1015,     0,   806,    97,  1019,    98,     0,
       0,   995,     0,     0,     0,   813,     0,     0,    23,     0,
       0,  1032,  1033,     0,     0,  1035,    99,     0,     0,     0,
     100,   101,   102,   103,   104,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,  1042,
       0,     0,  1043,     0,  1044,     0,    67,  1046,     0,     0,
       0,     0,     0,     0,     0,     0,     0,  1050,     0,     0,
       0,     0,     0,     0,     0,   105,   106,   107,   108,   109,
     110,   111,   112,     0,     0,     0,   599,   600,   601,   113,
       0,   602,     0,     0,     0,     0,   254,   257,    23,    97,
       0,    98,     0,   603,   870,     0,     0,   604,   873,     0,
     874,     0,   875,     0,     0,   876,   877,   878,     0,    99,
       0,     0,   880,   100,   101,   102,   103,   104,     0,     0,
       0,     0,     0,   890,     0,     0,    67,     0,   911,   912,
       0,     0,     0,     0,     0,     0,     0,    97,     0,    98,
       0,     0,     0,     0,     0,   105,   106,   107,   108,   109,
     110,   111,   112,     0,     0,     0,   922,    99,     0,   113,
       0,   100,   101,   102,   103,   104,     0,     0,   814,     0,
      98,     0,     0,     0,     0,   935,     0,     0,     0,   261,
     938,    23,     0,   940,     0,     0,     0,     0,    99,     0,
       0,   945,   100,   101,   102,   103,   104,   599,   600,   601,
       0,   827,   602,     0,     0,     0,     0,     0,   954,   955,
       0,   605,   224,     0,   603,     0,   606,     0,   604,    67,
     964,     0,     0,   967,   607,   608,   609,   610,     0,   250,
     599,   600,   601,     0,   901,   602,     0,     0,   105,   106,
     107,   108,   109,   110,   111,   112,   950,   603,    98,     0,
       0,   604,   113,     0,     0,     0,   991,     0,     0,     0,
      23,     0,     0,     0,     0,     0,    99,    67,     0,     0,
     100,   101,   102,   103,   104,     0,     0,   994,     0,    98,
       0,  1005,     0,     0,     0,     0,   105,   106,   107,   108,
     109,   110,   111,   112,     0,     0,     0,    99,    67,     0,
     113,   100,   101,   102,   103,   104,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   105,   106,   107,
     108,   109,   110,   111,   112,     0,     0,     0,     0,     0,
       0,   113,   605,   224,     0,     0,     0,   606,    23,     0,
       0,     0,     0,     0,     0,   607,   608,   609,   610,     0,
       0,   599,   600,   601,     0,   902,   602,     0,     0,     0,
       0,     0,     0,     0,     0,   605,   224,     0,   603,    23,
     606,     0,   604,     0,     0,     0,    67,     0,   607,   608,
     609,   610,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   131,     0,   132,   105,   106,   107,   108,   109,
     110,   111,   112,   133,     0,     0,     0,    67,     0,   113,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   105,   106,   107,   108,
     109,   110,   111,   112,     0,     0,     0,   134,     0,   135,
     113,     0,     0,   136,     0,   137,   138,   139,   140,   141,
     142,   143,   144,   145,   146,   147,   148,   149,   150,   151,
     152,   153,   154,   155,   156,     0,     0,   157,   158,     0,
       0,    64,   159,   160,   161,   162,   163,   164,   318,     0,
       0,     0,     0,     0,   433,   319,   605,   224,     0,     0,
       0,   606,     0,     0,     0,     0,     0,     0,   478,   607,
     608,   609,   610,   599,   600,   601,     0,   321,   602,     0,
       0,     0,     0,     0,   166,   167,   168,   169,   956,     0,
     603,    66,    67,     0,   604,   599,   600,   601,     0,     0,
     602,     0,   126,     0,     0,     0,     0,     0,   322,   323,
     957,     0,   603,     0,     0,     0,   604,   599,   600,   601,
       0,     0,   602,     0,   742,   217,   218,   219,   479,   480,
     220,     0,   963,   324,   603,     0,     0,     0,   604,     0,
     860,     0,   221,     0,     0,     0,   222,     0,     0,   325,
     326,   327,   328,   329,   330,   331,   332,   333,   334,   335,
     336,   337,   338,   339,   340,   341,   342,   343,   344,   345,
     346,   347,   348,   349,   350,   351,   352,   318,     0,     0,
       0,     0,     0,     0,   319,     0,     0,     0,     0,     0,
     599,   600,   601,   724,  1030,   602,     0,     0,     0,   353,
       0,     0,     0,     0,     0,     0,   321,   603,   605,   224,
       0,   604,     0,   606,     0,     0,     0,     0,     0,     0,
       0,   607,   608,   609,   610,     0,     0,     0,     0,     0,
     605,   224,   725,     0,     0,   606,     0,   322,   323,     0,
       0,     0,     0,   607,   608,   609,   610,     0,     0,     0,
       0,     0,   605,   224,     0,     0,     0,   606,     0,     0,
     223,   224,   324,     0,     0,   607,   608,   609,   610,     0,
       0,     0,     0,   225,   226,   227,   228,     0,   325,   326,
     327,   328,   329,   330,   331,   332,   333,   334,   335,   336,
     337,   338,   339,   340,   341,   342,   343,   344,   345,   346,
     347,   348,   349,   350,   351,   352,   318,     0,     0,     0,
       0,     0,     0,   319,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   605,   224,     0,   353,     0,
     606,     0,     0,     0,     0,   321,     0,     0,   607,   608,
     609,   610,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   794,   795,     0,     0,   322,   323,     0,     0,
       0,     0,     0,     0,     0,   318,     0,     0,     0,     0,
       0,     0,   319,   320,     0,     0,     0,     0,     0,     0,
       0,   324,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   321,     0,     0,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   337,
     338,   339,   340,   341,   342,   343,   344,   345,   346,   347,
     348,   349,   350,   351,   352,   322,   323,     0,     0,     0,
       0,   599,   600,   601,   318,  1037,   602,     0,     0,     0,
       0,   319,   472,     0,     0,     0,     0,   353,   603,     0,
     324,     0,   604,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   321,     0,     0,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   337,   338,
     339,   340,   341,   342,   343,   344,   345,   346,   347,   348,
     349,   350,   351,   352,   322,   323,     0,     0,     0,     0,
     599,   600,   601,   318,  1048,   602,     0,     0,     0,     0,
     319,     0,     0,     0,     0,     0,   353,   603,     0,   324,
       0,   604,     0,   475,     0,     0,     0,     0,     0,     0,
       0,     0,   321,     0,     0,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,   337,   338,   339,
     340,   341,   342,   343,   344,   345,   346,   347,   348,   349,
     350,   351,   352,   322,   323,     0,   605,   224,     0,     0,
       0,   606,     0,     0,     0,     0,     0,     0,     0,   607,
     608,   609,   610,     0,     0,   353,     0,     0,   324,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   337,   338,   339,   340,
     341,   342,   343,   344,   345,   346,   347,   348,   349,   350,
     351,   352,   318,     0,     0,   605,   224,     0,     0,   319,
     606,     0,     0,     0,     0,     0,     0,     0,   607,   608,
     609,   610,     0,     0,   353,     0,     0,     0,     0,     0,
       0,   321,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   487,     0,     0,
       0,     0,   322,   323,     0,     0,     0,     0,     0,     0,
       0,   318,     0,     0,     0,     0,     0,     0,   319,     0,
       0,     0,     0,     0,     0,     0,     0,   324,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     321,     0,     0,   325,   326,   327,   328,   329,   330,   331,
     332,   333,   334,   335,   336,   337,   338,   339,   340,   341,
     342,   343,   344,   345,   346,   347,   348,   349,   350,   351,
     352,   322,   323,     0,     0,     0,     0,     0,     0,     0,
     318,     0,     0,     0,     0,     0,     0,   319,     0,     0,
       0,     0,     0,   353,     0,     0,   324,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   321,
       0,     0,   325,   326,   327,   328,   329,   330,   331,   332,
     333,   334,   335,   336,   337,   338,   339,   340,   341,   342,
     343,   344,   345,   346,   347,   348,   349,   350,   351,   352,
     322,   323,     0,     0,     0,     0,     0,     0,     0,   318,
       0,     0,     0,     0,     0,     0,   319,   488,     0,     0,
       0,     0,   353,     0,     0,   324,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   321,     0,
       0,   325,   326,   327,   328,   329,   330,   331,   332,   333,
     334,   335,   336,   337,   338,   339,   340,   341,   342,   343,
     344,   345,   346,   347,   348,   349,   350,   351,   352,   322,
     323,     0,     0,     0,     0,     0,     0,     0,   318,     0,
       0,     0,     0,   669,     0,   319,     0,     0,     0,   683,
       0,   353,     0,     0,   324,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   321,     0,     0,
     325,   326,   327,   328,   329,   330,   331,   332,   333,   334,
     335,   336,   337,   338,   339,   340,   341,   342,   343,   344,
     345,   346,   347,   348,   349,   350,   351,   352,   322,   323,
       0,     0,     0,     0,     0,     0,     0,   318,     0,     0,
       0,     0,     0,     0,   319,     0,   217,   218,   219,     0,
     353,   220,     0,   324,     0,     0,     0,     0,     0,     0,
       0,   861,     0,   221,     0,     0,   321,   222,     0,   325,
     326,   327,   328,   329,   330,   331,   332,   333,   334,   335,
     336,   337,   338,   339,   340,   341,   342,   343,   344,   345,
     346,   347,   348,   349,   350,   351,   352,   322,   323,     0,
     217,   218,   219,     0,     0,   220,   318,     0,     0,     0,
       0,     0,     0,   319,   726,   865,     0,   221,     0,   353,
       0,   222,   324,     0,     0,   791,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   321,     0,     0,   325,   326,
     327,   328,   329,   330,   331,   332,   333,   334,   335,   336,
     337,   338,   339,   340,   341,   342,   343,   344,   345,   346,
     347,   348,   349,   350,   351,   352,   322,   323,     0,     0,
       0,     0,     0,     0,     0,   738,     0,   318,     0,     0,
       0,   223,   224,     0,   319,     0,     0,     0,   353,     0,
       0,   324,     0,     0,   225,   226,   227,   228,     0,     0,
       0,     0,     0,     0,     0,     0,   321,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   337,
     338,   339,   340,   341,   342,   343,   344,   345,   346,   347,
     348,   349,   350,   351,   352,   223,   224,   322,   323,     0,
       0,     0,     0,     0,     0,     0,   318,     0,   225,   226,
     227,   228,     0,   319,   803,     0,     0,   353,   480,     0,
       0,     0,   324,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   321,     0,     0,   325,   326,
     327,   328,   329,   330,   331,   332,   333,   334,   335,   336,
     337,   338,   339,   340,   341,   342,   343,   344,   345,   346,
     347,   348,   349,   350,   351,   352,   322,   323,     0,     0,
       0,     0,     0,     0,     0,   318,     0,     0,     0,     0,
       0,     0,   319,   804,     0,     0,     0,     0,   353,     0,
       0,   324,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   321,     0,     0,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   337,
     338,   339,   340,   341,   342,   343,   344,   345,   346,   347,
     348,   349,   350,   351,   352,   322,   323,     0,     0,     0,
       0,     0,     0,     0,   318,     0,     0,     0,     0,     0,
       0,   319,   832,     0,     0,     0,     0,   353,     0,     0,
     324,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   321,     0,     0,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   337,   338,
     339,   340,   341,   342,   343,   344,   345,   346,   347,   348,
     349,   350,   351,   352,   322,   323,     0,     0,     0,     0,
       0,     0,     0,   318,     0,     0,     0,     0,     0,     0,
     319,   841,     0,     0,     0,     0,   353,     0,     0,   324,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   321,     0,     0,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,   337,   338,   339,
     340,   341,   342,   343,   344,   345,   346,   347,   348,   349,
     350,   351,   352,   322,   323,     0,     0,     0,     0,     0,
       0,     0,   318,     0,     0,     0,     0,     0,     0,   319,
       0,     0,     0,     0,     0,   353,     0,     0,   324,     0,
       0,     0,   851,     0,     0,     0,     0,     0,     0,     0,
       0,   321,     0,     0,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   337,   338,   339,   340,
     341,   342,   343,   344,   345,   346,   347,   348,   349,   350,
     351,   352,   322,   323,     0,     0,     0,     0,     0,     0,
       0,   318,     0,     0,     0,     0,     0,     0,   319,   882,
       0,     0,     0,     0,   353,     0,     0,   324,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     321,     0,     0,   325,   326,   327,   328,   329,   330,   331,
     332,   333,   334,   335,   336,   337,   338,   339,   340,   341,
     342,   343,   344,   345,   346,   347,   348,   349,   350,   351,
     352,   322,   323,     0,     0,     0,     0,     0,     0,     0,
     318,     0,     0,     0,     0,     0,     0,   319,   883,     0,
       0,     0,     0,   353,     0,     0,   324,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   321,
       0,     0,   325,   326,   327,   328,   329,   330,   331,   332,
     333,   334,   335,   336,   337,   338,   339,   340,   341,   342,
     343,   344,   345,   346,   347,   348,   349,   350,   351,   352,
     322,   323,     0,     0,     0,     0,     0,     0,     0,   318,
       0,     0,     0,     0,     0,     0,   319,     0,     0,     0,
       0,     0,   353,     0,     0,   324,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   321,     0,
       0,   325,   326,   327,   328,   329,   330,   331,   332,   333,
     334,   335,   336,   337,   338,   339,   340,   341,   342,   343,
     344,   345,   346,   347,   348,   349,   350,   351,   352,   322,
     323,     0,     0,     0,     0,     0,     0,     0,   318,     0,
       0,     0,     0,     0,     0,   319,     0,     0,   941,     0,
       0,   353,     0,     0,   324,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   321,     0,     0,
     325,   326,   327,   328,   329,   330,   331,   332,   333,   334,
     335,   336,   337,   338,   339,   340,   341,   342,   343,   344,
     345,   346,   347,   348,   349,   350,   351,   352,   322,   323,
       0,     0,     0,     0,   217,   218,   219,   318,     0,   220,
       0,     0,     0,     0,   319,     0,     0,   939,     0,   988,
     353,   221,     0,   324,     0,   222,   942,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   321,     0,     0,   325,
     326,   327,   328,   329,   330,   331,   332,   333,   334,   335,
     336,   337,   338,   339,   340,   341,   342,   343,   344,   345,
     346,   347,   348,   349,   350,   351,   352,   322,   323,     0,
       0,     0,     0,     0,     0,     0,   318,     0,     0,     0,
       0,     0,     0,   319,     0,     0,     0,     0,     0,   353,
       0,     0,   324,     0,     0,   943,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   321,     0,     0,   325,   326,
     327,   328,   329,   330,   331,   332,   333,   334,   335,   336,
     337,   338,   339,   340,   341,   342,   343,   344,   345,   346,
     347,   348,   349,   350,   351,   352,   322,   323,     0,   223,
     224,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   225,   226,   227,   228,     0,     0,   353,     0,
       0,   324,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   337,
     338,   339,   340,   341,   342,   343,   344,   345,   346,   347,
     348,   349,   350,   351,   352,   318,     0,     0,     0,     0,
       0,     0,   319,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   353,     0,     0,
       0,     0,     0,     0,   321,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     944,     0,     0,     0,     0,   322,   323,     0,     0,     0,
       0,     0,     0,     0,   318,     0,     0,     0,     0,     0,
       0,   319,     0,     0,     0,     0,     0,     0,     0,     0,
     324,     0,     0,     0,     0,     0,     0,     0,     0,   946,
       0,     0,     0,   321,     0,     0,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   337,   338,
     339,   340,   341,   342,   343,   344,   345,   346,   347,   348,
     349,   350,   351,   352,   322,   323,     0,     0,     0,     0,
       0,     0,     0,   318,     0,     0,     0,     0,     0,     0,
     319,   987,     0,     0,     0,     0,   353,     0,     0,   324,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   321,     0,     0,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,   337,   338,   339,
     340,   341,   342,   343,   344,   345,   346,   347,   348,   349,
     350,   351,   352,   322,   323,     0,     0,     0,     0,     0,
       0,     0,   318,     0,     0,     0,     0,     0,     0,   319,
       0,     0,     0,     0,     0,   353,     0,     0,   324,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   321,     0,     0,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   337,   338,   339,   340,
     341,   342,   343,   344,   345,   346,   347,   348,   349,   350,
     351,   352,   322,   323,     0,     0,     0,     0,     0,     0,
       0,   318,     0,     0,     0,     0,     0,     0,   319,     0,
       0,     0,     0,     0,   353,     0,     0,   324,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     321,     0,     0,   325,   326,   327,   328,   329,   330,   331,
     332,   333,   334,   335,   336,   337,   338,   339,   340,   341,
     342,   343,   344,   345,   346,   347,   348,   349,   350,   351,
     352,   322,   323,     0,     0,     0,     0,     0,     0,     0,
       0,   318,     0,   989,     0,     0,     0,     0,   319,     0,
       0,     0,     0,   353,     0,     0,   324,  1003,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     321,     0,   325,   326,   327,   328,   329,   330,   331,   332,
     333,   334,   335,   336,   337,   338,   339,   340,   341,   342,
     343,   344,   345,   346,   347,   348,   349,   350,   351,   352,
       0,   322,   323,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   990,
       0,     0,   353,     0,     0,     0,   324,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   325,   326,   327,   328,   329,   330,   331,   332,
     333,   334,   335,   336,   337,   338,   339,   340,   341,   342,
     343,   344,   345,   346,   347,   348,   349,   350,   351,   352,
     318,     0,     0,   381,   382,     0,   383,   319,   384,     0,
     385,   386,   387,   388,   389,     0,     0,     0,     0,     0,
     390,     0,   353,     0,   391,     0,     0,     0,     0,   321,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   392,     0,   393,     0,  1004,     0,   394,     0,     0,
     322,   323,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   324,     0,     0,   395,     0,
       0,     0,     0,   567,     0,     0,     0,   396,   397,     0,
       0,   325,   326,   327,   328,   329,   330,   331,   332,   333,
     334,   335,   336,   337,   338,   339,   340,   341,   342,   343,
     344,   345,   346,   347,   348,   349,   350,   351,   352,   318,
       0,     0,     0,     0,     0,     0,   319,     0,   398,     0,
       0,     0,     0,   399,     0,   400,     0,     0,     0,     0,
       0,   353,     0,     0,     0,     0,     0,     0,   321,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,  1034,     0,     0,     0,     0,   322,
     323,     0,     0,     0,     0,     0,     0,     0,   318,     0,
       0,     0,     0,     0,     0,   319,     0,     0,     0,     0,
       0,     0,     0,     0,   324,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   321,     0,     0,
     325,   326,   327,   328,   329,   330,   331,   332,   333,   334,
     335,   336,   337,   338,   339,   340,   341,   342,   343,   344,
     345,   346,   347,   348,   349,   350,   351,   352,   322,   323,
       0,     0,     0,     0,     0,     0,     0,   318,     0,     0,
       0,     0,     0,     0,   319,     0,     0,     0,     0,     0,
     353,     0,     0,   324,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   321,     0,     0,   325,
     326,   327,   328,   329,   330,   331,   332,   333,   334,   335,
     336,   337,   338,   339,   340,   341,   342,   343,   344,   345,
     346,   347,   348,   349,   350,   351,   352,   322,   323,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   353,
       0,     0,   324,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   325,   326,
     327,   328,   329,   627,   331,   332,   333,   334,   335,   336,
     337,   338,   339,   340,   341,   342,   343,   344,   345,   346,
     347,   348,   349,   350,   351,   352,   381,   382,     0,   383,
       0,   384,     0,   385,   386,   387,   388,   389,     0,     0,
       0,     0,     0,   390,     0,     0,     0,   391,   353,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   131,     0,   132,   392,     0,   393,     0,     0,     0,
     394,     0,   133,   258,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   395,     0,     0,     0,     0,   134,     0,   135,     0,
     396,   397,   136,     0,   137,   138,   139,   140,   141,   142,
     143,   144,   145,   146,   147,   148,   149,   150,   151,   152,
     153,   154,   155,   156,     0,   259,   157,   158,     0,     0,
      64,   159,   160,   161,   162,   163,   164,     0,     0,     0,
       0,   398,   131,   433,   132,     0,   399,     0,   400,     0,
       0,     0,     0,   133,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   166,   167,   168,   169,     0,     0,     0,
      66,    67,     0,     0,     0,     0,     0,   134,     0,   135,
       0,   126,     0,   136,     0,   137,   138,   139,   140,   141,
     142,   143,   144,   145,   146,   147,   148,   149,   150,   151,
     152,   153,   154,   155,   156,     0,     0,   157,   158,     0,
       0,    64,   159,   160,   161,   162,   163,   164,    82,     0,
      83,    84,    85,   774,   433,   775,     0,     0,     0,     0,
       0,     0,     0,     0,   133,     0,     0,     0,     0,     0,
       0,    88,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   166,   167,   168,   169,     0,     0,
       0,    66,    67,     0,     0,     0,     0,     0,   134,     0,
     135,     0,   126,     0,   136,     0,   137,   138,   139,   140,
     141,   142,   143,   144,   145,   146,   147,   148,   149,   150,
     151,   152,   153,   154,   155,   156,     0,     0,   157,   158,
       0,     0,    64,   159,   160,   161,   162,   163,   164,   131,
       0,   132,     0,     0,     0,   165,     0,     0,     0,     0,
     133,   258,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   166,   167,   168,   169,     0,
       0,     0,    66,    67,   134,     0,   135,     0,     0,     0,
     136,     0,   137,   138,   139,   140,   141,   142,   143,   144,
     145,   146,   147,   148,   149,   150,   151,   152,   153,   154,
     155,   156,     0,   259,   157,   158,     0,     0,    64,   159,
     160,   161,   162,   163,   164,     0,     0,   131,     0,   132,
       0,   165,     0,     0,     0,     0,     0,     0,   133,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   166,   167,   168,   169,   887,   888,     0,    66,    67,
       0,     0,   134,     0,   135,     0,     0,     0,   136,     0,
     137,   138,   139,   140,   141,   142,   143,   144,   145,   146,
     147,   148,   149,   150,   151,   152,   153,   154,   155,   156,
       0,     0,   157,   158,     0,     0,    64,   159,   160,   161,
     162,   163,   164,   131,     0,   132,   255,     0,     0,   165,
       0,     0,     0,     0,   133,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   166,
     167,   168,   169,     0,     0,     0,    66,    67,   134,     0,
     135,     0,     0,     0,   136,     0,   137,   138,   139,   140,
     141,   142,   143,   144,   145,   146,   147,   148,   149,   150,
     151,   152,   153,   154,   155,   156,     0,     0,   157,   158,
       0,     0,    64,   159,   160,   161,   162,   163,   164,   131,
     510,   132,     0,     0,     0,   165,     0,     0,     0,     0,
     133,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   166,   167,   168,   169,     0,
       0,     0,    66,    67,   134,     0,   135,     0,     0,     0,
     136,     0,   137,   138,   139,   140,   141,   142,   143,   144,
     145,   146,   147,   148,   149,   150,   151,   152,   153,   154,
     155,   156,     0,     0,   157,   158,     0,     0,    64,   159,
     160,   161,   162,   163,   164,   131,   756,   132,     0,     0,
       0,   165,     0,     0,     0,     0,   133,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   166,   167,   168,   169,     0,     0,     0,    66,    67,
     134,     0,   135,     0,     0,     0,   136,     0,   137,   138,
     139,   140,   141,   142,   143,   144,   145,   146,   147,   148,
     149,   150,   151,   152,   153,   154,   155,   156,     0,     0,
     157,   158,     0,     0,    64,   159,   160,   161,   162,   163,
     164,   131,     0,   132,   255,     0,     0,   165,     0,     0,
       0,     0,   867,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   166,   167,   168,
     169,     0,     0,     0,    66,    67,   134,     0,   135,     0,
       0,     0,   136,     0,   137,   138,   139,   140,   141,   142,
     143,   144,   145,   146,   147,   148,   149,   150,   151,   152,
     153,   154,   155,   156,     0,     0,   157,   158,     0,     0,
      64,   159,   160,   161,   162,   163,   164,   131,     0,   132,
       0,     0,     0,   165,     0,     0,     0,     0,   133,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   166,   167,   168,   169,     0,     0,     0,
      66,    67,   134,     0,   135,     0,     0,     0,   136,     0,
     137,   138,   139,   140,   141,   142,   143,   144,   145,   146,
     147,   148,   149,   150,   151,   152,   153,   154,   155,   156,
       0,     0,   157,   158,     0,     0,    64,   159,   160,   161,
     162,   163,   164,   318,     0,     0,     0,     0,     0,   165,
     319,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   321,     0,     0,     0,     0,     0,     0,   166,
     167,   168,   169,     0,     0,     0,    66,    67,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   322,   323,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   324,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   337,   338,   339,   340,
     341,     0,   343,   344,   345,   346,   347,   348,   349,   350,
     351,   352
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
         3,   167,   557,   579,   252,   183,   184,   133,   591,   195,
       3,    52,   190,   159,     3,   168,   191,   567,    15,    15,
      26,    23,   168,    57,    39,    23,     8,    26,     7,    14,
       8,    44,    56,    17,    60,     8,     5,     6,     7,    60,
      27,     7,    14,   106,     7,    15,    17,    14,    49,    52,
      53,   106,    55,    56,    32,    14,     8,     8,    14,    14,
      53,    14,   106,    55,    53,    32,    14,    11,    12,    13,
      15,    15,    16,    97,    98,    15,   100,   101,   102,   103,
     104,    17,    82,    86,    28,   107,    17,    26,    32,    45,
      59,   154,   120,     9,    97,    98,    17,   100,   101,   102,
     103,   104,    15,    15,    91,   129,   130,    76,    14,    23,
      79,    15,    15,    15,   106,    52,    90,     7,    23,   134,
      15,    15,   107,   126,   150,   153,   129,   130,   150,   150,
      15,    15,    15,    49,     0,    23,    91,   126,    91,   145,
     153,    89,    90,    33,   150,    93,   145,   102,   150,   150,
      26,   150,   150,   150,   150,    11,    12,    13,   106,   162,
      16,   145,   186,   718,   188,   291,   292,   191,   150,   210,
     211,   212,    28,   155,   153,   216,    32,   150,   304,   150,
     150,   153,   155,   186,   106,   188,   220,   153,   191,   223,
     153,    23,   233,   319,   153,   143,   144,   148,   150,   150,
     106,   204,   146,   147,    14,   150,   206,   210,   211,   212,
     150,   150,    46,   216,   150,   159,   160,   161,   162,   150,
     244,   245,   246,   171,   172,   173,   174,   196,   252,   150,
     233,    39,    27,    27,    23,    45,   150,   150,   150,   213,
      15,   244,   245,   246,    14,   150,   150,   150,   150,   252,
      23,    14,    15,    16,   106,   150,   150,   163,   164,   165,
     166,   167,   168,   169,   170,   150,   150,   150,   156,   157,
      43,    34,   106,   823,   150,    38,    39,    40,    41,    42,
     217,   218,   219,   106,   221,   222,    15,    14,   225,   226,
     146,   147,     0,    15,    32,   232,   462,    91,    27,     4,
      17,    14,   305,   159,   160,   161,   162,    23,    16,   484,
     488,    28,   478,   889,    89,    90,     7,   483,   107,   895,
     466,    91,    27,   175,   156,   157,   134,    43,   106,   475,
       0,   106,   102,     7,    23,     4,     7,   171,   172,   173,
     174,   494,   383,   106,     7,    14,    16,    36,   494,   390,
     107,   159,    23,   148,    43,   150,   502,    32,   166,   167,
     168,   150,    89,    90,   153,    36,    93,    89,    90,   495,
       0,    60,    43,   551,   560,   183,   184,   392,    91,   106,
     383,   144,   190,   100,   106,   106,    16,   390,   104,   102,
     638,    32,   975,   171,   172,   173,   174,   106,   953,    68,
     163,   164,   165,   166,   167,   168,   169,   170,    32,    23,
      11,    12,    13,   102,   177,    16,   143,   144,   421,     9,
      89,    90,    36,    37,    93,   449,   450,    28,   452,    43,
     454,    32,   456,   457,   458,    14,     9,   106,    11,    12,
      13,    14,    96,    16,   468,   469,   449,   450,  1024,   452,
     106,   454,   106,   456,   457,   458,   430,   431,   432,    32,
     484,    14,   436,   437,   630,   468,   469,   150,   492,   443,
     444,   486,   155,   447,   143,   144,   107,   643,     4,   503,
     106,   484,   660,    23,    25,    17,   612,   106,    14,   492,
      16,    23,   145,   120,   145,    14,    36,   150,   154,   150,
     503,    31,   145,    43,    36,     7,   150,   150,    34,   153,
     688,    43,    38,    39,    40,    41,    42,    47,    48,   150,
     176,    23,   153,   150,   565,   566,   153,    25,   552,    25,
     554,    14,    15,   106,    36,   154,    89,    90,    89,    90,
      93,    43,     8,   584,   150,   146,   147,   153,   726,   552,
     151,   554,     3,   106,     5,   106,    25,   176,   159,   160,
     161,   162,   565,   566,   104,   568,   569,   570,   602,   150,
      25,   605,   153,   576,   615,   568,   569,   570,    29,    30,
     106,   584,    32,   624,   392,    68,   106,   590,   106,    23,
     143,   144,   626,    56,    57,   564,   599,   600,   601,   602,
     603,   604,    36,    37,   607,   608,    89,    90,    14,    43,
      93,   614,   615,   602,   638,    63,    64,   150,   144,   622,
     153,   624,     4,   106,   171,   172,   173,   174,    15,    11,
      12,    13,   810,   622,    16,   638,    23,   163,   164,   165,
     166,   167,   168,   169,   170,   669,    28,    14,   106,    36,
      32,   177,    23,    14,   462,    14,    43,   100,   466,   106,
     143,   144,     6,     4,   106,    36,   669,   475,     8,    35,
     478,   695,    43,    36,   698,   483,    23,    26,   486,   153,
     488,    27,   619,   620,   621,    14,   494,    31,   625,    36,
     627,   628,   695,    14,   502,   698,    43,   505,    93,    46,
     145,   106,   736,    47,    48,   708,   106,   710,    56,   153,
      54,    15,    32,   716,    58,   140,   141,   142,   106,    23,
     723,   724,   725,    27,    32,    25,    25,   716,    14,    25,
       7,   734,    36,   106,   175,   738,   175,   915,   106,    43,
       8,   867,     8,   551,     8,    14,    15,    17,   106,    16,
      98,     8,   100,   101,   102,   103,   104,    28,   149,    91,
      14,    24,    14,   106,   146,   147,    50,   153,     8,   151,
      49,   774,    17,     8,    27,   152,   106,   159,   160,   161,
     162,   129,   130,   807,     7,    14,    15,   176,   812,   830,
     814,    15,   766,   767,   768,    45,    91,   120,   772,    68,
     153,    66,   776,   777,   807,     8,   847,    14,     8,   812,
      16,   814,   204,    14,     8,   849,   850,   820,   821,   853,
      89,    90,   630,    14,    93,    14,   153,   830,    14,    27,
      27,   834,   958,   959,   149,   643,    28,   106,   186,    68,
     188,     8,    27,   191,   847,   176,    14,     8,    14,   852,
      16,   854,   660,    17,   857,   106,    14,   150,    23,   862,
      89,    90,    55,   852,    93,    66,   175,    68,     4,   893,
     150,   149,   113,   862,   143,   144,    14,   106,   176,   903,
     688,    14,     8,    27,    14,     8,    52,    53,    89,    90,
     893,    28,    93,    59,   149,    61,   244,   245,   246,    65,
     903,    17,    27,   152,   252,   106,    23,    17,    51,   912,
      68,     8,    27,   916,   143,   144,   176,   149,   726,    17,
     176,    15,   419,   206,   927,   949,   950,   864,   962,   866,
      96,    89,    90,   593,   843,    93,   977,   709,   384,   105,
     106,   633,   143,   144,   632,   979,   949,   950,   106,   439,
      53,     6,    30,     8,    16,    10,   198,    59,   961,    39,
     162,   456,   936,   937,   252,    76,    79,   970,    42,   894,
     994,   559,   696,    61,   977,   978,    31,   980,   817,  1041,
     983,     3,   985,     5,   714,   143,   144,   564,   392,   482,
     997,   994,    47,    48,   997,   486,    14,    15,    16,    54,
    1003,  1004,   810,    58,  1007,   991,   997,    29,    30,    31,
     660,   910,   614,   475,  1017,   915,    34,  1020,   614,   505,
      38,    39,    40,    41,    42,    47,    48,    -1,  1031,   421,
      -1,  1034,   997,  1036,    -1,    -1,  1039,    -1,    -1,    -1,
      -1,    -1,    -1,   131,   132,   133,  1049,   135,   136,   137,
     138,   139,   140,   141,   142,   143,   144,   145,   146,   147,
     148,   149,   150,   151,   152,   153,   154,   155,   156,    -1,
       6,    -1,     8,    -1,    10,     9,    -1,    11,    12,    13,
      14,    -1,    16,    -1,    -1,    -1,    -1,    -1,   106,    -1,
      -1,    -1,    -1,    -1,    -1,    31,    -1,   185,    32,   187,
      -1,   449,   450,    -1,   452,    -1,   454,   915,   456,   457,
     458,    47,    48,    -1,    -1,    -1,    -1,    -1,    54,    -1,
     468,   469,    58,    -1,    -1,    -1,   144,    -1,    -1,    -1,
      -1,    -1,   220,    -1,    -1,    -1,   484,    -1,    -1,   227,
     228,    -1,    -1,    -1,   492,   163,   164,   165,   166,   167,
     168,   169,   170,     4,    -1,   503,    -1,    -1,    -1,   177,
      11,    12,    13,    -1,    -1,    16,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    27,    28,    -1,    -1,
      -1,    32,    -1,    -1,   576,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   590,    -1,
      -1,    -1,    -1,    -1,   552,    -1,   554,   599,   600,   601,
      -1,   603,   604,   301,    -1,   607,   608,    -1,    -1,    -1,
      -1,    -1,    -1,   615,    -1,    -1,    -1,    -1,    -1,    -1,
     318,   319,    -1,   321,   322,   323,    -1,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   337,
     338,   339,   340,   341,   342,   343,   344,   345,   346,   347,
     348,   349,   350,   351,   352,   353,    -1,    -1,    -1,    -1,
      -1,    -1,    11,    12,    -1,    14,    -1,    16,    -1,    18,
      19,    20,    21,    22,    -1,    -1,   374,    -1,    -1,    28,
     638,    -1,    -1,    32,    -1,   146,   147,    -1,    -1,    -1,
     151,    -1,    11,    12,    13,   393,   394,    16,   159,   160,
     161,   162,   400,    -1,    -1,    -1,   708,    26,   710,    28,
      59,   669,    61,    32,    -1,    -1,    65,     4,    -1,    -1,
      -1,   723,   724,   725,    11,    12,    13,    -1,    -1,    16,
      -1,    -1,   734,    -1,    -1,    -1,   738,   695,    -1,    -1,
     698,    28,    -1,    -1,    -1,    32,    14,    96,    16,    -1,
      -1,    -1,   101,    -1,    -1,    -1,   105,   106,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   463,    34,    -1,    -1,    -1,
      38,    39,    40,    41,    42,    -1,   474,    -1,    46,    -1,
      -1,   479,   480,    -1,   482,    -1,    -1,   485,    -1,   487,
      -1,   140,   141,   142,    -1,   493,    -1,   146,    -1,   497,
     498,    -1,   151,   501,   153,    -1,   504,    -1,   506,   507,
     508,    -1,    11,    12,    13,    -1,    15,    16,   820,   821,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   146,   147,    28,
      -1,    -1,   834,    32,    -1,    -1,    -1,    -1,   106,    -1,
     159,   160,   161,   162,    -1,    -1,    -1,    -1,    -1,   807,
     548,   549,   854,    -1,   812,   857,    -1,   555,    -1,   146,
     147,    -1,    -1,    -1,   151,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   159,   160,   161,   162,   144,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   582,    -1,    -1,    -1,    -1,    -1,
     588,    -1,    -1,    -1,   592,   163,   164,   165,   166,   167,
     168,   169,   170,    -1,   602,    -1,    14,    15,    16,   177,
     912,   609,   610,    -1,   916,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   927,    34,    -1,    -1,   627,
      38,    39,    40,    41,    42,   893,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   903,   644,   146,   147,    -1,
      -1,    -1,   151,    -1,    -1,    -1,    -1,    -1,    -1,   961,
     159,   160,   161,   162,    -1,    -1,    -1,    -1,   970,    -1,
      -1,    -1,    -1,    -1,    -1,   673,   978,    -1,   980,    -1,
      -1,   983,    -1,   985,    -1,   683,    14,    15,    16,    -1,
      -1,   949,    -1,    -1,    -1,   693,    -1,    -1,   106,    -1,
      -1,  1003,  1004,    -1,    -1,  1007,    34,    -1,    -1,    -1,
      38,    39,    40,    41,    42,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,  1031,
      -1,    -1,  1034,    -1,  1036,    -1,   144,  1039,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,  1049,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   163,   164,   165,   166,   167,
     168,   169,   170,    -1,    -1,    -1,    11,    12,    13,   177,
      -1,    16,    -1,    -1,    -1,    -1,   774,   775,   106,    14,
      -1,    16,    -1,    28,   782,    -1,    -1,    32,   786,    -1,
     788,    -1,   790,    -1,    -1,   793,   794,   795,    -1,    34,
      -1,    -1,   800,    38,    39,    40,    41,    42,    -1,    -1,
      -1,    -1,    -1,   811,    -1,    -1,   144,    -1,    63,    64,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    14,    -1,    16,
      -1,    -1,    -1,    -1,    -1,   163,   164,   165,   166,   167,
     168,   169,   170,    -1,    -1,    -1,   844,    34,    -1,   177,
      -1,    38,    39,    40,    41,    42,    -1,    -1,    14,    -1,
      16,    -1,    -1,    -1,    -1,   863,    -1,    -1,    -1,   867,
     868,   106,    -1,   871,    -1,    -1,    -1,    -1,    34,    -1,
      -1,   879,    38,    39,    40,    41,    42,    11,    12,    13,
      -1,    15,    16,    -1,    -1,    -1,    -1,    -1,   896,   897,
      -1,   146,   147,    -1,    28,    -1,   151,    -1,    32,   144,
     908,    -1,    -1,   911,   159,   160,   161,   162,    -1,   106,
      11,    12,    13,    -1,    15,    16,    -1,    -1,   163,   164,
     165,   166,   167,   168,   169,   170,    14,    28,    16,    -1,
      -1,    32,   177,    -1,    -1,    -1,   944,    -1,    -1,    -1,
     106,    -1,    -1,    -1,    -1,    -1,    34,   144,    -1,    -1,
      38,    39,    40,    41,    42,    -1,    -1,    14,    -1,    16,
      -1,   969,    -1,    -1,    -1,    -1,   163,   164,   165,   166,
     167,   168,   169,   170,    -1,    -1,    -1,    34,   144,    -1,
     177,    38,    39,    40,    41,    42,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   163,   164,   165,
     166,   167,   168,   169,   170,    -1,    -1,    -1,    -1,    -1,
      -1,   177,   146,   147,    -1,    -1,    -1,   151,   106,    -1,
      -1,    -1,    -1,    -1,    -1,   159,   160,   161,   162,    -1,
      -1,    11,    12,    13,    -1,    15,    16,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   146,   147,    -1,    28,   106,
     151,    -1,    32,    -1,    -1,    -1,   144,    -1,   159,   160,
     161,   162,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    14,    -1,    16,   163,   164,   165,   166,   167,
     168,   169,   170,    25,    -1,    -1,    -1,   144,    -1,   177,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   163,   164,   165,   166,
     167,   168,   169,   170,    -1,    -1,    -1,    59,    -1,    61,
     177,    -1,    -1,    65,    -1,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    86,    -1,    -1,    89,    90,    -1,
      -1,    93,    94,    95,    96,    97,    98,    99,     7,    -1,
      -1,    -1,    -1,    -1,   106,    14,   146,   147,    -1,    -1,
      -1,   151,    -1,    -1,    -1,    -1,    -1,    -1,    27,   159,
     160,   161,   162,    11,    12,    13,    -1,    36,    16,    -1,
      -1,    -1,    -1,    -1,   136,   137,   138,   139,    26,    -1,
      28,   143,   144,    -1,    32,    11,    12,    13,    -1,    -1,
      16,    -1,   154,    -1,    -1,    -1,    -1,    -1,    67,    68,
      26,    -1,    28,    -1,    -1,    -1,    32,    11,    12,    13,
      -1,    -1,    16,    -1,   176,    11,    12,    13,    87,    88,
      16,    -1,    26,    92,    28,    -1,    -1,    -1,    32,    -1,
      26,    -1,    28,    -1,    -1,    -1,    32,    -1,    -1,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    14,    -1,    -1,    -1,    -1,    -1,
      11,    12,    13,    23,    15,    16,    -1,    -1,    -1,   158,
      -1,    -1,    -1,    -1,    -1,    -1,    36,    28,   146,   147,
      -1,    32,    -1,   151,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   159,   160,   161,   162,    -1,    -1,    -1,    -1,    -1,
     146,   147,    62,    -1,    -1,   151,    -1,    67,    68,    -1,
      -1,    -1,    -1,   159,   160,   161,   162,    -1,    -1,    -1,
      -1,    -1,   146,   147,    -1,    -1,    -1,   151,    -1,    -1,
     146,   147,    92,    -1,    -1,   159,   160,   161,   162,    -1,
      -1,    -1,    -1,   159,   160,   161,   162,    -1,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,     7,    -1,    -1,    -1,
      -1,    -1,    -1,    14,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   146,   147,    -1,   158,    -1,
     151,    -1,    -1,    -1,    -1,    36,    -1,    -1,   159,   160,
     161,   162,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    63,    64,    -1,    -1,    67,    68,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    14,    15,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    36,    -1,    -1,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,    67,    68,    -1,    -1,    -1,
      -1,    11,    12,    13,     7,    15,    16,    -1,    -1,    -1,
      -1,    14,    15,    -1,    -1,    -1,    -1,   158,    28,    -1,
      92,    -1,    32,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    36,    -1,    -1,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,    67,    68,    -1,    -1,    -1,    -1,
      11,    12,    13,     7,    15,    16,    -1,    -1,    -1,    -1,
      14,    -1,    -1,    -1,    -1,    -1,   158,    28,    -1,    92,
      -1,    32,    -1,    27,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    36,    -1,    -1,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,    67,    68,    -1,   146,   147,    -1,    -1,
      -1,   151,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   159,
     160,   161,   162,    -1,    -1,   158,    -1,    -1,    92,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,     7,    -1,    -1,   146,   147,    -1,    -1,    14,
     151,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   159,   160,
     161,   162,    -1,    -1,   158,    -1,    -1,    -1,    -1,    -1,
      -1,    36,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    62,    -1,    -1,
      -1,    -1,    67,    68,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,     7,    -1,    -1,    -1,    -1,    -1,    -1,    14,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    92,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      36,    -1,    -1,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,    67,    68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
       7,    -1,    -1,    -1,    -1,    -1,    -1,    14,    -1,    -1,
      -1,    -1,    -1,   158,    -1,    -1,    92,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    36,
      -1,    -1,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
      67,    68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,
      -1,    -1,    -1,    -1,    -1,    -1,    14,   153,    -1,    -1,
      -1,    -1,   158,    -1,    -1,    92,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    36,    -1,
      -1,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,    67,
      68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,
      -1,    -1,    -1,   150,    -1,    14,    -1,    -1,    -1,    87,
      -1,   158,    -1,    -1,    92,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    36,    -1,    -1,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,    67,    68,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    14,    -1,    11,    12,    13,    -1,
     158,    16,    -1,    92,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    26,    -1,    28,    -1,    -1,    36,    32,    -1,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,    67,    68,    -1,
      11,    12,    13,    -1,    -1,    16,     7,    -1,    -1,    -1,
      -1,    -1,    -1,    14,   153,    26,    -1,    28,    -1,   158,
      -1,    32,    92,    -1,    -1,    26,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    36,    -1,    -1,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,    67,    68,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   145,    -1,     7,    -1,    -1,
      -1,   146,   147,    -1,    14,    -1,    -1,    -1,   158,    -1,
      -1,    92,    -1,    -1,   159,   160,   161,   162,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    36,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   146,   147,    67,    68,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,   159,   160,
     161,   162,    -1,    14,    15,    -1,    -1,   158,    88,    -1,
      -1,    -1,    92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    36,    -1,    -1,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,    67,    68,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    14,    15,    -1,    -1,    -1,    -1,   158,    -1,
      -1,    92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    36,    -1,    -1,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,    67,    68,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,    -1,
      -1,    14,    15,    -1,    -1,    -1,    -1,   158,    -1,    -1,
      92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    36,    -1,    -1,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,    67,    68,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,    -1,    -1,
      14,    15,    -1,    -1,    -1,    -1,   158,    -1,    -1,    92,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    36,    -1,    -1,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,    67,    68,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,     7,    -1,    -1,    -1,    -1,    -1,    -1,    14,
      -1,    -1,    -1,    -1,    -1,   158,    -1,    -1,    92,    -1,
      -1,    -1,    27,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    36,    -1,    -1,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,    67,    68,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,     7,    -1,    -1,    -1,    -1,    -1,    -1,    14,    15,
      -1,    -1,    -1,    -1,   158,    -1,    -1,    92,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      36,    -1,    -1,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,    67,    68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
       7,    -1,    -1,    -1,    -1,    -1,    -1,    14,    15,    -1,
      -1,    -1,    -1,   158,    -1,    -1,    92,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    36,
      -1,    -1,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
      67,    68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,
      -1,    -1,    -1,    -1,    -1,    -1,    14,    -1,    -1,    -1,
      -1,    -1,   158,    -1,    -1,    92,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    36,    -1,
      -1,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,    67,
      68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    14,    -1,    -1,    17,    -1,
      -1,   158,    -1,    -1,    92,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    36,    -1,    -1,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,    67,    68,
      -1,    -1,    -1,    -1,    11,    12,    13,     7,    -1,    16,
      -1,    -1,    -1,    -1,    14,    -1,    -1,   155,    -1,    26,
     158,    28,    -1,    92,    -1,    32,    26,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    36,    -1,    -1,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,    67,    68,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,
      -1,    -1,    -1,    14,    -1,    -1,    -1,    -1,    -1,   158,
      -1,    -1,    92,    -1,    -1,    26,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    36,    -1,    -1,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,    67,    68,    -1,   146,
     147,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   159,   160,   161,   162,    -1,    -1,   158,    -1,
      -1,    92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    14,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   158,    -1,    -1,
      -1,    -1,    -1,    -1,    36,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      62,    -1,    -1,    -1,    -1,    67,    68,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,    -1,
      -1,    14,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    32,
      -1,    -1,    -1,    36,    -1,    -1,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,    67,    68,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,    -1,    -1,
      14,    15,    -1,    -1,    -1,    -1,   158,    -1,    -1,    92,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    36,    -1,    -1,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,    67,    68,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,     7,    -1,    -1,    -1,    -1,    -1,    -1,    14,
      -1,    -1,    -1,    -1,    -1,   158,    -1,    -1,    92,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    36,    -1,    -1,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,    67,    68,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,     7,    -1,    -1,    -1,    -1,    -1,    -1,    14,    -1,
      -1,    -1,    -1,    -1,   158,    -1,    -1,    92,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      36,    -1,    -1,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,    67,    68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,     7,    -1,   148,    -1,    -1,    -1,    -1,    14,    -1,
      -1,    -1,    -1,   158,    -1,    -1,    92,    23,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      36,    -1,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
      -1,    67,    68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   155,
      -1,    -1,   158,    -1,    -1,    -1,    92,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
       7,    -1,    -1,    11,    12,    -1,    14,    14,    16,    -1,
      18,    19,    20,    21,    22,    -1,    -1,    -1,    -1,    -1,
      28,    -1,   158,    -1,    32,    -1,    -1,    -1,    -1,    36,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    59,    -1,    61,    -1,    62,    -1,    65,    -1,    -1,
      67,    68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    92,    -1,    -1,    96,    -1,
      -1,    -1,    -1,   101,    -1,    -1,    -1,   105,   106,    -1,
      -1,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,     7,
      -1,    -1,    -1,    -1,    -1,    -1,    14,    -1,   146,    -1,
      -1,    -1,    -1,   151,    -1,   153,    -1,    -1,    -1,    -1,
      -1,   158,    -1,    -1,    -1,    -1,    -1,    -1,    36,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    62,    -1,    -1,    -1,    -1,    67,
      68,    -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    14,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    92,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    36,    -1,    -1,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,    67,    68,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    14,    -1,    -1,    -1,    -1,    -1,
     158,    -1,    -1,    92,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    36,    -1,    -1,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,    67,    68,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   158,
      -1,    -1,    92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,    11,    12,    -1,    14,
      -1,    16,    -1,    18,    19,    20,    21,    22,    -1,    -1,
      -1,    -1,    -1,    28,    -1,    -1,    -1,    32,   158,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    14,    -1,    16,    59,    -1,    61,    -1,    -1,    -1,
      65,    -1,    25,    26,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    96,    -1,    -1,    -1,    -1,    59,    -1,    61,    -1,
     105,   106,    65,    -1,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    86,    -1,    88,    89,    90,    -1,    -1,
      93,    94,    95,    96,    97,    98,    99,    -1,    -1,    -1,
      -1,   146,    14,   106,    16,    -1,   151,    -1,   153,    -1,
      -1,    -1,    -1,    25,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   136,   137,   138,   139,    -1,    -1,    -1,
     143,   144,    -1,    -1,    -1,    -1,    -1,    59,    -1,    61,
      -1,   154,    -1,    65,    -1,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    86,    -1,    -1,    89,    90,    -1,
      -1,    93,    94,    95,    96,    97,    98,    99,     9,    -1,
      11,    12,    13,    14,   106,    16,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    25,    -1,    -1,    -1,    -1,    -1,
      -1,    32,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   136,   137,   138,   139,    -1,    -1,
      -1,   143,   144,    -1,    -1,    -1,    -1,    -1,    59,    -1,
      61,    -1,   154,    -1,    65,    -1,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    86,    -1,    -1,    89,    90,
      -1,    -1,    93,    94,    95,    96,    97,    98,    99,    14,
      -1,    16,    -1,    -1,    -1,   106,    -1,    -1,    -1,    -1,
      25,    26,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   136,   137,   138,   139,    -1,
      -1,    -1,   143,   144,    59,    -1,    61,    -1,    -1,    -1,
      65,    -1,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    86,    -1,    88,    89,    90,    -1,    -1,    93,    94,
      95,    96,    97,    98,    99,    -1,    -1,    14,    -1,    16,
      -1,   106,    -1,    -1,    -1,    -1,    -1,    -1,    25,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   136,   137,   138,   139,    52,    53,    -1,   143,   144,
      -1,    -1,    59,    -1,    61,    -1,    -1,    -1,    65,    -1,
      67,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    86,
      -1,    -1,    89,    90,    -1,    -1,    93,    94,    95,    96,
      97,    98,    99,    14,    -1,    16,    17,    -1,    -1,   106,
      -1,    -1,    -1,    -1,    25,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   136,
     137,   138,   139,    -1,    -1,    -1,   143,   144,    59,    -1,
      61,    -1,    -1,    -1,    65,    -1,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    86,    -1,    -1,    89,    90,
      -1,    -1,    93,    94,    95,    96,    97,    98,    99,    14,
      15,    16,    -1,    -1,    -1,   106,    -1,    -1,    -1,    -1,
      25,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   136,   137,   138,   139,    -1,
      -1,    -1,   143,   144,    59,    -1,    61,    -1,    -1,    -1,
      65,    -1,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    86,    -1,    -1,    89,    90,    -1,    -1,    93,    94,
      95,    96,    97,    98,    99,    14,    15,    16,    -1,    -1,
      -1,   106,    -1,    -1,    -1,    -1,    25,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   136,   137,   138,   139,    -1,    -1,    -1,   143,   144,
      59,    -1,    61,    -1,    -1,    -1,    65,    -1,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    86,    -1,    -1,
      89,    90,    -1,    -1,    93,    94,    95,    96,    97,    98,
      99,    14,    -1,    16,    17,    -1,    -1,   106,    -1,    -1,
      -1,    -1,    25,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   136,   137,   138,
     139,    -1,    -1,    -1,   143,   144,    59,    -1,    61,    -1,
      -1,    -1,    65,    -1,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    86,    -1,    -1,    89,    90,    -1,    -1,
      93,    94,    95,    96,    97,    98,    99,    14,    -1,    16,
      -1,    -1,    -1,   106,    -1,    -1,    -1,    -1,    25,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   136,   137,   138,   139,    -1,    -1,    -1,
     143,   144,    59,    -1,    61,    -1,    -1,    -1,    65,    -1,
      67,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    86,
      -1,    -1,    89,    90,    -1,    -1,    93,    94,    95,    96,
      97,    98,    99,     7,    -1,    -1,    -1,    -1,    -1,   106,
      14,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    36,    -1,    -1,    -1,    -1,    -1,    -1,   136,
     137,   138,   139,    -1,    -1,    -1,   143,   144,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    67,    68,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    92,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,    -1,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135
  };
  }


  static class YyTokenClass {
  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  public static final short
  yystos_[] =
  {
         0,     3,     5,    29,    30,    31,    47,    48,   181,   182,
     183,   184,   185,   211,   216,   220,   221,   222,   226,   244,
     248,   106,   106,   106,   212,   213,   215,   302,   303,   106,
     217,   218,   171,   172,   173,   174,   227,   228,   229,   229,
     245,   246,   229,   249,   250,   251,   252,     0,   183,   182,
     222,     7,     7,    32,   107,   150,   153,     7,   218,    32,
     106,    14,    89,    90,    93,   106,   143,   144,   240,   247,
     283,   284,   325,   328,   329,   330,    32,   106,   253,    32,
       9,   223,     9,    11,    12,    13,    14,    16,    32,   186,
     187,   214,   215,   303,   212,   106,   302,    14,    16,    34,
      38,    39,    40,    41,    42,   163,   164,   165,   166,   167,
     168,   169,   170,   177,   230,   231,   232,   233,   234,   236,
     237,   238,   239,   240,   302,   106,   154,   219,   228,     7,
      33,    14,    16,    25,    59,    61,    65,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    89,    90,    94,
      95,    96,    97,    98,    99,   106,   136,   137,   138,   139,
     282,   283,   287,   289,   292,   293,   296,   297,   298,   299,
     300,   301,   302,    14,    14,     7,   153,     7,   153,   245,
      14,   153,   256,   249,     6,    54,    58,   224,   225,   226,
     244,   248,   265,   277,     8,    10,   188,   189,   190,   225,
      25,    25,    25,   187,   214,    25,    25,    11,    12,    13,
      16,    28,    32,   146,   147,   159,   160,   161,   162,   204,
     205,   206,     8,    32,    15,   230,   235,   230,   106,   230,
     230,   230,   230,   230,    23,    36,    43,   106,   303,   230,
     106,   230,   241,   242,   282,    17,   281,   282,    26,    88,
     281,   282,   294,   295,   106,   247,   253,   285,   286,   282,
     282,   282,   282,   282,   282,   282,   282,   282,   282,   282,
     282,   282,   282,   282,   282,   282,   282,   282,   282,   282,
     282,    14,    14,    68,   106,   324,   325,   327,   333,   338,
     106,    14,   232,   302,    14,    14,   100,   324,   326,   334,
     335,   336,   337,   334,   324,   331,   332,   333,     7,    14,
      15,    36,    67,    68,    92,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   158,   326,    15,   326,   282,   230,   282,
     230,    15,   257,   326,   230,   237,   106,   259,   229,   267,
     268,   269,   266,   267,    46,   229,   278,   279,   280,     4,
     224,    11,    12,    14,    16,    18,    19,    20,    21,    22,
      28,    32,    59,    61,    65,    96,   105,   106,   146,   151,
     153,   194,   199,   202,   203,   302,   304,   305,   308,   309,
     313,   315,   318,   319,   320,   321,   322,   106,   175,   191,
     192,     8,   189,   214,   214,   214,    15,     8,   219,   214,
     187,   187,   187,   106,   219,   282,   187,   187,   219,   106,
     207,   208,   209,   187,   187,   282,   282,   187,   214,    23,
      43,    15,    27,    15,    27,    17,    35,    37,    37,   230,
     230,   230,    27,    91,   210,   155,    46,   243,    44,   153,
     242,   243,    15,    17,   150,    27,    26,    26,    27,    87,
      88,    26,   150,    27,   153,    60,   150,    62,   153,   281,
     282,   281,   153,   145,   150,    14,   282,    14,    14,   281,
     302,   120,   150,   153,   145,   150,   145,   120,   145,   282,
      15,   281,   282,   282,   282,   282,    93,   282,   282,   282,
     282,   282,   282,   282,   282,   282,   282,   282,   282,   282,
     282,   282,   282,   282,   282,   282,   282,   282,   282,   282,
     282,   282,   282,   282,   282,   282,    15,    15,     7,     7,
      15,   150,   153,   106,   153,    49,   150,   260,   261,   106,
      32,   282,   106,   312,    32,    25,    25,   101,   140,   141,
     142,   194,   200,   201,   214,   302,   310,    55,   207,   272,
     273,   323,    14,   214,    25,   285,   282,   282,    14,    32,
      23,   156,   157,   195,   196,   175,   175,   282,     4,    11,
      12,    13,    16,    28,    32,   146,   151,   159,   160,   161,
     162,   204,    14,    91,   102,     7,   106,   192,   194,     8,
       8,     8,    16,   106,    17,     8,    28,   113,   149,   210,
      27,   148,   150,    24,   230,   230,   230,   230,   241,   230,
     230,   334,   282,    27,    91,   324,   230,   230,   282,   324,
     332,   334,   282,   282,   282,   295,   334,   282,   286,   282,
     290,   291,   326,    15,    15,   230,   282,   333,   281,   150,
     282,   282,    15,   150,   282,   324,   230,   282,   335,   282,
     282,   282,    15,    87,   282,   282,   326,   230,    14,   254,
     230,   282,   106,    50,   263,   153,   256,   267,   153,   278,
     214,   214,   311,   312,   215,   215,   215,    15,     8,    32,
       8,   194,    56,    57,   274,   275,   276,    17,   260,    17,
     282,     8,   214,    60,    23,    62,   153,   282,   194,    89,
      90,   106,   197,   282,    23,   196,    27,   152,   145,   194,
     194,   194,   176,   219,   282,   302,   303,   194,   194,   176,
     219,   176,   194,   194,   282,   282,    15,   281,   106,   319,
     320,   193,   194,   214,     7,     4,   187,   187,   187,   303,
      15,   214,   187,   219,    14,    16,   187,   187,   334,   208,
     209,     4,     8,   155,   334,   282,    45,    17,   145,    26,
     145,    26,    26,   145,    63,    64,   288,     4,   291,    66,
      23,    15,   230,    15,    15,   282,   282,   153,    15,   326,
      14,    45,   153,   282,    14,   230,   270,   258,   259,   230,
       8,     8,     8,   150,   194,   201,   194,    15,   275,   303,
      14,   263,    15,    16,     8,   194,   194,   194,   316,   317,
     326,    15,    14,    14,   153,   194,   219,    14,   194,    27,
      28,    27,    23,    27,   149,    27,    15,     8,   176,    26,
      26,    26,    23,    14,     8,    26,    17,    25,     8,   148,
     282,     8,   155,   282,   282,   282,   282,   282,   282,    23,
     282,    15,    15,    15,   230,    15,   326,    52,    53,   255,
     282,   230,    15,   104,   106,   272,    60,   102,   194,   194,
     312,    15,    15,   153,   214,   175,   194,     4,    27,   306,
     307,    63,    64,   314,     4,   150,    23,    15,   197,   198,
      15,   198,   282,   149,   214,   219,   219,   113,   303,   176,
     219,   194,   176,   194,   303,   282,   187,   187,   282,   155,
     282,    17,    26,    26,    62,   282,    32,    15,   260,   104,
      14,   230,   254,   260,   282,   282,    26,    26,    14,    14,
     230,     8,    27,    26,   282,     4,   306,   282,   194,    63,
      64,    66,   316,   194,    15,   150,    15,    14,     8,    28,
      17,   194,    17,   149,    27,   152,    17,    15,    26,   148,
     155,   282,   262,   263,    14,   230,    15,    45,   263,   281,
     281,   194,   219,    23,    62,   282,   194,    23,   197,   214,
     194,   219,   194,   194,   176,   194,   288,    51,   264,    15,
      14,    16,    52,    53,   271,   304,   308,   309,    15,    15,
      15,    17,   194,   194,    62,   194,     8,    15,    27,   149,
     302,   260,   194,   194,   194,   176,   194,   262,    15,    17,
     194
  };
  }

  /* TOKEN_NUMBER_[YYLEX-NUM] -- Internal symbol number corresponding
     to YYLEX-NUM.  */
  static class YyTokenNum {
  public static final short
  yytoken_number_[] =
  {
         0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     315,   316,   317,   318,   319,   320,   321,   322,   323,   324,
     325,   326,   327,   328,   329,   330,   331,   332,   333,   334,
     335,   336,   337,   338,   339,   340,   341,   342,   343,   344,
     345,   346,   347,   348,   349,   350,   351,   352,   353,   354,
     355,   356,   357,   358,   359,   360,   361,   362,   363,   364,
     365,   366,   367,   368,   369,   370,   371,   372,   373,   374,
     375,   376,   377,   378,   379,   380,   381,   382,   383,   384,
     385,   386,   387,   388,   389,   390,   391,   392,   393,   394,
     395,   396,   397,   398,   399,   400,   401,   402,   403,   404,
     405,   406,   407,   408,   409,   410,   411,   412,   413,   414,
     415,   416,   417,   418,   419,   420,   421,   422,   423,   424,
     425,   426,   427,   428,   429,   430,   431,   432,   433,   434
  };
  }

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */

  private static final short
  yyr1_[] =
  {
         0,   180,   181,   181,   181,   182,   182,   183,   183,   183,
     183,   184,   185,   186,   186,   187,   187,   187,   187,   187,
     187,   187,   187,   187,   187,   187,   187,   187,   187,   187,
     187,   187,   187,   187,   187,   187,   187,   188,   188,   189,
     189,   190,   190,   191,   191,   192,   193,   193,   194,   194,
     194,   194,   194,   194,   194,   194,   194,   194,   194,   194,
     194,   194,   194,   194,   194,   194,   194,   194,   194,   194,
     194,   194,   194,   195,   195,   196,   196,   196,   197,   197,
     197,   197,   197,   198,   198,   199,   199,   199,   199,   199,
     199,   199,   199,   200,   200,   201,   201,   201,   202,   202,
     203,   203,   203,   203,   203,   203,   203,   204,   204,   205,
     206,   206,   207,   207,   208,   209,   209,   210,   210,   211,
     212,   212,   213,   213,   214,   214,   215,   216,   216,   217,
     217,   218,   219,   219,   219,   219,   219,   219,   220,   221,
     221,   222,   222,   222,   223,   224,   224,   225,   225,   225,
     225,   225,   225,   226,   226,   226,   227,   227,   228,   228,
     228,   228,   229,   229,   229,   229,   229,   230,   230,   230,
     230,   230,   230,   230,   230,   230,   230,   230,   230,   230,
     230,   230,   231,   232,   232,   232,   232,   232,   232,   232,
     232,   233,   234,   235,   235,   236,   237,   237,   238,   238,
     239,   239,   240,   241,   241,   242,   242,   242,   243,   244,
     245,   245,   245,   246,   247,   247,   247,   247,   248,   248,
     249,   249,   249,   250,   250,   251,   252,   253,   254,   254,
     254,   255,   255,   255,   256,   256,   257,   257,   258,   258,
     259,   259,   260,   260,   261,   262,   262,   263,   264,   264,
     265,   265,   266,   266,   267,   267,   268,   269,   270,   270,
     270,   270,   271,   271,   271,   271,   271,   272,   272,   273,
     274,   274,   275,   275,   276,   276,   277,   277,   278,   278,
     278,   279,   279,   280,   281,   281,   282,   282,   282,   282,
     282,   282,   282,   282,   282,   282,   282,   282,   282,   282,
     282,   282,   282,   282,   282,   282,   282,   282,   282,   282,
     282,   282,   282,   282,   282,   282,   282,   282,   282,   282,
     282,   282,   282,   282,   283,   283,   284,   284,   285,   285,
     286,   286,   287,   288,   288,   289,   290,   290,   290,   291,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     293,   293,   293,   293,   293,   293,   293,   293,   293,   293,
     293,   293,   293,   293,   293,   293,   293,   293,   293,   293,
     293,   293,   293,   293,   293,   293,   293,   293,   293,   293,
     293,   293,   293,   294,   294,   295,   296,   297,   298,   299,
     300,   300,   300,   301,   302,   302,   303,   303,   304,   304,
     304,   304,   304,   304,   305,   305,   306,   307,   307,   308,
     309,   309,   310,   311,   311,   312,   312,   312,   313,   313,
     314,   314,   315,   316,   316,   316,   317,   318,   318,   319,
     319,   320,   320,   320,   320,   320,   321,   321,   322,   323,
     324,   324,   325,   325,   325,   326,   326,   327,   327,   328,
     328,   329,   330,   330,   331,   331,   332,   333,   334,   334,
     335,   335,   336,   337,   338,   338
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     2,     1,     1,     2,     1,     1,     1,
       1,     4,     4,     3,     1,     5,     4,     3,     3,     3,
       5,     7,     3,     3,     3,     3,     5,     3,     3,     8,
       2,     8,     6,     6,     6,     8,     6,     1,     2,     1,
       1,     2,     5,     1,     2,     3,     1,     3,     1,     1,
       1,     1,     4,     3,     4,     4,     3,     3,     3,     3,
       3,     6,     3,     3,     2,     1,     5,     1,     1,     6,
       1,     1,     1,     1,     2,     2,     4,     2,     1,     4,
       3,     4,     3,     1,     3,     7,     3,     7,     3,    11,
       7,     9,     5,     1,     3,     2,     2,     2,     8,     8,
       6,     6,     6,     8,    10,     9,     8,     1,     1,     3,
       5,     7,     1,     3,     3,     1,     2,     2,     3,     2,
       1,     3,     1,     1,     1,     3,     3,     1,     2,     1,
       2,     3,     1,     3,     5,     6,     7,     8,     1,     1,
       2,     1,     1,     1,     3,     1,     2,     1,     1,     1,
       1,     1,     2,     1,     3,     2,     1,     3,     5,     4,
       4,     5,     1,     1,     1,     1,     0,     1,     1,     1,
       5,     3,     1,     1,     2,     2,     2,     4,     4,     1,
       1,     1,     3,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     3,     3,     3,     3,     1,     1,     3,     4,
       3,     4,     1,     1,     2,     1,     3,     3,     4,     2,
       1,     2,     3,     2,     5,     5,     3,     3,     1,     2,
       1,     2,     3,     1,     1,     6,     2,    10,     2,     3,
       4,     1,     1,     1,     2,     3,     3,     5,     0,     1,
       3,     5,     1,     0,     2,     1,     0,     2,     2,     0,
       2,     1,     1,     3,     1,     1,    10,     7,     3,     4,
       4,     5,     1,     1,     1,     1,     1,     1,     0,     2,
       1,     2,     2,     4,     1,     1,     2,     1,     1,     2,
       3,     2,     1,     2,     1,     3,     1,     3,     4,     1,
       1,     1,     1,     4,     4,     4,     2,     3,     5,     7,
       5,     2,     3,     5,     7,     6,     3,     3,     5,     7,
       1,     1,     3,     4,     3,     1,     1,     1,     1,     1,
       6,     1,     2,     1,     1,     1,     1,     1,     1,     3,
       1,     1,     5,     2,     5,     5,     1,     4,     2,     4,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     1,     3,     3,     4,     4,     4,     5,
       5,     5,     6,     4,     1,     3,     1,     3,     1,     1,
       1,     1,     1,     1,     5,     6,     4,     1,     2,     4,
       3,     4,     3,     1,     3,     3,     5,     5,     7,     6,
       4,     5,     5,     1,     5,     3,     3,     1,     3,     3,
       4,     1,     1,     3,     3,     4,     2,     4,     3,     3,
       1,     1,     1,     1,     1,     1,     3,     1,     1,     1,
       3,     4,     3,     4,     1,     1,     3,     3,     1,     3,
       1,     1,     3,     3,     1,     3
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "CLASS", "END", "PROCESS", "INITIAL",
  "EQUALS", "AT", "BEGIN", "ACTIONS", "BARTILDEBAR", "LRSQUARE", "TBAR",
  "LPAREN", "RPAREN", "LSQUARE", "RSQUARE", "CSPSKIP", "CSPSTOP",
  "CSPCHAOS", "CSPDIV", "CSPWAIT", "RARROW", "LARROW", "LCURLY", "RCURLY",
  "BAR", "DBAR", "CHANNELS", "CHANSETS", "TYPES", "SEMI", "DCOLON",
  "COMPOSE", "OF", "STAR", "TO", "INMAPOF", "MAPOF", "SEQOF", "SEQ1OF",
  "SETOF", "PLUSGT", "COLONDASH", "DEQUALS", "INV", "VALUES", "FUNCTIONS",
  "PRE", "POST", "MEASURE", "SUBCLASSRESP", "NOTYETSPEC", "OPERATIONS",
  "FRAME", "RD", "WR", "STATE", "LET", "IN", "IF", "THEN", "ELSEIF",
  "ELSE", "CASES", "OTHERS", "PLUS", "MINUS", "ABS", "FLOOR", "NOT",
  "CARD", "POWER", "DUNION", "DINTER", "HD", "TL", "LEN", "ELEMS", "INDS",
  "REVERSE", "CONC", "DOM", "RNG", "MERGE", "INVERSE", "ELLIPSIS",
  "BARRARROW", "MKUNDER", "MKUNDERNAME", "DOT", "DOTHASH", "NUMERAL",
  "LAMBDA", "NEW", "SELF", "ISUNDER", "PREUNDER", "ISOFCLASS", "TILDE",
  "DCL", "COLONEQUALS", "ATOMIC", "DEQRARROW", "RETURN", "IDENTIFIER",
  "BACKTICK", "DIVIDE", "REM", "MOD", "LT", "LTE", "GT", "GTE", "NEQ",
  "OR", "AND", "EQRARROW", "LTEQUALSGT", "INSET", "NOTINSET", "SUBSET",
  "PROPER_SUBSET", "UNION", "BACKSLASH", "INTER", "CARET", "DPLUS",
  "MAPMERGE", "LTCOLON", "LTDASHCOLON", "COLONGT", "COLONDASHGT", "COMP",
  "DSTAR", "FORALL", "EXISTS", "EXISTS1", "STRING", "VRES", "RES", "VAL",
  "HEX_LITERAL", "QUOTE_LITERAL", "AMP", "LSQUAREBAR", "DLSQUARE",
  "DRSQUARE", "BARRSQUARE", "COMMA", "LSQUAREDBAR", "DBARRSQUARE", "COLON",
  "LCURLYBAR", "BARRCURLY", "QUESTION", "BANG", "SLASH", "SLASHBACKSLASH",
  "LSQUAREGT", "ENDBY", "STARTBY", "TBOOL", "TNAT", "TNAT1", "TINT",
  "TRAT", "TREAL", "TCHAR", "TTOKEN", "PRIVATE", "PROTECTED", "PUBLIC",
  "LOGICAL", "nameset", "namesetExpr", "typeVarIdentifier", "UMINUS",
  "UPLUS", "$accept", "sourceFile", "programParagraphList",
  "programParagraph", "classDecl", "processDecl", "processDef", "process",
  "processParagraphList", "processParagraph", "actionParagraph",
  "actionDefinitionList", "actionDefinition", "paragraphAction", "action",
  "communicationParameterList", "communicationParameter", "parameter",
  "paramList", "parallelAction", "parametrisationList", "parametrisation",
  "instantiatedAction", "replicatedAction", "renameExpression",
  "renameEnumeration", "renameComprehension", "renameList", "renamePair",
  "channelEvent", "dotted_expression", "channelDecl", "channelDef",
  "channelNameDecl", "declaration", "singleTypeDecl", "chansetDecl",
  "chansetDefinitionList", "chansetDefinition", "chansetExpr",
  "globalDecl", "globalDefinitionBlock",
  "globalDefinitionBlockAlternative", "classBody", "classDefinitionBlock",
  "classDefinitionBlockAlternative", "typeDefs", "typeDefList", "typeDef",
  "qualifier", "type", "bracketedType", "basicType", "quoteType",
  "optionalType", "unionType", "productType", "functionType",
  "partialFunctionType", "totalFunctionType", "quoteLiteral", "fieldList",
  "field", "invariant", "valueDefs", "valueDefList", "qualifiedValueDef",
  "valueDef", "functionDefs", "functionDefList", "functionDef",
  "implicitFunctionDef", "qualifiedExplicitFunctionDef",
  "explicitFunctionDef", "parameterList", "functionBody", "parameterTypes",
  "patternListTypeList", "identifierTypePairList_opt",
  "identifierTypePairList", "preExpr_opt", "preExpr", "postExpr_opt",
  "postExpr", "measureExpr", "operationDefs", "operationDefList",
  "operationDef", "explicitOperationDef", "implicitOperationDef",
  "operationType", "operationBody", "externals_opt", "externals",
  "varInformationList", "varInformation", "mode", "stateDefs",
  "stateDefList", "stateDef", "invariantDef", "expressionList",
  "expression", "symbolicLiteral", "numericLiteral", "localDefList",
  "localDef", "ifExpr", "elseExprs", "casesExpr", "casesExprAltList",
  "casesExprAlt", "unaryExpr", "binaryExpr", "mapletList", "maplet",
  "tupleConstructor", "recordConstructor", "lambdaExpr", "newExpr",
  "generalIsExpr", "preconditionExpr", "name", "nameList",
  "controlStatement", "nonDeterministicIfStatement",
  "nonDeterministicIfAlt", "nonDeterministicIfAltList", "letStatement",
  "blockStatement", "dclStatement", "assignmentDefList", "assignmentDef",
  "ifStatement", "elseStatements", "casesStatement",
  "casesStatementAltList", "casesStatementAlt", "callStatement", "call",
  "objectDesignator", "returnStatement", "specificationStatement",
  "implicitOperationBody", "pattern", "patternLessID", "patternList",
  "patternIdentifier", "matchValue", "tuplePattern", "recordPattern",
  "bind", "setBind", "typeBind", "bindList", "multipleBind",
  "multipleSetBind", "multipleTypeBind", "typeBindList", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final short yyrhs_[] =
  {
       181,     0,    -1,   182,    -1,   220,   182,    -1,   220,    -1,
     183,    -1,   182,   183,    -1,   184,    -1,   185,    -1,   211,
      -1,   216,    -1,     3,   106,     7,   223,    -1,     5,   106,
       7,   186,    -1,   214,     8,   187,    -1,   187,    -1,     9,
     188,     8,   194,     4,    -1,     9,     8,   194,     4,    -1,
     187,    32,   187,    -1,   187,    12,   187,    -1,   187,    11,
     187,    -1,   187,   146,   219,   149,   187,    -1,   187,    16,
     219,    28,   219,    17,   187,    -1,   187,    28,   187,    -1,
     187,    13,   187,    -1,   187,   159,   187,    -1,   187,   160,
     187,    -1,   187,    16,   282,   113,   187,    -1,   187,   162,
     282,    -1,   187,   161,   282,    -1,    14,   214,     8,   106,
      15,    14,   282,    15,    -1,   187,   204,    -1,    14,   187,
      15,    16,   303,    23,   303,    17,    -1,    32,    25,   214,
       8,   187,    26,    -1,    11,    25,   214,     8,   187,    26,
      -1,    12,    25,   214,     8,   187,    26,    -1,    16,    25,
     219,    17,   214,     8,   187,    26,    -1,    13,    25,   214,
       8,   187,    26,    -1,   189,    -1,   188,   189,    -1,   225,
      -1,   190,    -1,    10,   191,    -1,    10,   175,   106,     7,
     176,    -1,   192,    -1,   191,   192,    -1,   106,     7,   193,
      -1,   194,    -1,   214,     8,   194,    -1,    18,    -1,    19,
      -1,    20,    -1,    21,    -1,    22,    14,   282,    15,    -1,
     106,    23,   194,    -1,   106,   195,    23,   194,    -1,   153,
     282,   145,   194,    -1,   194,    32,   194,    -1,   194,    12,
     194,    -1,   194,    11,   194,    -1,   194,   159,   194,    -1,
     194,   160,   194,    -1,   194,    16,   282,    27,   113,   194,
      -1,   194,   162,   282,    -1,   194,   161,   282,    -1,   194,
     204,    -1,   199,    -1,    14,   200,     8,   194,    15,    -1,
     202,    -1,   203,    -1,   194,    16,   303,    23,   303,    17,
      -1,   308,    -1,   309,    -1,   304,    -1,   196,    -1,   195,
     196,    -1,   156,   197,    -1,   156,   197,   153,   282,    -1,
     157,   282,    -1,   106,    -1,    89,    14,   198,    15,    -1,
      89,    14,    15,    -1,    90,    14,   198,    15,    -1,    90,
      14,    15,    -1,   197,    -1,   198,   150,   197,    -1,   194,
     151,   176,    27,   176,   152,   194,    -1,   194,    13,   194,
      -1,   194,   146,   176,    27,   176,   149,   194,    -1,   194,
      28,   194,    -1,   194,    16,   176,    27,   219,    28,   219,
      27,   176,    17,   194,    -1,   194,    16,   219,    28,   219,
      17,   194,    -1,   194,   146,   176,    27,   219,    27,   176,
     149,   194,    -1,   194,   146,   219,   149,   194,    -1,   201,
      -1,   200,    32,   201,    -1,   142,   215,    -1,   141,   215,
      -1,   140,   215,    -1,    14,   214,     8,   194,    15,    14,
     281,    15,    -1,    14,   200,     8,   194,    15,    14,   281,
      15,    -1,    32,    25,   214,     8,   194,    26,    -1,    11,
      25,   214,     8,   194,    26,    -1,    12,    25,   214,     8,
     194,    26,    -1,   151,   175,   152,    14,   214,     8,   194,
      15,    -1,   146,   175,    27,   219,   149,    14,   214,     8,
     194,    15,    -1,    28,   214,     8,    16,   175,    27,   219,
      17,   194,    -1,    16,   207,    17,    14,   214,     8,   194,
      15,    -1,   205,    -1,   206,    -1,   147,   207,   148,    -1,
     147,   207,    27,   334,   148,    -1,   147,   207,    27,   334,
       8,   282,   148,    -1,   208,    -1,   207,   150,   208,    -1,
     209,    24,   209,    -1,   106,    -1,   106,   210,    -1,    91,
     282,    -1,   210,    91,   282,    -1,    29,   212,    -1,   213,
      -1,   213,    32,   212,    -1,   303,    -1,   215,    -1,   215,
      -1,   215,    32,   214,    -1,   303,   153,   230,    -1,    30,
      -1,    30,   217,    -1,   218,    -1,   217,   218,    -1,   106,
       7,   219,    -1,   106,    -1,   154,   303,   155,    -1,   154,
     106,    27,   334,   155,    -1,   154,   106,   210,    27,   334,
     155,    -1,   154,   106,    27,   334,     8,   282,   155,    -1,
     154,   106,   210,    27,   334,     8,   282,   155,    -1,   221,
      -1,   222,    -1,   221,   222,    -1,   226,    -1,   244,    -1,
     248,    -1,     9,   224,     4,    -1,   225,    -1,   225,   224,
      -1,   226,    -1,   244,    -1,   248,    -1,   265,    -1,   277,
      -1,     6,   267,    -1,    31,    -1,    31,   227,    32,    -1,
      31,   227,    -1,   228,    -1,   227,    32,   228,    -1,   229,
     106,     7,   230,   243,    -1,   229,   106,     7,   230,    -1,
     229,   106,    33,   241,    -1,   229,   106,    33,   241,   243,
      -1,   171,    -1,   172,    -1,   173,    -1,   174,    -1,    -1,
     231,    -1,   232,    -1,   233,    -1,    34,   106,    35,   241,
       4,    -1,    14,   235,    15,    -1,   236,    -1,   234,    -1,
      42,   230,    -1,    40,   230,    -1,    41,   230,    -1,    39,
     230,    37,   230,    -1,    38,   230,    37,   230,    -1,   237,
      -1,   302,    -1,   177,    -1,    14,   230,    15,    -1,   163,
      -1,   164,    -1,   165,    -1,   166,    -1,   167,    -1,   168,
      -1,   169,    -1,   170,    -1,   240,    -1,    16,   230,    17,
      -1,   230,    27,   230,    -1,   235,    27,   230,    -1,   230,
      36,   230,    -1,   238,    -1,   239,    -1,   230,    43,   230,
      -1,    14,    15,    43,   230,    -1,   230,    23,   230,    -1,
      14,    15,    23,   230,    -1,   144,    -1,   242,    -1,   241,
     242,    -1,   230,    -1,   106,   153,   230,    -1,   106,    44,
     230,    -1,    46,   324,    45,   282,    -1,    47,   245,    -1,
     246,    -1,   246,    32,    -1,   246,    32,   245,    -1,   229,
     247,    -1,   106,   153,   230,     7,   282,    -1,   325,   153,
     230,     7,   282,    -1,   106,     7,   282,    -1,   325,     7,
     282,    -1,    48,    -1,    48,   249,    -1,   250,    -1,   250,
      32,    -1,   250,    32,   249,    -1,   251,    -1,   252,    -1,
     229,   106,   256,   259,   260,   263,    -1,   229,   253,    -1,
     106,   153,   237,   106,   254,    45,   255,   260,   262,   264,
      -1,    14,    15,    -1,    14,   326,    15,    -1,   254,    14,
     326,    15,    -1,   282,    -1,    52,    -1,    53,    -1,    14,
      15,    -1,    14,   257,    15,    -1,   326,   153,   230,    -1,
     257,   150,   326,   153,   230,    -1,    -1,   259,    -1,   106,
     153,   230,    -1,   259,   150,   106,   153,   230,    -1,   261,
      -1,    -1,    49,   282,    -1,   263,    -1,    -1,    50,   282,
      -1,    51,   302,    -1,    -1,    54,   266,    -1,    54,    -1,
     267,    -1,   266,    32,   267,    -1,   269,    -1,   268,    -1,
     229,   106,   153,   270,   106,   254,    45,   271,   260,   262,
      -1,   229,   106,   256,   258,   272,   260,   263,    -1,   230,
     104,   230,    -1,    14,    15,   104,   230,    -1,   230,   104,
      14,    15,    -1,    14,    15,   104,    14,    15,    -1,   308,
      -1,   309,    -1,   304,    -1,    52,    -1,    53,    -1,   273,
      -1,    -1,    55,   274,    -1,   275,    -1,   274,   275,    -1,
     276,   303,    -1,   276,   303,   153,   230,    -1,    56,    -1,
      57,    -1,    58,   278,    -1,    58,    -1,   279,    -1,   279,
      32,    -1,   279,    32,   278,    -1,   229,   312,    -1,   280,
      -1,    46,   282,    -1,   282,    -1,   281,   150,   282,    -1,
     139,    -1,    14,   282,    15,    -1,    59,   285,    60,   282,
      -1,   287,    -1,   289,    -1,   292,    -1,   293,    -1,   136,
     334,   145,   282,    -1,   137,   334,   145,   282,    -1,   138,
     331,   145,   282,    -1,    25,    26,    -1,    25,   281,    26,
      -1,    25,   282,    27,   334,    26,    -1,    25,   282,    27,
     334,   145,   282,    26,    -1,    25,   282,    87,   282,    26,
      -1,    16,    17,    -1,    16,   281,    17,    -1,    16,   282,
      27,   332,    17,    -1,    16,   282,    27,   332,   145,   282,
      17,    -1,   282,    14,   282,    87,   282,    15,    -1,    25,
      88,    26,    -1,    25,   294,    26,    -1,    25,   295,    27,
     334,    26,    -1,    25,   295,    27,   334,   145,   282,    26,
      -1,   296,    -1,   297,    -1,   282,    14,    15,    -1,   282,
      14,   281,    15,    -1,   282,    92,    93,    -1,   298,    -1,
     299,    -1,    96,    -1,   300,    -1,   301,    -1,    99,    14,
     302,   150,   282,    15,    -1,   302,    -1,   106,   100,    -1,
     283,    -1,   284,    -1,   240,    -1,    93,    -1,   143,    -1,
     286,    -1,   285,   150,   286,    -1,   247,    -1,   253,    -1,
      61,   282,    62,   282,   288,    -1,    64,   282,    -1,    63,
     282,    62,   282,   288,    -1,    65,   282,   153,   290,     4,
      -1,   291,    -1,   291,    66,    23,   282,    -1,   290,   291,
      -1,   326,    23,   282,    32,    -1,    67,   282,    -1,    68,
     282,    -1,    69,   282,    -1,    70,   282,    -1,    71,   282,
      -1,    72,   282,    -1,    73,   282,    -1,    74,   282,    -1,
      75,   282,    -1,    76,   282,    -1,    77,   282,    -1,    78,
     282,    -1,    79,   282,    -1,    80,   282,    -1,    81,   282,
      -1,    82,   282,    -1,    83,   282,    -1,    84,   282,    -1,
      85,   282,    -1,    86,   282,    -1,   282,    67,   282,    -1,
     282,    36,   282,    -1,   282,    68,   282,    -1,   282,   108,
     282,    -1,   282,   158,   282,    -1,   282,   109,   282,    -1,
     282,   110,   282,    -1,   282,   111,   282,    -1,   282,   112,
     282,    -1,   282,   113,   282,    -1,   282,   114,   282,    -1,
     282,     7,   282,    -1,   282,   115,   282,    -1,   282,   116,
     282,    -1,   282,   117,   282,    -1,   282,   118,   282,    -1,
     282,   119,   282,    -1,   282,   120,   282,    -1,   282,   121,
     282,    -1,   282,   122,   282,    -1,   282,   123,   282,    -1,
     282,   124,   282,    -1,   282,   125,   282,    -1,   282,   126,
     282,    -1,   282,   127,   282,    -1,   282,   128,   282,    -1,
     282,   129,   282,    -1,   282,   130,   282,    -1,   282,   131,
     282,    -1,   282,   132,   282,    -1,   282,   133,   282,    -1,
     282,   134,   282,    -1,   282,   135,   282,    -1,   295,    -1,
     294,   150,   295,    -1,   282,    88,   282,    -1,    89,    14,
     281,    15,    -1,    90,    14,   281,    15,    -1,    94,   338,
     145,   282,    -1,    95,   106,    14,   281,    15,    -1,    97,
     302,    14,   282,    15,    -1,    97,   232,    14,   282,    15,
      -1,    97,    14,   282,   150,   230,    15,    -1,    98,    14,
     281,    15,    -1,   106,    -1,   302,   107,   106,    -1,   302,
      -1,   303,   150,   302,    -1,   305,    -1,   313,    -1,   315,
      -1,   318,    -1,   322,    -1,   321,    -1,    61,   282,    23,
     194,     4,    -1,    61,   282,    23,   194,   307,     4,    -1,
      27,   282,    23,   194,    -1,   306,    -1,   307,   306,    -1,
      59,   285,    60,   194,    -1,    14,   194,    15,    -1,    14,
     310,   194,    15,    -1,   101,   311,     8,    -1,   312,    -1,
     311,   150,   312,    -1,   106,   153,   230,    -1,   106,   153,
     230,   102,   282,    -1,   106,   153,   230,    60,   282,    -1,
      61,   282,    62,   194,   314,    64,   194,    -1,    61,   282,
      62,   194,    64,   194,    -1,    63,   282,    62,   194,    -1,
     314,    63,   282,    62,   194,    -1,    65,   282,   153,   316,
       4,    -1,   317,    -1,   317,   150,    66,    23,   194,    -1,
     317,   150,   316,    -1,   326,    23,   194,    -1,   319,    -1,
     320,   102,   319,    -1,   320,    14,    15,    -1,   320,    14,
     281,    15,    -1,    96,    -1,   302,    -1,   320,    91,   106,
      -1,   320,    14,    15,    -1,   320,    14,   281,    15,    -1,
     105,    32,    -1,   105,    14,   282,    15,    -1,    16,   323,
      17,    -1,   272,   260,   263,    -1,   327,    -1,   325,    -1,
     328,    -1,   329,    -1,   330,    -1,   324,    -1,   326,   150,
     324,    -1,   106,    -1,    68,    -1,   283,    -1,    14,   282,
      15,    -1,    89,    14,   326,    15,    -1,    90,    14,    15,
      -1,    90,    14,   326,    15,    -1,   332,    -1,   333,    -1,
     324,   120,   282,    -1,   324,   153,   230,    -1,   335,    -1,
     334,   150,   335,    -1,   336,    -1,   337,    -1,   326,   120,
     282,    -1,   326,   153,   230,    -1,   333,    -1,   338,   150,
     333,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     8,    10,    12,    15,    17,    19,
      21,    23,    28,    33,    37,    39,    45,    50,    54,    58,
      62,    68,    76,    80,    84,    88,    92,    98,   102,   106,
     115,   118,   127,   134,   141,   148,   157,   164,   166,   169,
     171,   173,   176,   182,   184,   187,   191,   193,   197,   199,
     201,   203,   205,   210,   214,   219,   224,   228,   232,   236,
     240,   244,   251,   255,   259,   262,   264,   270,   272,   274,
     281,   283,   285,   287,   289,   292,   295,   300,   303,   305,
     310,   314,   319,   323,   325,   329,   337,   341,   349,   353,
     365,   373,   383,   389,   391,   395,   398,   401,   404,   413,
     422,   429,   436,   443,   452,   463,   473,   482,   484,   486,
     490,   496,   504,   506,   510,   514,   516,   519,   522,   526,
     529,   531,   535,   537,   539,   541,   545,   549,   551,   554,
     556,   559,   563,   565,   569,   575,   582,   590,   599,   601,
     603,   606,   608,   610,   612,   616,   618,   621,   623,   625,
     627,   629,   631,   634,   636,   640,   643,   645,   649,   655,
     660,   665,   671,   673,   675,   677,   679,   680,   682,   684,
     686,   692,   696,   698,   700,   703,   706,   709,   714,   719,
     721,   723,   725,   729,   731,   733,   735,   737,   739,   741,
     743,   745,   747,   751,   755,   759,   763,   765,   767,   771,
     776,   780,   785,   787,   789,   792,   794,   798,   802,   807,
     810,   812,   815,   819,   822,   828,   834,   838,   842,   844,
     847,   849,   852,   856,   858,   860,   867,   870,   881,   884,
     888,   893,   895,   897,   899,   902,   906,   910,   916,   917,
     919,   923,   929,   931,   932,   935,   937,   938,   941,   944,
     945,   948,   950,   952,   956,   958,   960,   971,   979,   983,
     988,   993,   999,  1001,  1003,  1005,  1007,  1009,  1011,  1012,
    1015,  1017,  1020,  1023,  1028,  1030,  1032,  1035,  1037,  1039,
    1042,  1046,  1049,  1051,  1054,  1056,  1060,  1062,  1066,  1071,
    1073,  1075,  1077,  1079,  1084,  1089,  1094,  1097,  1101,  1107,
    1115,  1121,  1124,  1128,  1134,  1142,  1149,  1153,  1157,  1163,
    1171,  1173,  1175,  1179,  1184,  1188,  1190,  1192,  1194,  1196,
    1198,  1205,  1207,  1210,  1212,  1214,  1216,  1218,  1220,  1222,
    1226,  1228,  1230,  1236,  1239,  1245,  1251,  1253,  1258,  1261,
    1266,  1269,  1272,  1275,  1278,  1281,  1284,  1287,  1290,  1293,
    1296,  1299,  1302,  1305,  1308,  1311,  1314,  1317,  1320,  1323,
    1326,  1330,  1334,  1338,  1342,  1346,  1350,  1354,  1358,  1362,
    1366,  1370,  1374,  1378,  1382,  1386,  1390,  1394,  1398,  1402,
    1406,  1410,  1414,  1418,  1422,  1426,  1430,  1434,  1438,  1442,
    1446,  1450,  1454,  1458,  1460,  1464,  1468,  1473,  1478,  1483,
    1489,  1495,  1501,  1508,  1513,  1515,  1519,  1521,  1525,  1527,
    1529,  1531,  1533,  1535,  1537,  1543,  1550,  1555,  1557,  1560,
    1565,  1569,  1574,  1578,  1580,  1584,  1588,  1594,  1600,  1608,
    1615,  1620,  1626,  1632,  1634,  1640,  1644,  1648,  1650,  1654,
    1658,  1663,  1665,  1667,  1671,  1675,  1680,  1683,  1688,  1692,
    1696,  1698,  1700,  1702,  1704,  1706,  1708,  1712,  1714,  1716,
    1718,  1722,  1727,  1731,  1736,  1738,  1740,  1744,  1748,  1750,
    1754,  1756,  1758,  1762,  1766,  1768
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   361,   361,   367,   374,   382,   390,   403,   404,   405,
     406,   411,   443,   454,   467,   480,   487,   494,   503,   512,
     521,   531,   542,   551,   560,   582,   592,   614,   624,   638,
     666,   676,   677,   678,   679,   680,   681,   719,   726,   739,
     743,   750,   760,   764,   771,   781,   800,   804,   811,   816,
     821,   826,   831,   840,   847,   858,   864,   871,   878,   885,
     900,   908,   923,   930,   937,   948,   952,   953,   957,   958,
     959,   960,   961,   972,   979,   993,  1000,  1008,  1026,  1031,
    1037,  1043,  1052,  1064,  1070,  1079,  1080,  1081,  1082,  1083,
    1084,  1085,  1086,  1094,  1095,  1099,  1100,  1101,  1105,  1112,
    1117,  1118,  1119,  1120,  1121,  1122,  1123,  1127,  1131,  1135,
    1144,  1151,  1161,  1168,  1177,  1186,  1194,  1205,  1211,  1223,
    1240,  1247,  1256,  1270,  1282,  1288,  1297,  1309,  1314,  1326,
    1332,  1342,  1359,  1371,  1401,  1409,  1417,  1426,  1440,  1447,
    1455,  1465,  1471,  1476,  1487,  1494,  1503,  1513,  1517,  1521,
    1527,  1533,  1537,  1543,  1552,  1562,  1575,  1581,  1590,  1611,
    1628,  1650,  1677,  1685,  1693,  1701,  1707,  1716,  1720,  1724,
    1728,  1729,  1733,  1737,  1741,  1754,  1765,  1776,  1790,  1805,
    1809,  1814,  1821,  1828,  1832,  1836,  1840,  1844,  1848,  1852,
    1856,  1863,  1871,  1882,  1899,  1908,  1930,  1934,  1941,  1956,
    1975,  1990,  2029,  2039,  2045,  2054,  2058,  2065,  2072,  2084,
    2127,  2134,  2144,  2157,  2169,  2195,  2215,  2238,  2262,  2269,
    2282,  2288,  2294,  2303,  2307,  2314,  2342,  2352,  2394,  2399,
    2406,  2416,  2420,  2424,  2431,  2435,  2442,  2451,  2464,  2467,
    2474,  2485,  2499,  2504,  2510,  2517,  2522,  2528,  2535,  2539,
    2545,  2557,  2567,  2574,  2586,  2590,  2597,  2607,  2645,  2646,
    2647,  2648,  2652,  2653,  2654,  2655,  2659,  2668,  2673,  2679,
    2686,  2693,  2702,  2706,  2713,  2717,  2745,  2756,  2763,  2771,
    2779,  2788,  2792,  2799,  2815,  2821,  2841,  2866,  2871,  2878,
    2882,  2886,  2890,  2895,  2907,  2919,  2932,  2942,  2949,  2962,
    2978,  2991,  3002,  3015,  3027,  3042,  3054,  3063,  3074,  3086,
    3100,  3104,  3109,  3116,  3126,  3135,  3139,  3143,  3147,  3151,
    3155,  3159,  3166,  3171,  3175,  3186,  3194,  3200,  3209,  3215,
    3225,  3230,  3239,  3274,  3289,  3312,  3332,  3342,  3359,  3372,
    3405,  3413,  3421,  3429,  3437,  3445,  3453,  3461,  3469,  3477,
    3485,  3493,  3501,  3509,  3517,  3525,  3533,  3541,  3549,  3557,
    3574,  3579,  3584,  3589,  3594,  3599,  3604,  3609,  3614,  3619,
    3624,  3629,  3634,  3639,  3644,  3649,  3654,  3659,  3664,  3669,
    3674,  3679,  3684,  3689,  3694,  3699,  3704,  3710,  3716,  3722,
    3728,  3734,  3740,  3964,  3970,  3980,  4024,  4042,  4118,  4140,
    4160,  4175,  4191,  4212,  4236,  4244,  4254,  4261,  4274,  4278,
    4282,  4284,  4293,  4294,  4307,  4315,  4326,  4338,  4345,  4357,
    4366,  4374,  4386,  4394,  4401,  4414,  4430,  4431,  4466,  4476,
    4489,  4501,  4521,  4532,  4545,  4557,  4566,  4583,  4587,  4603,
    4604,  4651,  4656,  4661,  4662,  4663,  4675,  4676,  4689,  4693,
    4701,  4702,  4706,  4707,  4708,  4712,  4718,  4727,  4736,  4741,
    4755,  4763,  4768,  4779,  4802,  4806,  4813,  4823,  4833,  4839,
    4848,  4852,  4856,  4866,  4876,  4883
  };

  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
	      + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
		       yyrhs_[yyprhs_[yyrule] + yyi],
		       ((yystack.valueAt (yynrhs-(yyi + 1)))), 
		       yystack.locationAt (yynrhs-(yyi + 1)));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final short yytranslate_table_[] =
  {
         0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    86,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,   144,
     145,   146,   147,   148,   149,   150,   151,   152,   153,   154,
     155,   156,   157,   158,   159,   160,   161,   162,   163,   164,
     165,   166,   167,   168,   169,   170,   171,   172,   173,   174,
     175,   176,   177,   178,   179
  };

  private static final short yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 5961;
  private static final int yynnts_ = 159;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 47;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 180;

  private static final int yyuser_token_number_max_ = 434;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 898 of cmlskeleton.java.m4  */
/* Line 38 of "src/main/bison/cml.y"  */

 // **************************
  // *** PARSER PUBLIC INFO ***
  // **************************
  public static class Info {
    public static final String CML_LANG_VERSION = "CML 0";
  };

  public class CustomSyntaxErrorException extends RuntimeException
  {

  };

   // **************************
  // *** PARSER INTERNAL DS ***
  // **************************
  class PatternWithVDMType {
    public PType type;
    public PPattern pattern;
  };

    // ************************
    // *** MEMBER VARIABLES ***
    // ************************

    private ASourcefileSourcefile currentSourceFile = null;

    // *************************
    // *** PRIVATE OPERATIONS ***
    // *************************

    PStateDesignator convertToStateDesignator(PDesignator designator)
    {
	PStateDesignator sd = null;
	if(designator instanceof ANameDesignator){
	    ANameDesignator ns = (ANameDesignator)designator;
	    LexNameToken name = extractLexNameToken(ns.getName());
	    
	    sd = new AIdentifierStateDesignator(name.getLocation(), 
						null, 
						name); 
	}
	else if (designator instanceof APrimaryDesignator){
	    APrimaryDesignator primary = (APrimaryDesignator)designator;
	    
	    if(primary.getPrimary() instanceof ACallPrimary){
		ACallCallStatementControlStatementAction call = 
		    (ACallCallStatementControlStatementAction) ((ACallPrimary)primary.getPrimary()).getCall();
		if(call.getArgs().size() != 1)
		    throw new RuntimeException("Missing implementation in convertToStateDesignator method. Throw nice error about map ot sequence apply only takes one arg");

		sd = new AMapSeqStateDesignator(call.getLocation(), 
						null, 
						call.getArgs().get(0));
	    }
	    else
		throw new RuntimeException("Missing implementation in convertToStateDesignator method. Currently only ACallPrimary is implemented");
	}
	else
	    throw new RuntimeException("Missing implementation in convertToStateDesignator method. sd was null");
		    
      
	return sd;
    }
    
    private LexNameToken getNameTokenFromMKUNDERNAME(CmlLexeme mkUnderName)
    {
	LexNameToken name = new LexNameToken("Default",
					     mkUnderName.getValue().split("_")[1], 
					     extractLexLocation(mkUnderName),
					     false, 
					     true);
	return name;
    }
 
    private LexNameToken extractLexNameToken(ASimpleName sn)
    {
	LexNameToken lnt = null;
	if (sn.getIdentifiers().size() > 2){
	    throw new CustomSyntaxErrorException();
	}
	else if (sn.getIdentifiers().size() == 2)
	    lnt = new LexNameToken(sn.getIdentifiers().get(0).getName(), 
				   sn.getIdentifiers().get(1).getName(), 
				   combineLexLocation(extractFirstLexLocation(sn.getIdentifiers()),
						      extractLastLexLocation(sn.getIdentifiers())) 
				   );
	else{
	    lnt = new LexNameToken("Default", 
				   sn.getIdentifiers().get(0));
	}
	return lnt;
    }

    private LexLocation extractLexLocation(CmlLexeme lexeme)
    {
	return new LexLocation(currentSourceFile.getFile(), "Default",
			       lexeme.getStartPos().line, lexeme.getStartPos().column, 
			       lexeme.getEndPos().line, lexeme.getEndPos().column,0,0);
    }

    private LexLocation extractLexLocation(CmlLexeme start, CmlLexeme end)
    {
	return new LexLocation(currentSourceFile.getFile(), "Default",
			       start.getStartPos().line, start.getStartPos().column, 
			       end.getEndPos().line, end.getEndPos().column,0,0);
    }

    private LexLocation extractLexLocation(CmlLexeme start, LexLocation end)
    {
	return new LexLocation(currentSourceFile.getFile(), "Default",
			       start.getStartPos().line, start.getStartPos().column, 
			       end.endLine, end.endPos,0,0);
    }
    
    private LexLocation extractLexLocation(LexLocation start, CmlLexeme end)
    {
	return new LexLocation(currentSourceFile.getFile(), "Default",
			       start.endLine, start.endPos, 
			       end.getStartPos().line, end.getStartPos().column,0,0);
    }

    private LexLocation combineLexLocation(LexLocation start, LexLocation end)
    {
      return new LexLocation(currentSourceFile.getFile(), "Default",
			     start.startLine, start.startPos, 
			     end.endLine, end.endPos,0,0);
    }


    private LexLocation extractLastLexLocation ( List<?> fields )
    {
      try 
	{
	  Object o = fields.get(0);
	  Class<?> clz = o.getClass();
	  
	  Method locMethod = clz.getMethod("getLocation", new Class<?>[] {} );
	  
	  LexLocation candidate = (LexLocation)locMethod.invoke( o, null );
	  for(Object p : fields)
	    {
	      LexLocation pLoc = (LexLocation)locMethod.invoke( o, null );
	      if (pLoc.endOffset > candidate.endOffset)
		candidate = pLoc;
	    }
	  return candidate;
	} catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
    }

    private LexLocation extractFirstLexLocation ( List<?> fields )
    {
      try 
	{
	  Object o = fields.get(0);
	  Class<?> clz = o.getClass();
	  
	  Method locMethod = clz.getMethod("getLocation", new Class<?>[] {} );
	  
	  LexLocation candidate = (LexLocation)locMethod.invoke( o, null );
	  for(Object p : fields)
	    {
	      LexLocation pLoc = (LexLocation)locMethod.invoke( o, null );
	      if (pLoc.startOffset < candidate.startOffset)
		candidate = pLoc;
	    }
	  return candidate;
	} catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
    }

    private< T extends PPattern> LexLocation extractLexLeftMostFromPatterns(List<T> ptrns )
    {
      LexLocation candidate = ptrns.get(0).getLocation();
      for(PPattern p : ptrns)
	if (p.getLocation().endOffset > candidate.endOffset)
	  candidate = p.getLocation();
      return candidate;
    }
    
    private LexNameToken extractLexNameToken(CmlLexeme lexeme)
    {
      return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
    }

    private LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme)
    {
      return new LexIdentifierToken(lexeme.getValue(), false, extractLexLocation(lexeme));
    }

    // *************************
    // *** PUBLIC OPERATIONS ***
    // *************************

    public void setDocument(ASourcefileSourcefile doc)
    {
      currentSourceFile = doc;
    }
     
    public ASourcefileSourcefile getDocument()
    {
      return currentSourceFile;
    }
    
    public static void main(String[] args) throws Exception
    {
	if (args.length == 0) {
	    System.out.println("Usage : java CmlParser <inputfile>");
	}
	else {
      
	    CmlLexer scanner = null;
	    try {
	      String filePath = args[0];
	      ClonableFile file = new ClonableFile(filePath); 
	      ASourcefileSourcefile currentSourceFile = new ASourcefileSourcefile();
	      currentSourceFile.setName(file.getName());
	      currentSourceFile.setFile(file);
	      scanner = new CmlLexer( new java.io.FileReader(file) );
	      CmlParser cmlParser = new CmlParser(scanner);
	      cmlParser.setDocument(currentSourceFile);
	      //cmlParser.setDebugLevel(1);
	  
	      //do {
	      //System.out.println(scanner.yylex());
	      boolean result = cmlParser.parse();
	      if (result){
		System.out.println("parsed!");
				
		//DotGraphVisitor dgv = new DotGraphVisitor();
		INode node = cmlParser.getDocument();

		//node.apply(dgv,null);
				
		File dotFile = new File("generatedAST.gv");
		java.io.FileWriter fw = new java.io.FileWriter(dotFile);
		//fw.write(dgv.getResultString());
		fw.close();

		//System.out.println(dgv.getResultString());
	    
	      }
	      else
		{
		  System.out.println("Not parsed!");
		  System.exit(-1);
		}
		
	      //} while (!scanner.zzAtEOF);

	    }
	    catch (java.io.FileNotFoundException e) {
		System.out.println("File not found : \""+args[0]+"\"");
		System.exit(-2);
	    }
	    catch (java.io.IOException e) {
		System.out.println("IO error scanning file \""+args[0]+"\"");
		System.out.println(e);
		System.exit(-3);
	    }
	    catch (Exception e) {
		System.out.println("Unexpected exception:");
		e.printStackTrace();
		System.exit(-4);
	    }
	    
	}
    }
    
 


/* Line 898 of cmlskeleton.java.m4  */
/* Line 9653 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


