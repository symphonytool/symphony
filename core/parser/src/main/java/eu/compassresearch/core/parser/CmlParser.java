
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
    



/* Line 374 of cmlskeleton.java.m4  */
/* Line 1633 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -652;
  public static final short yypact_[] =
  {
        99,   684,  -133,   -86,   684,   327,   -25,    11,   200,    99,
    -652,  -652,  -652,  -652,  -652,  -652,  -652,  -652,  -652,  -652,
    -652,  -652,  -652,   342,  -652,    32,   344,   351,  -652,  -652,
     399,   -86,  -652,   246,   411,  -652,  -652,  -652,  1549,   430,
    -652,   455,   460,  -652,  -652,   684,   107,   296,   513,  -133,
    2708,  -652,   275,  -652,   684,  2708,   477,   340,  -652,  -652,
    -652,     7,  -652,  -652,  -652,  -652,  -652,  -652,  -652,  -652,
    -652,  -652,  -652,  -652,  -652,    22,  -652,   327,   329,   163,
    -652,   513,  1002,   344,  -652,   513,    78,   513,   319,   513,
     513,   513,   513,   513,  -652,  -652,  -652,  -652,  -652,  -652,
    -652,  -652,  -652,   687,    27,  -652,  -652,  -652,  -652,  -652,
    -652,  -652,  2708,  2708,  2164,   588,  2026,  2708,  2708,  2708,
    2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,
    2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,   491,
     498,   588,   202,   512,   519,   588,   588,   588,  2708,   526,
    -652,   467,  -652, 11268,  -652,  -652,  -652,  -652,  -652,  -652,
    -652,  -652,  -652,  -652,   588,  -652,   513,   395,  -652,  5401,
     588,   588,  -652,  2708,   513,  2708,   513,  -652,   396,   531,
     441,   441,   441,   296,   441,   441,  2708,  1454,   196,  3341,
     285,  -652,   536,    -5,   687,   267,  -652,   337,   513,   513,
     986,   574,   685,   922,   590,   590,   590,   513,   513,   513,
     513,   488,   493,  5523,    18,  5646,  -652,    14,  4666,   157,
     656,  -652,  -652,    -4,  -652,  -652,   698,  -652,  -652,    25,
    -652,  -652,   231,  -652,  5768,  5890,   736,   736,   736,   736,
   11719,   736,   736,   736,   736,   736,   736,   736,   736,   736,
     736,   736,   736,   736,   736,  1187,  2708,  2708,   548,  -652,
      38,  2708,   683,  2708,  2708,  -652,     0,    52,  -652,  -652,
    -652,    80,   708,   179,  6013,  2708,  -652,  2708,  2708,  -652,
    2708,  2708,  2708,   718,   635,   550,  2708,  2708,  2708,  2708,
    2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,
    2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,  2708,
    2708,  2708,  2708,  2708,  2708,   588,   947,    17,   371,   687,
     553,   583,   -11,  -652,   591,    23, 11268,   533, 11268,   682,
     566,   684,   654,   101,   576,   531,  -652,  -652,  -652,  -652,
    -652,   183,   291,  -652,  -652,   374,   387,   400,   401,   452,
    6135,   567,   570,  2849,  1705,  -652,  -652,  2708,  -652,   389,
     389,   389,  2708,   389,   389,  2708,  2708,  2708,   389,   389,
    2708,  2708,  2708,  -652,   389,   296,   588,  -652,   513,   513,
    -652,  -652,  -652,   687,   687,  -652,  1002,   513,   513,   687,
     590,   590,   687,  -652,  -652,  -652,  -652,  2708,   588,  -652,
     588,  2708,  2708,  -652,  2708,   588,  2708,   513,  2708,   513,
    2708,  2026,  2708,   588,    74, 11268,    89,  2708,   588,  6257,
    2708,   136,  6379,  2708,   588,   513,  2708,   588,  2708,  2708,
    -652,   588,  6501, 11769,   143,  6623,   782, 11842, 11842,  -652,
    -652,  -652,  -652,   782,   782,   782,   782, 11769, 11769, 11769,
   11769, 11769, 11592, 11642, 11512, 11390, 11769, 11769, 11769, 11769,
    3086,  3086,  1187,  3510,  1855,  1855,  1187,  1187,   736,   736,
     317,    90,  -652,  -652,  -652,  -652,  -652,   588,   513,   352,
     513,  2708,   572,   716,  -652,   588,  -652,  2708,  2708,   531,
     587,   684,  -652,  -652,  -652,  -652,  2708,   589,   743,  -652,
    -652,  -652,  2708,   441,   389,   441,   389,   389,   389,   609,
     389,   441,   765,   -12,   767,   602,   441,   441,   441,  2797,
    2300,  -652,  -652,  -652,  -652,  2708,   441,   441,  2026,  2708,
    2708,   769,  2436,  2708,  2708,  2708,  2708,  1262,  2708,  2108,
      26,  -652,  -652,  -652,  2849,  -652,   191,   423,  1425,   766,
    3371,  4299,   459,   357,  6745,   378,  6867,  4421,   458,   376,
   11268, 11268, 11268,  3341,  -652,   737,   687,   687,    13,   590,
     590, 11268,   677,   414,    16,  6991, 11268,  7113,  -652,    83,
   11769,   687, 11390, 11268,  -652,  4788,   -35,  -652,    70,  -652,
    -652, 11390,  -652,   513,  7235,  -652,  2708, 11268,  -652,   687,
   11390,  -652, 11390, 11390,    46,  -652,  -652,  2708,   454,   687,
     588,  -652,    91,   687, 11268,   641,  2708,  -652,   784, 11268,
   11268,   627,   280,  -652, 11268,   657,  -652,   101, 11268,  -652,
    -652,  -652,  -652,  -652,  -652,  3210,  2708,  -652,  -652,   549,
    2708,   628,   800,  3008,   804,   555,   558,   561,   589,   296,
     296,   296,   344,  3119,   571,  -652,   598,   345,   771,  -652,
    7357,   803, 11268,   615,   619,   252,  4910,    -8,  7479,   645,
    -652, 11268,  7601,  7723,    87,  7845,    -3,   588,   809,   216,
     788,  3566,  2849,  2849,  2849,  2708,  2849,  2849,  2708,  2708,
    2708,  2849,  2849,  2708,  2708,  2708,  -652,  -652,  2708,  -652,
    2572,  2528,  -652,  2708,   389,   389,  -652,  2708,  2708,   389,
    2708,  -652,  2708,  -652,  2708,  -652,  -652,  2708,  -652,  2708,
    2708,  -652,  1460,  -652,  2708,   778,  -652,  7967,  2708,  -652,
    8089,   513,   282,   588,  2015,   513, 11268,  -652,  -652,  1504,
     395,   513,  -652,   811,  8211,   389, 11268,   829,  2708,  -652,
    3267,   621,  3008,  2849,  2849,   831,   126,  -652,  -652,  -652,
    -652,  -652,  2849,   552,  2849,  -652,  -652,   345,  -652,  2708,
     716,   190,  -652,   832,  2849,  2849,  2849,  2849,  2708,  -652,
     588,   582,   328,  -652,   441,   441,   837,  -652,   738,  2708,
    2708,  2708,  2849,  1058,   438,  3362,  4177,  1243,   967,  5033,
    8334,  4543,   490,   820, 11268, 11268, 11268,   305,  2708, 11268,
    -652,  8456,   376,   459,  8578,  8701,   458, 11268,  8823,  8947,
    9071,  9193, 11390,   850,  -652, 11268,  -652,  -652,  9315,  -652,
     687,  -652,   308,  -652,  -652,   771, 11268,   687,   339,   644,
     703,   830,   744,  1061,  2708,   979,  -652,  2708, 11268,  2849,
    -652,  -652,  -652,  2708,  2849,   589,  3149,  -652,  3169,  -652,
     510,  -652,  2849,  2849,  2708,  -652,  3267,  3267,  3098,  9437,
     -22,  -652,    88,  2708,  -652,   645,   633,   652,  2849,  2708,
    9559,  3688,  3810,  3267,  2708,  2708,  2849,  2708,  2849,  2708,
    2849,  -652, 11268,   389,  2708,   588,  -652,  -652,  -652,  2708,
    2708,  -652,  -652,   716,  1748,  1860,   352,   771,  2708,  2708,
    9681,    72,  -652, 11268,  3267,  9803,  3189,  -652,   867,   874,
     513,  -652,   967,  5156,  2708,  2849,   496,  2849,  1618,  -652,
    2849,  -652,   872,  2849,  3267,   323,  3932,  2708,  2849,  2849,
    9925, 10047,   820,  5279,  1243, 10169,   490,   459, 11268,   139,
    4788,  2969,   842,  -652,    78,   687,    78,   687,   104,   716,
   11268, 11268,  -652,  2849,  -652,  2708,  2708,   687,  2849,  2708,
   10291,  3267,  2708,  2849,  3267,   868,  -652,  3267,  2708,  -652,
    -652,  2849,  2849,  3444,  3267,  3267,  2708,  2849,  2708,  2849,
    2849,  2708,  -652,  -652,  -652,  2708,  -652,  2388,  -652,  -652,
     332,   335,  -652, 10413,  2849, 10535,  3267,  2849, 10657,  3267,
    3267,  2849,  2708, 10780,  1243, 10902,  1243,  -652, 11024, 11268,
    -652,  -652,  3267,   771,  -652,  -652,  2849,  3267,  2849,  2896,
    2849,  3267,  4054,  2708,  2849,  -652,   716,  -652,  3267,  -652,
    -652,  2849, 11146,  1243,  -652,  3267,  2849,  1243
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
       0,   105,   102,   113,     0,    49,     0,    31,    18,    17,
      22,     0,    21,    16,     0,     0,     0,     0,    23,    25,
      29,    28,    27,    13,   140,     0,   208,   207,     0,   184,
     183,   284,     0,     0,     0,     0,   415,     0,   414,     0,
     455,   456,   314,   306,   349,     0,     0,   357,     0,   328,
     329,   330,   464,     0,     0,   334,     0,   461,   443,   462,
     311,   458,   312,   313,     0,   331,   299,     0,     0,   238,
       0,   230,     0,   242,   246,     0,     0,   227,   443,   216,
     217,     0,     0,   255,   282,     0,   281,   278,    46,    47,
      37,    45,    44,    36,    41,     0,     0,    40,    35,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   187,     0,     0,   122,     0,     0,   245,   267,
       0,     0,    65,     0,     0,     0,     0,     0,     0,     0,
     110,   111,     0,     0,     0,     0,     0,     0,   444,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    14,    79,     0,   107,
       0,     0,    33,     0,     0,     0,   127,     0,     0,     0,
       0,   176,     0,   322,     0,   317,   319,     0,   326,     0,
       0,   352,     0,   355,     0,     0,   332,     0,     0,   337,
       0,     0,     0,     0,     0,     0,   249,   439,    11,     0,
     240,     0,   280,     0,     0,     0,    56,     0,     0,    54,
      59,     0,     0,     0,     0,     0,     0,   418,   126,   125,
     124,    66,     0,     0,     0,   274,   275,   269,   270,     0,
       0,     0,   109,     0,     0,     0,     0,     0,     0,   103,
       0,     0,     0,   423,     0,     0,     0,   104,     0,     0,
       0,     0,     0,    71,    70,    82,     0,    84,    69,     0,
       0,     0,    72,    74,    78,    77,    76,     0,     0,   425,
      15,     0,    26,    19,     0,   130,    24,   209,     0,     0,
       0,     0,   353,     0,   358,   359,   333,   335,     0,   300,
     239,   231,     0,   234,   235,   245,   233,   243,     0,     0,
       0,   268,   241,   420,     0,     0,    38,     0,    57,     0,
      55,    94,    93,     0,     0,     0,     0,   123,     0,   271,
     272,   435,     0,     0,     0,    92,   100,   416,     0,     0,
       0,   432,     0,     0,   106,     0,     0,     0,     0,     0,
       0,     0,     0,   119,     0,     0,     0,     0,     0,     0,
       0,   108,   112,     0,     0,     0,   323,   318,   327,     0,
       0,   338,   232,   248,     0,     0,     0,   245,     0,     0,
       0,    31,    39,    58,    60,     0,     0,   419,    89,     0,
       0,    67,    68,     0,     0,     0,     0,     0,     0,   430,
       0,   424,     0,     0,   120,     0,     0,     0,     0,     0,
       0,     0,    75,     0,    88,     0,    73,    20,   131,     0,
       0,     0,   251,   247,   263,   261,   262,   260,     0,     0,
     422,   421,    30,     0,   101,     0,     0,   273,     0,     0,
       0,   427,     0,     0,   417,     0,   433,   434,     0,    95,
      80,     0,     0,     0,   115,   114,     0,     0,     0,     0,
       0,     0,   128,   354,   356,     0,   229,     0,   259,    96,
       0,     0,    99,     0,     0,     0,   426,     0,     0,   121,
     116,     0,     0,     0,    86,     0,    83,    81,     0,   250,
     265,   266,   264,   245,    90,    91,     0,   428,     0,     0,
       0,   117,     0,     0,     0,   129,   248,    98,   429,   431,
      97,     0,     0,    87,   258,   118,     0,    85
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -652,  -652,  -652,   887,  -652,  -652,  1148,  -178,   212,  -652,
     543,  -652,  -652,   147,   985,  -652,  -652,   153,   398,  -652,
    -652,  -652,   869,   -71,    93,  -652,  -652,   893,  -652,  -304,
    -165,   120,  -652,   881,    56,  1407,  1094,   787,  -141,  -652,
    -652,   544,  -182,   747,   134,  -652,   854,   894,   363,  -652,
     879,  -652,  -652,   906,    48,  -652,   330,  -652,  -652,   218,
    -641,  -652,   -68,  -482,  -652,  -652,  -652,  -316,  -652,  -652,
    -652,  -652,   124,  -652,  -652,   203,  -652,  -652,  -652,   349,
     -90,   -50,    12,   380,   828,   856,   948,   672,   449,   569,
    -652,    28,  -652,  -652,   260,  -652,  -652,  -652,   580,   453,
    -652,  -633,  -652,  -651,  -652,  -652,  -652,  -652,    57,  -652,
    -105,   -26,  -144,  -652,  -217,  -138,  -266,  -127,  -135,   573,
    -652,  -652,  -652
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   189,   639,   343,   354,
     355,   515,   513,   749,   750,   935,   654,   655,   697,   555,
      13,    27,    28,   751,   344,    14,    31,    32,    15,   334,
     335,   336,    23,    24,   490,   194,   540,   105,   106,   107,
     108,   195,   196,   377,   337,    39,    40,   230,   338,    34,
      35,    36,    37,   231,   612,   835,   167,   317,   841,   322,
     483,   484,   952,   953,   996,   339,   491,   492,   493,   494,
     840,  1023,   658,   659,   767,   768,   769,   340,   498,   499,
     214,   415,   154,   155,   156,   157,   158,   159,   232,   233,
     160,   721,   161,   586,   587,   162,   163,   219,   220,   667,
     756,   626,   782,   541,   542,   926,   543,   870,   871,   661,
     265,   224,   266,   225,    76,   226,   227,   228,   267,   268,
     269,   270,   260
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "153,617,342,345,346,169,348,349,190,272,223,271,75,380,259,757,495,770,783,173,318,641,356,778,217,320,324,325,778,714,85,501,86,87,175,476,258,173,481,396,378,486,223,698,399,699,715,26,88,473,69,417,89,90,91,92,93,25,273,728,33,38,213,215,218,426,440,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,357,75,358,589,428,30,29,717,724,274,475,786,25,1,2,3,198,4,590,733,279,33,722,347,718,406,930,211,211,423,81,16,733,199,326,928,328,700,69,69,16,212,212,573,38,17,482,350,734,723,854,84,82,29,17,379,407,424,5,496,425,997,929,991,69,595,41,174,69,69,69,397,606,427,477,397,779,414,416,642,424,787,176,191,421,409,623,69,180,181,182,183,284,69,69,418,621,711,403,434,356,502,42,427,193,903,184,185,729,427,66,43,95,96,97,98,99,100,101,102,702,472,419,46,357,422,358,424,862,503,261,397,48,917,314,931,432,427,433,435,427,436,437,438,397,424,397,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,574,959,546,320,588,579,565,855,6,7,790,191,19,20,21,22,-172,397,992,85,427,86,87,861,397,410,592,164,572,165,69,604,164,374,165,831,502,88,404,504,186,89,90,91,92,93,775,551,258,376,554,556,557,375,598,560,561,562,891,505,397,902,69,69,503,430,863,48,608,406,187,279,397,645,646,647,980,47,188,-172,48,874,571,663,664,1024,575,576,1025,577,382,580,171,582,172,583,875,585,18,207,407,198,591,208,610,594,611,18,597,209,45,600,411,602,603,618,210,1036,199,49,75,380,506,69,497,95,96,97,98,99,100,101,102,411,680,507,765,766,180,181,182,183,505,284,364,69,50,69,508,509,-172,-172,69,70,-172,505,184,185,69,166,69,52,712,424,739,69,614,679,375,505,713,69,619,620,69,357,904,358,69,54,674,624,193,656,313,314,397,628,66,424,95,96,97,98,99,100,101,102,77,-172,510,732,78,564,981,660,687,79,359,360,662,83,998,397,666,668,397,671,672,673,505,675,-172,681,69,47,364,364,48,170,70,70,69,178,201,366,179,75,19,20,21,22,-172,256,-172,-172,-172,-172,-172,-172,257,370,424,371,366,478,70,706,687,707,70,70,70,372,263,85,186,86,87,1,370,264,371,4,331,69,332,333,275,70,487,727,372,88,69,70,70,89,90,91,92,93,730,972,973,207,187,745,207,208,276,736,208,753,547,209,754,788,209,755,321,330,210,588,5,210,366,505,376,762,689,744,373,505,832,746,505,690,691,505,692,84,693,632,694,424,366,366,731,763,55,876,877,807,695,386,764,191,367,368,369,369,370,370,371,371,341,69,180,181,182,183,209,773,372,372,375,774,366,849,796,872,689,799,800,801,184,185,804,805,806,932,692,505,693,809,694,505,811,375,221,397,814,815,920,817,695,818,70,819,933,505,820,393,821,822,207,211,394,825,208,56,57,828,860,58,209,873,497,836,505,212,405,210,69,649,650,651,94,488,70,70,407,848,66,420,95,96,97,98,99,100,101,102,207,74,408,207,208,207,629,208,631,208,209,109,429,209,387,209,59,210,441,869,210,442,210,480,479,69,55,191,485,489,880,881,882,758,759,760,69,905,512,60,500,514,636,615,109,109,279,70,109,892,109,949,109,109,109,109,109,616,622,222,625,62,63,64,65,66,67,627,640,70,643,70,187,644,710,872,70,669,74,74,547,735,70,69,70,910,406,826,913,70,364,738,279,737,915,70,207,741,70,747,208,58,70,748,74,923,209,752,74,74,74,481,789,210,809,772,94,283,284,844,936,19,20,21,22,940,941,74,943,109,945,285,847,74,74,948,191,109,791,109,950,951,853,864,687,878,59,879,70,960,961,19,20,21,22,-172,70,71,109,313,314,109,109,283,284,970,1000,1001,900,60,109,109,109,109,906,965,657,285,983,315,316,482,966,978,995,72,1007,44,545,439,850,62,63,64,65,66,67,305,69,70,366,309,310,311,312,313,314,857,70,110,1003,367,368,1005,369,51,370,80,371,1008,262,568,177,68,168,315,316,1013,372,1015,53,69,1018,381,71,71,1019,373,373,373,207,373,373,740,208,958,74,373,373,842,209,388,373,1032,366,55,907,210,689,1044,71,859,72,72,71,71,71,742,665,993,693,584,694,824,1042,578,976,73,74,74,676,70,695,71,180,181,182,183,72,71,71,601,72,72,72,0,0,0,385,0,0,0,184,185,207,0,0,0,208,0,85,72,86,87,209,0,0,72,72,373,0,210,373,373,373,0,373,373,88,0,58,0,89,90,91,92,93,0,0,74,0,109,109,0,0,0,0,0,70,109,109,109,0,0,73,73,0,0,0,0,0,74,0,74,683,0,0,0,74,0,109,0,109,0,74,59,74,0,0,207,73,74,687,208,73,73,73,74,109,209,74,0,0,70,74,0,210,0,60,0,0,366,71,73,70,689,0,0,0,73,73,908,186,0,0,0,0,693,474,694,62,63,64,65,66,67,0,0,0,695,72,0,0,104,71,71,0,0,0,0,74,109,187,109,0,0,0,0,74,0,911,70,909,0,0,0,0,0,0,0,0,0,72,72,0,0,104,104,0,0,104,0,104,193,104,104,104,104,104,66,0,95,96,97,98,99,100,101,102,0,0,74,366,0,0,71,689,279,0,0,74,373,373,690,691,373,692,0,693,0,694,0,0,0,0,0,0,71,0,71,695,0,73,72,71,0,0,0,0,0,71,109,71,0,0,373,0,71,0,0,0,0,0,71,0,72,71,72,682,683,71,104,72,0,73,73,109,0,72,104,72,104,0,0,0,72,70,687,283,284,55,72,0,74,72,0,0,0,72,0,104,0,285,104,104,0,0,0,0,0,0,0,104,104,104,104,71,0,0,70,0,373,0,0,71,0,309,310,311,312,313,314,0,0,0,73,0,0,0,0,221,0,0,0,72,0,0,0,0,539,315,316,72,0,0,0,373,73,0,73,74,56,57,0,73,58,0,71,0,0,73,0,73,0,0,0,71,73,0,0,0,0,0,73,0,0,73,0,0,0,73,0,0,0,0,72,0,366,0,0,0,689,0,0,72,74,0,0,690,691,59,692,0,693,109,694,74,0,109,0,0,0,109,0,109,695,0,0,0,0,0,0,0,60,0,0,73,0,677,0,0,0,0,0,73,0,0,0,0,71,0,360,0,678,0,62,63,64,65,66,67,0,0,74,0,0,103,0,1,364,0,351,4,331,352,332,333,72,353,0,0,0,0,104,104,0,0,73,55,0,0,104,104,104,0,0,73,0,0,192,0,0,0,197,0,200,0,202,203,204,205,206,104,5,104,653,71,0,548,549,550,0,552,553,0,0,0,558,559,0,104,0,85,563,838,87,823,0,221,0,701,0,0,0,72,0,0,0,0,0,88,0,0,0,89,90,91,92,93,56,57,71,0,58,0,0,0,0,0,73,0,0,71,0,0,0,0,55,74,366,0,0,0,104,319,104,0,109,109,72,367,368,327,369,329,370,0,371,0,0,72,0,0,109,0,0,0,372,59,0,0,74,0,0,0,0,383,384,0,71,0,0,0,0,0,389,390,391,392,0,0,60,0,0,0,0,73,0,0,0,0,0,0,0,0,0,55,72,56,57,0,222,58,62,63,64,65,66,67,0,0,0,0,630,0,633,634,635,637,638,0,0,0,104,0,0,0,0,793,794,795,73,797,798,0,0,0,802,803,0,0,0,73,0,975,94,221,59,104,0,0,66,0,95,96,97,98,99,100,101,102,0,0,0,0,0,0,56,57,1,60,58,351,4,331,352,332,333,0,544,0,0,0,0,71,0,0,0,0,73,61,0,62,63,64,65,66,67,0,851,852,0,0,0,0,0,0,0,856,0,858,0,72,0,5,0,59,71,0,0,865,866,867,868,781,0,85,0,954,87,0,0,0,0,0,0,0,0,883,60,0,0,0,0,88,72,566,567,89,90,91,92,93,0,0,569,570,0,0,222,0,62,63,64,65,66,67,0,0,0,0,0,0,0,0,581,0,327,0,0,0,0,0,0,0,0,104,0,0,0,104,0,0,599,104,914,104,0,0,0,916,0,0,0,73,0,0,0,921,922,0,0,0,812,813,0,0,0,816,0,0,0,0,0,934,0,0,0,0,0,0,0,942,0,944,279,946,73,85,0,956,87,0,0,0,0,609,0,613,0,0,0,0,280,846,0,88,0,0,0,89,90,91,92,93,0,0,0,0,0,0,971,0,974,0,0,977,0,0,979,0,0,0,0,984,985,0,0,0,94,0,0,0,0,0,66,0,95,96,97,98,99,100,101,102,0,283,284,0,999,0,0,0,0,1002,0,0,0,0,1006,285,286,287,288,289,0,0,1009,1010,0,781,0,0,1014,0,1016,1017,0,0,0,305,306,0,1022,309,310,311,312,313,314,1027,0,0,1029,912,0,0,1031,0,104,104,725,0,0,0,0,0,0,315,316,0,0,1037,0,1038,104,1040,0,0,0,1043,0,0,0,0,0,0,1045,0,0,0,111,1047,112,0,0,113,0,0,0,0,94,947,0,55,114,0,66,0,95,96,97,98,99,100,101,102,0,0,0,0,0,0,0,0,0,0,0,115,833,834,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,56,57,0,0,58,0,0,0,682,683,684,0,0,0,685,0,0,0,0,0,0,0,0,0,830,0,686,687,837,0,0,0,839,0,843,145,146,147,59,0,0,0,0,0,0,0,0,0,0,59,0,148,0,0,0,0,0,0,0,0,0,60,0,0,0,111,0,112,0,149,113,150,60,0,0,0,0,0,0,114,216,151,152,62,63,64,65,66,67,0,0,0,229,0,62,63,64,65,66,67,0,0,115,0,0,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,688,366,139,140,0,689,58,141,0,142,143,144,690,691,0,692,0,693,0,694,0,0,0,0,0,0,0,0,0,695,0,696,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,145,146,147,59,0,0,0,0,0,0,0,0,0,955,957,0,148,111,0,112,0,0,113,0,0,0,60,0,0,967,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,657,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,516,517,518,519,0,0,520,0,521,522,523,524,525,0,0,0,0,0,526,527,0,0,0,0,0,0,0,0,0,0,0,0,145,146,147,59,0,0,0,1020,1021,0,0,0,528,0,529,0,148,111,530,112,0,670,113,0,0,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,531,0,532,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,533,142,143,144,0,534,0,0,0,0,0,0,682,683,684,0,0,0,685,0,535,536,537,0,0,538,0,0,0,0,686,687,0,0,0,0,0,0,94,0,145,146,147,59,0,0,0,0,0,0,0,0,0,0,0,0,148,111,0,112,0,0,113,0,0,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,808,142,143,144,688,366,0,0,0,689,0,0,0,0,0,0,690,691,0,692,0,693,0,694,0,0,0,0,0,0,0,0,0,695,0,810,0,0,0,0,145,146,147,59,0,0,0,0,0,0,0,0,0,0,0,0,148,111,0,112,0,0,113,0,0,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,0,516,517,518,519,0,0,520,0,521,522,523,524,525,0,0,0,0,0,526,527,0,0,0,0,0,0,0,0,0,0,0,145,146,147,59,0,0,0,0,0,0,0,0,0,528,0,529,148,0,0,530,0,516,517,518,519,0,60,520,0,521,522,523,524,525,149,0,150,0,0,526,527,0,0,0,0,0,151,152,62,63,64,65,66,67,648,0,531,0,532,0,0,0,0,0,0,528,0,529,682,683,684,530,0,0,685,0,0,0,0,0,0,0,0,0,0,0,686,687,0,0,0,0,0,649,650,651,0,533,0,0,0,0,534,0,0,0,0,0,531,0,532,0,0,0,0,0,0,535,536,537,0,0,538,0,0,0,0,0,0,0,0,0,0,0,0,652,0,0,0,277,0,0,0,0,278,0,279,0,0,533,0,0,0,0,534,0,0,0,0,0,0,0,0,0,280,0,0,0,0,535,536,537,0,0,538,0,0,0,0,0,516,517,518,519,0,0,520,94,521,522,523,524,525,281,282,0,688,366,526,527,0,689,0,0,0,0,0,0,690,691,0,692,0,693,0,694,0,283,284,0,0,0,0,0,528,695,529,1039,0,0,530,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,279,0,0,0,531,0,532,682,683,684,0,0,0,685,0,0,315,316,280,0,0,0,0,0,0,686,687,0,682,683,684,0,761,0,685,0,0,994,0,0,0,0,0,0,0,533,686,687,0,0,534,0,0,0,0,924,925,0,682,683,684,0,918,0,685,535,536,537,0,0,538,283,284,0,0,0,686,687,682,683,684,0,919,652,685,285,286,287,288,289,0,0,0,0,0,0,686,687,682,683,684,0,964,0,685,305,306,307,308,309,310,311,312,313,314,0,686,687,0,359,360,361,0,743,0,362,0,0,0,0,0,0,0,315,316,688,366,363,364,0,689,0,0,0,0,0,0,690,691,0,692,0,693,0,694,0,688,366,0,0,0,689,0,0,695,0,0,0,690,691,0,692,0,693,0,694,682,683,684,0,0,0,685,0,0,695,688,366,0,0,0,689,0,0,686,687,0,0,690,691,0,692,0,693,0,694,688,366,0,0,0,689,0,0,0,695,0,0,690,691,0,692,0,693,0,694,688,366,0,0,0,689,0,0,0,695,0,0,690,691,0,692,0,693,0,694,0,365,366,0,359,360,361,0,0,695,362,0,0,367,368,0,369,0,370,0,371,0,363,364,0,682,683,0,0,0,372,685,0,0,359,360,0,0,0,0,362,0,0,686,687,0,0,0,0,0,0,0,363,364,0,0,0,0,688,366,0,0,0,689,0,0,0,0,0,0,690,691,0,692,0,693,0,694,0,0,0,0,0,0,0,0,0,695,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,280,0,365,366,0,0,0,0,0,0,0,0,0,0,367,368,0,369,0,370,0,371,0,688,366,0,0,0,689,281,282,372,365,366,0,690,691,0,692,0,693,0,694,0,367,368,0,369,279,370,0,371,695,283,284,0,0,0,0,0,0,372,0,0,0,0,280,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,283,284,0,280,0,0,0,1011,0,0,1012,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,305,0,0,0,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,315,316,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,792,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,938,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,939,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,982,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,884,885,0,0,0,0,280,0,0,1041,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,703,0,0,0,886,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,704,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,709,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,400,0,890,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,401,402,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,719,720,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,776,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,777,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,887,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,968,888,0,0,0,0,0,0,315,316,969,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,988,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,323,279,0,0,0,989,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,395,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,398,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,412,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,413,0,0,315,316,431,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,511,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,593,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,605,279,0,0,0,0,596,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,607,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,705,0,0,0,0,708,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,716,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,402,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,726,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,771,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,780,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,784,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,785,776,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,827,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,829,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,845,0,0,0,0,0,0,315,316,889,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,893,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,894,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,895,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,896,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,897,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,898,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,899,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,927,0,901,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,937,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,962,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,963,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,986,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,987,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,990,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1004,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1026,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1028,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1030,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,1033,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,1034,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1046,1035,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,0,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,0,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,277,0,0,0,0,0,0,279,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,280,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,277,0,0,0,281,282,0,279,0,0,315,316,0,0,0,0,0,0,0,0,0,0,0,0,0,280,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,281,282,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,277,283,284,0,0,0,0,279,0,0,0,0,0,315,316,285,286,287,288,289,290,291,292,293,294,280,296,0,0,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,281,282,0,279,0,0,0,0,0,315,316,0,0,0,0,0,0,0,0,0,0,280,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,0,0,281,282,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,283,284,279,0,0,0,0,0,0,0,0,0,315,316,285,286,287,288,289,0,280,0,0,0,0,0,0,0,0,0,0,0,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,315,316";
  public static final short yytable_ninf_ = -173;
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
  public static final String yyCheckString = "50,483,180,181,182,55,184,185,79,147,115,146,38,195,141,648,332,658,669,12,164,33,187,31,114,166,170,171,31,13,17,335,19,20,12,18,141,12,49,21,45,18,147,17,30,19,30,180,35,315,38,13,39,40,41,42,43,1,148,13,4,5,112,113,114,13,283,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,17,116,19,18,13,180,2,13,27,148,316,13,45,3,4,5,27,7,18,17,19,54,146,183,30,118,27,90,90,118,12,0,17,44,173,146,175,100,115,116,9,104,104,398,77,0,146,186,46,173,13,47,34,49,9,149,149,146,48,47,149,46,173,13,141,18,180,149,145,146,147,146,18,146,146,146,173,256,257,180,146,173,149,79,263,149,491,164,14,15,16,17,91,170,171,146,489,173,30,278,354,118,180,146,180,835,32,33,151,146,186,0,188,189,190,191,192,193,194,195,18,315,261,180,17,264,19,146,27,146,17,146,149,855,133,875,275,146,277,278,146,280,281,282,146,146,146,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,400,907,357,409,413,405,376,146,174,175,59,183,176,177,178,179,180,146,144,17,146,19,20,770,146,59,418,17,398,19,283,431,17,13,19,18,118,35,146,13,142,39,40,41,42,43,59,362,418,47,365,366,367,33,424,370,371,372,18,33,146,18,315,316,146,151,141,149,477,118,172,19,146,516,517,518,18,146,180,17,149,18,397,526,527,18,401,402,18,404,18,406,17,408,19,410,33,412,0,27,149,27,417,31,17,420,19,9,423,37,33,426,146,428,429,485,44,1023,44,33,411,568,13,376,333,188,189,190,191,192,193,194,195,146,537,13,56,57,14,15,16,17,33,91,33,398,12,400,13,13,88,89,405,38,92,33,32,33,411,149,413,180,13,146,149,418,481,537,33,33,21,424,487,488,427,17,102,19,431,33,535,496,180,519,132,133,146,502,186,146,188,189,190,191,192,193,194,195,33,137,13,610,12,375,146,520,33,12,14,15,525,180,959,146,529,530,146,532,533,534,33,536,160,538,477,146,33,33,149,17,115,116,485,169,180,143,172,528,176,177,178,179,180,17,182,183,184,185,186,187,17,159,146,161,143,149,141,144,33,146,145,146,147,171,17,17,142,19,20,3,159,17,161,7,8,528,10,11,17,164,12,596,171,35,537,170,171,39,40,41,42,43,607,62,63,27,172,13,27,31,98,616,31,13,180,37,13,677,37,13,180,180,44,722,48,44,143,33,47,13,147,636,189,33,733,640,33,154,155,33,157,503,159,505,161,146,143,143,149,33,17,784,785,698,171,36,13,519,154,155,157,157,159,159,161,161,180,610,14,15,16,17,37,13,171,171,33,13,143,13,685,780,147,688,689,690,32,33,693,694,695,13,157,33,159,700,161,33,703,33,67,146,707,708,149,710,171,712,283,714,13,33,717,180,719,720,27,90,180,724,31,88,89,728,769,92,37,100,627,734,33,104,31,44,677,138,139,140,180,12,315,316,149,748,186,17,188,189,190,191,192,193,194,195,27,38,13,27,31,27,503,31,505,31,37,48,13,37,38,37,137,44,92,778,44,180,44,149,180,722,17,643,146,172,789,790,791,649,650,651,733,102,180,160,173,180,142,180,81,82,19,376,85,808,87,895,89,90,91,92,93,50,180,180,180,182,183,184,185,186,187,33,12,398,12,400,172,180,46,928,405,17,115,116,180,149,411,780,413,844,118,18,847,418,33,173,19,18,853,424,27,149,427,180,31,92,431,12,141,864,37,12,145,146,147,49,12,44,873,21,180,90,91,17,879,176,177,178,179,884,885,164,887,166,889,104,12,170,171,894,752,174,59,176,899,900,20,20,33,17,137,118,477,908,909,176,177,178,179,180,485,38,195,132,133,198,199,90,91,924,965,966,27,160,207,208,209,210,180,17,55,104,937,152,153,146,17,20,51,38,27,9,354,180,752,182,183,184,185,186,187,124,895,528,143,128,129,130,131,132,133,763,537,49,969,154,155,972,157,31,159,45,161,978,142,386,77,38,54,152,153,986,171,988,33,928,991,195,115,116,995,548,549,550,27,552,553,622,31,906,283,558,559,740,37,38,563,1012,143,17,841,44,147,1036,141,767,115,116,145,146,147,627,528,950,159,411,161,722,1033,404,928,38,315,316,536,610,171,164,14,15,16,17,141,170,171,427,145,146,147,-1,-1,-1,21,-1,-1,-1,32,33,27,-1,-1,-1,31,-1,17,164,19,20,37,-1,-1,170,171,630,-1,44,633,634,635,-1,637,638,35,-1,92,-1,39,40,41,42,43,-1,-1,376,-1,378,379,-1,-1,-1,-1,-1,677,386,387,388,-1,-1,115,116,-1,-1,-1,-1,-1,398,-1,400,15,-1,-1,-1,405,-1,407,-1,409,-1,411,137,413,-1,-1,27,141,418,33,31,145,146,147,424,425,37,427,-1,-1,722,431,-1,44,-1,160,-1,-1,143,283,164,733,147,-1,-1,-1,170,171,59,142,-1,-1,-1,-1,159,180,161,182,183,184,185,186,187,-1,-1,-1,171,283,-1,-1,48,315,316,-1,-1,-1,-1,477,478,172,480,-1,-1,-1,-1,485,-1,180,780,100,-1,-1,-1,-1,-1,-1,-1,-1,-1,315,316,-1,-1,81,82,-1,-1,85,-1,87,180,89,90,91,92,93,186,-1,188,189,190,191,192,193,194,195,-1,-1,528,143,-1,-1,376,147,19,-1,-1,537,812,813,154,155,816,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,398,-1,400,171,-1,283,376,405,-1,-1,-1,-1,-1,411,568,413,-1,-1,846,-1,418,-1,-1,-1,-1,-1,424,-1,398,427,400,14,15,431,166,405,-1,315,316,593,-1,411,174,413,176,-1,-1,-1,418,895,33,90,91,17,424,-1,610,427,-1,-1,-1,431,-1,195,-1,104,198,199,-1,-1,-1,-1,-1,-1,-1,207,208,209,210,477,-1,-1,928,-1,912,-1,-1,485,-1,128,129,130,131,132,133,-1,-1,-1,376,-1,-1,-1,-1,67,-1,-1,-1,477,-1,-1,-1,-1,353,152,153,485,-1,-1,-1,947,398,-1,400,677,88,89,-1,405,92,-1,528,-1,-1,411,-1,413,-1,-1,-1,537,418,-1,-1,-1,-1,-1,424,-1,-1,427,-1,-1,-1,431,-1,-1,-1,-1,528,-1,143,-1,-1,-1,147,-1,-1,537,722,-1,-1,154,155,137,157,-1,159,731,161,733,-1,735,-1,-1,-1,739,-1,741,171,-1,-1,-1,-1,-1,-1,-1,160,-1,-1,477,-1,165,-1,-1,-1,-1,-1,485,-1,-1,-1,-1,610,-1,15,-1,180,-1,182,183,184,185,186,187,-1,-1,780,-1,-1,48,-1,3,33,-1,6,7,8,9,10,11,610,13,-1,-1,-1,-1,378,379,-1,-1,528,17,-1,-1,386,387,388,-1,-1,537,-1,-1,81,-1,-1,-1,85,-1,87,-1,89,90,91,92,93,407,48,409,519,677,-1,359,360,361,-1,363,364,-1,-1,-1,368,369,-1,425,-1,17,374,19,20,65,-1,67,-1,544,-1,-1,-1,677,-1,-1,-1,-1,-1,35,-1,-1,-1,39,40,41,42,43,88,89,722,-1,92,-1,-1,-1,-1,-1,610,-1,-1,733,-1,-1,-1,-1,17,895,143,-1,-1,-1,478,166,480,-1,904,905,722,154,155,174,157,176,159,-1,161,-1,-1,733,-1,-1,920,-1,-1,-1,171,137,-1,-1,928,-1,-1,-1,-1,198,199,-1,780,-1,-1,-1,-1,-1,207,208,209,210,-1,-1,160,-1,-1,-1,-1,677,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,780,88,89,-1,180,92,182,183,184,185,186,187,-1,-1,-1,-1,504,-1,506,507,508,509,510,-1,-1,-1,568,-1,-1,-1,-1,682,683,684,722,686,687,-1,-1,-1,691,692,-1,-1,-1,733,-1,65,180,67,137,593,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,-1,-1,88,89,3,160,92,6,7,8,9,10,11,-1,13,-1,-1,-1,-1,895,-1,-1,-1,-1,780,180,-1,182,183,184,185,186,187,-1,753,754,-1,-1,-1,-1,-1,-1,-1,762,-1,764,-1,895,-1,48,-1,137,928,-1,-1,774,775,776,777,669,-1,17,-1,19,20,-1,-1,-1,-1,-1,-1,-1,-1,792,160,-1,-1,-1,-1,35,928,378,379,39,40,41,42,43,-1,-1,387,388,-1,-1,180,-1,182,183,184,185,186,187,-1,-1,-1,-1,-1,-1,-1,-1,407,-1,409,-1,-1,-1,-1,-1,-1,-1,-1,731,-1,-1,-1,735,-1,-1,425,739,849,741,-1,-1,-1,854,-1,-1,-1,895,-1,-1,-1,862,863,-1,-1,-1,704,705,-1,-1,-1,709,-1,-1,-1,-1,-1,878,-1,-1,-1,-1,-1,-1,-1,886,-1,888,19,890,928,17,-1,19,20,-1,-1,-1,-1,478,-1,480,-1,-1,-1,-1,37,745,-1,35,-1,-1,-1,39,40,41,42,43,-1,-1,-1,-1,-1,-1,925,-1,927,-1,-1,930,-1,-1,933,-1,-1,-1,-1,938,939,-1,-1,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,90,91,-1,963,-1,-1,-1,-1,968,-1,-1,-1,-1,973,104,105,106,107,108,-1,-1,981,982,-1,875,-1,-1,987,-1,989,990,-1,-1,-1,124,125,-1,997,128,129,130,131,132,133,1004,-1,-1,1007,845,-1,-1,1011,-1,904,905,593,-1,-1,-1,-1,-1,-1,152,153,-1,-1,1026,-1,1028,920,1030,-1,-1,-1,1034,-1,-1,-1,-1,-1,-1,1041,-1,-1,-1,15,1046,17,-1,-1,20,-1,-1,-1,-1,180,893,-1,17,29,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,88,89,-1,-1,92,-1,-1,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,731,-1,32,33,735,-1,-1,-1,739,-1,741,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,137,-1,150,-1,-1,-1,-1,-1,-1,-1,-1,-1,160,-1,-1,-1,15,-1,17,-1,168,20,170,160,-1,-1,-1,-1,-1,-1,29,30,180,181,182,183,184,185,186,187,-1,-1,-1,180,-1,182,183,184,185,186,187,-1,-1,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,142,143,88,89,-1,147,92,93,-1,95,96,97,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,904,905,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,920,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,53,54,-1,-1,-1,58,-1,60,-1,150,15,64,17,-1,19,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,101,-1,103,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,142,95,96,97,-1,147,-1,-1,-1,-1,-1,-1,14,15,16,-1,-1,-1,20,-1,162,163,164,-1,-1,167,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,180,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,94,95,96,97,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,58,-1,60,150,-1,-1,64,-1,14,15,16,17,-1,160,20,-1,22,23,24,25,26,168,-1,170,-1,-1,32,33,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,99,-1,101,-1,103,-1,-1,-1,-1,-1,-1,58,-1,60,14,15,16,64,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,138,139,140,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,101,-1,103,-1,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,162,163,164,-1,-1,167,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,180,22,23,24,25,26,66,67,-1,142,143,32,33,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,90,91,-1,-1,-1,-1,-1,58,171,60,173,-1,-1,64,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,19,-1,-1,-1,101,-1,103,14,15,16,-1,-1,-1,20,-1,-1,152,153,37,-1,-1,-1,-1,-1,-1,32,33,-1,14,15,16,-1,18,-1,20,-1,-1,173,-1,-1,-1,-1,-1,-1,-1,142,32,33,-1,-1,147,-1,-1,-1,-1,62,63,-1,14,15,16,-1,18,-1,20,162,163,164,-1,-1,167,90,91,-1,-1,-1,32,33,14,15,16,-1,18,180,20,104,105,106,107,108,-1,-1,-1,-1,-1,-1,32,33,14,15,16,-1,18,-1,20,124,125,126,127,128,129,130,131,132,133,-1,32,33,-1,14,15,16,-1,18,-1,20,-1,-1,-1,-1,-1,-1,-1,152,153,142,143,32,33,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,142,143,-1,-1,-1,147,-1,-1,171,-1,-1,-1,154,155,-1,157,-1,159,-1,161,14,15,16,-1,-1,-1,20,-1,-1,171,142,143,-1,-1,-1,147,-1,-1,32,33,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,-1,-1,147,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,-1,-1,147,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,-1,142,143,-1,14,15,16,-1,-1,171,20,-1,-1,154,155,-1,157,-1,159,-1,161,-1,32,33,-1,14,15,-1,-1,-1,171,20,-1,-1,14,15,-1,-1,-1,-1,20,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,142,143,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,142,143,-1,-1,-1,147,66,67,171,142,143,-1,154,155,-1,157,-1,159,-1,161,-1,154,155,-1,157,19,159,-1,161,171,90,91,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
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
      12,    33,   180,    12,   180,   218,   218,   218,    99,   138,
     139,   140,   180,   225,   227,   228,   234,    55,   283,   284,
     292,   320,   292,   218,   218,   299,   292,   310,   292,    17,
      19,   292,   292,   292,   291,   292,   310,   165,   180,   321,
     326,   292,    14,    15,    16,    20,    32,    33,   142,   147,
     154,   155,   157,   159,   161,   171,   173,   229,    17,    19,
     100,   225,    18,    32,   158,   145,   144,   146,    28,   156,
      46,   173,    13,    21,    13,    30,    30,    13,    30,    62,
      63,   302,   146,   173,    27,   246,    18,   292,    13,   151,
     292,   149,   323,    17,    46,   149,   292,    18,   173,   149,
     267,   149,   290,    18,   292,    13,   292,   180,    12,   224,
     225,   234,    12,    13,    13,    13,   311,   312,   235,   235,
     235,    18,    13,    33,    13,    56,    57,   285,   286,   287,
     271,    21,    21,    13,    13,    59,    27,    61,    31,   173,
     149,   247,   313,   314,   145,   148,    13,   173,   321,    12,
      59,    59,   163,   225,   225,   225,   292,   225,   225,   292,
     292,   292,   225,   225,   292,   292,   292,   291,    94,   292,
     173,   292,   217,   217,   292,   292,   217,   292,   292,   292,
     292,   292,   292,    65,   305,   292,    18,    18,   292,    18,
     246,    18,   323,    53,    54,   266,   292,   246,    19,   246,
     281,   269,   270,   246,    17,   145,   217,    12,   292,    13,
     224,   225,   225,    20,    13,   146,   225,   228,   225,   286,
     291,   274,    27,   141,    20,   225,   225,   225,   225,   292,
     318,   319,   323,   100,    18,    33,   218,   218,    17,   118,
     292,   292,   292,   225,    31,    32,   158,    31,   145,    31,
     156,    18,   292,    21,    28,    31,    21,    30,    30,    61,
      27,   151,    18,   271,   102,   102,   180,   283,    59,   100,
     292,   180,   217,   292,   225,   292,   225,   312,    18,    18,
     149,   225,   225,   292,    62,    63,   316,    27,   146,   173,
      27,   314,    13,    13,   225,   226,   292,    38,   163,   163,
     292,   292,   225,   292,   225,   292,   225,   217,   292,   329,
     292,   292,   273,   274,    19,   246,    19,   246,   265,   271,
     292,   292,    18,    21,    18,    17,    17,   246,    21,    31,
     292,   225,    62,    63,   225,    65,   319,   225,    20,   225,
      18,   146,   163,   292,   225,   225,    32,    21,    31,   145,
     148,    13,   144,   302,   173,    51,   275,    46,   274,   225,
     291,   291,   225,   292,    61,   292,   225,    27,   292,   225,
     225,   163,   166,   292,   225,   292,   225,   225,   292,   292,
      53,    54,   225,   282,    18,    18,    21,   225,    61,   225,
      21,   225,   292,    31,   145,   144,   271,   225,   225,   173,
     225,   163,   292,   225,   273,   225,    21,   225
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
       3,     1,     1,     1,     1,     2,     3,     5,     5,     3,
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
     218,    13,   142,   292,   145,   217,    -1,    32,   218,    13,
     217,    -1,    16,   218,    13,   217,    -1,   235,    -1,   219,
      -1,   218,    33,   235,    -1,   218,    33,   219,    -1,   180,
     118,   292,    -1,   180,   146,   219,    -1,   221,    -1,   220,
     221,    -1,   241,    -1,     9,   222,    -1,     6,    -1,     6,
     223,    -1,   180,    12,   224,    -1,   222,   180,    12,   224,
      -1,   180,    12,   292,    -1,   223,   180,    12,   292,    -1,
     223,    33,   180,    12,   292,    -1,   225,    -1,   234,    13,
     225,    -1,    22,    -1,    23,    -1,    24,    -1,    25,    -1,
      26,   292,    -1,    17,   225,    18,    -1,    20,   292,    21,
      27,   225,    -1,    20,   292,    21,   141,   225,    -1,   225,
      33,   225,    -1,   225,    15,   225,    -1,   225,    14,   225,
      -1,   225,   155,   225,    -1,   225,   154,   292,   156,   225,
      -1,   225,   157,   225,    -1,   225,    20,   292,   158,   225,
      -1,   225,   171,   292,    -1,   225,   161,   292,    -1,   225,
     159,   292,    -1,   225,   229,    -1,   162,   291,    13,    17,
     226,    18,    -1,   225,   147,   292,    31,   292,   148,   225,
      -1,   225,    16,   225,    -1,   225,   142,   292,    31,   292,
     145,   225,    -1,   225,    32,   225,    -1,   225,    20,   292,
      31,   292,    32,   292,    31,   292,    21,   225,    -1,   225,
      20,   292,    32,   292,    21,   225,    -1,   225,   142,   292,
      31,   292,    31,   292,   145,   225,    -1,   225,   142,   292,
     145,   225,    -1,    17,   227,    13,   225,    18,    -1,    17,
     227,    13,   225,    18,    17,   291,    18,    -1,    17,   234,
      13,   225,    18,    17,   291,    18,    -1,    33,   218,    13,
     225,    -1,    15,   218,    13,   225,    -1,    14,   218,    13,
     225,    -1,   147,   292,   148,   218,    13,   225,    -1,    16,
     218,    13,    20,   292,    21,   225,    -1,   142,   292,   145,
     218,    13,    20,   292,    21,   225,    -1,    32,   218,    13,
      20,   292,    31,   292,    21,   225,    -1,    32,   218,    13,
      20,   292,    21,   225,    -1,    58,   299,    59,   225,    -1,
      17,    99,   311,    13,   225,    18,    -1,   315,    -1,    60,
     310,   173,    -1,   163,   310,   173,    -1,   314,    -1,   101,
      17,   313,    18,    -1,   247,    19,    -1,   247,    17,   291,
      18,    -1,    20,   320,    21,    -1,   103,    19,    -1,   103,
     292,    -1,   247,   100,    94,   292,    -1,   317,    -1,   164,
     326,    59,   292,   163,   225,    -1,   164,   321,    59,   292,
     163,   225,    -1,   164,   165,   321,   118,   292,   163,   225,
      -1,   164,   180,    12,   292,    38,   292,   163,   225,    -1,
     164,   180,    12,   292,    38,   292,   166,   292,   163,   225,
      -1,   167,   292,   163,   225,    -1,   225,    -1,   226,   146,
     225,    -1,   228,    -1,   227,    33,   228,    -1,   140,   235,
      -1,   139,   235,    -1,   138,   235,    -1,   143,   230,   144,
      -1,   143,   292,    28,   292,    31,   329,   144,    -1,   143,
     292,    28,   292,    31,   329,    13,   292,   144,    -1,   292,
      28,   292,    -1,   230,   146,   292,    28,   292,    -1,     4,
      -1,     4,   232,    -1,     4,   232,    33,    -1,   233,    -1,
     232,    33,   233,    -1,   180,    -1,   235,    -1,   235,    -1,
     234,    33,   235,    -1,   180,   149,   246,    -1,   180,   146,
     235,    -1,     5,    -1,     5,   237,    -1,   238,    -1,   237,
     238,    -1,   180,    12,   292,    -1,   242,    -1,   255,    -1,
     259,    -1,   241,    -1,   241,   240,    -1,   242,    -1,   255,
      -1,   259,    -1,   276,    -1,   288,    -1,    10,   278,    -1,
       3,    -1,     3,   243,    -1,     3,   243,    33,    -1,   244,
      -1,   243,    33,   244,    -1,   245,   180,    12,   246,   254,
      -1,   245,   180,    12,   246,    -1,   245,   180,    34,   252,
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
      -1,    48,    -1,    48,   256,    -1,    48,   256,    33,    -1,
     257,    -1,   256,    33,   257,    -1,   245,   258,    -1,   180,
     149,   246,    12,   292,    -1,   322,   149,   246,    12,   292,
      -1,   180,    12,   292,    -1,   322,    12,   292,    -1,     7,
      -1,     7,   260,    -1,     7,   260,    33,    -1,   261,    -1,
     260,    33,   261,    -1,   262,    -1,   263,    -1,   245,   180,
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
      -1,    11,   289,    -1,    11,   289,    33,    -1,   290,    -1,
     289,    33,   290,    -1,   245,   312,    -1,    47,   292,    -1,
     292,    -1,   291,   146,   292,    -1,   296,    -1,   293,    -1,
     295,    -1,   294,    -1,   297,    -1,   298,    -1,    17,   292,
      18,    -1,   181,    -1,   180,    -1,   180,    98,    -1,   292,
      90,   180,    -1,   292,   104,   180,    -1,   292,    91,    92,
      -1,   292,    19,    -1,   292,    17,   291,    18,    -1,   292,
      17,   292,    86,   292,    18,    -1,   292,    90,   325,    -1,
     292,   153,   180,    -1,   292,   153,   325,    -1,   292,   152,
     321,    -1,   292,   152,   327,    -1,    58,   299,    59,   292,
      -1,   301,    -1,   303,    -1,   306,    -1,   307,    -1,   134,
     329,    13,   292,    -1,   135,   329,    13,   292,    -1,   136,
     326,    13,   292,    -1,    52,   326,    13,   292,    -1,    29,
      30,    -1,    29,   291,    30,    -1,    29,   292,    31,   329,
      30,    -1,    29,   292,    31,   329,    13,   292,    30,    -1,
      29,   292,    86,   292,    30,    -1,    15,    -1,    20,   291,
      21,    -1,    20,   292,    31,   327,    21,    -1,    20,   292,
      31,   327,    13,   292,    21,    -1,   170,    -1,    29,   308,
      30,    -1,    29,   309,    31,   329,    30,    -1,    29,   309,
      31,   329,    13,   292,    30,    -1,    88,    17,   291,    18,
      -1,    89,    17,   291,    18,    -1,    93,   333,    13,   292,
      -1,   168,    17,   292,    18,    -1,    95,   248,    17,   292,
      18,    -1,    95,    17,   292,   146,   246,    18,    -1,    96,
      17,   291,    18,    -1,    97,    17,   292,   146,   292,    18,
      -1,   150,   291,   151,    -1,   150,   292,    31,   329,   151,
      -1,   150,   292,    31,   329,    13,   292,   151,    -1,   183,
      -1,   182,    -1,   184,    -1,   185,    -1,    92,    -1,   187,
      -1,   160,    -1,   137,    -1,   186,    -1,   300,    -1,   299,
     146,   300,    -1,   258,    -1,   264,    -1,    60,   292,    61,
     292,   302,    -1,    63,   292,    -1,    62,   292,    61,   292,
     302,    -1,    64,   292,   149,   304,   173,    -1,    64,   292,
     149,   304,   146,    65,    27,   292,   173,    -1,   305,    -1,
     304,   146,   305,    -1,   323,    27,   292,    -1,    66,   292,
      -1,    67,   292,    -1,    68,   292,    -1,    69,   292,    -1,
      70,   292,    -1,    71,   292,    -1,    72,   292,    -1,    73,
     292,    -1,    74,   292,    -1,    75,   292,    -1,    76,   292,
      -1,    77,   292,    -1,    78,   292,    -1,    79,   292,    -1,
      80,   292,    -1,    81,   292,    -1,    82,   292,    -1,    83,
     292,    -1,    84,   292,    -1,    85,   292,    -1,   292,    66,
     292,    -1,   292,    37,   292,    -1,   292,    67,   292,    -1,
     292,   106,   292,    -1,   292,   105,   292,    -1,   292,   107,
     292,    -1,   292,   108,   292,    -1,   292,   109,   292,    -1,
     292,   110,   292,    -1,   292,   111,   292,    -1,   292,   112,
     292,    -1,   292,    12,   292,    -1,   292,   113,   292,    -1,
     292,   114,   292,    -1,   292,   115,   292,    -1,   292,   116,
     292,    -1,   292,   117,   292,    -1,   292,   118,   292,    -1,
     292,   119,   292,    -1,   292,   120,   292,    -1,   292,   121,
     292,    -1,   292,   122,   292,    -1,   292,   123,   292,    -1,
     292,   124,   292,    -1,   292,   125,   292,    -1,   292,   126,
     292,    -1,   292,   127,   292,    -1,   292,   128,   292,    -1,
     292,   129,   292,    -1,   292,   130,   292,    -1,   292,   131,
     292,    -1,   292,   132,   292,    -1,   292,   133,   292,    -1,
     309,    -1,   308,   146,   309,    -1,   292,    87,   292,    -1,
     292,    27,   225,    -1,   310,    31,   292,    27,   225,    -1,
     312,    -1,   311,   146,   312,    -1,   180,   149,   246,    -1,
     180,   149,   246,   100,   292,    -1,   180,   149,   246,    59,
     292,    -1,   314,    -1,   313,    33,   314,    -1,   247,   100,
     292,    -1,    60,   292,    61,   225,   316,    63,   225,    -1,
      60,   292,    61,   225,    63,   225,    -1,    62,   292,    61,
     225,    -1,   316,    62,   292,    61,   225,    -1,    64,   292,
     149,   318,   173,    -1,    64,   292,   149,   318,   146,    65,
      27,   225,   173,    -1,   319,    -1,   318,   146,   319,    -1,
     323,    27,   225,    -1,   283,   271,   274,    -1,   324,    -1,
     322,    -1,   325,    -1,    88,    17,   323,   146,   321,    18,
      -1,    89,    19,    -1,    89,    17,   323,    18,    -1,   321,
      -1,   323,   146,   321,    -1,   180,    -1,    67,    -1,   296,
      -1,   293,    -1,   295,    -1,   294,    -1,   297,    -1,   298,
      -1,    17,   292,    18,    -1,   327,    -1,   328,    -1,   321,
     118,   292,    -1,   321,   149,   246,    -1,   330,    -1,   329,
     146,   330,    -1,   331,    -1,   332,    -1,   323,   118,   292,
      -1,   323,   149,   246,    -1,   328,    -1,   333,   146,   328,
      -1
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
     248,   252,   254,   256,   258,   260,   263,   267,   273,   279,
     283,   287,   291,   295,   301,   305,   311,   315,   319,   323,
     326,   333,   341,   345,   353,   357,   369,   377,   387,   393,
     399,   408,   417,   422,   427,   432,   439,   447,   457,   467,
     475,   480,   487,   489,   493,   497,   499,   504,   507,   512,
     516,   519,   522,   527,   529,   536,   543,   551,   560,   571,
     576,   578,   582,   584,   588,   591,   594,   597,   601,   609,
     619,   623,   629,   631,   634,   638,   640,   644,   646,   648,
     650,   654,   658,   662,   664,   667,   669,   672,   676,   678,
     680,   682,   684,   687,   689,   691,   693,   695,   697,   700,
     702,   705,   709,   711,   715,   721,   726,   731,   737,   739,
     741,   743,   745,   746,   750,   752,   754,   760,   764,   768,
     772,   775,   778,   781,   786,   791,   793,   795,   797,   801,
     805,   807,   809,   811,   813,   815,   817,   819,   821,   823,
     825,   829,   833,   837,   841,   843,   846,   848,   852,   856,
     861,   863,   866,   870,   872,   876,   879,   885,   891,   895,
     899,   901,   904,   908,   910,   914,   916,   918,   925,   928,
     939,   941,   945,   950,   952,   954,   956,   958,   962,   966,
     972,   973,   975,   979,   985,   987,   988,   991,   993,   994,
     997,  1000,  1001,  1003,  1006,  1008,  1011,  1013,  1015,  1026,
    1034,  1038,  1042,  1046,  1050,  1052,  1054,  1056,  1058,  1059,
    1062,  1064,  1067,  1070,  1075,  1077,  1079,  1081,  1084,  1088,
    1090,  1094,  1097,  1100,  1102,  1106,  1108,  1110,  1112,  1114,
    1116,  1118,  1122,  1124,  1126,  1129,  1133,  1137,  1141,  1144,
    1149,  1156,  1160,  1164,  1168,  1172,  1176,  1181,  1183,  1185,
    1187,  1189,  1194,  1199,  1204,  1209,  1212,  1216,  1222,  1230,
    1236,  1238,  1242,  1248,  1256,  1258,  1262,  1268,  1276,  1281,
    1286,  1291,  1296,  1302,  1309,  1314,  1321,  1325,  1331,  1339,
    1341,  1343,  1345,  1347,  1349,  1351,  1353,  1355,  1357,  1359,
    1363,  1365,  1367,  1373,  1376,  1382,  1388,  1398,  1400,  1404,
    1408,  1411,  1414,  1417,  1420,  1423,  1426,  1429,  1432,  1435,
    1438,  1441,  1444,  1447,  1450,  1453,  1456,  1459,  1462,  1465,
    1468,  1472,  1476,  1480,  1484,  1488,  1492,  1496,  1500,  1504,
    1508,  1512,  1516,  1520,  1524,  1528,  1532,  1536,  1540,  1544,
    1548,  1552,  1556,  1560,  1564,  1568,  1572,  1576,  1580,  1584,
    1588,  1592,  1596,  1600,  1602,  1606,  1610,  1614,  1620,  1622,
    1626,  1630,  1636,  1642,  1644,  1648,  1652,  1660,  1667,  1672,
    1678,  1684,  1694,  1696,  1700,  1704,  1708,  1710,  1712,  1714,
    1721,  1724,  1729,  1731,  1735,  1737,  1739,  1741,  1743,  1745,
    1747,  1749,  1751,  1755,  1757,  1759,  1763,  1767,  1769,  1773,
    1775,  1777,  1781,  1785,  1787
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   549,   549,   557,   563,   574,   575,   576,   577,   578,
     582,   593,   626,   654,   688,   695,   703,   709,   715,   724,
     733,   739,   745,   751,   766,   773,   788,   806,   813,   820,
     830,   844,   853,   863,   873,   883,   891,   899,   910,   923,
     933,   941,   952,   958,   964,   970,   987,   991,   998,  1004,
    1020,  1024,  1031,  1035,  1042,  1059,  1085,  1086,  1087,  1091,
    1092,  1096,  1097,  1098,  1099,  1100,  1101,  1113,  1125,  1126,
    1127,  1128,  1129,  1138,  1139,  1148,  1158,  1159,  1160,  1161,
    1168,  1172,  1173,  1177,  1178,  1182,  1186,  1190,  1194,  1201,
    1202,  1203,  1204,  1205,  1206,  1210,  1214,  1218,  1222,  1226,
    1230,  1231,  1232,  1233,  1234,  1247,  1248,  1250,  1251,  1252,
    1257,  1258,  1269,  1270,  1277,  1279,  1281,  1282,  1283,  1284,
    1303,  1304,  1308,  1309,  1313,  1314,  1315,  1320,  1322,  1323,
    1336,  1337,  1347,  1348,  1349,  1353,  1354,  1366,  1367,  1374,
    1375,  1379,  1380,  1384,  1385,  1389,  1390,  1397,  1401,  1402,
    1403,  1407,  1408,  1412,  1413,  1414,  1415,  1416,  1423,  1427,
    1428,  1429,  1433,  1434,  1438,  1439,  1440,  1441,  1451,  1452,
    1453,  1461,  1462,  1466,  1467,  1468,  1469,  1470,  1471,  1472,
    1473,  1474,  1475,  1476,  1477,  1478,  1487,  1491,  1492,  1493,
    1497,  1498,  1499,  1500,  1501,  1502,  1503,  1504,  1508,  1509,
    1513,  1514,  1518,  1519,  1523,  1524,  1528,  1529,  1530,  1534,
    1545,  1546,  1547,  1551,  1552,  1556,  1560,  1561,  1562,  1563,
    1567,  1568,  1569,  1573,  1574,  1578,  1579,  1583,  1587,  1591,
    1596,  1597,  1598,  1602,  1603,  1604,  1608,  1609,  1613,  1614,
    1617,  1619,  1623,  1624,  1628,  1629,  1633,  1637,  1638,  1642,
    1653,  1654,  1658,  1659,  1666,  1667,  1671,  1672,  1676,  1680,
    1684,  1685,  1686,  1687,  1691,  1692,  1693,  1697,  1698,  1702,
    1706,  1707,  1711,  1712,  1716,  1717,  1739,  1740,  1741,  1745,
    1746,  1750,  1751,  1755,  1756,  1761,  1762,  1763,  1764,  1765,
    1766,  1768,  1769,  1770,  1771,  1772,  1773,  1774,  1775,  1776,
    1777,  1778,  1780,  1781,  1782,  1783,  1785,  1786,  1787,  1788,
    1789,  1791,  1792,  1793,  1794,  1796,  1797,  1799,  1800,  1802,
    1804,  1815,  1817,  1818,  1820,  1821,  1823,  1824,  1826,  1828,
    1830,  1839,  1840,  1841,  1846,  1854,  1856,  1857,  1858,  1864,
    1865,  1869,  1873,  1877,  1878,  1879,  1883,  1887,  1893,  1894,
    1898,  1899,  1903,  1907,  1908,  1912,  1913,  1917,  1918,  1922,
    1926,  1927,  1928,  1929,  1930,  1931,  1932,  1933,  1934,  1935,
    1936,  1937,  1938,  1939,  1940,  1941,  1942,  1943,  1944,  1945,
    1949,  1950,  1951,  1952,  1953,  1954,  1955,  1956,  1957,  1958,
    1959,  1960,  1961,  1962,  1963,  1964,  1965,  1966,  1967,  1968,
    1969,  1970,  1971,  1972,  1973,  1974,  1975,  1976,  1977,  1978,
    1979,  1980,  1981,  1985,  1986,  1990,  1994,  1995,  1999,  2000,
    2004,  2005,  2011,  2018,  2019,  2032,  2036,  2037,  2041,  2042,
    2046,  2047,  2051,  2052,  2056,  2060,  2064,  2065,  2069,  2071,
    2080,  2081,  2085,  2086,  2090,  2092,  2097,  2098,  2099,  2100,
    2101,  2102,  2103,  2107,  2108,  2112,  2116,  2120,  2121,  2125,
    2126,  2130,  2134,  2138,  2139
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

  private static final int yylast_ = 11995;
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
/* Line 3370 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


