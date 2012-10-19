/**
 * 
 */
package eu.compassResearch.rttMbtTmsClientApi;

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
public class jsonGetCachFileListCommand extends jsonCommand {

	private String dirname;
	private List<String> filenames;

	public jsonGetCachFileListCommand(RttMbtClient client) {
		super(client);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("user", userName);
		params.put("user-id", userId);
		params.put("directory", dirname);
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("get-cache-file-list-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		// @todo: extract parameters by resule name
		return (JSONObject)reply.get("get-cache-file-list-reply");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String user = (String)parameters.get("user");
		String userId = (String)parameters.get("user-id");
		JSONArray filelist = (JSONArray)parameters.get("file-list");

		if ((user == null) ||
			(userId == null) ||
			(filelist == null)) {
			return;
		}
		// store file list
		if (filenames == null) {
			filenames = new ArrayList<String>();
		} else {
			filenames.clear();
		}
		for (int idx = 0; idx < filelist.size(); idx++) {
			String encoded = (String) filelist.get(idx);
			byte [] decoded = Base64.decodeBase64(encoded);
			try {
				String filename = null;
				filename = new String(decoded, "ASCII");
				filenames.add(filename.toString());
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

	public List<String> getFilenames() {
		return filenames;
	}

	public void setFilenames(List<String> filenames) {
		this.filenames = filenames;
	}
}
