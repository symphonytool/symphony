
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
/* Line 549 of "src/main/bison/cml.y"  */
    {
  List<SParagraphDefinition> paragraphs = (List<SParagraphDefinition>) ((yystack.valueAt (1-(1))));
  currentSource.setParagraphs(paragraphs);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 557 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> programParagraphList = new LinkedList<PDefinition>();
  programParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = programParagraphList;
};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 563 of "src/main/bison/cml.y"  */
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
/* Line 573 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 574 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 575 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 576 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 577 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 582 of "src/main/bison/cml.y"  */
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
/* Line 593 of "src/main/bison/cml.y"  */
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
/* Line 626 of "src/main/bison/cml.y"  */
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
/* Line 654 of "src/main/bison/cml.y"  */
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
/* Line 688 of "src/main/bison/cml.y"  */
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
/* Line 695 of "src/main/bison/cml.y"  */
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
/* Line 703 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASequentialCompositionProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 709 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AExternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 715 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 724 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  yyval = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 733 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (7-(1))));
  PProcess right = (PProcess)((yystack.valueAt (7-(7))));
  yyval = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (7-(3)))), (PExp)((yystack.valueAt (7-(5)))), right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 739 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 745 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 751 of "src/main/bison/cml.y"  */
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
/* Line 766 of "src/main/bison/cml.y"  */
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
/* Line 773 of "src/main/bison/cml.y"  */
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
/* Line 788 of "src/main/bison/cml.y"  */
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
/* Line 806 of "src/main/bison/cml.y"  */
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
/* Line 813 of "src/main/bison/cml.y"  */
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
/* Line 820 of "src/main/bison/cml.y"  */
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
/* Line 830 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8)))));
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (8-(2))));
  List<PExp> args = new LinkedList<PExp>();
  args.add((PExp)((yystack.valueAt (8-(7)))));
  PProcess proc = (PProcess)((yystack.valueAt (8-(4))));
  /* FIXME: (->AKM) process was a processDefinition */
  /* $$ = new AInstantiationProcess(location,  */
  /* 				 decls,  */
  /* 				 null, */
  /* 				 (AProcessDefinition)$proc,  */
  /* 				 args); */
};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 844 of "src/main/bison/cml.y"  */
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
/* Line 853 of "src/main/bison/cml.y"  */
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
/* Line 863 of "src/main/bison/cml.y"  */
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
/* Line 873 of "src/main/bison/cml.y"  */
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
/* Line 883 of "src/main/bison/cml.y"  */
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
/* Line 891 of "src/main/bison/cml.y"  */
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
/* Line 899 of "src/main/bison/cml.y"  */
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
/* Line 910 of "src/main/bison/cml.y"  */
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
/* Line 923 of "src/main/bison/cml.y"  */
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
/* Line 933 of "src/main/bison/cml.y"  */
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
/* Line 941 of "src/main/bison/cml.y"  */
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
/* Line 952 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 958 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 964 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 970 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 987 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 991 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 998 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = processParagraphList;
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1004 of "src/main/bison/cml.y"  */
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
/* Line 1020 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1024 of "src/main/bison/cml.y"  */
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
/* Line 1031 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1035 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1042 of "src/main/bison/cml.y"  */
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
/* Line 1059 of "src/main/bison/cml.y"  */
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
/* Line 1085 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1089 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1096 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{new Vector<ATypeSingleDeclaration>(), ((yystack.valueAt (1-(1))))};
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1100 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3))))};
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1107 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASkipAction(location);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1112 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AStopAction(location);
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1117 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AChaosAction(location);
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1122 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ADivAction(location);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1127 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
  yyval = new AWaitAction(location, exp);
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1133 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  PAction action = (PAction)((yystack.valueAt (3-(2))));
  yyval = new ABlockStatementAction(location, null, action);
};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1150 of "src/main/bison/cml.y"  */
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
/* Line 1173 of "src/main/bison/cml.y"  */
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
/* Line 1180 of "src/main/bison/cml.y"  */
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
/* Line 1187 of "src/main/bison/cml.y"  */
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
/* Line 1194 of "src/main/bison/cml.y"  */
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
/* Line 1201 of "src/main/bison/cml.y"  */
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
/* Line 1216 of "src/main/bison/cml.y"  */
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
/* Line 1223 of "src/main/bison/cml.y"  */
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
/* Line 1238 of "src/main/bison/cml.y"  */
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
/* Line 1254 of "src/main/bison/cml.y"  */
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
/* Line 1261 of "src/main/bison/cml.y"  */
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
/* Line 1268 of "src/main/bison/cml.y"  */
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
/* Line 1275 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PAction action = (PAction)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingAction(combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1287 of "src/main/bison/cml.y"  */
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
/* Line 1301 of "src/main/bison/cml.y"  */
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
/* Line 1312 of "src/main/bison/cml.y"  */
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
/* Line 1326 of "src/main/bison/cml.y"  */
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
/* Line 1337 of "src/main/bison/cml.y"  */
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
/* Line 1351 of "src/main/bison/cml.y"  */
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
/* Line 1368 of "src/main/bison/cml.y"  */
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
/* Line 1385 of "src/main/bison/cml.y"  */
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
/* Line 1401 of "src/main/bison/cml.y"  */
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
/* Line 1420 of "src/main/bison/cml.y"  */
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
/* Line 1427 of "src/main/bison/cml.y"  */
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
/* Line 1438 of "src/main/bison/cml.y"  */
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
/* Line 1445 of "src/main/bison/cml.y"  */
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
/* Line 1453 of "src/main/bison/cml.y"  */
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
/* Line 1461 of "src/main/bison/cml.y"  */
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
/* Line 1472 of "src/main/bison/cml.y"  */
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
/* Line 1485 of "src/main/bison/cml.y"  */
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
/* Line 1498 of "src/main/bison/cml.y"  */
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
/* Line 1512 of "src/main/bison/cml.y"  */
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
/* Line 1525 of "src/main/bison/cml.y"  */
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
/* Line 1537 of "src/main/bison/cml.y"  */
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
/* Line 1546 of "src/main/bison/cml.y"  */
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
/* Line 1556 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 102:
  if (yyn == 102)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1560 of "src/main/bison/cml.y"  */
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
/* Line 1568 of "src/main/bison/cml.y"  */
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
/* Line 1588 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 105:
  if (yyn == 105)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1592 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AMultipleGeneralAssignmentStatementAction(location,
						     (List<? extends ASingleGeneralAssignmentStatementAction>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1599 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1603 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 108:
  if (yyn == 108)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1607 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 109:
  if (yyn == 109)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1618 of "src/main/bison/cml.y"  */
    {
  yyval = new AReturnStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), null);
};
  break;
    

  case 110:
  if (yyn == 110)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1622 of "src/main/bison/cml.y"  */
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
/* Line 1639 of "src/main/bison/cml.y"  */
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
  //				  target.convertToStateDesignator(),
  //				  newExp.convertToName(),
  //				  args);
  yyval = stm;
};
  break;
    

  case 112:
  if (yyn == 112)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1676 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 113:
  if (yyn == 113)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1686 of "src/main/bison/cml.y"  */
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
/* Line 1702 of "src/main/bison/cml.y"  */
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
/* Line 1718 of "src/main/bison/cml.y"  */
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
/* Line 1727 of "src/main/bison/cml.y"  */
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
/* Line 1738 of "src/main/bison/cml.y"  */
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
/* Line 1749 of "src/main/bison/cml.y"  */
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
/* Line 1764 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = new LinkedList<PAction>();
  actionList.add((PAction)((yystack.valueAt (1-(1)))));
  yyval = actionList;
};
  break;
    

  case 120:
  if (yyn == 120)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1770 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = (List<PAction>)((yystack.valueAt (3-(1))));
  actionList.add((PAction)((yystack.valueAt (3-(3)))));
  yyval = actionList;
};
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1779 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add((PParametrisation)((yystack.valueAt (1-(1)))));
  yyval = plist;
};
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1785 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add(0,(PParametrisation)((yystack.valueAt (3-(3)))));
  yyval = plist;
};
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1794 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AValParametrisation(loc, decl);
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1800 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AResParametrisation(loc, decl);
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1806 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AVresParametrisation(loc, decl);
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1815 of "src/main/bison/cml.y"  */
    {
  yyval = new AEnumerationRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
							   (CmlLexeme)((yystack.valueAt (3-(3))))),
					(List<? extends ARenamePair>)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1821 of "src/main/bison/cml.y"  */
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
/* Line 1834 of "src/main/bison/cml.y"  */
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
/* Line 1859 of "src/main/bison/cml.y"  */
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
/* Line 1870 of "src/main/bison/cml.y"  */
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
/* Line 1890 of "src/main/bison/cml.y"  */
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
/* Line 1905 of "src/main/bison/cml.y"  */
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
/* Line 1926 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
  decls.add((AChannelNameDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 134:
  if (yyn == 134)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1932 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((AChannelNameDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1949 of "src/main/bison/cml.y"  */
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
/* Line 1970 of "src/main/bison/cml.y"  */
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
/* Line 1991 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = new Vector<ATypeSingleDeclaration>();
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 138:
  if (yyn == 138)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1997 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2006 of "src/main/bison/cml.y"  */
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
/* Line 2019 of "src/main/bison/cml.y"  */
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
/* Line 2029 of "src/main/bison/cml.y"  */
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
/* Line 2045 of "src/main/bison/cml.y"  */
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
/* Line 2065 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 144:
  if (yyn == 144)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2071 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(1))));
  defs.add((AChansetDefinition)((yystack.valueAt (2-(2)))));
  yyval = defs;
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2083 of "src/main/bison/cml.y"  */
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
/* Line 2099 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 147:
  if (yyn == 147)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2103 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2107 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2114 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2120 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  defs.add((PDefinition)((yystack.valueAt (2-(1)))));
  yyval = defs;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2129 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 152:
  if (yyn == 152)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2133 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 153:
  if (yyn == 153)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2137 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2141 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2145 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2155 of "src/main/bison/cml.y"  */
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
/* Line 2169 of "src/main/bison/cml.y"  */
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
/* Line 2179 of "src/main/bison/cml.y"  */
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
/* Line 2191 of "src/main/bison/cml.y"  */
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
/* Line 2206 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
  yyval = list;
};
  break;
    

  case 161:
  if (yyn == 161)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2212 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
  list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
  yyval = list;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2221 of "src/main/bison/cml.y"  */
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
/* Line 2241 of "src/main/bison/cml.y"  */
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
/* Line 2262 of "src/main/bison/cml.y"  */
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
/* Line 2291 of "src/main/bison/cml.y"  */
    {
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
							  null,
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
    



/* Line 374 of cmlskeleton.java.m4  */
/* Line 3388 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
       112,   780,  -146,  -133,   780,   705,  -123,   -72,   132,   112,
    -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  -655,   171,  -655,   -34,   274,  -146,  -655,  -655,
     295,  -133,  -655,   141,   780,  -655,  -655,  -655,   437,   705,
    -655,   335,   344,  -655,  -655,   780,   370,   294,  1848,  -655,
    2665,  -655,   326,  -655,  -655,  2665,   467,   576,  -655,  -655,
    -655,     6,  -655,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  -655,  -655,  -655,    20,  -655,  -655,   409,    22,
    -655,  1848,  1885,   274,  -655,  1848,   372,  1848,   311,  1848,
    1848,  1848,  1848,  1848,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  -655,   874,   -46,  -655,  -655,  -655,  -655,  -655,
    -655,  2665,  2665,  2257,   423,  1290,  2665,  2665,  2665,  2665,
    2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,
    2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,   480,   484,
     423,   140,   492,   499,   423,   423,   423,  2665,   529,  -655,
     454,  -655, 11225,  -655,  -655,  -655,  -655,  -655,  -655,  -655,
    -655,  -655,  -655,   423,  -655,  1848,   387,  5236,   423,   423,
    -655,  2665,  1848,  2665,  1848,   396,   792,   434,   434,   434,
     294,   434,   434,  2665,   972,   306,  1571,    -6,  -655,  1047,
     293,   874,  1597,  -655,   795,  1848,  1848,   496,   592,   711,
    1076,   600,   600,   600,  1848,  1848,  1848,  1848,   478,   486,
    5358,     7,  5481,  -655,    59,  4501,   190,   618,  -655,  -655,
     -23,  -655,  -655,   657,  -655,  -655,    23,  -655,  -655,   -19,
    -655,  5603,  5725,   577,   577,   577,   577, 11676,   577,   577,
     577,   577,   577,   577,   577,   577,   577,   577,   577,   577,
     577,   577,  1668,  2665,  2665,   531,  -655,    51,  2665,   673,
    2665,  2665,  -655,   228,    86,  -655,  -655,  -655,   118,   680,
     349,  5848,  2665,  -655,  2665,  2665,  -655,  2665,  2665,  2665,
     879,   604,   527,  2665,  2665,  2665,  2665,  2665,  2665,  2665,
    2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,
    2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,  2665,
    2665,  2665,   423,  1058,    73,   453,   874,   539,   571,   -16,
    -655,   585,   120, 11347,   426, 11347,   567,   560,   780,   547,
     538,   570,   792,  -655,  -655,  -655,  -655,  -655,   242,    81,
    -655,  -655,    96,   397,    -1,   413,   438,  5970,   586,   594,
    2806,  1176,  -655,  -655,  2665,  -655,   265,   265,   265,  2665,
     265,   265,  2665,  2665,  2665,   265,   265,  2665,  2665,  2665,
    -655,   265,  -655,   423,  -655,  1848,  1848,  -655,  -655,  -655,
     874,   874,  -655,  1885,  1848,  1848,   874,   600,   600,   874,
    -655,  -655,  -655,  -655,  2665,   423,  -655,   423,  2665,  2665,
    -655,  2665,   423,  2665,  1848,  2665,  1848,  2665,  1290,  2665,
     423,   153, 11225,   160,  2665,   423,  6092,  2665,   178,  6214,
    2665,   423,  1848,  2665,   423,  2665,  2665,  -655,   423,  6336,
   11726,   181,  6458,  2798, 11799, 11799,  -655,  -655,  -655,  -655,
    2798,  2798,  2798,  2798, 11726, 11726, 11726, 11726, 11726, 11549,
   11599, 11469, 11347, 11726, 11726, 11726, 11726,  3043,  3043,  1668,
    1517,  3467,  3467,  1668,  1668,   577,   577,   403,   182,  -655,
    -655,  -655,  -655,  -655,   423,  1848,   625,  1848,  2665,   595,
     690,  -655,   423,  -655,  2665,  2665,   792,   599,   780,  -655,
    -655,  -655,  -655,  2665,   603,   538,  -655,  -655,  -655,  2665,
     434,   265,   434,   265,   265,   265,   386,   265,   434,   738,
     606,   739,   609,   434,   434,   434,  2754,  2393,  -655,  -655,
    -655,  -655,  2665,   434,   434,  1290,  2665,  2665,   763,   637,
    2665,  2665,  2665,  2665,   767,  2665,  2079,   546,  -655,  -655,
    -655,  2806,  -655,   191,   667,   517,   307,  2477,  4134,   134,
     470,  6580,   222,  6702,  4256,    16,   149, 11225, 11225, 11225,
    1571,   710,   874,   874,  1798,   600,   600, 11225,   672,    39,
     124,  6826, 11225,  6948,  -655,   128, 11726,   874, 11347, 11225,
    -655,  4623,   -26,  -655,    38,  -655,  -655, 11347,  -655,  1848,
    7070,  -655,  2665, 11225,  -655,   874, 11347,  -655, 11347, 11347,
     121,  -655,  -655,  2665,   543,   874,   423,  -655,   362,   874,
   11225,   642,  2665,  -655,   774, 11347, 11347,   628,   333,  -655,
   11225,   655,  -655,  -655, 11225,  -655,  -655,  -655,  -655,  -655,
    -655,  1160,  2665,  -655,  -655,   446,  2665,   794,  2965,   798,
     497,   518,   521,   603,   294,   294,   294,   274,  3076,   528,
    -655,     0,   356,   762,  -655,  7192,   791, 11225,   535,   544,
     -11,  4745,    -8,  7314,   634,  2665,  -655,  7436,  7558,   139,
    7680,    -7,   423,   803,   347,   765,  3401,  2806,  2806,  2806,
    2665,  2806,  2806,  2665,  2665,  2665,  2806,  2806,  2665,  2665,
    2665,  -655,  -655,  2665,  -655,  2529,  2365,  -655,  2665,   265,
     265,  -655,  2665,  2665,   265,  2665,  -655,  2665,  -655,  2665,
    -655,  -655,  2665,  -655,  2665,  2665,  -655,   984,  -655,  2665,
    1062,  -655,  7802,  2665,  -655,  7924,  1848,   208,   423,  2121,
    1848, 11225,  -655,  -655,  1912,   387,  1848,   804,  8046,   265,
   11225,  2665,  -655,  3167,     1,  2965,  2806,  2806,   805,   148,
    -655,  -655,  -655,  -655,  -655,  2806,   632,  2806,  -655,  -655,
     356,  -655,  2665,   690,   273,  -655,   807,  2806,  2806,  2806,
    2806,  2665,  -655,   423,   455,   469,  -655,  8168,   434,   434,
     813,  -655,   717,  2665,  2665,  2665,  2806,  1901,  1285,  3207,
    4012,  1689,   707,  4868,  8291,  4378,   540,   250, 11225, 11225,
   11225,   212,  2665, 11225,  -655,  8413,   149,   134,  8535,  8658,
      16, 11225,  8780,  8904,  9028,  9150, 11347,   809,  -655, 11225,
    -655,  -655,  9272,  -655,   874,  -655,   283,  -655,  -655,   762,
   11225,   874,    98,   462,   661,   793,   701,   512,  2665,   505,
    -655, 11225,  2806,  -655,  -655,  -655,  2665,  2806,   603,  3106,
    -655,  3126,  -655,   590,  -655,  2806,  2806,  2665,  -655,  3167,
    3167,  3055,  9394,   219,  -655,    85,  2665,  -655,   634,  -655,
     557,   630,  2806,  2665,  9516,  3523,  3645,  3167,  2665,  2665,
    2806,  2665,  2806,  2665,  2806,  -655, 11225,   265,  2665,   423,
    -655,  -655,  -655,  2665,  2665,  -655,  -655,   690,  1940,  1977,
     625,   762,  2665,  2665,  9638,   193,  -655,  3167,  9760,  3146,
    -655,   832,   834,  1848,  -655,   707,  4991,  2665,  2806,   650,
    2806,  1209,  -655,  2806,  -655,   837,  2806,  3167,   286,  3767,
    2665,  2806,  2806,  9882, 10004,   250,  5114,  1689, 10126,   540,
     134, 11225,    77,  4623,  2926,   811,  -655,   372,   874,   372,
     874,   381,   690, 11225, 11225,  -655,  2806,  -655,  2665,  2665,
     874,  2806,  2665, 10248,  3167,  2665,  2806,  3167,   831,  -655,
    3167,  2665,  -655,  -655,  2806,  2806,  3279,  3167,  3167,  2665,
    2806,  2665,  2806,  2806,  2665,  -655,  -655,  -655,  2665,  -655,
    2351,  -655,  -655,   298,   323,  -655, 10370,  2806, 10492,  3167,
    2806, 10614,  3167,  3167,  2806,  2665, 10737,  1689, 10859,  1689,
    -655, 10981, 11225,  -655,  -655,  3167,   762,  -655,  -655,  2806,
    3167,  2806,  2853,  2806,  3167,  3889,  2665,  2806,  -655,   690,
    -655,  3167,  -655,  -655,  2806, 11103,  1689,  -655,  3167,  2806,
    1689
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
       0,   142,   143,     0,   218,   219,   221,   222,     0,   209,
     210,     0,     0,     1,     4,   159,     0,     0,     0,   134,
       0,   144,     0,   224,   220,     0,     0,     0,   338,   341,
     340,     0,   335,   334,   336,   337,   342,   339,   212,   442,
     444,   443,   441,   445,   446,     0,   433,   211,     0,     0,
     161,     0,     0,     0,   140,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   185,   188,   189,   190,   191,   192,
     193,   194,   195,   139,   184,   172,   183,   196,   197,   173,
     315,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   319,
     288,   287,   145,   281,   283,   282,   280,   284,   285,   302,
     303,   304,   305,     0,   232,     0,     0,     0,     0,     0,
     435,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    31,    12,     0,   137,   162,
     185,   204,   164,   202,     0,     0,     0,     0,     0,     0,
       0,   179,   180,   178,     0,     0,     0,     0,     0,     0,
       0,     0,   278,   310,     0,   278,     0,   408,   440,   439,
       0,   432,   431,     0,   448,   449,     0,   345,   346,     0,
     343,     0,     0,   355,   356,   357,   358,   359,   360,   361,
     362,   363,   364,   365,   366,   367,   368,   369,   370,   371,
     372,   373,   374,     0,     0,     0,   458,     0,     0,     0,
       0,     0,   437,     0,     0,   452,   454,   455,     0,     0,
       0,   278,     0,   289,     0,     0,   293,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   183,     0,   241,
     447,     0,     0,   215,     0,   216,     0,     0,   248,   170,
     272,     0,   149,   151,   152,   153,   154,   155,     0,     0,
      43,    42,     0,     0,     0,     0,     0,     0,    52,     0,
       0,     0,    48,    50,     0,    32,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      34,     0,   138,     0,   163,     0,     0,   203,   165,   171,
     201,   199,   177,     0,     0,     0,   200,   175,   176,   198,
     186,   187,   286,   316,     0,     0,   311,     0,     0,     0,
     320,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   278,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   331,     0,     0,
     386,     0,   278,   376,   375,   377,   290,   296,   292,   291,
     379,   378,   380,   381,   382,   383,   384,   385,   387,   388,
     389,   390,   391,   392,   393,   394,   395,   396,   397,   398,
     399,   400,   401,   402,   403,   404,   405,   406,   407,   299,
     300,   297,   298,   233,     0,     0,     0,     0,     0,     0,
       0,   240,     0,   436,     0,     0,     0,     0,   249,   250,
     253,   252,   156,     0,     0,   273,   274,    10,   150,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      53,     0,    51,     0,     0,     0,     0,   264,    60,    61,
      62,    63,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   104,   101,
     112,     0,    49,     0,    31,    18,    17,    22,     0,    21,
      16,     0,     0,     0,     0,    23,    25,    29,    28,    27,
      13,     0,   206,   205,     0,   182,   181,   279,     0,     0,
       0,     0,   410,     0,   409,     0,   450,   451,   309,   301,
     344,     0,     0,   352,     0,   323,   324,   325,   459,     0,
       0,   329,     0,   456,   438,   457,   306,   453,   307,   308,
       0,   326,   294,     0,     0,   234,     0,   226,     0,   238,
     242,     0,     0,   223,   438,   213,   214,     0,     0,   251,
     277,     0,   276,   275,    46,    47,    37,    45,    44,    36,
      41,     0,     0,    40,    35,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   185,     0,     0,
     121,     0,     0,   241,   263,     0,     0,    64,     0,     0,
       0,     0,     0,     0,     0,     0,   109,     0,     0,     0,
       0,     0,     0,   439,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    14,    78,     0,   106,     0,     0,    33,     0,     0,
       0,   126,     0,     0,     0,     0,   174,     0,   317,     0,
     312,   314,     0,   321,     0,     0,   347,     0,   350,     0,
       0,   327,     0,     0,   332,     0,     0,     0,     0,     0,
       0,   245,   434,    11,     0,   236,     0,     0,     0,     0,
      56,     0,    54,    58,     0,     0,     0,     0,     0,     0,
     413,   125,   124,   123,    65,     0,     0,     0,   270,   271,
     265,   266,     0,     0,     0,   108,     0,     0,     0,     0,
       0,     0,   102,     0,     0,     0,   418,     0,     0,     0,
       0,   103,     0,     0,     0,     0,     0,    70,    69,    81,
       0,    83,    68,     0,     0,     0,    71,    73,    77,    76,
      75,     0,     0,   420,    15,     0,    26,    19,     0,   129,
      24,   207,     0,     0,     0,     0,   348,     0,   353,   354,
     328,   330,     0,   295,   235,   227,     0,   230,   231,   241,
     229,   239,     0,     0,     0,   264,   237,   415,     0,     0,
      38,    57,     0,    55,    93,    92,     0,     0,     0,     0,
     122,     0,   267,   268,   430,     0,     0,     0,    91,    99,
     411,     0,     0,     0,   427,     0,     0,   105,     0,   110,
       0,     0,     0,     0,     0,     0,     0,   118,     0,     0,
       0,     0,     0,     0,     0,   107,   111,     0,     0,     0,
     318,   313,   322,     0,     0,   333,   228,   244,     0,     0,
       0,   241,     0,     0,     0,    31,    39,    59,     0,     0,
     414,    88,     0,     0,    66,    67,     0,     0,     0,     0,
       0,     0,   425,     0,   419,     0,     0,   119,     0,     0,
       0,     0,     0,     0,     0,    74,     0,    87,     0,    72,
      20,   130,     0,     0,     0,   247,   243,   259,   257,   258,
     256,     0,     0,   417,   416,    30,     0,   100,     0,     0,
     269,     0,     0,     0,   422,     0,     0,   412,     0,   428,
     429,     0,    94,    79,     0,     0,     0,   114,   113,     0,
       0,     0,     0,     0,     0,   127,   349,   351,     0,   225,
       0,   255,    95,     0,     0,    98,     0,     0,     0,   421,
       0,     0,   120,   115,     0,     0,     0,    85,     0,    82,
      80,     0,   246,   261,   262,   260,   241,    89,    90,     0,
     423,     0,     0,     0,   116,     0,     0,     0,   128,   244,
      97,   424,   426,    96,     0,     0,    86,   254,   117,     0,
      84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -655,  -655,  -655,   854,  -655,  -655,  -315,  -152,   244,  -655,
     519,  -655,  -655,   127,   896,  -655,  -655,   117,   -42,  -655,
    -655,  -655,   842,   -75,   116,  -655,  -655,   843,  -655,  -316,
    -153,   113,  -655,   841,   106,  1333,    11,   754,  -143,  -655,
    -655,   514,  -186,   714,   439,  -655,   863,   865,   447,  -655,
     873,  -655,  -655,   875,    17,  -655,   301,  -655,  -655,   185,
    -636,  -655,  -113,  -479,  -655,  -655,  -655,  -320,  -655,  -655,
    -655,  -655,    87,  -655,  -655,   168,  -655,  -655,  -655,   435,
      15,   -50,   731,   799,   885,   956,  1085,   613,   408,   532,
    -655,    -2,  -655,  -655,   218,  -655,  -655,  -655,   536,   415,
    -655,  -635,  -655,  -654,  -655,  -655,  -655,  -655,    21,  -655,
      10,   -27,  -148,  -655,  -174,  -144,  -259,  -137,  -126,   522,
    -655,  -655,  -655
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   186,   635,   340,   351,
     352,   512,   510,   742,   743,   928,   649,   650,   692,   552,
      13,    27,    28,   744,   341,    14,    31,    32,    15,   331,
     332,   333,    23,    24,   487,   191,   537,   105,   106,   107,
     108,   192,   193,   374,   334,    39,    40,   227,   335,    34,
      35,    36,    37,   228,   608,   829,   166,   314,   835,   319,
     480,   481,   945,   946,   989,   336,   488,   489,   490,   491,
     834,  1016,   653,   654,   760,   761,   762,   337,   495,   496,
     211,   412,   153,   154,   155,   156,   157,   158,   229,   230,
     159,   716,   160,   582,   583,   161,   162,   216,   217,   662,
     749,   622,   775,   538,   539,   919,   540,   863,   864,   656,
     262,   221,   263,   222,    76,   223,   224,   225,   264,   265,
     266,   267,   257
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "152,613,269,256,187,167,377,371,750,492,776,75,505,757,842,315,498,763,171,268,321,322,317,771,771,339,342,343,393,345,346,353,173,478,26,171,177,178,179,180,407,545,546,547,208,549,550,30,768,361,555,556,707,470,181,182,560,41,209,104,708,210,212,215,414,719,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,75,396,984,473,104,104,501,403,104,271,104,423,104,104,104,104,104,344,437,25,42,503,33,38,923,16,502,1,2,3,29,4,717,323,16,325,220,195,404,408,214,502,479,425,43,347,723,408,569,709,483,472,33,712,196,29,370,38,46,718,356,357,255,25,780,394,710,172,220,258,713,363,5,847,270,84,183,772,781,361,619,174,617,585,406,366,83,367,104,368,586,83,83,83,361,104,421,104,626,369,629,630,631,633,634,897,184,188,591,415,353,602,898,276,185,104,45,394,104,104,416,697,354,419,355,910,924,104,104,104,104,474,400,985,429,424,430,432,825,433,434,435,885,421,424,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,584,317,424,952,421,424,411,413,424,570,724,281,424,418,575,363,588,177,178,179,180,682,854,394,6,7,364,365,431,366,363,367,848,368,188,181,182,394,855,896,600,372,973,369,394,50,367,548,368,499,551,553,554,311,1017,557,558,559,369,52,469,354,394,355,604,394,95,96,97,98,99,100,101,102,401,394,375,500,361,1018,48,163,567,164,420,78,571,572,163,573,164,576,421,578,79,579,394,581,499,640,641,642,587,921,701,590,702,543,593,658,659,596,421,598,599,422,377,728,1029,75,81,561,806,807,104,104,500,810,675,48,922,363,104,104,104,684,728,195,177,178,179,180,82,568,784,183,729,688,504,689,758,759,856,104,196,104,181,182,47,690,276,48,840,255,506,990,610,421,502,594,974,104,615,616,494,184,484,17,55,651,376,620,394,544,502,18,17,624,363,507,47,204,55,48,18,205,727,739,372,364,365,206,366,403,367,655,368,394,207,502,657,991,83,165,661,663,369,502,667,668,734,670,168,676,104,867,104,204,218,198,614,205,281,394,404,253,75,206,427,254,868,370,370,370,207,370,370,260,746,56,57,370,370,58,261,382,370,177,178,179,180,204,906,56,57,205,632,58,502,747,357,206,748,310,311,181,182,204,207,755,722,205,674,208,272,669,766,206,361,502,273,725,502,866,207,767,184,209,59,756,731,693,899,694,544,318,502,584,925,902,940,682,59,104,327,502,175,485,826,176,738,60,370,493,740,370,370,370,502,370,370,169,204,170,276,60,205,421,104,494,475,219,206,62,63,64,65,66,67,207,903,363,338,777,84,61,628,62,63,64,65,66,67,865,870,871,383,367,790,368,188,793,794,795,208,206,798,799,800,369,606,926,607,803,695,183,805,402,209,74,808,809,665,811,666,812,390,813,363,109,814,502,815,816,391,280,281,819,405,364,365,822,366,774,367,184,368,830,404,282,782,363,354,905,355,684,369,421,417,841,726,426,109,109,438,687,109,688,109,689,109,109,109,109,109,439,801,310,311,690,965,966,19,20,21,22,-170,476,477,862,-170,19,20,21,22,74,74,312,313,482,486,874,875,876,394,104,204,913,612,104,205,497,625,104,627,104,206,384,636,638,886,74,188,207,705,74,74,74,751,752,753,942,370,370,509,372,370,69,644,645,646,865,511,611,74,853,109,618,664,74,74,621,55,109,637,109,904,639,403,730,732,-170,-170,1,908,-170,370,4,328,733,329,330,736,109,741,916,109,109,745,478,765,379,94,783,803,109,109,109,109,838,204,929,785,846,205,857,933,934,872,936,206,938,218,873,894,70,941,207,5,900,-170,943,944,69,69,479,652,958,363,959,953,954,684,56,57,971,1000,58,372,188,988,44,370,-170,688,963,689,49,542,69,843,850,51,69,69,69,690,774,976,19,20,21,22,-170,80,-170,-170,-170,-170,-170,-170,74,69,259,55,564,370,69,69,204,77,68,59,205,378,54,53,104,104,206,996,70,70,998,1037,951,207,735,836,1001,901,71,104,74,74,60,852,1006,623,1008,672,660,1011,818,0,574,1012,70,580,986,969,70,70,70,597,673,671,62,63,64,65,66,67,1025,19,20,21,22,-170,0,70,0,0,0,0,70,70,0,0,58,0,993,994,1,1035,0,348,4,328,349,329,330,0,350,74,0,109,109,0,0,0,0,72,0,109,109,109,71,71,55,0,0,0,0,0,0,74,0,74,69,0,0,0,74,59,109,0,109,5,74,0,74,0,71,0,0,74,71,71,71,0,0,74,109,0,74,0,60,0,74,0,69,69,0,0,0,71,817,0,218,0,71,71,0,0,0,0,436,0,62,63,64,65,66,67,0,0,0,72,72,56,57,204,55,58,0,205,70,820,0,0,0,206,0,0,74,109,204,109,207,0,205,373,74,72,0,0,206,72,72,72,204,69,0,207,205,0,0,0,70,70,206,385,0,0,0,0,72,207,59,0,73,72,72,69,0,69,0,0,0,0,69,0,0,0,0,74,69,0,69,0,0,60,0,69,74,0,0,58,0,69,0,0,69,0,0,0,69,0,0,0,0,219,71,62,63,64,65,66,67,70,0,356,357,358,109,737,1,359,0,348,4,328,349,329,330,0,541,0,0,360,361,70,59,70,71,71,73,73,70,109,0,0,69,0,70,0,70,0,0,0,69,70,0,0,0,60,74,70,0,0,70,5,73,55,70,0,73,73,73,0,0,0,0,72,0,471,0,62,63,64,65,66,67,536,0,73,0,0,0,0,73,73,0,69,0,71,0,0,0,0,0,0,69,0,0,72,72,0,0,0,70,968,0,218,0,0,0,71,70,71,0,0,74,0,71,0,0,0,0,0,71,0,71,0,56,57,0,71,58,362,363,0,0,71,55,0,71,0,0,0,71,364,365,0,366,682,367,0,368,0,0,70,0,0,0,0,72,74,369,0,70,0,0,0,69,0,109,0,74,0,109,0,0,59,109,0,109,0,72,0,72,0,0,0,0,72,71,0,0,0,0,72,73,72,71,0,60,0,72,0,0,0,0,0,72,56,57,72,103,58,0,72,0,74,0,0,219,0,62,63,64,65,66,67,73,73,0,0,0,0,69,0,70,0,0,0,0,71,0,648,0,189,0,0,0,194,71,197,0,199,200,201,202,203,59,363,0,72,0,684,0,0,0,0,696,72,685,686,0,687,0,688,0,689,0,69,0,60,0,0,0,0,0,690,0,73,69,0,0,0,0,0,0,0,0,0,0,226,70,62,63,64,65,66,67,0,0,73,72,73,0,0,0,0,73,0,0,72,71,0,73,0,73,0,0,316,0,73,0,74,0,69,324,73,326,0,73,0,109,109,73,0,0,70,0,0,0,0,0,0,0,0,0,109,70,380,381,0,0,0,0,74,0,276,386,387,388,389,0,0,0,0,0,0,0,0,0,0,0,0,0,277,0,0,71,0,73,0,0,72,0,0,0,0,73,0,0,0,0,70,787,788,789,0,791,792,0,0,0,796,797,0,356,357,358,0,0,0,359,0,0,0,0,0,0,0,0,0,0,71,360,361,0,0,280,281,0,73,0,0,71,85,0,86,87,0,73,69,282,283,284,285,286,0,0,72,0,0,0,88,0,0,0,89,90,91,92,93,302,844,845,373,306,307,308,309,310,311,849,69,851,0,0,0,0,71,0,0,0,0,858,859,860,861,0,0,312,313,0,0,72,0,0,0,0,0,0,0,0,877,0,72,0,0,276,70,0,0,73,0,0,0,0,0,0,0,0,0,0,0,677,678,0,0,0,562,563,0,0,0,362,363,0,0,565,566,0,70,0,682,0,0,364,365,0,366,72,367,0,368,0,0,0,0,577,907,324,0,0,369,909,0,0,0,0,0,0,0,914,915,0,0,595,0,73,280,281,0,0,0,0,0,0,0,0,927,0,0,0,282,0,71,0,935,190,937,0,939,0,0,66,0,95,96,97,98,99,100,101,102,0,0,0,306,307,308,309,310,311,73,0,0,0,71,0,605,0,609,0,0,73,964,85,967,86,87,970,312,313,972,0,0,0,0,977,978,0,0,0,363,88,0,0,684,89,90,91,92,93,0,685,686,72,687,0,688,0,689,0,992,0,0,0,0,995,73,0,690,0,999,0,0,85,0,86,87,0,1002,1003,0,0,0,0,1007,72,1009,1010,0,0,0,88,0,0,1015,89,90,91,92,93,0,1020,0,0,1022,0,0,0,1024,0,85,0,86,87,0,0,0,0,0,0,0,0,0,1030,678,1031,0,1033,88,0,720,1036,89,90,91,92,93,85,1038,832,87,0,682,1040,0,0,0,0,0,0,0,0,0,0,0,88,0,0,0,89,90,91,92,93,0,85,0,947,87,0,0,0,0,0,0,0,0,0,0,706,0,0,73,88,0,0,190,89,90,91,92,93,66,0,95,96,97,98,99,100,101,102,85,0,949,87,0,0,0,0,0,0,0,0,73,0,0,0,0,0,88,0,0,0,89,90,91,92,93,0,0,0,0,0,0,0,94,0,0,0,0,0,66,0,95,96,97,98,99,100,101,102,363,0,0,0,684,0,0,0,0,0,0,685,686,0,687,824,688,0,689,831,0,190,0,833,0,837,0,66,690,95,96,97,98,99,100,101,102,0,0,0,0,0,0,0,0,0,0,0,94,677,678,679,0,0,66,680,95,96,97,98,99,100,101,102,0,0,0,681,682,0,0,0,0,0,0,0,94,0,0,0,0,0,66,0,95,96,97,98,99,100,101,102,110,0,111,0,0,112,0,0,0,0,0,0,0,0,113,0,0,0,0,0,0,94,0,0,0,0,0,66,0,95,96,97,98,99,100,101,102,114,827,828,0,0,0,115,0,116,0,0,0,117,0,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,0,0,138,139,0,0,58,140,0,141,142,143,0,0,683,363,0,0,0,684,0,0,0,0,948,950,685,686,0,687,0,688,0,689,0,0,0,0,0,960,0,0,0,690,0,691,0,0,144,145,146,59,0,0,0,0,0,0,0,0,0,0,0,0,147,110,0,111,0,0,112,0,0,0,60,0,0,0,0,113,213,0,148,0,149,0,0,0,0,0,0,0,0,0,150,151,62,63,64,65,66,67,114,0,0,0,0,0,115,0,116,0,0,0,117,0,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,0,0,138,139,0,0,58,140,0,141,142,143,0,0,0,0,0,0,0,0,0,0,513,514,515,516,0,0,517,0,518,519,520,521,522,0,677,678,679,0,523,524,680,0,0,0,0,0,144,145,146,59,0,0,681,682,0,0,0,0,0,1013,1014,0,147,110,525,111,526,0,112,0,527,0,60,0,0,0,0,113,0,0,148,0,149,0,0,0,0,0,0,0,0,0,150,151,62,63,64,65,66,67,114,0,0,652,0,0,115,528,116,529,0,0,117,0,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,0,0,138,139,0,0,58,140,0,141,142,143,356,357,530,0,0,0,359,531,0,0,0,0,0,0,0,0,683,363,360,361,0,684,532,533,534,0,0,535,685,686,0,687,0,688,0,689,144,145,146,59,94,0,0,0,0,690,0,804,0,0,0,0,147,110,0,111,0,0,112,0,0,0,60,0,0,0,0,113,0,0,148,0,149,0,0,0,0,0,0,0,0,0,150,151,62,63,64,65,66,67,114,0,0,0,0,0,115,0,116,0,0,0,117,0,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,0,0,138,139,362,363,58,140,802,141,142,143,0,0,0,0,364,365,0,366,0,367,0,368,0,0,0,0,0,0,0,0,0,369,0,0,0,0,0,0,0,0,0,0,0,0,0,0,144,145,146,59,0,0,0,0,0,0,0,0,0,0,0,0,147,110,0,111,0,0,112,0,0,0,60,0,0,0,0,113,0,0,148,0,149,0,0,0,0,0,0,0,0,0,150,151,62,63,64,65,66,67,114,0,0,0,0,0,115,0,116,0,0,0,117,0,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,0,0,138,139,0,0,58,140,0,141,142,143,0,0,0,0,0,513,514,515,516,0,0,517,0,518,519,520,521,522,0,0,0,0,0,523,524,0,0,0,0,0,0,0,0,0,0,0,144,145,146,59,0,0,0,0,0,0,0,0,0,525,0,526,147,0,276,527,0,513,514,515,516,0,60,517,0,518,519,520,521,522,148,0,149,0,0,523,524,0,0,0,0,0,150,151,62,63,64,65,66,67,643,0,528,0,529,0,0,0,0,0,0,525,0,526,677,678,679,527,0,0,680,0,0,0,0,0,0,0,0,0,0,0,681,682,0,280,281,0,0,644,645,646,0,530,0,0,0,0,531,282,0,0,0,0,528,0,529,0,0,0,0,0,0,532,533,534,0,0,535,302,0,0,0,306,307,308,309,310,311,0,0,647,0,0,0,274,0,0,0,0,275,0,276,0,0,530,0,312,313,0,531,0,0,0,0,0,0,0,0,0,277,0,0,0,0,532,533,534,0,0,535,0,0,0,0,0,513,514,515,516,0,0,517,94,518,519,520,521,522,278,279,0,683,363,523,524,0,684,0,0,0,0,0,0,685,686,0,687,0,688,0,689,0,280,281,0,0,0,0,0,525,690,526,1032,0,0,527,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,276,0,0,0,528,0,529,677,678,679,0,0,0,680,0,0,312,313,277,0,0,0,0,0,0,681,682,0,677,678,679,0,754,0,680,0,0,987,0,0,0,0,0,0,0,530,681,682,0,0,531,0,0,0,0,917,918,0,677,678,679,0,911,0,680,532,533,534,0,0,535,280,281,0,0,0,681,682,677,678,679,0,912,647,680,282,283,284,285,286,0,0,0,0,0,0,681,682,677,678,679,0,957,0,680,302,303,304,305,306,307,308,309,310,311,0,681,682,0,677,678,679,0,0,0,680,0,0,0,0,0,0,0,312,313,683,363,681,682,0,684,0,0,0,0,0,0,685,686,0,687,0,688,0,689,0,683,363,0,677,678,684,0,0,690,680,0,0,685,686,0,687,0,688,0,689,0,681,682,0,0,0,0,0,0,690,683,363,0,0,0,684,0,0,0,0,0,0,685,686,0,687,0,688,0,689,683,363,0,0,0,684,0,0,0,690,0,0,685,686,0,687,0,688,0,689,683,363,0,274,0,684,0,0,275,690,276,0,685,686,0,687,0,688,0,689,0,683,363,0,0,0,684,0,277,690,0,0,0,685,686,0,687,0,688,0,689,0,0,0,0,0,0,0,0,0,690,0,0,0,0,0,0,278,279,0,0,683,363,0,0,0,684,0,0,0,0,0,0,685,686,0,687,0,688,0,689,280,281,0,0,0,0,0,0,0,690,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,1004,0,0,1005,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,277,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,280,281,0,277,0,0,0,786,0,0,0,0,0,0,282,283,284,285,286,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,302,303,0,0,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,312,313,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,931,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,932,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,975,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,878,879,0,0,0,0,277,0,0,1034,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,698,0,0,0,880,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,699,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,704,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,397,0,884,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,398,399,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,714,715,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,769,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,770,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,881,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,961,882,0,0,0,0,0,0,312,313,962,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,981,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,320,276,0,0,0,982,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,392,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,395,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,409,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,410,0,0,312,313,428,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,508,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,589,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,601,276,0,0,0,0,592,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,603,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,700,0,0,0,0,703,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,312,313,711,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,399,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,721,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,764,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,773,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,778,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,779,769,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,821,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,823,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,869,276,0,0,0,839,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,883,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,887,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,888,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,889,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,890,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,312,313,891,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,312,313,892,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,893,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,920,0,895,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,930,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,955,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,956,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,979,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,980,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,983,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,997,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,1019,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1021,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,1023,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,312,313,1026,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,1027,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,1039,1028,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,275,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,274,0,0,0,0,0,0,276,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,281,274,0,0,0,0,0,0,276,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,294,277,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,0,274,0,0,0,278,279,0,276,0,0,312,313,0,0,0,0,0,0,0,0,0,0,0,0,0,277,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,292,293,278,279,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,274,280,281,0,0,0,0,276,0,0,0,0,0,312,313,282,283,284,285,286,287,288,289,290,291,277,293,0,0,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,278,279,0,276,0,0,0,0,0,312,313,0,0,0,0,0,0,0,0,0,0,277,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,287,288,289,290,291,0,0,278,279,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,280,281,276,0,0,0,0,0,0,0,0,0,312,313,282,283,284,285,286,0,277,0,0,0,0,0,0,0,0,0,0,0,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,312,313,0,0,0,0,0,0,0,0,0,280,281,0,0,0,0,0,0,0,0,0,0,0,0,282,283,284,285,286,0,0,0,0,0,0,0,0,0,0,0,0,0,300,301,302,303,304,305,306,307,308,309,310,311,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,312,313";
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
  public static final String yyCheckString = "50,480,146,140,79,55,192,13,643,329,664,38,13,13,13,163,332,653,12,145,168,169,165,31,31,177,178,179,21,181,182,184,12,49,180,12,14,15,16,17,59,356,357,358,90,360,361,180,59,33,365,366,13,312,32,33,371,180,104,48,21,111,112,113,13,27,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,115,30,13,18,81,82,13,118,85,147,87,13,89,90,91,92,93,180,280,1,180,13,4,5,27,0,33,3,4,5,2,7,146,171,9,173,114,27,149,146,113,33,146,13,0,183,13,146,395,13,18,313,34,13,44,27,186,39,180,173,14,15,140,45,13,146,30,149,146,17,30,143,48,13,147,47,142,173,173,33,488,149,486,18,149,157,180,159,165,161,18,180,180,180,33,172,146,174,501,171,503,504,505,506,507,829,172,79,18,146,351,18,102,19,180,192,33,146,195,196,258,18,17,261,19,848,868,204,205,206,207,146,30,144,272,146,274,275,18,277,278,279,18,146,146,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,410,406,146,901,146,146,253,254,146,397,151,91,146,260,402,143,415,14,15,16,17,33,763,146,174,175,154,155,275,157,143,159,146,161,180,32,33,146,27,18,428,187,18,171,146,12,159,359,161,118,362,363,364,133,18,367,368,369,171,180,312,17,146,19,474,146,188,189,190,191,192,193,194,195,146,146,45,146,33,18,149,17,394,19,118,12,398,399,17,401,19,403,146,405,12,407,146,409,118,513,514,515,414,146,144,417,146,354,420,523,524,423,146,425,426,149,564,17,1016,408,12,373,699,700,375,376,146,704,534,149,173,143,383,384,385,147,17,27,14,15,16,17,34,395,59,142,46,159,13,161,56,57,141,404,44,406,32,33,146,171,19,149,739,415,13,46,478,146,33,421,146,422,484,485,330,172,12,0,17,516,149,493,146,180,33,0,9,499,143,13,146,27,17,149,9,31,606,13,344,154,155,37,157,118,159,517,161,146,44,33,522,952,180,149,526,527,171,33,530,531,149,533,17,535,475,18,477,27,67,180,482,31,91,146,149,17,525,37,151,17,33,545,546,547,44,549,550,17,13,88,89,555,556,92,17,21,560,14,15,16,17,27,839,88,89,31,142,92,33,13,15,37,13,132,133,32,33,27,44,13,592,31,534,90,17,532,13,37,33,33,98,603,33,100,44,13,172,104,137,33,612,17,102,19,180,180,33,717,13,59,887,33,137,564,180,33,169,12,728,172,632,160,626,47,636,629,630,631,33,633,634,17,27,19,19,160,31,146,589,495,149,180,37,182,183,184,185,186,187,44,100,143,180,665,500,180,502,182,183,184,185,186,187,773,778,779,36,159,680,161,516,683,684,685,90,37,688,689,690,171,17,13,19,695,100,142,698,31,104,38,702,703,17,705,19,707,180,709,143,48,712,33,714,715,180,90,91,719,13,154,155,723,157,664,159,172,161,729,149,104,672,143,17,180,19,147,171,146,17,741,149,13,81,82,92,157,85,159,87,161,89,90,91,92,93,180,693,132,133,171,62,63,176,177,178,179,180,180,149,771,17,176,177,178,179,114,115,152,153,146,172,783,784,785,146,726,27,149,50,730,31,173,500,734,502,736,37,38,12,12,802,140,638,44,46,144,145,146,644,645,646,889,806,807,180,651,810,38,138,139,140,921,180,180,163,762,165,180,17,168,169,180,17,172,180,174,838,180,118,149,18,88,89,3,846,92,840,7,8,173,10,11,149,192,12,857,195,196,12,49,21,18,180,12,866,204,205,206,207,17,27,873,59,20,31,20,878,879,17,881,37,883,67,118,27,38,888,44,48,180,137,893,894,114,115,146,55,17,143,17,902,903,147,88,89,20,27,92,744,745,51,9,906,160,159,917,161,27,351,140,745,756,31,144,145,146,171,868,930,176,177,178,179,180,45,182,183,184,185,186,187,280,163,141,17,383,940,168,169,27,39,38,137,31,192,34,33,898,899,37,962,114,115,965,1029,900,44,618,735,971,835,38,913,312,313,160,760,979,495,981,165,525,984,717,-1,401,988,140,408,943,921,144,145,146,424,180,533,182,183,184,185,186,187,1005,176,177,178,179,180,-1,163,-1,-1,-1,-1,168,169,-1,-1,92,-1,958,959,3,1026,-1,6,7,8,9,10,11,-1,13,373,-1,375,376,-1,-1,-1,-1,38,-1,383,384,385,114,115,17,-1,-1,-1,-1,-1,-1,395,-1,397,280,-1,-1,-1,402,137,404,-1,406,48,408,-1,410,-1,140,-1,-1,415,144,145,146,-1,-1,421,422,-1,424,-1,160,-1,428,-1,312,313,-1,-1,-1,163,65,-1,67,-1,168,169,-1,-1,-1,-1,180,-1,182,183,184,185,186,187,-1,-1,-1,114,115,88,89,27,17,92,-1,31,280,18,-1,-1,-1,37,-1,-1,474,475,27,477,44,-1,31,47,482,140,-1,-1,37,144,145,146,27,373,-1,44,31,-1,-1,-1,312,313,37,38,-1,-1,-1,-1,163,44,137,-1,38,168,169,395,-1,397,-1,-1,-1,-1,402,-1,-1,-1,-1,525,408,-1,410,-1,-1,160,-1,415,534,-1,-1,92,-1,421,-1,-1,424,-1,-1,-1,428,-1,-1,-1,-1,180,280,182,183,184,185,186,187,373,-1,14,15,16,564,18,3,20,-1,6,7,8,9,10,11,-1,13,-1,-1,32,33,395,137,397,312,313,114,115,402,589,-1,-1,474,-1,408,-1,410,-1,-1,-1,482,415,-1,-1,-1,160,606,421,-1,-1,424,48,140,17,428,-1,144,145,146,-1,-1,-1,-1,280,-1,180,-1,182,183,184,185,186,187,350,-1,163,-1,-1,-1,-1,168,169,-1,525,-1,373,-1,-1,-1,-1,-1,-1,534,-1,-1,312,313,-1,-1,-1,474,65,-1,67,-1,-1,-1,395,482,397,-1,-1,672,-1,402,-1,-1,-1,-1,-1,408,-1,410,-1,88,89,-1,415,92,142,143,-1,-1,421,17,-1,424,-1,-1,-1,428,154,155,-1,157,33,159,-1,161,-1,-1,525,-1,-1,-1,-1,373,717,171,-1,534,-1,-1,-1,606,-1,726,-1,728,-1,730,-1,-1,137,734,-1,736,-1,395,-1,397,-1,-1,-1,-1,402,474,-1,-1,-1,-1,408,280,410,482,-1,160,-1,415,-1,-1,-1,-1,-1,421,88,89,424,48,92,-1,428,-1,773,-1,-1,180,-1,182,183,184,185,186,187,312,313,-1,-1,-1,-1,672,-1,606,-1,-1,-1,-1,525,-1,516,-1,81,-1,-1,-1,85,534,87,-1,89,90,91,92,93,137,143,-1,474,-1,147,-1,-1,-1,-1,541,482,154,155,-1,157,-1,159,-1,161,-1,717,-1,160,-1,-1,-1,-1,-1,171,-1,373,728,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,672,182,183,184,185,186,187,-1,-1,395,525,397,-1,-1,-1,-1,402,-1,-1,534,606,-1,408,-1,410,-1,-1,165,-1,415,-1,889,-1,773,172,421,174,-1,424,-1,898,899,428,-1,-1,717,-1,-1,-1,-1,-1,-1,-1,-1,-1,913,728,195,196,-1,-1,-1,-1,921,-1,19,204,205,206,207,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,672,-1,474,-1,-1,606,-1,-1,-1,-1,482,-1,-1,-1,-1,773,677,678,679,-1,681,682,-1,-1,-1,686,687,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,717,32,33,-1,-1,90,91,-1,525,-1,-1,728,17,-1,19,20,-1,534,889,104,105,106,107,108,-1,-1,672,-1,-1,-1,35,-1,-1,-1,39,40,41,42,43,124,746,747,47,128,129,130,131,132,133,755,921,757,-1,-1,-1,-1,773,-1,-1,-1,-1,767,768,769,770,-1,-1,152,153,-1,-1,717,-1,-1,-1,-1,-1,-1,-1,-1,786,-1,728,-1,-1,19,889,-1,-1,606,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,-1,-1,-1,375,376,-1,-1,-1,142,143,-1,-1,384,385,-1,921,-1,33,-1,-1,154,155,-1,157,773,159,-1,161,-1,-1,-1,-1,404,842,406,-1,-1,171,847,-1,-1,-1,-1,-1,-1,-1,855,856,-1,-1,422,-1,672,90,91,-1,-1,-1,-1,-1,-1,-1,-1,872,-1,-1,-1,104,-1,889,-1,880,180,882,-1,884,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,128,129,130,131,132,133,717,-1,-1,-1,921,-1,475,-1,477,-1,-1,728,918,17,920,19,20,923,152,153,926,-1,-1,-1,-1,931,932,-1,-1,-1,143,35,-1,-1,147,39,40,41,42,43,-1,154,155,889,157,-1,159,-1,161,-1,956,-1,-1,-1,-1,961,773,-1,171,-1,966,-1,-1,17,-1,19,20,-1,974,975,-1,-1,-1,-1,980,921,982,983,-1,-1,-1,35,-1,-1,990,39,40,41,42,43,-1,997,-1,-1,1000,-1,-1,-1,1004,-1,17,-1,19,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,1019,15,1021,-1,1023,35,-1,589,1027,39,40,41,42,43,17,1034,19,20,-1,33,1039,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,35,-1,-1,-1,39,40,41,42,43,-1,17,-1,19,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,173,-1,-1,889,35,-1,-1,180,39,40,41,42,43,186,-1,188,189,190,191,192,193,194,195,17,-1,19,20,-1,-1,-1,-1,-1,-1,-1,-1,921,-1,-1,-1,-1,-1,35,-1,-1,-1,39,40,41,42,43,-1,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,726,159,-1,161,730,-1,180,-1,734,-1,736,-1,186,171,188,189,190,191,192,193,194,195,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,14,15,16,-1,-1,186,20,188,189,190,191,192,193,194,195,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,15,-1,17,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,29,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,898,899,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,913,-1,-1,-1,171,-1,173,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,30,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,53,54,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,14,15,142,-1,-1,-1,20,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,32,33,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,142,143,92,93,94,95,96,97,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,58,-1,60,150,-1,19,64,-1,14,15,16,17,-1,160,20,-1,22,23,24,25,26,168,-1,170,-1,-1,32,33,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,99,-1,101,-1,103,-1,-1,-1,-1,-1,-1,58,-1,60,14,15,16,64,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,90,91,-1,-1,138,139,140,-1,142,-1,-1,-1,-1,147,104,-1,-1,-1,-1,101,-1,103,-1,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,180,-1,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,142,-1,152,153,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,162,163,164,-1,-1,167,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,180,22,23,24,25,26,66,67,-1,142,143,32,33,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,90,91,-1,-1,-1,-1,-1,58,171,60,173,-1,-1,64,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,19,-1,-1,-1,101,-1,103,14,15,16,-1,-1,-1,20,-1,-1,152,153,37,-1,-1,-1,-1,-1,-1,32,33,-1,14,15,16,-1,18,-1,20,-1,-1,173,-1,-1,-1,-1,-1,-1,-1,142,32,33,-1,-1,147,-1,-1,-1,-1,62,63,-1,14,15,16,-1,18,-1,20,162,163,164,-1,-1,167,90,91,-1,-1,-1,32,33,14,15,16,-1,18,180,20,104,105,106,107,108,-1,-1,-1,-1,-1,-1,32,33,14,15,16,-1,18,-1,20,124,125,126,127,128,129,130,131,132,133,-1,32,33,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,152,153,142,143,32,33,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,142,143,-1,14,15,147,-1,-1,171,20,-1,-1,154,155,-1,157,-1,159,-1,161,-1,32,33,-1,-1,-1,-1,-1,-1,171,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,-1,-1,147,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,12,-1,147,-1,-1,17,171,19,-1,154,155,-1,157,-1,159,-1,161,-1,142,143,-1,-1,-1,147,-1,37,171,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,66,67,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,90,91,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
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
     180,   237,   238,   245,   260,   261,   262,   263,   245,   256,
     257,   180,   180,     0,   214,    33,   180,   146,   149,   233,
      12,   238,   180,   264,   261,    17,    88,    89,    92,   137,
     160,   180,   182,   183,   184,   185,   186,   187,   258,   293,
     294,   295,   296,   297,   298,   322,   325,   257,    12,    12,
     244,    12,    34,   180,   235,    17,    19,    20,    35,    39,
      40,    41,    42,    43,   180,   188,   189,   190,   191,   192,
     193,   194,   195,   246,   247,   248,   249,   250,   251,   298,
      15,    17,    20,    29,    52,    58,    60,    64,    66,    67,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    88,    89,
      93,    95,    96,    97,   134,   135,   136,   150,   168,   170,
     180,   181,   292,   293,   294,   295,   296,   297,   298,   301,
     303,   306,   307,    17,    19,   149,   267,   292,    17,    17,
      19,    12,   149,    12,   149,   169,   172,    14,    15,    16,
      17,    32,    33,   142,   172,   180,   217,   234,   235,   246,
     180,   246,   252,   253,   246,    27,    44,   246,   180,   246,
     246,   246,   246,   246,    27,    31,    37,    44,    90,   104,
     292,   291,   292,    30,   291,   292,   308,   309,    67,   180,
     321,   322,   324,   326,   327,   328,   180,   258,   264,   299,
     300,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,    17,    17,   321,   328,   333,    17,   248,
      17,    17,   321,   323,   329,   330,   331,   332,   329,   326,
     291,   292,    17,    98,    12,    17,    19,    37,    66,    67,
      90,    91,   104,   105,   106,   107,   108,   109,   110,   111,
     112,   113,   114,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   152,   153,   268,   323,   246,   249,   180,   270,
      18,   323,   323,   292,   246,   292,   246,   180,     8,    10,
      11,   240,   241,   242,   255,   259,   276,   288,   180,   218,
     219,   235,   218,   218,   234,   218,   218,   292,     6,     9,
      13,   220,   221,   241,    17,    19,    14,    15,    16,    20,
      32,    33,   142,   143,   154,   155,   157,   159,   161,   171,
     229,    13,   235,    47,   254,    45,   149,   253,   254,    18,
     246,   246,    21,    36,    38,    38,   246,   246,   246,   246,
     180,   180,    18,    21,   146,    31,    30,    31,    86,    87,
      30,   146,    31,   118,   149,    13,   149,    59,   146,    61,
     149,   291,   292,   291,    13,   146,   292,    17,   291,   292,
     118,   146,   149,    13,   146,    13,    13,   151,    31,   292,
     292,   291,   292,   292,   292,   292,   180,   325,    92,   180,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   321,
     327,   180,   325,    18,   146,   149,   180,   149,    49,   146,
     271,   272,   146,    18,    12,    12,   172,   245,   277,   278,
     279,   280,   278,    47,   245,   289,   290,   173,   240,   118,
     146,    13,    33,    13,    13,    13,    13,    13,   145,   180,
     223,   180,   222,    14,    15,    16,    17,    20,    22,    23,
      24,    25,    26,    32,    33,    58,    60,    64,   101,   103,
     142,   147,   162,   163,   164,   167,   225,   247,   314,   315,
     317,    13,   221,   291,   180,   217,   217,   217,   292,   217,
     217,   292,   230,   292,   292,   217,   217,   292,   292,   292,
     217,   321,   246,   246,   252,   246,   246,   292,   321,   327,
     329,   292,   292,   292,   309,   329,   292,   246,   292,   292,
     300,   292,   304,   305,   323,    18,    18,   292,   328,   146,
     292,    18,   146,   292,   321,   246,   292,   330,   292,   292,
     329,    18,    18,    86,   323,   246,    17,    19,   265,   246,
     292,   180,    50,   274,   321,   292,   292,   240,   180,   278,
     292,   180,   312,   290,   292,   219,   217,   219,   235,   217,
     217,   217,   142,   217,   217,   218,    12,   180,    12,   180,
     218,   218,   218,    99,   138,   139,   140,   180,   225,   227,
     228,   234,    55,   283,   284,   292,   320,   292,   218,   218,
     299,   292,   310,   292,    17,    17,    19,   292,   292,   291,
     292,   310,   165,   180,   321,   326,   292,    14,    15,    16,
      20,    32,    33,   142,   147,   154,   155,   157,   159,   161,
     171,   173,   229,    17,    19,   100,   225,    18,    32,   158,
     145,   144,   146,    28,   156,    46,   173,    13,    21,    13,
      30,    30,    13,    30,    62,    63,   302,   146,   173,    27,
     246,    18,   292,    13,   151,   292,   149,   323,    17,    46,
     149,   292,    18,   173,   149,   267,   149,    18,   292,    13,
     292,    12,   224,   225,   234,    12,    13,    13,    13,   311,
     312,   235,   235,   235,    18,    13,    33,    13,    56,    57,
     285,   286,   287,   271,    21,    21,    13,    13,    59,    27,
      61,    31,   173,   149,   247,   313,   314,   292,   145,   148,
      13,   173,   321,    12,    59,    59,   163,   225,   225,   225,
     292,   225,   225,   292,   292,   292,   225,   225,   292,   292,
     292,   291,    94,   292,   173,   292,   217,   217,   292,   292,
     217,   292,   292,   292,   292,   292,   292,    65,   305,   292,
      18,    18,   292,    18,   246,    18,   323,    53,    54,   266,
     292,   246,    19,   246,   281,   269,   270,   246,    17,   145,
     217,   292,    13,   224,   225,   225,    20,    13,   146,   225,
     228,   225,   286,   291,   274,    27,   141,    20,   225,   225,
     225,   225,   292,   318,   319,   323,   100,    18,    33,    18,
     218,   218,    17,   118,   292,   292,   292,   225,    31,    32,
     158,    31,   145,    31,   156,    18,   292,    21,    28,    31,
      21,    30,    30,    61,    27,   151,    18,   271,   102,   102,
     180,   283,    59,   100,   292,   180,   217,   225,   292,   225,
     312,    18,    18,   149,   225,   225,   292,    62,    63,   316,
      27,   146,   173,    27,   314,    13,    13,   225,   226,   292,
      38,   163,   163,   292,   292,   225,   292,   225,   292,   225,
     217,   292,   329,   292,   292,   273,   274,    19,   246,    19,
     246,   265,   271,   292,   292,    18,    21,    18,    17,    17,
     246,    21,    31,   292,   225,    62,    63,   225,    65,   319,
     225,    20,   225,    18,   146,   163,   292,   225,   225,    32,
      21,    31,   145,   148,    13,   144,   302,   173,    51,   275,
      46,   274,   225,   291,   291,   225,   292,    61,   292,   225,
      27,   292,   225,   225,   163,   166,   292,   225,   292,   225,
     225,   292,   292,    53,    54,   225,   282,    18,    18,    21,
     225,    61,   225,    21,   225,   292,    31,   145,   144,   271,
     225,   225,   173,   225,   163,   292,   225,   273,   225,    21,
     225
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
     260,   261,   261,   262,   263,   264,   265,   265,   265,   266,
     266,   266,   267,   267,   268,   268,   269,   269,   270,   270,
     271,   271,   272,   273,   273,   274,   275,   275,   276,   276,
     277,   277,   278,   278,   279,   280,   281,   281,   281,   281,
     282,   282,   282,   283,   283,   284,   285,   285,   286,   286,
     287,   287,   288,   288,   289,   289,   290,   290,   291,   291,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   293,   293,   294,   295,   296,   296,
     296,   297,   298,   299,   299,   300,   300,   301,   302,   302,
     303,   303,   304,   304,   305,   306,   306,   306,   306,   306,
     306,   306,   306,   306,   306,   306,   306,   306,   306,   306,
     306,   306,   306,   306,   306,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   308,   308,
     309,   310,   310,   311,   311,   312,   312,   312,   313,   313,
     314,   315,   315,   316,   316,   317,   317,   318,   318,   319,
     320,   321,   321,   322,   322,   322,   322,   323,   323,   324,
     324,   325,   325,   325,   325,   325,   325,   325,   326,   326,
     327,   328,   329,   329,   330,   330,   331,   332,   333,   333
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
       1,     2,     2,     5,     5,     3,     3,     1,     2,     1,
       2,     1,     1,     6,     2,    10,     1,     3,     4,     1,
       1,     1,     1,     3,     3,     5,     0,     1,     3,     5,
       1,     0,     2,     1,     0,     2,     2,     0,     1,     2,
       1,     2,     1,     1,    10,     7,     3,     3,     3,     3,
       1,     1,     1,     1,     0,     2,     1,     2,     2,     4,
       1,     1,     1,     2,     1,     2,     2,     2,     1,     3,
       1,     1,     1,     1,     1,     1,     3,     1,     1,     2,
       3,     3,     3,     2,     4,     6,     3,     3,     3,     3,
       3,     4,     1,     1,     1,     1,     4,     4,     4,     4,
       2,     3,     5,     7,     5,     1,     3,     5,     7,     1,
       3,     5,     7,     4,     4,     4,     4,     5,     6,     4,
       6,     3,     5,     7,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     3,     1,     1,     5,     2,     5,
       5,     9,     1,     3,     3,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     1,     3,
       3,     3,     5,     1,     3,     3,     5,     5,     1,     3,
       3,     7,     6,     4,     5,     5,     9,     1,     3,     3,
       3,     1,     1,     1,     6,     2,     4,     1,     3,     1,
       1,     1,     1,     1,     1,     1,     1,     3,     1,     1,
       3,     3,     1,     3,     1,     1,     3,     3,     1,     3
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
     218,    13,   142,   292,   145,   217,    -1,    32,   218,    13,
     217,    -1,    16,   218,    13,   217,    -1,   235,    -1,   219,
      -1,   218,    33,   235,    -1,   218,    33,   219,    -1,   180,
     118,   292,    -1,   180,   146,   219,    -1,   221,    -1,   220,
     221,    -1,   241,    -1,     9,   222,    -1,     6,    -1,     6,
     223,    -1,   180,    12,   224,    -1,   222,   180,    12,   224,
      -1,   180,    12,   292,    -1,   223,   180,    12,   292,    -1,
     225,    -1,   234,    13,   225,    -1,    22,    -1,    23,    -1,
      24,    -1,    25,    -1,    26,   292,    -1,    17,   225,    18,
      -1,    20,   292,    21,    27,   225,    -1,    20,   292,    21,
     141,   225,    -1,   225,    33,   225,    -1,   225,    15,   225,
      -1,   225,    14,   225,    -1,   225,   155,   225,    -1,   225,
     154,   292,   156,   225,    -1,   225,   157,   225,    -1,   225,
      20,   292,   158,   225,    -1,   225,   171,   292,    -1,   225,
     161,   292,    -1,   225,   159,   292,    -1,   225,   229,    -1,
     162,   291,    13,    17,   226,    18,    -1,   225,   147,   292,
      31,   292,   148,   225,    -1,   225,    16,   225,    -1,   225,
     142,   292,    31,   292,   145,   225,    -1,   225,    32,   225,
      -1,   225,    20,   292,    31,   292,    32,   292,    31,   292,
      21,   225,    -1,   225,    20,   292,    32,   292,    21,   225,
      -1,   225,   142,   292,    31,   292,    31,   292,   145,   225,
      -1,   225,   142,   292,   145,   225,    -1,    17,   227,    13,
     225,    18,    -1,    17,   227,    13,   225,    18,    17,   291,
      18,    -1,    17,   234,    13,   225,    18,    17,   291,    18,
      -1,    33,   218,    13,   225,    -1,    15,   218,    13,   225,
      -1,    14,   218,    13,   225,    -1,   147,   292,   148,   218,
      13,   225,    -1,    16,   218,    13,    20,   292,    21,   225,
      -1,   142,   292,   145,   218,    13,    20,   292,    21,   225,
      -1,    32,   218,    13,    20,   292,    31,   292,    21,   225,
      -1,    32,   218,    13,    20,   292,    21,   225,    -1,    58,
     299,    59,   225,    -1,    17,    99,   311,    13,   225,    18,
      -1,   315,    -1,    60,   310,   173,    -1,   163,   310,   173,
      -1,   314,    -1,   101,    17,   313,    18,    -1,   247,    19,
      -1,   247,    17,   291,    18,    -1,    20,   320,    21,    -1,
     103,    19,    -1,   103,    17,   292,    18,    -1,   247,   100,
      94,   292,    -1,   317,    -1,   164,   326,    59,   292,   163,
     225,    -1,   164,   321,    59,   292,   163,   225,    -1,   164,
     165,   321,   118,   292,   163,   225,    -1,   164,   180,    12,
     292,    38,   292,   163,   225,    -1,   164,   180,    12,   292,
      38,   292,   166,   292,   163,   225,    -1,   167,   292,   163,
     225,    -1,   225,    -1,   226,   146,   225,    -1,   228,    -1,
     227,    33,   228,    -1,   140,   235,    -1,   139,   235,    -1,
     138,   235,    -1,   143,   230,   144,    -1,   143,   292,    28,
     292,    31,   329,   144,    -1,   143,   292,    28,   292,    31,
     329,    13,   292,   144,    -1,   292,    28,   292,    -1,   230,
     146,   292,    28,   292,    -1,     4,    -1,     4,   232,    -1,
     233,    -1,   232,   233,    -1,   180,    -1,   235,    -1,   235,
      -1,   234,   235,    -1,   180,   149,   246,    -1,   180,   146,
     235,    -1,     5,    -1,     5,   237,    -1,   238,    -1,   237,
     238,    -1,   180,    12,   292,    -1,   242,    -1,   255,    -1,
     259,    -1,   241,    -1,   241,   240,    -1,   242,    -1,   255,
      -1,   259,    -1,   276,    -1,   288,    -1,    10,   278,    -1,
       3,    -1,     3,   243,    -1,     3,   243,    33,    -1,   244,
      -1,   243,    33,   244,    -1,   245,   180,    12,   246,    -1,
     245,   180,    12,   246,   254,    -1,   245,   180,    34,   252,
      -1,   245,   180,    34,   252,   254,    -1,   176,    -1,   177,
      -1,   178,    -1,   179,    -1,    -1,    17,   246,    18,    -1,
     248,    -1,   298,    -1,    35,   180,    36,   252,   173,    -1,
     246,    31,   246,    -1,   246,    37,   246,    -1,    20,   246,
      21,    -1,    43,   246,    -1,    41,   246,    -1,    42,   246,
      -1,    40,   246,    38,   246,    -1,    39,   246,    38,   246,
      -1,   249,    -1,   247,    -1,   180,    -1,   247,    90,   180,
      -1,   247,   104,   180,    -1,   188,    -1,   189,    -1,   190,
      -1,   191,    -1,   192,    -1,   193,    -1,   194,    -1,   195,
      -1,   250,    -1,   251,    -1,   246,    44,   246,    -1,    19,
      44,   246,    -1,   246,    27,   246,    -1,    19,    27,   246,
      -1,   253,    -1,   252,   253,    -1,   246,    -1,   180,   149,
     246,    -1,   180,    45,   246,    -1,    47,   321,    46,   292,
      -1,    48,    -1,    48,   256,    -1,   257,    -1,   256,   257,
      -1,   245,   258,    -1,   180,   149,   246,    12,   292,    -1,
     322,   149,   246,    12,   292,    -1,   180,    12,   292,    -1,
     322,    12,   292,    -1,     7,    -1,     7,   260,    -1,   261,
      -1,   260,   261,    -1,   262,    -1,   263,    -1,   245,   180,
     267,   270,   271,   274,    -1,   245,   264,    -1,   180,   149,
     249,   180,   265,    46,   266,   271,   273,   275,    -1,    19,
      -1,    17,   323,    18,    -1,   265,    17,   323,    18,    -1,
     292,    -1,    53,    -1,    54,    -1,    19,    -1,    17,   268,
      18,    -1,   323,   149,   246,    -1,   268,   146,   323,   149,
     246,    -1,    -1,   270,    -1,   180,   149,   246,    -1,   270,
     146,   180,   149,   246,    -1,   272,    -1,    -1,    49,   292,
      -1,   274,    -1,    -1,    50,   292,    -1,    51,   292,    -1,
      -1,     8,    -1,     8,   277,    -1,   278,    -1,   277,   278,
      -1,   280,    -1,   279,    -1,   245,   180,   149,   281,   180,
     265,    46,   282,   271,   273,    -1,   245,   180,   267,   269,
     283,   271,   274,    -1,   246,   102,   246,    -1,    19,   102,
     246,    -1,   246,   102,    19,    -1,    19,   102,    19,    -1,
     225,    -1,    53,    -1,    54,    -1,   284,    -1,    -1,    55,
     285,    -1,   286,    -1,   285,   286,    -1,   287,   291,    -1,
     287,   291,   149,   246,    -1,    56,    -1,    57,    -1,    11,
      -1,    11,   289,    -1,   290,    -1,   289,   290,    -1,   245,
     312,    -1,    47,   292,    -1,   292,    -1,   291,   146,   292,
      -1,   296,    -1,   293,    -1,   295,    -1,   294,    -1,   297,
      -1,   298,    -1,    17,   292,    18,    -1,   181,    -1,   180,
      -1,   180,    98,    -1,   292,    90,   180,    -1,   292,   104,
     180,    -1,   292,    91,    92,    -1,   292,    19,    -1,   292,
      17,   291,    18,    -1,   292,    17,   292,    86,   292,    18,
      -1,   292,    90,   325,    -1,   292,   153,   180,    -1,   292,
     153,   325,    -1,   292,   152,   321,    -1,   292,   152,   327,
      -1,    58,   299,    59,   292,    -1,   301,    -1,   303,    -1,
     306,    -1,   307,    -1,   134,   329,    13,   292,    -1,   135,
     329,    13,   292,    -1,   136,   326,    13,   292,    -1,    52,
     326,    13,   292,    -1,    29,    30,    -1,    29,   291,    30,
      -1,    29,   292,    31,   329,    30,    -1,    29,   292,    31,
     329,    13,   292,    30,    -1,    29,   292,    86,   292,    30,
      -1,    15,    -1,    20,   291,    21,    -1,    20,   292,    31,
     327,    21,    -1,    20,   292,    31,   327,    13,   292,    21,
      -1,   170,    -1,    29,   308,    30,    -1,    29,   309,    31,
     329,    30,    -1,    29,   309,    31,   329,    13,   292,    30,
      -1,    88,    17,   291,    18,    -1,    89,    17,   291,    18,
      -1,    93,   333,    13,   292,    -1,   168,    17,   292,    18,
      -1,    95,   248,    17,   292,    18,    -1,    95,    17,   292,
     146,   246,    18,    -1,    96,    17,   291,    18,    -1,    97,
      17,   292,   146,   292,    18,    -1,   150,   291,   151,    -1,
     150,   292,    31,   329,   151,    -1,   150,   292,    31,   329,
      13,   292,   151,    -1,   183,    -1,   182,    -1,   184,    -1,
     185,    -1,    92,    -1,   187,    -1,   160,    -1,   137,    -1,
     186,    -1,   300,    -1,   299,   146,   300,    -1,   258,    -1,
     264,    -1,    60,   292,    61,   292,   302,    -1,    63,   292,
      -1,    62,   292,    61,   292,   302,    -1,    64,   292,   149,
     304,   173,    -1,    64,   292,   149,   304,   146,    65,    27,
     292,   173,    -1,   305,    -1,   304,   146,   305,    -1,   323,
      27,   292,    -1,    66,   292,    -1,    67,   292,    -1,    68,
     292,    -1,    69,   292,    -1,    70,   292,    -1,    71,   292,
      -1,    72,   292,    -1,    73,   292,    -1,    74,   292,    -1,
      75,   292,    -1,    76,   292,    -1,    77,   292,    -1,    78,
     292,    -1,    79,   292,    -1,    80,   292,    -1,    81,   292,
      -1,    82,   292,    -1,    83,   292,    -1,    84,   292,    -1,
      85,   292,    -1,   292,    66,   292,    -1,   292,    37,   292,
      -1,   292,    67,   292,    -1,   292,   106,   292,    -1,   292,
     105,   292,    -1,   292,   107,   292,    -1,   292,   108,   292,
      -1,   292,   109,   292,    -1,   292,   110,   292,    -1,   292,
     111,   292,    -1,   292,   112,   292,    -1,   292,    12,   292,
      -1,   292,   113,   292,    -1,   292,   114,   292,    -1,   292,
     115,   292,    -1,   292,   116,   292,    -1,   292,   117,   292,
      -1,   292,   118,   292,    -1,   292,   119,   292,    -1,   292,
     120,   292,    -1,   292,   121,   292,    -1,   292,   122,   292,
      -1,   292,   123,   292,    -1,   292,   124,   292,    -1,   292,
     125,   292,    -1,   292,   126,   292,    -1,   292,   127,   292,
      -1,   292,   128,   292,    -1,   292,   129,   292,    -1,   292,
     130,   292,    -1,   292,   131,   292,    -1,   292,   132,   292,
      -1,   292,   133,   292,    -1,   309,    -1,   308,   146,   309,
      -1,   292,    87,   292,    -1,   292,    27,   225,    -1,   310,
      31,   292,    27,   225,    -1,   312,    -1,   311,   146,   312,
      -1,   180,   149,   246,    -1,   180,   149,   246,   100,   292,
      -1,   180,   149,   246,    59,   292,    -1,   314,    -1,   313,
      33,   314,    -1,   247,   100,   292,    -1,    60,   292,    61,
     225,   316,    63,   225,    -1,    60,   292,    61,   225,    63,
     225,    -1,    62,   292,    61,   225,    -1,   316,    62,   292,
      61,   225,    -1,    64,   292,   149,   318,   173,    -1,    64,
     292,   149,   318,   146,    65,    27,   225,   173,    -1,   319,
      -1,   318,   146,   319,    -1,   323,    27,   225,    -1,   283,
     271,   274,    -1,   324,    -1,   322,    -1,   325,    -1,    88,
      17,   323,   146,   321,    18,    -1,    89,    19,    -1,    89,
      17,   323,    18,    -1,   321,    -1,   323,   146,   321,    -1,
     180,    -1,    67,    -1,   296,    -1,   293,    -1,   295,    -1,
     294,    -1,   297,    -1,   298,    -1,    17,   292,    18,    -1,
     327,    -1,   328,    -1,   321,   118,   292,    -1,   321,   149,
     246,    -1,   330,    -1,   329,   146,   330,    -1,   331,    -1,
     332,    -1,   323,   118,   292,    -1,   323,   149,   246,    -1,
     328,    -1,   333,   146,   328,    -1
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
     856,   858,   861,   864,   870,   876,   880,   884,   886,   889,
     891,   894,   896,   898,   905,   908,   919,   921,   925,   930,
     932,   934,   936,   938,   942,   946,   952,   953,   955,   959,
     965,   967,   968,   971,   973,   974,   977,   980,   981,   983,
     986,   988,   991,   993,   995,  1006,  1014,  1018,  1022,  1026,
    1030,  1032,  1034,  1036,  1038,  1039,  1042,  1044,  1047,  1050,
    1055,  1057,  1059,  1061,  1064,  1066,  1069,  1072,  1075,  1077,
    1081,  1083,  1085,  1087,  1089,  1091,  1093,  1097,  1099,  1101,
    1104,  1108,  1112,  1116,  1119,  1124,  1131,  1135,  1139,  1143,
    1147,  1151,  1156,  1158,  1160,  1162,  1164,  1169,  1174,  1179,
    1184,  1187,  1191,  1197,  1205,  1211,  1213,  1217,  1223,  1231,
    1233,  1237,  1243,  1251,  1256,  1261,  1266,  1271,  1277,  1284,
    1289,  1296,  1300,  1306,  1314,  1316,  1318,  1320,  1322,  1324,
    1326,  1328,  1330,  1332,  1334,  1338,  1340,  1342,  1348,  1351,
    1357,  1363,  1373,  1375,  1379,  1383,  1386,  1389,  1392,  1395,
    1398,  1401,  1404,  1407,  1410,  1413,  1416,  1419,  1422,  1425,
    1428,  1431,  1434,  1437,  1440,  1443,  1447,  1451,  1455,  1459,
    1463,  1467,  1471,  1475,  1479,  1483,  1487,  1491,  1495,  1499,
    1503,  1507,  1511,  1515,  1519,  1523,  1527,  1531,  1535,  1539,
    1543,  1547,  1551,  1555,  1559,  1563,  1567,  1571,  1575,  1577,
    1581,  1585,  1589,  1595,  1597,  1601,  1605,  1611,  1617,  1619,
    1623,  1627,  1635,  1642,  1647,  1653,  1659,  1669,  1671,  1675,
    1679,  1683,  1685,  1687,  1689,  1696,  1699,  1704,  1706,  1710,
    1712,  1714,  1716,  1718,  1720,  1722,  1724,  1726,  1730,  1732,
    1734,  1738,  1742,  1744,  1748,  1750,  1752,  1756,  1760,  1762
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   548,   548,   556,   562,   573,   574,   575,   576,   577,
     581,   592,   625,   653,   687,   694,   702,   708,   714,   723,
     732,   738,   744,   750,   765,   772,   787,   805,   812,   819,
     829,   843,   852,   862,   872,   882,   890,   898,   909,   922,
     932,   940,   951,   957,   963,   969,   986,   990,   997,  1003,
    1019,  1023,  1030,  1034,  1041,  1058,  1084,  1088,  1095,  1099,
    1106,  1111,  1116,  1121,  1126,  1132,  1149,  1172,  1179,  1186,
    1193,  1200,  1215,  1222,  1237,  1253,  1260,  1267,  1274,  1286,
    1300,  1311,  1325,  1336,  1350,  1367,  1384,  1400,  1419,  1426,
    1437,  1444,  1452,  1460,  1471,  1484,  1497,  1511,  1524,  1536,
    1545,  1555,  1559,  1567,  1587,  1591,  1598,  1602,  1606,  1617,
    1621,  1638,  1675,  1685,  1701,  1717,  1726,  1737,  1748,  1763,
    1769,  1778,  1784,  1793,  1799,  1805,  1814,  1820,  1833,  1858,
    1869,  1889,  1904,  1925,  1931,  1948,  1969,  1990,  1996,  2005,
    2018,  2028,  2044,  2064,  2070,  2082,  2098,  2102,  2106,  2113,
    2119,  2128,  2132,  2136,  2140,  2144,  2154,  2168,  2178,  2190,
    2205,  2211,  2220,  2240,  2261,  2290,  2331,  2332,  2333,  2341,
    2342,  2346,  2347,  2348,  2349,  2350,  2351,  2352,  2353,  2354,
    2355,  2356,  2357,  2358,  2367,  2371,  2372,  2373,  2377,  2378,
    2379,  2380,  2381,  2382,  2383,  2384,  2388,  2389,  2393,  2394,
    2398,  2399,  2403,  2404,  2408,  2409,  2410,  2414,  2425,  2426,
    2431,  2432,  2436,  2440,  2441,  2442,  2443,  2447,  2448,  2453,
    2454,  2458,  2459,  2463,  2467,  2471,  2476,  2477,  2478,  2482,
    2483,  2484,  2488,  2489,  2493,  2494,  2497,  2499,  2503,  2504,
    2508,  2509,  2513,  2517,  2518,  2522,  2533,  2534,  2538,  2539,
    2546,  2547,  2551,  2552,  2556,  2560,  2564,  2565,  2566,  2567,
    2571,  2572,  2573,  2577,  2578,  2582,  2586,  2587,  2591,  2592,
    2596,  2597,  2619,  2620,  2625,  2626,  2630,  2631,  2635,  2636,
    2641,  2642,  2643,  2644,  2645,  2646,  2648,  2649,  2650,  2651,
    2652,  2653,  2654,  2655,  2656,  2657,  2658,  2660,  2661,  2662,
    2663,  2665,  2666,  2667,  2668,  2669,  2671,  2672,  2673,  2674,
    2676,  2677,  2679,  2680,  2682,  2684,  2695,  2697,  2698,  2700,
    2701,  2703,  2704,  2706,  2708,  2710,  2719,  2720,  2721,  2726,
    2734,  2736,  2737,  2738,  2744,  2745,  2749,  2753,  2757,  2758,
    2759,  2763,  2767,  2773,  2774,  2778,  2779,  2783,  2787,  2788,
    2792,  2793,  2797,  2798,  2802,  2806,  2807,  2808,  2809,  2810,
    2811,  2812,  2813,  2814,  2815,  2816,  2817,  2818,  2819,  2820,
    2821,  2822,  2823,  2824,  2825,  2829,  2830,  2831,  2832,  2833,
    2834,  2835,  2836,  2837,  2838,  2839,  2840,  2841,  2842,  2843,
    2844,  2845,  2846,  2847,  2848,  2849,  2850,  2851,  2852,  2853,
    2854,  2855,  2856,  2857,  2858,  2859,  2860,  2861,  2865,  2866,
    2870,  2874,  2875,  2879,  2880,  2884,  2885,  2891,  2898,  2899,
    2912,  2916,  2917,  2921,  2922,  2926,  2927,  2931,  2932,  2936,
    2940,  2944,  2945,  2949,  2951,  2960,  2961,  2965,  2966,  2970,
    2972,  2977,  2978,  2979,  2980,  2981,  2982,  2983,  2987,  2988,
    2992,  2996,  3000,  3001,  3005,  3006,  3010,  3014,  3018,  3019
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

  private static final int yylast_ = 11952;
  private static final int yynnts_ = 123;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 43;
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
    return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
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
/* Line 5118 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


