
/* A Bison parser, made by GNU Bison 2.4.1.  */

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
package eu.compassresearch.cml.compiler;
/* First part of user declarations.  */

import static eu.compassresearch.cml.compiler.CmlParser.YyGotoClass.yypgoto_;
import static eu.compassresearch.cml.compiler.CmlParser.YyDefGotoClass.yydefgoto_;
import static eu.compassresearch.cml.compiler.CmlParser.YyTableClass.yytable_;
import static eu.compassresearch.cml.compiler.CmlParser.YyCheckClass.yycheck_;
import static eu.compassresearch.cml.compiler.CmlParser.YyTableClass.yytable_ninf_;
import static eu.compassresearch.cml.compiler.CmlParser.YyTokenClass.yystos_;
import static eu.compassresearch.cml.compiler.CmlParser.YyPactClass.yypact_ninf_;
import static eu.compassresearch.cml.compiler.CmlParser.YyPactClass.yypact_;
/* "%code imports" blocks.  */

/* Line 40 of cmlskeleton.java.m4  */
/* Line 8 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */


// ******************************
// *** required local imports ***
// ******************************

// required standard Java definitions
    import java.util.*;
    import org.overture.ast.definitions.*;
    import org.overture.ast.types.*;
    import org.overturetool.vdmj.lex.*;

    public



/* Line 40 of cmlskeleton.java.m4  */
/* Line 66 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/src/main/java/eu/compassresearch/cml/compiler/CmlParser.java"  */

/**
 * A Bison parser, automatically generated from <tt>/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
class CmlParser
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.4.1";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cmlskeleton.java.m4";


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
  public static final int EQUALS = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int AT = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int BEGIN = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSEQ = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPINTCH = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPEXTCH = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPLCHSYNC = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPRCHSYNC = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPINTERLEAVE = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPHIDE = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int LPAREN = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int RPAREN = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPRENAME = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int LSQUARE = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int RSQUARE = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSKIP = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSTOP = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPCHAOS = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int RARROW = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int LCURLY = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int RCURLY = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPAND = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int BAR = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int DBAR = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANNEL = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int CHANSET = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int TYPES = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int SEMI = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMRECORDDEF = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMCOMPOSE = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int OF = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMTYPEUNION = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int STAR = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int TO = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMINMAPOF = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMMAPOF = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMSEQOF = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMSEQ1OF = 298;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMSETOF = 299;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMPFUNCARROW = 300;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMTFUNCARROW = 301;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMUNITTYPE = 302;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMTYPE = 303;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMTYPENCMP = 304;
  /** Token number, to be returned by the scanner.  */
  public static final int DEQUALS = 305;
  /** Token number, to be returned by the scanner.  */
  public static final int INV = 306;
  /** Token number, to be returned by the scanner.  */
  public static final int VALUES = 307;
  /** Token number, to be returned by the scanner.  */
  public static final int FUNCTIONS = 308;
  /** Token number, to be returned by the scanner.  */
  public static final int PRE = 309;
  /** Token number, to be returned by the scanner.  */
  public static final int POST = 310;
  /** Token number, to be returned by the scanner.  */
  public static final int MEASURE = 311;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMSUBCLASSRESP = 312;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMNOTYETSPEC = 313;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMNOTYETDEF = 314;
  /** Token number, to be returned by the scanner.  */
  public static final int OPERATIONS = 315;
  /** Token number, to be returned by the scanner.  */
  public static final int EXT = 316;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMRD = 317;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMWR = 318;
  /** Token number, to be returned by the scanner.  */
  public static final int INSTANCEVARS = 319;
  /** Token number, to be returned by the scanner.  */
  public static final int LET = 320;
  /** Token number, to be returned by the scanner.  */
  public static final int IN = 321;
  /** Token number, to be returned by the scanner.  */
  public static final int IF = 322;
  /** Token number, to be returned by the scanner.  */
  public static final int THEN = 323;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSEIF = 324;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSE = 325;
  /** Token number, to be returned by the scanner.  */
  public static final int CASES = 326;
  /** Token number, to be returned by the scanner.  */
  public static final int OTHERS = 327;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUS = 328;
  /** Token number, to be returned by the scanner.  */
  public static final int MINUS = 329;
  /** Token number, to be returned by the scanner.  */
  public static final int ABS = 330;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOOR = 331;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT = 332;
  /** Token number, to be returned by the scanner.  */
  public static final int CARD = 333;
  /** Token number, to be returned by the scanner.  */
  public static final int POWER = 334;
  /** Token number, to be returned by the scanner.  */
  public static final int DUNION = 335;
  /** Token number, to be returned by the scanner.  */
  public static final int DINTER = 336;
  /** Token number, to be returned by the scanner.  */
  public static final int HD = 337;
  /** Token number, to be returned by the scanner.  */
  public static final int TL = 338;
  /** Token number, to be returned by the scanner.  */
  public static final int LEN = 339;
  /** Token number, to be returned by the scanner.  */
  public static final int ELEMS = 340;
  /** Token number, to be returned by the scanner.  */
  public static final int INDS = 341;
  /** Token number, to be returned by the scanner.  */
  public static final int REVERSE = 342;
  /** Token number, to be returned by the scanner.  */
  public static final int DCONC = 343;
  /** Token number, to be returned by the scanner.  */
  public static final int DOM = 344;
  /** Token number, to be returned by the scanner.  */
  public static final int RNG = 345;
  /** Token number, to be returned by the scanner.  */
  public static final int MERGE = 346;
  /** Token number, to be returned by the scanner.  */
  public static final int INVERSE = 347;
  /** Token number, to be returned by the scanner.  */
  public static final int ELLIPSIS = 348;
  /** Token number, to be returned by the scanner.  */
  public static final int MAPLETARROW = 349;
  /** Token number, to be returned by the scanner.  */
  public static final int MKUNDER = 350;
  /** Token number, to be returned by the scanner.  */
  public static final int DOT = 351;
  /** Token number, to be returned by the scanner.  */
  public static final int DOTHASH = 352;
  /** Token number, to be returned by the scanner.  */
  public static final int NUMERAL = 353;
  /** Token number, to be returned by the scanner.  */
  public static final int LAMBDA = 354;
  /** Token number, to be returned by the scanner.  */
  public static final int NEW = 355;
  /** Token number, to be returned by the scanner.  */
  public static final int SELF = 356;
  /** Token number, to be returned by the scanner.  */
  public static final int ISUNDER = 357;
  /** Token number, to be returned by the scanner.  */
  public static final int PREUNDER = 358;
  /** Token number, to be returned by the scanner.  */
  public static final int ISOFCLASS = 359;
  /** Token number, to be returned by the scanner.  */
  public static final int BACKTICK = 360;
  /** Token number, to be returned by the scanner.  */
  public static final int TILDE = 361;
  /** Token number, to be returned by the scanner.  */
  public static final int DCL = 362;
  /** Token number, to be returned by the scanner.  */
  public static final int ASSIGN = 363;
  /** Token number, to be returned by the scanner.  */
  public static final int ATOMIC = 364;
  /** Token number, to be returned by the scanner.  */
  public static final int OPERATIONARROW = 365;
  /** Token number, to be returned by the scanner.  */
  public static final int RETURN = 366;
  /** Token number, to be returned by the scanner.  */
  public static final int SKIP = 367;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMWHATEVER = 368;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER = 369;
  /** Token number, to be returned by the scanner.  */
  public static final int DIVIDE = 370;
  /** Token number, to be returned by the scanner.  */
  public static final int DIV = 371;
  /** Token number, to be returned by the scanner.  */
  public static final int REM = 372;
  /** Token number, to be returned by the scanner.  */
  public static final int MOD = 373;
  /** Token number, to be returned by the scanner.  */
  public static final int LT = 374;
  /** Token number, to be returned by the scanner.  */
  public static final int LTE = 375;
  /** Token number, to be returned by the scanner.  */
  public static final int GT = 376;
  /** Token number, to be returned by the scanner.  */
  public static final int GTE = 377;
  /** Token number, to be returned by the scanner.  */
  public static final int NEQ = 378;
  /** Token number, to be returned by the scanner.  */
  public static final int OR = 379;
  /** Token number, to be returned by the scanner.  */
  public static final int AND = 380;
  /** Token number, to be returned by the scanner.  */
  public static final int IMPLY = 381;
  /** Token number, to be returned by the scanner.  */
  public static final int BIMPLY = 382;
  /** Token number, to be returned by the scanner.  */
  public static final int INSET = 383;
  /** Token number, to be returned by the scanner.  */
  public static final int NOTINSET = 384;
  /** Token number, to be returned by the scanner.  */
  public static final int SUBSET = 385;
  /** Token number, to be returned by the scanner.  */
  public static final int PROPER_SUBSET = 386;
  /** Token number, to be returned by the scanner.  */
  public static final int UNION = 387;
  /** Token number, to be returned by the scanner.  */
  public static final int SETDIFF = 388;
  /** Token number, to be returned by the scanner.  */
  public static final int INTER = 389;
  /** Token number, to be returned by the scanner.  */
  public static final int CONC = 390;
  /** Token number, to be returned by the scanner.  */
  public static final int OVERWRITE = 391;
  /** Token number, to be returned by the scanner.  */
  public static final int MAPMERGE = 392;
  /** Token number, to be returned by the scanner.  */
  public static final int DOMRES = 393;
  /** Token number, to be returned by the scanner.  */
  public static final int DOMSUB = 394;
  /** Token number, to be returned by the scanner.  */
  public static final int RNGRES = 395;
  /** Token number, to be returned by the scanner.  */
  public static final int RNGSUB = 396;
  /** Token number, to be returned by the scanner.  */
  public static final int COMP = 397;
  /** Token number, to be returned by the scanner.  */
  public static final int ITERATE = 398;
  /** Token number, to be returned by the scanner.  */
  public static final int FORALL = 399;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS = 400;
  /** Token number, to be returned by the scanner.  */
  public static final int EXISTS1 = 401;
  /** Token number, to be returned by the scanner.  */
  public static final int AMP = 402;
  /** Token number, to be returned by the scanner.  */
  public static final int THREEBAR = 403;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPBARGT = 404;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPLSQUAREBAR = 405;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPLSQUAREGT = 406;
  /** Token number, to be returned by the scanner.  */
  public static final int DLSQUARE = 407;
  /** Token number, to be returned by the scanner.  */
  public static final int DRSQUARE = 408;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPBARRSQUARE = 409;
  /** Token number, to be returned by the scanner.  */
  public static final int COMMA = 410;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPSAMEAS = 411;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPLSQUAREDBAR = 412;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPDBARRSQUARE = 413;
  /** Token number, to be returned by the scanner.  */
  public static final int CSPDBAR = 414;
  /** Token number, to be returned by the scanner.  */
  public static final int COLON = 415;
  /** Token number, to be returned by the scanner.  */
  public static final int TBOOL = 416;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT = 417;
  /** Token number, to be returned by the scanner.  */
  public static final int TNAT1 = 418;
  /** Token number, to be returned by the scanner.  */
  public static final int TINT = 419;
  /** Token number, to be returned by the scanner.  */
  public static final int TRAT = 420;
  /** Token number, to be returned by the scanner.  */
  public static final int TREAL = 421;
  /** Token number, to be returned by the scanner.  */
  public static final int TCHAR = 422;
  /** Token number, to be returned by the scanner.  */
  public static final int TTOKEN = 423;
  /** Token number, to be returned by the scanner.  */
  public static final int globalDef = 424;
  /** Token number, to be returned by the scanner.  */
  public static final int chansetExpr = 425;
  /** Token number, to be returned by the scanner.  */
  public static final int declaration = 426;
  /** Token number, to be returned by the scanner.  */
  public static final int VDMcommand = 427;
  /** Token number, to be returned by the scanner.  */
  public static final int nameset = 428;
  /** Token number, to be returned by the scanner.  */
  public static final int namesetExpr = 429;
  /** Token number, to be returned by the scanner.  */
  public static final int communication = 430;
  /** Token number, to be returned by the scanner.  */
  public static final int predicate = 431;
  /** Token number, to be returned by the scanner.  */
  public static final int chanset = 432;
  /** Token number, to be returned by the scanner.  */
  public static final int typeVarIdentifier = 433;
  /** Token number, to be returned by the scanner.  */
  public static final int quoteLiteral = 434;
  /** Token number, to be returned by the scanner.  */
  public static final int functionType = 435;
  /** Token number, to be returned by the scanner.  */
  public static final int localDef = 436;
  /** Token number, to be returned by the scanner.  */
  public static final int symbolicLiteral = 437;
  /** Token number, to be returned by the scanner.  */
  public static final int implicitOperationBody = 438;
  /** Token number, to be returned by the scanner.  */
  public static final int UMINUS = 439;
  /** Token number, to be returned by the scanner.  */
  public static final int UPLUS = 440;



  
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
/* Line 167 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
                                yyval = ((yystack.valueAt (1-(1))));  
			      };
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 174 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {  documentDefs.add((PDefinition)((yystack.valueAt (1-(1))))); };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 179 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { yyval = ((yystack.valueAt (1-(1)))); };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 188 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    Position classStartPos =  ((CmlLexeme)((yystack.valueAt (5-(1))))).getStartPos();
    Position classEndPos = ((CmlLexeme)((yystack.valueAt (5-(4))))).getEndPos();
    LexLocation loc = new LexLocation(null, "Default", classStartPos.line,classStartPos.column,classEndPos.line,classEndPos.column,0,0);
    LexNameToken lexName = extractLexNameToken((CmlLexeme)((yystack.valueAt (5-(2))))); 
    // $$ = new AClassClassDefinition(loc, lexName , /*NameScope nameScope_*/ null, /*Boolean used_*/ null, 
    // 				   /*AAccessSpecifierAccessSpecifier*/ null,/* List<? extends LexNameToken> supernames_*/ new Vector<LexNameToken>(), 
    // 				   null /*hasContructors_*/, /*ClassDefinitionSettings settingHierarchy_*/null, 
    // 				   null/*Boolean gettingInheritable_*/, null/*Boolean gettingInvDefs_*/, 
    // 				   /*Boolean isAbstract_*/null, /*Boolean isUndefined_*/null); 
    AClassClassDefinition c = new AClassClassDefinition();
    c.setLocation(loc);
    c.setName(lexName);
    c.setIsAbstract(false);
    c.setDefinitions((List)((yystack.valueAt (5-(3)))));
    yyval = c;
};
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 249 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { /*new CMLIdentifier($1);*/ };
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 309 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
  yyval = (List)((yystack.valueAt (1-(1)))); 
};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 313 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
  yyval = new Vector<PDefinition>();
};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 320 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    List<PDefinition> defBlockList = new Vector<PDefinition>();
    List<PDefinition> defBlock = (List<PDefinition>)((yystack.valueAt (1-(1))));
    if (defBlockList != null) if (defBlock != null) defBlockList.addAll(defBlock);
    yyval = defBlockList;
};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 328 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    List<PDefinition> defBlockList = (List<PDefinition>)((yystack.valueAt (2-(1))));
    List<PDefinition> defBlock = (List<PDefinition>)((yystack.valueAt (2-(2))));
    if (defBlockList != null) if (defBlock != null) defBlockList.addAll(defBlock);
    yyval = defBlockList;
};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 338 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 72:
  if (yyn == 72)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 342 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 346 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 350 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 354 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    yyval = ((yystack.valueAt (1-(1))));
};
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 363 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = null; 
};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 367 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    yyval = (List<PDefinition>)((yystack.valueAt (3-(2))));
};
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 371 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    yyval = (List<PDefinition>)((yystack.valueAt (2-(2))));
};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 378 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    List<PDefinition> list = (List<PDefinition>)((yystack.valueAt (3-(1))));
    list.add((PDefinition)((yystack.valueAt (3-(3)))));
    yyval = list;
};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 384 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    {
    List<PDefinition> list = new Vector<PDefinition>(); 
    list.add((PDefinition)((yystack.valueAt (1-(1)))));
    yyval = list;
};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 394 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    LexLocation location = extractLexLocation((CmlLexeme)((yystack.valueAt (3-(1)))),((PTypeBase)((yystack.valueAt (3-(3))))).getLocation());
    LexNameToken name = extractLexNameToken((CmlLexeme)((yystack.valueAt (3-(1)))));
    AAccessSpecifierAccessSpecifier access = new AAccessSpecifierAccessSpecifier(new APublicAccess(),null,null);
    yyval = new ATypeDefinition(location,null /*NameScope nameScope_*/, false, 
			     null/*SClassDefinition classDefinition_*/,access, 
			     (PType)((yystack.valueAt (3-(3)))), null, null, null, 
			     null, true, name); 
};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 408 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = ((yystack.valueAt (3-(2))));
};
  break;
    

  case 85:
  if (yyn == 85)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 412 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = new ABooleanBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 86:
  if (yyn == 86)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 416 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = new ANatNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 87:
  if (yyn == 87)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 420 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 88:
  if (yyn == 88)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 424 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = new AIntNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 89:
  if (yyn == 89)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 428 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 90:
  if (yyn == 90)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 432 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = new ARealNumericBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 91:
  if (yyn == 91)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 436 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = new ACharBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    

  case 92:
  if (yyn == 92)
    
/* Line 360 of cmlskeleton.java.m4  */
/* Line 440 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */
    { 
    yyval = new ATokenBasicType(extractLexLocation((CmlLexeme)((yystack.valueAt (1-(1))))) , false);
};
  break;
    



/* Line 360 of cmlskeleton.java.m4  */
/* Line 1087 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/src/main/java/eu/compassresearch/cml/compiler/CmlParser.java"  */
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
  public static final short yypact_ninf_ = -732;
  public static final short yypact_[] =
  {
        38,   -81,   -75,   -55,   -54,   213,    81,  -732,   213,  -732,
    -732,  -732,  -732,   438,    78,   -47,  -732,  -732,  -732,    91,
    -732,  -732,  -732,    12,   145,    16,    26,   -31,   129,   438,
    -732,  -732,  -732,  -732,  -732,  -732,   148,    49,    -5,    46,
     138,  -732,    -2,  -732,  -732,  -732,  -732,    19,  -732,  -732,
    -732,  -732,   130,  -732,   153,  -732,   171,  1280,   162,  -732,
     -31,   -31,   101,  -732,    35,   194,   220,   226,   232,   485,
     239,   222,   258,  -732,   750,  -732,  -732,   441,   685,    12,
     145,   163,   253,  1280,   441,    90,  1278,    26,  1280,   950,
     730,    94,  1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,
    1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,
    1280,  1280,  1280,  1280,    -1,   145,   255,  -732,   407,   261,
     269,   -48,   145,   145,   145,  -732,  3567,  -732,  -732,  -732,
    -732,  -732,  -732,  -732,  -732,  -732,  -732,  -732,  -732,  -732,
    -732,  -732,  -732,  -732,  -732,  -732,  -732,  -732,  -732,  -732,
    -732,   441,  -732,  -732,  -732,   285,   178,  -732,   299,   136,
     137,   139,   147,   325,   946,   165,  1280,   499,   499,   499,
     499,   169,   499,   441,   441,   224,   441,   441,   441,   441,
     441,    32,  -732,  -732,  -732,  -732,  -732,  -732,  -732,  -732,
    -732,  -732,   642,  -732,     6,   644,   291,   685,  -732,   190,
     326,   235,    47,  2124,   359,   241,     5,   416,   243,  -732,
    2195,  -732,   339,  1694,  -732,   334,   340,  1623,   376,   -12,
     248,   343,  2271,  1460,  3567,  3567,  3567,  3567,  3567,  3567,
    3567,  3567,  3567,  3567,  3567,  3567,  3567,  3567,  3567,  3567,
    3567,  3567,  3567,  3567,  1280,   401,   370,   271,   280,  1280,
    1280,  -732,  -732,  -732,  -732,  -732,  -732,  -732,  -732,   417,
     418,  1280,   318,  -732,   -14,   288,   283,  -732,  -732,   292,
      -3,   302,  -732,  -732,  1280,  1280,  1280,  1280,   327,   357,
    1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,
    1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,
    1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,  1280,   168,
      52,   445,   389,   456,   479,   480,   482,   148,   473,   477,
    2342,   750,   750,   750,   750,   492,   750,   513,   486,   476,
     742,   854,   644,   644,   644,   441,   441,   441,   441,   441,
     441,   145,  -732,   441,   441,  -732,  -732,   145,  -732,  -732,
    -732,   497,   145,  1280,   503,  1358,  1387,   503,  -732,  -732,
     145,  1280,  -732,  -732,   145,  1280,  1060,  -732,   145,  1280,
      94,  1280,  1280,   145,  1765,  1280,   441,   145,  1280,  3638,
    1836,  1280,  1280,   505,  1907,   365,   441,  1280,  1280,   145,
    1280,  1280,  1280,   509,   517,  1978,   509,   509,  -732,  -732,
     509,   509,   509,   509,   509,   509,   509,   509,   509,   509,
     509,   509,   509,   509,   509,   509,   509,   509,   509,   509,
     509,   509,   509,   509,   509,   509,   509,   509,   509,  1280,
     511,   516,   522,   424,  -732,  -732,  -732,  -732,   369,   379,
     382,   547,  -732,   532,   -60,  -732,   538,     2,   392,    66,
     499,   499,   499,   499,   546,    49,   396,  -732,   499,  -732,
    -732,   685,   441,   441,   644,   644,   644,   644,   644,   644,
     528,   644,   644,  -732,  -732,  -732,  2413,   145,   530,    32,
     644,    32,   644,   534,   454,     4,  -732,    -8,  3567,   435,
      22,  2484,  -732,   443,  -732,   509,  2053,   596,   329,   577,
    1280,   601,   644,  -732,  3567,  1280,   441,  2555,  2626,  -732,
    1280,   644,  3567,  3567,  -732,  3567,  3567,  3567,  -732,  1170,
    3567,   450,   451,   457,   471,   609,   603,   481,   633,    52,
      80,    80,    80,    49,    80,    80,    80,    80,    80,   -92,
      80,   424,  -732,  -732,   314,   696,   870,   876,   625,   626,
     639,   750,   643,   644,   644,  1280,   145,   632,   840,   524,
    -732,  1280,  -732,  1280,  1280,  -732,  1280,  1280,  1280,  -732,
    -732,   628,  -732,  1280,  2697,  -732,   640,   579,  -732,  -732,
    2768,   504,   651,   654,   655,   549,   648,   488,   657,   652,
    -732,     2,     2,     2,   658,     2,     2,     2,     2,     2,
     526,   664,     2,   533,  -732,  -732,  -732,  -732,  1280,    49,
     499,  -732,  3567,  -732,   503,  -732,  -732,   646,  3567,   607,
     514,   215,  -732,  -732,    94,  1280,  1280,   678,  1280,  -732,
     635,  -732,    11,  -732,  -732,  -732,  -732,  -732,  -732,  -732,
    -732,  -732,  2839,  2911,  2983,  3055,  3131,  3567,  1280,  3567,
    -732,   681,  -732,  -732,  1280,    80,    80,    80,   554,   531,
     557,   552,   544,    49,    80,   560,  -732,  3202,   711,   936,
    -732,  1280,   679,   619,   703,   721,   729,   719,  -732,  -732,
     714,    10,   677,  3278,  1552,   318,  3567,   159,   646,  1280,
     638,  1280,  -732,  -732,  -732,  -732,  1280,  3567,  -732,  3349,
     263,   518,   758,   424,   746,   741,   759,   743,   752,     2,
     197,  -732,  -732,  -732,  3567,  1280,   726,  -732,   615,   729,
    -732,   760,  -732,   770,  1280,   661,   729,   729,   145,   756,
    -732,  -732,  -732,   318,   679,  3420,  -732,  3567,  2053,  -732,
    -732,  -732,  -732,  -732,    80,   620,    80,   613,  -732,   618,
     623,  3567,  1280,  -732,   619,  -732,  -732,  1280,   781,    23,
     797,  -732,   172,   822,   353,   803,   318,   673,   184,  -732,
    -732,  -732,    83,   828,   294,   816,   683,   814,  3567,  -732,
     837,  -732,   829,  1280,   729,  -732,  -732,   831,  -732,   729,
     824,   841,   318,   441,  -732,  -732,    80,  -732,    80,    80,
     693,  -732,  1280,  3496,  -732,   729,  -732,   318,  -732,  -732,
     548,   851,     2,     2,   715,   855,   729,  -732,  -732,  -732,
    -732,    80,  -732,   172,     2,  -732
  };
 }

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
         0,     0,     0,     0,     0,     0,     0,     2,     4,     6,
       7,     8,     9,    68,     0,   358,    65,    63,    64,     0,
       3,     1,     5,    76,   118,     0,   134,   152,     0,    67,
      69,    71,    72,    73,    74,    75,     0,     0,     0,     0,
      78,    80,     0,   341,   340,   342,   115,     0,   334,   335,
     336,   337,     0,   119,     0,   132,     0,     0,     0,   149,
     152,   152,     0,    70,     0,     0,     0,     0,     0,     0,
       0,    22,     0,    11,    13,   359,    66,     0,     0,    77,
       0,   286,     0,     0,     0,     0,     0,   134,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   178,     0,     0,
       0,   286,     0,     0,     0,   184,   153,   158,   159,   160,
     161,   162,   163,   164,   165,   166,   167,   168,   169,   170,
     171,   172,   173,   174,   175,   176,   177,   179,   180,   182,
     183,     0,   151,   150,    10,     0,     0,    29,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    85,    86,    87,    88,    89,    90,    91,    92,
     108,    93,    82,   107,   286,   111,     0,   109,    79,   338,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   133,
       0,   255,     0,   154,   250,     0,     0,   154,     0,   262,
     185,     0,     0,     0,   195,   196,   197,   198,   199,   200,
     201,   202,   203,   204,   205,   206,   207,   208,   209,   210,
     211,   212,   213,   214,     0,     0,     0,   356,     0,     0,
       0,   277,   278,   279,   280,   281,   282,   283,   284,     0,
       0,     0,     0,   290,     0,     0,   350,   352,   353,     0,
       0,     0,   346,   347,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   310,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    12,    15,    16,    17,     0,    19,     0,     0,     0,
       0,     0,    99,   100,    98,     0,     0,     0,     0,     0,
       0,     0,    81,     0,     0,    83,   110,     0,   343,   287,
     344,     0,   118,     0,     0,     0,     0,     0,   156,   256,
       0,     0,   260,   251,     0,     0,     0,   261,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   154,     0,     0,     0,     0,     0,
       0,     0,     0,   225,     0,   154,   215,   216,   270,   271,
     217,   218,   219,   220,   221,   222,   223,   224,   226,   227,
     228,   229,   230,   231,   232,   233,   234,   235,   236,   237,
     238,   239,   240,   241,   242,   243,   244,   245,   246,     0,
       0,     0,     0,     0,    38,    39,    40,    35,     0,     0,
       0,     0,    34,     0,     0,    30,    32,    36,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    21,     0,    84,
      97,     0,     0,     0,   104,   106,    95,    96,   103,   105,
       0,   112,   113,   339,   345,   116,     0,     0,     0,   139,
     137,   138,   136,     0,     0,     0,   155,     0,   264,     0,
       0,     0,   263,   262,   186,   157,     0,     0,   191,     0,
       0,     0,   349,   357,   272,     0,     0,     0,     0,   285,
       0,   355,   354,   247,   351,   248,   348,   249,   269,     0,
     311,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    31,    14,     0,     0,     0,     0,     0,     0,
       0,    18,     0,   102,   101,     0,   118,     0,     0,     0,
     257,     0,   252,     0,     0,   265,     0,     0,     0,   187,
     190,     0,   193,     0,     0,   268,     0,     0,   275,   274,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      33,    41,    50,    51,     0,    42,    46,    47,    44,    43,
       0,     0,    48,     0,    24,    25,    26,    28,     0,     0,
       0,    94,   114,   117,   121,   124,   125,   127,   123,     0,
       0,     0,   141,   142,     0,     0,     0,     0,     0,   294,
       0,   291,     0,   140,   296,   297,   312,   313,   298,   299,
     300,   302,     0,     0,     0,     0,     0,   188,     0,   194,
     267,   269,   276,   181,     0,     0,     0,     0,    61,     0,
       0,     0,     0,     0,     0,     0,    45,     0,     0,     0,
     122,     0,   129,     0,   303,     0,     0,     0,   328,   330,
     329,     0,     0,     0,     0,     0,   301,   146,   127,     0,
       0,     0,   258,   253,   254,   266,     0,   192,   273,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    49,
       0,    20,    23,    27,   126,     0,   131,   307,   308,     0,
     305,     0,   333,     0,     0,     0,     0,     0,     0,     0,
     147,   148,   143,     0,   129,     0,   292,   314,     0,   259,
      54,    55,    56,    62,     0,     0,     0,     0,    37,     0,
       0,   128,     0,   120,     0,   304,   306,     0,     0,   331,
       0,   295,     0,     0,   322,     0,     0,   288,   146,   135,
     293,   189,     0,     0,     0,     0,     0,     0,   130,   309,
       0,   332,     0,     0,     0,   318,   321,     0,   324,     0,
     315,     0,     0,     0,   144,    60,     0,    57,     0,     0,
       0,   326,     0,     0,   319,     0,   325,     0,   317,   289,
     146,     0,    59,    52,     0,     0,     0,   323,   316,   145,
      58,     0,   327,     0,    53,   320
  };

  /* YYPGOTO[NTERM-NUM].  */
  static class YyGotoClass {
  public static final short yypgoto_[] =
  {
      -732,  -732,    48,   807,  -732,  -732,   556,   -22,  -732,   348,
     571,  -184,  -531,  -732,  -732,  -732,  -732,  -732,   860,  -732,
    -732,   815,   -56,  -195,  -732,   699,  -732,  -344,  -732,  -732,
    -354,  -732,   216,   167,  -732,  -732,   819,  -732,  -732,  -732,
    -732,  -731,  -732,  -732,   188,  -732,   -58,    87,  -363,  -732,
     195,  -732,  -497,  -732,  -732,  -732,  -732,  -732,  -732,  -732,
    -732,  -732,  -732,  -732,   565,   567,  -732,  -732,  -732,  -732,
    -732,  -732,  -732,   282,  -732,  -732,  -732,   -42,   146,  -732,
    -732,  -553,  -590,  -732,  -732,   183,  -650,  -732,  -639,   142,
    -732,  -732,   128,  -732,  -732,  -732,  -732,  -732,  -732,   -19,
     -61,  -732,  -732,  -732,  -732,  -732,   592,   835,  -114,   564,
    -732,  -732,   584,   -33
  };
  }

  /* YYDEFGOTO[NTERM-NUM].  */
  static class YyDefGotoClass {
  public static final short
  yydefgoto_[] =
  {
        -1,     6,     7,     8,     9,    10,    73,    74,   158,   445,
     446,   447,   525,    11,    17,    12,    28,    29,    30,    31,
      40,    41,   195,   196,   197,   342,    32,    46,    33,    53,
     478,   617,   672,   716,   753,    34,    55,    56,   208,   630,
     688,   732,   733,    35,    59,    60,   486,   384,   221,   127,
     569,   128,   497,   498,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   218,   219,   139,   140,   141,   142,
     143,   144,   145,   146,   147,   259,   148,   149,   768,   150,
     632,   674,   675,   634,   676,   717,    61,   635,   636,   791,
     637,   638,   785,   639,   763,   764,   640,   681,   641,   199,
     264,    48,    49,    50,    51,   271,   272,   247,   265,   266,
     267,   268,   248,    18
  };
  }

  static class YyTableClass {
  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  public static final short yytable_ninf_ = -287;
  public static final short
  yytable_[] =
  {
        82,   572,   346,   483,    75,    47,   633,   494,   475,   269,
     603,   534,   535,   536,    80,   244,   368,   537,   562,   200,
      57,   192,   600,   718,   560,    83,   724,   689,   204,   538,
     207,   212,   216,    13,   386,   193,   193,   794,     1,    14,
       2,     1,   193,     2,   193,   376,   729,   164,   565,  -286,
     335,   336,    77,    20,   343,   344,    22,   201,   263,    15,
      19,   430,   431,   432,   350,     3,     4,    84,     3,     4,
     543,   433,   245,   434,   435,   436,   260,   335,   336,   819,
      78,    21,   601,    58,    36,   533,   721,   531,   532,   430,
     431,   432,   534,   535,   536,   309,   246,    38,   537,   433,
     795,   434,   435,   436,   718,   270,   725,   690,    37,   193,
     538,   201,    81,    81,   387,   355,    16,   327,   328,   691,
     330,   331,   332,   333,   334,   391,    39,   790,   201,   755,
      52,   193,   193,    62,   193,   193,   193,   193,   193,   563,
      54,   351,    42,   369,   126,   321,   322,   323,   324,   155,
     326,   561,   539,   540,   541,   193,    64,    65,    66,    67,
      43,    44,    68,    15,    69,    76,   437,    70,   790,   566,
     203,    79,   743,   761,   762,   210,   213,   217,    85,   222,
     223,   224,   225,   226,   227,   228,   229,   230,   231,   232,
     233,   234,   235,   236,   237,   238,   239,   240,   241,   242,
     243,    86,   438,   383,    87,   337,   338,     5,   156,   439,
     151,   440,   613,   339,   340,   154,     1,   394,     2,   159,
     385,   730,   731,   441,   442,   749,   750,   443,   444,    45,
     438,   804,   793,   539,   540,   541,   806,   439,   166,   440,
      42,   783,   784,     3,     4,   160,   730,   731,   152,   153,
     487,   161,   817,   320,   490,   443,   444,   162,    43,    44,
     670,   682,    71,   823,   165,   167,   552,   788,   201,   202,
     205,   249,   534,   535,   536,   220,   429,   261,   537,   464,
     465,   466,   467,   468,   469,   262,   473,   471,   472,   740,
     538,   310,   311,   193,   193,   193,   193,   193,   193,   480,
     482,   193,   193,   534,   535,   536,   312,   313,   314,   537,
     315,   797,   499,   193,   193,   116,   678,   501,   316,    72,
     502,   538,   470,   168,   169,   170,   171,    45,   172,    81,
     511,   374,   317,    47,   193,   319,   379,   380,   329,   325,
     604,   484,   341,   348,   193,   347,   591,   592,   593,   349,
     595,   596,   597,   598,   599,   354,   602,   357,   246,   359,
     362,   393,   395,   396,   397,   353,   363,   400,   401,   402,
     403,   404,   405,   406,   407,   408,   409,   410,   411,   412,
     413,   414,   415,   416,   417,   418,   419,   420,   421,   422,
     423,   424,   425,   426,   427,   428,   337,   338,   430,   431,
     432,   571,   367,   370,   339,   340,   553,   554,   433,   371,
     434,   435,   436,   539,   540,   541,   557,   375,   376,   193,
     193,   193,   549,   250,    42,   787,   377,   378,   544,   545,
     546,   547,    81,   381,   382,   388,   551,   499,   389,   390,
     476,   398,    43,    44,   539,   540,   541,   576,    42,   392,
     577,   448,   488,   337,   338,   399,   491,   173,   495,   496,
     174,   339,   340,   450,   193,   504,    43,    44,   507,   508,
      23,   700,   701,   702,   512,   513,   175,   515,   516,   517,
     709,   176,   177,   178,   179,   180,   451,   452,   181,   453,
      24,    25,   455,    64,    65,    66,    67,   456,    26,    68,
     594,    69,    27,   437,    70,   458,   460,    64,    65,    66,
      67,    45,   461,    68,   474,    69,   520,   631,    70,   477,
     510,    81,   509,   337,   338,   275,   356,   534,   535,   536,
     459,   339,   340,   537,   518,    45,   521,    47,   524,   438,
     619,   522,   526,   620,   741,   538,   439,   523,   440,   621,
     337,   338,   527,   528,   529,    81,   530,   533,   339,   340,
     772,   442,   774,   548,   443,   444,   542,   550,   251,   252,
     253,   254,   255,   256,   257,   258,   668,   631,   555,   680,
     558,   622,   391,   623,   559,   337,   338,   574,   669,   624,
     564,   625,   395,   339,   340,   626,   652,   580,   369,    71,
     570,   573,   182,   183,   184,   185,   186,   187,   188,   189,
     730,   731,   811,    71,   812,   813,   337,   338,   575,   190,
     191,   582,   583,   619,   339,   340,   620,   585,   584,   586,
     708,   587,   621,   627,   631,   628,   629,   824,    81,   588,
     589,   608,   612,   631,   609,   618,   610,   611,   642,   614,
     643,   644,   648,   645,   646,   647,   163,   651,   655,   654,
     649,   656,   657,   658,   659,   660,   758,   765,   539,   540,
     541,   662,   624,   661,   625,   664,   663,   631,   626,   337,
     338,   337,   338,   760,   631,   631,   666,   339,   340,   339,
     340,   767,   665,   341,   685,   667,   687,   677,   698,   780,
     671,   173,   704,   499,   174,   168,   169,   170,   171,   703,
     172,   705,   683,   684,   673,   686,   627,   707,   628,   629,
     175,    81,   605,   706,   631,   176,   177,   178,   179,   180,
     710,   712,   181,    58,   715,   697,   719,   810,   720,   722,
     723,   699,   631,   726,   815,   619,    88,   631,   620,    89,
     767,   193,   736,   744,   621,    90,   214,   745,   714,   168,
     169,   170,   171,   631,   172,   631,   746,   534,   535,   536,
     754,   747,   748,   537,   631,   759,   735,   756,   737,   337,
     338,   462,   752,   738,   742,   538,   757,   339,   340,   766,
     775,   773,   776,   777,   624,    91,   625,    92,   781,   194,
     626,    93,   751,    94,    95,    96,    97,    98,    99,   100,
     101,   102,   103,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   782,   215,   114,   786,   789,   792,   115,
     116,   117,   118,   119,   120,   796,   798,   799,   627,   778,
     628,   629,   800,    81,   121,   802,   182,   183,   184,   185,
     186,   187,   188,   189,   801,   805,    88,   807,   808,    89,
     534,   535,   536,   190,   191,    90,   537,   814,   820,   821,
     803,   157,   822,   454,   122,   123,   124,   590,   538,   168,
     169,   170,   171,   449,   172,   168,   169,   170,   171,    63,
     172,   337,   338,   463,   198,   345,   606,   615,   616,   339,
     340,   769,   607,   679,   734,    91,   209,    92,   539,   540,
     541,    93,   125,    94,    95,    96,    97,    98,    99,   100,
     101,   102,   103,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   771,   492,   114,   493,   779,   809,   115,
     116,   117,   118,   119,   120,   168,   169,   170,   171,   818,
     172,   825,   485,   514,   121,   168,   169,   170,   171,   273,
     172,   503,   713,   318,     0,     0,    88,     0,     0,    89,
     211,     0,     0,     0,     0,    90,     0,     0,     0,     0,
       0,     0,     0,     0,   122,   123,   124,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   539,   540,   541,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    91,     0,    92,     0,     0,
       0,    93,   125,    94,    95,    96,    97,    98,    99,   100,
     101,   102,   103,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,     0,     0,   114,     0,     0,     0,   115,
     116,   117,   118,   119,   120,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   121,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    88,     0,     0,    89,
       0,     0,     0,     0,     0,    90,     0,     0,     0,     0,
       0,     0,     0,     0,   122,   123,   124,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    91,     0,    92,     0,     0,
       0,    93,   125,    94,    95,    96,    97,    98,    99,   100,
     101,   102,   103,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   489,     0,   114,     0,     0,     0,   115,
     116,   117,   118,   119,   120,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   121,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    88,     0,     0,    89,
       0,     0,     0,     0,     0,    90,     0,     0,     0,     0,
       0,     0,     0,     0,   122,   123,   124,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    91,     0,    92,     0,     0,
       0,    93,   125,    94,    95,    96,    97,    98,    99,   100,
     101,   102,   103,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,   581,     0,   114,     0,     0,     0,   115,
     116,   117,   118,   119,   120,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   121,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   173,     0,    88,   174,     0,    89,
       0,     0,     0,     0,     0,    90,     0,     0,     0,     0,
       0,     0,     0,   175,   122,   123,   124,     0,   176,   177,
     178,   179,   180,     0,     0,   206,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    91,     0,    92,     0,     0,
       0,    93,   125,    94,    95,    96,    97,    98,    99,   100,
     101,   102,   103,   104,   105,   106,   107,   108,   109,   110,
     111,   112,   113,     0,   173,   114,     0,   174,     0,   115,
     116,   117,   118,   119,   120,     0,     0,     0,     0,     0,
       0,     0,    81,   175,   121,     0,     0,     0,   176,   177,
     178,   179,   180,   173,     0,   479,   174,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   175,     0,   122,   123,   124,   176,   177,   178,
     179,   180,     0,     0,   481,     0,     0,     0,     0,   182,
     183,   184,   185,   186,   187,   188,   189,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   190,   191,     0,     0,
       0,     0,   125,     0,     0,     0,   274,     0,     0,     0,
       0,     0,    81,     0,     0,     0,   275,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    81,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   182,
     183,   184,   185,   186,   187,   188,   189,     0,     0,     0,
       0,     0,     0,   276,   277,     0,   190,   191,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   182,   183,
     184,   185,   186,   187,   188,   189,   278,   279,   274,     0,
       0,     0,     0,     0,     0,   190,   191,     0,   275,     0,
       0,     0,     0,     0,     0,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     373,     0,     0,     0,     0,   276,   277,     0,     0,   274,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
       0,     0,     0,     0,     0,     0,     0,     0,   278,   279,
       0,   364,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,   282,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   276,   277,     0,     0,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,     0,   728,     0,     0,     0,     0,   365,     0,   278,
     279,     0,   360,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   276,   277,     0,
       0,   274,     0,     0,     0,     0,     0,     0,   366,     0,
       0,   275,     0,     0,     0,     0,     0,     0,     0,     0,
     278,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,   282,   283,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   276,   277,
       0,     0,   274,     0,     0,     0,     0,     0,     0,   361,
       0,     0,   275,     0,     0,     0,     0,     0,     0,     0,
       0,   278,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   276,
     277,     0,     0,   274,     0,     0,     0,     0,     0,     0,
     500,     0,     0,   275,     0,     0,     0,     0,     0,     0,
       0,     0,   278,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     276,   277,     0,     0,   274,     0,     0,     0,     0,     0,
       0,   506,     0,     0,   275,     0,     0,     0,     0,     0,
       0,     0,     0,   278,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   276,   277,     0,     0,     0,     0,     0,     0,   274,
       0,     0,   361,     0,     0,     0,     0,     0,     0,   275,
       0,     0,     0,     0,   278,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   567,   568,     0,     0,   276,   277,     0,     0,
     274,     0,     0,   519,     0,     0,     0,     0,     0,     0,
     275,     0,     0,     0,     0,     0,     0,     0,     0,   278,
     279,     0,     0,     0,     0,     0,     0,   352,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   276,   277,     0,
       0,   274,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   358,     0,     0,     0,     0,     0,     0,     0,
     278,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,   282,   283,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   276,   277,
       0,     0,     0,     0,     0,     0,     0,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,     0,     0,
       0,   278,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   372,
       0,     0,     0,     0,   276,   277,     0,     0,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   275,   457,
       0,     0,     0,     0,     0,     0,     0,   278,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   276,   277,     0,     0,   274,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
       0,     0,     0,     0,     0,     0,     0,     0,   278,   279,
       0,     0,     0,     0,     0,     0,   556,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,   282,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   276,   277,     0,     0,
     274,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     275,     0,     0,     0,     0,     0,     0,     0,     0,   278,
     279,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,   276,   277,     0,
       0,   274,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,   578,     0,     0,     0,     0,     0,   365,     0,
     278,   279,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   280,
     281,   282,   283,   284,   285,   286,   287,   288,   289,   290,
     291,   292,   293,   294,   295,   296,   297,   298,   299,   300,
     301,   302,   303,   304,   305,   306,   307,   308,   276,   277,
       0,     0,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   275,   579,     0,     0,     0,     0,     0,     0,
       0,   278,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   276,
     277,     0,     0,   274,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,   650,     0,     0,     0,     0,     0,
       0,     0,   278,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     276,   277,     0,     0,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   275,   653,     0,     0,     0,     0,
       0,     0,     0,   278,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   276,   277,     0,     0,   274,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,     0,     0,     0,   692,
       0,     0,     0,     0,   278,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   276,   277,     0,     0,     0,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,     0,     0,
       0,     0,     0,     0,     0,   278,   279,   693,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,     0,   276,   277,     0,     0,     0,   274,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   275,
       0,     0,     0,     0,     0,     0,     0,   278,   279,   694,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,     0,   276,   277,     0,     0,
       0,   274,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   275,     0,     0,     0,     0,     0,     0,     0,   278,
     279,   695,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   280,   281,
     282,   283,   284,   285,   286,   287,   288,   289,   290,   291,
     292,   293,   294,   295,   296,   297,   298,   299,   300,   301,
     302,   303,   304,   305,   306,   307,   308,     0,   276,   277,
       0,     0,     0,     0,     0,     0,     0,   274,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   275,     0,     0,
       0,   278,   279,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   295,   296,   297,   298,   299,
     300,   301,   302,   303,   304,   305,   306,   307,   308,   696,
       0,     0,     0,     0,   276,   277,     0,     0,   274,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   275,   711,
       0,     0,     0,     0,     0,     0,     0,   278,   279,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   280,   281,   282,   283,
     284,   285,   286,   287,   288,   289,   290,   291,   292,   293,
     294,   295,   296,   297,   298,   299,   300,   301,   302,   303,
     304,   305,   306,   307,   308,   276,   277,     0,     0,     0,
       0,     0,     0,     0,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   275,     0,     0,     0,   278,   279,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   280,   281,   282,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,   727,     0,     0,     0,
       0,   276,   277,     0,     0,   274,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   275,   739,     0,     0,     0,
       0,     0,     0,     0,   278,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308,   276,   277,     0,     0,   274,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   275,   770,     0,     0,
       0,     0,     0,     0,     0,   278,   279,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   280,   281,   282,   283,   284,   285,
     286,   287,   288,   289,   290,   291,   292,   293,   294,   295,
     296,   297,   298,   299,   300,   301,   302,   303,   304,   305,
     306,   307,   308,   276,   277,     0,     0,     0,     0,     0,
       0,     0,   274,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   275,     0,     0,     0,   278,   279,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   816,     0,     0,     0,     0,   276,
     277,     0,     0,   274,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   275,     0,     0,     0,     0,     0,     0,
       0,     0,   278,   279,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   280,   281,   282,   283,   284,   285,   286,   287,   288,
     289,   290,   291,   292,   293,   294,   295,   296,   297,   298,
     299,   300,   301,   302,   303,   304,   305,   306,   307,   308,
     276,   277,     0,     0,   274,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   505,     0,     0,     0,     0,     0,
       0,     0,     0,   278,   279,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   280,   281,   282,   283,   284,   285,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   307,
     308,   276,   277,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   278,   279,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   280,   281,   282,   283,   284,   285,   286,
     287,   288,   289,   290,   291,   292,   293,   294,   295,   296,
     297,   298,   299,   300,   301,   302,   303,   304,   305,   306,
     307,   308
  };
  }

  /* YYCHECK.  */
  static class YyCheckClass {
  public static final short
  yycheck_[] =
  {
        42,   498,   197,   357,    37,    24,   559,   370,   352,   123,
     541,     9,    10,    11,    16,    16,    28,    15,    26,    80,
      51,    77,   114,   673,    20,     6,    16,    16,    84,    27,
      86,    89,    90,   114,    48,    77,    78,   768,     3,   114,
       5,     3,    84,     5,    86,    48,   685,    69,    26,    26,
      45,    46,     6,     5,    48,    49,     8,   105,   106,   114,
     114,     9,    10,    11,    17,    30,    31,    48,    30,    31,
       4,    19,   114,    21,    22,    23,   118,    45,    46,   810,
      34,     0,   174,   114,     6,    19,   676,   147,   148,     9,
      10,    11,     9,    10,    11,   151,   115,     6,    15,    19,
      17,    21,    22,    23,   754,   124,    96,    96,   155,   151,
      27,   105,   114,   114,   128,   110,   171,   173,   174,   108,
     176,   177,   178,   179,   180,   128,   114,   766,   105,   719,
     114,   173,   174,     4,   176,   177,   178,   179,   180,   147,
     114,   202,    95,   155,    57,   167,   168,   169,   170,   114,
     172,   147,   150,   151,   152,   197,     8,     9,    10,    11,
     113,   114,    14,   114,    16,   170,   114,    19,   807,   147,
      83,    33,   703,   726,   727,    88,    89,    90,    48,    92,
      93,    94,    95,    96,    97,    98,    99,   100,   101,   102,
     103,   104,   105,   106,   107,   108,   109,   110,   111,   112,
     113,    48,   150,   261,    33,    37,    38,   169,   173,   157,
      48,   159,   556,    45,    46,   114,     3,   275,     5,    25,
     262,    62,    63,   171,   172,    28,    29,   175,   176,   182,
     150,   784,    48,   150,   151,   152,   789,   157,    16,   159,
      95,    69,    70,    30,    31,    25,    62,    63,    60,    61,
     364,    25,   805,   166,   368,   175,   176,    25,   113,   114,
     614,   624,   114,   816,    25,     7,   461,   764,   105,    16,
     180,    16,     9,    10,    11,   181,   108,    16,    15,   335,
     336,   337,   338,   339,   340,    16,   347,   343,   344,    26,
      27,     6,   114,   335,   336,   337,   338,   339,   340,   355,
     356,   343,   344,     9,    10,    11,     7,   171,   171,    15,
     171,    17,   373,   355,   356,   100,   101,   375,   171,   171,
     376,    27,   341,     9,    10,    11,    12,   182,    14,   114,
     386,   244,     7,   352,   376,   170,   249,   250,   114,   170,
      26,   360,    51,    17,   386,   155,   530,   531,   532,   114,
     534,   535,   536,   537,   538,   114,   540,   114,   377,    20,
      26,   274,   275,   276,   277,     6,    26,   280,   281,   282,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296,   297,   298,   299,   300,   301,   302,
     303,   304,   305,   306,   307,   308,    37,    38,     9,    10,
      11,    72,    26,   155,    45,    46,   462,   463,    19,    66,
      21,    22,    23,   150,   151,   152,   477,    16,    48,   461,
     462,   463,   455,    16,    95,    72,   155,   147,   450,   451,
     452,   453,   114,    16,    16,   147,   458,   498,   155,   147,
     353,   114,   113,   114,   150,   151,   152,   505,    95,   147,
     506,     6,   365,    37,    38,    98,   369,    16,   371,   372,
      19,    45,    46,     7,   506,   378,   113,   114,   381,   382,
      32,   655,   656,   657,   387,   388,    35,   390,   391,   392,
     664,    40,    41,    42,    43,    44,     7,     7,    47,     7,
      52,    53,    19,     8,     9,    10,    11,    20,    60,    14,
     533,    16,    64,   114,    19,    13,    20,     8,     9,    10,
      11,   182,    36,    14,    17,    16,   429,   559,    19,    16,
     155,   114,    17,    37,    38,    16,   110,     9,    10,    11,
      17,    45,    46,    15,    17,   182,    25,   556,   114,   150,
      16,    25,   173,    19,    26,    27,   157,    25,   159,    25,
      37,    38,   173,   171,     7,   114,    24,    19,    45,    46,
     744,   172,   746,    17,   175,   176,   174,   171,   161,   162,
     163,   164,   165,   166,   167,   168,   609,   619,    50,   621,
      50,    57,   128,    59,    50,    37,    38,   500,   610,    65,
     155,    67,   505,    45,    46,    71,    17,   510,   155,   114,
       4,    24,   161,   162,   163,   164,   165,   166,   167,   168,
      62,    63,   796,   114,   798,   799,    37,    38,    17,   178,
     179,   171,   171,    16,    45,    46,    19,   156,   171,    20,
     663,    28,    25,   109,   676,   111,   112,   821,   114,   158,
       7,    16,   555,   685,    18,   558,     7,     4,   561,    17,
     563,   564,    24,   566,   567,   568,   171,    17,     7,   155,
     573,     7,     7,   114,    16,   177,   724,   728,   150,   151,
     152,    19,    65,    16,    67,   149,    18,   719,    71,    37,
      38,    37,    38,   725,   726,   727,   153,    45,    46,    45,
      46,   733,    28,    51,    16,   608,    61,   183,    17,   757,
      54,    16,   171,   764,    19,     9,    10,    11,    12,   155,
      14,   154,   625,   626,   107,   628,   109,   173,   111,   112,
      35,   114,    26,   171,   766,    40,    41,    42,    43,    44,
     170,    20,    47,   114,    55,   648,    33,   793,    17,    20,
      26,   654,   784,    66,   802,    16,    16,   789,    19,    19,
     792,   793,   114,     7,    25,    25,    26,    16,   671,     9,
      10,    11,    12,   805,    14,   807,     7,     9,    10,    11,
     155,    28,    20,    15,   816,   114,   689,    17,   691,    37,
      38,    39,    56,   696,    26,    27,    16,    45,    46,    33,
     177,   171,   174,   170,    65,    65,    67,    67,    17,   114,
      71,    71,   715,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,    26,    94,    95,     4,    24,   155,    99,
     100,   101,   102,   103,   104,     7,    20,   154,   109,   752,
     111,   112,    28,   114,   114,    16,   161,   162,   163,   164,
     165,   166,   167,   168,    17,    24,    16,    33,    17,    19,
       9,    10,    11,   178,   179,    25,    15,   174,    17,   154,
     783,    64,    17,   317,   144,   145,   146,   529,    27,     9,
      10,    11,    12,   312,    14,     9,    10,    11,    12,    29,
      14,    37,    38,    39,    79,   196,    26,    57,    58,    45,
      46,   734,    26,   621,   688,    65,    87,    67,   150,   151,
     152,    71,   182,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,   738,   369,    95,   369,   754,   792,    99,
     100,   101,   102,   103,   104,     9,    10,    11,    12,   807,
      14,   823,   360,   389,   114,     9,    10,    11,    12,   124,
      14,   377,    26,    17,    -1,    -1,    16,    -1,    -1,    19,
      20,    -1,    -1,    -1,    -1,    25,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   144,   145,   146,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   150,   151,   152,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    65,    -1,    67,    -1,    -1,
      -1,    71,   182,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,    -1,    -1,    95,    -1,    -1,    -1,    99,
     100,   101,   102,   103,   104,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   114,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    16,    -1,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    25,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   144,   145,   146,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    65,    -1,    67,    -1,    -1,
      -1,    71,   182,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,    93,    -1,    95,    -1,    -1,    -1,    99,
     100,   101,   102,   103,   104,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   114,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    16,    -1,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    25,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   144,   145,   146,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    65,    -1,    67,    -1,    -1,
      -1,    71,   182,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,    93,    -1,    95,    -1,    -1,    -1,    99,
     100,   101,   102,   103,   104,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   114,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    16,    -1,    16,    19,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    25,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    35,   144,   145,   146,    -1,    40,    41,
      42,    43,    44,    -1,    -1,    47,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    65,    -1,    67,    -1,    -1,
      -1,    71,   182,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,    -1,    16,    95,    -1,    19,    -1,    99,
     100,   101,   102,   103,   104,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   114,    35,   114,    -1,    -1,    -1,    40,    41,
      42,    43,    44,    16,    -1,    47,    19,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    35,    -1,   144,   145,   146,    40,    41,    42,
      43,    44,    -1,    -1,    47,    -1,    -1,    -1,    -1,   161,
     162,   163,   164,   165,   166,   167,   168,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   178,   179,    -1,    -1,
      -1,    -1,   182,    -1,    -1,    -1,     6,    -1,    -1,    -1,
      -1,    -1,   114,    -1,    -1,    -1,    16,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   114,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   161,
     162,   163,   164,   165,   166,   167,   168,    -1,    -1,    -1,
      -1,    -1,    -1,    73,    74,    -1,   178,   179,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   161,   162,
     163,   164,   165,   166,   167,   168,    96,    97,     6,    -1,
      -1,    -1,    -1,    -1,    -1,   178,   179,    -1,    16,    -1,
      -1,    -1,    -1,    -1,    -1,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   139,
     140,   141,   142,   143,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     160,    -1,    -1,    -1,    -1,    73,    74,    -1,    -1,     6,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    16,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    96,    97,
      -1,    28,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,   139,   140,   141,   142,   143,    73,    74,    -1,    -1,
       6,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      16,    -1,   160,    -1,    -1,    -1,    -1,    94,    -1,    96,
      97,    -1,    28,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,   139,   140,   141,   142,   143,    73,    74,    -1,
      -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,   155,    -1,
      -1,    16,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,   139,   140,   141,   142,   143,    73,    74,
      -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,   155,
      -1,    -1,    16,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,    73,
      74,    -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,
     155,    -1,    -1,    16,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,   139,   140,   141,   142,   143,
      73,    74,    -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,
      -1,   155,    -1,    -1,    16,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    96,    97,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,   139,   140,   141,   142,
     143,    73,    74,    -1,    -1,    -1,    -1,    -1,    -1,     6,
      -1,    -1,   155,    -1,    -1,    -1,    -1,    -1,    -1,    16,
      -1,    -1,    -1,    -1,    96,    97,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,   140,   141,
     142,   143,    69,    70,    -1,    -1,    73,    74,    -1,    -1,
       6,    -1,    -1,   155,    -1,    -1,    -1,    -1,    -1,    -1,
      16,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    96,
      97,    -1,    -1,    -1,    -1,    -1,    -1,    33,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,   139,   140,   141,   142,   143,    73,    74,    -1,
      -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    16,    17,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,   139,   140,   141,   142,   143,    73,    74,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     6,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    16,    -1,    -1,
      -1,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,    68,
      -1,    -1,    -1,    -1,    73,    74,    -1,    -1,     6,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    16,    17,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    96,    97,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
     139,   140,   141,   142,   143,    73,    74,    -1,    -1,     6,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    16,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    96,    97,
      -1,    -1,    -1,    -1,    -1,    -1,    33,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,   139,   140,   141,   142,   143,    73,    74,    -1,    -1,
       6,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      16,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    96,
      97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,   139,   140,   141,   142,   143,    73,    74,    -1,
      -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    16,    17,    -1,    -1,    -1,    -1,    -1,    94,    -1,
      96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   124,   125,
     126,   127,   128,   129,   130,   131,   132,   133,   134,   135,
     136,   137,   138,   139,   140,   141,   142,   143,    73,    74,
      -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    16,    17,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,    73,
      74,    -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    16,    17,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,   139,   140,   141,   142,   143,
      73,    74,    -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    16,    17,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    96,    97,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,   139,   140,   141,   142,
     143,    73,    74,    -1,    -1,     6,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    16,    -1,    -1,    -1,    20,
      -1,    -1,    -1,    -1,    96,    97,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,   140,   141,
     142,   143,    73,    74,    -1,    -1,    -1,     6,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    16,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    96,    97,    26,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   138,   139,   140,
     141,   142,   143,    -1,    73,    74,    -1,    -1,    -1,     6,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    16,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    96,    97,    26,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
     139,   140,   141,   142,   143,    -1,    73,    74,    -1,    -1,
      -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    16,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    96,
      97,    26,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,   116,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   132,   133,   134,   135,   136,
     137,   138,   139,   140,   141,   142,   143,    -1,    73,    74,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,     6,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    16,    -1,    -1,
      -1,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,    68,
      -1,    -1,    -1,    -1,    73,    74,    -1,    -1,     6,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    16,    17,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    96,    97,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,   115,   116,   117,   118,
     119,   120,   121,   122,   123,   124,   125,   126,   127,   128,
     129,   130,   131,   132,   133,   134,   135,   136,   137,   138,
     139,   140,   141,   142,   143,    73,    74,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    16,    -1,    -1,    -1,    96,    97,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   115,   116,   117,
     118,   119,   120,   121,   122,   123,   124,   125,   126,   127,
     128,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,   139,   140,   141,   142,   143,    68,    -1,    -1,    -1,
      -1,    73,    74,    -1,    -1,     6,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    16,    17,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    96,    97,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,   140,   141,
     142,   143,    73,    74,    -1,    -1,     6,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    16,    17,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    96,    97,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,   115,   116,   117,   118,   119,   120,
     121,   122,   123,   124,   125,   126,   127,   128,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   138,   139,   140,
     141,   142,   143,    73,    74,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    16,    -1,    -1,    -1,    96,    97,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   136,   137,   138,   139,
     140,   141,   142,   143,    68,    -1,    -1,    -1,    -1,    73,
      74,    -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    16,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    96,    97,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   115,   116,   117,   118,   119,   120,   121,   122,   123,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   135,   136,   137,   138,   139,   140,   141,   142,   143,
      73,    74,    -1,    -1,     6,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    16,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    96,    97,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,   115,   116,   117,   118,   119,   120,   121,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   137,   138,   139,   140,   141,   142,
     143,    73,    74,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    96,    97,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,   115,   116,   117,   118,   119,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,   140,   141,
     142,   143
  };
  }


  static class YyTokenClass {
  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  public static final short
  yystos_[] =
  {
         0,     3,     5,    30,    31,   169,   187,   188,   189,   190,
     191,   199,   201,   114,   114,   114,   171,   200,   299,   114,
     188,     0,   188,    32,    52,    53,    60,    64,   202,   203,
     204,   205,   212,   214,   221,   229,     6,   155,     6,   114,
     206,   207,    95,   113,   114,   182,   213,   285,   287,   288,
     289,   290,   114,   215,   114,   222,   223,    51,   114,   230,
     231,   272,     4,   204,     8,     9,    10,    11,    14,    16,
      19,   114,   171,   192,   193,   299,   170,     6,    34,    33,
      16,   114,   263,     6,    48,    48,    48,    33,    16,    19,
      25,    65,    67,    71,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    86,    87,    88,
      89,    90,    91,    92,    95,    99,   100,   101,   102,   103,
     104,   114,   144,   145,   146,   182,   233,   235,   237,   240,
     241,   242,   243,   244,   245,   246,   247,   248,   249,   252,
     253,   254,   255,   256,   257,   258,   259,   260,   262,   263,
     265,    48,   230,   230,   114,   114,   173,   189,   194,    25,
      25,    25,    25,   171,   193,    25,    16,     7,     9,    10,
      11,    12,    14,    16,    19,    35,    40,    41,    42,    43,
      44,    47,   161,   162,   163,   164,   165,   166,   167,   168,
     178,   179,   208,   263,   114,   208,   209,   210,   207,   285,
     286,   105,    16,   233,   208,   180,    47,   208,   224,   222,
     233,    20,   232,   233,    26,    94,   232,   233,   250,   251,
     181,   234,   233,   233,   233,   233,   233,   233,   233,   233,
     233,   233,   233,   233,   233,   233,   233,   233,   233,   233,
     233,   233,   233,   233,    16,   263,   285,   293,   298,    16,
      16,   161,   162,   163,   164,   165,   166,   167,   168,   261,
     263,    16,    16,   106,   286,   294,   295,   296,   297,   294,
     285,   291,   292,   293,     6,    16,    73,    74,    96,    97,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   142,   143,   208,
       6,   114,     7,   171,   171,   171,   171,     7,    17,   170,
     233,   193,   193,   193,   193,   170,   193,   208,   208,   114,
     208,   208,   208,   208,   208,    45,    46,    37,    38,    45,
      46,    51,   211,    48,    49,   211,   209,   155,    17,   114,
      17,   286,    33,     6,   114,   110,   110,   114,    17,    20,
      28,   155,    26,    26,    28,    94,   155,    26,    28,   155,
     155,    66,    68,   160,   233,    16,    48,   155,   147,   233,
     233,    16,    16,   232,   233,   263,    48,   128,   147,   155,
     147,   128,   147,   233,   232,   233,   233,   233,   114,    98,
     233,   233,   233,   233,   233,   233,   233,   233,   233,   233,
     233,   233,   233,   233,   233,   233,   233,   233,   233,   233,
     233,   233,   233,   233,   233,   233,   233,   233,   233,   108,
       9,    10,    11,    19,    21,    22,    23,   114,   150,   157,
     159,   171,   172,   175,   176,   195,   196,   197,     6,   196,
       7,     7,     7,     7,   192,    19,    20,    17,    13,    17,
      20,    36,    39,    39,   208,   208,   208,   208,   208,   208,
     285,   208,   208,   286,    17,   213,   233,    16,   216,    47,
     208,    47,   208,   216,   285,   292,   232,   294,   233,    93,
     294,   233,   250,   251,   234,   233,   233,   238,   239,   286,
     155,   232,   208,   298,   233,    16,   155,   233,   233,    17,
     155,   208,   233,   233,   295,   233,   233,   233,    17,   155,
     233,    25,    25,    25,   114,   198,   173,   173,   171,     7,
      24,   147,   148,    19,     9,    10,    11,    15,    27,   150,
     151,   152,   174,     4,   193,   193,   193,   193,    17,   299,
     171,   193,   209,   208,   208,    50,    33,   286,    50,    50,
      20,   147,    26,   147,   155,    26,   147,    69,    70,   236,
       4,    72,   238,    24,   233,    17,   232,   208,    17,    17,
     233,    93,   171,   171,   171,   156,    20,    28,   158,     7,
     195,   197,   197,   197,   299,   197,   197,   197,   197,   197,
     114,   174,   197,   198,    26,    26,    26,    26,    16,    18,
       7,     4,   233,   213,    17,    57,    58,   217,   233,    16,
      19,    25,    57,    59,    65,    67,    71,   109,   111,   112,
     225,   263,   266,   267,   269,   273,   274,   276,   277,   279,
     282,   284,   233,   233,   233,   233,   233,   233,    24,   233,
      17,    17,    17,    17,   155,     7,     7,     7,   114,    16,
     177,    16,    19,    18,   149,    28,   153,   233,   299,   193,
     216,    54,   218,   107,   267,   268,   270,   183,   101,   259,
     263,   283,   234,   233,   233,    16,   233,    61,   226,    16,
      96,   108,    20,    26,    26,    26,    68,   233,    17,   233,
     197,   197,   197,   155,   171,   154,   171,   173,   299,   197,
     170,    17,    20,    26,   233,    55,   219,   271,   272,    33,
      17,   268,    20,    26,    16,    96,    66,    68,   160,   274,
      62,    63,   227,   228,   218,   233,   114,   233,   233,    17,
      26,    26,    26,   198,     7,    16,     7,    28,    20,    28,
      29,   233,    56,   220,   155,   268,    17,    16,   232,   114,
     263,   267,   267,   280,   281,   286,    33,   263,   264,   219,
      17,   236,   197,   171,   197,   177,   174,   170,   233,   271,
     232,    17,    26,    69,    70,   278,     4,    72,   238,    24,
     274,   275,   155,    48,   227,    17,     7,    17,    20,   154,
      28,    17,    16,   233,   267,    24,   267,    33,    17,   264,
     208,   197,   197,   197,   174,   232,    68,   267,   275,   227,
      17,   154,    17,   267,   197,   278
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
     435,   436,   437,   438,   439,   440
  };
  }

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */

  private static final short
  yyr1_[] =
  {
         0,   186,   187,   187,   188,   188,   189,   189,   189,   189,
     190,   191,   192,   192,   193,   193,   193,   193,   193,   193,
     193,   193,   193,   193,   193,   193,   193,   193,   193,   194,
     194,   194,   195,   195,   196,   196,   196,   196,   197,   197,
     197,   197,   197,   197,   197,   197,   197,   197,   197,   197,
     197,   197,   197,   197,   197,   197,   197,   197,   197,   197,
     197,   198,   198,   199,   200,   200,   201,   202,   202,   203,
     203,   204,   204,   204,   204,   204,   205,   205,   205,   206,
     206,   207,   207,   207,   208,   208,   208,   208,   208,   208,
     208,   208,   208,   208,   208,   208,   208,   208,   208,   208,
     208,   208,   208,   208,   208,   208,   208,   208,   208,   209,
     209,   210,   210,   210,   211,   212,   213,   213,   213,   214,
     215,   216,   216,   217,   217,   217,   218,   218,   219,   219,
     220,   220,   221,   222,   222,   223,   224,   224,   224,   224,
     225,   225,   225,   226,   227,   227,   227,   228,   228,   229,
     230,   230,   230,   231,   232,   232,   233,   233,   233,   233,
     233,   233,   233,   233,   233,   233,   233,   233,   233,   233,
     233,   233,   233,   233,   233,   233,   233,   233,   233,   233,
     233,   233,   233,   233,   233,   234,   234,   235,   236,   236,
     237,   238,   238,   238,   239,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   240,   240,   241,   241,   241,   241,   241,
     241,   241,   241,   241,   241,   241,   241,   241,   241,   241,
     241,   241,   241,   241,   241,   241,   241,   241,   241,   241,
     241,   241,   241,   241,   241,   241,   241,   242,   242,   242,
     243,   243,   244,   244,   245,   246,   246,   247,   247,   248,
     249,   249,   250,   250,   251,   252,   252,   253,   254,   255,
     256,   257,   258,   259,   260,   260,   260,   261,   261,   261,
     261,   261,   261,   261,   261,   262,   263,   263,   264,   264,
     265,   266,   266,   266,   267,   267,   267,   267,   267,   267,
     267,   267,   267,   268,   268,   269,   269,   270,   271,   271,
     272,   272,   273,   273,   274,   275,   275,   276,   277,   278,
     278,   279,   280,   280,   280,   281,   282,   282,   283,   283,
     283,   283,   283,   284,   285,   285,   285,   285,   286,   286,
     287,   287,   288,   289,   290,   290,   291,   291,   292,   293,
     294,   294,   295,   295,   296,   297,   298,   298,   299,   299
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     2,     1,     2,     1,     1,     1,     1,
       5,     4,     3,     1,     5,     3,     3,     3,     5,     3,
       8,     4,     1,     8,     6,     6,     6,     8,     6,     1,
       3,     4,     1,     3,     1,     1,     1,     6,     1,     1,
       1,     3,     3,     3,     3,     4,     3,     3,     3,     5,
       3,     3,     9,    11,     6,     6,     6,     8,    10,     9,
       8,     3,     5,     2,     1,     1,     4,     1,     0,     1,
       2,     1,     1,     1,     1,     1,     1,     3,     2,     3,
       1,     4,     3,     4,     3,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     5,     3,     3,     3,     2,     2,
       2,     4,     4,     3,     3,     3,     3,     1,     1,     1,
       2,     1,     3,     3,     4,     2,     5,     7,     0,     2,
      10,     3,     4,     1,     1,     1,     2,     0,     2,     0,
       2,     0,     2,     3,     0,    10,     3,     3,     3,     3,
       1,     1,     1,     2,     3,     5,     0,     1,     1,     2,
       2,     2,     0,     2,     1,     3,     3,     4,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     6,     1,     1,     1,     1,     3,     5,     2,     5,
       5,     1,     4,     2,     3,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     4,     4,     4,
       2,     3,     5,     7,     7,     2,     3,     5,     7,     8,
       3,     3,     1,     3,     3,     5,     7,     6,     5,     4,
       3,     3,     4,     7,     5,     5,     6,     1,     1,     1,
       1,     1,     1,     1,     1,     4,     1,     3,     1,     3,
       2,     1,     3,     4,     1,     4,     1,     1,     1,     1,
       1,     2,     1,     1,     3,     3,     4,     2,     1,     3,
       3,     5,     1,     1,     3,     1,     3,     6,     5,     2,
       5,     5,     1,     4,     2,     3,     6,     8,     1,     1,
       1,     3,     4,     3,     1,     1,     1,     1,     1,     3,
       1,     1,     1,     4,     4,     5,     1,     1,     3,     3,
       1,     3,     1,     1,     3,     3,     1,     3,     1,     3
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "CLASS", "END", "PROCESS", "EQUALS",
  "AT", "BEGIN", "CSPSEQ", "CSPINTCH", "CSPEXTCH", "CSPLCHSYNC",
  "CSPRCHSYNC", "CSPINTERLEAVE", "CSPHIDE", "LPAREN", "RPAREN",
  "CSPRENAME", "LSQUARE", "RSQUARE", "CSPSKIP", "CSPSTOP", "CSPCHAOS",
  "RARROW", "LCURLY", "RCURLY", "CSPAND", "BAR", "DBAR", "CHANNEL",
  "CHANSET", "TYPES", "SEMI", "VDMRECORDDEF", "VDMCOMPOSE", "OF",
  "VDMTYPEUNION", "STAR", "TO", "VDMINMAPOF", "VDMMAPOF", "VDMSEQOF",
  "VDMSEQ1OF", "VDMSETOF", "VDMPFUNCARROW", "VDMTFUNCARROW", "VDMUNITTYPE",
  "VDMTYPE", "VDMTYPENCMP", "DEQUALS", "INV", "VALUES", "FUNCTIONS", "PRE",
  "POST", "MEASURE", "VDMSUBCLASSRESP", "VDMNOTYETSPEC", "VDMNOTYETDEF",
  "OPERATIONS", "EXT", "VDMRD", "VDMWR", "INSTANCEVARS", "LET", "IN", "IF",
  "THEN", "ELSEIF", "ELSE", "CASES", "OTHERS", "PLUS", "MINUS", "ABS",
  "FLOOR", "NOT", "CARD", "POWER", "DUNION", "DINTER", "HD", "TL", "LEN",
  "ELEMS", "INDS", "REVERSE", "DCONC", "DOM", "RNG", "MERGE", "INVERSE",
  "ELLIPSIS", "MAPLETARROW", "MKUNDER", "DOT", "DOTHASH", "NUMERAL",
  "LAMBDA", "NEW", "SELF", "ISUNDER", "PREUNDER", "ISOFCLASS", "BACKTICK",
  "TILDE", "DCL", "ASSIGN", "ATOMIC", "OPERATIONARROW", "RETURN", "SKIP",
  "VDMWHATEVER", "IDENTIFIER", "DIVIDE", "DIV", "REM", "MOD", "LT", "LTE",
  "GT", "GTE", "NEQ", "OR", "AND", "IMPLY", "BIMPLY", "INSET", "NOTINSET",
  "SUBSET", "PROPER_SUBSET", "UNION", "SETDIFF", "INTER", "CONC",
  "OVERWRITE", "MAPMERGE", "DOMRES", "DOMSUB", "RNGRES", "RNGSUB", "COMP",
  "ITERATE", "FORALL", "EXISTS", "EXISTS1", "AMP", "THREEBAR", "CSPBARGT",
  "CSPLSQUAREBAR", "CSPLSQUAREGT", "DLSQUARE", "DRSQUARE", "CSPBARRSQUARE",
  "COMMA", "CSPSAMEAS", "CSPLSQUAREDBAR", "CSPDBARRSQUARE", "CSPDBAR",
  "COLON", "TBOOL", "TNAT", "TNAT1", "TINT", "TRAT", "TREAL", "TCHAR",
  "TTOKEN", "globalDef", "chansetExpr", "declaration", "VDMcommand",
  "nameset", "namesetExpr", "communication", "predicate", "chanset",
  "typeVarIdentifier", "quoteLiteral", "functionType", "localDef",
  "symbolicLiteral", "implicitOperationBody", "UMINUS", "UPLUS", "$accept",
  "document", "paragraphList", "paragraph", "classDef", "processDef",
  "processDecl", "process", "processPara", "paragraphAction", "action",
  "cspAction", "renameList", "channelDef", "channelDefList", "chansetDef",
  "classBody", "definitionBlock", "definitionBlockAlternative", "typeDefs",
  "typeDefList", "typeDef", "type", "fieldList", "field", "invariant",
  "valueDefs", "valueDefList", "functionDefs", "functionDefList",
  "parameterList", "functionBody", "preExpr", "postExpr", "measureExpr",
  "operationDefs", "operationDefList", "operationDef", "operationType",
  "operationBody", "externals", "varInformationList", "mode",
  "instanceVarDefs", "instanceVarDefList", "invariantDef",
  "expressionList", "expression", "localDefList", "ifExpr", "elseExprs",
  "casesExpr", "casesExprAltList", "casesExprAlt", "unaryExpr",
  "binaryExpr", "quantifiedExpr", "setEnumeration", "setComprehension",
  "setRangeExpr", "sequenceEnumeration", "sequenceComprehension",
  "subsequence", "mapEnumeration", "mapletList", "maplet",
  "mapComprehension", "tupleConstructor", "recordConstructor", "apply",
  "fieldSelect", "tupleSelect", "lambdaExpr", "newExpr", "generalIsExpr",
  "basicType", "preconditionExpr", "name", "nameList", "oldName",
  "stateDesignator", "statement", "statementList", "blockStatement",
  "dclStatement", "assignmentDefList", "assignmentDef",
  "generalAssignStatement", "assignStatement", "assignStatementList",
  "multiAssignStatement", "ifStatement", "elseStatements",
  "casesStatement", "casesStatementAltList", "casesStatementAlt",
  "callStatement", "objectDesignator", "specificationStatement", "pattern",
  "patternList", "patternIdentifier", "matchValue", "tuplePattern",
  "recordPattern", "bind", "setBind", "typeBind", "bindList",
  "multipleBind", "multipleSetBind", "multipleTypeBind", "typeBindList",
  "identifierList", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final short yyrhs_[] =
  {
       187,     0,    -1,   188,    -1,   169,   188,    -1,   189,    -1,
     189,   188,    -1,   190,    -1,   191,    -1,   199,    -1,   201,
      -1,     3,   114,   202,     4,   114,    -1,     5,   114,     6,
     192,    -1,   171,     7,   193,    -1,   193,    -1,     8,   194,
       7,   196,     4,    -1,   193,     9,   193,    -1,   193,    10,
     193,    -1,   193,    11,   193,    -1,   193,    12,   170,    13,
     193,    -1,   193,    14,   193,    -1,    16,   171,     7,   192,
      17,    16,   233,    17,    -1,   114,    16,   233,    17,    -1,
     114,    -1,    16,   193,    17,    19,   299,    18,   299,    20,
      -1,     9,    25,   171,     7,   193,    26,    -1,    10,    25,
     171,     7,   193,    26,    -1,    11,    25,   171,     7,   193,
      26,    -1,    19,    25,   170,    20,   171,     7,   193,    26,
      -1,    14,    25,   171,     7,   193,    26,    -1,   189,    -1,
     114,     6,   195,    -1,   173,   114,     6,   174,    -1,   196,
      -1,   171,     7,   195,    -1,   172,    -1,   114,    -1,   197,
      -1,   196,    19,   299,    18,   299,    20,    -1,    21,    -1,
      22,    -1,    23,    -1,   175,    24,   197,    -1,   197,     9,
     197,    -1,   197,    27,   197,    -1,   197,    15,   197,    -1,
     197,   152,   198,   153,    -1,   197,    10,   197,    -1,   197,
      11,   197,    -1,   197,   151,   197,    -1,   197,   150,   114,
     149,   197,    -1,   176,   147,   197,    -1,   176,   148,   197,
      -1,   197,   150,   174,    28,   170,    28,   174,   154,   197,
      -1,   197,   150,   174,    28,   170,    29,   170,    28,   174,
     154,   197,    -1,     9,    25,   171,     7,   197,    26,    -1,
      10,    25,   171,     7,   197,    26,    -1,    11,    25,   171,
       7,   197,    26,    -1,   157,   173,   158,    16,   171,     7,
     197,    17,    -1,   150,   173,    28,   177,   154,    16,   171,
       7,   197,    17,    -1,   159,   171,     7,    19,   173,    28,
     177,    20,   197,    -1,    19,   198,    20,    16,   171,     7,
     197,    17,    -1,   114,   156,   114,    -1,   114,   156,   114,
     155,   198,    -1,    30,   200,    -1,   299,    -1,   171,    -1,
      31,   114,     6,   170,    -1,   203,    -1,    -1,   204,    -1,
     203,   204,    -1,   205,    -1,   212,    -1,   214,    -1,   221,
      -1,   229,    -1,    32,    -1,    32,   206,    33,    -1,    32,
     206,    -1,   206,    33,   207,    -1,   207,    -1,   114,     6,
     208,   211,    -1,   114,     6,   208,    -1,   114,    34,   209,
     211,    -1,    16,   208,    17,    -1,   161,    -1,   162,    -1,
     163,    -1,   164,    -1,   165,    -1,   166,    -1,   167,    -1,
     168,    -1,   179,    -1,    35,   114,    36,   209,     4,    -1,
     208,    37,   208,    -1,   208,    38,   208,    -1,    19,   208,
      20,    -1,    44,   208,    -1,    42,   208,    -1,    43,   208,
      -1,    41,   208,    39,   208,    -1,    40,   208,    39,   208,
      -1,   208,    45,   208,    -1,    47,    45,   208,    -1,   208,
      46,   208,    -1,    47,    46,   208,    -1,   263,    -1,   178,
      -1,   210,    -1,   210,   209,    -1,   208,    -1,   114,    48,
     208,    -1,   114,    49,   208,    -1,    51,   285,    50,   233,
      -1,    52,   213,    -1,   285,     6,   233,    33,   213,    -1,
     285,    48,   208,     6,   233,    33,   213,    -1,    -1,    53,
     215,    -1,   114,    48,   180,   114,   216,    50,   217,   218,
     219,   220,    -1,    16,   286,    17,    -1,    16,   286,    17,
     216,    -1,   233,    -1,    57,    -1,    58,    -1,    54,   233,
      -1,    -1,    55,   233,    -1,    -1,    56,   233,    -1,    -1,
      60,   222,    -1,   223,    33,   222,    -1,    -1,   114,    48,
     224,   114,   216,    50,   225,   226,   218,   219,    -1,   208,
     110,   208,    -1,    47,   110,   208,    -1,   208,   110,    47,
      -1,    47,   110,    47,    -1,   267,    -1,    57,    -1,    59,
      -1,    61,   227,    -1,   228,   264,   227,    -1,   228,   264,
      48,   208,   227,    -1,    -1,    62,    -1,    63,    -1,    64,
     230,    -1,   272,   230,    -1,   231,   230,    -1,    -1,    51,
     233,    -1,   233,    -1,   233,   155,   232,    -1,    16,   233,
      17,    -1,    65,   234,    66,   233,    -1,   235,    -1,   237,
      -1,   240,    -1,   241,    -1,   242,    -1,   243,    -1,   244,
      -1,   245,    -1,   246,    -1,   247,    -1,   248,    -1,   249,
      -1,   252,    -1,   253,    -1,   254,    -1,   255,    -1,   256,
      -1,   257,    -1,   258,    -1,   259,    -1,   101,    -1,   260,
      -1,   262,    -1,   104,    16,   263,   155,   233,    17,    -1,
     263,    -1,   265,    -1,   182,    -1,   181,    -1,   181,   155,
     234,    -1,    67,   233,    68,   233,   236,    -1,    70,   233,
      -1,    69,   233,    68,   233,   236,    -1,    71,   233,   160,
     238,     4,    -1,   239,    -1,   239,    72,    24,   233,    -1,
     239,   238,    -1,   286,    24,   233,    -1,    73,   233,    -1,
      74,   233,    -1,    75,   233,    -1,    76,   233,    -1,    77,
     233,    -1,    78,   233,    -1,    79,   233,    -1,    80,   233,
      -1,    81,   233,    -1,    82,   233,    -1,    83,   233,    -1,
      84,   233,    -1,    85,   233,    -1,    86,   233,    -1,    87,
     233,    -1,    88,   233,    -1,    89,   233,    -1,    90,   233,
      -1,    91,   233,    -1,    92,   233,    -1,   233,    73,   233,
      -1,   233,    74,   233,    -1,   233,   115,   233,    -1,   233,
     116,   233,    -1,   233,   117,   233,    -1,   233,   118,   233,
      -1,   233,   119,   233,    -1,   233,   120,   233,    -1,   233,
     121,   233,    -1,   233,   122,   233,    -1,   233,     6,   233,
      -1,   233,   123,   233,    -1,   233,   124,   233,    -1,   233,
     125,   233,    -1,   233,   126,   233,    -1,   233,   127,   233,
      -1,   233,   128,   233,    -1,   233,   129,   233,    -1,   233,
     130,   233,    -1,   233,   131,   233,    -1,   233,   132,   233,
      -1,   233,   133,   233,    -1,   233,   134,   233,    -1,   233,
     135,   233,    -1,   233,   136,   233,    -1,   233,   137,   233,
      -1,   233,   138,   233,    -1,   233,   139,   233,    -1,   233,
     140,   233,    -1,   233,   141,   233,    -1,   233,   142,   233,
      -1,   233,   143,   233,    -1,   144,   294,   147,   233,    -1,
     145,   294,   147,   233,    -1,   146,   291,   147,   233,    -1,
      25,    26,    -1,    25,   232,    26,    -1,    25,   233,    28,
     294,    26,    -1,    25,   233,    28,   294,   147,   233,    26,
      -1,    25,   233,   155,    93,   155,   233,    26,    -1,    19,
      20,    -1,    19,   232,    20,    -1,    19,   233,    28,   292,
      20,    -1,    19,   233,    28,   292,   147,   233,    20,    -1,
     233,    16,   233,   155,    93,   155,   233,    17,    -1,    25,
      94,    26,    -1,    25,   250,    26,    -1,   251,    -1,   251,
     155,   250,    -1,   233,    94,   233,    -1,    25,   251,    28,
     294,    26,    -1,    25,   251,    28,   294,   147,   233,    26,
      -1,    95,    16,   233,   155,   233,    17,    -1,    95,   263,
      16,   232,    17,    -1,   233,    16,   232,    17,    -1,   233,
      96,   114,    -1,   233,    97,    98,    -1,    99,   298,   147,
     233,    -1,   100,    16,   233,    16,   232,    17,    17,    -1,
     102,   263,    16,   233,    17,    -1,   102,   261,    16,   233,
      17,    -1,   102,    16,   233,   155,   208,    17,    -1,   161,
      -1,   162,    -1,   163,    -1,   164,    -1,   165,    -1,   166,
      -1,   167,    -1,   168,    -1,   103,    16,   232,    17,    -1,
     114,    -1,   114,   105,   114,    -1,   263,    -1,   263,   155,
     264,    -1,   114,   106,    -1,   263,    -1,   266,    96,   114,
      -1,   266,    16,   233,    17,    -1,   112,    -1,    65,   234,
      66,   267,    -1,   269,    -1,   273,    -1,   277,    -1,   279,
      -1,   282,    -1,   111,   233,    -1,   284,    -1,   267,    -1,
     267,    33,   268,    -1,    16,   268,    17,    -1,    16,   270,
     268,    17,    -1,   107,   271,    -1,   272,    -1,   272,   155,
     271,    -1,   114,    48,   208,    -1,   114,    48,   208,   108,
     233,    -1,   274,    -1,   276,    -1,   266,   108,   233,    -1,
     274,    -1,   274,    33,   275,    -1,   109,    16,   274,    33,
     275,    17,    -1,    67,   233,    68,   267,   278,    -1,    70,
     267,    -1,    69,   233,    68,   267,   278,    -1,    71,   233,
     160,   280,     4,    -1,   281,    -1,   281,    72,    24,   267,
      -1,   281,   238,    -1,   286,    24,   267,    -1,    25,   263,
      26,    16,   232,    17,    -1,    25,   283,    96,   263,    26,
      16,   232,    17,    -1,   101,    -1,   263,    -1,   259,    -1,
     283,    96,   114,    -1,   283,    16,   232,    17,    -1,    19,
     183,    20,    -1,   287,    -1,   288,    -1,   289,    -1,   290,
      -1,   285,    -1,   285,   155,   286,    -1,   114,    -1,   113,
      -1,   182,    -1,    95,    16,   286,    17,    -1,    95,   263,
      16,    17,    -1,    95,   263,    16,   286,    17,    -1,   292,
      -1,   293,    -1,   285,   128,   233,    -1,   285,    48,   208,
      -1,   295,    -1,   295,   155,   295,    -1,   296,    -1,   297,
      -1,   286,   128,   233,    -1,   286,    48,   208,    -1,   293,
      -1,   293,   155,   298,    -1,   114,    -1,   114,   155,   299,
      -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     8,    10,    13,    15,    17,    19,
      21,    27,    32,    36,    38,    44,    48,    52,    56,    62,
      66,    75,    80,    82,    91,    98,   105,   112,   121,   128,
     130,   134,   139,   141,   145,   147,   149,   151,   158,   160,
     162,   164,   168,   172,   176,   180,   185,   189,   193,   197,
     203,   207,   211,   221,   233,   240,   247,   254,   263,   274,
     284,   293,   297,   303,   306,   308,   310,   315,   317,   318,
     320,   323,   325,   327,   329,   331,   333,   335,   339,   342,
     346,   348,   353,   357,   362,   366,   368,   370,   372,   374,
     376,   378,   380,   382,   384,   390,   394,   398,   402,   405,
     408,   411,   416,   421,   425,   429,   433,   437,   439,   441,
     443,   446,   448,   452,   456,   461,   464,   470,   478,   479,
     482,   493,   497,   502,   504,   506,   508,   511,   512,   515,
     516,   519,   520,   523,   527,   528,   539,   543,   547,   551,
     555,   557,   559,   561,   564,   568,   574,   575,   577,   579,
     582,   585,   588,   589,   592,   594,   598,   602,   607,   609,
     611,   613,   615,   617,   619,   621,   623,   625,   627,   629,
     631,   633,   635,   637,   639,   641,   643,   645,   647,   649,
     651,   653,   660,   662,   664,   666,   668,   672,   678,   681,
     687,   693,   695,   700,   703,   707,   710,   713,   716,   719,
     722,   725,   728,   731,   734,   737,   740,   743,   746,   749,
     752,   755,   758,   761,   764,   767,   771,   775,   779,   783,
     787,   791,   795,   799,   803,   807,   811,   815,   819,   823,
     827,   831,   835,   839,   843,   847,   851,   855,   859,   863,
     867,   871,   875,   879,   883,   887,   891,   895,   900,   905,
     910,   913,   917,   923,   931,   939,   942,   946,   952,   960,
     969,   973,   977,   979,   983,   987,   993,  1001,  1008,  1014,
    1019,  1023,  1027,  1032,  1040,  1046,  1052,  1059,  1061,  1063,
    1065,  1067,  1069,  1071,  1073,  1075,  1080,  1082,  1086,  1088,
    1092,  1095,  1097,  1101,  1106,  1108,  1113,  1115,  1117,  1119,
    1121,  1123,  1126,  1128,  1130,  1134,  1138,  1143,  1146,  1148,
    1152,  1156,  1162,  1164,  1166,  1170,  1172,  1176,  1183,  1189,
    1192,  1198,  1204,  1206,  1211,  1214,  1218,  1225,  1234,  1236,
    1238,  1240,  1244,  1249,  1253,  1255,  1257,  1259,  1261,  1263,
    1267,  1269,  1271,  1273,  1278,  1283,  1289,  1291,  1293,  1297,
    1301,  1303,  1307,  1309,  1311,  1315,  1319,  1321,  1325,  1327
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   167,   167,   170,   174,   175,   179,   180,   181,   182,
     187,   210,   214,   215,   219,   220,   221,   222,   223,   224,
     225,   226,   227,   228,   229,   230,   231,   232,   233,   237,
     238,   239,   243,   244,   248,   249,   250,   251,   255,   256,
     257,   258,   259,   260,   261,   262,   263,   264,   265,   266,
     267,   268,   269,   270,   272,   273,   274,   275,   276,   277,
     278,   282,   283,   289,   293,   294,   300,   308,   313,   319,
     327,   337,   341,   345,   349,   353,   362,   366,   370,   377,
     383,   392,   393,   403,   407,   411,   415,   419,   423,   427,
     431,   435,   439,   443,   444,   445,   446,   447,   448,   449,
     450,   451,   452,   453,   454,   455,   456,   457,   458,   462,
     463,   467,   468,   469,   473,   479,   483,   484,   485,   493,
     497,   502,   503,   507,   508,   509,   513,   514,   518,   519,
     523,   524,   530,   534,   535,   541,   545,   546,   547,   548,
     552,   553,   554,   558,   563,   564,   565,   569,   570,   577,
     582,   583,   584,   588,   596,   597,   601,   602,   603,   604,
     605,   606,   607,   608,   609,   610,   611,   612,   613,   614,
     615,   616,   617,   618,   619,   620,   621,   622,   623,   624,
     625,   626,   627,   628,   629,   633,   634,   640,   644,   645,
     649,   653,   654,   655,   659,   673,   674,   675,   676,   677,
     678,   679,   680,   681,   682,   683,   684,   685,   686,   687,
     688,   689,   690,   691,   692,   702,   703,   704,   705,   706,
     707,   708,   709,   710,   711,   712,   713,   714,   715,   716,
     717,   718,   719,   720,   721,   722,   723,   724,   725,   726,
     727,   728,   729,   730,   731,   732,   733,   739,   740,   741,
     747,   748,   752,   753,   757,   763,   764,   768,   769,   773,
     777,   778,   782,   783,   787,   791,   792,   798,   804,   810,
     814,   818,   824,   832,   838,   839,   840,   844,   845,   846,
     847,   848,   849,   850,   851,   857,   863,   864,   868,   869,
     873,   880,   881,   882,   888,   889,   890,   891,   892,   893,
     894,   895,   896,   900,   901,   910,   911,   915,   919,   920,
     924,   925,   929,   930,   934,   938,   939,   943,   949,   953,
     954,   958,   962,   963,   964,   968,   977,   978,   982,   983,
     984,   985,   986,   995,  1004,  1005,  1006,  1007,  1011,  1012,
    1016,  1017,  1022,  1028,  1033,  1034,  1047,  1048,  1052,  1056,
    1060,  1061,  1065,  1066,  1070,  1074,  1078,  1079,  1085,  1086
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
     185
  };

  private static final short yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 3781;
  private static final int yynnts_ = 114;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 21;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 186;

  private static final int yyuser_token_number_max_ = 440;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 898 of cmlskeleton.java.m4  */
/* Line 24 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/Parser/cml.y"  */

    // ************************
    // *** MEMBER VARIABLES ***
    // ************************

    private List<PDefinition> documentDefs = new Vector<PDefinition>();


    // *************************
    // *** PRIVATE OPERATIONS ***
    // *************************
     
    private LexLocation extractLexLocation(CmlLexeme lexeme)
    {
	return new LexLocation(null/*File file*/, "Default",
			       lexeme.getStartPos().line, lexeme.getStartPos().column, 
			       lexeme.getEndPos().line, lexeme.getEndPos().column,0,0);
    }

    private LexLocation extractLexLocation(CmlLexeme start, CmlLexeme end)
    {
	return new LexLocation(null/*File file*/, "Default",
			       start.getStartPos().line, start.getStartPos().column, 
			       end.getEndPos().line, end.getEndPos().column,0,0);
    }

    private LexLocation extractLexLocation(CmlLexeme start, LexLocation end)
    {
	return new LexLocation(null/*File file*/, "Default",
			       start.getStartPos().line, start.getStartPos().column, 
			       end.endLine, end.endPos,0,0);
    }

    
    private LexNameToken extractLexNameToken(CmlLexeme lexeme)
    {
	return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
    }

    // *************************
    // *** PUBLIC OPERATIONS ***
    // *************************
     
    public List<PDefinition> getDocument()
    {
	return documentDefs;
    }

    public static void main(String[] args) throws Exception
    {
	if (args.length == 0) {
	    System.out.println("Usage : java CmlParser <inputfile>");
	}
	else {
      
	    CmlLexer scanner = null;
	    try {
		scanner = new CmlLexer( new java.io.FileReader(args[0]) );
		CmlParser cmlParser = new CmlParser(scanner);
		//cmlParser.setDebugLevel(1);
	  
		//do {
		//System.out.println(scanner.yylex());
		boolean result = cmlParser.parse();
		if (result){
		    System.out.println("parsed!");
		    System.out.println(cmlParser.getDocument());
		}
		else
		    System.out.println("Not parsed!");
		
		//} while (!scanner.zzAtEOF);

	    }
	    catch (java.io.FileNotFoundException e) {
		System.out.println("File not found : \""+args[0]+"\"");
	    }
	    catch (java.io.IOException e) {
		System.out.println("IO error scanning file \""+args[0]+"\"");
		System.out.println(e);
	    }
	    catch (Exception e) {
		System.out.println("Unexpected exception:");
		e.printStackTrace();
	    }
      
	}
    
  }

  // the abstract syntax element
  //public OmlDocument astDocument = null;
  
  /* public void parseDocument() //throws CGException */
  /* { */
  /*   // create the top-level AST element */
  /*   //astDocument = new OmlDocument(); */
  /*   // link the scanner to the document (for the tokens) */
  /*   //theScanner.setLexems(astDocument.getLexems()); */
  /*   // go parse the file */
  /*   yyparse(); */
  /* } */
 


/* Line 898 of cmlskeleton.java.m4  */
/* Line 3142 of "/media/C/sandbox/overture-workspace/overture/branches/astV2/CML/ToBeMoved/compiler/src/main/java/eu/compassresearch/cml/compiler/CmlParser.java"  */

}


