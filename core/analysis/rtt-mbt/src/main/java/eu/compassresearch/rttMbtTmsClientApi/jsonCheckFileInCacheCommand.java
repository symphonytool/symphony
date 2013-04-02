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
public class jsonCheckFileInCacheCommand extends jsonCommand {

	private String filename;

	public jsonCheckFileInCacheCommand(RttMbtClient client) {
		super(client);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("user", userName);
		params.put("user-id", userId);
		params.put("filename", client.toUnixPath(client.removeLocalWorkspace(filename)));
		params.put("checksum", getSHA256Checksum(filename));
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("check-file-in-cache-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		// @todo: extract parameters by resule name
		return (JSONObject)reply.get("check-file-in-cache-reply");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String user = (String)parameters.get("user");
		String userId = (String)parameters.get("user-id");
		String filename = (String)parameters.get("filename");
		String checkResult = (String)parameters.get("result");
		if ((user == null) ||
			(userId == null) ||
			(filename == null) ||
			(checkResult == null)) {
			return;
		}
		if (!(checkResult.equals("PASS"))) {
			resultValue = false;
		} else {
			resultValue = true;
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
