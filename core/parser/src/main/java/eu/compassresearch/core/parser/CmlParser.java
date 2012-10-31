
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
  public static final short yypact_ninf_ = -664;
  public static final short yypact_[] =
  {
       124,   558,   -83,   -42,   558,   450,   -30,    30,   157,   124,
    -664,  -664,  -664,  -664,  -664,  -664,  -664,  -664,  -664,  -664,
    -664,  -664,  -664,   275,  -664,   194,   -34,   359,  -664,  -664,
     403,   -42,  -664,   223,   420,  -664,  -664,  -664,   636,   427,
    -664,   433,   452,  -664,  -664,   558,   325,   298,  1764,   -83,
    3477,  -664,   116,  -664,   558,  3477,   477,   402,  -664,  -664,
    -664,     7,  -664,  -664,  -664,  -664,  -664,  -664,  -664,  -664,
    -664,  -664,  -664,  -664,  -664,    43,  -664,   450,   245,   286,
    -664,  1764,  1841,   -34,  -664,  1764,   478,  1764,   332,  1764,
    1764,  1764,  1764,  1764,  -664,  -664,  -664,  -664,  -664,  -664,
    -664,  -664,  -664,   741,   276,  -664,  -664,  -664,  -664,  -664,
    -664,  -664,  3477,  3477,  2933,  1253,  1068,  3477,  3477,  3477,
    3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,
    3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,   507,
     517,  1253,    96,   526,   550,  1253,  1253,  1253,  3477,   556,
    -664,   485,  -664, 11881,  -664,  -664,  -664,  -664,  -664,  -664,
    -664,  -664,  -664,  -664,  1253,  -664,  1764,   413,  -664,  6014,
    1253,  1253,  -664,  3477,  1764,  3477,  1764,  -664,   429,   492,
     439,   439,   439,   298,   439,   439,  3477,  1103,   319,  2095,
      87,  -664,  1071,   295,   741,   560,  -664,   671,  1764,  1764,
     773,   585,   874,   919,   588,   588,   588,  1764,  1764,  1764,
    1764,   451,   468,  6136,    14,  6259,  -664,   265,  5156,   279,
     611,  -664,  -664,   -53,  -664,  -664,   639,  -664,  -664,    54,
    -664,  -664,    40,  -664,  6381,  6503,   437,   437,   437,   437,
   12332,   437,   437,   437,   437,   437,   437,   437,   437,   437,
     437,   437,   437,   437,   437,  2947,  3477,  3477,   514,  -664,
      23,  3477,   656,  3477,  3477,  -664,   253,    38,  -664,  -664,
    -664,    39,   672,   -90,  6626,  3477,  -664,  3477,  3477,  -664,
    3477,  3477,  3477,    15,   564,   527,  3477,  3477,  3477,  3477,
    3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,
    3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,  3477,
    3477,  3477,  3477,  3477,  3477,  1253,   808,     5,   344,   741,
     529,   563,    -5,  -664,   575,   129, 11881,   627, 11881,   689,
     559,   558,   657,   102,   566,   492,  -664,  -664,  -664,  -664,
    -664,   308,   101,  -664,  -664,   430,   463,   542,   548,   549,
    6748,   552,   567,  2487,  1133,  -664,  -664,  3477,  -664,   391,
     391,   391,  3477,   391,   391,  3477,  3477,  3477,   391,   391,
    3477,  3477,  3477,  -664,   391,   298,  1253,  -664,  1764,  1764,
    -664,  -664,  -664,   741,   741,  -664,  1841,  1764,  1764,   741,
     588,   588,   741,  -664,  -664,  -664,  -664,  3477,  1253,  -664,
    1253,  3477,  3477,  -664,  3477,  1253,  3477,  1764,  3477,  1764,
    3477,  1068,  3477,  1253,   130, 11881,   178,  3477,  1253,  6870,
    3477,   200,  6992,  3477,  1253,  1764,  3477,  1253,  3477,  3477,
    -664,  1253,  7114, 12382,   202,  7236,  2511,  4958,  4958,  -664,
    -664,  -664,  -664,  2511,  2511,  2511,  2511, 12382, 12382, 12382,
   12382, 12382, 12205, 12255, 12125, 12003, 12382, 12382, 12382, 12382,
    3751,  3751,  2947,  2337,  1721,  1721,  2947,  2947,   437,   437,
     277,   294,  -664,  -664,  -664,  -664,  -664,  1253,  1764,   518,
    1764,  3477,   576,   694,  -664,  1253,  -664,  3477,  3477,   492,
     577,   558,  -664,  -664,  -664,  -664,  3477,   584,   733,  -664,
    -664,  -664,  3477,   439,   391,   439,   391,   391,   391,     1,
     391,   439,   755,   -19,   757,   595,   439,   439,   439,  2313,
    3069,  -664,  -664,  -664,  -664,  3477,   439,   439,  1068,  3477,
    3477,   769,  3205,   298,   298,   298,  3477,  3477,  3477,  3477,
     431,  3477,  1303,   571,  -664,  5278,  -664,  -664,  -664,  -664,
    -664,  -664,  2487,  -664,   204,   539,   812,   622,  3467,  4540,
    1504,   354,  7358,   462,  7480,  4662,   261,   171, 11881, 11881,
   11881,  2095,  -664,   745,   741,   741,   889,   588,   588, 11881,
     674,   356,    80,  7604, 11881,  7726,  -664,    89, 12382,   741,
   12003, 11881,  -664,  5401,   158,  -664,    12,  -664,  -664, 12003,
    -664,  1764,  7848,  -664,  3477, 11881,  -664,   741, 12003,  -664,
   12003, 12003,    78,  -664,  -664,  3477,   372,   741,  1253,  -664,
      99,   741, 11881,   648,  3477,  -664,   777, 11881, 11881,   625,
     125,  -664, 11881,   652,  -664,   102, 11881,  -664,  -664,  -664,
    -664,  -664,  -664,  2024,  3477,  -664,  -664,   572,  3477,   623,
     793,  2661,   794,   579,   607,   610,   584,   251,  3039,   634,
    5033,   491,   760,  -664,   790, 11881,   646,   661,   247,  5523,
      -3,  7970,  3477,  -664, 11881,  -664,  -664,  -664,  8092,  8214,
      88,  8336,    -2,  1253,   800,   280,   765,  3807,  2487,  2487,
    2487,  3477,  2487,  2487,  3477,  3477,  3477,  2487,  2487,  3477,
    3477,  3477,  -664,  -664,  2487,   412,  2487,  3341,  2487,  2753,
    -664,  3477,   391,   391,  -664,  3477,  3477,   391,  3477,  -664,
    3477,  -664,  3477,  -664,  -664,  3477,  -664,  3477,  3477,  -664,
     324,  -664,  3477,   743,  -664,  8458,  3477,  -664,  8580,  1764,
     287,  1253,  2797,  1764, 11881,  -664,  -664,  1880,   413,  1764,
    -664,   813,  8702,   391, 11881,   817,  3477,  -664,  3583,   663,
    2661,  2487,  2487,   811,   127,  -664,  -664,  2487,  -664,  -664,
     491,  -664,  3477,   694,  -664,   819,  2487,  2487,  2487,  2487,
    3477,  -664,  1253,  8824,    24,  -664,   439,   439,   824,  -664,
     725,  3477,  3477,  3477,  2487,  1387,    62,  1482,  4418,  1007,
     417,  5646,  8947,  4784,   712,   312, 11881, 11881, 11881,  3583,
    -664,  -664,  3477, 11881,   417,  -664,  9069,   171,  1504,  9191,
    9314,   261, 11881,  9436,  9560,  9684,  9806, 12003,   820,  -664,
   11881,  -664,  -664,  9928,  -664,   741,  -664,   293,  -664,  -664,
     760, 11881,   741,   328,   522,   668,   795,   703,   660,  3477,
     391,  -664,  3477, 11881,  2487,  -664,  -664,  -664,  3477,  2487,
     584,  3297,  -664,   380,  -664,  3477,  -664,  3583,  5278,  3583,
    1594,  4906, 10050,   215,  -664,    13,  3477,  -664,  3477,   667,
     680,  2487,  3477, 10172,  3929,  4051,  3583,  3477,  3477,  2487,
    3477,  2487,  3477,  2487, 11881,   391,  3477,  1253,  -664,  -664,
    -664,  3477,  3477,  -664,  -664,   694,  1928,  1957,   518,   760,
    3477,  3477, 10294,  -664, 11881,  3583, 10416,  3447,  -664,   836,
    1764,  5769,  3477,  2487,   655,  2487,   850,  -664,  2487,  -664,
     837,  2487,  3583,   339,  4173,  3477,  2487,  2487, 10538, 10660,
     312,  5892,  1007, 10782,   712,  1504, 11881,   126,  5401,  3563,
     805,  -664,   478,   741,   478,   741,   100,   694, 11881, 11881,
    -664,  2487,  -664,  3477,   741,  2487,  3477, 10904,  3583,  3477,
    2487,  3583,   831,  -664,   164,  3583,  5278,  3477,  -664,  -664,
    2487,  2487,  3685,  3583,  3583,  3477,  2487,  3477,  2487,  2487,
    3477,  -664,  -664,  -664,  3477,  -664,  2139,  -664,  -664,   346,
    -664, 11026,  2487, 11148,  3583,  2487,  2487, 11270,  3583,  3583,
    2487,  3477, 11393,  1007, 11515,  1007,  -664, 11637, 11881,  -664,
    -664,  3583,   760,  -664,  2487,  3583,  2487,  2889,  2487,  3583,
    4295,  3477,  2487,  -664,   694,  -664,  3583,  -664,  -664,  2487,
   11759,  1007,  -664,  3583,  2487,  1007
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,   140,   113,   124,   201,   191,     0,     0,     0,     2,
       3,     5,     6,     7,     8,     9,   129,   130,   131,   149,
     150,   151,   152,   141,   143,     0,   118,   114,   116,   119,
       0,   125,   126,     0,   202,   204,   206,   207,     0,   192,
     194,     0,     0,     1,     4,   142,     0,     0,     0,   115,
       0,   127,     0,   209,   203,     0,     0,     0,   324,   327,
     326,     0,   321,   320,   322,   323,   328,   325,   196,   447,
     449,   448,   446,   450,   451,     0,   438,   193,     0,     0,
     144,     0,     0,     0,   123,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   168,   171,   172,   173,   174,   175,
     176,   177,   178,   122,   167,   155,   166,   179,   180,   156,
     117,   301,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     305,   274,   273,   128,   267,   269,   268,   266,   270,   271,
     288,   289,   290,   291,     0,   217,     0,     0,   205,     0,
       0,     0,   440,     0,     0,     0,     0,   195,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    31,    12,
       0,   120,   146,   168,   187,   147,   185,     0,     0,     0,
       0,     0,     0,     0,   162,   163,   161,     0,     0,     0,
       0,     0,     0,     0,     0,   264,   296,     0,   264,     0,
     394,   445,   444,     0,   437,   436,     0,   453,   454,     0,
     331,   332,     0,   329,     0,     0,   341,   342,   343,   344,
     345,   346,   347,   348,   349,   350,   351,   352,   353,   354,
     355,   356,   357,   358,   359,   360,     0,     0,     0,   463,
       0,     0,     0,     0,     0,   442,     0,     0,   457,   459,
     460,     0,     0,     0,   264,     0,   275,     0,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     166,     0,   226,   452,     0,     0,   199,     0,   200,     0,
       0,   233,   153,   257,     0,   132,   134,   135,   136,   137,
     138,     0,     0,    43,    42,     0,     0,     0,     0,     0,
       0,    52,     0,     0,     0,    48,    50,     0,    32,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    34,     0,     0,     0,   145,     0,     0,
     186,   148,   154,   184,   182,   160,     0,     0,     0,   183,
     158,   159,   181,   169,   170,   272,   302,     0,     0,   297,
       0,     0,     0,   306,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   264,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     317,     0,     0,   372,     0,   264,   362,   361,   363,   276,
     282,   278,   277,   365,   364,   366,   367,   368,   369,   370,
     371,   373,   374,   375,   376,   377,   378,   379,   380,   381,
     382,   383,   384,   385,   386,   387,   388,   389,   390,   391,
     392,   393,   285,   286,   283,   284,   218,     0,     0,     0,
       0,     0,     0,     0,   225,     0,   441,     0,     0,     0,
       0,   234,   235,   238,   237,   139,     0,     0,   258,   260,
      10,   133,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    53,     0,    51,     0,   301,     0,     0,
     249,    61,    62,    63,    64,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   103,     0,   100,    98,    99,   400,
     397,   406,     0,    49,     0,    31,    18,    17,    22,     0,
      21,    16,     0,     0,     0,     0,    23,    25,    29,    28,
      27,    13,   121,     0,   189,   188,     0,   165,   164,   265,
       0,     0,     0,     0,   396,     0,   395,     0,   455,   456,
     295,   287,   330,     0,     0,   338,     0,   309,   310,   311,
     464,     0,     0,   315,     0,   461,   443,   462,   292,   458,
     293,   294,     0,   312,   280,     0,     0,   219,     0,   211,
       0,   223,   227,     0,     0,   208,   443,   197,   198,     0,
       0,   236,   263,     0,   262,   259,    46,    47,    37,    45,
      44,    36,    41,     0,     0,    40,    35,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   274,     0,     0,
       0,     0,   226,   248,     0,    65,     0,     0,     0,     0,
       0,     0,     0,   403,   404,   107,   106,   105,     0,     0,
       0,     0,     0,     0,   444,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    14,    78,     0,     0,     0,     0,     0,     0,
      33,     0,     0,     0,   108,     0,     0,     0,     0,   157,
       0,   303,     0,   298,   300,     0,   307,     0,     0,   333,
       0,   336,     0,     0,   313,     0,     0,   318,     0,     0,
       0,     0,     0,     0,   230,   439,    11,     0,   221,     0,
     261,     0,     0,     0,    56,     0,     0,    54,    59,     0,
       0,     0,     0,     0,     0,   418,   416,     0,   255,   256,
     250,   251,     0,     0,   402,     0,     0,     0,     0,     0,
       0,   398,     0,     0,     0,   423,     0,     0,     0,   399,
       0,     0,     0,     0,     0,    70,    69,    81,     0,    83,
      68,     0,     0,     0,    71,    73,    77,    76,    75,    88,
     104,    66,     0,   425,    67,    15,     0,    26,    19,     0,
     111,    24,   190,     0,     0,     0,     0,   334,     0,   339,
     340,   314,   316,     0,   281,   220,   212,     0,   215,   216,
     226,   214,   224,     0,     0,     0,   249,   222,   420,     0,
       0,    38,     0,    57,     0,    55,    92,    91,     0,     0,
       0,     0,   252,   253,   435,     0,    90,   415,   287,   413,
       0,     0,     0,     0,   432,     0,     0,   401,     0,     0,
       0,     0,     0,     0,     0,     0,   412,     0,     0,     0,
       0,     0,     0,     0,   405,     0,     0,     0,   304,   299,
     308,     0,     0,   319,   213,   229,     0,     0,     0,   226,
       0,     0,     0,    39,    58,    60,     0,     0,   419,     0,
       0,     0,     0,     0,     0,     0,     0,   430,     0,   424,
       0,     0,   101,     0,     0,     0,     0,     0,     0,     0,
      74,     0,    87,     0,    72,    20,   112,     0,     0,     0,
     232,   228,   244,   242,   243,   241,     0,     0,   422,   421,
      30,     0,   417,     0,   254,     0,     0,     0,   427,     0,
       0,   414,     0,   433,     0,   434,   340,     0,    93,    79,
       0,     0,     0,   408,   407,     0,     0,     0,     0,     0,
       0,   109,   335,   337,     0,   210,     0,   240,    94,     0,
      97,     0,     0,     0,   426,     0,     0,     0,   102,   409,
       0,     0,     0,    85,     0,    82,    80,     0,   231,   246,
     247,   245,   226,    89,     0,   428,     0,     0,     0,   410,
       0,     0,     0,   110,   229,    96,   429,   431,    95,     0,
       0,    86,   239,   411,     0,    84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -664,  -664,  -664,   855,  -664,  -664,  -181,   -76,   185,  -664,
     511,  -664,  -664,   106,   907,  -664,  -664,   163,   534,  -664,
    -664,  -664,   821,   -72,    11,  -664,  -664,   844,  -664,  -313,
    -177,   153,  -664,   832,    49,   -44,  -664,   737,  -142,  -664,
    -664,   494,  -193,   686,   387,  -664,   807,   848,   472,  -664,
     828,  -664,  -664,   856,   -18,  -664,   262,  -664,  -664,   146,
    -632,  -664,  -132,  -480,  -664,  -664,  -664,  -301,  -664,  -664,
    -664,  -664,    57,  -664,  -664,   134,  -664,  -664,  -664,   272,
     -89,   -50,   643,   882,   988,  1093,  1362,   806,   382,   503,
    -664,   -25,  -664,  -664,   195,  -664,  -664,  -664,   530,  -664,
     394,  -664,  -664,  -664,  -648,  -664,  -663,  -664,  -664,  -664,
    -664,    10,  -664,   -23,   -27,  -144,  -664,  -179,  -146,  -277,
    -135,  -134,   516,  -664,  -664,  -664
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   189,   342,   343,   354,
     355,   515,   513,   757,   758,   933,   543,   544,   703,   563,
      13,    27,    28,   759,   344,    14,    31,    32,    15,   334,
     335,   336,    23,    24,   490,   194,   104,   105,   106,   107,
     108,   195,   196,   377,   337,    39,    40,   230,   338,    34,
      35,    36,    37,   231,   620,   840,   167,   317,   846,   322,
     483,   484,   950,   951,   995,   339,   491,   492,   493,   494,
     845,  1022,   662,   663,   770,   771,   772,   340,   498,   499,
     214,   545,   154,   155,   156,   157,   158,   159,   232,   233,
     160,   729,   161,   594,   595,   162,   163,   219,   220,   546,
     670,   547,   548,   764,   634,   784,   549,   550,   924,   551,
     873,   874,   664,   265,   224,   266,   225,    76,   226,   227,
     228,   267,   268,   269,   270,   260
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -154;
  public static final short
  yytable_[] =
  {
       153,   272,   380,   625,   103,   169,   259,   190,   765,   785,
     356,    75,   271,    29,   649,   180,   181,   182,   183,   173,
     318,   644,   501,   476,   320,   217,   324,   325,   780,   780,
     773,   495,    55,   184,   185,   396,   417,   192,   473,   732,
     928,   197,   877,   200,   481,   202,   203,   204,   205,   206,
      25,   426,   428,    33,    38,   175,   397,   878,    84,   273,
      29,   430,   213,   215,   218,   406,   173,   234,   235,   236,
     237,   238,   239,   240,   241,   242,   243,   244,   245,   246,
     247,   248,   249,   250,   251,   252,   253,   254,   255,    75,
     191,   736,   223,   722,    25,   693,   407,    26,   274,   410,
     374,   788,   725,    33,   440,   345,   346,    58,   348,   349,
     723,   347,    47,   261,   504,    48,   741,   741,   258,   726,
     375,   581,   319,   326,   223,   328,    38,     1,     2,     3,
     327,     4,   329,   164,   505,   165,   350,   475,    30,   990,
     859,   482,   164,   186,   165,   742,   996,   486,   597,   496,
      41,   477,    59,    16,   383,   384,   174,    43,   424,   424,
     397,   650,    16,   389,   390,   391,   392,   414,   416,   418,
     781,   789,     5,   187,   421,    60,   629,   356,   556,   557,
     558,   555,   560,   561,   427,   427,   411,   566,   567,   434,
     631,  1006,   176,   571,   191,   439,   598,    62,    63,    64,
      65,    66,    67,   409,   364,   366,   415,   415,   905,   695,
      42,   419,   918,   415,   422,   929,   696,   697,   603,   698,
     614,   699,   710,   700,   427,   432,   427,   433,   435,   737,
     436,   437,   438,   701,   397,   427,   443,   444,   445,   446,
     447,   448,   449,   450,   451,   452,   453,   454,   455,   456,
     457,   458,   459,   460,   461,   462,   463,   464,   465,   466,
     467,   468,   469,   470,   471,   166,   582,   320,   554,   596,
     991,   587,   427,   860,   747,   424,   397,   957,    19,    20,
      21,    22,  -153,   600,    95,    96,    97,    98,    99,   100,
     101,   102,   472,   864,   364,   399,   279,   612,     6,     7,
     180,   181,   182,   183,   730,   836,   777,   415,    45,   403,
     424,   904,   559,   279,   366,   562,   564,   565,   184,   185,
     568,   569,   570,   638,   397,   641,   642,   643,   645,   646,
     370,   731,   371,   616,   574,   575,   357,    81,   358,   792,
     378,    55,   372,   577,   578,   693,   397,   579,   397,   276,
     397,   583,   584,   573,   585,   198,   588,   979,   590,    82,
     591,   926,   593,   589,  1023,   327,   211,   599,   284,   720,
     602,   423,   199,   605,    46,   580,   608,   721,   610,   611,
     212,   607,   497,   380,    75,   284,   572,    17,   927,   828,
    1034,   221,    49,   411,   686,   258,    17,    47,   406,   424,
      48,   606,   425,    52,   366,   180,   181,   182,   183,   313,
     314,   397,    56,    57,   178,    50,    58,   179,   369,   171,
     370,   172,   371,   184,   185,   404,   502,   314,   186,   407,
     906,   622,   372,   424,   617,   647,   621,   627,   628,   424,
     653,   654,   655,   506,   379,    78,   632,   659,    55,   680,
     666,   667,   636,    54,   503,   366,   279,    48,   187,   695,
      77,    59,   626,   505,    79,    47,   188,  -153,    48,   660,
     215,   699,    18,   700,   740,   665,   507,   997,    83,   669,
     671,    18,   674,   701,    60,   980,   678,   679,   415,   681,
     424,   687,   397,   478,   170,     1,   505,   366,   221,     4,
     331,    75,   332,   333,   222,   198,    62,    63,    64,    65,
      66,    67,   201,   370,    84,   371,   640,   685,   424,    56,
      57,   739,   199,    58,   256,   372,   397,   283,   284,   920,
     191,   817,   818,   186,   257,   618,   821,   619,  -153,  -153,
       5,   285,  -153,   263,   675,   676,   677,   768,   769,   207,
     533,   534,   535,   208,   735,   508,   357,   733,   358,   209,
     366,   509,   510,   187,   695,   738,   210,   264,    59,   313,
     314,   555,   851,   275,   744,   375,   699,    85,   700,    86,
      87,   505,   505,   276,   704,   753,   596,  -153,   701,   315,
     316,    60,   761,   321,   752,    88,   683,   837,   754,    89,
      90,    91,    92,    93,   705,   505,   714,   376,   715,   330,
    -153,   684,   505,    62,    63,    64,    65,    66,    67,   341,
     762,   386,   783,   763,   907,   209,    19,    20,    21,    22,
    -153,   393,  -153,  -153,  -153,  -153,  -153,  -153,   875,   487,
     505,   798,   405,   505,   801,   802,   803,   767,   394,   806,
     807,   808,   408,    55,   207,   364,   441,   813,   208,   775,
     790,   816,   191,   407,   209,   819,   820,   375,   822,   913,
     823,   210,   824,   420,   776,   825,   854,   826,   827,   505,
     930,    69,   830,   863,   497,   429,   833,   207,   637,   382,
     639,   208,   841,   931,   505,   835,   375,   209,   207,   842,
     505,   488,   208,   844,   210,   848,   853,   442,   209,   479,
     879,   880,   480,   505,   945,   210,   207,   969,   970,   910,
     208,   485,   415,   373,    56,    57,   209,   868,    58,   871,
     872,   489,   512,   210,    19,    20,    21,    22,  -153,   500,
     193,   883,   884,   885,   624,   693,    66,   514,    95,    96,
      97,    98,    99,   100,   101,   102,   623,   630,    69,    69,
     911,   831,   894,   947,   633,   366,   635,   648,   207,   651,
     207,   191,   208,    59,   208,   652,   367,   368,   209,   369,
     209,   370,   974,   371,    69,   210,   672,   210,    69,    69,
      69,   718,   406,   372,   385,   745,    60,   743,   746,   912,
     207,   749,   914,   755,   208,   756,   760,    69,   916,   481,
     209,   774,   791,    69,    69,   921,    61,   210,    62,    63,
      64,    65,    66,    67,   793,    55,   813,   360,   783,   852,
     849,   858,   934,    19,    20,    21,    22,   938,   939,   865,
     941,   881,   943,   882,    74,   364,   946,   902,   908,   482,
     661,   948,   949,   963,   109,   366,   994,   977,  1005,   695,
     958,   959,   953,   955,    44,   553,   855,    55,   810,   698,
     110,   699,   967,   700,   999,    51,   964,    80,   976,   262,
     576,   381,   168,   701,   177,   982,    68,   109,   109,    53,
     956,   109,   748,   109,   847,   109,   109,   109,   109,   109,
      58,   207,  1042,   909,   862,   208,    85,   750,    86,    87,
     668,   209,   387,   415,   592,   972,  1001,   221,   210,  1003,
      70,    74,    74,   992,    88,   829,    69,  1007,    89,    90,
      91,    92,    93,   682,   586,  1012,   973,  1014,    56,    57,
    1017,     0,    58,   609,  1018,    59,   207,    74,     0,     0,
     208,    74,    74,    74,     0,   366,   209,   388,    69,    69,
       0,  1030,     0,   210,     0,     0,   367,   368,    60,   369,
      74,   370,   109,   371,     0,     0,    74,    74,     0,     0,
     109,  1040,   109,   372,     0,     0,     0,    59,   474,     0,
      62,    63,    64,    65,    66,    67,     0,    70,    70,     0,
       0,   109,     0,     0,   109,   109,     0,     0,     0,     0,
      60,     0,     0,   109,   109,   109,   109,     0,     0,    69,
       0,   688,   689,    70,     0,     0,    71,    70,    70,    70,
     222,     0,    62,    63,    64,    65,    66,    67,     0,     0,
     693,    69,     0,    69,     0,     0,    70,     0,    69,     0,
       0,     0,    70,    70,    69,     0,    69,     0,     0,     0,
       0,    69,   719,     0,     0,     0,     0,    69,     0,   193,
      69,     0,     0,     0,    69,    66,     0,    95,    96,    97,
      98,    99,   100,   101,   102,    55,     0,     0,     0,    74,
     373,   373,   373,     0,   373,   373,     0,     0,   207,     0,
     373,   373,   208,    71,    71,   373,     1,     0,   209,   351,
       4,   331,   352,   332,   333,   210,   353,     0,   376,     0,
      69,    74,    74,     0,     0,     0,     0,     0,    69,    71,
       0,    72,     0,    71,    71,    71,     1,     0,     0,   351,
       4,   331,   352,   332,   333,     0,   552,     0,     0,     0,
     366,     5,    71,     0,   695,     0,    56,    57,    71,    71,
      58,   696,   697,     0,   698,    70,   699,     0,   700,     0,
       0,    69,   373,     0,     0,   373,   373,   373,   701,   373,
     373,     5,    74,    69,   109,   109,     0,     0,     0,     0,
       0,     0,   109,   109,   109,     0,     0,    70,    70,     0,
       0,     0,     0,     0,    74,    59,    74,     0,    72,    72,
       0,    74,     0,   109,     0,   109,     0,    74,     0,    74,
       0,     0,     0,     0,    74,     0,     0,     0,    60,     0,
      74,   109,     0,    74,    72,     0,     0,    74,    72,    72,
      72,     0,     0,     0,     0,     0,     0,     0,   229,     0,
      62,    63,    64,    65,    66,    67,     0,    72,    70,     0,
     542,    69,     0,    72,    72,     0,     0,     0,     0,     0,
      55,    71,     0,     0,     0,     0,     0,     0,     0,     0,
      70,     0,    70,    74,   109,     0,   109,    70,     0,     0,
       0,    74,     0,    70,     0,    70,     0,     0,     0,     0,
      70,     0,     0,    71,    71,     0,    70,     0,     0,    70,
       0,     0,     0,    70,     0,     0,     0,   688,   689,   690,
     221,     0,     0,   691,     0,     0,    69,     0,     0,     0,
       0,     0,     0,     0,    74,   692,   693,     0,     0,     0,
       0,    56,    57,     0,     0,    58,    74,     0,     0,     0,
       0,   373,   373,     0,     0,   373,     0,     0,     0,    70,
       0,     0,     0,     0,    71,     0,     0,    70,     0,     0,
       0,     0,     0,    69,     0,     0,    72,     0,     0,     0,
       0,     0,   109,     0,    69,   373,    71,     0,    71,     0,
      59,     0,     0,    71,     0,     0,     0,     0,     0,    71,
      73,    71,   689,     0,     0,     0,    71,   109,    72,    72,
      70,     0,    71,    60,     0,    71,     0,     0,     0,    71,
     693,     0,    70,     0,    74,    69,   658,     0,     0,     0,
       0,     0,     0,   222,     0,    62,    63,    64,    65,    66,
      67,     0,     0,     0,     0,   694,   366,   373,     0,     0,
     695,     0,     0,     0,     0,     0,     0,   696,   697,   709,
     698,     0,   699,     0,   700,    71,     0,     0,     0,    72,
       0,     0,     0,    71,   701,     0,   702,    73,    73,   373,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    74,
       0,    72,     0,    72,     0,     0,   688,   689,    72,     0,
      70,     0,   691,    73,    72,     0,    72,    73,    73,    73,
       0,    72,     0,     0,   692,   693,    71,    72,   359,   360,
      72,     0,     0,     0,    72,     0,    73,     0,    71,     0,
     366,     0,    73,    73,   695,     0,    74,   364,     0,     0,
      69,   696,   697,     0,   698,   109,   699,    74,   700,   109,
       0,     0,     0,   109,     0,   109,     0,     0,   701,     0,
       0,     0,     0,     0,     0,    70,     0,     0,     0,    69,
      72,     0,     0,     0,     0,     0,     0,     0,    72,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    74,     0,
       0,     0,     0,     0,     0,   795,   796,   797,     0,   799,
     800,     0,     0,     0,   804,   805,    71,     0,   688,   689,
     690,   809,    70,   811,   691,   814,     0,     0,     0,     0,
       0,    72,     0,    70,   694,   366,   692,   693,     0,   695,
       0,     0,     0,    72,     0,     0,   696,   697,     0,   698,
       0,   699,     0,   700,     0,    73,     0,   366,     0,     0,
       0,     0,     0,   701,     0,     0,   922,   923,   367,   368,
       0,   369,     0,   370,    70,   371,     0,     0,   856,   857,
       0,    71,     0,     0,   861,   372,     0,    73,    73,     0,
       0,     0,     0,   866,   867,   869,   870,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   886,     0,    74,     0,     0,     0,     0,     0,     0,
       0,    72,   109,   109,     0,     0,     0,     0,    71,     0,
       0,     0,     0,     0,     0,     0,   109,     0,     0,    71,
       0,     0,    74,     0,     0,     0,   694,   366,    73,     0,
     279,   695,     0,     0,     0,     0,     0,     0,   696,   697,
       0,   698,     0,   699,     0,   700,     0,     0,   280,     0,
      73,   915,    73,     0,     0,   701,   917,    73,     0,     0,
      71,     0,     0,    73,     0,    73,    72,     0,     0,    70,
      73,    85,     0,    86,    87,     0,    73,     0,   932,    73,
       0,     0,     0,    73,     0,     0,   940,     0,   942,    88,
     944,     0,     0,    89,    90,    91,    92,    93,    70,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    72,     0,   285,   286,   287,   288,   289,
     968,     0,   971,     0,    72,   975,     0,     0,   978,    73,
       0,     0,     0,   983,   984,   305,   306,    73,     0,   309,
     310,   311,   312,   313,   314,     0,     0,     0,    85,     0,
      86,    87,     0,     0,     0,     0,     0,     0,   998,     0,
       0,     0,  1000,   315,   316,    72,    88,  1004,     0,     0,
      89,    90,    91,    92,    93,    71,     0,  1008,  1009,     0,
      73,     0,     0,  1013,     0,  1015,  1016,    85,     0,   843,
      87,     0,    73,  1021,     0,     0,     0,     0,     0,  1025,
       0,     0,  1027,   975,    71,    88,     0,  1029,     0,    89,
      90,    91,    92,    93,     0,     0,     0,     0,     0,     0,
       0,  1035,     0,  1036,     0,  1038,     0,     0,     0,  1041,
       0,     0,     0,     0,    94,    85,  1043,   952,    87,     0,
      66,  1045,    95,    96,    97,    98,    99,   100,   101,   102,
       0,     0,     0,    88,     0,     0,     0,    89,    90,    91,
      92,    93,     0,     0,    85,     0,   954,    87,     0,     0,
      73,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      72,     0,    88,     0,     0,     0,    89,    90,    91,    92,
      93,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    72,
       0,   193,     0,     0,     0,     0,     0,    66,     0,    95,
      96,    97,    98,    99,   100,   101,   102,     0,   359,   360,
     361,     0,   751,     0,   362,    73,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   363,   364,     0,     0,
      94,     0,     0,     0,     0,     0,    66,     0,    95,    96,
      97,    98,    99,   100,   101,   102,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    73,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    73,     0,     0,     0,     0,    94,   359,
     360,   361,     0,     0,    66,   362,    95,    96,    97,    98,
      99,   100,   101,   102,     0,     0,     0,   363,   364,     0,
       0,     0,     0,     0,     0,     0,     0,    94,     0,     0,
       0,     0,     0,    66,    73,    95,    96,    97,    98,    99,
     100,   101,   102,   516,   517,   518,   519,     0,     0,   520,
       0,   521,   522,   523,   524,   525,   365,   366,   114,     0,
       0,   526,   527,     0,     0,     0,     0,     0,   367,   368,
       0,   369,     0,   370,     0,   371,     0,     0,     0,     0,
       0,   115,  1019,  1020,     0,   372,     0,   528,     0,   529,
       0,     0,     0,   530,     0,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,     0,     0,   139,   140,     0,
       0,    58,   141,     0,   142,   143,   144,   365,   366,     0,
     531,     0,   532,     0,     0,     0,     0,     0,     0,   367,
     368,     0,   369,     0,   370,     0,   371,     0,     0,    73,
       0,     0,     0,     0,     0,     0,   372,     0,     0,     0,
       0,     0,     0,   145,   146,   147,    59,   533,   534,   535,
       0,   536,     0,     0,     0,     0,   537,     0,    73,   148,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    60,
       0,   538,   539,   540,     0,     0,   541,   149,     0,   150,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   151,
     152,    62,    63,    64,    65,    66,    67,   516,   517,   518,
     519,     0,     0,   520,     0,   521,   522,   523,   524,   525,
       0,     0,   114,     0,     0,   526,   527,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,   115,     0,     0,     0,     0,
       0,   528,     0,   529,   280,     0,     0,   530,     0,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,     0,
       0,   139,   140,     0,     0,    58,   141,     0,   142,   143,
     144,     0,   656,     0,   531,     0,   532,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   283,   284,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   285,   286,   287,   288,   289,     0,   145,   146,   147,
      59,   533,   534,   535,     0,   536,     0,     0,     0,     0,
     537,   305,     0,   148,     0,   309,   310,   311,   312,   313,
     314,     0,     0,    60,     0,   538,   539,   540,     0,     0,
     541,   149,     0,   150,     0,     0,     0,     0,     0,   315,
     316,     0,     0,   657,   152,    62,    63,    64,    65,    66,
      67,   516,   517,   518,   519,     0,     0,   520,     0,   521,
     522,   523,   524,   525,     0,     0,   114,     0,     0,   526,
     527,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,   115,
       0,     0,     0,     0,     0,   528,     0,   529,     0,     0,
       0,   530,     0,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,     0,     0,   139,   140,     0,     0,    58,
     141,     0,   142,   143,   144,     0,     0,     0,   531,     0,
     532,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   285,     0,     0,     0,     0,
       0,   145,   146,   147,    59,   533,   534,   535,     0,   536,
       0,     0,     0,     0,   537,   305,     0,   148,     0,   309,
     310,   311,   312,   313,   314,     0,     0,    60,     0,   538,
     539,   540,     0,     0,   541,   149,     0,   150,     0,     0,
       0,     0,     0,   315,   316,     0,     0,   151,   152,    62,
      63,    64,    65,    66,    67,   516,   517,   518,   519,     0,
       0,   520,     0,   521,   522,   523,   524,   525,     0,     0,
     114,     0,     0,   526,   527,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   115,     0,     0,     0,     0,     0,   528,
       0,   529,     0,     0,     0,   530,     0,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,     0,     0,   139,
     140,     0,     0,    58,   141,     0,   142,   143,   144,     0,
       0,     0,   531,     0,   532,     0,     0,   688,   689,   690,
       0,     0,     0,   691,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   692,   693,     0,     0,     0,
       0,     0,     0,     0,     0,   145,   146,   147,    59,   533,
     534,   535,     0,   536,     0,     0,     0,     0,   537,     0,
       0,   148,   111,     0,   112,     0,     0,   113,     0,     0,
       0,    60,     0,   538,   539,   540,   114,     0,   541,   149,
       0,   150,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   657,   152,    62,    63,    64,    65,    66,    67,   115,
     838,   839,     0,     0,     0,   116,     0,   117,     0,     0,
       0,   118,     0,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,     0,     0,   139,   140,     0,     0,    58,
     141,     0,   142,   143,   144,   694,   366,     0,     0,     0,
     695,     0,     0,   688,   689,   690,     0,   696,   697,   691,
     698,     0,   699,     0,   700,     0,     0,     0,     0,     0,
       0,   692,   693,     0,   701,     0,   815,     0,     0,     0,
       0,   145,   146,   147,    59,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   148,   111,     0,
     112,     0,     0,   113,     0,     0,     0,    60,     0,     0,
       0,     0,   114,   216,     0,   149,   279,   150,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   151,   152,    62,
      63,    64,    65,    66,    67,   115,     0,     0,     0,     0,
       0,   116,     0,   117,     0,     0,     0,   118,     0,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,     0,
       0,   139,   140,     0,     0,    58,   141,     0,   142,   143,
     144,   694,   366,     0,     0,     0,   695,   283,   284,     0,
       0,     0,     0,   696,   697,     0,   698,     0,   699,     0,
     700,   285,     0,   688,   689,   690,     0,   766,     0,   691,
     701,     0,  1037,     0,     0,     0,     0,   145,   146,   147,
      59,   692,   693,     0,     0,   309,   310,   311,   312,   313,
     314,     0,     0,   148,   111,     0,   112,     0,     0,   113,
       0,     0,     0,    60,     0,     0,     0,     0,   114,   315,
     316,   149,     0,   150,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   151,   152,    62,    63,    64,    65,    66,
      67,   115,     0,     0,   661,     0,     0,   116,     0,   117,
       0,     0,     0,   118,     0,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,     0,     0,   139,   140,     0,
       0,    58,   141,     0,   142,   143,   144,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   694,   366,     0,     0,     0,   695,     0,     0,     0,
       0,     0,     0,   696,   697,     0,   698,     0,   699,     0,
     700,     0,     0,   145,   146,   147,    59,     0,     0,     0,
     701,     0,     0,     0,     0,     0,     0,     0,     0,   148,
     111,     0,   112,     0,   673,   113,     0,     0,     0,    60,
       0,     0,     0,     0,   114,     0,     0,   149,     0,   150,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   151,
     152,    62,    63,    64,    65,    66,    67,   115,     0,     0,
       0,     0,     0,   116,     0,   117,     0,     0,     0,   118,
       0,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,     0,     0,   139,   140,     0,     0,    58,   141,     0,
     142,   143,   144,     0,     0,     0,     0,     0,     0,     0,
       0,   688,   689,   690,     0,   919,     0,   691,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   692,
     693,     0,     0,     0,     0,     0,     0,     0,     0,   145,
     146,   147,    59,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   148,   111,     0,   112,     0,
       0,   113,     0,     0,     0,    60,     0,     0,     0,     0,
     114,     0,     0,   149,     0,   150,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   151,   152,    62,    63,    64,
      65,    66,    67,   115,     0,     0,     0,     0,     0,   116,
       0,   117,     0,     0,     0,   118,     0,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,     0,     0,   139,
     140,     0,     0,    58,   141,   812,   142,   143,   144,   694,
     366,     0,     0,     0,   695,     0,     0,     0,     0,     0,
       0,   696,   697,     0,   698,     0,   699,     0,   700,     0,
       0,   688,   689,   690,     0,   962,     0,   691,   701,     0,
       0,     0,     0,     0,     0,   145,   146,   147,    59,   692,
     693,   359,   360,     0,     0,     0,     0,   362,     0,     0,
       0,   148,   111,     0,   112,     0,     0,   113,     0,   363,
     364,    60,     0,     0,     0,     0,   114,     0,     0,   149,
       0,   150,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   151,   152,    62,    63,    64,    65,    66,    67,   115,
       0,     0,     0,     0,     0,   116,     0,   117,     0,     0,
       0,   118,     0,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,     0,     0,   139,   140,     0,     0,    58,
     141,     0,   142,   143,   144,   277,     0,     0,     0,     0,
     278,     0,   279,     0,     0,     0,     0,     0,     0,   694,
     366,     0,     0,     0,   695,     0,     0,   688,   689,   690,
     280,   696,   697,   691,   698,     0,   699,     0,   700,   365,
     366,   145,   146,   147,    59,   692,   693,     0,   701,     0,
       0,   367,   368,     0,   369,     0,   370,   148,   371,   281,
     282,     0,     0,     0,     0,     0,     0,    60,   372,     0,
       0,     0,     0,     0,     0,   149,     0,   150,     0,     0,
       0,     0,     0,   283,   284,     0,     0,   151,   152,    62,
      63,    64,    65,    66,    67,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,   277,     0,     0,
       0,     0,   278,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   315,   316,     0,     0,     0,
       0,     0,   280,     0,     0,   694,   366,     0,     0,     0,
     695,     0,     0,     0,     0,     0,   993,   696,   697,     0,
     698,     0,   699,     0,   700,     0,     0,     0,     0,     0,
       0,   281,   282,     0,   701,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     279,     0,     0,     0,     0,   283,   284,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,   277,
       0,     0,     0,     0,   278,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   315,   316,     0,
       0,   283,   284,     0,   280,     0,     0,     0,  1010,     0,
       0,  1011,     0,     0,     0,   285,   286,   287,   288,   289,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   281,   282,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   283,   284,     0,
       0,     0,     0,   315,   316,     0,     0,     0,     0,     0,
       0,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,   277,     0,     0,     0,     0,   278,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,   280,     0,     0,     0,
     794,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   281,   282,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   283,
     284,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   314,   277,     0,     0,     0,     0,   278,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   315,   316,     0,     0,     0,     0,     0,   280,     0,
       0,     0,   936,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   281,   282,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,   277,     0,     0,     0,     0,
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316,     0,     0,     0,     0,     0,
     280,     0,     0,     0,   937,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,   277,     0,     0,
       0,     0,   278,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   315,   316,     0,     0,     0,
       0,     0,   280,     0,     0,     0,   981,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   281,   282,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   283,   284,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,     0,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   315,   316,   887,
     888,     0,     0,     0,     0,   280,     0,     0,  1039,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,   711,     0,     0,     0,   889,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316,     0,     0,     0,     0,   712,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,   717,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,   706,     0,     0,   315,   316,     0,     0,
     893,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   727,   728,
       0,     0,   281,   282,     0,     0,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,   707,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
       0,     0,     0,     0,     0,   277,     0,   708,   283,   284,
     278,   395,   279,     0,     0,     0,     0,     0,   315,   316,
     706,     0,   285,   286,   287,   288,   289,     0,     0,     0,
     280,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,     0,     0,     0,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,   707,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,     0,   277,     0,
       0,     0,     0,   278,   708,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   315,   316,   400,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   401,   402,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,   706,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,   707,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,     0,   277,     0,     0,     0,     0,   278,   708,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,     0,   280,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   727,   728,     0,     0,   281,   282,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,   277,     0,     0,     0,     0,
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
     778,     0,     0,   315,   316,     0,     0,     0,     0,     0,
     280,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   779,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,     0,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   315,   316,   890,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
       0,   277,     0,     0,     0,     0,   278,     0,   279,     0,
     965,   891,     0,     0,     0,     0,     0,     0,   315,   316,
     966,     0,     0,     0,     0,     0,   280,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   281,   282,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   283,
     284,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   314,     0,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   315,   316,   987,     0,     0,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,   323,   279,     0,     0,     0,   988,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,   395,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
       0,   277,     0,     0,     0,     0,   278,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
     398,     0,     0,     0,     0,     0,   280,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   281,   282,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   283,
     284,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   314,   277,     0,     0,     0,     0,   278,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   315,   316,     0,     0,     0,     0,     0,   280,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   412,     0,     0,     0,     0,   281,   282,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,   277,     0,     0,     0,     0,
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316,     0,     0,     0,     0,     0,
     280,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,     0,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,   413,     0,     0,   315,   316,   431,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,     0,   279,
       0,     0,     0,   511,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,   601,     0,     0,     0,
       0,     0,   315,   316,     0,     0,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,   613,   279,     0,     0,     0,     0,   604,     0,
       0,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   615,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,     0,   279,
       0,     0,     0,   713,     0,     0,     0,     0,   716,     0,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,     0,     0,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316,   724,     0,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   402,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,   734,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,   782,
       0,     0,   315,   316,     0,     0,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,   786,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,   787,   778,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,   832,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,   834,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,   277,     0,     0,     0,     0,   278,
       0,   279,     0,   850,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316,     0,     0,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,   876,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,     0,   277,
       0,     0,     0,     0,   278,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,   892,     0,
       0,     0,     0,     0,   280,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   281,   282,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   283,   284,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,   277,     0,     0,     0,     0,   278,     0,   279,     0,
     895,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,   280,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   281,   282,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   283,
     284,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   314,   277,     0,     0,     0,     0,   278,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,   896,
       0,   315,   316,     0,     0,     0,     0,     0,   280,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   281,   282,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,     0,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316,   897,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,   898,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
       0,     0,   277,     0,     0,     0,     0,   278,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
     899,     0,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,     0,     0,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316,   900,     0,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   901,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,     0,   279,
       0,     0,     0,     0,     0,     0,     0,   925,     0,   903,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316,     0,     0,     0,     0,     0,   280,
     935,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,   960,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,   961,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
     985,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,     0,   279,
       0,   986,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316,     0,     0,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,     0,     0,
     989,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,  1002,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,  1024,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,  1026,
       0,     0,     0,     0,   281,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,     0,   279,
       0,  1028,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,     0,   277,     0,     0,     0,     0,
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316,  1031,     0,     0,     0,     0,     0,
     280,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,   277,     0,     0,
       0,     0,   278,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   315,   316,     0,     0,     0,
       0,     0,   280,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   281,   282,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   283,   284,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,   277,
       0,     0,     0,     0,   278,     0,   279,     0,     0,     0,
    1032,     0,     0,     0,     0,     0,     0,   315,   316,     0,
       0,     0,     0,     0,   280,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   281,   282,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   283,   284,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,   277,     0,     0,     0,     0,   278,     0,   279,     0,
    1044,  1033,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,   280,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   281,   282,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   283,
     284,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   314,   277,     0,     0,     0,     0,   278,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   315,   316,     0,     0,     0,     0,     0,   280,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   281,   282,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,   277,     0,     0,     0,     0,
       0,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316,     0,     0,     0,     0,     0,
     280,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,   277,     0,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   315,   316,     0,     0,     0,
       0,     0,   280,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   281,   282,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   283,   284,   277,     0,     0,
       0,     0,     0,     0,   279,     0,     0,     0,     0,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   280,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,     0,
       0,     0,     0,     0,     0,     0,     0,   277,     0,     0,
       0,   281,   282,     0,   279,     0,     0,   315,   316,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   280,     0,     0,   283,   284,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   281,   282,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,     0,
       0,     0,     0,     0,   277,   283,   284,     0,     0,     0,
       0,   279,     0,     0,     0,     0,     0,   315,   316,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   280,
     296,     0,     0,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,   279,     0,     0,     0,     0,     0,   315,   316,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,     0,     0,   281,   282,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,   285,   286,   287,   288,
     289,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
        50,   147,   195,   483,    48,    55,   141,    79,   656,   672,
     187,    38,   146,     2,    33,    14,    15,    16,    17,    12,
     164,    20,   335,    18,   166,   114,   170,   171,    31,    31,
     662,   332,    17,    32,    33,    21,    13,    81,   315,    27,
      27,    85,    18,    87,    49,    89,    90,    91,    92,    93,
       1,    13,    13,     4,     5,    12,   146,    33,    47,   148,
      49,   151,   112,   113,   114,   118,    12,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   116,
      79,    13,   115,    13,    45,    33,   149,   180,   148,    59,
      13,    13,    13,    54,   283,   181,   182,    92,   184,   185,
      30,   183,   146,    17,    13,   149,    17,    17,   141,    30,
      33,   398,   166,   173,   147,   175,    77,     3,     4,     5,
     174,     7,   176,    17,    33,    19,   186,   316,   180,    13,
      13,   146,    17,   142,    19,    46,    46,    18,    18,    47,
     180,   146,   137,     0,   198,   199,   149,     0,   146,   146,
     146,   180,     9,   207,   208,   209,   210,   256,   257,   146,
     173,   173,    48,   172,   263,   160,   489,   354,   359,   360,
     361,   180,   363,   364,   146,   146,   146,   368,   369,   278,
     491,    27,   149,   374,   183,   180,    18,   182,   183,   184,
     185,   186,   187,   149,    33,   143,   256,   257,   840,   147,
     180,   261,   860,   263,   264,   878,   154,   155,    18,   157,
      18,   159,    18,   161,   146,   275,   146,   277,   278,   151,
     280,   281,   282,   171,   146,   146,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,   149,   400,   409,   357,   413,
     144,   405,   146,   146,   149,   146,   146,   909,   176,   177,
     178,   179,   180,   418,   188,   189,   190,   191,   192,   193,
     194,   195,   315,   773,    33,    30,    19,   431,   174,   175,
      14,    15,    16,    17,   146,    18,    59,   357,    33,    30,
     146,    18,   362,    19,   143,   365,   366,   367,    32,    33,
     370,   371,   372,   504,   146,   506,   507,   508,   509,   510,
     159,   173,   161,   477,   378,   379,    17,    12,    19,    59,
      45,    17,   171,   387,   388,    33,   146,   397,   146,    98,
     146,   401,   402,   376,   404,    27,   406,    18,   408,    34,
     410,   146,   412,   407,    18,   409,    90,   417,    91,    13,
     420,   118,    44,   423,   180,   398,   426,    21,   428,   429,
     104,   425,   333,   576,   411,    91,   375,     0,   173,    65,
    1022,    67,    33,   146,   540,   418,     9,   146,   118,   146,
     149,   424,   149,   180,   143,    14,    15,    16,    17,   132,
     133,   146,    88,    89,   169,    12,    92,   172,   157,    17,
     159,    19,   161,    32,    33,   146,   118,   133,   142,   149,
     102,   481,   171,   146,   478,   511,   480,   487,   488,   146,
     516,   517,   518,    13,   149,    12,   496,   519,    17,   538,
     526,   527,   502,    33,   146,   143,    19,   149,   172,   147,
      33,   137,   485,    33,    12,   146,   180,    17,   149,   519,
     520,   159,     0,   161,   618,   525,    13,   957,   180,   529,
     530,     9,   532,   171,   160,   146,   536,   537,   538,   539,
     146,   541,   146,   149,    17,     3,    33,   143,    67,     7,
       8,   528,    10,    11,   180,    27,   182,   183,   184,   185,
     186,   187,   180,   159,   503,   161,   505,   540,   146,    88,
      89,   149,    44,    92,    17,   171,   146,    90,    91,   149,
     519,   712,   713,   142,    17,    17,   717,    19,    88,    89,
      48,   104,    92,    17,   533,   534,   535,    56,    57,    27,
     138,   139,   140,    31,   604,    13,    17,   601,    19,    37,
     143,    13,    13,   172,   147,   615,    44,    17,   137,   132,
     133,   180,   753,    17,   624,    33,   159,    17,   161,    19,
      20,    33,    33,    98,    13,    13,   730,   137,   171,   152,
     153,   160,    13,   180,   644,    35,   165,   741,   648,    39,
      40,    41,    42,    43,    33,    33,   144,    47,   146,   180,
     160,   180,    33,   182,   183,   184,   185,   186,   187,   180,
      13,    36,   672,    13,   102,    37,   176,   177,   178,   179,
     180,   180,   182,   183,   184,   185,   186,   187,   782,    12,
      33,   691,    31,    33,   694,   695,   696,    13,   180,   699,
     700,   701,    13,    17,    27,    33,    92,   707,    31,    13,
     683,   711,   651,   149,    37,   715,   716,    33,   718,   850,
     720,    44,   722,    17,    13,   725,    13,   727,   728,    33,
      13,    38,   732,   772,   635,    13,   736,    27,   503,    18,
     505,    31,   742,    13,    33,   739,    33,    37,    27,   743,
      33,    12,    31,   747,    44,   749,   756,   180,    37,   180,
     786,   787,   149,    33,   895,    44,    27,    62,    63,    59,
      31,   146,   772,   189,    88,    89,    37,   777,    92,   779,
     780,   172,   180,    44,   176,   177,   178,   179,   180,   173,
     180,   791,   792,   793,    50,    33,   186,   180,   188,   189,
     190,   191,   192,   193,   194,   195,   180,   180,   115,   116,
     100,    18,   812,   897,   180,   143,    33,    12,    27,    12,
      27,   760,    31,   137,    31,   180,   154,   155,    37,   157,
      37,   159,   926,   161,   141,    44,    17,    44,   145,   146,
     147,    46,   118,   171,    21,    18,   160,   149,   173,   849,
      27,   149,   852,   180,    31,    12,    12,   164,   858,    49,
      37,    21,    12,   170,   171,   865,   180,    44,   182,   183,
     184,   185,   186,   187,    59,    17,   876,    15,   878,    12,
      17,    20,   882,   176,   177,   178,   179,   887,   888,    20,
     890,    17,   892,   118,    38,    33,   896,    27,   180,   146,
      55,   901,   902,    17,    48,   143,    51,    20,    27,   147,
     910,   911,   906,   907,     9,   354,   760,    17,   705,   157,
      49,   159,   922,   161,   963,    31,   920,    45,   928,   142,
     386,   195,    54,   171,    77,   935,    38,    81,    82,    33,
     908,    85,   630,    87,   748,    89,    90,    91,    92,    93,
      92,    27,  1034,   846,   770,    31,    17,   635,    19,    20,
     528,    37,    38,   963,   411,    65,   966,    67,    44,   969,
      38,   115,   116,   948,    35,   730,   283,   977,    39,    40,
      41,    42,    43,   539,   404,   985,   926,   987,    88,    89,
     990,    -1,    92,   427,   994,   137,    27,   141,    -1,    -1,
      31,   145,   146,   147,    -1,   143,    37,    38,   315,   316,
      -1,  1011,    -1,    44,    -1,    -1,   154,   155,   160,   157,
     164,   159,   166,   161,    -1,    -1,   170,   171,    -1,    -1,
     174,  1031,   176,   171,    -1,    -1,    -1,   137,   180,    -1,
     182,   183,   184,   185,   186,   187,    -1,   115,   116,    -1,
      -1,   195,    -1,    -1,   198,   199,    -1,    -1,    -1,    -1,
     160,    -1,    -1,   207,   208,   209,   210,    -1,    -1,   376,
      -1,    14,    15,   141,    -1,    -1,    38,   145,   146,   147,
     180,    -1,   182,   183,   184,   185,   186,   187,    -1,    -1,
      33,   398,    -1,   400,    -1,    -1,   164,    -1,   405,    -1,
      -1,    -1,   170,   171,   411,    -1,   413,    -1,    -1,    -1,
      -1,   418,   173,    -1,    -1,    -1,    -1,   424,    -1,   180,
     427,    -1,    -1,    -1,   431,   186,    -1,   188,   189,   190,
     191,   192,   193,   194,   195,    17,    -1,    -1,    -1,   283,
     556,   557,   558,    -1,   560,   561,    -1,    -1,    27,    -1,
     566,   567,    31,   115,   116,   571,     3,    -1,    37,     6,
       7,     8,     9,    10,    11,    44,    13,    -1,    47,    -1,
     477,   315,   316,    -1,    -1,    -1,    -1,    -1,   485,   141,
      -1,    38,    -1,   145,   146,   147,     3,    -1,    -1,     6,
       7,     8,     9,    10,    11,    -1,    13,    -1,    -1,    -1,
     143,    48,   164,    -1,   147,    -1,    88,    89,   170,   171,
      92,   154,   155,    -1,   157,   283,   159,    -1,   161,    -1,
      -1,   528,   638,    -1,    -1,   641,   642,   643,   171,   645,
     646,    48,   376,   540,   378,   379,    -1,    -1,    -1,    -1,
      -1,    -1,   386,   387,   388,    -1,    -1,   315,   316,    -1,
      -1,    -1,    -1,    -1,   398,   137,   400,    -1,   115,   116,
      -1,   405,    -1,   407,    -1,   409,    -1,   411,    -1,   413,
      -1,    -1,    -1,    -1,   418,    -1,    -1,    -1,   160,    -1,
     424,   425,    -1,   427,   141,    -1,    -1,   431,   145,   146,
     147,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,    -1,
     182,   183,   184,   185,   186,   187,    -1,   164,   376,    -1,
     353,   618,    -1,   170,   171,    -1,    -1,    -1,    -1,    -1,
      17,   283,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     398,    -1,   400,   477,   478,    -1,   480,   405,    -1,    -1,
      -1,   485,    -1,   411,    -1,   413,    -1,    -1,    -1,    -1,
     418,    -1,    -1,   315,   316,    -1,   424,    -1,    -1,   427,
      -1,    -1,    -1,   431,    -1,    -1,    -1,    14,    15,    16,
      67,    -1,    -1,    20,    -1,    -1,   683,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   528,    32,    33,    -1,    -1,    -1,
      -1,    88,    89,    -1,    -1,    92,   540,    -1,    -1,    -1,
      -1,   817,   818,    -1,    -1,   821,    -1,    -1,    -1,   477,
      -1,    -1,    -1,    -1,   376,    -1,    -1,   485,    -1,    -1,
      -1,    -1,    -1,   730,    -1,    -1,   283,    -1,    -1,    -1,
      -1,    -1,   576,    -1,   741,   851,   398,    -1,   400,    -1,
     137,    -1,    -1,   405,    -1,    -1,    -1,    -1,    -1,   411,
      38,   413,    15,    -1,    -1,    -1,   418,   601,   315,   316,
     528,    -1,   424,   160,    -1,   427,    -1,    -1,    -1,   431,
      33,    -1,   540,    -1,   618,   782,   519,    -1,    -1,    -1,
      -1,    -1,    -1,   180,    -1,   182,   183,   184,   185,   186,
     187,    -1,    -1,    -1,    -1,   142,   143,   913,    -1,    -1,
     147,    -1,    -1,    -1,    -1,    -1,    -1,   154,   155,   552,
     157,    -1,   159,    -1,   161,   477,    -1,    -1,    -1,   376,
      -1,    -1,    -1,   485,   171,    -1,   173,   115,   116,   945,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   683,
      -1,   398,    -1,   400,    -1,    -1,    14,    15,   405,    -1,
     618,    -1,    20,   141,   411,    -1,   413,   145,   146,   147,
      -1,   418,    -1,    -1,    32,    33,   528,   424,    14,    15,
     427,    -1,    -1,    -1,   431,    -1,   164,    -1,   540,    -1,
     143,    -1,   170,   171,   147,    -1,   730,    33,    -1,    -1,
     897,   154,   155,    -1,   157,   739,   159,   741,   161,   743,
      -1,    -1,    -1,   747,    -1,   749,    -1,    -1,   171,    -1,
      -1,    -1,    -1,    -1,    -1,   683,    -1,    -1,    -1,   926,
     477,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   485,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   782,    -1,
      -1,    -1,    -1,    -1,    -1,   688,   689,   690,    -1,   692,
     693,    -1,    -1,    -1,   697,   698,   618,    -1,    14,    15,
      16,   704,   730,   706,    20,   708,    -1,    -1,    -1,    -1,
      -1,   528,    -1,   741,   142,   143,    32,    33,    -1,   147,
      -1,    -1,    -1,   540,    -1,    -1,   154,   155,    -1,   157,
      -1,   159,    -1,   161,    -1,   283,    -1,   143,    -1,    -1,
      -1,    -1,    -1,   171,    -1,    -1,    62,    63,   154,   155,
      -1,   157,    -1,   159,   782,   161,    -1,    -1,   761,   762,
      -1,   683,    -1,    -1,   767,   171,    -1,   315,   316,    -1,
      -1,    -1,    -1,   776,   777,   778,   779,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   794,    -1,   897,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   618,   906,   907,    -1,    -1,    -1,    -1,   730,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   920,    -1,    -1,   741,
      -1,    -1,   926,    -1,    -1,    -1,   142,   143,   376,    -1,
      19,   147,    -1,    -1,    -1,    -1,    -1,    -1,   154,   155,
      -1,   157,    -1,   159,    -1,   161,    -1,    -1,    37,    -1,
     398,   854,   400,    -1,    -1,   171,   859,   405,    -1,    -1,
     782,    -1,    -1,   411,    -1,   413,   683,    -1,    -1,   897,
     418,    17,    -1,    19,    20,    -1,   424,    -1,   881,   427,
      -1,    -1,    -1,   431,    -1,    -1,   889,    -1,   891,    35,
     893,    -1,    -1,    39,    40,    41,    42,    43,   926,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   730,    -1,   104,   105,   106,   107,   108,
     923,    -1,   925,    -1,   741,   928,    -1,    -1,   931,   477,
      -1,    -1,    -1,   936,   937,   124,   125,   485,    -1,   128,
     129,   130,   131,   132,   133,    -1,    -1,    -1,    17,    -1,
      19,    20,    -1,    -1,    -1,    -1,    -1,    -1,   961,    -1,
      -1,    -1,   965,   152,   153,   782,    35,   970,    -1,    -1,
      39,    40,    41,    42,    43,   897,    -1,   980,   981,    -1,
     528,    -1,    -1,   986,    -1,   988,   989,    17,    -1,    19,
      20,    -1,   540,   996,    -1,    -1,    -1,    -1,    -1,  1002,
      -1,    -1,  1005,  1006,   926,    35,    -1,  1010,    -1,    39,
      40,    41,    42,    43,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,  1024,    -1,  1026,    -1,  1028,    -1,    -1,    -1,  1032,
      -1,    -1,    -1,    -1,   180,    17,  1039,    19,    20,    -1,
     186,  1044,   188,   189,   190,   191,   192,   193,   194,   195,
      -1,    -1,    -1,    35,    -1,    -1,    -1,    39,    40,    41,
      42,    43,    -1,    -1,    17,    -1,    19,    20,    -1,    -1,
     618,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     897,    -1,    35,    -1,    -1,    -1,    39,    40,    41,    42,
      43,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   926,
      -1,   180,    -1,    -1,    -1,    -1,    -1,   186,    -1,   188,
     189,   190,   191,   192,   193,   194,   195,    -1,    14,    15,
      16,    -1,    18,    -1,    20,   683,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    32,    33,    -1,    -1,
     180,    -1,    -1,    -1,    -1,    -1,   186,    -1,   188,   189,
     190,   191,   192,   193,   194,   195,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   730,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   741,    -1,    -1,    -1,    -1,   180,    14,
      15,    16,    -1,    -1,   186,    20,   188,   189,   190,   191,
     192,   193,   194,   195,    -1,    -1,    -1,    32,    33,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,    -1,    -1,
      -1,    -1,    -1,   186,   782,   188,   189,   190,   191,   192,
     193,   194,   195,    14,    15,    16,    17,    -1,    -1,    20,
      -1,    22,    23,    24,    25,    26,   142,   143,    29,    -1,
      -1,    32,    33,    -1,    -1,    -1,    -1,    -1,   154,   155,
      -1,   157,    -1,   159,    -1,   161,    -1,    -1,    -1,    -1,
      -1,    52,    53,    54,    -1,   171,    -1,    58,    -1,    60,
      -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    -1,    -1,    88,    89,    -1,
      -1,    92,    93,    -1,    95,    96,    97,   142,   143,    -1,
     101,    -1,   103,    -1,    -1,    -1,    -1,    -1,    -1,   154,
     155,    -1,   157,    -1,   159,    -1,   161,    -1,    -1,   897,
      -1,    -1,    -1,    -1,    -1,    -1,   171,    -1,    -1,    -1,
      -1,    -1,    -1,   134,   135,   136,   137,   138,   139,   140,
      -1,   142,    -1,    -1,    -1,    -1,   147,    -1,   926,   150,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   160,
      -1,   162,   163,   164,    -1,    -1,   167,   168,    -1,   170,
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
      97,    -1,    99,    -1,   101,    -1,   103,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,    -1,   134,   135,   136,
     137,   138,   139,   140,    -1,   142,    -1,    -1,    -1,    -1,
     147,   124,    -1,   150,    -1,   128,   129,   130,   131,   132,
     133,    -1,    -1,   160,    -1,   162,   163,   164,    -1,    -1,
     167,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,   180,   181,   182,   183,   184,   185,   186,
     187,    14,    15,    16,    17,    -1,    -1,    20,    -1,    22,
      23,    24,    25,    26,    -1,    -1,    29,    -1,    -1,    32,
      33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    52,
      -1,    -1,    -1,    -1,    -1,    58,    -1,    60,    -1,    -1,
      -1,    64,    -1,    66,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,
      93,    -1,    95,    96,    97,    -1,    -1,    -1,   101,    -1,
     103,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,    -1,    -1,    -1,    -1,
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
      -1,    -1,   101,    -1,   103,    -1,    -1,    14,    15,    16,
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
      17,    -1,    -1,    20,    -1,    -1,    -1,   160,    -1,    -1,
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
     171,    -1,   173,    -1,    -1,    -1,    -1,   134,   135,   136,
     137,    32,    33,    -1,    -1,   128,   129,   130,   131,   132,
     133,    -1,    -1,   150,    15,    -1,    17,    -1,    -1,    20,
      -1,    -1,    -1,   160,    -1,    -1,    -1,    -1,    29,   152,
     153,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   180,   181,   182,   183,   184,   185,   186,
     187,    52,    -1,    -1,    55,    -1,    -1,    58,    -1,    60,
      -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    -1,    -1,    88,    89,    -1,
      -1,    92,    93,    -1,    95,    96,    97,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   142,   143,    -1,    -1,    -1,   147,    -1,    -1,    -1,
      -1,    -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,    -1,    -1,   134,   135,   136,   137,    -1,    -1,    -1,
     171,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   150,
      15,    -1,    17,    -1,    19,    20,    -1,    -1,    -1,   160,
      -1,    -1,    -1,    -1,    29,    -1,    -1,   168,    -1,   170,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,
     181,   182,   183,   184,   185,   186,   187,    52,    -1,    -1,
      -1,    -1,    -1,    58,    -1,    60,    -1,    -1,    -1,    64,
      -1,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,    -1,
      95,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    14,    15,    16,    -1,    18,    -1,    20,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    32,
      33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   134,
     135,   136,   137,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   150,    15,    -1,    17,    -1,
      -1,    20,    -1,    -1,    -1,   160,    -1,    -1,    -1,    -1,
      29,    -1,    -1,   168,    -1,   170,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,
     185,   186,   187,    52,    -1,    -1,    -1,    -1,    -1,    58,
      -1,    60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    -1,    -1,    88,
      89,    -1,    -1,    92,    93,    94,    95,    96,    97,   142,
     143,    -1,    -1,    -1,   147,    -1,    -1,    -1,    -1,    -1,
      -1,   154,   155,    -1,   157,    -1,   159,    -1,   161,    -1,
      -1,    14,    15,    16,    -1,    18,    -1,    20,   171,    -1,
      -1,    -1,    -1,    -1,    -1,   134,   135,   136,   137,    32,
      33,    14,    15,    -1,    -1,    -1,    -1,    20,    -1,    -1,
      -1,   150,    15,    -1,    17,    -1,    -1,    20,    -1,    32,
      33,   160,    -1,    -1,    -1,    -1,    29,    -1,    -1,   168,
      -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   180,   181,   182,   183,   184,   185,   186,   187,    52,
      -1,    -1,    -1,    -1,    -1,    58,    -1,    60,    -1,    -1,
      -1,    64,    -1,    66,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,
      93,    -1,    95,    96,    97,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,   142,
     143,    -1,    -1,    -1,   147,    -1,    -1,    14,    15,    16,
      37,   154,   155,    20,   157,    -1,   159,    -1,   161,   142,
     143,   134,   135,   136,   137,    32,    33,    -1,   171,    -1,
      -1,   154,   155,    -1,   157,    -1,   159,   150,   161,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,   160,   171,    -1,
      -1,    -1,    -1,    -1,    -1,   168,    -1,   170,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,   180,   181,   182,
     183,   184,   185,   186,   187,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,   142,   143,    -1,    -1,    -1,
     147,    -1,    -1,    -1,    -1,    -1,   173,   154,   155,    -1,
     157,    -1,   159,    -1,   161,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,   171,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      19,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    90,    91,    -1,    37,    -1,    -1,    -1,   163,    -1,
      -1,   166,    -1,    -1,    -1,   104,   105,   106,   107,   108,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
     163,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,   163,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      37,    -1,    -1,    -1,   163,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    37,    -1,    -1,    -1,   163,    -1,    -1,    -1,
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
      32,    -1,    -1,    -1,    -1,    37,    -1,    -1,   163,    -1,
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
     152,   153,    32,    -1,    -1,    -1,   158,    37,    -1,    -1,
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
      -1,    -1,   152,   153,    -1,    -1,    -1,    -1,   158,    37,
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
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,   156,    -1,
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
      -1,    -1,    -1,    27,    -1,    -1,   152,   153,    -1,    -1,
     156,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    62,    63,
      -1,    -1,    66,    67,    -1,    -1,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   100,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    -1,    -1,    -1,    -1,    12,    -1,   141,    90,    91,
      17,    18,    19,    -1,    -1,    -1,    -1,    -1,   152,   153,
      27,    -1,   104,   105,   106,   107,   108,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   100,    -1,    -1,    -1,   104,   105,   106,
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
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    27,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    12,    -1,    -1,    -1,    -1,    17,   141,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    62,    63,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      27,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    61,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,
     107,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    -1,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    31,    -1,    -1,
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
      21,   145,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
      -1,    17,    18,    19,    -1,    -1,    -1,   145,    -1,    -1,
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
      -1,    -1,    -1,    17,    18,    19,    -1,    -1,    -1,    -1,
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
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    61,    -1,    -1,    -1,    -1,    66,    67,    -1,
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
     127,   128,   129,   130,   131,   132,   133,    -1,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,   149,    -1,    -1,   152,   153,    31,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
      -1,    19,    -1,    -1,    -1,    -1,   146,    -1,    -1,    -1,
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
      -1,    17,    18,    19,    -1,    -1,    -1,    -1,   146,    -1,
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
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    86,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
      -1,    -1,    -1,   145,    -1,    -1,    -1,    -1,    28,    -1,
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
     130,   131,   132,   133,    -1,    -1,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153,    30,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    87,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    18,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   149,
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
      -1,    17,    -1,    19,    -1,    -1,    -1,   145,    -1,    -1,
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
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,   148,    27,    -1,    -1,   152,   153,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
      -1,    19,    -1,    21,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,   100,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    -1,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    31,    -1,
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
      21,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
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
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    28,
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
      -1,    -1,    -1,    17,    -1,    19,    -1,    21,    -1,    -1,
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
      -1,    -1,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
     130,   131,   132,   133,    -1,    -1,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153,    30,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    61,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,   151,
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
      38,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    18,    19,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    17,    -1,    19,    -1,    21,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
      32,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
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
     148,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
     130,   131,   132,   133,    -1,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153,    31,    -1,    -1,    -1,    -1,    -1,
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
      21,   144,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
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
      -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    90,    91,    12,    -1,    -1,
      -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,    37,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    12,    -1,    -1,
      -1,    66,    67,    -1,    19,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,    66,    67,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      -1,    -1,    -1,    -1,    12,    90,    91,    -1,    -1,    -1,
      -1,    19,    -1,    -1,    -1,    -1,    -1,   152,   153,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,    37,
     115,    -1,    -1,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    19,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,    -1,    -1,    66,    67,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,   104,   105,   106,   107,
     108,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153
  };
  }


  static class YyTokenClass {
  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  public static final short
  yystos_[] =
  {
         0,     3,     4,     5,     7,    48,   174,   175,   212,   213,
     214,   215,   216,   231,   236,   239,   242,   255,   259,   176,
     177,   178,   179,   243,   244,   245,   180,   232,   233,   235,
     180,   237,   238,   245,   260,   261,   262,   263,   245,   256,
     257,   180,   180,     0,   214,    33,   180,   146,   149,    33,
      12,   238,   180,   264,    33,    17,    88,    89,    92,   137,
     160,   180,   182,   183,   184,   185,   186,   187,   258,   293,
     294,   295,   296,   297,   298,   325,   328,    33,    12,    12,
     244,    12,    34,   180,   235,    17,    19,    20,    35,    39,
      40,    41,    42,    43,   180,   188,   189,   190,   191,   192,
     193,   194,   195,   246,   247,   248,   249,   250,   251,   298,
     233,    15,    17,    20,    29,    52,    58,    60,    64,    66,
      67,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    88,
      89,    93,    95,    96,    97,   134,   135,   136,   150,   168,
     170,   180,   181,   292,   293,   294,   295,   296,   297,   298,
     301,   303,   306,   307,    17,    19,   149,   267,   261,   292,
      17,    17,    19,    12,   149,    12,   149,   257,   169,   172,
      14,    15,    16,    17,    32,    33,   142,   172,   180,   217,
     234,   235,   246,   180,   246,   252,   253,   246,    27,    44,
     246,   180,   246,   246,   246,   246,   246,    27,    31,    37,
      44,    90,   104,   292,   291,   292,    30,   291,   292,   308,
     309,    67,   180,   324,   325,   327,   329,   330,   331,   180,
     258,   264,   299,   300,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,    17,    17,   324,   331,
     336,    17,   248,    17,    17,   324,   326,   332,   333,   334,
     335,   332,   329,   291,   292,    17,    98,    12,    17,    19,
      37,    66,    67,    90,    91,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   152,   153,   268,   326,   246,
     249,   180,   270,    18,   326,   326,   292,   246,   292,   246,
     180,     8,    10,    11,   240,   241,   242,   255,   259,   276,
     288,   180,   218,   219,   235,   218,   218,   234,   218,   218,
     292,     6,     9,    13,   220,   221,   241,    17,    19,    14,
      15,    16,    20,    32,    33,   142,   143,   154,   155,   157,
     159,   161,   171,   229,    13,    33,    47,   254,    45,   149,
     253,   254,    18,   246,   246,    21,    36,    38,    38,   246,
     246,   246,   246,   180,   180,    18,    21,   146,    31,    30,
      31,    86,    87,    30,   146,    31,   118,   149,    13,   149,
      59,   146,    61,   149,   291,   292,   291,    13,   146,   292,
      17,   291,   292,   118,   146,   149,    13,   146,    13,    13,
     151,    31,   292,   292,   291,   292,   292,   292,   292,   180,
     328,    92,   180,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   324,   330,   180,   328,    18,   146,   149,   180,
     149,    49,   146,   271,   272,   146,    18,    12,    12,   172,
     245,   277,   278,   279,   280,   278,    47,   245,   289,   290,
     173,   240,   118,   146,    13,    33,    13,    13,    13,    13,
      13,   145,   180,   223,   180,   222,    14,    15,    16,    17,
      20,    22,    23,    24,    25,    26,    32,    33,    58,    60,
      64,   101,   103,   138,   139,   140,   142,   147,   162,   163,
     164,   167,   225,   227,   228,   292,   310,   312,   313,   317,
     318,   320,    13,   221,   291,   180,   217,   217,   217,   292,
     217,   217,   292,   230,   292,   292,   217,   217,   292,   292,
     292,   217,   235,   324,   246,   246,   252,   246,   246,   292,
     324,   330,   332,   292,   292,   292,   309,   332,   292,   246,
     292,   292,   300,   292,   304,   305,   326,    18,    18,   292,
     331,   146,   292,    18,   146,   292,   324,   246,   292,   333,
     292,   292,   332,    18,    18,    86,   326,   246,    17,    19,
     265,   246,   292,   180,    50,   274,   324,   292,   292,   240,
     180,   278,   292,   180,   315,    33,   292,   219,   217,   219,
     235,   217,   217,   217,    20,   217,   217,   218,    12,    33,
     180,    12,   180,   218,   218,   218,    99,   180,   225,   234,
     292,    55,   283,   284,   323,   292,   218,   218,   299,   292,
     311,   292,    17,    19,   292,   235,   235,   235,   292,   292,
     291,   292,   311,   165,   180,   324,   329,   292,    14,    15,
      16,    20,    32,    33,   142,   147,   154,   155,   157,   159,
     161,   171,   173,   229,    13,    33,    27,   100,   141,   225,
      18,    32,   158,   145,   144,   146,    28,   156,    46,   173,
      13,    21,    13,    30,    30,    13,    30,    62,    63,   302,
     146,   173,    27,   246,    18,   292,    13,   151,   292,   149,
     326,    17,    46,   149,   292,    18,   173,   149,   267,   149,
     290,    18,   292,    13,   292,   180,    12,   224,   225,   234,
      12,    13,    13,    13,   314,   315,    18,    13,    56,    57,
     285,   286,   287,   271,    21,    13,    13,    59,    27,    61,
      31,   173,   149,   292,   316,   317,   145,   148,    13,   173,
     324,    12,    59,    59,   163,   225,   225,   225,   292,   225,
     225,   292,   292,   292,   225,   225,   292,   292,   292,   225,
     228,   225,    94,   292,   225,   173,   292,   217,   217,   292,
     292,   217,   292,   292,   292,   292,   292,   292,    65,   305,
     292,    18,    18,   292,    18,   246,    18,   326,    53,    54,
     266,   292,   246,    19,   246,   281,   269,   270,   246,    17,
      21,   217,    12,   292,    13,   224,   225,   225,    20,    13,
     146,   225,   286,   291,   274,    20,   225,   225,   292,   225,
     225,   292,   292,   321,   322,   326,   100,    18,    33,   218,
     218,    17,   118,   292,   292,   292,   225,    31,    32,   158,
      31,   145,    31,   156,   292,    21,    28,    31,    21,    30,
      30,    61,    27,   151,    18,   271,   102,   102,   180,   283,
      59,   100,   292,   217,   292,   225,   292,   225,   315,    18,
     149,   292,    62,    63,   319,    27,   146,   173,    27,   317,
      13,    13,   225,   226,   292,    38,   163,   163,   292,   292,
     225,   292,   225,   292,   225,   217,   292,   332,   292,   292,
     273,   274,    19,   246,    19,   246,   265,   271,   292,   292,
      18,    21,    18,    17,   246,    21,    31,   292,   225,    62,
      63,   225,    65,   322,   326,   225,   292,    20,   225,    18,
     146,   163,   292,   225,   225,    32,    21,    31,   145,   148,
      13,   144,   302,   173,    51,   275,    46,   274,   225,   291,
     225,   292,    61,   292,   225,    27,    27,   292,   225,   225,
     163,   166,   292,   225,   292,   225,   225,   292,   292,    53,
      54,   225,   282,    18,    21,   225,    61,   225,    21,   225,
     292,    31,   145,   144,   271,   225,   225,   173,   225,   163,
     292,   225,   273,   225,    21,   225
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
     455,   456,   457,   458,   459,   460,   461,   462,   463,   464,
     465
  };
  }

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */

  private static final short
  yyr1_[] =
  {
         0,   211,   212,   213,   213,   214,   214,   214,   214,   214,
     215,   215,   216,   216,   217,   217,   217,   217,   217,   217,
     217,   217,   217,   217,   217,   217,   217,   217,   217,   217,
     217,   217,   217,   217,   217,   217,   217,   217,   217,   217,
     217,   217,   218,   218,   218,   218,   219,   219,   220,   220,
     221,   221,   221,   221,   222,   222,   223,   223,   223,   224,
     224,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   226,   226,   227,   227,   228,   228,   228,   229,   229,
     229,   230,   230,   231,   231,   231,   232,   232,   233,   233,
     234,   234,   235,   235,   236,   236,   237,   237,   238,   239,
     239,   239,   240,   240,   241,   241,   241,   241,   241,   241,
     242,   242,   242,   243,   243,   244,   244,   244,   244,   245,
     245,   245,   245,   245,   246,   246,   246,   246,   246,   246,
     246,   246,   246,   246,   246,   246,   246,   246,   247,   247,
     247,   248,   248,   248,   248,   248,   248,   248,   248,   249,
     249,   250,   250,   251,   251,   252,   252,   253,   253,   253,
     254,   255,   255,   255,   256,   256,   257,   258,   258,   258,
     258,   259,   259,   259,   260,   260,   261,   261,   262,   263,
     264,   265,   265,   265,   266,   266,   266,   267,   267,   268,
     268,   269,   269,   270,   270,   271,   271,   272,   273,   273,
     274,   275,   275,   276,   276,   277,   277,   278,   278,   279,
     280,   281,   281,   281,   281,   282,   282,   282,   283,   283,
     284,   285,   285,   286,   286,   287,   287,   288,   288,   288,
     289,   289,   290,   290,   291,   291,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     293,   293,   294,   295,   296,   296,   296,   297,   298,   299,
     299,   300,   300,   301,   302,   302,   303,   303,   304,   304,
     305,   306,   306,   306,   306,   306,   306,   306,   306,   306,
     306,   306,   306,   306,   306,   306,   306,   306,   306,   306,
     306,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   308,   308,   309,   310,   310,   310,
     310,   310,   310,   310,   310,   310,   310,   310,   310,   310,
     310,   310,   310,   311,   311,   312,   313,   313,   314,   314,
     315,   315,   315,   316,   316,   317,   318,   318,   319,   319,
     320,   320,   321,   321,   322,   323,   324,   324,   325,   325,
     325,   325,   326,   326,   327,   327,   328,   328,   328,   328,
     328,   328,   328,   329,   329,   330,   331,   332,   332,   333,
     333,   334,   335,   336,   336
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
       3,     2,     2,     2,     4,     4,     1,     1,     1,     3,
       3,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     3,     3,     3,     3,     1,     2,     1,     3,     3,
       4,     1,     2,     3,     1,     3,     2,     5,     5,     3,
       3,     1,     2,     3,     1,     3,     1,     1,     6,     2,
      10,     1,     3,     4,     1,     1,     1,     1,     3,     3,
       5,     0,     1,     3,     5,     1,     0,     2,     1,     0,
       2,     2,     0,     1,     2,     1,     2,     1,     1,    10,
       7,     3,     3,     3,     3,     1,     1,     1,     1,     0,
       2,     1,     2,     2,     4,     1,     1,     1,     2,     3,
       1,     3,     2,     2,     1,     3,     1,     1,     1,     1,
       1,     1,     3,     1,     1,     2,     3,     3,     3,     2,
       4,     6,     3,     3,     3,     3,     3,     4,     1,     1,
       1,     1,     4,     4,     4,     4,     2,     3,     5,     7,
       5,     1,     3,     5,     7,     1,     3,     5,     7,     4,
       4,     4,     4,     5,     6,     4,     6,     3,     5,     7,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       3,     1,     1,     5,     2,     5,     5,     9,     1,     3,
       3,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     1,     3,     3,     1,     3,     3,
       1,     4,     3,     2,     2,     4,     1,     6,     6,     7,
       8,    10,     4,     3,     5,     4,     3,     6,     1,     3,
       3,     5,     5,     1,     3,     3,     7,     6,     4,     5,
       5,     9,     1,     3,     3,     3,     1,     1,     1,     6,
       2,     4,     1,     3,     1,     1,     1,     1,     1,     1,
       1,     1,     3,     1,     1,     3,     3,     1,     3,     1,
       1,     3,     3,     1,     3
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
  "TREAL", "TCHAR", "TTOKEN", "DUMMY", "G-LOOSE", "S-LET", "U-DO",
  "U-LSQUAREDBAR", "U-LSQUAREBAR", "U-LRSQUARE", "U-TBAR", "U-DBAR",
  "U-BARTILDEBAR", "U-SEMI", "T-STAR", "U-MINUS", "U-PLUS", "G-HIGH",
  "$accept", "source", "programParagraphList", "programParagraph",
  "classDefinition", "processDefinition", "process",
  "replicationDeclaration", "singleExpressionDeclaration",
  "processParagraphList", "processParagraph", "actionDefinitionList",
  "namesetDefList", "paragraphAction", "action", "actionList",
  "parametrisationList", "parametrisation", "renameExpression",
  "renameList", "channelDefinition", "channelDef", "channelNameDecl",
  "singleTypeDeclarationList", "singleTypeDeclaration",
  "chansetDefinitionParagraph", "chansetDefinitionList",
  "chansetDefinition", "globalDefinitionParagraph", "classDefinitionBlock",
  "classDefinitionBlockAlternative", "typeDefs", "typeDefList", "typeDef",
  "qualifier", "type", "dottedIdentifier", "basicType", "functionType",
  "partialFunctionType", "totalFunctionType", "fieldList", "field",
  "invariant", "valueDefs", "valueDefList", "qualifiedValueDef",
  "valueDef", "functionDefs", "functionDefList", "functionDef",
  "implicitFunctionDef", "qualifiedExplicitFunctionDef",
  "explicitFunctionDef", "parameterList", "functionBody", "parameterTypes",
  "patternListTypeList", "identifierTypePairList_opt",
  "identifierTypePairList", "preExpr_opt", "preExpr", "postExpr_opt",
  "postExpr", "measureExpr", "operationDefs", "operationDefList",
  "operationDef", "explicitOperationDef", "implicitOperationDef",
  "operationType", "operationBody", "externals_opt", "externals",
  "varInformationList", "varInformation", "mode", "stateDefs",
  "stateDefList", "stateDef", "expressionList", "expression",
  "booleanLiteral", "characterLiteral", "nilLiteral", "numericLiteral",
  "textLiteral", "quoteLiteral", "localDefList", "localDef", "ifExpr",
  "elseExprs", "casesExpr", "casesExprAltList", "casesExprAlt",
  "unaryExpr", "binaryExpr", "mapletList", "maplet", "controlStatement",
  "nonDeterministicAltList", "letStatement", "blockStatement",
  "assignmentDefList", "assignmentDef", "assignStatementList",
  "assignStatement", "ifStatement", "elseStatements", "casesStatement",
  "casesStatementAltList", "casesStatementAlt", "implicitOperationBody",
  "pattern", "patternLessID", "patternList", "patternIdentifier",
  "matchValue", "bind", "setBind", "typeBind", "bindList", "multipleBind",
  "multipleSetBind", "multipleTypeBind", "typeBindList", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final short yyrhs_[] =
  {
       212,     0,    -1,   213,    -1,   214,    -1,   213,   214,    -1,
     215,    -1,   216,    -1,   231,    -1,   236,    -1,   239,    -1,
     174,   180,    12,   172,   240,   173,    -1,   174,   180,    12,
     169,   180,   172,   240,   173,    -1,   175,   180,    12,   217,
      -1,   175,   180,    12,   234,    13,   217,    -1,   172,    13,
     225,   173,    -1,   172,   220,    13,   225,   173,    -1,   217,
      33,   217,    -1,   217,    15,   217,    -1,   217,    14,   217,
      -1,   217,   142,   292,   145,   217,    -1,   217,    20,   292,
      32,   292,    21,   217,    -1,   217,    32,   217,    -1,   217,
      16,   217,    -1,   217,   155,   217,    -1,   217,   154,   292,
     156,   217,    -1,   217,   157,   217,    -1,   217,    20,   292,
     158,   217,    -1,   217,   171,   292,    -1,   217,   161,   292,
      -1,   217,   159,   292,    -1,    17,   234,    13,   217,    18,
      17,   292,    18,    -1,   180,    -1,   180,    19,    -1,   180,
      17,   291,    18,    -1,   217,   229,    -1,    33,   218,    13,
     217,    -1,    15,   218,    13,   217,    -1,    14,   218,    13,
     217,    -1,   142,   292,   145,   218,    13,   217,    -1,    32,
     218,    13,    20,   292,    21,   217,    -1,    32,   218,    13,
     217,    -1,    16,   218,    13,   217,    -1,   235,    -1,   219,
      -1,   218,    33,   235,    -1,   218,    33,   219,    -1,   180,
     118,   292,    -1,   180,   146,   219,    -1,   221,    -1,   220,
     221,    -1,   241,    -1,     9,   222,    -1,     6,    -1,     6,
     223,    -1,   180,    12,   224,    -1,   222,   180,    12,   224,
      -1,   180,    12,   292,    -1,   223,   180,    12,   292,    -1,
     223,    33,   180,    12,   292,    -1,   225,    -1,   234,    13,
     225,    -1,    22,    -1,    23,    -1,    24,    -1,    25,    -1,
      26,   292,    -1,   292,    27,   225,    -1,   292,   141,   225,
      -1,   225,    33,   225,    -1,   225,    15,   225,    -1,   225,
      14,   225,    -1,   225,   155,   225,    -1,   225,   154,   292,
     156,   225,    -1,   225,   157,   225,    -1,   225,    20,   292,
     158,   225,    -1,   225,   171,   292,    -1,   225,   161,   292,
      -1,   225,   159,   292,    -1,   225,   229,    -1,   162,   291,
      13,    17,   226,    18,    -1,   225,   147,   292,    31,   292,
     148,   225,    -1,   225,    16,   225,    -1,   225,   142,   292,
      31,   292,   145,   225,    -1,   225,    32,   225,    -1,   225,
      20,   292,    31,   292,    32,   292,    31,   292,    21,   225,
      -1,   225,    20,   292,    32,   292,    21,   225,    -1,   225,
     142,   292,    31,   292,    31,   292,   145,   225,    -1,   225,
     142,   292,   145,   225,    -1,   227,    13,   225,    -1,    17,
     234,    13,   225,    18,    17,   291,    18,    -1,    33,   218,
      13,   225,    -1,    15,   218,    13,   225,    -1,    14,   218,
      13,   225,    -1,   147,   292,   148,   218,    13,   225,    -1,
      16,   218,    13,    20,   292,    21,   225,    -1,   142,   292,
     145,   218,    13,    20,   292,    21,   225,    -1,    32,   218,
      13,    20,   292,    31,   292,    21,   225,    -1,    32,   218,
      13,    20,   292,    21,   225,    -1,   312,    -1,   313,    -1,
     310,    -1,   225,    -1,   226,   146,   225,    -1,   228,    -1,
     227,    33,   228,    -1,   140,   235,    -1,   139,   235,    -1,
     138,   235,    -1,   143,   230,   144,    -1,   143,   292,    28,
     292,    31,   332,   144,    -1,   143,   292,    28,   292,    31,
     332,    13,   292,   144,    -1,   292,    28,   292,    -1,   230,
     146,   292,    28,   292,    -1,     4,    -1,     4,   232,    -1,
       4,   232,    33,    -1,   233,    -1,   232,    33,   233,    -1,
     180,    -1,   235,    -1,   235,    -1,   234,    33,   235,    -1,
     180,   149,   246,    -1,   180,   146,   235,    -1,     5,    -1,
       5,   237,    -1,   238,    -1,   237,   238,    -1,   180,    12,
     292,    -1,   242,    -1,   255,    -1,   259,    -1,   241,    -1,
     241,   240,    -1,   242,    -1,   255,    -1,   259,    -1,   276,
      -1,   288,    -1,    10,   278,    -1,     3,    -1,     3,   243,
      -1,     3,   243,    33,    -1,   244,    -1,   243,    33,   244,
      -1,   245,   180,    12,   246,   254,    -1,   245,   180,    12,
     246,    -1,   245,   180,    34,   252,    -1,   245,   180,    34,
     252,   254,    -1,   176,    -1,   177,    -1,   178,    -1,   179,
      -1,    -1,    17,   246,    18,    -1,   248,    -1,   298,    -1,
      35,   180,    36,   252,   173,    -1,   246,    31,   246,    -1,
     246,    37,   246,    -1,    20,   246,    21,    -1,    43,   246,
      -1,    41,   246,    -1,    42,   246,    -1,    40,   246,    38,
     246,    -1,    39,   246,    38,   246,    -1,   249,    -1,   247,
      -1,   180,    -1,   247,    90,   180,    -1,   247,   104,   180,
      -1,   188,    -1,   189,    -1,   190,    -1,   191,    -1,   192,
      -1,   193,    -1,   194,    -1,   195,    -1,   250,    -1,   251,
      -1,   246,    44,   246,    -1,    19,    44,   246,    -1,   246,
      27,   246,    -1,    19,    27,   246,    -1,   253,    -1,   252,
     253,    -1,   246,    -1,   180,   149,   246,    -1,   180,    45,
     246,    -1,    47,   324,    46,   292,    -1,    48,    -1,    48,
     256,    -1,    48,   256,    33,    -1,   257,    -1,   256,    33,
     257,    -1,   245,   258,    -1,   180,   149,   246,    12,   292,
      -1,   325,   149,   246,    12,   292,    -1,   180,    12,   292,
      -1,   325,    12,   292,    -1,     7,    -1,     7,   260,    -1,
       7,   260,    33,    -1,   261,    -1,   260,    33,   261,    -1,
     262,    -1,   263,    -1,   245,   180,   267,   270,   271,   274,
      -1,   245,   264,    -1,   180,   149,   249,   180,   265,    46,
     266,   271,   273,   275,    -1,    19,    -1,    17,   326,    18,
      -1,   265,    17,   326,    18,    -1,   292,    -1,    53,    -1,
      54,    -1,    19,    -1,    17,   268,    18,    -1,   326,   149,
     246,    -1,   268,   146,   326,   149,   246,    -1,    -1,   270,
      -1,   180,   149,   246,    -1,   270,   146,   180,   149,   246,
      -1,   272,    -1,    -1,    49,   292,    -1,   274,    -1,    -1,
      50,   292,    -1,    51,   292,    -1,    -1,     8,    -1,     8,
     277,    -1,   278,    -1,   277,   278,    -1,   280,    -1,   279,
      -1,   245,   180,   149,   281,   180,   265,    46,   282,   271,
     273,    -1,   245,   180,   267,   269,   283,   271,   274,    -1,
     246,   102,   246,    -1,    19,   102,   246,    -1,   246,   102,
      19,    -1,    19,   102,    19,    -1,   225,    -1,    53,    -1,
      54,    -1,   284,    -1,    -1,    55,   285,    -1,   286,    -1,
     285,   286,    -1,   287,   291,    -1,   287,   291,   149,   246,
      -1,    56,    -1,    57,    -1,    11,    -1,    11,   289,    -1,
      11,   289,    33,    -1,   290,    -1,   289,    33,   290,    -1,
     245,   315,    -1,    47,   292,    -1,   292,    -1,   291,   146,
     292,    -1,   296,    -1,   293,    -1,   295,    -1,   294,    -1,
     297,    -1,   298,    -1,    17,   292,    18,    -1,   181,    -1,
     180,    -1,   180,    98,    -1,   292,    90,   180,    -1,   292,
     104,   180,    -1,   292,    91,    92,    -1,   292,    19,    -1,
     292,    17,   291,    18,    -1,   292,    17,   292,    86,   292,
      18,    -1,   292,    90,   328,    -1,   292,   153,   180,    -1,
     292,   153,   328,    -1,   292,   152,   324,    -1,   292,   152,
     330,    -1,    58,   299,    59,   292,    -1,   301,    -1,   303,
      -1,   306,    -1,   307,    -1,   134,   332,    13,   292,    -1,
     135,   332,    13,   292,    -1,   136,   329,    13,   292,    -1,
      52,   329,    13,   292,    -1,    29,    30,    -1,    29,   291,
      30,    -1,    29,   292,    31,   332,    30,    -1,    29,   292,
      31,   332,    13,   292,    30,    -1,    29,   292,    86,   292,
      30,    -1,    15,    -1,    20,   291,    21,    -1,    20,   292,
      31,   330,    21,    -1,    20,   292,    31,   330,    13,   292,
      21,    -1,   170,    -1,    29,   308,    30,    -1,    29,   309,
      31,   332,    30,    -1,    29,   309,    31,   332,    13,   292,
      30,    -1,    88,    17,   291,    18,    -1,    89,    17,   291,
      18,    -1,    93,   336,    13,   292,    -1,   168,    17,   292,
      18,    -1,    95,   248,    17,   292,    18,    -1,    95,    17,
     292,   146,   246,    18,    -1,    96,    17,   291,    18,    -1,
      97,    17,   292,   146,   292,    18,    -1,   150,   291,   151,
      -1,   150,   292,    31,   332,   151,    -1,   150,   292,    31,
     332,    13,   292,   151,    -1,   183,    -1,   182,    -1,   184,
      -1,   185,    -1,    92,    -1,   187,    -1,   160,    -1,   137,
      -1,   186,    -1,   300,    -1,   299,   146,   300,    -1,   258,
      -1,   264,    -1,    60,   292,    61,   292,   302,    -1,    63,
     292,    -1,    62,   292,    61,   292,   302,    -1,    64,   292,
     149,   304,   173,    -1,    64,   292,   149,   304,   146,    65,
      27,   292,   173,    -1,   305,    -1,   304,   146,   305,    -1,
     326,    27,   292,    -1,    66,   292,    -1,    67,   292,    -1,
      68,   292,    -1,    69,   292,    -1,    70,   292,    -1,    71,
     292,    -1,    72,   292,    -1,    73,   292,    -1,    74,   292,
      -1,    75,   292,    -1,    76,   292,    -1,    77,   292,    -1,
      78,   292,    -1,    79,   292,    -1,    80,   292,    -1,    81,
     292,    -1,    82,   292,    -1,    83,   292,    -1,    84,   292,
      -1,    85,   292,    -1,   292,    66,   292,    -1,   292,    37,
     292,    -1,   292,    67,   292,    -1,   292,   106,   292,    -1,
     292,   105,   292,    -1,   292,   107,   292,    -1,   292,   108,
     292,    -1,   292,   109,   292,    -1,   292,   110,   292,    -1,
     292,   111,   292,    -1,   292,   112,   292,    -1,   292,    12,
     292,    -1,   292,   113,   292,    -1,   292,   114,   292,    -1,
     292,   115,   292,    -1,   292,   116,   292,    -1,   292,   117,
     292,    -1,   292,   118,   292,    -1,   292,   119,   292,    -1,
     292,   120,   292,    -1,   292,   121,   292,    -1,   292,   122,
     292,    -1,   292,   123,   292,    -1,   292,   124,   292,    -1,
     292,   125,   292,    -1,   292,   126,   292,    -1,   292,   127,
     292,    -1,   292,   128,   292,    -1,   292,   129,   292,    -1,
     292,   130,   292,    -1,   292,   131,   292,    -1,   292,   132,
     292,    -1,   292,   133,   292,    -1,   309,    -1,   308,   146,
     309,    -1,   292,    87,   292,    -1,   318,    -1,    60,   311,
     173,    -1,   163,   311,   173,    -1,   317,    -1,   101,    17,
     316,    18,    -1,    20,   323,    21,    -1,   103,    19,    -1,
     103,   292,    -1,   292,   100,    94,   292,    -1,   320,    -1,
     164,   329,    59,   292,   163,   225,    -1,   164,   324,    59,
     292,   163,   225,    -1,   164,   165,   324,   118,   292,   163,
     225,    -1,   164,   180,    12,   292,    38,   292,   163,   225,
      -1,   164,   180,    12,   292,    38,   292,   166,   292,   163,
     225,    -1,   167,   292,   163,   225,    -1,   292,    27,   225,
      -1,   311,    31,   292,    27,   225,    -1,    58,   299,    59,
     225,    -1,    17,   225,    18,    -1,    17,    99,   314,    13,
     225,    18,    -1,   315,    -1,   314,   146,   315,    -1,   180,
     149,   246,    -1,   180,   149,   246,   100,   292,    -1,   180,
     149,   246,    59,   292,    -1,   317,    -1,   316,    33,   317,
      -1,   292,   100,   292,    -1,    60,   292,    61,   225,   319,
      63,   225,    -1,    60,   292,    61,   225,    63,   225,    -1,
      62,   292,    61,   225,    -1,   319,    62,   292,    61,   225,
      -1,    64,   292,   149,   321,   173,    -1,    64,   292,   149,
     321,   146,    65,    27,   225,   173,    -1,   322,    -1,   321,
     146,   322,    -1,   326,    27,   225,    -1,   283,   271,   274,
      -1,   327,    -1,   325,    -1,   328,    -1,    88,    17,   326,
     146,   324,    18,    -1,    89,    19,    -1,    89,    17,   326,
      18,    -1,   324,    -1,   326,   146,   324,    -1,   180,    -1,
      67,    -1,   296,    -1,   293,    -1,   295,    -1,   294,    -1,
     297,    -1,   298,    -1,    17,   292,    18,    -1,   330,    -1,
     331,    -1,   324,   118,   292,    -1,   324,   149,   246,    -1,
     333,    -1,   332,   146,   333,    -1,   334,    -1,   335,    -1,
     326,   118,   292,    -1,   326,   149,   246,    -1,   331,    -1,
     336,   146,   331,    -1
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
     654,   658,   661,   664,   667,   672,   677,   679,   681,   683,
     687,   691,   693,   695,   697,   699,   701,   703,   705,   707,
     709,   711,   715,   719,   723,   727,   729,   732,   734,   738,
     742,   747,   749,   752,   756,   758,   762,   765,   771,   777,
     781,   785,   787,   790,   794,   796,   800,   802,   804,   811,
     814,   825,   827,   831,   836,   838,   840,   842,   844,   848,
     852,   858,   859,   861,   865,   871,   873,   874,   877,   879,
     880,   883,   886,   887,   889,   892,   894,   897,   899,   901,
     912,   920,   924,   928,   932,   936,   938,   940,   942,   944,
     945,   948,   950,   953,   956,   961,   963,   965,   967,   970,
     974,   976,   980,   983,   986,   988,   992,   994,   996,   998,
    1000,  1002,  1004,  1008,  1010,  1012,  1015,  1019,  1023,  1027,
    1030,  1035,  1042,  1046,  1050,  1054,  1058,  1062,  1067,  1069,
    1071,  1073,  1075,  1080,  1085,  1090,  1095,  1098,  1102,  1108,
    1116,  1122,  1124,  1128,  1134,  1142,  1144,  1148,  1154,  1162,
    1167,  1172,  1177,  1182,  1188,  1195,  1200,  1207,  1211,  1217,
    1225,  1227,  1229,  1231,  1233,  1235,  1237,  1239,  1241,  1243,
    1245,  1249,  1251,  1253,  1259,  1262,  1268,  1274,  1284,  1286,
    1290,  1294,  1297,  1300,  1303,  1306,  1309,  1312,  1315,  1318,
    1321,  1324,  1327,  1330,  1333,  1336,  1339,  1342,  1345,  1348,
    1351,  1354,  1358,  1362,  1366,  1370,  1374,  1378,  1382,  1386,
    1390,  1394,  1398,  1402,  1406,  1410,  1414,  1418,  1422,  1426,
    1430,  1434,  1438,  1442,  1446,  1450,  1454,  1458,  1462,  1466,
    1470,  1474,  1478,  1482,  1486,  1488,  1492,  1496,  1498,  1502,
    1506,  1508,  1513,  1517,  1520,  1523,  1528,  1530,  1537,  1544,
    1552,  1561,  1572,  1577,  1581,  1587,  1592,  1596,  1603,  1605,
    1609,  1613,  1619,  1625,  1627,  1631,  1635,  1643,  1650,  1655,
    1661,  1667,  1677,  1679,  1683,  1687,  1691,  1693,  1695,  1697,
    1704,  1707,  1712,  1714,  1718,  1720,  1722,  1724,  1726,  1728,
    1730,  1732,  1734,  1738,  1740,  1742,  1746,  1750,  1752,  1756,
    1758,  1760,  1764,  1768,  1770
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   146,   146,   150,   151,   155,   156,   157,   158,   159,
     163,   164,   168,   169,   174,   175,   177,   178,   179,   183,
     187,   188,   189,   190,   199,   200,   209,   221,   222,   223,
     227,   228,   229,   230,   231,   233,   234,   235,   239,   243,
     244,   245,   251,   252,   253,   254,   266,   267,   271,   272,
     281,   282,   283,   284,   288,   289,   303,   304,   305,   309,
     310,   314,   315,   316,   317,   318,   330,   332,   333,   334,
     335,   336,   345,   346,   355,   365,   366,   367,   368,   375,
     380,   381,   385,   386,   391,   395,   400,   404,   407,   409,
     413,   414,   415,   419,   423,   428,   433,   437,   439,   440,
     441,   446,   447,   451,   452,   456,   457,   458,   463,   465,
     466,   479,   480,   490,   491,   492,   496,   497,   509,   510,
     517,   518,   522,   523,   527,   528,   532,   533,   540,   544,
     545,   546,   550,   551,   555,   556,   557,   558,   559,   566,
     570,   571,   572,   576,   577,   581,   582,   583,   584,   594,
     595,   596,   604,   605,   609,   610,   611,   612,   613,   614,
     615,   616,   617,   618,   619,   620,   621,   630,   634,   635,
     636,   640,   641,   642,   643,   644,   645,   646,   647,   651,
     652,   656,   657,   661,   662,   666,   667,   671,   672,   673,
     677,   688,   689,   690,   694,   695,   699,   703,   704,   705,
     706,   710,   711,   712,   716,   717,   721,   722,   726,   730,
     734,   739,   740,   741,   745,   746,   747,   751,   752,   756,
     757,   760,   762,   766,   767,   771,   772,   776,   780,   781,
     785,   796,   797,   801,   802,   809,   810,   814,   815,   819,
     823,   827,   828,   829,   830,   834,   835,   836,   840,   841,
     845,   849,   850,   854,   855,   859,   860,   882,   883,   884,
     888,   889,   893,   894,   898,   899,   904,   905,   906,   907,
     908,   909,   911,   912,   913,   914,   915,   916,   917,   918,
     919,   920,   921,   923,   924,   925,   926,   928,   929,   930,
     931,   932,   934,   935,   936,   937,   939,   940,   942,   943,
     945,   947,   958,   960,   961,   963,   964,   966,   967,   969,
     971,   973,   982,   983,   984,   989,   997,   999,  1000,  1001,
    1007,  1008,  1012,  1016,  1020,  1021,  1022,  1026,  1030,  1036,
    1037,  1041,  1042,  1046,  1050,  1051,  1055,  1056,  1060,  1061,
    1065,  1069,  1070,  1071,  1072,  1073,  1074,  1075,  1076,  1077,
    1078,  1079,  1080,  1081,  1082,  1083,  1084,  1085,  1086,  1087,
    1088,  1092,  1093,  1094,  1095,  1096,  1097,  1098,  1099,  1100,
    1101,  1102,  1103,  1104,  1105,  1106,  1107,  1108,  1109,  1110,
    1111,  1112,  1113,  1114,  1115,  1116,  1117,  1118,  1119,  1120,
    1121,  1122,  1123,  1124,  1128,  1129,  1133,  1137,  1138,  1139,
    1152,  1153,  1154,  1159,  1160,  1171,  1172,  1179,  1181,  1183,
    1184,  1185,  1186,  1204,  1205,  1209,  1213,  1214,  1218,  1219,
    1223,  1224,  1230,  1237,  1238,  1251,  1255,  1256,  1260,  1261,
    1265,  1266,  1270,  1271,  1275,  1279,  1283,  1284,  1288,  1290,
    1299,  1300,  1304,  1305,  1309,  1311,  1316,  1317,  1318,  1319,
    1320,  1321,  1322,  1326,  1327,  1331,  1335,  1339,  1340,  1344,
    1345,  1349,  1353,  1357,  1358
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
     205,   206,   207,   208,   209,   210
  };

  private static final short yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 12535;
  private static final int yynnts_ = 126;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 43;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 211;

  private static final int yyuser_token_number_max_ = 465;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */

}


