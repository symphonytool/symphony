package eu.compassresearch.core.analysis.modelchecker.formula.graph.event;

public class BasicEvent implements Event {
	private String eventName; 
	
	
	
	
	public BasicEvent(String name) {
		this.setEventName(name);
		
		
	}




	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}




	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}
