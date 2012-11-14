package eu.compassResearch.rttMbtTmsClientApi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RttMbtClient {

	// User name and id
	private String rttMbtServer;
	private Integer rttMbtPort;
	protected String projectName;
	protected String userName;
	protected String userId;
	private String CmlProject;   // starting and ending with file separator
	private String CmlWorkspace; // no file separator at the end

	public RttMbtClient(String server, Integer port, String user, String id) {
		rttMbtServer = server;
		rttMbtPort = port;
		userName = user;
		userId = id;
		projectName = null;
		CmlProject = null;
		CmlWorkspace = null;
	}

	public Boolean testConenction() {
		Boolean success = true;
		
		jsonTestConnectionCommand cmd = new jsonTestConnectionCommand(this);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			success = false;
		}

		return success;
	}

	public Boolean beginRttMbtSession() {
		jsonCheckFileCacheExistsCommand check =
					new jsonCheckFileCacheExistsCommand(this);
		check.executeCommand();
		if (!check.executedSuccessfully()) {
			jsonStartFileCacheCommand start =
					new jsonStartFileCacheCommand(this);
			start.executeCommand();
			if (!check.executedSuccessfully()) {
				return false;
			}
		}
		return true;
	}

	public Boolean removeRttMbtSession() {
		return false;
	}
	
	public Boolean uploadFile(String filename) {
		Boolean success = true;

		// add workspace
		filename = addLocalWorkspace(filename);
		
		// check if file exists in local file system
		File localFile = new File(filename);
		if (!localFile.exists()) {
			return false;
		}
		
		// check if file already is in cache
		System.out.println("checking if file '" + filename + "' already exists in cache");
		jsonCheckFileInCacheCommand check = new jsonCheckFileInCacheCommand(this);
		check.setFilename(filename);
		check.executeCommand();
		if (check.executedSuccessfully() && check.getResult()) {
			return true;
		} else {
			if (!check.executedSuccessfully()) {
				System.err.println("[FAIL]: checking file '" + filename + "' failed!");
			}
		}

		// if file is not in cache: upload file
		System.out.println("uploading file '" + filename + "' to cache");
		jsonSendFileToCacheCommand cmd = new jsonSendFileToCacheCommand(this);
		cmd.setFilename(filename);
		String reply = cmd.executeCommand();
		success = cmd.executedSuccessfully();
		if (!success) {
			System.err.println("[FAIL]: uploading file '" + filename + "' failed!");
			System.err.println("reply: " + reply);
		}
		return success;
	}
	
	public Boolean uploadDirectory(String directory, Boolean recursive) {
		Boolean success = true;

		// add workspace
		directory = addLocalWorkspace(directory);
		
		// check if directory exists
		File folder = new File(directory);
		if (!folder.isDirectory()) {
			System.err.println("local directory '" + directory + "' does not exist!");
			return false;
		}
		
		// get files in the directory
		File[] files = folder.listFiles();
		List<String> folders = new ArrayList<String>();
		if (files == null) {
			System.err.println("getting file list of directory '" + directory + "' failed!");
			return false;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String filename = removeLocalWorkspace(directory + "/" + files[i].getName());
				success = (uploadFile(filename) && success);
			} else if ((files[i].isDirectory()) && (recursive)) {
				folders.add(directory + "/" + files[i].getName());
			}
		}
		for (int i = 0; i < folders.size(); i++) {
			String subdirname = removeLocalWorkspace(folders.get(i));
			System.out.println("uploading directory '" + subdirname + "'");
			success = (uploadDirectory(subdirname, recursive) && success);
		}
		return success;
	}

	public Boolean downloadFile(String filename) {
		Boolean success = true;

		// add workspace
		filename = addLocalWorkspace(filename);

		// check if file already is up to date
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			jsonCheckFileInCacheCommand check = new jsonCheckFileInCacheCommand(this);
			check.setFilename(filename);
			check.executeCommand();
			if (check.executedSuccessfully() && check.getResult()) {
				return true;
			}
		}
		
		// if file is not up to date: download file
		System.out.println("downloading file '" + filename + "' from cache");
		jsonReceiveFileFromCacheCommand cmd = new jsonReceiveFileFromCacheCommand(this);
		cmd.setFilename(filename);
		String reply = cmd.executeCommand();
		success = cmd.executedSuccessfully();
		if (!success) {
			System.err.println("[FAIL]: downloading file '" + filename + "' failed!");
			System.err.println("reply: " + reply);
		}
		return success;
	}

	public Boolean downloadDirectory(String directory) {
		Boolean success = true;

		// add workspace
		directory = addLocalWorkspace(directory);

		System.out.println("downloading files in directory '" + directory + "' from cache");
		
		// get file list
		jsonGetCachFileListCommand cmd = new jsonGetCachFileListCommand(this);
		cmd.setDirname(directory);
		String reply = cmd.executeCommand();
		success = cmd.executedSuccessfully();
		if (!success) {
			System.err.println("[FAIL]: retrieveing file list for directory '" + directory + "' failed!");
			System.err.println("reply: " + reply);
			return success;
		}

		// get list if filenames
		List<String> filenames = cmd.getFilenames();

		// If no files are in the directory: do nothing
		if (filenames == null) {
			return success;
		}

		// check if local directory exists
		File folder = new File(directory);
		if (folder.isFile()) {
			System.err.println("[FAIL]: '" + directory + "' already exists and is a file!");
			return false;
		}
		if (! folder.isDirectory()) {
			// create directory
			success = folder.mkdirs();
			if (!success) {
				// Directory creation failed
				System.err.println("[FAIL]: creating local directory '" + directory + "' failed!");
			}
		}
		
		// for each file: download file
		for (int idx = 0; idx < filenames.size(); idx++) {
			String filename = removeLocalWorkspace(directory) + "/" + filenames.get(idx);
			downloadFile(filename);
		}
		
		return success;
	}
	
	public Boolean createProject(String project) {
		Boolean success = true;
		
		// set project name
		projectName = project;

		// check if local project directory exists
		File folder = new File(getRttProjectRoot());
		if (folder.isFile()) {
			System.err.println("[FAIL]: '" + project + "' already exists and is a file!");
			return false;
		}

		// create project structure
		if (! folder.exists()) {
			// create directory
			success = folder.mkdirs();
			if (!success) {
				// Directory creation failed
				System.err.println("[FAIL]: creating local directory '" + project + "' failed!");
				return false;
			}
		}
		// check for existing configuration project.rtp
		File projectConf = new File(folder, "project.rtp");
		if (!projectConf.exists()) {
			// extract project template
			File templates = new File(getCmlWorkspace() + getCmlProject() + File.separator + "templates");
			if (!templates.isDirectory()) {
				System.err.println("[FAIL]: local 'templates' directory does not exist!");
				return false;
			}
			File archive = new File(templates, "_Project_compass.zip");
			System.out.println("unzipping archive '" + archive.getPath() + "' into directory '" + getRttProjectRoot() + "'");
			success = unzipArchive(archive.getPath(), getRttProjectRoot());
			if (!success) {
				// extracting project template failed
				System.err.println("[FAIL]: creating project structure failed!");
				return false;
			}
		}

		// upload project structure to cache
		System.out.println("uploading '" + getProjectName() + "' to the rtt-mbt-tms file cache...");
		success = uploadDirectory(getProjectName(), true);
		return success;
	}
	
	public Boolean initProject(String modelName, String modelVersion, String modelFileName) {
		Boolean success = true;

		// check if project has been created / selected
		if (projectName == null) {
			System.err.println("[FAIL]: no project created / selected, yet!");
			return false;
		}
		// copy model to <projectroot>/model/ directory
		File projectRoot = new File(getRttProjectRoot());
		try {
			if (!projectRoot.isDirectory()) {
				System.err.println("[FAIL]: project directory '" + getRttProjectRoot() + "' does not exist!");
				return false;
			}
			File modelDir = new File(projectRoot, "model");
			if (!modelDir.exists()) {
				modelDir.mkdir();
			}
			if (!modelDir.isDirectory()) {
				System.err.println("[FAIL]: model directory '" + modelDir.getPath() + "' does not exist!");
				return false;
			}
			File inputModel = new File(modelFileName);
			if (!inputModel.isFile()) {
				System.err.println("[FAIL]: model file '" + modelFileName + "' does not exist!");
				return false;
			}
			File outputModel = new File(modelDir, "model_dump.xml");
			outputModel.createNewFile();
			
		    FileInputStream fromModel = new FileInputStream(inputModel);
		    FileOutputStream toModel = new FileOutputStream(outputModel);
		    byte[] buffer = new byte[1024];
		    int bytes_read = fromModel.read(buffer);
		    while (bytes_read != -1) {
		        toModel.write(buffer, 0, bytes_read);
		    	bytes_read = fromModel.read(buffer);
		    }
		    fromModel.close();
		    toModel.close();
		}
		catch (IOException e) {
			System.err.println("[FAIL]: unable to copy model file '" + modelFileName + "' to project '" + projectName + "'!");
		}
		// send model to file cache
		// this is actually needed for test generation command
		String modelDirName = getRttProjectRoot() + File.separator
				+ "model" + File.separator;
		uploadFile(modelDirName + "model_dump.xml");

		// perform store model command
		// this is needed for livelock-check, conftool and sigmaptool
		jsonStoreModelCommand storeModel= new jsonStoreModelCommand(this);
		storeModel.setModelName(modelName);
		storeModel.setModelId(modelVersion);
		storeModel.setModelFile(modelFileName);
		storeModel.executeCommand();
		if (!storeModel.executedSuccessfully()) {
			System.err.println("[FAIL]: unable to store model file '" + modelFileName + "' on RTT-MBT server!");
			return false;
		}

		// unpack _P1.zip
		File templates = new File(getCmlWorkspace() + getCmlProject() + File.separator + "templates");
		if (!templates.isDirectory()) {
			System.err.println("[FAIL]: local 'templates' directory does not exist!");
			return false;
		}
		File archive = new File(templates, "_P1_compass.zip");
		File testProcs = new File(projectRoot, "TestProcedures");
		if (!testProcs.exists()) {
			testProcs.mkdir();
		}
		if (!testProcs.isDirectory()) {
			System.err.println("[FAIL]: '" + testProcs.toString() + "' is not a directory!");
			return false;
		}
		success = unzipArchive(archive.getPath(), testProcs.getPath());

		// perform livelock check
		System.out.println("performing livelock check of the model...");
		jsonCheckModelCommand checkModel = new jsonCheckModelCommand(this);
		checkModel.setModelName(modelName);
		checkModel.setModelId(modelVersion);
		checkModel.executeCommand();
		if (!checkModel.executedSuccessfully()) {
			System.err.println("[FAIL]: livelock check of model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			return false;
		}

		// perform conftool command
		System.out.println("creating initial configuration for abstract test procedure _P1...");
		jsonConftoolCommand config = new jsonConftoolCommand(this);
		config.setModelName(modelName);
		config.setModelId(modelVersion);
		config.setTestProcName("_P1");
		config.executeCommand();
		if (!config.executedSuccessfully()) {
			System.err.println("[FAIL]: creating empty configuration for model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			return false;
		}

		// perform sigmaptool command
		System.out.println("creating initial signal map for abstract test procedure _P1...");
		jsonSigmaptoolCommand sigmap = new jsonSigmaptoolCommand(this);
		sigmap.setModelName(modelName);
		sigmap.setModelId(modelVersion);
		sigmap.setTestProcName("_P1");
		sigmap.executeCommand();
		if (!sigmap.executedSuccessfully()) {
			System.err.println("[FAIL]: creating initial signal map for model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			return false;
		}

		// return result
		return success;
	}

	private Boolean unzipArchive(String archiveName, String targetDirectory) {
		Boolean success = true;
		System.out.println("extracting archive '" + archiveName + "' to directory '" + targetDirectory + "'");
		try {
			File folder = new File(targetDirectory);
			if (!folder.exists()) {
				System.err.println("[FAIL]: target directory '" + targetDirectory + "' does not exist!");
				return false;
			}
			if (!folder.isDirectory()) {
				System.err.println("[FAIL]: '" + targetDirectory + "' is not a directory!");
				return false;
			}
			File zipfile = new File(archiveName);
	    	ZipInputStream stream;
			stream = new ZipInputStream(new FileInputStream(zipfile));
	    	ZipEntry entry = stream.getNextEntry();
	        while (entry != null) {
	            if(entry.isDirectory()) {
	            	// Assume directories are stored parents first then children.
	                File directory = new File(folder, entry.getName());
	                directory.mkdir();
	            } else {
	            	File file = new File(folder, entry.getName());
	            	FileOutputStream filestream = new FileOutputStream(file);
	                BufferedOutputStream dest = new BufferedOutputStream(filestream, 1024);
	                byte data[] = new byte[1024];
	                // read first chunk
	                int count = stream.read(data, 0, 1024);
	                while (count != -1) {
	                	// write chunk to file
	                    dest.write(data, 0, count);
	                    // prepare next loop
		                count = stream.read(data, 0, 1024);
	                }
	                dest.flush();
	                dest.close();
	            }
	        	// prepare next loop
		    	entry = stream.getNextEntry();
	        }
	    	stream.close();
		} catch (FileNotFoundException e) {
			System.err.println("[FAIL]: template archive '" + archiveName + "' does not exist!");
			return false;
		} catch (IOException e) {
			System.err.println("[FAIL]: unable to extract files from template archive '_Project_compass.zip'!");
			return false;
		}
		return success;
	}
	
	public Boolean generateTestProcedure(String abstractTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String confDirName = getProjectName() + File.separator
				+ "TestProcedures" + File.separator
				+ abstractTestProc + File.separator
				+ "conf" +  File.separator;
		uploadFile(confDirName + "configuration.csv");
		uploadFile(confDirName + "signalmap.csv");
		uploadFile(confDirName + "advanced.conf");
		uploadFile(confDirName + "addgoals.conf");
		uploadFile(confDirName + "addgoalsordered.conf");
		
		// generate-test-command
		System.out.println("generating concrete test procedure _P1...");
		jsonGenerateTestCommand cmd = new jsonGenerateTestCommand(this);
		cmd.setTestProcName("TestProcedures/" + abstractTestProc);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			System.err.println("[FAIL]: generatig RTT_TestProcedures/" + abstractTestProc + " failed!");
			// download debugging data to local directory
			// - error.log
			// - rtt-mbt-tms.out
			// - rtt-mbt-tms.err
			String dirname = getProjectName() + File.separator;
			downloadFile(dirname + "error.log");
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// - configuration.csv.bak
			dirname = getProjectName() + File.separator
					+ "TestProcedures" + File.separator
					+ abstractTestProc + File.separator
					+ "conf" + File.separator;
			downloadFile(dirname + "configuration.csv.bak");
			// - generation.log
			// - error.log
			dirname = getProjectName() + File.separator
					+ "TestProcedures" + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator;
			downloadFile(dirname + "generation.log");
			downloadFile(dirname + "errors.log");
			return false;
		}
		// download generated files to local directory:

		// from cache/<user-id>/<project-name>/model/
		// - symbols.log
		// - testcases.csv
		// - testcases.db
		// - tc2req.csv
		// - req2tc.csv
		// - overall_coverage.csv
		// - uncovered_testcases.csv
		// - unreachable_testcases.csv
		String dirname = getProjectName() + File.separator
		+ "model" + File.separator;
		downloadFile(dirname + "symbols.log");
		downloadFile(dirname + "testcases.csv");
		downloadFile(dirname + "testcases.db");
		downloadFile(dirname + "tc2req.csv");
		downloadFile(dirname + "req2tc.csv");
		downloadFile(dirname + "overall_coverage.csv");
		downloadFile(dirname + "uncovered_testcases.csv");
		downloadFile(dirname + "unreachable_testcases.csv");

		// from cache/<user-id>/<project-name>/<testproc>/conf
		// - configuration.csv
		dirname = getProjectName() + File.separator
				+ "TestProcedures" + File.separator
				+ abstractTestProc + File.separator
				+ "conf" + File.separator;
		downloadFile(dirname + "configuration.csv");

		// from cache/<user-id>/<project-name>/<testproc>/log
		// - addgoalcoverage.csv
		// - covered_testcases.csv
		// - focus_points_to_addgoals.conf
		dirname = getProjectName() + File.separator
				+ "TestProcedures" + File.separator
				+ abstractTestProc + File.separator
				+ "log" + File.separator;
		downloadFile(dirname + "addgoalcoverage.csv");
		downloadFile(dirname + "covered_testcases.csv");
		downloadFile(dirname + "focus_points_to_addgoals.conf");

		// from cache/<user-id>/<project-name>/<testproc>/model
		// - signals.dat
		// - signals.json
		// - *.pdf
		dirname = getProjectName() + File.separator
				+ "TestProcedures" + File.separator
				+ abstractTestProc + File.separator
				+ "model";
		downloadDirectory(dirname);

		// from cache/<user-id>/<project-name>/<testproc>/testdata
		// - signals.dat
		dirname = getProjectName() + File.separator
				+ "TestProcedures" + File.separator
				+ abstractTestProc + File.separator
				+ "testdata";
		downloadDirectory(dirname);

		// download concrete test procedure from cache
		dirname = getProjectName() + File.separator
				+ "RTT_TestProcedures" + File.separator
				+ abstractTestProc + File.separator;
		downloadDirectory(dirname + "conf");
		downloadDirectory(dirname + "inc");
		downloadDirectory(dirname + "specs");
		downloadDirectory(dirname + "testdata");

		return success;
	}
	
	public Boolean compileTestProcedure(String concreteTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		// /conf/
		// /inc/
		// /specs/
		// /stubs/
		// /RTT_Testprocedure/<testproc>/conf/
		// /RTT_Testprocedure/<testproc>/inc/
		// /RTT_Testprocedure/<testproc>/specs/
		// /RTT_Testprocedure/<testproc>/stubs/
		String dirName = getProjectName() + File.separator;
		uploadDirectory(dirName + "conf", false);
		uploadDirectory(dirName + "inc", false);
		uploadDirectory(dirName + "specs", false);
		uploadDirectory(dirName + "stubs", false);
		dirName = getProjectName() + File.separator
				+ "RTT_TestProcedures" + File.separator
				+ concreteTestProc + File.separator;
		uploadDirectory(dirName + "conf", false);
		uploadDirectory(dirName + "inc", false);
		uploadDirectory(dirName + "specs", false);
		uploadDirectory(dirName + "stubs", false);
		
		// generate-test-command
		System.out.println("generating concrete test procedure _P1...");
		jsonGenerateTestCommand cmd = new jsonGenerateTestCommand(this);
		cmd.setTestProcName("RTT_TestProcedures/" + concreteTestProc);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			System.err.println("[FAIL]: compiling RTT_TestProcedures/" + concreteTestProc + " failed!");
			// download debug information:
			// - rtt-mbt-tms.out
			// - rtt-mbt-tms.err
			String dirname = getProjectName() + File.separator;
			downloadDirectory(dirname + "rtt-mbt-tms-execution.err");
			downloadDirectory(dirname + "rtt-mbt-tms-execution.out");
		} else {
			// download generated files (not src, stubsrc, etc.)
			dirName = getProjectName() + File.separator;
			downloadDirectory(dirName + "conf");
			downloadDirectory(dirName + "inc");
			downloadDirectory(dirName + "specs");
			downloadDirectory(dirName + "stubs");
			dirName = getProjectName() + File.separator
					+ "RTT_TestProcedures" + File.separator
					+ concreteTestProc + File.separator;
			downloadDirectory(dirName + "conf");
			downloadDirectory(dirName + "inc");
			downloadDirectory(dirName + "specs");
			downloadDirectory(dirName + "stubs");
		}
		return success;
	}
	
	public String addLocalWorkspace(String filename) {
		if (filename == null) return filename;
		String workspace = getCmlWorkspace() + getCmlProject();
		return workspace + filename;
	}
	
	public String removeLocalWorkspace(String filename) {
		if (filename == null) return filename;
		String workspace = getCmlWorkspace() + getCmlProject();
		if ((workspace != null) &&
			(filename.startsWith(workspace))) {
			System.out.println("removing '" + workspace + "' from '" + filename + "'");
			return filename.substring(workspace.length());
		} else {
			System.out.println("workspace '" + workspace + "' is no prefix of '" + filename + "'");
			return filename;
		}
	}
	
	public String getRttMbtServer() {
		return rttMbtServer;
	}

	public void setRttMbtServer(String rttMbtServer) {
		this.rttMbtServer = rttMbtServer;
	}

	public Integer getRttMbtPort() {
		return rttMbtPort;
	}

	public void setRttMbtPort(Integer rttMbtPort) {
		this.rttMbtPort = rttMbtPort;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCmlWorkspace() {
		if (CmlWorkspace == null) return "";
		return CmlWorkspace;
	}

	public void setCmlWorkspace(String workspace) {
		this.CmlWorkspace = workspace;
	}
	
	public String getCmlProject() {
		if (CmlProject == null) return "";
		return CmlProject;
	}

	public void setCmlProject(String project) {
		this.CmlProject = project + File.separator;
	}
	
	public String getRttProjectRoot() {
		return getCmlWorkspace() + getCmlProject() + getProjectName();
	}
}
