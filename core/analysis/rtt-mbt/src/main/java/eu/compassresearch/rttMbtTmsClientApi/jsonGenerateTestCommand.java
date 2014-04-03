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
public class jsonGenerateTestCommand extends jsonCommand {

	private String testProcName;
	private Boolean guiPorts;
	private Boolean maximizeModelCoverage;
	private Boolean abstractInterpreter;

	public jsonGenerateTestCommand(RttMbtClient client) {
		super(client);
		guiPorts = false;
		maximizeModelCoverage = true;
		abstractInterpreter = false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getJsonCommandString() {
		// check if project name is properly assigned
		if (client.getRttProjectPath() == null) {
			System.err.println("[ERROR]: project name not assigned!");
			return null;
		}

		// add parameters
		Map params = new LinkedHashMap();
		params.put("project-name", client.toUnixPath(client.removeLocalWorkspace(client.getRttProjectPath())));
		params.put("test-procedure-path", client.toUnixPath(testProcName));
		// maximize-model-coverage
		if (maximizeModelCoverage) {
			params.put("maximize-model-coverage", "true");			
		}
		// abstract-interpreter
		if (abstractInterpreter) {
			params.put("abstract-interpreter", "true");			
		}
		// use gui ports
		if (guiPorts) {
			params.put("progress-port", "true");
			params.put("console-port", "true");
		}
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

	public String getTestProcName() {
		return testProcName;
	}

	public void setTestProcName(String testProcName) {
		this.testProcName = testProcName;
	}

	public Boolean getGuiPorts() {
		return guiPorts;
	}

	public void setGuiPorts(Boolean guiPorts) {
		this.guiPorts = guiPorts;
		hasProgress = guiPorts;
		hasConsole = guiPorts;
	}

	public Boolean getMaximizeModelCoverage() {
		return maximizeModelCoverage;
	}

	public void setMaximizeModelCoverage(Boolean maximizeModelCoverage) {
		this.maximizeModelCoverage = maximizeModelCoverage;
	}

	public Boolean getAbstractInterpreter() {
		return abstractInterpreter;
	}

	public void setAbstractInterpreter(Boolean abstractInterpreter) {
		this.abstractInterpreter = abstractInterpreter;
	}
}
