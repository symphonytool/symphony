package eu.compassresearch.ide.refinementtool.maude

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import scala.util.matching._

class MaudeRefiner(cmd: String, refine: String) {

  val maude = new Maude(cmd);
  maude.load(refine);
  
  // Not all Maude output follows strict CML; we used matchers to replace them
  val replacers: Map[String, String] = Map.empty +
    ( "\\#b\\(true\\)"  -> "true"
    , "\\#b\\(false\\)" -> "false"
    , "\\#nm\\(\"(\\w+)\"\\)" -> "$1" ) 
  
  def findApplLaws(cml: String) : java.util.List[MaudeRefineInfo] = {
    val mts = maude.search1("refs[" + cml + "]", "rinf[NM,DS,INP]");
    return mts.map(m => new MaudeRefineInfo( m.get("NM").get.tail.init // Trim off leading/trailing quotes
    									   , m.get("DS").get.tail.init
    									   , Set.empty)).to[ListBuffer]
  }
  
  def applyLaw(cml: String, mri: MaudeRefineInfo): String = {
    val mts = maude.search1("refine[\"" + mri.key + "\", < " + cml + " | M | p > ]", "< A | M | p >");
    val pogs = mts.head.get("p").get.split("and")
    
    // pogs.to[ListBuffer]
    
    // Apply all regular expression replacements
    replacers.foldLeft(mts.head.get("A").get)((x,vs) => x.replaceAll(vs._1, vs._2))
    
  }
}