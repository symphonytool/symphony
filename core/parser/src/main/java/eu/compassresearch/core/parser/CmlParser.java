
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
/* Line 1000 of "src/main/bison/cml.y"  */
    {
  LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  AExpressionSingleDeclaration decls = (AExpressionSingleDeclaration)((yystack.valueAt (3-(3))));
  decls.getIdentifiers().add(0, id);
  decls.setLocation(extractLexLocation(id.getLocation(),
				       exp.getLocation()));
  yyval = decls;
};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1012 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = processParagraphList;
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1018 of "src/main/bison/cml.y"  */
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
/* Line 1034 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1038 of "src/main/bison/cml.y"  */
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
/* Line 1045 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1049 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1056 of "src/main/bison/cml.y"  */
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
/* Line 1073 of "src/main/bison/cml.y"  */
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
/* Line 1099 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1103 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1110 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{new Vector<ATypeSingleDeclaration>(), ((yystack.valueAt (1-(1))))};
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1114 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3))))};
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1121 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASkipAction(location);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1126 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AStopAction(location);
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1131 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AChaosAction(location);
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1136 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ADivAction(location);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1141 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
  yyval = new AWaitAction(location, exp);
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1147 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  PAction action = (PAction)((yystack.valueAt (3-(2))));
  yyval = new ABlockStatementAction(location, null, action);
};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1164 of "src/main/bison/cml.y"  */
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
/* Line 1187 of "src/main/bison/cml.y"  */
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
/* Line 1194 of "src/main/bison/cml.y"  */
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
/* Line 1201 of "src/main/bison/cml.y"  */
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
/* Line 1208 of "src/main/bison/cml.y"  */
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
/* Line 1215 of "src/main/bison/cml.y"  */
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
/* Line 1230 of "src/main/bison/cml.y"  */
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
/* Line 1237 of "src/main/bison/cml.y"  */
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
/* Line 1252 of "src/main/bison/cml.y"  */
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
/* Line 1268 of "src/main/bison/cml.y"  */
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
/* Line 1275 of "src/main/bison/cml.y"  */
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
/* Line 1282 of "src/main/bison/cml.y"  */
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
/* Line 1289 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PAction action = (PAction)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingAction(combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1301 of "src/main/bison/cml.y"  */
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
/* Line 1315 of "src/main/bison/cml.y"  */
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
/* Line 1326 of "src/main/bison/cml.y"  */
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
/* Line 1340 of "src/main/bison/cml.y"  */
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
/* Line 1351 of "src/main/bison/cml.y"  */
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
/* Line 1365 of "src/main/bison/cml.y"  */
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
/* Line 1382 of "src/main/bison/cml.y"  */
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
/* Line 1399 of "src/main/bison/cml.y"  */
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
/* Line 1415 of "src/main/bison/cml.y"  */
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
/* Line 1434 of "src/main/bison/cml.y"  */
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
/* Line 1441 of "src/main/bison/cml.y"  */
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
/* Line 1452 of "src/main/bison/cml.y"  */
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
/* Line 1459 of "src/main/bison/cml.y"  */
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
/* Line 1467 of "src/main/bison/cml.y"  */
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
/* Line 1475 of "src/main/bison/cml.y"  */
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
/* Line 1486 of "src/main/bison/cml.y"  */
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
/* Line 1499 of "src/main/bison/cml.y"  */
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
/* Line 1512 of "src/main/bison/cml.y"  */
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
/* Line 1526 of "src/main/bison/cml.y"  */
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
/* Line 1539 of "src/main/bison/cml.y"  */
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
/* Line 1551 of "src/main/bison/cml.y"  */
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
/* Line 1560 of "src/main/bison/cml.y"  */
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
/* Line 1570 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 102:
  if (yyn == 102)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1574 of "src/main/bison/cml.y"  */
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
/* Line 1582 of "src/main/bison/cml.y"  */
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
/* Line 1602 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 105:
  if (yyn == 105)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1606 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AMultipleGeneralAssignmentStatementAction(location,
						     (List<? extends ASingleGeneralAssignmentStatementAction>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1613 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1617 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 108:
  if (yyn == 108)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1621 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 109:
  if (yyn == 109)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1632 of "src/main/bison/cml.y"  */
    {
  yyval = new AReturnStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), null);
};
  break;
    

  case 110:
  if (yyn == 110)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1636 of "src/main/bison/cml.y"  */
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
/* Line 1653 of "src/main/bison/cml.y"  */
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
/* Line 1690 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 113:
  if (yyn == 113)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1700 of "src/main/bison/cml.y"  */
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
/* Line 1716 of "src/main/bison/cml.y"  */
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
/* Line 1732 of "src/main/bison/cml.y"  */
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
/* Line 1741 of "src/main/bison/cml.y"  */
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
/* Line 1752 of "src/main/bison/cml.y"  */
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
/* Line 1763 of "src/main/bison/cml.y"  */
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
/* Line 1778 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = new LinkedList<PAction>();
  actionList.add((PAction)((yystack.valueAt (1-(1)))));
  yyval = actionList;
};
  break;
    

  case 120:
  if (yyn == 120)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1784 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = (List<PAction>)((yystack.valueAt (3-(1))));
  actionList.add((PAction)((yystack.valueAt (3-(3)))));
  yyval = actionList;
};
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1793 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add((PParametrisation)((yystack.valueAt (1-(1)))));
  yyval = plist;
};
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1799 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add(0,(PParametrisation)((yystack.valueAt (3-(3)))));
  yyval = plist;
};
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1808 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AValParametrisation(loc, decl);
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1814 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AResParametrisation(loc, decl);
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1820 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AVresParametrisation(loc, decl);
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1829 of "src/main/bison/cml.y"  */
    {
  yyval = new AEnumerationRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
							   (CmlLexeme)((yystack.valueAt (3-(3))))),
					(List<? extends ARenamePair>)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1835 of "src/main/bison/cml.y"  */
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
/* Line 1848 of "src/main/bison/cml.y"  */
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
/* Line 1873 of "src/main/bison/cml.y"  */
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
/* Line 1884 of "src/main/bison/cml.y"  */
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
/* Line 1904 of "src/main/bison/cml.y"  */
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
/* Line 1919 of "src/main/bison/cml.y"  */
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
/* Line 1940 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
  decls.add((AChannelNameDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 134:
  if (yyn == 134)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1946 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((AChannelNameDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1963 of "src/main/bison/cml.y"  */
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
/* Line 1984 of "src/main/bison/cml.y"  */
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
/* Line 2005 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = new Vector<ATypeSingleDeclaration>();
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 138:
  if (yyn == 138)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2011 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2020 of "src/main/bison/cml.y"  */
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
/* Line 2033 of "src/main/bison/cml.y"  */
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
/* Line 2043 of "src/main/bison/cml.y"  */
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
/* Line 2059 of "src/main/bison/cml.y"  */
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
/* Line 2079 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 144:
  if (yyn == 144)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2085 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(1))));
  defs.add((AChansetDefinition)((yystack.valueAt (2-(2)))));
  yyval = defs;
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2097 of "src/main/bison/cml.y"  */
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
/* Line 2113 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 147:
  if (yyn == 147)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2117 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2121 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2128 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2134 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  defs.add((PDefinition)((yystack.valueAt (2-(1)))));
  yyval = defs;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2143 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 152:
  if (yyn == 152)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2147 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 153:
  if (yyn == 153)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2151 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2155 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2159 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2169 of "src/main/bison/cml.y"  */
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
/* Line 2183 of "src/main/bison/cml.y"  */
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
/* Line 2193 of "src/main/bison/cml.y"  */
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
/* Line 2205 of "src/main/bison/cml.y"  */
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
/* Line 2220 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
  yyval = list;
};
  break;
    

  case 161:
  if (yyn == 161)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2226 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
  list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
  yyval = list;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2235 of "src/main/bison/cml.y"  */
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
/* Line 2255 of "src/main/bison/cml.y"  */
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
/* Line 2276 of "src/main/bison/cml.y"  */
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
/* Line 2305 of "src/main/bison/cml.y"  */
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
/* Line 2352 of "src/main/bison/cml.y"  */
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
/* Line 2359 of "src/main/bison/cml.y"  */
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
/* Line 2366 of "src/main/bison/cml.y"  */
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
/* Line 2373 of "src/main/bison/cml.y"  */
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
/* Line 2380 of "src/main/bison/cml.y"  */
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
/* Line 2390 of "src/main/bison/cml.y"  */
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
/* Line 2397 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 173:
  if (yyn == 173)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2401 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken quote = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteType(quote.location, false, null, quote);
};
  break;
    

  case 174:
  if (yyn == 174)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2406 of "src/main/bison/cml.y"  */
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
/* Line 2418 of "src/main/bison/cml.y"  */
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
/* Line 2452 of "src/main/bison/cml.y"  */
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
/* Line 2480 of "src/main/bison/cml.y"  */
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
/* Line 2487 of "src/main/bison/cml.y"  */
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
/* Line 2494 of "src/main/bison/cml.y"  */
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
/* Line 2501 of "src/main/bison/cml.y"  */
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
/* Line 2508 of "src/main/bison/cml.y"  */
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
/* Line 2516 of "src/main/bison/cml.y"  */
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
/* Line 2524 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 184:
  if (yyn == 184)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2536 of "src/main/bison/cml.y"  */
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
/* Line 2556 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  ids.add(id);
  yyval = ids;
};
  break;
    

  case 186:
  if (yyn == 186)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2564 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3)))));
  ids.add(id);
  yyval = ids;
};
  break;
    

  case 187:
  if (yyn == 187)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2572 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3)))));
  ids.add(id);
  yyval = ids;
};
  break;
    

  case 188:
  if (yyn == 188)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2583 of "src/main/bison/cml.y"  */
    {
  yyval = new ABooleanBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 189:
  if (yyn == 189)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2587 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 190:
  if (yyn == 190)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2591 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 191:
  if (yyn == 191)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2595 of "src/main/bison/cml.y"  */
    {
  yyval = new AIntNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 192:
  if (yyn == 192)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2599 of "src/main/bison/cml.y"  */
    {
  yyval = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 193:
  if (yyn == 193)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2603 of "src/main/bison/cml.y"  */
    {
  yyval = new ARealNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 194:
  if (yyn == 194)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2607 of "src/main/bison/cml.y"  */
    {
  yyval = new ACharBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 195:
  if (yyn == 195)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2611 of "src/main/bison/cml.y"  */
    {
  yyval = new ATokenBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 196:
  if (yyn == 196)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2618 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 197:
  if (yyn == 197)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2622 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 198:
  if (yyn == 198)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2629 of "src/main/bison/cml.y"  */
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
/* Line 2638 of "src/main/bison/cml.y"  */
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
/* Line 2650 of "src/main/bison/cml.y"  */
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
/* Line 2659 of "src/main/bison/cml.y"  */
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
/* Line 2671 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 203:
  if (yyn == 203)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2677 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = (List<AFieldField>)((yystack.valueAt (2-(2))));
  res.add((AFieldField)((yystack.valueAt (2-(1)))));
  yyval = res;
};
  break;
    

  case 204:
  if (yyn == 204)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2686 of "src/main/bison/cml.y"  */
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
/* Line 2694 of "src/main/bison/cml.y"  */
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
/* Line 2704 of "src/main/bison/cml.y"  */
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
/* Line 2717 of "src/main/bison/cml.y"  */
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
/* Line 2745 of "src/main/bison/cml.y"  */
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
/* Line 2757 of "src/main/bison/cml.y"  */
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
/* Line 2787 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 211:
  if (yyn == 211)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2793 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (2-(2))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(1))));
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 212:
  if (yyn == 212)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2803 of "src/main/bison/cml.y"  */
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
/* Line 2813 of "src/main/bison/cml.y"  */
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
/* Line 2837 of "src/main/bison/cml.y"  */
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
/* Line 2863 of "src/main/bison/cml.y"  */
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
/* Line 2883 of "src/main/bison/cml.y"  */
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
/* Line 2908 of "src/main/bison/cml.y"  */
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
/* Line 2922 of "src/main/bison/cml.y"  */
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
/* Line 2951 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = new Vector<PDefinition>();
  functionList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = functionList;
};
  break;
    

  case 220:
  if (yyn == 220)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2957 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = (List<PDefinition>)((yystack.valueAt (2-(1))));
  functionList.add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = functionList;
};
  break;
    

  case 221:
  if (yyn == 221)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2966 of "src/main/bison/cml.y"  */
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
/* Line 2990 of "src/main/bison/cml.y"  */
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
/* Line 3000 of "src/main/bison/cml.y"  */
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
/* Line 3022 of "src/main/bison/cml.y"  */
    {
  yyval =  new Vector<List<PPattern>>();
};
  break;
    

  case 225:
  if (yyn == 225)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3026 of "src/main/bison/cml.y"  */
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
/* Line 3033 of "src/main/bison/cml.y"  */
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
/* Line 3043 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 228:
  if (yyn == 228)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3047 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 229:
  if (yyn == 229)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3051 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 230:
  if (yyn == 230)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3058 of "src/main/bison/cml.y"  */
    {
  yyval = new Vector<APatternListTypePair>();
};
  break;
    

  case 231:
  if (yyn == 231)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3062 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 232:
  if (yyn == 232)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3069 of "src/main/bison/cml.y"  */
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
/* Line 3076 of "src/main/bison/cml.y"  */
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
/* Line 3086 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 235:
  if (yyn == 235)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3090 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 236:
  if (yyn == 236)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3097 of "src/main/bison/cml.y"  */
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
/* Line 3107 of "src/main/bison/cml.y"  */
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
/* Line 3119 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 239:
  if (yyn == 239)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3120 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 240:
  if (yyn == 240)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3124 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 241:
  if (yyn == 241)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3128 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 242:
  if (yyn == 242)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3129 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 243:
  if (yyn == 243)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3133 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 244:
  if (yyn == 244)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3144 of "src/main/bison/cml.y"  */
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
/* Line 3151 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 246:
  if (yyn == 246)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3158 of "src/main/bison/cml.y"  */
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
/* Line 3171 of "src/main/bison/cml.y"  */
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
/* Line 3192 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (1-(1)))));
  yyval = opDefinitions;
};
  break;
    

  case 249:
  if (yyn == 249)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3198 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)((yystack.valueAt (2-(1))));
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (2-(2)))));
  yyval = opDefinitions;
};
  break;
    

  case 250:
  if (yyn == 250)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3207 of "src/main/bison/cml.y"  */
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
/* Line 3234 of "src/main/bison/cml.y"  */
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
/* Line 3258 of "src/main/bison/cml.y"  */
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
/* Line 3270 of "src/main/bison/cml.y"  */
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
/* Line 3282 of "src/main/bison/cml.y"  */
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
/* Line 3294 of "src/main/bison/cml.y"  */
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
/* Line 3308 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 257:
  if (yyn == 257)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3312 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 258:
  if (yyn == 258)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3316 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), null, null);
};
  break;
    

  case 259:
  if (yyn == 259)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3322 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 260:
  if (yyn == 260)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3323 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 261:
  if (yyn == 261)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3327 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 262:
  if (yyn == 262)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3332 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)((yystack.valueAt (1-(1)))));
  yyval = infoList;
};
  break;
    

  case 263:
  if (yyn == 263)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3338 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = (Vector<AExternalClause>)((yystack.valueAt (2-(1))));
  infoList.add((AExternalClause)((yystack.valueAt (2-(2)))));
  yyval = infoList;
};
  break;
    

  case 264:
  if (yyn == 264)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3358 of "src/main/bison/cml.y"  */
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
/* Line 3367 of "src/main/bison/cml.y"  */
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
/* Line 3376 of "src/main/bison/cml.y"  */
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
/* Line 3385 of "src/main/bison/cml.y"  */
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
/* Line 3397 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.READ);
};
  break;
    

  case 269:
  if (yyn == 269)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3401 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.WRITE);
};
  break;
    

  case 270:
  if (yyn == 270)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3430 of "src/main/bison/cml.y"  */
    {
    AStateParagraphDefinition state = new AStateParagraphDefinition();
    state.setLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
    yyval  = state;
};
  break;
    

  case 271:
  if (yyn == 271)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3436 of "src/main/bison/cml.y"  */
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
/* Line 3452 of "src/main/bison/cml.y"  */
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
/* Line 3460 of "src/main/bison/cml.y"  */
    {
  AStateParagraphDefinition stateDef = (AStateParagraphDefinition)((yystack.valueAt (2-(1))));
  stateDef.getStateDefs().add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = stateDef;
};
  break;
    

  case 274:
  if (yyn == 274)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3469 of "src/main/bison/cml.y"  */
    {
  AAssignmentDefinition adef = (AAssignmentDefinition)((yystack.valueAt (2-(2))));
  adef.setAccess((AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1)))));
  yyval = adef;
};
  break;
    

  case 275:
  if (yyn == 275)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3475 of "src/main/bison/cml.y"  */
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
/* Line 3489 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)((yystack.valueAt (1-(1)))));
  yyval = exps;
};
  break;
    

  case 277:
  if (yyn == 277)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3495 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(1))));
  exps.add((PExp)((yystack.valueAt (3-(3)))));
  yyval = exps;
};
  break;
    

  case 278:
  if (yyn == 278)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3538 of "src/main/bison/cml.y"  */
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
/* Line 3550 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanConstExp(lit.location, lit);
};
  break;
    

  case 280:
  if (yyn == 280)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3555 of "src/main/bison/cml.y"  */
    {
    yyval = new ANilExp(((LexKeywordToken)((yystack.valueAt (1-(1))))).location);
};
  break;
    

  case 281:
  if (yyn == 281)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3559 of "src/main/bison/cml.y"  */
    {
    LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
    yyval = new ACharLiteralExp(lit.location, lit);
};
  break;
    

  case 282:
  if (yyn == 282)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3564 of "src/main/bison/cml.y"  */
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
/* Line 3576 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteLiteralExp(lit.location, lit);
};
  break;
    

  case 284:
  if (yyn == 284)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3582 of "src/main/bison/cml.y"  */
    {
  yyval = new ABracketedExp(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))), (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 285:
  if (yyn == 285)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3586 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 286:
  if (yyn == 286)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3590 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 287:
  if (yyn == 287)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3594 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 288:
  if (yyn == 288)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3598 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 289:
  if (yyn == 289)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3602 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 290:
  if (yyn == 290)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3606 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 291:
  if (yyn == 291)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3610 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 292:
  if (yyn == 292)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3614 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 293:
  if (yyn == 293)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3618 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 294:
  if (yyn == 294)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3622 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 295:
  if (yyn == 295)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3627 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 296:
  if (yyn == 296)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3631 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 297:
  if (yyn == 297)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3635 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 298:
  if (yyn == 298)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3639 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 299:
  if (yyn == 299)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3644 of "src/main/bison/cml.y"  */
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
/* Line 3651 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 301:
  if (yyn == 301)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3655 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 302:
  if (yyn == 302)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3659 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 303:
  if (yyn == 303)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3663 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 304:
  if (yyn == 304)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3668 of "src/main/bison/cml.y"  */
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
/* Line 3676 of "src/main/bison/cml.y"  */
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
/* Line 3684 of "src/main/bison/cml.y"  */
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
/* Line 3692 of "src/main/bison/cml.y"  */
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
/* Line 3701 of "src/main/bison/cml.y"  */
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
/* Line 3708 of "src/main/bison/cml.y"  */
    {
  LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<PExp> members = (List<PExp>)((yystack.valueAt (3-(2))));
  yyval = new ASetEnumSetExp(location, members);
};
  break;
    

  case 310:
  if (yyn == 310)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3715 of "src/main/bison/cml.y"  */
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
/* Line 3722 of "src/main/bison/cml.y"  */
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
/* Line 3731 of "src/main/bison/cml.y"  */
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
/* Line 3739 of "src/main/bison/cml.y"  */
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
/* Line 3755 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new ASeqEnumSeqExp(loc, exps);
};
  break;
    

  case 315:
  if (yyn == 315)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3762 of "src/main/bison/cml.y"  */
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
/* Line 3769 of "src/main/bison/cml.y"  */
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
/* Line 3778 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
};
  break;
    

  case 318:
  if (yyn == 318)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3783 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new AMapEnumMapExp(loc, maplets);
};
  break;
    

  case 319:
  if (yyn == 319)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3790 of "src/main/bison/cml.y"  */
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
/* Line 3797 of "src/main/bison/cml.y"  */
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
/* Line 3806 of "src/main/bison/cml.y"  */
    {
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new ATupleExp(loc, exprs);
};
  break;
    

  case 322:
  if (yyn == 322)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3813 of "src/main/bison/cml.y"  */
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
/* Line 3827 of "src/main/bison/cml.y"  */
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
/* Line 3842 of "src/main/bison/cml.y"  */
    {
  LexNameToken type = extractNameFromUNDERNAMEToken((CmlLexeme)((yystack.valueAt (4-(1)))));
  LexLocation loc = extractLexLocation(type.getLocation(),
				       (CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AIsExp(loc, type, (PExp)((yystack.valueAt (4-(3)))), null);
};
  break;
    

  case 325:
  if (yyn == 325)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3849 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 326:
  if (yyn == 326)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3853 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 327:
  if (yyn == 327)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3861 of "src/main/bison/cml.y"  */
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
/* Line 3878 of "src/main/bison/cml.y"  */
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
/* Line 3891 of "src/main/bison/cml.y"  */
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
/* Line 3901 of "src/main/bison/cml.y"  */
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
/* Line 3912 of "src/main/bison/cml.y"  */
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
/* Line 3927 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.FALSE, extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 333:
  if (yyn == 333)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3931 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.TRUE, extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 334:
  if (yyn == 334)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3938 of "src/main/bison/cml.y"  */
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
/* Line 3949 of "src/main/bison/cml.y"  */
    {
  yyval = new LexKeywordToken(VDMToken.NIL, extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 336:
  if (yyn == 336)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3956 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(lexeme);
  yyval = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
};
  break;
    

  case 337:
  if (yyn == 337)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3962 of "src/main/bison/cml.y"  */
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
/* Line 3969 of "src/main/bison/cml.y"  */
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
/* Line 3983 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexStringToken(lit.substring(1, lit.length()-2),
			  extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 340:
  if (yyn == 340)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3992 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexQuoteToken(lit.substring(1, lit.length()-2),
			 extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 341:
  if (yyn == 341)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4003 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 342:
  if (yyn == 342)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4009 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add((PDefinition)((yystack.valueAt (3-(3)))));
  yyval = defs;
};
  break;
    

  case 343:
  if (yyn == 343)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4017 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 344:
  if (yyn == 344)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4018 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 345:
  if (yyn == 345)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4023 of "src/main/bison/cml.y"  */
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
/* Line 4055 of "src/main/bison/cml.y"  */
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
/* Line 4062 of "src/main/bison/cml.y"  */
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
/* Line 4076 of "src/main/bison/cml.y"  */
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
/* Line 4083 of "src/main/bison/cml.y"  */
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
/* Line 4094 of "src/main/bison/cml.y"  */
    {
  ACasesExp casesExp = new ACasesExp();
  casesExp.getCases().add((ACaseAlternative)((yystack.valueAt (1-(1)))));
  yyval = casesExp;
};
  break;
    

  case 351:
  if (yyn == 351)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4100 of "src/main/bison/cml.y"  */
    {
  ACasesExp casesExp = (ACasesExp)((yystack.valueAt (3-(1))));
  casesExp.getCases().add((ACaseAlternative)((yystack.valueAt (3-(3)))));
  yyval = casesExp;
};
  break;
    

  case 352:
  if (yyn == 352)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4111 of "src/main/bison/cml.y"  */
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
/* Line 4127 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryPlusUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			      (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 354:
  if (yyn == 354)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4133 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryMinusUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			       (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 355:
  if (yyn == 355)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4139 of "src/main/bison/cml.y"  */
    {
  yyval = new AAbsoluteUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			     (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 356:
  if (yyn == 356)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4145 of "src/main/bison/cml.y"  */
    {
  yyval = new AFloorUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
					     ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			  (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 357:
  if (yyn == 357)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4151 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
					   ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			(PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 358:
  if (yyn == 358)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4157 of "src/main/bison/cml.y"  */
    {
  yyval = new ACardinalityUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						   ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
				(PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 359:
  if (yyn == 359)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4163 of "src/main/bison/cml.y"  */
    {
  yyval = new APowerSetUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			     (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 360:
  if (yyn == 360)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4169 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistUnionUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			      (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 361:
  if (yyn == 361)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4175 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistIntersectUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						     ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
				  (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 362:
  if (yyn == 362)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4181 of "src/main/bison/cml.y"  */
    {
  yyval = new AHeadUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
					    ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			 (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 363:
  if (yyn == 363)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4187 of "src/main/bison/cml.y"  */
    {
  yyval = new ATailUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
					    ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			 (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 364:
  if (yyn == 364)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4193 of "src/main/bison/cml.y"  */
    {
  yyval = new ALenUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
					   ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			(PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 365:
  if (yyn == 365)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4199 of "src/main/bison/cml.y"  */
    {
  yyval = new AElementsUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			     (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 366:
  if (yyn == 366)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4205 of "src/main/bison/cml.y"  */
    {
  yyval = new AIndicesUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
					       ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			    (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 367:
  if (yyn == 367)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4211 of "src/main/bison/cml.y"  */
    {
  yyval = new AReverseUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
					       ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			    (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 368:
  if (yyn == 368)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4217 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistConcatUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			       (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 369:
  if (yyn == 369)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4223 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapDomainUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			      (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 370:
  if (yyn == 370)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4229 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapRangeUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			     (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 371:
  if (yyn == 371)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4235 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistMergeUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			      (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 372:
  if (yyn == 372)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4241 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapInverseUnaryExp(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
			       (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 373:
  if (yyn == 373)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4250 of "src/main/bison/cml.y"  */
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
/* Line 4258 of "src/main/bison/cml.y"  */
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
/* Line 4266 of "src/main/bison/cml.y"  */
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
/* Line 4274 of "src/main/bison/cml.y"  */
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
/* Line 4282 of "src/main/bison/cml.y"  */
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
/* Line 4290 of "src/main/bison/cml.y"  */
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
/* Line 4298 of "src/main/bison/cml.y"  */
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
/* Line 4306 of "src/main/bison/cml.y"  */
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
/* Line 4314 of "src/main/bison/cml.y"  */
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
/* Line 4322 of "src/main/bison/cml.y"  */
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
/* Line 4330 of "src/main/bison/cml.y"  */
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
/* Line 4338 of "src/main/bison/cml.y"  */
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
/* Line 4346 of "src/main/bison/cml.y"  */
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
/* Line 4354 of "src/main/bison/cml.y"  */
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
/* Line 4362 of "src/main/bison/cml.y"  */
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
/* Line 4370 of "src/main/bison/cml.y"  */
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
/* Line 4378 of "src/main/bison/cml.y"  */
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
/* Line 4386 of "src/main/bison/cml.y"  */
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
/* Line 4394 of "src/main/bison/cml.y"  */
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
/* Line 4402 of "src/main/bison/cml.y"  */
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
/* Line 4410 of "src/main/bison/cml.y"  */
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
/* Line 4418 of "src/main/bison/cml.y"  */
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
/* Line 4426 of "src/main/bison/cml.y"  */
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
/* Line 4434 of "src/main/bison/cml.y"  */
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
/* Line 4442 of "src/main/bison/cml.y"  */
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
/* Line 4450 of "src/main/bison/cml.y"  */
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
/* Line 4458 of "src/main/bison/cml.y"  */
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
/* Line 4466 of "src/main/bison/cml.y"  */
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
/* Line 4474 of "src/main/bison/cml.y"  */
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
/* Line 4482 of "src/main/bison/cml.y"  */
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
/* Line 4490 of "src/main/bison/cml.y"  */
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
/* Line 4498 of "src/main/bison/cml.y"  */
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
/* Line 4506 of "src/main/bison/cml.y"  */
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
/* Line 4517 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 407:
  if (yyn == 407)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4523 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(1))));
  maplets.add((AMapletExp)((yystack.valueAt (3-(3)))));
  yyval = maplets;
};
  break;
    

  case 408:
  if (yyn == 408)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4531 of "src/main/bison/cml.y"  */
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
/* Line 4543 of "src/main/bison/cml.y"  */
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
/* Line 4556 of "src/main/bison/cml.y"  */
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
/* Line 4572 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (1-(1)))));
  yyval = assignmentDefs;
};
  break;
    

  case 412:
  if (yyn == 412)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4578 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)((yystack.valueAt (3-(1))));
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (3-(3)))));
  yyval = assignmentDefs;
};
  break;
    

  case 413:
  if (yyn == 413)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4587 of "src/main/bison/cml.y"  */
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
/* Line 4604 of "src/main/bison/cml.y"  */
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
/* Line 4620 of "src/main/bison/cml.y"  */
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
/* Line 4643 of "src/main/bison/cml.y"  */
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
/* Line 4650 of "src/main/bison/cml.y"  */
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
/* Line 4668 of "src/main/bison/cml.y"  */
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
/* Line 4683 of "src/main/bison/cml.y"  */
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
/* Line 4692 of "src/main/bison/cml.y"  */
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
/* Line 4704 of "src/main/bison/cml.y"  */
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
/* Line 4715 of "src/main/bison/cml.y"  */
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
/* Line 4729 of "src/main/bison/cml.y"  */
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
/* Line 4736 of "src/main/bison/cml.y"  */
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
/* Line 4747 of "src/main/bison/cml.y"  */
    {
  List<ACaseAlternativeAction> casesList = new LinkedList<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)((yystack.valueAt (1-(1)))));
  yyval = new ACasesStatementAction(null, null, casesList, null);
};
  break;
    

  case 426:
  if (yyn == 426)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4753 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (3-(1))));
  cases.getCases().add((ACaseAlternativeAction)((yystack.valueAt (3-(3)))));
  yyval = cases;
};
  break;
    

  case 427:
  if (yyn == 427)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4764 of "src/main/bison/cml.y"  */
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
/* Line 4776 of "src/main/bison/cml.y"  */
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
/* Line 4793 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 430:
  if (yyn == 430)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4794 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 431:
  if (yyn == 431)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4799 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 432:
  if (yyn == 432)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4803 of "src/main/bison/cml.y"  */
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
/* Line 4820 of "src/main/bison/cml.y"  */
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
/* Line 4831 of "src/main/bison/cml.y"  */
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
/* Line 4845 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)((yystack.valueAt (1-(1)))));
  yyval = patterns;
};
  break;
    

  case 436:
  if (yyn == 436)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4851 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  patterns.add((PPattern)((yystack.valueAt (3-(3)))));
  yyval = patterns;
};
  break;
    

  case 437:
  if (yyn == 437)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4860 of "src/main/bison/cml.y"  */
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
/* Line 4874 of "src/main/bison/cml.y"  */
    {
  yyval = new AIgnorePattern(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),
			  new LinkedList<PDefinition>(),
			  true);
};
  break;
    

  case 439:
  if (yyn == 439)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4884 of "src/main/bison/cml.y"  */
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
/* Line 4894 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 441:
  if (yyn == 441)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4899 of "src/main/bison/cml.y"  */
    {
  LexKeywordToken lit = (LexKeywordToken)((yystack.valueAt (1-(1))));
  yyval = new ANilPattern(lit.location, new LinkedList<PDefinition>(), true);
};
  break;
    

  case 442:
  if (yyn == 442)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4904 of "src/main/bison/cml.y"  */
    {
  LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
  yyval = new ACharacterPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 443:
  if (yyn == 443)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4909 of "src/main/bison/cml.y"  */
    {
  LexStringToken lit = (LexStringToken)((yystack.valueAt (1-(1))));
  yyval = new AStringPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 444:
  if (yyn == 444)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4914 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuotePattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 445:
  if (yyn == 445)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4919 of "src/main/bison/cml.y"  */
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
/* Line 4929 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 447:
  if (yyn == 447)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4930 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 448:
  if (yyn == 448)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4935 of "src/main/bison/cml.y"  */
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
/* Line 4946 of "src/main/bison/cml.y"  */
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
/* Line 4957 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)((yystack.valueAt (1-(1)))));
  yyval = binds;
};
  break;
    

  case 451:
  if (yyn == 451)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4963 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (3-(1))));
  binds.add((PMultipleBind)((yystack.valueAt (3-(3)))));
  yyval = binds;
};
  break;
    

  case 452:
  if (yyn == 452)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4971 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 453:
  if (yyn == 453)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4972 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 454:
  if (yyn == 454)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4977 of "src/main/bison/cml.y"  */
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
/* Line 4988 of "src/main/bison/cml.y"  */
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
/* Line 4999 of "src/main/bison/cml.y"  */
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
/* Line 5006 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> list = (List<ATypeBind>)((yystack.valueAt (3-(1))));
  ATypeBind bind = (ATypeBind)((yystack.valueAt (3-(3))));
  list.add(bind);
  yyval = list;
};
  break;
    



/* Line 374 of cmlskeleton.java.m4  */
/* Line 7688 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -636;
  public static final short yypact_[] =
  {
       105,   413,  -122,   -86,   413,   839,   -61,   -25,   304,   105,
    -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,
    -636,  -636,  -636,   237,  -636,   165,   120,  -122,  -636,  -636,
     357,   -86,  -636,   258,   413,  -636,   425,   839,  -636,   445,
     455,  -636,  -636,   413,    95,   292,  1117,  -636,  2643,  -636,
     194,  -636,  -636,  2643,   465,   503,  -636,  -636,  -636,    35,
    -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,
    -636,  -636,  -636,    44,  -636,  -636,   289,    18,  -636,  1117,
    1416,   120,  -636,  1117,   339,  1117,   324,  1117,  1117,  1117,
    1117,  1117,  -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,
    -636,   671,   316,  -636,  -636,  -636,  -636,  -636,  -636,  2643,
    2643,  2235,  1379,  1460,  2643,  2643,  2643,  2643,  2643,  2643,
    2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,
    2643,  2643,  2643,  2643,  2643,  2643,   495,   513,  1379,   139,
     539,   540,  1379,  1379,  1379,  2643,   547,  -636,   467,  -636,
   11069,  -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,  -636,
    -636,  1379,  -636,  1117,   399,  5202,  1379,  1379,  -636,  2643,
    1117,  2643,  1117,   446,   637,   447,   447,   447,   292,   447,
     447,  2643,  1645,   291,  3097,     6,  -636,   933,     3,   671,
      97,  -636,   745,  1117,  1117,   813,   599,  1089,  1091,   617,
     617,   617,  1117,  1117,  1117,  1117,   501,   507,  5324,    74,
    5447,  -636,    85,  4467,   119,   644,  -636,  -636,   267,  -636,
    -636,   675,  -636,  -636,    53,  -636,  -636,   -20,  -636,  5569,
    5691,   806,   806,   806,   806, 11520,   806,   806,   806,   806,
     806,   806,   806,   806,   806,   806,   806,   806,   806,   806,
    1013,  2643,  2643,   544,  -636,    86,  2643,   677,  2643,   517,
    -636,   383,   128,  -636,  -636,  -636,   135,   687,   -94,  5814,
    2643,  -636,  2643,  2643,  -636,  2643,  2643,  2643,  1070,   614,
     527,  2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,
    2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,
    2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,  2643,
    1379,  1300,    25,   454,   671,   531,   563,   156,  -636,   576,
      73, 11191,   833, 11191,  1037,   568,   413,   418,   145,   575,
     637,  -636,  -636,  -636,  -636,  -636,   420,   168,  -636,  -636,
     287,   371,     9,   426,   433,  5936,   561,   581,  2465,  1701,
    -636,  -636,  2643,  -636,   538,   538,   538,  2643,   538,   538,
    2643,  2643,  2643,   538,   538,  2643,  2643,  2643,  -636,   538,
    -636,  1379,  -636,  1117,  1117,  -636,  -636,  -636,   671,   671,
    -636,  1416,  1117,  1117,   671,   617,   617,   671,  -636,  -636,
    -636,  -636,  2643,  1379,  -636,  1379,  2643,  2643,  -636,  2643,
    1379,  2643,  1117,  2643,  1117,  2643,  1460,  2643,  1379,   159,
   11069,   208,  2643,  1379,  6058,  2643,   253,   268,  2643,  1379,
    1117,  2643,  1379,  2643,  2643,  -636,  1379,  6180, 11570,   281,
    6302,  1980, 11643, 11643,  -636,  -636,  -636,  -636,  1980,  1980,
    1980,  1980, 11570, 11570, 11570, 11570, 11570, 11393, 11443, 11313,
   11191, 11570, 11570, 11570, 11570,  3433,  3433,  1013,  3677,  3555,
    3555,  1013,  1013,   806,   806,   450,   362,  -636,  -636,  -636,
    -636,  -636,  1379,  1117,   651,  1117,  2643,   586,   717,  -636,
    1379,  -636,  2643,  2643,   637,   594,   413,  -636,  -636,  2643,
     595,   145,  -636,  -636,  -636,  2643,   447,   538,   447,   538,
     538,   538,   887,   538,   447,   765,   604,   775,   610,   447,
     447,   447,  2732,  2371,  -636,  -636,  -636,  -636,  2643,   447,
     447,  1460,  2643,  2643,   771,   684,  2643,  2643,  2643,  2643,
     363,  2643,  1768,   525,  -636,  -636,  -636,  2465,  -636,   283,
     719,   566,   463,  3173,  4100,   884,   585,  6424,   609,  6546,
    4222,   415,   328, 11069, 11069, 11069,  3097,   746,   671,   671,
     761,   617,   617, 11069,   673,   358,    75,  6670, 11069,  6792,
    -636,   129, 11570,   671, 11191, 11069,  -636,  4589,   -16,  -636,
       4,  -636,  -636, 11191,  -636,  1117,  6914,  -636,  2643, 11069,
    -636,   671, 11191,  -636, 11191, 11191,    28,  -636,  -636,  2643,
     512,   671,  1379,  -636,    89,   671, 11069,   649,  2643,  -636,
     781, 11191, 11191,   620,   259,  -636, 11069,   665,  -636,  -636,
   11069,  -636,  -636,  -636,  -636,  -636,  -636,  3057,  2643,  -636,
    -636,   485,  2643,   804,  2784,   807,   506,   555,   606,   595,
     292,   292,   292,   120,  2907,   608,  -636,    14,    -3,   772,
    -636,  7036,   803, 11069,   636,   639,   -19,  4711,     5,  7158,
     517,  2643,  -636,  7280,  7402,   151,  7524,     7,  1379,   811,
     359,   767,  3367,  2465,  2465,  2465,  2643,  2465,  2465,  2643,
    2643,  2643,  2465,  2465,  2643,  2643,  2643,  -636,  -636,  2643,
    -636,  2507,  1846,  -636,  2643,   538,   538,  -636,  2643,  2643,
     538,  2643,  -636,  2643,  -636,  2643,  -636,  -636,  2643,  -636,
    2643,  2643,  -636,   625,  -636,  2643,  1038,  -636,  7646,  2643,
    -636,  7768,  1117,   317,  1379,  2099,  1117, 11069,  -636,  -636,
    1660,   399,  1117,   816,  7890,   538, 11069,  2643,  -636,  3077,
      16,  2784,  2465,  2465,   815,   152,  -636,  -636,  -636,  -636,
    -636,  2465,   526,  2465,  -636,  -636,    -3,   690,   517,   717,
      63,  -636,   819,  2465,  2465,  2465,  2465,  2643,  -636,  1379,
     364,   181,  -636,  8012,   447,   447,   836,  -636,   731,  2643,
    2643,  2643,  2465,  1759,  1430,  3143,  3978,   832,   670,  4834,
    8135,  4344,    11,   416, 11069, 11069, 11069,   334,  2643, 11069,
    -636,  8257,   328,   884,  8379,  8502,   415, 11069,  8624,  8748,
    8872,  8994, 11191,   827,  -636, 11069,  -636,  -636,  9116,  -636,
     671,  -636,   338,  -636,  -636,   772, 11069,   671,   107,   647,
     681,   800,   722,   720,  2643,  1485,  -636, 11069,  2465,  -636,
    -636,  -636,  2643,  2465,   595,  2985,  -636,  2994,   690,   517,
     270,  -636,  2465,  2465,  2643,  -636,  3077,  3077,  2887,  9238,
     204,  -636,    15,  2643,  -636,   517,  -636,   643,   646,  2465,
    2643,  9360,  3489,  3611,  3077,  2643,  2643,  2465,  2643,  2465,
    2643,  2465,  -636, 11069,   538,  2643,  1379,  -636,  -636,  -636,
    2643,  2643,  -636,  -636,   717,  1774,  1955,   651,   772,  2643,
    2643,  9482,   193,  -636,  3077,  9604,  3037,  -636,   846,   849,
     372,  1117,  -636,   670,  4957,  2643,  2465,    62,  2465,  1226,
    -636,  2465,  -636,   853,  2465,  3077,   344,  3733,  2643,  2465,
    2465,  9726,  9848,   416,  5080,   832,  9970,    11,   884, 11069,
     149,  4589,  2863,   823,  -636,   339,   671,   339,   671,   101,
     717, 11069, 11069,  -636,  2465,  -636,  2643,  2643,  1117,   671,
    2465,  2643, 10092,  3077,  2643,  2465,  3077,   848,  -636,  3077,
    2643,  -636,  -636,  2465,  2465,  3245,  3077,  3077,  2643,  2465,
    2643,  2465,  2465,  2643,  -636,  -636,  -636,   517,  -636,  2187,
    -636,  -636,   345,   347,   671,  -636, 10214,  2465, 10336,  3077,
    2465, 10458,  3077,  3077,  2465,  2643, 10581,   832, 10703,   832,
    -636, 10825,   316,  -636,  -636,  3077,   772,  -636,  -636,  2465,
    3077,  2465,  2479,  2465,  3077,  3855,  2643,  2465,  -636,   717,
    -636,  3077,  -636,  -636,  2465, 10947,   832,  -636,  3077,  2465,
     832
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
     439,   443,   444,     0,   431,   211,     0,     0,   161,     0,
       0,     0,   140,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   185,   188,   189,   190,   191,   192,   193,   194,
     195,   139,   184,   172,   183,   196,   197,   173,   313,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   317,   286,   285,
     145,   279,   281,   280,   278,   282,   283,   300,   301,   302,
     303,     0,   230,     0,     0,     0,     0,     0,   433,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    31,    12,     0,   137,   162,   185,   204,
     164,   202,     0,     0,     0,     0,     0,     0,     0,   179,
     180,   178,     0,     0,     0,     0,     0,     0,     0,     0,
     276,   308,     0,   276,     0,   406,   438,   437,     0,   430,
     429,     0,   446,   447,     0,   343,   344,     0,   341,     0,
       0,   353,   354,   355,   356,   357,   358,   359,   360,   361,
     362,   363,   364,   365,   366,   367,   368,   369,   370,   371,
     372,     0,     0,     0,   456,     0,     0,     0,     0,     0,
     435,     0,     0,   450,   452,   453,     0,     0,     0,   276,
       0,   287,     0,     0,   291,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   183,     0,   239,   445,     0,
       0,   213,     0,   215,     0,     0,   246,   170,   270,     0,
     149,   151,   152,   153,   154,   155,     0,     0,    43,    42,
       0,     0,     0,     0,     0,     0,    52,     0,     0,     0,
      48,    50,     0,    32,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    34,     0,
     138,     0,   163,     0,     0,   203,   165,   171,   201,   199,
     177,     0,     0,     0,   200,   175,   176,   198,   186,   187,
     284,   314,     0,     0,   309,     0,     0,     0,   318,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     276,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   329,     0,     0,   384,     0,
     276,   374,   373,   375,   288,   294,   290,   289,   377,   376,
     378,   379,   380,   381,   382,   383,   385,   386,   387,   388,
     389,   390,   391,   392,   393,   394,   395,   396,   397,   398,
     399,   400,   401,   402,   403,   404,   405,   297,   298,   295,
     296,   231,     0,     0,     0,     0,     0,     0,     0,   238,
       0,   434,     0,     0,     0,     0,   247,   248,   156,     0,
       0,   271,   272,    10,   150,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    53,     0,    51,     0,
       0,     0,     0,   260,    60,    61,    62,    63,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   104,   101,   112,     0,    49,     0,
      31,    18,    17,    22,     0,    21,    16,     0,     0,     0,
       0,    23,    25,    29,    28,    27,    13,     0,   206,   205,
       0,   182,   181,   277,     0,     0,     0,     0,   408,     0,
     407,     0,   448,   449,   307,   299,   342,     0,     0,   350,
       0,   321,   322,   323,   457,     0,     0,   327,     0,   454,
     436,   455,   304,   451,   305,   306,     0,   324,   292,     0,
       0,   232,     0,   224,     0,   236,   240,     0,     0,   221,
     436,   214,   216,     0,     0,   249,   275,     0,   274,   273,
      46,    47,    37,    45,    44,    36,    41,     0,     0,    40,
      35,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   185,     0,     0,   121,     0,     0,   239,
     259,     0,     0,    64,     0,     0,     0,     0,     0,     0,
       0,     0,   109,     0,     0,     0,     0,     0,     0,   437,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    14,    78,     0,
     106,     0,     0,    33,     0,     0,     0,   126,     0,     0,
       0,     0,   174,     0,   315,     0,   310,   312,     0,   319,
       0,     0,   345,     0,   348,     0,     0,   325,     0,     0,
     330,     0,     0,     0,     0,     0,     0,   243,   432,    11,
       0,   234,     0,     0,     0,     0,    56,     0,    54,    58,
       0,     0,     0,     0,     0,     0,   411,   125,   124,   123,
      65,     0,     0,     0,   268,   269,   261,   262,     0,     0,
       0,   108,     0,     0,     0,     0,     0,     0,   102,     0,
       0,     0,   416,     0,     0,     0,     0,   103,     0,     0,
       0,     0,     0,    70,    69,    81,     0,    83,    68,     0,
       0,     0,    71,    73,    77,    76,    75,     0,     0,   418,
      15,     0,    26,    19,     0,   129,    24,   207,     0,     0,
       0,     0,   346,     0,   351,   352,   326,   328,     0,   293,
     233,   225,     0,   228,   229,   239,   227,   237,     0,     0,
       0,   260,   235,   413,     0,     0,    38,    57,     0,    55,
      93,    92,     0,     0,     0,     0,   122,     0,   263,     0,
     264,   428,     0,     0,     0,    91,    99,   409,     0,     0,
       0,   425,     0,     0,   105,     0,   110,     0,     0,     0,
       0,     0,     0,     0,   118,     0,     0,     0,     0,     0,
       0,     0,   107,   111,     0,     0,     0,   316,   311,   320,
       0,     0,   331,   226,   242,     0,     0,     0,   239,     0,
       0,     0,    31,    39,    59,     0,     0,   412,    88,     0,
     266,     0,    66,    67,     0,     0,     0,     0,     0,     0,
     423,     0,   417,     0,     0,   119,     0,     0,     0,     0,
       0,     0,     0,    74,     0,    87,     0,    72,    20,   130,
       0,     0,     0,   245,   241,   255,   253,   254,   252,     0,
       0,   415,   414,    30,     0,   100,     0,     0,     0,   265,
       0,     0,     0,   420,     0,     0,   410,     0,   426,   427,
       0,    94,    79,     0,     0,     0,   114,   113,     0,     0,
       0,     0,     0,     0,   127,   347,   349,     0,   223,     0,
     250,    95,     0,     0,   267,    98,     0,     0,     0,   419,
       0,     0,   120,   115,     0,     0,     0,    85,     0,    82,
      80,     0,   244,   257,   258,   256,   239,    89,    90,     0,
     421,     0,     0,     0,   116,     0,     0,     0,   128,   242,
      97,   422,   424,    96,     0,     0,    86,   251,   117,     0,
      84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -636,  -636,  -636,   867,  -636,  -636,    34,  -173,   272,  -636,
     530,  -636,  -636,   142,  1055,  -636,  -636,   132,  -120,  -636,
    -636,  -636,   859,   -67,   118,  -636,  -636,   857,  -636,  -293,
    -152,   122,  -636,   851,    12,   322,    13,   756,  -140,  -636,
    -636,   519,  -182,   715,   306,  -636,   874,   876,   307,  -636,
     889,   888,    29,  -636,   311,  -636,  -636,   198,  -625,  -636,
     -96,  -477,  -636,  -636,  -636,  -299,  -636,  -636,   106,  -636,
    -636,   180,  -636,  -636,  -636,   451,   -85,   -48,   870,   948,
     958,  1026,  1353,   802,   422,   542,  -636,    20,  -636,  -636,
     249,  -636,  -636,  -636,   567,   438,  -636,  -621,  -636,  -635,
    -636,  -636,  -636,  -636,    54,  -636,    31,   -24,  -146,  -636,
    -165,  -130,  -265,  -129,  -132,   559,  -636,  -636,  -636
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   184,   631,   338,   349,
     350,   508,   506,   738,   739,   926,   645,   646,   688,   548,
      13,    27,    28,   740,   339,    14,    31,    32,    15,   329,
     330,   331,    23,    24,   485,   189,   533,   103,   104,   105,
     106,   190,   191,   372,   332,    37,    38,   225,   333,    34,
      35,   226,   604,   825,   164,   312,   831,   317,   478,   479,
     943,   944,   988,   334,   486,   487,   830,  1016,   649,   650,
     756,   757,   758,   335,   491,   492,   209,   410,   151,   152,
     153,   154,   155,   156,   227,   228,   157,   712,   158,   578,
     579,   159,   160,   214,   215,   658,   745,   618,   771,   534,
     535,   917,   536,   860,   861,   652,   260,   219,   261,   220,
      74,   221,   222,   223,   262,   263,   264,   265,   255
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "150,609,337,340,341,165,343,344,375,254,185,266,73,25,267,313,33,36,746,369,319,320,501,315,759,772,212,753,488,838,351,715,175,176,177,178,767,494,767,405,764,719,921,471,678,468,33,169,373,36,179,180,392,754,755,25,171,425,26,102,268,208,210,213,368,169,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,705,73,852,481,102,102,30,391,102,269,102,412,102,102,102,102,102,706,724,79,1,2,3,342,4,435,83,394,84,85,724,39,29,321,16,323,964,965,406,406,565,80,713,16,86,345,193,725,87,88,89,90,91,421,708,218,371,29,470,989,423,398,419,194,374,5,361,40,256,714,680,709,181,419,983,82,776,843,409,411,683,253,684,472,685,416,422,218,102,581,768,720,777,497,686,102,170,102,81,615,429,81,182,613,489,172,81,186,81,351,183,864,894,498,404,102,853,476,102,102,414,895,352,161,353,162,865,102,102,102,102,419,392,422,427,907,428,430,582,431,432,433,922,392,413,438,439,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,580,566,315,399,45,539,571,46,43,587,417,950,422,422,161,188,162,6,7,422,851,64,584,93,94,95,96,97,98,99,100,984,596,422,186,392,844,598,499,693,477,370,41,392,17,18,352,544,353,495,547,549,550,17,18,553,554,555,498,19,20,21,22,-170,600,93,94,95,96,97,98,99,100,821,636,637,638,496,490,467,46,163,563,44,654,655,567,568,919,569,882,572,392,574,893,575,206,577,206,359,972,1017,583,1018,193,586,101,48,589,703,207,592,207,594,595,920,375,704,53,274,73,194,500,401,102,102,541,542,543,1029,545,546,102,102,102,551,552,392,671,187,557,556,498,192,206,195,730,197,198,199,200,201,588,102,402,102,780,911,207,368,368,368,564,368,368,392,606,392,216,368,368,102,611,612,368,45,50,502,46,616,53,665,253,647,503,620,359,678,590,54,55,279,206,56,723,76,173,498,370,174,206,419,863,651,498,77,207,274,653,361,81,990,657,659,207,401,663,664,392,666,166,672,419,314,102,365,102,366,973,392,322,392,324,309,359,73,735,367,57,418,368,490,196,368,368,368,402,368,368,610,251,54,55,378,379,56,498,742,167,958,168,58,384,385,386,387,668,419,252,622,420,625,626,627,629,630,495,498,718,279,689,669,690,60,61,62,63,64,65,721,175,176,177,178,258,259,361,361,727,670,57,680,270,271,496,580,743,46,179,180,364,102,365,684,366,685,822,316,734,355,308,309,736,58,367,686,498,19,20,21,22,-170,19,20,21,22,102,359,419,867,868,473,797,59,361,60,61,62,63,64,65,773,82,206,624,362,363,744,364,751,365,862,366,691,325,336,786,207,186,789,790,791,367,381,794,795,796,498,1,752,53,799,4,326,801,327,328,762,804,805,763,807,204,808,923,809,419,924,810,722,811,812,640,641,642,815,602,498,603,818,498,770,202,400,498,826,203,498,181,388,368,368,204,5,368,389,403,837,813,205,216,402,415,558,559,92,202,778,424,661,203,662,561,562,436,437,204,361,182,474,475,54,55,205,368,56,540,859,362,363,480,364,573,365,322,366,361,802,803,871,872,873,806,102,352,367,353,102,484,505,591,102,365,102,366,202,493,896,883,203,186,697,940,698,367,204,747,748,749,507,57,377,205,370,607,608,621,836,623,850,202,862,614,617,203,632,83,899,84,85,204,368,633,58,901,634,660,205,635,401,701,729,905,601,86,605,726,728,87,88,89,90,91,217,914,60,61,62,63,64,65,361,732,799,737,680,368,741,900,476,927,779,761,274,781,931,932,684,934,685,936,834,380,842,849,939,72,854,202,686,941,942,203,482,673,674,107,870,204,951,952,869,891,648,-170,205,370,186,202,897,910,956,203,678,957,962,477,903,204,992,993,970,987,1000,42,205,770,538,975,107,107,839,846,107,47,107,49,107,107,107,107,107,78,257,278,279,354,355,560,175,176,177,178,376,67,716,102,102,280,75,66,996,72,72,998,359,938,179,180,51,1001,52,102,731,949,-170,-170,832,1006,-170,1008,1037,702,1011,848,898,308,309,72,188,619,656,72,72,72,64,576,93,94,95,96,97,98,99,100,1025,310,311,202,985,814,72,203,107,570,667,72,72,204,102,107,968,107,361,-170,205,1035,680,371,593,67,67,68,0,681,682,0,683,0,684,107,685,69,107,107,0,0,-170,1012,0,0,686,107,107,107,107,67,0,0,0,67,67,67,19,20,21,22,-170,0,-170,-170,-170,-170,-170,-170,361,0,628,0,67,274,0,0,0,67,67,362,363,0,364,0,365,820,366,0,0,827,483,0,0,829,0,833,367,816,0,0,182,68,68,70,0,202,202,0,540,203,203,69,69,0,0,204,204,0,0,0,0,72,205,205,0,0,0,68,53,0,0,68,68,68,0,0,0,69,0,0,0,69,69,69,278,279,0,0,0,0,68,0,0,72,72,68,68,202,280,202,69,203,0,203,0,69,69,204,382,204,383,0,0,0,205,83,205,84,85,70,70,0,304,305,306,307,308,309,0,67,0,0,0,86,0,0,0,87,88,89,90,91,0,56,0,70,310,311,0,70,70,70,0,0,72,0,107,107,0,0,0,67,67,0,107,107,107,0,70,0,0,0,0,70,70,0,72,0,72,0,0,0,0,72,0,107,0,107,57,72,0,72,0,0,0,0,72,0,946,948,0,0,72,107,0,72,0,68,0,72,0,58,0,0,959,0,0,69,0,0,0,0,67,0,53,0,0,0,0,0,0,434,0,60,61,62,63,64,65,68,68,0,0,0,67,0,67,0,0,69,69,67,0,0,0,72,107,67,107,67,0,994,0,72,67,0,0,0,0,0,67,0,967,67,216,0,0,67,92,0,0,0,0,0,64,70,93,94,95,96,97,98,99,100,0,54,55,0,53,56,68,0,0,0,72,0,0,0,0,0,69,0,0,72,0,0,0,70,70,0,0,0,68,67,68,0,0,0,0,68,0,67,69,0,69,68,0,68,0,69,0,0,68,107,57,69,0,69,68,0,0,68,69,0,0,68,0,0,69,0,0,69,0,0,0,69,0,58,107,0,71,0,67,56,0,0,0,53,70,0,0,67,0,0,532,72,0,217,0,60,61,62,63,64,65,0,0,0,0,0,70,68,70,0,0,0,0,70,0,68,0,69,0,70,83,70,84,85,57,69,70,0,0,0,0,0,70,216,0,70,0,0,86,70,0,0,87,88,89,90,91,58,0,0,678,0,71,71,54,55,68,72,56,67,0,0,0,0,53,68,69,469,0,60,61,62,63,64,65,69,0,0,71,0,0,0,71,71,71,70,175,176,177,178,0,0,0,70,0,0,0,0,0,0,0,71,72,57,179,180,71,71,0,0,0,107,0,72,0,107,0,0,0,107,0,107,0,0,0,67,58,0,0,0,0,0,0,0,70,54,55,68,0,56,0,0,0,70,0,0,217,69,60,61,62,63,64,65,644,0,0,0,72,0,361,0,0,0,680,0,0,0,0,0,67,681,682,0,683,0,684,0,685,692,0,67,0,188,57,0,0,0,686,64,0,93,94,95,96,97,98,99,100,0,0,0,0,68,0,0,0,58,0,0,0,0,0,69,181,70,0,0,71,0,0,0,0,0,0,0,67,224,0,60,61,62,63,64,65,1,0,0,346,4,326,347,327,328,182,348,0,0,68,0,71,71,902,0,0,0,0,0,69,68,0,0,0,0,83,0,828,85,0,69,0,0,0,0,0,72,0,0,0,0,5,70,86,0,107,107,87,88,89,90,91,1,0,0,346,4,326,347,327,328,107,537,0,0,68,0,0,0,72,0,0,71,0,0,69,783,784,785,0,787,788,0,0,0,792,793,70,0,0,0,0,0,0,71,0,71,5,70,0,0,71,0,0,67,0,0,71,107,71,0,0,0,0,71,0,0,0,0,0,71,0,674,71,0,0,0,71,0,0,673,674,675,0,0,0,676,67,0,83,678,945,85,70,0,840,841,0,677,678,0,0,0,0,845,0,847,86,0,0,0,87,88,89,90,91,855,856,857,858,0,0,0,71,0,0,0,0,0,0,0,71,68,0,0,874,0,0,92,0,0,0,69,0,64,0,93,94,95,96,97,98,99,100,0,0,0,0,673,674,675,0,0,0,676,68,0,0,0,0,0,0,71,0,0,69,677,678,0,0,0,71,0,0,0,0,0,0,0,0,0,904,0,0,0,0,906,0,0,0,361,0,0,0,680,912,913,0,679,361,70,681,682,680,683,0,684,0,685,0,681,682,925,683,0,684,0,685,686,0,933,0,935,0,937,0,0,686,0,687,0,0,0,70,0,0,0,0,0,0,0,0,92,71,0,0,0,0,64,0,93,94,95,96,97,98,99,100,0,963,83,966,947,85,969,0,0,971,0,0,0,0,976,977,0,0,679,361,86,0,0,680,87,88,89,90,91,274,681,682,0,683,0,684,0,685,0,991,0,0,0,0,0,995,0,686,0,800,999,71,0,0,0,0,0,0,1002,1003,0,0,0,0,1007,0,1009,1010,0,0,0,0,0,0,1015,0,0,0,0,0,0,0,1020,0,0,1022,0,0,0,1024,0,0,0,0,0,0,71,0,0,0,278,279,0,0,1030,0,1031,71,1033,0,0,0,1036,0,280,0,0,0,0,1038,0,0,0,0,1040,0,0,0,0,0,0,0,0,0,300,0,0,0,304,305,306,307,308,309,108,0,109,0,0,110,0,0,71,0,0,0,0,0,111,0,0,0,310,311,0,92,0,0,0,0,0,64,0,93,94,95,96,97,98,99,100,112,823,824,0,0,0,113,0,114,0,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,0,139,140,141,0,0,0,0,509,510,511,512,0,0,513,0,514,515,516,517,518,0,0,0,0,0,519,520,0,0,0,0,0,0,0,0,0,0,0,0,142,143,144,57,0,0,71,1013,1014,0,0,0,521,0,522,0,145,108,523,109,0,0,110,0,0,0,58,0,0,0,0,111,211,0,146,0,147,0,0,71,0,0,0,0,0,0,148,149,60,61,62,63,64,65,112,524,0,525,0,0,113,0,114,0,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,526,139,140,141,0,527,0,0,0,0,0,0,0,0,0,0,0,0,0,0,528,529,530,0,0,531,0,0,0,0,0,0,0,0,0,0,0,0,92,0,142,143,144,57,0,0,0,0,0,0,0,0,0,0,0,0,145,108,0,109,0,0,110,0,0,0,58,0,0,0,0,111,0,0,146,0,147,0,0,0,0,0,0,0,0,0,148,149,60,61,62,63,64,65,112,0,0,648,0,0,113,0,114,0,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,0,139,140,141,0,0,0,0,0,0,0,0,0,0,509,510,511,512,0,0,513,0,514,515,516,517,518,0,673,674,675,0,519,520,676,0,0,0,0,0,142,143,144,57,0,0,677,678,0,0,0,0,0,0,0,0,145,108,521,109,522,0,110,0,523,0,58,0,0,0,0,111,0,0,146,0,147,0,0,0,0,0,0,0,0,0,148,149,60,61,62,63,64,65,112,0,0,0,0,0,113,524,114,525,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,798,139,140,141,0,0,526,0,0,0,0,527,0,0,0,0,0,0,0,0,679,361,0,0,0,680,528,529,530,0,0,531,681,682,0,683,0,684,0,685,142,143,144,57,92,0,0,0,0,686,0,1032,0,0,0,0,145,108,0,109,0,0,110,0,0,0,58,0,0,0,0,111,0,0,146,0,147,0,0,0,0,0,0,0,0,0,148,149,60,61,62,63,64,65,112,0,0,0,0,0,113,0,114,0,0,0,115,0,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,0,0,136,137,0,0,56,138,0,139,140,141,0,0,0,0,0,509,510,511,512,0,0,513,0,514,515,516,517,518,0,0,0,0,0,519,520,0,0,0,0,0,0,0,0,0,0,0,142,143,144,57,0,0,0,0,0,0,0,0,0,521,0,522,145,0,0,523,0,509,510,511,512,0,58,513,0,514,515,516,517,518,146,0,147,0,0,519,520,0,0,0,0,0,148,149,60,61,62,63,64,65,639,0,524,0,525,0,0,0,0,0,0,521,0,522,0,0,0,523,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,640,641,642,0,526,272,0,0,0,527,273,0,274,0,0,524,0,525,0,0,0,0,0,0,528,529,530,0,0,531,275,673,674,675,0,0,0,676,0,0,0,0,643,0,0,0,0,0,0,677,678,673,674,675,0,750,526,676,0,276,277,527,0,0,0,0,0,0,0,677,678,0,0,0,0,0,528,529,530,915,916,531,0,278,279,0,0,0,0,0,0,0,0,0,643,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,0,673,674,675,0,908,0,676,0,0,673,674,675,0,909,0,676,310,311,677,678,0,0,0,0,0,0,0,677,678,0,679,361,0,0,0,680,0,986,0,0,0,0,681,682,0,683,0,684,0,685,679,361,673,674,675,680,955,0,676,686,0,0,681,682,0,683,0,684,0,685,677,678,354,355,356,0,733,0,357,686,0,0,0,0,0,0,0,0,0,0,358,359,673,674,675,0,0,0,676,0,0,0,0,0,0,0,0,0,0,0,677,678,354,355,356,0,0,0,357,0,0,0,0,0,0,0,0,0,679,361,358,359,0,680,0,0,0,679,361,0,681,682,680,683,0,684,0,685,0,681,682,0,683,0,684,0,685,686,673,674,0,0,0,0,676,0,686,0,0,0,0,0,0,0,0,0,677,678,0,0,679,361,0,0,0,680,0,0,354,355,0,0,681,682,357,683,0,684,0,685,360,361,0,0,0,0,358,359,0,686,0,0,362,363,0,364,0,365,0,366,679,361,0,0,0,680,0,0,0,367,0,0,681,682,0,683,0,684,0,685,360,361,0,0,0,0,0,0,0,686,0,0,362,363,0,364,0,365,272,366,0,0,0,273,0,274,0,0,0,367,0,0,0,0,0,0,0,0,0,0,0,0,0,275,0,0,679,361,0,0,0,680,0,0,0,0,0,0,681,682,0,683,0,684,0,685,0,0,0,0,0,0,276,277,0,686,360,361,0,0,0,0,0,0,0,0,0,0,362,363,0,364,0,365,0,366,278,279,0,0,0,0,0,0,0,367,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,1004,0,0,1005,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,274,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,275,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,278,279,0,275,0,0,0,782,0,0,0,0,0,0,280,281,282,283,284,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,300,301,302,303,304,305,306,307,308,309,0,0,0,0,0,0,0,274,0,0,0,0,278,279,0,0,0,0,310,311,0,0,0,0,0,275,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,278,279,0,275,0,0,0,929,0,0,0,0,0,0,280,281,282,283,284,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,300,301,0,0,304,305,306,307,308,309,0,0,0,0,0,0,0,274,0,0,0,0,278,279,0,0,0,0,310,311,0,0,0,0,0,275,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,278,279,0,275,0,0,0,930,0,0,0,0,0,0,280,281,282,283,284,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,300,0,0,0,304,305,306,307,308,309,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,310,311,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,974,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,310,311,875,876,0,0,0,0,275,0,0,1034,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,694,0,0,0,877,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,695,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,700,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,310,311,395,0,881,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,396,397,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,710,711,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,765,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,766,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,310,311,878,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,960,879,0,0,0,0,0,0,310,311,961,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,310,311,980,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,318,274,0,0,0,981,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,390,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,310,311,393,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,407,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,408,0,0,310,311,426,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,504,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,597,274,0,0,0,0,585,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,599,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,696,0,0,0,0,699,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,310,311,707,0,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,397,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,717,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,760,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,769,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,774,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,775,765,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,817,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,819,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,866,274,0,0,0,835,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,310,311,880,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,884,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,885,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,310,311,886,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,887,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,310,311,888,0,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,310,311,889,0,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,890,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,918,0,892,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,928,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,953,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,954,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,978,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,979,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,982,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,997,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,1019,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1021,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,1023,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,310,311,1026,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,1027,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,1039,1028,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,273,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,0,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,272,0,0,0,0,0,0,274,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,275,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,276,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,278,279,272,0,0,0,0,0,0,274,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,292,275,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,0,0,0,0,0,0,0,272,0,0,0,276,277,0,274,0,0,310,311,0,0,0,0,0,0,0,0,0,0,0,0,0,275,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,290,291,276,277,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,0,0,0,0,272,278,279,0,0,0,0,274,0,0,0,0,0,310,311,280,281,282,283,284,285,286,287,288,289,275,291,0,0,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,0,0,0,0,0,0,0,0,276,277,0,274,0,0,0,0,0,310,311,0,0,0,0,0,0,0,0,0,0,275,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,285,286,287,288,289,0,0,276,277,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,0,0,0,0,0,0,278,279,274,0,0,0,0,0,0,0,0,0,310,311,280,281,282,283,284,0,275,0,0,0,0,0,0,0,0,0,0,0,298,299,300,301,302,303,304,305,306,307,308,309,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,310,311,0,0,0,0,0,0,0,0,0,278,279,0,0,0,0,0,0,0,0,0,0,0,0,280,281,282,283,284,0,0,0,0,0,0,0,0,0,0,0,0,0,298,299,300,301,302,303,304,305,306,307,308,309,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,310,311";
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
  public static final String yyCheckString = "48,478,175,176,177,53,179,180,190,138,77,143,36,1,144,161,4,5,639,13,166,167,13,163,649,660,111,13,327,13,182,27,14,15,16,17,31,330,31,59,59,13,27,18,33,310,34,12,45,37,32,33,146,56,57,43,12,151,180,46,145,109,110,111,184,12,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,13,113,27,18,79,80,180,21,83,145,85,13,87,88,89,90,91,30,17,12,3,4,5,178,7,278,17,30,19,20,17,180,2,169,0,171,62,63,146,146,393,34,146,9,35,181,27,46,39,40,41,42,43,13,13,112,47,27,311,46,13,30,146,44,149,48,143,180,17,173,147,30,142,146,13,45,13,13,251,252,157,138,159,146,161,258,146,144,163,18,173,151,173,13,171,170,149,172,180,486,273,180,172,484,47,149,180,77,180,349,180,18,825,33,149,190,141,49,193,194,256,102,17,17,19,19,33,202,203,204,205,146,146,146,270,844,272,273,18,275,276,277,865,146,146,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,408,395,404,146,146,352,400,149,33,18,259,898,146,146,17,180,19,174,175,146,759,186,413,188,189,190,191,192,193,194,195,144,426,146,178,146,146,18,13,18,146,185,0,146,0,0,17,357,19,118,360,361,362,9,9,365,366,367,33,176,177,178,179,180,472,188,189,190,191,192,193,194,195,18,509,510,511,146,328,310,149,149,392,180,519,520,396,397,146,399,18,401,146,403,18,405,90,407,90,33,18,18,412,18,27,415,46,12,418,13,104,421,104,423,424,173,560,21,17,19,406,44,13,118,373,374,354,355,356,1016,358,359,381,382,383,363,364,146,530,79,371,369,33,83,90,85,149,87,88,89,90,91,146,402,149,404,59,149,104,541,542,543,393,545,546,146,476,146,67,551,552,420,482,483,556,146,180,13,149,489,17,528,413,512,13,495,33,33,419,88,89,91,90,92,602,12,169,33,342,172,90,146,100,513,33,12,104,19,518,143,180,950,522,523,104,118,526,527,146,529,17,531,146,163,473,159,475,161,146,146,170,146,172,133,33,521,13,171,137,118,622,491,180,625,626,627,149,629,630,480,17,88,89,193,194,92,33,13,17,149,19,160,202,203,204,205,165,146,17,497,149,499,500,501,502,503,118,33,588,91,17,180,19,182,183,184,185,186,187,599,14,15,16,17,17,17,143,143,608,530,137,147,17,98,146,713,13,149,32,33,157,560,159,159,161,161,724,180,628,15,132,133,632,160,171,171,33,176,177,178,179,180,176,177,178,179,585,33,146,774,775,149,689,180,143,182,183,184,185,186,187,661,496,90,498,154,155,13,157,13,159,769,161,100,180,180,676,104,512,679,680,681,171,36,684,685,686,33,3,33,17,691,7,8,694,10,11,13,698,699,13,701,37,703,13,705,146,13,708,149,710,711,138,139,140,715,17,33,19,719,33,660,27,31,33,725,31,33,142,180,802,803,37,48,806,180,13,737,65,44,67,149,17,373,374,180,27,668,13,17,31,19,382,383,92,180,37,143,172,180,149,88,89,44,836,92,180,767,154,155,146,157,402,159,404,161,143,695,696,779,780,781,700,722,17,171,19,726,172,180,420,730,159,732,161,27,173,102,798,31,634,144,886,146,171,37,640,641,642,180,137,18,44,647,180,50,496,735,498,758,27,919,180,180,31,12,17,59,19,20,37,903,180,160,834,12,17,44,180,118,46,173,842,473,35,475,149,18,39,40,41,42,43,180,854,182,183,184,185,186,187,143,149,863,12,147,938,12,100,49,870,12,21,19,59,875,876,159,878,161,880,17,21,20,146,885,36,20,27,171,890,891,31,12,14,15,46,118,37,899,900,17,27,55,17,44,740,741,27,180,849,17,31,33,17,915,146,835,37,956,957,20,51,27,9,44,865,349,928,79,80,741,752,83,27,85,31,87,88,89,90,91,43,139,90,91,14,15,381,14,15,16,17,190,36,585,895,896,104,37,36,961,112,113,964,33,884,32,33,33,970,34,911,614,897,88,89,731,978,92,980,1029,173,983,756,831,132,133,138,180,491,521,142,143,144,186,406,188,189,190,191,192,193,194,195,1005,152,153,27,941,713,161,31,163,399,529,166,167,37,958,170,919,172,143,137,44,1026,147,47,422,112,113,36,-1,154,155,-1,157,-1,159,190,161,36,193,194,-1,-1,160,987,-1,-1,171,202,203,204,205,138,-1,-1,-1,142,143,144,176,177,178,179,180,-1,182,183,184,185,186,187,143,-1,142,-1,161,19,-1,-1,-1,166,167,154,155,-1,157,-1,159,722,161,-1,-1,726,12,-1,-1,730,-1,732,171,18,-1,-1,172,112,113,36,-1,27,27,-1,180,31,31,112,113,-1,-1,37,37,-1,-1,-1,-1,278,44,44,-1,-1,-1,138,17,-1,-1,142,143,144,-1,-1,-1,138,-1,-1,-1,142,143,144,90,91,-1,-1,-1,-1,161,-1,-1,310,311,166,167,27,104,27,161,31,-1,31,-1,166,167,37,38,37,38,-1,-1,-1,44,17,44,19,20,112,113,-1,128,129,130,131,132,133,-1,278,-1,-1,-1,35,-1,-1,-1,39,40,41,42,43,-1,92,-1,138,152,153,-1,142,143,144,-1,-1,371,-1,373,374,-1,-1,-1,310,311,-1,381,382,383,-1,161,-1,-1,-1,-1,166,167,-1,393,-1,395,-1,-1,-1,-1,400,-1,402,-1,404,137,406,-1,408,-1,-1,-1,-1,413,-1,895,896,-1,-1,419,420,-1,422,-1,278,-1,426,-1,160,-1,-1,911,-1,-1,278,-1,-1,-1,-1,371,-1,17,-1,-1,-1,-1,-1,-1,180,-1,182,183,184,185,186,187,310,311,-1,-1,-1,393,-1,395,-1,-1,310,311,400,-1,-1,-1,472,473,406,475,408,-1,958,-1,480,413,-1,-1,-1,-1,-1,419,-1,65,422,67,-1,-1,426,180,-1,-1,-1,-1,-1,186,278,188,189,190,191,192,193,194,195,-1,88,89,-1,17,92,371,-1,-1,-1,521,-1,-1,-1,-1,-1,371,-1,-1,530,-1,-1,-1,310,311,-1,-1,-1,393,472,395,-1,-1,-1,-1,400,-1,480,393,-1,395,406,-1,408,-1,400,-1,-1,413,560,137,406,-1,408,419,-1,-1,422,413,-1,-1,426,-1,-1,419,-1,-1,422,-1,-1,-1,426,-1,160,585,-1,36,-1,521,92,-1,-1,-1,17,371,-1,-1,530,-1,-1,348,602,-1,180,-1,182,183,184,185,186,187,-1,-1,-1,-1,-1,393,472,395,-1,-1,-1,-1,400,-1,480,-1,472,-1,406,17,408,19,20,137,480,413,-1,-1,-1,-1,-1,419,67,-1,422,-1,-1,35,426,-1,-1,39,40,41,42,43,160,-1,-1,33,-1,112,113,88,89,521,668,92,602,-1,-1,-1,-1,17,530,521,180,-1,182,183,184,185,186,187,530,-1,-1,138,-1,-1,-1,142,143,144,472,14,15,16,17,-1,-1,-1,480,-1,-1,-1,-1,-1,-1,-1,161,713,137,32,33,166,167,-1,-1,-1,722,-1,724,-1,726,-1,-1,-1,730,-1,732,-1,-1,-1,668,160,-1,-1,-1,-1,-1,-1,-1,521,88,89,602,-1,92,-1,-1,-1,530,-1,-1,180,602,182,183,184,185,186,187,512,-1,-1,-1,769,-1,143,-1,-1,-1,147,-1,-1,-1,-1,-1,713,154,155,-1,157,-1,159,-1,161,537,-1,724,-1,180,137,-1,-1,-1,171,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,668,-1,-1,-1,160,-1,-1,-1,-1,-1,668,142,602,-1,-1,278,-1,-1,-1,-1,-1,-1,-1,769,180,-1,182,183,184,185,186,187,3,-1,-1,6,7,8,9,10,11,172,13,-1,-1,713,-1,310,311,180,-1,-1,-1,-1,-1,713,724,-1,-1,-1,-1,17,-1,19,20,-1,724,-1,-1,-1,-1,-1,886,-1,-1,-1,-1,48,668,35,-1,895,896,39,40,41,42,43,3,-1,-1,6,7,8,9,10,11,911,13,-1,-1,769,-1,-1,-1,919,-1,-1,371,-1,-1,769,673,674,675,-1,677,678,-1,-1,-1,682,683,713,-1,-1,-1,-1,-1,-1,393,-1,395,48,724,-1,-1,400,-1,-1,886,-1,-1,406,958,408,-1,-1,-1,-1,413,-1,-1,-1,-1,-1,419,-1,15,422,-1,-1,-1,426,-1,-1,14,15,16,-1,-1,-1,20,919,-1,17,33,19,20,769,-1,742,743,-1,32,33,-1,-1,-1,-1,751,-1,753,35,-1,-1,-1,39,40,41,42,43,763,764,765,766,-1,-1,-1,472,-1,-1,-1,-1,-1,-1,-1,480,886,-1,-1,782,-1,-1,180,-1,-1,-1,886,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,14,15,16,-1,-1,-1,20,919,-1,-1,-1,-1,-1,-1,521,-1,-1,919,32,33,-1,-1,-1,530,-1,-1,-1,-1,-1,-1,-1,-1,-1,838,-1,-1,-1,-1,843,-1,-1,-1,143,-1,-1,-1,147,852,853,-1,142,143,886,154,155,147,157,-1,159,-1,161,-1,154,155,869,157,-1,159,-1,161,171,-1,877,-1,879,-1,881,-1,-1,171,-1,173,-1,-1,-1,919,-1,-1,-1,-1,-1,-1,-1,-1,180,602,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,916,17,918,19,20,921,-1,-1,924,-1,-1,-1,-1,929,930,-1,-1,142,143,35,-1,-1,147,39,40,41,42,43,19,154,155,-1,157,-1,159,-1,161,-1,954,-1,-1,-1,-1,-1,960,-1,171,-1,173,965,668,-1,-1,-1,-1,-1,-1,973,974,-1,-1,-1,-1,979,-1,981,982,-1,-1,-1,-1,-1,-1,989,-1,-1,-1,-1,-1,-1,-1,997,-1,-1,1000,-1,-1,-1,1004,-1,-1,-1,-1,-1,-1,713,-1,-1,-1,90,91,-1,-1,1019,-1,1021,724,1023,-1,-1,-1,1027,-1,104,-1,-1,-1,-1,1034,-1,-1,-1,-1,1039,-1,-1,-1,-1,-1,-1,-1,-1,-1,124,-1,-1,-1,128,129,130,131,132,133,15,-1,17,-1,-1,20,-1,-1,769,-1,-1,-1,-1,-1,29,-1,-1,-1,152,153,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,886,53,54,-1,-1,-1,58,-1,60,-1,150,15,64,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,30,-1,168,-1,170,-1,-1,919,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,101,-1,103,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,142,95,96,97,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,94,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,58,-1,60,150,-1,-1,64,-1,14,15,16,17,-1,160,20,-1,22,23,24,25,26,168,-1,170,-1,-1,32,33,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,99,-1,101,-1,103,-1,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,138,139,140,-1,142,12,-1,-1,-1,147,17,-1,19,-1,-1,101,-1,103,-1,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,37,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,-1,32,33,14,15,16,-1,18,142,20,-1,66,67,147,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,162,163,164,62,63,167,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,14,15,16,-1,18,-1,20,-1,-1,14,15,16,-1,18,-1,20,152,153,32,33,-1,-1,-1,-1,-1,-1,-1,32,33,-1,142,143,-1,-1,-1,147,-1,173,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,14,15,16,147,18,-1,20,171,-1,-1,154,155,-1,157,-1,159,-1,161,32,33,14,15,16,-1,18,-1,20,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,142,143,32,33,-1,147,-1,-1,-1,142,143,-1,154,155,147,157,-1,159,-1,161,-1,154,155,-1,157,-1,159,-1,161,171,14,15,-1,-1,-1,-1,20,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,142,143,-1,-1,-1,147,-1,-1,14,15,-1,-1,154,155,20,157,-1,159,-1,161,142,143,-1,-1,-1,-1,32,33,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,-1,-1,147,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,12,161,-1,-1,-1,17,-1,19,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,66,67,-1,171,142,143,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,90,91,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
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
     292,   293,   294,   318,   321,   257,    12,    12,   244,    12,
      34,   180,   235,    17,    19,    20,    35,    39,    40,    41,
      42,    43,   180,   188,   189,   190,   191,   192,   193,   194,
     195,   246,   247,   248,   249,   250,   251,   294,    15,    17,
      20,    29,    52,    58,    60,    64,    66,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    88,    89,    93,    95,
      96,    97,   134,   135,   136,   150,   168,   170,   180,   181,
     288,   289,   290,   291,   292,   293,   294,   297,   299,   302,
     303,    17,    19,   149,   265,   288,    17,    17,    19,    12,
     149,    12,   149,   169,   172,    14,    15,    16,    17,    32,
      33,   142,   172,   180,   217,   234,   235,   246,   180,   246,
     252,   253,   246,    27,    44,   246,   180,   246,   246,   246,
     246,   246,    27,    31,    37,    44,    90,   104,   288,   287,
     288,    30,   287,   288,   304,   305,    67,   180,   317,   318,
     320,   322,   323,   324,   180,   258,   262,   295,   296,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,    17,    17,   317,   324,   329,    17,   248,    17,    17,
     317,   319,   325,   326,   327,   328,   325,   322,   287,   288,
      17,    98,    12,    17,    19,    37,    66,    67,    90,    91,
     104,   105,   106,   107,   108,   109,   110,   111,   112,   113,
     114,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     152,   153,   266,   319,   246,   249,   180,   268,    18,   319,
     319,   288,   246,   288,   246,   180,     8,    10,    11,   240,
     241,   242,   255,   259,   274,   284,   180,   218,   219,   235,
     218,   218,   234,   218,   218,   288,     6,     9,    13,   220,
     221,   241,    17,    19,    14,    15,    16,    20,    32,    33,
     142,   143,   154,   155,   157,   159,   161,   171,   229,    13,
     235,    47,   254,    45,   149,   253,   254,    18,   246,   246,
      21,    36,    38,    38,   246,   246,   246,   246,   180,   180,
      18,    21,   146,    31,    30,    31,    86,    87,    30,   146,
      31,   118,   149,    13,   149,    59,   146,    61,   149,   287,
     288,   287,    13,   146,   288,    17,   287,   247,   118,   146,
     149,    13,   146,    13,    13,   151,    31,   288,   288,   287,
     288,   288,   288,   288,   180,   321,    92,   180,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   288,   288,   288,
     288,   288,   288,   288,   288,   288,   288,   317,   323,   180,
     321,    18,   146,   149,   180,   149,    49,   146,   269,   270,
     146,    18,    12,    12,   172,   245,   275,   276,   276,    47,
     245,   285,   286,   173,   240,   118,   146,    13,    33,    13,
      13,    13,    13,    13,   145,   180,   223,   180,   222,    14,
      15,    16,    17,    20,    22,    23,    24,    25,    26,    32,
      33,    58,    60,    64,   101,   103,   142,   147,   162,   163,
     164,   167,   225,   247,   310,   311,   313,    13,   221,   287,
     180,   217,   217,   217,   288,   217,   217,   288,   230,   288,
     288,   217,   217,   288,   288,   288,   217,   317,   246,   246,
     252,   246,   246,   288,   317,   323,   325,   288,   288,   288,
     305,   325,   288,   246,   288,   288,   296,   288,   300,   301,
     319,    18,    18,   288,   324,   146,   288,    18,   146,   288,
     317,   246,   288,   326,   288,   288,   325,    18,    18,    86,
     319,   246,    17,    19,   263,   246,   288,   180,    50,   272,
     317,   288,   288,   240,   180,   276,   288,   180,   308,   286,
     288,   219,   217,   219,   235,   217,   217,   217,   142,   217,
     217,   218,    12,   180,    12,   180,   218,   218,   218,    99,
     138,   139,   140,   180,   225,   227,   228,   234,    55,   279,
     280,   288,   316,   288,   218,   218,   295,   288,   306,   288,
      17,    17,    19,   288,   288,   287,   288,   306,   165,   180,
     317,   322,   288,    14,    15,    16,    20,    32,    33,   142,
     147,   154,   155,   157,   159,   161,   171,   173,   229,    17,
      19,   100,   225,    18,    32,   158,   145,   144,   146,    28,
     156,    46,   173,    13,    21,    13,    30,    30,    13,    30,
      62,    63,   298,   146,   173,    27,   246,    18,   288,    13,
     151,   288,   149,   319,    17,    46,   149,   288,    18,   173,
     149,   265,   149,    18,   288,    13,   288,    12,   224,   225,
     234,    12,    13,    13,    13,   307,   308,   235,   235,   235,
      18,    13,    33,    13,    56,    57,   281,   282,   283,   269,
      21,    21,    13,    13,    59,    27,    61,    31,   173,   149,
     247,   309,   310,   288,   145,   148,    13,   173,   317,    12,
      59,    59,   163,   225,   225,   225,   288,   225,   225,   288,
     288,   288,   225,   225,   288,   288,   288,   287,    94,   288,
     173,   288,   217,   217,   288,   288,   217,   288,   288,   288,
     288,   288,   288,    65,   301,   288,    18,    18,   288,    18,
     246,    18,   319,    53,    54,   264,   288,   246,    19,   246,
     277,   267,   268,   246,    17,   145,   217,   288,    13,   224,
     225,   225,    20,    13,   146,   225,   228,   225,   282,   146,
     247,   272,    27,   141,    20,   225,   225,   225,   225,   288,
     314,   315,   319,   100,    18,    33,    18,   218,   218,    17,
     118,   288,   288,   288,   225,    31,    32,   158,    31,   145,
      31,   156,    18,   288,    21,    28,    31,    21,    30,    30,
      61,    27,   151,    18,   269,   102,   102,   180,   279,    59,
     100,   288,   180,   217,   225,   288,   225,   308,    18,    18,
     247,   149,   225,   225,   288,    62,    63,   312,    27,   146,
     173,    27,   310,    13,    13,   225,   226,   288,    38,   163,
     163,   288,   288,   225,   288,   225,   288,   225,   217,   288,
     325,   288,   288,   271,   272,    19,   246,    19,   246,   263,
     269,   288,   288,    18,    21,    18,    17,    17,   149,   246,
      21,    31,   288,   225,    62,    63,   225,    65,   315,   225,
      20,   225,    18,   146,   163,   288,   225,   225,    32,    21,
      31,   145,   148,    13,   144,   298,   173,    51,   273,    46,
     272,   225,   287,   287,   246,   225,   288,    61,   288,   225,
      27,   288,   225,   225,   163,   166,   288,   225,   288,   225,
     225,   288,   247,    53,    54,   225,   278,    18,    18,    21,
     225,    61,   225,    21,   225,   288,    31,   145,   144,   269,
     225,   225,   173,   225,   163,   288,   225,   271,   225,    21,
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
       1,     2,     2,     3,     5,     3,     5,     1,     2,     1,
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
      -1,    48,    -1,    48,   256,    -1,   257,    -1,   256,   257,
      -1,   245,   258,    -1,   180,    12,   288,    -1,   180,   149,
     246,    12,   288,    -1,   318,    12,   288,    -1,   318,   149,
     246,    12,   288,    -1,     7,    -1,     7,   260,    -1,   261,
      -1,   260,   261,    -1,   245,   180,   265,   268,   269,   272,
      -1,   245,   262,    -1,   180,   149,   249,   180,   263,    46,
     264,   269,   271,   273,    -1,    19,    -1,    17,   319,    18,
      -1,   263,    17,   319,    18,    -1,   288,    -1,    53,    -1,
      54,    -1,    19,    -1,    17,   266,    18,    -1,   319,   149,
     246,    -1,   266,   146,   319,   149,   246,    -1,    -1,   268,
      -1,   180,   149,   246,    -1,   268,   146,   180,   149,   246,
      -1,   270,    -1,    -1,    49,   288,    -1,   272,    -1,    -1,
      50,   288,    -1,    51,   247,    -1,    -1,     8,    -1,     8,
     275,    -1,   276,    -1,   275,   276,    -1,   245,   180,   265,
     267,   279,   269,   272,    -1,   245,   180,   149,   277,   180,
     263,    46,   278,   269,   271,    -1,   246,   102,   246,    -1,
      19,   102,   246,    -1,   246,   102,    19,    -1,    19,   102,
      19,    -1,   225,    -1,    53,    -1,    54,    -1,   280,    -1,
      -1,    55,   281,    -1,   282,    -1,   281,   282,    -1,   283,
     247,    -1,   283,   247,   149,   246,    -1,   282,   146,   247,
      -1,   282,   146,   247,   149,   246,    -1,    56,    -1,    57,
      -1,    11,    -1,    11,   285,    -1,   286,    -1,   285,   286,
      -1,   245,   308,    -1,    47,   288,    -1,   288,    -1,   287,
     146,   288,    -1,   292,    -1,   289,    -1,   291,    -1,   290,
      -1,   293,    -1,   294,    -1,    17,   288,    18,    -1,   181,
      -1,   180,    -1,   180,    98,    -1,   288,    90,   180,    -1,
     288,   104,   180,    -1,   288,    91,    92,    -1,   288,    19,
      -1,   288,    17,   287,    18,    -1,   288,    17,   288,    86,
     288,    18,    -1,   288,    90,   321,    -1,   288,   153,   180,
      -1,   288,   153,   321,    -1,   288,   152,   317,    -1,   288,
     152,   323,    -1,    58,   295,    59,   288,    -1,   297,    -1,
     299,    -1,   302,    -1,   303,    -1,   134,   325,    13,   288,
      -1,   135,   325,    13,   288,    -1,   136,   322,    13,   288,
      -1,    52,   322,    13,   288,    -1,    29,    30,    -1,    29,
     287,    30,    -1,    29,   288,    31,   325,    30,    -1,    29,
     288,    31,   325,    13,   288,    30,    -1,    29,   288,    86,
     288,    30,    -1,    15,    -1,    20,   287,    21,    -1,    20,
     288,    31,   323,    21,    -1,    20,   288,    31,   323,    13,
     288,    21,    -1,   170,    -1,    29,   304,    30,    -1,    29,
     305,    31,   325,    30,    -1,    29,   305,    31,   325,    13,
     288,    30,    -1,    88,    17,   287,    18,    -1,    89,    17,
     287,    18,    -1,    93,   329,    13,   288,    -1,   168,    17,
     288,    18,    -1,    95,   248,    17,   288,    18,    -1,    95,
      17,   288,   146,   246,    18,    -1,    96,    17,   287,    18,
      -1,    97,    17,   247,   146,   288,    18,    -1,   150,   287,
     151,    -1,   150,   288,    31,   325,   151,    -1,   150,   288,
      31,   325,    13,   288,   151,    -1,   183,    -1,   182,    -1,
     184,    -1,   185,    -1,    92,    -1,   187,    -1,   160,    -1,
     137,    -1,   186,    -1,   296,    -1,   295,   146,   296,    -1,
     258,    -1,   262,    -1,    60,   288,    61,   288,   298,    -1,
      63,   288,    -1,    62,   288,    61,   288,   298,    -1,    64,
     288,   149,   300,   173,    -1,    64,   288,   149,   300,   146,
      65,    27,   288,   173,    -1,   301,    -1,   300,   146,   301,
      -1,   319,    27,   288,    -1,    66,   288,    -1,    67,   288,
      -1,    68,   288,    -1,    69,   288,    -1,    70,   288,    -1,
      71,   288,    -1,    72,   288,    -1,    73,   288,    -1,    74,
     288,    -1,    75,   288,    -1,    76,   288,    -1,    77,   288,
      -1,    78,   288,    -1,    79,   288,    -1,    80,   288,    -1,
      81,   288,    -1,    82,   288,    -1,    83,   288,    -1,    84,
     288,    -1,    85,   288,    -1,   288,    66,   288,    -1,   288,
      37,   288,    -1,   288,    67,   288,    -1,   288,   106,   288,
      -1,   288,   105,   288,    -1,   288,   107,   288,    -1,   288,
     108,   288,    -1,   288,   109,   288,    -1,   288,   110,   288,
      -1,   288,   111,   288,    -1,   288,   112,   288,    -1,   288,
      12,   288,    -1,   288,   113,   288,    -1,   288,   114,   288,
      -1,   288,   115,   288,    -1,   288,   116,   288,    -1,   288,
     117,   288,    -1,   288,   118,   288,    -1,   288,   119,   288,
      -1,   288,   120,   288,    -1,   288,   121,   288,    -1,   288,
     122,   288,    -1,   288,   123,   288,    -1,   288,   124,   288,
      -1,   288,   125,   288,    -1,   288,   126,   288,    -1,   288,
     127,   288,    -1,   288,   128,   288,    -1,   288,   129,   288,
      -1,   288,   130,   288,    -1,   288,   131,   288,    -1,   288,
     132,   288,    -1,   288,   133,   288,    -1,   305,    -1,   304,
     146,   305,    -1,   288,    87,   288,    -1,   288,    27,   225,
      -1,   306,    31,   288,    27,   225,    -1,   308,    -1,   307,
     146,   308,    -1,   180,   149,   246,    -1,   180,   149,   246,
     100,   288,    -1,   180,   149,   246,    59,   288,    -1,   310,
      -1,   309,    33,   310,    -1,   247,   100,   288,    -1,    60,
     288,    61,   225,   312,    63,   225,    -1,    60,   288,    61,
     225,    63,   225,    -1,    62,   288,    61,   225,    -1,   312,
      62,   288,    61,   225,    -1,    64,   288,   149,   314,   173,
      -1,    64,   288,   149,   314,   146,    65,    27,   225,   173,
      -1,   315,    -1,   314,   146,   315,    -1,   319,    27,   225,
      -1,   279,   269,   272,    -1,   320,    -1,   318,    -1,   321,
      -1,    88,    17,   319,   146,   317,    18,    -1,    89,    19,
      -1,    89,    17,   319,    18,    -1,   317,    -1,   319,   146,
     317,    -1,   180,    -1,    67,    -1,   292,    -1,   289,    -1,
     291,    -1,   290,    -1,   293,    -1,   294,    -1,    17,   288,
      18,    -1,   323,    -1,   324,    -1,   317,   118,   288,    -1,
     317,   149,   246,    -1,   326,    -1,   325,   146,   326,    -1,
     327,    -1,   328,    -1,   319,   118,   288,    -1,   319,   149,
     246,    -1,   324,    -1,   329,   146,   324,    -1
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
     856,   858,   861,   864,   868,   874,   878,   884,   886,   889,
     891,   894,   901,   904,   915,   917,   921,   926,   928,   930,
     932,   934,   938,   942,   948,   949,   951,   955,   961,   963,
     964,   967,   969,   970,   973,   976,   977,   979,   982,   984,
     987,   995,  1006,  1010,  1014,  1018,  1022,  1024,  1026,  1028,
    1030,  1031,  1034,  1036,  1039,  1042,  1047,  1051,  1057,  1059,
    1061,  1063,  1066,  1068,  1071,  1074,  1077,  1079,  1083,  1085,
    1087,  1089,  1091,  1093,  1095,  1099,  1101,  1103,  1106,  1110,
    1114,  1118,  1121,  1126,  1133,  1137,  1141,  1145,  1149,  1153,
    1158,  1160,  1162,  1164,  1166,  1171,  1176,  1181,  1186,  1189,
    1193,  1199,  1207,  1213,  1215,  1219,  1225,  1233,  1235,  1239,
    1245,  1253,  1258,  1263,  1268,  1273,  1279,  1286,  1291,  1298,
    1302,  1308,  1316,  1318,  1320,  1322,  1324,  1326,  1328,  1330,
    1332,  1334,  1336,  1340,  1342,  1344,  1350,  1353,  1359,  1365,
    1375,  1377,  1381,  1385,  1388,  1391,  1394,  1397,  1400,  1403,
    1406,  1409,  1412,  1415,  1418,  1421,  1424,  1427,  1430,  1433,
    1436,  1439,  1442,  1445,  1449,  1453,  1457,  1461,  1465,  1469,
    1473,  1477,  1481,  1485,  1489,  1493,  1497,  1501,  1505,  1509,
    1513,  1517,  1521,  1525,  1529,  1533,  1537,  1541,  1545,  1549,
    1553,  1557,  1561,  1565,  1569,  1573,  1577,  1579,  1583,  1587,
    1591,  1597,  1599,  1603,  1607,  1613,  1619,  1621,  1625,  1629,
    1637,  1644,  1649,  1655,  1661,  1671,  1673,  1677,  1681,  1685,
    1687,  1689,  1691,  1698,  1701,  1706,  1708,  1712,  1714,  1716,
    1718,  1720,  1722,  1724,  1726,  1728,  1732,  1734,  1736,  1740,
    1744,  1746,  1750,  1752,  1754,  1758,  1762,  1764
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   548,   548,   556,   562,   573,   574,   575,   576,   577,
     581,   592,   625,   653,   687,   694,   702,   708,   714,   723,
     732,   738,   744,   750,   765,   772,   787,   805,   812,   819,
     829,   843,   852,   862,   872,   882,   890,   898,   909,   922,
     932,   940,   951,   957,   963,   969,   986,   999,  1011,  1017,
    1033,  1037,  1044,  1048,  1055,  1072,  1098,  1102,  1109,  1113,
    1120,  1125,  1130,  1135,  1140,  1146,  1163,  1186,  1193,  1200,
    1207,  1214,  1229,  1236,  1251,  1267,  1274,  1281,  1288,  1300,
    1314,  1325,  1339,  1350,  1364,  1381,  1398,  1414,  1433,  1440,
    1451,  1458,  1466,  1474,  1485,  1498,  1511,  1525,  1538,  1550,
    1559,  1569,  1573,  1581,  1601,  1605,  1612,  1616,  1620,  1631,
    1635,  1652,  1689,  1699,  1715,  1731,  1740,  1751,  1762,  1777,
    1783,  1792,  1798,  1807,  1813,  1819,  1828,  1834,  1847,  1872,
    1883,  1903,  1918,  1939,  1945,  1962,  1983,  2004,  2010,  2019,
    2032,  2042,  2058,  2078,  2084,  2096,  2112,  2116,  2120,  2127,
    2133,  2142,  2146,  2150,  2154,  2158,  2168,  2182,  2192,  2204,
    2219,  2225,  2234,  2254,  2275,  2304,  2351,  2358,  2365,  2372,
    2380,  2389,  2396,  2400,  2405,  2417,  2451,  2479,  2486,  2493,
    2500,  2507,  2515,  2523,  2535,  2555,  2563,  2571,  2582,  2586,
    2590,  2594,  2598,  2602,  2606,  2610,  2617,  2621,  2628,  2637,
    2649,  2658,  2670,  2676,  2685,  2693,  2703,  2716,  2744,  2756,
    2786,  2792,  2802,  2812,  2836,  2862,  2882,  2907,  2921,  2950,
    2956,  2965,  2989,  2999,  3021,  3025,  3032,  3042,  3046,  3050,
    3057,  3061,  3068,  3075,  3086,  3089,  3096,  3106,  3119,  3120,
    3124,  3128,  3129,  3133,  3143,  3151,  3157,  3170,  3191,  3197,
    3206,  3233,  3257,  3269,  3281,  3293,  3307,  3311,  3315,  3322,
    3323,  3327,  3331,  3337,  3357,  3366,  3375,  3384,  3396,  3400,
    3429,  3435,  3451,  3459,  3468,  3474,  3488,  3494,  3537,  3549,
    3554,  3558,  3563,  3575,  3581,  3585,  3589,  3593,  3597,  3601,
    3605,  3609,  3613,  3617,  3621,  3626,  3630,  3634,  3638,  3643,
    3650,  3654,  3658,  3662,  3667,  3675,  3683,  3691,  3700,  3707,
    3714,  3721,  3730,  3738,  3754,  3761,  3768,  3777,  3782,  3789,
    3796,  3805,  3812,  3826,  3841,  3848,  3852,  3860,  3877,  3890,
    3900,  3911,  3926,  3930,  3937,  3948,  3955,  3961,  3968,  3982,
    3991,  4002,  4008,  4017,  4018,  4022,  4054,  4061,  4075,  4082,
    4093,  4099,  4110,  4126,  4132,  4138,  4144,  4150,  4156,  4162,
    4168,  4174,  4180,  4186,  4192,  4198,  4204,  4210,  4216,  4222,
    4228,  4234,  4240,  4249,  4257,  4265,  4273,  4281,  4289,  4297,
    4305,  4313,  4321,  4329,  4337,  4345,  4353,  4361,  4369,  4377,
    4385,  4393,  4401,  4409,  4417,  4425,  4433,  4441,  4449,  4457,
    4465,  4473,  4481,  4489,  4497,  4505,  4516,  4522,  4530,  4542,
    4555,  4571,  4577,  4586,  4603,  4619,  4642,  4649,  4667,  4682,
    4691,  4703,  4714,  4728,  4735,  4746,  4752,  4763,  4775,  4793,
    4794,  4798,  4802,  4819,  4830,  4844,  4850,  4859,  4873,  4883,
    4893,  4898,  4903,  4908,  4913,  4918,  4929,  4930,  4934,  4945,
    4956,  4962,  4971,  4972,  4976,  4987,  4998,  5005
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

  private static final int yylast_ = 11796;
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
/* Line 9414 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


