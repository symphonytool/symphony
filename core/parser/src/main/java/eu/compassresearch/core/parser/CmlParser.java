
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




/* Line 40 of cmlskeleton.java.m4  */
/* Line 94 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

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
/* Line 559 of "src/main/bison/cml.y"  */
    {
  List<SParagraphDefinition> paragraphs = (List<SParagraphDefinition>) ((yystack.valueAt (1-(1))));
  currentSource.setParagraphs(paragraphs);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 567 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> programParagraphList = new LinkedList<PDefinition>();
  programParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = programParagraphList;
};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 573 of "src/main/bison/cml.y"  */
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
/* Line 583 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 584 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 585 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 586 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 587 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 592 of "src/main/bison/cml.y"  */
    {
  AClassParagraphDefinition clz = new AClassParagraphDefinition();
  LexNameToken lexName = extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),(CmlLexeme)((yystack.valueAt (6-(6)))));
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
/* Line 606 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),(CmlLexeme)((yystack.valueAt (8-(8)))));
  List<LexNameToken> supernames = new LinkedList<LexNameToken>();
  supernames.add(extractLexNameToken(((yystack.valueAt (8-(5))))));
  yyval = new AClassParagraphDefinition(location,
                                     extractLexNameToken(((yystack.valueAt (8-(2))))),
                                     NameScope.CLASSNAME,
                                     false,
                                     null,//ClassDefinition
                                     getDefaultAccessSpecifier(false,false,null),
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
/* Line 639 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, process.getLocation());
  AProcessDefinition processDef = new AProcessDefinition(process.getLocation(),
                                                         NameScope.GLOBAL,
                                                         false,
                                                         access,
                                                         null,//Pass
                                                         null,
                                                         process);
  LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  processDef.setName(id);
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                            processDef.getLocation());
  access = getDefaultAccessSpecifier(true, false, location);
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
/* Line 667 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (6-(4))));
  PProcess process = (PProcess)((yystack.valueAt (6-(6))));
  LexLocation loc = combineLexLocation(extractFirstLexLocation(decls),
                                       process.getLocation());
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  AProcessDefinition processDef = new AProcessDefinition(loc,
                                                         NameScope.GLOBAL,
                                                         false,
                                                         access,
                                                         null,//Pass
                                                         decls,
                                                         process);
  LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));
  processDef.setName(id);
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
                                            processDef.getLocation());
  access = getDefaultAccessSpecifier(true, false, location);
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
/* Line 701 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  List<SParagraphDefinition> processParagraphs = null;
  PAction action = (PAction)((yystack.valueAt (4-(3))));
  yyval = new AStateProcess(location, processParagraphs, action);
};
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 708 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  List<SParagraphDefinition> processParagraphs = (List<SParagraphDefinition>)((yystack.valueAt (5-(2))));
  PAction action = (PAction)((yystack.valueAt (5-(4))));
  yyval = new AStateProcess(location, processParagraphs, action);
};
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 716 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASequentialCompositionProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 722 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AExternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 728 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 737 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  yyval = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 746 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (7-(1))));
  PProcess right = (PProcess)((yystack.valueAt (7-(7))));
  yyval = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (7-(3)))), (PExp)((yystack.valueAt (7-(5)))), right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 752 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 758 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 764 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInterruptProcess(location, left, right);
};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 779 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimedInterruptProcess(location, left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 786 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AUntimedTimeoutProcess(location, left, right);
};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 801 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimeoutProcess(location, left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 819 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PExp cse = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), cse.getLocation());
  yyval = new AHidingProcess(location, left, cse);
};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 826 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AStartDeadlineProcess(location, left, exp);
};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 833 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AEndDeadlineProcess(location, left, exp);
};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 843 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8)))));
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
/* Line 857 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken(((yystack.valueAt (1-(1)))));
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
/* Line 866 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken(((yystack.valueAt (2-(1)))));
  LexLocation location = extractLexLocation(name.location,(CmlLexeme)((yystack.valueAt (2-(2)))));
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
/* Line 876 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken(((yystack.valueAt (4-(1)))));
  LexLocation location = extractLexLocation(name.location,(CmlLexeme)((yystack.valueAt (4-(4)))));
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
/* Line 886 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PProcess process = (PProcess)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingProcess(combineLexLocation(process.getLocation(),
                                                      renameExpression.getLocation()),
                                   process,
                                   renameExpression);
};
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 896 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new ASequentialCompositionReplicatedProcess(location,
                                                   (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                                   process);
};
  break;
    

  case 36:
  if (yyn == 36)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 904 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new AExternalChoiceReplicatedProcess(location,
                                            (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                            process);
};
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 912 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new AInternalChoiceReplicatedProcess(location,
                                            (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                            process);
};
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 923 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (6-(6))));
  PExp chansetExp = (PExp)((yystack.valueAt (6-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),process.getLocation());
  yyval = new AGeneralisedParallelismReplicatedProcess(location,
                                                    (List<SSingleDeclaration>)((yystack.valueAt (6-(4)))),
                                                    process,
                                                    chansetExp);
};
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 936 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (7-(7))));
  PExp chansetExp = (PExp)((yystack.valueAt (7-(5))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),process.getLocation());
  yyval = new AAlphabetisedParallelismReplicatedProcess(location,
                                                     (List<SSingleDeclaration>)((yystack.valueAt (7-(2)))),
                                                     process,
                                                     chansetExp);
};
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 946 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new ASynchronousParallelismReplicatedProcess(location,
                                                    (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                                    process);
};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 954 of "src/main/bison/cml.y"  */
    {
  PProcess process = (PProcess)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),process.getLocation());
  yyval = new AInterleavingReplicatedProcess(location,
                                          (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                          process);
};
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 965 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 971 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 977 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 983 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1000 of "src/main/bison/cml.y"  */
    {
  LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  List<LexNameToken> ids = new Vector<LexNameToken>();
  ids.add(id);
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation(id.getLocation(),
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
/* Line 1013 of "src/main/bison/cml.y"  */
    {
  LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  AExpressionSingleDeclaration decls = (AExpressionSingleDeclaration)((yystack.valueAt (3-(3))));
  decls.getIdentifiers().add(0, id);
  decls.setLocation(extractLexLocation(id.getLocation(),
                                       decls.getLocation()));
  yyval = decls;
};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1025 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = processParagraphList;
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1031 of "src/main/bison/cml.y"  */
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
/* Line 1047 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1051 of "src/main/bison/cml.y"  */
    {
  List<AActionDefinition> actionDefinitions = (List<AActionDefinition>)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), extractLastLexLocation(actionDefinitions));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  yyval = new AActionParagraphDefinition( loc, NameScope.LOCAL, false, access,null/*Pass*/, actionDefinitions);
};
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1058 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1062 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1069 of "src/main/bison/cml.y"  */
    {
  Object[] pa = (Object[])((yystack.valueAt (3-(3))));
  List<ATypeSingleDeclaration> declarations = (List<ATypeSingleDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), action.getLocation());
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
/* Line 1086 of "src/main/bison/cml.y"  */
    {
  List<AActionDefinition> actionDefs = (List<AActionDefinition>)((yystack.valueAt (4-(1))));
  Object[] pa = (Object[])((yystack.valueAt (4-(4))));
  List<ATypeSingleDeclaration> declarations = (List<ATypeSingleDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(2))))), action.getLocation());
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
/* Line 1112 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1116 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1123 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{new Vector<ATypeSingleDeclaration>(), ((yystack.valueAt (1-(1))))};
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1127 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3))))};
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1134 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASkipAction(location);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1139 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AStopAction(location);
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1144 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AChaosAction(location);
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1149 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ADivAction(location);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1154 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
  yyval = new AWaitAction(location, exp);
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1160 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  PAction action = (PAction)((yystack.valueAt (3-(2))));
  yyval = new ABlockStatementAction(location, null, action);
};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1177 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* need to merge in old action rule RARROW parser action and the old communication rule actions; along with an expression -> communication conversion?
   */
  //ACommunicationAction comAction = (ACommunicationAction)$communication;
  //PAction to = (PAction)$to;
  //LexLocation location = extractLexLocation(comAction.getLocation(), to.getLocation());
  // comAction.setAction(to);
  // comAction.setLocation(location);
  // $$ = comAction;
};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1200 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  PAction action = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(exp.getLocation(), action.getLocation());
  yyval = new AGuardedAction(location, exp, action);
};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1207 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ASequentialCompositionAction(location, left, right);
};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1214 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AExternalChoiceAction(location, left, right);
};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1221 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInternalChoiceAction(location, left, right);
};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1228 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AInterruptAction(location, left, right);
};
  break;
    

  case 72:
  if (yyn == 72)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1243 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (5-(1))));
  PAction right = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimedInterruptAction(location, left, right, (PExp)((yystack.valueAt (5-(3)))));
};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1250 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PAction right = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new AUntimedTimeoutAction(location, left, right);
};
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1265 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (5-(1))));
  PAction right = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  yyval = new ATimeoutAction(location, left, right, (PExp)((yystack.valueAt (5-(3)))));
};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1281 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PExp chansetExp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), chansetExp.getLocation());
  yyval = new AHidingAction(location, left, chansetExp);
};
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1288 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AStartDeadlineAction(location, left, exp);
};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1295 of "src/main/bison/cml.y"  */
    {
  PAction left = (PAction)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  yyval = new AEndDeadlineAction(location, left, exp);
};
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1302 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PAction action = (PAction)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingAction(combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1314 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* that expressionList needs to be converted down into a nameList?
   */
  List<LexNameToken> nameList = (List<LexNameToken>)((yystack.valueAt (6-(2))));
  List<LexIdentifierToken> ids = convertNameListToIdentifierList(nameList);
  yyval = new AMuAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),(CmlLexeme)((yystack.valueAt (6-(6))))),
                     ids,
                     (List<PAction>)((yystack.valueAt (6-(5)))));
};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1328 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (7-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (7-(7))));
  yyval = new AInterleavingParallelAction(extractLexLocation(leftAction.getLocation(),
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
/* Line 1339 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (3-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingParallelAction(extractLexLocation(leftAction.getLocation(),
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
/* Line 1353 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (7-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (7-(7))));
  yyval = new ASynchronousParallelismParallelAction(extractLexLocation(leftAction.getLocation(),
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
/* Line 1364 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (3-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismParallelAction(extractLexLocation(leftAction.getLocation(),
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
/* Line 1378 of "src/main/bison/cml.y"  */
    {
    PAction leftAction = (PAction)((yystack.valueAt (11-(1))));
    PAction rightAction = (PAction)((yystack.valueAt (11-(11))));
    LexLocation location = extractLexLocation(leftAction.getLocation(),
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
/* Line 1395 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (7-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = extractLexLocation(leftAction.getLocation(),
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
/* Line 1412 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (9-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (9-(9))));
  LexLocation location = extractLexLocation(leftAction.getLocation(),
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
/* Line 1428 of "src/main/bison/cml.y"  */
    {
  PAction leftAction = (PAction)((yystack.valueAt (5-(1))));
  PAction rightAction = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = extractLexLocation(leftAction.getLocation(),
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
/* Line 1447 of "src/main/bison/cml.y"  */
    {
  yyval = new AParametrisedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),
                                                  (CmlLexeme)((yystack.valueAt (5-(5))))),
                               (List<PParametrisation>)((yystack.valueAt (5-(2)))),
                               (PAction)((yystack.valueAt (5-(4)))));
};
  break;
    

  case 89:
  if (yyn == 89)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1454 of "src/main/bison/cml.y"  */
    {
  AParametrisedAction action = new AParametrisedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),
                                                                          (CmlLexeme)((yystack.valueAt (8-(5))))),
                                                       (List<PParametrisation>)((yystack.valueAt (8-(2)))),
                                                       (PAction)((yystack.valueAt (8-(4)))));
  yyval = new AParametrisedInstantiatedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),
                                                              (CmlLexeme)((yystack.valueAt (8-(8))))),
                                           action,
                                           (List<PExp>)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 90:
  if (yyn == 90)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1465 of "src/main/bison/cml.y"  */
    {
  yyval = new ADeclarationInstantiatedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8))))),
                                          (List<? extends ATypeSingleDeclaration>)((yystack.valueAt (8-(2)))),
                                          (PAction)((yystack.valueAt (8-(4)))),
                                          (List<PExp>)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 91:
  if (yyn == 91)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1472 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new ASequentialCompositionReplicatedAction(location,
                                                  (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                                  action);
};
  break;
    

  case 92:
  if (yyn == 92)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1480 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AExternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                           action);
};
  break;
    

  case 93:
  if (yyn == 93)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1488 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AInternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                           action);
};
  break;
    

  case 94:
  if (yyn == 94)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1499 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (6-(6))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
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
/* Line 1512 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
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
/* Line 1525 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (9-(9))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))),
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
/* Line 1539 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (9-(9))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))),replicatedAction.getLocation());
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
/* Line 1552 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),replicatedAction.getLocation());
  yyval = new ASynchronousParallelismReplicatedAction(location,
                                                   (List<? extends SSingleDeclaration>)((yystack.valueAt (7-(2)))),
                                                   replicatedAction,
                                                   (PExp)((yystack.valueAt (7-(5)))));
};
  break;
    

  case 99:
  if (yyn == 99)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1564 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                            action.getLocation());
  yyval = new ALetStatementAction(location,
                               action,
                               (List<? extends PDefinition>)((yystack.valueAt (4-(2)))));
};
  break;
    

  case 100:
  if (yyn == 100)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1573 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))), (CmlLexeme)((yystack.valueAt (6-(6)))));
  ADeclareStatementAction dclStm =
    new ADeclareStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(2)))),
                                                   (CmlLexeme)((yystack.valueAt (6-(4))))),
                                (List<? extends PDefinition>)((yystack.valueAt (6-(3)))));
  PAction action = (PAction)((yystack.valueAt (6-(5))));
  yyval = new ABlockStatementAction(location, dclStm, action);
};
  break;
    

  case 101:
  if (yyn == 101)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1583 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 102:
  if (yyn == 102)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1587 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)((yystack.valueAt (3-(2))));
  yyval = new ANonDeterministicIfStatementAction(location,
                                              alternatives);
};
  break;
    

  case 103:
  if (yyn == 103)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1595 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)((yystack.valueAt (3-(2))));
  yyval = new ANonDeterministicDoStatementAction(location,
                                              alternatives);
};
  break;
    

  case 104:
  if (yyn == 104)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1615 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 105:
  if (yyn == 105)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1619 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AMultipleGeneralAssignmentStatementAction(location,
                                                     (List<? extends ASingleGeneralAssignmentStatementAction>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1626 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1630 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 108:
  if (yyn == 108)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1634 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 109:
  if (yyn == 109)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1645 of "src/main/bison/cml.y"  */
    {
  yyval = new AReturnStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), null);
};
  break;
    

  case 110:
  if (yyn == 110)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1649 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (4-(3))));
  yyval = new AReturnStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                                     exp.getLocation()),
                                  exp);
};
  break;
    

  case 111:
  if (yyn == 111)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1666 of "src/main/bison/cml.y"  */
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
  LexLocation location = combineLexLocation(target.getLocation(),newExp.getLocation());
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
/* Line 1703 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 113:
  if (yyn == 113)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1713 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (6-(6))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),action.getLocation());
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
/* Line 1729 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (6-(6))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),action.getLocation());
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
/* Line 1745 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),action.getLocation());
  yyval = new AForSetStatementAction(location,
                                  (PPattern)((yystack.valueAt (7-(3)))),
                                  (PExp)((yystack.valueAt (7-(5)))),
                                  action);
};
  break;
    

  case 116:
  if (yyn == 116)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1754 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (8-(8))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),action.getLocation());
  yyval = new AForIndexStatementAction(location,
                                    extractLexNameToken(((yystack.valueAt (8-(2))))),
                                    (PExp)((yystack.valueAt (8-(4)))) ,
                                    (PExp)((yystack.valueAt (8-(6)))),
                                    null,
                                    action);
};
  break;
    

  case 117:
  if (yyn == 117)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1765 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (10-(10))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (10-(1)))),action.getLocation());
  yyval = new AForIndexStatementAction(location,
                                    extractLexNameToken(((yystack.valueAt (10-(2))))),
                                    (PExp)((yystack.valueAt (10-(4)))) ,
                                    (PExp)((yystack.valueAt (10-(6)))),
                                    (PExp)((yystack.valueAt (10-(8)))),
                                    action);
};
  break;
    

  case 118:
  if (yyn == 118)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1776 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AWhileStatementAction(location,
                                 (PExp)((yystack.valueAt (4-(2)))),
                                 action);
};
  break;
    

  case 119:
  if (yyn == 119)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1791 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = new LinkedList<PAction>();
  actionList.add((PAction)((yystack.valueAt (1-(1)))));
  yyval = actionList;
};
  break;
    

  case 120:
  if (yyn == 120)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1797 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = (List<PAction>)((yystack.valueAt (3-(1))));
  actionList.add((PAction)((yystack.valueAt (3-(3)))));
  yyval = actionList;
};
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1806 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add((PParametrisation)((yystack.valueAt (1-(1)))));
  yyval = plist;
};
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1812 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add(0,(PParametrisation)((yystack.valueAt (3-(3)))));
  yyval = plist;
};
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1821 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AValParametrisation(loc, decl);
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1827 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AResParametrisation(loc, decl);
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1833 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AVresParametrisation(loc, decl);
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1842 of "src/main/bison/cml.y"  */
    {
  yyval = new AEnumerationRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
                                                           (CmlLexeme)((yystack.valueAt (3-(3))))),
                                        (List<? extends ARenamePair>)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1848 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* path elimination */
  ARenamePair pair = new ARenamePair(false,
                                     (ANameChannelExp)((yystack.valueAt (7-(2)))),
                                     (ANameChannelExp)((yystack.valueAt (7-(4)))));
  yyval = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
                                                             (CmlLexeme)((yystack.valueAt (7-(7))))),
                                          pair,
                                          (List<? extends PMultipleBind>)((yystack.valueAt (7-(6)))),
                                          null);
};
  break;
    

  case 128:
  if (yyn == 128)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1861 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* path elimination */
  ARenamePair pair = new ARenamePair(false,
                                     (ANameChannelExp)((yystack.valueAt (9-(2)))),
                                     (ANameChannelExp)((yystack.valueAt (9-(4)))));
  yyval = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))),
                                                             (CmlLexeme)((yystack.valueAt (9-(9))))),
                                          pair,
                                          (List<? extends PMultipleBind>)((yystack.valueAt (9-(6)))),
                                          (PExp)((yystack.valueAt (9-(8)))));
};
  break;
    

  case 129:
  if (yyn == 129)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1886 of "src/main/bison/cml.y"  */
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
/* Line 1897 of "src/main/bison/cml.y"  */
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
/* Line 1917 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> chanNameDecls = new Vector<AChannelNameDeclaration>();
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
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
/* Line 1932 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> chanNameDecls = (List<AChannelNameDeclaration>)((yystack.valueAt (2-(2))));
  LexLocation start = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  LexLocation end = (chanNameDecls != null && chanNameDecls.size() > 0) ?
    chanNameDecls.get(chanNameDecls.size()-1).getLocation() : start;
  LexLocation location = combineLexLocation(start, end);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, start);
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
/* Line 1953 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
  decls.add((AChannelNameDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 134:
  if (yyn == 134)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1959 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((AChannelNameDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1976 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* I (jwc) have no idea if this is correct.  When did
   * AChannelNameDefinition become AChannelNameDeclaration?
   */
  LexIdentifierToken decl = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  LexLocation location = decl.getLocation();
  List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
  ids.add(decl);
  ATypeSingleDeclaration singleTypeDeclaration =
    new ATypeSingleDeclaration(location, NameScope.GLOBAL, ids, null);
  AChannelNameDeclaration channelNameDecl =
    new AChannelNameDeclaration(location,
                                NameScope.GLOBAL,
                                /* false, */
                                /* null, */
                                /* null, */
                                singleTypeDeclaration);
  yyval = channelNameDecl;
};
  break;
    

  case 136:
  if (yyn == 136)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1997 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* I (jwc) have no idea if this is correct.  When did
   * AChannelNameDefinition become AChannelNameDeclaration?
   */
  ATypeSingleDeclaration singleTypeDeclaration = (ATypeSingleDeclaration)((yystack.valueAt (1-(1))));
  AChannelNameDeclaration channelNameDecl =
    new AChannelNameDeclaration(singleTypeDeclaration.getLocation(),
                               NameScope.GLOBAL,
                               /* false, */
                               /* null, */
                               /* null, */
                               singleTypeDeclaration);
  yyval = channelNameDecl;
};
  break;
    

  case 137:
  if (yyn == 137)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2018 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = new Vector<ATypeSingleDeclaration>();
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 138:
  if (yyn == 138)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2024 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2033 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
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
/* Line 2046 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (3-(3))));
  decl.setLocation(combineLexLocation(id.getLocation(), decl.getLocation()));
  decl.getIdentifiers().addFirst(id);
  yyval = decl;
};
  break;
    

  case 141:
  if (yyn == 141)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2056 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
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
/* Line 2072 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
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
/* Line 2092 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 144:
  if (yyn == 144)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2098 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(1))));
  defs.add((AChansetDefinition)((yystack.valueAt (2-(2)))));
  yyval = defs;
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2110 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = combineLexLocation(id.getLocation(), exp.getLocation());
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
/* Line 2126 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 147:
  if (yyn == 147)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2130 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2134 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2141 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2147 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  defs.add((PDefinition)((yystack.valueAt (2-(1)))));
  yyval = defs;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2156 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 152:
  if (yyn == 152)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2160 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 153:
  if (yyn == 153)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2164 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2168 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2172 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2182 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (2-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),def.getLocation());
  yyval = new AInitialParagraphDefinition(location,
                                       NameScope.GLOBAL,
                                       true,
                                       getDefaultAccessSpecifier(false,false,null),
                                       null/*Pass*/,
                                       def);
};
  break;
    

  case 157:
  if (yyn == 157)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2196 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     null);
};
  break;
    

  case 158:
  if (yyn == 158)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2206 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       extractLastLexLocation(typeDefinitions));
  yyval = new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     typeDefinitions);
};
  break;
    

  case 159:
  if (yyn == 159)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2218 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)((yystack.valueAt (3-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))),
                                       extractLexLocation((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     typeDefinitions);
};
  break;
    

  case 160:
  if (yyn == 160)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2233 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
  yyval = list;
};
  break;
    

  case 161:
  if (yyn == 161)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2239 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
  list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
  yyval = list;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2248 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  LexLocation location = null;
  location = combineLexLocation(name.getLocation(), ((PType)((yystack.valueAt (4-(4))))).getLocation());
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
/* Line 2268 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2)))));
  AInvariantDefinition inv = (AInvariantDefinition)((yystack.valueAt (5-(5))));
  LexLocation location = null;
  location = combineLexLocation(name.getLocation(), inv.getLocation());
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
/* Line 2289 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (4-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(name.getLocation(), extractLexLocation(vdmrec));
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
/* Line 2318 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* Not sure why, but we're not actually using the invariant
   * definition here.
   */
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2)))));
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (5-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (5-(4))));
  AInvariantDefinition inv = (AInvariantDefinition)((yystack.valueAt (5-(5))));
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.
  LexLocation loc = combineLexLocation(name.getLocation(), extractLexLocation(vdmrec));
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
/* Line 2365 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 167:
  if (yyn == 167)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2372 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new AProtectedAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 168:
  if (yyn == 168)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2379 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 169:
  if (yyn == 169)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2386 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 170:
  if (yyn == 170)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2393 of "src/main/bison/cml.y"  */
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
/* Line 2403 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (3-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  type.setLocation(loc);
  yyval = type;
};
  break;
    

  case 172:
  if (yyn == 172)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2410 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 173:
  if (yyn == 173)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2414 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken quote = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteType(quote.location, false, null, quote);
};
  break;
    

  case 174:
  if (yyn == 174)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2419 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (5-(4))));
  yyval = new ARecordInvariantType(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5))))),
                                false,
                                null,//definitions
                                false,//opaque
                                null,//invdef
                                extractLexNameToken(((yystack.valueAt (5-(2))))),
                                (List<? extends AFieldField>)((yystack.valueAt (5-(4)))),
                                false/*infinite_*/);
};
  break;
    

  case 175:
  if (yyn == 175)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2431 of "src/main/bison/cml.y"  */
    {
  PType first = (PType)((yystack.valueAt (3-(1))));
  PType second = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(first.getLocation(), second.getLocation());
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
/* Line 2465 of "src/main/bison/cml.y"  */
    {
  PType first = (PType)((yystack.valueAt (3-(1))));
  PType second = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(first.getLocation(), second.getLocation());
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
/* Line 2493 of "src/main/bison/cml.y"  */
    {
  yyval = new AOptionalType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))),
                         false,
                         null,
                         (PType)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 178:
  if (yyn == 178)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2500 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASetType(loc, false, null, type, false, false);
};
  break;
    

  case 179:
  if (yyn == 179)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2507 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASeqSeqType(loc, false, null, type, false);
};
  break;
    

  case 180:
  if (yyn == 180)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2514 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASeq1SeqType(loc, false, null, type, false);
};
  break;
    

  case 181:
  if (yyn == 181)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2521 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (4-(2))));
  PType rng = (PType)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))),
                                       rng.getLocation());
  yyval = new AMapMapType(loc, false, null, dom, rng, false);
};
  break;
    

  case 182:
  if (yyn == 182)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2529 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (4-(2))));
  PType rng = (PType)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))),
                                       rng.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, dom, rng, false);
};
  break;
    

  case 183:
  if (yyn == 183)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2537 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 184:
  if (yyn == 184)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2549 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* Convert the dottedIdentifier into a LexNameToken?
   * Old code from the IDENTIFIER DOT IDENTIFIER production below.
   */
  /* LexNameToken name = extractLexNameToken((CmlLexeme)$3); */
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
/* Line 2569 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
  ids.add(extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1))))));
  yyval = ids;
};
  break;
    

  case 186:
  if (yyn == 186)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2575 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  ids.add(extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = ids;
};
  break;
    

  case 187:
  if (yyn == 187)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2581 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  ids.add(extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = ids;
};
  break;
    

  case 188:
  if (yyn == 188)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2590 of "src/main/bison/cml.y"  */
    {
  yyval = new ABooleanBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 189:
  if (yyn == 189)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2594 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 190:
  if (yyn == 190)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2598 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 191:
  if (yyn == 191)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2602 of "src/main/bison/cml.y"  */
    {
  yyval = new AIntNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 192:
  if (yyn == 192)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2606 of "src/main/bison/cml.y"  */
    {
  yyval = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 193:
  if (yyn == 193)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2610 of "src/main/bison/cml.y"  */
    {
  yyval = new ARealNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 194:
  if (yyn == 194)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2614 of "src/main/bison/cml.y"  */
    {
  yyval = new ACharBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 195:
  if (yyn == 195)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2618 of "src/main/bison/cml.y"  */
    {
  yyval = new ATokenBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 196:
  if (yyn == 196)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2625 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 197:
  if (yyn == 197)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2629 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 198:
  if (yyn == 198)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2636 of "src/main/bison/cml.y"  */
    {
  PType domType = (PType)((yystack.valueAt (3-(1))));
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, true, params, rngType);
};
  break;
    

  case 199:
  if (yyn == 199)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2645 of "src/main/bison/cml.y"  */
    {
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, true, params, rngType);
};
  break;
    

  case 200:
  if (yyn == 200)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2657 of "src/main/bison/cml.y"  */
    {
  PType domType = (PType)((yystack.valueAt (3-(1))));
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, false, params, rngType);
};
  break;
    

  case 201:
  if (yyn == 201)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2666 of "src/main/bison/cml.y"  */
    {
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, false, params, rngType);
};
  break;
    

  case 202:
  if (yyn == 202)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2678 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 203:
  if (yyn == 203)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2684 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = (List<AFieldField>)((yystack.valueAt (2-(2))));
  res.add((AFieldField)((yystack.valueAt (2-(1)))));
  yyval = res;
};
  break;
    

  case 204:
  if (yyn == 204)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2693 of "src/main/bison/cml.y"  */
    {
  yyval = new AFieldField(getDefaultAccessSpecifier(false,false,null),
                       null,
                       null,
                       (PType)((yystack.valueAt (1-(1)))),
                       false);
};
  break;
    

  case 205:
  if (yyn == 205)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2701 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  yyval = new AFieldField(getDefaultAccessSpecifier(false,false,null),
                       name,
                       null,
                       type,
                       false);
};
  break;
    

  case 206:
  if (yyn == 206)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2711 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    PType type = (PType)((yystack.valueAt (3-(3))));
    yyval = new AFieldField(getDefaultAccessSpecifier(false,false,null),
                         name,
                         null,
                         type,
                         true);
};
  break;
    

  case 207:
  if (yyn == 207)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2724 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), exp.getLocation());
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, true, loc);
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
/* Line 2752 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new Vector<PDefinition>();
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
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
/* Line 2764 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            extractLastLexLocation(defs));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
/* Line 2794 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 211:
  if (yyn == 211)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2800 of "src/main/bison/cml.y"  */
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
/* Line 2810 of "src/main/bison/cml.y"  */
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
/* Line 2820 of "src/main/bison/cml.y"  */
    {
  PExp expression = (PExp)((yystack.valueAt (3-(3))));
  LexNameToken lnt = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,
                                                  null,
                                                  false,
                                                  lnt,
                                                  false/*constrained*/);
  AValueDefinition vdef =
    new AValueDefinition(combineLexLocation(idp.getLocation(),
                                            expression.getLocation()),
                            NameScope.LOCAL,
                            false, // used
                            getPrivateAccessSpecifier(false, false, lnt.location),
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
/* Line 2844 of "src/main/bison/cml.y"  */
    {
  LexNameToken lnt = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(1)))));
  PType type = (PType)((yystack.valueAt (5-(3))));
  PExp expression = (PExp)((yystack.valueAt (5-(5))));
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,
                                                  null,
                                                  false,
                                                  lnt,
                                                  false/*constrained*/);
  // Build the resulting AValueDefinition
  AValueDefinition vdef =
    new AValueDefinition(combineLexLocation(idp.getLocation(),
                                            expression.getLocation()),
                         NameScope.LOCAL,
                         false, // used
                         getPrivateAccessSpecifier(false, false, lnt.location),
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
/* Line 2870 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PExp expression = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(pattern.getLocation(),
                                       expression.getLocation());
  AValueDefinition vdef =
    new AValueDefinition(loc,
                         NameScope.LOCAL,
                         false, // used
                         getPrivateAccessSpecifier(false, false, loc),
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
/* Line 2890 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (5-(1))));
  PType type = (PType)((yystack.valueAt (5-(3))));
  PExp expression = (PExp)((yystack.valueAt (5-(5))));
  LexLocation loc = combineLexLocation(pattern.getLocation(),
                                       expression.getLocation());
  AValueDefinition vdef =
    new AValueDefinition(loc,
                         NameScope.LOCAL,
                         false, // used
                         getPrivateAccessSpecifier(false, false, loc),
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
/* Line 2915 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
/* Line 2929 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
/* Line 2958 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = new Vector<PDefinition>();
  functionList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = functionList;
};
  break;
    

  case 220:
  if (yyn == 220)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2964 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = (List<PDefinition>)((yystack.valueAt (2-(1))));
  functionList.add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = functionList;
};
  break;
    

  case 221:
  if (yyn == 221)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2973 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (6-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));
  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)((yystack.valueAt (6-(3))));
  List<APatternTypePair> result = (List<APatternTypePair>)((yystack.valueAt (6-(4))));
  PExp preExp = (PExp)((yystack.valueAt (6-(5))));
  PExp postExp = (PExp)((yystack.valueAt (6-(6))));
  LexLocation location = combineLexLocation(name.getLocation(), postExp.getLocation());
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
/* Line 2997 of "src/main/bison/cml.y"  */
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
/* Line 3007 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (10-(1)))));
  /* --- TODO --- */
  /* We should be checking that the two IDENTIFIERS are equivalent
   */
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (10-(1)))));
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
/* Line 3029 of "src/main/bison/cml.y"  */
    {
  yyval =  new Vector<List<PPattern>>();
};
  break;
    

  case 225:
  if (yyn == 225)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3033 of "src/main/bison/cml.y"  */
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
/* Line 3040 of "src/main/bison/cml.y"  */
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
/* Line 3050 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 228:
  if (yyn == 228)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3054 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 229:
  if (yyn == 229)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3058 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 230:
  if (yyn == 230)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3065 of "src/main/bison/cml.y"  */
    {
  yyval = new Vector<APatternListTypePair>();
};
  break;
    

  case 231:
  if (yyn == 231)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3069 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 232:
  if (yyn == 232)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3076 of "src/main/bison/cml.y"  */
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
/* Line 3083 of "src/main/bison/cml.y"  */
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
/* Line 3093 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 235:
  if (yyn == 235)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3097 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 236:
  if (yyn == 236)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3104 of "src/main/bison/cml.y"  */
    {
  List<AIdentifierTypePair> typePairs = new Vector<AIdentifierTypePair>();
  AIdentifierTypePair typePair =
    new AIdentifierTypePair(null,
                            extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1))))),
                            (PType)((yystack.valueAt (3-(3)))));
  typePairs.add(typePair);
  yyval = typePairs;
};
  break;
    

  case 237:
  if (yyn == 237)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3114 of "src/main/bison/cml.y"  */
    {
  List<AIdentifierTypePair> typePairs = (List<AIdentifierTypePair>)((yystack.valueAt (5-(1))));
  AIdentifierTypePair typePair =
    new AIdentifierTypePair(null,
                            extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (5-(3))))),
                            (PType)((yystack.valueAt (5-(5)))));
  typePairs.add(typePair);
  yyval = typePairs;
};
  break;
    

  case 238:
  if (yyn == 238)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3126 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 239:
  if (yyn == 239)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3127 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 240:
  if (yyn == 240)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3131 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 241:
  if (yyn == 241)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3135 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 242:
  if (yyn == 242)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3136 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 243:
  if (yyn == 243)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3140 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 244:
  if (yyn == 244)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3151 of "src/main/bison/cml.y"  */
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
/* Line 3158 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 246:
  if (yyn == 246)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3165 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
/* Line 3178 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
/* Line 3199 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (1-(1)))));
  yyval = opDefinitions;
};
  break;
    

  case 249:
  if (yyn == 249)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3205 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)((yystack.valueAt (2-(1))));
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (2-(2)))));
  yyval = opDefinitions;
};
  break;
    

  case 250:
  if (yyn == 250)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3214 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (7-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (7-(2)))));
  List<? extends APatternListTypePair> parameterPatterns =
    (List<? extends APatternListTypePair>)((yystack.valueAt (7-(3))));
   List<? extends AIdentifierTypePair> result =
     (List<? extends AIdentifierTypePair>)((yystack.valueAt (7-(4))));
  List<? extends AExternalClause> externals = (List<? extends AExternalClause>)((yystack.valueAt (7-(5))));
  PExp precondition = (PExp)((yystack.valueAt (7-(6))));
  PExp postcondition = (PExp)((yystack.valueAt (7-(7))));
  LexLocation location = combineLexLocation(name.location, postcondition.getLocation());
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
/* Line 3241 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* We shold check id against checkId for equality */
  LexNameToken name = extractLexNameToken(((yystack.valueAt (10-(2)))));
  SStatementAction body = (SStatementAction)((yystack.valueAt (10-(8))));
  LexLocation loc = extractLexLocation(name.location,
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
/* Line 3265 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (3-(1))));
  PType rng = (PType)((yystack.valueAt (3-(3))));
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  yyval = new AOperationType(extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};
  break;
    

  case 253:
  if (yyn == 253)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3277 of "src/main/bison/cml.y"  */
    {
  PType dom = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rng = (PType)((yystack.valueAt (3-(3))));
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  yyval = new AOperationType(extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};
  break;
    

  case 254:
  if (yyn == 254)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3289 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (3-(1))));
  PType rng = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(3))))), true);
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  yyval = new AOperationType(extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};
  break;
    

  case 255:
  if (yyn == 255)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3301 of "src/main/bison/cml.y"  */
    {
  PType dom = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rng = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(3))))), true);
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  yyval = new AOperationType(extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};
  break;
    

  case 256:
  if (yyn == 256)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3315 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 257:
  if (yyn == 257)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3319 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 258:
  if (yyn == 258)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3323 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), null, null);
};
  break;
    

  case 259:
  if (yyn == 259)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3329 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 260:
  if (yyn == 260)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3330 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 261:
  if (yyn == 261)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3334 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 262:
  if (yyn == 262)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3339 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)((yystack.valueAt (1-(1)))));
  yyval = infoList;
};
  break;
    

  case 263:
  if (yyn == 263)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3345 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = (Vector<AExternalClause>)((yystack.valueAt (2-(1))));
  infoList.add((AExternalClause)((yystack.valueAt (2-(2)))));
  yyval = infoList;
};
  break;
    

  case 264:
  if (yyn == 264)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3365 of "src/main/bison/cml.y"  */
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
/* Line 3374 of "src/main/bison/cml.y"  */
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
/* Line 3383 of "src/main/bison/cml.y"  */
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
/* Line 3392 of "src/main/bison/cml.y"  */
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
/* Line 3404 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.READ);
};
  break;
    

  case 269:
  if (yyn == 269)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3408 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.WRITE);
};
  break;
    

  case 270:
  if (yyn == 270)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3437 of "src/main/bison/cml.y"  */
    {
    AStateParagraphDefinition state = new AStateParagraphDefinition();
    state.setLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
    yyval  = state;
};
  break;
    

  case 271:
  if (yyn == 271)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3443 of "src/main/bison/cml.y"  */
    {
    AStateParagraphDefinition state = (AStateParagraphDefinition)((yystack.valueAt (2-(2))));
    state.setLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                         extractLastLexLocation(state.getStateDefs())));
    yyval = state;
};
  break;
    

  case 272:
  if (yyn == 272)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3459 of "src/main/bison/cml.y"  */
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
/* Line 3467 of "src/main/bison/cml.y"  */
    {
  AStateParagraphDefinition stateDef = (AStateParagraphDefinition)((yystack.valueAt (2-(1))));
  stateDef.getStateDefs().add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = stateDef;
};
  break;
    

  case 274:
  if (yyn == 274)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3476 of "src/main/bison/cml.y"  */
    {
  AAssignmentDefinition adef = (AAssignmentDefinition)((yystack.valueAt (2-(2))));
  adef.setAccess((AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1)))));
  yyval = adef;
};
  break;
    

  case 275:
  if (yyn == 275)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3482 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp) ((yystack.valueAt (2-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
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
/* Line 3496 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)((yystack.valueAt (1-(1)))));
  yyval = exps;
};
  break;
    

  case 277:
  if (yyn == 277)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3502 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(1))));
  exps.add((PExp)((yystack.valueAt (3-(3)))));
  yyval = exps;
};
  break;
    

  case 278:
  if (yyn == 278)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3545 of "src/main/bison/cml.y"  */
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
/* Line 3557 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanConstExp(lit.location, lit);
};
  break;
    

  case 280:
  if (yyn == 280)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3562 of "src/main/bison/cml.y"  */
    {
    yyval = new ANilExp(((LexKeywordToken)((yystack.valueAt (1-(1))))).location);
};
  break;
    

  case 281:
  if (yyn == 281)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3566 of "src/main/bison/cml.y"  */
    {
    LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
    yyval = new ACharLiteralExp(lit.location, lit);
};
  break;
    

  case 282:
  if (yyn == 282)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3571 of "src/main/bison/cml.y"  */
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
/* Line 3583 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteLiteralExp(lit.location, lit);
};
  break;
    

  case 284:
  if (yyn == 284)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3589 of "src/main/bison/cml.y"  */
    {
  yyval = new ABracketedExp(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))), (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 285:
  if (yyn == 285)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3593 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASelfExp(name.location, name);
};
  break;
    

  case 286:
  if (yyn == 286)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3598 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ANameExp(name.location, name);
};
  break;
    

  case 287:
  if (yyn == 287)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3603 of "src/main/bison/cml.y"  */
    {
  LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (2-(1)))), true);
  yyval = new ANameExp(name.location, name);
};
  break;
    

  case 288:
  if (yyn == 288)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3608 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 289:
  if (yyn == 289)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3612 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 290:
  if (yyn == 290)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3616 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 291:
  if (yyn == 291)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3620 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 292:
  if (yyn == 292)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3624 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 293:
  if (yyn == 293)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3628 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 294:
  if (yyn == 294)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3632 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 295:
  if (yyn == 295)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3637 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 296:
  if (yyn == 296)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3641 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 297:
  if (yyn == 297)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3645 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 298:
  if (yyn == 298)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3649 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 299:
  if (yyn == 299)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3654 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), exp.getLocation());
  yyval = new ALetDefExp(loc, defs, exp);
};
  break;
    

  case 300:
  if (yyn == 300)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3661 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 301:
  if (yyn == 301)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3665 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 302:
  if (yyn == 302)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3669 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 303:
  if (yyn == 303)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3673 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 304:
  if (yyn == 304)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3678 of "src/main/bison/cml.y"  */
    {
  CmlLexeme forall = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(forall), exp.getLocation());
  yyval = new AForAllExp(loc, binds, exp);
};
  break;
    

  case 305:
  if (yyn == 305)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3686 of "src/main/bison/cml.y"  */
    {
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  yyval = new AExistsExp(loc, binds, exp);
};
  break;
    

  case 306:
  if (yyn == 306)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3694 of "src/main/bison/cml.y"  */
    {
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  yyval = new AExists1Exp(loc, bind, exp, null);
};
  break;
    

  case 307:
  if (yyn == 307)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3702 of "src/main/bison/cml.y"  */
    {
  CmlLexeme iota = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = combineLexLocation(extractLexLocation(iota), exp.getLocation());
  yyval = new AIotaExp(loc, bind, exp);
};
  break;
    

  case 308:
  if (yyn == 308)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3711 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), (CmlLexeme)((yystack.valueAt (2-(2)))));
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation(loc);
  yyval = res;
};
  break;
    

  case 309:
  if (yyn == 309)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3718 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<PExp> members = (List<PExp>)((yystack.valueAt (3-(2))));
  yyval = new ASetEnumSetExp(location, members);
};
  break;
    

  case 310:
  if (yyn == 310)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3725 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASetCompSetExp(loc, exp, binds, null);
};
  break;
    

  case 311:
  if (yyn == 311)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3732 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (7-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new ASetCompSetExp(loc, exp, binds, pred);
};
  break;
    

  case 312:
  if (yyn == 312)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3741 of "src/main/bison/cml.y"  */
    {
  PExp start = (PExp)((yystack.valueAt (5-(2))));
  PExp end = (PExp)((yystack.valueAt (5-(4))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASetRangeSetExp(loc, start, end);
};
  break;
    

  case 313:
  if (yyn == 313)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3749 of "src/main/bison/cml.y"  */
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
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASeqEnumSeqExp(loc, exps);
};
  break;
    

  case 314:
  if (yyn == 314)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3765 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new ASeqEnumSeqExp(loc, exps);
};
  break;
    

  case 315:
  if (yyn == 315)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3772 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  ASetBind binds = (ASetBind)((yystack.valueAt (5-(4))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASeqCompSeqExp(loc, exp, binds, null);
};
  break;
    

  case 316:
  if (yyn == 316)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3779 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (7-(2))));
  ASetBind binds = (ASetBind)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new ASeqCompSeqExp(loc, exp, binds, pred);
};
  break;
    

  case 317:
  if (yyn == 317)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3788 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
};
  break;
    

  case 318:
  if (yyn == 318)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3793 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new AMapEnumMapExp(loc, maplets);
};
  break;
    

  case 319:
  if (yyn == 319)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3800 of "src/main/bison/cml.y"  */
    {
  AMapletExp maplet = (AMapletExp)((yystack.valueAt (5-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new AMapCompMapExp(loc, maplet, binds, null);
};
  break;
    

  case 320:
  if (yyn == 320)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3807 of "src/main/bison/cml.y"  */
    {
  AMapletExp maplet = (AMapletExp)((yystack.valueAt (7-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new AMapCompMapExp(loc, maplet, binds, pred);
};
  break;
    

  case 321:
  if (yyn == 321)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3816 of "src/main/bison/cml.y"  */
    {
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new ATupleExp(loc, exprs);
};
  break;
    

  case 322:
  if (yyn == 322)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3823 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (4-(1)))));
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  LexLocation loc = extractLexLocation(((CmlLexeme)((yystack.valueAt (4-(1))))), (CmlLexeme)((yystack.valueAt (4-(4)))));
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
/* Line 3837 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> binds = (List<ATypeBind>)((yystack.valueAt (4-(2))));
  PExp body = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), body.getLocation());
  yyval = new ALambdaExp(loc, binds, body, null, null);
};
  break;
    

  case 324:
  if (yyn == 324)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3852 of "src/main/bison/cml.y"  */
    {
  yyval = new AIsExp(null, // tc type
                  extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                     (CmlLexeme)((yystack.valueAt (4-(4))))),
                  extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (4-(1))))),
                  null, //basicType
                  (PExp)((yystack.valueAt (4-(3)))),
                  null); //PDef
};
  break;
    

  case 325:
  if (yyn == 325)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3862 of "src/main/bison/cml.y"  */
    {
  yyval = new AIsExp(null,
                  extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),
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
/* Line 3872 of "src/main/bison/cml.y"  */
    {
  yyval = new AIsExp(null,
                  extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
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
/* Line 3886 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* This instantiation can't be correct.
   */
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  PExp function = null;
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new APreExp(loc, function, exprs);
};
  break;
    

  case 328:
  if (yyn == 328)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3903 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))), (CmlLexeme)((yystack.valueAt (6-(6)))));
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
/* Line 3916 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
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
/* Line 3926 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  /* below is from the old path-based code
   */
  //LexLocation loc = extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
  //ANameChannelExp chanNameExp =
  //  (ANameChannelExp)((Path)$path).convertToChannelNameExpression();
  //List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
  //$$ = new ACompChansetSetExp(loc, chanNameExp , bindings, null);
};
  break;
    

  case 331:
  if (yyn == 331)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3937 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  //LexLocation loc = extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
  //ANameChannelExp chanNameExp = (ANameChannelExp)((Path)$path).convertToChannelNameExpression();
  //List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
  //PExp pred = (PExp)$exp;
  //$$ = new ACompChansetSetExp(loc, chanNameExp, bindings, pred);
};
  break;
    

  case 332:
  if (yyn == 332)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3952 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.FALSE, extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 333:
  if (yyn == 333)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3956 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.TRUE, extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 334:
  if (yyn == 334)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3963 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lex = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(lex);
  String res = lex.getValue();
  res = res.replace("'", ""); // FIXME: what is this for? is it correct?
  yyval = new LexCharacterToken(convertEscapeToChar(res), loc);
};
  break;
    

  case 335:
  if (yyn == 335)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3974 of "src/main/bison/cml.y"  */
    {
  yyval = new LexKeywordToken(VDMToken.NIL, extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 336:
  if (yyn == 336)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3981 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(lexeme);
  yyval = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
};
  break;
    

  case 337:
  if (yyn == 337)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3987 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(lexeme);
  BigInteger b = new BigInteger(lexeme.getValue().substring(2), 16);
  yyval = new LexIntegerToken(b.longValue(), loc);
};
  break;
    

  case 338:
  if (yyn == 338)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3994 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(lexeme);
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
/* Line 4008 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexStringToken(lit.substring(1, lit.length()-2),
                          extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 340:
  if (yyn == 340)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4017 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexQuoteToken(lit.substring(1, lit.length()-2),
                         extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 341:
  if (yyn == 341)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4028 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 342:
  if (yyn == 342)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4034 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add((PDefinition)((yystack.valueAt (3-(3)))));
  yyval = defs;
};
  break;
    

  case 343:
  if (yyn == 343)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4042 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 344:
  if (yyn == 344)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4043 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 345:
  if (yyn == 345)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4048 of "src/main/bison/cml.y"  */
    {
  PExp test = (PExp)((yystack.valueAt (5-(2))));
  PExp then = (PExp)((yystack.valueAt (5-(4))));
  List<PExp> elses = (List<PExp>)((yystack.valueAt (5-(5))));
  LexLocation loc = null;
  if (elses.size() > 0) {
    loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1))))),
                             extractLastLexLocation(elses));
  } else {
    loc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1))))),
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
/* Line 4080 of "src/main/bison/cml.y"  */
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
/* Line 4087 of "src/main/bison/cml.y"  */
    {
  PExp test = (PExp)((yystack.valueAt (5-(2))));
  PExp then = (PExp)((yystack.valueAt (5-(4))));
  List<PExp> tail = (List<PExp>)((yystack.valueAt (5-(5))));
  tail.add(new AElseIfExp(extractLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1))))),
                                             then.getLocation()),
                          test,
                          then));
  yyval = tail;
};
  break;
    

  case 348:
  if (yyn == 348)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4101 of "src/main/bison/cml.y"  */
    {
  ACasesExp alts = (ACasesExp)((yystack.valueAt (5-(4))));
  alts.setExpression((PExp)((yystack.valueAt (5-(2)))));
  alts.setLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5))))));
  yyval = alts;
};
  break;
    

  case 349:
  if (yyn == 349)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4108 of "src/main/bison/cml.y"  */
    {
  ACasesExp alts = (ACasesExp)((yystack.valueAt (9-(4))));
  alts.setExpression((PExp)((yystack.valueAt (9-(2)))));
  alts.setLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))), (CmlLexeme)((yystack.valueAt (9-(9))))));
  alts.setOthers((PExp)((yystack.valueAt (9-(8)))));
  yyval = alts;
};
  break;
    

  case 350:
  if (yyn == 350)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4119 of "src/main/bison/cml.y"  */
    {
  ACasesExp casesExp = new ACasesExp();
  casesExp.getCases().add((ACaseAlternative)((yystack.valueAt (1-(1)))));
  yyval = casesExp;
};
  break;
    

  case 351:
  if (yyn == 351)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4125 of "src/main/bison/cml.y"  */
    {
  ACasesExp casesExp = (ACasesExp)((yystack.valueAt (3-(1))));
  casesExp.getCases().add((ACaseAlternative)((yystack.valueAt (3-(3)))));
  yyval = casesExp;
};
  break;
    

  case 352:
  if (yyn == 352)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4136 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patList = (List<PPattern>)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = combineLexLocation(extractLexLeftMostFromPatterns(patList),
                                       exp.getLocation());
  ACaseAlternative res = new ACaseAlternative(loc,
                                              exp,
                                              patList,
                                              null,//PExp result_
                                              null);//List<? extends PDefinition> defs_
  yyval = res;
};
  break;
    

  case 353:
  if (yyn == 353)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4152 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryPlusUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 354:
  if (yyn == 354)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4158 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryMinusUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 355:
  if (yyn == 355)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4164 of "src/main/bison/cml.y"  */
    {
  yyval = new AAbsoluteUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 356:
  if (yyn == 356)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4170 of "src/main/bison/cml.y"  */
    {
  yyval = new AFloorUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                             ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                          (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 357:
  if (yyn == 357)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4176 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                           ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                        (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 358:
  if (yyn == 358)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4182 of "src/main/bison/cml.y"  */
    {
  yyval = new ACardinalityUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                   ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                                (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 359:
  if (yyn == 359)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4188 of "src/main/bison/cml.y"  */
    {
  yyval = new APowerSetUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 360:
  if (yyn == 360)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4194 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistUnionUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 361:
  if (yyn == 361)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4200 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistIntersectUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                     ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                                  (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 362:
  if (yyn == 362)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4206 of "src/main/bison/cml.y"  */
    {
  yyval = new AHeadUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                         (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 363:
  if (yyn == 363)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4212 of "src/main/bison/cml.y"  */
    {
  yyval = new ATailUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                         (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 364:
  if (yyn == 364)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4218 of "src/main/bison/cml.y"  */
    {
  yyval = new ALenUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                           ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                        (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 365:
  if (yyn == 365)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4224 of "src/main/bison/cml.y"  */
    {
  yyval = new AElementsUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 366:
  if (yyn == 366)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4230 of "src/main/bison/cml.y"  */
    {
  yyval = new AIndicesUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                               ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                            (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 367:
  if (yyn == 367)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4236 of "src/main/bison/cml.y"  */
    {
  yyval = new AReverseUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                               ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                            (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 368:
  if (yyn == 368)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4242 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistConcatUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 369:
  if (yyn == 369)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4248 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapDomainUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 370:
  if (yyn == 370)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4254 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapRangeUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 371:
  if (yyn == 371)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4260 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistMergeUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 372:
  if (yyn == 372)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4266 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapInverseUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 373:
  if (yyn == 373)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4275 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 374:
  if (yyn == 374)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4283 of "src/main/bison/cml.y"  */
    {
  yyval = new ATimesNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 375:
  if (yyn == 375)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4291 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubstractNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                         ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                      (PExp)((yystack.valueAt (3-(1)))),
                                      extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                      (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 376:
  if (yyn == 376)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4299 of "src/main/bison/cml.y"  */
    {
  yyval = new ADivideNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                      ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                   (PExp)((yystack.valueAt (3-(1)))),
                                   extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                   (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 377:
  if (yyn == 377)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4307 of "src/main/bison/cml.y"  */
    {
  yyval = new ADivNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 378:
  if (yyn == 378)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4315 of "src/main/bison/cml.y"  */
    {
  yyval = new ARemNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 379:
  if (yyn == 379)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4323 of "src/main/bison/cml.y"  */
    {
  yyval = new AModNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 380:
  if (yyn == 380)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4331 of "src/main/bison/cml.y"  */
    {
  yyval = new ALessNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 381:
  if (yyn == 381)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4339 of "src/main/bison/cml.y"  */
    {
  yyval = new ALessEqualNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                         ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                      (PExp)((yystack.valueAt (3-(1)))),
                                      extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                      (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 382:
  if (yyn == 382)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4347 of "src/main/bison/cml.y"  */
    {
  yyval = new AGreaterNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                       ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                    (PExp)((yystack.valueAt (3-(1)))),
                                    extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                    (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 383:
  if (yyn == 383)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4355 of "src/main/bison/cml.y"  */
    {
  yyval = new AGreaterEqualNumericBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                            ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                         (PExp)((yystack.valueAt (3-(1)))),
                                         extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                         (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 384:
  if (yyn == 384)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4363 of "src/main/bison/cml.y"  */
    {
  yyval = new AEqualsBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                               ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                            (PExp)((yystack.valueAt (3-(1)))),
                            extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                            (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 385:
  if (yyn == 385)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4371 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotEqualBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 386:
  if (yyn == 386)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4379 of "src/main/bison/cml.y"  */
    {
  yyval = new AOrBooleanBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                  ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                               (PExp)((yystack.valueAt (3-(1)))),
                               extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                               (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 387:
  if (yyn == 387)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4387 of "src/main/bison/cml.y"  */
    {
  yyval = new AAndBooleanBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 388:
  if (yyn == 388)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4395 of "src/main/bison/cml.y"  */
    {
  yyval = new AImpliesBooleanBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                       ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                    (PExp)((yystack.valueAt (3-(1)))),
                                    extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                    (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 389:
  if (yyn == 389)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4403 of "src/main/bison/cml.y"  */
    {
  yyval = new AEquivalentBooleanBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                          ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                       (PExp)((yystack.valueAt (3-(1)))),
                                       extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                       (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 390:
  if (yyn == 390)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4411 of "src/main/bison/cml.y"  */
    {
  yyval = new AInSetBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                              ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                           (PExp)((yystack.valueAt (3-(1)))),
                           extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                           (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 391:
  if (yyn == 391)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4419 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotInSetBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 392:
  if (yyn == 392)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4427 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubsetBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                               ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                            (PExp)((yystack.valueAt (3-(1)))),
                            extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                            (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 393:
  if (yyn == 393)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4435 of "src/main/bison/cml.y"  */
    {
  yyval = new AProperSubsetBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 394:
  if (yyn == 394)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4443 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetUnionBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 395:
  if (yyn == 395)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4451 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetDifferenceBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                      ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                   (PExp)((yystack.valueAt (3-(1)))),
                                   extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                   (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 396:
  if (yyn == 396)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4459 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetIntersectBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 397:
  if (yyn == 397)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4467 of "src/main/bison/cml.y"  */
    {
  yyval = new ASeqConcatBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                  ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                               (PExp)((yystack.valueAt (3-(1)))),
                               extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                               (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 398:
  if (yyn == 398)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4475 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 399:
  if (yyn == 399)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4483 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapUnionBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 400:
  if (yyn == 400)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4491 of "src/main/bison/cml.y"  */
    {
  yyval = new ADomainResToBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 401:
  if (yyn == 401)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4499 of "src/main/bison/cml.y"  */
    {
  yyval = new ADomainResByBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 402:
  if (yyn == 402)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4507 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 403:
  if (yyn == 403)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4515 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 404:
  if (yyn == 404)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4523 of "src/main/bison/cml.y"  */
    {
  yyval = new ACompBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                             ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                          (PExp)((yystack.valueAt (3-(1)))),
                          extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                          (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 405:
  if (yyn == 405)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4531 of "src/main/bison/cml.y"  */
    {
  yyval = new AStarStarBinaryExp(extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 406:
  if (yyn == 406)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4542 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 407:
  if (yyn == 407)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4548 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(1))));
  maplets.add((AMapletExp)((yystack.valueAt (3-(3)))));
  yyval = maplets;
};
  break;
    

  case 408:
  if (yyn == 408)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4556 of "src/main/bison/cml.y"  */
    {
  PExp dom = (PExp)((yystack.valueAt (3-(1))));
  PExp rng = (PExp)((yystack.valueAt (3-(3))));
  yyval = new AMapletExp(extractLexLocation(dom.getLocation(),
                                         rng.getLocation()),
                      dom,
                      rng);
};
  break;
    

  case 409:
  if (yyn == 409)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4568 of "src/main/bison/cml.y"  */
    {
  PExp guard = (PExp)((yystack.valueAt (3-(1))));
  PAction action = (PAction)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation(guard.getLocation(),
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
/* Line 4581 of "src/main/bison/cml.y"  */
    {
  PExp guard = (PExp)((yystack.valueAt (5-(3))));
  PAction action = (PAction)((yystack.valueAt (5-(5))));
  LexLocation location = extractLexLocation(guard.getLocation(),
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
/* Line 4597 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (1-(1)))));
  yyval = assignmentDefs;
};
  break;
    

  case 412:
  if (yyn == 412)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4603 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)((yystack.valueAt (3-(1))));
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (3-(3)))));
  yyval = assignmentDefs;
};
  break;
    

  case 413:
  if (yyn == 413)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4612 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation(name.location, type.getLocation());
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
/* Line 4629 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(1)))));
  PType type = (PType)((yystack.valueAt (5-(3))));
  PExp exp = (PExp)((yystack.valueAt (5-(5))));
  LexLocation location = combineLexLocation(name.location, exp.getLocation());
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
/* Line 4645 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(1)))));
    PType type = (PType)((yystack.valueAt (5-(3))));
    PExp exp = (PExp)((yystack.valueAt (5-(5))));
    LexLocation location = combineLexLocation(name.location, exp.getLocation());
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
/* Line 4668 of "src/main/bison/cml.y"  */
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
/* Line 4675 of "src/main/bison/cml.y"  */
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
/* Line 4693 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  List<LexIdentifierToken> id = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  PStateDesignator stateDesignator = null;
  // FIXME: old path code
  // stateDesignator = path.convertToStateDesignator();
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation(stateDesignator.getLocation(),
                                            exp.getLocation());
  yyval = new ASingleGeneralAssignmentStatementAction(location, stateDesignator, exp);
};
  break;
    

  case 419:
  if (yyn == 419)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4708 of "src/main/bison/cml.y"  */
    {
  yyval = new AIfStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
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
/* Line 4717 of "src/main/bison/cml.y"  */
    {
  yyval = new AIfStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
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
/* Line 4729 of "src/main/bison/cml.y"  */
    {
  List<AElseIfStatementAction> elseStms = new Vector<AElseIfStatementAction>();
  AElseIfStatementAction elseif =
    new AElseIfStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
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
/* Line 4740 of "src/main/bison/cml.y"  */
    {
  List<AElseIfStatementAction> elseStms = (List<AElseIfStatementAction>)((yystack.valueAt (5-(1))));
  AElseIfStatementAction elseif =
    new AElseIfStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(2)))),
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
/* Line 4754 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (5-(4))));
  cases.setLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5))))));
  cases.setExp((PExp)((yystack.valueAt (5-(2)))));
  yyval = cases;
};
  break;
    

  case 424:
  if (yyn == 424)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4761 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (9-(4))));
  cases.setLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))), (CmlLexeme)((yystack.valueAt (9-(9))))));
  cases.setExp((PExp)((yystack.valueAt (9-(2)))));
  cases.setOthers((PAction)((yystack.valueAt (9-(8)))));
  yyval = cases;
};
  break;
    

  case 425:
  if (yyn == 425)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4772 of "src/main/bison/cml.y"  */
    {
  List<ACaseAlternativeAction> casesList = new LinkedList<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)((yystack.valueAt (1-(1)))));
  yyval = new ACasesStatementAction(null, null, casesList, null);
};
  break;
    

  case 426:
  if (yyn == 426)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4778 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (3-(1))));
  cases.getCases().add((ACaseAlternativeAction)((yystack.valueAt (3-(3)))));
  yyval = cases;
};
  break;
    

  case 427:
  if (yyn == 427)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4789 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PAction action = (PAction)((yystack.valueAt (3-(3))));
  yyval = new ACaseAlternativeAction(combineLexLocation(extractFirstLexLocation(patterns),
                                                     action.getLocation()),
                                  patterns,
                                  action);
};
  break;
    

  case 428:
  if (yyn == 428)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4801 of "src/main/bison/cml.y"  */
    {
  List<? extends AExternalClause> exts = (List<? extends AExternalClause>)((yystack.valueAt (3-(1))));
  PExp pre = (PExp)((yystack.valueAt (3-(2))));
  PExp post = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = null;
  if (exts != null) {
    loc = extractLexLocation(extractFirstLexLocation(exts), post.getLocation());
  } else if (pre != null) {
    loc = extractLexLocation(pre.getLocation(), post.getLocation());
  } else {
    loc = post.getLocation();
  }
  yyval = new ASpecificationStatementAction(loc, exts, pre, post);
};
  break;
    

  case 429:
  if (yyn == 429)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4818 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 430:
  if (yyn == 430)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4819 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 431:
  if (yyn == 431)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4824 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 432:
  if (yyn == 432)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4828 of "src/main/bison/cml.y"  */
    {
  List<PPattern> plist = (List<PPattern>)((yystack.valueAt (6-(3))));
  plist.add((PPattern)((yystack.valueAt (6-(5)))));
  yyval = new ATuplePattern(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
                                            (CmlLexeme)((yystack.valueAt (6-(6))))),
                         new LinkedList<PDefinition>(),
                         false,
                         plist);
};
  break;
    

  case 433:
  if (yyn == 433)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4845 of "src/main/bison/cml.y"  */
    {
  List<? extends PPattern> plist = null;
  LexNameToken name = extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (2-(1)))));
  yyval = new ARecordPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
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
/* Line 4856 of "src/main/bison/cml.y"  */
    {
  List<? extends PPattern> plist = (List<? extends PPattern>)((yystack.valueAt (4-(3))));
  LexNameToken name = extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (4-(1)))));
  yyval = new ARecordPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
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
/* Line 4870 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)((yystack.valueAt (1-(1)))));
  yyval = patterns;
};
  break;
    

  case 436:
  if (yyn == 436)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4876 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  patterns.add((PPattern)((yystack.valueAt (3-(3)))));
  yyval = patterns;
};
  break;
    

  case 437:
  if (yyn == 437)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4885 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(lexeme);
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
/* Line 4899 of "src/main/bison/cml.y"  */
    {
  yyval = new AIgnorePattern(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),
                          new LinkedList<PDefinition>(),
                          true);
};
  break;
    

  case 439:
  if (yyn == 439)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4909 of "src/main/bison/cml.y"  */
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
/* Line 4919 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 441:
  if (yyn == 441)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4924 of "src/main/bison/cml.y"  */
    {
  LexKeywordToken lit = (LexKeywordToken)((yystack.valueAt (1-(1))));
  yyval = new ANilPattern(lit.location, new LinkedList<PDefinition>(), true);
};
  break;
    

  case 442:
  if (yyn == 442)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4929 of "src/main/bison/cml.y"  */
    {
  LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
  yyval = new ACharacterPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 443:
  if (yyn == 443)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4934 of "src/main/bison/cml.y"  */
    {
  LexStringToken lit = (LexStringToken)((yystack.valueAt (1-(1))));
  yyval = new AStringPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 444:
  if (yyn == 444)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4939 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuotePattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 445:
  if (yyn == 445)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4944 of "src/main/bison/cml.y"  */
    {
  yyval = new AExpressionPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
                                                 (CmlLexeme)((yystack.valueAt (3-(3))))),
                              new LinkedList<PDefinition>(),
                              false,
                              (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 446:
  if (yyn == 446)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4954 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 447:
  if (yyn == 447)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4955 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 448:
  if (yyn == 448)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4960 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation(pattern.getLocation(),
                                            exp.getLocation());
  yyval = new ASetBind(location, pattern, exp);
};
  break;
    

  case 449:
  if (yyn == 449)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4971 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation location = extractLexLocation(pattern.getLocation(),
                                            type.getLocation());
  yyval = new ATypeBind(location, pattern, type);
};
  break;
    

  case 450:
  if (yyn == 450)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4982 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)((yystack.valueAt (1-(1)))));
  yyval = binds;
};
  break;
    

  case 451:
  if (yyn == 451)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4988 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (3-(1))));
  binds.add((PMultipleBind)((yystack.valueAt (3-(3)))));
  yyval = binds;
};
  break;
    

  case 452:
  if (yyn == 452)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4996 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 453:
  if (yyn == 453)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4997 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 454:
  if (yyn == 454)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 5002 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = extractLexLocation(extractFirstLexLocation(patterns),
                                       exp.getLocation());
  yyval = new ASetMultipleBind(loc, patterns, exp);
};
  break;
    

  case 455:
  if (yyn == 455)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 5013 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = extractLexLocation(extractFirstLexLocation(patterns),
                                       type.getLocation());
  yyval = new ATypeMultipleBind(loc, patterns, type);
};
  break;
    

  case 456:
  if (yyn == 456)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 5024 of "src/main/bison/cml.y"  */
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
/* Line 5031 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> list = (List<ATypeBind>)((yystack.valueAt (3-(1))));
  ATypeBind bind = (ATypeBind)((yystack.valueAt (3-(3))));
  list.add(bind);
  yyval = list;
};
  break;
    



/* Line 374 of cmlskeleton.java.m4  */
/* Line 7700 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
       105,   872,  -124,   -65,   872,  1004,   -57,   -31,   154,   105,
    -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  -655,   159,  -655,    36,   -92,  -124,  -655,  -655,
     191,   -65,  -655,    42,   872,  -655,   437,   236,  -655,    16,
     260,  -655,  -655,   872,    79,   147,  1414,  -655,  2702,  -655,
     289,  -655,  -655,  2702,   328,   426,  -655,  -655,  -655,    23,
    -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  -655,    30,  -655,   541,   208,   213,   391,  -655,
    1414,  1682,   -92,  -655,  1414,    22,  1414,   241,  1414,  1414,
    1414,  1414,  1414,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  1010,    29,  -655,  -655,  -655,  -655,  -655,  -655,
    2702,  2702,  2294,  1395,  1220,  2702,  2702,  2702,  2702,  2702,
    2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,
    2702,  2702,  2702,  2702,  2702,  2702,  2702,   399,   416,  1395,
     141,   433,   438,  1395,  1395,  1395,  2702,   443,  -655,   380,
    -655, 11044,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  1395,  -655,  1414,   375,  5177,  1395,  1395,  -655,
    2702,  1414,  2702,  1414,  -655,   938,   474,   387,   387,   387,
     147,   387,   387,  2702,   895,   254,  1488,    -4,  -655,   944,
      -2,  1010,    97,  -655,   732,  1414,  1414,   933,   532,   986,
     995,   548,   548,   548,  1414,  1414,  1414,  1414,   398,   401,
    5299,     4,  5422,  -655,    18,  4442,   195,   559,  -655,  -655,
     230,  -655,  -655,   599,  -655,  -655,    39,  -655,  -655,   -20,
    -655,  5544,  5666,  1579,  1579,  1579,  1579, 11495,  1579,  1579,
    1579,  1579,  1579,  1579,  1579,  1579,  1579,  1579,  1579,  1579,
    1579,  1579,  2773,  2702,  2702,   458,  -655,    45,  2702,   596,
    2702,   456,  -655,   338,    87,  -655,  -655,  -655,   130,   630,
     -96,  5789,  2702,  -655,  2702,  2702,  -655,  2702,  2702,  2702,
      15,   557,   478,  2702,  2702,  2702,  2702,  2702,  2702,  2702,
    2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,
    2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,  2702,
    2702,  2702,  1395,   309,    47,   -18,  1010,   497,   539,    -5,
    -655,   543,   197, 11044,   520, 11044,   619,   872,   541,   123,
     537,   938,  -655,  -655,  -655,  -655,  -655,   521,   452,   382,
    -655,  -655,   435,   446,     0,   533,   560,  5911,   535,   542,
    2654,  1616,  -655,  -655,  2702,  -655,   544,   544,   544,  2702,
     544,   544,  2702,  2702,  2702,   544,   544,  2702,  2702,  2702,
    -655,   544,  -655,  1395,  -655,  1414,  1414,  -655,  -655,  -655,
    1010,  1010,  -655,  1682,  1414,  1414,  1010,   548,   548,  1010,
    -655,  -655,  -655,  -655,  2702,  1395,  -655,  1395,  2702,  2702,
    -655,  2702,  1395,  2702,  1414,  2702,  1414,  2702,  1220,  2702,
    1395,   203, 11044,   216,  2702,  1395,  6033,  2702,   286,   324,
    2702,  1395,  1414,  2702,  1395,  2702,  2702,  -655,  1395,  6155,
   11545,   295,  6277,  3164, 11618, 11618,  -655,  -655,  -655,  -655,
    3164,  3164,  3164,  3164, 11545, 11545, 11545, 11545, 11545, 11368,
   11418, 11288, 11166, 11545, 11545, 11545, 11545,  3286,  3286,  2773,
    3530,  3408,  3408,  2773,  2773,  1579,  1579,   463,   373,  -655,
    -655,  -655,  -655,  -655,  1395,  1414,   511,  1414,  2702,   558,
     693,  -655,  1395,  -655,  2702,  2702,   565,   872,  -655,  -655,
    2702,   571,   123,  -655,  -655,  -655,   938,  2702,   387,   544,
     387,   544,   544,   544,   594,   544,   387,   743,   578,   758,
     591,   387,   387,   387,  1922,  2430,  -655,  -655,  -655,  -655,
    2702,   387,   387,  1220,  2702,  2702,   760,   625,  2702,  2702,
    2702,  2702,   337,  2702,  2402,   649,  -655,  -655,  -655,  2654,
    -655,   306,   708,  2038,  1686,  3119,  4075,  1695,   151,  6399,
     582,  6521,  4197,   199,   251, 11044, 11044, 11044,  1488,   737,
    1010,  1010,  1092,   548,   548, 11044,   666,    40,   193,  6645,
   11044,  6767,  -655,   198, 11545,  1010, 11166, 11044,  -655,  4564,
     240,  -655,    10,  -655,  -655, 11166,  -655,  1414,  6889,  -655,
    2702, 11044,  -655,  1010, 11166,  -655, 11166, 11166,    14,  -655,
    -655,  2702,   403,  1010,  1395,  -655,   301,  1010, 11044,   642,
    2702,  -655,   772, 11044, 11044,   290,  -655, 11044,   645,  -655,
    -655,   622, 11044,  -655,  -655,  -655,  -655,  -655,  -655,   924,
    2702,  -655,  -655,   566,  2702,   785,  2833,   789,   581,   628,
     634,   571,   147,   147,   147,   -92,  2951,   638,  -655,     1,
     444,   762,  -655,  7011,   791, 11044,   641,   657,   -19,  4686,
     -12,  7133,   456,  2702,  -655,  7255,  7377,   135,  7499,     5,
    1395,   801,    -7,   756,  3342,  2654,  2654,  2654,  2702,  2654,
    2654,  2702,  2702,  2702,  2654,  2654,  2702,  2702,  2702,  -655,
    -655,  2702,  -655,  2566,  2790,  -655,  2702,   544,   544,  -655,
    2702,  2702,   544,  2702,  -655,  2702,  -655,  2702,  -655,  -655,
    2702,  -655,  2702,  2702,  -655,   620,  -655,  2702,   819,  -655,
    7621,  2702,  -655,  7743,  1414,   307,  1395,  2158,  1414, 11044,
    -655,  1730,   375,  1414,  -655,   799,  7865,   544, 11044,  2702,
    -655,  3087,     7,  2833,  2654,  2654,   805,   194,  -655,  -655,
    -655,  -655,  -655,  2654,   464,  2654,  -655,  -655,   444,   680,
     456,   693,    63,  -655,   807,  2654,  2654,  2654,  2654,  2702,
    -655,  1395,   689,   334,  -655,  7987,   387,   387,   814,  -655,
     717,  2702,  2702,  2702,  2654,  1146,  1501,   808,  3953,  1180,
     552,  4809,  8110,  4319,   304,    12, 11044, 11044, 11044,   320,
    2702, 11044,  -655,  8232,   251,  1695,  8354,  8477,   199, 11044,
    8599,  8723,  8847,  8969, 11166,   806,  -655, 11044,  -655,  -655,
    9091,  -655,  1010,  -655,   353,  -655,  -655,   762, 11044,  1010,
     504,   670,   656,   783,   696,   755,  2702,   715,  -655, 11044,
    2654,  -655,  -655,  -655,  2702,  2654,   571,  2971,  -655,  3042,
     680,   456,   278,  -655,  2654,  2654,  2702,  -655,  3087,  3087,
    3033,  9213,   245,  -655,    11,  2702,  -655,   456,  -655,   659,
     663,  2654,  2702,  9335,  3464,  3586,  3087,  2702,  2702,  2654,
    2702,  2654,  2702,  2654,  -655, 11044,   544,  2702,  1395,  -655,
    -655,  -655,  2702,  2702,  -655,  -655,   693,  1777,  1971,   511,
     762,  2702,  2702,  9457,   392,  -655,  3087,  9579,  3067,  -655,
     826,   830,   367,  1414,  -655,   552,  4932,  2702,  2654,   449,
    2654,  1452,  -655,  2654,  -655,   828,  2654,  3087,   363,  3708,
    2702,  2654,  2654,  9701,  9823,    12,  5055,  1180,  9945,   304,
    1695, 11044,   149,  4564,  2911,   798,  -655,    22,  1010,    22,
    1010,   385,   693, 11044, 11044,  -655,  2654,  -655,  2702,  2702,
    1414,  1010,  2654,  2702, 10067,  3087,  2702,  2654,  3087,   824,
    -655,  3087,  2702,  -655,  -655,  2654,  2654,  3220,  3087,  3087,
    2702,  2654,  2702,  2654,  2654,  2702,  -655,  -655,  -655,   456,
    -655,  2388,  -655,  -655,   369,   381,  1010,  -655, 10189,  2654,
   10311,  3087,  2654, 10433,  3087,  3087,  2654,  2702, 10556,  1180,
   10678,  1180,  -655, 10800,    29,  -655,  -655,  3087,   762,  -655,
    -655,  2654,  3087,  2654,  2799,  2654,  3087,  3830,  2702,  2654,
    -655,   693,  -655,  3087,  -655,  -655,  2654, 10922,  1180,  -655,
    3087,  2654,  1180
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
     439,   443,   444,     0,   431,   170,     0,     0,     0,   161,
       0,     0,     0,   140,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   185,   188,   189,   190,   191,   192,   193,
     194,   195,   139,   184,   172,   183,   196,   197,   173,   313,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   317,   286,
     285,   145,   279,   281,   280,   278,   282,   283,   300,   301,
     302,   303,     0,   230,     0,     0,     0,     0,     0,   433,
       0,     0,     0,     0,   211,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    31,    12,     0,   137,   162,
     185,   204,   164,   202,     0,     0,     0,     0,     0,     0,
       0,   179,   180,   178,     0,     0,     0,     0,     0,     0,
       0,     0,   276,   308,     0,   276,     0,   406,   438,   437,
       0,   430,   429,     0,   446,   447,     0,   343,   344,     0,
     341,     0,     0,   353,   354,   355,   356,   357,   358,   359,
     360,   361,   362,   363,   364,   365,   366,   367,   368,   369,
     370,   371,   372,     0,     0,     0,   456,     0,     0,     0,
       0,     0,   435,     0,     0,   450,   452,   453,     0,     0,
       0,   276,     0,   287,     0,     0,   291,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   183,     0,   239,
     445,     0,     0,   213,     0,   215,     0,   246,   170,   270,
       0,   149,   151,   152,   153,   154,   155,     0,     0,     0,
      43,    42,     0,     0,     0,     0,     0,     0,    52,     0,
       0,     0,    48,    50,     0,    32,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      34,     0,   138,     0,   163,     0,     0,   203,   165,   171,
     201,   199,   177,     0,     0,     0,   200,   175,   176,   198,
     186,   187,   284,   314,     0,     0,   309,     0,     0,     0,
     318,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   276,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   329,     0,     0,
     384,     0,   276,   374,   373,   375,   288,   294,   290,   289,
     377,   376,   378,   379,   380,   381,   382,   383,   385,   386,
     387,   388,   389,   390,   391,   392,   393,   394,   395,   396,
     397,   398,   399,   400,   401,   402,   403,   404,   405,   297,
     298,   295,   296,   231,     0,     0,     0,     0,     0,     0,
       0,   238,     0,   434,     0,     0,     0,   247,   248,   156,
       0,     0,   271,   272,    10,   150,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    53,     0,
      51,     0,     0,     0,     0,   260,    60,    61,    62,    63,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   104,   101,   112,     0,
      49,     0,    31,    18,    17,    22,     0,    21,    16,     0,
       0,     0,     0,    23,    25,    29,    28,    27,    13,     0,
     206,   205,     0,   182,   181,   277,     0,     0,     0,     0,
     408,     0,   407,     0,   448,   449,   307,   299,   342,     0,
       0,   350,     0,   321,   322,   323,   457,     0,     0,   327,
       0,   454,   436,   455,   304,   451,   305,   306,     0,   324,
     292,     0,     0,   232,     0,   224,     0,   236,   240,     0,
       0,   221,   436,   214,   216,     0,   249,   275,     0,   274,
     273,     0,    46,    47,    37,    45,    44,    36,    41,     0,
       0,    40,    35,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   185,     0,     0,   121,     0,
       0,   239,   259,     0,     0,    64,     0,     0,     0,     0,
       0,     0,     0,     0,   109,     0,     0,     0,     0,     0,
       0,   437,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    14,
      78,     0,   106,     0,     0,    33,     0,     0,     0,   126,
       0,     0,     0,     0,   174,     0,   315,     0,   310,   312,
       0,   319,     0,     0,   345,     0,   348,     0,     0,   325,
       0,     0,   330,     0,     0,     0,     0,     0,     0,   243,
     432,     0,   234,     0,    11,     0,     0,     0,    56,     0,
      54,    58,     0,     0,     0,     0,     0,     0,   411,   125,
     124,   123,    65,     0,     0,     0,   268,   269,   261,   262,
       0,     0,     0,   108,     0,     0,     0,     0,     0,     0,
     102,     0,     0,     0,   416,     0,     0,     0,     0,   103,
       0,     0,     0,     0,     0,    70,    69,    81,     0,    83,
      68,     0,     0,     0,    71,    73,    77,    76,    75,     0,
       0,   418,    15,     0,    26,    19,     0,   129,    24,   207,
       0,     0,     0,     0,   346,     0,   351,   352,   326,   328,
       0,   293,   233,   225,     0,   228,   229,   239,   227,   237,
       0,     0,     0,   260,   235,   413,     0,     0,    38,    57,
       0,    55,    93,    92,     0,     0,     0,     0,   122,     0,
     263,     0,   264,   428,     0,     0,     0,    91,    99,   409,
       0,     0,     0,   425,     0,     0,   105,     0,   110,     0,
       0,     0,     0,     0,     0,     0,   118,     0,     0,     0,
       0,     0,     0,     0,   107,   111,     0,     0,     0,   316,
     311,   320,     0,     0,   331,   226,   242,     0,     0,     0,
     239,     0,     0,     0,    31,    39,    59,     0,     0,   412,
      88,     0,   266,     0,    66,    67,     0,     0,     0,     0,
       0,     0,   423,     0,   417,     0,     0,   119,     0,     0,
       0,     0,     0,     0,     0,    74,     0,    87,     0,    72,
      20,   130,     0,     0,     0,   245,   241,   255,   253,   254,
     252,     0,     0,   415,   414,    30,     0,   100,     0,     0,
       0,   265,     0,     0,     0,   420,     0,     0,   410,     0,
     426,   427,     0,    94,    79,     0,     0,     0,   114,   113,
       0,     0,     0,     0,     0,     0,   127,   347,   349,     0,
     223,     0,   250,    95,     0,     0,   267,    98,     0,     0,
       0,   419,     0,     0,   120,   115,     0,     0,     0,    85,
       0,    82,    80,     0,   244,   257,   258,   256,   239,    89,
      90,     0,   421,     0,     0,     0,   116,     0,     0,     0,
     128,   242,    97,   422,   424,    96,     0,     0,    86,   251,
     117,     0,    84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -655,  -655,  -655,   843,  -655,  -655,   179,  -148,   242,  -655,
     515,  -655,  -655,   119,  1147,  -655,  -655,   113,   -40,  -655,
    -655,  -655,   841,   -74,   118,  -655,  -655,   840,  -655,  -314,
    -161,   121,  -655,   827,    91,   614,    13,   734,  -140,  -655,
    -655,   495,  -186,   687,   534,  -655,   811,   847,   556,  -655,
     857,   859,    -6,  -655,   279,  -655,  -655,   164,  -633,  -655,
    -134,  -479,  -655,  -655,  -655,  -318,  -655,  -655,    66,  -655,
    -655,   156,  -655,  -655,  -655,   417,   -86,   -48,   745,   958,
    1064,  1279,  1385,   839,   394,   513,  -655,   -21,  -655,  -655,
     218,  -655,  -655,  -655,   546,   404,  -655,  -634,  -655,  -654,
    -655,  -655,  -655,  -655,    37,  -655,   -98,   -25,  -146,  -655,
    -162,  -142,  -266,  -137,  -132,   512,  -655,  -655,  -655
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   186,   633,   340,   351,
     352,   510,   508,   740,   741,   928,   647,   648,   690,   550,
      13,    27,    28,   742,   341,    14,    31,    32,    15,   330,
     331,   332,    23,    24,   486,   191,   535,   104,   105,   106,
     107,   192,   193,   374,   333,    37,    38,   227,   334,    34,
      35,   228,   606,   827,   165,   314,   833,   319,   480,   481,
     945,   946,   990,   335,   487,   488,   832,  1018,   651,   652,
     758,   759,   760,   336,   492,   493,   211,   412,   152,   153,
     154,   155,   156,   157,   229,   230,   158,   714,   159,   580,
     581,   160,   161,   216,   217,   660,   747,   619,   773,   536,
     537,   919,   538,   862,   863,   654,   262,   221,   263,   222,
      74,   223,   224,   225,   264,   265,   266,   267,   257
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "151,611,256,269,187,166,377,748,774,371,489,73,268,503,755,220,315,495,761,769,840,321,322,353,317,393,214,721,76,339,342,343,53,345,346,170,769,717,923,407,766,255,172,375,478,680,470,220,396,195,394,170,782,705,45,427,26,46,414,103,270,706,210,212,215,473,196,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,73,854,80,25,103,103,33,36,103,271,103,423,103,103,103,103,103,344,56,1,2,3,403,4,81,84,30,85,86,437,208,29,16,323,39,325,33,408,408,421,567,16,475,87,209,25,347,88,89,90,91,92,479,404,425,373,29,370,376,778,40,394,472,57,5,41,363,421,421,258,682,424,770,985,83,394,722,36,411,413,616,490,686,171,687,418,58,82,103,779,173,82,82,621,688,103,77,103,82,406,431,353,415,43,474,896,436,188,60,61,62,63,64,65,48,855,103,707,845,103,103,416,710,909,924,469,483,44,103,103,103,103,583,50,708,429,400,430,432,711,433,434,435,361,424,584,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,582,568,317,952,541,75,573,354,78,355,419,559,424,190,586,6,7,394,853,64,361,94,95,96,97,98,99,100,101,986,363,424,598,566,188,19,20,21,22,-170,589,372,162,162,163,163,367,546,368,600,549,551,552,255,726,555,556,557,369,592,695,823,53,82,602,94,95,96,97,98,99,100,101,680,884,424,846,401,363,421,424,167,565,727,403,394,569,570,866,571,53,574,366,576,367,577,368,579,394,638,639,640,585,867,208,588,369,895,591,656,657,594,377,596,597,404,175,974,209,73,612,1031,715,1019,103,103,673,921,276,176,363,499,103,103,103,1020,45,56,726,46,218,177,178,179,180,354,367,355,368,716,208,500,253,103,922,103,491,198,369,181,182,54,55,913,209,56,608,991,394,254,672,103,613,614,164,731,649,394,617,168,667,169,57,363,501,622,260,682,394,421,53,261,420,208,725,502,272,685,372,686,281,687,394,653,500,58,590,209,655,992,57,688,659,661,273,500,665,666,276,668,421,674,337,422,103,471,103,60,61,62,63,64,65,58,73,421,756,757,670,370,370,370,311,370,370,975,497,966,967,370,370,394,960,671,370,60,61,62,63,64,65,54,55,394,604,56,605,195,484,183,17,543,544,545,498,547,548,46,720,17,553,554,504,204,196,421,558,205,724,723,281,318,18,206,177,178,179,180,729,184,207,18,500,338,383,582,497,185,780,505,57,103,181,182,390,737,824,391,736,491,370,206,738,370,370,370,402,370,370,500,744,310,311,58,498,500,103,46,642,643,644,799,897,404,177,178,179,180,405,417,500,775,83,59,626,60,61,62,63,64,65,864,181,182,869,870,788,485,188,791,792,793,93,53,796,797,798,745,663,426,664,801,204,746,803,438,205,753,806,807,764,809,206,810,439,811,102,500,812,207,813,814,691,500,692,817,765,754,925,820,500,772,926,476,624,828,627,628,629,631,632,815,183,218,477,482,500,839,500,496,189,363,500,204,194,682,197,205,199,200,201,202,203,206,54,55,494,686,56,687,207,507,184,19,20,21,22,861,509,688,542,354,699,355,700,177,178,179,180,873,874,875,630,103,609,208,623,103,625,610,103,615,103,181,182,693,379,618,885,209,188,634,942,57,635,204,749,750,751,205,370,370,184,372,370,206,636,637,898,852,542,864,207,662,316,208,58,67,204,703,403,324,205,326,903,865,730,728,206,209,733,734,907,739,370,207,219,743,60,61,62,63,64,65,916,380,381,478,763,781,901,783,836,801,386,387,388,389,675,676,929,844,851,856,678,933,934,871,936,893,938,872,899,818,650,941,679,680,479,958,943,944,204,959,972,989,205,1002,42,953,954,902,206,183,67,67,372,188,841,207,912,370,540,848,47,964,79,49,994,995,259,72,804,805,562,378,772,808,977,66,67,108,174,184,67,67,67,52,51,951,732,904,834,1039,1,900,370,348,4,327,349,328,329,67,350,620,103,103,67,67,850,998,838,658,1000,108,108,578,987,108,1003,108,103,108,108,108,108,108,1008,816,1010,669,595,1013,356,357,358,1,735,5,359,4,327,572,328,329,681,363,72,72,382,682,360,361,970,1027,204,0,683,684,205,685,0,686,0,687,206,204,0,103,0,205,0,207,72,688,1037,206,72,72,72,0,5,0,207,560,561,373,0,0,68,0,0,0,563,564,0,72,1014,108,0,0,72,72,0,0,108,0,108,204,0,0,905,205,575,0,324,-170,204,206,384,67,205,0,0,0,207,108,206,385,108,108,593,204,0,207,0,205,0,108,108,108,108,206,19,20,21,22,-170,0,207,0,0,67,67,0,0,0,0,0,0,940,362,363,0,0,0,68,68,0,0,0,0,0,364,365,0,366,0,367,0,368,0,0,0,603,0,607,-170,-170,0,369,-170,68,0,0,69,68,68,68,0,0,0,0,0,84,0,85,86,0,0,0,0,0,67,72,68,0,0,0,0,68,68,87,0,0,0,88,89,90,91,92,0,0,0,0,67,-170,67,0,0,0,0,67,0,0,0,72,72,67,0,67,0,0,0,0,67,676,0,0,-170,0,67,0,0,67,0,0,0,67,0,0,0,69,69,680,19,20,21,22,-170,0,-170,-170,-170,-170,-170,-170,0,0,675,676,0,0,0,0,0,718,0,69,0,0,0,69,69,69,0,0,72,680,108,108,0,0,0,67,0,0,108,108,108,0,69,67,0,0,0,69,69,0,72,0,72,53,68,0,0,72,0,108,0,108,0,72,0,72,0,0,0,0,72,0,0,0,0,0,72,108,0,72,0,704,0,72,67,0,68,68,190,0,0,0,0,67,64,0,94,95,96,97,98,99,100,101,0,363,0,0,0,682,0,0,0,0,0,0,683,684,0,685,0,686,0,687,54,55,0,0,56,72,108,70,108,688,0,0,0,72,0,363,0,0,0,682,0,0,0,68,0,0,683,684,0,685,822,686,0,687,829,0,69,831,0,835,0,67,0,688,0,68,0,68,0,57,0,0,68,0,72,0,0,0,68,0,68,0,0,72,0,68,0,0,69,69,0,68,58,0,68,0,0,0,68,0,0,0,0,0,70,70,0,0,0,0,0,0,226,108,60,61,62,63,64,65,0,0,0,0,53,0,0,67,0,0,70,0,0,71,70,70,70,0,108,0,0,0,0,84,68,85,86,0,0,69,0,0,68,70,0,72,0,0,70,70,0,87,0,0,0,88,89,90,91,92,0,69,67,69,218,0,0,0,69,0,0,53,0,67,69,0,69,0,0,0,0,69,0,68,0,54,55,69,0,56,69,0,68,0,69,0,0,0,0,534,71,71,0,0,356,357,358,0,0,0,359,72,0,948,950,0,0,0,67,969,0,218,360,361,0,0,71,0,0,961,71,71,71,0,57,0,680,0,0,0,69,0,54,55,0,0,56,0,69,71,0,0,0,0,71,71,72,58,0,0,0,70,0,0,68,108,0,72,0,108,0,0,108,0,108,0,996,219,0,60,61,62,63,64,65,0,0,0,0,69,0,57,0,70,70,0,93,0,69,0,276,0,64,0,94,95,96,97,98,99,100,101,72,0,58,0,0,0,0,0,0,1,0,0,348,4,327,349,328,329,68,539,362,363,219,67,60,61,62,63,64,65,0,0,364,365,363,366,0,367,682,368,0,0,70,0,0,683,684,0,685,369,686,646,687,0,5,71,67,0,69,280,281,0,688,68,70,0,70,0,0,0,0,70,0,282,68,0,694,70,0,70,0,0,0,0,70,0,0,71,71,84,70,85,86,70,0,0,0,70,0,356,357,310,311,0,0,0,0,87,0,361,0,88,89,90,91,92,0,72,361,68,0,312,313,0,69,0,108,108,0,0,0,0,0,0,0,0,0,84,0,830,86,0,108,70,0,0,0,0,71,0,72,70,0,0,0,87,0,0,0,88,89,90,91,92,0,0,0,0,0,69,71,0,71,0,0,0,0,71,0,0,69,0,0,71,84,71,947,86,0,108,71,0,70,0,0,0,71,0,0,71,0,70,87,71,0,0,88,89,90,91,92,0,785,786,787,0,789,790,0,363,0,794,795,0,0,69,0,0,363,0,364,365,0,366,0,367,68,368,0,364,365,0,366,0,367,0,368,369,0,71,0,0,190,0,0,0,369,71,64,0,94,95,96,97,98,99,100,101,0,68,0,0,0,70,0,0,0,0,0,0,0,842,843,0,0,0,0,0,0,0,847,0,849,0,0,0,0,0,71,0,93,0,857,858,859,860,64,71,94,95,96,97,98,99,100,101,0,0,0,0,0,876,0,0,0,0,511,512,513,514,0,0,515,0,516,517,518,519,520,70,0,0,69,0,521,522,0,93,0,0,0,0,0,64,0,94,95,96,97,98,99,100,101,0,0,0,0,0,0,0,523,0,524,0,0,69,525,906,84,71,949,86,908,0,70,0,0,0,0,0,0,914,915,0,0,70,87,0,0,0,88,89,90,91,92,0,0,0,927,0,0,641,0,526,0,527,935,0,937,0,939,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,70,0,0,357,0,71,0,0,0,0,642,643,644,0,528,965,0,968,0,529,971,361,0,973,0,0,0,0,978,979,0,0,0,0,530,531,532,0,0,533,0,0,0,0,0,0,0,0,0,0,71,0,645,993,0,0,0,0,0,997,0,71,0,0,1001,0,0,0,0,0,0,0,1004,1005,0,0,0,0,1009,0,1011,1012,0,0,0,0,0,0,1017,0,0,0,0,0,0,0,1022,0,0,1024,0,93,0,1026,0,0,71,64,0,94,95,96,97,98,99,100,101,70,1032,0,1033,0,1035,109,0,110,1038,0,111,0,0,363,0,1040,0,0,0,112,1042,0,0,0,364,365,0,366,0,367,0,368,70,0,0,0,0,0,0,0,0,369,113,825,826,0,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,0,140,141,142,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,71,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,143,144,145,57,0,0,0,0,0,0,0,0,0,0,71,0,146,109,0,110,0,0,111,0,0,0,58,0,0,0,0,112,213,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,0,0,0,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,0,140,141,142,0,0,0,0,0,0,0,0,0,0,511,512,513,514,0,0,515,0,516,517,518,519,520,0,675,676,677,0,521,522,678,0,0,0,0,0,143,144,145,57,0,0,679,680,0,0,0,0,0,1015,1016,0,146,109,523,110,524,0,111,0,525,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,0,0,650,0,0,114,526,115,527,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,0,140,141,142,0,0,528,0,0,0,0,529,0,0,0,0,0,0,0,0,681,363,0,0,0,682,530,531,532,0,0,533,683,684,0,685,0,686,0,687,143,144,145,57,93,0,0,0,0,688,0,689,0,0,0,0,146,109,0,110,0,0,111,0,0,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,0,0,0,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,800,140,141,142,0,0,0,0,511,512,513,514,0,0,515,0,516,517,518,519,520,0,0,0,0,0,521,522,0,0,0,0,0,0,0,0,0,0,0,0,143,144,145,57,0,0,0,0,0,0,0,0,523,0,524,0,146,109,525,110,0,0,111,0,0,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,526,0,527,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,276,0,56,139,528,140,141,142,0,529,0,0,675,676,677,0,0,0,678,0,0,675,676,677,530,531,532,678,0,533,679,680,0,0,0,0,0,0,0,679,680,0,93,0,143,144,145,57,0,0,0,0,0,0,0,511,512,513,514,0,146,515,0,516,517,518,519,520,0,0,58,280,281,521,522,0,0,0,147,0,148,0,0,0,0,282,0,0,0,0,149,150,60,61,62,63,64,65,0,523,0,524,0,0,0,525,0,0,0,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,274,0,312,313,0,275,0,276,0,681,363,526,0,527,682,0,0,0,681,363,0,683,684,682,685,277,686,0,687,0,683,684,0,685,0,686,0,687,688,0,802,0,675,676,677,0,752,688,678,1034,0,0,528,0,278,279,0,529,0,0,679,680,675,676,677,0,910,0,678,0,0,0,530,531,532,0,0,533,280,281,679,680,0,0,0,0,0,0,0,0,645,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,675,676,677,0,0,0,678,0,0,675,676,677,0,911,0,678,312,313,679,680,0,0,0,0,0,0,0,679,680,0,0,0,0,0,675,676,677,988,957,0,678,0,0,0,0,0,681,363,917,918,0,682,679,680,675,676,677,0,683,684,678,685,0,686,0,687,681,363,0,0,0,682,679,680,0,688,0,0,683,684,0,685,0,686,0,687,356,357,0,0,0,0,359,0,0,688,0,0,0,0,0,0,0,0,360,361,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,681,363,0,0,0,682,0,0,276,681,363,0,683,684,682,685,0,686,0,687,0,683,684,0,685,0,686,0,687,688,0,0,0,0,681,363,0,0,688,682,0,0,0,0,0,0,683,684,0,685,0,686,0,687,681,363,0,274,0,682,0,0,275,688,276,0,683,684,0,685,0,686,0,687,0,0,0,0,0,280,281,0,277,688,0,0,362,363,0,0,0,0,0,282,0,0,0,0,364,365,0,366,0,367,0,368,0,0,0,0,0,278,279,302,0,369,0,306,307,308,309,310,311,0,0,0,0,0,0,0,276,0,0,0,0,280,281,0,0,0,0,312,313,0,0,0,0,0,277,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,280,281,0,277,0,0,0,1006,0,0,1007,0,0,0,282,283,284,285,286,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,276,0,0,0,0,280,281,0,0,0,0,312,313,0,0,0,0,0,277,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,280,281,0,277,0,0,0,784,0,0,0,0,0,0,282,283,284,285,286,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,302,303,0,0,306,307,308,309,310,311,0,0,0,0,0,0,0,276,0,0,0,0,280,281,0,0,0,0,312,313,0,0,0,0,0,277,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,280,281,0,277,0,0,0,931,0,0,0,0,0,0,282,283,284,285,286,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,302,0,0,0,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,312,313,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,932,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,976,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,877,878,0,0,0,0,277,0,0,1036,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,696,0,0,0,879,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,697,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,702,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,397,0,883,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,398,399,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,712,713,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,767,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,768,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,880,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,962,881,0,0,0,0,0,0,312,313,963,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,982,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,320,276,0,0,0,983,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,392,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,395,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,409,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,410,0,0,312,313,428,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,506,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,599,276,0,0,0,0,587,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,601,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,698,0,0,0,0,701,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,312,313,709,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,399,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,719,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,762,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,771,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,776,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,777,767,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,819,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,821,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,868,276,0,0,0,837,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,882,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,886,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,887,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,888,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,889,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,312,313,890,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,312,313,891,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,892,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,920,0,894,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,930,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,955,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,956,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,980,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,981,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,984,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,999,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,1021,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1023,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,1025,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,1028,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,1029,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,1041,1030,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,274,0,0,0,0,0,0,276,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,277,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,0,274,0,0,0,278,279,0,276,0,0,312,313,0,0,0,0,0,0,0,0,0,0,0,0,0,277,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,278,279,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,274,280,281,0,0,0,0,276,0,0,0,0,0,312,313,282,283,284,285,286,287,288,289,290,291,277,293,0,0,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,278,279,0,276,0,0,0,0,0,312,313,0,0,0,0,0,0,0,0,0,0,277,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,0,0,278,279,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,280,281,276,0,0,0,0,0,0,0,0,0,312,313,282,283,284,285,286,0,277,0,0,0,0,0,0,0,0,0,0,0,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,0,0,0,0,0,0,0,0,0,0,0,0,0,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,312,313";
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
  public static final String yyCheckString = "48,480,139,145,78,53,192,641,662,13,328,36,144,13,13,113,162,331,651,31,13,167,168,184,164,21,112,13,12,177,178,179,17,181,182,12,31,27,27,59,59,139,12,45,49,33,312,145,30,27,146,12,59,13,146,151,180,149,13,46,146,21,110,111,112,18,44,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,114,27,12,1,80,81,4,5,84,146,86,13,88,89,90,91,92,180,92,3,4,5,118,7,34,17,180,19,20,280,90,2,0,170,180,172,34,146,146,146,395,9,149,35,104,43,183,39,40,41,42,43,146,149,13,47,27,186,149,13,180,146,313,137,48,0,143,146,146,17,147,146,173,13,45,146,151,75,253,254,487,47,159,149,161,260,160,180,164,173,149,180,180,496,171,171,169,173,180,149,275,351,146,33,146,827,180,78,182,183,184,185,186,187,12,141,192,13,13,195,196,258,13,846,867,312,18,180,204,205,206,207,18,180,30,272,30,274,275,30,277,278,279,33,146,18,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,410,397,406,900,354,33,402,17,12,19,261,373,146,180,415,174,175,146,761,186,33,188,189,190,191,192,193,194,195,144,143,146,428,395,180,176,177,178,179,180,18,187,17,17,19,19,159,359,161,18,362,363,364,415,17,367,368,369,171,421,18,18,17,180,474,188,189,190,191,192,193,194,195,33,18,146,146,146,143,146,146,17,394,46,118,146,398,399,18,401,17,403,157,405,159,407,161,409,146,511,512,513,414,33,90,417,171,18,420,521,522,423,562,425,426,149,172,18,104,408,482,1018,146,18,375,376,532,146,19,180,143,13,383,384,385,18,146,92,17,149,67,14,15,16,17,17,159,19,161,173,90,33,17,404,173,406,329,180,171,32,33,88,89,149,104,92,478,46,146,17,532,422,484,485,149,149,514,146,490,17,530,19,137,143,13,497,17,147,146,146,17,17,118,90,604,13,17,157,344,159,91,161,146,515,33,160,146,104,520,952,137,171,524,525,98,33,528,529,19,531,146,533,12,149,475,180,477,182,183,184,185,186,187,160,523,146,56,57,165,543,544,545,133,547,548,146,118,62,63,553,554,146,149,180,558,182,183,184,185,186,187,88,89,146,17,92,19,27,12,142,0,356,357,358,146,360,361,149,590,9,365,366,13,27,44,146,371,31,149,601,91,180,0,37,14,15,16,17,610,172,44,9,33,180,36,715,118,180,670,13,137,562,32,33,180,13,726,180,630,492,624,37,634,627,628,629,31,631,632,33,13,132,133,160,146,33,587,149,138,139,140,691,102,149,14,15,16,17,13,17,33,663,498,180,500,182,183,184,185,186,187,771,32,33,776,777,678,12,514,681,682,683,180,17,686,687,688,13,17,13,19,693,27,13,696,92,31,13,700,701,13,703,37,705,180,707,46,33,710,44,712,713,17,33,19,717,13,33,13,721,33,662,13,180,499,727,501,502,503,504,505,65,142,67,149,146,33,739,33,172,80,143,33,27,84,147,86,31,88,89,90,91,92,37,88,89,173,159,92,161,44,180,172,176,177,178,179,769,180,171,180,17,144,19,146,14,15,16,17,781,782,783,142,724,180,90,498,728,500,50,731,180,733,32,33,100,18,180,800,104,636,12,888,137,180,27,642,643,644,31,804,805,172,649,808,37,12,180,102,760,180,921,44,17,164,90,160,36,27,46,118,171,31,173,836,100,18,149,37,104,149,173,844,12,838,44,180,12,182,183,184,185,186,187,856,195,196,49,21,12,59,59,17,865,204,205,206,207,14,15,872,20,146,20,20,877,878,17,880,27,882,118,180,18,55,887,32,33,146,17,892,893,27,17,20,51,31,27,9,901,902,100,37,142,113,114,742,743,743,44,851,905,351,754,27,917,43,31,958,959,140,36,697,698,383,192,867,702,930,36,139,46,75,172,143,144,145,34,33,899,615,180,732,1031,3,833,940,6,7,8,9,10,11,162,13,492,897,898,167,168,758,963,737,523,966,80,81,408,943,84,972,86,913,88,89,90,91,92,980,715,982,531,424,985,14,15,16,3,18,48,20,7,8,401,10,11,142,143,113,114,21,147,32,33,921,1007,27,-1,154,155,31,157,-1,159,-1,161,37,27,-1,960,-1,31,-1,44,139,171,1028,37,143,144,145,-1,48,-1,44,375,376,47,-1,-1,36,-1,-1,-1,384,385,-1,162,989,164,-1,-1,167,168,-1,-1,171,-1,173,27,-1,-1,837,31,404,-1,406,17,27,37,38,280,31,-1,-1,-1,44,192,37,38,195,196,422,27,-1,44,-1,31,-1,204,205,206,207,37,176,177,178,179,180,-1,44,-1,-1,312,313,-1,-1,-1,-1,-1,-1,886,142,143,-1,-1,-1,113,114,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,475,-1,477,88,89,-1,171,92,139,-1,-1,36,143,144,145,-1,-1,-1,-1,-1,17,-1,19,20,-1,-1,-1,-1,-1,373,280,162,-1,-1,-1,-1,167,168,35,-1,-1,-1,39,40,41,42,43,-1,-1,-1,-1,395,137,397,-1,-1,-1,-1,402,-1,-1,-1,312,313,408,-1,410,-1,-1,-1,-1,415,15,-1,-1,160,-1,421,-1,-1,424,-1,-1,-1,428,-1,-1,-1,113,114,33,176,177,178,179,180,-1,182,183,184,185,186,187,-1,-1,14,15,-1,-1,-1,-1,-1,587,-1,139,-1,-1,-1,143,144,145,-1,-1,373,33,375,376,-1,-1,-1,474,-1,-1,383,384,385,-1,162,482,-1,-1,-1,167,168,-1,395,-1,397,17,280,-1,-1,402,-1,404,-1,406,-1,408,-1,410,-1,-1,-1,-1,415,-1,-1,-1,-1,-1,421,422,-1,424,-1,173,-1,428,523,-1,312,313,180,-1,-1,-1,-1,532,186,-1,188,189,190,191,192,193,194,195,-1,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,88,89,-1,-1,92,474,475,36,477,171,-1,-1,-1,482,-1,143,-1,-1,-1,147,-1,-1,-1,373,-1,-1,154,155,-1,157,724,159,-1,161,728,-1,280,731,-1,733,-1,604,-1,171,-1,395,-1,397,-1,137,-1,-1,402,-1,523,-1,-1,-1,408,-1,410,-1,-1,532,-1,415,-1,-1,312,313,-1,421,160,-1,424,-1,-1,-1,428,-1,-1,-1,-1,-1,113,114,-1,-1,-1,-1,-1,-1,180,562,182,183,184,185,186,187,-1,-1,-1,-1,17,-1,-1,670,-1,-1,139,-1,-1,36,143,144,145,-1,587,-1,-1,-1,-1,17,474,19,20,-1,-1,373,-1,-1,482,162,-1,604,-1,-1,167,168,-1,35,-1,-1,-1,39,40,41,42,43,-1,395,715,397,67,-1,-1,-1,402,-1,-1,17,-1,726,408,-1,410,-1,-1,-1,-1,415,-1,523,-1,88,89,421,-1,92,424,-1,532,-1,428,-1,-1,-1,-1,350,113,114,-1,-1,14,15,16,-1,-1,-1,20,670,-1,897,898,-1,-1,-1,771,65,-1,67,32,33,-1,-1,139,-1,-1,913,143,144,145,-1,137,-1,33,-1,-1,-1,474,-1,88,89,-1,-1,92,-1,482,162,-1,-1,-1,-1,167,168,715,160,-1,-1,-1,280,-1,-1,604,724,-1,726,-1,728,-1,-1,731,-1,733,-1,960,180,-1,182,183,184,185,186,187,-1,-1,-1,-1,523,-1,137,-1,312,313,-1,180,-1,532,-1,19,-1,186,-1,188,189,190,191,192,193,194,195,771,-1,160,-1,-1,-1,-1,-1,-1,3,-1,-1,6,7,8,9,10,11,670,13,142,143,180,888,182,183,184,185,186,187,-1,-1,154,155,143,157,-1,159,147,161,-1,-1,373,-1,-1,154,155,-1,157,171,159,514,161,-1,48,280,921,-1,604,90,91,-1,171,715,395,-1,397,-1,-1,-1,-1,402,-1,104,726,-1,539,408,-1,410,-1,-1,-1,-1,415,-1,-1,312,313,17,421,19,20,424,-1,-1,-1,428,-1,14,15,132,133,-1,-1,-1,-1,35,-1,33,-1,39,40,41,42,43,-1,888,33,771,-1,152,153,-1,670,-1,897,898,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,-1,19,20,-1,913,474,-1,-1,-1,-1,373,-1,921,482,-1,-1,-1,35,-1,-1,-1,39,40,41,42,43,-1,-1,-1,-1,-1,715,395,-1,397,-1,-1,-1,-1,402,-1,-1,726,-1,-1,408,17,410,19,20,-1,960,415,-1,523,-1,-1,-1,421,-1,-1,424,-1,532,35,428,-1,-1,39,40,41,42,43,-1,675,676,677,-1,679,680,-1,143,-1,684,685,-1,-1,771,-1,-1,143,-1,154,155,-1,157,-1,159,888,161,-1,154,155,-1,157,-1,159,-1,161,171,-1,474,-1,-1,180,-1,-1,-1,171,482,186,-1,188,189,190,191,192,193,194,195,-1,921,-1,-1,-1,604,-1,-1,-1,-1,-1,-1,-1,744,745,-1,-1,-1,-1,-1,-1,-1,753,-1,755,-1,-1,-1,-1,-1,523,-1,180,-1,765,766,767,768,186,532,188,189,190,191,192,193,194,195,-1,-1,-1,-1,-1,784,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,670,-1,-1,888,-1,32,33,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,-1,-1,-1,58,-1,60,-1,-1,921,64,840,17,604,19,20,845,-1,715,-1,-1,-1,-1,-1,-1,854,855,-1,-1,726,35,-1,-1,-1,39,40,41,42,43,-1,-1,-1,871,-1,-1,99,-1,101,-1,103,879,-1,881,-1,883,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,771,-1,-1,15,-1,670,-1,-1,-1,-1,138,139,140,-1,142,918,-1,920,-1,147,923,33,-1,926,-1,-1,-1,-1,931,932,-1,-1,-1,-1,162,163,164,-1,-1,167,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,715,-1,180,956,-1,-1,-1,-1,-1,962,-1,726,-1,-1,967,-1,-1,-1,-1,-1,-1,-1,975,976,-1,-1,-1,-1,981,-1,983,984,-1,-1,-1,-1,-1,-1,991,-1,-1,-1,-1,-1,-1,-1,999,-1,-1,1002,-1,180,-1,1006,-1,-1,771,186,-1,188,189,190,191,192,193,194,195,888,1021,-1,1023,-1,1025,15,-1,17,1029,-1,20,-1,-1,143,-1,1036,-1,-1,-1,29,1041,-1,-1,-1,154,155,-1,157,-1,159,-1,161,921,-1,-1,-1,-1,-1,-1,-1,-1,171,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,888,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,921,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,30,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,53,54,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,94,95,96,97,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,58,-1,60,-1,150,15,64,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,101,-1,103,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,19,-1,92,93,142,95,96,97,-1,147,-1,-1,14,15,16,-1,-1,-1,20,-1,-1,14,15,16,162,163,164,20,-1,167,32,33,-1,-1,-1,-1,-1,-1,-1,32,33,-1,180,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,150,20,-1,22,23,24,25,26,-1,-1,160,90,91,32,33,-1,-1,-1,168,-1,170,-1,-1,-1,-1,104,-1,-1,-1,-1,180,181,182,183,184,185,186,187,-1,58,-1,60,-1,-1,-1,64,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,152,153,-1,17,-1,19,-1,142,143,101,-1,103,147,-1,-1,-1,142,143,-1,154,155,147,157,37,159,-1,161,-1,154,155,-1,157,-1,159,-1,161,171,-1,173,-1,14,15,16,-1,18,171,20,173,-1,-1,142,-1,66,67,-1,147,-1,-1,32,33,14,15,16,-1,18,-1,20,-1,-1,-1,162,163,164,-1,-1,167,90,91,32,33,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,14,15,16,-1,-1,-1,20,-1,-1,14,15,16,-1,18,-1,20,152,153,32,33,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,14,15,16,173,18,-1,20,-1,-1,-1,-1,-1,142,143,62,63,-1,147,32,33,14,15,16,-1,154,155,20,157,-1,159,-1,161,142,143,-1,-1,-1,147,32,33,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,14,15,-1,-1,-1,-1,20,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,19,142,143,-1,154,155,147,157,-1,159,-1,161,-1,154,155,-1,157,-1,159,-1,161,171,-1,-1,-1,-1,142,143,-1,-1,171,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,12,-1,147,-1,-1,17,171,19,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,90,91,-1,37,171,-1,-1,142,143,-1,-1,-1,-1,-1,104,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,66,67,124,-1,171,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
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
     292,   293,   294,   318,   321,    33,    12,   169,    12,   244,
      12,    34,   180,   235,    17,    19,    20,    35,    39,    40,
      41,    42,    43,   180,   188,   189,   190,   191,   192,   193,
     194,   195,   246,   247,   248,   249,   250,   251,   294,    15,
      17,    20,    29,    52,    58,    60,    64,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    88,    89,    93,
      95,    96,    97,   134,   135,   136,   150,   168,   170,   180,
     181,   288,   289,   290,   291,   292,   293,   294,   297,   299,
     302,   303,    17,    19,   149,   265,   288,    17,    17,    19,
      12,   149,    12,   149,   257,   172,   180,    14,    15,    16,
      17,    32,    33,   142,   172,   180,   217,   234,   235,   246,
     180,   246,   252,   253,   246,    27,    44,   246,   180,   246,
     246,   246,   246,   246,    27,    31,    37,    44,    90,   104,
     288,   287,   288,    30,   287,   288,   304,   305,    67,   180,
     317,   318,   320,   322,   323,   324,   180,   258,   262,   295,
     296,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,    17,    17,   317,   324,   329,    17,   248,
      17,    17,   317,   319,   325,   326,   327,   328,   325,   322,
     287,   288,    17,    98,    12,    17,    19,    37,    66,    67,
      90,    91,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   152,   153,   266,   319,   246,   249,   180,   268,
      18,   319,   319,   288,   246,   288,   246,     8,    10,    11,
     240,   241,   242,   255,   259,   274,   284,    12,   180,   218,
     219,   235,   218,   218,   234,   218,   218,   288,     6,     9,
      13,   220,   221,   241,    17,    19,    14,    15,    16,    20,
      32,    33,   142,   143,   154,   155,   157,   159,   161,   171,
     229,    13,   235,    47,   254,    45,   149,   253,   254,    18,
     246,   246,    21,    36,    38,    38,   246,   246,   246,   246,
     180,   180,    18,    21,   146,    31,    30,    31,    86,    87,
      30,   146,    31,   118,   149,    13,   149,    59,   146,    61,
     149,   287,   288,   287,    13,   146,   288,    17,   287,   247,
     118,   146,   149,    13,   146,    13,    13,   151,    31,   288,
     288,   287,   288,   288,   288,   288,   180,   321,    92,   180,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   317,
     323,   180,   321,    18,   146,   149,   180,   149,    49,   146,
     269,   270,   146,    18,    12,    12,   245,   275,   276,   276,
      47,   245,   285,   286,   173,   240,   172,   118,   146,    13,
      33,    13,    13,    13,    13,    13,   145,   180,   223,   180,
     222,    14,    15,    16,    17,    20,    22,    23,    24,    25,
      26,    32,    33,    58,    60,    64,   101,   103,   142,   147,
     162,   163,   164,   167,   225,   247,   310,   311,   313,    13,
     221,   287,   180,   217,   217,   217,   288,   217,   217,   288,
     230,   288,   288,   217,   217,   288,   288,   288,   217,   317,
     246,   246,   252,   246,   246,   288,   317,   323,   325,   288,
     288,   288,   305,   325,   288,   246,   288,   288,   296,   288,
     300,   301,   319,    18,    18,   288,   324,   146,   288,    18,
     146,   288,   317,   246,   288,   326,   288,   288,   325,    18,
      18,    86,   319,   246,    17,    19,   263,   246,   288,   180,
      50,   272,   317,   288,   288,   180,   276,   288,   180,   308,
     286,   240,   288,   219,   217,   219,   235,   217,   217,   217,
     142,   217,   217,   218,    12,   180,    12,   180,   218,   218,
     218,    99,   138,   139,   140,   180,   225,   227,   228,   234,
      55,   279,   280,   288,   316,   288,   218,   218,   295,   288,
     306,   288,    17,    17,    19,   288,   288,   287,   288,   306,
     165,   180,   317,   322,   288,    14,    15,    16,    20,    32,
      33,   142,   147,   154,   155,   157,   159,   161,   171,   173,
     229,    17,    19,   100,   225,    18,    32,   158,   145,   144,
     146,    28,   156,    46,   173,    13,    21,    13,    30,    30,
      13,    30,    62,    63,   298,   146,   173,    27,   246,    18,
     288,    13,   151,   288,   149,   319,    17,    46,   149,   288,
      18,   149,   265,   149,   173,    18,   288,    13,   288,    12,
     224,   225,   234,    12,    13,    13,    13,   307,   308,   235,
     235,   235,    18,    13,    33,    13,    56,    57,   281,   282,
     283,   269,    21,    21,    13,    13,    59,    27,    61,    31,
     173,   149,   247,   309,   310,   288,   145,   148,    13,   173,
     317,    12,    59,    59,   163,   225,   225,   225,   288,   225,
     225,   288,   288,   288,   225,   225,   288,   288,   288,   287,
      94,   288,   173,   288,   217,   217,   288,   288,   217,   288,
     288,   288,   288,   288,   288,    65,   301,   288,    18,    18,
     288,    18,   246,    18,   319,    53,    54,   264,   288,   246,
      19,   246,   277,   267,   268,   246,    17,   145,   217,   288,
      13,   224,   225,   225,    20,    13,   146,   225,   228,   225,
     282,   146,   247,   272,    27,   141,    20,   225,   225,   225,
     225,   288,   314,   315,   319,   100,    18,    33,    18,   218,
     218,    17,   118,   288,   288,   288,   225,    31,    32,   158,
      31,   145,    31,   156,    18,   288,    21,    28,    31,    21,
      30,    30,    61,    27,   151,    18,   269,   102,   102,   180,
     279,    59,   100,   288,   180,   217,   225,   288,   225,   308,
      18,    18,   247,   149,   225,   225,   288,    62,    63,   312,
      27,   146,   173,    27,   310,    13,    13,   225,   226,   288,
      38,   163,   163,   288,   288,   225,   288,   225,   288,   225,
     217,   288,   325,   288,   288,   271,   272,    19,   246,    19,
     246,   263,   269,   288,   288,    18,    21,    18,    17,    17,
     149,   246,    21,    31,   288,   225,    62,    63,   225,    65,
     315,   225,    20,   225,    18,   146,   163,   288,   225,   225,
      32,    21,    31,   145,   148,    13,   144,   298,   173,    51,
     273,    46,   272,   225,   287,   287,   246,   225,   288,    61,
     288,   225,    27,   288,   225,   225,   163,   166,   288,   225,
     288,   225,   225,   288,   247,    53,    54,   225,   278,    18,
      18,    21,   225,    61,   225,    21,   225,   288,    31,   145,
     144,   269,   225,   225,   173,   225,   163,   288,   225,   271,
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
     174,   180,    12,   172,   240,   173,    -1,   174,   180,   169,
     180,    12,   172,   240,   173,    -1,   175,   180,    12,   217,
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
         0,   558,   558,   566,   572,   583,   584,   585,   586,   587,
     591,   605,   638,   666,   700,   707,   715,   721,   727,   736,
     745,   751,   757,   763,   778,   785,   800,   818,   825,   832,
     842,   856,   865,   875,   885,   895,   903,   911,   922,   935,
     945,   953,   964,   970,   976,   982,   999,  1012,  1024,  1030,
    1046,  1050,  1057,  1061,  1068,  1085,  1111,  1115,  1122,  1126,
    1133,  1138,  1143,  1148,  1153,  1159,  1176,  1199,  1206,  1213,
    1220,  1227,  1242,  1249,  1264,  1280,  1287,  1294,  1301,  1313,
    1327,  1338,  1352,  1363,  1377,  1394,  1411,  1427,  1446,  1453,
    1464,  1471,  1479,  1487,  1498,  1511,  1524,  1538,  1551,  1563,
    1572,  1582,  1586,  1594,  1614,  1618,  1625,  1629,  1633,  1644,
    1648,  1665,  1702,  1712,  1728,  1744,  1753,  1764,  1775,  1790,
    1796,  1805,  1811,  1820,  1826,  1832,  1841,  1847,  1860,  1885,
    1896,  1916,  1931,  1952,  1958,  1975,  1996,  2017,  2023,  2032,
    2045,  2055,  2071,  2091,  2097,  2109,  2125,  2129,  2133,  2140,
    2146,  2155,  2159,  2163,  2167,  2171,  2181,  2195,  2205,  2217,
    2232,  2238,  2247,  2267,  2288,  2317,  2364,  2371,  2378,  2385,
    2393,  2402,  2409,  2413,  2418,  2430,  2464,  2492,  2499,  2506,
    2513,  2520,  2528,  2536,  2548,  2568,  2574,  2580,  2589,  2593,
    2597,  2601,  2605,  2609,  2613,  2617,  2624,  2628,  2635,  2644,
    2656,  2665,  2677,  2683,  2692,  2700,  2710,  2723,  2751,  2763,
    2793,  2799,  2809,  2819,  2843,  2869,  2889,  2914,  2928,  2957,
    2963,  2972,  2996,  3006,  3028,  3032,  3039,  3049,  3053,  3057,
    3064,  3068,  3075,  3082,  3093,  3096,  3103,  3113,  3126,  3127,
    3131,  3135,  3136,  3140,  3150,  3158,  3164,  3177,  3198,  3204,
    3213,  3240,  3264,  3276,  3288,  3300,  3314,  3318,  3322,  3329,
    3330,  3334,  3338,  3344,  3364,  3373,  3382,  3391,  3403,  3407,
    3436,  3442,  3458,  3466,  3475,  3481,  3495,  3501,  3544,  3556,
    3561,  3565,  3570,  3582,  3588,  3592,  3597,  3602,  3607,  3611,
    3615,  3619,  3623,  3627,  3631,  3636,  3640,  3644,  3648,  3653,
    3660,  3664,  3668,  3672,  3677,  3685,  3693,  3701,  3710,  3717,
    3724,  3731,  3740,  3748,  3764,  3771,  3778,  3787,  3792,  3799,
    3806,  3815,  3822,  3836,  3851,  3861,  3871,  3885,  3902,  3915,
    3925,  3936,  3951,  3955,  3962,  3973,  3980,  3986,  3993,  4007,
    4016,  4027,  4033,  4042,  4043,  4047,  4079,  4086,  4100,  4107,
    4118,  4124,  4135,  4151,  4157,  4163,  4169,  4175,  4181,  4187,
    4193,  4199,  4205,  4211,  4217,  4223,  4229,  4235,  4241,  4247,
    4253,  4259,  4265,  4274,  4282,  4290,  4298,  4306,  4314,  4322,
    4330,  4338,  4346,  4354,  4362,  4370,  4378,  4386,  4394,  4402,
    4410,  4418,  4426,  4434,  4442,  4450,  4458,  4466,  4474,  4482,
    4490,  4498,  4506,  4514,  4522,  4530,  4541,  4547,  4555,  4567,
    4580,  4596,  4602,  4611,  4628,  4644,  4667,  4674,  4692,  4707,
    4716,  4728,  4739,  4753,  4760,  4771,  4777,  4788,  4800,  4818,
    4819,  4823,  4827,  4844,  4855,  4869,  4875,  4884,  4898,  4908,
    4918,  4923,  4928,  4933,  4938,  4943,  4954,  4955,  4959,  4970,
    4981,  4987,  4996,  4997,  5001,  5012,  5023,  5030
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

  private static final int yylast_ = 11771;
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
/* Line 51 of "src/main/bison/cml.y"  */

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

  private LexNameToken extractNameFromUNDERNAMEToken(CmlLexeme mkUnder)
  {
      String nameString = mkUnder.getValue().substring(3);

      LexNameToken name = null;
      if(nameString.matches(".+['`''.'].+")){
          String[] tokens = nameString.split("['`''.']");
          name = new LexNameToken(tokens[0],
                                  tokens[1],
                                  extractLexLocation(mkUnder),
                                  false,
                                  true);
      }
      else
          name = new LexNameToken("Default",
                                  nameString,
                                  extractLexLocation(mkUnder),
                                  false,
                                  true);
      return name;
  }

  private AAccessSpecifierAccessSpecifier getDefaultAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc)
  {
    /* return new AAccessSpecifierAccessSpecifier(new APublicAccess(), */
    /*                             (isStatic ? new TStatic() : null), */
    /*                             (isAsync ? new TAsync() : null),loc); */

    return new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                (isStatic ? new TStatic() : null),
                                (isAsync ? new TAsync() : null));

  }

  private AAccessSpecifierAccessSpecifier getPrivateAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc)
  {
    /* return new AAccessSpecifierAccessSpecifier(new APrivateAccess(), */
    /*                             (isStatic ? new TStatic() : null), */
    /*                             (isAsync ? new TAsync() : null),loc); */
    return new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                (isStatic ? new TStatic() : null),
                                (isAsync ? new TAsync() : null));
  }

  private LexToken extractLexToken(CmlLexeme lexeme)
  {
    LexLocation loc = extractLexLocation(lexeme);
    VDMToken tok = null;
    for(VDMToken t : VDMToken.values())
      {
        String tokenDisplay = t.toString();
        if (tokenDisplay != null && tokenDisplay.equals(lexeme.getValue())) { tok = t; break; }
      }
    if (tok == null) throw new RuntimeException("Cannot find VDM token for "+lexeme.getValue());
    return new LexToken(loc, tok);
  }

  private LexLocation extractLexLocation(CmlLexeme lexeme)
  {
    return new LexLocation(currentSource.toString(), "Default",
                           lexeme.getStartPos().line,
                           lexeme.getStartPos().column,
                           lexeme.getEndPos().line,
                           lexeme.getEndPos().column,
                           lexeme.getStartPos().offset,
                           lexeme.getEndPos().offset);
  }

  private LexLocation extractLexLocation(CmlLexeme start, CmlLexeme end)
  {
    return new LexLocation(currentSource.toString(), "Default",
                           start.getStartPos().line,
                           start.getStartPos().column,
                           end.getEndPos().line,
                           end.getEndPos().column,
                           start.getStartPos().offset,
                           end.getEndPos().offset);
  }

  private LexLocation extractLexLocation(CmlLexeme start, LexLocation end)
  {

    return new LexLocation(currentSource.toString(), "Default",
                           start.getStartPos().line, start.getStartPos().column,
                           end.endLine,
                           end.endPos,
                           start.getStartPos().offset,
                           end.endOffset);
  }

  private LexLocation extractLexLocation(LexLocation start, CmlLexeme end)
  {

    return new LexLocation(currentSource.toString(), "Default",
                           start.startLine, start.startPos,
                           end.getEndPos().line,
                           end.getEndPos().column,
                           start.startOffset,
                           end.getEndPos().offset);
  }

  private LexLocation extractLexLocation(LexLocation start, LexLocation end)
  {
    return new LexLocation(currentSource.toString(), "Default",
                           start.startLine, start.startPos,
                           end.endLine,
                           end.endPos,
                           start.startOffset,
                           end.endOffset);
  }

  private LexLocation combineLexLocation(LexLocation start, LexLocation end)
  {
    return extractLexLocation(start,end);
  }

  private LexLocation extractLastLexLocation(List<?> fields)
  {
    try {
      Object o = fields.get(0);
      Class<?> clz = o.getClass();

      Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});

      LexLocation candidate = (LexLocation)locMethod.invoke(o, null);
      for(Object p : fields) {
        LexLocation pLoc = (LexLocation)locMethod.invoke(o, null);
        if (pLoc.endOffset > candidate.endOffset)
          candidate = pLoc;
      }
      return candidate;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private LexLocation extractFirstLexLocation(List<?> fields)
  {
    try {
      Object o = fields.get(0);
      Class<?> clz = o.getClass();

      Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});
      LexLocation candidate = (LexLocation)locMethod.invoke(o, null);
      for(Object p : fields) {
        LexLocation pLoc = (LexLocation)locMethod.invoke(o, null);
        if (pLoc.startOffset < candidate.startOffset)
          candidate = pLoc;
      }
      return candidate;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static CmlParser newParserFromSource(PSource doc) throws FileNotFoundException
  {
    if (doc instanceof AFileSource) {
      AFileSource fs = (AFileSource)doc;
      File f= fs.getFile();
      FileReader reader = new FileReader(f);
      CmlLexer lexer = new CmlLexer(reader);
      CmlParser parser = new CmlParser(lexer);
      parser.setDocument(fs);
      return parser;
    }

    if (doc instanceof AInputStreamSource) {
      AInputStreamSource is = (AInputStreamSource)doc;
      InputStreamReader in = new InputStreamReader(is.getStream());
      CmlLexer lexer = new CmlLexer(in);
      CmlParser parser = new CmlParser(lexer);
      parser.setDocument(is);
      return parser;
    }
    return null;
  }

  private< T extends PPattern> LexLocation extractLexLeftMostFromPatterns(List<T> ptrns)
    {
      LexLocation candidate = ptrns.get(0).getLocation();
      for(PPattern p : ptrns) {
        if (p.getLocation().endOffset > candidate.endOffset)
          candidate = p.getLocation();
      }
      return candidate;
    }

  private List<LexIdentifierToken> convertPathListToIdentifiers(List<LexNameToken> pathList)
  {
    List<LexIdentifierToken> identifiers = new LinkedList<LexIdentifierToken>();
    for (LexNameToken name : pathList){
      if(name.explicit == true || !name.module.equals("Default"))
        throw new RuntimeException("A single expression declaration can only contain identifiers");
      identifiers.add(0,name.getIdentifier());
    }

    return identifiers;
  }

  private LexNameToken extractLexNameToken(CmlLexeme lexeme)
  {
    return new LexNameToken("Default", lexeme.getValue(), extractLexLocation(lexeme), false, true);
  }

  private LexNameToken extractLexNameToken(CmlLexeme lexeme, boolean old)
  {
    return new LexNameToken("Default", lexeme.getValue(), extractLexLocation(lexeme), old, true);
  }

  private LexNameToken extractLexNameToken(Object obj)
  {
    CmlLexeme lexeme = (CmlLexeme)obj;
    return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
  }

  private LexIdentifierToken extractLexIdentifierToken(Object obj)
  {
      return extractLexIdentifierToken((CmlLexeme)obj);
  }

  private LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme)
  {
    return new LexIdentifierToken(lexeme.getValue(), false, extractLexLocation(lexeme));
  }

  private LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme, boolean old)
  {
    return new LexIdentifierToken(lexeme.getValue(), old, extractLexLocation(lexeme));
  }

  private LexIdentifierToken extractLexIdentifierToken(LexNameToken name)
  {
    return new LexIdentifierToken(name.getName(), false, name.getLocation());
  }


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

  // *************************
  // *** PUBLIC OPERATIONS ***
  // *************************

  public void setDocument(PSource doc)
  {
    this.currentSource = doc;
  }

  public PSource getDocument()
  {
    return currentSource;
  }



/* Line 922 of cmlskeleton.java.m4  */
/* Line 9436 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


