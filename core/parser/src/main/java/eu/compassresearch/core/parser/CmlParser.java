
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
/* Line 8 of "src/main/bison/cml.y"  */


  // ******************************
  // *** required local imports ***
  // ******************************

  // required standard Java definitions
  import java.text.DecimalFormat;
  import java.math.BigInteger;
  import java.util.*;
  import java.io.File;
  import java.io.FileReader;
  import java.io.FileNotFoundException;
  import java.io.InputStreamReader;
  import java.io.Reader;
  import java.lang.reflect.*;

  import org.overture.ast.factory.AstFactory;
  import org.overture.ast.definitions.*;
  import org.overture.ast.expressions.*;
  import org.overture.ast.lex.*;
  import org.overture.ast.node.*;
  import org.overture.ast.node.tokens.*;
  import org.overture.ast.patterns.*;
  import org.overture.ast.preview.*;
  import org.overture.ast.statements.*;
  import org.overture.ast.types.*;
  import org.overture.ast.typechecker.NameScope;
  import org.overture.ast.util.*;

  import eu.compassresearch.ast.actions.*;
  import eu.compassresearch.ast.declarations.*;
  import eu.compassresearch.ast.definitions.*;
  import eu.compassresearch.ast.expressions.*;
  import eu.compassresearch.ast.patterns.*;
  import eu.compassresearch.ast.process.*;
  import eu.compassresearch.ast.program.*;
  import eu.compassresearch.ast.types.*;
  import eu.compassresearch.core.lexer.CmlLexeme;
  import eu.compassresearch.core.lexer.CmlLexer;
  import eu.compassresearch.core.lexer.Position;
  import eu.compassresearch.core.parser.util.*;




/* Line 40 of cmlskeleton.java.m4  */
/* Line 96 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

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
	  case 2:
  if (yyn == 2)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 314 of "src/main/bison/cml.y"  */
    {
  List<SParagraphDefinition> paragraphs = (List<SParagraphDefinition>) ((yystack.valueAt (1-(1))));
  currentSource.setParagraphs(paragraphs);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 322 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> programParagraphList = new LinkedList<PDefinition>();
  programParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = programParagraphList;
};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 328 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> programParagraphList = (List<PDefinition>)((yystack.valueAt (2-(1))));
  if (programParagraphList == null)
    programParagraphList = new Vector<PDefinition>();
  programParagraphList.add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = programParagraphList;
};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 338 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 339 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 340 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 341 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 342 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 347 of "src/main/bison/cml.y"  */
    {
  AClassParagraphDefinition clz = new AClassParagraphDefinition();
  LexNameToken lexName = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),(CmlLexeme)((yystack.valueAt (6-(6)))));
  clz.setLocation(loc);
  clz.setName(lexName);
  clz.setDefinitions((List<PDefinition>)((yystack.valueAt (6-(5)))));
  clz.setNameScope(NameScope.CLASSNAME);
  yyval = clz;
};
  break;
    

  case 11:
  if (yyn == 11)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 358 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),(CmlLexeme)((yystack.valueAt (8-(8)))));
  List<LexNameToken> supernames = new LinkedList<LexNameToken>();
  supernames.add(util.extractLexNameToken(((yystack.valueAt (8-(5))))));
  yyval = new AClassParagraphDefinition(location,
                                     util.extractLexNameToken(((yystack.valueAt (8-(2))))),
                                     NameScope.CLASSNAME,
                                     false,
                                     null,//ClassDefinition
                                     util.getDefaultAccessSpecifier(false,false,null),
                                     null/*PType type_  should this be the namedInvariantType*/,
                                     null,//Pass
                                     (List<? extends PDefinition>)((yystack.valueAt (8-(7)))),
                                     new LinkedList<PType>() /* supertypes_*/,
                                     supernames,
                                     new LinkedList<PDefinition>()/* definitions_*/,
                                     new LinkedList<PDefinition>() /*allInheritedDefinitions_*/,
                                     new LinkedList<PDefinition>() /*localInheritedDefinitions_*/,
                                     null /*Boolean hasContructors_*/,
                                     null,
                                     new LinkedList<AClassParagraphDefinition>()/* superDefs_*/,
                                     true/*Boolean gettingInheritable_*/,
                                     new LinkedList<PDefinition>() /*superInheritedDefinitions_*/,
                                     null /*Boolean gettingInvDefs_*/,
                                     false,
                                     false /*Boolean isUndefined_*/,
                                     null/*PType classtype_*/,
                                     false /*Boolean isTypeChecked_*/,
                                     null/*AExplicitOperationDefinition invariant_*/);
};
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 391 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, process.getLocation());
  AProcessDefinition processDef = new AProcessDefinition(process.getLocation(),
                                                         NameScope.GLOBAL,
                                                         false,
                                                         access,
                                                         null,//Pass
                                                         null,
                                                         process);
  LexNameToken id = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  processDef.setName(id);
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                            processDef.getLocation());
  access = util.getDefaultAccessSpecifier(true, false, location);
  processDef.setName(id);
  AProcessParagraphDefinition p = new AProcessParagraphDefinition(location,
                                                                  id,
                                                                  NameScope.PROCESSNAME,
                                                                  false,
                                                                  null,//VDM ClassDefinition
                                                                  access,
                                                                  null,//Type
                                                                  null,//Pass
                                                                  processDef);
  yyval = p;
};
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 419 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (6-(4))));
  PProcess process = (PProcess)((yystack.valueAt (6-(6))));
  LexLocation loc = util.combineLexLocation(util.extractFirstLexLocation(decls),
                                       process.getLocation());
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, loc);
  AProcessDefinition processDef = new AProcessDefinition(loc,
                                                         NameScope.GLOBAL,
                                                         false,
                                                         access,
                                                         null,//Pass
                                                         decls,
                                                         process);
  LexNameToken id = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));
  processDef.setName(id);
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
                                            processDef.getLocation());
  access = util.getDefaultAccessSpecifier(true, false, location);
  processDef.setName(id);
  AProcessParagraphDefinition p = new AProcessParagraphDefinition(location,
                                                                  id,
                                                                  NameScope.PROCESSNAME,
                                                                  false,
                                                                  null,//VDM ClassDefinition
                                                                  access,
                                                                  null,//Type
                                                                  null,//Pass
                                                                  processDef);
  yyval = p;
};
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 453 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  List<SParagraphDefinition> processParagraphs = null;
  PAction action = (PAction)((yystack.valueAt (4-(3))));
  yyval = new AStateProcess(location, processParagraphs, action);
};
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 460 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  List<SParagraphDefinition> processParagraphs = (List<SParagraphDefinition>)((yystack.valueAt (5-(2))));
  PAction action = (PAction)((yystack.valueAt (5-(4))));
  yyval = new AStateProcess(location, processParagraphs, action);
};
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 468 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASequentialCompositionProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 474 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AExternalChoiceProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 480 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInternalChoiceProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 489 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  yyval = new AGeneralisedParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 498 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (7-(1))));
  PProcess right = (PProcess)((yystack.valueAt (7-(7))));
  yyval = new AAlphabetisedParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (7-(3)))), (PExp)((yystack.valueAt (7-(5)))), right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 504 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 510 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 516 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInterruptProcess(location, left, right);
};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 531 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimedInterruptProcess(location, left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 538 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AUntimedTimeoutProcess(location, left, right);
};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 553 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimeoutProcess(location, left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 571 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PExp cse = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), cse.getLocation());
  yyval = new AHidingProcess(location, left, cse);
};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 578 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AStartDeadlineProcess(location, left, exp);
};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 585 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AEndDeadlineProcess(location, left, exp);
};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 595 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8)))));
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (8-(2))));
  List<PExp> args = new LinkedList<PExp>();
  args.add((PExp)((yystack.valueAt (8-(7)))));
  PProcess proc = (PProcess)((yystack.valueAt (8-(4))));
  /* FIXME: (->AKM) process was a processDefinition */
  /* $$ = new AInstantiationProcess(location,  */
  /*                             decls,  */
  /*                             null, */
  /*                             (AProcessDefinition)$proc,  */
  /*                             args); */
};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 609 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken(((yystack.valueAt (1-(1)))));
  yyval = new AInstantiationProcess(name.location,
                                 null,
                                 name,
                                 null,
                                 null);
};
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 618 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken(((yystack.valueAt (2-(1)))));
  LexLocation location = util.extractLexLocation(name.location,(CmlLexeme)((yystack.valueAt (2-(2)))));
  yyval = new AInstantiationProcess(location,
                                 null,
                                 name,
                                 null,
                                 null);
};
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 628 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken(((yystack.valueAt (4-(1)))));
  LexLocation location = util.extractLexLocation(name.location,(CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AInstantiationProcess(location,
                                 null,
                                 name,
                                 null,
                                 (List<PExp>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 34:
  if (yyn == 34)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 638 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PProcess process = (PProcess)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingProcess(util.combineLexLocation(process.getLocation(),
                                                      renameExpression.getLocation()),
                                   process,
                                   renameExpression);
};
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 648 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new ASequentialCompositionReplicatedProcess(location,
                                                   (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                                   process);
};
  break;
    

  case 36:
  if (yyn == 36)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 656 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new AExternalChoiceReplicatedProcess(location,
                                            (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                            process);
};
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 664 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new AInternalChoiceReplicatedProcess(location,
                                            (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                            process);
};
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 675 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (6-(6))));
  PExp chansetExp = (PExp)((yystack.valueAt (6-(2))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),process.getLocation());
  yyval = new AGeneralisedParallelismReplicatedProcess(location,
                                                    (List<SSingleDeclaration>)((yystack.valueAt (6-(4)))),
                                                    process,
                                                    chansetExp);
};
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 688 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (7-(7))));
  PExp chansetExp = (PExp)((yystack.valueAt (7-(5))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),process.getLocation());
  yyval = new AAlphabetisedParallelismReplicatedProcess(location,
                                                     (List<SSingleDeclaration>)((yystack.valueAt (7-(2)))),
                                                     process,
                                                     chansetExp);
};
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 698 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new ASynchronousParallelismReplicatedProcess(location,
                                                    (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                                    process);
};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 706 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new AInterleavingReplicatedProcess(location,
                                          (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                          process);
};
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 717 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 723 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 729 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 735 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 752 of "src/main/bison/cml.y"  */
    {
  LexNameToken id = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  List<LexNameToken> ids = new Vector<LexNameToken>();
  ids.add(id);
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.extractLexLocation(id.getLocation(),
                                            exp.getLocation());
  yyval = new AExpressionSingleDeclaration(location,
                                        NameScope.LOCAL,
                                        ids,
                                        exp);
};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 765 of "src/main/bison/cml.y"  */
    {
  LexNameToken id = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  AExpressionSingleDeclaration decls = (AExpressionSingleDeclaration)((yystack.valueAt (3-(3))));
  decls.getIdentifiers().add(0, id);
  decls.setLocation(util.extractLexLocation(id.getLocation(),
                                       decls.getLocation()));
  yyval = decls;
};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 777 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = processParagraphList;
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 783 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> processParagraphList = (List<PDefinition>)((yystack.valueAt (2-(1))));
  /* if (processParagraphList == null) // FIXME: surely this cannot possibly happen? -jwc */
  /*   processParagraphList = new Vector<PDefinition>(); */
  processParagraphList.add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = processParagraphList;
};
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 799 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 803 of "src/main/bison/cml.y"  */
    {
  List<AActionDefinition> actionDefinitions = (List<AActionDefinition>)((yystack.valueAt (2-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), util.extractLastLexLocation(actionDefinitions));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, loc);
  yyval = new AActionParagraphDefinition( loc, NameScope.LOCAL, false, access,null/*Pass*/, actionDefinitions);
};
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 810 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 814 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 821 of "src/main/bison/cml.y"  */
    {
  Object[] pa = (Object[])((yystack.valueAt (3-(3))));
  List<ATypeSingleDeclaration> declarations = (List<ATypeSingleDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), action.getLocation());
  AActionDefinition actionDefinition = new AActionDefinition(defLocation,
                                                             NameScope.LOCAL,
                                                             false,
                                                             null,//Access
                                                             null,//Pass
                                                             declarations,
                                                             action);
  List<AActionDefinition> actionDefs = new Vector<AActionDefinition>();
  actionDefs.add(actionDefinition);
  yyval = actionDefs;
};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 838 of "src/main/bison/cml.y"  */
    {
  List<AActionDefinition> actionDefs = (List<AActionDefinition>)((yystack.valueAt (4-(1))));
  Object[] pa = (Object[])((yystack.valueAt (4-(4))));
  List<ATypeSingleDeclaration> declarations = (List<ATypeSingleDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(2))))), action.getLocation());
  AActionDefinition actionDefinition = new AActionDefinition(defLocation,
                                                             NameScope.LOCAL,
                                                             false,
                                                             null,//Access
                                                             null,//Pass
                                                             declarations,
                                                             action);
  actionDefs.add(actionDefinition);
  yyval = actionDefs;
};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 864 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 868 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 875 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{new Vector<ATypeSingleDeclaration>(), ((yystack.valueAt (1-(1))))};
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 879 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3))))};
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 886 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASkipAction(location);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 891 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AStopAction(location);
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 896 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AChaosAction(location);
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 901 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ADivAction(location);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 906 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
  yyval = new AWaitAction(location, exp);
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 912 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  PAction action = (PAction)((yystack.valueAt (3-(2))));
  yyval = new ABlockStatementAction(location, null, action);
};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 929 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseCommunicationAction(((yystack.valueAt (5-(2)))),(PAction)((yystack.valueAt (5-(5)))));
  
  /* need to merge in old action rule RARROW parser action and the old communication rule actions; along with an expression -> communication conversion?
   */
  //ACommunicationAction comAction = (ACommunicationAction)$communication;
  //PAction to = (PAction)$to;
  //LexLocation location = util.extractLexLocation(comAction.getLocation(), to.getLocation());
  // comAction.setAction(to);
  // comAction.setLocation(location);
  // $$ = comAction;
};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 953 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  PAction action = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = util.combineLexLocation(exp.getLocation(), action.getLocation());
  yyval = new AGuardedAction(location, exp, action);
};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 960 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ASequentialCompositionAction(location, left, right);
};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 967 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AExternalChoiceAction(location, left, right);
};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 974 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInternalChoiceAction(location, left, right);
};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 981 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInterruptAction(location, left, right);
};
  break;
    

  case 72:
  if (yyn == 72)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 996 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (5-(1))));
  PAction right = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimedInterruptAction(location, left, right, (PExp)((yystack.valueAt (5-(3)))));
};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1003 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AUntimedTimeoutAction(location, left, right);
};
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1018 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (5-(1))));
  PAction right = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimeoutAction(location, left, right, (PExp)((yystack.valueAt (5-(3)))));
};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1034 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PExp chansetExp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), chansetExp.getLocation());
  yyval = new AHidingAction(location, left, chansetExp);
};
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1041 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AStartDeadlineAction(location, left, exp);
};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1048 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AEndDeadlineAction(location, left, exp);
};
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1055 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PAction action = (PAction)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingAction(util.combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1067 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* that expressionList needs to be converted down into a nameList?
   */
  List<LexNameToken> nameList = (List<LexNameToken>)((yystack.valueAt (6-(2))));
  List<LexIdentifierToken> ids = util.convertNameListToIdentifierList(nameList);
  yyval = new AMuAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),(CmlLexeme)((yystack.valueAt (6-(6))))),
                     ids,
                     (List<PAction>)((yystack.valueAt (6-(5)))));
};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1081 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (7-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (7-(7))));
  yyval = new AInterleavingParallelAction(util.extractLexLocation(leftAction.getLocation(),
                                                          rightAction.getLocation()),
                                       leftAction,
                                       (PExp)((yystack.valueAt (7-(3)))),
                                       (PExp)((yystack.valueAt (7-(5)))) ,
                                       rightAction);
};
  break;
    

  case 81:
  if (yyn == 81)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1092 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (3-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingParallelAction(util.extractLexLocation(leftAction.getLocation(),
                                                          rightAction.getLocation()),
                                       leftAction,
                                       null,
                                       null,
                                       rightAction);
};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1106 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (7-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (7-(7))));
  yyval = new ASynchronousParallelismParallelAction(util.extractLexLocation(leftAction.getLocation(),
                                                                    rightAction.getLocation()),
                                                 leftAction,
                                                 (PExp)((yystack.valueAt (7-(3)))),
                                                 (PExp)((yystack.valueAt (7-(5)))),
                                                 rightAction);
};
  break;
    

  case 83:
  if (yyn == 83)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1117 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (3-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismParallelAction(util.extractLexLocation(leftAction.getLocation(),
                                                                    rightAction.getLocation()),
                                                 leftAction,
                                                 null,
                                                 null,
                                                 rightAction);
};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1131 of "src/main/bison/cml.y"  */
    {
    PAction leftAction = (PAction)((yystack.valueAt (11-(1))));
    PAction rightAction = (PAction)((yystack.valueAt (11-(11))));
    LexLocation location = util.extractLexLocation(leftAction.getLocation(),
                                              rightAction.getLocation());
    yyval = new AAlphabetisedParallelismParallelAction(location,
                                                    leftAction,
                                                    (PExp)((yystack.valueAt (11-(3)))),
                                                    (PExp)((yystack.valueAt (11-(9)))),
                                                    rightAction,
                                                    (PExp)((yystack.valueAt (11-(5)))),
                                                    (PExp)((yystack.valueAt (11-(7)))));
};
  break;
    

  case 85:
  if (yyn == 85)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1148 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (7-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = util.extractLexLocation(leftAction.getLocation(),
                                            rightAction.getLocation());
  yyval = new AAlphabetisedParallelismParallelAction(location,
                                                  leftAction,
                                                  null,
                                                  null,
                                                  rightAction,
                                                  (PExp)((yystack.valueAt (7-(3)))),
                                                  (PExp)((yystack.valueAt (7-(5)))));
};
  break;
    

  case 86:
  if (yyn == 86)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1165 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (9-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (9-(9))));
  LexLocation location = util.extractLexLocation(leftAction.getLocation(),
                                            rightAction.getLocation());
  yyval = new AGeneralisedParallelismParallelAction(location,
                                                 leftAction,
                                                 (PExp)((yystack.valueAt (9-(3)))),
                                                 (PExp)((yystack.valueAt (9-(7)))),
                                                 rightAction,
                                                 (PExp)((yystack.valueAt (9-(5)))));
};
  break;
    

  case 87:
  if (yyn == 87)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1181 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (5-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = util.extractLexLocation(leftAction.getLocation(),
                                            rightAction.getLocation());
  yyval = new AGeneralisedParallelismParallelAction(location,
                                                 leftAction,
                                                 null,
                                                 null,
                                                 rightAction,
                                                 (PExp)((yystack.valueAt (5-(3)))));
};
  break;
    

  case 88:
  if (yyn == 88)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1200 of "src/main/bison/cml.y"  */
    {
  yyval = new AParametrisedAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),
                                                  (CmlLexeme)((yystack.valueAt (5-(5))))),
                               (List<PParametrisation>)((yystack.valueAt (5-(2)))),
                               (PAction)((yystack.valueAt (5-(4)))));
};
  break;
    

  case 89:
  if (yyn == 89)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1207 of "src/main/bison/cml.y"  */
    {
  AParametrisedAction action = new AParametrisedAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),
                                                                          (CmlLexeme)((yystack.valueAt (8-(5))))),
                                                       (List<PParametrisation>)((yystack.valueAt (8-(2)))),
                                                       (PAction)((yystack.valueAt (8-(4)))));
  yyval = new AParametrisedInstantiatedAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),
                                                              (CmlLexeme)((yystack.valueAt (8-(8))))),
                                           action,
                                           (List<PExp>)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 90:
  if (yyn == 90)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1218 of "src/main/bison/cml.y"  */
    {
  yyval = new ADeclarationInstantiatedAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8))))),
                                          (List<? extends ATypeSingleDeclaration>)((yystack.valueAt (8-(2)))),
                                          (PAction)((yystack.valueAt (8-(4)))),
                                          (List<PExp>)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 91:
  if (yyn == 91)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1225 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new ASequentialCompositionReplicatedAction(location,
                                                  (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                                  action);
};
  break;
    

  case 92:
  if (yyn == 92)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1233 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AExternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                           action);
};
  break;
    

  case 93:
  if (yyn == 93)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1241 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AInternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                           action);
};
  break;
    

  case 94:
  if (yyn == 94)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1252 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (6-(6))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
                                            replicatedAction.getLocation());
  yyval = new AInterleavingReplicatedAction(location,
                                         (List<SSingleDeclaration>)((yystack.valueAt (6-(4)))),
                                         replicatedAction,
                                         (PExp)((yystack.valueAt (6-(2)))));
};
  break;
    

  case 95:
  if (yyn == 95)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1265 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
                                            replicatedAction.getLocation());
  yyval = new AInterleavingReplicatedAction(location,
                                         (List<SSingleDeclaration>)((yystack.valueAt (7-(2)))),
                                         replicatedAction,
                                         (PExp)((yystack.valueAt (7-(5)))));
};
  break;
    

  case 96:
  if (yyn == 96)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1278 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (9-(9))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))),
                                            replicatedAction.getLocation());
  yyval = new AGeneralisedParallelismReplicatedAction(location,
                                                   (List<SSingleDeclaration>)((yystack.valueAt (9-(4)))),
                                                   replicatedAction,
                                                   (PExp)((yystack.valueAt (9-(2)))),
                                                   (PExp)((yystack.valueAt (9-(7)))));
};
  break;
    

  case 97:
  if (yyn == 97)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1292 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (9-(9))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))),replicatedAction.getLocation());
  yyval = new AAlphabetisedParallelismReplicatedAction(location,
                                                    (List<? extends SSingleDeclaration>)((yystack.valueAt (9-(2)))),
                                                    replicatedAction,
                                                    (PExp)((yystack.valueAt (9-(5)))),
                                                    (PExp)((yystack.valueAt (9-(7)))));
};
  break;
    

  case 98:
  if (yyn == 98)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1305 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),replicatedAction.getLocation());
  yyval = new ASynchronousParallelismReplicatedAction(location,
                                                   (List<? extends SSingleDeclaration>)((yystack.valueAt (7-(2)))),
                                                   replicatedAction,
                                                   (PExp)((yystack.valueAt (7-(5)))));
};
  break;
    

  case 99:
  if (yyn == 99)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1317 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                            action.getLocation());
  yyval = new ALetStatementAction(location,
                               action,
                               (List<? extends PDefinition>)((yystack.valueAt (4-(2)))));
};
  break;
    

  case 100:
  if (yyn == 100)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1326 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))), (CmlLexeme)((yystack.valueAt (6-(6)))));
  ADeclareStatementAction dclStm =
    new ADeclareStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(2)))),
                                                   (CmlLexeme)((yystack.valueAt (6-(4))))),
                                (List<? extends PDefinition>)((yystack.valueAt (6-(3)))));
  PAction action = (PAction)((yystack.valueAt (6-(5))));
  yyval = new ABlockStatementAction(location, dclStm, action);
};
  break;
    

  case 101:
  if (yyn == 101)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1336 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 102:
  if (yyn == 102)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1340 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)((yystack.valueAt (3-(2))));
  yyval = new ANonDeterministicIfStatementAction(location,
                                              alternatives);
};
  break;
    

  case 103:
  if (yyn == 103)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1348 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)((yystack.valueAt (3-(2))));
  yyval = new ANonDeterministicDoStatementAction(location,
                                              alternatives);
};
  break;
    

  case 104:
  if (yyn == 104)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1368 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 105:
  if (yyn == 105)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1372 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AMultipleGeneralAssignmentStatementAction(location,
                                                     (List<? extends ASingleGeneralAssignmentStatementAction>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1379 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1383 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 108:
  if (yyn == 108)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1387 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 109:
  if (yyn == 109)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1398 of "src/main/bison/cml.y"  */
    {
  yyval = new AReturnStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), null);
};
  break;
    

  case 110:
  if (yyn == 110)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1402 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (4-(3))));
  yyval = new AReturnStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                                     exp.getLocation()),
                                  exp);
};
  break;
    

  case 111:
  if (yyn == 111)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1419 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* Need to rip out the path-based stuff here.
   * rule was: | path COLONEQUALS NEW path LRPAREN
   */
  ANewStatementAction stm = null;
  // these were Paths
  PExp target = (PExp)((yystack.valueAt (4-(1)))); //should probably be more specific, typewise
  PExp newExp = (PExp)((yystack.valueAt (4-(4))));
  List<? extends PExp> args = null;
  LexLocation location = util.combineLexLocation(target.getLocation(),newExp.getLocation());
  //stm = new ANewStatementAction(location,
  //                              target.convertToStateDesignator(),
  //                              newExp.convertToName(),
  //                              args);
  yyval = stm;
};
  break;
    

  case 112:
  if (yyn == 112)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1456 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 113:
  if (yyn == 113)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1466 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (6-(6))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),action.getLocation());
  PBind bind = (PBind)((yystack.valueAt (6-(2))));
  ADefPatternBind patternBind = new ADefPatternBind(bind.getLocation(),
                                                    null,
                                                    bind,
                                                    null,
                                                    null);
  yyval = new AForSequenceStatementAction(location,
                                       patternBind,
                                       (PExp)((yystack.valueAt (6-(4)))),
                                       action);
};
  break;
    

  case 114:
  if (yyn == 114)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1482 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (6-(6))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),action.getLocation());
  PPattern pattern = (PPattern)((yystack.valueAt (6-(2))));
  ADefPatternBind patternBind = new ADefPatternBind(pattern.getLocation(),
                                                    pattern,
                                                    null,
                                                    null,
                                                    null);
  yyval = new AForSequenceStatementAction(location,
                                       patternBind,
                                       (PExp)((yystack.valueAt (6-(4)))),
                                       action);
};
  break;
    

  case 115:
  if (yyn == 115)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1498 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),action.getLocation());
  yyval = new AForSetStatementAction(location,
                                  (PPattern)((yystack.valueAt (7-(3)))),
                                  (PExp)((yystack.valueAt (7-(5)))),
                                  action);
};
  break;
    

  case 116:
  if (yyn == 116)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1507 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (8-(8))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),action.getLocation());
  yyval = new AForIndexStatementAction(location,
                                    util.extractLexNameToken(((yystack.valueAt (8-(2))))),
                                    (PExp)((yystack.valueAt (8-(4)))) ,
                                    (PExp)((yystack.valueAt (8-(6)))),
                                    null,
                                    action);
};
  break;
    

  case 117:
  if (yyn == 117)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1518 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (10-(10))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (10-(1)))),action.getLocation());
  yyval = new AForIndexStatementAction(location,
                                    util.extractLexNameToken(((yystack.valueAt (10-(2))))),
                                    (PExp)((yystack.valueAt (10-(4)))) ,
                                    (PExp)((yystack.valueAt (10-(6)))),
                                    (PExp)((yystack.valueAt (10-(8)))),
                                    action);
};
  break;
    

  case 118:
  if (yyn == 118)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1529 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AWhileStatementAction(location,
                                 (PExp)((yystack.valueAt (4-(2)))),
                                 action);
};
  break;
    

  case 119:
  if (yyn == 119)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1544 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = new LinkedList<PAction>();
  actionList.add((PAction)((yystack.valueAt (1-(1)))));
  yyval = actionList;
};
  break;
    

  case 120:
  if (yyn == 120)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1550 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = (List<PAction>)((yystack.valueAt (3-(1))));
  actionList.add((PAction)((yystack.valueAt (3-(3)))));
  yyval = actionList;
};
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1559 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add((PParametrisation)((yystack.valueAt (1-(1)))));
  yyval = plist;
};
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1565 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add(0,(PParametrisation)((yystack.valueAt (3-(3)))));
  yyval = plist;
};
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1574 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AValParametrisation(loc, decl);
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1580 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AResParametrisation(loc, decl);
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1586 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AVresParametrisation(loc, decl);
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1595 of "src/main/bison/cml.y"  */
    {
  yyval = new AEnumerationRenameChannelExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
                                                           (CmlLexeme)((yystack.valueAt (3-(3))))),
                                        (List<? extends ARenamePair>)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1601 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* path elimination */
  ARenamePair pair = new ARenamePair(false,
                                     (ANameChannelExp)((yystack.valueAt (7-(2)))),
                                     (ANameChannelExp)((yystack.valueAt (7-(4)))));
  yyval = new AComprehensionRenameChannelExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
                                                             (CmlLexeme)((yystack.valueAt (7-(7))))),
                                          pair,
                                          (List<? extends PMultipleBind>)((yystack.valueAt (7-(6)))),
                                          null);
};
  break;
    

  case 128:
  if (yyn == 128)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1614 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* path elimination */
  ARenamePair pair = new ARenamePair(false,
                                     (ANameChannelExp)((yystack.valueAt (9-(2)))),
                                     (ANameChannelExp)((yystack.valueAt (9-(4)))));
  yyval = new AComprehensionRenameChannelExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))),
                                                             (CmlLexeme)((yystack.valueAt (9-(9))))),
                                          pair,
                                          (List<? extends PMultipleBind>)((yystack.valueAt (9-(6)))),
                                          (PExp)((yystack.valueAt (9-(8)))));
};
  break;
    

  case 129:
  if (yyn == 129)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1639 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* path elimination: from, to */
  List<ARenamePair> renamePairs = new Vector<ARenamePair>();
  ARenamePair pair = new ARenamePair(false,
                                     (ANameChannelExp)((yystack.valueAt (3-(1)))),
                                     (ANameChannelExp)((yystack.valueAt (3-(3)))));
  renamePairs.add(pair);
  yyval = renamePairs;
};
  break;
    

  case 130:
  if (yyn == 130)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1650 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* path elimination: from, to */
  List<ARenamePair> renamePairs = (List<ARenamePair>)((yystack.valueAt (5-(1))));
  ARenamePair pair = new ARenamePair(false,
                                     (ANameChannelExp)((yystack.valueAt (5-(3)))),
                                     (ANameChannelExp)((yystack.valueAt (5-(5)))));
  renamePairs.add(pair);
  yyval = renamePairs;
};
  break;
    

  case 131:
  if (yyn == 131)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1670 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDefinition> chanNameDecls = new Vector<AChannelNameDefinition>();
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, location);
  AChannelParagraphDefinition channelDefinition =
    new AChannelParagraphDefinition(location,
                                    NameScope.GLOBAL,
                                    false,
                                    access,
                                    null,//Pass
                                    chanNameDecls);
  channelDefinition.setName(new LexNameToken("","",new LexLocation()));
  yyval = channelDefinition;
};
  break;
    

  case 132:
  if (yyn == 132)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1685 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDefinition> chanNameDecls = (List<AChannelNameDefinition>)((yystack.valueAt (2-(2))));
  LexLocation start = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation end = (chanNameDecls != null && chanNameDecls.size() > 0) ?
    chanNameDecls.get(chanNameDecls.size()-1).getLocation() : start;
  LexLocation location = util.combineLexLocation(start, end);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, start);
  AChannelParagraphDefinition channelDefinition =
    new AChannelParagraphDefinition(location,
                                    NameScope.GLOBAL,
                                    false,
                                    access,
                                    null,//Pass
                                    chanNameDecls);
  channelDefinition.setName(new LexNameToken("","",new LexLocation()));
  yyval = channelDefinition;
};
  break;
    

  case 133:
  if (yyn == 133)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1706 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDefinition> decls = new Vector<AChannelNameDefinition>();
  decls.add((AChannelNameDefinition)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 134:
  if (yyn == 134)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1712 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDefinition> decls = (List<AChannelNameDefinition>)((yystack.valueAt (2-(1))));
  decls.add((AChannelNameDefinition)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1729 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* I (jwc) have no idea if this is correct.  When did
   * AChannelNameDefinition become AChannelNameDeclaration?
   */
  LexIdentifierToken decl = util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  LexLocation location = decl.getLocation();
  List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
  ids.add(decl);
  ATypeSingleDeclaration singleTypeDeclaration =
    new ATypeSingleDeclaration(location, NameScope.GLOBAL, ids, null);
  AChannelNameDefinition channelNameDecl =
    new AChannelNameDefinition(location,
                                NameScope.GLOBAL,
                                false, 
                                null, 
                                null, 
                                singleTypeDeclaration);
  yyval = channelNameDecl;
};
  break;
    

  case 136:
  if (yyn == 136)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1750 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* I (jwc) have no idea if this is correct.  When did
   * AChannelNameDefinition become AChannelNameDeclaration?
   */
  ATypeSingleDeclaration singleTypeDeclaration = (ATypeSingleDeclaration)((yystack.valueAt (1-(1))));
  AChannelNameDefinition channelNameDecl =
    new AChannelNameDefinition(singleTypeDeclaration.getLocation(),
                               NameScope.GLOBAL,
                               false,
                               null,
                               null,
                               singleTypeDeclaration);
  yyval = channelNameDecl;
};
  break;
    

  case 137:
  if (yyn == 137)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1771 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = new Vector<ATypeSingleDeclaration>();
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 138:
  if (yyn == 138)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1777 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1786 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  LexLocation location = id.getLocation();
  List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
  ids.add(id);
  ATypeSingleDeclaration singleTypeDeclaration =
    new ATypeSingleDeclaration(location,
                               NameScope.LOCAL,
                               ids,
                               (PType)((yystack.valueAt (3-(3)))));
  yyval = singleTypeDeclaration;
};
  break;
    

  case 140:
  if (yyn == 140)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1799 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (3-(3))));
  decl.setLocation(util.combineLexLocation(id.getLocation(), decl.getLocation()));
  decl.getIdentifiers().addFirst(id);
  yyval = decl;
};
  break;
    

  case 141:
  if (yyn == 141)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1809 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access =
    new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                        new TStatic(),
                                        new TAsync());
  AChansetParagraphDefinition chansetParagraph =
    new AChansetParagraphDefinition(loc,
                                    NameScope.GLOBAL,
                                    false,
                                    access,
                                    null/*Pass*/,
                                    new LinkedList<AChansetDefinition>());
  yyval = chansetParagraph;
};
  break;
    

  case 142:
  if (yyn == 142)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1825 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  List<AChansetDefinition> chansetDefinitions = (List<AChansetDefinition>)((yystack.valueAt (2-(2))));
  AAccessSpecifierAccessSpecifier access =
    new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                        new TStatic(),
                                        new TAsync());
  AChansetParagraphDefinition chansetParagraph =
    new AChansetParagraphDefinition(loc,
                                    NameScope.GLOBAL,
                                    false,
                                    access,
                                    null/*Pass*/,
                                    chansetDefinitions);
  yyval = chansetParagraph;
};
  break;
    

  case 143:
  if (yyn == 143)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1845 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 144:
  if (yyn == 144)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1851 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(1))));
  defs.add((AChansetDefinition)((yystack.valueAt (2-(2)))));
  yyval = defs;
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1863 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.combineLexLocation(id.getLocation(), exp.getLocation());
  yyval = new AChansetDefinition(location,
                              NameScope.GLOBAL,
                              false/*used*/,
                              null/*access*/,
                              null/*Pass*/,
                              id,
                              exp);
};
  break;
    

  case 146:
  if (yyn == 146)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1879 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 147:
  if (yyn == 147)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1883 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1887 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1894 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1900 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  defs.add((PDefinition)((yystack.valueAt (2-(1)))));
  yyval = defs;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1909 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 152:
  if (yyn == 152)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1913 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 153:
  if (yyn == 153)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1917 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1921 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1925 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1935 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (2-(2))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),def.getLocation());
  yyval = new AInitialParagraphDefinition(location,
                                       NameScope.GLOBAL,
                                       true,
                                       util.getDefaultAccessSpecifier(false,false,null),
                                       null/*Pass*/,
                                       def);
};
  break;
    

  case 157:
  if (yyn == 157)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1949 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     util.getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     null);
};
  break;
    

  case 158:
  if (yyn == 158)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1959 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)((yystack.valueAt (2-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       util.extractLastLexLocation(typeDefinitions));
  yyval = new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     util.getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     typeDefinitions);
};
  break;
    

  case 159:
  if (yyn == 159)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1971 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)((yystack.valueAt (3-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))),
                                       util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     util.getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     typeDefinitions);
};
  break;
    

  case 160:
  if (yyn == 160)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1986 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
  yyval = list;
};
  break;
    

  case 161:
  if (yyn == 161)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1992 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
  list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
  yyval = list;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2001 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  LexLocation location = null;
  location = util.combineLexLocation(name.getLocation(), ((PType)((yystack.valueAt (4-(4))))).getLocation());
  yyval = new ATypeDefinition(location,
                           NameScope.TYPENAME,
                           false/*Boolean used_*/,
                           null/*VDM ClassDef*/,
                           access,
                           (PType)((yystack.valueAt (4-(4)))),
                           null/*Pass*/,
                           null/*SInvariantType invType_*/,
                           null/*PPattern invPattern_*/,
                           null/*PExp invExpression_*/,
                           null /*AExplicitFunctionDefinition invdef_*/,
                           false/*Boolean infinite_*/,
                           name);
};
  break;
    

  case 163:
  if (yyn == 163)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2021 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2)))));
  AInvariantDefinition inv = (AInvariantDefinition)((yystack.valueAt (5-(5))));
  LexLocation location = null;
  location = util.combineLexLocation(name.getLocation(), inv.getLocation());
  yyval = new ATypeDefinition(location,
                           NameScope.TYPENAME,
                           false/*Boolean used_*/,
                           null/*VDM ClassDef*/,
                           access,
                           (PType)((yystack.valueAt (5-(4)))),
                           null/*Pass*/,
                           null/*SInvariantType invType_*/,
                           inv.getPattern()/*PPattern invPattern_*/,
                           inv.getExpression()/*PExp invExpression_*/,
                           null /*AExplicitFunctionDefinition invdef_*/,
                           false/*Boolean infinite_*/,
                           name);
};
  break;
    

  case 164:
  if (yyn == 164)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2042 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (4-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(name.getLocation(), util.extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc,
                                                          false,
                                                          null,
                                                          false,
                                                          null,
                                                          name,
                                                          fields,
                                                          true);
  yyval = new ATypeDefinition(loc, /* FIXME: this should end with the fieldList */
                           NameScope.GLOBAL,
                           false,
                           null/*VDM ClassDef*/,
                           access,
                           recType,
                           null/*Pass*/,
                           null,
                           null,
                           null,
                           null,
                           true,
                           name);
};
  break;
    

  case 165:
  if (yyn == 165)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2071 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* Not sure why, but we're not actually using the invariant
   * definition here.
   */
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2)))));
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (5-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (5-(4))));
  AInvariantDefinition inv = (AInvariantDefinition)((yystack.valueAt (5-(5))));
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.
  LexLocation loc = util.combineLexLocation(name.getLocation(), util.extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc,
                                                          false,
                                                          null,
                                                          false,
                                                          null, /* invdef */
                                                          name,
                                                          fields,
                                                          true);
  yyval = new ATypeDefinition(loc,
                           NameScope.TYPENAME,
                           false,
                           null/*VDM ClassDef*/,
                           access,
                           recType,
                           null/*Pass*/,
                           null,
                           null,
                           null,
                           null,
                           true,
                           name);
};
  break;
    

  case 166:
  if (yyn == 166)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2118 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 167:
  if (yyn == 167)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2125 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new AProtectedAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 168:
  if (yyn == 168)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2132 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 169:
  if (yyn == 169)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2139 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 170:
  if (yyn == 170)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2146 of "src/main/bison/cml.y"  */
    {
  /*Default private*/
  yyval = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 171:
  if (yyn == 171)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2156 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (3-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  type.setLocation(loc);
  yyval = type;
};
  break;
    

  case 172:
  if (yyn == 172)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2163 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 173:
  if (yyn == 173)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2167 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken quote = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteType(quote.location, false, null, quote);
};
  break;
    

  case 174:
  if (yyn == 174)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2172 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (5-(4))));
  yyval = new ARecordInvariantType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5))))),
                                false,
                                null,//definitions
                                false,//opaque
                                null,//invdef
                                util.extractLexNameToken(((yystack.valueAt (5-(2))))),
                                (List<? extends AFieldField>)((yystack.valueAt (5-(4)))),
                                false/*infinite_*/);
};
  break;
    

  case 175:
  if (yyn == 175)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2184 of "src/main/bison/cml.y"  */
    {
  PType first = (PType)((yystack.valueAt (3-(1))));
  PType second = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(first.getLocation(), second.getLocation());
  AUnionType utype;
  LinkedList<PType> types;
  if (first instanceof AUnionType) {
    // First is a UnionType
    utype = (AUnionType)first;
    types = utype.getTypes();
    if (second instanceof AUnionType) {
      // Second is also a union type, so merge the type lists
      types.addAll(((AUnionType)second).getTypes());
    } else {
      // Second is not a union type, so just add it to the type list
      types.add(second);
    }
  } else if (second instanceof AUnionType) {
    // First isn't union, but Second is, so just add first to the type list
    utype = (AUnionType)second;
    types = utype.getTypes();
    types.add(first);
  } else {
    // Neither is a union type
    types = new LinkedList<PType>();
    types.add(first);
    types.add(second);
    utype = new AUnionType(loc, false, false, false);
    utype.setTypes(types);
  }
  utype.setLocation(loc);
  yyval = utype;
};
  break;
    

  case 176:
  if (yyn == 176)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2218 of "src/main/bison/cml.y"  */
    {
  PType first = (PType)((yystack.valueAt (3-(1))));
  PType second = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(first.getLocation(), second.getLocation());
  AProductType ptype;
  LinkedList<PType> types;
  if (first instanceof AProductType) {
    ptype = (AProductType)first;
    types = ptype.getTypes();
    if (second instanceof AProductType) {
      types.addAll(((AProductType)second).getTypes());
    } else {
      types.add(second);
    }
  } else if (second instanceof AProductType) {
    ptype = (AProductType)second;
    types = ptype.getTypes();
    types.add(first);
  } else {
    types = new LinkedList<PType>();
    types.add(first);
    types.add(second);
    ptype = new AProductType(loc, false, null, types);
  }
  ptype.setLocation(loc);
  yyval = ptype;
};
  break;
    

  case 177:
  if (yyn == 177)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2246 of "src/main/bison/cml.y"  */
    {
  yyval = new AOptionalType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))),
                         false,
                         null,
                         (PType)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 178:
  if (yyn == 178)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2253 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASetType(loc, false, null, type, false, false);
};
  break;
    

  case 179:
  if (yyn == 179)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2260 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASeqSeqType(loc, false, null, type, false);
};
  break;
    

  case 180:
  if (yyn == 180)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2267 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASeq1SeqType(loc, false, null, type, false);
};
  break;
    

  case 181:
  if (yyn == 181)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2274 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (4-(2))));
  PType rng = (PType)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))),
                                       rng.getLocation());
  yyval = new AMapMapType(loc, false, null, dom, rng, false);
};
  break;
    

  case 182:
  if (yyn == 182)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2282 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (4-(2))));
  PType rng = (PType)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))),
                                       rng.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, dom, rng, false);
};
  break;
    

  case 183:
  if (yyn == 183)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2290 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 184:
  if (yyn == 184)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2302 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* Convert the dottedIdentifier into a LexNameToken?
   * Old code from the IDENTIFIER DOT IDENTIFIER production below.
   */
  /* LexNameToken name = util.extractLexNameToken((CmlLexeme)$3); */
  /* name = new LexNameToken(((CmlLexeme)$1).getValue(),name.getIdentifier()); */
  /* ANamedInvariantType type = new ANamedInvariantType(); */
  /* type.setLocation(name.getLocation()); */
  /* type.setName(name); */
  /* $$ = type; */
};
  break;
    

  case 185:
  if (yyn == 185)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2322 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1))))));
  yyval = ids;
};
  break;
    

  case 186:
  if (yyn == 186)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2328 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = ids;
};
  break;
    

  case 187:
  if (yyn == 187)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2334 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = ids;
};
  break;
    

  case 188:
  if (yyn == 188)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2343 of "src/main/bison/cml.y"  */
    {
  yyval = new ABooleanBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 189:
  if (yyn == 189)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2347 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 190:
  if (yyn == 190)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2351 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatOneNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 191:
  if (yyn == 191)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2355 of "src/main/bison/cml.y"  */
    {
  yyval = new AIntNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 192:
  if (yyn == 192)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2359 of "src/main/bison/cml.y"  */
    {
  yyval = new ARationalNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 193:
  if (yyn == 193)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2363 of "src/main/bison/cml.y"  */
    {
  yyval = new ARealNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 194:
  if (yyn == 194)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2367 of "src/main/bison/cml.y"  */
    {
  yyval = new ACharBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 195:
  if (yyn == 195)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2371 of "src/main/bison/cml.y"  */
    {
  yyval = new ATokenBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 196:
  if (yyn == 196)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2378 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 197:
  if (yyn == 197)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2382 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 198:
  if (yyn == 198)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2389 of "src/main/bison/cml.y"  */
    {
  PType domType = (PType)((yystack.valueAt (3-(1))));
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, true, params, rngType);
};
  break;
    

  case 199:
  if (yyn == 199)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2398 of "src/main/bison/cml.y"  */
    {
  PType domType = new AVoidType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, true, params, rngType);
};
  break;
    

  case 200:
  if (yyn == 200)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2410 of "src/main/bison/cml.y"  */
    {
  PType domType = (PType)((yystack.valueAt (3-(1))));
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, false, params, rngType);
};
  break;
    

  case 201:
  if (yyn == 201)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2419 of "src/main/bison/cml.y"  */
    {
  PType domType = new AVoidType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, false, params, rngType);
};
  break;
    

  case 202:
  if (yyn == 202)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2431 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 203:
  if (yyn == 203)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2437 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = (List<AFieldField>)((yystack.valueAt (2-(1))));
  res.add((AFieldField)((yystack.valueAt (2-(2)))));
  yyval = res;
};
  break;
    

  case 204:
  if (yyn == 204)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2446 of "src/main/bison/cml.y"  */
    {
  yyval = new AFieldField(util.getDefaultAccessSpecifier(false,false,null),
                       null,
                       null,
                       (PType)((yystack.valueAt (1-(1)))),
                       false);
};
  break;
    

  case 205:
  if (yyn == 205)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2454 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  yyval = new AFieldField(util.getDefaultAccessSpecifier(false,false,null),
                       name,
                       null,
                       type,
                       false);
};
  break;
    

  case 206:
  if (yyn == 206)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2464 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    PType type = (PType)((yystack.valueAt (3-(3))));
    yyval = new AFieldField(util.getDefaultAccessSpecifier(false,false,null),
                         name,
                         null,
                         type,
                         true);
};
  break;
    

  case 207:
  if (yyn == 207)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2477 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), exp.getLocation());
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, true, loc);
  yyval = new AInvariantDefinition(loc,
                                null, // decided later
                                NameScope.LOCAL,
                                false,
                                null, // VDM ClassDef
                                access,
                                null, // decided later
                                null, // Pass
                                (PPattern)((yystack.valueAt (4-(2)))),
                                exp);
};
  break;
    

  case 208:
  if (yyn == 208)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2505 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new Vector<PDefinition>();
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, location);
  yyval = new AValueParagraphDefinition(location,
                                     NameScope.NAMES,
                                     false,
                                     access,
                                     null, // Pass
                                     defs);
};
  break;
    

  case 209:
  if (yyn == 209)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2517 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            util.extractLastLexLocation(defs));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  yyval = new AValueParagraphDefinition(location,
                                     NameScope.NAMES,
                                     false,
                                     access,
                                     null,//Pass
                                     defs);
};
  break;
    

  case 210:
  if (yyn == 210)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2547 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 211:
  if (yyn == 211)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2553 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (3-(3))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 212:
  if (yyn == 212)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2563 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
  PDefinition def = (PDefinition)((yystack.valueAt (2-(2))));
  def.setAccess(access);
  yyval = def;
};
  break;
    

  case 213:
  if (yyn == 213)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2573 of "src/main/bison/cml.y"  */
    {
  PExp expression = (PExp)((yystack.valueAt (3-(3))));
  LexNameToken lnt = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,
                                                  null,
                                                  false,
                                                  lnt,
                                                  false/*constrained*/);
  AValueDefinition vdef =
    new AValueDefinition(util.combineLexLocation(idp.getLocation(),
                                            expression.getLocation()),
                            NameScope.LOCAL,
                            false, // used
                            util.getPrivateAccessSpecifier(false, false, lnt.location),
                            null, // pass
                            idp,
                            expression,
                            null // defs
                            );
  vdef.setName(lnt);
  vdef.setType(null);
  yyval = vdef;
};
  break;
    

  case 214:
  if (yyn == 214)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2597 of "src/main/bison/cml.y"  */
    {
  LexNameToken lnt = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(1)))));
  PType type = (PType)((yystack.valueAt (5-(3))));
  PExp expression = (PExp)((yystack.valueAt (5-(5))));
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,
                                                  null,
                                                  false,
                                                  lnt,
                                                  false/*constrained*/);
  // Build the resulting AValueDefinition
  AValueDefinition vdef =
    new AValueDefinition(util.combineLexLocation(idp.getLocation(),
                                            expression.getLocation()),
                         NameScope.LOCAL,
                         false, // used
                         util.getPrivateAccessSpecifier(false, false, lnt.location),
                         null, // pass
                         idp,
                         expression,
                         null // defs
                         );
  vdef.setName(lnt);
  vdef.setType(type);
  yyval = vdef;
};
  break;
    

  case 215:
  if (yyn == 215)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2623 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PExp expression = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(pattern.getLocation(),
                                       expression.getLocation());
  AValueDefinition vdef =
    new AValueDefinition(loc,
                         NameScope.LOCAL,
                         false, // used
                         util.getPrivateAccessSpecifier(false, false, loc),
                         null, // pass
                         pattern,
                         expression,
                         null // defs
                         );
  vdef.setName(null);
  vdef.setType(null);
  yyval = vdef;
};
  break;
    

  case 216:
  if (yyn == 216)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2643 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (5-(1))));
  PType type = (PType)((yystack.valueAt (5-(3))));
  PExp expression = (PExp)((yystack.valueAt (5-(5))));
  LexLocation loc = util.combineLexLocation(pattern.getLocation(),
                                       expression.getLocation());
  AValueDefinition vdef =
    new AValueDefinition(loc,
                         NameScope.LOCAL,
                         false, // used
                         util.getPrivateAccessSpecifier(false, false, loc),
                         null, // pass
                         pattern,
                         expression,
                         null // defs
                         );
  // FIXE wtf.setName -- esp. w.r.t. previous action
  vdef.setName(new LexNameToken("Default", "plesstypeexp", loc, false, false));
  vdef.setType(type);
  yyval = vdef;
};
  break;
    

  case 217:
  if (yyn == 217)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2668 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  List<PDefinition> functionDefs = new LinkedList<PDefinition>();
  yyval = new AFunctionParagraphDefinition(location,
                                        NameScope.GLOBAL,
                                        false,
                                        access,
                                        null,//Pass
                                        functionDefs);
};
  break;
    

  case 218:
  if (yyn == 218)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2682 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  List<PDefinition> functionDefs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  yyval = new AFunctionParagraphDefinition(location,
                                        NameScope.GLOBAL,
                                        false,
                                        access,
                                        null,//Pass
                                        functionDefs);
};
  break;
    

  case 219:
  if (yyn == 219)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2711 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = new Vector<PDefinition>();
  functionList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = functionList;
};
  break;
    

  case 220:
  if (yyn == 220)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2717 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = (List<PDefinition>)((yystack.valueAt (2-(1))));
  functionList.add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = functionList;
};
  break;
    

  case 221:
  if (yyn == 221)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2726 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (6-(1))));
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));
  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)((yystack.valueAt (6-(3))));
  APatternTypePair result = (APatternTypePair)((yystack.valueAt (6-(4))));
  PExp preExp = (PExp)((yystack.valueAt (6-(5))));
  PExp postExp = (PExp)((yystack.valueAt (6-(6))));
  LexLocation location = util.combineLexLocation(name.getLocation(), postExp.getLocation());
  AImplicitFunctionDefinition impFunc =
    new AImplicitFunctionDefinition(location,
                                    NameScope.LOCAL,
                                    false,
                                    access,
                                    null,//Pass
                                    null,
                                    paramPatterns,
                                    result,
                                    preExp,
                                    postExp,
                                    null/*LexNameToken measure*/);
  impFunc.setName(name);
  yyval = impFunc;
};
  break;
    

  case 222:
  if (yyn == 222)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2750 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
  AExplicitFunctionDefinition func = (AExplicitFunctionDefinition)((yystack.valueAt (2-(2))));
  func.setAccess(access);
  yyval = func;
};
  break;
    

  case 223:
  if (yyn == 223)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2760 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (10-(1)))));
  /* --- TODO --- */
  /* We should be checking that the two IDENTIFIERS are equivalent
   */
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (10-(1)))));
  AFunctionType ftype = (AFunctionType)((yystack.valueAt (10-(3))));
  PExp functionBody = (PExp)((yystack.valueAt (10-(7))));
  List<List<PPattern>> args = (List<List<PPattern>>)((yystack.valueAt (10-(5))));
  AExplicitFunctionDefinition res = new AExplicitFunctionDefinition();
  res.setName(name);
  res.setLocation(loc);
  res.setType(ftype);
  res.setBody(functionBody);
  res.setMeasure((LexNameToken)((yystack.valueAt (10-(10)))));
  res.setParamPatternList(args);
  yyval = res;
};
  break;
    

  case 224:
  if (yyn == 224)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2782 of "src/main/bison/cml.y"  */
    {
  yyval =  new Vector<List<PPattern>>();
};
  break;
    

  case 225:
  if (yyn == 225)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2786 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(2))));
  List<List<PPattern>> paramList = new Vector<List<PPattern>>();
  paramList.add(patternList);
  yyval = paramList;
};
  break;
    

  case 226:
  if (yyn == 226)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2793 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (4-(3))));
  List<List<PPattern>> paramList = (List<List<PPattern>>)((yystack.valueAt (4-(1))));
  paramList.add(patternList);
  yyval = paramList;
};
  break;
    

  case 227:
  if (yyn == 227)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2803 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 228:
  if (yyn == 228)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2807 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 229:
  if (yyn == 229)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2811 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 230:
  if (yyn == 230)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2818 of "src/main/bison/cml.y"  */
    {
  yyval = new Vector<APatternListTypePair>();
};
  break;
    

  case 231:
  if (yyn == 231)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2822 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 232:
  if (yyn == 232)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2829 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(1))));
  List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
  pltpl.add(new APatternListTypePair(false, patternList, (PType)((yystack.valueAt (3-(3))))));
  yyval = pltpl;
};
  break;
    

  case 233:
  if (yyn == 233)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2836 of "src/main/bison/cml.y"  */
    {
  List<APatternListTypePair> pltpl = (List<APatternListTypePair>)((yystack.valueAt (5-(1))));
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (5-(3))));
  pltpl.add(new APatternListTypePair(false, patternList, (PType)((yystack.valueAt (5-(5))))));
  yyval = pltpl;
};
  break;
    

  case 234:
  if (yyn == 234)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2846 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 235:
  if (yyn == 235)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2850 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 236:
  if (yyn == 236)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2857 of "src/main/bison/cml.y"  */
    {
  List<APatternTypePair> typePairs = new LinkedList<APatternTypePair>();
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  APatternTypePair typePair = 
      new APatternTypePair(false,
			   new AIdentifierPattern(name.getLocation(),
						  new LinkedList<PDefinition>(),
						  false,
						  name,
						  false /*FIXME what is constrained_??*/),
			   (PType)((yystack.valueAt (3-(3)))));
  typePairs.add(typePair);
  yyval = typePairs;
};
  break;
    

  case 237:
  if (yyn == 237)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2872 of "src/main/bison/cml.y"  */
    {
  List<APatternTypePair> typePairs = (List<APatternTypePair>)((yystack.valueAt (5-(1))));
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(3)))));
  APatternTypePair typePair =
      new APatternTypePair(null,
			   new AIdentifierPattern(name.getLocation(),
						  new LinkedList<PDefinition>(),
						  false,
						  name,
						  false /*FIXME what is constrained_??*/),
			   (PType)((yystack.valueAt (5-(5)))));
  typePairs.add(typePair);
  yyval = typePairs;
};
  break;
    

  case 238:
  if (yyn == 238)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2889 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 239:
  if (yyn == 239)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2890 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 240:
  if (yyn == 240)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2894 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 241:
  if (yyn == 241)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2898 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 242:
  if (yyn == 242)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2899 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 243:
  if (yyn == 243)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2903 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 244:
  if (yyn == 244)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2914 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* dottedIdentifier should be a list of LexIdentifierWhatsits, and
   * we need a LexName here.
   */
};
  break;
    

  case 245:
  if (yyn == 245)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2921 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 246:
  if (yyn == 246)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2928 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  yyval = new AOperationParagraphDefinition(location,
                                         NameScope.LOCAL,
                                         false,
                                         access,
                                         null,//Pass
                                         null);
};
  break;
    

  case 247:
  if (yyn == 247)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2941 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  List<? extends SOperationDefinition> opDefinitions =
    (List<? extends SOperationDefinition>)((yystack.valueAt (2-(2))));
  yyval = new AOperationParagraphDefinition(location,
                                         NameScope.LOCAL,
                                         false,
                                         access,
                                         null,//Pass
                                         opDefinitions);
};
  break;
    

  case 248:
  if (yyn == 248)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2962 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (1-(1)))));
  yyval = opDefinitions;
};
  break;
    

  case 249:
  if (yyn == 249)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2968 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)((yystack.valueAt (2-(1))));
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (2-(2)))));
  yyval = opDefinitions;
};
  break;
    

  case 250:
  if (yyn == 250)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2977 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (7-(1))));
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (7-(2)))));
  List<? extends APatternListTypePair> parameterPatterns =
    (List<? extends APatternListTypePair>)((yystack.valueAt (7-(3))));
   List<? extends APatternTypePair> result =
     (List<? extends APatternTypePair>)((yystack.valueAt (7-(4))));
  List<? extends AExternalClause> externals = (List<? extends AExternalClause>)((yystack.valueAt (7-(5))));
  PExp precondition = (PExp)((yystack.valueAt (7-(6))));
  PExp postcondition = (PExp)((yystack.valueAt (7-(7))));
  LexLocation location = util.combineLexLocation(name.location, postcondition.getLocation());
  eu.compassresearch.ast.definitions.AImplicitOperationDefinition res =
    new eu.compassresearch.ast.definitions.AImplicitOperationDefinition(location,
                                                                        NameScope.GLOBAL,
                                     false,
                                     access,
                                     null,//Pass
                                     parameterPatterns,
                                     result,
                                     externals,
                                     precondition,
                                     postcondition,
                                     null, null);
  res.setName(name);
  yyval = res;
};
  break;
    

  case 251:
  if (yyn == 251)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3004 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* We shold check id against checkId for equality */
  LexNameToken name = util.extractLexNameToken(((yystack.valueAt (10-(2)))));
  SStatementAction body = (SStatementAction)((yystack.valueAt (10-(8))));
  LexLocation loc = util.extractLexLocation(name.location,
                                       body.getLocation());
  eu.compassresearch.ast.definitions.AExplicitOperationDefinition res =
    new eu.compassresearch.ast.definitions.AExplicitOperationDefinition();
  res.setLocation(loc);
  res.setAccess((AAccessSpecifierAccessSpecifier)((yystack.valueAt (10-(1)))));
  res.setName(name);
  res.setType((PType)((yystack.valueAt (10-(4)))));
  res.setParameterPatterns((List<? extends PPattern>)((yystack.valueAt (10-(6)))));
  res.setBody(body);
  res.setPrecondition((PExp)((yystack.valueAt (10-(9)))));
  res.setPostcondition((PExp)((yystack.valueAt (10-(10)))));
  res.setIsConstructor(false);
  yyval = res;
};
  break;
    

  case 252:
  if (yyn == 252)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3028 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (3-(1))));
  PType rng = (PType)((yystack.valueAt (3-(3))));
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  yyval = new AOperationType(util.extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};
  break;
    

  case 253:
  if (yyn == 253)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3040 of "src/main/bison/cml.y"  */
    {
  PType dom = new AVoidType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rng = (PType)((yystack.valueAt (3-(3))));
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  yyval = new AOperationType(util.extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};
  break;
    

  case 254:
  if (yyn == 254)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3052 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (3-(1))));
  PType rng = new AVoidType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(3))))), true);
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  yyval = new AOperationType(util.extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};
  break;
    

  case 255:
  if (yyn == 255)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3064 of "src/main/bison/cml.y"  */
    {
  PType dom = new AVoidType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rng = new AVoidType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(3))))), true);
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  yyval = new AOperationType(util.extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};
  break;
    

  case 256:
  if (yyn == 256)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3078 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 257:
  if (yyn == 257)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3082 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 258:
  if (yyn == 258)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3086 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), null, null);
};
  break;
    

  case 259:
  if (yyn == 259)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3092 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 260:
  if (yyn == 260)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3093 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 261:
  if (yyn == 261)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3097 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 262:
  if (yyn == 262)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3102 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)((yystack.valueAt (1-(1)))));
  yyval = infoList;
};
  break;
    

  case 263:
  if (yyn == 263)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3108 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = (Vector<AExternalClause>)((yystack.valueAt (2-(1))));
  infoList.add((AExternalClause)((yystack.valueAt (2-(2)))));
  yyval = infoList;
};
  break;
    

  case 264:
  if (yyn == 264)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3128 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  LexToken mode = (LexToken)((yystack.valueAt (2-(1))));
  List<? extends LexNameToken> ids = null;
  // FIXME: dottedIdentifier
  //ids.add(convertDottedIdentifierToLexNameToken((List<? extends LexNameToken>)$id));
  yyval = new AExternalClause(mode, ids, null);
};
  break;
    

  case 265:
  if (yyn == 265)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3137 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  LexToken mode = (LexToken)((yystack.valueAt (4-(1))));
  List<? extends LexNameToken> ids = null;
  // FIXME: dottedIdentifier
  //ids.add(convertDottedIdentifierToLexNameToken((List<? extends LexNameToken>)$id));
  yyval = new AExternalClause(mode, ids, (PType)((yystack.valueAt (4-(4)))));
};
  break;
    

  case 266:
  if (yyn == 266)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3146 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  AExternalClause info = (AExternalClause)((yystack.valueAt (3-(1))));
  List<? extends LexNameToken> ids = info.getIdentifiers();
  // FIXME: dottedIdentifier
  //ids.add(convertDottedIdentifierToLexNameToken((List<? extends LexNameToken>)$id));
  yyval = info;
};
  break;
    

  case 267:
  if (yyn == 267)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3155 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  AExternalClause info = (AExternalClause)((yystack.valueAt (5-(1))));
  List<? extends LexNameToken> ids = info.getIdentifiers();
  // FIXME: dottedIdentifier
  //ids.add(convertDottedIdentifierToLexNameToken((List<? extends LexNameToken>)$id));
  yyval = info;
};
  break;
    

  case 268:
  if (yyn == 268)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3167 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.READ);
};
  break;
    

  case 269:
  if (yyn == 269)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3171 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.WRITE);
};
  break;
    

  case 270:
  if (yyn == 270)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3200 of "src/main/bison/cml.y"  */
    {
    AStateParagraphDefinition state = new AStateParagraphDefinition();
    state.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
    yyval  = state;
};
  break;
    

  case 271:
  if (yyn == 271)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3206 of "src/main/bison/cml.y"  */
    {
    AStateParagraphDefinition state = (AStateParagraphDefinition)((yystack.valueAt (2-(2))));
    state.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                         util.extractLastLexLocation(state.getStateDefs())));
    yyval = state;
};
  break;
    

  case 272:
  if (yyn == 272)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3222 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  AStateParagraphDefinition stateDef = new AStateParagraphDefinition();
  stateDef.setStateDefs(defs);
  yyval = stateDef;
};
  break;
    

  case 273:
  if (yyn == 273)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3230 of "src/main/bison/cml.y"  */
    {
  AStateParagraphDefinition stateDef = (AStateParagraphDefinition)((yystack.valueAt (2-(1))));
  stateDef.getStateDefs().add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = stateDef;
};
  break;
    

  case 274:
  if (yyn == 274)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3239 of "src/main/bison/cml.y"  */
    {
  AAssignmentDefinition adef = (AAssignmentDefinition)((yystack.valueAt (2-(2))));
  adef.setAccess((AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1)))));
  yyval = adef;
};
  break;
    

  case 275:
  if (yyn == 275)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3245 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp) ((yystack.valueAt (2-(2))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
  yyval = new AClassInvariantDefinition(location,
                                     NameScope.GLOBAL,
                                     true,
                                     null/*access*/,
                                     null/*Pass*/,
                                     exp);
};
  break;
    

  case 276:
  if (yyn == 276)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3259 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)((yystack.valueAt (1-(1)))));
  yyval = exps;
};
  break;
    

  case 277:
  if (yyn == 277)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3265 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(1))));
  exps.add((PExp)((yystack.valueAt (3-(3)))));
  yyval = exps;
};
  break;
    

  case 278:
  if (yyn == 278)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3308 of "src/main/bison/cml.y"  */
    {
    PExp exp = null;
    if(((yystack.valueAt (1-(1)))) instanceof LexIntegerToken) {
        LexIntegerToken lit = (LexIntegerToken)((yystack.valueAt (1-(1))));
        exp = new AIntLiteralExp(lit.location, lit);
    } else {
        LexRealToken lit = (LexRealToken)((yystack.valueAt (1-(1))));
        exp = new ARealLiteralExp(lit.location, lit);
    }
    yyval = exp;
};
  break;
    

  case 279:
  if (yyn == 279)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3320 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanConstExp(lit.location, lit);
};
  break;
    

  case 280:
  if (yyn == 280)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3325 of "src/main/bison/cml.y"  */
    {
    yyval = new ANilExp(((LexKeywordToken)((yystack.valueAt (1-(1))))).location);
};
  break;
    

  case 281:
  if (yyn == 281)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3329 of "src/main/bison/cml.y"  */
    {
    LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
    yyval = new ACharLiteralExp(lit.location, lit);
};
  break;
    

  case 282:
  if (yyn == 282)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3334 of "src/main/bison/cml.y"  */
    {
    LexStringToken lit = (LexStringToken)((yystack.valueAt (1-(1))));
    ASeqSeqType charSeq = new ASeqSeqType(lit.location,
                                    true,
                                    null,
                                    new ACharBasicType(),
                                    lit.value.length() == 0);
    yyval = new AStringLiteralExp(charSeq,
                               lit.location,
                               lit);
};
  break;
    

  case 283:
  if (yyn == 283)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3346 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteLiteralExp(lit.location, lit);
};
  break;
    

  case 284:
  if (yyn == 284)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3352 of "src/main/bison/cml.y"  */
    {
  yyval = new ABracketedExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))), (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 285:
  if (yyn == 285)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3356 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASelfExp(name.location, name);
};
  break;
    

  case 286:
  if (yyn == 286)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3361 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AVariableExp(name.location, name,"");
};
  break;
    

  case 287:
  if (yyn == 287)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3366 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (2-(1)))), true);
  yyval = new AVariableExp(name.location, name,"");
};
  break;
    

  case 288:
  if (yyn == 288)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3371 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpDotIdentifier((PExp)((yystack.valueAt (3-(1)))), util.extractLexIdentifierToken(((yystack.valueAt (3-(3))))));
};
  break;
    

  case 289:
  if (yyn == 289)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3375 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpDotIdentifier((PExp)((yystack.valueAt (3-(1)))), util.extractLexIdentifierToken(((yystack.valueAt (3-(3))))));
};
  break;
    

  case 290:
  if (yyn == 290)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3379 of "src/main/bison/cml.y"  */
    {
  PExp tuple = (PExp)((yystack.valueAt (3-(1))));
  LexIntegerToken field = (LexIntegerToken)((yystack.valueAt (3-(3)))); 
  yyval = AstFactory.newAFieldNumberExp(tuple, field);
};
  break;
    

  case 291:
  if (yyn == 291)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3385 of "src/main/bison/cml.y"  */
    {
  PExp rootExp = (PExp)((yystack.valueAt (2-(1))));
  LexLocation location = util.extractLexLocation(rootExp.getLocation(),(CmlLexeme)((yystack.valueAt (2-(2)))));
  yyval = new AApplyExp(location, (PExp)((yystack.valueAt (2-(1)))), new LinkedList<PExp>());
};
  break;
    

  case 292:
  if (yyn == 292)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3391 of "src/main/bison/cml.y"  */
    {
  PExp rootExp = (PExp)((yystack.valueAt (4-(1))));
  LexLocation location = util.extractLexLocation(rootExp.getLocation(),(CmlLexeme)((yystack.valueAt (4-(4)))));
  List<PExp> exps = (List<PExp>)((yystack.valueAt (4-(3))));
  yyval = new AApplyExp(location, (PExp)((yystack.valueAt (4-(1)))), exps);
};
  break;
    

  case 293:
  if (yyn == 293)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3398 of "src/main/bison/cml.y"  */
    {
  PExp seq = (PExp)((yystack.valueAt (6-(1))));
  PExp from = (PExp)((yystack.valueAt (6-(3))));
  PExp to = (PExp)((yystack.valueAt (6-(5))));
  yyval = AstFactory.newASubseqExp(seq,from,to);
};
  break;
    

  case 294:
  if (yyn == 294)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3405 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 295:
  if (yyn == 295)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3410 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpBangIdentifier(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 296:
  if (yyn == 296)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3414 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 297:
  if (yyn == 297)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3418 of "src/main/bison/cml.y"  */
    {
  /* PExp exp = (PExp)  */
  /* LexLocation location = util.extractLexLocation($exp); */
  /* $$ = new AReadCommunicationParameter(location, PParameter parameter_, PExp expression_); */
  /* --- TODO --- */
};
  break;
    

  case 298:
  if (yyn == 298)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3425 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 299:
  if (yyn == 299)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3430 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), exp.getLocation());
  yyval = new ALetDefExp(loc, defs, exp);
};
  break;
    

  case 300:
  if (yyn == 300)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3437 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 301:
  if (yyn == 301)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3441 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 302:
  if (yyn == 302)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3445 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 303:
  if (yyn == 303)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3449 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 304:
  if (yyn == 304)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3454 of "src/main/bison/cml.y"  */
    {
  CmlLexeme forall = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(forall), exp.getLocation());
  yyval = new AForAllExp(loc, binds, exp);
};
  break;
    

  case 305:
  if (yyn == 305)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3462 of "src/main/bison/cml.y"  */
    {
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(exists), exp.getLocation());
  yyval = new AExistsExp(loc, binds, exp);
};
  break;
    

  case 306:
  if (yyn == 306)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3470 of "src/main/bison/cml.y"  */
    {
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(exists), exp.getLocation());
  yyval = new AExists1Exp(loc, bind, exp, null);
};
  break;
    

  case 307:
  if (yyn == 307)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3478 of "src/main/bison/cml.y"  */
    {
  CmlLexeme iota = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(iota), exp.getLocation());
  yyval = new AIotaExp(loc, bind, exp);
};
  break;
    

  case 308:
  if (yyn == 308)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3487 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), (CmlLexeme)((yystack.valueAt (2-(2)))));
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation(loc);
  yyval = res;
};
  break;
    

  case 309:
  if (yyn == 309)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3494 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<PExp> members = (List<PExp>)((yystack.valueAt (3-(2))));
  yyval = new ASetEnumSetExp(location, members);
};
  break;
    

  case 310:
  if (yyn == 310)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3501 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASetCompSetExp(loc, exp, binds, null);
};
  break;
    

  case 311:
  if (yyn == 311)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3508 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (7-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new ASetCompSetExp(loc, exp, binds, pred);
};
  break;
    

  case 312:
  if (yyn == 312)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3517 of "src/main/bison/cml.y"  */
    {
  PExp start = (PExp)((yystack.valueAt (5-(2))));
  PExp end = (PExp)((yystack.valueAt (5-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASetRangeSetExp(loc, start, end);
};
  break;
    

  case 313:
  if (yyn == 313)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3525 of "src/main/bison/cml.y"  */
    {
  /* ?FIXME: Sequences cannot handle '[[]]' since '[[' and ']]' will be
   * lexed as a DLSQUARE and DRSQUARE token because of the renaming
   * comprehension. For now we need spaces like '[ [] ]' to be able to
   * parse it correctly.
   *
   * (JWC) We definitely need to document this out for users; I don't
   * think we can necessarily fix it (though it should be noted that a
   * sequence enumeration that contains sequence enumerations is a
   * little unusual).
   */
  List<PExp> exps = new LinkedList<PExp>();
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASeqEnumSeqExp(loc, exps);
};
  break;
    

  case 314:
  if (yyn == 314)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3541 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new ASeqEnumSeqExp(loc, exps);
};
  break;
    

  case 315:
  if (yyn == 315)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3548 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  ASetBind binds = (ASetBind)((yystack.valueAt (5-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASeqCompSeqExp(loc, exp, binds, null);
};
  break;
    

  case 316:
  if (yyn == 316)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3555 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (7-(2))));
  ASetBind binds = (ASetBind)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new ASeqCompSeqExp(loc, exp, binds, pred);
};
  break;
    

  case 317:
  if (yyn == 317)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3564 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
};
  break;
    

  case 318:
  if (yyn == 318)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3569 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new AMapEnumMapExp(loc, maplets);
};
  break;
    

  case 319:
  if (yyn == 319)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3576 of "src/main/bison/cml.y"  */
    {
  AMapletExp maplet = (AMapletExp)((yystack.valueAt (5-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new AMapCompMapExp(loc, maplet, binds, null);
};
  break;
    

  case 320:
  if (yyn == 320)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3583 of "src/main/bison/cml.y"  */
    {
  AMapletExp maplet = (AMapletExp)((yystack.valueAt (7-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new AMapCompMapExp(loc, maplet, binds, pred);
};
  break;
    

  case 321:
  if (yyn == 321)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3592 of "src/main/bison/cml.y"  */
    {
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new ATupleExp(loc, exprs);
};
  break;
    

  case 322:
  if (yyn == 322)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3599 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (4-(1)))));
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  LexLocation loc = util.extractLexLocation(((CmlLexeme)((yystack.valueAt (4-(1))))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  PExp res = null;
  if ("token".equals(name.name) && exprs != null && exprs.size() == 1) {
    ATokenBasicType type = new ATokenBasicType(loc, true);
    yyval = new AMkBasicExp(type, loc, exprs.get(0));
  } else {
    yyval = new AMkTypeExp(loc, name, exprs);
  }
};
  break;
    

  case 323:
  if (yyn == 323)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3613 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> binds = (List<ATypeBind>)((yystack.valueAt (4-(2))));
  PExp body = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), body.getLocation());
  yyval = new ALambdaExp(loc, binds, body, null, null);
};
  break;
    

  case 324:
  if (yyn == 324)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3628 of "src/main/bison/cml.y"  */
    {
  yyval = new AIsExp(null, // tc type
                  util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                     (CmlLexeme)((yystack.valueAt (4-(4))))),
                  util.extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (4-(1))))),
                  null, //basicType
                  (PExp)((yystack.valueAt (4-(3)))),
                  null); //PDef
};
  break;
    

  case 325:
  if (yyn == 325)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3638 of "src/main/bison/cml.y"  */
    {
  yyval = new AIsExp(null,
                  util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),
                                     (CmlLexeme)((yystack.valueAt (5-(5))))),
                  null,
                  (PType)((yystack.valueAt (5-(2)))),
                  (PExp)((yystack.valueAt (5-(4)))),
                  null);
};
  break;
    

  case 326:
  if (yyn == 326)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3648 of "src/main/bison/cml.y"  */
    {
  yyval = new AIsExp(null,
                  util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
                                     (CmlLexeme)((yystack.valueAt (6-(6))))),
                  null,
                  (PType)((yystack.valueAt (6-(5)))),
                  (PExp)((yystack.valueAt (6-(3)))),
                  null);
};
  break;
    

  case 327:
  if (yyn == 327)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3662 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* This instantiation can't be correct.
   */
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  PExp function = null;
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new APreExp(loc, function, exprs);
};
  break;
    

  case 328:
  if (yyn == 328)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3679 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))), (CmlLexeme)((yystack.valueAt (6-(6)))));
  List<LexIdentifierToken> dotted = (List<LexIdentifierToken>)((yystack.valueAt (6-(3))));
  // FIXME
  LexNameToken name = null;
  //LexNameToken name = convertDottedIdentifierToLexNameToken(dotted);
  yyval = new AIsOfClassExp(loc,
                         name,
                         (PExp)((yystack.valueAt (6-(5)))));
};
  break;
    

  case 329:
  if (yyn == 329)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3692 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (3-(2))));
  // FIXME
  List<LexIdentifierToken> ids = null;
  //List<LexIdentifierToken> ids = convertExpressionListToLexNameTokenList(exprs);
  yyval = new AEnumChansetSetExp(loc, ids);
};
  break;
    

  case 330:
  if (yyn == 330)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3702 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* below is from the old path-based code
   */
  //LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
  //ANameChannelExp chanNameExp =
  //  (ANameChannelExp)((Path)$path).convertToChannelNameExpression();
  //List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
  //$$ = new ACompChansetSetExp(loc, chanNameExp , bindings, null);
};
  break;
    

  case 331:
  if (yyn == 331)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3713 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  //LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
  //ANameChannelExp chanNameExp = (ANameChannelExp)((Path)$path).convertToChannelNameExpression();
  //List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
  //PExp pred = (PExp)$exp;
  //$$ = new ACompChansetSetExp(loc, chanNameExp, bindings, pred);
};
  break;
    

  case 332:
  if (yyn == 332)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3728 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.FALSE, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 333:
  if (yyn == 333)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3732 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.TRUE, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 334:
  if (yyn == 334)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3739 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lex = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = util.extractLexLocation(lex);
  String res = lex.getValue();
  res = res.replace("'", ""); // FIXME: what is this for? is it correct?
  yyval = new LexCharacterToken(util.convertEscapeToChar(res), loc);
};
  break;
    

  case 335:
  if (yyn == 335)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3750 of "src/main/bison/cml.y"  */
    {
  yyval = new LexKeywordToken(VDMToken.NIL, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 336:
  if (yyn == 336)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3757 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = util.extractLexLocation(lexeme);
  yyval = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
};
  break;
    

  case 337:
  if (yyn == 337)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3763 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = util.extractLexLocation(lexeme);
  BigInteger b = new BigInteger(lexeme.getValue().substring(2), 16);
  yyval = new LexIntegerToken(b.longValue(), loc);
};
  break;
    

  case 338:
  if (yyn == 338)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3770 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = util.extractLexLocation(lexeme);
  try {
    DecimalFormat dec = new DecimalFormat();
    yyval = new LexRealToken(dec.parse(lexeme.getValue()).doubleValue(), loc);
  } catch (Exception e) {
    yyval = new LexRealToken(0, loc);
  }
};
  break;
    

  case 339:
  if (yyn == 339)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3784 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexStringToken(lit.substring(1, lit.length()-2),
                          util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 340:
  if (yyn == 340)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3793 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexQuoteToken(lit.substring(1, lit.length()-2),
                         util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 341:
  if (yyn == 341)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3804 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 342:
  if (yyn == 342)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3810 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add((PDefinition)((yystack.valueAt (3-(3)))));
  yyval = defs;
};
  break;
    

  case 343:
  if (yyn == 343)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3818 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 344:
  if (yyn == 344)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3819 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 345:
  if (yyn == 345)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3824 of "src/main/bison/cml.y"  */
    {
  PExp test = (PExp)((yystack.valueAt (5-(2))));
  PExp then = (PExp)((yystack.valueAt (5-(4))));
  List<PExp> elses = (List<PExp>)((yystack.valueAt (5-(5))));
  LexLocation loc = null;
  if (elses.size() > 0) {
    loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1))))),
                             util.extractLastLexLocation(elses));
  } else {
    loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1))))),
                             then.getLocation());
  }
  AIfExp ifexp = new AIfExp(loc,
                            test,
                            then,
                            null, // elseIfs
                            null); // else
  List<AElseIfExp> elseifs = new LinkedList<AElseIfExp>();
  ifexp.setElseList(elseifs);
  for(PExp exp : elses) {
    if (exp instanceof AElseIfExp) {
      elseifs.add((AElseIfExp)exp);
    } else {
      ifexp.setElse(exp);
    }
  }
  yyval = ifexp;
};
  break;
    

  case 346:
  if (yyn == 346)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3856 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  List<PExp> res = new LinkedList<PExp>();
  res.add(exp);
  yyval = res;
};
  break;
    

  case 347:
  if (yyn == 347)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3863 of "src/main/bison/cml.y"  */
    {
  PExp test = (PExp)((yystack.valueAt (5-(2))));
  PExp then = (PExp)((yystack.valueAt (5-(4))));
  List<PExp> tail = (List<PExp>)((yystack.valueAt (5-(5))));
  tail.add(new AElseIfExp(util.extractLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1))))),
                                             then.getLocation()),
                          test,
                          then));
  yyval = tail;
};
  break;
    

  case 348:
  if (yyn == 348)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3877 of "src/main/bison/cml.y"  */
    {
  ACasesExp alts = (ACasesExp)((yystack.valueAt (5-(4))));
  alts.setExpression((PExp)((yystack.valueAt (5-(2)))));
  alts.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5))))));
  yyval = alts;
};
  break;
    

  case 349:
  if (yyn == 349)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3884 of "src/main/bison/cml.y"  */
    {
  ACasesExp alts = (ACasesExp)((yystack.valueAt (9-(4))));
  alts.setExpression((PExp)((yystack.valueAt (9-(2)))));
  alts.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))), (CmlLexeme)((yystack.valueAt (9-(9))))));
  alts.setOthers((PExp)((yystack.valueAt (9-(8)))));
  yyval = alts;
};
  break;
    

  case 350:
  if (yyn == 350)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3895 of "src/main/bison/cml.y"  */
    {
  ACasesExp casesExp = new ACasesExp();
  casesExp.getCases().add((ACaseAlternative)((yystack.valueAt (1-(1)))));
  yyval = casesExp;
};
  break;
    

  case 351:
  if (yyn == 351)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3901 of "src/main/bison/cml.y"  */
    {
  ACasesExp casesExp = (ACasesExp)((yystack.valueAt (3-(1))));
  casesExp.getCases().add((ACaseAlternative)((yystack.valueAt (3-(3)))));
  yyval = casesExp;
};
  break;
    

  case 352:
  if (yyn == 352)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3912 of "src/main/bison/cml.y"  */
    {
   List<ACaseAlternative> res = new LinkedList<ACaseAlternative>();
   List<PPattern> patList = (List<PPattern>)((yystack.valueAt (3-(1))));
   PExp exp = (PExp)((yystack.valueAt (3-(3))));
   LexLocation leftMost = util.extractLexLeftMostFromPatterns(patList);
   LexLocation loc = util.combineLexLocation(leftMost, exp.getLocation());
   for(PPattern p : patList)
   {
     ACaseAlternative r = new ACaseAlternative();
     r.setPattern(p);
     r.setLocation(loc);
     r.setCexp(exp);
     res.add(r);
    }
   yyval = res;
};
  break;
    

  case 353:
  if (yyn == 353)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3932 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryPlusUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 354:
  if (yyn == 354)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3938 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryMinusUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 355:
  if (yyn == 355)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3944 of "src/main/bison/cml.y"  */
    {
  yyval = new AAbsoluteUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 356:
  if (yyn == 356)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3950 of "src/main/bison/cml.y"  */
    {
  yyval = new AFloorUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                             ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                          (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 357:
  if (yyn == 357)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3956 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                           ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                        (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 358:
  if (yyn == 358)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3962 of "src/main/bison/cml.y"  */
    {
  yyval = new ACardinalityUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                   ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                                (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 359:
  if (yyn == 359)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3968 of "src/main/bison/cml.y"  */
    {
  yyval = new APowerSetUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 360:
  if (yyn == 360)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3974 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistUnionUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 361:
  if (yyn == 361)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3980 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistIntersectUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                     ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                                  (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 362:
  if (yyn == 362)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3986 of "src/main/bison/cml.y"  */
    {
  yyval = new AHeadUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                         (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 363:
  if (yyn == 363)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3992 of "src/main/bison/cml.y"  */
    {
  yyval = new ATailUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                         (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 364:
  if (yyn == 364)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3998 of "src/main/bison/cml.y"  */
    {
  yyval = new ALenUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                           ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                        (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 365:
  if (yyn == 365)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4004 of "src/main/bison/cml.y"  */
    {
  yyval = new AElementsUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 366:
  if (yyn == 366)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4010 of "src/main/bison/cml.y"  */
    {
  yyval = new AIndicesUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                               ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                            (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 367:
  if (yyn == 367)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4016 of "src/main/bison/cml.y"  */
    {
  yyval = new AReverseUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                               ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                            (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 368:
  if (yyn == 368)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4022 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistConcatUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 369:
  if (yyn == 369)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4028 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapDomainUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 370:
  if (yyn == 370)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4034 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapRangeUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 371:
  if (yyn == 371)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4040 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistMergeUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 372:
  if (yyn == 372)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4046 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapInverseUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 373:
  if (yyn == 373)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4055 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 374:
  if (yyn == 374)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4063 of "src/main/bison/cml.y"  */
    {
  yyval = new ATimesNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 375:
  if (yyn == 375)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4071 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubstractNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                         ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                      (PExp)((yystack.valueAt (3-(1)))),
                                      util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                      (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 376:
  if (yyn == 376)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4079 of "src/main/bison/cml.y"  */
    {
  yyval = new ADivideNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                      ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                   (PExp)((yystack.valueAt (3-(1)))),
                                   util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                   (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 377:
  if (yyn == 377)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4087 of "src/main/bison/cml.y"  */
    {
  yyval = new ADivNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 378:
  if (yyn == 378)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4095 of "src/main/bison/cml.y"  */
    {
  yyval = new ARemNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 379:
  if (yyn == 379)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4103 of "src/main/bison/cml.y"  */
    {
  yyval = new AModNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 380:
  if (yyn == 380)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4111 of "src/main/bison/cml.y"  */
    {
  yyval = new ALessNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 381:
  if (yyn == 381)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4119 of "src/main/bison/cml.y"  */
    {
  yyval = new ALessEqualNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                         ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                      (PExp)((yystack.valueAt (3-(1)))),
                                      util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                      (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 382:
  if (yyn == 382)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4127 of "src/main/bison/cml.y"  */
    {
  yyval = new AGreaterNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                       ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                    (PExp)((yystack.valueAt (3-(1)))),
                                    util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                    (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 383:
  if (yyn == 383)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4135 of "src/main/bison/cml.y"  */
    {
  yyval = new AGreaterEqualNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                            ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                         (PExp)((yystack.valueAt (3-(1)))),
                                         util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                         (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 384:
  if (yyn == 384)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4143 of "src/main/bison/cml.y"  */
    {
  yyval = new AEqualsBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                               ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                            (PExp)((yystack.valueAt (3-(1)))),
                            util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                            (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 385:
  if (yyn == 385)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4151 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotEqualBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 386:
  if (yyn == 386)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4159 of "src/main/bison/cml.y"  */
    {
  yyval = new AOrBooleanBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                  ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                               (PExp)((yystack.valueAt (3-(1)))),
                               util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                               (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 387:
  if (yyn == 387)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4167 of "src/main/bison/cml.y"  */
    {
  yyval = new AAndBooleanBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 388:
  if (yyn == 388)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4175 of "src/main/bison/cml.y"  */
    {
  yyval = new AImpliesBooleanBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                       ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                    (PExp)((yystack.valueAt (3-(1)))),
                                    util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                    (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 389:
  if (yyn == 389)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4183 of "src/main/bison/cml.y"  */
    {
  yyval = new AEquivalentBooleanBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                          ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                       (PExp)((yystack.valueAt (3-(1)))),
                                       util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                       (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 390:
  if (yyn == 390)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4191 of "src/main/bison/cml.y"  */
    {
  yyval = new AInSetBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                              ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                           (PExp)((yystack.valueAt (3-(1)))),
                           util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                           (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 391:
  if (yyn == 391)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4199 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotInSetBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 392:
  if (yyn == 392)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4207 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubsetBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                               ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                            (PExp)((yystack.valueAt (3-(1)))),
                            util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                            (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 393:
  if (yyn == 393)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4215 of "src/main/bison/cml.y"  */
    {
  yyval = new AProperSubsetBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 394:
  if (yyn == 394)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4223 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetUnionBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 395:
  if (yyn == 395)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4231 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetDifferenceBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                      ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                   (PExp)((yystack.valueAt (3-(1)))),
                                   util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                   (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 396:
  if (yyn == 396)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4239 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetIntersectBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 397:
  if (yyn == 397)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4247 of "src/main/bison/cml.y"  */
    {
  yyval = new ASeqConcatBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                  ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                               (PExp)((yystack.valueAt (3-(1)))),
                               util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                               (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 398:
  if (yyn == 398)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4255 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 399:
  if (yyn == 399)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4263 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapUnionBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 400:
  if (yyn == 400)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4271 of "src/main/bison/cml.y"  */
    {
  yyval = new ADomainResToBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 401:
  if (yyn == 401)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4279 of "src/main/bison/cml.y"  */
    {
  yyval = new ADomainResByBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 402:
  if (yyn == 402)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4287 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 403:
  if (yyn == 403)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4295 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 404:
  if (yyn == 404)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4303 of "src/main/bison/cml.y"  */
    {
  yyval = new ACompBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                             ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                          (PExp)((yystack.valueAt (3-(1)))),
                          util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                          (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 405:
  if (yyn == 405)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4311 of "src/main/bison/cml.y"  */
    {
  yyval = new AStarStarBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 406:
  if (yyn == 406)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4322 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 407:
  if (yyn == 407)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4328 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(1))));
  maplets.add((AMapletExp)((yystack.valueAt (3-(3)))));
  yyval = maplets;
};
  break;
    

  case 408:
  if (yyn == 408)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4336 of "src/main/bison/cml.y"  */
    {
  PExp dom = (PExp)((yystack.valueAt (3-(1))));
  PExp rng = (PExp)((yystack.valueAt (3-(3))));
  yyval = new AMapletExp(util.extractLexLocation(dom.getLocation(),
                                         rng.getLocation()),
                      dom,
                      rng);
};
  break;
    

  case 409:
  if (yyn == 409)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4348 of "src/main/bison/cml.y"  */
    {
  PExp guard = (PExp)((yystack.valueAt (3-(1))));
  PAction action = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = util.extractLexLocation(guard.getLocation(),
                                            action.getLocation());
  List<ANonDeterministicAltStatementAction> alts =
    new LinkedList<ANonDeterministicAltStatementAction>();
  alts.add(new ANonDeterministicAltStatementAction(location,
                                                   guard,
                                                   action));
  yyval = alts;
};
  break;
    

  case 410:
  if (yyn == 410)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4361 of "src/main/bison/cml.y"  */
    {
  PExp guard = (PExp)((yystack.valueAt (5-(3))));
  PAction action = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = util.extractLexLocation(guard.getLocation(),
                                            action.getLocation());
  List<ANonDeterministicAltStatementAction> alts =
    (List<ANonDeterministicAltStatementAction>)((yystack.valueAt (5-(1))));
  alts.add(new ANonDeterministicAltStatementAction(location,
                                                   guard,
                                                   action));
  yyval = alts;
};
  break;
    

  case 411:
  if (yyn == 411)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4377 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (1-(1)))));
  yyval = assignmentDefs;
};
  break;
    

  case 412:
  if (yyn == 412)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4383 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)((yystack.valueAt (3-(1))));
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (3-(3)))));
  yyval = assignmentDefs;
};
  break;
    

  case 413:
  if (yyn == 413)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4392 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation location = util.extractLexLocation(name.location, type.getLocation());
  AAccessSpecifierAccessSpecifier access = null;
  yyval = new AAssignmentDefinition(location,
                                 name,
                                 NameScope.GLOBAL,
                                 false,
                                 null,//VDM classDef
                                 access,
                                 type,
                                 null,//Pass
                                 null,
                                 null);
};
  break;
    

  case 414:
  if (yyn == 414)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4409 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(1)))));
  PType type = (PType)((yystack.valueAt (5-(3))));
  PExp exp = (PExp)((yystack.valueAt (5-(5))));
  LexLocation location = util.combineLexLocation(name.location, exp.getLocation());
  yyval = new AAssignmentDefinition(location, name,
                                 NameScope.GLOBAL,
                                 false,
                                 null,//VDM classDef
                                 null,//access
                                 type,
                                 null,//Pass
                                 exp,
                                 null);
};
  break;
    

  case 415:
  if (yyn == 415)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4425 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(1)))));
    PType type = (PType)((yystack.valueAt (5-(3))));
    PExp exp = (PExp)((yystack.valueAt (5-(5))));
    LexLocation location = util.combineLexLocation(name.location, exp.getLocation());
    yyval = new AAssignmentDefinition(location, name,
                                   NameScope.GLOBAL,
                                   false,
                                   null,//VDM classDef
                                   null,//access
                                   type,
                                   null,//Pass
                                   exp,
                                   null);
};
  break;
    

  case 416:
  if (yyn == 416)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4448 of "src/main/bison/cml.y"  */
    {
  List<ASingleGeneralAssignmentStatementAction> assigns =
    new LinkedList<ASingleGeneralAssignmentStatementAction>();
  assigns.add((ASingleGeneralAssignmentStatementAction)((yystack.valueAt (1-(1)))));
  yyval = assigns;
};
  break;
    

  case 417:
  if (yyn == 417)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4455 of "src/main/bison/cml.y"  */
    {
  List<ASingleGeneralAssignmentStatementAction> assigns =
    (List<ASingleGeneralAssignmentStatementAction>)((yystack.valueAt (3-(1))));
  assigns.add((ASingleGeneralAssignmentStatementAction)((yystack.valueAt (3-(3)))));
  yyval = assigns;
};
  break;
    

  case 418:
  if (yyn == 418)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4473 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  List<LexIdentifierToken> id = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  PStateDesignator stateDesignator = null;
  // FIXME: old path code
  // stateDesignator = path.convertToStateDesignator();
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.extractLexLocation(stateDesignator.getLocation(),
                                            exp.getLocation());
  yyval = new ASingleGeneralAssignmentStatementAction(location, stateDesignator, exp);
};
  break;
    

  case 419:
  if (yyn == 419)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4488 of "src/main/bison/cml.y"  */
    {
  yyval = new AIfStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
                                                 ((PAction)((yystack.valueAt (7-(7))))).getLocation()),
                              (PExp)((yystack.valueAt (7-(2)))),
                              (PAction)((yystack.valueAt (7-(4)))),
                              (List<? extends AElseIfStatementAction>)((yystack.valueAt (7-(5)))),
                              (PAction)((yystack.valueAt (7-(7)))));
};
  break;
    

  case 420:
  if (yyn == 420)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4497 of "src/main/bison/cml.y"  */
    {
  yyval = new AIfStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
                                                 ((PAction)((yystack.valueAt (6-(6))))).getLocation()),
                              (PExp)((yystack.valueAt (6-(2)))),
                              (PAction)((yystack.valueAt (6-(4)))),
                              null,
                              (PAction)((yystack.valueAt (6-(6)))));
};
  break;
    

  case 421:
  if (yyn == 421)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4509 of "src/main/bison/cml.y"  */
    {
  List<AElseIfStatementAction> elseStms = new Vector<AElseIfStatementAction>();
  AElseIfStatementAction elseif =
    new AElseIfStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                                  ((PAction)((yystack.valueAt (4-(4))))).getLocation()),
                               (PExp)((yystack.valueAt (4-(2)))),
                               (PAction)((yystack.valueAt (4-(4)))));
  elseStms.add(elseif);
  yyval = elseStms;
};
  break;
    

  case 422:
  if (yyn == 422)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4520 of "src/main/bison/cml.y"  */
    {
  List<AElseIfStatementAction> elseStms = (List<AElseIfStatementAction>)((yystack.valueAt (5-(1))));
  AElseIfStatementAction elseif =
    new AElseIfStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(2)))),
                                                  ((PAction)((yystack.valueAt (5-(5))))).getLocation()),
                               (PExp)((yystack.valueAt (5-(3)))),
                               (PAction)((yystack.valueAt (5-(5)))));
  elseStms.add(elseif);
  yyval = elseStms;
};
  break;
    

  case 423:
  if (yyn == 423)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4534 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (5-(4))));
  cases.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5))))));
  cases.setExp((PExp)((yystack.valueAt (5-(2)))));
  yyval = cases;
};
  break;
    

  case 424:
  if (yyn == 424)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4541 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (9-(4))));
  cases.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))), (CmlLexeme)((yystack.valueAt (9-(9))))));
  cases.setExp((PExp)((yystack.valueAt (9-(2)))));
  cases.setOthers((PAction)((yystack.valueAt (9-(8)))));
  yyval = cases;
};
  break;
    

  case 425:
  if (yyn == 425)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4552 of "src/main/bison/cml.y"  */
    {
  List<ACaseAlternativeAction> casesList = new LinkedList<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)((yystack.valueAt (1-(1)))));
  yyval = new ACasesStatementAction(null, null, casesList, null);
};
  break;
    

  case 426:
  if (yyn == 426)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4558 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (3-(1))));
  cases.getCases().add((ACaseAlternativeAction)((yystack.valueAt (3-(3)))));
  yyval = cases;
};
  break;
    

  case 427:
  if (yyn == 427)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4569 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PAction action = (PAction)((yystack.valueAt (3-(3))));
  yyval = new ACaseAlternativeAction(util.combineLexLocation(util.extractFirstLexLocation(patterns),
                                                     action.getLocation()),
                                  patterns,
                                  action);
};
  break;
    

  case 428:
  if (yyn == 428)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4581 of "src/main/bison/cml.y"  */
    {
  List<? extends AExternalClause> exts = (List<? extends AExternalClause>)((yystack.valueAt (3-(1))));
  PExp pre = (PExp)((yystack.valueAt (3-(2))));
  PExp post = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = null;
  if (exts != null) {
    loc = util.extractLexLocation(util.extractFirstLexLocation(exts), post.getLocation());
  } else if (pre != null) {
    loc = util.extractLexLocation(pre.getLocation(), post.getLocation());
  } else {
    loc = post.getLocation();
  }
  yyval = new ASpecificationStatementAction(loc, exts, pre, post);
};
  break;
    

  case 429:
  if (yyn == 429)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4598 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 430:
  if (yyn == 430)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4599 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 431:
  if (yyn == 431)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4604 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 432:
  if (yyn == 432)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4608 of "src/main/bison/cml.y"  */
    {
  List<PPattern> plist = (List<PPattern>)((yystack.valueAt (6-(3))));
  plist.add((PPattern)((yystack.valueAt (6-(5)))));
  yyval = new ATuplePattern(util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
                                            (CmlLexeme)((yystack.valueAt (6-(6))))),
                         new LinkedList<PDefinition>(),
                         false,
                         plist);
};
  break;
    

  case 433:
  if (yyn == 433)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4625 of "src/main/bison/cml.y"  */
    {
  List<? extends PPattern> plist = null;
  LexNameToken name = util.extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (2-(1)))));
  yyval = new ARecordPattern(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                             (CmlLexeme)((yystack.valueAt (2-(2))))),
                          null,
                          false,
                          name,
                          plist);
};
  break;
    

  case 434:
  if (yyn == 434)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4636 of "src/main/bison/cml.y"  */
    {
  List<? extends PPattern> plist = (List<? extends PPattern>)((yystack.valueAt (4-(3))));
  LexNameToken name = util.extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (4-(1)))));
  yyval = new ARecordPattern(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                             (CmlLexeme)((yystack.valueAt (4-(4))))),
                          null,
                          false,
                          name,
                          plist);
};
  break;
    

  case 435:
  if (yyn == 435)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4650 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)((yystack.valueAt (1-(1)))));
  yyval = patterns;
};
  break;
    

  case 436:
  if (yyn == 436)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4656 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  patterns.add((PPattern)((yystack.valueAt (3-(3)))));
  yyval = patterns;
};
  break;
    

  case 437:
  if (yyn == 437)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4665 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = util.extractLexLocation(lexeme);
  LexNameToken lnt = new LexNameToken("",
                                      lexeme.getValue(),
                                      loc,
                                      false,
                                      true);
  AIdentifierPattern res = new AIdentifierPattern();
  res.setName(lnt);
  res.setLocation(loc);
  yyval = res;
};
  break;
    

  case 438:
  if (yyn == 438)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4679 of "src/main/bison/cml.y"  */
    {
  yyval = new AIgnorePattern(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),
                          new LinkedList<PDefinition>(),
                          true);
};
  break;
    

  case 439:
  if (yyn == 439)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4689 of "src/main/bison/cml.y"  */
    {
  if (((yystack.valueAt (1-(1)))) instanceof LexIntegerToken) {
    LexIntegerToken lit = (LexIntegerToken)((yystack.valueAt (1-(1))));
    yyval = new AIntegerPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
  } else {
    LexRealToken lit = (LexRealToken)((yystack.valueAt (1-(1))));
    yyval = new ARealPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
  }
};
  break;
    

  case 440:
  if (yyn == 440)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4699 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 441:
  if (yyn == 441)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4704 of "src/main/bison/cml.y"  */
    {
  LexKeywordToken lit = (LexKeywordToken)((yystack.valueAt (1-(1))));
  yyval = new ANilPattern(lit.location, new LinkedList<PDefinition>(), true);
};
  break;
    

  case 442:
  if (yyn == 442)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4709 of "src/main/bison/cml.y"  */
    {
  LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
  yyval = new ACharacterPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 443:
  if (yyn == 443)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4714 of "src/main/bison/cml.y"  */
    {
  LexStringToken lit = (LexStringToken)((yystack.valueAt (1-(1))));
  yyval = new AStringPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 444:
  if (yyn == 444)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4719 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuotePattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 445:
  if (yyn == 445)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4724 of "src/main/bison/cml.y"  */
    {
  yyval = new AExpressionPattern(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
                                                 (CmlLexeme)((yystack.valueAt (3-(3))))),
                              new LinkedList<PDefinition>(),
                              false,
                              (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 446:
  if (yyn == 446)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4734 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 447:
  if (yyn == 447)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4735 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 448:
  if (yyn == 448)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4740 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.extractLexLocation(pattern.getLocation(),
                                            exp.getLocation());
  yyval = new ASetBind(location, pattern, exp);
};
  break;
    

  case 449:
  if (yyn == 449)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4751 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation location = util.extractLexLocation(pattern.getLocation(),
                                            type.getLocation());
  yyval = new ATypeBind(location, pattern, type);
};
  break;
    

  case 450:
  if (yyn == 450)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4762 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)((yystack.valueAt (1-(1)))));
  yyval = binds;
};
  break;
    

  case 451:
  if (yyn == 451)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4768 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (3-(1))));
  binds.add((PMultipleBind)((yystack.valueAt (3-(3)))));
  yyval = binds;
};
  break;
    

  case 452:
  if (yyn == 452)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4776 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 453:
  if (yyn == 453)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4777 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 454:
  if (yyn == 454)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4782 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = util.extractLexLocation(util.extractFirstLexLocation(patterns),
                                       exp.getLocation());
  yyval = new ASetMultipleBind(loc, patterns, exp);
};
  break;
    

  case 455:
  if (yyn == 455)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4793 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.extractLexLocation(util.extractFirstLexLocation(patterns),
                                       type.getLocation());
  yyval = new ATypeMultipleBind(loc, patterns, type);
};
  break;
    

  case 456:
  if (yyn == 456)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4804 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> list = new LinkedList<ATypeBind>();
  ATypeBind bind = (ATypeBind)((yystack.valueAt (1-(1))));
  list.add(bind);
  yyval = list;
};
  break;
    

  case 457:
  if (yyn == 457)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4811 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> list = (List<ATypeBind>)((yystack.valueAt (3-(1))));
  ATypeBind bind = (ATypeBind)((yystack.valueAt (3-(3))));
  list.add(bind);
  yyval = list;
};
  break;
    



/* Line 374 of cmlskeleton.java.m4  */
/* Line 7730 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -655;
  public static final short yypact_[] =
  {
        46,   503,  -135,  -122,   503,   983,   -69,    90,   196,    46,
    -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  -655,   167,  -655,   101,   202,  -135,  -655,  -655,
     283,  -122,  -655,   131,   503,  -655,  1450,   324,  -655,   334,
     378,  -655,  -655,   503,   335,   308,   505,  -655,  2647,  -655,
     134,  -655,  -655,  2647,   475,   623,  -655,  -655,  -655,    14,
    -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  -655,    25,  -655,   642,   515,   377,  -655,   505,
    1807,   202,  -655,   505,   420,   505,   341,   505,   505,   505,
     505,   505,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,   828,    32,  -655,  -655,  -655,  -655,  -655,  -655,  2647,
    2647,  2239,  1889,  2658,  2647,  2647,  2647,  2647,  2647,  2647,
    2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,
    2647,  2647,  2647,  2647,  2647,  2647,   497,   522,  1889,   110,
     533,   580,  1889,  1889,  1889,  2647,   595,  -655,   434,  -655,
   11035,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  1889,  -655,   505,   387,  5168,  1889,  1889,  -655,  2647,
     505,  2647,   505,  -655,   400,   782,   433,   433,   433,   308,
     433,   433,  2647,  1070,   130,  3132,    -5,  -655,   907,   263,
     828,    98,  -655,   618,   505,   505,   868,   639,   992,  1012,
     655,   655,   655,   505,   505,   505,   505,   455,   532,  5290,
      10,  5413,  -655,    59,  4433,    60,   682,  -655,  -655,   307,
    -655,  -655,   718,  -655,  -655,    31,  -655,  -655,   -18,  -655,
    5535,  5657,   569,   569,   569,   569, 11486,   569,   569,   569,
     569,   569,   569,   569,   569,   569,   569,   569,   569,   569,
     569,  3622,  2647,  2647,   593,  -655,    23,  2647,   730,  2647,
     572,  -655,   446,    41,  -655,  -655,  -655,    86,   743,   -99,
    5780,  2647,  -655,  2647,  2647,  -655,  2647,  2647,  2647,   436,
     670,   583,  2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,
    2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,
    2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,  2647,
    2647,  1889,   925,     9,   459,   828,   585,   619,    81,  -655,
     629,    20, 11035,   727, 11035,   810,   604,   503,   642,   222,
     608,   782,  -655,  -655,  -655,  -655,  -655,   460,    96,  -655,
    -655,    99,   113,    -3,   373,   395,  5902,   602,   611,  2469,
    1086,  -655,  -655,  2647,  -655,   435,   435,   435,  2647,   435,
     435,  2647,  2647,  2647,   435,   435,  2647,  2647,  2647,  -655,
     435,  -655,  1889,  -655,   505,   505,  -655,  -655,  -655,   828,
     828,  -655,  1807,   505,   505,   828,   655,   655,   828,  -655,
    -655,  -655,  -655,  2647,  1889,  -655,  1889,  2647,  2647,  -655,
    2647,  1889,  2647,   505,  2647,   505,  2647,  2658,  2647,  1889,
     126, 11035,   139,  2647,  1889,  6024,  2647,   192,    16,  2647,
    1889,   505,  2647,  1889,  2647,  2647,  -655,  1889,  6146, 11536,
     213,  6268,  2253, 11609, 11609,  -655,  -655,  -655,  -655,  2253,
    2253,  2253,  2253, 11536, 11536, 11536, 11536, 11536, 11359, 11409,
   11279, 11157, 11536, 11536, 11536, 11536,  3277,  3277,  3622,  3521,
    3399,  3399,  3622,  3622,   569,   569,   471,   313,  -655,  -655,
    -655,  -655,  -655,  1889,   505,   648,   505,  2647,   614,   738,
    -655,  1889,  -655,  2647,  2647,   782,   616,   503,  -655,  -655,
    2647,   622,   222,  -655,  -655,  -655,  2647,   433,   435,   433,
     435,   435,   435,   445,   435,   433,   785,   625,   792,   632,
     433,   433,   433,  2788,  2375,  -655,  -655,  -655,  -655,  2647,
     433,   433,  2658,  2647,  2647,   797,   700,  2647,  2647,  2647,
    2647,  1172,  2647,  1220,   624,  -655,  -655,  -655,  2469,  -655,
     215,   706,  1400,   169,  1288,  4066,   399,   439,  6390,   599,
    6512,  4188,   342,   322, 11035, 11035, 11035,  3132,   763,   828,
     828,  1757,   655,   655, 11035,   707,   563,    26,  6636, 11035,
    6758,  -655,    35, 11536,   828, 11157, 11035,  -655,  4555,   148,
    -655,    15,  -655,  -655, 11157,  -655,   505,  6880,  -655,  2647,
   11035,  -655,   828, 11157,  -655, 11157, 11157,    22,  -655,  -655,
    2647,   592,   828,  1889,  -655,   108,   828, 11035,   675,  2647,
    -655,   808, 11035, 11035,   659,   325,  -655, 11035,   685,  -655,
    -655, 11035,  -655,  -655,  -655,  -655,  -655,  -655,  1586,  2647,
    -655,  -655,   411,  2647,   823,  2839,   836,   478,   510,   518,
     622,   308,   308,   308,   202,  2962,   550,  -655,    -1,   250,
     791,  -655,  7002,   829, 11035,   598,   613,    49,  4677,   -14,
    7124,   572,  2647,  -655,  7246,  7368,   122,  7490,    -6,  1889,
     839,     1,   799,  3333,  2469,  2469,  2469,  2647,  2469,  2469,
    2647,  2647,  2647,  2469,  2469,  2647,  2647,  2647,  -655,  -655,
    2647,  -655,  2511,  2483,  -655,  2647,   435,   435,  -655,  2647,
    2647,   435,  2647,  -655,  2647,  -655,  2647,  -655,  -655,  2647,
    -655,  2647,  2647,  -655,  1624,  -655,  2647,   893,  -655,  7612,
    2647,  -655,  7734,   505,   259,  1889,  2103,   505, 11035,  -655,
    -655,  1852,   387,   505,   843,  7856,   435, 11035,  2647,  -655,
    3112,    11,  2839,  2469,  2469,   842,   129,  -655,  -655,  -655,
    -655,  -655,  2469,   565,  2469,  -655,  -655,   250,   710,   572,
     738,    17,  -655,   851,  2469,  2469,  2469,  2469,  2647,  -655,
    1889,   396,   500,  -655,  7978,   433,   433,   859,  -655,   759,
    2647,  2647,  2647,  2469,   981,  1760,   656,  3944,   831,   714,
    4800,  8101,  4310,   182,   284, 11035, 11035, 11035,   291,  2647,
   11035,  -655,  8223,   322,   399,  8345,  8468,   342, 11035,  8590,
    8714,  8838,  8960, 11157,   853,  -655, 11035,  -655,  -655,  9082,
    -655,   828,  -655,   348,  -655,  -655,   791, 11035,   828,    80,
     528,   698,   832,   751,   742,  2647,   694,  -655, 11035,  2469,
    -655,  -655,  -655,  2647,  2469,   622,  3039,  -655,  3048,   710,
     572,   277,  -655,  2469,  2469,  2647,  -655,  3112,  3112,  2942,
    9204,   257,  -655,    68,  2647,  -655,   572,  -655,   644,   653,
    2469,  2647,  9326,  3455,  3577,  3112,  2647,  2647,  2469,  2647,
    2469,  2647,  2469,  -655, 11035,   435,  2647,  1889,  -655,  -655,
    -655,  2647,  2647,  -655,  -655,   738,  1919,  1949,   648,   791,
    2647,  2647,  9448,   631,  -655,  3112,  9570,  3092,  -655,   867,
     881,   368,   505,  -655,   714,  4923,  2647,  2469,   407,  2469,
    1966,  -655,  2469,  -655,   880,  2469,  3112,   352,  3699,  2647,
    2469,  2469,  9692,  9814,   284,  5046,   831,  9936,   182,   399,
   11035,    78,  4555,  2917,   850,  -655,   420,   828,   420,   828,
     114,   738, 11035, 11035,  -655,  2469,  -655,  2647,  2647,   505,
     828,  2469,  2647, 10058,  3112,  2647,  2469,  3112,   876,  -655,
    3112,  2647,  -655,  -655,  2469,  2469,  3211,  3112,  3112,  2647,
    2469,  2647,  2469,  2469,  2647,  -655,  -655,  -655,   572,  -655,
    2191,  -655,  -655,   362,   366,   828,  -655, 10180,  2469, 10302,
    3112,  2469, 10424,  3112,  3112,  2469,  2647, 10547,   831, 10669,
     831,  -655, 10791,    32,  -655,  -655,  3112,   791,  -655,  -655,
    2469,  3112,  2469,  2739,  2469,  3112,  3821,  2647,  2469,  -655,
     738,  -655,  3112,  -655,  -655,  2469, 10913,   831,  -655,  3112,
    2469,   831
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,   157,   131,   141,   217,   208,     0,     0,     0,     2,
       3,     5,     6,     7,     8,     9,   146,   147,   148,   166,
     167,   168,   169,   158,   160,     0,   135,   132,   133,   136,
       0,   142,   143,     0,   218,   219,     0,   209,   210,     0,
       0,     1,     4,   159,     0,     0,     0,   134,     0,   144,
       0,   222,   220,     0,     0,     0,   336,   339,   338,     0,
     333,   332,   334,   335,   340,   337,   212,   440,   442,   441,
     439,   443,   444,     0,   431,   170,     0,     0,   161,     0,
       0,     0,   140,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   185,   188,   189,   190,   191,   192,   193,   194,
     195,   139,   184,   172,   183,   196,   197,   173,   313,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   317,   286,   285,
     145,   279,   281,   280,   278,   282,   283,   300,   301,   302,
     303,     0,   230,     0,     0,     0,     0,     0,   433,     0,
       0,     0,     0,   211,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,    31,    12,     0,   137,   162,   185,
     204,   164,   202,     0,     0,     0,     0,     0,     0,     0,
     179,   180,   178,     0,     0,     0,     0,     0,     0,     0,
       0,   276,   308,     0,   276,     0,   406,   438,   437,     0,
     430,   429,     0,   446,   447,     0,   343,   344,     0,   341,
       0,     0,   353,   354,   355,   356,   357,   358,   359,   360,
     361,   362,   363,   364,   365,   366,   367,   368,   369,   370,
     371,   372,     0,     0,     0,   456,     0,     0,     0,     0,
       0,   435,     0,     0,   450,   452,   453,     0,     0,     0,
     276,     0,   287,     0,     0,   291,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   183,     0,   239,   445,
       0,     0,   213,     0,   215,     0,     0,   246,   170,   270,
       0,   149,   151,   152,   153,   154,   155,     0,     0,    43,
      42,     0,     0,     0,     0,     0,     0,    52,     0,     0,
       0,    48,    50,     0,    32,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    34,
       0,   138,     0,   163,     0,     0,   203,   165,   171,   201,
     199,   177,     0,     0,     0,   200,   175,   176,   198,   186,
     187,   284,   314,     0,     0,   309,     0,     0,     0,   318,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   276,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   329,     0,     0,   384,
       0,   276,   374,   373,   375,   288,   294,   290,   289,   377,
     376,   378,   379,   380,   381,   382,   383,   385,   386,   387,
     388,   389,   390,   391,   392,   393,   394,   395,   396,   397,
     398,   399,   400,   401,   402,   403,   404,   405,   297,   298,
     295,   296,   231,     0,     0,     0,     0,     0,     0,     0,
     238,     0,   434,     0,     0,     0,     0,   247,   248,   156,
       0,     0,   271,   272,    10,   150,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    53,     0,    51,
       0,     0,     0,     0,   260,    60,    61,    62,    63,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   104,   101,   112,     0,    49,
       0,    31,    18,    17,    22,     0,    21,    16,     0,     0,
       0,     0,    23,    25,    29,    28,    27,    13,     0,   206,
     205,     0,   182,   181,   277,     0,     0,     0,     0,   408,
       0,   407,     0,   448,   449,   307,   299,   342,     0,     0,
     350,     0,   321,   322,   323,   457,     0,     0,   327,     0,
     454,   436,   455,   304,   451,   305,   306,     0,   324,   292,
       0,     0,   232,     0,   224,     0,   236,   240,     0,     0,
     221,   436,   214,   216,     0,     0,   249,   275,     0,   274,
     273,    46,    47,    37,    45,    44,    36,    41,     0,     0,
      40,    35,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   185,     0,     0,   121,     0,     0,
     239,   259,     0,     0,    64,     0,     0,     0,     0,     0,
       0,     0,     0,   109,     0,     0,     0,     0,     0,     0,
     437,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    14,    78,
       0,   106,     0,     0,    33,     0,     0,     0,   126,     0,
       0,     0,     0,   174,     0,   315,     0,   310,   312,     0,
     319,     0,     0,   345,     0,   348,     0,     0,   325,     0,
       0,   330,     0,     0,     0,     0,     0,     0,   243,   432,
      11,     0,   234,     0,     0,     0,     0,    56,     0,    54,
      58,     0,     0,     0,     0,     0,     0,   411,   125,   124,
     123,    65,     0,     0,     0,   268,   269,   261,   262,     0,
       0,     0,   108,     0,     0,     0,     0,     0,     0,   102,
       0,     0,     0,   416,     0,     0,     0,     0,   103,     0,
       0,     0,     0,     0,    70,    69,    81,     0,    83,    68,
       0,     0,     0,    71,    73,    77,    76,    75,     0,     0,
     418,    15,     0,    26,    19,     0,   129,    24,   207,     0,
       0,     0,     0,   346,     0,   351,   352,   326,   328,     0,
     293,   233,   225,     0,   228,   229,   239,   227,   237,     0,
       0,     0,   260,   235,   413,     0,     0,    38,    57,     0,
      55,    93,    92,     0,     0,     0,     0,   122,     0,   263,
       0,   264,   428,     0,     0,     0,    91,    99,   409,     0,
       0,     0,   425,     0,     0,   105,     0,   110,     0,     0,
       0,     0,     0,     0,     0,   118,     0,     0,     0,     0,
       0,     0,     0,   107,   111,     0,     0,     0,   316,   311,
     320,     0,     0,   331,   226,   242,     0,     0,     0,   239,
       0,     0,     0,    31,    39,    59,     0,     0,   412,    88,
       0,   266,     0,    66,    67,     0,     0,     0,     0,     0,
       0,   423,     0,   417,     0,     0,   119,     0,     0,     0,
       0,     0,     0,     0,    74,     0,    87,     0,    72,    20,
     130,     0,     0,     0,   245,   241,   255,   253,   254,   252,
       0,     0,   415,   414,    30,     0,   100,     0,     0,     0,
     265,     0,     0,     0,   420,     0,     0,   410,     0,   426,
     427,     0,    94,    79,     0,     0,     0,   114,   113,     0,
       0,     0,     0,     0,     0,   127,   347,   349,     0,   223,
       0,   250,    95,     0,     0,   267,    98,     0,     0,     0,
     419,     0,     0,   120,   115,     0,     0,     0,    85,     0,
      82,    80,     0,   244,   257,   258,   256,   239,    89,    90,
       0,   421,     0,     0,     0,   116,     0,     0,     0,   128,
     242,    97,   422,   424,    96,     0,     0,    86,   251,   117,
       0,    84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -655,  -655,  -655,   895,  -655,  -655,  -167,  -148,   251,  -655,
     556,  -655,  -655,   166,   900,  -655,  -655,   160,   -37,  -655,
    -655,  -655,   892,   -74,   905,  -655,  -655,   890,  -655,  -315,
    -149,    55,  -655,   879,   109,   332,    13,   787,  -141,  -655,
    -655,   545,  -182,   744,   454,  -655,   865,   912,   566,  -655,
     909,   916,    54,  -655,   338,  -655,  -655,   223,  -632,  -655,
     -73,  -478,  -655,  -655,  -655,  -309,  -655,  -655,   124,  -655,
    -655,   203,  -655,  -655,  -655,   467,   -88,   -48,   617,   903,
     989,  1089,  1310,   803,   440,   554,  -655,    21,  -655,  -655,
     253,  -655,  -655,  -655,   568,   441,  -655,  -634,  -655,  -654,
    -655,  -655,  -655,  -655,    45,  -655,   -98,   -25,  -146,  -655,
     128,  -142,  -195,  -134,  -130,   553,  -655,  -655,  -655
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   185,   632,   339,   350,
     351,   509,   507,   739,   740,   927,   646,   647,   689,   549,
      13,    27,    28,   741,   340,    14,    31,    32,    15,   330,
     331,   332,    23,    24,   486,   190,   534,   103,   104,   105,
     106,   191,   192,   373,   333,    37,    38,   226,   334,    34,
      35,   227,   605,   826,   164,   313,   832,   318,   479,   480,
     944,   945,   989,   335,   487,   488,   831,  1017,   650,   651,
     757,   758,   759,   336,   492,   493,   210,   411,   151,   152,
     153,   154,   155,   156,   228,   229,   157,   713,   158,   579,
     580,   159,   160,   215,   216,   659,   746,   619,   772,   535,
     536,   918,   537,   861,   862,   653,   261,   220,   262,   221,
      74,   222,   223,   224,   263,   264,   265,   266,   256
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "150,610,268,186,255,165,747,773,370,376,502,73,754,267,219,314,495,768,760,489,320,321,316,213,839,768,169,472,338,341,342,392,344,345,352,720,413,171,482,706,254,406,716,169,853,26,219,393,709,1,2,3,426,4,422,16,707,269,30,102,781,209,211,214,16,710,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,73,395,399,984,102,102,5,922,102,270,102,424,102,102,102,102,102,343,207,194,765,498,25,39,500,33,36,83,469,84,85,402,208,322,207,324,195,725,501,257,407,499,477,725,499,86,346,777,208,87,88,89,90,91,844,33,582,372,499,353,369,354,403,161,25,162,726,473,393,583,854,769,990,420,589,170,410,412,420,778,423,414,614,417,423,721,172,81,102,81,616,81,405,423,896,102,36,102,430,423,542,543,544,81,546,547,895,407,41,552,553,566,43,352,360,557,102,393,400,102,102,415,588,908,923,468,420,679,102,102,102,102,6,7,985,428,423,429,431,478,432,433,434,599,423,694,439,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,581,316,540,567,951,393,490,40,572,393,418,558,845,45,822,189,46,585,44,852,163,64,393,93,94,95,96,97,98,99,100,714,48,565,597,93,94,95,96,97,98,99,100,755,756,374,883,545,50,362,548,550,551,254,679,554,555,556,715,591,363,364,362,365,601,366,681,367,623,275,626,627,628,630,631,393,684,368,685,161,686,162,564,76,79,45,568,569,46,570,687,573,360,575,75,576,393,578,393,637,638,639,584,894,207,587,80,973,590,655,656,593,360,595,596,101,376,1018,208,73,611,1019,1030,503,102,102,672,77,176,177,178,179,102,102,102,19,20,21,22,-170,920,280,420,499,436,504,180,181,188,375,355,356,193,102,196,102,198,199,200,201,202,736,402,912,362,499,607,921,681,360,671,102,612,613,393,491,648,471,666,617,685,499,686,310,194,621,176,177,178,179,53,17,687,403,724,207,176,177,178,179,17,195,362,652,180,181,965,966,654,208,991,731,658,660,180,181,664,665,366,667,367,673,362,207,102,81,102,275,743,166,368,420,315,864,73,974,365,208,366,323,367,325,369,369,369,393,369,369,499,393,368,252,369,369,959,865,182,369,197,83,744,84,85,379,380,56,803,804,745,272,866,807,385,386,387,388,253,86,719,362,499,87,88,89,90,91,183,259,499,722,363,364,203,365,184,366,204,367,728,280,752,419,205,18,317,581,837,368,779,206,57,102,18,704,182,496,823,326,735,362,753,705,737,369,629,275,369,369,369,420,369,369,421,58,260,366,102,367,491,798,309,310,420,497,183,474,46,368,763,271,337,774,541,435,183,60,61,62,63,64,65,863,541,764,868,869,787,897,499,790,791,792,389,378,795,796,797,167,690,168,691,800,203,499,802,353,204,354,805,806,67,808,205,809,924,810,279,280,811,206,812,813,603,925,604,816,904,674,675,819,281,771,382,677,499,827,19,20,21,22,-170,174,92,499,175,678,679,838,64,205,93,94,95,96,97,98,99,100,309,310,641,642,643,559,560,176,177,178,179,390,401,207,562,563,662,939,663,860,311,312,353,692,354,180,181,208,67,67,404,872,873,874,574,102,323,420,483,102,723,403,698,102,699,102,416,622,496,624,884,92,592,203,67,425,941,204,67,67,67,437,438,205,475,369,369,476,203,369,206,851,204,863,481,485,497,67,205,46,494,506,67,67,1,206,902,609,4,327,508,328,329,608,906,615,633,680,362,369,900,618,681,635,634,602,915,606,702,682,683,636,684,661,685,800,686,19,20,21,22,484,928,727,402,729,687,932,933,5,935,730,937,733,738,182,203,940,72,477,204,901,942,943,674,675,205,742,107,762,780,952,953,206,203,850,362,782,204,835,681,843,911,679,205,183,369,963,993,994,855,206,685,903,686,870,871,898,771,892,976,107,107,957,687,107,649,107,381,107,107,107,107,107,203,67,478,958,204,971,988,369,1001,42,205,539,29,840,102,102,817,206,847,997,72,72,999,717,47,203,49,78,1002,204,102,258,561,67,67,205,1007,29,1009,203,377,1012,206,204,68,173,72,53,52,205,72,72,72,66,51,82,206,950,732,372,833,899,1038,1026,620,849,577,657,986,72,969,107,815,571,72,72,668,102,107,362,107,594,0,681,1036,0,0,187,0,0,682,683,0,684,67,685,0,686,0,107,0,675,107,107,0,-170,1013,687,0,0,0,107,107,107,107,0,67,0,67,679,68,68,56,67,203,0,0,0,204,67,69,67,0,0,205,383,67,0,0,0,0,206,67,0,203,67,68,0,204,67,68,68,68,0,205,384,0,0,0,0,821,206,0,0,828,0,0,57,830,68,834,0,0,0,68,68,-170,-170,1,0,-170,347,4,327,348,328,329,72,349,187,58,0,0,0,1,67,371,347,4,327,348,328,329,67,538,0,69,69,0,0,470,0,60,61,62,63,64,65,0,72,72,0,0,5,0,-170,0,0,0,362,70,0,69,681,0,0,69,69,69,5,682,683,0,684,67,685,0,686,-170,0,0,0,0,67,0,69,0,687,0,0,69,69,0,0,19,20,21,22,-170,0,-170,-170,-170,-170,-170,-170,0,0,0,0,72,0,107,107,0,0,0,68,0,0,107,107,107,0,53,0,0,0,0,0,0,0,72,0,72,0,70,70,0,72,0,107,0,107,0,72,0,72,0,68,68,0,72,0,0,67,0,0,72,107,0,72,70,947,949,72,70,70,70,674,675,676,0,0,217,677,0,0,0,960,0,0,0,371,533,70,0,678,679,0,70,70,0,0,0,54,55,0,0,56,0,0,0,69,0,0,0,0,0,0,68,72,107,0,107,0,0,0,0,72,0,67,0,0,0,0,995,0,0,0,0,0,68,0,68,69,69,355,356,68,0,0,0,358,57,68,0,68,0,0,0,0,68,0,0,359,360,0,68,0,72,68,0,0,0,68,67,58,0,72,0,0,669,0,0,0,0,67,0,0,0,71,0,0,0,0,0,670,0,60,61,62,63,64,65,0,69,680,362,107,0,0,681,70,0,0,0,0,0,682,683,68,684,0,685,0,686,0,69,68,69,0,67,0,107,69,687,0,688,0,0,69,0,69,0,70,70,82,69,625,0,72,0,0,69,0,0,69,645,0,356,69,0,187,0,0,0,71,71,0,68,0,0,0,0,361,362,0,360,68,0,0,0,693,0,0,0,363,364,0,365,0,366,71,367,0,0,71,71,71,0,0,0,0,368,0,70,69,0,0,0,0,53,0,0,69,71,72,0,0,0,71,71,0,0,0,0,0,70,0,70,0,0,0,0,70,0,0,0,0,0,70,0,70,0,0,0,0,70,67,0,68,0,0,70,0,69,70,0,0,0,70,72,0,0,69,0,0,0,0,0,107,0,72,0,107,0,0,0,107,0,107,67,54,55,187,0,56,362,0,0,748,749,750,0,0,0,0,371,363,364,0,365,0,366,0,367,70,0,0,0,0,0,0,0,70,368,68,72,784,785,786,0,788,789,0,0,0,793,794,0,0,57,0,71,0,0,69,0,0,0,0,0,0,0,355,356,357,0,734,0,358,0,0,0,58,70,0,0,0,0,0,68,359,360,70,71,71,0,0,0,0,0,68,0,59,0,60,61,62,63,64,65,0,0,0,53,0,841,842,0,371,187,0,0,0,0,846,0,848,0,0,0,69,0,0,0,0,0,856,857,858,859,0,0,0,0,0,68,0,0,0,0,0,0,0,0,71,875,0,0,0,0,0,814,72,217,70,0,0,0,0,0,0,107,107,0,0,69,71,0,71,0,0,0,0,71,54,55,69,107,56,71,0,71,0,0,0,72,71,0,0,0,361,362,71,0,0,71,0,0,0,71,0,905,363,364,0,365,907,366,0,367,0,0,0,0,0,913,914,0,0,368,70,69,0,57,107,0,0,0,0,0,0,0,926,0,0,0,83,0,84,85,934,0,936,0,938,71,58,0,0,0,0,0,68,71,86,679,0,0,87,88,89,90,91,0,0,70,218,0,60,61,62,63,64,65,0,0,70,0,0,964,0,967,0,0,970,68,83,972,84,85,0,0,977,978,71,0,0,0,0,0,0,0,0,71,86,0,0,0,87,88,89,90,91,0,0,0,0,992,0,0,0,70,0,996,0,0,0,0,1000,0,0,83,0,829,85,0,1003,1004,69,0,0,0,1008,0,1010,1011,0,0,0,86,0,0,1016,87,88,89,90,91,0,0,1021,0,0,1023,0,362,0,1025,53,681,0,69,0,0,0,71,682,683,0,684,0,685,1031,686,1032,0,1034,0,0,0,1037,0,703,687,0,0,0,1039,83,189,946,85,1041,0,0,64,0,93,94,95,96,97,98,99,100,0,86,0,217,0,87,88,89,90,91,0,0,0,83,0,948,85,0,0,0,0,0,0,70,54,55,71,0,56,0,53,86,0,0,189,87,88,89,90,91,64,0,93,94,95,96,97,98,99,100,0,0,0,0,0,0,70,0,0,0,0,0,0,0,0,0,0,0,0,0,0,71,0,57,0,0,0,0,968,92,217,0,71,0,0,64,0,93,94,95,96,97,98,99,100,0,58,0,0,0,0,54,55,0,0,56,0,0,0,0,0,0,0,0,0,0,218,0,60,61,62,63,64,65,0,0,0,71,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,92,0,0,0,57,0,64,0,93,94,95,96,97,98,99,100,0,0,0,108,0,109,0,0,110,0,0,58,0,0,92,0,0,111,0,0,64,0,93,94,95,96,97,98,99,100,0,218,0,60,61,62,63,64,65,0,112,824,825,0,0,0,113,0,114,0,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,71,139,140,141,0,0,0,0,510,511,512,513,0,0,514,0,515,516,517,518,519,0,0,0,0,0,520,521,0,0,0,0,0,71,0,0,0,0,0,0,142,143,144,57,0,0,0,1014,1015,0,0,0,522,0,523,0,145,108,524,109,0,0,110,0,0,0,58,0,0,0,0,111,212,0,146,275,147,0,0,0,0,0,0,0,0,0,148,149,60,61,62,63,64,65,112,525,0,526,0,0,113,0,114,0,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,527,139,140,141,0,528,0,0,0,0,279,280,0,0,0,0,0,0,0,0,529,530,531,0,281,532,0,0,0,0,0,0,0,0,0,0,0,0,92,0,142,143,144,57,301,0,0,0,305,306,307,308,309,310,0,0,145,108,0,109,0,0,110,0,0,0,58,0,0,0,0,111,311,312,146,0,147,0,0,0,0,0,0,0,0,0,148,149,60,61,62,63,64,65,112,0,0,649,0,0,113,0,114,0,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,0,139,140,141,0,0,0,0,0,0,0,0,0,0,510,511,512,513,0,0,514,0,515,516,517,518,519,0,674,675,676,0,520,521,677,0,0,0,0,0,142,143,144,57,0,0,678,679,0,0,0,0,0,0,0,0,145,108,522,109,523,0,110,0,524,0,58,0,0,0,0,111,0,0,146,0,147,0,0,0,0,0,0,0,0,0,148,149,60,61,62,63,64,65,112,0,0,0,0,0,113,525,114,526,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,799,139,140,141,0,0,527,0,0,0,0,528,0,0,0,0,0,0,0,0,680,362,0,0,0,681,529,530,531,0,0,532,682,683,0,684,0,685,0,686,142,143,144,57,92,0,0,0,0,687,0,801,0,0,0,0,145,108,0,109,0,0,110,0,0,0,58,0,0,0,53,111,0,0,146,0,147,0,0,0,0,0,0,0,0,0,148,149,60,61,62,63,64,65,112,0,0,0,0,0,113,0,114,0,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,0,139,140,141,0,54,55,0,0,56,0,0,674,675,676,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,678,679,0,0,0,0,0,0,0,0,142,143,144,57,0,0,0,0,0,0,0,0,0,0,57,0,145,0,0,0,0,510,511,512,513,0,58,514,0,515,516,517,518,519,146,0,147,58,0,520,521,0,0,0,0,0,148,149,60,61,62,63,64,65,0,0,0,225,0,60,61,62,63,64,65,522,0,523,0,0,0,524,510,511,512,513,0,0,514,0,515,516,517,518,519,0,0,0,0,0,520,521,0,0,0,0,0,0,0,0,680,362,0,0,0,681,640,0,525,0,526,0,682,683,0,684,522,685,523,686,0,0,524,0,0,0,0,0,0,687,0,1033,0,0,0,0,0,0,0,0,0,0,0,0,0,641,642,643,273,527,0,0,0,274,528,275,0,0,0,525,0,526,0,0,0,0,0,0,0,529,530,531,0,276,532,674,675,676,0,0,0,677,0,0,0,0,0,644,0,0,0,0,0,678,679,674,675,676,0,751,527,677,277,278,0,528,0,0,0,0,0,0,0,678,679,0,0,0,0,0,529,530,531,916,917,532,279,280,0,0,0,0,0,0,0,0,0,0,644,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,0,674,675,676,0,909,0,677,0,0,674,675,676,0,910,0,677,311,312,678,679,0,0,0,0,0,0,0,678,679,0,0,680,362,0,0,0,681,987,0,0,0,0,0,682,683,0,684,0,685,0,686,680,362,674,675,676,681,956,0,677,687,0,0,682,683,0,684,0,685,0,686,678,679,674,675,676,0,0,0,677,687,0,0,0,0,0,0,0,0,0,0,678,679,355,356,357,0,0,0,358,0,0,0,0,0,0,0,0,0,0,0,359,360,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,680,362,0,0,0,681,0,0,0,680,362,0,682,683,681,684,0,685,0,686,0,682,683,0,684,0,685,0,686,687,0,0,0,0,0,0,0,0,687,0,0,0,273,0,0,0,0,274,0,275,0,0,0,680,362,0,0,0,681,0,0,0,0,0,0,682,683,276,684,0,685,0,686,680,362,0,0,0,681,0,0,0,687,0,0,682,683,0,684,0,685,0,686,361,362,0,277,278,0,0,0,0,687,0,0,363,364,0,365,0,366,0,367,0,0,275,0,0,0,0,279,280,368,0,0,0,0,0,0,0,0,0,0,276,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,279,280,0,276,0,0,0,1005,0,0,1006,0,0,0,281,282,283,284,285,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,301,302,303,304,305,306,307,308,309,310,0,0,0,0,0,0,0,275,0,0,0,0,279,280,0,0,0,0,311,312,0,0,0,0,0,276,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,279,280,0,276,0,0,0,783,0,0,0,0,0,0,281,282,283,284,285,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,301,302,0,0,305,306,307,308,309,310,0,0,0,0,0,0,0,275,0,0,0,0,279,280,0,0,0,0,311,312,0,0,0,0,0,276,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,279,280,0,276,0,0,0,930,0,0,0,0,0,0,281,282,283,284,285,0,0,0,0,0,0,0,0,0,0,0,275,0,277,278,301,0,0,0,305,306,307,308,309,310,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,311,312,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,279,280,0,0,274,0,275,0,0,0,0,0,0,0,281,0,0,311,312,0,0,0,0,0,276,0,0,0,931,0,0,0,0,0,0,0,0,0,305,306,307,308,309,310,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,311,312,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,975,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,311,312,876,877,0,0,0,0,276,0,0,1035,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,695,0,0,0,878,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,696,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,701,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,311,312,396,0,882,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,397,398,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,711,712,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,766,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,767,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,311,312,879,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,961,880,0,0,0,0,0,0,311,312,962,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,311,312,981,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,319,275,0,0,0,982,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,391,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,311,312,394,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,408,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,409,0,0,311,312,427,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,505,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,598,275,0,0,0,0,586,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,600,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,697,0,0,0,0,700,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,311,312,708,0,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,398,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,718,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,761,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,770,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,775,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,776,766,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,818,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,820,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,867,275,0,0,0,836,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,311,312,881,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,885,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,886,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,311,312,887,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,888,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,311,312,889,0,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,311,312,890,0,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,891,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,919,0,893,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,929,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,954,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,955,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,979,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,980,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,983,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,998,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,1020,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1022,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,1024,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,311,312,1027,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,1028,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,1040,1029,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,274,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,273,0,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,273,0,0,0,0,0,0,275,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,293,276,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,0,0,0,0,0,0,0,273,0,0,0,277,278,0,275,0,0,311,312,0,0,0,0,0,0,0,0,0,0,0,0,0,276,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,291,292,277,278,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,0,0,0,0,273,279,280,0,0,0,0,275,0,0,0,0,0,311,312,281,282,283,284,285,286,287,288,289,290,276,292,0,0,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,0,0,0,0,0,0,0,0,277,278,0,275,0,0,0,0,0,311,312,0,0,0,0,0,0,0,0,0,0,276,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,286,287,288,289,290,0,0,277,278,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,0,0,0,0,0,0,279,280,275,0,0,0,0,0,0,0,0,0,311,312,281,282,283,284,285,0,276,0,0,0,0,0,0,0,0,0,0,0,299,300,301,302,303,304,305,306,307,308,309,310,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,311,312,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,281,282,283,284,285,0,0,0,0,0,0,0,0,0,0,0,0,0,299,300,301,302,303,304,305,306,307,308,309,310,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,311,312";
  public static final short yytable_ninf_ = -171;
  public static final short yytable_[];
  static {
    String[] shorts = yyTableString.split(",");
    int size = shorts.length;
    yytable_ = new short[size];
    for (int pos=0; pos < size; pos++) {
    	yytable_[pos] = Short.valueOf(shorts[pos]);
    }
  }
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final String yyCheckString = "48,479,144,77,138,53,640,661,13,191,13,36,13,143,112,161,331,31,650,328,166,167,163,111,13,31,12,18,176,177,178,21,180,181,183,13,13,12,18,13,138,59,27,12,27,180,144,146,13,3,4,5,151,7,13,0,30,145,180,46,59,109,110,111,9,30,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,113,30,30,13,79,80,48,27,83,145,85,13,87,88,89,90,91,179,90,27,59,13,1,180,13,4,5,17,311,19,20,118,104,169,90,171,44,17,13,17,146,33,49,17,33,35,182,13,104,39,40,41,42,43,13,34,18,47,33,17,185,19,149,17,43,19,46,146,146,18,141,173,46,146,146,149,252,253,146,173,146,146,485,259,146,151,149,180,163,180,487,180,149,146,102,170,75,172,274,146,355,356,357,180,359,360,826,146,0,364,365,394,33,350,33,370,191,146,146,194,195,257,18,845,866,311,146,33,203,204,205,206,174,175,144,271,146,273,274,146,276,277,278,18,146,18,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,409,405,353,396,899,146,47,180,401,146,260,372,146,146,18,180,149,414,180,760,149,186,146,188,189,190,191,192,193,194,195,146,12,394,427,188,189,190,191,192,193,194,195,56,57,45,18,358,180,143,361,362,363,414,33,366,367,368,173,420,154,155,143,157,473,159,147,161,498,19,500,501,502,503,504,146,157,171,159,17,161,19,393,12,12,146,397,398,149,400,171,402,33,404,33,406,146,408,146,510,511,512,413,18,90,416,34,18,419,520,521,422,33,424,425,46,561,18,104,407,481,18,1017,13,374,375,531,12,14,15,16,17,382,383,384,176,177,178,179,180,146,91,146,33,279,13,32,33,79,149,14,15,83,403,85,405,87,88,89,90,91,13,118,149,143,33,477,173,147,33,531,421,483,484,146,329,513,312,529,490,159,33,161,133,27,496,14,15,16,17,17,0,171,149,603,90,14,15,16,17,9,44,143,514,32,33,62,63,519,104,951,149,523,524,32,33,527,528,159,530,161,532,143,90,474,180,476,19,13,17,171,146,163,100,522,146,157,104,159,170,161,172,542,543,544,146,546,547,33,146,171,17,552,553,149,18,142,557,180,17,13,19,20,194,195,92,696,697,13,98,33,701,203,204,205,206,17,35,589,143,33,39,40,41,42,43,172,17,33,600,154,155,27,157,180,159,31,161,609,91,13,118,37,0,180,714,736,171,669,44,137,561,9,13,142,118,725,180,629,143,33,21,633,623,142,19,626,627,628,146,630,631,149,160,17,159,586,161,492,690,132,133,146,146,172,149,149,171,13,17,180,662,180,180,172,182,183,184,185,186,187,770,180,13,775,776,677,102,33,680,681,682,180,18,685,686,687,17,17,19,19,692,27,33,695,17,31,19,699,700,36,702,37,704,13,706,90,91,709,44,711,712,17,13,19,716,836,14,15,720,104,661,36,20,33,726,176,177,178,179,180,169,180,33,172,32,33,738,186,37,188,189,190,191,192,193,194,195,132,133,138,139,140,374,375,14,15,16,17,180,31,90,383,384,17,885,19,768,152,153,17,100,19,32,33,104,112,113,13,780,781,782,403,723,405,146,12,727,149,149,144,731,146,733,17,497,118,499,799,180,421,27,138,13,887,31,142,143,144,92,180,37,180,803,804,149,27,807,44,759,31,920,146,172,146,161,37,149,173,180,166,167,3,44,835,50,7,8,180,10,11,180,843,180,12,142,143,837,59,180,147,12,180,474,855,476,46,154,155,180,157,17,159,864,161,176,177,178,179,12,871,149,118,18,171,876,877,48,879,173,881,149,12,142,27,886,36,49,31,100,891,892,14,15,37,12,46,21,12,900,901,44,27,146,143,59,31,17,147,20,850,33,37,172,904,916,957,958,20,44,159,180,161,17,118,180,866,27,929,79,80,17,171,83,55,85,21,87,88,89,90,91,27,279,146,17,31,20,51,939,27,9,37,350,2,742,896,897,18,44,753,962,112,113,965,586,27,27,31,43,971,31,912,139,382,311,312,37,979,27,981,27,191,984,44,31,36,75,138,17,34,37,142,143,144,36,33,45,44,898,615,47,732,832,1030,1006,492,757,407,522,942,161,920,163,714,400,166,167,530,959,170,143,172,423,-1,147,1027,-1,-1,77,-1,-1,154,155,-1,157,372,159,-1,161,-1,191,-1,15,194,195,-1,17,988,171,-1,-1,-1,203,204,205,206,-1,394,-1,396,33,112,113,92,401,27,-1,-1,-1,31,407,36,409,-1,-1,37,38,414,-1,-1,-1,-1,44,420,-1,27,423,138,-1,31,427,142,143,144,-1,37,38,-1,-1,-1,-1,723,44,-1,-1,727,-1,-1,137,731,161,733,-1,-1,-1,166,167,88,89,3,-1,92,6,7,8,9,10,11,279,13,179,160,-1,-1,-1,3,473,186,6,7,8,9,10,11,481,13,-1,112,113,-1,-1,180,-1,182,183,184,185,186,187,-1,311,312,-1,-1,48,-1,137,-1,-1,-1,143,36,-1,138,147,-1,-1,142,143,144,48,154,155,-1,157,522,159,-1,161,160,-1,-1,-1,-1,531,-1,161,-1,171,-1,-1,166,167,-1,-1,176,177,178,179,180,-1,182,183,184,185,186,187,-1,-1,-1,-1,372,-1,374,375,-1,-1,-1,279,-1,-1,382,383,384,-1,17,-1,-1,-1,-1,-1,-1,-1,394,-1,396,-1,112,113,-1,401,-1,403,-1,405,-1,407,-1,409,-1,311,312,-1,414,-1,-1,603,-1,-1,420,421,-1,423,138,896,897,427,142,143,144,14,15,16,-1,-1,67,20,-1,-1,-1,912,-1,-1,-1,343,349,161,-1,32,33,-1,166,167,-1,-1,-1,88,89,-1,-1,92,-1,-1,-1,279,-1,-1,-1,-1,-1,-1,372,473,474,-1,476,-1,-1,-1,-1,481,-1,669,-1,-1,-1,-1,959,-1,-1,-1,-1,-1,394,-1,396,311,312,14,15,401,-1,-1,-1,20,137,407,-1,409,-1,-1,-1,-1,414,-1,-1,32,33,-1,420,-1,522,423,-1,-1,-1,427,714,160,-1,531,-1,-1,165,-1,-1,-1,-1,725,-1,-1,-1,36,-1,-1,-1,-1,-1,180,-1,182,183,184,185,186,187,-1,372,142,143,561,-1,-1,147,279,-1,-1,-1,-1,-1,154,155,473,157,-1,159,-1,161,-1,394,481,396,-1,770,-1,586,401,171,-1,173,-1,-1,407,-1,409,-1,311,312,497,414,499,-1,603,-1,-1,420,-1,-1,423,513,-1,15,427,-1,513,-1,-1,-1,112,113,-1,522,-1,-1,-1,-1,142,143,-1,33,531,-1,-1,-1,538,-1,-1,-1,154,155,-1,157,-1,159,138,161,-1,-1,142,143,144,-1,-1,-1,-1,171,-1,372,473,-1,-1,-1,-1,17,-1,-1,481,161,669,-1,-1,-1,166,167,-1,-1,-1,-1,-1,394,-1,396,-1,-1,-1,-1,401,-1,-1,-1,-1,-1,407,-1,409,-1,-1,-1,-1,414,887,-1,603,-1,-1,420,-1,522,423,-1,-1,-1,427,714,-1,-1,531,-1,-1,-1,-1,-1,723,-1,725,-1,727,-1,-1,-1,731,-1,733,920,88,89,635,-1,92,143,-1,-1,641,642,643,-1,-1,-1,-1,648,154,155,-1,157,-1,159,-1,161,473,-1,-1,-1,-1,-1,-1,-1,481,171,669,770,674,675,676,-1,678,679,-1,-1,-1,683,684,-1,-1,137,-1,279,-1,-1,603,-1,-1,-1,-1,-1,-1,-1,14,15,16,-1,18,-1,20,-1,-1,-1,160,522,-1,-1,-1,-1,-1,714,32,33,531,311,312,-1,-1,-1,-1,-1,725,-1,180,-1,182,183,184,185,186,187,-1,-1,-1,17,-1,743,744,-1,741,742,-1,-1,-1,-1,752,-1,754,-1,-1,-1,669,-1,-1,-1,-1,-1,764,765,766,767,-1,-1,-1,-1,-1,770,-1,-1,-1,-1,-1,-1,-1,-1,372,783,-1,-1,-1,-1,-1,65,887,67,603,-1,-1,-1,-1,-1,-1,896,897,-1,-1,714,394,-1,396,-1,-1,-1,-1,401,88,89,725,912,92,407,-1,409,-1,-1,-1,920,414,-1,-1,-1,142,143,420,-1,-1,423,-1,-1,-1,427,-1,839,154,155,-1,157,844,159,-1,161,-1,-1,-1,-1,-1,853,854,-1,-1,171,669,770,-1,137,959,-1,-1,-1,-1,-1,-1,-1,870,-1,-1,-1,17,-1,19,20,878,-1,880,-1,882,473,160,-1,-1,-1,-1,-1,887,481,35,33,-1,-1,39,40,41,42,43,-1,-1,714,180,-1,182,183,184,185,186,187,-1,-1,725,-1,-1,917,-1,919,-1,-1,922,920,17,925,19,20,-1,-1,930,931,522,-1,-1,-1,-1,-1,-1,-1,-1,531,35,-1,-1,-1,39,40,41,42,43,-1,-1,-1,-1,955,-1,-1,-1,770,-1,961,-1,-1,-1,-1,966,-1,-1,17,-1,19,20,-1,974,975,887,-1,-1,-1,980,-1,982,983,-1,-1,-1,35,-1,-1,990,39,40,41,42,43,-1,-1,998,-1,-1,1001,-1,143,-1,1005,17,147,-1,920,-1,-1,-1,603,154,155,-1,157,-1,159,1020,161,1022,-1,1024,-1,-1,-1,1028,-1,173,171,-1,-1,-1,1035,17,180,19,20,1040,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,35,-1,67,-1,39,40,41,42,43,-1,-1,-1,17,-1,19,20,-1,-1,-1,-1,-1,-1,887,88,89,669,-1,92,-1,17,35,-1,-1,180,39,40,41,42,43,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,-1,-1,920,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,714,-1,137,-1,-1,-1,-1,65,180,67,-1,725,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,160,-1,-1,-1,-1,88,89,-1,-1,92,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,182,183,184,185,186,187,-1,-1,-1,770,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,137,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,15,-1,17,-1,-1,20,-1,-1,160,-1,-1,180,-1,-1,29,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,180,-1,182,183,184,185,186,187,-1,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,887,95,96,97,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,920,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,53,54,-1,-1,-1,58,-1,60,-1,150,15,64,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,30,-1,168,19,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,101,-1,103,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,142,95,96,97,-1,147,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,162,163,164,-1,104,167,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,134,135,136,137,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,152,153,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,94,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,17,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,88,89,-1,-1,92,-1,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,137,-1,150,-1,-1,-1,-1,14,15,16,17,-1,160,20,-1,22,23,24,25,26,168,-1,170,160,-1,32,33,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,-1,-1,-1,180,-1,182,183,184,185,186,187,58,-1,60,-1,-1,-1,64,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,99,-1,101,-1,103,-1,154,155,-1,157,58,159,60,161,-1,-1,64,-1,-1,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,138,139,140,12,142,-1,-1,-1,17,147,19,-1,-1,-1,101,-1,103,-1,-1,-1,-1,-1,-1,-1,162,163,164,-1,37,167,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,32,33,14,15,16,-1,18,142,20,66,67,-1,147,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,162,163,164,62,63,167,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,14,15,16,-1,18,-1,20,-1,-1,14,15,16,-1,18,-1,20,152,153,32,33,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,142,143,-1,-1,-1,147,173,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,14,15,16,147,18,-1,20,171,-1,-1,154,155,-1,157,-1,159,-1,161,32,33,14,15,16,-1,-1,-1,20,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,142,143,-1,154,155,147,157,-1,159,-1,161,-1,154,155,-1,157,-1,159,-1,161,171,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,37,157,-1,159,-1,161,142,143,-1,-1,-1,147,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,66,67,-1,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,19,-1,-1,-1,-1,90,91,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,-1,66,67,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,90,91,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,104,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
  public static final short yycheck_[];
  static {
    String[] shorts = yyCheckString.split(",");
    int size = shorts.length;
    yycheck_ = new short[size];
    for (int pos=0; pos < size; pos++) {
    	yycheck_[pos] = Short.valueOf(shorts[pos]);
    }
  }
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
     180,   237,   238,   245,   260,   261,   245,   256,   257,   180,
     180,     0,   214,    33,   180,   146,   149,   233,    12,   238,
     180,   262,   261,    17,    88,    89,    92,   137,   160,   180,
     182,   183,   184,   185,   186,   187,   258,   289,   290,   291,
     292,   293,   294,   318,   321,    33,    12,    12,   244,    12,
      34,   180,   235,    17,    19,    20,    35,    39,    40,    41,
      42,    43,   180,   188,   189,   190,   191,   192,   193,   194,
     195,   246,   247,   248,   249,   250,   251,   294,    15,    17,
      20,    29,    52,    58,    60,    64,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    88,    89,    93,    95,
      96,    97,   134,   135,   136,   150,   168,   170,   180,   181,
     288,   289,   290,   291,   292,   293,   294,   297,   299,   302,
     303,    17,    19,   149,   265,   288,    17,    17,    19,    12,
     149,    12,   149,   257,   169,   172,    14,    15,    16,    17,
      32,    33,   142,   172,   180,   217,   234,   235,   246,   180,
     246,   252,   253,   246,    27,    44,   246,   180,   246,   246,
     246,   246,   246,    27,    31,    37,    44,    90,   104,   288,
     287,   288,    30,   287,   288,   304,   305,    67,   180,   317,
     318,   320,   322,   323,   324,   180,   258,   262,   295,   296,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,    17,    17,   317,   324,   329,    17,   248,    17,
      17,   317,   319,   325,   326,   327,   328,   325,   322,   287,
     288,    17,    98,    12,    17,    19,    37,    66,    67,    90,
      91,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,   114,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   152,   153,   266,   319,   246,   249,   180,   268,    18,
     319,   319,   288,   246,   288,   246,   180,     8,    10,    11,
     240,   241,   242,   255,   259,   274,   284,   180,   218,   219,
     235,   218,   218,   234,   218,   218,   288,     6,     9,    13,
     220,   221,   241,    17,    19,    14,    15,    16,    20,    32,
      33,   142,   143,   154,   155,   157,   159,   161,   171,   229,
      13,   235,    47,   254,    45,   149,   253,   254,    18,   246,
     246,    21,    36,    38,    38,   246,   246,   246,   246,   180,
     180,    18,    21,   146,    31,    30,    31,    86,    87,    30,
     146,    31,   118,   149,    13,   149,    59,   146,    61,   149,
     287,   288,   287,    13,   146,   288,    17,   287,   247,   118,
     146,   149,    13,   146,    13,    13,   151,    31,   288,   288,
     287,   288,   288,   288,   288,   180,   321,    92,   180,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   317,   323,
     180,   321,    18,   146,   149,   180,   149,    49,   146,   269,
     270,   146,    18,    12,    12,   172,   245,   275,   276,   276,
      47,   245,   285,   286,   173,   240,   118,   146,    13,    33,
      13,    13,    13,    13,    13,   145,   180,   223,   180,   222,
      14,    15,    16,    17,    20,    22,    23,    24,    25,    26,
      32,    33,    58,    60,    64,   101,   103,   142,   147,   162,
     163,   164,   167,   225,   247,   310,   311,   313,    13,   221,
     287,   180,   217,   217,   217,   288,   217,   217,   288,   230,
     288,   288,   217,   217,   288,   288,   288,   217,   317,   246,
     246,   252,   246,   246,   288,   317,   323,   325,   288,   288,
     288,   305,   325,   288,   246,   288,   288,   296,   288,   300,
     301,   319,    18,    18,   288,   324,   146,   288,    18,   146,
     288,   317,   246,   288,   326,   288,   288,   325,    18,    18,
      86,   319,   246,    17,    19,   263,   246,   288,   180,    50,
     272,   317,   288,   288,   240,   180,   276,   288,   180,   308,
     286,   288,   219,   217,   219,   235,   217,   217,   217,   142,
     217,   217,   218,    12,   180,    12,   180,   218,   218,   218,
      99,   138,   139,   140,   180,   225,   227,   228,   234,    55,
     279,   280,   288,   316,   288,   218,   218,   295,   288,   306,
     288,    17,    17,    19,   288,   288,   287,   288,   306,   165,
     180,   317,   322,   288,    14,    15,    16,    20,    32,    33,
     142,   147,   154,   155,   157,   159,   161,   171,   173,   229,
      17,    19,   100,   225,    18,    32,   158,   145,   144,   146,
      28,   156,    46,   173,    13,    21,    13,    30,    30,    13,
      30,    62,    63,   298,   146,   173,    27,   246,    18,   288,
      13,   151,   288,   149,   319,    17,    46,   149,   288,    18,
     173,   149,   265,   149,    18,   288,    13,   288,    12,   224,
     225,   234,    12,    13,    13,    13,   307,   308,   235,   235,
     235,    18,    13,    33,    13,    56,    57,   281,   282,   283,
     269,    21,    21,    13,    13,    59,    27,    61,    31,   173,
     149,   247,   309,   310,   288,   145,   148,    13,   173,   317,
      12,    59,    59,   163,   225,   225,   225,   288,   225,   225,
     288,   288,   288,   225,   225,   288,   288,   288,   287,    94,
     288,   173,   288,   217,   217,   288,   288,   217,   288,   288,
     288,   288,   288,   288,    65,   301,   288,    18,    18,   288,
      18,   246,    18,   319,    53,    54,   264,   288,   246,    19,
     246,   277,   267,   268,   246,    17,   145,   217,   288,    13,
     224,   225,   225,    20,    13,   146,   225,   228,   225,   282,
     146,   247,   272,    27,   141,    20,   225,   225,   225,   225,
     288,   314,   315,   319,   100,    18,    33,    18,   218,   218,
      17,   118,   288,   288,   288,   225,    31,    32,   158,    31,
     145,    31,   156,    18,   288,    21,    28,    31,    21,    30,
      30,    61,    27,   151,    18,   269,   102,   102,   180,   279,
      59,   100,   288,   180,   217,   225,   288,   225,   308,    18,
      18,   247,   149,   225,   225,   288,    62,    63,   312,    27,
     146,   173,    27,   310,    13,    13,   225,   226,   288,    38,
     163,   163,   288,   288,   225,   288,   225,   288,   225,   217,
     288,   325,   288,   288,   271,   272,    19,   246,    19,   246,
     263,   269,   288,   288,    18,    21,    18,    17,    17,   149,
     246,    21,    31,   288,   225,    62,    63,   225,    65,   315,
     225,    20,   225,    18,   146,   163,   288,   225,   225,    32,
      21,    31,   145,   148,    13,   144,   298,   173,    51,   273,
      46,   272,   225,   287,   287,   246,   225,   288,    61,   288,
     225,    27,   288,   225,   225,   163,   166,   288,   225,   288,
     225,   225,   288,   247,    53,    54,   225,   278,    18,    18,
      21,   225,    61,   225,    21,   225,   288,    31,   145,   144,
     269,   225,   225,   173,   225,   163,   288,   225,   271,   225,
      21,   225
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
     221,   221,   221,   221,   222,   222,   223,   223,   224,   224,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   225,
     225,   225,   225,   225,   225,   225,   225,   225,   225,   226,
     226,   227,   227,   228,   228,   228,   229,   229,   229,   230,
     230,   231,   231,   232,   232,   233,   233,   234,   234,   235,
     235,   236,   236,   237,   237,   238,   239,   239,   239,   240,
     240,   241,   241,   241,   241,   241,   241,   242,   242,   242,
     243,   243,   244,   244,   244,   244,   245,   245,   245,   245,
     245,   246,   246,   246,   246,   246,   246,   246,   246,   246,
     246,   246,   246,   246,   246,   247,   247,   247,   248,   248,
     248,   248,   248,   248,   248,   248,   249,   249,   250,   250,
     251,   251,   252,   252,   253,   253,   253,   254,   255,   255,
     256,   256,   257,   258,   258,   258,   258,   259,   259,   260,
     260,   261,   261,   262,   263,   263,   263,   264,   264,   264,
     265,   265,   266,   266,   267,   267,   268,   268,   269,   269,
     270,   271,   271,   272,   273,   273,   274,   274,   275,   275,
     276,   276,   277,   277,   277,   277,   278,   278,   278,   279,
     279,   280,   281,   281,   282,   282,   282,   282,   283,   283,
     284,   284,   285,   285,   286,   286,   287,   287,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   289,   289,   290,   291,   292,   292,   292,   293,
     294,   295,   295,   296,   296,   297,   298,   298,   299,   299,
     300,   300,   301,   302,   302,   302,   302,   302,   302,   302,
     302,   302,   302,   302,   302,   302,   302,   302,   302,   302,
     302,   302,   302,   303,   303,   303,   303,   303,   303,   303,
     303,   303,   303,   303,   303,   303,   303,   303,   303,   303,
     303,   303,   303,   303,   303,   303,   303,   303,   303,   303,
     303,   303,   303,   303,   303,   303,   304,   304,   305,   306,
     306,   307,   307,   308,   308,   308,   309,   309,   310,   311,
     311,   312,   312,   313,   313,   314,   314,   315,   316,   317,
     317,   318,   318,   318,   318,   319,   319,   320,   320,   321,
     321,   321,   321,   321,   321,   321,   322,   322,   323,   324,
     325,   325,   326,   326,   327,   328,   329,   329
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
       1,     2,     1,     2,     3,     4,     3,     4,     1,     3,
       1,     1,     1,     1,     2,     3,     5,     5,     3,     3,
       3,     3,     5,     3,     5,     3,     3,     3,     2,     6,
       7,     3,     7,     3,    11,     7,     9,     5,     5,     8,
       8,     4,     4,     4,     6,     7,     9,     9,     7,     4,
       6,     1,     3,     3,     1,     4,     2,     4,     3,     2,
       4,     4,     1,     6,     6,     7,     8,    10,     4,     1,
       3,     1,     3,     2,     2,     2,     3,     7,     9,     3,
       5,     1,     2,     1,     2,     1,     1,     1,     2,     3,
       3,     1,     2,     1,     2,     3,     1,     1,     1,     1,
       2,     1,     1,     1,     1,     1,     2,     1,     2,     3,
       1,     3,     4,     5,     4,     5,     1,     1,     1,     1,
       0,     3,     1,     1,     5,     3,     3,     3,     2,     2,
       2,     4,     4,     1,     1,     1,     3,     3,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     3,     3,
       3,     3,     1,     2,     1,     3,     3,     4,     1,     2,
       1,     3,     2,     3,     5,     3,     5,     1,     2,     1,
       2,     6,     2,    10,     1,     3,     4,     1,     1,     1,
       1,     3,     3,     5,     0,     1,     3,     5,     1,     0,
       2,     1,     0,     2,     2,     0,     1,     2,     1,     2,
       7,    10,     3,     3,     3,     3,     1,     1,     1,     1,
       0,     2,     1,     2,     2,     4,     3,     5,     1,     1,
       1,     2,     1,     2,     2,     2,     1,     3,     1,     1,
       1,     1,     1,     1,     3,     1,     1,     2,     3,     3,
       3,     2,     4,     6,     3,     3,     3,     3,     3,     4,
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
       3,     3,     3,     3,     3,     3,     1,     3,     3,     3,
       5,     1,     3,     3,     5,     5,     1,     3,     3,     7,
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
  "explicitFunctionDef", "parameterList", "functionBody", "parameterTypes",
  "patternListTypeList", "identifierTypePairList_opt",
  "identifierTypePairList", "preExpr_opt", "preExpr", "postExpr_opt",
  "postExpr", "measureExpr", "operationDefs", "operationDefList",
  "operationDef", "operationType", "operationBody", "externals_opt",
  "externals", "varInformationList", "varInformation", "mode", "stateDefs",
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
      -1,   217,   142,   288,   145,   217,    -1,   217,    20,   288,
      32,   288,    21,   217,    -1,   217,    32,   217,    -1,   217,
      16,   217,    -1,   217,   155,   217,    -1,   217,   154,   288,
     156,   217,    -1,   217,   157,   217,    -1,   217,    20,   288,
     158,   217,    -1,   217,   171,   288,    -1,   217,   161,   288,
      -1,   217,   159,   288,    -1,    17,   234,    13,   217,    18,
      17,   288,    18,    -1,   180,    -1,   180,    19,    -1,   180,
      17,   287,    18,    -1,   217,   229,    -1,    33,   218,    13,
     217,    -1,    15,   218,    13,   217,    -1,    14,   218,    13,
     217,    -1,   142,   288,   145,   218,    13,   217,    -1,    32,
     218,    13,   142,   288,   145,   217,    -1,    32,   218,    13,
     217,    -1,    16,   218,    13,   217,    -1,   235,    -1,   219,
      -1,   218,    33,   235,    -1,   218,    33,   219,    -1,   180,
     118,   288,    -1,   180,   146,   219,    -1,   221,    -1,   220,
     221,    -1,   241,    -1,     9,   222,    -1,     6,    -1,     6,
     223,    -1,   180,    12,   224,    -1,   222,   180,    12,   224,
      -1,   180,    12,   288,    -1,   223,   180,    12,   288,    -1,
     225,    -1,   234,    13,   225,    -1,    22,    -1,    23,    -1,
      24,    -1,    25,    -1,    26,   288,    -1,    17,   225,    18,
      -1,    20,   288,    21,    27,   225,    -1,    20,   288,    21,
     141,   225,    -1,   225,    33,   225,    -1,   225,    15,   225,
      -1,   225,    14,   225,    -1,   225,   155,   225,    -1,   225,
     154,   288,   156,   225,    -1,   225,   157,   225,    -1,   225,
      20,   288,   158,   225,    -1,   225,   171,   288,    -1,   225,
     161,   288,    -1,   225,   159,   288,    -1,   225,   229,    -1,
     162,   287,    13,    17,   226,    18,    -1,   225,   147,   288,
      31,   288,   148,   225,    -1,   225,    16,   225,    -1,   225,
     142,   288,    31,   288,   145,   225,    -1,   225,    32,   225,
      -1,   225,    20,   288,    31,   288,    32,   288,    31,   288,
      21,   225,    -1,   225,    20,   288,    32,   288,    21,   225,
      -1,   225,   142,   288,    31,   288,    31,   288,   145,   225,
      -1,   225,   142,   288,   145,   225,    -1,    17,   227,    13,
     225,    18,    -1,    17,   227,    13,   225,    18,    17,   287,
      18,    -1,    17,   234,    13,   225,    18,    17,   287,    18,
      -1,    33,   218,    13,   225,    -1,    15,   218,    13,   225,
      -1,    14,   218,    13,   225,    -1,   147,   288,   148,   218,
      13,   225,    -1,    16,   218,    13,    20,   288,    21,   225,
      -1,   142,   288,   145,   218,    13,    20,   288,    21,   225,
      -1,    32,   218,    13,    20,   288,    31,   288,    21,   225,
      -1,    32,   218,    13,    20,   288,    21,   225,    -1,    58,
     295,    59,   225,    -1,    17,    99,   307,    13,   225,    18,
      -1,   311,    -1,    60,   306,   173,    -1,   163,   306,   173,
      -1,   310,    -1,   101,    17,   309,    18,    -1,   247,    19,
      -1,   247,    17,   287,    18,    -1,    20,   316,    21,    -1,
     103,    19,    -1,   103,    17,   288,    18,    -1,   247,   100,
      94,   288,    -1,   313,    -1,   164,   322,    59,   288,   163,
     225,    -1,   164,   317,    59,   288,   163,   225,    -1,   164,
     165,   317,   118,   288,   163,   225,    -1,   164,   180,    12,
     288,    38,   288,   163,   225,    -1,   164,   180,    12,   288,
      38,   288,   166,   288,   163,   225,    -1,   167,   288,   163,
     225,    -1,   225,    -1,   226,   146,   225,    -1,   228,    -1,
     227,    33,   228,    -1,   140,   235,    -1,   139,   235,    -1,
     138,   235,    -1,   143,   230,   144,    -1,   143,   288,    28,
     288,    31,   325,   144,    -1,   143,   288,    28,   288,    31,
     325,    13,   288,   144,    -1,   288,    28,   288,    -1,   230,
     146,   288,    28,   288,    -1,     4,    -1,     4,   232,    -1,
     233,    -1,   232,   233,    -1,   180,    -1,   235,    -1,   235,
      -1,   234,   235,    -1,   180,   149,   246,    -1,   180,   146,
     235,    -1,     5,    -1,     5,   237,    -1,   238,    -1,   237,
     238,    -1,   180,    12,   288,    -1,   242,    -1,   255,    -1,
     259,    -1,   241,    -1,   241,   240,    -1,   242,    -1,   255,
      -1,   259,    -1,   274,    -1,   284,    -1,    10,   276,    -1,
       3,    -1,     3,   243,    -1,     3,   243,    33,    -1,   244,
      -1,   243,    33,   244,    -1,   245,   180,    12,   246,    -1,
     245,   180,    12,   246,   254,    -1,   245,   180,    34,   252,
      -1,   245,   180,    34,   252,   254,    -1,   176,    -1,   177,
      -1,   178,    -1,   179,    -1,    -1,    17,   246,    18,    -1,
     248,    -1,   294,    -1,    35,   180,    36,   252,   173,    -1,
     246,    31,   246,    -1,   246,    37,   246,    -1,    20,   246,
      21,    -1,    43,   246,    -1,    41,   246,    -1,    42,   246,
      -1,    40,   246,    38,   246,    -1,    39,   246,    38,   246,
      -1,   249,    -1,   247,    -1,   180,    -1,   247,    90,   180,
      -1,   247,   104,   180,    -1,   188,    -1,   189,    -1,   190,
      -1,   191,    -1,   192,    -1,   193,    -1,   194,    -1,   195,
      -1,   250,    -1,   251,    -1,   246,    44,   246,    -1,    19,
      44,   246,    -1,   246,    27,   246,    -1,    19,    27,   246,
      -1,   253,    -1,   252,   253,    -1,   246,    -1,   180,   149,
     246,    -1,   180,    45,   246,    -1,    47,   317,    46,   288,
      -1,    48,    -1,    48,   256,    -1,   257,    -1,   256,    33,
     257,    -1,   245,   258,    -1,   180,    12,   288,    -1,   180,
     149,   246,    12,   288,    -1,   318,    12,   288,    -1,   318,
     149,   246,    12,   288,    -1,     7,    -1,     7,   260,    -1,
     261,    -1,   260,   261,    -1,   245,   180,   265,   268,   269,
     272,    -1,   245,   262,    -1,   180,   149,   249,   180,   263,
      46,   264,   269,   271,   273,    -1,    19,    -1,    17,   319,
      18,    -1,   263,    17,   319,    18,    -1,   288,    -1,    53,
      -1,    54,    -1,    19,    -1,    17,   266,    18,    -1,   319,
     149,   246,    -1,   266,   146,   319,   149,   246,    -1,    -1,
     268,    -1,   180,   149,   246,    -1,   268,   146,   180,   149,
     246,    -1,   270,    -1,    -1,    49,   288,    -1,   272,    -1,
      -1,    50,   288,    -1,    51,   247,    -1,    -1,     8,    -1,
       8,   275,    -1,   276,    -1,   275,   276,    -1,   245,   180,
     265,   267,   279,   269,   272,    -1,   245,   180,   149,   277,
     180,   263,    46,   278,   269,   271,    -1,   246,   102,   246,
      -1,    19,   102,   246,    -1,   246,   102,    19,    -1,    19,
     102,    19,    -1,   225,    -1,    53,    -1,    54,    -1,   280,
      -1,    -1,    55,   281,    -1,   282,    -1,   281,   282,    -1,
     283,   247,    -1,   283,   247,   149,   246,    -1,   282,   146,
     247,    -1,   282,   146,   247,   149,   246,    -1,    56,    -1,
      57,    -1,    11,    -1,    11,   285,    -1,   286,    -1,   285,
     286,    -1,   245,   308,    -1,    47,   288,    -1,   288,    -1,
     287,   146,   288,    -1,   292,    -1,   289,    -1,   291,    -1,
     290,    -1,   293,    -1,   294,    -1,    17,   288,    18,    -1,
     181,    -1,   180,    -1,   180,    98,    -1,   288,    90,   180,
      -1,   288,   104,   180,    -1,   288,    91,    92,    -1,   288,
      19,    -1,   288,    17,   287,    18,    -1,   288,    17,   288,
      86,   288,    18,    -1,   288,    90,   321,    -1,   288,   153,
     180,    -1,   288,   153,   321,    -1,   288,   152,   317,    -1,
     288,   152,   323,    -1,    58,   295,    59,   288,    -1,   297,
      -1,   299,    -1,   302,    -1,   303,    -1,   134,   325,    13,
     288,    -1,   135,   325,    13,   288,    -1,   136,   322,    13,
     288,    -1,    52,   322,    13,   288,    -1,    29,    30,    -1,
      29,   287,    30,    -1,    29,   288,    31,   325,    30,    -1,
      29,   288,    31,   325,    13,   288,    30,    -1,    29,   288,
      86,   288,    30,    -1,    15,    -1,    20,   287,    21,    -1,
      20,   288,    31,   323,    21,    -1,    20,   288,    31,   323,
      13,   288,    21,    -1,   170,    -1,    29,   304,    30,    -1,
      29,   305,    31,   325,    30,    -1,    29,   305,    31,   325,
      13,   288,    30,    -1,    88,    17,   287,    18,    -1,    89,
      17,   287,    18,    -1,    93,   329,    13,   288,    -1,   168,
      17,   288,    18,    -1,    95,   248,    17,   288,    18,    -1,
      95,    17,   288,   146,   246,    18,    -1,    96,    17,   287,
      18,    -1,    97,    17,   247,   146,   288,    18,    -1,   150,
     287,   151,    -1,   150,   288,    31,   325,   151,    -1,   150,
     288,    31,   325,    13,   288,   151,    -1,   183,    -1,   182,
      -1,   184,    -1,   185,    -1,    92,    -1,   187,    -1,   160,
      -1,   137,    -1,   186,    -1,   296,    -1,   295,   146,   296,
      -1,   258,    -1,   262,    -1,    60,   288,    61,   288,   298,
      -1,    63,   288,    -1,    62,   288,    61,   288,   298,    -1,
      64,   288,   149,   300,   173,    -1,    64,   288,   149,   300,
     146,    65,    27,   288,   173,    -1,   301,    -1,   300,   146,
     301,    -1,   319,    27,   288,    -1,    66,   288,    -1,    67,
     288,    -1,    68,   288,    -1,    69,   288,    -1,    70,   288,
      -1,    71,   288,    -1,    72,   288,    -1,    73,   288,    -1,
      74,   288,    -1,    75,   288,    -1,    76,   288,    -1,    77,
     288,    -1,    78,   288,    -1,    79,   288,    -1,    80,   288,
      -1,    81,   288,    -1,    82,   288,    -1,    83,   288,    -1,
      84,   288,    -1,    85,   288,    -1,   288,    66,   288,    -1,
     288,    37,   288,    -1,   288,    67,   288,    -1,   288,   106,
     288,    -1,   288,   105,   288,    -1,   288,   107,   288,    -1,
     288,   108,   288,    -1,   288,   109,   288,    -1,   288,   110,
     288,    -1,   288,   111,   288,    -1,   288,   112,   288,    -1,
     288,    12,   288,    -1,   288,   113,   288,    -1,   288,   114,
     288,    -1,   288,   115,   288,    -1,   288,   116,   288,    -1,
     288,   117,   288,    -1,   288,   118,   288,    -1,   288,   119,
     288,    -1,   288,   120,   288,    -1,   288,   121,   288,    -1,
     288,   122,   288,    -1,   288,   123,   288,    -1,   288,   124,
     288,    -1,   288,   125,   288,    -1,   288,   126,   288,    -1,
     288,   127,   288,    -1,   288,   128,   288,    -1,   288,   129,
     288,    -1,   288,   130,   288,    -1,   288,   131,   288,    -1,
     288,   132,   288,    -1,   288,   133,   288,    -1,   305,    -1,
     304,   146,   305,    -1,   288,    87,   288,    -1,   288,    27,
     225,    -1,   306,    31,   288,    27,   225,    -1,   308,    -1,
     307,   146,   308,    -1,   180,   149,   246,    -1,   180,   149,
     246,   100,   288,    -1,   180,   149,   246,    59,   288,    -1,
     310,    -1,   309,    33,   310,    -1,   247,   100,   288,    -1,
      60,   288,    61,   225,   312,    63,   225,    -1,    60,   288,
      61,   225,    63,   225,    -1,    62,   288,    61,   225,    -1,
     312,    62,   288,    61,   225,    -1,    64,   288,   149,   314,
     173,    -1,    64,   288,   149,   314,   146,    65,    27,   225,
     173,    -1,   315,    -1,   314,   146,   315,    -1,   319,    27,
     225,    -1,   279,   269,   272,    -1,   320,    -1,   318,    -1,
     321,    -1,    88,    17,   319,   146,   317,    18,    -1,    89,
      19,    -1,    89,    17,   319,    18,    -1,   317,    -1,   319,
     146,   317,    -1,   180,    -1,    67,    -1,   292,    -1,   289,
      -1,   291,    -1,   290,    -1,   293,    -1,   294,    -1,    17,
     288,    18,    -1,   323,    -1,   324,    -1,   317,   118,   288,
      -1,   317,   149,   246,    -1,   326,    -1,   325,   146,   326,
      -1,   327,    -1,   328,    -1,   319,   118,   288,    -1,   319,
     149,   246,    -1,   324,    -1,   329,   146,   324,    -1
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
     212,   214,   217,   219,   222,   226,   231,   235,   240,   242,
     246,   248,   250,   252,   254,   257,   261,   267,   273,   277,
     281,   285,   289,   295,   299,   305,   309,   313,   317,   320,
     327,   335,   339,   347,   351,   363,   371,   381,   387,   393,
     402,   411,   416,   421,   426,   433,   441,   451,   461,   469,
     474,   481,   483,   487,   491,   493,   498,   501,   506,   510,
     513,   518,   523,   525,   532,   539,   547,   556,   567,   572,
     574,   578,   580,   584,   587,   590,   593,   597,   605,   615,
     619,   625,   627,   630,   632,   635,   637,   639,   641,   644,
     648,   652,   654,   657,   659,   662,   666,   668,   670,   672,
     674,   677,   679,   681,   683,   685,   687,   690,   692,   695,
     699,   701,   705,   710,   716,   721,   727,   729,   731,   733,
     735,   736,   740,   742,   744,   750,   754,   758,   762,   765,
     768,   771,   776,   781,   783,   785,   787,   791,   795,   797,
     799,   801,   803,   805,   807,   809,   811,   813,   815,   819,
     823,   827,   831,   833,   836,   838,   842,   846,   851,   853,
     856,   858,   862,   865,   869,   875,   879,   885,   887,   890,
     892,   895,   902,   905,   916,   918,   922,   927,   929,   931,
     933,   935,   939,   943,   949,   950,   952,   956,   962,   964,
     965,   968,   970,   971,   974,   977,   978,   980,   983,   985,
     988,   996,  1007,  1011,  1015,  1019,  1023,  1025,  1027,  1029,
    1031,  1032,  1035,  1037,  1040,  1043,  1048,  1052,  1058,  1060,
    1062,  1064,  1067,  1069,  1072,  1075,  1078,  1080,  1084,  1086,
    1088,  1090,  1092,  1094,  1096,  1100,  1102,  1104,  1107,  1111,
    1115,  1119,  1122,  1127,  1134,  1138,  1142,  1146,  1150,  1154,
    1159,  1161,  1163,  1165,  1167,  1172,  1177,  1182,  1187,  1190,
    1194,  1200,  1208,  1214,  1216,  1220,  1226,  1234,  1236,  1240,
    1246,  1254,  1259,  1264,  1269,  1274,  1280,  1287,  1292,  1299,
    1303,  1309,  1317,  1319,  1321,  1323,  1325,  1327,  1329,  1331,
    1333,  1335,  1337,  1341,  1343,  1345,  1351,  1354,  1360,  1366,
    1376,  1378,  1382,  1386,  1389,  1392,  1395,  1398,  1401,  1404,
    1407,  1410,  1413,  1416,  1419,  1422,  1425,  1428,  1431,  1434,
    1437,  1440,  1443,  1446,  1450,  1454,  1458,  1462,  1466,  1470,
    1474,  1478,  1482,  1486,  1490,  1494,  1498,  1502,  1506,  1510,
    1514,  1518,  1522,  1526,  1530,  1534,  1538,  1542,  1546,  1550,
    1554,  1558,  1562,  1566,  1570,  1574,  1578,  1580,  1584,  1588,
    1592,  1598,  1600,  1604,  1608,  1614,  1620,  1622,  1626,  1630,
    1638,  1645,  1650,  1656,  1662,  1672,  1674,  1678,  1682,  1686,
    1688,  1690,  1692,  1699,  1702,  1707,  1709,  1713,  1715,  1717,
    1719,  1721,  1723,  1725,  1727,  1729,  1733,  1735,  1737,  1741,
    1745,  1747,  1751,  1753,  1755,  1759,  1763,  1765
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   313,   313,   321,   327,   338,   339,   340,   341,   342,
     346,   357,   390,   418,   452,   459,   467,   473,   479,   488,
     497,   503,   509,   515,   530,   537,   552,   570,   577,   584,
     594,   608,   617,   627,   637,   647,   655,   663,   674,   687,
     697,   705,   716,   722,   728,   734,   751,   764,   776,   782,
     798,   802,   809,   813,   820,   837,   863,   867,   874,   878,
     885,   890,   895,   900,   905,   911,   928,   952,   959,   966,
     973,   980,   995,  1002,  1017,  1033,  1040,  1047,  1054,  1066,
    1080,  1091,  1105,  1116,  1130,  1147,  1164,  1180,  1199,  1206,
    1217,  1224,  1232,  1240,  1251,  1264,  1277,  1291,  1304,  1316,
    1325,  1335,  1339,  1347,  1367,  1371,  1378,  1382,  1386,  1397,
    1401,  1418,  1455,  1465,  1481,  1497,  1506,  1517,  1528,  1543,
    1549,  1558,  1564,  1573,  1579,  1585,  1594,  1600,  1613,  1638,
    1649,  1669,  1684,  1705,  1711,  1728,  1749,  1770,  1776,  1785,
    1798,  1808,  1824,  1844,  1850,  1862,  1878,  1882,  1886,  1893,
    1899,  1908,  1912,  1916,  1920,  1924,  1934,  1948,  1958,  1970,
    1985,  1991,  2000,  2020,  2041,  2070,  2117,  2124,  2131,  2138,
    2146,  2155,  2162,  2166,  2171,  2183,  2217,  2245,  2252,  2259,
    2266,  2273,  2281,  2289,  2301,  2321,  2327,  2333,  2342,  2346,
    2350,  2354,  2358,  2362,  2366,  2370,  2377,  2381,  2388,  2397,
    2409,  2418,  2430,  2436,  2445,  2453,  2463,  2476,  2504,  2516,
    2546,  2552,  2562,  2572,  2596,  2622,  2642,  2667,  2681,  2710,
    2716,  2725,  2749,  2759,  2781,  2785,  2792,  2802,  2806,  2810,
    2817,  2821,  2828,  2835,  2846,  2849,  2856,  2871,  2889,  2890,
    2894,  2898,  2899,  2903,  2913,  2921,  2927,  2940,  2961,  2967,
    2976,  3003,  3027,  3039,  3051,  3063,  3077,  3081,  3085,  3092,
    3093,  3097,  3101,  3107,  3127,  3136,  3145,  3154,  3166,  3170,
    3199,  3205,  3221,  3229,  3238,  3244,  3258,  3264,  3307,  3319,
    3324,  3328,  3333,  3345,  3351,  3355,  3360,  3365,  3370,  3374,
    3378,  3384,  3390,  3397,  3404,  3409,  3413,  3417,  3424,  3429,
    3436,  3440,  3444,  3448,  3453,  3461,  3469,  3477,  3486,  3493,
    3500,  3507,  3516,  3524,  3540,  3547,  3554,  3563,  3568,  3575,
    3582,  3591,  3598,  3612,  3627,  3637,  3647,  3661,  3678,  3691,
    3701,  3712,  3727,  3731,  3738,  3749,  3756,  3762,  3769,  3783,
    3792,  3803,  3809,  3818,  3819,  3823,  3855,  3862,  3876,  3883,
    3894,  3900,  3911,  3931,  3937,  3943,  3949,  3955,  3961,  3967,
    3973,  3979,  3985,  3991,  3997,  4003,  4009,  4015,  4021,  4027,
    4033,  4039,  4045,  4054,  4062,  4070,  4078,  4086,  4094,  4102,
    4110,  4118,  4126,  4134,  4142,  4150,  4158,  4166,  4174,  4182,
    4190,  4198,  4206,  4214,  4222,  4230,  4238,  4246,  4254,  4262,
    4270,  4278,  4286,  4294,  4302,  4310,  4321,  4327,  4335,  4347,
    4360,  4376,  4382,  4391,  4408,  4424,  4447,  4454,  4472,  4487,
    4496,  4508,  4519,  4533,  4540,  4551,  4557,  4568,  4580,  4598,
    4599,  4603,  4607,  4624,  4635,  4649,  4655,  4664,  4678,  4688,
    4698,  4703,  4708,  4713,  4718,  4723,  4734,  4735,  4739,  4750,
    4761,  4767,  4776,  4777,  4781,  4792,  4803,  4810
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

  private static final int yylast_ = 11762;
  private static final int yynnts_ = 119;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 41;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 211;

  private static final int yyuser_token_number_max_ = 465;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 922 of cmlskeleton.java.m4  */
/* Line 53 of "src/main/bison/cml.y"  */

  // **************************
  // *** PARSER PUBLIC INFO ***
  // **************************
  public static class Info {
    public static final String CML_LANG_VERSION = "CML 0";
  };

  // **************************
  // *** PARSER INTERNAL DS ***
  // **************************
  class PatternWithVDMType {
    public PType type;
    public PPattern pattern;
  }

  // ************************
  // *** MEMBER VARIABLES ***
  // ************************

  private PSource currentSource = null;
  private ParserUtil util = null;

  // *************************
  // *** PRIVATE OPERATIONS ***
  // *************************

  public static char convertEscapeToChar(String escape)
  {
    if (escape.startsWith("\\")) {
      switch(escape.charAt(1)) {
      case 'n':  return '\n';
      case '\\': return '\\';
      case 'r': return '\r';
      case 't': return '\t';
      case 'f': return '\f';
      case 'e': return (char)0x001B;
      case 'a': return (char)0x0007;
      case 'x': return (char)new BigInteger(escape.substring(2), 16).intValue();
      case 'u': return (char)new BigInteger(escape.substring(2), 16).intValue();
      case 'c': return (char)(escape.charAt(2) - 'A' + 1);
      case '0': return (char)new BigInteger(escape.substring(2), 8).intValue();
      case '\"' : return '\"';
      case '\'': return '\'';
      default:
        throw new RuntimeException("Illegal escape sequence: "+escape);
      }
    }
    return escape.charAt(0);
  }

  // *************************
  // *** PUBLIC OPERATIONS ***
  // *************************

    public static void main(String[] args) throws Exception
  {
    if (args.length == 0) {
      System.out.println("Usage : java CmlParser <inputfile>");
    } else {
      CmlLexer scanner = null;
      try {
        String filePath = args[0];
        ClonableFile file = new ClonableFile(filePath);
        AFileSource fileSource = new AFileSource();
        fileSource.setName(file.getName());
        scanner = new CmlLexer( new java.io.FileReader(file) );
        CmlParser cmlParser = new CmlParser(scanner);
        cmlParser.setDocument(fileSource);
        //cmlParser.setDebugLevel(1);

        //do {
        //System.out.println(scanner.yylex());
        boolean result = cmlParser.parse();
        if (result){
          System.out.println("parsed!");

          DotGraphVisitor dgv = new DotGraphVisitor();
          INode node = cmlParser.getDocument();

          node.apply(dgv,null);

          File dotFile = new File("generatedAST.gv");
          java.io.FileWriter fw = new java.io.FileWriter(dotFile);
          fw.write(dgv.getResultString());
          fw.close();

          //System.out.println(dgv.getResultString());

        } else {
          System.out.println("Not parsed!");
          System.exit(-1);
        }

        //} while (!scanner.zzAtEOF);

      } catch (java.io.FileNotFoundException e) {
        System.out.println("File not found : \""+args[0]+"\"");
        System.exit(-2);
      } catch (java.io.IOException e) {
        System.out.println("IO error scanning file \""+args[0]+"\"");
        System.out.println(e);
        System.exit(-3);
      } catch (Exception e) {
        System.out.println("Unexpected exception:");
        e.printStackTrace();
        System.exit(-4);
      }
    }
  }

  public void setDocument(PSource doc)
  {
    this.currentSource = doc;
    this.util = new ParserUtil(this.currentSource);
  }

  public PSource getDocument()
  {
    return currentSource;
  }



/* Line 922 of cmlskeleton.java.m4  */
/* Line 9219 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


