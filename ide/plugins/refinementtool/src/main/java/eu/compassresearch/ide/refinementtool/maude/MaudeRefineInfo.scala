package eu.compassresearch.ide.refinementtool.maude

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

class MaudeRefineInfo(val key: String, val desc: String, val law: String, val vars: Map[String,String]) {

  def getMetas: java.util.Map[String, String] = {
    vars;
  }
  
  def getNames: java.util.List[String] = {
    vars.map(_._1).to[ListBuffer]
  }
  
}