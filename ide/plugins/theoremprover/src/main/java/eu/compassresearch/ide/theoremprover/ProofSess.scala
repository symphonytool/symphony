package eu.compassresearch.ide.theoremprover

import scala.actors.Actor.loop
import scala.actors.Actor.react
import isabelle.eclipse.core.text.EditDocumentModel
import org.overture.ast.node.INode
import org.eclipse.ui.editors.text.TextFileDocumentProvider
import isabelle.{Command, Session}
import org.overture.pog.pub.IProofObligation
import eu.compassresearch.core.analysis.theoremprover.visitors.TPVisitor
import isabelle.eclipse.core.util.SessionEvents
import isabelle.eclipse.core.util.LoggingActor
import org.overture.pog.pub.POStatus
import org.eclipse.ui.texteditor.IDocumentProvider
import org.eclipse.core.runtime.NullProgressMonitor
import isabelle.Protocol

class ProofSess( val poEDM : EditDocumentModel
                , val ast : java.util.List[INode]
			    , val thyProvider : IDocumentProvider
			    , val sess : Session) extends SessionEvents {

  var poMap: Map[Command,IProofObligation] = Map.empty
  
  def enqueuePO(ipo : IProofObligation) {
    val isaPO = TPVisitor.generatePoStr(ast, ipo)
    val doc = poEDM.document
    val offset = doc.getLineOffset(doc.getNumberOfLines() - 1)
    doc.replace(offset, 0, isaPO + "\n")

    poEDM.submitFullPerspective(new NullProgressMonitor());
    thyProvider.saveDocument(new NullProgressMonitor(), null, poEDM.document, true);
    
    val byPos = offset + (isaPO.length() - TPConstants.BY_CML_AUTO_TAC_OFFSET)

    val node = sess.snapshot(poEDM.name).node
    val cmd = node.command_at(byPos).map(_._1)
    cmd match {
      case Some(c) => poMap += (c -> ipo)
      case None => {}
    }
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

          // FIXME Need to use the new registry

          for (c <- changed.commands) {
            if (poMap.contains(c)) {
              // FIXME: Check if PO has indeed been proved or has actually failed...
              val po = poMap(c) 
              val version = sess.snapshot().version
              val state   = sess.snapshot().state
           	  val st = state.command_state(version, c)
    	      val cst = Protocol.command_status(st.status)
           	  
           	  if (cst.is_finished && !cst.is_failed) {
                System.out.println("PO " + po.toString() + " discharged...")
                po.setStatus(POStatus.PROVED)       	    
           	  }
           	  
            }
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