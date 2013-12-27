package eu.compassresearch.rttMbtTmsClientApi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class jsonBuildMakeTargetCommand extends jsonCommand {

	 private String makefileName;
	 private String target;
	 private String makeTool;

	 public jsonBuildMakeTargetCommand(RttMbtClient client) {
		 super(client);
	 }

	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public String getJsonCommandString() {
		 // check if project name is properly assigned
		 if (client.getRttProjectName() == null) {
			 System.err.println("[ERROR]: project name not assigned!");
			 return null;
		 }

		 // add parameters
		 Map params = new LinkedHashMap();
		 params.put("user", client.getUserName());
		 params.put("user-id", client.getUserId());
		 params.put("filename", client.toUnixPath(client.removeLocalWorkspace(makefileName)));
		 params.put("target", getTarget());
		 params.put("make-tool", getMakeTool());
		 // create command
		 JSONObject cmd = new JSONObject();
		 cmd.put("build-make-target-command", params);
		 return cmd.toJSONString();
	 }

	 public JSONObject getParameters(JSONObject reply) {
		 if (reply == null) {
			 return null;
		 }
		 return (JSONObject)reply.get("build-make-target-result");
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

	 public String getMakefileName() {
		 return makefileName;
	 }

	 public void setMakefileName(String makefileName) {
		 this.makefileName = makefileName;
	 }

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getMakeTool() {
		return makeTool;
	}

	public void setMakeTool(String makeTool) {
		this.makeTool = makeTool;
	}

}
