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
public class jsonRemoveFileFromCacheCommand extends jsonCommand {

	private String filename;

	public jsonRemoveFileFromCacheCommand(RttMbtClient client) {
		super(client);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("user", userName);
		params.put("user-id", userId);
		params.put("filename", client.toUnixPath(client.removeLocalWorkspace(filename)));
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("remove-file-from-cache-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		// @todo: extract parameters by resule name
		return (JSONObject)reply.get("remove-file-from-cache-reply");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String user = (String)parameters.get("user");
		String userId = (String)parameters.get("user-id");
		String filename = (String)parameters.get("filename");
		String result = (String)parameters.get("result");
		if ((user == null) ||
			(userId == null) ||
			(filename == null) ||
			(result == null)) {
			return;
		}
		if (!(result.equals("PASS"))) {
			System.err.println("*** error: removing file '"
							   + filename + "' from cache directory for " 
							   + user + " (ID: '" + userId + "') failed!");
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
