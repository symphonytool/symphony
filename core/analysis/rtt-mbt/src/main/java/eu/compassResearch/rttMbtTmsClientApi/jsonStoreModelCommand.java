/**
 * 
 */
package eu.compassResearch.rttMbtTmsClientApi;
import java.util.Map;
import java.util.LinkedHashMap;
import org.json.simple.JSONObject;

/**
 * @author uwe
 *
 */
public class jsonStoreModelCommand extends jsonCommand {

	private String modelName;
	private String modelId;
	private String modelFile;

	public jsonStoreModelCommand(RttMbtClient client) {
		super(client);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// add parameters
		Map params = new LinkedHashMap();
		params.put("model-name", modelName);
		params.put("model-version", modelId);
		params.put("model", getBase64StringFileContent(modelFile, false));
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("store-model-command", params);
		return cmd.toJSONString();
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
}
