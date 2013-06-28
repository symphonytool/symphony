package eu.compassresearch.rttMbtTmsClientApi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RttMbtClient {

	// enum type for client mode
	public enum Modes {
		RTT_MBT_VSI_MODE,
		RTT_MBT_DAG_MODE,
		RTT_MBT_SCADE_MODE,
		RTT_MBT_MODE_UNDEFINED
	}

	// User name and id
	private String rttMbtServer;
	private Integer rttMbtPort;
	private String projectName;
	private String userName;
	private String userId;
	private String CmlProject;   // starting and ending with file separator
	private String CmlWorkspace; // no file separator at the end
	private String rttMbtServerVersion;
	private String rttMbtServerUptime;

	private String currentJobId;
	private String RttMbtTestProcFolderName;
	private String RttMbtTProcGenCtxFolderName;
	private Boolean serverWorkspaceChecked;

	// Logging facility
	private String consoleName;
	private IRttMbtLoggingFacility log;
	
	// progress bar
	private IRttMbtProgressBar progress;

	// client mode
	private Modes mode;
	private Boolean verboseLogging;
	private Boolean extraFiles;

	public RttMbtClient(String server, Integer port, String user, String id) {
		rttMbtServer = server;
		rttMbtPort = port;
		userName = user;
		userId = id;
		projectName = null;
		CmlProject = null;
		CmlWorkspace = null;
		rttMbtServerVersion = "-";
		rttMbtServerUptime = "-";
		consoleName = null;
		log = null;
		progress = null;
		currentJobId = null;
		RttMbtTestProcFolderName = getRttMbtTestProcFolderName();
		RttMbtTProcGenCtxFolderName = getRttMbtTProcGenCtxFolderName();
		mode = Modes.RTT_MBT_MODE_UNDEFINED;
		serverWorkspaceChecked = false;
		verboseLogging = false;
		extraFiles = false;
	}

	public void setLoggingFacility(String name, IRttMbtLoggingFacility logger) {
		consoleName = name;
		log = logger;
	}
	
	public void addLogMessage(String msg) {
		String timestamp = "";
		if (getVerboseLogging()) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
			String dateString = sdf.format(date);
			timestamp = dateString + " ";
		}
		if (log != null) {
			log.addLogMessage(timestamp + consoleName, msg + "\n");
		} else {
			System.out.println(timestamp + "[" + consoleName + "]:" + msg);
		}
	}
	
	public void addErrorMessage(String msg) {
		String timestamp = "";
		if (getVerboseLogging()) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
			String dateString = sdf.format(date);
			timestamp = dateString + " ";
		}
		if (log != null) {
			log.addErrorMessage(timestamp + consoleName, msg + "\n");
		} else {
			System.err.println(timestamp + "[" + consoleName + "]: *** error: " + msg);
		}
	}
	
	public void setProgressBar(IRttMbtProgressBar p) {
		progress = p;
	}
	
	public void setProgress(IRttMbtProgressBar.Tasks task, int percent) {
		if (progress != null) {
			progress.setProgress(task, percent);
		} else {
			System.out.println("[" + task.toString() + "]:" + percent + "%");
		}		
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
		if (!check.executedSuccessfully() || (!check.getResult())) {
			jsonStartFileCacheCommand start =
					new jsonStartFileCacheCommand(this);
			start.executeCommand();
			if ((!start.executedSuccessfully()) || (!start.getResult())) {
				return false;
			}
		}
		return true;
	}

	public Boolean removeRttMbtSession() {
		jsonCheckFileCacheExistsCommand check =
				new jsonCheckFileCacheExistsCommand(this);
		check.executeCommand();
		if ((check.executedSuccessfully()) && (check.getResult())){
			System.out.println("remove file cache for user id '" + getUserId() + "'.");
			jsonRemoveFileCacheCommand remove =
					new jsonRemoveFileCacheCommand(this);
			remove.executeCommand();
			if ((!remove.executedSuccessfully()) || (!remove.getResult())) {
				return false;
			}
		} else {
			System.out.println("unable to remove file cache for user id '" + getUserId() + "'!");
			return false;
		}
		return true;
	}
	
	public Boolean checkServerWorkspace() {
		Boolean success = true;

		// if the server workspace for this server has already
		// been checked or created, return true
		if (getServerWorkspaceChecked()) {
			return success;
		}
		
		// check/create server work area
		success = beginRttMbtSession();
		return success;
	}
	
	public Boolean uploadFile(String filename) {
		Boolean success = true;

		// assert that server workspace exists
		if (!checkServerWorkspace()) {
			addErrorMessage("*** error: server working area does not exist an cannot be created!");
			return false;
		}
		
		// add workspace
		filename = addLocalWorkspace(filename);
		
		// check if file exists in local file system
		File localFile = new File(filename);
		if (!localFile.exists()) {
			return false;
		}
		
		// check if file already is in cache
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

		// assert that server workspace exists
		if (!checkServerWorkspace()) {
			return false;
		}

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
			if (!recursive) {
				setProgress(IRttMbtProgressBar.Tasks.Global, (i * 100)/files.length);
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
		cmd.executeCommand();
		success = cmd.executedSuccessfully();
		if (!success) {
			addErrorMessage("[FAIL]: retrieveing file list for directory '" + directory + "' failed!");
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
			setProgress(IRttMbtProgressBar.Tasks.Global, (idx * 100)/filenames.size());
		}
		
		return success;
	}

	public Boolean deleteLocalFile(File file) {
		if (file == null) { return false; }
		if (!file.exists()) { return true; }
		if (!file.isFile()) { return false; }
		if (!file.delete()) {
			return false;
		}
		return true;
	}
	
	public Boolean deleteLocalDirectory(File dir) {
		if (dir == null) { return false; }
		if (!dir.exists()) { return true; }
		if (!dir.isDirectory()) { return false; }

		String[] entries = dir.list();
		if (entries == null) { return dir.delete(); }

		for (int i = 0; i < entries.length; i++) {
			File entry = new File(dir, entries[i]);
			if (entry.isDirectory()) {
				if (!deleteLocalDirectory(entry))
					return false;
			} else {
				if (!entry.delete())
					return false;
			}
		}
		return dir.delete();
	}

	public Boolean deleteRemoteFileOrDir(String filename) {
		Boolean success = true;

		// add workspace
		filename = addLocalWorkspace(filename);

		// remove file on rtt-mbt server
		System.out.println("removing file or directory '" + filename + "' from cache");
		jsonRemoveFileFromCacheCommand cmd = new jsonRemoveFileFromCacheCommand(this);
		cmd.setFilename(filename);
		cmd.executeCommand();
		success = cmd.executedSuccessfully();
		if (!success) {
			addErrorMessage("[FAIL]: removing file or directory '" + filename + "' from cache failed!");
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
			String templatesDir = getCmlWorkspace() + File.separator + "templates";
			File templates = new File(templatesDir);
			if (!templates.isDirectory()) {
				System.err.println("[FAIL]: local templates directory " + templatesDir + " does not exist!");
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
		System.out.println("uploading '" + getProjectPath() + "' to the rtt-mbt-tms file cache...");
		success = uploadDirectory(getProjectPath(), true);
		return success;
	}
	
	public Boolean cleanProject(String project) {
		Boolean success = true;

		// remove local files in /model directory (except model_dump.xml)
		String modelDirName = getCmlWorkspace() + File.separator
				+ getCmlProject() + File.separator
				+ getProjectName() + File.separator
				+ "model" + File.separator;
		deleteLocalFile(new File(modelDirName + "LivelockReport.log"));
		deleteLocalFile(new File(modelDirName + "errors.log"));
		deleteLocalFile(new File(modelDirName + "generation.log"));
		deleteLocalFile(new File(modelDirName + "overall_coverage.csv"));
		deleteLocalFile(new File(modelDirName + "req2tc.csv"));
		deleteLocalFile(new File(modelDirName + "tc2req.csv"));
		deleteLocalFile(new File(modelDirName + "symbols.log"));
		deleteLocalFile(new File(modelDirName + "testcases.csv"));
		deleteLocalFile(new File(modelDirName + "testcases.db"));
		deleteLocalFile(new File(modelDirName + "uncovered_testcases.csv"));
		deleteLocalFile(new File(modelDirName + "unreachable_testcases.csv"));
		deleteLocalFile(new File(modelDirName + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(modelDirName + "rtt-mbt-tms-execution.out"));

		// remove working area on the server
		success = deleteRemoteFileOrDir(getProjectPath());
		return success;
	}
	
	public Boolean livelockCheckModelFile(File model) {
		if (model == null) {
			return false;
		}

		// calculate model parameters
		String modelName = getCmlProject() + "." + getProjectName() + ".model";
		String modelVersion = "1.0";
		String modelFile = model.getAbsolutePath();

		// store model
		jsonStoreModelCommand storeModel= new jsonStoreModelCommand(this);
		storeModel.setModelName(modelName);
		storeModel.setModelId(modelVersion);
		storeModel.setModelFile(modelFile);
		storeModel.executeCommand();
		if (!storeModel.executedSuccessfully()) {
			addErrorMessage("[FAIL]: unable to store model file '" + modelFile + "' on RTT-MBT server!");
			return false;
		} else {
			addLogMessage("[PASS]: model file '" + modelFile + "' stored on RTT-MBT server!");
		}

		// perform live lock check
		jsonCheckModelCommand checkModel = new jsonCheckModelCommand(this);
		checkModel.setGuiPorts(true);
		checkModel.setModelName(modelName);
		checkModel.setModelId(modelVersion);
		checkModel.executeCommand();
		if (!checkModel.executedSuccessfully()) {
			System.err.println("[FAIL]: livelock check of model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			return false;
		}
		
		return true;
	}
	
	public Boolean initProject(String modelName, String modelVersion, String modelFileName) {
		Boolean success = true;

		// check if project has been created / selected
		if (projectName == null) {
			System.err.println("[FAIL]: no project created / selected, yet!");
			return false;
		}

		// remove local model directory
		String modelDirName = getCmlWorkspace() + File.separator
				+ getCmlProject() + File.separator
				+ getProjectName() + File.separator
				+ "model";
		File modeldir = new File(modelDirName);
		deleteLocalDirectory(modeldir);

		// remove working area on the server
		success = deleteRemoteFileOrDir(getProjectPath());

		// copy model to <projectroot>/model/model_dump.xml
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
		// send model_dump.xml to file cache
		// this is actually needed for test generation command
		modelDirName = getRttProjectRoot() + File.separator
				+ "model" + File.separator;
		if (uploadFile(modelDirName + "model_dump.xml")) {
			System.out.println("[PASS]: upload model file '" + modelDirName + "model_dump.xml" + "'!");
		} else {
			System.err.println("[FAIL]: unable to upload model file '" + modelDirName + "model_dump.xml" + "'!");
		}

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
		String templatesDir = getCmlWorkspace() + File.separator + "templates";
		File templates = new File(templatesDir);
		if (!templates.isDirectory()) {
			System.err.println("[FAIL]: local templates directory " + templatesDir + " does not exist!");
			return false;
		}
		File archive = new File(templates, "_P1_compass.zip");
		File testProcs = new File(projectRoot, getRttMbtTProcGenCtxFolderName());
		if (!testProcs.exists()) {
			testProcs.mkdir();
		}
		if (!testProcs.isDirectory()) {
			System.err.println("[FAIL]: '" + testProcs.toString() + "' is not a directory!");
			return false;
		}
		success = unzipArchive(archive.getPath(), testProcs.getPath());
		if (!success) {
			addErrorMessage("[FAIL]: unpacking of template test procedure _P1 failed!");
			return false;
		}

		// perform livelock check
		System.out.println("performing livelock check of the model (with enabled GUI ports)...");
		jsonCheckModelCommand checkModel = new jsonCheckModelCommand(this);
		checkModel.setGuiPorts(true);
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
	
	public Boolean abortCommand(String jobIdString) {

		if (jobIdString == null) {
			return false;
		}
		
		jsonAbortCommand cmd = new jsonAbortCommand(this);
		cmd.setJobId(jobIdString);
		cmd.executeCommand();
		return true;
	}
	
	public Boolean generateTestProcedure(String abstractTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		// cache/<user-id>/<project-name>/model/
		// - model_dump.xml
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String modelDirName = getProjectPath() + File.separator + "model" + File.separator;
		uploadFile(modelDirName + "model_dump.xml");
		uploadFile(modelDirName + "configuration.csv");
		uploadFile(modelDirName + "signalmap.csv");
		uploadFile(modelDirName + "advanced.conf");
		uploadFile(modelDirName + "addgoals.conf");
		uploadFile(modelDirName + "addgoalsordered.conf");
		// cache/<user-id>/<project-name>/<abstract-testproc>/conf
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String confDirName = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" +  File.separator;
		uploadFile(confDirName + "configuration.csv");
		uploadFile(confDirName + "signalmap.csv");
		uploadFile(confDirName + "advanced.conf");
		uploadFile(confDirName + "addgoals.conf");
		uploadFile(confDirName + "addgoalsordered.conf");
		// cache/<user-id>/<project-name>/TMPL
		confDirName = getProjectPath() + File.separator + "TMPL";
		uploadDirectory(confDirName, true);

		// generate-test-command
		System.out.println("generating concrete test procedure" + abstractTestProc + "...");
		jsonGenerateTestCommand cmd = new jsonGenerateTestCommand(this);
		cmd.setGuiPorts(true);
		cmd.setTestProcName("TestProcedures/" + abstractTestProc);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			System.err.println("[FAIL]: generatig RTT_TestProcedures/" + abstractTestProc + " failed!");
			// download debugging data to local directory
			// - rtt-mbt-tms-execution.out
			// - rtt-mbt-tms-execution.err
			String dirname = getProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// - model/error.log
			// - model/genertion.log
			dirname = getProjectPath() + File.separator
					+ "model" + File.separator;
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
			// - TestProcedures/<TP>/generation.log
			// - TestProcedures/<TP>/error.log
			dirname = getProjectPath() + File.separator
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator;
			downloadFile(dirname + "generation.log");
			downloadFile(dirname + "errors.log");
			// - configuration.csv.bak
			dirname = getProjectPath() + File.separator
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "conf" + File.separator;
			downloadFile(dirname + "configuration.csv.bak");
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
		String dirname = getProjectPath() + File.separator
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
		dirname = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" + File.separator;
		downloadFile(dirname + "configuration.csv");

		// from cache/<user-id>/<project-name>/<testproc>/log
		// - addgoalcoverage.csv
		// - covered_testcases.csv
		// - focus_points_to_addgoals.conf
		// - model/error.log
		// - model/genertion.log
		dirname = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "log" + File.separator;
		downloadFile(dirname + "addgoalcoverage.csv");
		downloadFile(dirname + "covered_testcases.csv");
		downloadFile(dirname + "focus_points_to_addgoals.conf");
		if (getExtraFiles()) {
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
		}

		// from cache/<user-id>/<project-name>/<testproc>/model
		// - signals.dat
		// - signals.json
		// - *.pdf
		dirname = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "model";
		downloadDirectory(dirname);

		// from cache/<user-id>/<project-name>/<testproc>/testdata
		// - signals.dat
		dirname = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "testdata";
		downloadDirectory(dirname);

		// download concrete test procedure from cache
		dirname = getProjectPath() + File.separator
				+ getRttMbtTestProcFolderName() + File.separator
				+ abstractTestProc + File.separator;
		downloadDirectory(dirname + "conf");
		downloadDirectory(dirname + "inc");
		downloadDirectory(dirname + "specs");

		return success;
	}
	
	public Boolean replayTestProcedure(String abstractTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		// cache/<user-id>/<project-name>/model/
		// - model_dump.xml
		// - configuration.csv
		// - signalmap.csv
		// - addgoals.conf
		// - addgoalsordered.conf
		String modelDirName = getProjectPath() + File.separator + "model" + File.separator;
		uploadFile(modelDirName + "model_dump.xml");
		uploadFile(modelDirName + "configuration.csv");
		uploadFile(modelDirName + "signalmap.csv");
		uploadFile(modelDirName + "addgoals.conf");
		uploadFile(modelDirName + "addgoalsordered.conf");
		// cache/<user-id>/<project-name>/<abstract-testproc>/conf
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String confDirName = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" +  File.separator;
		uploadFile(confDirName + "configuration.csv");
		uploadFile(confDirName + "signalmap.csv");
		uploadFile(confDirName + "advanced.conf");
		uploadFile(confDirName + "addgoals.conf");
		uploadFile(confDirName + "addgoalsordered.conf");

		// replay-command
		System.out.println("replay test execution " + abstractTestProc + "...");
		jsonReplayTestCommand cmd = new jsonReplayTestCommand(this);
		cmd.setGuiPorts(true);
		cmd.setTestProcName("TestProcedures/" + abstractTestProc);
		cmd.executeCommand();

		// retrieve results
		if (!cmd.executedSuccessfully()) {
			System.err.println("[FAIL]: replay of " + abstractTestProc + " failed!");
			// download debugging data to local directory
			// - rtt-mbt-tms-execution.out
			// - rtt-mbt-tms-execution.err
			String dirname = getProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// - model/error.log
			// - model/genertion.log
			dirname = getProjectPath() + File.separator
					+ "model" + File.separator;
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
			// - TestProcedures<TP>/log/generation.log
			// - TestProcedures<TP>/log/error.log
			dirname = getProjectPath() + File.separator
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator;
			downloadFile(dirname + "generation.log");
			downloadFile(dirname + "errors.log");
			return false;
		}

		// download generated files to local directory:
		String dirname;
		// Testprocedures/<TP>/log/configuration.csv
		dirname = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" + File.separator;;
		downloadFile(dirname + "configuration.csv");
		// Testprocedures/<TP>/log/covered_testcases.csv
		// Testprocedures/<TP>/log/missed_goals.csv
		dirname = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "log" + File.separator;
		downloadFile(dirname + "covered_testcases.csv");
		downloadFile(dirname + "missed_goals.csv");
		// Testprocedures/<TP>/model/signals.json
		dirname = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "model" + File.separator;
		downloadFile(dirname + "signals.dat");
		downloadFile(dirname + "signals.json");
		// RTT_Testprocedures/<TP>/testdata/replay.log
		dirname = getProjectPath() + File.separator
				+ getRttMbtTestProcFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "testdata" + File.separator;
		downloadFile(dirname + "replay.log");
		// - model/error.log
		// - model/genertion.log
		if (getExtraFiles()) {
		dirname = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "log" + File.separator;
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
		}
		
		return success;
	}

	public Boolean generateSimulation(String abstractTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		// cache/<user-id>/<project-name>/model/
		// - model_dump.xml
		// - configuration.csv
		// - signalmap.csv
		// - addgoals.conf
		// - addgoalsordered.conf
		String modelDirName = getProjectPath() + File.separator + "model" + File.separator;
		uploadFile(modelDirName + "model_dump.xml");
		uploadFile(modelDirName + "configuration.csv");
		uploadFile(modelDirName + "signalmap.csv");
		uploadFile(modelDirName + "addgoals.conf");
		uploadFile(modelDirName + "addgoalsordered.conf");
		// cache/<user-id>/<project-name>/TestProcedures/<TP>/conf/
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String confDirName = getProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" +  File.separator;
		uploadFile(confDirName + "configuration.csv");
		uploadFile(confDirName + "signalmap.csv");
		uploadFile(confDirName + "advanced.conf");
		uploadFile(confDirName + "addgoals.conf");
		uploadFile(confDirName + "addgoalsordered.conf");
		
		// generate-simulation-command
		System.out.println("generating simulation from " + abstractTestProc + "...");
		jsonGenerateSimulationCommand cmd = new jsonGenerateSimulationCommand(this);
		cmd.setTestProcName("TestProcedures/" + abstractTestProc);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			success = false;
			System.err.println("[FAIL]: generatig Simulation " + abstractTestProc + " failed!");
			// download debugging data to local directory
			// - rtt-mbt-tms-execution.out
			// - rtt-mbt-tms-execution.err
			String dirname = getProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// - model/error.log
			// - model/genertion.log
			dirname = getProjectPath() + File.separator
					+ "model" + File.separator;
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
			// - generation.log
			// - error.log
			dirname = getProjectPath() + File.separator
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator;
			downloadFile(dirname + "generation.log");
			downloadFile(dirname + "errors.log");
			return false;
		} else {
			String dirName;
			// - model/error.log
			// - model/genertion.log
			if (getExtraFiles()) {
			dirName = getProjectPath() + File.separator
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator;
				downloadFile(dirName + "errors.log");
				downloadFile(dirName + "generation.log");
			}
			// download generated files to local directory:
			// /RTT_Testprocedures/conf/
			// /RTT_Testprocedures/inc/
			// /RTT_Testprocedures/specs/
			// /RTT_Testprocedures/stubs/
			dirName = getProjectPath() + File.separator
					+ getRttMbtTestProcFolderName() + File.separator;
			downloadDirectory(dirName + "conf");
			downloadDirectory(dirName + "inc");
			downloadDirectory(dirName + "specs");
			downloadDirectory(dirName + "stubs");
		}
		
		return success;
	}

	public Boolean cleanTestProcedure(String concreteTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		// /project.rtp
		// /RTT_Testprocedure/<testproc>/conf/
		String fileName = getProjectPath() + File.separator;
		uploadFile(fileName + "project.rtp");
		String dirName = getProjectPath() + File.separator
				+ getRttMbtTestProcFolderName() + File.separator
				+ concreteTestProc + File.separator;
		uploadDirectory(dirName + "conf", false);

		// generate-test-command
		System.out.println("cleanup concrete test procedure " + concreteTestProc + "...");
		jsonCleanTestCommand cmd = new jsonCleanTestCommand(this);
		cmd.setTestProcName("RTT_TestProcedures/" + concreteTestProc);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			// set result to false
			success = false;
			System.err.println("[FAIL]: cleanup of RTT_TestProcedures/" + concreteTestProc + " failed!");

			// download debug information:
			// - rtt-mbt-tms.out
			// - rtt-mbt-tms.err
			String dirname = getProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
		} else {
			// remove local files
			// /stubsrc/
			// /RTT_Testprocedure/stubsrc/
			// /RTT_Testprocedure/<testproc>/src/
			// /RTT_Testprocedure/<testproc>/stubsrc/
			// /RTT_Testprocedure/<testproc>/testdata/
			String dirname = getRttProjectRoot() + File.separator;
			deleteLocalDirectory(new File(dirname + File.separator + "stubsrc"));
			dirname = getRttProjectRoot() + File.separator
					+ getRttMbtTestProcFolderName() + File.separator;
			deleteLocalDirectory(new File(dirname + File.separator + "stubsrc"));
			dirname = getRttProjectRoot() + File.separator
					+ getRttMbtTestProcFolderName() + File.separator
					+ concreteTestProc + File.separator;
			deleteLocalDirectory(new File(dirname + File.separator + "src"));
			deleteLocalDirectory(new File(dirname + File.separator + "stubsrc"));
			deleteLocalDirectory(new File(dirname + File.separator + "testdata"));
		}

		// return result
		return success;
	}
	
	public Boolean compileTestProcedure(String concreteTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		// /project.rtp
		// /conf/
		// /inc/
		// /specs/
		// /stubs/
		// /RTT_Testprocedure/conf/
		// /RTT_Testprocedure/inc/
		// /RTT_Testprocedure/specs/
		// /RTT_Testprocedure/stubs/
		// /RTT_Testprocedure/<testproc>/conf/
		// /RTT_Testprocedure/<testproc>/inc/
		// /RTT_Testprocedure/<testproc>/specs/
		// /RTT_Testprocedure/<testproc>/stubs/
		String fileName = getProjectPath() + File.separator;
		uploadFile(fileName + "project.rtp");
		String dirName = getProjectPath() + File.separator;
		uploadDirectory(dirName + "conf", false);
		uploadDirectory(dirName + "inc", false);
		uploadDirectory(dirName + "specs", false);
		uploadDirectory(dirName + "stubs", false);
		dirName = getProjectPath() + File.separator
				+ getRttMbtTestProcFolderName() + File.separator;
		uploadDirectory(dirName + "conf", false);
		uploadDirectory(dirName + "inc", false);
		uploadDirectory(dirName + "specs", false);
		uploadDirectory(dirName + "stubs", false);
		dirName = getProjectPath() + File.separator
				+ getRttMbtTestProcFolderName() + File.separator
				+ concreteTestProc + File.separator;
		uploadDirectory(dirName + "conf", false);
		uploadDirectory(dirName + "inc", false);
		uploadDirectory(dirName + "specs", false);
		uploadDirectory(dirName + "stubs", false);
		
		// compile-test-command
		System.out.println("compiling concrete test procedure " + concreteTestProc + "...");
		jsonCompileTestCommand cmd = new jsonCompileTestCommand(this);
		cmd.setTestProcName("RTT_TestProcedures/" + concreteTestProc);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			success = false;
			System.err.println("[FAIL]: compiling RTT_TestProcedures/" + concreteTestProc + " failed!");
			// download debug information:
			// - rtt-mbt-tms.out
			// - rtt-mbt-tms.err
			String dirname = getProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
		} else {
			// download generated files (not src, stubsrc, etc.)
			dirName = getProjectPath() + File.separator
					+ getRttMbtTestProcFolderName() + File.separator
					+ concreteTestProc + File.separator
					+ "src" + File.separator;
			// create local target directory (src) if it does not exist
			File src = new File(addLocalWorkspace(dirName));
			if (!src.exists()) {
				System.out.println("creating directory '" + addLocalWorkspace(dirName) + "'");
				if (!src.mkdir()) {
					System.err.println("creating directory '" + addLocalWorkspace(dirName) + "' failed!");
				}
			}
			downloadFile(dirName + "used.conf");
			downloadFile(dirName + "used.rtp");
			downloadFile(dirName + "rtt-compile-test.timestamp");
		}
		return success;
	}
	
	public Boolean runTestProcedure(String concreteTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		String dirName;

		// run-test-command
		System.out.println("run concrete test procedure " + concreteTestProc + "...");
		jsonRunTestCommand cmd = new jsonRunTestCommand(this);
		cmd.setTestProcName("RTT_TestProcedures/" + concreteTestProc);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			// set result to false
			success = false;
			System.err.println("[FAIL]: run RTT_TestProcedures/" + concreteTestProc + " failed!");
			// download debug information:
		} else {
			// download generated files
			dirName = getProjectPath() + File.separator
					+ getRttMbtTestProcFolderName() + File.separator
					+ concreteTestProc + File.separator
					+ "testdata" + File.separator;
			// create local target directory (testdata) if it does not exist
			File testdata = new File(addLocalWorkspace(dirName));
			if (!testdata.exists()) {
				System.out.println("creating directory '" + addLocalWorkspace(dirName) + "'");
				if (!testdata.mkdir()) {
					System.err.println("creating directory '" + addLocalWorkspace(dirName) + "' failed!");
				}
			}
			// - model/error.log
			// - model/genertion.log
			if (getExtraFiles()) {
				downloadDirectory(dirName);
			} else {
				downloadFile(dirName + "VERDICT.txt");
				downloadFile(dirName + "rtt-run-test.log");
			}
		}

		// return result
		return success;
	}
	
	public Boolean docTestProcedure(String concreteTestProc) {
		Boolean success = true;

		// push necessary files to cache:
		// /project.rtp
		// /TMPL
		String fileName = getProjectPath() + File.separator;
		uploadFile(fileName + "componentnames.txt");
		String dirName = getProjectPath() + File.separator;
		uploadDirectory(dirName + "TMPL", true);

		// doc-test-command
		System.out.println("doc concrete test procedure " + concreteTestProc + "...");
		jsonDocTestCommand cmd = new jsonDocTestCommand(this);
		cmd.setTestProcName("RTT_TestProcedures/" + concreteTestProc);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			// set result to false
			success = false;
			System.err.println("[FAIL]: doc RTT_TestProcedures/" + concreteTestProc + " failed!");
			// download debug information:
		} else {
			// download generated files
			dirName = getProjectPath() + File.separator
					+ getRttMbtTestProcFolderName() + File.separator
					+ concreteTestProc + File.separator
					+ "testdata" + File.separator;
			downloadFile(dirName + "RTT_TestProcedures_" + concreteTestProc + "_testprocedure.pdf");
			downloadFile(dirName + "RTT_TestProcedures_" + concreteTestProc + "_testreport.pdf");
			downloadFile(dirName + "signals.dat");
			downloadFile(dirName + "ALL-TC-COV.csv");
			downloadFile(dirName + "map_tc2rts.csv");
		}

		// return result
		return success;
	}
	
	// substitute local names for generation or execution context in the
	// path of <filename> with the ones from the RTT-MBT server
	public String substituteContextFolderNamesLocal2Server(String filename) {
		String serverFilename = null;
		
		// check/substitute generation context
		String localGenerationContext = getProjectPath() + File.separator + getRttMbtTProcGenCtxFolderName();
		String serverGenerationContext = getProjectPath() + File.separator + "TestProcedures";
		if (filename.startsWith(localGenerationContext)) {
			serverFilename = serverGenerationContext + filename.substring(localGenerationContext.length());
		} else {
			serverFilename = filename;
		}
		
		String localExecutionContext = getProjectPath() + File.separator + getRttMbtTestProcFolderName();
		String serverExecutionContext = getProjectPath() + File.separator + "RTT_TestProcedures";
		if (filename.startsWith(localExecutionContext)) {
			serverFilename = serverExecutionContext + filename.substring(localExecutionContext.length());
		}

		return serverFilename;
	}
	
	// substitute local names for generation or execution context in the
	// path of <filename> with the ones from the RTT-MBT server
	public String substituteContextFolderNamesServer2Local(String filename) {
		String serverFilename = null;
		
		// check/substitute generation context
		String localGenerationContext = getProjectPath() + File.separator + getRttMbtTProcGenCtxFolderName();
		String serverGenerationContext = toUnixPath(getProjectPath()) + "/" + "TestProcedures";
		if (filename.startsWith(serverGenerationContext)) {
			serverFilename = localGenerationContext + filename.substring(serverGenerationContext.length());
		} else {
			serverFilename = filename;
		}
		
		String localExecutionContext = getProjectPath() + File.separator + getRttMbtTestProcFolderName();
		String serverExecutionContext = toUnixPath(getProjectPath()) + "/" + "RTT_TestProcedures";
		if (filename.startsWith(serverExecutionContext)) {
			serverFilename = localExecutionContext + filename.substring(serverExecutionContext.length());
		}

		return serverFilename;
	}
	
	public String addLocalWorkspace(String filename) {
		String localFilename = null;

		// check argument
		if (filename == null) return filename;

		// substitute context folder names
		localFilename = substituteContextFolderNamesServer2Local(filename);

		// add workspace prefix
		String workspace = getCmlWorkspace() + File.separator;
		if (!localFilename.startsWith(workspace)) {
			localFilename =  workspace + localFilename;
		}

		// return new filename (with path)
		return localFilename;
	}

	public String removeLocalWorkspace(String filename) {
		String serverFilename = null;

		// check argument
		if (filename == null) return null;

		// remove workspace prefix
		String workspace = getCmlWorkspace() + File.separator;
		if (filename.startsWith(workspace)) {
			serverFilename = filename.substring(workspace.length());
		} else {
			serverFilename = filename;
		}

		// substitute context folder names
		serverFilename = substituteContextFolderNamesLocal2Server(serverFilename);

		// return new filename (with path)
		return serverFilename;
	}
	
	public String toUnixPath(String path) {
		if (File.pathSeparatorChar != '/') {
			return path.replace(File.separatorChar, '/');
		} else {
			return path;
		}
	}
	
	public String getRttMbtServer() {
		return rttMbtServer;
	}

	public void setRttMbtServer(String rttMbtServer) {
		if (this.rttMbtServer.compareTo(rttMbtServer) != 0) {
			setServerWorkspaceChecked(false);
		}
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
		this.CmlProject = project;
	}
	
	public String getProjectPath() {
		return getCmlProject() + File.separator + getProjectName();
	}

	public String getRttProjectRoot() {
		return getCmlWorkspace() + File.separator + getCmlProject() + File.separator + getProjectName();
	}

	public String getConsoleName() {
		return consoleName;
	}

	public void setConsoleName(String consoleName) {
		this.consoleName = consoleName;
	}
	
	public String getCurrentJobId() {
		return currentJobId;
	}

	public void setCurrentJobId(String jobId) {
		this.currentJobId = jobId;
	}
	
	public void setRttMbtTestProcFolderName(String name) {
		RttMbtTestProcFolderName = name;
	}

	public String getRttMbtTestProcFolderName() {
		if ((RttMbtTestProcFolderName == null) ||
			(RttMbtTestProcFolderName.length() == 0)) {
			return "RTT_TestProcedures";
		}
		return RttMbtTestProcFolderName;
	}

	public void setRttMbtTProcGenCtxFolderName(String name) {
		RttMbtTProcGenCtxFolderName = name;
	}

	public String getRttMbtTProcGenCtxFolderName() {
		if ((RttMbtTProcGenCtxFolderName == null) ||
			(RttMbtTProcGenCtxFolderName.length() == 0)) {
			return "TestProcedures";
		}
		return RttMbtTProcGenCtxFolderName;
	}

	public Modes getMode() {
		return mode;
	}

	public void setMode(Modes mode) {
		this.mode = mode;
	}

	public void setMode(String mode) {
		if (mode.compareTo("RTT_MBT_VSI_MODE") == 0) {
			this.mode = Modes.RTT_MBT_VSI_MODE;
		} else if (mode.compareTo("RTT_MBT_DAG_MODE") == 0) {
			this.mode = Modes.RTT_MBT_DAG_MODE;
		} else if (mode.compareTo("RTT_MBT_SCADE_MODE") == 0) {
			this.mode = Modes.RTT_MBT_SCADE_MODE;
		} else {
			this.mode = Modes.RTT_MBT_MODE_UNDEFINED;
		}
	}

	public Boolean getServerWorkspaceChecked() {
		return serverWorkspaceChecked;
	}

	public void setServerWorkspaceChecked(Boolean serverWorkspaceExists) {
		this.serverWorkspaceChecked = serverWorkspaceExists;
	}

	public String getRttMbtServerVersion() {
		return rttMbtServerVersion;
	}

	public void setRttMbtServerVersion(String rttMbtServerVersion) {
		this.rttMbtServerVersion = rttMbtServerVersion;
	}

	public String getRttMbtServerUptime() {
		return rttMbtServerUptime;
	}

	public void setRttMbtServerUptime(String rttMbtServerUptime) {
		this.rttMbtServerUptime = rttMbtServerUptime;
	}

	public Boolean getVerboseLogging() {
		return verboseLogging;
	}

	public void setVerboseLogging(Boolean verboseLogging) {
		this.verboseLogging = verboseLogging;
	}

	public Boolean getExtraFiles() {
		return extraFiles;
	}

	public void setExtraFiles(Boolean extraFiles) {
		this.extraFiles = extraFiles;
	}
}
