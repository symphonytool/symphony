package eu.compassresearch.ide.collaboration.communication.messages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ecf.core.identity.ID;

import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.User;
import eu.compassresearch.ide.collaboration.files.FileDTO;

public class NewConfigurationMessage extends BaseMessage
{
	private static final long serialVersionUID = 3464157884650995258L;
	private String configUniqueID;
	private String signedBy;
	private long timeStamp;
	private String parentConfigUniqueID;
	private List<FileDTO> fileDTOs;
	private List<String> receiverList;

	public NewConfigurationMessage(ID sender, 
			String projectID, Configuration config, List<FileDTO> dtos, List<User> sendTo)
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
		
		receiverList = new ArrayList<>();
		for (User user : sendTo)
		{
			receiverList.add(user.getName());
		}
		
		this.fileDTOs = dtos;
	}

	public NewConfigurationMessage(ID sender, String projectID,
			Configuration config)
	{
		this(sender, projectID, config, new ArrayList<FileDTO>(), new ArrayList<User>());
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

	public List<FileDTO> getFileDTOs()
	{
		return fileDTOs;
	}
	
	public List<String> getReceiverList()
	{
		return receiverList;
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
