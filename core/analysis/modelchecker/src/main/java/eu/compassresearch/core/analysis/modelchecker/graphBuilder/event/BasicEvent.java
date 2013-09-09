package eu.compassresearch.core.analysis.modelchecker.graphBuilder.event;


public class BasicEvent implements Event {
	private String eventName; 
	
	public BasicEvent(String name) {
		this.setEventName(name);
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	@Override
	public int hashCode() {
		return "ev".hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof BasicEvent){
			BasicEvent other = (BasicEvent) obj;
			result = this.getEventName().equals(other.getEventName());
		}
		return result;

	}

	@Override
	public String toString() {
		return eventName;
	}

}
