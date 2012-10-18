package eu.compassResearch.rttMbtTmsClientApi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

/**
 * @author uwe
 *
 */
public class jsonCheckModelCommand extends jsonCommand {

	public enum logExtent {
	    TINY, STANDARD, VERBOSE
	}
	
	private String modelName;
	private String modelId;
	private logExtent logOption;
	private Boolean guiPorts;

	public jsonCheckModelCommand(String server, Integer port, String user, String uid) {
		super(server, port, user, uid);
		logOption = logExtent.STANDARD;
		guiPorts = false;
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
		// set log option
		switch (logOption) {
        case TINY:  
        	params.put("log-extent", "TINY");
        	break;
        case VERBOSE:  
        	params.put("log-extent", "VERBOSE");
        	break;
        case STANDARD:
        default:
        	params.put("log-extent", "STANDARD");
        	break;
		}
		// use gui ports
		if (guiPorts) {
			params.put("progress-port", "true");
			params.put("console-port", "true");
		}
		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("check-model-command", params);
		return cmd.toJSONString();
	}

	public void handleCompletemessage(JSONObject reply) {
		if (reply == null) {
			return;
		}
		writeBase64StringFileContent("Livelockreport.log",
								     (String)reply.get("model-checking-results"), false);
	}
}
