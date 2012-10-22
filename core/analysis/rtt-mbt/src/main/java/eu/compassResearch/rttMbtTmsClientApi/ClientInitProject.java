/**
 * 
 */
package eu.compassResearch.rttMbtTmsClientApi;

/**
 * @author uwe
 *
 */
public class ClientInitProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// create client instance
		RttMbtClient client = new RttMbtClient("localhost", 9116, "uwe", "uschulze@informatik.uni-bremen.de");

		// test connection to rtt-mbt-tms server
		if (client.testConenction()) {
			System.out.println("[PASS]: test RTT-MBT server connection");
		} else {
			System.err.println("[FAIL]: test RTT-MBT server connection");
			return;
		}

		// start RTT-MBT-TMS session
		if (client.beginRttMbtSession()) {
			System.out.println("[PASS]: begin RTT-MBT session");
		} else {
			System.err.println("[FAIL]: begin RTT-MBT session");
			return;
		}
		
		// download templates
		if (client.downloadDirectory("templates")) {
			System.out.println("[PASS]: downloading templates");
		} else {
			System.err.println("[FAIL]: downloading templates");
			return;
		}

		// create/select a new project
		client.createProject("turnIndicationUML");
		
		// initialize project with a model
		if (client.initProject("turnIndicationUML", "1.0", "AS-401-turn-indication-UML.xml")) {
			System.out.println("[PASS]: init project");
		} else {
			System.err.println("[FAIL]: init project");
			return;
		}

		// create a new abstract test procedure
		// - download templates/_P1_compass.zip
		// - unzip _P1_compass.zip

		// generate concrete test procedure
		// - generate-test-command

		// compile test procedure
		// - (@todo) compile-test-command

		// run test procedure
		// - (@todo) run-test-command
		// - (@todo) stop-test-command

		// document test procedure
		// - (@todo) doc-test-command
	}
}
