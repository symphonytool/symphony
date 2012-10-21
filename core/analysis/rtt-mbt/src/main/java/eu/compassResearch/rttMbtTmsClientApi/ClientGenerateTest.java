package eu.compassResearch.rttMbtTmsClientApi;

public class ClientGenerateTest {
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
		
		// generate concrete test procedure
		// - generate-test-command
		client.setProjectName("turnIndicationUML");
		if (client.generateTestProcedure("_P1")) {
			System.out.println("[PASS]: generate test");
		} else {
			System.err.println("[FAIL]: generate test");
			return;
		}

		// compile test procedure
		// - (@todo) compile-test-command

		// run test procedure
		// - (@todo) run-test-command
		// - (@todo) stop-test-command

		// document test procedure
		// - (@todo) doc-test-command
	}
}
