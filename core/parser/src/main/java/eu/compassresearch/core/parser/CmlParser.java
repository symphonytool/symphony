
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
/* Line 338 of "src/main/bison/cml.y"  */
    {
  List<SParagraphDefinition> paragraphs = (List<SParagraphDefinition>) ((yystack.valueAt (1-(1))));
  currentSource.setParagraphs(paragraphs);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 346 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseFirstListElement((PDefinition)((yystack.valueAt (1-(1)))));
};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 350 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseNextListElement(((yystack.valueAt (2-(1)))),(PDefinition)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 356 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 357 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 358 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 359 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 360 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 365 of "src/main/bison/cml.y"  */
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
/* Line 379 of "src/main/bison/cml.y"  */
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
/* Line 474 of "src/main/bison/cml.y"  */
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
/* Line 481 of "src/main/bison/cml.y"  */
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
/* Line 489 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASequentialCompositionProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 495 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AExternalChoiceProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 501 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInternalChoiceProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 510 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  yyval = new AGeneralisedParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 519 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (7-(1))));
  PProcess right = (PProcess)((yystack.valueAt (7-(7))));
  yyval = new AAlphabetisedParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (7-(3)))), (PExp)((yystack.valueAt (7-(5)))), right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 525 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 531 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 537 of "src/main/bison/cml.y"  */
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
/* Line 552 of "src/main/bison/cml.y"  */
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
/* Line 559 of "src/main/bison/cml.y"  */
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
/* Line 574 of "src/main/bison/cml.y"  */
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
/* Line 592 of "src/main/bison/cml.y"  */
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
/* Line 599 of "src/main/bison/cml.y"  */
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
/* Line 606 of "src/main/bison/cml.y"  */
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
/* Line 616 of "src/main/bison/cml.y"  */
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
/* Line 628 of "src/main/bison/cml.y"  */
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
/* Line 640 of "src/main/bison/cml.y"  */
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
/* Line 654 of "src/main/bison/cml.y"  */
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
/* Line 668 of "src/main/bison/cml.y"  */
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
/* Line 678 of "src/main/bison/cml.y"  */
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
/* Line 686 of "src/main/bison/cml.y"  */
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
/* Line 694 of "src/main/bison/cml.y"  */
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
/* Line 705 of "src/main/bison/cml.y"  */
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
/* Line 718 of "src/main/bison/cml.y"  */
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
/* Line 728 of "src/main/bison/cml.y"  */
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
/* Line 736 of "src/main/bison/cml.y"  */
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
/* Line 747 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 753 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 759 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 765 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 782 of "src/main/bison/cml.y"  */
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
/* Line 795 of "src/main/bison/cml.y"  */
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
/* Line 807 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = processParagraphList;
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 813 of "src/main/bison/cml.y"  */
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
/* Line 829 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 833 of "src/main/bison/cml.y"  */
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
/* Line 840 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 844 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 851 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseFirstListElement(util.caseActionDefinition(((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3))))));
};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 855 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseNextListElement(((yystack.valueAt (4-(1)))),util.caseActionDefinition(((yystack.valueAt (4-(2)))), ((yystack.valueAt (4-(3)))),((yystack.valueAt (4-(4))))));
};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 868 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 872 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 879 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{new Vector<ATypeSingleDeclaration>(), ((yystack.valueAt (1-(1))))};
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 883 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3))))};
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 890 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASkipAction(location);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 895 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AStopAction(location);
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 900 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AChaosAction(location);
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 905 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ADivAction(location);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 910 of "src/main/bison/cml.y"  */
    {
  PExp exp = (PExp)((yystack.valueAt (2-(2))));
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), exp.getLocation());
  yyval = new AWaitAction(location, exp);
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 916 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  PAction action = (PAction)((yystack.valueAt (3-(2))));
  yyval = new ABlockStatementAction(location, null, action);
};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 933 of "src/main/bison/cml.y"  */
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
/* Line 957 of "src/main/bison/cml.y"  */
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
/* Line 964 of "src/main/bison/cml.y"  */
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
/* Line 971 of "src/main/bison/cml.y"  */
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
/* Line 978 of "src/main/bison/cml.y"  */
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
/* Line 985 of "src/main/bison/cml.y"  */
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
/* Line 1000 of "src/main/bison/cml.y"  */
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
/* Line 1007 of "src/main/bison/cml.y"  */
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
/* Line 1022 of "src/main/bison/cml.y"  */
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
/* Line 1038 of "src/main/bison/cml.y"  */
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
/* Line 1045 of "src/main/bison/cml.y"  */
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
/* Line 1052 of "src/main/bison/cml.y"  */
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
/* Line 1059 of "src/main/bison/cml.y"  */
    {
  SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
  PAction action = (PAction)((yystack.valueAt (2-(1))));
  yyval = new AChannelRenamingAction(util.combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1071 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseMuAction(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(5)))),((yystack.valueAt (6-(6)))));
};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1078 of "src/main/bison/cml.y"  */
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
/* Line 1089 of "src/main/bison/cml.y"  */
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
/* Line 1103 of "src/main/bison/cml.y"  */
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
/* Line 1114 of "src/main/bison/cml.y"  */
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
/* Line 1128 of "src/main/bison/cml.y"  */
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
/* Line 1145 of "src/main/bison/cml.y"  */
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
/* Line 1162 of "src/main/bison/cml.y"  */
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
/* Line 1178 of "src/main/bison/cml.y"  */
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
/* Line 1197 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierToRefAction(((yystack.valueAt (1-(1)))));
};
  break;
    

  case 89:
  if (yyn == 89)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1201 of "src/main/bison/cml.y"  */
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
/* Line 1208 of "src/main/bison/cml.y"  */
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
/* Line 1219 of "src/main/bison/cml.y"  */
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
/* Line 1226 of "src/main/bison/cml.y"  */
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
/* Line 1234 of "src/main/bison/cml.y"  */
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
/* Line 1242 of "src/main/bison/cml.y"  */
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
/* Line 1253 of "src/main/bison/cml.y"  */
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
/* Line 1266 of "src/main/bison/cml.y"  */
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
/* Line 1279 of "src/main/bison/cml.y"  */
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
/* Line 1293 of "src/main/bison/cml.y"  */
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
/* Line 1306 of "src/main/bison/cml.y"  */
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
/* Line 1318 of "src/main/bison/cml.y"  */
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
/* Line 1327 of "src/main/bison/cml.y"  */
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
/* Line 1337 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 103:
  if (yyn == 103)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1341 of "src/main/bison/cml.y"  */
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
/* Line 1349 of "src/main/bison/cml.y"  */
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
/* Line 1369 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1373 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new AMultipleGeneralAssignmentStatementAction(location,
                                                     (List<? extends ASingleGeneralAssignmentStatementAction>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1380 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierLRPARENToCallAction(((yystack.valueAt (2-(1)))), ((yystack.valueAt (2-(2)))),new LinkedList<PExp>());
};
  break;
    

  case 108:
  if (yyn == 108)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1384 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierLRPARENToCallAction(((yystack.valueAt (4-(1)))), ((yystack.valueAt (4-(4)))),((yystack.valueAt (4-(3)))));
};
  break;
    

  case 109:
  if (yyn == 109)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1388 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 110:
  if (yyn == 110)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1399 of "src/main/bison/cml.y"  */
    {
  yyval = new AReturnStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))), null);
};
  break;
    

  case 111:
  if (yyn == 111)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1403 of "src/main/bison/cml.y"  */
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
/* Line 1420 of "src/main/bison/cml.y"  */
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
    

  case 113:
  if (yyn == 113)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1457 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 114:
  if (yyn == 114)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1467 of "src/main/bison/cml.y"  */
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
/* Line 1483 of "src/main/bison/cml.y"  */
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
/* Line 1499 of "src/main/bison/cml.y"  */
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
/* Line 1508 of "src/main/bison/cml.y"  */
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
/* Line 1519 of "src/main/bison/cml.y"  */
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
/* Line 1530 of "src/main/bison/cml.y"  */
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
/* Line 1545 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = new LinkedList<PAction>();
  actionList.add((PAction)((yystack.valueAt (1-(1)))));
  yyval = actionList;
};
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1551 of "src/main/bison/cml.y"  */
    {
  List<PAction> actionList = (List<PAction>)((yystack.valueAt (3-(1))));
  actionList.add((PAction)((yystack.valueAt (3-(3)))));
  yyval = actionList;
};
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1560 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add((PParametrisation)((yystack.valueAt (1-(1)))));
  yyval = plist;
};
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1566 of "src/main/bison/cml.y"  */
    {
  List<PParametrisation> plist = new LinkedList<PParametrisation>();
  plist.add(0,(PParametrisation)((yystack.valueAt (3-(3)))));
  yyval = plist;
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1575 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AValParametrisation(loc, decl);
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1581 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AResParametrisation(loc, decl);
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1587 of "src/main/bison/cml.y"  */
    {
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)((yystack.valueAt (2-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))), decl.getLocation());
  yyval = new AVresParametrisation(loc, decl);
};
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1596 of "src/main/bison/cml.y"  */
    {
  yyval = new AEnumerationRenameChannelExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
                                                           (CmlLexeme)((yystack.valueAt (3-(3))))),
                                        (List<? extends ARenamePair>)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 128:
  if (yyn == 128)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1602 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseRenameExpressionAComprehensionRenameChannelExp(((yystack.valueAt (7-(1)))),((yystack.valueAt (7-(2)))),((yystack.valueAt (7-(4)))),((yystack.valueAt (7-(6)))),null,((yystack.valueAt (7-(7)))));
};
  break;
    

  case 129:
  if (yyn == 129)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1606 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseRenameExpressionAComprehensionRenameChannelExp(((yystack.valueAt (9-(1)))),((yystack.valueAt (9-(2)))),((yystack.valueAt (9-(4)))),((yystack.valueAt (9-(6)))),((yystack.valueAt (9-(8)))),((yystack.valueAt (9-(9)))));
};
  break;
    

  case 130:
  if (yyn == 130)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1622 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseARenamePair(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 131:
  if (yyn == 131)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1626 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseRenameList(((yystack.valueAt (5-(1)))),((yystack.valueAt (5-(3)))),((yystack.valueAt (5-(5)))));
};
  break;
    

  case 132:
  if (yyn == 132)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1639 of "src/main/bison/cml.y"  */
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
/* Line 1654 of "src/main/bison/cml.y"  */
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
/* Line 1675 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDefinition> decls = new Vector<AChannelNameDefinition>();
  decls.add((AChannelNameDefinition)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1681 of "src/main/bison/cml.y"  */
    {
  List<AChannelNameDefinition> decls = (List<AChannelNameDefinition>)((yystack.valueAt (2-(1))));
  decls.add((AChannelNameDefinition)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 136:
  if (yyn == 136)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1698 of "src/main/bison/cml.y"  */
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
/* Line 1719 of "src/main/bison/cml.y"  */
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
/* Line 1740 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = new Vector<ATypeSingleDeclaration>();
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1746 of "src/main/bison/cml.y"  */
    {
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)((yystack.valueAt (2-(1))));
  decls.add((ATypeSingleDeclaration)((yystack.valueAt (2-(2)))));
  yyval = decls;
};
  break;
    

  case 140:
  if (yyn == 140)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1755 of "src/main/bison/cml.y"  */
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
/* Line 1768 of "src/main/bison/cml.y"  */
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
/* Line 1778 of "src/main/bison/cml.y"  */
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
/* Line 1794 of "src/main/bison/cml.y"  */
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
/* Line 1814 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1820 of "src/main/bison/cml.y"  */
    {
  List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (3-(1))));
  defs.add((AChansetDefinition)((yystack.valueAt (3-(3)))));
  yyval = defs;
};
  break;
    

  case 146:
  if (yyn == 146)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1832 of "src/main/bison/cml.y"  */
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
/* Line 1848 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1852 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1856 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1863 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1869 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
  defs.add((PDefinition)((yystack.valueAt (2-(1)))));
  yyval = defs;
};
  break;
    

  case 152:
  if (yyn == 152)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1878 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 153:
  if (yyn == 153)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1882 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1886 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1890 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1894 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 157:
  if (yyn == 157)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1904 of "src/main/bison/cml.y"  */
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
/* Line 1918 of "src/main/bison/cml.y"  */
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
/* Line 1928 of "src/main/bison/cml.y"  */
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
/* Line 1940 of "src/main/bison/cml.y"  */
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
/* Line 1955 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
  yyval = list;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1961 of "src/main/bison/cml.y"  */
    {
  List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
  list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
  yyval = list;
};
  break;
    

  case 163:
  if (yyn == 163)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1970 of "src/main/bison/cml.y"  */
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
/* Line 1990 of "src/main/bison/cml.y"  */
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
/* Line 2011 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseRecordTypeDefinition(((yystack.valueAt (4-(1)))), ((yystack.valueAt (4-(2)))), ((yystack.valueAt (4-(4)))));
};
  break;
    

  case 166:
  if (yyn == 166)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2015 of "src/main/bison/cml.y"  */
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
/* Line 2062 of "src/main/bison/cml.y"  */
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
/* Line 2069 of "src/main/bison/cml.y"  */
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
/* Line 2076 of "src/main/bison/cml.y"  */
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
/* Line 2083 of "src/main/bison/cml.y"  */
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
/* Line 2090 of "src/main/bison/cml.y"  */
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
/* Line 2100 of "src/main/bison/cml.y"  */
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
/* Line 2107 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 174:
  if (yyn == 174)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2111 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken quote = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteType(quote.location, false, null, quote);
};
  break;
    

  case 175:
  if (yyn == 175)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2116 of "src/main/bison/cml.y"  */
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
/* Line 2128 of "src/main/bison/cml.y"  */
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
/* Line 2162 of "src/main/bison/cml.y"  */
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
/* Line 2190 of "src/main/bison/cml.y"  */
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
/* Line 2197 of "src/main/bison/cml.y"  */
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
/* Line 2204 of "src/main/bison/cml.y"  */
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
/* Line 2211 of "src/main/bison/cml.y"  */
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
/* Line 2218 of "src/main/bison/cml.y"  */
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
/* Line 2226 of "src/main/bison/cml.y"  */
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
/* Line 2234 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 185:
  if (yyn == 185)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2246 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierToNamedType(((yystack.valueAt (1-(1)))));
};
  break;
    

  case 186:
  if (yyn == 186)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2257 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1))))));
  yyval = ids;
};
  break;
    

  case 187:
  if (yyn == 187)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2263 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = ids;
};
  break;
    

  case 188:
  if (yyn == 188)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2269 of "src/main/bison/cml.y"  */
    {
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(1))));
  ids.add(util.extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
  yyval = ids;
};
  break;
    

  case 189:
  if (yyn == 189)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2278 of "src/main/bison/cml.y"  */
    {
  yyval = new ABooleanBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 190:
  if (yyn == 190)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2282 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 191:
  if (yyn == 191)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2286 of "src/main/bison/cml.y"  */
    {
  yyval = new ANatOneNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 192:
  if (yyn == 192)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2290 of "src/main/bison/cml.y"  */
    {
  yyval = new AIntNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 193:
  if (yyn == 193)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2294 of "src/main/bison/cml.y"  */
    {
  yyval = new ARationalNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 194:
  if (yyn == 194)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2298 of "src/main/bison/cml.y"  */
    {
  yyval = new ARealNumericBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 195:
  if (yyn == 195)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2302 of "src/main/bison/cml.y"  */
    {
  yyval = new ACharBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 196:
  if (yyn == 196)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2306 of "src/main/bison/cml.y"  */
    {
  yyval = new ATokenBasicType(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), false);
};
  break;
    

  case 197:
  if (yyn == 197)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2313 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 198:
  if (yyn == 198)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2317 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 199:
  if (yyn == 199)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2324 of "src/main/bison/cml.y"  */
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
/* Line 2333 of "src/main/bison/cml.y"  */
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
/* Line 2345 of "src/main/bison/cml.y"  */
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
/* Line 2354 of "src/main/bison/cml.y"  */
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
/* Line 2366 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 204:
  if (yyn == 204)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2372 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> res = (List<AFieldField>)((yystack.valueAt (2-(1))));
  res.add((AFieldField)((yystack.valueAt (2-(2)))));
  yyval = res;
};
  break;
    

  case 205:
  if (yyn == 205)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2381 of "src/main/bison/cml.y"  */
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
/* Line 2389 of "src/main/bison/cml.y"  */
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
/* Line 2399 of "src/main/bison/cml.y"  */
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
/* Line 2412 of "src/main/bison/cml.y"  */
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
/* Line 2440 of "src/main/bison/cml.y"  */
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
/* Line 2452 of "src/main/bison/cml.y"  */
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
/* Line 2482 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = defs;
};
  break;
    

  case 212:
  if (yyn == 212)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2488 of "src/main/bison/cml.y"  */
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
/* Line 2498 of "src/main/bison/cml.y"  */
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
/* Line 2508 of "src/main/bison/cml.y"  */
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
/* Line 2532 of "src/main/bison/cml.y"  */
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
/* Line 2558 of "src/main/bison/cml.y"  */
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
/* Line 2578 of "src/main/bison/cml.y"  */
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
/* Line 2603 of "src/main/bison/cml.y"  */
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
/* Line 2617 of "src/main/bison/cml.y"  */
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
/* Line 2646 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = new Vector<PDefinition>();
  functionList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = functionList;
};
  break;
    

  case 221:
  if (yyn == 221)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2652 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> functionList = (List<PDefinition>)((yystack.valueAt (2-(1))));
  functionList.add((PDefinition)((yystack.valueAt (2-(2)))));
  yyval = functionList;
};
  break;
    

  case 222:
  if (yyn == 222)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2661 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseImplicitFunctionDefinition(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(4)))),((yystack.valueAt (6-(5)))),((yystack.valueAt (6-(6)))));
};
  break;
    

  case 223:
  if (yyn == 223)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2665 of "src/main/bison/cml.y"  */
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
/* Line 2675 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExplicitFunctionDefinition(((yystack.valueAt (10-(1)))),((yystack.valueAt (10-(3)))),((yystack.valueAt (10-(4)))),((yystack.valueAt (10-(5)))), 
					   ((yystack.valueAt (10-(7)))),((yystack.valueAt (10-(8)))),((yystack.valueAt (10-(9)))),((yystack.valueAt (10-(10)))));
};
  break;
    

  case 225:
  if (yyn == 225)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2706 of "src/main/bison/cml.y"  */
    {
  List<List<PPattern>> paramsList = new LinkedList<List<PPattern>>();
  paramsList.add((List<PPattern>)((yystack.valueAt (1-(1)))));
  yyval = paramsList;
};
  break;
    

  case 226:
  if (yyn == 226)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2712 of "src/main/bison/cml.y"  */
    {
  List<List<PPattern>> paramsList = (List<List<PPattern>>)((yystack.valueAt (2-(1))));
  paramsList.add((List<PPattern>)((yystack.valueAt (2-(2)))));
  yyval = paramsList;
};
  break;
    

  case 227:
  if (yyn == 227)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2721 of "src/main/bison/cml.y"  */
    {
  yyval =  new LinkedList<PPattern>();
};
  break;
    

  case 228:
  if (yyn == 228)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2725 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 229:
  if (yyn == 229)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2732 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 230:
  if (yyn == 230)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2736 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 231:
  if (yyn == 231)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2740 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 232:
  if (yyn == 232)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2747 of "src/main/bison/cml.y"  */
    {
  yyval = new Vector<APatternListTypePair>();
};
  break;
    

  case 233:
  if (yyn == 233)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2751 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 234:
  if (yyn == 234)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2758 of "src/main/bison/cml.y"  */
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
/* Line 2765 of "src/main/bison/cml.y"  */
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
/* Line 2775 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 237:
  if (yyn == 237)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2779 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 238:
  if (yyn == 238)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2786 of "src/main/bison/cml.y"  */
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
/* Line 2801 of "src/main/bison/cml.y"  */
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
/* Line 2818 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 241:
  if (yyn == 241)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2819 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 242:
  if (yyn == 242)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2823 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 243:
  if (yyn == 243)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2827 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 244:
  if (yyn == 244)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2828 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 245:
  if (yyn == 245)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2832 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 246:
  if (yyn == 246)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2843 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseMeasure(((yystack.valueAt (2-(2)))));
};
  break;
    

  case 247:
  if (yyn == 247)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2847 of "src/main/bison/cml.y"  */
    {
  yyval = null;
};
  break;
    

  case 248:
  if (yyn == 248)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2854 of "src/main/bison/cml.y"  */
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
/* Line 2867 of "src/main/bison/cml.y"  */
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
/* Line 2888 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (1-(1)))));
  yyval = opDefinitions;
};
  break;
    

  case 251:
  if (yyn == 251)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2894 of "src/main/bison/cml.y"  */
    {
  List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)((yystack.valueAt (2-(1))));
  opDefinitions.add((SOperationDefinition)((yystack.valueAt (2-(2)))));
  yyval = opDefinitions;
};
  break;
    

  case 252:
  if (yyn == 252)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2903 of "src/main/bison/cml.y"  */
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
/* Line 2930 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExplicitOperationDefinition(((yystack.valueAt (10-(1)))),((yystack.valueAt (10-(2)))),((yystack.valueAt (10-(3)))),((yystack.valueAt (10-(4)))),((yystack.valueAt (10-(5)))),((yystack.valueAt (10-(6)))),((yystack.valueAt (10-(7)))),((yystack.valueAt (10-(8)))),((yystack.valueAt (10-(9)))),((yystack.valueAt (10-(10)))) );
};
  break;
    

  case 254:
  if (yyn == 254)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2937 of "src/main/bison/cml.y"  */
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
/* Line 2949 of "src/main/bison/cml.y"  */
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
/* Line 2961 of "src/main/bison/cml.y"  */
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
/* Line 2973 of "src/main/bison/cml.y"  */
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
/* Line 2987 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 259:
  if (yyn == 259)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2991 of "src/main/bison/cml.y"  */
    {
  yyval = new ASubclassResponsibilityAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 260:
  if (yyn == 260)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 2995 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotYetSpecifiedStatementAction(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), null, null);
};
  break;
    

  case 261:
  if (yyn == 261)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3001 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 262:
  if (yyn == 262)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3002 of "src/main/bison/cml.y"  */
    { yyval = null; };
  break;
    

  case 263:
  if (yyn == 263)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3006 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (2-(2)))); };
  break;
    

  case 264:
  if (yyn == 264)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3011 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)((yystack.valueAt (1-(1)))));
  yyval = infoList;
};
  break;
    

  case 265:
  if (yyn == 265)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3017 of "src/main/bison/cml.y"  */
    {
  List<AExternalClause> infoList = (Vector<AExternalClause>)((yystack.valueAt (2-(1))));
  infoList.add((AExternalClause)((yystack.valueAt (2-(2)))));
  yyval = infoList;
};
  break;
    

  case 266:
  if (yyn == 266)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3037 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseVarInformation(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))),null);
};
  break;
    

  case 267:
  if (yyn == 267)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3041 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseVarInformation(((yystack.valueAt (4-(1)))),((yystack.valueAt (4-(2)))),((yystack.valueAt (4-(4)))));
};
  break;
    

  case 268:
  if (yyn == 268)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3045 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseMultiVarInformation(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))),null);
};
  break;
    

  case 269:
  if (yyn == 269)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3049 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseMultiVarInformation(((yystack.valueAt (5-(1)))),((yystack.valueAt (5-(3)))),((yystack.valueAt (5-(5)))));
};
  break;
    

  case 270:
  if (yyn == 270)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3056 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.READ);
};
  break;
    

  case 271:
  if (yyn == 271)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3060 of "src/main/bison/cml.y"  */
    {
  yyval = new LexToken(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), VDMToken.WRITE);
};
  break;
    

  case 272:
  if (yyn == 272)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3089 of "src/main/bison/cml.y"  */
    {
    AStateParagraphDefinition state = new AStateParagraphDefinition();
    state.setLocation(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
    yyval  = state;
};
  break;
    

  case 273:
  if (yyn == 273)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3095 of "src/main/bison/cml.y"  */
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
/* Line 3111 of "src/main/bison/cml.y"  */
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
/* Line 3119 of "src/main/bison/cml.y"  */
    {
  AStateParagraphDefinition stateDef = (AStateParagraphDefinition)((yystack.valueAt (3-(1))));
  stateDef.getStateDefs().add((PDefinition)((yystack.valueAt (3-(3)))));
  yyval = stateDef;
};
  break;
    

  case 276:
  if (yyn == 276)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3128 of "src/main/bison/cml.y"  */
    {
  AAssignmentDefinition adef = (AAssignmentDefinition)((yystack.valueAt (2-(2))));
  adef.setAccess((AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1)))));
  yyval = adef;
};
  break;
    

  case 277:
  if (yyn == 277)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3134 of "src/main/bison/cml.y"  */
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
/* Line 3148 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)((yystack.valueAt (1-(1)))));
  yyval = exps;
};
  break;
    

  case 279:
  if (yyn == 279)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3154 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(1))));
  exps.add((PExp)((yystack.valueAt (3-(3)))));
  yyval = exps;
};
  break;
    

  case 280:
  if (yyn == 280)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3197 of "src/main/bison/cml.y"  */
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
/* Line 3209 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanConstExp(lit.location, lit);
};
  break;
    

  case 282:
  if (yyn == 282)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3214 of "src/main/bison/cml.y"  */
    {
    yyval = new ANilExp(((LexKeywordToken)((yystack.valueAt (1-(1))))).location);
};
  break;
    

  case 283:
  if (yyn == 283)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3218 of "src/main/bison/cml.y"  */
    {
    LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
    yyval = new ACharLiteralExp(lit.location, lit);
};
  break;
    

  case 284:
  if (yyn == 284)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3223 of "src/main/bison/cml.y"  */
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
/* Line 3235 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuoteLiteralExp(lit.location, lit);
};
  break;
    

  case 286:
  if (yyn == 286)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3241 of "src/main/bison/cml.y"  */
    {
  yyval = new ABracketedExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3))))), (PExp)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 287:
  if (yyn == 287)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3245 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new ASelfExp(name.location, name);
};
  break;
    

  case 288:
  if (yyn == 288)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3250 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AVariableExp(name.location, name,"");
};
  break;
    

  case 289:
  if (yyn == 289)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3255 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = util.extractLexNameToken((CmlLexeme)((yystack.valueAt (2-(1)))), true);
  yyval = new AVariableExp(name.location, name,"");
};
  break;
    

  case 290:
  if (yyn == 290)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3260 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpDotIdentifier((PExp)((yystack.valueAt (3-(1)))), util.extractLexIdentifierToken(((yystack.valueAt (3-(3))))));
};
  break;
    

  case 291:
  if (yyn == 291)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3264 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpDotIdentifier((PExp)((yystack.valueAt (3-(1)))), util.extractLexIdentifierToken(((yystack.valueAt (3-(3))))));
};
  break;
    

  case 292:
  if (yyn == 292)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3268 of "src/main/bison/cml.y"  */
    {
  yyval = util.expressionDotHashNumeralToFieldNumberExp(((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3)))));
};
  break;
    

  case 293:
  if (yyn == 293)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3272 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpressionApply(((yystack.valueAt (2-(1)))), new LinkedList<PExp>(), ((yystack.valueAt (2-(2)))));
};
  break;
    

  case 294:
  if (yyn == 294)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3276 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpressionApply(((yystack.valueAt (4-(1)))), ((yystack.valueAt (4-(3)))), ((yystack.valueAt (4-(4)))));
};
  break;
    

  case 295:
  if (yyn == 295)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3280 of "src/main/bison/cml.y"  */
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
/* Line 3287 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpDotMatchValue(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 297:
  if (yyn == 297)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3292 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpBangIdentifier(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 298:
  if (yyn == 298)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3296 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpBangMatchValue(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 299:
  if (yyn == 299)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3300 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpQuestionPattern(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 300:
  if (yyn == 300)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3304 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpQuestionSetBind(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 301:
  if (yyn == 301)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3309 of "src/main/bison/cml.y"  */
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
/* Line 3316 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 303:
  if (yyn == 303)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3320 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 304:
  if (yyn == 304)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3324 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 305:
  if (yyn == 305)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3328 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 306:
  if (yyn == 306)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3333 of "src/main/bison/cml.y"  */
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
/* Line 3341 of "src/main/bison/cml.y"  */
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
/* Line 3349 of "src/main/bison/cml.y"  */
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
/* Line 3357 of "src/main/bison/cml.y"  */
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
/* Line 3366 of "src/main/bison/cml.y"  */
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
/* Line 3373 of "src/main/bison/cml.y"  */
    {
  LexLocation location = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  List<PExp> members = (List<PExp>)((yystack.valueAt (3-(2))));
  yyval = new ASetEnumSetExp(location, members);
};
  break;
    

  case 312:
  if (yyn == 312)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3380 of "src/main/bison/cml.y"  */
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
/* Line 3387 of "src/main/bison/cml.y"  */
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
/* Line 3396 of "src/main/bison/cml.y"  */
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
/* Line 3404 of "src/main/bison/cml.y"  */
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
/* Line 3420 of "src/main/bison/cml.y"  */
    {
  List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new ASeqEnumSeqExp(loc, exps);
};
  break;
    

  case 317:
  if (yyn == 317)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3427 of "src/main/bison/cml.y"  */
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
/* Line 3434 of "src/main/bison/cml.y"  */
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
/* Line 3443 of "src/main/bison/cml.y"  */
    {
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
  yyval = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
};
  break;
    

  case 320:
  if (yyn == 320)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3448 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(2))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new AMapEnumMapExp(loc, maplets);
};
  break;
    

  case 321:
  if (yyn == 321)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3455 of "src/main/bison/cml.y"  */
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
/* Line 3462 of "src/main/bison/cml.y"  */
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
/* Line 3471 of "src/main/bison/cml.y"  */
    {
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
  LexLocation loc = util.extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))), (CmlLexeme)((yystack.valueAt (4-(4)))));
  yyval = new ATupleExp(loc, exprs);
};
  break;
    

  case 324:
  if (yyn == 324)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3478 of "src/main/bison/cml.y"  */
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
/* Line 3492 of "src/main/bison/cml.y"  */
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
/* Line 3507 of "src/main/bison/cml.y"  */
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
/* Line 3517 of "src/main/bison/cml.y"  */
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
/* Line 3527 of "src/main/bison/cml.y"  */
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
/* Line 3541 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpressionPrecondition(((yystack.valueAt (4-(1)))),((yystack.valueAt (4-(3)))),((yystack.valueAt (4-(4)))));
};
  break;
    

  case 330:
  if (yyn == 330)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3552 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseExpressionIsOfBaseClass(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(5)))),((yystack.valueAt (6-(6)))));
};
  break;
    

  case 331:
  if (yyn == 331)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3557 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseAEnumChansetSetExp(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 332:
  if (yyn == 332)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3561 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseACompChansetSetExp(((yystack.valueAt (5-(1)))), ((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))), null, ((yystack.valueAt (5-(5)))));
};
  break;
    

  case 333:
  if (yyn == 333)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3565 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseACompChansetSetExp(((yystack.valueAt (7-(1)))), ((yystack.valueAt (7-(2)))),((yystack.valueAt (7-(4)))), ((yystack.valueAt (7-(6)))), ((yystack.valueAt (7-(7)))));
};
  break;
    

  case 334:
  if (yyn == 334)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3575 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.FALSE, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 335:
  if (yyn == 335)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3579 of "src/main/bison/cml.y"  */
    {
  yyval = new LexBooleanToken(VDMToken.TRUE, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 336:
  if (yyn == 336)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3586 of "src/main/bison/cml.y"  */
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
/* Line 3597 of "src/main/bison/cml.y"  */
    {
  yyval = new LexKeywordToken(VDMToken.NIL, util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 338:
  if (yyn == 338)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3604 of "src/main/bison/cml.y"  */
    {
  yyval = util.CmlLexemeToLexIntegerToken(((yystack.valueAt (1-(1)))));
};
  break;
    

  case 339:
  if (yyn == 339)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3608 of "src/main/bison/cml.y"  */
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
/* Line 3615 of "src/main/bison/cml.y"  */
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
/* Line 3629 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexStringToken(lit.substring(1, lit.length()-2),
                          util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 342:
  if (yyn == 342)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3638 of "src/main/bison/cml.y"  */
    {
  String lit = ((CmlLexeme)((yystack.valueAt (1-(1))))).getValue();
  yyval = new LexQuoteToken(lit.substring(1, lit.length()-2),
                         util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 343:
  if (yyn == 343)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3649 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 344:
  if (yyn == 344)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3655 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(1))));
  defs.add((PDefinition)((yystack.valueAt (3-(3)))));
  yyval = defs;
};
  break;
    

  case 345:
  if (yyn == 345)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3663 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 346:
  if (yyn == 346)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3664 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 347:
  if (yyn == 347)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3669 of "src/main/bison/cml.y"  */
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
/* Line 3701 of "src/main/bison/cml.y"  */
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
/* Line 3708 of "src/main/bison/cml.y"  */
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
/* Line 3722 of "src/main/bison/cml.y"  */
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
/* Line 3730 of "src/main/bison/cml.y"  */
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
/* Line 3742 of "src/main/bison/cml.y"  */
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
/* Line 3749 of "src/main/bison/cml.y"  */
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
/* Line 3763 of "src/main/bison/cml.y"  */
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
/* Line 3783 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryPlusUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 356:
  if (yyn == 356)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3789 of "src/main/bison/cml.y"  */
    {
  yyval = new AUnaryMinusUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 357:
  if (yyn == 357)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3795 of "src/main/bison/cml.y"  */
    {
  yyval = new AAbsoluteUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 358:
  if (yyn == 358)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3801 of "src/main/bison/cml.y"  */
    {
  yyval = new AFloorUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                             ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                          (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 359:
  if (yyn == 359)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3807 of "src/main/bison/cml.y"  */
    {
  yyval = new ANotUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                           ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                        (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 360:
  if (yyn == 360)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3813 of "src/main/bison/cml.y"  */
    {
  yyval = new ACardinalityUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                   ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                                (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 361:
  if (yyn == 361)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3819 of "src/main/bison/cml.y"  */
    {
  yyval = new APowerSetUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 362:
  if (yyn == 362)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3825 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistUnionUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 363:
  if (yyn == 363)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3831 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistIntersectUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                     ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                                  (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 364:
  if (yyn == 364)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3837 of "src/main/bison/cml.y"  */
    {
  yyval = new AHeadUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                         (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 365:
  if (yyn == 365)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3843 of "src/main/bison/cml.y"  */
    {
  yyval = new ATailUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                            ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                         (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 366:
  if (yyn == 366)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3849 of "src/main/bison/cml.y"  */
    {
  yyval = new ALenUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                           ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                        (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 367:
  if (yyn == 367)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3855 of "src/main/bison/cml.y"  */
    {
  yyval = new AElementsUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 368:
  if (yyn == 368)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3861 of "src/main/bison/cml.y"  */
    {
  yyval = new AIndicesUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                               ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                            (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 369:
  if (yyn == 369)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3867 of "src/main/bison/cml.y"  */
    {
  yyval = new AReverseUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                               ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                            (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 370:
  if (yyn == 370)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3873 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistConcatUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 371:
  if (yyn == 371)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3879 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapDomainUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 372:
  if (yyn == 372)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3885 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapRangeUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                             (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 373:
  if (yyn == 373)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3891 of "src/main/bison/cml.y"  */
    {
  yyval = new ADistMergeUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                 ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                              (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 374:
  if (yyn == 374)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3897 of "src/main/bison/cml.y"  */
    {
  yyval = new AMapInverseUnaryExp(util.extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
                                                  ((PExp)((yystack.valueAt (2-(2))))).getLocation()),
                               (PExp)((yystack.valueAt (2-(2)))));
};
  break;
    

  case 375:
  if (yyn == 375)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 3906 of "src/main/bison/cml.y"  */
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
/* Line 3914 of "src/main/bison/cml.y"  */
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
/* Line 3922 of "src/main/bison/cml.y"  */
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
/* Line 3930 of "src/main/bison/cml.y"  */
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
/* Line 3938 of "src/main/bison/cml.y"  */
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
/* Line 3946 of "src/main/bison/cml.y"  */
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
/* Line 3954 of "src/main/bison/cml.y"  */
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
/* Line 3962 of "src/main/bison/cml.y"  */
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
/* Line 3970 of "src/main/bison/cml.y"  */
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
/* Line 3978 of "src/main/bison/cml.y"  */
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
/* Line 3986 of "src/main/bison/cml.y"  */
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
/* Line 3994 of "src/main/bison/cml.y"  */
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
/* Line 4002 of "src/main/bison/cml.y"  */
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
/* Line 4010 of "src/main/bison/cml.y"  */
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
/* Line 4018 of "src/main/bison/cml.y"  */
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
/* Line 4026 of "src/main/bison/cml.y"  */
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
/* Line 4034 of "src/main/bison/cml.y"  */
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
/* Line 4042 of "src/main/bison/cml.y"  */
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
/* Line 4050 of "src/main/bison/cml.y"  */
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
/* Line 4058 of "src/main/bison/cml.y"  */
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
/* Line 4066 of "src/main/bison/cml.y"  */
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
/* Line 4074 of "src/main/bison/cml.y"  */
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
/* Line 4082 of "src/main/bison/cml.y"  */
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
/* Line 4090 of "src/main/bison/cml.y"  */
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
/* Line 4098 of "src/main/bison/cml.y"  */
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
/* Line 4106 of "src/main/bison/cml.y"  */
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
/* Line 4114 of "src/main/bison/cml.y"  */
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
/* Line 4122 of "src/main/bison/cml.y"  */
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
/* Line 4130 of "src/main/bison/cml.y"  */
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
/* Line 4138 of "src/main/bison/cml.y"  */
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
/* Line 4146 of "src/main/bison/cml.y"  */
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
/* Line 4154 of "src/main/bison/cml.y"  */
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
/* Line 4162 of "src/main/bison/cml.y"  */
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
/* Line 4173 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)((yystack.valueAt (1-(1)))));
  yyval = res;
};
  break;
    

  case 409:
  if (yyn == 409)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4179 of "src/main/bison/cml.y"  */
    {
  List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(1))));
  maplets.add((AMapletExp)((yystack.valueAt (3-(3)))));
  yyval = maplets;
};
  break;
    

  case 410:
  if (yyn == 410)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4187 of "src/main/bison/cml.y"  */
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
/* Line 4199 of "src/main/bison/cml.y"  */
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
/* Line 4212 of "src/main/bison/cml.y"  */
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
/* Line 4228 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (1-(1)))));
  yyval = assignmentDefs;
};
  break;
    

  case 414:
  if (yyn == 414)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4234 of "src/main/bison/cml.y"  */
    {
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)((yystack.valueAt (3-(1))));
  assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (3-(3)))));
  yyval = assignmentDefs;
};
  break;
    

  case 415:
  if (yyn == 415)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4243 of "src/main/bison/cml.y"  */
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
/* Line 4260 of "src/main/bison/cml.y"  */
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
/* Line 4276 of "src/main/bison/cml.y"  */
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
/* Line 4299 of "src/main/bison/cml.y"  */
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
/* Line 4306 of "src/main/bison/cml.y"  */
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
/* Line 4324 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseDottedIdentifierToAssignmentStm(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3)))));
};
  break;
    

  case 421:
  if (yyn == 421)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4331 of "src/main/bison/cml.y"  */
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
/* Line 4340 of "src/main/bison/cml.y"  */
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
/* Line 4352 of "src/main/bison/cml.y"  */
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
/* Line 4363 of "src/main/bison/cml.y"  */
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
/* Line 4377 of "src/main/bison/cml.y"  */
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
/* Line 4384 of "src/main/bison/cml.y"  */
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
/* Line 4395 of "src/main/bison/cml.y"  */
    {
  List<ACaseAlternativeAction> casesList = new LinkedList<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)((yystack.valueAt (1-(1)))));
  yyval = new ACasesStatementAction(null, null, casesList, null);
};
  break;
    

  case 428:
  if (yyn == 428)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4401 of "src/main/bison/cml.y"  */
    {
  ACasesStatementAction cases = (ACasesStatementAction)((yystack.valueAt (3-(1))));
  cases.getCases().add((ACaseAlternativeAction)((yystack.valueAt (3-(3)))));
  yyval = cases;
};
  break;
    

  case 429:
  if (yyn == 429)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4412 of "src/main/bison/cml.y"  */
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
/* Line 4424 of "src/main/bison/cml.y"  */
    {
  yyval = util.caseImplicitOperationBody(((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(2)))), ((yystack.valueAt (3-(3)))));
};
  break;
    

  case 431:
  if (yyn == 431)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4430 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 432:
  if (yyn == 432)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4431 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 433:
  if (yyn == 433)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4436 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 434:
  if (yyn == 434)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4440 of "src/main/bison/cml.y"  */
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
/* Line 4457 of "src/main/bison/cml.y"  */
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
/* Line 4468 of "src/main/bison/cml.y"  */
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
/* Line 4482 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)((yystack.valueAt (1-(1)))));
  yyval = patterns;
};
  break;
    

  case 438:
  if (yyn == 438)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4488 of "src/main/bison/cml.y"  */
    {
  List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
  patterns.add((PPattern)((yystack.valueAt (3-(3)))));
  yyval = patterns;
};
  break;
    

  case 439:
  if (yyn == 439)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4497 of "src/main/bison/cml.y"  */
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
/* Line 4511 of "src/main/bison/cml.y"  */
    {
  yyval = new AIgnorePattern(util.extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),
                          new LinkedList<PDefinition>(),
                          true);
};
  break;
    

  case 441:
  if (yyn == 441)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4521 of "src/main/bison/cml.y"  */
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
/* Line 4531 of "src/main/bison/cml.y"  */
    {
  LexBooleanToken lit = (LexBooleanToken)((yystack.valueAt (1-(1))));
  yyval = new ABooleanPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 443:
  if (yyn == 443)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4536 of "src/main/bison/cml.y"  */
    {
  LexKeywordToken lit = (LexKeywordToken)((yystack.valueAt (1-(1))));
  yyval = new ANilPattern(lit.location, new LinkedList<PDefinition>(), true);
};
  break;
    

  case 444:
  if (yyn == 444)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4541 of "src/main/bison/cml.y"  */
    {
  LexCharacterToken lit = (LexCharacterToken)((yystack.valueAt (1-(1))));
  yyval = new ACharacterPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 445:
  if (yyn == 445)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4546 of "src/main/bison/cml.y"  */
    {
  LexStringToken lit = (LexStringToken)((yystack.valueAt (1-(1))));
  yyval = new AStringPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 446:
  if (yyn == 446)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4551 of "src/main/bison/cml.y"  */
    {
  LexQuoteToken lit = (LexQuoteToken)((yystack.valueAt (1-(1))));
  yyval = new AQuotePattern(lit.location, new LinkedList<PDefinition>(), true, lit);
};
  break;
    

  case 447:
  if (yyn == 447)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4556 of "src/main/bison/cml.y"  */
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
/* Line 4566 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 449:
  if (yyn == 449)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4567 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 450:
  if (yyn == 450)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4572 of "src/main/bison/cml.y"  */
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
/* Line 4583 of "src/main/bison/cml.y"  */
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
/* Line 4594 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)((yystack.valueAt (1-(1)))));
  yyval = binds;
};
  break;
    

  case 453:
  if (yyn == 453)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4600 of "src/main/bison/cml.y"  */
    {
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (3-(1))));
  binds.add((PMultipleBind)((yystack.valueAt (3-(3)))));
  yyval = binds;
};
  break;
    

  case 454:
  if (yyn == 454)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4608 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 455:
  if (yyn == 455)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4609 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 456:
  if (yyn == 456)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 4614 of "src/main/bison/cml.y"  */
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
/* Line 4625 of "src/main/bison/cml.y"  */
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
/* Line 4636 of "src/main/bison/cml.y"  */
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
/* Line 4643 of "src/main/bison/cml.y"  */
    {
  List<ATypeBind> list = (List<ATypeBind>)((yystack.valueAt (3-(1))));
  ATypeBind bind = (ATypeBind)((yystack.valueAt (3-(3))));
  list.add(bind);
  yyval = list;
};
  break;
    



/* Line 374 of cmlskeleton.java.m4  */
/* Line 7525 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
       123,   512,  -115,   -47,   512,  1437,   -43,    49,   207,   123,
    -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,
    -657,  -657,  -657,   276,  -657,   166,   156,  -115,  -657,  -657,
     360,   426,  -657,   284,   512,  -657,  1638,   432,  -657,     7,
     463,  -657,  -657,   512,    83,   308,  1873,  -657,  2703,   -47,
     195,  -657,  -657,  2703,   475,   384,  -657,  -657,  -657,    21,
    -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,
    -657,  -657,  -657,    32,  -657,   630,   339,   335,    22,  -657,
    1873,  1900,   156,  -657,  1873,    91,  1873,   375,  1873,  1873,
    1873,  1873,  1873,  -657,  -657,  -657,  -657,  -657,  -657,  -657,
    -657,  -657,   985,   357,  -657,  -657,  -657,  -657,  -657,  -657,
    2703,  2703,  2295,  1197,  1985,  2703,  2703,  2703,  2703,  2703,
    2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,
    2703,  2703,  2703,  2703,  2703,  2703,  2703,   499,   553,  1197,
      99,   570,   581,  1197,  1197,  1197,  2703,   595,  -657,   517,
    -657, 11124,  -657,  -657,  -657,  -657,  -657,  -657,  -657,  -657,
    -657,  -657,  -657,  1197,  -657,  1873,   440,  5257,  1197,  1197,
    -657,  2703,  1873,  2703,  1873,  -657,   789,   609,   447,   447,
     447,   308,   447,   447,  2703,   962,   323,  3202,    -3,  -657,
     960,   -11,   985,  1087,  -657,   798,  1873,  1873,   860,   597,
     736,   920,   602,   602,   602,  1873,  1873,  1873,  1873,   454,
     472,  5379,   137,  5502,  -657,    20,  4522,    70,   622,  -657,
    -657,   331,  -657,  -657,   647,  -657,  -657,    46,  -657,  -657,
     -24,  -657,  5624,  5746,   619,   619,   619,   619, 11575,   619,
     619,   619,   619,   619,   619,   619,   619,   619,   619,   619,
     619,   619,   619,   983,  2703,  2703,   529,  -657,    17,  2703,
     651,  2703,   501,  -657,   259,    28,  -657,  -657,  -657,    29,
     680,    54,  5869,  2703,  -657,  2703,  2703,  -657,  2703,  2703,
    2703,   519,   604,   520,  2703,  2703,  2703,  2703,  2703,  2703,
    2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,
    2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,  2703,
    2703,  2703,  2703,  1197,   678,   173,   204,   985,   533,   558,
     -17,  -657,   562,   183, 11124,   703, 11124,   774,   512,   630,
     -25,   545,   789,  -657,  -657,  -657,  -657,  -657,   539,   396,
     101,  -657,  -657,   336,   362,    -1,   412,   480,  5991,   540,
     542,  2525,  1401,  -657,  -657,  2703,  -657,   127,   127,   127,
    2703,   127,   127,  2703,  2703,  2703,   127,   127,  2703,  2703,
    2703,  -657,   127,  -657,  1197,  -657,  1873,  1873,  -657,  -657,
    -657,   985,   985,  -657,  1900,  1873,  1873,   985,   602,   602,
     985,  -657,  -657,  -657,  -657,  2703,  1197,  -657,  1197,  2703,
    2703,  -657,  2703,  1197,  2703,  1873,  2703,  1873,  2703,  1985,
    2703,  1197,   186, 11124,   215,  2703,  1197,  6113,  2703,   254,
     420,  2703,  1197,  1873,  2703,  1197,  2703,  2703,  -657,  1197,
    6235, 11625,   263,  6357,  2030, 11698, 11698,  -657,  -657,  -657,
    -657,  2030,  2030,  2030,  2030, 11625, 11625, 11625, 11625, 11625,
   11448, 11498, 11368, 11246, 11625, 11625, 11625, 11625,  3366,  3366,
     983,  3488,  1627,  1627,   983,   983,   619,   619,   289,    24,
     607,  -657,  -657,  -657,  -657,  1197,  1873,   532,  1873,  2703,
     551,   682,  -657,  1197,  -657,  2703,  2703,   564,   512,  -657,
    -657,  2703,   569,   708,  -657,  -657,  -657,   789,  2703,   447,
     127,   447,   127,   127,   127,   397,   127,   447,   730,   576,
     738,   582,   447,   447,   447,  2834,  2431,  -657,  -657,  -657,
    -657,  2703,   447,   447,  1985,  2703,  2703,   744,   546,  2703,
    2703,  2703,  2703,  1065,  2703,  2539,   400,  -657,  -657,  -657,
    2525,  -657,   264,   611,   184,   424,  3139,  4155,  1141,   555,
    6479,   538,  6601,  4277,   245,   324, 11124, 11124, 11124,  3202,
     707,   985,   985,  1835,   602,   602, 11124,   607,    40,    27,
    6725, 11124,  6847,  -657,    78, 11625,   985, 11246, 11124,  -657,
    4644,   130,  -657,     1,  -657,  -657, 11246,  -657,  1873,  6969,
    -657,  2703, 11124,  -657,   985, 11246,  -657, 11246, 11246,    16,
    -657,  -657,  2703,   247,   985,  1197,  -657,    94,  -657,   985,
   11124,   616,  2703,  -657,   746, 11124, 11124,   309,  -657, 11124,
     620,  -657,   187,   606, 11124,  -657,  -657,  -657,  -657,  -657,
    -657,  1674,  2703,  -657,  -657,   513,  2703,   756,  2993,   763,
     527,   528,   560,   569,   308,   308,   308,   156,  1971,   561,
    -657,     2,    63,   739,  -657,  7091,   766, 11124,   573,   583,
     -14,  4766,    -5,  7213,   501,  2703,  -657,  7335,  7457,    76,
    7579,    -4,  1197,   777,   266,   732,  3422,  2525,  2525,  2525,
    2703,  2525,  2525,  2703,  2703,  2703,  2525,  2525,  2703,  2703,
    2703,  -657,  -657,  2703,  -657,  2567,  2895,  -657,  2703,   127,
     127,  -657,  2703,  2703,   127,  2703,  -657,  2703,  -657,  2703,
    -657,  -657,  2703,  -657,  2703,  2703,  -657,  2739,  -657,  2703,
     812,  -657,  7701,  2703,  -657,  7823,  1873,   288,  2159,  -657,
    1873, 11124,  -657,  1917,   440,  1873,  -657,  -657,   778,  7945,
     127, 11124,  2703,  -657,  1385,     3,  2993,  2525,  2525,   784,
      77,  -657,  -657,  -657,  -657,  -657,  2525,   505,  2525,  -657,
    -657,    63,   666,   501,   682,     4,  -657,   793,  2525,  2525,
    2525,  2525,  2703,  -657,  1197,   -44,   514,  -657,  8067,   447,
     447,   800,  -657,   702,  2703,  2703,  2703,  2525,  1273,   285,
     934,  4033,   891,   574,  4889,  8190,  4399,   515,   526, 11124,
   11124, 11124,   305,  2703, 11124,  -657,  8312,   324,  1141,  8434,
    8557,   245, 11124,  8679,  8803,  8927,  9049, 11246,   794,  -657,
   11124,  -657,  -657,  9171,  -657,   985,  -657,  -657,  -657,   739,
   11124,   985,   435,   490,   648,   772,   698,   531,  2703,   438,
    -657, 11124,  2525,  -657,  -657,  -657,  2703,  2525,   569,  3086,
    -657,  3131,   666,   501,   408,  -657,  2525,  2525,  2703,  -657,
    1385,  1385,  2970,  9293,   138,  -657,    69,  2703,  -657,   501,
    -657,   586,   613,  2525,  2703,  9415,  3544,  3666,  1385,  2703,
    2703,  2525,  2703,  2525,  2703,  2525,  -657, 11124,   127,  2703,
    1197,  -657,  -657,  -657,  2703,  2703,  -657,   682,  1958,  2015,
     532,   739,  2703,  2703,  9537,   407,  -657,  1385,  9659,  3193,
    -657,   828,   831,   448,  1873,  -657,   574,  5012,  2703,  2525,
     251,  2525,  2776,  -657,  2525,  -657,   830,  2525,  1385,   330,
    3788,  2703,  2525,  2525,  9781,  9903,   526,  5135,   891, 10025,
     515,  1141, 11124,   133,  4644,  3071,   801,  -657,    91,   985,
      91,   985,   805,   682, 11124, 11124,  -657,  2525,  -657,  2703,
    2703,  1873,   985,  2525,  2703, 10147,  1385,  2703,  2525,  1385,
     826,  -657,  1385,  2703,  -657,  -657,  2525,  2525,  3300,  1385,
    1385,  2703,  2525,  2703,  2525,  2525,  2703,  -657,  -657,  -657,
     501,  -657,  2247,  -657,  -657,   341,   350,   985,  -657, 10269,
    2525, 10391,  1385,  2525, 10513,  1385,  1385,  2525,  2703, 10636,
     891, 10758,   891,  -657, 10880,   357,  -657,  -657,  1385,   739,
    -657,  -657,  2525,  1385,  2525,  2932,  2525,  1385,  3910,  2703,
    2525,  -657,   682,  -657,  1385,  -657,  -657,  2525, 11002,   891,
    -657,  1385,  2525,   891
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
     299,   300,   297,   298,   233,     0,     0,     0,     0,     0,
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
     326,   294,     0,     0,   234,     0,   227,     0,   225,   238,
     242,     0,     0,   222,   438,   215,   217,     0,   251,   277,
       0,   276,   171,     0,    46,    47,    37,    45,    44,    36,
      41,     0,     0,    40,    35,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   186,     0,     0,
     122,     0,     0,   241,   261,     0,     0,    64,     0,     0,
       0,     0,     0,     0,     0,     0,   110,     0,     0,     0,
       0,     0,     0,   439,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    14,    78,     0,   107,     0,     0,    33,     0,     0,
       0,   127,     0,     0,     0,     0,   175,     0,   317,     0,
     312,   314,     0,   321,     0,     0,   347,     0,   350,     0,
       0,   327,     0,     0,   332,     0,     0,     0,     0,   226,
       0,   245,   434,     0,   236,     0,   275,    11,     0,     0,
       0,    56,     0,    54,    58,     0,     0,     0,     0,     0,
       0,   413,   126,   125,   124,    65,     0,     0,     0,   270,
     271,   263,   264,     0,     0,     0,   109,     0,     0,     0,
       0,     0,     0,   103,     0,     0,     0,   418,     0,     0,
       0,     0,   104,     0,     0,     0,     0,     0,    70,    69,
      81,     0,    83,    68,     0,     0,     0,    71,    73,    77,
      76,    75,     0,     0,   420,    15,     0,    26,    19,     0,
     130,    24,   208,     0,     0,     0,     0,   348,     0,   353,
     354,   328,   330,     0,   295,   235,   228,   230,   231,   241,
     229,   239,     0,     0,     0,   262,   237,   415,     0,     0,
      38,    57,     0,    55,    94,    93,     0,     0,     0,     0,
     123,     0,   265,     0,   266,   430,     0,     0,     0,    92,
     100,   411,     0,     0,     0,   427,     0,     0,   106,     0,
     111,     0,     0,     0,     0,     0,     0,     0,   119,     0,
       0,     0,     0,     0,     0,     0,   108,   112,     0,     0,
       0,   318,   313,   322,     0,     0,   333,   244,     0,     0,
       0,   241,     0,     0,     0,    31,    39,    59,     0,     0,
     414,    89,     0,   268,     0,    66,    67,     0,     0,     0,
       0,     0,     0,   425,     0,   419,     0,     0,   120,     0,
       0,     0,     0,     0,     0,     0,    74,     0,    87,     0,
      72,    20,   131,     0,     0,     0,   247,   243,   257,   255,
     256,   254,     0,     0,   417,   416,    30,     0,   101,     0,
       0,     0,   267,     0,     0,     0,   422,     0,     0,   412,
       0,   428,   429,     0,    95,    79,     0,     0,     0,   115,
     114,     0,     0,     0,     0,     0,     0,   128,   349,   351,
       0,   224,     0,   252,    96,     0,     0,   269,    99,     0,
       0,     0,   421,     0,     0,   121,   116,     0,     0,     0,
      85,     0,    82,    80,     0,   246,   259,   260,   258,   241,
      90,    91,     0,   423,     0,     0,     0,   117,     0,     0,
       0,   129,   244,    98,   424,   426,    97,     0,     0,    86,
     253,   118,     0,    84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -657,  -657,  -657,   848,  -657,  -657,  -169,  -131,   164,  -657,
     507,  -657,  -657,   126,   881,  -657,  -657,   117,   -26,  -657,
    -657,  -657,   842,   -74,   421,  -657,  -657,   835,  -657,  -311,
    -168,   112,  -657,   843,   105,   417,    13,   745,  -140,  -657,
    -657,   504,  -192,   696,   226,  -657,   815,   856,   301,  -657,
     864,   861,  -657,  -600,  -657,   282,  -657,  -657,   168,  -633,
    -657,  -129,  -479,  -657,  -657,  -657,  -316,  -657,  -657,    72,
    -657,  -657,   147,  -657,  -657,  -657,   287,    19,   -48,   615,
     940,  1074,  1179,  1367,   787,   386,   508,  -657,   -31,  -657,
    -657,   197,  -657,  -657,  -657,   516,   389,  -657,  -640,  -657,
    -656,  -657,  -657,  -657,  -657,    12,  -657,    11,   -22,  -145,
    -657,   -10,  -139,  -247,  -130,  -133,   498,  -657,  -657,  -657
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   187,   635,   341,   352,
     353,   511,   509,   743,   744,   929,   649,   650,   692,   551,
      13,    27,    28,   745,   342,    14,    31,    32,    15,   331,
     332,   333,    23,    24,   487,   192,   536,   104,   105,   106,
     107,   193,   194,   375,   334,    37,    38,   228,   335,    34,
      35,   229,   607,   608,   829,   166,   315,   835,   320,   481,
     482,   946,   947,   991,   336,   488,   489,   834,  1019,   653,
     654,   761,   762,   763,   337,   493,   494,   212,   413,   152,
     153,   154,   155,   156,   157,   230,   231,   158,   716,   159,
     581,   582,   160,   161,   217,   218,   662,   750,   621,   776,
     537,   538,   920,   539,   864,   865,   656,   263,   222,   264,
     223,    74,   224,   225,   226,   265,   266,   267,   268,   258
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "151,378,613,751,188,167,270,729,777,257,372,269,504,490,73,758,842,354,316,76,764,496,491,322,323,318,772,772,719,723,415,856,479,171,376,408,178,179,180,181,709,424,426,277,173,769,209,340,343,344,397,346,347,707,182,183,867,710,171,103,210,708,211,213,216,26,471,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,781,847,712,73,103,103,80,924,103,272,103,401,103,103,103,103,103,25,345,713,33,36,605,16,606,500,282,259,81,196,759,760,16,409,324,221,326,1,2,3,480,4,215,409,30,501,197,348,39,377,33,728,178,179,180,181,857,986,422,25,568,256,19,20,21,22,-171,221,312,394,182,183,371,425,416,184,271,395,724,773,782,172,5,618,425,425,425,77,82,103,82,36,174,82,82,354,103,623,103,544,545,546,474,548,549,185,407,897,554,555,358,395,484,186,559,584,428,103,41,910,103,103,417,163,925,164,422,402,362,103,103,103,103,395,848,425,430,17,431,433,40,434,435,436,585,491,17,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,569,583,318,953,184,574,438,590,412,414,420,717,987,362,425,419,601,697,395,922,855,587,94,95,96,97,98,99,100,101,432,599,6,7,185,952,18,45,718,473,46,826,543,277,43,18,923,547,967,968,550,552,553,682,475,556,557,558,886,470,785,163,364,164,422,603,626,395,629,630,631,633,634,365,366,355,367,356,368,165,369,44,566,975,502,422,570,571,476,572,370,575,362,577,1020,578,395,580,19,20,21,22,586,1021,501,589,378,48,592,542,503,595,421,597,598,282,640,641,642,404,560,1032,73,364,103,103,658,659,422,675,501,726,103,103,103,395,169,367,170,368,422,369,567,423,395,395,178,179,180,181,405,370,693,103,694,103,311,312,29,355,505,356,256,364,182,183,610,684,593,422,492,103,615,616,685,686,651,687,619,688,501,689,209,29,404,624,395,178,179,180,181,690,362,733,49,727,210,196,102,50,75,83,364,655,45,182,183,46,657,993,78,976,661,663,197,405,667,668,368,670,369,676,395,82,103,209,103,168,506,614,370,395,190,209,189,695,195,73,198,210,200,201,202,203,204,209,176,210,501,498,177,254,205,371,371,371,206,371,371,210,498,740,207,371,371,807,808,868,371,208,811,53,898,209,632,747,748,499,722,674,46,501,869,682,605,669,606,210,499,725,199,46,914,205,682,501,501,206,665,731,666,591,364,207,185,255,840,583,749,756,208,103,543,365,366,184,367,317,368,739,369,767,261,741,325,902,327,899,501,757,370,768,961,262,926,371,103,189,371,371,371,501,371,371,373,185,56,273,381,382,274,501,778,905,501,319,338,387,388,389,390,927,339,355,866,356,903,791,384,391,794,795,796,277,207,799,800,801,644,645,646,501,804,871,872,806,67,392,403,809,810,57,812,364,813,406,814,684,625,815,627,816,817,418,364,906,820,687,684,688,823,689,775,405,58,830,93,701,783,702,688,690,689,19,20,21,22,-171,427,841,53,439,690,364,437,440,60,61,62,63,64,65,478,483,281,282,497,802,477,368,485,369,364,495,941,508,684,510,283,863,404,370,492,67,67,205,611,612,688,206,689,875,876,877,103,207,622,636,103,617,690,103,208,103,620,638,311,312,705,67,887,637,943,67,67,67,664,639,205,732,730,373,206,742,735,56,313,314,207,385,746,854,866,67,737,208,371,371,67,67,371,486,766,479,784,904,786,1,561,562,838,4,328,908,329,330,205,564,565,846,206,19,20,21,22,917,207,853,858,371,57,380,873,208,804,874,895,576,72,325,205,930,652,900,206,821,934,935,108,937,207,939,5,58,205,594,942,208,206,480,959,944,945,960,207,973,992,990,1003,954,955,208,42,472,541,60,61,62,63,64,65,913,108,108,47,965,108,843,108,850,108,108,108,108,108,371,383,775,978,162,260,79,205,563,379,175,206,66,604,51,609,67,207,52,734,72,72,836,1040,208,677,678,901,852,736,660,103,103,988,819,371,999,579,573,1001,83,671,628,596,682,1004,72,103,67,67,72,72,72,1009,971,1011,189,0,1014,0,0,0,0,0,0,0,0,205,677,678,72,206,108,0,680,72,72,207,386,108,1028,108,0,0,208,1,681,682,349,4,328,350,329,330,103,351,68,0,995,996,108,1038,0,108,108,0,0,205,0,67,0,206,108,108,108,108,0,207,0,0,0,0,277,1015,208,720,0,374,0,0,5,67,205,67,0,0,206,0,67,0,0,0,207,0,67,0,67,0,0,208,0,67,0,0,364,0,0,67,684,0,67,0,0,0,67,685,686,0,687,0,688,0,689,68,68,0,0,0,0,189,0,0,690,0,0,752,753,754,72,0,0,0,373,281,282,0,683,364,0,68,0,684,53,68,68,68,0,283,685,686,67,687,0,688,0,689,0,0,67,0,72,72,0,68,84,690,85,86,68,68,69,307,308,309,310,311,312,0,0,0,0,0,87,0,0,0,88,89,90,91,92,0,219,0,374,313,314,0,0,67,0,0,0,825,0,0,0,831,67,0,833,0,837,54,55,357,358,56,0,0,0,72,0,108,108,0,373,189,0,0,0,108,108,108,362,0,0,0,0,0,0,0,0,72,0,72,0,69,69,0,72,0,108,0,108,0,72,0,72,0,0,0,57,72,0,0,0,0,0,72,108,0,72,69,53,70,72,69,69,69,67,68,0,0,0,58,0,0,0,0,672,0,535,0,0,0,0,69,0,0,0,0,69,69,0,673,0,60,61,62,63,64,65,68,68,0,0,0,0,0,0,0,72,108,219,108,0,191,0,0,72,0,0,64,0,94,95,96,97,98,99,100,101,0,364,54,55,67,678,56,0,0,70,70,0,365,366,0,367,0,368,0,369,0,0,0,682,0,0,0,0,72,370,0,68,949,951,0,70,0,72,0,70,70,70,0,0,0,0,0,0,962,67,0,57,0,68,0,68,0,0,0,70,68,0,0,0,70,70,68,108,68,0,0,0,69,68,58,0,0,0,0,68,0,0,68,0,0,0,68,0,0,0,0,0,108,0,220,997,60,61,62,63,64,65,0,0,69,69,67,0,0,72,0,0,0,648,0,0,677,678,679,0,71,1,680,0,349,4,328,350,329,330,0,540,68,364,681,682,0,684,696,0,68,0,0,0,685,686,0,687,0,688,0,689,0,0,0,0,0,0,0,0,0,690,0,0,0,69,5,0,0,0,0,-171,0,0,0,0,72,70,0,0,0,68,0,0,0,0,0,69,0,69,68,0,0,0,69,0,0,71,71,0,69,0,69,0,0,0,0,69,0,70,70,0,0,69,0,0,69,0,0,0,69,72,67,71,0,0,0,71,71,71,108,0,0,0,108,0,0,108,0,108,0,0,-171,-171,683,364,-171,71,0,684,0,0,71,71,67,0,685,686,0,687,0,688,68,689,0,0,69,0,0,0,70,0,0,690,69,788,789,790,72,792,793,0,0,0,797,798,0,0,0,0,0,-171,70,0,70,0,0,0,0,70,0,0,0,0,0,70,0,70,0,0,0,0,70,0,-171,69,0,0,70,0,0,70,0,0,69,70,0,0,0,68,19,20,21,22,-171,0,-171,-171,-171,-171,-171,-171,0,0,0,844,845,0,0,0,0,0,0,0,849,0,851,0,0,0,0,0,0,277,0,71,859,860,861,862,0,70,53,0,68,0,0,0,0,70,0,278,0,0,0,878,0,0,0,0,0,0,0,0,72,0,69,71,71,0,0,0,108,108,0,357,358,359,0,738,0,360,0,0,0,0,0,0,108,0,70,0,0,361,362,0,72,0,0,70,0,68,0,0,281,282,0,0,0,0,907,0,0,54,55,909,0,56,283,284,285,286,287,0,915,916,0,0,71,0,0,0,0,69,0,108,0,0,303,304,0,928,307,308,309,310,311,312,0,936,71,938,71,940,0,0,0,71,0,0,0,0,57,71,0,71,313,314,0,0,71,70,0,0,0,0,71,0,69,71,0,0,0,71,0,58,0,966,0,969,0,0,972,0,0,974,0,0,0,0,979,980,0,363,364,59,0,60,61,62,63,64,65,0,0,365,366,68,367,0,368,0,369,0,0,994,0,0,0,71,0,998,370,0,0,69,1002,71,70,84,0,85,86,0,1005,1006,0,0,0,68,1010,0,1012,1013,0,0,0,87,0,0,1018,88,89,90,91,92,0,0,1023,0,0,1025,0,0,0,1027,0,84,71,85,86,0,0,70,0,0,0,71,0,0,1033,0,1034,0,1036,87,0,0,1039,88,89,90,91,92,84,1041,85,86,0,0,1043,0,0,0,0,0,0,0,0,0,0,84,87,832,86,0,88,89,90,91,92,0,0,0,0,0,0,0,0,87,70,0,0,88,89,90,91,92,0,0,0,69,0,0,0,0,0,0,0,71,0,0,84,0,948,86,0,0,0,0,0,0,677,678,679,0,755,0,680,0,87,0,0,69,88,89,90,91,92,53,681,682,0,0,0,706,0,0,0,0,0,0,191,0,0,0,0,0,64,0,94,95,96,97,98,99,100,101,0,84,0,950,86,0,0,0,71,0,0,0,0,0,0,0,0,0,277,87,0,0,93,88,89,90,91,92,64,0,94,95,96,97,98,99,100,101,70,0,0,0,54,55,0,0,56,0,0,191,0,0,0,71,0,64,0,94,95,96,97,98,99,100,101,0,93,0,0,0,70,0,64,0,94,95,96,97,98,99,100,101,683,364,0,0,0,684,0,281,282,57,0,0,685,686,0,687,0,688,0,689,0,283,0,0,0,93,0,0,71,690,0,64,58,94,95,96,97,98,99,100,101,303,0,0,0,307,308,309,310,311,312,0,227,0,60,61,62,63,64,65,0,109,0,110,0,0,111,0,0,313,314,0,0,0,0,112,0,0,0,0,0,0,93,0,0,0,0,0,64,0,94,95,96,97,98,99,100,101,113,827,828,0,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,0,140,141,142,71,0,0,0,512,513,514,515,0,0,516,0,517,518,519,520,521,0,0,0,0,0,522,523,0,0,0,0,0,0,0,0,71,0,0,0,143,144,145,57,0,0,0,1016,1017,0,0,0,524,0,525,0,146,109,526,110,0,0,111,0,0,0,58,0,0,0,0,112,214,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,527,0,528,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,529,140,141,142,0,530,0,0,0,0,0,0,0,0,0,0,0,0,0,0,531,532,533,0,0,534,0,0,0,0,0,0,0,0,0,0,0,0,93,0,143,144,145,57,0,0,0,0,0,0,0,0,0,0,0,0,146,109,0,110,0,0,111,0,0,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,0,0,652,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,0,140,141,142,0,0,0,0,0,0,0,0,0,0,512,513,514,515,0,0,516,0,517,518,519,520,521,0,677,678,679,0,522,523,680,0,0,0,0,0,143,144,145,57,0,0,681,682,0,0,0,0,0,0,0,0,146,109,524,110,525,0,111,0,526,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,0,0,0,0,0,114,527,115,528,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,0,0,56,139,803,140,141,142,0,0,529,0,0,0,0,530,0,0,0,0,0,0,0,0,683,364,0,0,0,684,531,532,533,0,0,534,685,686,0,687,0,688,0,689,143,144,145,57,93,0,0,0,0,690,0,691,0,0,0,0,146,109,0,110,0,0,111,0,0,0,58,0,0,0,0,112,0,0,147,0,148,0,0,0,0,0,0,0,0,0,149,150,60,61,62,63,64,65,113,53,0,0,0,0,114,0,115,0,0,0,116,0,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,0,0,137,138,53,0,56,139,0,140,141,142,0,0,0,818,0,219,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,54,55,0,0,56,0,0,0,0,0,143,144,145,57,970,0,219,0,0,0,0,512,513,514,515,0,146,516,0,517,518,519,520,521,0,0,58,54,55,522,523,56,0,0,147,0,148,0,0,57,0,0,0,0,0,0,149,150,60,61,62,63,64,65,0,524,0,525,0,0,0,526,58,0,0,0,0,0,0,0,0,0,677,678,679,0,57,0,680,0,0,0,220,0,60,61,62,63,64,65,681,682,0,0,0,0,643,0,527,58,528,0,0,0,0,0,0,0,0,677,678,679,0,0,0,680,0,0,0,220,0,60,61,62,63,64,65,681,682,0,0,0,0,0,0,644,645,646,0,529,0,0,0,0,530,0,0,677,678,679,0,0,0,680,0,0,0,0,0,531,532,533,0,0,534,681,682,0,0,0,512,513,514,515,0,0,516,647,517,518,519,520,521,0,0,0,0,0,522,523,0,0,0,0,0,918,919,0,0,0,683,364,0,0,0,684,0,0,0,0,0,0,685,686,524,687,525,688,0,689,526,0,0,0,0,0,0,0,0,690,0,805,0,0,0,0,0,683,364,0,0,0,684,0,0,0,275,0,0,685,686,276,687,277,688,0,689,527,0,528,0,0,0,677,678,679,690,911,1035,680,0,278,0,0,0,683,364,0,0,0,684,681,682,0,0,0,0,685,686,0,687,0,688,0,689,0,0,0,529,0,279,280,0,530,690,0,0,0,677,678,679,0,912,0,680,0,357,358,531,532,533,0,360,534,281,282,681,682,0,0,0,0,0,0,361,362,647,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,677,678,679,0,958,0,680,0,0,357,358,359,0,0,0,360,313,314,681,682,0,683,364,0,0,0,684,361,362,0,0,0,0,685,686,0,687,989,688,0,689,0,0,0,0,0,0,0,0,0,690,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,683,364,0,0,0,684,0,0,363,364,0,0,685,686,0,687,0,688,0,689,365,366,0,367,0,368,0,369,0,690,0,0,0,0,0,0,0,370,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,683,364,278,0,0,684,0,0,0,363,364,0,685,686,0,687,0,688,0,689,0,365,366,0,367,0,368,0,369,690,0,279,280,0,0,0,0,0,370,0,0,0,0,0,0,0,0,0,0,0,277,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,278,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,281,282,0,278,0,0,0,1007,0,0,1008,0,0,0,283,284,285,286,287,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,277,0,0,0,0,281,282,0,0,0,0,313,314,0,0,0,0,0,278,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,281,282,0,278,0,0,0,787,0,0,0,0,0,0,283,284,285,286,287,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,303,0,0,0,307,308,309,310,311,312,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,313,314,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,932,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,933,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,977,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,879,880,0,0,0,0,278,0,0,1037,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,698,0,0,0,881,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,699,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,704,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,398,0,885,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,399,400,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,714,715,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,770,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,771,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,882,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,963,883,0,0,0,0,0,0,313,314,964,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,983,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,321,277,0,0,0,984,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,393,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,396,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,410,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,411,0,0,313,314,429,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,507,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,600,277,0,0,0,0,588,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,602,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,700,0,0,0,0,703,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,313,314,711,0,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,400,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,721,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,765,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,774,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,779,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,780,770,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,822,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,824,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,870,277,0,0,0,839,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,884,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,888,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,889,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,890,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,891,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,313,314,892,0,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,313,314,893,0,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,894,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,921,0,896,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,931,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,956,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,957,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,981,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,982,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,985,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1000,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,1022,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1024,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,1026,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,313,314,1029,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,1030,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,1042,1031,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,276,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,275,0,0,0,0,0,0,277,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,275,0,0,0,0,0,0,277,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,295,278,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,0,275,0,0,0,279,280,0,277,0,0,313,314,0,0,0,0,0,0,0,0,0,0,0,0,0,278,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,293,294,279,280,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,275,281,282,0,0,0,0,277,0,0,0,0,0,313,314,283,284,285,286,287,288,289,290,291,292,278,294,0,0,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,0,0,279,280,0,277,0,0,0,0,0,313,314,0,0,0,0,0,0,0,0,0,0,278,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,288,289,290,291,292,0,0,279,280,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,281,282,277,0,0,0,0,0,0,0,0,0,313,314,283,284,285,286,287,0,278,0,0,0,0,0,0,0,0,0,0,0,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,313,314,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,283,284,285,286,287,0,0,0,0,0,0,0,0,0,0,0,0,0,301,302,303,304,305,306,307,308,309,310,311,312,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,313,314";
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
  public static final String yyCheckString = "48,193,481,643,78,53,145,607,664,139,13,144,13,329,36,13,13,185,163,12,653,332,47,168,169,165,31,31,27,13,13,27,49,12,45,59,14,15,16,17,13,13,13,19,12,59,90,178,179,180,30,182,183,13,32,33,100,30,12,46,104,21,110,111,112,180,313,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,13,13,13,114,80,81,12,27,84,146,86,30,88,89,90,91,92,1,181,30,4,5,17,0,19,13,91,17,34,27,56,57,9,146,171,113,173,3,4,5,146,7,112,146,180,33,44,184,180,149,34,46,14,15,16,17,141,13,146,43,396,139,176,177,178,179,180,145,133,21,32,33,187,146,146,142,146,146,151,173,173,149,48,488,146,146,146,169,180,165,180,75,149,180,180,352,172,497,174,357,358,359,18,361,362,172,149,829,366,367,15,146,18,180,372,18,151,193,0,848,196,197,259,17,869,19,146,146,33,205,206,207,208,146,146,146,273,0,275,276,180,278,279,280,18,47,9,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,398,411,407,901,142,403,281,18,254,255,262,146,144,33,146,261,18,18,146,146,764,416,188,189,190,191,192,193,194,195,276,429,174,175,172,900,0,146,173,314,149,18,180,19,33,9,173,360,62,63,363,364,365,33,146,368,369,370,18,313,59,17,143,19,146,475,500,146,502,503,504,505,506,154,155,17,157,19,159,149,161,180,395,18,13,146,399,400,149,402,171,404,33,406,18,408,146,410,176,177,178,179,415,18,33,418,563,12,421,355,13,424,118,426,427,91,512,513,514,118,374,1019,409,143,376,377,522,523,146,533,33,149,384,385,386,146,17,157,19,159,146,161,396,149,146,146,14,15,16,17,149,171,17,405,19,407,132,133,2,17,13,19,416,143,32,33,479,147,422,146,330,423,485,486,154,155,515,157,491,159,33,161,90,27,118,498,146,14,15,16,17,171,33,149,33,605,104,27,46,180,33,45,143,516,146,32,33,149,521,953,12,146,525,526,44,149,529,530,159,532,161,534,146,180,476,90,478,17,13,483,171,146,80,90,78,100,84,524,86,104,88,89,90,91,92,90,172,104,33,118,180,17,27,544,545,546,31,548,549,104,118,13,37,554,555,699,700,18,559,44,704,17,102,90,142,13,13,146,591,533,149,33,33,33,17,531,19,104,146,602,180,149,149,27,33,33,33,31,17,612,19,146,143,37,172,17,740,717,13,13,44,563,180,154,155,142,157,165,159,632,161,13,17,636,172,59,174,102,33,33,171,13,149,17,13,626,588,181,629,630,631,33,633,634,188,172,92,17,196,197,98,33,665,180,33,180,12,205,206,207,208,13,180,17,774,19,100,680,36,180,683,684,685,19,37,688,689,690,138,139,140,33,695,779,780,698,36,180,31,702,703,137,705,143,707,13,709,147,499,712,501,714,715,17,143,839,719,157,147,159,723,161,664,149,160,728,180,144,672,146,159,171,161,176,177,178,179,180,13,742,17,92,171,143,180,180,182,183,184,185,186,187,149,146,90,91,172,693,180,159,12,161,143,173,888,180,147,180,104,772,118,171,622,113,114,27,180,50,159,31,161,784,785,786,726,37,33,12,730,180,171,733,44,735,180,12,132,133,46,139,803,180,890,143,144,145,17,180,27,18,149,345,31,12,149,92,152,153,37,38,12,763,922,163,173,44,807,808,168,169,811,12,21,49,12,838,59,3,376,377,17,7,8,846,10,11,27,385,386,20,31,176,177,178,179,858,37,146,20,840,137,18,17,44,867,118,27,405,36,407,27,874,55,180,31,18,879,880,46,882,37,884,48,160,27,423,889,44,31,146,17,894,895,17,37,20,46,51,27,902,903,44,9,180,352,182,183,184,185,186,187,853,80,81,27,918,84,746,86,757,88,89,90,91,92,906,21,869,931,49,140,43,27,384,193,75,31,36,476,33,478,281,37,34,617,113,114,734,1032,44,14,15,835,761,622,524,898,899,944,717,941,964,409,402,967,499,532,501,425,33,973,139,914,313,314,143,144,145,981,922,983,515,-1,986,-1,-1,-1,-1,-1,-1,-1,-1,27,14,15,163,31,165,-1,20,168,169,37,38,172,1008,174,-1,-1,44,3,32,33,6,7,8,9,10,11,961,13,36,-1,959,960,193,1029,-1,196,197,-1,-1,27,-1,374,-1,31,205,206,207,208,-1,37,-1,-1,-1,-1,19,990,44,588,-1,47,-1,-1,48,396,27,398,-1,-1,31,-1,403,-1,-1,-1,37,-1,409,-1,411,-1,-1,44,-1,416,-1,-1,143,-1,-1,422,147,-1,425,-1,-1,-1,429,154,155,-1,157,-1,159,-1,161,113,114,-1,-1,-1,-1,638,-1,-1,171,-1,-1,644,645,646,281,-1,-1,-1,651,90,91,-1,142,143,-1,139,-1,147,17,143,144,145,-1,104,154,155,475,157,-1,159,-1,161,-1,-1,483,-1,313,314,-1,163,17,171,19,20,168,169,36,128,129,130,131,132,133,-1,-1,-1,-1,-1,35,-1,-1,-1,39,40,41,42,43,-1,67,-1,47,152,153,-1,-1,524,-1,-1,-1,726,-1,-1,-1,730,533,-1,733,-1,735,88,89,14,15,92,-1,-1,-1,374,-1,376,377,-1,745,746,-1,-1,-1,384,385,386,33,-1,-1,-1,-1,-1,-1,-1,-1,396,-1,398,-1,113,114,-1,403,-1,405,-1,407,-1,409,-1,411,-1,-1,-1,137,416,-1,-1,-1,-1,-1,422,423,-1,425,139,17,36,429,143,144,145,605,281,-1,-1,-1,160,-1,-1,-1,-1,165,-1,351,-1,-1,-1,-1,163,-1,-1,-1,-1,168,169,-1,180,-1,182,183,184,185,186,187,313,314,-1,-1,-1,-1,-1,-1,-1,475,476,67,478,-1,180,-1,-1,483,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,143,88,89,672,15,92,-1,-1,113,114,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,33,-1,-1,-1,-1,524,171,-1,374,898,899,-1,139,-1,533,-1,143,144,145,-1,-1,-1,-1,-1,-1,914,717,-1,137,-1,396,-1,398,-1,-1,-1,163,403,-1,-1,-1,168,169,409,563,411,-1,-1,-1,281,416,160,-1,-1,-1,-1,422,-1,-1,425,-1,-1,-1,429,-1,-1,-1,-1,-1,588,-1,180,961,182,183,184,185,186,187,-1,-1,313,314,774,-1,-1,605,-1,-1,-1,515,-1,-1,14,15,16,-1,36,3,20,-1,6,7,8,9,10,11,-1,13,475,143,32,33,-1,147,540,-1,483,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,374,48,-1,-1,-1,-1,17,-1,-1,-1,-1,672,281,-1,-1,-1,524,-1,-1,-1,-1,-1,396,-1,398,533,-1,-1,-1,403,-1,-1,113,114,-1,409,-1,411,-1,-1,-1,-1,416,-1,313,314,-1,-1,422,-1,-1,425,-1,-1,-1,429,717,890,139,-1,-1,-1,143,144,145,726,-1,-1,-1,730,-1,-1,733,-1,735,-1,-1,88,89,142,143,92,163,-1,147,-1,-1,168,169,922,-1,154,155,-1,157,-1,159,605,161,-1,-1,475,-1,-1,-1,374,-1,-1,171,483,677,678,679,774,681,682,-1,-1,-1,686,687,-1,-1,-1,-1,-1,137,396,-1,398,-1,-1,-1,-1,403,-1,-1,-1,-1,-1,409,-1,411,-1,-1,-1,-1,416,-1,160,524,-1,-1,422,-1,-1,425,-1,-1,533,429,-1,-1,-1,672,176,177,178,179,180,-1,182,183,184,185,186,187,-1,-1,-1,747,748,-1,-1,-1,-1,-1,-1,-1,756,-1,758,-1,-1,-1,-1,-1,-1,19,-1,281,768,769,770,771,-1,475,17,-1,717,-1,-1,-1,-1,483,-1,37,-1,-1,-1,787,-1,-1,-1,-1,-1,-1,-1,-1,890,-1,605,313,314,-1,-1,-1,898,899,-1,14,15,16,-1,18,-1,20,-1,-1,-1,-1,-1,-1,914,-1,524,-1,-1,32,33,-1,922,-1,-1,533,-1,774,-1,-1,90,91,-1,-1,-1,-1,842,-1,-1,88,89,847,-1,92,104,105,106,107,108,-1,856,857,-1,-1,374,-1,-1,-1,-1,672,-1,961,-1,-1,124,125,-1,873,128,129,130,131,132,133,-1,881,396,883,398,885,-1,-1,-1,403,-1,-1,-1,-1,137,409,-1,411,152,153,-1,-1,416,605,-1,-1,-1,-1,422,-1,717,425,-1,-1,-1,429,-1,160,-1,919,-1,921,-1,-1,924,-1,-1,927,-1,-1,-1,-1,932,933,-1,142,143,180,-1,182,183,184,185,186,187,-1,-1,154,155,890,157,-1,159,-1,161,-1,-1,957,-1,-1,-1,475,-1,963,171,-1,-1,774,968,483,672,17,-1,19,20,-1,976,977,-1,-1,-1,922,982,-1,984,985,-1,-1,-1,35,-1,-1,992,39,40,41,42,43,-1,-1,1000,-1,-1,1003,-1,-1,-1,1007,-1,17,524,19,20,-1,-1,717,-1,-1,-1,533,-1,-1,1022,-1,1024,-1,1026,35,-1,-1,1030,39,40,41,42,43,17,1037,19,20,-1,-1,1042,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,35,19,20,-1,39,40,41,42,43,-1,-1,-1,-1,-1,-1,-1,-1,35,774,-1,-1,39,40,41,42,43,-1,-1,-1,890,-1,-1,-1,-1,-1,-1,-1,605,-1,-1,17,-1,19,20,-1,-1,-1,-1,-1,-1,14,15,16,-1,18,-1,20,-1,35,-1,-1,922,39,40,41,42,43,17,32,33,-1,-1,-1,173,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,17,-1,19,20,-1,-1,-1,672,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,35,-1,-1,180,39,40,41,42,43,186,-1,188,189,190,191,192,193,194,195,890,-1,-1,-1,88,89,-1,-1,92,-1,-1,180,-1,-1,-1,717,-1,186,-1,188,189,190,191,192,193,194,195,-1,180,-1,-1,-1,922,-1,186,-1,188,189,190,191,192,193,194,195,142,143,-1,-1,-1,147,-1,90,91,137,-1,-1,154,155,-1,157,-1,159,-1,161,-1,104,-1,-1,-1,180,-1,-1,774,171,-1,186,160,188,189,190,191,192,193,194,195,124,-1,-1,-1,128,129,130,131,132,133,-1,180,-1,182,183,184,185,186,187,-1,15,-1,17,-1,-1,20,-1,-1,152,153,-1,-1,-1,-1,29,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,890,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,922,-1,-1,-1,134,135,136,137,-1,-1,-1,53,54,-1,-1,-1,58,-1,60,-1,150,15,64,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,30,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,101,-1,103,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,142,95,96,97,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,94,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,17,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,17,-1,92,93,-1,95,96,97,-1,-1,-1,65,-1,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,88,89,-1,-1,92,-1,-1,-1,-1,-1,134,135,136,137,65,-1,67,-1,-1,-1,-1,14,15,16,17,-1,150,20,-1,22,23,24,25,26,-1,-1,160,88,89,32,33,92,-1,-1,168,-1,170,-1,-1,137,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,-1,58,-1,60,-1,-1,-1,64,160,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,-1,137,-1,20,-1,-1,-1,180,-1,182,183,184,185,186,187,32,33,-1,-1,-1,-1,99,-1,101,160,103,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,180,-1,182,183,184,185,186,187,32,33,-1,-1,-1,-1,-1,-1,138,139,140,-1,142,-1,-1,-1,-1,147,-1,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,32,33,-1,-1,-1,14,15,16,17,-1,-1,20,180,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,62,63,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,58,157,60,159,-1,161,64,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,12,-1,-1,154,155,17,157,19,159,-1,161,101,-1,103,-1,-1,-1,14,15,16,171,18,173,20,-1,37,-1,-1,-1,142,143,-1,-1,-1,147,32,33,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,142,-1,66,67,-1,147,171,-1,-1,-1,14,15,16,-1,18,-1,20,-1,14,15,162,163,164,-1,20,167,90,91,32,33,-1,-1,-1,-1,-1,-1,32,33,180,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,14,15,16,-1,18,-1,20,-1,-1,14,15,16,-1,-1,-1,20,152,153,32,33,-1,142,143,-1,-1,-1,147,32,33,-1,-1,-1,-1,154,155,-1,157,173,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,142,143,-1,-1,154,155,-1,157,-1,159,-1,161,154,155,-1,157,-1,159,-1,161,-1,171,-1,-1,-1,-1,-1,-1,-1,171,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,142,143,37,-1,-1,147,-1,-1,-1,142,143,-1,154,155,-1,157,-1,159,-1,161,-1,154,155,-1,157,-1,159,-1,161,171,-1,66,67,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
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
     319,   325,   180,   323,    18,   146,   149,   180,   149,    49,
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
      18,    18,    86,   321,   247,    17,    19,   264,   265,   247,
     290,   180,    50,   274,   319,   290,   290,   180,   278,   290,
     180,   310,    33,   241,   290,   220,   218,   220,   236,   218,
     218,   218,   142,   218,   218,   219,    12,   180,    12,   180,
     219,   219,   219,    99,   138,   139,   140,   180,   226,   228,
     229,   235,    55,   281,   282,   290,   318,   290,   219,   219,
     297,   290,   308,   290,    17,    17,    19,   290,   290,   289,
     290,   308,   165,   180,   319,   324,   290,    14,    15,    16,
      20,    32,    33,   142,   147,   154,   155,   157,   159,   161,
     171,   173,   230,    17,    19,   100,   226,    18,    32,   158,
     145,   144,   146,    28,   156,    46,   173,    13,    21,    13,
      30,    30,    13,    30,    62,    63,   300,   146,   173,    27,
     247,    18,   290,    13,   151,   290,   149,   321,    46,   265,
     149,   290,    18,   149,   267,   149,   288,   173,    18,   290,
      13,   290,    12,   225,   226,   235,    12,    13,    13,    13,
     309,   310,   236,   236,   236,    18,    13,    33,    13,    56,
      57,   283,   284,   285,   271,    21,    21,    13,    13,    59,
      27,    61,    31,   173,   149,   248,   311,   312,   290,   145,
     148,    13,   173,   319,    12,    59,    59,   163,   226,   226,
     226,   290,   226,   226,   290,   290,   290,   226,   226,   290,
     290,   290,   289,    94,   290,   173,   290,   218,   218,   290,
     290,   218,   290,   290,   290,   290,   290,   290,    65,   303,
     290,    18,    18,   290,    18,   247,    18,    53,    54,   266,
     290,   247,    19,   247,   279,   269,   270,   247,    17,   145,
     218,   290,    13,   225,   226,   226,    20,    13,   146,   226,
     229,   226,   284,   146,   248,   274,    27,   141,    20,   226,
     226,   226,   226,   290,   316,   317,   321,   100,    18,    33,
      18,   219,   219,    17,   118,   290,   290,   290,   226,    31,
      32,   158,    31,   145,    31,   156,    18,   290,    21,    28,
      31,    21,    30,    30,    61,    27,   151,   271,   102,   102,
     180,   281,    59,   100,   290,   180,   218,   226,   290,   226,
     310,    18,    18,   248,   149,   226,   226,   290,    62,    63,
     314,    27,   146,   173,    27,   312,    13,    13,   226,   227,
     290,    38,   163,   163,   290,   290,   226,   290,   226,   290,
     226,   218,   290,   327,   290,   290,   273,   274,    19,   247,
      19,   247,   265,   271,   290,   290,    18,    21,    18,    17,
      17,   149,   247,    21,    31,   290,   226,    62,    63,   226,
      65,   317,   226,    20,   226,    18,   146,   163,   290,   226,
     226,    32,    21,    31,   145,   148,    13,   144,   300,   173,
      51,   275,    46,   274,   226,   289,   289,   247,   226,   290,
      61,   290,   226,    27,   290,   226,   226,   163,   166,   290,
     226,   290,   226,   226,   290,   248,    53,    54,   226,   280,
      18,    18,    21,   226,    61,   226,    21,   226,   290,    31,
     145,   144,   271,   226,   226,   173,   226,   163,   290,   226,
     273,   226,    21,   226
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
     290,   152,   319,    -1,   290,   152,   325,    -1,    58,   297,
      59,   290,    -1,   299,    -1,   301,    -1,   304,    -1,   305,
      -1,   134,   327,    13,   290,    -1,   135,   327,    13,   290,
      -1,   136,   324,    13,   290,    -1,    52,   324,    13,   290,
      -1,    29,    30,    -1,    29,   289,    30,    -1,    29,   290,
      31,   327,    30,    -1,    29,   290,    31,   327,    13,   290,
      30,    -1,    29,   290,    86,   290,    30,    -1,    15,    -1,
      20,   289,    21,    -1,    20,   290,    31,   325,    21,    -1,
      20,   290,    31,   325,    13,   290,    21,    -1,   170,    -1,
      29,   306,    30,    -1,    29,   307,    31,   327,    30,    -1,
      29,   307,    31,   327,    13,   290,    30,    -1,    88,    17,
     289,    18,    -1,    89,    17,   289,    18,    -1,    93,   331,
      13,   290,    -1,   168,    17,   290,    18,    -1,    95,   249,
      17,   290,    18,    -1,    95,    17,   290,   146,   247,    18,
      -1,    96,    17,   289,    18,    -1,    97,    17,   248,   146,
     290,    18,    -1,   150,   289,   151,    -1,   150,   290,    31,
     327,   151,    -1,   150,   290,    31,   327,    13,   290,   151,
      -1,   183,    -1,   182,    -1,   184,    -1,   185,    -1,    92,
      -1,   187,    -1,   160,    -1,   137,    -1,   186,    -1,   298,
      -1,   297,   146,   298,    -1,   259,    -1,   263,    -1,    60,
     290,    61,   290,   300,    -1,    63,   290,    -1,    62,   290,
      61,   290,   300,    -1,    64,   290,   149,   302,   173,    -1,
      64,   290,   149,   302,   146,    65,    27,   290,   173,    -1,
     303,    -1,   302,   146,   303,    -1,   321,    27,   290,    -1,
      66,   290,    -1,    67,   290,    -1,    68,   290,    -1,    69,
     290,    -1,    70,   290,    -1,    71,   290,    -1,    72,   290,
      -1,    73,   290,    -1,    74,   290,    -1,    75,   290,    -1,
      76,   290,    -1,    77,   290,    -1,    78,   290,    -1,    79,
     290,    -1,    80,   290,    -1,    81,   290,    -1,    82,   290,
      -1,    83,   290,    -1,    84,   290,    -1,    85,   290,    -1,
     290,    66,   290,    -1,   290,    37,   290,    -1,   290,    67,
     290,    -1,   290,   106,   290,    -1,   290,   105,   290,    -1,
     290,   107,   290,    -1,   290,   108,   290,    -1,   290,   109,
     290,    -1,   290,   110,   290,    -1,   290,   111,   290,    -1,
     290,   112,   290,    -1,   290,    12,   290,    -1,   290,   113,
     290,    -1,   290,   114,   290,    -1,   290,   115,   290,    -1,
     290,   116,   290,    -1,   290,   117,   290,    -1,   290,   118,
     290,    -1,   290,   119,   290,    -1,   290,   120,   290,    -1,
     290,   121,   290,    -1,   290,   122,   290,    -1,   290,   123,
     290,    -1,   290,   124,   290,    -1,   290,   125,   290,    -1,
     290,   126,   290,    -1,   290,   127,   290,    -1,   290,   128,
     290,    -1,   290,   129,   290,    -1,   290,   130,   290,    -1,
     290,   131,   290,    -1,   290,   132,   290,    -1,   290,   133,
     290,    -1,   307,    -1,   306,   146,   307,    -1,   290,    87,
     290,    -1,   290,    27,   226,    -1,   308,    31,   290,    27,
     226,    -1,   310,    -1,   309,   146,   310,    -1,   180,   149,
     247,    -1,   180,   149,   247,   100,   290,    -1,   180,   149,
     247,    59,   290,    -1,   312,    -1,   311,    33,   312,    -1,
     248,   100,   290,    -1,    60,   290,    61,   226,   314,    63,
     226,    -1,    60,   290,    61,   226,    63,   226,    -1,    62,
     290,    61,   226,    -1,   314,    62,   290,    61,   226,    -1,
      64,   290,   149,   316,   173,    -1,    64,   290,   149,   316,
     146,    65,    27,   226,   173,    -1,   317,    -1,   316,   146,
     317,    -1,   321,    27,   226,    -1,   281,   271,   274,    -1,
     322,    -1,   320,    -1,   323,    -1,    88,    17,   321,   146,
     319,    18,    -1,    89,    19,    -1,    89,    17,   321,    18,
      -1,   319,    -1,   321,   146,   319,    -1,   180,    -1,    67,
      -1,   294,    -1,   291,    -1,   293,    -1,   292,    -1,   295,
      -1,   296,    -1,    17,   290,    18,    -1,   325,    -1,   326,
      -1,   319,   118,   290,    -1,   319,   149,   247,    -1,   328,
      -1,   327,   146,   328,    -1,   329,    -1,   330,    -1,   321,
     118,   290,    -1,   321,   149,   247,    -1,   326,    -1,   331,
     146,   326,    -1
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
    1154,  1158,  1163,  1165,  1167,  1169,  1171,  1176,  1181,  1186,
    1191,  1194,  1198,  1204,  1212,  1218,  1220,  1224,  1230,  1238,
    1240,  1244,  1250,  1258,  1263,  1268,  1273,  1278,  1284,  1291,
    1296,  1303,  1307,  1313,  1321,  1323,  1325,  1327,  1329,  1331,
    1333,  1335,  1337,  1339,  1341,  1345,  1347,  1349,  1355,  1358,
    1364,  1370,  1380,  1382,  1386,  1390,  1393,  1396,  1399,  1402,
    1405,  1408,  1411,  1414,  1417,  1420,  1423,  1426,  1429,  1432,
    1435,  1438,  1441,  1444,  1447,  1450,  1454,  1458,  1462,  1466,
    1470,  1474,  1478,  1482,  1486,  1490,  1494,  1498,  1502,  1506,
    1510,  1514,  1518,  1522,  1526,  1530,  1534,  1538,  1542,  1546,
    1550,  1554,  1558,  1562,  1566,  1570,  1574,  1578,  1582,  1584,
    1588,  1592,  1596,  1602,  1604,  1608,  1612,  1618,  1624,  1626,
    1630,  1634,  1642,  1649,  1654,  1660,  1666,  1676,  1678,  1682,
    1686,  1690,  1692,  1694,  1696,  1703,  1706,  1711,  1713,  1717,
    1719,  1721,  1723,  1725,  1727,  1729,  1731,  1733,  1737,  1739,
    1741,  1745,  1749,  1751,  1755,  1757,  1759,  1763,  1767,  1769
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   337,   337,   345,   349,   356,   357,   358,   359,   360,
     364,   378,   411,   439,   473,   480,   488,   494,   500,   509,
     518,   524,   530,   536,   551,   558,   573,   591,   598,   605,
     615,   627,   639,   653,   667,   677,   685,   693,   704,   717,
     727,   735,   746,   752,   758,   764,   781,   794,   806,   812,
     828,   832,   839,   843,   850,   854,   867,   871,   878,   882,
     889,   894,   899,   904,   909,   915,   932,   956,   963,   970,
     977,   984,   999,  1006,  1021,  1037,  1044,  1051,  1058,  1070,
    1077,  1088,  1102,  1113,  1127,  1144,  1161,  1177,  1196,  1200,
    1207,  1218,  1225,  1233,  1241,  1252,  1265,  1278,  1292,  1305,
    1317,  1326,  1336,  1340,  1348,  1368,  1372,  1379,  1383,  1387,
    1398,  1402,  1419,  1456,  1466,  1482,  1498,  1507,  1518,  1529,
    1544,  1550,  1559,  1565,  1574,  1580,  1586,  1595,  1601,  1605,
    1621,  1625,  1638,  1653,  1674,  1680,  1697,  1718,  1739,  1745,
    1754,  1767,  1777,  1793,  1813,  1819,  1831,  1847,  1851,  1855,
    1862,  1868,  1877,  1881,  1885,  1889,  1893,  1903,  1917,  1927,
    1939,  1954,  1960,  1969,  1989,  2010,  2014,  2061,  2068,  2075,
    2082,  2090,  2099,  2106,  2110,  2115,  2127,  2161,  2189,  2196,
    2203,  2210,  2217,  2225,  2233,  2245,  2256,  2262,  2268,  2277,
    2281,  2285,  2289,  2293,  2297,  2301,  2305,  2312,  2316,  2323,
    2332,  2344,  2353,  2365,  2371,  2380,  2388,  2398,  2411,  2439,
    2451,  2481,  2487,  2497,  2507,  2531,  2557,  2577,  2602,  2616,
    2645,  2651,  2660,  2664,  2674,  2705,  2711,  2720,  2724,  2731,
    2735,  2739,  2746,  2750,  2757,  2764,  2775,  2778,  2785,  2800,
    2818,  2819,  2823,  2827,  2828,  2832,  2842,  2847,  2853,  2866,
    2887,  2893,  2902,  2929,  2936,  2948,  2960,  2972,  2986,  2990,
    2994,  3001,  3002,  3006,  3010,  3016,  3036,  3040,  3044,  3048,
    3055,  3059,  3088,  3094,  3110,  3118,  3127,  3133,  3147,  3153,
    3196,  3208,  3213,  3217,  3222,  3234,  3240,  3244,  3249,  3254,
    3259,  3263,  3267,  3271,  3275,  3279,  3286,  3291,  3295,  3299,
    3303,  3308,  3315,  3319,  3323,  3327,  3332,  3340,  3348,  3356,
    3365,  3372,  3379,  3386,  3395,  3403,  3419,  3426,  3433,  3442,
    3447,  3454,  3461,  3470,  3477,  3491,  3506,  3516,  3526,  3540,
    3551,  3556,  3560,  3564,  3574,  3578,  3585,  3596,  3603,  3607,
    3614,  3628,  3637,  3648,  3654,  3663,  3664,  3668,  3700,  3707,
    3721,  3729,  3741,  3748,  3762,  3782,  3788,  3794,  3800,  3806,
    3812,  3818,  3824,  3830,  3836,  3842,  3848,  3854,  3860,  3866,
    3872,  3878,  3884,  3890,  3896,  3905,  3913,  3921,  3929,  3937,
    3945,  3953,  3961,  3969,  3977,  3985,  3993,  4001,  4009,  4017,
    4025,  4033,  4041,  4049,  4057,  4065,  4073,  4081,  4089,  4097,
    4105,  4113,  4121,  4129,  4137,  4145,  4153,  4161,  4172,  4178,
    4186,  4198,  4211,  4227,  4233,  4242,  4259,  4275,  4298,  4305,
    4323,  4330,  4339,  4351,  4362,  4376,  4383,  4394,  4400,  4411,
    4423,  4430,  4431,  4435,  4439,  4456,  4467,  4481,  4487,  4496,
    4510,  4520,  4530,  4535,  4540,  4545,  4550,  4555,  4566,  4567,
    4571,  4582,  4593,  4599,  4608,  4609,  4613,  4624,  4635,  4642
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

  private static final int yylast_ = 11851;
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




/* Line 922 of cmlskeleton.java.m4  */
/* Line 9038 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


