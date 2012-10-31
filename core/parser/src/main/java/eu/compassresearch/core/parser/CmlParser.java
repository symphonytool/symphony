
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
       106,   775,   -79,   -73,   775,   898,   -63,   112,   171,   106,
    -661,  -661,  -661,  -661,  -661,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  -661,   280,  -661,   139,   127,   292,  -661,  -661,
     198,   -73,  -661,   173,   339,  -661,  -661,  -661,   314,   397,
    -661,   432,   454,  -661,  -661,   775,   109,   293,  1740,   -79,
    3590,  -661,   270,  -661,   775,  3590,   467,   426,  -661,  -661,
    -661,    31,  -661,  -661,  -661,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  -661,  -661,  -661,    84,  -661,   898,   359,   394,
    -661,  1740,  1813,   127,  -661,  1740,    87,  1740,   323,  1740,
    1740,  1740,  1740,  1740,  -661,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  -661,   899,    18,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  3590,  3590,  3046,  1198,   964,  3590,  3590,  3590,
    3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,
    3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,   503,
     533,  1198,   146,   537,   551,  1198,  1198,  1198,  3590,   556,
    -661,   471,  -661, 12000,  -661,  -661,  -661,  -661,  -661,  -661,
    -661,  -661,  -661,  -661,  1198,  -661,  1740,   398,  -661,  6133,
    1198,  1198,  -661,  3590,  1740,  3590,  1740,  -661,   402,   613,
     424,   424,   424,   293,   424,   424,  3590,  1449,   199,  3732,
     281,  -661,   747,   -10,   899,   110,  -661,   660,  1740,  1740,
     490,   582,   828,   836,   585,   585,   585,  1740,  1740,  1740,
    1740,   445,   453,  6255,    30,  6378,  -661,   162,  5275,   178,
     603,  -661,  -661,    -2,  -661,  -661,   626,  -661,  -661,    85,
    -661,  -661,    59,  -661,  6500,  6622,  1072,  1072,  1072,  1072,
   12451,  1072,  1072,  1072,  1072,  1072,  1072,  1072,  1072,  1072,
    1072,  1072,  1072,  1072,  1072,  1242,  3590,  3590,   495,  -661,
      23,  3590,   635,  3590,  3590,  -661,   316,    35,  -661,  -661,
    -661,    44,   643,   -90,  6745,  3590,  -661,  3590,  3590,  -661,
    3590,  3590,  3590,   835,   578,   491,  3590,  3590,  3590,  3590,
    3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,
    3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,  3590,
    3590,  3590,  3590,  3590,  3590,  1198,  1180,     0,   222,   899,
     493,   527,    -5,  -661,   531,    74, 12000,   833, 12000,   894,
     516,   775,   565,    19,   517,   613,  -661,  -661,  -661,  -661,
    -661,   332,   356,  -661,  -661,   399,   423,   428,   434,   477,
    6867,   514,   521,  2600,  1485,  -661,  -661,  3590,  -661,   407,
     407,   407,  3590,   407,   407,  3590,  3590,  3590,   407,   407,
    3590,  3590,  3590,  -661,   407,   293,  1198,  -661,  1740,  1740,
    -661,  -661,  -661,   899,   899,  -661,  1813,  1740,  1740,   899,
     585,   585,   899,  -661,  -661,  -661,  -661,  3590,  1198,  -661,
    1198,  3590,  3590,  -661,  3590,  1198,  3590,  1740,  3590,  1740,
    3590,   964,  3590,  1198,   165, 12000,   182,  3590,  1198,  6989,
    3590,   184,  7111,  3590,  1198,  1740,  3590,  1198,  3590,  3590,
    -661,  1198,  7233, 12501,   203,  7355,  1455,  5077,  5077,  -661,
    -661,  -661,  -661,  1455,  1455,  1455,  1455, 12501, 12501, 12501,
   12501, 12501, 12324, 12374, 12244, 12122, 12501, 12501, 12501, 12501,
    3992,  3992,  1242,  2624,  2450,  2450,  1242,  1242,  1072,  1072,
     327,   344,  -661,  -661,  -661,  -661,  -661,  1198,  1740,   632,
    1740,  3590,   522,   655,  -661,  1198,  -661,  3590,  3590,   613,
     530,   775,  -661,  -661,  -661,  -661,  3590,   536,   684,  -661,
    -661,  -661,  3590,   424,   407,   424,   407,   407,   407,   384,
     407,   424,   708,   -19,   724,   569,   424,   424,   424,  2426,
    3182,  -661,  -661,  -661,  -661,  3590,   424,   424,   964,  3590,
    3590,   734,  3318,   293,   293,   293,  3590,  3590,  3590,  3590,
     416,  3590,  1654,   482,  -661,  5397,  -661,  -661,  -661,  2600,
    -661,   204,   638,   743,  1000,  1634,  4659,   612,   586,  7477,
     415,  7599,  4781,   525,    58, 12000, 12000, 12000,  3732,  -661,
     706,   899,   899,  1426,   585,   585, 12000,   636,    99,    89,
    7723, 12000,  7845,  -661,   129, 12501,   899, 12122, 12000,  -661,
    5520,   136,  -661,    -8,  -661,  -661, 12122,  -661,  1740,  7967,
    -661,  3590, 12000,  -661,   899, 12122,  -661, 12122, 12122,    27,
    -661,  -661,  3590,   414,   899,  1198,  -661,    82,   899, 12000,
     615,  3590,  -661,   744, 12000, 12000,   588,   276,  -661, 12000,
     616,  -661,    19, 12000,  -661,  -661,  -661,  -661,  -661,  -661,
    3580,  3590,  -661,  -661,   496,  3590,   592,   768,  2774,   769,
     500,   519,   524,   536,   231,  3152,   542,  5152,   287,   736,
    -661,   765, 12000,   559,   573,   215,  5642,    -9,  8089,  3590,
    -661, 12000,  -661,  -661,  -661,  8211,  8333,   119,  8455,    -3,
    1198,   777,   -14,   728,  3926,  2600,  2600,  2600,  3590,  2600,
    2600,  3590,  3590,  3590,  2600,  2600,  3590,  3590,  3590,  -661,
    -661,  2600,   256,  2600,  3454,  2600,  1723,  -661,  3590,   407,
     407,  -661,  3590,  3590,   407,  3590,  -661,  3590,  -661,  3590,
    -661,  -661,  3590,  -661,  3590,  3590,  -661,   548,  -661,  3590,
     719,  -661,  8577,  3590,  -661,  8699,  1740,   259,  1198,  2910,
    1740, 12000,  -661,  -661,  1994,   398,  1740,  -661,   780,  8821,
     407, 12000,   786,  3590,  -661,  3696,   595,  2774,  2600,  2600,
     772,   124,  -661,  -661,  2600,  -661,  -661,   287,  -661,  3590,
     655,  -661,   782,  2600,  2600,  2600,  2600,  3590,  -661,  1198,
    8943,   324,  -661,   424,   424,   783,  -661,   685,  3590,  3590,
    3590,  2600,   552,   952,   974,  4537,     1,   446,  5765,  9066,
    4903,   679,    32, 12000, 12000, 12000,  3696,  -661,  -661,  3590,
   12000,   446,  -661,  9188,    58,   612,  9310,  9433,   525, 12000,
    9555,  9679,  9803,  9925, 12122,   779,  -661, 12000,  -661,  -661,
   10047,  -661,   899,  -661,   261,  -661,  -661,   736, 12000,   899,
     283,   475,   627,   754,   665,   553,  3590,   407,  -661,  3590,
   12000,  2600,  -661,  -661,  -661,  3590,  2600,   536,  3410,  -661,
     465,  -661,  3590,  -661,  3696,  5397,  3696,  3002,  5025, 10169,
     145,  -661,    -6,  3590,  -661,  3590,   596,   597,  2600,  3590,
   10291,  4048,  4170,  3696,  3590,  3590,  2600,  3590,  2600,  3590,
    2600, 12000,   407,  3590,  1198,  -661,  -661,  -661,  3590,  3590,
    -661,  -661,   655,  2046,  2063,   632,   736,  3590,  3590, 10413,
    -661, 12000,  3696, 10535,  3560,  -661,   797,  1740,  5888,  3590,
    2600,   321,  2600,   633,  -661,  2600,  -661,   801,  2600,  3696,
     267,  4292,  3590,  2600,  2600, 10657, 10779,    32,  6011,     1,
   10901,   679,   612, 12000,    80,  5520,  3676,   773,  -661,    87,
     899,    87,   899,    88,   655, 12000, 12000,  -661,  2600,  -661,
    3590,   899,  2600,  3590, 11023,  3696,  3590,  2600,  3696,   800,
    -661,   177,  3696,  5397,  3590,  -661,  -661,  2600,  2600,  3804,
    3696,  3696,  3590,  2600,  3590,  2600,  2600,  3590,  -661,  -661,
    -661,  3590,  -661,  2252,  -661,  -661,   268,  -661, 11145,  2600,
   11267,  3696,  2600,  2600, 11389,  3696,  3696,  2600,  3590, 11512,
       1, 11634,     1,  -661, 11756, 12000,  -661,  -661,  3696,   736,
    -661,  2600,  3696,  2600,  2866,  2600,  3696,  4414,  3590,  2600,
    -661,   655,  -661,  3696,  -661,  -661,  2600, 11878,     1,  -661,
    3696,  2600,     1
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,   156,   129,   140,   217,   207,     0,     0,     0,     2,
       3,     5,     6,     7,     8,     9,   145,   146,   147,   165,
     166,   167,   168,   157,   159,     0,   134,   130,   132,   135,
       0,   141,   142,     0,   218,   220,   222,   223,     0,   208,
     210,     0,     0,     1,     4,   158,     0,     0,     0,   131,
       0,   143,     0,   225,   219,     0,     0,     0,   340,   343,
     342,     0,   337,   336,   338,   339,   344,   341,   212,   444,
     446,   445,   443,   447,   448,     0,   435,   209,     0,     0,
     160,     0,     0,     0,   139,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   184,   187,   188,   189,   190,   191,
     192,   193,   194,   138,   183,   171,   182,   195,   196,   172,
     133,   317,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     321,   290,   289,   144,   283,   285,   284,   282,   286,   287,
     304,   305,   306,   307,     0,   233,     0,     0,   221,     0,
       0,     0,   437,     0,     0,     0,     0,   211,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    31,    12,
       0,   136,   162,   184,   203,   163,   201,     0,     0,     0,
       0,     0,     0,     0,   178,   179,   177,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   312,     0,   280,     0,
     410,   442,   441,     0,   434,   433,     0,   450,   451,     0,
     347,   348,     0,   345,     0,     0,   357,   358,   359,   360,
     361,   362,   363,   364,   365,   366,   367,   368,   369,   370,
     371,   372,   373,   374,   375,   376,     0,     0,     0,   460,
       0,     0,     0,     0,     0,   439,     0,     0,   454,   456,
     457,     0,     0,     0,   280,     0,   291,     0,     0,   295,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     182,     0,   242,   449,     0,     0,   215,     0,   216,     0,
       0,   249,   169,   273,     0,   148,   150,   151,   152,   153,
     154,     0,     0,    43,    42,     0,     0,     0,     0,     0,
       0,    52,     0,     0,     0,    48,    50,     0,    32,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    34,     0,     0,     0,   161,     0,     0,
     202,   164,   170,   200,   198,   176,     0,     0,     0,   199,
     174,   175,   197,   185,   186,   288,   318,     0,     0,   313,
       0,     0,     0,   322,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     333,     0,     0,   388,     0,   280,   378,   377,   379,   292,
     298,   294,   293,   381,   380,   382,   383,   384,   385,   386,
     387,   389,   390,   391,   392,   393,   394,   395,   396,   397,
     398,   399,   400,   401,   402,   403,   404,   405,   406,   407,
     408,   409,   301,   302,   299,   300,   234,     0,     0,     0,
       0,     0,     0,     0,   241,     0,   438,     0,     0,     0,
       0,   250,   251,   254,   253,   155,     0,     0,   274,   276,
      10,   149,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    53,     0,    51,     0,   317,     0,     0,
     265,    61,    62,    63,    64,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   119,     0,   104,   101,   110,     0,
      49,     0,    31,    18,    17,    22,     0,    21,    16,     0,
       0,     0,     0,    23,    25,    29,    28,    27,    13,   137,
       0,   205,   204,     0,   181,   180,   281,     0,     0,     0,
       0,   412,     0,   411,     0,   452,   453,   311,   303,   346,
       0,     0,   354,     0,   325,   326,   327,   461,     0,     0,
     331,     0,   458,   440,   459,   308,   455,   309,   310,     0,
     328,   296,     0,     0,   235,     0,   227,     0,   239,   243,
       0,     0,   224,   440,   213,   214,     0,     0,   252,   279,
       0,   278,   275,    46,    47,    37,    45,    44,    36,    41,
       0,     0,    40,    35,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   290,     0,     0,     0,     0,   242,
     264,     0,    65,     0,     0,     0,     0,     0,     0,     0,
     107,   108,   123,   122,   121,     0,     0,     0,     0,     0,
       0,   441,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    14,
      79,     0,     0,     0,     0,     0,     0,    33,     0,     0,
       0,   124,     0,     0,     0,     0,   173,     0,   319,     0,
     314,   316,     0,   323,     0,     0,   349,     0,   352,     0,
       0,   329,     0,     0,   334,     0,     0,     0,     0,     0,
       0,   246,   436,    11,     0,   237,     0,   277,     0,     0,
       0,    56,     0,     0,    54,    59,     0,     0,     0,     0,
       0,     0,   415,    66,     0,   271,   272,   266,   267,     0,
       0,   106,     0,     0,     0,     0,     0,     0,   102,     0,
       0,     0,   420,     0,     0,     0,   103,     0,     0,     0,
       0,     0,    71,    70,    82,     0,    84,    69,     0,     0,
       0,    72,    74,    78,    77,    76,    89,   120,    67,     0,
     422,    68,    15,     0,    26,    19,     0,   127,    24,   206,
       0,     0,     0,     0,   350,     0,   355,   356,   330,   332,
       0,   297,   236,   228,     0,   231,   232,   242,   230,   240,
       0,     0,     0,   265,   238,   417,     0,     0,    38,     0,
      57,     0,    55,    93,    92,     0,     0,     0,     0,   268,
     269,   432,     0,    91,    99,   303,   413,     0,     0,     0,
       0,   429,     0,     0,   105,     0,     0,     0,     0,     0,
       0,     0,     0,   116,     0,     0,     0,     0,     0,     0,
       0,   109,     0,     0,     0,   320,   315,   324,     0,     0,
     335,   229,   245,     0,     0,     0,   242,     0,     0,     0,
      39,    58,    60,     0,     0,   416,     0,     0,     0,     0,
       0,     0,     0,     0,   427,     0,   421,     0,     0,   117,
       0,     0,     0,     0,     0,     0,     0,    75,     0,    88,
       0,    73,    20,   128,     0,     0,     0,   248,   244,   260,
     258,   259,   257,     0,     0,   419,   418,    30,     0,   100,
       0,   270,     0,     0,     0,   424,     0,     0,   414,     0,
     430,     0,   431,   356,     0,    94,    80,     0,     0,     0,
     112,   111,     0,     0,     0,     0,     0,     0,   125,   351,
     353,     0,   226,     0,   256,    95,     0,    98,     0,     0,
       0,   423,     0,     0,     0,   118,   113,     0,     0,     0,
      86,     0,    83,    81,     0,   247,   262,   263,   261,   242,
      90,     0,   425,     0,     0,     0,   114,     0,     0,     0,
     126,   245,    97,   426,   428,    96,     0,     0,    87,   255,
     115,     0,    85
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -661,  -661,  -661,   819,  -661,  -661,  -322,  -152,   161,  -661,
     478,  -661,  -661,    73,  1134,  -661,  -661,   131,  -100,  -661,
    -661,  -661,   795,   -77,    11,  -661,  -661,   815,  -661,  -324,
    -177,   115,  -661,   802,    49,  1481,  -661,   689,  -142,  -661,
    -661,   470,  -192,   658,   420,  -661,   784,   830,   476,  -661,
     822,  -661,  -661,   845,   -26,  -661,   254,  -661,  -661,   138,
    -628,  -661,  -140,  -482,  -661,  -661,  -661,  -325,  -661,  -661,
    -661,  -661,    46,  -661,  -661,   120,  -661,  -661,  -661,   269,
     -89,   -50,   377,   884,   923,  1023,  1104,   803,   371,   492,
    -661,   -38,  -661,  -661,   181,  -661,  -661,  -661,   505,   372,
    -661,  -645,  -661,  -660,  -661,  -661,  -661,  -661,   -11,  -661,
     -92,   -21,  -144,  -661,  -183,  -143,   -43,  -135,  -134,   501,
    -661,  -661,  -661
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   189,   342,   343,   354,
     355,   515,   513,   754,   755,   930,   543,   544,   700,   560,
      13,    27,    28,   756,   344,    14,    31,    32,    15,   334,
     335,   336,    23,    24,   490,   194,   104,   105,   106,   107,
     108,   195,   196,   377,   337,    39,    40,   230,   338,    34,
      35,    36,    37,   231,   617,   837,   167,   317,   843,   322,
     483,   484,   947,   948,   992,   339,   491,   492,   493,   494,
     842,  1019,   659,   660,   767,   768,   769,   340,   498,   499,
     214,   545,   154,   155,   156,   157,   158,   159,   232,   233,
     160,   726,   161,   591,   592,   162,   163,   219,   220,   667,
     761,   631,   781,   546,   547,   921,   548,   870,   871,   661,
     265,   224,   266,   225,    76,   226,   227,   228,   267,   268,
     269,   270,   260
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -170;
  public static final short
  yytable_[] =
  {
       153,   622,   190,   380,   272,   169,   259,   495,   762,   782,
     356,   501,   271,    29,   646,   685,   686,    75,   476,   729,
     318,   925,   777,   223,   320,   217,   324,   325,   777,   345,
     346,   770,   348,   349,   690,   378,   417,   553,   554,   555,
     733,   557,   558,   173,   481,   789,   563,   564,   426,   258,
      25,   396,   568,    33,    38,   223,   397,   428,    84,   273,
      29,   430,   213,   215,   218,   690,   496,   234,   235,   236,
     237,   238,   239,   240,   241,   242,   243,   244,   245,   246,
     247,   248,   249,   250,   251,   252,   253,   254,   255,   373,
     191,   364,   486,   987,    25,    75,   175,   173,   274,   738,
     440,    26,   719,    33,   406,   738,   347,    30,   211,     1,
       2,     3,   717,     4,   198,    16,   406,    41,   410,   720,
     718,    81,   212,   326,    16,   328,    38,    85,   739,    86,
      87,   199,   785,   475,   993,   407,   350,   856,   424,   379,
     424,   482,   722,    82,   366,    88,   477,   407,   692,    89,
      90,    91,    92,    93,     5,   693,   694,   376,   695,   723,
     696,   647,   697,   261,   778,   626,   628,   414,   416,   418,
     786,    43,   698,   427,   421,   366,   397,   356,   734,   692,
     174,   427,   635,   594,   638,   639,   640,   642,   643,   434,
     427,   696,   399,   697,   191,    19,    20,    21,    22,  -169,
     595,   366,   600,   698,  1003,   411,   415,   415,   403,   902,
      50,   419,   915,   415,   422,   926,   357,   370,   358,   371,
     424,   611,   707,   472,   988,   432,   427,   433,   435,   372,
     436,   437,   438,   176,   409,   427,   443,   444,   445,   446,
     447,   448,   449,   450,   451,   452,   453,   454,   455,   456,
     457,   458,   459,   460,   461,   462,   463,   464,   465,   466,
     467,   468,   469,   470,   471,   397,   579,   320,   551,   593,
     857,   584,   473,    47,   774,   427,    48,   833,   954,   901,
       6,     7,   727,   597,   570,   976,  1020,   164,   861,   165,
     193,   923,    42,   164,   374,   165,    66,   609,    95,    96,
      97,    98,    99,   100,   101,   102,   577,   415,   397,   728,
     198,   397,   556,    45,   375,   559,   561,   562,   924,    46,
     565,   566,   567,   424,   404,    49,   258,   199,   397,   276,
     397,    55,   603,   613,    95,    96,    97,    98,    99,   100,
     101,   102,   874,   765,   766,    47,   279,   576,    48,   397,
     397,   580,   581,    52,   582,   578,   585,   875,   587,   644,
     588,   411,   590,   279,   650,   651,   652,   596,   424,   504,
     599,   478,    54,   602,   663,   664,   605,    47,   607,   608,
      48,   380,   497,   966,   967,   903,   569,   814,   815,   505,
      75,  1031,   818,   623,   533,   534,   535,   683,   180,   181,
     182,   183,    56,    57,   641,   424,    58,   424,   180,   181,
     182,   183,   506,   977,   397,    69,   184,   185,   284,   166,
      17,   180,   181,   182,   183,   744,   184,   185,   848,    17,
      77,   619,   505,    55,   423,   284,   507,   624,   625,   184,
     185,   508,   656,   171,    78,   172,   629,   509,   682,   677,
     502,    59,   633,   373,   373,   373,   505,   373,   373,   313,
     314,   375,   424,   373,   373,   425,    79,   505,   373,   657,
     215,   737,   994,    83,    60,   662,    18,   314,   503,   666,
     668,    48,   671,   221,   170,    18,   675,   676,   415,   678,
     510,   684,    69,    69,    61,   701,    62,    63,    64,    65,
      66,    67,   207,   201,    56,    57,   208,    75,    58,   750,
     505,   385,   209,   758,    84,   702,   637,   207,    69,   210,
     256,   208,    69,    69,    69,   910,   186,   209,   178,   505,
     191,   179,   759,   505,   210,   373,   186,   760,   373,   373,
     373,    69,   373,   373,   672,   673,   674,    69,    69,   186,
     257,   732,   505,    59,   263,   764,   187,   505,   364,   711,
     424,   712,   735,   736,   552,    55,   187,   686,   264,   276,
     942,   741,   772,   275,   188,   375,    60,   904,   321,   187,
     207,   680,   330,   593,   208,   690,   773,   552,   787,   366,
     209,   749,   505,   692,   834,   751,   681,   210,    62,    63,
      64,    65,    66,    67,   341,   696,   505,   697,   851,   927,
     928,   397,   907,   825,   917,   221,     1,   698,   386,   780,
       4,   331,   209,   332,   333,   393,   359,   360,   375,   505,
     505,   876,   877,   394,   405,   872,    56,    57,   795,   408,
      58,   798,   799,   800,   407,   364,   803,   804,   805,   615,
      55,   616,   420,   908,   810,   357,   429,   358,   813,   191,
      69,     5,   816,   817,   634,   819,   636,   820,   366,   821,
     441,   442,   822,   479,   823,   824,   480,   485,   382,   827,
     860,   497,   369,   830,   370,    59,   371,   207,   489,   838,
     500,   208,    69,    69,   512,   366,   372,   209,   969,   692,
     221,   514,   620,   850,   210,   621,   693,   694,    60,   695,
     627,   696,   690,   697,   373,   373,   630,   632,   373,   415,
     645,    56,    57,   698,   865,    58,   868,   869,   222,   366,
      62,    63,    64,    65,    66,    67,   648,   828,   880,   881,
     882,    19,    20,    21,    22,   370,   207,   371,   373,   649,
     208,   669,   715,    69,   406,   366,   209,   372,   360,   891,
     944,   743,   742,   210,   740,   746,   367,   368,   191,   369,
      59,   370,   752,   371,   207,    69,   364,    69,   208,   971,
     753,   757,    69,   372,   209,   481,   771,   790,    69,   788,
      69,   210,   855,    60,   376,    69,   909,   846,   849,   911,
     878,    69,   862,   879,    69,   913,   899,   905,    69,   658,
     373,   482,   918,   222,   960,    62,    63,    64,    65,    66,
      67,   974,   366,   810,   991,   780,   692,  1002,    44,   931,
     852,   262,   550,   807,   935,   936,   695,   938,   696,   940,
     697,    74,   373,   943,   110,   487,    51,    80,   945,   946,
     698,   109,    55,   381,    69,   207,   573,   955,   956,   208,
     207,   177,    69,   207,   208,   209,   387,   208,    68,   964,
     209,   996,   210,   209,   388,   973,   168,   210,    53,   953,
     210,   745,   979,   844,   109,   109,   366,   859,   109,   906,
     109,  1039,   109,   109,   109,   109,   109,   367,   368,   665,
     369,   747,   370,   589,   371,    69,   488,   989,   826,   583,
     415,   679,   970,   998,   372,  -169,  1000,    69,    74,    74,
       0,   207,    70,     0,  1004,   208,   207,    58,   606,     0,
     208,   209,  1009,     0,  1011,     0,   209,  1014,   210,     0,
       0,  1015,     0,   210,    74,     0,     0,     0,    74,    74,
      74,    19,    20,    21,    22,  -169,     0,     0,  1027,     0,
       0,    71,     0,     0,     0,     0,     0,    74,     0,   109,
       0,     0,    59,    74,    74,     0,     0,   109,  1037,   109,
       0,    55,     0,     0,     0,   690,  -169,  -169,   685,   686,
    -169,     0,    69,     0,   688,    60,     0,     0,   109,    70,
      70,   109,   109,     0,     0,     0,   689,   690,     0,     0,
     109,   109,   109,   109,     0,   439,     0,    62,    63,    64,
      65,    66,    67,     0,     0,    70,     0,     0,     0,    70,
      70,    70,     0,   364,     0,  -169,     0,     0,    71,    71,
       0,     0,     0,     0,     0,     0,     0,     0,    70,     0,
       0,     0,    56,    57,    70,    70,    58,    69,  -169,     0,
       0,    72,     0,     0,    71,     0,     0,     0,    71,    71,
      71,     0,     0,     0,    19,    20,    21,    22,  -169,     0,
    -169,  -169,  -169,  -169,  -169,  -169,    74,    71,     0,     0,
       0,   279,     0,    71,    71,   366,     0,     0,     0,   692,
       0,    59,     0,     0,    69,     0,   693,   694,     0,   695,
       0,   696,     0,   697,     0,    69,   691,   366,    74,    74,
       0,   692,     0,   698,    60,     0,     0,     0,   693,   694,
       0,   695,     0,   696,     0,   697,     0,     0,    72,    72,
       0,     0,    73,   366,   229,   698,    62,    63,    64,    65,
      66,    67,     0,     0,   367,   368,    69,   369,     0,   370,
       0,   371,   283,   284,    72,     0,     0,    70,    72,    72,
      72,   372,     0,     0,     0,     0,   285,     0,     0,    74,
       0,   109,   109,     0,     0,     0,     0,    72,     0,   109,
     109,   109,     0,    72,    72,     0,     0,    55,     0,    70,
      70,    74,     0,    74,   313,   314,    71,     0,    74,     0,
     109,     0,   109,     0,    74,    55,    74,     0,     0,    73,
      73,    74,     0,     0,   315,   316,     0,    74,   109,     0,
      74,     0,     0,     0,    74,     0,     0,     0,    71,    71,
       0,     0,     0,     0,     0,    73,     0,     0,     0,    73,
      73,    73,     0,     0,     0,     0,     0,     0,     0,     0,
      70,   279,     0,     0,     0,   221,     0,     0,    73,     0,
       0,    69,    58,     0,    73,    73,     0,     0,     0,     0,
      74,   109,    70,   109,    70,     0,    56,    57,    74,    70,
      58,     0,     0,     0,     0,    70,     0,    70,     0,    71,
      69,     0,    70,     0,     0,     0,    72,     0,    70,     0,
       0,    70,     0,     0,     0,    70,     0,    59,     0,     0,
       0,    71,     0,    71,     0,     0,     0,     0,    71,     0,
       0,    74,   283,   284,    71,    59,    71,     0,    72,    72,
      60,    71,     0,    74,     0,     0,   285,    71,     0,     0,
      71,     0,     0,     0,    71,     0,     0,     0,    60,     0,
     474,    70,    62,    63,    64,    65,    66,    67,     0,    70,
     309,   310,   311,   312,   313,   314,   109,     0,   222,     0,
      62,    63,    64,    65,    66,    67,     0,    73,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,     0,    72,
      71,   109,     0,     0,     0,     0,     0,     0,    71,     0,
       0,     0,    70,     0,     0,     0,     0,     0,    74,    73,
      73,    72,     0,    72,    70,     0,     0,     0,    72,     0,
       0,     0,     0,     0,    72,     0,    72,     0,     0,     0,
       0,    72,     0,    85,     0,    86,    87,    72,     0,     0,
      72,    71,     1,     0,    72,   351,     4,   331,   352,   332,
     333,    88,   353,    71,     0,    89,    90,    91,    92,    93,
       0,     0,     0,     0,   279,     0,     0,     0,     0,     0,
      73,     0,     0,    74,     0,     0,     0,   542,     1,     0,
       0,   351,     4,   331,   352,   332,   333,     5,   549,    70,
      72,     0,    73,     0,    73,     0,     0,     0,    72,    73,
       0,     0,     0,     0,     0,    73,     0,    73,     0,     0,
       0,     0,    73,     0,     0,     0,     0,     0,    73,   103,
      74,    73,     0,     5,     0,    73,     0,     0,    71,   109,
       0,    74,     0,   109,     0,   283,   284,   109,     0,   109,
       0,    72,     0,     0,     0,     0,     0,     0,     0,   285,
       0,     0,   192,    72,    70,     0,   197,     0,   200,     0,
     202,   203,   204,   205,   206,     0,     0,     0,     0,   305,
       0,    73,    74,   309,   310,   311,   312,   313,   314,    73,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   716,
       0,     0,     0,    71,     0,     0,   193,   315,   316,     0,
       0,    70,    66,     0,    95,    96,    97,    98,    99,   100,
     101,   102,    70,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    73,     0,     0,     0,     0,     0,    72,     0,
       0,     0,     0,     0,    73,     0,     0,   319,   359,   360,
      71,     0,     0,   655,   362,   327,     0,   329,     0,     0,
       0,    71,     0,    70,     0,     0,   363,   364,   685,   686,
     687,     0,     0,     0,   688,     0,     0,     0,     0,   383,
     384,     0,     0,   706,     0,     0,   689,   690,   389,   390,
     391,   392,     0,     0,     0,     0,     0,    74,     0,     0,
       0,     0,    71,    72,     0,     0,   109,   109,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    73,
     109,     0,     0,     0,     0,     0,    74,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   685,   686,   687,
       0,     0,     0,   688,     0,     0,     0,     0,     0,     0,
      72,     0,     0,     0,     0,   689,   690,    85,     0,    86,
      87,    72,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    88,   365,   366,    70,    89,
      90,    91,    92,    93,    73,     0,     0,     0,   367,   368,
       0,   369,     0,   370,     0,   371,   691,   366,     0,     0,
       0,   692,    72,     0,     0,   372,     0,    70,   693,   694,
       0,   695,     0,   696,     0,   697,     0,    71,     0,   792,
     793,   794,     0,   796,   797,   698,     0,   699,   801,   802,
      85,    73,    86,    87,     0,   806,     0,   808,     0,   811,
       0,     0,    73,     0,     0,     0,    71,     0,    88,     0,
       0,     0,    89,    90,    91,    92,    93,     0,     0,   571,
     572,     0,     0,     0,     0,   691,   366,     0,   574,   575,
     692,     0,     0,     0,     0,     0,     0,   693,   694,     0,
     695,     0,   696,    73,   697,     0,     0,     0,   586,     0,
     327,     0,   853,   854,   698,     0,   812,     0,   858,     0,
       0,     0,     0,     0,     0,     0,   604,   863,   864,   866,
     867,     0,     0,     0,     0,     0,     0,    72,     0,     0,
      94,     0,     0,     0,     0,   883,    66,     0,    95,    96,
      97,    98,    99,   100,   101,   102,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    72,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   614,
       0,   618,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   912,     0,     0,     0,     0,
     914,     0,     0,   193,     0,     0,     0,     0,    73,    66,
       0,    95,    96,    97,    98,    99,   100,   101,   102,     0,
       0,    85,   929,   840,    87,     0,     0,     0,     0,     0,
     937,     0,   939,     0,   941,     0,     0,    73,     0,    88,
       0,     0,     0,    89,    90,    91,    92,    93,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   965,     0,   968,     0,     0,   972,
       0,     0,   975,    85,     0,   949,    87,   980,   981,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   730,
      85,    88,   951,    87,     0,    89,    90,    91,    92,    93,
       0,     0,   995,     0,     0,     0,   997,     0,    88,     0,
       0,  1001,    89,    90,    91,    92,    93,     0,     0,     0,
       0,  1005,  1006,     0,     0,     0,     0,  1010,     0,  1012,
    1013,     0,     0,     0,     0,     0,     0,  1018,     0,     0,
       0,     0,     0,  1022,     0,     0,  1024,   972,     0,     0,
       0,  1026,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,  1032,     0,  1033,     0,  1035,
       0,     0,     0,  1038,     0,     0,     0,     0,     0,     0,
    1040,     0,     0,     0,    94,  1042,     0,     0,     0,     0,
      66,     0,    95,    96,    97,    98,    99,   100,   101,   102,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   832,     0,     0,
       0,   839,     0,     0,     0,   841,    94,   845,     0,     0,
       0,     0,    66,     0,    95,    96,    97,    98,    99,   100,
     101,   102,     0,    94,     0,     0,     0,     0,     0,    66,
       0,    95,    96,    97,    98,    99,   100,   101,   102,     0,
       0,     0,     0,     0,     0,     0,   516,   517,   518,   519,
       0,     0,   520,     0,   521,   522,   523,   524,   525,     0,
       0,   114,     0,     0,   526,   527,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   115,  1016,  1017,     0,     0,     0,
     528,     0,   529,     0,     0,     0,   530,     0,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   138,     0,     0,
     139,   140,     0,     0,    58,   141,     0,   142,   143,   144,
       0,     0,     0,   531,     0,   532,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   950,   952,   145,   146,   147,    59,
     533,   534,   535,     0,   536,     0,     0,     0,   961,   537,
       0,     0,   148,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    60,     0,   538,   539,   540,     0,     0,   541,
     149,     0,   150,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   151,   152,    62,    63,    64,    65,    66,    67,
     516,   517,   518,   519,     0,     0,   520,     0,   521,   522,
     523,   524,   525,     0,     0,   114,     0,     0,   526,   527,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,   115,     0,
       0,     0,     0,     0,   528,     0,   529,   280,     0,     0,
     530,     0,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,     0,     0,   139,   140,     0,     0,    58,   141,
       0,   142,   143,   144,     0,   653,     0,   531,     0,   532,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,     0,
     145,   146,   147,    59,   533,   534,   535,     0,   536,     0,
       0,     0,     0,   537,   305,   306,   148,     0,   309,   310,
     311,   312,   313,   314,     0,     0,    60,     0,   538,   539,
     540,     0,     0,   541,   149,     0,   150,     0,     0,     0,
       0,     0,   315,   316,     0,     0,   654,   152,    62,    63,
      64,    65,    66,    67,   516,   517,   518,   519,     0,     0,
     520,     0,   521,   522,   523,   524,   525,     0,     0,   114,
       0,     0,   526,   527,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,   115,     0,     0,     0,     0,     0,   528,     0,
     529,   280,     0,     0,   530,     0,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,     0,     0,   139,   140,
       0,     0,    58,   141,     0,   142,   143,   144,     0,     0,
       0,   531,     0,   532,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,     0,   145,   146,   147,    59,   533,   534,
     535,     0,   536,     0,     0,     0,     0,   537,   305,     0,
     148,     0,   309,   310,   311,   312,   313,   314,     0,     0,
      60,     0,   538,   539,   540,     0,     0,   541,   149,     0,
     150,     0,     0,     0,     0,     0,   315,   316,     0,     0,
     151,   152,    62,    63,    64,    65,    66,    67,   516,   517,
     518,   519,     0,     0,   520,     0,   521,   522,   523,   524,
     525,     0,     0,   114,     0,     0,   526,   527,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   115,     0,     0,     0,
       0,     0,   528,     0,   529,     0,     0,     0,   530,     0,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
       0,     0,   139,   140,     0,     0,    58,   141,     0,   142,
     143,   144,     0,     0,     0,   531,     0,   532,     0,     0,
     685,   686,   687,     0,     0,     0,   688,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   689,   690,
       0,     0,     0,     0,     0,     0,     0,     0,   145,   146,
     147,    59,   533,   534,   535,     0,   536,     0,     0,     0,
       0,   537,     0,     0,   148,   111,     0,   112,     0,     0,
     113,     0,     0,     0,    60,     0,   538,   539,   540,   114,
       0,   541,   149,     0,   150,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   654,   152,    62,    63,    64,    65,
      66,    67,   115,   835,   836,     0,     0,     0,   116,     0,
     117,     0,     0,     0,   118,     0,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,     0,     0,   139,   140,
       0,     0,    58,   141,     0,   142,   143,   144,   691,   366,
       0,     0,     0,   692,     0,     0,   685,   686,   687,     0,
     693,   694,   688,   695,     0,   696,     0,   697,     0,     0,
       0,     0,     0,     0,   689,   690,     0,   698,     0,  1034,
       0,     0,     0,     0,   145,   146,   147,    59,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     148,   111,     0,   112,   919,   920,   113,     0,     0,     0,
      60,     0,     0,     0,     0,   114,   216,     0,   149,     0,
     150,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     151,   152,    62,    63,    64,    65,    66,    67,   115,     0,
       0,     0,     0,     0,   116,     0,   117,     0,     0,     0,
     118,     0,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,     0,     0,   139,   140,     0,     0,    58,   141,
       0,   142,   143,   144,   691,   366,     0,     0,     0,   692,
       0,     0,     0,     0,     0,     0,   693,   694,     0,   695,
       0,   696,     0,   697,     0,     0,   685,   686,   687,     0,
     763,     0,   688,   698,     0,     0,     0,     0,     0,     0,
     145,   146,   147,    59,   689,   690,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   148,   111,     0,   112,
       0,     0,   113,     0,     0,     0,    60,     0,     0,     0,
       0,   114,     0,     0,   149,     0,   150,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   151,   152,    62,    63,
      64,    65,    66,    67,   115,     0,     0,   658,     0,     0,
     116,     0,   117,     0,     0,     0,   118,     0,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   138,     0,     0,
     139,   140,     0,     0,    58,   141,     0,   142,   143,   144,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   691,   366,     0,     0,     0,   692,
       0,     0,     0,     0,     0,     0,   693,   694,     0,   695,
       0,   696,     0,   697,     0,     0,   145,   146,   147,    59,
       0,     0,     0,   698,     0,     0,     0,     0,     0,     0,
       0,     0,   148,   111,     0,   112,     0,   670,   113,     0,
       0,     0,    60,     0,     0,     0,     0,   114,     0,     0,
     149,     0,   150,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   151,   152,    62,    63,    64,    65,    66,    67,
     115,     0,     0,     0,     0,     0,   116,     0,   117,     0,
       0,     0,   118,     0,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,     0,     0,   139,   140,     0,     0,
      58,   141,     0,   142,   143,   144,     0,     0,     0,     0,
       0,     0,     0,     0,   685,   686,   687,     0,   916,     0,
     688,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   689,   690,     0,     0,     0,     0,     0,     0,
       0,     0,   145,   146,   147,    59,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   148,   111,
       0,   112,     0,     0,   113,     0,     0,     0,    60,     0,
       0,     0,     0,   114,     0,     0,   149,     0,   150,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   151,   152,
      62,    63,    64,    65,    66,    67,   115,     0,     0,     0,
       0,     0,   116,     0,   117,     0,     0,     0,   118,     0,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
       0,     0,   139,   140,     0,     0,    58,   141,   809,   142,
     143,   144,   691,   366,     0,     0,     0,   692,     0,     0,
       0,     0,     0,     0,   693,   694,     0,   695,     0,   696,
       0,   697,     0,     0,   685,   686,   687,     0,   959,     0,
     688,   698,     0,     0,     0,     0,     0,     0,   145,   146,
     147,    59,   689,   690,   359,   360,   361,     0,   748,     0,
     362,     0,     0,     0,   148,   111,     0,   112,     0,     0,
     113,     0,   363,   364,    60,     0,     0,     0,     0,   114,
       0,     0,   149,     0,   150,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   151,   152,    62,    63,    64,    65,
      66,    67,   115,     0,     0,     0,     0,     0,   116,     0,
     117,     0,     0,     0,   118,     0,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,     0,     0,   139,   140,
       0,     0,    58,   141,     0,   142,   143,   144,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,   691,   366,     0,     0,     0,   692,     0,     0,
     685,   686,   687,   280,   693,   694,   688,   695,     0,   696,
       0,   697,   365,   366,   145,   146,   147,    59,   689,   690,
       0,   698,     0,     0,   367,   368,     0,   369,     0,   370,
     148,   371,   281,   282,     0,     0,   359,   360,   361,     0,
      60,   372,   362,     0,     0,     0,     0,     0,   149,     0,
     150,     0,     0,     0,   363,   364,   283,   284,     0,     0,
     151,   152,    62,    63,    64,    65,    66,    67,     0,     0,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
       0,     0,     0,     0,     0,     0,   277,     0,     0,     0,
       0,   278,     0,   279,     0,     0,     0,     0,   315,   316,
       0,     0,     0,     0,     0,     0,     0,     0,   691,   366,
       0,   280,     0,   692,     0,     0,     0,     0,     0,   990,
     693,   694,     0,   695,     0,   696,     0,   697,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   698,     0,     0,
     281,   282,     0,     0,   365,   366,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   367,   368,     0,   369,
       0,   370,     0,   371,   283,   284,     0,     0,     0,     0,
       0,     0,     0,   372,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,   280,     0,     0,     0,  1007,     0,     0,
    1008,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   281,   282,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   279,     0,     0,     0,     0,   283,   284,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     277,     0,     0,     0,     0,   278,     0,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   315,   316,
       0,     0,   283,   284,     0,   280,     0,     0,     0,   791,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   281,   282,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   283,   284,
       0,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,     0,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   309,   310,   311,   312,
     313,   314,   277,     0,     0,     0,     0,   278,     0,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,     0,     0,     0,     0,     0,   280,     0,     0,
       0,   933,     0,     0,     0,     0,     0,     0,     0,     0,
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
       0,     0,     0,   934,     0,     0,     0,     0,     0,     0,
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
       0,   280,     0,     0,     0,   978,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,     0,   277,
       0,     0,     0,     0,   278,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   315,   316,   884,   885,
       0,     0,     0,     0,   280,     0,     0,  1036,     0,     0,
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
     316,   708,     0,     0,     0,   886,   280,     0,     0,     0,
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
       0,   315,   316,     0,     0,     0,     0,   709,   280,     0,
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
       0,     0,     0,   315,   316,     0,     0,   714,     0,     0,
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
       0,     0,   703,     0,     0,   315,   316,     0,     0,   890,
       0,     0,   280,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   724,   725,     0,
       0,   281,   282,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   283,   284,     0,     0,     0,
       0,     0,     0,     0,     0,   704,     0,     0,     0,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,     0,
       0,     0,     0,     0,   277,     0,   705,   283,   284,   278,
     395,   279,     0,     0,     0,     0,     0,   315,   316,   703,
       0,   285,   286,   287,   288,   289,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,     0,     0,     0,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,   704,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,     0,   277,     0,     0,
       0,     0,   278,   705,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,   400,     0,     0,     0,
       0,     0,   280,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   281,   282,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   401,   402,     0,     0,   283,   284,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,   277,
       0,     0,     0,     0,   278,     0,   279,     0,     0,     0,
       0,     0,     0,     0,   703,     0,     0,   315,   316,     0,
       0,     0,     0,     0,   280,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   281,   282,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   283,   284,     0,
       0,     0,     0,     0,     0,     0,     0,   704,     0,     0,
       0,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,     0,   277,     0,     0,     0,     0,   278,   705,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   315,
     316,     0,     0,     0,     0,     0,     0,   280,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   724,   725,     0,     0,   281,   282,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     283,   284,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   309,   310,
     311,   312,   313,   314,   277,     0,     0,     0,     0,   278,
       0,   279,     0,     0,     0,     0,     0,     0,     0,   775,
       0,     0,   315,   316,     0,     0,     0,     0,     0,   280,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   776,     0,     0,     0,     0,   281,   282,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   283,   284,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     309,   310,   311,   312,   313,   314,     0,   277,     0,     0,
       0,     0,   278,     0,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,   887,     0,     0,     0,
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
     277,     0,     0,     0,     0,   278,     0,   279,     0,   962,
     888,     0,     0,     0,     0,     0,     0,   315,   316,   963,
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
     313,   314,     0,   277,     0,     0,     0,     0,   278,     0,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     315,   316,   984,     0,     0,     0,     0,     0,   280,     0,
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
     278,   323,   279,     0,     0,     0,   985,     0,     0,     0,
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
       0,     0,   278,   395,   279,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,   315,   316,   398,
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
       0,   412,     0,     0,     0,     0,   281,   282,     0,     0,
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
     309,   310,   311,   312,   313,   314,     0,   277,     0,     0,
       0,     0,   278,     0,   279,     0,     0,     0,     0,     0,
       0,   413,     0,     0,   315,   316,   431,     0,     0,     0,
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
       0,     0,   511,     0,     0,     0,     0,     0,     0,   315,
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
     279,     0,     0,     0,     0,   598,     0,     0,     0,     0,
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
     278,   610,   279,     0,     0,     0,     0,   601,     0,     0,
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
       0,   612,     0,     0,     0,   283,   284,     0,     0,     0,
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
       0,     0,   710,     0,     0,     0,     0,   713,     0,   315,
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
     312,   313,   314,     0,     0,   277,     0,     0,     0,     0,
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,   315,   316,   721,     0,     0,     0,     0,     0,     0,
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
       0,     0,   402,     0,     0,   283,   284,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   309,   310,   311,   312,   313,   314,   277,
       0,     0,     0,     0,   278,   731,   279,     0,     0,     0,
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
     279,     0,     0,     0,     0,     0,     0,     0,   779,     0,
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
     278,     0,   279,     0,     0,     0,   783,     0,     0,     0,
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
       0,   784,   775,     0,     0,   315,   316,     0,     0,     0,
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
       0,     0,     0,     0,   278,   829,   279,     0,     0,     0,
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
     314,   277,     0,     0,     0,     0,   278,   831,   279,     0,
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
     279,     0,   847,     0,     0,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,   873,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,     0,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   315,   316,   889,     0,     0,
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
     277,     0,     0,     0,     0,   278,     0,   279,     0,   892,
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
       0,     0,     0,     0,     0,     0,     0,     0,   893,     0,
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
       0,     0,   315,   316,   894,     0,     0,     0,     0,     0,
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
       0,     0,   278,     0,   279,     0,   895,     0,     0,     0,
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
       0,   277,     0,     0,     0,     0,   278,     0,   279,     0,
       0,     0,     0,     0,     0,     0,     0,   315,   316,   896,
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
     312,   313,   314,     0,     0,   277,     0,     0,     0,     0,
     278,     0,   279,     0,     0,     0,     0,     0,     0,     0,
       0,   315,   316,   897,     0,     0,     0,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,   898,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,   922,     0,   900,   315,
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
       0,   315,   316,     0,     0,     0,     0,     0,   280,   932,
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
     278,   957,   279,     0,     0,     0,     0,     0,     0,     0,
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
       0,     0,   278,     0,   279,     0,   958,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,   315,   316,   982,
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
     983,     0,     0,     0,     0,     0,     0,     0,     0,   315,
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
     278,     0,   279,     0,     0,     0,     0,     0,     0,   986,
       0,     0,     0,   315,   316,     0,     0,     0,     0,     0,
     280,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   999,     0,     0,     0,     0,   281,
     282,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   283,   284,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,   277,     0,     0,
       0,     0,   278,     0,   279,     0,  1021,     0,     0,     0,
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
       0,     0,     0,     0,     0,     0,     0,     0,  1023,     0,
       0,     0,     0,   281,   282,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   283,   284,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   309,   310,   311,   312,   313,
     314,   277,     0,     0,     0,     0,   278,     0,   279,     0,
    1025,     0,     0,     0,     0,     0,     0,     0,     0,   315,
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
       0,   315,   316,  1028,     0,     0,     0,     0,     0,   280,
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
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,   277,     0,
       0,     0,     0,   278,     0,   279,     0,     0,     0,  1029,
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
     277,     0,     0,     0,     0,   278,     0,   279,     0,  1041,
    1030,     0,     0,     0,     0,     0,     0,     0,   315,   316,
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
     311,   312,   313,   314,   277,     0,     0,     0,     0,     0,
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
       0,     0,     0,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   315,   316,     0,     0,     0,     0,
       0,   280,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     281,   282,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   283,   284,   277,     0,     0,     0,
       0,     0,     0,   279,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   280,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,     0,     0,
       0,     0,     0,     0,     0,     0,   277,     0,     0,     0,
     281,   282,     0,   279,     0,     0,   315,   316,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,     0,     0,   283,   284,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     281,   282,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,     0,     0,
       0,     0,     0,   277,   283,   284,     0,     0,     0,     0,
     279,     0,     0,     0,     0,     0,   315,   316,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   280,   296,
       0,     0,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   309,   310,   311,   312,   313,   314,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   281,   282,     0,
     279,     0,     0,     0,     0,     0,   315,   316,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,     0,     0,   281,   282,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,     0,     0,     0,     0,     0,
       0,   283,   284,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316,   285,   286,   287,   288,   289,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   315,   316
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
        50,   483,    79,   195,   147,    55,   141,   332,   653,   669,
     187,   335,   146,     2,    33,    14,    15,    38,    18,    27,
     164,    27,    31,   115,   166,   114,   170,   171,    31,   181,
     182,   659,   184,   185,    33,    45,    13,   359,   360,   361,
      13,   363,   364,    12,    49,    59,   368,   369,    13,   141,
       1,    21,   374,     4,     5,   147,   146,    13,    47,   148,
      49,   151,   112,   113,   114,    33,    47,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   189,
      79,    33,    18,    13,    45,   116,    12,    12,   148,    17,
     283,   180,    13,    54,   118,    17,   183,   180,    90,     3,
       4,     5,    13,     7,    27,     0,   118,   180,    59,    30,
      21,    12,   104,   173,     9,   175,    77,    17,    46,    19,
      20,    44,    13,   316,    46,   149,   186,    13,   146,   149,
     146,   146,    13,    34,   143,    35,   146,   149,   147,    39,
      40,    41,    42,    43,    48,   154,   155,    47,   157,    30,
     159,   180,   161,    17,   173,   489,   491,   256,   257,   146,
     173,     0,   171,   146,   263,   143,   146,   354,   151,   147,
     149,   146,   504,    18,   506,   507,   508,   509,   510,   278,
     146,   159,    30,   161,   183,   176,   177,   178,   179,   180,
      18,   143,    18,   171,    27,   146,   256,   257,    30,   837,
      12,   261,   857,   263,   264,   875,    17,   159,    19,   161,
     146,    18,    18,   315,   144,   275,   146,   277,   278,   171,
     280,   281,   282,   149,   149,   146,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   309,
     310,   311,   312,   313,   314,   146,   400,   409,   357,   413,
     146,   405,   315,   146,    59,   146,   149,    18,   906,    18,
     174,   175,   146,   418,   376,    18,    18,    17,   770,    19,
     180,   146,   180,    17,    13,    19,   186,   431,   188,   189,
     190,   191,   192,   193,   194,   195,   398,   357,   146,   173,
      27,   146,   362,    33,    33,   365,   366,   367,   173,   180,
     370,   371,   372,   146,   146,    33,   418,    44,   146,    98,
     146,    17,   424,   477,   188,   189,   190,   191,   192,   193,
     194,   195,    18,    56,    57,   146,    19,   397,   149,   146,
     146,   401,   402,   180,   404,   398,   406,    33,   408,   511,
     410,   146,   412,    19,   516,   517,   518,   417,   146,    13,
     420,   149,    33,   423,   526,   527,   426,   146,   428,   429,
     149,   573,   333,    62,    63,   102,   375,   709,   710,    33,
     411,  1019,   714,   485,   138,   139,   140,   540,    14,    15,
      16,    17,    88,    89,    20,   146,    92,   146,    14,    15,
      16,    17,    13,   146,   146,    38,    32,    33,    91,   149,
       0,    14,    15,    16,    17,   149,    32,    33,   750,     9,
      33,   481,    33,    17,   118,    91,    13,   487,   488,    32,
      33,    13,   519,    17,    12,    19,   496,    13,   540,   538,
     118,   137,   502,   553,   554,   555,    33,   557,   558,   132,
     133,    33,   146,   563,   564,   149,    12,    33,   568,   519,
     520,   615,   954,   180,   160,   525,     0,   133,   146,   529,
     530,   149,   532,    67,    17,     9,   536,   537,   538,   539,
      13,   541,   115,   116,   180,    13,   182,   183,   184,   185,
     186,   187,    27,   180,    88,    89,    31,   528,    92,    13,
      33,    21,    37,    13,   503,    33,   505,    27,   141,    44,
      17,    31,   145,   146,   147,   847,   142,    37,   169,    33,
     519,   172,    13,    33,    44,   635,   142,    13,   638,   639,
     640,   164,   642,   643,   533,   534,   535,   170,   171,   142,
      17,   601,    33,   137,    17,    13,   172,    33,    33,   144,
     146,   146,   612,   149,   180,    17,   172,    15,    17,    98,
     892,   621,    13,    17,   180,    33,   160,   102,   180,   172,
      27,   165,   180,   727,    31,    33,    13,   180,   680,   143,
      37,   641,    33,   147,   738,   645,   180,    44,   182,   183,
     184,   185,   186,   187,   180,   159,    33,   161,    13,    13,
      13,   146,    59,    65,   149,    67,     3,   171,    36,   669,
       7,     8,    37,    10,    11,   180,    14,    15,    33,    33,
      33,   783,   784,   180,    31,   779,    88,    89,   688,    13,
      92,   691,   692,   693,   149,    33,   696,   697,   698,    17,
      17,    19,    17,   100,   704,    17,    13,    19,   708,   648,
     283,    48,   712,   713,   503,   715,   505,   717,   143,   719,
      92,   180,   722,   180,   724,   725,   149,   146,    18,   729,
     769,   632,   157,   733,   159,   137,   161,    27,   172,   739,
     173,    31,   315,   316,   180,   143,   171,    37,    65,   147,
      67,   180,   180,   753,    44,    50,   154,   155,   160,   157,
     180,   159,    33,   161,   814,   815,   180,    33,   818,   769,
      12,    88,    89,   171,   774,    92,   776,   777,   180,   143,
     182,   183,   184,   185,   186,   187,    12,    18,   788,   789,
     790,   176,   177,   178,   179,   159,    27,   161,   848,   180,
      31,    17,    46,   376,   118,   143,    37,   171,    15,   809,
     894,   173,    18,    44,   149,   149,   154,   155,   757,   157,
     137,   159,   180,   161,    27,   398,    33,   400,    31,   923,
      12,    12,   405,   171,    37,    49,    21,    59,   411,    12,
     413,    44,    20,   160,    47,   418,   846,    17,    12,   849,
      17,   424,    20,   118,   427,   855,    27,   180,   431,    55,
     910,   146,   862,   180,    17,   182,   183,   184,   185,   186,
     187,    20,   143,   873,    51,   875,   147,    27,     9,   879,
     757,   142,   354,   702,   884,   885,   157,   887,   159,   889,
     161,    38,   942,   893,    49,    12,    31,    45,   898,   899,
     171,    48,    17,   195,   477,    27,   386,   907,   908,    31,
      27,    77,   485,    27,    31,    37,    38,    31,    38,   919,
      37,   960,    44,    37,    38,   925,    54,    44,    33,   905,
      44,   627,   932,   745,    81,    82,   143,   767,    85,   843,
      87,  1031,    89,    90,    91,    92,    93,   154,   155,   528,
     157,   632,   159,   411,   161,   528,    12,   945,   727,   404,
     960,   539,   923,   963,   171,    17,   966,   540,   115,   116,
      -1,    27,    38,    -1,   974,    31,    27,    92,   427,    -1,
      31,    37,   982,    -1,   984,    -1,    37,   987,    44,    -1,
      -1,   991,    -1,    44,   141,    -1,    -1,    -1,   145,   146,
     147,   176,   177,   178,   179,   180,    -1,    -1,  1008,    -1,
      -1,    38,    -1,    -1,    -1,    -1,    -1,   164,    -1,   166,
      -1,    -1,   137,   170,   171,    -1,    -1,   174,  1028,   176,
      -1,    17,    -1,    -1,    -1,    33,    88,    89,    14,    15,
      92,    -1,   615,    -1,    20,   160,    -1,    -1,   195,   115,
     116,   198,   199,    -1,    -1,    -1,    32,    33,    -1,    -1,
     207,   208,   209,   210,    -1,   180,    -1,   182,   183,   184,
     185,   186,   187,    -1,    -1,   141,    -1,    -1,    -1,   145,
     146,   147,    -1,    33,    -1,   137,    -1,    -1,   115,   116,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   164,    -1,
      -1,    -1,    88,    89,   170,   171,    92,   680,   160,    -1,
      -1,    38,    -1,    -1,   141,    -1,    -1,    -1,   145,   146,
     147,    -1,    -1,    -1,   176,   177,   178,   179,   180,    -1,
     182,   183,   184,   185,   186,   187,   283,   164,    -1,    -1,
      -1,    19,    -1,   170,   171,   143,    -1,    -1,    -1,   147,
      -1,   137,    -1,    -1,   727,    -1,   154,   155,    -1,   157,
      -1,   159,    -1,   161,    -1,   738,   142,   143,   315,   316,
      -1,   147,    -1,   171,   160,    -1,    -1,    -1,   154,   155,
      -1,   157,    -1,   159,    -1,   161,    -1,    -1,   115,   116,
      -1,    -1,    38,   143,   180,   171,   182,   183,   184,   185,
     186,   187,    -1,    -1,   154,   155,   779,   157,    -1,   159,
      -1,   161,    90,    91,   141,    -1,    -1,   283,   145,   146,
     147,   171,    -1,    -1,    -1,    -1,   104,    -1,    -1,   376,
      -1,   378,   379,    -1,    -1,    -1,    -1,   164,    -1,   386,
     387,   388,    -1,   170,   171,    -1,    -1,    17,    -1,   315,
     316,   398,    -1,   400,   132,   133,   283,    -1,   405,    -1,
     407,    -1,   409,    -1,   411,    17,   413,    -1,    -1,   115,
     116,   418,    -1,    -1,   152,   153,    -1,   424,   425,    -1,
     427,    -1,    -1,    -1,   431,    -1,    -1,    -1,   315,   316,
      -1,    -1,    -1,    -1,    -1,   141,    -1,    -1,    -1,   145,
     146,   147,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     376,    19,    -1,    -1,    -1,    67,    -1,    -1,   164,    -1,
      -1,   894,    92,    -1,   170,   171,    -1,    -1,    -1,    -1,
     477,   478,   398,   480,   400,    -1,    88,    89,   485,   405,
      92,    -1,    -1,    -1,    -1,   411,    -1,   413,    -1,   376,
     923,    -1,   418,    -1,    -1,    -1,   283,    -1,   424,    -1,
      -1,   427,    -1,    -1,    -1,   431,    -1,   137,    -1,    -1,
      -1,   398,    -1,   400,    -1,    -1,    -1,    -1,   405,    -1,
      -1,   528,    90,    91,   411,   137,   413,    -1,   315,   316,
     160,   418,    -1,   540,    -1,    -1,   104,   424,    -1,    -1,
     427,    -1,    -1,    -1,   431,    -1,    -1,    -1,   160,    -1,
     180,   477,   182,   183,   184,   185,   186,   187,    -1,   485,
     128,   129,   130,   131,   132,   133,   573,    -1,   180,    -1,
     182,   183,   184,   185,   186,   187,    -1,   283,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,   376,
     477,   598,    -1,    -1,    -1,    -1,    -1,    -1,   485,    -1,
      -1,    -1,   528,    -1,    -1,    -1,    -1,    -1,   615,   315,
     316,   398,    -1,   400,   540,    -1,    -1,    -1,   405,    -1,
      -1,    -1,    -1,    -1,   411,    -1,   413,    -1,    -1,    -1,
      -1,   418,    -1,    17,    -1,    19,    20,   424,    -1,    -1,
     427,   528,     3,    -1,   431,     6,     7,     8,     9,    10,
      11,    35,    13,   540,    -1,    39,    40,    41,    42,    43,
      -1,    -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,
     376,    -1,    -1,   680,    -1,    -1,    -1,   353,     3,    -1,
      -1,     6,     7,     8,     9,    10,    11,    48,    13,   615,
     477,    -1,   398,    -1,   400,    -1,    -1,    -1,   485,   405,
      -1,    -1,    -1,    -1,    -1,   411,    -1,   413,    -1,    -1,
      -1,    -1,   418,    -1,    -1,    -1,    -1,    -1,   424,    48,
     727,   427,    -1,    48,    -1,   431,    -1,    -1,   615,   736,
      -1,   738,    -1,   740,    -1,    90,    91,   744,    -1,   746,
      -1,   528,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
      -1,    -1,    81,   540,   680,    -1,    85,    -1,    87,    -1,
      89,    90,    91,    92,    93,    -1,    -1,    -1,    -1,   124,
      -1,   477,   779,   128,   129,   130,   131,   132,   133,   485,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   173,
      -1,    -1,    -1,   680,    -1,    -1,   180,   152,   153,    -1,
      -1,   727,   186,    -1,   188,   189,   190,   191,   192,   193,
     194,   195,   738,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   528,    -1,    -1,    -1,    -1,    -1,   615,    -1,
      -1,    -1,    -1,    -1,   540,    -1,    -1,   166,    14,    15,
     727,    -1,    -1,   519,    20,   174,    -1,   176,    -1,    -1,
      -1,   738,    -1,   779,    -1,    -1,    32,    33,    14,    15,
      16,    -1,    -1,    -1,    20,    -1,    -1,    -1,    -1,   198,
     199,    -1,    -1,   549,    -1,    -1,    32,    33,   207,   208,
     209,   210,    -1,    -1,    -1,    -1,    -1,   894,    -1,    -1,
      -1,    -1,   779,   680,    -1,    -1,   903,   904,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   615,
     917,    -1,    -1,    -1,    -1,    -1,   923,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    14,    15,    16,
      -1,    -1,    -1,    20,    -1,    -1,    -1,    -1,    -1,    -1,
     727,    -1,    -1,    -1,    -1,    32,    33,    17,    -1,    19,
      20,   738,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    35,   142,   143,   894,    39,
      40,    41,    42,    43,   680,    -1,    -1,    -1,   154,   155,
      -1,   157,    -1,   159,    -1,   161,   142,   143,    -1,    -1,
      -1,   147,   779,    -1,    -1,   171,    -1,   923,   154,   155,
      -1,   157,    -1,   159,    -1,   161,    -1,   894,    -1,   685,
     686,   687,    -1,   689,   690,   171,    -1,   173,   694,   695,
      17,   727,    19,    20,    -1,   701,    -1,   703,    -1,   705,
      -1,    -1,   738,    -1,    -1,    -1,   923,    -1,    35,    -1,
      -1,    -1,    39,    40,    41,    42,    43,    -1,    -1,   378,
     379,    -1,    -1,    -1,    -1,   142,   143,    -1,   387,   388,
     147,    -1,    -1,    -1,    -1,    -1,    -1,   154,   155,    -1,
     157,    -1,   159,   779,   161,    -1,    -1,    -1,   407,    -1,
     409,    -1,   758,   759,   171,    -1,   173,    -1,   764,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   425,   773,   774,   775,
     776,    -1,    -1,    -1,    -1,    -1,    -1,   894,    -1,    -1,
     180,    -1,    -1,    -1,    -1,   791,   186,    -1,   188,   189,
     190,   191,   192,   193,   194,   195,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   923,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   478,
      -1,   480,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   851,    -1,    -1,    -1,    -1,
     856,    -1,    -1,   180,    -1,    -1,    -1,    -1,   894,   186,
      -1,   188,   189,   190,   191,   192,   193,   194,   195,    -1,
      -1,    17,   878,    19,    20,    -1,    -1,    -1,    -1,    -1,
     886,    -1,   888,    -1,   890,    -1,    -1,   923,    -1,    35,
      -1,    -1,    -1,    39,    40,    41,    42,    43,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   920,    -1,   922,    -1,    -1,   925,
      -1,    -1,   928,    17,    -1,    19,    20,   933,   934,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   598,
      17,    35,    19,    20,    -1,    39,    40,    41,    42,    43,
      -1,    -1,   958,    -1,    -1,    -1,   962,    -1,    35,    -1,
      -1,   967,    39,    40,    41,    42,    43,    -1,    -1,    -1,
      -1,   977,   978,    -1,    -1,    -1,    -1,   983,    -1,   985,
     986,    -1,    -1,    -1,    -1,    -1,    -1,   993,    -1,    -1,
      -1,    -1,    -1,   999,    -1,    -1,  1002,  1003,    -1,    -1,
      -1,  1007,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,  1021,    -1,  1023,    -1,  1025,
      -1,    -1,    -1,  1029,    -1,    -1,    -1,    -1,    -1,    -1,
    1036,    -1,    -1,    -1,   180,  1041,    -1,    -1,    -1,    -1,
     186,    -1,   188,   189,   190,   191,   192,   193,   194,   195,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   736,    -1,    -1,
      -1,   740,    -1,    -1,    -1,   744,   180,   746,    -1,    -1,
      -1,    -1,   186,    -1,   188,   189,   190,   191,   192,   193,
     194,   195,    -1,   180,    -1,    -1,    -1,    -1,    -1,   186,
      -1,   188,   189,   190,   191,   192,   193,   194,   195,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    14,    15,    16,    17,
      -1,    -1,    20,    -1,    22,    23,    24,    25,    26,    -1,
      -1,    29,    -1,    -1,    32,    33,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    52,    53,    54,    -1,    -1,    -1,
      58,    -1,    60,    -1,    -1,    -1,    64,    -1,    66,    67,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    -1,    -1,
      88,    89,    -1,    -1,    92,    93,    -1,    95,    96,    97,
      -1,    -1,    -1,   101,    -1,   103,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   903,   904,   134,   135,   136,   137,
     138,   139,   140,    -1,   142,    -1,    -1,    -1,   917,   147,
      -1,    -1,   150,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   160,    -1,   162,   163,   164,    -1,    -1,   167,
     168,    -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   180,   181,   182,   183,   184,   185,   186,   187,
      14,    15,    16,    17,    -1,    -1,    20,    -1,    22,    23,
      24,    25,    26,    -1,    -1,    29,    -1,    -1,    32,    33,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    52,    -1,
      -1,    -1,    -1,    -1,    58,    -1,    60,    37,    -1,    -1,
      64,    -1,    66,    67,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,
      -1,    95,    96,    97,    -1,    99,    -1,   101,    -1,   103,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,    -1,
     134,   135,   136,   137,   138,   139,   140,    -1,   142,    -1,
      -1,    -1,    -1,   147,   124,   125,   150,    -1,   128,   129,
     130,   131,   132,   133,    -1,    -1,   160,    -1,   162,   163,
     164,    -1,    -1,   167,   168,    -1,   170,    -1,    -1,    -1,
      -1,    -1,   152,   153,    -1,    -1,   180,   181,   182,   183,
     184,   185,   186,   187,    14,    15,    16,    17,    -1,    -1,
      20,    -1,    22,    23,    24,    25,    26,    -1,    -1,    29,
      -1,    -1,    32,    33,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    52,    -1,    -1,    -1,    -1,    -1,    58,    -1,
      60,    37,    -1,    -1,    64,    -1,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    -1,    -1,    88,    89,
      -1,    -1,    92,    93,    -1,    95,    96,    97,    -1,    -1,
      -1,   101,    -1,   103,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,    -1,   134,   135,   136,   137,   138,   139,
     140,    -1,   142,    -1,    -1,    -1,    -1,   147,   124,    -1,
     150,    -1,   128,   129,   130,   131,   132,   133,    -1,    -1,
     160,    -1,   162,   163,   164,    -1,    -1,   167,   168,    -1,
     170,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
     180,   181,   182,   183,   184,   185,   186,   187,    14,    15,
      16,    17,    -1,    -1,    20,    -1,    22,    23,    24,    25,
      26,    -1,    -1,    29,    -1,    -1,    32,    33,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    52,    -1,    -1,    -1,
      -1,    -1,    58,    -1,    60,    -1,    -1,    -1,    64,    -1,
      66,    67,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,    85,
      -1,    -1,    88,    89,    -1,    -1,    92,    93,    -1,    95,
      96,    97,    -1,    -1,    -1,   101,    -1,   103,    -1,    -1,
      14,    15,    16,    -1,    -1,    -1,    20,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    32,    33,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   134,   135,
     136,   137,   138,   139,   140,    -1,   142,    -1,    -1,    -1,
      -1,   147,    -1,    -1,   150,    15,    -1,    17,    -1,    -1,
      20,    -1,    -1,    -1,   160,    -1,   162,   163,   164,    29,
      -1,   167,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,   185,
     186,   187,    52,    53,    54,    -1,    -1,    -1,    58,    -1,
      60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    -1,    -1,    88,    89,
      -1,    -1,    92,    93,    -1,    95,    96,    97,   142,   143,
      -1,    -1,    -1,   147,    -1,    -1,    14,    15,    16,    -1,
     154,   155,    20,   157,    -1,   159,    -1,   161,    -1,    -1,
      -1,    -1,    -1,    -1,    32,    33,    -1,   171,    -1,   173,
      -1,    -1,    -1,    -1,   134,   135,   136,   137,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     150,    15,    -1,    17,    62,    63,    20,    -1,    -1,    -1,
     160,    -1,    -1,    -1,    -1,    29,    30,    -1,   168,    -1,
     170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     180,   181,   182,   183,   184,   185,   186,   187,    52,    -1,
      -1,    -1,    -1,    -1,    58,    -1,    60,    -1,    -1,    -1,
      64,    -1,    66,    67,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      84,    85,    -1,    -1,    88,    89,    -1,    -1,    92,    93,
      -1,    95,    96,    97,   142,   143,    -1,    -1,    -1,   147,
      -1,    -1,    -1,    -1,    -1,    -1,   154,   155,    -1,   157,
      -1,   159,    -1,   161,    -1,    -1,    14,    15,    16,    -1,
      18,    -1,    20,   171,    -1,    -1,    -1,    -1,    -1,    -1,
     134,   135,   136,   137,    32,    33,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   150,    15,    -1,    17,
      -1,    -1,    20,    -1,    -1,    -1,   160,    -1,    -1,    -1,
      -1,    29,    -1,    -1,   168,    -1,   170,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   180,   181,   182,   183,
     184,   185,   186,   187,    52,    -1,    -1,    55,    -1,    -1,
      58,    -1,    60,    -1,    -1,    -1,    64,    -1,    66,    67,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    -1,    -1,
      88,    89,    -1,    -1,    92,    93,    -1,    95,    96,    97,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   142,   143,    -1,    -1,    -1,   147,
      -1,    -1,    -1,    -1,    -1,    -1,   154,   155,    -1,   157,
      -1,   159,    -1,   161,    -1,    -1,   134,   135,   136,   137,
      -1,    -1,    -1,   171,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   150,    15,    -1,    17,    -1,    19,    20,    -1,
      -1,    -1,   160,    -1,    -1,    -1,    -1,    29,    -1,    -1,
     168,    -1,   170,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   180,   181,   182,   183,   184,   185,   186,   187,
      52,    -1,    -1,    -1,    -1,    -1,    58,    -1,    60,    -1,
      -1,    -1,    64,    -1,    66,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    -1,    -1,    88,    89,    -1,    -1,
      92,    93,    -1,    95,    96,    97,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    14,    15,    16,    -1,    18,    -1,
      20,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    32,    33,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   134,   135,   136,   137,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   150,    15,
      -1,    17,    -1,    -1,    20,    -1,    -1,    -1,   160,    -1,
      -1,    -1,    -1,    29,    -1,    -1,   168,    -1,   170,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   180,   181,
     182,   183,   184,   185,   186,   187,    52,    -1,    -1,    -1,
      -1,    -1,    58,    -1,    60,    -1,    -1,    -1,    64,    -1,
      66,    67,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,    85,
      -1,    -1,    88,    89,    -1,    -1,    92,    93,    94,    95,
      96,    97,   142,   143,    -1,    -1,    -1,   147,    -1,    -1,
      -1,    -1,    -1,    -1,   154,   155,    -1,   157,    -1,   159,
      -1,   161,    -1,    -1,    14,    15,    16,    -1,    18,    -1,
      20,   171,    -1,    -1,    -1,    -1,    -1,    -1,   134,   135,
     136,   137,    32,    33,    14,    15,    16,    -1,    18,    -1,
      20,    -1,    -1,    -1,   150,    15,    -1,    17,    -1,    -1,
      20,    -1,    32,    33,   160,    -1,    -1,    -1,    -1,    29,
      -1,    -1,   168,    -1,   170,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,   185,
     186,   187,    52,    -1,    -1,    -1,    -1,    -1,    58,    -1,
      60,    -1,    -1,    -1,    64,    -1,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    -1,    -1,    88,    89,
      -1,    -1,    92,    93,    -1,    95,    96,    97,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,   142,   143,    -1,    -1,    -1,   147,    -1,    -1,
      14,    15,    16,    37,   154,   155,    20,   157,    -1,   159,
      -1,   161,   142,   143,   134,   135,   136,   137,    32,    33,
      -1,   171,    -1,    -1,   154,   155,    -1,   157,    -1,   159,
     150,   161,    66,    67,    -1,    -1,    14,    15,    16,    -1,
     160,   171,    20,    -1,    -1,    -1,    -1,    -1,   168,    -1,
     170,    -1,    -1,    -1,    32,    33,    90,    91,    -1,    -1,
     180,   181,   182,   183,   184,   185,   186,   187,    -1,    -1,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      -1,    -1,    -1,    -1,    -1,    -1,    12,    -1,    -1,    -1,
      -1,    17,    -1,    19,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   142,   143,
      -1,    37,    -1,   147,    -1,    -1,    -1,    -1,    -1,   173,
     154,   155,    -1,   157,    -1,   159,    -1,   161,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   171,    -1,    -1,
      66,    67,    -1,    -1,   142,   143,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   154,   155,    -1,   157,
      -1,   159,    -1,   161,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   171,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    12,    -1,
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    37,    -1,    -1,    -1,   163,    -1,    -1,
     166,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    19,    -1,    -1,    -1,    -1,    90,    91,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
      -1,    -1,    90,    91,    -1,    37,    -1,    -1,    -1,   163,
      -1,    -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,
     108,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    66,    67,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,
      -1,    -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,
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
     126,   127,   128,   129,   130,   131,   132,   133,    -1,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    31,    32,
      -1,    -1,    -1,    -1,    37,    -1,    -1,   163,    -1,    -1,
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
     153,    32,    -1,    -1,    -1,   158,    37,    -1,    -1,    -1,
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
      -1,   152,   153,    -1,    -1,    -1,    -1,   158,    37,    -1,
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
      -1,    -1,    -1,   152,   153,    -1,    -1,   156,    -1,    -1,
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
      -1,    -1,    27,    -1,    -1,   152,   153,    -1,    -1,   156,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    62,    63,    -1,
      -1,    66,    67,    -1,    -1,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    37,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   100,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      -1,    -1,    -1,    -1,    12,    -1,   141,    90,    91,    17,
      18,    19,    -1,    -1,    -1,    -1,    -1,   152,   153,    27,
      -1,   104,   105,   106,   107,   108,    -1,    -1,    -1,    37,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   100,    -1,    -1,    -1,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,    -1,    12,    -1,    -1,
      -1,    -1,    17,   141,    19,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   152,   153,    31,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    86,    87,    -1,    -1,    90,    91,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    27,    -1,    -1,   152,   153,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    67,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    90,    91,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,    -1,    -1,
      -1,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,    -1,    12,    -1,    -1,    -1,    -1,    17,   141,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,
     153,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,
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
      -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,
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
     125,   126,   127,   128,   129,   130,   131,   132,   133,    -1,
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,
     145,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    31,
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
     132,   133,    -1,    12,    -1,    -1,    -1,    -1,    17,    -1,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     152,   153,    31,    -1,    -1,    -1,    -1,    -1,    37,    -1,
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
      17,    18,    19,    -1,    -1,    -1,   145,    -1,    -1,    -1,
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
      -1,    61,    -1,    -1,    -1,    -1,    66,    67,    -1,    -1,
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
     128,   129,   130,   131,   132,   133,    -1,    12,    -1,    -1,
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,   149,    -1,    -1,   152,   153,    31,    -1,    -1,    -1,
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
      19,    -1,    -1,    -1,    -1,   146,    -1,    -1,    -1,    -1,
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
      17,    18,    19,    -1,    -1,    -1,    -1,   146,    -1,    -1,
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
      -1,    86,    -1,    -1,    -1,    90,    91,    -1,    -1,    -1,
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
      -1,    -1,   145,    -1,    -1,    -1,    -1,    28,    -1,   152,
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
     131,   132,   133,    -1,    -1,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    30,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    87,    -1,    -1,    90,    91,    -1,    -1,    -1,
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
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   149,    -1,
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
      -1,    -1,    17,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,   148,    27,    -1,    -1,   152,   153,    -1,    -1,    -1,
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
      -1,    -1,    -1,   152,   153,    -1,    -1,    -1,    -1,    -1,
      37,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      67,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   100,    -1,    -1,    -1,   104,   105,   106,
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
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,
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
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    28,    -1,
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
     131,   132,   133,    -1,    -1,    12,    -1,    -1,    -1,    -1,
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   152,   153,    30,    -1,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    61,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,   151,   152,
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
      -1,   152,   153,    -1,    -1,    -1,    -1,    -1,    37,    38,
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
     125,   126,   127,   128,   129,   130,   131,   132,   133,    12,
      -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,    32,
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
      17,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,   148,
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
      -1,    -1,    -1,    17,    -1,    19,    -1,    -1,    -1,   145,
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
      12,    -1,    -1,    -1,    -1,    17,    -1,    19,    -1,    21,
     144,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   152,   153,
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
     130,   131,   132,   133,    12,    -1,    -1,    -1,    -1,    -1,
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
      -1,    -1,    -1,    -1,    90,    91,    12,    -1,    -1,    -1,
      -1,    -1,    -1,    19,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,    37,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    12,    -1,    -1,    -1,
      66,    67,    -1,    19,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    37,    -1,    -1,    90,    91,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
      66,    67,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    -1,    -1,
      -1,    -1,    -1,    12,    90,    91,    -1,    -1,    -1,    -1,
      19,    -1,    -1,    -1,    -1,    -1,   152,   153,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,    37,   115,
      -1,    -1,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    66,    67,    -1,
      19,    -1,    -1,    -1,    -1,    -1,   152,   153,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   104,   105,   106,   107,   108,
     109,   110,   111,   112,   113,    -1,    -1,    66,    67,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,
      -1,    90,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153,   104,   105,   106,   107,   108,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   152,   153
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
      64,   101,   103,   138,   139,   140,   142,   147,   162,   163,
     164,   167,   225,   227,   228,   292,   314,   315,   317,    13,
     221,   291,   180,   217,   217,   217,   292,   217,   217,   292,
     230,   292,   292,   217,   217,   292,   292,   292,   217,   235,
     321,   246,   246,   252,   246,   246,   292,   321,   327,   329,
     292,   292,   292,   309,   329,   292,   246,   292,   292,   300,
     292,   304,   305,   323,    18,    18,   292,   328,   146,   292,
      18,   146,   292,   321,   246,   292,   330,   292,   292,   329,
      18,    18,    86,   323,   246,    17,    19,   265,   246,   292,
     180,    50,   274,   321,   292,   292,   240,   180,   278,   292,
     180,   312,    33,   292,   219,   217,   219,   235,   217,   217,
     217,    20,   217,   217,   218,    12,    33,   180,    12,   180,
     218,   218,   218,    99,   180,   225,   234,   292,    55,   283,
     284,   320,   292,   218,   218,   299,   292,   310,   292,    17,
      19,   292,   235,   235,   235,   292,   292,   291,   292,   310,
     165,   180,   321,   326,   292,    14,    15,    16,    20,    32,
      33,   142,   147,   154,   155,   157,   159,   161,   171,   173,
     229,    13,    33,    27,   100,   141,   225,    18,    32,   158,
     145,   144,   146,    28,   156,    46,   173,    13,    21,    13,
      30,    30,    13,    30,    62,    63,   302,   146,   173,    27,
     246,    18,   292,    13,   151,   292,   149,   323,    17,    46,
     149,   292,    18,   173,   149,   267,   149,   290,    18,   292,
      13,   292,   180,    12,   224,   225,   234,    12,    13,    13,
      13,   311,   312,    18,    13,    56,    57,   285,   286,   287,
     271,    21,    13,    13,    59,    27,    61,    31,   173,   149,
     292,   313,   314,   145,   148,    13,   173,   321,    12,    59,
      59,   163,   225,   225,   225,   292,   225,   225,   292,   292,
     292,   225,   225,   292,   292,   292,   225,   228,   225,    94,
     292,   225,   173,   292,   217,   217,   292,   292,   217,   292,
     292,   292,   292,   292,   292,    65,   305,   292,    18,    18,
     292,    18,   246,    18,   323,    53,    54,   266,   292,   246,
      19,   246,   281,   269,   270,   246,    17,    21,   217,    12,
     292,    13,   224,   225,   225,    20,    13,   146,   225,   286,
     291,   274,    20,   225,   225,   292,   225,   225,   292,   292,
     318,   319,   323,   100,    18,    33,   218,   218,    17,   118,
     292,   292,   292,   225,    31,    32,   158,    31,   145,    31,
     156,   292,    21,    28,    31,    21,    30,    30,    61,    27,
     151,    18,   271,   102,   102,   180,   283,    59,   100,   292,
     217,   292,   225,   292,   225,   312,    18,   149,   292,    62,
      63,   316,    27,   146,   173,    27,   314,    13,    13,   225,
     226,   292,    38,   163,   163,   292,   292,   225,   292,   225,
     292,   225,   217,   292,   329,   292,   292,   273,   274,    19,
     246,    19,   246,   265,   271,   292,   292,    18,    21,    18,
      17,   246,    21,    31,   292,   225,    62,    63,   225,    65,
     319,   323,   225,   292,    20,   225,    18,   146,   163,   292,
     225,   225,    32,    21,    31,   145,   148,    13,   144,   302,
     173,    51,   275,    46,   274,   225,   291,   225,   292,    61,
     292,   225,    27,    27,   292,   225,   225,   163,   166,   292,
     225,   292,   225,   225,   292,   292,    53,    54,   225,   282,
      18,    21,   225,    61,   225,    21,   225,   292,    31,   145,
     144,   271,   225,   225,   173,   225,   163,   292,   225,   273,
     225,    21,   225
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
     225,   225,   225,   225,   225,   225,   225,   226,   226,   227,
     227,   228,   228,   228,   229,   229,   229,   230,   230,   231,
     231,   231,   232,   232,   233,   233,   234,   234,   235,   235,
     236,   236,   237,   237,   238,   239,   239,   239,   240,   240,
     241,   241,   241,   241,   241,   241,   242,   242,   242,   243,
     243,   244,   244,   244,   244,   245,   245,   245,   245,   245,
     246,   246,   246,   246,   246,   246,   246,   246,   246,   246,
     246,   246,   246,   246,   247,   247,   247,   248,   248,   248,
     248,   248,   248,   248,   248,   249,   249,   250,   250,   251,
     251,   252,   252,   253,   253,   253,   254,   255,   255,   255,
     256,   256,   257,   258,   258,   258,   258,   259,   259,   259,
     260,   260,   261,   261,   262,   263,   264,   265,   265,   265,
     266,   266,   266,   267,   267,   268,   268,   269,   269,   270,
     270,   271,   271,   272,   273,   273,   274,   275,   275,   276,
     276,   277,   277,   278,   278,   279,   280,   281,   281,   281,
     281,   282,   282,   282,   283,   283,   284,   285,   285,   286,
     286,   287,   287,   288,   288,   288,   289,   289,   290,   290,
     291,   291,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   293,   293,   294,   295,
     296,   296,   296,   297,   298,   299,   299,   300,   300,   301,
     302,   302,   303,   303,   304,   304,   305,   306,   306,   306,
     306,   306,   306,   306,   306,   306,   306,   306,   306,   306,
     306,   306,   306,   306,   306,   306,   306,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     308,   308,   309,   310,   310,   311,   311,   312,   312,   312,
     313,   313,   314,   315,   315,   316,   316,   317,   317,   318,
     318,   319,   320,   321,   321,   322,   322,   322,   322,   323,
     323,   324,   324,   325,   325,   325,   325,   325,   325,   325,
     326,   326,   327,   328,   329,   329,   330,   330,   331,   332,
     333,   333
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
       3,     3,     3,     5,     3,     5,     3,     3,     3,     2,
       6,     7,     3,     7,     3,    11,     7,     9,     5,     3,
       8,     4,     4,     4,     6,     7,     9,     9,     7,     4,
       6,     1,     3,     3,     1,     4,     3,     2,     2,     4,
       1,     6,     6,     7,     8,    10,     4,     1,     3,     1,
       3,     2,     2,     2,     3,     7,     9,     3,     5,     1,
       2,     3,     1,     3,     1,     1,     1,     3,     3,     3,
       1,     2,     1,     2,     3,     1,     1,     1,     1,     2,
       1,     1,     1,     1,     1,     2,     1,     2,     3,     1,
       3,     5,     4,     4,     5,     1,     1,     1,     1,     0,
       3,     1,     1,     5,     3,     3,     3,     2,     2,     2,
       4,     4,     1,     1,     1,     3,     3,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     3,     3,     3,
       3,     1,     2,     1,     3,     3,     4,     1,     2,     3,
       1,     3,     2,     5,     5,     3,     3,     1,     2,     3,
       1,     3,     1,     1,     6,     2,    10,     1,     3,     4,
       1,     1,     1,     1,     3,     3,     5,     0,     1,     3,
       5,     1,     0,     2,     1,     0,     2,     2,     0,     1,
       2,     1,     2,     1,     1,    10,     7,     3,     3,     3,
       3,     1,     1,     1,     1,     0,     2,     1,     2,     2,
       4,     1,     1,     1,     2,     3,     1,     3,     2,     2,
       1,     3,     1,     1,     1,     1,     1,     1,     3,     1,
       1,     2,     3,     3,     3,     2,     4,     6,     3,     3,
       3,     3,     3,     4,     1,     1,     1,     1,     4,     4,
       4,     4,     2,     3,     5,     7,     5,     1,     3,     5,
       7,     1,     3,     5,     7,     4,     4,     4,     4,     5,
       6,     4,     6,     3,     5,     7,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     3,     1,     1,     5,
       2,     5,     5,     9,     1,     3,     3,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       1,     3,     3,     3,     5,     1,     3,     3,     5,     5,
       1,     3,     3,     7,     6,     4,     5,     5,     9,     1,
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
      26,   292,    -1,    17,   225,    18,    -1,   292,    27,   225,
      -1,   292,   141,   225,    -1,   225,    33,   225,    -1,   225,
      15,   225,    -1,   225,    14,   225,    -1,   225,   155,   225,
      -1,   225,   154,   292,   156,   225,    -1,   225,   157,   225,
      -1,   225,    20,   292,   158,   225,    -1,   225,   171,   292,
      -1,   225,   161,   292,    -1,   225,   159,   292,    -1,   225,
     229,    -1,   162,   291,    13,    17,   226,    18,    -1,   225,
     147,   292,    31,   292,   148,   225,    -1,   225,    16,   225,
      -1,   225,   142,   292,    31,   292,   145,   225,    -1,   225,
      32,   225,    -1,   225,    20,   292,    31,   292,    32,   292,
      31,   292,    21,   225,    -1,   225,    20,   292,    32,   292,
      21,   225,    -1,   225,   142,   292,    31,   292,    31,   292,
     145,   225,    -1,   225,   142,   292,   145,   225,    -1,   227,
      13,   225,    -1,    17,   234,    13,   225,    18,    17,   291,
      18,    -1,    33,   218,    13,   225,    -1,    15,   218,    13,
     225,    -1,    14,   218,    13,   225,    -1,   147,   292,   148,
     218,    13,   225,    -1,    16,   218,    13,    20,   292,    21,
     225,    -1,   142,   292,   145,   218,    13,    20,   292,    21,
     225,    -1,    32,   218,    13,    20,   292,    31,   292,    21,
     225,    -1,    32,   218,    13,    20,   292,    21,   225,    -1,
      58,   299,    59,   225,    -1,    17,    99,   311,    13,   225,
      18,    -1,   315,    -1,    60,   310,   173,    -1,   163,   310,
     173,    -1,   314,    -1,   101,    17,   313,    18,    -1,    20,
     320,    21,    -1,   103,    19,    -1,   103,   292,    -1,   292,
     100,    94,   292,    -1,   317,    -1,   164,   326,    59,   292,
     163,   225,    -1,   164,   321,    59,   292,   163,   225,    -1,
     164,   165,   321,   118,   292,   163,   225,    -1,   164,   180,
      12,   292,    38,   292,   163,   225,    -1,   164,   180,    12,
     292,    38,   292,   166,   292,   163,   225,    -1,   167,   292,
     163,   225,    -1,   225,    -1,   226,   146,   225,    -1,   228,
      -1,   227,    33,   228,    -1,   140,   235,    -1,   139,   235,
      -1,   138,   235,    -1,   143,   230,   144,    -1,   143,   292,
      28,   292,    31,   329,   144,    -1,   143,   292,    28,   292,
      31,   329,    13,   292,   144,    -1,   292,    28,   292,    -1,
     230,   146,   292,    28,   292,    -1,     4,    -1,     4,   232,
      -1,     4,   232,    33,    -1,   233,    -1,   232,    33,   233,
      -1,   180,    -1,   235,    -1,   235,    -1,   234,    33,   235,
      -1,   180,   149,   246,    -1,   180,   146,   235,    -1,     5,
      -1,     5,   237,    -1,   238,    -1,   237,   238,    -1,   180,
      12,   292,    -1,   242,    -1,   255,    -1,   259,    -1,   241,
      -1,   241,   240,    -1,   242,    -1,   255,    -1,   259,    -1,
     276,    -1,   288,    -1,    10,   278,    -1,     3,    -1,     3,
     243,    -1,     3,   243,    33,    -1,   244,    -1,   243,    33,
     244,    -1,   245,   180,    12,   246,   254,    -1,   245,   180,
      12,   246,    -1,   245,   180,    34,   252,    -1,   245,   180,
      34,   252,   254,    -1,   176,    -1,   177,    -1,   178,    -1,
     179,    -1,    -1,    17,   246,    18,    -1,   248,    -1,   298,
      -1,    35,   180,    36,   252,   173,    -1,   246,    31,   246,
      -1,   246,    37,   246,    -1,    20,   246,    21,    -1,    43,
     246,    -1,    41,   246,    -1,    42,   246,    -1,    40,   246,
      38,   246,    -1,    39,   246,    38,   246,    -1,   249,    -1,
     247,    -1,   180,    -1,   247,    90,   180,    -1,   247,   104,
     180,    -1,   188,    -1,   189,    -1,   190,    -1,   191,    -1,
     192,    -1,   193,    -1,   194,    -1,   195,    -1,   250,    -1,
     251,    -1,   246,    44,   246,    -1,    19,    44,   246,    -1,
     246,    27,   246,    -1,    19,    27,   246,    -1,   253,    -1,
     252,   253,    -1,   246,    -1,   180,   149,   246,    -1,   180,
      45,   246,    -1,    47,   321,    46,   292,    -1,    48,    -1,
      48,   256,    -1,    48,   256,    33,    -1,   257,    -1,   256,
      33,   257,    -1,   245,   258,    -1,   180,   149,   246,    12,
     292,    -1,   322,   149,   246,    12,   292,    -1,   180,    12,
     292,    -1,   322,    12,   292,    -1,     7,    -1,     7,   260,
      -1,     7,   260,    33,    -1,   261,    -1,   260,    33,   261,
      -1,   262,    -1,   263,    -1,   245,   180,   267,   270,   271,
     274,    -1,   245,   264,    -1,   180,   149,   249,   180,   265,
      46,   266,   271,   273,   275,    -1,    19,    -1,    17,   323,
      18,    -1,   265,    17,   323,    18,    -1,   292,    -1,    53,
      -1,    54,    -1,    19,    -1,    17,   268,    18,    -1,   323,
     149,   246,    -1,   268,   146,   323,   149,   246,    -1,    -1,
     270,    -1,   180,   149,   246,    -1,   270,   146,   180,   149,
     246,    -1,   272,    -1,    -1,    49,   292,    -1,   274,    -1,
      -1,    50,   292,    -1,    51,   292,    -1,    -1,     8,    -1,
       8,   277,    -1,   278,    -1,   277,   278,    -1,   280,    -1,
     279,    -1,   245,   180,   149,   281,   180,   265,    46,   282,
     271,   273,    -1,   245,   180,   267,   269,   283,   271,   274,
      -1,   246,   102,   246,    -1,    19,   102,   246,    -1,   246,
     102,    19,    -1,    19,   102,    19,    -1,   225,    -1,    53,
      -1,    54,    -1,   284,    -1,    -1,    55,   285,    -1,   286,
      -1,   285,   286,    -1,   287,   291,    -1,   287,   291,   149,
     246,    -1,    56,    -1,    57,    -1,    11,    -1,    11,   289,
      -1,    11,   289,    33,    -1,   290,    -1,   289,    33,   290,
      -1,   245,   312,    -1,    47,   292,    -1,   292,    -1,   291,
     146,   292,    -1,   296,    -1,   293,    -1,   295,    -1,   294,
      -1,   297,    -1,   298,    -1,    17,   292,    18,    -1,   181,
      -1,   180,    -1,   180,    98,    -1,   292,    90,   180,    -1,
     292,   104,   180,    -1,   292,    91,    92,    -1,   292,    19,
      -1,   292,    17,   291,    18,    -1,   292,    17,   292,    86,
     292,    18,    -1,   292,    90,   325,    -1,   292,   153,   180,
      -1,   292,   153,   325,    -1,   292,   152,   321,    -1,   292,
     152,   327,    -1,    58,   299,    59,   292,    -1,   301,    -1,
     303,    -1,   306,    -1,   307,    -1,   134,   329,    13,   292,
      -1,   135,   329,    13,   292,    -1,   136,   326,    13,   292,
      -1,    52,   326,    13,   292,    -1,    29,    30,    -1,    29,
     291,    30,    -1,    29,   292,    31,   329,    30,    -1,    29,
     292,    31,   329,    13,   292,    30,    -1,    29,   292,    86,
     292,    30,    -1,    15,    -1,    20,   291,    21,    -1,    20,
     292,    31,   327,    21,    -1,    20,   292,    31,   327,    13,
     292,    21,    -1,   170,    -1,    29,   308,    30,    -1,    29,
     309,    31,   329,    30,    -1,    29,   309,    31,   329,    13,
     292,    30,    -1,    88,    17,   291,    18,    -1,    89,    17,
     291,    18,    -1,    93,   333,    13,   292,    -1,   168,    17,
     292,    18,    -1,    95,   248,    17,   292,    18,    -1,    95,
      17,   292,   146,   246,    18,    -1,    96,    17,   291,    18,
      -1,    97,    17,   292,   146,   292,    18,    -1,   150,   291,
     151,    -1,   150,   292,    31,   329,   151,    -1,   150,   292,
      31,   329,    13,   292,   151,    -1,   183,    -1,   182,    -1,
     184,    -1,   185,    -1,    92,    -1,   187,    -1,   160,    -1,
     137,    -1,   186,    -1,   300,    -1,   299,   146,   300,    -1,
     258,    -1,   264,    -1,    60,   292,    61,   292,   302,    -1,
      63,   292,    -1,    62,   292,    61,   292,   302,    -1,    64,
     292,   149,   304,   173,    -1,    64,   292,   149,   304,   146,
      65,    27,   292,   173,    -1,   305,    -1,   304,   146,   305,
      -1,   323,    27,   292,    -1,    66,   292,    -1,    67,   292,
      -1,    68,   292,    -1,    69,   292,    -1,    70,   292,    -1,
      71,   292,    -1,    72,   292,    -1,    73,   292,    -1,    74,
     292,    -1,    75,   292,    -1,    76,   292,    -1,    77,   292,
      -1,    78,   292,    -1,    79,   292,    -1,    80,   292,    -1,
      81,   292,    -1,    82,   292,    -1,    83,   292,    -1,    84,
     292,    -1,    85,   292,    -1,   292,    66,   292,    -1,   292,
      37,   292,    -1,   292,    67,   292,    -1,   292,   106,   292,
      -1,   292,   105,   292,    -1,   292,   107,   292,    -1,   292,
     108,   292,    -1,   292,   109,   292,    -1,   292,   110,   292,
      -1,   292,   111,   292,    -1,   292,   112,   292,    -1,   292,
      12,   292,    -1,   292,   113,   292,    -1,   292,   114,   292,
      -1,   292,   115,   292,    -1,   292,   116,   292,    -1,   292,
     117,   292,    -1,   292,   118,   292,    -1,   292,   119,   292,
      -1,   292,   120,   292,    -1,   292,   121,   292,    -1,   292,
     122,   292,    -1,   292,   123,   292,    -1,   292,   124,   292,
      -1,   292,   125,   292,    -1,   292,   126,   292,    -1,   292,
     127,   292,    -1,   292,   128,   292,    -1,   292,   129,   292,
      -1,   292,   130,   292,    -1,   292,   131,   292,    -1,   292,
     132,   292,    -1,   292,   133,   292,    -1,   309,    -1,   308,
     146,   309,    -1,   292,    87,   292,    -1,   292,    27,   225,
      -1,   310,    31,   292,    27,   225,    -1,   312,    -1,   311,
     146,   312,    -1,   180,   149,   246,    -1,   180,   149,   246,
     100,   292,    -1,   180,   149,   246,    59,   292,    -1,   314,
      -1,   313,    33,   314,    -1,   292,   100,   292,    -1,    60,
     292,    61,   225,   316,    63,   225,    -1,    60,   292,    61,
     225,    63,   225,    -1,    62,   292,    61,   225,    -1,   316,
      62,   292,    61,   225,    -1,    64,   292,   149,   318,   173,
      -1,    64,   292,   149,   318,   146,    65,    27,   225,   173,
      -1,   319,    -1,   318,   146,   319,    -1,   323,    27,   225,
      -1,   283,   271,   274,    -1,   324,    -1,   322,    -1,   325,
      -1,    88,    17,   323,   146,   321,    18,    -1,    89,    19,
      -1,    89,    17,   323,    18,    -1,   321,    -1,   323,   146,
     321,    -1,   180,    -1,    67,    -1,   296,    -1,   293,    -1,
     295,    -1,   294,    -1,   297,    -1,   298,    -1,    17,   292,
      18,    -1,   327,    -1,   328,    -1,   321,   118,   292,    -1,
     321,   149,   246,    -1,   330,    -1,   329,   146,   330,    -1,
     331,    -1,   332,    -1,   323,   118,   292,    -1,   323,   149,
     246,    -1,   328,    -1,   333,   146,   328,    -1
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
     279,   283,   287,   291,   297,   301,   307,   311,   315,   319,
     322,   329,   337,   341,   349,   353,   365,   373,   383,   389,
     393,   402,   407,   412,   417,   424,   432,   442,   452,   460,
     465,   472,   474,   478,   482,   484,   489,   493,   496,   499,
     504,   506,   513,   520,   528,   537,   548,   553,   555,   559,
     561,   565,   568,   571,   574,   578,   586,   596,   600,   606,
     608,   611,   615,   617,   621,   623,   625,   627,   631,   635,
     639,   641,   644,   646,   649,   653,   655,   657,   659,   661,
     664,   666,   668,   670,   672,   674,   677,   679,   682,   686,
     688,   692,   698,   703,   708,   714,   716,   718,   720,   722,
     723,   727,   729,   731,   737,   741,   745,   749,   752,   755,
     758,   763,   768,   770,   772,   774,   778,   782,   784,   786,
     788,   790,   792,   794,   796,   798,   800,   802,   806,   810,
     814,   818,   820,   823,   825,   829,   833,   838,   840,   843,
     847,   849,   853,   856,   862,   868,   872,   876,   878,   881,
     885,   887,   891,   893,   895,   902,   905,   916,   918,   922,
     927,   929,   931,   933,   935,   939,   943,   949,   950,   952,
     956,   962,   964,   965,   968,   970,   971,   974,   977,   978,
     980,   983,   985,   988,   990,   992,  1003,  1011,  1015,  1019,
    1023,  1027,  1029,  1031,  1033,  1035,  1036,  1039,  1041,  1044,
    1047,  1052,  1054,  1056,  1058,  1061,  1065,  1067,  1071,  1074,
    1077,  1079,  1083,  1085,  1087,  1089,  1091,  1093,  1095,  1099,
    1101,  1103,  1106,  1110,  1114,  1118,  1121,  1126,  1133,  1137,
    1141,  1145,  1149,  1153,  1158,  1160,  1162,  1164,  1166,  1171,
    1176,  1181,  1186,  1189,  1193,  1199,  1207,  1213,  1215,  1219,
    1225,  1233,  1235,  1239,  1245,  1253,  1258,  1263,  1268,  1273,
    1279,  1286,  1291,  1298,  1302,  1308,  1316,  1318,  1320,  1322,
    1324,  1326,  1328,  1330,  1332,  1334,  1336,  1340,  1342,  1344,
    1350,  1353,  1359,  1365,  1375,  1377,  1381,  1385,  1388,  1391,
    1394,  1397,  1400,  1403,  1406,  1409,  1412,  1415,  1418,  1421,
    1424,  1427,  1430,  1433,  1436,  1439,  1442,  1445,  1449,  1453,
    1457,  1461,  1465,  1469,  1473,  1477,  1481,  1485,  1489,  1493,
    1497,  1501,  1505,  1509,  1513,  1517,  1521,  1525,  1529,  1533,
    1537,  1541,  1545,  1549,  1553,  1557,  1561,  1565,  1569,  1573,
    1577,  1579,  1583,  1587,  1591,  1597,  1599,  1603,  1607,  1613,
    1619,  1621,  1625,  1629,  1637,  1644,  1649,  1655,  1661,  1671,
    1673,  1677,  1681,  1685,  1687,  1689,  1691,  1698,  1701,  1706,
    1708,  1712,  1714,  1716,  1718,  1720,  1722,  1724,  1726,  1728,
    1732,  1734,  1736,  1740,  1744,  1746,  1750,  1752,  1754,  1758,
    1762,  1764
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
     310,   314,   315,   316,   317,   318,   319,   331,   333,   334,
     335,   336,   337,   346,   347,   356,   366,   367,   368,   369,
     376,   381,   382,   386,   387,   392,   396,   401,   405,   408,
     410,   414,   415,   416,   420,   424,   429,   434,   438,   443,
     444,   445,   446,   447,   460,   461,   462,   467,   468,   479,
     480,   487,   489,   491,   492,   493,   494,   513,   514,   518,
     519,   523,   524,   525,   530,   532,   533,   546,   547,   557,
     558,   559,   563,   564,   576,   577,   584,   585,   589,   590,
     594,   595,   599,   600,   607,   611,   612,   613,   617,   618,
     622,   623,   624,   625,   626,   633,   637,   638,   639,   643,
     644,   648,   649,   650,   651,   661,   662,   663,   671,   672,
     676,   677,   678,   679,   680,   681,   682,   683,   684,   685,
     686,   687,   688,   697,   701,   702,   703,   707,   708,   709,
     710,   711,   712,   713,   714,   718,   719,   723,   724,   728,
     729,   733,   734,   738,   739,   740,   744,   755,   756,   757,
     761,   762,   766,   770,   771,   772,   773,   777,   778,   779,
     783,   784,   788,   789,   793,   797,   801,   806,   807,   808,
     812,   813,   814,   818,   819,   823,   824,   827,   829,   833,
     834,   838,   839,   843,   847,   848,   852,   863,   864,   868,
     869,   876,   877,   881,   882,   886,   890,   894,   895,   896,
     897,   901,   902,   903,   907,   908,   912,   916,   917,   921,
     922,   926,   927,   949,   950,   951,   955,   956,   960,   961,
     965,   966,   971,   972,   973,   974,   975,   976,   978,   979,
     980,   981,   982,   983,   984,   985,   986,   987,   988,   990,
     991,   992,   993,   995,   996,   997,   998,   999,  1001,  1002,
    1003,  1004,  1006,  1007,  1009,  1010,  1012,  1014,  1025,  1027,
    1028,  1030,  1031,  1033,  1034,  1036,  1038,  1040,  1049,  1050,
    1051,  1056,  1064,  1066,  1067,  1068,  1074,  1075,  1079,  1083,
    1087,  1088,  1089,  1093,  1097,  1103,  1104,  1108,  1109,  1113,
    1117,  1118,  1122,  1123,  1127,  1128,  1132,  1136,  1137,  1138,
    1139,  1140,  1141,  1142,  1143,  1144,  1145,  1146,  1147,  1148,
    1149,  1150,  1151,  1152,  1153,  1154,  1155,  1159,  1160,  1161,
    1162,  1163,  1164,  1165,  1166,  1167,  1168,  1169,  1170,  1171,
    1172,  1173,  1174,  1175,  1176,  1177,  1178,  1179,  1180,  1181,
    1182,  1183,  1184,  1185,  1186,  1187,  1188,  1189,  1190,  1191,
    1195,  1196,  1200,  1204,  1205,  1209,  1210,  1214,  1215,  1221,
    1228,  1229,  1242,  1246,  1247,  1251,  1252,  1256,  1257,  1261,
    1262,  1266,  1270,  1274,  1275,  1279,  1281,  1290,  1291,  1295,
    1296,  1300,  1302,  1307,  1308,  1309,  1310,  1311,  1312,  1313,
    1317,  1318,  1322,  1326,  1330,  1331,  1335,  1336,  1340,  1344,
    1348,  1349
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

  private static final int yylast_ = 12654;
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


