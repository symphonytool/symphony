package eu.compassresearch.ide.refinementtool.maude

class Maude(command: String) {

    import scala.sys.process._
    import scala.io._
    import java.io._
    import scala.concurrent._
    import scala.concurrent.ops.spawn

    val inputStream = new SyncVar[OutputStream];
    
    var cmdLog: List[String] = List();
    var cmdOutput: List[String] = List();
    var err: String = "";
    
    def addOutput(i : Iterator[String]) = {
      while (i.hasNext) {
        cmdOutput = cmdOutput ++ List(i.next)
      }
    }
    
    val process = Process(command + " -no-wrap -batch").run(
        new ProcessIO(
            stdin => inputStream.put(stdin),
            stdout => addOutput(Source.fromInputStream(stdout).getLines),                       
            stderr => Source.fromInputStream(stderr).getLines().foreach(x => err = err + x)));

    def execute(s:String): List[String] = {
      cmdLog ++= List(s);
      cmdOutput = List();
      synchronized {
        inputStream.get.write((s + "\n").getBytes);
        // To ensure we can detect the commands completion, we perform a dummy reduction afterwards
        inputStream.get.write("red \"DONE\" .\n".getBytes);
        inputStream.get.flush();
      }
      
      // Wait until the result of the dummy reduction comes through
      while (!cmdOutput.nonEmpty || cmdOutput.last != "result String: \"DONE\"") { 
        Thread.sleep(100);
      }
      
      // Remove the four lines produced by the reduction
      return cmdOutput.init.init.init.init;
    }

    def toCygPath(f:String): String = {
      val ps = f.split(":\\\\")
      "/cygdrive/" + ps(0).toLowerCase() + "/" + ps(1).replace("\\", "/")
    }
    
    def isCygPath(f : String) = f.contains("cygdrive")
    
    def load(f:String) = {
      var file = f
      if (System.getProperty("os.name").contains("Windows") && !isCygPath(f)) {
        file = toCygPath(f)
      }
      execute("load " + file + " .");
    }

    def search1(st: String, pat: String): List[Map[String,String]] = {
      execute("search " + st + " =>1 " + pat + " .");
      
      var co = cmdOutput;
      var mvs: Map[String,String] = Map();
      var mts: List[Map[String,String]] = List();
      
      co = co.dropWhile(p => !p.startsWith("Solution"))
      
      while (co.nonEmpty) {
    	  mvs = Map()
    	  co = co.drop(2)    	  
    	  while (co.head.contains("-->")) {
    		  val vs = co.head.split(" --> ");
    		  mvs = mvs + (vs(0) -> vs(1))
    	      co = co.drop(1);
    	  }
    	  mts ++= List(mvs);
    	  co = co.dropWhile(p => !p.startsWith("Solution"))
      }
      
      return mts;
    }
    
    def rewrite(st : String): String = {
      val rw = execute("rew " + st + " .");
      return (rw.last.split(": ")).last;
    }
    
    def quit():Unit = {
        inputStream.get.close
    }
}