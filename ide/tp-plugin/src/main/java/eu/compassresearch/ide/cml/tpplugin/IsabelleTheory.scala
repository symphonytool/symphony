package eu.compassresearch.ide.cml.tpplugin

import java.util.concurrent.locks.ReentrantReadWriteLock

import scala.collection.mutable.ListBuffer
import scala.io.Source

import org.eclipse.core.runtime.{IProgressMonitor, NullProgressMonitor, Status}
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.jface.text.{DocumentEvent, IDocument, IDocumentListener}

import isabelle.{Document, Session, Text}
import isabelle.eclipse.core.util.{PostponeJob, SerialSchedulingRule}
import isabelle.eclipse.core.util.ConcurrentUtil.FunReadWriteLock


/**
 * A model for the Isabelle text document.
 *
 * It tracks changes in the text and submits them to the prover for evaluation when needed.
 *
 * @author Andrius Velykis
 */
object IsabelleTheory {

  /**
   * A rule to use in Job framework that ensures serial execution of jobs.
   * Used for submitting content to the Isabelle prover backend.
   */
  val serialSubmitRule = new SerialSchedulingRule
  
  // TODO add as a configurable option
  val flushDelay = 300
  
}

class IsabelleTheorem(val name: String, val goal: String, var proof: List[String]) {
  val lemmaString: String = "lemma " + name +": \"" + goal + "\"\n" 
  val proofString: String = proof.mkString("\n")
  def thmString: String = lemmaString + proofString
  
  def this(name: String, goal:String, proof: String) {
    this(name,goal,List(proof))
  }
  
}

class IsabelleTheory( val session: Session
                     , val thyName: String
                     , val thyDir: String) {

  val thyNode = Document.Node.Name(thyName, thyDir, thyName)
  val thyHead = "theory " + thyName + "\nimports HOL\nbegin\n"
  val header = session.thy_load.check_thy_text(thyNode, thyHead)
  var thms: List[IsabelleTheorem] = Nil
  
  val tpListener = new TPListener(session);
  
  private def thmEnd = thyHead.length() + thms.map(_.thmString).mkString("\n").length(); 
  private def thyEnd = thmEnd + 10;
  
  
  
  def init() {
    // Set the header for the theory node, clear the contents and add the header and end
    tpListener.init()
    
    tpListener.dispose()
    
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
  def thmCmd(thm : String): Option[Document.Command_ID] = {
    val s = session.snapshot(thyNode)
    val cmds = s.node.commands.toList  
    val cmd = cmds.find(c => c.source.contains(thm))    
    cmd.map(_.id)
  }
  
  def proofCmds(thm : String): Option[List[Document.Command_ID]] = {
    val s = session.snapshot(thyNode)
    val cmds = s.node.commands.toList
    var found = false;
    var prf = false;
    var prfcmds : List[Document.Command_ID] = Nil
    for (c <- cmds) {
      if (prf && !c.source.contains("done") && !c.source.contains("by")) {
        prfcmds = c.id :: prfcmds
      }
      
      if (c.source.contains("done") || c.source.contains("by")) {
        prfcmds = c.id :: prfcmds
        prf = false;
      }
      
      if (c.source.contains("lemma " + thm) && !prf) {
        prf = true;
        found = true;
      }
    }
    
    if (found) { Some(prfcmds) } else { None }
  }

 
}