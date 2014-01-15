/**
 * 
 */
package eu.compassresearch.rttMbtTmsClientApi;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * @author uwe
 *
 */
public class jsonGetCachDirectoryListCommand extends jsonCommand {

	private String dirname;
	private List<String> dirnames;

	public jsonGetCachDirectoryListCommand(RttMbtClient client) {
		super(client);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("user", userName);
		params.put("user-id", userId);
		params.put("directory", client.toUnixPath(client.removeLocalWorkspace(dirname)));
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("get-cache-directory-list-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		// @todo: extract parameters by resule name
		return (JSONObject)reply.get("get-cache-directory-list-reply");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String user = (String)parameters.get("user");
		String userId = (String)parameters.get("user-id");
		JSONArray dirlist = (JSONArray)parameters.get("directory-list");

		if ((user == null) ||
			(userId == null) ||
			(dirlist == null)) {
			return;
		}
		// store file list
		if (dirnames == null) {
			dirnames = new ArrayList<String>();
		} else {
			dirnames.clear();
		}
		for (int idx = 0; idx < dirlist.size(); idx++) {
			String encoded = (String) dirlist.get(idx);
			byte [] decoded = Base64.decodeBase64(encoded);
			try {
				String filename = null;
				filename = new String(decoded, "ASCII");
				dirnames.add(filename.toString());
			} catch (UnsupportedEncodingException e) {
				// do not add filename
			}
		}

		return;
	}

	public String getDirname() {
		return dirname;
	}

	public void setDirname(String dirname) {
		this.dirname = dirname;
	}

	public List<String> getDirnames() {
		return dirnames;
	}

	public void setDirnames(List<String> dirnames) {
		this.dirnames = dirnames;
	}
}
