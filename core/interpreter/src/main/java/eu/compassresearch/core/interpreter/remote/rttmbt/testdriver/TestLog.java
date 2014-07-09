package eu.compassresearch.core.interpreter.remote.rttmbt.testdriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.overture.interpreter.messages.Console;

public class TestLog {

	static private boolean debug = false;
	static File logfile = null;
	static Writer logfileWriter = null;
	static BufferedWriter logWriter = null;

	static public class Pair<L,R> {

		private final L left;
		private final R right;

		public Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}
		public String toString() {
			return "(" + left.toString() + "," + right.toString() + ")";
		}

		public L getLeft() { return left; }
		public R getRight() { return right; }
	}

	static public void printTestlogEntry(long timestamp, String message)
	{
		if (logfile == null) {
			logfile = new File("lib" + File.separator + "complete_execution_log.txt");
			System.err.println("opened log file " + logfile.getAbsolutePath());
			try {
				logfileWriter = new FileWriter(logfile);
				logWriter = new BufferedWriter(logfileWriter);
			} catch (IOException e) {
				System.err.println("creating FileWriter for " + logfile.getAbsolutePath() + "failed!");
				logfileWriter = null;
				logWriter = null;
			}
		}
		if (logWriter != null) {
			try {
				logWriter.write("TM " + String.format("%011d", timestamp) + " " + message + "\n");
				logWriter.flush();
			} catch (IOException e) {
				System.err.println("writing to log file " + logfile.getAbsolutePath() + "failed!");
				Console.out.printf("TM " + String.format("%011d", timestamp) + " " + message + "\n");
				Console.out.flush();
			}
		} else {
			Console.out.printf("TM " + String.format("%011d", timestamp) + " " + message + "\n");
			Console.out.flush();
		}
	}

    static public void printTestlogEntry(long timestamp, String signal, String value)
	{
    	printTestlogEntry(timestamp, "AM   0 G " + signal + ": " + value);
	}

	static public void println(String message)
	{
    	if (!debug) {
    		return;
    	}
		Console.out.printf("%s", message);
		Console.out.printf("%s", "\n");
		Console.out.flush();
	}

	static public boolean isDebug() {
		return debug;
	}

	static public void setDebug(boolean debug) {
		TestLog.debug = debug;
	}

	static public String header = "AM   0 N ***************************** STARTUP OF AM   0 *****************************\n" +
            "                        * PROJECT        : \n" +
            "                        * COMPONENT      : \n" +
            "                        * TEST PROCEDURE : \n" +
            "                        * TOOL VERSION   : \n" +
            "                        * AUTHOR         : \n" +
            "                        * START OF TEST  : \n" +
            "                        * TIME SCALE     : millisecond\n" +
            "                        * SCHEDULING     : AM 0 - TestDriver\n" +
            "                        *****************************************************************************";

	static public String footer = "AM   0 R *************************** TERMINATION OF AM   0 ***************************";

}
