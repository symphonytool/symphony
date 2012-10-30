
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

/**
 * A Bison parser, automatically generated from <tt>src/main/bison/cml.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class CmlParser
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
      
      String beginString;
      if (begin == null)
      	 beginString = "Warning! Position begin is null";
      else
      	 beginString = begin.toString ();

      String endString;
      if (end == null)
      	 endString = "Warning! Position end is null";
      else
      	 endString = end.toString (); 
      /*
      if (begin.equals (end))
        return beginString;
      else
      */
        return beginString + "-" + endString;
    }
  }



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int TYPES = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANNELS = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANSETS = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int NAMESETS = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int FUNCTIONS = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int OPERATIONS = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int ACTIONS = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int INITIAL = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int STATE = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int EQUALS = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int AT = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int BARTILDEBAR = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int LRSQUARE = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int TBAR = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int LPAREN = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int RPAREN = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int LRPAREN = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUARE = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int RSQUARE = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSKIP = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSTOP = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPCHAOS = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPDIV = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPWAIT = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int RARROW = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int LARROW = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLY = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int RCURLY = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int BAR = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int DBAR = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int SEMI = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int DCOLON = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int COMPOSE = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int OF = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int STAR = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int TO = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int INMAPOF = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int MAPOF = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int SEQOF = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int SEQ1OF = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int SETOF = 298;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUSGT = 299;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONDASH = 300;
  /** Token number, to be returned by the scanner.  */
  public static final int DEQUALS = 301;
  /** Token number, to be returned by the scanner.  */
  public static final int INV = 302;
  /** Token number, to be returned by the scanner.  */
  public static final int VALUES = 303;
  /** Token number, to be returned by the scanner.  */
  public static final int PRE = 304;
  /** Token number, to be returned by the scanner.  */
  public static final int POST = 305;
  /** Token number, to be returned by the scanner.  */
  public static final int MEASURE = 306;
  /** Token number, to be returned by the scanner.  */
  public static final int IOTA = 307;
  /** Token number, to be returned by the scanner.  */
  public static final int SUBCLASSRESP = 308;
  /** Token number, to be returned by the scanner.  */
  public static final int NOTYETSPEC = 309;
  /** Token number, to be returned by the scanner.  */
  public static final int FRAME = 310;
  /** Token number, to be returned by the scanner.  */
  public static final int RD = 311;
  /** Token number, to be returned by the scanner.  */
  public static final int WR = 312;
  /** Token number, to be returned by the scanner.  */
  public static final int LET = 313;
  /** Token number, to be returned by the scanner.  */
  public static final int IN = 314;
  /** Token number, to be returned by the scanner.  */
  public static final int IF = 315;
  /** Token number, to be returned by the scanner.  */
  public static final int THEN = 316;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSEIF = 317;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSE = 318;
  /** Token number, to be returned by the scanner.  */
  public static final int CASES = 319;
  /** Token number, to be returned by the scanner.  */
  public static final int OTHERS = 320;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUS = 321;
  /** Token number, to be returned by the scanner.  */
  public static final int MINUS = 322;
  /** Token number, to be returned by the scanner.  */
  public static final int ABS = 323;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOOR = 324;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT = 325;
  /** Token number, to be returned by the scanner.  */
  public static final int CARD = 326;
  /** Token number, to be returned by the scanner.  */
  public static final int POWER = 327;
  /** Token number, to be returned by the scanner.  */
  public static final int DUNION = 328;
  /** Token number, to be returned by the scanner.  */
  public static final int DINTER = 329;
  /** Token number, to be returned by the scanner.  */
  public static final int HD = 330;
  /** Token number, to be returned by the scanner.  */
  public static final int TL = 331;
  /** Token number, to be returned by the scanner.  */
  public static final int LEN = 332;
  /** Token number, to be returned by the scanner.  */
  public static final int ELEMS = 333;
  /** Token number, to be returned by the scanner.  */
  public static final int INDS = 334;
  /** Token number, to be returned by the scanner.  */
  public static final int REVERSE = 335;
  /** Token number, to be returned by the scanner.  */
  public static final int CONC = 336;
  /** Token number, to be returned by the scanner.  */
  public static final int DOM = 337;
  /** Token number, to be returned by the scanner.  */
  public static final int RNG = 338;
  /** Token number, to be returned by the scanner.  */
  public static final int MERGE = 339;
  /** Token number, to be returned by the scanner.  */
  public static final int INVERSE = 340;
  /** Token number, to be returned by the scanner.  */
  public static final int ELLIPSIS = 341;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRARROW = 342;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDER = 343;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDERNAME = 344;
  /** Token number, to be returned by the scanner.  */
  public static final int DOT = 345;
  /** Token number, to be returned by the scanner.  */
  public static final int DOTHASH = 346;
  /** Token number, to be returned by the scanner.  */
  public static final int NUMERAL = 347;
  /** Token number, to be returned by the scanner.  */
  public static final int LAMBDA = 348;
  /** Token number, to be returned by the scanner.  */
  public static final int NEW = 349;
  /** Token number, to be returned by the scanner.  */
  public static final int ISUNDER = 350;
  /** Token number, to be returned by the scanner.  */
  public static final int PREUNDER = 351;
  /** Token number, to be returned by the scanner.  */
  public static final int ISOFCLASS = 352;
  /** Token number, to be returned by the scanner.  */
  public static final int TILDE = 353;
  /** Token number, to be returned by the scanner.  */
  public static final int DCL = 354;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONEQUALS = 355;
  /** Token number, to be returned by the scanner.  */
  public static final int ATOMIC = 356;
  /** Token number, to be returned by the scanner.  */
  public static final int DEQRARROW = 357;
  /** Token number, to be returned by the scanner.  */
  public static final int RETURN = 358;
  /** Token number, to be returned by the scanner.  */
  public static final int BACKTICK = 359;
  /** Token number, to be returned by the scanner.  */
  public static final int SLASH = 360;
  /** Token number, to be returned by the scanner.  */
  public static final int DIV = 361;
  /** Token number, to be returned by the scanner.  */
  public static final int REM = 362;
  /** Token number, to be returned by the scanner.  */
  public static final int MOD = 363;
  /** Token number, to be returned by the scanner.  */
  public static final int LT = 364;
  /** Token number, to be returned by the scanner.  */
  public static final int LTE = 365;
  /** Token number, to be returned by the scanner.  */
  public static final int GT = 366;
  /** Token number, to be returned by the scanner.  */
  public static final int GTE = 367;
  /** Token number, to be returned by the scanner.  */
  public static final int NEQ = 368;
  /** Token number, to be returned by the scanner.  */
  public static final int OR = 369;
  /** Token number, to be returned by the scanner.  */
  public static final int AND = 370;
  /** Token number, to be returned by the scanner.  */
  public static final int EQRARROW = 371;
  /** Token number, to be returned by the scanner.  */
  public static final int LTEQUALSGT = 372;
  /** Token number, to be returned by the scanner.  */
  public static final int INSET = 373;
  /** Token number, to be returned by the scanner.  */
  public static final int NOTINSET = 374;
  /** Token number, to be returned by the scanner.  */
  public static final int SUBSET = 375;
  /** Token number, to be returned by the scanner.  */
  public static final int PSUBSET = 376;
  /** Token number, to be returned by the scanner.  */
  public static final int UNION = 377;
  /** Token number, to be returned by the scanner.  */
  public static final int BACKSLASH = 378;
  /** Token number, to be returned by the scanner.  */
  public static final int INTER = 379;
  /** Token number, to be returned by the scanner.  */
  public static final int CARET = 380;
  /** Token number, to be returned by the scanner.  */
  public static final int DPLUS = 381;
  /** Token number, to be returned by the scanner.  */
  public static final int MUNION = 382;
  /** Token number, to be returned by the scanner.  */
  public static final int LTCOLON = 383;
  /** Token number, to be returned by the scanner.  */
  public static final int LTDASHCOLON = 384;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONGT = 385;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONDASHGT = 386;
  /** Token number, to be returned by the scanner.  */
  public static final int COMP = 387;
  /** Token number, to be returned by the scanner.  */
  public static final int DSTAR = 388;
  /** Token number, to be returned by the scanner.  */
  public static final int FORALL = 389;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS = 390;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS1 = 391;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING = 392;
  /** Token number, to be returned by the scanner.  */
  public static final int VRES = 393;
  /** Token number, to be returned by the scanner.  */
  public static final int RES = 394;
  /** Token number, to be returned by the scanner.  */
  public static final int VAL = 395;
  /** Token number, to be returned by the scanner.  */
  public static final int AMP = 396;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREBAR = 397;
  /** Token number, to be returned by the scanner.  */
  public static final int DLSQUARE = 398;
  /** Token number, to be returned by the scanner.  */
  public static final int DRSQUARE = 399;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRSQUARE = 400;
  /** Token number, to be returned by the scanner.  */
  public static final int COMMA = 401;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREDBAR = 402;
  /** Token number, to be returned by the scanner.  */
  public static final int DBARRSQUARE = 403;
  /** Token number, to be returned by the scanner.  */
  public static final int COLON = 404;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLYBAR = 405;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRCURLY = 406;
  /** Token number, to be returned by the scanner.  */
  public static final int QUESTION = 407;
  /** Token number, to be returned by the scanner.  */
  public static final int BANG = 408;
  /** Token number, to be returned by the scanner.  */
  public static final int SLASHCOLON = 409;
  /** Token number, to be returned by the scanner.  */
  public static final int SLASHBACKSLASH = 410;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONBACKSLASH = 411;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREGT = 412;
  /** Token number, to be returned by the scanner.  */
  public static final int BARGT = 413;
  /** Token number, to be returned by the scanner.  */
  public static final int ENDSBY = 414;
  /** Token number, to be returned by the scanner.  */
  public static final int DECIMAL = 415;
  /** Token number, to be returned by the scanner.  */
  public static final int STARTBY = 416;
  /** Token number, to be returned by the scanner.  */
  public static final int MU = 417;
  /** Token number, to be returned by the scanner.  */
  public static final int DO = 418;
  /** Token number, to be returned by the scanner.  */
  public static final int FOR = 419;
  /** Token number, to be returned by the scanner.  */
  public static final int ALL = 420;
  /** Token number, to be returned by the scanner.  */
  public static final int BY = 421;
  /** Token number, to be returned by the scanner.  */
  public static final int WHILE = 422;
  /** Token number, to be returned by the scanner.  */
  public static final int ISUNDERNAME = 423;
  /** Token number, to be returned by the scanner.  */
  public static final int EXTENDS = 424;
  /** Token number, to be returned by the scanner.  */
  public static final int EMPTYMAP = 425;
  /** Token number, to be returned by the scanner.  */
  public static final int DBACKSLASH = 426;
  /** Token number, to be returned by the scanner.  */
  public static final int BEGIN = 427;
  /** Token number, to be returned by the scanner.  */
  public static final int END = 428;
  /** Token number, to be returned by the scanner.  */
  public static final int CLASS = 429;
  /** Token number, to be returned by the scanner.  */
  public static final int PROCESS = 430;
  /** Token number, to be returned by the scanner.  */
  public static final int PRIVATE = 431;
  /** Token number, to be returned by the scanner.  */
  public static final int PROTECTED = 432;
  /** Token number, to be returned by the scanner.  */
  public static final int PUBLIC = 433;
  /** Token number, to be returned by the scanner.  */
  public static final int LOGICAL = 434;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER = 435;
  /** Token number, to be returned by the scanner.  */
  public static final int SELF = 436;
  /** Token number, to be returned by the scanner.  */
  public static final int TRUE = 437;
  /** Token number, to be returned by the scanner.  */
  public static final int FALSE = 438;
  /** Token number, to be returned by the scanner.  */
  public static final int CHAR_LIT = 439;
  /** Token number, to be returned by the scanner.  */
  public static final int NIL = 440;
  /** Token number, to be returned by the scanner.  */
  public static final int QUOTE_LITERAL = 441;
  /** Token number, to be returned by the scanner.  */
  public static final int HEX_LITERAL = 442;
  /** Token number, to be returned by the scanner.  */
  public static final int TBOOL = 443;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT = 444;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT1 = 445;
  /** Token number, to be returned by the scanner.  */
  public static final int TINT = 446;
  /** Token number, to be returned by the scanner.  */
  public static final int TRAT = 447;
  /** Token number, to be returned by the scanner.  */
  public static final int TREAL = 448;
  /** Token number, to be returned by the scanner.  */
  public static final int TCHAR = 449;
  /** Token number, to be returned by the scanner.  */
  public static final int TTOKEN = 450;
  /** Token number, to be returned by the scanner.  */
  public static final int DUMMY = 451;



  
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
	

/* Line 374 of cmlskeleton.java.m4  */
/* Line 758 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -646;
  public static final short yypact_[] =
  {
       105,   874,  -129,   -85,   874,   315,   -81,   -61,   157,   105,
    -646,  -646,  -646,  -646,  -646,  -646,  -646,  -646,  -646,  -646,
    -646,  -646,  -646,   168,  -646,    91,   -31,   269,  -646,  -646,
     332,   -85,  -646,   169,   380,  -646,  -646,  -646,   979,   392,
    -646,   460,   478,  -646,  -646,   874,   422,   335,  1785,  -129,
    3480,  -646,    84,  -646,   874,  3480,   432,   337,  -646,  -646,
    -646,     7,  -646,  -646,  -646,  -646,  -646,  -646,  -646,  -646,
    -646,  -646,  -646,  -646,  -646,    14,  -646,   315,   401,   259,
    -646,  1785,  1834,   -31,  -646,  1785,   461,  1785,   352,  1785,
    1785,  1785,  1785,  1785,   196,  -646,  -646,  -646,  -646,  -646,
    -646,  -646,  -646,   859,  -646,  -646,  -646,  -646,  -646,  -646,
    -646,  3480,  3480,  3072,   918,  1416,  3480,  3480,  3480,  3480,
    3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,
    3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,   519,   530,
     918,    26,   552,   558,   918,   918,   918,  3480,   559,  -646,
     483,  -646, 11762,  -646,  -646,  -646,  -646,  -646,  -646,  -646,
    -646,  -646,  -646,   918,  -646,  1785,   407,  -646,  6017,   918,
     918,  -646,  3480,  1785,  3480,  1785,  -646,   412,   785,   419,
     419,   419,   335,   419,   419,  3480,  1127,   268,  3470,    83,
    -646,   685,   244,   859,  1268,  -646,   632,  1785,  1785,   912,
     564,   774,   989,   567,   567,   567,   428,   434,  1785,  1785,
    1785,  1785,  6139,   147,  6262,  -646,   -12,  5159,    -7,   587,
    -646,  -646,   -58,  -646,  -646,   612,  -646,  -646,    21,  -646,
    -646,   -29,  -646,  6384,  6506,    46,    46,    46,    46, 12335,
      46,    46,    46,    46,    46,    46,    46,    46,    46,    46,
      46,    46,    46,    46,   503,  3480,  3480,   477,  -646,    29,
    3480,   621,  3480,  3480,  -646,   222,    35,  -646,  -646,  -646,
      41,   626,   192,  6629,  3480,  -646,  3480,  3480,  -646,  3480,
    3480,  3480,   437,   557,   464,  3480,  3480,  3480,  3480,  3480,
    3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,
    3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,  3480,
    3480,  3480,  3480,  3480,   918,  1152,    82,   467,   859,   474,
     550,   -15,  -646,   554,    86, 11762,   101, 11762,   426,   542,
     874,   646,    17,   544,   785,  -646,  -646,  -646,  -646,  -646,
     364,   329,  -646,  -646,   334,   369,   409,   448,   490,  6751,
     547,   551,  2626,  1381,  -646,  -646,  3480,  -646,   383,   383,
     383,  3480,   383,   383,  3480,  3480,  3480,   383,   383,  3480,
    3480,  3480,  -646,   383,   335,   918,  -646,  1785,  1785,  -646,
    -646,  -646,   859,   859,  -646,  1834,  1785,  1785,  -646,  -646,
     859,   567,   567,   859,  -646,  -646,  3480,   918,  -646,   918,
    3480,  3480,  -646,  3480,   918,  3480,  1785,  3480,  1785,  3480,
    1416,  3480,   918,    88, 11762,   151,  3480,   918,  6873,  3480,
     159,  6995,  3480,   918,  1785,  3480,   918,  3480,  3480,  -646,
     918,  7117, 12385,   172,  7239,  1273,  4961,  4961,  -646,  -646,
    -646,  -646,  1273,  1273,  1273,  1273, 12385, 12385, 12385, 12385,
   12385, 12208, 12258, 12128, 12006, 12385, 12385, 12385, 12385,  3754,
    3754,   503,  2650,  2476,  2476,   503,   503,    46,    46,   375,
     279,  -646,  -646,  -646,  -646,  -646,   918,  1785,   445,  1785,
    3480,   553,   696,  -646,   918,  -646,  3480,  3480,   785,   568,
     874,  -646,  -646,  -646,  -646,  3480,   573,   697,  -646,  -646,
    -646,  3480,   419,   383,   419,   383,   383,   383,   127,   383,
     419,   735,   -19,   742,   577,   419,   419,   419,  2452,  3208,
    -646,  -646,  -646,  -646,  3480,   419,   419,  1416,  3480,  3480,
    3344,   335,   335,   335,  3480,  3480,  3480,  3480,   672,  3480,
    1979,   491,  -646,  5281,  -646,  -646,  -646,  -646,  -646,  2626,
    -646,   184,   492,   756,   810,  3606,  4543,   289,   406,  7361,
     439,  7483,  4665,   262,   395, 11762, 11762, 11762,  3470,  -646,
     712,   859,   859,  1574,   567,   567, 11762,   641,   288,    25,
    7607, 11762,  7729,  -646,    77, 12385,   859, 12006, 11762,  -646,
    5404,   162,  -646,   164,  -646,  -646, 12006,  -646,  1785,  7851,
    -646,  3480, 11762,  -646,   859, 12006,  -646, 12006, 12006,     3,
    -646,  -646,  3480,   502,   859,   918,  -646,    81,   859, 11762,
     613,  3480,  -646,   745, 11762, 11762,   592,   129,  -646, 11762,
     620,  -646,    17, 11762,  -646,  -646,  -646,  -646,  -646,  -646,
    2038,  3480,  -646,  -646,   520,  3480,   597,   767,  2800,   769,
     524,   531,   596,   573,   278,  1688,   639,  5036,   420,   733,
    -646,   762, 11762,   652,   668,   -13,  5526,   -18,  7973,  -646,
   11762,  -646,  -646,  -646,  8095,  8217,    43,  8339,   -11,   918,
     773,   163,   727,  3810,  2626,  2626,  2626,  3480,  2626,  2626,
    3480,  3480,  3480,  2626,  2626,  3480,  3480,  3480,  -646,  -646,
    2626,   602,  2626,   693,  2626,  2235,  -646,  3480,   383,   383,
    -646,  3480,  3480,   383,  3480,  -646,  3480,  -646,  3480,  -646,
    -646,  3480,  -646,  3480,  3480,  -646,   523,  -646,  3480,   854,
    -646,  8461,  3480,  -646,  8583,  1785,   190,   918,  2936,  1785,
   11762,  -646,  -646,  1888,   407,  1785,  -646,   780,  8705,   383,
   11762,   778,  3480,  -646,  3573,   671,  2800,  2626,  2626,   784,
      79,  -646,  -646,  2626,  -646,  -646,   420,  -646,  3480,   696,
    -646,   788,  2626,  2626,  2626,  2626,  3480,  -646,   918,   419,
     419,   781,  -646,   692,  3480,  3480,  3480,  2626,   425,   170,
    1522,  4421,   723,   387,  5649,  8828,  4787,   249,   312, 11762,
   11762, 11762,  3573,  -646,  -646,  3480,   387,  -646,  8950,   395,
     289,  9072,  9195,   262, 11762,  9317,  9441,  9565,  9687, 12006,
     790,  -646, 11762,  -646,  -646,  9809,  -646,   859,  -646,   191,
    -646,  -646,   733, 11762,   859,   250,   508,   640,   764,   682,
     684,  3480,   383,  -646,  3480, 11762,  2626,  -646,  -646,  -646,
    3480,  2626,   573,  3178,  -646,   521,  -646,  3480,  -646,  3573,
    5281,  3573,  3028,  4909,  9931,   187,  -646,   177,   680,   711,
    2626,  3480, 10053,  3932,  4054,  3573,  3480,  3480,  2626,  3480,
    2626,  3480,  2626, 11884,   383,  3480,   918,  -646,  -646,  -646,
    3480,  3480,  -646,  -646,   696,  1967,  1996,   445,   733,  3480,
    3480, 10175,  -646, 11762,  3573, 10297,  3437,  -646,   817,  1785,
    5772,  3480,  2626,   497,  2626,   858,  -646,  2626,   816,  2626,
    3573,   193,  4176,  3480,  2626,  2626, 10419, 10541,   312,  5895,
     723, 10663,   249,  3480,   289, 11762,    28,  5404,  3566,   787,
    -646,   461,   859,   461,   859,    94,   696, 11762, 11762,  -646,
    2626,  -646,  3480,   859,  2626,  3480, 10785,  3573,  3480,  2626,
    3573,   812,  -646,   245,  3573,  5281,  3480,  -646,  -646,  2626,
    2626,  3688,  3573,  3573,  3480,  2626,  3480,  2626,  2626,   265,
    3480,  -646,  -646,  -646,  3480,  -646,  2278,  -646,  -646,   272,
    -646, 10907,  2626, 11029,  3573,  2626,  2626, 11151,  3573,  3573,
    2626,  3480, 11274,   723, 11396,   723,  -646,  2081, 11518, 11762,
    -646,  -646,  3573,   733,  -646,  2626,  3573,  2626,  2892,  2626,
    3573,  4298,  3480,  2626,  -646,   696,  -646,  3573,  -646,  -646,
    2626, 11640,   723,  -646,  3573,  2626,   723
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,   140,   113,   124,   200,   190,     0,     0,     0,     2,
       3,     5,     6,     7,     8,     9,   129,   130,   131,   149,
     150,   151,   152,   141,   143,     0,   118,   114,   116,   119,
       0,   125,   126,     0,   201,   203,   205,   206,     0,   191,
     193,     0,     0,     1,     4,   142,     0,     0,     0,   115,
       0,   127,     0,   208,   202,     0,     0,     0,   323,   326,
     325,     0,   320,   319,   321,   322,   327,   324,   195,   441,
     443,   442,   440,   444,   445,     0,   432,   192,     0,     0,
     144,     0,     0,     0,   123,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   167,   170,   171,   172,   173,   174,
     175,   176,   177,   122,   155,   166,   178,   179,   156,   117,
     300,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   304,
     273,   272,   128,   266,   268,   267,   265,   269,   270,   287,
     288,   289,   290,     0,   216,     0,     0,   204,     0,     0,
       0,   434,     0,     0,     0,     0,   194,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    31,    12,     0,
     120,   146,   167,   186,   147,   184,     0,     0,     0,     0,
       0,     0,     0,   162,   163,   161,     0,     0,     0,     0,
       0,     0,     0,     0,   263,   295,     0,   263,     0,   393,
     439,   438,     0,   431,   430,     0,   447,   448,     0,   330,
     331,     0,   328,     0,     0,   340,   341,   342,   343,   344,
     345,   346,   347,   348,   349,   350,   351,   352,   353,   354,
     355,   356,   357,   358,   359,     0,     0,     0,   457,     0,
       0,     0,     0,     0,   436,     0,     0,   451,   453,   454,
       0,     0,     0,   263,     0,   274,     0,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   166,
       0,   225,   446,     0,     0,   198,     0,   199,     0,     0,
     232,   153,   256,     0,   132,   134,   135,   136,   137,   138,
       0,     0,    43,    42,     0,     0,     0,     0,     0,     0,
      52,     0,     0,     0,    48,    50,     0,    32,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    34,     0,     0,     0,   145,     0,     0,   185,
     148,   154,   183,   181,   160,     0,     0,     0,   168,   169,
     182,   158,   159,   180,   271,   301,     0,     0,   296,     0,
       0,     0,   305,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   263,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   316,
       0,     0,   371,     0,   263,   361,   360,   362,   275,   281,
     277,   276,   364,   363,   365,   366,   367,   368,   369,   370,
     372,   373,   374,   375,   376,   377,   378,   379,   380,   381,
     382,   383,   384,   385,   386,   387,   388,   389,   390,   391,
     392,   284,   285,   282,   283,   217,     0,     0,     0,     0,
       0,     0,     0,   224,     0,   435,     0,     0,     0,     0,
     233,   234,   237,   236,   139,     0,     0,   257,   259,    10,
     133,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    53,     0,    51,     0,   300,     0,     0,   248,
      61,    62,    63,    64,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   103,     0,   100,    98,    99,   396,   403,     0,
      49,     0,    31,    18,    17,    22,     0,    21,    16,     0,
       0,     0,     0,    23,    25,    29,    28,    27,    13,   121,
       0,   188,   187,     0,   165,   164,   264,     0,     0,     0,
       0,   395,     0,   394,     0,   449,   450,   294,   286,   329,
       0,     0,   337,     0,   308,   309,   310,   458,     0,     0,
     314,     0,   455,   437,   456,   291,   452,   292,   293,     0,
     311,   279,     0,     0,   218,     0,   210,     0,   222,   226,
       0,     0,   207,   437,   196,   197,     0,     0,   235,   262,
       0,   261,   258,    46,    47,    37,    45,    44,    36,    41,
       0,     0,    40,    35,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   273,     0,     0,     0,     0,   225,
     247,     0,    65,     0,     0,     0,     0,     0,     0,   400,
     401,   107,   106,   105,     0,     0,     0,     0,     0,     0,
     438,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    14,    78,
       0,     0,     0,     0,     0,     0,    33,     0,     0,     0,
     108,     0,     0,     0,     0,   157,     0,   302,     0,   297,
     299,     0,   306,     0,     0,   332,     0,   335,     0,     0,
     312,     0,     0,   317,     0,     0,     0,     0,     0,     0,
     229,   433,    11,     0,   220,     0,   260,     0,     0,     0,
      56,     0,     0,    54,    59,     0,     0,     0,     0,     0,
       0,   415,   413,     0,   254,   255,   249,   250,     0,     0,
     399,     0,     0,     0,     0,     0,     0,   397,     0,     0,
       0,     0,   398,     0,     0,     0,     0,     0,    70,    69,
      81,     0,    83,    68,     0,     0,     0,    71,    73,    77,
      76,    75,    88,   104,    66,     0,    67,    15,     0,    26,
      19,     0,   111,    24,   189,     0,     0,     0,     0,   333,
       0,   338,   339,   313,   315,     0,   280,   219,   211,     0,
     214,   215,   225,   213,   223,     0,     0,     0,   248,   221,
     417,     0,     0,    38,     0,    57,     0,    55,    92,    91,
       0,     0,     0,     0,   251,   252,   429,     0,    90,   412,
     286,   410,     0,     0,     0,     0,   426,     0,     0,     0,
       0,     0,     0,     0,     0,   409,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   303,   298,   307,
       0,     0,   318,   212,   228,     0,     0,     0,   225,     0,
       0,     0,    39,    58,    60,     0,     0,   416,     0,     0,
       0,     0,     0,     0,     0,     0,   424,     0,     0,     0,
     101,     0,     0,     0,     0,     0,     0,     0,    74,     0,
      87,     0,    72,     0,    20,   112,     0,     0,     0,   231,
     227,   243,   241,   242,   240,     0,     0,   419,   418,    30,
       0,   414,     0,   253,     0,     0,     0,   421,     0,     0,
     411,     0,   427,     0,   428,   339,     0,    93,    79,     0,
       0,     0,   405,   404,     0,     0,     0,     0,     0,     0,
       0,   109,   334,   336,     0,   209,     0,   239,    94,     0,
      97,     0,     0,     0,   420,     0,     0,     0,   102,   406,
       0,     0,     0,    85,     0,    82,    80,   279,     0,   230,
     245,   246,   244,   225,    89,     0,   422,     0,     0,     0,
     407,     0,     0,     0,   110,   228,    96,   423,   425,    95,
       0,     0,    86,   238,   408,     0,    84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -646,  -646,  -646,   833,  -646,  -646,  -323,  -152,   173,  -646,
     493,  -646,  -646,    89,   966,  -646,  -646,   146,   -37,  -646,
    -646,  -646,   799,   -77,    10,  -646,  -646,   820,  -646,  -324,
    -177,   114,  -646,   808,    48,  1432,   719,  -140,  -646,  -646,
     479,  -190,   675,   120,  -646,   794,   824,   379,  -646,   822,
    -646,  -646,   841,    -9,  -646,   252,  -646,  -646,   145,  -632,
    -646,  -132,  -481,  -646,  -646,  -646,  -325,  -646,  -646,  -646,
    -646,    54,  -646,  -646,   134,  -646,  -646,  -646,   263,   -89,
     -50,   792,   830,   948,  1011,  1135,   605,   374,   494,  -646,
     -28,  -646,  -646,   186,  -646,  -646,  -646,   511,  -646,   381,
    -646,  -646,  -646,  -645,  -646,  -646,  -646,  -646,     6,  -646,
      12,   -21,  -148,  -646,  -194,  -143,  -267,  -133,  -134,   496,
    -646,  -646,  -646
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   188,   341,   342,   353,
     354,   514,   512,   753,   754,   921,   541,   542,   699,   560,
      13,    27,    28,   755,   343,    14,    31,    32,    15,   333,
     334,   335,    23,    24,   489,   193,   104,   105,   106,   107,
     194,   195,   376,   336,    39,    40,   229,   337,    34,    35,
      36,    37,   230,   617,   832,   166,   316,   838,   321,   482,
     483,   939,   940,   985,   338,   490,   491,   492,   493,   837,
    1013,   659,   660,   766,   767,   768,   339,   497,   498,   213,
     543,   153,   154,   155,   156,   157,   158,   231,   232,   159,
     725,   160,   591,   592,   161,   162,   218,   219,   544,   667,
     545,   546,   760,   631,   547,   913,   548,   865,   866,   661,
     264,   223,   265,   224,    76,   225,   226,   227,   266,   267,
     268,   269,   259
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -403;
  public static final short
  yytable_[] =
  {
       152,   622,   189,   271,   379,   168,   494,   258,   761,   355,
     500,   270,    29,   776,   646,   317,   732,    75,   398,   172,
     776,   323,   324,   402,   216,   319,   174,   769,   344,   345,
     409,   347,   348,   172,   480,   553,   554,   555,   718,   557,
     558,   980,   416,   260,   563,   564,   773,   472,   425,    25,
     568,    26,    33,    38,   427,   719,   781,    84,   272,    29,
     405,   212,   214,   217,   495,   278,   233,   234,   235,   236,
     237,   238,   239,   240,   241,   242,   243,   244,   245,   246,
     247,   248,   249,   250,   251,   252,   253,   254,   439,   190,
     721,   406,   851,    25,    75,    30,   373,   273,   737,    41,
     475,   163,    33,   164,   485,   346,   594,   722,     1,     2,
       3,   737,     4,   486,    16,    47,   374,   410,    48,    42,
      17,   474,   325,    16,   327,    38,   222,   738,   208,    17,
     578,   481,   209,   410,   396,   349,   282,   283,   210,   403,
     986,   179,   180,   181,   182,   211,   163,   641,   164,   426,
     284,   372,   257,     5,   733,   777,   173,    43,   222,   183,
     184,   647,   782,   175,   626,   628,   413,   415,   395,   595,
     408,   426,   981,   420,   426,   417,   355,   600,   312,   313,
     635,   426,   638,   639,   640,   642,   643,   426,   433,   396,
     611,   728,   190,    19,    20,    21,    22,  -153,   314,   315,
     894,    45,   706,   689,   917,   414,   414,   907,   828,   893,
     418,   968,   414,   421,    95,    96,    97,    98,    99,   100,
     101,   102,   785,   426,   431,   852,   432,   434,   476,   435,
     436,   437,   423,   165,   396,   442,   443,   444,   445,   446,
     447,   448,   449,   450,   451,   452,   453,   454,   455,   456,
     457,   458,   459,   460,   461,   462,   463,   464,   465,   466,
     467,   468,   469,   470,   593,   579,   946,   551,   319,   185,
     584,    46,   996,   179,   180,   181,   182,   197,   743,     6,
       7,   405,   689,  1007,   597,   356,   206,   357,   856,   377,
    1014,   183,   184,   396,   198,   363,   609,   396,   278,   186,
     207,   716,    49,   358,   359,   396,   414,   552,   726,   717,
     423,   556,   406,   365,   559,   561,   562,   691,   396,   565,
     566,   567,   363,   423,   692,   693,   471,   694,   613,   695,
     396,   696,  -153,   915,   206,   727,   423,   423,   396,   969,
     422,   697,   503,   429,    50,   689,   576,   505,   207,    52,
     580,   581,   895,   582,   170,   585,   171,   587,   644,   588,
     916,   590,   504,   650,   651,   652,   596,   504,   423,   599,
     283,   424,   602,   663,   664,   605,   275,   607,   608,    18,
     496,  1025,   506,   379,   569,   809,   810,   570,    18,    75,
     813,   423,   365,   378,   278,   682,   691,   179,   180,   181,
     182,   185,   504,  -153,  -153,   365,   694,  -153,   695,   577,
     696,   396,   313,    54,    47,   183,   184,    48,   396,   368,
     697,   369,   507,   370,    47,    77,   843,    48,   363,   257,
     619,   186,   365,   371,    81,   603,   624,   625,   487,   187,
     685,   656,   374,   366,   367,   629,   368,   676,   369,   169,
     370,   633,  -153,   208,    55,   365,    82,   209,   689,   691,
     371,   508,   615,   210,   616,   987,   283,   736,   657,   214,
     211,   695,    78,   696,   662,  -153,   764,   765,   666,   668,
     670,   504,   501,   697,   674,   675,   414,   677,   197,   683,
      79,    19,    20,    21,    22,  -153,   623,  -153,  -153,  -153,
    -153,  -153,  -153,   509,   700,   198,    75,   312,   313,   356,
     502,   357,    84,    48,   637,    83,   372,   372,   372,   902,
     372,   372,   278,   504,   701,   185,   372,   372,   190,    58,
     365,   372,   200,   749,   691,   208,   255,   757,   365,   209,
      55,   671,   672,   673,   758,   210,   695,   256,   696,   365,
     681,   731,   211,   504,   369,   186,   370,   504,   697,   958,
     959,   934,   734,   552,   504,   369,   371,   370,   365,   262,
     177,   740,   691,   178,    59,   263,   274,   371,   593,   692,
     693,   275,   694,   710,   695,   711,   696,   320,   820,   829,
     220,   748,   329,   282,   283,   750,   697,    60,   372,   340,
     385,   372,   372,   372,   210,   372,   372,   284,   388,   759,
     896,    56,    57,   423,   389,    58,   477,   438,   404,    62,
      63,    64,    65,    66,    67,   407,   406,   868,   869,   504,
     867,   308,   309,   310,   311,   312,   313,   791,   419,   428,
     794,   795,   796,    74,   441,   799,   800,   801,   423,   440,
     381,   735,   763,   108,   478,   314,   315,   808,   190,   208,
      59,   811,   812,   209,   814,   771,   815,   396,   816,   210,
     909,   817,   374,   818,   819,   634,   211,   636,   822,   855,
     496,   772,   825,    60,   846,   504,   108,   108,   833,    55,
     108,   783,   108,   918,   108,   108,   108,   108,   108,   479,
     484,   504,   845,   221,   374,    62,    63,    64,    65,    66,
      67,   208,   208,   504,   488,   209,   209,   499,   414,    74,
      74,   210,   210,   860,   919,   863,   864,   511,   211,   211,
     632,   513,   375,   620,   872,   873,   874,   684,   685,   220,
     531,   532,   533,   899,   504,    74,   621,   645,   627,    74,
      74,    74,   936,   630,   648,   883,   689,   649,   714,   405,
      56,    57,   739,   741,    58,   742,   190,   963,    74,   745,
     108,   359,   372,   372,    74,    74,   372,   751,   108,   752,
     108,   756,   480,   770,   900,   784,   786,   805,     1,   363,
     844,   901,     4,   330,   903,   331,   332,   841,   870,   108,
     905,   208,   108,   108,   850,   209,   372,   910,   857,    59,
     871,   210,   386,   108,   108,   108,   108,   891,   211,   658,
     897,   922,    19,    20,    21,    22,   926,   927,   481,   929,
      69,   931,    60,     5,   952,   935,   966,   679,   984,   995,
     937,   938,    44,   363,   979,   847,   550,   803,   109,   947,
     948,    51,   680,    80,    62,    63,    64,    65,    66,    67,
     261,   956,    68,   989,   573,   372,   365,   965,    70,   380,
     691,   176,   823,   971,    53,    55,   167,   692,   693,   744,
     694,   208,   695,   434,   696,   209,   208,    74,   945,   839,
     209,   210,   898,  1033,   697,   746,   210,   372,   211,   365,
     854,   665,   414,   211,   589,   991,    69,    69,   993,   982,
     366,   367,   821,   368,   583,   369,   997,   370,   678,    74,
      74,   962,   606,   961,  1002,   220,  1004,   371,     0,     0,
    1008,     0,    69,   384,  1009,    55,    69,    69,    69,   208,
       0,     0,     0,   209,    70,    70,    56,    57,     0,   210,
      58,  1021,     0,   365,     0,    69,   211,     0,     0,     0,
       0,    69,    69,     0,   366,   367,     0,   368,     0,   369,
      70,   370,  1031,     0,    70,    70,    70,     0,     0,     0,
      74,   371,   108,   108,     0,   220,    71,     0,     0,     0,
     108,   108,   108,    70,     0,    59,    55,     0,     0,    70,
      70,     0,    74,     0,    74,     0,    56,    57,     0,    74,
      58,   108,     0,   108,     0,    74,   208,    74,    60,     0,
     209,     0,    74,     0,     0,     0,   210,   387,    74,   108,
       0,    74,     0,   211,     0,    74,     0,     0,   221,     0,
      62,    63,    64,    65,    66,    67,     0,     0,     0,    72,
      19,    20,    21,    22,  -153,    59,     0,     0,     0,     0,
       0,     0,    71,    71,     0,     0,     0,    56,    57,     0,
       0,    58,     0,     0,    69,     0,     0,     0,    60,     0,
       0,    74,   108,     0,   108,     0,     0,     0,    71,    74,
       0,     0,    71,    71,    71,     0,     0,     0,   221,     0,
      62,    63,    64,    65,    66,    67,    69,    69,     0,     0,
       0,    71,    70,     0,     0,     0,    59,    71,    71,     0,
       0,     0,     0,     0,     0,    72,    72,     0,     0,     0,
       1,     0,    74,   350,     4,   330,   351,   331,   332,    60,
     352,     0,     0,    74,    70,    70,     0,     0,     0,     0,
       0,    72,     0,     0,     0,    72,    72,    72,     0,    61,
       0,    62,    63,    64,    65,    66,    67,    69,     0,    55,
       0,     0,     0,    73,    72,     5,     0,     0,   108,     0,
      72,    72,     0,     0,     0,     0,     0,     0,     0,    69,
       0,    69,     0,     0,     0,     0,    69,     0,     0,     0,
       0,     0,    69,   108,    69,    70,     0,     0,     0,    69,
       0,     0,     0,     0,     0,    69,     0,     0,    69,     0,
      74,     0,    69,     0,     0,     0,     0,    70,     0,    70,
      71,     0,     0,     0,    70,     0,     0,     0,     0,     0,
      70,     0,    70,     0,    58,     0,     0,    70,     0,    73,
      73,     0,     0,    70,     0,     0,    70,     0,     0,     0,
      70,     0,    71,    71,     0,     0,     0,     0,    69,     0,
       0,     0,     0,     0,     0,    73,    69,     0,     0,    73,
      73,    73,     0,     0,    74,    85,     0,    86,    87,    59,
       0,     0,   278,    72,     0,     0,     0,     0,    73,     0,
       0,     0,     0,    88,    73,    73,    70,    89,    90,    91,
      92,    93,    60,     0,    70,   375,     0,     0,   540,    69,
       0,     0,     0,    71,     0,    72,    72,     0,     0,     0,
      69,    74,   473,     0,    62,    63,    64,    65,    66,    67,
     108,     0,    74,     0,   108,    71,     0,    71,   108,     0,
     108,     0,    71,     0,     0,     0,     0,    70,    71,     0,
      71,     0,     0,   282,   283,    71,     0,     0,    70,     0,
       0,    71,     0,     0,    71,     0,     0,   284,    71,     0,
       0,     0,     0,    74,     1,     0,    72,   350,     4,   330,
     351,   331,   332,     0,   549,     0,     0,   304,     0,     0,
       0,   308,   309,   310,   311,   312,   313,    69,    72,     0,
      72,     0,     0,     0,     0,    72,     0,    73,     0,     0,
       0,    72,     0,    72,    71,   314,   315,     0,    72,     5,
       0,     0,    71,    55,    72,     0,     0,    72,     0,     0,
       0,    72,     0,     0,     0,    70,     0,     0,   192,    73,
      73,     0,     0,     0,    66,     0,    95,    96,    97,    98,
      99,   100,   101,   102,     0,     0,     0,     0,     0,     0,
       0,    69,     0,     0,     0,    71,     0,     0,     0,     0,
     103,     0,     0,     0,   655,     0,    71,    72,     0,     0,
       0,    74,     0,     0,     0,    72,     0,     0,     0,     0,
     108,   108,     0,     0,    56,    57,     0,     0,    58,    70,
      73,     0,     0,   191,   108,   705,     0,   196,    69,   199,
      74,   201,   202,   203,   204,   205,     0,     0,     0,    69,
       0,     0,    73,     0,    73,     0,   684,   685,    72,    73,
       0,     0,   687,     0,     0,    73,     0,    73,     0,    72,
       0,     0,    73,    59,   688,   689,    70,     0,    73,     0,
       0,    73,     0,    71,     0,    73,     0,    70,     0,     0,
      69,     0,     0,     0,     0,     0,    60,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    85,     0,    86,    87,     0,   228,   318,    62,    63,
      64,    65,    66,    67,     0,   326,     0,   328,    70,    88,
       0,    73,     0,    89,    90,    91,    92,    93,     0,    73,
       0,     0,     0,     0,     0,     0,    72,    71,     0,   382,
     383,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     390,   391,   392,   393,     0,     0,     0,     0,     0,     0,
     788,   789,   790,     0,   792,   793,     0,     0,     0,   797,
     798,     0,    73,     0,   690,   365,   802,     0,   804,   691,
     806,     0,     0,    73,    71,     0,   692,   693,    69,   694,
       0,   695,     0,   696,     0,    71,     0,     0,     0,     0,
      72,     0,     0,   697,     0,     0,     0,     0,     0,     0,
       0,     0,   684,   685,   686,     0,   762,    69,   687,     0,
       0,     0,     0,     0,     0,     0,    70,     0,     0,     0,
     688,   689,     0,   848,   849,     0,    71,     0,     0,   853,
       0,     0,     0,     0,     0,     0,     0,    72,   858,   859,
     861,   862,     0,     0,     0,    70,     0,   715,    72,     0,
      73,     0,     0,   875,   192,     0,     0,     0,     0,     0,
      66,     0,    95,    96,    97,    98,    99,   100,   101,   102,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    72,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    85,     0,    86,    87,     0,     0,     0,   571,
     572,     0,   904,     0,    73,     0,     0,   906,   574,   575,
      88,     0,     0,     0,    89,    90,    91,    92,    93,     0,
     690,   365,     0,     0,    71,   691,   920,     0,   586,     0,
     326,     0,   692,   693,   928,   694,   930,   695,   932,   696,
       0,    85,     0,    86,    87,     0,   604,     0,     0,   697,
       0,    73,     0,    71,     0,     0,     0,     0,     0,    88,
       0,     0,    73,    89,    90,    91,    92,    93,   957,     0,
     960,     0,     0,   964,     0,   967,     0,     0,     0,     0,
     972,   973,     0,     0,     0,     0,     0,    72,     0,     0,
       0,     0,     0,     0,     0,    85,     0,   835,    87,   614,
       0,   618,     0,    73,     0,     0,   988,     0,     0,     0,
     990,     0,     0,    88,     0,   994,    72,    89,    90,    91,
      92,    93,     0,     0,     0,   998,   999,     0,     0,     0,
       0,  1003,     0,  1005,  1006,     0,     0,     0,     0,     0,
       0,     0,  1012,     0,     0,     0,     0,     0,  1016,     0,
       0,  1018,   964,     0,     0,    94,  1020,     0,     0,     0,
       0,    66,     0,    95,    96,    97,    98,    99,   100,   101,
     102,  1026,     0,  1027,    85,  1029,   941,    87,     0,  1032,
       0,     0,     0,   684,   685,   686,  1034,     0,     0,   687,
       0,  1036,    88,     0,     0,     0,    89,    90,    91,    92,
      93,   688,   689,    85,   192,   943,    87,     0,     0,     0,
      66,    73,    95,    96,    97,    98,    99,   100,   101,   102,
     729,    88,     0,     0,     0,    89,    90,    91,    92,    93,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      73,     0,   358,   359,   360,     0,   747,     0,   361,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    94,     0,
     362,   363,     0,     0,    66,     0,    95,    96,    97,    98,
      99,   100,   101,   102,  -402,     0,     0,  -402,  -402,  -402,
    -402,  -402,  -402,     0,  -402,  -402,  -402,  -402,     0,  -402,
       0,  -402,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,  -402,  -402,  -402,     0,     0,     0,     0,     0,
       0,   690,   365,     0,     0,     0,   691,     0,     0,  -402,
    -402,  -402,     0,   692,   693,     0,   694,     0,   695,     0,
     696,     0,     0,  -402,  -402,     0,     0,    94,     0,     0,
     697,     0,   698,    66,     0,    95,    96,    97,    98,    99,
     100,   101,   102,     0,     0,     0,     0,   827,     0,     0,
       0,   834,     0,     0,     0,   836,    94,   840,     0,     0,
     364,   365,    66,     0,    95,    96,    97,    98,    99,   100,
     101,   102,   366,   367,     0,   368,     0,   369,     0,   370,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   371,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,  -402,  -402,     0,     0,  -402,  -402,     0,
       0,     0,     0,     0,     0,  -402,  -402,     0,  -402,     0,
    -402,     0,  -402,     0,     0,     0,     0,     0,     0,   684,
     685,   686,  -402,     0,  -402,   687,     0,  -402,  -402,  -402,
    -402,  -402,     0,     0,     0,     0,     0,   688,   689,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   515,   516,   517,   518,     0,     0,   519,     0,
     520,   521,   522,   523,   524,     0,     0,   113,     0,     0,
     525,   526,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   942,   944,     0,
     114,  1010,  1011,     0,     0,     0,   527,     0,   528,     0,
       0,   953,   529,     0,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,     0,     0,   138,   139,     0,     0,
      58,   140,     0,   141,   142,   143,     0,   690,   365,     0,
       0,   530,   691,     0,     0,     0,     0,     0,     0,   692,
     693,     0,   694,     0,   695,     0,   696,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   697,     0,   807,     0,
       0,     0,   144,   145,   146,    59,   531,   532,   533,     0,
     534,     0,     0,     0,     0,   535,     0,     0,   147,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    60,     0,
     536,   537,   538,     0,     0,   539,   148,     0,   149,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   150,   151,
      62,    63,    64,    65,    66,    67,   515,   516,   517,   518,
       0,     0,   519,     0,   520,   521,   522,   523,   524,     0,
       0,   113,     0,     0,   525,   526,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   278,     0,     0,     0,     0,
       0,     0,     0,     0,   114,     0,     0,     0,     0,     0,
     527,     0,   528,   279,     0,     0,   529,     0,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     0,     0,
     138,   139,     0,     0,    58,   140,     0,   141,   142,   143,
       0,   653,     0,     0,     0,   530,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   282,   283,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     284,   285,   286,   287,   288,     0,   144,   145,   146,    59,
     531,   532,   533,     0,   534,     0,     0,     0,     0,   535,
     304,   305,   147,     0,   308,   309,   310,   311,   312,   313,
       0,     0,    60,     0,   536,   537,   538,     0,     0,   539,
     148,     0,   149,     0,     0,     0,     0,     0,   314,   315,
       0,     0,   654,   151,    62,    63,    64,    65,    66,    67,
     515,   516,   517,   518,     0,     0,   519,     0,   520,   521,
     522,   523,   524,     0,     0,   113,     0,     0,   525,   526,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   278,
       0,     0,     0,     0,     0,     0,     0,     0,   114,     0,
       0,     0,     0,     0,   527,     0,   528,   279,     0,     0,
     529,     0,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,     0,     0,   138,   139,     0,     0,    58,   140,
       0,   141,   142,   143,     0,     0,     0,     0,     0,   530,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     282,   283,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   284,   285,   286,   287,   288,     0,
     144,   145,   146,    59,   531,   532,   533,     0,   534,     0,
       0,     0,     0,   535,   304,     0,   147,     0,   308,   309,
     310,   311,   312,   313,     0,     0,    60,     0,   536,   537,
     538,     0,     0,   539,   148,     0,   149,     0,     0,     0,
       0,     0,   314,   315,     0,     0,   150,   151,    62,    63,
      64,    65,    66,    67,   515,   516,   517,   518,     0,     0,
     519,     0,   520,   521,   522,   523,   524,     0,     0,   113,
       0,     0,   525,   526,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   114,     0,     0,     0,     0,     0,   527,     0,
     528,     0,     0,     0,   529,     0,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,     0,     0,   138,   139,
       0,     0,    58,   140,     0,   141,   142,   143,     0,     0,
       0,     0,     0,   530,     0,     0,   684,   685,   686,     0,
       0,     0,   687,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   688,   689,     0,     0,     0,     0,
       0,     0,     0,     0,   144,   145,   146,    59,   531,   532,
     533,     0,   534,     0,     0,     0,     0,   535,     0,     0,
     147,   110,     0,   111,     0,     0,   112,     0,     0,     0,
      60,     0,   536,   537,   538,   113,     0,   539,   148,     0,
     149,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     654,   151,    62,    63,    64,    65,    66,    67,   114,   830,
     831,     0,     0,     0,   115,     0,   116,     0,     0,     0,
     117,     0,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,     0,     0,   138,   139,     0,     0,    58,   140,
       0,   141,   142,   143,   690,   365,     0,     0,     0,   691,
       0,     0,   684,   685,   686,     0,   692,   693,   687,   694,
       0,   695,     0,   696,     0,     0,     0,     0,     0,     0,
     688,   689,     0,   697,     0,  1028,     0,     0,     0,     0,
     144,   145,   146,    59,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   147,   110,     0,   111,
     911,   912,   112,     0,     0,     0,    60,     0,     0,     0,
       0,   113,   215,     0,   148,     0,   149,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   150,   151,    62,    63,
      64,    65,    66,    67,   114,     0,     0,     0,     0,     0,
     115,     0,   116,     0,     0,     0,   117,     0,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     0,     0,
     138,   139,     0,     0,    58,   140,     0,   141,   142,   143,
     690,   365,     0,     0,     0,   691,     0,     0,     0,     0,
       0,     0,   692,   693,     0,   694,     0,   695,     0,   696,
       0,     0,   684,   685,   686,     0,   908,     0,   687,   697,
       0,     0,     0,     0,     0,     0,   144,   145,   146,    59,
     688,   689,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   147,   110,     0,   111,     0,     0,   112,     0,
       0,     0,    60,     0,     0,     0,     0,   113,     0,     0,
     148,     0,   149,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   150,   151,    62,    63,    64,    65,    66,    67,
     114,     0,     0,   658,     0,     0,   115,     0,   116,     0,
       0,     0,   117,     0,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,     0,     0,   138,   139,     0,     0,
      58,   140,     0,   141,   142,   143,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     690,   365,     0,     0,     0,   691,     0,     0,     0,     0,
       0,     0,   692,   693,     0,   694,     0,   695,     0,   696,
       0,     0,   144,   145,   146,    59,     0,     0,     0,   697,
       0,     0,     0,     0,     0,     0,     0,     0,   147,   110,
       0,   111,     0,   669,   112,     0,     0,     0,    60,     0,
       0,     0,     0,   113,     0,     0,   148,     0,   149,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   150,   151,
      62,    63,    64,    65,    66,    67,   114,     0,     0,     0,
       0,     0,   115,     0,   116,     0,     0,     0,   117,     0,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
       0,     0,   138,   139,     0,     0,    58,   140,     0,   141,
     142,   143,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   684,   685,   686,     0,   951,     0,   687,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   688,
     689,     0,     0,     0,     0,     0,     0,     0,   144,   145,
     146,    59,     0,     0,   358,   359,   360,     0,     0,     0,
     361,     0,     0,     0,   147,   110,     0,   111,     0,     0,
     112,     0,   362,   363,    60,     0,     0,     0,     0,   113,
       0,     0,   148,     0,   149,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   150,   151,    62,    63,    64,    65,
      66,    67,   114,     0,     0,     0,     0,     0,   115,     0,
     116,     0,     0,     0,   117,     0,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,     0,     0,   138,   139,
       0,     0,    58,   140,     0,   141,   142,   143,   276,   690,
     365,     0,     0,   277,   691,   278,     0,   684,   685,   686,
       0,   692,   693,   687,   694,     0,   695,     0,   696,     0,
       0,     0,     0,   279,     0,   688,   689,     0,   697,     0,
       0,     0,   364,   365,   144,   145,   146,    59,     0,     0,
     358,   359,     0,     0,   366,   367,   361,   368,     0,   369,
     147,   370,   280,   281,     0,     0,     0,     0,   362,   363,
      60,   371,     0,     0,     0,     0,     0,     0,   148,     0,
     149,     0,     0,     0,     0,     0,   282,   283,     0,     0,
     150,   151,    62,    63,    64,    65,    66,    67,     0,     0,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     276,     0,     0,     0,     0,   277,     0,   278,     0,     0,
       0,     0,     0,     0,     0,   690,   365,     0,   314,   315,
     691,     0,     0,     0,     0,   279,     0,   692,   693,     0,
     694,     0,   695,     0,   696,     0,     0,     0,     0,   983,
       0,     0,     0,     0,   697,     0,     0,     0,   364,   365,
       0,     0,     0,     0,   280,   281,     0,     0,     0,     0,
     366,   367,     0,   368,     0,   369,     0,   370,     0,     0,
       0,     0,     0,   278,     0,     0,     0,   371,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   279,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   276,     0,     0,     0,     0,   277,     0,   278,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     314,   315,     0,     0,   282,   283,     0,   279,     0,     0,
       0,  1000,     0,     0,  1001,     0,     0,     0,   284,   285,
     286,   287,   288,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   280,   281,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     282,   283,     0,     0,     0,     0,   314,   315,     0,     0,
       0,     0,     0,     0,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   276,     0,     0,     0,     0,   277,
       0,   278,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   314,   315,     0,     0,     0,     0,     0,   279,
       0,     0,     0,   787,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   282,   283,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   276,     0,     0,     0,
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,     0,     0,     0,     0,
       0,   279,     0,     0,     0,   924,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   276,     0,
       0,     0,     0,   277,     0,   278,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   314,   315,     0,     0,
       0,     0,     0,   279,     0,     0,     0,   925,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   280,   281,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   282,   283,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     276,     0,     0,     0,     0,   277,     0,   278,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
       0,     0,     0,     0,     0,   279,     0,     0,     0,   970,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   281,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,     0,   276,     0,     0,     0,     0,   277,     0,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     314,   315,   876,   877,     0,     0,     0,     0,   279,     0,
       0,  1030,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   276,     0,     0,     0,     0,
     277,     0,   278,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,   707,     0,     0,     0,   878,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   282,   283,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   276,     0,     0,
       0,     0,   277,     0,   278,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   314,   315,     0,     0,     0,
       0,   708,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   276,
       0,     0,     0,     0,   277,     0,   278,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   314,   315,     0,
       0,   713,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,     0,   702,     0,     0,   314,
     315,     0,     0,   882,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   723,   724,     0,     0,   280,   281,     0,     0,     0,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   279,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,   703,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,     0,     0,     0,     0,     0,   276,     0,
     704,   282,   283,   277,   394,   278,     0,     0,     0,     0,
       0,   314,   315,   702,     0,   284,   285,   286,   287,   288,
       0,     0,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,     0,     0,     0,     0,     0,
       0,     0,   280,   281,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   282,   283,     0,     0,
       0,     0,     0,     0,     0,     0,   703,     0,     0,     0,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
       0,   276,     0,     0,     0,     0,   277,   704,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
     399,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   400,   401,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,     0,
     278,     0,     0,     0,     0,     0,     0,     0,   702,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,   703,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,     0,   276,     0,     0,     0,
       0,   277,   704,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,     0,     0,     0,     0,     0,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   723,   724,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   276,     0,
       0,     0,     0,   277,     0,   278,     0,     0,     0,     0,
       0,     0,     0,   774,     0,     0,   314,   315,     0,     0,
       0,     0,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   775,     0,     0,
       0,     0,   280,   281,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   282,   283,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
       0,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
     879,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,     0,   276,     0,     0,     0,     0,   277,
       0,   278,     0,   954,   880,     0,     0,     0,     0,     0,
       0,   314,   315,   955,     0,     0,     0,     0,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   282,   283,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,     0,   276,     0,     0,
       0,     0,   277,     0,   278,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,   976,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   276,
       0,     0,     0,     0,   277,   322,   278,     0,     0,     0,
     977,     0,     0,     0,     0,     0,     0,   314,   315,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,   394,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,     0,   276,     0,     0,     0,     0,   277,
       0,   278,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,   397,     0,     0,     0,     0,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   282,   283,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   276,     0,     0,     0,
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,     0,     0,     0,     0,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   411,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   276,     0,
       0,     0,     0,   277,     0,   278,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   314,   315,     0,     0,
       0,     0,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   280,   281,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   282,   283,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
       0,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,   412,     0,     0,   314,   315,
     430,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,     0,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   276,     0,     0,     0,     0,
     277,     0,   278,     0,     0,     0,   510,     0,     0,     0,
       0,     0,     0,   314,   315,     0,     0,     0,     0,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   282,   283,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   276,     0,     0,
       0,     0,   277,     0,   278,     0,     0,     0,     0,   598,
       0,     0,     0,     0,     0,   314,   315,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   276,
       0,     0,     0,     0,   277,   610,   278,     0,     0,     0,
       0,   601,     0,     0,     0,     0,     0,   314,   315,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   612,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,     0,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   276,     0,     0,     0,     0,
     277,     0,   278,     0,     0,     0,   709,     0,     0,     0,
       0,   712,     0,   314,   315,     0,     0,     0,     0,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   282,   283,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,     0,     0,   276,
       0,     0,     0,     0,   277,     0,   278,     0,     0,     0,
       0,     0,     0,     0,     0,   314,   315,   720,     0,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   401,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,   730,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   276,     0,     0,     0,     0,
     277,     0,   278,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,     0,     0,     0,     0,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   282,   283,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   276,     0,     0,
       0,     0,   277,     0,   278,     0,     0,     0,     0,     0,
       0,     0,   778,     0,     0,   314,   315,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   276,
       0,     0,     0,     0,   277,     0,   278,     0,     0,     0,
     779,     0,     0,     0,     0,     0,     0,   314,   315,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,   780,   774,     0,     0,   314,
     315,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,   824,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   276,     0,     0,     0,     0,
     277,   826,   278,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,     0,     0,     0,     0,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   282,   283,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   276,     0,     0,
       0,     0,   277,     0,   278,     0,   842,     0,     0,     0,
       0,     0,     0,     0,     0,   314,   315,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,     0,
     276,     0,     0,     0,     0,   277,     0,   278,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   314,   315,   881,
       0,     0,     0,     0,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   281,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   276,     0,     0,     0,     0,   277,     0,   278,
       0,   884,     0,     0,     0,     0,     0,     0,     0,     0,
     314,   315,     0,     0,     0,     0,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   280,   281,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     282,   283,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   276,     0,     0,     0,     0,   277,
       0,   278,     0,     0,     0,     0,     0,     0,     0,     0,
     885,     0,   314,   315,     0,     0,     0,     0,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   282,   283,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,     0,   276,     0,     0,
       0,     0,   277,     0,   278,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,   886,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   276,
       0,     0,     0,     0,   277,     0,   278,     0,   887,     0,
       0,     0,     0,     0,     0,     0,     0,   314,   315,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,     0,     0,   276,     0,     0,     0,     0,   277,     0,
     278,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,   888,     0,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,     0,     0,   276,     0,     0,
       0,     0,   277,     0,   278,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,   889,     0,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   276,
       0,     0,     0,     0,   277,     0,   278,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   314,   315,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   890,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,     0,
     278,     0,     0,     0,     0,     0,     0,     0,   914,     0,
     892,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   276,     0,     0,     0,     0,
     277,     0,   278,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,     0,     0,     0,     0,     0,
     279,   923,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   282,   283,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   276,     0,     0,
       0,     0,   277,   949,   278,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   314,   315,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   276,
       0,     0,     0,     0,   277,     0,   278,     0,   950,     0,
       0,     0,     0,     0,     0,     0,     0,   314,   315,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,   974,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,     0,
     278,     0,   975,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   276,     0,     0,     0,     0,
     277,     0,   278,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,     0,     0,     0,     0,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   282,   283,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   276,     0,     0,
       0,     0,   277,     0,   278,     0,     0,     0,     0,     0,
       0,   978,     0,     0,     0,   314,   315,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   992,     0,     0,     0,
       0,   280,   281,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   276,
       0,     0,     0,     0,   277,     0,   278,     0,  1015,     0,
       0,     0,     0,     0,     0,     0,     0,   314,   315,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
    1017,     0,     0,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,     0,
     278,     0,  1019,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,     0,   276,     0,     0,     0,
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,  1022,     0,     0,     0,     0,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   276,     0,
       0,     0,     0,   277,     0,   278,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   314,   315,     0,     0,
       0,     0,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   280,   281,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   282,   283,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     276,     0,     0,     0,     0,   277,     0,   278,     0,     0,
       0,  1023,     0,     0,     0,     0,     0,     0,   314,   315,
       0,     0,     0,     0,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   281,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   276,     0,     0,     0,     0,   277,     0,   278,
       0,  1035,  1024,     0,     0,     0,     0,     0,     0,     0,
     314,   315,     0,     0,     0,     0,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   280,   281,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     282,   283,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   276,     0,     0,     0,     0,   277,
       0,   278,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   314,   315,     0,     0,     0,     0,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   282,   283,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   276,     0,     0,     0,
       0,   933,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,     0,     0,     0,     0,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   276,     0,
       0,     0,     0,     0,     0,   278,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   314,   315,     0,     0,
       0,     0,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   280,   281,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   282,   283,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     276,     0,     0,     0,     0,     0,     0,   278,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
       0,     0,     0,     0,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   281,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   282,   283,
     276,     0,     0,     0,     0,     0,     0,   278,     0,     0,
       0,     0,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   279,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,     0,     0,     0,     0,     0,     0,     0,     0,
     276,     0,     0,     0,   280,   281,     0,   278,     0,     0,
     314,   315,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   279,     0,     0,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   280,   281,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,     0,     0,     0,     0,     0,   276,   282,   283,
       0,     0,     0,     0,   278,     0,     0,     0,     0,     0,
     314,   315,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   279,   295,     0,     0,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,     0,   278,     0,     0,     0,     0,     0,
     314,   315,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   279,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,     0,
       0,   280,   281,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,     0,
       0,     0,     0,     0,     0,   282,   283,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   314,   315,   284,
     285,   286,   287,   288,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   314,   315
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
        50,   482,    79,   146,   194,    55,   331,   140,   653,   186,
     334,   145,     2,    31,    33,   163,    13,    38,    30,    12,
      31,   169,   170,    30,   113,   165,    12,   659,   180,   181,
      59,   183,   184,    12,    49,   358,   359,   360,    13,   362,
     363,    13,    13,    17,   367,   368,    59,   314,    13,     1,
     373,   180,     4,     5,    13,    30,    13,    47,   147,    49,
     118,   111,   112,   113,    47,    19,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   282,    79,
      13,   149,    13,    45,   115,   180,    13,   147,    17,   180,
      18,    17,    54,    19,    18,   182,    18,    30,     3,     4,
       5,    17,     7,    12,     0,   146,    33,   146,   149,   180,
       0,   315,   172,     9,   174,    77,   114,    46,    27,     9,
     397,   146,    31,   146,   146,   185,    90,    91,    37,   146,
      46,    14,    15,    16,    17,    44,    17,    20,    19,   146,
     104,   188,   140,    48,   151,   173,   149,     0,   146,    32,
      33,   180,   173,   149,   488,   490,   255,   256,    21,    18,
     149,   146,   144,   262,   146,   146,   353,    18,   132,   133,
     503,   146,   505,   506,   507,   508,   509,   146,   277,   146,
      18,    27,   182,   176,   177,   178,   179,   180,   152,   153,
     832,    33,    18,    33,    27,   255,   256,   852,    18,    18,
     260,    18,   262,   263,   188,   189,   190,   191,   192,   193,
     194,   195,    59,   146,   274,   146,   276,   277,   146,   279,
     280,   281,   146,   149,   146,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   412,   399,   898,   356,   408,   142,
     404,   180,    27,    14,    15,    16,    17,    27,   149,   174,
     175,   118,    33,    18,   417,    17,    90,    19,   769,    45,
      18,    32,    33,   146,    44,    33,   430,   146,    19,   172,
     104,    13,    33,    14,    15,   146,   356,   180,   146,    21,
     146,   361,   149,   143,   364,   365,   366,   147,   146,   369,
     370,   371,    33,   146,   154,   155,   314,   157,   476,   159,
     146,   161,    17,   146,    90,   173,   146,   146,   146,   146,
     118,   171,    13,   151,    12,    33,   396,    13,   104,   180,
     400,   401,   102,   403,    17,   405,    19,   407,   510,   409,
     173,   411,    33,   515,   516,   517,   416,    33,   146,   419,
      91,   149,   422,   525,   526,   425,    98,   427,   428,     0,
     332,  1013,    13,   573,   374,   708,   709,   375,     9,   410,
     713,   146,   143,   149,    19,   538,   147,    14,    15,    16,
      17,   142,    33,    88,    89,   143,   157,    92,   159,   397,
     161,   146,   133,    33,   146,    32,    33,   149,   146,   157,
     171,   159,    13,   161,   146,    33,   749,   149,    33,   417,
     480,   172,   143,   171,    12,   423,   486,   487,    12,   180,
      15,   518,    33,   154,   155,   495,   157,   536,   159,    17,
     161,   501,   137,    27,    17,   143,    34,    31,    33,   147,
     171,    13,    17,    37,    19,   946,    91,   615,   518,   519,
      44,   159,    12,   161,   524,   160,    56,    57,   528,   529,
     530,    33,   118,   171,   534,   535,   536,   537,    27,   539,
      12,   176,   177,   178,   179,   180,   484,   182,   183,   184,
     185,   186,   187,    13,    13,    44,   527,   132,   133,    17,
     146,    19,   502,   149,   504,   180,   553,   554,   555,   842,
     557,   558,    19,    33,    33,   142,   563,   564,   518,    92,
     143,   568,   180,    13,   147,    27,    17,    13,   143,    31,
      17,   531,   532,   533,    13,    37,   159,    17,   161,   143,
     538,   601,    44,    33,   159,   172,   161,    33,   171,    62,
      63,   884,   612,   180,    33,   159,   171,   161,   143,    17,
     169,   621,   147,   172,   137,    17,    17,   171,   726,   154,
     155,    98,   157,   144,   159,   146,   161,   180,    65,   737,
      67,   641,   180,    90,    91,   645,   171,   160,   635,   180,
      36,   638,   639,   640,    37,   642,   643,   104,   180,    13,
     102,    88,    89,   146,   180,    92,   149,   180,    31,   182,
     183,   184,   185,   186,   187,    13,   149,   779,   780,    33,
     778,   128,   129,   130,   131,   132,   133,   687,    17,    13,
     690,   691,   692,    38,   180,   695,   696,   697,   146,    92,
      18,   149,    13,    48,   180,   152,   153,   707,   648,    27,
     137,   711,   712,    31,   714,    13,   716,   146,   718,    37,
     149,   721,    33,   723,   724,   502,    44,   504,   728,   768,
     632,    13,   732,   160,    13,    33,    81,    82,   738,    17,
      85,   679,    87,    13,    89,    90,    91,    92,    93,   149,
     146,    33,   752,   180,    33,   182,   183,   184,   185,   186,
     187,    27,    27,    33,   172,    31,    31,   173,   768,   114,
     115,    37,    37,   773,    13,   775,   776,   180,    44,    44,
      33,   180,    47,   180,   784,   785,   786,    14,    15,    67,
     138,   139,   140,    59,    33,   140,    50,    12,   180,   144,
     145,   146,   886,   180,    12,   805,    33,   180,    46,   118,
      88,    89,   149,    18,    92,   173,   756,   915,   163,   149,
     165,    15,   809,   810,   169,   170,   813,   180,   173,    12,
     175,    12,    49,    21,   100,    12,    59,    94,     3,    33,
      12,   841,     7,     8,   844,    10,    11,    17,    17,   194,
     850,    27,   197,   198,    20,    31,   843,   857,    20,   137,
     118,    37,    38,   208,   209,   210,   211,    27,    44,    55,
     180,   871,   176,   177,   178,   179,   876,   877,   146,   879,
      38,   881,   160,    48,    17,   885,    20,   165,    51,    27,
     890,   891,     9,    33,   933,   756,   353,   701,    49,   899,
     900,    31,   180,    45,   182,   183,   184,   185,   186,   187,
     141,   911,    38,   952,   385,   902,   143,   917,    38,   194,
     147,    77,    18,   923,    33,    17,    54,   154,   155,   627,
     157,    27,   159,   933,   161,    31,    27,   282,   897,   744,
      31,    37,   838,  1025,   171,   632,    37,   934,    44,   143,
     766,   527,   952,    44,   410,   955,   114,   115,   958,   937,
     154,   155,   726,   157,   403,   159,   966,   161,   537,   314,
     315,   915,   426,    65,   974,    67,   976,   171,    -1,    -1,
     980,    -1,   140,    21,   984,    17,   144,   145,   146,    27,
      -1,    -1,    -1,    31,   114,   115,    88,    89,    -1,    37,
      92,  1001,    -1,   143,    -1,   163,    44,    -1,    -1,    -1,
      -1,   169,   170,    -1,   154,   155,    -1,   157,    -1,   159,
     140,   161,  1022,    -1,   144,   145,   146,    -1,    -1,    -1,
     375,   171,   377,   378,    -1,    67,    38,    -1,    -1,    -1,
     385,   386,   387,   163,    -1,   137,    17,    -1,    -1,   169,
     170,    -1,   397,    -1,   399,    -1,    88,    89,    -1,   404,
      92,   406,    -1,   408,    -1,   410,    27,   412,   160,    -1,
      31,    -1,   417,    -1,    -1,    -1,    37,    38,   423,   424,
      -1,   426,    -1,    44,    -1,   430,    -1,    -1,   180,    -1,
     182,   183,   184,   185,   186,   187,    -1,    -1,    -1,    38,
     176,   177,   178,   179,   180,   137,    -1,    -1,    -1,    -1,
      -1,    -1,   114,   115,    -1,    -1,    -1,    88,    89,    -1,
      -1,    92,    -1,    -1,   282,    -1,    -1,    -1,   160,    -1,
      -1,   476,   477,    -1,   479,    -1,    -1,    -1,   140,   484,
      -1,    -1,   144,   145,   146,    -1,    -1,    -1,   180,    -1,
     182,   183,   184,   185,   186,   187,   314,   315,    -1,    -1,
      -1,   163,   282,    -1,    -1,    -1,   137,   169,   170,    -1,
      -1,    -1,    -1,    -1,    -1,   114,   115,    -1,    -1,    -1,
       3,    -1,   527,     6,     7,     8,     9,    10,    11,   160,
      13,    -1,    -1,   538,   314,   315,    -1,    -1,    -1,    -1,
      -1,   140,    -1,    -1,    -1,   144,   145,   146,    -1,   180,
      -1,   182,   183,   184,   185,   186,   187,   375,    -1,    17,
      -1,    -1,    -1,    38,   163,    48,    -1,    -1,   573,    -1,
     169,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   397,
      -1,   399,    -1,    -1,    -1,    -1,   404,    -1,    -1,    -1,
      -1,    -1,   410,   598,   412,   375,    -1,    -1,    -1,   417,
      -1,    -1,    -1,    -1,    -1,   423,    -1,    -1,   426,    -1,
     615,    -1,   430,    -1,    -1,    -1,    -1,   397,    -1,   399,
     282,    -1,    -1,    -1,   404,    -1,    -1,    -1,    -1,    -1,
     410,    -1,   412,    -1,    92,    -1,    -1,   417,    -1,   114,
     115,    -1,    -1,   423,    -1,    -1,   426,    -1,    -1,    -1,
     430,    -1,   314,   315,    -1,    -1,    -1,    -1,   476,    -1,
      -1,    -1,    -1,    -1,    -1,   140,   484,    -1,    -1,   144,
     145,   146,    -1,    -1,   679,    17,    -1,    19,    20,   137,
      -1,    -1,    19,   282,    -1,    -1,    -1,    -1,   163,    -1,
      -1,    -1,    -1,    35,   169,   170,   476,    39,    40,    41,
      42,    43,   160,    -1,   484,    47,    -1,    -1,   352,   527,
      -1,    -1,    -1,   375,    -1,   314,   315,    -1,    -1,    -1,
     538,   726,   180,    -1,   182,   183,   184,   185,   186,   187,
     735,    -1,   737,    -1,   739,   397,    -1,   399,   743,    -1,
     745,    -1,   404,    -1,    -1,    -1,    -1,   527,   410,    -1,
     412,    -1,    -1,    90,    91,   417,    -1,    -1,   538,    -1,
      -1,   423,    -1,    -1,   426,    -1,    -1,   104,   430,    -1,
      -1,    -1,    -1,   778,     3,    -1,   375,     6,     7,     8,
       9,    10,    11,    -1,    13,    -1,    -1,   124,    -1,    -1,
      -1,   128,   129,   130,   131,   132,   133,   615,   397,    -1,
     399,    -1,    -1,    -1,    -1,   404,    -1,   282,    -1,    -1,
      -1,   410,    -1,   412,   476,   152,   153,    -1,   417,    48,
      -1,    -1,   484,    17,   423,    -1,    -1,   426,    -1,    -1,
      -1,   430,    -1,    -1,    -1,   615,    -1,    -1,   180,   314,
     315,    -1,    -1,    -1,   186,    -1,   188,   189,   190,   191,
     192,   193,   194,   195,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   679,    -1,    -1,    -1,   527,    -1,    -1,    -1,    -1,
      48,    -1,    -1,    -1,   518,    -1,   538,   476,    -1,    -1,
      -1,   886,    -1,    -1,    -1,   484,    -1,    -1,    -1,    -1,
     895,   896,    -1,    -1,    88,    89,    -1,    -1,    92,   679,
     375,    -1,    -1,    81,   909,   549,    -1,    85,   726,    87,
     915,    89,    90,    91,    92,    93,    -1,    -1,    -1,   737,
      -1,    -1,   397,    -1,   399,    -1,    14,    15,   527,   404,
      -1,    -1,    20,    -1,    -1,   410,    -1,   412,    -1,   538,
      -1,    -1,   417,   137,    32,    33,   726,    -1,   423,    -1,
      -1,   426,    -1,   615,    -1,   430,    -1,   737,    -1,    -1,
     778,    -1,    -1,    -1,    -1,    -1,   160,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    17,    -1,    19,    20,    -1,   180,   165,   182,   183,
     184,   185,   186,   187,    -1,   173,    -1,   175,   778,    35,
      -1,   476,    -1,    39,    40,    41,    42,    43,    -1,   484,
      -1,    -1,    -1,    -1,    -1,    -1,   615,   679,    -1,   197,
     198,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     208,   209,   210,   211,    -1,    -1,    -1,    -1,    -1,    -1,
     684,   685,   686,    -1,   688,   689,    -1,    -1,    -1,   693,
     694,    -1,   527,    -1,   142,   143,   700,    -1,   702,   147,
     704,    -1,    -1,   538,   726,    -1,   154,   155,   886,   157,
      -1,   159,    -1,   161,    -1,   737,    -1,    -1,    -1,    -1,
     679,    -1,    -1,   171,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    14,    15,    16,    -1,    18,   915,    20,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   886,    -1,    -1,    -1,
      32,    33,    -1,   757,   758,    -1,   778,    -1,    -1,   763,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   726,   772,   773,
     774,   775,    -1,    -1,    -1,   915,    -1,   173,   737,    -1,
     615,    -1,    -1,   787,   180,    -1,    -1,    -1,    -1,    -1,
     186,    -1,   188,   189,   190,   191,   192,   193,   194,   195,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   778,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    17,    -1,    19,    20,    -1,    -1,    -1,   377,
     378,    -1,   846,    -1,   679,    -1,    -1,   851,   386,   387,
      35,    -1,    -1,    -1,    39,    40,    41,    42,    43,    -1,
     142,   143,    -1,    -1,   886,   147,   870,    -1,   406,    -1,
     408,    -1,   154,   155,   878,   157,   880,   159,   882,   161,
      -1,    17,    -1,    19,    20,    -1,   424,    -1,    -1,   171,
      -1,   726,    -1,   915,    -1,    -1,    -1,    -1,    -1,    35,
      -1,    -1,   737,    39,    40,    41,    42,    43,   912,    -1,
     914,    -1,    -1,   917,    -1,   919,    -1,    -1,    -1,    -1,
     924,   925,    -1,    -1,    -1,    -1,    -1,   886,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    17,    -1,    19,    20,   477,
      -1,   479,    -1,   778,    -1,    -1,   950,    -1,    -1,    -1,
     954,    -1,    -1,    35,    -1,   959,   915,    39,    40,    41,
      42,    43,    -1,    -1,    -1,   969,   970,    -1,    -1,    -1,
      -1,   975,    -1,   977,   978,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   986,    -1,    -1,    -1,    -1,    -1,   992,    -1,
      -1,   995,   996,    -1,    -1,   180,  1000,    -1,    -1,    -1,
      -1,   186,    -1,   188,   189,   190,   191,   192,   193,   194,
     195,  1015,    -1,  1017,    17,  1019,    19,    20,    -1,  1023,
      -1,    -1,    -1,    14,    15,    16,  1030,    -1,    -1,    20,
      -1,  1035,    35,    -1,    -1,    -1,    39,    40,    41,    42,
      43,    32,    33,    17,   180,    19,    20,    -1,    -1,    -1,
     186,   886,   188,   189,   190,   191,   192,   193,   194,   195,
     598,    35,    -1,    -1,    -1,    39,    40,    41,    42,    43,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     915,    -1,    14,    15,    16,    -1,    18,    -1,    20,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,    -1,
      32,    33,    -1,    -1,   186,    -1,   188,   189,   190,   191,
     192,   193,   194,   195,     3,    -1,    -1,     6,     7,     8,
       9,    10,    11,    -1,    13,    14,    15,    16,    -1,    18,
      -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    31,    32,    33,    -1,    -1,    -1,    -1,    -1,
      -1,   142,   143,    -1,    -1,    -1,   147,    -1,    -1,    48,
      49,    50,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,    -1,    -1,    62,    63,    -1,    -1,   180,    -1,    -1,
     171,    -1,   173,   186,    -1,   188,   189,   190,   191,   192,
     193,   194,   195,    -1,    -1,    -1,    -1,   735,    -1,    -1,
      -1,   739,    -1,    -1,    -1,   743,   180,   745,    -1,    -1,
     142,   143,   186,    -1,   188,   189,   190,   191,   192,   193,
     194,   195,   154,   155,    -1,   157,    -1,   159,    -1,   161,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   171,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   142,   143,    -1,    -1,   146,   147,    -1,
      -1,    -1,    -1,    -1,    -1,   154,   155,    -1,   157,    -1,
     159,    -1,   161,    -1,    -1,    -1,    -1,    -1,    -1,    14,
      15,    16,   171,    -1,   173,    20,    -1,   176,   177,   178,
     179,   180,    -1,    -1,    -1,    -1,    -1,    32,    33,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    14,    15,    16,    17,    -1,    -1,    20,    -1,
      22,    23,    24,    25,    26,    -1,    -1,    29,    -1,    -1,
      32,    33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   895,   896,    -1,
      52,    53,    54,    -1,    -1,    -1,    58,    -1,    60,    -1,
      -1,   909,    64,    -1,    66,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,
      92,    93,    -1,    95,    96,    97,    -1,   142,   143,    -1,
      -1,   103,   147,    -1,    -1,    -1,    -1,    -1,    -1,   154,
     155,    -1,   157,    -1,   159,    -1,   161,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   171,    -1,   173,    -1,
      -1,    -1,   134,   135,   136,   137,   138,   139,   140,    -1,
     142,    -1,    -1,    -1,    -1,   147,    -1,    -1,   150,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   160,    -1,
     162,   163,   164,    -1,    -1,   167,   168,    -1,   170,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,   181,
     182,   183,   184,   185,   186,   187,    14,    15,    16,    17,
      -1,    -1,    20,    -1,    22,    23,    24,    25,    26,    -1,
      -1,    29,    -1,    -1,    32,    33,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    52,    -1,    -1,    -1,    -1,    -1,
      58,    -1,    60,    37,    -1,    -1,    64,    -1,    66,    67,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    -1,    -1,
      88,    89,    -1,    -1,    92,    93,    -1,    95,    96,    97,
      -1,    99,    -1,    -1,    -1,   103,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,    -1,   134,   135,   136,   137,
     138,   139,   140,    -1,   142,    -1,    -1,    -1,    -1,   147,
     124,   125,   150,    -1,   128,   129,   130,   131,   132,   133,
      -1,    -1,   160,    -1,   162,   163,   164,    -1,    -1,   167,
     168,    -1,   170,    -1,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,   180,   181,   182,   183,   184,   185,   186,   187,
      14,    15,    16,    17,    -1,    -1,    20,    -1,    22,    23,
      24,    25,    26,    -1,    -1,    29,    -1,    -1,    32,    33,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    52,    -1,
      -1,    -1,    -1,    -1,    58,    -1,    60,    37,    -1,    -1,
      64,    -1,    66,    67,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,
      -1,    95,    96,    97,    -1,    -1,    -1,    -1,    -1,   103,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,    -1,
     134,   135,   136,   137,   138,   139,   140,    -1,   142,    -1,
      -1,    -1,    -1,   147,   124,    -1,   150,    -1,   128,   129,
     130,   131,   132,   133,    -1,    -1,   160,    -1,   162,   163,
     164,    -1,    -1,   167,   168,    -1,   170,    -1,    -1,    -1,
      -1,    -1,   152,   153,    -1,    -1,   180,   181,   182,   183,
     184,   185,   186,   187,    14,    15,    16,    17,    -1,    -1,
      20,    -1,    22,    23,    24,    25,    26,    -1,    -1,    29,
      -1,    -1,    32,    33,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    52,    -1,    -1,    -1,    -1,    -1,    58,    -1,
      60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    -1,    -1,    88,    89,
      -1,    -1,    92,    93,    -1,    95,    96,    97,    -1,    -1,
      -1,    -1,    -1,   103,    -1,    -1,    14,    15,    16,    -1,
      -1,    -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    32,    33,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   134,   135,   136,   137,   138,   139,
     140,    -1,   142,    -1,    -1,    -1,    -1,   147,    -1,    -1,
     150,    15,    -1,    17,    -1,    -1,    20,    -1,    -1,    -1,
     160,    -1,   162,   163,   164,    29,    -1,   167,   168,    -1,
     170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     180,   181,   182,   183,   184,   185,   186,   187,    52,    53,
      54,    -1,    -1,    -1,    58,    -1,    60,    -1,    -1,    -1,
      64,    -1,    66,    67,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,
      -1,    95,    96,    97,   142,   143,    -1,    -1,    -1,   147,
      -1,    -1,    14,    15,    16,    -1,   154,   155,    20,   157,
      -1,   159,    -1,   161,    -1,    -1,    -1,    -1,    -1,    -1,
      32,    33,    -1,   171,    -1,   173,    -1,    -1,    -1,    -1,
     134,   135,   136,   137,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   150,    15,    -1,    17,
      62,    63,    20,    -1,    -1,    -1,   160,    -1,    -1,    -1,
      -1,    29,    30,    -1,   168,    -1,   170,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   180,   181,   182,   183,
     184,   185,   186,   187,    52,    -1,    -1,    -1,    -1,    -1,
      58,    -1,    60,    -1,    -1,    -1,    64,    -1,    66,    67,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    -1,    -1,
      88,    89,    -1,    -1,    92,    93,    -1,    95,    96,    97,
     142,   143,    -1,    -1,    -1,   147,    -1,    -1,    -1,    -1,
      -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,   161,
      -1,    -1,    14,    15,    16,    -1,    18,    -1,    20,   171,
      -1,    -1,    -1,    -1,    -1,    -1,   134,   135,   136,   137,
      32,    33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   150,    15,    -1,    17,    -1,    -1,    20,    -1,
      -1,    -1,   160,    -1,    -1,    -1,    -1,    29,    -1,    -1,
     168,    -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   180,   181,   182,   183,   184,   185,   186,   187,
      52,    -1,    -1,    55,    -1,    -1,    58,    -1,    60,    -1,
      -1,    -1,    64,    -1,    66,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,
      92,    93,    -1,    95,    96,    97,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     142,   143,    -1,    -1,    -1,   147,    -1,    -1,    -1,    -1,
      -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,   161,
      -1,    -1,   134,   135,   136,   137,    -1,    -1,    -1,   171,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   150,    15,
      -1,    17,    -1,    19,    20,    -1,    -1,    -1,   160,    -1,
      -1,    -1,    -1,    29,    -1,    -1,   168,    -1,   170,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,   181,
     182,   183,   184,   185,   186,   187,    52,    -1,    -1,    -1,
      -1,    -1,    58,    -1,    60,    -1,    -1,    -1,    64,    -1,
      66,    67,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,    85,
      -1,    -1,    88,    89,    -1,    -1,    92,    93,    -1,    95,
      96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    14,    15,    16,    -1,    18,    -1,    20,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    32,
      33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   134,   135,
     136,   137,    -1,    -1,    14,    15,    16,    -1,    -1,    -1,
      20,    -1,    -1,    -1,   150,    15,    -1,    17,    -1,    -1,
      20,    -1,    32,    33,   160,    -1,    -1,    -1,    -1,    29,
      -1,    -1,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,   185,
     186,   187,    52,    -1,    -1,    -1,    -1,    -1,    58,    -1,
      60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    -1,    -1,    88,    89,
      -1,    -1,    92,    93,    -1,    95,    96,    97,    12,   142,
     143,    -1,    -1,    17,   147,    19,    -1,    14,    15,    16,
      -1,   154,   155,    20,   157,    -1,   159,    -1,   161,    -1,
      -1,    -1,    -1,    37,    -1,    32,    33,    -1,   171,    -1,
      -1,    -1,   142,   143,   134,   135,   136,   137,    -1,    -1,
      14,    15,    -1,    -1,   154,   155,    20,   157,    -1,   159,
     150,   161,    66,    67,    -1,    -1,    -1,    -1,    32,    33,
     160,   171,    -1,    -1,    -1,    -1,    -1,    -1,   168,    -1,
     170,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
     180,   181,   182,   183,   184,   185,   186,   187,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   142,   143,    -1,   152,   153,
     147,    -1,    -1,    -1,    -1,    37,    -1,   154,   155,    -1,
     157,    -1,   159,    -1,   161,    -1,    -1,    -1,    -1,   173,
      -1,    -1,    -1,    -1,   171,    -1,    -1,    -1,   142,   143,
      -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,
     154,   155,    -1,   157,    -1,   159,    -1,   161,    -1,    -1,
      -1,    -1,    -1,    19,    -1,    -1,    -1,   171,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    37,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    90,    91,    -1,    37,    -1,    -1,
      -1,   163,    -1,    -1,   166,    -1,    -1,    -1,   104,   105,
     106,   107,   108,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    66,    67,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,   163,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,
      -1,    37,    -1,    -1,    -1,   163,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    -1,   163,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,   163,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    31,    32,    -1,    -1,    -1,    -1,    37,    -1,
      -1,   163,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    32,    -1,    -1,    -1,   158,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,   158,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,   156,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,    -1,   152,
     153,    -1,    -1,   156,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    62,    63,    -1,    -1,    66,    67,    -1,    -1,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    -1,    -1,    -1,    -1,    -1,    12,    -1,
     141,    90,    91,    17,    18,    19,    -1,    -1,    -1,    -1,
      -1,   152,   153,    27,    -1,   104,   105,   106,   107,   108,
      -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   100,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    12,    -1,    -1,    -1,    -1,    17,   141,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
      31,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    86,    87,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   100,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    12,    -1,    -1,    -1,
      -1,    17,   141,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    62,    63,    -1,    -1,
      66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    27,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    61,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
      31,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    -1,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    21,   145,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    31,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    -1,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    31,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    18,    19,    -1,    -1,    -1,
     145,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    18,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    -1,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    31,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,
      -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    61,    -1,    -1,    -1,    -1,
      66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,   149,    -1,    -1,   152,   153,
      31,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,   145,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,   146,
      -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    18,    19,    -1,    -1,    -1,
      -1,   146,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    86,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,   145,    -1,    -1,    -1,
      -1,    28,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    -1,    -1,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    30,    -1,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    87,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    18,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   149,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
     145,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,   148,    27,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    18,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    18,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    21,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    31,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    21,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      28,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    -1,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    31,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    -1,    -1,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    30,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    -1,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    30,    -1,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    61,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,
     151,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    38,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    12,    -1,    -1,
      -1,    -1,    17,    18,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    32,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    21,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,   148,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    61,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      61,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    21,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    31,    -1,    -1,    -1,    -1,
      -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,   145,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    21,   144,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,
      -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    12,    -1,
      -1,    -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    -1,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      12,    -1,    -1,    -1,    -1,    -1,    -1,    19,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,    37,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      12,    -1,    -1,    -1,    66,    67,    -1,    19,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,    66,    67,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    12,    90,    91,
      -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,
     152,   153,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,    37,   115,    -1,    -1,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    19,    -1,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,    -1,
      -1,    66,    67,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,   104,
     105,   106,   107,   108,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153
  };
  }


  static class YyTokenClass {
  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  public static final short
  yystos_[] =
  {
         0,     3,     4,     5,     7,    48,   174,   175,   211,   212,
     213,   214,   215,   230,   235,   238,   241,   253,   257,   176,
     177,   178,   179,   242,   243,   244,   180,   231,   232,   234,
     180,   236,   237,   244,   258,   259,   260,   261,   244,   254,
     255,   180,   180,     0,   213,    33,   180,   146,   149,    33,
      12,   237,   180,   262,    33,    17,    88,    89,    92,   137,
     160,   180,   182,   183,   184,   185,   186,   187,   256,   291,
     292,   293,   294,   295,   296,   321,   324,    33,    12,    12,
     243,    12,    34,   180,   234,    17,    19,    20,    35,    39,
      40,    41,    42,    43,   180,   188,   189,   190,   191,   192,
     193,   194,   195,   245,   246,   247,   248,   249,   296,   232,
      15,    17,    20,    29,    52,    58,    60,    64,    66,    67,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    88,    89,
      93,    95,    96,    97,   134,   135,   136,   150,   168,   170,
     180,   181,   290,   291,   292,   293,   294,   295,   296,   299,
     301,   304,   305,    17,    19,   149,   265,   259,   290,    17,
      17,    19,    12,   149,    12,   149,   255,   169,   172,    14,
      15,    16,    17,    32,    33,   142,   172,   180,   216,   233,
     234,   245,   180,   245,   250,   251,   245,    27,    44,   245,
     180,   245,   245,   245,   245,   245,    90,   104,    27,    31,
      37,    44,   290,   289,   290,    30,   289,   290,   306,   307,
      67,   180,   320,   321,   323,   325,   326,   327,   180,   256,
     262,   297,   298,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,    17,    17,   320,   327,   332,
      17,   246,    17,    17,   320,   322,   328,   329,   330,   331,
     328,   325,   289,   290,    17,    98,    12,    17,    19,    37,
      66,    67,    90,    91,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   152,   153,   266,   322,   245,   247,
     180,   268,    18,   322,   322,   290,   245,   290,   245,   180,
       8,    10,    11,   239,   240,   241,   253,   257,   274,   286,
     180,   217,   218,   234,   217,   217,   233,   217,   217,   290,
       6,     9,    13,   219,   220,   240,    17,    19,    14,    15,
      16,    20,    32,    33,   142,   143,   154,   155,   157,   159,
     161,   171,   228,    13,    33,    47,   252,    45,   149,   251,
     252,    18,   245,   245,    21,    36,    38,    38,   180,   180,
     245,   245,   245,   245,    18,    21,   146,    31,    30,    31,
      86,    87,    30,   146,    31,   118,   149,    13,   149,    59,
     146,    61,   149,   289,   290,   289,    13,   146,   290,    17,
     289,   290,   118,   146,   149,    13,   146,    13,    13,   151,
      31,   290,   290,   289,   290,   290,   290,   290,   180,   324,
      92,   180,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   320,   326,   180,   324,    18,   146,   149,   180,   149,
      49,   146,   269,   270,   146,    18,    12,    12,   172,   244,
     275,   276,   277,   278,   276,    47,   244,   287,   288,   173,
     239,   118,   146,    13,    33,    13,    13,    13,    13,    13,
     145,   180,   222,   180,   221,    14,    15,    16,    17,    20,
      22,    23,    24,    25,    26,    32,    33,    58,    60,    64,
     103,   138,   139,   140,   142,   147,   162,   163,   164,   167,
     224,   226,   227,   290,   308,   310,   311,   314,   316,    13,
     220,   289,   180,   216,   216,   216,   290,   216,   216,   290,
     229,   290,   290,   216,   216,   290,   290,   290,   216,   234,
     320,   245,   245,   250,   245,   245,   290,   320,   326,   328,
     290,   290,   290,   307,   328,   290,   245,   290,   290,   298,
     290,   302,   303,   322,    18,    18,   290,   327,   146,   290,
      18,   146,   290,   320,   245,   290,   329,   290,   290,   328,
      18,    18,    86,   322,   245,    17,    19,   263,   245,   290,
     180,    50,   272,   320,   290,   290,   239,   180,   276,   290,
     180,   313,    33,   290,   218,   216,   218,   234,   216,   216,
     216,    20,   216,   216,   217,    12,    33,   180,    12,   180,
     217,   217,   217,    99,   180,   224,   233,   290,    55,   281,
     282,   319,   290,   217,   217,   297,   290,   309,   290,    19,
     290,   234,   234,   234,   290,   290,   289,   290,   309,   165,
     180,   320,   325,   290,    14,    15,    16,    20,    32,    33,
     142,   147,   154,   155,   157,   159,   161,   171,   173,   228,
      13,    33,    27,   100,   141,   224,    18,    32,   158,   145,
     144,   146,    28,   156,    46,   173,    13,    21,    13,    30,
      30,    13,    30,    62,    63,   300,   146,   173,    27,   245,
      18,   290,    13,   151,   290,   149,   322,    17,    46,   149,
     290,    18,   173,   149,   265,   149,   288,    18,   290,    13,
     290,   180,    12,   223,   224,   233,    12,    13,    13,    13,
     312,   313,    18,    13,    56,    57,   283,   284,   285,   269,
      21,    13,    13,    59,    27,    61,    31,   173,   149,   145,
     148,    13,   173,   320,    12,    59,    59,   163,   224,   224,
     224,   290,   224,   224,   290,   290,   290,   224,   224,   290,
     290,   290,   224,   227,   224,    94,   224,   173,   290,   216,
     216,   290,   290,   216,   290,   290,   290,   290,   290,   290,
      65,   303,   290,    18,    18,   290,    18,   245,    18,   322,
      53,    54,   264,   290,   245,    19,   245,   279,   267,   268,
     245,    17,    21,   216,    12,   290,    13,   223,   224,   224,
      20,    13,   146,   224,   284,   289,   272,    20,   224,   224,
     290,   224,   224,   290,   290,   317,   318,   322,   217,   217,
      17,   118,   290,   290,   290,   224,    31,    32,   158,    31,
     145,    31,   156,   290,    21,    28,    31,    21,    30,    30,
      61,    27,   151,    18,   269,   102,   102,   180,   281,    59,
     100,   290,   216,   290,   224,   290,   224,   313,    18,   149,
     290,    62,    63,   315,    27,   146,   173,    27,    13,    13,
     224,   225,   290,    38,   163,   163,   290,   290,   224,   290,
     224,   290,   224,    17,   216,   290,   328,   290,   290,   271,
     272,    19,   245,    19,   245,   263,   269,   290,   290,    18,
      21,    18,    17,   245,    21,    31,   290,   224,    62,    63,
     224,    65,   318,   322,   224,   290,    20,   224,    18,   146,
     163,   290,   224,   224,    32,    21,    31,   145,   148,   289,
      13,   144,   300,   173,    51,   273,    46,   272,   224,   289,
     224,   290,    61,   290,   224,    27,    27,   290,   224,   224,
     163,   166,   290,   224,   290,   224,   224,    18,   290,   290,
      53,    54,   224,   280,    18,    21,   224,    61,   224,    21,
     224,   290,    31,   145,   144,   269,   224,   224,   173,   224,
     163,   290,   224,   271,   224,    21,   224
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
     425,   426,   427,   428,   429,   430,   431,   432,   433,   434,
     435,   436,   437,   438,   439,   440,   441,   442,   443,   444,
     445,   446,   447,   448,   449,   450,   451,   452,   453,   454,
     455,   456,   457,   458,   459,   460,   461,   462,   463,   464
  };
  }

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */

  private static final short
  yyr1_[] =
  {
         0,   210,   211,   212,   212,   213,   213,   213,   213,   213,
     214,   214,   215,   215,   216,   216,   216,   216,   216,   216,
     216,   216,   216,   216,   216,   216,   216,   216,   216,   216,
     216,   216,   216,   216,   216,   216,   216,   216,   216,   216,
     216,   216,   217,   217,   217,   217,   218,   218,   219,   219,
     220,   220,   220,   220,   221,   221,   222,   222,   222,   223,
     223,   224,   224,   224,   224,   224,   224,   224,   224,   224,
     224,   224,   224,   224,   224,   224,   224,   224,   224,   224,
     224,   224,   224,   224,   224,   224,   224,   224,   224,   224,
     224,   224,   224,   224,   224,   224,   224,   224,   224,   224,
     224,   225,   225,   226,   226,   227,   227,   227,   228,   228,
     228,   229,   229,   230,   230,   230,   231,   231,   232,   232,
     233,   233,   234,   234,   235,   235,   236,   236,   237,   238,
     238,   238,   239,   239,   240,   240,   240,   240,   240,   240,
     241,   241,   241,   242,   242,   243,   243,   243,   243,   244,
     244,   244,   244,   244,   245,   245,   245,   245,   245,   245,
     245,   245,   245,   245,   245,   245,   245,   245,   245,   245,
     246,   246,   246,   246,   246,   246,   246,   246,   247,   247,
     248,   248,   249,   249,   250,   250,   251,   251,   251,   252,
     253,   253,   253,   254,   254,   255,   256,   256,   256,   256,
     257,   257,   257,   258,   258,   259,   259,   260,   261,   262,
     263,   263,   263,   264,   264,   264,   265,   265,   266,   266,
     267,   267,   268,   268,   269,   269,   270,   271,   271,   272,
     273,   273,   274,   274,   275,   275,   276,   276,   277,   278,
     279,   279,   279,   279,   280,   280,   280,   281,   281,   282,
     283,   283,   284,   284,   285,   285,   286,   286,   286,   287,
     287,   288,   288,   289,   289,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   291,
     291,   292,   293,   294,   294,   294,   295,   296,   297,   297,
     298,   298,   299,   300,   300,   301,   301,   302,   302,   303,
     304,   304,   304,   304,   304,   304,   304,   304,   304,   304,
     304,   304,   304,   304,   304,   304,   304,   304,   304,   304,
     305,   305,   305,   305,   305,   305,   305,   305,   305,   305,
     305,   305,   305,   305,   305,   305,   305,   305,   305,   305,
     305,   305,   305,   305,   305,   305,   305,   305,   305,   305,
     305,   305,   305,   306,   306,   307,   308,   308,   308,   308,
     308,   308,   308,   308,   308,   308,   308,   308,   308,   308,
     309,   309,   310,   311,   311,   312,   312,   313,   313,   313,
     314,   314,   315,   315,   316,   316,   317,   317,   318,   319,
     320,   320,   321,   321,   321,   321,   322,   322,   323,   323,
     324,   324,   324,   324,   324,   324,   324,   325,   325,   326,
     327,   328,   328,   329,   329,   330,   331,   332,   332
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     1,     2,     1,     1,     1,     1,     1,
       6,     8,     4,     6,     4,     5,     3,     3,     3,     5,
       7,     3,     3,     3,     5,     3,     5,     3,     3,     3,
       8,     1,     2,     4,     2,     4,     4,     4,     6,     7,
       4,     4,     1,     1,     3,     3,     3,     3,     1,     2,
       1,     2,     1,     2,     3,     4,     3,     4,     5,     1,
       3,     1,     1,     1,     1,     2,     3,     3,     3,     3,
       3,     3,     5,     3,     5,     3,     3,     3,     2,     6,
       7,     3,     7,     3,    11,     7,     9,     5,     3,     8,
       4,     4,     4,     6,     7,     9,     9,     7,     1,     1,
       1,     1,     3,     1,     3,     2,     2,     2,     3,     7,
       9,     3,     5,     1,     2,     3,     1,     3,     1,     1,
       1,     3,     3,     3,     1,     2,     1,     2,     3,     1,
       1,     1,     1,     2,     1,     1,     1,     1,     1,     2,
       1,     2,     3,     1,     3,     5,     4,     4,     5,     1,
       1,     1,     1,     0,     3,     1,     1,     5,     3,     3,
       3,     2,     2,     2,     4,     4,     1,     1,     3,     3,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       3,     3,     3,     3,     1,     2,     1,     3,     3,     4,
       1,     2,     3,     1,     3,     2,     5,     5,     3,     3,
       1,     2,     3,     1,     3,     1,     1,     6,     2,    10,
       1,     3,     4,     1,     1,     1,     1,     3,     3,     5,
       0,     1,     3,     5,     1,     0,     2,     1,     0,     2,
       2,     0,     1,     2,     1,     2,     1,     1,    10,     7,
       3,     3,     3,     3,     1,     1,     1,     1,     0,     2,
       1,     2,     2,     4,     1,     1,     1,     2,     3,     1,
       3,     2,     2,     1,     3,     1,     1,     1,     1,     1,
       1,     3,     1,     1,     2,     3,     3,     3,     2,     4,
       6,     3,     3,     3,     3,     3,     4,     1,     1,     1,
       1,     4,     4,     4,     4,     2,     3,     5,     7,     5,
       1,     3,     5,     7,     1,     3,     5,     7,     4,     4,
       4,     4,     5,     6,     4,     6,     3,     5,     7,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     3,
       1,     1,     5,     2,     5,     5,     9,     1,     3,     3,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     1,     3,     3,     1,     3,     3,     3,
       2,     2,     7,     1,     6,     6,     7,     8,    10,     4,
       3,     5,     4,     3,     6,     1,     3,     3,     5,     5,
       7,     6,     4,     5,     5,     9,     1,     3,     3,     3,
       1,     1,     1,     6,     2,     4,     1,     3,     1,     1,
       1,     1,     1,     1,     1,     1,     3,     1,     1,     3,
       3,     1,     3,     1,     1,     3,     3,     1,     3
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "TYPES", "CHANNELS", "CHANSETS",
  "NAMESETS", "FUNCTIONS", "OPERATIONS", "ACTIONS", "INITIAL", "STATE",
  "EQUALS", "AT", "BARTILDEBAR", "LRSQUARE", "TBAR", "LPAREN", "RPAREN",
  "LRPAREN", "LSQUARE", "RSQUARE", "CSPSKIP", "CSPSTOP", "CSPCHAOS",
  "CSPDIV", "CSPWAIT", "RARROW", "LARROW", "LCURLY", "RCURLY", "BAR",
  "DBAR", "SEMI", "DCOLON", "COMPOSE", "OF", "STAR", "TO", "INMAPOF",
  "MAPOF", "SEQOF", "SEQ1OF", "SETOF", "PLUSGT", "COLONDASH", "DEQUALS",
  "INV", "VALUES", "PRE", "POST", "MEASURE", "IOTA", "SUBCLASSRESP",
  "NOTYETSPEC", "FRAME", "RD", "WR", "LET", "IN", "IF", "THEN", "ELSEIF",
  "ELSE", "CASES", "OTHERS", "PLUS", "MINUS", "ABS", "FLOOR", "NOT",
  "CARD", "POWER", "DUNION", "DINTER", "HD", "TL", "LEN", "ELEMS", "INDS",
  "REVERSE", "CONC", "DOM", "RNG", "MERGE", "INVERSE", "ELLIPSIS",
  "BARRARROW", "MKUNDER", "MKUNDERNAME", "DOT", "DOTHASH", "NUMERAL",
  "LAMBDA", "NEW", "ISUNDER", "PREUNDER", "ISOFCLASS", "TILDE", "DCL",
  "COLONEQUALS", "ATOMIC", "DEQRARROW", "RETURN", "BACKTICK", "SLASH",
  "DIV", "REM", "MOD", "LT", "LTE", "GT", "GTE", "NEQ", "OR", "AND",
  "EQRARROW", "LTEQUALSGT", "INSET", "NOTINSET", "SUBSET", "PSUBSET",
  "UNION", "BACKSLASH", "INTER", "CARET", "DPLUS", "MUNION", "LTCOLON",
  "LTDASHCOLON", "COLONGT", "COLONDASHGT", "COMP", "DSTAR", "FORALL",
  "EXISTS", "EXISTS1", "STRING", "VRES", "RES", "VAL", "AMP", "LSQUAREBAR",
  "DLSQUARE", "DRSQUARE", "BARRSQUARE", "COMMA", "LSQUAREDBAR",
  "DBARRSQUARE", "COLON", "LCURLYBAR", "BARRCURLY", "QUESTION", "BANG",
  "SLASHCOLON", "SLASHBACKSLASH", "COLONBACKSLASH", "LSQUAREGT", "BARGT",
  "ENDSBY", "DECIMAL", "STARTBY", "MU", "DO", "FOR", "ALL", "BY", "WHILE",
  "ISUNDERNAME", "EXTENDS", "EMPTYMAP", "DBACKSLASH", "BEGIN", "END",
  "CLASS", "PROCESS", "PRIVATE", "PROTECTED", "PUBLIC", "LOGICAL",
  "IDENTIFIER", "SELF", "TRUE", "FALSE", "CHAR_LIT", "NIL",
  "QUOTE_LITERAL", "HEX_LITERAL", "TBOOL", "TNAT", "TNAT1", "TINT", "TRAT",
  "TREAL", "TCHAR", "TTOKEN", "DUMMY", "G-LOOSE", "U-DO", "U-LSQUAREDBAR",
  "U-LSQUAREBAR", "U-LRSQUARE", "U-TBAR", "U-DBAR", "U-BARTILDEBAR",
  "U-SEMI", "T-STAR", "U-MINUS", "U-PLUS", "G-HIGH", "$accept", "source",
  "programParagraphList", "programParagraph", "classDefinition",
  "processDefinition", "process", "replicationDeclaration",
  "singleExpressionDeclaration", "processParagraphList",
  "processParagraph", "actionDefinitionList", "namesetDefList",
  "paragraphAction", "action", "actionList", "parametrisationList",
  "parametrisation", "renameExpression", "renameList", "channelDefinition",
  "channelDef", "channelNameDecl", "singleTypeDeclarationList",
  "singleTypeDeclaration", "chansetDefinitionParagraph",
  "chansetDefinitionList", "chansetDefinition",
  "globalDefinitionParagraph", "classDefinitionBlock",
  "classDefinitionBlockAlternative", "typeDefs", "typeDefList", "typeDef",
  "qualifier", "type", "basicType", "functionType", "partialFunctionType",
  "totalFunctionType", "fieldList", "field", "invariant", "valueDefs",
  "valueDefList", "qualifiedValueDef", "valueDef", "functionDefs",
  "functionDefList", "functionDef", "implicitFunctionDef",
  "qualifiedExplicitFunctionDef", "explicitFunctionDef", "parameterList",
  "functionBody", "parameterTypes", "patternListTypeList",
  "identifierTypePairList_opt", "identifierTypePairList", "preExpr_opt",
  "preExpr", "postExpr_opt", "postExpr", "measureExpr", "operationDefs",
  "operationDefList", "operationDef", "explicitOperationDef",
  "implicitOperationDef", "operationType", "operationBody",
  "externals_opt", "externals", "varInformationList", "varInformation",
  "mode", "stateDefs", "stateDefList", "stateDef", "expressionList",
  "expression", "booleanLiteral", "characterLiteral", "nilLiteral",
  "numericLiteral", "textLiteral", "quoteLiteral", "localDefList",
  "localDef", "ifExpr", "elseExprs", "casesExpr", "casesExprAltList",
  "casesExprAlt", "unaryExpr", "binaryExpr", "mapletList", "maplet",
  "controlStatement", "nonDeterministicAltList", "letStatement",
  "blockStatement", "assignmentDefList", "assignmentDef", "ifStatement",
  "elseStatements", "casesStatement", "casesStatementAltList",
  "casesStatementAlt", "implicitOperationBody", "pattern", "patternLessID",
  "patternList", "patternIdentifier", "matchValue", "bind", "setBind",
  "typeBind", "bindList", "multipleBind", "multipleSetBind",
  "multipleTypeBind", "typeBindList", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final short yyrhs_[] =
  {
       211,     0,    -1,   212,    -1,   213,    -1,   212,   213,    -1,
     214,    -1,   215,    -1,   230,    -1,   235,    -1,   238,    -1,
     174,   180,    12,   172,   239,   173,    -1,   174,   180,    12,
     169,   180,   172,   239,   173,    -1,   175,   180,    12,   216,
      -1,   175,   180,    12,   233,    13,   216,    -1,   172,    13,
     224,   173,    -1,   172,   219,    13,   224,   173,    -1,   216,
      33,   216,    -1,   216,    15,   216,    -1,   216,    14,   216,
      -1,   216,   142,   290,   145,   216,    -1,   216,    20,   290,
      32,   290,    21,   216,    -1,   216,    32,   216,    -1,   216,
      16,   216,    -1,   216,   155,   216,    -1,   216,   154,   290,
     156,   216,    -1,   216,   157,   216,    -1,   216,    20,   290,
     158,   216,    -1,   216,   171,   290,    -1,   216,   161,   290,
      -1,   216,   159,   290,    -1,    17,   233,    13,   216,    18,
      17,   290,    18,    -1,   180,    -1,   180,    19,    -1,   180,
      17,   289,    18,    -1,   216,   228,    -1,    33,   217,    13,
     216,    -1,    15,   217,    13,   216,    -1,    14,   217,    13,
     216,    -1,   142,   290,   145,   217,    13,   216,    -1,    32,
     217,    13,    20,   290,    21,   216,    -1,    32,   217,    13,
     216,    -1,    16,   217,    13,   216,    -1,   234,    -1,   218,
      -1,   217,    33,   234,    -1,   217,    33,   218,    -1,   180,
     118,   290,    -1,   180,   146,   218,    -1,   220,    -1,   219,
     220,    -1,   240,    -1,     9,   221,    -1,     6,    -1,     6,
     222,    -1,   180,    12,   223,    -1,   221,   180,    12,   223,
      -1,   180,    12,   290,    -1,   222,   180,    12,   290,    -1,
     222,    33,   180,    12,   290,    -1,   224,    -1,   233,    13,
     224,    -1,    22,    -1,    23,    -1,    24,    -1,    25,    -1,
      26,   290,    -1,   290,    27,   224,    -1,   290,   141,   224,
      -1,   224,    33,   224,    -1,   224,    15,   224,    -1,   224,
      14,   224,    -1,   224,   155,   224,    -1,   224,   154,   290,
     156,   224,    -1,   224,   157,   224,    -1,   224,    20,   290,
     158,   224,    -1,   224,   171,   290,    -1,   224,   161,   290,
      -1,   224,   159,   290,    -1,   224,   228,    -1,   162,   289,
      13,    17,   225,    18,    -1,   224,   147,   290,    31,   290,
     148,   224,    -1,   224,    16,   224,    -1,   224,   142,   290,
      31,   290,   145,   224,    -1,   224,    32,   224,    -1,   224,
      20,   290,    31,   290,    32,   290,    31,   290,    21,   224,
      -1,   224,    20,   290,    32,   290,    21,   224,    -1,   224,
     142,   290,    31,   290,    31,   290,   145,   224,    -1,   224,
     142,   290,   145,   224,    -1,   226,    13,   224,    -1,    17,
     233,    13,   224,    18,    17,   289,    18,    -1,    33,   217,
      13,   224,    -1,    15,   217,    13,   224,    -1,    14,   217,
      13,   224,    -1,   147,   290,   148,   217,    13,   224,    -1,
      16,   217,    13,    20,   290,    21,   224,    -1,   142,   290,
     145,   217,    13,    20,   290,    21,   224,    -1,    32,   217,
      13,    20,   290,    31,   290,    21,   224,    -1,    32,   217,
      13,    20,   290,    21,   224,    -1,   310,    -1,   311,    -1,
     308,    -1,   224,    -1,   225,   146,   224,    -1,   227,    -1,
     226,    33,   227,    -1,   140,   234,    -1,   139,   234,    -1,
     138,   234,    -1,   143,   229,   144,    -1,   143,   290,    28,
     290,    31,   328,   144,    -1,   143,   290,    28,   290,    31,
     328,    13,   290,   144,    -1,   290,    28,   290,    -1,   229,
     146,   290,    28,   290,    -1,     4,    -1,     4,   231,    -1,
       4,   231,    33,    -1,   232,    -1,   231,    33,   232,    -1,
     180,    -1,   234,    -1,   234,    -1,   233,    33,   234,    -1,
     180,   149,   245,    -1,   180,   146,   234,    -1,     5,    -1,
       5,   236,    -1,   237,    -1,   236,   237,    -1,   180,    12,
     290,    -1,   241,    -1,   253,    -1,   257,    -1,   240,    -1,
     240,   239,    -1,   241,    -1,   253,    -1,   257,    -1,   274,
      -1,   286,    -1,    10,   276,    -1,     3,    -1,     3,   242,
      -1,     3,   242,    33,    -1,   243,    -1,   242,    33,   243,
      -1,   244,   180,    12,   245,   252,    -1,   244,   180,    12,
     245,    -1,   244,   180,    34,   250,    -1,   244,   180,    34,
     250,   252,    -1,   176,    -1,   177,    -1,   178,    -1,   179,
      -1,    -1,    17,   245,    18,    -1,   246,    -1,   296,    -1,
      35,   180,    36,   250,   173,    -1,   245,    31,   245,    -1,
     245,    37,   245,    -1,    20,   245,    21,    -1,    43,   245,
      -1,    41,   245,    -1,    42,   245,    -1,    40,   245,    38,
     245,    -1,    39,   245,    38,   245,    -1,   247,    -1,   180,
      -1,   180,    90,   180,    -1,   180,   104,   180,    -1,   188,
      -1,   189,    -1,   190,    -1,   191,    -1,   192,    -1,   193,
      -1,   194,    -1,   195,    -1,   248,    -1,   249,    -1,   245,
      44,   245,    -1,    19,    44,   245,    -1,   245,    27,   245,
      -1,    19,    27,   245,    -1,   251,    -1,   250,   251,    -1,
     245,    -1,   180,   149,   245,    -1,   180,    45,   245,    -1,
      47,   320,    46,   290,    -1,    48,    -1,    48,   254,    -1,
      48,   254,    33,    -1,   255,    -1,   254,    33,   255,    -1,
     244,   256,    -1,   180,   149,   245,    12,   290,    -1,   321,
     149,   245,    12,   290,    -1,   180,    12,   290,    -1,   321,
      12,   290,    -1,     7,    -1,     7,   258,    -1,     7,   258,
      33,    -1,   259,    -1,   258,    33,   259,    -1,   260,    -1,
     261,    -1,   244,   180,   265,   268,   269,   272,    -1,   244,
     262,    -1,   180,   149,   247,   180,   263,    46,   264,   269,
     271,   273,    -1,    19,    -1,    17,   322,    18,    -1,   263,
      17,   322,    18,    -1,   290,    -1,    53,    -1,    54,    -1,
      19,    -1,    17,   266,    18,    -1,   322,   149,   245,    -1,
     266,   146,   322,   149,   245,    -1,    -1,   268,    -1,   180,
     149,   245,    -1,   268,   146,   180,   149,   245,    -1,   270,
      -1,    -1,    49,   290,    -1,   272,    -1,    -1,    50,   290,
      -1,    51,   290,    -1,    -1,     8,    -1,     8,   275,    -1,
     276,    -1,   275,   276,    -1,   278,    -1,   277,    -1,   244,
     180,   149,   279,   180,   263,    46,   280,   269,   271,    -1,
     244,   180,   265,   267,   281,   269,   272,    -1,   245,   102,
     245,    -1,    19,   102,   245,    -1,   245,   102,    19,    -1,
      19,   102,    19,    -1,   224,    -1,    53,    -1,    54,    -1,
     282,    -1,    -1,    55,   283,    -1,   284,    -1,   283,   284,
      -1,   285,   289,    -1,   285,   289,   149,   245,    -1,    56,
      -1,    57,    -1,    11,    -1,    11,   287,    -1,    11,   287,
      33,    -1,   288,    -1,   287,    33,   288,    -1,   244,   313,
      -1,    47,   290,    -1,   290,    -1,   289,   146,   290,    -1,
     294,    -1,   291,    -1,   293,    -1,   292,    -1,   295,    -1,
     296,    -1,    17,   290,    18,    -1,   181,    -1,   180,    -1,
     180,    98,    -1,   290,    90,   180,    -1,   290,   104,   180,
      -1,   290,    91,    92,    -1,   290,    19,    -1,   290,    17,
     289,    18,    -1,   290,    17,   290,    86,   290,    18,    -1,
     290,    90,   324,    -1,   290,   153,   180,    -1,   290,   153,
     324,    -1,   290,   152,   320,    -1,   290,   152,   326,    -1,
      58,   297,    59,   290,    -1,   299,    -1,   301,    -1,   304,
      -1,   305,    -1,   134,   328,    13,   290,    -1,   135,   328,
      13,   290,    -1,   136,   325,    13,   290,    -1,    52,   325,
      13,   290,    -1,    29,    30,    -1,    29,   289,    30,    -1,
      29,   290,    31,   328,    30,    -1,    29,   290,    31,   328,
      13,   290,    30,    -1,    29,   290,    86,   290,    30,    -1,
      15,    -1,    20,   289,    21,    -1,    20,   290,    31,   326,
      21,    -1,    20,   290,    31,   326,    13,   290,    21,    -1,
     170,    -1,    29,   306,    30,    -1,    29,   307,    31,   328,
      30,    -1,    29,   307,    31,   328,    13,   290,    30,    -1,
      88,    17,   289,    18,    -1,    89,    17,   289,    18,    -1,
      93,   332,    13,   290,    -1,   168,    17,   290,    18,    -1,
      95,   246,    17,   290,    18,    -1,    95,    17,   290,   146,
     245,    18,    -1,    96,    17,   289,    18,    -1,    97,    17,
     290,   146,   290,    18,    -1,   150,   289,   151,    -1,   150,
     290,    31,   328,   151,    -1,   150,   290,    31,   328,    13,
     290,   151,    -1,   183,    -1,   182,    -1,   184,    -1,   185,
      -1,    92,    -1,   187,    -1,   160,    -1,   137,    -1,   186,
      -1,   298,    -1,   297,   146,   298,    -1,   256,    -1,   262,
      -1,    60,   290,    61,   290,   300,    -1,    63,   290,    -1,
      62,   290,    61,   290,   300,    -1,    64,   290,   149,   302,
     173,    -1,    64,   290,   149,   302,   146,    65,    27,   290,
     173,    -1,   303,    -1,   302,   146,   303,    -1,   322,    27,
     290,    -1,    66,   290,    -1,    67,   290,    -1,    68,   290,
      -1,    69,   290,    -1,    70,   290,    -1,    71,   290,    -1,
      72,   290,    -1,    73,   290,    -1,    74,   290,    -1,    75,
     290,    -1,    76,   290,    -1,    77,   290,    -1,    78,   290,
      -1,    79,   290,    -1,    80,   290,    -1,    81,   290,    -1,
      82,   290,    -1,    83,   290,    -1,    84,   290,    -1,    85,
     290,    -1,   290,    66,   290,    -1,   290,    37,   290,    -1,
     290,    67,   290,    -1,   290,   106,   290,    -1,   290,   105,
     290,    -1,   290,   107,   290,    -1,   290,   108,   290,    -1,
     290,   109,   290,    -1,   290,   110,   290,    -1,   290,   111,
     290,    -1,   290,   112,   290,    -1,   290,    12,   290,    -1,
     290,   113,   290,    -1,   290,   114,   290,    -1,   290,   115,
     290,    -1,   290,   116,   290,    -1,   290,   117,   290,    -1,
     290,   118,   290,    -1,   290,   119,   290,    -1,   290,   120,
     290,    -1,   290,   121,   290,    -1,   290,   122,   290,    -1,
     290,   123,   290,    -1,   290,   124,   290,    -1,   290,   125,
     290,    -1,   290,   126,   290,    -1,   290,   127,   290,    -1,
     290,   128,   290,    -1,   290,   129,   290,    -1,   290,   130,
     290,    -1,   290,   131,   290,    -1,   290,   132,   290,    -1,
     290,   133,   290,    -1,   307,    -1,   306,   146,   307,    -1,
     290,    87,   290,    -1,   314,    -1,    60,   309,   173,    -1,
     163,   309,   173,    -1,    20,   319,    21,    -1,   103,    19,
      -1,   103,   290,    -1,   290,   100,    94,   290,    17,   289,
      18,    -1,   316,    -1,   164,   325,    59,   290,   163,   224,
      -1,   164,   320,    59,   290,   163,   224,    -1,   164,   165,
     320,   118,   290,   163,   224,    -1,   164,   180,    12,   290,
      38,   290,   163,   224,    -1,   164,   180,    12,   290,    38,
     290,   166,   290,   163,   224,    -1,   167,   290,   163,   224,
      -1,   290,    27,   224,    -1,   309,    31,   290,    27,   224,
      -1,    58,   297,    59,   224,    -1,    17,   224,    18,    -1,
      17,    99,   312,    13,   224,    18,    -1,   313,    -1,   312,
     146,   313,    -1,   180,   149,   245,    -1,   180,   149,   245,
     100,   290,    -1,   180,   149,   245,    59,   290,    -1,    60,
     290,    61,   224,   315,    63,   224,    -1,    60,   290,    61,
     224,    63,   224,    -1,    62,   290,    61,   224,    -1,   315,
      62,   290,    61,   224,    -1,    64,   290,   149,   317,   173,
      -1,    64,   290,   149,   317,   146,    65,    27,   224,   173,
      -1,   318,    -1,   317,   146,   318,    -1,   322,    27,   224,
      -1,   281,   269,   272,    -1,   323,    -1,   321,    -1,   324,
      -1,    88,    17,   322,   146,   320,    18,    -1,    89,    19,
      -1,    89,    17,   322,    18,    -1,   320,    -1,   322,   146,
     320,    -1,   180,    -1,    67,    -1,   294,    -1,   291,    -1,
     293,    -1,   292,    -1,   295,    -1,   296,    -1,    17,   290,
      18,    -1,   326,    -1,   327,    -1,   320,   118,   290,    -1,
     320,   149,   245,    -1,   329,    -1,   328,   146,   329,    -1,
     330,    -1,   331,    -1,   322,   118,   290,    -1,   322,   149,
     245,    -1,   327,    -1,   332,   146,   327,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     7,    10,    12,    14,    16,    18,
      20,    27,    36,    41,    48,    53,    59,    63,    67,    71,
      77,    85,    89,    93,    97,   103,   107,   113,   117,   121,
     125,   134,   136,   139,   144,   147,   152,   157,   162,   169,
     177,   182,   187,   189,   191,   195,   199,   203,   207,   209,
     212,   214,   217,   219,   222,   226,   231,   235,   240,   246,
     248,   252,   254,   256,   258,   260,   263,   267,   271,   275,
     279,   283,   287,   293,   297,   303,   307,   311,   315,   318,
     325,   333,   337,   345,   349,   361,   369,   379,   385,   389,
     398,   403,   408,   413,   420,   428,   438,   448,   456,   458,
     460,   462,   464,   468,   470,   474,   477,   480,   483,   487,
     495,   505,   509,   515,   517,   520,   524,   526,   530,   532,
     534,   536,   540,   544,   548,   550,   553,   555,   558,   562,
     564,   566,   568,   570,   573,   575,   577,   579,   581,   583,
     586,   588,   591,   595,   597,   601,   607,   612,   617,   623,
     625,   627,   629,   631,   632,   636,   638,   640,   646,   650,
     654,   658,   661,   664,   667,   672,   677,   679,   681,   685,
     689,   691,   693,   695,   697,   699,   701,   703,   705,   707,
     709,   713,   717,   721,   725,   727,   730,   732,   736,   740,
     745,   747,   750,   754,   756,   760,   763,   769,   775,   779,
     783,   785,   788,   792,   794,   798,   800,   802,   809,   812,
     823,   825,   829,   834,   836,   838,   840,   842,   846,   850,
     856,   857,   859,   863,   869,   871,   872,   875,   877,   878,
     881,   884,   885,   887,   890,   892,   895,   897,   899,   910,
     918,   922,   926,   930,   934,   936,   938,   940,   942,   943,
     946,   948,   951,   954,   959,   961,   963,   965,   968,   972,
     974,   978,   981,   984,   986,   990,   992,   994,   996,   998,
    1000,  1002,  1006,  1008,  1010,  1013,  1017,  1021,  1025,  1028,
    1033,  1040,  1044,  1048,  1052,  1056,  1060,  1065,  1067,  1069,
    1071,  1073,  1078,  1083,  1088,  1093,  1096,  1100,  1106,  1114,
    1120,  1122,  1126,  1132,  1140,  1142,  1146,  1152,  1160,  1165,
    1170,  1175,  1180,  1186,  1193,  1198,  1205,  1209,  1215,  1223,
    1225,  1227,  1229,  1231,  1233,  1235,  1237,  1239,  1241,  1243,
    1247,  1249,  1251,  1257,  1260,  1266,  1272,  1282,  1284,  1288,
    1292,  1295,  1298,  1301,  1304,  1307,  1310,  1313,  1316,  1319,
    1322,  1325,  1328,  1331,  1334,  1337,  1340,  1343,  1346,  1349,
    1352,  1356,  1360,  1364,  1368,  1372,  1376,  1380,  1384,  1388,
    1392,  1396,  1400,  1404,  1408,  1412,  1416,  1420,  1424,  1428,
    1432,  1436,  1440,  1444,  1448,  1452,  1456,  1460,  1464,  1468,
    1472,  1476,  1480,  1484,  1486,  1490,  1494,  1496,  1500,  1504,
    1508,  1511,  1514,  1522,  1524,  1531,  1538,  1546,  1555,  1566,
    1571,  1575,  1581,  1586,  1590,  1597,  1599,  1603,  1607,  1613,
    1619,  1627,  1634,  1639,  1645,  1651,  1661,  1663,  1667,  1671,
    1675,  1677,  1679,  1681,  1688,  1691,  1696,  1698,  1702,  1704,
    1706,  1708,  1710,  1712,  1714,  1716,  1718,  1722,  1724,  1726,
    1730,  1734,  1736,  1740,  1742,  1744,  1748,  1752,  1754
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   144,   144,   148,   149,   153,   154,   155,   156,   157,
     161,   162,   166,   167,   172,   173,   175,   176,   177,   181,
     185,   186,   187,   188,   197,   198,   207,   219,   220,   221,
     225,   226,   227,   228,   229,   231,   232,   233,   237,   241,
     242,   243,   249,   250,   251,   252,   264,   265,   269,   270,
     279,   280,   281,   282,   286,   287,   301,   302,   303,   307,
     308,   312,   313,   314,   315,   316,   328,   330,   331,   332,
     333,   334,   343,   344,   353,   363,   364,   365,   366,   373,
     378,   379,   383,   384,   389,   393,   398,   402,   405,   407,
     411,   412,   413,   417,   421,   426,   431,   435,   437,   438,
     439,   444,   445,   449,   450,   454,   455,   456,   461,   463,
     464,   477,   478,   488,   489,   490,   494,   495,   507,   508,
     515,   516,   520,   521,   525,   526,   530,   531,   538,   542,
     543,   544,   548,   549,   553,   554,   555,   556,   557,   564,
     568,   569,   570,   574,   575,   579,   580,   581,   582,   592,
     593,   594,   602,   603,   607,   608,   609,   610,   611,   612,
     613,   614,   615,   616,   617,   618,   619,   628,   629,   630,
     634,   635,   636,   637,   638,   639,   640,   641,   645,   646,
     650,   651,   655,   656,   660,   661,   665,   666,   667,   671,
     682,   683,   684,   688,   689,   693,   697,   698,   699,   700,
     704,   705,   706,   710,   711,   715,   716,   720,   724,   728,
     733,   734,   735,   739,   740,   741,   745,   746,   750,   751,
     754,   756,   760,   761,   765,   766,   770,   774,   775,   779,
     790,   791,   795,   796,   803,   804,   808,   809,   813,   817,
     821,   822,   823,   824,   828,   829,   830,   834,   835,   839,
     843,   844,   848,   849,   853,   854,   876,   877,   878,   882,
     883,   887,   888,   892,   893,   898,   899,   900,   901,   902,
     903,   905,   906,   907,   908,   909,   910,   911,   912,   913,
     914,   915,   917,   918,   919,   920,   922,   923,   924,   925,
     926,   928,   929,   930,   931,   933,   934,   936,   937,   939,
     941,   952,   954,   955,   957,   958,   960,   961,   963,   965,
     967,   976,   977,   978,   983,   991,   993,   994,   995,  1001,
    1002,  1006,  1010,  1014,  1015,  1016,  1020,  1024,  1030,  1031,
    1035,  1036,  1040,  1044,  1045,  1049,  1050,  1054,  1055,  1059,
    1063,  1064,  1065,  1066,  1067,  1068,  1069,  1070,  1071,  1072,
    1073,  1074,  1075,  1076,  1077,  1078,  1079,  1080,  1081,  1082,
    1086,  1087,  1088,  1089,  1090,  1091,  1092,  1093,  1094,  1095,
    1096,  1097,  1098,  1099,  1100,  1101,  1102,  1103,  1104,  1105,
    1106,  1107,  1108,  1109,  1110,  1111,  1112,  1113,  1114,  1115,
    1116,  1117,  1118,  1122,  1123,  1127,  1131,  1133,  1134,  1160,
    1166,  1167,  1178,  1179,  1187,  1189,  1193,  1195,  1196,  1199,
    1217,  1218,  1222,  1226,  1227,  1231,  1232,  1236,  1237,  1243,
    1263,  1264,  1268,  1269,  1273,  1274,  1278,  1279,  1283,  1287,
    1291,  1292,  1296,  1298,  1308,  1309,  1313,  1314,  1318,  1320,
    1325,  1326,  1327,  1328,  1329,  1330,  1331,  1335,  1336,  1340,
    1344,  1348,  1349,  1353,  1354,  1358,  1362,  1366,  1367
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
     175,   176,   177,   178,   179,   180,   181,   182,   183,   184,
     185,   186,   187,   188,   189,   190,   191,   192,   193,   194,
     195,   196,   197,   198,   199,   200,   201,   202,   203,   204,
     205,   206,   207,   208,   209
  };

  private static final short yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 12538;
  private static final int yynnts_ = 123;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 43;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 210;

  private static final int yyuser_token_number_max_ = 464;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */

}


