
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
    // import org.overture.transforms.*;
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
  public static final int LRPAREN = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUARE = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int RSQUARE = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSKIP = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSTOP = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPCHAOS = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPDIV = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPWAIT = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int RARROW = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int LARROW = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLY = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int RCURLY = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int BAR = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int DBAR = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANNELS = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANSETS = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int TYPES = 287;
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
  public static final int FUNCTIONS = 304;
  /** Token number, to be returned by the scanner.  */
  public static final int PRE = 305;
  /** Token number, to be returned by the scanner.  */
  public static final int POST = 306;
  /** Token number, to be returned by the scanner.  */
  public static final int MEASURE = 307;
  /** Token number, to be returned by the scanner.  */
  public static final int SUBCLASSRESP = 308;
  /** Token number, to be returned by the scanner.  */
  public static final int NOTYETSPEC = 309;
  /** Token number, to be returned by the scanner.  */
  public static final int OPERATIONS = 310;
  /** Token number, to be returned by the scanner.  */
  public static final int FRAME = 311;
  /** Token number, to be returned by the scanner.  */
  public static final int RD = 312;
  /** Token number, to be returned by the scanner.  */
  public static final int WR = 313;
  /** Token number, to be returned by the scanner.  */
  public static final int STATE = 314;
  /** Token number, to be returned by the scanner.  */
  public static final int LET = 315;
  /** Token number, to be returned by the scanner.  */
  public static final int IN = 316;
  /** Token number, to be returned by the scanner.  */
  public static final int IF = 317;
  /** Token number, to be returned by the scanner.  */
  public static final int THEN = 318;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSEIF = 319;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSE = 320;
  /** Token number, to be returned by the scanner.  */
  public static final int CASES = 321;
  /** Token number, to be returned by the scanner.  */
  public static final int OTHERS = 322;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUS = 323;
  /** Token number, to be returned by the scanner.  */
  public static final int MINUS = 324;
  /** Token number, to be returned by the scanner.  */
  public static final int ABS = 325;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOOR = 326;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT = 327;
  /** Token number, to be returned by the scanner.  */
  public static final int CARD = 328;
  /** Token number, to be returned by the scanner.  */
  public static final int POWER = 329;
  /** Token number, to be returned by the scanner.  */
  public static final int DUNION = 330;
  /** Token number, to be returned by the scanner.  */
  public static final int DINTER = 331;
  /** Token number, to be returned by the scanner.  */
  public static final int HD = 332;
  /** Token number, to be returned by the scanner.  */
  public static final int TL = 333;
  /** Token number, to be returned by the scanner.  */
  public static final int LEN = 334;
  /** Token number, to be returned by the scanner.  */
  public static final int ELEMS = 335;
  /** Token number, to be returned by the scanner.  */
  public static final int INDS = 336;
  /** Token number, to be returned by the scanner.  */
  public static final int REVERSE = 337;
  /** Token number, to be returned by the scanner.  */
  public static final int CONC = 338;
  /** Token number, to be returned by the scanner.  */
  public static final int DOM = 339;
  /** Token number, to be returned by the scanner.  */
  public static final int RNG = 340;
  /** Token number, to be returned by the scanner.  */
  public static final int MERGE = 341;
  /** Token number, to be returned by the scanner.  */
  public static final int INVERSE = 342;
  /** Token number, to be returned by the scanner.  */
  public static final int ELLIPSIS = 343;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRARROW = 344;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDER = 345;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDERNAME = 346;
  /** Token number, to be returned by the scanner.  */
  public static final int DOT = 347;
  /** Token number, to be returned by the scanner.  */
  public static final int DOTHASH = 348;
  /** Token number, to be returned by the scanner.  */
  public static final int NUMERAL = 349;
  /** Token number, to be returned by the scanner.  */
  public static final int LAMBDA = 350;
  /** Token number, to be returned by the scanner.  */
  public static final int NEW = 351;
  /** Token number, to be returned by the scanner.  */
  public static final int SELF = 352;
  /** Token number, to be returned by the scanner.  */
  public static final int ISUNDER = 353;
  /** Token number, to be returned by the scanner.  */
  public static final int PREUNDER = 354;
  /** Token number, to be returned by the scanner.  */
  public static final int ISOFCLASS = 355;
  /** Token number, to be returned by the scanner.  */
  public static final int TILDE = 356;
  /** Token number, to be returned by the scanner.  */
  public static final int DCL = 357;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONEQUALS = 358;
  /** Token number, to be returned by the scanner.  */
  public static final int ATOMIC = 359;
  /** Token number, to be returned by the scanner.  */
  public static final int DEQRARROW = 360;
  /** Token number, to be returned by the scanner.  */
  public static final int RETURN = 361;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER = 362;
  /** Token number, to be returned by the scanner.  */
  public static final int BACKTICK = 363;
  /** Token number, to be returned by the scanner.  */
  public static final int SLASH = 364;
  /** Token number, to be returned by the scanner.  */
  public static final int DIVIDE = 365;
  /** Token number, to be returned by the scanner.  */
  public static final int REM = 366;
  /** Token number, to be returned by the scanner.  */
  public static final int MOD = 367;
  /** Token number, to be returned by the scanner.  */
  public static final int LT = 368;
  /** Token number, to be returned by the scanner.  */
  public static final int LTE = 369;
  /** Token number, to be returned by the scanner.  */
  public static final int GT = 370;
  /** Token number, to be returned by the scanner.  */
  public static final int GTE = 371;
  /** Token number, to be returned by the scanner.  */
  public static final int NEQ = 372;
  /** Token number, to be returned by the scanner.  */
  public static final int OR = 373;
  /** Token number, to be returned by the scanner.  */
  public static final int AND = 374;
  /** Token number, to be returned by the scanner.  */
  public static final int EQRARROW = 375;
  /** Token number, to be returned by the scanner.  */
  public static final int LTEQUALSGT = 376;
  /** Token number, to be returned by the scanner.  */
  public static final int INSET = 377;
  /** Token number, to be returned by the scanner.  */
  public static final int NOTINSET = 378;
  /** Token number, to be returned by the scanner.  */
  public static final int SUBSET = 379;
  /** Token number, to be returned by the scanner.  */
  public static final int PROPER_SUBSET = 380;
  /** Token number, to be returned by the scanner.  */
  public static final int UNION = 381;
  /** Token number, to be returned by the scanner.  */
  public static final int BACKSLASH = 382;
  /** Token number, to be returned by the scanner.  */
  public static final int INTER = 383;
  /** Token number, to be returned by the scanner.  */
  public static final int CARET = 384;
  /** Token number, to be returned by the scanner.  */
  public static final int DPLUS = 385;
  /** Token number, to be returned by the scanner.  */
  public static final int MAPMERGE = 386;
  /** Token number, to be returned by the scanner.  */
  public static final int LTCOLON = 387;
  /** Token number, to be returned by the scanner.  */
  public static final int LTDASHCOLON = 388;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONGT = 389;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONDASHGT = 390;
  /** Token number, to be returned by the scanner.  */
  public static final int COMP = 391;
  /** Token number, to be returned by the scanner.  */
  public static final int DSTAR = 392;
  /** Token number, to be returned by the scanner.  */
  public static final int FORALL = 393;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS = 394;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS1 = 395;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING = 396;
  /** Token number, to be returned by the scanner.  */
  public static final int VRES = 397;
  /** Token number, to be returned by the scanner.  */
  public static final int RES = 398;
  /** Token number, to be returned by the scanner.  */
  public static final int VAL = 399;
  /** Token number, to be returned by the scanner.  */
  public static final int HEX_LITERAL = 400;
  /** Token number, to be returned by the scanner.  */
  public static final int QUOTE_LITERAL = 401;
  /** Token number, to be returned by the scanner.  */
  public static final int AMP = 402;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREBAR = 403;
  /** Token number, to be returned by the scanner.  */
  public static final int DLSQUARE = 404;
  /** Token number, to be returned by the scanner.  */
  public static final int DRSQUARE = 405;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRSQUARE = 406;
  /** Token number, to be returned by the scanner.  */
  public static final int COMMA = 407;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREDBAR = 408;
  /** Token number, to be returned by the scanner.  */
  public static final int DBARRSQUARE = 409;
  /** Token number, to be returned by the scanner.  */
  public static final int COLON = 410;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLYBAR = 411;
  /** Token number, to be returned by the scanner.  */
  public static final int BARRCURLY = 412;
  /** Token number, to be returned by the scanner.  */
  public static final int QUESTION = 413;
  /** Token number, to be returned by the scanner.  */
  public static final int BANG = 414;
  /** Token number, to be returned by the scanner.  */
  public static final int SLASHCOLON = 415;
  /** Token number, to be returned by the scanner.  */
  public static final int SLASHBACKSLASH = 416;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONBACKSLASH = 417;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUAREGT = 418;
  /** Token number, to be returned by the scanner.  */
  public static final int BARGT = 419;
  /** Token number, to be returned by the scanner.  */
  public static final int ENDSBY = 420;
  /** Token number, to be returned by the scanner.  */
  public static final int STARTBY = 421;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONINTER = 422;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONUNION = 423;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLYCOLON = 424;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONRCURLY = 425;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUARECOLON = 426;
  /** Token number, to be returned by the scanner.  */
  public static final int COLONRSQUARE = 427;
  /** Token number, to be returned by the scanner.  */
  public static final int MU = 428;
  /** Token number, to be returned by the scanner.  */
  public static final int TBOOL = 429;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT = 430;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT1 = 431;
  /** Token number, to be returned by the scanner.  */
  public static final int TINT = 432;
  /** Token number, to be returned by the scanner.  */
  public static final int TRAT = 433;
  /** Token number, to be returned by the scanner.  */
  public static final int TREAL = 434;
  /** Token number, to be returned by the scanner.  */
  public static final int TCHAR = 435;
  /** Token number, to be returned by the scanner.  */
  public static final int TTOKEN = 436;
  /** Token number, to be returned by the scanner.  */
  public static final int PRIVATE = 437;
  /** Token number, to be returned by the scanner.  */
  public static final int PROTECTED = 438;
  /** Token number, to be returned by the scanner.  */
  public static final int PUBLIC = 439;
  /** Token number, to be returned by the scanner.  */
  public static final int LOGICAL = 440;
  /** Token number, to be returned by the scanner.  */
  public static final int nameset = 441;
  /** Token number, to be returned by the scanner.  */
  public static final int namesetExpr = 442;
  /** Token number, to be returned by the scanner.  */
  public static final int UMINUS = 443;
  /** Token number, to be returned by the scanner.  */
  public static final int UPLUS = 444;



  
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
/* Line 367 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> decls = (List<PDeclaration>) ((yystack.valueAt (1-(1))));
    currentSourceFile.setDecls(decls);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 372 of "src/main/bison/cml.y"  */
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
/* Line 379 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> globalDecls = (List<PDeclaration>)((yystack.valueAt (1-(1))));
    currentSourceFile.setDecls(globalDecls);
};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 387 of "src/main/bison/cml.y"  */
    {
  List<PDeclaration> programParagraphList = new Vector<PDeclaration>();
  programParagraphList.add((PDeclaration)((yystack.valueAt (1-(1)))));
  yyval = programParagraphList;
};
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 393 of "src/main/bison/cml.y"  */
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
  LexNameToken lexName = extractLexNameToken(id);
  LexIdentifierToken classIdent = extractLexIdentifierToken(id);
  LexLocation loc = new LexLocation(currentSourceFile.getFile(), id.getValue(), startPos.line, startPos.column,
				    endPos.line, endPos.column, startPos.offset, endPos.offset);
  c.setLocation(loc);
  c.setName(lexName);
  c.setDeclarations((List<PDeclaration>)((yystack.valueAt (4-(4)))));
  //  c.setDefinitions((List)$4);
  AClassDeclaration res = new AClassDeclaration();
  res.setClassBody(c);
  res.setLocation(loc);
  res.setIdentifier(classIdent);
  res.setNameScope(NameScope.CLASSNAME);
  yyval = res;
};
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 438 of "src/main/bison/cml.y"  */
    {
  LexLocation processLoc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))));
  AProcessDefinition processDef = (AProcessDefinition)((yystack.valueAt (4-(4))));
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  LexLocation location = combineLexLocation(processLoc, processDef.getLocation());
  yyval = new AProcessDeclaration(location, NameScope.GLOBAL, id, processDef);
};
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 449 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (3-(3))));
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (3-(1))));
  LexLocation loc = combineLexLocation(extractFirstLexLocation(decls), process.getLocation());
  yyval = new AProcessDefinition(loc, NameScope.GLOBAL, false, null, decls, process);
};
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 456 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (1-(1))));
  yyval = new AProcessDefinition(process.getLocation(), NameScope.GLOBAL, false, null, null, process);
};
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 464 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  List<PDeclaration> processDeclarations = (List<PDeclaration>)((yystack.valueAt (5-(2))));
  PAction action = (PAction)((yystack.valueAt (5-(4))));
  yyval = new AStateProcess(location, processDeclarations, action);
};
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 471 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  List<PDeclaration> processDeclarations = null;
  PAction action = (PAction)((yystack.valueAt (4-(3))));
  yyval = new AStateProcess(location, processDeclarations, action);
};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 478 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASequentialCompositionProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 484 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AExternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 490 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 496 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  yyval = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (SChansetSetExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 502 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (7-(1))));
  PProcess right = (PProcess)((yystack.valueAt (7-(7))));
  yyval = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (SChansetSetExp)((yystack.valueAt (7-(3)))), (SChansetSetExp)((yystack.valueAt (7-(5)))), right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 508 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 514 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 520 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInterruptProcess(location, left, right);
};
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 533 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimedInterruptProcess(location, left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 540 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AUntimedTimeoutProcess(location, left, right);
};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 547 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimeoutProcess(location, left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 560 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  SChansetSetExp cse = (SChansetSetExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), cse.getLocation());
  yyval = new AHidingProcess(location, left, cse);
};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 567 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AStartDeadlineProcess(location, left, exp);
};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 574 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AEndDeadlineProcess(location, left, exp);
};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 585 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8)))));
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (8-(2))));
  LexNameToken identifier = extractLexNameToken((CmlLexeme)((yystack.valueAt (8-(4)))));
  yyval = new AInstantiationProcess(location, decls, identifier, (PExp)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 606 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PProcess process = (PProcess)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingProcess(combineLexLocation(process.getLocation(), renameExpression.getLocation()), process, renameExpression);
};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 665 of "src/main/bison/cml.y"  */
    {
  List<PDeclaration> processParagraphList = new Vector<PDeclaration>();
  processParagraphList.add((PDeclaration)((yystack.valueAt (1-(1)))));
  yyval = processParagraphList;
};
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 671 of "src/main/bison/cml.y"  */
    {
  List<PDeclaration> processParagraphList = (List<PDeclaration>)((yystack.valueAt (2-(1))));
  if (processParagraphList == null)
    processParagraphList = new Vector<PDeclaration>();
  processParagraphList.add((PDeclaration)((yystack.valueAt (2-(2)))));
  yyval = processParagraphList;
};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 682 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 686 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 693 of "src/main/bison/cml.y"  */
    {
  List<AActionDefinition> actionDefinitions = (List<AActionDefinition>)((yystack.valueAt (2-(2))));
  LexLocation declLoc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), extractLastLexLocation(actionDefinitions));
  yyval = new AActionDeclaration(declLoc, NameScope.GLOBAL, actionDefinitions);
};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 703 of "src/main/bison/cml.y"  */
    {
  List<AActionDefinition> actionDefs = new Vector<AActionDefinition>();
  actionDefs.add((AActionDefinition)((yystack.valueAt (1-(1)))));
  yyval = actionDefs;
};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 709 of "src/main/bison/cml.y"  */
    {
  List<AActionDefinition> actionDefs = (List<AActionDefinition>)((yystack.valueAt (2-(1))));
  actionDefs.add((AActionDefinition)((yystack.valueAt (2-(2)))));
  yyval = actionDefs;
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 718 of "src/main/bison/cml.y"  */
    {
  Object[] pa = (Object[])((yystack.valueAt (3-(3))));
  List<ASingleTypeDeclaration> declarations = (List<ASingleTypeDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), action.getLocation());
  AActionDefinition actionDefinition = new AActionDefinition(defLocation, NameScope.GLOBAL, false, null, declarations, action);
  yyval = actionDefinition;
};
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 730 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{new Vector<ASingleTypeDeclaration>(), ((yystack.valueAt (1-(1))))};
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 734 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3))))};
};
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 741 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASkipAction(location);
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 746 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AStopAction(location);
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 751 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AChaosAction(location);
};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 756 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ADivAction(location);
};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 761 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (4-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), exp.getLocation());
  yyval = new AWaitAction(location, exp);
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 768 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PAction action = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(id.getLocation(), action.getLocation());
  yyval = new ACommunicationAction(location, id, null, action);
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 775 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (4-(1)))));
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = combineLexLocation(id.getLocation(), action.getLocation());
  List<PCommunicationParameter> communicationParamters = (List<PCommunicationParameter>)((yystack.valueAt (4-(2))));
  yyval = new ACommunicationAction(location, id, communicationParamters, action);
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 790 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (4-(2))));
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = combineLexLocation(exp.getLocation(), action.getLocation());
  yyval = new AGuardedAction(location, exp, action);
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 797 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ASequentialCompositionAction(location, left, right);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 804 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AExternalChoiceAction(location, left, right);
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 811 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInternalChoiceAction(location, left, right);
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 818 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInterruptAction(location, left, right);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 831 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (5-(1))));
  PAction right = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimedInterruptAction(location, left, right, (PExp)((yystack.valueAt (5-(3)))));
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 838 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AUntimedTimeoutAction(location, left, right);
};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 851 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (5-(1))));
  PAction right = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimeoutAction(location, left, right, (PExp)((yystack.valueAt (5-(3)))));
};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 864 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  SChansetSetExp chansetExp = (SChansetSetExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), chansetExp.getLocation());
  yyval = new AHidingAction(location, left, chansetExp);
};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 871 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AStartDeadlineAction(location, left, exp);
};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 878 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AEndDeadlineAction(location, left, exp);
};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 885 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PAction action = (PAction)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingAction(combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 900 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 925 of "src/main/bison/cml.y"  */
    {
  List<PCommunicationParameter> comParamList = new Vector<PCommunicationParameter>();
  comParamList.add((PCommunicationParameter)((yystack.valueAt (1-(1)))));
  yyval = comParamList;
};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 931 of "src/main/bison/cml.y"  */
    {
  List<PCommunicationParameter> comParamList = (List<PCommunicationParameter>)((yystack.valueAt (2-(1))));
  if (comParamList == null)
    comParamList = new Vector<PCommunicationParameter>();
  comParamList.add((PCommunicationParameter)((yystack.valueAt (2-(2)))));
  yyval = comParamList;
};
  break;
    

  case 81:
  if (yyn == 81)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 942 of "src/main/bison/cml.y"  */
    {
  PParameter parameter = (PParameter)((yystack.valueAt (2-(2))));
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), parameter.getLocation());
  yyval = new AReadCommunicationParameter(location, parameter, null);
};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 948 of "src/main/bison/cml.y"  */
    {
  PParameter parameter = (PParameter)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))), exp.getLocation());
  yyval = new AReadCommunicationParameter(location, parameter, exp);
};
  break;
    

  case 83:
  if (yyn == 83)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 955 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), exp.getLocation());
  yyval = new AWriteCommunicationParameter(location, exp);
};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 971 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AIdentifierParameter(id.getLocation(), id);
};
  break;
    

  case 85:
  if (yyn == 85)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 976 of "src/main/bison/cml.y"  */
    {
  yyval = new ATupleParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4))))), (List<? extends PParameter>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 86:
  if (yyn == 86)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 980 of "src/main/bison/cml.y"  */
    {
  yyval = new ATupleParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), (CmlLexeme)((yystack.valueAt (2-(2))))), null);
};
  break;
    

  case 87:
  if (yyn == 87)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 984 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (4-(1)))));
  yyval = new ARecordParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4))))), name, (List<? extends PParameter>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 88:
  if (yyn == 88)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 989 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (2-(1)))));
  yyval = new ARecordParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), (CmlLexeme)((yystack.valueAt (2-(2))))), name, null);
};
  break;
    

  case 89:
  if (yyn == 89)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 997 of "src/main/bison/cml.y"  */
    {
  List<PParameter> parameters = new Vector<PParameter>();
  parameters.add((PParameter)((yystack.valueAt (1-(1)))));
  yyval = parameters;
};
  break;
    

  case 90:
  if (yyn == 90)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1003 of "src/main/bison/cml.y"  */
    {
  List<PParameter> parameters = (List<PParameter>)((yystack.valueAt (3-(1))));
  parameters.add((PParameter)((yystack.valueAt (3-(3)))));
  yyval = parameters;
};
  break;
    

  case 104:
  if (yyn == 104)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1034 of "src/main/bison/cml.y"  */
    {
  yyval = new ADeclarationInstantiatedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8))))), (List<? extends ASingleTypeDeclaration>)((yystack.valueAt (8-(2)))), (PAction)((yystack.valueAt (8-(4)))), (List<PExp>)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 114:
  if (yyn == 114)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1061 of "src/main/bison/cml.y"  */
    {
    yyval = new AEnumerationRenameChannelExp(null, extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))), (List<? extends ARenamePair>)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 115:
  if (yyn == 115)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1066 of "src/main/bison/cml.y"  */
    {
  yyval = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5))))), (List<? extends ARenamePair>)((yystack.valueAt (5-(2)))), (List<? extends PMultipleBind>)((yystack.valueAt (5-(4)))), null);
};
  break;
    

  case 116:
  if (yyn == 116)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1070 of "src/main/bison/cml.y"  */
    {
    yyval = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7))))), (List<? extends ARenamePair>)((yystack.valueAt (7-(2)))), (List<? extends PMultipleBind>)((yystack.valueAt (7-(4)))), (PExp)((yystack.valueAt (7-(6)))));
};
  break;
    

  case 117:
  if (yyn == 117)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1077 of "src/main/bison/cml.y"  */
    {
  List<ARenamePair> renamePairs = new Vector<ARenamePair>();
  ARenamePair pair = new ARenamePair(false, (AEventChannelExp)((yystack.valueAt (3-(1)))), (AEventChannelExp)((yystack.valueAt (3-(3)))));
  renamePairs.add(pair);
  yyval = renamePairs;
};
  break;
    

  case 118:
  if (yyn == 118)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1084 of "src/main/bison/cml.y"  */
    {
  List<ARenamePair> renamePairs = (List<ARenamePair>)((yystack.valueAt (5-(1))));
  ARenamePair pair = new ARenamePair(false, (AEventChannelExp)((yystack.valueAt (5-(3)))), (AEventChannelExp)((yystack.valueAt (5-(5)))));
  renamePairs.add(pair);
  yyval = renamePairs;
};
  break;
    

  case 119:
  if (yyn == 119)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1094 of "src/main/bison/cml.y"  */
    {
  LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  List<? extends PExp> dotExpression = null;
  yyval = new AEventChannelExp(id.getLocation(), id, dotExpression);
};
  break;
    

  case 120:
  if (yyn == 120)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1128 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (2-(2))));
  LexLocation start = decls.get(0).getLocation();
  LexLocation end = decls.get(decls.size()-1).getLocation();
  LexLocation location = combineLexLocation(start, end);
  AChannelDefinition channelDefinition = new AChannelDefinition(location, null, null, null, decls);
  AChannelDeclaration channelDecl = new AChannelDeclaration(location, NameScope.GLOBAL, channelDefinition);
  yyval = channelDecl;
};
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1141 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
  decls.add((AChannelNameDeclaration)((yystack.valueAt (1-(1)))));
  AChannelDefinition channelDefinition = new AChannelDefinition();
  yyval = decls;
};
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1148 of "src/main/bison/cml.y"  */
    {
     List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (3-(1))));
     decls.add((AChannelNameDeclaration)((yystack.valueAt (3-(3)))));
     yyval = decls;
 };
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1164 of "src/main/bison/cml.y"  */
    {
  List<LexNameToken> nameList = (List<LexNameToken>)((yystack.valueAt (1-(1))));
  List<LexIdentifierToken> ids = convertNameListToIdentifierList(nameList);
  LexLocation start = nameList.get(0).getLocation();
  LexLocation end = nameList.get(ids.size()-1).getLocation();
  LexLocation location = combineLexLocation(start, end);
  ASingleTypeDeclaration singleTypeDeclaration = new ASingleTypeDeclaration(location, NameScope.GLOBAL, ids, null);
  AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(location, NameScope.GLOBAL, singleTypeDeclaration);
  yyval = channelNameDecl;
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1175 of "src/main/bison/cml.y"  */
    {
  ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)((yystack.valueAt (1-(1))));
  AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(singleTypeDeclaration.getLocation(), NameScope.GLOBAL, singleTypeDeclaration);
  yyval = channelNameDecl;
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1184 of "src/main/bison/cml.y"  */
    {
  List<ASingleTypeDeclaration> decls = new Vector<ASingleTypeDeclaration>();
  decls.add((ASingleTypeDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1190 of "src/main/bison/cml.y"  */
    {
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((ASingleTypeDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1205 of "src/main/bison/cml.y"  */
    {
  List<LexNameToken> nameList = (List<LexNameToken>)((yystack.valueAt (3-(1))));
  List<LexIdentifierToken> ids = convertNameListToIdentifierList(nameList);
  ASingleTypeDeclaration singleTypeDeclaration =
    new ASingleTypeDeclaration(nameList.get(0).getLocation(), NameScope.GLOBAL, ids, (PType)((yystack.valueAt (3-(3)))));
  yyval = singleTypeDeclaration;
};
  break;
    

  case 128:
  if (yyn == 128)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1218 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AChansetDeclaration(id.getLocation(), NameScope.GLOBAL, null);
};
  break;
    

  case 129:
  if (yyn == 129)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1223 of "src/main/bison/cml.y"  */
    {
    List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(2))));
    yyval = new AChansetDeclaration(combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), extractLastLexLocation(defs)), NameScope.GLOBAL, defs);
};
  break;
    

  case 130:
  if (yyn == 130)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1231 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 131:
  if (yyn == 131)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1237 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(1))));
  defs.add((AChansetDefinition)((yystack.valueAt (2-(2)))));
  yyval = defs;
};
  break;
    

  case 132:
  if (yyn == 132)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1246 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  SChansetSetBase chansetExp = (SChansetSetBase)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(idToken.getLocation(), chansetExp.getLocation());
  yyval = new AChansetDefinition(location, NameScope.GLOBAL, false/*used_*/, null, /*AAccessSpecifierAccessSpecifier access_*/ idToken, chansetExp);
};
  break;
    

  case 133:
  if (yyn == 133)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1256 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AIdentifierChansetSetExp(idToken.getLocation(), idToken);
};
  break;
    

  case 134:
  if (yyn == 134)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1267 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)((yystack.valueAt (3-(2))));
  yyval = new AEnumChansetSetExp(location, identifiers);
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1273 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)((yystack.valueAt (3-(2))));
  yyval = new AEnumChansetSetExp(location, identifiers);
};
  break;
    

  case 136:
  if (yyn == 136)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1285 of "src/main/bison/cml.y"  */
    {
  PExp left = (PExp)((yystack.valueAt (3-(1))));
  PExp right = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ASetUnionBinaryExp(location, left, null, right);
};
  break;
    

  case 137:
  if (yyn == 137)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1298 of "src/main/bison/cml.y"  */
    {
  PExp left = (PExp)((yystack.valueAt (3-(1))));
  PExp right = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ASetIntersectBinaryExp(location, left, null, right);
};
  break;
    

  case 138:
  if (yyn == 138)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1311 of "src/main/bison/cml.y"  */
    {
  PExp left = (PExp)((yystack.valueAt (3-(1))));
  PExp right = (PExp)((yystack.valueAt (3-(3))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));
  /* LexToken lexToken = new LexToken(opLocation, VDMToken.BACKSLASH); */
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ASetDifferenceBinaryExp(location, left, /*lexToken*/null, right);
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1360 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 140:
  if (yyn == 140)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1367 of "src/main/bison/cml.y"  */
    {
  List<PDeclaration> declBlockList = new Vector<PDeclaration>();
  PDeclaration globalDecl = (PDeclaration)((yystack.valueAt (1-(1))));
  if (globalDecl != null) declBlockList.add(globalDecl);
  yyval = declBlockList;
};
  break;
    

  case 141:
  if (yyn == 141)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1374 of "src/main/bison/cml.y"  */
    {
  List<PDeclaration> declBlockList = (List<PDeclaration>)((yystack.valueAt (2-(1))));
  PDeclaration globalDecl = (PDeclaration)((yystack.valueAt (2-(2))));
  if (declBlockList != null && globalDecl != null)
      declBlockList.add(globalDecl);
  yyval = declBlockList;
};
  break;
    

  case 142:
  if (yyn == 142)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1385 of "src/main/bison/cml.y"  */
    {
  ATypeDeclaration typeDeclaration = (ATypeDeclaration)((yystack.valueAt (1-(1))));
  typeDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = typeDeclaration;
};
  break;
    

  case 143:
  if (yyn == 143)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1391 of "src/main/bison/cml.y"  */
    {
  AValueDeclaration valueGlobalDeclaration = new AValueDeclaration();
  yyval = valueGlobalDeclaration;
};
  break;
    

  case 144:
  if (yyn == 144)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1396 of "src/main/bison/cml.y"  */
    {
  AFunctionDeclaration functionGlobalDeclaration = (AFunctionDeclaration)((yystack.valueAt (1-(1))));
  functionGlobalDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = functionGlobalDeclaration;
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1407 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 146:
  if (yyn == 146)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1414 of "src/main/bison/cml.y"  */
    {
  List<PDeclaration> decls = new LinkedList<PDeclaration>();
  PDeclaration decl = (PDeclaration)((yystack.valueAt (1-(1))));
  decls.add(decl);
  yyval = decls;
};
  break;
    

  case 147:
  if (yyn == 147)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1421 of "src/main/bison/cml.y"  */
    {
  List<PDeclaration> decls = (List<PDeclaration>)((yystack.valueAt (2-(2))));
  PDeclaration decl = (PDeclaration)((yystack.valueAt (2-(1))));
  decls.add(decl);
  yyval = decls;
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1431 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1435 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1439 of "src/main/bison/cml.y"  */
    {
  AFunctionDeclaration functionDeclaration = (AFunctionDeclaration)((yystack.valueAt (1-(1))));
  functionDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = functionDeclaration;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1445 of "src/main/bison/cml.y"  */
    {
  AOperationDeclaration operationDeclaration = (AOperationDeclaration)((yystack.valueAt (1-(1))));
  operationDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = operationDeclaration;
};
  break;
    

  case 152:
  if (yyn == 152)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1451 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1464 of "src/main/bison/cml.y"  */
    {
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(typesLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(new Vector<ATypeDefinition>());
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1473 of "src/main/bison/cml.y"  */
    {
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (3-(1))));
  CmlLexeme semiLexeme = (CmlLexeme)((yystack.valueAt (3-(3))));
  LexLocation loc = extractLexLocation(typesLexeme, semiLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions((List<ATypeDefinition>)((yystack.valueAt (3-(2)))));
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1483 of "src/main/bison/cml.y"  */
    {
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (2-(1))));
  List<ATypeDefinition> tdefs = (List<ATypeDefinition>)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation(typesLexeme, tdefs.get(tdefs.size()-1).getLocation());
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(tdefs);
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 157:
  if (yyn == 157)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1496 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
  yyval = list;
};
  break;
    

  case 158:
  if (yyn == 158)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1502 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
  list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
  yyval = list;
};
  break;
    

  case 159:
  if (yyn == 159)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1511 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2)))));
  AInvariantInvariant inv = (AInvariantInvariant)((yystack.valueAt (5-(5))));
  LexLocation location = null;
  if (access.getLocation() != null) {
    location = combineLexLocation(access.getLocation(), inv.getLocation());
  } else {
    location = combineLexLocation(name.getLocation(), inv.getLocation());
  }
  yyval = new ATypeDefinition(location, null /*NameScope nameScope_*/, false, null/*SClassDefinition classDefinition_*/, access,
			   (PType)((yystack.valueAt (5-(4)))), null, inv.getInvPattern(), inv.getInvExpression(), null, true, name);
};
  break;
    

  case 160:
  if (yyn == 160)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1525 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  LexLocation location = null;
  if (access.getLocation() != null) {
    location = combineLexLocation(access.getLocation(), ((PType)((yystack.valueAt (4-(4))))).getLocation());
  } else {
      location = combineLexLocation(name.getLocation(), ((PType)((yystack.valueAt (4-(4))))).getLocation());
  }
  yyval = new ATypeDefinition(location, null /*NameScope nameScope_*/, false, null/*SClassDefinition classDefinition_*/, access,
			   (PType)((yystack.valueAt (4-(4)))), null, null, null, null, true, name);
};
  break;
    

  case 161:
  if (yyn == 161)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1538 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (4-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(access.getLocation(), extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc, false, null, false, null, name, fields, true);
  ATypeDefinition res = new ATypeDefinition(loc, NameScope.GLOBAL, false, null, access, recType, null, null, null, null, true, name);
  yyval = res;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1549 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2)))));
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (5-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (5-(4))));
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.
  LexLocation loc = combineLexLocation(access.getLocation(), extractLexLocation(vdmrec));
  //
  ARecordInvariantType recType = new ARecordInvariantType(loc, false, null, false, null, name, fields, true);
  ATypeDefinition res = new ATypeDefinition(loc, NameScope.GLOBAL, false, null, access, recType, null, null, null, null, true, name);
  yyval = res;
};
  break;
    

  case 163:
  if (yyn == 163)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1572 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  res.setAccess(new APrivateAccess());
  res.setLocation(location);
  yyval = res;
};
  break;
    

  case 164:
  if (yyn == 164)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1580 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  res.setLocation(location);
  res.setAccess(new AProtectedAccess());
  yyval = res;
};
  break;
    

  case 165:
  if (yyn == 165)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1588 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  res.setLocation(location);
  res.setAccess(new APublicAccess());
  yyval = res;
};
  break;
    

  case 166:
  if (yyn == 166)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1596 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(), null, null, location);
};
  break;
    

  case 167:
  if (yyn == 167)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1601 of "src/main/bison/cml.y"  */
    {
  /*Default private*/
  AAccessSpecifierAccessSpecifier a = new AAccessSpecifierAccessSpecifier();
  a.setAccess(new APrivateAccess());
  yyval = a;
};
  break;
    

  case 168:
  if (yyn == 168)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1611 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 169:
  if (yyn == 169)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1615 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 170:
  if (yyn == 170)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1619 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken value = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteType(value.location, false, null, value);
};
  break;
    

  case 172:
  if (yyn == 172)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1625 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 174:
  if (yyn == 174)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1630 of "src/main/bison/cml.y"  */
    {
  yyval = new  AOptionalType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))), false, null, (PType)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 175:
  if (yyn == 175)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1634 of "src/main/bison/cml.y"  */
    {
  CmlLexeme setof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation(setof), type.getLocation());
  ASetType res = new ASetType(loc, false, null, type, false, false);
  yyval = res;
};
  break;
    

  case 176:
  if (yyn == 176)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1642 of "src/main/bison/cml.y"  */
    {
  CmlLexeme seqof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation(seqof), type.getLocation());
  ASeqSeqType res = new ASeqSeqType(loc, false, null, type, false);
  yyval = res;
};
  break;
    

  case 177:
  if (yyn == 177)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1650 of "src/main/bison/cml.y"  */
    {
  CmlLexeme seqof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation(seqof), type.getLocation());
  ASeq1SeqType res = new ASeq1SeqType(loc, false, null, type, false);
  yyval = res;
};
  break;
    

  case 178:
  if (yyn == 178)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1658 of "src/main/bison/cml.y"  */
    {
  CmlLexeme mapof = (CmlLexeme)((yystack.valueAt (4-(1))));
  PType from = (PType)((yystack.valueAt (4-(2))));
  PType to   = (PType)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(mapof), to.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, from, to, false);
  yyval = res;
};
  break;
    

  case 179:
  if (yyn == 179)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1667 of "src/main/bison/cml.y"  */
    {
  CmlLexeme mapof = (CmlLexeme)((yystack.valueAt (4-(1))));
  PType from = (PType)((yystack.valueAt (4-(2))));
  PType to   = (PType)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(mapof), to.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, from, to, false);
  yyval = res;
};
  break;
    

  case 180:
  if (yyn == 180)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1676 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 184:
  if (yyn == 184)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1695 of "src/main/bison/cml.y"  */
    {
  yyval = new ABooleanBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 185:
  if (yyn == 185)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1699 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 186:
  if (yyn == 186)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1703 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 187:
  if (yyn == 187)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1707 of "src/main/bison/cml.y"  */
    {
  yyval = new AIntNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 188:
  if (yyn == 188)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1711 of "src/main/bison/cml.y"  */
    {
  yyval = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 189:
  if (yyn == 189)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1715 of "src/main/bison/cml.y"  */
    {
  yyval = new ARealNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 190:
  if (yyn == 190)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1719 of "src/main/bison/cml.y"  */
    {
  yyval = new ACharBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 191:
  if (yyn == 191)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1723 of "src/main/bison/cml.y"  */
    {
  yyval = new ATokenBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 192:
  if (yyn == 192)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1771 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 193:
  if (yyn == 193)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1775 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 194:
  if (yyn == 194)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1782 of "src/main/bison/cml.y"  */
    {
  PType domType = (PType)((yystack.valueAt (3-(1))));
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  // TODO --- is this a dead comment?
  // [CONSIDER,RWL] The domain type of a function is not a list,
  // I think the AST is wrong taking a list of types for params
  // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, true, params, rngType);
};
  break;
    

  case 195:
  if (yyn == 195)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1795 of "src/main/bison/cml.y"  */
    {
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  // TODO --- is this a dead comment?
  // [CONSIDER,RWL] The domain type of a function is not a list,
  // I think the AST is wrong taking a list of types for params
  // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, true, params, rngType);
};
  break;
    

  case 196:
  if (yyn == 196)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1811 of "src/main/bison/cml.y"  */
    {
  PType domType = (PType)((yystack.valueAt (3-(1))));
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  // TODO --- is this a dead comment?
  // [CONSIDER,RWL] The domain type of a function is not a list,
  // I think the AST is wrong taking a list of types for params
  // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, false, params, rngType);
};
  break;
    

  case 197:
  if (yyn == 197)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1824 of "src/main/bison/cml.y"  */
    {
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  // TODO --- is this a dead comment?
  // [CONSIDER,RWL] The domain type of a function is not a list,
  // I think the AST is wrong taking a list of types for params
  // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, false, params, rngType);
};
  break;
    

  case 198:
  if (yyn == 198)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1840 of "src/main/bison/cml.y"  */
    {
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  String value = id.getValue();
  yyval = new LexQuoteToken(value.substring(1, value.length()-2), loc);
};
  break;
    

  case 199:
  if (yyn == 199)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1850 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 200:
  if (yyn == 200)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1856 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> head = (List<AFieldField>)((yystack.valueAt (2-(1))));
  head.add((AFieldField)((yystack.valueAt (2-(2)))));
  yyval = head;
};
  break;
    

  case 201:
  if (yyn == 201)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1865 of "src/main/bison/cml.y"  */
    {
  yyval = new AFieldField(null, null, null, (PType)((yystack.valueAt (1-(1)))), null);
};
  break;
    

  case 202:
  if (yyn == 202)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1869 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PType type = (PType) ((yystack.valueAt (3-(3))));
  yyval = new AFieldField(null, name, null, type, null);
};
  break;
    

  case 203:
  if (yyn == 203)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1875 of "src/main/bison/cml.y"  */
    {
  // TODO --- dude, that's harsh
  throw new RuntimeException("No way");
};
  break;
    

  case 204:
  if (yyn == 204)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1883 of "src/main/bison/cml.y"  */
    {
  CmlLexeme vdmInvLexeme = (CmlLexeme)((yystack.valueAt (4-(1))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = extractLexLocation(vdmInvLexeme, exp.getLocation());
  yyval = new AInvariantInvariant(loc, (PPattern)((yystack.valueAt (4-(2)))), exp);
};
  break;
    

  case 205:
  if (yyn == 205)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1895 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  AValueDeclaration valueDecl = new AValueDeclaration();
  valueDecl.setDefinitions(defs);
  yyval = valueDecl;
};
  break;
    

  case 206:
  if (yyn == 206)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1936 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 207:
  if (yyn == 207)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1942 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (2-(1)))));
  yyval = defs;
};
  break;
    

  case 208:
  if (yyn == 208)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1948 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (3-(1))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(3))));
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 209:
  if (yyn == 209)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1958 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
  PDefinition def = (PDefinition)((yystack.valueAt (2-(2))));
  def.setAccess(access);
  yyval = def;
};
  break;
    

  case 210:
  if (yyn == 210)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1968 of "src/main/bison/cml.y"  */
    {
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (5-(1))));
  PType type = (PType)((yystack.valueAt (5-(3))));
  PExp expression = (PExp)((yystack.valueAt (5-(5))));
  LexNameToken lnt = extractLexNameToken(id);
  AIdentifierPattern idp = new AIdentifierPattern();
  idp.setLocation(lnt.location);
  // Build the resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(idp.getLocation(), expression.getLocation()));
  yyval = vdef;
};
  break;
    

  case 211:
  if (yyn == 211)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1985 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (5-(1))));
  PType type = (PType)((yystack.valueAt (5-(3))));
  PExp expression = (PExp)((yystack.valueAt (5-(5))));
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(pattern.getLocation(), expression.getLocation()));
  yyval = vdef;
};
  break;
    

  case 212:
  if (yyn == 212)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1998 of "src/main/bison/cml.y"  */
    {
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (3-(1))));
  PExp expression = (PExp)((yystack.valueAt (3-(3))));
  LexNameToken lnt = extractLexNameToken(id);
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location, null, false, lnt);
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(idp.getLocation(), expression.getLocation()));
  yyval = vdef;
};
  break;
    

  case 213:
  if (yyn == 213)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2012 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PExp expression = (PExp)((yystack.valueAt (3-(3))));
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(pattern.getLocation(), expression.getLocation()));
  yyval = vdef;
};
  break;
    

  case 214:
  if (yyn == 214)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2029 of "src/main/bison/cml.y"  */
    {
  CmlLexeme functionsLexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  AFunctionDeclaration fdecl = new AFunctionDeclaration();
  fdecl.setLocation(extractLexLocation(functionsLexeme));
  yyval = fdecl;
};
  break;
    

  case 215:
  if (yyn == 215)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2036 of "src/main/bison/cml.y"  */
    {
  CmlLexeme functionsLexeme = (CmlLexeme)((yystack.valueAt (2-(1))));
  List<SFunctionDefinition> functionDefs = (List<SFunctionDefinition>) ((yystack.valueAt (2-(2))));
  AFunctionDeclaration fdecl = new AFunctionDeclaration();
  fdecl.setLocation(extractLexLocation(functionsLexeme, functionDefs.get(functionDefs.size()-1).getLocation()));
  fdecl.setFunctionDefinitions(functionDefs);
  yyval = fdecl;
};
  break;
    

  case 216:
  if (yyn == 216)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2048 of "src/main/bison/cml.y"  */
    {
  List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
  functionList.add((SFunctionDefinition)((yystack.valueAt (1-(1)))));
  yyval = functionList;
};
  break;
    

  case 217:
  if (yyn == 217)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2054 of "src/main/bison/cml.y"  */
    {
  List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
  functionList.add((SFunctionDefinition)((yystack.valueAt (2-(1)))));
  yyval = functionList;
};
  break;
    

  case 218:
  if (yyn == 218)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2060 of "src/main/bison/cml.y"  */
    {
  List<SFunctionDefinition> functionList = (List<SFunctionDefinition>)((yystack.valueAt (3-(3))));
  functionList.add((SFunctionDefinition)((yystack.valueAt (3-(1)))));
  yyval = functionList;
};
  break;
    

  case 219:
  if (yyn == 219)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2069 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 220:
  if (yyn == 220)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2073 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 221:
  if (yyn == 221)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2080 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (6-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));
  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)((yystack.valueAt (6-(3))));
  List<APatternTypePair> result = (List<APatternTypePair>)((yystack.valueAt (6-(4))));
  PExp preExp = (PExp)((yystack.valueAt (6-(5))));
  PExp postExp = (PExp)((yystack.valueAt (6-(6))));
  LexLocation location = null;
  if (access.getLocation() != null) {
    location = combineLexLocation(access.getLocation(), postExp.getLocation());
  } else {
    location = combineLexLocation(name.getLocation(), postExp.getLocation());
  }
  AImplicitFunctionFunctionDefinition impFunc =
    new AImplicitFunctionFunctionDefinition(location, null, false, access, null, paramPatterns, result, preExp, postExp);
  impFunc.setName(name);
  yyval = impFunc;
};
  break;
    

  case 222:
  if (yyn == 222)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2102 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
  AExplicitFunctionFunctionDefinition f = (AExplicitFunctionFunctionDefinition)((yystack.valueAt (2-(2))));
  f.setAccess(access);
  yyval = f;
};
  break;
    

  case 223:
  if (yyn == 223)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2112 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (10-(1)))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (10-(1)))));
  AFunctionType ftype = (AFunctionType)((yystack.valueAt (10-(3))));
  AExplicitFunctionFunctionDefinition res = new AExplicitFunctionFunctionDefinition();
  res.setName(name);
  res.setLocation(loc);
  res.setType(ftype);
  yyval = res;
};
  break;
    

  case 224:
  if (yyn == 224)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2128 of "src/main/bison/cml.y"  */
    {
  List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
  yyval = patternListList;
};
  break;
    

  case 225:
  if (yyn == 225)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2133 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(2))));
  List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
  patternListList.add(patternList);
  yyval = patternListList;
};
  break;
    

  case 226:
  if (yyn == 226)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2140 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (4-(3))));
  List<List<PPattern>> patternListList = (List<List<PPattern>>)((yystack.valueAt (4-(1))));
  patternListList.add(patternList);
  yyval = patternListList;
};
  break;
    

  case 227:
  if (yyn == 227)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2150 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 228:
  if (yyn == 228)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2154 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 229:
  if (yyn == 229)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2158 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 230:
  if (yyn == 230)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2165 of "src/main/bison/cml.y"  */
    {
  yyval = new Vector<APatternListTypePair>();
};
  break;
    

  case 231:
  if (yyn == 231)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2169 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 232:
  if (yyn == 232)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2176 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(1))));
  List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
  pltpl.add(new APatternListTypePair(false, patternList, (PType)((yystack.valueAt (3-(3))))));
  yyval = pltpl;
};
  break;
    

  case 233:
  if (yyn == 233)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2183 of "src/main/bison/cml.y"  */
    {
  List<APatternListTypePair> pltpl = (List<APatternListTypePair>)((yystack.valueAt (5-(1))));
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (5-(3))));
  pltpl.add(new APatternListTypePair(false, patternList, (PType)((yystack.valueAt (5-(5))))));
  yyval = pltpl;
};
  break;
    

  case 234:
  if (yyn == 234)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2193 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 235:
  if (yyn == 235)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2197 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 236:
  if (yyn == 236)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2204 of "src/main/bison/cml.y"  */
    {
  AIdentifierTypePair typePair = new AIdentifierTypePair(null, extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1))))), (PType)((yystack.valueAt (3-(3)))));
  List<AIdentifierTypePair> typePairs = new Vector<AIdentifierTypePair>();
  typePairs.add(typePair);
  yyval = typePairs;
};
  break;
    

  case 237:
  if (yyn == 237)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2211 of "src/main/bison/cml.y"  */
    {
  AIdentifierTypePair typePair = new AIdentifierTypePair(null, extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (5-(3))))), (PType)((yystack.valueAt (5-(5)))));
  List<AIdentifierTypePair> typePairs = (List<AIdentifierTypePair>)((yystack.valueAt (5-(1))));
  typePairs.add(typePair);
  yyval = typePairs;
};
  break;
    

  case 238:
  if (yyn == 238)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2221 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 239:
  if (yyn == 239)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2225 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 240:
  if (yyn == 240)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2232 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 241:
  if (yyn == 241)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2239 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 242:
  if (yyn == 242)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2243 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 243:
  if (yyn == 243)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2250 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 244:
  if (yyn == 244)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2262 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 246:
  if (yyn == 246)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2272 of "src/main/bison/cml.y"  */
    {
  List<? extends SOperationDefinition> opDefinitions = (List<? extends SOperationDefinition>)((yystack.valueAt (2-(2))));
  LexLocation lastInListLoc = opDefinitions.get(opDefinitions.size()-1).getLocation();
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), lastInListLoc);
  yyval = new AOperationDeclaration(location, NameScope.GLOBAL, opDefinitions);
};
  break;
    

  case 247:
  if (yyn == 247)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2279 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AOperationDeclaration(location, NameScope.GLOBAL, null);
};
  break;
    

  case 248:
  if (yyn == 248)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2287 of "src/main/bison/cml.y"  */
    {
    List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
    opDefinitions.add((SOperationDefinition)((yystack.valueAt (1-(1)))));
    yyval = opDefinitions;
};
  break;
    

  case 249:
  if (yyn == 249)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2300 of "src/main/bison/cml.y"  */
    {
    List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)((yystack.valueAt (3-(1))));
    opDefinitions.add((SOperationDefinition)((yystack.valueAt (3-(3)))));
    yyval = opDefinitions;
};
  break;
    

  case 250:
  if (yyn == 250)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2311 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 251:
  if (yyn == 251)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2315 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 252:
  if (yyn == 252)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2322 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (10-(2)))));
  AExplicitOperationOperationDefinition res = new AExplicitOperationOperationDefinition();
  res.setLocation(loc);
  yyval = res;
};
  break;
    

  case 253:
  if (yyn == 253)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2332 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (7-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (7-(2)))));
  List<? extends APatternListTypePair> parameterPatterns = (List<? extends APatternListTypePair>)((yystack.valueAt (7-(3))));
  List<? extends AIdentifierTypePair> result = (List<? extends AIdentifierTypePair>)((yystack.valueAt (7-(4))));
  List<? extends AExternalClause> externals = (List<? extends AExternalClause>)((yystack.valueAt (7-(5))));
  PExp precondition = (PExp)((yystack.valueAt (7-(6))));
  PExp postcondition = (PExp)((yystack.valueAt (7-(7))));
  LexLocation location = null;
  if (access != null)
    location = combineLexLocation(name.location, postcondition.getLocation());
  else
    location = combineLexLocation(access.getLocation(), postcondition.getLocation());
  AImplicitOperationOperationDefinition ifunc =
    new AImplicitOperationOperationDefinition(location, NameScope.GLOBAL, null, access, parameterPatterns, result,
					      externals, precondition, postcondition, null, null);
  yyval = ifunc;
};
  break;
    

  case 261:
  if (yyn == 261)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2364 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 262:
  if (yyn == 262)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2368 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), null, null);
};
  break;
    

  case 263:
  if (yyn == 263)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2375 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 264:
  if (yyn == 264)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2379 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 265:
  if (yyn == 265)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2386 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 266:
  if (yyn == 266)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2393 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)((yystack.valueAt (1-(1)))));
  yyval = infoList;
};
  break;
    

  case 267:
  if (yyn == 267)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2399 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = (List<AExternalClause>)((yystack.valueAt (2-(1))));
  infoList.add((AExternalClause)((yystack.valueAt (2-(2)))));
  yyval = infoList;
};
  break;
    

  case 268:
  if (yyn == 268)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2408 of "src/main/bison/cml.y"  */
    {
  yyval = new AExternalClause((LexToken)((yystack.valueAt (2-(1)))), (List<? extends LexNameToken>)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 269:
  if (yyn == 269)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2412 of "src/main/bison/cml.y"  */
    {
  yyval = new AExternalClause((LexToken)((yystack.valueAt (4-(1)))), (List<? extends LexNameToken>)((yystack.valueAt (4-(2)))), (PType)((yystack.valueAt (4-(4)))));
};
  break;
    

  case 270:
  if (yyn == 270)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2419 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.READ); // TODO why are we using VDMToken?
};
  break;
    

  case 271:
  if (yyn == 271)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2423 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.WRITE); // TODO why are we using VDMToken?
};
  break;
    

  case 272:
  if (yyn == 272)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2450 of "src/main/bison/cml.y"  */
    {
  AStateDefinition stateDef = (AStateDefinition)((yystack.valueAt (2-(2))));
  yyval  = new AStateDeclaration(null, NameScope.GLOBAL, stateDef);
};
  break;
    

  case 273:
  if (yyn == 273)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2455 of "src/main/bison/cml.y"  */
    {
  yyval  = new AStateDeclaration(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), NameScope.GLOBAL, null);
};
  break;
    

  case 274:
  if (yyn == 274)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2462 of "src/main/bison/cml.y"  */
    {
  AStateDefinition stateDef = new AStateDefinition();
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  stateDef.setStateDefs(defs);
  yyval = stateDef;
};
  break;
    

  case 275:
  if (yyn == 275)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2470 of "src/main/bison/cml.y"  */
    {
  AStateDefinition stateDef = new AStateDefinition();
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (2-(1)))));
  stateDef.setStateDefs(defs);
  yyval = stateDef;
};
  break;
    

  case 276:
  if (yyn == 276)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2478 of "src/main/bison/cml.y"  */
    {
  AStateDefinition stateDef = (AStateDefinition)((yystack.valueAt (3-(3))));
  stateDef.getStateDefs().add((PDefinition)((yystack.valueAt (3-(1)))));
  yyval = stateDef;
};
  break;
    

  case 277:
  if (yyn == 277)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2487 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 278:
  if (yyn == 278)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2491 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp) ((yystack.valueAt (2-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
  yyval = new AClassInvariantDefinition(location, NameScope.GLOBAL, true, null, exp);
};
  break;
    

  case 279:
  if (yyn == 279)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2502 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)((yystack.valueAt (1-(1)))));
  yyval = exps;
};
  break;
    

  case 280:
  if (yyn == 280)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2508 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(1))));
  exps.add((PExp)((yystack.valueAt (3-(3)))));
  yyval = exps;
};
  break;
    

  case 281:
  if (yyn == 281)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2528 of "src/main/bison/cml.y"  */
    {
  // Get a whole STRING from the lexer
  CmlLexeme s = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation sl = extractLexLocation(s);

  // extract the string and convert it to a char array
  String str = s.getValue();
  char[] chrs = str.toCharArray();

  // build a list of ACharLiteralSymbolicLiteralExp from the lexer String
  List<PExp> members = new LinkedList<PExp>();
  for(int i = 0; i < chrs.length;i++) {
    LexLocation cl = new LexLocation(currentSourceFile.getFile(), "Default",
				     sl.startLine, sl.startPos + i,
				     sl.startLine, sl.startPos + (i + 1), 0, 0);
    members.add(new ACharLiteralSymbolicLiteralExp(cl, new LexCharacterToken(chrs[i], cl)));
  }

  // Build the ASeqEnumSeqExp as usual
  ASeqEnumSeqExp res = new ASeqEnumSeqExp(sl, members);
  yyval = res;
};
  break;
    

  case 282:
  if (yyn == 282)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2551 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new ABracketedExp(loc, (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 283:
  if (yyn == 283)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2556 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> l = (List<PDefinition>)((yystack.valueAt (4-(2))));
  PExp e = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), e.getLocation());
  yyval = new ALetDefExp(loc, l, e);
};
  break;
    

  case 284:
  if (yyn == 284)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2563 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 285:
  if (yyn == 285)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2567 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 286:
  if (yyn == 286)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2571 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 287:
  if (yyn == 287)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2575 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 288:
  if (yyn == 288)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2580 of "src/main/bison/cml.y"  */
    {
  CmlLexeme forall = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  CmlLexeme amp = (CmlLexeme)((yystack.valueAt (4-(3))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(forall), exp.getLocation());
  AForAllExp forallexp = new AForAllExp(loc, binds, exp);
  yyval = forallexp;
};
  break;
    

  case 289:
  if (yyn == 289)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2590 of "src/main/bison/cml.y"  */
    {
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  AExistsExp existsExp = new AExistsExp(loc, binds, exp);
  yyval = existsExp;
};
  break;
    

  case 290:
  if (yyn == 290)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2599 of "src/main/bison/cml.y"  */
    {
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  AExists1Exp existsExp = new AExists1Exp(loc, bind, exp, null);
  yyval = existsExp;
};
  break;
    

  case 291:
  if (yyn == 291)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2609 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lc = (CmlLexeme)((yystack.valueAt (2-(1))));
  CmlLexeme rc = (CmlLexeme)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation(lc), extractLexLocation(rc));
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation(loc);
  yyval = res;
};
  break;
    

  case 292:
  if (yyn == 292)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2618 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<PExp> members = (List<PExp>)((yystack.valueAt (3-(2))));
  yyval = new ASetEnumSetExp(location, members);
};
  break;
    

  case 293:
  if (yyn == 293)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2625 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (5-(1))));
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (5-(5))));
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  ASetCompSetExp setComp = new ASetCompSetExp(loc, exp, binds, null);
  yyval = setComp;
};
  break;
    

  case 294:
  if (yyn == 294)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2635 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (7-(1))));
    PExp exp = (PExp)((yystack.valueAt (7-(2))));
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
    PExp pred = (PExp)((yystack.valueAt (7-(6))));
    CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (7-(7))));
    LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
    ASetCompSetExp setComp = new ASetCompSetExp(loc, exp, binds, pred);
    yyval = setComp;
};
  break;
    

  case 295:
  if (yyn == 295)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2647 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (5-(1))));
  PExp start = (PExp)((yystack.valueAt (5-(2))));
  PExp end = (PExp)((yystack.valueAt (5-(4))));
  CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (5-(5))));
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  ASetRangeSetExp res = new ASetRangeSetExp(loc, start, end);
  yyval = res;
};
  break;
    

  case 296:
  if (yyn == 296)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2658 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (2-(1))));
  CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (2-(2))));
  List<PExp> exps = new LinkedList<PExp>();
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
  yyval = exp;
};
  break;
    

  case 297:
  if (yyn == 297)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2667 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (3-(1))));
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(2))));
  CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
  yyval = exp;
};
  break;
    

  case 298:
  if (yyn == 298)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2677 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (5-(1))));
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  ASetBind binds = (ASetBind)((yystack.valueAt (5-(4))));
  CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (5-(5))));
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqCompSeqExp res = new ASeqCompSeqExp(loc, exp, binds, null);
  yyval = res;
};
  break;
    

  case 299:
  if (yyn == 299)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2687 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (7-(1))));
  PExp exp = (PExp)((yystack.valueAt (7-(2))));
  ASetBind binds = (ASetBind)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (7-(7))));
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqCompSeqExp res = new ASeqCompSeqExp(loc, exp, binds, pred);
  yyval = res;
};
  break;
    

  case 300:
  if (yyn == 300)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2699 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (3-(1))));
  CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (3-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  AMapEnumMapExp res = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
  yyval = res;
};
  break;
    

  case 301:
  if (yyn == 301)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2707 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (3-(1))));
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(2))));
  CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  AMapEnumMapExp res = new AMapEnumMapExp(loc, maplets);
  yyval = res;
};
  break;
    

  case 302:
  if (yyn == 302)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2717 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lcurl = (CmlLexeme)((yystack.valueAt (5-(1))));
  AMapletExp maplet = (AMapletExp)((yystack.valueAt (5-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  CmlLexeme rcurl = (CmlLexeme)((yystack.valueAt (5-(5))));
  LexLocation loc = combineLexLocation(extractLexLocation(lcurl), extractLexLocation(rcurl));
  AMapCompMapExp res = new AMapCompMapExp(loc, maplet, binds, null);
  yyval = res;
};
  break;
    

  case 303:
  if (yyn == 303)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2727 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lcurl = (CmlLexeme)((yystack.valueAt (7-(1))));
  AMapletExp maplet = (AMapletExp)((yystack.valueAt (7-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  CmlLexeme rcurl = (CmlLexeme)((yystack.valueAt (7-(7))));
  LexLocation loc = combineLexLocation(extractLexLocation(lcurl), extractLexLocation(rcurl));
  AMapCompMapExp res = new AMapCompMapExp(loc, maplet, binds, pred);
  yyval = res;
};
  break;
    

  case 304:
  if (yyn == 304)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2738 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 305:
  if (yyn == 305)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2742 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 306:
  if (yyn == 306)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2746 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 307:
  if (yyn == 307)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2750 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 308:
  if (yyn == 308)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2754 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 309:
  if (yyn == 309)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2763 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (6-(1))));
};
  break;
    

  case 312:
  if (yyn == 312)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2788 of "src/main/bison/cml.y"  */
    {
  LexIntegerToken lit = (LexIntegerToken)((yystack.valueAt (1-(1))));
  yyval = new AIntLiteralSymbolicLiteralExp(lit.location, lit);
};
  break;
    

  case 313:
  if (yyn == 313)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2798 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken value = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteLiteralSymbolicLiteralExp(value.location, value);
};
  break;
    

  case 314:
  if (yyn == 314)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2806 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(lexeme);
  yyval = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
};
  break;
    

  case 315:
  if (yyn == 315)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2812 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(lexeme);
  yyval = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
};
  break;
    

  case 316:
  if (yyn == 316)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2821 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 317:
  if (yyn == 317)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2827 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (3-(3))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 318:
  if (yyn == 318)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2837 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 319:
  if (yyn == 319)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2841 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 320:
  if (yyn == 320)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2850 of "src/main/bison/cml.y"  */
    {
  CmlLexeme _if = (CmlLexeme)((yystack.valueAt (5-(1))));
  Position sif = _if.getStartPos();
  Position eif = _if.getEndPos();
  PExp test = (PExp)((yystack.valueAt (5-(2))));
  PExp then = (PExp)((yystack.valueAt (5-(4))));
  List<AElseIfExp> elses = (List<AElseIfExp>)((yystack.valueAt (5-(5))));
  AIfExp ifexp = new AIfExp();
  ifexp.setTest(test);
  ifexp.setThen(then);
  ifexp.setElseList(elses);
  ifexp.setLocation(combineLexLocation(new LexLocation(null, "DEFAULT", sif.line, sif.column, sif.line, eif.column,
						       sif.offset, eif.offset),
				       extractLastLexLocation(elses)));
  yyval = ifexp;
};
  break;
    

  case 321:
  if (yyn == 321)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2870 of "src/main/bison/cml.y"  */
    {
  CmlLexeme elsetok = (CmlLexeme)((yystack.valueAt (2-(1))));
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  AElseIfExp eie = new AElseIfExp();
  eie.setLocation(combineLexLocation(extractLexLocation(elsetok), exp.getLocation()));
  List<AElseIfExp> res = new LinkedList<AElseIfExp>();
  res.add(eie);
  yyval = res;
};
  break;
    

  case 322:
  if (yyn == 322)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2880 of "src/main/bison/cml.y"  */
    {
  CmlLexeme elseif = (CmlLexeme)((yystack.valueAt (5-(1))));
  PExp elseIf = (PExp)((yystack.valueAt (5-(2))));
  PExp then = (PExp)((yystack.valueAt (5-(4))));
  List<AElseIfExp> tail = (List<AElseIfExp>)((yystack.valueAt (5-(5))));
  LexLocation loc = combineLexLocation(extractLexLocation(elseif), then.getLocation());
  AElseIfExp eie = new AElseIfExp();
  eie.setElseIf(elseIf);
  eie.setThen(then);
  eie.setLocation(loc);
  tail.add(eie);
  yyval = tail;
};
  break;
    

  case 323:
  if (yyn == 323)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2897 of "src/main/bison/cml.y"  */
    {
  CmlLexeme cases = (CmlLexeme)((yystack.valueAt (5-(1))));
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  ACasesExp bubbleUp = (ACasesExp)((yystack.valueAt (5-(4)))); // Others and Cases are taken care of
  CmlLexeme end = (CmlLexeme)((yystack.valueAt (5-(5))));
  LexLocation lexLoc = combineLexLocation(extractLexLocation(cases), extractLexLocation(end));
  bubbleUp.setExpression(exp);
  bubbleUp.setLocation(lexLoc);
  yyval = bubbleUp;
};
  break;
    

  case 327:
  if (yyn == 327)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2939 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patList = (List<PPattern>)((yystack.valueAt (4-(1))));
  PExp exp = (PExp)((yystack.valueAt (4-(3))));
  CmlLexeme semi = (CmlLexeme)((yystack.valueAt (4-(4))));
  LexLocation leftMost = extractLexLeftMostFromPatterns(patList);
  LexLocation loc = combineLexLocation(leftMost, extractLexLocation(semi));
  ACaseAlternative res = new ACaseAlternative();
  res.setPattern(patList);
  res.setLocation(loc);
  res.setCexp(exp);
  yyval = res;
};
  break;
    

  case 328:
  if (yyn == 328)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2963 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AUnaryPlusUnaryExp(location, exp);
};
  break;
    

  case 329:
  if (yyn == 329)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2970 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AUnaryMinusUnaryExp(location, exp);
};
  break;
    

  case 330:
  if (yyn == 330)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2977 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AAbsoluteUnaryExp(location, exp);
};
  break;
    

  case 331:
  if (yyn == 331)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2984 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AFloorUnaryExp(location, exp);
};
  break;
    

  case 332:
  if (yyn == 332)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2991 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new ANotUnaryExp(location, exp);
};
  break;
    

  case 333:
  if (yyn == 333)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2998 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new ACardinalityUnaryExp(location, exp);
};
  break;
    

  case 334:
  if (yyn == 334)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3005 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new APowerSetUnaryExp(location, exp);
};
  break;
    

  case 335:
  if (yyn == 335)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3012 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new ADistUnionUnaryExp(location, exp);
};
  break;
    

  case 336:
  if (yyn == 336)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3019 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new ADistIntersectUnaryExp(location, exp);
};
  break;
    

  case 337:
  if (yyn == 337)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3026 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AHeadUnaryExp(location, exp);
};
  break;
    

  case 338:
  if (yyn == 338)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3033 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new ATailUnaryExp(location, exp);
};
  break;
    

  case 339:
  if (yyn == 339)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3040 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new ALenUnaryExp(location, exp);
};
  break;
    

  case 340:
  if (yyn == 340)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3047 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AElementsUnaryExp(location, exp);
};
  break;
    

  case 341:
  if (yyn == 341)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3054 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AIndicesUnaryExp(location, exp);
};
  break;
    

  case 342:
  if (yyn == 342)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3061 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AReverseUnaryExp(location, exp);
};
  break;
    

  case 343:
  if (yyn == 343)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3068 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new ADistConcatUnaryExp(location, exp);
};
  break;
    

  case 344:
  if (yyn == 344)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3075 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AMapDomainUnaryExp(location, exp);
};
  break;
    

  case 345:
  if (yyn == 345)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3082 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AMapRangeUnaryExp(location, exp);
};
  break;
    

  case 346:
  if (yyn == 346)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3089 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new ADistMergeUnaryExp(location, exp);
};
  break;
    

  case 347:
  if (yyn == 347)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3096 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  yyval = new AMapInverseUnaryExp(location, exp);
};
  break;
    

  case 348:
  if (yyn == 348)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3108 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new APlusNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 349:
  if (yyn == 349)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3113 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new ATimesNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 350:
  if (yyn == 350)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3118 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new ASubstractNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 351:
  if (yyn == 351)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3123 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new ADivideNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 352:
  if (yyn == 352)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3128 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new ADivNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 353:
  if (yyn == 353)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3133 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new ARemNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 354:
  if (yyn == 354)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3138 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new AModNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 355:
  if (yyn == 355)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3143 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new ALessNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 356:
  if (yyn == 356)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3148 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new ALessEqualNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 357:
  if (yyn == 357)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3153 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new AGreaterNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 358:
  if (yyn == 358)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3158 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new AGreaterEqualNumericBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 359:
  if (yyn == 359)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3163 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AEqualsBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 360:
  if (yyn == 360)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3168 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ANotEqualBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 361:
  if (yyn == 361)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3173 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AOrBooleanBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 362:
  if (yyn == 362)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3178 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AAndBooleanBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 363:
  if (yyn == 363)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3183 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AImpliesBooleanBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 364:
  if (yyn == 364)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3188 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AEquivalentBooleanBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 365:
  if (yyn == 365)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3193 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AInSetBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 366:
  if (yyn == 366)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3198 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ANotInSetBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 367:
  if (yyn == 367)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3203 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASubsetBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 368:
  if (yyn == 368)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3208 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AProperSubsetBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 369:
  if (yyn == 369)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3213 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetUnionBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 370:
  if (yyn == 370)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3218 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetDifferenceBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 371:
  if (yyn == 371)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3223 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetIntersectBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 372:
  if (yyn == 372)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3228 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASeqConcatBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 373:
  if (yyn == 373)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3233 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 374:
  if (yyn == 374)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3238 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AMapUnionBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 375:
  if (yyn == 375)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3243 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new ADomainResToBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 376:
  if (yyn == 376)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3248 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new AModifyBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 377:
  if (yyn == 377)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3253 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new AModifyBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 378:
  if (yyn == 378)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3258 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new AModifyBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 379:
  if (yyn == 379)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3263 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new AModifyBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 380:
  if (yyn == 380)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3268 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(), ((PExp)((yystack.valueAt (3-(3))))).getLocation());
  yyval = new AModifyBinaryExp(loc, (PExp)((yystack.valueAt (3-(1)))), null, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 381:
  if (yyn == 381)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3276 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 382:
  if (yyn == 382)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3282 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(1))));
  AMapletExp hd = (AMapletExp)((yystack.valueAt (3-(3))));
  maplets.add(hd);
  yyval = maplets;
};
  break;
    

  case 383:
  if (yyn == 383)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3292 of "src/main/bison/cml.y"  */
    {
  PExp domValue = (PExp)((yystack.valueAt (3-(1))));
  PExp rngValue = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domValue.getLocation(), rngValue.getLocation());
  AMapletExp res = new AMapletExp(loc, domValue, rngValue);
  yyval = res;
};
  break;
    

  case 384:
  if (yyn == 384)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3305 of "src/main/bison/cml.y"  */
    {
  CmlLexeme mku = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(mku), extractLexLocation(rparen));
  ATupleExp res = new ATupleExp(loc, exprs);
  yyval = res;
};
  break;
    

  case 385:
  if (yyn == 385)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3319 of "src/main/bison/cml.y"  */
    {

  CmlLexeme mku = (CmlLexeme)((yystack.valueAt (4-(1))));
  LexNameToken name = getNameTokenFromMKUNDERNAME(mku);
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  LexLocation loc = combineLexLocation(extractLexLocation(mku), extractLexLocation((CmlLexeme)((yystack.valueAt (4-(4))))));
  ARecordExp res = new ARecordExp(loc, name, exprs);
  yyval = res;
};
  break;
    

  case 386:
  if (yyn == 386)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3342 of "src/main/bison/cml.y"  */
    {
  CmlLexeme l = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<ATypeBind> binds = (List<ATypeBind>)((yystack.valueAt (4-(2))));
  PExp body = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(l), body.getLocation());
  ALambdaExp res = new ALambdaExp(loc, binds, body, null, null);
  yyval = res;
};
  break;
    

  case 390:
  if (yyn == 390)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3380 of "src/main/bison/cml.y"  */
    {
  CmlLexeme isUnder = (CmlLexeme)((yystack.valueAt (5-(1))));
  PType type = (PType)((yystack.valueAt (5-(2))));
  PExp test = (PExp)((yystack.valueAt (5-(4))));
  CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (5-(5))));
  LexLocation loc = combineLexLocation(extractLexLocation(isUnder), extractLexLocation(rparen));
  AIsExp res = new AIsExp(loc, null, test, null);
  res.setBasicType(type);
  yyval = res;
};
  break;
    

  case 391:
  if (yyn == 391)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3391 of "src/main/bison/cml.y"  */
    {
  CmlLexeme isUnder = (CmlLexeme)((yystack.valueAt (6-(1))));
  PExp test = (PExp)((yystack.valueAt (6-(3))));
  PType type = (PType)((yystack.valueAt (6-(5))));
  CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (6-(6))));
  LexLocation loc = combineLexLocation(extractLexLocation(isUnder), extractLexLocation(rparen));
  AIsExp res = new AIsExp(loc, null, test, null);
  res.setBasicType(type);
  yyval = res;
};
  break;
    

  case 392:
  if (yyn == 392)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3406 of "src/main/bison/cml.y"  */
    {
  CmlLexeme preu = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (4-(4))));
  // RWL FIXME: Either this is right because we dedeuce the
  // function in a later phase where we know more or
  // the production above should be PREUNDER exp LPAREN expList RPAREN
  // however that introduces 36 reduce/reduce conflicts at this time.
  PExp function = null;
  LexLocation loc = combineLexLocation(extractLexLocation(preu), extractLexLocation(rparen));
  APreExp res = new APreExp(loc, function, exprs);
  yyval = res;
};
  break;
    

  case 393:
  if (yyn == 393)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3425 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 394:
  if (yyn == 394)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3429 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 396:
  if (yyn == 396)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3443 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 398:
  if (yyn == 398)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3448 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 399:
  if (yyn == 399)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3469 of "src/main/bison/cml.y"  */
    {
  yyval = new ANonDeterministicIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5))))), (PExp)((yystack.valueAt (5-(2)))), (PAction)((yystack.valueAt (5-(4)))), null);
};
  break;
    

  case 400:
  if (yyn == 400)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3473 of "src/main/bison/cml.y"  */
    {
  yyval = new ANonDeterministicIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))), (CmlLexeme)((yystack.valueAt (6-(5))))), (PExp)((yystack.valueAt (6-(2)))), (PAction)((yystack.valueAt (6-(4)))), (List<ANonDeterministicElseIfControlStatementAction>)((yystack.valueAt (6-(5)))));
};
  break;
    

  case 401:
  if (yyn == 401)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3480 of "src/main/bison/cml.y"  */
    {
  PAction thenStm = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), thenStm.getLocation());
  yyval = new ANonDeterministicElseIfControlStatementAction(location, (PExp)((yystack.valueAt (4-(2)))), thenStm);
};
  break;
    

  case 402:
  if (yyn == 402)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3489 of "src/main/bison/cml.y"  */
    {
  List<ANonDeterministicElseIfControlStatementAction> alts = new Vector<ANonDeterministicElseIfControlStatementAction>();
  alts.add((ANonDeterministicElseIfControlStatementAction)((yystack.valueAt (1-(1)))));
  yyval = alts;
};
  break;
    

  case 403:
  if (yyn == 403)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3495 of "src/main/bison/cml.y"  */
    {
  List<ANonDeterministicElseIfControlStatementAction> alts = (List<ANonDeterministicElseIfControlStatementAction>)((yystack.valueAt (2-(1))));
  alts.add((ANonDeterministicElseIfControlStatementAction)((yystack.valueAt (2-(2)))));
  yyval = alts;
};
  break;
    

  case 405:
  if (yyn == 405)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3515 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  PAction action = (PAction)((yystack.valueAt (3-(2))));
  yyval = new ABlockAction(location, null, action);
};
  break;
    

  case 406:
  if (yyn == 406)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3521 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  ADeclareStatementDeclareStatement dclStm = (ADeclareStatementDeclareStatement)((yystack.valueAt (4-(2))));
  PAction action = (PAction)((yystack.valueAt (4-(3))));
  yyval = new ABlockAction(location, dclStm, action);
};
  break;
    

  case 407:
  if (yyn == 407)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3531 of "src/main/bison/cml.y"  */
    {
  yyval = new ADeclareStatementDeclareStatement(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))),
					     (List<? extends PDefinition>)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 408:
  if (yyn == 408)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3539 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (1-(1)))));
  yyval = assignmentDefs;
};
  break;
    

  case 409:
  if (yyn == 409)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3545 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)((yystack.valueAt (3-(1))));
  if (assignmentDefs == null)
    assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (3-(3)))));
  yyval = assignmentDefs;
};
  break;
    

  case 410:
  if (yyn == 410)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3556 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(name.location, type.getLocation());
  AAccessSpecifierAccessSpecifier access = null;
  yyval = new AAssignmentDefinition(location, name, NameScope.LOCAL, false, null, access, type, null, null);
};
  break;
    

  case 413:
  if (yyn == 413)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3569 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 418:
  if (yyn == 418)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3591 of "src/main/bison/cml.y"  */
    {
  PStateDesignator stateDesignator = convertToStateDesignator((PDesignator)((yystack.valueAt (3-(1)))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(stateDesignator.getLocation(), exp.getLocation());
  yyval = new ASingleGeneralAssignmentControlStatementAction(location, stateDesignator, (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 419:
  if (yyn == 419)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3602 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (7-(7))));
  yyval = new AIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), action.getLocation()),
				     (PExp)((yystack.valueAt (7-(2)))), (PAction)((yystack.valueAt (7-(4)))), (List<? extends AElseIfControlStatementAction>)((yystack.valueAt (7-(5)))), action);
};
  break;
    

  case 420:
  if (yyn == 420)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3608 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (6-(6))));
  yyval = new AIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))), action.getLocation()),
				     (PExp)((yystack.valueAt (6-(2)))), (PAction)((yystack.valueAt (6-(4)))), null, action);
};
  break;
    

  case 421:
  if (yyn == 421)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3617 of "src/main/bison/cml.y"  */
    {
  List<AElseIfControlStatementAction> elseStms = new Vector<AElseIfControlStatementAction>();
  PAction thenStm = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), thenStm.getLocation());
  elseStms.add(new AElseIfControlStatementAction(location, (PExp)((yystack.valueAt (4-(2)))), thenStm));
  yyval = elseStms;
};
  break;
    

  case 422:
  if (yyn == 422)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3625 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (5-(5))));
  List<AElseIfControlStatementAction> elseStms = (List<AElseIfControlStatementAction>)((yystack.valueAt (5-(1))));
  LexLocation location = combineLexLocation(extractLastLexLocation(elseStms), extractLexLocation((CmlLexeme)((yystack.valueAt (5-(4))))));
  elseStms.add(0, new AElseIfControlStatementAction(location, (PExp)((yystack.valueAt (5-(3)))), action));
  yyval = elseStms;
};
  break;
    

  case 423:
  if (yyn == 423)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3637 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
    ACasesControlStatementAction cases = (ACasesControlStatementAction)((yystack.valueAt (5-(4))));
    cases.setLocation(location);
    cases.setExp((PExp)((yystack.valueAt (5-(2)))));
    yyval = cases;
};
  break;
    

  case 425:
  if (yyn == 425)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3656 of "src/main/bison/cml.y"  */
    {
  List<ACaseAlternativeAction> casesList = new Vector<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)((yystack.valueAt (1-(1)))));
  ACasesControlStatementAction cases = new ACasesControlStatementAction(null, null, casesList, null);
  yyval = cases;
};
  break;
    

  case 426:
  if (yyn == 426)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3663 of "src/main/bison/cml.y"  */
    {
  ACasesControlStatementAction cases = (ACasesControlStatementAction)((yystack.valueAt (3-(3))));
  cases.getCases().add((ACaseAlternativeAction)((yystack.valueAt (3-(1)))));
  yyval = cases;
};
  break;
    

  case 427:
  if (yyn == 427)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3672 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (3-(3))));
    List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
    yyval = new ACaseAlternativeAction(combineLexLocation(extractFirstLexLocation(patterns), action.getLocation()),
				    patterns, (PAction)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 429:
  if (yyn == 429)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3683 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  yyval = new AReturnControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation()), exp);
};
  break;
    

  case 437:
  if (yyn == 437)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3716 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)((yystack.valueAt (1-(1)))));
  yyval = patterns;
};
  break;
    

  case 438:
  if (yyn == 438)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3722 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (Vector<PPattern>)((yystack.valueAt (3-(1))));
  patterns.add((PPattern)((yystack.valueAt (3-(3)))));
  yyval = patterns;
};
  break;
    

  case 439:
  if (yyn == 439)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3731 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexNameToken lnt = extractLexNameToken(lexeme);
  AIdentifierPattern res = new AIdentifierPattern();
  res.setName(lnt);
  res.setLocation(lnt.getLocation());
  yyval = res;
};
  break;
    

  case 441:
  if (yyn == 441)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3746 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (1-(1))));
  if (exp instanceof AIntLiteralSymbolicLiteralExp) {
    AIntLiteralSymbolicLiteralExp intExp = (AIntLiteralSymbolicLiteralExp)exp;
    AIntegerPattern res = new AIntegerPattern();
    res.setLocation(intExp.getLocation());
    res.setValue(intExp.getValue());
    yyval = res;
  } else {
    throw new RuntimeException("Unhandled expression type in pattern. ("+exp.getClass()+")"); // TODO RWL
  }
};
  break;
    

  case 444:
  if (yyn == 444)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3769 of "src/main/bison/cml.y"  */
    {
  List<? extends PPattern> plist = null;
  LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (2-(1)))));
  yyval = new ARecordPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), (CmlLexeme)((yystack.valueAt (2-(2))))), null, false, name, plist);
};
  break;
    

  case 445:
  if (yyn == 445)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3775 of "src/main/bison/cml.y"  */
    {
    List<? extends PPattern> plist = (List<? extends PPattern>)((yystack.valueAt (4-(3))));
    LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (4-(1)))));
    yyval = new ARecordPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4))))), null, false, name, plist);
};
  break;
    

  case 446:
  if (yyn == 446)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3787 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 447:
  if (yyn == 447)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3791 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 448:
  if (yyn == 448)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3798 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));
  yyval = new ASetBind(location, pattern, exp);
};
  break;
    

  case 449:
  if (yyn == 449)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3808 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));
  yyval = new ATypeBind(location, pattern, type);
};
  break;
    

  case 450:
  if (yyn == 450)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3818 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)((yystack.valueAt (1-(1)))));
  yyval = binds;
};
  break;
    

  case 451:
  if (yyn == 451)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3824 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (3-(1))));
  binds.add((PMultipleBind)((yystack.valueAt (3-(3)))));
  yyval = binds;
};
  break;
    

  case 452:
  if (yyn == 452)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3833 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 454:
  if (yyn == 454)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3841 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));;
  yyval = new ASetMultipleBind(location, patterns, exp);
};
  break;
    

  case 455:
  if (yyn == 455)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3851 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));;
  yyval = new ATypeMultipleBind(location, patterns, type);
};
  break;
    

  case 456:
  if (yyn == 456)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3861 of "src/main/bison/cml.y"  */
    {
  ATypeBind tb = (ATypeBind)((yystack.valueAt (1-(1))));
  List<ATypeBind> res = new LinkedList<ATypeBind>();
  res.add(tb);
  yyval = res;
};
  break;
    

  case 457:
  if (yyn == 457)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3868 of "src/main/bison/cml.y"  */
    {
  ATypeBind hd = (ATypeBind)((yystack.valueAt (3-(3))));
  List<ATypeBind> tbl = (List<ATypeBind>)((yystack.valueAt (3-(1))));
  tbl.add(hd);
  yyval = tbl;
};
  break;
    



/* Line 360 of cmlskeleton.java.m4  */
/* Line 5828 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -614;
  public static final short yypact_[] =
  {
       910,   -51,   -30,   255,   -15,   446,   864,   446,   146,   367,
    -614,  -614,  -614,  -614,  -614,   367,   214,  -614,  -614,  -614,
    -614,   150,   183,  -614,  -614,   223,  -614,  -614,   723,  -614,
     327,   401,   -15,  -614,  -614,  -614,  -614,  -614,   259,  -614,
     293,   547,  -614,   384,   336,  -614,   438,  -614,  -614,  -614,
    -614,   367,  -614,   425,    52,   255,  3257,  -614,   255,   397,
    -614,   255,   255,  1101,   294,  -614,   446,    47,  3257,   489,
      29,  -614,    27,  -614,  -614,  -614,  -614,  -614,  -614,    33,
    -614,  -614,  -614,    93,    16,  -614,   446,   771,  -614,   694,
     494,   506,   513,   255,   526,   294,  -614,  2318,    60,  -614,
     327,  -614,  3257,  3083,  2907,   661,  3257,  3257,  3257,  3257,
    3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,
    3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,   560,   564,
     443,   122,   577,   599,   443,   443,   443,  -614,    58,  3568,
    -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,
     723,  -614,  -614,  -614,   723,  1101,   161,  1101,   479,  1101,
    1101,  1101,  1101,  1101,   430,  -614,  -614,  -614,  -614,  -614,
    -614,  -614,  -614,   875,  -614,  -614,  -614,  -614,  -614,  -614,
     255,   255,   357,  -614,  1101,  1111,  3638,   443,   443,  -614,
    3257,  1101,  3257,  1101,  -614,   443,  -614,  1101,   533,  -614,
     864,   446,   324,   565,   771,  -614,  -614,  -614,  -614,  -614,
    2642,    57,   831,  -614,  -614,  -614,   255,   255,   255,   257,
     255,    80,    24,    24,    24,  1844,    24,    24,   294,   539,
    3257,    24,   294,    24,  3257,  3257,  -614,    24,   255,  3708,
    -614,    13,  3778,  -614,   606,    72,  3338,   107,   625,    53,
    -614,  -614,   -13,  -614,  3879,  2317,  5371,  5371,  5371,  5371,
    5371,  5371,  5371,  5371,  5371,  5371,  5371,  5371,  5371,  5371,
    5371,  5371,  5371,  5371,  5371,  5371,  3257,  3257,  -614,  -614,
     504,  -614,  -614,  -614,    -9,  3257,   149,   665,  3257,   255,
    -614,   314,   102,  -614,  -614,  -614,   285,   136,   542,  -614,
    -614,  -614,  3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,
    3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,
    3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,  3257,
    3257,  3257,  3257,  3257,  3257,  3257,  3257,   929,  1101,  1101,
     820,   660,   720,   784,   875,   326,   326,   601,   609,  1101,
    1101,  1101,  1101,   308,   328,   294,   294,   294,   972,   304,
     875,   988,  -614,  -614,   561,    67,  5240,   666,  5240,   939,
      68,   457,   875,   621,   575,    17,   634,  -614,  -614,  -614,
     717,  -614,  3257,   647,  -614,   726,  -614,  -614,   744,   748,
     255,  2585,   727,  -614,  -614,  -614,  -614,   762,   255,   767,
     661,  3257,  3257,   809,  3170,   185,   294,   612,  3257,  1536,
    -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,
    -614,  -614,  -614,  -614,   462,   818,   722,   729,  -614,  2642,
    -614,   382,  -614,  -614,   416,   420,   733,   421,   816,  -614,
    -614,  -614,   112,   121,  1649,  -614,  -614,   434,  -614,   217,
     821,  1925,  -614,   650,  -614,  5240,  5240,  2318,  -614,  -614,
    -614,   443,  -614,  -614,   443,  3257,  3257,  -614,  3257,   443,
    1101,  3257,   661,  3257,   443,    82,  5240,    88,  1101,  3257,
     443,  2458,  3257,   743,   751,  3257,   117,   393,  3257,   443,
    1101,  3257,   443,  3257,  3257,  3257,  5240,  -614,  -614,  -614,
    -614,  3912,  5371,  -614,  -614,  -614,  -614,  -614,  -614,  -614,
    -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,
    5371,  -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,  -614,
    -614,  -614,   326,   326,  -614,  1111,  1101,  1101,  -614,  -614,
     326,   824,  -614,   326,  -614,  -614,  -614,  -614,  -614,   443,
    -614,  1101,  1101,  -614,  -614,   443,  -614,  3257,  3257,  -614,
     443,  1101,   126,  1101,  3257,   758,   817,  -614,    73,   864,
    5240,   712,  -614,   324,   255,   255,   444,   647,   255,   255,
     255,  1856,   480,  -614,   481,  2642,   448,   664,   819,  -614,
     857,  3257,   502,   255,     8,  3367,  2388,   255,  -614,  5240,
    2642,   269,  3257,   227,  -614,   620,   724,  2759,  -614,  2642,
    2642,  2642,  1439,  2642,  2642,   411,   689,  3257,  2642,   294,
    2642,  3257,  3257,  -614,  3257,  2642,   874,  -614,  1559,    24,
     255,    24,    24,   868,    24,   255,   294,    24,    24,   443,
    -614,   539,   539,    24,   766,    21,    15,  3949,  5240,  4050,
    -614,   103,  -614,  -614,  3498,   354,  -614,    20,  -614,  -614,
     875,  5371,  -614,  1101,  4083,   877,   878,  4120,  -614,  3257,
    5240,  -614,   875,  5371,  -614,  5371,  5240,  5371,  -614,   488,
     326,   326,   843,   875,   875,   880,  5240,  5240,   495,   875,
     443,  -614,   187,   875,  5240,   746,  3257,  -614,  1188,   533,
    -614,  1101,  -614,   503,   509,   881,    64,  -614,  -614,  -614,
    -614,  -614,  2642,   690,  2642,  1935,  -614,  -614,   664,  -614,
     255,   817,  -614,  4251,   885,   535,  2642,  2642,  2642,   443,
     441,  -614,   462,  -614,   339,   671,  -614,   752,  5240,  2642,
    -614,   255,   892,  2642,  -614,  -614,  -614,   886,   189,  1720,
    -614,  -614,   890,   643,   897,  1995,  -614,   650,  -614,  5240,
    5240,  5240,  -614,  2231,   554,   739,  -614,   768,  -614,  1026,
    1654,   906,  1863,   610,    31,  -614,  -614,    39,   902,  -614,
    -614,  -614,  3257,  -614,  3257,  -614,  -614,  3257,  3257,  3257,
    -614,  -614,   908,  -614,  3257,  1017,  -614,  3257,  3257,  -614,
    4280,  -614,  3257,  -614,  1101,   129,   443,  2996,  1101,  5240,
     378,   520,   828,   727,   786,   555,  2642,  2642,   760,  -614,
     647,  2056,  -614,  2122,  -614,  -614,   505,  -614,  -614,   761,
    2642,  -614,  1469,  1646,    84,   798,    74,  -614,   255,   269,
    -614,   269,  -614,  3257,  -614,   628,   255,  -614,   294,   294,
    2642,   532,  2642,   765,  2642,  2642,  -614,  -614,  -614,  -614,
    3257,  -614,    24,    24,  3257,  -614,   539,  4411,  4440,  4571,
    4600,  5371,  3257,  4731,  -614,  4760,  4891,  -614,  5240,   875,
    -614,   130,  -614,  -614,   819,  5240,   875,  1224,  1435,   126,
     819,  3257,  3257,  2141,  2160,   936,  -614,   942,   946,  1101,
      66,  2179,  -614,  3257,  -614,   346,  3257,  2642,   791,  -614,
     937,   443,  2642,  -614,  -614,   132,   133,  5240,   945,   641,
     215,    35,  -614,   827,    63,  -614,   815,  -614,  2231,  4920,
    2239,  -614,  2689,  -614,  -614,  -614,  -614,  3257,  2829,  -614,
    -614,  -614,  -614,   817,   161,   875,   161,   875,   333,   817,
    5240,  5240,  -614,  -614,  2642,  3257,  3257,   875,  2642,   294,
    -614,  5051,  -614,  -614,  5080,  -614,  3257,  2642,  2642,  -614,
    2231,  -614,   269,  -614,   783,  2642,   294,  2642,  2642,   792,
    2642,  -614,  -614,  -614,  3498,  -614,   919,  -614,   883,  -614,
    -614,   141,   143,  -614,    44,  2642,  2642,  5211,  -614,  1590,
    -614,   963,  2212,   105,  -614,  -614,   833,  -614,  -614,   255,
    -614,  2608,  -614,  -614,   819,  -614,  -614,  -614,  -614,  -614,
    2642,  2231,  2231,  2642,  -614,  2642,  -614,   795,  2642,   723,
     817,  -614,  2231,  -614,   968,  -614,  -614,  2642,  -614
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,     0,     0,     0,   128,   154,   167,   214,     0,     2,
       5,     7,     8,     9,    10,     4,   139,   140,   142,   143,
     144,     0,     0,   466,   467,   120,   121,   124,   468,   458,
     123,     0,   129,   130,   163,   164,   165,   166,   156,   157,
       0,     0,   205,   206,     0,   215,   216,   219,   220,     1,
       6,     3,   141,     0,     0,     0,     0,   463,     0,     0,
     459,     0,     0,     0,     0,   131,   155,     0,     0,     0,
       0,   314,     0,   315,   198,   313,   209,   441,   312,     0,
     434,   435,   436,   207,     0,   222,   217,     0,    11,     0,
       0,     0,     0,     0,     0,     0,    12,    14,     0,   125,
       0,   122,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   281,     0,   279,
     311,   284,   285,   286,   287,   304,   305,   306,   307,   308,
     310,   460,   462,   461,   469,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   181,   184,   185,   186,   187,   188,
     189,   190,   191,   127,   169,   180,   192,   193,   170,   133,
       0,     0,   132,   158,     0,     0,     0,     0,     0,   444,
       0,     0,     0,     0,   208,     0,   230,     0,     0,   218,
     167,   247,   273,     0,   146,   148,   149,   150,   151,   152,
       0,     0,     0,    41,    44,    43,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    32,     0,     0,     0,
     296,     0,   279,   291,     0,     0,   279,     0,   381,     0,
     318,   319,     0,   316,     0,     0,   328,   329,   330,   331,
     332,   333,   334,   335,   336,   337,   338,   339,   340,   341,
     342,   343,   344,   345,   346,   347,     0,     0,   440,   439,
       0,   433,   432,   456,     0,     0,     0,     0,     0,     0,
     437,     0,     0,   450,   452,   453,     0,     0,     0,   446,
     447,   464,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   176,   177,   175,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   160,   181,
     201,   161,   199,   442,     0,     0,   212,     0,   213,     0,
       0,     0,     0,   180,     0,   239,     0,   153,   251,   250,
     246,   248,     0,     0,   272,   274,   145,   147,     0,     0,
       0,     0,   264,    52,    53,    54,    55,     0,     0,     0,
       0,     0,     0,     0,     0,   467,     0,     0,     0,     0,
      71,    73,    74,    77,   393,    75,    76,   396,   414,   413,
     394,   395,   398,   397,    78,     0,     0,    45,    47,     0,
      42,     0,    38,    40,     0,     0,     0,     0,     0,    19,
      18,    23,   467,     0,     0,    22,    17,     0,   119,     0,
       0,     0,    24,    28,    26,    30,    29,    13,   126,   282,
     297,     0,   300,   292,     0,     0,     0,   301,     0,     0,
       0,     0,     0,     0,     0,     0,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   280,   359,   349,   348,
     350,     0,   352,   351,   353,   354,   355,   356,   357,   358,
     360,   361,   362,   363,   364,   365,   366,   367,   368,   369,
     370,   371,   372,   373,   374,   375,   376,   377,   378,   379,
     380,   168,   197,   195,   174,     0,     0,     0,   182,   183,
     196,   172,   173,   194,   135,   134,   138,   137,   136,     0,
     159,     0,     0,   200,   162,     0,   445,     0,     0,   231,
       0,     0,     0,     0,     0,     0,     0,   238,     0,   167,
     278,     0,   277,   275,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    99,     0,     0,    78,     0,   239,   263,
       0,     0,     0,     0,     0,     0,     0,     0,   428,   429,
       0,     0,     0,     0,    79,     0,     0,     0,    16,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    70,     0,     0,     0,    48,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     114,     0,     0,     0,     0,     0,     0,     0,   383,     0,
     382,     0,   283,   317,     0,     0,   325,     0,   384,   385,
     449,   386,   457,     0,     0,     0,     0,     0,   392,     0,
     454,   438,   455,   288,   451,   289,   448,   290,   465,     0,
     179,   178,     0,   203,   202,   438,   210,   211,     0,   232,
       0,   224,     0,   236,   240,     0,     0,   221,     0,   234,
     249,     0,   276,     0,     0,     0,     0,   408,   103,   102,
     101,   405,     0,     0,     0,     0,   270,   271,   265,   266,
       0,     0,   430,     0,     0,     0,     0,     0,     0,     0,
       0,   415,     0,    57,     0,     0,    84,    81,    83,     0,
      80,     0,     0,     0,    62,    61,    92,     0,     0,     0,
      94,    60,     0,     0,     0,     0,    63,    67,    65,    69,
      68,   418,    49,    50,     0,     0,    15,     0,    39,     0,
       0,     0,     0,     0,     0,    27,    20,     0,     0,   117,
      25,   298,     0,   293,     0,   295,   302,     0,     0,     0,
     320,   323,     0,   326,     0,     0,   387,     0,     0,   390,
       0,   171,     0,   443,     0,     0,     0,     0,     0,   243,
       0,     0,     0,   264,   235,   410,     0,     0,     0,   407,
       0,     0,   100,     0,   406,   267,   268,   431,    56,     0,
       0,   404,     0,     0,     0,   425,     0,   417,     0,     0,
      86,     0,    88,     0,    58,     0,     0,    59,     0,     0,
       0,     0,     0,     0,     0,     0,    46,    34,    35,    36,
       0,    33,     0,     0,     0,   115,     0,     0,     0,     0,
       0,   321,     0,     0,   391,     0,     0,   309,   204,   233,
     225,     0,   228,   229,   239,   227,   237,     0,     0,     0,
     239,     0,     0,     0,     0,     0,   409,    72,     0,     0,
       0,     0,   399,     0,   402,     0,     0,     0,     0,   423,
       0,     0,     0,   416,    89,     0,     0,    82,     0,     0,
       0,     0,    66,     0,     0,    98,     0,    64,    51,     0,
       0,    21,     0,   118,   299,   294,   303,     0,     0,   327,
     388,   389,   226,   242,   257,   255,   256,   254,     0,     0,
     412,   411,   108,   107,     0,     0,     0,   269,     0,     0,
     106,     0,   400,   403,     0,   420,     0,     0,     0,   426,
     427,    85,     0,    87,     0,     0,     0,     0,     0,     0,
       0,    31,    37,   116,     0,   324,   245,   241,     0,   253,
     110,     0,     0,   113,     0,     0,     0,     0,   419,     0,
      90,     0,     0,     0,    96,    93,     0,    91,   322,     0,
     223,     0,   261,   262,   239,   260,   258,   259,   105,   104,
       0,   401,   421,     0,   424,     0,   109,     0,     0,   244,
     242,   112,   422,   111,     0,    97,   252,     0,    95
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -614,  -614,   973,    86,  -614,  -614,  -614,  -218,  -194,   364,
    -614,   785,  -614,  -614,   571,  -614,   467,  -614,   398,  -583,
     162,  -614,  -614,   297,  -614,  -614,   321,  -614,  -613,  -614,
    -614,   952,   -52,   167,  -614,  -614,   981,    36,  -614,  -614,
     998,  -614,   811,   -39,   670,  -614,   955,    69,   572,   887,
    -176,  -614,  -614,   521,   491,  -339,   663,   691,   950,  -614,
     995,   699,   954,  -614,  -614,  -614,  1000,   171,  -614,   483,
    -614,  -614,   365,  -542,  -614,    38,  -555,  -614,  -614,  -614,
    -191,  -614,  -614,  -614,  -614,   250,  -614,  -614,   347,  -614,
    -614,   493,  -614,  -102,     2,   -34,  -614,   669,   598,  -614,
      87,  -614,  -614,   419,  -614,  -614,  -614,   607,  -614,  -614,
    -614,  -614,  -614,    91,  -614,   178,  -614,    98,   101,  -614,
    -614,  -550,  -614,  -614,  -614,  -572,  -614,  -614,  -614,   188,
    -614,  -614,  -614,  -614,  -116,   -25,  -109,  -614,  -614,  -614,
    -614,  -614,   633,  -119,  -123,   608,  -614,  -614,  -614,    -3,
     118,     0
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,    96,    97,   431,   432,
     212,   213,   214,   427,   428,   762,   581,   603,   604,   914,
     915,   410,   582,   583,   411,   412,   623,   449,   450,    13,
      25,    26,    98,   433,    14,    32,    33,   182,    15,    16,
      17,    88,   203,   204,   205,    38,    39,   376,   360,   174,
     175,   176,   177,    75,   361,   362,   550,   206,    42,    43,
     250,   207,    45,    46,    47,    48,   251,   692,   884,   198,
     370,   813,   375,   566,   567,   986,   987,  1010,   208,   380,
     377,   378,   379,   812,  1014,   588,   589,   718,   719,   720,
     209,   384,   385,   138,   476,   140,    78,   252,   253,   141,
     790,   142,   655,   656,   143,   144,   247,   248,   145,   146,
     147,   148,   149,   413,   414,   904,   905,   415,   416,   585,
     706,   572,   417,   730,   418,   419,   420,   908,   421,   834,
     835,   422,   423,   590,   290,   281,   291,   282,    80,    81,
      82,   298,   299,   283,   292,   293,   294,   295,   284,   150,
      29,   100
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -469;
  public static final short
  yytable_[] =
  {
        28,   241,   245,    30,   439,   440,   441,    77,   445,   446,
     381,   697,   296,   452,   280,   454,    79,   300,   737,   457,
     297,   373,   553,   434,   435,   731,   437,   707,   778,   779,
     195,   460,   196,    89,   190,    90,    91,    92,    93,   781,
     192,   219,   783,   188,   794,   189,   721,   864,   471,   863,
     215,    28,    28,   977,   184,    30,    21,    94,   139,   154,
     190,    89,  1020,    90,    91,    92,    93,   564,   237,   726,
     186,    77,   819,   301,    40,    41,    44,    22,   364,   365,
      79,   185,   556,   559,   958,    94,   371,   195,   909,   196,
      28,   979,    31,   238,   959,    50,    77,   658,   912,   463,
      77,    77,    77,   659,   239,   242,   246,  -167,   254,   255,
     256,   257,   258,   259,   260,   261,   262,   263,   264,   265,
     266,   267,   268,   269,   270,   271,   272,   273,   274,   275,
     786,   221,   668,  1027,   467,    40,   285,    50,   479,   472,
     690,  -133,   691,   480,   880,   942,    49,   971,   973,    23,
     636,   910,    41,    77,    77,    44,  1018,    53,  1019,    24,
     472,    77,   784,   482,   425,   302,   827,   492,   782,   565,
      27,   197,   489,   215,   475,   477,   151,    28,    28,   153,
     353,   354,   191,  -167,  -167,   338,   486,  -167,   193,   865,
      54,   492,   366,   355,   368,    95,   576,   355,   356,   357,
    -167,   806,   356,   357,   592,   339,   355,   424,   470,   600,
     302,   356,   357,    28,    28,    28,   820,    28,   849,   489,
     560,    99,    27,    95,   302,   355,   489,   444,   698,   286,
     356,   357,   451,   807,   302,    28,   455,   456,  -167,  -167,
     302,   483,   355,   426,   976,   639,     5,   356,   357,   491,
     787,   739,   438,   933,   492,   492,    55,   484,   494,   468,
      99,   443,     6,     7,   447,   436,   913,   355,   453,   302,
     896,   383,   356,   357,  -133,    34,    35,    36,    37,  -133,
    -133,   489,   489,   355,   972,   972,   487,   481,   356,   357,
     238,   478,    66,   302,   373,   302,   165,   166,   167,   168,
     169,   170,   171,   172,   496,   497,   498,   499,   500,   501,
     502,   503,   504,   505,   506,   507,   508,   509,   510,   511,
     512,   513,   514,   515,   516,   517,   518,   519,   520,   521,
     522,   523,   524,   525,   526,   527,   528,   529,   530,   584,
     553,   646,   943,   601,   602,   644,   651,   806,   949,   551,
     962,   355,    23,   839,   350,   840,   356,   357,   791,   734,
     735,   662,    24,   351,   280,   657,    77,   640,    68,   641,
       1,   382,     2,   671,   903,    79,   736,   355,   700,   988,
     703,   704,   356,   357,   570,   601,   602,    28,   586,  1000,
     629,   546,   547,   548,   989,    28,   347,     3,     4,   725,
      67,   179,   338,   595,   596,   458,   599,    56,    64,    57,
     607,   767,   348,   769,   770,   630,   772,    83,   236,   775,
     776,   792,   339,   278,   631,   780,   424,    77,   632,   634,
      77,  -167,   493,   682,    87,    77,   488,   492,    77,   685,
      77,   773,   605,    84,    69,    70,    77,    79,    71,   630,
     180,   688,   705,   630,   630,    77,   837,    68,    77,   552,
      62,   279,    56,   181,    57,   544,   489,   647,   648,   490,
     649,    86,  1030,   652,   838,   654,    56,   630,    57,    62,
      62,   661,    63,   887,   664,    58,    59,   667,   712,   714,
     670,   152,   801,   673,    60,   675,   676,   677,   545,    73,
      74,    61,   155,   187,   156,   157,    34,    35,    36,    37,
     724,   816,   278,   713,   238,    77,   777,   817,   179,   355,
     216,    77,   347,   158,   356,   357,    77,   159,   160,   161,
     162,   163,   217,    69,    70,   630,   630,    71,   348,   218,
      58,    59,   630,   830,   349,   669,   657,   845,   350,    60,
     279,   624,   220,  -167,    58,    59,    61,   351,    99,   686,
     687,    68,   855,    60,   352,   624,   694,   180,   630,   386,
      61,    28,    28,   764,   276,    28,    28,    28,   277,   349,
     181,   805,   424,   350,   178,   638,   341,   238,    73,    74,
      28,   288,   351,   723,   732,   359,   355,   424,   752,   352,
    -468,   356,   357,  -468,   738,    77,   424,   424,   424,   489,
     424,   424,   561,   289,   749,   424,   891,   424,   862,   755,
     836,    77,   586,   759,   760,   888,   761,    28,    34,    35,
      36,    37,    28,   462,    74,   173,   918,    69,    70,   179,
     374,    71,   383,   630,   930,   931,   448,   489,   748,   975,
     804,   753,   919,   469,    72,   757,    77,    62,   892,   478,
     899,   630,   165,   166,   167,   168,   169,   170,   171,   172,
      18,   800,   774,   557,   630,    68,   178,   409,   178,   485,
     178,   178,   178,   178,   178,   841,    18,   842,   180,   495,
     349,    19,    73,    74,   350,    77,   535,   881,   809,    20,
     200,   181,   210,   351,   211,   178,   178,    19,   538,   424,
     352,   424,   178,   555,   178,    20,   539,    28,   178,   923,
     826,   716,   717,   424,   424,   424,     5,   337,   562,   340,
     563,   342,   343,   344,   345,   346,   424,    56,    28,    57,
     424,   568,     6,     7,   349,   708,   709,   710,   350,   201,
     569,    69,    70,   202,   571,    71,   358,   351,   536,   573,
     236,   236,   236,   367,   352,   369,   236,   236,   249,   372,
     574,   741,    77,   236,   575,   236,   591,   200,   236,   222,
     223,   224,   355,   587,   867,   225,   868,   356,   357,   869,
     870,   871,    99,   593,   852,   857,   873,   226,   606,   875,
     876,   227,   836,     5,   878,   355,    73,    74,   349,   885,
     356,   357,   350,   424,   424,    58,    59,   356,   357,     6,
       7,   351,   537,   597,    60,   625,   201,   424,   352,   626,
     202,    61,   578,   579,   580,   732,   425,   200,   534,   429,
     633,   211,   635,    28,   349,   917,   642,   424,   350,   424,
     665,   424,   424,   991,   992,   966,   967,   351,   666,   178,
     178,   351,   929,     5,   352,   695,   932,   701,   696,   564,
     178,   178,   178,   178,   938,   722,   754,    77,   742,     6,
       7,   765,   178,   771,   920,   921,   201,   924,   494,   802,
     202,   797,   798,   950,   951,   803,   628,  1011,   818,   349,
     392,   808,   829,   350,   424,   961,   846,   843,   964,   424,
     532,   533,   351,     1,   848,     2,   228,   229,   851,   352,
     860,   540,   541,   542,   543,   853,   856,   866,   230,   231,
     232,   233,   872,   234,   235,   889,  1012,  1013,   565,   984,
       3,     4,     5,   400,   531,   401,   558,   895,   900,   402,
     911,   424,   926,   349,   954,   424,   955,   350,     6,     7,
     956,   968,   974,   349,   424,   424,   351,   350,   997,   980,
    1001,  1009,   424,   352,   424,   424,   351,   424,   978,  1006,
      23,  1025,  1034,   352,  1028,   732,  1037,   403,    51,   404,
      24,   178,   424,   424,   768,   994,   349,   430,   627,   178,
     350,   740,   155,   916,   156,   157,  1029,   101,   424,   351,
     822,   178,  1003,    65,    52,   387,   352,   424,   287,   549,
     424,   183,   424,   158,   554,   424,   679,   159,   160,   161,
     162,   163,   874,   194,   424,   549,    76,   222,   223,   224,
     199,   349,   367,   225,    85,   350,    34,    35,    36,    37,
     660,   699,   715,   858,   351,   226,   178,   178,   178,   227,
     948,   352,   672,   890,   814,   825,   702,   733,  1036,   594,
     653,  1008,   178,   178,   793,   650,   744,   745,   746,  1015,
     750,   751,   178,   963,   178,   756,  1016,   758,   236,  1017,
     236,   236,   763,   236,   645,   359,   236,   236,     0,   969,
     674,   236,     0,     0,     0,     0,     0,     0,   680,   681,
       0,     0,     0,     0,     0,   155,     0,   156,   157,     0,
       0,     0,     0,   683,   684,   155,     0,   156,   157,     0,
       0,     0,     0,   689,    74,   693,   158,     0,     0,     0,
     159,   160,   161,   162,   163,     0,   158,     0,     0,     0,
     159,   160,   161,   162,   163,     0,     0,     0,     0,     0,
       0,     0,   165,   166,   167,   168,   169,   170,   171,   172,
       0,     0,     0,     0,   228,   229,     0,     0,     0,   821,
       0,   823,     0,     0,   178,     0,   230,   231,   232,   233,
       0,   234,   235,   831,   832,   833,     0,     0,     0,     0,
     178,     0,   155,     0,   810,   157,   844,     0,   164,     0,
     847,     0,     0,     0,     0,     0,     0,     0,   359,   178,
       0,     0,   178,   158,     0,     0,     0,   159,   160,   161,
     162,   163,     0,     0,     0,   795,     0,     0,   155,     0,
     944,   157,     0,     0,     0,     0,     0,    74,     0,     0,
       0,   236,   236,     0,     0,     0,     0,    74,     0,   158,
       0,     0,     0,   159,   160,   161,   162,   163,     0,     0,
     811,     0,     0,   815,     0,   165,   166,   167,   168,   169,
     170,   171,   172,   893,   894,   165,   166,   167,   168,   169,
     170,   171,   172,     0,     0,   164,     0,   901,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   922,     0,   925,
       0,   927,   928,     0,     0,   178,     0,     0,     0,   178,
       0,   164,     0,     0,    74,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   165,   166,   167,   168,   169,   170,   171,   172,
      74,     0,     0,     0,   965,     0,   879,     0,     0,   970,
     886,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   165,   166,
     167,   168,   169,   170,   171,   172,     0,     0,   178,   178,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     178,   990,     0,     0,     0,   993,     0,     0,     0,     0,
       0,     0,     0,     0,   998,   999,     0,     0,     0,     0,
       0,     0,  1002,     0,  1004,  1005,     0,  1007,     0,   155,
       0,   946,   157,   102,     0,     0,   103,     0,     0,   945,
     947,     0,  1021,  1022,     0,   104,     0,     0,     0,     0,
     158,   957,     0,   902,   159,   160,   161,   162,   163,     0,
     609,   610,   611,     0,     0,     0,   612,  1031,     0,     0,
    1032,     0,  1033,     0,     0,  1035,     0,   903,   613,   105,
       0,   106,   614,     0,  1038,   107,     0,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,     0,     0,   128,
     129,     0,     0,    71,   130,     0,    23,   131,   132,   133,
     608,     0,   164,     0,     0,     0,   442,   609,   610,   611,
       0,     0,     0,   612,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   766,     0,   613,     0,     0,     0,   614,
     609,   610,   611,     0,     0,     0,   612,   134,   135,   136,
     137,    74,     0,     0,    73,    74,     0,     0,   613,     0,
       0,     0,   614,     0,  1024,   180,     0,     0,     0,     0,
       0,   609,   610,   611,     0,     0,     0,   612,   181,   165,
     166,   167,   168,   169,   170,   171,   172,   615,   229,   613,
       0,     0,   616,   614,     0,     0,   747,     0,     0,   617,
     618,   619,   620,     0,   621,   622,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   303,   609,   610,   611,
       0,     0,     0,   612,     0,   222,   223,   224,     0,     0,
       0,   225,     0,     0,     0,   613,     0,     0,     0,   614,
       0,   859,     0,   226,   615,   229,   304,   227,     0,   616,
       0,     0,     0,     0,     0,     0,   617,   618,   619,   620,
       0,   621,   622,     0,     0,     0,     0,   615,   229,     0,
     906,   907,   616,     0,     0,     0,     0,   305,   306,   617,
     618,   619,   620,     0,   621,   622,     0,   303,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   615,   229,
       0,     0,     0,   616,     0,     0,     0,     0,     0,     0,
     617,   618,   619,   620,     0,   621,   622,   304,   308,   309,
     310,   311,   312,   313,   314,   315,   316,   317,   318,   319,
     320,   321,   322,   323,   324,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,     0,   305,   306,
       0,     0,     0,     0,   615,   229,     0,     0,     0,   616,
       0,     0,   228,   229,     0,     0,   617,   618,   619,   620,
       0,   621,   622,   637,   230,   231,   232,   233,     0,   234,
     235,     0,     0,     0,     0,     0,     0,     0,     0,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   102,     0,
       0,   103,     0,     0,     0,     0,     0,   609,   610,   611,
     104,   711,     0,   612,   222,   223,   224,     0,     0,     0,
     225,     0,     0,     0,   850,   613,     0,     0,     0,   614,
     861,     0,   226,     0,     0,     0,   227,     0,     0,     0,
       0,     0,     0,     0,   105,     0,   106,     0,     0,     0,
     107,     0,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   303,     0,   128,   129,     0,     0,    71,   130,
       0,    23,   131,   132,   133,     0,   609,   610,   611,     0,
     824,   442,   612,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   304,     0,   613,     0,     0,     0,   614,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   134,   135,   136,   137,     0,     0,     0,    73,
      74,     0,     0,   305,   306,     0,     0,     0,     0,     0,
     180,     0,   303,     0,   615,   229,     0,     0,     0,   616,
       0,   228,   229,   181,     0,     0,   617,   618,   619,   620,
       0,   621,   622,   230,   231,   232,   233,     0,   234,   235,
       0,     0,   304,     0,   308,   309,   310,   311,   312,   313,
     314,   315,   316,   317,   318,   319,   320,   321,   322,   323,
     324,   325,   326,   327,   328,   329,   330,   331,   332,   333,
     334,   335,   336,   305,   306,     0,     0,   609,   610,   611,
       0,   897,     0,   612,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   615,   229,   613,     0,   643,   616,   614,
       0,     0,     0,     0,     0,   617,   618,   619,   620,     0,
     621,   622,     0,     0,   308,   309,   310,   311,   312,   313,
     314,   315,   316,   317,   318,   319,   320,   321,   322,   323,
     324,   325,   326,   327,   328,   329,   330,   331,   332,   333,
     334,   335,   336,   609,   610,   611,     0,   898,     0,   612,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   613,   609,   610,   611,   614,     0,   854,   612,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   952,     0,
     613,   609,   610,   611,   614,     0,     0,   612,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   953,     0,   613,
     609,   610,   611,   614,     0,     0,   612,     0,     0,     0,
       0,     0,     0,     0,   615,   229,   960,     0,   613,   616,
       0,     0,   614,     0,     0,     0,   617,   618,   619,   620,
       0,   621,   622,   609,   610,   611,     0,  1026,     0,   612,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   613,   609,   610,   611,   614,     0,     0,   612,     0,
     222,   223,   224,     0,     0,     0,   225,     0,     0,     0,
     613,     0,     0,     0,   614,     0,   982,     0,   226,     0,
     615,   229,   227,     0,     0,   616,     0,     0,     0,     0,
       0,     0,   617,   618,   619,   620,     0,   621,   622,   615,
     229,     0,     0,     0,   616,     0,     0,     0,     0,     0,
       0,   617,   618,   619,   620,     0,   621,   622,   615,   229,
       0,     0,     0,   616,     0,     0,     0,     0,     0,     0,
     617,   618,   619,   620,   303,   621,   622,   615,   229,   222,
     223,   224,   616,     0,     0,   225,     0,     0,     0,   617,
     618,   619,   620,     0,   621,   622,     0,   226,     0,     0,
       0,   227,     0,     0,   304,     0,     0,     0,     0,     0,
     615,   229,     0,     0,     0,   616,     0,     0,     0,     0,
       0,     0,   617,   618,   619,   620,     0,   621,   622,   615,
     229,     0,     0,     0,   616,   305,   306,   228,   229,     0,
       0,   617,   618,   619,   620,   303,   621,   622,     0,   230,
     231,   232,   233,     0,   234,   235,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   304,   308,   309,   310,   311,
     312,   313,   314,   315,   316,   317,   318,   319,   320,   321,
     322,   323,   324,   325,   326,   327,   328,   329,   330,   331,
     332,   333,   334,   335,   336,     0,   305,   306,     0,     0,
       0,     0,     0,     0,     0,   303,   228,   229,     0,     0,
       0,     0,   474,     0,     0,     0,     0,     0,   230,   231,
     232,   233,     0,   234,   235,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   304,     0,   308,   309,   310,
     311,   312,   313,   314,   315,   316,   317,   318,   319,   320,
     321,   322,   323,   324,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   305,   306,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   729,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   308,   309,   310,
     311,   312,   313,   314,   315,   316,   317,   318,   319,   320,
     321,   322,   323,   324,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   388,   389,   390,   391,
       0,     0,   392,     0,   393,   394,   395,   396,   397,     0,
     663,     0,     0,     0,   398,     0,     0,     0,   399,   388,
     389,   390,   391,     0,     0,   392,     0,   393,   394,   395,
     396,   397,     0,     0,     0,     0,     0,   398,     0,     0,
       0,   399,     0,     0,     0,   400,     0,   401,     0,     0,
       0,   402,     0,   388,   389,   390,   391,     0,     0,   392,
       0,   393,   394,   395,   396,   397,     0,     0,   400,     0,
     401,   398,     0,     0,   402,   399,     0,     0,     0,     0,
       0,     0,    23,     0,     0,     0,     0,   577,     0,   403,
       0,   404,   405,     0,     0,     0,   303,     0,     0,     0,
       0,     0,   400,     0,   401,    23,     0,     0,   402,     0,
     577,     0,   403,     0,   404,   405,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   304,   578,   579,   580,
       0,     0,     0,   406,     0,     0,     0,     0,   407,    23,
     408,     0,     0,     0,     0,     0,   403,     0,   404,   405,
       0,     0,     0,     0,     0,     0,   406,   305,   306,     0,
       0,   407,     0,   408,     0,     0,   303,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     406,     0,     0,     0,     0,   407,   304,   408,   308,   309,
     310,   311,   312,   313,   314,   315,   316,   317,   318,   319,
     320,   321,   322,   323,   324,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,   305,   306,     0,
       0,     0,     0,   985,     0,     0,   303,     0,     0,   983,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   304,     0,   308,   309,
     310,   311,   312,   313,   314,   315,   316,   317,   318,   319,
     320,   321,   322,   323,   324,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,   305,   306,     0,
       0,     0,     0,     0,     0,     0,   743,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   102,     0,     0,   103,     0,     0,     0,     0,     0,
       0,     0,     0,   104,   243,     0,     0,     0,   308,   309,
     310,   311,   312,   313,   314,   315,   316,   317,   318,   319,
     320,   321,   322,   323,   324,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,   105,     0,   106,
       0,     0,     0,   107,     0,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,     0,   244,   128,   129,     0,
       0,    71,   130,     0,    23,   131,   132,   133,     0,     0,
     102,     0,     0,   103,    24,     0,     0,     0,     0,     0,
       0,     0,   104,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   134,   135,   136,   137,   882,
     883,     0,    73,    74,     0,     0,   105,     0,   106,     0,
       0,     0,   107,     0,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,     0,     0,   128,   129,     0,     0,
      71,   130,     0,    23,   131,   132,   133,   102,     0,     0,
     103,   240,     0,    24,     0,     0,     0,     0,     0,   104,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   134,   135,   136,   137,     0,     0,
       0,    73,    74,   105,     0,   106,     0,     0,     0,   107,
       0,   108,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,     0,     0,   128,   129,     0,     0,    71,   130,     0,
      23,   131,   132,   133,   102,     0,     0,   103,     0,     0,
      24,     0,     0,     0,     0,     0,   104,     0,     0,     0,
       0,     0,     0,   598,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   134,   135,   136,   137,     0,     0,     0,    73,    74,
     105,     0,   106,     0,     0,     0,   107,     0,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,     0,     0,
     128,   129,     0,     0,    71,   130,     0,    23,   131,   132,
     133,   102,     0,     0,   103,     0,     0,    24,     0,     0,
       0,     0,     0,   104,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   134,   135,
     136,   137,     0,     0,     0,    73,    74,   105,     0,   106,
       0,     0,     0,   107,     0,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   303,     0,   128,   129,     0,
       0,    71,   130,     0,    23,   131,   132,   133,     0,     0,
       0,     0,     0,     0,    24,     0,   464,     0,     0,     0,
       0,     0,     0,     0,   303,   304,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   727,     0,     0,     0,   134,   135,   136,   137,     0,
       0,     0,    73,    74,   304,     0,   305,   306,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   465,   466,     0,     0,
     728,     0,     0,     0,     0,   305,   306,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   308,   309,   310,
     311,   312,   313,   314,   315,   316,   317,   318,   319,   320,
     321,   322,   323,   324,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   308,   309,   310,   311,
     312,   313,   314,   315,   316,   317,   318,   319,   320,   321,
     322,   323,   324,   325,   326,   327,   328,   329,   330,   331,
     332,   333,   334,   335,   336,   303,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   304,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   788,   789,     0,     0,   305,   306,     0,     0,
       0,     0,     0,     0,     0,   303,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   304,     0,   308,   309,   310,
     311,   312,   313,   314,   315,   316,   317,   318,   319,   320,
     321,   322,   323,   324,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   305,   306,     0,     0,
       0,     0,     0,     0,     0,   303,     0,     0,     0,     0,
       0,     0,     0,   363,     0,     0,   307,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   304,     0,   308,   309,   310,
     311,   312,   313,   314,   315,   316,   317,   318,   319,   320,
     321,   322,   323,   324,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   305,   306,     0,     0,
       0,     0,     0,     0,     0,   303,     0,     0,     0,     0,
       0,     0,     0,   459,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   304,     0,   308,   309,   310,
     311,   312,   313,   314,   315,   316,   317,   318,   319,   320,
     321,   322,   323,   324,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   305,   306,     0,     0,
       0,     0,     0,     0,     0,   303,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   461,     0,     0,     0,
       0,     0,     0,     0,     0,   304,     0,   308,   309,   310,
     311,   312,   313,   314,   315,   316,   317,   318,   319,   320,
     321,   322,   323,   324,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   305,   306,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   303,   308,   309,   310,
     311,   312,   313,   314,   315,   316,   317,   318,   319,   320,
     321,   322,   323,   324,   325,   326,   327,   328,   329,   330,
     331,   332,   333,   334,   335,   336,   304,     0,     0,   303,
       0,     0,     0,     0,     0,     0,     0,   678,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   473,     0,     0,     0,     0,   305,   306,   304,
       0,     0,     0,     0,     0,     0,   303,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   785,     0,     0,     0,
     305,   306,     0,     0,     0,     0,   304,     0,   308,   309,
     310,   311,   312,   313,   314,   315,   316,   317,   318,   319,
     320,   321,   322,   323,   324,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,   305,   306,     0,
       0,   308,   309,   310,   311,   312,   313,   314,   315,   316,
     317,   318,   319,   320,   321,   322,   323,   324,   325,   326,
     327,   328,   329,   330,   331,   332,   333,   334,   335,   336,
       0,     0,     0,     0,     0,     0,     0,   303,   308,   309,
     310,   311,   312,   313,   314,   315,   316,   317,   318,   319,
     320,   321,   322,   323,   324,   325,   326,   327,   328,   329,
     330,   331,   332,   333,   334,   335,   336,   304,     0,     0,
     303,     0,     0,     0,     0,     0,     0,     0,   796,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306,
     304,     0,     0,     0,     0,     0,     0,   303,     0,     0,
       0,     0,     0,     0,     0,   799,     0,     0,     0,   466,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   305,   306,     0,     0,     0,     0,   304,     0,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   305,   306,
       0,     0,   308,   309,   310,   311,   312,   313,   314,   315,
     316,   317,   318,   319,   320,   321,   322,   323,   324,   325,
     326,   327,   328,   329,   330,   331,   332,   333,   334,   335,
     336,     0,     0,     0,     0,     0,     0,     0,     0,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   303,     0,
       0,     0,     0,     0,     0,     0,   828,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   303,   304,     0,
       0,     0,     0,     0,     0,   877,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   304,     0,   305,
     306,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     308,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   303,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   934,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   303,   304,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   935,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   304,     0,   305,
     306,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     308,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   303,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   936,     0,
       0,     0,     0,     0,     0,     0,     0,   303,   304,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   304,     0,   305,
     306,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   937,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     308,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   303,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   939,     0,     0,   303,   304,     0,
       0,     0,     0,     0,     0,   940,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   304,     0,   305,
     306,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     308,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   303,     0,
       0,     0,     0,     0,     0,     0,   941,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   303,   304,     0,
       0,     0,     0,     0,     0,   981,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   304,     0,   305,
     306,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     308,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   303,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   995,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   303,   304,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   304,     0,   305,
     306,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   996,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     308,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   303,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   303,   304,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,  1023,     0,     0,   304,     0,   305,
     306,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   305,   306,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     308,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   308,
     309,   310,   311,   312,   313,   314,   315,   316,   317,   318,
     319,   320,   321,   322,   323,   324,   325,   326,   327,   328,
     329,   330,   331,   332,   333,   334,   335,   336,   303,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   304,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   305,
     306,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,     0,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
         3,   103,   104,     3,   222,   223,   224,    41,   226,   227,
     201,   566,   135,   231,   130,   233,    41,   136,   601,   237,
     136,   197,   361,   217,   218,   597,   220,   577,   641,   642,
      14,    18,    16,     9,     7,    11,    12,    13,    14,    18,
       7,    93,    27,    14,    24,    16,   588,     8,    61,    18,
      89,    54,    55,    18,     7,    55,   107,    33,    56,    62,
       7,     9,    18,    11,    12,    13,    14,    50,     8,    61,
      68,   105,     8,    15,     5,     6,     7,   107,   187,   188,
     105,    34,    15,    15,    18,    33,   195,    14,     4,    16,
      93,    28,   107,    33,    28,     9,   130,    15,    24,    27,
     134,   135,   136,    15,   102,   103,   104,    14,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
      27,    95,    15,    28,    27,    66,    14,    51,   147,   152,
      14,    29,    16,   152,    15,    15,     0,    15,    15,    97,
      29,    67,    83,   187,   188,    86,    15,     7,    15,   107,
     152,   195,   147,    14,   107,   152,   721,   152,   147,   152,
       3,   155,   152,   212,   276,   277,    58,   180,   181,    61,
     180,   181,   155,    90,    91,    24,   288,    94,   155,   150,
       7,   152,   190,   162,   192,   171,   390,   162,   167,   168,
     107,    14,   167,   168,   398,    44,   162,   210,   155,    24,
     152,   167,   168,   216,   217,   218,   152,   220,    29,   152,
     152,    54,    55,   171,   152,   162,   152,   225,   155,   107,
     167,   168,   230,    46,   152,   238,   234,   235,   145,   146,
     152,    92,   162,   186,    29,    28,    32,   167,   168,   147,
     147,    24,   172,   866,   152,   152,    33,   108,   122,   152,
      93,   225,    48,    49,   228,     8,   838,   162,   232,   152,
     820,   202,   167,   168,   162,   182,   183,   184,   185,   167,
     168,   152,   152,   162,   152,   152,   289,   285,   167,   168,
      33,   155,    33,   152,   470,   152,   174,   175,   176,   177,
     178,   179,   180,   181,   302,   303,   304,   305,   306,   307,
     308,   309,   310,   311,   312,   313,   314,   315,   316,   317,
     318,   319,   320,   321,   322,   323,   324,   325,   326,   327,
     328,   329,   330,   331,   332,   333,   334,   335,   336,   391,
     679,   464,   884,   158,   159,   461,   469,    14,   890,    45,
       4,   162,    97,    14,    28,    16,   167,   168,     4,    90,
      91,   480,   107,    37,   480,   474,   400,   150,    14,   152,
       3,    47,     5,   489,    28,   400,   107,   162,   569,    46,
     574,   575,   167,   168,   382,   158,   159,   390,   391,   972,
       8,   355,   356,   357,   949,   398,    92,    30,    31,   593,
     107,   107,    24,   401,   402,   238,   404,    14,     7,    16,
     408,   629,   108,   631,   632,    33,   634,    33,    97,   637,
     638,    67,    44,    69,     8,   643,   429,   461,     8,     8,
     464,   107,   147,   549,     9,   469,   122,   152,   472,   555,
     474,   635,   406,   107,    90,    91,   480,   472,    94,    33,
     156,   560,     8,    33,    33,   489,    15,    14,   492,   155,
     152,   107,    14,   169,    16,   157,   152,   465,   466,   155,
     468,    33,  1014,   471,    33,   473,    14,    33,    16,   152,
     152,   479,   155,   105,   482,    92,    93,   485,     8,     8,
     488,    94,     4,   491,   101,   493,   494,   495,   170,   145,
     146,   108,    14,    14,    16,    17,   182,   183,   184,   185,
       8,     8,    69,    33,    33,   549,   639,     8,   107,   162,
      26,   555,    92,    35,   167,   168,   560,    39,    40,    41,
      42,    43,    26,    90,    91,    33,    33,    94,   108,    26,
      92,    93,    33,     8,    24,   152,   655,   741,    28,   101,
     107,   103,    26,   107,    92,    93,   108,    37,   391,   557,
     558,    14,     8,   101,    44,   103,   564,   156,    33,     4,
     108,   574,   575,   625,    14,   578,   579,   580,    14,    24,
     169,   690,   585,    28,    63,   151,   107,    33,   145,   146,
     593,    14,    37,   591,   597,   107,   162,   600,   187,    44,
     152,   167,   168,   155,   602,   639,   609,   610,   611,   152,
     613,   614,   155,    14,   612,   618,    61,   620,     8,   617,
     729,   655,   625,   621,   622,   105,   624,   630,   182,   183,
     184,   185,   635,    27,   146,    63,     8,    90,    91,   107,
     107,    94,   573,    33,   862,   863,   107,   152,   612,     8,
     155,   615,   846,    28,   107,   619,   690,   152,   103,   155,
     155,    33,   174,   175,   176,   177,   178,   179,   180,   181,
       0,   669,   636,     7,    33,    14,   155,   210,   157,    14,
     159,   160,   161,   162,   163,    14,    16,    16,   156,   147,
      24,     0,   145,   146,    28,   729,    36,   806,   696,     0,
       6,   169,     8,    37,    10,   184,   185,    16,   107,   712,
      44,   714,   191,   152,   193,    16,   107,   720,   197,   187,
     720,    57,    58,   726,   727,   728,    32,   155,   107,   157,
     155,   159,   160,   161,   162,   163,   739,    14,   741,    16,
     743,   107,    48,    49,    24,   578,   579,   580,    28,    55,
      33,    90,    91,    59,   107,    94,   184,    37,    38,    33,
     439,   440,   441,   191,    44,   193,   445,   446,   107,   197,
      26,   151,   806,   452,    26,   454,    14,     6,   457,    11,
      12,    13,   162,    56,   782,    17,   784,   167,   168,   787,
     788,   789,   625,    26,   151,    27,   794,    29,   186,   797,
     798,    33,   911,    32,   802,   162,   145,   146,    24,   807,
     167,   168,    28,   816,   817,    92,    93,   167,   168,    48,
      49,    37,    38,    14,   101,     7,    55,   830,    44,   107,
      59,   108,   142,   143,   144,   838,   107,     6,    18,     8,
     107,    10,    26,   846,    24,   843,    25,   850,    28,   852,
     107,   854,   855,   955,   956,    64,    65,    37,   107,   338,
     339,    37,   860,    32,    44,   107,   864,   155,    51,    50,
     349,   350,   351,   352,   872,    18,   187,   911,   154,    48,
      49,     7,   361,    15,   848,   849,    55,   851,   122,    46,
      59,    14,    14,   891,   892,    15,   429,    14,    17,    24,
      17,   155,    17,    28,   907,   903,    14,   155,   906,   912,
     338,   339,    37,     3,    28,     5,   148,   149,    28,    44,
      14,   349,   350,   351,   352,    28,   187,    25,   160,   161,
     162,   163,    24,   165,   166,   107,    53,    54,   152,   937,
      30,    31,    32,    60,    15,    62,     7,   187,   187,    66,
     152,   954,   187,    24,    18,   958,    14,    28,    48,    49,
      14,    24,    17,    24,   967,   968,    37,    28,   966,   154,
     187,    52,   975,    44,   977,   978,    37,   980,   151,   187,
      97,    18,   187,    44,   151,   988,    18,   104,    15,   106,
     107,   470,   995,   996,   630,   959,    24,   212,   427,   478,
      28,   603,    14,   841,    16,    17,  1009,    55,  1011,    37,
     713,   490,   976,    32,    16,   204,    44,  1020,   131,    47,
    1023,    66,  1025,    35,   361,  1028,   535,    39,    40,    41,
      42,    43,    15,    83,  1037,    47,    41,    11,    12,    13,
      86,    24,   470,    17,    44,    28,   182,   183,   184,   185,
     478,   568,   585,    27,    37,    29,   535,   536,   537,    33,
     889,    44,   490,   813,   699,   718,   573,   600,  1030,   400,
     472,   984,   551,   552,   655,   468,   609,   610,   611,   988,
     613,   614,   561,   905,   563,   618,   988,   620,   767,   988,
     769,   770,   625,   772,   461,   107,   775,   776,    -1,   911,
     492,   780,    -1,    -1,    -1,    -1,    -1,    -1,   536,   537,
      -1,    -1,    -1,    -1,    -1,    14,    -1,    16,    17,    -1,
      -1,    -1,    -1,   551,   552,    14,    -1,    16,    17,    -1,
      -1,    -1,    -1,   561,   146,   563,    35,    -1,    -1,    -1,
      39,    40,    41,    42,    43,    -1,    35,    -1,    -1,    -1,
      39,    40,    41,    42,    43,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   174,   175,   176,   177,   178,   179,   180,   181,
      -1,    -1,    -1,    -1,   148,   149,    -1,    -1,    -1,   712,
      -1,   714,    -1,    -1,   663,    -1,   160,   161,   162,   163,
      -1,   165,   166,   726,   727,   728,    -1,    -1,    -1,    -1,
     679,    -1,    14,    -1,    16,    17,   739,    -1,   107,    -1,
     743,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   107,   698,
      -1,    -1,   701,    35,    -1,    -1,    -1,    39,    40,    41,
      42,    43,    -1,    -1,    -1,   663,    -1,    -1,    14,    -1,
      16,    17,    -1,    -1,    -1,    -1,    -1,   146,    -1,    -1,
      -1,   930,   931,    -1,    -1,    -1,    -1,   146,    -1,    35,
      -1,    -1,    -1,    39,    40,    41,    42,    43,    -1,    -1,
     698,    -1,    -1,   701,    -1,   174,   175,   176,   177,   178,
     179,   180,   181,   816,   817,   174,   175,   176,   177,   178,
     179,   180,   181,    -1,    -1,   107,    -1,   830,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   850,    -1,   852,
      -1,   854,   855,    -1,    -1,   804,    -1,    -1,    -1,   808,
      -1,   107,    -1,    -1,   146,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   174,   175,   176,   177,   178,   179,   180,   181,
     146,    -1,    -1,    -1,   907,    -1,   804,    -1,    -1,   912,
     808,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   174,   175,
     176,   177,   178,   179,   180,   181,    -1,    -1,   887,   888,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     899,   954,    -1,    -1,    -1,   958,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   967,   968,    -1,    -1,    -1,    -1,
      -1,    -1,   975,    -1,   977,   978,    -1,   980,    -1,    14,
      -1,    16,    17,    14,    -1,    -1,    17,    -1,    -1,   887,
     888,    -1,   995,   996,    -1,    26,    -1,    -1,    -1,    -1,
      35,   899,    -1,     4,    39,    40,    41,    42,    43,    -1,
      11,    12,    13,    -1,    -1,    -1,    17,  1020,    -1,    -1,
    1023,    -1,  1025,    -1,    -1,  1028,    -1,    28,    29,    60,
      -1,    62,    33,    -1,  1037,    66,    -1,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    86,    87,    -1,    -1,    90,
      91,    -1,    -1,    94,    95,    -1,    97,    98,    99,   100,
       4,    -1,   107,    -1,    -1,    -1,   107,    11,    12,    13,
      -1,    -1,    -1,    17,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,     4,    -1,    29,    -1,    -1,    -1,    33,
      11,    12,    13,    -1,    -1,    -1,    17,   138,   139,   140,
     141,   146,    -1,    -1,   145,   146,    -1,    -1,    29,    -1,
      -1,    -1,    33,    -1,     4,   156,    -1,    -1,    -1,    -1,
      -1,    11,    12,    13,    -1,    -1,    -1,    17,   169,   174,
     175,   176,   177,   178,   179,   180,   181,   148,   149,    29,
      -1,    -1,   153,    33,    -1,    -1,   187,    -1,    -1,   160,
     161,   162,   163,    -1,   165,   166,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,     7,    11,    12,    13,
      -1,    -1,    -1,    17,    -1,    11,    12,    13,    -1,    -1,
      -1,    17,    -1,    -1,    -1,    29,    -1,    -1,    -1,    33,
      -1,    27,    -1,    29,   148,   149,    37,    33,    -1,   153,
      -1,    -1,    -1,    -1,    -1,    -1,   160,   161,   162,   163,
      -1,   165,   166,    -1,    -1,    -1,    -1,   148,   149,    -1,
      64,    65,   153,    -1,    -1,    -1,    -1,    68,    69,   160,
     161,   162,   163,    -1,   165,   166,    -1,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   148,   149,
      -1,    -1,    -1,   153,    -1,    -1,    -1,    -1,    -1,    -1,
     160,   161,   162,   163,    -1,   165,   166,    37,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,    -1,    68,    69,
      -1,    -1,    -1,    -1,   148,   149,    -1,    -1,    -1,   153,
      -1,    -1,   148,   149,    -1,    -1,   160,   161,   162,   163,
      -1,   165,   166,   164,   160,   161,   162,   163,    -1,   165,
     166,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,    14,    -1,
      -1,    17,    -1,    -1,    -1,    -1,    -1,    11,    12,    13,
      26,    15,    -1,    17,    11,    12,    13,    -1,    -1,    -1,
      17,    -1,    -1,    -1,   164,    29,    -1,    -1,    -1,    33,
      27,    -1,    29,    -1,    -1,    -1,    33,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    60,    -1,    62,    -1,    -1,    -1,
      66,    -1,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,    85,
      86,    87,     7,    -1,    90,    91,    -1,    -1,    94,    95,
      -1,    97,    98,    99,   100,    -1,    11,    12,    13,    -1,
      15,   107,    17,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    37,    -1,    29,    -1,    -1,    -1,    33,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   138,   139,   140,   141,    -1,    -1,    -1,   145,
     146,    -1,    -1,    68,    69,    -1,    -1,    -1,    -1,    -1,
     156,    -1,     7,    -1,   148,   149,    -1,    -1,    -1,   153,
      -1,   148,   149,   169,    -1,    -1,   160,   161,   162,   163,
      -1,   165,   166,   160,   161,   162,   163,    -1,   165,   166,
      -1,    -1,    37,    -1,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,    68,    69,    -1,    -1,    11,    12,    13,
      -1,    15,    -1,    17,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   148,   149,    29,    -1,   162,   153,    33,
      -1,    -1,    -1,    -1,    -1,   160,   161,   162,   163,    -1,
     165,   166,    -1,    -1,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,    11,    12,    13,    -1,    15,    -1,    17,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    29,    11,    12,    13,    33,    -1,   162,    17,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,
      29,    11,    12,    13,    33,    -1,    -1,    17,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,    29,
      11,    12,    13,    33,    -1,    -1,    17,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   148,   149,    27,    -1,    29,   153,
      -1,    -1,    33,    -1,    -1,    -1,   160,   161,   162,   163,
      -1,   165,   166,    11,    12,    13,    -1,    15,    -1,    17,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    29,    11,    12,    13,    33,    -1,    -1,    17,    -1,
      11,    12,    13,    -1,    -1,    -1,    17,    -1,    -1,    -1,
      29,    -1,    -1,    -1,    33,    -1,    27,    -1,    29,    -1,
     148,   149,    33,    -1,    -1,   153,    -1,    -1,    -1,    -1,
      -1,    -1,   160,   161,   162,   163,    -1,   165,   166,   148,
     149,    -1,    -1,    -1,   153,    -1,    -1,    -1,    -1,    -1,
      -1,   160,   161,   162,   163,    -1,   165,   166,   148,   149,
      -1,    -1,    -1,   153,    -1,    -1,    -1,    -1,    -1,    -1,
     160,   161,   162,   163,     7,   165,   166,   148,   149,    11,
      12,    13,   153,    -1,    -1,    17,    -1,    -1,    -1,   160,
     161,   162,   163,    -1,   165,   166,    -1,    29,    -1,    -1,
      -1,    33,    -1,    -1,    37,    -1,    -1,    -1,    -1,    -1,
     148,   149,    -1,    -1,    -1,   153,    -1,    -1,    -1,    -1,
      -1,    -1,   160,   161,   162,   163,    -1,   165,   166,   148,
     149,    -1,    -1,    -1,   153,    68,    69,   148,   149,    -1,
      -1,   160,   161,   162,   163,     7,   165,   166,    -1,   160,
     161,   162,   163,    -1,   165,   166,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,    -1,    68,    69,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,     7,   148,   149,    -1,    -1,
      -1,    -1,   155,    -1,    -1,    -1,    -1,    -1,   160,   161,
     162,   163,    -1,   165,   166,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,    68,    69,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   155,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,    11,    12,    13,    14,
      -1,    -1,    17,    -1,    19,    20,    21,    22,    23,    -1,
     152,    -1,    -1,    -1,    29,    -1,    -1,    -1,    33,    11,
      12,    13,    14,    -1,    -1,    17,    -1,    19,    20,    21,
      22,    23,    -1,    -1,    -1,    -1,    -1,    29,    -1,    -1,
      -1,    33,    -1,    -1,    -1,    60,    -1,    62,    -1,    -1,
      -1,    66,    -1,    11,    12,    13,    14,    -1,    -1,    17,
      -1,    19,    20,    21,    22,    23,    -1,    -1,    60,    -1,
      62,    29,    -1,    -1,    66,    33,    -1,    -1,    -1,    -1,
      -1,    -1,    97,    -1,    -1,    -1,    -1,   102,    -1,   104,
      -1,   106,   107,    -1,    -1,    -1,     7,    -1,    -1,    -1,
      -1,    -1,    60,    -1,    62,    97,    -1,    -1,    66,    -1,
     102,    -1,   104,    -1,   106,   107,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    37,   142,   143,   144,
      -1,    -1,    -1,   148,    -1,    -1,    -1,    -1,   153,    97,
     155,    -1,    -1,    -1,    -1,    -1,   104,    -1,   106,   107,
      -1,    -1,    -1,    -1,    -1,    -1,   148,    68,    69,    -1,
      -1,   153,    -1,   155,    -1,    -1,     7,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     148,    -1,    -1,    -1,    -1,   153,    37,   155,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,    68,    69,    -1,
      -1,    -1,    -1,     4,    -1,    -1,     7,    -1,    -1,   150,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,    68,    69,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   147,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    14,    -1,    -1,    17,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    26,    27,    -1,    -1,    -1,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,    60,    -1,    62,
      -1,    -1,    -1,    66,    -1,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    86,    87,    -1,    89,    90,    91,    -1,
      -1,    94,    95,    -1,    97,    98,    99,   100,    -1,    -1,
      14,    -1,    -1,    17,   107,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    26,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   138,   139,   140,   141,    53,
      54,    -1,   145,   146,    -1,    -1,    60,    -1,    62,    -1,
      -1,    -1,    66,    -1,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      84,    85,    86,    87,    -1,    -1,    90,    91,    -1,    -1,
      94,    95,    -1,    97,    98,    99,   100,    14,    -1,    -1,
      17,    18,    -1,   107,    -1,    -1,    -1,    -1,    -1,    26,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   138,   139,   140,   141,    -1,    -1,
      -1,   145,   146,    60,    -1,    62,    -1,    -1,    -1,    66,
      -1,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    86,
      87,    -1,    -1,    90,    91,    -1,    -1,    94,    95,    -1,
      97,    98,    99,   100,    14,    -1,    -1,    17,    -1,    -1,
     107,    -1,    -1,    -1,    -1,    -1,    26,    -1,    -1,    -1,
      -1,    -1,    -1,    33,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   138,   139,   140,   141,    -1,    -1,    -1,   145,   146,
      60,    -1,    62,    -1,    -1,    -1,    66,    -1,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    -1,    -1,
      90,    91,    -1,    -1,    94,    95,    -1,    97,    98,    99,
     100,    14,    -1,    -1,    17,    -1,    -1,   107,    -1,    -1,
      -1,    -1,    -1,    26,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   138,   139,
     140,   141,    -1,    -1,    -1,   145,   146,    60,    -1,    62,
      -1,    -1,    -1,    66,    -1,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    86,    87,     7,    -1,    90,    91,    -1,
      -1,    94,    95,    -1,    97,    98,    99,   100,    -1,    -1,
      -1,    -1,    -1,    -1,   107,    -1,    28,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,     7,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    24,    -1,    -1,    -1,   138,   139,   140,   141,    -1,
      -1,    -1,   145,   146,    37,    -1,    68,    69,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    88,    89,    -1,    -1,
      63,    -1,    -1,    -1,    -1,    68,    69,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    64,    65,    -1,    -1,    68,    69,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,    68,    69,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    15,    -1,    -1,    88,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,    68,    69,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    15,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,    68,    69,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    28,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    37,    -1,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,    68,    69,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,     7,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,    37,    -1,    -1,     7,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    15,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    63,    -1,    -1,    -1,    -1,    68,    69,    37,
      -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,    -1,    -1,
      68,    69,    -1,    -1,    -1,    -1,    37,    -1,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,    68,    69,    -1,
      -1,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,    37,    -1,    -1,
       7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    15,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    68,    69,
      37,    -1,    -1,    -1,    -1,    -1,    -1,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    15,    -1,    -1,    -1,    89,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    68,    69,    -1,    -1,    -1,    -1,    37,    -1,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,    68,    69,
      -1,    -1,   109,   110,   111,   112,   113,   114,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    15,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    15,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    68,
      69,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    68,    69,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    68,
      69,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    68,    69,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    27,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    68,
      69,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    63,    -1,    -1,    -1,    -1,    68,    69,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    33,    -1,    -1,     7,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    15,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    68,
      69,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    68,    69,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    15,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    15,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    68,
      69,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    68,    69,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    24,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,    68,
      69,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    63,    -1,    -1,    -1,    -1,    68,    69,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     7,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    63,    -1,    -1,    37,    -1,    68,
      69,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    68,    69,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     109,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    68,
      69,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   110,   111,   112,   113,   114,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,    -1,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137
  };
  }


  static class YyTokenClass {
  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  public static final short
  yystos_[] =
  {
         0,     3,     5,    30,    31,    32,    48,    49,   191,   192,
     193,   194,   195,   219,   224,   228,   229,   230,   234,   247,
     251,   107,   107,    97,   107,   220,   221,   223,   339,   340,
     341,   107,   225,   226,   182,   183,   184,   185,   235,   236,
     237,   237,   248,   249,   237,   252,   253,   254,   255,     0,
     193,   192,   230,     7,     7,    33,    14,    16,    92,    93,
     101,   108,   152,   155,     7,   226,    33,   107,    14,    90,
      91,    94,   107,   145,   146,   243,   250,   285,   286,   325,
     328,   329,   330,    33,   107,   256,    33,     9,   231,     9,
      11,    12,    13,    14,    33,   171,   196,   197,   222,   223,
     341,   221,    14,    17,    26,    60,    62,    66,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    90,    91,
      95,    98,    99,   100,   138,   139,   140,   141,   283,   284,
     285,   289,   291,   294,   295,   298,   299,   300,   301,   302,
     339,   340,    94,   340,   339,    14,    16,    17,    35,    39,
      40,    41,    42,    43,   107,   174,   175,   176,   177,   178,
     179,   180,   181,   238,   239,   240,   241,   242,   243,   107,
     156,   169,   227,   236,     7,    34,   284,    14,    14,    16,
       7,   155,     7,   155,   248,    14,    16,   155,   259,   252,
       6,    55,    59,   232,   233,   234,   247,   251,   268,   280,
       8,    10,   200,   201,   202,   233,    26,    26,    26,   222,
      26,   227,    11,    12,    13,    17,    29,    33,   148,   149,
     160,   161,   162,   163,   165,   166,   216,     8,    33,   284,
      18,   283,   284,    27,    89,   283,   284,   296,   297,   107,
     250,   256,   287,   288,   284,   284,   284,   284,   284,   284,
     284,   284,   284,   284,   284,   284,   284,   284,   284,   284,
     284,   284,   284,   284,   284,   284,    14,    14,    69,   107,
     324,   325,   327,   333,   338,    14,   107,   239,    14,    14,
     324,   326,   334,   335,   336,   337,   334,   324,   331,   332,
     333,    15,   152,     7,    37,    68,    69,    88,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   238,    24,    44,
     238,   107,   238,   238,   238,   238,   238,    92,   108,    24,
      28,    37,    44,   341,   341,   162,   167,   168,   238,   107,
     238,   244,   245,    15,   326,   326,   284,   238,   284,   238,
     260,   326,   238,   240,   107,   262,   237,   270,   271,   272,
     269,   270,    47,   237,   281,   282,     4,   232,    11,    12,
      13,    14,    17,    19,    20,    21,    22,    23,    29,    33,
      60,    62,    66,   104,   106,   107,   148,   153,   155,   206,
     211,   214,   215,   303,   304,   307,   308,   312,   314,   315,
     316,   318,   321,   322,   339,   107,   186,   203,   204,     8,
     201,   198,   199,   223,   198,   198,     8,   198,   172,   197,
     197,   197,   107,   227,   284,   197,   197,   227,   107,   217,
     218,   284,   197,   227,   197,   284,   284,   197,   223,    15,
      18,    28,    27,    27,    28,    88,    89,    27,   152,    28,
     155,    61,   152,    63,   155,   283,   284,   283,   155,   147,
     152,   284,    14,    92,   108,    14,   283,   339,   122,   152,
     155,   147,   152,   147,   122,   147,   284,   284,   284,   284,
     284,   284,   284,   284,   284,   284,   284,   284,   284,   284,
     284,   284,   284,   284,   284,   284,   284,   284,   284,   284,
     284,   284,   284,   284,   284,   284,   284,   284,   284,   284,
     284,    15,   238,   238,    18,    36,    38,    38,   107,   107,
     238,   238,   238,   238,   157,   170,   227,   227,   227,    47,
     246,    45,   155,   245,   246,   152,    15,     7,     7,    15,
     152,   155,   107,   155,    50,   152,   263,   264,   107,    33,
     284,   107,   311,    33,    26,    26,   198,   102,   142,   143,
     144,   206,   212,   213,   222,   309,   339,    56,   275,   276,
     323,    14,   198,    26,   287,   284,   284,    14,    33,   284,
      24,   158,   159,   207,   208,   227,   186,   284,     4,    11,
      12,    13,    17,    29,    33,   148,   153,   160,   161,   162,
     163,   165,   166,   216,   103,     7,   107,   204,   206,     8,
      33,     8,     8,   107,     8,    26,    29,   164,   151,    28,
     150,   152,    25,   162,   324,   332,   334,   284,   284,   284,
     297,   334,   284,   288,   284,   292,   293,   326,    15,    15,
     238,   284,   333,   152,   284,   107,   107,   284,    15,   152,
     284,   324,   238,   284,   335,   284,   284,   284,    15,   244,
     238,   238,   324,   238,   238,   324,   284,   284,   326,   238,
      14,    16,   257,   238,   284,   107,    51,   266,   155,   259,
     270,   155,   281,   198,   198,     8,   310,   311,   223,   223,
     223,    15,     8,    33,     8,   206,    57,    58,   277,   278,
     279,   263,    18,   284,     8,   198,    61,    24,    63,   155,
     313,   315,   339,   206,    90,    91,   107,   209,   284,    24,
     208,   151,   154,   147,   206,   206,   206,   187,   227,   284,
     206,   206,   187,   227,   187,   284,   206,   227,   206,   284,
     284,   284,   205,   206,   222,     7,     4,   197,   199,   197,
     197,    15,   197,   198,   227,   197,   197,   334,   218,   218,
     197,    18,   147,    27,   147,    27,    27,   147,    64,    65,
     290,     4,    67,   293,    24,   238,    15,    14,    14,    15,
     284,     4,    46,    15,   155,   326,    14,    46,   155,   284,
      16,   238,   273,   261,   262,   238,     8,     8,    17,     8,
     152,   206,   213,   206,    15,   278,   341,   266,    15,    17,
       8,   206,   206,   206,   319,   320,   326,    15,    33,    14,
      16,    14,    16,   155,   206,   198,    14,   206,    28,    29,
     164,    28,   151,    28,   162,     8,   187,    27,    27,    27,
      14,    27,     8,    18,     8,   150,    25,   284,   284,   284,
     284,   284,    24,   284,    15,   284,   284,    15,   284,   238,
      15,   326,    53,    54,   258,   284,   238,   105,   105,   107,
     275,    61,   103,   206,   206,   187,   311,    15,    15,   155,
     187,   206,     4,    28,   305,   306,    64,    65,   317,     4,
      67,   152,    24,   315,   209,   210,   210,   284,     8,   198,
     227,   227,   206,   187,   227,   206,   187,   206,   206,   284,
     197,   197,   284,   218,    18,    27,    27,    63,   284,    33,
      15,    15,    15,   263,    16,   238,    16,   238,   257,   263,
     284,   284,    27,    27,    18,    14,    14,   238,    18,    28,
      27,   284,     4,   305,   284,   206,    64,    65,    24,   319,
     206,    15,   152,    15,    17,     8,    29,    18,   151,    28,
     154,    15,    27,   150,   284,     4,   265,   266,    46,   266,
     206,   283,   283,   206,   227,    24,    63,   284,   206,   206,
     209,   187,   206,   227,   206,   206,   187,   206,   290,    52,
     267,    14,    53,    54,   274,   303,   307,   308,    15,    15,
      18,   206,   206,    63,     4,    18,    15,    28,   151,   339,
     263,   206,   206,   206,   187,   206,   265,    18,   206
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
     435,   436,   437,   438,   439,   440,   441,   442,   443,   444
  };
  }

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */

  private static final short
  yyr1_[] =
  {
         0,   190,   191,   191,   191,   192,   192,   193,   193,   193,
     193,   194,   195,   196,   196,   197,   197,   197,   197,   197,
     197,   197,   197,   197,   197,   197,   197,   197,   197,   197,
     197,   197,   197,   197,   197,   197,   197,   197,   198,   198,
     199,   200,   200,   201,   201,   202,   202,   203,   203,   204,
     205,   205,   206,   206,   206,   206,   206,   206,   206,   206,
     206,   206,   206,   206,   206,   206,   206,   206,   206,   206,
     206,   206,   206,   206,   206,   206,   206,   206,   206,   207,
     207,   208,   208,   208,   209,   209,   209,   209,   209,   210,
     210,   211,   211,   211,   211,   211,   211,   211,   211,   212,
     212,   213,   213,   213,   214,   214,   215,   215,   215,   215,
     215,   215,   215,   215,   216,   216,   216,   217,   217,   218,
     219,   220,   220,   221,   221,   222,   222,   223,   224,   224,
     225,   225,   226,   227,   227,   227,   227,   227,   227,   228,
     229,   229,   230,   230,   230,   231,   232,   232,   233,   233,
     233,   233,   233,   233,   234,   234,   234,   235,   235,   236,
     236,   236,   236,   237,   237,   237,   237,   237,   238,   238,
     238,   238,   238,   238,   238,   238,   238,   238,   238,   238,
     238,   238,   238,   238,   239,   239,   239,   239,   239,   239,
     239,   239,   240,   240,   241,   241,   242,   242,   243,   244,
     244,   245,   245,   245,   246,   247,   248,   248,   248,   249,
     250,   250,   250,   250,   251,   251,   252,   252,   252,   253,
     253,   254,   255,   256,   257,   257,   257,   258,   258,   258,
     259,   259,   260,   260,   261,   261,   262,   262,   263,   263,
     264,   265,   265,   266,   267,   267,   268,   268,   269,   269,
     270,   270,   271,   272,   273,   273,   273,   273,   274,   274,
     274,   274,   274,   275,   275,   276,   277,   277,   278,   278,
     279,   279,   280,   280,   281,   281,   281,   282,   282,   283,
     283,   284,   284,   284,   284,   284,   284,   284,   284,   284,
     284,   284,   284,   284,   284,   284,   284,   284,   284,   284,
     284,   284,   284,   284,   284,   284,   284,   284,   284,   284,
     284,   284,   285,   285,   286,   286,   287,   287,   288,   288,
     289,   290,   290,   291,   291,   292,   292,   293,   294,   294,
     294,   294,   294,   294,   294,   294,   294,   294,   294,   294,
     294,   294,   294,   294,   294,   294,   294,   294,   295,   295,
     295,   295,   295,   295,   295,   295,   295,   295,   295,   295,
     295,   295,   295,   295,   295,   295,   295,   295,   295,   295,
     295,   295,   295,   295,   295,   295,   295,   295,   295,   295,
     295,   296,   296,   297,   298,   299,   300,   301,   301,   301,
     301,   301,   302,   303,   303,   303,   303,   303,   303,   304,
     304,   305,   306,   306,   307,   308,   308,   309,   310,   310,
     311,   311,   311,   312,   312,   313,   313,   314,   315,   316,
     316,   317,   317,   318,   318,   319,   319,   320,   321,   321,
     322,   323,   324,   324,   325,   325,   325,   326,   326,   327,
     327,   328,   328,   329,   330,   330,   331,   331,   332,   333,
     334,   334,   335,   335,   336,   337,   338,   338,   339,   339,
     339,   339,   339,   339,   339,   339,   340,   340,   341,   341
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     2,     1,     1,     2,     1,     1,     1,
       1,     4,     4,     3,     1,     5,     4,     3,     3,     3,
       5,     7,     3,     3,     3,     5,     3,     5,     3,     3,
       3,     8,     2,     6,     6,     6,     6,     8,     1,     3,
       1,     1,     2,     1,     1,     2,     5,     1,     2,     3,
       1,     3,     1,     1,     1,     1,     4,     3,     4,     4,
       3,     3,     3,     3,     5,     3,     5,     3,     3,     3,
       2,     1,     5,     1,     1,     1,     1,     1,     1,     1,
       2,     2,     4,     2,     1,     4,     2,     4,     2,     1,
       3,     7,     3,     7,     3,    11,     7,     9,     5,     1,
       3,     2,     2,     2,     8,     8,     6,     6,     6,     8,
       7,     9,     9,     7,     3,     5,     7,     3,     5,     1,
       2,     1,     3,     1,     1,     1,     3,     3,     1,     2,
       1,     2,     3,     1,     3,     3,     3,     3,     3,     1,
       1,     2,     1,     1,     1,     3,     1,     2,     1,     1,
       1,     1,     1,     2,     1,     3,     2,     1,     3,     5,
       4,     4,     5,     1,     1,     1,     1,     0,     3,     1,
       1,     5,     3,     3,     3,     2,     2,     2,     4,     4,
       1,     1,     3,     3,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     3,     3,     3,     3,     1,     1,
       2,     1,     3,     3,     4,     2,     1,     2,     3,     2,
       5,     5,     3,     3,     1,     2,     1,     2,     3,     1,
       1,     6,     2,    10,     1,     3,     4,     1,     1,     1,
       1,     3,     3,     5,     0,     1,     3,     5,     1,     0,
       2,     1,     0,     2,     2,     0,     2,     1,     1,     3,
       1,     1,    10,     7,     3,     3,     3,     3,     1,     1,
       1,     1,     1,     1,     0,     2,     1,     2,     2,     4,
       1,     1,     2,     1,     1,     2,     3,     2,     2,     1,
       3,     1,     3,     4,     1,     1,     1,     1,     4,     4,
       4,     2,     3,     5,     7,     5,     2,     3,     5,     7,
       3,     3,     5,     7,     1,     1,     1,     1,     1,     6,
       1,     1,     1,     1,     1,     1,     1,     3,     1,     1,
       5,     2,     5,     5,     8,     1,     2,     4,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     1,     3,     3,     4,     4,     4,     5,     7,     7,
       5,     6,     4,     1,     1,     1,     1,     1,     1,     5,
       6,     4,     1,     2,     4,     3,     4,     3,     1,     3,
       3,     5,     5,     1,     1,     1,     3,     4,     3,     7,
       6,     4,     5,     5,     8,     1,     3,     3,     2,     2,
       3,     3,     1,     1,     1,     1,     1,     1,     3,     1,
       1,     1,     3,     6,     2,     4,     1,     1,     3,     3,
       1,     3,     1,     1,     3,     3,     1,     3,     1,     2,
       3,     3,     3,     2,     4,     6,     1,     1,     1,     3
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "CLASS", "END", "PROCESS", "INITIAL",
  "EQUALS", "AT", "BEGIN", "ACTIONS", "BARTILDEBAR", "LRSQUARE", "TBAR",
  "LPAREN", "RPAREN", "LRPAREN", "LSQUARE", "RSQUARE", "CSPSKIP",
  "CSPSTOP", "CSPCHAOS", "CSPDIV", "CSPWAIT", "RARROW", "LARROW", "LCURLY",
  "RCURLY", "BAR", "DBAR", "CHANNELS", "CHANSETS", "TYPES", "SEMI",
  "DCOLON", "COMPOSE", "OF", "STAR", "TO", "INMAPOF", "MAPOF", "SEQOF",
  "SEQ1OF", "SETOF", "PLUSGT", "COLONDASH", "DEQUALS", "INV", "VALUES",
  "FUNCTIONS", "PRE", "POST", "MEASURE", "SUBCLASSRESP", "NOTYETSPEC",
  "OPERATIONS", "FRAME", "RD", "WR", "STATE", "LET", "IN", "IF", "THEN",
  "ELSEIF", "ELSE", "CASES", "OTHERS", "PLUS", "MINUS", "ABS", "FLOOR",
  "NOT", "CARD", "POWER", "DUNION", "DINTER", "HD", "TL", "LEN", "ELEMS",
  "INDS", "REVERSE", "CONC", "DOM", "RNG", "MERGE", "INVERSE", "ELLIPSIS",
  "BARRARROW", "MKUNDER", "MKUNDERNAME", "DOT", "DOTHASH", "NUMERAL",
  "LAMBDA", "NEW", "SELF", "ISUNDER", "PREUNDER", "ISOFCLASS", "TILDE",
  "DCL", "COLONEQUALS", "ATOMIC", "DEQRARROW", "RETURN", "IDENTIFIER",
  "BACKTICK", "SLASH", "DIVIDE", "REM", "MOD", "LT", "LTE", "GT", "GTE",
  "NEQ", "OR", "AND", "EQRARROW", "LTEQUALSGT", "INSET", "NOTINSET",
  "SUBSET", "PROPER_SUBSET", "UNION", "BACKSLASH", "INTER", "CARET",
  "DPLUS", "MAPMERGE", "LTCOLON", "LTDASHCOLON", "COLONGT", "COLONDASHGT",
  "COMP", "DSTAR", "FORALL", "EXISTS", "EXISTS1", "STRING", "VRES", "RES",
  "VAL", "HEX_LITERAL", "QUOTE_LITERAL", "AMP", "LSQUAREBAR", "DLSQUARE",
  "DRSQUARE", "BARRSQUARE", "COMMA", "LSQUAREDBAR", "DBARRSQUARE", "COLON",
  "LCURLYBAR", "BARRCURLY", "QUESTION", "BANG", "SLASHCOLON",
  "SLASHBACKSLASH", "COLONBACKSLASH", "LSQUAREGT", "BARGT", "ENDSBY",
  "STARTBY", "COLONINTER", "COLONUNION", "LCURLYCOLON", "COLONRCURLY",
  "LSQUARECOLON", "COLONRSQUARE", "MU", "TBOOL", "TNAT", "TNAT1", "TINT",
  "TRAT", "TREAL", "TCHAR", "TTOKEN", "PRIVATE", "PROTECTED", "PUBLIC",
  "LOGICAL", "nameset", "namesetExpr", "UMINUS", "UPLUS", "$accept",
  "sourceFile", "programParagraphList", "programParagraph", "classDecl",
  "processDecl", "processDef", "process", "replicationDeclaration",
  "replicationDeclarationAlt", "processParagraphList", "processParagraph",
  "actionParagraph", "actionDefinitionList", "actionDefinition",
  "paragraphAction", "action", "communicationParameterList",
  "communicationParameter", "parameter", "paramList", "parallelAction",
  "parametrisationList", "parametrisation", "instantiatedAction",
  "replicatedAction", "renameExpression", "renameList", "channelEvent",
  "channelDecl", "channelDef", "channelNameDecl", "declaration",
  "singleTypeDecl", "chansetDecl", "chansetDefinitionList",
  "chansetDefinition", "chansetExpr", "globalDecl",
  "globalDefinitionBlock", "globalDefinitionBlockAlternative", "classBody",
  "classDefinitionBlock", "classDefinitionBlockAlternative", "typeDefs",
  "typeDefList", "typeDef", "qualifier", "type", "basicType",
  "functionType", "partialFunctionType", "totalFunctionType",
  "quoteLiteral", "fieldList", "field", "invariant", "valueDefs",
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
  "expression", "symbolicLiteral", "numericLiteral", "localDefList",
  "localDef", "ifExpr", "elseExprs", "casesExpr", "casesExprAltList",
  "casesExprAlt", "unaryExpr", "binaryExpr", "mapletList", "maplet",
  "tupleConstructor", "recordConstructor", "lambdaExpr", "generalIsExpr",
  "preconditionExpr", "controlStatement", "nonDeterministicIfStatement",
  "nonDeterministicIfAlt", "nonDeterministicIfAltList", "letStatement",
  "blockStatement", "dclStatement", "assignmentDefList", "assignmentDef",
  "generalAssignStatement", "assignStatementList", "multiAssignStatement",
  "assignStatement", "ifStatement", "elseStatements", "casesStatement",
  "casesStatementAltList", "casesStatementAlt", "returnStatement",
  "specificationStatement", "implicitOperationBody", "pattern",
  "patternLessID", "patternList", "patternIdentifier", "matchValue",
  "tuplePattern", "recordPattern", "bind", "setBind", "typeBind",
  "bindList", "multipleBind", "multipleSetBind", "multipleTypeBind",
  "typeBindList", "path", "unit", "pathList", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final short yyrhs_[] =
  {
       191,     0,    -1,   192,    -1,   228,   192,    -1,   228,    -1,
     193,    -1,   192,   193,    -1,   194,    -1,   195,    -1,   219,
      -1,   224,    -1,     3,   107,     7,   231,    -1,     5,   107,
       7,   196,    -1,   222,     8,   197,    -1,   197,    -1,     9,
     200,     8,   206,     4,    -1,     9,     8,   206,     4,    -1,
     197,    33,   197,    -1,   197,    12,   197,    -1,   197,    11,
     197,    -1,   197,   148,   227,   151,   197,    -1,   197,    17,
     227,    29,   227,    18,   197,    -1,   197,    29,   197,    -1,
     197,    13,   197,    -1,   197,   161,   197,    -1,   197,   160,
     284,   162,   197,    -1,   197,   163,   197,    -1,   197,    17,
     284,   164,   197,    -1,   197,   162,   227,    -1,   197,   166,
     284,    -1,   197,   165,   284,    -1,    14,   222,     8,   107,
      15,    14,   284,    15,    -1,   197,   216,    -1,    33,    26,
     198,     8,   197,    27,    -1,    11,    26,   198,     8,   197,
      27,    -1,    12,    26,   198,     8,   197,    27,    -1,    13,
      26,   198,     8,   197,    27,    -1,   171,   227,   172,    26,
     198,     8,   197,    27,    -1,   199,    -1,   198,    33,   199,
      -1,   223,    -1,   201,    -1,   200,   201,    -1,   233,    -1,
     202,    -1,    10,   203,    -1,    10,   186,   107,     7,   187,
      -1,   204,    -1,   203,   204,    -1,   107,     7,   205,    -1,
     206,    -1,   222,     8,   206,    -1,    19,    -1,    20,    -1,
      21,    -1,    22,    -1,    23,    14,   284,    15,    -1,   107,
      24,   206,    -1,   107,   207,    24,   206,    -1,   155,   284,
     147,   206,    -1,   206,    33,   206,    -1,   206,    12,   206,
      -1,   206,    11,   206,    -1,   206,   161,   206,    -1,   206,
     160,   284,   162,   206,    -1,   206,   163,   206,    -1,   206,
      17,   284,   164,   206,    -1,   206,   162,   227,    -1,   206,
     166,   284,    -1,   206,   165,   284,    -1,   206,   216,    -1,
     211,    -1,    14,   212,     8,   206,    15,    -1,   214,    -1,
     215,    -1,   307,    -1,   308,    -1,   303,    -1,   339,    -1,
     208,    -1,   207,   208,    -1,   158,   209,    -1,   158,   209,
     155,   284,    -1,   159,   284,    -1,   107,    -1,    90,    14,
     210,    15,    -1,    90,    16,    -1,    91,    14,   210,    15,
      -1,    91,    16,    -1,   209,    -1,   210,   152,   209,    -1,
     206,   153,   187,    28,   187,   154,   206,    -1,   206,    13,
     206,    -1,   206,   148,   187,    28,   187,   151,   206,    -1,
     206,    29,   206,    -1,   206,    17,   187,    28,   227,    29,
     227,    28,   187,    18,   206,    -1,   206,    17,   227,    29,
     227,    18,   206,    -1,   206,   148,   187,    28,   227,    28,
     187,   151,   206,    -1,   206,   148,   227,   151,   206,    -1,
     213,    -1,   212,    33,   213,    -1,   144,   223,    -1,   143,
     223,    -1,   142,   223,    -1,    14,   222,     8,   206,    15,
      14,   283,    15,    -1,    14,   212,     8,   206,    15,    14,
     283,    15,    -1,    33,    26,   198,     8,   206,    27,    -1,
      12,    26,   198,     8,   206,    27,    -1,    11,    26,   198,
       8,   206,    27,    -1,   153,   186,   154,    14,   198,     8,
     206,    15,    -1,    13,   198,     8,    17,   187,    18,   206,
      -1,   148,   227,   151,   198,     8,    17,   187,    18,   206,
      -1,    29,   198,     8,    17,   187,    28,   227,    18,   206,
      -1,    29,   198,     8,    17,   187,    18,   206,    -1,   149,
     217,   150,    -1,   149,   217,    28,   334,   150,    -1,   149,
     217,    28,   334,     8,   284,   150,    -1,   218,    25,   218,
      -1,   217,   152,   218,    25,   218,    -1,   107,    -1,    30,
     220,    -1,   221,    -1,   220,    33,   221,    -1,   341,    -1,
     223,    -1,   223,    -1,   222,    33,   223,    -1,   341,   155,
     238,    -1,    31,    -1,    31,   225,    -1,   226,    -1,   225,
     226,    -1,   107,     7,   227,    -1,   107,    -1,   169,   341,
     170,    -1,   156,   341,   157,    -1,   227,   168,   227,    -1,
     227,   167,   227,    -1,   227,   162,   227,    -1,   229,    -1,
     230,    -1,   229,   230,    -1,   234,    -1,   247,    -1,   251,
      -1,     9,   232,     4,    -1,   233,    -1,   233,   232,    -1,
     234,    -1,   247,    -1,   251,    -1,   268,    -1,   280,    -1,
       6,   270,    -1,    32,    -1,    32,   235,    33,    -1,    32,
     235,    -1,   236,    -1,   235,    33,   236,    -1,   237,   107,
       7,   238,   246,    -1,   237,   107,     7,   238,    -1,   237,
     107,    34,   244,    -1,   237,   107,    34,   244,   246,    -1,
     182,    -1,   183,    -1,   184,    -1,   185,    -1,    -1,    14,
     238,    15,    -1,   239,    -1,   243,    -1,    35,   107,    36,
     244,     4,    -1,   238,    28,   238,    -1,   238,    37,   238,
      -1,    17,   238,    18,    -1,    43,   238,    -1,    41,   238,
      -1,    42,   238,    -1,    40,   238,    38,   238,    -1,    39,
     238,    38,   238,    -1,   240,    -1,   107,    -1,   107,    92,
     107,    -1,   107,   108,   107,    -1,   174,    -1,   175,    -1,
     176,    -1,   177,    -1,   178,    -1,   179,    -1,   180,    -1,
     181,    -1,   241,    -1,   242,    -1,   238,    44,   238,    -1,
      16,    44,   238,    -1,   238,    24,   238,    -1,    16,    24,
     238,    -1,   146,    -1,   245,    -1,   244,   245,    -1,   238,
      -1,   107,   155,   238,    -1,   107,    45,   238,    -1,    47,
     324,    46,   284,    -1,    48,   248,    -1,   249,    -1,   249,
      33,    -1,   249,    33,   248,    -1,   237,   250,    -1,   107,
     155,   238,     7,   284,    -1,   325,   155,   238,     7,   284,
      -1,   107,     7,   284,    -1,   325,     7,   284,    -1,    49,
      -1,    49,   252,    -1,   253,    -1,   253,    33,    -1,   253,
      33,   252,    -1,   254,    -1,   255,    -1,   237,   107,   259,
     262,   263,   266,    -1,   237,   256,    -1,   107,   155,   240,
     107,   257,    46,   258,   263,   265,   267,    -1,    16,    -1,
      14,   326,    15,    -1,   257,    14,   326,    15,    -1,   284,
      -1,    53,    -1,    54,    -1,    16,    -1,    14,   260,    15,
      -1,   326,   155,   238,    -1,   260,   152,   326,   155,   238,
      -1,    -1,   262,    -1,   107,   155,   238,    -1,   262,   152,
     107,   155,   238,    -1,   264,    -1,    -1,    50,   284,    -1,
     266,    -1,    -1,    51,   284,    -1,    52,   339,    -1,    -1,
      55,   269,    -1,    55,    -1,   270,    -1,   269,    33,   270,
      -1,   272,    -1,   271,    -1,   237,   107,   155,   273,   107,
     257,    46,   274,   263,   265,    -1,   237,   107,   259,   261,
     275,   263,   266,    -1,   238,   105,   238,    -1,    16,   105,
     238,    -1,   238,   105,    16,    -1,    16,   105,    16,    -1,
     307,    -1,   308,    -1,   303,    -1,    53,    -1,    54,    -1,
     276,    -1,    -1,    56,   277,    -1,   278,    -1,   277,   278,
      -1,   279,   341,    -1,   279,   341,   155,   238,    -1,    57,
      -1,    58,    -1,    59,   281,    -1,    59,    -1,   282,    -1,
     282,    33,    -1,   282,    33,   281,    -1,   237,   311,    -1,
      47,   284,    -1,   284,    -1,   283,   152,   284,    -1,   141,
      -1,    14,   284,    15,    -1,    60,   287,    61,   284,    -1,
     289,    -1,   291,    -1,   294,    -1,   295,    -1,   138,   334,
     147,   284,    -1,   139,   334,   147,   284,    -1,   140,   331,
     147,   284,    -1,    26,    27,    -1,    26,   283,    27,    -1,
      26,   284,    28,   334,    27,    -1,    26,   284,    28,   334,
     147,   284,    27,    -1,    26,   284,    88,   284,    27,    -1,
      17,    18,    -1,    17,   283,    18,    -1,    17,   284,    28,
     332,    18,    -1,    17,   284,    28,   332,   147,   284,    18,
      -1,    26,    89,    27,    -1,    26,   296,    27,    -1,    26,
     297,    28,   334,    27,    -1,    26,   297,    28,   334,   147,
     284,    27,    -1,   298,    -1,   299,    -1,   300,    -1,   301,
      -1,   302,    -1,   100,    14,   339,   152,   284,    15,    -1,
     339,    -1,   285,    -1,   286,    -1,   243,    -1,    94,    -1,
     145,    -1,   288,    -1,   287,   152,   288,    -1,   250,    -1,
     256,    -1,    62,   284,    63,   284,   290,    -1,    65,   284,
      -1,    64,   284,    63,   284,   290,    -1,    66,   284,   155,
     292,     4,    -1,    66,   284,   155,   292,    67,    24,   284,
       4,    -1,   293,    -1,   292,   293,    -1,   326,    24,   284,
      33,    -1,    68,   284,    -1,    69,   284,    -1,    70,   284,
      -1,    71,   284,    -1,    72,   284,    -1,    73,   284,    -1,
      74,   284,    -1,    75,   284,    -1,    76,   284,    -1,    77,
     284,    -1,    78,   284,    -1,    79,   284,    -1,    80,   284,
      -1,    81,   284,    -1,    82,   284,    -1,    83,   284,    -1,
      84,   284,    -1,    85,   284,    -1,    86,   284,    -1,    87,
     284,    -1,   284,    68,   284,    -1,   284,    37,   284,    -1,
     284,    69,   284,    -1,   284,   110,   284,    -1,   284,   109,
     284,    -1,   284,   111,   284,    -1,   284,   112,   284,    -1,
     284,   113,   284,    -1,   284,   114,   284,    -1,   284,   115,
     284,    -1,   284,   116,   284,    -1,   284,     7,   284,    -1,
     284,   117,   284,    -1,   284,   118,   284,    -1,   284,   119,
     284,    -1,   284,   120,   284,    -1,   284,   121,   284,    -1,
     284,   122,   284,    -1,   284,   123,   284,    -1,   284,   124,
     284,    -1,   284,   125,   284,    -1,   284,   126,   284,    -1,
     284,   127,   284,    -1,   284,   128,   284,    -1,   284,   129,
     284,    -1,   284,   130,   284,    -1,   284,   131,   284,    -1,
     284,   132,   284,    -1,   284,   133,   284,    -1,   284,   134,
     284,    -1,   284,   135,   284,    -1,   284,   136,   284,    -1,
     284,   137,   284,    -1,   297,    -1,   296,   152,   297,    -1,
     284,    89,   284,    -1,    90,    14,   283,    15,    -1,    91,
      14,   283,    15,    -1,    95,   338,   147,   284,    -1,    98,
     107,    14,   284,    15,    -1,    98,   107,    92,   107,    14,
     284,    15,    -1,    98,   107,   108,   107,    14,   284,    15,
      -1,    98,   239,    14,   284,    15,    -1,    98,    14,   284,
     152,   238,    15,    -1,    99,    14,   283,    15,    -1,   304,
      -1,   316,    -1,   318,    -1,   312,    -1,   322,    -1,   321,
      -1,    62,   284,    24,   206,     4,    -1,    62,   284,    24,
     206,   306,     4,    -1,    28,   284,    24,   206,    -1,   305,
      -1,   306,   305,    -1,    60,   287,    61,   206,    -1,    14,
     206,    15,    -1,    14,   309,   206,    15,    -1,   102,   310,
       8,    -1,   311,    -1,   310,   152,   311,    -1,   107,   155,
     238,    -1,   107,   155,   238,   103,   284,    -1,   107,   155,
     238,    61,   284,    -1,   315,    -1,   314,    -1,   315,    -1,
     313,    33,   315,    -1,   104,    14,   313,    15,    -1,   339,
     103,   284,    -1,    62,   284,    63,   206,   317,    65,   206,
      -1,    62,   284,    63,   206,    65,   206,    -1,    64,   284,
      63,   206,    -1,   317,    64,   284,    63,   206,    -1,    66,
     284,   155,   319,     4,    -1,    66,   284,   155,   319,    67,
      24,   206,     4,    -1,   320,    -1,   320,   152,   319,    -1,
     326,    24,   206,    -1,   106,    33,    -1,   106,   284,    -1,
      17,   323,    18,    -1,   275,   263,   266,    -1,   327,    -1,
     325,    -1,   328,    -1,   329,    -1,   330,    -1,   324,    -1,
     326,   152,   324,    -1,   107,    -1,    69,    -1,   285,    -1,
      14,   284,    15,    -1,    90,    14,   326,   152,   324,    15,
      -1,    91,    16,    -1,    91,    14,   326,    15,    -1,   332,
      -1,   333,    -1,   324,   122,   284,    -1,   324,   155,   238,
      -1,   335,    -1,   334,   152,   335,    -1,   336,    -1,   337,
      -1,   326,   122,   284,    -1,   326,   155,   238,    -1,   333,
      -1,   338,   152,   333,    -1,   340,    -1,   339,   101,    -1,
     339,    92,   340,    -1,   339,   108,   340,    -1,   339,    93,
      94,    -1,   339,    16,    -1,   339,    14,   283,    15,    -1,
     339,    14,   284,    88,   284,    15,    -1,    97,    -1,   107,
      -1,   339,    -1,   341,   152,   339,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     8,    10,    12,    15,    17,    19,
      21,    23,    28,    33,    37,    39,    45,    50,    54,    58,
      62,    68,    76,    80,    84,    88,    94,    98,   104,   108,
     112,   116,   125,   128,   135,   142,   149,   156,   165,   167,
     171,   173,   175,   178,   180,   182,   185,   191,   193,   196,
     200,   202,   206,   208,   210,   212,   214,   219,   223,   228,
     233,   237,   241,   245,   249,   255,   259,   265,   269,   273,
     277,   280,   282,   288,   290,   292,   294,   296,   298,   300,
     302,   305,   308,   313,   316,   318,   323,   326,   331,   334,
     336,   340,   348,   352,   360,   364,   376,   384,   394,   400,
     402,   406,   409,   412,   415,   424,   433,   440,   447,   454,
     463,   471,   481,   491,   499,   503,   509,   517,   521,   527,
     529,   532,   534,   538,   540,   542,   544,   548,   552,   554,
     557,   559,   562,   566,   568,   572,   576,   580,   584,   588,
     590,   592,   595,   597,   599,   601,   605,   607,   610,   612,
     614,   616,   618,   620,   623,   625,   629,   632,   634,   638,
     644,   649,   654,   660,   662,   664,   666,   668,   669,   673,
     675,   677,   683,   687,   691,   695,   698,   701,   704,   709,
     714,   716,   718,   722,   726,   728,   730,   732,   734,   736,
     738,   740,   742,   744,   746,   750,   754,   758,   762,   764,
     766,   769,   771,   775,   779,   784,   787,   789,   792,   796,
     799,   805,   811,   815,   819,   821,   824,   826,   829,   833,
     835,   837,   844,   847,   858,   860,   864,   869,   871,   873,
     875,   877,   881,   885,   891,   892,   894,   898,   904,   906,
     907,   910,   912,   913,   916,   919,   920,   923,   925,   927,
     931,   933,   935,   946,   954,   958,   962,   966,   970,   972,
     974,   976,   978,   980,   982,   983,   986,   988,   991,   994,
     999,  1001,  1003,  1006,  1008,  1010,  1013,  1017,  1020,  1023,
    1025,  1029,  1031,  1035,  1040,  1042,  1044,  1046,  1048,  1053,
    1058,  1063,  1066,  1070,  1076,  1084,  1090,  1093,  1097,  1103,
    1111,  1115,  1119,  1125,  1133,  1135,  1137,  1139,  1141,  1143,
    1150,  1152,  1154,  1156,  1158,  1160,  1162,  1164,  1168,  1170,
    1172,  1178,  1181,  1187,  1193,  1202,  1204,  1207,  1212,  1215,
    1218,  1221,  1224,  1227,  1230,  1233,  1236,  1239,  1242,  1245,
    1248,  1251,  1254,  1257,  1260,  1263,  1266,  1269,  1272,  1276,
    1280,  1284,  1288,  1292,  1296,  1300,  1304,  1308,  1312,  1316,
    1320,  1324,  1328,  1332,  1336,  1340,  1344,  1348,  1352,  1356,
    1360,  1364,  1368,  1372,  1376,  1380,  1384,  1388,  1392,  1396,
    1400,  1404,  1406,  1410,  1414,  1419,  1424,  1429,  1435,  1443,
    1451,  1457,  1464,  1469,  1471,  1473,  1475,  1477,  1479,  1481,
    1487,  1494,  1499,  1501,  1504,  1509,  1513,  1518,  1522,  1524,
    1528,  1532,  1538,  1544,  1546,  1548,  1550,  1554,  1559,  1563,
    1571,  1578,  1583,  1589,  1595,  1604,  1606,  1610,  1614,  1617,
    1620,  1624,  1628,  1630,  1632,  1634,  1636,  1638,  1640,  1644,
    1646,  1648,  1650,  1654,  1661,  1664,  1669,  1671,  1673,  1677,
    1681,  1683,  1687,  1689,  1691,  1695,  1699,  1701,  1705,  1707,
    1710,  1714,  1718,  1722,  1725,  1730,  1737,  1739,  1741,  1743
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   366,   366,   371,   378,   386,   392,   403,   404,   405,
     406,   411,   437,   448,   455,   463,   470,   477,   483,   489,
     495,   501,   507,   513,   519,   532,   539,   546,   559,   566,
     573,   584,   605,   618,   619,   620,   621,   629,   633,   634,
     638,   664,   670,   681,   685,   692,   698,   702,   708,   717,
     729,   733,   740,   745,   750,   755,   760,   767,   774,   789,
     796,   803,   810,   817,   830,   837,   850,   863,   870,   877,
     884,   899,   903,   904,   905,   906,   907,   908,   915,   924,
     930,   941,   947,   954,   970,   975,   979,   983,   988,   996,
    1002,  1011,  1012,  1013,  1014,  1015,  1016,  1017,  1018,  1022,
    1023,  1027,  1028,  1029,  1033,  1037,  1048,  1049,  1050,  1051,
    1052,  1053,  1054,  1055,  1060,  1065,  1069,  1076,  1083,  1093,
    1127,  1140,  1147,  1163,  1174,  1183,  1189,  1204,  1217,  1222,
    1230,  1236,  1245,  1255,  1266,  1272,  1284,  1297,  1310,  1359,
    1366,  1373,  1384,  1390,  1395,  1406,  1413,  1420,  1430,  1434,
    1438,  1444,  1450,  1457,  1463,  1472,  1482,  1495,  1501,  1510,
    1524,  1537,  1548,  1571,  1579,  1587,  1595,  1601,  1610,  1614,
    1618,  1623,  1624,  1628,  1629,  1633,  1641,  1649,  1657,  1666,
    1675,  1688,  1689,  1690,  1694,  1698,  1702,  1706,  1710,  1714,
    1718,  1722,  1770,  1774,  1781,  1794,  1810,  1823,  1839,  1849,
    1855,  1864,  1868,  1874,  1882,  1894,  1935,  1941,  1947,  1957,
    1967,  1984,  1997,  2011,  2028,  2035,  2047,  2053,  2059,  2068,
    2072,  2079,  2101,  2111,  2127,  2132,  2139,  2149,  2153,  2157,
    2164,  2168,  2175,  2182,  2193,  2196,  2203,  2210,  2220,  2225,
    2231,  2238,  2243,  2249,  2261,  2265,  2271,  2278,  2286,  2299,
    2310,  2314,  2321,  2331,  2353,  2354,  2355,  2356,  2360,  2361,
    2362,  2363,  2367,  2374,  2379,  2385,  2392,  2398,  2407,  2411,
    2418,  2422,  2449,  2454,  2461,  2469,  2477,  2486,  2490,  2501,
    2507,  2527,  2550,  2555,  2562,  2566,  2570,  2574,  2579,  2589,
    2598,  2608,  2617,  2624,  2634,  2646,  2657,  2666,  2676,  2686,
    2698,  2706,  2716,  2726,  2737,  2741,  2745,  2749,  2753,  2762,
    2782,  2783,  2787,  2797,  2805,  2811,  2820,  2826,  2836,  2840,
    2849,  2869,  2879,  2896,  2907,  2921,  2928,  2938,  2962,  2969,
    2976,  2983,  2990,  2997,  3004,  3011,  3018,  3025,  3032,  3039,
    3046,  3053,  3060,  3067,  3074,  3081,  3088,  3095,  3107,  3112,
    3117,  3122,  3127,  3132,  3137,  3142,  3147,  3152,  3157,  3162,
    3167,  3172,  3177,  3182,  3187,  3192,  3197,  3202,  3207,  3212,
    3217,  3222,  3227,  3232,  3237,  3242,  3247,  3252,  3257,  3262,
    3267,  3275,  3281,  3291,  3304,  3318,  3341,  3368,  3369,  3370,
    3379,  3390,  3405,  3424,  3428,  3432,  3442,  3446,  3447,  3468,
    3472,  3479,  3488,  3494,  3505,  3514,  3520,  3530,  3538,  3544,
    3555,  3563,  3564,  3568,  3572,  3576,  3577,  3581,  3590,  3601,
    3607,  3616,  3624,  3636,  3644,  3655,  3662,  3671,  3681,  3682,
    3692,  3696,  3704,  3705,  3709,  3710,  3711,  3715,  3721,  3730,
    3740,  3745,  3758,  3764,  3768,  3774,  3786,  3790,  3797,  3807,
    3817,  3823,  3832,  3836,  3840,  3850,  3860,  3867,  3883,  3884,
    3885,  3886,  3887,  3888,  3889,  3890,  3894,  3895,  3903,  3910
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
     185,   186,   187,   188,   189
  };

  private static final short yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 5508;
  private static final int yynnts_ = 152;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 49;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 190;

  private static final int yyuser_token_number_max_ = 444;
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

    /* FIXME
     * needs to throw an error if the name is multipart
     */
    private List<LexIdentifierToken> convertNameListToIdentifierList(List<LexNameToken> nameList) {
      List<LexIdentifierToken> out = new Vector<LexIdentifierToken>();
      for (LexNameToken name : nameList) {
	out.add(extractLexIdentifierToken(name));
      }
      return out;
    }

    private PStateDesignator convertToStateDesignator(PDesignator designator)
    {
	PStateDesignator sd = null;
	if(designator instanceof ANameDesignator) {
	    ANameDesignator ns = (ANameDesignator)designator;
	    LexNameToken name = extractLexNameToken(ns.getName());
	    sd = new AIdentifierStateDesignator(name.getLocation(), null, name);
	} else if (designator instanceof APrimaryDesignator) {
	    APrimaryDesignator primary = (APrimaryDesignator)designator;
	    if(primary.getPrimary() instanceof ACallPrimary) {
		ACallCallStatementControlStatementAction call = (ACallCallStatementControlStatementAction) ((ACallPrimary)primary.getPrimary()).getCall();
		if(call.getArgs().size() != 1) {
		    throw new RuntimeException("Missing implementation in convertToStateDesignator method. Throw nice error about map ot sequence apply only takes one arg");
		}
		sd = new AMapSeqStateDesignator(call.getLocation(), null, call.getArgs().get(0));
	    } else {
		throw new RuntimeException("Missing implementation in convertToStateDesignator method. Currently only ACallPrimary is implemented");
	    }
	} else {
	    throw new RuntimeException("Missing implementation in convertToStateDesignator method. sd was null");
	}
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
      } else if (sn.getIdentifiers().size() == 2) {
	lnt = new LexNameToken(sn.getIdentifiers().get(0).getName(), sn.getIdentifiers().get(1).getName(),
			       combineLexLocation(extractFirstLexLocation(sn.getIdentifiers()),
						  extractLastLexLocation(sn.getIdentifiers())));
      } else {
	lnt = new LexNameToken("Default", sn.getIdentifiers().get(0));
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


    private LexLocation extractLastLexLocation(List<?> fields)
    {
      try
	{
	  Object o = fields.get(0);
	  Class<?> clz = o.getClass();

	  Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});

	  LexLocation candidate = (LexLocation)locMethod.invoke(o, null);
	  for(Object p : fields)
	    {
	      LexLocation pLoc = (LexLocation)locMethod.invoke(o, null);
	      if (pLoc.endOffset > candidate.endOffset)
		candidate = pLoc;
	    }
	  return candidate;
	} catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
    }

    private LexLocation extractFirstLexLocation(List<?> fields)
    {
      try
	{
	  Object o = fields.get(0);
	  Class<?> clz = o.getClass();

	  Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});

	  LexLocation candidate = (LexLocation)locMethod.invoke(o, null);
	  for(Object p : fields)
	    {
	      LexLocation pLoc = (LexLocation)locMethod.invoke(o, null);
	      if (pLoc.startOffset < candidate.startOffset)
		candidate = pLoc;
	    }
	  return candidate;
	} catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
    }

    private< T extends PPattern> LexLocation extractLexLeftMostFromPatterns(List<T> ptrns)
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

    private LexIdentifierToken extractLexIdentifierToken(LexNameToken name)
    {
      return new LexIdentifierToken(name.getName(), false, name.getLocation());
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
	      scanner = new CmlLexer(new java.io.FileReader(file));
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
/* Line 8569 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


