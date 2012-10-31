
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
  public static final short yypact_ninf_ = -661;
  public static final short yypact_[] =
  {
       108,   560,  -120,   -66,   560,   445,   -53,   -49,   152,   108,
    -661,  -661,  -661,  -661,  -661,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  -661,   186,  -661,    85,   -99,   255,  -661,  -661,
     286,   -66,  -661,   175,   309,  -661,  -661,  -661,  2172,   332,
    -661,   374,   387,  -661,  -661,   560,    32,   235,  1662,  -120,
    3029,  -661,   123,  -661,   560,  3029,   416,   267,  -661,  -661,
    -661,    27,  -661,  -661,  -661,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  -661,  -661,  -661,    28,  -661,   445,   272,   380,
    -661,  1662,  2094,   -99,  -661,  1662,    78,  1662,   242,  1662,
    1662,  1662,  1662,  1662,  -661,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  -661,   814,   106,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  3029,  3029,  2485,  2072,  3040,  3029,  3029,  3029,
    3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,
    3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,   433,
     434,  2072,    -8,   464,   482,  2072,  2072,  2072,  3029,   503,
    -661,   423,  -661, 11386,  -661,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  -661,  -661,  2072,  -661,  1662,   368,  -661,  5397,
    2072,  2072,  -661,  3029,  1662,  3029,  1662,  -661,   401,   361,
     412,   412,   412,   235,   412,   412,  3029,  1175,   307,  3321,
     371,  -661,   744,    -4,   814,    14,  -661,   683,  1662,  1662,
     749,   519,   790,   819,   559,   559,   559,  1662,  1662,  1662,
    1662,   424,   427,  5519,    88,  5642,  -661,    -2,  4662,    67,
     535,  -661,  -661,   -10,  -661,  -661,   595,  -661,  -661,    77,
    -661,  -661,   -27,  -661,  5764,  5886,  1427,  1427,  1427,  1427,
   11837,  1427,  1427,  1427,  1427,  1427,  1427,  1427,  1427,  1427,
    1427,  1427,  1427,  1427,  1427,  2139,  3029,  3029,   494,  -661,
      25,  3029,   618,  3029,  3029,  -661,   325,    46,  -661,  -661,
    -661,    52,   637,   351,  6009,  3029,  -661,  3029,  3029,  -661,
    3029,  3029,  3029,   813,   562,   477,  3029,  3029,  3029,  3029,
    3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,
    3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,  3029,
    3029,  3029,  3029,  3029,  3029,  2072,  1326,     2,   357,   814,
     478,   514,   -28,  -661,   521,     4, 11386,   486, 11386,   534,
     496,   560,   573,   289,   500,   361,  -661,  -661,  -661,  -661,
    -661,   386,   375,  -661,  -661,   388,   397,   421,   463,   529,
    6131,   505,   507,  1237,  1530,  -661,  -661,  3029,  -661,   511,
     511,   511,  3029,   511,   511,  3029,  3029,  3029,   511,   511,
    3029,  3029,  3029,  -661,   511,   235,  2072,  -661,  1662,  1662,
    -661,  -661,  -661,   814,   814,  -661,  2094,  1662,  1662,   814,
     559,   559,   814,  -661,  -661,  -661,  -661,  3029,  2072,  -661,
    2072,  3029,  3029,  -661,  3029,  2072,  3029,  1662,  3029,  1662,
    3029,  3040,  3029,  2072,    17, 11386,    89,  3029,  2072,  6253,
    3029,   172,  6375,  3029,  2072,  1662,  3029,  2072,  3029,  3029,
    -661,  2072,  6497, 11887,   173,  6619,  1963, 11960, 11960,  -661,
    -661,  -661,  -661,  1963,  1963,  1963,  1963, 11887, 11887, 11887,
   11887, 11887, 11710, 11760, 11630, 11508, 11887, 11887, 11887, 11887,
    3506,  3506,  2139,  3750,  3628,  3628,  2139,  2139,  1427,  1427,
     315,   270,  -661,  -661,  -661,  -661,  -661,  2072,  1662,   468,
    1662,  3029,   517,   653,  -661,  2072,  -661,  3029,  3029,   361,
     524,   560,  -661,  -661,  -661,  -661,  3029,   525,   674,  -661,
    -661,  -661,  3029,   412,   511,   412,   511,   511,   511,   260,
     511,   412,   696,   -20,   700,   533,   412,   412,   412,  1865,
    2621,  -661,  -661,  -661,  -661,  3029,   412,   412,  3040,  3029,
    3029,   701,  2757,  3029,  3029,  3029,  3029,   627,  3029,  3029,
    2441,   512,  -661,  -661,  -661,  1237,  -661,   273,   495,   961,
     665,  3361,  4295,  1301,   490,  6741,   405,  6863,  4417,   531,
     135, 11386, 11386, 11386,  3321,  -661,   671,   814,   814,  1611,
     559,   559, 11386,   608,   180,    16,  6987, 11386,  7109,  -661,
      87, 11887,   814, 11508, 11386,  -661,  4784,   202,  -661,   162,
    -661,  -661, 11508,  -661,  1662,  7231,  -661,  3029, 11386,  -661,
     814, 11508,  -661, 11508, 11508,    13,  -661,  -661,  3029,   362,
     814,  2072,  -661,   287,   814, 11386,   574,  3029,  -661,   706,
   11386, 11386,   555,   268,  -661, 11386,   596,  -661,   289, 11386,
    -661,  -661,  -661,  -661,  -661,  -661,  3269,  3029,  -661,  -661,
     537,  3029,   564,   741,  2048,   742,   540,   541,   550,   525,
     235,   235,   235,   -99,  1678,   582,  -661,   587,    64,   709,
    -661,  7353,   746, 11386,   593,   601,   -17,  4906,   -19,  7475,
     583,  -661, 11386,  7597,  7719,    83,  7841,   -12,  2072,   756,
     -14,   715,  3562,  7963,  1237,  1237,  1237,  3029,  1237,  1237,
    3029,  3029,  3029,  1237,  1237,  3029,  3029,  3029,  -661,  -661,
    3029,  -661,  2893,  2577,  -661,  3029,   511,   511,  -661,  3029,
    3029,   511,  3029,  -661,  3029,  -661,  3029,  -661,  -661,  3029,
    -661,  3029,  3029,  -661,  1138,  -661,  3029,   728,  -661,  8085,
    3029,  -661,  8207,  1662,   277,  2072,  2349,  1662, 11386,  -661,
    -661,  2133,   368,  1662,  -661,   765,  8329,   511, 11386,   771,
    3029,  -661,  3301,   605,  2048,  1237,  1237,   764,   125,  -661,
    -661,  -661,  -661,  -661,  1237,   539,  1237,  -661,  -661,    64,
    -661,  3029,   653,   644,  -661,   774,  1237,  1237,  1237,  1237,
    3029,  -661,  2072,   513,   356,  -661,   412,   412,   781,  -661,
     681,  3029,  3029,  3029,  1237,  1237,   928,   995,  3341,  4173,
    1831,   398,  5029,  8452,  4539,   166,    -6, 11386, 11386, 11386,
     282,  3029, 11386,  -661,  8574,   135,  1301,  8696,  8819,   531,
   11386,  8941,  9065,  9189,  9311, 11508,   777,  -661, 11386,  -661,
    -661,  9433,  -661,   814,  -661,   293,  -661,  -661,   709, 11386,
     814,    72,   322,   621,   750,   669,   662,  3029,   511,  -661,
    3029, 11386,  1237,  -661,  -661,  -661,  3029,  1237,   525,  2999,
    -661,  3153,  -661,   390,  -661,  1237,  3029,  -661,  3301,  3301,
    2849,  9555,   204,  -661,   168,  3029,  -661,   583,   606,   623,
    1237,  3029,  9677,  3684,  3806,  3301,  -661,  3029,  3029,  1237,
    3029,  1237,  3029,  1237,  -661, 11386,   511,  3029,  2072,  -661,
    -661,  -661,  3029,  3029,  -661,  -661,   653,  2182,  2199,   468,
     709,  3029,  3029,  9799,  -661, 11386,  3301,  9921,  3260,  -661,
     801,   806,  1662,   398,  5152,  3029,  1237,   395,  1237,  1434,
    -661,  1237,  -661,   809,  1237,  3301,   313,  3928,  3029,  1237,
    1237, 10043, 10165,    -6,  5275,  1831, 10287,   166,  1301, 11386,
       3,  4784,  3138,   782,  -661,    78,   814,    78,   814,   311,
     653, 11386, 11386,  -661,  1237,  -661,  3029,  3029,   814,  1237,
    3029, 10409,  3301,  3029,  1237,  3301,   805,  -661,  3301,  3029,
    -661,  -661,  1237,  1237,  3440,  3301,  3301,  3029,  1237,  3029,
    1237,  1237,  3029,  -661,  -661,  -661,  3029,  -661,  2024,  -661,
    -661,   314,   327,  -661, 10531,  1237, 10653,  3301,  1237, 10775,
    3301,  3301,  1237,  3029, 10898,  1831, 11020,  1831,  -661, 11142,
   11386,  -661,  -661,  3301,   709,  -661,  -661,  1237,  3301,  1237,
    2713,  1237,  3301,  4050,  3029,  1237,  -661,   653,  -661,  3301,
    -661,  -661,  1237, 11264,  1831,  -661,  3301,  1237,  1831
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,   159,   132,   143,   220,   210,     0,     0,     0,     2,
       3,     5,     6,     7,     8,     9,   148,   149,   150,   168,
     169,   170,   171,   160,   162,     0,   137,   133,   135,   138,
       0,   144,   145,     0,   221,   223,   225,   226,     0,   211,
     213,     0,     0,     1,     4,   161,     0,     0,     0,   134,
       0,   146,     0,   228,   222,     0,     0,     0,   343,   346,
     345,     0,   340,   339,   341,   342,   347,   344,   215,   447,
     449,   448,   446,   450,   451,     0,   438,   212,     0,     0,
     163,     0,     0,     0,   142,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   187,   190,   191,   192,   193,   194,
     195,   196,   197,   141,   186,   174,   185,   198,   199,   175,
     136,   320,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     324,   293,   292,   147,   286,   288,   287,   285,   289,   290,
     307,   308,   309,   310,     0,   236,     0,     0,   224,     0,
       0,     0,   440,     0,     0,     0,     0,   214,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    31,    12,
       0,   139,   165,   187,   206,   166,   204,     0,     0,     0,
       0,     0,     0,     0,   181,   182,   180,     0,     0,     0,
       0,     0,     0,     0,     0,   283,   315,     0,   283,     0,
     413,   445,   444,     0,   437,   436,     0,   453,   454,     0,
     350,   351,     0,   348,     0,     0,   360,   361,   362,   363,
     364,   365,   366,   367,   368,   369,   370,   371,   372,   373,
     374,   375,   376,   377,   378,   379,     0,     0,     0,   463,
       0,     0,     0,     0,     0,   442,     0,     0,   457,   459,
     460,     0,     0,     0,   283,     0,   294,     0,     0,   298,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     185,     0,   245,   452,     0,     0,   218,     0,   219,     0,
       0,   252,   172,   276,     0,   151,   153,   154,   155,   156,
     157,     0,     0,    43,    42,     0,     0,     0,     0,     0,
       0,    52,     0,     0,     0,    48,    50,     0,    32,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    34,     0,     0,     0,   164,     0,     0,
     205,   167,   173,   203,   201,   179,     0,     0,     0,   202,
     177,   178,   200,   188,   189,   291,   321,     0,     0,   316,
       0,     0,     0,   325,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   283,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     336,     0,     0,   391,     0,   283,   381,   380,   382,   295,
     301,   297,   296,   384,   383,   385,   386,   387,   388,   389,
     390,   392,   393,   394,   395,   396,   397,   398,   399,   400,
     401,   402,   403,   404,   405,   406,   407,   408,   409,   410,
     411,   412,   304,   305,   302,   303,   237,     0,     0,     0,
       0,     0,     0,     0,   244,     0,   441,     0,     0,     0,
       0,   253,   254,   257,   256,   158,     0,     0,   277,   279,
      10,   152,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    53,     0,    51,     0,     0,     0,     0,
     268,    61,    62,    63,    64,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   105,   102,   113,     0,    49,     0,    31,    18,
      17,    22,     0,    21,    16,     0,     0,     0,     0,    23,
      25,    29,    28,    27,    13,   140,     0,   208,   207,     0,
     184,   183,   284,     0,     0,     0,     0,   415,     0,   414,
       0,   455,   456,   314,   306,   349,     0,     0,   357,     0,
     328,   329,   330,   464,     0,     0,   334,     0,   461,   443,
     462,   311,   458,   312,   313,     0,   331,   299,     0,     0,
     238,     0,   230,     0,   242,   246,     0,     0,   227,   443,
     216,   217,     0,     0,   255,   282,     0,   281,   278,    46,
      47,    37,    45,    44,    36,    41,     0,     0,    40,    35,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   187,     0,     0,   122,     0,     0,   245,
     267,     0,     0,    65,     0,     0,     0,     0,     0,     0,
       0,   110,   111,     0,     0,     0,     0,     0,     0,   444,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    14,    79,
       0,   107,     0,     0,    33,     0,     0,     0,   127,     0,
       0,     0,     0,   176,     0,   322,     0,   317,   319,     0,
     326,     0,     0,   352,     0,   355,     0,     0,   332,     0,
       0,   337,     0,     0,     0,     0,     0,     0,   249,   439,
      11,     0,   240,     0,   280,     0,     0,     0,    56,     0,
       0,    54,    59,     0,     0,     0,     0,     0,     0,   418,
     126,   125,   124,    66,     0,     0,     0,   274,   275,   269,
     270,     0,     0,     0,   109,     0,     0,     0,     0,     0,
       0,   103,     0,     0,     0,   423,     0,     0,     0,   104,
       0,     0,     0,     0,     0,     0,    71,    70,    82,     0,
      84,    69,     0,     0,     0,    72,    74,    78,    77,    76,
       0,     0,   425,    15,     0,    26,    19,     0,   130,    24,
     209,     0,     0,     0,     0,   353,     0,   358,   359,   333,
     335,     0,   300,   239,   231,     0,   234,   235,   245,   233,
     243,     0,     0,     0,   268,   241,   420,     0,     0,    38,
       0,    57,     0,    55,    94,    93,     0,     0,     0,     0,
     123,     0,   271,   272,   435,     0,     0,    92,   100,   416,
       0,     0,     0,   432,     0,     0,   106,     0,     0,     0,
       0,     0,     0,     0,     0,   119,    67,     0,     0,     0,
       0,     0,     0,     0,   108,   112,     0,     0,     0,   323,
     318,   327,     0,     0,   338,   232,   248,     0,     0,     0,
     245,     0,     0,     0,    39,    58,    60,     0,     0,   419,
      89,     0,     0,    68,     0,     0,     0,     0,     0,     0,
     430,     0,   424,     0,     0,   120,     0,     0,     0,     0,
       0,     0,     0,    75,     0,    88,     0,    73,    20,   131,
       0,     0,     0,   251,   247,   263,   261,   262,   260,     0,
       0,   422,   421,    30,     0,   101,     0,     0,   273,     0,
       0,     0,   427,     0,     0,   417,     0,   433,   434,     0,
      95,    80,     0,     0,     0,   115,   114,     0,     0,     0,
       0,     0,     0,   128,   354,   356,     0,   229,     0,   259,
      96,     0,     0,    99,     0,     0,     0,   426,     0,     0,
     121,   116,     0,     0,     0,    86,     0,    83,    81,     0,
     250,   265,   266,   264,   245,    90,    91,     0,   428,     0,
       0,     0,   117,     0,     0,     0,   129,   248,    98,   429,
     431,    97,     0,     0,    87,   258,   118,     0,    85
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -661,  -661,  -661,   826,  -661,  -661,   225,   -91,   179,  -661,
     485,  -661,  -661,    90,   979,  -661,  -661,    95,   -59,  -661,
    -661,  -661,   794,   -77,   414,  -661,  -661,   817,  -661,  -320,
    -179,   301,  -661,   820,    47,   131,  1389,   712,  -143,  -661,
    -661,   480,  -188,   672,   335,  -661,   792,   832,   382,  -661,
     824,  -661,  -661,   838,   -36,  -661,   256,  -661,  -661,   138,
    -641,  -661,  -156,  -482,  -661,  -661,  -661,  -321,  -661,  -661,
    -661,  -661,    38,  -661,  -661,   114,  -661,  -661,  -661,   258,
     -90,   -50,   -13,   632,   931,   965,  1030,   811,   359,   473,
    -661,   -61,  -661,  -661,   167,  -661,  -661,  -661,   491,   358,
    -661,  -643,  -661,  -660,  -661,  -661,  -661,  -661,   -32,  -661,
      31,   -21,  -134,  -661,  -173,  -144,  -272,  -137,  -132,   472,
    -661,  -661,  -661
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   189,   342,   343,   354,
     355,   515,   513,   751,   752,   936,   655,   656,   699,   556,
      13,    27,    28,   753,   344,    14,    31,    32,    15,   334,
     335,   336,    23,    24,   490,   194,   541,   105,   106,   107,
     108,   195,   196,   377,   337,    39,    40,   230,   338,    34,
      35,    36,    37,   231,   613,   838,   167,   317,   844,   322,
     483,   484,   953,   954,   997,   339,   491,   492,   493,   494,
     843,  1024,   659,   660,   769,   770,   771,   340,   498,   499,
     214,   415,   154,   155,   156,   157,   158,   159,   232,   233,
     160,   723,   161,   587,   588,   162,   163,   219,   220,   668,
     758,   627,   784,   542,   543,   927,   544,   872,   873,   662,
     265,   224,   266,   225,    76,   226,   227,   228,   267,   268,
     269,   270,   260
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -173;
  public static final short
  yytable_[] =
  {
       153,   618,   190,   272,   259,   169,   759,   380,   356,   261,
     785,   495,   780,   642,   271,   501,   992,    75,   772,   780,
     476,   481,   486,   320,   217,    69,   730,   689,   399,   716,
     318,    85,   410,    86,    87,   590,   324,   325,   417,   173,
     175,   378,   777,   473,    81,   792,   717,    47,    25,    88,
      48,    33,    38,    89,    90,    91,    92,    93,   273,   426,
      26,   376,   213,   215,   218,   428,    82,   234,   235,   236,
     237,   238,   239,   240,   241,   242,   243,   244,   245,   246,
     247,   248,   249,   250,   251,   252,   253,   254,   255,   173,
     345,   346,    25,   348,   349,    75,   788,   403,   274,   198,
     719,    33,    69,    69,   406,   198,   347,   591,   406,   396,
     440,     1,     2,     3,    30,     4,   199,   720,   482,   411,
     767,   768,   199,   326,    38,   328,   574,    41,    69,   411,
     373,    42,    69,    69,    69,   407,   350,   366,   857,   407,
     164,   691,   165,   475,   397,   379,   223,   993,   477,   427,
     424,    69,    43,   695,   781,   696,     5,    69,    69,   427,
     643,   789,   427,   397,   731,   697,   414,   416,   364,   622,
     624,   418,   258,   421,   907,   356,   174,   176,   223,   103,
      95,    96,    97,    98,    99,   100,   101,   102,   434,   726,
     596,   607,   427,   714,   193,   931,   211,   906,   427,   689,
      66,   715,    95,    96,    97,    98,    99,   100,   101,   102,
     212,   419,   192,   404,   422,   919,   197,   932,   200,    45,
     202,   203,   204,   205,   206,   432,   409,   433,   435,   397,
     436,   437,   438,   427,   397,   397,   443,   444,   445,   446,
     447,   448,   449,   450,   451,   452,   453,   454,   455,   456,
     457,   458,   459,   460,   461,   462,   463,   464,   465,   466,
     467,   468,   469,   470,   471,    46,   320,   547,   575,   960,
      69,   858,   166,   580,   180,   181,   182,   183,   366,   589,
     637,   593,     6,     7,   171,   164,   172,   165,    49,   279,
     864,   704,   184,   185,   370,   834,   371,   319,    50,   605,
     894,    16,    69,    69,   735,   327,   372,   329,   424,   366,
      16,   905,   552,   691,   424,   555,   557,   558,   397,   397,
     561,   562,   563,   694,   357,   695,   358,   696,   735,   383,
     384,   981,  1025,   736,   279,    17,   496,   697,   389,   390,
     391,   392,    54,   609,    17,  1026,   472,   572,   724,   207,
     929,   576,   577,   208,   578,    52,   581,   998,   583,   209,
     584,   284,   586,    69,     1,    77,   210,   592,     4,   331,
     595,   332,   333,   598,   876,   725,   601,   930,   603,   604,
     497,   380,    18,  1037,   374,    69,    78,    69,   504,   877,
      75,    18,    69,   681,   180,   181,   182,   183,    69,    79,
      69,   506,   186,   314,   375,    69,   284,   566,   505,     5,
     507,    69,   184,   185,    69,    83,    29,   741,    69,   397,
     640,   505,   201,   424,   908,   646,   647,   648,   397,   573,
     505,   615,   187,   170,   508,   664,   665,   620,   621,   424,
     548,   178,   657,   423,   179,   675,   625,   313,   314,   258,
     256,   257,   629,    47,   375,   599,    48,   973,   974,   982,
     397,    84,  -172,    29,    69,    19,    20,    21,    22,  -172,
     661,   424,    69,   397,   425,   663,   509,   734,   999,   667,
     669,   263,   672,   673,   674,   611,   676,   612,   682,   683,
     373,   373,   373,   191,   373,   373,   505,   397,   487,   264,
     373,   373,   430,   424,   502,   373,   478,    75,   424,   567,
     568,   733,   357,   207,   358,    69,   619,   208,   570,   571,
     275,   276,   186,   209,    69,   180,   181,   182,   183,   700,
     210,   701,   503,  -172,  -172,    48,   397,  -172,   582,   922,
     327,   366,   510,   184,   185,   691,   488,   729,   321,   708,
     747,   709,   187,   755,   756,   386,   600,   695,   732,   696,
     188,   207,   505,   757,   364,   208,   405,   738,   680,   697,
     505,   209,   373,   505,   505,   373,   373,   373,   210,   373,
     373,   330,  -172,   505,   549,   550,   551,   746,   553,   554,
     589,   748,   341,   559,   560,   764,   209,   191,    69,   564,
     766,   835,   211,   211,   393,  -172,   775,   394,   408,   610,
     810,   614,   702,   875,   776,   765,   212,   212,   852,   933,
     375,    19,    20,    21,    22,  -172,   505,  -172,  -172,  -172,
    -172,  -172,  -172,   366,   505,   420,   934,   799,   375,   505,
     802,   803,   804,   407,    55,   807,   808,   809,   874,   370,
     429,   371,   812,   186,   441,   814,   505,   442,   479,   817,
     818,   372,   820,   480,   821,    69,   822,   485,   489,   823,
      70,   824,   825,   500,   366,   497,   828,   650,   651,   652,
     831,   863,   630,   187,   632,   512,   839,   514,   369,   207,
     370,   548,   371,   208,   221,   878,   879,   616,   364,   209,
     851,   382,   372,   617,   623,   626,   210,   628,   641,   790,
     207,    69,   644,   645,   208,    56,    57,   712,   670,    58,
     209,   911,    69,   737,   739,   727,   406,   210,   740,   631,
     871,   634,   635,   636,   638,   639,    19,    20,    21,    22,
    -172,   882,   883,   884,   749,   743,   829,    70,    70,    19,
      20,    21,    22,   750,   754,   207,   373,   373,   481,   208,
     373,   895,   912,    94,    59,   209,   950,   774,   791,    69,
     385,   207,   210,    70,   793,   208,   207,    70,    70,    70,
     208,   209,   847,   850,   856,   865,   209,    60,   210,   565,
     373,   376,   678,   210,   866,   874,    70,   913,   880,   881,
     915,   909,    70,    70,   903,   658,   917,   679,   366,    62,
      63,    64,    65,    66,    67,   482,   924,   207,   966,   367,
     368,   208,   369,   967,   370,   812,   371,   209,   387,   979,
      55,   937,  1008,   996,   210,    44,   372,   941,   942,   546,
     944,   207,   946,   110,   853,   208,   207,   949,    51,    74,
     208,   209,   951,   952,   262,   373,   209,   388,   210,   109,
     860,   961,   962,   210,   833,    80,   569,   381,   840,   177,
      68,    53,   842,   959,   846,   971,  1001,  1002,   168,   742,
     845,  1045,   910,   862,   585,    69,   744,   666,   984,   373,
     994,   827,   109,   109,   677,   579,   109,   977,   109,   602,
     109,   109,   109,   109,   109,    58,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    70,    69,    84,     0,   633,
    1004,     0,     0,  1006,     0,     0,    74,    74,     0,  1009,
       0,   815,   816,   191,     0,     0,   819,  1014,     0,  1016,
       0,     0,  1019,   685,     0,     0,  1020,    70,    70,     0,
      59,     0,    74,     0,     0,     0,    74,    74,    74,     0,
       0,   689,     0,  1033,     0,     0,     0,     0,     0,    71,
       0,     0,   849,    60,     0,    74,   360,   109,     0,     0,
       0,    74,    74,     0,  1043,   109,     0,   109,     0,     0,
       0,     0,     0,   439,   364,    62,    63,    64,    65,    66,
      67,     0,     0,    72,     0,     0,   109,     0,    70,   109,
     109,     0,     0,     0,     0,     0,     0,     0,   109,   109,
     109,   109,     0,     0,     0,     0,     0,     0,   689,     0,
      70,     0,    70,     0,     0,     0,     0,    70,   956,   958,
       0,     0,     0,    70,     0,    70,    71,    71,     0,     0,
      70,     0,     0,   968,     0,     0,    70,     0,   191,    70,
       0,     0,     0,    70,   760,   761,   762,     0,    73,     0,
       0,   366,    71,   914,     0,   691,    71,    71,    71,     0,
      72,    72,   692,   693,     0,   694,     0,   695,     0,   696,
       0,     0,     0,     0,    74,    71,     0,     0,     0,   697,
       0,    71,    71,     0,   366,     0,    72,     0,     0,    70,
      72,    72,    72,     0,     0,   367,   368,    70,   369,     0,
     370,   948,   371,     0,     0,     0,    74,    74,     0,    72,
       0,     0,   372,     0,     0,    72,    72,     0,   366,     0,
       0,     0,   691,     0,     0,    73,    73,     0,     0,   692,
     693,     0,   694,     0,   695,    55,   696,     0,     0,     0,
      70,     0,     0,     0,     0,     0,   697,     0,   191,    70,
       0,    73,     0,     0,     0,    73,    73,    73,     1,     0,
       0,   351,     4,   331,   352,   332,   333,    74,   353,   109,
     109,     0,     0,     0,    73,     0,     0,   109,   109,   109,
      73,    73,     0,   826,     0,   221,     0,     0,     0,    74,
       0,    74,     0,     0,    71,     0,    74,     0,   109,     0,
     109,     0,    74,     5,    74,     0,    56,    57,     0,    74,
      58,     0,     0,     0,     0,    74,   109,     0,    74,     0,
       0,     0,    74,    70,     0,     0,    71,    71,    72,     0,
       0,   516,   517,   518,   519,     0,     0,   520,     0,   521,
     522,   523,   524,   525,     0,     0,     0,     0,     0,   526,
     527,     0,     0,     0,     0,    59,     0,     0,     0,     0,
      72,    72,     0,     0,     0,     0,     0,     0,    74,   109,
       0,   109,     0,     0,     0,   528,    74,   529,    60,     0,
       0,   530,     0,     0,     0,     0,     0,    71,     0,     0,
      70,     0,     0,    73,     0,   359,   360,     0,   222,     0,
      62,    63,    64,    65,    66,    67,     0,     0,     0,    71,
       0,    71,   540,     0,   364,     0,    71,     0,   531,    74,
     532,    72,    71,    55,    71,    73,    73,     0,    74,    71,
       0,     0,     0,     0,     0,    71,    70,     0,    71,     0,
       0,     0,    71,    72,     0,    72,     0,    70,     0,     0,
      72,     0,     0,     0,     0,     0,    72,     0,    72,   533,
     109,     0,     0,    72,   534,     0,     0,     0,     0,    72,
       0,     0,    72,     0,     0,     0,    72,     0,     0,   535,
     536,   537,     0,     0,   538,   109,    73,     0,    71,     0,
       0,     0,     0,     0,    70,     0,    71,    94,    58,     0,
       0,     0,    74,     0,     0,     0,     0,     0,    73,     0,
      73,     0,     0,   539,     0,    73,     0,   104,     0,     0,
       0,    73,    72,    73,   366,     0,   279,     0,    73,     0,
      72,    55,     0,     0,    73,   367,   368,    73,   369,    71,
     370,    73,   371,    59,     0,     0,     0,     0,    71,     0,
     104,   104,   372,     0,   104,     0,   104,     0,   104,   104,
     104,   104,   104,     0,     0,     0,    60,     0,     0,    74,
       0,     0,     0,    72,     0,     0,     0,     0,   654,   976,
       0,   221,    72,     0,     0,     0,   474,    73,    62,    63,
      64,    65,    66,    67,     0,    73,     0,   283,   284,     0,
       0,     0,    56,    57,   703,     0,    58,     0,     0,     0,
      70,   285,     0,     1,     0,    74,   351,     4,   331,   352,
     332,   333,    71,   545,   109,     0,    74,     0,   109,     0,
       0,     0,   109,     0,   109,   104,     0,     0,    73,   313,
     314,    70,     0,   104,     0,   104,     0,    73,     0,     0,
       0,    59,     0,     0,     0,     0,    72,     0,     5,   315,
     316,     0,     0,     0,   104,     0,     0,   104,   104,     0,
       0,     0,     0,    74,    60,     0,   104,   104,   104,   104,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    71,
       0,     0,     0,     0,   222,     0,    62,    63,    64,    65,
      66,    67,     0,     0,     0,     0,     0,     0,    85,     0,
      86,    87,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    73,     0,    72,     0,     0,    88,     0,     0,     0,
      89,    90,    91,    92,    93,    71,     0,     0,     0,     0,
       0,     0,     0,   796,   797,   798,    71,   800,   801,     0,
       0,     0,   805,   806,     0,     0,     0,     0,     0,    85,
       0,    86,    87,     0,     0,     0,     0,     0,     0,    72,
       0,     0,   684,   685,   686,     0,   763,    88,   687,     0,
      72,    89,    90,    91,    92,    93,     0,     0,    73,    74,
     688,   689,     0,    71,     0,     0,     0,     0,   109,   109,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   109,   854,   855,     0,     0,     0,     0,
      74,     0,     0,   859,     0,   861,     0,    72,     0,     0,
       0,     0,     0,     0,    73,   867,   868,   869,   870,     0,
       0,     0,     0,     0,     0,    73,     0,   104,   104,     0,
       0,     0,     0,   885,   886,   104,   104,   104,     0,     0,
       0,     0,     0,     0,   713,     0,     0,     0,     0,     0,
       0,   193,     0,     0,     0,     0,   104,    66,   104,    95,
      96,    97,    98,    99,   100,   101,   102,     0,     0,     0,
       0,     0,    73,     0,   104,     0,     0,     0,     0,     0,
     690,   366,     0,     0,     0,   691,     0,     0,     0,    71,
       0,   916,   692,   693,     0,   694,   918,   695,     0,   696,
       0,     0,    94,     0,   923,   684,   685,     0,    66,   697,
      95,    96,    97,    98,    99,   100,   101,   102,     0,   935,
      71,     0,     0,    72,   689,     0,     0,   104,   943,   104,
     945,     0,   947,     0,     0,     0,     0,     0,     0,   516,
     517,   518,   519,     0,     0,   520,     0,   521,   522,   523,
     524,   525,     0,     0,    72,     0,     0,   526,   527,     0,
       0,     0,     0,     0,     0,   972,     0,   975,     0,     0,
     978,     0,     0,   980,     0,     0,     0,     0,   985,   986,
       0,     0,     0,   528,     0,   529,     0,     0,    73,   530,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,  1000,     0,     0,     0,     0,  1003,     0,
       0,     0,     0,  1007,     0,     0,     0,     0,   104,    73,
       0,  1010,  1011,     0,   649,     0,   531,  1015,   532,  1017,
    1018,     0,     0,     0,   366,     0,     0,  1023,   691,     0,
       0,     0,   279,   104,  1028,   692,   693,  1030,   694,     0,
     695,  1032,   696,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   697,   650,   651,   652,  1038,   533,  1039,     0,
    1041,     0,   534,     0,  1044,     0,     0,     0,     0,     0,
       0,  1046,     0,     0,     0,     0,  1048,   535,   536,   537,
       0,     0,   538,     0,     0,     0,     0,     0,   516,   517,
     518,   519,     0,     0,   520,   653,   521,   522,   523,   524,
     525,     0,     0,   283,   284,     0,   526,   527,     0,   783,
       0,   539,   516,   517,   518,   519,     0,   285,   520,     0,
     521,   522,   523,   524,   525,     0,     0,  1021,  1022,     0,
     526,   527,   528,     0,   529,     0,     0,   305,   530,    55,
       0,   309,   310,   311,   312,   313,   314,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   528,     0,   529,     0,
       0,    85,   530,    86,    87,   315,   316,     0,     0,     0,
       0,     0,   104,     0,     0,   531,   104,   532,     0,    88,
     104,     0,   104,    89,    90,    91,    92,    93,     0,   221,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   531,
      85,   532,   841,    87,     0,     0,     0,     0,   279,     0,
      56,    57,     0,     0,    58,     0,   533,     0,    88,     0,
       0,   534,    89,    90,    91,    92,    93,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   535,   536,   537,    55,
     533,   538,     0,     0,     0,   534,     0,     0,     0,    85,
       0,   955,    87,     0,    94,     0,     0,     0,     0,    59,
     535,   536,   537,     0,     0,   538,    85,    88,   957,    87,
     539,    89,    90,    91,    92,    93,     0,     0,   653,   283,
     284,     0,    60,     0,    88,     0,     0,     0,    89,    90,
      91,    92,    93,   285,   539,     0,     0,     0,     0,     0,
       0,     0,   222,     0,    62,    63,    64,    65,    66,    67,
      56,    57,     0,     0,    58,     0,   783,   309,   310,   311,
     312,   313,   314,     0,   193,     0,     0,     0,     0,     0,
      66,     0,    95,    96,    97,    98,    99,   100,   101,   102,
       0,   315,   316,     0,     0,     0,   104,   104,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    59,
       0,   104,     0,    94,     0,     0,     0,     0,     0,    66,
       0,    95,    96,    97,    98,    99,   100,   101,   102,     0,
       0,     0,    60,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    61,     0,    62,    63,    64,    65,    66,    67,
       0,     0,    94,     0,   111,     0,   112,     0,    66,   113,
      95,    96,    97,    98,    99,   100,   101,   102,   114,    94,
       0,     0,     0,     0,     0,    66,     0,    95,    96,    97,
      98,    99,   100,   101,   102,     0,     0,     0,     0,     0,
       0,   115,   836,   837,     0,     0,     0,   116,     0,   117,
       0,     0,     0,   118,     0,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,     0,     0,   139,   140,     0,
       0,    58,   141,     0,   142,   143,   144,     0,     0,     0,
       0,     0,     0,     0,     0,   684,   685,   686,     0,     0,
       0,   687,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   688,   689,     0,     0,     0,     0,     0,
       0,     0,     0,   145,   146,   147,    59,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   148,
     111,     0,   112,     0,     0,   113,     0,     0,     0,    60,
       0,     0,     0,     0,   114,   216,     0,   149,     0,   150,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   151,
     152,    62,    63,    64,    65,    66,    67,   115,     0,     0,
       0,     0,     0,   116,     0,   117,     0,     0,     0,   118,
       0,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,     0,     0,   139,   140,     0,     0,    58,   141,     0,
     142,   143,   144,   690,   366,     0,     0,     0,   691,     0,
       0,   684,   685,   686,     0,   692,   693,   687,   694,     0,
     695,     0,   696,     0,     0,     0,     0,     0,     0,   688,
     689,     0,   697,     0,   698,     0,     0,     0,     0,   145,
     146,   147,    59,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   148,   111,     0,   112,     0,
       0,   113,     0,     0,     0,    60,     0,     0,     0,     0,
     114,     0,     0,   149,     0,   150,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   151,   152,    62,    63,    64,
      65,    66,    67,   115,     0,     0,   658,     0,     0,   116,
       0,   117,     0,     0,     0,   118,     0,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,     0,     0,   139,
     140,     0,     0,    58,   141,     0,   142,   143,   144,   690,
     366,     0,     0,     0,   691,     0,     0,   684,   685,   686,
       0,   692,   693,   687,   694,     0,   695,     0,   696,     0,
       0,     0,     0,     0,     0,   688,   689,     0,   697,     0,
     813,     0,     0,     0,     0,   145,   146,   147,    59,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   148,   111,     0,   112,     0,   671,   113,     0,     0,
       0,    60,     0,     0,     0,     0,   114,     0,     0,   149,
       0,   150,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   151,   152,    62,    63,    64,    65,    66,    67,   115,
       0,     0,     0,     0,     0,   116,     0,   117,     0,     0,
       0,   118,     0,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,     0,     0,   139,   140,     0,     0,    58,
     141,     0,   142,   143,   144,   690,   366,     0,     0,     0,
     691,     0,     0,   684,   685,   686,     0,   692,   693,   687,
     694,     0,   695,     0,   696,     0,     0,     0,     0,     0,
       0,   688,   689,     0,   697,     0,  1040,     0,     0,     0,
       0,   145,   146,   147,    59,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   148,   111,     0,
     112,   925,   926,   113,     0,     0,     0,    60,     0,     0,
       0,     0,   114,     0,     0,   149,     0,   150,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   151,   152,    62,
      63,    64,    65,    66,    67,   115,     0,     0,     0,     0,
       0,   116,     0,   117,     0,     0,     0,   118,     0,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,     0,
       0,   139,   140,     0,     0,    58,   141,   811,   142,   143,
     144,   690,   366,     0,     0,     0,   691,     0,     0,     0,
       0,     0,     0,   692,   693,     0,   694,     0,   695,     0,
     696,     0,     0,   684,   685,   686,     0,   920,     0,   687,
     697,     0,     0,     0,     0,     0,     0,   145,   146,   147,
      59,   688,   689,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   148,   111,     0,   112,     0,     0,   113,
       0,     0,     0,    60,     0,     0,     0,    55,   114,     0,
       0,   149,     0,   150,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   151,   152,    62,    63,    64,    65,    66,
      67,   115,     0,     0,     0,     0,     0,   116,     0,   117,
       0,     0,     0,   118,     0,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,     0,     0,   139,   140,     0,
       0,    58,   141,     0,   142,   143,   144,     0,    56,    57,
       0,     0,    58,     0,     0,     0,     0,     0,     0,     0,
       0,   690,   366,     0,     0,     0,   691,     0,     0,     0,
     277,     0,     0,   692,   693,   278,   694,   279,   695,     0,
     696,     0,     0,   145,   146,   147,    59,   684,   685,   686,
     697,   921,     0,   687,     0,   280,     0,    59,     0,   148,
       0,     0,     0,     0,     0,   688,   689,     0,     0,    60,
       0,     0,     0,     0,     0,     0,     0,   149,     0,   150,
      60,     0,     0,     0,   281,   282,     0,     0,     0,   151,
     152,    62,    63,    64,    65,    66,    67,     0,     0,     0,
     229,     0,    62,    63,    64,    65,    66,    67,   283,   284,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,     0,     0,   684,   685,   686,     0,   965,     0,
     687,     0,     0,   359,   360,   361,     0,   745,     0,   362,
     315,   316,   688,   689,     0,   690,   366,     0,     0,     0,
     691,   363,   364,     0,     0,     0,     0,   692,   693,     0,
     694,   995,   695,     0,   696,   684,   685,   686,     0,     0,
       0,   687,     0,     0,   697,     0,     0,     0,     0,     0,
       0,     0,     0,   688,   689,   359,   360,   361,     0,     0,
       0,   362,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   363,   364,   684,   685,     0,     0,     0,
       0,   687,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   688,   689,   359,   360,     0,     0,     0,
       0,   362,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   363,   364,     0,     0,     0,     0,     0,
       0,     0,   690,   366,     0,     0,     0,   691,     0,     0,
       0,   365,   366,     0,   692,   693,     0,   694,     0,   695,
       0,   696,     0,   367,   368,     0,   369,     0,   370,     0,
     371,   697,     0,     0,     0,     0,     0,     0,     0,     0,
     372,     0,     0,   690,   366,     0,     0,     0,   691,     0,
       0,     0,   277,     0,     0,   692,   693,   278,   694,   279,
     695,     0,   696,   365,   366,     0,     0,     0,     0,     0,
       0,     0,   697,     0,     0,   367,   368,   280,   369,     0,
     370,     0,   371,   690,   366,     0,     0,     0,   691,     0,
       0,     0,   372,     0,     0,   692,   693,     0,   694,     0,
     695,     0,   696,   365,   366,     0,   281,   282,     0,     0,
       0,     0,   697,     0,     0,   367,   368,     0,   369,     0,
     370,     0,   371,     0,     0,   279,     0,     0,     0,     0,
     283,   284,   372,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316,     0,     0,   283,   284,     0,   280,
       0,     0,     0,  1012,     0,     0,  1013,     0,     0,     0,
     285,   286,   287,   288,   289,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
       0,     0,     0,     0,     0,     0,     0,   279,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,   283,   284,
       0,   280,     0,     0,     0,   794,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,   305,   306,     0,     0,   309,   310,   311,   312,
     313,   314,     0,     0,     0,     0,     0,     0,     0,   279,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,   280,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
     283,   284,     0,   280,     0,     0,     0,   939,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,   305,     0,     0,     0,   309,   310,
     311,   312,   313,   314,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,   315,   316,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,   280,     0,     0,     0,   940,
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
       0,   983,     0,     0,     0,     0,     0,     0,     0,     0,
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
       0,     0,   315,   316,   887,   888,     0,     0,     0,     0,
     280,     0,     0,  1042,     0,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,   315,   316,   705,     0,     0,
       0,   889,   280,     0,     0,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,   315,   316,     0,
       0,     0,     0,   706,   280,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,   711,     0,     0,   280,     0,     0,     0,
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
       0,   315,   316,   400,     0,   893,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   401,   402,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   721,   722,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,   778,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   779,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
       0,   277,     0,     0,     0,     0,   278,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
     890,     0,     0,     0,     0,     0,   280,     0,     0,     0,
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
       0,   279,     0,   969,   891,     0,     0,     0,     0,     0,
       0,   315,   316,   970,     0,     0,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,     0,   277,     0,     0,
       0,     0,   278,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,   989,     0,     0,     0,
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
       0,     0,     0,     0,   278,   323,   279,     0,     0,     0,
     990,     0,     0,     0,     0,     0,     0,   315,   316,     0,
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
     314,   277,     0,     0,     0,     0,   278,   395,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
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
     312,   313,   314,     0,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   315,   316,   398,     0,     0,     0,     0,     0,   280,
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
       0,     0,     0,     0,     0,   412,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
       0,   277,     0,     0,     0,     0,   278,     0,   279,     0,
       0,     0,     0,     0,     0,   413,     0,     0,   315,   316,
     431,     0,     0,     0,     0,     0,   280,     0,     0,     0,
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
     278,     0,   279,     0,     0,     0,   511,     0,     0,     0,
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
       0,     0,   278,     0,   279,     0,     0,     0,     0,   594,
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
       0,     0,     0,     0,   278,   606,   279,     0,     0,     0,
       0,   597,     0,     0,     0,     0,     0,   315,   316,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,   280,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   281,   282,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   608,     0,     0,     0,   283,
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
     278,     0,   279,     0,     0,     0,   707,     0,     0,     0,
       0,   710,     0,   315,   316,     0,     0,     0,     0,     0,
     280,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,     0,     0,   277,
       0,     0,     0,     0,   278,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,   315,   316,   718,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,   280,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   281,   282,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   402,     0,     0,   283,
     284,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   314,   277,     0,     0,     0,     0,   278,   728,
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
     278,     0,   279,     0,   773,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,   782,     0,     0,   315,   316,     0,
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
       0,     0,   786,     0,     0,     0,     0,     0,     0,   315,
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
     279,     0,     0,     0,     0,     0,     0,   787,   778,     0,
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
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
     795,     0,     0,   315,   316,     0,     0,     0,     0,     0,
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
       0,     0,   278,   830,   279,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,   278,   832,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   315,   316,     0,
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
     848,     0,     0,     0,     0,     0,     0,     0,     0,   315,
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
     312,   313,   314,     0,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   315,   316,   892,     0,     0,     0,     0,     0,   280,
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
       0,   278,     0,   279,     0,   896,     0,     0,     0,     0,
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
       0,     0,     0,     0,   897,     0,   315,   316,     0,     0,
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
     898,     0,     0,     0,     0,     0,   280,     0,     0,     0,
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
     279,     0,   899,     0,     0,     0,     0,     0,     0,     0,
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
     310,   311,   312,   313,   314,     0,     0,   277,     0,     0,
       0,     0,   278,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316,   900,     0,     0,     0,     0,
       0,     0,   280,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   281,   282,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   283,   284,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,     0,
       0,   277,     0,     0,     0,     0,   278,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,   315,   316,   901,
       0,     0,     0,     0,     0,     0,   280,     0,     0,     0,
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
       0,     0,   902,     0,     0,     0,     0,   281,   282,     0,
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
     308,   309,   310,   311,   312,   313,   314,   277,     0,     0,
       0,     0,   278,     0,   279,     0,     0,     0,     0,     0,
       0,     0,   928,     0,   904,   315,   316,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,   315,   316,     0,
       0,     0,     0,     0,   280,   938,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   281,   282,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   283,   284,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,   277,     0,     0,     0,     0,   278,   963,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
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
     279,     0,   964,     0,     0,     0,     0,     0,     0,     0,
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
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316,   987,     0,     0,     0,     0,
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
       0,     0,   278,     0,   279,     0,   988,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,   315,   316,     0,
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
       0,     0,     0,     0,     0,   991,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,   280,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
    1005,     0,     0,     0,     0,   281,   282,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   283,
     284,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   309,   310,   311,
     312,   313,   314,   277,     0,     0,     0,     0,   278,     0,
     279,     0,  1027,     0,     0,     0,     0,     0,     0,     0,
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
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316,     0,     0,     0,     0,     0,
     280,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,  1029,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,   277,     0,     0,
       0,     0,   278,     0,   279,     0,  1031,     0,     0,     0,
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
     306,   307,   308,   309,   310,   311,   312,   313,   314,     0,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   315,   316,  1034,
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
       0,   279,     0,     0,     0,  1035,     0,     0,     0,     0,
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
       0,   278,     0,   279,     0,  1047,  1036,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,     0,     0,   279,     0,     0,
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
     313,   314,   277,     0,     0,     0,     0,     0,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,   277,     0,     0,     0,     0,     0,     0,   279,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   280,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,     0,     0,     0,     0,     0,     0,
       0,     0,   277,     0,     0,     0,   281,   282,     0,   279,
       0,     0,   315,   316,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   281,   282,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,     0,     0,     0,     0,     0,   277,
     283,   284,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,   315,   316,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   280,   296,     0,     0,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   281,   282,     0,   279,     0,     0,     0,
       0,     0,   315,   316,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,     0,     0,   283,   284,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,     0,     0,   281,   282,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,     0,     0,     0,     0,     0,     0,   283,   284,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,   285,   286,   287,   288,   289,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   315,   316
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
        50,   483,    79,   147,   141,    55,   649,   195,   187,    17,
     670,   332,    31,    33,   146,   335,    13,    38,   659,    31,
      18,    49,    18,   166,   114,    38,    13,    33,    30,    13,
     164,    17,    59,    19,    20,    18,   170,   171,    13,    12,
      12,    45,    59,   315,    12,    59,    30,   146,     1,    35,
     149,     4,     5,    39,    40,    41,    42,    43,   148,    13,
     180,    47,   112,   113,   114,    13,    34,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,    12,
     181,   182,    45,   184,   185,   116,    13,    30,   148,    27,
      13,    54,   115,   116,   118,    27,   183,    18,   118,    21,
     283,     3,     4,     5,   180,     7,    44,    30,   146,   146,
      56,    57,    44,   173,    77,   175,   398,   180,   141,   146,
     189,   180,   145,   146,   147,   149,   186,   143,    13,   149,
      17,   147,    19,   316,   146,   149,   115,   144,   146,   146,
     146,   164,     0,   159,   173,   161,    48,   170,   171,   146,
     180,   173,   146,   146,   151,   171,   256,   257,    33,   489,
     491,   146,   141,   263,   102,   354,   149,   149,   147,    48,
     188,   189,   190,   191,   192,   193,   194,   195,   278,    27,
      18,    18,   146,    13,   180,    27,    90,   838,   146,    33,
     186,    21,   188,   189,   190,   191,   192,   193,   194,   195,
     104,   261,    81,   146,   264,   858,    85,   877,    87,    33,
      89,    90,    91,    92,    93,   275,   149,   277,   278,   146,
     280,   281,   282,   146,   146,   146,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,   180,   409,   357,   400,   910,
     283,   146,   149,   405,    14,    15,    16,    17,   143,   413,
      20,   418,   174,   175,    17,    17,    19,    19,    33,    19,
     772,    18,    32,    33,   159,    18,   161,   166,    12,   431,
      18,     0,   315,   316,    17,   174,   171,   176,   146,   143,
       9,    18,   362,   147,   146,   365,   366,   367,   146,   146,
     370,   371,   372,   157,    17,   159,    19,   161,    17,   198,
     199,    18,    18,    46,    19,     0,    47,   171,   207,   208,
     209,   210,    33,   477,     9,    18,   315,   397,   146,    27,
     146,   401,   402,    31,   404,   180,   406,    46,   408,    37,
     410,    91,   412,   376,     3,    33,    44,   417,     7,     8,
     420,    10,    11,   423,    18,   173,   426,   173,   428,   429,
     333,   569,     0,  1024,    13,   398,    12,   400,    13,    33,
     411,     9,   405,   537,    14,    15,    16,    17,   411,    12,
     413,    13,   142,   133,    33,   418,    91,   376,    33,    48,
      13,   424,    32,    33,   427,   180,     2,   149,   431,   146,
     511,    33,   180,   146,   102,   516,   517,   518,   146,   398,
      33,   481,   172,    17,    13,   526,   527,   487,   488,   146,
     180,   169,   519,   118,   172,   535,   496,   132,   133,   418,
      17,    17,   502,   146,    33,   424,   149,    62,    63,   146,
     146,    47,    17,    49,   477,   176,   177,   178,   179,   180,
     520,   146,   485,   146,   149,   525,    13,   611,   960,   529,
     530,    17,   532,   533,   534,    17,   536,    19,   538,   539,
     549,   550,   551,    79,   553,   554,    33,   146,    12,    17,
     559,   560,   151,   146,   118,   564,   149,   528,   146,   378,
     379,   149,    17,    27,    19,   528,   485,    31,   387,   388,
      17,    98,   142,    37,   537,    14,    15,    16,    17,    17,
      44,    19,   146,    88,    89,   149,   146,    92,   407,   149,
     409,   143,    13,    32,    33,   147,    12,   597,   180,   144,
      13,   146,   172,    13,    13,    36,   425,   159,   608,   161,
     180,    27,    33,    13,    33,    31,    31,   617,   537,   171,
      33,    37,   631,    33,    33,   634,   635,   636,    44,   638,
     639,   180,   137,    33,   359,   360,   361,   637,   363,   364,
     724,   641,   180,   368,   369,    13,    37,   183,   611,   374,
      13,   735,    90,    90,   180,   160,    13,   180,    13,   478,
     700,   480,   100,   100,    13,    33,   104,   104,    13,    13,
      33,   176,   177,   178,   179,   180,    33,   182,   183,   184,
     185,   186,   187,   143,    33,    17,    13,   687,    33,    33,
     690,   691,   692,   149,    17,   695,   696,   697,   782,   159,
      13,   161,   702,   142,    92,   705,    33,   180,   180,   709,
     710,   171,   712,   149,   714,   678,   716,   146,   172,   719,
      38,   721,   722,   173,   143,   628,   726,   138,   139,   140,
     730,   771,   503,   172,   505,   180,   736,   180,   157,    27,
     159,   180,   161,    31,    67,   786,   787,   180,    33,    37,
     750,    18,   171,    50,   180,   180,    44,    33,    12,   678,
      27,   724,    12,   180,    31,    88,    89,    46,    17,    92,
      37,    59,   735,   149,    18,   594,   118,    44,   173,   504,
     780,   506,   507,   508,   509,   510,   176,   177,   178,   179,
     180,   791,   792,   793,   180,   149,    18,   115,   116,   176,
     177,   178,   179,    12,    12,    27,   815,   816,    49,    31,
     819,   811,   100,   180,   137,    37,   898,    21,    12,   782,
      21,    27,    44,   141,    59,    31,    27,   145,   146,   147,
      31,    37,    17,    12,    20,   141,    37,   160,    44,   375,
     849,    47,   165,    44,    20,   929,   164,   847,    17,   118,
     850,   180,   170,   171,    27,    55,   856,   180,   143,   182,
     183,   184,   185,   186,   187,   146,   866,    27,    17,   154,
     155,    31,   157,    17,   159,   875,   161,    37,    38,    20,
      17,   881,    27,    51,    44,     9,   171,   887,   888,   354,
     890,    27,   892,    49,   754,    31,    27,   897,    31,    38,
      31,    37,   902,   903,   142,   914,    37,    38,    44,    48,
     765,   911,   912,    44,   733,    45,   386,   195,   737,    77,
      38,    33,   741,   909,   743,   925,   966,   967,    54,   623,
     742,  1037,   844,   769,   411,   898,   628,   528,   938,   948,
     951,   724,    81,    82,   536,   404,    85,   929,    87,   427,
      89,    90,    91,    92,    93,    92,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   283,   929,   503,    -1,   505,
     970,    -1,    -1,   973,    -1,    -1,   115,   116,    -1,   979,
      -1,   706,   707,   519,    -1,    -1,   711,   987,    -1,   989,
      -1,    -1,   992,    15,    -1,    -1,   996,   315,   316,    -1,
     137,    -1,   141,    -1,    -1,    -1,   145,   146,   147,    -1,
      -1,    33,    -1,  1013,    -1,    -1,    -1,    -1,    -1,    38,
      -1,    -1,   747,   160,    -1,   164,    15,   166,    -1,    -1,
      -1,   170,   171,    -1,  1034,   174,    -1,   176,    -1,    -1,
      -1,    -1,    -1,   180,    33,   182,   183,   184,   185,   186,
     187,    -1,    -1,    38,    -1,    -1,   195,    -1,   376,   198,
     199,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   207,   208,
     209,   210,    -1,    -1,    -1,    -1,    -1,    -1,    33,    -1,
     398,    -1,   400,    -1,    -1,    -1,    -1,   405,   907,   908,
      -1,    -1,    -1,   411,    -1,   413,   115,   116,    -1,    -1,
     418,    -1,    -1,   922,    -1,    -1,   424,    -1,   644,   427,
      -1,    -1,    -1,   431,   650,   651,   652,    -1,    38,    -1,
      -1,   143,   141,   848,    -1,   147,   145,   146,   147,    -1,
     115,   116,   154,   155,    -1,   157,    -1,   159,    -1,   161,
      -1,    -1,    -1,    -1,   283,   164,    -1,    -1,    -1,   171,
      -1,   170,   171,    -1,   143,    -1,   141,    -1,    -1,   477,
     145,   146,   147,    -1,    -1,   154,   155,   485,   157,    -1,
     159,   896,   161,    -1,    -1,    -1,   315,   316,    -1,   164,
      -1,    -1,   171,    -1,    -1,   170,   171,    -1,   143,    -1,
      -1,    -1,   147,    -1,    -1,   115,   116,    -1,    -1,   154,
     155,    -1,   157,    -1,   159,    17,   161,    -1,    -1,    -1,
     528,    -1,    -1,    -1,    -1,    -1,   171,    -1,   754,   537,
      -1,   141,    -1,    -1,    -1,   145,   146,   147,     3,    -1,
      -1,     6,     7,     8,     9,    10,    11,   376,    13,   378,
     379,    -1,    -1,    -1,   164,    -1,    -1,   386,   387,   388,
     170,   171,    -1,    65,    -1,    67,    -1,    -1,    -1,   398,
      -1,   400,    -1,    -1,   283,    -1,   405,    -1,   407,    -1,
     409,    -1,   411,    48,   413,    -1,    88,    89,    -1,   418,
      92,    -1,    -1,    -1,    -1,   424,   425,    -1,   427,    -1,
      -1,    -1,   431,   611,    -1,    -1,   315,   316,   283,    -1,
      -1,    14,    15,    16,    17,    -1,    -1,    20,    -1,    22,
      23,    24,    25,    26,    -1,    -1,    -1,    -1,    -1,    32,
      33,    -1,    -1,    -1,    -1,   137,    -1,    -1,    -1,    -1,
     315,   316,    -1,    -1,    -1,    -1,    -1,    -1,   477,   478,
      -1,   480,    -1,    -1,    -1,    58,   485,    60,   160,    -1,
      -1,    64,    -1,    -1,    -1,    -1,    -1,   376,    -1,    -1,
     678,    -1,    -1,   283,    -1,    14,    15,    -1,   180,    -1,
     182,   183,   184,   185,   186,   187,    -1,    -1,    -1,   398,
      -1,   400,   353,    -1,    33,    -1,   405,    -1,   101,   528,
     103,   376,   411,    17,   413,   315,   316,    -1,   537,   418,
      -1,    -1,    -1,    -1,    -1,   424,   724,    -1,   427,    -1,
      -1,    -1,   431,   398,    -1,   400,    -1,   735,    -1,    -1,
     405,    -1,    -1,    -1,    -1,    -1,   411,    -1,   413,   142,
     569,    -1,    -1,   418,   147,    -1,    -1,    -1,    -1,   424,
      -1,    -1,   427,    -1,    -1,    -1,   431,    -1,    -1,   162,
     163,   164,    -1,    -1,   167,   594,   376,    -1,   477,    -1,
      -1,    -1,    -1,    -1,   782,    -1,   485,   180,    92,    -1,
      -1,    -1,   611,    -1,    -1,    -1,    -1,    -1,   398,    -1,
     400,    -1,    -1,   196,    -1,   405,    -1,    48,    -1,    -1,
      -1,   411,   477,   413,   143,    -1,    19,    -1,   418,    -1,
     485,    17,    -1,    -1,   424,   154,   155,   427,   157,   528,
     159,   431,   161,   137,    -1,    -1,    -1,    -1,   537,    -1,
      81,    82,   171,    -1,    85,    -1,    87,    -1,    89,    90,
      91,    92,    93,    -1,    -1,    -1,   160,    -1,    -1,   678,
      -1,    -1,    -1,   528,    -1,    -1,    -1,    -1,   519,    65,
      -1,    67,   537,    -1,    -1,    -1,   180,   477,   182,   183,
     184,   185,   186,   187,    -1,   485,    -1,    90,    91,    -1,
      -1,    -1,    88,    89,   545,    -1,    92,    -1,    -1,    -1,
     898,   104,    -1,     3,    -1,   724,     6,     7,     8,     9,
      10,    11,   611,    13,   733,    -1,   735,    -1,   737,    -1,
      -1,    -1,   741,    -1,   743,   166,    -1,    -1,   528,   132,
     133,   929,    -1,   174,    -1,   176,    -1,   537,    -1,    -1,
      -1,   137,    -1,    -1,    -1,    -1,   611,    -1,    48,   152,
     153,    -1,    -1,    -1,   195,    -1,    -1,   198,   199,    -1,
      -1,    -1,    -1,   782,   160,    -1,   207,   208,   209,   210,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   678,
      -1,    -1,    -1,    -1,   180,    -1,   182,   183,   184,   185,
     186,   187,    -1,    -1,    -1,    -1,    -1,    -1,    17,    -1,
      19,    20,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   611,    -1,   678,    -1,    -1,    35,    -1,    -1,    -1,
      39,    40,    41,    42,    43,   724,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   684,   685,   686,   735,   688,   689,    -1,
      -1,    -1,   693,   694,    -1,    -1,    -1,    -1,    -1,    17,
      -1,    19,    20,    -1,    -1,    -1,    -1,    -1,    -1,   724,
      -1,    -1,    14,    15,    16,    -1,    18,    35,    20,    -1,
     735,    39,    40,    41,    42,    43,    -1,    -1,   678,   898,
      32,    33,    -1,   782,    -1,    -1,    -1,    -1,   907,   908,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   922,   755,   756,    -1,    -1,    -1,    -1,
     929,    -1,    -1,   764,    -1,   766,    -1,   782,    -1,    -1,
      -1,    -1,    -1,    -1,   724,   776,   777,   778,   779,    -1,
      -1,    -1,    -1,    -1,    -1,   735,    -1,   378,   379,    -1,
      -1,    -1,    -1,   794,   795,   386,   387,   388,    -1,    -1,
      -1,    -1,    -1,    -1,   173,    -1,    -1,    -1,    -1,    -1,
      -1,   180,    -1,    -1,    -1,    -1,   407,   186,   409,   188,
     189,   190,   191,   192,   193,   194,   195,    -1,    -1,    -1,
      -1,    -1,   782,    -1,   425,    -1,    -1,    -1,    -1,    -1,
     142,   143,    -1,    -1,    -1,   147,    -1,    -1,    -1,   898,
      -1,   852,   154,   155,    -1,   157,   857,   159,    -1,   161,
      -1,    -1,   180,    -1,   865,    14,    15,    -1,   186,   171,
     188,   189,   190,   191,   192,   193,   194,   195,    -1,   880,
     929,    -1,    -1,   898,    33,    -1,    -1,   478,   889,   480,
     891,    -1,   893,    -1,    -1,    -1,    -1,    -1,    -1,    14,
      15,    16,    17,    -1,    -1,    20,    -1,    22,    23,    24,
      25,    26,    -1,    -1,   929,    -1,    -1,    32,    33,    -1,
      -1,    -1,    -1,    -1,    -1,   926,    -1,   928,    -1,    -1,
     931,    -1,    -1,   934,    -1,    -1,    -1,    -1,   939,   940,
      -1,    -1,    -1,    58,    -1,    60,    -1,    -1,   898,    64,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   964,    -1,    -1,    -1,    -1,   969,    -1,
      -1,    -1,    -1,   974,    -1,    -1,    -1,    -1,   569,   929,
      -1,   982,   983,    -1,    99,    -1,   101,   988,   103,   990,
     991,    -1,    -1,    -1,   143,    -1,    -1,   998,   147,    -1,
      -1,    -1,    19,   594,  1005,   154,   155,  1008,   157,    -1,
     159,  1012,   161,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   171,   138,   139,   140,  1027,   142,  1029,    -1,
    1031,    -1,   147,    -1,  1035,    -1,    -1,    -1,    -1,    -1,
      -1,  1042,    -1,    -1,    -1,    -1,  1047,   162,   163,   164,
      -1,    -1,   167,    -1,    -1,    -1,    -1,    -1,    14,    15,
      16,    17,    -1,    -1,    20,   180,    22,    23,    24,    25,
      26,    -1,    -1,    90,    91,    -1,    32,    33,    -1,   670,
      -1,   196,    14,    15,    16,    17,    -1,   104,    20,    -1,
      22,    23,    24,    25,    26,    -1,    -1,    53,    54,    -1,
      32,    33,    58,    -1,    60,    -1,    -1,   124,    64,    17,
      -1,   128,   129,   130,   131,   132,   133,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    58,    -1,    60,    -1,
      -1,    17,    64,    19,    20,   152,   153,    -1,    -1,    -1,
      -1,    -1,   733,    -1,    -1,   101,   737,   103,    -1,    35,
     741,    -1,   743,    39,    40,    41,    42,    43,    -1,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   101,
      17,   103,    19,    20,    -1,    -1,    -1,    -1,    19,    -1,
      88,    89,    -1,    -1,    92,    -1,   142,    -1,    35,    -1,
      -1,   147,    39,    40,    41,    42,    43,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   162,   163,   164,    17,
     142,   167,    -1,    -1,    -1,   147,    -1,    -1,    -1,    17,
      -1,    19,    20,    -1,   180,    -1,    -1,    -1,    -1,   137,
     162,   163,   164,    -1,    -1,   167,    17,    35,    19,    20,
     196,    39,    40,    41,    42,    43,    -1,    -1,   180,    90,
      91,    -1,   160,    -1,    35,    -1,    -1,    -1,    39,    40,
      41,    42,    43,   104,   196,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   180,    -1,   182,   183,   184,   185,   186,   187,
      88,    89,    -1,    -1,    92,    -1,   877,   128,   129,   130,
     131,   132,   133,    -1,   180,    -1,    -1,    -1,    -1,    -1,
     186,    -1,   188,   189,   190,   191,   192,   193,   194,   195,
      -1,   152,   153,    -1,    -1,    -1,   907,   908,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   137,
      -1,   922,    -1,   180,    -1,    -1,    -1,    -1,    -1,   186,
      -1,   188,   189,   190,   191,   192,   193,   194,   195,    -1,
      -1,    -1,   160,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   180,    -1,   182,   183,   184,   185,   186,   187,
      -1,    -1,   180,    -1,    15,    -1,    17,    -1,   186,    20,
     188,   189,   190,   191,   192,   193,   194,   195,    29,   180,
      -1,    -1,    -1,    -1,    -1,   186,    -1,   188,   189,   190,
     191,   192,   193,   194,   195,    -1,    -1,    -1,    -1,    -1,
      -1,    52,    53,    54,    -1,    -1,    -1,    58,    -1,    60,
      -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    -1,    -1,    88,    89,    -1,
      -1,    92,    93,    -1,    95,    96,    97,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    14,    15,    16,    -1,    -1,
      -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    32,    33,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   134,   135,   136,   137,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   150,
      15,    -1,    17,    -1,    -1,    20,    -1,    -1,    -1,   160,
      -1,    -1,    -1,    -1,    29,    30,    -1,   168,    -1,   170,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,
     181,   182,   183,   184,   185,   186,   187,    52,    -1,    -1,
      -1,    -1,    -1,    58,    -1,    60,    -1,    -1,    -1,    64,
      -1,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,    -1,
      95,    96,    97,   142,   143,    -1,    -1,    -1,   147,    -1,
      -1,    14,    15,    16,    -1,   154,   155,    20,   157,    -1,
     159,    -1,   161,    -1,    -1,    -1,    -1,    -1,    -1,    32,
      33,    -1,   171,    -1,   173,    -1,    -1,    -1,    -1,   134,
     135,   136,   137,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   150,    15,    -1,    17,    -1,
      -1,    20,    -1,    -1,    -1,   160,    -1,    -1,    -1,    -1,
      29,    -1,    -1,   168,    -1,   170,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,
     185,   186,   187,    52,    -1,    -1,    55,    -1,    -1,    58,
      -1,    60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    -1,    -1,    88,
      89,    -1,    -1,    92,    93,    -1,    95,    96,    97,   142,
     143,    -1,    -1,    -1,   147,    -1,    -1,    14,    15,    16,
      -1,   154,   155,    20,   157,    -1,   159,    -1,   161,    -1,
      -1,    -1,    -1,    -1,    -1,    32,    33,    -1,   171,    -1,
     173,    -1,    -1,    -1,    -1,   134,   135,   136,   137,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   150,    15,    -1,    17,    -1,    19,    20,    -1,    -1,
      -1,   160,    -1,    -1,    -1,    -1,    29,    -1,    -1,   168,
      -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   180,   181,   182,   183,   184,   185,   186,   187,    52,
      -1,    -1,    -1,    -1,    -1,    58,    -1,    60,    -1,    -1,
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
      -1,    -1,    29,    -1,    -1,   168,    -1,   170,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,   181,   182,
     183,   184,   185,   186,   187,    52,    -1,    -1,    -1,    -1,
      -1,    58,    -1,    60,    -1,    -1,    -1,    64,    -1,    66,
      67,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    -1,
      -1,    88,    89,    -1,    -1,    92,    93,    94,    95,    96,
      97,   142,   143,    -1,    -1,    -1,   147,    -1,    -1,    -1,
      -1,    -1,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,    -1,    -1,    14,    15,    16,    -1,    18,    -1,    20,
     171,    -1,    -1,    -1,    -1,    -1,    -1,   134,   135,   136,
     137,    32,    33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   150,    15,    -1,    17,    -1,    -1,    20,
      -1,    -1,    -1,   160,    -1,    -1,    -1,    17,    29,    -1,
      -1,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   180,   181,   182,   183,   184,   185,   186,
     187,    52,    -1,    -1,    -1,    -1,    -1,    58,    -1,    60,
      -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    -1,    -1,    88,    89,    -1,
      -1,    92,    93,    -1,    95,    96,    97,    -1,    88,    89,
      -1,    -1,    92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   142,   143,    -1,    -1,    -1,   147,    -1,    -1,    -1,
      12,    -1,    -1,   154,   155,    17,   157,    19,   159,    -1,
     161,    -1,    -1,   134,   135,   136,   137,    14,    15,    16,
     171,    18,    -1,    20,    -1,    37,    -1,   137,    -1,   150,
      -1,    -1,    -1,    -1,    -1,    32,    33,    -1,    -1,   160,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   168,    -1,   170,
     160,    -1,    -1,    -1,    66,    67,    -1,    -1,    -1,   180,
     181,   182,   183,   184,   185,   186,   187,    -1,    -1,    -1,
     180,    -1,   182,   183,   184,   185,   186,   187,    90,    91,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    -1,    -1,    14,    15,    16,    -1,    18,    -1,
      20,    -1,    -1,    14,    15,    16,    -1,    18,    -1,    20,
     152,   153,    32,    33,    -1,   142,   143,    -1,    -1,    -1,
     147,    32,    33,    -1,    -1,    -1,    -1,   154,   155,    -1,
     157,   173,   159,    -1,   161,    14,    15,    16,    -1,    -1,
      -1,    20,    -1,    -1,   171,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    32,    33,    14,    15,    16,    -1,    -1,
      -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    32,    33,    14,    15,    -1,    -1,    -1,
      -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    32,    33,    14,    15,    -1,    -1,    -1,
      -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    32,    33,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   142,   143,    -1,    -1,    -1,   147,    -1,    -1,
      -1,   142,   143,    -1,   154,   155,    -1,   157,    -1,   159,
      -1,   161,    -1,   154,   155,    -1,   157,    -1,   159,    -1,
     161,   171,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     171,    -1,    -1,   142,   143,    -1,    -1,    -1,   147,    -1,
      -1,    -1,    12,    -1,    -1,   154,   155,    17,   157,    19,
     159,    -1,   161,   142,   143,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   171,    -1,    -1,   154,   155,    37,   157,    -1,
     159,    -1,   161,   142,   143,    -1,    -1,    -1,   147,    -1,
      -1,    -1,   171,    -1,    -1,   154,   155,    -1,   157,    -1,
     159,    -1,   161,   142,   143,    -1,    66,    67,    -1,    -1,
      -1,    -1,   171,    -1,    -1,   154,   155,    -1,   157,    -1,
     159,    -1,   161,    -1,    -1,    19,    -1,    -1,    -1,    -1,
      90,    91,   171,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    37,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,    17,
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153,    -1,    -1,    90,    91,    -1,    37,
      -1,    -1,    -1,   163,    -1,    -1,   166,    -1,    -1,    -1,
     104,   105,   106,   107,   108,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    19,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    37,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    90,    91,
      -1,    37,    -1,    -1,    -1,   163,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      66,    67,   124,   125,    -1,    -1,   128,   129,   130,   131,
     132,   133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    19,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    37,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      90,    91,    -1,    37,    -1,    -1,    -1,   163,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,   124,    -1,    -1,    -1,   128,   129,
     130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    -1,
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
     130,   131,   132,   133,    -1,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153,    31,    32,    -1,    -1,    -1,    -1,
      37,    -1,    -1,   163,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,   152,   153,    32,    -1,    -1,
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
     153,    -1,    -1,   156,    -1,    -1,    37,    -1,    -1,    -1,
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
      -1,   152,   153,    31,    -1,   156,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    86,    87,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,
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
      17,    -1,    19,    -1,    21,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,   149,    -1,    -1,   152,   153,    -1,
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
      -1,    -1,   145,    -1,    -1,    -1,    -1,    -1,    -1,   152,
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
      19,    -1,    -1,    -1,    -1,    -1,    -1,   148,    27,    -1,
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
      -1,    -1,    -1,    -1,    17,    18,    19,    -1,    -1,    -1,
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
      -1,    17,    -1,    19,    -1,    21,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    28,    -1,   152,   153,    -1,    -1,
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
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      -1,    12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    30,
      -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,
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
     127,   128,   129,   130,   131,   132,   133,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    27,    -1,   151,   152,   153,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    37,    38,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,   152,   153,    32,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,   148,    -1,    -1,    -1,   152,
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
     129,   130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    61,    -1,    -1,    -1,    -1,    66,
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
      -1,    17,    -1,    19,    -1,    21,   144,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,    12,    -1,    -1,    -1,    -1,    -1,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    12,    -1,    -1,    -1,    -1,    -1,    -1,    19,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,    37,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    12,    -1,    -1,    -1,    66,    67,    -1,    19,
      -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,    66,    67,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,    12,
      90,    91,    -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,
      -1,    -1,   152,   153,   104,   105,   106,   107,   108,   109,
     110,   111,   112,   113,    37,   115,    -1,    -1,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    19,    -1,    -1,    -1,
      -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,    -1,    -1,    66,    67,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,   104,   105,   106,   107,   108,    -1,    37,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   152,   153
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
     294,   295,   296,   297,   298,   322,   325,    33,    12,    12,
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
     309,    67,   180,   321,   322,   324,   326,   327,   328,   180,
     258,   264,   299,   300,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,    17,    17,   321,   328,
     333,    17,   248,    17,    17,   321,   323,   329,   330,   331,
     332,   329,   326,   291,   292,    17,    98,    12,    17,    19,
      37,    66,    67,    90,    91,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   152,   153,   268,   323,   246,
     249,   180,   270,    18,   323,   323,   292,   246,   292,   246,
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
     325,    92,   180,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   321,   327,   180,   325,    18,   146,   149,   180,
     149,    49,   146,   271,   272,   146,    18,    12,    12,   172,
     245,   277,   278,   279,   280,   278,    47,   245,   289,   290,
     173,   240,   118,   146,    13,    33,    13,    13,    13,    13,
      13,   145,   180,   223,   180,   222,    14,    15,    16,    17,
      20,    22,    23,    24,    25,    26,    32,    33,    58,    60,
      64,   101,   103,   142,   147,   162,   163,   164,   167,   196,
     225,   247,   314,   315,   317,    13,   221,   291,   180,   217,
     217,   217,   292,   217,   217,   292,   230,   292,   292,   217,
     217,   292,   292,   292,   217,   235,   321,   246,   246,   252,
     246,   246,   292,   321,   327,   329,   292,   292,   292,   309,
     329,   292,   246,   292,   292,   300,   292,   304,   305,   323,
      18,    18,   292,   328,   146,   292,    18,   146,   292,   321,
     246,   292,   330,   292,   292,   329,    18,    18,    86,   323,
     246,    17,    19,   265,   246,   292,   180,    50,   274,   321,
     292,   292,   240,   180,   278,   292,   180,   312,    33,   292,
     219,   217,   219,   235,   217,   217,   217,    20,   217,   217,
     218,    12,    33,   180,    12,   180,   218,   218,   218,    99,
     138,   139,   140,   180,   225,   227,   228,   234,    55,   283,
     284,   292,   320,   292,   218,   218,   299,   292,   310,   292,
      17,    19,   292,   292,   292,   291,   292,   310,   165,   180,
     321,   326,   292,   292,    14,    15,    16,    20,    32,    33,
     142,   147,   154,   155,   157,   159,   161,   171,   173,   229,
      17,    19,   100,   225,    18,    32,   158,   145,   144,   146,
      28,   156,    46,   173,    13,    21,    13,    30,    30,    13,
      30,    62,    63,   302,   146,   173,    27,   246,    18,   292,
      13,   151,   292,   149,   323,    17,    46,   149,   292,    18,
     173,   149,   267,   149,   290,    18,   292,    13,   292,   180,
      12,   224,   225,   234,    12,    13,    13,    13,   311,   312,
     235,   235,   235,    18,    13,    33,    13,    56,    57,   285,
     286,   287,   271,    21,    21,    13,    13,    59,    27,    61,
      31,   173,   149,   247,   313,   314,   145,   148,    13,   173,
     321,    12,    59,    59,   163,    27,   225,   225,   225,   292,
     225,   225,   292,   292,   292,   225,   225,   292,   292,   292,
     291,    94,   292,   173,   292,   217,   217,   292,   292,   217,
     292,   292,   292,   292,   292,   292,    65,   305,   292,    18,
      18,   292,    18,   246,    18,   323,    53,    54,   266,   292,
     246,    19,   246,   281,   269,   270,   246,    17,    21,   217,
      12,   292,    13,   224,   225,   225,    20,    13,   146,   225,
     228,   225,   286,   291,   274,   141,    20,   225,   225,   225,
     225,   292,   318,   319,   323,   100,    18,    33,   218,   218,
      17,   118,   292,   292,   292,   225,   225,    31,    32,   158,
      31,   145,    31,   156,    18,   292,    21,    28,    31,    21,
      30,    30,    61,    27,   151,    18,   271,   102,   102,   180,
     283,    59,   100,   292,   217,   292,   225,   292,   225,   312,
      18,    18,   149,   225,   292,    62,    63,   316,    27,   146,
     173,    27,   314,    13,    13,   225,   226,   292,    38,   163,
     163,   292,   292,   225,   292,   225,   292,   225,   217,   292,
     329,   292,   292,   273,   274,    19,   246,    19,   246,   265,
     271,   292,   292,    18,    21,    18,    17,    17,   246,    21,
      31,   292,   225,    62,    63,   225,    65,   319,   225,    20,
     225,    18,   146,   163,   292,   225,   225,    32,    21,    31,
     145,   148,    13,   144,   302,   173,    51,   275,    46,   274,
     225,   291,   291,   225,   292,    61,   292,   225,    27,   292,
     225,   225,   163,   166,   292,   225,   292,   225,   225,   292,
     292,    53,    54,   225,   282,    18,    18,    21,   225,    61,
     225,    21,   225,   292,    31,   145,   144,   271,   225,   225,
     173,   225,   163,   292,   225,   273,   225,    21,   225
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
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     226,   226,   227,   227,   228,   228,   228,   229,   229,   229,
     230,   230,   231,   231,   231,   232,   232,   233,   233,   234,
     234,   235,   235,   236,   236,   237,   237,   238,   239,   239,
     239,   240,   240,   241,   241,   241,   241,   241,   241,   242,
     242,   242,   243,   243,   244,   244,   244,   244,   245,   245,
     245,   245,   245,   246,   246,   246,   246,   246,   246,   246,
     246,   246,   246,   246,   246,   246,   246,   247,   247,   247,
     248,   248,   248,   248,   248,   248,   248,   248,   249,   249,
     250,   250,   251,   251,   252,   252,   253,   253,   253,   254,
     255,   255,   255,   256,   256,   257,   258,   258,   258,   258,
     259,   259,   259,   260,   260,   261,   261,   262,   263,   264,
     265,   265,   265,   266,   266,   266,   267,   267,   268,   268,
     269,   269,   270,   270,   271,   271,   272,   273,   273,   274,
     275,   275,   276,   276,   277,   277,   278,   278,   279,   280,
     281,   281,   281,   281,   282,   282,   282,   283,   283,   284,
     285,   285,   286,   286,   287,   287,   288,   288,   288,   289,
     289,   290,   290,   291,   291,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   293,
     293,   294,   295,   296,   296,   296,   297,   298,   299,   299,
     300,   300,   301,   302,   302,   303,   303,   304,   304,   305,
     306,   306,   306,   306,   306,   306,   306,   306,   306,   306,
     306,   306,   306,   306,   306,   306,   306,   306,   306,   306,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   308,   308,   309,   310,   310,   311,   311,
     312,   312,   312,   313,   313,   314,   315,   315,   316,   316,
     317,   317,   318,   318,   319,   320,   321,   321,   322,   322,
     322,   322,   323,   323,   324,   324,   325,   325,   325,   325,
     325,   325,   325,   326,   326,   327,   328,   329,   329,   330,
     330,   331,   332,   333,   333
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
       3,     1,     1,     1,     1,     2,     3,     4,     5,     3,
       3,     3,     3,     5,     3,     5,     3,     3,     3,     2,
       6,     7,     3,     7,     3,    11,     7,     9,     5,     5,
       8,     8,     4,     4,     4,     6,     7,     9,     9,     7,
       4,     6,     1,     3,     3,     1,     4,     2,     4,     3,
       2,     2,     4,     1,     6,     6,     7,     8,    10,     4,
       1,     3,     1,     3,     2,     2,     2,     3,     7,     9,
       3,     5,     1,     2,     3,     1,     3,     1,     1,     1,
       3,     3,     3,     1,     2,     1,     2,     3,     1,     1,
       1,     1,     2,     1,     1,     1,     1,     1,     2,     1,
       2,     3,     1,     3,     5,     4,     4,     5,     1,     1,
       1,     1,     0,     3,     1,     1,     5,     3,     3,     3,
       2,     2,     2,     4,     4,     1,     1,     1,     3,     3,
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
       3,     3,     3,     1,     3,     3,     3,     5,     1,     3,
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
  "unaryExpr", "binaryExpr", "mapletList", "maplet",
  "nonDeterministicAltList", "assignmentDefList", "assignmentDef",
  "assignStatementList", "assignStatement", "ifStatement",
  "elseStatements", "casesStatement", "casesStatementAltList",
  "casesStatementAlt", "implicitOperationBody", "pattern", "patternLessID",
  "patternList", "patternIdentifier", "matchValue", "bind", "setBind",
  "typeBind", "bindList", "multipleBind", "multipleSetBind",
  "multipleTypeBind", "typeBindList", null
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
      26,   292,    -1,    17,   225,    18,    -1,   196,   292,    27,
     225,    -1,    20,   292,    21,   141,   225,    -1,   225,    33,
     225,    -1,   225,    15,   225,    -1,   225,    14,   225,    -1,
     225,   155,   225,    -1,   225,   154,   292,   156,   225,    -1,
     225,   157,   225,    -1,   225,    20,   292,   158,   225,    -1,
     225,   171,   292,    -1,   225,   161,   292,    -1,   225,   159,
     292,    -1,   225,   229,    -1,   162,   291,    13,    17,   226,
      18,    -1,   225,   147,   292,    31,   292,   148,   225,    -1,
     225,    16,   225,    -1,   225,   142,   292,    31,   292,   145,
     225,    -1,   225,    32,   225,    -1,   225,    20,   292,    31,
     292,    32,   292,    31,   292,    21,   225,    -1,   225,    20,
     292,    32,   292,    21,   225,    -1,   225,   142,   292,    31,
     292,    31,   292,   145,   225,    -1,   225,   142,   292,   145,
     225,    -1,    17,   227,    13,   225,    18,    -1,    17,   227,
      13,   225,    18,    17,   291,    18,    -1,    17,   234,    13,
     225,    18,    17,   291,    18,    -1,    33,   218,    13,   225,
      -1,    15,   218,    13,   225,    -1,    14,   218,    13,   225,
      -1,   147,   292,   148,   218,    13,   225,    -1,    16,   218,
      13,    20,   292,    21,   225,    -1,   142,   292,   145,   218,
      13,    20,   292,    21,   225,    -1,    32,   218,    13,    20,
     292,    31,   292,    21,   225,    -1,    32,   218,    13,    20,
     292,    21,   225,    -1,    58,   299,    59,   225,    -1,    17,
      99,   311,    13,   225,    18,    -1,   315,    -1,    60,   310,
     173,    -1,   163,   310,   173,    -1,   314,    -1,   101,    17,
     313,    18,    -1,   247,    19,    -1,   247,    17,   291,    18,
      -1,    20,   320,    21,    -1,   103,    19,    -1,   103,   292,
      -1,   247,   100,    94,   292,    -1,   317,    -1,   164,   326,
      59,   292,   163,   225,    -1,   164,   321,    59,   292,   163,
     225,    -1,   164,   165,   321,   118,   292,   163,   225,    -1,
     164,   180,    12,   292,    38,   292,   163,   225,    -1,   164,
     180,    12,   292,    38,   292,   166,   292,   163,   225,    -1,
     167,   292,   163,   225,    -1,   225,    -1,   226,   146,   225,
      -1,   228,    -1,   227,    33,   228,    -1,   140,   235,    -1,
     139,   235,    -1,   138,   235,    -1,   143,   230,   144,    -1,
     143,   292,    28,   292,    31,   329,   144,    -1,   143,   292,
      28,   292,    31,   329,    13,   292,   144,    -1,   292,    28,
     292,    -1,   230,   146,   292,    28,   292,    -1,     4,    -1,
       4,   232,    -1,     4,   232,    33,    -1,   233,    -1,   232,
      33,   233,    -1,   180,    -1,   235,    -1,   235,    -1,   234,
      33,   235,    -1,   180,   149,   246,    -1,   180,   146,   235,
      -1,     5,    -1,     5,   237,    -1,   238,    -1,   237,   238,
      -1,   180,    12,   292,    -1,   242,    -1,   255,    -1,   259,
      -1,   241,    -1,   241,   240,    -1,   242,    -1,   255,    -1,
     259,    -1,   276,    -1,   288,    -1,    10,   278,    -1,     3,
      -1,     3,   243,    -1,     3,   243,    33,    -1,   244,    -1,
     243,    33,   244,    -1,   245,   180,    12,   246,   254,    -1,
     245,   180,    12,   246,    -1,   245,   180,    34,   252,    -1,
     245,   180,    34,   252,   254,    -1,   176,    -1,   177,    -1,
     178,    -1,   179,    -1,    -1,    17,   246,    18,    -1,   248,
      -1,   298,    -1,    35,   180,    36,   252,   173,    -1,   246,
      31,   246,    -1,   246,    37,   246,    -1,    20,   246,    21,
      -1,    43,   246,    -1,    41,   246,    -1,    42,   246,    -1,
      40,   246,    38,   246,    -1,    39,   246,    38,   246,    -1,
     249,    -1,   247,    -1,   180,    -1,   247,    90,   180,    -1,
     247,   104,   180,    -1,   188,    -1,   189,    -1,   190,    -1,
     191,    -1,   192,    -1,   193,    -1,   194,    -1,   195,    -1,
     250,    -1,   251,    -1,   246,    44,   246,    -1,    19,    44,
     246,    -1,   246,    27,   246,    -1,    19,    27,   246,    -1,
     253,    -1,   252,   253,    -1,   246,    -1,   180,   149,   246,
      -1,   180,    45,   246,    -1,    47,   321,    46,   292,    -1,
      48,    -1,    48,   256,    -1,    48,   256,    33,    -1,   257,
      -1,   256,    33,   257,    -1,   245,   258,    -1,   180,   149,
     246,    12,   292,    -1,   322,   149,   246,    12,   292,    -1,
     180,    12,   292,    -1,   322,    12,   292,    -1,     7,    -1,
       7,   260,    -1,     7,   260,    33,    -1,   261,    -1,   260,
      33,   261,    -1,   262,    -1,   263,    -1,   245,   180,   267,
     270,   271,   274,    -1,   245,   264,    -1,   180,   149,   249,
     180,   265,    46,   266,   271,   273,   275,    -1,    19,    -1,
      17,   323,    18,    -1,   265,    17,   323,    18,    -1,   292,
      -1,    53,    -1,    54,    -1,    19,    -1,    17,   268,    18,
      -1,   323,   149,   246,    -1,   268,   146,   323,   149,   246,
      -1,    -1,   270,    -1,   180,   149,   246,    -1,   270,   146,
     180,   149,   246,    -1,   272,    -1,    -1,    49,   292,    -1,
     274,    -1,    -1,    50,   292,    -1,    51,   292,    -1,    -1,
       8,    -1,     8,   277,    -1,   278,    -1,   277,   278,    -1,
     280,    -1,   279,    -1,   245,   180,   149,   281,   180,   265,
      46,   282,   271,   273,    -1,   245,   180,   267,   269,   283,
     271,   274,    -1,   246,   102,   246,    -1,    19,   102,   246,
      -1,   246,   102,    19,    -1,    19,   102,    19,    -1,   225,
      -1,    53,    -1,    54,    -1,   284,    -1,    -1,    55,   285,
      -1,   286,    -1,   285,   286,    -1,   287,   291,    -1,   287,
     291,   149,   246,    -1,    56,    -1,    57,    -1,    11,    -1,
      11,   289,    -1,    11,   289,    33,    -1,   290,    -1,   289,
      33,   290,    -1,   245,   312,    -1,    47,   292,    -1,   292,
      -1,   291,   146,   292,    -1,   296,    -1,   293,    -1,   295,
      -1,   294,    -1,   297,    -1,   298,    -1,    17,   292,    18,
      -1,   181,    -1,   180,    -1,   180,    98,    -1,   292,    90,
     180,    -1,   292,   104,   180,    -1,   292,    91,    92,    -1,
     292,    19,    -1,   292,    17,   291,    18,    -1,   292,    17,
     292,    86,   292,    18,    -1,   292,    90,   325,    -1,   292,
     153,   180,    -1,   292,   153,   325,    -1,   292,   152,   321,
      -1,   292,   152,   327,    -1,    58,   299,    59,   292,    -1,
     301,    -1,   303,    -1,   306,    -1,   307,    -1,   134,   329,
      13,   292,    -1,   135,   329,    13,   292,    -1,   136,   326,
      13,   292,    -1,    52,   326,    13,   292,    -1,    29,    30,
      -1,    29,   291,    30,    -1,    29,   292,    31,   329,    30,
      -1,    29,   292,    31,   329,    13,   292,    30,    -1,    29,
     292,    86,   292,    30,    -1,    15,    -1,    20,   291,    21,
      -1,    20,   292,    31,   327,    21,    -1,    20,   292,    31,
     327,    13,   292,    21,    -1,   170,    -1,    29,   308,    30,
      -1,    29,   309,    31,   329,    30,    -1,    29,   309,    31,
     329,    13,   292,    30,    -1,    88,    17,   291,    18,    -1,
      89,    17,   291,    18,    -1,    93,   333,    13,   292,    -1,
     168,    17,   292,    18,    -1,    95,   248,    17,   292,    18,
      -1,    95,    17,   292,   146,   246,    18,    -1,    96,    17,
     291,    18,    -1,    97,    17,   292,   146,   292,    18,    -1,
     150,   291,   151,    -1,   150,   292,    31,   329,   151,    -1,
     150,   292,    31,   329,    13,   292,   151,    -1,   183,    -1,
     182,    -1,   184,    -1,   185,    -1,    92,    -1,   187,    -1,
     160,    -1,   137,    -1,   186,    -1,   300,    -1,   299,   146,
     300,    -1,   258,    -1,   264,    -1,    60,   292,    61,   292,
     302,    -1,    63,   292,    -1,    62,   292,    61,   292,   302,
      -1,    64,   292,   149,   304,   173,    -1,    64,   292,   149,
     304,   146,    65,    27,   292,   173,    -1,   305,    -1,   304,
     146,   305,    -1,   323,    27,   292,    -1,    66,   292,    -1,
      67,   292,    -1,    68,   292,    -1,    69,   292,    -1,    70,
     292,    -1,    71,   292,    -1,    72,   292,    -1,    73,   292,
      -1,    74,   292,    -1,    75,   292,    -1,    76,   292,    -1,
      77,   292,    -1,    78,   292,    -1,    79,   292,    -1,    80,
     292,    -1,    81,   292,    -1,    82,   292,    -1,    83,   292,
      -1,    84,   292,    -1,    85,   292,    -1,   292,    66,   292,
      -1,   292,    37,   292,    -1,   292,    67,   292,    -1,   292,
     106,   292,    -1,   292,   105,   292,    -1,   292,   107,   292,
      -1,   292,   108,   292,    -1,   292,   109,   292,    -1,   292,
     110,   292,    -1,   292,   111,   292,    -1,   292,   112,   292,
      -1,   292,    12,   292,    -1,   292,   113,   292,    -1,   292,
     114,   292,    -1,   292,   115,   292,    -1,   292,   116,   292,
      -1,   292,   117,   292,    -1,   292,   118,   292,    -1,   292,
     119,   292,    -1,   292,   120,   292,    -1,   292,   121,   292,
      -1,   292,   122,   292,    -1,   292,   123,   292,    -1,   292,
     124,   292,    -1,   292,   125,   292,    -1,   292,   126,   292,
      -1,   292,   127,   292,    -1,   292,   128,   292,    -1,   292,
     129,   292,    -1,   292,   130,   292,    -1,   292,   131,   292,
      -1,   292,   132,   292,    -1,   292,   133,   292,    -1,   309,
      -1,   308,   146,   309,    -1,   292,    87,   292,    -1,   292,
      27,   225,    -1,   310,    31,   292,    27,   225,    -1,   312,
      -1,   311,   146,   312,    -1,   180,   149,   246,    -1,   180,
     149,   246,   100,   292,    -1,   180,   149,   246,    59,   292,
      -1,   314,    -1,   313,    33,   314,    -1,   247,   100,   292,
      -1,    60,   292,    61,   225,   316,    63,   225,    -1,    60,
     292,    61,   225,    63,   225,    -1,    62,   292,    61,   225,
      -1,   316,    62,   292,    61,   225,    -1,    64,   292,   149,
     318,   173,    -1,    64,   292,   149,   318,   146,    65,    27,
     225,   173,    -1,   319,    -1,   318,   146,   319,    -1,   323,
      27,   225,    -1,   283,   271,   274,    -1,   324,    -1,   322,
      -1,   325,    -1,    88,    17,   323,   146,   321,    18,    -1,
      89,    19,    -1,    89,    17,   323,    18,    -1,   321,    -1,
     323,   146,   321,    -1,   180,    -1,    67,    -1,   296,    -1,
     293,    -1,   295,    -1,   294,    -1,   297,    -1,   298,    -1,
      17,   292,    18,    -1,   327,    -1,   328,    -1,   321,   118,
     292,    -1,   321,   149,   246,    -1,   330,    -1,   329,   146,
     330,    -1,   331,    -1,   332,    -1,   323,   118,   292,    -1,
     323,   149,   246,    -1,   328,    -1,   333,   146,   328,    -1
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
     248,   252,   254,   256,   258,   260,   263,   267,   272,   278,
     282,   286,   290,   294,   300,   304,   310,   314,   318,   322,
     325,   332,   340,   344,   352,   356,   368,   376,   386,   392,
     398,   407,   416,   421,   426,   431,   438,   446,   456,   466,
     474,   479,   486,   488,   492,   496,   498,   503,   506,   511,
     515,   518,   521,   526,   528,   535,   542,   550,   559,   570,
     575,   577,   581,   583,   587,   590,   593,   596,   600,   608,
     618,   622,   628,   630,   633,   637,   639,   643,   645,   647,
     649,   653,   657,   661,   663,   666,   668,   671,   675,   677,
     679,   681,   683,   686,   688,   690,   692,   694,   696,   699,
     701,   704,   708,   710,   714,   720,   725,   730,   736,   738,
     740,   742,   744,   745,   749,   751,   753,   759,   763,   767,
     771,   774,   777,   780,   785,   790,   792,   794,   796,   800,
     804,   806,   808,   810,   812,   814,   816,   818,   820,   822,
     824,   828,   832,   836,   840,   842,   845,   847,   851,   855,
     860,   862,   865,   869,   871,   875,   878,   884,   890,   894,
     898,   900,   903,   907,   909,   913,   915,   917,   924,   927,
     938,   940,   944,   949,   951,   953,   955,   957,   961,   965,
     971,   972,   974,   978,   984,   986,   987,   990,   992,   993,
     996,   999,  1000,  1002,  1005,  1007,  1010,  1012,  1014,  1025,
    1033,  1037,  1041,  1045,  1049,  1051,  1053,  1055,  1057,  1058,
    1061,  1063,  1066,  1069,  1074,  1076,  1078,  1080,  1083,  1087,
    1089,  1093,  1096,  1099,  1101,  1105,  1107,  1109,  1111,  1113,
    1115,  1117,  1121,  1123,  1125,  1128,  1132,  1136,  1140,  1143,
    1148,  1155,  1159,  1163,  1167,  1171,  1175,  1180,  1182,  1184,
    1186,  1188,  1193,  1198,  1203,  1208,  1211,  1215,  1221,  1229,
    1235,  1237,  1241,  1247,  1255,  1257,  1261,  1267,  1275,  1280,
    1285,  1290,  1295,  1301,  1308,  1313,  1320,  1324,  1330,  1338,
    1340,  1342,  1344,  1346,  1348,  1350,  1352,  1354,  1356,  1358,
    1362,  1364,  1366,  1372,  1375,  1381,  1387,  1397,  1399,  1403,
    1407,  1410,  1413,  1416,  1419,  1422,  1425,  1428,  1431,  1434,
    1437,  1440,  1443,  1446,  1449,  1452,  1455,  1458,  1461,  1464,
    1467,  1471,  1475,  1479,  1483,  1487,  1491,  1495,  1499,  1503,
    1507,  1511,  1515,  1519,  1523,  1527,  1531,  1535,  1539,  1543,
    1547,  1551,  1555,  1559,  1563,  1567,  1571,  1575,  1579,  1583,
    1587,  1591,  1595,  1599,  1601,  1605,  1609,  1613,  1619,  1621,
    1625,  1629,  1635,  1641,  1643,  1647,  1651,  1659,  1666,  1671,
    1677,  1683,  1693,  1695,  1699,  1703,  1707,  1709,  1711,  1713,
    1720,  1723,  1728,  1730,  1734,  1736,  1738,  1740,  1742,  1744,
    1746,  1748,  1750,  1754,  1756,  1758,  1762,  1766,  1768,  1772,
    1774,  1776,  1780,  1784,  1786
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
     310,   314,   315,   316,   317,   318,   319,   331,   343,   344,
     345,   346,   347,   356,   357,   366,   376,   377,   378,   379,
     386,   391,   392,   396,   397,   401,   405,   409,   413,   420,
     421,   422,   423,   424,   425,   429,   433,   437,   441,   445,
     449,   450,   451,   452,   453,   466,   467,   469,   470,   471,
     476,   477,   488,   489,   496,   498,   500,   501,   502,   503,
     522,   523,   527,   528,   532,   533,   534,   539,   541,   542,
     555,   556,   566,   567,   568,   572,   573,   585,   586,   593,
     594,   598,   599,   603,   604,   608,   609,   616,   620,   621,
     622,   626,   627,   631,   632,   633,   634,   635,   642,   646,
     647,   648,   652,   653,   657,   658,   659,   660,   670,   671,
     672,   680,   681,   685,   686,   687,   688,   689,   690,   691,
     692,   693,   694,   695,   696,   697,   706,   710,   711,   712,
     716,   717,   718,   719,   720,   721,   722,   723,   727,   728,
     732,   733,   737,   738,   742,   743,   747,   748,   749,   753,
     764,   765,   766,   770,   771,   775,   779,   780,   781,   782,
     786,   787,   788,   792,   793,   797,   798,   802,   806,   810,
     815,   816,   817,   821,   822,   823,   827,   828,   832,   833,
     836,   838,   842,   843,   847,   848,   852,   856,   857,   861,
     872,   873,   877,   878,   885,   886,   890,   891,   895,   899,
     903,   904,   905,   906,   910,   911,   912,   916,   917,   921,
     925,   926,   930,   931,   935,   936,   958,   959,   960,   964,
     965,   969,   970,   974,   975,   980,   981,   982,   983,   984,
     985,   987,   988,   989,   990,   991,   992,   993,   994,   995,
     996,   997,   999,  1000,  1001,  1002,  1004,  1005,  1006,  1007,
    1008,  1010,  1011,  1012,  1013,  1015,  1016,  1018,  1019,  1021,
    1023,  1034,  1036,  1037,  1039,  1040,  1042,  1043,  1045,  1047,
    1049,  1058,  1059,  1060,  1065,  1073,  1075,  1076,  1077,  1083,
    1084,  1088,  1092,  1096,  1097,  1098,  1102,  1106,  1112,  1113,
    1117,  1118,  1122,  1126,  1127,  1131,  1132,  1136,  1137,  1141,
    1145,  1146,  1147,  1148,  1149,  1150,  1151,  1152,  1153,  1154,
    1155,  1156,  1157,  1158,  1159,  1160,  1161,  1162,  1163,  1164,
    1168,  1169,  1170,  1171,  1172,  1173,  1174,  1175,  1176,  1177,
    1178,  1179,  1180,  1181,  1182,  1183,  1184,  1185,  1186,  1187,
    1188,  1189,  1190,  1191,  1192,  1193,  1194,  1195,  1196,  1197,
    1198,  1199,  1200,  1204,  1205,  1209,  1213,  1214,  1218,  1219,
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

  private static final int yylast_ = 12113;
  private static final int yynnts_ = 123;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 43;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 211;

  private static final int yyuser_token_number_max_ = 465;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */

}


