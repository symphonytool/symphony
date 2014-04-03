package eu.compassresearch.core.analysis.theoremprover
import isabelle.{Build, Document, Session, Text, Protocol, Thy_Load, Command}
import isabelle.Document.Node
import java.io.File
import java.io.FileWriter

object IsabelleTheory {
  

  val isabelleHome = if (System.getenv("ISABELLE_HOME") == null) 
	  					"/local/d0p6/simonf/Isabelle/Isabelle2013" 
                     else System.getenv("ISABELLE_HOME")
  
  def createHOLSession(): Session = {
    System.setProperty("isabelle.home", isabelleHome)
    val content = Build.session_content(false, Nil, "HOL")
    val session = new Session(new Thy_Load(content.loaded_theories, content.syntax))
    session.start(List("HOL"))
    session
  }
  
}

class IsabelleTheory ( val session: Session
                     , val thyName: String
                     , val thyDir: String) {

  sealed abstract class IsabelleProof {
    var location: Option[Text.Offset]= None
    def qedOffset(): Text.Offset
  }
  case class IsabelleByProof(val proof: String) extends IsabelleProof {
    override def toString() = "by ("+ proof +")" + ls
    override def qedOffset() = 0
  }

  case class IsabelleApplyProof(val proof: List[String]) extends IsabelleProof {
    val applyString = proof.map("apply (" + _ + ")" + ls).mkString("") 
    override def toString() = {
       applyString + "done" + ls
    }
    override def qedOffset() = applyString.length()
  }

  
  class IsabelleTheorem( val name: String
                       , val goal: String
                       , var proof: IsabelleProof) {
    var location: Option[Text.Offset] = None
    def qedLocation() = proof.location.map(_ + proof.qedOffset())
    val lemmaString: String = "lemma " + name +": \"" + goal + "\"" + ls 
	def proofString: String = proof.toString();
    def thmString: String = lemmaString + proofString
  
    def this(name: String, goal:String, proof: String) {
      this(name, goal, IsabelleByProof(proof))
    }
  
    def this(name: String, goal:String) {
      this(name, goal, "oops")
    }
    
    def updateLocation(l: Text.Offset) = {
      location = Some(l)
      updateProofLocation()
    }
    
    def writeHead() {
      location match {
        case Some(l) => {
          session.update(List(thyNode -> Node.Edits(List(Text.Edit.insert(l,lemmaString)))))
        }
        case None => return
      }
    }
    
    def writeProof() {
      location match {
        case Some(l) => {
          session.update(List(thyNode -> Node.Edits(List(Text.Edit.insert(l + lemmaString.length(),proofString)))))
        }
        case None => return
      }
    }
    
    def deleteProof() {
      location match {
        case Some(l) => {
          session.update(List(thyNode -> Node.Edits(List(Text.Edit.remove(l + lemmaString.length(),proofString)))))
        }
        case None => return
      }
    }
   
    def updateProofLocation() {
      location match {
        case Some(l) => {
          proof.location = Some(l + lemmaString.length())
        }
        case None => return
      }
    }
    
    def updateProof(p: IsabelleProof) {
      deleteProof()
      proof = p
      writeProof()
      updateProofLocation()
    }
    
  }

  private val ls = System.getProperty("line.separator")
  
  val thyNode = Document.Node.Name(thyName, thyDir, thyName)
  val thyHead = "theory " + thyName + ls + "imports Main" + ls + "begin" + ls + ls
  val thyTail  = ls + "end" + ls
  val header = session.thy_load.check_thy_text(thyNode, thyHead)
  var thms: List[IsabelleTheorem] = Nil
  
  def thyBody = thms.map(_.thmString).mkString(ls)
  def thmEnd = thyHead.length() + thyBody.length(); 
  def thyEnd = thmEnd + 10;
  
  
  def init() {
    // Set the header for the theory node, clear the contents and add the header and end
    
    // tpListener.dispose()
    
    val perspective = Text.Perspective(List(Text.Range(0, thyEnd)))
    
    session.update(List( session.header_edit(thyNode, header)
                       , thyNode -> Document.Node.Clear()
                       , thyNode -> Document.Node.Edits(List(Text.Edit.insert(0, thyHead + ls + "end")))
                       , thyNode -> Document.Node.Perspective(perspective))
    );
  }
  
  def updatePerspective() {
    val perspective = Text.Perspective(List(Text.Range(0, thyEnd)))
    session.update(List(thyNode -> Document.Node.Perspective(perspective)));
  }
  
  def addThm(thm: IsabelleTheorem) {
    val oldThmEnd = thmEnd
    thm.updateLocation(oldThmEnd)
    thms = thms ++ List(thm)
    val newThmEnd = thmEnd
    val perspective = Text.Perspective(List(Text.Range(0, thyEnd)))
    thm.writeHead()
    thm.writeProof()
    updatePerspective()
  }

  def updateProof(thmName: String, proof: IsabelleProof) {
    val thmList = thms.dropWhile(_.name != thmName)
    thmList match {
      case (t :: ts) => {
        val op = t.proofString
        t.updateProof(proof)
        val np = t.proofString
        updatePerspective()
        // Every theorem following must have its location updated
        for (t1 <- ts) {
          t1.location.map(_ + (np.length() - op.length())) 
        }
      }
      case Nil => return
    }
  }
  
  override def toString() = thyHead + thyBody + thyTail
  // Alternatively: session.snapshot(ithy.thyNode).node.commands.map(_.source).mkString("")
  
  def writeThmFile() {
    val thyFile = new File(thyDir + "/" + thyName + ".thy") 
    if (!thyFile.exists()) thyFile.createNewFile()
    val fw = new FileWriter(thyFile.getAbsoluteFile())
    fw.write(thyHead + thyBody + thyTail)
    fw.flush()
    fw.close()
  }
  
  
  // Gets the theorem command by looking at the appropriate location in the current snapshot
  def thmCmd(thmName : String): Option[Command] = {
    val thm  = thms.find(_.name == thmName)
    val node = session.snapshot(thyNode).node
    thm.flatMap(_.location.flatMap(node.command_at(_))).map(_._1)
  }

  def qedCmd(thmName : String): Option[Command] = {
    val thm  = thms.find(_.name == thmName)
    val node = session.snapshot(thyNode).node
    thm.flatMap(_.qedLocation().flatMap(node.command_at(_))).map(_._1)
  }
  
/*
  def proofCmds(thm : String): Option[List[Command]] = {
    val s = session.snapshot(thyNode)
    val cmds = s.node.commands.toList
    var found = false
    var prf = false;
    var prfcmds : List[Command] = Nil
    for (c <- cmds) {
      if (prf && !c.source.contains("done") && !c.source.contains("by")) {
        prfcmds = c :: prfcmds
      }
      
      /* FIXME: Need some more sophisticated to control logic to deal with,
         e.g. Isar proofs */
      if (prf && ( c.source.contains("done") || c.source.contains("by") 
                || c.source.contains("sorry") || c.source.contains("oops"))) {
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
*/

/*
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
*/
  
  def thmStatus(thm : String): Option[Protocol.Status] = {
    val cmd = qedCmd(thm)
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
    val cmd = qedCmd(thm)
    val status = thmStatus(thm)
    (status.exists(x => x.is_finished && !x.is_failed) 
      && cmd.exists(x => !(x.source.contains("oops") || x.source.contains("sorry"))))
  }
  
}