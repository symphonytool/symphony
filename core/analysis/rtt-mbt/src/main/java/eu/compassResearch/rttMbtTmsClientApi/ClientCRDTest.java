package eu.compassResearch.rttMbtTmsClientApi;

import java.io.File;

public class ClientCRDTest {
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
		System.out.println("using RTT-MBT project 'turnIndicationUML'");

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

		// clean test procedure
		// - clean-test-command
		if (client.cleanTestProcedure("_P1")) {
			System.out.println("[PASS]: clean test");
		} else {
			System.err.println("[FAIL]: clean test");
			return;
		}

		// compile test procedure
		// - compile-test-command
		if (client.compileTestProcedure("_P1")) {
			System.out.println("[PASS]: compile test");
		} else {
			System.err.println("[FAIL]: compile test");
			return;
		}

		// run test procedure
		// - run-test-command
		// - (@todo) stop-test-command
		if (client.runTestProcedure("_P1")) {
			System.out.println("[PASS]: run test");
		} else {
			System.err.println("[FAIL]: run test");
			return;
		}

		// document test procedure
		// - doc-test-command
		if (client.docTestProcedure("_P1")) {
			System.out.println("[PASS]: doc test");
		} else {
			System.err.println("[FAIL]: doc test");
			return;
		}		
	}
}
