package eu.compassresearch.rttMbtTmsClientApi;

public interface IRttMbtProgressMonitor {
	public void beginTask(String taskname, int totalWork);
	public void setSubTaskName(String taskname);
	public void addCompletedTaskItems(int itemsWorked);
	public boolean isCanceled();
	public void setCanceled(boolean canceled);
}
