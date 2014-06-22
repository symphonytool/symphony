package eu.compassresearch.ide.theoremprover

import scala.actors.Actor.loop
import scala.actors.Actor.react
import scala.collection.JavaConversions
import org.overture.pog.pub.POStatus
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList
import eu.compassresearch.ide.pog.PogPluginConstants
import eu.compassresearch.ide.core.resources.ICmlModel
import eu.compassresearch.core.analysis.theoremprover.IsabelleTheory
import isabelle.Command
import isabelle.Document
import isabelle.Session
import isabelle.eclipse.core.util.LoggingActor
import isabelle.eclipse.core.util.SessionEvents
import eu.compassresearch.ide.pog.PogPluginRunner

object TPListener {

  def updatePOListFromThy(poList: CmlProofObligationList, ithy: IsabelleTheory) = {
    var flag: Boolean = false
    for (p <- JavaConversions.asScalaIterable(poList)) {
      // FIXME: Add code to update POs from the theory
      if (ithy.thmIsRejected("po" + p.getUniqueName())) {
        p.setStatus(POStatus.DISPROVED);
        // FIXME: Update the status to failed
      } else
      if (ithy.thmIsProved("po" + p.getUniqueName())) {
        p.setStatus(POStatus.PROVED);
        println(p.getName() + " is proved!");
        // ischanged.statusChanges(p)
        flag = true
      }
    }
  }
  
}

class TPListener(session: Session) extends SessionEvents {

  var nodeCMLMap: Map[Document.Node.Name, ICmlModel] = Map()
  // var statusChangedThing : IPoStatusChanged = ischanged
  
  def registerNode(n: Document.Node.Name, m: ICmlModel) {
    nodeCMLMap += (n -> m)
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

          // FIXME Need to use the new refistry

          for (c <- changed.commands) {
            val model = nodeCMLMap(c.node_name)
            val poList = model.getAttribute(PogPluginConstants.PO_REGISTRY_ID, classOf[CmlProofObligationList]);
            //FIXME Hardcoded ID string. scala cannot solve the path
            val ithy = model.getAttribute("eu.compassresearch.ide.theoremprover", classOf[IsabelleTheory]);
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

  def cmdAt(thyNode : Document.Node.Name, offset : Integer): Option[Command] = {
    val node = session.snapshot(thyNode).node
    val rng = node.command_range(offset)
    val cm = if (rng.hasNext) Some(rng.next) else None
    cm.map(_._1)
  }

  
}
