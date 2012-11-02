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
 */public class jsonCompileTestCommand extends jsonCommand {

	 private String testProcName;

	 public jsonCompileTestCommand(RttMbtClient client) {
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
		 cmd.put("compile-test-command", params);
		 return cmd.toJSONString();
	 }

	 public JSONObject getParameters(JSONObject reply) {
		 if (reply == null) {
			 return null;
		 }
		 return (JSONObject)reply.get("compile-test-result");
	 }

	 public void handleParameters(JSONObject parameters) {
		 if (parameters == null) {
			 return;
		 }
		 // @todo:
		 // - get result
		 // - if compilation failed: get error.log
		 // - if compilation passes: anything to do?
	 }

	 public String getTestProcName() {
		 return testProcName;
	 }

	 public void setTestProcName(String testProcName) {
		 this.testProcName = testProcName;
	 }
}
