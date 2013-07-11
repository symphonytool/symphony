package eu.compassresearch.ide.cml.tpplugin

import scala.actors.Actor._

import isabelle.{Command, Session, Document}
import isabelle.Document.Snapshot
import isabelle.Text.Range
import isabelle.eclipse.core.text.DocumentModel
import isabelle.eclipse.core.util.{LoggingActor, SessionEvents}
import eu.compassresearch.theoremprover.IsabelleTheory
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligation
import eu.compassresearch.ast.program.PSource
import eu.compassresearch.core.common.Registry
import eu.compassresearch.core.common.RegistryFactory
import eu.compassresearch.ide.cml.ui.editor.core.dom.ICmlSourceUnit
import eu.compassresearch.ide.cml.pogplugin.POConstants
import org.overture.pog.POStatus

import scala.collection.JavaConversions;

object TPListener {
  
  def updatePOListFromThy(poList: CMLProofObligationList, ithy: IsabelleTheory) = {
    for (p <- JavaConversions.asScalaIterable(poList)) {      
      // FIXME: Add code to update POs from the theory
      if (ithy.thmIsProved("po" + p.getName())) {
        p.setStatus(POStatus.PROVED)        
        println(p.getName() + " is proved!");
      }
    }
  }
  
}

class TPListener(session: Session) extends SessionEvents {

  var nodeCMLMap:Map[Document.Node.Name, PSource] = Map()
  
  def registerNode(n: Document.Node.Name, s: PSource) {
    nodeCMLMap += (n -> s)
  }
  
  // When commands change (e.g. results from the prover), notify the handler about changed ranges.
  /** Subscribe to commands change session events */
  override protected def sessionEvents(session: Session) = List(session.commands_changed)

  /** When the session is initialised, notify about "all changed" if enabled */
  override protected def sessionInit(session: Session) =
    notifyCommandsChanged(None)

  /** The actor to react to session events */
  override protected val sessionActor = LoggingActor {
    loop {
      react {
        case changed: Session.Commands_Changed => {

          // avoid updating if commands are from a different document
          /*
          if (changed.nodes contains docModel.name) {
            notifyCommandsChanged(Some(changed.commands))
          } */
          
          val registry = RegistryFactory.getInstance(POConstants.PO_REGISTRY_ID).getRegistry()
          
          for (c <- changed.commands) {
            val ast  = nodeCMLMap(c.node_name)
            val poList = registry.lookup(ast, classOf[CMLProofObligationList]);
            val ithy   = registry.lookup(ast, classOf[IsabelleTheory])
            TPListener.updatePOListFromThy(poList, ithy)
          }
        }
      }
    }
  }

  def init() {
    initSessionEvents()
  }

  def dispose() {
    disposeSessionEvents()
  }

  private def notifyCommandsChanged(changedCmds: Option[Set[Command]]) {
    /*
    val ranges = changedRanges(docModel.snapshot, changedCmds)
    handler(ranges)
    * 
    */
  }


}