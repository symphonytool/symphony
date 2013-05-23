package eu.compassresearch.rttMbtTmsClientApi;

import java.io.File;

public class ClientGenerateTest {
	public static void main(String[] args) {

		// create client instance
		RttMbtClient client = new RttMbtClient("localhost", 9116, "uwe", "uschulze@informatik.uni-bremen.de");

		// get CML workspace path
		File directory = new File(".");
		String pathname = directory.getAbsolutePath() + File.separator;

		// set CML workspace
		client.setCmlWorkspace(pathname);
		System.out.println("using CML workspace '" + pathname + "'");

		// create/set CML project
		pathname = "CMLProject";
		File cmlprjdir = new File(directory, pathname);
		if (!cmlprjdir.exists()) {
			cmlprjdir.mkdirs();
		}
		// set CML project name
		client.setCmlProject(pathname);
		System.out.println("using CML project '" + pathname + "'");

		// set RTT-MBT project name
		client.setProjectName("turnIndicationUML");
		System.out.println("using CML project '" + pathname + "'");

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
		if (client.generateTestProcedure("_P1")) {
			System.out.println("[PASS]: generate test");
		} else {
			System.err.println("[FAIL]: generate test");
			return;
		}

		// generate simulation
		// - generate-simulation-command
		if (client.generateSimulation("SIM")) {
			System.out.println("[PASS]: generate simulation");
		} else {
			System.err.println("[FAIL]: generate simulation");
			return;
		}
	}
}
