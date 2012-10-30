
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
  public static final short yypact_ninf_ = -648;
  public static final short yypact_[] =
  {
       111,   663,  -129,   -82,   663,   561,   -70,   -63,   147,   111,
    -648,  -648,  -648,  -648,  -648,  -648,  -648,  -648,  -648,  -648,
    -648,  -648,  -648,    90,  -648,   129,    51,   338,  -648,  -648,
     374,   -82,  -648,   219,   409,  -648,  -648,  -648,  1031,   433,
    -648,   465,   469,  -648,  -648,   663,    99,   225,   514,  -129,
    3260,  -648,     8,  -648,   663,  3260,   485,   328,  -648,  -648,
    -648,    -4,  -648,  -648,  -648,  -648,  -648,  -648,  -648,  -648,
    -648,  -648,  -648,  -648,  -648,    28,  -648,   561,   122,   123,
    -648,   514,  1546,    51,  -648,   514,   261,   514,   333,   514,
     514,   514,   514,   514,    15,  -648,  -648,  -648,  -648,  -648,
    -648,  -648,  -648,   950,  -648,  -648,  -648,  -648,  -648,  -648,
    -648,  3260,  3260,  2852,   856,  1243,  3260,  3260,  3260,  3260,
    3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,
    3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,   504,   515,
     856,    26,   529,   543,   856,   856,   856,  3260,   555,  -648,
     486,  -648, 11637,  -648,  -648,  -648,  -648,  -648,  -648,  -648,
    -648,  -648,  -648,   856,  -648,   514,   414,  -648,  5892,   856,
     856,  -648,  3260,   514,  3260,   514,  -648,   445,   582,   453,
     453,   453,   225,   453,   453,  3260,   488,   283,  1436,   335,
    -648,   938,   299,   950,   143,  -648,   786,   514,   514,   910,
     563,   912,  1080,   599,   599,   599,   471,   477,   514,   514,
     514,   514,  6014,    29,  6137,  -648,    61,  4911,   171,   648,
    -648,  -648,     2,  -648,  -648,   672,  -648,  -648,    32,  -648,
    -648,   -14,  -648,  6259,  6381,  1011,  1011,  1011,  1011, 12088,
    1011,  1011,  1011,  1011,  1011,  1011,  1011,  1011,  1011,  1011,
    1011,  1011,  1011,  1011,  2429,  3260,  3260,   547,  -648,    33,
    3260,   680,  3260,  3260,  -648,   -18,    47,  -648,  -648,  -648,
      52,   699,   241,  6504,  3260,  -648,  3260,  3260,  -648,  3260,
    3260,  3260,   426,   623,   538,  3260,  3260,  3260,  3260,  3260,
    3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,
    3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,  3260,
    3260,  3260,  3260,  3260,   856,   506,    30,   239,   950,   539,
     567,   -19,  -648,   580,    76, 11637,   724, 11637,   862,   570,
     663,   553,   230,   581,   582,  -648,  -648,  -648,  -648,  -648,
     309,   343,  -648,  -648,   380,   384,   413,   421,   443,  6626,
     548,   573,  2406,  1061,  -648,  -648,  3260,  -648,   310,   310,
     310,  3260,   310,   310,  3260,  3260,  3260,   310,   310,  3260,
    3260,  3260,  -648,   310,   225,   856,  -648,   514,   514,  -648,
    -648,  -648,   950,   950,  -648,  1546,   514,   514,  -648,  -648,
     950,   599,   599,   950,  -648,  -648,  3260,   856,  -648,   856,
    3260,  3260,  -648,  3260,   856,  3260,   514,  3260,   514,  3260,
    1243,  3260,   856,    88, 11637,   125,  3260,   856,  6748,  3260,
     134,  6870,  3260,   856,   514,  3260,   856,  3260,  3260,  -648,
     856,  6992, 12138,   151,  7114,  1618,  3468,  3468,  -648,  -648,
    -648,  -648,  1618,  1618,  1618,  1618, 12138, 12138, 12138, 12138,
   12138, 11961, 12011, 11881, 11759, 12138, 12138, 12138, 12138,  3633,
    3633,  2429,  2256,  2082,  2082,  2429,  2429,  1011,  1011,   263,
     311,  -648,  -648,  -648,  -648,  -648,   856,   514,   454,   514,
    3260,   577,   710,  -648,   856,  -648,  3260,  3260,   582,   586,
     663,  -648,  -648,  -648,  -648,  3260,   587,   730,  -648,  -648,
    -648,  3260,   453,   310,   453,   310,   310,   310,    22,   310,
     453,   759,   -15,   762,   595,   453,   453,   453,  2232,  2988,
    -648,  -648,  -648,  -648,  3260,   453,   453,  1243,  3260,  3260,
    3124,   225,   225,   225,  3260,  3260,  3260,  3260,   323,  3260,
    1457,   459,  -648,  5156,  -648,  -648,  -648,  -648,  -648,  2406,
    -648,   152,   556,   629,   729,  3489,  4422,  1187,   379,  7236,
     476,  7358,  4544,   651,   368, 11637, 11637, 11637,  1436,  -648,
     731,   950,   950,  1355,   599,   599, 11637,   658,   418,    34,
    7482, 11637,  7604,  -648,    77, 12138,   950, 11759, 11637,  -648,
    5279,   -47,  -648,     7,  -648,  -648, 11759,  -648,   514,  7726,
    -648,  3260, 11637,  -648,   950, 11759,  -648, 11759, 11759,     3,
    -648,  -648,  3260,   412,   950,   856,  -648,   276,   950, 11637,
     630,  3260,  -648,   764, 11637, 11637,   608,    84,  -648, 11637,
     638,  -648,   230, 11637,  -648,  -648,  -648,  -648,  -648,  -648,
    1802,  3260,  -648,  -648,   491,  3260,   611,   781,  2580,   783,
     546,   554,   583,   587,   203,  3094,   605,  5033,   393,   750,
    -648,   785, 11637,   619,   621,    49,  5401,   -12,  7848,  -648,
   11637,  -648,  -648,  -648,  7970,  8092,    79,  8214,    -5,   856,
     790,   249,   755,  3689,  2406,  2406,  2406,  3260,  2406,  2406,
    3260,  3260,  3260,  2406,  2406,  3260,  3260,  3260,  -648,  -648,
    2406,   536,  2406,  2406,  2672,  -648,  3260,   310,   310,  -648,
    3260,  3260,   310,  3260,  -648,  3260,  -648,  3260,  -648,  -648,
    3260,  -648,  3260,  3260,  -648,   913,  -648,  3260,   789,  -648,
    8336,  3260,  -648,  8458,   514,   193,   856,  2716,   514, 11637,
    -648,  -648,  1824,   414,   514,  -648,   792,  8580,   310, 11637,
     803,  3260,  -648,  3469,   622,  2580,  2406,  2406,   798,    82,
    -648,  -648,  2406,  -648,  -648,   393,  -648,  3260,   710,  -648,
     801,  2406,  2406,  2406,  2406,  3260,  -648,   856,   453,   453,
     811,  -648,   713,  3260,  3260,  3260,  2406,   460,   -13,  3394,
    4300,  1470,   552,  5524,  8703,  4666,   257,   405, 11637, 11637,
   11637,  3469,  -648,  -648,   552,  -648,  8825,   368,  1187,  8947,
    9070,   651, 11637,  9192,  9316,  9440,  9562, 11759,   807,  -648,
   11637,  -648,  -648,  9684,  -648,   950,  -648,   214,  -648,  -648,
     750, 11637,   950,   260,   683,   655,   802,   690,  1218,  3260,
     310,  -648,  3260, 11637,  2406,  -648,  -648,  -648,  3260,  2406,
     587,  3353,  -648,   419,  -648,  3260,  -648,  3469,  5156,  3469,
    2944,  4788,  9806,   -37,  -648,   164,   626,   635,  2406,  3260,
    9928,  3811,  3933,  3469,  3260,  3260,  2406,  3260,  2406,  3260,
    2406,   310,  3260,   856,  -648,  -648,  -648,  3260,  3260,  -648,
    -648,   710,  1859,  1876,   454,   750,  3260,  3260, 10050,  -648,
   11637,  3469, 10172,  3373,  -648,   843,   514,  5647,  3260,  2406,
     620,  2406,   945,  -648,  2406,   842,  2406,  3469,   289,  4055,
    3260,  2406,  2406, 10294, 10416,   405,  5770,  1470, 10538,   257,
    1187, 11637,    43,  5279,  3346,   812,  -648,   261,   950,   261,
     950,   324,   710, 11637, 11637,  -648,  2406,  -648,  3260,   950,
    2406,  3260, 10660,  3469,  3260,  2406,  3469,   838,  -648,   245,
    3469,  5156,  3260,  -648,  -648,  2406,  2406,  3567,  3469,  3469,
    3260,  2406,  3260,  2406,  2406,  3260,  -648,  -648,  -648,  3260,
    -648,  2058,  -648,  -648,   295,  -648, 10782,  2406, 10904,  3469,
    2406,  2406, 11026,  3469,  3469,  2406,  3260, 11149,  1470, 11271,
    1470,  -648, 11393, 11637,  -648,  -648,  3469,   750,  -648,  2406,
    3469,  2406,  2808,  2406,  3469,  4177,  3260,  2406,  -648,   710,
    -648,  3469,  -648,  -648,  2406, 11515,  1470,  -648,  3469,  2406,
    1470
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
     325,     0,   320,   319,   321,   322,   327,   324,   195,   440,
     442,   441,   439,   443,   444,     0,   431,   192,     0,     0,
     144,     0,     0,     0,   123,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   167,   170,   171,   172,   173,   174,
     175,   176,   177,   122,   155,   166,   178,   179,   156,   117,
     300,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   304,
     273,   272,   128,   266,   268,   267,   265,   269,   270,   287,
     288,   289,   290,     0,   216,     0,     0,   204,     0,     0,
       0,   433,     0,     0,     0,     0,   194,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    31,    12,     0,
     120,   146,   167,   186,   147,   184,     0,     0,     0,     0,
       0,     0,     0,   162,   163,   161,     0,     0,     0,     0,
       0,     0,     0,     0,   263,   295,     0,   263,     0,   393,
     438,   437,     0,   430,   429,     0,   446,   447,     0,   330,
     331,     0,   328,     0,     0,   340,   341,   342,   343,   344,
     345,   346,   347,   348,   349,   350,   351,   352,   353,   354,
     355,   356,   357,   358,   359,     0,     0,     0,   456,     0,
       0,     0,     0,     0,   435,     0,     0,   450,   452,   453,
       0,     0,     0,   263,     0,   274,     0,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   166,
       0,   225,   445,     0,     0,   198,     0,   199,     0,     0,
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
       0,     0,     0,   224,     0,   434,     0,     0,     0,     0,
     233,   234,   237,   236,   139,     0,     0,   257,   259,    10,
     133,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    53,     0,    51,     0,   300,     0,     0,   248,
      61,    62,    63,    64,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   103,     0,   100,    98,    99,   396,   402,     0,
      49,     0,    31,    18,    17,    22,     0,    21,    16,     0,
       0,     0,     0,    23,    25,    29,    28,    27,    13,   121,
       0,   188,   187,     0,   165,   164,   264,     0,     0,     0,
       0,   395,     0,   394,     0,   448,   449,   294,   286,   329,
       0,     0,   337,     0,   308,   309,   310,   457,     0,     0,
     314,     0,   454,   436,   455,   291,   451,   292,   293,     0,
     311,   279,     0,     0,   218,     0,   210,     0,   222,   226,
       0,     0,   207,   436,   196,   197,     0,     0,   235,   262,
       0,   261,   258,    46,    47,    37,    45,    44,    36,    41,
       0,     0,    40,    35,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   273,     0,     0,     0,     0,   225,
     247,     0,    65,     0,     0,     0,     0,     0,     0,   400,
     401,   107,   106,   105,     0,     0,     0,     0,     0,     0,
     437,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    14,    78,
       0,     0,     0,     0,     0,    33,     0,     0,     0,   108,
       0,     0,     0,     0,   157,     0,   302,     0,   297,   299,
       0,   306,     0,     0,   332,     0,   335,     0,     0,   312,
       0,     0,   317,     0,     0,     0,     0,     0,     0,   229,
     432,    11,     0,   220,     0,   260,     0,     0,     0,    56,
       0,     0,    54,    59,     0,     0,     0,     0,     0,     0,
     414,   412,     0,   254,   255,   249,   250,     0,     0,   399,
       0,     0,     0,     0,     0,     0,   397,     0,     0,     0,
       0,   398,     0,     0,     0,     0,     0,    70,    69,    81,
       0,    83,    68,     0,     0,     0,    71,    73,    77,    76,
      75,    88,   104,    66,    67,    15,     0,    26,    19,     0,
     111,    24,   189,     0,     0,     0,     0,   333,     0,   338,
     339,   313,   315,     0,   280,   219,   211,     0,   214,   215,
     225,   213,   223,     0,     0,     0,   248,   221,   416,     0,
       0,    38,     0,    57,     0,    55,    92,    91,     0,     0,
       0,     0,   251,   252,   428,     0,    90,   411,   286,   409,
       0,     0,     0,     0,   425,     0,     0,     0,     0,     0,
       0,     0,     0,   408,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   303,   298,   307,     0,     0,   318,
     212,   228,     0,     0,     0,   225,     0,     0,     0,    39,
      58,    60,     0,     0,   415,     0,     0,     0,     0,     0,
       0,     0,     0,   423,     0,     0,     0,   101,     0,     0,
       0,     0,     0,     0,     0,    74,     0,    87,     0,    72,
      20,   112,     0,     0,     0,   231,   227,   243,   241,   242,
     240,     0,     0,   418,   417,    30,     0,   413,     0,   253,
       0,     0,     0,   420,     0,     0,   410,     0,   426,     0,
     427,   339,     0,    93,    79,     0,     0,     0,   404,   403,
       0,     0,     0,     0,     0,     0,   109,   334,   336,     0,
     209,     0,   239,    94,     0,    97,     0,     0,     0,   419,
       0,     0,     0,   102,   405,     0,     0,     0,    85,     0,
      82,    80,     0,   230,   245,   246,   244,   225,    89,     0,
     421,     0,     0,     0,   406,     0,     0,     0,   110,   228,
      96,   422,   424,    95,     0,     0,    86,   238,   407,     0,
      84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -648,  -648,  -648,   857,  -648,  -648,   -84,  -152,   167,  -648,
     516,  -648,  -648,   113,  1012,  -648,  -648,   166,   -38,  -648,
    -648,  -648,   822,   -78,    10,  -648,  -648,   845,  -648,  -317,
    -179,   112,  -648,   830,    48,   372,   738,  -142,  -648,  -648,
     495,  -191,   687,   199,  -648,   808,   849,   535,  -648,   837,
    -648,  -648,   861,     1,  -648,   269,  -648,  -648,   154,  -626,
    -648,  -117,  -472,  -648,  -648,  -648,  -318,  -648,  -648,  -648,
    -648,    69,  -648,  -648,   145,  -648,  -648,  -648,   279,   -89,
     -50,   844,   883,   917,  1037,  1269,   763,   386,   505,  -648,
     -17,  -648,  -648,   189,  -648,  -648,  -648,   521,  -648,   381,
    -648,  -648,  -648,  -647,  -648,  -648,  -648,  -648,     5,  -648,
    -105,   -27,  -148,  -648,  -186,  -144,  -201,  -136,  -131,   493,
    -648,  -648,  -648
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   188,   341,   342,   353,
     354,   514,   512,   752,   753,   918,   541,   542,   699,   560,
      13,    27,    28,   754,   343,    14,    31,    32,    15,   333,
     334,   335,    23,    24,   489,   193,   104,   105,   106,   107,
     194,   195,   376,   336,    39,    40,   229,   337,    34,    35,
      36,    37,   230,   617,   830,   166,   316,   836,   321,   482,
     483,   935,   936,   980,   338,   490,   491,   492,   493,   835,
    1007,   659,   660,   765,   766,   767,   339,   497,   498,   213,
     543,   153,   154,   155,   156,   157,   158,   231,   232,   159,
     724,   160,   591,   592,   161,   162,   218,   219,   544,   667,
     545,   546,   759,   631,   547,   910,   548,   863,   864,   661,
     264,   223,   265,   224,    76,   225,   226,   227,   266,   267,
     268,   269,   259
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
       152,   189,   271,   379,   258,   168,   760,   355,   172,   222,
     622,    75,    29,   494,   270,   317,   731,   500,   646,   775,
     689,   323,   324,   319,   216,   163,   775,   164,   344,   345,
     480,   347,   348,   768,   727,   257,   179,   180,   181,   182,
     174,   222,   641,   260,   172,   409,   416,   717,   475,    25,
     395,    26,    33,    38,   183,   184,   975,    84,   272,    29,
     425,   212,   214,   217,   718,   427,   233,   234,   235,   236,
     237,   238,   239,   240,   241,   242,   243,   244,   245,   246,
     247,   248,   249,   250,   251,   252,   253,   254,    75,   190,
     720,   398,   780,    25,   485,   849,   439,   273,    30,   725,
     422,   163,    33,   164,   346,   206,   594,   721,   772,   912,
      41,    81,    16,   472,     1,     2,     3,    42,     4,   207,
     405,    16,   325,    45,   327,    38,   726,   481,   423,   474,
     365,   424,   410,    82,   691,   349,   913,   179,   180,   181,
     182,   692,   693,   595,   694,   173,   695,    43,   696,   426,
     372,   406,   600,   423,   732,   183,   184,   165,   697,     5,
      85,   776,    86,    87,   185,   647,   413,   415,   781,   611,
     705,   626,   628,   420,   355,   396,   476,   175,    88,   417,
     426,   408,    89,    90,    91,    92,    93,   976,   433,   426,
     375,   914,   190,   426,   186,   410,   578,    47,   426,    17,
      48,   402,   552,   904,   891,   414,   414,   396,    17,   471,
     418,   826,   414,   421,    95,    96,    97,    98,    99,   100,
     101,   102,   423,   426,   431,   396,   432,   434,   850,   435,
     436,   437,   890,   742,   396,   442,   443,   444,   445,   446,
     447,   448,   449,   450,   451,   452,   453,   454,   455,   456,
     457,   458,   459,   460,   461,   462,   463,   464,   465,   466,
     467,   468,   469,   470,   593,   185,   319,   551,   579,   942,
     570,   396,   991,   584,   553,   554,   555,   495,   557,   558,
     396,   597,   278,   563,   564,     6,     7,   197,   197,   568,
     689,   177,   577,   736,   178,   186,   854,   396,   396,   609,
     356,   275,   357,   187,   198,   198,   414,   964,   784,    46,
     423,   556,   257,  1008,   559,   561,   562,   403,   603,   565,
     566,   567,   737,   192,   179,   180,   181,   182,   613,    66,
     278,    95,    96,    97,    98,    99,   100,   101,   102,   423,
      55,   736,   183,   184,   377,   170,   576,   171,   373,    47,
     580,   581,    48,   582,   283,   585,   503,   587,   644,   588,
     423,   590,   892,   650,   651,   652,   596,   405,   374,   599,
     981,    49,   602,   663,   664,   605,   504,   607,   608,   623,
     496,  1019,   379,    75,   569,   423,    50,   396,   477,   206,
     220,   423,   429,   505,   682,   312,   313,   506,   406,    52,
     365,   363,   283,   207,   691,    83,    19,    20,    21,    22,
    -153,    56,    57,   504,   694,    58,   695,   504,   696,   635,
     103,   638,   639,   640,   642,   643,   507,   501,   697,    47,
     619,   715,    48,   681,   508,   965,   624,   625,   689,   716,
     656,   396,    54,    55,   313,   629,   374,   676,   378,   763,
     764,   633,   185,   191,   504,   502,   509,   196,    48,   199,
      59,   201,   202,   203,   204,   205,    77,   735,   657,   214,
     982,   615,   700,   616,   662,   685,   504,    78,   666,   668,
     670,    79,   186,    60,   674,   675,   414,   677,   679,   683,
     552,     1,   701,   689,   350,     4,   330,   351,   331,   332,
      75,   352,   169,   680,   748,    62,    63,    64,    65,    66,
      67,   365,    84,   200,   637,   372,   372,   372,    58,   372,
     372,   255,   365,    55,   504,   372,   372,   369,   190,   370,
     372,    85,   256,    86,    87,    18,     5,   318,   369,   371,
     370,   671,   672,   673,    18,   326,   262,   328,   365,    88,
     371,   730,   691,    89,    90,    91,    92,    93,   423,   756,
     263,   734,   733,    59,   695,   396,   696,   757,   906,   382,
     383,   739,   274,   356,   782,   357,   697,   593,  -153,   504,
     390,   391,   392,   393,   275,     1,    60,   504,   827,     4,
     330,   747,   331,   332,   320,   749,   758,   372,    58,   385,
     372,   372,   372,   365,   372,   372,   438,   691,    62,    63,
      64,    65,    66,    67,   692,   693,   504,   694,   762,   695,
     709,   696,   710,   807,   808,   329,   866,   867,   811,   865,
       5,   697,   770,   340,   771,   844,   210,   790,   374,   915,
     793,   794,   795,    59,   359,   798,   799,   800,   916,  -153,
    -153,   388,   504,  -153,   504,   374,   806,   389,   190,   504,
     809,   810,   363,   812,   841,   813,    60,   814,   504,   634,
     815,   636,   816,   817,   531,   532,   533,   820,   853,   404,
     496,   823,   954,   955,   363,   407,   473,   831,    62,    63,
      64,    65,    66,    67,    94,   365,   406,   419,  -153,   691,
      66,   843,    95,    96,    97,    98,    99,   100,   101,   102,
     208,   695,   428,   696,   209,   440,   479,   414,   441,   478,
     210,  -153,   858,   697,   861,   862,   484,   211,   511,    19,
      20,    21,    22,   870,   871,   872,   486,    19,    20,    21,
      22,  -153,   488,  -153,  -153,  -153,  -153,  -153,  -153,   571,
     572,   208,   932,   513,   499,   209,   899,   620,   574,   575,
     621,   210,   363,   632,   959,   190,   627,   630,   211,   372,
     372,   645,   365,   372,   648,   649,   405,   713,   586,   738,
     326,   741,   740,   366,   367,   893,   368,   744,   369,   898,
     370,   750,   900,   751,   365,   755,   604,   930,   902,   480,
     371,    74,   783,   372,   381,   907,   769,   821,   368,   839,
     369,   108,   370,   208,   785,   842,   208,   209,   848,   919,
     209,   855,   371,   210,   923,   924,   210,   926,   868,   928,
     211,   869,   931,   211,   888,   894,   481,   933,   934,    19,
      20,    21,    22,  -153,   108,   108,   943,   944,   108,   614,
     108,   618,   108,   108,   108,   108,   108,   658,   952,   984,
     948,   372,   962,   979,   961,   990,    44,   802,   845,   550,
     967,   109,   365,    55,   487,    80,    51,    74,    74,   261,
     573,   380,    69,   366,   367,   176,   368,    68,   369,   208,
     370,   167,   372,   209,    53,   941,   743,   837,   414,   210,
     371,   986,  1027,    74,   988,   895,   211,    74,    74,    74,
     852,   745,   992,   665,   819,   589,   977,   958,   678,   606,
     997,    70,   999,   220,   583,  1002,    74,     0,   108,  1003,
      55,   384,    74,    74,     0,     0,   108,   208,   108,   208,
       0,   209,     0,   209,    56,    57,  1015,   210,    58,   210,
     386,     0,     0,     0,   211,    71,   211,   108,    69,    69,
     108,   108,    55,     0,     0,   208,  1025,     0,     0,   209,
     728,   108,   108,   108,   108,   210,     0,   208,   818,     0,
     220,   209,   211,     0,    69,   375,     0,   210,    69,    69,
      69,     0,     0,    59,   211,     0,     0,    70,    70,     0,
       0,    56,    57,     0,     0,    58,     0,    69,     0,     0,
     957,     0,   220,    69,    69,     0,    60,     0,     0,     0,
       0,     0,     0,    70,     0,     0,     0,    70,    70,    70,
     278,    71,    71,    56,    57,     0,   221,    58,    62,    63,
      64,    65,    66,    67,     0,    74,    70,     0,    55,     0,
      59,     0,    70,    70,     0,     0,     0,    71,     0,     0,
       0,    71,    71,    71,     1,     0,     0,   350,     4,   330,
     351,   331,   332,    60,   549,    72,     0,    74,    74,     0,
      71,     0,    59,     0,     0,     0,    71,    71,     0,     0,
       0,     0,     0,   221,     0,    62,    63,    64,    65,    66,
      67,   282,   283,     0,     0,    60,   825,   208,     0,     5,
     832,   209,     0,     0,   834,   284,   838,   210,   387,    56,
      57,     0,     0,    58,   211,   221,    69,    62,    63,    64,
      65,    66,    67,     0,     0,     0,     0,     0,    74,     0,
     108,   108,     0,   312,   313,     0,     0,     0,   108,   108,
     108,    72,    72,     0,     0,     0,     0,     0,    69,    69,
      74,     0,    74,   314,   315,    70,     0,    74,    59,   108,
       0,   108,     0,    74,     0,    74,     0,    72,     0,     0,
      74,    72,    72,    72,     0,     0,    74,   108,     0,    74,
       0,    60,     0,    74,     0,     0,     0,    70,    70,    71,
      72,   358,   359,     0,     0,     0,    72,    72,     0,     0,
       0,    61,     0,    62,    63,    64,    65,    66,    67,    69,
     363,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    71,    71,     0,     0,     0,     0,     0,     0,    74,
     108,    69,   108,    69,     0,   208,     0,    74,    69,   209,
       0,     0,     0,     0,    69,   210,    69,     0,    70,     0,
      55,    69,   211,     0,   938,   940,     0,    69,     0,     0,
      69,     0,     0,     0,    69,     0,     0,   896,   949,     0,
      70,     0,    70,     0,     0,     0,     0,    70,     0,     0,
      74,     0,    71,    70,     0,    70,     0,     0,     0,     0,
      70,    74,     0,     0,     0,     0,    70,    73,     0,    70,
       0,     0,     0,    70,    71,     0,    71,     0,   897,    72,
      69,    71,     0,     0,     0,     0,     0,    71,    69,    71,
     365,    56,    57,     0,    71,    58,   108,     0,     0,     0,
      71,   366,   367,    71,   368,     0,   369,    71,   370,     0,
       0,    72,    72,     0,     0,     0,     0,     0,   371,    70,
       0,   108,     0,     0,   540,     0,     0,    70,     0,     0,
       0,    69,    85,     0,    86,    87,     0,     0,    74,     0,
      59,     0,    69,    73,    73,     0,     0,     0,     0,     0,
      88,     0,     0,    71,    89,    90,    91,    92,    93,     0,
       0,    71,     0,    60,     0,     0,     0,     0,     0,    73,
      70,     0,    72,    73,    73,    73,     0,     0,     0,     0,
       0,    70,     0,   228,     0,    62,    63,    64,    65,    66,
      67,     0,    73,     0,    72,     0,    72,     0,    73,    73,
       0,    72,    74,     0,    71,     0,     0,    72,     0,    72,
     358,   359,   360,     0,    72,    71,   361,     0,     0,    69,
      72,     0,     0,    72,     0,     0,     0,    72,   362,   363,
       0,   684,   685,   686,     0,     0,     0,   687,     0,     0,
       0,     0,     0,     0,   684,   685,     0,     0,    74,   688,
     689,     0,     0,     0,     0,     0,     0,   108,    70,    74,
       0,   108,     0,   689,     0,   108,     0,   108,     0,     0,
       0,     0,     0,    72,     0,     0,     0,     0,     0,     0,
       0,    72,     0,    69,     0,     0,     0,     0,   714,     0,
     655,     0,    71,     0,     0,   192,     0,     0,     0,     0,
      74,    66,     0,    95,    96,    97,    98,    99,   100,   101,
     102,    73,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   704,    70,    85,    72,    86,    87,     0,     0,    69,
       0,     0,     0,     0,     0,    72,     0,     0,   364,   365,
      69,    88,     0,    73,    73,    89,    90,    91,    92,    93,
     366,   367,     0,   368,     0,   369,    71,   370,     0,   690,
     365,     0,     0,     0,   691,     0,     0,   371,    70,     0,
       0,   692,   693,   365,   694,     0,   695,   691,   696,    70,
       0,    69,     0,     0,   692,   693,     0,   694,   697,   695,
     698,   696,     0,     0,     0,     0,     0,   278,     0,     0,
       0,   697,    71,     0,    73,     0,    74,     0,     0,     0,
       0,     0,    72,    71,     0,   108,   108,     0,     0,     0,
      70,     0,     0,     0,     0,     0,    73,     0,    73,   108,
       0,     0,     0,    73,     0,    74,     0,     0,     0,    73,
       0,    73,     0,     0,     0,     0,    73,     0,     0,     0,
       0,     0,    73,     0,    71,    73,   787,   788,   789,    73,
     791,   792,     0,     0,     0,   796,   797,     0,   282,   283,
       0,     0,   801,     0,   803,   804,    72,     0,     0,     0,
       0,     0,   284,     0,     0,     0,   192,    69,     0,     0,
       0,     0,    66,     0,    95,    96,    97,    98,    99,   100,
     101,   102,   304,     0,     0,    73,   308,   309,   310,   311,
     312,   313,     0,    73,     0,     0,    69,     0,     0,     0,
       0,     0,    72,     0,     0,     0,    70,     0,   846,   847,
     314,   315,     0,    72,   851,     0,     0,     0,     0,     0,
       0,     0,     0,   856,   857,   859,   860,     0,     0,     0,
       0,     0,     0,     0,     0,    70,    73,     0,   873,     0,
      71,     0,     0,     0,     0,     0,     0,    73,     0,     0,
       0,     0,     0,     0,    72,     0,   358,   359,   360,     0,
     746,     0,   361,     0,     0,     0,     0,     0,     0,    71,
       0,     0,     0,     0,   362,   363,     0,     0,     0,     0,
       0,    85,     0,   833,    87,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   901,     0,     0,    88,
       0,   903,     0,    89,    90,    91,    92,    93,     0,     0,
       0,     0,     0,     0,     0,     0,    85,     0,   937,    87,
     917,     0,     0,     0,    73,     0,     0,     0,   925,     0,
     927,     0,   929,    85,    88,   939,    87,     0,    89,    90,
      91,    92,    93,     0,     0,     0,     0,     0,     0,     0,
       0,    88,     0,     0,     0,    89,    90,    91,    92,    93,
      72,   953,     0,   956,     0,     0,   960,     0,   963,     0,
       0,     0,     0,   968,   969,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   364,   365,     0,     0,    73,    72,
       0,     0,     0,     0,     0,     0,   366,   367,   983,   368,
       0,   369,   985,   370,     0,     0,     0,   989,     0,     0,
       0,     0,     0,   371,     0,     0,     0,   993,   994,     0,
       0,     0,     0,   998,     0,  1000,  1001,     0,     0,     0,
       0,     0,     0,  1006,    73,     0,     0,     0,     0,  1010,
       0,     0,  1012,   960,    94,    73,     0,  1014,     0,     0,
      66,     0,    95,    96,    97,    98,    99,   100,   101,   102,
       0,  1020,     0,  1021,     0,  1023,     0,     0,     0,  1026,
       0,     0,     0,     0,     0,     0,  1028,     0,     0,    94,
       0,  1030,     0,     0,     0,    66,    73,    95,    96,    97,
      98,    99,   100,   101,   102,     0,    94,     0,     0,     0,
       0,     0,    66,     0,    95,    96,    97,    98,    99,   100,
     101,   102,   515,   516,   517,   518,     0,     0,   519,     0,
     520,   521,   522,   523,   524,     0,     0,   113,     0,     0,
     525,   526,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   278,     0,     0,     0,     0,     0,     0,     0,     0,
     114,  1004,  1005,     0,     0,     0,   527,     0,   528,   279,
       0,     0,   529,     0,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,     0,     0,   138,   139,     0,     0,
      58,   140,    73,   141,   142,   143,     0,     0,     0,     0,
       0,   530,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   282,   283,     0,     0,     0,     0,     0,     0,
       0,    73,     0,     0,     0,     0,   284,   285,   286,   287,
     288,     0,   144,   145,   146,    59,   531,   532,   533,     0,
     534,     0,     0,     0,     0,   535,   304,   305,   147,     0,
     308,   309,   310,   311,   312,   313,     0,     0,    60,     0,
     536,   537,   538,     0,     0,   539,   148,     0,   149,     0,
       0,     0,     0,     0,   314,   315,     0,     0,   150,   151,
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
     304,     0,   147,     0,   308,   309,   310,   311,   312,   313,
       0,     0,    60,     0,   536,   537,   538,     0,     0,   539,
     148,     0,   149,     0,     0,     0,     0,     0,   314,   315,
       0,     0,   654,   151,    62,    63,    64,    65,    66,    67,
     515,   516,   517,   518,     0,     0,   519,     0,   520,   521,
     522,   523,   524,     0,     0,   113,     0,     0,   525,   526,
       0,     0,     0,     0,     0,     0,     0,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   114,     0,
       0,     0,     0,     0,   527,     0,   528,     0,     0,     0,
     529,     0,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,     0,     0,   138,   139,     0,     0,    58,   140,
       0,   141,   142,   143,     0,     0,     0,     0,     0,   530,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,     0,     0,     0,     0,     0,     0,
     144,   145,   146,    59,   531,   532,   533,     0,   534,     0,
       0,     0,     0,   535,     0,     0,   147,   308,   309,   310,
     311,   312,   313,     0,     0,     0,    60,     0,   536,   537,
     538,     0,     0,   539,   148,     0,   149,     0,     0,     0,
       0,   314,   315,     0,     0,     0,   150,   151,    62,    63,
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
     654,   151,    62,    63,    64,    65,    66,    67,   114,   828,
     829,     0,     0,     0,   115,     0,   116,     0,     0,     0,
     117,     0,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,     0,     0,   138,   139,     0,     0,    58,   140,
       0,   141,   142,   143,   690,   365,     0,     0,     0,   691,
       0,     0,   684,   685,   686,     0,   692,   693,   687,   694,
       0,   695,     0,   696,     0,     0,     0,     0,     0,     0,
     688,   689,     0,   697,     0,   805,     0,     0,     0,     0,
     144,   145,   146,    59,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   147,   110,     0,   111,
       0,     0,   112,     0,     0,     0,    60,     0,     0,     0,
       0,   113,   215,     0,   148,     0,   149,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   150,   151,    62,    63,
      64,    65,    66,    67,   114,     0,     0,     0,     0,     0,
     115,     0,   116,     0,     0,     0,   117,     0,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     0,     0,
     138,   139,     0,     0,    58,   140,     0,   141,   142,   143,
     690,   365,     0,     0,     0,   691,     0,     0,   684,   685,
     686,     0,   692,   693,   687,   694,     0,   695,     0,   696,
       0,     0,     0,     0,     0,     0,   688,   689,     0,   697,
       0,  1022,     0,     0,     0,     0,   144,   145,   146,    59,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   147,   110,     0,   111,   908,   909,   112,     0,
       0,     0,    60,     0,     0,     0,     0,   113,     0,     0,
     148,     0,   149,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   150,   151,    62,    63,    64,    65,    66,    67,
     114,     0,     0,   658,     0,     0,   115,     0,   116,     0,
       0,     0,   117,     0,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,     0,     0,   138,   139,     0,     0,
      58,   140,     0,   141,   142,   143,   690,   365,     0,     0,
       0,   691,     0,     0,     0,     0,     0,     0,   692,   693,
       0,   694,     0,   695,     0,   696,     0,     0,   684,   685,
     686,     0,   761,     0,   687,   697,     0,     0,     0,     0,
       0,     0,   144,   145,   146,    59,   688,   689,     0,     0,
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
       0,     0,     0,     0,     0,     0,   690,   365,     0,     0,
       0,   691,     0,     0,     0,     0,     0,     0,   692,   693,
       0,   694,     0,   695,     0,   696,     0,     0,   144,   145,
     146,    59,     0,     0,     0,   697,     0,     0,     0,     0,
       0,     0,     0,     0,   147,   110,     0,   111,     0,     0,
     112,     0,     0,     0,    60,     0,     0,     0,     0,   113,
       0,     0,   148,     0,   149,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   150,   151,    62,    63,    64,    65,
      66,    67,   114,     0,     0,     0,     0,     0,   115,     0,
     116,     0,     0,     0,   117,     0,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,     0,     0,   138,   139,
       0,     0,    58,   140,     0,   141,   142,   143,   276,     0,
       0,     0,     0,   277,     0,   278,     0,   684,   685,   686,
       0,   905,     0,   687,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   279,     0,   688,   689,   684,   685,   686,
       0,   947,     0,   687,   144,   145,   146,    59,     0,     0,
       0,     0,     0,     0,     0,   688,   689,     0,   684,   685,
     147,     0,   280,   281,   687,     0,     0,     0,     0,     0,
      60,     0,     0,     0,     0,     0,   688,   689,   148,     0,
     149,     0,     0,     0,     0,     0,   282,   283,     0,     0,
     150,   151,    62,    63,    64,    65,    66,    67,     0,     0,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
       0,     0,     0,   684,   685,   686,     0,   278,     0,   687,
       0,     0,     0,     0,     0,   690,   365,     0,   314,   315,
     691,   688,   689,   358,   359,   279,     0,   692,   693,   361,
     694,     0,   695,     0,   696,   690,   365,     0,     0,   978,
     691,   362,   363,     0,   697,     0,     0,   692,   693,     0,
     694,     0,   695,     0,   696,     0,   690,   365,     0,     0,
       0,   691,     0,     0,   697,     0,     0,     0,   692,   693,
       0,   694,     0,   695,     0,   696,     0,     0,   282,   283,
       0,     0,     0,     0,     0,   697,     0,     0,     0,     0,
       0,     0,   284,   285,   286,   287,   288,     0,     0,   276,
       0,     0,     0,     0,   277,     0,   278,     0,     0,     0,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,     0,     0,   279,     0,     0,     0,     0,     0,
       0,   690,   365,     0,     0,     0,   691,     0,     0,     0,
     314,   315,     0,   692,   693,     0,   694,     0,   695,     0,
     696,   364,   365,   280,   281,     0,     0,     0,     0,     0,
     697,     0,     0,   366,   367,     0,   368,     0,   369,     0,
     370,     0,   278,     0,     0,     0,     0,   282,   283,     0,
     371,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     279,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   276,     0,     0,     0,     0,   277,     0,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,     0,     0,   282,   283,     0,   279,     0,     0,     0,
     995,     0,     0,   996,     0,     0,     0,   284,   285,   286,
     287,   288,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,   314,   315,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,     0,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,   786,     0,     0,     0,     0,     0,     0,     0,
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
     279,     0,     0,     0,   921,     0,     0,     0,     0,     0,
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
       0,     0,   279,     0,     0,     0,   922,     0,     0,     0,
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
       0,     0,     0,     0,   279,     0,     0,     0,   966,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   282,   283,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,     0,   276,     0,     0,     0,     0,   277,     0,   278,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,   874,   875,     0,     0,     0,     0,   279,     0,     0,
    1024,     0,     0,     0,     0,     0,     0,     0,     0,     0,
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
       0,     0,   314,   315,   706,     0,     0,     0,   876,   279,
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
     707,   279,     0,     0,     0,     0,     0,     0,     0,     0,
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
     712,     0,     0,   279,     0,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,   702,     0,     0,   314,   315,
       0,     0,   880,     0,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     722,   723,     0,     0,   280,   281,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,     0,   276,     0,     0,     0,     0,   277,   703,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     314,   315,   399,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   400,   401,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   276,     0,     0,     0,     0,
     277,   394,   278,     0,     0,     0,     0,     0,     0,     0,
     702,     0,     0,   314,   315,     0,     0,     0,     0,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   282,   283,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,     0,   276,     0,
       0,     0,     0,   277,   703,   278,     0,     0,     0,     0,
       0,     0,     0,   702,     0,   314,   315,     0,     0,     0,
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
       0,   276,     0,     0,     0,     0,   277,   703,   278,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
       0,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   722,   723,     0,     0,   280,   281,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   282,
     283,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   276,     0,     0,     0,     0,   277,     0,
     278,     0,     0,     0,     0,     0,     0,     0,   773,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   774,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,     0,   276,     0,     0,     0,
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   314,   315,   877,     0,     0,     0,     0,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,     0,   276,
       0,     0,     0,     0,   277,     0,   278,     0,   950,   878,
       0,     0,     0,     0,     0,     0,   314,   315,   951,     0,
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
     313,     0,   276,     0,     0,     0,     0,   277,     0,   278,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,   972,     0,     0,     0,     0,     0,   279,     0,     0,
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
     322,   278,     0,     0,     0,   973,     0,     0,     0,     0,
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
       0,   277,   394,   278,     0,     0,     0,     0,     0,     0,
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
     306,   307,   308,   309,   310,   311,   312,   313,     0,   276,
       0,     0,     0,     0,   277,     0,   278,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   314,   315,   397,     0,
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
     411,     0,     0,     0,     0,   280,   281,     0,     0,     0,
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
     309,   310,   311,   312,   313,     0,   276,     0,     0,     0,
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
     412,     0,     0,   314,   315,   430,     0,     0,     0,     0,
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
       0,   510,     0,     0,     0,     0,     0,     0,   314,   315,
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
       0,     0,     0,     0,   598,     0,     0,     0,     0,     0,
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
     610,   278,     0,     0,     0,     0,   601,     0,     0,     0,
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
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,     0,     0,     0,     0,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     612,     0,     0,     0,   282,   283,     0,     0,     0,     0,
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
       0,   708,     0,     0,     0,     0,   711,     0,   314,   315,
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
     312,   313,     0,     0,   276,     0,     0,     0,     0,   277,
       0,   278,     0,     0,     0,     0,     0,     0,     0,     0,
     314,   315,   719,     0,     0,     0,     0,     0,     0,   279,
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
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   401,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   276,     0,
       0,     0,     0,   277,   729,   278,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
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
       0,     0,     0,     0,     0,     0,     0,   777,     0,     0,
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
       0,   278,     0,     0,     0,   778,     0,     0,     0,     0,
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
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
     779,   773,     0,     0,   314,   315,     0,     0,     0,     0,
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
       0,     0,     0,   277,   822,   278,     0,     0,     0,     0,
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
     276,     0,     0,     0,     0,   277,   824,   278,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
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
       0,   840,     0,     0,     0,     0,     0,     0,     0,     0,
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
     310,   311,   312,   313,     0,   276,     0,     0,     0,     0,
     277,     0,   278,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   314,   315,   879,     0,     0,     0,     0,     0,
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
       0,     0,   277,     0,   278,     0,   881,     0,     0,     0,
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
       0,     0,     0,     0,   277,     0,   278,     0,     0,     0,
       0,     0,     0,     0,     0,   882,     0,   314,   315,     0,
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
     313,     0,   276,     0,     0,     0,     0,   277,     0,   278,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   314,
     315,   883,     0,     0,     0,     0,     0,   279,     0,     0,
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
       0,   278,     0,   884,     0,     0,     0,     0,     0,     0,
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
     308,   309,   310,   311,   312,   313,     0,     0,   276,     0,
       0,     0,     0,   277,     0,   278,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,   885,     0,     0,     0,
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
       0,     0,   276,     0,     0,     0,     0,   277,     0,   278,
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
     886,     0,     0,     0,     0,     0,     0,   279,     0,     0,
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
       0,     0,     0,   887,     0,     0,     0,     0,   280,   281,
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
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   276,     0,
       0,     0,     0,   277,     0,   278,     0,     0,     0,     0,
       0,     0,     0,   911,     0,   889,   314,   315,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
       0,     0,     0,     0,     0,   279,   920,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   281,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   276,     0,     0,     0,     0,   277,   945,   278,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
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
       0,   278,     0,   946,     0,     0,     0,     0,     0,     0,
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
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,   970,     0,     0,     0,
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
       0,     0,     0,   277,     0,   278,     0,   971,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
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
       0,     0,     0,     0,     0,     0,   974,     0,     0,     0,
     314,   315,     0,     0,     0,     0,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   987,     0,     0,     0,     0,   280,   281,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     282,   283,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   276,     0,     0,     0,     0,   277,
       0,   278,     0,  1009,     0,     0,     0,     0,     0,     0,
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
       0,   277,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   314,   315,     0,     0,     0,     0,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,  1011,     0,     0,     0,     0,
     280,   281,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   282,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   276,     0,
       0,     0,     0,   277,     0,   278,     0,  1013,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,   314,   315,
    1016,     0,     0,     0,     0,     0,   279,     0,     0,     0,
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
     277,     0,   278,     0,     0,     0,  1017,     0,     0,     0,
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
       0,     0,   277,     0,   278,     0,  1029,  1018,     0,     0,
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
       0,     0,     0,     0,   277,     0,   278,     0,     0,     0,
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
     313,   276,     0,     0,     0,     0,     0,     0,   278,     0,
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
     311,   312,   313,   276,     0,     0,     0,     0,     0,     0,
     278,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   314,   315,     0,     0,     0,     0,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   282,   283,   276,     0,     0,     0,     0,     0,     0,
     278,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   279,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,     0,     0,     0,     0,     0,
       0,     0,     0,   276,     0,     0,     0,   280,   281,     0,
     278,     0,     0,   314,   315,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   279,     0,
       0,   282,   283,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   280,   281,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,     0,     0,     0,     0,     0,
     276,   282,   283,     0,     0,     0,     0,   278,     0,     0,
       0,     0,     0,   314,   315,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   279,   295,     0,     0,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   281,     0,   278,     0,     0,
       0,     0,     0,   314,   315,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   279,     0,     0,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,     0,     0,   280,   281,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,     0,     0,     0,     0,     0,     0,   282,   283,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     314,   315,   284,   285,   286,   287,   288,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     314,   315
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
        50,    79,   146,   194,   140,    55,   653,   186,    12,   114,
     482,    38,     2,   331,   145,   163,    13,   334,    33,    31,
      33,   169,   170,   165,   113,    17,    31,    19,   180,   181,
      49,   183,   184,   659,    27,   140,    14,    15,    16,    17,
      12,   146,    20,    17,    12,    59,    13,    13,    18,     1,
      21,   180,     4,     5,    32,    33,    13,    47,   147,    49,
      13,   111,   112,   113,    30,    13,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   115,    79,
      13,    30,    13,    45,    18,    13,   282,   147,   180,   146,
     118,    17,    54,    19,   182,    90,    18,    30,    59,   146,
     180,    12,     0,   314,     3,     4,     5,   180,     7,   104,
     118,     9,   172,    33,   174,    77,   173,   146,   146,   315,
     143,   149,   146,    34,   147,   185,   173,    14,    15,    16,
      17,   154,   155,    18,   157,   149,   159,     0,   161,   146,
     188,   149,    18,   146,   151,    32,    33,   149,   171,    48,
      17,   173,    19,    20,   142,   180,   255,   256,   173,    18,
      18,   488,   490,   262,   353,   146,   146,   149,    35,   146,
     146,   149,    39,    40,    41,    42,    43,   144,   277,   146,
      47,    27,   182,   146,   172,   146,   397,   146,   146,     0,
     149,    30,   180,   850,   830,   255,   256,   146,     9,   314,
     260,    18,   262,   263,   188,   189,   190,   191,   192,   193,
     194,   195,   146,   146,   274,   146,   276,   277,   146,   279,
     280,   281,    18,   149,   146,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   412,   142,   408,   356,   399,   895,
     375,   146,    27,   404,   358,   359,   360,    47,   362,   363,
     146,   417,    19,   367,   368,   174,   175,    27,    27,   373,
      33,   169,   397,    17,   172,   172,   768,   146,   146,   430,
      17,    98,    19,   180,    44,    44,   356,    18,    59,   180,
     146,   361,   417,    18,   364,   365,   366,   146,   423,   369,
     370,   371,    46,   180,    14,    15,    16,    17,   476,   186,
      19,   188,   189,   190,   191,   192,   193,   194,   195,   146,
      17,    17,    32,    33,    45,    17,   396,    19,    13,   146,
     400,   401,   149,   403,    91,   405,    13,   407,   510,   409,
     146,   411,   102,   515,   516,   517,   416,   118,    33,   419,
      46,    33,   422,   525,   526,   425,    33,   427,   428,   484,
     332,  1007,   573,   410,   374,   146,    12,   146,   149,    90,
      67,   146,   151,    13,   538,   132,   133,    13,   149,   180,
     143,    33,    91,   104,   147,   180,   176,   177,   178,   179,
     180,    88,    89,    33,   157,    92,   159,    33,   161,   503,
      48,   505,   506,   507,   508,   509,    13,   118,   171,   146,
     480,    13,   149,   538,    13,   146,   486,   487,    33,    21,
     518,   146,    33,    17,   133,   495,    33,   536,   149,    56,
      57,   501,   142,    81,    33,   146,    13,    85,   149,    87,
     137,    89,    90,    91,    92,    93,    33,   615,   518,   519,
     942,    17,    13,    19,   524,    15,    33,    12,   528,   529,
     530,    12,   172,   160,   534,   535,   536,   537,   165,   539,
     180,     3,    33,    33,     6,     7,     8,     9,    10,    11,
     527,    13,    17,   180,    13,   182,   183,   184,   185,   186,
     187,   143,   502,   180,   504,   553,   554,   555,    92,   557,
     558,    17,   143,    17,    33,   563,   564,   159,   518,   161,
     568,    17,    17,    19,    20,     0,    48,   165,   159,   171,
     161,   531,   532,   533,     9,   173,    17,   175,   143,    35,
     171,   601,   147,    39,    40,    41,    42,    43,   146,    13,
      17,   149,   612,   137,   159,   146,   161,    13,   149,   197,
     198,   621,    17,    17,   679,    19,   171,   725,    17,    33,
     208,   209,   210,   211,    98,     3,   160,    33,   736,     7,
       8,   641,    10,    11,   180,   645,    13,   635,    92,    36,
     638,   639,   640,   143,   642,   643,   180,   147,   182,   183,
     184,   185,   186,   187,   154,   155,    33,   157,    13,   159,
     144,   161,   146,   707,   708,   180,   778,   779,   712,   777,
      48,   171,    13,   180,    13,    13,    37,   687,    33,    13,
     690,   691,   692,   137,    15,   695,   696,   697,    13,    88,
      89,   180,    33,    92,    33,    33,   706,   180,   648,    33,
     710,   711,    33,   713,   748,   715,   160,   717,    33,   502,
     720,   504,   722,   723,   138,   139,   140,   727,   767,    31,
     632,   731,    62,    63,    33,    13,   180,   737,   182,   183,
     184,   185,   186,   187,   180,   143,   149,    17,   137,   147,
     186,   751,   188,   189,   190,   191,   192,   193,   194,   195,
      27,   159,    13,   161,    31,    92,   149,   767,   180,   180,
      37,   160,   772,   171,   774,   775,   146,    44,   180,   176,
     177,   178,   179,   783,   784,   785,    12,   176,   177,   178,
     179,   180,   172,   182,   183,   184,   185,   186,   187,   377,
     378,    27,   883,   180,   173,    31,   840,   180,   386,   387,
      50,    37,    33,    33,   912,   755,   180,   180,    44,   807,
     808,    12,   143,   811,    12,   180,   118,    46,   406,   149,
     408,   173,    18,   154,   155,   102,   157,   149,   159,   839,
     161,   180,   842,    12,   143,    12,   424,   881,   848,    49,
     171,    38,    12,   841,    18,   855,    21,    18,   157,    17,
     159,    48,   161,    27,    59,    12,    27,    31,    20,   869,
      31,    20,   171,    37,   874,   875,    37,   877,    17,   879,
      44,   118,   882,    44,    27,   180,   146,   887,   888,   176,
     177,   178,   179,   180,    81,    82,   896,   897,    85,   477,
      87,   479,    89,    90,    91,    92,    93,    55,   908,   948,
      17,   899,    20,    51,   914,    27,     9,   701,   755,   353,
     920,    49,   143,    17,    12,    45,    31,   114,   115,   141,
     385,   194,    38,   154,   155,    77,   157,    38,   159,    27,
     161,    54,   930,    31,    33,   894,   627,   743,   948,    37,
     171,   951,  1019,   140,   954,   836,    44,   144,   145,   146,
     765,   632,   962,   527,   725,   410,   933,   912,   537,   426,
     970,    38,   972,    67,   403,   975,   163,    -1,   165,   979,
      17,    21,   169,   170,    -1,    -1,   173,    27,   175,    27,
      -1,    31,    -1,    31,    88,    89,   996,    37,    92,    37,
      38,    -1,    -1,    -1,    44,    38,    44,   194,   114,   115,
     197,   198,    17,    -1,    -1,    27,  1016,    -1,    -1,    31,
     598,   208,   209,   210,   211,    37,    -1,    27,    65,    -1,
      67,    31,    44,    -1,   140,    47,    -1,    37,   144,   145,
     146,    -1,    -1,   137,    44,    -1,    -1,   114,   115,    -1,
      -1,    88,    89,    -1,    -1,    92,    -1,   163,    -1,    -1,
      65,    -1,    67,   169,   170,    -1,   160,    -1,    -1,    -1,
      -1,    -1,    -1,   140,    -1,    -1,    -1,   144,   145,   146,
      19,   114,   115,    88,    89,    -1,   180,    92,   182,   183,
     184,   185,   186,   187,    -1,   282,   163,    -1,    17,    -1,
     137,    -1,   169,   170,    -1,    -1,    -1,   140,    -1,    -1,
      -1,   144,   145,   146,     3,    -1,    -1,     6,     7,     8,
       9,    10,    11,   160,    13,    38,    -1,   314,   315,    -1,
     163,    -1,   137,    -1,    -1,    -1,   169,   170,    -1,    -1,
      -1,    -1,    -1,   180,    -1,   182,   183,   184,   185,   186,
     187,    90,    91,    -1,    -1,   160,   734,    27,    -1,    48,
     738,    31,    -1,    -1,   742,   104,   744,    37,    38,    88,
      89,    -1,    -1,    92,    44,   180,   282,   182,   183,   184,
     185,   186,   187,    -1,    -1,    -1,    -1,    -1,   375,    -1,
     377,   378,    -1,   132,   133,    -1,    -1,    -1,   385,   386,
     387,   114,   115,    -1,    -1,    -1,    -1,    -1,   314,   315,
     397,    -1,   399,   152,   153,   282,    -1,   404,   137,   406,
      -1,   408,    -1,   410,    -1,   412,    -1,   140,    -1,    -1,
     417,   144,   145,   146,    -1,    -1,   423,   424,    -1,   426,
      -1,   160,    -1,   430,    -1,    -1,    -1,   314,   315,   282,
     163,    14,    15,    -1,    -1,    -1,   169,   170,    -1,    -1,
      -1,   180,    -1,   182,   183,   184,   185,   186,   187,   375,
      33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   314,   315,    -1,    -1,    -1,    -1,    -1,    -1,   476,
     477,   397,   479,   399,    -1,    27,    -1,   484,   404,    31,
      -1,    -1,    -1,    -1,   410,    37,   412,    -1,   375,    -1,
      17,   417,    44,    -1,   892,   893,    -1,   423,    -1,    -1,
     426,    -1,    -1,    -1,   430,    -1,    -1,    59,   906,    -1,
     397,    -1,   399,    -1,    -1,    -1,    -1,   404,    -1,    -1,
     527,    -1,   375,   410,    -1,   412,    -1,    -1,    -1,    -1,
     417,   538,    -1,    -1,    -1,    -1,   423,    38,    -1,   426,
      -1,    -1,    -1,   430,   397,    -1,   399,    -1,   100,   282,
     476,   404,    -1,    -1,    -1,    -1,    -1,   410,   484,   412,
     143,    88,    89,    -1,   417,    92,   573,    -1,    -1,    -1,
     423,   154,   155,   426,   157,    -1,   159,   430,   161,    -1,
      -1,   314,   315,    -1,    -1,    -1,    -1,    -1,   171,   476,
      -1,   598,    -1,    -1,   352,    -1,    -1,   484,    -1,    -1,
      -1,   527,    17,    -1,    19,    20,    -1,    -1,   615,    -1,
     137,    -1,   538,   114,   115,    -1,    -1,    -1,    -1,    -1,
      35,    -1,    -1,   476,    39,    40,    41,    42,    43,    -1,
      -1,   484,    -1,   160,    -1,    -1,    -1,    -1,    -1,   140,
     527,    -1,   375,   144,   145,   146,    -1,    -1,    -1,    -1,
      -1,   538,    -1,   180,    -1,   182,   183,   184,   185,   186,
     187,    -1,   163,    -1,   397,    -1,   399,    -1,   169,   170,
      -1,   404,   679,    -1,   527,    -1,    -1,   410,    -1,   412,
      14,    15,    16,    -1,   417,   538,    20,    -1,    -1,   615,
     423,    -1,    -1,   426,    -1,    -1,    -1,   430,    32,    33,
      -1,    14,    15,    16,    -1,    -1,    -1,    20,    -1,    -1,
      -1,    -1,    -1,    -1,    14,    15,    -1,    -1,   725,    32,
      33,    -1,    -1,    -1,    -1,    -1,    -1,   734,   615,   736,
      -1,   738,    -1,    33,    -1,   742,    -1,   744,    -1,    -1,
      -1,    -1,    -1,   476,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   484,    -1,   679,    -1,    -1,    -1,    -1,   173,    -1,
     518,    -1,   615,    -1,    -1,   180,    -1,    -1,    -1,    -1,
     777,   186,    -1,   188,   189,   190,   191,   192,   193,   194,
     195,   282,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   549,   679,    17,   527,    19,    20,    -1,    -1,   725,
      -1,    -1,    -1,    -1,    -1,   538,    -1,    -1,   142,   143,
     736,    35,    -1,   314,   315,    39,    40,    41,    42,    43,
     154,   155,    -1,   157,    -1,   159,   679,   161,    -1,   142,
     143,    -1,    -1,    -1,   147,    -1,    -1,   171,   725,    -1,
      -1,   154,   155,   143,   157,    -1,   159,   147,   161,   736,
      -1,   777,    -1,    -1,   154,   155,    -1,   157,   171,   159,
     173,   161,    -1,    -1,    -1,    -1,    -1,    19,    -1,    -1,
      -1,   171,   725,    -1,   375,    -1,   883,    -1,    -1,    -1,
      -1,    -1,   615,   736,    -1,   892,   893,    -1,    -1,    -1,
     777,    -1,    -1,    -1,    -1,    -1,   397,    -1,   399,   906,
      -1,    -1,    -1,   404,    -1,   912,    -1,    -1,    -1,   410,
      -1,   412,    -1,    -1,    -1,    -1,   417,    -1,    -1,    -1,
      -1,    -1,   423,    -1,   777,   426,   684,   685,   686,   430,
     688,   689,    -1,    -1,    -1,   693,   694,    -1,    90,    91,
      -1,    -1,   700,    -1,   702,   703,   679,    -1,    -1,    -1,
      -1,    -1,   104,    -1,    -1,    -1,   180,   883,    -1,    -1,
      -1,    -1,   186,    -1,   188,   189,   190,   191,   192,   193,
     194,   195,   124,    -1,    -1,   476,   128,   129,   130,   131,
     132,   133,    -1,   484,    -1,    -1,   912,    -1,    -1,    -1,
      -1,    -1,   725,    -1,    -1,    -1,   883,    -1,   756,   757,
     152,   153,    -1,   736,   762,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   771,   772,   773,   774,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   912,   527,    -1,   786,    -1,
     883,    -1,    -1,    -1,    -1,    -1,    -1,   538,    -1,    -1,
      -1,    -1,    -1,    -1,   777,    -1,    14,    15,    16,    -1,
      18,    -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,   912,
      -1,    -1,    -1,    -1,    32,    33,    -1,    -1,    -1,    -1,
      -1,    17,    -1,    19,    20,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   844,    -1,    -1,    35,
      -1,   849,    -1,    39,    40,    41,    42,    43,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    17,    -1,    19,    20,
     868,    -1,    -1,    -1,   615,    -1,    -1,    -1,   876,    -1,
     878,    -1,   880,    17,    35,    19,    20,    -1,    39,    40,
      41,    42,    43,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    35,    -1,    -1,    -1,    39,    40,    41,    42,    43,
     883,   909,    -1,   911,    -1,    -1,   914,    -1,   916,    -1,
      -1,    -1,    -1,   921,   922,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   142,   143,    -1,    -1,   679,   912,
      -1,    -1,    -1,    -1,    -1,    -1,   154,   155,   946,   157,
      -1,   159,   950,   161,    -1,    -1,    -1,   955,    -1,    -1,
      -1,    -1,    -1,   171,    -1,    -1,    -1,   965,   966,    -1,
      -1,    -1,    -1,   971,    -1,   973,   974,    -1,    -1,    -1,
      -1,    -1,    -1,   981,   725,    -1,    -1,    -1,    -1,   987,
      -1,    -1,   990,   991,   180,   736,    -1,   995,    -1,    -1,
     186,    -1,   188,   189,   190,   191,   192,   193,   194,   195,
      -1,  1009,    -1,  1011,    -1,  1013,    -1,    -1,    -1,  1017,
      -1,    -1,    -1,    -1,    -1,    -1,  1024,    -1,    -1,   180,
      -1,  1029,    -1,    -1,    -1,   186,   777,   188,   189,   190,
     191,   192,   193,   194,   195,    -1,   180,    -1,    -1,    -1,
      -1,    -1,   186,    -1,   188,   189,   190,   191,   192,   193,
     194,   195,    14,    15,    16,    17,    -1,    -1,    20,    -1,
      22,    23,    24,    25,    26,    -1,    -1,    29,    -1,    -1,
      32,    33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      52,    53,    54,    -1,    -1,    -1,    58,    -1,    60,    37,
      -1,    -1,    64,    -1,    66,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,
      92,    93,   883,    95,    96,    97,    -1,    -1,    -1,    -1,
      -1,   103,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   912,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,    -1,   134,   135,   136,   137,   138,   139,   140,    -1,
     142,    -1,    -1,    -1,    -1,   147,   124,   125,   150,    -1,
     128,   129,   130,   131,   132,   133,    -1,    -1,   160,    -1,
     162,   163,   164,    -1,    -1,   167,   168,    -1,   170,    -1,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,   180,   181,
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
     124,    -1,   150,    -1,   128,   129,   130,   131,   132,   133,
      -1,    -1,   160,    -1,   162,   163,   164,    -1,    -1,   167,
     168,    -1,   170,    -1,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,   180,   181,   182,   183,   184,   185,   186,   187,
      14,    15,    16,    17,    -1,    -1,    20,    -1,    22,    23,
      24,    25,    26,    -1,    -1,    29,    -1,    -1,    32,    33,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    52,    -1,
      -1,    -1,    -1,    -1,    58,    -1,    60,    -1,    -1,    -1,
      64,    -1,    66,    67,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,
      -1,    95,    96,    97,    -1,    -1,    -1,    -1,    -1,   103,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,    -1,    -1,    -1,    -1,    -1,    -1,
     134,   135,   136,   137,   138,   139,   140,    -1,   142,    -1,
      -1,    -1,    -1,   147,    -1,    -1,   150,   128,   129,   130,
     131,   132,   133,    -1,    -1,    -1,   160,    -1,   162,   163,
     164,    -1,    -1,   167,   168,    -1,   170,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,   180,   181,   182,   183,
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
      -1,    -1,    20,    -1,    -1,    -1,   160,    -1,    -1,    -1,
      -1,    29,    30,    -1,   168,    -1,   170,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   180,   181,   182,   183,
     184,   185,   186,   187,    52,    -1,    -1,    -1,    -1,    -1,
      58,    -1,    60,    -1,    -1,    -1,    64,    -1,    66,    67,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    -1,    -1,
      88,    89,    -1,    -1,    92,    93,    -1,    95,    96,    97,
     142,   143,    -1,    -1,    -1,   147,    -1,    -1,    14,    15,
      16,    -1,   154,   155,    20,   157,    -1,   159,    -1,   161,
      -1,    -1,    -1,    -1,    -1,    -1,    32,    33,    -1,   171,
      -1,   173,    -1,    -1,    -1,    -1,   134,   135,   136,   137,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   150,    15,    -1,    17,    62,    63,    20,    -1,
      -1,    -1,   160,    -1,    -1,    -1,    -1,    29,    -1,    -1,
     168,    -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   180,   181,   182,   183,   184,   185,   186,   187,
      52,    -1,    -1,    55,    -1,    -1,    58,    -1,    60,    -1,
      -1,    -1,    64,    -1,    66,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,
      92,    93,    -1,    95,    96,    97,   142,   143,    -1,    -1,
      -1,   147,    -1,    -1,    -1,    -1,    -1,    -1,   154,   155,
      -1,   157,    -1,   159,    -1,   161,    -1,    -1,    14,    15,
      16,    -1,    18,    -1,    20,   171,    -1,    -1,    -1,    -1,
      -1,    -1,   134,   135,   136,   137,    32,    33,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,   142,   143,    -1,    -1,
      -1,   147,    -1,    -1,    -1,    -1,    -1,    -1,   154,   155,
      -1,   157,    -1,   159,    -1,   161,    -1,    -1,   134,   135,
     136,   137,    -1,    -1,    -1,   171,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   150,    15,    -1,    17,    -1,    -1,
      20,    -1,    -1,    -1,   160,    -1,    -1,    -1,    -1,    29,
      -1,    -1,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,   185,
     186,   187,    52,    -1,    -1,    -1,    -1,    -1,    58,    -1,
      60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    -1,    -1,    88,    89,
      -1,    -1,    92,    93,    -1,    95,    96,    97,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    14,    15,    16,
      -1,    18,    -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    32,    33,    14,    15,    16,
      -1,    18,    -1,    20,   134,   135,   136,   137,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    32,    33,    -1,    14,    15,
     150,    -1,    66,    67,    20,    -1,    -1,    -1,    -1,    -1,
     160,    -1,    -1,    -1,    -1,    -1,    32,    33,   168,    -1,
     170,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
     180,   181,   182,   183,   184,   185,   186,   187,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    -1,    -1,    14,    15,    16,    -1,    19,    -1,    20,
      -1,    -1,    -1,    -1,    -1,   142,   143,    -1,   152,   153,
     147,    32,    33,    14,    15,    37,    -1,   154,   155,    20,
     157,    -1,   159,    -1,   161,   142,   143,    -1,    -1,   173,
     147,    32,    33,    -1,   171,    -1,    -1,   154,   155,    -1,
     157,    -1,   159,    -1,   161,    -1,   142,   143,    -1,    -1,
      -1,   147,    -1,    -1,   171,    -1,    -1,    -1,   154,   155,
      -1,   157,    -1,   159,    -1,   161,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,   171,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,    -1,    -1,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,   142,   143,    -1,    -1,    -1,   147,    -1,    -1,    -1,
     152,   153,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,   142,   143,    66,    67,    -1,    -1,    -1,    -1,    -1,
     171,    -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,    -1,    19,    -1,    -1,    -1,    -1,    90,    91,    -1,
     171,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      37,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    90,    91,    -1,    37,    -1,    -1,    -1,
     163,    -1,    -1,   166,    -1,    -1,    -1,   104,   105,   106,
     107,   108,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    66,    67,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,
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
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,   163,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    -1,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    31,    32,    -1,    -1,    -1,    -1,    37,    -1,    -1,
     163,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,   152,   153,    32,    -1,    -1,    -1,   158,    37,
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
     158,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
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
     156,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    27,    -1,    -1,   152,   153,
      -1,    -1,   156,    -1,    -1,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      62,    63,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    12,    -1,    -1,    -1,    -1,    17,   141,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    31,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    86,    87,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    18,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      27,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    17,   141,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    27,    -1,   152,   153,    -1,    -1,    -1,
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
      -1,    12,    -1,    -1,    -1,    -1,    17,   141,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    62,    63,    -1,    -1,    66,    67,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,
      91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,
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
     126,   127,   128,   129,   130,   131,   132,   133,    -1,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,   145,
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
     133,    -1,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    31,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      18,    19,    -1,    -1,    -1,   145,    -1,    -1,    -1,    -1,
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
     129,   130,   131,   132,   133,    -1,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
     149,    -1,    -1,   152,   153,    31,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,   146,    -1,    -1,    -1,    -1,    -1,
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
      18,    19,    -1,    -1,    -1,    -1,   146,    -1,    -1,    -1,
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
      86,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
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
      -1,   145,    -1,    -1,    -1,    -1,    28,    -1,   152,   153,
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
     132,   133,    -1,    -1,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    30,    -1,    -1,    -1,    -1,    -1,    -1,    37,
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
      -1,    87,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   149,    -1,    -1,
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
      -1,    19,    -1,    -1,    -1,   145,    -1,    -1,    -1,    -1,
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
     148,    27,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,
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
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    28,    -1,   152,   153,    -1,
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
     133,    -1,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    31,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
     128,   129,   130,   131,   132,   133,    -1,    -1,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    30,    -1,    -1,    -1,
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
     130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,
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
      -1,    -1,    -1,    27,    -1,   151,   152,   153,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    37,    38,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,   152,   153,    32,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,   148,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    61,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,
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
      -1,    -1,    17,    -1,    19,    -1,    21,   144,    -1,    -1,
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
     133,    12,    -1,    -1,    -1,    -1,    -1,    -1,    19,    -1,
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
     131,   132,   133,    12,    -1,    -1,    -1,    -1,    -1,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    12,    -1,    -1,    -1,    -1,    -1,    -1,
      19,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,    37,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    12,    -1,    -1,    -1,    66,    67,    -1,
      19,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,    66,    67,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,
      12,    90,    91,    -1,    -1,    -1,    -1,    19,    -1,    -1,
      -1,    -1,    -1,   152,   153,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,    37,   115,    -1,    -1,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    66,    67,    -1,    19,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,    -1,    -1,    66,    67,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,   104,   105,   106,   107,   108,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153
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
      13,    33,    27,   141,   224,    18,    32,   158,   145,   144,
     146,    28,   156,    46,   173,    13,    21,    13,    30,    30,
      13,    30,    62,    63,   300,   146,   173,    27,   245,    18,
     290,    13,   151,   290,   149,   322,    17,    46,   149,   290,
      18,   173,   149,   265,   149,   288,    18,   290,    13,   290,
     180,    12,   223,   224,   233,    12,    13,    13,    13,   312,
     313,    18,    13,    56,    57,   283,   284,   285,   269,    21,
      13,    13,    59,    27,    61,    31,   173,   149,   145,   148,
      13,   173,   320,    12,    59,    59,   163,   224,   224,   224,
     290,   224,   224,   290,   290,   290,   224,   224,   290,   290,
     290,   224,   227,   224,   224,   173,   290,   216,   216,   290,
     290,   216,   290,   290,   290,   290,   290,   290,    65,   303,
     290,    18,    18,   290,    18,   245,    18,   322,    53,    54,
     264,   290,   245,    19,   245,   279,   267,   268,   245,    17,
      21,   216,    12,   290,    13,   223,   224,   224,    20,    13,
     146,   224,   284,   289,   272,    20,   224,   224,   290,   224,
     224,   290,   290,   317,   318,   322,   217,   217,    17,   118,
     290,   290,   290,   224,    31,    32,   158,    31,   145,    31,
     156,    21,    28,    31,    21,    30,    30,    61,    27,   151,
      18,   269,   102,   102,   180,   281,    59,   100,   290,   216,
     290,   224,   290,   224,   313,    18,   149,   290,    62,    63,
     315,    27,   146,   173,    27,    13,    13,   224,   225,   290,
      38,   163,   163,   290,   290,   224,   290,   224,   290,   224,
     216,   290,   328,   290,   290,   271,   272,    19,   245,    19,
     245,   263,   269,   290,   290,    18,    21,    18,    17,   245,
      21,    31,   290,   224,    62,    63,   224,    65,   318,   322,
     224,   290,    20,   224,    18,   146,   163,   290,   224,   224,
      32,    21,    31,   145,   148,    13,   144,   300,   173,    51,
     273,    46,   272,   224,   289,   224,   290,    61,   290,   224,
      27,    27,   290,   224,   224,   163,   166,   290,   224,   290,
     224,   224,   290,   290,    53,    54,   224,   280,    18,    21,
     224,    61,   224,    21,   224,   290,    31,   145,   144,   269,
     224,   224,   173,   224,   163,   290,   224,   271,   224,    21,
     224
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
     308,   308,   308,   308,   308,   308,   308,   308,   308,   309,
     309,   310,   311,   311,   312,   312,   313,   313,   313,   314,
     314,   315,   315,   316,   316,   317,   317,   318,   319,   320,
     320,   321,   321,   321,   321,   322,   322,   323,   323,   324,
     324,   324,   324,   324,   324,   324,   325,   325,   326,   327,
     328,   328,   329,   329,   330,   331,   332,   332
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
       2,     2,     1,     6,     6,     7,     8,    10,     4,     3,
       5,     4,     3,     6,     1,     3,     3,     5,     5,     7,
       6,     4,     5,     5,     9,     1,     3,     3,     3,     1,
       1,     1,     6,     2,     4,     1,     3,     1,     1,     1,
       1,     1,     1,     1,     1,     3,     1,     1,     3,     3,
       1,     3,     1,     1,     3,     3,     1,     3
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
      -1,   103,   290,    -1,   316,    -1,   164,   325,    59,   290,
     163,   224,    -1,   164,   320,    59,   290,   163,   224,    -1,
     164,   165,   320,   118,   290,   163,   224,    -1,   164,   180,
      12,   290,    38,   290,   163,   224,    -1,   164,   180,    12,
     290,    38,   290,   166,   290,   163,   224,    -1,   167,   290,
     163,   224,    -1,   290,    27,   224,    -1,   309,    31,   290,
      27,   224,    -1,    58,   297,    59,   224,    -1,    17,   224,
      18,    -1,    17,    99,   312,    13,   224,    18,    -1,   313,
      -1,   312,   146,   313,    -1,   180,   149,   245,    -1,   180,
     149,   245,   100,   290,    -1,   180,   149,   245,    59,   290,
      -1,    60,   290,    61,   224,   315,    63,   224,    -1,    60,
     290,    61,   224,    63,   224,    -1,    62,   290,    61,   224,
      -1,   315,    62,   290,    61,   224,    -1,    64,   290,   149,
     317,   173,    -1,    64,   290,   149,   317,   146,    65,    27,
     224,   173,    -1,   318,    -1,   317,   146,   318,    -1,   322,
      27,   224,    -1,   281,   269,   272,    -1,   323,    -1,   321,
      -1,   324,    -1,    88,    17,   322,   146,   320,    18,    -1,
      89,    19,    -1,    89,    17,   322,    18,    -1,   320,    -1,
     322,   146,   320,    -1,   180,    -1,    67,    -1,   294,    -1,
     291,    -1,   293,    -1,   292,    -1,   295,    -1,   296,    -1,
      17,   290,    18,    -1,   326,    -1,   327,    -1,   320,   118,
     290,    -1,   320,   149,   245,    -1,   329,    -1,   328,   146,
     329,    -1,   330,    -1,   331,    -1,   322,   118,   290,    -1,
     322,   149,   245,    -1,   327,    -1,   332,   146,   327,    -1
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
    1508,  1511,  1514,  1516,  1523,  1530,  1538,  1547,  1558,  1563,
    1567,  1573,  1578,  1582,  1589,  1591,  1595,  1599,  1605,  1611,
    1619,  1626,  1631,  1637,  1643,  1653,  1655,  1659,  1663,  1667,
    1669,  1671,  1673,  1680,  1683,  1688,  1690,  1694,  1696,  1698,
    1700,  1702,  1704,  1706,  1708,  1710,  1714,  1716,  1718,  1722,
    1726,  1728,  1732,  1734,  1736,  1740,  1744,  1746
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   144,   144,   148,   149,   153,   154,   155,   156,   157,
     161,   162,   166,   167,   172,   173,   175,   176,   177,   181,
     185,   186,   187,   188,   197,   198,   207,   219,   220,   221,
     225,   234,   235,   236,   237,   239,   240,   241,   245,   249,
     250,   251,   257,   258,   259,   260,   273,   274,   278,   279,
     288,   289,   290,   291,   295,   296,   310,   311,   312,   316,
     317,   321,   322,   323,   324,   325,   338,   340,   341,   342,
     343,   344,   353,   354,   363,   373,   374,   375,   376,   383,
     388,   389,   393,   394,   399,   403,   408,   412,   415,   417,
     421,   422,   423,   427,   431,   436,   441,   445,   447,   448,
     449,   454,   455,   459,   460,   464,   465,   466,   471,   473,
     474,   488,   489,   499,   500,   501,   505,   506,   518,   519,
     526,   527,   531,   532,   536,   537,   541,   542,   549,   553,
     554,   555,   559,   560,   564,   565,   566,   567,   568,   575,
     579,   580,   581,   585,   586,   590,   591,   592,   593,   603,
     604,   605,   613,   614,   618,   619,   620,   621,   622,   623,
     624,   625,   626,   627,   628,   629,   630,   639,   640,   641,
     645,   646,   647,   648,   649,   650,   651,   652,   656,   657,
     661,   662,   666,   667,   671,   672,   676,   677,   678,   682,
     693,   694,   695,   699,   700,   704,   708,   709,   710,   711,
     715,   716,   717,   721,   722,   726,   727,   731,   735,   739,
     744,   745,   746,   750,   751,   752,   756,   757,   761,   762,
     765,   767,   771,   772,   776,   777,   781,   785,   786,   790,
     799,   800,   804,   805,   812,   813,   817,   818,   822,   826,
     830,   831,   832,   833,   837,   838,   839,   843,   844,   848,
     852,   853,   857,   858,   862,   863,   885,   886,   887,   891,
     892,   896,   897,   901,   902,   907,   908,   909,   910,   911,
     912,   914,   915,   916,   917,   918,   919,   920,   921,   922,
     923,   924,   926,   927,   928,   929,   931,   932,   933,   934,
     935,   937,   938,   939,   940,   942,   943,   945,   946,   948,
     950,   961,   963,   964,   966,   967,   969,   970,   972,   974,
     976,   985,   986,   987,   992,  1000,  1002,  1003,  1004,  1010,
    1011,  1015,  1019,  1023,  1024,  1025,  1029,  1033,  1039,  1040,
    1044,  1045,  1049,  1053,  1054,  1058,  1059,  1063,  1064,  1068,
    1072,  1073,  1074,  1075,  1076,  1077,  1078,  1079,  1080,  1081,
    1082,  1083,  1084,  1085,  1086,  1087,  1088,  1089,  1090,  1091,
    1095,  1096,  1097,  1098,  1099,  1100,  1101,  1102,  1103,  1104,
    1105,  1106,  1107,  1108,  1109,  1110,  1111,  1112,  1113,  1114,
    1115,  1116,  1117,  1118,  1119,  1120,  1121,  1122,  1123,  1124,
    1125,  1126,  1127,  1131,  1132,  1136,  1140,  1142,  1143,  1169,
    1175,  1176,  1188,  1196,  1198,  1202,  1204,  1205,  1208,  1226,
    1227,  1231,  1235,  1236,  1240,  1241,  1245,  1246,  1252,  1272,
    1273,  1277,  1278,  1282,  1283,  1287,  1288,  1292,  1296,  1300,
    1301,  1305,  1307,  1317,  1318,  1322,  1323,  1327,  1329,  1334,
    1335,  1336,  1337,  1338,  1339,  1340,  1344,  1345,  1349,  1353,
    1357,  1358,  1362,  1363,  1367,  1371,  1375,  1376
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

  private static final int yylast_ = 12291;
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


