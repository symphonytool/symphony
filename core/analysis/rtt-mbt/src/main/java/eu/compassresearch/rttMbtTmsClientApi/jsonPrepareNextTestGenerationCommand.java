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
public class jsonPrepareNextTestGenerationCommand extends jsonCommand {

	private String testProcName;
	private String oldPostfix;
	private String newPostfix;

	public jsonPrepareNextTestGenerationCommand(RttMbtClient client) {
		super(client);
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
		params.put("user", client.getUserName());
		params.put("user-id", client.getUserId());
		params.put("project-name", client.toUnixPath(client.removeLocalWorkspace(client.getRttProjectPath())));
		params.put("test-procedure-path", client.toUnixPath(testProcName));
		params.put("old-postfix", oldPostfix);
		params.put("new-postfix", newPostfix);

		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("prepare-next-test-generation-command", params);
		System.out.println("command: '" + cmd.toJSONString() + "'");
		return cmd.toJSONString();
	}

	public JSONObject getParameters(JSONObject reply) {
		if (reply == null) {
			return null;
		}
		return (JSONObject)reply.get("prepare-next-test-generation-result");
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

	public String getOldPostfix() {
		return oldPostfix;
	}

	public void setOldPostfix(String postfix) {
		this.oldPostfix = postfix;
	}

	public String getNewPostfix() {
		return newPostfix;
	}

	public void setNewPostfix(String postfix) {
		this.newPostfix = postfix;
	}
}
