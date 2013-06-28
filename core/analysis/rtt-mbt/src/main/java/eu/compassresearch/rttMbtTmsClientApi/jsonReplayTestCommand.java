package eu.compassresearch.rttMbtTmsClientApi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class jsonReplayTestCommand extends jsonCommand {

	private String testProcName;
	private Boolean guiPorts;

	public jsonReplayTestCommand(RttMbtClient client) {
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
		params.put("project-name", client.toUnixPath(client.getProjectPath()));
		params.put("test-procedure-path", client.toUnixPath(testProcName));
		// use gui ports
		if (guiPorts) {
			params.put("progress-port", "true");
			params.put("console-port", "true");
		}
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("replay-command", params);
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		return (JSONObject)reply.get("replay-results");
	}

	public void handleParameters(JSONObject parameters) {

		// get the parameter list
		if (parameters == null) {
			return;
		}

		// get the result
		String checkResult = (String)parameters.get("result");
		if (!(checkResult.equals("PASS"))) {
			resultValue = false;
		} else {
			resultValue = true;
		}
	}

	public void setTestProcName(String testProcedureName) {
		testProcName = testProcedureName;
	}

	public void setGuiPorts(Boolean gui) {
		guiPorts = gui;
		hasProgress = gui;
		hasConsole = gui;
	}

}
