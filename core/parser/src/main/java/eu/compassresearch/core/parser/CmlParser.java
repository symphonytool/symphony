
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
  import eu.compassresearch.ast.definitions.*;
  import org.overture.ast.definitions.*;
  import eu.compassresearch.ast.declarations.*;
  import eu.compassresearch.ast.expressions.*;
  import org.overture.ast.expressions.*;
  import org.overture.ast.statements.*;
  import eu.compassresearch.ast.actions.*;
  import eu.compassresearch.ast.process.*;
  import eu.compassresearch.ast.patterns.*;
  import org.overture.ast.patterns.*;
  import eu.compassresearch.ast.program.*;
  import eu.compassresearch.ast.types.*;
  import org.overture.ast.types.*;
  import org.overture.ast.lex.*;
  import org.overture.ast.typechecker.NameScope;
  import org.overture.ast.node.*;
  import org.overture.ast.node.tokens.*;
  import org.overture.ast.preview.*;
  import org.overture.ast.util.*;
  import eu.compassresearch.core.lexer.CmlLexeme;
  import eu.compassresearch.core.lexer.CmlLexer;
  import eu.compassresearch.core.lexer.Position;
  import eu.compassresearch.ast.definitions.AImplicitOperationDefinition;
  import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
  import org.overture.ast.types.*;




/* Line 40 of cmlskeleton.java.m4  */
/* Line 95 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

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
/* Line 550 of "src/main/bison/cml.y"  */
    {
  List<SParagraphDefinition> paragraphs = (List<SParagraphDefinition>) ((yystack.valueAt (1-(1))));
  currentSource.setParagraphs(paragraphs);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 558 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> programParagraphList = new LinkedList<PDefinition>();
  programParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = programParagraphList;
};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 564 of "src/main/bison/cml.y"  */
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
/* Line 574 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 575 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 576 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 577 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 578 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 583 of "src/main/bison/cml.y"  */
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
/* Line 594 of "src/main/bison/cml.y"  */
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
/* Line 627 of "src/main/bison/cml.y"  */
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
/* Line 655 of "src/main/bison/cml.y"  */
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
/* Line 689 of "src/main/bison/cml.y"  */
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
/* Line 696 of "src/main/bison/cml.y"  */
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
/* Line 704 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASequentialCompositionProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 710 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AExternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 716 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 725 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (5-(1))));
  PProcess right = (PProcess)((yystack.valueAt (5-(5))));
  yyval = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (5-(3)))), right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 734 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (7-(1))));
  PProcess right = (PProcess)((yystack.valueAt (7-(7))));
  yyval = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)((yystack.valueAt (7-(3)))), (PExp)((yystack.valueAt (7-(5)))), right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 740 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new ASynchronousParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 746 of "src/main/bison/cml.y"  */
    {
  PProcess left = (PProcess)((yystack.valueAt (3-(1))));
  PProcess right = (PProcess)((yystack.valueAt (3-(3))));
  yyval = new AInterleavingProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 752 of "src/main/bison/cml.y"  */
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
/* Line 767 of "src/main/bison/cml.y"  */
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
/* Line 774 of "src/main/bison/cml.y"  */
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
/* Line 789 of "src/main/bison/cml.y"  */
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
/* Line 807 of "src/main/bison/cml.y"  */
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
/* Line 814 of "src/main/bison/cml.y"  */
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
/* Line 821 of "src/main/bison/cml.y"  */
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
/* Line 831 of "src/main/bison/cml.y"  */
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
/* Line 845 of "src/main/bison/cml.y"  */
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
/* Line 854 of "src/main/bison/cml.y"  */
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
/* Line 864 of "src/main/bison/cml.y"  */
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
/* Line 874 of "src/main/bison/cml.y"  */
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
/* Line 884 of "src/main/bison/cml.y"  */
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
/* Line 892 of "src/main/bison/cml.y"  */
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
/* Line 900 of "src/main/bison/cml.y"  */
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
/* Line 911 of "src/main/bison/cml.y"  */
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
/* Line 924 of "src/main/bison/cml.y"  */
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
/* Line 934 of "src/main/bison/cml.y"  */
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
/* Line 942 of "src/main/bison/cml.y"  */
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
/* Line 953 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 959 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 965 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 971 of "src/main/bison/cml.y"  */
    {
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)((yystack.valueAt (3-(1))));
  decls.add((SSingleDeclaration)((yystack.valueAt (3-(3)))));
  yyval = decls;
};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 988 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 992 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 999 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)((yystack.valueAt (1-(1)))));
  yyval = processParagraphList;
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1005 of "src/main/bison/cml.y"  */
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
/* Line 1021 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1025 of "src/main/bison/cml.y"  */
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
/* Line 1032 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1036 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1043 of "src/main/bison/cml.y"  */
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
/* Line 1060 of "src/main/bison/cml.y"  */
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
/* Line 1086 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1090 of "src/main/bison/cml.y"  */
    {
  /* --- TODO --- */
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1097 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{new Vector<ATypeSingleDeclaration>(), ((yystack.valueAt (1-(1))))};
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 374 of cmlskeleton.java.m4  */
/* Line 1101 of "src/main/bison/cml.y"  */
    {
  yyval = new Object[]{((yystack.valueAt (3-(1)))), ((yystack.valueAt (3-(3))))};
};
  break;
    



/* Line 374 of cmlskeleton.java.m4  */
/* Line 1677 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -651;
  public static final short yypact_[] =
  {
        99,   853,   -13,    97,   853,  1019,   104,   144,   145,    99,
    -651,  -651,  -651,  -651,  -651,  -651,  -651,  -651,  -651,  -651,
    -651,  -651,  -651,   331,  -651,   210,   -32,   367,  -651,  -651,
     406,    97,  -651,   269,   441,  -651,  -651,  -651,   950,   472,
    -651,   520,   536,  -651,  -651,   853,   101,   375,   855,   -13,
    2783,  -651,   180,  -651,   853,  2783,   555,   317,  -651,  -651,
    -651,    21,  -651,  -651,  -651,  -651,  -651,  -651,  -651,  -651,
    -651,  -651,  -651,  -651,  -651,    23,  -651,  1019,   -48,    13,
    -651,   855,  1489,   -32,  -651,   855,   428,   855,   415,   855,
     855,   855,   855,   855,  -651,  -651,  -651,  -651,  -651,  -651,
    -651,  -651,  -651,   817,   188,  -651,  -651,  -651,  -651,  -651,
    -651,  -651,  2783,  2783,  2239,  2802,  1050,  2783,  2783,  2783,
    2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,
    2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,   601,
     612,  2802,    14,   619,   637,  2802,  2802,  2802,  2783,   647,
    -651,   510,  -651, 11100,  -651,  -651,  -651,  -651,  -651,  -651,
    -651,  -651,  -651,  -651,  2802,  -651,   855,   499,  -651,  5233,
    2802,  2802,  -651,  2783,   855,  2783,   855,  -651,   507,   482,
     513,   513,   513,   375,   513,   513,  2783,   897,   316,  3135,
     106,  -651,   798,    -8,   817,   109,  -651,   745,   855,   855,
     704,   659,   953,  1046,   665,   665,   665,   855,   855,   855,
     855,   527,   530,  5355,    80,  5478,  -651,    22,  4498,    75,
     683,  -651,  -651,   -18,  -651,  -651,   703,  -651,  -651,    43,
    -651,  -651,   -12,  -651,  5600,  5722,   670,   670,   670,   670,
   11551,   670,   670,   670,   670,   670,   670,   670,   670,   670,
     670,   670,   670,   670,   670,  2094,  2783,  2783,   569,  -651,
      37,  2783,   707,  2783,  2783,  -651,   219,    45,  -651,  -651,
    -651,    48,   714,   -35,  5845,  2783,  -651,  2783,  2783,  -651,
    2783,  2783,  2783,   327,   640,   549,  2783,  2783,  2783,  2783,
    2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,
    2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,  2783,
    2783,  2783,  2783,  2783,  2783,  2802,   358,   122,   332,   817,
     556,   600,     8,  -651,   605,   139, 11100,   557, 11100,   566,
     580,   853,   778,   130,   582,   482,  -651,  -651,  -651,  -651,
    -651,   382,   333,  -651,  -651,   361,   369,   402,   421,   423,
    5967,   573,   578,  2605,   962,  -651,  -651,  2783,  -651,   146,
     146,   146,  2783,   146,   146,  2783,  2783,  2783,   146,   146,
    2783,  2783,  2783,  -651,   146,   375,  2802,  -651,   855,   855,
    -651,  -651,  -651,   817,   817,  -651,  1489,   855,   855,   817,
     665,   665,   817,  -651,  -651,  -651,  -651,  2783,  2802,  -651,
    2802,  2783,  2783,  -651,  2783,  2802,  2783,   855,  2783,   855,
    2783,  1050,  2783,  2802,   141, 11100,   148,  2783,  2802,  6089,
    2783,   177,  6211,  2783,  2802,   855,  2783,  2802,  2783,  2783,
    -651,  2802,  6333, 11601,   182,  6455,  1060, 11674, 11674,  -651,
    -651,  -651,  -651,  1060,  1060,  1060,  1060, 11601, 11601, 11601,
   11601, 11601, 11424, 11474, 11344, 11222, 11601, 11601, 11601, 11601,
    3464,  3464,  2094,  3708,  3586,  3586,  2094,  2094,   670,   670,
     334,   264,  -651,  -651,  -651,  -651,  -651,  2802,   855,   411,
     855,  2783,   579,   712,  -651,  2802,  -651,  2783,  2783,   482,
     585,   853,  -651,  -651,  -651,  -651,  2783,   587,   737,  -651,
    -651,  -651,  2783,   513,   146,   513,   146,   146,   146,   426,
     146,   513,   759,   595,   767,   604,   513,   513,   513,  2976,
    2375,  -651,  -651,  -651,  -651,  2783,   513,   513,  1050,  2783,
    2783,   763,  2511,  2783,  2783,  2783,  2783,   702,  2783,  1679,
     594,  -651,  -651,  -651,  2605,  -651,   217,   534,  1448,   365,
    3198,  4131,  1267,   286,  6577,   526,  6699,  4253,   418,   200,
   11100, 11100, 11100,  3135,  -651,   739,   817,   817,   982,   665,
     665, 11100,   669,    38,    77,  6823, 11100,  6945,  -651,    78,
   11601,   817, 11222, 11100,  -651,  4620,   -51,  -651,    70,  -651,
    -651, 11222,  -651,   855,  7067,  -651,  2783, 11100,  -651,   817,
   11222,  -651, 11222, 11222,     7,  -651,  -651,  2783,   484,   817,
    2802,  -651,    92,   817, 11100,   649,  2783,  -651,   787, 11100,
   11100,   623,   201,  -651, 11100,   660,  -651,   130, 11100,  -651,
    -651,  -651,  -651,  -651,  -651,  3190,  2783,  -651,  -651,   458,
    2783,   801,  2990,   804,   463,   514,   543,   587,   375,   375,
     375,   -32,  2890,   574,  -651,   586,   590,   761,  -651,  7189,
     796, 11100,   599,   608,    71,  4742,    -9,  7311,   644,  -651,
   11100,  7433,  7555,    52,  7677,     3,  2802,   815,    -3,   769,
    3398,  2605,  2605,  2605,  2783,  2605,  2605,  2783,  2783,  2783,
    2605,  2605,  2783,  2783,  2783,  -651,  -651,  2783,  -651,  2647,
    2048,  -651,  2783,   146,   146,  -651,  2783,  2783,   146,  2783,
    -651,  2783,  -651,  2783,  -651,  -651,  2783,  -651,  2783,  2783,
    -651,  1480,  -651,  2783,   777,  -651,  7799,  2783,  -651,  7921,
     855,   261,  2802,  2080,   855, 11100,  -651,  -651,  1594,   499,
     855,  -651,   813,  8043,   146, 11100,  2783,  -651,  2915,   622,
    2990,  2605,  2605,   818,    83,  -651,  -651,  -651,  -651,  -651,
    2605,   377,  2605,  -651,  -651,   590,  -651,  2783,   712,    93,
    -651,   820,  2605,  2605,  2605,  2605,  2783,  -651,  2802,   558,
     258,  -651,   513,   513,   816,  -651,   719,  2783,  2783,  2783,
    2605,  1419,   249,  1842,  4009,  1895,   607,  4865,  8166,  4375,
     234,   378, 11100, 11100, 11100,   263,  2783, 11100,  -651,  8288,
     200,  1267,  8410,  8533,   418, 11100,  8655,  8779,  8903,  9025,
   11222,   822,  -651, 11100,  -651,  -651,  9147,  -651,   817,  -651,
     287,  -651,  -651,   761, 11100,   817,   342,   565,   663,   797,
     711,   523,  2783,   548,  -651, 11100,  2605,  -651,  -651,  -651,
    2783,  2605,   587,  2941,  -651,  3093,  -651,   551,  -651,  2605,
    2605,  2783,  -651,  2915,  2915,  2742,  9269,   -36,  -651,   185,
    2783,  -651,   644,   627,   632,  2605,  2783,  9391,  3520,  3642,
    2915,  2783,  2783,  2605,  2783,  2605,  2783,  2605,  -651, 11100,
     146,  2783,  2802,  -651,  -651,  -651,  2783,  2783,  -651,  -651,
     712,  1841,  1899,   411,   761,  2783,  2783,  9513,   196,  -651,
    2915,  9635,  3113,  -651,   836,   841,   855,  -651,   607,  4988,
    2783,  2605,   650,  2605,  1891,  -651,  2605,  -651,   840,  2605,
    2915,   293,  3764,  2783,  2605,  2605,  9757,  9879,   378,  5111,
    1895, 10001,   234,  1267, 11100,    25,  4620,  3068,   812,  -651,
     428,   817,   428,   817,   355,   712, 11100, 11100,  -651,  2605,
    -651,  2783,  2783,   817,  2605,  2783, 10123,  2915,  2783,  2605,
    2915,   837,  -651,  2915,  2783,  -651,  -651,  2605,  2605,  3276,
    2915,  2915,  2783,  2605,  2783,  2605,  2605,  2783,  -651,  -651,
    -651,  2783,  -651,  2333,  -651,  -651,   314,   323,  -651, 10245,
    2605, 10367,  2915,  2605, 10489,  2915,  2915,  2605,  2783, 10612,
    1895, 10734,  1895,  -651, 10856, 11100,  -651,  -651,  2915,   761,
    -651,  -651,  2605,  2915,  2605,  2347,  2605,  2915,  3886,  2783,
    2605,  -651,   712,  -651,  2915,  -651,  -651,  2605, 10978,  1895,
    -651,  2915,  2605,  1895
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,   158,   131,   142,   219,   209,     0,     0,     0,     2,
       3,     5,     6,     7,     8,     9,   147,   148,   149,   167,
     168,   169,   170,   159,   161,     0,   136,   132,   134,   137,
       0,   143,   144,     0,   220,   222,   224,   225,     0,   210,
     212,     0,     0,     1,     4,   160,     0,     0,     0,   133,
       0,   145,     0,   227,   221,     0,     0,     0,   342,   345,
     344,     0,   339,   338,   340,   341,   346,   343,   214,   446,
     448,   447,   445,   449,   450,     0,   437,   211,     0,     0,
     162,     0,     0,     0,   141,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   186,   189,   190,   191,   192,   193,
     194,   195,   196,   140,   185,   173,   184,   197,   198,   174,
     135,   319,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     323,   292,   291,   146,   285,   287,   286,   284,   288,   289,
     306,   307,   308,   309,     0,   235,     0,     0,   223,     0,
       0,     0,   439,     0,     0,     0,     0,   213,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    31,    12,
       0,   138,   164,   186,   205,   165,   203,     0,     0,     0,
       0,     0,     0,     0,   180,   181,   179,     0,     0,     0,
       0,     0,     0,     0,     0,   282,   314,     0,   282,     0,
     412,   444,   443,     0,   436,   435,     0,   452,   453,     0,
     349,   350,     0,   347,     0,     0,   359,   360,   361,   362,
     363,   364,   365,   366,   367,   368,   369,   370,   371,   372,
     373,   374,   375,   376,   377,   378,     0,     0,     0,   462,
       0,     0,     0,     0,     0,   441,     0,     0,   456,   458,
     459,     0,     0,     0,   282,     0,   293,     0,     0,   297,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     184,     0,   244,   451,     0,     0,   217,     0,   218,     0,
       0,   251,   171,   275,     0,   150,   152,   153,   154,   155,
     156,     0,     0,    43,    42,     0,     0,     0,     0,     0,
       0,    52,     0,     0,     0,    48,    50,     0,    32,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    34,     0,     0,     0,   163,     0,     0,
     204,   166,   172,   202,   200,   178,     0,     0,     0,   201,
     176,   177,   199,   187,   188,   290,   320,     0,     0,   315,
       0,     0,     0,   324,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   282,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     335,     0,     0,   390,     0,   282,   380,   379,   381,   294,
     300,   296,   295,   383,   382,   384,   385,   386,   387,   388,
     389,   391,   392,   393,   394,   395,   396,   397,   398,   399,
     400,   401,   402,   403,   404,   405,   406,   407,   408,   409,
     410,   411,   303,   304,   301,   302,   236,     0,     0,     0,
       0,     0,     0,     0,   243,     0,   440,     0,     0,     0,
       0,   252,   253,   256,   255,   157,     0,     0,   276,   278,
      10,   151,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    53,     0,    51,     0,     0,     0,     0,
     267,    60,    61,    62,    63,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   104,   101,   112,     0,    49,     0,    31,    18,    17,
      22,     0,    21,    16,     0,     0,     0,     0,    23,    25,
      29,    28,    27,    13,   139,     0,   207,   206,     0,   183,
     182,   283,     0,     0,     0,     0,   414,     0,   413,     0,
     454,   455,   313,   305,   348,     0,     0,   356,     0,   327,
     328,   329,   463,     0,     0,   333,     0,   460,   442,   461,
     310,   457,   311,   312,     0,   330,   298,     0,     0,   237,
       0,   229,     0,   241,   245,     0,     0,   226,   442,   215,
     216,     0,     0,   254,   281,     0,   280,   277,    46,    47,
      37,    45,    44,    36,    41,     0,     0,    40,    35,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   186,     0,     0,   121,     0,     0,   244,   266,     0,
       0,    64,     0,     0,     0,     0,     0,     0,     0,   109,
     110,     0,     0,     0,     0,     0,     0,   443,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    14,    78,     0,   106,     0,
       0,    33,     0,     0,     0,   126,     0,     0,     0,     0,
     175,     0,   321,     0,   316,   318,     0,   325,     0,     0,
     351,     0,   354,     0,     0,   331,     0,     0,   336,     0,
       0,     0,     0,     0,     0,   248,   438,    11,     0,   239,
       0,   279,     0,     0,     0,    56,     0,    54,    58,     0,
       0,     0,     0,     0,     0,   417,   125,   124,   123,    65,
       0,     0,     0,   273,   274,   268,   269,     0,     0,     0,
     108,     0,     0,     0,     0,     0,     0,   102,     0,     0,
       0,   422,     0,     0,     0,   103,     0,     0,     0,     0,
       0,    70,    69,    81,     0,    83,    68,     0,     0,     0,
      71,    73,    77,    76,    75,     0,     0,   424,    15,     0,
      26,    19,     0,   129,    24,   208,     0,     0,     0,     0,
     352,     0,   357,   358,   332,   334,     0,   299,   238,   230,
       0,   233,   234,   244,   232,   242,     0,     0,     0,   267,
     240,   419,     0,     0,    38,    57,     0,    55,    93,    92,
       0,     0,     0,     0,   122,     0,   270,   271,   434,     0,
       0,     0,    91,    99,   415,     0,     0,     0,   431,     0,
       0,   105,     0,     0,     0,     0,     0,     0,     0,     0,
     118,     0,     0,     0,     0,     0,     0,     0,   107,   111,
       0,     0,     0,   322,   317,   326,     0,     0,   337,   231,
     247,     0,     0,     0,   244,     0,     0,     0,    31,    39,
      59,     0,     0,   418,    88,     0,     0,    66,    67,     0,
       0,     0,     0,     0,     0,   429,     0,   423,     0,     0,
     119,     0,     0,     0,     0,     0,     0,     0,    74,     0,
      87,     0,    72,    20,   130,     0,     0,     0,   250,   246,
     262,   260,   261,   259,     0,     0,   421,   420,    30,     0,
     100,     0,     0,   272,     0,     0,     0,   426,     0,     0,
     416,     0,   432,   433,     0,    94,    79,     0,     0,     0,
     114,   113,     0,     0,     0,     0,     0,     0,   127,   353,
     355,     0,   228,     0,   258,    95,     0,     0,    98,     0,
       0,     0,   425,     0,     0,   120,   115,     0,     0,     0,
      85,     0,    82,    80,     0,   249,   264,   265,   263,   244,
      89,    90,     0,   427,     0,     0,     0,   116,     0,     0,
       0,   128,   247,    97,   428,   430,    96,     0,     0,    86,
     257,   117,     0,    84
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -651,  -651,  -651,   857,  -651,  -651,  -320,  -178,   183,  -651,
     515,  -651,  -651,   121,   968,  -651,  -651,   107,  -136,  -651,
    -651,  -651,   827,   -71,   278,  -651,  -651,   846,  -651,  -316,
    -172,   118,  -651,   833,    88,  1385,  1327,   751,  -140,  -651,
    -651,   495,  -183,   706,   468,  -651,   825,   861,   591,  -651,
     858,  -651,  -651,   876,    16,  -651,   295,  -651,  -651,   190,
    -632,  -651,  -102,  -482,  -651,  -651,  -651,  -311,  -651,  -651,
    -651,  -651,   100,  -651,  -651,   166,  -651,  -651,  -651,   309,
     -82,   -50,   388,   576,   911,   955,   976,   535,   410,   532,
    -651,     1,  -651,  -651,   229,  -651,  -651,  -651,   547,   425,
    -651,  -633,  -651,  -650,  -651,  -651,  -651,  -651,    39,  -651,
    -105,   -22,  -147,  -651,  -184,  -138,  -255,  -128,  -135,   537,
    -651,  -651,  -651
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   189,   639,   343,   354,
     355,   515,   513,   747,   748,   931,   653,   654,   696,   555,
      13,    27,    28,   749,   344,    14,    31,    32,    15,   334,
     335,   336,    23,    24,   490,   194,   540,   105,   106,   107,
     108,   195,   196,   377,   337,    39,    40,   230,   338,    34,
      35,    36,    37,   231,   612,   833,   167,   317,   839,   322,
     483,   484,   948,   949,   992,   339,   491,   492,   493,   494,
     838,  1019,   657,   658,   765,   766,   767,   340,   498,   499,
     214,   415,   154,   155,   156,   157,   158,   159,   232,   233,
     160,   720,   161,   586,   587,   162,   163,   219,   220,   666,
     754,   626,   780,   541,   542,   922,   543,   867,   868,   660,
     265,   224,   266,   225,    76,   226,   227,   228,   267,   268,
     269,   270,   260
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "153,617,342,345,346,169,348,349,190,272,223,271,380,259,755,356,75,318,781,501,727,495,776,324,325,768,320,180,181,182,183,261,217,173,776,175,258,378,987,548,549,550,223,552,553,184,185,410,558,559,417,711,399,373,563,173,788,481,426,712,473,428,213,215,218,784,273,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,25,713,716,33,38,75,721,851,723,274,440,406,396,1,2,3,403,4,714,717,732,924,397,347,81,47,406,430,48,16,374,859,178,722,326,179,328,85,16,86,87,773,407,475,25,411,82,350,925,733,375,476,379,33,573,88,43,407,5,89,90,91,92,93,427,482,186,376,486,728,589,180,181,182,183,777,38,590,26,397,988,174,427,176,621,414,416,785,496,184,185,623,421,356,418,630,187,633,634,635,637,638,427,409,188,427,595,434,164,397,165,606,900,95,96,97,98,99,100,101,102,472,419,926,357,422,358,424,411,164,913,165,404,927,427,427,432,397,433,435,852,436,437,438,364,860,701,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,574,588,686,477,320,579,565,955,6,7,546,871,30,211,829,29,888,686,279,41,424,858,397,186,193,592,872,212,572,397,66,604,95,96,97,98,99,100,101,102,899,19,20,21,22,-171,976,551,258,502,554,556,557,187,598,560,561,562,397,42,84,547,29,397,166,608,424,1020,357,171,358,172,423,644,645,646,1021,503,366,55,48,504,571,662,663,738,575,576,279,577,284,580,191,582,370,583,371,585,397,45,424,505,591,425,198,594,372,732,597,506,55,600,366,602,603,618,688,507,810,811,380,199,1032,814,75,46,691,366,692,505,693,688,314,364,679,49,993,505,689,690,694,691,424,692,397,693,686,373,373,373,508,373,373,50,58,694,497,373,373,844,284,69,373,610,366,611,614,678,424,509,375,510,619,620,977,180,181,182,183,901,370,624,371,655,52,58,364,628,673,505,198,505,372,184,185,397,191,47,731,59,48,313,314,17,397,659,744,199,994,54,661,751,17,424,665,667,478,670,671,672,1,674,60,680,4,331,505,332,333,373,59,505,373,373,373,502,373,373,69,69,77,75,439,366,62,63,64,65,66,67,648,649,650,60,367,368,366,369,909,370,688,371,752,503,69,5,48,78,69,69,69,372,692,474,693,62,63,64,65,66,67,726,505,79,694,207,357,69,358,208,83,753,729,69,69,209,366,180,181,182,183,735,210,636,487,943,786,170,74,588,369,505,370,488,371,184,185,905,109,207,830,743,760,208,372,745,18,207,207,209,201,208,208,187,762,18,210,209,209,873,874,547,761,276,210,210,697,771,698,70,805,109,109,256,375,109,772,109,906,109,109,109,109,109,257,424,869,505,730,794,846,263,797,798,799,928,505,802,803,804,929,763,764,211,807,74,74,809,564,264,375,812,813,870,815,505,816,212,817,275,505,818,902,819,820,705,69,706,823,373,373,74,826,373,321,74,74,74,834,211,857,629,330,631,279,186,70,70,341,699,386,845,397,212,74,916,109,209,69,69,74,74,393,373,109,394,109,968,969,405,497,408,70,407,55,187,70,70,70,420,385,866,429,908,442,109,207,441,109,109,208,479,877,878,879,70,209,109,109,109,109,70,70,210,480,366,485,489,512,688,500,889,945,514,615,283,284,616,382,69,622,692,625,693,221,627,640,207,373,285,641,208,869,694,642,668,84,209,632,643,709,69,406,69,210,56,57,907,69,58,824,737,191,734,69,911,69,313,314,207,736,69,373,208,740,481,919,69,746,209,69,750,770,74,69,807,210,315,316,94,207,932,787,789,208,842,936,937,875,939,209,941,876,850,59,861,944,210,903,207,376,946,947,208,897,74,74,656,961,209,956,957,482,962,70,974,210,60,991,1003,69,44,676,854,545,966,847,85,69,86,87,110,51,80,996,997,568,677,979,62,63,64,65,66,67,88,70,70,262,89,90,91,92,93,68,1,381,177,351,4,331,352,332,333,53,353,74,168,109,109,999,69,739,1001,954,191,109,109,109,1004,69,756,757,758,840,1040,856,1009,74,1011,74,741,1014,664,904,74,1015,109,584,109,5,74,989,74,71,822,578,70,74,19,20,21,22,1028,74,109,675,74,972,601,1,74,55,351,4,331,352,332,333,70,544,70,0,0,1038,207,70,0,0,208,0,0,70,0,70,209,387,0,72,70,0,0,210,69,85,70,86,87,70,0,0,0,70,0,0,5,0,74,109,73,109,0,88,0,0,74,89,90,91,92,93,71,71,191,19,20,21,22,-171,0,94,-171,0,56,57,0,66,58,95,96,97,98,99,100,101,102,0,71,70,0,0,71,71,71,0,0,70,0,74,69,0,0,55,0,0,72,72,74,207,0,71,0,208,0,279,0,71,71,209,388,0,0,59,0,0,210,73,73,0,0,0,72,0,0,0,72,72,72,109,70,0,0,-171,-171,69,60,-171,0,70,0,0,0,73,0,72,69,73,73,73,0,72,72,0,109,0,61,0,62,63,64,65,66,67,56,57,73,0,58,0,0,74,73,73,0,0,283,284,0,0,0,710,-171,0,0,0,0,0,193,0,285,0,69,0,66,0,95,96,97,98,99,100,101,102,0,-171,0,0,0,0,305,0,70,59,309,310,311,312,313,314,71,19,20,21,22,-171,0,-171,-171,-171,-171,-171,-171,0,0,0,60,74,315,316,0,0,0,0,0,0,0,0,0,0,0,0,71,71,0,0,229,0,62,63,64,65,66,67,72,0,0,0,0,0,0,0,0,0,0,0,0,0,70,0,0,0,74,0,0,73,0,0,0,0,0,109,0,74,0,109,72,72,0,109,0,109,0,0,0,0,69,359,360,0,0,0,0,71,0,0,0,73,73,0,0,0,0,70,0,0,364,0,0,0,0,0,0,0,70,71,0,71,69,74,0,0,71,0,0,0,0,539,71,0,71,0,0,0,0,71,0,72,0,0,0,71,0,0,71,0,0,0,71,0,0,0,0,0,0,0,0,0,73,72,70,72,0,0,0,0,72,0,0,0,0,0,72,0,72,0,0,0,0,72,73,104,73,0,0,72,0,73,72,0,0,0,72,73,71,73,0,0,0,0,73,0,71,0,0,0,73,0,0,73,0,0,0,73,104,104,366,0,104,0,104,0,104,104,104,104,104,367,368,0,369,0,370,74,371,0,0,0,72,103,682,0,109,109,372,71,72,0,0,0,0,0,0,0,71,0,0,109,686,73,0,0,0,0,0,74,0,73,0,360,0,0,192,0,70,0,197,0,200,0,202,203,204,205,206,0,0,364,0,72,0,0,0,652,0,0,0,0,72,104,0,0,0,55,0,0,70,104,0,104,73,0,85,0,86,87,0,0,700,73,0,0,0,0,0,0,0,71,104,0,88,104,104,0,89,90,91,92,93,0,104,104,104,104,0,0,0,0,0,0,0,821,0,221,0,0,0,319,0,0,0,0,0,0,0,327,0,329,366,0,0,72,688,0,56,57,0,0,58,689,690,0,691,0,692,0,693,0,0,383,384,0,73,71,0,0,694,366,389,390,391,392,0,0,0,0,0,0,367,368,0,369,0,370,0,371,0,85,0,836,87,0,0,59,0,372,0,0,0,0,0,0,0,0,0,88,0,72,71,89,90,91,92,93,0,0,60,0,0,71,0,0,0,0,0,791,792,793,73,795,796,0,0,0,800,801,222,0,62,63,64,65,66,67,0,193,0,0,0,0,0,66,72,95,96,97,98,99,100,101,102,0,0,72,0,71,0,0,0,681,682,683,0,73,0,684,0,0,0,0,0,104,104,0,73,0,0,685,686,104,104,104,0,0,0,848,849,0,0,0,0,0,0,0,853,0,855,0,0,72,104,0,104,0,0,0,862,863,864,865,0,0,0,0,0,0,0,0,104,0,73,0,0,0,880,0,0,0,0,566,567,0,0,0,0,0,0,0,569,570,94,0,0,0,0,0,66,0,95,96,97,98,99,100,101,102,0,0,581,0,327,0,0,0,0,0,0,0,0,71,0,104,0,104,0,0,599,0,0,0,910,0,0,0,0,912,0,687,366,0,0,0,688,917,918,0,0,0,0,689,690,71,691,0,692,0,693,0,0,930,0,0,0,72,0,0,694,938,695,940,0,942,681,682,85,0,950,87,684,609,0,613,0,0,73,0,0,0,0,0,685,686,88,0,0,72,89,90,91,92,93,0,0,0,0,967,0,970,0,0,973,104,0,975,0,0,73,0,980,981,0,0,0,0,55,681,682,0,0,0,0,0,85,0,952,87,104,0,0,0,0,0,0,995,686,0,0,0,998,0,88,0,0,1002,89,90,91,92,93,0,0,1005,1006,0,0,0,0,1010,0,1012,1013,0,971,0,221,0,0,1018,0,0,0,0,0,0,1023,0,0,1025,0,0,0,1027,0,0,724,56,57,0,0,58,687,366,0,0,0,688,1033,0,1034,0,1036,779,689,690,1039,691,0,692,0,693,0,1041,0,0,0,0,1043,0,0,694,0,0,0,0,0,0,0,94,0,0,0,0,0,66,59,95,96,97,98,99,100,101,102,0,366,0,0,0,688,0,0,0,0,0,0,689,690,60,691,0,692,0,693,104,0,0,0,104,681,682,683,104,694,104,684,0,0,222,0,62,63,64,65,66,67,94,685,686,0,0,0,66,0,95,96,97,98,99,100,101,102,111,0,112,0,0,113,0,0,0,0,0,0,0,0,114,0,0,0,279,0,828,0,0,0,835,0,0,0,837,0,841,0,0,0,0,0,0,115,831,832,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,0,0,283,284,0,0,0,0,687,366,0,0,0,688,0,0,285,779,0,0,689,690,0,691,0,692,0,693,0,0,0,0,145,146,147,59,0,694,0,808,309,310,311,312,313,314,104,104,148,0,0,0,0,0,0,0,0,0,60,0,0,104,0,0,315,316,149,0,150,0,0,0,111,0,112,0,0,113,151,152,62,63,64,65,66,67,114,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,951,953,0,0,0,115,0,0,0,0,0,116,0,117,0,963,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,0,0,0,0,0,0,516,517,518,519,0,0,520,0,521,522,523,524,525,0,681,682,683,0,526,527,684,0,0,0,0,0,145,146,147,59,0,0,685,686,0,0,0,0,0,1016,1017,0,148,111,528,112,529,0,113,0,530,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,656,0,0,116,531,117,532,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,533,0,0,0,0,534,0,0,0,0,0,0,0,0,687,366,0,0,0,688,535,536,537,0,0,538,689,690,0,691,0,692,0,693,145,146,147,59,94,0,0,0,0,694,0,1035,0,0,0,0,148,111,0,112,0,669,113,0,0,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,0,0,0,0,0,0,516,517,518,519,0,0,520,0,521,522,523,524,525,0,0,0,0,0,526,527,0,0,0,0,0,0,145,146,147,59,0,0,0,0,0,0,0,0,0,0,0,0,148,111,528,112,529,0,113,0,530,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,0,0,0,116,531,117,532,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,806,142,143,144,0,0,533,0,0,0,0,534,0,0,0,681,682,683,0,0,0,684,0,0,0,0,535,536,537,0,0,538,0,685,686,0,0,0,0,0,145,146,147,59,94,0,0,0,0,0,0,0,0,0,0,0,148,111,0,112,0,0,113,920,921,0,60,0,0,0,0,114,0,0,149,0,150,0,55,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,221,0,139,140,0,0,58,141,0,142,143,144,0,0,0,687,366,0,0,0,688,56,57,0,0,58,0,689,690,0,691,0,692,0,693,681,682,683,0,759,0,684,0,0,694,0,0,0,145,146,147,59,0,685,686,0,0,0,0,0,681,682,683,0,148,0,684,0,0,0,59,0,0,0,60,0,0,0,685,686,0,0,149,0,150,0,681,682,683,0,914,0,684,60,151,152,62,63,64,65,66,67,0,0,685,686,0,0,0,0,0,0,0,222,0,62,63,64,65,66,67,516,517,518,519,0,0,520,0,521,522,523,524,525,0,516,517,518,519,526,527,520,0,521,522,523,524,525,0,0,0,0,0,526,527,0,0,0,0,0,0,0,0,687,366,528,0,529,688,0,0,530,0,0,0,689,690,0,691,528,692,529,693,0,0,530,0,0,687,366,0,0,694,688,0,0,0,0,0,0,689,690,0,691,0,692,647,693,531,0,532,277,0,0,687,366,278,694,279,688,0,0,531,0,532,0,689,690,0,691,0,692,0,693,0,0,280,0,681,682,683,0,915,694,684,648,649,650,0,533,0,0,0,0,534,0,685,686,681,682,683,0,960,533,684,281,282,0,534,535,536,537,0,0,538,0,685,686,0,0,359,360,361,535,536,537,362,651,538,283,284,0,0,0,0,0,0,0,363,364,0,651,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,359,360,361,0,742,0,362,0,359,360,0,0,0,0,362,0,315,316,363,364,0,0,0,0,0,0,363,364,0,0,0,687,366,0,0,0,688,990,0,0,0,0,0,689,690,0,691,0,692,0,693,687,366,0,0,0,688,0,0,0,694,0,0,689,690,0,691,0,692,0,693,0,0,365,366,0,0,0,0,0,694,0,0,0,277,367,368,0,369,278,370,279,371,0,0,0,0,0,0,0,0,0,372,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,365,366,0,0,0,0,0,0,365,366,281,282,367,368,0,369,0,370,0,371,367,368,0,369,0,370,0,371,0,372,0,0,0,0,283,284,0,372,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,1007,0,0,1008,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,279,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,280,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,283,284,0,280,0,0,0,790,0,0,0,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,279,0,0,0,0,283,284,0,0,0,0,315,316,0,0,0,0,0,280,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,283,284,0,280,0,0,0,934,0,0,0,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,305,306,0,0,309,310,311,312,313,314,0,0,0,0,0,0,0,279,0,0,0,0,283,284,0,0,0,0,315,316,0,0,0,0,0,280,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,283,284,0,280,0,0,0,935,0,0,0,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,305,0,0,0,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,315,316,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,978,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,881,882,0,0,0,0,280,0,0,1037,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,702,0,0,0,883,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,703,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,708,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,400,0,887,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,401,402,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,718,719,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,774,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,775,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,884,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,964,885,0,0,0,0,0,0,315,316,965,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,984,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,323,279,0,0,0,985,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,395,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,398,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,412,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,413,0,0,315,316,431,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,511,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,593,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,605,279,0,0,0,0,596,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,607,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,704,0,0,0,0,707,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,715,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,402,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,725,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,769,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,778,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,782,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,783,774,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,825,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,827,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,843,0,0,0,0,0,0,315,316,886,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,890,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,891,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,892,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,893,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,894,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,895,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,896,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,923,0,898,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,933,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,958,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,959,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,982,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,983,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,986,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1000,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1022,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1024,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1026,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,1029,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,1030,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1042,1031,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,0,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,0,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,277,0,0,0,0,0,0,279,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,280,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,277,0,0,0,281,282,0,279,0,0,315,316,0,0,0,0,0,0,0,0,0,0,0,0,0,280,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,281,282,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,277,283,284,0,0,0,0,279,0,0,0,0,0,315,316,285,286,287,288,289,290,291,292,293,294,280,296,0,0,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,281,282,0,279,0,0,0,0,0,315,316,0,0,0,0,0,0,0,0,0,0,280,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,0,0,281,282,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,283,284,279,0,0,0,0,0,0,0,0,0,315,316,285,286,287,288,289,0,280,0,0,0,0,0,0,0,0,0,0,0,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,315,316";
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
  public static final String yyCheckString = "50,483,180,181,182,55,184,185,79,147,115,146,195,141,647,187,38,164,668,335,13,332,31,170,171,657,166,14,15,16,17,17,114,12,31,12,141,45,13,359,360,361,147,363,364,32,33,59,368,369,13,13,30,189,374,12,59,49,13,21,315,13,112,113,114,13,148,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,1,13,13,4,5,116,146,13,27,148,283,118,21,3,4,5,30,7,30,30,17,146,146,183,12,146,118,151,149,0,13,27,169,173,173,172,175,17,9,19,20,59,149,316,45,146,34,186,173,46,33,18,149,54,398,35,0,149,48,39,40,41,42,43,146,146,142,47,18,151,18,14,15,16,17,173,77,18,180,146,144,149,146,149,489,256,257,173,47,32,33,491,263,354,146,504,172,506,507,508,509,510,146,149,180,146,18,278,17,146,19,18,833,188,189,190,191,192,193,194,195,315,261,27,17,264,19,146,146,17,852,19,146,872,146,146,275,146,277,278,146,280,281,282,33,141,18,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,400,413,33,146,409,405,376,904,174,175,357,18,180,90,18,2,18,33,19,180,146,768,146,142,180,418,33,104,398,146,186,431,188,189,190,191,192,193,194,195,18,176,177,178,179,180,18,362,418,118,365,366,367,172,424,370,371,372,146,180,47,180,49,146,149,477,146,18,17,17,19,19,118,516,517,518,18,146,143,17,149,13,397,526,527,149,401,402,19,404,91,406,79,408,159,410,161,412,146,33,146,33,417,149,27,420,171,17,423,13,17,426,143,428,429,485,147,13,703,704,568,44,1019,708,411,180,157,143,159,33,161,147,133,33,537,33,46,33,154,155,171,157,146,159,146,161,33,548,549,550,13,552,553,12,92,171,333,558,559,744,91,38,563,17,143,19,481,537,146,13,33,13,487,488,146,14,15,16,17,102,159,496,161,519,180,92,33,502,535,33,27,33,171,32,33,146,183,146,610,137,149,132,133,0,146,520,13,44,955,33,525,13,9,146,529,530,149,532,533,534,3,536,160,538,7,8,33,10,11,630,137,33,633,634,635,118,637,638,115,116,33,528,180,143,182,183,184,185,186,187,138,139,140,160,154,155,143,157,843,159,147,161,13,146,141,48,149,12,145,146,147,171,159,180,161,182,183,184,185,186,187,596,33,12,171,27,17,164,19,31,180,13,607,170,171,37,143,14,15,16,17,616,44,142,12,890,676,17,38,721,157,33,159,12,161,32,33,59,48,27,732,636,13,31,171,640,0,27,27,37,180,31,31,172,13,9,44,37,37,782,783,180,33,98,44,44,17,13,19,38,697,81,82,17,33,85,13,87,100,89,90,91,92,93,17,146,778,33,149,684,13,17,687,688,689,13,33,692,693,694,13,56,57,90,699,115,116,702,375,17,33,706,707,100,709,33,711,104,713,17,33,716,102,718,719,144,283,146,723,810,811,141,727,814,180,145,146,147,733,90,767,503,180,505,19,142,115,116,180,100,36,746,146,104,164,149,166,37,315,316,170,171,180,844,174,180,176,62,63,31,627,13,141,149,17,172,145,146,147,17,21,776,13,180,180,195,27,92,198,199,31,180,787,788,789,164,37,207,208,209,210,170,171,44,149,143,146,172,180,147,173,806,892,180,180,90,91,50,18,376,180,159,180,161,67,33,12,27,909,104,180,31,924,171,12,17,503,37,505,180,46,398,118,400,44,88,89,842,405,92,18,173,519,149,411,850,413,132,133,27,18,418,943,31,149,49,861,424,12,37,427,12,21,283,431,870,44,152,153,180,27,876,12,59,31,17,881,882,17,884,37,886,118,20,137,20,891,44,180,27,47,896,897,31,27,315,316,55,17,37,905,906,146,17,283,20,44,160,51,27,477,9,165,761,354,920,750,17,485,19,20,49,31,45,961,962,386,180,933,182,183,184,185,186,187,35,315,316,142,39,40,41,42,43,38,3,195,77,6,7,8,9,10,11,33,13,376,54,378,379,965,528,622,968,903,642,386,387,388,974,537,648,649,650,739,1032,765,982,398,984,400,627,987,528,839,405,991,407,411,409,48,411,946,413,38,721,404,376,418,176,177,178,179,1008,424,425,536,427,924,427,3,431,17,6,7,8,9,10,11,398,13,400,-1,-1,1029,27,405,-1,-1,31,-1,-1,411,-1,413,37,38,-1,38,418,-1,-1,44,610,17,424,19,20,427,-1,-1,-1,431,-1,-1,48,-1,477,478,38,480,-1,35,-1,-1,485,39,40,41,42,43,115,116,750,176,177,178,179,180,-1,180,17,-1,88,89,-1,186,92,188,189,190,191,192,193,194,195,-1,141,477,-1,-1,145,146,147,-1,-1,485,-1,528,676,-1,-1,17,-1,-1,115,116,537,27,-1,164,-1,31,-1,19,-1,170,171,37,38,-1,-1,137,-1,-1,44,115,116,-1,-1,-1,141,-1,-1,-1,145,146,147,568,528,-1,-1,88,89,721,160,92,-1,537,-1,-1,-1,141,-1,164,732,145,146,147,-1,170,171,-1,593,-1,180,-1,182,183,184,185,186,187,88,89,164,-1,92,-1,-1,610,170,171,-1,-1,90,91,-1,-1,-1,173,137,-1,-1,-1,-1,-1,180,-1,104,-1,778,-1,186,-1,188,189,190,191,192,193,194,195,-1,160,-1,-1,-1,-1,124,-1,610,137,128,129,130,131,132,133,283,176,177,178,179,180,-1,182,183,184,185,186,187,-1,-1,-1,160,676,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,315,316,-1,-1,180,-1,182,183,184,185,186,187,283,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,676,-1,-1,-1,721,-1,-1,283,-1,-1,-1,-1,-1,730,-1,732,-1,734,315,316,-1,738,-1,740,-1,-1,-1,-1,892,14,15,-1,-1,-1,-1,376,-1,-1,-1,315,316,-1,-1,-1,-1,721,-1,-1,33,-1,-1,-1,-1,-1,-1,-1,732,398,-1,400,924,778,-1,-1,405,-1,-1,-1,-1,353,411,-1,413,-1,-1,-1,-1,418,-1,376,-1,-1,-1,424,-1,-1,427,-1,-1,-1,431,-1,-1,-1,-1,-1,-1,-1,-1,-1,376,398,778,400,-1,-1,-1,-1,405,-1,-1,-1,-1,-1,411,-1,413,-1,-1,-1,-1,418,398,48,400,-1,-1,424,-1,405,427,-1,-1,-1,431,411,477,413,-1,-1,-1,-1,418,-1,485,-1,-1,-1,424,-1,-1,427,-1,-1,-1,431,81,82,143,-1,85,-1,87,-1,89,90,91,92,93,154,155,-1,157,-1,159,892,161,-1,-1,-1,477,48,15,-1,901,902,171,528,485,-1,-1,-1,-1,-1,-1,-1,537,-1,-1,916,33,477,-1,-1,-1,-1,-1,924,-1,485,-1,15,-1,-1,81,-1,892,-1,85,-1,87,-1,89,90,91,92,93,-1,-1,33,-1,528,-1,-1,-1,519,-1,-1,-1,-1,537,166,-1,-1,-1,17,-1,-1,924,174,-1,176,528,-1,17,-1,19,20,-1,-1,544,537,-1,-1,-1,-1,-1,-1,-1,610,195,-1,35,198,199,-1,39,40,41,42,43,-1,207,208,209,210,-1,-1,-1,-1,-1,-1,-1,65,-1,67,-1,-1,-1,166,-1,-1,-1,-1,-1,-1,-1,174,-1,176,143,-1,-1,610,147,-1,88,89,-1,-1,92,154,155,-1,157,-1,159,-1,161,-1,-1,198,199,-1,610,676,-1,-1,171,143,207,208,209,210,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,17,-1,19,20,-1,-1,137,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,35,-1,676,721,39,40,41,42,43,-1,-1,160,-1,-1,732,-1,-1,-1,-1,-1,681,682,683,676,685,686,-1,-1,-1,690,691,180,-1,182,183,184,185,186,187,-1,180,-1,-1,-1,-1,-1,186,721,188,189,190,191,192,193,194,195,-1,-1,732,-1,778,-1,-1,-1,14,15,16,-1,721,-1,20,-1,-1,-1,-1,-1,378,379,-1,732,-1,-1,32,33,386,387,388,-1,-1,-1,751,752,-1,-1,-1,-1,-1,-1,-1,760,-1,762,-1,-1,778,407,-1,409,-1,-1,-1,772,773,774,775,-1,-1,-1,-1,-1,-1,-1,-1,425,-1,778,-1,-1,-1,790,-1,-1,-1,-1,378,379,-1,-1,-1,-1,-1,-1,-1,387,388,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,407,-1,409,-1,-1,-1,-1,-1,-1,-1,-1,892,-1,478,-1,480,-1,-1,425,-1,-1,-1,846,-1,-1,-1,-1,851,-1,142,143,-1,-1,-1,147,859,860,-1,-1,-1,-1,154,155,924,157,-1,159,-1,161,-1,-1,875,-1,-1,-1,892,-1,-1,171,883,173,885,-1,887,14,15,17,-1,19,20,20,478,-1,480,-1,-1,892,-1,-1,-1,-1,-1,32,33,35,-1,-1,924,39,40,41,42,43,-1,-1,-1,-1,921,-1,923,-1,-1,926,568,-1,929,-1,-1,924,-1,934,935,-1,-1,-1,-1,17,14,15,-1,-1,-1,-1,-1,17,-1,19,20,593,-1,-1,-1,-1,-1,-1,959,33,-1,-1,-1,964,-1,35,-1,-1,969,39,40,41,42,43,-1,-1,977,978,-1,-1,-1,-1,983,-1,985,986,-1,65,-1,67,-1,-1,993,-1,-1,-1,-1,-1,-1,1000,-1,-1,1003,-1,-1,-1,1007,-1,-1,593,88,89,-1,-1,92,142,143,-1,-1,-1,147,1022,-1,1024,-1,1026,668,154,155,1030,157,-1,159,-1,161,-1,1037,-1,-1,-1,-1,1042,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,186,137,188,189,190,191,192,193,194,195,-1,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,160,157,-1,159,-1,161,730,-1,-1,-1,734,14,15,16,738,171,740,20,-1,-1,180,-1,182,183,184,185,186,187,180,32,33,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,15,-1,17,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,29,-1,-1,-1,19,-1,730,-1,-1,-1,734,-1,-1,-1,738,-1,740,-1,-1,-1,-1,-1,-1,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,104,872,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,134,135,136,137,-1,171,-1,173,128,129,130,131,132,133,901,902,150,-1,-1,-1,-1,-1,-1,-1,-1,-1,160,-1,-1,916,-1,-1,152,153,168,-1,170,-1,-1,-1,15,-1,17,-1,-1,20,180,181,182,183,184,185,186,187,29,30,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,901,902,-1,-1,-1,52,-1,-1,-1,-1,-1,58,-1,60,-1,916,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,53,54,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,19,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,94,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,162,163,164,-1,-1,167,-1,32,33,-1,-1,-1,-1,-1,134,135,136,137,180,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,62,63,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,17,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,67,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,142,143,-1,-1,-1,147,88,89,-1,-1,92,-1,154,155,-1,157,-1,159,-1,161,14,15,16,-1,18,-1,20,-1,-1,171,-1,-1,-1,134,135,136,137,-1,32,33,-1,-1,-1,-1,-1,14,15,16,-1,150,-1,20,-1,-1,-1,137,-1,-1,-1,160,-1,-1,-1,32,33,-1,-1,168,-1,170,-1,14,15,16,-1,18,-1,20,160,180,181,182,183,184,185,186,187,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,180,-1,182,183,184,185,186,187,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,17,32,33,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,142,143,58,-1,60,147,-1,-1,64,-1,-1,-1,154,155,-1,157,58,159,60,161,-1,-1,64,-1,-1,142,143,-1,-1,171,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,99,161,101,-1,103,12,-1,-1,142,143,17,171,19,147,-1,-1,101,-1,103,-1,154,155,-1,157,-1,159,-1,161,-1,-1,37,-1,14,15,16,-1,18,171,20,138,139,140,-1,142,-1,-1,-1,-1,147,-1,32,33,14,15,16,-1,18,142,20,66,67,-1,147,162,163,164,-1,-1,167,-1,32,33,-1,-1,14,15,16,162,163,164,20,180,167,90,91,-1,-1,-1,-1,-1,-1,-1,32,33,-1,180,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,14,15,16,-1,18,-1,20,-1,14,15,-1,-1,-1,-1,20,-1,152,153,32,33,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,142,143,-1,-1,-1,147,173,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,-1,-1,147,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,142,143,-1,-1,-1,-1,-1,171,-1,-1,-1,12,154,155,-1,157,17,159,19,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,-1,-1,-1,142,143,66,67,154,155,-1,157,-1,159,-1,161,154,155,-1,157,-1,159,-1,161,-1,171,-1,-1,-1,-1,90,91,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
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
      64,   101,   103,   142,   147,   162,   163,   164,   167,   225,
     247,   314,   315,   317,    13,   221,   291,   180,   217,   217,
     217,   292,   217,   217,   292,   230,   292,   292,   217,   217,
     292,   292,   292,   217,   235,   321,   246,   246,   252,   246,
     246,   292,   321,   327,   329,   292,   292,   292,   309,   329,
     292,   246,   292,   292,   300,   292,   304,   305,   323,    18,
      18,   292,   328,   146,   292,    18,   146,   292,   321,   246,
     292,   330,   292,   292,   329,    18,    18,    86,   323,   246,
      17,    19,   265,   246,   292,   180,    50,   274,   321,   292,
     292,   240,   180,   278,   292,   180,   312,    33,   292,   219,
     217,   219,   235,   217,   217,   217,   142,   217,   217,   218,
      12,   180,    12,   180,   218,   218,   218,    99,   138,   139,
     140,   180,   225,   227,   228,   234,    55,   283,   284,   292,
     320,   292,   218,   218,   299,   292,   310,   292,    17,    19,
     292,   292,   292,   291,   292,   310,   165,   180,   321,   326,
     292,    14,    15,    16,    20,    32,    33,   142,   147,   154,
     155,   157,   159,   161,   171,   173,   229,    17,    19,   100,
     225,    18,    32,   158,   145,   144,   146,    28,   156,    46,
     173,    13,    21,    13,    30,    30,    13,    30,    62,    63,
     302,   146,   173,    27,   246,    18,   292,    13,   151,   292,
     149,   323,    17,    46,   149,   292,    18,   173,   149,   267,
     149,   290,    18,   292,    13,   292,    12,   224,   225,   234,
      12,    13,    13,    13,   311,   312,   235,   235,   235,    18,
      13,    33,    13,    56,    57,   285,   286,   287,   271,    21,
      21,    13,    13,    59,    27,    61,    31,   173,   149,   247,
     313,   314,   145,   148,    13,   173,   321,    12,    59,    59,
     163,   225,   225,   225,   292,   225,   225,   292,   292,   292,
     225,   225,   292,   292,   292,   291,    94,   292,   173,   292,
     217,   217,   292,   292,   217,   292,   292,   292,   292,   292,
     292,    65,   305,   292,    18,    18,   292,    18,   246,    18,
     323,    53,    54,   266,   292,   246,    19,   246,   281,   269,
     270,   246,    17,   145,   217,   292,    13,   224,   225,   225,
      20,    13,   146,   225,   228,   225,   286,   291,   274,    27,
     141,    20,   225,   225,   225,   225,   292,   318,   319,   323,
     100,    18,    33,   218,   218,    17,   118,   292,   292,   292,
     225,    31,    32,   158,    31,   145,    31,   156,    18,   292,
      21,    28,    31,    21,    30,    30,    61,    27,   151,    18,
     271,   102,   102,   180,   283,    59,   100,   292,   180,   217,
     225,   292,   225,   312,    18,    18,   149,   225,   225,   292,
      62,    63,   316,    27,   146,   173,    27,   314,    13,    13,
     225,   226,   292,    38,   163,   163,   292,   292,   225,   292,
     225,   292,   225,   217,   292,   329,   292,   292,   273,   274,
      19,   246,    19,   246,   265,   271,   292,   292,    18,    21,
      18,    17,    17,   246,    21,    31,   292,   225,    62,    63,
     225,    65,   319,   225,    20,   225,    18,   146,   163,   292,
     225,   225,    32,    21,    31,   145,   148,    13,   144,   302,
     173,    51,   275,    46,   274,   225,   291,   291,   225,   292,
      61,   292,   225,    27,   292,   225,   225,   163,   166,   292,
     225,   292,   225,   225,   292,   292,    53,    54,   225,   282,
      18,    18,    21,   225,    61,   225,    21,   225,   292,    31,
     145,   144,   271,   225,   225,   173,   225,   163,   292,   225,
     273,   225,    21,   225
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
     230,   231,   231,   231,   232,   232,   233,   233,   234,   234,
     235,   235,   236,   236,   237,   237,   238,   239,   239,   239,
     240,   240,   241,   241,   241,   241,   241,   241,   242,   242,
     242,   243,   243,   244,   244,   244,   244,   245,   245,   245,
     245,   245,   246,   246,   246,   246,   246,   246,   246,   246,
     246,   246,   246,   246,   246,   246,   247,   247,   247,   248,
     248,   248,   248,   248,   248,   248,   248,   249,   249,   250,
     250,   251,   251,   252,   252,   253,   253,   253,   254,   255,
     255,   255,   256,   256,   257,   258,   258,   258,   258,   259,
     259,   259,   260,   260,   261,   261,   262,   263,   264,   265,
     265,   265,   266,   266,   266,   267,   267,   268,   268,   269,
     269,   270,   270,   271,   271,   272,   273,   273,   274,   275,
     275,   276,   276,   277,   277,   278,   278,   279,   280,   281,
     281,   281,   281,   282,   282,   282,   283,   283,   284,   285,
     285,   286,   286,   287,   287,   288,   288,   288,   289,   289,
     290,   290,   291,   291,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   292,   292,
     292,   292,   292,   292,   292,   292,   292,   292,   293,   293,
     294,   295,   296,   296,   296,   297,   298,   299,   299,   300,
     300,   301,   302,   302,   303,   303,   304,   304,   305,   306,
     306,   306,   306,   306,   306,   306,   306,   306,   306,   306,
     306,   306,   306,   306,   306,   306,   306,   306,   306,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   307,   307,   307,   307,   307,   307,   307,   307,
     307,   307,   308,   308,   309,   310,   310,   311,   311,   312,
     312,   312,   313,   313,   314,   315,   315,   316,   316,   317,
     317,   318,   318,   319,   320,   321,   321,   322,   322,   322,
     322,   323,   323,   324,   324,   325,   325,   325,   325,   325,
     325,   325,   326,   326,   327,   328,   329,   329,   330,   330,
     331,   332,   333,   333
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
       2,     4,     1,     6,     6,     7,     8,    10,     4,     1,
       3,     1,     3,     2,     2,     2,     3,     7,     9,     3,
       5,     1,     2,     3,     1,     3,     1,     1,     1,     3,
       3,     3,     1,     2,     1,     2,     3,     1,     1,     1,
       1,     2,     1,     1,     1,     1,     1,     2,     1,     2,
       3,     1,     3,     5,     4,     4,     5,     1,     1,     1,
       1,     0,     3,     1,     1,     5,     3,     3,     3,     2,
       2,     2,     4,     4,     1,     1,     1,     3,     3,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     3,
       3,     3,     3,     1,     2,     1,     3,     3,     4,     1,
       2,     3,     1,     3,     2,     5,     5,     3,     3,     1,
       2,     3,     1,     3,     1,     1,     6,     2,    10,     1,
       3,     4,     1,     1,     1,     1,     3,     3,     5,     0,
       1,     3,     5,     1,     0,     2,     1,     0,     2,     2,
       0,     1,     2,     1,     2,     1,     1,    10,     7,     3,
       3,     3,     3,     1,     1,     1,     1,     0,     2,     1,
       2,     2,     4,     1,     1,     1,     2,     3,     1,     3,
       2,     2,     1,     3,     1,     1,     1,     1,     1,     1,
       3,     1,     1,     2,     3,     3,     3,     2,     4,     6,
       3,     3,     3,     3,     3,     4,     1,     1,     1,     1,
       4,     4,     4,     4,     2,     3,     5,     7,     5,     1,
       3,     5,     7,     1,     3,     5,     7,     4,     4,     4,
       4,     5,     6,     4,     6,     3,     5,     7,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     3,     1,
       1,     5,     2,     5,     5,     9,     1,     3,     3,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     1,     3,     3,     3,     5,     1,     3,     3,
       5,     5,     1,     3,     3,     7,     6,     4,     5,     5,
       9,     1,     3,     3,     3,     1,     1,     1,     6,     2,
       4,     1,     3,     1,     1,     1,     1,     1,     1,     1,
       1,     3,     1,     1,     3,     3,     1,     3,     1,     1,
       3,     3,     1,     3
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
     103,    19,    -1,   103,   292,    -1,   247,   100,    94,   292,
      -1,   317,    -1,   164,   326,    59,   292,   163,   225,    -1,
     164,   321,    59,   292,   163,   225,    -1,   164,   165,   321,
     118,   292,   163,   225,    -1,   164,   180,    12,   292,    38,
     292,   163,   225,    -1,   164,   180,    12,   292,    38,   292,
     166,   292,   163,   225,    -1,   167,   292,   163,   225,    -1,
     225,    -1,   226,   146,   225,    -1,   228,    -1,   227,    33,
     228,    -1,   140,   235,    -1,   139,   235,    -1,   138,   235,
      -1,   143,   230,   144,    -1,   143,   292,    28,   292,    31,
     329,   144,    -1,   143,   292,    28,   292,    31,   329,    13,
     292,   144,    -1,   292,    28,   292,    -1,   230,   146,   292,
      28,   292,    -1,     4,    -1,     4,   232,    -1,     4,   232,
      33,    -1,   233,    -1,   232,    33,   233,    -1,   180,    -1,
     235,    -1,   235,    -1,   234,    33,   235,    -1,   180,   149,
     246,    -1,   180,   146,   235,    -1,     5,    -1,     5,   237,
      -1,   238,    -1,   237,   238,    -1,   180,    12,   292,    -1,
     242,    -1,   255,    -1,   259,    -1,   241,    -1,   241,   240,
      -1,   242,    -1,   255,    -1,   259,    -1,   276,    -1,   288,
      -1,    10,   278,    -1,     3,    -1,     3,   243,    -1,     3,
     243,    33,    -1,   244,    -1,   243,    33,   244,    -1,   245,
     180,    12,   246,   254,    -1,   245,   180,    12,   246,    -1,
     245,   180,    34,   252,    -1,   245,   180,    34,   252,   254,
      -1,   176,    -1,   177,    -1,   178,    -1,   179,    -1,    -1,
      17,   246,    18,    -1,   248,    -1,   298,    -1,    35,   180,
      36,   252,   173,    -1,   246,    31,   246,    -1,   246,    37,
     246,    -1,    20,   246,    21,    -1,    43,   246,    -1,    41,
     246,    -1,    42,   246,    -1,    40,   246,    38,   246,    -1,
      39,   246,    38,   246,    -1,   249,    -1,   247,    -1,   180,
      -1,   247,    90,   180,    -1,   247,   104,   180,    -1,   188,
      -1,   189,    -1,   190,    -1,   191,    -1,   192,    -1,   193,
      -1,   194,    -1,   195,    -1,   250,    -1,   251,    -1,   246,
      44,   246,    -1,    19,    44,   246,    -1,   246,    27,   246,
      -1,    19,    27,   246,    -1,   253,    -1,   252,   253,    -1,
     246,    -1,   180,   149,   246,    -1,   180,    45,   246,    -1,
      47,   321,    46,   292,    -1,    48,    -1,    48,   256,    -1,
      48,   256,    33,    -1,   257,    -1,   256,    33,   257,    -1,
     245,   258,    -1,   180,   149,   246,    12,   292,    -1,   322,
     149,   246,    12,   292,    -1,   180,    12,   292,    -1,   322,
      12,   292,    -1,     7,    -1,     7,   260,    -1,     7,   260,
      33,    -1,   261,    -1,   260,    33,   261,    -1,   262,    -1,
     263,    -1,   245,   180,   267,   270,   271,   274,    -1,   245,
     264,    -1,   180,   149,   249,   180,   265,    46,   266,   271,
     273,   275,    -1,    19,    -1,    17,   323,    18,    -1,   265,
      17,   323,    18,    -1,   292,    -1,    53,    -1,    54,    -1,
      19,    -1,    17,   268,    18,    -1,   323,   149,   246,    -1,
     268,   146,   323,   149,   246,    -1,    -1,   270,    -1,   180,
     149,   246,    -1,   270,   146,   180,   149,   246,    -1,   272,
      -1,    -1,    49,   292,    -1,   274,    -1,    -1,    50,   292,
      -1,    51,   292,    -1,    -1,     8,    -1,     8,   277,    -1,
     278,    -1,   277,   278,    -1,   280,    -1,   279,    -1,   245,
     180,   149,   281,   180,   265,    46,   282,   271,   273,    -1,
     245,   180,   267,   269,   283,   271,   274,    -1,   246,   102,
     246,    -1,    19,   102,   246,    -1,   246,   102,    19,    -1,
      19,   102,    19,    -1,   225,    -1,    53,    -1,    54,    -1,
     284,    -1,    -1,    55,   285,    -1,   286,    -1,   285,   286,
      -1,   287,   291,    -1,   287,   291,   149,   246,    -1,    56,
      -1,    57,    -1,    11,    -1,    11,   289,    -1,    11,   289,
      33,    -1,   290,    -1,   289,    33,   290,    -1,   245,   312,
      -1,    47,   292,    -1,   292,    -1,   291,   146,   292,    -1,
     296,    -1,   293,    -1,   295,    -1,   294,    -1,   297,    -1,
     298,    -1,    17,   292,    18,    -1,   181,    -1,   180,    -1,
     180,    98,    -1,   292,    90,   180,    -1,   292,   104,   180,
      -1,   292,    91,    92,    -1,   292,    19,    -1,   292,    17,
     291,    18,    -1,   292,    17,   292,    86,   292,    18,    -1,
     292,    90,   325,    -1,   292,   153,   180,    -1,   292,   153,
     325,    -1,   292,   152,   321,    -1,   292,   152,   327,    -1,
      58,   299,    59,   292,    -1,   301,    -1,   303,    -1,   306,
      -1,   307,    -1,   134,   329,    13,   292,    -1,   135,   329,
      13,   292,    -1,   136,   326,    13,   292,    -1,    52,   326,
      13,   292,    -1,    29,    30,    -1,    29,   291,    30,    -1,
      29,   292,    31,   329,    30,    -1,    29,   292,    31,   329,
      13,   292,    30,    -1,    29,   292,    86,   292,    30,    -1,
      15,    -1,    20,   291,    21,    -1,    20,   292,    31,   327,
      21,    -1,    20,   292,    31,   327,    13,   292,    21,    -1,
     170,    -1,    29,   308,    30,    -1,    29,   309,    31,   329,
      30,    -1,    29,   309,    31,   329,    13,   292,    30,    -1,
      88,    17,   291,    18,    -1,    89,    17,   291,    18,    -1,
      93,   333,    13,   292,    -1,   168,    17,   292,    18,    -1,
      95,   248,    17,   292,    18,    -1,    95,    17,   292,   146,
     246,    18,    -1,    96,    17,   291,    18,    -1,    97,    17,
     292,   146,   292,    18,    -1,   150,   291,   151,    -1,   150,
     292,    31,   329,   151,    -1,   150,   292,    31,   329,    13,
     292,   151,    -1,   183,    -1,   182,    -1,   184,    -1,   185,
      -1,    92,    -1,   187,    -1,   160,    -1,   137,    -1,   186,
      -1,   300,    -1,   299,   146,   300,    -1,   258,    -1,   264,
      -1,    60,   292,    61,   292,   302,    -1,    63,   292,    -1,
      62,   292,    61,   292,   302,    -1,    64,   292,   149,   304,
     173,    -1,    64,   292,   149,   304,   146,    65,    27,   292,
     173,    -1,   305,    -1,   304,   146,   305,    -1,   323,    27,
     292,    -1,    66,   292,    -1,    67,   292,    -1,    68,   292,
      -1,    69,   292,    -1,    70,   292,    -1,    71,   292,    -1,
      72,   292,    -1,    73,   292,    -1,    74,   292,    -1,    75,
     292,    -1,    76,   292,    -1,    77,   292,    -1,    78,   292,
      -1,    79,   292,    -1,    80,   292,    -1,    81,   292,    -1,
      82,   292,    -1,    83,   292,    -1,    84,   292,    -1,    85,
     292,    -1,   292,    66,   292,    -1,   292,    37,   292,    -1,
     292,    67,   292,    -1,   292,   106,   292,    -1,   292,   105,
     292,    -1,   292,   107,   292,    -1,   292,   108,   292,    -1,
     292,   109,   292,    -1,   292,   110,   292,    -1,   292,   111,
     292,    -1,   292,   112,   292,    -1,   292,    12,   292,    -1,
     292,   113,   292,    -1,   292,   114,   292,    -1,   292,   115,
     292,    -1,   292,   116,   292,    -1,   292,   117,   292,    -1,
     292,   118,   292,    -1,   292,   119,   292,    -1,   292,   120,
     292,    -1,   292,   121,   292,    -1,   292,   122,   292,    -1,
     292,   123,   292,    -1,   292,   124,   292,    -1,   292,   125,
     292,    -1,   292,   126,   292,    -1,   292,   127,   292,    -1,
     292,   128,   292,    -1,   292,   129,   292,    -1,   292,   130,
     292,    -1,   292,   131,   292,    -1,   292,   132,   292,    -1,
     292,   133,   292,    -1,   309,    -1,   308,   146,   309,    -1,
     292,    87,   292,    -1,   292,    27,   225,    -1,   310,    31,
     292,    27,   225,    -1,   312,    -1,   311,   146,   312,    -1,
     180,   149,   246,    -1,   180,   149,   246,   100,   292,    -1,
     180,   149,   246,    59,   292,    -1,   314,    -1,   313,    33,
     314,    -1,   247,   100,   292,    -1,    60,   292,    61,   225,
     316,    63,   225,    -1,    60,   292,    61,   225,    63,   225,
      -1,    62,   292,    61,   225,    -1,   316,    62,   292,    61,
     225,    -1,    64,   292,   149,   318,   173,    -1,    64,   292,
     149,   318,   146,    65,    27,   225,   173,    -1,   319,    -1,
     318,   146,   319,    -1,   323,    27,   225,    -1,   283,   271,
     274,    -1,   324,    -1,   322,    -1,   325,    -1,    88,    17,
     323,   146,   321,    18,    -1,    89,    19,    -1,    89,    17,
     323,    18,    -1,   321,    -1,   323,   146,   321,    -1,   180,
      -1,    67,    -1,   296,    -1,   293,    -1,   295,    -1,   294,
      -1,   297,    -1,   298,    -1,    17,   292,    18,    -1,   327,
      -1,   328,    -1,   321,   118,   292,    -1,   321,   149,   246,
      -1,   330,    -1,   329,   146,   330,    -1,   331,    -1,   332,
      -1,   323,   118,   292,    -1,   323,   149,   246,    -1,   328,
      -1,   333,   146,   328,    -1
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
     513,   516,   521,   523,   530,   537,   545,   554,   565,   570,
     572,   576,   578,   582,   585,   588,   591,   595,   603,   613,
     617,   623,   625,   628,   632,   634,   638,   640,   642,   644,
     648,   652,   656,   658,   661,   663,   666,   670,   672,   674,
     676,   678,   681,   683,   685,   687,   689,   691,   694,   696,
     699,   703,   705,   709,   715,   720,   725,   731,   733,   735,
     737,   739,   740,   744,   746,   748,   754,   758,   762,   766,
     769,   772,   775,   780,   785,   787,   789,   791,   795,   799,
     801,   803,   805,   807,   809,   811,   813,   815,   817,   819,
     823,   827,   831,   835,   837,   840,   842,   846,   850,   855,
     857,   860,   864,   866,   870,   873,   879,   885,   889,   893,
     895,   898,   902,   904,   908,   910,   912,   919,   922,   933,
     935,   939,   944,   946,   948,   950,   952,   956,   960,   966,
     967,   969,   973,   979,   981,   982,   985,   987,   988,   991,
     994,   995,   997,  1000,  1002,  1005,  1007,  1009,  1020,  1028,
    1032,  1036,  1040,  1044,  1046,  1048,  1050,  1052,  1053,  1056,
    1058,  1061,  1064,  1069,  1071,  1073,  1075,  1078,  1082,  1084,
    1088,  1091,  1094,  1096,  1100,  1102,  1104,  1106,  1108,  1110,
    1112,  1116,  1118,  1120,  1123,  1127,  1131,  1135,  1138,  1143,
    1150,  1154,  1158,  1162,  1166,  1170,  1175,  1177,  1179,  1181,
    1183,  1188,  1193,  1198,  1203,  1206,  1210,  1216,  1224,  1230,
    1232,  1236,  1242,  1250,  1252,  1256,  1262,  1270,  1275,  1280,
    1285,  1290,  1296,  1303,  1308,  1315,  1319,  1325,  1333,  1335,
    1337,  1339,  1341,  1343,  1345,  1347,  1349,  1351,  1353,  1357,
    1359,  1361,  1367,  1370,  1376,  1382,  1392,  1394,  1398,  1402,
    1405,  1408,  1411,  1414,  1417,  1420,  1423,  1426,  1429,  1432,
    1435,  1438,  1441,  1444,  1447,  1450,  1453,  1456,  1459,  1462,
    1466,  1470,  1474,  1478,  1482,  1486,  1490,  1494,  1498,  1502,
    1506,  1510,  1514,  1518,  1522,  1526,  1530,  1534,  1538,  1542,
    1546,  1550,  1554,  1558,  1562,  1566,  1570,  1574,  1578,  1582,
    1586,  1590,  1594,  1596,  1600,  1604,  1608,  1614,  1616,  1620,
    1624,  1630,  1636,  1638,  1642,  1646,  1654,  1661,  1666,  1672,
    1678,  1688,  1690,  1694,  1698,  1702,  1704,  1706,  1708,  1715,
    1718,  1723,  1725,  1729,  1731,  1733,  1735,  1737,  1739,  1741,
    1743,  1745,  1749,  1751,  1753,  1757,  1761,  1763,  1767,  1769,
    1771,  1775,  1779,  1781
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   549,   549,   557,   563,   574,   575,   576,   577,   578,
     582,   593,   626,   654,   688,   695,   703,   709,   715,   724,
     733,   739,   745,   751,   766,   773,   788,   806,   813,   820,
     830,   844,   853,   863,   873,   883,   891,   899,   910,   923,
     933,   941,   952,   958,   964,   970,   987,   991,   998,  1004,
    1020,  1024,  1031,  1035,  1042,  1059,  1085,  1089,  1096,  1100,
    1107,  1108,  1109,  1110,  1111,  1112,  1124,  1136,  1137,  1138,
    1139,  1140,  1149,  1150,  1159,  1169,  1170,  1171,  1172,  1179,
    1183,  1184,  1188,  1189,  1193,  1197,  1201,  1205,  1212,  1213,
    1214,  1215,  1216,  1217,  1221,  1225,  1229,  1233,  1237,  1241,
    1242,  1243,  1244,  1245,  1258,  1259,  1261,  1262,  1263,  1268,
    1269,  1280,  1281,  1288,  1290,  1292,  1293,  1294,  1295,  1314,
    1315,  1319,  1320,  1324,  1325,  1326,  1331,  1333,  1334,  1347,
    1348,  1358,  1359,  1360,  1364,  1365,  1377,  1378,  1385,  1386,
    1390,  1391,  1395,  1396,  1400,  1401,  1408,  1412,  1413,  1414,
    1418,  1419,  1423,  1424,  1425,  1426,  1427,  1434,  1438,  1439,
    1440,  1444,  1445,  1449,  1450,  1451,  1452,  1462,  1463,  1464,
    1472,  1473,  1477,  1478,  1479,  1480,  1481,  1482,  1483,  1484,
    1485,  1486,  1487,  1488,  1489,  1498,  1502,  1503,  1504,  1508,
    1509,  1510,  1511,  1512,  1513,  1514,  1515,  1519,  1520,  1524,
    1525,  1529,  1530,  1534,  1535,  1539,  1540,  1541,  1545,  1556,
    1557,  1558,  1562,  1563,  1567,  1571,  1572,  1573,  1574,  1578,
    1579,  1580,  1584,  1585,  1589,  1590,  1594,  1598,  1602,  1607,
    1608,  1609,  1613,  1614,  1615,  1619,  1620,  1624,  1625,  1628,
    1630,  1634,  1635,  1639,  1640,  1644,  1648,  1649,  1653,  1664,
    1665,  1669,  1670,  1677,  1678,  1682,  1683,  1687,  1691,  1695,
    1696,  1697,  1698,  1702,  1703,  1704,  1708,  1709,  1713,  1717,
    1718,  1722,  1723,  1727,  1728,  1750,  1751,  1752,  1756,  1757,
    1761,  1762,  1766,  1767,  1772,  1773,  1774,  1775,  1776,  1777,
    1779,  1780,  1781,  1782,  1783,  1784,  1785,  1786,  1787,  1788,
    1789,  1791,  1792,  1793,  1794,  1796,  1797,  1798,  1799,  1800,
    1802,  1803,  1804,  1805,  1807,  1808,  1810,  1811,  1813,  1815,
    1826,  1828,  1829,  1831,  1832,  1834,  1835,  1837,  1839,  1841,
    1850,  1851,  1852,  1857,  1865,  1867,  1868,  1869,  1875,  1876,
    1880,  1884,  1888,  1889,  1890,  1894,  1898,  1904,  1905,  1909,
    1910,  1914,  1918,  1919,  1923,  1924,  1928,  1929,  1933,  1937,
    1938,  1939,  1940,  1941,  1942,  1943,  1944,  1945,  1946,  1947,
    1948,  1949,  1950,  1951,  1952,  1953,  1954,  1955,  1956,  1960,
    1961,  1962,  1963,  1964,  1965,  1966,  1967,  1968,  1969,  1970,
    1971,  1972,  1973,  1974,  1975,  1976,  1977,  1978,  1979,  1980,
    1981,  1982,  1983,  1984,  1985,  1986,  1987,  1988,  1989,  1990,
    1991,  1992,  1996,  1997,  2001,  2005,  2006,  2010,  2011,  2015,
    2016,  2022,  2029,  2030,  2043,  2047,  2048,  2052,  2053,  2057,
    2058,  2062,  2063,  2067,  2071,  2075,  2076,  2080,  2082,  2091,
    2092,  2096,  2097,  2101,  2103,  2108,  2109,  2110,  2111,  2112,
    2113,  2114,  2118,  2119,  2123,  2127,  2131,  2132,  2136,  2137,
    2141,  2145,  2149,  2150
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

  private static final int yylast_ = 11827;
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
/* Line 3413 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


