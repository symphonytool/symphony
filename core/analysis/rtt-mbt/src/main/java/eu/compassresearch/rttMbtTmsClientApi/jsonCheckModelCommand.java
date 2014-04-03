package eu.compassresearch.rttMbtTmsClientApi;

import java.io.File;
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
	
	private String reportName;
	private String modelName;
	private String modelId;
	private logExtent logOption;
	private Boolean guiPorts;

	public jsonCheckModelCommand(RttMbtClient client) {
		super(client);
		logOption = logExtent.STANDARD;
		guiPorts = false;
		reportName = null;
	}
	
	public void setReportName(String name) {
		reportName = name;
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
		String filename;
		if (client.getWorkspaceProjectName() != null) {
			if (reportName == null) {
				reportName = client.getRttProjectPath() + File.separator + "model" + File.separator + "LivelockReport.log";
			}
			filename = reportName;
		} else {
			filename = "LivelockReport.log";
		}
		writeBase64StringFileContent(filename, (String)reply.get("model-checking-results"), false);
	}

	public Boolean getGuiPorts() {
		return guiPorts;
	}

	public void setGuiPorts(Boolean guiPorts) {
		this.guiPorts = guiPorts;
		hasProgress = guiPorts;
		hasConsole = guiPorts;
	}
}
