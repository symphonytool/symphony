package eu.compassresearch.rttMbtTmsClientApi;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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
	private String userName;
	private String userId;

    // Name of the folder that contains the RT-Tester project (MBT or RTT6).
	// Note: a project can be an eclipse project or a sub-folder within another eclipse project.
    private String rttProjectName;

	// The path to the RT-Tester project in the file system (including the project directory itself)
	// Note: this is the path in the file system to the actual
	//       project folder in the file system. This can be different
	//       to the path of the current eclipse workspace.
	private String rttProjectPath;

	// The name of the eclipse project within the current workspace
	// Note: The actual RT-Tester project can be the eclipse project
	//       or a folder that exists further down in the file system of the project
	// Note: Even if the RT-Tester project is directly an eclipse project inside
	//       the workspace, the project names can be different, because the
	//       RT-Tester project can be mapped into the workspace from somewhere else
	//       in the file system.
    private String workspaceProjectName;

    // The prefix of the RT-Tester project within the current workspace.
    // this is necessary to map the path of a filename or directory
    // between client and server.
    // Note: if there is no prefix, the prefix is "." or null.
    private String workspaceProjectPrefix;

	// the path in the local file system to the current eclipse workspace.
    // this path is only needed for files that are to be stored directly in the workspace (e.g. templates)
	private String workspacePath;

	// runtime information
	private String currentJobId;
	private String RttMbtTestProcFolderName;
	private String RttMbtTProcGenCtxFolderName;
	private Boolean serverWorkspaceChecked;
	private String rttMbtServerVersion;
	private String rttMbtServerUptime;
	private String tmsDatabaseName;

	// Logging facility
	private String consoleName;
	private IRttMbtLoggingFacility log;
	
	// progress bar
	private IRttMbtProgressBar progress;
	private IRttMbtProgressMonitor monitor;

	// client mode
	private Modes mode;
	private Boolean verboseLogging;
	private Boolean extraFiles;
	private Boolean allowMultipleRttMbtTmsTasks;
	
	// Space separated list of patterns to exclude from uploads
	private String noUploadFilePatterns;

	// make tool to be used with Makefiles
	private String sutMakeTool;

	public RttMbtClient(String server, Integer port, String user, String id) {
		rttMbtServer = server;
		rttMbtPort = port;
		userName = user;
		userId = id;
		rttProjectName = null;
		rttProjectPath = null;
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
		allowMultipleRttMbtTmsTasks = true;
		tmsDatabaseName = null;
		setDefaultIgnorePatternProperty();
		setDefaultMakeToolProperty();
	}

	public void setLoggingFacility(String name, IRttMbtLoggingFacility logger) {
		consoleName = name;
		log = logger;
	}
	
	public void addLogMessage(String msg) {
		String timestamp = "";
		if (getVerboseLogging()) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = sdf.format(date);
			timestamp = dateString + " ";
		}
		if (log != null) {
			log.addErrorMessage(timestamp + consoleName, msg + "\n");
		} else {
			System.err.println(timestamp + "[" + consoleName + "]: *** error: " + msg);
		}
	}

	public void showErrorMessagesFromFile(String errorsFileName) {
		File errors = new File(errorsFileName);
		if (errors.isFile()) {
			try {
				String line;
				FileReader readErrors = new FileReader (errors);
				BufferedReader reader = new BufferedReader(readErrors);
				while ((line = reader.readLine()) != null) {
					addErrorMessage(line);
				}
				reader.close();
			} catch (IOException e) {
				addErrorMessage("Failure while reading " + errorsFileName + ": " + e.getMessage());
			}
		} else {
			addErrorMessage("unable to find error log file " + errorsFileName);
		}
	}
	
	public void showLogMessagesFromFile(String FileName) {
		File errors = new File(FileName);
		if (errors.isFile()) {
			try {
				String line;
				FileReader readErrors = new FileReader (errors);
				BufferedReader reader = new BufferedReader(readErrors);
				while ((line = reader.readLine()) != null) {
					addLogMessage(line);
				}
				reader.close();
			} catch (IOException e) {
				addLogMessage("Failure while reading " + FileName + ": " + e.getMessage());
			}
		} else {
			addLogMessage("unable to find error log file " + FileName);
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
	
	public void setProgressMonitor(IRttMbtProgressMonitor m) {
		monitor = m;
	}
	
	public void beginTask(String taskname, int totalWork) {
		if (monitor != null) {
			monitor.beginTask(taskname, totalWork);
		}
	}

	public void setSubTaskName(String taskname) {
		if (monitor != null) {
			monitor.setSubTaskName(taskname);
		}
	}

	public void addCompletedTaskItems(int itemsWorked) {
		if (monitor != null) {
			monitor.addCompletedTaskItems(itemsWorked);
		}
	}

	public boolean isCurrentTaskCanceled() {
		if (monitor != null) {
			return monitor.isCanceled();
		}
		return false;
	}

	public void setCurrentTaskCanceled(boolean canceled) {
		if (monitor != null) {
			monitor.setCanceled(canceled);
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

	// go through the list of ignore patterns to find a match
	public Boolean isToBeIgnored(String filename) {
		Boolean matches = false;
		// header:
		Scanner line;
		line = new Scanner(noUploadFilePatterns);
		line.useDelimiter(":");
		while (line.hasNext()) {
			// get pattern from the list
			String pattern = line.next();

			// replace "." with "\."
			pattern = pattern.replaceAll("\\.", "\\\\.");

			// replace "*" with ".*"
			pattern = pattern.replaceAll("\\*", "\\.\\*");

			if (!pattern.startsWith(".*")) {
				// add regex pattern for any prefix path
				pattern = ".*" + File.separator + pattern;
			}

			// search for substring
			if (filename.matches(pattern)) {
				if (getVerboseLogging()) {
					addLogMessage("Ignore file " + filename + " because of matching ignore-pattern " + pattern);
				}
				matches = true;
			}
		}
		line.close();
		return matches;
	}

	public Boolean uploadFile(String filename) {
		Boolean success = true;

		// check if the file matches any of the patterns in the ignore list
		if (isToBeIgnored(filename)) {
			return success;
		}

		// assert that server workspace exists
		if (!checkServerWorkspace()) {
			addErrorMessage("server working area does not exist and cannot be created!");
			return false;
		}
		
		// check if file exists in local file system
		File localFile = new File(filename);
		if (!localFile.exists()) {
			if (getVerboseLogging()) {
				addLogMessage("note: local file " + filename + " does not exist - no upload possible!");
			}
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
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// if file is not in cache: upload file
		if (getVerboseLogging()) {
			addLogMessage("uploading file " + filename);
		}
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

		// check if the file matches any of the patterns in the ignore list
		if (isToBeIgnored(directory)) {
			return success;
		}

		// assert that server workspace exists
		if (!checkServerWorkspace()) {
			return false;
		}

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
				String filename = directory + "/" + files[i].getName();
				success = (uploadFile(filename) && success);
			} else if ((files[i].isDirectory()) && (recursive)) {
				folders.add(directory + "/" + files[i].getName());
			}
			if (!recursive) {
				setProgress(IRttMbtProgressBar.Tasks.Global, (i * 100)/files.length);
			}
			// check if task has been canceled
			if (isCurrentTaskCanceled()) {
				return false;
			}
		}
		for (int i = 0; i < folders.size(); i++) {
			String subdirname = folders.get(i);
			System.out.println("uploading directory '" + subdirname + "'");
			success = (uploadDirectory(subdirname, recursive) && success);
			setProgress(IRttMbtProgressBar.Tasks.Global, (i * 100)/folders.size());
			// check if task has been canceled
			if (isCurrentTaskCanceled()) {
				return false;
			}
		}
		return success;
	}

	public Boolean downloadFile(String filename) {
		Boolean success = true;

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
		if (isCurrentTaskCanceled()) {
			return false;
		}
		
		// if file is not up to date: download file
		if (getVerboseLogging()) {
			addLogMessage("downloading file " + filename);
		}
		jsonReceiveFileFromCacheCommand cmd = new jsonReceiveFileFromCacheCommand(this);
		cmd.setFilename(filename);
		String reply = cmd.executeCommand();
		success = cmd.executedSuccessfully() && cmd.getResult();
		if (!success) {
			System.err.println("[FAIL]: downloading file '" + filename + "' failed!");
			System.err.println("reply: " + reply);
		}
		return success;
	}

	public Boolean downloadDirectory(String directory) {
		Boolean success = true;

		// check if local directory exists
		File folder = new File(directory);
		if (folder.isFile()) {
			addErrorMessage("'" + directory + "' already exists and is a file!");
			return false;
		}
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (!folder.isDirectory()) {
			addErrorMessage("'" + directory + "' does not exists and could not be created!");
			return false;
		}

		if (getVerboseLogging()) {
			addLogMessage("downloading files in directory " + directory);
		}
		
		// get file list
		jsonGetCachFileListCommand fileCmd = new jsonGetCachFileListCommand(this);
		fileCmd.setDirname(directory);
		fileCmd.executeCommand();
		success = fileCmd.executedSuccessfully();
		if (!success) {
			addErrorMessage("[FAIL]: retrieveing file list for directory '" + directory + "' failed!");
			return false;
		}

		// check if local directory has to be created
		if (! folder.isDirectory()) {
			// create directory
			success = folder.mkdirs();
			if (!success) {
				// Directory creation failed
				addErrorMessage("[FAIL]: creating local directory '" + directory + "' failed!");
				return false;
			}
		}

		// get list if filenames
		List<String> filenames = fileCmd.getFilenames();

		// If the list contains files, download the files
		if ((filenames != null) && (filenames.size() != 0)) {

			// for each file: download file
			for (int idx = 0; idx < filenames.size(); idx++) {
				String filename = directory + "/" + filenames.get(idx);
				success = success && downloadFile(filename);
				setProgress(IRttMbtProgressBar.Tasks.Global, (idx * 100)/filenames.size());
				// check if task has been canceled
				if (isCurrentTaskCanceled()) {
					return false;
				}
			}
		}

		// get list of sub directory names
		jsonGetCachDirectoryListCommand dirCmd = new jsonGetCachDirectoryListCommand(this);
		dirCmd.setDirname(directory);
		dirCmd.executeCommand();
		success = dirCmd.executedSuccessfully();
		if (!success) {
			addErrorMessage("[FAIL]: retrieveing file list for directory '" + directory + "' failed!");
			return false;
		}
		List<String> dirnames = dirCmd.getDirnames();

		// If no files are in the directory: do nothing
		if ((dirnames == null) || (dirnames.size() == 0)) {
			return success;
		}

		// download all sub directories
		for (int idx = 0; idx < dirnames.size(); idx++) {
			String dirname = directory + File.separator + dirnames.get(idx);
			success = success && downloadDirectory(dirname);
			// check if task has been canceled
			if (isCurrentTaskCanceled()) {
				return false;
			}
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
	
	public Boolean deleteLocalDirectory(File dir, Boolean deleteContentOnly) {
		if (dir == null) { return false; }
		if (!dir.exists()) { return true; }
		if (!dir.isDirectory()) { return false; }

		String[] entries = dir.list();
		if (entries == null) { return dir.delete(); }

		for (int i = 0; i < entries.length; i++) {
			File entry = new File(dir, entries[i]);
			if (entry.isDirectory()) {
				if (!deleteLocalDirectory(entry, deleteContentOnly))
					return false;
			} else {
				if (!entry.delete())
					return false;
			}
			if (isCurrentTaskCanceled()) {
				return false;
			}
		}
		if (!deleteContentOnly) {
			return dir.delete();
		}
		return true;
	}

	public Boolean deleteRemoteFileOrDir(String filename) {
		Boolean success = true;

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
		setRttProjectName(project);

		// check if local project directory exists
		File folder = new File(getRttProjectPath());
		if (folder.isFile()) {
			addErrorMessage("'" + project + "' already exists and is a file!");
			return false;
		}

		// create project structure
		if (! folder.exists()) {
			// create directory
			success = folder.mkdirs();
			if (!success) {
				// Directory creation failed
				addErrorMessage("creating local directory '" + project + "' failed!");
				return false;
			}
		}

		// check for existing configuration project.rtp
		File projectConf = new File(folder, "project.rtp");
		if (!projectConf.exists()) {
			// extract project template
			String templatesDir = getWorkspacePath() + File.separator + "templates";
			File templates = new File(templatesDir);
			if (!templates.isDirectory()) {
				addErrorMessage("local templates directory " + templatesDir + " does not exist!");
				return false;
			}
			File archive = new File(templates, "_Project_compass.zip");
			if (getVerboseLogging()) {
				addLogMessage("unzipping archive '" + archive.getAbsolutePath() + "' into directory '" + getRttProjectPath() + "'");
			}
			success = unzipArchive(archive.getPath(), getRttProjectPath());
			if (!success) {
				// extracting project template failed
				addErrorMessage("creating project structure failed!");
				return false;
			}
		}

		return success;
	}
	
	public Boolean cleanProject(String project) {
		Boolean success = true;

		// remove local files in /model directory (except model_dump.xml)
		setSubTaskName("cleanup local project files");
		String modelDirName = getRttProjectPath() + File.separator + "model" + File.separator;
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
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// clean all test procedures in the test execution context
		setSubTaskName("cleanup local test procedures");
		String dirname = getRttProjectPath() + File.separator + getRttMbtTestProcFolderName();
		File dir = new File(dirname);
		if ((dir.exists()) && (dir.isDirectory())) {
			String[] entries = dir.list();
			if (entries != null) {
				for (int i = 0; i < entries.length; i++) {
					if (!isRtt6TestProcedure(dirname + File.separator + entries[i])) {
						continue;
					}
					if (getVerboseLogging()) {
						addLogMessage("clean Test Procedure " + getRttMbtTestProcFolderName() + File.separator + entries[i]);
					}
					cleanTestProcedure(getRttMbtTestProcFolderName() + File.separator + entries[i]);
					if (isCurrentTaskCanceled()) {
						return false;
					}
				}
			}
		}
		addCompletedTaskItems(1);

		// clean all test procedures in the test generation context
		setSubTaskName("cleanup local test procedure generation context directories");
		dirname = getRttProjectPath() + File.separator + getRttMbtTProcGenCtxFolderName();
		dir = new File(dirname);
		if ((dir.exists()) && (dir.isDirectory())) {
			String[] entries = dir.list();
			if (entries != null) {
				for (int i = 0; i < entries.length; i++) {
					if (!isRttMbtTestProcedure(dirname + File.separator + entries[i])) {
						continue;
					}
					if (getVerboseLogging()) {
						addLogMessage("clean Test Procedure Generation Context " + entries[i]);
					}
					cleanTestProcedureGenerationContext(entries[i]);
					if (isCurrentTaskCanceled()) {
						return false;
					}
				}
			}
		}
		addCompletedTaskItems(1);
		
		// remove working area for this project on the server
		setSubTaskName("remove project directory on the server");
		success = deleteRemoteFileOrDir(getRttProjectPath());
		addCompletedTaskItems(1);

		return success;
	}
	
	public Boolean livelockCheckModelFile(File model) {
		if (model == null) {
			return false;
		}
		
		// calculate model parameters
		setSubTaskName("preparing command");
		String prefix = getWorkspaceProjectPrefix();
		String modelName;
		if (prefix == null) {
			modelName = getRttProjectName() + "_model";
		} else {
			prefix = prefix.replaceAll(fileSeparatorPattern(), "_");
			modelName = prefix + "_" + getRttProjectName() + "_model";
		}
		String modelVersion = "1.0";
		String modelFile = model.getAbsolutePath();
		int pos = modelFile.lastIndexOf(File.separator);
		String modelReport = modelFile.substring(0,pos + 1)  + "LivelockReport.log";

		// remove local (and old) log files
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		deleteLocalFile(new File(modelReport));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// store model
		setSubTaskName("uploading source files");
		jsonStoreModelCommand storeModel= new jsonStoreModelCommand(this);
		storeModel.setModelName(modelName);
		storeModel.setModelId(modelVersion);
		storeModel.setModelFile(modelFile);
		storeModel.executeCommand();
		if (!storeModel.executedSuccessfully()) {
			addErrorMessage("[FAIL]: unable to store model file '" + modelFile + "' on RTT-MBT server!");
			// download debug information:
			// - rtt-mbt-tms-execution.out
			// - rtt-mbt-tms-execution.err
			String dirname = getRttProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// print error messages from output file
			String errorsFileName = getRttProjectPath() +  File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
			return false;
		} else {
			addLogMessage("[PASS]: model file '" + modelFile + "' stored on RTT-MBT server!");
		}
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// perform live lock check
		setSubTaskName("perform livelock check");
		jsonCheckModelCommand checkModel = new jsonCheckModelCommand(this);
		checkModel.setGuiPorts(true);
		checkModel.setReportName(modelReport);
		checkModel.setModelName(modelName);
		checkModel.setModelId(modelVersion);
		checkModel.executeCommand();
		if (!checkModel.executedSuccessfully()) {
			System.err.println("[FAIL]: livelock check of model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			showLogMessagesFromFile(modelReport);
			return false;
		}
		showLogMessagesFromFile(modelReport);
		addCompletedTaskItems(1);
		
		return true;
	}
	
	public Boolean initProject(String modelName, String modelVersion, String modelFileName) {
		Boolean success = true;

		// check if project has been created / selected
		setSubTaskName("preparing command");
		if (getRttProjectName() == null) {
			System.err.println("[FAIL]: no project created / selected, yet!");
			addCompletedTaskItems(1);
			return false;
		}
		// remove local model directory
		String modelDirName = getRttProjectPath() + File.separator
				+ "model";
		File modeldir = new File(modelDirName);
		deleteLocalDirectory(modeldir, false);
		// remove local model directory
		String P1DirName = getRttProjectPath() + File.separator
				+ "model";
		File p1dir = new File(P1DirName);
		deleteLocalDirectory(p1dir, false);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// remove working area on the server
		setSubTaskName("remove project directory on the server");
		success = deleteRemoteFileOrDir(getRttProjectPath());
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// copy model to <projectroot>/model/model_dump.xml
		setSubTaskName("store model locally");
		File projectRoot = new File(getRttProjectPath());
		try {
			if (!projectRoot.isDirectory()) {
				addErrorMessage("project directory '" + getRttProjectPath() + "' does not exist!");
				return false;
			}
			File modelDir = new File(projectRoot, "model");
			if (!modelDir.exists()) {
				modelDir.mkdir();
			}
			if (!modelDir.isDirectory()) {
				addErrorMessage("model directory '" + modelDir.getPath() + "' does not exist!");
				return false;
			}
			File inputModel = new File(modelFileName);
			if (!inputModel.isFile()) {
				addErrorMessage("model file '" + modelFileName + "' does not exist!");
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
			addErrorMessage("unable to copy model file '" + modelFileName + "' to project '" + getRttProjectName() + "'!");
		}
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// send model_dump.xml to file cache
		// this is actually needed for test generation command
		setSubTaskName("upload model file");
		modelDirName = getRttProjectPath() + File.separator + "model" + File.separator;
		if (uploadFile(modelDirName + "model_dump.xml")) {
			System.out.println("[PASS]: upload model file '" + modelDirName + "model_dump.xml" + "'!");
		} else {
			addErrorMessage("unable to upload model file '" + modelDirName + "model_dump.xml" + "'!");
		}
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// perform store model command
		// this is needed for livelock-check, conftool and sigmaptool
		setSubTaskName("store model");
		jsonStoreModelCommand storeModel= new jsonStoreModelCommand(this);
		storeModel.setModelName(modelName);
		storeModel.setModelId(modelVersion);
		storeModel.setModelFile(modelFileName);
		storeModel.executeCommand();
		if (!storeModel.executedSuccessfully()) {
			addErrorMessage("unable to store model file '" + modelFileName + "' on RTT-MBT server!");
			return false;
		}
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// unpack _P1.zip
		setSubTaskName("extract test procedure generation context template");
		String templatesDir = getFilesystemWorkspacePath() + File.separator + "templates";
		File templates = new File(templatesDir);
		downloadDirectory(templatesDir);
		File archive = new File(templates, "_P1_compass.zip");
		if (!archive.isFile()) {
			addErrorMessage("'" + archive.getAbsolutePath() + "' does not exist or is not a regular file!");
			return false;
		}
		File testProcs = new File(projectRoot, getRttMbtTProcGenCtxFolderName());
		if (!testProcs.exists()) {
			testProcs.mkdir();
		}
		if (!testProcs.isDirectory()) {
			addErrorMessage("'" + testProcs.toString() + "' is not a directory!");
			return false;
		}
		success = unzipArchive(archive.getPath(), testProcs.getPath());
		if (!success) {
			addErrorMessage("unpacking of template test procedure _P1 failed!");
			return false;
		}
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// perform livelock check
		setSubTaskName("perform livelock check");
		System.out.println("performing livelock check of the model (with enabled GUI ports)...");
		jsonCheckModelCommand checkModel = new jsonCheckModelCommand(this);
		checkModel.setGuiPorts(true);
		checkModel.setModelName(modelName);
		checkModel.setModelId(modelVersion);
		checkModel.executeCommand();
		if (!checkModel.executedSuccessfully()) {
			addErrorMessage("livelock check of model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			return false;
		}
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// perform conftool command
		setSubTaskName("generating initial configuration");
		System.out.println("creating initial configuration for abstract test procedure _P1...");
		jsonConftoolCommand config = new jsonConftoolCommand(this);
		config.setModelName(modelName);
		config.setModelId(modelVersion);
		config.setTestProcName("_P1");
		config.executeCommand();
		if (!config.executedSuccessfully()) {
			addErrorMessage("creating empty configuration for model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			return false;
		}
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// perform sigmaptool command
		setSubTaskName("generating initial signal map");
		System.out.println("creating initial signal map for abstract test procedure _P1...");
		jsonSigmaptoolCommand sigmap = new jsonSigmaptoolCommand(this);
		sigmap.setModelName(modelName);
		sigmap.setModelId(modelVersion);
		sigmap.setTestProcName("_P1");
		sigmap.executeCommand();
		if (!sigmap.executedSuccessfully()) {
			addErrorMessage("creating initial signal map for model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			return false;
		}
		addCompletedTaskItems(1);

		// return result
		return success;
	}

	private Boolean unzipArchive(String archiveName, String targetDirectory) {
		Boolean success = true;
		System.out.println("extracting archive '" + archiveName + "' to directory '" + targetDirectory + "'");
		try {
			File folder = new File(targetDirectory);
			if (!folder.exists()) {
				addErrorMessage("target directory '" + targetDirectory + "' does not exist!");
				return false;
			}
			if (!folder.isDirectory()) {
				addErrorMessage("'" + targetDirectory + "' is not a directory!");
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
		    	// check for cancel request
				if (isCurrentTaskCanceled()) {
			    	stream.close();
					return false;
				}
	        }
	    	stream.close();
		} catch (FileNotFoundException e) {
			addErrorMessage("template archive '" + archiveName + "' does not exist!");
			return false;
		} catch (IOException e) {
			addErrorMessage("unable to extract files from template archive '_Project_compass.zip'!");
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
	
	public Boolean cleanTestProcedureGenerationContext(String abstractTestProc) {
		Boolean success = true;

		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// remove local files
		setSubTaskName("cleanup local test procedure generation context");
		// /Testprocedure/<testproc>/model/*
		// /Testprocedure/<testproc>/log/*
		// /Testprocedure/<testproc>/testdata/*
		String dirname = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator;
		deleteLocalDirectory(new File(dirname + File.separator + "model"), true);
		deleteLocalDirectory(new File(dirname + File.separator + "log"), true);
		deleteLocalDirectory(new File(dirname + File.separator + "testdata"), true);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		setSubTaskName("delete test procedure generation context directory on the server");
		deleteRemoteFileOrDir(getRttProjectPath() + File.separator + getRttMbtTProcGenCtxFolderName() + File.separator + abstractTestProc);
		addCompletedTaskItems(1);

		// return result
		return success;
	}

	public Boolean generateTestProcedure(String abstractTestProc) {
		Boolean success = true;

		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		File logs = new File(getRttProjectPath() + File.separator + "logs");
		if (!logs.isDirectory()) {
			logs.mkdirs();
		}
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "logs" + File.separator + "rtt-mbt-gen-tcgen-errors.log"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "logs" + File.separator + "rtt-mbt-gen-tcgen-generation.log"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "logs" + File.separator + "rtt-mbt-gen-tcgen-postprocessing.log"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "logs" + File.separator + "rtt-mbt-gen-tcgen-preprocessing.log"));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// push necessary files to cache:
		setSubTaskName("uploading source files");
		if (getVerboseLogging()) {
			addLogMessage("pushing files to RTT-MBT server...");
		}
		// cache/<user-id>/<project-name>/model/
		// - model_dump.xml
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String modelDirName = getRttProjectPath() + File.separator + "model" + File.separator;
		uploadFile(modelDirName + "model_dump.xml");
		uploadFile(modelDirName + "configuration.csv");
		uploadFile(modelDirName + "signalmap.csv");
		uploadFile(modelDirName + "advanced.conf");
		uploadFile(modelDirName + "addgoals.conf");
		uploadFile(modelDirName + "addgoalsordered.conf");
		if (isCurrentTaskCanceled()) {
			return false;
		}
		// cache/<user-id>/<project-name>/<abstract-testproc>/conf
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String confDirName = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" +  File.separator;
		uploadFile(confDirName + "configuration.csv");
		uploadFile(confDirName + "signalmap.csv");
		uploadFile(confDirName + "advanced.conf");
		uploadFile(confDirName + "addgoals.conf");
		uploadFile(confDirName + "addgoalsordered.conf");
		// cache/<user-id>/<project-name>/TMPL
		confDirName = getRttProjectPath() + File.separator + "TMPL";
		uploadDirectory(confDirName, true);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}
		
		// generate-test-command
		setSubTaskName("executing server task");
		System.out.println("generating concrete test procedure " + abstractTestProc + "...");
		jsonGenerateTestCommand cmd = new jsonGenerateTestCommand(this);
		cmd.setGuiPorts(true);
		cmd.setTestProcName("TestProcedures/" + abstractTestProc);
		// read advanced.conf
		RttMbtAdvConfParser advConf = new RttMbtAdvConfParser();
		confDirName = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" +  File.separator;
		if (advConf.readAdvancedConfig(confDirName + "advanced.conf")) {
			cmd.setMaximizeModelCoverage(advConf.getMM());
			cmd.setAbstractInterpreter(advConf.getAI());
		}
		if (getVerboseLogging()) {
			addLogMessage("starting test generation...");
		}
		long startTime = System.nanoTime();
		cmd.executeCommand();
		long endTime = System.nanoTime();
		if (getVerboseLogging()) {
			long secs = ((endTime - startTime)/1000000000);
			long msecs = ((endTime - startTime)/1000000);
			addLogMessage("Generation took " + secs + "." + msecs + "s.");
		}		
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// download result
		setSubTaskName("downloading relevant files");
		String dirname;
		String errorsFileName;
		if (getVerboseLogging()) {
			addLogMessage("retrieving generated files from RTT-MBT server...");
		}
		if (!cmd.executedSuccessfully()) {
			System.err.println("[FAIL]: generatig RTT_TestProcedures/" + abstractTestProc + " failed!");
			// download debugging data to local directory
			// - rtt-mbt-tms-execution.out
			// - rtt-mbt-tms-execution.err
			dirname = getRttProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// print error messages from output file
			errorsFileName = getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
			// - model/error.log
			// - model/genertion.log
			dirname = getRttProjectPath() + File.separator
					+ "model" + File.separator;
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
			// - TestProcedures/<TP>/generation.log
			// - TestProcedures/<TP>/error.log
			dirname = getRttProjectPath() + File.separator
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator;
			downloadFile(dirname + "generation.log");
			downloadFile(dirname + "errors.log");
			// print error messages from errors.log file
			errorsFileName = getRttProjectPath() +  File.separator 
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator + "errors.log";
			showErrorMessagesFromFile(errorsFileName);
			// - configuration.csv.bak
			dirname = getRttProjectPath() + File.separator
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "conf" + File.separator;
			downloadFile(dirname + "configuration.csv.bak");
			return false;
		}
		if (isCurrentTaskCanceled()) {
			return false;
		}
		// - rtt-mbt-tms-execution.out
		// - rtt-mbt-tms-execution.err
		dirname = getRttProjectPath() + File.separator;
		downloadFile(dirname + "rtt-mbt-tms-execution.err");
		downloadFile(dirname + "rtt-mbt-tms-execution.out");
		// print error messages from output file
		errorsFileName = getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err";
		showErrorMessagesFromFile(errorsFileName);
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
		dirname = getRttProjectPath() + File.separator
		+ "model" + File.separator;
		downloadFile(dirname + "symbols.log");
		downloadFile(dirname + "testcases.csv");
		downloadFile(dirname + "testcases.db");
		downloadFile(dirname + "tc2req.csv");
		downloadFile(dirname + "req2tc.csv");
		downloadFile(dirname + "overall_coverage.csv");
		downloadFile(dirname + "uncovered_testcases.csv");
		downloadFile(dirname + "unreachable_testcases.csv");
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// from cache/<user-id>/<project-name>/<testproc>/conf
		// - configuration.csv
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" + File.separator;
		downloadFile(dirname + "configuration.csv");
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// from cache/<user-id>/<project-name>/<testproc>/log
		// - addgoalcoverage.csv
		// - covered_testcases.csv
		// - focus_points_to_addgoals.conf
		// - model/error.log
		// - model/genertion.log
		dirname = getRttProjectPath() + File.separator
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
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// from cache/<user-id>/<project-name>/<testproc>/model
		// - signals.dat
		// - signals.json
		// - *.pdf
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "model";
		downloadDirectory(dirname);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// from cache/<user-id>/<project-name>/<testproc>/testdata
		// - signals.dat
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "testdata";
		downloadDirectory(dirname);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// download concrete test procedure from cache
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTestProcFolderName() + File.separator
				+ abstractTestProc + File.separator;
		downloadDirectory(dirname + "conf");
		downloadDirectory(dirname + "inc");
		downloadDirectory(dirname + "specs");
		if (getExtraFiles()) {
			dirname = getRttProjectPath() + File.separator;
			downloadDirectory(dirname + "logs");
		}
		addCompletedTaskItems(1);

		return success;
	}
	
	public Boolean replayTestProcedure(String abstractTestProc) {
		Boolean success = true;

		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		File logs = new File(getRttProjectPath() + File.separator + "logs");
		if (!logs.isDirectory()) {
			logs.mkdirs();
		}
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "logs" + File.separator + "rtt-mbt-replay-tcgen-errors.log"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "logs" + File.separator + "rtt-mbt-replay-tcgen-generation.log"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "logs" + File.separator + "rtt-mbt-replay-tcgen-postprocessing.log"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "logs" + File.separator + "rtt-mbt-replay-tcgen-preprocessing.log"));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// push necessary files to cache:
		setSubTaskName("uploading files");
		// cache/<user-id>/<project-name>/model/
		// - model_dump.xml
		// - configuration.csv
		// - signalmap.csv
		// - addgoals.conf
		// - addgoalsordered.conf
		String modelDirName = getRttProjectPath() + File.separator + "model" + File.separator;
		uploadFile(modelDirName + "model_dump.xml");
		uploadFile(modelDirName + "configuration.csv");
		uploadFile(modelDirName + "signalmap.csv");
		uploadFile(modelDirName + "addgoals.conf");
		uploadFile(modelDirName + "addgoalsordered.conf");
		if (isCurrentTaskCanceled()) {
			return false;
		}
		// cache/<user-id>/<project-name>/<abstract-testproc>/conf
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String confDirName = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" +  File.separator;
		uploadFile(confDirName + "configuration.csv");
		uploadFile(confDirName + "signalmap.csv");
		uploadFile(confDirName + "advanced.conf");
		uploadFile(confDirName + "addgoals.conf");
		uploadFile(confDirName + "addgoalsordered.conf");
		if (isCurrentTaskCanceled()) {
			return false;
		}
		// cache/<user-id>/<project-name>/<abstract-testproc>/model
		// covered_testcases.csv
		String localModelDirName = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "model" +  File.separator;
		uploadFile(localModelDirName + "signals.dat");		
		uploadFile(localModelDirName + "signals.json");		
		// /RTT_Testprocedure/<testproc>/testdata/
		String dirName = getRttProjectPath() + File.separator
				+ getRttMbtTestProcFolderName() + File.separator
				+ abstractTestProc + File.separator;
		uploadDirectory(dirName + "testdata", false);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// replay-command
		setSubTaskName("executing server task");
		System.out.println("replay test execution " + abstractTestProc + "...");
		jsonReplayTestCommand cmd = new jsonReplayTestCommand(this);
		cmd.setGuiPorts(true);
		cmd.setTestProcName("TestProcedures/" + abstractTestProc);
		cmd.executeCommand();
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// retrieve results
		setSubTaskName("downloading replay result");
		if (!cmd.executedSuccessfully()) {
			System.err.println("[FAIL]: replay of " + abstractTestProc + " failed!");
			// download debugging data to local directory
			// - rtt-mbt-tms-execution.out
			// - rtt-mbt-tms-execution.err
			String dirname = getRttProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// print error messages from output file
			String errorsFileName = getRttProjectPath() +  File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
			// - model/error.log
			// - model/genertion.log
			dirname = getRttProjectPath() + File.separator
					+ "model" + File.separator;
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
			// - TestProcedures<TP>/log/generation.log
			// - TestProcedures<TP>/log/error.log
			dirname = getRttProjectPath() + File.separator
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator;
			downloadFile(dirname + "generation.log");
			downloadFile(dirname + "errors.log");
			// print error messages from errors.log file
			errorsFileName = getRttProjectPath() +  File.separator 
					+ getRttMbtTProcGenCtxFolderName() + File.separator
					+ abstractTestProc + File.separator
					+ "log" + File.separator + "errors.log";
			showErrorMessagesFromFile(errorsFileName);
			return false;
		}
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// download generated files to local directory:
		String dirname;
		// Testprocedures/<TP>/log/configuration.csv
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" + File.separator;;
		downloadFile(dirname + "configuration.csv");
		// Testprocedures/<TP>/log/covered_testcases.csv
		// Testprocedures/<TP>/log/missed_goals.csv
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "log" + File.separator;
		downloadFile(dirname + "covered_testcases.csv");
		downloadFile(dirname + "missed_goals.csv");
		// Testprocedures/<TP>/model/signals.json
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "model" + File.separator;
		downloadFile(dirname + "signals.dat");
		downloadFile(dirname + "signals.json");
		// RTT_Testprocedures/<TP>/testdata/replay.log
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTestProcFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "testdata" + File.separator;
		downloadFile(dirname + "replay.log");
		// - model/error.log
		// - model/genertion.log
		if (getExtraFiles()) {
		dirname = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "log" + File.separator;
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
		}
		addCompletedTaskItems(1);
		
		return success;
	}

	public Boolean generateSimulation(String abstractTestProc) {
		Boolean success = true;

		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// push necessary files to cache:
		setSubTaskName("uploading source files");
		if (getVerboseLogging()) {
			addLogMessage("pushing files to RTT-MBT server...");
		}
		// cache/<user-id>/<project-name>/model/
		// - model_dump.xml
		// - configuration.csv
		// - signalmap.csv
		// - addgoals.conf
		// - addgoalsordered.conf
		String modelDirName = getRttProjectPath() + File.separator + "model" + File.separator;
		uploadFile(modelDirName + "model_dump.xml");
		uploadFile(modelDirName + "configuration.csv");
		uploadFile(modelDirName + "signalmap.csv");
		uploadFile(modelDirName + "addgoals.conf");
		uploadFile(modelDirName + "addgoalsordered.conf");
		if (isCurrentTaskCanceled()) {
			return false;
		}
		// cache/<user-id>/<project-name>/TestProcedures/<TP>/conf/
		// - configuration.csv
		// - signalmap.csv
		// - advanced.conf
		// - addgoals.conf
		// - addgoalsordered.conf
		String confDirName = getRttProjectPath() + File.separator
				+ getRttMbtTProcGenCtxFolderName() + File.separator
				+ abstractTestProc + File.separator
				+ "conf" +  File.separator;
		uploadFile(confDirName + "configuration.csv");
		uploadFile(confDirName + "signalmap.csv");
		uploadFile(confDirName + "advanced.conf");
		uploadFile(confDirName + "addgoals.conf");
		uploadFile(confDirName + "addgoalsordered.conf");
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}
		
		// generate-simulation-command
		setSubTaskName("executing server task");
		System.out.println("generating simulation from " + abstractTestProc + "...");
		jsonGenerateSimulationCommand cmd = new jsonGenerateSimulationCommand(this);
		cmd.setTestProcName("TestProcedures/" + abstractTestProc);
		cmd.executeCommand();
		if (getVerboseLogging()) {
			addLogMessage("retrieving generated files from RTT-MBT server...");
		}
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		setSubTaskName("downloading relevant files");
		if (!cmd.executedSuccessfully()) {
			success = false;
			System.err.println("[FAIL]: generatig Simulation " + abstractTestProc + " failed!");
			// download debugging data to local directory
			// - rtt-mbt-tms-execution.out
			// - rtt-mbt-tms-execution.err
			String dirname = getRttProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// print error messages from output file
			String errorsFileName = getRttProjectPath() +  File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
			// - model/error.log
			// - model/genertion.log
			dirname = getRttProjectPath() + File.separator
					+ "model" + File.separator;
			downloadFile(dirname + "errors.log");
			downloadFile(dirname + "generation.log");
			// - generation.log
			// - error.log
			dirname = getRttProjectPath() + File.separator
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
			dirName = getRttProjectPath() + File.separator
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
			dirName = getRttProjectPath() + File.separator
					+ getRttMbtTestProcFolderName() + File.separator;
			downloadDirectory(dirName + "conf");
			downloadDirectory(dirName + "inc");
			downloadDirectory(dirName + "specs");
			downloadDirectory(dirName + "stubs");
		}
		addCompletedTaskItems(1);
		
		return success;
	}

	public Boolean cleanTestProcedure(String concreteTestProcPath) {
		Boolean success = true;

		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// push necessary files to cache:
		setSubTaskName("uploading source files");
		// /project.rtp
		// /<testproc_path>/conf/
		String fileName = getRttProjectPath() + File.separator;
		uploadFile(fileName + "project.rtp");
		String dirName = getRttProjectPath() + File.separator
				+ concreteTestProcPath + File.separator;
		uploadDirectory(dirName + "conf", false);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// clean-test-command
		setSubTaskName("executing server task");
		jsonCleanTestCommand cmd = new jsonCleanTestCommand(this);
		String mappedTestProcPath = assertRttMbtExecutionContextMapping(concreteTestProcPath);
		System.out.println("cleanup concrete test procedure " + mappedTestProcPath + "...");
		cmd.setTestProcName(mappedTestProcPath);
		cmd.executeCommand();
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// check return value
		setSubTaskName("downloading relevant files");
		if (!cmd.executedSuccessfully()) {
			// set result to false
			success = false;
			System.err.println("[FAIL]: cleanup of " + mappedTestProcPath + " failed!");

			// download debug information:
			// - rtt-mbt-tms.out
			// - rtt-mbt-tms.err
			String dirname = getRttProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// print error messages from output file
			String errorsFileName = getRttProjectPath() +  File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
		} else {
			// remove local files
			// /stubsrc/
			// <testproc_path>/src/
			// <testproc_path>/stubsrc/
			// <testproc_path>/testdata/
			String dirname = getRttProjectPath() + File.separator;
			deleteLocalDirectory(new File(dirname + File.separator + "stubsrc"), false);
			dirname = getRttProjectPath() + File.separator
					+ concreteTestProcPath + File.separator;
			deleteLocalDirectory(new File(dirname + File.separator + "src"), false);
			deleteLocalDirectory(new File(dirname + File.separator + "stubsrc"), false);
			deleteLocalDirectory(new File(dirname + File.separator + "testdata"), false);
		}
		addCompletedTaskItems(1);

		// return result
		return success;
	}
	
	public Boolean compileTestProcedure(String concreteTestProcPath) {
		Boolean success = true;

		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// push necessary files to cache:
		setSubTaskName("uploading source files");
		// /project.rtp
		String fileName = getRttProjectPath() + File.separator;
		uploadFile(fileName + "project.rtp");
		uploadRttTestProcSources(concreteTestProcPath);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}
		
		// compile-test-command
		setSubTaskName("executing server task");
		String mappedTestProcPath = assertRttMbtExecutionContextMapping(concreteTestProcPath);
		System.out.println("compiling concrete test procedure " + mappedTestProcPath + "...");
		jsonCompileTestCommand cmd = new jsonCompileTestCommand(this);
		cmd.setTestProcName(mappedTestProcPath);
		cmd.executeCommand();
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// download result
		setSubTaskName("downloading relevant files");
		if (!cmd.executedSuccessfully()) {
			success = false;
			System.err.println("[FAIL]: compiling " + mappedTestProcPath + " failed!");
			// download debug information:
			// - rtt-mbt-tms.out
			// - rtt-mbt-tms.err
			String dirname = getRttProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// print error messages from output file
			String errorsFileName = getRttProjectPath() +  File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
		} else {
			// download generated files (not src, stubsrc, etc.)
			String dirName = getRttProjectPath() + File.separator
					+ concreteTestProcPath + File.separator
					+ "src" + File.separator;
			// create local target directory (src) if it does not exist
			File src = new File(dirName);
			if (!src.exists()) {
				System.out.println("creating directory '" + dirName + "'");
				if (!src.mkdir()) {
					System.err.println("creating directory '" + dirName + "' failed!");
				}
			}
			downloadFile(dirName + "used.conf");
			downloadFile(dirName + "used.rtp");
			downloadFile(dirName + "rtt-compile-test.timestamp");
		}
		addCompletedTaskItems(1);

		return success;
	}
	
	public Boolean runTestProcedure(String concreteTestProcPath) {
		Boolean success = true;

		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		deleteLocalDirectory(new File(getRttProjectPath() + File.separator +
				                      concreteTestProcPath + File.separator + "testdata"), true);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// push necessary files to cache:
		setSubTaskName("uploading source files");
		String dirName;
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// run-test-command
		setSubTaskName("executing server task");
		String mappedTestProcPath = assertRttMbtExecutionContextMapping(concreteTestProcPath);
		System.out.println("run concrete test procedure " + mappedTestProcPath + "...");
		jsonRunTestCommand cmd = new jsonRunTestCommand(this);
		cmd.setTestProcName(mappedTestProcPath);
		cmd.executeCommand();
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// download test result
		setSubTaskName("downloading relevant files");
		if (!cmd.executedSuccessfully()) {
			// set result to false
			success = false;
			System.err.println("[FAIL]: run " + mappedTestProcPath + " failed!");
			// download debug information:
			// - rtt-mbt-tms.out
			// - rtt-mbt-tms.err
			String dirname = getRttProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// print error messages from output file
			String errorsFileName = getRttProjectPath() +  File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
			// download debug information:
		} else {
			// download generated files
			dirName = getRttProjectPath() + File.separator
					+ concreteTestProcPath + File.separator
					+ "testdata" + File.separator;
			// create local target directory (testdata) if it does not exist
			File testdata = new File(dirName);
			if (!testdata.exists()) {
				System.out.println("creating directory '" + dirName + "'");
				if (!testdata.mkdir()) {
					System.err.println("creating directory '" + dirName + "' failed!");
				}
			}
			if (getExtraFiles()) {
				// - complete testdata directory
				downloadDirectory(dirName);
				// - rtt-mbt-tms-execution.out
				downloadFile(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out");
			} else {
				// only selected files from testdata directory
				downloadFile(dirName + "VERDICT.txt");
				downloadFile(dirName + "rtt-run-test.log");
			}
			showLogMessagesFromFile(dirName + "VERDICT.txt");
		}
		addCompletedTaskItems(1);

		// return result
		return success;
	}
	
	public Boolean docTestProcedure(String concreteTestProcPath) {
		Boolean success = true;

		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// push necessary files to cache:
		setSubTaskName("uploading source files");
		// /project.rtp
		// /TMPL
		String fileName = getRttProjectPath() + File.separator;
		uploadFile(fileName + "componentnames.txt");
		String dirName = getRttProjectPath() + File.separator;
		uploadDirectory(dirName + "TMPL", true);
		// /RTT_Testprocedure/conf/
		dirName = getRttProjectPath() + File.separator
				+ concreteTestProcPath + File.separator;
		uploadDirectory(dirName + "conf", false);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// doc-test-command
		setSubTaskName("executing server task");
		String mappedTestProcPath = assertRttMbtExecutionContextMapping(concreteTestProcPath);
		System.out.println("doc concrete test procedure " + mappedTestProcPath + "...");
		jsonDocTestCommand cmd = new jsonDocTestCommand(this);
		cmd.setTestProcName(mappedTestProcPath);
		cmd.executeCommand();
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// evaluate result and download files
		setSubTaskName("downloading relevant files");
		if (!cmd.executedSuccessfully()) {
			// set result to false
			success = false;
			System.err.println("[FAIL]: doc " + mappedTestProcPath + " failed!");
			// download debug information:
			// - rtt-mbt-tms.out
			// - rtt-mbt-tms.err
			String dirname = getRttProjectPath() + File.separator;
			downloadFile(dirname + "rtt-mbt-tms-execution.err");
			downloadFile(dirname + "rtt-mbt-tms-execution.out");
			// print error messages from output file
			String errorsFileName = getRttProjectPath() +  File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
			// download debug information:
		} else {
			// download generated files
			dirName = getRttProjectPath() + File.separator
					+ concreteTestProcPath + File.separator
					+ "testdata" + File.separator;
			String mappedTestProcPathDocPrefix = mappedTestProcPath.replaceAll(fileSeparatorPattern(), "_");
			downloadFile(dirName + mappedTestProcPathDocPrefix + "_testprocedure.pdf");
			downloadFile(dirName + mappedTestProcPathDocPrefix + "_testreport.pdf");
			downloadFile(dirName + "signals.dat");
			downloadFile(dirName + "ALL-TC-COV.csv");
			downloadFile(dirName + "map_tc2rts.csv");
		}
		addCompletedTaskItems(1);

		// return result
		return success;
	}

	public Boolean buildMakeTargetCommand(String makefilePath, String makeTarget) {
		Boolean success = true;

		// remove execution logs local to Makefile
		File makefile = new File(makefilePath);
		File makefileParent = makefile.getParentFile();
		String dirname = makefileParent.getAbsolutePath();
		deleteRemoteFileOrDir(dirname + File.separator + "rtt-mbt-tms-execution.err");
		deleteRemoteFileOrDir(dirname + File.separator + "rtt-mbt-tms-execution.out");
		deleteLocalFile(new File(dirname + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(dirname + File.separator + "rtt-mbt-tms-execution.out"));
		// remove local (and old) log files
		setSubTaskName("preparing command");
		deleteRemoteFileOrDir(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err");
		deleteRemoteFileOrDir(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out");
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.err"));
		deleteLocalFile(new File(getRttProjectPath() + File.separator + "rtt-mbt-tms-execution.out"));
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// push necessary files to cache:
		setSubTaskName("uploading source files");
		// <path-to-Makefile>
		if (!makefile.exists()) {
			addErrorMessage("Makefile " + makefilePath + " does not exist!");
		}
		if (!makefile.isFile()) {
			addErrorMessage("Makefile " + makefilePath + " is not a file!");
		}
		String sourceDir = makefile.getParent();
		uploadDirectory(sourceDir, true);
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// build-make-target-command
		setSubTaskName("building target " + makeTarget);
		System.out.println("build make target " + makeTarget + " from Makefile " + makefilePath + " using " + sutMakeTool);
		jsonBuildMakeTargetCommand cmd = new jsonBuildMakeTargetCommand(this);
		cmd.setMakefileName(makefilePath);
		cmd.setTarget(makeTarget);
		cmd.setMakeTool("/usr/bin/" + sutMakeTool);
		cmd.executeCommand();
		addCompletedTaskItems(1);
		if (isCurrentTaskCanceled()) {
			return false;
		}

		// download debug information:
		setSubTaskName("downloading relevant files");
		// - rtt-mbt-tms.out
		// - rtt-mbt-tms.err
		dirname = makefileParent.getAbsolutePath();
		downloadFile(dirname +  File.separator + "rtt-mbt-tms-execution.err");
		downloadFile(dirname +  File.separator + "rtt-mbt-tms-execution.out");

		if (!cmd.executedSuccessfully()) {
			// set result to false
			success = false;
			System.err.println("[FAIL]: build make target " + makeTarget + " from Makefile " + makefilePath + " using " + sutMakeTool + " failed!");
			// print error messages from output file
			dirname = makefileParent.getAbsolutePath();
			String errorsFileName = dirname +  File.separator + "rtt-mbt-tms-execution.err";
			showErrorMessagesFromFile(errorsFileName);
		} else {
			// download generated files
		}
		addCompletedTaskItems(1);

		// return result
		return success;
	}

	public Boolean createProjectDatabase() {
		Boolean success = true;
		
		// create-project-database-command
		System.out.println("create project database " + getProjectDatabaseName() + "...");
		jsonCreateProjectDatabaseCommand cmd = new jsonCreateProjectDatabaseCommand(this);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			// set result to false
			success = false;
			System.err.println("[FAIL]: create project database " + getProjectDatabaseName() + " failed!");
			// download debug information:
		} else {
			System.out.println("[PASS]: create project database " + getProjectDatabaseName());
		}

		return success;
	}
	
	// substitute local names for generation or execution context in the
	// path of <filename> with the ones from the RTT-MBT server
	public String substituteIllegalDbNameCharacters(String rawName) {
		String dbName = null;

		dbName = rawName;
		dbName = dbName.replaceAll("@", "_");
		dbName = dbName.replaceAll("/", "_");
		dbName = dbName.replaceAll(fileSeparatorPattern(), "_");
		dbName = dbName.replaceAll("\\.", "_");

		return dbName;
	}

	// substitute local names for generation or execution context in the
	// path of <filename> with the ones from the RTT-MBT server
	public String substituteContextFolderNamesLocal2Server(String filename) {
		String serverFilename = null;
		
		// check/substitute generation context
		String localGenerationContext = getRttProjectPath() + File.separator + getRttMbtTProcGenCtxFolderName();
		String serverGenerationContext = getRttProjectPath() + File.separator + "TestProcedures";
		if (filename.startsWith(localGenerationContext)) {
			serverFilename = serverGenerationContext + filename.substring(localGenerationContext.length());
		} else {
			serverFilename = filename;
		}
		
		String localExecutionContext = getRttProjectPath() + File.separator + getRttMbtTestProcFolderName();
		String serverExecutionContext = getRttProjectPath() + File.separator + "RTT_TestProcedures";
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
		String localGenerationContext = getRttProjectPath() + File.separator + getRttMbtTProcGenCtxFolderName();
		String serverGenerationContext = toUnixPath(getRttProjectPath()) + "/" + "TestProcedures";
		if (filename.startsWith(serverGenerationContext)) {
			serverFilename = localGenerationContext + filename.substring(serverGenerationContext.length());
		} else {
			serverFilename = filename;
		}
		
		String localExecutionContext = getRttProjectPath() + File.separator + getRttMbtTestProcFolderName();
		String serverExecutionContext = toUnixPath(getRttProjectPath()) + "/" + "RTT_TestProcedures";
		if (filename.startsWith(serverExecutionContext)) {
			serverFilename = localExecutionContext + filename.substring(serverExecutionContext.length());
		}

		return serverFilename;
	}
	
	// this function adds the actual file system path to the filename path.
	// the function also mapps the remote genertation context or execution context
	// folder names into the local folder names.
	// If the filename path does not start with the project name, nothing else replaced.
	// For a project, that exists inside the workspace, the filename-path
	// <project>/<path-to-file-or-dir>/<file-or-dir>
	// is replaced with
	// /<path-to-workspace>/<project>/<path-to-file-or-dir>/<file-or-dir>
	// Note:
	// for remotly located projects that are mapped inside an eclipse workspace, the path
	// <local-project-name>/<path-to-file-or-dir>/<file-or-dir>
	// is replaced with
	// /<path-to-remote-project>/<remote-project-name>/<path-to-file-or-dir>/<file-or-dir>
	// Note: Because client and server use different absolute paths for their workspaces,
	//       only the relative path inside the respective workspace is used in the
	//       communication between client and server.
	public String addLocalWorkspace(String filepath) {
		String localFilename = filepath;

		// check argument
		if (filepath == null) return filepath;
		System.out.println("addLocalWorkspace(" + filepath + ")");
		if ((localFilename.substring(1, 3)).compareTo(":\\") == 0) {
			localFilename = "/" + filepath.replace('\\', '/');
		}
		System.out.println("addLocalWorkspace(" + localFilename + ")");

		// remove prefix of the project in the current workspace
		if ((getWorkspaceProjectPrefix() != null) &&
			(localFilename.startsWith(getWorkspaceProjectPrefix()))) {
			localFilename = localFilename.substring(getWorkspaceProjectPrefix().length() + 1);
			System.out.println("remove workspace project prefix: " + localFilename);
		}

		// if the file or dir does not start with the project (local),
		// no more replacement is performed.
		if (!(localFilename.startsWith(getWorkspaceProjectName()))) {
			System.err.println("*** warning: the path does not start with the local workspace project");
			System.out.println("add workspace path: " + localFilename);
			return getFilesystemWorkspacePath() + localFilename;
		}

		// replace local workspace project name with
		// remote workspace project name
		localFilename = localFilename.replaceFirst(getWorkspaceProjectName(), getRttProjectName());
		System.out.println("replace project name: " + localFilename);

		// add workspace path
		// note: getFilesystemWorkspacePath() already ends in a slash.
		localFilename =  getFilesystemWorkspacePath() + localFilename;
		System.out.println("add workspace path: " + localFilename);

		// substitute context folder names
		localFilename = substituteContextFolderNamesServer2Local(localFilename);
		System.out.println("substitute context folder names: " + localFilename);

		// return new filename (with path)
		return localFilename;
	}

	// this function replaces the absolute path to the project in the filename-path with the
	// relative path inside the eclipse workspace. For a project, that exists inside the
	// workspace, the filename-path
	// /<path-to-workspace>/<project>/<path-to-file-or-dir>/<file-or-dir>
	// is replaced with
	// /<project>/<path-to-file-or-dir>/<file-or-dir>
	// Note:
	// for remotly located projects that are mapped inside an eclipse workspace, the path
	// /<path-to-remote-project>/<remote-project-name>/<path-to-file-or-dir>/<file-or-dir>
	// is replaced with
	// /<local-project-name>/<path-to-file-or-dir>/<file-or-dir>
	// Note: Because client and server use different absolute paths for their workspaces,
	//       only the relative path inside the respective workspace is used in the
	//       communication between client and server.
	public String removeLocalWorkspace(String filepath) {
		String serverFilename = null;

		// check argument
		if (filepath == null) return null;

		// check for windows path that has to be transfomed into unix style path
		System.out.println("removeLocalWorkspace(" + filepath + ")");
		String localFilename = filepath;
		if (filepath.substring(1, 3).compareTo(":\\") == 0) {
			localFilename = "/" + filepath.replace('\\', '/');
		}
		System.out.println("removeLocalWorkspace(" + localFilename + ")");

		// if the file or dir does not start with the workspace prefix,
		// no more replacement is performed.
		if (!(localFilename.startsWith(getFilesystemWorkspacePath()))) {
			System.err.println("*** warning: the file or dir does not start with the workspace path '" + getFilesystemWorkspacePath() + "'");
			return filepath;
		}

		// substitute context folder names
		serverFilename = substituteContextFolderNamesLocal2Server(localFilename);
		System.out.println("substitute context folder names: " + serverFilename);

		// remove workspace path (path in the file system to the project folder)
		String workspace = getFilesystemWorkspacePath() + File.separator;
		serverFilename = serverFilename.substring(workspace.length() - 1);
		System.out.println("remove workspace path: '" + getFilesystemWorkspacePath() + "': " + serverFilename);

		// replace local workspace project name with
		// remote workspace project name
		serverFilename = serverFilename.replaceFirst(getRttProjectName(), getWorkspaceProjectName());
		System.out.println("replace project names: " + serverFilename);

		// add prefix of the project in the current workspace
		if (getWorkspaceProjectPrefix() != null) {
			serverFilename = getWorkspaceProjectPrefix() + File.separator + serverFilename;
			System.out.println("add workspace project prefix: " + serverFilename);
		}

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

	// this function calculates the relative path inside the current RT-Tester Project
	// from an absolute filesystem path
	public String getPathInsideRttTestcontext(String selectedObjectFilesystemPath) {
		String relativePath;

		// check that the selected object is located
		// inside the current RT-Tester project
		if (selectedObjectFilesystemPath.startsWith(rttProjectPath)) {
			relativePath = selectedObjectFilesystemPath.substring(rttProjectPath.length() + 1);
		} else {
			relativePath = selectedObjectFilesystemPath;
		}

		return relativePath;
	}

	// this function maps a test procedure path from the local execution context
	// to the RTT-MBT build in path "RTT_TestProcedure" for execution contexts
	public String assertRttMbtExecutionContextMapping(String concreteTestProcPath) {
		if (concreteTestProcPath.startsWith(getRttMbtTestProcFolderName())) {
			return concreteTestProcPath.replaceFirst(getRttMbtTestProcFolderName(), "RTT_TestProcedures");
		} else {
			return concreteTestProcPath;
		}
	}

	// upload all conf, inc, spec, stubs folders on the path
	public void uploadRttTestProcSources(String testProcedurePath) {
		String dirName;
		
		// upload global conf, inc, spec, stubs folders
		dirName = getRttProjectPath() + File.separator;
		uploadDirectory(dirName + "conf", false);
		if (isCurrentTaskCanceled()) { return; }
		uploadDirectory(dirName + "inc", false);
		if (isCurrentTaskCanceled()) { return; }
		uploadDirectory(dirName + "specs", false);
		if (isCurrentTaskCanceled()) { return; }
		uploadDirectory(dirName + "stubs", false);
		if (isCurrentTaskCanceled()) { return; }

		// upload all conf, inc, spec, stubs folders on the path
		String currentDir = "";
		String remainingPath = testProcedurePath;
		int pos = testProcedurePath.indexOf(File.separator);
		while (pos != -1) {
			// upload conf, inc, spec, stubs folders
			currentDir = currentDir + File.separator + remainingPath.substring(0, pos);
			dirName = getRttProjectPath() + currentDir + File.separator;
			uploadDirectory(dirName + "conf", false);
			if (isCurrentTaskCanceled()) { return; }
			uploadDirectory(dirName + "inc", false);
			if (isCurrentTaskCanceled()) { return; }
			uploadDirectory(dirName + "specs", false);
			if (isCurrentTaskCanceled()) { return; }
			uploadDirectory(dirName + "stubs", false);
			if (isCurrentTaskCanceled()) { return; }
			
			// prepare next loop
			remainingPath = remainingPath.substring(pos + 1);
			pos = remainingPath.indexOf(File.separator);
		}

		// upload conf, inc, spec, stubs folders from test procedure
		dirName = getRttProjectPath() + File.separator + testProcedurePath + File.separator;
		uploadDirectory(dirName + "conf", false);
		if (isCurrentTaskCanceled()) { return; }
		uploadDirectory(dirName + "inc", false);
		if (isCurrentTaskCanceled()) { return; }
		uploadDirectory(dirName + "specs", false);
		if (isCurrentTaskCanceled()) { return; }
		uploadDirectory(dirName + "stubs", false);
		if (isCurrentTaskCanceled()) { return; }
	}

	// this static function checks if a given path leads to an RTT-MBT project
	public static String getAbsolutePathFromFileURI(URI uri) {
		if (uri == null) {
			return null;
		}
		String path = uri.toString();
		if (path.startsWith("file:")) {
			return path.substring(5);
		} else {
			return null;
		}
	}

	// this static function checks if a given path leads to an RT-Tester project
	public static Boolean isRttMbtProject(String path) {
		if (!isRttProject(path)) {
			return false;
		}

    	// search child files/folders for RT-Tester criteria
		File folder = new File(path);
		File[] files = folder.listFiles();
		Boolean has_model_subdir = false;
		for (int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if (files[i].isDirectory()) {
				// search for TMPL sub directory
				if (name.compareTo("model") == 0) {
					has_model_subdir = true;
				}
			} else {
				// search for RTT-MBT specific files file
			}
			if (has_model_subdir) {
				return true;
			}
		}

		return false;
	}
	
	// this static function checks if a given path leads to an RTT-MBT project
	public static Boolean isRttProject(String path) {
		if (path == null) {
			System.err.println("*** error: isRttMbtProject(null)");
			return false;
		}
		
		File folder = new File(path);
		if (!folder.isDirectory()) {
			return false;
		}

    	// check if directory is empty
		File[] files = folder.listFiles();
		if (files == null) {
			return false;
		}

    	// search child files/folders for RT-Tester criteria
		Boolean has_rtp_file = false;
		Boolean has_TMPL_subdir = false;
		for (int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if (files[i].isDirectory()) {
				// search for TMPL sub directory
				if (name.compareTo("TMPL") == 0) {
					has_TMPL_subdir = true;
				}
			} else {
				// search for *.rtp file
				if (name.substring(name.length() - 4,name.length()).compareTo(".rtp") == 0) {
					has_rtp_file = true;
				}
			}
			if (has_rtp_file && has_TMPL_subdir) {
				return true;
			}
		}

		return false;
	}
	
	// this static function checks if a given path leads to an RT-Tester 6 test procedure
	// the checks are perfomed according to the criteria in 
	// Mantis PR 7720 "Adjust the project scanner library for MBT"
	// https://software.verified.de/mantis/view.php?id=7720#c15235
	public static Boolean isRtt6TestProcedure(String path) {
		if (path == null) {
			return false;
		}

		// check if directory exists
		File folder = new File(path);
		if (!((folder.exists()) && (folder.isDirectory()))) {
			return false;
		}

    	// check if conf directory does not exist or is empty (if so, return false)
		File confdir = new File(folder, "conf");
		if (!((confdir.exists()) && (confdir.isDirectory()))) {
			return false;
		}
		File[] files = confdir.listFiles();
		if (files == null) {
			return false;
		}

    	// check if config directory exists (if so, return false)
		File configdir = new File(folder, "config");
		if ((configdir.exists()) && (configdir.isDirectory())) {
			return false;
		}

    	// search files in conf for *.conf, but NOT configuration.csv
		Boolean has_conf_file = false;
		for (int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if (files[i].isFile()) {
				// search for *.conf
				if (name.endsWith(".conf")) {
					has_conf_file = true;
				}
				if (name.compareTo("configuration.csv") == 0) {
					return false;
				}
			}
		}

		return has_conf_file;
	}

	// this static function checks if a given path leads to an RTT-MBT test procedure generation context
	// this static function checks if a given path leads to an RT-Tester 6 test procedure
	// the checks are perfomed according to the criteria in 
	// Mantis PR 7720 "Adjust the project scanner library for MBT"
	// https://software.verified.de/mantis/view.php?id=7720#c15235
	public static Boolean isRttMbtTestProcedure(String path) {
		if (path == null) {
			return false;
		}

		// check if directory exists
		File folder = new File(path);
		if (!((folder.exists()) && (folder.isDirectory()))) {
			return false;
		}

    	// check if conf directory does not exist or is empty (if so, return false)
		File confdir = new File(folder, "conf");
		if (!((confdir.exists()) && (confdir.isDirectory()))) {
			return false;
		}
		File[] files = confdir.listFiles();
		if (files == null) {
			return false;
		}

    	// search files in conf for *.conf, but NOT configuration.csv
		for (int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if (files[i].isFile()) {
				// search for configuration.csv
				if (name.compareTo("configuration.csv") == 0) {
					return true;
				}
			}
		}

		return false;
	}

	// file separator that is regular expression compatible
	private static String fileSeparatorPattern() {
		String pattern = File.separator;
		if (pattern.compareTo("\\") == 0) {
			pattern = "\\\\";
		}
		return pattern;
	}

	public void initPathVariablesFromSelection(String selectedObjectFilesystemPath,
			                                   String selectedObjectWorkspacePath,
			                                   String selectedObjectWorkspaceProjectName,
			                                   String selectedObjectWorkspaceProjectPath) {
		String selectedObjectFilesystemProjectName = selectedObjectWorkspaceProjectName;
		String selectedObjectFilesystemProjectPath = selectedObjectWorkspaceProjectPath;
		String selectedObjectWorkspaceProjectPrefix;

		// check for null pointers
		if ((selectedObjectFilesystemPath == null) ||
			(selectedObjectWorkspacePath == null) ||
			(selectedObjectWorkspaceProjectName == null) ||
			(selectedObjectWorkspaceProjectPath == null)) {
			return;
		}
		// calculate selectedObjectWorkspaceProjectName and selectedObjectWorkspaceProjectPrefix
		// from selectedObjectWorkspacePath and selectedObjectFilesystemPath
		// iterate from the last pat item to the first and check for RT-Tester project criteria
		String filesystempath = selectedObjectFilesystemPath;
		String workspacepath = selectedObjectWorkspacePath;
		int fpos = filesystempath.lastIndexOf(File.separator);
		int wpos = workspacepath.lastIndexOf(File.separator);
		while ((fpos != -1) && (wpos != -1)) {

			// check that the path does not end with '/'
			if ((filesystempath.length() > fpos + 1) && (workspacepath.length() > wpos + 1)) {
				// get last item of path
				String fitem = filesystempath.substring(fpos + 1);
				String witem = workspacepath.substring(wpos + 1);
				// the first RT-Tester project in the path must be
				// the project of the selected item
				if (RttMbtClient.isRttProject(filesystempath)) {
					selectedObjectFilesystemProjectName = fitem;
					selectedObjectFilesystemProjectPath = filesystempath;
					selectedObjectWorkspaceProjectName = witem;
					selectedObjectWorkspaceProjectPath = workspacepath;
					break;
				}
				// if the items do not match, the file system item
				// must be the file system project name
				if (fitem.compareTo(witem) != 0) {
					System.out.println(fitem + "and" + witem + " differ.");
					selectedObjectFilesystemProjectName = fitem;
					selectedObjectFilesystemProjectPath = filesystempath;
					selectedObjectWorkspaceProjectName = witem;
					selectedObjectWorkspaceProjectPath = workspacepath;
					break;
				}
			}

			// prepare next loop
			filesystempath = filesystempath.substring(0, fpos);
			workspacepath = workspacepath.substring(0, wpos);
			fpos = filesystempath.lastIndexOf(File.separator);
			wpos = workspacepath.lastIndexOf(File.separator);
		}

		// remove selectedObjectWorkspaceProjectName from selectedObjectWorkspaceProjectPrefix
		int pos = selectedObjectWorkspaceProjectPath.indexOf(selectedObjectWorkspaceProjectName);
		if (pos == -1) {
			System.err.println("*** error: unable to find workspace project " +
		                       selectedObjectWorkspaceProjectName + " in workspace project path " +
		                       selectedObjectWorkspaceProjectPath);
		}
		selectedObjectWorkspaceProjectPrefix = selectedObjectWorkspaceProjectPath.substring(0, pos - 1);
		if (selectedObjectWorkspaceProjectPrefix.length() == 0) {
			selectedObjectWorkspaceProjectPrefix = null;
		}

		// remove leading file separator characters from workspace project prefix
		if ((selectedObjectWorkspaceProjectPrefix != null) && (selectedObjectWorkspaceProjectPrefix.startsWith(File.separator))) {
			selectedObjectWorkspaceProjectPrefix = selectedObjectWorkspaceProjectPrefix.substring(1);
		}

		// set RT-Tester project name (folder in the file system)
		setRttProjectName(selectedObjectFilesystemProjectName);

		// set the path to the RT-Tester project in the filesystem
		setRttProjectPath(selectedObjectFilesystemProjectPath);

		// set eclipse project name in the current workspace that contains the RT-Tester project
		setWorkspaceProjectName(selectedObjectWorkspaceProjectName);

		// set the prefix of the RT-Tester project within the current workspace.
		setWorkspaceProjectPrefix(selectedObjectWorkspaceProjectPrefix);
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

	public String getDefaultProjectDatabaseName() {
		String databasename = substituteIllegalDbNameCharacters(userId + workspaceProjectPrefix + "_" + workspaceProjectName);
		return databasename;
	}

	public String getProjectDatabaseName() {
		return tmsDatabaseName;
	}
	
	public void setProjectDatabaseName(String name) {
		tmsDatabaseName = name;
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

	public String getRttProjectName() {
		return rttProjectName;
	}

	public void setRttProjectName(String projectName) {
		this.rttProjectName = projectName;
	}
	
	public String getRttProjectPath() {
		return rttProjectPath;
	}

	public void setRttProjectPath(String projectPath) {
		this.rttProjectPath = projectPath;
	}

    public String getWorkspaceProjectName() {
		return workspaceProjectName;
	}

	public void setWorkspaceProjectName(String workspaceProjectName) {
		this.workspaceProjectName = workspaceProjectName;
	}

	public String getWorkspaceProjectPrefix() {
		return workspaceProjectPrefix;
	}

	public void setWorkspaceProjectPrefix(String workspaceProjectPrefix) {
		this.workspaceProjectPrefix = workspaceProjectPrefix;
	}

	public String getWorkspacePath() {
		return workspacePath;
	}

	public void setWorkspacePath(String workspace) {
		this.workspacePath = workspace;
	}

	public String getFilesystemWorkspacePath() {
		if ((rttProjectName == null) || (rttProjectPath == null)) {
			return null;
		}
		int pos = rttProjectPath.indexOf(rttProjectName);
		if (pos == -1) {
			return null;
		}
		return rttProjectPath.substring(0, pos);
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
		if (name == null) {
			return;
		}
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
		if (name == null) {
			return;
		}
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

	public Boolean getAllowMultipleRttMbtTmsTasks() {
		return allowMultipleRttMbtTmsTasks;
	}

	public void setAllowMultipleRttMbtTmsTasks(Boolean multipleTasks) {
		this.allowMultipleRttMbtTmsTasks = multipleTasks;
	}

	public String getMakeToolProperty() {
		return sutMakeTool;
	}

	public void setMakeToolProperty(String maketool) {
		sutMakeTool = maketool;
	}

	public void setDefaultMakeToolProperty() {
		sutMakeTool = "make";
	}

	public void setIgnorePatternProperty(String pattern) {
		noUploadFilePatterns = pattern;
	}

	public void setDefaultIgnorePatternProperty() {
		noUploadFilePatterns = ".svn:.git:*.o";
	}
}
