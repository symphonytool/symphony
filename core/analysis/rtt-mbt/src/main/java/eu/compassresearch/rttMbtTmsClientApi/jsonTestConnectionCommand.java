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
}
