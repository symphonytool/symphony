package eu.compassresearch.ide.collaboration.communication.messages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ecf.core.identity.ID;

import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.files.FileDTO;

public class NewConfigurationMessage extends BaseMessage
{
	private static final long serialVersionUID = 3464157884650995258L;
	private String configUniqueID;
	private String signedBy;
	private long timeStamp;
	private String parentConfigUniqueID;
	private List<FileDTO> fileDTOs;

	public NewConfigurationMessage(ID sender, 
			String projectID, Configuration config, List<FileDTO> dtos)
	{
		super(sender, projectID);

		this.configUniqueID = config.getUniqueID();
		this.signedBy = config.getSignedBy();
		this.timeStamp = config.getTimeStamp().getTime();
		Configuration parentConfiguration = config.getParentConfiguration();
		if (parentConfiguration != null)
		{
			this.parentConfigUniqueID = parentConfiguration.getUniqueID();
		}
		
		this.fileDTOs = dtos;
	}

	public NewConfigurationMessage(ID sender, String projectID,
			Configuration config)
	{
		this(sender, projectID, config, new ArrayList<FileDTO>());
	}

	public String getConfigurationUniqueID()
	{
		return configUniqueID;
	}

	public String getSignedBy()
	{
		return signedBy;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public String getParentConfigurationUniqueID()
	{
		return parentConfigUniqueID;
	}

	public List<FileDTO> getFileSets()
	{
		return fileDTOs;
	}

	public void addFiles(List<FileDTO> files)
	{
		if(files == null)
			return;
		
		fileDTOs.addAll(files);
	}
	
	@Override
	public String toString()
	{
		return fileDTOs.toString();
	}
}
