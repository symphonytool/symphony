package eu.compassresearch.ide.cml.tpplugin

import java.util.concurrent.locks.ReentrantReadWriteLock

import scala.collection.mutable.ListBuffer
import scala.io.Source

import org.eclipse.core.runtime.{IProgressMonitor, NullProgressMonitor, Status}
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.jface.text.{DocumentEvent, IDocument, IDocumentListener}

import isabelle.{Document, Session, Text, Protocol, Command}
import isabelle.eclipse.core.util.{PostponeJob, SerialSchedulingRule}
import isabelle.eclipse.core.util.ConcurrentUtil.FunReadWriteLock
import eu.compassresearch.core.common.AnalysisArtifact


/**
 * A model for the Isabelle text document.
 *
 * It tracks changes in the text and submits them to the prover for evaluation when needed.
 *
 * @author Andrius Velykis
 */
object IsabelleTheory extends AnalysisArtifact {

  /**
   * A rule to use in Job framework that ensures serial execution of jobs.
   * Used for submitting content to the Isabelle prover backend.
   */
  val serialSubmitRule = new SerialSchedulingRule
  
  // TODO add as a configurable option
  val flushDelay = 300
  
}

class IsabelleTheorem(val name: String, val goal: String, var proof: List[String])
  extends AnalysisArtifact {
  val lemmaString: String = "lemma " + name +": \"" + goal + "\"\n" 
  val proofString: String = proof.mkString("\n")
  def thmString: String = lemmaString + proofString
  
  def this(name: String, goal:String, proof: String) {
    this(name, goal, List(proof))
  }
  
  def this(name: String, goal:String) {
    this(name, goal, "oops")
  }
  
}

class IsabelleTheory( val session: Session
                     , val thyName: String
                     , val thyDir: String) {

  val thyNode = Document.Node.Name(thyName, thyDir, thyName)
  val thyHead = "theory " + thyName + "\nimports HOL\nbegin\n"
  val header = session.thy_load.check_thy_text(thyNode, thyHead)
  var thms: List[IsabelleTheorem] = Nil
  
  
  private def thmEnd = thyHead.length() + thms.map(_.thmString).mkString("\n").length(); 
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