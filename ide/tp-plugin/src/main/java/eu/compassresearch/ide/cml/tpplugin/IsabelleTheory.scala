package eu.compassresearch.ide.cml.tpplugin

import scala.collection.mutable.ListBuffer
import scala.io.Source
import isabelle.{Document, Session, Text, Protocol, Command}
import eu.compassresearch.core.common.AnalysisArtifact
import java.io.File
import java.io.FileWriter


class IsabelleTheory ( val session: Session
                     , val thyName: String
                     , val thyDir: String) extends AnalysisArtifact {

  sealed abstract class IsabelleProof
  case class IsabelleByProof(val proof: String) extends IsabelleProof {
    var command: Option[Command]= None
    override def toString() = "by ("+ proof +")\n" 
  }

  case class IsabelleApplyProof(val proof: List[String]) {
    override def toString() = {
      proof.map("  apply (" + _ + ")\n").mkString("") + "done\n"
    }
  }

  
  class IsabelleTheorem( val name: String
                       , val goal: String
                       , var proof: IsabelleProof) {
    val lemmaString: String = "lemma " + name +": \"" + goal + "\"\n" 
	val proofString: String = proof.toString();
    def thmString: String = lemmaString + proofString
  
    def this(name: String, goal:String, proof: String) {
      this(name, goal, IsabelleByProof(proof))
    }
  
    def this(name: String, goal:String) {
      this(name, goal, "oops")
    }  
  }

  
  val thyNode = Document.Node.Name(thyName, thyDir, thyName)
  val thyHead = "theory " + thyName + "\nimports HOL\nbegin\n"
  val thyTail  = "end\n"
  val header = session.thy_load.check_thy_text(thyNode, thyHead)
  private var thms: List[IsabelleTheorem] = Nil
  
  private def thyBody = thms.map(_.thmString).mkString("\n")
  private def thmEnd = thyHead.length() + thyBody.length(); 
  private def thyEnd = thmEnd + 10;
  
  
  def init() {
    // Set the header for the theory node, clear the contents and add the header and end
    
    // tpListener.dispose()
    
    val perspective = Text.Perspective(List(Text.Range(0, thyEnd)))
    
    session.update(List( session.header_edit(thyNode, header)
                       , thyNode -> Document.Node.Clear()
                       , thyNode -> Document.Node.Edits(List(Text.Edit.insert(0, thyHead + "\nend")))
                       , thyNode -> Document.Node.Perspective(perspective))
    );
  }
  
  def addThm(thm: IsabelleTheorem) {
    val oldThmEnd = thmEnd;
    thms = thms ++ List(thm);
    val newThmEnd = thmEnd;
    val perspective = Text.Perspective(List(Text.Range(0, thyEnd)))
    session.update(List( thyNode -> Document.Node.Edits(List(Text.Edit.insert(oldThmEnd,thm.thmString)))
                       , thyNode -> Document.Node.Perspective(perspective)));
  }
  
  def writeThmFile() {
    val thyFile = new File(thyDir + "/" + thyName + ".thy") 
    if (!thyFile.exists()) thyFile.createNewFile()
    val fw = new FileWriter(thyFile.getAbsoluteFile())
    fw.write(thyHead + thyBody + thyTail)
    fw.flush()
    fw.close()
  }
  
  
  /**
   * Checks if the contents of this document have already been submitted as-is to the prover
   */
  def thmCmd(thm : String): Option[Command] = {
    val s = session.snapshot(thyNode)
    val cmds = s.node.commands.toList  
    cmds.find(c => c.source.contains(thm))
  }
  
  def proofCmds(thm : String): Option[List[Command]] = {
    val s = session.snapshot(thyNode)
    val cmds = s.node.commands.toList
    var found = false;
    var prf = false;
    var prfcmds : List[Command] = Nil
    for (c <- cmds) {
      if (prf && !c.source.contains("done") && !c.source.contains("by")) {
        prfcmds = c :: prfcmds
      }
      
      /* FIXME: Need some more sophisticated to control logic to deal with,
         e.g. Isar proofs */
      if ( c.source.contains("done") || c.source.contains("by") 
         || c.source.contains("sorry") || c.source.contains("oops")) {
        prfcmds = c :: prfcmds
        prf = false;
      }
      
      if (c.source.contains("lemma " + thm) && !prf) {
        prf = true;
        found = true;
      }
    }
    
    if (found) { Some(prfcmds) } else { None }
  }

  def thmStatus(thm : String): Option[Protocol.Status] = {
    val cmd = proofCmds(thm).flatMap(_.lastOption)
    val version = session.snapshot().version
    val state   = session.snapshot().state
    cmd match {
      case Some(c) => {
    	val st = state.command_state(version, c)
    	Some(Protocol.command_status(st.status))
      }
      case None => None
    }
  }
 
  def thmIsProved(thm : String): Boolean = {
    val cmd = proofCmds(thm).flatMap(_.lastOption)
    val status = thmStatus(thm)
    (status.exists(x => x.is_finished && !x.is_failed) 
      && cmd.exists(x => !(x.source.contains("oops") || x.source.contains("sorry"))))
  }
  
}