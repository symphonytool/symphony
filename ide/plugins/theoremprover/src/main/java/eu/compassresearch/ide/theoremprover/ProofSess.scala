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
import eu.compassresearch.ide.core.resources.ICmlProject
import org.overture.pog.pub.IProofObligationList
import eu.compassresearch.ide.pog.PogPluginRunner

sealed case class PoThm(val offset : Int, val body: String, val byPos: Int, val poNum: Int)

class ProofSess( val poEDM : EditDocumentModel
                , val proj : ICmlProject
                , val pol : IProofObligationList
                , val ast : java.util.List[INode]
			    , val thyProvider : IDocumentProvider
			    , val sess : Session) extends SessionEvents {

  // A map from position offsets in the CML file to the corresponding proof obligation
  var poMap: Map[Command,PoThm] = Map.empty
  var poPending: List[PoThm] = List()
  var poSubmitted: Set[Int] = Set()
  
  def enqueueAllPOs() {
    val it = pol.iterator()
    while (it.hasNext()) {
      enqueuePO(it.next())
    }
  }
  
  def enqueuePO(ipo : IProofObligation) {
    
    // Only enqueue if the ipo has not been submitted yet
    if (!poSubmitted.contains(ipo.getNumber())) {
      ipo.setStatus(POStatus.SUBMITTED)      
      PogPluginRunner.redrawPos(proj, pol)
      poSubmitted += ipo.getNumber()
      val isaPO = TPVisitor.generatePoStr(ast, ipo)
      val doc = poEDM.document
      val offset = doc.getLineOffset(doc.getNumberOfLines() - 1)
      val byPos = offset + (isaPO.length() - 17)
    
      poPending ++= PoThm(offset, isaPO + "\n", byPos, ipo.getNumber()) :: List()
    
      doc.replace(offset, 0, isaPO + "\n")
    
      poEDM.submitFullPerspective(new NullProgressMonitor())
      thyProvider.saveDocument(new NullProgressMonitor(), null, poEDM.document, true)
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

          
          // If a proof obligation has just been added extract the associated command
          poPending match {
            case (pt :: pts) => {
        	  val node = sess.snapshot(poEDM.name).node
              val cmd = node.command_at(pt.byPos).map(_._1)
              cmd match {
                case Some(c) => { poMap += (c -> pt); poPending = pts }
                case None => {}
        	  }
        	  
            }
        	case List() => {}
          }
      
          for (c <- changed.commands) {

            val version = sess.snapshot().version
            val state   = sess.snapshot().state
            
            if (poMap.contains(c)) {
              // FIXME: Check if PO has indeed been proved or has actually failed...
              val pt = poMap(c) 
           	  val st = state.command_state(version, c)
    	      val cst = Protocol.command_status(st.status)
    	      
    	      
           	  if (cst.is_finished && !cst.is_failed) {
                // Proof Succeeded
           	    pol.get(pt.poNum - 1).setStatus(POStatus.PROVED)
                PogPluginRunner.redrawPos(proj, pol)
           	  } else if (cst.is_failed) {
           	    // Proof Failed
                pol.get(pt.poNum - 1).setStatus(POStatus.DISPROVED)
                PogPluginRunner.redrawPos(proj, pol)           	    

                // Remove the failed proof goal
                poEDM.document.replace(pt.offset, pt.body.length(), "")
                poEDM.submitFullPerspective(new NullProgressMonitor())
                thyProvider.saveDocument(new NullProgressMonitor(), null, poEDM.document, true)

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