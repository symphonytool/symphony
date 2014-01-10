package eu.compassresearch.ide.collaboration.files;

import java.io.Serializable;
import java.util.Date;

import eu.compassresearch.ide.collaboration.datamodel.File;

public class FileSet implements Serializable 
{
	private static final long serialVersionUID = 1205778148091363586L;
	
	private String fileName;
	private String fileHash;
	private String fileHashName;
	private String fileContent;
	private long timstamp;
	
	public FileSet(File file, String content)
	{
		this.fileName = file.getName();
		this.fileHash = file.getHash();
		this.fileContent = content;
		this.timstamp = file.getTimeStamp().getTime();
		this.fileHashName = file.getHashFileName();
	}

	public String getFileName()
	{
		return fileName;
	}

	public String getFileHash()
	{
		return fileHash;
	}

	public String getFileHashName()
	{
		return fileHashName;
	}
	
	public String getFileContent()
	{
		return fileContent;
	}

	public long getTimestamp()
	{
		return timstamp;
	}
}
