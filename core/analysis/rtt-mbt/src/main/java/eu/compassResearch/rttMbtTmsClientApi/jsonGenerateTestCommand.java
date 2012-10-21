/**
 * 
 */
package eu.compassResearch.rttMbtTmsClientApi;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

/**
 * @author uwe
 *
 */
public class jsonGenerateTestCommand extends jsonCommand {

	private String testProcName;

	public jsonGenerateTestCommand(RttMbtClient client) {
		super(client);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// check if project name is properly assigned
		if (client.getProjectName() == null) {
			System.err.println("[ERROR]: project name not assigned!");
			return null;
		}

		// add parameters
		Map params = new LinkedHashMap();
		params.put("project-name", client.getProjectName());
		params.put("test-procedure-path", testProcName);
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("generate-test-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		return (JSONObject)reply.get("test-generation-result");
	}

	public void handleParameters(JSONObject parameters) {
		if (parameters == null) {
			return;
		}
		String filename = "";
		if (client.getProjectName() != null) {
			filename = client.projectName + File.separator;
		}
		// if the test procedure name is not defined, generate
		// the signalmap.csv in the model folder of the project 
		if (testProcName != null) {
			filename += "TestProcedures" + File.separator + testProcName + File.separator + "conf" + File.separator;
		} else {
			filename += "model" + File.separator;
		}
		filename += "configuration.csv";
		writeBase64StringFileContent(filename,
								     (String)parameters.get("configuration.csv"), false);
	}

	public String getTestProcName() {
		return testProcName;
	}

	public void setTestProcName(String testProcName) {
		this.testProcName = testProcName;
	}
}
