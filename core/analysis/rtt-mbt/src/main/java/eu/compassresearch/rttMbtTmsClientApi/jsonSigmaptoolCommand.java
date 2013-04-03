package eu.compassresearch.rttMbtTmsClientApi;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

/**
 * @author uwe
 *
 */
public class jsonSigmaptoolCommand extends jsonCommand {

	private String modelName;
	private String modelId;
	private String testProcName;

	public jsonSigmaptoolCommand(RttMbtClient client) {
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
		cmd.put("sigmaptool-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		return (JSONObject)reply.get("sigmaptool-result");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String filename = "";
		if (client.getRttProjectRoot() != null) {
			filename = client.getRttProjectRoot() + File.separator;
		}
		// if the test procedure name is not defined, generate
		// the signalmap.csv in the model folder of the project 
		if (testProcName != null) {
			filename += "TestProcedures" + File.separator + testProcName + File.separator + "conf" + File.separator;
		} else {
			filename += "model" + File.separator;
		}
		filename += "signalmap.csv";
		writeBase64StringFileContent(filename,
								     (String)parameters.get("signalmap.csv"), false);
	}

	public String getTestProcName() {
		return testProcName;
	}

	public void setTestProcName(String testProcName) {
		this.testProcName = testProcName;
	}
}
