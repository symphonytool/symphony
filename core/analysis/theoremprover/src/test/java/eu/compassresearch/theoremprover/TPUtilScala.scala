package eu.compassresearch.theoremprover

import isabelle._

object TPUtilScala {

  val isabelleHome = "/local/d0p6/simonf/Isabelle/Isabelle2013"
  
  def createHOLSession(): Session = {
    System.setProperty("isabelle.home", isabelleHome)
    val content = Build.session_content(false, Nil, "HOL")
    val session = new Session(new Thy_Load(content.loaded_theories, content.syntax))
    session.start(List("HOL"))
    session
  }
  
}