package eu.compassresearch.ide.collaboration.messages;

import java.util.Date;

import org.eclipse.ecf.core.identity.ID;

public class FileStatusMessage extends BaseMessage
{
	private static final long serialVersionUID = 2945404088404137893L;

	private final String filename;
	private final Date timestamp;
	private final NegotiationStatus status; 

	
	public enum NegotiationStatus {
	    RECEIVED, ACCEPT, REJECT, RENEGOTIATE; 
	}
	
	public FileStatusMessage(ID sendBy, ID sendTo, String filename, NegotiationStatus status, Date timestamp)
	{
		super(sendBy, sendTo);
		
		this.filename = filename;
		this.status = status;
		this.timestamp = timestamp;
	}

	public Date getTimestamp()
	{
		return timestamp;
	}

	public String getFilename()
	{
		return filename;
	}

	public NegotiationStatus getStatus()
	{
		return status;
	}

}
