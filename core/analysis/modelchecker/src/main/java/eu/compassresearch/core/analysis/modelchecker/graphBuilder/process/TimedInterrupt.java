package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

public class TimedInterrupt extends UntimedInterrupt {

	private String timeExp;
	
	public TimedInterrupt(Process firstProcess, Process secondProcess, String timeExp) {
		super(firstProcess, secondProcess);
		this.timeExp = timeExp;
	}

	@Override
	public String toString() {
		return this.getFirstProcess() + "/_" + timeExp + "_\\" + this.getSecondProcess();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof TimedInterrupt) {
			result = this.timeExp.equals(((TimedInterrupt) obj).timeExp)
					&& this.getFirstProcess().equals(((TimedInterrupt) obj).getFirstProcess())
					&& this.getSecondProcess().equals(((TimedInterrupt) obj).getSecondProcess());
		}
		return result;
	}
}
