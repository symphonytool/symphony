package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.user.IUser;

public class NewFileMessage extends BaseMessage
{
	private static final long serialVersionUID = 3464157884650995258L;
	
	private final String filename;
	private final String contents;
	
	public NewFileMessage(IUser sentBy, IUser sentTo, String projectID, String filename, String contents)
	{
		super(sentBy, sentTo, projectID);
		this.filename = filename;
		this.contents = contents;
	}

	public String getContents()
	{
		return contents;
	}

	public String getFilename()
	{
		return filename;
	}
}


