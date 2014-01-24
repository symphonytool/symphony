package eu.compassresearch.ide.collaboration.datamodel; //TODO move to a different package

import java.util.ArrayList;
import java.util.List;

import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.files.FileStatus.FileState;

public class ConfigurationComparison
{	
	private List<FileStatus> compareResult;
	
	public ConfigurationComparison()
	{
		compareResult = new ArrayList<FileStatus>();
	}
	
	public void addRemovedFiles(List<File> removedFiles)
	{
		for (File file : removedFiles)
		{
			compareResult.add(new FileStatus(file.getName(), file.getHash(), FileState.REMOVED));
		}
	}
	

	public void addRemovedFile(File removedFile)
	{
			compareResult.add(new FileStatus(removedFile.getName(), removedFile.getHash(), FileState.REMOVED));
	}
	

	public void addAddedFiles(ArrayList<File> addedFiles)
	{
		for (File file : addedFiles)
		{
			compareResult.add(new FileStatus(file.getName(), file.getHash(), FileState.ADDED));
		}	
	}
	
	public void addAddedFile(File addedFile)
	{
		compareResult.add(new FileStatus(addedFile.getName(), addedFile.getHash(), FileState.ADDED));
	}

	public void addChangedFiles(ArrayList<File> changedFiles)
	{
		for (File file : changedFiles)
		{
			compareResult.add(new FileStatus(file.getName(), file.getHash(), FileState.CHANGED));
		}	
	}

	public void addChangedFile(File changedFile)
	{
		compareResult.add(new FileStatus(changedFile.getName(), changedFile.getHash(), FileState.CHANGED));
	}
	
	public void addUnchangedFiles(ArrayList<File> unchangedFiles)
	{
		for (File file : unchangedFiles)
		{
			compareResult.add(new FileStatus(file.getName(), file.getHash(), FileState.UNCHANGED));
		}	
	}
	
	public void addUnchangedFile(File unchangedFile)
	{
		compareResult.add(new FileStatus(unchangedFile.getName(), unchangedFile.getHash(), FileState.UNCHANGED));
	}

	public void addFileStatus(FileStatus fileStatus)
	{
		compareResult.add(fileStatus);
	}
	
	public List<FileStatus> getCompareResult()
	{
		return compareResult;
	}
}
