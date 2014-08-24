package eu.compassresearch.ide.refinementtool.maude

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import scala.util.matching._
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.combinator.JavaTokenParsers
import org.overture.ast.node.INode
import eu.compassresearch.ide.refinementtool.Refinement
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation
import eu.compassresearch.ide.refinementtool.RefUtils
import eu.compassresearch.ide.refinementtool.CmlRefineProvisoObligation
import eu.compassresearch.ide.refinementtool.CmlActionRefineProvisoObligation

class MaudeRefiner(cmd: String, refine: String) {

  val maude = new Maude(cmd);
  maude.load(refine);
  
  // Not all Maude output follows strict CML; we used matchers to replace them
  val replacers: Map[String, String] = Map.empty +
    ( "\\#b\\(true\\)"  -> "true"
    , "\\#b\\(false\\)" -> "false"
    , "\\#n\\((\\d+)\\)" -> "$1"
    , "\\#nm\\(\"(\\w+~?)\"\\)" -> "$1" 
    , "\\#anm\\(\"(\\w+)\"\\)" -> "$1"
    , "=def=" -> "="
    , "\\#paren" -> ""
    , "\\#c\\(\"(\\w+)\"\\)" -> "$1"
    , "\\[\\$" -> ""
    , "\\$\\]" -> ""
    , "==" -> "="
  ) 

  object MetaSetParser extends JavaTokenParsers {
    val stringParser = stringLiteral ^^ {str => str.substring(1, str.length - 1)}

    val stringPair = 
      stringParser ~ elem(',') ~ stringParser ^^ { case x ~ y ~ z => Pair(x, z) } 

    val metaParser = "#meta\\(".r ~> stringPair <~ "\\)".r

    val metaSetParser = elem('{') ~> repsep(metaParser, elem(',')) <~ elem('}')

    def apply(input: String): Option[List[Pair[String,String]]] = 
      parseAll(metaSetParser, input.replaceAll(" ", "")) match {
        case Success(result, _) => Some(result)
        case NoSuccess(_, _) => None
      }
  }    
    
    
  def varSet(inp: String): Map[String, String] = {
    Map.empty ++ MetaSetParser.apply(inp).get
  }
  
    
  def findApplLaws(cml: String) : java.util.List[MaudeRefineInfo] = {
    val mts = maude.search1("refs[" + cml + "]", "rinf[NM,DS,LD,INP]");
    // val inp  = m.get("INP").get;
    
    
    return mts.map(m => new MaudeRefineInfo( m.get("NM").get.tail.init // Trim off leading/trailing quotes
    									   , m.get("DS").get.tail.init
    									   , m.get("LD").get.tail.init
    									   , varSet(m.get("INP").get))).to[ListBuffer]
  }
  
  def applyLaw(cml: String, mri: MaudeRefineInfo, met: scala.collection.mutable.Map[String,String]): Refinement = {
    
    val metas = met.toMap
    
    // val v = mri.vars(x._1).g
    
    // Create the meta variable map
    val M = metas.foldLeft("empty")((m, x) => "insert(\"" + x._1 + "\"," 
                                                     + (if (mri.vars(x._1).toLowerCase().contains("exp") ||mri.vars(x._1).toLowerCase().contains("varset")) "exp" else "act") 
                                                     + "(" + x._2 + ")" + "," + m + ")")
   
    
    var mts = maude.search1("refine[\"" + mri.key + "\", < " + cml + " | " + M + " | p > ]", "< A | M | p >");
    var pogs: Array[String] = null;
    val ps = new java.util.LinkedList[CmlProofObligation]()
    var res: String = "";
    if (mts.size > 0) {
      pogs = mts.head.get("p").get.split("and")
      // pogs.to[ListBuffer]
      // Apply all regular expression replacements
      res = replacers.foldLeft(mts.head.get("A").get)((x,vs) => x.replaceAll(vs._1, vs._2))
    } else {
      mts = maude.search1("refine[\"" + mri.key + "\", < " + cml + " | " + M + " | p > ]", "< Decl | M | p >");
      pogs = mts.head.get("p").get.split("and")
      // pogs.to[ListBuffer]
      // Apply all regular expression replacements
      res = replacers.foldLeft(mts.head.get("Decl").get)((x,vs) => x.replaceAll(vs._1, vs._2))
    } 

    for (p <- pogs) {
        val p1 = replacers.foldLeft(p)((x,vs) => x.replaceAll(vs._1, vs._2))
        if (p1.contains("[=")) {
          val bs = p1.split("\\[=")
          val a1 = RefUtils.parsePAction(bs(0))
          val a2 = RefUtils.parsePAction(bs(1))
          ps.add(new CmlActionRefineProvisoObligation(a1, a2))
        } else {
          val e = RefUtils.parsePExp(p1)
          ps.add(new CmlRefineProvisoObligation(e))
        }
    }

    return new Refinement(res, ps)
  }
}