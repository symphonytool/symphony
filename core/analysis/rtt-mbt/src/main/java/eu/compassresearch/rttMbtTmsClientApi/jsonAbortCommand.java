package eu.compassresearch.rttMbtTmsClientApi;

import org.json.simple.JSONObject;

public class jsonAbortCommand extends jsonCommand {

	private String jobId;

	public jsonAbortCommand(RttMbtClient client) {
		super(client);
	}
	
	@SuppressWarnings({ "unchecked" })
	public String getJsonCommandString() {

		// add parameters		
		int job = Integer.parseInt(jobId);
		JSONObject param = new JSONObject();
		param.put("abort", job);

		// create command
		JSONObject cmd = new JSONObject();
		cmd.put("abort-command", param);
		return cmd.toJSONString();
	}

	public void handleParameters(JSONObject parameters) {
		resultValue = true;
		// get the parameter list
		if (parameters == null) {
			return;
		}
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}
