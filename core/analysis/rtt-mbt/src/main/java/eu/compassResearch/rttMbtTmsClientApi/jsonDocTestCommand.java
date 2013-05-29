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
public class jsonDocTestCommand extends jsonCommand {

	 private String testProcName;

	 public jsonDocTestCommand(RttMbtClient client) {
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
		 params.put("user", client.getUserName());
		 params.put("user-id", client.getUserId());
		 params.put("project-name", client.getProjectName());
		 params.put("test-procedure-path", client.toUnixPath(testProcName));
		 // create command
		 JSONObject cmd = new JSONObject();
		 cmd.put("doc-test-command", params);
		 return cmd.toJSONString();
	 }

	 public JSONObject getParameters(JSONObject reply) {
		 if (reply == null) {
			 return null;
		 }
		 return (JSONObject)reply.get("doc-test-result");
	 }

	 public void handleParameters(JSONObject parameters) {
		 // get the parameter list
		 if (parameters == null) {
			 return;
		 }
		 // - get result
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
}
