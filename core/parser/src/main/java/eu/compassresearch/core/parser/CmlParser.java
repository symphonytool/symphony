
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
  public static final short yypact_ninf_ = -275;
  public static final short yypact_[] =
  {
      2064,  1970,  1970,  1970,  1436,  1562,  -275,  -275,  -275,  -275,
    1970,  1970,  1970,    13,  1970,  1970,  1698,  1970,  1970,  1970,
    1970,  1970,  1970,  1970,   362,  1970,  -275,    66,   249,    59,
    -275,  -275,  -275,  -275,  -275,  -275,  -275,  1970,  1970,  1834,
     637,    13,  1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,
    1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,
    1970,  1970,  1970,  1970,     0,   115,  -275,   637,    -3,   155,
     163,   637,   637,   637,  -275,  1970,  -275,   192,  -275,   182,
    -275,  -275,  -275,  -275,  -275,  -275,  -275,    98,  -275,  -120,
    8734,  -275,  -275,  -275,  -275,  -275,  -275,  -275,  -275,  -275,
    -275,   105,   106,   116,  1529,   121,   253,  -275,  5074,   297,
    8734,   111,   161,  1970,   313,   152,    15,  -275,  -275,  -275,
    -275,  -275,  -275,  -275,  -275,    20,  -275,    16,  -275,  4708,
     -25,  3366,  -275,  8734,  -275,  -275,  -275,  4342,  3610,    22,
    5196,   -24,  -275,   637,   331,    18,  -275,  -275,   288,  -275,
    -275,  2390,  -275,  2064,  2064,  2064,  1970,  2064,  2064,  1970,
    1970,  1970,  2064,  2064,  1970,  1970,  1970,  2064,   300,   637,
     459,  2064,   197,  5318,    38,  5440,  -275,    35,  4586,    85,
     323,  -275,     3,   342,    24,  5562,  3488,   601,   601,   601,
     601,  9185,   601,   601,   601,   601,   601,   601,   601,   601,
     601,   601,   601,   601,   601,   601,   377,  1970,  1970,   211,
    -275,    58,  1970,  -275,  -275,  -275,  -275,  -275,  -275,  -275,
    -275,   347,  1970,  1970,  -275,   -12,    84,  -275,  -275,  -275,
      97,   359,   -16,  5684,  1970,  -275,  2064,  1970,  1970,  1031,
    1970,  1970,  -275,  1970,  1970,  1970,   558,   286,   204,  1970,
    1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,
    1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,
    1970,  1970,  1970,  1970,  1970,  1970,  1970,  1970,  2064,   368,
     244,   129,  -275,  -275,  -275,  -275,   121,  -275,  1970,  1970,
     345,  -275,   259,  -275,   385,  2064,  5806,   637,   637,  -275,
    1970,  1031,  2064,    13,  1970,  1031,  2064,  2064,  1970,  -275,
     637,  1970,  1970,   389,  -275,   293,  1970,  1970,  1970,  1031,
    1970,  2064,   599,   -14,  1806,  3000,   525,   238,  4098,  5928,
    3122,    55,    63,  8734,  8734,  8734,  -275,  -275,  -275,   298,
    -275,  -275,  -275,  1795,  -275,  -275,  -275,   637,  -275,   637,
    1970,  1970,  -275,  1970,   637,  1970,  1970,  1970,   637,    57,
      69,  1970,   637,  3854,  1970,    71,  3976,  1970,   637,  1031,
    1970,   637,  1970,  1970,  -275,   637,  6050,  -275,  -275,  8734,
    1031,   287,  1031,   228,  1031,  1031,  1031,  1031,  1031,    72,
     467,  -275,  -275,  -275,  -275,  -275,  9234,    73,  6172,   290,
    2313,  2313,  -275,  -275,  -275,  -275,   290,   290,   290,   290,
    9234,  9234,  9234,  9234,  9234,  9058,  9108,  8978,  8856,  9234,
    9234,  9234,  9234,  1480,  1480,   377,  2435,  2090,  2090,   377,
     377,   601,   601,   258,   270,  -275,  1970,  1031,  2064,   116,
    -275,   -20,  8734,  1970,  -275,  2064,  1970,  -275,  -275,   269,
      86,  8734,   279,   245,  1795,  -275,  8734,   415,  1795,  1331,
    6294,   -79,  -275,   -19,   274,   307,  2064,  1970,  6416,  2512,
    8734,   467,  2634,  1795,  1970,  1970,  2064,  1970,  2064,  1970,
    2064,   298,   107,    60,  6538,  8734,  6660,  -275,    65,  8856,
    8734,  4830,   -61,  -275,    37,  -275,  -275,  8856,  -275,  1031,
    6782,  -275,  1970,  8734,  -275,   467,  8856,  -275,  8856,  8856,
      12,  -275,   261,  1031,  1031,   666,   381,   728,   744,   387,
     387,   387,   250,   260,  1031,  1031,  1031,  1031,  -275,  1970,
    6904,   581,  1786,  -275,  1031,  8734,   238,  4952,   637,  -275,
    1970,   370,  1970,  1970,  2064,     6,  2064,  1091,  -275,  2064,
     419,  2064,  1795,    95,  2756,  1970,  2064,  2064,  7026,  7148,
      63,  4220,   525,  3732,    55,  1970,  -275,  1970,  -275,  -275,
    1970,  -275,  1970,  1970,  -275,  2006,  -275,  1970,   326,  -275,
    7270,  1970,  -275,  -275,   467,   467,  -275,  1104,  1031,  1031,
    -275,  -275,   467,   387,   387,   467,  7392,  2064,  1970,  1970,
    -275,   467,  2064,  1970,   425,  8734,   637,  -275,    76,  8734,
    7514,  1795,  1970,  2064,  1795,   417,  -275,  1795,  1970,  -275,
    -275,  2064,  2064,  2268,  1795,  1795,  1970,  2064,  1970,  2064,
    2064,  7636,  7758,  7880,  8002,  8856,   418,  -275,  8734,  -275,
    -275,  3244,   237,   467,   393,  -275,   387,   387,  -275,  -275,
    8734,  8734,  -275,  8124,  -275,    96,   637,  1345,  2064,  8246,
    1795,  2064,  8368,  1795,  1795,  2064,  1970,  8490,   525,  4464,
     525,  -275,  -275,  -275,  -275,  1970,  1970,  -275,  1031,  1031,
    -275,  -275,  2064,  -275,   128,  -275,  -275,   253,  8734,  1795,
    2064,  1181,  2064,  1795,  2878,  1970,  2064,  4830,  2146,   467,
     467,  -275,  -275,   345,  1795,  -275,  -275,  2064,  8612,   525,
    -275,  -275,   396,  -275,  1795,  2064,  1970,  -275,   525,  8734
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,     0,     0,     0,     0,   112,     4,     5,     6,     7,
       0,     0,     0,     0,     0,     0,   253,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    44,     0,     0,     0,
      51,    41,    39,    40,   249,   256,   153,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   176,     0,     0,     0,
       0,     0,     0,     0,   179,     0,   178,     0,   157,   129,
     128,   173,   172,   174,   175,   180,   177,     0,     2,     0,
     120,   123,   125,   124,   122,   126,   127,   140,   141,   142,
     143,     0,     0,     0,     0,     0,   104,   111,     0,     0,
       8,     0,     0,     0,     0,     0,     0,   183,   184,   294,
     296,   295,   293,   297,   298,     0,   181,     0,   285,     0,
       0,     0,   254,   255,    55,    54,    53,     0,     0,     0,
       0,     0,   292,     0,   291,     0,   284,   283,     0,   300,
     301,     0,     1,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   120,   148,     0,   120,     0,
     246,   291,     0,     0,     0,     0,     0,   193,   194,   195,
     196,   197,   198,   199,   200,   201,   202,   203,   204,   205,
     206,   207,   208,   209,   210,   211,   212,     0,     0,     0,
     310,     0,     0,    73,    74,    75,    76,    77,    78,    79,
      80,     0,     0,     0,   289,     0,     0,   304,   306,   307,
       0,     0,     0,   120,     0,   130,     0,     0,     0,     0,
       0,     0,   134,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   268,   266,   118,   119,   113,   114,     0,     0,
       0,   103,     0,   252,     0,     0,     0,     0,     0,   287,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   250,
       0,     0,     0,     0,   251,     0,     0,     0,     0,     0,
       0,     0,    13,    12,    23,     0,    25,    11,     0,     0,
       0,    14,    16,    20,    19,    18,     9,    45,    46,    49,
      50,    47,    48,    30,    52,   138,   154,     0,   149,     0,
       0,     0,   158,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   169,     0,     0,    33,     3,   121,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    70,
      56,    58,    69,    81,    82,    59,   224,     0,   120,   214,
     213,   215,   131,   137,   133,   132,   217,   216,   218,   219,
     220,   221,   222,   223,   225,   226,   227,   228,   229,   230,
     231,   232,   233,   234,   235,   236,   237,   238,   239,   240,
     241,   242,   243,   244,   245,    32,     0,     0,     0,     0,
     115,   116,   105,     0,   282,     0,     0,    31,   299,     0,
       0,    94,     0,    69,   265,   182,    95,     0,   263,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
     302,   303,     0,   262,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   248,     0,   247,     0,   147,
     139,     0,     0,   190,     0,   161,   162,   163,   311,     0,
       0,   167,     0,   308,   290,   309,   144,   305,   145,   146,
       0,   164,     0,     0,     0,     0,     0,     0,     0,    65,
      66,    64,     0,     0,     0,     0,     0,     0,   135,     0,
       0,   270,     0,   269,     0,   108,    10,     0,     0,   288,
       0,     0,     0,     0,     0,     0,     0,     0,   277,     0,
       0,     0,    42,     0,     0,     0,     0,     0,     0,     0,
      17,     0,    29,     0,    15,     0,   155,     0,   150,   152,
       0,   159,     0,     0,   185,     0,   188,     0,     0,   165,
       0,     0,   170,    57,    86,    84,    63,     0,     0,     0,
      71,    72,    85,    61,    62,    83,     0,     0,     0,     0,
     267,   117,     0,     0,   290,    92,     0,    97,     0,    93,
       0,   274,     0,     0,   264,     0,   280,   281,     0,    34,
      21,     0,     0,     0,   258,   257,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   186,     0,   191,   192,   166,
     168,     0,    70,    89,     0,    87,    68,    67,   136,    35,
     272,   271,    38,     0,   286,     0,     0,     0,     0,     0,
     273,     0,     0,    43,   259,     0,     0,     0,    27,     0,
      24,    22,   156,   151,   160,     0,     0,   171,     0,     0,
      60,    88,     0,    98,     0,   101,   102,   104,   100,   275,
       0,     0,     0,   260,     0,     0,     0,     0,     0,    91,
      90,    37,    99,   107,   276,   278,    36,     0,     0,    28,
     187,   189,   110,   106,   261,     0,     0,    96,    26,   109
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -275,    10,   171,  -275,  -275,  -275,   276,    44,   -11,   388,
     154,  -275,  -275,  -275,  -191,  -275,  -275,  -275,  -275,  -230,
    -275,  -275,  -245,  -275,  -275,  -275,  -275,   175,  -275,    78,
      -5,   593,   635,   801,   959,  1021,   530,   422,   162,  -275,
    -233,  -275,  -275,  -104,  -275,  -275,  -275,   122,  -275,   456,
    -275,  -275,  -275,    50,  -275,  -275,  -275,  -275,   -57,  -275,
      36,   -10,  -274,  -275,   -45,    34,  -165,   -65,   -71,   120,
    -275,  -275,  -275
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,    87,    27,   553,    28,    29,    30,    88,   643,   391,
     392,   393,   394,   644,   645,   117,   118,   608,   687,   290,
     291,   712,   444,   717,   106,   107,   286,   287,   288,    89,
      90,    91,    92,    93,    94,    95,    96,   125,   126,    97,
     574,    98,   492,   493,    99,   100,   179,   180,    31,   130,
      32,    33,   281,   282,    34,   545,    35,   461,   462,   109,
     224,   146,   225,   147,   128,   148,   149,   150,   226,   227,
     228,   229,   211
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -1;
  public static final short
  yytable_[] =
  {
       108,   230,   210,   127,   340,   110,   308,   308,   549,   129,
     131,   133,   101,   102,   212,   137,   138,   207,   140,   158,
     151,   111,   112,   449,   450,   581,   238,   300,   304,   239,
     113,   127,   173,   175,   178,   313,   463,   185,   186,   187,
     188,   189,   190,   191,   192,   193,   194,   195,   196,   197,
     198,   199,   200,   201,   202,   203,   204,   205,   206,   346,
     145,   134,   135,   136,   577,   348,   152,   547,   612,   613,
     233,   361,   171,   567,   183,   495,   182,   317,   570,   302,
     153,   154,   155,   356,   494,   575,   156,   496,   158,   501,
     568,   528,   172,   656,   548,   571,   158,   370,   157,   158,
     139,   114,   115,   209,   539,    66,   367,   231,   296,   182,
     372,   236,   576,   620,   683,   352,   174,   177,   278,   279,
     565,   318,   657,   338,   294,   342,   238,   368,   566,   534,
     238,   237,   208,   160,   368,   374,   318,   369,   237,   237,
     161,   162,   438,   163,   237,   164,   702,   165,   309,   314,
      74,   325,   319,   232,   328,   329,   330,   166,   371,   333,
     334,   335,   522,   582,   301,   305,   303,   319,   238,   298,
     303,   299,   222,    76,   295,   104,   523,   284,   285,   315,
     223,   238,   482,   368,   238,   213,   214,   215,   216,   217,
     218,   219,   220,   116,   237,    81,    82,    83,    84,    85,
      86,   403,   160,   238,   362,   339,   371,   363,   159,   234,
     160,   371,   163,   160,   164,   238,   165,   238,   366,   238,
     161,   162,   164,   163,   165,   164,   166,   165,   390,   376,
     371,   353,   368,   379,   166,   396,   398,   166,   399,   400,
     401,   621,   368,   371,   406,   407,   408,   409,   410,   411,
     412,   413,   414,   415,   416,   417,   418,   419,   420,   421,
     422,   423,   424,   425,   426,   427,   428,   429,   430,   431,
     432,   433,   434,   463,   368,   439,   167,   242,   483,   583,
     235,   378,   678,   488,   442,   359,   360,   550,   524,   242,
     452,   540,   525,   127,   457,   451,   280,   498,   526,   456,
     365,   494,   289,   460,   510,   527,   524,   237,   471,   242,
     525,   468,   469,   470,   513,   472,   526,   113,   293,   397,
     551,   464,   465,   527,   322,   323,   324,   522,   326,   327,
     297,   514,   655,   331,   332,    17,    18,    19,   336,   168,
     237,   523,   343,   316,   639,   484,   485,   320,   486,   247,
     489,   490,   491,   524,   354,   355,   497,   525,   505,   500,
     319,   247,   503,   526,   364,   506,   441,   508,   509,   512,
     527,   515,   373,   517,   518,   519,   520,   521,   404,   113,
     246,   247,   684,   481,   405,   160,   679,   606,   436,   607,
     276,   277,    66,   437,   248,   443,   242,   164,   209,   165,
     445,   169,   170,   277,   504,   446,   466,   377,   516,   166,
     380,   467,   381,   382,   268,   538,   318,   587,   272,   273,
     274,   275,   276,   277,   526,   541,   531,   542,   383,   142,
     590,   530,   384,   385,   386,   387,   388,    74,   535,   618,
     591,   537,   524,   654,   661,   676,   525,   716,   344,   435,
     114,   115,   526,   681,    66,   453,   221,   703,   713,   527,
      76,   440,   554,   184,   710,   455,   447,   246,   247,   558,
     559,   637,   561,   454,   563,   487,   113,   458,   459,   141,
     337,   248,    81,    82,    83,    84,    85,    86,   578,   533,
     616,   507,   473,     0,   524,     0,     0,   580,   525,    74,
       0,     0,   584,   585,   526,   272,   273,   274,   275,   276,
     277,   527,     0,   592,   593,   594,   595,     0,     0,     0,
       0,     0,    76,   601,   596,     0,     0,   143,     0,     0,
       0,     0,     0,     0,     0,   605,     0,   609,   610,   153,
     154,     0,   144,   124,    81,    82,    83,    84,    85,    86,
     623,    66,     0,     0,   124,     0,     0,     0,   158,     0,
     631,     0,   632,     0,     0,   633,   680,   634,   635,     0,
     124,   124,   638,   642,   604,   113,   641,   646,   647,    85,
       0,   213,   214,   215,   216,   217,   218,   219,   220,     0,
       0,     0,     0,   650,   651,     0,    74,   124,   653,     0,
       0,   124,   124,   124,     0,     0,   119,   659,   524,   532,
       0,     0,   525,   662,   154,     0,   536,   119,   526,    76,
     242,   667,     0,   669,     0,   527,     0,     0,     0,     0,
       0,     0,   158,   119,   119,     0,     0,   552,     0,   341,
     598,    81,    82,    83,    84,    85,    86,   560,   120,   562,
      66,   564,   688,     0,   113,     0,     0,     0,     0,   120,
     119,   694,     0,     0,   119,   119,   119,   699,   700,     0,
     697,   698,   160,   124,     0,   120,   120,     0,     0,   161,
     162,   599,   163,     0,   164,     0,   165,   586,     0,     0,
     708,   246,   247,   524,     0,    74,   166,   525,   124,   124,
     124,     0,   120,   526,   142,   248,   120,   120,   120,     0,
     527,   719,     0,     0,     0,   611,     0,   614,    76,     0,
     617,     0,   619,     0,     0,   114,   115,   624,   625,    66,
       0,     0,     0,   276,   277,     0,   119,     0,   402,     0,
      81,    82,    83,    84,    85,    86,   160,     0,     0,     0,
       0,     0,     0,   161,   162,   524,   163,     0,   164,   525,
     165,   119,   119,   119,     0,   526,   588,     0,   649,   395,
     166,   524,   527,   652,    74,   525,   124,     0,   120,     0,
       0,   526,   589,     0,   660,     0,     0,     0,   527,     0,
       0,     0,   663,   664,     0,     0,     0,    76,   668,     0,
     670,   671,     0,   120,   120,   120,     0,     0,     0,     0,
       0,     0,     0,     0,   121,     0,     0,   181,     0,    81,
      82,    83,    84,    85,    86,   121,     0,   124,   124,   689,
       0,   395,   691,   124,     0,   395,   693,     0,     0,   119,
     124,   121,   121,     0,     0,     0,     0,     0,     0,   395,
       0,     0,     0,   701,     0,     0,     0,     0,     0,     0,
       0,   704,     0,   706,     0,     0,     0,   709,   121,     0,
       0,     0,   121,   121,   121,     0,     0,   124,   714,   124,
       0,   120,     0,     0,   124,     0,   718,     0,   124,     0,
     119,   119,   124,     0,     0,     0,   119,     0,   124,   395,
       0,   124,     0,   119,     0,   124,     0,     0,     0,     0,
     395,     0,   395,     0,   395,   395,   395,   395,   395,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   120,   120,     0,     0,     0,     0,   120,     0,
     119,     0,   119,     0,   121,   120,     0,   119,     0,     0,
       0,   119,     0,     0,     0,   119,     0,     0,     0,     0,
       0,   119,     0,     0,   119,     0,     0,   395,   119,   121,
     121,   121,   122,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   120,   122,   120,     0,     0,     0,     0,   120,
       0,     0,     0,   120,     0,     0,     0,   120,     0,   122,
     122,     0,     0,   120,     0,     0,   120,     0,     0,     0,
     120,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   122,     0,     0,   395,
     122,   122,   122,     0,   123,     0,     0,     0,     0,     0,
       0,     0,     0,   395,   395,   123,     0,   121,   380,     0,
     381,   382,     0,     0,   395,   395,   395,   395,     0,     0,
       0,   123,   123,     0,   395,     0,   383,     0,   124,     0,
     384,   385,   386,   387,   388,     0,     0,   124,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   123,     0,
       0,     0,   123,   123,   123,     0,     0,     0,   121,   121,
       0,     0,   122,     0,   121,   124,     0,     0,   113,     0,
       0,   121,     0,     0,     0,     0,     0,   395,   395,   395,
       0,   380,     0,   381,   382,     0,     0,   122,   122,   122,
       0,   119,     0,     0,     0,     0,   124,     0,     0,   383,
     119,     0,     0,   384,   385,   386,   387,   388,   121,     0,
     121,     0,     0,     0,     0,   121,   615,     0,   142,   121,
       0,     0,     0,   121,   123,     0,     0,     0,   119,   121,
       0,     0,   121,   120,   395,     0,   121,     0,     0,   114,
     115,     0,   120,    66,     0,     0,   124,     0,     0,   123,
     123,   123,     0,     0,     0,   153,   154,   155,     0,   119,
       0,   156,     0,     0,     0,   122,     0,     0,   395,   395,
     120,   389,     0,   157,   158,     0,     0,    85,     0,   213,
     214,   215,   216,   217,   218,   219,   220,     0,    74,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   120,     0,     0,     0,     0,     0,     0,     0,   119,
       0,    76,     0,     0,     0,     0,   122,   122,     0,     0,
       0,     0,   122,     0,     0,     0,     0,   123,     0,   122,
       0,   181,     0,    81,    82,    83,    84,    85,    86,     0,
       0,     0,     0,     0,   642,     0,     0,     0,     0,     0,
      85,   120,   213,   214,   215,   216,   217,   218,   219,   220,
       0,     0,     0,     0,     0,     0,   122,     0,   122,     0,
       0,     0,     0,   122,     0,     0,     0,   122,   123,   123,
       0,   122,     0,   159,   123,     0,     0,   122,   160,     0,
     122,   123,     0,     0,   122,   161,   162,     0,   163,   121,
     164,     0,   165,     0,     0,   153,   154,   155,   121,     0,
       0,   156,   166,     0,   705,     0,     0,     0,     0,     0,
      36,     0,    37,   157,   158,    38,     0,     0,   123,     0,
     123,     0,     0,     0,    39,   123,   121,     0,     0,   123,
       0,     0,     0,   123,     0,     0,     0,     0,     0,   123,
       0,     0,   123,   543,   544,     0,   123,    40,   685,   686,
       0,     0,     0,    41,     0,    42,     0,   121,     0,    43,
       0,    44,    45,    46,    47,    48,    49,    50,    51,    52,
      53,    54,    55,    56,    57,    58,    59,    60,    61,    62,
      63,     0,     0,    64,    65,     0,     0,    66,    67,     0,
      68,    69,    70,     0,     0,     0,     0,     0,     0,     0,
       1,     2,     3,     4,     0,     0,     5,   121,     6,     7,
       8,     9,    10,     0,     0,     0,     0,     0,    11,    12,
       0,     0,     0,   159,     0,     0,     0,     0,   160,    71,
      72,    73,    74,     0,     0,   161,   162,     0,   163,     0,
     164,     0,   165,     0,    13,    75,    14,   122,     0,   242,
      15,     0,   166,     0,     0,    76,   122,     0,     0,     0,
       0,     0,     0,    77,     0,    78,     0,   243,     0,     0,
       0,     0,     0,     0,     0,    79,    80,    81,    82,    83,
      84,    85,    86,     0,   122,   103,     0,     0,     0,    16,
       0,     0,     0,   153,   154,   155,     0,   283,     0,   156,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   123,
       0,   157,   158,     0,     0,   122,     0,     0,   123,     0,
     246,   247,     0,     0,    17,    18,    19,    36,    20,    37,
       0,     0,    38,    21,   248,   249,   250,   251,   252,     0,
       0,    39,     0,     0,     0,     0,   123,     0,    22,    23,
      24,     0,     0,    25,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,    40,   122,    26,   105,     0,     0,
      41,     0,    42,     0,     0,     0,    43,   123,    44,    45,
      46,    47,    48,    49,    50,    51,    52,    53,    54,    55,
      56,    57,    58,    59,    60,    61,    62,    63,     0,     0,
      64,    65,     0,     0,    66,    67,     0,    68,    69,    70,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   159,     0,     0,     0,     0,   160,   123,     0,     0,
       0,     0,     0,   161,   162,     0,   163,     0,   164,     0,
     165,     0,     0,     0,     0,     0,    71,    72,    73,    74,
     166,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    75,    36,     0,    37,     0,   132,    38,     0,
       0,     0,    76,     0,     0,     0,     0,    39,     0,     0,
      77,     0,    78,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    79,    80,    81,    82,    83,    84,    85,    86,
      40,     0,     0,     0,     0,     0,    41,     0,    42,     0,
       0,     0,    43,     0,    44,    45,    46,    47,    48,    49,
      50,    51,    52,    53,    54,    55,    56,    57,    58,    59,
      60,    61,    62,    63,     0,     0,    64,    65,     0,     0,
      66,    67,     0,    68,    69,    70,     0,     0,     0,     0,
     153,   154,   155,     0,   600,     0,   156,     0,     0,   153,
     154,   155,     0,     0,     0,   156,     0,     0,   157,   158,
     153,   154,     0,     0,     0,     0,   156,   157,   158,     0,
       0,     0,    71,    72,    73,    74,     0,     0,   157,   158,
       0,     0,     0,     0,     0,     0,     0,     0,    75,    36,
       0,    37,     0,     0,    38,     0,     0,     0,    76,     0,
       0,     0,     0,    39,   176,     0,    77,     0,    78,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    79,    80,
      81,    82,    83,    84,    85,    86,    40,     0,     0,     0,
       0,     0,    41,     0,    42,     0,     0,     0,    43,     0,
      44,    45,    46,    47,    48,    49,    50,    51,    52,    53,
      54,    55,    56,    57,    58,    59,    60,    61,    62,    63,
       0,     0,    64,    65,     0,     0,    66,    67,   159,    68,
      69,    70,     0,   160,     0,     0,     0,   159,     0,     0,
     161,   162,   160,   163,     0,   164,     0,   165,   159,   161,
     162,     0,   163,   160,   164,     0,   165,   166,     0,     0,
     161,   162,     0,   163,     0,   164,   166,   165,    71,    72,
      73,    74,     0,     0,     0,     0,     0,   166,     0,     0,
       0,     0,     0,     0,    75,    36,     0,    37,     0,     0,
      38,     0,     0,     0,    76,     0,     0,     0,     0,    39,
       0,     0,    77,     0,    78,     0,     0,     0,     0,     0,
       0,     0,     0,     0,    79,    80,    81,    82,    83,    84,
      85,    86,    40,   113,     0,     0,     0,     0,    41,     0,
      42,     0,     0,     0,    43,     0,    44,    45,    46,    47,
      48,    49,    50,    51,    52,    53,    54,    55,    56,    57,
      58,    59,    60,    61,    62,    63,     0,     0,    64,    65,
       0,     0,    66,    67,     0,    68,    69,    70,     0,     0,
       0,   636,     0,   142,     0,     0,     0,     0,     1,     2,
       3,     4,     0,     0,     5,     0,     6,     7,     8,     9,
      10,     0,     0,     0,   114,   115,    11,    12,    66,     0,
       0,     0,     0,     0,    71,    72,    73,    74,     0,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      75,     0,    13,     0,    14,     0,     0,   243,    15,     0,
      76,     0,     0,     0,     0,     0,     0,     0,    77,     0,
      78,     0,     0,    74,     0,     0,     0,     0,     0,     0,
      79,    80,    81,    82,    83,    84,    85,    86,   240,     0,
       0,     0,     0,   241,     0,   242,    76,    16,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,   243,     0,     0,   181,     0,    81,    82,
      83,    84,    85,    86,   248,   249,   250,   251,   252,     0,
       0,     0,    17,    18,    19,     0,    20,     0,     0,     0,
       0,    21,   244,   245,   268,   269,     0,     0,   272,   273,
     274,   275,   276,   277,     0,     0,    22,    23,    24,     0,
       0,    25,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,    26,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   711,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   242,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     243,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,   246,   247,     0,     0,   241,     0,   242,
       0,     0,     0,     0,     0,     0,     0,   248,   249,   250,
     251,   252,     0,     0,     0,     0,     0,   243,     0,     0,
       0,   665,     0,     0,   666,   266,   267,   268,   269,   270,
     271,   272,   273,   274,   275,   276,   277,     0,     0,     0,
       0,     0,     0,     0,   242,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   243,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,   246,   247,     0,     0,   241,
       0,   242,     0,     0,     0,     0,     0,     0,     0,   248,
     249,   250,   251,   252,     0,     0,     0,     0,     0,   243,
       0,     0,     0,   321,     0,     0,     0,     0,     0,   268,
       0,     0,     0,   272,   273,   274,   275,   276,   277,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,   556,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   243,     0,     0,     0,   557,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,   622,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,     0,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   474,   475,     0,     0,     0,     0,   243,     0,     0,
       0,   707,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
       0,   242,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   476,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   480,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   677,     0,     0,     0,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,     0,
       0,     0,     0,     0,     0,   310,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,     0,   242,
       0,     0,     0,     0,     0,     0,     0,   358,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
       0,   242,     0,     0,     0,     0,     0,     0,   312,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,     0,     0,     0,     0,     0,
     630,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,     0,     0,     0,
     499,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,     0,
       0,     0,   502,     0,     0,     0,     0,     0,     0,   477,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,     0,   242,
       0,     0,     0,   478,     0,     0,     0,     0,     0,     0,
       0,   628,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
       0,   242,     0,     0,     0,   629,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,     0,     0,   311,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,     0,     0,   696,
       0,     0,     0,     0,     0,     0,     0,   349,     0,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   350,   351,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,     0,
       0,     0,     0,     0,     0,   306,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   307,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,     0,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   572,   573,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
       0,   242,     0,   602,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   603,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,   292,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,     0,     0,     0,
       0,     0,     0,   306,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,   345,   242,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,     0,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   347,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
       0,   242,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   357,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   375,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,   448,   242,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   479,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,   511,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
       0,   242,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   529,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,     0,     0,     0,     0,     0,
       0,   546,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   243,   555,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   569,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,     0,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   351,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
     579,   242,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,   597,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   626,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,   627,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,   640,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
     648,   242,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   658,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,   672,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   673,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,     0,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     674,     0,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
       0,   242,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   675,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,   682,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,   241,     0,   242,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   690,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,   241,     0,   242,     0,   692,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,   240,     0,     0,     0,     0,   241,     0,   242,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   695,     0,     0,     0,     0,     0,   243,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   244,   245,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     246,   247,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   248,   249,   250,   251,   252,   253,
     254,   255,   256,   257,   258,   259,   260,   261,   262,   263,
     264,   265,   266,   267,   268,   269,   270,   271,   272,   273,
     274,   275,   276,   277,   240,     0,     0,     0,     0,   241,
       0,   242,     0,   715,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   243,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   244,   245,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   246,   247,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   248,   249,   250,   251,
     252,   253,   254,   255,   256,   257,   258,   259,   260,   261,
     262,   263,   264,   265,   266,   267,   268,   269,   270,   271,
     272,   273,   274,   275,   276,   277,   240,     0,     0,     0,
       0,   241,     0,   242,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     244,   245,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,   253,   254,   255,   256,   257,   258,   259,
     260,   261,   262,   263,   264,   265,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277,   240,     0,
       0,     0,     0,     0,     0,   242,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   243,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   244,   245,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   246,   247,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     248,   249,   250,   251,   252,   253,   254,   255,   256,   257,
     258,   259,   260,   261,   262,   263,   264,   265,   266,   267,
     268,   269,   270,   271,   272,   273,   274,   275,   276,   277,
     240,     0,     0,     0,     0,     0,     0,   242,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   244,   245,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   246,   247,
     240,     0,     0,     0,     0,     0,     0,   242,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   243,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,     0,     0,     0,     0,     0,     0,     0,     0,
     240,     0,     0,     0,   244,   245,     0,   242,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   243,     0,     0,   246,   247,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   258,   259,   244,   245,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,     0,     0,     0,     0,     0,   240,   246,   247,
       0,     0,     0,     0,   242,     0,     0,     0,     0,     0,
       0,     0,   248,   249,   250,   251,   252,   253,   254,   255,
     256,   257,   243,   259,     0,     0,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     276,   277,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   244,   245,   242,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   243,     0,     0,     0,   246,   247,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   248,
     249,   250,   251,   252,   253,   254,   255,   256,   257,     0,
     244,   245,     0,   262,   263,   264,   265,   266,   267,   268,
     269,   270,   271,   272,   273,   274,   275,   276,   277,     0,
       0,     0,     0,     0,   246,   247,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   248,   249,
     250,   251,   252,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   266,   267,   268,   269,
     270,   271,   272,   273,   274,   275,   276,   277
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
         5,    72,    67,    13,   169,    10,    31,    31,    27,    14,
      15,    16,     2,     3,    17,    20,    21,    17,    23,    33,
      25,    11,    12,   297,   298,    13,   146,    12,    12,   149,
      17,    41,    37,    38,    39,    13,   310,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    21,
      24,    17,    18,    19,    27,    30,     0,   146,    62,    63,
      75,    13,    13,    13,    40,    18,    40,    59,    13,    59,
      14,    15,    16,    59,   358,   146,    20,    18,    33,    18,
      30,    18,    33,    17,   173,    30,    33,    13,    32,    33,
      22,    88,    89,    67,    18,    92,   118,    73,   113,    73,
      13,    13,   173,    18,    18,    30,    38,    39,    13,    13,
      13,   118,    46,   168,    13,   170,   146,   146,    21,   149,
     146,    33,    17,   147,   146,   151,   118,   149,    33,    33,
     154,   155,    13,   157,    33,   159,    18,   161,   173,   173,
     137,   156,   149,    75,   159,   160,   161,   171,   146,   164,
     165,   166,    90,   151,   149,   149,   146,   149,   146,    17,
     146,    19,    17,   160,    13,     4,   104,    56,    57,   143,
      17,   146,   347,   146,   146,   188,   189,   190,   191,   192,
     193,   194,   195,   180,    33,   182,   183,   184,   185,   186,
     187,   246,   147,   146,   146,   169,   146,   212,   142,    17,
     147,   146,   157,   147,   159,   146,   161,   146,   223,   146,
     154,   155,   159,   157,   161,   159,   171,   161,   239,   234,
     146,   146,   146,   238,   171,   240,   241,   171,   243,   244,
     245,   146,   146,   146,   249,   250,   251,   252,   253,   254,
     255,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   547,   146,   146,    27,    19,   349,    18,
      98,   237,    45,   354,   289,   207,   208,    13,    27,    19,
     301,    12,    31,   303,   305,   300,   180,   362,    37,   304,
     222,   575,    49,   308,   375,    44,    27,    33,   319,    19,
      31,   316,   317,   318,    27,   320,    37,    17,    21,   241,
      13,   311,   312,    44,   153,   154,   155,    90,   157,   158,
      17,    44,   606,   162,   163,   138,   139,   140,   167,    90,
      33,   104,   171,    12,    18,   350,   351,    59,   353,    91,
     355,   356,   357,    27,    31,    13,   361,    31,   369,   364,
     149,    91,   367,    37,    17,   370,   288,   372,   373,   380,
      44,   382,    13,   384,   385,   386,   387,   388,    92,    17,
      90,    91,   656,   347,   180,   147,   149,    17,    20,    19,
     132,   133,    92,   149,   104,    50,    19,   159,   362,   161,
     141,   152,   153,   133,   368,    20,    17,   236,   180,   171,
      17,   118,    19,    20,   124,   146,   118,    36,   128,   129,
     130,   131,   132,   133,    37,   180,   437,    12,    35,    67,
     180,   436,    39,    40,    41,    42,    43,   137,   443,    20,
     180,   446,    27,    18,    27,    27,    31,    51,   172,   278,
      88,    89,    37,   644,    92,   301,    68,   687,   703,    44,
     160,   286,   467,    41,   697,   303,   295,    90,    91,   474,
     475,   575,   477,   302,   479,   353,    17,   306,   307,    23,
     180,   104,   182,   183,   184,   185,   186,   187,   499,   439,
     547,   371,   321,    -1,    27,    -1,    -1,   502,    31,   137,
      -1,    -1,   513,   514,    37,   128,   129,   130,   131,   132,
     133,    44,    -1,   524,   525,   526,   527,    -1,    -1,    -1,
      -1,    -1,   160,   534,   529,    -1,    -1,   165,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   540,    -1,   542,   543,    14,
      15,    -1,   180,    13,   182,   183,   184,   185,   186,   187,
     555,    92,    -1,    -1,    24,    -1,    -1,    -1,    33,    -1,
     565,    -1,   567,    -1,    -1,   570,   173,   572,   573,    -1,
      40,    41,   577,   180,   538,    17,   581,   588,   589,   186,
      -1,   188,   189,   190,   191,   192,   193,   194,   195,    -1,
      -1,    -1,    -1,   598,   599,    -1,   137,    67,   603,    -1,
      -1,    71,    72,    73,    -1,    -1,    13,   612,    27,   438,
      -1,    -1,    31,   618,    15,    -1,   445,    24,    37,   160,
      19,   626,    -1,   628,    -1,    44,    -1,    -1,    -1,    -1,
      -1,    -1,    33,    40,    41,    -1,    -1,   466,    -1,   180,
      59,   182,   183,   184,   185,   186,   187,   476,    13,   478,
      92,   480,   657,    -1,    17,    -1,    -1,    -1,    -1,    24,
      67,   666,    -1,    -1,    71,    72,    73,   678,   679,    -1,
     675,   676,   147,   143,    -1,    40,    41,    -1,    -1,   154,
     155,   100,   157,    -1,   159,    -1,   161,    21,    -1,    -1,
     695,    90,    91,    27,    -1,   137,   171,    31,   168,   169,
     170,    -1,    67,    37,    67,   104,    71,    72,    73,    -1,
      44,   716,    -1,    -1,    -1,   544,    -1,   546,   160,    -1,
     549,    -1,   551,    -1,    -1,    88,    89,   556,   557,    92,
      -1,    -1,    -1,   132,   133,    -1,   143,    -1,   180,    -1,
     182,   183,   184,   185,   186,   187,   147,    -1,    -1,    -1,
      -1,    -1,    -1,   154,   155,    27,   157,    -1,   159,    31,
     161,   168,   169,   170,    -1,    37,    38,    -1,   597,   239,
     171,    27,    44,   602,   137,    31,   246,    -1,   143,    -1,
      -1,    37,    38,    -1,   613,    -1,    -1,    -1,    44,    -1,
      -1,    -1,   621,   622,    -1,    -1,    -1,   160,   627,    -1,
     629,   630,    -1,   168,   169,   170,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    13,    -1,    -1,   180,    -1,   182,
     183,   184,   185,   186,   187,    24,    -1,   297,   298,   658,
      -1,   301,   661,   303,    -1,   305,   665,    -1,    -1,   246,
     310,    40,    41,    -1,    -1,    -1,    -1,    -1,    -1,   319,
      -1,    -1,    -1,   682,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   690,    -1,   692,    -1,    -1,    -1,   696,    67,    -1,
      -1,    -1,    71,    72,    73,    -1,    -1,   347,   707,   349,
      -1,   246,    -1,    -1,   354,    -1,   715,    -1,   358,    -1,
     297,   298,   362,    -1,    -1,    -1,   303,    -1,   368,   369,
      -1,   371,    -1,   310,    -1,   375,    -1,    -1,    -1,    -1,
     380,    -1,   382,    -1,   384,   385,   386,   387,   388,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   297,   298,    -1,    -1,    -1,    -1,   303,    -1,
     347,    -1,   349,    -1,   143,   310,    -1,   354,    -1,    -1,
      -1,   358,    -1,    -1,    -1,   362,    -1,    -1,    -1,    -1,
      -1,   368,    -1,    -1,   371,    -1,    -1,   437,   375,   168,
     169,   170,    13,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   347,    24,   349,    -1,    -1,    -1,    -1,   354,
      -1,    -1,    -1,   358,    -1,    -1,    -1,   362,    -1,    40,
      41,    -1,    -1,   368,    -1,    -1,   371,    -1,    -1,    -1,
     375,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    67,    -1,    -1,   499,
      71,    72,    73,    -1,    13,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   513,   514,    24,    -1,   246,    17,    -1,
      19,    20,    -1,    -1,   524,   525,   526,   527,    -1,    -1,
      -1,    40,    41,    -1,   534,    -1,    35,    -1,   538,    -1,
      39,    40,    41,    42,    43,    -1,    -1,   547,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    67,    -1,
      -1,    -1,    71,    72,    73,    -1,    -1,    -1,   297,   298,
      -1,    -1,   143,    -1,   303,   575,    -1,    -1,    17,    -1,
      -1,   310,    -1,    -1,    -1,    -1,    -1,   587,   588,   589,
      -1,    17,    -1,    19,    20,    -1,    -1,   168,   169,   170,
      -1,   538,    -1,    -1,    -1,    -1,   606,    -1,    -1,    35,
     547,    -1,    -1,    39,    40,    41,    42,    43,   347,    -1,
     349,    -1,    -1,    -1,    -1,   354,    65,    -1,    67,   358,
      -1,    -1,    -1,   362,   143,    -1,    -1,    -1,   575,   368,
      -1,    -1,   371,   538,   644,    -1,   375,    -1,    -1,    88,
      89,    -1,   547,    92,    -1,    -1,   656,    -1,    -1,   168,
     169,   170,    -1,    -1,    -1,    14,    15,    16,    -1,   606,
      -1,    20,    -1,    -1,    -1,   246,    -1,    -1,   678,   679,
     575,   180,    -1,    32,    33,    -1,    -1,   186,    -1,   188,
     189,   190,   191,   192,   193,   194,   195,    -1,   137,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   606,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   656,
      -1,   160,    -1,    -1,    -1,    -1,   297,   298,    -1,    -1,
      -1,    -1,   303,    -1,    -1,    -1,    -1,   246,    -1,   310,
      -1,   180,    -1,   182,   183,   184,   185,   186,   187,    -1,
      -1,    -1,    -1,    -1,   180,    -1,    -1,    -1,    -1,    -1,
     186,   656,   188,   189,   190,   191,   192,   193,   194,   195,
      -1,    -1,    -1,    -1,    -1,    -1,   347,    -1,   349,    -1,
      -1,    -1,    -1,   354,    -1,    -1,    -1,   358,   297,   298,
      -1,   362,    -1,   142,   303,    -1,    -1,   368,   147,    -1,
     371,   310,    -1,    -1,   375,   154,   155,    -1,   157,   538,
     159,    -1,   161,    -1,    -1,    14,    15,    16,   547,    -1,
      -1,    20,   171,    -1,   173,    -1,    -1,    -1,    -1,    -1,
      15,    -1,    17,    32,    33,    20,    -1,    -1,   347,    -1,
     349,    -1,    -1,    -1,    29,   354,   575,    -1,    -1,   358,
      -1,    -1,    -1,   362,    -1,    -1,    -1,    -1,    -1,   368,
      -1,    -1,   371,    62,    63,    -1,   375,    52,    53,    54,
      -1,    -1,    -1,    58,    -1,    60,    -1,   606,    -1,    64,
      -1,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,    -1,
      95,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      14,    15,    16,    17,    -1,    -1,    20,   656,    22,    23,
      24,    25,    26,    -1,    -1,    -1,    -1,    -1,    32,    33,
      -1,    -1,    -1,   142,    -1,    -1,    -1,    -1,   147,   134,
     135,   136,   137,    -1,    -1,   154,   155,    -1,   157,    -1,
     159,    -1,   161,    -1,    58,   150,    60,   538,    -1,    19,
      64,    -1,   171,    -1,    -1,   160,   547,    -1,    -1,    -1,
      -1,    -1,    -1,   168,    -1,   170,    -1,    37,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,
     185,   186,   187,    -1,   575,    99,    -1,    -1,    -1,   103,
      -1,    -1,    -1,    14,    15,    16,    -1,    18,    -1,    20,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   538,
      -1,    32,    33,    -1,    -1,   606,    -1,    -1,   547,    -1,
      90,    91,    -1,    -1,   138,   139,   140,    15,   142,    17,
      -1,    -1,    20,   147,   104,   105,   106,   107,   108,    -1,
      -1,    29,    -1,    -1,    -1,    -1,   575,    -1,   162,   163,
     164,    -1,    -1,   167,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    52,   656,   180,    55,    -1,    -1,
      58,    -1,    60,    -1,    -1,    -1,    64,   606,    66,    67,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    -1,    -1,
      88,    89,    -1,    -1,    92,    93,    -1,    95,    96,    97,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   142,    -1,    -1,    -1,    -1,   147,   656,    -1,    -1,
      -1,    -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,    -1,    -1,    -1,    -1,    -1,   134,   135,   136,   137,
     171,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   150,    15,    -1,    17,    -1,    19,    20,    -1,
      -1,    -1,   160,    -1,    -1,    -1,    -1,    29,    -1,    -1,
     168,    -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   180,   181,   182,   183,   184,   185,   186,   187,
      52,    -1,    -1,    -1,    -1,    -1,    58,    -1,    60,    -1,
      -1,    -1,    64,    -1,    66,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,
      92,    93,    -1,    95,    96,    97,    -1,    -1,    -1,    -1,
      14,    15,    16,    -1,    18,    -1,    20,    -1,    -1,    14,
      15,    16,    -1,    -1,    -1,    20,    -1,    -1,    32,    33,
      14,    15,    -1,    -1,    -1,    -1,    20,    32,    33,    -1,
      -1,    -1,   134,   135,   136,   137,    -1,    -1,    32,    33,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   150,    15,
      -1,    17,    -1,    -1,    20,    -1,    -1,    -1,   160,    -1,
      -1,    -1,    -1,    29,    30,    -1,   168,    -1,   170,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,   181,
     182,   183,   184,   185,   186,   187,    52,    -1,    -1,    -1,
      -1,    -1,    58,    -1,    60,    -1,    -1,    -1,    64,    -1,
      66,    67,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,    85,
      -1,    -1,    88,    89,    -1,    -1,    92,    93,   142,    95,
      96,    97,    -1,   147,    -1,    -1,    -1,   142,    -1,    -1,
     154,   155,   147,   157,    -1,   159,    -1,   161,   142,   154,
     155,    -1,   157,   147,   159,    -1,   161,   171,    -1,    -1,
     154,   155,    -1,   157,    -1,   159,   171,   161,   134,   135,
     136,   137,    -1,    -1,    -1,    -1,    -1,   171,    -1,    -1,
      -1,    -1,    -1,    -1,   150,    15,    -1,    17,    -1,    -1,
      20,    -1,    -1,    -1,   160,    -1,    -1,    -1,    -1,    29,
      -1,    -1,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,   185,
     186,   187,    52,    17,    -1,    -1,    -1,    -1,    58,    -1,
      60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    -1,    -1,    88,    89,
      -1,    -1,    92,    93,    -1,    95,    96,    97,    -1,    -1,
      -1,    65,    -1,    67,    -1,    -1,    -1,    -1,    14,    15,
      16,    17,    -1,    -1,    20,    -1,    22,    23,    24,    25,
      26,    -1,    -1,    -1,    88,    89,    32,    33,    92,    -1,
      -1,    -1,    -1,    -1,   134,   135,   136,   137,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     150,    -1,    58,    -1,    60,    -1,    -1,    37,    64,    -1,
     160,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   168,    -1,
     170,    -1,    -1,   137,    -1,    -1,    -1,    -1,    -1,    -1,
     180,   181,   182,   183,   184,   185,   186,   187,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,   160,   103,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    37,    -1,    -1,   180,    -1,   182,   183,
     184,   185,   186,   187,   104,   105,   106,   107,   108,    -1,
      -1,    -1,   138,   139,   140,    -1,   142,    -1,    -1,    -1,
      -1,   147,    66,    67,   124,   125,    -1,    -1,   128,   129,
     130,   131,   132,   133,    -1,    -1,   162,   163,   164,    -1,
      -1,   167,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,   180,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   173,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    19,    -1,    66,    67,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    12,    90,    91,    -1,    -1,    17,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      -1,   163,    -1,    -1,   166,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    19,    -1,    66,    67,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    12,    90,    91,    -1,    -1,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,   163,    -1,    -1,    -1,    -1,    -1,   124,
      -1,    -1,    -1,   128,   129,   130,   131,   132,   133,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    31,    32,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   158,    37,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   156,    -1,
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
      -1,    -1,    -1,    -1,    -1,   151,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,   149,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   149,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,   148,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
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
     148,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
     146,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,   146,    -1,    -1,    -1,    -1,    -1,    -1,    31,
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
      -1,    -1,    -1,   145,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    31,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      -1,    19,    -1,    -1,    -1,   145,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
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
      -1,    17,    -1,    19,    -1,    -1,    -1,   145,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,   145,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    31,    -1,    -1,
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
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    27,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    61,
      -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    62,    63,    -1,    -1,    66,    67,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    21,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    31,    -1,    -1,    -1,    -1,    -1,    37,
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
      -1,    17,    -1,    19,    -1,    21,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    27,    -1,    -1,    -1,    -1,    -1,    -1,
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
      12,    -1,    -1,    -1,    -1,    17,    18,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    31,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    61,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    31,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    17,    18,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    31,
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
     132,   133,    12,    -1,    -1,    -1,    -1,    17,    18,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    86,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    27,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    37,    38,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    30,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    87,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,
      18,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
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
      -1,    17,    -1,    19,    -1,    21,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    32,    -1,
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
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
     132,   133,    12,    -1,    -1,    -1,    -1,    17,    18,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      18,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    17,    -1,    19,    -1,    21,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    30,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      30,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    61,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    21,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    31,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      -1,    19,    -1,    21,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,    66,    67,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    12,    90,    91,
      -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,    37,   115,    -1,    -1,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    37,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,    -1,
      66,    67,    -1,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133
  };
  }


  static class YyTokenClass {
  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  public static final short
  yystos_[] =
  {
         0,    14,    15,    16,    17,    20,    22,    23,    24,    25,
      26,    32,    33,    58,    60,    64,   103,   138,   139,   140,
     142,   147,   162,   163,   164,   167,   180,   212,   214,   215,
     216,   258,   260,   261,   264,   266,    15,    17,    20,    29,
      52,    58,    60,    64,    66,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    88,    89,    92,    93,    95,    96,
      97,   134,   135,   136,   137,   150,   160,   168,   170,   180,
     181,   182,   183,   184,   185,   186,   187,   211,   217,   239,
     240,   241,   242,   243,   244,   245,   246,   249,   251,   254,
     255,   211,   211,    99,   212,    55,   234,   235,   240,   269,
     240,   211,   211,    17,    88,    89,   180,   225,   226,   241,
     242,   243,   244,   245,   246,   247,   248,   271,   274,   240,
     259,   240,    19,   240,   217,   217,   217,   240,   240,   239,
     240,   259,    67,   165,   180,   270,   271,   273,   275,   276,
     277,   240,     0,    14,    15,    16,    20,    32,    33,   142,
     147,   154,   155,   157,   159,   161,   171,    27,    90,   152,
     153,    13,    33,   240,   239,   240,    30,   239,   240,   256,
     257,   180,   270,   275,   247,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,    17,    17,   270,
     277,   282,    17,   188,   189,   190,   191,   192,   193,   194,
     195,   219,    17,    17,   270,   272,   278,   279,   280,   281,
     278,   275,   239,   240,    17,    98,    13,    33,   146,   149,
      12,    17,    19,    37,    66,    67,    90,    91,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    13,    13,
     180,   262,   263,    18,    56,    57,   236,   237,   238,    49,
     229,   230,    21,    21,    13,    13,   240,    17,    17,    19,
      12,   149,    59,   146,    12,   149,    27,    61,    31,   173,
     149,   145,   148,    13,   173,   270,    12,    59,   118,   149,
      59,   163,   212,   212,   212,   240,   212,   212,   240,   240,
     240,   212,   212,   240,   240,   240,   212,   180,   274,   270,
     276,   180,   274,   212,   216,    18,    21,    31,    30,    31,
      86,    87,    30,   146,    31,    13,    59,    61,   149,   239,
     239,    13,   146,   240,    17,   239,   240,   118,   146,   149,
      13,   146,    13,    13,   151,    31,   240,   212,   217,   240,
      17,    19,    20,    35,    39,    40,    41,    42,    43,   180,
     218,   219,   220,   221,   222,   246,   240,   239,   240,   240,
     240,   240,   180,   274,    92,   180,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   212,    20,   149,    13,   146,
     237,   239,   240,    50,   232,   141,    20,   212,    18,   272,
     272,   240,   218,   220,   212,   248,   240,   218,   212,   212,
     240,   267,   268,   272,   211,   211,    17,   118,   240,   240,
     240,   218,   240,   212,    31,    32,   158,    31,   145,    31,
     156,   270,   276,   278,   240,   240,   240,   257,   278,   240,
     240,   240,   252,   253,   272,    18,    18,   240,   277,   146,
     240,    18,   146,   240,   270,   218,   240,   279,   240,   240,
     278,    18,   218,    27,    44,   218,   180,   218,   218,   218,
     218,   218,    90,   104,    27,    31,    37,    44,    18,    86,
     240,   218,   212,   263,   149,   240,   212,   240,   146,    18,
      12,   180,    12,    62,    63,   265,    27,   146,   173,    27,
      13,    13,   212,   213,   240,    38,   163,   163,   240,   240,
     212,   240,   212,   240,   212,    13,    21,    13,    30,    30,
      13,    30,    62,    63,   250,   146,   173,    27,   218,    18,
     240,    13,   151,    18,   218,   218,    21,    36,    38,    38,
     180,   180,   218,   218,   218,   218,   240,    21,    59,   100,
      18,   218,    21,    31,   270,   240,    17,    19,   227,   240,
     240,   212,    62,    63,   212,    65,   268,   212,    20,   212,
      18,   146,   163,   240,   212,   212,    32,    21,    31,   145,
     148,   240,   240,   240,   240,   240,    65,   253,   240,    18,
      18,   240,   180,   218,   223,   224,   218,   218,    18,   212,
     240,   240,   212,   240,    18,   272,    17,    46,    61,   240,
     212,    27,   240,   212,   212,   163,   166,   240,   212,   240,
     212,   212,    21,    30,    30,    61,    27,   151,    45,   149,
     173,   224,    21,    18,   272,    53,    54,   228,   240,   212,
      61,   212,    21,   212,   240,    31,   145,   240,   240,   218,
     218,   212,    18,   229,   212,   173,   212,   163,   240,   212,
     250,   173,   231,   232,   212,    21,    51,   233,   212,   240
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
         0,   210,   211,   211,   212,   212,   212,   212,   212,   212,
     212,   212,   212,   212,   212,   212,   212,   212,   212,   212,
     212,   212,   212,   212,   212,   212,   212,   212,   212,   212,
     212,   212,   212,   212,   212,   212,   212,   212,   212,   212,
     212,   212,   213,   213,   214,   214,   214,   214,   214,   214,
     214,   215,   215,   216,   216,   216,   217,   218,   218,   218,
     218,   218,   218,   218,   218,   218,   218,   218,   218,   218,
     218,   218,   218,   219,   219,   219,   219,   219,   219,   219,
     219,   220,   220,   221,   221,   222,   222,   223,   223,   224,
     224,   224,   225,   225,   225,   225,   226,   227,   227,   227,
     228,   228,   228,   229,   229,   230,   231,   231,   232,   233,
     233,   234,   234,   235,   236,   236,   237,   237,   238,   238,
     239,   239,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   241,   241,   242,   243,   244,   244,   244,   245,
     246,   247,   247,   248,   248,   249,   250,   250,   251,   251,
     252,   252,   253,   254,   254,   254,   254,   254,   254,   254,
     254,   254,   254,   254,   254,   254,   254,   254,   254,   254,
     254,   254,   254,   255,   255,   255,   255,   255,   255,   255,
     255,   255,   255,   255,   255,   255,   255,   255,   255,   255,
     255,   255,   255,   255,   255,   255,   255,   255,   255,   255,
     255,   255,   255,   255,   255,   255,   256,   256,   257,   258,
     258,   258,   258,   258,   258,   258,   258,   258,   258,   258,
     258,   258,   258,   259,   259,   260,   261,   261,   262,   262,
     263,   263,   263,   264,   264,   265,   265,   266,   266,   267,
     267,   268,   269,   270,   270,   271,   271,   271,   271,   272,
     272,   273,   273,   274,   274,   274,   274,   274,   274,   274,
     275,   275,   276,   277,   278,   278,   279,   279,   280,   281,
     282,   282
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     3,     1,     1,     1,     1,     2,     3,
       5,     3,     3,     3,     3,     5,     3,     5,     3,     3,
       3,     6,     7,     3,     7,     3,    11,     7,     9,     5,
       3,     4,     4,     4,     6,     7,     9,     9,     7,     1,
       1,     1,     1,     3,     1,     3,     3,     3,     3,     3,
       3,     1,     3,     2,     2,     2,     3,     3,     1,     1,
       5,     3,     3,     3,     2,     2,     2,     4,     4,     1,
       1,     3,     3,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     3,     3,     3,     3,     1,     2,     1,
       3,     3,     5,     5,     3,     3,    10,     1,     3,     4,
       1,     1,     1,     1,     0,     2,     1,     0,     2,     2,
       0,     1,     0,     2,     1,     2,     2,     4,     1,     1,
       1,     3,     1,     1,     1,     1,     1,     1,     1,     1,
       2,     3,     3,     3,     2,     4,     6,     3,     3,     4,
       1,     1,     1,     1,     4,     4,     4,     4,     2,     3,
       5,     7,     5,     1,     3,     5,     7,     1,     3,     5,
       7,     4,     4,     4,     4,     5,     6,     4,     6,     3,
       5,     7,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     3,     1,     1,     5,     2,     5,     5,     9,
       1,     3,     3,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     1,     3,     3,     1,
       3,     3,     3,     1,     2,     2,     1,     6,     6,     7,
       8,    10,     4,     3,     5,     4,     3,     6,     1,     3,
       3,     5,     5,     7,     6,     4,     5,     5,     9,     1,
       3,     3,     3,     1,     1,     1,     6,     2,     4,     1,
       3,     1,     1,     1,     1,     1,     1,     1,     1,     3,
       1,     1,     3,     3,     1,     3,     1,     1,     3,     3,
       1,     3
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
  "replicationDeclaration", "action", "actionList", "communication",
  "parametrisationList", "parametrisation", "singleTypeDeclaration",
  "type", "basicType", "functionType", "partialFunctionType",
  "totalFunctionType", "fieldList", "field", "valueDef",
  "explicitFunctionDef", "parameterList", "functionBody", "preExpr_opt",
  "preExpr", "postExpr_opt", "postExpr", "measureExpr", "externals_opt",
  "externals", "varInformationList", "varInformation", "mode",
  "expressionList", "expression", "booleanLiteral", "characterLiteral",
  "nilLiteral", "numericLiteral", "textLiteral", "quoteLiteral",
  "localDefList", "localDef", "ifExpr", "elseExprs", "casesExpr",
  "casesExprAltList", "casesExprAlt", "unaryExpr", "binaryExpr",
  "mapletList", "maplet", "controlStatement", "nonDeterministicAltList",
  "letStatement", "blockStatement", "assignmentDefList", "assignmentDef",
  "ifStatement", "elseStatements", "casesStatement",
  "casesStatementAltList", "casesStatementAlt", "implicitOperationBody",
  "pattern", "patternLessID", "patternList", "patternIdentifier",
  "matchValue", "bind", "setBind", "typeBind", "bindList", "multipleBind",
  "multipleSetBind", "multipleTypeBind", "typeBindList", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final short yyrhs_[] =
  {
       212,     0,    -1,   217,    -1,   211,    33,   217,    -1,    22,
      -1,    23,    -1,    24,    -1,    25,    -1,    26,   240,    -1,
     214,    27,   212,    -1,    20,   240,    21,   141,   212,    -1,
     212,    33,   212,    -1,   212,    15,   212,    -1,   212,    14,
     212,    -1,   212,   155,   212,    -1,   212,   154,   240,   156,
     212,    -1,   212,   157,   212,    -1,   212,    20,   240,   158,
     212,    -1,   212,   171,   240,    -1,   212,   161,   240,    -1,
     212,   159,   240,    -1,   162,   239,    13,    17,   213,    18,
      -1,   212,   147,   240,    31,   240,   148,   212,    -1,   212,
      16,   212,    -1,   212,   142,   240,    31,   240,   145,   212,
      -1,   212,    32,   212,    -1,   212,    20,   240,    31,   240,
      32,   240,    31,   240,    21,   212,    -1,   212,    20,   240,
      32,   240,    21,   212,    -1,   212,   142,   240,    31,   240,
      31,   240,   145,   212,    -1,   212,   142,   240,   145,   212,
      -1,   215,    13,   212,    -1,    33,   211,    13,   212,    -1,
      15,   211,    13,   212,    -1,    14,   211,    13,   212,    -1,
     147,   240,   148,   211,    13,   212,    -1,    16,   211,    13,
      20,   240,    21,   212,    -1,   142,   240,   145,   211,    13,
      20,   240,    21,   212,    -1,    32,   211,    13,    20,   240,
      31,   240,    21,   212,    -1,    32,   211,    13,    20,   240,
      21,   212,    -1,   260,    -1,   261,    -1,   258,    -1,   212,
      -1,   213,   146,   212,    -1,   180,    -1,   214,    90,   180,
      -1,   214,    90,   274,    -1,   214,   153,   180,    -1,   214,
     153,   274,    -1,   214,   152,   270,    -1,   214,   152,   276,
      -1,   216,    -1,   215,    33,   216,    -1,   140,   217,    -1,
     139,   217,    -1,   138,   217,    -1,   239,   149,   218,    -1,
      17,   218,    18,    -1,   219,    -1,   246,    -1,    35,   180,
      36,   223,   173,    -1,   218,    31,   218,    -1,   218,    37,
     218,    -1,    20,   218,    21,    -1,    43,   218,    -1,    41,
     218,    -1,    42,   218,    -1,    40,   218,    38,   218,    -1,
      39,   218,    38,   218,    -1,   220,    -1,   180,    -1,   180,
      90,   180,    -1,   180,   104,   180,    -1,   188,    -1,   189,
      -1,   190,    -1,   191,    -1,   192,    -1,   193,    -1,   194,
      -1,   195,    -1,   221,    -1,   222,    -1,   218,    44,   218,
      -1,    19,    44,   218,    -1,   218,    27,   218,    -1,    19,
      27,   218,    -1,   224,    -1,   223,   224,    -1,   218,    -1,
     180,   149,   218,    -1,   180,    45,   218,    -1,   180,   149,
     218,    12,   240,    -1,   271,   149,   218,    12,   240,    -1,
     180,    12,   240,    -1,   271,    12,   240,    -1,   180,   149,
     220,   180,   227,    46,   228,   229,   231,   233,    -1,    19,
      -1,    17,   272,    18,    -1,   227,    17,   272,    18,    -1,
     240,    -1,    53,    -1,    54,    -1,   230,    -1,    -1,    49,
     240,    -1,   232,    -1,    -1,    50,   240,    -1,    51,   240,
      -1,    -1,   235,    -1,    -1,    55,   236,    -1,   237,    -1,
     236,   237,    -1,   238,   239,    -1,   238,   239,   149,   218,
      -1,    56,    -1,    57,    -1,   240,    -1,   239,   146,   240,
      -1,   244,    -1,   241,    -1,   243,    -1,   242,    -1,   245,
      -1,   246,    -1,   181,    -1,   180,    -1,   180,    98,    -1,
     240,    90,   180,    -1,   240,   104,   180,    -1,   240,    91,
      92,    -1,   240,    19,    -1,   240,    17,   239,    18,    -1,
     240,    17,   240,    86,   240,    18,    -1,   240,    90,   274,
      -1,    17,   240,    18,    -1,    58,   247,    59,   240,    -1,
     249,    -1,   251,    -1,   254,    -1,   255,    -1,   134,   278,
      13,   240,    -1,   135,   278,    13,   240,    -1,   136,   275,
      13,   240,    -1,    52,   275,    13,   240,    -1,    29,    30,
      -1,    29,   239,    30,    -1,    29,   240,    31,   278,    30,
      -1,    29,   240,    31,   278,    13,   240,    30,    -1,    29,
     240,    86,   240,    30,    -1,    15,    -1,    20,   239,    21,
      -1,    20,   240,    31,   276,    21,    -1,    20,   240,    31,
     276,    13,   240,    21,    -1,   170,    -1,    29,   256,    30,
      -1,    29,   257,    31,   278,    30,    -1,    29,   257,    31,
     278,    13,   240,    30,    -1,    88,    17,   239,    18,    -1,
      89,    17,   239,    18,    -1,    93,   282,    13,   240,    -1,
     168,    17,   240,    18,    -1,    95,   219,    17,   240,    18,
      -1,    95,    17,   240,   146,   218,    18,    -1,    96,    17,
     239,    18,    -1,    97,    17,   240,   146,   240,    18,    -1,
     150,   239,   151,    -1,   150,   240,    31,   278,   151,    -1,
     150,   240,    31,   278,    13,   240,   151,    -1,   183,    -1,
     182,    -1,   184,    -1,   185,    -1,    92,    -1,   187,    -1,
     160,    -1,   137,    -1,   186,    -1,   248,    -1,   247,   146,
     248,    -1,   225,    -1,   226,    -1,    60,   240,    61,   240,
     250,    -1,    63,   240,    -1,    62,   240,    61,   240,   250,
      -1,    64,   240,   149,   252,   173,    -1,    64,   240,   149,
     252,   146,    65,    27,   240,   173,    -1,   253,    -1,   252,
     146,   253,    -1,   272,    27,   240,    -1,    66,   240,    -1,
      67,   240,    -1,    68,   240,    -1,    69,   240,    -1,    70,
     240,    -1,    71,   240,    -1,    72,   240,    -1,    73,   240,
      -1,    74,   240,    -1,    75,   240,    -1,    76,   240,    -1,
      77,   240,    -1,    78,   240,    -1,    79,   240,    -1,    80,
     240,    -1,    81,   240,    -1,    82,   240,    -1,    83,   240,
      -1,    84,   240,    -1,    85,   240,    -1,   240,    66,   240,
      -1,   240,    37,   240,    -1,   240,    67,   240,    -1,   240,
     106,   240,    -1,   240,   105,   240,    -1,   240,   107,   240,
      -1,   240,   108,   240,    -1,   240,   109,   240,    -1,   240,
     110,   240,    -1,   240,   111,   240,    -1,   240,   112,   240,
      -1,   240,    12,   240,    -1,   240,   113,   240,    -1,   240,
     114,   240,    -1,   240,   115,   240,    -1,   240,   116,   240,
      -1,   240,   117,   240,    -1,   240,   118,   240,    -1,   240,
     119,   240,    -1,   240,   120,   240,    -1,   240,   121,   240,
      -1,   240,   122,   240,    -1,   240,   123,   240,    -1,   240,
     124,   240,    -1,   240,   125,   240,    -1,   240,   126,   240,
      -1,   240,   127,   240,    -1,   240,   128,   240,    -1,   240,
     129,   240,    -1,   240,   130,   240,    -1,   240,   131,   240,
      -1,   240,   132,   240,    -1,   240,   133,   240,    -1,   257,
      -1,   256,   146,   257,    -1,   240,    87,   240,    -1,   264,
      -1,    60,   259,   173,    -1,   163,   259,   173,    -1,    20,
     269,    21,    -1,   103,    -1,   103,    19,    -1,   103,   240,
      -1,   266,    -1,   164,   275,    59,   240,   163,   212,    -1,
     164,   270,    59,   240,   163,   212,    -1,   164,   165,   270,
     118,   240,   163,   212,    -1,   164,   180,    12,   240,    38,
     240,   163,   212,    -1,   164,   180,    12,   240,    38,   240,
     166,   240,   163,   212,    -1,   167,   240,   163,   212,    -1,
     240,    27,   212,    -1,   259,    31,   240,    27,   212,    -1,
      58,   247,    59,   212,    -1,    17,   212,    18,    -1,    17,
      99,   262,    13,   212,    18,    -1,   263,    -1,   262,   146,
     263,    -1,   180,   149,   218,    -1,   180,   149,   218,   100,
     240,    -1,   180,   149,   218,    59,   240,    -1,    60,   240,
      61,   212,   265,    63,   212,    -1,    60,   240,    61,   212,
      63,   212,    -1,    62,   240,    61,   212,    -1,   265,    62,
     240,    61,   212,    -1,    64,   240,   149,   267,   173,    -1,
      64,   240,   149,   267,   146,    65,    27,   212,   173,    -1,
     268,    -1,   267,   146,   268,    -1,   272,    27,   212,    -1,
     234,   229,   232,    -1,   273,    -1,   271,    -1,   274,    -1,
      88,    17,   272,   146,   270,    18,    -1,    89,    19,    -1,
      89,    17,   272,    18,    -1,   270,    -1,   272,   146,   270,
      -1,   180,    -1,    67,    -1,   244,    -1,   241,    -1,   243,
      -1,   242,    -1,   245,    -1,   246,    -1,    17,   240,    18,
      -1,   276,    -1,   277,    -1,   270,   118,   240,    -1,   270,
     149,   218,    -1,   279,    -1,   278,   146,   279,    -1,   280,
      -1,   281,    -1,   272,   118,   240,    -1,   272,   149,   218,
      -1,   277,    -1,   282,   146,   277,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     9,    11,    13,    15,    17,    20,
      24,    30,    34,    38,    42,    46,    52,    56,    62,    66,
      70,    74,    81,    89,    93,   101,   105,   117,   125,   135,
     141,   145,   150,   155,   160,   167,   175,   185,   195,   203,
     205,   207,   209,   211,   215,   217,   221,   225,   229,   233,
     237,   241,   243,   247,   250,   253,   256,   260,   264,   266,
     268,   274,   278,   282,   286,   289,   292,   295,   300,   305,
     307,   309,   313,   317,   319,   321,   323,   325,   327,   329,
     331,   333,   335,   337,   341,   345,   349,   353,   355,   358,
     360,   364,   368,   374,   380,   384,   388,   399,   401,   405,
     410,   412,   414,   416,   418,   419,   422,   424,   425,   428,
     431,   432,   434,   435,   438,   440,   443,   446,   451,   453,
     455,   457,   461,   463,   465,   467,   469,   471,   473,   475,
     477,   480,   484,   488,   492,   495,   500,   507,   511,   515,
     520,   522,   524,   526,   528,   533,   538,   543,   548,   551,
     555,   561,   569,   575,   577,   581,   587,   595,   597,   601,
     607,   615,   620,   625,   630,   635,   641,   648,   653,   660,
     664,   670,   678,   680,   682,   684,   686,   688,   690,   692,
     694,   696,   698,   702,   704,   706,   712,   715,   721,   727,
     737,   739,   743,   747,   750,   753,   756,   759,   762,   765,
     768,   771,   774,   777,   780,   783,   786,   789,   792,   795,
     798,   801,   804,   807,   811,   815,   819,   823,   827,   831,
     835,   839,   843,   847,   851,   855,   859,   863,   867,   871,
     875,   879,   883,   887,   891,   895,   899,   903,   907,   911,
     915,   919,   923,   927,   931,   935,   939,   941,   945,   949,
     951,   955,   959,   963,   965,   968,   971,   973,   980,   987,
     995,  1004,  1015,  1020,  1024,  1030,  1035,  1039,  1046,  1048,
    1052,  1056,  1062,  1068,  1076,  1083,  1088,  1094,  1100,  1110,
    1112,  1116,  1120,  1124,  1126,  1128,  1130,  1137,  1140,  1145,
    1147,  1151,  1153,  1155,  1157,  1159,  1161,  1163,  1165,  1167,
    1171,  1173,  1175,  1179,  1183,  1185,  1189,  1191,  1193,  1197,
    1201,  1203
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   263,   263,   265,   320,   321,   322,   323,   324,   337,
     346,   347,   348,   349,   350,   359,   360,   369,   379,   380,
     381,   389,   394,   395,   399,   400,   405,   409,   414,   418,
     428,   434,   435,   436,   440,   444,   449,   454,   458,   460,
     461,   462,   467,   468,   472,   473,   474,   475,   476,   477,
     478,   482,   483,   487,   488,   489,   561,   652,   653,   654,
     655,   656,   657,   658,   659,   660,   661,   662,   663,   664,
     673,   674,   675,   679,   680,   681,   682,   683,   684,   685,
     686,   690,   691,   695,   696,   700,   701,   705,   706,   710,
     711,   712,   742,   743,   744,   745,   773,   778,   779,   780,
     784,   785,   786,   810,   811,   815,   819,   820,   824,   833,
     834,   884,   885,   889,   893,   894,   898,   899,   903,   904,
     942,   943,   948,   949,   950,   951,   952,   953,   955,   956,
     957,   958,   959,   960,   961,   962,   963,   964,   965,   966,
     967,   968,   969,   970,   972,   973,   974,   975,   977,   978,
     980,   981,   983,   985,   996,   998,   999,  1001,  1002,  1004,
    1005,  1007,  1009,  1011,  1020,  1021,  1022,  1027,  1035,  1037,
    1038,  1039,  1045,  1046,  1050,  1054,  1058,  1059,  1060,  1064,
    1068,  1074,  1075,  1079,  1080,  1084,  1088,  1089,  1093,  1094,
    1098,  1099,  1103,  1107,  1108,  1109,  1110,  1111,  1112,  1113,
    1114,  1115,  1116,  1117,  1118,  1119,  1120,  1121,  1122,  1123,
    1124,  1125,  1126,  1130,  1131,  1132,  1133,  1134,  1135,  1136,
    1137,  1138,  1139,  1140,  1141,  1142,  1143,  1144,  1145,  1146,
    1147,  1148,  1149,  1150,  1151,  1152,  1153,  1154,  1155,  1156,
    1157,  1158,  1159,  1160,  1161,  1162,  1166,  1167,  1171,  1175,
    1177,  1178,  1204,  1206,  1207,  1208,  1220,  1228,  1230,  1234,
    1236,  1237,  1240,  1258,  1259,  1263,  1267,  1268,  1272,  1273,
    1277,  1278,  1284,  1304,  1305,  1309,  1310,  1314,  1315,  1319,
    1320,  1324,  1328,  1332,  1333,  1337,  1339,  1349,  1350,  1354,
    1355,  1359,  1361,  1366,  1367,  1368,  1369,  1370,  1371,  1372,
    1376,  1377,  1381,  1385,  1389,  1390,  1394,  1395,  1399,  1403,
    1407,  1408
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

  private static final int yylast_ = 9367;
  private static final int yynnts_ = 73;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 152;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 210;

  private static final int yyuser_token_number_max_ = 464;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */

}


