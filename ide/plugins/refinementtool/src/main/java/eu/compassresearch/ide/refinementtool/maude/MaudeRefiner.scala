package eu.compassresearch.ide.refinementtool.maude

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

class MaudeRefiner(cmd: String, refine: String) {

  val maude = new Maude(cmd);
  maude.load(refine);
  
  def findApplLaws(cml: String) : java.util.List[MaudeRefineInfo] = {
    val mts = maude.search1("refs[" + cml + "]", "rinf[NM,DS,INP]");
    return mts.map(m => new MaudeRefineInfo( m.get("NM").get.tail.init // Trim off leading/trailing quotes
    									   , m.get("DS").get.tail.init
    									   , Set.empty)).to[ListBuffer]
  }
  
  def applyLaw(cml: String, mri: MaudeRefineInfo): String = {
    val mts = maude.search1("refine[\"" + mri.key + "\", < " + cml + " | M | p > ]", "< A | M | p >");
    mts.head.get("A").get
  }
}