package eu.compassresearch.ide.collaboration.files;

public class FileStatus
{
	public enum FileState
	{
		NEWFILE, CHANGED, UNCHANGED, NOCONFIG, ERROR;
	}
	
	private String fileName;
	private FileState status;
	private String hash;
	
	public FileStatus(String fileName, String hash)
	{
		this.fileName = fileName;
		this.hash = hash;
		this.status = FileState.NEWFILE;
	}

	public String getFileName()
	{
		return fileName;
	}

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
	}

	public FileState getStatus()
	{
		return status;
	}

	public void setStatus(FileState status)
	{
		this.status = status;
	}
}
