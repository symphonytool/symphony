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
public class jsonCheckModelFileCommand extends jsonCommand {

	private String modelName;
	private String modelId;
	private String modelFile;

	public jsonCheckModelFileCommand(RttMbtClient client) {
		super(client);
	}
	
	public void setModelName(String name) {
		modelName = name;
	}

	public void setModelId(String id) {
		modelId = id;
	}

	public void setModelFile(String filename) {
		modelFile = filename;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("model-name", modelName);
		params.put("model-version", modelId);
		params.put("model-file-checksum", getSHA256Checksum(modelFile));
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("check-model-file-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		return (JSONObject)reply.get("check-model-file-result");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		if (!((String)parameters.get("result")).equals("PASS")) {
			System.err.println("The local model file for " +
		                       (String)parameters.get("model-name") + ", " +
					           (String)parameters.get("model-version") +
					           " differs from the file stored on the server!");
		}
	}
}
