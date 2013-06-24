/**
 * 
 */
package eu.compassresearch.rttMbtTmsClientApi;
import org.json.simple.JSONObject;

/**
 * @author uwe
 *
 */
public class jsonTestConnectionCommand extends jsonCommand {

	public jsonTestConnectionCommand(RttMbtClient client) {
		super(client);
	}
	
	@SuppressWarnings("unchecked")
	public String getJsonCommandString() {
		JSONObject obj = new JSONObject();
		obj.put("test-connection-command", "");
		return obj.toJSONString();
	}
	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		return (JSONObject)reply.get("test-connection-result");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String version = (String)parameters.get("server-version");
		String uptime = (String)parameters.get("uptime");
		if (version != null) {
			client.setRttMbtServerVersion(version);
		}
		if (uptime != null) {
			client.setRttMbtServerUptime(uptime);
		}
	}
}
