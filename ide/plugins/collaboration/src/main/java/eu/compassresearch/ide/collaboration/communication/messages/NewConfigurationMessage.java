package eu.compassresearch.ide.collaboration.communication.messages;

import java.util.List;

import org.eclipse.ecf.core.user.IUser;

import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.files.FileSet;

public class NewConfigurationMessage extends BaseMessage
{
	private static final long serialVersionUID = 3464157884650995258L;
	private String configUniqueID;
	private String signedBy;
	private long timeStamp;
	private String parentConfigUniqueID;
	private List<FileSet> fileSets;

	public NewConfigurationMessage(IUser sentBy, IUser sentTo,
			String projectID, Configuration config, List<FileSet> fileSets)
	{
		super(sentBy, sentTo, projectID);

		this.configUniqueID = config.getUniqueID();
		this.signedBy = config.getSignedBy();
		this.timeStamp = config.getTimeStamp().getTime();
		Configuration parentConfiguration = config.getParentConfiguration();
		if (parentConfiguration != null)
		{
			this.parentConfigUniqueID = parentConfiguration.getUniqueID();
		}
		
		this.fileSets = fileSets;
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

	public List<FileSet> getFileSets()
	{
		return fileSets;
	}
}
