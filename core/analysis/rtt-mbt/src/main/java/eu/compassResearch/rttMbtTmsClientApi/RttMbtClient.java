package eu.compassResearch.rttMbtTmsClientApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RttMbtClient {

	// User name and id
	private String rttMbtServer;
	private Integer rttMbtPort;
	protected String projectName;
	protected String userName;
	protected String userId;

	public RttMbtClient(String server, Integer port, String user, String id) {
		rttMbtServer = server;
		rttMbtPort = port;
		userName = user;
		userId = id;
	}

	public void setProject(String project) {
		projectName = project;
	}
	
	public String getProject() {
		return projectName;
	}

	public Boolean testConenction() {
		Boolean success = false;
		
		jsonTestConnectionCommand cmd = new jsonTestConnectionCommand(rttMbtServer, rttMbtPort, userName, userId);
		cmd.executeCommand();
		if (!cmd.executedSuccessfully()) {
			success = false;
		}

		return success;
	}

	public Boolean beginRttMbtSession() {
		jsonCheckFileCacheExistsCommand check =
					new jsonCheckFileCacheExistsCommand(rttMbtServer, rttMbtPort, userName, userId);
		check.executeCommand();
		if (!check.executedSuccessfully()) {
			jsonStartFileCacheCommand start =
					new jsonStartFileCacheCommand(rttMbtServer, rttMbtPort, userName, userId);
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

		// check if file already is in cache
		System.out.println("checking if file '" + filename + "' already exists in cache");
		jsonCheckFileInCacheCommand check = new jsonCheckFileInCacheCommand(rttMbtServer, rttMbtPort, userName, userId);
		check.setFilename(filename);
		String checkReply = check.executeCommand();
		if (check.executedSuccessfully() && check.getResult()) {
			return true;
		} else {
			System.err.println("[FAIL]: checking file '" + filename + "' failed!");
			System.err.println("reply: " + checkReply);
		}

		// if file is not in cache: upload file
		System.out.println("uploading file '" + filename + "' to cache");
		jsonSendFileToCacheCommand cmd = new jsonSendFileToCacheCommand(rttMbtServer, rttMbtPort, userName, userId);
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
		File folder = new File(directory);
		File[] files = folder.listFiles();
		List<String> folders = new ArrayList<String>();
		if (files == null) {
			System.err.println("getting file list of directory '" + directory + "' failed!");
			return false;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				success = (uploadFile(directory + "/" + files[i].getName()) && success);
			} else if ((files[i].isDirectory()) && (recursive)) {
				folders.add(directory + "/" + files[i].getName());
			}
		}
		for (int i = 0; i < folders.size(); i++) {
			System.out.println("uploading directory " + folders.get(i));
			success = (uploadDirectory(folders.get(i), recursive) && success);
		}
		return success;
	}

	public Boolean downloadFile(String filename) {
		Boolean success = true;

		// check if file already is up to date
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			System.out.println("checking if existing file '" + filename + "' already is up to date");
			jsonCheckFileInCacheCommand check = new jsonCheckFileInCacheCommand(rttMbtServer, rttMbtPort, userName, userId);
			check.setFilename(filename);
			String checkReply = check.executeCommand();
			if (check.executedSuccessfully() && check.getResult()) {
				return true;
			} else {
				System.err.println("[FAIL]: checking file '" + filename + "' failed!");
				System.err.println("reply: " + checkReply);
			}
		}
		
		// if file is not up to date: download file
		System.out.println("downloading file '" + filename + "' from cache");
		jsonReceiveFileFromCacheCommand cmd = new jsonReceiveFileFromCacheCommand(rttMbtServer, rttMbtPort, userName, userId);
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

		System.out.println("downloading files in directory '" + directory + "' from cache");
		
		// get file list
		jsonGetCachFileListCommand cmd = new jsonGetCachFileListCommand(rttMbtServer, rttMbtPort, userName, userId);
		cmd.setDirname(directory);
		String reply = cmd.executeCommand();
		success = cmd.executedSuccessfully();
		if (!success) {
			System.err.println("[FAIL]: retrieveing file list for directory '" + directory + "' failed!");
			System.err.println("reply: " + reply);
			return success;
		}

		// for each file: download file
		List<String> filenames = cmd.getFilenames();
		if (filenames == null) {
			return success;
		}
		for (int idx = 0; idx < filenames.size(); idx++) {
			downloadFile(directory + "/" + filenames.get(idx));
		}
		
		return success;
	}
	
	public Boolean createProject(String projecName) {
		// check if project already exists
		// set project name
		// create project structure
		// check/start cache
		// upload project structure to cache
		return false;
	}
	
	public Boolean initProject(String modelName, String modelVersion, String modelFileName) {
		// copy model to model/ directory
		// send model to file cache
		// perform store model command
		// unpack _P1.zip
		// perform livelock check
		// perform conftool command
		// perform sigmaptool command
		return false;
	}
	
}
