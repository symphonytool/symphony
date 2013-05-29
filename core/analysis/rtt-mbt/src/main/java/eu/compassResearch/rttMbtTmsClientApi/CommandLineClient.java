package eu.compassResearch.rttMbtTmsClientApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class CommandLineClient {

	static String username = null;
	static String userid = null;
	static String server = null;
	static String port = null;
	static String workspace = null;
	static String container = null;
	static String project = null;
	
	public enum Commands {
		TestConnection,
		AbortCommand,
	    StartFileCache,
	    CheckFileCacheExists,
	    RemoveFileCache,
	    CreateProject,
	    InitProject,
	    StoreModel,
		CheckModel,
		Conftool,
		Sigmaptool,
	    CheckModelExists,
	    GenerateTest,
	    GenerateSimulation,
	    CleanTest,
	    CompileTest,
	    RunTest,
	    ReplayTest,
	    DocTest,
	    SendFileToCache,
	    SendDirectoryToCache,
	    CheckFileInCache,
	    GetCacheFileList,
	    ReceiveFileFromCache,
	    ReceiveDirectoryFromCache,
	    RemoveFileFromCache,
	    Undefined
	}

	static Commands command = Commands.Undefined;
	
	public static void usage() {
		System.out.println("usage: <command> <parameters>\n" +
				"commands:\n" +
				"--TestConnection [<server>] [<port>]\n" +
				"--AbortCommand <command id>\n" +
				"--StartFileCache [<user name>] [<user id>]\n" +
				"--CheckFileCacheExists [<user name>] [<user id>]\n" +
				"--RemoveFileCache [<user name>] [<user id>]\n" +
				"--CreateProject [<project container>] <project name>\n" +
				"--InitProject [<project container>] <project name> <model name> <model version> <model file>\n" +
				"--StoreModel [<project container>] <project name> <model name> <model version> <model file>\n" +
				"--CheckModel [<project container>] <project name>\n" +
				"--Conftool [<project container>] <project name>\n" +
				"--Sigmaptool [<project container>] <project name>\n" +
				"--CheckModelExists [<project container>] <project name>\n" +
				"--GenerateTest [<project container> <Project>] <TP gen. context name>\n" +
				"--GenerateSimulation [<project container> <Project>] <TP gen. context name>\n" +
				"--CleanTest [<project container>] <project name> <TP name>\n" +
				"--CompileTest [<project container>] <project name> <TP name>\n" +
				"--RunTest [<project container>] <project name> <TP name>\n" +
				"--ReplayTest [<project container>] <project name> <TP name>\n" +
				"--DocTest [<project container>] <project name> <TP name>\n" +
				"--SendFileToCache [<user name> <user id>] <local file name>\n" +
				"--SendDirectoryToCache [<user name> <user id>] <local directory name>\n" +
				"--CheckFileInCache [<user name> <user id>] <local file name>\n" +
				"--GetCacheFileList [<user name> <user id>] <local directory name>\n" +
				"--ReceiveFileFromCache [<user name> <user id>] <local file name>\n" +
				"--ReceiveDirectoryFromCache [<user name> <user id>] <local file name>\n" +
				"--RemoveFileFromCache [<user name> <user id>] <local file name>");
	}

	public static void exitWithError(String msg) {
		System.err.println("*** error: " + msg + "!");
		usage();
		System.exit(1);		
	}
	
	public static void parseParameters(String[] args) {
		if (args.length < 1) {
			exitWithError("illegal number of arguments");
		}

		if (args[0].compareTo("--TestConnection") == 0) {
			command = Commands.TestConnection;
			if (args.length > 3) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--AbortCommand") == 0) {
			command = Commands.AbortCommand;
			if (args.length != 2) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--StartFileCache") == 0) {
			command = Commands.StartFileCache;
			if (args.length > 3) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--CheckFileCacheExists") == 0) {
			command = Commands.CheckFileCacheExists;
			if (args.length > 3) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--RemoveFileCache") == 0) {
			command = Commands.RemoveFileCache;
			if (args.length > 3) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--CreateProject") == 0) {
			command = Commands.CreateProject;
			if ((args.length < 2) || (args.length > 3)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--InitProject") == 0) {
			command = Commands.InitProject;
			if ((args.length < 5) || (args.length > 6)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--StoreModel") == 0) {
			command = Commands.StoreModel;
			if ((args.length < 4) || (args.length > 5)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--CheckModel") == 0) {
			command = Commands.CheckModel;
			if ((args.length < 2) || (args.length > 3)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--Conftool") == 0) {
			command = Commands.Conftool;
			if ((args.length < 2) || (args.length > 3)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--Sigmaptool") == 0) {
			command = Commands.Sigmaptool;
			if ((args.length < 2) || (args.length > 3)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--CheckModelExists") == 0) {
			command = Commands.CheckModelExists;
			if ((args.length < 2) || (args.length > 3)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--GenerateTest") == 0) {
			command = Commands.GenerateTest;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--GenerateSimulation") == 0) {
			command = Commands.GenerateSimulation;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--CleanTest") == 0) {
			command = Commands.CleanTest;
			if ((args.length < 3) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--CompileTest") == 0) {
			command = Commands.CompileTest;
			if ((args.length < 3) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--RunTest") == 0) {
			command = Commands.RunTest;
			if ((args.length < 3) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--ReplayTest") == 0) {
			command = Commands.ReplayTest;
			if ((args.length < 3) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--DocTest") == 0) {
			command = Commands.DocTest;
			if ((args.length < 3) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--SendFileToCache") == 0) {
			command = Commands.SendFileToCache;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--SendDirectoryToCache") == 0) {
			command = Commands.SendDirectoryToCache;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--CheckFileInCache") == 0) {
			command = Commands.CheckFileInCache;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--GetCacheFileList") == 0) {
			command = Commands.GetCacheFileList;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--ReceiveFileFromCache") == 0) {
			command = Commands.ReceiveFileFromCache;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--ReceiveDirectoryFromCache") == 0) {
			command = Commands.ReceiveDirectoryFromCache;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}

		if (args[0].compareTo("--RemoveFileFromCache") == 0) {
			command = Commands.RemoveFileFromCache;
			if ((args.length < 2) || (args.length > 4)) {
				exitWithError("illegal number of arguments");
			}
			return;
		}
	}

	public static void read_settings_file(String filename) {
		
		// open for settings file
		InputStream istream = null;
		try {
			istream = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			System.err.println("*** error: unable to open settings file '" + filename + "'");
			System.exit(1);
		}
		Scanner fileScanner;
		fileScanner = new Scanner(istream);

		// parse settings
		Scanner line;
		int lineno = 0;
		while (fileScanner.hasNextLine()) {
			lineno++;
			line = new Scanner(fileScanner.nextLine());
			line.useDelimiter("=");
			String propName = null;
			String propValue = null;
			if (line.hasNext()) {
				propName = line.next();
			}
			if (line.hasNext()) {
				propValue = line.next();
			}
			line.close();
			if ((propName == null) || (propValue == null)) {
				System.err.println("*** error: systax error in line " + lineno + ": '" + line.toString() + "'");
			} else if (propName.compareTo("RTT_MBT_USERNAME") == 0) {
				username=propValue;
			} else if (propName.compareTo("RTT_MBT_USERID") == 0) {
				userid=propValue;
			} else if (propName.compareTo("RTT_MBT_SERVER") == 0) {
				server=propValue;
			} else if (propName.compareTo("RTT_MBT_SERVERPORT") == 0) {
				port=propValue;
			} else if (propName.compareTo("RTT_MBT_WORKSPACE") == 0) {
				workspace=propValue;
			} else if (propName.compareTo("RTT_MBT_PROJECT_CONTAINER") == 0) {
				container=propValue;
			} else if (propName.compareTo("RTT_MBT_PROJECT") == 0) {
				project=propValue;
			}
		}
		fileScanner.close();
	}
	
	public static void main(String[] args) {

		// parse parameters
		parseParameters(args);
				
		// check settings file in $RTT_TESTCONTEXT
		String testcontext = System.getenv("RTT_TESTCONTEXT");
		if (testcontext == null) {
			exitWithError("Please define environment variable RTT_TESTCONTEXT!");
		}
		File settings = new File(testcontext + File.separator + ".rttjsonrc");
		if (settings.isFile()) {
			read_settings_file(settings.getAbsolutePath());
		}

		// check environment variables (overwriting settings file
		if (System.getenv("RTT_MBT_USERNAME") != null) {
			username = System.getenv("RTT_MBT_USERNAME");
		}
		if (System.getenv("RTT_MBT_USERID") != null) {
			userid = System.getenv("RTT_MBT_USERID");
		}
		if (System.getenv("RTT_MBT_SERVER") != null) {
			server = System.getenv("RTT_MBT_SERVER");
		}
		if (System.getenv("RTT_MBT_SERVERPORT") != null) {
			port = System.getenv("RTT_MBT_SERVERPORT");
		}
		if (System.getenv("RTT_MBT_WORKSPACE") != null) {
			workspace = System.getenv("RTT_MBT_WORKSPACE");
		}
		if (System.getenv("RTT_MBT_PROJECT_CONTAINER") != null) {
			container = System.getenv("RTT_MBT_PROJECT_CONTAINER");
		}
		if (System.getenv("RTT_MBT_PROJECT") != null) {
			project = System.getenv("RTT_MBT_PROJECT");
		}
		
		// create client instance
		if ((userid == null) || (server == null) || (port == null)) {
			System.err.println("*** error: not enough arguments provided!\n" +
                               "Please add username, userid, server and port settings to '" +
                               System.getProperty("user.home") + File.separator + ".rttjsonrc'\n" +
                               "or define environment variables RTT_MBT_USERNAME, RTT_MBT_USERID, RTT_MBT_SERVER, RTT_MBT_SERVERPORT.\n");
			System.exit(1);
		}
		RttMbtClient client = new RttMbtClient(server, Integer.parseInt(port), username, userid);

		if (workspace == null) {
			// get CML workspace path
			File directory = new File(".");
			workspace = directory.getAbsolutePath() + File.separator;
		}

		// set CML workspace path
		client.setCmlWorkspace(workspace);

		if (container == null) {
			// create/set CML project
			container = "ProjectContainer";
			File cmlprjdir = new File(client.getCmlWorkspace(), container);
			if (!cmlprjdir.exists()) {
				cmlprjdir.mkdirs();
			}
		}
		
		// set CML project name
		client.setCmlProject(container);

		// set RTT-MBT project name
		if (project == null) {
			client.setProjectName("Project");
		} else {
			client.setProjectName(project);
		}

		// execute command
		String modelname, modelversion, modelfile, path;
		switch(command) {

		// test connection to rtt-mbt-tms server
		case TestConnection:
			client.setConsoleName("TestConnection");
			if (args.length > 1) {
				server = args[1];
				client.setRttMbtServer(server);
			}
			if (args.length > 2) {
				port = args[2];
				client.setRttMbtPort(Integer.parseInt(port));
			}
			if (client.testConenction()) {
				System.out.println("[PASS]: test RTT-MBT server connection");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: test RTT-MBT server connection");
				System.exit(1);
			}
			break;

		// abort task
		case AbortCommand:
			client.setConsoleName("AbortCommand");
			if (client.abortCommand(args[1])) {
				System.out.println("[PASS]: abort current task");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: abort current task");
				System.exit(1);
			}
			break;

		// start RTT-MBT-TMS session
		case StartFileCache:
			client.setConsoleName("StartFileCache");
			if (args.length > 1) {
				client.setUserName(args[1]);
			}
			if (args.length > 2) {
				client.setUserId(args[2]);
			}
			if (client.beginRttMbtSession()) {
				System.out.println("[PASS]: begin RTT-MBT session");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: begin RTT-MBT session");
				System.exit(1);
			}
			break;

		// Remove RTT-MBT session
		case RemoveFileCache:
			client.setConsoleName("RemoveFileCache");
			if (args.length > 1) {
				client.setUserName(args[1]);
			}
			if (args.length > 2) {
				client.setUserId(args[2]);
			}
			if (client.removeRttMbtSession()) {
				System.out.println("[PASS]: Remove RTT-MBT session");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Remove RTT-MBT session");
				System.exit(1);
			}
			break;

		// Create Project
		case CreateProject:
			client.setConsoleName("CreateProject");
			String project = null;
			if (args.length > 2) {
				client.setCmlProject(args[1]);
				project = args[2];
			} else {
				project = args[1];
			}
			if (client.createProject(project)) {
				System.out.println("[PASS]: Create Project");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Create Project");
				System.exit(1);
			}
			break;

		// Init Project
		case InitProject:
			client.setConsoleName("InitProject");
			if (args.length > 5) {
				client.setCmlProject(args[1]);
				client.setProjectName(args[2]);
				modelname = args[3];
				modelversion = args[4];
				modelfile = args[5];
			} else {
				client.setProjectName(args[1]);
				modelname = args[2];
				modelversion = args[3];
				modelfile = args[4];
			}
			if (client.initProject(modelname, modelversion, modelfile)) {
				System.out.println("[PASS]: Init Project");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Init Project");
				System.exit(1);
			}
			break;

		// Generate Test
		case GenerateTest:
			client.setConsoleName("GenerateTest");
			if (args.length > 3) {
				client.setCmlProject(args[1]);
				client.setProjectName(args[2]);
				path = args[3];
			} else {
				path = args[1];
			}
			if (client.generateTestProcedure(path)) {
				System.out.println("[PASS]: Generate Test");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Generate Test");
				System.exit(1);
			}
			break;

		// Generate Simulation
		case GenerateSimulation:
			client.setConsoleName("GenerateSimulation");
			if (args.length > 3) {
				client.setCmlProject(args[1]);
				client.setProjectName(args[2]);
				path = args[3];
			} else {
				path = args[1];
			}
			if (client.generateSimulation(path)) {
				System.out.println("[PASS]: Generate Simulation");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Generate Simulation");
				System.exit(1);
			}
			break;

		// Clean Test
		case CleanTest:
			client.setConsoleName("CleanTest");
			if (args.length > 3) {
				client.setCmlProject(args[1]);
				client.setProjectName(args[2]);
				path = args[3];
			} else {
				client.setProjectName(args[1]);
				path = args[2];
			}
			if (client.cleanTestProcedure(path)) {
				System.out.println("[PASS]: Clean Test");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Clean Test");
				System.exit(1);
			}
			break;

		// Compile Test
		case CompileTest:
			client.setConsoleName("CompileTest");
			if (args.length > 3) {
				client.setCmlProject(args[1]);
				client.setProjectName(args[2]);
				path = args[3];
			} else {
				client.setProjectName(args[1]);
				path = args[2];
			}
			if (client.compileTestProcedure(path)) {
				System.out.println("[PASS]: Compile Test");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Compile Test");
				System.exit(1);
			}
			break;

		// Run Test
		case RunTest:
			client.setConsoleName("RunTest");
			if (args.length > 3) {
				client.setCmlProject(args[1]);
				client.setProjectName(args[2]);
				path = args[3];
			} else {
				client.setProjectName(args[1]);
				path = args[2];
			}
			if (client.runTestProcedure(path)) {
				System.out.println("[PASS]: Run Test");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Run Test");
				System.exit(1);
			}
			break;

		// Replay Test
		case ReplayTest:
			client.setConsoleName("ReplayTest");
			if (args.length > 3) {
				client.setCmlProject(args[1]);
				client.setProjectName(args[2]);
				path = args[3];
			} else {
				client.setProjectName(args[1]);
				path = args[2];
			}
			if (client.replayTestProcedure(path)) {
				System.out.println("[PASS]: Replay Test");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Replay Test");
				System.exit(1);
			}
			break;

		// generate test procedure documentation
		case DocTest:
			client.setConsoleName("DocTest");
			if (args.length > 3) {
				client.setCmlProject(args[1]);
				client.setProjectName(args[2]);
				path = args[3];
			} else {
				client.setProjectName(args[1]);
				path = args[2];
			}
			if (client.docTestProcedure(path)) {
				System.out.println("[PASS]: Doc Test");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Doc Test");
				System.exit(1);
			}
			break;

		// upload file
		case SendFileToCache:
			client.setConsoleName("SendFileToCache");
			if (args.length > 3) {
				client.setUserName(args[1]);
				client.setUserId(args[2]);
				path = args[3];
			} else {
				path = args[1];
			}
			if (client.uploadFile(path)) {
				System.out.println("[PASS]: Upload File " + path);
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Upload File " + path);
				System.exit(1);
			}
			break;

		// upload directory
		case SendDirectoryToCache:
			client.setConsoleName("SendDirectoryToCache");
			if (args.length > 3) {
				client.setUserName(args[1]);
				client.setUserId(args[2]);
				path = args[3];
			} else {
				path = args[1];
			}
			if (client.uploadDirectory(path, true)) {
				System.out.println("[PASS]: Upload Directory " + path);
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Upload Directory" + path);
				System.exit(1);
			}
			break;

		// download file
		case ReceiveFileFromCache:
			client.setConsoleName("ReceiveFileFromCache");
			if (args.length > 3) {
				client.setUserName(args[1]);
				client.setUserId(args[2]);
				path = args[3];
			} else {
				path = args[1];
			}
			if (client.downloadFile(path)) {
				System.out.println("[PASS]: Download File " + path);
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Download File " + path);
				System.exit(1);
			}
			break;

		// Download Directory
		case ReceiveDirectoryFromCache:
			client.setConsoleName("ReceiveDirectoryFromCache");
			if (args.length > 3) {
				client.setUserName(args[1]);
				client.setUserId(args[2]);
				path = args[3];
			} else {
				path = args[1];
			}
			if (client.downloadDirectory(path)) {
				System.out.println("[PASS]: Download Directory " + path);
				System.exit(0);
			} else {
				System.err.println("[FAIL]: Download Directory " + path);
				System.exit(1);
			}
			break;

		// 

/*
		case :
			if (client.) {
				System.out.println("[PASS]: ");
				System.exit(0);
			} else {
				System.err.println("[FAIL]: ");
				System.exit(1);
			}
			break;

		// 

	    StoreModel,
		CheckModel,
		Conftool,
		Sigmaptool,
	    CheckModelExists,
	    RemoveFileFromCache,
	    CheckFileCacheExists,

 */
			
		default:
			System.err.println("*** error: command not implemented, yet!");
		}
	}
}
