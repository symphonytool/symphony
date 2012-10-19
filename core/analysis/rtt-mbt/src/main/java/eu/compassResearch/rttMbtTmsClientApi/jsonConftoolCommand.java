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
public class jsonConftoolCommand extends jsonCommand {

	private String modelName;
	private String modelId;

	public jsonConftoolCommand(RttMbtClient client) {
		super(client);
	}
	
	public void setModelName(String name) {
		modelName = name;
	}

	public void setModelId(String id) {
		modelId = id;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("model-name", modelName);
		params.put("model-version", modelId);
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("conftool-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		return (JSONObject)reply.get("conftool-result");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		writeBase64StringFileContent("configuration.csv",
								     (String)parameters.get("configuration.csv"), false);
	}
}
