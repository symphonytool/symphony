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

class ProofSess( val poEDM : EditDocumentModel
                , var proj : ICmlProject
                , var pol : IProofObligationList
                , val ast : java.util.List[INode]
			    , val thyProvider : IDocumentProvider
			    , val sess : Session) extends SessionEvents {

  // A map from position offsets in the CML file to the corresponding proof obligation
  var poMap: Map[Command,Int] = Map.empty
  var nextPO: Option[Pair[Int,Int]] = None
  
  def enqueuePO(ipo : IProofObligation) {
    ipo.setStatus(POStatus.SUBMITTED)
    PogPluginRunner.redrawPos(proj, pol)
    val isaPO = TPVisitor.generatePoStr(ast, ipo)
    val doc = poEDM.document
    val offset = doc.getLineOffset(doc.getNumberOfLines() - 1)
    val byPos = offset + (isaPO.length() - TPConstants.BY_CML_AUTO_TAC_OFFSET)
    
    nextPO = Some(Pair(byPos, ipo.getNumber()))
    
    doc.replace(offset, 0, isaPO + "\n")
    
    poEDM.submitFullPerspective(new NullProgressMonitor());
    thyProvider.saveDocument(new NullProgressMonitor(), null, poEDM.document, true);
    
/*

    val node = sess.snapshot(poEDM.name).node
    val cmd = node.command_at(byPos).map(_._1)
    cmd match {
      case Some(c) => poMap += (c -> ipo)
      case None => {}
    }
    * 
    * 
    */
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
          nextPO match {
            case Some(Pair(byPos, ipo)) => {
        	  val node = sess.snapshot(poEDM.name).node
              val cmd = node.command_at(byPos).map(_._1)
              cmd match {
                case Some(c) => { poMap += (c -> ipo); nextPO = None }
                case None => {}
        	  }
        	  
            }
        	case None => {}
          }
      
          for (c <- changed.commands) {

            val version = sess.snapshot().version
            val state   = sess.snapshot().state
            
            if (poMap.contains(c)) {
              // FIXME: Check if PO has indeed been proved or has actually failed...
              val po = poMap(c) 
           	  val st = state.command_state(version, c)
    	      val cst = Protocol.command_status(st.status)
    	      
    	      
           	  if (cst.is_finished && !cst.is_failed) {
                System.out.println("PO " + po.toString() + " discharged...")
                pol.get(po - 1).setStatus(POStatus.PROVED)
                PogPluginRunner.redrawPos(proj, pol)
           	  } else if (cst.is_failed) {
                System.out.println("PO " + po.toString() + " failed...")
                pol.get(po - 1).setStatus(POStatus.DISPROVED)
                PogPluginRunner.redrawPos(proj, pol)           	    
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