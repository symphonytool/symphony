
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
  public static final short yypact_ninf_ = -623;
  public static final short yypact_[] =
  {
         7,   860,  -133,   -80,   860,   106,   -78,   111,  -623,  -623,
    -623,  -623,  -623,  -623,  -623,  -623,  -623,  -623,  -623,   108,
    -623,   -62,   338,   167,  -623,  -623,   204,   -80,  -623,    91,
     242,  -623,  -623,  -623,   503,   248,  -623,   285,  -623,   860,
      95,   186,  1432,  -133,  3231,  -623,    39,  -623,   860,  3231,
     363,   299,  -623,  -623,  -623,    28,  -623,  -623,  -623,  -623,
    -623,  -623,  -623,  -623,  -623,  -623,  -623,  -623,  -623,    29,
    -623,   533,   884,  -623,  1432,  1594,   338,  -623,  1432,    19,
    1432,   214,  1432,  1432,  1432,  1432,  1432,   -47,  -623,  -623,
    -623,  -623,  -623,  -623,  -623,  -623,   742,  -623,  -623,  -623,
    -623,  -623,  -623,  -623,  3231,  3231,  2823,   317,  1835,  3231,
    3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,
    3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,
    3231,   387,   398,   317,     1,   400,   402,   317,   317,   317,
    3231,   406,  -623,   346,  -623, 11582,  -623,  -623,  -623,  -623,
    -623,  -623,  -623,  -623,  -623,  -623,   317,  -623,  1432,   265,
    -623,  5837,   317,   317,  -623,  3231,  1432,  3231,  1432,  -623,
     186,   186,   186,   186,   186,   186,  3231,  1590,     4,  3439,
     324,  -623,   826,   284,   742,   971,  -623,   634,  1432,  1432,
     532,   415,   691,   709,   425,   425,   425,   270,   306,  1432,
    1432,  1432,  1432,  5959,   -12,  6082,  -623,    -6,  4856,    60,
     459,  -623,  -623,   203,  -623,  -623,   479,  -623,  -623,    30,
    -623,  -623,   -22,  -623,  6204,  6326,  1873,  1873,  1873,  1873,
   12033,  1873,  1873,  1873,  1873,  1873,  1873,  1873,  1873,  1873,
    1873,  1873,  1873,  1873,  1873,  2837,  3231,  3231,   366,  -623,
      22,  3231,   502,  3231,  3231,  -623,   176,    23,  -623,  -623,
    -623,    51,   510,   217,  6449,  3231,  -623,  3231,  3231,  -623,
    3231,  3231,  3231,   345,   442,   367,  3231,  3231,  3231,  3231,
    3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,
    3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,  3231,
    3231,  3231,  3231,  3231,  3231,   317,   418,    76,   375,   742,
     384,   416,   -16,  -623,   433,   127, 11582,   523, 11582,   562,
     327,  -623,   352,   377,   378,   405,   413,  6571,   410,   860,
     417,   490,   263,  2377,  1976,  -623,  -623,  -623,  -623,  -623,
    -623,  -623,  3231,  -623,  1101,  1101,  1101,  3231,  1101,  1101,
    3231,  3231,  3231,  1101,  1101,  3231,  3231,  3231,  -623,  1101,
     186,   317,  -623,  1432,  1432,  -623,  -623,  -623,   742,   742,
    -623,  1594,  1432,  1432,  -623,  -623,   742,   425,   425,   742,
    -623,  -623,  3231,   317,  -623,   317,  3231,  3231,  -623,  3231,
     317,  3231,  1432,  3231,  1432,  3231,  1835,  3231,   317,   141,
   11582,   168,  3231,   317,  6693,  3231,   181,  6815,  3231,   317,
    1432,  3231,   317,  3231,  3231,  -623,   317,  6937, 12083,   190,
    7059,  2973, 12156, 12156,  -623,  -623,  -623,  -623,  2973,  2973,
    2973,  2973, 12083, 12083, 12083, 12083, 12083, 11906, 11956, 11826,
   11704, 12083, 12083, 12083, 12083,  3578,  3578,  2837,  2401,  2227,
    2227,  2837,  2837,  1873,  1873,   280,   373,  -623,  -623,  -623,
    -623,  -623,   317,  1432,   314,  1432,  3231,   431,   565,  -623,
     317,  -623,  3231,  3231,  1101,   186,  1101,  1101,  1101,   964,
    1101,   186,   609,   -17,   452,   860,  -623,  -623,  -623,   639,
     453,  -623,  3231,   478,   640,  -623,   186,   186,   186,  2203,
    2959,  -623,  -623,  -623,  -623,  3231,   186,   186,  1835,  3231,
    3231,  3095,   186,   186,   186,  3231,  3231,  3231,  3231,   213,
    3231,  1349,   450,  -623,  5101,  -623,  -623,  -623,  -623,  -623,
    2377,  -623,   256,   322,  1913,  1071,  1162,  4367,  1648,   451,
    7181,   243,  7303,  4489,   129,    -1, 11582, 11582, 11582,  3439,
    -623,   626,   742,   742,  1385,   425,   425, 11582,   558,    80,
      21,  7427, 11582,  7549,  -623,    74, 12083,   742, 11704, 11582,
    -623,  5224,     2,  -623,    -8,  -623,  -623, 11704,  -623,  1432,
    7671,  -623,  3231, 11582,  -623,   742, 11704,  -623, 11704, 11704,
      25,  -623,  -623,  3231,   399,   742,   317,  -623,    82,   742,
   11582,   528,  3231,  -623,   663, 11582, 11582,  -623,  -623,  -623,
    -623,  3221,  3231,  -623,  -623,   462,  3231,   515,   685,   114,
    -623,  2551,   688, 11582,   553,  -623,   263,   480,   481,   483,
     478,   237,  2929,   575,  4978,   298,   654,  -623,   684, 11582,
     583,   594,   -11,  5346,    -9,  7793,  -623, 11582,  -623,  -623,
    -623,  7915,  8037,    79,  8159,     0,   317,   701,   -10,   655,
    3634,  2377,  2377,  2377,  3231,  2377,  2377,  3231,  3231,  3231,
    2377,  2377,  3231,  3231,  3231,  -623,  -623,  2377,   319,  2377,
    2377,  1800,  -623,  3231,  1101,  1101,  -623,  3231,  3231,  1101,
    3231,  -623,  3231,  -623,  3231,  -623,  -623,  3231,  -623,  3231,
    3231,  -623,  1081,  -623,  3231,   838,  -623,  8281,  3231,  -623,
    8403,  1432,   261,   317,  2687,  1432, 11582,  -623,   702,  8525,
    1101, 11582,   711,  3231,  1674,   265,  -623,  3357,   605,  2551,
    1432,  -623,  2377,  2377,   704,    85,  -623,  -623,  2377,  -623,
    -623,   298,  -623,  3231,   565,  -623,   705,  2377,  2377,  2377,
    2377,  3231,  -623,   317,   186,   186,   713,  -623,   603,  3231,
    3231,  3231,  2377,  1687,    58,   780,  4245,   537,   488,  5469,
    8648,  4611,   414,   185, 11582, 11582, 11582,  3357,  -623,  -623,
     488,  -623,  8770,    -1,  1648,  8892,  9015,   129, 11582,  9137,
    9261,  9385,  9507, 11704,   721,  -623, 11582,  -623,  -623,  9629,
    -623,   742,  -623,   274,  -623,  -623,   654, 11582,   742,  3231,
    1101,  -623,  3231, 11582,    83,   454,   552,   694,   604,  2377,
    -623,   582,  -623,  -623,  3231,  2377,   478,  3188,  -623,   496,
    -623,  3231,  -623,  3357,  5101,  3357,  2779,  4733,  9751,   147,
    -623,     3,   621,   624,  2377,  3231,  9873,  3756,  3878,  3357,
    3231,  3231,  2377,  3231,  2377,  3231,  2377,  1101,  3231,   317,
    -623,  -623,  -623,  3231,  3231,  -623,  -623,   565,  9995,  -623,
   11582,  1691,  1727,   314,   654,  3357,  3231,  3231, 10117,  3337,
    -623,   737,  1432,  5592,  3231,  2377,   121,  2377,  1471,  -623,
    2377,   735,  2377,  3357,   278,  4000,  3231,  2377,  2377, 10239,
   10361,   185,  5715,   537, 10483,   414,  1648, 11582,   124,  5224,
    3317,   707,  -623,  -623,    19,   742,    19,   742,   313,   565,
   11582, 11582,  2377,  -623,  3231,   742,  2377,  3231, 10605,  3357,
    3231,  2377,  3357,   732,  -623,    68,  3357,  5101,  3231,  -623,
    -623,  2377,  2377,  3512,  3357,  3357,  3231,  2377,  3231,  2377,
    2377,  3231,  -623,  -623,  -623,  3231,  -623,  2029,  -623,  -623,
     286,  -623, 10727,  2377, 10849,  3357,  2377,  2377, 10971,  3357,
    3357,  2377,  3231, 11094,   537, 11216,   537,  -623, 11338, 11582,
    -623,  -623,  3357,   654,  -623,  2377,  3357,  2377,  2643,  2377,
    3357,  4122,  3231,  2377,  -623,   565,  -623,  3357,  -623,  -623,
    2377, 11460,   537,  -623,  3357,  2377,   537
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         2,   129,   104,   115,   189,   142,     0,     0,     3,     4,
       5,     6,   120,   121,   122,   138,   139,   140,   141,   130,
     132,     0,   109,   105,   107,   110,     0,   116,   117,     0,
     190,   192,   194,   195,     0,   180,   182,     0,     1,   131,
       0,     0,     0,   106,     0,   118,     0,   197,   191,     0,
       0,     0,   312,   315,   314,     0,   309,   308,   310,   311,
     316,   313,   184,   429,   431,   430,   428,   432,   433,     0,
     420,   142,     0,   133,     0,     0,     0,   114,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   156,   159,   160,
     161,   162,   163,   164,   165,   166,   113,   144,   155,   167,
     168,   145,   108,   289,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   293,   262,   261,   119,   255,   257,   256,   254,
     258,   259,   276,   277,   278,   279,     0,   205,     0,     0,
     193,     0,     0,     0,   422,     0,     0,     0,     0,   183,
       0,     0,     0,     0,     0,     0,     0,     0,    26,     7,
       0,   111,   135,   156,   175,   136,   173,     0,     0,     0,
       0,     0,     0,     0,   151,   152,   150,     0,     0,     0,
       0,     0,     0,     0,     0,   252,   284,     0,   252,     0,
     382,   427,   426,     0,   419,   418,     0,   435,   436,     0,
     319,   320,     0,   317,     0,     0,   329,   330,   331,   332,
     333,   334,   335,   336,   337,   338,   339,   340,   341,   342,
     343,   344,   345,   346,   347,   348,     0,     0,     0,   445,
       0,     0,     0,     0,     0,   424,     0,     0,   439,   441,
     442,     0,     0,     0,   252,     0,   263,     0,     0,   267,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     155,     0,   214,   434,     0,     0,   187,     0,   188,     0,
       0,    37,     0,     0,     0,     0,     0,     0,    43,   221,
       0,   142,   245,     0,     0,    39,    41,   123,   124,   125,
     126,   127,     0,    27,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    29,     0,
       0,     0,   134,     0,     0,   174,   137,   143,   172,   170,
     149,     0,     0,     0,   157,   158,   171,   147,   148,   169,
     260,   290,     0,     0,   285,     0,     0,     0,   294,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     252,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   305,     0,     0,   360,     0,
     252,   350,   349,   351,   264,   270,   266,   265,   353,   352,
     354,   355,   356,   357,   358,   359,   361,   362,   363,   364,
     365,   366,   367,   368,   369,   370,   371,   372,   373,   374,
     375,   376,   377,   378,   379,   380,   381,   273,   274,   271,
     272,   206,     0,     0,     0,     0,     0,     0,     0,   213,
       0,   423,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    44,     0,   222,   223,   226,   225,     0,
      42,   128,     0,     0,   246,   248,     0,   289,     0,     0,
     237,    52,    53,    54,    55,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    94,     0,    91,    89,    90,   385,   391,
       0,    40,     0,    26,    13,    12,    17,     0,    16,    11,
       0,     0,     0,     0,    18,    20,    24,    23,    22,     8,
     112,     0,   177,   176,     0,   154,   153,   253,     0,     0,
       0,     0,   384,     0,   383,     0,   437,   438,   283,   275,
     318,     0,     0,   326,     0,   297,   298,   299,   446,     0,
       0,   303,     0,   443,   425,   444,   280,   440,   281,   282,
       0,   300,   268,     0,     0,   207,     0,   199,     0,   211,
     215,     0,     0,   196,   425,   185,   186,    32,    38,    31,
      36,     0,     0,    35,    30,     0,     0,     0,     0,     0,
     224,     0,     0,   251,     0,   250,   247,     0,     0,     0,
       0,   262,     0,     0,     0,     0,   214,   236,     0,    56,
       0,     0,     0,     0,     0,     0,   389,   390,    98,    97,
      96,     0,     0,     0,     0,     0,     0,   426,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     9,    69,     0,     0,     0,
       0,     0,    28,     0,     0,     0,    99,     0,     0,     0,
       0,   146,     0,   291,     0,   286,   288,     0,   295,     0,
       0,   321,     0,   324,     0,     0,   301,     0,     0,   306,
       0,     0,     0,     0,     0,     0,   218,   421,     0,     0,
       0,    47,     0,     0,     0,   209,    45,    50,     0,     0,
       0,   249,     0,     0,     0,     0,   403,   401,     0,   243,
     244,   238,   239,     0,     0,   388,     0,     0,     0,     0,
       0,     0,   386,     0,     0,     0,     0,   387,     0,     0,
       0,     0,     0,    61,    60,    72,     0,    74,    59,     0,
       0,     0,    62,    64,    68,    67,    66,    79,    95,    57,
      58,    10,     0,    21,    14,     0,   102,    19,   178,     0,
       0,     0,     0,   322,     0,   327,   328,   302,   304,     0,
     269,   208,   200,     0,   203,   204,   214,   202,   212,     0,
       0,    33,     0,    48,     0,     0,     0,   237,   210,     0,
      46,   405,    83,    82,     0,     0,     0,     0,   240,   241,
     417,     0,    81,   400,   275,   398,     0,     0,     0,     0,
     414,     0,     0,     0,     0,     0,     0,     0,     0,   397,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     292,   287,   296,     0,     0,   307,   201,   217,     0,    34,
      49,     0,     0,     0,   214,    51,     0,     0,     0,     0,
     404,     0,     0,     0,     0,     0,     0,     0,     0,   412,
       0,     0,     0,    92,     0,     0,     0,     0,     0,     0,
       0,    65,     0,    78,     0,    63,    15,   103,     0,     0,
       0,   220,   216,    25,   232,   230,   231,   229,     0,     0,
     407,   406,     0,   402,     0,   242,     0,     0,     0,   409,
       0,     0,   399,     0,   415,     0,   416,   328,     0,    84,
      70,     0,     0,     0,   393,   392,     0,     0,     0,     0,
       0,     0,   100,   323,   325,     0,   198,     0,   228,    85,
       0,    88,     0,     0,     0,   408,     0,     0,     0,    93,
     394,     0,     0,     0,    76,     0,    73,    71,     0,   219,
     234,   235,   233,   214,    80,     0,   410,     0,     0,     0,
     395,     0,     0,     0,   101,   217,    87,   411,   413,    86,
       0,     0,    77,   227,   396,     0,    75
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -623,  -623,  -623,   608,   -28,  -623,   427,  -623,  -623,    33,
    1003,  -623,  -623,    86,   -27,  -623,  -623,  -623,   727,   -68,
     276,  -623,  -623,   745,  -623,  -623,   774,  -623,   738,    49,
    1312,   642,  -129,  -623,  -623,   407,  -182,   596,   782,  -623,
     712,   751,   791,  -623,   754,  -623,  -623,   764,   -74,  -623,
     184,  -623,  -623,    93,  -608,  -623,  -191,  -467,  -623,  -623,
    -623,  -324,  -623,  -623,  -623,  -623,    -7,  -623,  -623,    64,
    -623,  -623,  -623,   182,   -81,   -44,    18,   729,   837,   939,
    1047,   659,   308,   428,  -623,   -86,  -623,  -623,   126,  -623,
    -623,  -623,   437,  -623,   311,  -623,  -623,  -623,  -622,  -623,
    -623,  -623,  -623,   -58,  -623,   -94,   -19,  -136,  -623,  -170,
    -137,  -261,  -127,  -121,   419,  -623,  -623,  -623
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     7,     8,   179,   320,   334,   335,   490,   483,   726,
     727,   894,   522,   523,   676,   541,     9,    23,    24,   728,
     321,    10,    27,    28,    11,   336,   337,    19,    20,   484,
     184,    97,    98,    99,   100,   185,   186,   362,   338,    35,
      36,   220,   339,    30,    31,    32,    33,   221,   598,   806,
     159,   307,   817,   312,   468,   469,   911,   912,   956,   340,
     485,   486,   487,   488,   816,   983,   636,   637,   741,   742,
     743,   341,   494,   495,   204,   524,   146,   147,   148,   149,
     150,   151,   222,   223,   152,   701,   153,   572,   573,   154,
     155,   209,   210,   525,   644,   526,   527,   735,   625,   528,
     886,   529,   839,   840,   638,   255,   214,   256,   215,    70,
     216,   217,   218,   257,   258,   259,   260,   250
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -182;
  public static final short
  yytable_[] =
  {
       145,   603,   262,   365,   180,   161,   249,   491,   736,   381,
       1,     2,     3,   213,     4,    69,   617,   261,   251,   704,
     308,   342,   751,   343,   384,   207,   314,   315,   744,   310,
     890,   751,   349,   466,   694,   402,   411,   395,   708,   248,
     165,   167,   165,   197,   458,   213,   188,    22,   748,   760,
      21,   695,    63,    29,    34,     5,   156,   198,   157,   263,
     203,   205,   208,   189,   413,   224,   225,   226,   227,   228,
     229,   230,   231,   232,   233,   234,   235,   236,   237,   238,
     239,   240,   241,   242,   243,   244,   245,   697,    21,    69,
     388,   666,   756,   692,   461,   967,   264,    29,   825,   713,
      26,   693,    37,   425,   698,   324,  -179,    74,   391,  -179,
     188,    38,  -179,  -179,  -179,  -179,  -179,  -179,    40,  -179,
      34,   316,   559,   318,   396,    63,    63,   189,   714,    75,
     467,   156,   327,   157,   382,   396,   460,   951,   409,   392,
     382,    39,   351,   322,   323,   471,   325,   326,   702,   409,
      41,    63,   358,    42,  -179,    63,    63,    63,   355,   575,
     356,   620,   349,   618,   752,   399,   401,   412,   403,   412,
     357,   412,   406,   757,    63,   703,   709,   166,   168,   394,
      63,    63,     6,   930,   931,   871,   576,   419,   158,    88,
      89,    90,    91,    92,    93,    94,    95,   412,   867,   581,
      43,   351,   400,   400,   880,   668,   389,   404,   592,   400,
     407,   457,   669,   670,   409,   671,    44,   672,   666,   673,
     412,   417,   462,   418,   420,   382,   421,   422,   423,   674,
      49,   826,   428,   429,   430,   431,   432,   433,   434,   435,
     436,   437,   438,   439,   440,   441,   442,   443,   444,   445,
     446,   447,   448,   449,   450,   451,   452,   453,   454,   455,
     456,   532,   574,   724,   560,   310,   919,   551,   952,   565,
     412,    46,   351,   409,   682,    48,   578,   830,    25,   802,
     211,    71,    15,    16,    17,    18,   354,   382,   355,   558,
     356,    63,   866,   888,   408,   590,   940,    72,   400,   269,
     357,    50,    51,   537,   984,    52,   540,   542,   543,   248,
     492,   546,   547,   548,   382,   584,   163,    77,   164,    25,
     889,   391,   409,    63,    63,   410,   594,   382,   351,   363,
     713,   596,   668,   597,    49,   266,   382,   359,   557,   342,
     474,   343,   561,   562,   672,   563,   673,   566,   181,   568,
      53,   569,   392,   571,   739,   740,   674,   360,   577,   957,
     475,   580,    49,   382,   583,   476,    76,   586,   415,   588,
     589,   274,   365,    54,   197,   995,   604,    69,   656,    63,
     162,   493,   659,    41,   211,   475,    42,   686,   198,   687,
     477,   478,   269,   657,   191,    56,    57,    58,    59,    60,
      61,    63,   382,    63,   246,    50,    51,   409,    63,    52,
     475,   360,   303,   304,    63,   247,    63,   253,   479,   254,
     409,    63,   600,   265,   941,   658,   480,    63,   605,   606,
      63,   633,   382,   364,    63,    49,   653,    52,   475,    15,
      16,    17,    18,  -142,   266,   311,   475,   666,   623,   181,
     374,   371,   958,   615,    53,   634,   205,   512,   513,   514,
     712,   639,   201,   677,   274,   643,   645,   647,   627,   628,
     629,   651,   652,   400,   654,   720,   660,    54,   640,   641,
      63,   199,    53,   678,    41,   200,   375,    42,    63,    69,
     390,   201,   393,   732,   733,   475,   734,   212,   202,    56,
      57,    58,    59,    60,    61,    54,   304,   358,   358,   358,
      52,   358,   358,   475,   475,   392,   475,   358,   358,   405,
      49,   409,   358,   414,   463,   424,    63,    56,    57,    58,
      59,    60,    61,  -181,   426,   472,  -181,    63,   707,  -181,
    -181,  -181,  -181,  -181,  -181,   409,  -181,   427,   711,   710,
     199,   661,   662,   370,   200,    53,   872,   351,   716,   199,
     201,   668,   758,   200,   464,   465,   574,   202,   719,   201,
     666,   671,   721,   672,   473,   673,   202,   803,    54,   470,
     358,  -181,   358,   358,   358,   674,   358,   358,   738,   199,
     482,    50,    51,   200,   351,    52,   746,   489,   459,   201,
      56,    57,    58,    59,    60,    61,   202,   747,   360,   199,
     355,   601,   356,   200,    63,   602,   475,   841,   819,   201,
     766,   616,   357,   769,   770,   771,   202,   475,   774,   775,
     776,   351,   619,   622,   891,   668,   550,   892,   360,   782,
      53,   876,   382,   785,   786,   882,   788,   672,   789,   673,
     790,   621,   367,   791,   475,   792,   793,   475,   624,   674,
     796,   199,   829,    54,   799,   200,    15,    16,    17,    18,
     807,   201,   690,   626,    63,   493,   391,   715,   202,   813,
     351,   717,   877,    55,   668,    56,    57,    58,    59,    60,
      61,   669,   670,    68,   671,   722,   672,   723,   673,   400,
     729,   101,   730,   466,   834,   745,   837,   838,   674,    15,
      16,    17,    18,   759,   761,   846,   847,   848,   199,   809,
      63,   845,   200,   812,   824,   831,   842,   843,   201,   372,
     844,    63,   873,   101,   101,   202,   199,   101,   908,   101,
     200,   101,   101,   101,   101,   101,   201,   373,   864,   635,
     467,   608,   935,   202,   924,   938,   358,   358,   955,   966,
     358,   531,   820,    64,   778,   868,    68,    68,   870,   199,
     102,    63,    45,   200,    12,   181,   252,    73,   554,   201,
     878,   366,    13,   169,   358,    62,   202,   883,   648,   649,
     650,    14,    68,    47,   661,   662,    68,    68,    68,   918,
     664,   895,   160,   725,  1003,   828,   899,   900,   731,   902,
     874,   904,   665,   666,   907,    68,   642,   101,   818,   909,
     910,    68,    68,   953,   570,   101,   564,   101,   795,   655,
     934,   587,   920,   921,     0,     0,    64,    64,     0,     0,
     928,     0,   358,   960,   101,     0,   937,   101,   101,     0,
       0,     0,   943,   199,     0,     0,   797,   200,   101,   101,
     101,   101,    64,   201,     0,   199,    64,    64,    64,   200,
     202,    65,     0,   361,     0,   201,     0,    63,     0,   358,
     400,     0,   202,   962,     0,    64,   964,     0,     0,     0,
       0,    64,    64,     0,   968,     0,     0,   181,   170,   171,
     172,   173,   973,     0,   975,     0,    63,   978,     0,     0,
       0,   979,     0,     0,     0,     0,   174,   175,     0,     0,
       0,     0,   667,   351,     0,     0,     0,   668,   991,     0,
       0,     0,    68,     0,   669,   670,     0,   671,     0,   672,
       0,   673,     0,     0,    65,    65,     0,     0,  1001,     0,
       0,   674,   534,   535,   536,     0,   538,   539,     0,     0,
       0,   544,   545,     0,    68,    68,     0,   549,     0,     0,
      65,     0,     0,    66,    65,    65,    65,     0,   170,   171,
     172,   173,     0,     0,   612,     0,     0,     0,    78,     0,
      79,    80,     0,    65,     0,     0,   174,   175,     0,    65,
      65,     0,    64,     0,     0,   181,    81,     0,     0,     0,
      82,    83,    84,    85,    86,     0,     0,     0,   361,     0,
      68,     0,   101,   101,     0,     0,   176,     0,     0,     0,
     101,   101,   101,     0,    64,    64,    15,    16,    17,    18,
    -142,     0,    68,     0,    68,     0,    66,    66,     0,    68,
       0,   101,     0,   101,     0,    68,   177,    68,     0,     0,
       0,     0,    68,     0,   178,     0,     0,     0,    68,   101,
       0,    68,    66,     0,     0,    68,    66,    66,    66,     0,
       0,    67,   607,     0,   609,   610,   611,   613,   614,     0,
      64,     0,     0,     0,     0,    66,     0,     0,    49,     0,
       0,    66,    66,     0,   349,     0,   176,     0,     0,     0,
      65,     0,    64,     0,    64,   170,   171,   172,   173,    64,
       0,    68,   101,     0,   101,    64,     0,    64,     0,    68,
       0,     0,    64,   174,   175,     0,   177,     0,    64,     0,
       0,    64,    65,    65,   533,    64,   794,     0,   211,     0,
       0,   183,     0,     0,    67,    67,     0,    60,     0,    88,
      89,    90,    91,    92,    93,    94,    95,    68,     0,    50,
      51,     0,     0,    52,     0,     0,   344,   345,    68,     0,
      67,     0,   347,     0,    67,    67,    67,     0,     0,     0,
       0,    64,     0,     0,   348,   349,     0,     0,    65,    64,
       0,     0,     0,    67,     0,     0,     0,     0,     0,    67,
      67,     0,    66,   101,   351,     0,     0,     0,    53,     0,
      65,     0,    65,     0,     0,   352,   353,    65,   354,     0,
     355,     0,   356,    65,     0,    65,     0,    64,   101,     0,
      65,    54,   357,   176,    66,    66,    65,     0,    64,    65,
       0,     0,     0,    65,     0,    68,     0,     0,     0,     0,
       0,   212,     0,    56,    57,    58,    59,    60,    61,     0,
       0,     0,     0,   177,     0,     0,     0,     0,     0,     0,
       0,   533,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   783,   784,     0,     0,     0,   787,     0,    65,
      66,     0,     0,     0,   350,   351,     0,    65,     0,     0,
       0,     0,     0,     0,     0,    68,   352,   353,     0,   354,
      67,   355,    66,   356,    66,    64,     0,     0,   811,    66,
       0,     0,     0,   357,     0,    66,   521,    66,     0,     0,
       0,     0,    66,     0,     0,    65,     0,     0,    66,     0,
       0,    66,    67,    67,    96,    66,    65,     0,     0,     0,
       0,    68,     0,   661,   662,   663,     0,     0,     0,   664,
     101,     0,    68,     0,   101,     0,     0,     0,     0,     0,
       0,   665,   666,   101,     0,    64,   182,     0,     0,   101,
     187,     0,   190,     0,   192,   193,   194,   195,   196,     0,
       0,    66,    78,     0,    79,    80,     0,     0,    67,    66,
       0,     0,    68,     0,     0,     0,     0,     0,   869,     0,
      81,     0,     0,     0,    82,    83,    84,    85,    86,     0,
      67,    64,    67,    65,     0,     0,     0,    67,     0,     0,
       0,     0,    64,    67,     0,    67,     0,    66,     0,    78,
      67,    79,    80,     0,     0,     0,    67,     0,    66,    67,
       0,     0,     0,    67,     0,   906,     0,    81,     0,     0,
     309,    82,    83,    84,    85,    86,     0,     0,   317,     0,
     319,     0,    64,     0,     0,     0,     0,     0,    49,     0,
       0,   667,   351,    65,     0,     0,   668,     0,     0,     0,
     368,   369,   632,   669,   670,     0,   671,     0,   672,    67,
     673,   376,   377,   378,   379,     0,     0,    67,    68,     0,
     674,     0,   675,     0,     0,     0,     0,     0,     0,     0,
     101,   101,     0,   681,     0,    66,   933,     0,   211,    65,
       0,   101,     0,     0,     0,     0,     0,    68,     0,     0,
      65,     0,     0,     0,     0,    67,     0,     0,   691,    50,
      51,     0,     0,    52,     0,   183,    67,     0,     0,     0,
       0,    60,     0,    88,    89,    90,    91,    92,    93,    94,
      95,     0,     0,     0,     0,     0,     0,     0,    64,     0,
      65,     0,     0,     1,     0,    66,   328,     4,   329,   330,
     331,   332,     0,   333,     0,     0,     0,     0,    53,     0,
       0,    78,    87,    79,    80,     0,     0,    64,    60,     0,
      88,    89,    90,    91,    92,    93,    94,    95,     0,    81,
       0,    54,     0,    82,    83,    84,    85,    86,     5,     0,
       0,    66,     0,    67,     0,     0,     0,     0,     0,     0,
       0,   212,    66,    56,    57,    58,    59,    60,    61,     0,
       0,     0,   344,   345,   763,   764,   765,     0,   767,   768,
       0,     0,     0,   772,   773,   552,   553,     0,     0,     0,
     777,   349,   779,   780,   555,   556,     0,     0,     0,     0,
       0,    78,    66,   814,    80,     0,    65,     0,     0,     0,
       0,     0,   662,    67,   567,     0,   317,     0,    78,    81,
     914,    80,     0,    82,    83,    84,    85,    86,     0,     0,
     666,     0,   585,     0,     0,    65,    81,     0,     0,     0,
      82,    83,    84,    85,    86,   822,   823,     0,     0,     0,
       0,   827,     0,     0,    78,     0,   916,    80,     0,    67,
     832,   833,   835,   836,     0,     0,     0,     0,     0,     0,
      67,     0,    81,     0,     0,   849,    82,    83,    84,    85,
      86,     0,     0,     0,   183,   595,     0,   599,     0,     0,
      60,     0,    88,    89,    90,    91,    92,    93,    94,    95,
       0,   351,     0,     0,     0,     0,     0,     0,    66,     0,
      67,     0,   352,   353,     0,   354,     0,   355,     0,   356,
       0,     0,     0,     0,   661,   662,   663,     0,     0,   357,
     664,     0,   875,     0,     0,     0,     0,    66,   879,     0,
     351,     0,   665,   666,   668,     0,     0,     0,     0,     0,
       0,   669,   670,     0,   671,     0,   672,   893,   673,     0,
       0,     0,    49,     0,    87,   901,     0,   903,   674,   905,
      60,     0,    88,    89,    90,    91,    92,    93,    94,    95,
       0,    87,     0,     0,     0,     0,     0,    60,     0,    88,
      89,    90,    91,    92,    93,    94,    95,     0,   929,     0,
     932,   705,   269,   936,     0,   939,     0,     0,     0,     0,
     944,   945,     0,     0,     0,     0,    67,    87,     0,     0,
       0,     0,     0,    60,     0,    88,    89,    90,    91,    92,
      93,    94,    95,    50,    51,   959,     0,    52,   345,   961,
       0,     0,     0,     0,   965,    67,     0,     0,     0,     0,
       0,     0,   667,   351,   969,   970,   349,   668,     0,     0,
     974,     0,   976,   977,   669,   670,     0,   671,     0,   672,
     982,   673,     0,   273,   274,     0,   986,     0,     0,   988,
     936,   674,    53,   781,   990,     0,     0,   275,     0,     1,
       0,     0,   328,     4,   329,   330,   331,   332,   996,   530,
     997,     0,   999,     0,     0,    54,  1002,     0,     0,     0,
       0,     0,     0,  1004,     0,   303,   304,     0,  1006,     0,
       0,     0,     0,     0,     0,   219,     0,    56,    57,    58,
      59,    60,    61,   801,     5,   305,   306,   808,     0,     0,
       0,     0,     0,     0,     0,     0,   815,     0,     0,     0,
       0,     0,   821,   496,   497,   498,   499,     0,     0,   500,
       0,   501,   502,   503,   504,   505,   351,     0,   106,     0,
       0,   506,   507,     0,     0,     0,     0,   352,   353,     0,
     354,     0,   355,     0,   356,     0,     0,     0,     0,     0,
       0,   107,   980,   981,   357,     0,     0,   508,     0,   509,
       0,     0,     0,   510,     0,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,     0,     0,   131,   132,     0,
       0,    52,   133,     0,   134,   135,   136,     0,     0,     0,
       0,     0,   511,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   137,   138,   139,    53,   512,   513,   514,
       0,   515,     0,     0,     0,     0,   516,     0,     0,   140,
       0,     0,     0,   915,   917,     0,     0,     0,     0,    54,
       0,   517,   518,   519,   925,     0,   520,   141,     0,   142,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   143,
     144,    56,    57,    58,    59,    60,    61,   496,   497,   498,
     499,     0,     0,   500,     0,   501,   502,   503,   504,   505,
       0,     0,   106,     0,     0,   506,   507,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   269,     0,     0,     0,
       0,     0,     0,     0,     0,   107,     0,     0,     0,     0,
       0,   508,     0,   509,   270,     0,     0,   510,     0,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,     0,
       0,   131,   132,     0,     0,    52,   133,     0,   134,   135,
     136,     0,   630,     0,     0,     0,   511,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,     0,   137,   138,   139,
      53,   512,   513,   514,     0,   515,     0,     0,     0,     0,
     516,   295,   296,   140,     0,   299,   300,   301,   302,   303,
     304,     0,     0,    54,     0,   517,   518,   519,     0,     0,
     520,   141,     0,   142,     0,     0,     0,     0,     0,   305,
     306,     0,     0,   631,   144,    56,    57,    58,    59,    60,
      61,   496,   497,   498,   499,     0,     0,   500,     0,   501,
     502,   503,   504,   505,     0,     0,   106,     0,     0,   506,
     507,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     269,     0,     0,     0,     0,     0,     0,     0,     0,   107,
       0,     0,     0,     0,     0,   508,     0,   509,   270,     0,
       0,   510,     0,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,     0,     0,   131,   132,     0,     0,    52,
     133,     0,   134,   135,   136,     0,     0,     0,     0,     0,
     511,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
       0,   137,   138,   139,    53,   512,   513,   514,     0,   515,
       0,     0,     0,     0,   516,   295,     0,   140,     0,   299,
     300,   301,   302,   303,   304,     0,     0,    54,     0,   517,
     518,   519,     0,     0,   520,   141,     0,   142,     0,     0,
       0,     0,     0,   305,   306,     0,     0,   143,   144,    56,
      57,    58,    59,    60,    61,   496,   497,   498,   499,     0,
       0,   500,     0,   501,   502,   503,   504,   505,     0,     0,
     106,     0,     0,   506,   507,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   107,     0,     0,     0,     0,     0,   508,
       0,   509,     0,     0,     0,   510,     0,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,     0,     0,   131,
     132,     0,     0,    52,   133,     0,   134,   135,   136,     0,
       0,     0,     0,     0,   511,     0,     0,   661,   662,   663,
       0,     0,     0,   664,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   665,   666,     0,     0,     0,
       0,     0,     0,     0,     0,   137,   138,   139,    53,   512,
     513,   514,     0,   515,     0,     0,     0,     0,   516,     0,
       0,   140,   103,     0,   104,     0,     0,   105,     0,     0,
       0,    54,     0,   517,   518,   519,   106,     0,   520,   141,
       0,   142,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   631,   144,    56,    57,    58,    59,    60,    61,   107,
     804,   805,     0,     0,     0,   108,     0,   109,     0,     0,
       0,   110,     0,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,     0,     0,   131,   132,     0,     0,    52,
     133,     0,   134,   135,   136,   667,   351,     0,     0,     0,
     668,     0,     0,   661,   662,   663,     0,   669,   670,   664,
     671,     0,   672,     0,   673,     0,     0,     0,     0,     0,
       0,   665,   666,     0,   674,     0,   998,     0,     0,     0,
       0,   137,   138,   139,    53,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   140,   103,     0,
     104,   884,   885,   105,     0,     0,     0,    54,     0,     0,
       0,     0,   106,   206,     0,   141,   269,   142,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   143,   144,    56,
      57,    58,    59,    60,    61,   107,     0,     0,     0,     0,
       0,   108,     0,   109,     0,     0,     0,   110,     0,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,     0,
       0,   131,   132,     0,     0,    52,   133,     0,   134,   135,
     136,   667,   351,     0,     0,     0,   668,   273,   274,     0,
       0,     0,     0,   669,   670,     0,   671,     0,   672,     0,
     673,   275,     0,   661,   662,   663,     0,   737,     0,   664,
     674,     0,     0,     0,     0,     0,     0,   137,   138,   139,
      53,   665,   666,     0,     0,   299,   300,   301,   302,   303,
     304,     0,     0,   140,   103,     0,   104,     0,     0,   105,
       0,     0,     0,    54,     0,     0,     0,     0,   106,   305,
     306,   141,   269,   142,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   143,   144,    56,    57,    58,    59,    60,
      61,   107,     0,     0,   635,     0,     0,   108,     0,   109,
       0,     0,     0,   110,     0,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,     0,     0,   131,   132,     0,
       0,    52,   133,     0,   134,   135,   136,     0,     0,     0,
       0,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,   667,   351,     0,     0,     0,   668,   275,     0,     0,
       0,     0,     0,   669,   670,     0,   671,     0,   672,     0,
     673,     0,     0,   137,   138,   139,    53,   295,     0,     0,
     674,   299,   300,   301,   302,   303,   304,     0,     0,   140,
     103,     0,   104,     0,   646,   105,     0,     0,     0,    54,
       0,     0,     0,     0,   106,   305,   306,   141,     0,   142,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   143,
     144,    56,    57,    58,    59,    60,    61,   107,     0,     0,
       0,     0,     0,   108,     0,   109,     0,     0,     0,   110,
       0,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,     0,     0,   131,   132,     0,     0,    52,   133,     0,
     134,   135,   136,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   661,   662,   663,     0,   881,     0,   664,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     665,   666,     0,     0,     0,     0,     0,     0,     0,   137,
     138,   139,    53,     0,     0,   344,   345,   346,     0,   718,
       0,   347,     0,     0,     0,   140,   103,     0,   104,     0,
       0,   105,     0,   348,   349,    54,     0,     0,     0,     0,
     106,     0,     0,   141,     0,   142,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   143,   144,    56,    57,    58,
      59,    60,    61,   107,     0,     0,     0,     0,     0,   108,
       0,   109,     0,     0,     0,   110,     0,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,     0,     0,   131,
     132,     0,     0,    52,   133,     0,   134,   135,   136,   267,
     667,   351,     0,     0,   268,   668,   269,     0,     0,     0,
       0,     0,   669,   670,     0,   671,     0,   672,     0,   673,
       0,   661,   662,   663,   270,   923,     0,   664,     0,   674,
       0,     0,     0,   350,   351,   137,   138,   139,    53,   665,
     666,   661,   662,   663,     0,   352,   353,   664,   354,     0,
     355,   140,   356,   271,   272,     0,     0,     0,     0,   665,
     666,    54,   357,     0,     0,     0,     0,     0,     0,   141,
       0,   142,     0,     0,     0,     0,     0,   273,   274,     0,
       0,   143,   144,    56,    57,    58,    59,    60,    61,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,     0,     0,   344,   345,   346,     0,     0,     0,   347,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,   348,   349,     0,     0,     0,     0,     0,     0,   667,
     351,     0,     0,     0,   668,     0,     0,     0,     0,     0,
     954,   669,   670,     0,   671,     0,   672,     0,   673,   667,
     351,     0,     0,     0,   668,     0,     0,     0,   674,     0,
       0,   669,   670,     0,   671,     0,   672,     0,   673,     0,
       0,     0,     0,     0,   267,     0,     0,     0,   674,   268,
       0,   269,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   270,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   271,   272,
       0,   350,   351,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   352,   353,     0,   354,   269,   355,     0,
     356,     0,   273,   274,     0,     0,     0,     0,     0,     0,
     357,     0,     0,     0,     0,   270,   275,   276,   277,   278,
     279,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   267,     0,     0,     0,
       0,   268,     0,   269,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   305,   306,     0,     0,   273,   274,
       0,   270,     0,     0,     0,   971,     0,     0,   972,     0,
       0,     0,   275,   276,   277,   278,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     271,   272,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   273,   274,     0,     0,     0,     0,
     305,   306,     0,     0,     0,     0,     0,     0,   275,   276,
     277,   278,   279,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   267,     0,
       0,     0,     0,   268,     0,   269,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   305,   306,     0,     0,
       0,     0,     0,   270,     0,     0,     0,   762,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   271,   272,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   273,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     267,     0,     0,     0,     0,   268,     0,   269,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,   270,     0,     0,     0,   897,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   271,   272,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   273,   274,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   275,   276,   277,   278,   279,   280,   281,   282,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   267,     0,     0,     0,     0,   268,     0,   269,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     305,   306,     0,     0,     0,     0,     0,   270,     0,     0,
       0,   898,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   271,   272,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     273,   274,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   275,   276,   277,   278,   279,   280,
     281,   282,   283,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   267,     0,     0,     0,     0,   268,
       0,   269,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   305,   306,     0,     0,     0,     0,     0,   270,
       0,     0,     0,   942,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   271,   272,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   273,   274,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   275,   276,   277,   278,
     279,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,     0,   267,     0,     0,
       0,     0,   268,     0,   269,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   305,   306,   850,   851,     0,     0,
       0,     0,   270,     0,     0,  1000,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   267,
       0,     0,     0,     0,   268,     0,   269,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   305,   306,   683,
       0,     0,     0,   852,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,     0,     0,     0,     0,   684,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   267,     0,     0,     0,     0,   268,     0,
     269,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,     0,     0,   689,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   267,     0,     0,     0,     0,
     268,     0,   269,     0,     0,     0,     0,     0,     0,     0,
     679,     0,     0,   305,   306,     0,     0,   856,     0,     0,
     270,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   699,   700,     0,     0,   271,
     272,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,   276,   277,
     278,   279,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,     0,   267,     0,
       0,     0,     0,   268,   680,   269,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   305,   306,   385,     0,     0,
       0,     0,     0,   270,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   271,   272,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   386,   387,     0,     0,   273,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     267,     0,     0,     0,     0,   268,   380,   269,     0,     0,
       0,     0,     0,     0,     0,   679,     0,     0,   305,   306,
       0,     0,     0,     0,     0,   270,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   271,   272,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   273,   274,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   275,   276,   277,   278,   279,   280,   281,   282,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,     0,   267,     0,     0,     0,     0,   268,   680,
     269,     0,     0,     0,     0,     0,     0,     0,   679,     0,
     305,   306,     0,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,     0,   267,     0,     0,     0,
       0,   268,   680,   269,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   305,   306,     0,     0,     0,     0,     0,
       0,   270,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   699,   700,     0,     0,
     271,   272,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   273,   274,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   275,   276,
     277,   278,   279,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   267,     0,
       0,     0,     0,   268,     0,   269,     0,     0,     0,     0,
       0,     0,     0,   749,     0,     0,   305,   306,     0,     0,
       0,     0,     0,   270,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   750,     0,     0,
       0,     0,   271,   272,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   273,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
       0,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306,
     853,     0,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,     0,   267,     0,     0,     0,     0,   268,
       0,   269,     0,   926,   854,     0,     0,     0,     0,     0,
       0,   305,   306,   927,     0,     0,     0,     0,     0,   270,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   271,   272,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   273,   274,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   275,   276,   277,   278,
     279,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,     0,   267,     0,     0,
       0,     0,   268,     0,   269,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   305,   306,   948,     0,     0,     0,
       0,     0,   270,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   267,
       0,     0,     0,     0,   268,   313,   269,     0,     0,     0,
     949,     0,     0,     0,     0,     0,     0,   305,   306,     0,
       0,     0,     0,     0,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   267,     0,     0,     0,     0,   268,   380,   269,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,     0,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,     0,   267,     0,     0,     0,     0,   268,
       0,   269,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,   383,     0,     0,     0,     0,     0,   270,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   271,   272,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   273,   274,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   275,   276,   277,   278,
     279,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   267,     0,     0,     0,
       0,   268,     0,   269,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   305,   306,     0,     0,     0,     0,
       0,   270,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   397,     0,     0,     0,     0,
     271,   272,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   273,   274,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   275,   276,
     277,   278,   279,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   267,     0,
       0,     0,     0,   268,     0,   269,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   305,   306,     0,     0,
       0,     0,     0,   270,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   271,   272,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   273,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
       0,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,   398,     0,     0,   305,   306,
     416,     0,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   267,     0,     0,     0,     0,   268,     0,
     269,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   267,     0,     0,     0,     0,
     268,     0,   269,     0,     0,     0,   481,     0,     0,     0,
       0,     0,     0,   305,   306,     0,     0,     0,     0,     0,
     270,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   271,
     272,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,   276,   277,
     278,   279,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   267,     0,     0,
       0,     0,   268,     0,   269,     0,     0,     0,     0,   579,
       0,     0,     0,     0,     0,   305,   306,     0,     0,     0,
       0,     0,   270,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   267,
       0,     0,     0,     0,   268,   591,   269,     0,     0,     0,
       0,   582,     0,     0,     0,     0,     0,   305,   306,     0,
       0,     0,     0,     0,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,     0,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   593,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   267,     0,     0,     0,     0,   268,     0,
     269,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   267,     0,     0,     0,     0,
     268,     0,   269,     0,     0,     0,   685,     0,     0,     0,
       0,   688,     0,   305,   306,     0,     0,     0,     0,     0,
     270,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   271,
     272,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,   276,   277,
     278,   279,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,     0,     0,   267,
       0,     0,     0,     0,   268,     0,   269,     0,     0,     0,
       0,     0,     0,     0,     0,   305,   306,   696,     0,     0,
       0,     0,     0,     0,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,     0,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   387,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   267,     0,     0,     0,     0,   268,   706,
     269,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   267,     0,     0,     0,     0,
     268,     0,   269,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   305,   306,     0,     0,     0,     0,     0,
     270,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   271,
     272,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,   276,   277,
     278,   279,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   267,     0,     0,
       0,     0,   268,     0,   269,     0,     0,     0,     0,     0,
       0,     0,   753,     0,     0,   305,   306,     0,     0,     0,
       0,     0,   270,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   267,
       0,     0,     0,     0,   268,     0,   269,     0,     0,     0,
     754,     0,     0,     0,     0,     0,     0,   305,   306,     0,
       0,     0,     0,     0,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,   755,   749,     0,     0,   305,
     306,     0,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   267,     0,     0,     0,     0,   268,   798,
     269,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   267,     0,     0,     0,     0,
     268,   800,   269,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   305,   306,     0,     0,     0,     0,     0,
     270,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   271,
     272,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,   276,   277,
     278,   279,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   267,     0,     0,
       0,     0,   268,     0,   269,     0,   810,     0,     0,     0,
       0,     0,     0,     0,     0,   305,   306,     0,     0,     0,
       0,     0,   270,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,     0,
     267,     0,     0,     0,     0,   268,     0,   269,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   305,   306,   855,
       0,     0,     0,     0,     0,   270,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   271,   272,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   273,   274,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   275,   276,   277,   278,   279,   280,   281,   282,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   267,     0,     0,     0,     0,   268,     0,   269,
       0,   857,     0,     0,     0,     0,     0,     0,     0,     0,
     305,   306,     0,     0,     0,     0,     0,   270,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   271,   272,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     273,   274,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   275,   276,   277,   278,   279,   280,
     281,   282,   283,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   267,     0,     0,     0,     0,   268,
       0,   269,     0,     0,     0,     0,     0,     0,     0,     0,
     858,     0,   305,   306,     0,     0,     0,     0,     0,   270,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   271,   272,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   273,   274,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   275,   276,   277,   278,
     279,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,     0,   267,     0,     0,
       0,     0,   268,     0,   269,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   305,   306,   859,     0,     0,     0,
       0,     0,   270,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   267,
       0,     0,     0,     0,   268,     0,   269,     0,   860,     0,
       0,     0,     0,     0,     0,     0,     0,   305,   306,     0,
       0,     0,     0,     0,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,     0,     0,   267,     0,     0,     0,     0,   268,     0,
     269,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,   861,     0,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,     0,     0,   267,     0,     0,
       0,     0,   268,     0,   269,     0,     0,     0,     0,     0,
       0,     0,     0,   305,   306,   862,     0,     0,     0,     0,
       0,     0,   270,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   267,
       0,     0,     0,     0,   268,     0,   269,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   305,   306,     0,
       0,     0,     0,     0,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   863,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,     0,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   267,     0,     0,     0,     0,   268,     0,
     269,     0,     0,     0,     0,     0,     0,     0,   887,     0,
     865,   305,   306,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   267,     0,     0,     0,     0,
     268,     0,   269,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   305,   306,     0,     0,     0,     0,     0,
     270,   896,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   271,
     272,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,   276,   277,
     278,   279,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   267,     0,     0,
       0,     0,   268,   913,   269,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   305,   306,     0,     0,     0,
       0,     0,   270,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   267,
       0,     0,     0,     0,   268,     0,   269,     0,   922,     0,
       0,     0,     0,     0,     0,     0,     0,   305,   306,     0,
       0,     0,     0,     0,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,   946,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   267,     0,     0,     0,     0,   268,     0,
     269,     0,   947,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   267,     0,     0,     0,     0,
     268,     0,   269,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   305,   306,     0,     0,     0,     0,     0,
     270,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   271,
     272,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,   276,   277,
     278,   279,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   267,     0,     0,
       0,     0,   268,     0,   269,     0,     0,     0,     0,     0,
       0,   950,     0,     0,     0,   305,   306,     0,     0,     0,
       0,     0,   270,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   963,     0,     0,     0,
       0,   271,   272,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   273,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
     276,   277,   278,   279,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   267,
       0,     0,     0,     0,   268,     0,   269,     0,   985,     0,
       0,     0,     0,     0,     0,     0,     0,   305,   306,     0,
       0,     0,     0,     0,   270,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   271,   272,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   273,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   267,     0,     0,     0,     0,   268,     0,   269,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,     0,     0,     0,     0,     0,   270,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     987,     0,     0,     0,     0,   271,   272,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   273,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   276,   277,   278,   279,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   267,     0,     0,     0,     0,   268,     0,
     269,     0,   989,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,     0,     0,     0,     0,     0,   270,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   271,   272,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   273,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   276,   277,   278,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,     0,   267,     0,     0,     0,
       0,   268,     0,   269,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   305,   306,   992,     0,     0,     0,     0,
       0,   270,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     271,   272,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   273,   274,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   275,   276,
     277,   278,   279,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   267,     0,
       0,     0,     0,   268,     0,   269,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   305,   306,     0,     0,
       0,     0,     0,   270,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   271,   272,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   273,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     267,     0,     0,     0,     0,   268,     0,   269,     0,     0,
       0,   993,     0,     0,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,   270,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   271,   272,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   273,   274,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   275,   276,   277,   278,   279,   280,   281,   282,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   267,     0,     0,     0,     0,   268,     0,   269,
       0,  1005,   994,     0,     0,     0,     0,     0,     0,     0,
     305,   306,     0,     0,     0,     0,     0,   270,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   271,   272,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     273,   274,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   275,   276,   277,   278,   279,   280,
     281,   282,   283,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   267,     0,     0,     0,     0,   268,
       0,   269,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   305,   306,     0,     0,     0,     0,     0,   270,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   271,   272,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   273,   274,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   275,   276,   277,   278,
     279,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   267,     0,     0,     0,
       0,     0,     0,   269,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   305,   306,     0,     0,     0,     0,
       0,   270,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     271,   272,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   273,   274,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   275,   276,
     277,   278,   279,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   267,     0,
       0,     0,     0,     0,     0,   269,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   305,   306,     0,     0,
       0,     0,     0,   270,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   271,   272,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   273,   274,   267,     0,
       0,     0,     0,     0,     0,   269,     0,     0,     0,     0,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   270,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
       0,     0,     0,     0,     0,     0,     0,     0,   267,     0,
       0,     0,   271,   272,     0,   269,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   270,     0,     0,   273,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   271,   272,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
       0,     0,     0,     0,     0,   267,   273,   274,     0,     0,
       0,     0,   269,     0,     0,     0,     0,     0,   305,   306,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     270,   286,     0,     0,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   271,
     272,     0,   269,     0,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     270,     0,     0,   273,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,   276,   277,
     278,   279,   280,   281,   282,   283,   284,     0,     0,   271,
     272,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,     0,     0,     0,
       0,     0,     0,   273,   274,   269,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   305,   306,   275,   276,   277,
     278,   279,     0,   270,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   305,   306,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   273,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,   276,   277,   278,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
        44,   468,   139,   185,    72,    49,   133,   331,   630,    21,
       3,     4,     5,   107,     7,    34,    33,   138,    17,    27,
     156,    17,    31,    19,    30,   106,   162,   163,   636,   158,
      27,    31,    33,    49,    13,    13,    13,    59,    13,   133,
      12,    12,    12,    90,   305,   139,    27,   180,    59,    59,
       1,    30,    34,     4,     5,    48,    17,   104,    19,   140,
     104,   105,   106,    44,    13,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,    13,    39,   108,
      30,    33,    13,    13,    18,    27,   140,    48,    13,    17,
     180,    21,   180,   273,    30,   173,     0,    12,   118,     3,
      27,     0,     6,     7,     8,     9,    10,    11,   180,    13,
      71,   165,   383,   167,   146,   107,   108,    44,    46,    34,
     146,    17,   176,    19,   146,   146,   306,    13,   146,   149,
     146,    33,   143,   171,   172,    18,   174,   175,   146,   146,
     146,   133,   179,   149,    48,   137,   138,   139,   159,    18,
     161,   485,    33,   180,   173,   246,   247,   146,   146,   146,
     171,   146,   253,   173,   156,   173,   151,   149,   149,   149,
     162,   163,   175,    62,    63,   102,    18,   268,   149,   188,
     189,   190,   191,   192,   193,   194,   195,   146,   806,    18,
      33,   143,   246,   247,   826,   147,   146,   251,    18,   253,
     254,   305,   154,   155,   146,   157,    12,   159,    33,   161,
     146,   265,   146,   267,   268,   146,   270,   271,   272,   171,
      17,   146,   276,   277,   278,   279,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   342,   398,   149,   385,   394,   874,   361,   144,   390,
     146,   180,   143,   146,    18,    33,   403,   744,     2,    18,
      67,    33,   176,   177,   178,   179,   157,   146,   159,   383,
     161,   273,    18,   146,   118,   416,    18,    12,   342,    19,
     171,    88,    89,   347,    18,    92,   350,   351,   352,   403,
      47,   355,   356,   357,   146,   409,    17,    41,    19,    43,
     173,   118,   146,   305,   306,   149,   462,   146,   143,    45,
      17,    17,   147,    19,    17,    98,   146,    13,   382,    17,
      13,    19,   386,   387,   159,   389,   161,   391,    72,   393,
     137,   395,   149,   397,    56,    57,   171,    33,   402,    46,
      33,   405,    17,   146,   408,    13,   180,   411,   151,   413,
     414,    91,   554,   160,    90,   983,   470,   396,   165,   361,
      17,   332,   519,   146,    67,    33,   149,   144,   104,   146,
      13,    13,    19,   180,   180,   182,   183,   184,   185,   186,
     187,   383,   146,   385,    17,    88,    89,   146,   390,    92,
      33,    33,   132,   133,   396,    17,   398,    17,    13,    17,
     146,   403,   466,    17,   146,   519,    13,   409,   472,   473,
     412,   499,   146,   149,   416,    17,   517,    92,    33,   176,
     177,   178,   179,   180,    98,   180,    33,    33,   492,   173,
     180,    36,   919,   481,   137,   499,   500,   138,   139,   140,
     596,   505,    37,    13,    91,   509,   510,   511,   496,   497,
     498,   515,   516,   517,   518,    13,   520,   160,   506,   507,
     462,    27,   137,    33,   146,    31,   180,   149,   470,   508,
      31,    37,    13,    13,    13,    33,    13,   180,    44,   182,
     183,   184,   185,   186,   187,   160,   133,   534,   535,   536,
      92,   538,   539,    33,    33,   149,    33,   544,   545,    17,
      17,   146,   549,    13,   149,   180,   508,   182,   183,   184,
     185,   186,   187,     0,    92,    12,     3,   519,   582,     6,
       7,     8,     9,    10,    11,   146,    13,   180,   149,   593,
      27,    14,    15,    21,    31,   137,   102,   143,   602,    27,
      37,   147,   656,    31,   180,   149,   702,    44,   612,    37,
      33,   157,   616,   159,    12,   161,    44,   713,   160,   146,
     607,    48,   609,   610,   611,   171,   613,   614,    13,    27,
     180,    88,    89,    31,   143,    92,    13,   180,   180,    37,
     182,   183,   184,   185,   186,   187,    44,    13,    33,    27,
     159,   180,   161,    31,   596,    50,    33,   753,    13,    37,
     664,    12,   171,   667,   668,   669,    44,    33,   672,   673,
     674,   143,   180,   180,    13,   147,   360,    13,    33,   683,
     137,    59,   146,   687,   688,   149,   690,   159,   692,   161,
     694,    12,    18,   697,    33,   699,   700,    33,   180,   171,
     704,    27,   743,   160,   708,    31,   176,   177,   178,   179,
     714,    37,    46,    33,   656,   626,   118,   149,    44,   723,
     143,    18,   100,   180,   147,   182,   183,   184,   185,   186,
     187,   154,   155,    34,   157,   180,   159,    12,   161,   743,
      12,    42,   149,    49,   748,    21,   750,   751,   171,   176,
     177,   178,   179,    12,    59,   759,   760,   761,    27,    17,
     702,   118,    31,    12,    20,    20,   754,   755,    37,    38,
      17,   713,   180,    74,    75,    44,    27,    78,   859,    80,
      31,    82,    83,    84,    85,    86,    37,    38,    27,    55,
     146,   475,   888,    44,    17,    20,   783,   784,    51,    27,
     787,   334,   729,    34,   678,   809,   107,   108,   812,    27,
      43,   753,    27,    31,     0,   499,   134,    39,   371,    37,
     824,   185,     0,    71,   811,    34,    44,   831,   512,   513,
     514,     0,   133,    29,    14,    15,   137,   138,   139,   873,
      20,   845,    48,   619,   995,   741,   850,   851,   626,   853,
     817,   855,    32,    33,   858,   156,   508,   158,   725,   863,
     864,   162,   163,   909,   396,   166,   389,   168,   702,   518,
     888,   412,   876,   877,    -1,    -1,   107,   108,    -1,    -1,
     884,    -1,   869,   924,   185,    -1,   890,   188,   189,    -1,
      -1,    -1,   896,    27,    -1,    -1,    18,    31,   199,   200,
     201,   202,   133,    37,    -1,    27,   137,   138,   139,    31,
      44,    34,    -1,    47,    -1,    37,    -1,   859,    -1,   906,
     924,    -1,    44,   927,    -1,   156,   930,    -1,    -1,    -1,
      -1,   162,   163,    -1,   938,    -1,    -1,   621,    14,    15,
      16,    17,   946,    -1,   948,    -1,   888,   951,    -1,    -1,
      -1,   955,    -1,    -1,    -1,    -1,    32,    33,    -1,    -1,
      -1,    -1,   142,   143,    -1,    -1,    -1,   147,   972,    -1,
      -1,    -1,   273,    -1,   154,   155,    -1,   157,    -1,   159,
      -1,   161,    -1,    -1,   107,   108,    -1,    -1,   992,    -1,
      -1,   171,   344,   345,   346,    -1,   348,   349,    -1,    -1,
      -1,   353,   354,    -1,   305,   306,    -1,   359,    -1,    -1,
     133,    -1,    -1,    34,   137,   138,   139,    -1,    14,    15,
      16,    17,    -1,    -1,    20,    -1,    -1,    -1,    17,    -1,
      19,    20,    -1,   156,    -1,    -1,    32,    33,    -1,   162,
     163,    -1,   273,    -1,    -1,   729,    35,    -1,    -1,    -1,
      39,    40,    41,    42,    43,    -1,    -1,    -1,    47,    -1,
     361,    -1,   363,   364,    -1,    -1,   142,    -1,    -1,    -1,
     371,   372,   373,    -1,   305,   306,   176,   177,   178,   179,
     180,    -1,   383,    -1,   385,    -1,   107,   108,    -1,   390,
      -1,   392,    -1,   394,    -1,   396,   172,   398,    -1,    -1,
      -1,    -1,   403,    -1,   180,    -1,    -1,    -1,   409,   410,
      -1,   412,   133,    -1,    -1,   416,   137,   138,   139,    -1,
      -1,    34,   474,    -1,   476,   477,   478,   479,   480,    -1,
     361,    -1,    -1,    -1,    -1,   156,    -1,    -1,    17,    -1,
      -1,   162,   163,    -1,    33,    -1,   142,    -1,    -1,    -1,
     273,    -1,   383,    -1,   385,    14,    15,    16,    17,   390,
      -1,   462,   463,    -1,   465,   396,    -1,   398,    -1,   470,
      -1,    -1,   403,    32,    33,    -1,   172,    -1,   409,    -1,
      -1,   412,   305,   306,   180,   416,    65,    -1,    67,    -1,
      -1,   180,    -1,    -1,   107,   108,    -1,   186,    -1,   188,
     189,   190,   191,   192,   193,   194,   195,   508,    -1,    88,
      89,    -1,    -1,    92,    -1,    -1,    14,    15,   519,    -1,
     133,    -1,    20,    -1,   137,   138,   139,    -1,    -1,    -1,
      -1,   462,    -1,    -1,    32,    33,    -1,    -1,   361,   470,
      -1,    -1,    -1,   156,    -1,    -1,    -1,    -1,    -1,   162,
     163,    -1,   273,   554,   143,    -1,    -1,    -1,   137,    -1,
     383,    -1,   385,    -1,    -1,   154,   155,   390,   157,    -1,
     159,    -1,   161,   396,    -1,   398,    -1,   508,   579,    -1,
     403,   160,   171,   142,   305,   306,   409,    -1,   519,   412,
      -1,    -1,    -1,   416,    -1,   596,    -1,    -1,    -1,    -1,
      -1,   180,    -1,   182,   183,   184,   185,   186,   187,    -1,
      -1,    -1,    -1,   172,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   180,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   684,   685,    -1,    -1,    -1,   689,    -1,   462,
     361,    -1,    -1,    -1,   142,   143,    -1,   470,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   656,   154,   155,    -1,   157,
     273,   159,   383,   161,   385,   596,    -1,    -1,   720,   390,
      -1,    -1,    -1,   171,    -1,   396,   333,   398,    -1,    -1,
      -1,    -1,   403,    -1,    -1,   508,    -1,    -1,   409,    -1,
      -1,   412,   305,   306,    42,   416,   519,    -1,    -1,    -1,
      -1,   702,    -1,    14,    15,    16,    -1,    -1,    -1,    20,
     711,    -1,   713,    -1,   715,    -1,    -1,    -1,    -1,    -1,
      -1,    32,    33,   724,    -1,   656,    74,    -1,    -1,   730,
      78,    -1,    80,    -1,    82,    83,    84,    85,    86,    -1,
      -1,   462,    17,    -1,    19,    20,    -1,    -1,   361,   470,
      -1,    -1,   753,    -1,    -1,    -1,    -1,    -1,   810,    -1,
      35,    -1,    -1,    -1,    39,    40,    41,    42,    43,    -1,
     383,   702,   385,   596,    -1,    -1,    -1,   390,    -1,    -1,
      -1,    -1,   713,   396,    -1,   398,    -1,   508,    -1,    17,
     403,    19,    20,    -1,    -1,    -1,   409,    -1,   519,   412,
      -1,    -1,    -1,   416,    -1,   857,    -1,    35,    -1,    -1,
     158,    39,    40,    41,    42,    43,    -1,    -1,   166,    -1,
     168,    -1,   753,    -1,    -1,    -1,    -1,    -1,    17,    -1,
      -1,   142,   143,   656,    -1,    -1,   147,    -1,    -1,    -1,
     188,   189,   499,   154,   155,    -1,   157,    -1,   159,   462,
     161,   199,   200,   201,   202,    -1,    -1,   470,   859,    -1,
     171,    -1,   173,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     871,   872,    -1,   530,    -1,   596,    65,    -1,    67,   702,
      -1,   882,    -1,    -1,    -1,    -1,    -1,   888,    -1,    -1,
     713,    -1,    -1,    -1,    -1,   508,    -1,    -1,   173,    88,
      89,    -1,    -1,    92,    -1,   180,   519,    -1,    -1,    -1,
      -1,   186,    -1,   188,   189,   190,   191,   192,   193,   194,
     195,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   859,    -1,
     753,    -1,    -1,     3,    -1,   656,     6,     7,     8,     9,
      10,    11,    -1,    13,    -1,    -1,    -1,    -1,   137,    -1,
      -1,    17,   180,    19,    20,    -1,    -1,   888,   186,    -1,
     188,   189,   190,   191,   192,   193,   194,   195,    -1,    35,
      -1,   160,    -1,    39,    40,    41,    42,    43,    48,    -1,
      -1,   702,    -1,   596,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   180,   713,   182,   183,   184,   185,   186,   187,    -1,
      -1,    -1,    14,    15,   661,   662,   663,    -1,   665,   666,
      -1,    -1,    -1,   670,   671,   363,   364,    -1,    -1,    -1,
     677,    33,   679,   680,   372,   373,    -1,    -1,    -1,    -1,
      -1,    17,   753,    19,    20,    -1,   859,    -1,    -1,    -1,
      -1,    -1,    15,   656,   392,    -1,   394,    -1,    17,    35,
      19,    20,    -1,    39,    40,    41,    42,    43,    -1,    -1,
      33,    -1,   410,    -1,    -1,   888,    35,    -1,    -1,    -1,
      39,    40,    41,    42,    43,   732,   733,    -1,    -1,    -1,
      -1,   738,    -1,    -1,    17,    -1,    19,    20,    -1,   702,
     747,   748,   749,   750,    -1,    -1,    -1,    -1,    -1,    -1,
     713,    -1,    35,    -1,    -1,   762,    39,    40,    41,    42,
      43,    -1,    -1,    -1,   180,   463,    -1,   465,    -1,    -1,
     186,    -1,   188,   189,   190,   191,   192,   193,   194,   195,
      -1,   143,    -1,    -1,    -1,    -1,    -1,    -1,   859,    -1,
     753,    -1,   154,   155,    -1,   157,    -1,   159,    -1,   161,
      -1,    -1,    -1,    -1,    14,    15,    16,    -1,    -1,   171,
      20,    -1,   819,    -1,    -1,    -1,    -1,   888,   825,    -1,
     143,    -1,    32,    33,   147,    -1,    -1,    -1,    -1,    -1,
      -1,   154,   155,    -1,   157,    -1,   159,   844,   161,    -1,
      -1,    -1,    17,    -1,   180,   852,    -1,   854,   171,   856,
     186,    -1,   188,   189,   190,   191,   192,   193,   194,   195,
      -1,   180,    -1,    -1,    -1,    -1,    -1,   186,    -1,   188,
     189,   190,   191,   192,   193,   194,   195,    -1,   885,    -1,
     887,   579,    19,   890,    -1,   892,    -1,    -1,    -1,    -1,
     897,   898,    -1,    -1,    -1,    -1,   859,   180,    -1,    -1,
      -1,    -1,    -1,   186,    -1,   188,   189,   190,   191,   192,
     193,   194,   195,    88,    89,   922,    -1,    92,    15,   926,
      -1,    -1,    -1,    -1,   931,   888,    -1,    -1,    -1,    -1,
      -1,    -1,   142,   143,   941,   942,    33,   147,    -1,    -1,
     947,    -1,   949,   950,   154,   155,    -1,   157,    -1,   159,
     957,   161,    -1,    90,    91,    -1,   963,    -1,    -1,   966,
     967,   171,   137,   173,   971,    -1,    -1,   104,    -1,     3,
      -1,    -1,     6,     7,     8,     9,    10,    11,   985,    13,
     987,    -1,   989,    -1,    -1,   160,   993,    -1,    -1,    -1,
      -1,    -1,    -1,  1000,    -1,   132,   133,    -1,  1005,    -1,
      -1,    -1,    -1,    -1,    -1,   180,    -1,   182,   183,   184,
     185,   186,   187,   711,    48,   152,   153,   715,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   724,    -1,    -1,    -1,
      -1,    -1,   730,    14,    15,    16,    17,    -1,    -1,    20,
      -1,    22,    23,    24,    25,    26,   143,    -1,    29,    -1,
      -1,    32,    33,    -1,    -1,    -1,    -1,   154,   155,    -1,
     157,    -1,   159,    -1,   161,    -1,    -1,    -1,    -1,    -1,
      -1,    52,    53,    54,   171,    -1,    -1,    58,    -1,    60,
      -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    -1,    -1,    88,    89,    -1,
      -1,    92,    93,    -1,    95,    96,    97,    -1,    -1,    -1,
      -1,    -1,   103,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   134,   135,   136,   137,   138,   139,   140,
      -1,   142,    -1,    -1,    -1,    -1,   147,    -1,    -1,   150,
      -1,    -1,    -1,   871,   872,    -1,    -1,    -1,    -1,   160,
      -1,   162,   163,   164,   882,    -1,   167,   168,    -1,   170,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,
     181,   182,   183,   184,   185,   186,   187,    14,    15,    16,
      17,    -1,    -1,    20,    -1,    22,    23,    24,    25,    26,
      -1,    -1,    29,    -1,    -1,    32,    33,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    52,    -1,    -1,    -1,    -1,
      -1,    58,    -1,    60,    37,    -1,    -1,    64,    -1,    66,
      67,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    -1,
      -1,    88,    89,    -1,    -1,    92,    93,    -1,    95,    96,
      97,    -1,    99,    -1,    -1,    -1,   103,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,    -1,   134,   135,   136,
     137,   138,   139,   140,    -1,   142,    -1,    -1,    -1,    -1,
     147,   124,   125,   150,    -1,   128,   129,   130,   131,   132,
     133,    -1,    -1,   160,    -1,   162,   163,   164,    -1,    -1,
     167,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,   180,   181,   182,   183,   184,   185,   186,
     187,    14,    15,    16,    17,    -1,    -1,    20,    -1,    22,
      23,    24,    25,    26,    -1,    -1,    29,    -1,    -1,    32,
      33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    52,
      -1,    -1,    -1,    -1,    -1,    58,    -1,    60,    37,    -1,
      -1,    64,    -1,    66,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,
      93,    -1,    95,    96,    97,    -1,    -1,    -1,    -1,    -1,
     103,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
      -1,   134,   135,   136,   137,   138,   139,   140,    -1,   142,
      -1,    -1,    -1,    -1,   147,   124,    -1,   150,    -1,   128,
     129,   130,   131,   132,   133,    -1,    -1,   160,    -1,   162,
     163,   164,    -1,    -1,   167,   168,    -1,   170,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,   180,   181,   182,
     183,   184,   185,   186,   187,    14,    15,    16,    17,    -1,
      -1,    20,    -1,    22,    23,    24,    25,    26,    -1,    -1,
      29,    -1,    -1,    32,    33,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    52,    -1,    -1,    -1,    -1,    -1,    58,
      -1,    60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    -1,    -1,    88,
      89,    -1,    -1,    92,    93,    -1,    95,    96,    97,    -1,
      -1,    -1,    -1,    -1,   103,    -1,    -1,    14,    15,    16,
      -1,    -1,    -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    32,    33,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   134,   135,   136,   137,   138,
     139,   140,    -1,   142,    -1,    -1,    -1,    -1,   147,    -1,
      -1,   150,    15,    -1,    17,    -1,    -1,    20,    -1,    -1,
      -1,   160,    -1,   162,   163,   164,    29,    -1,   167,   168,
      -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   180,   181,   182,   183,   184,   185,   186,   187,    52,
      53,    54,    -1,    -1,    -1,    58,    -1,    60,    -1,    -1,
      -1,    64,    -1,    66,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,
      93,    -1,    95,    96,    97,   142,   143,    -1,    -1,    -1,
     147,    -1,    -1,    14,    15,    16,    -1,   154,   155,    20,
     157,    -1,   159,    -1,   161,    -1,    -1,    -1,    -1,    -1,
      -1,    32,    33,    -1,   171,    -1,   173,    -1,    -1,    -1,
      -1,   134,   135,   136,   137,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   150,    15,    -1,
      17,    62,    63,    20,    -1,    -1,    -1,   160,    -1,    -1,
      -1,    -1,    29,    30,    -1,   168,    19,   170,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,   181,   182,
     183,   184,   185,   186,   187,    52,    -1,    -1,    -1,    -1,
      -1,    58,    -1,    60,    -1,    -1,    -1,    64,    -1,    66,
      67,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    -1,
      -1,    88,    89,    -1,    -1,    92,    93,    -1,    95,    96,
      97,   142,   143,    -1,    -1,    -1,   147,    90,    91,    -1,
      -1,    -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,   104,    -1,    14,    15,    16,    -1,    18,    -1,    20,
     171,    -1,    -1,    -1,    -1,    -1,    -1,   134,   135,   136,
     137,    32,    33,    -1,    -1,   128,   129,   130,   131,   132,
     133,    -1,    -1,   150,    15,    -1,    17,    -1,    -1,    20,
      -1,    -1,    -1,   160,    -1,    -1,    -1,    -1,    29,   152,
     153,   168,    19,   170,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   180,   181,   182,   183,   184,   185,   186,
     187,    52,    -1,    -1,    55,    -1,    -1,    58,    -1,    60,
      -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    -1,    -1,    88,    89,    -1,
      -1,    92,    93,    -1,    95,    96,    97,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,   142,   143,    -1,    -1,    -1,   147,   104,    -1,    -1,
      -1,    -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,    -1,    -1,   134,   135,   136,   137,   124,    -1,    -1,
     171,   128,   129,   130,   131,   132,   133,    -1,    -1,   150,
      15,    -1,    17,    -1,    19,    20,    -1,    -1,    -1,   160,
      -1,    -1,    -1,    -1,    29,   152,   153,   168,    -1,   170,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,
     181,   182,   183,   184,   185,   186,   187,    52,    -1,    -1,
      -1,    -1,    -1,    58,    -1,    60,    -1,    -1,    -1,    64,
      -1,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,    -1,
      95,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    14,    15,    16,    -1,    18,    -1,    20,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      32,    33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   134,
     135,   136,   137,    -1,    -1,    14,    15,    16,    -1,    18,
      -1,    20,    -1,    -1,    -1,   150,    15,    -1,    17,    -1,
      -1,    20,    -1,    32,    33,   160,    -1,    -1,    -1,    -1,
      29,    -1,    -1,   168,    -1,   170,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,
     185,   186,   187,    52,    -1,    -1,    -1,    -1,    -1,    58,
      -1,    60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    -1,    -1,    88,
      89,    -1,    -1,    92,    93,    -1,    95,    96,    97,    12,
     142,   143,    -1,    -1,    17,   147,    19,    -1,    -1,    -1,
      -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,   161,
      -1,    14,    15,    16,    37,    18,    -1,    20,    -1,   171,
      -1,    -1,    -1,   142,   143,   134,   135,   136,   137,    32,
      33,    14,    15,    16,    -1,   154,   155,    20,   157,    -1,
     159,   150,   161,    66,    67,    -1,    -1,    -1,    -1,    32,
      33,   160,   171,    -1,    -1,    -1,    -1,    -1,    -1,   168,
      -1,   170,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,   180,   181,   182,   183,   184,   185,   186,   187,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    -1,    -1,    14,    15,    16,    -1,    -1,    -1,    20,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    32,    33,    -1,    -1,    -1,    -1,    -1,    -1,   142,
     143,    -1,    -1,    -1,   147,    -1,    -1,    -1,    -1,    -1,
     173,   154,   155,    -1,   157,    -1,   159,    -1,   161,   142,
     143,    -1,    -1,    -1,   147,    -1,    -1,    -1,   171,    -1,
      -1,   154,   155,    -1,   157,    -1,   159,    -1,   161,    -1,
      -1,    -1,    -1,    -1,    12,    -1,    -1,    -1,   171,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,   142,   143,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   154,   155,    -1,   157,    19,   159,    -1,
     161,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
     171,    -1,    -1,    -1,    -1,    37,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    90,    91,
      -1,    37,    -1,    -1,    -1,   163,    -1,    -1,   166,    -1,
      -1,    -1,   104,   105,   106,   107,   108,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      66,    67,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
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
     132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      -1,   163,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,   163,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,   152,   153,    31,    32,    -1,    -1,
      -1,    -1,    37,    -1,    -1,   163,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    32,
      -1,    -1,    -1,   158,    37,    -1,    -1,    -1,    -1,    -1,
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
     153,    -1,    -1,    -1,    -1,   158,    37,    -1,    -1,    -1,
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
      -1,   152,   153,    -1,    -1,   156,    -1,    -1,    37,    -1,
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
      27,    -1,    -1,   152,   153,    -1,    -1,   156,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    62,    63,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    -1,    12,    -1,
      -1,    -1,    -1,    17,   141,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    31,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    86,    87,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    18,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    27,    -1,    -1,   152,   153,
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
     132,   133,    -1,    12,    -1,    -1,    -1,    -1,    17,   141,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,
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
      -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    12,    -1,
      -1,    -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,    37,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    12,    -1,
      -1,    -1,    66,    67,    -1,    19,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,    66,    67,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    -1,    -1,    -1,    -1,    12,    90,    91,    -1,    -1,
      -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,   152,   153,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
      37,   115,    -1,    -1,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    19,    -1,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,    -1,    -1,    66,
      67,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,   104,   105,   106,
     107,   108,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153
  };
  }


  static class YyTokenClass {
  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  public static final short
  yystos_[] =
  {
         0,     3,     4,     5,     7,    48,   175,   211,   212,   226,
     231,   234,   236,   248,   252,   176,   177,   178,   179,   237,
     238,   239,   180,   227,   228,   230,   180,   232,   233,   239,
     253,   254,   255,   256,   239,   249,   250,   180,     0,    33,
     180,   146,   149,    33,    12,   233,   180,   257,    33,    17,
      88,    89,    92,   137,   160,   180,   182,   183,   184,   185,
     186,   187,   251,   286,   287,   288,   289,   290,   291,   316,
     319,    33,    12,   238,    12,    34,   180,   230,    17,    19,
      20,    35,    39,    40,    41,    42,    43,   180,   188,   189,
     190,   191,   192,   193,   194,   195,   240,   241,   242,   243,
     244,   291,   228,    15,    17,    20,    29,    52,    58,    60,
      64,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    88,    89,    93,    95,    96,    97,   134,   135,   136,
     150,   168,   170,   180,   181,   285,   286,   287,   288,   289,
     290,   291,   294,   296,   299,   300,    17,    19,   149,   260,
     254,   285,    17,    17,    19,    12,   149,    12,   149,   250,
      14,    15,    16,    17,    32,    33,   142,   172,   180,   213,
     229,   230,   240,   180,   240,   245,   246,   240,    27,    44,
     240,   180,   240,   240,   240,   240,   240,    90,   104,    27,
      31,    37,    44,   285,   284,   285,    30,   284,   285,   301,
     302,    67,   180,   315,   316,   318,   320,   321,   322,   180,
     251,   257,   292,   293,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,    17,    17,   315,   322,
     327,    17,   241,    17,    17,   315,   317,   323,   324,   325,
     326,   323,   320,   284,   285,    17,    98,    12,    17,    19,
      37,    66,    67,    90,    91,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   152,   153,   261,   317,   240,
     242,   180,   263,    18,   317,   317,   285,   240,   285,   240,
     214,   230,   214,   214,   229,   214,   214,   285,     6,     8,
       9,    10,    11,    13,   215,   216,   235,   236,   248,   252,
     269,   281,    17,    19,    14,    15,    16,    20,    32,    33,
     142,   143,   154,   155,   157,   159,   161,   171,   224,    13,
      33,    47,   247,    45,   149,   246,   247,    18,   240,   240,
      21,    36,    38,    38,   180,   180,   240,   240,   240,   240,
      18,    21,   146,    31,    30,    31,    86,    87,    30,   146,
      31,   118,   149,    13,   149,    59,   146,    61,   149,   284,
     285,   284,    13,   146,   285,    17,   284,   285,   118,   146,
     149,    13,   146,    13,    13,   151,    31,   285,   285,   284,
     285,   285,   285,   285,   180,   319,    92,   180,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   315,   321,   180,
     319,    18,   146,   149,   180,   149,    49,   146,   264,   265,
     146,    18,    12,    12,    13,    33,    13,    13,    13,    13,
      13,   145,   180,   218,   239,   270,   271,   272,   273,   180,
     217,   271,    47,   239,   282,   283,    14,    15,    16,    17,
      20,    22,    23,    24,    25,    26,    32,    33,    58,    60,
      64,   103,   138,   139,   140,   142,   147,   162,   163,   164,
     167,   220,   222,   223,   285,   303,   305,   306,   309,   311,
      13,   216,   284,   180,   213,   213,   213,   285,   213,   213,
     285,   225,   285,   285,   213,   213,   285,   285,   285,   213,
     230,   315,   240,   240,   245,   240,   240,   285,   315,   321,
     323,   285,   285,   285,   302,   323,   285,   240,   285,   285,
     293,   285,   297,   298,   317,    18,    18,   285,   322,   146,
     285,    18,   146,   285,   315,   240,   285,   324,   285,   285,
     323,    18,    18,    86,   317,   240,    17,    19,   258,   240,
     285,   180,    50,   267,   315,   285,   285,   213,   230,   213,
     213,   213,    20,   213,   213,   214,    12,    33,   180,   180,
     271,    12,   180,   285,   180,   308,    33,   214,   214,   214,
      99,   180,   220,   229,   285,    55,   276,   277,   314,   285,
     214,   214,   292,   285,   304,   285,    19,   285,   230,   230,
     230,   285,   285,   284,   285,   304,   165,   180,   315,   320,
     285,    14,    15,    16,    20,    32,    33,   142,   147,   154,
     155,   157,   159,   161,   171,   173,   224,    13,    33,    27,
     141,   220,    18,    32,   158,   145,   144,   146,    28,   156,
      46,   173,    13,    21,    13,    30,    30,    13,    30,    62,
      63,   295,   146,   173,    27,   240,    18,   285,    13,   151,
     285,   149,   317,    17,    46,   149,   285,    18,    18,   285,
      13,   285,   180,    12,   149,   260,   219,   220,   229,    12,
     149,   283,    13,    13,    13,   307,   308,    18,    13,    56,
      57,   278,   279,   280,   264,    21,    13,    13,    59,    27,
      61,    31,   173,   149,   145,   148,    13,   173,   315,    12,
      59,    59,   163,   220,   220,   220,   285,   220,   220,   285,
     285,   285,   220,   220,   285,   285,   285,   220,   223,   220,
     220,   173,   285,   213,   213,   285,   285,   213,   285,   285,
     285,   285,   285,   285,    65,   298,   285,    18,    18,   285,
      18,   240,    18,   317,    53,    54,   259,   285,   240,    17,
      21,   213,    12,   285,    19,   240,   274,   262,   263,    13,
     219,   240,   220,   220,    20,    13,   146,   220,   279,   284,
     267,    20,   220,   220,   285,   220,   220,   285,   285,   312,
     313,   317,   214,   214,    17,   118,   285,   285,   285,   220,
      31,    32,   158,    31,   145,    31,   156,    21,    28,    31,
      21,    30,    30,    61,    27,   151,    18,   264,   285,   213,
     285,   102,   102,   180,   276,   220,    59,   100,   285,   220,
     308,    18,   149,   285,    62,    63,   310,    27,   146,   173,
      27,    13,    13,   220,   221,   285,    38,   163,   163,   285,
     285,   220,   285,   220,   285,   220,   213,   285,   323,   285,
     285,   266,   267,    18,    19,   240,    19,   240,   258,   264,
     285,   285,    21,    18,    17,   240,    21,    31,   285,   220,
      62,    63,   220,    65,   313,   317,   220,   285,    20,   220,
      18,   146,   163,   285,   220,   220,    32,    21,    31,   145,
     148,    13,   144,   295,   173,    51,   268,    46,   267,   220,
     284,   220,   285,    61,   285,   220,    27,    27,   285,   220,
     220,   163,   166,   285,   220,   285,   220,   220,   285,   285,
      53,    54,   220,   275,    18,    21,   220,    61,   220,    21,
     220,   285,    31,   145,   144,   264,   220,   220,   173,   220,
     163,   285,   220,   266,   220,    21,   220
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
         0,   210,   211,   211,   211,   211,   211,   212,   212,   213,
     213,   213,   213,   213,   213,   213,   213,   213,   213,   213,
     213,   213,   213,   213,   213,   213,   213,   213,   213,   213,
     213,   213,   213,   213,   213,   213,   213,   214,   214,   215,
     215,   216,   216,   216,   216,   217,   217,   218,   218,   218,
     219,   219,   220,   220,   220,   220,   220,   220,   220,   220,
     220,   220,   220,   220,   220,   220,   220,   220,   220,   220,
     220,   220,   220,   220,   220,   220,   220,   220,   220,   220,
     220,   220,   220,   220,   220,   220,   220,   220,   220,   220,
     220,   220,   221,   221,   222,   222,   223,   223,   223,   224,
     224,   224,   225,   225,   226,   226,   226,   227,   227,   228,
     228,   229,   229,   230,   230,   231,   231,   232,   232,   233,
     234,   234,   234,   235,   235,   235,   235,   235,   235,   236,
     236,   236,   237,   237,   238,   238,   238,   238,   239,   239,
     239,   239,   239,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   241,
     241,   241,   241,   241,   241,   241,   241,   242,   242,   243,
     243,   244,   244,   245,   245,   246,   246,   246,   247,   248,
     248,   248,   249,   249,   250,   251,   251,   251,   251,   252,
     252,   252,   253,   253,   254,   254,   255,   256,   257,   258,
     258,   258,   259,   259,   259,   260,   260,   261,   261,   262,
     262,   263,   263,   264,   264,   265,   266,   266,   267,   268,
     268,   269,   269,   270,   270,   271,   271,   272,   273,   274,
     274,   274,   274,   275,   275,   275,   276,   276,   277,   278,
     278,   279,   279,   280,   280,   281,   281,   281,   282,   282,
     283,   283,   284,   284,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   285,   285,   285,
     285,   285,   285,   285,   285,   285,   285,   285,   286,   286,
     287,   288,   289,   289,   289,   290,   291,   292,   292,   293,
     293,   294,   295,   295,   296,   296,   297,   297,   298,   299,
     299,   299,   299,   299,   299,   299,   299,   299,   299,   299,
     299,   299,   299,   299,   299,   299,   299,   299,   299,   300,
     300,   300,   300,   300,   300,   300,   300,   300,   300,   300,
     300,   300,   300,   300,   300,   300,   300,   300,   300,   300,
     300,   300,   300,   300,   300,   300,   300,   300,   300,   300,
     300,   300,   301,   301,   302,   303,   303,   303,   303,   303,
     303,   303,   303,   303,   303,   303,   303,   303,   304,   304,
     305,   306,   306,   307,   307,   308,   308,   308,   309,   309,
     310,   310,   311,   311,   312,   312,   313,   314,   315,   315,
     316,   316,   316,   316,   317,   317,   318,   318,   319,   319,
     319,   319,   319,   319,   319,   320,   320,   321,   322,   323,
     323,   324,   324,   325,   326,   327,   327
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     0,     1,     1,     1,     1,     4,     6,     4,
       5,     3,     3,     3,     5,     7,     3,     3,     3,     5,
       3,     5,     3,     3,     3,     8,     1,     2,     4,     2,
       4,     4,     4,     6,     7,     4,     4,     1,     3,     1,
       2,     1,     2,     1,     2,     3,     4,     3,     4,     5,
       1,     3,     1,     1,     1,     1,     2,     3,     3,     3,
       3,     3,     3,     5,     3,     5,     3,     3,     3,     2,
       6,     7,     3,     7,     3,    11,     7,     9,     5,     3,
       8,     4,     4,     4,     6,     7,     9,     9,     7,     1,
       1,     1,     1,     3,     1,     3,     2,     2,     2,     3,
       7,     9,     3,     5,     1,     2,     3,     1,     3,     1,
       1,     1,     3,     3,     3,     1,     2,     1,     2,     3,
       1,     1,     1,     1,     1,     1,     1,     1,     2,     1,
       2,     3,     1,     3,     5,     4,     4,     5,     1,     1,
       1,     1,     0,     3,     1,     1,     5,     3,     3,     3,
       2,     2,     2,     4,     4,     1,     1,     3,     3,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     3,
       3,     3,     3,     1,     2,     1,     3,     3,     4,     1,
       2,     3,     1,     3,     2,     5,     5,     3,     3,     1,
       2,     3,     1,     3,     1,     1,     6,     2,    10,     1,
       3,     4,     1,     1,     1,     1,     3,     3,     5,     0,
       1,     3,     5,     1,     0,     2,     1,     0,     2,     2,
       0,     1,     2,     1,     2,     1,     1,    10,     7,     3,
       3,     3,     3,     1,     1,     1,     1,     0,     2,     1,
       2,     2,     4,     1,     1,     1,     2,     3,     1,     3,
       2,     2,     1,     3,     1,     1,     1,     1,     1,     1,
       3,     1,     1,     2,     3,     3,     3,     2,     4,     6,
       3,     3,     3,     3,     3,     4,     1,     1,     1,     1,
       4,     4,     4,     4,     2,     3,     5,     7,     5,     1,
       3,     5,     7,     1,     3,     5,     7,     4,     4,     4,
       4,     5,     6,     4,     6,     3,     5,     7,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     3,     1,
       1,     5,     2,     5,     5,     9,     1,     3,     3,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     1,     3,     3,     1,     3,     3,     3,     2,
       2,     1,     6,     6,     7,     8,    10,     4,     3,     5,
       4,     3,     6,     1,     3,     3,     5,     5,     7,     6,
       4,     5,     5,     9,     1,     3,     3,     3,     1,     1,
       1,     6,     2,     4,     1,     3,     1,     1,     1,     1,
       1,     1,     1,     1,     3,     1,     1,     3,     3,     1,
       3,     1,     1,     3,     3,     1,     3
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
  "U-SEMI", "T-STAR", "U-MINUS", "U-PLUS", "G-HIGH", "$accept",
  "programParagraph", "processDefinition", "process",
  "replicationDeclaration", "processParagraphList", "processParagraph",
  "actionDefinitionList", "namesetDefList", "paragraphAction", "action",
  "actionList", "parametrisationList", "parametrisation",
  "renameExpression", "renameList", "channelDefinition", "channelDef",
  "channelNameDecl", "singleTypeDeclarationList", "singleTypeDeclaration",
  "chansetDefinitionParagraph", "chansetDefinitionList",
  "chansetDefinition", "globalDefinitionParagraph",
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
       211,     0,    -1,    -1,   212,    -1,   226,    -1,   231,    -1,
     234,    -1,   175,   180,    12,   213,    -1,   175,   180,    12,
     229,    13,   213,    -1,   172,    13,   220,   173,    -1,   172,
     215,    13,   220,   173,    -1,   213,    33,   213,    -1,   213,
      15,   213,    -1,   213,    14,   213,    -1,   213,   142,   285,
     145,   213,    -1,   213,    20,   285,    32,   285,    21,   213,
      -1,   213,    32,   213,    -1,   213,    16,   213,    -1,   213,
     155,   213,    -1,   213,   154,   285,   156,   213,    -1,   213,
     157,   213,    -1,   213,    20,   285,   158,   213,    -1,   213,
     171,   285,    -1,   213,   161,   285,    -1,   213,   159,   285,
      -1,    17,   229,    13,   213,    18,    17,   285,    18,    -1,
     180,    -1,   180,    19,    -1,   180,    17,   284,    18,    -1,
     213,   224,    -1,    33,   214,    13,   213,    -1,    15,   214,
      13,   213,    -1,    14,   214,    13,   213,    -1,   142,   285,
     145,   214,    13,   213,    -1,    32,   214,    13,    20,   285,
      21,   213,    -1,    32,   214,    13,   213,    -1,    16,   214,
      13,   213,    -1,   230,    -1,   214,    33,   230,    -1,   216,
      -1,   215,   216,    -1,   235,    -1,     9,   217,    -1,     6,
      -1,     6,   218,    -1,   180,    12,   219,    -1,   217,   180,
      12,   219,    -1,   180,    12,   285,    -1,   218,   180,    12,
     285,    -1,   218,    33,   180,    12,   285,    -1,   220,    -1,
     229,    13,   220,    -1,    22,    -1,    23,    -1,    24,    -1,
      25,    -1,    26,   285,    -1,   285,    27,   220,    -1,   285,
     141,   220,    -1,   220,    33,   220,    -1,   220,    15,   220,
      -1,   220,    14,   220,    -1,   220,   155,   220,    -1,   220,
     154,   285,   156,   220,    -1,   220,   157,   220,    -1,   220,
      20,   285,   158,   220,    -1,   220,   171,   285,    -1,   220,
     161,   285,    -1,   220,   159,   285,    -1,   220,   224,    -1,
     162,   284,    13,    17,   221,    18,    -1,   220,   147,   285,
      31,   285,   148,   220,    -1,   220,    16,   220,    -1,   220,
     142,   285,    31,   285,   145,   220,    -1,   220,    32,   220,
      -1,   220,    20,   285,    31,   285,    32,   285,    31,   285,
      21,   220,    -1,   220,    20,   285,    32,   285,    21,   220,
      -1,   220,   142,   285,    31,   285,    31,   285,   145,   220,
      -1,   220,   142,   285,   145,   220,    -1,   222,    13,   220,
      -1,    17,   229,    13,   220,    18,    17,   284,    18,    -1,
      33,   214,    13,   220,    -1,    15,   214,    13,   220,    -1,
      14,   214,    13,   220,    -1,   147,   285,   148,   214,    13,
     220,    -1,    16,   214,    13,    20,   285,    21,   220,    -1,
     142,   285,   145,   214,    13,    20,   285,    21,   220,    -1,
      32,   214,    13,    20,   285,    31,   285,    21,   220,    -1,
      32,   214,    13,    20,   285,    21,   220,    -1,   305,    -1,
     306,    -1,   303,    -1,   220,    -1,   221,   146,   220,    -1,
     223,    -1,   222,    33,   223,    -1,   140,   230,    -1,   139,
     230,    -1,   138,   230,    -1,   143,   225,   144,    -1,   143,
     285,    28,   285,    31,   323,   144,    -1,   143,   285,    28,
     285,    31,   323,    13,   285,   144,    -1,   285,    28,   285,
      -1,   225,   146,   285,    28,   285,    -1,     4,    -1,     4,
     227,    -1,     4,   227,    33,    -1,   228,    -1,   227,    33,
     228,    -1,   180,    -1,   230,    -1,   230,    -1,   229,    33,
     230,    -1,   180,   149,   240,    -1,   180,   146,   230,    -1,
       5,    -1,     5,   232,    -1,   233,    -1,   232,   233,    -1,
     180,    12,   285,    -1,   236,    -1,   248,    -1,   252,    -1,
     236,    -1,   248,    -1,   252,    -1,   269,    -1,   281,    -1,
      10,   271,    -1,     3,    -1,     3,   237,    -1,     3,   237,
      33,    -1,   238,    -1,   237,    33,   238,    -1,   239,   180,
      12,   240,   247,    -1,   239,   180,    12,   240,    -1,   239,
     180,    34,   245,    -1,   239,   180,    34,   245,   247,    -1,
     176,    -1,   177,    -1,   178,    -1,   179,    -1,    -1,    17,
     240,    18,    -1,   241,    -1,   291,    -1,    35,   180,    36,
     245,   173,    -1,   240,    31,   240,    -1,   240,    37,   240,
      -1,    20,   240,    21,    -1,    43,   240,    -1,    41,   240,
      -1,    42,   240,    -1,    40,   240,    38,   240,    -1,    39,
     240,    38,   240,    -1,   242,    -1,   180,    -1,   180,    90,
     180,    -1,   180,   104,   180,    -1,   188,    -1,   189,    -1,
     190,    -1,   191,    -1,   192,    -1,   193,    -1,   194,    -1,
     195,    -1,   243,    -1,   244,    -1,   240,    44,   240,    -1,
      19,    44,   240,    -1,   240,    27,   240,    -1,    19,    27,
     240,    -1,   246,    -1,   245,   246,    -1,   240,    -1,   180,
     149,   240,    -1,   180,    45,   240,    -1,    47,   315,    46,
     285,    -1,    48,    -1,    48,   249,    -1,    48,   249,    33,
      -1,   250,    -1,   249,    33,   250,    -1,   239,   251,    -1,
     180,   149,   240,    12,   285,    -1,   316,   149,   240,    12,
     285,    -1,   180,    12,   285,    -1,   316,    12,   285,    -1,
       7,    -1,     7,   253,    -1,     7,   253,    33,    -1,   254,
      -1,   253,    33,   254,    -1,   255,    -1,   256,    -1,   239,
     180,   260,   263,   264,   267,    -1,   239,   257,    -1,   180,
     149,   242,   180,   258,    46,   259,   264,   266,   268,    -1,
      19,    -1,    17,   317,    18,    -1,   258,    17,   317,    18,
      -1,   285,    -1,    53,    -1,    54,    -1,    19,    -1,    17,
     261,    18,    -1,   317,   149,   240,    -1,   261,   146,   317,
     149,   240,    -1,    -1,   263,    -1,   180,   149,   240,    -1,
     263,   146,   180,   149,   240,    -1,   265,    -1,    -1,    49,
     285,    -1,   267,    -1,    -1,    50,   285,    -1,    51,   285,
      -1,    -1,     8,    -1,     8,   270,    -1,   271,    -1,   270,
     271,    -1,   273,    -1,   272,    -1,   239,   180,   149,   274,
     180,   258,    46,   275,   264,   266,    -1,   239,   180,   260,
     262,   276,   264,   267,    -1,   240,   102,   240,    -1,    19,
     102,   240,    -1,   240,   102,    19,    -1,    19,   102,    19,
      -1,   220,    -1,    53,    -1,    54,    -1,   277,    -1,    -1,
      55,   278,    -1,   279,    -1,   278,   279,    -1,   280,   284,
      -1,   280,   284,   149,   240,    -1,    56,    -1,    57,    -1,
      11,    -1,    11,   282,    -1,    11,   282,    33,    -1,   283,
      -1,   282,    33,   283,    -1,   239,   308,    -1,    47,   285,
      -1,   285,    -1,   284,   146,   285,    -1,   289,    -1,   286,
      -1,   288,    -1,   287,    -1,   290,    -1,   291,    -1,    17,
     285,    18,    -1,   181,    -1,   180,    -1,   180,    98,    -1,
     285,    90,   180,    -1,   285,   104,   180,    -1,   285,    91,
      92,    -1,   285,    19,    -1,   285,    17,   284,    18,    -1,
     285,    17,   285,    86,   285,    18,    -1,   285,    90,   319,
      -1,   285,   153,   180,    -1,   285,   153,   319,    -1,   285,
     152,   315,    -1,   285,   152,   321,    -1,    58,   292,    59,
     285,    -1,   294,    -1,   296,    -1,   299,    -1,   300,    -1,
     134,   323,    13,   285,    -1,   135,   323,    13,   285,    -1,
     136,   320,    13,   285,    -1,    52,   320,    13,   285,    -1,
      29,    30,    -1,    29,   284,    30,    -1,    29,   285,    31,
     323,    30,    -1,    29,   285,    31,   323,    13,   285,    30,
      -1,    29,   285,    86,   285,    30,    -1,    15,    -1,    20,
     284,    21,    -1,    20,   285,    31,   321,    21,    -1,    20,
     285,    31,   321,    13,   285,    21,    -1,   170,    -1,    29,
     301,    30,    -1,    29,   302,    31,   323,    30,    -1,    29,
     302,    31,   323,    13,   285,    30,    -1,    88,    17,   284,
      18,    -1,    89,    17,   284,    18,    -1,    93,   327,    13,
     285,    -1,   168,    17,   285,    18,    -1,    95,   241,    17,
     285,    18,    -1,    95,    17,   285,   146,   240,    18,    -1,
      96,    17,   284,    18,    -1,    97,    17,   285,   146,   285,
      18,    -1,   150,   284,   151,    -1,   150,   285,    31,   323,
     151,    -1,   150,   285,    31,   323,    13,   285,   151,    -1,
     183,    -1,   182,    -1,   184,    -1,   185,    -1,    92,    -1,
     187,    -1,   160,    -1,   137,    -1,   186,    -1,   293,    -1,
     292,   146,   293,    -1,   251,    -1,   257,    -1,    60,   285,
      61,   285,   295,    -1,    63,   285,    -1,    62,   285,    61,
     285,   295,    -1,    64,   285,   149,   297,   173,    -1,    64,
     285,   149,   297,   146,    65,    27,   285,   173,    -1,   298,
      -1,   297,   146,   298,    -1,   317,    27,   285,    -1,    66,
     285,    -1,    67,   285,    -1,    68,   285,    -1,    69,   285,
      -1,    70,   285,    -1,    71,   285,    -1,    72,   285,    -1,
      73,   285,    -1,    74,   285,    -1,    75,   285,    -1,    76,
     285,    -1,    77,   285,    -1,    78,   285,    -1,    79,   285,
      -1,    80,   285,    -1,    81,   285,    -1,    82,   285,    -1,
      83,   285,    -1,    84,   285,    -1,    85,   285,    -1,   285,
      66,   285,    -1,   285,    37,   285,    -1,   285,    67,   285,
      -1,   285,   106,   285,    -1,   285,   105,   285,    -1,   285,
     107,   285,    -1,   285,   108,   285,    -1,   285,   109,   285,
      -1,   285,   110,   285,    -1,   285,   111,   285,    -1,   285,
     112,   285,    -1,   285,    12,   285,    -1,   285,   113,   285,
      -1,   285,   114,   285,    -1,   285,   115,   285,    -1,   285,
     116,   285,    -1,   285,   117,   285,    -1,   285,   118,   285,
      -1,   285,   119,   285,    -1,   285,   120,   285,    -1,   285,
     121,   285,    -1,   285,   122,   285,    -1,   285,   123,   285,
      -1,   285,   124,   285,    -1,   285,   125,   285,    -1,   285,
     126,   285,    -1,   285,   127,   285,    -1,   285,   128,   285,
      -1,   285,   129,   285,    -1,   285,   130,   285,    -1,   285,
     131,   285,    -1,   285,   132,   285,    -1,   285,   133,   285,
      -1,   302,    -1,   301,   146,   302,    -1,   285,    87,   285,
      -1,   309,    -1,    60,   304,   173,    -1,   163,   304,   173,
      -1,    20,   314,    21,    -1,   103,    19,    -1,   103,   285,
      -1,   311,    -1,   164,   320,    59,   285,   163,   220,    -1,
     164,   315,    59,   285,   163,   220,    -1,   164,   165,   315,
     118,   285,   163,   220,    -1,   164,   180,    12,   285,    38,
     285,   163,   220,    -1,   164,   180,    12,   285,    38,   285,
     166,   285,   163,   220,    -1,   167,   285,   163,   220,    -1,
     285,    27,   220,    -1,   304,    31,   285,    27,   220,    -1,
      58,   292,    59,   220,    -1,    17,   220,    18,    -1,    17,
      99,   307,    13,   220,    18,    -1,   308,    -1,   307,   146,
     308,    -1,   180,   149,   240,    -1,   180,   149,   240,   100,
     285,    -1,   180,   149,   240,    59,   285,    -1,    60,   285,
      61,   220,   310,    63,   220,    -1,    60,   285,    61,   220,
      63,   220,    -1,    62,   285,    61,   220,    -1,   310,    62,
     285,    61,   220,    -1,    64,   285,   149,   312,   173,    -1,
      64,   285,   149,   312,   146,    65,    27,   220,   173,    -1,
     313,    -1,   312,   146,   313,    -1,   317,    27,   220,    -1,
     276,   264,   267,    -1,   318,    -1,   316,    -1,   319,    -1,
      88,    17,   317,   146,   315,    18,    -1,    89,    19,    -1,
      89,    17,   317,    18,    -1,   315,    -1,   317,   146,   315,
      -1,   180,    -1,    67,    -1,   289,    -1,   286,    -1,   288,
      -1,   287,    -1,   290,    -1,   291,    -1,    17,   285,    18,
      -1,   321,    -1,   322,    -1,   315,   118,   285,    -1,   315,
     149,   240,    -1,   324,    -1,   323,   146,   324,    -1,   325,
      -1,   326,    -1,   317,   118,   285,    -1,   317,   149,   240,
      -1,   322,    -1,   327,   146,   322,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     4,     6,     8,    10,    12,    17,    24,
      29,    35,    39,    43,    47,    53,    61,    65,    69,    73,
      79,    83,    89,    93,    97,   101,   110,   112,   115,   120,
     123,   128,   133,   138,   145,   153,   158,   163,   165,   169,
     171,   174,   176,   179,   181,   184,   188,   193,   197,   202,
     208,   210,   214,   216,   218,   220,   222,   225,   229,   233,
     237,   241,   245,   249,   255,   259,   265,   269,   273,   277,
     280,   287,   295,   299,   307,   311,   323,   331,   341,   347,
     351,   360,   365,   370,   375,   382,   390,   400,   410,   418,
     420,   422,   424,   426,   430,   432,   436,   439,   442,   445,
     449,   457,   467,   471,   477,   479,   482,   486,   488,   492,
     494,   496,   498,   502,   506,   510,   512,   515,   517,   520,
     524,   526,   528,   530,   532,   534,   536,   538,   540,   543,
     545,   548,   552,   554,   558,   564,   569,   574,   580,   582,
     584,   586,   588,   589,   593,   595,   597,   603,   607,   611,
     615,   618,   621,   624,   629,   634,   636,   638,   642,   646,
     648,   650,   652,   654,   656,   658,   660,   662,   664,   666,
     670,   674,   678,   682,   684,   687,   689,   693,   697,   702,
     704,   707,   711,   713,   717,   720,   726,   732,   736,   740,
     742,   745,   749,   751,   755,   757,   759,   766,   769,   780,
     782,   786,   791,   793,   795,   797,   799,   803,   807,   813,
     814,   816,   820,   826,   828,   829,   832,   834,   835,   838,
     841,   842,   844,   847,   849,   852,   854,   856,   867,   875,
     879,   883,   887,   891,   893,   895,   897,   899,   900,   903,
     905,   908,   911,   916,   918,   920,   922,   925,   929,   931,
     935,   938,   941,   943,   947,   949,   951,   953,   955,   957,
     959,   963,   965,   967,   970,   974,   978,   982,   985,   990,
     997,  1001,  1005,  1009,  1013,  1017,  1022,  1024,  1026,  1028,
    1030,  1035,  1040,  1045,  1050,  1053,  1057,  1063,  1071,  1077,
    1079,  1083,  1089,  1097,  1099,  1103,  1109,  1117,  1122,  1127,
    1132,  1137,  1143,  1150,  1155,  1162,  1166,  1172,  1180,  1182,
    1184,  1186,  1188,  1190,  1192,  1194,  1196,  1198,  1200,  1204,
    1206,  1208,  1214,  1217,  1223,  1229,  1239,  1241,  1245,  1249,
    1252,  1255,  1258,  1261,  1264,  1267,  1270,  1273,  1276,  1279,
    1282,  1285,  1288,  1291,  1294,  1297,  1300,  1303,  1306,  1309,
    1313,  1317,  1321,  1325,  1329,  1333,  1337,  1341,  1345,  1349,
    1353,  1357,  1361,  1365,  1369,  1373,  1377,  1381,  1385,  1389,
    1393,  1397,  1401,  1405,  1409,  1413,  1417,  1421,  1425,  1429,
    1433,  1437,  1441,  1443,  1447,  1451,  1453,  1457,  1461,  1465,
    1468,  1471,  1473,  1480,  1487,  1495,  1504,  1515,  1520,  1524,
    1530,  1535,  1539,  1546,  1548,  1552,  1556,  1562,  1568,  1576,
    1583,  1588,  1594,  1600,  1610,  1612,  1616,  1620,  1624,  1626,
    1628,  1630,  1637,  1640,  1645,  1647,  1651,  1653,  1655,  1657,
    1659,  1661,  1663,  1665,  1667,  1671,  1673,  1675,  1679,  1683,
    1685,  1689,  1691,  1693,  1697,  1701,  1703
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   153,   153,   155,   156,   157,   158,   167,   168,   173,
     174,   176,   177,   178,   182,   186,   187,   188,   189,   198,
     199,   208,   220,   221,   222,   226,   235,   236,   237,   238,
     240,   241,   242,   246,   250,   251,   252,   258,   260,   278,
     279,   288,   289,   290,   291,   295,   296,   310,   311,   312,
     316,   317,   321,   322,   323,   324,   325,   338,   340,   341,
     342,   343,   344,   353,   354,   363,   373,   374,   375,   376,
     383,   388,   389,   393,   394,   399,   403,   408,   412,   415,
     417,   421,   422,   423,   427,   431,   436,   441,   445,   447,
     448,   449,   454,   455,   459,   460,   464,   465,   466,   471,
     473,   474,   488,   489,   499,   500,   501,   505,   506,   518,
     519,   526,   527,   531,   532,   536,   537,   541,   542,   549,
     553,   554,   555,   564,   565,   566,   567,   568,   575,   579,
     580,   581,   585,   586,   590,   591,   592,   593,   603,   604,
     605,   613,   614,   618,   619,   620,   621,   622,   623,   624,
     625,   626,   627,   628,   629,   630,   639,   640,   641,   645,
     646,   647,   648,   649,   650,   651,   652,   656,   657,   661,
     662,   666,   667,   671,   672,   676,   677,   678,   682,   693,
     694,   695,   699,   700,   704,   708,   709,   710,   711,   715,
     716,   717,   721,   722,   726,   727,   731,   735,   739,   744,
     745,   746,   750,   751,   752,   756,   757,   761,   762,   765,
     767,   771,   772,   776,   777,   781,   785,   786,   790,   799,
     800,   804,   805,   812,   813,   817,   818,   822,   826,   830,
     831,   832,   833,   837,   838,   839,   843,   844,   848,   852,
     853,   857,   858,   862,   863,   885,   886,   887,   891,   892,
     896,   897,   901,   902,   907,   908,   909,   910,   911,   912,
     914,   915,   916,   917,   918,   919,   920,   921,   922,   923,
     924,   926,   927,   928,   929,   931,   932,   933,   934,   935,
     937,   938,   939,   940,   942,   943,   945,   946,   948,   950,
     961,   963,   964,   966,   967,   969,   970,   972,   974,   976,
     985,   986,   987,   992,  1000,  1002,  1003,  1004,  1010,  1011,
    1015,  1019,  1023,  1024,  1025,  1029,  1033,  1039,  1040,  1044,
    1045,  1049,  1053,  1054,  1058,  1059,  1063,  1064,  1068,  1072,
    1073,  1074,  1075,  1076,  1077,  1078,  1079,  1080,  1081,  1082,
    1083,  1084,  1085,  1086,  1087,  1088,  1089,  1090,  1091,  1095,
    1096,  1097,  1098,  1099,  1100,  1101,  1102,  1103,  1104,  1105,
    1106,  1107,  1108,  1109,  1110,  1111,  1112,  1113,  1114,  1115,
    1116,  1117,  1118,  1119,  1120,  1121,  1122,  1123,  1124,  1125,
    1126,  1127,  1131,  1132,  1136,  1140,  1142,  1143,  1169,  1175,
    1176,  1188,  1196,  1198,  1202,  1204,  1205,  1208,  1226,  1227,
    1231,  1235,  1236,  1240,  1241,  1245,  1246,  1252,  1272,  1273,
    1277,  1278,  1282,  1283,  1287,  1288,  1292,  1296,  1300,  1301,
    1305,  1307,  1317,  1318,  1322,  1323,  1327,  1329,  1334,  1335,
    1336,  1337,  1338,  1339,  1340,  1344,  1345,  1349,  1353,  1357,
    1358,  1362,  1363,  1367,  1371,  1375,  1376
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

  private static final int yylast_ = 12309;
  private static final int yynnts_ = 118;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 38;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 210;

  private static final int yyuser_token_number_max_ = 464;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */

}


