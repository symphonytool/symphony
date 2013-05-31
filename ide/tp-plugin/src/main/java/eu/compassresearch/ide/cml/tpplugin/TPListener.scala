package eu.compassresearch.ide.cml.tpplugin

import scala.actors.Actor._

import isabelle.{Command, Session}
import isabelle.Document.Snapshot
import isabelle.Text.Range
import isabelle.eclipse.core.text.DocumentModel
import isabelle.eclipse.core.util.{LoggingActor, SessionEvents}
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligation
import org.overture.pog.obligation.POStatus

/**
 * A listener wrapper for Isabelle session command change events.
 *
 * Reacts to command changes and calculates the affected document ranges, which are then pushed
 * as handler notifications.
 *
 * @author Andrius Velykis
 */
class TPListener(session: Session, ithy: IsabelleTheory, poList: CMLProofObligationList) extends SessionEvents {

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
          System.out.print("T!")
          System.out.print("HELLO!")
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