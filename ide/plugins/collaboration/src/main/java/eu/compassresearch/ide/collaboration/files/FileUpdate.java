package eu.compassresearch.ide.collaboration.files;

public class FileUpdate
{
	private String hash;
	private String filename;

	public FileUpdate(String filename, String hash)
	{
		this.filename = filename;
		this.hash = hash;
	}
	
	public String getFileName()
	{
		return filename;
	}
	
	public String getHash()
	{
		return hash;
	}
}
