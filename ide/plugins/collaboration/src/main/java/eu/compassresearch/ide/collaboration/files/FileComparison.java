package eu.compassresearch.ide.collaboration.files;


import eu.compassresearch.ide.collaboration.datamodel.File;

public class FileComparison
{
	private File targetFile;
	private String targetFileContent;
	private File previousFile;
	private String previousFileContent;

	public FileComparison(File targetFile, String targetFileContent,
			File previousFile, String previousFileContent)
	{
		this.targetFile = targetFile;
		this.targetFileContent = targetFileContent;
		this.previousFile = previousFile;
		this.previousFileContent = previousFileContent;
	}

	public File getTargetFile()
	{
		return targetFile;
	}

	public String getTargetFileContent()
	{
		return targetFileContent;
	}
	
	public String getTargetFileName()
	{
		return targetFile.getName() + previousFile.getTimeStamp();
	}

	public File getPreviousFile()
	{
		return previousFile;
	}

	public String getPreviousFileContent()
	{
		return previousFileContent;
	}

	public String getPreviousFileName()
	{
		return previousFile.getName() + " " + previousFile.getTimeStamp();
	}
}
