package eu.compassresearch.core.interpreter.debug.messaging;


public class CmlDbgStatusMessage extends CmlMessage {

	private CmlDbgpStatus status;
	
	public CmlDbgStatusMessage(CmlDbgpStatus status)
	{
		this.setStatus(status);
	}
	
	public CmlDbgpStatus getStatus() {
		return status;
	}

	protected void setStatus(CmlDbgpStatus status) {
		this.status = status;
	}
			
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		return CmlDbgStatusMessage.class.getSimpleName() + newLine +
			   "Status: "+ status;
	}

	@Override
	public CmlMessageType getType() {
		return CmlMessageType.STATUS;
	}

	@Override
	public String getKey() {
		return getStatus().toString();
	}
}
