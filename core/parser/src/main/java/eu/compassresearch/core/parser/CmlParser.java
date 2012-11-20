
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
  /** Token number, to be returned by the scanner.  */
  public static final int QUESTIONALONE = 463;



  
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
/* Line 337 of "src/main/bison/cml.y"  */
    {
  List<SParagraphDefinition> paragraphs = (List<SParagraphDefinition>) ((yystack.valueAt (1-(1))));
  currentSource.setParagraphs(paragraphs);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 345 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseFirstListElement((PDefinition)((yystack.valueAt (1-(1)))));
};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 349 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseNextListElement(((yystack.valueAt (2-(1)))),(PDefinition)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 355 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 356 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 357 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 358 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 359 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 364 of "src/main/bison/cml.y"  */
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
/* Line 378 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),(CmlLexeme)((yystack.valueAt (8-(8)))));
  List<LexNameToken> supernames = new LinkedList<LexNameToken>();
  supernames.add(util.extractLexNameToken(((yystack.valueAt (8-(4))))));
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
/* Line 412 of "src/main/bison/cml.y"  */
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
/* Line 440 of "src/main/bison/cml.y"  */
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
/* Line 475 of "src/main/bison/cml.y"  */
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
/* Line 482 of "src/main/bison/cml.y"  */
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
/* Line 490 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASequentialCompositionProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 496 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AExternalChoiceProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 502 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInternalChoiceProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 511 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  yyval = new AGeneralisedParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 520 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (7-(1))));
  PProcess right = (PProcess)((yystack.valueAt (7-(7))));
  yyval = new AAlphabetisedParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (7-(3)))), (PExp)((yystack.valueAt (7-(5)))), right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 526 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 532 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 538 of "src/main/bison/cml.y"  */
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
/* Line 553 of "src/main/bison/cml.y"  */
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
/* Line 560 of "src/main/bison/cml.y"  */
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
/* Line 575 of "src/main/bison/cml.y"  */
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
/* Line 593 of "src/main/bison/cml.y"  */
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
/* Line 600 of "src/main/bison/cml.y"  */
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
/* Line 607 of "src/main/bison/cml.y"  */
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
/* Line 617 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8)))));
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (8-(2))));
  List<PExp> args = new LinkedList<PExp>();
  args.add((PExp)((yystack.valueAt (8-(7)))));
  PProcess proc = (PProcess)((yystack.valueAt (8-(4))));
  yyval = new AInstantiationProcess(location,
				 decls,
				 proc,
				 args);
};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 629 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken(((yystack.valueAt (1-(1)))));
  yyval = new AReferenceProcess(name.location,
			     name,
			     new LinkedList<PExp>());
  /* $$ = new AInstantiationProcess(name.location, */
  /*                                null, */
  /*                                name, */
  /*                                null, */
  /*                                null); */
};
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 641 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken(((yystack.valueAt (2-(1)))));
  LexLocation location = util.extractLexLocation(name.location,(CmlLexeme)((yystack.valueAt (2-(2)))));
  yyval = new AReferenceProcess(location,
			     name,
			     new LinkedList<PExp>());

  /* $$ = new AInstantiationProcess(location, */
  /*                                null, */
  /*                                name, */
  /*                                null, */
  /*                                null); */
};
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 655 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken(((yystack.valueAt (4-(1)))));
  LexLocation location = util.extractLexLocation(name.location,(CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AReferenceProcess(location,
			     name,
			     (List<PExp>)((yystack.valueAt (4-(3)))));

  /* $$ = new AInstantiationProcess(location, */
  /*                                null, */
  /*                                name, */
  /*                                null, */
  /*                                (List<PExp>)$expressionList); */
};
  break;
    

  case 34:
  if (yyn == 34)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 669 of "src/main/bison/cml.y"  */
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
/* Line 679 of "src/main/bison/cml.y"  */
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
/* Line 687 of "src/main/bison/cml.y"  */
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
/* Line 695 of "src/main/bison/cml.y"  */
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
/* Line 706 of "src/main/bison/cml.y"  */
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
/* Line 719 of "src/main/bison/cml.y"  */
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
/* Line 729 of "src/main/bison/cml.y"  */
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
/* Line 737 of "src/main/bison/cml.y"  */
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
/* Line 748 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 754 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 760 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 766 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 783 of "src/main/bison/cml.y"  */
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
/* Line 796 of "src/main/bison/cml.y"  */
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
/* Line 808 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = processParagraphList;
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 814 of "src/main/bison/cml.y"  */
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
/* Line 830 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 834 of "src/main/bison/cml.y"  */
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
/* Line 841 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 845 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 852 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseFirstListElement(util.caseActionDefinition(((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3))))));
};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 856 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseNextListElement(((yystack.valueAt (4-(1)))),util.caseActionDefinition(((yystack.valueAt (4-(2)))), ((yystack.valueAt (4-(3)))),((yystack.valueAt (4-(4))))));
};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 869 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 873 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 880 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{new Vector<ATypeSingleDeclaration>(), ((yystack.valueAt (1-(1))))};
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 884 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3))))};
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 891 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASkipAction(location);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 896 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AStopAction(location);
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 901 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AChaosAction(location);
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 906 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ADivAction(location);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 911 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
  yyval = new AWaitAction(location, exp);
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 917 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  PAction action = (PAction)((yystack.valueAt (3-(2))));
  yyval = new ABlockStatementAction(location, null, action);
};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 934 of "src/main/bison/cml.y"  */
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
/* Line 958 of "src/main/bison/cml.y"  */
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
/* Line 965 of "src/main/bison/cml.y"  */
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
/* Line 972 of "src/main/bison/cml.y"  */
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
/* Line 979 of "src/main/bison/cml.y"  */
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
/* Line 986 of "src/main/bison/cml.y"  */
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
/* Line 1001 of "src/main/bison/cml.y"  */
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
/* Line 1008 of "src/main/bison/cml.y"  */
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
/* Line 1023 of "src/main/bison/cml.y"  */
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
/* Line 1039 of "src/main/bison/cml.y"  */
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
/* Line 1046 of "src/main/bison/cml.y"  */
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
/* Line 1053 of "src/main/bison/cml.y"  */
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
/* Line 1060 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PAction action = (PAction)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingAction(util.combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1072 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseMuAction(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(5)))),((yystack.valueAt (6-(6)))));
};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1079 of "src/main/bison/cml.y"  */
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
/* Line 1090 of "src/main/bison/cml.y"  */
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
/* Line 1104 of "src/main/bison/cml.y"  */
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
/* Line 1115 of "src/main/bison/cml.y"  */
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
/* Line 1129 of "src/main/bison/cml.y"  */
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
/* Line 1146 of "src/main/bison/cml.y"  */
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
/* Line 1163 of "src/main/bison/cml.y"  */
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
/* Line 1179 of "src/main/bison/cml.y"  */
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
/* Line 1198 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierToRefAction(((yystack.valueAt (1-(1)))));
};
  break;
    

  case 89:
  if (yyn == 89)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1202 of "src/main/bison/cml.y"  */
    {
  yyval = new AParametrisedAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),
                                                  (CmlLexeme)((yystack.valueAt (5-(5))))),
                               (List<PParametrisation>)((yystack.valueAt (5-(2)))),
                               (PAction)((yystack.valueAt (5-(4)))));
};
  break;
    

  case 90:
  if (yyn == 90)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1209 of "src/main/bison/cml.y"  */
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
    

  case 91:
  if (yyn == 91)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1220 of "src/main/bison/cml.y"  */
    {
  yyval = new ADeclarationInstantiatedAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))), (CmlLexeme)((yystack.valueAt (8-(8))))),
                                          (List<? extends ATypeSingleDeclaration>)((yystack.valueAt (8-(2)))),
                                          (PAction)((yystack.valueAt (8-(4)))),
                                          (List<PExp>)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 92:
  if (yyn == 92)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1227 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new ASequentialCompositionReplicatedAction(location,
                                                  (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                                  action);
};
  break;
    

  case 93:
  if (yyn == 93)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1235 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AExternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                           action);
};
  break;
    

  case 94:
  if (yyn == 94)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1243 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AInternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)((yystack.valueAt (4-(2)))),
                                           action);
};
  break;
    

  case 95:
  if (yyn == 95)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1254 of "src/main/bison/cml.y"  */
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
    

  case 96:
  if (yyn == 96)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1267 of "src/main/bison/cml.y"  */
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
    

  case 97:
  if (yyn == 97)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1280 of "src/main/bison/cml.y"  */
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
    

  case 98:
  if (yyn == 98)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1294 of "src/main/bison/cml.y"  */
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
    

  case 99:
  if (yyn == 99)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1307 of "src/main/bison/cml.y"  */
    {
  PAction replicatedAction = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),replicatedAction.getLocation());
  yyval = new ASynchronousParallelismReplicatedAction(location,
                                                   (List<? extends SSingleDeclaration>)((yystack.valueAt (7-(2)))),
                                                   replicatedAction,
                                                   (PExp)((yystack.valueAt (7-(5)))));
};
  break;
    

  case 100:
  if (yyn == 100)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1319 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                            action.getLocation());
  yyval = new ALetStatementAction(location,
                               action,
                               (List<? extends PDefinition>)((yystack.valueAt (4-(2)))));
};
  break;
    

  case 101:
  if (yyn == 101)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1328 of "src/main/bison/cml.y"  */
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
    

  case 102:
  if (yyn == 102)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1338 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 103:
  if (yyn == 103)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1342 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)((yystack.valueAt (3-(2))));
  yyval = new ANonDeterministicIfStatementAction(location,
                                              alternatives);
};
  break;
    

  case 104:
  if (yyn == 104)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1350 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)((yystack.valueAt (3-(2))));
  yyval = new ANonDeterministicDoStatementAction(location,
                                              alternatives);
};
  break;
    

  case 105:
  if (yyn == 105)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1370 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1374 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AMultipleGeneralAssignmentStatementAction(location,
                                                     (List<? extends ASingleGeneralAssignmentStatementAction>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1381 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierLRPARENToCallAction(((yystack.valueAt (2-(1)))), ((yystack.valueAt (2-(2)))),new LinkedList<PExp>());
};
  break;
    

  case 108:
  if (yyn == 108)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1385 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierLRPARENToCallAction(((yystack.valueAt (4-(1)))), ((yystack.valueAt (4-(4)))),((yystack.valueAt (4-(3)))));
};
  break;
    

  case 109:
  if (yyn == 109)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1389 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 110:
  if (yyn == 110)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1400 of "src/main/bison/cml.y"  */
    {
  yyval = new AReturnStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), null);
};
  break;
    

  case 111:
  if (yyn == 111)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1404 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (4-(3))));
  yyval = new AReturnStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
                                                     exp.getLocation()),
                                  exp);
};
  break;
    

  case 112:
  if (yyn == 112)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1421 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseNewStatementAction(((yystack.valueAt (4-(1)))), ((yystack.valueAt (4-(3)))), ((yystack.valueAt (4-(4)))));
};
  break;
    

  case 113:
  if (yyn == 113)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1444 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 114:
  if (yyn == 114)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1454 of "src/main/bison/cml.y"  */
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
    

  case 115:
  if (yyn == 115)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1470 of "src/main/bison/cml.y"  */
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
    

  case 116:
  if (yyn == 116)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1486 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (7-(7))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),action.getLocation());
  yyval = new AForSetStatementAction(location,
                                  (PPattern)((yystack.valueAt (7-(3)))),
                                  (PExp)((yystack.valueAt (7-(5)))),
                                  action);
};
  break;
    

  case 117:
  if (yyn == 117)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1495 of "src/main/bison/cml.y"  */
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
    

  case 118:
  if (yyn == 118)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1506 of "src/main/bison/cml.y"  */
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
    

  case 119:
  if (yyn == 119)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1517 of "src/main/bison/cml.y"  */
    {
  PAction action = (PAction)((yystack.valueAt (4-(4))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
  yyval = new AWhileStatementAction(location,
                                 (PExp)((yystack.valueAt (4-(2)))),
                                 action);
};
  break;
    

  case 120:
  if (yyn == 120)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1532 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = new LinkedList<PAction>();
  actionList.add((PAction)((yystack.valueAt (1-(1)))));
  yyval = actionList;
};
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1538 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = (List<PAction>)((yystack.valueAt (3-(1))));
  actionList.add((PAction)((yystack.valueAt (3-(3)))));
  yyval = actionList;
};
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1547 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add((PParametrisation)((yystack.valueAt (1-(1)))));
  yyval = plist;
};
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1553 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add(0,(PParametrisation)((yystack.valueAt (3-(3)))));
  yyval = plist;
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1562 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AValParametrisation(loc, decl);
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1568 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AResParametrisation(loc, decl);
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1574 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AVresParametrisation(loc, decl);
};
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1583 of "src/main/bison/cml.y"  */
    {
  yyval = new AEnumerationRenameChannelExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
                                                           (CmlLexeme)((yystack.valueAt (3-(3))))),
                                        (List<? extends ARenamePair>)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 128:
  if (yyn == 128)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1589 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseRenameExpressionAComprehensionRenameChannelExp(((yystack.valueAt (7-(1)))),((yystack.valueAt (7-(2)))),((yystack.valueAt (7-(4)))),((yystack.valueAt (7-(6)))),null,((yystack.valueAt (7-(7)))));
};
  break;
    

  case 129:
  if (yyn == 129)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1593 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseRenameExpressionAComprehensionRenameChannelExp(((yystack.valueAt (9-(1)))),((yystack.valueAt (9-(2)))),((yystack.valueAt (9-(4)))),((yystack.valueAt (9-(6)))),((yystack.valueAt (9-(8)))),((yystack.valueAt (9-(9)))));
};
  break;
    

  case 130:
  if (yyn == 130)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1609 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseARenamePair(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 131:
  if (yyn == 131)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1613 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseRenameList(((yystack.valueAt (5-(1)))),((yystack.valueAt (5-(3)))),((yystack.valueAt (5-(5)))));
};
  break;
    

  case 132:
  if (yyn == 132)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1626 of "src/main/bison/cml.y"  */
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
    

  case 133:
  if (yyn == 133)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1641 of "src/main/bison/cml.y"  */
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
    

  case 134:
  if (yyn == 134)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1662 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDefinition> decls = new Vector<AChannelNameDefinition>();
  decls.add((AChannelNameDefinition)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1668 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDefinition> decls = (List<AChannelNameDefinition>)((yystack.valueAt (2-(1))));
  decls.add((AChannelNameDefinition)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 136:
  if (yyn == 136)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1685 of "src/main/bison/cml.y"  */
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
    

  case 137:
  if (yyn == 137)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1706 of "src/main/bison/cml.y"  */
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
    

  case 138:
  if (yyn == 138)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1727 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = new Vector<ATypeSingleDeclaration>();
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1733 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 140:
  if (yyn == 140)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1742 of "src/main/bison/cml.y"  */
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
    

  case 141:
  if (yyn == 141)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1755 of "src/main/bison/cml.y"  */
    {
  LexIdentifierToken id = util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (3-(3))));
  decl.setLocation(util.combineLexLocation(id.getLocation(), decl.getLocation()));
  decl.getIdentifiers().addFirst(id);
  yyval = decl;
};
  break;
    

  case 142:
  if (yyn == 142)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1765 of "src/main/bison/cml.y"  */
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
    

  case 143:
  if (yyn == 143)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1781 of "src/main/bison/cml.y"  */
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
    

  case 144:
  if (yyn == 144)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1801 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1807 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (3-(1))));
  defs.add((AChansetDefinition)((yystack.valueAt (3-(3)))));
  yyval = defs;
};
  break;
    

  case 146:
  if (yyn == 146)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1819 of "src/main/bison/cml.y"  */
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
    

  case 147:
  if (yyn == 147)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1835 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1839 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1843 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1850 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1856 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  defs.add((PDefinition)((yystack.valueAt (2-(1)))));
  yyval = defs;
};
  break;
    

  case 152:
  if (yyn == 152)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1865 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 153:
  if (yyn == 153)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1869 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1873 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1877 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1881 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 157:
  if (yyn == 157)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1891 of "src/main/bison/cml.y"  */
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
    

  case 158:
  if (yyn == 158)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1905 of "src/main/bison/cml.y"  */
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
    

  case 159:
  if (yyn == 159)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1915 of "src/main/bison/cml.y"  */
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
    

  case 160:
  if (yyn == 160)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1927 of "src/main/bison/cml.y"  */
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
    

  case 161:
  if (yyn == 161)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1942 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
  yyval = list;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1948 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
  list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
  yyval = list;
};
  break;
    

  case 163:
  if (yyn == 163)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1957 of "src/main/bison/cml.y"  */
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
    

  case 164:
  if (yyn == 164)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1977 of "src/main/bison/cml.y"  */
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
    

  case 165:
  if (yyn == 165)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1998 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseRecordTypeDefinition(((yystack.valueAt (4-(1)))), ((yystack.valueAt (4-(2)))), ((yystack.valueAt (4-(4)))));
};
  break;
    

  case 166:
  if (yyn == 166)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2002 of "src/main/bison/cml.y"  */
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
    

  case 167:
  if (yyn == 167)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2049 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 168:
  if (yyn == 168)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2056 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new AProtectedAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 169:
  if (yyn == 169)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2063 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 170:
  if (yyn == 170)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2070 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 171:
  if (yyn == 171)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2077 of "src/main/bison/cml.y"  */
    {
  /*Default private*/
  yyval = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                           null,
                                           null);//, location);
};
  break;
    

  case 172:
  if (yyn == 172)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2087 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (3-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
  type.setLocation(loc);
  yyval = type;
};
  break;
    

  case 173:
  if (yyn == 173)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2094 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 174:
  if (yyn == 174)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2098 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken quote = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteType(quote.location, false, null, quote);
};
  break;
    

  case 175:
  if (yyn == 175)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2103 of "src/main/bison/cml.y"  */
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
    

  case 176:
  if (yyn == 176)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2115 of "src/main/bison/cml.y"  */
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
    

  case 177:
  if (yyn == 177)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2149 of "src/main/bison/cml.y"  */
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
    

  case 178:
  if (yyn == 178)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2177 of "src/main/bison/cml.y"  */
    {
  yyval = new AOptionalType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))),
                         false,
                         null,
                         (PType)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 179:
  if (yyn == 179)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2184 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASetType(loc, false, null, type, false, false);
};
  break;
    

  case 180:
  if (yyn == 180)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2191 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASeqSeqType(loc, false, null, type, false);
};
  break;
    

  case 181:
  if (yyn == 181)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2198 of "src/main/bison/cml.y"  */
    {
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
                                       type.getLocation());
  yyval = new ASeq1SeqType(loc, false, null, type, false);
};
  break;
    

  case 182:
  if (yyn == 182)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2205 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (4-(2))));
  PType rng = (PType)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))),
                                       rng.getLocation());
  yyval = new AMapMapType(loc, false, null, dom, rng, false);
};
  break;
    

  case 183:
  if (yyn == 183)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2213 of "src/main/bison/cml.y"  */
    {
  PType dom = (PType)((yystack.valueAt (4-(2))));
  PType rng = (PType)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))),
                                       rng.getLocation());
  yyval = new AMapMapType(loc, false, null, dom, rng, false);
};
  break;
    

  case 184:
  if (yyn == 184)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2221 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 185:
  if (yyn == 185)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2233 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierToNamedType(((yystack.valueAt (1-(1)))));
};
  break;
    

  case 186:
  if (yyn == 186)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2244 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1))))));
  yyval = ids;
};
  break;
    

  case 187:
  if (yyn == 187)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2250 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = ids;
};
  break;
    

  case 188:
  if (yyn == 188)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2256 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = ids;
};
  break;
    

  case 189:
  if (yyn == 189)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2265 of "src/main/bison/cml.y"  */
    {
  yyval = new ABooleanBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 190:
  if (yyn == 190)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2269 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 191:
  if (yyn == 191)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2273 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatOneNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 192:
  if (yyn == 192)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2277 of "src/main/bison/cml.y"  */
    {
  yyval = new AIntNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 193:
  if (yyn == 193)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2281 of "src/main/bison/cml.y"  */
    {
  yyval = new ARationalNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 194:
  if (yyn == 194)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2285 of "src/main/bison/cml.y"  */
    {
  yyval = new ARealNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 195:
  if (yyn == 195)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2289 of "src/main/bison/cml.y"  */
    {
  yyval = new ACharBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 196:
  if (yyn == 196)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2293 of "src/main/bison/cml.y"  */
    {
  yyval = new ATokenBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 197:
  if (yyn == 197)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2300 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 198:
  if (yyn == 198)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2304 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 199:
  if (yyn == 199)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2311 of "src/main/bison/cml.y"  */
    {
  PType domType = (PType)((yystack.valueAt (3-(1))));
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
/* Line 2320 of "src/main/bison/cml.y"  */
    {
  PType domType = new AVoidType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, true, params, rngType);
};
  break;
    

  case 201:
  if (yyn == 201)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2332 of "src/main/bison/cml.y"  */
    {
  PType domType = (PType)((yystack.valueAt (3-(1))));
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
/* Line 2341 of "src/main/bison/cml.y"  */
    {
  PType domType = new AVoidType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), true);
  PType rngType = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  yyval = new AFunctionType(loc, false, null, false, params, rngType);
};
  break;
    

  case 203:
  if (yyn == 203)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2353 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 204:
  if (yyn == 204)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2359 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = (List<AFieldField>)((yystack.valueAt (2-(1))));
  res.add((AFieldField)((yystack.valueAt (2-(2)))));
  yyval = res;
};
  break;
    

  case 205:
  if (yyn == 205)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2368 of "src/main/bison/cml.y"  */
    {
  yyval = new AFieldField(util.getDefaultAccessSpecifier(false,false,null),
                       null,
                       null,
                       (PType)((yystack.valueAt (1-(1)))),
                       false);
};
  break;
    

  case 206:
  if (yyn == 206)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2376 of "src/main/bison/cml.y"  */
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
    

  case 207:
  if (yyn == 207)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2386 of "src/main/bison/cml.y"  */
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
    

  case 208:
  if (yyn == 208)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2399 of "src/main/bison/cml.y"  */
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
    

  case 209:
  if (yyn == 209)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2427 of "src/main/bison/cml.y"  */
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
    

  case 210:
  if (yyn == 210)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2439 of "src/main/bison/cml.y"  */
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
    

  case 211:
  if (yyn == 211)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2469 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 212:
  if (yyn == 212)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2475 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (3-(3))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 213:
  if (yyn == 213)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2485 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
  PDefinition def = (PDefinition)((yystack.valueAt (2-(2))));
  def.setAccess(access);
  yyval = def;
};
  break;
    

  case 214:
  if (yyn == 214)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2495 of "src/main/bison/cml.y"  */
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
    

  case 215:
  if (yyn == 215)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2519 of "src/main/bison/cml.y"  */
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
    

  case 216:
  if (yyn == 216)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2545 of "src/main/bison/cml.y"  */
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
    

  case 217:
  if (yyn == 217)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2565 of "src/main/bison/cml.y"  */
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
    

  case 218:
  if (yyn == 218)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2590 of "src/main/bison/cml.y"  */
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
    

  case 219:
  if (yyn == 219)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2604 of "src/main/bison/cml.y"  */
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
    

  case 220:
  if (yyn == 220)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2633 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = new Vector<PDefinition>();
  functionList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = functionList;
};
  break;
    

  case 221:
  if (yyn == 221)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2639 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = (List<PDefinition>)((yystack.valueAt (2-(1))));
  functionList.add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = functionList;
};
  break;
    

  case 222:
  if (yyn == 222)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2648 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseImplicitFunctionDefinition(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(4)))),((yystack.valueAt (6-(5)))),((yystack.valueAt (6-(6)))));
};
  break;
    

  case 223:
  if (yyn == 223)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2652 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
  AExplicitFunctionDefinition func = (AExplicitFunctionDefinition)((yystack.valueAt (2-(2))));
  func.setAccess(access);
  yyval = func;
};
  break;
    

  case 224:
  if (yyn == 224)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2662 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExplicitFunctionDefinition(((yystack.valueAt (10-(1)))),((yystack.valueAt (10-(3)))),((yystack.valueAt (10-(4)))),((yystack.valueAt (10-(5)))), 
					   ((yystack.valueAt (10-(7)))),((yystack.valueAt (10-(8)))),((yystack.valueAt (10-(9)))),((yystack.valueAt (10-(10)))));
};
  break;
    

  case 225:
  if (yyn == 225)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2693 of "src/main/bison/cml.y"  */
    {
  List<List<PPattern>> paramsList = new LinkedList<List<PPattern>>();
  paramsList.add((List<PPattern>)((yystack.valueAt (1-(1)))));
  yyval = paramsList;
};
  break;
    

  case 226:
  if (yyn == 226)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2699 of "src/main/bison/cml.y"  */
    {
  List<List<PPattern>> paramsList = (List<List<PPattern>>)((yystack.valueAt (2-(1))));
  paramsList.add((List<PPattern>)((yystack.valueAt (2-(2)))));
  yyval = paramsList;
};
  break;
    

  case 227:
  if (yyn == 227)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2708 of "src/main/bison/cml.y"  */
    {
  yyval =  new LinkedList<PPattern>();
};
  break;
    

  case 228:
  if (yyn == 228)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2712 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 229:
  if (yyn == 229)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2719 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 230:
  if (yyn == 230)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2723 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 231:
  if (yyn == 231)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2727 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 232:
  if (yyn == 232)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2734 of "src/main/bison/cml.y"  */
    {
  yyval = new Vector<APatternListTypePair>();
};
  break;
    

  case 233:
  if (yyn == 233)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2738 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 234:
  if (yyn == 234)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2745 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(1))));
  List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
  pltpl.add(new APatternListTypePair(false, patternList, (PType)((yystack.valueAt (3-(3))))));
  yyval = pltpl;
};
  break;
    

  case 235:
  if (yyn == 235)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2752 of "src/main/bison/cml.y"  */
    {
  List<APatternListTypePair> pltpl = (List<APatternListTypePair>)((yystack.valueAt (5-(1))));
  List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (5-(3))));
  pltpl.add(new APatternListTypePair(false, patternList, (PType)((yystack.valueAt (5-(5))))));
  yyval = pltpl;
};
  break;
    

  case 236:
  if (yyn == 236)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2762 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 237:
  if (yyn == 237)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2766 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 238:
  if (yyn == 238)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2773 of "src/main/bison/cml.y"  */
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
    

  case 239:
  if (yyn == 239)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2788 of "src/main/bison/cml.y"  */
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
    

  case 240:
  if (yyn == 240)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2805 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 241:
  if (yyn == 241)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2806 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 242:
  if (yyn == 242)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2810 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 243:
  if (yyn == 243)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2814 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 244:
  if (yyn == 244)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2815 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 245:
  if (yyn == 245)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2819 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 246:
  if (yyn == 246)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2830 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseMeasure(((yystack.valueAt (2-(2)))));
};
  break;
    

  case 247:
  if (yyn == 247)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2834 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 248:
  if (yyn == 248)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2841 of "src/main/bison/cml.y"  */
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
    

  case 249:
  if (yyn == 249)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2854 of "src/main/bison/cml.y"  */
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
    

  case 250:
  if (yyn == 250)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2875 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (1-(1)))));
  yyval = opDefinitions;
};
  break;
    

  case 251:
  if (yyn == 251)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2881 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)((yystack.valueAt (2-(1))));
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (2-(2)))));
  yyval = opDefinitions;
};
  break;
    

  case 252:
  if (yyn == 252)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2890 of "src/main/bison/cml.y"  */
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
    

  case 253:
  if (yyn == 253)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2917 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExplicitOperationDefinition(((yystack.valueAt (10-(1)))),((yystack.valueAt (10-(2)))),((yystack.valueAt (10-(3)))),((yystack.valueAt (10-(4)))),((yystack.valueAt (10-(5)))),((yystack.valueAt (10-(6)))),((yystack.valueAt (10-(7)))),((yystack.valueAt (10-(8)))),((yystack.valueAt (10-(9)))),((yystack.valueAt (10-(10)))) );
};
  break;
    

  case 254:
  if (yyn == 254)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2924 of "src/main/bison/cml.y"  */
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
    

  case 255:
  if (yyn == 255)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2936 of "src/main/bison/cml.y"  */
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
    

  case 256:
  if (yyn == 256)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2948 of "src/main/bison/cml.y"  */
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
    

  case 257:
  if (yyn == 257)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2960 of "src/main/bison/cml.y"  */
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
    

  case 258:
  if (yyn == 258)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2974 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 259:
  if (yyn == 259)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2978 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 260:
  if (yyn == 260)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2982 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), null, null);
};
  break;
    

  case 261:
  if (yyn == 261)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2988 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 262:
  if (yyn == 262)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2989 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 263:
  if (yyn == 263)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2993 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 264:
  if (yyn == 264)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2998 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)((yystack.valueAt (1-(1)))));
  yyval = infoList;
};
  break;
    

  case 265:
  if (yyn == 265)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3004 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = (Vector<AExternalClause>)((yystack.valueAt (2-(1))));
  infoList.add((AExternalClause)((yystack.valueAt (2-(2)))));
  yyval = infoList;
};
  break;
    

  case 266:
  if (yyn == 266)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3024 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseVarInformation(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))),null);
};
  break;
    

  case 267:
  if (yyn == 267)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3028 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseVarInformation(((yystack.valueAt (4-(1)))),((yystack.valueAt (4-(2)))),((yystack.valueAt (4-(4)))));
};
  break;
    

  case 268:
  if (yyn == 268)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3032 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseMultiVarInformation(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))),null);
};
  break;
    

  case 269:
  if (yyn == 269)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3036 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseMultiVarInformation(((yystack.valueAt (5-(1)))),((yystack.valueAt (5-(3)))),((yystack.valueAt (5-(5)))));
};
  break;
    

  case 270:
  if (yyn == 270)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3043 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.READ);
};
  break;
    

  case 271:
  if (yyn == 271)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3047 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.WRITE);
};
  break;
    

  case 272:
  if (yyn == 272)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3076 of "src/main/bison/cml.y"  */
    {
    AStateParagraphDefinition state = new AStateParagraphDefinition();
    state.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
    yyval  = state;
};
  break;
    

  case 273:
  if (yyn == 273)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3082 of "src/main/bison/cml.y"  */
    {
    AStateParagraphDefinition state = (AStateParagraphDefinition)((yystack.valueAt (2-(2))));
    state.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                         util.extractLastLexLocation(state.getStateDefs())));
    yyval = state;
};
  break;
    

  case 274:
  if (yyn == 274)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3098 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  AStateParagraphDefinition stateDef = new AStateParagraphDefinition();
  stateDef.setStateDefs(defs);
  yyval = stateDef;
};
  break;
    

  case 275:
  if (yyn == 275)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3106 of "src/main/bison/cml.y"  */
    {
  AStateParagraphDefinition stateDef = (AStateParagraphDefinition)((yystack.valueAt (3-(1))));
  stateDef.getStateDefs().add((PDefinition)((yystack.valueAt (3-(3)))));
  yyval = stateDef;
};
  break;
    

  case 276:
  if (yyn == 276)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3115 of "src/main/bison/cml.y"  */
    {
  AAssignmentDefinition adef = (AAssignmentDefinition)((yystack.valueAt (2-(2))));
  adef.setAccess((AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1)))));
  yyval = adef;
};
  break;
    

  case 277:
  if (yyn == 277)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3121 of "src/main/bison/cml.y"  */
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
    

  case 278:
  if (yyn == 278)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3135 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)((yystack.valueAt (1-(1)))));
  yyval = exps;
};
  break;
    

  case 279:
  if (yyn == 279)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3141 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(1))));
  exps.add((PExp)((yystack.valueAt (3-(3)))));
  yyval = exps;
};
  break;
    

  case 280:
  if (yyn == 280)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3184 of "src/main/bison/cml.y"  */
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
    

  case 281:
  if (yyn == 281)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3196 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanConstExp(lit.location, lit);
};
  break;
    

  case 282:
  if (yyn == 282)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3201 of "src/main/bison/cml.y"  */
    {
    yyval = new ANilExp(((LexKeywordToken)((yystack.valueAt (1-(1))))).location);
};
  break;
    

  case 283:
  if (yyn == 283)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3205 of "src/main/bison/cml.y"  */
    {
    LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
    yyval = new ACharLiteralExp(lit.location, lit);
};
  break;
    

  case 284:
  if (yyn == 284)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3210 of "src/main/bison/cml.y"  */
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
    

  case 285:
  if (yyn == 285)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3222 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteLiteralExp(lit.location, lit);
};
  break;
    

  case 286:
  if (yyn == 286)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3228 of "src/main/bison/cml.y"  */
    {
  yyval = new ABracketedExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))), (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 287:
  if (yyn == 287)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3232 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASelfExp(name.location, name);
};
  break;
    

  case 288:
  if (yyn == 288)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3237 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AVariableExp(name.location, name,"");
};
  break;
    

  case 289:
  if (yyn == 289)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3242 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (2-(1)))), true);
  yyval = new AVariableExp(name.location, name,"");
};
  break;
    

  case 290:
  if (yyn == 290)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3247 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpDotIdentifier((PExp)((yystack.valueAt (3-(1)))), util.extractLexIdentifierToken(((yystack.valueAt (3-(3))))));
};
  break;
    

  case 291:
  if (yyn == 291)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3251 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpDotIdentifier((PExp)((yystack.valueAt (3-(1)))), util.extractLexIdentifierToken(((yystack.valueAt (3-(3))))));
};
  break;
    

  case 292:
  if (yyn == 292)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3255 of "src/main/bison/cml.y"  */
    {
  yyval = util.expressionDotHashNumeralToFieldNumberExp(((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3)))));
};
  break;
    

  case 293:
  if (yyn == 293)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3259 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpressionApply(((yystack.valueAt (2-(1)))), new LinkedList<PExp>(), ((yystack.valueAt (2-(2)))));
};
  break;
    

  case 294:
  if (yyn == 294)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3263 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpressionApply(((yystack.valueAt (4-(1)))), ((yystack.valueAt (4-(3)))), ((yystack.valueAt (4-(4)))));
};
  break;
    

  case 295:
  if (yyn == 295)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3267 of "src/main/bison/cml.y"  */
    {
  PExp seq = (PExp)((yystack.valueAt (6-(1))));
  PExp from = (PExp)((yystack.valueAt (6-(3))));
  PExp to = (PExp)((yystack.valueAt (6-(5))));
  yyval = AstFactory.newASubseqExp(seq,from,to);
};
  break;
    

  case 296:
  if (yyn == 296)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3274 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpDotMatchValue(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 297:
  if (yyn == 297)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3279 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpBangIdentifier(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 298:
  if (yyn == 298)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3283 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpBangMatchValue(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 299:
  if (yyn == 299)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3287 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpQuestionPattern(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 300:
  if (yyn == 300)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3295 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpQuestionSetBind(((yystack.valueAt (5-(1)))),((yystack.valueAt (5-(4)))));
};
  break;
    

  case 301:
  if (yyn == 301)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3300 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), exp.getLocation());
  yyval = new ALetDefExp(loc, defs, exp);
};
  break;
    

  case 302:
  if (yyn == 302)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3307 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 303:
  if (yyn == 303)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3311 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 304:
  if (yyn == 304)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3315 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 305:
  if (yyn == 305)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3319 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 306:
  if (yyn == 306)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3324 of "src/main/bison/cml.y"  */
    {
  CmlLexeme forall = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(forall), exp.getLocation());
  yyval = new AForAllExp(loc, binds, exp);
};
  break;
    

  case 307:
  if (yyn == 307)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3332 of "src/main/bison/cml.y"  */
    {
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(exists), exp.getLocation());
  yyval = new AExistsExp(loc, binds, exp);
};
  break;
    

  case 308:
  if (yyn == 308)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3340 of "src/main/bison/cml.y"  */
    {
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(exists), exp.getLocation());
  yyval = new AExists1Exp(loc, bind, exp, null);
};
  break;
    

  case 309:
  if (yyn == 309)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3348 of "src/main/bison/cml.y"  */
    {
  CmlLexeme iota = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  PExp exp = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(iota), exp.getLocation());
  yyval = new AIotaExp(loc, bind, exp);
};
  break;
    

  case 310:
  if (yyn == 310)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3357 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), (CmlLexeme)((yystack.valueAt (2-(2)))));
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation(loc);
  yyval = res;
};
  break;
    

  case 311:
  if (yyn == 311)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3364 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<PExp> members = (List<PExp>)((yystack.valueAt (3-(2))));
  yyval = new ASetEnumSetExp(location, members);
};
  break;
    

  case 312:
  if (yyn == 312)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3371 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASetCompSetExp(loc, exp, binds, null);
};
  break;
    

  case 313:
  if (yyn == 313)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3378 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (7-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new ASetCompSetExp(loc, exp, binds, pred);
};
  break;
    

  case 314:
  if (yyn == 314)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3387 of "src/main/bison/cml.y"  */
    {
  PExp start = (PExp)((yystack.valueAt (5-(2))));
  PExp end = (PExp)((yystack.valueAt (5-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASetRangeSetExp(loc, start, end);
};
  break;
    

  case 315:
  if (yyn == 315)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3395 of "src/main/bison/cml.y"  */
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
    

  case 316:
  if (yyn == 316)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3411 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new ASeqEnumSeqExp(loc, exps);
};
  break;
    

  case 317:
  if (yyn == 317)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3418 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (5-(2))));
  ASetBind binds = (ASetBind)((yystack.valueAt (5-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new ASeqCompSeqExp(loc, exp, binds, null);
};
  break;
    

  case 318:
  if (yyn == 318)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3425 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (7-(2))));
  ASetBind binds = (ASetBind)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new ASeqCompSeqExp(loc, exp, binds, pred);
};
  break;
    

  case 319:
  if (yyn == 319)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3434 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
};
  break;
    

  case 320:
  if (yyn == 320)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3439 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new AMapEnumMapExp(loc, maplets);
};
  break;
    

  case 321:
  if (yyn == 321)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3446 of "src/main/bison/cml.y"  */
    {
  AMapletExp maplet = (AMapletExp)((yystack.valueAt (5-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5)))));
  yyval = new AMapCompMapExp(loc, maplet, binds, null);
};
  break;
    

  case 322:
  if (yyn == 322)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3453 of "src/main/bison/cml.y"  */
    {
  AMapletExp maplet = (AMapletExp)((yystack.valueAt (7-(2))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
  PExp pred = (PExp)((yystack.valueAt (7-(6))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))), (CmlLexeme)((yystack.valueAt (7-(7)))));
  yyval = new AMapCompMapExp(loc, maplet, binds, pred);
};
  break;
    

  case 323:
  if (yyn == 323)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3462 of "src/main/bison/cml.y"  */
    {
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new ATupleExp(loc, exprs);
};
  break;
    

  case 324:
  if (yyn == 324)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3469 of "src/main/bison/cml.y"  */
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
    

  case 325:
  if (yyn == 325)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3483 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> binds = (List<ATypeBind>)((yystack.valueAt (4-(2))));
  PExp body = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), body.getLocation());
  yyval = new ALambdaExp(loc, binds, body, null, null);
};
  break;
    

  case 326:
  if (yyn == 326)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3498 of "src/main/bison/cml.y"  */
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
    

  case 327:
  if (yyn == 327)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3508 of "src/main/bison/cml.y"  */
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
    

  case 328:
  if (yyn == 328)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3518 of "src/main/bison/cml.y"  */
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
    

  case 329:
  if (yyn == 329)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3532 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpressionPrecondition(((yystack.valueAt (4-(1)))),((yystack.valueAt (4-(3)))),((yystack.valueAt (4-(4)))));
};
  break;
    

  case 330:
  if (yyn == 330)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3543 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpressionIsOfBaseClass(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(5)))),((yystack.valueAt (6-(6)))));
};
  break;
    

  case 331:
  if (yyn == 331)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3548 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseAEnumChansetSetExp(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 332:
  if (yyn == 332)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3552 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseACompChansetSetExp(((yystack.valueAt (5-(1)))), ((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))), null, ((yystack.valueAt (5-(5)))));
};
  break;
    

  case 333:
  if (yyn == 333)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3556 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseACompChansetSetExp(((yystack.valueAt (7-(1)))), ((yystack.valueAt (7-(2)))),((yystack.valueAt (7-(4)))), ((yystack.valueAt (7-(6)))), ((yystack.valueAt (7-(7)))));
};
  break;
    

  case 334:
  if (yyn == 334)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3566 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.FALSE, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 335:
  if (yyn == 335)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3570 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.TRUE, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 336:
  if (yyn == 336)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3577 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lex = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = util.extractLexLocation(lex);
  String res = lex.getValue();
  res = res.replace("'", ""); // FIXME: what is this for? is it correct?
  yyval = new LexCharacterToken(util.convertEscapeToChar(res), loc);
};
  break;
    

  case 337:
  if (yyn == 337)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3588 of "src/main/bison/cml.y"  */
    {
  yyval = new LexKeywordToken(VDMToken.NIL, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 338:
  if (yyn == 338)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3595 of "src/main/bison/cml.y"  */
    {
  yyval = util.CmlLexemeToLexIntegerToken(((yystack.valueAt (1-(1)))));
};
  break;
    

  case 339:
  if (yyn == 339)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3599 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = util.extractLexLocation(lexeme);
  BigInteger b = new BigInteger(lexeme.getValue().substring(2), 16);
  yyval = new LexIntegerToken(b.longValue(), loc);
};
  break;
    

  case 340:
  if (yyn == 340)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3606 of "src/main/bison/cml.y"  */
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
    

  case 341:
  if (yyn == 341)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3620 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexStringToken(lit.substring(1, lit.length()-2),
                          util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 342:
  if (yyn == 342)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3629 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexQuoteToken(lit.substring(1, lit.length()-2),
                         util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 343:
  if (yyn == 343)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3640 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 344:
  if (yyn == 344)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3646 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add((PDefinition)((yystack.valueAt (3-(3)))));
  yyval = defs;
};
  break;
    

  case 345:
  if (yyn == 345)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3654 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 346:
  if (yyn == 346)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3655 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 347:
  if (yyn == 347)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3660 of "src/main/bison/cml.y"  */
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
    

  case 348:
  if (yyn == 348)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3692 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  List<PExp> res = new LinkedList<PExp>();
  res.add(exp);
  yyval = res;
};
  break;
    

  case 349:
  if (yyn == 349)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3699 of "src/main/bison/cml.y"  */
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
    

  case 350:
  if (yyn == 350)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3713 of "src/main/bison/cml.y"  */
    {
  ACasesExp alts = new ACasesExp();//(ACasesExp)$alts;
  alts.setCases((List<ACaseAlternative>)((yystack.valueAt (5-(4)))));
  alts.setExpression((PExp)((yystack.valueAt (5-(2)))));
  alts.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5))))));
  yyval = alts;
};
  break;
    

  case 351:
  if (yyn == 351)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3721 of "src/main/bison/cml.y"  */
    {
  ACasesExp alts = new ACasesExp();//(ACasesExp)$alts;
  alts.setCases((List<ACaseAlternative>)((yystack.valueAt (9-(4)))));
  alts.setExpression((PExp)((yystack.valueAt (9-(2)))));
  alts.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))), (CmlLexeme)((yystack.valueAt (9-(9))))));
  alts.setOthers((PExp)((yystack.valueAt (9-(8)))));
  yyval = alts;
};
  break;
    

  case 352:
  if (yyn == 352)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3733 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1)))); 
  //ACasesExp casesExp = new ACasesExp();
  //casesExp.getCases().add((ACaseAlternative)$alt);
  //$$ = casesExp;
};
  break;
    

  case 353:
  if (yyn == 353)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3740 of "src/main/bison/cml.y"  */
    {
  List<ACaseAlternative> alts = (List<ACaseAlternative>)((yystack.valueAt (3-(1))));
  alts.addAll((List<ACaseAlternative>)((yystack.valueAt (3-(3)))));
  yyval = alts;
  //ACasesExp casesExp = (ACasesExp)$alts;
  //casesExp.getCases().add((ACaseAlternative)$alt);
  //$$ = casesExp;
};
  break;
    

  case 354:
  if (yyn == 354)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3754 of "src/main/bison/cml.y"  */
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
    

  case 355:
  if (yyn == 355)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3774 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryPlusUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 356:
  if (yyn == 356)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3780 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryMinusUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 357:
  if (yyn == 357)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3786 of "src/main/bison/cml.y"  */
    {
  yyval = new AAbsoluteUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 358:
  if (yyn == 358)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3792 of "src/main/bison/cml.y"  */
    {
  yyval = new AFloorUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                             ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                          (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 359:
  if (yyn == 359)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3798 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                           ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                        (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 360:
  if (yyn == 360)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3804 of "src/main/bison/cml.y"  */
    {
  yyval = new ACardinalityUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                   ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                                (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 361:
  if (yyn == 361)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3810 of "src/main/bison/cml.y"  */
    {
  yyval = new APowerSetUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 362:
  if (yyn == 362)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3816 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistUnionUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 363:
  if (yyn == 363)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3822 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistIntersectUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                     ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                                  (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 364:
  if (yyn == 364)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3828 of "src/main/bison/cml.y"  */
    {
  yyval = new AHeadUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                         (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 365:
  if (yyn == 365)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3834 of "src/main/bison/cml.y"  */
    {
  yyval = new ATailUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                         (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 366:
  if (yyn == 366)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3840 of "src/main/bison/cml.y"  */
    {
  yyval = new ALenUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                           ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                        (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 367:
  if (yyn == 367)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3846 of "src/main/bison/cml.y"  */
    {
  yyval = new AElementsUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 368:
  if (yyn == 368)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3852 of "src/main/bison/cml.y"  */
    {
  yyval = new AIndicesUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                               ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                            (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 369:
  if (yyn == 369)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3858 of "src/main/bison/cml.y"  */
    {
  yyval = new AReverseUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                               ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                            (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 370:
  if (yyn == 370)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3864 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistConcatUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 371:
  if (yyn == 371)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3870 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapDomainUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 372:
  if (yyn == 372)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3876 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapRangeUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 373:
  if (yyn == 373)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3882 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistMergeUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 374:
  if (yyn == 374)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3888 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapInverseUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 375:
  if (yyn == 375)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3897 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 376:
  if (yyn == 376)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3905 of "src/main/bison/cml.y"  */
    {
  yyval = new ATimesNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 377:
  if (yyn == 377)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3913 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubstractNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                         ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                      (PExp)((yystack.valueAt (3-(1)))),
                                      util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                      (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 378:
  if (yyn == 378)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3921 of "src/main/bison/cml.y"  */
    {
  yyval = new ADivideNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                      ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                   (PExp)((yystack.valueAt (3-(1)))),
                                   util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                   (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 379:
  if (yyn == 379)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3929 of "src/main/bison/cml.y"  */
    {
  yyval = new ADivNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 380:
  if (yyn == 380)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3937 of "src/main/bison/cml.y"  */
    {
  yyval = new ARemNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 381:
  if (yyn == 381)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3945 of "src/main/bison/cml.y"  */
    {
  yyval = new AModNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 382:
  if (yyn == 382)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3953 of "src/main/bison/cml.y"  */
    {
  yyval = new ALessNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 383:
  if (yyn == 383)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3961 of "src/main/bison/cml.y"  */
    {
  yyval = new ALessEqualNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                         ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                      (PExp)((yystack.valueAt (3-(1)))),
                                      util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                      (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 384:
  if (yyn == 384)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3969 of "src/main/bison/cml.y"  */
    {
  yyval = new AGreaterNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                       ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                    (PExp)((yystack.valueAt (3-(1)))),
                                    util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                    (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 385:
  if (yyn == 385)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3977 of "src/main/bison/cml.y"  */
    {
  yyval = new AGreaterEqualNumericBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                            ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                         (PExp)((yystack.valueAt (3-(1)))),
                                         util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                         (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 386:
  if (yyn == 386)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3985 of "src/main/bison/cml.y"  */
    {
  yyval = new AEqualsBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                               ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                            (PExp)((yystack.valueAt (3-(1)))),
                            util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                            (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 387:
  if (yyn == 387)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3993 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotEqualBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 388:
  if (yyn == 388)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4001 of "src/main/bison/cml.y"  */
    {
  yyval = new AOrBooleanBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                  ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                               (PExp)((yystack.valueAt (3-(1)))),
                               util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                               (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 389:
  if (yyn == 389)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4009 of "src/main/bison/cml.y"  */
    {
  yyval = new AAndBooleanBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                   ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                (PExp)((yystack.valueAt (3-(1)))),
                                util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 390:
  if (yyn == 390)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4017 of "src/main/bison/cml.y"  */
    {
  yyval = new AImpliesBooleanBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                       ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                    (PExp)((yystack.valueAt (3-(1)))),
                                    util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                    (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 391:
  if (yyn == 391)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4025 of "src/main/bison/cml.y"  */
    {
  yyval = new AEquivalentBooleanBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                          ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                       (PExp)((yystack.valueAt (3-(1)))),
                                       util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                       (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 392:
  if (yyn == 392)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4033 of "src/main/bison/cml.y"  */
    {
  yyval = new AInSetBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                              ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                           (PExp)((yystack.valueAt (3-(1)))),
                           util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                           (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 393:
  if (yyn == 393)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4041 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotInSetBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 394:
  if (yyn == 394)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4049 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubsetBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                               ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                            (PExp)((yystack.valueAt (3-(1)))),
                            util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                            (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 395:
  if (yyn == 395)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4057 of "src/main/bison/cml.y"  */
    {
  yyval = new AProperSubsetBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 396:
  if (yyn == 396)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4065 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetUnionBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 397:
  if (yyn == 397)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4073 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetDifferenceBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                      ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                   (PExp)((yystack.valueAt (3-(1)))),
                                   util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                   (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 398:
  if (yyn == 398)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4081 of "src/main/bison/cml.y"  */
    {
  yyval = new ASetIntersectBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                     ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                  (PExp)((yystack.valueAt (3-(1)))),
                                  util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                  (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 399:
  if (yyn == 399)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4089 of "src/main/bison/cml.y"  */
    {
  yyval = new ASeqConcatBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                  ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                               (PExp)((yystack.valueAt (3-(1)))),
                               util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                               (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 400:
  if (yyn == 400)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4097 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 401:
  if (yyn == 401)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4105 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapUnionBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 402:
  if (yyn == 402)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4113 of "src/main/bison/cml.y"  */
    {
  yyval = new ADomainResToBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 403:
  if (yyn == 403)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4121 of "src/main/bison/cml.y"  */
    {
  yyval = new ADomainResByBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                    ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                                 (PExp)((yystack.valueAt (3-(1)))),
                                 util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                                 (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 404:
  if (yyn == 404)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4129 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 405:
  if (yyn == 405)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4137 of "src/main/bison/cml.y"  */
    {
  yyval = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 406:
  if (yyn == 406)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4145 of "src/main/bison/cml.y"  */
    {
  yyval = new ACompBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                             ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                          (PExp)((yystack.valueAt (3-(1)))),
                          util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                          (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 407:
  if (yyn == 407)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4153 of "src/main/bison/cml.y"  */
    {
  yyval = new AStarStarBinaryExp(util.extractLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),
                                                 ((PExp)((yystack.valueAt (3-(3))))).getLocation()),
                              (PExp)((yystack.valueAt (3-(1)))),
                              util.extractLexToken((CmlLexeme)((yystack.valueAt (3-(2))))),
                              (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 408:
  if (yyn == 408)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4164 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 409:
  if (yyn == 409)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4170 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(1))));
  maplets.add((AMapletExp)((yystack.valueAt (3-(3)))));
  yyval = maplets;
};
  break;
    

  case 410:
  if (yyn == 410)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4178 of "src/main/bison/cml.y"  */
    {
  PExp dom = (PExp)((yystack.valueAt (3-(1))));
  PExp rng = (PExp)((yystack.valueAt (3-(3))));
  yyval = new AMapletExp(util.extractLexLocation(dom.getLocation(),
                                         rng.getLocation()),
                      dom,
                      rng);
};
  break;
    

  case 411:
  if (yyn == 411)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4190 of "src/main/bison/cml.y"  */
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
    

  case 412:
  if (yyn == 412)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4203 of "src/main/bison/cml.y"  */
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
    

  case 413:
  if (yyn == 413)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4219 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (1-(1)))));
  yyval = assignmentDefs;
};
  break;
    

  case 414:
  if (yyn == 414)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4225 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)((yystack.valueAt (3-(1))));
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (3-(3)))));
  yyval = assignmentDefs;
};
  break;
    

  case 415:
  if (yyn == 415)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4234 of "src/main/bison/cml.y"  */
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
    

  case 416:
  if (yyn == 416)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4251 of "src/main/bison/cml.y"  */
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
    

  case 417:
  if (yyn == 417)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4267 of "src/main/bison/cml.y"  */
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
    

  case 418:
  if (yyn == 418)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4290 of "src/main/bison/cml.y"  */
    {
  List<ASingleGeneralAssignmentStatementAction> assigns =
    new LinkedList<ASingleGeneralAssignmentStatementAction>();
  assigns.add((ASingleGeneralAssignmentStatementAction)((yystack.valueAt (1-(1)))));
  yyval = assigns;
};
  break;
    

  case 419:
  if (yyn == 419)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4297 of "src/main/bison/cml.y"  */
    {
  List<ASingleGeneralAssignmentStatementAction> assigns =
    (List<ASingleGeneralAssignmentStatementAction>)((yystack.valueAt (3-(1))));
  assigns.add((ASingleGeneralAssignmentStatementAction)((yystack.valueAt (3-(3)))));
  yyval = assigns;
};
  break;
    

  case 420:
  if (yyn == 420)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4315 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierToAssignmentStm(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 421:
  if (yyn == 421)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4322 of "src/main/bison/cml.y"  */
    {
  yyval = new AIfStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
                                                 ((PAction)((yystack.valueAt (7-(7))))).getLocation()),
                              (PExp)((yystack.valueAt (7-(2)))),
                              (PAction)((yystack.valueAt (7-(4)))),
                              (List<? extends AElseIfStatementAction>)((yystack.valueAt (7-(5)))),
                              (PAction)((yystack.valueAt (7-(7)))));
};
  break;
    

  case 422:
  if (yyn == 422)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4331 of "src/main/bison/cml.y"  */
    {
  yyval = new AIfStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
                                                 ((PAction)((yystack.valueAt (6-(6))))).getLocation()),
                              (PExp)((yystack.valueAt (6-(2)))),
                              (PAction)((yystack.valueAt (6-(4)))),
                              null,
                              (PAction)((yystack.valueAt (6-(6)))));
};
  break;
    

  case 423:
  if (yyn == 423)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4343 of "src/main/bison/cml.y"  */
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
    

  case 424:
  if (yyn == 424)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4354 of "src/main/bison/cml.y"  */
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
    

  case 425:
  if (yyn == 425)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4368 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (5-(4))));
  cases.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))), (CmlLexeme)((yystack.valueAt (5-(5))))));
  cases.setExp((PExp)((yystack.valueAt (5-(2)))));
  yyval = cases;
};
  break;
    

  case 426:
  if (yyn == 426)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4375 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (9-(4))));
  cases.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (9-(1)))), (CmlLexeme)((yystack.valueAt (9-(9))))));
  cases.setExp((PExp)((yystack.valueAt (9-(2)))));
  cases.setOthers((PAction)((yystack.valueAt (9-(8)))));
  yyval = cases;
};
  break;
    

  case 427:
  if (yyn == 427)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4386 of "src/main/bison/cml.y"  */
    {
  List<ACaseAlternativeAction> casesList = new LinkedList<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)((yystack.valueAt (1-(1)))));
  yyval = new ACasesStatementAction(null, null, casesList, null);
};
  break;
    

  case 428:
  if (yyn == 428)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4392 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (3-(1))));
  cases.getCases().add((ACaseAlternativeAction)((yystack.valueAt (3-(3)))));
  yyval = cases;
};
  break;
    

  case 429:
  if (yyn == 429)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4403 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PAction action = (PAction)((yystack.valueAt (3-(3))));
  yyval = new ACaseAlternativeAction(util.combineLexLocation(util.extractFirstLexLocation(patterns),
                                                     action.getLocation()),
                                  patterns,
                                  action);
};
  break;
    

  case 430:
  if (yyn == 430)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4415 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseImplicitOperationBody(((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(2)))), ((yystack.valueAt (3-(3)))));
};
  break;
    

  case 431:
  if (yyn == 431)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4421 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 432:
  if (yyn == 432)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4422 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 433:
  if (yyn == 433)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4427 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 434:
  if (yyn == 434)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4431 of "src/main/bison/cml.y"  */
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
    

  case 435:
  if (yyn == 435)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4448 of "src/main/bison/cml.y"  */
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
    

  case 436:
  if (yyn == 436)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4459 of "src/main/bison/cml.y"  */
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
    

  case 437:
  if (yyn == 437)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4473 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)((yystack.valueAt (1-(1)))));
  yyval = patterns;
};
  break;
    

  case 438:
  if (yyn == 438)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4479 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  patterns.add((PPattern)((yystack.valueAt (3-(3)))));
  yyval = patterns;
};
  break;
    

  case 439:
  if (yyn == 439)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4488 of "src/main/bison/cml.y"  */
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
    

  case 440:
  if (yyn == 440)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4502 of "src/main/bison/cml.y"  */
    {
  yyval = new AIgnorePattern(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),
                          new LinkedList<PDefinition>(),
                          true);
};
  break;
    

  case 441:
  if (yyn == 441)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4512 of "src/main/bison/cml.y"  */
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
    

  case 442:
  if (yyn == 442)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4522 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 443:
  if (yyn == 443)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4527 of "src/main/bison/cml.y"  */
    {
  LexKeywordToken lit = (LexKeywordToken)((yystack.valueAt (1-(1))));
  yyval = new ANilPattern(lit.location, new LinkedList<PDefinition>(), true);
};
  break;
    

  case 444:
  if (yyn == 444)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4532 of "src/main/bison/cml.y"  */
    {
  LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
  yyval = new ACharacterPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 445:
  if (yyn == 445)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4537 of "src/main/bison/cml.y"  */
    {
  LexStringToken lit = (LexStringToken)((yystack.valueAt (1-(1))));
  yyval = new AStringPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 446:
  if (yyn == 446)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4542 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuotePattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 447:
  if (yyn == 447)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4547 of "src/main/bison/cml.y"  */
    {
  yyval = new AExpressionPattern(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
                                                 (CmlLexeme)((yystack.valueAt (3-(3))))),
                              new LinkedList<PDefinition>(),
                              false,
                              (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 448:
  if (yyn == 448)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4557 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 449:
  if (yyn == 449)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4558 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 450:
  if (yyn == 450)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4563 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation location = util.extractLexLocation(pattern.getLocation(),
                                            exp.getLocation());
  yyval = new ASetBind(location, pattern, exp);
};
  break;
    

  case 451:
  if (yyn == 451)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4574 of "src/main/bison/cml.y"  */
    {
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation location = util.extractLexLocation(pattern.getLocation(),
                                            type.getLocation());
  yyval = new ATypeBind(location, pattern, type);
};
  break;
    

  case 452:
  if (yyn == 452)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4585 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)((yystack.valueAt (1-(1)))));
  yyval = binds;
};
  break;
    

  case 453:
  if (yyn == 453)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4591 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (3-(1))));
  binds.add((PMultipleBind)((yystack.valueAt (3-(3)))));
  yyval = binds;
};
  break;
    

  case 454:
  if (yyn == 454)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4599 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 455:
  if (yyn == 455)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4600 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 456:
  if (yyn == 456)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4605 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PExp exp = (PExp)((yystack.valueAt (3-(3))));
  LexLocation loc = util.extractLexLocation(util.extractFirstLexLocation(patterns),
                                       exp.getLocation());
  yyval = new ASetMultipleBind(loc, patterns, exp);
};
  break;
    

  case 457:
  if (yyn == 457)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4616 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  PType type = (PType)((yystack.valueAt (3-(3))));
  LexLocation loc = util.extractLexLocation(util.extractFirstLexLocation(patterns),
                                       type.getLocation());
  yyval = new ATypeMultipleBind(loc, patterns, type);
};
  break;
    

  case 458:
  if (yyn == 458)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4627 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> list = new LinkedList<ATypeBind>();
  ATypeBind bind = (ATypeBind)((yystack.valueAt (1-(1))));
  list.add(bind);
  yyval = list;
};
  break;
    

  case 459:
  if (yyn == 459)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4634 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> list = (List<ATypeBind>)((yystack.valueAt (3-(1))));
  ATypeBind bind = (ATypeBind)((yystack.valueAt (3-(3))));
  list.add(bind);
  yyval = list;
};
  break;
    



/* Line 374 of cmlskeleton.java.m4  */
/* Line 7511 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -657;
  public static final short yypact_[] =
  {
        42,  1005,  -127,  -114,  1005,   437,   -61,    27,   226,    42,
    -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,
    -657,  -657,  -657,   180,  -657,   197,   -14,  -127,  -657,  -657,
     341,   243,  -657,   207,  1005,  -657,  1050,   271,  -657,    20,
     407,  -657,  -657,  1005,   351,   277,   499,  -657,  2715,  -114,
      33,  -657,  -657,  2715,   441,   588,  -657,  -657,  -657,    17,
    -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,
    -657,  -657,  -657,    26,  -657,   858,   307,   310,    25,  -657,
     499,  1242,   -14,  -657,   499,    94,   499,   316,   499,   499,
     499,   499,   499,  -657,  -657,  -657,  -657,  -657,  -657,  -657,
    -657,  -657,   526,   -39,  -657,  -657,  -657,  -657,  -657,  -657,
    2715,  2715,  2307,  1702,  1560,  2715,  2715,  2715,  2715,  2715,
    2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,
    2715,  2715,  2715,  2715,  2715,  2715,  2715,   496,   513,  1702,
     212,   519,   543,  1702,  1702,  1702,  2715,   550,  -657,   409,
    -657, 11167,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,
    -657,  -657,  -657,  1702,  -657,   499,   388,  5300,  1702,  1702,
    -657,  2715,   499,  2715,   499,  -657,   663,   616,   455,   455,
     455,   277,   455,   455,  2715,   852,   363,  3227,    -3,  -657,
     994,   -19,   526,   151,  -657,   808,   499,   499,   888,   610,
    1038,  1216,   640,   640,   640,   499,   499,   499,   499,   467,
     506,  5422,   213,  5545,  -657,    66,  4565,   172,   669,  -657,
    -657,   -57,  -657,  -657,   691,  -657,  -657,    32,  -657,  -657,
     -28,  -657,  5667,  5789,  1273,  1273,  1273,  1273, 11618,  1273,
    1273,  1273,  1273,  1273,  1273,  1273,  1273,  1273,  1273,  1273,
    1273,  1273,  1273,   926,  2715,  2715,   563,  -657,    87,  2715,
     696,  2715,   536,  -657,   399,   127,  -657,  -657,  -657,   133,
     704,   -20,  5912,  2715,  -657,  2715,  2715,  -657,  2715,  2715,
    2715,   395,   628,   549,  2715,  2715,  2715,  2715,  2715,  2715,
    2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,
    2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,  2715,
    2715,  2715,  2715,   707,   824,    77,   457,   526,   569,   605,
     -12,  -657,   612,    89, 11167,   613, 11167,   740,  1005,   858,
     148,   591,   663,  -657,  -657,  -657,  -657,  -657,   600,   480,
     408,  -657,  -657,   436,   475,    -1,   490,   533,  6034,   596,
     607,  2537,  1436,  -657,  -657,  2715,  -657,   378,   378,   378,
    2715,   378,   378,  2715,  2715,  2715,   378,   378,  2715,  2715,
    2715,  -657,   378,  -657,  1702,  -657,   499,   499,  -657,  -657,
    -657,   526,   526,  -657,  1242,   499,   499,   526,   640,   640,
     526,  -657,  -657,  -657,  -657,  2715,  1702,  -657,  1702,  2715,
    2715,  -657,  2715,  1702,  2715,   499,  2715,   499,  2715,  1560,
    2715,  1702,   126, 11167,   183,  2715,  1702,  6156,  2715,   190,
     279,  2715,  1702,   499,  2715,  1702,  2715,  2715,  -657,  1702,
    6278, 11668,   204,  6400,  1041, 11741, 11741,  -657,  -657,  -657,
    -657,  1041,  1041,  1041,  1041, 11668, 11668, 11668, 11668, 11668,
   11491, 11541, 11411, 11289, 11668, 11668, 11668, 11668,  3409,  3409,
     926,  3653,  3531,  3531,   926,   926,  1273,  1273,   389,   284,
    1702,  -657,  -657,  -657,  -657,  1702,   499,   706,   499,  2715,
     608,   743,  -657,  1702,  -657,  2715,  2715,   614,  1005,  -657,
    -657,  2715,   623,   764,  -657,  -657,  -657,   663,  2715,   455,
     378,   455,   378,   378,   378,   579,   378,   455,   793,   627,
     797,   635,   455,   455,   455,  2804,  2443,  -657,  -657,  -657,
    -657,  2715,   455,   455,  1560,  2715,  2715,   799,   736,  2715,
    2715,  2715,  2715,  1111,  2715,  2042,    37,  -657,  -657,  -657,
    2537,  -657,   263,   751,   273,   647,  3247,  4198,  1831,   390,
    6522,   629,  6644,  4320,   576,   324, 11167, 11167, 11167,  3227,
     771,   526,   526,   929,   640,   640, 11167,   701,   289,    30,
    6768, 11167,  6890,  -657,    78, 11167,   526, 11289, 11167,  -657,
    4687,   250,  -657,    28,  -657,  -657, 11289,  -657,   499,  7012,
    -657,  2715, 11167,  -657,   526, 11289,  -657, 11289, 11289,     7,
    -657,  -657,  2715,   806,   553,   526,  1702,  -657,   446,  -657,
     526, 11167,   671,  2715,  -657,   812, 11167, 11167,   265,  -657,
   11167,   682,  -657,   156,   659, 11167,  -657,  -657,  -657,  -657,
    -657,  -657,  3163,  2715,  -657,  -657,   559,  2715,   828,  2856,
     830,   571,   597,   619,   623,   277,   277,   277,   -14,  2995,
     626,  -657,     3,   292,   798,  -657,  7134,   825, 11167,   636,
     648,   -26,  4809,    -9,  7256,   536,  2715,  -657,  7378,  7500,
     143,  7622,    -4,  1702,   838,   322,   792,  3465,  2537,  2537,
    2537,  2715,  2537,  2537,  2715,  2715,  2715,  2537,  2537,  2715,
    2715,  2715,  -657,  -657,  2715,  -657,  2579,  2551,  -657,  2715,
     378,   378,  -657,  2715,  2715,   378,  2715,  -657,  2715,  -657,
    2715,  -657,  -657,  2715,  -657,  2715,  2715,  -657,  1483,  -657,
    2715,   948,  -657,  7744,  2715,  -657,  7866,  -657,   499,   267,
    2171,  -657,   499, 11167,  -657,  1594,   388,   499,  -657,  -657,
     836,  7988,   378, 11167,  2715,  -657,  3195,     4,  2856,  2537,
    2537,   834,   144,  -657,  -657,  -657,  -657,  -657,  2537,   604,
    2537,  -657,  -657,   292,   720,   536,   743,     1,  -657,   849,
    2537,  2537,  2537,  2537,  2715,  -657,  1702,   424,    96,  -657,
    8110,   455,   455,   853,  -657,   755,  2715,  2715,  2715,  2537,
     796,   305,  1445,  4076,  1070,   765,  4932,  8233,  4442,   575,
       2, 11167, 11167, 11167,   276,  2715, 11167,  -657,  8355,   324,
    1831,  8477,  8600,   576, 11167,  8722,  8846,  8970,  9092, 11289,
     847,  -657, 11167,  -657,  -657,  9214,  -657,   526,  -657,  -657,
    -657,   798, 11167,   526,   264,   525,   698,   821,   742,   500,
    2715,   890,  -657, 11167,  2537,  -657,  -657,  -657,  2715,  2537,
     623,  3079,  -657,  3111,   720,   536,   219,  -657,  2537,  2537,
    2715,  -657,  3195,  3195,  3059,  9336,   280,  -657,   173,  2715,
    -657,   536,  -657,   651,   662,  2537,  2715,  9458,  3587,  3709,
    3195,  2715,  2715,  2537,  2715,  2537,  2715,  2537,  -657, 11167,
     378,  2715,  1702,  -657,  -657,  -657,  2715,  2715,  -657,   743,
    1890,  1937,   706,   798,  2715,  2715,  9580,   453,  -657,  3195,
    9702,  3143,  -657,   869,   878,   325,   499,  -657,   765,  5055,
    2715,  2537,   302,  2537,  1734,  -657,  2537,  -657,   876,  2537,
    3195,   287,  3831,  2715,  2537,  2537,  9824,  9946,     2,  5178,
    1070, 10068,   575,  1831, 11167,   134,  4687,  2935,   846,  -657,
      94,   526,    94,   526,   855,   743, 11167, 11167,  -657,  2537,
    -657,  2715,  2715,   499,   526,  2537,  2715, 10190,  3195,  2715,
    2537,  3195,   875,  -657,  3195,  2715,  -657,  -657,  2537,  2537,
    3343,  3195,  3195,  2715,  2537,  2715,  2537,  2537,  2715,  -657,
    -657,  -657,   536,  -657,  2259,  -657,  -657,   293,   296,   526,
    -657, 10312,  2537, 10434,  3195,  2537, 10556,  3195,  3195,  2537,
    2715, 10679,  1070, 10801,  1070,  -657, 10923,   -39,  -657,  -657,
    3195,   798,  -657,  -657,  2537,  3195,  2537,  2959,  2537,  3195,
    3953,  2715,  2537,  -657,   743,  -657,  3195,  -657,  -657,  2537,
   11045,  1070,  -657,  3195,  2537,  1070
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,   158,   132,   142,   218,   209,     0,     0,     0,     2,
       3,     5,     6,     7,     8,     9,   147,   148,   149,   167,
     168,   169,   170,   159,   161,     0,   136,   133,   134,   137,
       0,   143,   144,     0,   219,   220,     0,   210,   211,     0,
       0,     1,     4,   160,     0,     0,     0,   135,     0,     0,
       0,   223,   221,     0,     0,     0,   338,   341,   340,     0,
     335,   334,   336,   337,   342,   339,   213,   442,   444,   443,
     441,   445,   446,     0,   433,   171,     0,     0,     0,   162,
       0,     0,     0,   141,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   186,   189,   190,   191,   192,   193,   194,
     195,   196,   140,   185,   173,   184,   197,   198,   174,   315,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   319,   288,
     287,   146,   281,   283,   282,   280,   284,   285,   302,   303,
     304,   305,   145,     0,   232,     0,     0,     0,     0,     0,
     435,     0,     0,     0,     0,   212,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    31,    12,     0,   138,
     163,   186,   205,   165,   203,     0,     0,     0,     0,     0,
       0,     0,   180,   181,   179,     0,     0,     0,     0,     0,
       0,     0,     0,   278,   310,     0,   278,     0,   408,   440,
     439,     0,   432,   431,     0,   448,   449,     0,   345,   346,
       0,   343,     0,     0,   355,   356,   357,   358,   359,   360,
     361,   362,   363,   364,   365,   366,   367,   368,   369,   370,
     371,   372,   373,   374,     0,     0,     0,   458,     0,     0,
       0,     0,     0,   437,     0,     0,   452,   454,   455,     0,
       0,     0,   278,     0,   289,     0,     0,   293,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   184,     0,
     241,   447,     0,     0,   214,     0,   216,     0,   248,   171,
     272,     0,   150,   152,   153,   154,   155,   156,     0,     0,
       0,    43,    42,     0,     0,     0,     0,     0,     0,    52,
       0,     0,     0,    48,    50,     0,    32,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    34,     0,   139,     0,   164,     0,     0,   204,   166,
     172,   200,   202,   178,     0,     0,     0,   199,   176,   177,
     201,   187,   188,   286,   316,     0,     0,   311,     0,     0,
       0,   320,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   278,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   331,     0,
       0,   386,     0,   278,   376,   375,   377,   290,   296,   292,
     291,   379,   378,   380,   381,   382,   383,   384,   385,   387,
     388,   389,   390,   391,   392,   393,   394,   395,   396,   397,
     398,   399,   400,   401,   402,   403,   404,   405,   406,   407,
       0,   299,   297,   298,   233,     0,     0,     0,     0,     0,
       0,     0,   240,     0,   436,     0,     0,     0,   249,   250,
     157,     0,     0,   273,   274,    10,   151,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    53,
       0,    51,     0,     0,     0,     0,   262,    60,    61,    62,
      63,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    88,   105,   102,   113,
       0,    49,     0,    31,    18,    17,    22,     0,    21,    16,
       0,     0,     0,     0,    23,    25,    29,    28,    27,    13,
       0,   207,   206,     0,   183,   182,   279,     0,     0,     0,
       0,   410,     0,   409,     0,   450,   451,   309,   301,   344,
       0,     0,   352,     0,   323,   324,   325,   459,     0,     0,
     329,     0,   456,   438,   457,   306,   453,   307,   308,     0,
     326,   294,     0,     0,     0,   234,     0,   227,     0,   225,
     238,   242,     0,     0,   222,   438,   215,   217,     0,   251,
     277,     0,   276,   171,     0,    46,    47,    37,    45,    44,
      36,    41,     0,     0,    40,    35,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   186,     0,
       0,   122,     0,     0,   241,   261,     0,     0,    64,     0,
       0,     0,     0,     0,     0,     0,     0,   110,     0,     0,
       0,     0,     0,     0,   439,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    14,    78,     0,   107,     0,     0,    33,     0,
       0,     0,   127,     0,     0,     0,     0,   175,     0,   317,
       0,   312,   314,     0,   321,     0,     0,   347,     0,   350,
       0,     0,   327,     0,     0,   332,     0,   300,     0,     0,
       0,   226,     0,   245,   434,     0,   236,     0,   275,    11,
       0,     0,     0,    56,     0,    54,    58,     0,     0,     0,
       0,     0,     0,   413,   126,   125,   124,    65,     0,     0,
       0,   270,   271,   263,   264,     0,     0,     0,   109,     0,
       0,     0,     0,     0,     0,   103,     0,     0,     0,   418,
       0,     0,     0,     0,   104,     0,     0,     0,     0,     0,
      70,    69,    81,     0,    83,    68,     0,     0,     0,    71,
      73,    77,    76,    75,     0,     0,   420,    15,     0,    26,
      19,     0,   130,    24,   208,     0,     0,     0,     0,   348,
       0,   353,   354,   328,   330,     0,   295,   235,   228,   230,
     231,   241,   229,   239,     0,     0,     0,   262,   237,   415,
       0,     0,    38,    57,     0,    55,    94,    93,     0,     0,
       0,     0,   123,     0,   265,     0,   266,   430,     0,     0,
       0,    92,   100,   411,     0,     0,     0,   427,     0,     0,
     106,     0,   111,     0,     0,     0,     0,     0,     0,     0,
     119,     0,     0,     0,     0,     0,     0,     0,   108,   112,
       0,     0,     0,   318,   313,   322,     0,     0,   333,   244,
       0,     0,     0,   241,     0,     0,     0,    31,    39,    59,
       0,     0,   414,    89,     0,   268,     0,    66,    67,     0,
       0,     0,     0,     0,     0,   425,     0,   419,     0,     0,
     120,     0,     0,     0,     0,     0,     0,     0,    74,     0,
      87,     0,    72,    20,   131,     0,     0,     0,   247,   243,
     257,   255,   256,   254,     0,     0,   417,   416,    30,     0,
     101,     0,     0,     0,   267,     0,     0,     0,   422,     0,
       0,   412,     0,   428,   429,     0,    95,    79,     0,     0,
       0,   115,   114,     0,     0,     0,     0,     0,     0,   128,
     349,   351,     0,   224,     0,   252,    96,     0,     0,   269,
      99,     0,     0,     0,   421,     0,     0,   121,   116,     0,
       0,     0,    85,     0,    82,    80,     0,   246,   259,   260,
     258,   241,    90,    91,     0,   423,     0,     0,     0,   117,
       0,     0,     0,   129,   244,    98,   424,   426,    97,     0,
       0,    86,   253,   118,     0,    84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -657,  -657,  -657,   894,  -657,  -657,  -207,   -67,   282,  -657,
     558,  -657,  -657,   163,  1178,  -657,  -657,   158,   -44,  -657,
    -657,  -657,   893,   -64,  1069,  -657,  -657,   879,  -657,  -298,
    -164,   124,  -657,   891,   105,   791,    13,   802,  -140,  -657,
    -657,   554,  -191,   754,   497,  -657,   877,   897,   645,  -657,
     910,   921,  -657,  -601,  -657,   340,  -657,  -657,   223,  -635,
    -657,   -74,  -480,  -657,  -657,  -657,  -316,  -657,  -657,   136,
    -657,  -657,   211,  -657,  -657,  -657,   357,    16,   -48,   905,
     991,  1077,  1229,  1422,   617,   458,   572,  -657,    40,  -657,
    -657,   272,  -657,  -657,  -657,   587,   463,  -657,  -636,  -657,
    -656,  -657,  -657,  -657,  -657,    76,  -657,  -109,   -25,  -145,
    -657,    74,  -142,  -348,  -133,  -129,   589,  -657,  -657,  -657
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   187,   636,   341,   352,
     353,   511,   509,   745,   746,   931,   650,   651,   693,   551,
      13,    27,    28,   747,   342,    14,    31,    32,    15,   331,
     332,   333,    23,    24,   487,   192,   536,   104,   105,   106,
     107,   193,   194,   375,   334,    37,    38,   228,   335,    34,
      35,   229,   608,   609,   831,   166,   315,   837,   320,   481,
     482,   948,   949,   993,   336,   488,   489,   836,  1021,   654,
     655,   763,   764,   765,   337,   493,   494,   212,   413,   152,
     153,   154,   155,   156,   157,   230,   231,   158,   717,   159,
     581,   582,   160,   161,   217,   218,   663,   752,   622,   778,
     537,   538,   922,   539,   866,   867,   657,   263,   222,   264,
     223,    74,   224,   225,   226,   265,   266,   267,   268,   258
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "151,614,378,270,221,167,257,731,753,779,372,73,504,490,188,269,760,844,316,766,724,354,774,322,323,318,376,774,858,171,256,408,76,771,496,683,221,479,173,178,179,180,181,710,171,1,2,3,568,4,163,209,164,26,694,720,695,182,183,103,711,404,211,213,216,210,30,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,73,5,713,405,103,103,474,397,103,272,103,415,103,103,103,103,103,25,484,714,33,36,340,343,344,870,346,347,345,409,39,409,196,603,324,16,326,395,209,215,871,377,428,45,16,480,46,348,696,197,33,424,210,859,371,584,364,426,988,25,685,544,545,546,425,548,549,783,849,725,554,555,689,271,690,775,559,172,184,84,784,85,86,619,691,422,174,425,82,103,82,36,407,165,82,82,103,87,103,354,77,88,89,90,91,92,491,899,185,374,624,926,585,401,491,471,186,103,40,590,103,103,417,395,43,912,927,6,7,103,103,103,103,601,475,425,430,41,431,433,259,434,435,436,416,394,422,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,560,583,318,955,569,412,414,395,425,574,420,49,419,989,425,425,698,163,587,164,828,857,567,358,395,850,196,432,627,888,630,631,632,634,635,599,954,708,277,75,977,362,256,197,209,709,1022,547,593,1023,550,552,553,402,422,556,557,558,210,19,20,21,22,-171,395,604,191,19,20,21,22,395,64,683,94,95,96,97,98,99,100,101,566,761,762,395,570,571,48,572,438,575,362,577,395,578,567,580,80,969,970,900,586,916,209,589,542,378,592,615,282,595,44,597,598,355,787,356,210,73,81,1034,50,473,103,103,676,178,179,180,181,718,103,103,103,94,95,96,97,98,99,100,101,277,395,182,183,53,422,735,209,364,312,103,78,103,500,395,719,675,591,924,365,366,210,367,611,368,978,369,492,103,616,617,395,404,501,395,620,370,641,642,643,364,502,625,652,685,925,-171,659,660,82,168,686,687,729,688,606,689,607,690,364,656,501,355,405,356,658,963,995,691,662,664,176,282,668,669,368,671,369,677,56,503,103,177,103,730,809,810,370,199,17,813,73,371,371,371,505,371,371,17,274,501,45,371,371,46,254,209,371,84,421,85,86,184,311,312,501,869,-171,-171,205,210,-171,255,206,57,364,87,842,261,207,88,89,90,91,92,723,208,422,506,670,423,368,185,369,205,205,726,58,206,206,543,904,262,370,207,207,785,733,501,273,319,208,208,498,742,583,-171,437,103,60,61,62,63,64,65,371,749,741,371,371,371,743,371,371,501,178,179,180,181,-171,498,499,905,103,46,422,501,169,476,170,683,362,750,182,183,19,20,21,22,-171,780,-171,-171,-171,-171,-171,-171,485,499,901,338,46,501,868,751,793,908,339,796,797,798,758,205,801,802,803,206,18,384,391,806,769,207,808,501,72,18,811,812,208,814,759,815,770,816,108,928,817,1,818,819,501,4,328,822,329,330,929,825,207,777,93,362,501,832,943,501,64,392,94,95,96,97,98,99,100,101,501,843,108,108,422,403,108,728,108,406,108,108,108,108,108,804,5,405,418,873,874,93,427,364,364,439,633,685,606,53,607,865,470,492,440,72,72,688,367,689,368,690,369,877,878,879,103,645,646,647,103,691,370,103,477,103,185,486,666,478,667,72,889,483,543,72,72,72,945,495,371,371,205,355,371,356,206,497,702,219,703,508,207,856,868,72,626,108,628,208,72,72,510,612,108,364,108,906,613,618,54,55,623,371,56,910,365,366,621,367,637,368,638,369,639,108,679,919,108,108,640,665,706,370,404,732,806,108,108,108,108,380,727,932,683,734,737,739,936,937,205,939,102,941,206,744,53,748,944,57,207,768,479,946,947,786,788,208,840,848,1,956,957,349,4,328,350,329,330,371,351,855,58,915,860,875,190,967,876,897,195,653,198,902,200,201,202,203,204,777,980,961,220,480,60,61,62,63,64,65,962,975,992,72,371,5,994,1005,42,178,179,180,181,364,383,541,845,685,103,103,205,56,852,1001,206,47,1003,182,183,689,207,690,1006,162,103,72,72,208,66,79,1011,691,1013,563,364,1016,67,260,685,52,277,84,379,85,86,686,687,175,688,51,689,317,690,736,838,1042,57,1030,325,87,327,823,691,88,89,90,91,92,903,854,205,103,997,998,206,738,579,661,1040,58,207,990,381,382,573,821,72,208,108,108,672,387,388,389,390,973,108,108,108,472,1017,60,61,62,63,64,65,0,72,596,72,281,282,67,67,72,205,108,0,108,206,72,68,72,0,283,207,184,72,19,20,21,22,208,72,108,374,72,0,67,0,72,0,67,67,67,0,0,0,307,308,309,310,311,312,277,0,185,0,0,205,0,53,67,206,907,29,0,67,67,207,385,0,313,314,0,0,208,0,678,679,0,72,0,0,0,0,72,108,0,108,29,0,0,0,72,0,707,683,68,68,0,0,0,191,0,0,0,69,83,64,0,94,95,96,97,98,99,100,101,0,0,0,53,0,68,281,282,0,68,68,68,0,54,55,0,72,56,0,0,283,0,189,0,0,72,0,0,0,68,0,0,0,0,68,68,0,0,0,0,303,0,561,562,307,308,309,310,311,312,0,564,565,219,0,108,19,20,21,22,-171,67,57,0,0,69,69,0,313,314,0,576,0,325,54,55,0,0,56,0,108,0,0,0,0,58,0,0,364,594,0,69,685,67,67,69,69,69,72,686,687,0,688,0,689,59,690,60,61,62,63,64,65,0,0,69,691,0,205,0,69,69,206,57,0,189,0,0,207,386,0,0,373,0,84,208,85,86,0,0,70,0,605,0,610,0,58,68,0,0,0,673,87,0,67,0,88,89,90,91,92,0,0,0,0,72,674,277,60,61,62,63,64,65,0,0,67,0,67,68,68,0,0,67,0,0,0,0,0,67,0,67,0,0,0,0,67,0,0,0,0,0,67,0,0,67,0,0,0,67,72,0,0,0,0,0,0,70,70,0,108,0,0,0,108,0,0,108,0,108,0,0,0,69,0,0,0,0,281,282,68,0,0,70,0,0,0,70,70,70,67,0,283,0,721,67,0,0,0,0,0,0,68,67,68,69,69,70,72,68,0,0,70,70,0,68,0,68,0,0,311,312,68,0,0,0,0,0,68,373,0,68,0,0,0,68,0,191,0,0,313,314,0,64,67,94,95,96,97,98,99,100,101,67,1,0,0,349,4,328,350,329,330,0,540,0,69,0,0,0,0,0,0,71,678,679,68,0,0,0,681,68,0,0,0,0,0,0,69,68,69,0,682,683,0,69,0,0,0,5,0,69,0,69,0,0,0,0,69,0,0,0,0,0,69,53,0,69,0,0,0,69,0,0,72,70,67,0,0,0,68,0,108,108,827,0,0,0,833,68,0,835,0,839,535,0,0,0,108,0,71,71,0,0,0,0,72,70,70,0,0,0,69,820,0,219,0,69,0,0,0,0,0,0,0,69,71,0,0,0,71,71,71,83,0,629,54,55,0,0,56,0,53,67,0,108,0,0,0,189,71,0,684,364,0,71,71,685,0,0,0,0,68,0,686,687,69,688,70,689,0,690,0,0,0,69,84,0,834,86,0,691,0,0,0,57,0,0,67,0,70,0,70,0,87,0,0,70,88,89,90,91,92,70,0,70,0,0,58,0,70,0,0,54,55,0,70,56,0,70,0,0,0,70,0,0,0,0,220,68,60,61,62,63,64,65,0,0,0,0,0,0,0,0,0,0,67,0,69,0,0,0,0,0,0,0,951,953,649,0,0,0,57,0,70,0,0,0,71,70,0,0,964,189,68,0,0,70,0,754,755,756,0,697,53,58,373,0,0,0,0,0,0,0,0,0,0,0,0,0,71,71,0,0,0,227,0,60,61,62,63,64,65,0,0,69,53,0,70,999,0,0,0,0,0,0,0,70,0,0,0,0,68,0,219,0,0,0,0,93,0,0,0,0,0,64,0,94,95,96,97,98,99,100,101,54,55,0,0,56,69,71,67,0,972,0,219,0,0,0,0,0,0,0,0,0,0,0,0,0,0,373,189,71,0,71,0,54,55,0,71,56,0,0,67,0,71,0,71,0,70,0,0,71,57,0,0,0,0,71,357,358,71,0,0,0,71,0,69,0,0,790,791,792,0,794,795,58,0,362,799,800,0,0,0,0,57,0,0,0,0,0,0,0,0,0,0,220,68,60,61,62,63,64,65,0,0,71,0,58,0,0,71,0,0,0,0,70,0,0,71,0,84,0,950,86,0,0,0,220,68,60,61,62,63,64,65,0,0,0,87,0,846,847,88,89,90,91,92,0,0,851,0,853,0,0,0,0,0,0,0,71,70,861,862,863,864,0,0,84,71,952,86,0,0,0,0,0,0,0,0,0,880,0,69,0,0,87,0,364,0,88,89,90,91,92,0,0,0,0,365,366,0,367,0,368,0,369,0,0,0,0,0,0,0,0,69,370,0,0,70,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,909,0,0,0,0,911,71,0,0,0,0,0,0,0,917,918,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,930,0,0,678,679,680,0,0,938,681,940,0,942,0,0,0,0,93,0,0,0,682,683,64,0,94,95,96,97,98,99,100,101,0,0,0,0,0,0,0,0,0,71,0,0,0,968,0,971,0,0,974,0,0,976,0,0,0,0,981,982,0,0,0,93,0,0,0,70,0,64,0,94,95,96,97,98,99,100,101,0,0,0,0,996,0,0,71,0,0,1000,0,0,0,0,1004,0,0,0,0,70,0,0,1007,1008,0,0,0,0,1012,0,1014,1015,0,0,0,0,0,0,1020,0,0,0,0,0,0,0,1025,0,0,1027,684,364,109,1029,110,685,0,111,0,0,0,0,686,687,71,688,112,689,1035,690,1036,0,1038,0,0,0,1041,0,0,691,0,692,0,1043,0,0,0,0,1045,113,829,830,0,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,0,140,141,142,0,0,0,0,512,513,514,515,0,0,516,0,517,518,519,520,521,0,0,0,0,0,522,523,0,0,0,0,0,0,0,0,0,0,0,0,143,144,145,57,0,0,0,1018,1019,71,0,0,524,0,525,0,146,109,526,110,0,0,111,0,0,0,58,0,0,0,0,112,214,0,147,0,148,0,0,0,0,71,0,0,0,0,149,150,60,61,62,63,64,65,113,527,0,528,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,529,140,141,142,0,530,0,0,0,0,0,0,0,0,0,0,0,0,0,0,531,532,533,0,0,534,0,0,0,0,0,0,0,0,0,0,0,0,93,0,143,144,145,57,0,0,0,0,0,0,0,0,0,0,0,0,146,109,0,110,0,0,111,0,0,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,0,0,653,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,0,140,141,142,0,0,0,0,0,0,0,0,0,0,512,513,514,515,0,0,516,0,517,518,519,520,521,0,678,679,680,0,522,523,681,0,0,0,0,0,143,144,145,57,0,0,682,683,0,0,0,0,0,0,0,0,146,109,524,110,525,0,111,0,526,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,0,0,0,0,0,114,527,115,528,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,805,140,141,142,0,0,529,0,0,0,0,530,0,0,0,0,0,0,0,0,684,364,0,0,0,685,531,532,533,0,0,534,686,687,0,688,0,689,0,690,143,144,145,57,93,0,0,0,0,691,0,807,0,0,0,0,146,109,0,110,0,0,111,0,0,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,0,0,0,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,0,140,141,142,0,0,0,0,0,512,513,514,515,0,0,516,0,517,518,519,520,521,0,0,0,0,0,522,523,0,0,0,0,0,0,0,0,0,0,0,143,144,145,57,0,0,0,0,0,0,0,0,0,524,0,525,146,0,0,526,0,512,513,514,515,0,58,516,0,517,518,519,520,521,147,0,148,0,0,522,523,0,0,0,0,0,149,150,60,61,62,63,64,65,644,0,527,0,528,0,0,0,0,0,0,524,0,525,0,0,0,526,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,645,646,647,0,529,275,0,0,0,530,276,0,277,0,0,527,0,528,0,0,0,0,0,0,531,532,533,0,0,534,278,678,679,680,0,0,0,681,0,0,0,0,648,0,0,0,0,0,0,682,683,0,0,0,0,0,529,0,0,279,280,530,0,0,0,0,0,678,679,680,0,757,0,681,0,0,531,532,533,0,0,534,0,281,282,682,683,0,0,0,0,0,0,0,648,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,678,679,680,0,0,0,681,0,0,0,0,0,0,0,313,314,0,0,682,683,678,679,680,0,913,0,681,0,684,364,0,0,0,685,0,991,0,0,682,683,686,687,0,688,0,689,0,690,920,921,0,0,678,679,680,0,914,691,681,1037,0,0,0,0,684,364,0,0,0,685,682,683,0,0,0,0,686,687,0,688,0,689,0,690,678,679,680,0,960,0,681,0,0,691,0,0,0,0,0,0,0,0,682,683,357,358,359,0,740,0,360,0,0,0,0,0,0,0,0,0,0,0,361,362,0,0,0,0,684,364,0,0,0,685,0,0,678,679,680,0,686,687,681,688,0,689,0,690,684,364,0,0,0,685,682,683,0,691,0,0,686,687,0,688,0,689,0,690,357,358,359,0,0,0,360,0,0,691,0,0,684,364,0,0,0,685,361,362,357,358,0,0,686,687,360,688,0,689,0,690,0,0,0,0,0,0,361,362,0,691,0,0,684,364,0,0,0,685,0,0,0,0,0,0,686,687,0,688,0,689,0,690,363,364,0,0,0,0,0,0,0,691,0,0,365,366,0,367,0,368,0,369,0,0,0,0,0,0,0,0,0,370,0,0,684,364,0,0,0,685,0,0,0,0,0,0,686,687,0,688,0,689,275,690,0,0,0,276,0,277,0,0,0,691,0,0,363,364,0,0,0,0,0,0,0,0,0,278,365,366,0,367,0,368,0,369,363,364,0,0,0,0,0,0,0,370,0,0,365,366,0,367,0,368,0,369,279,280,0,0,0,0,0,0,0,370,0,0,0,0,0,0,0,0,0,277,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,278,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,281,282,0,278,0,0,0,1009,0,0,1010,0,0,0,283,284,285,286,287,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,277,0,0,0,0,281,282,0,0,0,0,313,314,0,0,0,0,0,278,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,281,282,0,278,0,0,0,789,0,0,0,0,0,0,283,284,285,286,287,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,303,304,0,0,307,308,309,310,311,312,0,0,0,0,0,0,0,277,0,0,0,0,281,282,0,0,0,0,313,314,0,0,0,0,0,278,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,281,282,0,278,0,0,0,934,0,0,0,0,0,0,283,284,285,286,287,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,303,0,0,0,307,308,309,310,311,312,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,313,314,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,935,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,979,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,881,882,0,0,0,0,278,0,0,1039,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,699,0,0,0,883,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,700,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,705,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,398,0,887,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,399,400,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,715,716,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,772,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,773,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,884,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,965,885,0,0,0,0,0,0,313,314,966,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,985,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,321,277,0,0,0,986,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,393,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,396,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,410,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,411,0,0,313,314,429,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,507,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,600,277,0,0,0,0,588,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,602,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,701,0,0,0,0,704,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,313,314,712,0,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,400,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,722,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,767,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,776,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,781,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,782,772,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,824,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,826,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,872,277,0,0,0,841,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,886,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,890,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,891,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,892,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,893,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,313,314,894,0,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,313,314,895,0,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,896,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,923,0,898,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,933,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,958,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,959,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,983,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,984,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,987,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1002,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,1024,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1026,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,1028,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,1031,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,1032,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,1044,1033,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,275,0,0,0,0,0,0,277,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,278,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,0,275,0,0,0,279,280,0,277,0,0,313,314,0,0,0,0,0,0,0,0,0,0,0,0,0,278,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,279,280,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,275,281,282,0,0,0,0,277,0,0,0,0,0,313,314,283,284,285,286,287,288,289,290,291,292,278,294,0,0,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,0,0,279,280,0,277,0,0,0,0,0,313,314,0,0,0,0,0,0,0,0,0,0,278,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,0,0,279,280,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,281,282,277,0,0,0,0,0,0,0,0,0,313,314,283,284,285,286,287,0,278,0,0,0,0,0,0,0,0,0,0,0,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,0,0,0,0,0,0,0,0,0,0,0,0,0,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,313,314";
  public static final short yytable_ninf_ = -172;
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
  public static final String yyCheckString = "48,481,193,145,113,53,139,608,644,665,13,36,13,329,78,144,13,13,163,654,13,185,31,168,169,165,45,31,27,12,139,59,12,59,332,33,145,49,12,14,15,16,17,13,12,3,4,5,396,7,17,90,19,180,17,27,19,32,33,46,30,118,110,111,112,104,180,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,114,48,13,149,80,81,18,30,84,146,86,13,88,89,90,91,92,1,18,30,4,5,178,179,180,18,182,183,181,146,180,146,27,470,171,0,173,146,90,112,33,149,151,146,9,146,149,184,100,44,34,13,104,141,187,18,143,13,13,43,147,357,358,359,146,361,362,13,13,151,366,367,159,146,161,173,372,149,142,17,173,19,20,488,171,146,149,146,180,165,180,75,149,149,180,180,172,35,174,352,169,39,40,41,42,43,47,831,172,47,497,27,18,30,47,313,180,193,180,18,196,197,259,146,33,850,871,174,175,205,206,207,208,18,146,146,273,0,275,276,17,278,279,280,146,21,146,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,374,411,407,903,398,254,255,146,146,403,262,33,261,144,146,146,18,17,416,19,18,766,396,15,146,146,27,276,500,18,502,503,504,505,506,429,902,13,19,33,18,33,416,44,90,21,18,360,422,18,363,364,365,146,146,368,369,370,104,176,177,178,179,180,146,475,180,176,177,178,179,146,186,33,188,189,190,191,192,193,194,195,395,56,57,146,399,400,12,402,281,404,33,406,146,408,470,410,12,62,63,102,415,149,90,418,355,563,421,483,91,424,180,426,427,17,59,19,104,409,34,1021,180,314,376,377,533,14,15,16,17,146,384,385,386,188,189,190,191,192,193,194,195,19,146,32,33,17,146,149,90,143,133,405,12,407,13,146,173,533,146,146,154,155,104,157,479,159,146,161,330,423,485,486,146,118,33,146,491,171,512,513,514,143,13,498,515,147,173,17,522,523,180,17,154,155,606,157,17,159,19,161,143,516,33,17,149,19,521,149,955,171,525,526,172,91,529,530,159,532,161,534,92,13,476,180,478,46,700,701,171,180,0,705,524,544,545,546,13,548,549,9,98,33,146,554,555,149,17,90,559,17,118,19,20,142,132,133,33,100,88,89,27,104,92,17,31,137,143,35,742,17,37,39,40,41,42,43,591,44,146,13,531,149,159,172,161,27,27,602,160,31,31,180,59,17,171,37,37,673,613,33,17,180,44,44,118,13,718,137,180,563,182,183,184,185,186,187,627,13,633,630,631,632,637,634,635,33,14,15,16,17,160,118,146,100,588,149,146,33,17,149,19,33,33,13,32,33,176,177,178,179,180,666,182,183,184,185,186,187,12,146,102,12,149,33,776,13,681,841,180,684,685,686,13,27,689,690,691,31,0,36,180,696,13,37,699,33,36,9,703,704,44,706,33,708,13,710,46,13,713,3,715,716,33,7,8,720,10,11,13,724,37,665,180,33,33,730,890,33,186,180,188,189,190,191,192,193,194,195,33,744,80,81,146,31,84,149,86,13,88,89,90,91,92,694,48,149,17,781,782,180,13,143,143,92,142,147,17,17,19,774,20,623,180,113,114,157,157,159,159,161,161,786,787,788,728,138,139,140,732,171,171,735,180,737,172,12,17,149,19,139,805,146,180,143,144,145,892,173,809,810,27,17,813,19,31,172,144,67,146,180,37,765,924,163,499,165,501,44,168,169,180,180,172,143,174,840,50,180,88,89,33,842,92,848,154,155,180,157,12,159,180,161,12,193,15,860,196,197,180,17,46,171,118,149,869,205,206,207,208,18,21,876,33,18,149,173,881,882,27,884,46,886,31,12,17,12,891,137,37,21,49,896,897,12,59,44,17,20,3,904,905,6,7,8,9,10,11,908,13,146,160,855,20,17,80,920,118,27,84,55,86,180,88,89,90,91,92,871,933,17,180,146,182,183,184,185,186,187,17,20,51,281,943,48,46,27,9,14,15,16,17,143,21,352,748,147,900,901,27,92,759,966,31,27,969,32,33,159,37,161,975,49,916,313,314,44,36,43,983,171,985,384,143,988,36,140,147,34,19,17,193,19,20,154,155,75,157,33,159,165,161,618,736,1034,137,1010,172,35,174,18,171,39,40,41,42,43,837,763,27,963,961,962,31,623,409,524,1031,160,37,946,196,197,402,718,374,44,376,377,532,205,206,207,208,924,384,385,386,180,992,182,183,184,185,186,187,-1,396,425,398,90,91,113,114,403,27,405,-1,407,31,409,36,411,-1,104,37,142,416,176,177,178,179,44,422,423,47,425,-1,139,-1,429,-1,143,144,145,-1,-1,-1,128,129,130,131,132,133,19,-1,172,-1,-1,27,-1,17,163,31,180,2,-1,168,169,37,38,-1,152,153,-1,-1,44,-1,14,15,-1,470,-1,-1,-1,-1,475,476,-1,478,27,-1,-1,-1,483,-1,173,33,113,114,-1,-1,-1,180,-1,-1,-1,36,45,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,17,-1,139,90,91,-1,143,144,145,-1,88,89,-1,524,92,-1,-1,104,-1,78,-1,-1,533,-1,-1,-1,163,-1,-1,-1,-1,168,169,-1,-1,-1,-1,124,-1,376,377,128,129,130,131,132,133,-1,385,386,67,-1,563,176,177,178,179,180,281,137,-1,-1,113,114,-1,152,153,-1,405,-1,407,88,89,-1,-1,92,-1,588,-1,-1,-1,-1,160,-1,-1,143,423,-1,139,147,313,314,143,144,145,606,154,155,-1,157,-1,159,180,161,182,183,184,185,186,187,-1,-1,163,171,-1,27,-1,168,169,31,137,-1,181,-1,-1,37,38,-1,-1,188,-1,17,44,19,20,-1,-1,36,-1,476,-1,478,-1,160,281,-1,-1,-1,165,35,-1,374,-1,39,40,41,42,43,-1,-1,-1,-1,673,180,19,182,183,184,185,186,187,-1,-1,396,-1,398,313,314,-1,-1,403,-1,-1,-1,-1,-1,409,-1,411,-1,-1,-1,-1,416,-1,-1,-1,-1,-1,422,-1,-1,425,-1,-1,-1,429,718,-1,-1,-1,-1,-1,-1,113,114,-1,728,-1,-1,-1,732,-1,-1,735,-1,737,-1,-1,-1,281,-1,-1,-1,-1,90,91,374,-1,-1,139,-1,-1,-1,143,144,145,470,-1,104,-1,588,475,-1,-1,-1,-1,-1,-1,396,483,398,313,314,163,776,403,-1,-1,168,169,-1,409,-1,411,-1,-1,132,133,416,-1,-1,-1,-1,-1,422,345,-1,425,-1,-1,-1,429,-1,180,-1,-1,152,153,-1,186,524,188,189,190,191,192,193,194,195,533,3,-1,-1,6,7,8,9,10,11,-1,13,-1,374,-1,-1,-1,-1,-1,-1,36,14,15,470,-1,-1,-1,20,475,-1,-1,-1,-1,-1,-1,396,483,398,-1,32,33,-1,403,-1,-1,-1,48,-1,409,-1,411,-1,-1,-1,-1,416,-1,-1,-1,-1,-1,422,17,-1,425,-1,-1,-1,429,-1,-1,892,281,606,-1,-1,-1,524,-1,900,901,728,-1,-1,-1,732,533,-1,735,-1,737,351,-1,-1,-1,916,-1,113,114,-1,-1,-1,-1,924,313,314,-1,-1,-1,470,65,-1,67,-1,475,-1,-1,-1,-1,-1,-1,-1,483,139,-1,-1,-1,143,144,145,499,-1,501,88,89,-1,-1,92,-1,17,673,-1,963,-1,-1,-1,515,163,-1,142,143,-1,168,169,147,-1,-1,-1,-1,606,-1,154,155,524,157,374,159,-1,161,-1,-1,-1,533,17,-1,19,20,-1,171,-1,-1,-1,137,-1,-1,718,-1,396,-1,398,-1,35,-1,-1,403,39,40,41,42,43,409,-1,411,-1,-1,160,-1,416,-1,-1,88,89,-1,422,92,-1,425,-1,-1,-1,429,-1,-1,-1,-1,180,673,182,183,184,185,186,187,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,776,-1,606,-1,-1,-1,-1,-1,-1,-1,900,901,515,-1,-1,-1,137,-1,470,-1,-1,-1,281,475,-1,-1,916,639,718,-1,-1,483,-1,645,646,647,-1,540,17,160,652,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,313,314,-1,-1,-1,180,-1,182,183,184,185,186,187,-1,-1,673,17,-1,524,963,-1,-1,-1,-1,-1,-1,-1,533,-1,-1,-1,-1,776,-1,67,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,88,89,-1,-1,92,718,374,892,-1,65,-1,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,747,748,396,-1,398,-1,88,89,-1,403,92,-1,-1,924,-1,409,-1,411,-1,606,-1,-1,416,137,-1,-1,-1,-1,422,14,15,425,-1,-1,-1,429,-1,776,-1,-1,678,679,680,-1,682,683,160,-1,33,687,688,-1,-1,-1,-1,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,892,182,183,184,185,186,187,-1,-1,470,-1,160,-1,-1,475,-1,-1,-1,-1,673,-1,-1,483,-1,17,-1,19,20,-1,-1,-1,180,924,182,183,184,185,186,187,-1,-1,-1,35,-1,749,750,39,40,41,42,43,-1,-1,758,-1,760,-1,-1,-1,-1,-1,-1,-1,524,718,770,771,772,773,-1,-1,17,533,19,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,789,-1,892,-1,-1,35,-1,143,-1,39,40,41,42,43,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,924,171,-1,-1,776,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,844,-1,-1,-1,-1,849,606,-1,-1,-1,-1,-1,-1,-1,858,859,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,875,-1,-1,14,15,16,-1,-1,883,20,885,-1,887,-1,-1,-1,-1,180,-1,-1,-1,32,33,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,-1,-1,-1,-1,-1,673,-1,-1,-1,921,-1,923,-1,-1,926,-1,-1,929,-1,-1,-1,-1,934,935,-1,-1,-1,180,-1,-1,-1,892,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,959,-1,-1,718,-1,-1,965,-1,-1,-1,-1,970,-1,-1,-1,-1,924,-1,-1,978,979,-1,-1,-1,-1,984,-1,986,987,-1,-1,-1,-1,-1,-1,994,-1,-1,-1,-1,-1,-1,-1,1002,-1,-1,1005,142,143,15,1009,17,147,-1,20,-1,-1,-1,-1,154,155,776,157,29,159,1024,161,1026,-1,1028,-1,-1,-1,1032,-1,-1,171,-1,173,-1,1039,-1,-1,-1,-1,1044,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,53,54,892,-1,-1,58,-1,60,-1,150,15,64,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,30,-1,168,-1,170,-1,-1,-1,-1,924,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,101,-1,103,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,142,95,96,97,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,94,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,58,-1,60,150,-1,-1,64,-1,14,15,16,17,-1,160,20,-1,22,23,24,25,26,168,-1,170,-1,-1,32,33,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,99,-1,101,-1,103,-1,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,138,139,140,-1,142,12,-1,-1,-1,147,17,-1,19,-1,-1,101,-1,103,-1,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,37,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,142,-1,-1,66,67,147,-1,-1,-1,-1,-1,14,15,16,-1,18,-1,20,-1,-1,162,163,164,-1,-1,167,-1,90,91,32,33,-1,-1,-1,-1,-1,-1,-1,180,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,32,33,14,15,16,-1,18,-1,20,-1,142,143,-1,-1,-1,147,-1,173,-1,-1,32,33,154,155,-1,157,-1,159,-1,161,62,63,-1,-1,14,15,16,-1,18,171,20,173,-1,-1,-1,-1,142,143,-1,-1,-1,147,32,33,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,14,15,16,-1,18,-1,20,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,32,33,14,15,16,-1,18,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,14,15,16,-1,154,155,20,157,-1,159,-1,161,142,143,-1,-1,-1,147,32,33,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,14,15,16,-1,-1,-1,20,-1,-1,171,-1,-1,142,143,-1,-1,-1,147,32,33,14,15,-1,-1,154,155,20,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,32,33,-1,171,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,12,161,-1,-1,-1,17,-1,19,-1,-1,-1,171,-1,-1,142,143,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,154,155,-1,157,-1,159,-1,161,142,143,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,66,67,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
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
         0,     3,     4,     5,     7,    48,   174,   175,   213,   214,
     215,   216,   217,   232,   237,   240,   243,   256,   260,   176,
     177,   178,   179,   244,   245,   246,   180,   233,   234,   236,
     180,   238,   239,   246,   261,   262,   246,   257,   258,   180,
     180,     0,   215,    33,   180,   146,   149,   234,    12,    33,
     180,   263,   262,    17,    88,    89,    92,   137,   160,   180,
     182,   183,   184,   185,   186,   187,   259,   291,   292,   293,
     294,   295,   296,   320,   323,    33,    12,   169,    12,   245,
      12,    34,   180,   236,    17,    19,    20,    35,    39,    40,
      41,    42,    43,   180,   188,   189,   190,   191,   192,   193,
     194,   195,   247,   248,   249,   250,   251,   252,   296,    15,
      17,    20,    29,    52,    58,    60,    64,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    88,    89,    93,
      95,    96,    97,   134,   135,   136,   150,   168,   170,   180,
     181,   290,   291,   292,   293,   294,   295,   296,   299,   301,
     304,   305,   239,    17,    19,   149,   267,   290,    17,    17,
      19,    12,   149,    12,   149,   258,   172,   180,    14,    15,
      16,    17,    32,    33,   142,   172,   180,   218,   235,   236,
     247,   180,   247,   253,   254,   247,    27,    44,   247,   180,
     247,   247,   247,   247,   247,    27,    31,    37,    44,    90,
     104,   290,   289,   290,    30,   289,   290,   306,   307,    67,
     180,   319,   320,   322,   324,   325,   326,   180,   259,   263,
     297,   298,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,    17,    17,   319,   326,   331,    17,
     249,    17,    17,   319,   321,   327,   328,   329,   330,   327,
     324,   289,   290,    17,    98,    12,    17,    19,    37,    66,
      67,    90,    91,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   114,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   152,   153,   268,   321,   247,   250,   180,
     270,    18,   321,   321,   290,   247,   290,   247,     8,    10,
      11,   241,   242,   243,   256,   260,   276,   286,    12,   180,
     219,   220,   236,   219,   219,   235,   219,   219,   290,     6,
       9,    13,   221,   222,   242,    17,    19,    14,    15,    16,
      20,    32,    33,   142,   143,   154,   155,   157,   159,   161,
     171,   230,    13,   236,    47,   255,    45,   149,   254,   255,
      18,   247,   247,    21,    36,    38,    38,   247,   247,   247,
     247,   180,   180,    18,    21,   146,    31,    30,    31,    86,
      87,    30,   146,    31,   118,   149,    13,   149,    59,   146,
      61,   149,   289,   290,   289,    13,   146,   290,    17,   289,
     248,   118,   146,   149,    13,   146,    13,    13,   151,    31,
     290,   290,   289,   290,   290,   290,   290,   180,   323,    92,
     180,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
      20,   319,   180,   323,    18,   146,   149,   180,   149,    49,
     146,   271,   272,   146,    18,    12,    12,   246,   277,   278,
     278,    47,   246,   287,   288,   173,   241,   172,   118,   146,
      13,    33,    13,    13,    13,    13,    13,   145,   180,   224,
     180,   223,    14,    15,    16,    17,    20,    22,    23,    24,
      25,    26,    32,    33,    58,    60,    64,   101,   103,   142,
     147,   162,   163,   164,   167,   226,   248,   312,   313,   315,
      13,   222,   289,   180,   218,   218,   218,   290,   218,   218,
     290,   231,   290,   290,   218,   218,   290,   290,   290,   218,
     319,   247,   247,   253,   247,   247,   290,   319,   325,   327,
     290,   290,   290,   307,   327,   290,   247,   290,   290,   298,
     290,   302,   303,   321,    18,    18,   290,   326,   146,   290,
      18,   146,   290,   319,   247,   290,   328,   290,   290,   327,
      18,    18,    86,   325,   321,   247,    17,    19,   264,   265,
     247,   290,   180,    50,   274,   319,   290,   290,   180,   278,
     290,   180,   310,    33,   241,   290,   220,   218,   220,   236,
     218,   218,   218,   142,   218,   218,   219,    12,   180,    12,
     180,   219,   219,   219,    99,   138,   139,   140,   180,   226,
     228,   229,   235,    55,   281,   282,   290,   318,   290,   219,
     219,   297,   290,   308,   290,    17,    17,    19,   290,   290,
     289,   290,   308,   165,   180,   319,   324,   290,    14,    15,
      16,    20,    32,    33,   142,   147,   154,   155,   157,   159,
     161,   171,   173,   230,    17,    19,   100,   226,    18,    32,
     158,   145,   144,   146,    28,   156,    46,   173,    13,    21,
      13,    30,    30,    13,    30,    62,    63,   300,   146,   173,
      27,   247,    18,   290,    13,   151,   290,    21,   149,   321,
      46,   265,   149,   290,    18,   149,   267,   149,   288,   173,
      18,   290,    13,   290,    12,   225,   226,   235,    12,    13,
      13,    13,   309,   310,   236,   236,   236,    18,    13,    33,
      13,    56,    57,   283,   284,   285,   271,    21,    21,    13,
      13,    59,    27,    61,    31,   173,   149,   248,   311,   312,
     290,   145,   148,    13,   173,   319,    12,    59,    59,   163,
     226,   226,   226,   290,   226,   226,   290,   290,   290,   226,
     226,   290,   290,   290,   289,    94,   290,   173,   290,   218,
     218,   290,   290,   218,   290,   290,   290,   290,   290,   290,
      65,   303,   290,    18,    18,   290,    18,   247,    18,    53,
      54,   266,   290,   247,    19,   247,   279,   269,   270,   247,
      17,   145,   218,   290,    13,   225,   226,   226,    20,    13,
     146,   226,   229,   226,   284,   146,   248,   274,    27,   141,
      20,   226,   226,   226,   226,   290,   316,   317,   321,   100,
      18,    33,    18,   219,   219,    17,   118,   290,   290,   290,
     226,    31,    32,   158,    31,   145,    31,   156,    18,   290,
      21,    28,    31,    21,    30,    30,    61,    27,   151,   271,
     102,   102,   180,   281,    59,   100,   290,   180,   218,   226,
     290,   226,   310,    18,    18,   248,   149,   226,   226,   290,
      62,    63,   314,    27,   146,   173,    27,   312,    13,    13,
     226,   227,   290,    38,   163,   163,   290,   290,   226,   290,
     226,   290,   226,   218,   290,   327,   290,   290,   273,   274,
      19,   247,    19,   247,   265,   271,   290,   290,    18,    21,
      18,    17,    17,   149,   247,    21,    31,   290,   226,    62,
      63,   226,    65,   317,   226,    20,   226,    18,   146,   163,
     290,   226,   226,    32,    21,    31,   145,   148,    13,   144,
     300,   173,    51,   275,    46,   274,   226,   289,   289,   247,
     226,   290,    61,   290,   226,    27,   290,   226,   226,   163,
     166,   290,   226,   290,   226,   226,   290,   248,    53,    54,
     226,   280,    18,    18,    21,   226,    61,   226,    21,   226,
     290,    31,   145,   144,   271,   226,   226,   173,   226,   163,
     290,   226,   273,   226,    21,   226
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
     465,   466
  };
  }

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */

  private static final short
  yyr1_[] =
  {
         0,   212,   213,   214,   214,   215,   215,   215,   215,   215,
     216,   216,   217,   217,   218,   218,   218,   218,   218,   218,
     218,   218,   218,   218,   218,   218,   218,   218,   218,   218,
     218,   218,   218,   218,   218,   218,   218,   218,   218,   218,
     218,   218,   219,   219,   219,   219,   220,   220,   221,   221,
     222,   222,   222,   222,   223,   223,   224,   224,   225,   225,
     226,   226,   226,   226,   226,   226,   226,   226,   226,   226,
     226,   226,   226,   226,   226,   226,   226,   226,   226,   226,
     226,   226,   226,   226,   226,   226,   226,   226,   226,   226,
     226,   226,   226,   226,   226,   226,   226,   226,   226,   226,
     226,   226,   226,   226,   226,   226,   226,   226,   226,   226,
     226,   226,   226,   226,   226,   226,   226,   226,   226,   226,
     227,   227,   228,   228,   229,   229,   229,   230,   230,   230,
     231,   231,   232,   232,   233,   233,   234,   234,   235,   235,
     236,   236,   237,   237,   238,   238,   239,   240,   240,   240,
     241,   241,   242,   242,   242,   242,   242,   242,   243,   243,
     243,   244,   244,   245,   245,   245,   245,   246,   246,   246,
     246,   246,   247,   247,   247,   247,   247,   247,   247,   247,
     247,   247,   247,   247,   247,   247,   248,   248,   248,   249,
     249,   249,   249,   249,   249,   249,   249,   250,   250,   251,
     251,   252,   252,   253,   253,   254,   254,   254,   255,   256,
     256,   257,   257,   258,   259,   259,   259,   259,   260,   260,
     261,   261,   262,   262,   263,   264,   264,   265,   265,   266,
     266,   266,   267,   267,   268,   268,   269,   269,   270,   270,
     271,   271,   272,   273,   273,   274,   275,   275,   276,   276,
     277,   277,   278,   278,   279,   279,   279,   279,   280,   280,
     280,   281,   281,   282,   283,   283,   284,   284,   284,   284,
     285,   285,   286,   286,   287,   287,   288,   288,   289,   289,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   290,   290,   290,   290,   290,   290,
     290,   290,   290,   290,   291,   291,   292,   293,   294,   294,
     294,   295,   296,   297,   297,   298,   298,   299,   300,   300,
     301,   301,   302,   302,   303,   304,   304,   304,   304,   304,
     304,   304,   304,   304,   304,   304,   304,   304,   304,   304,
     304,   304,   304,   304,   304,   305,   305,   305,   305,   305,
     305,   305,   305,   305,   305,   305,   305,   305,   305,   305,
     305,   305,   305,   305,   305,   305,   305,   305,   305,   305,
     305,   305,   305,   305,   305,   305,   305,   305,   306,   306,
     307,   308,   308,   309,   309,   310,   310,   310,   311,   311,
     312,   313,   313,   314,   314,   315,   315,   316,   316,   317,
     318,   319,   319,   320,   320,   320,   320,   321,   321,   322,
     322,   323,   323,   323,   323,   323,   323,   323,   324,   324,
     325,   326,   327,   327,   328,   328,   329,   330,   331,   331
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
       7,     3,     7,     3,    11,     7,     9,     5,     1,     5,
       8,     8,     4,     4,     4,     6,     7,     9,     9,     7,
       4,     6,     1,     3,     3,     1,     4,     2,     4,     3,
       2,     4,     4,     1,     6,     6,     7,     8,    10,     4,
       1,     3,     1,     3,     2,     2,     2,     3,     7,     9,
       3,     5,     1,     2,     1,     2,     1,     1,     1,     2,
       3,     3,     1,     2,     1,     3,     3,     1,     1,     1,
       1,     2,     1,     1,     1,     1,     1,     2,     1,     2,
       3,     1,     3,     4,     5,     4,     5,     1,     1,     1,
       1,     0,     3,     1,     1,     5,     3,     3,     3,     2,
       2,     2,     4,     4,     1,     1,     1,     3,     3,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     3,
       3,     3,     3,     1,     2,     1,     3,     3,     4,     1,
       2,     1,     3,     2,     3,     5,     3,     5,     1,     2,
       1,     2,     6,     2,    10,     1,     2,     1,     3,     1,
       1,     1,     1,     3,     3,     5,     0,     1,     3,     5,
       1,     0,     2,     1,     0,     2,     2,     0,     1,     2,
       1,     2,     7,    10,     3,     3,     3,     3,     1,     1,
       1,     1,     0,     2,     1,     2,     2,     4,     3,     5,
       1,     1,     1,     2,     1,     3,     2,     2,     1,     3,
       1,     1,     1,     1,     1,     1,     3,     1,     1,     2,
       3,     3,     3,     2,     4,     6,     3,     3,     3,     3,
       5,     4,     1,     1,     1,     1,     4,     4,     4,     4,
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
  "U-BARTILDEBAR", "U-SEMI", "T-STAR", "QUESTIONALONE", "U-MINUS",
  "U-PLUS", "G-HIGH", "$accept", "source", "programParagraphList",
  "programParagraph", "classDefinition", "processDefinition", "process",
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
  "explicitFunctionDef", "parameterList", "parameters", "functionBody",
  "parameterTypes", "patternListTypeList", "identifierTypePairList_opt",
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
       213,     0,    -1,   214,    -1,   215,    -1,   214,   215,    -1,
     216,    -1,   217,    -1,   232,    -1,   237,    -1,   240,    -1,
     174,   180,    12,   172,   241,   173,    -1,   174,   180,   169,
     180,    12,   172,   241,   173,    -1,   175,   180,    12,   218,
      -1,   175,   180,    12,   235,    13,   218,    -1,   172,    13,
     226,   173,    -1,   172,   221,    13,   226,   173,    -1,   218,
      33,   218,    -1,   218,    15,   218,    -1,   218,    14,   218,
      -1,   218,   142,   290,   145,   218,    -1,   218,    20,   290,
      32,   290,    21,   218,    -1,   218,    32,   218,    -1,   218,
      16,   218,    -1,   218,   155,   218,    -1,   218,   154,   290,
     156,   218,    -1,   218,   157,   218,    -1,   218,    20,   290,
     158,   218,    -1,   218,   171,   290,    -1,   218,   161,   290,
      -1,   218,   159,   290,    -1,    17,   235,    13,   218,    18,
      17,   290,    18,    -1,   180,    -1,   180,    19,    -1,   180,
      17,   289,    18,    -1,   218,   230,    -1,    33,   219,    13,
     218,    -1,    15,   219,    13,   218,    -1,    14,   219,    13,
     218,    -1,   142,   290,   145,   219,    13,   218,    -1,    32,
     219,    13,   142,   290,   145,   218,    -1,    32,   219,    13,
     218,    -1,    16,   219,    13,   218,    -1,   236,    -1,   220,
      -1,   219,    33,   236,    -1,   219,    33,   220,    -1,   180,
     118,   290,    -1,   180,   146,   220,    -1,   222,    -1,   221,
     222,    -1,   242,    -1,     9,   223,    -1,     6,    -1,     6,
     224,    -1,   180,    12,   225,    -1,   223,   180,    12,   225,
      -1,   180,    12,   290,    -1,   224,   180,    12,   290,    -1,
     226,    -1,   235,    13,   226,    -1,    22,    -1,    23,    -1,
      24,    -1,    25,    -1,    26,   290,    -1,    17,   226,    18,
      -1,    20,   290,    21,    27,   226,    -1,    20,   290,    21,
     141,   226,    -1,   226,    33,   226,    -1,   226,    15,   226,
      -1,   226,    14,   226,    -1,   226,   155,   226,    -1,   226,
     154,   290,   156,   226,    -1,   226,   157,   226,    -1,   226,
      20,   290,   158,   226,    -1,   226,   171,   290,    -1,   226,
     161,   290,    -1,   226,   159,   290,    -1,   226,   230,    -1,
     162,   289,    13,    17,   227,    18,    -1,   226,   147,   290,
      31,   290,   148,   226,    -1,   226,    16,   226,    -1,   226,
     142,   290,    31,   290,   145,   226,    -1,   226,    32,   226,
      -1,   226,    20,   290,    31,   290,    32,   290,    31,   290,
      21,   226,    -1,   226,    20,   290,    32,   290,    21,   226,
      -1,   226,   142,   290,    31,   290,    31,   290,   145,   226,
      -1,   226,   142,   290,   145,   226,    -1,   248,    -1,    17,
     228,    13,   226,    18,    -1,    17,   228,    13,   226,    18,
      17,   289,    18,    -1,    17,   235,    13,   226,    18,    17,
     289,    18,    -1,    33,   219,    13,   226,    -1,    15,   219,
      13,   226,    -1,    14,   219,    13,   226,    -1,   147,   290,
     148,   219,    13,   226,    -1,    16,   219,    13,    20,   290,
      21,   226,    -1,   142,   290,   145,   219,    13,    20,   290,
      21,   226,    -1,    32,   219,    13,    20,   290,    31,   290,
      21,   226,    -1,    32,   219,    13,    20,   290,    21,   226,
      -1,    58,   297,    59,   226,    -1,    17,    99,   309,    13,
     226,    18,    -1,   313,    -1,    60,   308,   173,    -1,   163,
     308,   173,    -1,   312,    -1,   101,    17,   311,    18,    -1,
     248,    19,    -1,   248,    17,   289,    18,    -1,    20,   318,
      21,    -1,   103,    19,    -1,   103,    17,   290,    18,    -1,
     248,   100,    94,   290,    -1,   315,    -1,   164,   324,    59,
     290,   163,   226,    -1,   164,   319,    59,   290,   163,   226,
      -1,   164,   165,   319,   118,   290,   163,   226,    -1,   164,
     180,    12,   290,    38,   290,   163,   226,    -1,   164,   180,
      12,   290,    38,   290,   166,   290,   163,   226,    -1,   167,
     290,   163,   226,    -1,   226,    -1,   227,   146,   226,    -1,
     229,    -1,   228,    33,   229,    -1,   140,   236,    -1,   139,
     236,    -1,   138,   236,    -1,   143,   231,   144,    -1,   143,
     290,    28,   290,    31,   327,   144,    -1,   143,   290,    28,
     290,    31,   327,    13,   290,   144,    -1,   290,    28,   290,
      -1,   231,   146,   290,    28,   290,    -1,     4,    -1,     4,
     233,    -1,   234,    -1,   233,   234,    -1,   180,    -1,   236,
      -1,   236,    -1,   235,   236,    -1,   180,   149,   247,    -1,
     180,   146,   236,    -1,     5,    -1,     5,   238,    -1,   239,
      -1,   238,    33,   239,    -1,   180,    12,   290,    -1,   243,
      -1,   256,    -1,   260,    -1,   242,    -1,   242,   241,    -1,
     243,    -1,   256,    -1,   260,    -1,   276,    -1,   286,    -1,
      10,   278,    -1,     3,    -1,     3,   244,    -1,     3,   244,
      33,    -1,   245,    -1,   244,    33,   245,    -1,   246,   180,
      12,   247,    -1,   246,   180,    12,   247,   255,    -1,   246,
     180,    34,   253,    -1,   246,   180,    34,   253,   255,    -1,
     176,    -1,   177,    -1,   178,    -1,   179,    -1,    -1,    17,
     247,    18,    -1,   249,    -1,   296,    -1,    35,   180,    36,
     253,   173,    -1,   247,    31,   247,    -1,   247,    37,   247,
      -1,    20,   247,    21,    -1,    43,   247,    -1,    41,   247,
      -1,    42,   247,    -1,    40,   247,    38,   247,    -1,    39,
     247,    38,   247,    -1,   250,    -1,   248,    -1,   180,    -1,
     248,    90,   180,    -1,   248,   104,   180,    -1,   188,    -1,
     189,    -1,   190,    -1,   191,    -1,   192,    -1,   193,    -1,
     194,    -1,   195,    -1,   251,    -1,   252,    -1,   247,    27,
     247,    -1,    19,    27,   247,    -1,   247,    44,   247,    -1,
      19,    44,   247,    -1,   254,    -1,   253,   254,    -1,   247,
      -1,   180,   149,   247,    -1,   180,    45,   247,    -1,    47,
     319,    46,   290,    -1,    48,    -1,    48,   257,    -1,   258,
      -1,   257,    33,   258,    -1,   246,   259,    -1,   180,    12,
     290,    -1,   180,   149,   247,    12,   290,    -1,   320,    12,
     290,    -1,   320,   149,   247,    12,   290,    -1,     7,    -1,
       7,   261,    -1,   262,    -1,   261,   262,    -1,   246,   180,
     267,   270,   271,   274,    -1,   246,   263,    -1,   180,   149,
     250,   180,   264,    46,   266,   271,   273,   275,    -1,   265,
      -1,   264,   265,    -1,    19,    -1,    17,   321,    18,    -1,
     290,    -1,    53,    -1,    54,    -1,    19,    -1,    17,   268,
      18,    -1,   321,   149,   247,    -1,   268,   146,   321,   149,
     247,    -1,    -1,   270,    -1,   180,   149,   247,    -1,   270,
     146,   180,   149,   247,    -1,   272,    -1,    -1,    49,   290,
      -1,   274,    -1,    -1,    50,   290,    -1,    51,   248,    -1,
      -1,     8,    -1,     8,   277,    -1,   278,    -1,   277,   278,
      -1,   246,   180,   267,   269,   281,   271,   274,    -1,   246,
     180,   149,   279,   180,   265,    46,   280,   271,   273,    -1,
     247,   102,   247,    -1,    19,   102,   247,    -1,   247,   102,
      19,    -1,    19,   102,    19,    -1,   226,    -1,    53,    -1,
      54,    -1,   282,    -1,    -1,    55,   283,    -1,   284,    -1,
     283,   284,    -1,   285,   248,    -1,   285,   248,   149,   247,
      -1,   284,   146,   248,    -1,   284,   146,   248,   149,   247,
      -1,    56,    -1,    57,    -1,    11,    -1,    11,   287,    -1,
     288,    -1,   287,    33,   288,    -1,   246,   310,    -1,    47,
     290,    -1,   290,    -1,   289,   146,   290,    -1,   294,    -1,
     291,    -1,   293,    -1,   292,    -1,   295,    -1,   296,    -1,
      17,   290,    18,    -1,   181,    -1,   180,    -1,   180,    98,
      -1,   290,    90,   180,    -1,   290,   104,   180,    -1,   290,
      91,    92,    -1,   290,    19,    -1,   290,    17,   289,    18,
      -1,   290,    17,   290,    86,   290,    18,    -1,   290,    90,
     323,    -1,   290,   153,   180,    -1,   290,   153,   323,    -1,
     290,   152,   319,    -1,   290,   152,    20,   325,    21,    -1,
      58,   297,    59,   290,    -1,   299,    -1,   301,    -1,   304,
      -1,   305,    -1,   134,   327,    13,   290,    -1,   135,   327,
      13,   290,    -1,   136,   324,    13,   290,    -1,    52,   324,
      13,   290,    -1,    29,    30,    -1,    29,   289,    30,    -1,
      29,   290,    31,   327,    30,    -1,    29,   290,    31,   327,
      13,   290,    30,    -1,    29,   290,    86,   290,    30,    -1,
      15,    -1,    20,   289,    21,    -1,    20,   290,    31,   325,
      21,    -1,    20,   290,    31,   325,    13,   290,    21,    -1,
     170,    -1,    29,   306,    30,    -1,    29,   307,    31,   327,
      30,    -1,    29,   307,    31,   327,    13,   290,    30,    -1,
      88,    17,   289,    18,    -1,    89,    17,   289,    18,    -1,
      93,   331,    13,   290,    -1,   168,    17,   290,    18,    -1,
      95,   249,    17,   290,    18,    -1,    95,    17,   290,   146,
     247,    18,    -1,    96,    17,   289,    18,    -1,    97,    17,
     248,   146,   290,    18,    -1,   150,   289,   151,    -1,   150,
     290,    31,   327,   151,    -1,   150,   290,    31,   327,    13,
     290,   151,    -1,   183,    -1,   182,    -1,   184,    -1,   185,
      -1,    92,    -1,   187,    -1,   160,    -1,   137,    -1,   186,
      -1,   298,    -1,   297,   146,   298,    -1,   259,    -1,   263,
      -1,    60,   290,    61,   290,   300,    -1,    63,   290,    -1,
      62,   290,    61,   290,   300,    -1,    64,   290,   149,   302,
     173,    -1,    64,   290,   149,   302,   146,    65,    27,   290,
     173,    -1,   303,    -1,   302,   146,   303,    -1,   321,    27,
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
     290,    87,   290,    -1,   290,    27,   226,    -1,   308,    31,
     290,    27,   226,    -1,   310,    -1,   309,   146,   310,    -1,
     180,   149,   247,    -1,   180,   149,   247,   100,   290,    -1,
     180,   149,   247,    59,   290,    -1,   312,    -1,   311,    33,
     312,    -1,   248,   100,   290,    -1,    60,   290,    61,   226,
     314,    63,   226,    -1,    60,   290,    61,   226,    63,   226,
      -1,    62,   290,    61,   226,    -1,   314,    62,   290,    61,
     226,    -1,    64,   290,   149,   316,   173,    -1,    64,   290,
     149,   316,   146,    65,    27,   226,   173,    -1,   317,    -1,
     316,   146,   317,    -1,   321,    27,   226,    -1,   281,   271,
     274,    -1,   322,    -1,   320,    -1,   323,    -1,    88,    17,
     321,   146,   319,    18,    -1,    89,    19,    -1,    89,    17,
     321,    18,    -1,   319,    -1,   321,   146,   319,    -1,   180,
      -1,    67,    -1,   294,    -1,   291,    -1,   293,    -1,   292,
      -1,   295,    -1,   296,    -1,    17,   290,    18,    -1,   325,
      -1,   326,    -1,   319,   118,   290,    -1,   319,   149,   247,
      -1,   328,    -1,   327,   146,   328,    -1,   329,    -1,   330,
      -1,   321,   118,   290,    -1,   321,   149,   247,    -1,   326,
      -1,   331,   146,   326,    -1
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
     327,   335,   339,   347,   351,   363,   371,   381,   387,   389,
     395,   404,   413,   418,   423,   428,   435,   443,   453,   463,
     471,   476,   483,   485,   489,   493,   495,   500,   503,   508,
     512,   515,   520,   525,   527,   534,   541,   549,   558,   569,
     574,   576,   580,   582,   586,   589,   592,   595,   599,   607,
     617,   621,   627,   629,   632,   634,   637,   639,   641,   643,
     646,   650,   654,   656,   659,   661,   665,   669,   671,   673,
     675,   677,   680,   682,   684,   686,   688,   690,   693,   695,
     698,   702,   704,   708,   713,   719,   724,   730,   732,   734,
     736,   738,   739,   743,   745,   747,   753,   757,   761,   765,
     768,   771,   774,   779,   784,   786,   788,   790,   794,   798,
     800,   802,   804,   806,   808,   810,   812,   814,   816,   818,
     822,   826,   830,   834,   836,   839,   841,   845,   849,   854,
     856,   859,   861,   865,   868,   872,   878,   882,   888,   890,
     893,   895,   898,   905,   908,   919,   921,   924,   926,   930,
     932,   934,   936,   938,   942,   946,   952,   953,   955,   959,
     965,   967,   968,   971,   973,   974,   977,   980,   981,   983,
     986,   988,   991,   999,  1010,  1014,  1018,  1022,  1026,  1028,
    1030,  1032,  1034,  1035,  1038,  1040,  1043,  1046,  1051,  1055,
    1061,  1063,  1065,  1067,  1070,  1072,  1076,  1079,  1082,  1084,
    1088,  1090,  1092,  1094,  1096,  1098,  1100,  1104,  1106,  1108,
    1111,  1115,  1119,  1123,  1126,  1131,  1138,  1142,  1146,  1150,
    1154,  1160,  1165,  1167,  1169,  1171,  1173,  1178,  1183,  1188,
    1193,  1196,  1200,  1206,  1214,  1220,  1222,  1226,  1232,  1240,
    1242,  1246,  1252,  1260,  1265,  1270,  1275,  1280,  1286,  1293,
    1298,  1305,  1309,  1315,  1323,  1325,  1327,  1329,  1331,  1333,
    1335,  1337,  1339,  1341,  1343,  1347,  1349,  1351,  1357,  1360,
    1366,  1372,  1382,  1384,  1388,  1392,  1395,  1398,  1401,  1404,
    1407,  1410,  1413,  1416,  1419,  1422,  1425,  1428,  1431,  1434,
    1437,  1440,  1443,  1446,  1449,  1452,  1456,  1460,  1464,  1468,
    1472,  1476,  1480,  1484,  1488,  1492,  1496,  1500,  1504,  1508,
    1512,  1516,  1520,  1524,  1528,  1532,  1536,  1540,  1544,  1548,
    1552,  1556,  1560,  1564,  1568,  1572,  1576,  1580,  1584,  1586,
    1590,  1594,  1598,  1604,  1606,  1610,  1614,  1620,  1626,  1628,
    1632,  1636,  1644,  1651,  1656,  1662,  1668,  1678,  1680,  1684,
    1688,  1692,  1694,  1696,  1698,  1705,  1708,  1713,  1715,  1719,
    1721,  1723,  1725,  1727,  1729,  1731,  1733,  1735,  1739,  1741,
    1743,  1747,  1751,  1753,  1757,  1759,  1761,  1765,  1769,  1771
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   336,   336,   344,   348,   355,   356,   357,   358,   359,
     363,   377,   411,   439,   474,   481,   489,   495,   501,   510,
     519,   525,   531,   537,   552,   559,   574,   592,   599,   606,
     616,   628,   640,   654,   668,   678,   686,   694,   705,   718,
     728,   736,   747,   753,   759,   765,   782,   795,   807,   813,
     829,   833,   840,   844,   851,   855,   868,   872,   879,   883,
     890,   895,   900,   905,   910,   916,   933,   957,   964,   971,
     978,   985,  1000,  1007,  1022,  1038,  1045,  1052,  1059,  1071,
    1078,  1089,  1103,  1114,  1128,  1145,  1162,  1178,  1197,  1201,
    1208,  1219,  1226,  1234,  1242,  1253,  1266,  1279,  1293,  1306,
    1318,  1327,  1337,  1341,  1349,  1369,  1373,  1380,  1384,  1388,
    1399,  1403,  1420,  1443,  1453,  1469,  1485,  1494,  1505,  1516,
    1531,  1537,  1546,  1552,  1561,  1567,  1573,  1582,  1588,  1592,
    1608,  1612,  1625,  1640,  1661,  1667,  1684,  1705,  1726,  1732,
    1741,  1754,  1764,  1780,  1800,  1806,  1818,  1834,  1838,  1842,
    1849,  1855,  1864,  1868,  1872,  1876,  1880,  1890,  1904,  1914,
    1926,  1941,  1947,  1956,  1976,  1997,  2001,  2048,  2055,  2062,
    2069,  2077,  2086,  2093,  2097,  2102,  2114,  2148,  2176,  2183,
    2190,  2197,  2204,  2212,  2220,  2232,  2243,  2249,  2255,  2264,
    2268,  2272,  2276,  2280,  2284,  2288,  2292,  2299,  2303,  2310,
    2319,  2331,  2340,  2352,  2358,  2367,  2375,  2385,  2398,  2426,
    2438,  2468,  2474,  2484,  2494,  2518,  2544,  2564,  2589,  2603,
    2632,  2638,  2647,  2651,  2661,  2692,  2698,  2707,  2711,  2718,
    2722,  2726,  2733,  2737,  2744,  2751,  2762,  2765,  2772,  2787,
    2805,  2806,  2810,  2814,  2815,  2819,  2829,  2834,  2840,  2853,
    2874,  2880,  2889,  2916,  2923,  2935,  2947,  2959,  2973,  2977,
    2981,  2988,  2989,  2993,  2997,  3003,  3023,  3027,  3031,  3035,
    3042,  3046,  3075,  3081,  3097,  3105,  3114,  3120,  3134,  3140,
    3183,  3195,  3200,  3204,  3209,  3221,  3227,  3231,  3236,  3241,
    3246,  3250,  3254,  3258,  3262,  3266,  3273,  3278,  3282,  3286,
    3294,  3299,  3306,  3310,  3314,  3318,  3323,  3331,  3339,  3347,
    3356,  3363,  3370,  3377,  3386,  3394,  3410,  3417,  3424,  3433,
    3438,  3445,  3452,  3461,  3468,  3482,  3497,  3507,  3517,  3531,
    3542,  3547,  3551,  3555,  3565,  3569,  3576,  3587,  3594,  3598,
    3605,  3619,  3628,  3639,  3645,  3654,  3655,  3659,  3691,  3698,
    3712,  3720,  3732,  3739,  3753,  3773,  3779,  3785,  3791,  3797,
    3803,  3809,  3815,  3821,  3827,  3833,  3839,  3845,  3851,  3857,
    3863,  3869,  3875,  3881,  3887,  3896,  3904,  3912,  3920,  3928,
    3936,  3944,  3952,  3960,  3968,  3976,  3984,  3992,  4000,  4008,
    4016,  4024,  4032,  4040,  4048,  4056,  4064,  4072,  4080,  4088,
    4096,  4104,  4112,  4120,  4128,  4136,  4144,  4152,  4163,  4169,
    4177,  4189,  4202,  4218,  4224,  4233,  4250,  4266,  4289,  4296,
    4314,  4321,  4330,  4342,  4353,  4367,  4374,  4385,  4391,  4402,
    4414,  4421,  4422,  4426,  4430,  4447,  4458,  4472,  4478,  4487,
    4501,  4511,  4521,  4526,  4531,  4536,  4541,  4546,  4557,  4558,
    4562,  4573,  4584,  4590,  4599,  4600,  4604,  4615,  4626,  4633
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
     205,   206,   207,   208,   209,   210,   211
  };

  private static final short yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 11894;
  private static final int yynnts_ = 120;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 41;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 212;

  private static final int yyuser_token_number_max_ = 466;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 922 of cmlskeleton.java.m4  */
/* Line 52 of "src/main/bison/cml.y"  */

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
  private CmlParserHelper util = null;

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

          //INode node = cmlParser.getDocument();
	  //DotGraphVisitor dgv = new DotGraphVisitor();
          //node.apply(dgv,null);

          //File dotFile = new File("generatedAST.gv");
          //java.io.FileWriter fw = new java.io.FileWriter(dotFile);
          //fw.write(dgv.getResultString());
          //fw.close();

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
    this.util = new CmlParserHelper(this.currentSource);
  }

  public PSource getDocument()
  {
    return currentSource;
  }




/* Line 922 of cmlskeleton.java.m4  */
/* Line 9024 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


