package eu.compassresearch.ide.cml.interpreter_plugin;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.Gson;

public class CmlDialogMessage extends CmlMessage{

	private UUID requestId;
	private CmlRequest request;
	private CmlMessageType type;
	private String content;

	public CmlDialogMessage(CmlRequest request, Object value)
	{
		requestId = UUID.randomUUID();
		type = CmlMessageType.REQUEST;
		setValue(request,value);
	}
	
	public CmlDialogMessage(UUID requestId, CmlRequest request, Object value)
	{
		this.requestId = requestId;
		type = CmlMessageType.RESPONSE;
		setValue(request,value);
	}
	
	public UUID getRequestId() {
		return requestId;
	}

	public CmlRequest getRequest()
	{
		return request;
	}
	
	protected void setValue(CmlRequest request, Object value)
	{
		Gson gson = new Gson();
		this.request = request;
		this.content = gson.toJson(value);
	}

	public <T> T getValue(Class<T> classType)
	{
		Gson gson = new Gson();
		return gson.fromJson(content,classType);
	}
	
	public <T> T getValue(Type classType)
	{
		Gson gson = new Gson();
		return gson.fromJson(content,classType);
	}

	@Override
	public CmlMessageType getType() {
		return type;
	}
}
