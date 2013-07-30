package eu.compassresearch.ide.cml.tpplugin

import isabelle.Document.Snapshot
import isabelle.Text.Range
import isabelle.eclipse.core.text.DocumentModel

import scala.actors.Actor.loop
import scala.actors.Actor.react
import scala.collection.JavaConversions
import org.overture.pog.pub.POStatus
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList
import eu.compassresearch.core.common.RegistryFactory
import eu.compassresearch.ide.cml.pogplugin.POConstants
import org.overture.ide.core.resources.IVdmProject
import eu.compassresearch.ide.cml.pogplugin.PogPluginDoStuff
import eu.compassresearch.ide.core.resources.ICmlModel
import eu.compassresearch.theoremprover.IsabelleTheory
import isabelle.Command
import isabelle.Document
import isabelle.Session
import isabelle.eclipse.core.util.LoggingActor
import isabelle.eclipse.core.util.SessionEvents
object TPListener {

  def updatePOListFromThy(poList: CmlProofObligationList, ithy: IsabelleTheory, ischanged : IPoStatusChanged) = {
    var flag: Boolean = false
    
    for (p <- JavaConversions.asScalaIterable(poList)) {
      // FIXME: Add code to update POs from the theory
      if (ithy.thmIsProved("po" + p.getName())) {
        p.setStatus(POStatus.PROVED);
        println(p.getName() + " is proved!");
        ischanged.statusChanges(p)
        flag = true
      }
    }
//    if (flag == true) {
//      val pol = new CmlProofObligationList()
//      for (po <- JavaConversions.asScalaIterable(poList)) {
//        pol.add(po)
//      }
//       PogPluginDoStuff.redrawPos(proj, pol)
//    }
  }

}

class TPListener(session: Session, ischanged : IPoStatusChanged) extends SessionEvents {

  var nodeCMLMap: Map[Document.Node.Name, ICmlModel] = Map()
  var statusChangedThing : IPoStatusChanged = ischanged
  
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
            val poList = model.getAttribute(POConstants.PO_REGISTRY_ID, classOf[CmlProofObligationList]);
            //FIXME Hardcoded ID string. scala cannot solve the path
            val ithy = model.getAttribute("eu.compassresearch.ide.cml.tp-plugin", classOf[IsabelleTheory]);
            TPListener.updatePOListFromThy(poList, ithy, statusChangedThing)
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