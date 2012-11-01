
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
	

/* Line 374 of cmlskeleton.java.m4  */
/* Line 809 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -663;
  public static final short yypact_[] =
  {
        48,   404,  -133,   -73,   404,   415,   -56,   -34,   130,    48,
    -663,  -663,  -663,  -663,  -663,  -663,  -663,  -663,  -663,  -663,
    -663,  -663,  -663,   184,  -663,   173,  -109,   333,  -663,  -663,
     410,   -73,  -663,   236,   401,  -663,  -663,  -663,  1213,   422,
    -663,   451,   454,  -663,  -663,   404,    82,   289,  1301,  -133,
    2754,  -663,   125,  -663,   404,  2754,   455,   381,  -663,  -663,
    -663,     0,  -663,  -663,  -663,  -663,  -663,  -663,  -663,  -663,
    -663,  -663,  -663,  -663,  -663,     3,  -663,   415,   124,   546,
    -663,  1301,  1694,  -109,  -663,  1301,   384,  1301,   307,  1301,
    1301,  1301,  1301,  1301,  -663,  -663,  -663,  -663,  -663,  -663,
    -663,  -663,  -663,   929,   329,  -663,  -663,  -663,  -663,  -663,
    -663,  -663,  2754,  2754,  2210,  1468,  1842,  2754,  2754,  2754,
    2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,
    2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,   500,
     503,  1468,    11,   507,   528,  1468,  1468,  1468,  2754,   541,
    -663,   461,  -663, 11122,  -663,  -663,  -663,  -663,  -663,  -663,
    -663,  -663,  -663,  -663,  1468,  -663,  1301,   397,  -663,  5255,
    1468,  1468,  -663,  2754,  1301,  2754,  1301,  -663,   408,   644,
     409,   409,   409,   289,   409,   409,  2754,  1628,    80,  3170,
      96,  -663,   825,    69,   929,   151,  -663,   685,  1301,  1301,
     921,   579,   745,   789,   586,   586,   586,  1301,  1301,  1301,
    1301,   449,   453,  5377,    10,  5500,  -663,    -5,  4520,   186,
     634,  -663,  -663,   -83,  -663,  -663,   629,  -663,  -663,    15,
    -663,  -663,   -26,  -663,  5622,  5744,     2,     2,     2,     2,
   11573,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,  3343,  2754,  2754,   517,  -663,
      19,  2754,   651,  2754,  2754,  -663,   246,    29,  -663,  -663,
    -663,    43,   658,  -108,  5867,  2754,  -663,  2754,  2754,  -663,
    2754,  2754,  2754,   718,   583,   497,  2754,  2754,  2754,  2754,
    2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,
    2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,  2754,
    2754,  2754,  2754,  2754,  2754,  1468,   729,   120,   222,   929,
     501,   531,   -20,  -663,   536,   133, 11122,   430, 11122,   660,
     513,   404,   523,   393,   532,   644,  -663,  -663,  -663,  -663,
    -663,   256,    99,  -663,  -663,   285,   291,   374,   435,   458,
    5989,   506,   526,  2576,  1830,  -663,  -663,  2754,  -663,   914,
     914,   914,  2754,   914,   914,  2754,  2754,  2754,   914,   914,
    2754,  2754,  2754,  -663,   914,   289,  1468,  -663,  1301,  1301,
    -663,  -663,  -663,   929,   929,  -663,  1694,  1301,  1301,   929,
     586,   586,   929,  -663,  -663,  -663,  -663,  2754,  1468,  -663,
    1468,  2754,  2754,  -663,  2754,  1468,  2754,  1301,  2754,  1301,
    2754,  1842,  2754,  1468,   160, 11122,   177,  2754,  1468,  6111,
    2754,   189,  6233,  2754,  1468,  1301,  2754,  1468,  2754,  2754,
    -663,  1468,  6355, 11623,   190,  6477,  1223, 11696, 11696,  -663,
    -663,  -663,  -663,  1223,  1223,  1223,  1223, 11623, 11623, 11623,
   11623, 11623, 11446, 11496, 11366, 11244, 11623, 11623, 11623, 11623,
    3486,  3486,  3343,  1588,  3608,  3608,  3343,  3343,     2,     2,
     367,   200,  -663,  -663,  -663,  -663,  -663,  1468,  1301,   607,
    1301,  2754,   527,   640,  -663,  1468,  -663,  2754,  2754,   644,
     529,   404,  -663,  -663,  -663,  -663,  2754,   547,   675,  -663,
    -663,  -663,  2754,   409,   914,   409,   914,   914,   914,   293,
     914,   409,   699,   -19,   703,   550,   409,   409,   409,  2843,
    2346,  -663,  -663,  -663,  -663,  2754,   409,   409,  1842,  2754,
    2754,   704,  2482,  2754,  2754,  2754,  2754,   882,  2754,  2023,
     537,  -663,  -663,  -663,  2576,  -663,   192,   611,  1174,   242,
    3212,  4153,  1607,   475,  6599,   362,  6721,  4275,   460,    26,
   11122, 11122, 11122,  3170,  -663,   686,   929,   929,   991,   586,
     586, 11122,   613,    23,    78,  6845, 11122,  6967,  -663,    87,
   11623,   929, 11244, 11122,  -663,  4642,   -42,  -663,    38,  -663,
    -663, 11244,  -663,  1301,  7089,  -663,  2754, 11122,  -663,   929,
   11244,  -663, 11244, 11244,    28,  -663,  -663,  2754,   275,   929,
    1468,  -663,   268,   929, 11122,   584,  2754,  -663,   719, 11122,
   11122,   565,   128,  -663, 11122,   587,  -663,   393, 11122,  -663,
    -663,  -663,  -663,  -663,  -663,  3148,  2754,  -663,  -663,   465,
    2754,   575,   731,  2895,   748,   472,   477,   489,   547,   289,
     289,   289,  -109,  2998,   496,  -663,   510,    62,   720,  -663,
    7211,   747, 11122,   534,   535,   -25,  4764,   -13,  7333,   590,
    -663, 11122,  7455,  7577,    89,  7699,   -11,  1468,   761,    63,
     715,  3420,  2576,  2576,  2576,  2754,  2576,  2576,  2754,  2754,
    2754,  2576,  2576,  2754,  2754,  2754,  -663,  -663,  2754,  -663,
    2618,  2318,  -663,  2754,   914,   914,  -663,  2754,  2754,   914,
    2754,  -663,  2754,  -663,  2754,  -663,  -663,  2754,  -663,  2754,
    2754,  -663,   427,  -663,  2754,   778,  -663,  7821,  2754,  -663,
    7943,  1301,   202,  1468,  2037,  1301, 11122,  -663,  -663,  1778,
     397,  1301,  -663,   758,  8065,   914, 11122,   769,  2754,  -663,
    3040,   563,  2895,  2576,  2576,   766,   127,  -663,  -663,  -663,
    -663,  -663,  2576,   373,  2576,  -663,  -663,    62,  -663,  2754,
     640,    68,  -663,   770,  2576,  2576,  2576,  2576,  2754,  -663,
    1468,   265,    21,  -663,   409,   409,   783,  -663,   677,  2754,
    2754,  2754,  2576,  1761,  1915,  3190,  4031,  1530,   763,  4887,
    8188,  4397,   371,   394, 11122, 11122, 11122,   203,  2754, 11122,
    -663,  8310,    26,  1607,  8432,  8555,   460, 11122,  8677,  8801,
    8925,  9047, 11244,   774,  -663, 11122,  -663,  -663,  9169,  -663,
     929,  -663,   260,  -663,  -663,   720, 11122,   929,   261,   255,
     622,   751,   662,   740,  2754,   914,  -663,  2754, 11122,  2576,
    -663,  -663,  -663,  2754,  2576,   547,  3018,  -663,  3096,  -663,
     280,  -663,  2576,  2576,  2754,  -663,  3040,  3040,  1998,  9291,
     188,  -663,    88,  2754,  -663,   590,   592,   643,  2576,  2754,
    9413,  3542,  3664,  3040,  2754,  2754,  2576,  2754,  2576,  2754,
    2576,  -663, 11122,   914,  2754,  1468,  -663,  -663,  -663,  2754,
    2754,  -663,  -663,   640,  1815,  1856,   607,   720,  2754,  2754,
    9535,  -663, 11122,  3040,  9657,  3105,  -663,   807,   808,  1301,
    -663,   763,  5010,  2754,  2576,   238,  2576,  1433,  -663,  2576,
    -663,   810,  2576,  3040,   262,  3786,  2754,  2576,  2576,  9779,
    9901,   394,  5133,  1530, 10023,   371,  1607, 11122,   126,  4642,
    2974,   777,  -663,   384,   929,   384,   929,   273,   640, 11122,
   11122,  -663,  2576,  -663,  2754,  2754,   929,  2576,  2754, 10145,
    3040,  2754,  2576,  3040,   804,  -663,  3040,  2754,  -663,  -663,
    2576,  2576,  3298,  3040,  3040,  2754,  2576,  2754,  2576,  2576,
    2754,  -663,  -663,  -663,  2754,  -663,  2304,  -663,  -663,   263,
     269,  -663, 10267,  2576, 10389,  3040,  2576, 10511,  3040,  3040,
    2576,  2754, 10634,  1530, 10756,  1530,  -663, 10878, 11122,  -663,
    -663,  3040,   720,  -663,  -663,  2576,  3040,  2576,  2590,  2576,
    3040,  3908,  2754,  2576,  -663,   640,  -663,  3040,  -663,  -663,
    2576, 11000,  1530,  -663,  3040,  2576,  1530
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
       0,    39,    58,    60,     0,     0,   419,    89,     0,     0,
      67,    68,     0,     0,     0,     0,     0,     0,   430,     0,
     424,     0,     0,   120,     0,     0,     0,     0,     0,     0,
       0,    75,     0,    88,     0,    73,    20,   131,     0,     0,
       0,   251,   247,   263,   261,   262,   260,     0,     0,   422,
     421,    30,     0,   101,     0,     0,   273,     0,     0,     0,
     427,     0,     0,   417,     0,   433,   434,     0,    95,    80,
       0,     0,     0,   115,   114,     0,     0,     0,     0,     0,
       0,   128,   354,   356,     0,   229,     0,   259,    96,     0,
       0,    99,     0,     0,     0,   426,     0,     0,   121,   116,
       0,     0,     0,    86,     0,    83,    81,     0,   250,   265,
     266,   264,   245,    90,    91,     0,   428,     0,     0,     0,
     117,     0,     0,     0,   129,   248,    98,   429,   431,    97,
       0,     0,    87,   258,   118,     0,    85
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -663,  -663,  -663,   823,  -663,  -663,   779,  -136,   156,  -663,
     487,  -663,  -663,    93,   820,  -663,  -663,    91,   -99,  -663,
    -663,  -663,   802,   -78,   101,  -663,  -663,   829,  -663,  -326,
    -177,   294,  -663,   818,    56,  1344,  1036,   722,  -140,  -663,
    -663,   482,  -191,   681,   350,  -663,   800,   845,   447,  -663,
     830,  -663,  -663,   852,   -18,  -663,   270,  -663,  -663,   147,
    -639,  -663,  -145,  -481,  -663,  -663,  -663,  -319,  -663,  -663,
    -663,  -663,    54,  -663,  -663,   129,  -663,  -663,  -663,   292,
     -90,   -50,    12,   380,   578,   853,  1051,   672,   369,   509,
    -663,   -24,  -663,  -663,   201,  -663,  -663,  -663,   522,   396,
    -663,  -640,  -663,  -662,  -663,  -663,  -663,  -663,     6,  -663,
      -4,   -27,  -148,  -663,    14,  -144,  -285,  -135,  -129,   511,
    -663,  -663,  -663
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,   189,   342,   343,   354,
     355,   515,   513,   749,   750,   934,   654,   655,   697,   555,
      13,    27,    28,   751,   344,    14,    31,    32,    15,   334,
     335,   336,    23,    24,   490,   194,   540,   105,   106,   107,
     108,   195,   196,   377,   337,    39,    40,   230,   338,    34,
      35,    36,    37,   231,   612,   835,   167,   317,   841,   322,
     483,   484,   951,   952,   995,   339,   491,   492,   493,   494,
     840,  1022,   658,   659,   767,   768,   769,   340,   498,   499,
     214,   415,   154,   155,   156,   157,   158,   159,   232,   233,
     160,   721,   161,   586,   587,   162,   163,   219,   220,   667,
     756,   626,   782,   541,   542,   925,   543,   870,   871,   661,
     265,   224,   266,   225,    76,   226,   227,   228,   267,   268,
     269,   270,   260
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final String yyTableString = "153,190,617,272,380,169,259,783,757,501,356,75,173,495,641,175,318,271,778,770,778,279,324,325,217,399,320,173,261,481,473,396,417,410,775,406,712,47,397,874,48,728,426,430,713,345,346,26,348,349,69,1,2,3,875,4,428,25,273,364,33,38,213,215,218,724,407,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,75,373,714,283,284,81,862,5,357,274,358,717,25,786,29,722,347,285,30,715,374,33,223,504,573,378,929,82,718,765,766,411,411,790,326,41,328,482,69,69,375,43,723,505,38,313,314,350,258,476,990,854,397,164,223,165,164,42,165,84,174,29,486,176,69,315,316,397,69,69,69,779,642,787,621,409,418,414,416,85,366,86,87,623,421,427,427,69,356,589,729,191,406,69,69,424,370,88,371,434,427,89,90,91,92,93,590,903,372,376,95,96,97,98,99,100,101,102,595,606,863,702,419,407,930,422,916,403,45,379,279,831,891,6,7,427,432,47,433,435,48,436,437,438,427,424,397,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,588,477,546,958,320,991,574,427,855,166,364,579,739,902,424,979,1023,207,592,191,733,208,1024,198,861,733,284,209,178,16,69,179,440,506,210,971,972,604,16,507,199,397,180,181,182,183,472,551,636,734,554,556,557,505,996,560,561,562,397,505,184,185,69,69,608,475,193,404,314,927,397,397,66,397,95,96,97,98,99,100,101,102,571,424,397,17,575,576,46,577,211,580,905,582,17,583,928,585,904,423,873,49,591,424,212,594,478,565,597,502,639,600,380,602,603,645,646,647,1035,75,366,279,508,69,497,663,664,424,680,572,425,367,368,171,369,172,370,503,371,687,48,424,375,980,397,69,198,69,372,258,397,52,69,70,211,598,424,50,69,731,69,397,687,199,919,69,614,-172,212,54,186,69,619,620,69,496,656,487,69,55,674,624,18,509,373,373,373,628,373,373,77,18,207,284,373,373,208,732,78,373,187,79,209,505,83,660,510,170,547,210,662,564,997,745,666,668,618,671,672,673,753,675,201,681,69,754,505,823,364,221,70,70,69,505,313,314,75,755,-172,-172,505,706,-172,707,762,505,649,650,651,366,56,57,256,689,58,257,70,505,764,263,70,70,70,692,763,693,373,694,679,373,373,373,366,373,373,69,689,695,375,70,264,727,773,774,69,70,70,-172,693,698,694,699,730,275,276,180,181,182,183,59,695,736,505,505,19,20,21,22,-172,588,-172,849,321,184,185,19,20,21,22,-172,832,744,60,330,341,746,19,20,21,22,-172,375,-172,-172,-172,-172,-172,-172,366,84,931,632,222,807,62,63,64,65,66,67,386,71,369,366,370,191,371,69,209,610,505,611,211,357,393,358,372,872,394,370,796,371,700,799,800,801,212,408,804,805,806,372,1,876,877,809,4,331,811,332,333,932,814,815,629,817,631,818,70,819,405,407,820,420,821,822,429,488,788,825,441,505,442,828,860,480,479,485,497,836,489,512,207,186,69,616,208,5,71,71,70,70,209,848,19,20,21,22,382,210,500,514,615,627,622,74,640,207,373,373,643,208,373,187,71,109,669,209,71,71,71,188,625,869,210,644,406,710,735,69,55,741,737,738,880,881,882,71,748,191,69,55,373,71,71,758,759,760,109,109,747,70,109,892,109,752,109,109,109,109,109,948,207,772,481,94,208,207,789,791,844,208,209,70,872,70,847,209,387,210,70,853,74,74,210,864,70,69,70,910,879,826,912,70,908,878,900,906,914,70,207,657,70,482,208,58,70,373,74,922,209,207,74,74,74,208,58,210,809,964,965,209,388,994,935,977,1006,44,210,939,940,74,942,109,944,909,545,74,74,947,850,109,373,109,949,950,110,207,191,857,59,208,70,959,960,51,71,209,80,262,70,59,109,568,210,109,109,376,969,999,1000,381,177,60,109,109,109,109,68,168,53,982,842,957,60,1043,72,740,71,71,907,859,665,439,55,62,63,64,65,66,67,366,69,70,474,689,62,63,64,65,66,67,70,1002,742,584,1004,693,824,694,992,578,1007,180,181,182,183,676,975,695,1012,0,1014,601,69,1017,0,385,0,1018,0,184,185,207,221,0,0,208,0,71,74,207,0,209,0,208,1031,0,0,0,210,209,0,72,72,56,57,0,210,58,0,71,0,71,0,0,0,1041,71,0,0,0,74,74,71,70,71,0,0,72,0,71,0,72,72,72,0,71,0,0,71,0,0,85,71,86,87,0,0,0,0,0,72,0,59,0,0,0,72,72,0,88,0,0,0,89,90,91,92,93,0,0,0,0,0,0,0,60,0,0,0,0,677,74,0,109,109,0,0,0,71,186,70,109,109,109,0,678,71,62,63,64,65,66,67,74,0,74,0,0,0,0,74,0,109,0,109,0,74,104,74,187,0,0,73,74,0,0,0,547,0,74,109,0,74,0,0,70,74,0,0,71,0,0,0,0,0,0,70,0,71,0,104,104,0,0,104,0,104,0,104,104,104,104,104,0,0,0,0,0,0,72,0,548,549,550,0,552,553,0,0,0,558,559,74,109,0,109,563,0,0,0,74,0,0,70,0,0,0,711,0,73,73,72,72,0,193,0,539,0,0,0,66,0,95,96,97,98,99,100,101,102,0,71,360,0,0,73,0,0,0,73,73,73,0,74,0,104,0,0,0,0,364,0,74,104,0,104,0,0,73,0,0,0,0,0,73,73,0,0,0,0,0,0,72,55,104,0,0,104,104,0,0,0,0,109,0,279,104,104,104,104,0,0,0,0,72,0,72,0,71,0,0,72,0,0,0,0,0,72,109,72,0,0,0,0,72,0,0,0,70,0,72,0,0,72,0,74,630,72,633,634,635,637,638,0,0,0,0,0,0,0,0,0,0,71,56,57,0,0,58,0,70,0,0,0,71,0,283,284,0,0,366,85,0,86,87,0,0,0,0,0,285,367,368,72,369,0,370,73,371,88,0,72,653,89,90,91,92,93,372,0,305,0,74,59,309,310,311,312,313,314,0,71,0,0,0,0,0,701,0,73,73,0,0,0,0,0,60,0,315,316,0,0,0,0,72,0,0,0,0,0,0,0,0,72,0,103,61,74,62,63,64,65,66,67,0,0,109,0,74,0,109,0,0,0,109,0,109,104,104,0,0,0,0,0,0,104,104,104,192,0,73,0,197,0,200,0,202,203,204,205,206,0,0,0,0,0,104,0,104,0,0,0,73,55,73,74,0,0,0,73,0,0,0,0,104,73,72,73,0,0,0,0,73,0,0,0,71,0,73,0,0,73,0,0,94,73,812,813,55,0,66,816,95,96,97,98,99,100,101,102,0,974,0,221,0,793,794,795,71,797,798,0,0,319,802,803,0,104,0,104,0,327,0,329,56,57,0,846,58,0,0,73,0,72,0,0,0,0,221,73,0,0,0,0,0,383,384,682,683,0,0,0,0,0,389,390,391,392,0,56,57,0,0,58,0,0,687,0,0,0,74,0,0,59,0,0,851,852,72,109,109,0,73,0,0,856,0,858,0,72,0,73,0,0,109,0,60,865,866,867,868,0,74,0,0,0,0,104,59,0,279,0,0,0,0,883,222,0,62,63,64,65,66,67,359,360,0,911,280,0,0,60,104,0,1,0,72,351,4,331,352,332,333,364,353,0,0,0,0,0,0,222,0,62,63,64,65,66,67,0,0,0,0,0,73,0,0,0,0,0,0,0,913,0,0,946,366,915,0,5,689,283,284,0,0,920,921,690,691,0,692,0,693,0,694,285,286,287,288,289,0,933,0,0,695,0,0,0,781,941,0,943,0,945,85,305,86,87,0,309,310,311,312,313,314,566,567,0,0,0,0,73,88,0,569,570,89,90,91,92,93,0,0,315,316,0,0,970,0,973,0,72,976,366,581,978,327,0,0,0,983,984,0,0,367,368,0,369,0,370,104,371,599,0,104,0,73,0,104,683,104,372,0,72,0,998,0,73,0,0,1001,0,0,0,0,1005,0,687,85,0,838,87,0,1008,1009,0,0,0,0,1013,0,1015,1016,0,0,0,88,0,0,1021,89,90,91,92,93,609,1026,613,0,1028,0,0,0,1030,73,85,1,953,87,351,4,331,352,332,333,0,544,0,1036,0,1037,0,1039,88,0,0,1042,89,90,91,92,93,55,1044,0,0,0,0,1046,0,0,0,0,0,0,0,85,193,955,87,0,5,0,66,0,95,96,97,98,99,100,101,102,0,88,0,0,0,89,90,91,92,93,0,0,0,0,366,0,0,0,689,0,0,781,0,0,0,690,691,0,692,0,693,0,694,0,0,0,0,0,0,0,56,57,695,0,58,0,0,725,0,0,104,104,0,0,0,0,73,0,687,0,0,0,0,0,0,104,0,0,94,0,0,0,0,0,66,0,95,96,97,98,99,100,101,102,0,0,0,0,73,59,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,94,0,0,0,0,0,66,60,95,96,97,98,99,100,101,102,0,682,683,684,0,0,0,685,0,0,0,229,0,62,63,64,65,66,67,686,687,0,0,0,0,94,682,683,684,0,0,66,685,95,96,97,98,99,100,101,102,111,0,112,686,687,113,366,0,923,924,689,0,0,0,114,0,0,690,691,0,692,0,693,830,694,0,0,837,0,0,0,839,0,843,695,0,0,115,833,834,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,0,688,366,0,0,0,689,0,0,0,0,0,0,690,691,0,692,0,693,0,694,0,0,0,0,0,688,366,0,0,695,689,145,146,147,59,0,0,690,691,0,692,0,693,0,694,0,0,148,0,0,0,0,0,0,695,0,696,60,0,0,0,0,0,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,111,0,112,0,0,113,0,0,0,0,0,0,0,0,114,216,0,0,0,0,0,0,0,954,956,0,0,0,0,0,0,0,0,0,0,0,0,115,966,0,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,0,0,0,0,0,0,516,517,518,519,0,0,520,0,521,522,523,524,525,0,682,683,684,0,526,527,685,0,0,0,0,0,145,146,147,59,0,0,686,687,0,0,0,0,0,1019,1020,0,148,111,528,112,529,0,113,0,530,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,657,0,0,116,531,117,532,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,533,0,0,0,0,534,0,0,0,0,0,0,0,0,688,366,0,0,0,689,535,536,537,0,0,538,690,691,0,692,0,693,0,694,145,146,147,59,94,0,0,0,0,695,0,810,0,0,0,0,148,111,0,112,0,670,113,0,0,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,0,0,0,0,0,0,516,517,518,519,0,0,520,0,521,522,523,524,525,0,682,683,684,0,526,527,685,0,0,0,0,0,145,146,147,59,0,0,686,687,0,0,0,0,0,0,0,0,148,111,528,112,529,0,113,0,530,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,0,0,0,116,531,117,532,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,808,142,143,144,0,0,533,0,0,0,0,534,0,0,0,0,0,0,0,0,688,366,0,0,0,689,535,536,537,0,0,538,690,691,0,692,0,693,0,694,145,146,147,59,94,0,0,0,0,695,0,1038,0,0,0,0,148,111,0,112,0,0,113,0,0,0,60,0,0,0,0,114,0,0,149,0,150,0,0,0,0,0,0,0,0,0,151,152,62,63,64,65,66,67,115,0,0,0,0,0,116,0,117,0,0,0,118,0,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,0,0,139,140,0,0,58,141,0,142,143,144,0,0,0,0,0,516,517,518,519,0,0,520,0,521,522,523,524,525,0,0,0,0,0,526,527,0,0,0,0,0,0,0,0,0,0,0,145,146,147,59,0,0,0,0,0,0,0,0,0,528,0,529,148,0,0,530,0,516,517,518,519,0,60,520,0,521,522,523,524,525,149,0,150,0,0,526,527,0,0,0,0,0,151,152,62,63,64,65,66,67,648,0,531,0,532,0,0,0,0,0,0,528,0,529,0,0,0,530,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,649,650,651,0,533,277,0,0,0,534,278,0,279,0,0,531,0,532,0,0,0,0,0,0,535,536,537,0,0,538,280,682,683,684,0,761,0,685,0,0,0,0,652,0,0,0,0,0,0,686,687,682,683,684,0,917,533,685,0,281,282,534,0,0,0,0,0,0,0,686,687,0,0,682,683,684,535,536,537,685,0,538,0,283,284,0,0,0,0,0,0,686,687,0,652,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,682,683,684,0,918,0,685,0,0,682,683,684,0,963,0,685,315,316,686,687,0,0,0,0,0,0,0,686,687,0,688,366,0,0,0,689,0,993,0,0,0,0,690,691,0,692,0,693,0,694,688,366,359,360,361,689,743,0,362,695,0,0,690,691,0,692,0,693,0,694,363,364,688,366,359,360,361,689,0,695,362,0,0,0,690,691,0,692,0,693,0,694,363,364,682,683,0,0,0,0,685,695,0,0,0,0,0,0,0,0,0,0,686,687,0,0,359,360,0,0,0,0,362,0,0,0,0,0,688,366,0,0,0,689,363,364,0,688,366,0,690,691,689,692,0,693,0,694,0,690,691,0,692,0,693,0,694,695,0,0,0,0,0,0,0,0,695,0,0,0,0,0,0,0,0,0,0,0,0,0,365,366,0,0,0,0,0,0,0,0,0,0,367,368,0,369,0,370,0,371,277,0,365,366,0,278,0,279,0,372,0,0,0,0,367,368,0,369,0,370,0,371,688,366,0,280,0,689,0,0,0,372,0,0,690,691,0,692,0,693,0,694,0,0,365,366,0,0,0,0,0,695,279,0,281,282,367,368,0,369,0,370,0,371,0,0,0,0,0,0,0,0,0,372,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,283,284,0,0,278,0,279,0,0,0,0,0,0,0,285,0,0,315,316,0,0,0,0,0,280,0,0,0,1010,0,0,1011,0,0,0,0,0,0,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,315,316,0,0,0,0,0,0,0,0,279,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,280,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,283,284,0,280,0,0,0,792,0,0,0,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,279,0,0,0,0,283,284,0,0,0,0,315,316,0,0,0,0,0,280,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,283,284,0,280,0,0,0,937,0,0,0,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,305,306,0,0,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,315,316,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,938,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,981,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,884,885,0,0,0,0,280,0,0,1040,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,703,0,0,0,886,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,704,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,709,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,400,0,890,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,401,402,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,719,720,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,776,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,777,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,887,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,967,888,0,0,0,0,0,0,315,316,968,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,987,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,323,279,0,0,0,988,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,395,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,398,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,412,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,413,0,0,315,316,431,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,511,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,593,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,605,279,0,0,0,0,596,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,607,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,705,0,0,0,0,708,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,716,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,402,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,726,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,771,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,780,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,784,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,785,776,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,827,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,829,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,845,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,889,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,893,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,894,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,895,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,896,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,897,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,315,316,898,0,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,899,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,926,0,901,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,936,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,961,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,962,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,985,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,986,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,989,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1003,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1025,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1027,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1029,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,315,316,1032,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,1033,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,1045,1034,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,278,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,0,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,277,0,0,0,0,0,0,279,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,280,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,281,282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,283,284,277,0,0,0,0,0,0,279,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,297,280,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,277,0,0,0,281,282,0,279,0,0,315,316,0,0,0,0,0,0,0,0,0,0,0,0,0,280,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,295,296,281,282,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,277,283,284,0,0,0,0,279,0,0,0,0,0,315,316,285,286,287,288,289,290,291,292,293,294,280,296,0,0,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,281,282,0,279,0,0,0,0,0,315,316,0,0,0,0,0,0,0,0,0,0,280,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,290,291,292,293,294,0,0,281,282,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,283,284,279,0,0,0,0,0,0,0,0,0,315,316,285,286,287,288,289,0,280,0,0,0,0,0,0,0,0,0,0,0,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,315,316,0,0,0,0,0,0,0,0,0,283,284,0,0,0,0,0,0,0,0,0,0,0,0,285,286,287,288,289,0,0,0,0,0,0,0,0,0,0,0,0,0,303,304,305,306,307,308,309,310,311,312,313,314,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,315,316";
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
  public static final String yyCheckString = "50,79,483,147,195,55,141,669,648,335,187,38,12,332,33,12,164,146,31,658,31,19,170,171,114,30,166,12,17,49,315,21,13,59,59,118,13,146,146,18,149,13,13,151,21,181,182,180,184,185,38,3,4,5,33,7,13,1,148,33,4,5,112,113,114,27,149,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,116,189,13,90,91,12,27,48,17,148,19,13,45,13,2,146,183,104,180,30,13,54,115,13,398,45,27,34,30,56,57,146,146,59,173,180,175,146,115,116,33,0,173,33,77,132,133,186,141,18,13,13,146,17,147,19,17,180,19,47,149,49,18,149,141,152,153,146,145,146,147,173,180,173,489,149,146,256,257,17,143,19,20,491,263,146,146,164,354,18,151,79,118,170,171,146,159,35,161,278,146,39,40,41,42,43,18,835,171,47,188,189,190,191,192,193,194,195,18,18,141,18,261,149,875,264,855,30,33,149,19,18,18,174,175,146,275,146,277,278,149,280,281,282,146,146,146,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,413,146,357,907,409,144,400,146,146,149,33,405,149,18,146,18,18,27,418,183,17,31,18,27,770,17,91,37,169,0,283,172,283,13,44,62,63,431,9,13,44,146,14,15,16,17,315,362,20,46,365,366,367,33,46,370,371,372,146,33,32,33,315,316,477,316,180,146,133,146,146,146,186,146,188,189,190,191,192,193,194,195,397,146,146,0,401,402,180,404,90,406,102,408,9,410,173,412,102,118,100,33,417,146,104,420,149,376,423,118,511,426,568,428,429,516,517,518,1022,411,143,19,13,376,333,526,527,146,537,398,149,154,155,17,157,19,159,146,161,33,149,146,33,146,146,398,27,400,171,418,146,180,405,38,90,424,146,12,411,149,413,146,33,44,149,418,481,17,104,33,142,424,487,488,427,47,519,12,431,17,535,496,0,13,548,549,550,502,552,553,33,9,27,91,558,559,31,610,12,563,172,12,37,33,180,520,13,17,180,44,525,375,958,13,529,530,485,532,533,534,13,536,180,538,477,13,33,65,33,67,115,116,485,33,132,133,528,13,88,89,33,144,92,146,13,33,138,139,140,143,88,89,17,147,92,17,141,33,13,17,145,146,147,157,33,159,630,161,537,633,634,635,143,637,638,528,147,171,33,164,17,596,13,13,537,170,171,137,159,17,161,19,607,17,98,14,15,16,17,137,171,616,33,33,176,177,178,179,180,722,160,13,180,32,33,176,177,178,179,180,733,636,160,180,180,640,176,177,178,179,180,33,182,183,184,185,186,187,143,503,13,505,180,698,182,183,184,185,186,187,36,38,157,143,159,519,161,610,37,17,33,19,90,17,180,19,171,780,180,159,685,161,100,688,689,690,104,13,693,694,695,171,3,784,785,700,7,8,703,10,11,13,707,708,503,710,505,712,283,714,31,149,717,17,719,720,13,12,677,724,92,33,180,728,769,149,180,146,627,734,172,180,27,142,677,50,31,48,115,116,315,316,37,748,176,177,178,179,18,44,173,180,180,33,180,38,12,27,812,813,12,31,816,172,141,48,17,37,145,146,147,180,180,778,44,180,118,46,149,722,17,149,18,173,789,790,791,164,12,643,733,17,846,170,171,649,650,651,81,82,180,376,85,808,87,12,89,90,91,92,93,895,27,21,49,180,31,27,12,59,17,31,37,398,927,400,12,37,38,44,405,20,115,116,44,20,411,780,413,844,118,18,847,418,59,17,27,180,853,424,27,55,427,146,31,92,431,911,141,864,37,27,145,146,147,31,92,44,873,17,17,37,38,51,879,20,27,9,44,884,885,164,887,166,889,100,354,170,171,894,752,174,946,176,899,900,49,27,752,763,137,31,477,908,909,31,283,37,45,142,485,137,195,386,44,198,199,47,923,964,965,195,77,160,207,208,209,210,38,54,33,936,740,906,160,1035,38,622,315,316,841,767,528,180,17,182,183,184,185,186,187,143,895,528,180,147,182,183,184,185,186,187,537,968,627,411,971,159,722,161,949,404,977,14,15,16,17,536,927,171,985,-1,987,427,927,990,-1,21,-1,994,-1,32,33,27,67,-1,-1,31,-1,376,283,27,-1,37,-1,31,1011,-1,-1,-1,44,37,-1,115,116,88,89,-1,44,92,-1,398,-1,400,-1,-1,-1,1032,405,-1,-1,-1,315,316,411,610,413,-1,-1,141,-1,418,-1,145,146,147,-1,424,-1,-1,427,-1,-1,17,431,19,20,-1,-1,-1,-1,-1,164,-1,137,-1,-1,-1,170,171,-1,35,-1,-1,-1,39,40,41,42,43,-1,-1,-1,-1,-1,-1,-1,160,-1,-1,-1,-1,165,376,-1,378,379,-1,-1,-1,477,142,677,386,387,388,-1,180,485,182,183,184,185,186,187,398,-1,400,-1,-1,-1,-1,405,-1,407,-1,409,-1,411,48,413,172,-1,-1,38,418,-1,-1,-1,180,-1,424,425,-1,427,-1,-1,722,431,-1,-1,528,-1,-1,-1,-1,-1,-1,733,-1,537,-1,81,82,-1,-1,85,-1,87,-1,89,90,91,92,93,-1,-1,-1,-1,-1,-1,283,-1,359,360,361,-1,363,364,-1,-1,-1,368,369,477,478,-1,480,374,-1,-1,-1,485,-1,-1,780,-1,-1,-1,173,-1,115,116,315,316,-1,180,-1,353,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,610,15,-1,-1,141,-1,-1,-1,145,146,147,-1,528,-1,166,-1,-1,-1,-1,33,-1,537,174,-1,176,-1,-1,164,-1,-1,-1,-1,-1,170,171,-1,-1,-1,-1,-1,-1,376,17,195,-1,-1,198,199,-1,-1,-1,-1,568,-1,19,207,208,209,210,-1,-1,-1,-1,398,-1,400,-1,677,-1,-1,405,-1,-1,-1,-1,-1,411,593,413,-1,-1,-1,-1,418,-1,-1,-1,895,-1,424,-1,-1,427,-1,610,504,431,506,507,508,509,510,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,722,88,89,-1,-1,92,-1,927,-1,-1,-1,733,-1,90,91,-1,-1,143,17,-1,19,20,-1,-1,-1,-1,-1,104,154,155,477,157,-1,159,283,161,35,-1,485,519,39,40,41,42,43,171,-1,124,-1,677,137,128,129,130,131,132,133,-1,780,-1,-1,-1,-1,-1,544,-1,315,316,-1,-1,-1,-1,-1,160,-1,152,153,-1,-1,-1,-1,528,-1,-1,-1,-1,-1,-1,-1,-1,537,-1,48,180,722,182,183,184,185,186,187,-1,-1,731,-1,733,-1,735,-1,-1,-1,739,-1,741,378,379,-1,-1,-1,-1,-1,-1,386,387,388,81,-1,376,-1,85,-1,87,-1,89,90,91,92,93,-1,-1,-1,-1,-1,407,-1,409,-1,-1,-1,398,17,400,780,-1,-1,-1,405,-1,-1,-1,-1,425,411,610,413,-1,-1,-1,-1,418,-1,-1,-1,895,-1,424,-1,-1,427,-1,-1,180,431,704,705,17,-1,186,709,188,189,190,191,192,193,194,195,-1,65,-1,67,-1,682,683,684,927,686,687,-1,-1,166,691,692,-1,478,-1,480,-1,174,-1,176,88,89,-1,745,92,-1,-1,477,-1,677,-1,-1,-1,-1,67,485,-1,-1,-1,-1,-1,198,199,14,15,-1,-1,-1,-1,-1,207,208,209,210,-1,88,89,-1,-1,92,-1,-1,33,-1,-1,-1,895,-1,-1,137,-1,-1,753,754,722,904,905,-1,528,-1,-1,762,-1,764,-1,733,-1,537,-1,-1,919,-1,160,774,775,776,777,-1,927,-1,-1,-1,-1,568,137,-1,19,-1,-1,-1,-1,792,180,-1,182,183,184,185,186,187,14,15,-1,845,37,-1,-1,160,593,-1,3,-1,780,6,7,8,9,10,11,33,13,-1,-1,-1,-1,-1,-1,180,-1,182,183,184,185,186,187,-1,-1,-1,-1,-1,610,-1,-1,-1,-1,-1,-1,-1,849,-1,-1,893,143,854,-1,48,147,90,91,-1,-1,862,863,154,155,-1,157,-1,159,-1,161,104,105,106,107,108,-1,878,-1,-1,171,-1,-1,-1,669,886,-1,888,-1,890,17,124,19,20,-1,128,129,130,131,132,133,378,379,-1,-1,-1,-1,677,35,-1,387,388,39,40,41,42,43,-1,-1,152,153,-1,-1,924,-1,926,-1,895,929,143,407,932,409,-1,-1,-1,937,938,-1,-1,154,155,-1,157,-1,159,731,161,425,-1,735,-1,722,-1,739,15,741,171,-1,927,-1,962,-1,733,-1,-1,967,-1,-1,-1,-1,972,-1,33,17,-1,19,20,-1,980,981,-1,-1,-1,-1,986,-1,988,989,-1,-1,-1,35,-1,-1,996,39,40,41,42,43,478,1003,480,-1,1006,-1,-1,-1,1010,780,17,3,19,20,6,7,8,9,10,11,-1,13,-1,1025,-1,1027,-1,1029,35,-1,-1,1033,39,40,41,42,43,17,1040,-1,-1,-1,-1,1045,-1,-1,-1,-1,-1,-1,-1,17,180,19,20,-1,48,-1,186,-1,188,189,190,191,192,193,194,195,-1,35,-1,-1,-1,39,40,41,42,43,-1,-1,-1,-1,143,-1,-1,-1,147,-1,-1,875,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,88,89,171,-1,92,-1,-1,593,-1,-1,904,905,-1,-1,-1,-1,895,-1,33,-1,-1,-1,-1,-1,-1,919,-1,-1,180,-1,-1,-1,-1,-1,186,-1,188,189,190,191,192,193,194,195,-1,-1,-1,-1,927,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,186,160,188,189,190,191,192,193,194,195,-1,14,15,16,-1,-1,-1,20,-1,-1,-1,180,-1,182,183,184,185,186,187,32,33,-1,-1,-1,-1,180,14,15,16,-1,-1,186,20,188,189,190,191,192,193,194,195,15,-1,17,32,33,20,143,-1,62,63,147,-1,-1,-1,29,-1,-1,154,155,-1,157,-1,159,731,161,-1,-1,735,-1,-1,-1,739,-1,741,171,-1,-1,52,53,54,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,142,143,-1,-1,171,147,134,135,136,137,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,150,-1,-1,-1,-1,-1,-1,171,-1,173,160,-1,-1,-1,-1,-1,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,15,-1,17,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,29,30,-1,-1,-1,-1,-1,-1,-1,904,905,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,52,919,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,53,54,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,55,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,19,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,14,15,16,-1,32,33,20,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,150,15,58,17,60,-1,20,-1,64,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,101,60,103,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,94,95,96,97,-1,-1,142,-1,-1,-1,-1,147,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,162,163,164,-1,-1,167,154,155,-1,157,-1,159,-1,161,134,135,136,137,180,-1,-1,-1,-1,171,-1,173,-1,-1,-1,-1,150,15,-1,17,-1,-1,20,-1,-1,-1,160,-1,-1,-1,-1,29,-1,-1,168,-1,170,-1,-1,-1,-1,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,52,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,-1,-1,88,89,-1,-1,92,93,-1,95,96,97,-1,-1,-1,-1,-1,14,15,16,17,-1,-1,20,-1,22,23,24,25,26,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,134,135,136,137,-1,-1,-1,-1,-1,-1,-1,-1,-1,58,-1,60,150,-1,-1,64,-1,14,15,16,17,-1,160,20,-1,22,23,24,25,26,168,-1,170,-1,-1,32,33,-1,-1,-1,-1,-1,180,181,182,183,184,185,186,187,99,-1,101,-1,103,-1,-1,-1,-1,-1,-1,58,-1,60,-1,-1,-1,64,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,138,139,140,-1,142,12,-1,-1,-1,147,17,-1,19,-1,-1,101,-1,103,-1,-1,-1,-1,-1,-1,162,163,164,-1,-1,167,37,14,15,16,-1,18,-1,20,-1,-1,-1,-1,180,-1,-1,-1,-1,-1,-1,32,33,14,15,16,-1,18,142,20,-1,66,67,147,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,14,15,16,162,163,164,20,-1,167,-1,90,91,-1,-1,-1,-1,-1,-1,32,33,-1,180,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,14,15,16,-1,18,-1,20,-1,-1,14,15,16,-1,18,-1,20,152,153,32,33,-1,-1,-1,-1,-1,-1,-1,32,33,-1,142,143,-1,-1,-1,147,-1,173,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,14,15,16,147,18,-1,20,171,-1,-1,154,155,-1,157,-1,159,-1,161,32,33,142,143,14,15,16,147,-1,171,20,-1,-1,-1,154,155,-1,157,-1,159,-1,161,32,33,14,15,-1,-1,-1,-1,20,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,14,15,-1,-1,-1,-1,20,-1,-1,-1,-1,-1,142,143,-1,-1,-1,147,32,33,-1,142,143,-1,154,155,147,157,-1,159,-1,161,-1,154,155,-1,157,-1,159,-1,161,171,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,142,143,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,12,-1,142,143,-1,17,-1,19,-1,171,-1,-1,-1,-1,154,155,-1,157,-1,159,-1,161,142,143,-1,37,-1,147,-1,-1,-1,171,-1,-1,154,155,-1,157,-1,159,-1,161,-1,-1,142,143,-1,-1,-1,-1,-1,171,19,-1,66,67,154,155,-1,157,-1,159,-1,161,-1,-1,-1,-1,-1,-1,-1,-1,-1,171,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,90,91,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,104,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,166,-1,-1,-1,-1,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,90,91,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,124,125,-1,-1,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,32,-1,-1,-1,-1,37,-1,-1,163,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,158,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,156,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,156,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,63,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,21,145,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,86,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,149,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,27,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,28,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,152,153,30,-1,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,27,-1,151,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,18,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,32,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,148,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,61,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,31,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,145,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,21,144,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,17,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,12,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,116,37,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,66,67,-1,19,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,114,115,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,12,90,91,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,109,110,111,112,113,37,115,-1,-1,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,66,67,-1,19,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,109,110,111,112,113,-1,-1,66,67,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,90,91,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,104,105,106,107,108,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153,-1,-1,-1,-1,-1,-1,-1,-1,-1,90,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,104,105,106,107,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,122,123,124,125,126,127,128,129,130,131,132,133,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,152,153";
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
     217,   219,   235,   217,   217,   217,    20,   217,   217,   218,
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
     281,   269,   270,   246,    17,    21,   217,    12,   292,    13,
     224,   225,   225,    20,    13,   146,   225,   228,   225,   286,
     291,   274,    27,   141,    20,   225,   225,   225,   225,   292,
     318,   319,   323,   100,    18,    33,   218,   218,    17,   118,
     292,   292,   292,   225,    31,    32,   158,    31,   145,    31,
     156,    18,   292,    21,    28,    31,    21,    30,    30,    61,
      27,   151,    18,   271,   102,   102,   180,   283,    59,   100,
     292,   217,   292,   225,   292,   225,   312,    18,    18,   149,
     225,   225,   292,    62,    63,   316,    27,   146,   173,    27,
     314,    13,    13,   225,   226,   292,    38,   163,   163,   292,
     292,   225,   292,   225,   292,   225,   217,   292,   329,   292,
     292,   273,   274,    19,   246,    19,   246,   265,   271,   292,
     292,    18,    21,    18,    17,    17,   246,    21,    31,   292,
     225,    62,    63,   225,    65,   319,   225,    20,   225,    18,
     146,   163,   292,   225,   225,    32,    21,    31,   145,   148,
      13,   144,   302,   173,    51,   275,    46,   274,   225,   291,
     291,   225,   292,    61,   292,   225,    27,   292,   225,   225,
     163,   166,   292,   225,   292,   225,   225,   292,   292,    53,
      54,   225,   282,    18,    18,    21,   225,    61,   225,    21,
     225,   292,    31,   145,   144,   271,   225,   225,   173,   225,
     163,   292,   225,   273,   225,    21,   225
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
     218,    13,    20,   292,    21,   217,    -1,    32,   218,    13,
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
         0,   549,   549,   553,   554,   558,   559,   560,   561,   562,
     566,   567,   571,   572,   577,   578,   580,   581,   582,   586,
     590,   591,   592,   593,   602,   603,   612,   624,   625,   626,
     630,   631,   632,   633,   634,   636,   637,   638,   642,   646,
     647,   648,   654,   655,   656,   657,   669,   670,   674,   675,
     684,   685,   686,   687,   691,   692,   706,   707,   708,   712,
     713,   717,   718,   719,   720,   721,   722,   734,   746,   747,
     748,   749,   750,   759,   760,   769,   779,   780,   781,   782,
     789,   794,   795,   799,   800,   804,   808,   812,   816,   823,
     824,   825,   826,   827,   828,   832,   836,   840,   844,   848,
     852,   853,   854,   855,   856,   869,   870,   872,   873,   874,
     879,   880,   891,   892,   899,   901,   903,   904,   905,   906,
     925,   926,   930,   931,   935,   936,   937,   942,   944,   945,
     958,   959,   969,   970,   971,   975,   976,   988,   989,   996,
     997,  1001,  1002,  1006,  1007,  1011,  1012,  1019,  1023,  1024,
    1025,  1029,  1030,  1034,  1035,  1036,  1037,  1038,  1045,  1049,
    1050,  1051,  1055,  1056,  1060,  1061,  1062,  1063,  1073,  1074,
    1075,  1083,  1084,  1088,  1089,  1090,  1091,  1092,  1093,  1094,
    1095,  1096,  1097,  1098,  1099,  1100,  1109,  1113,  1114,  1115,
    1119,  1120,  1121,  1122,  1123,  1124,  1125,  1126,  1130,  1131,
    1135,  1136,  1140,  1141,  1145,  1146,  1150,  1151,  1152,  1156,
    1167,  1168,  1169,  1173,  1174,  1178,  1182,  1183,  1184,  1185,
    1189,  1190,  1191,  1195,  1196,  1200,  1201,  1205,  1209,  1213,
    1218,  1219,  1220,  1224,  1225,  1226,  1230,  1231,  1235,  1236,
    1239,  1241,  1245,  1246,  1250,  1251,  1255,  1259,  1260,  1264,
    1275,  1276,  1280,  1281,  1288,  1289,  1293,  1294,  1298,  1302,
    1306,  1307,  1308,  1309,  1313,  1314,  1315,  1319,  1320,  1324,
    1328,  1329,  1333,  1334,  1338,  1339,  1361,  1362,  1363,  1367,
    1368,  1372,  1373,  1377,  1378,  1383,  1384,  1385,  1386,  1387,
    1388,  1390,  1391,  1392,  1393,  1394,  1395,  1396,  1397,  1398,
    1399,  1400,  1402,  1403,  1404,  1405,  1407,  1408,  1409,  1410,
    1411,  1413,  1414,  1415,  1416,  1418,  1419,  1421,  1422,  1424,
    1426,  1437,  1439,  1440,  1442,  1443,  1445,  1446,  1448,  1450,
    1452,  1461,  1462,  1463,  1468,  1476,  1478,  1479,  1480,  1486,
    1487,  1491,  1495,  1499,  1500,  1501,  1505,  1509,  1515,  1516,
    1520,  1521,  1525,  1529,  1530,  1534,  1535,  1539,  1540,  1544,
    1548,  1549,  1550,  1551,  1552,  1553,  1554,  1555,  1556,  1557,
    1558,  1559,  1560,  1561,  1562,  1563,  1564,  1565,  1566,  1567,
    1571,  1572,  1573,  1574,  1575,  1576,  1577,  1578,  1579,  1580,
    1581,  1582,  1583,  1584,  1585,  1586,  1587,  1588,  1589,  1590,
    1591,  1592,  1593,  1594,  1595,  1596,  1597,  1598,  1599,  1600,
    1601,  1602,  1603,  1607,  1608,  1612,  1616,  1617,  1621,  1622,
    1626,  1627,  1633,  1640,  1641,  1654,  1658,  1659,  1663,  1664,
    1668,  1669,  1673,  1674,  1678,  1682,  1686,  1687,  1691,  1693,
    1702,  1703,  1707,  1708,  1712,  1714,  1719,  1720,  1721,  1722,
    1723,  1724,  1725,  1729,  1730,  1734,  1738,  1742,  1743,  1747,
    1748,  1752,  1756,  1760,  1761
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

  private static final int yylast_ = 11849;
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
/* Line 2546 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


