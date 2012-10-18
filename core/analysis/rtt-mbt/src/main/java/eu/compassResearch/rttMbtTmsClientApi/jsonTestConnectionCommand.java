/**
 * 
 */
package eu.compassResearch.rttMbtTmsClientApi;
import org.json.simple.JSONObject;

/**
 * @author uwe
 *
 */
public class jsonTestConnectionCommand extends jsonCommand {

	public jsonTestConnectionCommand(String server, Integer port, String user, String uid) {
		super(server, port, user, uid);
	}
	
	@SuppressWarnings("unchecked")
	public String getJsonCommandString() {
		JSONObject obj = new JSONObject();
		obj.put("test-connection-command", "");
		return obj.toJSONString();
	}
}
