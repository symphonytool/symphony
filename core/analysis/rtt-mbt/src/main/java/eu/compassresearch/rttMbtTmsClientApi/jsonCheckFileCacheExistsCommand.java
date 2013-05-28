/**
 * 
 */
package eu.compassresearch.rttMbtTmsClientApi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

/**
 * @author uwe
 *
 */
public class jsonCheckFileCacheExistsCommand extends jsonCommand {
	public jsonCheckFileCacheExistsCommand(RttMbtClient client) {
		super(client);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("user", userName);
		params.put("user-id", userId);
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("check-file-cache-exists-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		// extract parameters by resule name
		return (JSONObject)reply.get("check-file-cache-exists-result");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String user = (String)parameters.get("user");
		String userId = (String)parameters.get("user-id");
		String result = (String)parameters.get("result");
		if ((result == null) ||
			(user == null) ||
			(userId == null)) {
			return;
		}
		if (!(result.equals("PASS"))) {
			System.err.println("*** error: setup cache directory for " 
							   + user + " (ID: '" + userId + "') failed!");
		} else {
			System.out.println("created cache directory for " 
					   + user + " (ID: '" + userId + "').");		
		}
	}
}
