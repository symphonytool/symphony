/**
 * 
 */
package eu.compassResearch.rttMbtTmsClientApi;

/**
 * @author uwe
 *
 */
public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RttMbtClient client = new RttMbtClient("peacock", 9116, "uwe", "uschulze@informatik.uni-bremen.de");

		// define the current project
		client.setProject("turnIndicationUML");

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

		// create a new project

		// initialize project with a model
		// - store-model-command
		// - check model-command
		// - conftool-command
		// - sigmaptool-command

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
