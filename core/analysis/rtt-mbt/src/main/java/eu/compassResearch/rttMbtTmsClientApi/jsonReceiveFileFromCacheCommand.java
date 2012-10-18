/**
 * 
 */
package eu.compassResearch.rttMbtTmsClientApi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

/**
 * @author uwe
 *
 */
public class jsonReceiveFileFromCacheCommand extends jsonCommand {

	private String filename;

	public jsonReceiveFileFromCacheCommand(String server, Integer port, String user, String uid) {
		super(server, port, user, uid);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("user", userName);
		params.put("user-id", userId);
		params.put("filename", filename);
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("receive-file-from-cache-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		// @todo: extract parameters by resule name
		return (JSONObject)reply.get("receive-file-from-cache-reply");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String user = (String)parameters.get("user");
		String userId = (String)parameters.get("user-id");
		String filename = (String)parameters.get("filename");
		String fileContent = (String)parameters.get("content");
		String checksum = (String)parameters.get("checksum");
		if ((user == null) ||
			(userId == null) ||
			(filename == null) ||
			(fileContent == null) ||
			(checksum == null)) {
			return;
		}
		// store file
		writeBase64StringFileContent(filename, fileContent, true);
		String localChecksum = getSHA256Checksum(filename);
		if (!checksum.equals(localChecksum)) {
			System.err.println("*** error: checksum of received file '" + filename + "' does not match!");
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
