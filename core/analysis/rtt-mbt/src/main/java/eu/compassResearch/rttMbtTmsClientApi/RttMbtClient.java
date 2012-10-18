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

	public RttMbtClient(String server, Integer port, String user, String id) {
		rttMbtServer = server;
		rttMbtPort = port;
		userName = user;
		userId = id;
	}

	public String getProject() {
		return projectName;
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
			jsonCheckFileInCacheCommand check = new jsonCheckFileInCacheCommand(this);
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
			downloadFile(directory + "/" + filenames.get(idx));
		}
		
		return success;
	}
	
	public Boolean createProject(String project) {
		Boolean success = true;
		
		// set project name
		projectName = project;

		// check if local directory exists
		File folder = new File(project);
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
			// extract project template
			File templates = new File("templates");
			if (!templates.isDirectory()) {
				System.err.println("[FAIL]: local 'templates' directory does not exist!");
				return false;
			}
			File archive = new File(templates, "_Project_compass.zip");
			success = unzipArchive(archive.getPath(), projectName);
		}

		// upload project structure to cache
		success = uploadDirectory(project, true);
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
		File projectRoot = new File(projectName);
		try {
			if (!projectRoot.isDirectory()) {
				System.err.println("[FAIL]: project directory '" + projectName + "' does not exist!");
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
		// @uwe: this is actually not needed, because the model is stored in the models directory

		// perform store model command
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
		File templates = new File("templates");
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
		System.out.println("performing livelock check of the model!");
		jsonCheckModelCommand checkModel = new jsonCheckModelCommand(this);
		checkModel.setModelName(modelName);
		checkModel.setModelId(modelVersion);
		checkModel.executeCommand();
		if (!checkModel.executedSuccessfully()) {
			System.err.println("[FAIL]: livelock check of model '" + modelName + "', version '" + modelVersion + "' on RTT-MBT server failed!");
			return false;
		}

		// perform conftool command
		// perform sigmaptool command
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
	    	
		} catch (FileNotFoundException e) {
			System.err.println("[FAIL]: template archive '" + archiveName + "' does not exist!");
			return false;
		} catch (IOException e) {
			System.err.println("[FAIL]: unable to extract files from template archive '_Project_compass.zip'!");
			return false;
		}
		return success;
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
	
}
