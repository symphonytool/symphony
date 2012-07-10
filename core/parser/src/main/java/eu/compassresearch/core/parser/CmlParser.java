
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
    //    import eu.compassresearch.transforms.*;
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
  public static final int CSP_ACTIONS = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSEQ = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPINTCH = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPEXTCH = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPLCHSYNC = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPRCHSYNC = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPINTERLEAVE = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPHIDE = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int LPAREN = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int RPAREN = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPRENAME = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUARE = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int RSQUARE = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSKIP = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSTOP = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPCHAOS = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPDIV = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPWAIT = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int RARROW = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int LARROW = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLY = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int RCURLY = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPAND = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int BAR = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int DBAR = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANNELS = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANSETS = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int TYPES = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int SEMI = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMRECORDDEF = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMCOMPOSE = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int OF = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMTYPEUNION = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int STAR = 298;
  /** Token number, to be returned by the scanner.  */
  public static final int TO = 299;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMINMAPOF = 300;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMMAPOF = 301;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMSEQOF = 302;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMSEQ1OF = 303;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMSETOF = 304;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMPFUNCARROW = 305;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMTFUNCARROW = 306;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMUNITTYPE = 307;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMTYPENCMP = 308;
  /** Token number, to be returned by the scanner.  */
  public static final int DEQUALS = 309;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMINV = 310;
  /** Token number, to be returned by the scanner.  */
  public static final int VALUES = 311;
  /** Token number, to be returned by the scanner.  */
  public static final int FUNCTIONS = 312;
  /** Token number, to be returned by the scanner.  */
  public static final int PRE = 313;
  /** Token number, to be returned by the scanner.  */
  public static final int POST = 314;
  /** Token number, to be returned by the scanner.  */
  public static final int MEASURE = 315;
  /** Token number, to be returned by the scanner.  */
  public static final int VDM_SUBCLASSRESP = 316;
  /** Token number, to be returned by the scanner.  */
  public static final int VDM_NOTYETSPEC = 317;
  /** Token number, to be returned by the scanner.  */
  public static final int OPERATIONS = 318;
  /** Token number, to be returned by the scanner.  */
  public static final int VDM_FRAME = 319;
  /** Token number, to be returned by the scanner.  */
  public static final int VDM_RD = 320;
  /** Token number, to be returned by the scanner.  */
  public static final int VDM_WR = 321;
  /** Token number, to be returned by the scanner.  */
  public static final int STATE = 322;
  /** Token number, to be returned by the scanner.  */
  public static final int LET = 323;
  /** Token number, to be returned by the scanner.  */
  public static final int IN = 324;
  /** Token number, to be returned by the scanner.  */
  public static final int IF = 325;
  /** Token number, to be returned by the scanner.  */
  public static final int THEN = 326;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSEIF = 327;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSE = 328;
  /** Token number, to be returned by the scanner.  */
  public static final int CASES = 329;
  /** Token number, to be returned by the scanner.  */
  public static final int OTHERS = 330;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUS = 331;
  /** Token number, to be returned by the scanner.  */
  public static final int MINUS = 332;
  /** Token number, to be returned by the scanner.  */
  public static final int ABS = 333;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOOR = 334;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT = 335;
  /** Token number, to be returned by the scanner.  */
  public static final int CARD = 336;
  /** Token number, to be returned by the scanner.  */
  public static final int POWER = 337;
  /** Token number, to be returned by the scanner.  */
  public static final int DUNION = 338;
  /** Token number, to be returned by the scanner.  */
  public static final int DINTER = 339;
  /** Token number, to be returned by the scanner.  */
  public static final int HD = 340;
  /** Token number, to be returned by the scanner.  */
  public static final int TL = 341;
  /** Token number, to be returned by the scanner.  */
  public static final int LEN = 342;
  /** Token number, to be returned by the scanner.  */
  public static final int ELEMS = 343;
  /** Token number, to be returned by the scanner.  */
  public static final int INDS = 344;
  /** Token number, to be returned by the scanner.  */
  public static final int REVERSE = 345;
  /** Token number, to be returned by the scanner.  */
  public static final int DCONC = 346;
  /** Token number, to be returned by the scanner.  */
  public static final int DOM = 347;
  /** Token number, to be returned by the scanner.  */
  public static final int RNG = 348;
  /** Token number, to be returned by the scanner.  */
  public static final int MERGE = 349;
  /** Token number, to be returned by the scanner.  */
  public static final int INVERSE = 350;
  /** Token number, to be returned by the scanner.  */
  public static final int ELLIPSIS = 351;
  /** Token number, to be returned by the scanner.  */
  public static final int MAPLETARROW = 352;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDER = 353;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDERNAME = 354;
  /** Token number, to be returned by the scanner.  */
  public static final int DOT = 355;
  /** Token number, to be returned by the scanner.  */
  public static final int DOTHASH = 356;
  /** Token number, to be returned by the scanner.  */
  public static final int NUMERAL = 357;
  /** Token number, to be returned by the scanner.  */
  public static final int LAMBDA = 358;
  /** Token number, to be returned by the scanner.  */
  public static final int NEW = 359;
  /** Token number, to be returned by the scanner.  */
  public static final int SELF = 360;
  /** Token number, to be returned by the scanner.  */
  public static final int ISUNDER = 361;
  /** Token number, to be returned by the scanner.  */
  public static final int PREUNDER = 362;
  /** Token number, to be returned by the scanner.  */
  public static final int ISOFCLASS = 363;
  /** Token number, to be returned by the scanner.  */
  public static final int BACKTICK = 364;
  /** Token number, to be returned by the scanner.  */
  public static final int TILDE = 365;
  /** Token number, to be returned by the scanner.  */
  public static final int DCL = 366;
  /** Token number, to be returned by the scanner.  */
  public static final int ASSIGN = 367;
  /** Token number, to be returned by the scanner.  */
  public static final int ATOMIC = 368;
  /** Token number, to be returned by the scanner.  */
  public static final int OPERATIONARROW = 369;
  /** Token number, to be returned by the scanner.  */
  public static final int RETURN = 370;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMDONTCARE = 371;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER = 372;
  /** Token number, to be returned by the scanner.  */
  public static final int DIVIDE = 373;
  /** Token number, to be returned by the scanner.  */
  public static final int DIV = 374;
  /** Token number, to be returned by the scanner.  */
  public static final int REM = 375;
  /** Token number, to be returned by the scanner.  */
  public static final int MOD = 376;
  /** Token number, to be returned by the scanner.  */
  public static final int LT = 377;
  /** Token number, to be returned by the scanner.  */
  public static final int LTE = 378;
  /** Token number, to be returned by the scanner.  */
  public static final int GT = 379;
  /** Token number, to be returned by the scanner.  */
  public static final int GTE = 380;
  /** Token number, to be returned by the scanner.  */
  public static final int NEQ = 381;
  /** Token number, to be returned by the scanner.  */
  public static final int OR = 382;
  /** Token number, to be returned by the scanner.  */
  public static final int AND = 383;
  /** Token number, to be returned by the scanner.  */
  public static final int IMPLY = 384;
  /** Token number, to be returned by the scanner.  */
  public static final int BIMPLY = 385;
  /** Token number, to be returned by the scanner.  */
  public static final int INSET = 386;
  /** Token number, to be returned by the scanner.  */
  public static final int NOTINSET = 387;
  /** Token number, to be returned by the scanner.  */
  public static final int SUBSET = 388;
  /** Token number, to be returned by the scanner.  */
  public static final int PROPER_SUBSET = 389;
  /** Token number, to be returned by the scanner.  */
  public static final int UNION = 390;
  /** Token number, to be returned by the scanner.  */
  public static final int SETDIFF = 391;
  /** Token number, to be returned by the scanner.  */
  public static final int INTER = 392;
  /** Token number, to be returned by the scanner.  */
  public static final int CONC = 393;
  /** Token number, to be returned by the scanner.  */
  public static final int OVERWRITE = 394;
  /** Token number, to be returned by the scanner.  */
  public static final int MAPMERGE = 395;
  /** Token number, to be returned by the scanner.  */
  public static final int DOMRES = 396;
  /** Token number, to be returned by the scanner.  */
  public static final int VDM_MAP_DOMAIN_RESTRICT_BY = 397;
  /** Token number, to be returned by the scanner.  */
  public static final int RNGRES = 398;
  /** Token number, to be returned by the scanner.  */
  public static final int RNGSUB = 399;
  /** Token number, to be returned by the scanner.  */
  public static final int COMP = 400;
  /** Token number, to be returned by the scanner.  */
  public static final int ITERATE = 401;
  /** Token number, to be returned by the scanner.  */
  public static final int FORALL = 402;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS = 403;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS1 = 404;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING = 405;
  /** Token number, to be returned by the scanner.  */
  public static final int PARAM_VRES = 406;
  /** Token number, to be returned by the scanner.  */
  public static final int PARAM_RES = 407;
  /** Token number, to be returned by the scanner.  */
  public static final int PARAM_VAL = 408;
  /** Token number, to be returned by the scanner.  */
  public static final int HEX_LITERAL = 409;
  /** Token number, to be returned by the scanner.  */
  public static final int AMP = 410;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPBARGT = 411;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPLSQUAREBAR = 412;
  /** Token number, to be returned by the scanner.  */
  public static final int DLSQUARE = 413;
  /** Token number, to be returned by the scanner.  */
  public static final int DRSQUARE = 414;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPBARRSQUARE = 415;
  /** Token number, to be returned by the scanner.  */
  public static final int COMMA = 416;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPLSQUAREDBAR = 417;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPDBARRSQUARE = 418;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPDBAR = 419;
  /** Token number, to be returned by the scanner.  */
  public static final int COLON = 420;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANSET_SETEXP_BEGIN = 421;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANSET_SETEXP_END = 422;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_CHANNEL_READ = 423;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_CHANNEL_WRITE = 424;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_OPS_COM = 425;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_CHANNEL_DOT = 426;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_SLASH = 427;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_BACKSLASH = 428;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPLSQUAREGT = 429;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_LSQUARE = 430;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_RSQUARE = 431;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_GT = 432;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_ENDBY = 433;
  /** Token number, to be returned by the scanner.  */
  public static final int CSP_STARTBY = 434;
  /** Token number, to be returned by the scanner.  */
  public static final int TBOOL = 435;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT = 436;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT1 = 437;
  /** Token number, to be returned by the scanner.  */
  public static final int TINT = 438;
  /** Token number, to be returned by the scanner.  */
  public static final int TRAT = 439;
  /** Token number, to be returned by the scanner.  */
  public static final int TREAL = 440;
  /** Token number, to be returned by the scanner.  */
  public static final int TCHAR = 441;
  /** Token number, to be returned by the scanner.  */
  public static final int TTOKEN = 442;
  /** Token number, to be returned by the scanner.  */
  public static final int PRIVATE = 443;
  /** Token number, to be returned by the scanner.  */
  public static final int PROTECTED = 444;
  /** Token number, to be returned by the scanner.  */
  public static final int PUBLIC = 445;
  /** Token number, to be returned by the scanner.  */
  public static final int LOGICAL = 446;
  /** Token number, to be returned by the scanner.  */
  public static final int nameset = 447;
  /** Token number, to be returned by the scanner.  */
  public static final int namesetExpr = 448;
  /** Token number, to be returned by the scanner.  */
  public static final int typeVarIdentifier = 449;
  /** Token number, to be returned by the scanner.  */
  public static final int UMINUS = 450;
  /** Token number, to be returned by the scanner.  */
  public static final int UPLUS = 451;



  
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
/* Line 388 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> decls = (List<PDeclaration>) ((yystack.valueAt (1-(1))));  
    currentSourceFile.setDecls(decls);
};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 394 of "src/main/bison/cml.y"  */
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
/* Line 401 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> globalDecls = (List<PDeclaration>)((yystack.valueAt (1-(1))));
    currentSourceFile.setDecls(globalDecls);
};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 409 of "src/main/bison/cml.y"  */
    {  
      List<PDeclaration> programParagraphList = 
	  new Vector<PDeclaration>();
      programParagraphList.add((PDeclaration)((yystack.valueAt (1-(1)))));
      yyval = programParagraphList;   
  };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 417 of "src/main/bison/cml.y"  */
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
/* Line 429 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 430 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 431 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 432 of "src/main/bison/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 11:
  if (yyn == 11)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 438 of "src/main/bison/cml.y"  */
    { 
  AClassbodyDefinition c = new AClassbodyDefinition();
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (4-(2))));
  Position startPos =  ((CmlLexeme)((yystack.valueAt (4-(1))))).getStartPos();
  Position endPos = ((CmlLexeme)((yystack.valueAt (4-(3))))).getEndPos(); // TODO Fix me, the ending position is the 
  LexNameToken lexName = extractLexNameToken( id ); 
  LexIdentifierToken classIdent = extractLexIdentifierToken( id );
  LexLocation loc = new LexLocation(currentSourceFile.getFile(),
				    id.getValue(),
				    startPos.line, 
				    startPos.column, 
				    endPos.line, 
				    endPos.column, 
				    startPos.offset, endPos.offset);
  
  c.setLocation(loc); 
  c.setName(lexName);
  c.setDeclarations( (List<PDeclaration>)((yystack.valueAt (4-(4)))) );
  //  c.setDefinitions((List)$4);
  AClassDeclaration res = new AClassDeclaration();
  res.setClassBody( c );
  res.setLocation ( loc );
  res.setIdentifier( classIdent );
  res.setNameScope( NameScope.CLASSNAME );
  yyval = res;
};
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 470 of "src/main/bison/cml.y"  */
    {
      LexLocation processLoc = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))));
      AProcessDefinition processDef = (AProcessDefinition)((yystack.valueAt (4-(4))));
      LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (4-(2)))));
      LexLocation location = combineLexLocation(processLoc,processDef.getLocation());
      yyval = new AProcessDeclaration(location, NameScope.GLOBAL, id, processDef);
  };
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 481 of "src/main/bison/cml.y"  */
    { 
    PProcess process = (PProcess)((yystack.valueAt (3-(3))));
    List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (3-(1))));
    LexLocation loc = combineLexLocation(extractFirstLexLocation(decls),
					 process.getLocation());
    yyval = new AProcessDefinition(loc, 
				NameScope.GLOBAL, 
				false, 
				null, 
				decls,
				process); 
};
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 494 of "src/main/bison/cml.y"  */
    {
    PProcess process = (PProcess)((yystack.valueAt (1-(1))));
    yyval = new AProcessDefinition(process.getLocation(), 
				NameScope.GLOBAL, 
				false, 
				null, 
				null,
				process);
};
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 507 of "src/main/bison/cml.y"  */
    {
      LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5)))));
      List<PDeclaration> processDeclarations = (List<PDeclaration>)((yystack.valueAt (5-(2))));
      PAction action = (PAction)((yystack.valueAt (5-(4))));
      yyval = new AStateProcess(location,processDeclarations,action);
  };
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 514 of "src/main/bison/cml.y"  */
    {
      LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
      List<PDeclaration> processDeclarations = null;
      PAction action = (PAction)((yystack.valueAt (4-(3))));
      yyval = new AStateProcess(location,processDeclarations,action);
  };
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 521 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new ASequentialCompositionProcess(combineLexLocation(left.getLocation(),
							      right.getLocation()), 
					   left, 
					   right);
};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 530 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new AExternalChoiceProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 539 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new AInternalChoiceProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 548 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (5-(1))));
    PProcess right = (PProcess)((yystack.valueAt (5-(5))));
    yyval = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left,
					    (SChansetSetExp)((yystack.valueAt (5-(3)))),
					    right);
};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 558 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (7-(1))));
    PProcess right = (PProcess)((yystack.valueAt (7-(7))));
    yyval = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left,
					    (SChansetSetExp)((yystack.valueAt (7-(3)))),
					    (SChansetSetExp)((yystack.valueAt (7-(5)))),
					    right);
};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 569 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new ASynchronousParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left, 
					    right);
};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 578 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    yyval = new AInterleavingProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 587 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (4-(1))));
    PProcess right = (PProcess)((yystack.valueAt (4-(4))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    yyval = new AInterruptProcess(location, 
			      left, 
			      right);
};
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 597 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (5-(1))));
    PProcess right = (PProcess)((yystack.valueAt (5-(5))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    yyval = new ATimedInterruptProcess(location, 
				    left, 
				    (PExp)((yystack.valueAt (5-(3)))),
				    right);
};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 608 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PProcess right = (PProcess)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    yyval = new AUntimedTimeoutProcess(location, 
				   left, 
				   right);
};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 630 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    SChansetSetExp cse = (SChansetSetExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      cse.getLocation());
    yyval = new AHidingProcess(location, 
			    left, 
			    cse);
};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 640 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      exp.getLocation());
    yyval = new AStartDeadlineProcess(location, 
			     left, 
			     exp);
};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 650 of "src/main/bison/cml.y"  */
    {
    PProcess left = (PProcess)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),
					      exp.getLocation());
    yyval = new AEndDeadlineProcess(location, 
			   left, 
			   exp);
};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 664 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),(CmlLexeme)((yystack.valueAt (8-(8))))); 
    List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (8-(2))));
    LexNameToken identifier = extractLexNameToken((CmlLexeme)((yystack.valueAt (8-(4)))));
    yyval = new AInstantiationProcess(location, 
				   decls,
				   identifier,
				   (PExp)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 674 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4))))); 
    List<ASingleTypeDeclaration> decls = null;
    LexNameToken identifier = extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(1)))));
    yyval = new AInstantiationProcess(location, 
				   decls,
				   identifier,
				   (PExp)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 685 of "src/main/bison/cml.y"  */
    {
    LexNameToken identifier = extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AIdentifierProcess(identifier.getLocation(), 
				identifier);
};
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 691 of "src/main/bison/cml.y"  */
    {
    SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
    PProcess process = (PProcess)((yystack.valueAt (2-(1))));
    
    yyval = new AChannelRenamingProcess(combineLexLocation(process.getLocation(),
						       renameExpression.getLocation()), 
				    process, 
				    renameExpression);
};
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 744 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> processParagraphList = 
	  new Vector<PDeclaration>();
      processParagraphList.add((PDeclaration)((yystack.valueAt (1-(1)))));
      yyval = processParagraphList;
};
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 751 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> processParagraphList = (List<PDeclaration>)((yystack.valueAt (2-(1))));

    if (processParagraphList == null) 
	processParagraphList = new Vector<PDeclaration>();
	    
    processParagraphList.add((PDeclaration)((yystack.valueAt (2-(2)))));
    yyval = processParagraphList;
};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 764 of "src/main/bison/cml.y"  */
    {
     yyval = ((yystack.valueAt (1-(1))));
 };
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 768 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 775 of "src/main/bison/cml.y"  */
    {
    List<AActionDefinition> actionDefinitions = 
	(List<AActionDefinition>)((yystack.valueAt (2-(2))));
    LexLocation declLoc = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
					     extractLastLexLocation(actionDefinitions));
    yyval = new AActionDeclaration(declLoc, 
				NameScope.GLOBAL, 
				actionDefinitions);
  };
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 789 of "src/main/bison/cml.y"  */
    {
    List<AActionDefinition> actionDefs = 
	new Vector<AActionDefinition>();
    actionDefs.add((AActionDefinition)((yystack.valueAt (1-(1)))));
    yyval = actionDefs;
};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 796 of "src/main/bison/cml.y"  */
    {
    List<AActionDefinition> actionDefs = 
	(List<AActionDefinition>)((yystack.valueAt (2-(1))));
    actionDefs.add((AActionDefinition)((yystack.valueAt (2-(2)))));
    yyval = actionDefs;
};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 806 of "src/main/bison/cml.y"  */
    {
    Object[] pa = (Object[])((yystack.valueAt (3-(3))));
    List<ASingleTypeDeclaration> declarations = 
	(List<ASingleTypeDeclaration>)pa[0];
    PAction action = (PAction)pa[1];
    LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))),
						 action.getLocation());
    AActionDefinition actionDefinition = new AActionDefinition(defLocation, 
							       NameScope.GLOBAL, 
							       false, 
							       null, 
							       declarations, 
							       action);
    yyval = actionDefinition;
};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 825 of "src/main/bison/cml.y"  */
    {
    yyval = new Object[]{new Vector<ASingleTypeDeclaration>(),((yystack.valueAt (1-(1))))};
};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 829 of "src/main/bison/cml.y"  */
    {
    yyval = new Object[]{((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3))))};
};
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 836 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new ASkipAction(location);
};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 841 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AStopAction(location);
};
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 846 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AChaosAction(location);
};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 851 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new ADivAction(location);
};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 856 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
					      exp.getLocation());
    yyval = new AWaitAction(location,exp);
};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 865 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    PAction action = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(id.getLocation(),action.getLocation());
    yyval = new ACommunicationAction(location, id, null,action);
};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 872 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (4-(1)))));
    PAction action = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = combineLexLocation(id.getLocation(),action.getLocation());
    List<PCommunicationParameter> communicationParamters = (List<PCommunicationParameter>)((yystack.valueAt (4-(2))));
    yyval = new ACommunicationAction(location, id, 
				  communicationParamters,
				  action);
};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 883 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),action.getLocation());
    yyval = new AGuardedAction(location, (PExp)((yystack.valueAt (4-(2)))), action);
};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 889 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new ASequentialCompositionAction(location, left, right);
};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 896 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AExternalChoiceAction(location, left, right);
};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 903 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AInternalChoiceAction(location, left, right);
};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 910 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (4-(1))));
    PAction right = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AInterruptAction(location, left, right);
};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 917 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (5-(1))));
    PAction right = (PAction)((yystack.valueAt (5-(5))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new ATimedInterruptAction(location, left, right,(PExp)((yystack.valueAt (5-(3)))));
};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 924 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PAction right = (PAction)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AUntimedTimeoutAction(location, left, right);
};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 931 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (6-(1))));
    PAction right = (PAction)((yystack.valueAt (6-(5))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new ATimeoutAction(location, left, right, (PExp)((yystack.valueAt (6-(3)))));
};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 938 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    SChansetSetExp chansetExp = (SChansetSetExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),chansetExp.getLocation());
    yyval = new AHidingAction(location, left, chansetExp);
};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 945 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),exp.getLocation());
    yyval = new AStartDeadlineAction(location, left, exp);
};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 952 of "src/main/bison/cml.y"  */
    {
    PAction left = (PAction)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),exp.getLocation());
    yyval = new AEndDeadlineAction(location, left, exp);
};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 959 of "src/main/bison/cml.y"  */
    {
    SRenameChannelExp renameExpression = (SRenameChannelExp)((yystack.valueAt (2-(2))));
    PAction action = (PAction)((yystack.valueAt (2-(1))));
    
    yyval = new AChannelRenamingAction(combineLexLocation(action.getLocation(),
						       renameExpression.getLocation()), 
				    action, 
				    renameExpression);
};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 970 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 974 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 978 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 72:
  if (yyn == 72)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 988 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 992 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 996 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AIdentifierAction(location);  
};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1004 of "src/main/bison/cml.y"  */
    {
      List<PCommunicationParameter> comParamList = 
	  new Vector<PCommunicationParameter>();
      comParamList.add((PCommunicationParameter)((yystack.valueAt (1-(1)))));
      yyval = comParamList;
  };
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1011 of "src/main/bison/cml.y"  */
    {
    List<PCommunicationParameter> comParamList = 
	(List<PCommunicationParameter>)((yystack.valueAt (2-(2))));

    if (comParamList == null) 
	comParamList = new Vector<PCommunicationParameter>();
    
    comParamList.add((PCommunicationParameter)((yystack.valueAt (2-(1)))));
    yyval = comParamList;
};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1025 of "src/main/bison/cml.y"  */
    {
      PParameter parameter = (PParameter)((yystack.valueAt (2-(2))));
      LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
								   parameter.getLocation());
      yyval = new AReadCommunicationParameter(location, parameter, null);
  };
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1032 of "src/main/bison/cml.y"  */
    {
    PParameter parameter = (PParameter)((yystack.valueAt (4-(2))));
    PExp exp = (PExp)((yystack.valueAt (4-(4))));
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1))))),
								 exp.getLocation());
    yyval = new AReadCommunicationParameter(location, parameter, exp);
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1040 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
								 exp.getLocation());
    yyval = new AWriteCommunicationParameter(location, exp);
};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1047 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
								 exp.getLocation());
    yyval = new AReferenceCommunicationParameter(location, exp);
};
  break;
    

  case 81:
  if (yyn == 81)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1057 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AIdentifierParameter(id.getLocation(),id);
};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1062 of "src/main/bison/cml.y"  */
    {
    yyval = new ATupleParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
						(CmlLexeme)((yystack.valueAt (4-(4))))), 
			     (List<? extends PParameter>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 83:
  if (yyn == 83)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1068 of "src/main/bison/cml.y"  */
    {
    yyval = new ATupleParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
						(CmlLexeme)((yystack.valueAt (3-(3))))), 
			     null);
};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1074 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (4-(1)))));
    
    yyval = new ARecordParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
    						 (CmlLexeme)((yystack.valueAt (4-(4))))), 
    			      name, 
    			      (List<? extends PParameter>)((yystack.valueAt (4-(3)))));
};
  break;
    

  case 85:
  if (yyn == 85)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1083 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (3-(1)))));
    
    yyval = new ARecordParameter(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
    						 (CmlLexeme)((yystack.valueAt (3-(3))))), 
    			      name, 
    			      null);
};
  break;
    

  case 86:
  if (yyn == 86)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1095 of "src/main/bison/cml.y"  */
    {
    List<PParameter> parameters = new Vector<PParameter>();
    parameters.add((PParameter)((yystack.valueAt (1-(1)))));
    yyval = parameters;
};
  break;
    

  case 87:
  if (yyn == 87)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1101 of "src/main/bison/cml.y"  */
    {
    List<PParameter> parameters = (List<PParameter>)((yystack.valueAt (3-(3))));
    parameters.add((PParameter)((yystack.valueAt (3-(1)))));
    yyval = parameters;
};
  break;
    

  case 100:
  if (yyn == 100)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1136 of "src/main/bison/cml.y"  */
    {
    yyval = new ADeclarationInstantiatedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),
							       (CmlLexeme)((yystack.valueAt (8-(8))))), 
					    (List<? extends ASingleTypeDeclaration>)((yystack.valueAt (8-(2)))), 
					    (PAction)((yystack.valueAt (8-(4)))), 
					    (List<PExp>)((yystack.valueAt (8-(7)))));
};
  break;
    

  case 102:
  if (yyn == 102)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1160 of "src/main/bison/cml.y"  */
    {
     yyval = ((yystack.valueAt (1-(1))));
 };
  break;
    

  case 104:
  if (yyn == 104)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1168 of "src/main/bison/cml.y"  */
    {
    yyval = new AEnumerationRenameChannelExp(null, 
					  extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3))))), 
					  (List<? extends ARenamePair>)((yystack.valueAt (3-(2))))); 
};
  break;
    

  case 105:
  if (yyn == 105)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1177 of "src/main/bison/cml.y"  */
    {
    yyval = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5))))), 
					    (List<? extends ARenamePair>)((yystack.valueAt (5-(2)))), 
					    (List<? extends PMultipleBind>)((yystack.valueAt (5-(4)))), 
					    null);
};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1184 of "src/main/bison/cml.y"  */
    {
    yyval = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),(CmlLexeme)((yystack.valueAt (7-(7))))), 
					    (List<? extends ARenamePair>)((yystack.valueAt (7-(2)))), 
					    (List<? extends PMultipleBind>)((yystack.valueAt (7-(4)))), 
					    (PExp)((yystack.valueAt (7-(6)))));
};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1194 of "src/main/bison/cml.y"  */
    {
    List<ARenamePair> renamePairs = 
	new Vector<ARenamePair>();
    renamePairs.add((ARenamePair)((yystack.valueAt (1-(1)))));
    yyval = renamePairs;
};
  break;
    

  case 108:
  if (yyn == 108)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1201 of "src/main/bison/cml.y"  */
    {
    List<ARenamePair> renamePairs = (List<ARenamePair>)((yystack.valueAt (3-(3))));
    renamePairs.add((ARenamePair)((yystack.valueAt (3-(1)))));
    yyval = renamePairs;
};
  break;
    

  case 109:
  if (yyn == 109)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1210 of "src/main/bison/cml.y"  */
    {
    yyval = new ARenamePair(false, 
			 (AEventChannelExp)((yystack.valueAt (3-(1)))), 
			 (AEventChannelExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 110:
  if (yyn == 110)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1219 of "src/main/bison/cml.y"  */
    {
    LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    List<? extends PExp> dotExpression = null;
    yyval = new AEventChannelExp(id.getLocation(), 
			      id, 
			      dotExpression);
};
  break;
    

  case 111:
  if (yyn == 111)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1227 of "src/main/bison/cml.y"  */
    {
    LexNameToken id = extractLexNameToken((CmlLexeme)((yystack.valueAt (2-(1)))));
    List<? extends PExp> dotExpression = (List<? extends PExp>)((yystack.valueAt (2-(2)))) ;
    yyval = new AEventChannelExp(id.getLocation(), 
			      id, 
			      dotExpression);
};
  break;
    

  case 112:
  if (yyn == 112)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1238 of "src/main/bison/cml.y"  */
    {
    List<PExp> expTokens = new Vector<PExp>();
    expTokens.add((PExp)((yystack.valueAt (2-(2)))));
    yyval = expTokens;
};
  break;
    

  case 113:
  if (yyn == 113)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1244 of "src/main/bison/cml.y"  */
    {
    List<PExp> expTokens = (List<PExp>)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    expTokens.add(exp);
    yyval = expTokens;
};
  break;
    

  case 114:
  if (yyn == 114)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1256 of "src/main/bison/cml.y"  */
    {
     List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (2-(2))));
     LexLocation start = decls.get(0).getLocation();
     LexLocation end = decls.get(decls.size()-1).getLocation();
     LexLocation location = combineLexLocation(start,end);

     AChannelDefinition channelDefinition = 
	 new AChannelDefinition(location,null,null,null,decls);
     AChannelDeclaration channelDecl = new AChannelDeclaration(location,
							       NameScope.GLOBAL,
							       channelDefinition);
     yyval = channelDecl;
 };
  break;
    

  case 115:
  if (yyn == 115)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1273 of "src/main/bison/cml.y"  */
    {
      List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
      decls.add((AChannelNameDeclaration)((yystack.valueAt (1-(1)))));
      AChannelDefinition channelDefinition = new AChannelDefinition();
      yyval = decls;
  };
  break;
    

  case 116:
  if (yyn == 116)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1280 of "src/main/bison/cml.y"  */
    {
     List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)((yystack.valueAt (3-(3))));
     decls.add((AChannelNameDeclaration)((yystack.valueAt (3-(1)))));
     yyval = decls;
 };
  break;
    

  case 117:
  if (yyn == 117)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1289 of "src/main/bison/cml.y"  */
    {
      List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (1-(1))));

      LexLocation start = ids.get(0).getLocation();
      LexLocation end = ids.get(ids.size()-1).getLocation();
      LexLocation location = combineLexLocation(start,end);

      ASingleTypeDeclaration singleTypeDeclaration = new ASingleTypeDeclaration(location,NameScope.GLOBAL,ids,null);
            
      AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(location,NameScope.GLOBAL,singleTypeDeclaration);
      
      yyval = channelNameDecl;
  };
  break;
    

  case 118:
  if (yyn == 118)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1303 of "src/main/bison/cml.y"  */
    {
     ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)((yystack.valueAt (1-(1))));

     AChannelNameDeclaration channelNameDecl = 
       new AChannelNameDeclaration(singleTypeDeclaration.getLocation(),NameScope.GLOBAL,singleTypeDeclaration);
      
      yyval = channelNameDecl; 
 };
  break;
    

  case 119:
  if (yyn == 119)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1315 of "src/main/bison/cml.y"  */
    {
  List<ASingleTypeDeclaration> decls = new Vector<ASingleTypeDeclaration>();
  decls.add((ASingleTypeDeclaration)((yystack.valueAt (1-(1)))));
  yyval = decls;
};
  break;
    

  case 120:
  if (yyn == 120)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1321 of "src/main/bison/cml.y"  */
    {
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)((yystack.valueAt (3-(3))));
  decls.add((ASingleTypeDeclaration)((yystack.valueAt (3-(1)))));
  yyval = decls;
};
  break;
    

  case 121:
  if (yyn == 121)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1330 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
    ids.add(id);
    ASingleTypeDeclaration singleTypeDeclaration = 
      new ASingleTypeDeclaration(id.getLocation(),NameScope.GLOBAL,ids,(PType)((yystack.valueAt (3-(3)))));
    yyval = singleTypeDeclaration;
};
  break;
    

  case 122:
  if (yyn == 122)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1339 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)((yystack.valueAt (3-(3))));
    
    singleTypeDeclaration.getIdentifiers().add(id);
    yyval = singleTypeDeclaration;
};
  break;
    

  case 123:
  if (yyn == 123)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1352 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AChansetDeclaration(id.getLocation(), NameScope.GLOBAL, null);
};
  break;
    

  case 124:
  if (yyn == 124)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1357 of "src/main/bison/cml.y"  */
    {
    List<AChansetDefinition> defs = (List<AChansetDefinition>)((yystack.valueAt (2-(2))));
    
    yyval = new AChansetDeclaration(combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1))))),
						    extractLastLexLocation(defs)), 
				 NameScope.GLOBAL, 
				 defs);
};
  break;
    

  case 125:
  if (yyn == 125)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1369 of "src/main/bison/cml.y"  */
    {
    List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
    defs.add((AChansetDefinition)((yystack.valueAt (1-(1)))));
    yyval = defs;
};
  break;
    

  case 126:
  if (yyn == 126)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1375 of "src/main/bison/cml.y"  */
    {
    List<AChansetDefinition> defs = 
	(List<AChansetDefinition>)((yystack.valueAt (2-(2))));
    defs.add((AChansetDefinition)((yystack.valueAt (2-(1)))));
    yyval = defs;
};
  break;
    

  case 127:
  if (yyn == 127)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1386 of "src/main/bison/cml.y"  */
    {
    LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    SChansetSetBase chansetExp = (SChansetSetBase)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(idToken.getLocation(),
					      chansetExp.getLocation());
    yyval = new AChansetDefinition(location, 
				NameScope.GLOBAL, 
				false/*used_*/, 
				null,/*AAccessSpecifierAccessSpecifier access_*/
				idToken, 
				chansetExp);
};
  break;
    

  case 128:
  if (yyn == 128)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1403 of "src/main/bison/cml.y"  */
    {
   LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1)))));
   yyval = new AIdentifierChansetSetExp(idToken.getLocation(),idToken);
 };
  break;
    

  case 129:
  if (yyn == 129)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1408 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
    List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)((yystack.valueAt (3-(2))));
    yyval = new AEnumChansetSetExp(location,identifiers);
};
  break;
    

  case 130:
  if (yyn == 130)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1414 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
    List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)((yystack.valueAt (3-(2))));
    yyval = new AEnumChansetSetExp(location,identifiers);
};
  break;
    

  case 131:
  if (yyn == 131)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1420 of "src/main/bison/cml.y"  */
    {
    PExp left = (PExp)((yystack.valueAt (3-(1))));
    PExp right = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new ASetUnionBinaryExp(location,left, null, right);
};
  break;
    

  case 132:
  if (yyn == 132)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1427 of "src/main/bison/cml.y"  */
    {
    PExp left = (PExp)((yystack.valueAt (3-(1))));
    PExp right = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new ASetIntersectBinaryExp(location,left, null, right);
};
  break;
    

  case 133:
  if (yyn == 133)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1434 of "src/main/bison/cml.y"  */
    {
    PExp left = (PExp)((yystack.valueAt (3-(1))));
    PExp right = (PExp)((yystack.valueAt (3-(3))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));
    LexToken lexToken = new LexToken(opLocation,VDMToken.SETDIFF);
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new ASetDifferenceBinaryExp(location, left, lexToken, right);
};
  break;
    

  case 134:
  if (yyn == 134)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1443 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5)))));
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (5-(2)))));
    List<PExp> dotted_expression = new Vector<PExp>();
    List<PMultipleBind> bindings = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
    yyval = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,null);
};
  break;
    

  case 135:
  if (yyn == 135)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1451 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),(CmlLexeme)((yystack.valueAt (6-(6)))));
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (6-(2)))));
    List<PExp> dotted_expression = (List<PExp>)((yystack.valueAt (6-(3))));
    List<PMultipleBind> bindings = (List<PMultipleBind>)((yystack.valueAt (6-(5))));
    yyval = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,null);
};
  break;
    

  case 136:
  if (yyn == 136)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1459 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),(CmlLexeme)((yystack.valueAt (7-(7)))));
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (7-(2)))));
    List<PExp> dotted_expression = new Vector<PExp>();
    List<PMultipleBind> bindings = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
    PExp pred = (PExp)((yystack.valueAt (7-(6))));
    yyval = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,pred);
};
  break;
    

  case 137:
  if (yyn == 137)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1468 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (8-(1)))),(CmlLexeme)((yystack.valueAt (8-(8)))));
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (8-(2)))));
    List<PExp> dotted_expression = (List<PExp>)((yystack.valueAt (8-(3))));
    List<PMultipleBind> bindings = (List<PMultipleBind>)((yystack.valueAt (8-(5))));
    PExp pred = (PExp)((yystack.valueAt (8-(7))));
    yyval = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,pred);
};
  break;
    

  case 138:
  if (yyn == 138)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1482 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 139:
  if (yyn == 139)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1489 of "src/main/bison/cml.y"  */
    {
    List<PDeclaration> declBlockList = new Vector<PDeclaration>();
    PDeclaration globalDecl = (PDeclaration)((yystack.valueAt (1-(1))));
    if (globalDecl != null) declBlockList.add(globalDecl);
    yyval = declBlockList;
};
  break;
    

  case 140:
  if (yyn == 140)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1497 of "src/main/bison/cml.y"  */
    { 
    List<PDeclaration> declBlockList = (List<PDeclaration>)((yystack.valueAt (2-(1))));
    PDeclaration globalDecl = (PDeclaration)((yystack.valueAt (2-(2))));
    if (declBlockList != null) if (globalDecl != null) declBlockList.add(globalDecl);
    yyval = declBlockList;
};
  break;
    

  case 141:
  if (yyn == 141)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1507 of "src/main/bison/cml.y"  */
    {
  ATypeDeclaration typeDeclaration = (ATypeDeclaration)((yystack.valueAt (1-(1))));
  typeDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = typeDeclaration;
};
  break;
    

  case 142:
  if (yyn == 142)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1513 of "src/main/bison/cml.y"  */
    {
    AValueDeclaration valueGlobalDeclaration = new AValueDeclaration();
    yyval = valueGlobalDeclaration;
};
  break;
    

  case 143:
  if (yyn == 143)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1518 of "src/main/bison/cml.y"  */
    {
    AFunctionDeclaration functionGlobalDeclaration = (AFunctionDeclaration)((yystack.valueAt (1-(1))));
    functionGlobalDeclaration.setNameScope(NameScope.GLOBAL);
    yyval = functionGlobalDeclaration;
};
  break;
    

  case 144:
  if (yyn == 144)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1529 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 145:
  if (yyn == 145)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1547 of "src/main/bison/cml.y"  */
    {
  
  List<PDeclaration> decls = new LinkedList<PDeclaration>();
  PDeclaration decl = (PDeclaration)((yystack.valueAt (1-(1))));
  decls.add(decl);
  yyval = decls;
};
  break;
    

  case 146:
  if (yyn == 146)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1556 of "src/main/bison/cml.y"  */
    { 
  List<PDeclaration> decls = (List<PDeclaration>)((yystack.valueAt (2-(2))));
  PDeclaration decl = (PDeclaration)((yystack.valueAt (2-(1))));
  decls.add(decl);
  yyval = decls;
};
  break;
    

  case 147:
  if (yyn == 147)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1566 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 148:
  if (yyn == 148)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1570 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 149:
  if (yyn == 149)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1574 of "src/main/bison/cml.y"  */
    {
  AFunctionDeclaration functionDeclaration = (AFunctionDeclaration)((yystack.valueAt (1-(1))));
  functionDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = functionDeclaration;
};
  break;
    

  case 150:
  if (yyn == 150)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1580 of "src/main/bison/cml.y"  */
    {
  AOperationDeclaration operationDeclaration = (AOperationDeclaration)((yystack.valueAt (1-(1))));
  operationDeclaration.setNameScope(NameScope.GLOBAL);
  yyval = operationDeclaration;
};
  break;
    

  case 151:
  if (yyn == 151)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1586 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 152:
  if (yyn == 152)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1600 of "src/main/bison/cml.y"  */
    { 
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  LexLocation loc = extractLexLocation(typesLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(new Vector<ATypeDefinition>());
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 153:
  if (yyn == 153)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1609 of "src/main/bison/cml.y"  */
    {
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (3-(1))));
  CmlLexeme semiLexeme = (CmlLexeme)((yystack.valueAt (3-(3))));
  LexLocation loc = extractLexLocation(typesLexeme,semiLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions((List<ATypeDefinition>)((yystack.valueAt (3-(2)))));
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 154:
  if (yyn == 154)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1619 of "src/main/bison/cml.y"  */
    {
  CmlLexeme typesLexeme = (CmlLexeme)((yystack.valueAt (2-(1))));
  List<ATypeDefinition> tdefs = (List<ATypeDefinition>)((yystack.valueAt (2-(2))));
  LexLocation loc = extractLexLocation(typesLexeme,tdefs.get(tdefs.size()-1).getLocation());
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(tdefs);
  td.setLocation(loc);
  yyval = td;
};
  break;
    

  case 155:
  if (yyn == 155)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1632 of "src/main/bison/cml.y"  */
    {
    List<ATypeDefinition> list = (List<ATypeDefinition>)((yystack.valueAt (3-(1))));
    list.add((ATypeDefinition)((yystack.valueAt (3-(3)))));
    yyval = list;
};
  break;
    

  case 156:
  if (yyn == 156)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1638 of "src/main/bison/cml.y"  */
    {
    List<ATypeDefinition> list = new Vector<ATypeDefinition>(); 
    list.add((ATypeDefinition)((yystack.valueAt (1-(1)))));
    yyval = list;
};
  break;
    

  case 157:
  if (yyn == 157)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1647 of "src/main/bison/cml.y"  */
    {
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2)))));
    AInvariantInvariant inv = (AInvariantInvariant)((yystack.valueAt (5-(5))));
    LexLocation location = null;
    if (access.getLocation() != null)
	location = combineLexLocation(access.getLocation(),inv.getLocation());
    else
    {
	location = combineLexLocation(name.getLocation(),inv.getLocation());
    }
    
    

    yyval = new ATypeDefinition(location,null /*NameScope nameScope_*/, false, 
			     null/*SClassDefinition classDefinition_*/,access, 
			     (PType)((yystack.valueAt (5-(4)))),null,inv.getInvPattern(),inv.getInvExpression(), 
			     null, true, name); 
    
};
  break;
    

  case 158:
  if (yyn == 158)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1668 of "src/main/bison/cml.y"  */
    { 
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (4-(2)))));
    LexLocation location = null;
    if (access.getLocation() != null)
	location = combineLexLocation(access.getLocation(),((PType)((yystack.valueAt (4-(4))))).getLocation());
    else
    {
	location = combineLexLocation(name.getLocation(),((PType)((yystack.valueAt (4-(4))))).getLocation());
    }
        
    yyval = new ATypeDefinition(location,null /*NameScope nameScope_*/, false, 
			     null/*SClassDefinition classDefinition_*/,access, 
			     (PType)((yystack.valueAt (4-(4)))), null, null, null, 
			     null, true, name); 
};
  break;
    

  case 159:
  if (yyn == 159)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1685 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (4-(1))));
  LexNameToken name = extractLexNameToken( (CmlLexeme)((yystack.valueAt (4-(2)))) );
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (4-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (4-(4))));

  LexLocation loc = combineLexLocation( access.getLocation(),
					extractLexLocation( vdmrec ) );
					

  //
  ARecordInvariantType recType = new ARecordInvariantType( loc, false, null, false, null, name, fields, true );
							   

  ATypeDefinition res = new ATypeDefinition( loc, NameScope.GLOBAL, 
					     false, null, access,
					     recType, null, null, null,
					     null, true, name );

  yyval = res;
};
  break;
    

  case 160:
  if (yyn == 160)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1707 of "src/main/bison/cml.y"  */
    {
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (5-(1))));
  LexNameToken name = extractLexNameToken( (CmlLexeme)((yystack.valueAt (5-(2)))) );
  CmlLexeme vdmrec = (CmlLexeme)((yystack.valueAt (5-(3))));
  List<AFieldField> fields = (List<AFieldField>)((yystack.valueAt (5-(4))));
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.


  LexLocation loc = combineLexLocation( access.getLocation(),
					extractLexLocation( vdmrec ));
					

  //
  ARecordInvariantType recType = new ARecordInvariantType( loc, false, null, false, null, name, fields, true );
							   

  ATypeDefinition res = new ATypeDefinition( loc, NameScope.GLOBAL, 
					     false, null, access,
					     recType, null, null, null,
					     null, true, name );

  yyval = res;
};
  break;
    

  case 161:
  if (yyn == 161)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1734 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
    res.setAccess(new APrivateAccess());
    res.setLocation(location);
    yyval = res;
};
  break;
    

  case 162:
  if (yyn == 162)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1742 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
    res.setLocation(location);
    res.setAccess(new AProtectedAccess());
    yyval = res;
};
  break;
    

  case 163:
  if (yyn == 163)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1750 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
    res.setLocation(location);
    res.setAccess(new APublicAccess());
    yyval = res; 
};
  break;
    

  case 164:
  if (yyn == 164)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1758 of "src/main/bison/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(),null,null,location);
};
  break;
    

  case 165:
  if (yyn == 165)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1763 of "src/main/bison/cml.y"  */
    {
    /*Default private*/
  AAccessSpecifierAccessSpecifier a = new AAccessSpecifierAccessSpecifier();
  a.setAccess(new APrivateAccess());
  yyval = a;
};
  break;
    

  case 166:
  if (yyn == 166)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1773 of "src/main/bison/cml.y"  */
    { 
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 167:
  if (yyn == 167)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1777 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 168:
  if (yyn == 168)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1781 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 170:
  if (yyn == 170)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1786 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 171:
  if (yyn == 171)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1790 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 172:
  if (yyn == 172)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1794 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 173:
  if (yyn == 173)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1798 of "src/main/bison/cml.y"  */
    {
  // Get Constituents
  CmlLexeme setof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));

  LexLocation loc = combineLexLocation( extractLexLocation ( setof ),
					type.getLocation() );

  // Build ASetType
  ASetType res = new ASetType( loc, false, null, type, false, false );
  yyval = res;
};
  break;
    

  case 174:
  if (yyn == 174)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1811 of "src/main/bison/cml.y"  */
    {
  CmlLexeme seqof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation( extractLexLocation ( seqof ),
					type.getLocation() );

  // Build ASetType
  ASeqSeqType res = new ASeqSeqType( loc, false, null, type, false );
  yyval = res;
};
  break;
    

  case 175:
  if (yyn == 175)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1822 of "src/main/bison/cml.y"  */
    {
  CmlLexeme seqof = (CmlLexeme)((yystack.valueAt (2-(1))));
  PType type = (PType)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation( extractLexLocation ( seqof ),
					type.getLocation() );

  // Build ASetType
  ASeq1SeqType res = new ASeq1SeqType( loc, false, null, type, false );
  yyval = res;
};
  break;
    

  case 176:
  if (yyn == 176)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1833 of "src/main/bison/cml.y"  */
    {
  CmlLexeme mapof = (CmlLexeme)((yystack.valueAt (4-(1))));
  PType from = (PType)((yystack.valueAt (4-(2))));
  // $3 TO
  PType to   = (PType)((yystack.valueAt (4-(4))));
  
  LexLocation loc = combineLexLocation ( extractLexLocation ( mapof ),
					 to.getLocation() );

  // Build res
  AMapMapType res = new AMapMapType( loc, false, null, from, to, false );
  yyval = res;
};
  break;
    

  case 177:
  if (yyn == 177)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1847 of "src/main/bison/cml.y"  */
    {
  CmlLexeme mapof = (CmlLexeme)((yystack.valueAt (4-(1))));
  PType from = (PType)((yystack.valueAt (4-(2))));
  // $3 TO
  PType to   = (PType)((yystack.valueAt (4-(4))));
  
  LexLocation loc = combineLexLocation ( extractLexLocation ( mapof ),
					 to.getLocation() );


  // Build res
  AMapMapType res = new AMapMapType( loc, false, null, from, to, false );
  yyval = res;
};
  break;
    

  case 178:
  if (yyn == 178)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1862 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 179:
  if (yyn == 179)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1866 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = extractLexNameToken((ASimpleName)((yystack.valueAt (1-(1)))));
    yyval = new AUnresolvedType(lnt.location,false /*resolved*/, null/*defs*/,lnt);
};
  break;
    

  case 180:
  if (yyn == 180)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1871 of "src/main/bison/cml.y"  */
    {
  
};
  break;
    

  case 181:
  if (yyn == 181)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1878 of "src/main/bison/cml.y"  */
    { 
    yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 182:
  if (yyn == 182)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1885 of "src/main/bison/cml.y"  */
    { 
    yyval = new ABooleanBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 183:
  if (yyn == 183)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1889 of "src/main/bison/cml.y"  */
    { 
    yyval = new ANatNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 184:
  if (yyn == 184)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1893 of "src/main/bison/cml.y"  */
    { 
    yyval = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 185:
  if (yyn == 185)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1897 of "src/main/bison/cml.y"  */
    { 
    yyval = new AIntNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 186:
  if (yyn == 186)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1901 of "src/main/bison/cml.y"  */
    { 
    yyval = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 187:
  if (yyn == 187)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1905 of "src/main/bison/cml.y"  */
    { 
     yyval = new ARealNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 188:
  if (yyn == 188)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1909 of "src/main/bison/cml.y"  */
    { 
    yyval = new ACharBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 189:
  if (yyn == 189)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1913 of "src/main/bison/cml.y"  */
    { 
    yyval = new ATokenBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 190:
  if (yyn == 190)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1920 of "src/main/bison/cml.y"  */
    {
    LexQuoteToken value = (LexQuoteToken)((yystack.valueAt (1-(1))));
    yyval = new AQuoteType( value.location, false, null, value );
};
  break;
    

  case 191:
  if (yyn == 191)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1928 of "src/main/bison/cml.y"  */
    {
  yyval = new  AOptionalType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
					     (CmlLexeme)((yystack.valueAt (3-(3))))), 
			  false,/* resolved_*/ 
			  null,/* definitions_*/ 
			  (PType)((yystack.valueAt (3-(2)))));
};
  break;
    

  case 192:
  if (yyn == 192)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1939 of "src/main/bison/cml.y"  */
    {
  // CmlLexeme lp = (CmlLexeme)$1;
  // CmlLexeme rp = (CmlLexeme)$5;
  PType fst = (PType)((yystack.valueAt (3-(1))));
  PType snd = (PType)((yystack.valueAt (3-(3))));

  LexLocation loc = combineLexLocation ( fst.getLocation(),
  					 snd.getLocation() );

  AUnionType utype = new AUnionType(loc, false, false, false );
  List<PType> types = new Vector<PType>();
  types.add(fst);
  types.add(snd);
  utype.setTypes(types);
  yyval = utype;
};
  break;
    

  case 193:
  if (yyn == 193)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1956 of "src/main/bison/cml.y"  */
    {
    AUnionType utype = (AUnionType)((yystack.valueAt (3-(1))));
    utype.getTypes().add((PType)((yystack.valueAt (3-(3)))));
    yyval = utype;
};
  break;
    

  case 194:
  if (yyn == 194)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1965 of "src/main/bison/cml.y"  */
    {
    List<PType> types = new Vector<PType>();
    PType left = (PType)((yystack.valueAt (3-(1))));
    PType right = (PType)((yystack.valueAt (3-(3))));
    types.add(left);
    types.add(right);
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    yyval = new AProductType(location, 
			  false /*resolved_*/, 
			  null/*List<? extends PDefinition> definitions_*/, 
			  types);
};
  break;
    

  case 195:
  if (yyn == 195)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1987 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 196:
  if (yyn == 196)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1991 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1)))); 
};
  break;
    

  case 197:
  if (yyn == 197)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 1998 of "src/main/bison/cml.y"  */
    {
    PType domType = (PType)((yystack.valueAt (3-(1))));
    PType rngType = (PType)((yystack.valueAt (3-(3))));
    
    LexLocation loc = combineLexLocation ( domType.getLocation(),
					   rngType.getLocation() ) ;
    
    // [CONSIDER,RWL] The domain type of a function is not a list, 
    // I think the AST is wrong taking a list of types for params
    // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
    List<PType> params = new LinkedList<PType>();
    params.add(domType);
    yyval = new AFunctionType(loc, false, null, true, params, rngType );
};
  break;
    

  case 198:
  if (yyn == 198)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2013 of "src/main/bison/cml.y"  */
    {
    PType domType = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), 
				  true);
    PType rngType = (PType)((yystack.valueAt (3-(3))));
    
    LexLocation loc = combineLexLocation ( domType.getLocation(),
					   rngType.getLocation() ) ;
    
    // [CONSIDER,RWL] The domain type of a function is not a list, 
    // I think the AST is wrong taking a list of types for params
    // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
    List<PType> params = new LinkedList<PType>();
    params.add(domType);
    yyval = new AFunctionType(loc, false, null, true, params, rngType );
};
  break;
    

  case 199:
  if (yyn == 199)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2032 of "src/main/bison/cml.y"  */
    {
    PType domType = (PType)((yystack.valueAt (3-(1))));
    PType rngType = (PType)((yystack.valueAt (3-(3))));
    
    LexLocation loc = combineLexLocation ( domType.getLocation(),
					   rngType.getLocation() ) ;
    
    // [CONSIDER,RWL] The domain type of a function is not a list, 
    // I think the AST is wrong taking a list of types for params
    // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
    List<PType> params = new LinkedList<PType>();
    params.add(domType);
    yyval = new AFunctionType(loc, false, null, false, params, rngType );
};
  break;
    

  case 200:
  if (yyn == 200)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2047 of "src/main/bison/cml.y"  */
    {
    PType domType = new AVoidType(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1))))), 
				  true);
    PType rngType = (PType)((yystack.valueAt (3-(3))));
    
    LexLocation loc = combineLexLocation ( domType.getLocation(),
					   rngType.getLocation() ) ;
    
    // [CONSIDER,RWL] The domain type of a function is not a list, 
    // I think the AST is wrong taking a list of types for params
    // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
    List<PType> params = new LinkedList<PType>();
    params.add(domType);
    yyval = new AFunctionType(loc, false, null, false, params, rngType );
};
  break;
    

  case 201:
  if (yyn == 201)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2087 of "src/main/bison/cml.y"  */
    {
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (3-(2))));
  LexLocation loc = extractLexLocation ((CmlLexeme)((yystack.valueAt (3-(1)))),
					(CmlLexeme)((yystack.valueAt (3-(3)))));
  yyval = new LexQuoteToken(id.getValue(),loc);
};
  break;
    

  case 202:
  if (yyn == 202)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2097 of "src/main/bison/cml.y"  */
    {
    List<AFieldField> res = new LinkedList<AFieldField>();
    res.add ( (AFieldField) ((yystack.valueAt (1-(1)))) );
    yyval = res;
  };
  break;
    

  case 203:
  if (yyn == 203)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2103 of "src/main/bison/cml.y"  */
    {
  List<AFieldField> tail = (List<AFieldField>)((yystack.valueAt (2-(2))));
  tail.add( (AFieldField) ((yystack.valueAt (2-(1)))) );
  yyval = tail;
};
  break;
    

  case 204:
  if (yyn == 204)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2112 of "src/main/bison/cml.y"  */
    {
    yyval = new AFieldField( null, null, null, (PType) ((yystack.valueAt (1-(1)))), null );
  };
  break;
    

  case 205:
  if (yyn == 205)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2116 of "src/main/bison/cml.y"  */
    {
  LexNameToken name = extractLexNameToken( (CmlLexeme) ((yystack.valueAt (3-(1)))) );
  PType type = (PType) ((yystack.valueAt (3-(3))));

  yyval = new AFieldField( null, name, null, type, null );
};
  break;
    

  case 206:
  if (yyn == 206)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2123 of "src/main/bison/cml.y"  */
    {
  throw new RuntimeException("No way");
};
  break;
    

  case 207:
  if (yyn == 207)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2130 of "src/main/bison/cml.y"  */
    {
   CmlLexeme vdmInvLexeme = (CmlLexeme)((yystack.valueAt (4-(1))));
   PExp exp = (PExp)((yystack.valueAt (4-(4))));
   LexLocation loc = extractLexLocation(vdmInvLexeme,exp.getLocation());
   yyval = new AInvariantInvariant(loc,(PPattern)((yystack.valueAt (4-(2)))),exp);
 };
  break;
    

  case 208:
  if (yyn == 208)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2142 of "src/main/bison/cml.y"  */
    {
    List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (2-(2))));
    AValueDeclaration valueDecl = new AValueDeclaration();
    valueDecl.setDefinitions( defs );
    yyval = valueDecl;
  };
  break;
    

  case 209:
  if (yyn == 209)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2185 of "src/main/bison/cml.y"  */
    {
   // Build resulting list 
   List<PDefinition> defs = new LinkedList<PDefinition>();
   defs.add((PDefinition)((yystack.valueAt (1-(1)))));
   yyval = defs;
};
  break;
    

  case 210:
  if (yyn == 210)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2192 of "src/main/bison/cml.y"  */
    {
   // This case allows tailing SEMI in value def. list, comment out to
   // enforce no tailing SEMI.

   // Build resulting list 
   List<PDefinition> defs = new LinkedList<PDefinition>();
   defs.add((PDefinition)((yystack.valueAt (2-(1)))));
   yyval = defs;
 };
  break;
    

  case 211:
  if (yyn == 211)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2202 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  PDefinition def = (PDefinition)((yystack.valueAt (3-(1))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(3))));
  
  // add hd to tl
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 212:
  if (yyn == 212)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2214 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
  PDefinition def = (PDefinition)((yystack.valueAt (2-(2))));
  
  // set qualifier
  def.setAccess(access);
  yyval = def;
};
  break;
    

  case 213:
  if (yyn == 213)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2227 of "src/main/bison/cml.y"  */
    {
   // Get constituent elements
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (5-(1))));
  // $2 COLON
  PType type = (PType)((yystack.valueAt (5-(3))));
  // $4 EQUALS
  PExp expression = (PExp)((yystack.valueAt (5-(5))));
  
  // Make pattern
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (5-(1))));
  LexNameToken lnt = extractLexNameToken(lexeme);
  //   AIdentifierPattern idp = new AIdentifierPattern(lnt.location,null,false,lnt);
  AIdentifierPattern idp = new AIdentifierPattern();
  idp.setLocation(lnt.location);
  // Build the resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( idp.getLocation(), 
				       expression.getLocation() ) );
  yyval = vdef;
};
  break;
    

  case 214:
  if (yyn == 214)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2253 of "src/main/bison/cml.y"  */
    {
  // Get constituent elements
  PPattern pattern = (PPattern)((yystack.valueAt (5-(1))));
  // $2 COLON
  PType type = (PType)((yystack.valueAt (5-(3))));
  // $4 EQUALS
  PExp expression = (PExp)((yystack.valueAt (5-(5))));

  // Build resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( pattern.getLocation(), 
				       expression.getLocation() ) );
  yyval = vdef;
};
  break;
    

  case 215:
  if (yyn == 215)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2273 of "src/main/bison/cml.y"  */
    {
   // Get constituent elements
  CmlLexeme id = (CmlLexeme)((yystack.valueAt (3-(1))));
  // $4 EQUALS
  PExp expression = (PExp)((yystack.valueAt (3-(3))));
  
  // Make pattern
  CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (3-(1))));
  LexNameToken lnt = extractLexNameToken(lexeme);
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,null,false,lnt);
  
  // Build the resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( idp.getLocation(), 
				       expression.getLocation() ) );
  yyval = vdef;
};
  break;
    

  case 216:
  if (yyn == 216)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2296 of "src/main/bison/cml.y"  */
    {
  // Get constituent elements
  PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
  // $2 EQUALS
  PExp expression = (PExp)((yystack.valueAt (3-(3))));

  // Build resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( pattern.getLocation(), 
				       expression.getLocation() ) );
  yyval = vdef;
};
  break;
    

  case 217:
  if (yyn == 217)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2320 of "src/main/bison/cml.y"  */
    {
  CmlLexeme functionsLexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
  AFunctionDeclaration fdecl = new AFunctionDeclaration();
  fdecl.setLocation(extractLexLocation(functionsLexeme));
  yyval = fdecl;
};
  break;
    

  case 218:
  if (yyn == 218)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2327 of "src/main/bison/cml.y"  */
    {
  CmlLexeme functionsLexeme = (CmlLexeme)((yystack.valueAt (2-(1))));
  List<SFunctionDefinition> functionDefs = (List<SFunctionDefinition>) ((yystack.valueAt (2-(2))));
  AFunctionDeclaration fdecl = new AFunctionDeclaration();
  fdecl.setLocation(extractLexLocation(functionsLexeme,
				       functionDefs.get(functionDefs.size()-1).getLocation()));
  fdecl.setFunctionDefinitions(functionDefs);
  yyval = fdecl;
};
  break;
    

  case 219:
  if (yyn == 219)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2340 of "src/main/bison/cml.y"  */
    {
    List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
    functionList.add((SFunctionDefinition)((yystack.valueAt (1-(1)))));
    yyval = functionList;
};
  break;
    

  case 220:
  if (yyn == 220)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2346 of "src/main/bison/cml.y"  */
    {
    List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
    functionList.add((SFunctionDefinition)((yystack.valueAt (2-(1)))));
    yyval = functionList;
};
  break;
    

  case 221:
  if (yyn == 221)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2352 of "src/main/bison/cml.y"  */
    {
    List<SFunctionDefinition> functionList = (List<SFunctionDefinition>)((yystack.valueAt (3-(3))));
    functionList.add((SFunctionDefinition)((yystack.valueAt (3-(1)))));
    yyval = functionList;
};
  break;
    

  case 222:
  if (yyn == 222)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2361 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 223:
  if (yyn == 223)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2365 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 224:
  if (yyn == 224)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2372 of "src/main/bison/cml.y"  */
    {
  
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (6-(1))));
  LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (6-(2)))));

  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)((yystack.valueAt (6-(3))));
  List<APatternTypePair> result = (List<APatternTypePair>)((yystack.valueAt (6-(4))));  
  PExp preExp = (PExp)((yystack.valueAt (6-(5))));
  PExp postExp = (PExp)((yystack.valueAt (6-(6))));

  LexLocation location = null;
  if (access.getLocation() != null){
      location = combineLexLocation(access.getLocation(),postExp.getLocation());
  }
  else{
      location = combineLexLocation(name.getLocation(),postExp.getLocation());
  }
  AImplicitFunctionFunctionDefinition impFunc = 
      new AImplicitFunctionFunctionDefinition(location, null/*nameScope */, false/*used_*/, 
					      access, null /* typeParams*/, 
					      paramPatterns, result, preExp, postExp);
  impFunc.setName(name);
  yyval = impFunc;
};
  break;
    

  case 225:
  if (yyn == 225)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2400 of "src/main/bison/cml.y"  */
    {
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)((yystack.valueAt (2-(1))));
    AExplicitFunctionFunctionDefinition f = (AExplicitFunctionFunctionDefinition)((yystack.valueAt (2-(2))));
    f.setAccess(access);
    yyval = f;
  };
  break;
    

  case 226:
  if (yyn == 226)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2410 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = extractLexNameToken( (CmlLexeme) ((yystack.valueAt (10-(1)))) );
    LexLocation loc = extractLexLocation ( (CmlLexeme) ((yystack.valueAt (10-(1)))) );
    AFunctionType ftype = (AFunctionType)((yystack.valueAt (10-(3))));
    AExplicitFunctionFunctionDefinition res = new AExplicitFunctionFunctionDefinition();
    res.setName(name);
    res.setLocation(loc);
    res.setType(ftype);

/* loc,  */
    						 /* name,  */
    						 /* NameScope.GLOBAL,  */
    						 /* false /\* used_*\/,  */
    						 /* null /\*declaration_*\/,  */
    						 /* null/\* access_*\/,  */
    						 /* null/\*List<? extends LexNameToken> typeParams_*\/,  */
    						 /* (Collection<? extends List<PPattern>>)$5,  */
    						 /* ftype,  */
    						 /* (PExp)$7 /\*body_*\/,  */
    						 /* (PExp)$8 /\*precondition_*\/,  */
    						 /* (PExp)$9 /\*postcondition_*\/,  */
    						 /* (LexNameToken)$10 /\*measure_*\/,  */
    						 /* null/\*AExplicitFunctionFunctionDefinition predef_*\/,  */
    						 /* null/\*AExplicitFunctionFunctionDefinition postdef_*\/,  */
    						 /* null/\*PDefinition measureDef_*\/,  */
    						 /* null/\*List<? extends PDefinition> paramDefinitionList_*\/,  */
    						 /* false /\*Boolean recursive_*\/,  */
    						 /* false /\*isUndefined_*\/,  */
    						 /* null/\*measureLexical_*\/,  */
    						 /* ftype.getResult(),  */
    						 /* null /\* actualResult_*\/,  */
    						 /* false /\*isTypeInvariant_*\/,  */
    						 /* false /\*isCurried_*\/,  */
    						 /* false /\*typeInvariant_*\/); */
    yyval = res;
  };
  break;
    

  case 227:
  if (yyn == 227)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2452 of "src/main/bison/cml.y"  */
    {
    List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
    yyval = patternListList;
};
  break;
    

  case 228:
  if (yyn == 228)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2457 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(2))));
    List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
    patternListList.add(patternList);
    yyval = patternListList;
};
  break;
    

  case 229:
  if (yyn == 229)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2464 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (4-(2))));
    List<List<PPattern>> patternListList = (List<List<PPattern>>)((yystack.valueAt (4-(4))));
    patternListList.add(patternList);
    yyval = patternListList;
};
  break;
    

  case 230:
  if (yyn == 230)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2474 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 231:
  if (yyn == 231)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2478 of "src/main/bison/cml.y"  */
    {
    yyval = new ASubclassResponsibilityExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 232:
  if (yyn == 232)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2482 of "src/main/bison/cml.y"  */
    {
    yyval = new ANotYetSpecifiedExp(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 233:
  if (yyn == 233)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2489 of "src/main/bison/cml.y"  */
    {
    yyval = new Vector<APatternListTypePair>();
};
  break;
    

  case 234:
  if (yyn == 234)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2493 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 235:
  if (yyn == 235)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2500 of "src/main/bison/cml.y"  */
    {
    List<APatternListTypePair> pltpl = (List<APatternListTypePair>)((yystack.valueAt (5-(5))));
    List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (5-(1))));
    pltpl.add(new APatternListTypePair(false /*resolved*/, 
				       patternList, 
				       (PType)((yystack.valueAt (5-(3))))));
    yyval = pltpl;
};
  break;
    

  case 236:
  if (yyn == 236)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2509 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patternList = (List<PPattern>)((yystack.valueAt (3-(1))));
    List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
    pltpl.add(new APatternListTypePair(false /*resolved*/, 
				       patternList, 
				       (PType)((yystack.valueAt (3-(3))))));
    yyval = pltpl;
};
  break;
    

  case 237:
  if (yyn == 237)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2521 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 238:
  if (yyn == 238)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2525 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 239:
  if (yyn == 239)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2532 of "src/main/bison/cml.y"  */
    {
    AIdentifierTypePair typePair = 
	new AIdentifierTypePair(null /*resolved*/, 
				extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(1))))), 
				(PType)((yystack.valueAt (3-(3))))
				);
    List<AIdentifierTypePair> typePairs = new Vector<AIdentifierTypePair>();
    typePairs.add(typePair);
    yyval = typePairs;
};
  break;
    

  case 240:
  if (yyn == 240)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2543 of "src/main/bison/cml.y"  */
    {
    AIdentifierTypePair typePair = 
	new AIdentifierTypePair(null /*resolved*/, 
				extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (5-(1))))), 
				(PType)((yystack.valueAt (5-(3))))
				);
    List<AIdentifierTypePair> typePairs = (List<AIdentifierTypePair>)((yystack.valueAt (5-(5))));
    typePairs.add(typePair);
    yyval = typePairs;
};
  break;
    

  case 241:
  if (yyn == 241)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2557 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 242:
  if (yyn == 242)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2561 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 243:
  if (yyn == 243)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2568 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 244:
  if (yyn == 244)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2575 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 245:
  if (yyn == 245)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2579 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 246:
  if (yyn == 246)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2586 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 247:
  if (yyn == 247)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2593 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 249:
  if (yyn == 249)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2603 of "src/main/bison/cml.y"  */
    {
      List<? extends SOperationDefinition> opDefinitions = 
	  (List<? extends SOperationDefinition>)((yystack.valueAt (2-(2))));
      LexLocation lastInListLoc = 
	  opDefinitions.get(opDefinitions.size()-1).getLocation();
      LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
						lastInListLoc);
      yyval = new AOperationDeclaration(location, 
				     NameScope.GLOBAL,
				     opDefinitions);
  };
  break;
    

  case 250:
  if (yyn == 250)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2615 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new AOperationDeclaration(location, 
				   NameScope.GLOBAL,
				   null);
};
  break;
    

  case 251:
  if (yyn == 251)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2625 of "src/main/bison/cml.y"  */
    {
    List<SOperationDefinition> opDefinitions = 
	new Vector<SOperationDefinition>();
    opDefinitions.add((SOperationDefinition)((yystack.valueAt (1-(1)))));
    yyval = opDefinitions;
};
  break;
    

  case 252:
  if (yyn == 252)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2632 of "src/main/bison/cml.y"  */
    {
    List<SOperationDefinition> opDefinitions = 
	  (List<SOperationDefinition>)((yystack.valueAt (3-(3))));
    opDefinitions.add((SOperationDefinition)((yystack.valueAt (3-(1)))));
    yyval = opDefinitions;
};
  break;
    

  case 253:
  if (yyn == 253)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2644 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 254:
  if (yyn == 254)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2648 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 255:
  if (yyn == 255)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2655 of "src/main/bison/cml.y"  */
    {
   LexLocation loc = extractLexLocation ( (CmlLexeme)((yystack.valueAt (11-(2)))) );
   AExplicitOperationOperationDefinition res = new AExplicitOperationOperationDefinition();
   res.setLocation( loc );
   yyval = res;
 };
  break;
    

  case 256:
  if (yyn == 256)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2665 of "src/main/bison/cml.y"  */
    {
    AAccessSpecifierAccessSpecifier access = 
	(AAccessSpecifierAccessSpecifier)((yystack.valueAt (7-(1))));
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (7-(2)))));
    List<? extends APatternListTypePair> parameterPatterns = 
	(List<? extends APatternListTypePair>)((yystack.valueAt (7-(3)))); 
    List<? extends AIdentifierTypePair> result = 
	(List<? extends AIdentifierTypePair>)((yystack.valueAt (7-(4))));
    List<? extends AExternalClause> externals = 
	(List<? extends AExternalClause>)((yystack.valueAt (7-(5))));
    PExp precondition = (PExp)((yystack.valueAt (7-(6))));
    PExp postcondition = (PExp)((yystack.valueAt (7-(7))));
    LexLocation location = null;
    if (access != null)
	location = combineLexLocation(name.location,
				      postcondition.getLocation());
    else
	location = combineLexLocation(access.getLocation(),
				      postcondition.getLocation());
					      
    AImplicitOperationOperationDefinition ifunc = 
	new AImplicitOperationOperationDefinition(location, 
						  NameScope.GLOBAL, 
						  null /*used*/, 
						  access, 
						  parameterPatterns, 
						  result, 
						  externals, 
						  precondition, 
						  postcondition, 
						  null/*errors*/, 
						  null/*isConstructor_*/);
    yyval = ifunc;
};
  break;
    

  case 261:
  if (yyn == 261)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2710 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 262:
  if (yyn == 262)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2714 of "src/main/bison/cml.y"  */
    {
    yyval = new ASubclassResponsibilityAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))));
};
  break;
    

  case 263:
  if (yyn == 263)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2718 of "src/main/bison/cml.y"  */
    {
    yyval = new ANotYetSpecifiedAction(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))), 
				    null, 
				    null);
};
  break;
    

  case 264:
  if (yyn == 264)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2727 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 265:
  if (yyn == 265)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2731 of "src/main/bison/cml.y"  */
    {
    yyval = null;
};
  break;
    

  case 266:
  if (yyn == 266)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2738 of "src/main/bison/cml.y"  */
    {
      yyval = ((yystack.valueAt (2-(2))));
  };
  break;
    

  case 267:
  if (yyn == 267)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2745 of "src/main/bison/cml.y"  */
    {
      List<AExternalClause> infoList = 
	  new Vector<AExternalClause>();
      infoList.add((AExternalClause)((yystack.valueAt (1-(1)))));
      yyval = infoList;
  };
  break;
    

  case 268:
  if (yyn == 268)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2752 of "src/main/bison/cml.y"  */
    {
    List<AExternalClause> infoList = 
	(List<AExternalClause>)((yystack.valueAt (2-(2))));
    infoList.add((AExternalClause)((yystack.valueAt (2-(1)))));
    yyval = infoList;
};
  break;
    

  case 269:
  if (yyn == 269)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2762 of "src/main/bison/cml.y"  */
    {
      yyval = new AExternalClause((LexToken)((yystack.valueAt (2-(1)))), 
       			       (List<? extends LexNameToken>)((yystack.valueAt (2-(2)))));
  };
  break;
    

  case 270:
  if (yyn == 270)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2767 of "src/main/bison/cml.y"  */
    {
    yyval = new AExternalClause((LexToken)((yystack.valueAt (4-(1)))), 
     			     (List<? extends LexNameToken>)((yystack.valueAt (4-(2)))),
     			     (PType)((yystack.valueAt (4-(4)))));
};
  break;
    

  case 271:
  if (yyn == 271)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2776 of "src/main/bison/cml.y"  */
    {
    yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),
    					 VDMToken.READ); 
};
  break;
    

  case 272:
  if (yyn == 272)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2781 of "src/main/bison/cml.y"  */
    {
    yyval = new LexToken(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),
    					 VDMToken.WRITE); 
};
  break;
    

  case 273:
  if (yyn == 273)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2813 of "src/main/bison/cml.y"  */
    {
      
      // LexLocation lastInListLoc = 
      AStateDefinition stateDef = (AStateDefinition)((yystack.valueAt (2-(2))));
      // LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$1),
      // 					   stateDef.getLocation());
      yyval  = new AStateDeclaration(null,
				  NameScope.GLOBAL,
				  stateDef);
  };
  break;
    

  case 274:
  if (yyn == 274)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2824 of "src/main/bison/cml.y"  */
    {
      yyval  = new AStateDeclaration(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))),NameScope.GLOBAL,null);
  };
  break;
    

  case 275:
  if (yyn == 275)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2831 of "src/main/bison/cml.y"  */
    {
     AStateDefinition stateDef = new AStateDefinition();
     List<PDefinition> defs = new Vector<PDefinition>();
     defs.add((PDefinition)((yystack.valueAt (1-(1)))));
     stateDef.setStateDefs(defs);
     yyval = stateDef;
 };
  break;
    

  case 276:
  if (yyn == 276)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2839 of "src/main/bison/cml.y"  */
    {
     AStateDefinition stateDef = new AStateDefinition();
     List<PDefinition> defs = new Vector<PDefinition>();
     defs.add((PDefinition)((yystack.valueAt (2-(1)))));
     stateDef.setStateDefs(defs);
     yyval = stateDef;
 };
  break;
    

  case 277:
  if (yyn == 277)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2847 of "src/main/bison/cml.y"  */
    {
    AStateDefinition stateDef = (AStateDefinition)((yystack.valueAt (3-(3))));
    stateDef.getStateDefs().add((PDefinition)((yystack.valueAt (3-(1)))));
    yyval = stateDef;
};
  break;
    

  case 278:
  if (yyn == 278)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2856 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (2-(2))));
};
  break;
    

  case 279:
  if (yyn == 279)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2860 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 280:
  if (yyn == 280)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2867 of "src/main/bison/cml.y"  */
    {
     //  if (42 > 2) throw new RuntimeException("In expression");
     PExp exp = (PExp) ((yystack.valueAt (2-(2))));
     LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),exp.getLocation());
     yyval = new AClassInvariantDefinition(location, 
					NameScope.GLOBAL, 
					true, 
					null/*AAccessSpecifierAccessSpecifier access_*/,
					exp);
 };
  break;
    

  case 281:
  if (yyn == 281)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2883 of "src/main/bison/cml.y"  */
    {
    List<PExp> exps = new Vector<PExp>();
    exps.add((PExp)((yystack.valueAt (1-(1)))));
    yyval = exps;    
};
  break;
    

  case 282:
  if (yyn == 282)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2889 of "src/main/bison/cml.y"  */
    {
    List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(3))));
    exps.add((PExp)((yystack.valueAt (3-(1)))));
    yyval = exps;    
};
  break;
    

  case 283:
  if (yyn == 283)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2909 of "src/main/bison/cml.y"  */
    {
  // Get a whole STRING from the lexer  
  CmlLexeme s = (CmlLexeme)((yystack.valueAt (1-(1))));

  LexLocation sl = extractLexLocation ( s );
  
  // extract the string and convert it to a char array
  String str = s.getValue();
  char[] chrs = str.toCharArray();

  // build a list of ACharLiteralSymbolicLiteralExp from the lexer String
  List<PExp> members = new LinkedList<PExp>();
  for(int i = 0; i < chrs.length;i++)
    {
      LexLocation cl = new LexLocation(currentSourceFile.getFile(), "Default",
				       sl.startLine, sl.startPos + i,
				       sl.startLine, sl.startPos + (i + 1),0,0);
      members.add(new ACharLiteralSymbolicLiteralExp(cl, new LexCharacterToken( chrs[i], cl )) ); 
    }

  // Build the ASeqEnumSeqExp as usual
  ASeqEnumSeqExp res = new ASeqEnumSeqExp( sl, members );
  yyval = res;
};
  break;
    

  case 284:
  if (yyn == 284)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2935 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
      yyval = new ABracketedExp(loc,(PExp)((yystack.valueAt (3-(2)))));
  };
  break;
    

  case 285:
  if (yyn == 285)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2940 of "src/main/bison/cml.y"  */
    {
  List<PDefinition> l = (List<PDefinition>)((yystack.valueAt (4-(2))));
  PExp e = (PExp)((yystack.valueAt (4-(4))));
  LexLocation loc = extractLexLocation( (CmlLexeme) ((yystack.valueAt (4-(1)))), e.getLocation());
  yyval = new ALetDefExp( loc, l, e );
};
  break;
    

  case 286:
  if (yyn == 286)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2947 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 287:
  if (yyn == 287)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2951 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 288:
  if (yyn == 288)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2955 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 289:
  if (yyn == 289)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2959 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 290:
  if (yyn == 290)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2963 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 291:
  if (yyn == 291)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2967 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 292:
  if (yyn == 292)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2971 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 293:
  if (yyn == 293)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2975 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 294:
  if (yyn == 294)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2979 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 295:
  if (yyn == 295)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2983 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 296:
  if (yyn == 296)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2987 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 297:
  if (yyn == 297)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2991 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 298:
  if (yyn == 298)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2995 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 299:
  if (yyn == 299)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 2999 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 300:
  if (yyn == 300)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3003 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 301:
  if (yyn == 301)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3007 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 302:
  if (yyn == 302)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3011 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 303:
  if (yyn == 303)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3015 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 304:
  if (yyn == 304)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3019 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 305:
  if (yyn == 305)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3023 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 306:
  if (yyn == 306)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3027 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 307:
  if (yyn == 307)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3031 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 308:
  if (yyn == 308)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3035 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 309:
  if (yyn == 309)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3039 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (6-(1))));
};
  break;
    

  case 310:
  if (yyn == 310)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3044 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = extractLexNameToken((CmlLexeme)((yystack.valueAt (1-(1)))));
    yyval = new ANameExp(lnt.location,lnt);
};
  break;
    

  case 311:
  if (yyn == 311)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3049 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = ((LexNameToken)((yystack.valueAt (1-(1))))).getOldName();
    yyval = new ANameExp(lnt.location,lnt);
};
  break;
    

  case 313:
  if (yyn == 313)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3058 of "src/main/bison/cml.y"  */
    {
    LexIntegerToken lit = (LexIntegerToken)((yystack.valueAt (1-(1))));
    yyval = new AIntLiteralSymbolicLiteralExp(lit.location,lit);
};
  break;
    

  case 314:
  if (yyn == 314)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3069 of "src/main/bison/cml.y"  */
    {
    LexQuoteToken value = (LexQuoteToken)((yystack.valueAt (1-(1))));
    yyval = new AQuoteLiteralSymbolicLiteralExp(value.location, 
					 value);
};
  break;
    

  case 315:
  if (yyn == 315)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3078 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
    LexLocation loc = extractLexLocation(lexeme);
    yyval = new LexIntegerToken(Long.decode(lexeme.getValue()),loc);   
 };
  break;
    

  case 316:
  if (yyn == 316)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3084 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
    LexLocation loc = extractLexLocation(lexeme);
    yyval = new LexIntegerToken(Long.decode(lexeme.getValue()),loc);   
};
  break;
    

  case 317:
  if (yyn == 317)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3093 of "src/main/bison/cml.y"  */
    {
    List<PDefinition> res = new LinkedList<PDefinition>();
    res.add((PDefinition)((yystack.valueAt (1-(1)))));
    yyval = res;
  };
  break;
    

  case 318:
  if (yyn == 318)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3099 of "src/main/bison/cml.y"  */
    {
  PDefinition def = (PDefinition)((yystack.valueAt (3-(1))));
  List<PDefinition> defs = (List<PDefinition>)((yystack.valueAt (3-(3))));
  defs.add(def);
  yyval = defs;
};
  break;
    

  case 319:
  if (yyn == 319)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3109 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 320:
  if (yyn == 320)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3114 of "src/main/bison/cml.y"  */
    {
  yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 321:
  if (yyn == 321)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3123 of "src/main/bison/cml.y"  */
    {
    // Get constituents 
    // $1 IF
    CmlLexeme _if = (CmlLexeme)((yystack.valueAt (5-(1))));

    Position sif = _if.getStartPos();
    Position eif = _if.getEndPos();

    PExp test = (PExp)((yystack.valueAt (5-(2))));
    // $3 THEN
    PExp then = (PExp)((yystack.valueAt (5-(4))));
    List<AElseIfExp> elses = (List<AElseIfExp>)((yystack.valueAt (5-(5))));
    
    // Build an AIfExp ast node
    AIfExp ifexp = new AIfExp();
    ifexp.setTest(test);
    ifexp.setThen(then);
    ifexp.setElseList(elses);
    ifexp.setLocation( 
		      combineLexLocation( new LexLocation(null, 
							  "DEFAULT", 
							  sif.line, 
							  sif.column, 
							  sif.line, 
							  eif.column, 
							  sif.offset, 
							  eif.offset ),  
					  extractLastLexLocation( elses ) ));
    yyval = ifexp;
    
  };
  break;
    

  case 322:
  if (yyn == 322)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3158 of "src/main/bison/cml.y"  */
    {

    CmlLexeme elsetok = (CmlLexeme)((yystack.valueAt (2-(1))));
    PExp exp = (PExp)((yystack.valueAt (2-(2))));

    AElseIfExp eie = new AElseIfExp();
    eie.setLocation( 
		    combineLexLocation(  extractLexLocation( elsetok ), 
					 exp.getLocation() )
		     );
    List<AElseIfExp> res = new LinkedList<AElseIfExp>();
    res.add(eie);
    yyval =res;
  };
  break;
    

  case 323:
  if (yyn == 323)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3173 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  CmlLexeme elseif = (CmlLexeme)((yystack.valueAt (5-(1))));
  PExp elseIf = (PExp)((yystack.valueAt (5-(2))));
  // $3 THEN
  PExp then = (PExp)((yystack.valueAt (5-(4))));
  List<AElseIfExp> tail = (List<AElseIfExp>)((yystack.valueAt (5-(5))));
  
  LexLocation loc = combineLexLocation( extractLexLocation( elseif ),
					then.getLocation() );

  // Build result
  AElseIfExp eie = new AElseIfExp();
  eie.setElseIf(elseIf);
  eie.setThen(then);
  eie.setLocation( loc );
  tail.add(eie);
  yyval = tail;
};
  break;
    

  case 324:
  if (yyn == 324)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3196 of "src/main/bison/cml.y"  */
    {
    // Get Constituents
    CmlLexeme cases = (CmlLexeme)((yystack.valueAt (5-(1))));
    PExp exp = (PExp)((yystack.valueAt (5-(2))));
    // $3 COLON
    ACasesExp bubbleUp = (ACasesExp)((yystack.valueAt (5-(4)))); // Others and Cases are taken care of
    CmlLexeme end = (CmlLexeme)((yystack.valueAt (5-(5))));
    LexLocation lexLoc = combineLexLocation( extractLexLocation( cases ),
					     extractLexLocation( end ) );
					     
    // Set expression and location
    bubbleUp.setExpression(exp);
    bubbleUp.setLocation(lexLoc);
   
    yyval = bubbleUp;
  };
  break;
    

  case 325:
  if (yyn == 325)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3216 of "src/main/bison/cml.y"  */
    {
    // Get Constituent
    ACasesExp casesExp = new ACasesExp();

    // Set up a CasesExp and add this alternative to its list
    ACaseAlternative caseAlt = (ACaseAlternative)((yystack.valueAt (1-(1))));
    casesExp.getCases().add(caseAlt);
    yyval = casesExp;
  };
  break;
    

  case 326:
  if (yyn == 326)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3226 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  ACaseAlternative altExp = (ACaseAlternative)((yystack.valueAt (4-(1))));
  // $2 OTHERS
  // $3 RARROW
  PExp othExp = (PExp)((yystack.valueAt (4-(4))));

  // Build ACasesExp
  List<ACaseAlternative> altList = new LinkedList<ACaseAlternative>();
  altList.add(altExp);
  ACasesExp casesExp = new ACasesExp();
  casesExp.setCases(altList);
  casesExp.setOthers(othExp);

  yyval = casesExp;
};
  break;
    

  case 327:
  if (yyn == 327)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3243 of "src/main/bison/cml.y"  */
    {
  
  // Get constituents
  ACaseAlternative altExp = (ACaseAlternative)((yystack.valueAt (2-(1))));
  ACasesExp casesExp = (ACasesExp)((yystack.valueAt (2-(2))));

  // Add altExp to tail
  casesExp.getCases().add(altExp);
  yyval = casesExp;
};
  break;
    

  case 328:
  if (yyn == 328)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3257 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patList = (List<PPattern>)((yystack.valueAt (4-(1))));
    // $2 RARROW
    PExp exp = (PExp)((yystack.valueAt (4-(3))));
    CmlLexeme semi = (CmlLexeme)((yystack.valueAt (4-(4))));

    LexLocation leftMost = extractLexLeftMostFromPatterns( patList );
    LexLocation loc = combineLexLocation ( leftMost, 
					   extractLexLocation( semi )
					   );
    
    // Build res
    ACaseAlternative res = new ACaseAlternative();
    res.setPattern(patList);
    res.setLocation(loc);
    res.setCexp( exp );

    yyval = res;
  };
  break;
    

  case 329:
  if (yyn == 329)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3290 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    yyval = new AUnaryPlusUnaryExp(location,exp);
};
  break;
    

  case 330:
  if (yyn == 330)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3298 of "src/main/bison/cml.y"  */
    {
      PExp exp = (PExp)((yystack.valueAt (2-(2))));
      LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
      LexLocation location = 
	  combineLexLocation(opLocation,exp.getLocation());
      yyval = new AUnaryMinusUnaryExp(location,exp);
  };
  break;
    

  case 331:
  if (yyn == 331)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3306 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AAbsoluteUnaryExp(location,exp);
  };
  break;
    

  case 332:
  if (yyn == 332)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3314 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AFloorUnaryExp(location,exp);
  };
  break;
    

  case 333:
  if (yyn == 333)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3322 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ANotUnaryExp(location,exp);
  };
  break;
    

  case 334:
  if (yyn == 334)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3330 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ACardinalityUnaryExp(location,exp);
  };
  break;
    

  case 335:
  if (yyn == 335)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3338 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new APowerSetUnaryExp(location,exp);
  };
  break;
    

  case 336:
  if (yyn == 336)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3346 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ADistUnionUnaryExp(location,exp);
  };
  break;
    

  case 337:
  if (yyn == 337)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3354 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ADistIntersectUnaryExp(location,exp);
  };
  break;
    

  case 338:
  if (yyn == 338)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3362 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AHeadUnaryExp(location,exp);
  };
  break;
    

  case 339:
  if (yyn == 339)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3370 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ATailUnaryExp(location,exp);
  };
  break;
    

  case 340:
  if (yyn == 340)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3378 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ALenUnaryExp(location,exp);
  };
  break;
    

  case 341:
  if (yyn == 341)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3386 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AElementsUnaryExp(location,exp);
  };
  break;
    

  case 342:
  if (yyn == 342)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3394 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AIndicesUnaryExp(location,exp);
  };
  break;
    

  case 343:
  if (yyn == 343)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3402 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AReverseUnaryExp(location,exp);
  };
  break;
    

  case 344:
  if (yyn == 344)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3410 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    yyval = new ADistConcatUnaryExp(location,exp);
};
  break;
    

  case 345:
  if (yyn == 345)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3418 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    yyval = new AMapDomainUnaryExp(location,exp);
};
  break;
    

  case 346:
  if (yyn == 346)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3426 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AMapRangeUnaryExp(location,exp);
  };
  break;
    

  case 347:
  if (yyn == 347)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3434 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new ADistMergeUnaryExp(location,exp);
  };
  break;
    

  case 348:
  if (yyn == 348)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3442 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (2-(2))));
    LexLocation opLocation = extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))));
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    yyval = new AMapInverseUnaryExp(location,exp);
  };
  break;
    

  case 349:
  if (yyn == 349)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3459 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new APlusNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 350:
  if (yyn == 350)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3464 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ATimesNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));  
  };
  break;
    

  case 351:
  if (yyn == 351)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3469 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ASubstractNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 352:
  if (yyn == 352)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3474 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ADivideNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 353:
  if (yyn == 353)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3479 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ADivNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 354:
  if (yyn == 354)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3484 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new ARemNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 355:
  if (yyn == 355)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3489 of "src/main/bison/cml.y"  */
    {
      LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
      yyval = new AModNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
  };
  break;
    

  case 356:
  if (yyn == 356)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3494 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ALessNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 357:
  if (yyn == 357)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3499 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ALessEqualNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 358:
  if (yyn == 358)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3504 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AGreaterNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 359:
  if (yyn == 359)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3509 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AGreaterEqualNumericBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 360:
  if (yyn == 360)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3514 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AEqualsBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 361:
  if (yyn == 361)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3519 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ANotEqualBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 362:
  if (yyn == 362)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3524 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AOrBooleanBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 363:
  if (yyn == 363)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3529 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AAndBooleanBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 364:
  if (yyn == 364)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3534 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AImpliesBooleanBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 365:
  if (yyn == 365)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3539 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AEquivalentBooleanBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 366:
  if (yyn == 366)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3544 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AInSetBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 367:
  if (yyn == 367)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3549 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ANotInSetBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 368:
  if (yyn == 368)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3554 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASubsetBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 369:
  if (yyn == 369)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3559 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AProperSubsetBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 370:
  if (yyn == 370)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3564 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetUnionBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 371:
  if (yyn == 371)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3569 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetDifferenceBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 372:
  if (yyn == 372)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3574 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASetIntersectBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 373:
  if (yyn == 373)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3579 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ASeqConcatBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 374:
  if (yyn == 374)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3584 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 375:
  if (yyn == 375)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3589 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AMapUnionBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 376:
  if (yyn == 376)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3595 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new ADomainResToBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 377:
  if (yyn == 377)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3601 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 378:
  if (yyn == 378)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3607 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 379:
  if (yyn == 379)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3613 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 380:
  if (yyn == 380)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3619 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 381:
  if (yyn == 381)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3625 of "src/main/bison/cml.y"  */
    {
    LexLocation loc = combineLexLocation(((PExp)((yystack.valueAt (3-(1))))).getLocation(),((PExp)((yystack.valueAt (3-(3))))).getLocation());
    yyval = new AModifyBinaryExp(loc,(PExp)((yystack.valueAt (3-(1)))),null,(PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 382:
  if (yyn == 382)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3636 of "src/main/bison/cml.y"  */
    {
    // Get constituents
    CmlLexeme forall = (CmlLexeme)((yystack.valueAt (4-(1))));
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
    CmlLexeme amp = ( CmlLexeme)((yystack.valueAt (4-(3))));
    PExp exp = (PExp)((yystack.valueAt (4-(4))));
    
    LexLocation loc = combineLexLocation( extractLexLocation ( forall ),
					  exp.getLocation() );

    // Build forall expression
    AForAllExp forallexp = new AForAllExp( loc, binds, exp );
    yyval = forallexp;
    
  };
  break;
    

  case 383:
  if (yyn == 383)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3652 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (4-(2))));
  // CmlLexeme amp = (CmlLexeme)$3; AMP $3
  PExp exp = (PExp)((yystack.valueAt (4-(4))));

  LexLocation loc = combineLexLocation( extractLexLocation( exists ),
					exp.getLocation() );

  // Build exists expression
  AExistsExp existsExp = new AExistsExp( loc, binds, exp );
  yyval = existsExp;
};
  break;
    

  case 384:
  if (yyn == 384)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3667 of "src/main/bison/cml.y"  */
    {
  // Get constituents
  CmlLexeme exists = (CmlLexeme)((yystack.valueAt (4-(1))));
  PBind bind = (PBind)((yystack.valueAt (4-(2))));
  // CmlLexeme amp = (CmlLexeme)$3; AMP $3
  PExp exp = (PExp)((yystack.valueAt (4-(4))));

  LexLocation loc = combineLexLocation( extractLexLocation( exists ),
					exp.getLocation() );

  // Build exists expression
  AExists1Exp existsExp = new AExists1Exp( loc, bind, exp, null );
  yyval = existsExp;
};
  break;
    

  case 385:
  if (yyn == 385)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3687 of "src/main/bison/cml.y"  */
    {
  CmlLexeme lc = (CmlLexeme)((yystack.valueAt (2-(1))));
  CmlLexeme rc = (CmlLexeme)((yystack.valueAt (2-(2))));
  LexLocation loc = combineLexLocation ( extractLexLocation ( lc ),
					 extractLexLocation ( rc ) );
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation( loc );
  yyval = res;
};
  break;
    

  case 386:
  if (yyn == 386)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3697 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))), (CmlLexeme)((yystack.valueAt (3-(3)))));
    List<PExp> members = (List<PExp>)((yystack.valueAt (3-(2))));
    yyval = new ASetEnumSetExp(location,members);
};
  break;
    

  case 387:
  if (yyn == 387)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3706 of "src/main/bison/cml.y"  */
    {
    // Get Constituents
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (5-(1))));
    PExp exp = (PExp)((yystack.valueAt (5-(2))));
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
    CmlLexeme rcurle = (CmlLexeme)((yystack.valueAt (5-(5))));

    LexLocation loc = combineLexLocation( extractLexLocation( lcurly ), 
					  extractLexLocation( rcurle ) );

    // Build result
    ASetCompSetExp setComp = new ASetCompSetExp( loc, exp, binds, null );
    yyval = setComp;
    
  };
  break;
    

  case 388:
  if (yyn == 388)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3723 of "src/main/bison/cml.y"  */
    {
    // Get Constituents
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (7-(1))));
    PExp exp = (PExp)((yystack.valueAt (7-(2))));
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
    // $5 AMP
    PExp pred = (PExp)((yystack.valueAt (7-(6))));
    
    CmlLexeme rcurle = (CmlLexeme)((yystack.valueAt (7-(7))));

    LexLocation loc = combineLexLocation( extractLexLocation( lcurly ), 
					  extractLexLocation( rcurle ) );

    // Build result
    ASetCompSetExp setComp = new ASetCompSetExp( loc, exp, binds, pred );
    yyval = setComp;
};
  break;
    

  case 389:
  if (yyn == 389)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3745 of "src/main/bison/cml.y"  */
    {
    // Get constituents
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (7-(1))));
    PExp start = (PExp)((yystack.valueAt (7-(2))));
    // COMMA $3
    // ELLIPSIS $4
    // COMMA $5
    PExp end = (PExp)((yystack.valueAt (7-(6))));
    CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (7-(7))));

    LexLocation loc = combineLexLocation( extractLexLocation( lcurly ),
					  extractLexLocation( rcurly ) );

    // Build ASetRangeSetExpr
    ASetRangeSetExp res = new ASetRangeSetExp( loc, start, end );
    yyval = res;
  };
  break;
    

  case 390:
  if (yyn == 390)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3768 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (2-(1))));
    CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (2-(2))));
    List<PExp> exps = new LinkedList<PExp>();
    LexLocation loc = combineLexLocation( extractLexLocation( lsqr ),
					  extractLexLocation( rsqr ) );
    
    ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
    yyval = exp;
  };
  break;
    

  case 391:
  if (yyn == 391)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3779 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (3-(1))));
    List<PExp> exps = (List<PExp>)((yystack.valueAt (3-(2))));
    CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (3-(3))));
    
    LexLocation loc = combineLexLocation( extractLexLocation( lsqr ),
					  extractLexLocation( rsqr ) );
    
    ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
    yyval = exp;
};
  break;
    

  case 392:
  if (yyn == 392)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3795 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (5-(1))));
    PExp exp = (PExp)((yystack.valueAt (5-(2))));
    // BAR $3
    ASetBind binds = (ASetBind)((yystack.valueAt (5-(4))));
    CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (5-(5))));

    LexLocation loc = combineLexLocation ( extractLexLocation ( lsqr ),
					   extractLexLocation ( rsqr ) );


    // Build response
    ASeqCompSeqExp res = new ASeqCompSeqExp( loc, exp, binds, null );
    yyval = res;
  };
  break;
    

  case 393:
  if (yyn == 393)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3812 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lsqr = (CmlLexeme)((yystack.valueAt (7-(1))));
    PExp exp = (PExp)((yystack.valueAt (7-(2))));
    // BAR $3
    ASetBind binds = (ASetBind)((yystack.valueAt (7-(4))));
    // AMP $5
    PExp pred = (PExp)((yystack.valueAt (7-(6))));
    CmlLexeme rsqr = (CmlLexeme)((yystack.valueAt (7-(7))));

    LexLocation loc = combineLexLocation ( extractLexLocation ( lsqr ),
					   extractLexLocation ( rsqr ) );

    // Build response
    ASeqCompSeqExp res = new ASeqCompSeqExp( loc, exp, binds, pred );
    yyval = res;
};
  break;
    

  case 394:
  if (yyn == 394)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3832 of "src/main/bison/cml.y"  */
    {
    PExp seq = (PExp)((yystack.valueAt (8-(1))));
    // $2 LPAREN
    PExp from = (PExp)((yystack.valueAt (8-(3))));
    // $4 COMMA
    // $5 ELLIPSIS
    // $6 COMMA
    PExp to   = (PExp)((yystack.valueAt (8-(7))));
    CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (8-(8))));

    LexLocation loc = combineLexLocation(seq.getLocation(),
					 extractLexLocation( rparen ) );
    
    // Build result
    ASubseqExp res = new ASubseqExp( loc, seq, from , to );
    yyval = res;
  };
  break;
    

  case 395:
  if (yyn == 395)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3853 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (3-(1))));
    CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (3-(2))));

    LexLocation loc = combineLexLocation( extractLexLocation ( lcurly ),
					  extractLexLocation ( rcurly ) );

    AMapEnumMapExp res = new AMapEnumMapExp( loc, new LinkedList<AMapletExp>() );
    yyval = res;
  };
  break;
    

  case 396:
  if (yyn == 396)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3864 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lcurly = (CmlLexeme)((yystack.valueAt (3-(1))));
    List<AMapletExp> maplets = (List<AMapletExp>)((yystack.valueAt (3-(2))));
    CmlLexeme rcurly = (CmlLexeme)((yystack.valueAt (3-(3))));

    LexLocation loc = combineLexLocation( extractLexLocation ( lcurly ),
					  extractLexLocation ( rcurly ) );

    AMapEnumMapExp res = new AMapEnumMapExp( loc, maplets );
    yyval = res;
};
  break;
    

  case 397:
  if (yyn == 397)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3879 of "src/main/bison/cml.y"  */
    {
    List<AMapletExp> res = new LinkedList<AMapletExp>();
    res.add( (AMapletExp) ((yystack.valueAt (1-(1)))) );
    yyval = res;
  };
  break;
    

  case 398:
  if (yyn == 398)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3885 of "src/main/bison/cml.y"  */
    {
    AMapletExp hd = (AMapletExp)((yystack.valueAt (3-(1))));
    // $2 COMMA
    List<AMapletExp> tail = (List<AMapletExp>)((yystack.valueAt (3-(3))));
    
    tail.add(hd);
    yyval = tail;
  };
  break;
    

  case 399:
  if (yyn == 399)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3897 of "src/main/bison/cml.y"  */
    {
    PExp domValue = (PExp)((yystack.valueAt (3-(1))));
    // MAPLETARROW
    PExp rngValue = (PExp)((yystack.valueAt (3-(3))));

    LexLocation loc = combineLexLocation( domValue.getLocation(),
					  rngValue.getLocation() );

    AMapletExp res = new AMapletExp(loc, domValue, rngValue);
    yyval = res;
  };
  break;
    

  case 400:
  if (yyn == 400)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3912 of "src/main/bison/cml.y"  */
    {
    CmlLexeme lcurl = (CmlLexeme)((yystack.valueAt (5-(1))));
    AMapletExp maplet = (AMapletExp)((yystack.valueAt (5-(2))));
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (5-(4))));
    CmlLexeme rcurl = (CmlLexeme)((yystack.valueAt (5-(5))));

    LexLocation loc = combineLexLocation( extractLexLocation ( lcurl ),
					  extractLexLocation ( rcurl ) );

    AMapCompMapExp res = new AMapCompMapExp( loc, maplet, binds, null);
    yyval = res;

  };
  break;
    

  case 401:
  if (yyn == 401)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3927 of "src/main/bison/cml.y"  */
    {

    CmlLexeme lcurl = (CmlLexeme)((yystack.valueAt (7-(1))));
    AMapletExp maplet = (AMapletExp)((yystack.valueAt (7-(2))));
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (7-(4))));
    // $5 AMP
    PExp pred = (PExp)((yystack.valueAt (7-(6))));
    CmlLexeme rcurl = (CmlLexeme)((yystack.valueAt (7-(7))));

    LexLocation loc = combineLexLocation( extractLexLocation ( lcurl ),
					  extractLexLocation ( rcurl ) );


    AMapCompMapExp res = new AMapCompMapExp( loc, maplet, binds, pred);
    yyval = res;
};
  break;
    

  case 402:
  if (yyn == 402)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3950 of "src/main/bison/cml.y"  */
    {
    CmlLexeme mku = (CmlLexeme)((yystack.valueAt (4-(1))));
    // $2 LPAREN
    List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
    CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (4-(4))));

    LexLocation loc = combineLexLocation( extractLexLocation ( mku ),
					  extractLexLocation ( rparen ) );

    ATupleExp res = new ATupleExp( loc, exprs );
    yyval = res;
  };
  break;
    

  case 403:
  if (yyn == 403)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3968 of "src/main/bison/cml.y"  */
    {
      
    CmlLexeme mku = (CmlLexeme)((yystack.valueAt (4-(1))));
    LexNameToken name = getNameTokenFromMKUNDERNAME(mku);
    // // $3 LPAREN
    List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));

    LexLocation loc = combineLexLocation( extractLexLocation ( mku ) ,
     					  extractLexLocation ( (CmlLexeme)((yystack.valueAt (4-(4)))) ) );

    ARecordExp res = new ARecordExp(loc, name, exprs );
    yyval = res;
  };
  break;
    

  case 404:
  if (yyn == 404)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3987 of "src/main/bison/cml.y"  */
    {
      PExp root = (PExp)((yystack.valueAt (4-(1))));
      List<? extends PExp> args = (List<? extends PExp>)((yystack.valueAt (4-(3))));
      
      LexLocation location = combineLexLocation(root.getLocation(),
						extractLexLocation((CmlLexeme)((yystack.valueAt (4-(4))))));
      yyval = new AApplyExp(location, root, args);
  };
  break;
    

  case 405:
  if (yyn == 405)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 3996 of "src/main/bison/cml.y"  */
    {
      PExp root = (PExp)((yystack.valueAt (3-(1))));
      List<? extends PExp> args = null;
      
      LexLocation location = combineLexLocation(root.getLocation(),
						extractLexLocation((CmlLexeme)((yystack.valueAt (3-(3))))));
      yyval = new AApplyExp(location, root, args);
  };
  break;
    

  case 406:
  if (yyn == 406)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4008 of "src/main/bison/cml.y"  */
    {
    // PExp obj = (PExp)$1;
    // // $2 DOT

    // PExp res = null;

    // CmlLexeme id = (CmlLexeme)$3;
    // LexNameToken name = extractLexNameToken( id );
    // LexLocation loc = combineLexLocation ( obj.getLocation(),
    // 					   extractLexLocation( id ) );
    // if (obj instanceof ANameExp){
	
    // 	LexNameToken prefixName = (LexNameToken)obj;
	
    // 	res = new LexNameToken(prefixName.getSimpleName(), name.getIdentifier());
	 
    // }
    // else{
    // 	res = new AFieldExp( loc, obj, name, null );
    // }
    // $$ = res;
  };
  break;
    

  case 407:
  if (yyn == 407)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4034 of "src/main/bison/cml.y"  */
    {
    PExp tup = (PExp)((yystack.valueAt (3-(1))));
    // $2 DOTHASH
    CmlLexeme lexNum = (CmlLexeme)((yystack.valueAt (3-(3))));

    int num = Integer.parseInt(lexNum.getValue());

    LexLocation loc = combineLexLocation ( tup.getLocation(),
					   extractLexLocation ( lexNum ) );

    ATupleSelectExp res = new ATupleSelectExp( loc, tup, num );
    yyval = res;
  };
  break;
    

  case 408:
  if (yyn == 408)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4053 of "src/main/bison/cml.y"  */
    {
    CmlLexeme l = (CmlLexeme)((yystack.valueAt (4-(1))));
    List<ATypeBind> binds = (List<ATypeBind>)((yystack.valueAt (4-(2))));
    // AMP $3
    PExp body = (PExp)((yystack.valueAt (4-(4))));

    LexLocation loc = combineLexLocation ( extractLexLocation ( l ),
					   body.getLocation() );

    ALambdaExp res = new ALambdaExp( loc, binds, body, null, null );
    yyval = res;
  };
  break;
    

  case 409:
  if (yyn == 409)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4075 of "src/main/bison/cml.y"  */
    {
  CmlLexeme wen = (CmlLexeme)((yystack.valueAt (5-(1))));
  CmlLexeme lexId  = (CmlLexeme)((yystack.valueAt (5-(2))));
  // $3 LPAREN
  List<PExp> exprs = (List<PExp>)((yystack.valueAt (5-(4))));
  CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (5-(5))));

  LexLocation loc = combineLexLocation( extractLexLocation ( wen ),
					extractLexLocation ( rparen ) );

  LexIdentifierToken id = extractLexIdentifierToken ( lexId );

  ANewExp res = new ANewExp( loc, id, exprs );
};
  break;
    

  case 410:
  if (yyn == 410)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4095 of "src/main/bison/cml.y"  */
    {
    CmlLexeme isUnder = (CmlLexeme)((yystack.valueAt (5-(1))));
    LexNameToken typeName = (LexNameToken)((yystack.valueAt (5-(2))));
    // // $3 LPAREN
    PExp test = null;//(PExp)$4;
    // CmlLexeme rparen = (CmlLexeme)$5;
    
    LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					   typeName.getLocation() );
    
    AIsExp res = new AIsExp( loc,typeName, test, null );
    yyval = res;
    
};
  break;
    

  case 411:
  if (yyn == 411)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4110 of "src/main/bison/cml.y"  */
    {
   CmlLexeme isUnder = (CmlLexeme)((yystack.valueAt (5-(1))));
   PType type = (PType)((yystack.valueAt (5-(2))));
   // LPAREN $3
   PExp test = (PExp)((yystack.valueAt (5-(4))));
   CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (5-(5))));

   LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					  extractLexLocation ( rparen ) );

   AIsExp res = new AIsExp( loc, null, test, null );
   res.setBasicType( type );
   yyval = res;

};
  break;
    

  case 412:
  if (yyn == 412)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4126 of "src/main/bison/cml.y"  */
    {
   CmlLexeme isUnder = (CmlLexeme)((yystack.valueAt (6-(1))));
   // LPAREN $2
   PExp test = (PExp)((yystack.valueAt (6-(3))));
   // COMMA $4
   PType type = (PType)((yystack.valueAt (6-(5))));
   CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (6-(6))));

   LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					  extractLexLocation ( rparen ) );


   AIsExp res = new AIsExp( loc, null, test, null);
   res.setBasicType( type );
   yyval = res;
};
  break;
    

  case 413:
  if (yyn == 413)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4147 of "src/main/bison/cml.y"  */
    {
    CmlLexeme preu = (CmlLexeme)((yystack.valueAt (4-(1))));
    // LPAREN $2
    List<PExp> exprs = (List<PExp>)((yystack.valueAt (4-(3))));
    CmlLexeme rparen = (CmlLexeme)((yystack.valueAt (4-(4))));

    // RWL FIXME: Either this is right because we dedeuce the
    // function in a later phase where we know more or
    // the production above should be PREUNDER exp LPAREN expList RPAREN
    // however that introduces 36 reduce/reduce conflicts at this time.

    PExp function = null;
    LexLocation loc = combineLexLocation( extractLexLocation ( preu ),
					 extractLexLocation ( rparen ) );

    APreExp res = new APreExp( loc, function, exprs );
    yyval = res;
  };
  break;
    

  case 414:
  if (yyn == 414)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4181 of "src/main/bison/cml.y"  */
    {
    List<LexIdentifierToken> ids = 
	new Vector<LexIdentifierToken>();
    
    ids.add(extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (1-(1))))));
    yyval = new ASimpleName(ids);
};
  break;
    

  case 415:
  if (yyn == 415)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4189 of "src/main/bison/cml.y"  */
    {
    ASimpleName sname = (ASimpleName)((yystack.valueAt (3-(1))));
    
    sname.getIdentifiers().add(extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
    yyval = sname;
};
  break;
    

  case 416:
  if (yyn == 416)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4199 of "src/main/bison/cml.y"  */
    {
    yyval = new ANameDesignator((ASimpleName)((yystack.valueAt (1-(1)))));
};
  break;
    

  case 417:
  if (yyn == 417)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4203 of "src/main/bison/cml.y"  */
    {
    yyval = new APrimaryDesignator((PPrimary)((yystack.valueAt (1-(1)))));
};
  break;
    

  case 418:
  if (yyn == 418)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4210 of "src/main/bison/cml.y"  */
    {
    yyval = new ASelfPrimary();
};
  break;
    

  case 419:
  if (yyn == 419)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4214 of "src/main/bison/cml.y"  */
    {
    yyval = new ACallPrimary((ACallCallStatementControlStatementAction)((yystack.valueAt (1-(1)))));
};
  break;
    

  case 420:
  if (yyn == 420)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4218 of "src/main/bison/cml.y"  */
    {
    yyval = new APrimaryPrimary((PPrimary)((yystack.valueAt (3-(1)))), 
			     extractLexIdentifierToken((CmlLexeme)((yystack.valueAt (3-(3))))));
};
  break;
    

  case 421:
  if (yyn == 421)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4251 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = extractLexNameToken((ASimpleName)((yystack.valueAt (1-(1)))));
    List<LexNameToken> identifiers = 
	new Vector<LexNameToken>();
    identifiers.add(lnt);
    yyval = identifiers;
};
  break;
    

  case 422:
  if (yyn == 422)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4259 of "src/main/bison/cml.y"  */
    {
    LexNameToken lnt = extractLexNameToken((ASimpleName)((yystack.valueAt (3-(1)))));
    List<LexNameToken> identifiers = 
	(List<LexNameToken>)((yystack.valueAt (3-(3))));
    identifiers.add(lnt);
    yyval = identifiers;
};
  break;
    

  case 423:
  if (yyn == 423)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4270 of "src/main/bison/cml.y"  */
    {
    yyval = extractLexNameToken((CmlLexeme)((yystack.valueAt (2-(1)))));
  };
  break;
    

  case 424:
  if (yyn == 424)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4279 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 425:
  if (yyn == 425)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4283 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 427:
  if (yyn == 427)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4289 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 428:
  if (yyn == 428)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4293 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 430:
  if (yyn == 430)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4298 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 431:
  if (yyn == 431)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4311 of "src/main/bison/cml.y"  */
    {
     yyval = new ANonDeterministicIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),
									   (CmlLexeme)((yystack.valueAt (5-(5))))), 
							(PExp)((yystack.valueAt (5-(2)))), 
							(PAction)((yystack.valueAt (5-(4)))), 
							null);
 };
  break;
    

  case 432:
  if (yyn == 432)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4319 of "src/main/bison/cml.y"  */
    {
     yyval = new ANonDeterministicIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
									   (CmlLexeme)((yystack.valueAt (6-(5))))), 
							(PExp)((yystack.valueAt (6-(2)))), 
							(PAction)((yystack.valueAt (6-(4)))), 
							(List<ANonDeterministicElseIfControlStatementAction>)((yystack.valueAt (6-(5)))));
 };
  break;
    

  case 433:
  if (yyn == 433)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4330 of "src/main/bison/cml.y"  */
    {
    PAction thenStm = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),
					      thenStm.getLocation());
    yyval = new ANonDeterministicElseIfControlStatementAction(location, 
							   (PExp)((yystack.valueAt (4-(2)))), 
							   thenStm);
};
  break;
    

  case 434:
  if (yyn == 434)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4342 of "src/main/bison/cml.y"  */
    {
    List<ANonDeterministicElseIfControlStatementAction> alts =
	new Vector<ANonDeterministicElseIfControlStatementAction>();
    alts.add((ANonDeterministicElseIfControlStatementAction)((yystack.valueAt (1-(1)))));
    yyval = alts;
};
  break;
    

  case 435:
  if (yyn == 435)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4349 of "src/main/bison/cml.y"  */
    {
    List<ANonDeterministicElseIfControlStatementAction> alts =
	(List<ANonDeterministicElseIfControlStatementAction>)((yystack.valueAt (2-(1))));
    alts.add((ANonDeterministicElseIfControlStatementAction)((yystack.valueAt (2-(2)))));
    yyval = alts;
};
  break;
    

  case 436:
  if (yyn == 436)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4364 of "src/main/bison/cml.y"  */
    {
      LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3)))));
      PAction action = (PAction)((yystack.valueAt (3-(2))));
      yyval = new ABlockAction(location, 
			    null, 
			    action);
  };
  break;
    

  case 437:
  if (yyn == 437)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4372 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4)))));
    ADeclareStatementDeclareStatement dclStm = (ADeclareStatementDeclareStatement)((yystack.valueAt (4-(2))));
    PAction action = (PAction)((yystack.valueAt (4-(3))));
    yyval = new ABlockAction(location, 
			  dclStm, 
			  action);
};
  break;
    

  case 438:
  if (yyn == 438)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4384 of "src/main/bison/cml.y"  */
    {
    yyval = new ADeclareStatementDeclareStatement(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),(CmlLexeme)((yystack.valueAt (3-(3))))), 
					       (List<? extends PDefinition>) ((yystack.valueAt (3-(2)))));
};
  break;
    

  case 439:
  if (yyn == 439)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4392 of "src/main/bison/cml.y"  */
    {
    List<AAssignmentDefinition> assignmentDefs = 
	new Vector<AAssignmentDefinition>();
    assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (1-(1)))));
    yyval = assignmentDefs; 
};
  break;
    

  case 440:
  if (yyn == 440)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4399 of "src/main/bison/cml.y"  */
    {
    List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)((yystack.valueAt (3-(3))));
    
    if (assignmentDefs == null) 
	assignmentDefs = new Vector<AAssignmentDefinition>();
    
    assignmentDefs.add((AAssignmentDefinition)((yystack.valueAt (3-(1)))));
    yyval = assignmentDefs;
};
  break;
    

  case 441:
  if (yyn == 441)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4412 of "src/main/bison/cml.y"  */
    {
      LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
      PType type = (PType)((yystack.valueAt (3-(3))));
      LexLocation location = combineLexLocation(name.location,type.getLocation());
      AAccessSpecifierAccessSpecifier access = null;
      yyval = new AAssignmentDefinition(location, 
				     name, 
				     NameScope.LOCAL, 
				     false /*Boolean used_*/, 
				     null /*PDeclaration declaration_*/, 
				     access, 
				     type, 
				     null /*PExp expression_*/, 
				     null /*PType expType_*/);
  };
  break;
    

  case 444:
  if (yyn == 444)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4433 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 446:
  if (yyn == 446)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4441 of "src/main/bison/cml.y"  */
    {
    PStateDesignator stateDesignator = convertToStateDesignator((PDesignator)((yystack.valueAt (3-(1)))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = combineLexLocation(stateDesignator.getLocation(),
					      exp.getLocation());
    yyval = new ASingleGeneralAssignmentControlStatementAction(location, 
							    stateDesignator, 
							    (PExp)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 450:
  if (yyn == 450)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4464 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (7-(7))));
    yyval = new AIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (7-(1)))),
							  action.getLocation()),
				       (PExp)((yystack.valueAt (7-(2)))), 
				       (PAction)((yystack.valueAt (7-(4)))), 
				       (List<? extends AElseIfControlStatementAction>)((yystack.valueAt (7-(5)))), 
				       action);
};
  break;
    

  case 451:
  if (yyn == 451)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4474 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (6-(6))));
    yyval = new AIfControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (6-(1)))),
							  action.getLocation()),
				       (PExp)((yystack.valueAt (6-(2)))), 
				       (PAction)((yystack.valueAt (6-(4)))), 
				       null,
				       action);
};
  break;
    

  case 452:
  if (yyn == 452)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4487 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (5-(4))));
    List<AElseIfControlStatementAction> elseStms = 
	(List<AElseIfControlStatementAction>)((yystack.valueAt (5-(5))));
        
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1))))),
					      extractLastLexLocation(elseStms));
    elseStms.add(new AElseIfControlStatementAction(location, 
						   (PExp)((yystack.valueAt (5-(2)))), 
						   action));
    yyval = elseStms;
};
  break;
    

  case 453:
  if (yyn == 453)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4500 of "src/main/bison/cml.y"  */
    {
    List<AElseIfControlStatementAction> elseStms = 
	new Vector<AElseIfControlStatementAction>();

    PAction thenStm = (PAction)((yystack.valueAt (4-(4))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),thenStm.getLocation());
    elseStms.add(new AElseIfControlStatementAction(location, 
						   (PExp)((yystack.valueAt (4-(2)))), 
						   thenStm));
    yyval = elseStms;
};
  break;
    

  case 454:
  if (yyn == 454)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4524 of "src/main/bison/cml.y"  */
    {
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (5-(1)))),(CmlLexeme)((yystack.valueAt (5-(5)))));
    ACasesControlStatementAction cases = (ACasesControlStatementAction)((yystack.valueAt (5-(4))));
    cases.setLocation(location);
    cases.setExp((PExp)((yystack.valueAt (5-(2)))));
    yyval = cases;
};
  break;
    

  case 455:
  if (yyn == 455)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4535 of "src/main/bison/cml.y"  */
    {
    List<ACaseAlternativeAction> casesList = new Vector<ACaseAlternativeAction>();
    casesList.add((ACaseAlternativeAction)((yystack.valueAt (1-(1)))));
    
    ACasesControlStatementAction cases = 
	new ACasesControlStatementAction(null, 
					 null, 
					 casesList, 
					 null);
    yyval = cases;
    
};
  break;
    

  case 456:
  if (yyn == 456)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4548 of "src/main/bison/cml.y"  */
    {
    List<ACaseAlternativeAction> casesList = new Vector<ACaseAlternativeAction>();
    casesList.add((ACaseAlternativeAction)((yystack.valueAt (5-(1)))));
    
    ACasesControlStatementAction cases = 
	new ACasesControlStatementAction(null, 
					 null, 
					 casesList, 
					 (PAction)((yystack.valueAt (5-(5)))));
    yyval = cases;
};
  break;
    

  case 457:
  if (yyn == 457)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4560 of "src/main/bison/cml.y"  */
    {
    ACasesControlStatementAction cases = (ACasesControlStatementAction)((yystack.valueAt (3-(3))));
    cases.getCases().add((ACaseAlternativeAction)((yystack.valueAt (3-(1)))));
    yyval = cases;
};
  break;
    

  case 458:
  if (yyn == 458)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4569 of "src/main/bison/cml.y"  */
    {
    PAction action = (PAction)((yystack.valueAt (3-(3))));
    List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
    
    yyval = new ACaseAlternativeAction(combineLexLocation(extractFirstLexLocation(patterns),
						       action.getLocation()), 
				    patterns, 
				    (PAction)((yystack.valueAt (3-(3)))));
};
  break;
    

  case 459:
  if (yyn == 459)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4586 of "src/main/bison/cml.y"  */
    {
     yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 460:
  if (yyn == 460)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4590 of "src/main/bison/cml.y"  */
    {
    ACallCallStatementControlStatementAction call = 
	(ACallCallStatementControlStatementAction)((yystack.valueAt (3-(3))));
    PStateDesignator designator = convertToStateDesignator((PDesignator)((yystack.valueAt (3-(1)))));
    LexLocation location = combineLexLocation(designator.getLocation(),call.getLocation());
    yyval = new AAssignmentCallCallStatementControlStatementAction(location,
								designator, 
								call);
};
  break;
    

  case 461:
  if (yyn == 461)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4603 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = extractLexNameToken((ASimpleName)((yystack.valueAt (4-(1)))));
    LexLocation location = extractLexLocation(name.getLocation(),
					      (CmlLexeme)((yystack.valueAt (4-(4)))));
    List<PExp> args = (List<PExp>)((yystack.valueAt (4-(3))));
    yyval = new ACallCallStatementControlStatementAction(location, 
    						      null, 
    						      name,  
    						      args);
};
  break;
    

  case 462:
  if (yyn == 462)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4614 of "src/main/bison/cml.y"  */
    {
    LexNameToken name = extractLexNameToken((ASimpleName)((yystack.valueAt (3-(1)))));
    LexLocation location = extractLexLocation(name.getLocation(),(CmlLexeme)((yystack.valueAt (3-(3)))));
    
    yyval = new ACallCallStatementControlStatementAction(location, 
    						      null, 
    						      name,  
    						      null);
};
  break;
    

  case 463:
  if (yyn == 463)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4624 of "src/main/bison/cml.y"  */
    {
//    PObjectDesignator designator = null;//(PObjectDesignator)$1;
 //   LexLocation location = extractLexLocation(designator.getLocation(),
//    					      (CmlLexeme)$6);
//    LexNameToken name = extractLexNameToken((CmlLexeme)$3);
//    List<PExp> args = (List<PExp>)$5;
 //   $$ = new ACallCallStatementControlStatementAction(location, 
   // 						      designator, 
    //						      name,  
    	//					      args);
    						      yyval = null;
};
  break;
    

  case 464:
  if (yyn == 464)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4637 of "src/main/bison/cml.y"  */
    {
 //   PObjectDesignator designator = null;//(PObjectDesignator)$1;
 //   LexLocation location = extractLexLocation(designator.getLocation(),
  //  					      (CmlLexeme)$5);
   // LexNameToken name = extractLexNameToken((CmlLexeme)$3);
   // List<PExp> args = null;
   // $$ = new ACallCallStatementControlStatementAction(location, 
    //						      designator, 
    	//					      name,  
    		//				      args);
    		yyval = null;
};
  break;
    

  case 466:
  if (yyn == 466)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4739 of "src/main/bison/cml.y"  */
    {
       PExp exp = (PExp)((yystack.valueAt (2-(2))));
       yyval = new AReturnControlStatementAction(extractLexLocation((CmlLexeme)((yystack.valueAt (2-(1)))),
								 exp.getLocation()), 
					      exp);
   };
  break;
    

  case 474:
  if (yyn == 474)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4778 of "src/main/bison/cml.y"  */
    {
      List<PPattern> patterns = new Vector<PPattern>();
      patterns.add((PPattern)((yystack.valueAt (1-(1)))));
      yyval = patterns;
  };
  break;
    

  case 475:
  if (yyn == 475)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4784 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patterns = (Vector<PPattern>)((yystack.valueAt (3-(3))));
    patterns.add((PPattern)((yystack.valueAt (3-(1)))));
    yyval = patterns;
};
  break;
    

  case 476:
  if (yyn == 476)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4793 of "src/main/bison/cml.y"  */
    {
      CmlLexeme lexeme = (CmlLexeme)((yystack.valueAt (1-(1))));
      LexNameToken lnt = extractLexNameToken(lexeme);
      AIdentifierPattern res = new AIdentifierPattern();
      res.setName(lnt);
      res.setLocation(lnt.getLocation());
      yyval = res;
  };
  break;
    

  case 478:
  if (yyn == 478)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4807 of "src/main/bison/cml.y"  */
    {
    PExp exp = (PExp)((yystack.valueAt (1-(1))));
    if (exp instanceof AIntLiteralSymbolicLiteralExp)
      {
	AIntLiteralSymbolicLiteralExp intExp = (AIntLiteralSymbolicLiteralExp)exp;
	AIntegerPattern res = new AIntegerPattern();
	res.setLocation(intExp.getLocation());
	res.setValue(intExp.getValue());
	yyval = res;
      }
    else
      throw new RuntimeException("Unhandled expression type in pattern. ("+exp.getClass()+")"); // TODO RWL
  };
  break;
    

  case 479:
  if (yyn == 479)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4821 of "src/main/bison/cml.y"  */
    {
    
};
  break;
    

  case 481:
  if (yyn == 481)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4834 of "src/main/bison/cml.y"  */
    {
      List<? extends PPattern> plist = null;
      LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (3-(1)))));
      yyval = new ARecordPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),
						 (CmlLexeme)((yystack.valueAt (3-(3))))), 
			      null, 
			      false, 
			      name, 
			      plist);
  };
  break;
    

  case 482:
  if (yyn == 482)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4845 of "src/main/bison/cml.y"  */
    {
    List<? extends PPattern> plist = (List<? extends PPattern>)((yystack.valueAt (4-(3))));
    LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)((yystack.valueAt (4-(1)))));
    yyval = new ARecordPattern(extractLexLocation((CmlLexeme)((yystack.valueAt (4-(1)))),(CmlLexeme)((yystack.valueAt (4-(4))))), 
			    null, 
			    false, 
			    name, 
			    plist);

};
  break;
    

  case 483:
  if (yyn == 483)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4868 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
  };
  break;
    

  case 484:
  if (yyn == 484)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4872 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
  };
  break;
    

  case 485:
  if (yyn == 485)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4879 of "src/main/bison/cml.y"  */
    {
    PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));
    yyval = new ASetBind(location, pattern, exp);
};
  break;
    

  case 486:
  if (yyn == 486)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4889 of "src/main/bison/cml.y"  */
    {
    PPattern pattern = (PPattern)((yystack.valueAt (3-(1))));
    PType type = (PType)((yystack.valueAt (3-(3))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));
    yyval = new ATypeBind(location, pattern, type);
};
  break;
    

  case 487:
  if (yyn == 487)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4899 of "src/main/bison/cml.y"  */
    {
    List<PMultipleBind> binds = new Vector<PMultipleBind>();
    binds.add((PMultipleBind)((yystack.valueAt (1-(1)))));
    yyval = binds;
};
  break;
    

  case 488:
  if (yyn == 488)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4905 of "src/main/bison/cml.y"  */
    {
    List<PMultipleBind> binds = (List<PMultipleBind>)((yystack.valueAt (3-(1))));
    binds.add((PMultipleBind)((yystack.valueAt (3-(3)))));
    yyval = binds;
};
  break;
    

  case 489:
  if (yyn == 489)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4914 of "src/main/bison/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
  };
  break;
    

  case 491:
  if (yyn == 491)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4922 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
    PExp exp = (PExp)((yystack.valueAt (3-(3))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));;
    yyval = new ASetMultipleBind(location, patterns, exp);
};
  break;
    

  case 492:
  if (yyn == 492)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4932 of "src/main/bison/cml.y"  */
    {
    List<PPattern> patterns = (List<PPattern>)((yystack.valueAt (3-(1))));
    PType type = (PType)((yystack.valueAt (3-(3))));
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(2)))));;
    yyval = new ATypeMultipleBind(location, patterns, type);
};
  break;
    

  case 493:
  if (yyn == 493)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4942 of "src/main/bison/cml.y"  */
    {
    ATypeBind tb = (ATypeBind)((yystack.valueAt (1-(1))));
    List<ATypeBind> res = new LinkedList<ATypeBind>();
    res.add(tb);
    yyval = res;
  };
  break;
    

  case 494:
  if (yyn == 494)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4949 of "src/main/bison/cml.y"  */
    {
      ATypeBind hd = (ATypeBind)((yystack.valueAt (3-(1))));
      List<ATypeBind> tl = (List<ATypeBind>)((yystack.valueAt (3-(3))));
      tl.add(hd);
      yyval = tl;
    };
  break;
    

  case 495:
  if (yyn == 495)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4961 of "src/main/bison/cml.y"  */
    {
      CmlLexeme cmlLex = (CmlLexeme) ((yystack.valueAt (1-(1))));
      LexLocation location = extractLexLocation(cmlLex);
      LexIdentifierToken lexIdToken = new LexIdentifierToken(cmlLex.getValue(),false,location);
      List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
      ids.add(lexIdToken);
      yyval = ids;
  };
  break;
    

  case 496:
  if (yyn == 496)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 4971 of "src/main/bison/cml.y"  */
    {

    List<LexIdentifierToken> ids = (List<LexIdentifierToken>)((yystack.valueAt (3-(3))));

    if (ids == null)
	ids = new Vector<LexIdentifierToken>();

    CmlLexeme cmlLex = (CmlLexeme) ((yystack.valueAt (3-(1))));
    LexLocation location = extractLexLocation(cmlLex);
    LexIdentifierToken lexIdToken = 
	new LexIdentifierToken(cmlLex.getValue(),false,location);
    ids.add(lexIdToken);
    yyval = ids;
};
  break;
    



/* Line 360 of cmlskeleton.java.m4  */
/* Line 7501 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -832;
  public static final short yypact_[] =
  {
       710,   -97,    -7,    31,    81,   246,   547,   246,   191,   484,
    -832,  -832,  -832,  -832,  -832,   484,   408,  -832,  -832,  -832,
    -832,   192,   231,   -74,  -832,   229,  -832,  -832,   313,  -832,
      81,  -832,  -832,  -832,  -832,   290,  -832,   249,   263,  -832,
     291,   256,  -832,   337,  -832,  -832,  -832,  -832,   484,  -832,
     372,   392,    31,   872,    31,    -1,  -832,   246,   194,  5635,
     380,   384,  -832,    15,   292,  -832,  -832,  -832,  -832,  -832,
      20,  -832,  -832,  -832,   539,    25,  -832,   246,   612,  -832,
     697,   377,   381,   382,   386,   301,   403,    32,  -832,   863,
     414,   400,  -832,  -832,   872,   872,   332,   872,   872,   872,
     872,   872,   225,  -832,  -832,  -832,  -832,  -832,  -832,  -832,
    -832,  -832,  -832,   517,  -832,  -832,  -832,  -832,  -832,  -832,
    -832,  -832,  -832,   354,  -832,   338,  -832,   346,   497,  -832,
     872,  1055,  5635,  4998,  4816,   706,  5635,  5635,  5635,  5635,
    5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,
    5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,   465,   468,
     830,   374,  -832,    -9,   475,   480,   389,   830,   830,   830,
    -832,  3179,  -832,  -832,  -832,  -832,  -832,  -832,  -832,  -832,
    -832,  -832,  -832,  -832,  -832,  -832,  -832,  -832,  -832,  -832,
    -832,  -832,  -832,  -832,  -832,  -832,   830,   641,  5635,   872,
     394,  5635,   872,  -832,   663,   872,   391,  -832,   246,   128,
     506,   612,  -832,  -832,  -832,  -832,  -832,  2348,   -91,   784,
    -832,  -832,  -832,   301,   301,   301,   301,    74,   518,    -1,
    5635,   301,   519,   519,   519,   519,    -1,   397,   519,  2210,
      -1,   519,    -1,  5635,  5635,  -832,  -832,  -832,   519,   301,
     462,   261,   425,   492,   648,   666,   517,   495,   495,   872,
     872,   872,   872,   872,   422,   383,   510,   -14,   387,    -1,
      -1,    -1,   607,   -20,   517,   513,  1055,  3250,  -832,   525,
    2472,  -832,   543,   553,  2401,   554,     6,    21,  -832,  -832,
     501,   428,  3390,  2010,  4735,  4735,  4735,  4735,  4735,  4735,
    4735,  4735,  4735,  4735,  4735,  4735,  4735,  4735,  4735,  4735,
    4735,  4735,  4735,  4735,  5635,  5635,  -832,  -832,   426,  -832,
    -832,   432,   439,   579,  5635,   584,    62,  5635,   486,  -832,
     445,   -67,   137,  -832,  -832,  -832,   209,   -58,   455,  -832,
    -832,  5635,  5089,  -832,  5635,  5635,  5635,   499,   524,  5635,
    5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,
    5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,
    5635,  5635,  5635,  5635,  5635,  5635,  5635,  5635,   600,  -832,
     608,  4735,   258,  -832,  4735,   271,  -832,   623,   488,   517,
     534,   490,   615,   548,  -832,   628,  -832,  -832,  5635,   560,
    -832,   632,  -832,  -832,  -832,  2168,   616,  -832,  -832,  -832,
    -832,  5635,  5635,  5635,  -832,   677,  5180,   203,  5635,  1214,
    -832,   682,  -832,    88,   176,   601,  -832,  -832,  -832,  -832,
    -832,  -832,  -832,  -832,  -832,   212,  -832,  -832,   696,   587,
     595,  -832,  2348,  -832,   711,   713,   714,   715,   609,   174,
    3461,   723,  -832,  -832,  -832,  -832,   365,   561,    33,   588,
     719,  -832,   519,  1657,   497,  -832,   441,  4735,  4735,   863,
    -832,  -832,   872,  -832,   872,  -832,  1055,   872,   872,   495,
     495,   495,  -832,   495,  -832,   338,  -832,   830,  5635,   -10,
    -832,  -832,  -832,  -832,   830,  -832,   872,   872,  -832,  -832,
    -832,  -832,   830,  5635,  -832,  -832,   830,  5635,  5271,  -832,
     830,  5635,   872,  5635,   706,  5635,   830,   731,  2543,   732,
     872,   830,  5635,  5635,  2614,  5635,  5635,   733,   -69,   830,
    5635,   872,  5635,   830,  5635,  5635,  5635,   737,  -832,   740,
    2685,   737,   737,   737,  -832,  -832,   737,   737,   737,   737,
     737,   737,   737,   737,   737,   737,   737,   737,   737,   737,
     737,   737,   737,   737,   737,   737,   737,   737,   737,   737,
     737,   737,   737,   737,   737,  -832,  -832,  5635,  5635,  -832,
     872,   738,   872,  5635,   709,  -832,    72,   547,  4735,   604,
    -832,   128,   560,   122,   301,   301,   301,  1247,   768,   745,
     780,  2348,   -28,   615,  -832,   771,  4735,  2968,  2081,   190,
    -832,  4735,  2348,   464,  5635,  5635,   769,    75,  2827,  -832,
    2348,  2348,  2348,  2348,   -12,   605,  2348,  2308,    -1,  2348,
    5635,  5635,  5635,  -832,  5635,  5362,   190,  5635,   685,  2911,
     792,  -832,  1271,   519,   519,   519,   519,   788,   301,  -832,
     519,   638,   830,  -832,   397,   397,  -832,   519,    -1,   517,
     517,   799,   495,   495,     2,  4735,   830,  5635,   756,   517,
     517,   681,    34,  -832,   111,  4735,   652,   113,  3532,  -832,
     653,   737,  -832,  3108,   812,   917,   790,  -832,  -832,   517,
    -832,  4735,   800,   872,  3603,  3674,  -832,  5635,  -832,  4735,
     517,  4735,  -832,  4735,  4735,  4735,  -832,  5453,  4735,  4735,
      39,  1178,   766,   234,  4735,  5635,  -832,  1070,   391,  -832,
     872,  -832,   816,   664,  -832,  -832,  -832,  -832,  2348,   493,
    2348,  1345,  -832,  -832,  -832,   -28,   486,   709,  -832,  2348,
    2348,   830,   717,   804,   807,  -832,  -832,   819,   826,  -832,
     680,  4735,  4735,  2348,  -832,  2348,  -832,  -832,  -832,  -832,
     817,   494,   824,  -832,  2348,  1797,   497,  -832,  3745,  4735,
    4735,   840,  -832,   842,    88,   601,   763,  4735,   846,  -832,
    1459,   857,   673,  -832,  1482,  1515,  1538,  1572,   850,   861,
    -832,     7,  -832,  -832,  -832,   124,  -832,  5635,  -832,     3,
    4735,  5635,  -832,  5635,  -832,  5635,  5635,  -832,  5635,  5635,
    5635,  -832,  -832,   844,  -832,  5635,  -832,   341,  -832,  -832,
    3816,   712,   830,  -832,   851,  4907,   391,  4735,   503,   254,
     760,   616,  -832,   454,  -832,   560,  1383,  -832,  1440,  -832,
    -832,   -68,   721,  -832,  1162,  1139,   874,   726,   852,   190,
    -832,   359,   363,  5635,  -832,  -832,    -6,  2348,   689,  -832,
    2348,   764,  -832,  -832,  5544,  2348,  -832,  -832,  -832,  -832,
    -832,  5635,   519,  5635,  -832,   519,  1868,  5635,  -832,  4735,
    3887,  3958,  4029,  4100,  4240,  4735,  5635,  4311,  -832,  -832,
    5635,  -832,   738,  -832,  -832,   615,  4735,  -832,   775,  1087,
     738,   615,  5635,  5635,  -832,  -832,   879,   486,   872,  -832,
    5635,  -832,    70,  5635,  2348,   827,  -832,  1325,  2348,  -832,
    -832,   741,   882,  -832,   884,  4735,   744,   252,  -832,   743,
    -832,  2348,  -832,   888,  1459,  4382,  1595,  2756,  -832,  -832,
    1939,  -832,  -832,  -832,  -832,  5635,  4735,  -832,  4453,  -832,
     709,  1168,   962,   517,   854,   709,  4735,  4735,  5635,  -832,
     517,  4524,  -832,  -832,  4664,  -832,  2348,   883,  -832,  1459,
     464,  -832,  -832,  2348,   720,  2348,  -832,  -832,  -832,  -832,
    -832,  -832,  3108,  -832,   855,  -832,   977,   517,  -832,  1787,
    -832,   891,  2348,  2348,  -832,  2348,  -832,  -832,   754,  -832,
    -832,   486,  -832,  -832,  -832,  -832,  1459,   616,  -832,  1459,
    1402,  1459,  2348,   354,   615,  -832,  -832,   709,  -832
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,     0,     0,     0,   123,   152,   165,   217,     0,     2,
       5,     7,     8,     9,    10,     4,   138,   139,   141,   142,
     143,     0,     0,   495,   114,   115,   118,   117,     0,   124,
     125,   161,   162,   163,   164,   154,   156,     0,     0,   208,
     209,     0,   218,   219,   222,   223,     1,     6,     3,   140,
       0,     0,     0,     0,     0,     0,   126,   153,     0,     0,
       0,     0,   315,     0,     0,   316,   314,   212,   478,   313,
       0,   471,   472,   473,   210,     0,   225,   220,     0,    11,
       0,     0,     0,     0,     0,     0,     0,    32,    12,    14,
       0,   119,   122,   496,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   414,   182,   183,   184,   185,   186,   187,
     188,   189,   180,   121,   166,   167,   168,   172,   171,   178,
     195,   196,   190,   179,   116,     0,   128,     0,   127,   155,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   306,     0,     0,     0,   310,     0,     0,     0,
     283,     0,   312,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   307,   308,   311,     0,     0,     0,     0,
       0,     0,     0,   211,     0,     0,     0,   221,   250,   274,
       0,   145,   147,   148,   149,   150,   151,     0,     0,     0,
      39,    42,    41,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    33,   102,   103,     0,     0,
       0,     0,     0,     0,     0,     0,   174,   175,   173,     0,
       0,     0,     0,     0,     0,   495,     0,   495,     0,     0,
       0,     0,   158,   414,   204,   159,   202,     0,   390,     0,
     281,   385,     0,     0,   281,     0,   397,     0,   319,   320,
       0,   317,     0,     0,   329,   330,   331,   332,   333,   334,
     335,   336,   337,   338,   339,   340,   341,   342,   343,   344,
     345,   346,   347,   348,     0,     0,   477,   476,     0,   470,
     469,   493,     0,     0,     0,     0,     0,     0,     0,   423,
     474,     0,     0,   487,   489,   490,     0,     0,     0,   483,
     484,     0,     0,   479,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   481,
       0,   215,     0,   201,   216,     0,   233,     0,     0,     0,
     178,     0,   242,     0,   249,   251,   254,   253,     0,     0,
     273,   275,   279,   144,   146,     0,   265,    50,    51,    52,
      53,     0,     0,     0,   418,     0,     0,    74,     0,     0,
      69,    70,    71,   416,     0,   417,    73,   424,    72,   428,
     444,   445,   425,   426,   427,   459,   430,   429,     0,     0,
      43,    45,     0,    40,     0,     0,     0,     0,     0,     0,
       0,    32,    17,    19,    18,    23,     0,   110,     0,   107,
       0,    22,     0,     0,    27,    26,     0,    29,    28,    13,
     120,   181,     0,   170,     0,   191,     0,     0,     0,   200,
     198,   199,   194,   197,   415,     0,   129,     0,     0,     0,
     130,   131,   133,   132,     0,   157,     0,     0,   160,   203,
     284,   391,     0,     0,   395,   386,     0,     0,     0,   396,
       0,     0,     0,     0,     0,     0,     0,     0,   281,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   360,   405,     0,
     281,   350,   349,   351,   406,   407,   352,   353,   354,   355,
     356,   357,   358,   359,   361,   362,   363,   364,   365,   366,
     367,   368,   369,   370,   371,   372,   373,   374,   375,   376,
     377,   378,   379,   380,   381,   480,   482,     0,     0,   234,
       0,     0,     0,     0,     0,   241,     0,   165,   280,     0,
     278,   276,     0,    74,     0,     0,     0,     0,     0,    95,
       0,     0,     0,   242,   264,     0,    54,     0,     0,     0,
     465,   466,     0,     0,     0,     0,     0,    75,     0,    16,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    68,     0,     0,     0,     0,     0,     0,
       0,    46,     0,     0,     0,     0,     0,     0,     0,    31,
       0,   111,     0,   104,     0,     0,    24,     0,     0,   192,
     193,     0,   177,   176,     0,   112,     0,     0,     0,   206,
     205,     0,     0,   282,     0,   399,     0,     0,     0,   398,
     397,   285,   318,     0,     0,   325,     0,   402,   403,   486,
     494,   408,     0,     0,     0,     0,   413,     0,   475,   491,
     492,   382,   488,   383,   485,   384,   404,     0,   213,   214,
     236,     0,     0,   239,   243,     0,   224,     0,   237,   252,
       0,   277,     0,   439,    99,    98,    97,   436,     0,     0,
       0,     0,   271,   272,   266,   267,     0,     0,   467,     0,
       0,     0,     0,   447,     0,   419,    55,     0,     0,    81,
      77,    79,    80,     0,    76,     0,    58,    60,    59,    89,
       0,     0,     0,    91,     0,     0,    65,    63,     0,    67,
      66,     0,   462,     0,     0,     0,   460,   446,   420,    47,
      48,     0,     0,    15,     0,     0,     0,     0,     0,     0,
      20,     0,   108,   109,    25,     0,   169,     0,   134,     0,
     113,     0,   392,     0,   387,     0,     0,   400,     0,     0,
       0,   321,   324,     0,   327,     0,   409,     0,   411,   410,
       0,     0,     0,   227,     0,     0,     0,   246,     0,     0,
       0,   265,   238,   441,   438,     0,     0,    96,     0,   437,
     268,   421,   269,   468,     0,     0,     0,   455,     0,     0,
     449,     0,     0,     0,    56,    57,     0,     0,     0,    61,
       0,     0,   101,   461,     0,     0,    44,    34,    35,    36,
      38,     0,     0,     0,   105,     0,     0,     0,   135,   207,
       0,     0,     0,     0,     0,   322,     0,     0,   412,   309,
       0,   235,   228,   231,   232,   242,   230,   240,     0,     0,
       0,   242,     0,     0,   440,    94,     0,     0,     0,   431,
       0,   434,     0,     0,     0,     0,   454,     0,     0,   448,
      83,    86,     0,    85,     0,    78,     0,     0,    93,     0,
      62,     0,   464,     0,    49,     0,     0,     0,    21,   136,
       0,   393,   388,   389,   401,     0,   326,   328,     0,   229,
     245,     0,     0,   257,     0,     0,   443,   442,     0,   422,
     270,     0,   432,   435,     0,   451,     0,     0,   457,   458,
       0,    82,    84,     0,     0,     0,    64,   463,    30,    37,
     106,   137,     0,   394,   248,   244,     0,   258,   259,     0,
     256,     0,     0,     0,   450,     0,    87,    90,     0,    88,
     323,     0,   226,   260,   262,   263,   261,   265,   100,   433,
     453,   456,     0,   247,   242,   452,    92,   245,   255
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -832,  -832,   901,    48,  -832,  -832,  -832,  -173,  -832,   703,
    -832,  -832,   487,  -832,   210,   314,  -832,   317,  -831,  -832,
    -832,   205,  -832,  -832,   -13,  -832,  -832,   282,  -832,   284,
     485,  -832,   887,  -832,    24,    27,  -832,   907,  -832,     0,
    -832,  -832,   927,  -832,   734,   -64,    83,  -832,   897,    -5,
     -46,  -832,   785,  -832,  -832,  -832,  -832,  -199,  -832,  -832,
     -53,  -264,  -832,   683,   202,   881,  -832,   918,   390,   880,
    -832,  -832,  -832,   919,  -788,  -832,   373,   139,  -832,  -683,
    -599,  -832,   -55,  -579,  -832,  -832,   379,  -832,  -832,  -832,
    -832,  -832,  -823,  -832,   233,  -832,  -832,  -832,   378,  -832,
    -832,   -63,   -19,    59,  -832,   450,  -832,  -832,    -4,  -832,
     287,  -832,  -832,  -832,  -832,  -832,  -832,  -832,  -832,  -832,
    -832,  -832,   466,   471,  -832,  -832,  -832,  -832,  -832,  -832,
    -832,  -832,  -832,  -832,   -22,  -595,   339,    78,  -832,  -832,
    -832,    76,  -832,  -832,  -832,   151,   592,  -832,  -592,   138,
    -832,  -832,   -17,  -832,    80,  -832,  -832,  -573,  -832,  -832,
    -832,  -135,   -35,  -102,  -832,  -832,  -832,  -832,  -832,   498,
     832,  -110,   470,  -832,  -832,   478,   -39
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     8,     9,    10,    11,    12,    88,    89,   219,   220,
     221,   440,   441,   779,   419,   616,   617,   921,   922,   420,
     421,   598,   599,   422,   633,   246,   247,   458,   459,   460,
     489,    13,    24,    25,    90,    91,    14,    29,    30,   128,
      15,    16,    17,    79,   210,   211,   212,    35,    36,    37,
     250,   114,   115,   116,   117,   251,   118,   119,   120,   121,
      66,   275,   276,   495,   213,    39,    40,   288,   214,    42,
      43,    44,    45,   289,   712,   895,   206,   387,   831,   392,
     584,   585,   984,   985,  1002,   215,   394,   395,   396,   397,
     830,  1007,   603,   604,   734,   735,   736,   216,   400,   401,
     402,   673,   518,   172,    69,   290,   291,   173,   811,   174,
     684,   685,   175,   176,   177,   178,   179,   180,   181,   182,
     183,   184,   285,   286,   185,   186,   187,   188,   189,   190,
     191,   192,   193,   194,   123,   424,   425,   842,   195,   426,
     427,   911,   912,   428,   601,   722,   723,   429,   430,   744,
     431,   432,   915,   433,   846,   847,   434,   435,   436,   437,
     605,   330,   319,   331,   320,    71,    72,    73,   338,   339,
     321,   332,   333,   334,   335,   322,    27
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -420;
  public static final short
  yytable_[] =
  {
       122,    38,    41,    70,   737,   716,   390,   113,   901,   324,
     797,   877,   499,    93,   742,   873,   222,   743,   125,   487,
      21,   924,   198,   666,   125,   318,   438,   201,   198,   125,
      26,   264,   264,   496,   337,   832,   745,   732,   733,   510,
     171,   122,   122,   204,   122,   122,   122,   122,   122,   252,
     230,   254,   255,   256,   257,   258,   802,    47,   336,   452,
     453,   454,   455,   776,   530,   461,   652,   261,   465,    38,
     279,   283,    41,   535,   962,   469,   245,   122,   122,    92,
     526,    26,   262,    18,   272,   274,   266,    52,   268,   263,
     204,    53,   697,   907,   378,   380,    47,    68,   531,    18,
      70,   439,   388,   910,   949,   126,   635,   520,   103,   228,
      22,   126,   954,   277,   280,   284,   126,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   996,
    -414,   326,   804,   897,   807,   497,   122,   485,    23,   122,
     612,  -414,   122,   382,   127,   222,   385,   488,   843,   389,
     127,   667,   264,   533,   533,   127,   874,   511,   533,   798,
     878,   104,   105,   106,   107,   108,   109,   110,   111,   381,
     199,   760,   384,   398,  1014,   202,   512,   926,   264,   803,
     205,    46,   653,   231,    68,   423,   648,    53,    28,    50,
     822,   130,    19,   393,   399,   636,   122,   122,   122,   122,
     122,   450,   661,   479,   480,   481,   482,   483,    19,    68,
     463,  -414,  -414,   122,   467,   468,    68,    68,    68,   449,
     274,   612,  -414,   131,  -414,   231,   456,   717,    51,    53,
     464,  -419,   466,   613,   614,  -165,   615,   444,   445,   446,
     447,   517,   519,   259,   742,    68,    68,   743,    92,   269,
     270,   271,   261,    68,   527,   577,   805,    54,   808,   491,
     492,   493,   533,   470,   533,   260,   745,   262,   578,   539,
     473,    59,   261,   231,   263,   974,   261,    53,   637,   656,
     613,   614,   532,   615,   474,   414,   950,   262,   533,   261,
     875,   262,   955,  -414,   263,   524,   528,   103,   263,   269,
     270,   271,  -419,   390,   262,  -414,    31,    32,    33,    34,
      55,   263,   537,   540,  -419,   541,   542,   543,    57,    74,
     546,   547,   548,   549,   550,   551,   552,   553,   554,   555,
     556,   557,   558,   559,   560,   561,   562,   563,   564,   565,
     566,   567,   568,   569,   570,   571,   572,   573,   574,   668,
     888,    60,    61,  -165,   534,    62,    58,   671,   899,   261,
     533,   613,   614,    75,   615,    77,   990,   664,   920,   588,
      63,    78,   923,   423,   262,    64,   318,   269,   270,   271,
      20,   263,   606,   607,   608,   826,   674,   611,   196,   618,
     677,    80,   197,    81,    82,    83,    20,   223,    84,   200,
      85,   224,   225,    86,   686,  1017,   226,    65,   227,   122,
     423,   122,   248,   122,   122,   122,   659,   698,   660,   600,
     274,   662,   663,   229,    31,    32,    33,    34,   249,   245,
     245,   245,   245,   122,   122,     5,    93,   475,   245,   253,
     669,   670,   245,   261,   264,   265,   245,   747,   748,   122,
     692,   747,   748,   267,     6,     7,   382,   122,   262,   665,
     784,   785,   786,   787,   689,   263,   749,   790,   122,    70,
     749,   471,   261,   314,   794,   700,   315,     1,   675,     2,
     261,   323,   678,   327,   681,   472,   683,   262,   328,   329,
     269,   270,   271,   691,   263,   262,   694,   695,   391,    87,
     403,   699,   263,   701,   457,   703,   704,   705,   383,     3,
       4,    94,   898,   902,    95,   650,   448,   122,    80,   122,
      81,    82,    83,   476,   710,    84,   713,    85,   262,   484,
      86,   486,   791,    96,   485,   261,    68,   501,    97,    98,
      99,   100,   101,    68,   490,   102,   799,  -165,   708,   709,
     262,    68,   747,   748,   714,    68,   903,   263,   494,    68,
     513,   771,   773,    68,   504,    68,   269,   270,   271,   423,
      68,   749,   393,   686,   505,   509,   399,   423,    68,   514,
     423,   520,    68,   521,   522,   751,   752,   523,   423,   423,
     423,   423,   525,   103,   423,   658,   529,   423,   765,   824,
     536,   768,   769,   770,   774,   597,   544,   423,   777,   575,
     103,   724,   725,   726,   761,    64,   545,   576,   766,   269,
     270,   271,   269,   270,   271,   261,   451,  -165,  -165,   848,
     122,  -165,   579,   245,   594,   595,   596,   817,   800,     5,
     262,   581,   642,   580,   857,   582,  -165,   263,   795,    59,
     379,  -165,   494,   781,   122,   586,   587,   122,     6,     7,
     591,   829,   789,   583,   833,   208,   261,   589,   820,   209,
     602,    59,   386,   104,   105,   106,   107,   108,   109,   110,
     111,   262,   477,  -165,   261,   609,   827,   112,   263,   936,
     634,   638,   938,   639,   640,   217,   423,   218,   423,   262,
     478,    68,   438,     1,   841,     2,   263,   423,   423,   643,
     388,   644,   645,   646,    59,    68,   647,    31,    32,    33,
      34,   423,   488,   423,     5,    31,    32,    33,    34,    60,
      61,   230,   423,    62,    68,     3,     4,     5,   655,   654,
     687,   688,   696,     6,     7,   342,   711,   316,   317,   706,
     208,    60,    61,    64,   209,    62,     6,     7,   715,   720,
      68,   245,   245,   245,   245,   122,   728,   245,   876,   316,
     317,   245,   879,   729,   880,    64,   881,   882,   730,   883,
     884,   885,   442,   738,   218,    65,   887,   753,   762,   782,
      68,   933,   778,   796,    60,    61,   896,   788,    62,   667,
     801,   731,   535,   806,   511,   848,   812,    65,   815,   816,
     825,     5,   746,   287,   834,   835,   850,   423,    64,   637,
     756,   757,   758,   759,   925,   423,   763,   851,   423,   767,
       6,     7,   849,   423,   852,   853,   122,   208,    59,   780,
     856,   209,   935,   953,   937,   122,   927,   858,   940,   862,
      65,   863,   960,  -419,   864,   865,   866,   946,   871,   872,
     892,   948,   886,   890,   232,   233,   234,   900,   916,   235,
     918,    68,   929,   956,   957,   841,   908,   917,   931,   951,
      94,   961,   423,    95,   964,   991,   423,   958,   122,   122,
     966,   971,   970,   972,   973,   987,   975,   977,   989,   423,
    1008,   995,    96,   998,  1012,  1001,    48,    97,    98,    99,
     100,   101,   443,   245,   102,   245,   982,   641,    60,    61,
     750,   754,    62,   122,   837,    59,   792,    56,   836,   793,
     838,   124,   651,    49,   423,   404,   316,   317,   325,   844,
     845,   423,    64,   423,   129,   203,    67,   207,   498,   718,
      76,   891,  1018,   854,   682,   855,   719,   423,   840,   721,
     423,   423,   814,   423,   859,   775,    68,   679,  1000,  1013,
      94,   988,   680,    95,    65,   959,   904,   919,   963,   103,
     423,   590,   813,  1015,    64,    94,  1003,   968,    95,   690,
     672,   340,    96,   702,     0,     0,     0,    97,    98,    99,
     100,   101,     0,     0,   102,    60,    61,    96,     0,    62,
     236,   237,    97,    98,    99,   100,   101,   238,     0,   102,
       0,     0,     0,   316,   317,   239,   240,   241,   242,    64,
       0,   243,   244,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   104,   105,   106,   107,   108,   109,   110,   111,
       0,     0,     0,     0,     0,     0,   112,   928,     0,     0,
     930,    65,     0,    94,     0,   934,    95,     0,     0,   103,
       0,     0,     0,     0,    64,     0,     0,     0,   828,     0,
       0,    95,     0,     0,   103,    96,     0,     0,     0,    64,
      97,    98,    99,   100,   101,   952,     0,   102,    95,     0,
      96,     0,     0,     0,     0,    97,    98,    99,   100,   101,
       0,     0,   102,     0,   965,     0,     0,    96,   969,     0,
       0,     0,    97,    98,    99,   100,   101,     0,     0,   102,
       0,   976,   104,   105,   106,   107,   108,   109,   110,   111,
     620,   621,   622,     0,     0,   623,   112,   104,   105,   106,
     107,   108,   109,   110,   111,     0,   909,     0,     0,     0,
       0,   112,   273,   620,   621,   622,   994,    64,   623,     0,
       0,     0,     0,   997,     0,   999,   986,   103,     0,    95,
       0,     0,    64,     0,     0,   910,    59,   823,     0,  1006,
       0,     0,  1009,  1010,   103,  1011,     0,     0,    96,    64,
       0,   913,   914,    97,    98,    99,   100,   101,   619,     0,
     102,     0,  1016,     0,     0,   620,   621,   622,     0,     0,
     623,     0,     0,     0,     0,   104,   105,   106,   107,   108,
     109,   110,   111,     0,     0,     0,     0,     0,     0,   112,
     104,   105,   106,   107,   108,   109,   110,   111,   620,   621,
     622,     0,     0,   623,   112,     0,   727,   104,   105,   106,
     107,   108,   109,   110,   111,   783,    60,    61,     0,     0,
      62,   112,   620,   621,   622,   103,     0,   623,     0,     0,
      64,     0,     0,     0,   316,   317,   624,   237,     0,     0,
      64,   625,     0,   626,     0,     0,     0,     0,     0,     0,
       0,   627,   628,   629,   630,     0,     0,   631,   632,   624,
     237,     0,     0,     0,   625,     0,   626,     0,     0,     0,
       0,     0,    65,     0,   627,   628,   629,   630,     0,     0,
     631,   632,     0,    59,     0,     0,     0,     0,   104,   105,
     106,   107,   108,   109,   110,   111,   620,   621,   622,     0,
       0,   623,   112,     0,   839,     0,     0,     0,     0,     0,
       0,   624,   237,     0,     0,     0,   625,     0,   626,     0,
       0,     0,     0,     0,     0,     0,   627,   628,   629,   630,
       0,     0,   631,   632,   620,   621,   622,     0,     0,   623,
     967,     0,   905,     0,   624,   237,     0,     0,     0,   625,
       0,   626,     0,   620,   621,   622,     0,     0,   623,   627,
     628,   629,   630,    60,    61,   631,   632,    62,   624,   237,
       0,     0,     0,   625,     0,   626,     0,     0,     0,     0,
       0,   316,   317,   627,   628,   629,   630,    64,     0,   631,
     632,   620,   621,   622,     0,     0,   623,     0,     0,   906,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     620,   621,   622,     0,   913,   623,     0,     0,     0,    65,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   232,   233,   234,     0,     0,   235,     0,
       0,     0,   624,   237,     0,     0,     0,   625,     0,   626,
       0,     0,     0,   867,     0,     0,     0,   627,   628,   629,
     630,     0,     0,   631,   632,     0,   232,   233,   234,     0,
       0,   235,     0,     0,     0,     0,     0,     0,     0,     0,
     624,   237,     0,     0,     0,   625,   868,   626,     0,   232,
     233,   234,     0,     0,   235,   627,   628,   629,   630,   624,
     237,   631,   632,     0,   625,     0,   626,     0,     0,   869,
       0,     0,     0,     0,   627,   628,   629,   630,     0,     0,
     631,   632,     0,   232,   233,   234,     0,     0,   235,     0,
       0,     0,     0,     0,     0,     0,     0,   624,   237,     0,
       0,     0,   625,   870,   626,     0,   232,   233,   234,     0,
       0,   235,   627,   628,   629,   630,   624,   237,   631,   632,
       0,   625,     0,   626,     0,     0,   979,     0,     0,     0,
       0,   627,   628,   629,   630,     0,     0,   631,   632,   236,
     237,     0,     0,     0,     0,     0,   238,     0,     0,     0,
       0,     0,     0,     0,   239,   240,   241,   242,     0,     0,
     243,   244,     0,     0,   341,     0,     0,     0,     0,     0,
       0,     0,   236,   237,     0,   342,     0,     0,     0,   238,
       0,     0,     0,     0,     0,     0,     0,   239,   240,   241,
     242,     0,     0,   243,   244,   236,   237,     0,     0,     0,
     344,     0,   238,     0,     0,     0,     0,     0,     0,     0,
     239,   240,   241,   242,     0,     0,   243,   244,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   236,
     237,     0,     0,   345,   346,     0,   238,     0,     0,     0,
       0,     0,     0,     0,   239,   240,   241,   242,     0,     0,
     243,   244,   236,   237,     0,     0,     0,   347,   348,   238,
       0,     0,     0,     0,     0,     0,     0,   239,   240,   241,
     242,     0,     0,   243,   244,   349,   350,   351,   352,   353,
     354,   355,   356,   357,   358,   359,   360,   361,   362,   363,
     364,   365,   366,   367,   368,   369,   370,   371,   372,   373,
     374,   375,   376,   377,   341,   405,     0,     0,   406,     0,
     407,   408,   409,   410,   411,   342,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     657,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     344,     0,     0,     0,     0,     0,     0,     0,  1004,  1005,
       0,     0,     0,     0,     0,     0,     0,   412,     0,     0,
       0,   413,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   345,   346,   341,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   342,     0,     0,     0,
       0,     0,   414,     0,     0,     0,     0,   347,   348,     0,
     415,     0,   416,     0,   417,     0,     0,     0,     0,     0,
       0,   344,     0,     0,     0,   349,   350,   351,   352,   353,
     354,   355,   356,   357,   358,   359,   360,   361,   362,   363,
     364,   365,   366,   367,   368,   369,   370,   371,   372,   373,
     374,   375,   376,   377,   345,   346,   341,     0,     0,     0,
       0,     0,   418,     0,     0,     0,     0,   342,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   347,   348,
     860,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   344,     0,     0,     0,   349,   350,   351,   352,
     353,   354,   355,   356,   357,   358,   359,   360,   361,   362,
     363,   364,   365,   366,   367,   368,   369,   370,   371,   372,
     373,   374,   375,   376,   377,   345,   346,   341,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   342,     0,
       0,     0,     0,     0,     0,   939,     0,     0,     0,   347,
     348,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   344,     0,     0,     0,   349,   350,   351,
     352,   353,   354,   355,   356,   357,   358,   359,   360,   361,
     362,   363,   364,   365,   366,   367,   368,   369,   370,   371,
     372,   373,   374,   375,   376,   377,   345,   346,   341,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   342,
       0,     0,     0,     0,     0,     0,   981,     0,     0,     0,
     347,   348,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   344,     0,     0,     0,   349,   350,
     351,   352,   353,   354,   355,   356,   357,   358,   359,   360,
     361,   362,   363,   364,   365,   366,   367,   368,   369,   370,
     371,   372,   373,   374,   375,   376,   377,   345,   346,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   516,     0,     0,     0,     0,
       0,   347,   348,     0,     0,     0,   405,     0,     0,   406,
       0,   407,   408,   409,   410,   411,     0,     0,     0,   349,
     350,   351,   352,   353,   354,   355,   356,   357,   358,   359,
     360,   361,   362,   363,   364,   365,   366,   367,   368,   369,
     370,   371,   372,   373,   374,   375,   376,   377,   132,     0,
       0,   133,     0,     0,     0,     0,     0,     0,   412,     0,
     134,     0,   413,     0,     0,     0,   741,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   414,     0,     0,     0,     0,   135,   592,
     136,   415,     0,   416,   137,   593,   138,   139,   140,   141,
     142,   143,   144,   145,   146,   147,   148,   149,   150,   151,
     152,   153,   154,   155,   156,   157,     0,     0,   158,   159,
       0,     0,    62,   160,   161,   162,   163,   164,   165,   594,
     595,   596,     0,     0,     0,     0,   132,   166,     0,   133,
       0,     0,    64,   418,     0,     0,     0,     0,   134,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   167,   168,   169,
     170,     0,     0,     0,    65,     0,   405,     0,     0,   406,
       0,   407,   408,   409,   410,   411,   135,     0,   136,     0,
       0,     0,   137,   462,   138,   139,   140,   141,   142,   143,
     144,   145,   146,   147,   148,   149,   150,   151,   152,   153,
     154,   155,   156,   157,     0,     0,   158,   159,   341,     0,
      62,   160,   161,   162,   163,   164,   165,     0,   412,   342,
       0,     0,   413,     0,     0,   166,     0,     0,     0,     0,
      64,     0,     0,     0,   506,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   344,     0,     0,     0,     0,     0,
       0,     0,     0,   414,     0,   167,   168,   169,   170,     0,
       0,   415,    65,   416,     0,   417,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   345,   346,   341,
       0,   764,     0,     0,     0,     0,     0,     0,     0,     0,
     342,     0,     0,     0,     0,     0,     0,     0,   507,     0,
       0,   347,   348,     0,     0,   502,     0,     0,     0,     0,
       0,     0,     0,   418,     0,   344,     0,     0,     0,   349,
     350,   351,   352,   353,   354,   355,   356,   357,   358,   359,
     360,   361,   362,   363,   364,   365,   366,   367,   368,   369,
     370,   371,   372,   373,   374,   375,   376,   377,   345,   346,
     341,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   342,   508,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   347,   348,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   344,     0,     0,     0,
     349,   350,   351,   352,   353,   354,   355,   356,   357,   358,
     359,   360,   361,   362,   363,   364,   365,   366,   367,   368,
     369,   370,   371,   372,   373,   374,   375,   376,   377,   345,
     346,   341,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   342,   503,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   347,   348,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   344,     0,     0,
       0,   349,   350,   351,   352,   353,   354,   355,   356,   357,
     358,   359,   360,   361,   362,   363,   364,   365,   366,   367,
     368,   369,   370,   371,   372,   373,   374,   375,   376,   377,
     345,   346,   341,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   342,   503,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   347,   348,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   344,     0,
       0,     0,   349,   350,   351,   352,   353,   354,   355,   356,
     357,   358,   359,   360,   361,   362,   363,   364,   365,   366,
     367,   368,   369,   370,   371,   372,   373,   374,   375,   376,
     377,   345,   346,   341,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   342,   693,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   347,   348,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   344,
       0,     0,     0,   349,   350,   351,   352,   353,   354,   355,
     356,   357,   358,   359,   360,   361,   362,   363,   364,   365,
     366,   367,   368,   369,   370,   371,   372,   373,   374,   375,
     376,   377,   345,   346,   341,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   342,   707,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   347,   348,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     344,     0,     0,     0,   349,   350,   351,   352,   353,   354,
     355,   356,   357,   358,   359,   360,   361,   362,   363,   364,
     365,   366,   367,   368,   369,   370,   371,   372,   373,   374,
     375,   376,   377,   345,   346,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   980,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   347,   348,   405,
       0,     0,   406,     0,   407,   408,   409,   410,   411,     0,
       0,     0,     0,     0,     0,   349,   350,   351,   352,   353,
     354,   355,   356,   357,   358,   359,   360,   361,   362,   363,
     364,   365,   366,   367,   368,   369,   370,   371,   372,   373,
     374,   375,   376,   377,     0,   341,     0,     0,     0,     0,
       0,   412,   755,     0,     0,   413,   342,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   739,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   344,     0,     0,     0,     0,   414,     0,     0,     0,
       0,     0,     0,     0,   415,     0,   416,     0,   593,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   740,
       0,     0,     0,     0,   345,   346,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   347,   348,
       0,     0,     0,     0,     0,     0,   418,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   349,   350,   351,   352,
     353,   354,   355,   356,   357,   358,   359,   360,   361,   362,
     363,   364,   365,   366,   367,   368,   369,   370,   371,   372,
     373,   374,   375,   376,   377,   341,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   342,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   344,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     809,   810,     0,     0,   345,   346,   341,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   342,   343,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   347,   348,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   344,     0,     0,     0,   349,   350,   351,   352,
     353,   354,   355,   356,   357,   358,   359,   360,   361,   362,
     363,   364,   365,   366,   367,   368,   369,   370,   371,   372,
     373,   374,   375,   376,   377,   345,   346,   341,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   342,   500,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   347,
     348,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   344,     0,     0,     0,   349,   350,   351,
     352,   353,   354,   355,   356,   357,   358,   359,   360,   361,
     362,   363,   364,   365,   366,   367,   368,   369,   370,   371,
     372,   373,   374,   375,   376,   377,   345,   346,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     347,   348,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   349,   350,
     351,   352,   353,   354,   355,   356,   357,   358,   359,   360,
     361,   362,   363,   364,   365,   366,   367,   368,   369,   370,
     371,   372,   373,   374,   375,   376,   377,   341,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   342,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   344,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   515,     0,     0,     0,     0,   345,   346,   341,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   342,
     649,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     347,   348,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   344,     0,     0,     0,   349,   350,
     351,   352,   353,   354,   355,   356,   357,   358,   359,   360,
     361,   362,   363,   364,   365,   366,   367,   368,   369,   370,
     371,   372,   373,   374,   375,   376,   377,   345,   346,   341,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     342,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   347,   348,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   344,     0,     0,     0,   349,
     350,   351,   352,   353,   354,   355,   356,   357,   358,   359,
     360,   361,   362,   363,   364,   365,   366,   367,   368,   369,
     370,   371,   372,   373,   374,   375,   376,   377,   345,   346,
     341,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   342,   818,     0,     0,     0,     0,     0,     0,   507,
       0,     0,   347,   348,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   344,     0,     0,     0,
     349,   350,   351,   352,   353,   354,   355,   356,   357,   358,
     359,   360,   361,   362,   363,   364,   365,   366,   367,   368,
     369,   370,   371,   372,   373,   374,   375,   376,   377,   345,
     346,   341,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   342,   819,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   347,   348,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   344,     0,     0,
       0,   349,   350,   351,   352,   353,   354,   355,   356,   357,
     358,   359,   360,   361,   362,   363,   364,   365,   366,   367,
     368,   369,   370,   371,   372,   373,   374,   375,   376,   377,
     345,   346,   341,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   342,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   347,   348,     0,     0,   861,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   344,     0,
       0,     0,   349,   350,   351,   352,   353,   354,   355,   356,
     357,   358,   359,   360,   361,   362,   363,   364,   365,   366,
     367,   368,   369,   370,   371,   372,   373,   374,   375,   376,
     377,   345,   346,   341,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   342,   889,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   347,   348,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   344,
       0,     0,     0,   349,   350,   351,   352,   353,   354,   355,
     356,   357,   358,   359,   360,   361,   362,   363,   364,   365,
     366,   367,   368,   369,   370,   371,   372,   373,   374,   375,
     376,   377,   345,   346,   341,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   342,     0,     0,     0,   941,
       0,     0,     0,     0,     0,     0,   347,   348,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     344,     0,     0,     0,   349,   350,   351,   352,   353,   354,
     355,   356,   357,   358,   359,   360,   361,   362,   363,   364,
     365,   366,   367,   368,   369,   370,   371,   372,   373,   374,
     375,   376,   377,   345,   346,   341,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   342,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   347,   348,   942,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   344,     0,     0,     0,   349,   350,   351,   352,   353,
     354,   355,   356,   357,   358,   359,   360,   361,   362,   363,
     364,   365,   366,   367,   368,   369,   370,   371,   372,   373,
     374,   375,   376,   377,   345,   346,   341,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   342,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   347,   348,
     943,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   344,     0,     0,     0,   349,   350,   351,   352,
     353,   354,   355,   356,   357,   358,   359,   360,   361,   362,
     363,   364,   365,   366,   367,   368,   369,   370,   371,   372,
     373,   374,   375,   376,   377,   345,   346,   341,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   342,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   347,
     348,   944,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   344,     0,     0,     0,   349,   350,   351,
     352,   353,   354,   355,   356,   357,   358,   359,   360,   361,
     362,   363,   364,   365,   366,   367,   368,   369,   370,   371,
     372,   373,   374,   375,   376,   377,   345,   346,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     347,   348,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   349,   350,
     351,   352,   353,   354,   355,   356,   357,   358,   359,   360,
     361,   362,   363,   364,   365,   366,   367,   368,   369,   370,
     371,   372,   373,   374,   375,   376,   377,   341,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   342,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   344,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   945,     0,     0,     0,     0,   345,   346,   341,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   342,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     347,   348,     0,     0,     0,     0,     0,     0,     0,   947,
       0,     0,     0,     0,   344,     0,     0,     0,   349,   350,
     351,   352,   353,   354,   355,   356,   357,   358,   359,   360,
     361,   362,   363,   364,   365,   366,   367,   368,   369,   370,
     371,   372,   373,   374,   375,   376,   377,   345,   346,   341,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     342,   978,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   347,   348,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   344,     0,     0,     0,   349,
     350,   351,   352,   353,   354,   355,   356,   357,   358,   359,
     360,   361,   362,   363,   364,   365,   366,   367,   368,   369,
     370,   371,   372,   373,   374,   375,   376,   377,   345,   346,
     341,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   342,   983,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   347,   348,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   344,     0,     0,     0,
     349,   350,   351,   352,   353,   354,   355,   356,   357,   358,
     359,   360,   361,   362,   363,   364,   365,   366,   367,   368,
     369,   370,   371,   372,   373,   374,   375,   376,   377,   345,
     346,   341,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   342,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   992,   347,   348,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   344,     0,     0,
       0,   349,   350,   351,   352,   353,   354,   355,   356,   357,
     358,   359,   360,   361,   362,   363,   364,   365,   366,   367,
     368,   369,   370,   371,   372,   373,   374,   375,   376,   377,
     345,   346,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   347,   348,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   349,   350,   351,   352,   353,   354,   355,   356,
     357,   358,   359,   360,   361,   362,   363,   364,   365,   366,
     367,   368,   369,   370,   371,   372,   373,   374,   375,   376,
     377,   341,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   342,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   344,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   993,     0,     0,     0,     0,
     345,   346,   341,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   342,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   347,   348,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   344,     0,
       0,     0,   349,   350,   351,   352,   353,   354,   355,   356,
     357,   358,   359,   360,   361,   362,   363,   364,   365,   366,
     367,   368,   369,   370,   371,   372,   373,   374,   375,   376,
     377,   345,   346,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   132,   347,   348,   133,     0,     0,
       0,     0,     0,     0,     0,     0,   134,   281,     0,     0,
       0,     0,     0,   349,   350,   351,   352,   353,   354,   355,
     356,   357,   358,   359,   360,   361,   362,   363,   364,   365,
     366,   367,   368,   369,   370,   371,   372,   373,   374,   375,
     376,   377,     0,     0,   135,     0,   136,     0,     0,     0,
     137,     0,   138,   139,   140,   141,   142,   143,   144,   145,
     146,   147,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,     0,   282,   158,   159,     0,     0,    62,   160,
     161,   162,   163,   164,   165,   132,     0,     0,   133,     0,
       0,     0,     0,   166,     0,     0,     0,   134,    64,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   167,   168,   169,   170,     0,   893,   894,
      65,     0,     0,     0,     0,   135,     0,   136,     0,     0,
       0,   137,     0,   138,   139,   140,   141,   142,   143,   144,
     145,   146,   147,   148,   149,   150,   151,   152,   153,   154,
     155,   156,   157,     0,     0,   158,   159,     0,     0,    62,
     160,   161,   162,   163,   164,   165,   132,     0,     0,   133,
     278,     0,     0,     0,   166,     0,     0,     0,   134,    64,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   167,   168,   169,   170,     0,     0,
       0,    65,     0,     0,     0,     0,   135,     0,   136,     0,
       0,     0,   137,     0,   138,   139,   140,   141,   142,   143,
     144,   145,   146,   147,   148,   149,   150,   151,   152,   153,
     154,   155,   156,   157,     0,     0,   158,   159,     0,     0,
      62,   160,   161,   162,   163,   164,   165,   132,   538,     0,
     133,     0,     0,     0,     0,   166,     0,     0,     0,   134,
      64,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   167,   168,   169,   170,     0,
       0,     0,    65,     0,     0,     0,     0,   135,     0,   136,
       0,     0,     0,   137,     0,   138,   139,   140,   141,   142,
     143,   144,   145,   146,   147,   148,   149,   150,   151,   152,
     153,   154,   155,   156,   157,     0,     0,   158,   159,     0,
       0,    62,   160,   161,   162,   163,   164,   165,   132,     0,
       0,   133,     0,     0,     0,     0,   166,     0,     0,     0,
     134,    64,     0,     0,     0,     0,     0,     0,   610,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   167,   168,   169,   170,
       0,     0,     0,    65,     0,     0,     0,     0,   135,     0,
     136,     0,     0,     0,   137,     0,   138,   139,   140,   141,
     142,   143,   144,   145,   146,   147,   148,   149,   150,   151,
     152,   153,   154,   155,   156,   157,     0,     0,   158,   159,
       0,     0,    62,   160,   161,   162,   163,   164,   165,   132,
       0,     0,   133,     0,     0,     0,     0,   166,     0,     0,
       0,   134,    64,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   167,   168,   169,
     170,     0,     0,     0,    65,     0,     0,     0,     0,   135,
       0,   136,     0,     0,     0,   137,     0,   138,   139,   140,
     141,   142,   143,   144,   145,   146,   147,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   676,     0,   158,
     159,     0,     0,    62,   160,   161,   162,   163,   164,   165,
     132,   772,     0,   133,     0,     0,     0,     0,   166,     0,
       0,     0,   134,    64,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   167,   168,
     169,   170,     0,     0,     0,    65,     0,     0,     0,     0,
     135,     0,   136,     0,     0,     0,   137,     0,   138,   139,
     140,   141,   142,   143,   144,   145,   146,   147,   148,   149,
     150,   151,   152,   153,   154,   155,   156,   157,     0,     0,
     158,   159,     0,     0,    62,   160,   161,   162,   163,   164,
     165,   132,     0,     0,   133,     0,     0,     0,     0,   166,
       0,     0,     0,   134,    64,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   167,
     168,   169,   170,     0,     0,     0,    65,     0,     0,     0,
       0,   135,     0,   136,     0,     0,     0,   137,     0,   138,
     139,   140,   141,   142,   143,   144,   145,   146,   147,   148,
     149,   150,   151,   152,   153,   154,   155,   156,   157,   821,
       0,   158,   159,     0,     0,    62,   160,   161,   162,   163,
     164,   165,   132,   932,     0,   133,     0,     0,     0,     0,
     166,     0,     0,     0,   134,    64,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     167,   168,   169,   170,     0,     0,     0,    65,     0,     0,
       0,     0,   135,     0,   136,     0,     0,     0,   137,     0,
     138,   139,   140,   141,   142,   143,   144,   145,   146,   147,
     148,   149,   150,   151,   152,   153,   154,   155,   156,   157,
       0,     0,   158,   159,     0,     0,    62,   160,   161,   162,
     163,   164,   165,   132,     0,     0,   133,     0,     0,     0,
       0,   166,     0,     0,     0,   134,    64,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   167,   168,   169,   170,     0,     0,     0,    65,     0,
       0,     0,     0,   135,     0,   136,     0,     0,     0,   137,
       0,   138,   139,   140,   141,   142,   143,   144,   145,   146,
     147,   148,   149,   150,   151,   152,   153,   154,   155,   156,
     157,     0,     0,   158,   159,     0,     0,    62,   160,   161,
     162,   163,   164,   165,     0,     0,     0,     0,     0,     0,
       0,     0,   166,     0,     0,     0,     0,    64,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   167,   168,   169,   170,     0,     0,     0,    65
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
        53,     6,     7,    38,   603,   584,   205,    53,   831,    18,
       8,     8,   276,    52,   609,     8,    80,   609,    30,    33,
     117,   852,     7,    33,    30,   160,   117,     7,     7,    30,
       3,   100,   100,    53,   169,   718,   609,    65,    66,    33,
      59,    94,    95,    18,    97,    98,    99,   100,   101,    95,
      18,    97,    98,    99,   100,   101,    22,     9,   168,   232,
     233,   234,   235,   636,   131,   238,    33,    28,   241,    74,
     133,   134,    77,   131,     4,   248,    89,   130,   131,    52,
      18,    54,    43,     0,   130,   131,   125,   161,   127,    50,
      18,   165,   161,   161,   196,   197,    48,    38,   165,    16,
     135,   192,   204,    33,   892,   117,    18,   165,   117,    85,
     117,   117,   900,   132,   133,   134,   117,   136,   137,   138,
     139,   140,   141,   142,   143,   144,   145,   146,   147,   148,
     149,   150,   151,   152,   153,   154,   155,   156,   157,   970,
      18,   163,    31,   826,    31,   165,   199,   161,   117,   202,
      28,    29,   205,   199,   166,   219,   202,   171,   737,   205,
     166,   171,   100,   161,   161,   166,   159,   161,   161,   167,
     167,   180,   181,   182,   183,   184,   185,   186,   187,   198,
     165,   193,   201,    55,  1007,   165,   165,   193,   100,   155,
     165,     0,   159,   161,   135,   217,    22,   165,   117,     7,
     161,     7,     0,   208,   209,    29,   259,   260,   261,   262,
     263,   230,   476,   259,   260,   261,   262,   263,    16,   160,
     239,    18,   100,   276,   243,   244,   167,   168,   169,   229,
     276,    28,    29,    39,   112,   161,   236,   165,     7,   165,
     240,    29,   242,   168,   169,   117,   171,   223,   224,   225,
     226,   314,   315,    28,   849,   196,   197,   849,   231,   135,
     136,   137,    28,   204,   327,     7,   155,    38,   155,   269,
     270,   271,   161,   249,   161,    50,   849,    43,     7,   342,
      19,    18,    28,   161,    50,    33,    28,   165,   112,   462,
     168,   169,   155,   171,    33,   105,   895,    43,   161,    28,
     176,    43,   901,   100,    50,   324,   328,   117,    50,   135,
     136,   137,   100,   512,    43,   112,   188,   189,   190,   191,
       7,    50,   341,   342,   112,   344,   345,   346,    38,    38,
     349,   350,   351,   352,   353,   354,   355,   356,   357,   358,
     359,   360,   361,   362,   363,   364,   365,   366,   367,   368,
     369,   370,   371,   372,   373,   374,   375,   376,   377,   494,
      19,    98,    99,   117,   155,   102,   117,   502,   114,    28,
     161,   168,   169,   117,   171,    38,   955,   487,    19,   398,
     117,     9,    19,   405,    43,   122,   521,   135,   136,   137,
       0,    50,   411,   412,   413,   161,   506,   416,    18,   418,
     510,     9,    18,    11,    12,    13,    16,    30,    16,   117,
      18,    30,    30,    21,   516,  1014,    30,   154,   117,   472,
     442,   474,     8,   476,   477,   478,   472,   529,   474,   405,
     476,   477,   478,    30,   188,   189,   190,   191,    38,   452,
     453,   454,   455,   496,   497,    37,   485,    22,   461,   117,
     496,   497,   465,    28,   100,   117,   469,    98,    99,   512,
     523,    98,    99,   117,    56,    57,   512,   520,    43,   488,
     643,   644,   645,   646,   520,    50,   117,   650,   531,   514,
     117,    19,    28,    18,   657,   531,    18,     3,   507,     5,
      28,   117,   511,    18,   513,    33,   515,    43,    18,   110,
     135,   136,   137,   522,    50,    43,   525,   526,   117,   117,
       4,   530,    50,   532,   117,   534,   535,   536,   124,    35,
      36,    18,    19,    69,    21,   160,     8,   580,     9,   582,
      11,    12,    13,    41,   580,    16,   582,    18,    43,   117,
      21,    31,   652,    40,   161,    28,   487,    22,    45,    46,
      47,    48,    49,   494,   167,    52,   666,    18,   577,   578,
      43,   502,    98,    99,   583,   506,   112,    50,    55,   510,
      69,   634,   635,   514,    31,   516,   135,   136,   137,   601,
     521,   117,   587,   685,    31,    31,   591,   609,   529,   161,
     612,   165,   533,   161,   155,   614,   615,    18,   620,   621,
     622,   623,    18,   117,   626,   164,   161,   629,   627,   711,
     155,   630,   631,   632,   636,   405,   117,   639,   637,    19,
     117,   594,   595,   596,   624,   122,   102,    19,   628,   135,
     136,   137,   135,   136,   137,    28,   117,    98,    99,   741,
     693,   102,    19,   656,   151,   152,   153,   693,   667,    37,
      43,   117,   442,   165,   160,   165,   117,    50,   658,    18,
      19,   122,    55,   639,   717,   117,    38,   720,    56,    57,
      38,   717,   648,    58,   720,    63,    28,   117,   697,    67,
      64,    18,    19,   180,   181,   182,   183,   184,   185,   186,
     187,    43,    44,   154,    28,    18,   715,   194,    50,   872,
      18,   100,   875,     7,   117,     8,   728,    10,   730,    43,
      44,   652,   117,     3,   736,     5,    50,   739,   740,     8,
     822,     8,     8,     8,    18,   666,   117,   188,   189,   190,
     191,   753,   171,   755,    37,   188,   189,   190,   191,    98,
      99,    18,   764,   102,   685,    35,    36,    37,    29,   161,
      19,    19,    19,    56,    57,    18,    18,   116,   117,    19,
      63,    98,    99,   122,    67,   102,    56,    57,    59,   165,
     711,   784,   785,   786,   787,   828,     8,   790,   797,   116,
     117,   794,   801,    38,   803,   122,   805,   806,     8,   808,
     809,   810,     8,    22,    10,   154,   815,    28,   193,     7,
     741,   864,   117,     4,    98,    99,   825,    19,   102,   171,
      54,   601,   131,   161,   161,   917,     4,   154,    28,    19,
      54,    37,   612,   117,     8,   161,    19,   849,   122,   112,
     620,   621,   622,   623,   853,   857,   626,    18,   860,   629,
      56,    57,    38,   865,    18,   165,   899,    63,    18,   639,
      33,    67,   871,   899,   873,   908,   856,    33,   877,    19,
     154,    19,   908,   100,    18,     8,   193,   886,    18,     8,
      19,   890,    28,   161,    11,    12,    13,   117,     4,    16,
      28,   822,   193,   902,   903,   907,   165,   161,   124,   114,
      18,   910,   914,    21,   913,   958,   918,    18,   951,   952,
      73,    19,   161,    19,   160,   951,   163,    19,    54,   931,
      19,    28,    40,   193,   160,    60,    15,    45,    46,    47,
      48,    49,   219,   936,    52,   938,   945,   440,    98,    99,
     613,   617,   102,   986,   729,    18,   654,    30,   728,   655,
     730,    54,   457,    16,   966,   211,   116,   117,   163,   739,
     740,   973,   122,   975,    57,    74,    38,    77,   275,   586,
      41,   822,  1017,   753,   514,   755,   587,   989,   735,   591,
     992,   993,   685,   995,   764,   636,   917,   511,   982,  1001,
      18,    19,   511,    21,   154,   907,   835,   849,   912,   117,
    1012,   399,    75,  1010,   122,    18,    19,   917,    21,   521,
     502,   169,    40,   533,    -1,    -1,    -1,    45,    46,    47,
      48,    49,    -1,    -1,    52,    98,    99,    40,    -1,   102,
     157,   158,    45,    46,    47,    48,    49,   164,    -1,    52,
      -1,    -1,    -1,   116,   117,   172,   173,   174,   175,   122,
      -1,   178,   179,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   180,   181,   182,   183,   184,   185,   186,   187,
      -1,    -1,    -1,    -1,    -1,    -1,   194,   857,    -1,    -1,
     860,   154,    -1,    18,    -1,   865,    21,    -1,    -1,   117,
      -1,    -1,    -1,    -1,   122,    -1,    -1,    -1,    18,    -1,
      -1,    21,    -1,    -1,   117,    40,    -1,    -1,    -1,   122,
      45,    46,    47,    48,    49,    18,    -1,    52,    21,    -1,
      40,    -1,    -1,    -1,    -1,    45,    46,    47,    48,    49,
      -1,    -1,    52,    -1,   914,    -1,    -1,    40,   918,    -1,
      -1,    -1,    45,    46,    47,    48,    49,    -1,    -1,    52,
      -1,   931,   180,   181,   182,   183,   184,   185,   186,   187,
      11,    12,    13,    -1,    -1,    16,   194,   180,   181,   182,
     183,   184,   185,   186,   187,    -1,     4,    -1,    -1,    -1,
      -1,   194,   117,    11,    12,    13,   966,   122,    16,    -1,
      -1,    -1,    -1,   973,    -1,   975,    18,   117,    -1,    21,
      -1,    -1,   122,    -1,    -1,    33,    18,    19,    -1,   989,
      -1,    -1,   992,   993,   117,   995,    -1,    -1,    40,   122,
      -1,    72,    73,    45,    46,    47,    48,    49,     4,    -1,
      52,    -1,  1012,    -1,    -1,    11,    12,    13,    -1,    -1,
      16,    -1,    -1,    -1,    -1,   180,   181,   182,   183,   184,
     185,   186,   187,    -1,    -1,    -1,    -1,    -1,    -1,   194,
     180,   181,   182,   183,   184,   185,   186,   187,    11,    12,
      13,    -1,    -1,    16,   194,    -1,    19,   180,   181,   182,
     183,   184,   185,   186,   187,     4,    98,    99,    -1,    -1,
     102,   194,    11,    12,    13,   117,    -1,    16,    -1,    -1,
     122,    -1,    -1,    -1,   116,   117,   157,   158,    -1,    -1,
     122,   162,    -1,   164,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   172,   173,   174,   175,    -1,    -1,   178,   179,   157,
     158,    -1,    -1,    -1,   162,    -1,   164,    -1,    -1,    -1,
      -1,    -1,   154,    -1,   172,   173,   174,   175,    -1,    -1,
     178,   179,    -1,    18,    -1,    -1,    -1,    -1,   180,   181,
     182,   183,   184,   185,   186,   187,    11,    12,    13,    -1,
      -1,    16,   194,    -1,    19,    -1,    -1,    -1,    -1,    -1,
      -1,   157,   158,    -1,    -1,    -1,   162,    -1,   164,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   172,   173,   174,   175,
      -1,    -1,   178,   179,    11,    12,    13,    -1,    -1,    16,
      75,    -1,    19,    -1,   157,   158,    -1,    -1,    -1,   162,
      -1,   164,    -1,    11,    12,    13,    -1,    -1,    16,   172,
     173,   174,   175,    98,    99,   178,   179,   102,   157,   158,
      -1,    -1,    -1,   162,    -1,   164,    -1,    -1,    -1,    -1,
      -1,   116,   117,   172,   173,   174,   175,   122,    -1,   178,
     179,    11,    12,    13,    -1,    -1,    16,    -1,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      11,    12,    13,    -1,    72,    16,    -1,    -1,    -1,   154,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    11,    12,    13,    -1,    -1,    16,    -1,
      -1,    -1,   157,   158,    -1,    -1,    -1,   162,    -1,   164,
      -1,    -1,    -1,    31,    -1,    -1,    -1,   172,   173,   174,
     175,    -1,    -1,   178,   179,    -1,    11,    12,    13,    -1,
      -1,    16,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     157,   158,    -1,    -1,    -1,   162,    31,   164,    -1,    11,
      12,    13,    -1,    -1,    16,   172,   173,   174,   175,   157,
     158,   178,   179,    -1,   162,    -1,   164,    -1,    -1,    31,
      -1,    -1,    -1,    -1,   172,   173,   174,   175,    -1,    -1,
     178,   179,    -1,    11,    12,    13,    -1,    -1,    16,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   157,   158,    -1,
      -1,    -1,   162,    31,   164,    -1,    11,    12,    13,    -1,
      -1,    16,   172,   173,   174,   175,   157,   158,   178,   179,
      -1,   162,    -1,   164,    -1,    -1,    31,    -1,    -1,    -1,
      -1,   172,   173,   174,   175,    -1,    -1,   178,   179,   157,
     158,    -1,    -1,    -1,    -1,    -1,   164,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   172,   173,   174,   175,    -1,    -1,
     178,   179,    -1,    -1,     7,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   157,   158,    -1,    18,    -1,    -1,    -1,   164,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   172,   173,   174,
     175,    -1,    -1,   178,   179,   157,   158,    -1,    -1,    -1,
      43,    -1,   164,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     172,   173,   174,   175,    -1,    -1,   178,   179,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   157,
     158,    -1,    -1,    76,    77,    -1,   164,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   172,   173,   174,   175,    -1,    -1,
     178,   179,   157,   158,    -1,    -1,    -1,   100,   101,   164,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   172,   173,   174,
     175,    -1,    -1,   178,   179,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,   139,   140,   141,   142,
     143,   144,   145,   146,     7,    18,    -1,    -1,    21,    -1,
      23,    24,    25,    26,    27,    18,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     173,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      43,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    61,    62,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    70,    -1,    -1,
      -1,    74,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    76,    77,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    18,    -1,    -1,    -1,
      -1,    -1,   105,    -1,    -1,    -1,    -1,   100,   101,    -1,
     113,    -1,   115,    -1,   117,    -1,    -1,    -1,    -1,    -1,
      -1,    43,    -1,    -1,    -1,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,   139,   140,   141,   142,
     143,   144,   145,   146,    76,    77,     7,    -1,    -1,    -1,
      -1,    -1,   165,    -1,    -1,    -1,    -1,    18,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,   101,
     173,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    43,    -1,    -1,    -1,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,   140,   141,
     142,   143,   144,   145,   146,    76,    77,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,    -1,
      -1,    -1,    -1,    -1,    -1,   167,    -1,    -1,    -1,   100,
     101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    43,    -1,    -1,    -1,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   138,   139,   140,
     141,   142,   143,   144,   145,   146,    76,    77,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,
      -1,    -1,    -1,    -1,    -1,    -1,   167,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   139,
     140,   141,   142,   143,   144,   145,   146,    76,    77,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   165,    -1,    -1,    -1,    -1,
      -1,   100,   101,    -1,    -1,    -1,    18,    -1,    -1,    21,
      -1,    23,    24,    25,    26,    27,    -1,    -1,    -1,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
     139,   140,   141,   142,   143,   144,   145,   146,    18,    -1,
      -1,    21,    -1,    -1,    -1,    -1,    -1,    -1,    70,    -1,
      30,    -1,    74,    -1,    -1,    -1,   165,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   105,    -1,    -1,    -1,    -1,    68,   111,
      70,   113,    -1,   115,    74,   117,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,    93,    94,    95,    -1,    -1,    98,    99,
      -1,    -1,   102,   103,   104,   105,   106,   107,   108,   151,
     152,   153,    -1,    -1,    -1,    -1,    18,   117,    -1,    21,
      -1,    -1,   122,   165,    -1,    -1,    -1,    -1,    30,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   147,   148,   149,
     150,    -1,    -1,    -1,   154,    -1,    18,    -1,    -1,    21,
      -1,    23,    24,    25,    26,    27,    68,    -1,    70,    -1,
      -1,    -1,    74,   173,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    86,    87,    88,    89,    90,    91,
      92,    93,    94,    95,    -1,    -1,    98,    99,     7,    -1,
     102,   103,   104,   105,   106,   107,   108,    -1,    70,    18,
      -1,    -1,    74,    -1,    -1,   117,    -1,    -1,    -1,    -1,
     122,    -1,    -1,    -1,    33,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   105,    -1,   147,   148,   149,   150,    -1,
      -1,   113,   154,   115,    -1,   117,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    76,    77,     7,
      -1,   173,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      18,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    97,    -1,
      -1,   100,   101,    -1,    -1,    33,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   165,    -1,    43,    -1,    -1,    -1,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
     139,   140,   141,   142,   143,   144,   145,   146,    76,    77,
       7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    18,   161,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   100,   101,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,   139,   140,   141,   142,   143,   144,   145,   146,    76,
      77,     7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    18,   161,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   100,   101,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,
      -1,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,   139,   140,   141,   142,   143,   144,   145,   146,
      76,    77,     7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    18,   161,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,
      -1,    -1,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,   139,   140,   141,   142,   143,   144,   145,
     146,    76,    77,     7,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    18,   161,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,
      -1,    -1,    -1,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,   144,
     145,   146,    76,    77,     7,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    18,   161,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   100,   101,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      43,    -1,    -1,    -1,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,   139,   140,   141,   142,   143,
     144,   145,   146,    76,    77,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   159,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,   101,    18,
      -1,    -1,    21,    -1,    23,    24,    25,    26,    27,    -1,
      -1,    -1,    -1,    -1,    -1,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,   139,   140,   141,   142,
     143,   144,   145,   146,    -1,     7,    -1,    -1,    -1,    -1,
      -1,    70,   155,    -1,    -1,    74,    18,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    28,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    43,    -1,    -1,    -1,    -1,   105,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   113,    -1,   115,    -1,   117,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    71,
      -1,    -1,    -1,    -1,    76,    77,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,   101,
      -1,    -1,    -1,    -1,    -1,    -1,   165,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,   140,   141,
     142,   143,   144,   145,   146,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    18,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    43,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      72,    73,    -1,    -1,    76,    77,     7,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,   101,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    43,    -1,    -1,    -1,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,   140,   141,
     142,   143,   144,   145,   146,    76,    77,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,    19,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,
     101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    43,    -1,    -1,    -1,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   138,   139,   140,
     141,   142,   143,   144,   145,   146,    76,    77,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   139,
     140,   141,   142,   143,   144,   145,   146,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    43,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    71,    -1,    -1,    -1,    -1,    76,    77,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,
      19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   139,
     140,   141,   142,   143,   144,   145,   146,    76,    77,     7,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      18,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   100,   101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
     139,   140,   141,   142,   143,   144,   145,   146,    76,    77,
       7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    18,    19,    -1,    -1,    -1,    -1,    -1,    -1,    97,
      -1,    -1,   100,   101,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,   139,   140,   141,   142,   143,   144,   145,   146,    76,
      77,     7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    18,    19,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   100,   101,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,
      -1,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,   139,   140,   141,   142,   143,   144,   145,   146,
      76,    77,     7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    18,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    33,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,
      -1,    -1,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,   139,   140,   141,   142,   143,   144,   145,
     146,    76,    77,     7,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    18,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,
      -1,    -1,    -1,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,   144,
     145,   146,    76,    77,     7,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    18,    -1,    -1,    -1,    22,
      -1,    -1,    -1,    -1,    -1,    -1,   100,   101,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      43,    -1,    -1,    -1,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,   139,   140,   141,   142,   143,
     144,   145,   146,    76,    77,     7,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    18,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,   101,    31,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    43,    -1,    -1,    -1,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,   139,   140,   141,   142,
     143,   144,   145,   146,    76,    77,     7,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,   101,
      31,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    43,    -1,    -1,    -1,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,   140,   141,
     142,   143,   144,   145,   146,    76,    77,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   100,
     101,    31,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    43,    -1,    -1,    -1,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   138,   139,   140,
     141,   142,   143,   144,   145,   146,    76,    77,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   139,
     140,   141,   142,   143,   144,   145,   146,     7,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    43,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    71,    -1,    -1,    -1,    -1,    76,    77,     7,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    18,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    38,
      -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   139,
     140,   141,   142,   143,   144,   145,   146,    76,    77,     7,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      18,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   100,   101,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
     139,   140,   141,   142,   143,   144,   145,   146,    76,    77,
       7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    18,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   100,   101,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,    -1,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,   139,   140,   141,   142,   143,   144,   145,   146,    76,
      77,     7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    18,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    28,   100,   101,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,
      -1,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,   139,   140,   141,   142,   143,   144,   145,   146,
      76,    77,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,   139,   140,   141,   142,   143,   144,   145,
     146,     7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    18,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    71,    -1,    -1,    -1,    -1,
      76,    77,     7,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    18,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    43,    -1,
      -1,    -1,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,   139,   140,   141,   142,   143,   144,   145,
     146,    76,    77,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    18,   100,   101,    21,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    30,    31,    -1,    -1,
      -1,    -1,    -1,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,   144,
     145,   146,    -1,    -1,    68,    -1,    70,    -1,    -1,    -1,
      74,    -1,    76,    77,    78,    79,    80,    81,    82,    83,
      84,    85,    86,    87,    88,    89,    90,    91,    92,    93,
      94,    95,    -1,    97,    98,    99,    -1,    -1,   102,   103,
     104,   105,   106,   107,   108,    18,    -1,    -1,    21,    -1,
      -1,    -1,    -1,   117,    -1,    -1,    -1,    30,   122,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   147,   148,   149,   150,    -1,    61,    62,
     154,    -1,    -1,    -1,    -1,    68,    -1,    70,    -1,    -1,
      -1,    74,    -1,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    86,    87,    88,    89,    90,    91,    92,
      93,    94,    95,    -1,    -1,    98,    99,    -1,    -1,   102,
     103,   104,   105,   106,   107,   108,    18,    -1,    -1,    21,
      22,    -1,    -1,    -1,   117,    -1,    -1,    -1,    30,   122,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   147,   148,   149,   150,    -1,    -1,
      -1,   154,    -1,    -1,    -1,    -1,    68,    -1,    70,    -1,
      -1,    -1,    74,    -1,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    86,    87,    88,    89,    90,    91,
      92,    93,    94,    95,    -1,    -1,    98,    99,    -1,    -1,
     102,   103,   104,   105,   106,   107,   108,    18,    19,    -1,
      21,    -1,    -1,    -1,    -1,   117,    -1,    -1,    -1,    30,
     122,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   147,   148,   149,   150,    -1,
      -1,    -1,   154,    -1,    -1,    -1,    -1,    68,    -1,    70,
      -1,    -1,    -1,    74,    -1,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    86,    87,    88,    89,    90,
      91,    92,    93,    94,    95,    -1,    -1,    98,    99,    -1,
      -1,   102,   103,   104,   105,   106,   107,   108,    18,    -1,
      -1,    21,    -1,    -1,    -1,    -1,   117,    -1,    -1,    -1,
      30,   122,    -1,    -1,    -1,    -1,    -1,    -1,    38,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   147,   148,   149,   150,
      -1,    -1,    -1,   154,    -1,    -1,    -1,    -1,    68,    -1,
      70,    -1,    -1,    -1,    74,    -1,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,    93,    94,    95,    -1,    -1,    98,    99,
      -1,    -1,   102,   103,   104,   105,   106,   107,   108,    18,
      -1,    -1,    21,    -1,    -1,    -1,    -1,   117,    -1,    -1,
      -1,    30,   122,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   147,   148,   149,
     150,    -1,    -1,    -1,   154,    -1,    -1,    -1,    -1,    68,
      -1,    70,    -1,    -1,    -1,    74,    -1,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    86,    87,    88,
      89,    90,    91,    92,    93,    94,    95,    96,    -1,    98,
      99,    -1,    -1,   102,   103,   104,   105,   106,   107,   108,
      18,    19,    -1,    21,    -1,    -1,    -1,    -1,   117,    -1,
      -1,    -1,    30,   122,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   147,   148,
     149,   150,    -1,    -1,    -1,   154,    -1,    -1,    -1,    -1,
      68,    -1,    70,    -1,    -1,    -1,    74,    -1,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    86,    87,
      88,    89,    90,    91,    92,    93,    94,    95,    -1,    -1,
      98,    99,    -1,    -1,   102,   103,   104,   105,   106,   107,
     108,    18,    -1,    -1,    21,    -1,    -1,    -1,    -1,   117,
      -1,    -1,    -1,    30,   122,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   147,
     148,   149,   150,    -1,    -1,    -1,   154,    -1,    -1,    -1,
      -1,    68,    -1,    70,    -1,    -1,    -1,    74,    -1,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    86,
      87,    88,    89,    90,    91,    92,    93,    94,    95,    96,
      -1,    98,    99,    -1,    -1,   102,   103,   104,   105,   106,
     107,   108,    18,    19,    -1,    21,    -1,    -1,    -1,    -1,
     117,    -1,    -1,    -1,    30,   122,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     147,   148,   149,   150,    -1,    -1,    -1,   154,    -1,    -1,
      -1,    -1,    68,    -1,    70,    -1,    -1,    -1,    74,    -1,
      76,    77,    78,    79,    80,    81,    82,    83,    84,    85,
      86,    87,    88,    89,    90,    91,    92,    93,    94,    95,
      -1,    -1,    98,    99,    -1,    -1,   102,   103,   104,   105,
     106,   107,   108,    18,    -1,    -1,    21,    -1,    -1,    -1,
      -1,   117,    -1,    -1,    -1,    30,   122,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   147,   148,   149,   150,    -1,    -1,    -1,   154,    -1,
      -1,    -1,    -1,    68,    -1,    70,    -1,    -1,    -1,    74,
      -1,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    86,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    -1,    -1,    98,    99,    -1,    -1,   102,   103,   104,
     105,   106,   107,   108,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   117,    -1,    -1,    -1,    -1,   122,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   147,   148,   149,   150,    -1,    -1,    -1,   154
  };
  }


  static class YyTokenClass {
  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  public static final short
  yystos_[] =
  {
         0,     3,     5,    35,    36,    37,    56,    57,   198,   199,
     200,   201,   202,   228,   233,   237,   238,   239,   243,   261,
     265,   117,   117,   117,   229,   230,   232,   373,   117,   234,
     235,   188,   189,   190,   191,   244,   245,   246,   246,   262,
     263,   246,   266,   267,   268,   269,     0,   200,   199,   239,
       7,     7,   161,   165,    38,     7,   234,    38,   117,    18,
      98,    99,   102,   117,   122,   154,   257,   264,   300,   301,
     359,   362,   363,   364,    38,   117,   270,    38,     9,   240,
       9,    11,    12,    13,    16,    18,    21,   117,   203,   204,
     231,   232,   232,   373,    18,    21,    40,    45,    46,    47,
      48,    49,    52,   117,   180,   181,   182,   183,   184,   185,
     186,   187,   194,   247,   248,   249,   250,   251,   253,   254,
     255,   256,   257,   331,   229,    30,   117,   166,   236,   245,
       7,    39,    18,    21,    30,    68,    70,    74,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    86,    87,
      88,    89,    90,    91,    92,    93,    94,    95,    98,    99,
     103,   104,   105,   106,   107,   108,   117,   147,   148,   149,
     150,   299,   300,   304,   306,   309,   310,   311,   312,   313,
     314,   315,   316,   317,   318,   321,   322,   323,   324,   325,
     326,   327,   328,   329,   330,   335,    18,    18,     7,   165,
     117,     7,   165,   262,    18,   165,   273,   266,    63,    67,
     241,   242,   243,   261,   265,   282,   294,     8,    10,   205,
     206,   207,   242,    30,    30,    30,    30,   117,   231,    30,
      18,   161,    11,    12,    13,    16,   157,   158,   164,   172,
     173,   174,   175,   178,   179,   221,   222,   223,     8,    38,
     247,   252,   247,   117,   247,   247,   247,   247,   247,    28,
      50,    28,    43,    50,   100,   117,   373,   117,   373,   135,
     136,   137,   247,   117,   247,   258,   259,   299,    22,   298,
     299,    31,    97,   298,   299,   319,   320,   117,   264,   270,
     302,   303,   299,   299,   299,   299,   299,   299,   299,   299,
     299,   299,   299,   299,   299,   299,   299,   299,   299,   299,
     299,   299,   299,   299,    18,    18,   116,   117,   358,   359,
     361,   367,   372,   117,    18,   249,   331,    18,    18,   110,
     358,   360,   368,   369,   370,   371,   368,   358,   365,   366,
     367,     7,    18,    19,    43,    76,    77,   100,   101,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
     139,   140,   141,   142,   143,   144,   145,   146,   360,    19,
     360,   299,   247,   124,   299,   247,    19,   274,   360,   247,
     254,   117,   276,   246,   283,   284,   285,   286,    55,   246,
     295,   296,   297,     4,   241,    18,    21,    23,    24,    25,
      26,    27,    70,    74,   105,   113,   115,   117,   165,   211,
     216,   217,   220,   331,   332,   333,   336,   337,   340,   344,
     345,   347,   348,   350,   353,   354,   355,   356,   117,   192,
     208,   209,     8,   206,   231,   231,   231,   231,     8,   236,
     299,   117,   204,   204,   204,   204,   236,   117,   224,   225,
     226,   204,   173,   299,   236,   204,   236,   299,   299,   204,
     231,    19,    33,    19,    33,    22,    41,    44,    44,   247,
     247,   247,   247,   247,   117,   161,    31,    33,   171,   227,
     167,   236,   236,   236,    55,   260,    53,   165,   260,   258,
      19,    22,    33,   161,    31,    31,    33,    97,   161,    31,
      33,   161,   165,    69,   161,    71,   165,   298,   299,   298,
     165,   161,   155,    18,   299,    18,    18,   298,   331,   161,
     131,   165,   155,   161,   155,   131,   155,   299,    19,   298,
     299,   299,   299,   299,   117,   102,   299,   299,   299,   299,
     299,   299,   299,   299,   299,   299,   299,   299,   299,   299,
     299,   299,   299,   299,   299,   299,   299,   299,   299,   299,
     299,   299,   299,   299,   299,    19,    19,     7,     7,    19,
     165,   117,   165,    58,   277,   278,   117,    38,   299,   117,
     343,    38,   111,   117,   151,   152,   153,   211,   218,   219,
     231,   341,    64,   289,   290,   357,   299,   299,   299,    18,
      38,   299,    28,   168,   169,   171,   212,   213,   299,     4,
      11,    12,    13,    16,   157,   162,   164,   172,   173,   174,
     175,   178,   179,   221,    18,    18,    29,   112,   100,     7,
     117,   209,   211,     8,     8,     8,     8,   117,    22,    19,
     160,   227,    33,   159,   161,    29,   204,   173,   164,   247,
     247,   258,   247,   247,   368,   299,    33,   171,   358,   247,
     247,   358,   366,   298,   368,   299,    96,   368,   299,   319,
     320,   299,   302,   299,   307,   308,   360,    19,    19,   247,
     372,   299,   298,   161,   299,   299,    19,   161,   360,   299,
     247,   299,   369,   299,   299,   299,    19,   161,   299,   299,
     247,    18,   271,   247,   299,    59,   280,   165,   273,   283,
     165,   295,   342,   343,   232,   232,   232,    19,     8,    38,
       8,   211,    65,    66,   291,   292,   293,   277,    22,    28,
      71,   165,   332,   345,   346,   354,   211,    98,    99,   117,
     214,   299,   299,    28,   212,   155,   211,   211,   211,   211,
     193,   236,   193,   211,   173,   299,   236,   211,   299,   299,
     299,   298,    19,   298,   331,   333,   354,   299,   117,   210,
     211,   231,     7,     4,   204,   204,   204,   204,    19,   231,
     204,   368,   224,   226,   204,   236,     4,     8,   167,   368,
     299,    54,    22,   155,    31,   155,   161,    31,   155,    72,
      73,   305,     4,    75,   307,    28,    19,   247,    19,    19,
     299,    96,   161,    19,   360,    54,   161,   299,    18,   247,
     287,   275,   276,   247,     8,   161,   211,   218,   211,    19,
     291,   331,   334,   280,   211,   211,   351,   352,   360,    38,
      19,    18,    18,   165,   211,   211,    33,   160,    33,   211,
     173,    33,    19,    19,    18,     8,   193,    31,    31,    31,
      31,    18,     8,     8,   159,   176,   299,     8,   167,   299,
     299,   299,   299,   299,   299,   299,    28,   299,    19,    19,
     161,   274,    19,    61,    62,   272,   299,   276,    19,   114,
     117,   289,    69,   112,   342,    19,    19,   161,   165,     4,
      33,   338,   339,    72,    73,   349,     4,   161,    28,   346,
      19,   214,   215,    19,   215,   299,   193,   236,   211,   193,
     211,   124,    19,   298,   211,   299,   204,   299,   204,   167,
     299,    22,    31,    31,    31,    71,   299,    38,   299,   271,
     277,   114,    18,   247,   271,   277,   299,   299,    18,   334,
     247,   299,     4,   338,   299,   211,    73,    75,   351,   211,
     161,    19,    19,   160,    33,   163,   211,    19,    19,    31,
     159,   167,   299,    19,   279,   280,    18,   247,    19,    54,
     280,   298,    28,    71,   211,    28,   215,   211,   193,   211,
     305,    60,   281,    19,    61,    62,   211,   288,    19,   211,
     211,   211,   160,   331,   289,   349,   211,   277,   279
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
     445,   446,   447,   448,   449,   450,   451
  };
  }

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */

  private static final short
  yyr1_[] =
  {
         0,   197,   198,   198,   198,   199,   199,   200,   200,   200,
     200,   201,   202,   203,   203,   204,   204,   204,   204,   204,
     204,   204,   204,   204,   204,   204,   204,   204,   204,   204,
     204,   204,   204,   204,   204,   204,   204,   204,   204,   205,
     205,   206,   206,   207,   207,   208,   208,   209,   210,   210,
     211,   211,   211,   211,   211,   211,   211,   211,   211,   211,
     211,   211,   211,   211,   211,   211,   211,   211,   211,   211,
     211,   211,   211,   211,   211,   212,   212,   213,   213,   213,
     213,   214,   214,   214,   214,   214,   215,   215,   216,   216,
     216,   216,   216,   216,   217,   218,   218,   219,   219,   219,
     220,   220,   221,   221,   222,   223,   223,   224,   224,   225,
     226,   226,   227,   227,   228,   229,   229,   230,   230,   231,
     231,   232,   232,   233,   233,   234,   234,   235,   236,   236,
     236,   236,   236,   236,   236,   236,   236,   236,   237,   238,
     238,   239,   239,   239,   240,   241,   241,   242,   242,   242,
     242,   242,   243,   243,   243,   244,   244,   245,   245,   245,
     245,   246,   246,   246,   246,   246,   247,   247,   247,   247,
     247,   247,   247,   247,   247,   247,   247,   247,   247,   247,
     247,   248,   249,   249,   249,   249,   249,   249,   249,   249,
     250,   251,   252,   252,   253,   254,   254,   255,   255,   256,
     256,   257,   258,   258,   259,   259,   259,   260,   261,   262,
     262,   262,   263,   264,   264,   264,   264,   265,   265,   266,
     266,   266,   267,   267,   268,   269,   270,   271,   271,   271,
     272,   272,   272,   273,   273,   274,   274,   275,   275,   276,
     276,   277,   277,   278,   279,   279,   280,   281,   281,   282,
     282,   283,   283,   284,   284,   285,   286,   287,   287,   287,
     287,   288,   288,   288,   289,   289,   290,   291,   291,   292,
     292,   293,   293,   294,   294,   295,   295,   295,   296,   296,
     297,   298,   298,   299,   299,   299,   299,   299,   299,   299,
     299,   299,   299,   299,   299,   299,   299,   299,   299,   299,
     299,   299,   299,   299,   299,   299,   299,   299,   299,   299,
     299,   299,   299,   300,   300,   301,   301,   302,   302,   303,
     303,   304,   305,   305,   306,   307,   307,   307,   308,   309,
     309,   309,   309,   309,   309,   309,   309,   309,   309,   309,
     309,   309,   309,   309,   309,   309,   309,   309,   309,   310,
     310,   310,   310,   310,   310,   310,   310,   310,   310,   310,
     310,   310,   310,   310,   310,   310,   310,   310,   310,   310,
     310,   310,   310,   310,   310,   310,   310,   310,   310,   310,
     310,   310,   311,   311,   311,   312,   312,   313,   313,   314,
     315,   315,   316,   316,   317,   318,   318,   319,   319,   320,
     321,   321,   322,   323,   324,   324,   325,   326,   327,   328,
     329,   329,   329,   330,   331,   331,   332,   332,   333,   333,
     333,   334,   334,   335,   336,   336,   336,   336,   336,   336,
     336,   337,   337,   338,   339,   339,   340,   340,   341,   342,
     342,   343,   343,   343,   344,   344,   345,   346,   346,   347,
     348,   348,   349,   349,   350,   351,   351,   351,   352,   353,
     353,   354,   354,   354,   354,   355,   355,   356,   357,   358,
     358,   359,   359,   359,   360,   360,   361,   361,   362,   362,
     363,   364,   364,   365,   365,   366,   367,   368,   368,   369,
     369,   370,   371,   372,   372,   373,   373
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     2,     1,     1,     2,     1,     1,     1,
       1,     4,     4,     3,     1,     5,     4,     3,     3,     3,
       5,     7,     3,     3,     4,     5,     3,     3,     3,     3,
       8,     4,     1,     2,     6,     6,     6,     8,     6,     1,
       2,     1,     1,     2,     5,     1,     2,     3,     1,     3,
       1,     1,     1,     1,     2,     3,     4,     4,     3,     3,
       3,     4,     5,     3,     6,     3,     3,     3,     2,     1,
       1,     1,     1,     1,     1,     1,     2,     2,     4,     2,
       2,     1,     4,     3,     4,     3,     1,     3,     7,     3,
       7,     3,     9,     5,     5,     1,     3,     2,     2,     2,
       8,     4,     1,     1,     3,     5,     7,     1,     3,     3,
       1,     2,     2,     3,     2,     1,     3,     1,     1,     1,
       3,     3,     3,     1,     2,     1,     2,     3,     1,     3,
       3,     3,     3,     3,     5,     6,     7,     8,     1,     1,
       2,     1,     1,     1,     3,     1,     2,     1,     1,     1,
       1,     1,     1,     3,     2,     3,     1,     5,     4,     4,
       5,     1,     1,     1,     1,     0,     1,     1,     1,     5,
       3,     1,     1,     2,     2,     2,     4,     4,     1,     1,
       1,     3,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     3,     3,     3,     3,     1,     1,     3,     3,     3,
       3,     3,     1,     2,     1,     3,     3,     4,     2,     1,
       2,     3,     2,     5,     5,     3,     3,     1,     2,     1,
       2,     3,     1,     1,     6,     2,    10,     2,     3,     4,
       1,     1,     1,     2,     3,     5,     3,     0,     1,     3,
       5,     1,     0,     2,     1,     0,     2,     2,     0,     2,
       1,     1,     3,     1,     1,    11,     7,     3,     4,     4,
       5,     1,     1,     1,     1,     0,     2,     1,     2,     2,
       4,     1,     1,     2,     1,     1,     2,     3,     2,     1,
       2,     1,     3,     1,     3,     4,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     6,
       1,     1,     1,     1,     1,     1,     1,     1,     3,     1,
       1,     5,     2,     5,     5,     1,     4,     2,     4,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     4,     4,     4,     2,     3,     5,     7,     7,
       2,     3,     5,     7,     8,     3,     3,     1,     3,     3,
       5,     7,     4,     4,     4,     3,     3,     3,     4,     5,
       5,     5,     6,     4,     1,     3,     1,     1,     1,     1,
       3,     1,     3,     2,     1,     1,     1,     1,     1,     1,
       1,     5,     6,     4,     1,     2,     3,     4,     3,     1,
       3,     3,     5,     5,     1,     1,     3,     1,     3,     4,
       7,     6,     5,     4,     5,     1,     5,     3,     3,     1,
       3,     4,     3,     6,     5,     2,     2,     3,     3,     1,
       1,     1,     1,     1,     1,     3,     1,     1,     1,     3,
       4,     3,     4,     1,     1,     3,     3,     1,     3,     1,
       1,     3,     3,     1,     3,     1,     3
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "CLASS", "END", "PROCESS", "INITIAL",
  "EQUALS", "AT", "BEGIN", "CSP_ACTIONS", "CSPSEQ", "CSPINTCH", "CSPEXTCH",
  "CSPLCHSYNC", "CSPRCHSYNC", "CSPINTERLEAVE", "CSPHIDE", "LPAREN",
  "RPAREN", "CSPRENAME", "LSQUARE", "RSQUARE", "CSPSKIP", "CSPSTOP",
  "CSPCHAOS", "CSPDIV", "CSPWAIT", "RARROW", "LARROW", "LCURLY", "RCURLY",
  "CSPAND", "BAR", "DBAR", "CHANNELS", "CHANSETS", "TYPES", "SEMI",
  "VDMRECORDDEF", "VDMCOMPOSE", "OF", "VDMTYPEUNION", "STAR", "TO",
  "VDMINMAPOF", "VDMMAPOF", "VDMSEQOF", "VDMSEQ1OF", "VDMSETOF",
  "VDMPFUNCARROW", "VDMTFUNCARROW", "VDMUNITTYPE", "VDMTYPENCMP",
  "DEQUALS", "VDMINV", "VALUES", "FUNCTIONS", "PRE", "POST", "MEASURE",
  "VDM_SUBCLASSRESP", "VDM_NOTYETSPEC", "OPERATIONS", "VDM_FRAME",
  "VDM_RD", "VDM_WR", "STATE", "LET", "IN", "IF", "THEN", "ELSEIF", "ELSE",
  "CASES", "OTHERS", "PLUS", "MINUS", "ABS", "FLOOR", "NOT", "CARD",
  "POWER", "DUNION", "DINTER", "HD", "TL", "LEN", "ELEMS", "INDS",
  "REVERSE", "DCONC", "DOM", "RNG", "MERGE", "INVERSE", "ELLIPSIS",
  "MAPLETARROW", "MKUNDER", "MKUNDERNAME", "DOT", "DOTHASH", "NUMERAL",
  "LAMBDA", "NEW", "SELF", "ISUNDER", "PREUNDER", "ISOFCLASS", "BACKTICK",
  "TILDE", "DCL", "ASSIGN", "ATOMIC", "OPERATIONARROW", "RETURN",
  "VDMDONTCARE", "IDENTIFIER", "DIVIDE", "DIV", "REM", "MOD", "LT", "LTE",
  "GT", "GTE", "NEQ", "OR", "AND", "IMPLY", "BIMPLY", "INSET", "NOTINSET",
  "SUBSET", "PROPER_SUBSET", "UNION", "SETDIFF", "INTER", "CONC",
  "OVERWRITE", "MAPMERGE", "DOMRES", "VDM_MAP_DOMAIN_RESTRICT_BY",
  "RNGRES", "RNGSUB", "COMP", "ITERATE", "FORALL", "EXISTS", "EXISTS1",
  "STRING", "PARAM_VRES", "PARAM_RES", "PARAM_VAL", "HEX_LITERAL", "AMP",
  "CSPBARGT", "CSPLSQUAREBAR", "DLSQUARE", "DRSQUARE", "CSPBARRSQUARE",
  "COMMA", "CSPLSQUAREDBAR", "CSPDBARRSQUARE", "CSPDBAR", "COLON",
  "CHANSET_SETEXP_BEGIN", "CHANSET_SETEXP_END", "CSP_CHANNEL_READ",
  "CSP_CHANNEL_WRITE", "CSP_OPS_COM", "CSP_CHANNEL_DOT", "CSP_SLASH",
  "CSP_BACKSLASH", "CSPLSQUAREGT", "CSP_LSQUARE", "CSP_RSQUARE", "CSP_GT",
  "CSP_ENDBY", "CSP_STARTBY", "TBOOL", "TNAT", "TNAT1", "TINT", "TRAT",
  "TREAL", "TCHAR", "TTOKEN", "PRIVATE", "PROTECTED", "PUBLIC", "LOGICAL",
  "nameset", "namesetExpr", "typeVarIdentifier", "UMINUS", "UPLUS",
  "$accept", "sourceFile", "programParagraphList", "programParagraph",
  "classDecl", "processDecl", "processDef", "process",
  "processParagraphList", "processParagraph", "actionParagraph",
  "actionDefinitionList", "actionDefinition", "paragraphAction", "action",
  "communicationParameterUseList", "communicationParameter", "parameter",
  "paramList", "parallelAction", "parametrisedAction",
  "parametrisationList", "parametrisation", "instantiatedAction",
  "renameExpression", "renameEnumeration", "renameComprehension",
  "renameList", "renamePair", "channelEvent", "dotted_expression",
  "channelDecl", "channelDef", "channelNameDecl", "declaration",
  "singleTypeDecl", "chansetDecl", "chansetDefinitionList",
  "chansetDefinition", "chansetExpr", "globalDecl",
  "globalDefinitionBlock", "globalDefinitionBlockAlternative", "classBody",
  "classDefinitionBlock", "classDefinitionBlockAlternative", "typeDefs",
  "typeDefList", "typeDef", "qualifier", "type", "bracketedType",
  "basicType", "quoteType", "optionalType", "unionType", "productType",
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
  "mode", "stateDefs", "stateDefList", "stateDef", "invariantDef",
  "expressionList", "expression", "symbolicLiteral", "numericLiteral",
  "localDefList", "localDef", "ifExpr", "elseExprs", "casesExpr",
  "casesExprAltList", "casesExprAlt", "unaryExpr", "binaryExpr",
  "quantifiedExpr", "setEnumeration", "setComprehension", "setRangeExpr",
  "sequenceEnumeration", "sequenceComprehension", "subsequence",
  "mapEnumeration", "mapletList", "maplet", "mapComprehension",
  "tupleConstructor", "recordConstructor", "apply", "fieldSelectOrName",
  "tupleSelect", "lambdaExpr", "newExpr", "generalIsExpr",
  "preconditionExpr", "name", "designator", "primary", "nameList",
  "oldName", "controlStatements", "nonDeterministicIfStatement",
  "nonDeterministicIfAlt", "nonDeterministicIfAltList", "blockStatement",
  "dclStatement", "assignmentDefList", "assignmentDef",
  "generalAssignStatement", "assignStatement", "assignStatementList",
  "multiAssignStatement", "ifStatement", "elseStatements",
  "casesStatement", "casesStatementAltList", "casesStatementAlt",
  "callStatement", "call", "returnStatement", "specificationStatement",
  "implicitOperationBody", "pattern", "patternLessID", "patternList",
  "patternIdentifier", "matchValue", "tuplePattern", "recordPattern",
  "bind", "setBind", "typeBind", "bindList", "multipleBind",
  "multipleSetBind", "multipleTypeBind", "typeBindList", "identifierList", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final short yyrhs_[] =
  {
       198,     0,    -1,   199,    -1,   237,   199,    -1,   237,    -1,
     200,    -1,   199,   200,    -1,   201,    -1,   202,    -1,   228,
      -1,   233,    -1,     3,   117,     7,   240,    -1,     5,   117,
       7,   203,    -1,   231,     8,   204,    -1,   204,    -1,     9,
     205,     8,   211,     4,    -1,     9,     8,   211,     4,    -1,
     204,    11,   204,    -1,   204,    13,   204,    -1,   204,    12,
     204,    -1,   204,   157,   236,   160,   204,    -1,   204,   175,
     236,   164,   236,   176,   204,    -1,   204,   164,   204,    -1,
     204,    16,   204,    -1,   204,   172,   173,   204,    -1,   204,
     172,   299,   173,   204,    -1,   204,   174,   204,    -1,   204,
     173,   236,    -1,   204,   179,   299,    -1,   204,   178,   299,
      -1,    18,   231,     8,   117,    19,    18,   299,    19,    -1,
     117,    18,   299,    19,    -1,   117,    -1,   204,   221,    -1,
      11,    30,   231,     8,   204,    31,    -1,    12,    30,   231,
       8,   204,    31,    -1,    13,    30,   231,     8,   204,    31,
      -1,    21,    30,   236,    22,   231,     8,   204,    31,    -1,
      16,    30,   231,     8,   204,    31,    -1,   206,    -1,   205,
     206,    -1,   242,    -1,   207,    -1,    10,   208,    -1,    10,
     192,   117,     7,   193,    -1,   209,    -1,   208,   209,    -1,
     117,     7,   210,    -1,   211,    -1,   231,     8,   211,    -1,
      23,    -1,    24,    -1,    25,    -1,    26,    -1,    27,   299,
      -1,   117,    28,   211,    -1,   117,   212,    28,   211,    -1,
     165,   299,   155,   211,    -1,   211,    11,   211,    -1,   211,
      13,   211,    -1,   211,    12,   211,    -1,   211,   172,   173,
     211,    -1,   211,   172,   299,   173,   211,    -1,   211,   174,
     211,    -1,   211,   175,   299,    33,   124,   211,    -1,   211,
     173,   236,    -1,   211,   179,   299,    -1,   211,   178,   299,
      -1,   211,   221,    -1,   216,    -1,   217,    -1,   220,    -1,
     340,    -1,   336,    -1,   117,    -1,   213,    -1,   213,   212,
      -1,   168,   214,    -1,   168,   214,   165,   299,    -1,   169,
     299,    -1,   171,   299,    -1,   117,    -1,    98,    18,   215,
      19,    -1,    98,    18,    19,    -1,    99,    18,   215,    19,
      -1,    99,    18,    19,    -1,   214,    -1,   214,   161,   215,
      -1,   211,   162,   193,    33,   193,   163,   211,    -1,   211,
      16,   211,    -1,   211,   157,   193,    33,   193,   160,   211,
      -1,   211,   164,   211,    -1,   211,   157,   193,    33,   236,
      33,   193,   160,   211,    -1,   211,   157,   236,   160,   211,
      -1,    18,   218,     8,   211,    19,    -1,   219,    -1,   219,
      38,   218,    -1,   153,   232,    -1,   152,   232,    -1,   151,
     232,    -1,    18,   231,     8,   211,    19,    18,   298,    19,
      -1,   217,    18,   298,    19,    -1,   222,    -1,   223,    -1,
     158,   224,   159,    -1,   158,   224,    33,   368,   159,    -1,
     158,   224,    33,   368,     8,   299,   159,    -1,   225,    -1,
     225,   161,   224,    -1,   226,    29,   226,    -1,   117,    -1,
     117,   227,    -1,   171,   299,    -1,   227,   171,   299,    -1,
      35,   229,    -1,   230,    -1,   230,    38,   229,    -1,   373,
      -1,   232,    -1,   232,    -1,   232,    38,   231,    -1,   117,
     165,   247,    -1,   117,   161,   232,    -1,    36,    -1,    36,
     234,    -1,   235,    -1,   235,   234,    -1,   117,     7,   236,
      -1,   117,    -1,    30,   373,    31,    -1,   166,   373,   167,
      -1,   236,   135,   236,    -1,   236,   137,   236,    -1,   236,
     136,   236,    -1,   166,   117,    33,   368,   167,    -1,   166,
     117,   227,    33,   368,   167,    -1,   166,   117,    33,   368,
       8,   299,   167,    -1,   166,   117,   227,    33,   368,     8,
     299,   167,    -1,   238,    -1,   239,    -1,   238,   239,    -1,
     243,    -1,   261,    -1,   265,    -1,     9,   241,     4,    -1,
     242,    -1,   242,   241,    -1,   243,    -1,   261,    -1,   265,
      -1,   282,    -1,   294,    -1,    37,    -1,    37,   244,    38,
      -1,    37,   244,    -1,   244,    38,   245,    -1,   245,    -1,
     246,   117,     7,   247,   260,    -1,   246,   117,     7,   247,
      -1,   246,   117,    39,   258,    -1,   246,   117,    39,   258,
     260,    -1,   188,    -1,   189,    -1,   190,    -1,   191,    -1,
      -1,   248,    -1,   249,    -1,   250,    -1,    40,   117,    41,
     258,     4,    -1,    18,   252,    19,    -1,   253,    -1,   251,
      -1,    49,   247,    -1,    47,   247,    -1,    48,   247,    -1,
      46,   247,    44,   247,    -1,    45,   247,    44,   247,    -1,
     254,    -1,   331,    -1,   194,    -1,    18,   247,    19,    -1,
     180,    -1,   181,    -1,   182,    -1,   183,    -1,   184,    -1,
     185,    -1,   186,    -1,   187,    -1,   257,    -1,    21,   247,
      22,    -1,   247,    33,   247,    -1,   252,    33,   247,    -1,
     247,    43,   247,    -1,   255,    -1,   256,    -1,   247,    50,
     247,    -1,    52,    50,   247,    -1,   247,    28,   247,    -1,
      52,    28,   247,    -1,   122,   117,   124,    -1,   259,    -1,
     259,   258,    -1,   247,    -1,   117,   165,   247,    -1,   117,
      53,   247,    -1,    55,   358,    54,   299,    -1,    56,   262,
      -1,   263,    -1,   263,    38,    -1,   263,    38,   262,    -1,
     246,   264,    -1,   117,   165,   247,     7,   299,    -1,   359,
     165,   247,     7,   299,    -1,   117,     7,   299,    -1,   359,
       7,   299,    -1,    57,    -1,    57,   266,    -1,   267,    -1,
     267,    38,    -1,   267,    38,   266,    -1,   268,    -1,   269,
      -1,   246,   117,   273,   276,   277,   280,    -1,   246,   270,
      -1,   117,   165,   254,   117,   271,    54,   272,   277,   279,
     281,    -1,    18,    19,    -1,    18,   360,    19,    -1,    18,
     360,    19,   271,    -1,   299,    -1,    61,    -1,    62,    -1,
      18,    19,    -1,    18,   274,    19,    -1,   360,   165,   247,
     161,   274,    -1,   360,   165,   247,    -1,    -1,   276,    -1,
     117,   165,   247,    -1,   117,   165,   247,   161,   276,    -1,
     278,    -1,    -1,    58,   299,    -1,   280,    -1,    -1,    59,
     299,    -1,    60,   331,    -1,    -1,    63,   283,    -1,    63,
      -1,   284,    -1,   284,    38,   283,    -1,   286,    -1,   285,
      -1,   246,   117,   165,   287,   117,   271,    54,   288,   289,
     277,   279,    -1,   246,   117,   273,   275,   289,   277,   280,
      -1,   247,   114,   247,    -1,    18,    19,   114,   247,    -1,
     247,   114,    18,    19,    -1,    18,    19,   114,    18,    19,
      -1,   211,    -1,    61,    -1,    62,    -1,   290,    -1,    -1,
      64,   291,    -1,   292,    -1,   292,   291,    -1,   293,   334,
      -1,   293,   334,   165,   247,    -1,    65,    -1,    66,    -1,
      67,   295,    -1,    67,    -1,   296,    -1,   296,    38,    -1,
     296,    38,   295,    -1,   246,   343,    -1,   297,    -1,    55,
     299,    -1,   299,    -1,   299,   161,   298,    -1,   150,    -1,
      18,   299,    19,    -1,    68,   302,    69,   299,    -1,   304,
      -1,   306,    -1,   309,    -1,   310,    -1,   311,    -1,   312,
      -1,   313,    -1,   314,    -1,   315,    -1,   316,    -1,   317,
      -1,   318,    -1,   321,    -1,   322,    -1,   323,    -1,   324,
      -1,   325,    -1,   326,    -1,   327,    -1,   328,    -1,   105,
      -1,   329,    -1,   330,    -1,   108,    18,   331,   161,   299,
      19,    -1,   117,    -1,   335,    -1,   300,    -1,   301,    -1,
     257,    -1,   102,    -1,   154,    -1,   303,    -1,   303,   161,
     302,    -1,   264,    -1,   270,    -1,    70,   299,    71,   299,
     305,    -1,    73,   299,    -1,    72,   299,    71,   299,   305,
      -1,    74,   299,   165,   307,     4,    -1,   308,    -1,   308,
      75,    28,   299,    -1,   308,   307,    -1,   360,    28,   299,
      38,    -1,    76,   299,    -1,    77,   299,    -1,    78,   299,
      -1,    79,   299,    -1,    80,   299,    -1,    81,   299,    -1,
      82,   299,    -1,    83,   299,    -1,    84,   299,    -1,    85,
     299,    -1,    86,   299,    -1,    87,   299,    -1,    88,   299,
      -1,    89,   299,    -1,    90,   299,    -1,    91,   299,    -1,
      92,   299,    -1,    93,   299,    -1,    94,   299,    -1,    95,
     299,    -1,   299,    76,   299,    -1,   299,    43,   299,    -1,
     299,    77,   299,    -1,   299,   118,   299,    -1,   299,   119,
     299,    -1,   299,   120,   299,    -1,   299,   121,   299,    -1,
     299,   122,   299,    -1,   299,   123,   299,    -1,   299,   124,
     299,    -1,   299,   125,   299,    -1,   299,     7,   299,    -1,
     299,   126,   299,    -1,   299,   127,   299,    -1,   299,   128,
     299,    -1,   299,   129,   299,    -1,   299,   130,   299,    -1,
     299,   131,   299,    -1,   299,   132,   299,    -1,   299,   133,
     299,    -1,   299,   134,   299,    -1,   299,   135,   299,    -1,
     299,   136,   299,    -1,   299,   137,   299,    -1,   299,   138,
     299,    -1,   299,   139,   299,    -1,   299,   140,   299,    -1,
     299,   141,   299,    -1,   299,   142,   299,    -1,   299,   143,
     299,    -1,   299,   144,   299,    -1,   299,   145,   299,    -1,
     299,   146,   299,    -1,   147,   368,   155,   299,    -1,   148,
     368,   155,   299,    -1,   149,   365,   155,   299,    -1,    30,
      31,    -1,    30,   298,    31,    -1,    30,   299,    33,   368,
      31,    -1,    30,   299,    33,   368,   155,   299,    31,    -1,
      30,   299,   161,    96,   161,   299,    31,    -1,    21,    22,
      -1,    21,   298,    22,    -1,    21,   299,    33,   366,    22,
      -1,    21,   299,    33,   366,   155,   299,    22,    -1,   299,
      18,   299,   161,    96,   161,   299,    19,    -1,    30,    97,
      31,    -1,    30,   319,    31,    -1,   320,    -1,   320,   161,
     319,    -1,   299,    97,   299,    -1,    30,   320,    33,   368,
      31,    -1,    30,   320,    33,   368,   155,   299,    31,    -1,
      98,    18,   298,    19,    -1,    99,    18,   298,    19,    -1,
     299,    18,   298,    19,    -1,   299,    18,    19,    -1,   299,
     100,   117,    -1,   299,   101,   102,    -1,   103,   372,   155,
     299,    -1,   104,   117,    18,   298,    19,    -1,   106,   331,
      18,   299,    19,    -1,   106,   249,    18,   299,    19,    -1,
     106,    18,   299,   161,   247,    19,    -1,   107,    18,   298,
      19,    -1,   117,    -1,   331,   100,   117,    -1,   331,    -1,
     333,    -1,   105,    -1,   354,    -1,   333,   100,   117,    -1,
     331,    -1,   331,   161,   334,    -1,   117,   110,    -1,   337,
      -1,   348,    -1,   350,    -1,   353,    -1,   344,    -1,   356,
      -1,   355,    -1,    70,   299,    28,   211,     4,    -1,    70,
     299,    28,   211,   339,     4,    -1,    33,   299,    28,   211,
      -1,   338,    -1,   339,   338,    -1,    18,   211,    19,    -1,
      18,   341,   211,    19,    -1,   111,   342,     8,    -1,   343,
      -1,   343,   161,   342,    -1,   117,   165,   247,    -1,   117,
     165,   247,   112,   299,    -1,   117,   165,   247,    69,   299,
      -1,   345,    -1,   347,    -1,   332,   112,   299,    -1,   345,
      -1,   345,    38,   346,    -1,   113,    18,   346,    19,    -1,
      70,   299,    71,   211,   349,    73,   211,    -1,    70,   299,
      71,   211,    73,   211,    -1,    72,   299,    71,   211,   349,
      -1,    72,   299,    71,   211,    -1,    74,   299,   165,   351,
       4,    -1,   352,    -1,   352,   161,    75,    28,   211,    -1,
     352,   161,   351,    -1,   360,    28,   211,    -1,   354,    -1,
     332,    29,   354,    -1,   331,    18,   298,    19,    -1,   331,
      18,    19,    -1,   333,   100,   117,    18,   298,    19,    -1,
     333,   100,   117,    18,    19,    -1,   115,    38,    -1,   115,
     299,    -1,    21,   357,    22,    -1,   289,   277,   280,    -1,
     361,    -1,   359,    -1,   362,    -1,   363,    -1,   364,    -1,
     358,    -1,   358,   161,   360,    -1,   117,    -1,   116,    -1,
     300,    -1,    18,   299,    19,    -1,    98,    18,   360,    19,
      -1,    99,    18,    19,    -1,    99,    18,   360,    19,    -1,
     366,    -1,   367,    -1,   358,   131,   299,    -1,   358,   165,
     247,    -1,   369,    -1,   368,   161,   369,    -1,   370,    -1,
     371,    -1,   360,   131,   299,    -1,   360,   165,   247,    -1,
     367,    -1,   367,   161,   372,    -1,   117,    -1,   117,   161,
     373,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     8,    10,    12,    15,    17,    19,
      21,    23,    28,    33,    37,    39,    45,    50,    54,    58,
      62,    68,    76,    80,    84,    89,    95,    99,   103,   107,
     111,   120,   125,   127,   130,   137,   144,   151,   160,   167,
     169,   172,   174,   176,   179,   185,   187,   190,   194,   196,
     200,   202,   204,   206,   208,   211,   215,   220,   225,   229,
     233,   237,   242,   248,   252,   259,   263,   267,   271,   274,
     276,   278,   280,   282,   284,   286,   288,   291,   294,   299,
     302,   305,   307,   312,   316,   321,   325,   327,   331,   339,
     343,   351,   355,   365,   371,   377,   379,   383,   386,   389,
     392,   401,   406,   408,   410,   414,   420,   428,   430,   434,
     438,   440,   443,   446,   450,   453,   455,   459,   461,   463,
     465,   469,   473,   477,   479,   482,   484,   487,   491,   493,
     497,   501,   505,   509,   513,   519,   526,   534,   543,   545,
     547,   550,   552,   554,   556,   560,   562,   565,   567,   569,
     571,   573,   575,   577,   581,   584,   588,   590,   596,   601,
     606,   612,   614,   616,   618,   620,   621,   623,   625,   627,
     633,   637,   639,   641,   644,   647,   650,   655,   660,   662,
     664,   666,   670,   672,   674,   676,   678,   680,   682,   684,
     686,   688,   692,   696,   700,   704,   706,   708,   712,   716,
     720,   724,   728,   730,   733,   735,   739,   743,   748,   751,
     753,   756,   760,   763,   769,   775,   779,   783,   785,   788,
     790,   793,   797,   799,   801,   808,   811,   822,   825,   829,
     834,   836,   838,   840,   843,   847,   853,   857,   858,   860,
     864,   870,   872,   873,   876,   878,   879,   882,   885,   886,
     889,   891,   893,   897,   899,   901,   913,   921,   925,   930,
     935,   941,   943,   945,   947,   949,   950,   953,   955,   958,
     961,   966,   968,   970,   973,   975,   977,   980,   984,   987,
     989,   992,   994,   998,  1000,  1004,  1009,  1011,  1013,  1015,
    1017,  1019,  1021,  1023,  1025,  1027,  1029,  1031,  1033,  1035,
    1037,  1039,  1041,  1043,  1045,  1047,  1049,  1051,  1053,  1055,
    1062,  1064,  1066,  1068,  1070,  1072,  1074,  1076,  1078,  1082,
    1084,  1086,  1092,  1095,  1101,  1107,  1109,  1114,  1117,  1122,
    1125,  1128,  1131,  1134,  1137,  1140,  1143,  1146,  1149,  1152,
    1155,  1158,  1161,  1164,  1167,  1170,  1173,  1176,  1179,  1182,
    1186,  1190,  1194,  1198,  1202,  1206,  1210,  1214,  1218,  1222,
    1226,  1230,  1234,  1238,  1242,  1246,  1250,  1254,  1258,  1262,
    1266,  1270,  1274,  1278,  1282,  1286,  1290,  1294,  1298,  1302,
    1306,  1310,  1314,  1319,  1324,  1329,  1332,  1336,  1342,  1350,
    1358,  1361,  1365,  1371,  1379,  1388,  1392,  1396,  1398,  1402,
    1406,  1412,  1420,  1425,  1430,  1435,  1439,  1443,  1447,  1452,
    1458,  1464,  1470,  1477,  1482,  1484,  1488,  1490,  1492,  1494,
    1496,  1500,  1502,  1506,  1509,  1511,  1513,  1515,  1517,  1519,
    1521,  1523,  1529,  1536,  1541,  1543,  1546,  1550,  1555,  1559,
    1561,  1565,  1569,  1575,  1581,  1583,  1585,  1589,  1591,  1595,
    1600,  1608,  1615,  1621,  1626,  1632,  1634,  1640,  1644,  1648,
    1650,  1654,  1659,  1663,  1670,  1676,  1679,  1682,  1686,  1690,
    1692,  1694,  1696,  1698,  1700,  1702,  1706,  1708,  1710,  1712,
    1716,  1721,  1725,  1730,  1732,  1734,  1738,  1742,  1744,  1748,
    1750,  1752,  1756,  1760,  1762,  1766,  1768
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   387,   387,   393,   400,   408,   416,   429,   430,   431,
     432,   437,   469,   480,   493,   506,   513,   520,   529,   538,
     547,   557,   568,   577,   586,   596,   607,   629,   639,   649,
     663,   673,   684,   690,   701,   702,   703,   704,   705,   743,
     750,   763,   767,   774,   784,   788,   795,   805,   824,   828,
     835,   840,   845,   850,   855,   864,   871,   882,   888,   895,
     902,   909,   916,   923,   930,   937,   944,   951,   958,   969,
     973,   977,   987,   991,   995,  1003,  1010,  1024,  1031,  1039,
    1046,  1056,  1061,  1067,  1073,  1082,  1094,  1100,  1109,  1110,
    1111,  1112,  1115,  1116,  1120,  1124,  1125,  1129,  1130,  1131,
    1135,  1143,  1159,  1163,  1167,  1176,  1183,  1193,  1200,  1209,
    1218,  1226,  1237,  1243,  1255,  1272,  1279,  1288,  1302,  1314,
    1320,  1329,  1338,  1351,  1356,  1368,  1374,  1385,  1402,  1407,
    1413,  1419,  1426,  1433,  1442,  1450,  1458,  1467,  1481,  1488,
    1496,  1506,  1512,  1517,  1528,  1546,  1555,  1565,  1569,  1573,
    1579,  1585,  1599,  1608,  1618,  1631,  1637,  1646,  1667,  1684,
    1706,  1733,  1741,  1749,  1757,  1763,  1772,  1776,  1780,  1784,
    1785,  1789,  1793,  1797,  1810,  1821,  1832,  1846,  1861,  1865,
    1870,  1877,  1884,  1888,  1892,  1896,  1900,  1904,  1908,  1912,
    1919,  1927,  1938,  1955,  1964,  1986,  1990,  1997,  2012,  2031,
    2046,  2086,  2096,  2102,  2111,  2115,  2122,  2129,  2141,  2184,
    2191,  2201,  2214,  2226,  2252,  2272,  2295,  2319,  2326,  2339,
    2345,  2351,  2360,  2364,  2371,  2399,  2409,  2451,  2456,  2463,
    2473,  2477,  2481,  2488,  2492,  2499,  2508,  2521,  2524,  2531,
    2542,  2556,  2561,  2567,  2574,  2579,  2585,  2592,  2596,  2602,
    2614,  2624,  2631,  2643,  2647,  2654,  2664,  2702,  2703,  2704,
    2705,  2709,  2713,  2717,  2726,  2731,  2737,  2744,  2751,  2761,
    2766,  2775,  2780,  2812,  2823,  2830,  2838,  2846,  2855,  2859,
    2866,  2882,  2888,  2908,  2934,  2939,  2946,  2950,  2954,  2958,
    2962,  2966,  2970,  2974,  2978,  2982,  2986,  2990,  2994,  2998,
    3002,  3006,  3010,  3014,  3018,  3022,  3026,  3030,  3034,  3038,
    3043,  3048,  3053,  3057,  3068,  3077,  3083,  3092,  3098,  3108,
    3113,  3122,  3157,  3172,  3195,  3215,  3225,  3242,  3256,  3289,
    3297,  3305,  3313,  3321,  3329,  3337,  3345,  3353,  3361,  3369,
    3377,  3385,  3393,  3401,  3409,  3417,  3425,  3433,  3441,  3458,
    3463,  3468,  3473,  3478,  3483,  3488,  3493,  3498,  3503,  3508,
    3513,  3518,  3523,  3528,  3533,  3538,  3543,  3548,  3553,  3558,
    3563,  3568,  3573,  3578,  3583,  3588,  3594,  3600,  3606,  3612,
    3618,  3624,  3635,  3651,  3666,  3686,  3696,  3705,  3722,  3744,
    3767,  3778,  3794,  3811,  3831,  3852,  3863,  3878,  3884,  3896,
    3911,  3926,  3949,  3967,  3986,  3995,  4007,  4033,  4052,  4074,
    4094,  4109,  4125,  4146,  4180,  4188,  4198,  4202,  4209,  4213,
    4217,  4250,  4258,  4269,  4278,  4282,  4286,  4288,  4292,  4296,
    4297,  4310,  4318,  4329,  4341,  4348,  4363,  4371,  4383,  4391,
    4398,  4411,  4427,  4428,  4432,  4436,  4440,  4453,  4454,  4458,
    4463,  4473,  4486,  4499,  4523,  4534,  4547,  4559,  4568,  4585,
    4589,  4602,  4613,  4623,  4636,  4737,  4738,  4753,  4757,  4765,
    4766,  4771,  4772,  4773,  4777,  4783,  4792,  4801,  4806,  4820,
    4828,  4833,  4844,  4867,  4871,  4878,  4888,  4898,  4904,  4913,
    4917,  4921,  4931,  4941,  4948,  4960,  4970
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
     195,   196
  };

  private static final short yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 5789;
  private static final int yynnts_ = 177;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 46;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 197;

  private static final int yyuser_token_number_max_ = 451;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 898 of cmlskeleton.java.m4  */
/* Line 39 of "src/main/bison/cml.y"  */

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

    // private ACallCallStatementControlStatementAction convertToAssignCall(PExp exp){
	
    // 	ACallCallStatementControlStatementAction retVal = null;

    // 	AApplyExp applyExp = (exp instanceof AApplyExp) ? (AApplyExp) exp : null;
    // 	if (applyExp != null){
    // 	    ANameExp nameExp = (applyExp.getRoot()  instanceof ANameExp) ? (ANameExp) applyExp.getRoot() : null;
    // 	    if (nameExp != null ){
	    
    // 		PObjectDesignator designator = null; //TODO

    // 		ACallCallStatementControlStatementAction call = 
    // 		    new ACallCallStatementControlStatementAction(applyExp.getLocation(), 
    // 								 designator, 
    // 								 nameExp.getName().getIdentifier(), 
    // 								 applyExp.getArgs());
    // 		// PStateDesignator stateDesignator = null;
    // 		// retVal = new AAssignmentCallCallStatementControlStatementAction(exp.getLocation(), 
    // 		// 								    stateDesignator, 
    // 		// 								    call);
    // 	    }
    // 	}
    // 	return retVal;
    // }

    PStateDesignator convertToStateDesignator(PDesignator designator)
    {
	PStateDesignator sd = null;
	if(designator instanceof ANameDesignator){
	    ANameDesignator ns = (ANameDesignator)designator;
	    LexNameToken name = extractLexNameToken(ns.getName());
	    
	    sd = new AIdentifierStateDesignator(name.getLocation(), 
						null, 
						name); 
	}
	else if (designator instanceof APrimaryDesignator){
	    APrimaryDesignator primary = (APrimaryDesignator)designator;
	    
	    if(primary.getPrimary() instanceof ACallPrimary){
		ACallCallStatementControlStatementAction call = 
		    (ACallCallStatementControlStatementAction) ((ACallPrimary)primary.getPrimary()).getCall();
		if(call.getArgs().size() != 1)
		    throw new RuntimeException("Missing implementation in convertToStateDesignator method. Throw nice error about map ot sequence apply only takes one arg");

		sd = new AMapSeqStateDesignator(call.getLocation(), 
						null, 
						call.getArgs().get(0));
	    }
	    else
		throw new RuntimeException("Missing implementation in convertToStateDesignator method. Currently only ACallPrimary is implemented");
	}
	else
	    throw new RuntimeException("Missing implementation in convertToStateDesignator method. sd was null");
		    
      
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
	}
	else if (sn.getIdentifiers().size() == 2)
	    lnt = new LexNameToken(sn.getIdentifiers().get(0).getName(), 
				   sn.getIdentifiers().get(1).getName(), 
				   combineLexLocation(extractFirstLexLocation(sn.getIdentifiers()),
						      extractLastLexLocation(sn.getIdentifiers())) 
				   );
	else{
	    lnt = new LexNameToken("Default", 
				   sn.getIdentifiers().get(0));
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


    private LexLocation extractLastLexLocation ( List<?> fields )
    {
      try 
	{
	  Object o = fields.get(0);
	  Class<?> clz = o.getClass();
	  
	  Method locMethod = clz.getMethod("getLocation", new Class<?>[] {} );
	  
	  LexLocation candidate = (LexLocation)locMethod.invoke( o, null );
	  for(Object p : fields)
	    {
	      LexLocation pLoc = (LexLocation)locMethod.invoke( o, null );
	      if (pLoc.endOffset > candidate.endOffset)
		candidate = pLoc;
	    }
	  return candidate;
	} catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
    }

    private LexLocation extractFirstLexLocation ( List<?> fields )
    {
      try 
	{
	  Object o = fields.get(0);
	  Class<?> clz = o.getClass();
	  
	  Method locMethod = clz.getMethod("getLocation", new Class<?>[] {} );
	  
	  LexLocation candidate = (LexLocation)locMethod.invoke( o, null );
	  for(Object p : fields)
	    {
	      LexLocation pLoc = (LexLocation)locMethod.invoke( o, null );
	      if (pLoc.startOffset < candidate.startOffset)
		candidate = pLoc;
	    }
	  return candidate;
	} catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
    }

    private< T extends PPattern> LexLocation extractLexLeftMostFromPatterns(List<T> ptrns )
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
	      scanner = new CmlLexer( new java.io.FileReader(file) );
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
/* Line 10347 of "src/main/java/eu/compassresearch/core/parser/CmlParser.java"  */

}


