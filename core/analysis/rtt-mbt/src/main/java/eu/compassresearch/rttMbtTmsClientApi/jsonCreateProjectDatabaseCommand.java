package eu.compassresearch.rttMbtTmsClientApi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class jsonCreateProjectDatabaseCommand extends jsonCommand {

	 public jsonCreateProjectDatabaseCommand(RttMbtClient client) {
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
		 params.put("drop-existing-db", "true");
		 // create command
		 JSONObject cmd = new JSONObject();
		 cmd.put("create-project-database-command", params);
		 return cmd.toJSONString();
	 }

	 public JSONObject getParameters(JSONObject reply) {
		 if (reply == null) {
			 return null;
		 }
		 return (JSONObject)reply.get("create-project-database-result");
	 }

	 public void handleParameters(JSONObject parameters) {
		 // get the parameter list
		 if (parameters == null) {
			 return;
		 }
		 // @todo: retrieve database name from reply (for console message)
		 
		 // - get result
		 String checkResult = (String)parameters.get("result");
		 if (!(checkResult.equals("PASS"))) {
			 resultValue = false;
		 } else {
			 resultValue = true;
		 }
	 }
}
